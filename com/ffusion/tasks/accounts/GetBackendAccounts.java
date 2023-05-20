package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBackendAccounts
  extends BaseTask
  implements Task
{
  private String mU = "com.ffusion.services.Banking";
  private String mT = "Accounts";
  private String mS = "";
  private String mV = "Business";
  private String mR = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Business localBusiness = (Business)localHttpSession.getAttribute(this.mV);
    if (localBusiness == null)
    {
      this.error = 4104;
      str = this.taskErrorURL;
    }
    else
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      HashMap localHashMap = new HashMap();
      try
      {
        Object localObject = localHttpSession.getAttribute(this.mU);
        com.ffusion.beans.accounts.Accounts localAccounts1 = com.ffusion.csil.core.Accounts.getBackendAccounts(localSecureUser, localBusiness, localObject, localHashMap);
        if (this.mS.length() > 0)
        {
          com.ffusion.beans.accounts.Accounts localAccounts2 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.mS);
          Iterator localIterator = localAccounts2.iterator();
          while (localIterator.hasNext())
          {
            Account localAccount = (Account)localIterator.next();
            localAccounts1.remove(localAccounts1.getByIDAndBankIDAndRoutingNum(localAccount.getID(), localAccount.getBankID(), localAccount.getRoutingNum()));
          }
        }
        localHttpSession.setAttribute(this.mT, localAccounts1);
        this.mR = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setBusinessName(String paramString)
  {
    this.mV = paramString;
  }
  
  public void setAccountsName(String paramString)
  {
    this.mT = paramString;
  }
  
  public void setAccountsDeltaName(String paramString)
  {
    this.mS = paramString;
  }
  
  public void setBankingServiceSessionName(String paramString)
  {
    this.mU = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.GetBackendAccounts
 * JD-Core Version:    0.7.0.1
 */