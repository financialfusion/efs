package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetACHCompany
  extends BaseTask
  implements Task
{
  protected String companiesName = "ACHCOMPANIES";
  protected String companyName = "ACHCOMPANY";
  protected String companyID;
  protected String bpwID;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ACHCompanies localACHCompanies = (ACHCompanies)localHttpSession.getAttribute(this.companiesName);
    if (localACHCompanies == null)
    {
      this.error = 16505;
    }
    else
    {
      ACHCompany localACHCompany = null;
      if (this.companyID != null) {
        localACHCompany = localACHCompanies.getByCompanyID(this.companyID);
      }
      if ((localACHCompany == null) && (this.bpwID != null)) {
        localACHCompany = localACHCompanies.getByID(this.bpwID);
      }
      if (localACHCompany == null)
      {
        this.error = 16506;
      }
      else
      {
        localHttpSession.setAttribute(this.companyName, localACHCompany);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public void setCompanyID(String paramString)
  {
    this.companyID = paramString.trim();
    this.bpwID = null;
  }
  
  public void setBPWID(String paramString)
  {
    this.bpwID = paramString.trim();
    this.companyID = null;
  }
  
  public void setCompanyInSessionName(String paramString)
  {
    this.companyName = paramString.trim();
  }
  
  public final void setCompaniesInSessionName(String paramString)
  {
    this.companiesName = paramString;
  }
  
  public final String getCompaniesInSessionName()
  {
    return this.companiesName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.SetACHCompany
 * JD-Core Version:    0.7.0.1
 */