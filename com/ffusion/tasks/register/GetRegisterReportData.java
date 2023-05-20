package com.ffusion.tasks.register;

import com.ffusion.beans.Balance;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.accounts.AccountSummary;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.accounts.AssetAcctSummary;
import com.ffusion.beans.accounts.CreditCardAcctSummary;
import com.ffusion.beans.accounts.DepositAcctSummary;
import com.ffusion.beans.accounts.LoanAcctSummary;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.RegisterUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AccountAggregation;
import com.ffusion.csil.core.Register;
import com.ffusion.csil.core.Reporting;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.ExtendedBaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.reporting.GenerateReportBase;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetRegisterReportData
  extends ExtendedBaseTask
  implements Task
{
  public static final String BOTH_TAX_RELATED_AND_UNRELATED = "F";
  private String Fb = GenerateReportBase.REPORT_NAME;
  private String Fc = "BankingAccounts";
  protected String _bankingServiceName = "com.ffusion.services.Banking";
  protected String _separatorToken = ",";
  protected String _nameOfMaskedAccountsList = "MaskedAccountNumbers";
  
  public GetRegisterReportData()
  {
    this.collectionSessionName = "RegisterReport";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Report localReport = (Report)localHttpSession.getAttribute(this.Fb);
    if (localReport == null)
    {
      this.error = 64;
      return super.getTaskErrorURL();
    }
    ReportCriteria localReportCriteria = localReport.getReportCriteria();
    if (localReportCriteria == null)
    {
      this.error = 64;
      return super.getTaskErrorURL();
    }
    Accounts localAccounts = (Accounts)localHttpSession.getAttribute(getAccountsCollectionName());
    if (localAccounts == null)
    {
      this.error = 39;
      return super.getTaskErrorURL();
    }
    com.ffusion.services.Banking localBanking = (com.ffusion.services.Banking)localHttpSession.getAttribute(this._bankingServiceName);
    if (localBanking == null)
    {
      this.error = 37;
      return super.getTaskErrorURL();
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if (localSecureUser == null)
    {
      this.error = 20009;
      return super.getTaskErrorURL();
    }
    this.error = validateSearchCriteria(localSecureUser, localReportCriteria);
    if (this.error != 0) {
      return super.getTaskErrorURL();
    }
    try
    {
      Reporting.calculateDateRange(localSecureUser, null, localReportCriteria, new HashMap(5), new HashMap(5), null);
    }
    catch (CSILException localCSILException1)
    {
      this.error = localCSILException1.getServiceError();
      return super.getServiceErrorURL();
    }
    localReportCriteria.setCurrentSearchCriterion("Accounts");
    localReportCriteria.setHiddenSearchCriterion("Accounts", true);
    String str1 = localReportCriteria.getCurrentSearchCriterionValue();
    LocalizableList localLocalizableList = new LocalizableList();
    Object localObject1;
    Object localObject2;
    if ((str1 != null) && (str1.length() == 0))
    {
      localReportCriteria.setCurrentSearchCriterion("NumAccounts");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      localReportCriteria.getSearchCriteria().remove("NumAccounts");
      int i = 0;
      if ((localObject1 != null) && (((String)localObject1).length() > 0)) {
        try
        {
          i = Integer.parseInt((String)localObject1);
        }
        catch (NumberFormatException localNumberFormatException) {}
      }
      StringBuffer localStringBuffer = new StringBuffer();
      int j = 0;
      for (int k = 0; k < i; k++)
      {
        localReportCriteria.setCurrentSearchCriterion("AccountID_" + String.valueOf(k));
        String str3 = localReportCriteria.getCurrentSearchCriterionValue();
        localReportCriteria.getSearchCriteria().remove("AccountID_" + String.valueOf(k));
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append(str3);
          localStringBuffer.append(",");
          j++;
          jdMethod_for(localLocalizableList, str3, localAccounts, getLocale(localHttpSession, localSecureUser));
          this.error = jdMethod_for(localSecureUser, localAccounts, str3, localBanking);
          if (this.error != 0) {
            return super.getTaskErrorURL();
          }
        }
      }
      if (j > 0)
      {
        localStringBuffer.deleteCharAt(localStringBuffer.length() - 1);
        if (j == 1)
        {
          localReportCriteria.setCurrentSearchCriterion("Account");
          localReportCriteria.setCurrentSearchCriterionValue(localStringBuffer.toString());
          localReportCriteria.setHiddenSearchCriterion("true");
        }
        else
        {
          localReportCriteria.setCurrentSearchCriterion("Accounts");
          localReportCriteria.setCurrentSearchCriterionValue(localStringBuffer.toString());
          localReportCriteria.setHiddenSearchCriterion("true");
        }
      }
      if (localStringBuffer.length() <= 0)
      {
        localReportCriteria.setCurrentSearchCriterion("Account");
        localReportCriteria.setHiddenSearchCriterion("true");
        String str2 = localReportCriteria.getCurrentSearchCriterionValue();
        if ((str2 == null) || (str2.length() <= 0))
        {
          this.error = 20132;
          return super.getTaskErrorURL();
        }
        jdMethod_for(localLocalizableList, str2, localAccounts, getLocale(localHttpSession, localSecureUser));
        this.error = jdMethod_for(localSecureUser, localAccounts, str2, localBanking);
        if (this.error != 0) {
          return super.getTaskErrorURL();
        }
      }
    }
    else
    {
      localObject1 = new StringTokenizer(str1, this._separatorToken);
      localObject2 = "";
      try
      {
        while (((StringTokenizer)localObject1).hasMoreTokens())
        {
          localObject2 = ((StringTokenizer)localObject1).nextToken();
          jdMethod_for(localLocalizableList, (String)localObject2, localAccounts, getLocale(localHttpSession, localSecureUser));
          if ((localObject2 != null) && (!((String)localObject2).trim().equals(""))) {
            jdMethod_for(localSecureUser, localAccounts, (String)localObject2, localBanking);
          }
        }
      }
      catch (NoSuchElementException localNoSuchElementException) {}
    }
    try
    {
      localObject1 = new HashMap();
      ((HashMap)localObject1).put("Accounts", localAccounts);
      localObject2 = (ReportResult)Register.getReportData(localSecureUser, localReportCriteria, (HashMap)localObject1);
      localReport.setReportResult((IReportResult)localObject2);
      localHttpSession.setAttribute(this._nameOfMaskedAccountsList, (String)localLocalizableList.localize(getLocale(localHttpSession, localSecureUser)));
    }
    catch (CSILException localCSILException2)
    {
      this.error = localCSILException2.getCode();
      return super.getTaskErrorURL();
    }
    return this.successURL;
  }
  
  private void jdMethod_for(LocalizableList paramLocalizableList, String paramString, Accounts paramAccounts, Locale paramLocale)
  {
    com.ffusion.beans.accounts.Account localAccount = paramAccounts.getByID(paramString);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = localAccount.getConsumerDisplayText();
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text10319", arrayOfObject);
    paramLocalizableList.add(localLocalizableString);
  }
  
  public void setAccountDelimiter(String paramString)
  {
    this._separatorToken = paramString;
  }
  
  public String getAccountDelimiter()
  {
    return this._separatorToken;
  }
  
  public void setReportSessionName(String paramString)
  {
    this.Fb = paramString;
  }
  
  public String getReportSessionName()
  {
    return this.Fb;
  }
  
  public void setAccountsCollectionName(String paramString)
  {
    this.Fc = paramString;
  }
  
  public String getAccountsCollectionName()
  {
    return this.Fc;
  }
  
  private int jdMethod_for(SecureUser paramSecureUser, Accounts paramAccounts, String paramString, com.ffusion.services.Banking paramBanking)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      if (paramAccounts != null)
      {
        com.ffusion.beans.accounts.Account localAccount = paramAccounts.getByID(paramString);
        if (localAccount != null)
        {
          RegisterTransactions localRegisterTransactions = null;
          HashMap localHashMap = new HashMap();
          Object localObject1;
          Object localObject2;
          if (localAccount.getCoreAccount().equals("1"))
          {
            if (paramBanking != null) {
              localHashMap.put("SERVICE", paramBanking);
            } else {
              return 37;
            }
            localObject1 = (DateTime)localAccount.get("REG_RETRIEVAL_DATE");
            localObject2 = null;
            if (localObject1 == null)
            {
              localObject2 = Calendar.getInstance();
              ((Calendar)localObject2).set(1, 2003);
              ((Calendar)localObject2).set(5, 0);
              ((Calendar)localObject2).set(5, 1);
              ((Calendar)localObject2).set(10, 0);
              ((Calendar)localObject2).set(12, 0);
              ((Calendar)localObject2).set(13, 0);
              ((Calendar)localObject2).set(14, 0);
            }
            else
            {
              localObject2 = localObject1;
            }
            Calendar localCalendar = Calendar.getInstance();
            try
            {
              com.ffusion.csil.core.Banking.getTransactions(paramSecureUser, localAccount, (Calendar)localObject2, localCalendar, localHashMap);
              AccountSummaries localAccountSummaries = com.ffusion.csil.core.Banking.getSummary(paramSecureUser, localAccount, null, null, localHashMap);
              if ((localAccountSummaries != null) && (localAccountSummaries.size() > 0)) {
                jdMethod_for(localAccount, (AccountSummary)localAccountSummaries.get(localAccountSummaries.size() - 1));
              }
              localRegisterTransactions = RegisterUtil.buildRegisterTransactions(localAccount.getTransactions(), localAccount.getID());
            }
            catch (CSILException localCSILException3)
            {
              return MapError.mapError(localCSILException3);
            }
          }
          else if (localAccount.getCoreAccount().equals("0"))
          {
            localObject1 = AccountUtil.mapAccountsAccountBeanToAggregationAccountBean(localAccount, super.getLocale());
            try
            {
              AccountAggregation.getTransactions(paramSecureUser, (com.ffusion.beans.aggregation.Account)localObject1, localHashMap);
              localObject2 = AccountAggregation.getSummary(paramSecureUser, (com.ffusion.beans.aggregation.Account)localObject1, null, null, localHashMap);
              jdMethod_for(localAccount, (AccountSummary)((AccountSummaries)localObject2).get(((AccountSummaries)localObject2).size() - 1));
              localRegisterTransactions = RegisterUtil.buildRegisterTransactions(((com.ffusion.beans.aggregation.Account)localObject1).getTransactions(), (com.ffusion.beans.aggregation.Account)localObject1);
            }
            catch (CSILException localCSILException2)
            {
              return MapError.mapError(localCSILException2);
            }
          }
          try
          {
            Register.addBankTransactions(paramSecureUser, localRegisterTransactions, localHashMap);
          }
          catch (CSILException localCSILException1)
          {
            return MapError.mapError(localCSILException1);
          }
        }
        else
        {
          return 50;
        }
      }
      else
      {
        return 39;
      }
    }
    return 0;
  }
  
  public void setBankingServiceName(String paramString)
  {
    this._bankingServiceName = paramString;
  }
  
  public void setMaskedAccountsListName(String paramString)
  {
    this._nameOfMaskedAccountsList = paramString;
  }
  
  public String getMaskedAccountsListName()
  {
    return this._nameOfMaskedAccountsList;
  }
  
  private void jdMethod_for(com.ffusion.beans.accounts.Account paramAccount, AccountSummary paramAccountSummary)
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
  
  protected int validateSearchCriteria(SecureUser paramSecureUser, ReportCriteria paramReportCriteria)
  {
    int i = 0;
    i = jdMethod_for(paramSecureUser, paramReportCriteria);
    if (i != 0) {
      return i;
    }
    i = validateAmounts(paramSecureUser, paramReportCriteria);
    if (i != 0) {
      return i;
    }
    String str1 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    if (str1.equals("Custom Register Report"))
    {
      String str2 = paramReportCriteria.getSearchCriteria().getProperty("Tax");
      if ((str2 == null) || (str2.length() <= 0)) {
        paramReportCriteria.getSearchCriteria().setProperty("Tax", "F");
      }
    }
    return 0;
  }
  
  private int jdMethod_for(SecureUser paramSecureUser, ReportCriteria paramReportCriteria)
  {
    int i = 0;
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str1 = localProperties.getProperty("Date Range Type");
    if ((str1 == null) || (!str1.equals("Absolute"))) {
      return 0;
    }
    String str2 = localProperties.getProperty("StartDate");
    String str3 = localProperties.getProperty("EndDate");
    String str4 = paramReportCriteria.getReportOptions().getProperty("DATEFORMAT");
    String str5 = "MM/dd/yyyy";
    DateTime localDateTime1 = null;
    DateTime localDateTime2 = null;
    if ((str2 != null) && (str2.length() > 0) && (!str2.equalsIgnoreCase(str4))) {
      try
      {
        DateFormat localDateFormat1 = DateFormatUtil.getFormatter(str5);
        localDateTime1 = new DateTime(localDateFormat1.parse(str2), Locale.getDefault());
      }
      catch (Exception localException1)
      {
        i = 1021;
        return i;
      }
    }
    if ((str3 != null) && (str3.length() > 0) && (!str3.equalsIgnoreCase(str4))) {
      try
      {
        DateFormat localDateFormat2 = DateFormatUtil.getFormatter(str5);
        localDateTime2 = new DateTime(localDateFormat2.parse(str3), Locale.getDefault());
      }
      catch (Exception localException2)
      {
        i = 1022;
        return i;
      }
    }
    if ((localDateTime1 != null) && (localDateTime2 != null) && (localDateTime1.after(localDateTime2))) {
      i = 79;
    }
    return i;
  }
  
  protected int validateAmounts(SecureUser paramSecureUser, ReportCriteria paramReportCriteria)
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if ((localProperties == null) || (localProperties.isEmpty())) {
      return 0;
    }
    double d1 = 0.0D;
    double d2 = 0.0D;
    int i = 0;
    int j = 0;
    String str = localProperties.getProperty("MinimumAmount");
    if ((str != null) && (str.length() > 0))
    {
      try
      {
        d1 = Double.parseDouble(str);
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        return 20102;
      }
      if (d1 < 0.0D) {
        return 1050;
      }
      i = 1;
    }
    str = localProperties.getProperty("MaximumAmount");
    if ((str != null) && (str.length() > 0))
    {
      try
      {
        d2 = Double.parseDouble(str);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        return 20102;
      }
      if (d2 < 0.0D) {
        return 1050;
      }
      j = 1;
    }
    if ((i != 0) && (j != 0) && (d1 > d2)) {
      return 1051;
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.GetRegisterReportData
 * JD-Core Version:    0.7.0.1
 */