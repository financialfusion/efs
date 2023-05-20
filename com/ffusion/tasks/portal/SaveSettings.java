package com.ffusion.tasks.portal;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ToXml;
import com.ffusion.beans.portal.PortalItems;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.util.HashMap;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SaveSettings
  extends BaseTask
  implements Task
{
  private String mg = "StockSettings,StockIndexSettings,ForecastSettings,NewsSettings,CalculatorsSettings,AdvisorsSettings";
  private static final String mf = "com.ffusion.util.logging.audit.task";
  private static final String mh = "AuditMessage_SaveSettings_1";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PortalItems localPortalItems = (PortalItems)localHttpSession.getAttribute("PortalItems");
    if (localPortalItems == null)
    {
      this.error = 9009;
      str = this.taskErrorURL;
    }
    else
    {
      StringBuffer localStringBuffer = new StringBuffer();
      localStringBuffer.append(localPortalItems.toXML());
      StringTokenizer localStringTokenizer = new StringTokenizer(this.mg, ",");
      Object localObject1;
      Object localObject2;
      while (localStringTokenizer.hasMoreTokens())
      {
        localObject1 = localStringTokenizer.nextToken();
        localObject2 = (ToXml)localHttpSession.getAttribute((String)localObject1);
        if (localObject2 != null) {
          localStringBuffer.append(((ToXml)localObject2).toXML());
        }
      }
      try
      {
        localObject1 = new HashMap(1);
        localObject2 = (SecureUser)localHttpSession.getAttribute("SecureUser");
        UserAdmin.addAdditionalData((SecureUser)localObject2, "PORTAL_SETTINGS", localStringBuffer.toString(), (HashMap)localObject1);
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_SaveSettings_1", null);
        Initialize.audit((SecureUser)localObject2, localLocalizableString, TrackingIDGenerator.GetNextID(), 3215);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setSaveItems(String paramString)
  {
    this.mg = paramString;
  }
  
  public String getSaveItems()
  {
    return this.mg;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.SaveSettings
 * JD-Core Version:    0.7.0.1
 */