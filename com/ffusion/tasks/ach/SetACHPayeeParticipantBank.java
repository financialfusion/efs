package com.ffusion.tasks.ach;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetACHPayeeParticipantBank
  extends BaseTask
  implements Task
{
  private String A5 = "AddACHPayee";
  private String A6 = "ACHParticipantBank";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    EditACHPayee localEditACHPayee = (EditACHPayee)localHttpSession.getAttribute(this.A5);
    if (localEditACHPayee == null)
    {
      this.error = 16332;
      return this.taskErrorURL;
    }
    FinancialInstitution localFinancialInstitution = (FinancialInstitution)localHttpSession.getAttribute(this.A6);
    if (localFinancialInstitution == null)
    {
      this.error = 16330;
      return this.taskErrorURL;
    }
    localEditACHPayee.setBankName(localFinancialInstitution.getInstitutionName());
    localEditACHPayee.setRoutingNumber(localFinancialInstitution.getAchRoutingNumber());
    return str;
  }
  
  public void setPayeeSessionName(String paramString)
  {
    this.A5 = paramString;
  }
  
  public void setBeanSessionName(String paramString)
  {
    this.A6 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.SetACHPayeeParticipantBank
 * JD-Core Version:    0.7.0.1
 */