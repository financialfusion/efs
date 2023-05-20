package com.ffusion.tasks.accountgroups;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accountgroups.BusinessAccountGroup;
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

public class GetAccountGroup
  extends BaseTask
  implements AccountGroupsTask
{
  private int aMU = -1;
  private String aMT = "AccountGroup";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    BusinessAccountGroup localBusinessAccountGroup = null;
    if (this.aMU == -1)
    {
      this.error = 70001;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        int i = this.aMU;
        localBusinessAccountGroup = AccountGroup.getAccountGroup(localSecureUser, i, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.aMT, localBusinessAccountGroup);
      str = this.successURL;
    }
    return str;
  }
  
  public void setAccountId(String paramString)
  {
    try
    {
      this.aMU = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aMU = -1;
    }
  }
  
  public void setAccountGroup(String paramString)
  {
    this.aMT = paramString;
  }
  
  public String getAccountId()
  {
    return String.valueOf(this.aMU);
  }
  
  public String getAccountGroup()
  {
    return this.aMT;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accountgroups.GetAccountGroup
 * JD-Core Version:    0.7.0.1
 */