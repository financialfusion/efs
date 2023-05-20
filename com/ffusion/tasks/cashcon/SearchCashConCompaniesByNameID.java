package com.ffusion.tasks.cashcon;

import com.ffusion.beans.cashcon.CashConCompanies;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchCashConCompaniesByNameID
  extends BaseTask
  implements Task
{
  protected String companyName;
  protected String companyID;
  protected String companiesName = "CashConCompanies";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    CashConCompanies localCashConCompanies = (CashConCompanies)localHttpSession.getAttribute(this.companiesName);
    if (localCashConCompanies != null)
    {
      searchCashConCompanies(localHttpSession, localCashConCompanies);
      str = this.successURL;
    }
    else
    {
      this.error = 24003;
    }
    return str;
  }
  
  protected void searchCashConCompanies(HttpSession paramHttpSession, CashConCompanies paramCashConCompanies)
  {
    Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
    CashConCompanies localCashConCompanies = new CashConCompanies();
    Iterator localIterator = paramCashConCompanies.iterator();
    while (localIterator.hasNext())
    {
      CashConCompany localCashConCompany = (CashConCompany)localIterator.next();
      if (((this.companyName == null) || (this.companyName.length() <= 0) || (localCashConCompany.getCompanyName() == null) || (checkCompanyName(localCashConCompany.getCompanyName()))) && ((this.companyID == null) || (this.companyID.length() <= 0) || (localCashConCompany.getCompanyID() == null) || (checkCompanyID(localCashConCompany.getCompanyID())))) {
        localCashConCompanies.add(localCashConCompany);
      }
    }
    paramHttpSession.setAttribute("Filtered_CashConCompanies", localCashConCompanies);
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
 * Qualified Name:     com.ffusion.tasks.cashcon.SearchCashConCompaniesByNameID
 * JD-Core Version:    0.7.0.1
 */