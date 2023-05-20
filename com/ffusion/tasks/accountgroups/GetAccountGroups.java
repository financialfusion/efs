package com.ffusion.tasks.accountgroups;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AccountGroup;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountGroups
  extends BaseTask
  implements AccountGroupsTask
{
  private int aM9 = -1;
  private String aM8 = "AccountGroups";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BusinessAccountGroups localBusinessAccountGroups = null;
    if (this.aM9 == -1)
    {
      this.error = 70000;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        int i = this.aM9;
        localBusinessAccountGroups = AccountGroup.getAccountGroups(localSecureUser, i, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.aM8, localBusinessAccountGroups);
      str = this.successURL;
    }
    return str;
  }
  
  public void setBusDirectoryId(String paramString)
  {
    try
    {
      this.aM9 = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aM9 = -1;
    }
  }
  
  public void setAccountGroupsName(String paramString)
  {
    this.aM8 = paramString;
  }
  
  public String getBusDirectoryId()
  {
    return String.valueOf(this.aM9);
  }
  
  public String getAccountGroupsName()
  {
    return this.aM8;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accountgroups.GetAccountGroups
 * JD-Core Version:    0.7.0.1
 */