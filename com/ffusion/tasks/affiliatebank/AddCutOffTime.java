package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddCutOffTime
  extends EditCutOffTime
  implements CutOffTaskDefines
{
  protected boolean haveProcessed = false;
  
  public String process1(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
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
      str = processCutOffTime(paramHttpServletRequest, localHttpSession);
    }
    return str;
  }
  
  public boolean initProcess(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    setTimeOfDay("00:00");
    setStatus("ACTIVE");
    return true;
  }
  
  protected String processCutOffTime(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
        str = addCutOffTime(paramHttpSession);
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
  
  protected String addCutOffTime(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 26018;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    CutOffTime localCutOffTime1 = null;
    try
    {
      localCutOffTime1 = PaymentsAdmin.addCutOffTime(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      CutOffTime localCutOffTime2 = new CutOffTime();
      localCutOffTime2.set(localCutOffTime1);
      paramHttpSession.setAttribute(this.cutoffSessionName, localCutOffTime2);
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.AddCutOffTime
 * JD-Core Version:    0.7.0.1
 */