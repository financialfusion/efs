package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.ExternalTransferAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetExtTransferAccounts
  implements Task
{
  protected String fiId;
  protected String affiliateId;
  protected String custId;
  protected String nickname;
  protected String number;
  protected String type;
  protected ExtTransferAccount searchAccount;
  protected ExtTransferCompany company;
  protected String nextURL = null;
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected Locale locale;
  protected String accountsSessionName = "ExternalTransferAccounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag) {
      str = initProcess(paramHttpServletRequest, localHttpSession);
    } else if (getExtTransferAccounts(localHttpSession)) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected String initProcess(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    return this.successURL;
  }
  
  protected boolean getExtTransferAccounts(HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.error = 0;
    ExtTransferAccounts localExtTransferAccounts = null;
    this.searchAccount = new ExtTransferAccount(this.locale);
    this.company = new ExtTransferCompany(this.locale);
    this.company.setCustID(this.custId);
    try
    {
      this.fiId = null;
      if ((this.affiliateId != null) && (this.affiliateId.trim().length() > 0))
      {
        AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankInfoByID(localSecureUser, Integer.parseInt(this.affiliateId), localHashMap);
        this.fiId = localAffiliateBank.getFIBPWID();
      }
      if ((this.nickname != null) && (this.nickname.trim().length() > 0)) {
        this.searchAccount.setNickname(this.nickname);
      }
      if ((this.number != null) && (this.number.trim().length() > 0)) {
        this.searchAccount.setNumber(this.number);
      }
      if ((this.type != null) && (this.type.trim().length() > 0))
      {
        int i = Integer.parseInt(this.type);
        this.searchAccount.setType(i);
      }
      localExtTransferAccounts = ExternalTransferAdmin.getExtTransferAccounts(localSecureUser, this.searchAccount, this.company, this.fiId, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, paramHttpSession);
    }
    catch (Exception localException)
    {
      DebugLog.log("ERROR: Exception thrown when get external Transfer companies:");
      localException.printStackTrace();
    }
    if (this.error == 0) {
      paramHttpSession.setAttribute(this.accountsSessionName, localExtTransferAccounts);
    }
    return this.error == 0;
  }
  
  public void setAffiliateId(String paramString)
  {
    this.affiliateId = paramString;
  }
  
  public void setCustId(String paramString)
  {
    this.custId = paramString;
  }
  
  public void setNickname(String paramString)
  {
    this.nickname = paramString;
  }
  
  public void setNumber(String paramString)
  {
    this.number = paramString;
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setAccountsSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.accountsSessionName = "ExternalTransferAccounts";
    } else {
      this.accountsSessionName = paramString.trim();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.GetExtTransferAccounts
 * JD-Core Version:    0.7.0.1
 */