package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.services.Banking3;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetNumTransactions
  extends BaseTask
  implements Task
{
  private DateTime x6;
  private DateTime x8;
  private String x4 = "MM/dd/yyyy";
  private String yc = "Account";
  private int x7 = 0;
  private String ya = "P";
  private int x5;
  private String x9 = null;
  private Properties yb = new Properties();
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public void setStartDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        this.x6 = new DateTime(paramString, null, this.x4);
        if (this.x6 != null)
        {
          this.x6.set(11, 0);
          this.x6.set(12, 0);
          this.x6.set(13, 0);
        }
      }
      else
      {
        this.x6 = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getStartDate()
  {
    if (this.x6 == null) {
      return null;
    }
    return this.x6.toString();
  }
  
  public void setEndDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        this.x8 = new DateTime(paramString, null, this.x4);
        if (this.x8 != null)
        {
          this.x8.set(11, 23);
          this.x8.set(12, 59);
          this.x8.set(13, 59);
        }
      }
      else
      {
        this.x8 = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getEndDate()
  {
    if (this.x8 == null) {
      return null;
    }
    return this.x8.toString();
  }
  
  public void setDateFormat(String paramString)
  {
    this.x4 = paramString;
  }
  
  public String getDateFormat()
  {
    return this.x4;
  }
  
  public void setAccountName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.yc = "Account";
    } else {
      this.yc = paramString;
    }
  }
  
  public String getAccountName()
  {
    return this.yc;
  }
  
  public int getNumTransactions()
  {
    return this.x7;
  }
  
  public int getRunningTotal()
  {
    return this.x5;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.yc);
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localAccount == null) {
      localAccount = (Account)paramHttpServletRequest.getAttribute(this.yc);
    }
    if (localAccount == null) {
      this.error = 1002;
    } else if (localSecureUser == null) {
      this.error = 1037;
    } else {
      try
      {
        this.error = 0;
        HashMap localHashMap = new HashMap();
        localHashMap.put("SERVICE", localBanking3);
        localHashMap.put("DATA_CLASSIFICATION", this.ya);
        this.x7 = Banking.getNumTransactions(localSecureUser, localAccount, this.x6, this.x8, this.yb, localHashMap);
        this.x5 += this.x7;
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
  
  public void setDataClassification(String paramString)
  {
    this.ya = paramString;
  }
  
  public String getDataClassification()
  {
    return this.ya;
  }
  
  public void setClearSearchCriteria(String paramString)
  {
    this.yb.clear();
  }
  
  public void setSearchCriteriaKey(String paramString)
  {
    this.x9 = paramString;
  }
  
  public void setSearchCriteriaValue(String paramString)
  {
    if (this.x9 == null) {
      return;
    }
    if ((paramString != null) && (paramString.length() > 0)) {
      this.yb.setProperty(this.x9, paramString);
    } else {
      this.yb.remove(this.x9);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetNumTransactions
 * JD-Core Version:    0.7.0.1
 */