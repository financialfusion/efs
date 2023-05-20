package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpresentment.BillSummaries;
import com.ffusion.beans.billpresentment.BillSummary;
import com.ffusion.beans.billpresentment.Consumer;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BillPresentment;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBillSummaries
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str;
    if (getBillSummaries(localHttpSession)) {
      str = this.successURL;
    } else if (this.error == 6506) {
      str = this.taskErrorURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean getBillSummaries(HttpSession paramHttpSession)
  {
    this.error = 0;
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    BillSummaries localBillSummaries = new BillSummaries(localLocale);
    Consumer localConsumer = (Consumer)paramHttpSession.getAttribute("Consumer");
    BillSummary localBillSummary = (BillSummary)paramHttpSession.getAttribute("BillSummary");
    if (localConsumer == null)
    {
      this.error = 6506;
    }
    else
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      this.error = 0;
      try
      {
        localBillSummaries = BillPresentment.getBillSummaries(localSecureUser, localConsumer, localBillSummary, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if (this.error == 0) {
        paramHttpSession.setAttribute("BillSummaries", localBillSummaries);
      }
    }
    return this.error == 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.GetBillSummaries
 * JD-Core Version:    0.7.0.1
 */