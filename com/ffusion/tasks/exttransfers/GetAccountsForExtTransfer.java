package com.ffusion.tasks.exttransfers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.exttransfers.ExtTransferAccounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ExternalTransferAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountsForExtTransfer
  implements Task
{
  protected String accountsSessionName = "Accounts";
  protected String extAccountsSessionName = "ExternalTransferAccounts";
  protected String custId;
  protected int directoryId;
  protected boolean isConsumer = false;
  protected String _useDirectoryId = "false";
  protected String nextURL = null;
  protected int error = 0;
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected Locale locale;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    try
    {
      this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      ExtTransferAccounts localExtTransferAccounts = (ExtTransferAccounts)localHttpSession.getAttribute(this.extAccountsSessionName);
      HashMap localHashMap = new HashMap();
      this.directoryId = Integer.parseInt(this.custId);
      localHashMap.put("ExternalTransferAccounts", localExtTransferAccounts);
      localHashMap.put("isConsumer", "" + this.isConsumer);
      localHashMap.put("useDirectoryId", this._useDirectoryId);
      Accounts localAccounts = ExternalTransferAdmin.getAccountsForExtTransfer(localSecureUser, this.directoryId, localHashMap);
      localHttpSession.setAttribute(this.accountsSessionName, localAccounts);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException, localHttpSession);
      str = this.serviceErrorURL;
    }
    catch (Exception localException)
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setCustId(String paramString)
  {
    this.custId = paramString;
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
  
  public void setIsConsumer(String paramString)
  {
    this.isConsumer = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAccountsSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.accountsSessionName = "Accounts";
    } else {
      this.accountsSessionName = paramString.trim();
    }
  }
  
  public void setExtAccountsSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.extAccountsSessionName = "ExternalTransferAccounts";
    } else {
      this.extAccountsSessionName = paramString.trim();
    }
  }
  
  public void setUseDirectoryId(String paramString)
  {
    this._useDirectoryId = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.exttransfers.GetAccountsForExtTransfer
 * JD-Core Version:    0.7.0.1
 */