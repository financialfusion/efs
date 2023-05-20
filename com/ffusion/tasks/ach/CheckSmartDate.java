package com.ffusion.tasks.ach;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CheckSmartDate
  extends BaseTask
  implements Task
{
  private String AQ = "Business";
  private String AO = "yyyyMMdd";
  private boolean AS = false;
  private int AN = 14;
  private String AP = "17:00";
  private String AR = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Business localBusiness = (Business)localHttpSession.getAttribute(this.AQ);
    DateTime localDateTime1 = null;
    try
    {
      localDateTime1 = new DateTime(this.AR, localBusiness.getLocale(), this.AO);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
    DateTime localDateTime2 = new DateTime();
    int i = 0;
    int j = 0;
    int k = this.AP.indexOf(":");
    if (k > -1) {
      try
      {
        i = Integer.parseInt(this.AP.substring(0, k));
        j = Integer.parseInt(this.AP.substring(k + 1));
      }
      catch (Exception localException1) {}
    } else {
      try
      {
        i = Integer.parseInt(this.AP);
      }
      catch (Exception localException2) {}
    }
    if (((localDateTime2.get(11) == i) && (localDateTime2.get(12) >= j)) || (localDateTime2.get(11) > i)) {
      localDateTime2.add(6, 1);
    }
    DateTime localDateTime3 = (DateTime)localDateTime2.clone();
    localDateTime3.add(6, this.AN);
    if (localDateTime1 == null)
    {
      this.error = 44;
      return this.taskErrorURL;
    }
    if ((localDateTime1.get(1) < localDateTime2.get(1)) || ((localDateTime1.get(1) == localDateTime2.get(1)) && (localDateTime1.get(6) < localDateTime2.get(6))))
    {
      this.error = 16126;
      return this.taskErrorURL;
    }
    if ((localDateTime1.get(1) == localDateTime2.get(1)) && (localDateTime1.get(6) == localDateTime2.get(6)) && (!localBusiness.getACHAllowZeroDaysValue()))
    {
      this.error = 16126;
      return this.taskErrorURL;
    }
    if ((this.AS) && (localDateTime1.after(localDateTime3)))
    {
      this.error = 16181;
      return this.taskErrorURL;
    }
    try
    {
      Date localDate = ACH.getSmartDate(localSecureUser, localDateTime1);
      DateFormat localDateFormat = DateFormatUtil.getFormatter(this.AO);
      String str1 = localDateFormat.format(localDate);
      String str2 = localDateFormat.format(localDateTime1.getTime());
      if (!str2.equals(str1))
      {
        this.error = 16182;
        return this.taskErrorURL;
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = localCSILException.getCode();
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  public String getBusinessName()
  {
    return this.AQ;
  }
  
  public void setBusinessName(String paramString)
  {
    this.AQ = paramString;
  }
  
  public String getFutureDays()
  {
    return String.valueOf(this.AN);
  }
  
  public int getFutureDaysValue()
  {
    return this.AN;
  }
  
  public void setFutureDays(String paramString)
  {
    try
    {
      this.AN = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setCheckFutureDays(String paramString)
  {
    this.AS = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getDailyCutoffTime()
  {
    return this.AP;
  }
  
  public void setDailyCutoffTime(String paramString)
  {
    this.AP = paramString;
  }
  
  public void setDateFormat(String paramString)
  {
    this.AO = paramString;
  }
  
  public void setSmartDate(String paramString)
  {
    this.AR = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.CheckSmartDate
 * JD-Core Version:    0.7.0.1
 */