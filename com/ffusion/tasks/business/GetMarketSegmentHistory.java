package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMarketSegmentHistory
  extends BaseTask
  implements BusinessTask
{
  private String fP = "";
  private String fO = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    int i = 0;
    if (this.fO.length() > 0) {
      try
      {
        i = Integer.parseInt(this.fO);
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    if (this.error == 0)
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      History localHistory = new History((Locale)localHttpSession.getAttribute("java.util.Locale"));
      Histories localHistories = new Histories((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localHistory.setId(this.fP);
      localHistory.setIdType(3);
      Calendar localCalendar1 = null;
      Calendar localCalendar2 = null;
      if (i > 0)
      {
        localCalendar1 = Calendar.getInstance((Locale)localHttpSession.getAttribute("java.util.Locale"));
        localCalendar2 = Calendar.getInstance((Locale)localHttpSession.getAttribute("java.util.Locale"));
        localCalendar2.add(2, i - 2 * i);
      }
      try
      {
        localHistories = Business.getHistory(localSecureUser, localHistory, localCalendar2, localCalendar1, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if ((this.error == 0) || (this.error == 12))
      {
        str = this.successURL;
        localHttpSession.setAttribute("MarketSegmentHistories", localHistories);
      }
    }
    return str;
  }
  
  public void setId(String paramString)
  {
    this.fP = paramString;
  }
  
  public void setMonths(String paramString)
  {
    this.fO = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetMarketSegmentHistory
 * JD-Core Version:    0.7.0.1
 */