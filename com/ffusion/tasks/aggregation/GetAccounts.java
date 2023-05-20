package com.ffusion.tasks.aggregation;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.aggregation.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetAccounts
  extends BaseTask
  implements Task
{
  private String n5 = "com.ffusion.services.AccountAggregation";
  private String n3 = "AccountAggregationCollection";
  private boolean n4 = false;
  private HashMap n6;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = null;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.n3);
    if ((localAccounts == null) || (this.n4))
    {
      Locale localLocale = (Locale)localHttpSession.getAttribute("java.util.Locale");
      localAccounts = new Accounts(localLocale);
      com.ffusion.services.AccountAggregation localAccountAggregation = (com.ffusion.services.AccountAggregation)localHttpSession.getAttribute(this.n5);
      HashMap localHashMap = new HashMap();
      if (localAccountAggregation != null) {
        localHashMap.put("SERVICE", localAccountAggregation);
      }
      localHashMap.put("ACCOUNTS", localAccounts);
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      try
      {
        localAccounts = com.ffusion.csil.core.AccountAggregation.getAccounts(localSecureUser, this.n6, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
      if (this.error == 0)
      {
        localHttpSession.setAttribute(this.n3, localAccounts);
        str = this.successURL;
      }
    }
    else
    {
      str = this.successURL;
    }
    return str;
  }
  
  public void setAccountReload(String paramString)
  {
    this.n4 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setAccountsInSessionName(String paramString)
  {
    this.n3 = paramString;
  }
  
  public String getAccountsInSessionName()
  {
    return this.n3;
  }
  
  public void setServiceName(String paramString)
  {
    this.n5 = paramString;
  }
  
  public String getServiceName()
  {
    return this.n5;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.GetAccounts
 * JD-Core Version:    0.7.0.1
 */