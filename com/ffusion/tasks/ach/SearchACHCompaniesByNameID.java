package com.ffusion.tasks.ach;

import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchACHCompaniesByNameID
  extends BaseTask
  implements Task
{
  protected String companyName;
  protected String companyID;
  protected String companiesName = "ACHCOMPANIES";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ACHCompanies localACHCompanies = (ACHCompanies)localHttpSession.getAttribute(this.companiesName);
    if (localACHCompanies != null)
    {
      searchACHCompanies(localHttpSession, localACHCompanies);
      str = this.successURL;
    }
    else
    {
      this.error = 16505;
    }
    return str;
  }
  
  protected void searchACHCompanies(HttpSession paramHttpSession, ACHCompanies paramACHCompanies)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    ACHCompanies localACHCompanies = new ACHCompanies(localLocale);
    Iterator localIterator = paramACHCompanies.iterator();
    while (localIterator.hasNext())
    {
      ACHCompany localACHCompany = (ACHCompany)localIterator.next();
      if (((this.companyName == null) || (this.companyName.length() <= 0) || (localACHCompany.getCompanyName() == null) || (checkCompanyName(localACHCompany.getCompanyName()))) && ((this.companyID == null) || (this.companyID.length() <= 0) || (localACHCompany.getCompanyID() == null) || (checkCompanyID(localACHCompany.getCompanyID())))) {
        localACHCompanies.add(localACHCompany);
      }
    }
    paramHttpSession.setAttribute("FILTERED_ACHCOMPANIES", localACHCompanies);
  }
  
  protected boolean checkCompanyName(String paramString)
  {
    return paramString.toUpperCase().startsWith(this.companyName.toUpperCase());
  }
  
  protected boolean checkCompanyID(String paramString)
  {
    return paramString.toUpperCase().startsWith(this.companyID.toUpperCase());
  }
  
  public void setCompanyID(String paramString)
  {
    this.companyID = paramString.trim();
  }
  
  public void setCompanyName(String paramString)
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
  
  public String getCompanyID()
  {
    String str = "";
    if (this.companyID != null) {
      str = this.companyID;
    }
    return str;
  }
  
  public String getCompanyName()
  {
    String str = "";
    if (this.companyName != null) {
      str = this.companyName;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.SearchACHCompaniesByNameID
 * JD-Core Version:    0.7.0.1
 */