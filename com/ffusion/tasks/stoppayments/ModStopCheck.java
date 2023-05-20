package com.ffusion.tasks.stoppayments;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.stoppayments.StopCheck;
import com.ffusion.beans.stoppayments.StopChecks;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModStopCheck
  extends AddStopCheck
  implements Task
{
  protected String stopsName = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.initFlag)
    {
      str = initProcess(localHttpSession);
      timeoutValue = BaseTask.getTaskTimeoutValue(paramHttpServlet.getServletContext());
    }
    else
    {
      str = modStopCheck(localHttpSession);
    }
    return str;
  }
  
  protected String initProcess(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    this.initFlag = false;
    this.locale = ((Locale)paramHttpSession.getAttribute("java.util.Locale"));
    this.currentlyProcessing = Boolean.FALSE;
    this.haveProcessed = false;
    StopCheck localStopCheck = null;
    if ((this.IDStr != null) && (this.IDStr.trim().length() > 0))
    {
      StopChecks localStopChecks = (StopChecks)paramHttpSession.getAttribute(this.stopsName);
      if (localStopChecks != null)
      {
        localStopCheck = localStopChecks.getByID(this.IDStr);
        set(localStopCheck);
      }
    }
    if (localStopCheck == null)
    {
      this.error = 1;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public final void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected String modStopCheck(HttpSession paramHttpSession)
  {
    String str = null;
    if (validateInput(paramHttpSession))
    {
      if (this.processFlag)
      {
        this.processFlag = false;
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
          try
          {
            str = doProcess(paramHttpSession);
            this.nextURL = str;
          }
          catch (Exception localException1)
          {
            this.error = 1;
            this.nextURL = this.serviceErrorURL;
          }
          finally
          {
            this.currentlyProcessing = Boolean.FALSE;
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
            catch (Exception localException2) {}
          }
          str = this.nextURL;
        }
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
  
  protected String doProcess(HttpSession paramHttpSession)
  {
    String str = this.successURL;
    boolean bool = true;
    com.ffusion.services.Stops localStops = (com.ffusion.services.Stops)paramHttpSession.getAttribute("com.ffusion.services.Stops");
    bool = processModStopCheck(localStops, paramHttpSession);
    if (bool) {
      this.haveProcessed = true;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean processModStopCheck(com.ffusion.services.Stops paramStops, HttpSession paramHttpSession)
  {
    StopCheck localStopCheck = new StopCheck(this.locale);
    localStopCheck.set(this);
    StopChecks localStopChecks = (StopChecks)paramHttpSession.getAttribute("StopChecks");
    try
    {
      HashMap localHashMap = new HashMap(1);
      if (paramStops != null) {
        localHashMap.put("SERVICE", paramStops);
      }
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      localStopCheck = com.ffusion.csil.core.Stops.modStopPayment(localSecureUser, localStopCheck, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      localStopChecks.add(localStopCheck);
      paramHttpSession.setAttribute("StopCheck", localStopCheck);
    }
    return this.error == 0;
  }
  
  public void setStopsName(String paramString)
  {
    this.stopsName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.stoppayments.ModStopCheck
 * JD-Core Version:    0.7.0.1
 */