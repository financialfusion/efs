package com.ffusion.tasks.treasurydirect;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessAddedMasterAccounts
  extends BaseTask
  implements TreasuryDirectTask
{
  private String aOw = "ListAccountsForMasterAccountAdd";
  private String aOv = "MasterAccountAddCollection";
  private boolean aOu = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = null;
    com.ffusion.beans.accounts.Accounts localAccounts1 = null;
    com.ffusion.beans.accounts.Accounts localAccounts2 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    this.error = 0;
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    localAccounts1 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.aOw);
    localAccounts2 = new com.ffusion.beans.accounts.Accounts(localSecureUser.getLocale());
    if (localSecureUser == null)
    {
      this.error = 38;
      return this.taskErrorURL;
    }
    if (localAccounts1 == null)
    {
      this.error = 39;
      return this.taskErrorURL;
    }
    Account localAccount1 = null;
    Account localAccount2 = null;
    for (int i = 0; i < localAccounts1.size(); i++)
    {
      str2 = (String)localHttpSession.getAttribute("ADD_MASTER_" + i);
      if (str2 != null)
      {
        localAccount1 = (Account)localAccounts1.get(i);
        try
        {
          com.ffusion.csil.core.Accounts.getAccountAddress(localSecureUser, localAccount1, new HashMap());
        }
        catch (CSILException localCSILException)
        {
          this.error = MapError.mapError(localCSILException);
          return this.serviceErrorURL;
        }
        localAccount2 = new Account();
        localAccount2.set(localAccount1);
        localAccount2.setMaster(true);
        str3 = (String)localHttpSession.getAttribute("INCLUDE_ZBA_CREDIT_" + i);
        if (str3 != null) {
          localAccount2.setIncludeZBACreditInRollup(true);
        } else {
          localAccount2.setIncludeZBACreditInRollup(false);
        }
        str4 = (String)localHttpSession.getAttribute("INCLUDE_ZBA_DEBIT_" + i);
        if (str4 != null) {
          localAccount2.setIncludeZBADebitInRollup(true);
        } else {
          localAccount2.setIncludeZBADebitInRollup(false);
        }
        str5 = (String)localHttpSession.getAttribute("WITHHOLD_NON_ZBA_BAL_SUBS_" + i);
        if (str5 != null) {
          localAccount2.setWithholdNonZeroBalanceSubAccounts(true);
        } else {
          localAccount2.setWithholdNonZeroBalanceSubAccounts(false);
        }
        localAccounts2.add(localAccount2);
      }
    }
    if ((this.aOu) && (localAccounts2.size() == 0))
    {
      str1 = this.taskErrorURL;
      this.error = 80109;
    }
    else
    {
      localHttpSession.setAttribute(this.aOv, localAccounts2);
    }
    return str1;
  }
  
  public String getAccountCollectionName()
  {
    return this.aOw;
  }
  
  public void setAccountCollectionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aOw = "ListAccountsForMasterAccountAdd";
    } else {
      this.aOw = paramString.trim();
    }
  }
  
  public String getMasterAccountsCollectionName()
  {
    return this.aOv;
  }
  
  public void setMasterAccountsCollectionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aOv = "MasterAccountAddCollection";
    } else {
      this.aOv = paramString.trim();
    }
  }
  
  public boolean getErrorOnEmptyMasterAccounts()
  {
    return this.aOu;
  }
  
  public void setErrorOnEmptyMasterAccounts(String paramString)
  {
    this.aOu = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.ProcessAddedMasterAccounts
 * JD-Core Version:    0.7.0.1
 */