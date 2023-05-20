package com.ffusion.tasks.accounts;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchAccounts
  extends BaseTask
  implements Task
{
  private String mL = "Accounts";
  private String mK = "Account";
  private String mM = "ResetAccts";
  private String mN = "-1";
  private String mJ = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount1 = (Account)localHttpSession.getAttribute(this.mK);
    if (localAccount1 == null)
    {
      this.error = 18007;
      str1 = this.taskErrorURL;
    }
    else if (localAccount1.getBankID() == null)
    {
      this.error = 18008;
      str1 = this.taskErrorURL;
    }
    else if (localAccount1.getBankID().equals(""))
    {
      this.error = 18008;
      str1 = this.taskErrorURL;
    }
    else
    {
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      HashMap localHashMap = new HashMap();
      try
      {
        int i = -1;
        try
        {
          i = Integer.parseInt(this.mN);
        }
        catch (NumberFormatException localNumberFormatException)
        {
          i = -1;
        }
        String str2 = (String)localHttpSession.getAttribute(this.mM);
        com.ffusion.beans.accounts.Accounts localAccounts1 = (com.ffusion.beans.accounts.Accounts)localHttpSession.getAttribute(this.mL);
        if ((localAccounts1 == null) || (str2 == null) || ("true".equalsIgnoreCase(str2)))
        {
          localAccounts1 = com.ffusion.csil.core.Accounts.searchAccounts(localSecureUser, localAccount1, i, localHashMap);
        }
        else
        {
          com.ffusion.beans.accounts.Accounts localAccounts2 = com.ffusion.csil.core.Accounts.searchAccounts(localSecureUser, localAccount1, i, localHashMap);
          Iterator localIterator1 = localAccounts2.iterator();
          while (localIterator1.hasNext())
          {
            Account localAccount2 = (Account)localIterator1.next();
            int j = 0;
            Iterator localIterator2 = localAccounts1.iterator();
            while (localIterator2.hasNext())
            {
              Account localAccount3 = (Account)localIterator2.next();
              if ((localAccount2.getNumber().equalsIgnoreCase(localAccount3.getNumber())) && (localAccount2.getRoutingNum().equalsIgnoreCase(localAccount3.getRoutingNum())))
              {
                j = 1;
                break;
              }
            }
            if (j == 0) {
              localAccounts1.add(localAccount2);
            }
          }
        }
        localHttpSession.setAttribute(this.mL, localAccounts1);
        this.mJ = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public void setAccountName(String paramString)
  {
    this.mK = paramString;
  }
  
  public void setAccountsName(String paramString)
  {
    this.mL = paramString;
  }
  
  public void setDirectoryId(String paramString)
  {
    this.mN = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accounts.SearchAccounts
 * JD-Core Version:    0.7.0.1
 */