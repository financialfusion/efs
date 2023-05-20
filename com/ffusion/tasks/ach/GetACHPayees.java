package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHPayees;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetACHPayees
  extends BaseTask
  implements Task
{
  protected boolean reload = false;
  protected String payeeName = "ACHPayees";
  protected String companyName = "ACHCOMPANY";
  protected String businessID = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ACHPayees localACHPayees = (ACHPayees)localHttpSession.getAttribute(this.payeeName);
    String str1 = null;
    this.error = 0;
    if (this.reload)
    {
      localACHPayees = null;
      localHttpSession.removeAttribute(this.payeeName);
      this.reload = false;
    }
    ACHCompany localACHCompany = null;
    String str2 = null;
    if ((this.companyName != null) && (this.companyName.trim().length() > 0)) {
      localACHCompany = (ACHCompany)localHttpSession.getAttribute(this.companyName);
    }
    if (localACHCompany != null) {
      str2 = localACHCompany.getID();
    }
    if (localACHPayees == null)
    {
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        if (this.businessID != null) {
          localACHPayees = ACH.getACHPayees(localSecureUser, this.businessID, str2, localHashMap);
        } else {
          localACHPayees = ACH.getACHPayees(localSecureUser, str2, localHashMap);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if (this.error == 0)
      {
        Collections.sort(localACHPayees);
        localHttpSession.setAttribute(this.payeeName, localACHPayees);
        str1 = this.successURL;
      }
      else
      {
        str1 = this.serviceErrorURL;
      }
    }
    else
    {
      str1 = this.successURL;
    }
    return str1;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setPayeeName(String paramString)
  {
    this.payeeName = paramString;
  }
  
  public void setCompanyName(String paramString)
  {
    this.companyName = paramString.trim();
  }
  
  public void setBusinessID(String paramString)
  {
    this.businessID = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetACHPayees
 * JD-Core Version:    0.7.0.1
 */