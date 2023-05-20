package com.ffusion.tasks.cashcon;

import com.ffusion.beans.cashcon.CashConCompanies;
import com.ffusion.beans.cashcon.CashConCompany;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetCashConCompany
  extends BaseTask
  implements Task
{
  protected String _bpwid;
  protected String _cashconcompaniesName = "CashConCompanies";
  protected String _cashconcompanyName = "CashConCompany";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    CashConCompanies localCashConCompanies = (CashConCompanies)localHttpSession.getAttribute(this._cashconcompaniesName);
    if (localCashConCompanies != null)
    {
      CashConCompany localCashConCompany = localCashConCompanies.getByID(this._bpwid);
      if (localCashConCompany != null)
      {
        localHttpSession.setAttribute(this._cashconcompanyName, localCashConCompany);
        str = this.successURL;
      }
      else
      {
        this.error = 24002;
        str = this.taskErrorURL;
      }
    }
    else
    {
      this.error = 24003;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public final void setBPWID(String paramString)
  {
    this._bpwid = paramString;
  }
  
  public final void setCollectionSessionName(String paramString)
  {
    this._cashconcompaniesName = paramString;
  }
  
  public final void setCompanySessionName(String paramString)
  {
    this._cashconcompanyName = paramString;
  }
  
  public final String getCollectionName()
  {
    return this._cashconcompaniesName;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.SetCashConCompany
 * JD-Core Version:    0.7.0.1
 */