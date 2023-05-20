package com.ffusion.tasks.stoppayments;

import javax.servlet.http.HttpSession;

public class SignOff
  extends com.ffusion.tasks.SignOff
  implements Task
{
  protected void removeObjects(HttpSession paramHttpSession)
  {
    super.removeObjects(paramHttpSession);
    paramHttpSession.removeAttribute("com.ffusion.services.Stops");
    paramHttpSession.removeAttribute("StopCheck");
    paramHttpSession.removeAttribute("StopChecks");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.stoppayments.SignOff
 * JD-Core Version:    0.7.0.1
 */