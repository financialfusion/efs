package com.ffusion.tasks.positivepay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.positivepay.PPayIssues;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PositivePay;
import com.ffusion.services.AccountService3;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetIssues
  extends BaseTask
  implements Task
{
  public static String PPAY_ISSUES_NAME = "PPayIssues";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    AccountService3 localAccountService3 = (AccountService3)localHttpSession.getAttribute("com.ffusion.service.AccountService");
    Locale localLocale = (Locale)localHttpSession.getAttribute("Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    if (localSecureUser == null)
    {
      this.error = 22054;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("SERVICE", localAccountService3);
        localHashMap.put("Locale", localLocale);
        PPayIssues localPPayIssues = PositivePay.getIssues(localSecureUser, localHashMap);
        localHttpSession.setAttribute(PPAY_ISSUES_NAME, localPPayIssues);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.positivepay.GetIssues
 * JD-Core Version:    0.7.0.1
 */