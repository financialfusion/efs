package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.exttransfers.ExtTransferAccount;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.beans.exttransfers.ExtTransferCompany;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ExternalTransferAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyExtTransferAccounts
  extends ExtTransferAccounts
  implements Task
{
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected boolean modify = true;
  protected String fiId = null;
  protected String affiliateId = null;
  protected ExtTransferAccount currentExtTransferAccount;
  protected ExtTransferAccount originalExtTransferAccount;
  protected ExtTransferAccounts originalExtTransferAccounts;
  protected ExtTransferCompany originalExtTransferCompany;
  protected ExtTransferCompany currentExtTransferCompany;
  protected String nextURL = null;
  protected String custID = null;
  protected String sourceAccts = "BankingAccounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    this.error = 0;
    if (this.initFlag) {
      str = initProcess(paramHttpServletRequest, localHttpSession);
    } else {
      str = modifyTransferAccounts(paramHttpServletRequest, localHttpSession);
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("ExternalTransferAccountsUpdated", "true");
    }
    return str;
  }
  
  protected String initProcess(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    this.originalExtTransferAccounts = ((ExtTransferAccounts)paramHttpSession.getAttribute("ExternalTransferAccounts"));
    if (this.originalExtTransferAccounts == null)
    {
      this.error = 4201;
      this.nextURL = this.taskErrorURL;
    }
    else
    {
      clear();
      for (int i = 0; i < this.originalExtTransferAccounts.size(); i++)
      {
        ExtTransferAccount localExtTransferAccount = (ExtTransferAccount)this.originalExtTransferAccounts.get(i);
        add(localExtTransferAccount.clone());
      }
    }
    return this.successURL;
  }
  
  protected String modifyTransferAccounts(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    int i = 1;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        ExtTransferCompany localExtTransferCompany = new ExtTransferCompany(this.locale);
        if (localExtTransferCompany.getCustID() == null) {
          localExtTransferCompany.setCustID(this.custID);
        }
        str = processModifyExtTransferAccounts(localExtTransferCompany, paramHttpSession);
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String processModifyExtTransferAccounts(ExtTransferCompany paramExtTransferCompany, HttpSession paramHttpSession)
  {
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this.sourceAccts);
    this.error = 0;
    for (int i = 0; i < this.originalExtTransferAccounts.size(); i++)
    {
      this.originalExtTransferAccount = ((ExtTransferAccount)this.originalExtTransferAccounts.get(i));
      if (this.originalExtTransferAccount != null)
      {
        this.currentExtTransferAccount = getByBpwID(this.originalExtTransferAccount.getBpwID());
        if ((this.currentExtTransferAccount != null) && ((this.currentExtTransferAccount.getStatusValue() != this.originalExtTransferAccount.getStatusValue()) || (!this.currentExtTransferAccount.getNickname().equals(this.originalExtTransferAccount.getNickname())))) {
          try
          {
            localHashMap.put("OriginalAccount", this.originalExtTransferAccount);
            ExternalTransferAdmin.modifyExtTransferAccount(localSecureUser, this.currentExtTransferAccount, this.originalExtTransferAccount, paramExtTransferCompany, localHashMap);
            if (localAccounts != null)
            {
              Account localAccount = localAccounts.getByNumberAndRoutingNum(this.currentExtTransferAccount.getNumber(), this.currentExtTransferAccount.getRoutingNumber());
              if (localAccount != null)
              {
                localAccount.put("ExternalTransferACCOUNT", this.currentExtTransferAccount);
                localAccount.setNickName(this.currentExtTransferAccount.getNickname());
              }
            }
          }
          catch (CSILException localCSILException)
          {
            this.error = MapError.mapError(localCSILException, paramHttpSession);
          }
          catch (Exception localException)
          {
            DebugLog.log("ERROR: Exception thrown when adding transfer:");
            localException.printStackTrace();
          }
        }
      }
    }
    if (this.error == 0) {
      this.nextURL = this.successURL;
    } else {
      this.nextURL = this.serviceErrorURL;
    }
    return this.nextURL;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if (this.validate != null) {
      this.validate = null;
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
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
  
  public void setCurrentExtTransferCompany(ExtTransferCompany paramExtTransferCompany)
  {
    this.currentExtTransferCompany = paramExtTransferCompany;
  }
  
  public void setCurrentExtTransferAccount(ExtTransferAccount paramExtTransferAccount)
  {
    this.currentExtTransferAccount = paramExtTransferAccount;
  }
  
  public void setFiId(String paramString)
  {
    this.fiId = paramString;
  }
  
  public void setCustID(String paramString)
  {
    this.custID = paramString;
  }
  
  public void setSourceAccounts(String paramString)
  {
    this.sourceAccts = paramString;
  }
  
  public void setAffiliateId(String paramString)
  {
    this.affiliateId = paramString;
  }
  
  public boolean setError(int paramInt)
  {
    this.error = paramInt;
    return false;
  }
  
  public void setCurrentAccountByBPWID(String paramString)
  {
    this.currentExtTransferAccount = getByBpwID(paramString);
  }
  
  public void setActive(String paramString)
  {
    if (this.currentExtTransferAccount != null)
    {
      boolean bool = Boolean.valueOf(paramString).booleanValue();
      if (bool) {
        this.currentExtTransferAccount.setStatusValue(1);
      } else {
        this.currentExtTransferAccount.setStatusValue(2);
      }
    }
  }
  
  public String getActive()
  {
    if (this.currentExtTransferAccount != null)
    {
      if (1 == this.currentExtTransferAccount.getStatusValue()) {
        return "true";
      }
      return "false";
    }
    return "false";
  }
  
  public void setNickname(String paramString)
  {
    if (this.currentExtTransferAccount != null) {
      this.currentExtTransferAccount.setNickname(paramString);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.ModifyExtTransferAccounts
 * JD-Core Version:    0.7.0.1
 */