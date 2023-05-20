package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.user.User;
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

public class ChangeUserName
  extends BaseTask
  implements UserTask
{
  private String vX = "UserService";
  protected static String OLDUSERNAME = "OLDUSERNAME";
  protected static String NEWUSERNAME = "NEWUSERNAME";
  protected static String CONFIRMUSERNAME = "CONFIRMUSERNAME";
  protected int minUserNameLength;
  protected String oldUserName;
  protected String newUserName;
  protected String confirmUserName;
  protected String validate;
  protected String nextURL = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.nextURL = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    User localUser = (User)localHttpSession.getAttribute("User");
    if (validateInput(localHttpSession, localUser))
    {
      if (duplicateUserName(localHttpSession))
      {
        this.error = 3512;
        return this.taskErrorURL;
      }
      if (this.nextURL.equals(this.serviceErrorURL)) {
        return this.serviceErrorURL;
      }
      String str = localUser.getUserName();
      localUser.setUserName(this.newUserName);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        localUser = UserAdmin.modifyUser(localSecureUser, localUser, null);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
        this.nextURL = this.serviceErrorURL;
      }
      if (this.error != 0)
      {
        localUser.setUserName(str);
        this.nextURL = this.serviceErrorURL;
      }
      localHttpSession.setAttribute("User", localUser);
      if (this.error == 0)
      {
        Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
        Histories localHistories = new Histories(localLocale);
        localHistories.add(AddUser.buildHistory(localHttpSession, localUser, "USERNAME", str, this.newUserName, ""));
        try
        {
          HashMap localHashMap = null;
          UserAdmin.addHistory(localSecureUser, localHistories, localHashMap);
        }
        catch (CSILException localCSILException2)
        {
          this.error = MapError.mapError(localCSILException2);
        }
      }
    }
    else
    {
      this.nextURL = this.taskErrorURL;
    }
    return this.nextURL;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession, User paramUser)
  {
    this.error = 0;
    if (this.validate != null) {
      if ((this.validate.indexOf(OLDUSERNAME) != -1) && ((this.oldUserName == null) || (this.oldUserName.length() == 0) || (!this.oldUserName.equalsIgnoreCase(paramUser.getUserName())))) {
        this.error = 3509;
      } else if ((this.validate.indexOf(NEWUSERNAME) != -1) && ((this.newUserName == null) || (this.newUserName.length() == 0) || (this.newUserName.length() < this.minUserNameLength))) {
        this.error = 3510;
      } else if ((this.validate.indexOf(CONFIRMUSERNAME) != -1) && ((this.confirmUserName == null) || (!this.confirmUserName.equals(this.newUserName)))) {
        this.error = 3511;
      }
    }
    this.validate = null;
    return this.error == 0;
  }
  
  protected boolean duplicateUserName(HttpSession paramHttpSession)
  {
    boolean bool = true;
    HashMap localHashMap = new HashMap();
    User localUser = new User((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    localUser.setUserName(this.newUserName);
    localHashMap.put("USER", localUser);
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      localHashMap.put("BUSINESS_CUST_ID", localSecureUser.getBusinessCustId());
      bool = UserAdmin.userExists(localSecureUser, this.newUserName, localSecureUser.getBankID(), localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.nextURL = this.serviceErrorURL;
    }
    return bool;
  }
  
  public void setMinimumUserNameLength(String paramString)
  {
    try
    {
      this.minUserNameLength = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.minUserNameLength = 0;
    }
  }
  
  public void setNewUserName(String paramString)
  {
    this.newUserName = paramString;
  }
  
  public void setConfirmUserName(String paramString)
  {
    this.confirmUserName = paramString;
  }
  
  public void setOldUserName(String paramString)
  {
    this.oldUserName = paramString;
  }
  
  public String getUserName()
  {
    return this.newUserName;
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setUserServiceName(String paramString)
  {
    this.vX = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.ChangeUserName
 * JD-Core Version:    0.7.0.1
 */