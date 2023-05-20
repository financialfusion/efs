package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.user.AddUser;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchUser
  extends AddUser
  implements BankEmployeeTask
{
  protected String successURL;
  protected String taskErrorURL;
  protected String serviceErrorURL;
  protected String searchCriteria = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      if (validateInput(localHttpSession))
      {
        Users localUsers = new Users((Locale)localHttpSession.getAttribute("java.util.Locale"));
        SecureUser localSecureUser;
        if (useFilteredList())
        {
          try
          {
            HashMap localHashMap1 = null;
            localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
            localUsers = UserAdmin.getFilteredUsers(localSecureUser, this, null, localHashMap1);
          }
          catch (CSILException localCSILException2)
          {
            this.error = MapError.mapError(localCSILException2);
            str = this.serviceErrorURL;
          }
          if ((this.error == 0) || (this.error == 12)) {
            str = this.successURL;
          }
          localHttpSession.setAttribute("Users", localUsers);
        }
        else
        {
          try
          {
            HashMap localHashMap2 = null;
            localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
            localUsers = UserAdmin.getUserList(localSecureUser, this, localHashMap2);
          }
          catch (CSILException localCSILException3)
          {
            this.error = MapError.mapError(localCSILException3);
            str = this.serviceErrorURL;
          }
          if ((this.error == 0) || (this.error == 12))
          {
            str = this.successURL;
            localHttpSession.setAttribute("Users", localUsers);
          }
        }
      }
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean useFilteredList()
  {
    boolean bool = false;
    if ((this.searchCriteria.indexOf("USERNAME") != -1) && (this.userName.length() > 0)) {
      bool = true;
    }
    if ((this.searchCriteria.indexOf("FIRST") != -1) && (getFirstName().length() > 0)) {
      bool = true;
    }
    if ((this.searchCriteria.indexOf("LAST") != -1) && (getLastName().length() > 0)) {
      bool = true;
    }
    if ((this.searchCriteria.indexOf("CUSTID") != -1) && (getCustId().length() > 0)) {
      bool = true;
    }
    if ((this.searchCriteria.indexOf("STREET") != -1) && (getStreet().length() > 0)) {
      bool = true;
    }
    return bool;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setSearchCriteria(String paramString)
  {
    this.searchCriteria = paramString;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.SearchUser
 * JD-Core Version:    0.7.0.1
 */