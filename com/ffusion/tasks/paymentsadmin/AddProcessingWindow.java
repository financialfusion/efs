package com.ffusion.tasks.paymentsadmin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.paymentsadmin.ProcessingWindow;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.DateTime;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddProcessingWindow
  extends ModifyProcessingWindow
{
  protected static long timeoutValue = 120000L;
  protected boolean currentlyProcessing = false;
  protected boolean haveProcessed = false;
  protected String nextURL = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    if (this.initFlag == true) {
      str = initialize(paramHttpServletRequest, localHttpSession);
    } else {
      str = addProcessingWindow(localHttpSession);
    }
    return str;
  }
  
  public String initialize(HttpServletRequest paramHttpServletRequest, HttpSession paramHttpSession)
  {
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    setLocale(this.locale);
    this.currentlyProcessing = false;
    this.haveProcessed = false;
    DateTime localDateTime = new DateTime(this.locale);
    localDateTime.setFormat(getDateFormat());
    setCreateDate((DateTime)localDateTime.clone());
    return this.successURL;
  }
  
  public String addProcessingWindow(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    if (validateInput(paramHttpSession) == true)
    {
      if (this.processFlag == true)
      {
        this.processFlag = false;
        int i = 0;
        synchronized (this)
        {
          if ((!this.currentlyProcessing) && (!this.haveProcessed))
          {
            this.currentlyProcessing = true;
            i = 1;
          }
        }
        if (i == 1)
        {
          try
          {
            str = q(paramHttpSession);
            this.nextURL = str;
          }
          catch (Exception localException1)
          {
            this.error = 1;
            this.nextURL = this.serviceErrorURL;
          }
          finally
          {
            this.currentlyProcessing = false;
          }
        }
        else
        {
          long l = System.currentTimeMillis();
          while ((!this.haveProcessed) && (this.currentlyProcessing == true))
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
            catch (Exception localException2) {}
          }
          str = this.nextURL;
        }
      }
    }
    else {
      return this.taskErrorURL;
    }
    return str;
  }
  
  private String q(HttpSession paramHttpSession)
  {
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    this.nextURL = this.successURL;
    if (localSecureUser == null)
    {
      this.error = 36004;
      return this.taskErrorURL;
    }
    HashMap localHashMap = new HashMap();
    ProcessingWindow localProcessingWindow1 = null;
    try
    {
      localProcessingWindow1 = PaymentsAdmin.addProcessingWindow(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      this.nextURL = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      ProcessingWindow localProcessingWindow2 = new ProcessingWindow(this.locale);
      localProcessingWindow2.set(localProcessingWindow1);
      paramHttpSession.setAttribute(this.beanSessionName, localProcessingWindow2);
      this.haveProcessed = true;
    }
    return this.nextURL;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.paymentsadmin.AddProcessingWindow
 * JD-Core Version:    0.7.0.1
 */