package com.ffusion.tasks.banking;

import com.ffusion.beans.Balance;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.AssetAcctSummary;
import com.ffusion.beans.accounts.CreditCardAcctSummary;
import com.ffusion.beans.accounts.DepositAcctSummary;
import com.ffusion.beans.accounts.LoanAcctSummary;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AccountAggregation;
import com.ffusion.csil.core.Banking;
import com.ffusion.services.Banking3;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSummariesForAccountDate
  extends BaseTask
  implements Task
{
  String eE = "BankingAccounts";
  String eH = "AccountSummaries";
  String eC = "P";
  Date eB;
  String eF = "MM/dd/yyyy";
  boolean eD = false;
  boolean eG = false;
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public void setAllSummariesOnSameDate(String paramString)
  {
    this.eD = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAllSummariesOnSameDate()
  {
    return String.valueOf(this.eD);
  }
  
  public void setAccountsName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.eE = "BankingAccounts";
    } else {
      this.eE = paramString;
    }
  }
  
  public String getAccountsName()
  {
    return this.eE;
  }
  
  public void setSummariesName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.eH = "AccountSummaries";
    } else {
      this.eH = paramString;
    }
  }
  
  public String getSummariesName()
  {
    return this.eH;
  }
  
  public void setDataClassification(String paramString)
  {
    this.eC = paramString;
  }
  
  public String getDataClassification()
  {
    return this.eC;
  }
  
  public void setDateFormat(String paramString)
  {
    if (!paramString.equals(this.eF)) {
      this.eF = paramString;
    }
  }
  
  public String getDateFormat()
  {
    return this.eF;
  }
  
  public void setDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.eF))) {
      this.eB = null;
    } else {
      try
      {
        this.eB = DateFormatUtil.getFormatter(this.eF).parse(paramString);
      }
      catch (ParseException localParseException)
      {
        this.eB = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.eF + ")");
      }
    }
  }
  
  public String getDate()
  {
    String str = null;
    if (this.eB != null) {
      str = DateFormatUtil.getFormatter(this.eF).format(this.eB);
    }
    return str;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.eE);
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = BaseTask.getLocale(localHttpSession, localSecureUser);
    if (localAccounts == null) {
      this.error = 1001;
    } else if (localSecureUser == null) {
      this.error = 1037;
    } else {
      try
      {
        this.error = 0;
        HashMap localHashMap = new HashMap();
        localHashMap.put("DATA_CLASSIFICATION", this.eC);
        AccountSummaries localAccountSummaries = null;
        Object localObject1;
        Object localObject2;
        int i;
        Object localObject4;
        if ((this.eB == null) && (this.eD))
        {
          localObject1 = Calendar.getInstance();
          ((Calendar)localObject1).set(11, 0);
          ((Calendar)localObject1).set(12, 0);
          ((Calendar)localObject1).set(13, 0);
          ((Calendar)localObject1).set(14, 0);
          localObject2 = Calendar.getInstance();
          ((Calendar)localObject2).setTime(((Calendar)localObject1).getTime());
          ((Calendar)localObject2).add(5, 1);
          ((Calendar)localObject2).add(14, -1);
          i = 0;
          int j = 0;
          while (i++ < 7)
          {
            localAccountSummaries = new AccountSummaries();
            for (int m = 0; m < localAccounts.size(); m++)
            {
              com.ffusion.beans.accounts.Account localAccount = (com.ffusion.beans.accounts.Account)localAccounts.get(m);
              localObject4 = null;
              if ((localSecureUser.getBusinessID() != 0) || (localAccount.getCoreAccount().equals("1")))
              {
                localHashMap.put("SERVICE", localBanking3);
                localObject4 = Banking.getSummary(localSecureUser, localAccount, (Calendar)localObject1, (Calendar)localObject2, localHashMap);
              }
              else
              {
                if (!localAccount.getCoreAccount().equals("0")) {
                  continue;
                }
                com.ffusion.beans.aggregation.Account localAccount1 = AccountUtil.mapAccountsAccountBeanToAggregationAccountBean(localAccount, localLocale);
                localObject4 = AccountAggregation.getSummary(localSecureUser, localAccount1, (Calendar)localObject1, (Calendar)localObject2, localHashMap);
              }
              if (!((AccountSummaries)localObject4).isEmpty())
              {
                localAccountSummaries.add(((AccountSummaries)localObject4).get(((AccountSummaries)localObject4).size() - 1));
                j = 1;
                if (this.eG) {
                  updateAccountBalance(localAccount, (AccountSummary)((AccountSummaries)localObject4).get(((AccountSummaries)localObject4).size() - 1));
                }
              }
              else
              {
                int i2 = localAccount.getAccountGroup();
                int i3 = com.ffusion.beans.accounts.Account.getAccountSystemTypeFromGroup(i2);
                Object localObject5 = null;
                switch (i3)
                {
                case 1: 
                  localObject5 = new DepositAcctSummary();
                  break;
                case 2: 
                  localObject5 = new AssetAcctSummary();
                  break;
                case 4: 
                  localObject5 = new CreditCardAcctSummary();
                  break;
                case 3: 
                  localObject5 = new LoanAcctSummary();
                  break;
                default: 
                  localObject5 = new AccountSummary();
                }
                ((AccountSummary)localObject5).setAccountID(localAccount.getID());
                ((AccountSummary)localObject5).setAccountNumber(localAccount.getNumber());
                ((AccountSummary)localObject5).setBankID(localAccount.getBankID());
                ((AccountSummary)localObject5).setRoutingNumber(localAccount.getRoutingNum());
                localAccountSummaries.add(localObject5);
                if (this.eG) {
                  updateAccountBalance(localAccount, (AccountSummary)localObject5);
                }
              }
            }
            if (j != 0)
            {
              this.eB = ((Calendar)localObject1).getTime();
              break;
            }
            ((Calendar)localObject1).add(6, -1);
            ((Calendar)localObject2).add(6, -1);
          }
        }
        else
        {
          localAccountSummaries = new AccountSummaries();
          localObject1 = localAccounts.iterator();
          while (((Iterator)localObject1).hasNext())
          {
            localObject2 = (com.ffusion.beans.accounts.Account)((Iterator)localObject1).next();
            if (localObject2 != null)
            {
              i = 0;
              Calendar localCalendar1;
              Calendar localCalendar2;
              Object localObject3;
              if (this.eB == null)
              {
                localCalendar1 = Calendar.getInstance();
                localCalendar1.set(11, 0);
                localCalendar1.set(12, 0);
                localCalendar1.set(13, 0);
                localCalendar1.set(14, 0);
                localCalendar2 = Calendar.getInstance();
                localCalendar2.setTime(localCalendar1.getTime());
                localCalendar2.add(5, 1);
                localCalendar2.add(14, -1);
                int i1 = 0;
                while (i1++ < 7)
                {
                  localObject4 = null;
                  if ((localSecureUser.getBusinessID() != 0) || (((com.ffusion.beans.accounts.Account)localObject2).getCoreAccount().equals("1")))
                  {
                    localHashMap.put("SERVICE", localBanking3);
                    localObject4 = Banking.getSummary(localSecureUser, (com.ffusion.beans.accounts.Account)localObject2, localCalendar1, localCalendar2, localHashMap);
                  }
                  else
                  {
                    if (!((com.ffusion.beans.accounts.Account)localObject2).getCoreAccount().equals("0")) {
                      continue;
                    }
                    com.ffusion.beans.aggregation.Account localAccount2 = AccountUtil.mapAccountsAccountBeanToAggregationAccountBean((com.ffusion.beans.accounts.Account)localObject2, localLocale);
                    localObject4 = AccountAggregation.getSummary(localSecureUser, localAccount2, localCalendar1, localCalendar2, localHashMap);
                  }
                  if (!((AccountSummaries)localObject4).isEmpty())
                  {
                    localAccountSummaries.add(((AccountSummaries)localObject4).get(((AccountSummaries)localObject4).size() - 1));
                    i = 1;
                    if (!this.eG) {
                      break;
                    }
                    updateAccountBalance((com.ffusion.beans.accounts.Account)localObject2, (AccountSummary)((AccountSummaries)localObject4).get(((AccountSummaries)localObject4).size() - 1));
                    break;
                  }
                  localCalendar1.add(5, -1);
                  localCalendar2.add(5, -1);
                }
              }
              else
              {
                localCalendar1 = Calendar.getInstance();
                localCalendar1.setTime(this.eB);
                localCalendar1.set(11, 0);
                localCalendar1.set(12, 0);
                localCalendar1.set(13, 0);
                localCalendar1.set(14, 0);
                localCalendar2 = Calendar.getInstance();
                localCalendar2.setTime(localCalendar1.getTime());
                localCalendar2.add(5, 1);
                localCalendar2.add(14, -1);
                localObject3 = null;
                if ((localSecureUser.getBusinessID() != 0) || (((com.ffusion.beans.accounts.Account)localObject2).getCoreAccount().equals("1")))
                {
                  localHashMap.put("SERVICE", localBanking3);
                  localObject3 = Banking.getSummary(localSecureUser, (com.ffusion.beans.accounts.Account)localObject2, localCalendar1, localCalendar2, localHashMap);
                }
                else
                {
                  if (!((com.ffusion.beans.accounts.Account)localObject2).getCoreAccount().equals("0")) {
                    continue;
                  }
                  localObject4 = AccountUtil.mapAccountsAccountBeanToAggregationAccountBean((com.ffusion.beans.accounts.Account)localObject2, localLocale);
                  localObject3 = AccountAggregation.getSummary(localSecureUser, (com.ffusion.beans.aggregation.Account)localObject4, localCalendar1, localCalendar2, localHashMap);
                }
                if (!((AccountSummaries)localObject3).isEmpty())
                {
                  localAccountSummaries.add(((AccountSummaries)localObject3).get(((AccountSummaries)localObject3).size() - 1));
                  i = 1;
                  if (this.eG) {
                    updateAccountBalance((com.ffusion.beans.accounts.Account)localObject2, (AccountSummary)((AccountSummaries)localObject3).get(((AccountSummaries)localObject3).size() - 1));
                  }
                }
              }
              if (i == 0)
              {
                int k = ((com.ffusion.beans.accounts.Account)localObject2).getAccountGroup();
                int n = com.ffusion.beans.accounts.Account.getAccountSystemTypeFromGroup(k);
                localObject3 = null;
                switch (n)
                {
                case 1: 
                  localObject3 = new DepositAcctSummary();
                  break;
                case 2: 
                  localObject3 = new AssetAcctSummary();
                  break;
                case 4: 
                  localObject3 = new CreditCardAcctSummary();
                  break;
                case 3: 
                  localObject3 = new LoanAcctSummary();
                  break;
                default: 
                  localObject3 = new AccountSummary();
                }
                ((AccountSummary)localObject3).setAccountNumber(((com.ffusion.beans.accounts.Account)localObject2).getNumber());
                ((AccountSummary)localObject3).setBankID(((com.ffusion.beans.accounts.Account)localObject2).getBankID());
                ((AccountSummary)localObject3).setAccountID(((com.ffusion.beans.accounts.Account)localObject2).getID());
                ((AccountSummary)localObject3).setRoutingNumber(((com.ffusion.beans.accounts.Account)localObject2).getRoutingNum());
                localAccountSummaries.add(localObject3);
                if (this.eG) {
                  updateAccountBalance((com.ffusion.beans.accounts.Account)localObject2, (AccountSummary)localObject3);
                }
              }
            }
          }
        }
        localAccountSummaries.setSecureUser(localSecureUser);
        localAccounts.setSecureUser(localSecureUser);
        localHttpSession.setAttribute(this.eH, localAccountSummaries);
        str = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  protected void updateAccountBalance(com.ffusion.beans.accounts.Account paramAccount, AccountSummary paramAccountSummary)
  {
    int i = com.ffusion.beans.accounts.Account.getAccountSystemTypeFromGroup(paramAccount.getAccountGroup());
    int j = paramAccount.getTypeValue();
    Balance localBalance1;
    if ((i == 1) || (j == 12))
    {
      localBalance1 = new Balance(paramAccount.getLocale());
      Balance localBalance2 = new Balance(paramAccount.getLocale());
      Balance localBalance3 = new Balance(paramAccount.getLocale());
      localBalance1.setAmount(((DepositAcctSummary)paramAccountSummary).getCurrentLedger());
      localBalance2.setAmount(((DepositAcctSummary)paramAccountSummary).getCurrentAvailBal());
      localBalance3.setAmount(((DepositAcctSummary)paramAccountSummary).getClosingLedger());
      paramAccount.setCurrentBalance(localBalance1);
      paramAccount.setAvailableBalance(localBalance2);
      paramAccount.setClosingBalance(localBalance3);
    }
    else if (i == 2)
    {
      localBalance1 = new Balance(paramAccount.getLocale());
      localBalance1.setAmount(((AssetAcctSummary)paramAccountSummary).getMarketValue());
      paramAccount.setCurrentBalance(localBalance1);
    }
    else if (i == 4)
    {
      localBalance1 = new Balance(paramAccount.getLocale());
      localBalance1.setAmount(((CreditCardAcctSummary)paramAccountSummary).getCurrentBalance());
      paramAccount.setCurrentBalance(localBalance1);
    }
    else if (i == 3)
    {
      localBalance1 = new Balance(paramAccount.getLocale());
      localBalance1.setAmount(((LoanAcctSummary)paramAccountSummary).getCurrentBalance());
      paramAccount.setCurrentBalance(localBalance1);
    }
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public void setUpdateAccountBalances(String paramString)
  {
    if ((paramString != null) && (paramString.equalsIgnoreCase("true"))) {
      this.eG = true;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetSummariesForAccountDate
 * JD-Core Version:    0.7.0.1
 */