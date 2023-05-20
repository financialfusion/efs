package com.ffusion.tasks.positivepay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.positivepay.PPaySummaries;
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

public class GetSummaries
  extends BaseTask
  implements Task
{
  public static String PPAY_SUMMARIES_NAME = "PPaySummaries";
  
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
      this.error = 22052;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("Locale", localLocale);
        localHashMap.put("SERVICE", localAccountService3);
        PPaySummaries localPPaySummaries = PositivePay.getSummaries(localSecureUser, localHashMap);
        localHttpSession.setAttribute(PPAY_SUMMARIES_NAME, localPPaySummaries);
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
 * Qualified Name:     com.ffusion.tasks.positivepay.GetSummaries
 * JD-Core Version:    0.7.0.1
 */