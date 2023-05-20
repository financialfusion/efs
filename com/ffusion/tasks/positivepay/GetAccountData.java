package com.ffusion.tasks.positivepay;

import com.ffusion.beans.positivepay.PPayAccount;
import com.ffusion.beans.positivepay.PPaySummaries;
import com.ffusion.beans.positivepay.PPaySummary;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccountData
  extends BaseTask
  implements Task
{
  String aPh = null;
  String aPf = null;
  PPayAccount aPg = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.aPg = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PPaySummaries localPPaySummaries = (PPaySummaries)localHttpSession.getAttribute(GetSummaries.PPAY_SUMMARIES_NAME);
    if ((localPPaySummaries == null) || (this.aPh == null) || (this.aPf == null))
    {
      this.error = 22052;
      str = this.taskErrorURL;
    }
    else
    {
      for (int i = 0; i < localPPaySummaries.size(); i++)
      {
        PPaySummary localPPaySummary = (PPaySummary)localPPaySummaries.get(i);
        PPayAccount localPPayAccount = localPPaySummary.getPPayAccount();
        if ((localPPayAccount.getAccountID().equals(this.aPh)) && (localPPayAccount.getBankID().equals(this.aPf)))
        {
          this.aPg = localPPayAccount;
          break;
        }
      }
    }
    return str;
  }
  
  public void setAccountID(String paramString)
  {
    this.aPh = paramString;
  }
  
  public void setBankID(String paramString)
  {
    this.aPf = paramString;
  }
  
  public String getRoutingNumber()
  {
    String str = "";
    if (this.aPg != null) {
      str = this.aPg.getRoutingNumber();
    }
    return str;
  }
  
  public String getAccountID()
  {
    String str = "";
    if (this.aPg != null) {
      str = this.aPg.getAccountID();
    }
    return str;
  }
  
  public String getNickName()
  {
    String str = "";
    if (this.aPg != null) {
      str = this.aPg.getNickName();
    }
    return str;
  }
  
  public String getCurrencyType()
  {
    String str = "";
    if (this.aPg != null) {
      str = this.aPg.getCurrencyType();
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.positivepay.GetAccountData
 * JD-Core Version:    0.7.0.1
 */