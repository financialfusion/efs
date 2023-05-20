package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.ScheduleType;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.DateTime;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ResubmitSchedule
  extends ExtendedBaseTask
  implements CutOffTaskDefines
{
  private String Gl;
  private String Gk;
  
  public ResubmitSchedule()
  {
    this.beanSessionName = "ScheduleType";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ScheduleType localScheduleType = (ScheduleType)localHttpSession.getAttribute(this.beanSessionName);
    if (localScheduleType == null)
    {
      this.error = 26012;
      return this.taskErrorURL;
    }
    if ((this.Gl == null) || (this.Gl.length() == 0))
    {
      this.error = 26019;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    try
    {
      PaymentsAdmin.resubmitSchedule(localSecureUser, localScheduleType.getInstructionType(), localScheduleType.getFIId(), getDateForBPW(), this.Gk, localScheduleType.getCategory(), localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return str;
  }
  
  public void setDate(String paramString)
  {
    this.Gl = paramString;
  }
  
  public String getDate()
  {
    return this.Gl;
  }
  
  public void setTrackingID(String paramString)
  {
    this.Gk = paramString;
  }
  
  public String getTrackingID()
  {
    return this.Gk;
  }
  
  public String getDateForBPW()
  {
    DateTime localDateTime = new DateTime();
    localDateTime.setFormat("SHORT");
    localDateTime.setDate(this.Gl);
    localDateTime.setFormat("yyyyMMdd");
    return localDateTime.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.ResubmitSchedule
 * JD-Core Version:    0.7.0.1
 */