package com.ffusion.tasks.accountgroups;

import com.ffusion.beans.accountgroups.BusinessAccountGroup;
import com.ffusion.beans.accountgroups.BusinessAccountGroups;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetAccountGroupById
  extends BaseTask
  implements AccountGroupsTask
{
  private String aMS = "AccountGroups";
  private String aMQ = "AccountGroup";
  private int aMR;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    BusinessAccountGroups localBusinessAccountGroups = (BusinessAccountGroups)localHttpSession.getAttribute(this.aMS);
    if (this.aMR == -1)
    {
      this.error = 70001;
      str = this.taskErrorURL;
    }
    else if (localBusinessAccountGroups == null)
    {
      this.error = 70007;
      str = this.taskErrorURL;
    }
    else
    {
      BusinessAccountGroup localBusinessAccountGroup = null;
      localBusinessAccountGroup = localBusinessAccountGroups.getByID(this.aMR);
      if (localBusinessAccountGroup != null)
      {
        localHttpSession.setAttribute(this.aMQ, localBusinessAccountGroup);
      }
      else
      {
        this.error = 70008;
        str = this.taskErrorURL;
      }
    }
    if (this.error == 0) {
      str = this.successURL;
    }
    return str;
  }
  
  public void setId(String paramString)
  {
    try
    {
      this.aMR = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.aMR = -1;
    }
  }
  
  public void setAccountGroupsName(String paramString)
  {
    this.aMS = paramString;
  }
  
  public void setAccountGroup(String paramString)
  {
    this.aMQ = paramString;
  }
  
  public String getId()
  {
    return String.valueOf(this.aMR);
  }
  
  public String getAccountGroupsName()
  {
    return this.aMS;
  }
  
  public String getAccountGroup()
  {
    return this.aMQ;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accountgroups.SetAccountGroupById
 * JD-Core Version:    0.7.0.1
 */