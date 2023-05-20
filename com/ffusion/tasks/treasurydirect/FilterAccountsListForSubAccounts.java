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

public class FilterAccountsListForSubAccounts
  extends BaseTask
  implements TreasuryDirectTask
{
  private String aOJ = null;
  private String aOK = null;
  private String aON = "Business";
  private String aOM = "ListAccountsForSubAccountAdd";
  private String aOL = "SearchAccountsCollection";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    HashMap localHashMap = new HashMap();
    SecureUser localSecureUser = null;
    Accounts localAccounts1 = null;
    Account localAccount = null;
    Business localBusiness = null;
    Accounts localAccounts2 = null;
    this.error = 0;
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localBusiness = (Business)localHttpSession.getAttribute(this.aON);
    localAccounts1 = (Accounts)localHttpSession.getAttribute(this.aOL);
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (this.aOJ == null)
    {
      this.error = 80101;
      return this.taskErrorURL;
    }
    if (localBusiness == null)
    {
      this.error = 74;
      return this.taskErrorURL;
    }
    if (localAccounts1 == null)
    {
      this.error = 39;
    }
    else
    {
      localAccount = new Account();
      localAccount.setID(this.aOJ);
      localAccount.setRoutingNum(this.aOK);
      try
      {
        localAccounts2 = TreasuryDirect.filterAvailableSubAccounts(localSecureUser, localBusiness, localAccount, localAccounts1, localHashMap);
        localHttpSession.setAttribute(this.aOM, localAccounts2);
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public String getSubAccountsSessionName()
  {
    return this.aOM;
  }
  
  public void setSubAccountsSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aOM = "ListAccountsForSubAccountAdd";
    } else {
      this.aOM = paramString.trim();
    }
  }
  
  public String getSearchAccountsSessionName()
  {
    return this.aOL;
  }
  
  public void setSearchAccountsSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aOL = "SearchAccountsCollection";
    } else {
      this.aOL = paramString.trim();
    }
  }
  
  public String getBusinessSessionName()
  {
    return this.aON;
  }
  
  public void setBusinessSessionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aON = "Business";
    } else {
      this.aON = paramString.trim();
    }
  }
  
  public String getID()
  {
    return this.aOJ;
  }
  
  public void setID(String paramString)
  {
    this.aOJ = paramString;
  }
  
  public String getRoutingNum()
  {
    return this.aOK;
  }
  
  public void setRoutingNum(String paramString)
  {
    this.aOK = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.FilterAccountsListForSubAccounts
 * JD-Core Version:    0.7.0.1
 */