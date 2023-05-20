package com.ffusion.tasks.aggregation;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.aggregation.Institution;
import com.ffusion.beans.aggregation.InstitutionAccountTypes;
import com.ffusion.beans.aggregation.Institutions;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AccountAggregation;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetInstitution
  extends BaseTask
  implements Task
{
  private String nZ = "Agg_Institutions_Banking";
  private String n0 = "Agg_Institutions_CreditCards";
  private String nY = "Agg_Institutions_Investements";
  private String n1 = "Agg_Institutions_Rewards";
  private String nX = "Institution";
  private String n2;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ServletContext localServletContext = paramHttpServlet.getServletContext();
    Institutions localInstitutions1 = (Institutions)localServletContext.getAttribute(this.nZ);
    Institutions localInstitutions2 = (Institutions)localServletContext.getAttribute(this.n0);
    Institutions localInstitutions3 = (Institutions)localServletContext.getAttribute(this.nY);
    Institutions localInstitutions4 = (Institutions)localServletContext.getAttribute(this.n1);
    Institution localInstitution = null;
    if ((localInstitutions4 == null) && (localInstitutions3 == null) && (localInstitutions2 == null) && (localInstitutions1 == null))
    {
      this.error = 11005;
      return this.taskErrorURL;
    }
    if (localInstitutions1 != null) {
      localInstitution = localInstitutions1.getByID(this.n2);
    }
    if ((localInstitution == null) && (localInstitutions2 != null)) {
      localInstitution = localInstitutions2.getByID(this.n2);
    }
    if ((localInstitution == null) && (localInstitutions3 != null)) {
      localInstitution = localInstitutions3.getByID(this.n2);
    }
    if ((localInstitution == null) && (localInstitutions4 != null)) {
      localInstitution = localInstitutions4.getByID(this.n2);
    }
    if (localInstitution != null)
    {
      localHttpSession.setAttribute(this.nX, localInstitution);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        AccountAggregation.getInstitutionAccountTypes(localSecureUser, localInstitution, null);
      }
      catch (CSILException localCSILException) {}
      InstitutionAccountTypes localInstitutionAccountTypes = localInstitution.getInstitutionAccountTypes();
      localHttpSession.setAttribute("InstitutionAccountTypes", localInstitutionAccountTypes);
      str = this.successURL;
    }
    else
    {
      this.error = 11004;
    }
    return str;
  }
  
  public void setInstitutionInSessionName(String paramString)
  {
    this.nX = paramString;
  }
  
  public String getInstitutionInSessionName()
  {
    return this.nX;
  }
  
  public void setID(String paramString)
  {
    this.n2 = paramString;
  }
  
  public String getID()
  {
    return this.n2;
  }
  
  public void setAggBankingInstitutionsName(String paramString)
  {
    this.nZ = paramString;
  }
  
  public void setAggCreditCardsInstitutionsName(String paramString)
  {
    this.n0 = paramString;
  }
  
  public void setAggInvestmentsInstitutionsName(String paramString)
  {
    this.nY = paramString;
  }
  
  public void setAggRewardsInstitutionsName(String paramString)
  {
    this.n1 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.SetInstitution
 * JD-Core Version:    0.7.0.1
 */