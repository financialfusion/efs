package com.ffusion.tasks.positivepay;

import com.ffusion.beans.positivepay.PPayCheckRecord;
import com.ffusion.beans.positivepay.PPayCheckRecords;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateEmptyCheckRecords
  extends BaseTask
  implements Task
{
  public static final String PPAY_CHECK_RECORDS = "PPayCheckRecords";
  public static final int DEFAULT_NUM_OF_RECORDS = 14;
  private int aPl = 14;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PPayCheckRecords localPPayCheckRecords = (PPayCheckRecords)localHttpSession.getAttribute("PPayCheckRecords");
    Locale localLocale = (Locale)localHttpSession.getAttribute("Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    if (localPPayCheckRecords == null) {
      localPPayCheckRecords = new PPayCheckRecords(localLocale);
    }
    for (int i = 0; i < this.aPl; i++)
    {
      PPayCheckRecord localPPayCheckRecord = null;
      if (i + 1 <= localPPayCheckRecords.size()) {
        localPPayCheckRecord = (PPayCheckRecord)localPPayCheckRecords.get(i);
      } else {
        localPPayCheckRecord = localPPayCheckRecords.add();
      }
      localPPayCheckRecord.setAccountID("");
      localPPayCheckRecord.setBankID("");
      localPPayCheckRecord.setCheckNumber("");
      localPPayCheckRecord.setCheckDate(null);
      localPPayCheckRecord.setAmount(null);
      localPPayCheckRecord.setVoidCheck(false);
      localPPayCheckRecord.setAdditionalData("");
    }
    localHttpSession.setAttribute("PPayCheckRecords", localPPayCheckRecords);
    return str;
  }
  
  public void setNumberOfRecords(String paramString)
  {
    try
    {
      this.aPl = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.positivepay.CreateEmptyCheckRecords
 * JD-Core Version:    0.7.0.1
 */