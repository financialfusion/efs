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

public class GetUserAlertSettings
  extends BaseTask
  implements UserTask
{
  protected final String ADDITIONALSETTINGSNAMESPACE = "ALERT_SETTINGS";
  protected final String TAG = "UserAlertSettings";
  protected final String SECUREALERT = "SecureAlert";
  protected final String DELIVERYINFO1 = "DeliveryInfo1";
  protected final String DELIVERYINFO2 = "DeliveryInfo2";
  protected final String DELIVERYINFO1TO = "DeliveryInfo1To";
  protected final String DELIVERYINFO2TO = "DeliveryInfo2To";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    String str2 = null;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = null;
    try
    {
      str2 = UserAdmin.getAdditionalData(localSecureUser, "ALERT_SETTINGS", localHashMap);
      if ((str2 == null) || (str2.length() == 0))
      {
        localHttpSession.setAttribute("SecureAlert", "0");
        localHttpSession.setAttribute("DeliveryInfo1", "0");
        localHttpSession.setAttribute("DeliveryInfo2", "0");
        localHttpSession.setAttribute("DeliveryInfo1To", "");
        localHttpSession.setAttribute("DeliveryInfo2To", "");
      }
      else
      {
        XMLTag localXMLTag = new XMLTag();
        localXMLTag.build(str2);
        if (localXMLTag.getTagName().equals("ALERT_SETTINGS")) {
          localXMLTag = localXMLTag.getContainedTag("UserAlertSettings");
        }
        localHttpSession.setAttribute("SecureAlert", localXMLTag.getContainedTag("SecureAlert").getTagContent());
        localHttpSession.setAttribute("DeliveryInfo1", localXMLTag.getContainedTag("DeliveryInfo1").getTagContent());
        localHttpSession.setAttribute("DeliveryInfo2", localXMLTag.getContainedTag("DeliveryInfo2").getTagContent());
        localHttpSession.setAttribute("DeliveryInfo1To", localXMLTag.getContainedTag("DeliveryInfo1To").getTagContent());
        localHttpSession.setAttribute("DeliveryInfo2To", localXMLTag.getContainedTag("DeliveryInfo2To").getTagContent());
      }
    }
    catch (CSILException localCSILException)
    {
      if (this.error == 3009)
      {
        localHttpSession.setAttribute("SecureAlert", "0");
        localHttpSession.setAttribute("DeliveryInfo1", "0");
        localHttpSession.setAttribute("DeliveryInfo2", "0");
        localHttpSession.setAttribute("DeliveryInfo1To", "");
        localHttpSession.setAttribute("DeliveryInfo2To", "");
      }
      else
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("GetUserAlertSettings Task Exception: ", localThrowable);
      this.error = 3002;
      str1 = this.taskErrorURL;
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetUserAlertSettings
 * JD-Core Version:    0.7.0.1
 */