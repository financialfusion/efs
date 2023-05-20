package com.ffusion.tasks.banklookup;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetFinancialInstitution
  extends BaseTask
  implements BankLookupTask
{
  private boolean FR = false;
  private boolean FS = true;
  private FinancialInstitution FP = new FinancialInstitution();
  private FinancialInstitutions FQ = new FinancialInstitutions();
  private int FT;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    int i = 0;
    this.FQ = ((FinancialInstitutions)localHttpSession.getAttribute("FinancialInstitutions"));
    if (this.FQ != null)
    {
      try
      {
        Iterator localIterator = this.FQ.iterator();
        while (localIterator.hasNext())
        {
          FinancialInstitution localFinancialInstitution = (FinancialInstitution)localIterator.next();
          if (localFinancialInstitution.getInstitutionId() == this.FT)
          {
            localHttpSession.setAttribute("FinancialInstitution", localFinancialInstitution);
            break;
          }
        }
      }
      catch (Exception localException)
      {
        this.error = 32019;
        str = this.taskErrorURL;
      }
      this.FR = false;
    }
    else
    {
      str = this.taskErrorURL;
      this.error = 32018;
    }
    return str;
  }
  
  public void setInstitutionId(String paramString)
  {
    this.FT = Integer.parseInt(paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banklookup.SetFinancialInstitution
 * JD-Core Version:    0.7.0.1
 */