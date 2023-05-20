package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.billpresentment.BillSummaries;
import com.ffusion.beans.billpresentment.BillSummary;
import com.ffusion.beans.billpresentment.PaymentInfo;
import com.ffusion.beans.billpresentment.PaymentInfos;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetPaymentInfos
  extends BaseTask
  implements Task
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    BillSummaries localBillSummaries = (BillSummaries)localHttpSession.getAttribute("BillSummaries");
    if (localBillSummaries == null)
    {
      this.error = 6505;
      str = this.taskErrorURL;
    }
    Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (localLocale == null)
    {
      this.error = 41;
      str = this.taskErrorURL;
    }
    PaymentInfos localPaymentInfos = new PaymentInfos(localLocale);
    Iterator localIterator1 = localBillSummaries.iterator();
    while (localIterator1.hasNext())
    {
      Iterator localIterator2 = ((BillSummary)localIterator1.next()).getPaymentInfos().iterator();
      while (localIterator2.hasNext()) {
        localPaymentInfos.add((PaymentInfo)localIterator2.next());
      }
    }
    localHttpSession.setAttribute("PaymentInfos", localPaymentInfos);
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.GetPaymentInfos
 * JD-Core Version:    0.7.0.1
 */