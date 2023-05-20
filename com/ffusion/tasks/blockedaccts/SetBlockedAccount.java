package com.ffusion.tasks.blockedaccts;

import com.ffusion.beans.SecureUser;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResult;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResults;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetBlockedAccount
  extends BaseTask
  implements BlockedAccountTask
{
  protected int _index = -1;
  protected String _routingNumber = null;
  protected String _accountNumber = null;
  protected String _userName = null;
  protected String _blockedAccountName = "BlockedAccount";
  protected String _blockedAccountsName = "BlockedAccounts";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    BlockedAccountSearchResult localBlockedAccountSearchResult = null;
    BlockedAccountSearchResults localBlockedAccountSearchResults = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localBlockedAccountSearchResults = (BlockedAccountSearchResults)localHttpSession.getAttribute(this._blockedAccountsName);
    if (localSecureUser == null)
    {
      this.error = 38;
    }
    else if (localBlockedAccountSearchResults == null)
    {
      this.error = 100006;
    }
    else
    {
      if (this._index >= 0) {
        try
        {
          localBlockedAccountSearchResult = (BlockedAccountSearchResult)localBlockedAccountSearchResults.get(this._index);
        }
        catch (Exception localException)
        {
          localBlockedAccountSearchResult = null;
        }
      } else if ((this._routingNumber != null) && (this._accountNumber != null)) {
        localBlockedAccountSearchResult = localBlockedAccountSearchResults.getByAccountNumberAndRoutingNumberAndUserName(this._accountNumber, this._routingNumber, this._userName);
      } else {
        this.error = 100009;
      }
      if (localBlockedAccountSearchResult == null)
      {
        if (this.error == 0) {
          this.error = 100007;
        }
      }
      else {
        localHttpSession.setAttribute(this._blockedAccountName, localBlockedAccountSearchResult);
      }
      aa();
    }
    return str;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this._routingNumber = paramString;
  }
  
  public void setAccountNumber(String paramString)
  {
    this._accountNumber = paramString;
  }
  
  public void setUserName(String paramString)
  {
    this._userName = paramString;
  }
  
  public void setIndex(String paramString)
  {
    try
    {
      this._index = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this._index = -1;
    }
  }
  
  public void setBlockedAccountsName(String paramString)
  {
    this._blockedAccountsName = paramString;
  }
  
  public void setBlockedAccountName(String paramString)
  {
    this._blockedAccountName = paramString;
  }
  
  private void aa()
  {
    this._index = -1;
    this._routingNumber = null;
    this._accountNumber = null;
    this._userName = null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.blockedaccts.SetBlockedAccount
 * JD-Core Version:    0.7.0.1
 */