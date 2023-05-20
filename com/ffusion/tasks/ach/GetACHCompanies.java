package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompanies;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetACHCompanies
  extends BaseTask
  implements Task
{
  protected String companiesName = "ACHCOMPANIES";
  protected String custID;
  protected String fiID = null;
  protected boolean reload = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if (this.reload)
    {
      localHttpSession.removeAttribute(this.companiesName);
      this.reload = false;
    }
    String str;
    if (getACHCompanies(localHttpSession)) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean getACHCompanies(HttpSession paramHttpSession)
  {
    ACHCompanies localACHCompanies1 = (ACHCompanies)paramHttpSession.getAttribute(this.companiesName);
    if (localACHCompanies1 == null)
    {
      ACHCompanies localACHCompanies2 = null;
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      this.error = 0;
      try
      {
        localACHCompanies2 = ACH.getACHCompanies(localSecureUser, this.custID, this.fiID, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if ((this.error == 0) && (localACHCompanies2 != null))
      {
        localACHCompanies2.setSortedBy("CONAME");
        paramHttpSession.setAttribute(this.companiesName, localACHCompanies2);
      }
      else if (this.error == 20001)
      {
        localACHCompanies2 = new ACHCompanies(localSecureUser.getLocale());
        paramHttpSession.setAttribute(this.companiesName, localACHCompanies2);
        this.error = 0;
      }
    }
    return this.error == 0;
  }
  
  public final void setCompaniesInSessionName(String paramString)
  {
    this.companiesName = paramString;
  }
  
  public final String getCompaniesInSessionName()
  {
    return this.companiesName;
  }
  
  public String getCustID()
  {
    return this.custID;
  }
  
  public void setCustID(String paramString)
  {
    this.custID = paramString;
  }
  
  public void setFIID(String paramString)
  {
    this.fiID = paramString;
  }
  
  public String getFIID()
  {
    return this.fiID;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetACHCompanies
 * JD-Core Version:    0.7.0.1
 */