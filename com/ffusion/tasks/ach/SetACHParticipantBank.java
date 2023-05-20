package com.ffusion.tasks.ach;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.tasks.ExtendedBaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetACHParticipantBank
  extends ExtendedBaseTask
  implements Task
{
  public SetACHParticipantBank()
  {
    this.beanSessionName = "ACHParticipantBank";
    this.collectionSessionName = "ACHParticipantBanks";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    FinancialInstitutions localFinancialInstitutions = (FinancialInstitutions)localHttpSession.getAttribute(this.collectionSessionName);
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
 * Qualified Name:     com.ffusion.tasks.ach.SetACHParticipantBank
 * JD-Core Version:    0.7.0.1
 */