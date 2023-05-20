package com.ffusion.tasks.user;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.XMLTag;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetSessionSettings
  extends BaseTask
  implements UserTask
{
  public static final String ADDITIONALSETTINGSNAMESPACE = "SESSION_SETTINGS";
  public static final String TAG = "USER_LOGOUT_SETTINGS";
  public static final String DISPLAY_SESSION_SUMMARY_REPORT = "DISPLAY_SESSION_SUMMARY_REPORT";
  private static final String v8 = "com.ffusion.util.logging.audit.task";
  private static final String v9 = "AuditMessage_SetSessionSettings_1";
  private static final String wa = "AuditMessage_SetSessionSettings_2";
  protected int MAX_DISPLAY_SESSION_SUMMARY_LENGTH = 10;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    if (!validateFieldLength(localHttpSession)) {
      return this.taskErrorURL;
    }
    String str2 = null;
    HashMap localHashMap = null;
    try
    {
      XMLTag localXMLTag1 = new XMLTag();
      localXMLTag1.setTagName("USER_LOGOUT_SETTINGS");
      XMLTag localXMLTag2 = new XMLTag();
      localXMLTag2.setTagName("DISPLAY_SESSION_SUMMARY_REPORT");
      String str3 = (String)localHttpSession.getAttribute("DISPLAY_SESSION_SUMMARY_REPORT");
      localXMLTag2.setTagContent(str3);
      localXMLTag1.setContainedTag(localXMLTag2);
      str2 = XMLTag.toXML(localXMLTag1, true);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      UserAdmin.addAdditionalData(localSecureUser, "SESSION_SETTINGS", str2, localHashMap);
      LocalizableString localLocalizableString;
      if (str3.equalsIgnoreCase("NO"))
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_SetSessionSettings_1", null);
        Initialize.audit(localSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 3212);
      }
      else
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_SetSessionSettings_2", null);
        Initialize.audit(localSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 3212);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  protected boolean validateFieldLength(HttpSession paramHttpSession)
  {
    String str = (String)paramHttpSession.getAttribute("DISPLAY_SESSION_SUMMARY_REPORT");
    if ((str != null) && (str.length() > this.MAX_DISPLAY_SESSION_SUMMARY_LENGTH)) {
      return setError(192);
    }
    return true;
  }
  
  public boolean setError(int paramInt)
  {
    this.error = paramInt;
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.SetSessionSettings
 * JD-Core Version:    0.7.0.1
 */