package com.ffusion.tasks.banking;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.core.BlockedAccts;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import com.ffusion.util.enums.UserAssignedAmount;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditTransfer
  extends RecTransfer
  implements Task
{
  protected static String FROMACCOUNT = "FROMACCOUNT";
  protected static String TOACCOUNT = "TOACCOUNT";
  protected static String TRANSFERDESTINATION = "TRANSFERDESTINATION";
  protected static String AMOUNT = "AMOUNT";
  protected static String DATE = "DATE";
  protected static String FREQUENCY = "FREQUENCY";
  protected static String NUMBERTRANSFERS = "NUMBERTRANSFERS";
  protected String dateWasChangedURL;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected String minAmount = "0.01";
  protected String maxAmount = null;
  protected int minTransfers = 2;
  protected String transferDate;
  protected Transfer currentTransfer;
  protected Transfer originalTransfer;
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag)
    {
      if (initProcess(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else {
      str = processEditTransfer(paramHttpServletRequest, localHttpSession);
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("TransfersUpdated", "true");
    }
    return str;
  }
  
  protected boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    if (paramHttpSession.getAttribute("Transfer") == null)
    {
      this.error = 1004;
    }
    else
    {
      this.error = 0;
      if ((paramHttpSession.getAttribute("Transfer") instanceof RecTransfer))
      {
        set((RecTransfer)paramHttpSession.getAttribute("Transfer"));
        this.originalTransfer = ((RecTransfer)paramHttpSession.getAttribute("Transfer"));
      }
      else
      {
        set((Transfer)paramHttpSession.getAttribute("Transfer"));
        this.originalTransfer = ((Transfer)paramHttpSession.getAttribute("Transfer"));
      }
    }
    return this.error == 0;
  }
  
  protected String processEditTransfer(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = editTransfer(paramHttpSession);
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
  
  protected String editTransfer(HttpSession paramHttpSession)
  {
    String str = null;
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)paramHttpSession.getAttribute(this.bankingServiceName);
    if (paramHttpSession.getAttribute("Transfer") == null)
    {
      this.error = 1004;
      str = this.taskErrorURL;
    }
    else
    {
      this.error = 0;
      Object localObject1 = paramHttpSession.getAttribute("Transfer");
      HashMap localHashMap = new HashMap();
      if (localBanking != null) {
        localHashMap.put("SERVICE", localBanking);
      }
      localHashMap.put("FROMACCOUNT", this.fromAccount);
      localHashMap.put("TOACCOUNT", this.toAccount);
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      setBankID(localSecureUser.getBPWFIID());
      this.originalTransfer.setBankID(localSecureUser.getBPWFIID());
      try
      {
        Object localObject2;
        if ((localObject1 instanceof RecTransfer))
        {
          if ("RECTEMPLATE".equals(getTransferType()))
          {
            com.ffusion.csil.core.Banking.modifyRecTransferTemplate(localSecureUser, this, localHashMap);
          }
          else
          {
            localObject2 = com.ffusion.csil.core.Banking.modifyRecTransfer(localSecureUser, this, (RecTransfer)this.originalTransfer, localHashMap);
            set((RecTransfer)localObject2);
          }
        }
        else if ("TEMPLATE".equals(getTransferType()))
        {
          com.ffusion.csil.core.Banking.modifyTransferTemplate(localSecureUser, this, localHashMap);
        }
        else
        {
          localObject2 = com.ffusion.csil.core.Banking.modifyTransfer(localSecureUser, this, this.originalTransfer, localHashMap);
          set((Transfer)localObject2);
        }
      }
      catch (CSILException localCSILException)
      {
        if ((localCSILException.getCode() == 20003) || (localCSILException.getServiceError() == 20003)) {
          paramHttpSession.setAttribute("ExceededLimits", localHashMap.get("ExceededLimits"));
        }
        this.error = MapError.mapError(localCSILException);
      }
      if (this.error == 0)
      {
        Object localObject3;
        if ((localObject1 instanceof RecTransfer))
        {
          localObject3 = (RecTransfer)paramHttpSession.getAttribute("Transfer");
          ((RecTransfer)localObject3).set(this);
        }
        else
        {
          localObject3 = (Transfer)paramHttpSession.getAttribute("Transfer");
          ((Transfer)localObject3).set(this);
        }
        str = this.successURL;
      }
      else
      {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null)
    {
      if ((bool) && (this.validate.indexOf(FROMACCOUNT) != -1)) {
        bool = validateFromAccount(paramHttpSession, this.fromAccountID);
      }
      if ((bool) && (this.validate.indexOf(TOACCOUNT) != -1)) {
        bool = validateToAccount(paramHttpSession, this.toAccountID);
      }
      if ((bool) && (this.validate.indexOf(TRANSFERDESTINATION) != -1)) {
        bool = validateTransferDestination(paramHttpSession);
      }
      if ((bool) && (this.validate.indexOf(AMOUNT) != -1)) {
        bool = validateAmount();
      }
      if ((bool) && (this.validate.indexOf(DATE) != -1)) {
        bool = validateDate();
      }
      if ((bool) && (this.validate.indexOf(NUMBERTRANSFERS) != -1)) {
        bool = validateNumberTransfers();
      }
      if ((bool) && (this.validate.indexOf(FREQUENCY) != -1)) {
        bool = f();
      }
      if ((bool) && (this.validate.indexOf("TEMPLATENAME") != -1)) {
        bool = g();
      }
      if ((bool) && ((this.validate.indexOf(FROMACCOUNT) != -1) || (this.validate.indexOf(TOACCOUNT) != -1)) && (getFromAccountID().equals(getToAccountID())))
      {
        this.error = 1016;
        bool = false;
      }
      if ((bool) && (this.originalTransfer != null) && (this.originalTransfer.getTransferDestination() != null) && (!this.originalTransfer.getTransferDestination().equals(getTransferDestination()))) {
        if (this.originalTransfer.getTransferDestination().equals("INTERNAL"))
        {
          bool = false;
          this.error = 1041;
        }
        else if (getTransferDestination().equals("INTERNAL"))
        {
          bool = false;
          this.error = 1042;
        }
        else
        {
          bool = false;
          this.error = 1055;
        }
      }
      if ((bool) && (this.fromAccount != null) && (this.toAccount != null)) {
        if ((jdMethod_int(this.fromAccount, paramHttpSession)) && (!this.toAccount.getCurrencyCode().equals("USD")))
        {
          bool = false;
          this.error = 1054;
        }
        else if ((jdMethod_int(this.toAccount, paramHttpSession)) && (!this.fromAccount.getCurrencyCode().equals("USD")))
        {
          bool = false;
          this.error = 1053;
        }
      }
      this.validate = null;
    }
    return bool;
  }
  
  private boolean jdMethod_int(Account paramAccount, HttpSession paramHttpSession)
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
  
  protected boolean validateTransferDestination(HttpSession paramHttpSession)
  {
    this.error = 0;
    if ((getFromAccount() != null) && (getFromAccount().getCoreAccount() != null) && (getFromAccount().getCoreAccount().equals("1")) && (getToAccount() != null) && (getToAccount().getCoreAccount() != null) && (getToAccount().getCoreAccount().equals("1")))
    {
      if ((!getTransferDestination().equals("INTERNAL")) && (!getTransferDestination().equals("ITOI"))) {
        this.error = 1048;
      }
    }
    else if ((!getTransferDestination().equals("ITOE")) && (!getTransferDestination().equals("ITOE")) && (!getTransferDestination().equals("ETOI"))) {
      this.error = 1048;
    }
    return this.error == 0;
  }
  
  protected boolean validateFromAccount(HttpSession paramHttpSession, String paramString)
  {
    this.error = 0;
    if ((paramString == null) || (paramString.length() == 0))
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
        Account localAccount = localAccounts.getByID(paramString);
        if (localAccount == null)
        {
          this.error = 1025;
        }
        else
        {
          SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
          BlockedAccount localBlockedAccount = new BlockedAccount();
          localBlockedAccount.setAccountNumber(localAccount.getNumber());
          localBlockedAccount.setRoutingNumber(localAccount.getRoutingNum());
          localBlockedAccount.setBankName(localAccount.getBankName());
          localBlockedAccount.setUserDirectoryID(localAccount.getDirectoryID());
          try
          {
            if (BlockedAccts.isBlockedAccount(localSecureUser, localBlockedAccount, new HashMap())) {
              this.error = 4223;
            } else {
              setFromAccount(localAccount);
            }
          }
          catch (Exception localException) {}
        }
      }
    }
    return this.error == 0;
  }
  
  protected boolean validateToAccount(HttpSession paramHttpSession, String paramString)
  {
    this.error = 0;
    if ((paramString == null) || (paramString.length() == 0))
    {
      this.error = 1028;
    }
    else
    {
      Accounts localAccounts = (Accounts)paramHttpSession.getAttribute("BankingAccounts");
      localAccounts.setFilter("All");
      Account localAccount = localAccounts.getByID(paramString);
      if (localAccount == null)
      {
        this.error = 1027;
      }
      else
      {
        SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
        BlockedAccount localBlockedAccount = new BlockedAccount();
        localBlockedAccount.setAccountNumber(localAccount.getNumber());
        localBlockedAccount.setRoutingNumber(localAccount.getRoutingNum());
        localBlockedAccount.setBankName(localAccount.getBankName());
        localBlockedAccount.setUserDirectoryID(localAccount.getDirectoryID());
        try
        {
          if (BlockedAccts.isBlockedAccount(localSecureUser, localBlockedAccount, new HashMap())) {
            this.error = 4223;
          } else {
            setToAccount(localAccount);
          }
        }
        catch (Exception localException) {}
      }
    }
    return this.error == 0;
  }
  
  protected boolean validateAmount()
  {
    if (!validateAmountCurrency()) {
      return false;
    }
    Currency localCurrency1 = this.amount;
    if (this.userAssignedAmount == UserAssignedAmount.TO) {
      localCurrency1 = this.toAmount;
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
  
  protected boolean validateAmountCurrency()
  {
    String str1 = this.fromAccount.getCurrencyCode();
    String str2 = this.toAccount.getCurrencyCode();
    if (this.amount != null) {
      this.amount.setCurrencyCode(str1);
    }
    if (this.toAmount != null) {
      this.toAmount.setCurrencyCode(str2);
    }
    if (!str1.equals(str2))
    {
      if ((this.userAssignedAmount == null) || (this.userAssignedAmount == UserAssignedAmount.SINGLE))
      {
        this.error = 1052;
        return false;
      }
    }
    else
    {
      this.userAssignedAmount = UserAssignedAmount.SINGLE;
      this.toAmount = null;
    }
    return true;
  }
  
  protected boolean validateDate()
  {
    this.error = 0;
    if ((this.transferDate == null) || (this.transferDate.trim().length() == 0))
    {
      this.date = null;
      this.error = 1010;
    }
    else
    {
      DateTime localDateTime1 = new DateTime(getLocale());
      try
      {
        if (this.date == null) {
          this.date = new DateTime(this.transferDate, this.locale, this.datetype);
        } else {
          this.date.fromString(this.transferDate);
        }
        DateTime localDateTime2 = (DateTime)this.date.clone();
        localDateTime2.set(1, localDateTime1.get(1));
        localDateTime2.set(2, localDateTime1.get(2));
        localDateTime2.set(5, localDateTime1.get(5));
        if (this.date.before(localDateTime2))
        {
          this.date = localDateTime2;
          this.error = 1015;
        }
      }
      catch (InvalidDateTimeException localInvalidDateTimeException)
      {
        this.date = null;
        this.error = 1010;
      }
    }
    return this.error == 0;
  }
  
  protected boolean validateNumberTransfers()
  {
    if (this.numberTransfers < this.minTransfers) {
      this.error = 1012;
    } else {
      this.error = 0;
    }
    return this.error == 0;
  }
  
  private boolean f()
  {
    if ((this.frequency > 0) && (this.frequency < 10)) {
      this.error = 0;
    } else {
      this.error = 1011;
    }
    return this.error == 0;
  }
  
  private boolean g()
  {
    if ((this.templateName != null) && (this.templateName.trim().length() > 0)) {
      this.error = 0;
    } else {
      this.error = 1036;
    }
    return this.error == 0;
  }
  
  public void setDate(String paramString)
  {
    this.transferDate = paramString;
    try
    {
      if (this.date == null) {
        this.date = new DateTime(this.transferDate, this.locale, this.datetype);
      } else {
        this.date.fromString(this.transferDate);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.date = null;
    }
  }
  
  public String getDate()
  {
    if (this.date != null) {
      return this.date.toString();
    }
    return this.transferDate;
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
  
  public void setDateWasChangedURL(String paramString)
  {
    this.dateWasChangedURL = paramString;
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
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.EditTransfer
 * JD-Core Version:    0.7.0.1
 */