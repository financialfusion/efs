package com.ffusion.tasks.banking;

import com.ffusion.beans.FundsTransactions;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.beans.banking.TransferBatch;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SplitFundTransactionTemplates
  implements Task
{
  public static final String TASK_ERROR = "TE";
  protected String successURL = null;
  protected String taskErrorURL = "TE";
  protected int error = 0;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.error = 0;
    FundsTransactions localFundsTransactions1 = (FundsTransactions)localHttpSession.getAttribute("TransferTemplates");
    FundsTransactions localFundsTransactions2 = new FundsTransactions();
    FundsTransactions localFundsTransactions3 = new FundsTransactions();
    Iterator localIterator = localFundsTransactions1.iterator();
    while (localIterator.hasNext())
    {
      Object localObject1 = localIterator.next();
      Object localObject2;
      if ((localObject1 instanceof Transfer))
      {
        localObject2 = (Transfer)localObject1;
        localFundsTransactions2.add(localObject2);
      }
      else if ((localObject1 instanceof TransferBatch))
      {
        localObject2 = (TransferBatch)localObject1;
        localFundsTransactions3.add(localObject2);
      }
    }
    localHttpSession.setAttribute("SingleTransferTemplateList", localFundsTransactions2);
    localHttpSession.setAttribute("MultipleTransferTemplateList", localFundsTransactions3);
    return str;
  }
  
  public void setTaskErrorURL(String paramString)
  {
    this.taskErrorURL = paramString;
  }
  
  public String getTaskErrorURL()
  {
    return this.taskErrorURL;
  }
  
  public String getError()
  {
    return String.valueOf(this.error);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.SplitFundTransactionTemplates
 * JD-Core Version:    0.7.0.1
 */