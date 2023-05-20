package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.billpresentment.BillSummaries;
import com.ffusion.beans.billpresentment.BillSummary;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBillsToPay
  extends BillSummaries
  implements Task
{
  private String IW = null;
  private String IX = null;
  private String IU = null;
  private int IV = 0;
  public ArrayList IDList = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    clear();
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.IW;
    BillSummaries localBillSummaries = (BillSummaries)localHttpSession.getAttribute("BillSummaries");
    if (localBillSummaries == null)
    {
      str = this.IX;
      this.IV = 6505;
    }
    else
    {
      Iterator localIterator = this.IDList.iterator();
      while (localIterator.hasNext())
      {
        BillSummary localBillSummary1 = localBillSummaries.getByBillSummaryID((String)localIterator.next());
        BillSummary localBillSummary2 = getByBillSummaryID(localBillSummary1.getBillSummaryID());
        if (localBillSummary2 == null) {
          if (localBillSummary1 != null)
          {
            add(localBillSummary1);
          }
          else
          {
            str = this.IX;
            this.IV = 6700;
          }
        }
      }
    }
    if (size() < 1)
    {
      str = this.IX;
      this.IV = 6900;
    }
    localHttpSession.setAttribute("BillsToPay", this);
    this.IDList.clear();
    return str;
  }
  
  public void setIDsToPay(String paramString)
  {
    if (paramString != null) {
      this.IDList.add(paramString);
    }
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.IW = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.IX = paramString;
  }
  
  public final void setServiceErrorURL(String paramString)
  {
    this.IU = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.IV);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.GetBillsToPay
 * JD-Core Version:    0.7.0.1
 */