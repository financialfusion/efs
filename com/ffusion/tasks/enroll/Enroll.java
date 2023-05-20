package com.ffusion.tasks.enroll;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.applications.Application;
import com.ffusion.csil.CSILException;
import com.ffusion.services.Enroll2;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Enroll
  extends BaseTask
  implements Task, com.ffusion.tasks.applications.Task
{
  protected String validate;
  protected boolean processFlag = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = validateInput(localHttpSession);
    if (this.error == 0) {
      if (this.processFlag)
      {
        this.processFlag = false;
        Enroll2 localEnroll2 = (Enroll2)localHttpSession.getAttribute("com.ffusion.services.Enroll");
        Application localApplication = (Application)localHttpSession.getAttribute("Application");
        try
        {
          HashMap localHashMap1 = null;
          if (localEnroll2 != null)
          {
            localHashMap1 = new HashMap(1);
            localHashMap1.put("SERVICE", localEnroll2);
          }
          SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
          HashMap localHashMap2 = com.ffusion.csil.core.Enroll.enroll(localApplication.getFieldValues(), localHashMap1);
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          str = this.serviceErrorURL;
        }
        if (this.error == 0) {
          str = this.successURL;
        }
      }
      else
      {
        str = this.successURL;
      }
    }
    return str;
  }
  
  protected int validateInput(HttpSession paramHttpSession)
  {
    int i = 0;
    if (this.validate != null)
    {
      Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.states", localLocale);
      String str1 = localResourceBundle.getString("StateAbbr").toUpperCase();
      String str2 = localResourceBundle.getString("StateNames").toUpperCase();
      this.validate = null;
    }
    return i;
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.enroll.Enroll
 * JD-Core Version:    0.7.0.1
 */