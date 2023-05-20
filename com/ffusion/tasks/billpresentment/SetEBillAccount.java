package com.ffusion.tasks.billpresentment;

import com.ffusion.beans.billpresentment.EBillAccount;
import com.ffusion.beans.billpresentment.EBillAccounts;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetEBillAccount
  extends BaseTask
  implements Task
{
  private String c6;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.successURL;
    int i = setEBillAccount(localHttpSession);
    if (i == -1) {
      str = this.taskErrorURL;
    } else if (i == -2) {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected int setEBillAccount(HttpSession paramHttpSession)
  {
    int i = 0;
    EBillAccounts localEBillAccounts = (EBillAccounts)paramHttpSession.getAttribute("EBillAccounts");
    EBillAccount localEBillAccount = new EBillAccount();
    if (this.c6 == null)
    {
      i = -1;
      this.error = 6702;
    }
    else if (localEBillAccounts == null)
    {
      i = -1;
      this.error = 6510;
    }
    else
    {
      if (!"".equals(this.c6)) {
        localEBillAccount = localEBillAccounts.getByAccountID(Integer.valueOf(this.c6).intValue());
      }
      if (localEBillAccount != null)
      {
        paramHttpSession.setAttribute("EBillAccount", localEBillAccount);
      }
      else
      {
        i = -1;
        this.error = 6702;
      }
    }
    return i;
  }
  
  public final void setID(String paramString)
  {
    this.c6 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpresentment.SetEBillAccount
 * JD-Core Version:    0.7.0.1
 */