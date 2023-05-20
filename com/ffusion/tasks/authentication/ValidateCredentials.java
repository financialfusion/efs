package com.ffusion.tasks.authentication;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.authentication.Credentials;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Authentication;
import com.ffusion.efs.adapters.profile.SignonSettings;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ValidateCredentials
  extends BaseTask
  implements Task
{
  private String aKK = "Credentials";
  private String aKJ = null;
  private String aKI = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Credentials localCredentials = (Credentials)localHttpSession.getAttribute(this.aKK);
    if (localCredentials == null)
    {
      this.error = 90000;
      return this.taskErrorURL;
    }
    if (this.aKJ == null)
    {
      this.error = 90001;
      return this.taskErrorURL;
    }
    if (this.aKI == null)
    {
      this.error = 90002;
      return this.taskErrorURL;
    }
    try
    {
      Authentication.validateCredentials(localSecureUser, localCredentials, new HashMap());
    }
    catch (CSILException localCSILException)
    {
      if (localCSILException.getCode() == 90007) {
        return this.aKI;
      }
      localHttpSession.removeAttribute(this.aKK);
      Integer localInteger = (Integer)localHttpSession.getAttribute("FAILED_CREDENTIAL_REQUESTS");
      if (localInteger == null)
      {
        localInteger = new Integer(1);
      }
      else
      {
        localInteger = new Integer(localInteger.intValue() + 1);
        if (localInteger.intValue() == SignonSettings.getMaxInvalidCredentialsBeforeLogout()) {
          return this.aKI;
        }
      }
      localHttpSession.setAttribute("FAILED_CREDENTIAL_REQUESTS", localInteger);
      return this.aKJ;
    }
    localHttpSession.removeAttribute(this.aKK);
    localHttpSession.setAttribute("FAILED_CREDENTIAL_REQUESTS", new Integer(0));
    return this.successURL;
  }
  
  public void setCredentialsCollection(String paramString)
  {
    this.aKK = paramString;
  }
  
  public String getCredentialsCollection()
  {
    return this.aKK;
  }
  
  public void setFailureURL(String paramString)
  {
    this.aKJ = paramString;
  }
  
  public String getFailureURL()
  {
    return this.aKJ;
  }
  
  public void setLogoutURL(String paramString)
  {
    this.aKI = paramString;
  }
  
  public String getLogoutURL()
  {
    return this.aKI;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.authentication.ValidateCredentials
 * JD-Core Version:    0.7.0.1
 */