package com.ffusion.tasks.positivepay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.positivepay.PPayDecisions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PositivePay;
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

public class SubmitDecisions
  extends BaseTask
  implements Task
{
  public static String PPAY_DECISIONS_NAME = "PPayDecisions";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    PPayDecisions localPPayDecisions = (PPayDecisions)localHttpSession.getAttribute(PPAY_DECISIONS_NAME);
    Locale localLocale = (Locale)localHttpSession.getAttribute("Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    if ((localSecureUser == null) || (localPPayDecisions == null))
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
        PositivePay.submitDecisions(localSecureUser, localPPayDecisions, localHashMap);
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
 * Qualified Name:     com.ffusion.tasks.positivepay.SubmitDecisions
 * JD-Core Version:    0.7.0.1
 */