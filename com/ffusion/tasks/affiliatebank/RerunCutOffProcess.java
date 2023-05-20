package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RerunCutOffProcess
  extends ExtendedBaseTask
  implements CutOffTaskDefines
{
  public RerunCutOffProcess()
  {
    this.beanSessionName = "CutOffTime";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    this.error = 0;
    CutOffTime localCutOffTime = (CutOffTime)localHttpSession.getAttribute(this.beanSessionName);
    if (localCutOffTime == null)
    {
      this.error = 26004;
      return this.taskErrorURL;
    }
    if ((this.id == null) || (this.id.length() == 0))
    {
      this.error = 26020;
      return this.taskErrorURL;
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    try
    {
      PaymentsAdmin.rerunCutOffProcess(localSecureUser, localCutOffTime, this.id, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.RerunCutOffProcess
 * JD-Core Version:    0.7.0.1
 */