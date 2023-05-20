package com.ffusion.tasks.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterCategory;
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

public class SetReportCategory
  extends ExtendedBaseTask
  implements Task
{
  private String EW = "RegisterTransactions";
  private String EY = "RegisterCategories";
  private boolean EV = false;
  private boolean EX = false;
  
  public SetReportCategory()
  {
    this.collectionSessionName = "RegisterReport";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    RegisterCategories localRegisterCategories = (RegisterCategories)localHttpSession.getAttribute(this.EY);
    RegisterTransactions localRegisterTransactions = (RegisterTransactions)localHttpSession.getAttribute(this.EW);
    if (localRegisterTransactions != null)
    {
      if (localRegisterCategories != null)
      {
        RegisterReport localRegisterReport = getRegisterReport(localRegisterCategories, localRegisterTransactions);
        localHttpSession.setAttribute(this.collectionSessionName, localRegisterReport);
      }
      else
      {
        this.error = 20006;
        return this.taskErrorURL;
      }
    }
    else
    {
      this.error = 20004;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  protected RegisterReport getRegisterReport(RegisterCategories paramRegisterCategories, RegisterTransactions paramRegisterTransactions)
  {
    if ((paramRegisterCategories == null) || (paramRegisterTransactions == null)) {
      return null;
    }
    RegisterReport localRegisterReport = new RegisterReport();
    Iterator localIterator1 = paramRegisterTransactions.iterator();
    while (localIterator1.hasNext()) {
      try
      {
        RegisterTransaction localRegisterTransaction = (RegisterTransaction)localIterator1.next();
        if ((localRegisterTransaction != null) && ((localRegisterTransaction.getStatusValue() == 2) || (localRegisterTransaction.getStatusValue() == 3)))
        {
          TransactionCategories localTransactionCategories = localRegisterTransaction.getTransactionCategories();
          Iterator localIterator2 = localTransactionCategories.iterator();
          while (localIterator2.hasNext())
          {
            TransactionCategory localTransactionCategory = (TransactionCategory)localIterator2.next();
            if (localTransactionCategory != null)
            {
              int i = localTransactionCategory.getRegisterCategoryIdValue();
              boolean bool = localRegisterTransaction.getAmountValue().isNegative();
              RegisterReportTransactions localRegisterReportTransactions = jdMethod_for(localRegisterReport, i, bool);
              if (localRegisterReportTransactions == null)
              {
                localRegisterReportTransactions = new RegisterReportTransactions();
                if (bool)
                {
                  localRegisterReportTransactions.setType("EXPENSE");
                  if (i == 0) {
                    this.EX = true;
                  }
                }
                else
                {
                  localRegisterReportTransactions.setType("INCOME");
                  if (i == 0) {
                    this.EV = true;
                  }
                }
                localRegisterReport.add(localRegisterReportTransactions);
                localObject1 = paramRegisterCategories.getById(localTransactionCategory.getRegisterCategoryId());
                if (localObject1 != null)
                {
                  localRegisterReportTransactions.setTax(((RegisterCategory)localObject1).getTaxRelated());
                  localObject2 = paramRegisterCategories.getById(((RegisterCategory)localObject1).getParentCategory());
                  if (localObject2 != null) {
                    localRegisterReportTransactions.setParentName(((RegisterCategory)localObject2).getName());
                  }
                  localRegisterReportTransactions.setId(((RegisterCategory)localObject1).getId());
                  localRegisterReportTransactions.setName(((RegisterCategory)localObject1).getName());
                }
              }
              Object localObject1 = new RegisterTransaction();
              ((RegisterTransaction)localObject1).set(localRegisterTransaction);
              Object localObject2 = ((RegisterTransaction)localObject1).getTransactionCategories();
              ((TransactionCategories)localObject2).clear();
              ((TransactionCategories)localObject2).add(localTransactionCategory);
              localRegisterReportTransactions.add(localObject1);
            }
          }
        }
      }
      catch (Exception localException) {}
    }
    return localRegisterReport;
  }
  
  private RegisterReportTransactions jdMethod_for(RegisterReport paramRegisterReport, int paramInt, boolean paramBoolean)
  {
    if (paramInt != 0) {
      return paramRegisterReport.getById(String.valueOf(paramInt));
    }
    Object localObject = null;
    Iterator localIterator = paramRegisterReport.iterator();
    while (localIterator.hasNext())
    {
      RegisterReportTransactions localRegisterReportTransactions = (RegisterReportTransactions)localIterator.next();
      if ((localRegisterReportTransactions.getId().equals(String.valueOf(paramInt))) && (((localRegisterReportTransactions.getType().equals("EXPENSE")) && (paramBoolean)) || ((localRegisterReportTransactions.getType().equals("INCOME")) && (!paramBoolean))))
      {
        localObject = localRegisterReportTransactions;
        break;
      }
    }
    return localObject;
  }
  
  public void setTransactionsSessionName(String paramString)
  {
    this.EW = paramString;
  }
  
  public String getTransactionsSessionName()
  {
    return this.EW;
  }
  
  public void setCategoriesSessionName(String paramString)
  {
    this.EY = paramString;
  }
  
  public String getCategoriesSessionName()
  {
    return this.EY;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.SetReportCategory
 * JD-Core Version:    0.7.0.1
 */