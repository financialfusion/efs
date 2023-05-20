package com.ffusion.tasks.aggregation;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.aggregation.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RefreshAccount
  extends BaseTask
  implements Task
{
  private String it = "AggregatedAccount";
  private String ir = "AccountAggregationCollection";
  private String iu = "com.ffusion.services.AccountAggregation";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    com.ffusion.services.AccountAggregation localAccountAggregation = (com.ffusion.services.AccountAggregation)localHttpSession.getAttribute(this.iu);
    if (localHttpSession.getAttribute(this.it) == null)
    {
      this.error = 11009;
      str = this.taskErrorURL;
    }
    else if (localHttpSession.getAttribute(this.ir) == null)
    {
      this.error = 39;
      str = this.taskErrorURL;
    }
    else
    {
      this.error = refreshAccount(localAccountAggregation, localHttpSession);
      if (this.error == 0) {
        str = this.successURL;
      } else {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public int refreshAccount(com.ffusion.services.AccountAggregation paramAccountAggregation, HttpSession paramHttpSession)
  {
    int i = 0;
    Account localAccount = (Account)paramHttpSession.getAttribute(this.it);
    HashMap localHashMap = null;
    if (paramAccountAggregation != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", paramAccountAggregation);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    try
    {
      localAccount = com.ffusion.csil.core.AccountAggregation.refreshAccount(localSecureUser, localAccount, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      i = MapError.mapError(localCSILException);
    }
    return i;
  }
  
  public void setAccountInSessionName(String paramString)
  {
    this.it = paramString;
  }
  
  public String getAccountInSessionName()
  {
    return this.it;
  }
  
  public void setServiceName(String paramString)
  {
    this.iu = paramString;
  }
  
  public String getServiceName()
  {
    return this.iu;
  }
  
  public void setAccountsInSessionName(String paramString)
  {
    this.ir = paramString;
  }
  
  public String getAccountsInSessionName()
  {
    return this.ir;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.RefreshAccount
 * JD-Core Version:    0.7.0.1
 */