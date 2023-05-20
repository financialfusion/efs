package com.ffusion.tasks.ach;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
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

public class GetNextSmartDate
  extends BaseTask
  implements Task
{
  private String zG = "17:00";
  private int zF = 2;
  private String zE = "yyyyMMdd";
  private String zH;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    DateTime localDateTime = new DateTime();
    int i = 0;
    int j = 0;
    int k = this.zG.indexOf(":");
    if (k > -1) {
      try
      {
        i = Integer.parseInt(this.zG.substring(0, k));
        j = Integer.parseInt(this.zG.substring(k + 1));
      }
      catch (Exception localException1) {}
    } else {
      try
      {
        i = Integer.parseInt(this.zG);
      }
      catch (Exception localException2) {}
    }
    if (((localDateTime.get(11) == i) && (localDateTime.get(12) >= j)) || (localDateTime.get(11) > i)) {
      localDateTime.add(6, 1);
    }
    DateFormat localDateFormat = DateFormatUtil.getFormatter(this.zE);
    try
    {
      Date localDate = ACH.getSmartDate(localSecureUser, localDateTime);
      localDateTime.setTime(localDate);
      int m = 0;
      for (int n = this.zF; n > 0; n--)
      {
        localDateTime.add(6, 1);
        localDate = ACH.getSmartDate(localSecureUser, localDateTime);
        localDateTime.setTime(localDate);
      }
      while (m++ < 7)
      {
        localDate = ACH.getSmartDate(localSecureUser, localDateTime);
        this.zH = localDateFormat.format(localDate);
        String str = localDateFormat.format(localDateTime.getTime());
        if (str.equals(this.zH)) {
          break;
        }
        localDateTime.add(6, 1);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = localCSILException.getCode();
      return this.serviceErrorURL;
    }
    return this.successURL;
  }
  
  public String getDailyCutoffTime()
  {
    return this.zG;
  }
  
  public void setDailyCutoffTime(String paramString)
  {
    this.zG = paramString;
  }
  
  public String getLeadTime()
  {
    return String.valueOf(this.zF);
  }
  
  public String getSmartDate()
  {
    return this.zH;
  }
  
  public int getLeadTimeValue()
  {
    return this.zF;
  }
  
  public void setLeadTime(String paramString)
  {
    try
    {
      this.zF = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setDateFormat(String paramString)
  {
    this.zE = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetNextSmartDate
 * JD-Core Version:    0.7.0.1
 */