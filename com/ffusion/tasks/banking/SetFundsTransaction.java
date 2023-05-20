package com.ffusion.tasks.banking;

import com.ffusion.beans.FundsTransaction;
import com.ffusion.beans.FundsTransactions;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetFundsTransaction
  extends BaseTask
  implements Task
{
  protected String transactionID;
  protected String name = "TransferTemplates";
  protected String beanSessionName = "Transaction";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    FundsTransactions localFundsTransactions = (FundsTransactions)localHttpSession.getAttribute(this.name);
    if (localFundsTransactions == null)
    {
      this.error = 1024;
      str = this.taskErrorURL;
    }
    else
    {
      FundsTransaction localFundsTransaction = localFundsTransactions.getByID(this.transactionID);
      if (localFundsTransaction == null)
      {
        this.error = 1004;
        str = this.taskErrorURL;
      }
      else
      {
        localHttpSession.setAttribute(this.beanSessionName, localFundsTransaction);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public void setID(String paramString)
  {
    this.transactionID = paramString;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getID()
  {
    return this.transactionID;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getBeanSessionName()
  {
    return this.beanSessionName;
  }
  
  public void setBeanSessionName(String paramString)
  {
    this.beanSessionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.SetFundsTransaction
 * JD-Core Version:    0.7.0.1
 */