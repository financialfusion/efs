package com.ffusion.tasks.positivepay;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.positivepay.PPayAccount;
import com.ffusion.beans.positivepay.PPaySummaries;
import com.ffusion.beans.positivepay.PPaySummary;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.PositivePay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetIssuesForAccount
  extends BaseTask
  implements Task
{
  public static String PPAY_ISSUES_FOR_ACCOUNT_NAME = "PPayIssues";
  public static String PPAY_ACCOUNT_NAME = "PPayAccount";
  private String aPj = null;
  private String aPi = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    PPaySummaries localPPaySummaries = (PPaySummaries)localHttpSession.getAttribute(GetSummaries.PPAY_SUMMARIES_NAME);
    Locale localLocale = (Locale)localHttpSession.getAttribute("Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    if ((this.aPj == null) || (this.aPi == null) || (localPPaySummaries == null))
    {
      this.error = 22052;
      return this.taskErrorURL;
    }
    Object localObject1 = null;
    Object localObject2;
    for (int i = 0; i < localPPaySummaries.size(); i++)
    {
      localObject2 = (PPaySummary)localPPaySummaries.get(i);
      PPayAccount localPPayAccount = ((PPaySummary)localObject2).getPPayAccount();
      if ((localPPayAccount.getAccountID().equals(this.aPj)) && (localPPayAccount.getBankID().equals(this.aPi)))
      {
        localObject1 = localPPayAccount;
        break;
      }
    }
    if ((localSecureUser == null) || (localObject1 == null))
    {
      this.error = 22052;
      str = this.taskErrorURL;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("Locale", localLocale);
        localObject2 = PositivePay.getIssuesForAccount(localSecureUser, localObject1, localHashMap);
        localHttpSession.setAttribute(PPAY_ISSUES_FOR_ACCOUNT_NAME, localObject2);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setAccountID(String paramString)
  {
    this.aPj = paramString;
  }
  
  public void setBankID(String paramString)
  {
    this.aPi = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.positivepay.GetIssuesForAccount
 * JD-Core Version:    0.7.0.1
 */