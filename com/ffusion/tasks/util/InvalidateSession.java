package com.ffusion.tasks.util;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class InvalidateSession
  extends BaseTask
{
  private static final String Qt = "com.ffusion.util.logging.audit.task";
  private static final String Qw = "AuditMessage_InvalidateSession_1";
  private static final String Qv = "AuditMessage_InvalidateSession_2";
  private static final String Qu = "AuditMessage_InvalidateSession_3";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      if (localSecureUser != null)
      {
        String str = TrackingIDGenerator.GetNextID();
        StringBuffer localStringBuffer = new StringBuffer();
        if (localSecureUser.getUserType() != 0)
        {
          localObject1 = null;
          LocalizableString localLocalizableString = null;
          localObject1 = new Object[2];
          localObject1[0] = localSecureUser.getUserName();
          localObject1[1] = (localSecureUser.getAgent() == null ? null : localSecureUser.getAgent().getUserName());
          if (localSecureUser.getUserType() == 2) {
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_InvalidateSession_1", (Object[])localObject1);
          } else if (localSecureUser.getAgent() == null) {
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_InvalidateSession_2", (Object[])localObject1);
          } else {
            localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_InvalidateSession_3", (Object[])localObject1);
          }
          if (localSecureUser.getUserType() == 2) {
            Initialize.audit(localSecureUser, localLocalizableString, str, 1807);
          } else {
            Initialize.audit(localSecureUser, localLocalizableString, str, 3203);
          }
        }
        Object localObject1 = (UserLocale)localHttpSession.getAttribute("UserLocale");
        if ((this.successURL != null) && (this.successURL != "")) {
          this.successURL = (this.successURL + "?locale=" + ((UserLocale)localObject1).getLocale().toString());
        }
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("Unable to log out user", localThrowable);
    }
    finally
    {
      localHttpSession.invalidate();
      if ((this.successURL != null) && (this.successURL != "")) {
        paramHttpServletResponse.sendRedirect(paramHttpServletResponse.encodeRedirectURL(this.successURL));
      }
    }
    return "";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.util.InvalidateSession
 * JD-Core Version:    0.7.0.1
 */