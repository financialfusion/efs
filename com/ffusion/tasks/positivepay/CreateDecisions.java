package com.ffusion.tasks.positivepay;

import com.ffusion.beans.positivepay.PPayDecision;
import com.ffusion.beans.positivepay.PPayDecisions;
import com.ffusion.beans.positivepay.PPayIssue;
import com.ffusion.beans.positivepay.PPayIssues;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CreateDecisions
  extends BaseTask
  implements Task
{
  public static String PPAY_ISSUES_NAME = "PPayIssues";
  public static String PPAY_DECISIONS_NAME = "PPayDecisions";
  public static String DECISION_INPUT_KEY = "decision";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    PPayDecisions localPPayDecisions = null;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PPayIssues localPPayIssues = (PPayIssues)localHttpSession.getAttribute(PPAY_ISSUES_NAME);
    Locale localLocale = (Locale)localHttpSession.getAttribute("Locale");
    if (localLocale == null) {
      localLocale = Locale.getDefault();
    }
    if (localPPayIssues == null)
    {
      this.error = 22050;
      str1 = this.taskErrorURL;
    }
    else
    {
      localPPayDecisions = new PPayDecisions(localLocale);
      for (int i = 0; i < localPPayIssues.size(); i++)
      {
        PPayDecision localPPayDecision = localPPayDecisions.add();
        PPayIssue localPPayIssue = (PPayIssue)localPPayIssues.get(i);
        localPPayDecision.setCheckRecord(localPPayIssue.getCheckRecord());
        localPPayDecision.setRejectReason(localPPayIssue.getRejectReason());
        localPPayDecision.setIssueDate(localPPayIssue.getIssueDate());
        HashMap localHashMap = localPPayIssue.getHash();
        localHashMap.putAll(localPPayDecision.getHash());
        localPPayDecision.setHash(localHashMap);
        String str2 = paramHttpServletRequest.getParameter(DECISION_INPUT_KEY + i);
        if (str2 == null)
        {
          this.error = 22051;
          str1 = this.taskErrorURL;
          break;
        }
        localPPayDecision.setDecision(str2);
      }
    }
    if (str1 != this.taskErrorURL) {
      localHttpSession.setAttribute(PPAY_DECISIONS_NAME, localPPayDecisions);
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.positivepay.CreateDecisions
 * JD-Core Version:    0.7.0.1
 */