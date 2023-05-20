package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHCompany;
import com.ffusion.beans.ach.ACHOffsetAccount;
import com.ffusion.beans.ach.ACHOffsetAccounts;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetACHOffsetAccounts
  extends BaseTask
  implements Task
{
  protected String offsetAccountsName = "ACHOffsetAccounts";
  protected String companyName = "ACHCOMPANY";
  protected boolean reload = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ACHCompany localACHCompany = (ACHCompany)localHttpSession.getAttribute(this.companyName);
    if (localACHCompany == null) {
      this.error = 16506;
    } else if (getACHOffsetAccounts(localHttpSession, localACHCompany)) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean getACHOffsetAccounts(HttpSession paramHttpSession, ACHCompany paramACHCompany)
  {
    this.error = 0;
    ACHOffsetAccounts localACHOffsetAccounts = paramACHCompany.getAccts();
    if (this.reload)
    {
      localACHOffsetAccounts = null;
      this.reload = false;
    }
    if (localACHOffsetAccounts == null)
    {
      Locale localLocale = (Locale)paramHttpSession.getAttribute("java.util.Locale");
      ACHOffsetAccount localACHOffsetAccount = new ACHOffsetAccount(localLocale);
      HashMap localHashMap = new HashMap();
      SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        localACHOffsetAccounts = ACH.getOffsetAccounts(localSecureUser, localACHOffsetAccount, paramACHCompany, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
    }
    if (this.error == 0)
    {
      paramACHCompany.setAccts(localACHOffsetAccounts);
      paramHttpSession.setAttribute(this.offsetAccountsName, localACHOffsetAccounts);
    }
    return this.error == 0;
  }
  
  public final void setOffsetAccountsInSessionName(String paramString)
  {
    this.offsetAccountsName = paramString;
  }
  
  public final String getOffsetAccountsInSessionName()
  {
    return this.offsetAccountsName;
  }
  
  public final void setCompanyInSessionName(String paramString)
  {
    this.companyName = paramString.trim();
  }
  
  public final String getCompanyInSessionName()
  {
    return this.companyName;
  }
  
  public void setReload(String paramString)
  {
    this.reload = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetACHOffsetAccounts
 * JD-Core Version:    0.7.0.1
 */