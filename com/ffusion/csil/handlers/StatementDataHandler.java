package com.ffusion.csil.handlers;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.istatements.Statement;
import com.ffusion.beans.istatements.Statements;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.istatements.IStatementException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.AccountService3;
import com.ffusion.services.StatementData;
import com.ffusion.services.StatementData2;
import com.ffusion.util.FilteredList;
import com.ffusion.util.logging.DebugLog;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;

public class StatementDataHandler
{
  private static final String jdField_if = "Online Statement";
  private static StatementData a = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "StatementDataHandler.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Online Statement", str, 20107);
    a = (StatementData)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      a.initialize(paramHashMap);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36000, localIStatementException);
      DebugLog.throwing(str, localIStatementException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return a;
  }
  
  public static Statements getStatementList(Accounts paramAccounts)
    throws CSILException
  {
    String str = "StatementDataHandler.getStatementList";
    try
    {
      return a.getStatementList(paramAccounts);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36001, localIStatementException);
      DebugLog.throwing("StatementDataHandler.getImageIndex", localIStatementException);
      throw localCSILException;
    }
  }
  
  public static Statement getStatement(Statement paramStatement)
    throws CSILException
  {
    String str = "StatementDataHandler.getStatement";
    try
    {
      return a.getStatement(paramStatement);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36002, localIStatementException);
      DebugLog.throwing("StatementDataHandler.getStatement", localIStatementException);
      throw localCSILException;
    }
  }
  
  public static Statement getFullStatement(Statement paramStatement)
    throws CSILException
  {
    String str = "StatementDataHandler.getFullStatement";
    try
    {
      return a.getFullStatement(paramStatement);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36003, localIStatementException);
      DebugLog.throwing("StatementDataHandler.getFullStatement", localIStatementException);
      throw localCSILException;
    }
  }
  
  public static void addAccountsForIStatement(SecureUser paramSecureUser, Accounts paramAccounts)
    throws CSILException
  {
    String str = "StatementDataHandler.addAccountsForIStatement";
    try
    {
      a.addAccountsForIStatement(paramSecureUser, paramAccounts);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36004, localIStatementException);
      DebugLog.throwing("StatementDataHandler.addAccountsForIStatement", localIStatementException);
      throw localCSILException;
    }
  }
  
  public static void removeAccountsForIStatement(Accounts paramAccounts)
    throws CSILException
  {
    String str = "StatementDataHandler.removeAccountsForIStatement";
    try
    {
      a.removeAccountsForIStatement(paramAccounts);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36005, localIStatementException);
      DebugLog.throwing("StatementDataHandler.removeAccountsForIStatement", localIStatementException);
      throw localCSILException;
    }
  }
  
  public static void getPDF(Statement paramStatement, boolean paramBoolean, OutputStream paramOutputStream, Locale paramLocale)
    throws CSILException
  {
    String str = "StatementDataHandler.getPDF";
    try
    {
      a.getPDF(paramStatement, paramBoolean, paramOutputStream, paramLocale);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36006, localIStatementException);
      DebugLog.throwing("StatementDataHandler.getPDF", localIStatementException);
      throw localCSILException;
    }
  }
  
  public static void getCSV(Statement paramStatement, PrintWriter paramPrintWriter, Locale paramLocale)
    throws CSILException
  {
    String str = "StatementDataHandler.getCSV";
    try
    {
      a.getCSV(paramStatement, paramPrintWriter, paramLocale);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36007, localIStatementException);
      DebugLog.throwing("StatementDataHandler.getCSV", localIStatementException);
      throw localCSILException;
    }
  }
  
  public static Accounts getAccountsForIStatement(SecureUser paramSecureUser)
    throws CSILException
  {
    String str = "StatementDataHandler.getAccountsForIStatement";
    try
    {
      return a.getAccountsForIStatement(paramSecureUser);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36008, localIStatementException);
      DebugLog.throwing("StatementDataHandler.getAccountsForIStatement", localIStatementException);
      throw localCSILException;
    }
  }
  
  public static String getStatementAgreement(SecureUser paramSecureUser)
    throws CSILException
  {
    String str = "StatementDataHandler.getStatementAgreement";
    try
    {
      return a.getStatementAgreement(paramSecureUser);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36300, localIStatementException);
      DebugLog.throwing("StatementDataHandler.getStatementAgreement", localIStatementException);
      throw localCSILException;
    }
  }
  
  public static void setStatementAgreement(SecureUser paramSecureUser, String paramString)
    throws CSILException
  {
    String str = "StatementDataHandler.setStatementAgreement";
    try
    {
      a.setStatementAgreement(paramSecureUser, paramString);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36310, localIStatementException);
      DebugLog.throwing("StatementDataHandler.setStatementAgreement", localIStatementException);
      throw localCSILException;
    }
  }
  
  public static Accounts getAllAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "StatementDataHandler.getAllAccounts";
    Object localObject = (AccountService3)paramHashMap.get("SERVICE");
    if (localObject == null)
    {
      localObject = AccountHandler.getAccountService();
      paramHashMap.put("SERVICE", localObject);
    }
    try
    {
      Accounts localAccounts = ((AccountService3)localObject).getAccounts(paramSecureUser, paramHashMap);
      return localAccounts;
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(36320, localException);
      DebugLog.throwing("StatementDataHandler.getAllAccounts", localException);
      throw localCSILException;
    }
  }
  
  public static String getStatementID(SecureUser paramSecureUser, String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = null;
    String str2 = "StatementDataHandler.getStatementID";
    try
    {
      str1 = ((StatementData2)a).getStatementID(paramSecureUser, paramString, paramDateTime, paramHashMap);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36009, localIStatementException, str2, "An error occurred in the Statement Data service while attempting to retrieve the requested statement ID.");
      localCSILException.setServiceError(localIStatementException.getCode());
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    return str1;
  }
  
  public static FilteredList getStatementDates(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    FilteredList localFilteredList = null;
    String str = "StatementDataHandler.getStatementDates";
    try
    {
      localFilteredList = ((StatementData2)a).getStatementDates(paramSecureUser, paramString, paramHashMap);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36010, localIStatementException, str, "An error occurred in the Statement Data service while attempting to retrieve the requested statement dates.");
      localCSILException.setServiceError(localIStatementException.getCode());
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    return localFilteredList;
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    IReportResult localIReportResult = null;
    String str = "StatementDataHandler.getReportData";
    try
    {
      localIReportResult = ((StatementData2)a).getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36011, localIStatementException, str, "An error occurred in the Statement Data service while attempting to retrieve the requested statement report data.");
      localCSILException.setServiceError(localIStatementException.getCode());
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    return localIReportResult;
  }
  
  public static void addAccountsForIStatement(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "StatementDataHandler.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)";
    try
    {
      ((StatementData2)a).addAccountsForIStatement(paramSecureUser, paramString, paramAccounts, paramHashMap);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36012, localIStatementException, "StatementDataHandler.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)", "An error occurred in the Statement Data service while attempting to add online statementaccounts for the specified user.");
      localCSILException.setServiceError(localIStatementException.getCode());
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
  }
  
  public static void removeAccountsForIStatement(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str = "StatementDataHandler.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)";
    try
    {
      ((StatementData2)a).removeAccountsForIStatement(paramSecureUser, paramString, paramAccounts, paramHashMap);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36013, localIStatementException, "StatementDataHandler.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)", "An error occurred in the Statement Data service while attempting to remove online statementaccounts for the specified user.");
      localCSILException.setServiceError(localIStatementException.getCode());
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
  }
  
  public static Accounts getAccountsForIStatement(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "StatementDataHandler.getAccountsForIStatement(SecureUser, String, HashMap)";
    Accounts localAccounts = null;
    try
    {
      localAccounts = ((StatementData2)a).getAccountsForIStatement(paramSecureUser, paramString, paramHashMap);
    }
    catch (IStatementException localIStatementException)
    {
      CSILException localCSILException = new CSILException(36014, localIStatementException, "StatementDataHandler.getAccountsForIStatement(SecureUser, String, HashMap)", "An error occurred in the Statement Data service while attempting to retrieve online statementaccounts for the specified user.");
      localCSILException.setServiceError(localIStatementException.getCode());
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    return localAccounts;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.StatementDataHandler
 * JD-Core Version:    0.7.0.1
 */