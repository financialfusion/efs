package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountHistory;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
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

public class GetHistoriesForAccountDate
  extends BaseTask
  implements Task
{
  String zh = "BankingAccounts";
  String zf = "AccountSummaries";
  String ze = "P";
  Date zd;
  String zi = "MM/dd/yyyy";
  boolean zg = false;
  protected int _numberDaysInPast = 7;
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public void setAllHistoriesOnSameDate(String paramString)
  {
    this.zg = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getAllHistoriesOnSameDate()
  {
    return String.valueOf(this.zg);
  }
  
  public void setAccountsName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.zh = "BankingAccounts";
    } else {
      this.zh = paramString;
    }
  }
  
  public String getAccountsName()
  {
    return this.zh;
  }
  
  public void setHistoriesName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.zf = "AccountSummaries";
    } else {
      this.zf = paramString;
    }
  }
  
  public String getHistoriesName()
  {
    return this.zf;
  }
  
  public void setDataClassification(String paramString)
  {
    this.ze = paramString;
  }
  
  public String getDataClassification()
  {
    return this.ze;
  }
  
  public void setDateFormat(String paramString)
  {
    if (!paramString.equals(this.zi)) {
      this.zi = paramString;
    }
  }
  
  public String getDateFormat()
  {
    return this.zi;
  }
  
  public void setDate(String paramString)
  {
    if ((paramString.length() == 0) || (paramString.equalsIgnoreCase(this.zi))) {
      this.zd = null;
    } else {
      try
      {
        this.zd = DateFormatUtil.getFormatter(this.zi).parse(paramString);
      }
      catch (ParseException localParseException)
      {
        this.zd = null;
        System.err.println("The given date string \"" + paramString + "\" cannot be parsed using the pattern " + "provided (" + this.zi + ")");
      }
    }
  }
  
  public String getDate()
  {
    String str = null;
    if (this.zd != null) {
      str = DateFormatUtil.getFormatter(this.zi).format(this.zd);
    }
    return str;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(this.zh);
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Locale localLocale = BaseTask.getLocale(localHttpSession, localSecureUser);
    if (localAccounts == null)
    {
      this.error = 1001;
      return str;
    }
    if (localSecureUser == null)
    {
      this.error = 1037;
      return str;
    }
    try
    {
      this.error = 0;
      HashMap localHashMap = new HashMap();
      localHashMap.put("SERVICE", localBanking3);
      localHashMap.put("DATA_CLASSIFICATION", this.ze);
      AccountHistories localAccountHistories = null;
      Object localObject1;
      Object localObject2;
      int i;
      AccountHistory localAccountHistory2;
      if ((this.zd == null) && (this.zg))
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
        while (i++ < getNumberDaysInPast())
        {
          localAccountHistories = new AccountHistories();
          for (int k = 0; k < localAccounts.size(); k++)
          {
            Account localAccount = (Account)localAccounts.get(k);
            localAccountHistory2 = jdMethod_for(localSecureUser, localAccount, (Calendar)localObject1, (Calendar)localObject2, localHashMap);
            if (localAccountHistory2 != null)
            {
              localAccountHistories.add(localAccountHistory2);
              j = 1;
            }
            else
            {
              AccountHistory localAccountHistory3 = new AccountHistory();
              localAccountHistory3.setAccountID(localAccount.getID());
              localAccountHistory3.setAccountNumber(localAccount.getNumber());
              localAccountHistory3.setBankID(localAccount.getBankID());
              localAccountHistory3.setRoutingNumber(localAccount.getRoutingNum());
              localAccountHistories.add(localAccountHistory3);
            }
          }
          if (j != 0)
          {
            this.zd = ((Calendar)localObject1).getTime();
            break;
          }
          ((Calendar)localObject1).add(6, -1);
          ((Calendar)localObject2).add(6, -1);
        }
      }
      else
      {
        localAccountHistories = new AccountHistories();
        localObject1 = localAccounts.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (Account)((Iterator)localObject1).next();
          if (localObject2 != null)
          {
            i = 0;
            Object localObject3;
            Calendar localCalendar;
            if (this.zd == null)
            {
              localObject3 = Calendar.getInstance();
              ((Calendar)localObject3).set(11, 0);
              ((Calendar)localObject3).set(12, 0);
              ((Calendar)localObject3).set(13, 0);
              ((Calendar)localObject3).set(14, 0);
              localCalendar = Calendar.getInstance();
              localCalendar.setTime(((Calendar)localObject3).getTime());
              localCalendar.add(5, 1);
              localCalendar.add(14, -1);
              int m = 0;
              while (m++ < this._numberDaysInPast)
              {
                localAccountHistory2 = jdMethod_for(localSecureUser, (Account)localObject2, (Calendar)localObject3, localCalendar, localHashMap);
                if (localAccountHistory2 != null)
                {
                  localAccountHistories.add(localAccountHistory2);
                  i = 1;
                  break;
                }
                ((Calendar)localObject3).add(5, -1);
                localCalendar.add(5, -1);
              }
            }
            else
            {
              localObject3 = Calendar.getInstance();
              ((Calendar)localObject3).setTime(this.zd);
              ((Calendar)localObject3).set(11, 0);
              ((Calendar)localObject3).set(12, 0);
              ((Calendar)localObject3).set(13, 0);
              ((Calendar)localObject3).set(14, 0);
              localCalendar = Calendar.getInstance();
              localCalendar.setTime(((Calendar)localObject3).getTime());
              localCalendar.add(5, 1);
              localCalendar.add(14, -1);
              AccountHistory localAccountHistory1 = jdMethod_for(localSecureUser, (Account)localObject2, (Calendar)localObject3, localCalendar, localHashMap);
              if (localAccountHistory1 != null)
              {
                localAccountHistories.add(localAccountHistory1);
                i = 1;
              }
            }
            if (i == 0)
            {
              localObject3 = new AccountHistory();
              ((AccountHistory)localObject3).setAccountNumber(((Account)localObject2).getNumber());
              ((AccountHistory)localObject3).setBankID(((Account)localObject2).getBankID());
              ((AccountHistory)localObject3).setAccountID(((Account)localObject2).getID());
              ((AccountHistory)localObject3).setRoutingNumber(((Account)localObject2).getRoutingNum());
              localAccountHistories.add(localObject3);
            }
          }
        }
      }
      localAccounts.setSecureUser(localSecureUser);
      localHttpSession.setAttribute(this.zf, localAccountHistories);
      str = this.successURL;
    }
    catch (CSILException localCSILException)
    {
      str = this.serviceErrorURL;
    }
    return str;
  }
  
  private AccountHistory jdMethod_for(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    AccountHistories localAccountHistories = null;
    if (paramAccount.getCoreAccount().equals("1")) {
      localAccountHistories = Banking.getHistory(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    } else if (!paramAccount.getCoreAccount().equals("0")) {}
    AccountHistory localAccountHistory = null;
    if ((localAccountHistories != null) && (!localAccountHistories.isEmpty())) {
      localAccountHistory = (AccountHistory)localAccountHistories.get(localAccountHistories.size() - 1);
    }
    return localAccountHistory;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  protected void setNumberDaysInPast(int paramInt)
  {
    this._numberDaysInPast = paramInt;
  }
  
  protected int getNumberDaysInPast()
  {
    return this._numberDaysInPast;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetHistoriesForAccountDate
 * JD-Core Version:    0.7.0.1
 */