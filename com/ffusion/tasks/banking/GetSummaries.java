package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Banking;
import com.ffusion.services.Banking3;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSummaries
  extends BaseTask
  implements Task
{
  DateTime xX;
  DateTime xZ;
  String x0;
  String x2 = "Account";
  String x1 = "AccountSummaries";
  String xY = "P";
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public void setStartDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        this.xX = new DateTime(paramString, null, this.x0);
        if (this.xX != null)
        {
          this.xX.set(11, 0);
          this.xX.set(12, 0);
          this.xX.set(13, 0);
        }
      }
      else
      {
        this.xX = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getStartDate()
  {
    if (this.xX == null) {
      return null;
    }
    return this.xX.toString();
  }
  
  public void setEndDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        this.xZ = new DateTime(paramString, null, this.x0);
        if (this.xZ != null)
        {
          this.xZ.set(11, 23);
          this.xZ.set(12, 59);
          this.xZ.set(13, 59);
        }
      }
      else
      {
        this.xZ = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getEndDate()
  {
    if (this.xZ == null) {
      return null;
    }
    return this.xZ.toString();
  }
  
  public void setDateFormat(String paramString)
  {
    this.x0 = paramString;
  }
  
  public String getDateFormat()
  {
    return this.x0;
  }
  
  public void setAccountName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.x2 = "Account";
    } else {
      this.x2 = paramString;
    }
  }
  
  public String getAccountName()
  {
    return this.x2;
  }
  
  public void setSummariesName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.x1 = "AccountSummaries";
    } else {
      this.x1 = paramString;
    }
  }
  
  public String getSummariesName()
  {
    return this.x1;
  }
  
  public void setDataClassification(String paramString)
  {
    this.xY = paramString;
  }
  
  public String getDataClassification()
  {
    return this.xY;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.x2);
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
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
        localHashMap.put("DATA_CLASSIFICATION", this.xY);
        AccountSummaries localAccountSummaries = null;
        localAccountSummaries = Banking.getSummary(localSecureUser, localAccount, this.xX, this.xZ, localHashMap);
        localHttpSession.setAttribute(this.x1, localAccountSummaries);
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
 * Qualified Name:     com.ffusion.tasks.banking.GetSummaries
 * JD-Core Version:    0.7.0.1
 */