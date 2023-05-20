package com.ffusion.tasks.multiuser;

import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class MergeSecondaryUsers
  extends BaseTask
{
  protected Users secondaryUsers1 = null;
  protected Users secondaryUsers2 = null;
  protected String secondaryUsersSessionName1 = null;
  protected String secondaryUsersSessionName2 = null;
  protected boolean initialize = false;
  protected boolean process = false;
  protected boolean mergeOnly = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    if (this.initialize)
    {
      if (!initialize(localHttpSession)) {
        str = this.taskErrorURL;
      }
    }
    else if (validateInput())
    {
      if (this.process)
      {
        User localUser1 = null;
        User localUser2 = null;
        int i = 0;
        int j = this.secondaryUsers2.size();
        while (i < j)
        {
          localUser2 = (User)this.secondaryUsers2.get(i);
          localUser1 = this.secondaryUsers1.getByID(localUser2.getId());
          if (localUser1 == null)
          {
            localUser1 = this.secondaryUsers1.add();
            localUser1.set(localUser2);
          }
          else if (!this.mergeOnly)
          {
            localUser1.set(localUser2);
          }
          i++;
        }
        this.secondaryUsers1.setFilter(this.secondaryUsers1.getFilter());
        this.secondaryUsers2.setFilter(this.secondaryUsers2.getFilter());
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setSecondaryUsersSessionName1(String paramString)
  {
    this.secondaryUsersSessionName1 = paramString;
  }
  
  public void setSecondaryUsersSessionName2(String paramString)
  {
    this.secondaryUsersSessionName2 = paramString;
  }
  
  public void setInitialize(String paramString)
  {
    this.initialize = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.process = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setMergeOnly(String paramString)
  {
    this.mergeOnly = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected boolean initialize(HttpSession paramHttpSession)
  {
    boolean bool = true;
    if ((bool) && ((this.secondaryUsersSessionName1 == null) || (this.secondaryUsersSessionName1.length() == 0)))
    {
      this.error = 28025;
      bool = false;
    }
    if ((bool) && ((this.secondaryUsersSessionName2 == null) || (this.secondaryUsersSessionName2.length() == 0)))
    {
      this.error = 28026;
      bool = false;
    }
    if (bool)
    {
      this.secondaryUsers1 = ((Users)paramHttpSession.getAttribute(this.secondaryUsersSessionName1));
      this.secondaryUsers2 = ((Users)paramHttpSession.getAttribute(this.secondaryUsersSessionName2));
    }
    return bool;
  }
  
  protected boolean validateInput()
  {
    boolean bool = true;
    if ((bool) && (this.secondaryUsers1 == null))
    {
      this.error = 28027;
      bool = false;
    }
    if ((bool) && (this.secondaryUsers2 == null))
    {
      this.error = 28028;
      bool = false;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.multiuser.MergeSecondaryUsers
 * JD-Core Version:    0.7.0.1
 */