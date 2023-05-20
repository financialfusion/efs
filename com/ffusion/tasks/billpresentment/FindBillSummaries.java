package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.billpresentment.BillSummaries;
import com.ffusion.beans.billpresentment.BillSummary;
import com.ffusion.beans.billpresentment.PaymentInfos;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class FindBillSummaries
  extends BillSummaries
  implements Task
{
  private String IM;
  private String IL;
  private int IT;
  private String IJ = "SHORT";
  private String IO;
  private String IQ;
  private String IR;
  private String IS;
  private String IK;
  private String IP;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.IL;
    BillSummaries localBillSummaries1 = (BillSummaries)localHttpSession.getAttribute("BillSummaries");
    if (localBillSummaries1 == null)
    {
      this.IT = 6505;
      return this.IM;
    }
    BillSummaries localBillSummaries2 = new BillSummaries(getLocale());
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    int i;
    if ((this.IQ != null) && (this.IQ.length() > 0))
    {
      i = Integer.parseInt(this.IQ);
      localGregorianCalendar.add(2, -i);
      localGregorianCalendar.set(5, 1);
      localGregorianCalendar.set(11, 0);
      localGregorianCalendar.set(12, 0);
      localGregorianCalendar.set(13, 0);
      localGregorianCalendar.set(14, 1);
    }
    Iterator localIterator = localBillSummaries1.iterator();
    while (localIterator.hasNext())
    {
      i = 1;
      BillSummary localBillSummary = (BillSummary)localIterator.next();
      if ((this.IO != null) && (this.IO.length() > 0) && (this.IO.indexOf(localBillSummary.getStatusCode()) == -1)) {
        i = 0;
      }
      if ((i != 0) && (this.IQ != null) && (this.IQ.length() > 0)) {
        try
        {
          if (localGregorianCalendar.after(localBillSummary.getBillDateValue())) {
            i = 0;
          }
        }
        catch (Exception localException)
        {
          DebugLog.throwing("FinBillSummaries Task Exception: ", localException);
          break;
        }
      }
      if ((i != 0) && (this.IR != null) && (this.IR.length() > 0) && (this.IR.compareTo(localBillSummary.getAccountID()) != 0)) {
        i = 0;
      }
      if ((i != 0) && (this.IS != null) && (this.IS.equalsIgnoreCase("true")) && (localBillSummary.getPaymentInfos().size() == 0)) {
        i = 0;
      }
      DateTime localDateTime;
      if ((i != 0) && (this.IK != null) && (this.IK.trim().length() > 0)) {
        if (validateDate(this.IK, localHttpSession)) {
          try
          {
            Locale localLocale1 = (Locale)localHttpSession.getAttribute("java.util.Locale");
            localDateTime = new DateTime(this.IK, localLocale1, this.IJ);
            if (localBillSummary.getPaymentDueDateValue().before(localDateTime)) {
              i = 0;
            }
          }
          catch (InvalidDateTimeException localInvalidDateTimeException1)
          {
            i = 0;
          }
        } else {
          i = 0;
        }
      }
      if ((i != 0) && (this.IP != null) && (this.IP.trim().length() > 0)) {
        if (validateDate(this.IP, localHttpSession)) {
          try
          {
            Locale localLocale2 = (Locale)localHttpSession.getAttribute("java.util.Locale");
            localDateTime = new DateTime(this.IP, localLocale2, this.IJ);
            if (localBillSummary.getPaymentDueDateValue().after(localDateTime)) {
              i = 0;
            }
          }
          catch (InvalidDateTimeException localInvalidDateTimeException2)
          {
            i = 0;
          }
        } else {
          i = 0;
        }
      }
      if (i != 0) {
        localBillSummaries2.add(localBillSummary);
      }
    }
    localHttpSession.setAttribute("FilteredBillSummaries", localBillSummaries2);
    return str;
  }
  
  protected boolean validateDate(String paramString, HttpSession paramHttpSession)
  {
    boolean bool = true;
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      try
      {
        DateTime localDateTime = new DateTime(paramString, localLocale, this.IJ);
      }
      catch (InvalidDateTimeException localInvalidDateTimeException)
      {
        bool = false;
      }
    }
    return bool;
  }
  
  public final void setFilterStatus(String paramString)
  {
    this.IO = paramString;
  }
  
  public final String getFilterStatus()
  {
    return this.IO;
  }
  
  public final void setFilterBillingPeriod(String paramString)
  {
    this.IQ = paramString;
  }
  
  public final String getFilterBillingPeriod()
  {
    return this.IQ;
  }
  
  public final void setFilterAccountID(String paramString)
  {
    this.IR = paramString;
  }
  
  public final String getFilterAccountID()
  {
    return this.IR;
  }
  
  public final void setFilterPaidBillsOnly(String paramString)
  {
    this.IS = paramString;
  }
  
  public final String getFilterPaidBillsOnly()
  {
    return this.IS;
  }
  
  public final void setStartDate(String paramString)
  {
    this.IK = paramString;
  }
  
  public final String getStartDate()
  {
    return this.IK;
  }
  
  public final void setEndDate(String paramString)
  {
    this.IP = paramString;
  }
  
  public final String getEndDate()
  {
    return this.IP;
  }
  
  public final void setSuccessURL(String paramString)
  {
    this.IL = paramString;
  }
  
  public final void setTaskErrorURL(String paramString)
  {
    this.IM = paramString;
  }
  
  public final String getError()
  {
    return String.valueOf(this.IT);
  }
  
  public void setDateFormat(String paramString)
  {
    this.IJ = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.FindBillSummaries
 * JD-Core Version:    0.7.0.1
 */