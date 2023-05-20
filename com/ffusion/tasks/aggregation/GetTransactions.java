package com.ffusion.tasks.aggregation;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTransactions
  extends BaseTask
  implements Task
{
  private String nO = "AccountAggregationCollection";
  private String nP = "AggregatedAccount";
  private String nQ = "com.ffusion.services.AccountAggregation";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    com.ffusion.beans.accounts.Account localAccount = (com.ffusion.beans.accounts.Account)localHttpSession.getAttribute(this.nP);
    com.ffusion.beans.aggregation.Account localAccount1 = AccountUtil.mapAccountsAccountBeanToAggregationAccountBean(localAccount, getLocale(localHttpSession, localSecureUser));
    com.ffusion.services.AccountAggregation localAccountAggregation = (com.ffusion.services.AccountAggregation)localHttpSession.getAttribute(this.nQ);
    if (localAccount1 == null) {
      this.error = 11009;
    } else if (getTransactions(localSecureUser, localAccountAggregation, localAccount1)) {
      str = this.successURL;
    } else {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  protected boolean getTransactions(SecureUser paramSecureUser, com.ffusion.services.AccountAggregation paramAccountAggregation, com.ffusion.beans.aggregation.Account paramAccount)
  {
    this.error = 0;
    if (!paramAccount.getDownloadedItems())
    {
      HashMap localHashMap = null;
      if (paramAccountAggregation != null)
      {
        localHashMap = new HashMap();
        localHashMap.put("SERVICE", paramAccountAggregation);
      }
      try
      {
        com.ffusion.csil.core.AccountAggregation.getTransactions(paramSecureUser, paramAccount, localHashMap);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
      }
      if (this.error == 0) {
        paramAccount.setDownloadedItems(true);
      }
    }
    return this.error == 0;
  }
  
  public void setAccountsInSessionName(String paramString)
  {
    this.nO = paramString;
  }
  
  public String getAccountsInSessionName()
  {
    return this.nO;
  }
  
  public void setAccountInSessionName(String paramString)
  {
    this.nP = paramString;
  }
  
  public String getAccountInSessionName()
  {
    return this.nP;
  }
  
  public void setServiceName(String paramString)
  {
    this.nQ = paramString;
  }
  
  public String getServiceName()
  {
    return this.nQ;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.GetTransactions
 * JD-Core Version:    0.7.0.1
 */