package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.XMLTag;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSessionSettings
  extends BaseTask
  implements UserTask
{
  protected final String ADDITIONALSETTINGSNAMESPACE = "SESSION_SETTINGS";
  protected final String TAG = "USER_LOGOUT_SETTINGS";
  protected final String DISPLAY_SESSION_SUMMARY_REPORT = "DISPLAY_SESSION_SUMMARY_REPORT";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    String str2 = null;
    HashMap localHashMap = null;
    try
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      str2 = UserAdmin.getAdditionalData(localSecureUser, "SESSION_SETTINGS", localHashMap);
      if ((str2 == null) || (str2.length() == 0))
      {
        localHttpSession.setAttribute("DISPLAY_SESSION_SUMMARY_REPORT", "NO");
      }
      else
      {
        XMLTag localXMLTag = new XMLTag();
        localXMLTag.build(str2);
        if (localXMLTag.getTagName().equals("SESSION_SETTINGS")) {
          localXMLTag = localXMLTag.getContainedTag("USER_LOGOUT_SETTINGS");
        }
        String str3 = localXMLTag.getContainedTag("DISPLAY_SESSION_SUMMARY_REPORT").getTagContent();
        localHttpSession.setAttribute("DISPLAY_SESSION_SUMMARY_REPORT", str3);
      }
    }
    catch (CSILException localCSILException)
    {
      localHttpSession.setAttribute("DISPLAY_SESSION_SUMMARY_REPORT", "NO");
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("GetUserLogoutSettings Task Exception: ", localThrowable);
      this.error = 3002;
      str1 = this.taskErrorURL;
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetSessionSettings
 * JD-Core Version:    0.7.0.1
 */