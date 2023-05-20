package com.ffusion.tasks.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.register.RegisterReport;
import com.ffusion.beans.register.RegisterReportTransactions;
import com.ffusion.beans.register.RegisterTransaction;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.register.TransactionCategories;
import com.ffusion.beans.register.TransactionCategory;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetReportPayee
  extends ExtendedBaseTask
  implements Task
{
  private String EG = "RegisterTransactions";
  
  public SetReportPayee()
  {
    this.collectionSessionName = "RegisterReport";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    RegisterTransactions localRegisterTransactions = (RegisterTransactions)localHttpSession.getAttribute(this.EG);
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
    this.EG = paramString;
  }
  
  public String getTransactionsSessionName()
  {
    return this.EG;
  }
  
  protected RegisterReport getRegisterReport(RegisterTransactions paramRegisterTransactions)
  {
    RegisterReport localRegisterReport = new RegisterReport();
    localRegisterReport.addRegisterTransactions(paramRegisterTransactions);
    Iterator localIterator1 = paramRegisterTransactions.iterator();
    while (localIterator1.hasNext()) {
      try
      {
        RegisterTransaction localRegisterTransaction1 = (RegisterTransaction)localIterator1.next();
        if ((localRegisterTransaction1 != null) && ((localRegisterTransaction1.getStatusValue() == 2) || (localRegisterTransaction1.getStatusValue() == 3)))
        {
          TransactionCategories localTransactionCategories1 = localRegisterTransaction1.getTransactionCategories();
          if (localTransactionCategories1 != null)
          {
            Iterator localIterator2 = localTransactionCategories1.iterator();
            while (localIterator2.hasNext())
            {
              TransactionCategory localTransactionCategory = (TransactionCategory)localIterator2.next();
              if (localTransactionCategory != null)
              {
                String str = localRegisterTransaction1.getPayeeName();
                RegisterReportTransactions localRegisterReportTransactions = localRegisterReport.getById(str);
                if (localRegisterReportTransactions == null)
                {
                  localRegisterReportTransactions = new RegisterReportTransactions();
                  if (localRegisterTransaction1.getAmountValue().isNegative()) {
                    localRegisterReportTransactions.setType("EXPENSE");
                  } else {
                    localRegisterReportTransactions.setType("INCOME");
                  }
                  localRegisterReport.add(localRegisterReportTransactions);
                  localRegisterReportTransactions.setId(str);
                  localRegisterReportTransactions.setName(str);
                }
                RegisterTransaction localRegisterTransaction2 = new RegisterTransaction();
                localRegisterTransaction2.set(localRegisterTransaction1);
                TransactionCategories localTransactionCategories2 = localRegisterTransaction2.getTransactionCategories();
                localTransactionCategories2.clear();
                localTransactionCategories2.add(localTransactionCategory);
                localRegisterReportTransactions.add(localRegisterTransaction2);
              }
            }
          }
        }
      }
      catch (Exception localException) {}
    }
    return localRegisterReport;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.SetReportPayee
 * JD-Core Version:    0.7.0.1
 */