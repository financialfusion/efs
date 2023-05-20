package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ExternalTransferAdmin;
import com.ffusion.csil.handlers.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DepositAmountsForVerify
  extends BaseTask
  implements Task
{
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected boolean canDeposit = false;
  protected boolean isConsumer = false;
  protected boolean alreadyProcessed = false;
  protected Locale locale;
  protected ExtTransferAccount account;
  protected String accountCollection = "ExternalTransferAccounts";
  protected String accountBPWID = null;
  protected String affiliateBankBPWID = null;
  protected String custID = null;
  protected AffiliateBank bank = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag) {
      str = initProcess(paramHttpServletRequest, localHttpSession);
    } else {
      str = depositAmountsForVerify(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  protected String initProcess(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    try
    {
      this.bank = new AffiliateBank();
      if (this.affiliateBankBPWID != null) {
        this.bank.setFIBPWID(this.affiliateBankBPWID);
      } else {
        this.bank.setFIBPWID(localSecureUser.getBPWFIID());
      }
      this.bank = ACH.getAffiliateBank(localSecureUser, this.bank, localHashMap);
    }
    catch (Exception localException)
    {
      DebugLog.log("ERROR: Exception thrown when trying to get affiliate bank:");
      localException.printStackTrace();
    }
    if (this.bank == null)
    {
      this.error = 25504;
      str = this.taskErrorURL;
      return str;
    }
    if ((this.bank.getEtfCompany() != null) && (this.bank.getEtfAccountNum() != null) && (this.bank.getEtfAccountNum().trim().length() > 0)) {
      this.canDeposit = true;
    }
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
      return str;
    }
    paramHttpSession.setAttribute("ExternalTransferACCOUNT", this.account);
    if ((this.isConsumer) && (this.account.getVerifyStatusValue() != 1))
    {
      this.error = 4225;
      str = this.taskErrorURL;
      return str;
    }
    return str;
  }
  
  protected String depositAmountsForVerify(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (this.account == null)
    {
      str = this.taskErrorURL;
      this.error = 4206;
    }
    else if ((!"true".equalsIgnoreCase(this.account.getCanDepositNow())) && (!"true".equalsIgnoreCase(this.account.getCanCSRRedeposit())))
    {
      str = this.taskErrorURL;
      this.error = 4216;
    }
    if (!this.canDeposit)
    {
      str = this.taskErrorURL;
      this.error = 4215;
    }
    int i = 0;
    synchronized (this)
    {
      if ((this.processFlag) && (this.error == 0) && (!this.alreadyProcessed)) {
        i = 1;
      }
    }
    if (i != 0)
    {
      this.alreadyProcessed = true;
      this.processFlag = false;
      str = processDepositAmountsForVerify(paramHttpSession);
    }
    else if (this.error != 0)
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String processDepositAmountsForVerify(HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    if (this.custID != null) {
      localHashMap.put("CustID", this.custID);
    } else {
      localHashMap.put("CustID", "" + localSecureUser.getProfileID());
    }
    try
    {
      this.account = ExternalTransferAdmin.depositAmountsForVerify(localSecureUser, this.account, this.bank, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
    }
    catch (Exception localException)
    {
      DebugLog.log("ERROR: Exception thrown when deposit amounts for verify:");
      localException.printStackTrace();
    }
    String str;
    if (this.error == 0) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getCanDeposit()
  {
    return "" + this.canDeposit;
  }
  
  public void setExtAccountCollectionName(String paramString)
  {
    this.accountCollection = paramString;
  }
  
  public void setExtAccountBPWID(String paramString)
  {
    this.accountBPWID = paramString;
  }
  
  public void setAffiliateBankBPWID(String paramString)
  {
    this.affiliateBankBPWID = paramString;
  }
  
  public void setCustID(String paramString)
  {
    this.custID = paramString;
  }
  
  public void setIsConsumer(String paramString)
  {
    this.isConsumer = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.DepositAmountsForVerify
 * JD-Core Version:    0.7.0.1
 */