package com.ffusion.tasks.ach;

import javax.servlet.http.HttpSession;

public class SignOff
  extends com.ffusion.tasks.SignOff
  implements Task
{
  protected void removeObjects(HttpSession paramHttpSession)
  {
    super.removeObjects(paramHttpSession);
    paramHttpSession.removeAttribute("ACHEntry");
    paramHttpSession.removeAttribute("ACHBatch");
    paramHttpSession.removeAttribute("RecACHBatch");
    paramHttpSession.removeAttribute("ACHBatches");
    paramHttpSession.removeAttribute("ReportData");
    paramHttpSession.removeAttribute("ACHCOMPANIES");
    paramHttpSession.removeAttribute("ACHCOMPANY");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.SignOff
 * JD-Core Version:    0.7.0.1
 */