package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.beans.user.Users;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetFilteredConsumers
  extends BaseTask
  implements UserTask
{
  private String vN = "Consumers";
  protected String dataNotFoundURL;
  String vR;
  String vP;
  String vO;
  String vQ;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    Users localUsers = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    User localUser = null;
    localUser = new User((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localUser.setBankId(localSecureUser.getBankID());
    localUser.setFirstName(this.vP);
    localUser.setLastName(this.vO);
    localUser.setUserName(this.vQ);
    localUser.set("NO_BUSINESS", "NO_BUSINESS");
    if ((this.vR != null) && (this.vR.length() > 0)) {
      localUser.setAffiliateBankID(this.vR);
    }
    try
    {
      localUsers = UserAdmin.getUsers(localSecureUser, localUser, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute(this.vN, localUsers);
      str = this.successURL;
    }
    else if (this.error == 3009)
    {
      if ((this.dataNotFoundURL != null) && (this.dataNotFoundURL.length() > 0)) {
        str = this.dataNotFoundURL;
      } else {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setUsersSessionName(String paramString)
  {
    this.vN = paramString;
  }
  
  public void setDataNotFoundURL(String paramString)
  {
    this.dataNotFoundURL = paramString;
  }
  
  public void setAffiliateBankId(String paramString)
  {
    this.vR = paramString;
  }
  
  public void setFirstName(String paramString)
  {
    this.vP = paramString;
  }
  
  public void setLastName(String paramString)
  {
    this.vO = paramString;
  }
  
  public void setUserName(String paramString)
  {
    this.vQ = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetFilteredConsumers
 * JD-Core Version:    0.7.0.1
 */