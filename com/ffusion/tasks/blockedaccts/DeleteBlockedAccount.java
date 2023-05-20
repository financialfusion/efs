package com.ffusion.tasks.blockedaccts;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BlockedAccts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.blockedaccts.BlockedAccount;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResult;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteBlockedAccount
  extends BaseTask
  implements BlockedAccountTask
{
  protected BlockedAccount _account = new BlockedAccount();
  protected boolean _initFlag = false;
  protected boolean _processFlag = false;
  protected String _userName = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    this.error = 0;
    if (this._initFlag) {
      str = initProcess(paramHttpServletRequest.getSession());
    } else if (this._processFlag) {
      str = processDeleteBlockedAccount(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    }
    return str;
  }
  
  protected String initProcess(HttpSession paramHttpSession)
  {
    this._initFlag = false;
    BlockedAccountSearchResult localBlockedAccountSearchResult = (BlockedAccountSearchResult)paramHttpSession.getAttribute("BlockedAccount");
    String str = this.successURL;
    if (localBlockedAccountSearchResult == null)
    {
      this.error = 100005;
      str = this.taskErrorURL;
    }
    else
    {
      set(localBlockedAccountSearchResult);
      paramHttpSession.removeAttribute("BlockedAccount");
    }
    return str;
  }
  
  protected String processDeleteBlockedAccount(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
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
        if ((this._userName != null) && (this._userName.trim().length() != 0)) {
          localHashMap.put("UserName", this._userName.trim());
        }
        BlockedAccts.deleteBlockedAccount(localSecureUser, this._account, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public String getUserName()
  {
    return this._userName;
  }
  
  public void setProcess(String paramString)
  {
    this._processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setInitialize(String paramString)
  {
    this._initFlag = Boolean.valueOf(paramString).booleanValue();
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
  
  protected void set(BlockedAccountSearchResult paramBlockedAccountSearchResult)
  {
    this._account.setRoutingNumber(paramBlockedAccountSearchResult.getRoutingNumber());
    this._account.setAccountNumber(paramBlockedAccountSearchResult.getAccountNumber());
    this._account.setBankName(paramBlockedAccountSearchResult.getBankName());
    this._account.setUserDirectoryID(paramBlockedAccountSearchResult.getUserDirectoryID());
    this._userName = paramBlockedAccountSearchResult.getUserName();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.blockedaccts.DeleteBlockedAccount
 * JD-Core Version:    0.7.0.1
 */