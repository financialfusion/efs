package com.ffusion.tasks.billpay;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.ach.Task;
import java.io.IOException;
import java.io.PrintStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetBillPayBank
  extends ExtendedBaseTask
  implements Task
{
  public SetBillPayBank()
  {
    this.beanSessionName = "BillPayBank";
    this.collectionSessionName = "BillPayBanks";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    FinancialInstitutions localFinancialInstitutions = (FinancialInstitutions)localHttpSession.getAttribute(this.collectionSessionName);
    System.out.println("***size in set task of FIS in Task " + localFinancialInstitutions.size());
    System.out.println("***id set task of FIS in Task " + this.id);
    if (localFinancialInstitutions == null)
    {
      this.error = 16331;
      str = this.taskErrorURL;
    }
    else if ((this.id == null) || (this.id.length() == 0))
    {
      this.error = 16330;
      str = this.taskErrorURL;
    }
    else
    {
      FinancialInstitution localFinancialInstitution = localFinancialInstitutions.getByID(this.id);
      if (localFinancialInstitution != null)
      {
        localHttpSession.setAttribute(this.beanSessionName, localFinancialInstitution);
      }
      else
      {
        this.error = 16330;
        str = this.taskErrorURL;
      }
    }
    return str;
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.billpay.SetBillPayBank
 * JD-Core Version:    0.7.0.1
 */