package com.ffusion.csil.core;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.istatements.Statement;
import com.ffusion.beans.istatements.Statements;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.StatementDataHandler;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class StatementData
  extends Initialize
{
  private static Entitlement aB3 = new Entitlement("OnlineStatement", null, null);
  private static final String aB2 = "com.ffusion.util.logging.audit.statementdata";
  private static final String aBX = "AuditMessage_1";
  private static final String aB4 = "AuditMessage_2";
  private static final String aBZ = "AuditMessage_3";
  private static final String aB0 = "AuditMessage_4";
  private static final String aBW = "AuditMessage_5";
  private static final String aB1 = "AuditMessage_6";
  private static final String aBV = "AuditMessage_7";
  private static final String aBY = "AuditMessage_8";
  public static final String SECONDARY_USER_USER_NAME_KEY = "SECONDARY_USER_USER_NAME";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    StatementDataHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return StatementDataHandler.getService();
  }
  
  public static String getStatementID(SecureUser paramSecureUser, String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = null;
    String str2 = "StatementData.getStatementID";
    CSILException localCSILException;
    if (paramSecureUser == null)
    {
      localCSILException = new CSILException(str2, 300, "The SecureUser specified is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramString == null)
    {
      localCSILException = new CSILException(str2, 300, "The account number specified is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramDateTime == null)
    {
      localCSILException = new CSILException(str2, 300, "The statement date specified is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (!h(paramSecureUser))
    {
      localCSILException = new CSILException(str2, 20001);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw localCSILException;
    }
    long l = System.currentTimeMillis();
    str1 = StatementDataHandler.getStatementID(paramSecureUser, paramString, paramDateTime, paramHashMap);
    PerfLog.log(str2, l, true);
    debug(paramSecureUser, str2);
    return str1;
  }
  
  public static Statements getStatementList(SecureUser paramSecureUser, Accounts paramAccounts)
    throws CSILException
  {
    String str = "StatementData.getStatementList";
    if (h(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      Statements localStatements = StatementDataHandler.getStatementList(paramAccounts);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStatements;
    }
    throw new CSILException(str, 20001);
  }
  
  public static Statement getStatement(SecureUser paramSecureUser, Statement paramStatement)
    throws CSILException
  {
    String str = "StatementData.getStatement";
    if (h(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      Statement localStatement = StatementDataHandler.getStatement(paramStatement);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStatement;
    }
    throw new CSILException(str, 20001);
  }
  
  public static Statement getFullStatement(SecureUser paramSecureUser, Statement paramStatement)
    throws CSILException
  {
    String str = "StatementData.getFullStatement";
    if (h(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      Statement localStatement = StatementDataHandler.getFullStatement(paramStatement);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localStatement;
    }
    throw new CSILException(str, 20001);
  }
  
  public static void addAccountsForIStatement(SecureUser paramSecureUser, Accounts paramAccounts)
    throws CSILException
  {
    String str1 = "StatementData.addAccountsForIStatement";
    if (h(paramSecureUser))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      StatementDataHandler.addAccountsForIStatement(paramSecureUser, paramAccounts);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      for (int i = 0; i < paramAccounts.size(); i++)
      {
        Account localAccount = (Account)paramAccounts.get(i);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localAccount.buildLocalizableAccountID();
        String str2 = TrackingIDGenerator.GetNextID();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_4", arrayOfObject);
        audit(paramSecureUser, localLocalizableString, str2, 1211);
      }
    }
    else
    {
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void removeAccountsForIStatement(SecureUser paramSecureUser, Accounts paramAccounts)
    throws CSILException
  {
    String str1 = "StatementData.removeAccountsForIStatement";
    if (h(paramSecureUser))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      StatementDataHandler.removeAccountsForIStatement(paramAccounts);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      for (int i = 0; i < paramAccounts.size(); i++)
      {
        Account localAccount = (Account)paramAccounts.get(i);
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = localAccount.buildLocalizableAccountID();
        String str2 = TrackingIDGenerator.GetNextID();
        LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_5", arrayOfObject);
        audit(paramSecureUser, localLocalizableString, str2, 1212);
      }
    }
    else
    {
      throw new CSILException(str1, 20001);
    }
  }
  
  public static void getPDF(SecureUser paramSecureUser, Statement paramStatement, boolean paramBoolean, OutputStream paramOutputStream, Locale paramLocale)
    throws CSILException
  {
    String str = "StatementData.getPDF";
    if (h(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      StatementDataHandler.getPDF(paramStatement, paramBoolean, paramOutputStream, paramLocale);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      throw new CSILException(str, 20001);
    }
  }
  
  public static void getCSV(SecureUser paramSecureUser, Statement paramStatement, PrintWriter paramPrintWriter, Locale paramLocale)
    throws CSILException
  {
    String str = "StatementData.getCSV";
    if (h(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      StatementDataHandler.getCSV(paramStatement, paramPrintWriter, paramLocale);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      throw new CSILException(str, 20001);
    }
  }
  
  public static Accounts getAccountsForIStatement(SecureUser paramSecureUser)
    throws CSILException
  {
    String str = "StatementData.getAccountsForIStatement";
    if (h(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      Accounts localAccounts = StatementDataHandler.getAccountsForIStatement(paramSecureUser);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAccounts;
    }
    throw new CSILException(str, 20001);
  }
  
  public static String getStatementAgreement(SecureUser paramSecureUser)
    throws CSILException
  {
    String str1 = "StatementData.getStatementAgreement";
    if (h(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      String str2 = StatementDataHandler.getStatementAgreement(paramSecureUser);
      PerfLog.log(str1, l, true);
      debug(paramSecureUser, str1);
      return str2;
    }
    throw new CSILException(str1, 20001);
  }
  
  public static void setStatementAgreement(SecureUser paramSecureUser, String paramString)
    throws CSILException
  {
    String str = "StatementData.setStatementAgreement";
    if (h(paramSecureUser))
    {
      EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
      long l = System.currentTimeMillis();
      StatementDataHandler.setStatementAgreement(paramSecureUser, paramString);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
    }
    else
    {
      throw new CSILException(str, 20001);
    }
  }
  
  public static FilteredList getStatementDates(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    FilteredList localFilteredList = null;
    String str = "StatementData.getStatementDates";
    CSILException localCSILException;
    if (paramSecureUser == null)
    {
      localCSILException = new CSILException(str, 300, "The SecureUser specified is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramString == null)
    {
      localCSILException = new CSILException(str, 300, "The account number specified is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (!h(paramSecureUser))
    {
      localCSILException = new CSILException(str, 20001);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw localCSILException;
    }
    long l = System.currentTimeMillis();
    localFilteredList = StatementDataHandler.getStatementDates(paramSecureUser, paramString, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localFilteredList;
  }
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    IReportResult localIReportResult = null;
    String str1 = "StatementData.getReportData";
    Properties localProperties1 = null;
    Properties localProperties2 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    boolean bool = false;
    CSILException localCSILException;
    if (paramSecureUser == null)
    {
      localCSILException = new CSILException(str1, 300, "The SecureUser specified is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramReportCriteria == null)
    {
      localCSILException = new CSILException(str1, 300, "The criteria specified is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    localProperties1 = paramReportCriteria.getSearchCriteria();
    localProperties2 = paramReportCriteria.getReportOptions();
    if (localProperties1 == null)
    {
      localCSILException = new CSILException(str1, 300, "The report search criteria specified is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (localProperties2 == null)
    {
      localCSILException = new CSILException(str1, 300, "The report options specified is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    str2 = localProperties1.getProperty("AccountNumber");
    str3 = localProperties1.getProperty("StatementDate");
    str4 = localProperties2.getProperty("FORMAT");
    str5 = localProperties2.getProperty("DESTINATION");
    bool = Boolean.valueOf(localProperties2.getProperty("WithImages_HIDE")).booleanValue();
    if (str2 == null)
    {
      localCSILException = new CSILException(str1, 300, "No account number report criterion has been specified.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (str3 == null)
    {
      localCSILException = new CSILException(str1, 300, "No statement date report criterion has been specified.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (str4 == null)
    {
      localCSILException = new CSILException(str1, 300, "No report format option has been specified.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (str5 == null)
    {
      localCSILException = new CSILException(str1, 300, "No report destination option has been specified.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (!h(paramSecureUser))
    {
      localCSILException = new CSILException(str1, 20001);
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw localCSILException;
    }
    long l = System.currentTimeMillis();
    Reporting.prepareForReport(paramSecureUser, paramReportCriteria, paramHashMap);
    localIReportResult = StatementDataHandler.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
    PerfLog.log(str1, l, true);
    debug(paramSecureUser, str1);
    Object[] arrayOfObject = null;
    LocalizableString localLocalizableString2 = null;
    String str6 = TrackingIDGenerator.GetNextID();
    if ("USER_FILE_SYSTEM".equals(str5))
    {
      arrayOfObject = new Object[] { str2, str3, ReportConsts.getFormat(str4, paramSecureUser.getLocale()) };
      int i = 0;
      if ("COMMA".equals(str4)) {
        i = 1206;
      } else if ("TAB".equals(str4)) {
        i = 1207;
      } else if ("TEXT".equals(str4)) {
        i = 1208;
      } else if ("PDF".equals(str4))
      {
        if (bool) {
          i = 1210;
        } else {
          i = 1209;
        }
      }
      else {
        i = 1205;
      }
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_2", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str6, i);
    }
    else
    {
      arrayOfObject = new Object[] { str2, str3 };
      localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_3", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str6, 1205);
    }
    return localIReportResult;
  }
  
  public static Accounts getAllAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "StatementData.getAllAccounts";
    if (h(paramSecureUser))
    {
      long l = System.currentTimeMillis();
      Accounts localAccounts = StatementDataHandler.getAllAccounts(paramSecureUser, paramHashMap);
      PerfLog.log(str, l, true);
      debug(paramSecureUser, str);
      return localAccounts;
    }
    throw new CSILException(str, 20001);
  }
  
  public static void addAccountsForIStatement(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "StatementData.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)";
    CSILException localCSILException;
    if (paramSecureUser == null)
    {
      localCSILException = new CSILException("StatementData.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 300, "The specified SecureUser is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramString == null)
    {
      localCSILException = new CSILException("StatementData.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 300, "The specified user identifier is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramAccounts == null)
    {
      localCSILException = new CSILException("StatementData.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 300, "The specified collection of accounts is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramHashMap == null)
    {
      localCSILException = new CSILException("StatementData.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 300, "The specified extra hashmap is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramHashMap.get("SECONDARY_USER_USER_NAME") == null)
    {
      localCSILException = new CSILException("StatementData.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 300, "The specified extra hashmap does not contain a value for the SECONDARY_USER_USER_NAME key.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (!h(paramSecureUser))
    {
      localCSILException = new CSILException("StatementData.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 20001);
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw localCSILException;
    }
    long l = System.currentTimeMillis();
    StatementDataHandler.addAccountsForIStatement(paramSecureUser, paramString, paramAccounts, paramHashMap);
    PerfLog.log("StatementData.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)", l, true);
    debug(paramSecureUser, "StatementData.addAccountsForIStatement(SecureUser, String, Accounts, HashMap)");
    for (int i = 0; i < paramAccounts.size(); i++)
    {
      Account localAccount = (Account)paramAccounts.get(i);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = localAccount.buildLocalizableAccountID();
      arrayOfObject[1] = paramHashMap.get("SECONDARY_USER_USER_NAME");
      String str2 = TrackingIDGenerator.GetNextID();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_6", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 1211);
    }
  }
  
  public static void removeAccountsForIStatement(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "StatementData.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)";
    CSILException localCSILException;
    if (paramSecureUser == null)
    {
      localCSILException = new CSILException("StatementData.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 300, "The specified SecureUser is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramString == null)
    {
      localCSILException = new CSILException("StatementData.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 300, "The specified user identifier is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramAccounts == null)
    {
      localCSILException = new CSILException("StatementData.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 300, "The specified collection of accounts is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramHashMap == null)
    {
      localCSILException = new CSILException("StatementData.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 300, "The specified extra hashmap is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramHashMap.get("SECONDARY_USER_USER_NAME") == null)
    {
      localCSILException = new CSILException("StatementData.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 300, "The specified extra hashmap does not contain a value for the SECONDARY_USER_USER_NAME key.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (!h(paramSecureUser))
    {
      localCSILException = new CSILException("StatementData.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)", 20001);
      LocalizableString localLocalizableString1 = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString1, TrackingIDGenerator.GetNextID());
      throw localCSILException;
    }
    long l = System.currentTimeMillis();
    StatementDataHandler.removeAccountsForIStatement(paramSecureUser, paramString, paramAccounts, paramHashMap);
    PerfLog.log("StatementData.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)", l, true);
    debug(paramSecureUser, "StatementData.removeAccountsForIStatement(SecureUser, String, Accounts, HashMap)");
    for (int i = 0; i < paramAccounts.size(); i++)
    {
      Account localAccount = (Account)paramAccounts.get(i);
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = localAccount.buildLocalizableAccountID();
      arrayOfObject[1] = paramHashMap.get("SECONDARY_USER_USER_NAME");
      String str2 = TrackingIDGenerator.GetNextID();
      LocalizableString localLocalizableString2 = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_7", arrayOfObject);
      audit(paramSecureUser, localLocalizableString2, str2, 1212);
    }
  }
  
  public static Accounts getAccountsForIStatement(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "StatementData.getAccountsForIStatement(SecureUser, String, HashMap)";
    Accounts localAccounts = null;
    CSILException localCSILException;
    if (paramSecureUser == null)
    {
      localCSILException = new CSILException("StatementData.getAccountsForIStatement(SecureUser, String, HashMap)", 300, "The specified SecureUser is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (paramString == null)
    {
      localCSILException = new CSILException("StatementData.getAccountsForIStatement(SecureUser, String, HashMap)", 300, "The specified user identifier is null.");
      DebugLog.throwing(localCSILException.getLocalizedMessage(), localCSILException);
      throw localCSILException;
    }
    if (!h(paramSecureUser))
    {
      localCSILException = new CSILException("StatementData.getAccountsForIStatement(SecureUser, String, HashMap)", 20001);
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_1", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw localCSILException;
    }
    long l = System.currentTimeMillis();
    localAccounts = StatementDataHandler.getAccountsForIStatement(paramSecureUser, paramString, paramHashMap);
    PerfLog.log("StatementData.getAccountsForIStatement(SecureUser, String, HashMap)", l, true);
    debug(paramSecureUser, "StatementData.getAccountsForIStatement(SecureUser, String, HashMap)");
    return localAccounts;
  }
  
  private static boolean h(SecureUser paramSecureUser)
    throws CSILException
  {
    boolean bool = Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), aB3);
    if (!bool)
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.statementdata", "AuditMessage_8", null);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.StatementData
 * JD-Core Version:    0.7.0.1
 */