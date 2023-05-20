package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.AssetAcctSummary;
import com.ffusion.beans.accounts.CreditCardAcctSummary;
import com.ffusion.beans.accounts.DepositAcctSummary;
import com.ffusion.beans.accounts.LoanAcctSummary;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.services.Banking3;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetMostRecentSummaries
  extends BaseTask
  implements Task
{
  String e4 = "BankingAccounts";
  String e5 = "AccountSummaries";
  String e3 = "P";
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public void setAccountsName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.e4 = "BankingAccounts";
    } else {
      this.e4 = paramString;
    }
  }
  
  public String getAccountsName()
  {
    return this.e4;
  }
  
  public void setSummariesName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.e5 = "AccountSummaries";
    } else {
      this.e5 = paramString;
    }
  }
  
  public String getSummariesName()
  {
    return this.e5;
  }
  
  public void setDataClassification(String paramString)
  {
    this.e3 = paramString;
  }
  
  public String getDataClassification()
  {
    return this.e3;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.e4);
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localAccounts == null) {
      this.error = 1001;
    } else if (localSecureUser == null) {
      this.error = 1037;
    } else {
      try
      {
        this.error = 0;
        HashMap localHashMap = new HashMap();
        localHashMap.put("SERVICE", localBanking3);
        localHashMap.put("DATA_CLASSIFICATION", this.e3);
        AccountSummaries localAccountSummaries1 = new AccountSummaries();
        Iterator localIterator = localAccounts.iterator();
        while (localIterator.hasNext())
        {
          Account localAccount = (Account)localIterator.next();
          if (localAccount != null)
          {
            GregorianCalendar localGregorianCalendar1 = new GregorianCalendar();
            localGregorianCalendar1.set(11, 0);
            localGregorianCalendar1.set(12, 0);
            localGregorianCalendar1.set(13, 0);
            localGregorianCalendar1.set(14, 0);
            GregorianCalendar localGregorianCalendar2 = new GregorianCalendar();
            localGregorianCalendar2.set(11, 23);
            localGregorianCalendar2.set(12, 59);
            localGregorianCalendar2.set(13, 59);
            localGregorianCalendar2.set(14, 999);
            int i = 0;
            int j = 0;
            while (j++ < 7)
            {
              AccountSummaries localAccountSummaries2 = Banking.getSummary(localSecureUser, localAccount, localGregorianCalendar1, localGregorianCalendar2, localHashMap);
              if (!localAccountSummaries2.isEmpty())
              {
                localAccountSummaries1.add(localAccountSummaries2.get(localAccountSummaries2.size() - 1));
                i = 1;
                break;
              }
              localGregorianCalendar1.add(5, -1);
              localGregorianCalendar2.add(5, -1);
            }
            if (i == 0)
            {
              int k = localAccount.getAccountGroup();
              int m = Account.getAccountSystemTypeFromGroup(k);
              Object localObject = null;
              switch (m)
              {
              case 1: 
                localObject = new DepositAcctSummary();
                break;
              case 2: 
                localObject = new AssetAcctSummary();
                break;
              case 4: 
                localObject = new CreditCardAcctSummary();
                break;
              case 3: 
                localObject = new LoanAcctSummary();
                break;
              default: 
                localObject = new AccountSummary();
              }
              ((AccountSummary)localObject).setAccountNumber(localAccount.getNumber());
              ((AccountSummary)localObject).setBankID(localAccount.getBankID());
              localAccountSummaries1.add(localObject);
            }
          }
        }
        localHttpSession.setAttribute(this.e5, localAccountSummaries1);
        str = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetMostRecentSummaries
 * JD-Core Version:    0.7.0.1
 */