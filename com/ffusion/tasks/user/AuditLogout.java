package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuditLogout
  extends BaseTask
{
  private boolean aLh = false;
  private boolean aLl = false;
  private static final String aLg = "com.ffusion.util.logging.audit.task";
  private static final String aLi = "AuditMessage_AuditLogout_1.1";
  private static final String aLf = "AuditMessage_AuditLogout_1.4";
  private static final String aLj = "AuditMessage_AuditLogout_2.1";
  private static final String aLk = "AuditMessage_AuditLogout_2.4";
  private static final String aLe = "AuditMessage_AuditLogout_2.5";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser != null)
    {
      if (localSecureUser.getUserType() == 0) {
        return this.successURL;
      }
      Object[] arrayOfObject = null;
      LocalizableString localLocalizableString = null;
      arrayOfObject = new Object[2];
      arrayOfObject[0] = localSecureUser.getUserName();
      arrayOfObject[1] = (localSecureUser.getAgent() == null ? null : localSecureUser.getAgent().getUserName());
      if (this.aLh)
      {
        if (localSecureUser.getAgent() == null) {
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AuditLogout_1.1", arrayOfObject);
        } else {
          localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AuditLogout_1.4", arrayOfObject);
        }
      }
      else if (this.aLl) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AuditLogout_2.5", arrayOfObject);
      } else if (localSecureUser.getAgent() == null) {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AuditLogout_2.1", arrayOfObject);
      } else {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AuditLogout_2.4", arrayOfObject);
      }
      String str = TrackingIDGenerator.GetNextID();
      Initialize.audit(localSecureUser, localLocalizableString, str, 3203);
    }
    return this.successURL;
  }
  
  public void setTimedOut(String paramString)
  {
    this.aLh = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setDeclinedTerms(String paramString)
  {
    this.aLl = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.AuditLogout
 * JD-Core Version:    0.7.0.1
 */