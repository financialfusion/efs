package com.ffusion.tasks.positivepay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.positivepay.PPayCheckRecords;
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

public class SubmitCheckRecords
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    PPayCheckRecords localPPayCheckRecords1 = (PPayCheckRecords)localHttpSession.getAttribute("PPayCheckRecords");
    Locale localLocale = (Locale)localHttpSession.getAttribute("Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    if ((localSecureUser == null) || (localPPayCheckRecords1 == null))
    {
      this.error = 22052;
      str = this.taskErrorURL;
    }
    else
    {
      PPayCheckRecords localPPayCheckRecords2 = new PPayCheckRecords(localLocale);
      for (int i = 0; i < localPPayCheckRecords1.size(); i++)
      {
        PPayCheckRecord localPPayCheckRecord = (PPayCheckRecord)localPPayCheckRecords1.get(i);
        if (localPPayCheckRecord.getCheckDate() != null) {
          localPPayCheckRecords2.add(localPPayCheckRecord);
        }
      }
      try
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("Locale", localLocale);
        PositivePay.submitCheckRecords(localSecureUser, localPPayCheckRecords2, localHashMap);
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
 * Qualified Name:     com.ffusion.tasks.positivepay.SubmitCheckRecords
 * JD-Core Version:    0.7.0.1
 */