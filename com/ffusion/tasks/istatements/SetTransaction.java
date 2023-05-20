package com.ffusion.tasks.istatements;

import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetTransaction
  extends BaseTask
  implements StatementTask
{
  private String OX;
  private String O0 = null;
  private String OY = "StatementTransaction";
  private String OZ = "StatementTransactions";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.OX = this.taskErrorURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Transactions localTransactions = (Transactions)localHttpSession.getAttribute(this.OZ);
    if (localTransactions == null)
    {
      this.error = 36207;
      this.OX = this.taskErrorURL;
    }
    else
    {
      Transaction localTransaction = localTransactions.getByID(this.O0);
      if (localTransaction == null)
      {
        this.error = 36208;
        this.OX = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute(this.OY, localTransaction);
        this.OX = this.successURL;
      }
    }
    return this.OX;
  }
  
  public void setTransactionID(String paramString)
  {
    this.O0 = paramString;
  }
  
  public String getTransactionID()
  {
    return this.O0;
  }
  
  public void setStatementTransactionInSessionName(String paramString)
  {
    this.OY = paramString;
  }
  
  public void setStatementTransactionsInSessionName(String paramString)
  {
    this.OZ = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.istatements.SetTransaction
 * JD-Core Version:    0.7.0.1
 */