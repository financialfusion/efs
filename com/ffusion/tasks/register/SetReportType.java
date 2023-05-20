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

public class SetReportType
  extends ExtendedBaseTask
  implements Task
{
  private String EL = "RegisterTransactions";
  private boolean EK = false;
  
  public SetReportType()
  {
    this.collectionSessionName = "RegisterReport";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    RegisterTransactions localRegisterTransactions = (RegisterTransactions)localHttpSession.getAttribute(this.EL);
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
    this.EL = paramString;
  }
  
  public String getTransactionsSessionName()
  {
    return this.EL;
  }
  
  public void setUseTypeName(String paramString)
  {
    this.EK = Boolean.valueOf(paramString).booleanValue();
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
          Iterator localIterator2 = localTransactionCategories1.iterator();
          while (localIterator2.hasNext())
          {
            TransactionCategory localTransactionCategory = (TransactionCategory)localIterator2.next();
            if (localTransactionCategory != null)
            {
              String str = localRegisterTransaction1.getRegisterType();
              if (localRegisterTransaction1.getAmountValue().isNegative()) {
                str = str + "_" + "EXPENSE";
              } else {
                str = str + "_" + "INCOME";
              }
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
                if (this.EK) {
                  localRegisterReportTransactions.setName(localRegisterTransaction1.getRegisterTypeName());
                } else {
                  localRegisterReportTransactions.setName(localRegisterTransaction1.getRegisterTypeAbbr());
                }
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
      catch (Exception localException) {}
    }
    return localRegisterReport;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.SetReportType
 * JD-Core Version:    0.7.0.1
 */