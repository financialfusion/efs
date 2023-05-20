package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOn
  extends com.ffusion.tasks.SignOn
  implements Task
{
  protected Boolean currentlyProcessing = Boolean.FALSE;
  protected boolean haveProcessed = false;
  protected String serviceName = "com.ffusion.services.Banking";
  protected static long timeoutValue = 120000L;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    timeoutValue = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
    return super.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  protected int signOn(HttpServletRequest paramHttpServletRequest)
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)localHttpSession.getAttribute(this.serviceName);
    int i = 0;
    synchronized (this)
    {
      if ((!this.currentlyProcessing.booleanValue()) && (!this.haveProcessed))
      {
        this.currentlyProcessing = Boolean.TRUE;
        i = 1;
      }
    }
    if (i != 0)
    {
      ??? = null;
      if (localBanking != null)
      {
        ??? = new HashMap();
        ((HashMap)???).put("SERVICE", localBanking);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      this.error = 0;
      try
      {
        localSecureUser = com.ffusion.csil.core.Banking.signOn(localSecureUser, this.userName, this.password, (HashMap)???);
        this.haveProcessed = true;
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      finally
      {
        this.currentlyProcessing = Boolean.FALSE;
      }
      if (this.error == 0) {
        localHttpSession.setAttribute("SecureUser", localSecureUser);
      }
    }
    else
    {
      long l = System.currentTimeMillis();
      while ((!this.haveProcessed) && (this.currentlyProcessing.booleanValue() == true))
      {
        if (System.currentTimeMillis() - l > timeoutValue)
        {
          if (this.error != 0) {
            break;
          }
          this.error = 1;
          break;
        }
        try
        {
          Thread.sleep(2000L);
        }
        catch (Exception localException) {}
      }
    }
    return this.error;
  }
  
  public void setServiceName(String paramString)
  {
    this.serviceName = paramString;
  }
  
  public String getServiceName()
  {
    return this.serviceName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.SignOn
 * JD-Core Version:    0.7.0.1
 */