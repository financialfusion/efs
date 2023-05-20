package com.ffusion.tasks.aggregation;

import javax.servlet.http.HttpSession;

public class SignOff
  extends com.ffusion.tasks.SignOff
  implements Task
{
  protected void removeObjects(HttpSession paramHttpSession)
  {
    super.removeObjects(paramHttpSession);
    paramHttpSession.removeAttribute("com.ffusion.services.AccountAggregation");
    paramHttpSession.removeAttribute("AccountAggregationCollection");
    paramHttpSession.removeAttribute("AggregatedAccount");
    paramHttpSession.removeAttribute("InstitutionsCollection");
    paramHttpSession.removeAttribute("Institution");
    paramHttpSession.removeAttribute("AggTransaction");
    paramHttpSession.removeAttribute("AggTransactions");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.SignOff
 * JD-Core Version:    0.7.0.1
 */