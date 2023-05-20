package com.ffusion.tasks.aggregation;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AccountAggregation;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.InvalidDateTimeException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetSummary
  extends BaseTask
{
  private String aKQ = "AggregationSummary";
  private String aKP;
  private String aKR;
  private String aKS = "AggregatedAccount";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 38;
      return super.getTaskErrorURL();
    }
    Locale localLocale = localSecureUser.getLocale();
    com.ffusion.beans.accounts.Account localAccount = (com.ffusion.beans.accounts.Account)localHttpSession.getAttribute(this.aKS);
    com.ffusion.beans.aggregation.Account localAccount1 = new com.ffusion.beans.aggregation.Account(localLocale);
    jdMethod_int(localAccount, localAccount1);
    if (localAccount1 == null)
    {
      this.error = 11009;
      return super.getTaskErrorURL();
    }
    DateTime localDateTime1 = null;
    try
    {
      localDateTime1 = jdMethod_char(this.aKP, localLocale);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException1)
    {
      this.error = 11006;
      return super.getTaskErrorURL();
    }
    DateTime localDateTime2 = null;
    try
    {
      localDateTime2 = jdMethod_char(this.aKR, localLocale);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException2)
    {
      this.error = 11007;
      return super.getTaskErrorURL();
    }
    if ((localDateTime1 != null) && (localDateTime2 != null) && (localDateTime1.after(localDateTime2)))
    {
      this.error = 79;
      return super.getTaskErrorURL();
    }
    try
    {
      HashMap localHashMap = new HashMap();
      AccountSummaries localAccountSummaries = AccountAggregation.getSummary(localSecureUser, localAccount1, localDateTime1, localDateTime2, localHashMap);
      if (localAccountSummaries != null) {
        localHttpSession.setAttribute(getSummaryName(), localAccountSummaries);
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  private DateTime jdMethod_char(String paramString, Locale paramLocale)
    throws InvalidDateTimeException
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      return null;
    }
    DateTime localDateTime = new DateTime(paramString, paramLocale);
    return localDateTime;
  }
  
  public String getSummaryName()
  {
    return this.aKQ;
  }
  
  public void setSummaryName(String paramString)
  {
    this.aKQ = paramString;
  }
  
  public String getAccountName()
  {
    return this.aKS;
  }
  
  public void setAccountName(String paramString)
  {
    this.aKS = paramString;
  }
  
  public String getEndDate()
  {
    return this.aKR;
  }
  
  public void setEndDate(String paramString)
  {
    this.aKR = paramString;
  }
  
  public String getStartDate()
  {
    return this.aKP;
  }
  
  public void setStartDate(String paramString)
  {
    this.aKP = paramString;
  }
  
  private void jdMethod_int(com.ffusion.beans.accounts.Account paramAccount, com.ffusion.beans.aggregation.Account paramAccount1)
  {
    if ((paramAccount1 == null) || (paramAccount == null)) {
      return;
    }
    paramAccount1.setLocale(paramAccount.getLocale());
    paramAccount1.setType(String.valueOf(com.ffusion.beans.aggregation.Account.getAggregationTypeFromAccountType(paramAccount.getTypeValue())));
    paramAccount1.setID(paramAccount.getNumber(), String.valueOf(paramAccount.getTypeValue()));
    paramAccount1.setStatus(paramAccount.getStatus());
    paramAccount1.setNickName(paramAccount.getNickName());
    paramAccount1.setNumber(paramAccount.getNumber());
    paramAccount1.setInstitutionName(paramAccount.getBankName());
    paramAccount1.setInstitutionID(paramAccount.getRoutingNum());
    paramAccount1.set("CURRENCY_CODE", paramAccount.getCurrencyCode());
    paramAccount1.set("COREACCOUNT", "0");
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.aggregation.GetSummary
 * JD-Core Version:    0.7.0.1
 */