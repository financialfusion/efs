package com.ffusion.services.istatements;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.checkimaging.ImageRequest;
import com.ffusion.beans.checkimaging.ImageResult;
import com.ffusion.beans.checkimaging.ImageResults;
import com.ffusion.beans.istatements.BalanceSummary;
import com.ffusion.beans.istatements.DailyBalanceSummaries;
import com.ffusion.beans.istatements.DailyBalanceSummary;
import com.ffusion.beans.istatements.InterestBalanceSummary;
import com.ffusion.beans.istatements.InterestSummary;
import com.ffusion.beans.istatements.MonthlyAccountSummary;
import com.ffusion.beans.istatements.ReserveSummary;
import com.ffusion.beans.istatements.Statement;
import com.ffusion.beans.istatements.Statements;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.Messages;
import com.ffusion.beans.reporting.Image;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.checkimaging.CheckImagingException;
import com.ffusion.checkimaging.adapters.CheckImagingAdapter;
import com.ffusion.istatements.IStatementException;
import com.ffusion.istatements.adapters.StatementDataAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.services.StatementData2;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.InvalidDateTimeException;
import com.ffusion.util.beans.LocalizableCurrency;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import javax.imageio.ImageIO;
import javax.imageio.stream.MemoryCacheImageInputStream;

public class StatementDataService
  implements StatementData2
{
  private static final int a = 1;
  private static final String jdField_if = "StatementAccount";
  
  public Statements getStatementList(Accounts paramAccounts)
    throws IStatementException
  {
    return StatementDataAdapter.getStatementList(paramAccounts);
  }
  
  public Statement getStatement(Statement paramStatement)
    throws IStatementException
  {
    return StatementDataAdapter.getStatement(paramStatement);
  }
  
  public Statement getFullStatement(Statement paramStatement)
    throws IStatementException
  {
    return StatementDataAdapter.getFullStatement(paramStatement);
  }
  
  public void initialize(HashMap paramHashMap)
    throws IStatementException
  {
    paramHashMap.put("URL", "istatements.xml");
    StatementDataAdapter.initialize(paramHashMap);
  }
  
  public void addAccountsForIStatement(SecureUser paramSecureUser, Accounts paramAccounts)
    throws IStatementException
  {
    StatementDataAdapter.addAccountsForIStatement(paramSecureUser, paramAccounts);
  }
  
  public void removeAccountsForIStatement(Accounts paramAccounts)
    throws IStatementException
  {
    StatementDataAdapter.removeAccountsForIStatement(paramAccounts);
  }
  
  public void getPDF(Statement paramStatement, boolean paramBoolean, OutputStream paramOutputStream, Locale paramLocale)
    throws IStatementException
  {
    StatementDataAdapter.getPDF(paramStatement, paramBoolean, paramOutputStream, paramLocale);
  }
  
  public void getCSV(Statement paramStatement, PrintWriter paramPrintWriter, Locale paramLocale)
    throws IStatementException
  {
    StatementDataAdapter.getCSV(paramStatement, paramPrintWriter, paramLocale);
  }
  
  public Accounts getAccountsForIStatement(SecureUser paramSecureUser)
    throws IStatementException
  {
    return StatementDataAdapter.getAccountsForIStatement(paramSecureUser);
  }
  
  public String getStatementAgreement(SecureUser paramSecureUser)
    throws IStatementException
  {
    return StatementDataAdapter.getStatementAgreement(paramSecureUser);
  }
  
  public void setStatementAgreement(SecureUser paramSecureUser, String paramString)
    throws IStatementException
  {
    StatementDataAdapter.setStatementAgreement(paramSecureUser, paramString);
  }
  
  public void addAccountsForIStatement(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws IStatementException
  {
    StatementDataAdapter.addAccountsForIStatement(paramSecureUser, paramString, paramAccounts, paramHashMap);
  }
  
  public void removeAccountsForIStatement(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws IStatementException
  {
    StatementDataAdapter.removeAccountsForIStatement(paramSecureUser, paramString, paramAccounts, paramHashMap);
  }
  
  public Accounts getAccountsForIStatement(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws IStatementException
  {
    Accounts localAccounts = StatementDataAdapter.getAccountsForIStatement(paramSecureUser, paramString, paramHashMap);
    return localAccounts;
  }
  
  public String getStatementID(SecureUser paramSecureUser, String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws IStatementException
  {
    String str = null;
    str = StatementDataAdapter.getStatementID(paramSecureUser, paramString, paramDateTime, paramHashMap);
    return str;
  }
  
  public FilteredList getStatementDates(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws IStatementException
  {
    FilteredList localFilteredList = null;
    localFilteredList = StatementDataAdapter.getStatementDates(paramSecureUser, paramString, paramHashMap);
    return localFilteredList;
  }
  
  public IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws IStatementException
  {
    ReportResult localReportResult = null;
    String str1 = "StatementDataService.getReportData";
    Locale localLocale = null;
    Statement localStatement = null;
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str2 = null;
    DateTime localDateTime = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    int i = 1;
    int j = 1;
    boolean bool = false;
    int k = 0;
    Object localObject1 = null;
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    if (localProperties1 != null)
    {
      str2 = localProperties1.getProperty("AccountNumber");
      str3 = localProperties1.getProperty("StatementDate");
      str4 = localProperties2.getProperty("DATEFORMAT");
      str6 = localProperties1.getProperty("TransactionType");
      bool = Boolean.valueOf(localProperties2.getProperty("WithImages_HIDE", Boolean.FALSE.toString())).booleanValue();
    }
    Object localObject2;
    if (str2 == null)
    {
      localObject2 = new IStatementException(str1, 36122, "No account number search criterion information is available or is missing.");
      DebugLog.throwing("No account number search criterion information is available or is missing.", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if (str3 == null)
    {
      localObject2 = new IStatementException(str1, 36122, "No statement date search criterion information is available or is missing.");
      DebugLog.throwing("No statement date search criterion information is available or is missing.", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if (str4 == null)
    {
      localObject2 = new IStatementException(str1, 36125, "No statement date format information is available or is missing.");
      DebugLog.throwing("No statement date format information is available or is missing.", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if ("AllCreditTransactions".equals(str6))
    {
      i = 1;
      j = 0;
    }
    else if ("AllDebitTransactions".equals(str6))
    {
      i = 0;
      j = 1;
    }
    try
    {
      localObject2 = null;
      localObject3 = null;
      localLocale = paramSecureUser.getLocale();
      localDateTime = new DateTime(localLocale);
      localDateTime.setFormat(str4);
      localDateTime.fromString(str3);
      str5 = getStatementID(paramSecureUser, str2, localDateTime, paramHashMap);
      if (str5 == null) {
        throw new IStatementException(str1, 36123, "No statement exists for the specified account and/or date.");
      }
      localStatement = new Statement(str2, str5);
      localStatement.setLocale(localLocale);
      localStatement = getFullStatement(localStatement);
      IStatementException localIStatementException2;
      if (localStatement == null)
      {
        localIStatementException2 = new IStatementException(str1, 36123, "No statement exists for the specified account and/or date.");
        DebugLog.throwing("No statement exists for the specified account and/or date.", localIStatementException2);
        throw localIStatementException2;
      }
      if (localStatement.getBalanceSummary() == null)
      {
        localIStatementException2 = new IStatementException(str1, 36126, "The retrieved statement is missing balance summary information.");
        DebugLog.throwing("The retrieved statement is missing balance summary information.", localIStatementException2);
        throw localIStatementException2;
      }
      localObject2 = StatementDataAdapter.getAccountsForIStatement(paramSecureUser);
      localObject3 = ((Accounts)localObject2).getByNumberAndBankID(localStatement.getAccountNumber(), paramSecureUser.getBankID());
      if (localObject3 == null)
      {
        localIStatementException2 = new IStatementException(str1, 36128, "The specified account is not registered for Online Statements.");
        DebugLog.throwing("The specified account is not registered for Online Statements.", localIStatementException2);
        throw localIStatementException2;
      }
      if (paramHashMap.containsKey("StatementAccount"))
      {
        localObject1 = paramHashMap.get("StatementAccount");
        k = 1;
      }
      paramHashMap.put("StatementAccount", localObject3);
      a(paramReportCriteria, localLocale, paramHashMap);
      localReportResult = new ReportResult(localLocale);
      localReportResult.init(paramReportCriteria);
      a(localStatement, paramReportCriteria, localReportResult, localLocale, paramHashMap);
      jdMethod_int(localStatement, paramReportCriteria, localReportResult, localLocale);
      if (i != 0) {
        jdMethod_do(localStatement, paramReportCriteria, localReportResult, localLocale);
      }
      if (j != 0) {
        jdMethod_byte(localStatement, paramReportCriteria, localReportResult, localLocale);
      }
      jdMethod_new(localStatement, paramReportCriteria, localReportResult, localLocale);
      jdMethod_try(localStatement, paramReportCriteria, localReportResult, localLocale);
      a(localStatement, paramReportCriteria, localReportResult, localLocale);
      jdMethod_for(localStatement, paramReportCriteria, localReportResult, localLocale);
      jdField_if(localStatement, paramReportCriteria, localReportResult, localLocale);
      if ((j != 0) && (bool)) {
        a(paramSecureUser, localStatement, paramReportCriteria, localReportResult, localLocale, paramHashMap);
      }
      localReportResult.fini();
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      localObject3 = new IStatementException(localInvalidDateTimeException, str1, 36124, "The specified statement date is invalid.");
      DebugLog.throwing(((IStatementException)localObject3).getLocalizedMessage(), (Throwable)localObject3);
      throw ((Throwable)localObject3);
    }
    catch (RptException localRptException)
    {
      localObject3 = new IStatementException(localRptException, str1, 36118, "The request could not be completed because an error occurred in the reporting framework.");
      DebugLog.throwing(((IStatementException)localObject3).getLocalizedMessage(), (Throwable)localObject3);
      throw ((Throwable)localObject3);
    }
    catch (CheckImagingException localCheckImagingException)
    {
      Object localObject3 = new IStatementException(localCheckImagingException, str1, 36118, "The request could not be completed because the requested check images could not be retrieved.");
      DebugLog.throwing(((IStatementException)localObject3).getLocalizedMessage(), (Throwable)localObject3);
      throw ((Throwable)localObject3);
    }
    catch (IStatementException localIStatementException1)
    {
      throw localIStatementException1;
    }
    finally
    {
      if (k != 0) {
        paramHashMap.put("StatementAccount", localObject1);
      } else {
        paramHashMap.remove("StatementAccount");
      }
    }
    return localReportResult;
  }
  
  private static void a(Statement paramStatement, ReportCriteria paramReportCriteria, ReportResult paramReportResult, Locale paramLocale, HashMap paramHashMap)
    throws RptException, IStatementException
  {
    ReportResult localReportResult = null;
    ReportDataDimensions localReportDataDimensions = null;
    ReportColumn localReportColumn = null;
    ReportRow localReportRow = null;
    ReportDataItems localReportDataItems = null;
    ReportDataItem localReportDataItem = null;
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject = null;
    String str1 = null;
    int i = 0;
    Account localAccount = null;
    String str2 = paramReportCriteria.getReportOptions().getProperty("FORMAT");
    if (("COMMA".equals(str2)) || ("TAB".equals(str2))) {
      a(paramReportResult, paramLocale);
    }
    i = a(paramStatement);
    localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(1);
    localReportDataDimensions.setNumRows(4 + i);
    localReportResult.setDataDimensions(localReportDataDimensions);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(100);
    localReportResult.addColumn(localReportColumn);
    for (int j = 1; j <= i; j++)
    {
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      switch (j)
      {
      case 1: 
        localReportDataItem.setData(paramStatement.getNameAddress1());
        break;
      case 2: 
        localReportDataItem.setData(paramStatement.getNameAddress2());
        break;
      case 3: 
        localReportDataItem.setData(paramStatement.getNameAddress3());
        break;
      case 4: 
        localReportDataItem.setData(paramStatement.getNameAddress4());
        break;
      case 5: 
        localReportDataItem.setData(paramStatement.getNameAddress5());
        break;
      case 6: 
        localReportDataItem.setData(paramStatement.getNameAddress6());
        break;
      default: 
        IStatementException localIStatementException = new IStatementException(36130);
        DebugLog.throwing("Too many name/address lines detected.", localIStatementException);
        throw localIStatementException;
      }
      localReportRow = new ReportRow(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
    }
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("");
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localAccount = (Account)paramHashMap.get("StatementAccount");
    arrayOfObject = new Object[] { localAccount.getDisplayText(paramReportCriteria.getReportOptions().getProperty("FORMAT")) };
    localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text10800", arrayOfObject);
    localReportDataItem.setData(localLocalizableString.localize(paramLocale));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    str1 = paramReportCriteria.getReportOptions().getProperty("DATEFORMAT");
    paramStatement.getStatementStartDateValue().setFormat(str1);
    paramStatement.getStatementEndDateValue().setFormat(str1);
    arrayOfObject = new Object[] { paramStatement.getStatementStartDate(), paramStatement.getStatementEndDate() };
    localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text10801", arrayOfObject);
    localReportDataItem.setData(localLocalizableString.localize(paramLocale));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("");
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
  }
  
  private static int a(Statement paramStatement)
  {
    int i = 0;
    if (paramStatement.getNameAddress6() != null) {
      i = 6;
    } else if (paramStatement.getNameAddress5() != null) {
      i = 5;
    } else if (paramStatement.getNameAddress4() != null) {
      i = 4;
    } else if (paramStatement.getNameAddress3() != null) {
      i = 3;
    } else if (paramStatement.getNameAddress2() != null) {
      i = 2;
    } else if (paramStatement.getNameAddress1() != null) {
      i = 1;
    }
    return i;
  }
  
  private static void jdMethod_int(Statement paramStatement, ReportCriteria paramReportCriteria, ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = null;
    ReportHeading localReportHeading = null;
    ReportDataDimensions localReportDataDimensions = null;
    ReportColumn localReportColumn = null;
    ReportRow localReportRow = null;
    ReportDataItems localReportDataItems = null;
    ReportDataItem localReportDataItem = null;
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject = null;
    NumberFormat localNumberFormat = null;
    String str = paramReportCriteria.getReportOptions().getProperty("FORMAT");
    if (("COMMA".equals(str)) || ("TAB".equals(str))) {
      a(paramReportResult, paramLocale);
    }
    localNumberFormat = NumberFormat.getPercentInstance(paramLocale);
    localNumberFormat.setMinimumFractionDigits(2);
    localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    localReportHeading = new ReportHeading(paramLocale);
    localReportHeading.setLabel("");
    localReportResult.setHeading(localReportHeading);
    localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(4);
    localReportDataDimensions.setNumRows(5);
    localReportResult.setDataDimensions(localReportDataDimensions);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(40);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("CENTER");
    localReportColumn.setWidthAsPercent(5);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(25);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(30);
    localReportResult.addColumn(localReportColumn);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10844, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("");
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(paramStatement.getBalanceSummary().getPreviousAmountValue(), paramLocale, false, true));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("");
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10821, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("+");
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(paramStatement.getBalanceSummary().getTotalDepositsValue(), paramLocale, false, true));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("");
    localReportRow = new ReportRow(paramLocale);
    localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10825, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("-");
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(paramStatement.getBalanceSummary().getTotalWithdrawalsValue(), paramLocale, false, true));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("");
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10845, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("=");
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(paramStatement.getBalanceSummary().getNewAmountValue(), paramLocale, false, true));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("");
    localReportRow = new ReportRow(paramLocale);
    localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10846, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("");
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(paramStatement.getInterestSummary().getInterestEarnedValue(), paramLocale, false, true));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("");
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    if (("COMMA".equals(str)) || ("TAB".equals(str))) {
      a(paramReportResult, paramLocale);
    }
    localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(1);
    localReportDataDimensions.setNumRows(1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(100);
    localReportResult.addColumn(localReportColumn);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    arrayOfObject = new Object[] { localNumberFormat.format(paramStatement.getInterestSummary().getAPYValue() / 100.0D) };
    localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text10847", arrayOfObject);
    localReportDataItem.setData(localLocalizableString.localize(paramLocale));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
  }
  
  private static void jdMethod_do(Statement paramStatement, ReportCriteria paramReportCriteria, ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = null;
    ReportHeading localReportHeading = null;
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject = null;
    Transactions localTransactions = null;
    String str = paramReportCriteria.getReportOptions().getProperty("FORMAT");
    if (("COMMA".equals(str)) || ("TAB".equals(str))) {
      a(paramReportResult, paramLocale);
    }
    localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    arrayOfObject = new Object[] { paramStatement.getBalanceSummary().getNumDeposits(), a(paramStatement.getBalanceSummary().getTotalDepositsValue(), paramLocale, true, true) };
    localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text10802", arrayOfObject);
    localReportHeading = new ReportHeading(paramLocale);
    localReportHeading.setLabel((String)localLocalizableString.localize(paramLocale));
    localReportResult.setHeading(localReportHeading);
    localTransactions = paramStatement.getTransactions();
    localTransactions.setFilter("IS_CREDIT==true");
    localTransactions.setSortedBy("DATE");
    a(localTransactions, paramReportCriteria, localReportResult, paramLocale, null);
  }
  
  private static void jdMethod_byte(Statement paramStatement, ReportCriteria paramReportCriteria, ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = null;
    ReportHeading localReportHeading = null;
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject = null;
    Transactions localTransactions = null;
    String str = paramReportCriteria.getReportOptions().getProperty("FORMAT");
    if (("COMMA".equals(str)) || ("TAB".equals(str))) {
      a(paramReportResult, paramLocale);
    }
    localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    arrayOfObject = new Object[] { paramStatement.getBalanceSummary().getNumWithdrawals(), a(paramStatement.getBalanceSummary().getTotalWithdrawalsValue(), paramLocale, true, true) };
    localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text10803", arrayOfObject);
    localReportHeading = new ReportHeading(paramLocale);
    localReportHeading.setLabel((String)localLocalizableString.localize(paramLocale));
    localReportResult.setHeading(localReportHeading);
    localTransactions = paramStatement.getTransactions();
    localTransactions.setFilter("IS_CREDIT==false");
    localTransactions.setSortedBy("DATE");
    a(localTransactions, paramReportCriteria, localReportResult, paramLocale, null);
  }
  
  private static void a(Transactions paramTransactions, ReportCriteria paramReportCriteria, ReportResult paramReportResult, Locale paramLocale, String paramString)
    throws RptException
  {
    ReportResult localReportResult = null;
    ReportDataDimensions localReportDataDimensions = null;
    ReportHeading localReportHeading = null;
    ReportColumn localReportColumn = null;
    ReportRow localReportRow = null;
    ReportDataItems localReportDataItems = null;
    ReportDataItem localReportDataItem = null;
    Transaction localTransaction = null;
    String str = null;
    if (paramTransactions.size() > 0)
    {
      str = paramReportCriteria.getReportOptions().getProperty("DATEFORMAT");
      localReportResult = new ReportResult(paramLocale);
      paramReportResult.addSubReport(localReportResult);
      localReportDataDimensions = new ReportDataDimensions(paramLocale);
      localReportDataDimensions.setNumColumns(3);
      localReportDataDimensions.setNumRows(paramTransactions.size());
      localReportResult.setDataDimensions(localReportDataDimensions);
      if (paramString != null)
      {
        localReportHeading = new ReportHeading(paramLocale);
        localReportHeading.setLabel(paramString);
        localReportResult.setSectionHeading(localReportHeading);
      }
      localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.set("LABEL", ReportConsts.getColumnName(3122, paramLocale));
      localReportColumn.setWidthAsPercent(20);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setJustification("RIGHT");
      localReportColumn.set("LABEL", ReportConsts.getColumnName(3123, paramLocale));
      localReportColumn.setWidthAsPercent(25);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.set("LABEL", ReportConsts.getColumnName(3124, paramLocale));
      localReportColumn.setWidthAsPercent(55);
      localReportResult.addColumn(localReportColumn);
      int i = 0;
      int j = paramTransactions.size();
      while (i < j)
      {
        localTransaction = (Transaction)paramTransactions.get(i);
        localReportRow = new ReportRow(paramLocale);
        localReportDataItems = new ReportDataItems(paramLocale);
        localReportDataItem = localReportDataItems.add();
        localTransaction.getDateValue().setFormat(str);
        localReportDataItem.setData(localTransaction.getDate());
        localReportDataItem = localReportDataItems.add();
        localReportDataItem.setData(a(localTransaction.getAmountValue(), paramLocale, false, true));
        localReportDataItem = localReportDataItems.add();
        localReportDataItem.setData(localTransaction.getDescription());
        localReportRow.setDataItems(localReportDataItems);
        if (i % 2 == 1) {
          localReportRow.set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportResult.addRow(localReportRow);
        i++;
      }
    }
  }
  
  private static void jdMethod_new(Statement paramStatement, ReportCriteria paramReportCriteria, ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = null;
    ReportDataDimensions localReportDataDimensions = null;
    ReportHeading localReportHeading = null;
    ReportColumn localReportColumn = null;
    ReportRow localReportRow = null;
    ReportDataItems localReportDataItems = null;
    ReportDataItem localReportDataItem = null;
    ReserveSummary localReserveSummary = null;
    NumberFormat localNumberFormat = null;
    String str1 = null;
    localReserveSummary = paramStatement.getReserveSummary();
    if (localReserveSummary != null)
    {
      String str2 = paramReportCriteria.getReportOptions().getProperty("FORMAT");
      if (("COMMA".equals(str2)) || ("TAB".equals(str2))) {
        a(paramReportResult, paramLocale);
      }
      localReportResult = new ReportResult(paramLocale);
      paramReportResult.addSubReport(localReportResult);
      localReportHeading = new ReportHeading(paramLocale);
      localReportHeading.setLabel(ReportConsts.getText(10806, paramLocale));
      localReportResult.setHeading(localReportHeading);
      localNumberFormat = NumberFormat.getPercentInstance(paramLocale);
      localNumberFormat.setMinimumFractionDigits(2);
      localReportDataDimensions = new ReportDataDimensions(paramLocale);
      localReportDataDimensions.setNumColumns(2);
      localReportDataDimensions.setNumRows(10);
      localReportResult.setDataDimensions(localReportDataDimensions);
      localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(75);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setJustification("RIGHT");
      localReportColumn.setWidthAsPercent(25);
      localReportResult.addColumn(localReportColumn);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10809, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(a(localReserveSummary.getPreviousReserveAmountValue(), paramLocale, false, true));
      localReportRow = new ReportRow(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10810, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localNumberFormat.format(localReserveSummary.getPeriodIntRateValue() / 100.0D));
      localReportRow = new ReportRow(paramLocale);
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10811, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(a(localReserveSummary.getPmtsOnReserveAmountValue(), paramLocale, false, true));
      localReportRow = new ReportRow(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10812, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localNumberFormat.format(localReserveSummary.getAnnualIntRateValue() / 100.0D));
      localReportRow = new ReportRow(paramLocale);
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10813, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(a(localReserveSummary.getReserveTransAmountValue(), paramLocale, false, true));
      localReportRow = new ReportRow(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10814, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(a(localReserveSummary.getApprovedReserveAmountValue(), paramLocale, false, true));
      localReportRow = new ReportRow(paramLocale);
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10815, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(a(localReserveSummary.getFinanceChargeAmountValue(), paramLocale, false, true));
      localReportRow = new ReportRow(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10816, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(a(localReserveSummary.getAvailableReserveAmountValue(), paramLocale, false, true));
      localReportRow = new ReportRow(paramLocale);
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10817, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(a(localReserveSummary.getNewReserveAmountValue(), paramLocale, false, true));
      localReportRow = new ReportRow(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10818, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(a(localReserveSummary.getResInUseFinanceChargeAmountValue(), paramLocale, false, true));
      localReportRow = new ReportRow(paramLocale);
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      str1 = (String)localReserveSummary.get("RES_SUMMARY_TXT");
      str1 = str1 == null ? str1 : str1.trim();
      if ((str1 != null) && (str1.length() > 0))
      {
        if (("COMMA".equals(str2)) || ("TAB".equals(str2))) {
          a(paramReportResult, paramLocale);
        }
        localReportResult = new ReportResult(paramLocale);
        paramReportResult.addSubReport(localReportResult);
        localReportHeading = new ReportHeading(paramLocale);
        localReportHeading.setLabel(ReportConsts.getText(10854, paramLocale));
        localReportResult.setHeading(localReportHeading);
        localReportDataDimensions = new ReportDataDimensions(paramLocale);
        localReportDataDimensions.setNumColumns(1);
        localReportDataDimensions.setNumRows(1);
        localReportResult.setDataDimensions(localReportDataDimensions);
        localReportColumn = new ReportColumn(paramLocale);
        localReportColumn.setDataType("java.lang.String");
        localReportColumn.setWidthAsPercent(100);
        localReportResult.addColumn(localReportColumn);
        localReportDataItems = new ReportDataItems(paramLocale);
        localReportDataItem = localReportDataItems.add();
        localReportDataItem.setData(str1);
        localReportRow = new ReportRow(paramLocale);
        localReportRow.setDataItems(localReportDataItems);
        localReportResult.addRow(localReportRow);
      }
    }
  }
  
  private static void jdMethod_try(Statement paramStatement, ReportCriteria paramReportCriteria, ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = null;
    ReportDataDimensions localReportDataDimensions = null;
    ReportHeading localReportHeading = null;
    ReportColumn localReportColumn = null;
    ReportRow localReportRow = null;
    ReportDataItems localReportDataItems = null;
    ReportDataItem localReportDataItem = null;
    MonthlyAccountSummary localMonthlyAccountSummary = null;
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject = null;
    localMonthlyAccountSummary = paramStatement.getMonthlyAccountSummary();
    if (localMonthlyAccountSummary != null)
    {
      String str = paramReportCriteria.getReportOptions().getProperty("FORMAT");
      if (("COMMA".equals(str)) || ("TAB".equals(str))) {
        a(paramReportResult, paramLocale);
      }
      localReportResult = new ReportResult(paramLocale);
      paramReportResult.addSubReport(localReportResult);
      localReportHeading = new ReportHeading(paramLocale);
      localReportHeading.setLabel(ReportConsts.getText(10807, paramLocale));
      localReportResult.setHeading(localReportHeading);
      localReportDataDimensions = new ReportDataDimensions(paramLocale);
      localReportDataDimensions.setNumColumns(2);
      localReportDataDimensions.setNumRows(8);
      localReportResult.setDataDimensions(localReportDataDimensions);
      localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(75);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setJustification("RIGHT");
      localReportColumn.setWidthAsPercent(25);
      localReportResult.addColumn(localReportColumn);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10819, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(a(localMonthlyAccountSummary.getAvgColBalValue(), paramLocale, false, true));
      localReportRow = new ReportRow(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10821, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localMonthlyAccountSummary.getNumDeposits());
      localReportRow = new ReportRow(paramLocale);
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10823, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localMonthlyAccountSummary.getItemsDeposited());
      localReportRow = new ReportRow(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10825, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localMonthlyAccountSummary.getNumWithdrawals());
      localReportRow = new ReportRow(paramLocale);
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10820, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localMonthlyAccountSummary.getCashItemsDeposited());
      localReportRow = new ReportRow(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10822, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localMonthlyAccountSummary.getTransTowardLimit());
      localReportRow = new ReportRow(paramLocale);
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(ReportConsts.getText(10824, paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(localMonthlyAccountSummary.getNumElectronicTrans());
      localReportRow = new ReportRow(paramLocale);
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
      localReportDataItems = new ReportDataItems(paramLocale);
      localReportDataItem = localReportDataItems.add();
      arrayOfObject = new Object[] { localMonthlyAccountSummary.getExcessLimit(), localMonthlyAccountSummary.getNumExcessTrans(), a(localMonthlyAccountSummary.getExcessTransTotalFeeAmountValue(), paramLocale, true, true) };
      localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text10826", arrayOfObject);
      localReportDataItem.setData(localLocalizableString.localize(paramLocale));
      localReportDataItem = localReportDataItems.add();
      localReportDataItem.setData(a(localMonthlyAccountSummary.getExcessFeeAmountValue(), paramLocale, false, true));
      localReportRow = new ReportRow(paramLocale);
      localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      localReportRow.setDataItems(localReportDataItems);
      localReportResult.addRow(localReportRow);
    }
  }
  
  private static void a(Statement paramStatement, ReportCriteria paramReportCriteria, ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = null;
    ReportDataDimensions localReportDataDimensions = null;
    ReportHeading localReportHeading = null;
    LocalizableString localLocalizableString = null;
    Object[] arrayOfObject = null;
    localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(0);
    localReportDataDimensions.setNumRows(0);
    localReportResult.setDataDimensions(localReportDataDimensions);
    if (paramStatement.getInterestBalanceSummary() != null)
    {
      String str = paramReportCriteria.getReportOptions().getProperty("FORMAT");
      if (("COMMA".equals(str)) || ("TAB".equals(str))) {
        a(paramReportResult, paramLocale);
      }
      arrayOfObject = new Object[] { a(paramStatement.getInterestBalanceSummary().getReserveReqAmountValue(), paramLocale, true, true) };
      localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text10827", arrayOfObject);
      localReportHeading = new ReportHeading(paramLocale);
      localReportHeading.setLabel((String)localLocalizableString.localize(paramLocale));
      localReportResult.setHeading(localReportHeading);
      jdField_if(paramStatement, localReportResult, paramLocale);
      a(paramStatement, localReportResult, paramLocale);
    }
  }
  
  private static void jdField_if(Statement paramStatement, ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = null;
    ReportDataDimensions localReportDataDimensions = null;
    ReportHeading localReportHeading = null;
    ReportColumn localReportColumn = null;
    ReportRow localReportRow = null;
    ReportDataItems localReportDataItems = null;
    ReportDataItem localReportDataItem = null;
    InterestBalanceSummary localInterestBalanceSummary = null;
    NumberFormat localNumberFormat = null;
    localNumberFormat = NumberFormat.getPercentInstance(paramLocale);
    localNumberFormat.setMinimumFractionDigits(2);
    localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(2);
    localReportDataDimensions.setNumRows(9);
    localReportResult.setDataDimensions(localReportDataDimensions);
    localReportHeading = new ReportHeading(paramLocale);
    localReportHeading.setLabel(ReportConsts.getText(10828, paramLocale));
    localReportResult.setSectionHeading(localReportHeading);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(75);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(25);
    localReportResult.addColumn(localReportColumn);
    localInterestBalanceSummary = paramStatement.getInterestBalanceSummary();
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10829, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(localInterestBalanceSummary.getAvgLedgerBalanceAmountValue(), paramLocale, false, true));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10830, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(localInterestBalanceSummary.getAvgFloatAmountValue(), paramLocale, false, true));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10819, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(localInterestBalanceSummary.getAvgColAmountValue(), paramLocale, false, true));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10831, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(localInterestBalanceSummary.getAvgReserveReqAmountValue(), paramLocale, false, true));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10832, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(localInterestBalanceSummary.getAvgBalQualIntAmountValue(), paramLocale, false, true));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10833, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(localNumberFormat.format(localInterestBalanceSummary.getAvgIntRateValue() / 100.0D));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10834, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(localInterestBalanceSummary.getAvgDailyAccrAmountValue(), paramLocale, false, true));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10835, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(localInterestBalanceSummary.getNumDaysQual());
    localReportRow = new ReportRow(paramLocale);
    localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10836, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(localInterestBalanceSummary.getTotalIntCreditAmountValue(), paramLocale, false, true));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
  }
  
  private static void a(Statement paramStatement, ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = null;
    ReportDataDimensions localReportDataDimensions = null;
    ReportHeading localReportHeading = null;
    ReportColumn localReportColumn = null;
    ReportRow localReportRow = null;
    ReportDataItems localReportDataItems = null;
    ReportDataItem localReportDataItem = null;
    InterestBalanceSummary localInterestBalanceSummary = null;
    NumberFormat localNumberFormat = null;
    localNumberFormat = NumberFormat.getPercentInstance(paramLocale);
    localNumberFormat.setMinimumFractionDigits(2);
    localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(2);
    localReportDataDimensions.setNumRows(6);
    localReportResult.setDataDimensions(localReportDataDimensions);
    localReportHeading = new ReportHeading(paramLocale);
    localReportHeading.setLabel(ReportConsts.getText(10837, paramLocale));
    localReportResult.setSectionHeading(localReportHeading);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(75);
    localReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(25);
    localReportResult.addColumn(localReportColumn);
    localInterestBalanceSummary = paramStatement.getInterestBalanceSummary();
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10838, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(localInterestBalanceSummary.getNumDaysOver());
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10839, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(localInterestBalanceSummary.getAvgDailyOverAmountValue(), paramLocale, false, true));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10840, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(localNumberFormat.format(localInterestBalanceSummary.getAvgOverRateValue() / 100.0D));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10841, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(localInterestBalanceSummary.getAvgDailyOverAmountValue(), paramLocale, false, true));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10842, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(localInterestBalanceSummary.getNumDaysCharged());
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(ReportConsts.getText(10843, paramLocale));
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData(a(localInterestBalanceSummary.getTotalOverChargedAmountValue(), paramLocale, false, true));
    localReportRow = new ReportRow(paramLocale);
    localReportRow.set("CELLBACKGROUND", "reportDataShaded");
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
  }
  
  private static void jdMethod_for(Statement paramStatement, ReportCriteria paramReportCriteria, ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = null;
    ReportDataDimensions localReportDataDimensions = null;
    ReportHeading localReportHeading = null;
    ReportColumn localReportColumn = null;
    ReportRow localReportRow = null;
    ReportDataItems localReportDataItems = null;
    ReportDataItem localReportDataItem = null;
    DailyBalanceSummaries localDailyBalanceSummaries = null;
    DailyBalanceSummary localDailyBalanceSummary = null;
    String str1 = null;
    localDailyBalanceSummaries = paramStatement.getDailyBalanceSummaries();
    if (!localDailyBalanceSummaries.isEmpty())
    {
      String str2 = paramReportCriteria.getReportOptions().getProperty("FORMAT");
      if (("COMMA".equals(str2)) || ("TAB".equals(str2))) {
        a(paramReportResult, paramLocale);
      }
      str1 = paramReportCriteria.getReportOptions().getProperty("DATEFORMAT");
      localReportResult = new ReportResult(paramLocale);
      paramReportResult.addSubReport(localReportResult);
      localReportHeading = new ReportHeading(paramLocale);
      localReportHeading.setLabel(ReportConsts.getText(10808, paramLocale));
      localReportResult.setHeading(localReportHeading);
      localDailyBalanceSummaries.setSortedBy("DBS_DATE");
      localReportDataDimensions = new ReportDataDimensions(paramLocale);
      localReportDataDimensions.setNumColumns(4);
      localReportDataDimensions.setNumRows(localDailyBalanceSummaries.size());
      localReportResult.setDataDimensions(localReportDataDimensions);
      localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.set("LABEL", ReportConsts.getColumnName(3122, paramLocale));
      localReportColumn.setWidthAsPercent(20);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setJustification("RIGHT");
      localReportColumn.set("LABEL", ReportConsts.getColumnName(3125, paramLocale));
      localReportColumn.setWidthAsPercent(25);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setJustification("RIGHT");
      localReportColumn.set("LABEL", ReportConsts.getColumnName(3126, paramLocale));
      localReportColumn.setWidthAsPercent(25);
      localReportResult.addColumn(localReportColumn);
      localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(30);
      localReportResult.addColumn(localReportColumn);
      int i = 0;
      int j = localDailyBalanceSummaries.size();
      while (i < j)
      {
        localDailyBalanceSummary = (DailyBalanceSummary)localDailyBalanceSummaries.get(i);
        localReportDataItems = new ReportDataItems(paramLocale);
        localReportDataItem = localReportDataItems.add();
        localDailyBalanceSummary.getBalanceDateValue().setFormat(str1);
        localReportDataItem.setData(localDailyBalanceSummary.getBalanceDate());
        localReportDataItem = localReportDataItems.add();
        localReportDataItem.setData(a(localDailyBalanceSummary.getAmountValue(), paramLocale, false, true));
        localReportDataItem = localReportDataItems.add();
        localReportDataItem.setData(a(localDailyBalanceSummary.getReserveInUseAmountValue(), paramLocale, false, true));
        localReportDataItem = localReportDataItems.add();
        localReportDataItem.setData("");
        localReportRow = new ReportRow(paramLocale);
        if (i % 2 == 1) {
          localReportRow.set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportRow.setDataItems(localReportDataItems);
        localReportResult.addRow(localReportRow);
        i++;
      }
    }
  }
  
  private static void jdField_if(Statement paramStatement, ReportCriteria paramReportCriteria, ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = null;
    ReportDataDimensions localReportDataDimensions = null;
    ReportHeading localReportHeading = null;
    ReportColumn localReportColumn = null;
    ReportRow localReportRow = null;
    ReportDataItems localReportDataItems = null;
    ReportDataItem localReportDataItem = null;
    Messages localMessages = null;
    Message localMessage = null;
    localMessages = paramStatement.getMessages();
    if (!localMessages.isEmpty())
    {
      String str = paramReportCriteria.getReportOptions().getProperty("FORMAT");
      if (("COMMA".equals(str)) || ("TAB".equals(str))) {
        a(paramReportResult, paramLocale);
      }
      localReportResult = new ReportResult(paramLocale);
      paramReportResult.addSubReport(localReportResult);
      localReportDataDimensions = new ReportDataDimensions(paramLocale);
      localReportDataDimensions.setNumColumns(1);
      if (localMessages.size() > 1) {
        localReportDataDimensions.setNumRows(localMessages.size() * 2 - 1);
      } else {
        localReportDataDimensions.setNumRows(localMessages.size());
      }
      localReportResult.setDataDimensions(localReportDataDimensions);
      localReportHeading = new ReportHeading(paramLocale);
      localReportHeading.setLabel(ReportConsts.getText(10848, paramLocale));
      localReportResult.setHeading(localReportHeading);
      localReportColumn = new ReportColumn(paramLocale);
      localReportColumn.setDataType("java.lang.String");
      localReportColumn.setWidthAsPercent(100);
      localReportResult.addColumn(localReportColumn);
      int i = 0;
      int j = localMessages.size();
      while (i < j)
      {
        if (i > 0)
        {
          localReportDataItems = new ReportDataItems(paramLocale);
          localReportDataItem = localReportDataItems.add();
          localReportDataItem.setData("");
          localReportRow = new ReportRow(paramLocale);
          localReportRow.setDataItems(localReportDataItems);
          localReportResult.addRow(localReportRow);
        }
        localMessage = (Message)localMessages.get(i);
        localReportDataItems = new ReportDataItems(paramLocale);
        localReportDataItem = localReportDataItems.add();
        localReportDataItem.setData(localMessage.getMemo());
        localReportRow = new ReportRow(paramLocale);
        if (i % 2 == 1) {
          localReportRow.set("CELLBACKGROUND", "reportDataShaded");
        }
        localReportRow.setDataItems(localReportDataItems);
        localReportResult.addRow(localReportRow);
        i++;
      }
    }
  }
  
  private static void a(SecureUser paramSecureUser, Statement paramStatement, ReportCriteria paramReportCriteria, ReportResult paramReportResult, Locale paramLocale, HashMap paramHashMap)
    throws RptException, CheckImagingException, IStatementException
  {
    ReportResult localReportResult = null;
    ReportDataDimensions localReportDataDimensions = null;
    ReportHeading localReportHeading = null;
    ReportColumn localReportColumn = null;
    ReportRow localReportRow = null;
    ReportDataItems localReportDataItems = null;
    ReportDataItem localReportDataItem = null;
    ImageResults localImageResults1 = null;
    ImageRequest localImageRequest = null;
    ImageResults localImageResults2 = null;
    Transactions localTransactions = null;
    Transaction localTransaction = null;
    String str1 = null;
    Account localAccount = null;
    localImageResults1 = new ImageResults();
    localTransactions = paramStatement.getTransactions();
    str1 = paramReportCriteria.getReportOptions().getProperty("DATEFORMAT");
    if (localTransactions != null)
    {
      localTransactions.setFilter("TypeValue=3");
      localTransactions.setSortedBy("DATE");
      if (localTransactions.size() > 0)
      {
        localAccount = (Account)paramHashMap.get("StatementAccount");
        int i = 0;
        int j = localTransactions.size();
        int k;
        while (i < j)
        {
          localTransaction = (Transaction)localTransactions.get(i);
          localImageRequest = new ImageRequest(paramLocale);
          localImageRequest.setAccountID(localAccount.getID());
          localTransaction.getDateValue().setFormat(str1);
          localImageRequest.setPostingDateFrom(localTransaction.getDateValue().toString());
          localImageRequest.setPostingDateTo(localTransaction.getDateValue().toString());
          localImageRequest.setTransType("D");
          localImageRequest.setCheckNumberTo(localTransaction.getReferenceNumber());
          localImageRequest.setCheckNumberFrom(localTransaction.getReferenceNumber());
          localImageRequest.setAmountFrom(a(localTransaction.getAmountValue(), paramLocale, false, false));
          localImageRequest.setAmountTo(a(localTransaction.getAmountValue(), paramLocale, false, false));
          localImageResults2 = CheckImagingAdapter.getImageIndex(paramSecureUser, localImageRequest);
          localImageResults2 = CheckImagingAdapter.getImagePackageId(paramSecureUser, localImageResults2);
          k = 0;
          int m = localImageResults2.size();
          while (k < m)
          {
            localImageResults1.add(localImageResults2.get(k));
            k++;
          }
          i++;
        }
        if (!localImageResults1.isEmpty())
        {
          ImageResult localImageResult = null;
          String str2 = null;
          k = 0;
          HashMap localHashMap = null;
          Image localImage = null;
          byte[] arrayOfByte1 = null;
          byte[] arrayOfByte2 = null;
          localReportResult = new ReportResult(paramLocale);
          paramReportResult.addSubReport(localReportResult);
          localReportDataDimensions = new ReportDataDimensions(paramLocale);
          localReportDataDimensions.setNumColumns(2);
          localReportDataDimensions.setNumRows(localImageResults1.size());
          localReportResult.setDataDimensions(localReportDataDimensions);
          localReportHeading = new ReportHeading(paramLocale);
          localReportHeading.setLabel(ReportConsts.getText(10849, paramLocale));
          localReportResult.setHeading(localReportHeading);
          localReportColumn = new ReportColumn(paramLocale);
          localReportColumn.setDataType("com.ffusion.beans.reporting.Image");
          localReportColumn.setJustification("LEFT");
          localReportColumn.setWidthAsPercent(50);
          localReportResult.addColumn(localReportColumn);
          localReportColumn = new ReportColumn(paramLocale);
          localReportColumn.setDataType("com.ffusion.beans.reporting.Image");
          localReportColumn.setJustification("LEFT");
          localReportColumn.setWidthAsPercent(50);
          localReportResult.addColumn(localReportColumn);
          try
          {
            LocalizableString localLocalizableString = null;
            localObject = null;
            String str3 = null;
            DateTime localDateTime = null;
            str3 = paramReportCriteria.getReportOptions().getProperty("DATEFORMAT");
            int n = 0;
            int i1 = localImageResults1.size();
            while (n < i1)
            {
              localImageResult = (ImageResult)localImageResults1.get(n);
              localImageResult = CheckImagingAdapter.getImageStatus(paramSecureUser, localImageResult);
              if ("AVAILABLE".equalsIgnoreCase(localImageResult.getStatus())) {
                localImageResult = CheckImagingAdapter.getImage(paramSecureUser, localImageResult);
              }
              str2 = localImageResult.getErrorCode();
              k = 0;
              if ((str2 != null) && (!str2.equals("")) && (!str2.equalsIgnoreCase("0"))) {
                k = 1;
              }
              localHashMap = localImageResult.getImage();
              localReportDataItems = new ReportDataItems(paramLocale);
              localDateTime = new DateTime(localImageResult.getPostingDateValue(), paramLocale);
              localDateTime.setFormat(str3);
              localObject = new Object[] { localImageResult.getCheckNumber(), localImageResult.getSequenceNumber(), localDateTime.toString(), new LocalizableCurrency(localImageResult.getAmountValue()) };
              localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text10850", (Object[])localObject);
              if ((k == 0) && (localHashMap != null))
              {
                arrayOfByte1 = (byte[])localHashMap.get(localImageResult.getFrontViewHandle());
                localImage = new Image(a(arrayOfByte1, localImageResult.getCompressionType()), "image/jpeg", localLocalizableString);
                localReportDataItem = localReportDataItems.add();
                localReportDataItem.setData(localImage);
                arrayOfByte2 = (byte[])localHashMap.get(localImageResult.getBackViewHandle());
                localImage = new Image(a(arrayOfByte2, localImageResult.getCompressionType()), "image/jpeg", localLocalizableString);
                localReportDataItem = localReportDataItems.add();
                localReportDataItem.setData(localImage);
              }
              else
              {
                localImage = new Image(new byte[0], "image/jpeg", localLocalizableString);
                localReportDataItem = localReportDataItems.add();
                localReportDataItem.setData(localImage);
                localImage = new Image(new byte[0], "image/jpeg", localLocalizableString);
                localReportDataItem = localReportDataItems.add();
                localReportDataItem.setData(localImage);
              }
              localReportRow = new ReportRow(paramLocale);
              localReportRow.setDataItems(localReportDataItems);
              localReportResult.addRow(localReportRow);
              n++;
            }
          }
          catch (RptException localRptException)
          {
            throw localRptException;
          }
          catch (IStatementException localIStatementException)
          {
            throw localIStatementException;
          }
          catch (Exception localException)
          {
            Object localObject = new IStatementException(600009, localException);
            ((IStatementException)localObject).why = "An error occurred while attempting to add check images to the report.";
            DebugLog.throwing(((IStatementException)localObject).getLocalizedMessage(), (Throwable)localObject);
            throw ((Throwable)localObject);
          }
        }
      }
    }
  }
  
  private static byte[] a(byte[] paramArrayOfByte, int paramInt)
    throws IStatementException
  {
    byte[] arrayOfByte = null;
    BufferedImage localBufferedImage = null;
    ByteArrayOutputStream localByteArrayOutputStream = null;
    if (paramInt == 1) {
      arrayOfByte = paramArrayOfByte;
    } else {
      try
      {
        String[] arrayOfString = ImageIO.getWriterMIMETypes();
        int i = 0;
        MemoryCacheImageInputStream localMemoryCacheImageInputStream = new MemoryCacheImageInputStream(new ByteArrayInputStream(paramArrayOfByte));
        localBufferedImage = ImageIO.read(localMemoryCacheImageInputStream);
        localByteArrayOutputStream = new ByteArrayOutputStream();
        for (int j = 0; j < arrayOfString.length; j++) {
          if (arrayOfString[j].equalsIgnoreCase("image/jpeg"))
          {
            i = 1;
            break;
          }
        }
        if (i != 0)
        {
          ImageIO.write(localBufferedImage, "jpeg", localByteArrayOutputStream);
          arrayOfByte = localByteArrayOutputStream.toByteArray();
        }
        else
        {
          IStatementException localIStatementException2 = new IStatementException(36127);
          localIStatementException2.why = "No writer exists for the JPEG format.";
          throw localIStatementException2;
        }
      }
      catch (IOException localIOException)
      {
        IStatementException localIStatementException1 = new IStatementException(36127);
        localIStatementException1.why = "An error occurred while attempting to convert the check image to JPEG format.";
        throw localIStatementException1;
      }
    }
    return arrayOfByte;
  }
  
  private static void a(ReportCriteria paramReportCriteria, Locale paramLocale, HashMap paramHashMap)
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str = null;
    if (localProperties.containsKey("TransactionType"))
    {
      str = localProperties.getProperty("TransactionType");
      if ("AllTransactionTypes".equals(str)) {
        paramReportCriteria.setDisplayValue("TransactionType", ReportConsts.getText(10851, paramLocale));
      } else if ("AllCreditTransactions".equals(str)) {
        paramReportCriteria.setDisplayValue("TransactionType", ReportConsts.getText(10852, paramLocale));
      } else if ("AllDebitTransactions".equals(str)) {
        paramReportCriteria.setDisplayValue("TransactionType", ReportConsts.getText(10853, paramLocale));
      }
    }
    if (localProperties.containsKey("AccountNumber"))
    {
      Object localObject1 = null;
      Object localObject2 = null;
      Account localAccount = (Account)paramHashMap.get("StatementAccount");
      paramReportCriteria.setDisplayValue("AccountNumber", localAccount.getDisplayText(paramReportCriteria.getReportOptions().getProperty("FORMAT")));
    }
  }
  
  private static String a(Currency paramCurrency, Locale paramLocale, boolean paramBoolean1, boolean paramBoolean2)
  {
    LocalizableCurrency localLocalizableCurrency = null;
    if (paramCurrency == null) {
      localLocalizableCurrency = new LocalizableCurrency(new Currency(new BigDecimal(0.0D), paramLocale));
    } else {
      localLocalizableCurrency = new LocalizableCurrency(paramCurrency);
    }
    localLocalizableCurrency.setShowCurrencySymbol(paramBoolean1);
    localLocalizableCurrency.setShowSeparatorAndSymbol(paramBoolean2);
    return (String)localLocalizableCurrency.localize(paramLocale);
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportResult localReportResult = null;
    ReportDataDimensions localReportDataDimensions = null;
    ReportColumn localReportColumn = null;
    ReportRow localReportRow = null;
    ReportDataItems localReportDataItems = null;
    ReportDataItem localReportDataItem = null;
    localReportResult = new ReportResult(paramLocale);
    paramReportResult.addSubReport(localReportResult);
    localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumColumns(1);
    localReportDataDimensions.setNumRows(1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(100);
    localReportResult.addColumn(localReportColumn);
    localReportDataItems = new ReportDataItems(paramLocale);
    localReportDataItem = localReportDataItems.add();
    localReportDataItem.setData("");
    localReportRow = new ReportRow(paramLocale);
    localReportRow.setDataItems(localReportDataItems);
    localReportResult.addRow(localReportRow);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.istatements.StatementDataService
 * JD-Core Version:    0.7.0.1
 */