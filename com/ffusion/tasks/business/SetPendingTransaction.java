package com.ffusion.tasks.business;

import com.ffusion.beans.business.PendingTransaction;
import com.ffusion.beans.business.PendingTransactions;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetPendingTransaction
  extends BaseTask
  implements BusinessTask
{
  private String hm = "PendingTransactions";
  private String hl = "PendingTransaction";
  private String hk = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    try
    {
      if (!jdMethod_try(this.hk))
      {
        this.error = 4130;
        return this.taskErrorURL;
      }
      PendingTransactions localPendingTransactions = null;
      localPendingTransactions = (PendingTransactions)localHttpSession.getAttribute(this.hm);
      if (localPendingTransactions == null)
      {
        this.error = 4127;
        return this.taskErrorURL;
      }
      PendingTransaction localPendingTransaction = null;
      localPendingTransaction = localPendingTransactions.getPendingTransactionByItemID(this.hk);
      if (localPendingTransaction == null)
      {
        this.error = 4128;
        return this.taskErrorURL;
      }
      localHttpSession.setAttribute(this.hl, localPendingTransaction);
    }
    catch (Exception localException)
    {
      this.error = -1;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setItemID(String paramString)
  {
    this.hk = paramString;
  }
  
  public String getItemID()
  {
    return this.hk;
  }
  
  public void setSourceSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.hm = paramString;
    }
  }
  
  public String getSourceSessionKey()
  {
    return this.hm;
  }
  
  public void setDestinationSessionKey(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.hl = paramString;
    }
  }
  
  public String getDestinationSessionKey()
  {
    return this.hl;
  }
  
  private boolean jdMethod_try(String paramString)
  {
    return (paramString != null) && (paramString.trim().length() != 0);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.SetPendingTransaction
 * JD-Core Version:    0.7.0.1
 */