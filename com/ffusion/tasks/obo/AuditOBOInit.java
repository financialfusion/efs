package com.ffusion.tasks.obo;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.core.Initialize;
import com.ffusion.csil.core.TrackingIDGenerator;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuditOBOInit
  extends BaseTask
  implements OBOArchiveTask
{
  private static final int aQr = 0;
  private String aQo;
  private int aQs;
  private static final String aQp = "com.ffusion.util.logging.audit.task";
  private static final String aQq = "AuditMessage_AuditOBOInit_1";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    try
    {
      HttpSession localHttpSession = paramHttpServletRequest.getSession();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      String str2 = TrackingIDGenerator.GetNextID();
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = localSecureUser.getUserName();
      arrayOfObject[1] = this.aQo;
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.task", "AuditMessage_AuditOBOInit_1", arrayOfObject);
      if (this.aQs == 0) {
        Initialize.audit(localSecureUser, localLocalizableString, str2, 1806);
      } else {
        Initialize.audit(localSecureUser, localLocalizableString, this.aQs, str2, 1806);
      }
    }
    catch (Throwable localThrowable)
    {
      this.error = 100300;
      str1 = this.taskErrorURL;
    }
    return str1;
  }
  
  public void setUserName(String paramString)
  {
    this.aQo = paramString;
  }
  
  public String getUserName()
  {
    return this.aQo;
  }
  
  public String getBusinessID()
  {
    return Integer.toString(this.aQs);
  }
  
  public void setBusinessID(String paramString)
  {
    try
    {
      this.aQs = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.aQs = 0;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.obo.AuditOBOInit
 * JD-Core Version:    0.7.0.1
 */