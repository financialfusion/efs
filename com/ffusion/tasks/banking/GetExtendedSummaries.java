package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.ExtendedAccountSummaries;
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

public class GetExtendedSummaries
  extends BaseTask
  implements Task
{
  DateTime xF;
  DateTime xG;
  String xH;
  String xJ = "Account";
  String xI = "ExtAccountSummaries";
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public void setStartDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        this.xF = new DateTime(paramString, null, this.xH);
        if (this.xF != null)
        {
          this.xF.set(11, 0);
          this.xF.set(12, 0);
          this.xF.set(13, 0);
        }
      }
      else
      {
        this.xF = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getStartDate()
  {
    if (this.xF == null) {
      return null;
    }
    return this.xF.toString();
  }
  
  public void setEndDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        this.xG = new DateTime(paramString, null, this.xH);
        if (this.xG != null)
        {
          this.xG.set(11, 23);
          this.xG.set(12, 59);
          this.xG.set(13, 59);
        }
      }
      else
      {
        this.xG = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getEndDate()
  {
    if (this.xG == null) {
      return null;
    }
    return this.xG.toString();
  }
  
  public void setDateFormat(String paramString)
  {
    this.xH = paramString;
  }
  
  public String getDateFormat()
  {
    return this.xH;
  }
  
  public void setAccountName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.xJ = "Account";
    } else {
      this.xJ = paramString;
    }
  }
  
  public String getAccountName()
  {
    return this.xJ;
  }
  
  public void setSummariesName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.xI = "AccountSummaries";
    } else {
      this.xI = paramString;
    }
  }
  
  public String getSummariesName()
  {
    return this.xI;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.xJ);
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
        ExtendedAccountSummaries localExtendedAccountSummaries = null;
        localExtendedAccountSummaries = Banking.getExtendedSummary(localSecureUser, localAccount, this.xF, this.xG, localHashMap);
        localHttpSession.setAttribute(this.xI, localExtendedAccountSummaries);
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
 * Qualified Name:     com.ffusion.tasks.banking.GetExtendedSummaries
 * JD-Core Version:    0.7.0.1
 */