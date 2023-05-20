package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Authenticate;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.SignOn;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetUser
  extends SignOn
  implements UserTask
{
  private String oa = "UserService";
  protected boolean bAuthenticate;
  protected String mustReauthenticateURL;
  protected String lockedPasswordURL;
  protected String inactiveAccountURL;
  protected String bankID;
  protected String affiliateBankID;
  protected String accountStatus;
  protected String userType;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (validateInput(localHttpSession))
    {
      this.error = signOn(paramHttpServletRequest);
      switch (this.error)
      {
      case 37: 
        str = this.taskErrorURL;
        break;
      case 19002: 
        str = this.invalidPasswordURL;
        break;
      case 3007: 
        str = this.mustChangePasswordURL;
        break;
      case 3018: 
        str = this.successURL;
        break;
      case 3024: 
        str = this.mustChangePasswordURL;
        break;
      case 3023: 
        str = this.mustCompletePwdQAsURL;
        break;
      case 3017: 
        str = this.inactiveAccountURL;
        break;
      case 0: 
        str = this.successURL;
        break;
      case 3006: 
        str = this.mustReauthenticateURL;
        break;
      case 3008: 
        str = this.lockedPasswordURL;
        break;
      default: 
        str = this.serviceErrorURL;
        break;
      }
    }
    else
    {
      switch (this.error)
      {
      case 2: 
        str = this.invalidPasswordURL;
        break;
      default: 
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public int signOn(HttpServletRequest paramHttpServletRequest)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    int i = 0;
    if (this.bankID == null) {
      this.bankID = ((String)localHttpSession.getAttribute("BankId"));
    }
    if (this.bankID == null)
    {
      DebugLog.log(Level.WARNING, "***** com.ffusion.tasks.user.GetUser.BankID not set, assuming 1000 ****** ");
      this.bankID = "1000";
    }
    HashMap localHashMap = new HashMap(1);
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    SecureUser localSecureUser1 = (SecureUser)localHttpSession.getAttribute("SecureUser");
    SecureUser localSecureUser2 = null;
    if (localSecureUser1 != null)
    {
      localSecureUser2 = new SecureUser();
      localSecureUser2.set(localSecureUser1);
    }
    if (localSecureUser2 != null)
    {
      localSecureUser2.setUserName(this.userName);
      localSecureUser2.setPassword(this.password);
      localSecureUser2.setId(this.custId);
      localSecureUser2.setBankID(this.bankID);
      localSecureUser2.setLocale(localLocale);
    }
    else
    {
      try
      {
        localSecureUser2 = Authenticate.authenticate(this.userName, this.password, this.bankID, localHashMap);
        localSecureUser2.setLocale(localLocale);
        localHttpSession.setAttribute("SecureUser", localSecureUser2);
      }
      catch (CSILException localCSILException1)
      {
        i = MapError.mapError(localCSILException1);
      }
    }
    User localUser = new User(localLocale);
    localUser.setUserName(this.userName);
    if ((i == 0) && (this.bAuthenticate == true))
    {
      localUser.setPassword(this.password);
      try
      {
        localHashMap.put("USER", localUser);
        localSecureUser2 = UserAdmin.authenticate(localSecureUser2, localHashMap);
        localSecureUser2.setLocale(localLocale);
      }
      catch (CSILException localCSILException2)
      {
        i = MapError.mapError(localCSILException2);
      }
      localHttpSession.setAttribute("SecureUser", localSecureUser2);
    }
    if (i == 0)
    {
      try
      {
        localUser = UserAdmin.getUser(localSecureUser2, localUser, localHashMap);
      }
      catch (CSILException localCSILException3)
      {
        i = MapError.mapError(localCSILException3);
      }
      if (((this.accountStatus == null) || (!this.accountStatus.equals(localUser.getAccountStatus()))) && (localUser.getAccountStatus() != null) && (localUser.getAccountStatus().equals("2"))) {
        i = 100;
      }
      if (this.userType != null)
      {
        localObject = (String)localUser.get("BUSINESS_ID");
        if ((localObject != null) && (((String)localObject).length() == 0)) {
          localObject = null;
        }
        if ((this.userType.equalsIgnoreCase("consumer")) && (localObject != null)) {
          i = 3005;
        }
        if ((this.userType.equalsIgnoreCase("business")) && (localObject == null)) {
          i = 3005;
        }
      }
    }
    if (i == 0) {
      localHttpSession.setAttribute("User", localUser);
    }
    Object localObject = (Integer)localSecureUser2.get("AUTHENTICATE");
    if (localObject != null)
    {
      if (localSecureUser2.getAgent() == null) {
        i = ((Integer)localObject).intValue();
      }
      localSecureUser2.remove("AUTHENTICATE");
    }
    return i;
  }
  
  public void setAuthenticate(String paramString)
  {
    this.bAuthenticate = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setUserType(String paramString)
  {
    this.userType = paramString;
  }
  
  public void setAccountStatus(String paramString)
  {
    this.accountStatus = paramString;
  }
  
  public void setMustReauthenticateURL(String paramString)
  {
    this.mustReauthenticateURL = paramString;
  }
  
  public void setLockedPasswordURL(String paramString)
  {
    this.lockedPasswordURL = paramString;
  }
  
  public void setInactiveAccountURL(String paramString)
  {
    this.inactiveAccountURL = paramString;
  }
  
  public void setBankID(String paramString)
  {
    this.bankID = paramString;
  }
  
  public String getBankID()
  {
    return this.bankID;
  }
  
  public void setAffiliateBankID(String paramString)
  {
    this.affiliateBankID = paramString;
  }
  
  public String getAffiliateBankID()
  {
    return this.affiliateBankID;
  }
  
  public void setUserServiceName(String paramString)
  {
    this.oa = paramString;
  }
  
  protected boolean validateInput(HttpSession paramHttpSession)
  {
    this.error = 0;
    if (this.validate != null) {
      if ((this.validate.indexOf(USERNAME) != -1) && ((this.userName == null) || (this.userName.trim().length() == 0))) {
        this.error = 1;
      } else if ((this.bAuthenticate) && (this.validate.indexOf(PASSWORD) != -1) && ((this.password == null) || (this.password.length() < this.minPasswordLength) || (this.userName.length() > 50) || (this.password.trim().length() == 0))) {
        this.error = 2;
      }
    }
    this.validate = null;
    return this.error == 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetUser
 * JD-Core Version:    0.7.0.1
 */