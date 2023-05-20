package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.history.Histories;
import com.ffusion.beans.history.History;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BankEmployeeAdmin;
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

public class GetUserHistory
  extends BaseTask
  implements BankEmployeeTask
{
  private String ty = "";
  private String tx = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    int i = 0;
    if (this.tx.length() > 0) {
      try
      {
        i = Integer.parseInt(this.tx);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        this.error = 47;
      }
    }
    if (this.error == 0)
    {
      HashMap localHashMap = null;
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      History localHistory = new History((Locale)localHttpSession.getAttribute("java.util.Locale"));
      Histories localHistories = new Histories((Locale)localHttpSession.getAttribute("java.util.Locale"));
      localHistory.setId(this.ty);
      localHistory.setIdType(1);
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
        localHistories = BankEmployeeAdmin.getHistory(localSecureUser, localHistory, localCalendar2, localCalendar1, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if ((this.error == 0) || (this.error == 12))
      {
        str = this.successURL;
        localHttpSession.setAttribute("UserHistories", localHistories);
      }
    }
    return str;
  }
  
  public void setId(String paramString)
  {
    this.ty = paramString;
  }
  
  public void setMonths(String paramString)
  {
    this.tx = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetUserHistory
 * JD-Core Version:    0.7.0.1
 */