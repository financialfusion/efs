package com.ffusion.tasks.affiliatebank;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.affiliatebank.ScheduleCategory;
import com.ffusion.beans.affiliatebank.ScheduleType;
import com.ffusion.beans.affiliatebank.ScheduleTypes;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PaymentsAdmin;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.DateTime;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetNextCutOffTime
  extends ExtendedBaseTask
  implements CutOffTaskDefines
{
  protected ScheduleCategory scheduleCategory = new ScheduleCategory();
  protected String fIId;
  protected String category;
  protected String nextCutOffTime;
  protected String finalCutOffTime;
  protected String instructionType = "ACHBATCHTRN";
  protected String notSetString = "None set up";
  
  public GetNextCutOffTime()
  {
    this.beanSessionName = "ScheduleCategory";
    CutOffTimes localCutOffTimes = new CutOffTimes();
    ScheduleTypes localScheduleTypes = new ScheduleTypes();
    this.scheduleCategory.setCutOffTimes(localCutOffTimes);
    this.scheduleCategory.setScheduleTypes(localScheduleTypes);
    this.dateFormat = "E hh:mm a";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = this.successURL;
    this.error = 0;
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    HashMap localHashMap = new HashMap();
    try
    {
      if ((this.fIId == null) || (this.fIId.length() == 0))
      {
        this.error = 26011;
        return this.taskErrorURL;
      }
      if ((this.category == null) || (this.category.length() == 0))
      {
        this.error = 26001;
        return this.taskErrorURL;
      }
      this.scheduleCategory = PaymentsAdmin.getScheduleCategory(localSecureUser, this.fIId, this.category, localHashMap);
      this.nextCutOffTime = this.notSetString;
      this.finalCutOffTime = this.notSetString;
      if ((this.scheduleCategory != null) && (this.scheduleCategory.getScheduleTypes() != null))
      {
        ScheduleType localScheduleType = null;
        Iterator localIterator = this.scheduleCategory.getScheduleTypes().iterator();
        while (localIterator.hasNext())
        {
          localScheduleType = (ScheduleType)localIterator.next();
          if (this.instructionType.equals(localScheduleType.getInstructionType())) {
            break;
          }
        }
        if ((localScheduleType != null) && (this.instructionType.equals(localScheduleType.getInstructionType())) && ("TRUE".equalsIgnoreCase(localScheduleType.getActive())))
        {
          String str2 = localScheduleType.getNextRunTime();
          DateTime localDateTime1;
          if (str2 != null)
          {
            localDateTime1 = new DateTime();
            try
            {
              localDateTime1.setFormat("EEE MMM dd HH:mm:ss z yyyy");
              localDateTime1.fromString(str2);
            }
            catch (Exception localException1)
            {
              try
              {
                localDateTime1.setFormat("yyyy/MM/dd HH:mm");
                localDateTime1.fromString(str2);
              }
              catch (Exception localException3) {}
            }
            TimeZone localTimeZone1 = TimeZone.getDefault();
            if ((localScheduleType.getNextRunTimeZone() != null) && (localScheduleType.getNextRunTimeZone().length() > 0)) {
              localTimeZone1 = TimeZone.getTimeZone(localScheduleType.getNextRunTimeZone());
            }
            localDateTime1.setTimeZone(localTimeZone1);
            DateTime localDateTime2 = new DateTime(localDateTime1.getTime(), this.locale, this.dateFormat);
            this.nextCutOffTime = localDateTime2.toString();
            this.finalCutOffTime = localDateTime2.toString();
          }
          str2 = localScheduleType.getFinalRunTime();
          if (str2 != null)
          {
            localDateTime1 = new DateTime();
            try
            {
              localDateTime1.setFormat("EEE MMM dd HH:mm:ss z yyyy");
              localDateTime1.fromString(str2);
            }
            catch (Exception localException2)
            {
              try
              {
                localDateTime1.setFormat("yyyy/MM/dd HH:mm");
                localDateTime1.fromString(str2);
              }
              catch (Exception localException4) {}
            }
            TimeZone localTimeZone2 = TimeZone.getDefault();
            if ((localScheduleType.getNextRunTimeZone() != null) && (localScheduleType.getNextRunTimeZone().length() > 0)) {
              localTimeZone2 = TimeZone.getTimeZone(localScheduleType.getNextRunTimeZone());
            }
            localDateTime1.setTimeZone(localTimeZone2);
            DateTime localDateTime3 = new DateTime(localDateTime1.getTime(), this.locale, this.dateFormat);
            this.finalCutOffTime = localDateTime3.toString();
          }
        }
      }
    }
    catch (Throwable localThrowable)
    {
      if ((localThrowable instanceof CSILException)) {
        this.error = MapError.mapError((CSILException)localThrowable);
      } else {
        this.error = 37504;
      }
      localThrowable.printStackTrace();
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
  
  public void setFIId(String paramString)
  {
    this.fIId = paramString;
  }
  
  public String getFIId()
  {
    return this.fIId;
  }
  
  public void setCategory(String paramString)
  {
    this.category = paramString;
  }
  
  public String getCategory()
  {
    return this.category;
  }
  
  public String getInstructionType()
  {
    return this.instructionType;
  }
  
  public void setInstructionType(String paramString)
  {
    this.instructionType = paramString;
  }
  
  public String getDateFormat()
  {
    return this.dateFormat;
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateFormat = paramString;
  }
  
  public void setNotSetString(String paramString)
  {
    this.notSetString = paramString;
  }
  
  public String getNextCutOffTime()
  {
    return this.nextCutOffTime;
  }
  
  public String getFinalCutOffTime()
  {
    return this.finalCutOffTime;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.GetNextCutOffTime
 * JD-Core Version:    0.7.0.1
 */