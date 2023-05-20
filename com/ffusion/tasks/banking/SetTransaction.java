package com.ffusion.tasks.banking;

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
  implements Task
{
  private String yg = "Transactions";
  private String ye = "Transaction";
  private int yf = -1;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Transactions localTransactions = (Transactions)localHttpSession.getAttribute(this.yg);
    if ((localTransactions == null) || (localTransactions.size() < 1))
    {
      this.error = 1024;
    }
    else if (this.yf < 0)
    {
      this.error = 47;
    }
    else
    {
      Object localObject = null;
      for (int i = 0; i < localTransactions.size(); i++)
      {
        Transaction localTransaction = (Transaction)localTransactions.get(i);
        if (localTransaction.getTransactionIndex() == this.yf)
        {
          localObject = localTransaction;
          break;
        }
      }
      if (localObject != null)
      {
        localHttpSession.setAttribute(this.ye, localObject);
        str = this.successURL;
      }
      else
      {
        this.error = 63;
      }
    }
    return str;
  }
  
  public String getTransactionsName()
  {
    return this.yg;
  }
  
  public void setTransactionsName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.yg = "Transactions";
    } else {
      this.yg = paramString;
    }
  }
  
  public String getTransactionName()
  {
    return this.ye;
  }
  
  public void setTransactionName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.ye = "Transaction";
    } else {
      this.ye = paramString;
    }
  }
  
  public String getTransIndex()
  {
    return String.valueOf(this.yf);
  }
  
  public void setTransIndex(String paramString)
  {
    try
    {
      this.yf = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.yf = -1;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.SetTransaction
 * JD-Core Version:    0.7.0.1
 */