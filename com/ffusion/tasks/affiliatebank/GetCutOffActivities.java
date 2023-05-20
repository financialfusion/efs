package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffActivities;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetCutOffActivities
  extends ExtendedBaseTask
  implements CutOffTaskDefines
{
  protected Calendar startDate = null;
  protected Calendar endDate = null;
  
  public GetCutOffActivities()
  {
    this.beanSessionName = "CutOffTime";
    this.collectionSessionName = "CutOffActivities";
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
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    PagingContext localPagingContext = new PagingContext(this.startDate, this.endDate);
    CutOffActivities localCutOffActivities = null;
    try
    {
      localCutOffActivities = PaymentsAdmin.getCutOffActivities(localSecureUser, localCutOffTime.getCategory(), localCutOffTime.getCutOffId(), localPagingContext, localHashMap);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute(this.collectionSessionName, localCutOffActivities);
    return str;
  }
  
  public void setStartDate(String paramString)
  {
    GregorianCalendar localGregorianCalendar = null;
    DateFormat localDateFormat = DateFormatUtil.getFormatter(this.dateFormat);
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        localGregorianCalendar = new GregorianCalendar();
        localGregorianCalendar.setTime(localDateFormat.parse(paramString));
        localGregorianCalendar.set(11, 0);
        localGregorianCalendar.set(12, 0);
        localGregorianCalendar.set(13, 0);
        this.startDate = localGregorianCalendar;
      }
      else
      {
        this.startDate = null;
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("GetCutOffActivities.setStartDate:Exception = " + localThrowable.getMessage() + "," + localThrowable.toString());
    }
  }
  
  public String getStartDate()
  {
    if (this.startDate != null)
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter(this.dateFormat);
      return localDateFormat.format(this.startDate.getTime());
    }
    return null;
  }
  
  public void setEndDate(String paramString)
  {
    DateFormat localDateFormat = DateFormatUtil.getFormatter(this.dateFormat);
    GregorianCalendar localGregorianCalendar = null;
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        localGregorianCalendar = new GregorianCalendar();
        localGregorianCalendar.setTime(localDateFormat.parse(paramString));
        localGregorianCalendar.set(11, 23);
        localGregorianCalendar.set(12, 59);
        localGregorianCalendar.set(13, 59);
        this.endDate = localGregorianCalendar;
      }
      else
      {
        this.endDate = null;
      }
    }
    catch (Throwable localThrowable)
    {
      DebugLog.log("GetCutOffActivities.setEndDate:Exception = " + localThrowable.getMessage() + "," + localThrowable.toString());
    }
  }
  
  public String getEndDate()
  {
    if (this.endDate != null)
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter(this.dateFormat);
      return localDateFormat.format(this.endDate.getTime());
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.GetCutOffActivities
 * JD-Core Version:    0.7.0.1
 */