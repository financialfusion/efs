package com.ffusion.tasks.aggregation;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteAccount
  extends ModifyAccount
  implements Task
{
  private String nJ = "AggregatedAccount";
  private String nI = "AccountAggregationCollection";
  private String nK = "com.ffusion.services.AccountAggregation";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    com.ffusion.services.AccountAggregation localAccountAggregation = (com.ffusion.services.AccountAggregation)localHttpSession.getAttribute(this.nK);
    if (localHttpSession.getAttribute(this.nJ) == null)
    {
      this.error = 11009;
      str = this.taskErrorURL;
    }
    else if (localHttpSession.getAttribute(this.nI) == null)
    {
      this.error = 39;
      str = this.taskErrorURL;
    }
    else
    {
      this.error = deleteAccount(localAccountAggregation, localHttpSession);
      if (this.error == 0) {
        str = this.successURL;
      } else {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public int deleteAccount(com.ffusion.services.AccountAggregation paramAccountAggregation, HttpSession paramHttpSession)
  {
    this.error = 0;
    com.ffusion.beans.accounts.Account localAccount = (com.ffusion.beans.accounts.Account)paramHttpSession.getAttribute(this.nJ);
    jdMethod_for(localAccount, this);
    HashMap localHashMap = null;
    if (paramAccountAggregation != null)
    {
      localHashMap = new HashMap();
      localHashMap.put("SERVICE", paramAccountAggregation);
    }
    SecureUser localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
    com.ffusion.beans.aggregation.Account localAccount1 = null;
    try
    {
      localAccount1 = com.ffusion.csil.core.AccountAggregation.deleteAccount(localSecureUser, this, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
    }
    if (this.error == 0)
    {
      Accounts localAccounts = (Accounts)paramHttpSession.getAttribute(this.nI);
      localAccounts.removeByID(localAccount1.getID());
    }
    return this.error;
  }
  
  public void setAccountInSessionName(String paramString)
  {
    this.nJ = paramString;
  }
  
  public String getAccountInSessionName()
  {
    return this.nJ;
  }
  
  public void setServiceName(String paramString)
  {
    this.nK = paramString;
  }
  
  public String getServiceName()
  {
    return this.nK;
  }
  
  public void setAccountsInSessionName(String paramString)
  {
    this.nI = paramString;
  }
  
  public String getAccountsInSessionName()
  {
    return this.nI;
  }
  
  private void jdMethod_for(com.ffusion.beans.accounts.Account paramAccount, com.ffusion.beans.aggregation.Account paramAccount1)
  {
    if ((paramAccount1 == null) || (paramAccount == null)) {
      return;
    }
    paramAccount1.setLocale(paramAccount.getLocale());
    paramAccount1.setType(String.valueOf(com.ffusion.beans.aggregation.Account.getAggregationTypeFromAccountType(paramAccount.getTypeValue())));
    paramAccount1.setID(paramAccount.getNumber(), String.valueOf(paramAccount.getTypeValue()));
    paramAccount1.setStatus(paramAccount.getStatus());
    paramAccount1.setNickName(paramAccount.getNickName());
    paramAccount1.setNumber(paramAccount.getNumber());
    paramAccount1.setInstitutionName(paramAccount.getBankName());
    paramAccount1.setInstitutionID(paramAccount.getRoutingNum());
    paramAccount1.set("CURRENCY_CODE", paramAccount.getCurrencyCode());
    paramAccount1.set("COREACCOUNT", "0");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.DeleteAccount
 * JD-Core Version:    0.7.0.1
 */