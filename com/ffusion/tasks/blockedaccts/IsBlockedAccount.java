package com.ffusion.tasks.blockedaccts;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BlockedAccts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class IsBlockedAccount
  extends BaseTask
  implements BlockedAccountTask
{
  protected BlockedAccount _account = new BlockedAccount();
  protected boolean _isBlockedAccount = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        this._isBlockedAccount = BlockedAccts.isBlockedAccount(localSecureUser, this._account, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public boolean getIsBlockedAccount()
  {
    return this._isBlockedAccount;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this._account.setRoutingNumber(paramString);
  }
  
  public void setAccountNumber(String paramString)
  {
    this._account.setAccountNumber(paramString);
  }
  
  public void setBankName(String paramString)
  {
    this._account.setBankName(paramString);
  }
  
  public void setUserDirectoryID(String paramString)
  {
    try
    {
      this._account.setUserDirectoryID(Integer.parseInt(paramString));
    }
    catch (Exception localException)
    {
      this._account.setUserDirectoryID(0);
    }
  }
  
  public String getRoutingNumber()
  {
    return this._account.getRoutingNumber();
  }
  
  public String getAccountNumber()
  {
    return this._account.getAccountNumber();
  }
  
  public String getBankName()
  {
    return this._account.getBankName();
  }
  
  public int getUserDirectoryID()
  {
    return this._account.getUserDirectoryID();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.blockedaccts.IsBlockedAccount
 * JD-Core Version:    0.7.0.1
 */