package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.ScheduleType;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddScheduleType
  extends EditScheduleType
  implements CutOffTaskDefines
{
  protected boolean haveProcessed = false;
  
  public AddScheduleType()
  {
    setUseCutOffs(true);
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    if (this.initFlag)
    {
      if (initProcess(localHttpSession)) {
        str = this.successURL;
      } else {
        str = this.taskErrorURL;
      }
    }
    else {
      str = processScheduleType(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  public boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    return true;
  }
  
  protected String processScheduleType(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = addScheduleType(paramHttpSession);
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected String addScheduleType(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 26018;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    ScheduleType localScheduleType1 = null;
    try
    {
      localScheduleType1 = PaymentsAdmin.addScheduleType(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      ScheduleType localScheduleType2 = new ScheduleType();
      localScheduleType2.set(localScheduleType1);
      paramHttpSession.setAttribute(this.scheduleSessionName, localScheduleType2);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.AddScheduleType
 * JD-Core Version:    0.7.0.1
 */