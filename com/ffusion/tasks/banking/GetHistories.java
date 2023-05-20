package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
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

public class GetHistories
  extends BaseTask
  implements Task
{
  DateTime yn;
  DateTime yq;
  String yr;
  String ys = "Account";
  String yp = "AccountHistories";
  String yo = "P";
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public void setStartDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        this.yn = new DateTime(paramString, null, this.yr);
        if (this.yn != null)
        {
          this.yn.set(11, 0);
          this.yn.set(12, 0);
          this.yn.set(13, 0);
        }
      }
      else
      {
        this.yn = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getStartDate()
  {
    if (this.yn == null) {
      return null;
    }
    return this.yn.toString();
  }
  
  public void setEndDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        this.yq = new DateTime(paramString, null, this.yr);
        if (this.yq != null)
        {
          this.yq.set(11, 23);
          this.yq.set(12, 59);
          this.yq.set(13, 59);
        }
      }
      else
      {
        this.yq = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getEndDate()
  {
    if (this.yq == null) {
      return null;
    }
    return this.yq.toString();
  }
  
  public void setDateFormat(String paramString)
  {
    this.yr = paramString;
  }
  
  public String getDateFormat()
  {
    return this.yr;
  }
  
  public void setAccountName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.ys = "Account";
    } else {
      this.ys = paramString;
    }
  }
  
  public String getAccountName()
  {
    return this.ys;
  }
  
  public void setHistoriesName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.yp = "AccountHistories";
    } else {
      this.yp = paramString;
    }
  }
  
  public String getHistoriesName()
  {
    return this.yp;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.ys);
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
        localHashMap.put("DATA_CLASSIFICATION", this.yo);
        AccountHistories localAccountHistories = null;
        localAccountHistories = Banking.getHistory(localSecureUser, localAccount, this.yn, this.yq, localHashMap);
        localHttpSession.setAttribute(this.yp, localAccountHistories);
        str = this.successURL;
      }
      catch (CSILException localCSILException)
      {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setDataClassification(String paramString)
  {
    this.yo = paramString;
  }
  
  public String getDataClassification()
  {
    return this.yo;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetHistories
 * JD-Core Version:    0.7.0.1
 */