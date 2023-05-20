package com.ffusion.tasks.banking;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetTransactions
  extends BaseTask
  implements Task
{
  protected DateTime startDate;
  protected DateTime endDate;
  protected Locale locale;
  private String e9 = "SHORT";
  protected String bankingServiceName = "com.ffusion.services.Banking";
  protected String _dataClassification = "P";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute("Account");
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)localHttpSession.getAttribute(this.bankingServiceName);
    if (localAccount == null)
    {
      this.error = 1002;
    }
    else
    {
      this.error = 0;
      if (getTransactions(localHttpSession, localBanking, localAccount)) {
        str = this.successURL;
      } else {
        str = this.serviceErrorURL;
      }
    }
    return str;
  }
  
  public void setStartDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        this.startDate = new DateTime(paramString, this.locale, this.e9);
        if (this.startDate != null)
        {
          this.startDate.set(11, 0);
          this.startDate.set(12, 0);
          this.startDate.set(13, 0);
        }
      }
      else
      {
        this.startDate = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException1)
    {
      try
      {
        if ((paramString != null) && (paramString.length() > 0))
        {
          this.startDate = new DateTime(paramString, this.locale);
          if (this.startDate != null)
          {
            this.startDate.set(11, 0);
            this.startDate.set(12, 0);
            this.startDate.set(13, 0);
          }
        }
        else
        {
          this.startDate = null;
        }
      }
      catch (InvalidDateTimeException localInvalidDateTimeException2) {}
    }
  }
  
  public String getStartDate()
  {
    if (this.startDate != null) {
      return this.startDate.toString();
    }
    return null;
  }
  
  public void setEndDate(String paramString)
  {
    try
    {
      if ((paramString != null) && (paramString.length() > 0))
      {
        this.endDate = new DateTime(paramString, this.locale, this.e9);
        if (this.endDate != null)
        {
          this.endDate.set(11, 23);
          this.endDate.set(12, 59);
          this.endDate.set(13, 59);
        }
      }
      else
      {
        this.endDate = null;
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getEndDate()
  {
    if (this.endDate != null) {
      return this.endDate.toString();
    }
    return null;
  }
  
  public void setDateFormat(String paramString)
  {
    this.e9 = paramString;
  }
  
  protected boolean getTransactions(HttpSession paramHttpSession, com.ffusion.services.Banking paramBanking, Account paramAccount)
  {
    this.error = 0;
    HashMap localHashMap;
    SecureUser localSecureUser;
    if (!paramAccount.getDownloadedItems())
    {
      localHashMap = new HashMap();
      localHashMap.put("DATA_CLASSIFICATION", this._dataClassification);
      if (paramBanking != null) {
        localHashMap.put("SERVICE", paramBanking);
      }
      localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      synchronized (this)
      {
        try
        {
          com.ffusion.csil.core.Banking.getTransactions(localSecureUser, paramAccount, localHashMap);
        }
        catch (CSILException localCSILException2)
        {
          this.error = MapError.mapError(localCSILException2);
        }
      }
      if (this.error == 0) {
        paramAccount.setDownloadedItems(true);
      }
    }
    else if ((this.startDate != null) && (this.endDate != null))
    {
      localHashMap = new HashMap();
      localHashMap.put("DATA_CLASSIFICATION", this._dataClassification);
      if (paramBanking != null) {
        localHashMap.put("SERVICE", paramBanking);
      }
      localSecureUser = (SecureUser)paramHttpSession.getAttribute("SecureUser");
      try
      {
        com.ffusion.csil.core.Banking.getTransactions(localSecureUser, paramAccount, this.startDate, this.endDate, localHashMap);
      }
      catch (CSILException localCSILException1)
      {
        this.error = MapError.mapError(localCSILException1);
      }
      if (this.error == 0) {
        paramAccount.setDownloadedItems(true);
      }
    }
    this.startDate = null;
    this.endDate = null;
    return this.error == 0;
  }
  
  public Locale getLocale()
  {
    return this.locale;
  }
  
  public String getDateFormat()
  {
    return this.e9;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public void setDataClassification(String paramString)
  {
    this._dataClassification = paramString;
  }
  
  public String getDataClassification()
  {
    return this._dataClassification;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GetTransactions
 * JD-Core Version:    0.7.0.1
 */