package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.Currency;
import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BlockedAccts;
import com.ffusion.csil.handlers.AccountHandler;
import com.ffusion.efs.adapters.profile.CustomerAdapter;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class VerifyExtTransferAccount
  extends BaseTask
  implements Task
{
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected Locale locale;
  protected String numberOfAmounts = null;
  protected FundsTransactions amounts;
  protected FundsTransaction currAmount;
  protected ExtTransferAccount account;
  protected String accountCollection = "ExternalTransferAccounts";
  protected String accountBPWID = null;
  protected String verifyFailedURL = null;
  protected String verifyRetryURL = null;
  protected String sourceAccts = "BankingAccounts";
  
  public VerifyExtTransferAccount()
  {
    try
    {
      this.numberOfAmounts = com.ffusion.csil.handlers.ExternalTransferAdmin.getNumberOfVerifyDeposits();
    }
    catch (Exception localException)
    {
      localException.printStackTrace(System.out);
    }
    if (this.numberOfAmounts == null) {
      this.numberOfAmounts = "2";
    }
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag) {
      str = initProcess(paramHttpServletRequest, localHttpSession);
    } else {
      str = modifyTransferAccount(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  protected String initProcess(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    String str = this.successURL;
    ExtTransferAccounts localExtTransferAccounts = (ExtTransferAccounts)paramHttpSession.getAttribute(this.accountCollection);
    if (localExtTransferAccounts == null)
    {
      this.error = 4201;
      str = this.taskErrorURL;
      return str;
    }
    this.account = null;
    if (this.accountBPWID != null) {
      this.account = localExtTransferAccounts.getByID(this.accountBPWID);
    }
    if (this.account == null) {
      this.account = ((ExtTransferAccount)paramHttpSession.getAttribute("ExternalTransferACCOUNT"));
    }
    if (this.account == null)
    {
      this.error = 4206;
      str = this.taskErrorURL;
    }
    else
    {
      this.amounts = new FundsTransactions(this.locale);
      int i = Integer.parseInt(this.numberOfAmounts);
      for (int j = 0; j < i; j++)
      {
        FundsTransaction localFundsTransaction = (FundsTransaction)this.amounts.create();
        localFundsTransaction.setLocale(this.locale);
        localFundsTransaction.setID("" + j);
        localFundsTransaction.setAmount("0.00");
        localFundsTransaction.getAmountValue().setCurrencyCode(this.account.getCurrencyCode());
      }
      paramHttpSession.setAttribute("ExternalTransferACCOUNT", this.account);
      if (this.account.getStatusValue() == 3) {
        str = this.verifyFailedURL;
      }
    }
    return str;
  }
  
  protected String modifyTransferAccount(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = processModifyExtTransferAccount(paramHttpSession);
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String processModifyExtTransferAccount(HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    int[] arrayOfInt = new int[this.amounts.size()];
    Object localObject;
    for (int i = 0; i < this.amounts.size(); i++)
    {
      localObject = ((FundsTransaction)this.amounts.get(i)).getAmountValue().getAmountValue().movePointRight(2);
      arrayOfInt[i] = ((BigDecimal)localObject).intValue();
    }
    try
    {
      this.account = com.ffusion.csil.core.ExternalTransferAdmin.verifyExtTransferAccount(localSecureUser, this.account, arrayOfInt, localHashMap);
      Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this.sourceAccts);
      if (localAccounts != null)
      {
        localObject = localAccounts.getByNumberAndRoutingNum(this.account.getNumber(), this.account.getRoutingNumber());
        if (localObject != null)
        {
          ((Account)localObject).put("ExternalTransferACCOUNT", this.account);
          ((Account)localObject).put("EXTACCTID", this.account.getBpwID());
          ((Account)localObject).put("HIDE", "0");
          ((Account)localObject).setFilterable("ExternalTransferTo");
          ((Account)localObject).setFilterable("ExternalTransferFrom");
          if (this.account.getStatusValue() == 3)
          {
            AccountHandler.deleteAccount(localSecureUser, (Account)localObject, localSecureUser.getProfileID(), localHashMap);
            localAccounts.remove(localObject);
          }
        }
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
    }
    catch (Exception localException1)
    {
      DebugLog.log("ERROR: Exception thrown when verifying external transfer account:");
      localException1.printStackTrace();
    }
    String str;
    if (this.error == 0)
    {
      str = this.successURL;
      if (this.account.getStatusValue() == 3)
      {
        str = this.verifyFailedURL;
        BlockedAccount localBlockedAccount = new BlockedAccount();
        localBlockedAccount.setAccountNumber(this.account.getNumber());
        localBlockedAccount.setRoutingNumber(this.account.getRoutingNumber());
        localBlockedAccount.setBankName(this.account.getBankName());
        localBlockedAccount.setUserDirectoryID(localSecureUser.getProfileID());
        localObject = null;
        int j = localSecureUser.getProfileID();
        try
        {
          User localUser = CustomerAdapter.getUserById(j);
          if (localUser != null) {
            localObject = localUser.getUserName();
          }
          if (localObject != null) {
            localHashMap.put("UserName", ((String)localObject).trim());
          }
          BlockedAccts.addBlockedAccount(localSecureUser, localBlockedAccount, localHashMap);
        }
        catch (Exception localException2) {}
      }
      else if (this.account.getVerifyStatusValue() == 2)
      {
        str = this.verifyRetryURL;
      }
    }
    else
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null) {
      this.validate = null;
    }
    for (int i = 0; i < this.amounts.size(); i++)
    {
      BigDecimal localBigDecimal = ((FundsTransaction)this.amounts.get(i)).getAmountValue().getAmountValue().movePointRight(2);
      if (localBigDecimal.intValue() == 0)
      {
        this.error = 4217;
        bool = false;
      }
      else if (localBigDecimal.intValue() < 0)
      {
        this.error = 4218;
        bool = false;
      }
    }
    return bool;
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
    if (!"".equals(paramString)) {
      this.validate = paramString.toUpperCase();
    } else {
      this.validate = null;
    }
  }
  
  public FundsTransactions getAmounts()
  {
    return this.amounts;
  }
  
  public void setCurrentAmount(String paramString)
  {
    this.currAmount = ((FundsTransaction)this.amounts.getFirstByFilter("ID=" + paramString));
  }
  
  public void setAmount(String paramString)
  {
    if (this.currAmount != null) {
      this.currAmount.setAmount(paramString);
    }
  }
  
  public void setExtAccountCollectionName(String paramString)
  {
    this.accountCollection = paramString;
  }
  
  public void setExtAccountBPWID(String paramString)
  {
    this.accountBPWID = paramString;
  }
  
  public void setVerifyFailedURL(String paramString)
  {
    this.verifyFailedURL = paramString;
  }
  
  public String getVerifyFailedURL()
  {
    return this.verifyFailedURL;
  }
  
  public void setVerifyRetryURL(String paramString)
  {
    this.verifyRetryURL = paramString;
  }
  
  public String getVerifyRetryURL()
  {
    return this.verifyRetryURL;
  }
  
  public void setSourceAccounts(String paramString)
  {
    this.sourceAccts = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.VerifyExtTransferAccount
 * JD-Core Version:    0.7.0.1
 */