package com.ffusion.tasks.treasurydirect;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.treasurydirect.TreasuryDirectUtil;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessSubAccounts
  extends BaseTask
  implements TreasuryDirectTask
{
  private String aOI = "ListSubAccountsForMasterAccountEdit";
  private boolean aOH = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = null;
    this.error = 0;
    localAccounts = (Accounts)localHttpSession.getAttribute(this.aOI);
    if (localAccounts == null)
    {
      this.error = 80102;
      return this.taskErrorURL;
    }
    String str2 = null;
    String str3 = null;
    String str4 = null;
    for (int i = 0; i < localAccounts.size(); i++)
    {
      Account localAccount = (Account)localAccounts.get(i);
      str2 = (String)localHttpSession.getAttribute("INCLUDE_ZBA_CREDIT_" + i);
      if (str2 != null) {
        localAccount.setIncludeZBACreditInRollup(true);
      } else {
        localAccount.setIncludeZBACreditInRollup(false);
      }
      str3 = (String)localHttpSession.getAttribute("INCLUDE_ZBA_DEBIT_" + i);
      if (str3 != null) {
        localAccount.setIncludeZBADebitInRollup(true);
      } else {
        localAccount.setIncludeZBADebitInRollup(false);
      }
      str4 = (String)localHttpSession.getAttribute("LOCATION_ID_" + i);
      str4 = str4 == null ? str4 : str4.trim();
      if (this.aOH)
      {
        if ((str4 != null) && (str4.length() > 0))
        {
          localAccount.setLocationID(str4);
          if ((!TreasuryDirectUtil.validateLocationID(str4)) && (this.error == 0))
          {
            this.error = 80103;
            str1 = this.taskErrorURL;
          }
        }
        else
        {
          localAccount.setLocationID(null);
          if (this.error == 0)
          {
            this.error = 80110;
            str1 = this.taskErrorURL;
          }
        }
      }
      else if ((str4 != null) && (str4.length() > 0)) {
        localAccount.setLocationID(str4);
      } else {
        localAccount.setLocationID(null);
      }
    }
    if (this.error != 0) {
      return str1;
    }
    if ((this.aOH) && (!TreasuryDirectUtil.hasUniqueLocationIds(localAccounts)))
    {
      this.error = 80111;
      return this.taskErrorURL;
    }
    return str1;
  }
  
  public String getAccountCollectionName()
  {
    return this.aOI;
  }
  
  public void setAccountCollectionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aOI = "ListSubAccountsForMasterAccountEdit";
    } else {
      this.aOI = paramString.trim();
    }
  }
  
  public void setValidateLocationIds(String paramString)
  {
    this.aOH = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.ProcessSubAccounts
 * JD-Core Version:    0.7.0.1
 */