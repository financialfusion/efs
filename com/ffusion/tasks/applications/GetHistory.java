package com.ffusion.tasks.applications;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.ApplicationHistories;
import com.ffusion.beans.applications.ApplicationHistory;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Applications;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetHistory
  extends BaseTask
  implements Task
{
  protected String startDate = "";
  protected String endDate = "";
  protected String appID = "";
  protected String modifierID = "";
  protected String ownerID = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HashMap localHashMap = new HashMap();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    ApplicationHistory localApplicationHistory = new ApplicationHistory(localLocale);
    localApplicationHistory.setAppID(this.appID);
    localApplicationHistory.setModifierID(this.modifierID);
    localApplicationHistory.setOwnerID(this.ownerID);
    ApplicationHistories localApplicationHistories = new ApplicationHistories(localLocale);
    localHashMap.put("APPLICATIONHISTORIES", localApplicationHistories);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      localApplicationHistories = Applications.getHistories(localSecureUser, localApplicationHistory, this.startDate, this.endDate, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if ((this.error == 0) || (this.error == 7090))
    {
      str = this.successURL;
      localHttpSession.setAttribute("ApplicationHistory", localApplicationHistories);
    }
    return str;
  }
  
  public void setStartDate(String paramString)
  {
    this.startDate = paramString;
  }
  
  public void setEndDate(String paramString)
  {
    this.endDate = paramString;
  }
  
  public void setAppID(String paramString)
  {
    this.appID = paramString;
  }
  
  public void setModifierID(String paramString)
  {
    this.modifierID = paramString;
  }
  
  public void setOwnerID(String paramString)
  {
    this.ownerID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.applications.GetHistory
 * JD-Core Version:    0.7.0.1
 */