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

public class GetGeneratedFileName
  extends ExtendedBaseTask
  implements CutOffTaskDefines
{
  private String GE = "CutOffTime";
  
  public GetGeneratedFileName()
  {
    this.beanSessionName = "GeneratedFileName";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    CutOffTime localCutOffTime = (CutOffTime)localHttpSession.getAttribute(this.GE);
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
    String str2 = "";
    try
    {
      str2 = PaymentsAdmin.getGeneratedFileName(localSecureUser, localCutOffTime, this.id, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this.beanSessionName, str2);
    return str1;
  }
  
  public void setCutOffSessionName(String paramString)
  {
    this.GE = paramString;
  }
  
  public String getCutOffSessionName()
  {
    return this.GE;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.GetGeneratedFileName
 * JD-Core Version:    0.7.0.1
 */