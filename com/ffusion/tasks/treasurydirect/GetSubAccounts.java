package com.ffusion.tasks.treasurydirect;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.business.Business;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.TreasuryDirect;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSubAccounts
  extends BaseTask
  implements TreasuryDirectTask
{
  private String aO3 = null;
  private String aO4 = null;
  private String aO6 = "Business";
  private String aO5 = "ListSubAccountsForMasterAccountEdit";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = null;
    Account localAccount = null;
    Business localBusiness = null;
    Accounts localAccounts = null;
    this.error = 0;
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localBusiness = (Business)localHttpSession.getAttribute(this.aO6);
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (this.aO3 == null)
    {
      this.error = 80101;
      return this.taskErrorURL;
    }
    if (localBusiness == null)
    {
      this.error = 74;
      return this.taskErrorURL;
    }
    localAccount = new Account();
    localAccount.setID(this.aO3);
    if (this.aO4 != null) {
      localAccount.setRoutingNum(this.aO4);
    }
    try
    {
      localAccounts = TreasuryDirect.getSubAccounts(localSecureUser, localBusiness, localAccount, localHashMap);
      localHttpSession.setAttribute(this.aO5, localAccounts);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  public String getSubAccountsSessionName()
  {
    return this.aO5;
  }
  
  public void setSubAccountsSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aO5 = "ListSubAccountsForMasterAccountEdit";
    } else {
      this.aO5 = paramString.trim();
    }
  }
  
  public String getBusinessSessionName()
  {
    return this.aO6;
  }
  
  public void setBusinessSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aO6 = "Business";
    } else {
      this.aO6 = paramString.trim();
    }
  }
  
  public String getID()
  {
    return this.aO3;
  }
  
  public void setID(String paramString)
  {
    this.aO3 = paramString;
  }
  
  public String getRoutingNum()
  {
    return this.aO4;
  }
  
  public void setRoutingNum(String paramString)
  {
    this.aO4 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.GetSubAccounts
 * JD-Core Version:    0.7.0.1
 */