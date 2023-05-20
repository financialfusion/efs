package com.ffusion.tasks.banking;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.TransferBatch;
import com.ffusion.beans.banking.TransferBatches;
import com.ffusion.beans.banking.Transfers;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.MapError;
import com.ffusion.util.enums.UserAssignedAmount;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditTransferBatch
  extends TransferBatch
  implements Task
{
  protected static String FROMACCOUNT = "FROMACCOUNT";
  protected static String TOACCOUNT = "TOACCOUNT";
  protected static String AMOUNT = "AMOUNT";
  protected static String DATE = "DATE";
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected String minAmount = "0.01";
  protected String maxAmount = null;
  protected int minTransfers = 2;
  protected Transfer currentTransfer;
  protected TransferBatch originalTransferBatch;
  protected String bankingServiceName = "com.ffusion.services.Banking";
  protected String accountsName = "BankingAccounts";
  protected int maxTransfers = 5;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag) {
      initProcess(localHttpSession);
    } else {
      str = processEditTransfer(paramHttpServletRequest, localHttpSession);
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("TransfersUpdated", "true");
    }
    return str;
  }
  
  protected void initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    if (paramHttpSession.getAttribute("TransferBatch") == null)
    {
      this.error = 1004;
    }
    else
    {
      this.error = 0;
      set((TransferBatch)paramHttpSession.getAttribute("TransferBatch"));
      this.originalTransferBatch = ((TransferBatch)paramHttpSession.getAttribute("TransferBatch"));
      for (int i = getTransfers().size(); i < getMaxTransfersValue(); i++)
      {
        Transfer localTransfer = (Transfer)getTransfers().create();
        localTransfer.setID(String.valueOf(i));
        localTransfer.setDateFormat(this.datetype);
        localTransfer.setTransferType("TEMPLATE");
      }
    }
  }
  
  protected String processEditTransfer(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = editTransferBatch(paramHttpSession);
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String editTransferBatch(HttpSession paramHttpSession)
  {
    String str = this.serviceErrorURL;
    HashMap localHashMap = new HashMap();
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHttpSession.getAttribute(this.bankingServiceName);
    if (localBanking != null) {
      localHashMap.put("SERVICE", localBanking);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(getAccountsName());
    if (localAccounts != null) {
      put("Accounts", localAccounts);
    }
    TransferBatch localTransferBatch1 = (TransferBatch)paramHttpSession.getAttribute("TransferBatch");
    Transfers localTransfers = new Transfers();
    Iterator localIterator = getTransfers().iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = (Transfer)localIterator.next();
      if (((Transfer)localObject).getFromAccountID().equals(""))
      {
        if (((Transfer)localObject).getID().length() > 2)
        {
          Transfer localTransfer = localTransferBatch1.getTransfers().getByID(((Transfer)localObject).getID());
          localTransfer.setAmount("0");
          localTransfers.add(localTransfer);
        }
        localIterator.remove();
      }
      else if (!((Transfer)localObject).getFromAccount().getCoreAccount().equals("1"))
      {
        ((Transfer)localObject).setTransferDestination("ETOI");
      }
      else if (!((Transfer)localObject).getToAccount().getCoreAccount().equals("1"))
      {
        ((Transfer)localObject).setTransferDestination("ITOE");
      }
      else if ((((Transfer)localObject).getTransferType().equals("TEMPLATE")) || (((Transfer)localObject).getTransferType().equals("RECTEMPLATE")))
      {
        ((Transfer)localObject).setTransferDestination("ITOI");
      }
      else
      {
        ((Transfer)localObject).setTransferDestination("INTERNAL");
      }
    }
    getTransfers().addAll(localTransfers);
    if (!getHasSingleUserAssignedAmountCurrency()) {
      setAmount(new Currency(new BigDecimal(0.0D), null, this.locale));
    }
    this.error = 0;
    try
    {
      com.ffusion.csil.core.Banking.modifyTransferBatch(localSecureUser, this, localTransferBatch1, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
        paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
      }
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    catch (Exception localException)
    {
      DebugLog.log("ERROR: Exception thrown when modifying transfer:");
      localException.printStackTrace();
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      str = this.successURL;
      TransferBatch localTransferBatch2 = new TransferBatch(this.locale);
      localTransferBatch2.set(this);
      paramHttpSession.setAttribute("TransferBatch", localTransferBatch2);
      localObject = (TransferBatches)paramHttpSession.getAttribute("TransferBatches");
      if (localObject != null) {
        ((TransferBatches)localObject).add(localTransferBatch2);
      }
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      int i = 0;
      BigDecimal localBigDecimal = new BigDecimal(0.0D);
      String str = null;
      if ((bool) && (this.validate.indexOf("TEMPLATENAME") != -1)) {
        bool = e();
      }
      if (bool)
      {
        int j = 0;
        Iterator localIterator = getTransfers().iterator();
        while (localIterator.hasNext())
        {
          Transfer localTransfer = (Transfer)localIterator.next();
          localTransfer.resetValidationErrors();
          Object localObject;
          if (hasValue(localTransfer.getFromAccountID()))
          {
            i++;
            if (!hasValue(localTransfer.getToAccountID()))
            {
              this.error = 1028;
              bool = false;
            }
            if ((this.validate.indexOf(DATE) != -1) && (!hasValue(localTransfer.getDate())))
            {
              this.error = 1010;
              bool = false;
            }
            if ((bool) && (this.validate.indexOf(FROMACCOUNT) != -1)) {
              bool = validateFromAccount(paramHttpSession, localTransfer);
            }
            if ((bool) && (this.validate.indexOf(TOACCOUNT) != -1)) {
              bool = validateToAccount(paramHttpSession, localTransfer);
            }
            if ((bool) && (this.validate.indexOf(AMOUNT) != -1)) {
              bool = validateAmount(localTransfer);
            }
            if ((bool) && (this.validate.indexOf(DATE) != -1)) {
              bool = validateDate(localTransfer);
            }
            if ((bool) && ((this.validate.indexOf(FROMACCOUNT) != -1) || (this.validate.indexOf(TOACCOUNT) != -1)) && (localTransfer.getFromAccountID().equals(localTransfer.getToAccountID())))
            {
              this.error = 1016;
              bool = false;
            }
            if (bool)
            {
              localObject = localTransfer.getFromAccount();
              Account localAccount = localTransfer.getToAccount();
              if ((jdMethod_for((Account)localObject, paramHttpSession)) && (!localAccount.getCurrencyCode().equals("USD")))
              {
                bool = false;
                this.error = 1054;
              }
              else if ((jdMethod_for(localAccount, paramHttpSession)) && (!((Account)localObject).getCurrencyCode().equals("USD")))
              {
                bool = false;
                this.error = 1053;
              }
            }
          }
          else if ((hasValue(localTransfer.getToAccountID())) || (validateAmount(localTransfer)))
          {
            this.error = 1026;
            bool = false;
          }
          if (!bool) {
            break;
          }
          if (j == 0)
          {
            localObject = localTransfer.getUserAssignedAmountValue();
            if (str == null)
            {
              str = ((Currency)localObject).getCurrencyCode();
              localBigDecimal = localBigDecimal.add(((Currency)localObject).getAmountValue());
            }
            else if (str.equals(((Currency)localObject).getCurrencyCode()))
            {
              localBigDecimal = localBigDecimal.add(((Currency)localObject).getAmountValue());
            }
            else
            {
              localBigDecimal = new BigDecimal(0.0D);
              j = 1;
            }
          }
        }
      }
      if ((bool) && (i < this.minTransfers))
      {
        this.error = 1047;
        bool = false;
      }
      setTransferCount(i);
      if (localBigDecimal.doubleValue() == 0.0D) {
        str = null;
      }
      setAmount(new Currency(localBigDecimal, str, this.locale));
      this.validate = null;
    }
    return bool;
  }
  
  private boolean jdMethod_for(Account paramAccount, HttpSession paramHttpSession)
  {
    try
    {
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(localSecureUser), new Entitlement("External Transfers Via ACH", null, null))) {
        return false;
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.WARNING, "Unable to check entitlement External Transfers Via ACH: " + localCSILException.getMessage());
      localCSILException.printStackTrace();
      return false;
    }
    ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)paramAccount.get("ExternalTransferACCOUNT");
    if ((localExtTransferAccount != null) && (localExtTransferAccount.getAcctBankIDType().equals("FEDABA"))) {
      return true;
    }
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("TransferAccounts");
    if (localAccounts != null)
    {
      localAccounts.setFilter("All");
      Account localAccount = localAccounts.getByID(paramAccount.getID());
      if (localAccount != null)
      {
        String str = (String)localAccount.get("ACCTBANKRTNTYPE");
        if ((str != null) && (str.equals("FEDABA"))) {
          return true;
        }
      }
    }
    return false;
  }
  
  protected boolean validateFromAccount(HttpSession paramHttpSession, Transfer paramTransfer)
  {
    this.error = 0;
    String str = paramTransfer.getFromAccountID();
    if ((str == null) || (str.length() == 0))
    {
      this.error = 1026;
    }
    else
    {
      Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BankingAccounts");
      if (localAccounts == null)
      {
        this.error = 1001;
      }
      else
      {
        localAccounts.setFilter("All");
        Account localAccount = localAccounts.getByID(str);
        if (localAccount == null) {
          this.error = 1025;
        } else {
          paramTransfer.setFromAccount(localAccount);
        }
      }
    }
    return this.error == 0;
  }
  
  protected boolean validateToAccount(HttpSession paramHttpSession, Transfer paramTransfer)
  {
    this.error = 0;
    String str = paramTransfer.getToAccountID();
    if ((str == null) || (str.length() == 0))
    {
      this.error = 1028;
    }
    else
    {
      Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BankingAccounts");
      if (localAccounts == null)
      {
        this.error = 1001;
      }
      else
      {
        localAccounts.setFilter("All");
        Account localAccount = localAccounts.getByID(str);
        if (localAccount == null) {
          this.error = 1027;
        } else {
          paramTransfer.setToAccount(localAccount);
        }
      }
    }
    return this.error == 0;
  }
  
  protected boolean validateAmount(Transfer paramTransfer)
  {
    if (!validateAmountCurrency(paramTransfer)) {
      return false;
    }
    Currency localCurrency1 = paramTransfer.getAmountValue();
    if (paramTransfer.getUserAssignedAmountFlag() == UserAssignedAmount.TO) {
      localCurrency1 = paramTransfer.getToAmountValue();
    }
    if ((localCurrency1 == null) || (localCurrency1.getIsZero()))
    {
      this.error = 1009;
      return false;
    }
    Currency localCurrency2 = null;
    try
    {
      localCurrency2 = new Currency(this.minAmount, localCurrency1.getCurrencyCode(), this.locale);
    }
    catch (IllegalArgumentException localIllegalArgumentException1)
    {
      this.error = 1013;
      return false;
    }
    if (localCurrency1.compareTo(localCurrency2) == -1)
    {
      this.error = 1017;
      return false;
    }
    if (this.maxAmount != null)
    {
      Currency localCurrency3 = null;
      try
      {
        localCurrency3 = new Currency(this.maxAmount, localCurrency1.getCurrencyCode(), this.locale);
      }
      catch (IllegalArgumentException localIllegalArgumentException2)
      {
        this.error = 1014;
        return false;
      }
      if (localCurrency1.compareTo(localCurrency3) == 1)
      {
        this.error = 1018;
        return false;
      }
    }
    if (localCurrency1.getAmountValue().compareTo(Task.MAX_AMOUNT_ALLOWED) > 0)
    {
      this.error = 1018;
      return false;
    }
    return true;
  }
  
  protected boolean validateAmountCurrency(Transfer paramTransfer)
  {
    if ((paramTransfer.getFromAccount() != null) && (paramTransfer.getToAccount() != null))
    {
      String str1 = paramTransfer.getFromAccount().getCurrencyCode();
      String str2 = paramTransfer.getToAccount().getCurrencyCode();
      if (paramTransfer.getAmountValue() != null) {
        paramTransfer.getAmountValue().setCurrencyCode(str1);
      }
      if (paramTransfer.getToAmountValue() != null) {
        paramTransfer.getToAmountValue().setCurrencyCode(str2);
      }
      if (!str1.equals(str2))
      {
        if ((paramTransfer.getUserAssignedAmountFlag() == null) || (paramTransfer.getUserAssignedAmountFlag() == UserAssignedAmount.SINGLE))
        {
          this.error = 1052;
          return false;
        }
      }
      else
      {
        paramTransfer.setUserAssignedAmountFlag(UserAssignedAmount.SINGLE);
        paramTransfer.setToAmount((Currency)null);
      }
    }
    return true;
  }
  
  protected boolean validateDate(Transfer paramTransfer)
  {
    this.error = 0;
    if ((paramTransfer.getDate() == null) || (paramTransfer.getDate().trim().length() == 0))
    {
      this.error = 1010;
    }
    else
    {
      DateTime localDateTime1 = new DateTime(getLocale());
      DateTime localDateTime2 = (DateTime)paramTransfer.getDateValue().clone();
      localDateTime2.set(1, localDateTime1.get(1));
      localDateTime2.set(2, localDateTime1.get(2));
      localDateTime2.set(5, localDateTime1.get(5));
      if (paramTransfer.getDateValue().before(localDateTime2)) {
        this.error = 1015;
      }
    }
    return this.error == 0;
  }
  
  private boolean e()
  {
    if ((this.templateName != null) && (this.templateName.trim().length() > 0)) {
      this.error = 0;
    } else {
      this.error = 1036;
    }
    return this.error == 0;
  }
  
  public void setMinimumAmount(String paramString)
  {
    this.minAmount = paramString;
  }
  
  public void setMaximumAmount(String paramString)
  {
    this.maxAmount = paramString;
  }
  
  public void setMinTransfers(String paramString)
  {
    try
    {
      setMinTransfers(Integer.valueOf(paramString).intValue());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      setMinTransfers(2);
    }
  }
  
  public void setMinTransfers(int paramInt)
  {
    if (paramInt < 0) {
      paramInt = 0;
    }
    this.minTransfers = paramInt;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    } else {
      this.validate = null;
    }
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setCurrentTransfer(Transfer paramTransfer)
  {
    this.currentTransfer = paramTransfer;
  }
  
  public void setCurrentTransfer(String paramString)
  {
    if (getTransfers() != null) {
      this.currentTransfer = getTransfers().getByID(paramString);
    }
  }
  
  public String getTransferDestination()
  {
    if (this.currentTransfer != null) {
      return this.currentTransfer.getTransferDestination();
    }
    return null;
  }
  
  public void setTransferDestination(String paramString)
  {
    if (this.currentTransfer != null) {
      this.currentTransfer.setTransferDestination(paramString);
    }
  }
  
  public String getTransferFromAccountID()
  {
    if (this.currentTransfer != null) {
      return this.currentTransfer.getFromAccountID();
    }
    return null;
  }
  
  public void setTransferFromAccountID(String paramString)
  {
    if (this.currentTransfer != null)
    {
      this.currentTransfer.setClearFromAccount(null);
      this.currentTransfer.setFromAccountID(paramString);
    }
  }
  
  public Account getTransferFromAccount()
  {
    if (this.currentTransfer != null) {
      return this.currentTransfer.getFromAccount();
    }
    return null;
  }
  
  public String getTransferToAccountID()
  {
    if (this.currentTransfer != null) {
      return this.currentTransfer.getFromAccountID();
    }
    return null;
  }
  
  public void setTransferToAccountID(String paramString)
  {
    if (this.currentTransfer != null)
    {
      this.currentTransfer.setClearToAccount(null);
      this.currentTransfer.setToAccountID(paramString);
    }
  }
  
  public String getTransferAmount()
  {
    if (this.currentTransfer != null) {
      return this.currentTransfer.getAmount();
    }
    return null;
  }
  
  public void setTransferAmount(String paramString)
  {
    if (this.currentTransfer != null) {
      this.currentTransfer.setAmount(paramString);
    }
  }
  
  public void setTransferToAmount(String paramString)
  {
    if (this.currentTransfer != null) {
      this.currentTransfer.setToAmount(paramString);
    }
  }
  
  public void setUserAssignedAmountFlagName(String paramString)
  {
    if (this.currentTransfer != null) {
      this.currentTransfer.setUserAssignedAmountFlagName(paramString);
    }
  }
  
  public String getTransferDate()
  {
    if (this.currentTransfer != null) {
      return this.currentTransfer.getDate();
    }
    return null;
  }
  
  public void setTransferDate(String paramString)
  {
    if (this.currentTransfer != null) {
      if (hasValue(paramString)) {
        this.currentTransfer.setDate(paramString);
      } else {
        this.currentTransfer.setDate((DateTime)null);
      }
    }
  }
  
  public String getTransferMemo()
  {
    if (this.currentTransfer != null) {
      return this.currentTransfer.getMemo();
    }
    return null;
  }
  
  public void setTransferMemo(String paramString)
  {
    if (this.currentTransfer != null) {
      this.currentTransfer.setMemo(paramString);
    }
  }
  
  public String getTransferCategory()
  {
    if (this.currentTransfer != null) {
      return (String)this.currentTransfer.get("REGISTER_CATEGORY_ID");
    }
    return null;
  }
  
  public void setTransferCategory(String paramString)
  {
    if (this.currentTransfer != null) {
      this.currentTransfer.set("REGISTER_CATEGORY_ID", paramString);
    }
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public String getMaxTransfers()
  {
    return String.valueOf(this.maxTransfers);
  }
  
  public int getMaxTransfersValue()
  {
    return this.maxTransfers;
  }
  
  public void setMaxTransfers(int paramInt)
  {
    this.maxTransfers = paramInt;
  }
  
  public void setMaxTransfers(String paramString)
  {
    this.maxTransfers = Integer.parseInt(paramString);
  }
  
  public String getAccountsName()
  {
    return this.accountsName;
  }
  
  public void setAccountsName(String paramString)
  {
    this.accountsName = paramString;
  }
  
  public boolean hasValue(Object paramObject)
  {
    if (paramObject == null) {
      return false;
    }
    if ((paramObject instanceof String))
    {
      if (((String)paramObject).trim().equals("")) {
        return false;
      }
    }
    else if (((paramObject instanceof Currency)) && (((Currency)paramObject).getIsZero())) {
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.EditTransferBatch
 * JD-Core Version:    0.7.0.1
 */