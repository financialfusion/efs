package com.ffusion.tasks.lockbox;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.lockbox.LockboxAccounts;
import com.ffusion.beans.lockbox.LockboxSummaries;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Lockbox;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LockboxSummariesTask
  extends BaseTask
{
  String aNf = "P";
  Calendar aNe = null;
  Calendar aNg = null;
  String aNh = "MM/dd/yyyy";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("DATA_CLASSIFICATION", this.aNf);
      LockboxSummaries localLockboxSummaries = null;
      if ((this.aNe == null) && (this.aNg == null))
      {
        localObject = Calendar.getInstance();
        Calendar localCalendar = Calendar.getInstance();
        ((Calendar)localObject).set(11, 0);
        ((Calendar)localObject).set(12, 0);
        ((Calendar)localObject).set(13, 0);
        ((Calendar)localObject).set(14, 0);
        localCalendar.set(11, 0);
        localCalendar.set(12, 0);
        localCalendar.set(13, 0);
        localCalendar.set(14, 0);
        localCalendar.add(6, 1);
        localCalendar.add(14, -1);
        int i = 0;
        while (i++ < 7)
        {
          localLockboxSummaries = Lockbox.getSummaries(localSecureUser, (Calendar)localObject, localCalendar, localHashMap);
          if ((localLockboxSummaries == null) || (localLockboxSummaries.size() == 0))
          {
            ((Calendar)localObject).add(6, -1);
            localCalendar.add(6, -1);
          }
          else
          {
            this.aNe = ((Calendar)localObject);
            this.aNg = localCalendar;
          }
        }
      }
      else
      {
        if (this.aNe != null)
        {
          this.aNe.set(11, 0);
          this.aNe.set(12, 0);
          this.aNe.set(13, 0);
          this.aNe.set(14, 0);
        }
        if (this.aNg != null)
        {
          this.aNg.set(11, 0);
          this.aNg.set(12, 0);
          this.aNg.set(13, 0);
          this.aNg.set(14, 0);
          this.aNg.add(6, 1);
          this.aNg.add(14, -1);
        }
        localLockboxSummaries = Lockbox.getSummaries(localSecureUser, this.aNe, this.aNg, localHashMap);
      }
      localHttpSession.setAttribute("LOCKBOX_SUMMARIES", localLockboxSummaries);
      Object localObject = (LockboxAccounts)localHashMap.get(LockboxAccounts.class);
      localHttpSession.setAttribute("LOCKBOX_ACCOUNTS", localObject);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setDataClassification(String paramString)
  {
    this.aNf = paramString;
  }
  
  public String getDataClassification()
  {
    return this.aNf;
  }
  
  public void setDateFormat(String paramString)
  {
    if (!paramString.equals(this.aNh)) {
      this.aNh = paramString;
    }
  }
  
  public String getDateFormat()
  {
    return this.aNh;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.aNh))) {
      this.aNe = null;
    } else {
      try
      {
        this.aNe = Calendar.getInstance();
        this.aNe.setTime(DateFormatUtil.getFormatter(this.aNh).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.aNe = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.aNh + ")");
      }
    }
  }
  
  public String getStartDate()
  {
    String str = null;
    if (this.aNe != null) {
      str = DateFormatUtil.getFormatter(this.aNh).format(this.aNe.getTime());
    }
    return str;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.aNh))) {
      this.aNg = null;
    } else {
      try
      {
        this.aNg = Calendar.getInstance();
        this.aNg.setTime(DateFormatUtil.getFormatter(this.aNh).parse(paramString));
      }
      catch (ParseException localParseException)
      {
        this.aNg = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.aNh + ")");
      }
    }
  }
  
  public String getEndDate()
  {
    String str = null;
    if (this.aNg != null) {
      str = DateFormatUtil.getFormatter(this.aNh).format(this.aNg.getTime());
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.lockbox.LockboxSummariesTask
 * JD-Core Version:    0.7.0.1
 */