package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.billpresentment.BillSummaries;
import com.ffusion.beans.billpresentment.BillSummary;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetBillSummary
  extends BaseTask
  implements Task
{
  private String Kf;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    BillSummaries localBillSummaries = (BillSummaries)localHttpSession.getAttribute("BillSummaries");
    if (this.Kf == null)
    {
      str = this.taskErrorURL;
      this.error = 6504;
    }
    else if (localBillSummaries != null)
    {
      BillSummary localBillSummary = localBillSummaries.getByBillSummaryID(this.Kf);
      if (localBillSummary != null)
      {
        localHttpSession.setAttribute("BillSummary", localBillSummary);
        str = this.successURL;
      }
      else
      {
        this.error = 6504;
        str = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 6505;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public final void setID(String paramString)
  {
    this.Kf = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.SetBillSummary
 * JD-Core Version:    0.7.0.1
 */