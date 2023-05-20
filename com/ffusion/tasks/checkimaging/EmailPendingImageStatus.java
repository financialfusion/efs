package com.ffusion.tasks.checkimaging;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CheckImaging;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EmailPendingImageStatus
  extends BaseTask
  implements ImageTask
{
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected String serviceErrorURL = "SE";
  protected String nextURL;
  protected int error;
  protected String toEmail;
  protected String emailNotificationFlag;
  protected Locale locale;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = null;
    HashMap localHashMap = null;
    ImageResults localImageResults = null;
    User localUser = null;
    SecureUser localSecureUser = null;
    boolean bool = false;
    this.nextURL = this.taskErrorURL;
    this.error = 0;
    localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    localImageResults = (ImageResults)localHttpSession.getAttribute("EmailImageResults");
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if ((localImageResults == null) || (localImageResults.size() < 1)) {
      return this.successURL;
    }
    if ((this.toEmail == null) || (this.toEmail.equals("")))
    {
      localUser = (User)localHttpSession.getAttribute("User");
      if (localUser == null) {
        localUser = (User)localHttpSession.getAttribute("AdminUser");
      }
      if (localUser != null) {
        this.toEmail = localUser.getEmail();
      }
    }
    if ((this.emailNotificationFlag == null) || (this.emailNotificationFlag.equals("")))
    {
      if (localUser != null) {
        this.emailNotificationFlag = localUser.getHashEntry("EMAIL_NOTIFICATION");
      }
      if ((this.emailNotificationFlag != null) && (this.emailNotificationFlag.equalsIgnoreCase("yes"))) {
        bool = true;
      }
    }
    else
    {
      bool = Boolean.valueOf(this.emailNotificationFlag).booleanValue();
    }
    localHashMap = new HashMap();
    localHashMap.put("TOADDRESS", this.toEmail);
    if ((bool) && (localImageResults != null) && (!localImageResults.isEmpty())) {
      try
      {
        localImageResults = CheckImaging.emailPendingImageStatus(localSecureUser, localImageResults, localHashMap);
        localHttpSession.removeAttribute("EmailImageResults");
        this.nextURL = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        this.nextURL = this.serviceErrorURL;
      }
    } else {
      this.nextURL = this.successURL;
    }
    return this.nextURL;
  }
  
  public void setToEmail(String paramString)
  {
    this.toEmail = paramString;
  }
  
  public String getToEmail()
  {
    return this.toEmail;
  }
  
  public void setEmailNotificationFlag(String paramString)
  {
    this.emailNotificationFlag = paramString;
  }
  
  public String getEmailNotificationFlag()
  {
    return this.emailNotificationFlag;
  }
  
  public void setLocale(Locale paramLocale)
  {
    if (paramLocale == null) {
      this.locale = Locale.getDefault();
    } else {
      this.locale = paramLocale;
    }
  }
  
  public Locale getLocale()
  {
    return this.locale;
  }
  
  public void setSuccessURL(String paramString)
  {
    this.successURL = paramString;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public void setServiceErrorURL(String paramString)
  {
    this.serviceErrorURL = paramString;
  }
  
  public boolean setError(int paramInt, HttpSession paramHttpSession)
  {
    this.error = paramInt;
    return false;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.checkimaging.EmailPendingImageStatus
 * JD-Core Version:    0.7.0.1
 */