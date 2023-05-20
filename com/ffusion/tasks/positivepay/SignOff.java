package com.ffusion.tasks.positivepay;

import com.ffusion.tasks.Task;
import javax.servlet.http.HttpSession;

public class SignOff
  extends com.ffusion.tasks.SignOff
  implements Task
{
  protected void removeObjects(HttpSession paramHttpSession)
  {
    super.removeObjects(paramHttpSession);
    paramHttpSession.removeAttribute(GetIssues.PPAY_ISSUES_NAME);
    paramHttpSession.removeAttribute(GetIssuesForAccount.PPAY_ACCOUNT_NAME);
    paramHttpSession.removeAttribute(GetIssuesForAccount.PPAY_ISSUES_FOR_ACCOUNT_NAME);
    paramHttpSession.removeAttribute(CreateDecisions.PPAY_DECISIONS_NAME);
    paramHttpSession.removeAttribute(GetSummaries.PPAY_SUMMARIES_NAME);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.positivepay.SignOff
 * JD-Core Version:    0.7.0.1
 */