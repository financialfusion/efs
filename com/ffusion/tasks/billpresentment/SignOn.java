package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.Consumer;
import com.ffusion.beans.billpresentment.EnrollmentStatus;
import com.ffusion.beans.billpresentment.Publisher;
import com.ffusion.beans.billpresentment.Publishers;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.MapError;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SignOn
  extends com.ffusion.tasks.SignOn
  implements Task, EnrollmentStatus
{
  private static final int of = -3;
  private static final int on = -4;
  private static final int og = -5;
  private String oi = null;
  private String oj = null;
  private String oh = null;
  private String ok;
  private long om;
  Locale ol;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.ol = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    if ((this.ok == null) || (this.ok.length() < 1))
    {
      this.error = 6800;
      return this.taskErrorURL;
    }
    try
    {
      this.om = Long.parseLong(this.ok);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("BillPresentment.SignOn Task Exception: ", localException);
      this.error = 6800;
      return this.taskErrorURL;
    }
    if (validateInput(localHttpSession))
    {
      int i = signOn(paramHttpServletRequest);
      switch (i)
      {
      case -1: 
        str = this.taskErrorURL;
        break;
      case -2: 
        str = this.serviceErrorURL;
        break;
      case -3: 
        str = this.oj;
        break;
      case -4: 
        str = this.oi;
        break;
      case -5: 
        str = this.oh;
        break;
      case 101: 
        str = this.invalidPasswordURL;
        break;
      case 100: 
        str = this.invalidPasswordURL;
        break;
      case 102: 
        str = this.mustChangePasswordURL;
        break;
      case 0: 
      default: 
        str = this.successURL;
      }
    }
    else
    {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected int signOn(HttpServletRequest paramHttpServletRequest)
  {
    int i = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Consumer localConsumer1 = new Consumer(this.ol);
    localConsumer1.setConsumerID(this.ok);
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    this.error = 0;
    try
    {
      Consumer localConsumer2 = BillPresentment.getConsumer(localSecureUser, localConsumer1, localHashMap);
      localConsumer1.set(localConsumer2);
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
    }
    if (this.error == 0)
    {
      if (localConsumer1.getStatusCode() == null) {
        i = -3;
      } else {
        localHttpSession.setAttribute("Consumer", localConsumer1);
      }
    }
    else {
      i = -2;
    }
    if (i == 0)
    {
      i = -4;
      Publishers localPublishers1 = new Publishers();
      Publisher localPublisher1 = (Publisher)localHttpSession.getAttribute("Publisher");
      localHashMap = new HashMap();
      this.error = 0;
      try
      {
        Publishers localPublishers2 = BillPresentment.getPublishers(localSecureUser, localPublisher1, localHashMap);
        localPublishers1.addAll(localPublishers2);
      }
      catch (CSILException localCSILException2)
      {
        this.error = MapError.mapError(localCSILException2);
      }
      if ((this.error == 0) && (localPublishers1.size() > 0))
      {
        Iterator localIterator = localPublishers1.iterator();
        while (localIterator.hasNext())
        {
          Publisher localPublisher2 = (Publisher)localIterator.next();
          int j = 0;
          localHashMap = new HashMap();
          this.error = 0;
          try
          {
            j = BillPresentment.getEnrollmentStatus(localSecureUser, localPublisher2, localConsumer1, localHashMap);
          }
          catch (CSILException localCSILException3)
          {
            this.error = MapError.mapError(localCSILException3);
          }
          if (j == 3)
          {
            i = 0;
            break;
          }
          if (j == 1) {
            i = -5;
          }
        }
      }
      else
      {
        this.error = 6512;
        i = -2;
      }
    }
    return i;
  }
  
  public final void setID(String paramString)
  {
    this.ok = paramString;
  }
  
  public final void setAccessDeniedURL(String paramString)
  {
    this.oi = paramString;
  }
  
  public final void setMustEnrollURL(String paramString)
  {
    this.oj = paramString;
  }
  
  public final void setNewConsumerURL(String paramString)
  {
    this.oh = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.SignOn
 * JD-Core Version:    0.7.0.1
 */