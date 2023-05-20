package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.DateTime;
import java.io.IOException;
import java.text.DateFormat;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetLastExportedDate
  extends BaseTask
  implements Task
{
  private String eA = "Account";
  private String ez = null;
  private String ex = null;
  private String ey = "MM/dd/yyyy";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.eA);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localAccount == null)
    {
      this.error = 1002;
    }
    else if (localSecureUser == null)
    {
      this.error = 1037;
    }
    else if ((this.ez == null) || (this.ez.length() == 0))
    {
      this.error = 67;
    }
    else
    {
      HashMap localHashMap = new HashMap();
      try
      {
        DateTime localDateTime = UserAdmin.getLastExportedDateTime(localSecureUser, localAccount, this.ez, localHashMap);
        if (localDateTime == null)
        {
          this.ex = "";
        }
        else if ((this.ey != null) && (this.ey.length() > 0))
        {
          DateFormat localDateFormat = DateFormatUtil.getFormatter(this.ey);
          this.ex = localDateFormat.format(localDateTime.getTime());
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    if (this.error == 0) {
      str = this.successURL;
    }
    return str;
  }
  
  public void setAccountSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.eA = "Account";
    } else {
      this.eA = paramString;
    }
  }
  
  public void setReportType(String paramString)
  {
    this.ez = paramString;
  }
  
  public void setDateFormat(String paramString)
  {
    this.ey = paramString;
  }
  
  public String getLastExportedDate()
  {
    return this.ex;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetLastExportedDate
 * JD-Core Version:    0.7.0.1
 */