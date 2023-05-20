package com.ffusion.tasks.treasurydirect;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.tasks.BaseTask;
import com.ffusion.treasurydirect.TreasuryDirectUtil;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProcessAddedSubAccounts
  extends BaseTask
  implements TreasuryDirectTask
{
  private String aOT = "ListAccountsForSubAccountAdd";
  private String aOR = "SubAccountAddCollection";
  private String aOP = "ListSubAccountsForMasterAccountEdit";
  private boolean aOO = false;
  private boolean aOQ = false;
  private boolean aOS = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = null;
    Accounts localAccounts1 = null;
    Accounts localAccounts2 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    this.error = 0;
    localAccounts1 = (Accounts)localHttpSession.getAttribute(this.aOT);
    localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Accounts localAccounts3 = new Accounts(localSecureUser.getLocale());
    if (this.aOS) {
      localAccounts2 = (Accounts)localHttpSession.getAttribute(this.aOP);
    }
    if (localAccounts1 == null)
    {
      this.error = 39;
      return this.taskErrorURL;
    }
    if ((this.aOS) && (localAccounts2 == null))
    {
      this.error = 80102;
      return this.taskErrorURL;
    }
    Account localAccount = null;
    String str5 = null;
    for (int i = 0; i < localAccounts1.size(); i++)
    {
      str2 = (String)localHttpSession.getAttribute("SUB_ACCOUNT_ADD_" + i);
      if (str2 != null)
      {
        localAccount = new Account();
        localAccount.set((Account)localAccounts1.get(i));
        str3 = (String)localHttpSession.getAttribute("INCLUDE_ZBA_CREDIT_" + i);
        if (str3 != null) {
          localAccount.setIncludeZBACreditInRollup(true);
        } else {
          localAccount.setIncludeZBACreditInRollup(false);
        }
        str4 = (String)localHttpSession.getAttribute("INCLUDE_ZBA_DEBIT_" + i);
        if (str4 != null) {
          localAccount.setIncludeZBADebitInRollup(true);
        } else {
          localAccount.setIncludeZBADebitInRollup(false);
        }
        str5 = (String)localHttpSession.getAttribute("LOCATION_ID_" + i);
        str5 = str5 == null ? str5 : str5.trim();
        if (this.aOQ) {
          if (str5.length() > 0)
          {
            if (TreasuryDirectUtil.validateLocationID(str5))
            {
              localAccount.setLocationID(str5);
            }
            else
            {
              localAccount.setLocationID(null);
              if (this.error == 0)
              {
                this.error = 80103;
                str1 = this.taskErrorURL;
              }
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
        localAccounts3.add(localAccount);
      }
    }
    if (this.error != 0) {
      return str1;
    }
    if ((this.aOO) && (localAccounts3.isEmpty()))
    {
      this.error = 80112;
      str1 = this.taskErrorURL;
    }
    else if ((this.aOQ) && (!this.aOS) && (!TreasuryDirectUtil.hasUniqueLocationIds(localAccounts3)))
    {
      this.error = 80111;
      str1 = this.taskErrorURL;
    }
    else if ((this.aOQ) && (this.aOS) && (!jdMethod_int(localAccounts2, localAccounts3)))
    {
      this.error = 80111;
      str1 = this.taskErrorURL;
    }
    else
    {
      localHttpSession.setAttribute(this.aOR, localAccounts3);
    }
    return str1;
  }
  
  public String getAccountCollectionName()
  {
    return this.aOT;
  }
  
  public void setAccountCollectionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aOT = "ListAccountsForSubAccountAdd";
    } else {
      this.aOT = paramString.trim();
    }
  }
  
  public String getSubAccountsCollectionName()
  {
    return this.aOR;
  }
  
  public void setSubAccountsCollectionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aOR = "SubAccountAddCollection";
    } else {
      this.aOR = paramString.trim();
    }
  }
  
  public boolean getErrorOnEmptySubAccounts()
  {
    return this.aOO;
  }
  
  public void setErrorOnEmptySubAccounts(String paramString)
  {
    this.aOO = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean getValidateLocationIdsAgainstExistingSubAccounts()
  {
    return this.aOS;
  }
  
  public void setValidateLocationIdsAgainstExistingSubAccounts(String paramString)
  {
    this.aOS = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getExistingSubAccountsCollectionName()
  {
    return this.aOP;
  }
  
  public void setExistingSubAccountsCollectionName(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() == 0)) {
      this.aOP = "ListSubAccountsForMasterAccountEdit";
    } else {
      this.aOP = paramString.trim();
    }
  }
  
  public void setValidateLocationIds(String paramString)
  {
    this.aOQ = Boolean.valueOf(paramString).booleanValue();
  }
  
  private boolean jdMethod_int(Accounts paramAccounts1, Accounts paramAccounts2)
  {
    boolean bool = true;
    Accounts localAccounts = new Accounts(paramAccounts2.getLocale());
    Account localAccount = null;
    Iterator localIterator = paramAccounts1.iterator();
    while (localIterator.hasNext())
    {
      localAccount = (Account)localIterator.next();
      localAccounts.add(localAccount);
    }
    localIterator = paramAccounts2.iterator();
    while (localIterator.hasNext())
    {
      localAccount = (Account)localIterator.next();
      localAccounts.add(localAccount);
    }
    bool = TreasuryDirectUtil.hasUniqueLocationIds(localAccounts);
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.treasurydirect.ProcessAddedSubAccounts
 * JD-Core Version:    0.7.0.1
 */