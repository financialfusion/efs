package com.ffusion.tasks.blockedaccts;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BlockedAccts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchCriteria;
import com.ffusion.util.beans.blockedaccts.BlockedAccountSearchResults;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBlockedAccounts
  extends BaseTask
  implements BlockedAccountTask
{
  protected BlockedAccountSearchCriteria _searchCrit = new BlockedAccountSearchCriteria();
  protected boolean _searchRan = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HashMap localHashMap = new HashMap(1);
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
        BlockedAccountSearchResults localBlockedAccountSearchResults = BlockedAccts.getBlockedAccounts(localSecureUser, this._searchCrit, localHashMap);
        this._searchRan = true;
        localHttpSession.setAttribute("BlockedAccounts", localBlockedAccountSearchResults);
      }
      catch (CSILException localCSILException)
      {
        this._searchRan = false;
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setUserName(String paramString)
  {
    this._searchCrit.setUserName(paramString);
  }
  
  public void setRoutingNumber(String paramString)
  {
    this._searchCrit.setRoutingNumber(paramString);
  }
  
  public void setAccountNumber(String paramString)
  {
    this._searchCrit.setAccountNumber(paramString);
  }
  
  public void setBankName(String paramString)
  {
    this._searchCrit.setBankName(paramString);
  }
  
  public void setFirstName(String paramString)
  {
    this._searchCrit.setFirstName(paramString);
  }
  
  public void setLastName(String paramString)
  {
    this._searchCrit.setLastName(paramString);
  }
  
  public String getUserName()
  {
    return this._searchCrit.getUserName();
  }
  
  public String getRoutingNumber()
  {
    return this._searchCrit.getRoutingNumber();
  }
  
  public String getAccountNumber()
  {
    return this._searchCrit.getAccountNumber();
  }
  
  public String getBankName()
  {
    return this._searchCrit.getBankName();
  }
  
  public String getFirstName()
  {
    return this._searchCrit.getFirstName();
  }
  
  public String getLastName()
  {
    return this._searchCrit.getLastName();
  }
  
  public boolean getSearchRan()
  {
    return this._searchRan;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.blockedaccts.GetBlockedAccounts
 * JD-Core Version:    0.7.0.1
 */