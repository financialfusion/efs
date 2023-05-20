package com.ffusion.tasks.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.register.RegisterReport;
import com.ffusion.beans.register.RegisterReportTransactions;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetReportReconciliation
  extends ExtendedBaseTask
  implements Task
{
  private String E7 = "RegisterTransactions";
  
  public SetReportReconciliation()
  {
    this.collectionSessionName = "RegisterReport";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    RegisterTransactions localRegisterTransactions = (RegisterTransactions)localHttpSession.getAttribute(this.E7);
    if (localRegisterTransactions != null)
    {
      RegisterReport localRegisterReport = getRegisterReport(localRegisterTransactions);
      localHttpSession.setAttribute(this.collectionSessionName, localRegisterReport);
    }
    else
    {
      this.error = 20004;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  public void setTransactionsSessionName(String paramString)
  {
    this.E7 = paramString;
  }
  
  public String getTransactionsSessionName()
  {
    return this.E7;
  }
  
  protected RegisterReport getRegisterReport(RegisterTransactions paramRegisterTransactions)
  {
    RegisterReport localRegisterReport = new RegisterReport();
    localRegisterReport.addRegisterTransactions(paramRegisterTransactions);
    Iterator localIterator = paramRegisterTransactions.iterator();
    while (localIterator.hasNext())
    {
      RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator.next();
      RegisterReportTransactions localRegisterReportTransactions;
      if ((localRegisterTransaction != null) && (localRegisterTransaction.getStatusValue() == 0))
      {
        if (localRegisterTransaction.getAmountValue().isNegative())
        {
          localRegisterReportTransactions = localRegisterReport.getById("EXPENSE");
          if (localRegisterReportTransactions == null)
          {
            localRegisterReportTransactions = new RegisterReportTransactions();
            localRegisterReport.add(localRegisterReportTransactions);
            localRegisterReportTransactions.setId("EXPENSE");
            localRegisterReportTransactions.setName("EXPENSE");
          }
          localRegisterReportTransactions.setType("EXPENSE");
          localRegisterReportTransactions.add(localRegisterTransaction);
        }
        else
        {
          localRegisterReportTransactions = localRegisterReport.getById("INCOME");
          if (localRegisterReportTransactions == null)
          {
            localRegisterReportTransactions = new RegisterReportTransactions();
            localRegisterReport.add(localRegisterReportTransactions);
            localRegisterReportTransactions.setId("INCOME");
            localRegisterReportTransactions.setName("INCOME");
          }
          localRegisterReportTransactions.setType("INCOME");
          localRegisterReportTransactions.add(localRegisterTransaction);
        }
      }
      else if ((localRegisterTransaction != null) && (localRegisterTransaction.getStatusValue() == 1))
      {
        localRegisterReportTransactions = localRegisterReport.getById("BANK");
        if (localRegisterReportTransactions == null)
        {
          localRegisterReportTransactions = new RegisterReportTransactions();
          localRegisterReport.add(localRegisterReportTransactions);
          localRegisterReportTransactions.setId("BANK");
          localRegisterReportTransactions.setName("BANK");
        }
        localRegisterReportTransactions.setType("BANK");
        localRegisterReportTransactions.add(localRegisterTransaction);
      }
    }
    return localRegisterReport;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.SetReportReconciliation
 * JD-Core Version:    0.7.0.1
 */