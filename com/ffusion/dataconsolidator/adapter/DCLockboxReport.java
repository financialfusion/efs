package com.ffusion.dataconsolidator.adapter;

import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.AccountKey;
import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxAccounts;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportColumns;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.Vector;

public class DCLockboxReport
{
  private static final String P = "dcAccount.DCAccountID";
  private static final String Y = "dcAccount.BankID";
  private static final String jdField_if = "dcAccount.AccountID";
  private static final String K = "dcAccount.RoutingNumber";
  private static final String Z = "dcAccount.CurrencyCode";
  private static final String a = "dcLockbox.DCAccountID";
  private static final String H = "dcLockbox.LockboxNumber";
  private static final String ab = "dcLockbox.DCLockboxID";
  private static final String jdField_try = "dcLockboxSummary.DCAccountID";
  private static final String U = "dcLockboxSummary.DCLockboxID";
  private static final String f = "dcLockboxSummary.DataDate";
  private static final String R = "dcLockboxSummary.TotalCredits";
  private static final String jdField_for = "dcLockboxSummary.TotalDebits";
  private static final String x = "dcLockboxSummary.TotalNumCredits";
  private static final String d = "dcLockboxSummary.TotalNumDebits";
  private static final String O = "dcLockboxSummary.ImmediateFloat";
  private static final String y = "dcLockboxSummary.OneDayFloat";
  private static final String c = "dcLockboxSummary.TwoDayFloat";
  private static final String m = "dcLockboxSummary.DataClassification";
  private static final String k = "dcLBCreditItems.DCLockboxID";
  private static final String B = "dcLBCreditItems.DCCreditItemIndex";
  private static final String h = "dcLBCreditItems.ItemID";
  private static final String T = "dcLBCreditItems.DataDate";
  private static final String jdField_do = "dcLBCreditItems.DocumentType";
  private static final String I = "dcLBCreditItems.Payor";
  private static final String N = "dcLBCreditItems.Amount";
  private static final String z = "dcLBCreditItems.CheckNumber";
  private static final String l = "dcLBCreditItems.CheckDate";
  private static final String b = "dcLBCreditItems.CouponAccountNum";
  private static final String F = "dcLBCreditItems.CouponAmount1";
  private static final String E = "dcLBCreditItems.CouponAmount2";
  private static final String u = "dcLBCreditItems.CouponDate1";
  private static final String r = "dcLBCreditItems.CouponDate2";
  private static final String v = "dcLBCreditItems.CouponRefNum";
  private static final String jdField_case = "dcLBCreditItems.CheckRoutingNum";
  private static final String jdField_long = "dcLBCreditItems.CheckAccountNum";
  private static final String S = "dcLBCreditItems.LockboxWorkType";
  private static final String jdField_new = "dcLBCreditItems.LockboxBatchNum";
  private static final String t = "dcLBCreditItems.LockboxSeqNum";
  private static final String j = "dcLBCreditItems.Memo";
  private static final String M = "dcLBCreditItems.ImmedAvailAmount";
  private static final String jdField_void = "dcLBCreditItems.OneDayAvailAmount";
  private static final String jdField_int = "dcLBCreditItems.MoreOneDayAvailAm";
  private static final String G = "dcLBCreditItems.ValueDateTime";
  private static final String aa = "dcLBCreditItems.BankRefNum";
  private static final String jdField_byte = "dcLBCreditItems.CustRefNum";
  private static final String s = "dcLBCreditItems.ExtendABeanXMLID";
  private static final String jdField_char = "dcLBCreditItems.DataClassification";
  private static final String jdField_null = "dcLBTransactions.DCAccountID";
  private static final String X = "dcLBTransactions.LockboxNumber";
  private static final String A = "dcLBTransactions.DCTransactionIndex";
  private static final String o = "dcLBTransactions.TransID";
  private static final String n = "dcLBTransactions.TransTypeID";
  private static final String w = "dcLBTransactions.DataDate";
  private static final String jdField_goto = "dcLBTransactions.Amount";
  private static final String J = "dcLBTransactions.Description";
  private static final String g = "dcLBTransactions.NumRejectedChecks";
  private static final String ac = "dcLBTransactions.RejectedAmount";
  private static final String q = "dcLBTransactions.ImmedAvailAmount";
  private static final String i = "dcLBTransactions.OneDayAvailAmount";
  private static final String e = "dcLBTransactions.MoreOneDayAvailAm";
  private static final String W = "dcLBTransactions.ValueDateTime";
  private static final String p = "dcLBTransactions.BankRefNum";
  private static final String D = "dcLBTransactions.CustRefNum";
  private static final String L = "dcLBTransactions.ExtendABeanXMLID";
  private static final String C = "dcLBTransactions.DataClassification";
  private static final String Q = "SELECT dcAccount.BankID, dcAccount.AccountID, dcAccount.CurrencyCode, dcAccount.RoutingNumber, dcLockboxSummary.DataDate, dcLockboxSummary.TotalCredits, dcLockboxSummary.TotalDebits, dcLockboxSummary.TotalNumCredits, dcLockboxSummary.TotalNumDebits, dcLockboxSummary.ImmediateFloat, dcLockboxSummary.OneDayFloat, dcLockboxSummary.TwoDayFloat FROM DC_Account dcAccount, DC_LockboxSummary dcLockboxSummary ";
  private static final String V = "SELECT dcAccount.BankID, dcAccount.AccountID, dcAccount.CurrencyCode, dcLockbox.LockboxNumber, dcLBCreditItems.DCCreditItemIndex, dcLBCreditItems.ItemID, dcLBCreditItems.DataDate, dcLBCreditItems.DocumentType, dcLBCreditItems.Payor, dcLBCreditItems.Amount, dcLBCreditItems.CheckNumber, dcLBCreditItems.CheckDate, dcLBCreditItems.CouponAccountNum, dcLBCreditItems.CouponAmount1, dcLBCreditItems.CouponAmount2, dcLBCreditItems.CouponDate1, dcLBCreditItems.CouponDate2, dcLBCreditItems.CouponRefNum, dcLBCreditItems.CheckRoutingNum, dcLBCreditItems.CheckAccountNum, dcLBCreditItems.LockboxWorkType, dcLBCreditItems.LockboxBatchNum, dcLBCreditItems.LockboxSeqNum, dcLBCreditItems.Memo, dcLBCreditItems.ImmedAvailAmount, dcLBCreditItems.OneDayAvailAmount, dcLBCreditItems.MoreOneDayAvailAm, dcLBCreditItems.ValueDateTime, dcLBCreditItems.BankRefNum, dcLBCreditItems.CustRefNum, dcLBCreditItems.ExtendABeanXMLID FROM DC_Account dcAccount, DC_Lockbox dcLockbox, DC_LBCreditItems dcLBCreditItems ";
  private static final String jdField_else = "SELECT dcAccount.AccountID, dcAccount.BankID, dcAccount.CurrencyCode, dcLBTransactions.LockboxNumber, dcLBTransactions.DCTransactionIndex, dcLBTransactions.TransID, dcLBTransactions.TransTypeID, dcLBTransactions.DataDate, dcLBTransactions.Amount, dcLBTransactions.Description, dcLBTransactions.ImmedAvailAmount, dcLBTransactions.OneDayAvailAmount, dcLBTransactions.MoreOneDayAvailAm, dcLBTransactions.NumRejectedChecks, dcLBTransactions.RejectedAmount, dcLBTransactions.ValueDateTime, dcLBTransactions.BankRefNum, dcLBTransactions.CustRefNum, dcLBTransactions.ExtendABeanXMLID FROM DC_Account dcAccount, DC_LBTransactions dcLBTransactions ";
  
  private static String a(ReportCriteria paramReportCriteria, String paramString, Vector paramVector, HashMap paramHashMap)
    throws DCException
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    StringBuffer localStringBuffer = null;
    if (localProperties != null) {
      localStringBuffer = a(paramReportCriteria, paramVector, paramString);
    }
    String str1 = new String(localStringBuffer).trim();
    if (str1.length() != 0) {
      localStringBuffer.append(" AND ");
    }
    String str2 = (String)localProperties.get("LockboxNumber");
    if (str2 == null) {
      str2 = "ALL";
    } else if (jdField_if(str2)) {
      str2 = "ALL";
    }
    String str3 = a(str2, paramString, paramVector, paramHashMap);
    if (str3.length() != 0)
    {
      localStringBuffer.append(str3);
      localStringBuffer.append(" AND ");
    }
    if (paramString.equals("LockboxSummary")) {
      localStringBuffer.append("dcAccount.DCAccountID = dcLockboxSummary.DCAccountID");
    } else if (paramString.equals("DepositItemSearch")) {
      localStringBuffer.append("dcLockbox.DCAccountID = dcAccount.DCAccountID AND dcLockbox.DCLockboxID = dcLBCreditItems.DCLockboxID");
    } else if (paramString.equals("LockboxDepositReport")) {
      localStringBuffer.append("dcAccount.DCAccountID = dcLBTransactions.DCAccountID");
    }
    return localStringBuffer.toString();
  }
  
  private static String a(String paramString)
  {
    if (paramString.equals("LockboxSummary")) {
      return "dcLockboxSummary.DataDate";
    }
    if (paramString.equals("LockboxDepositReport")) {
      return "dcLBTransactions.DataDate";
    }
    if (paramString.equals("DepositItemSearch")) {
      return "dcLBCreditItems.DataDate";
    }
    return null;
  }
  
  private static String a(String paramString1, String paramString2, Vector paramVector, HashMap paramHashMap)
    throws DCException
  {
    HashMap localHashMap1 = (HashMap)paramHashMap.get("StartDates");
    if (localHashMap1 == null)
    {
      localObject1 = "com.ffusion.beans.reporting.ReportConsts.START_DATES_FOR_REPORT not in extra hash map.";
      DebugLog.log((String)localObject1);
      throw new DCException((String)localObject1);
    }
    Object localObject1 = (HashMap)paramHashMap.get("EndDates");
    if (localObject1 == null)
    {
      str1 = "com.ffusion.beans.reporting.ReportConsts.END_DATES_FOR_REPORT not in extra hash map.";
      DebugLog.log(str1);
      throw new DCException(str1);
    }
    String str1 = null;
    Object localObject2;
    Object localObject3;
    Object localObject4;
    Object localObject5;
    Object localObject6;
    Object localObject7;
    Object localObject8;
    if (paramString1.equals("ALL"))
    {
      localObject2 = new StringBuffer();
      if (paramHashMap == null) {
        throw new DCException("Extra hashmap is null.");
      }
      LockboxAccounts localLockboxAccounts = (LockboxAccounts)paramHashMap.get("LOCKBOX_ACCOUNTS");
      if (localLockboxAccounts == null) {
        throw new DCException("The LockboxAccounts object was not found in extra.");
      }
      localObject3 = localLockboxAccounts.iterator();
      if (!((Iterator)localObject3).hasNext()) {
        throw new DCException("No LockboxAccounts defined in extra.");
      }
      HashMap localHashMap2 = new HashMap();
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (LockboxAccount)((Iterator)localObject3).next();
        if (localHashMap2.containsKey(((LockboxAccount)localObject4).getRoutingNumber()))
        {
          localObject5 = (Vector)localHashMap2.get(((LockboxAccount)localObject4).getRoutingNumber());
        }
        else
        {
          localObject5 = new Vector();
          localHashMap2.put(((LockboxAccount)localObject4).getRoutingNumber(), localObject5);
        }
        ((Vector)localObject5).add(localObject4);
      }
      ((StringBuffer)localObject2).append("( ");
      localObject4 = localHashMap2.keySet().iterator();
      while (((Iterator)localObject4).hasNext())
      {
        localObject5 = (String)((Iterator)localObject4).next();
        Vector localVector = (Vector)localHashMap2.get(localObject5);
        ((StringBuffer)localObject2).append("( ");
        localObject6 = localVector.iterator();
        ((StringBuffer)localObject2).append("( ");
        while (((Iterator)localObject6).hasNext())
        {
          localObject7 = (LockboxAccount)((Iterator)localObject6).next();
          ((StringBuffer)localObject2).append("( ");
          ((StringBuffer)localObject2).append("dcAccount.AccountID");
          ((StringBuffer)localObject2).append(" = '");
          ((StringBuffer)localObject2).append(DBUtil.escapeSQLStringLiteral(((LockboxAccount)localObject7).getAccountID()));
          ((StringBuffer)localObject2).append("' AND ");
          ((StringBuffer)localObject2).append("dcAccount.BankID");
          ((StringBuffer)localObject2).append(" = '");
          ((StringBuffer)localObject2).append(DBUtil.escapeSQLStringLiteral(((LockboxAccount)localObject7).getBankID()));
          if (((LockboxAccount)localObject7).getRoutingNumber() != null)
          {
            ((StringBuffer)localObject2).append("' AND ");
            ((StringBuffer)localObject2).append("dcAccount.RoutingNumber");
            ((StringBuffer)localObject2).append(" = '");
            ((StringBuffer)localObject2).append(DBUtil.escapeSQLStringLiteral(((LockboxAccount)localObject7).getRoutingNumber()));
          }
          ((StringBuffer)localObject2).append("'");
          ((StringBuffer)localObject2).append(" )");
          if (((Iterator)localObject6).hasNext()) {
            ((StringBuffer)localObject2).append(" OR ");
          }
        }
        ((StringBuffer)localObject2).append(" )");
        localObject7 = (Calendar)localHashMap1.get(localObject5);
        if (localObject7 != null)
        {
          ((StringBuffer)localObject2).append(" AND ");
          ((StringBuffer)localObject2).append(a(paramString2));
          ((StringBuffer)localObject2).append(" >= ?");
          paramVector.add(new Long(((Calendar)localObject7).getTime().getTime()));
        }
        localObject8 = (Calendar)((HashMap)localObject1).get(localObject5);
        if (localObject8 != null)
        {
          ((StringBuffer)localObject2).append(" AND ");
          ((StringBuffer)localObject2).append(a(paramString2));
          ((StringBuffer)localObject2).append(" <= ?");
          paramVector.add(new Long(((Calendar)localObject8).getTime().getTime()));
        }
        ((StringBuffer)localObject2).append(" ) ");
        if (((Iterator)localObject4).hasNext()) {
          ((StringBuffer)localObject2).append(" OR ");
        }
      }
      ((StringBuffer)localObject2).append(" ) ");
      str1 = ((StringBuffer)localObject2).toString();
    }
    else
    {
      localObject2 = new StringTokenizer(paramString1, ",");
      int i1 = ((StringTokenizer)localObject2).countTokens();
      if (i1 < 1) {
        throw new DCException("Value for SearchCriteria key Accounts has incorrect format.");
      }
      localObject3 = new StringBuffer();
      for (int i2 = 0; i2 < i1; i2++)
      {
        localObject4 = ((StringTokenizer)localObject2).nextToken();
        localObject5 = new StringTokenizer((String)localObject4, ":");
        int i3 = ((StringTokenizer)localObject5).countTokens();
        if (i3 < 2) {
          throw new DCException("The account id and bank ID were not correctly passed in as a report criteria. The proper format is AccountID:BankID[:LockboxNumber(optional):RoutingID(optional)]");
        }
        localObject6 = ((StringTokenizer)localObject5).nextToken();
        localObject7 = ((StringTokenizer)localObject5).nextToken();
        localObject8 = null;
        String str2 = null;
        if (i3 >= 3) {
          localObject8 = ((StringTokenizer)localObject5).nextToken();
        }
        if (i3 == 4) {
          str2 = ((StringTokenizer)localObject5).nextToken();
        }
        Calendar localCalendar1 = (Calendar)localHashMap1.get(str2);
        Calendar localCalendar2 = (Calendar)((HashMap)localObject1).get(str2);
        if (((StringBuffer)localObject3).length() != 0) {
          ((StringBuffer)localObject3).append(" OR ");
        } else {
          ((StringBuffer)localObject3).append("( ");
        }
        ((StringBuffer)localObject3).append(a(paramString2, (String)localObject6, (String)localObject7, str2, (String)localObject8, localCalendar1, localCalendar2, paramVector));
      }
      ((StringBuffer)localObject3).append(" )");
      str1 = ((StringBuffer)localObject3).toString();
    }
    return str1;
  }
  
  private static String a(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, Calendar paramCalendar1, Calendar paramCalendar2, Vector paramVector)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("( ");
    localStringBuffer.append("dcAccount.AccountID");
    localStringBuffer.append(" = '");
    localStringBuffer.append(DBUtil.escapeSQLStringLiteral(paramString2));
    localStringBuffer.append("' AND ");
    localStringBuffer.append("dcAccount.BankID");
    localStringBuffer.append(" = '");
    localStringBuffer.append(DBUtil.escapeSQLStringLiteral(paramString3));
    if ((paramString4 != null) && (!paramString4.trim().equals("")))
    {
      localStringBuffer.append("' AND ");
      localStringBuffer.append("dcAccount.RoutingNumber");
      localStringBuffer.append(" = '");
      localStringBuffer.append(DBUtil.escapeSQLStringLiteral(paramString4));
    }
    if ((paramString5 != null) && (paramString5.trim().length() > 0))
    {
      localStringBuffer.append("' AND ");
      localStringBuffer.append("dcLockbox.LockboxNumber");
      localStringBuffer.append(" = '");
      localStringBuffer.append(DBUtil.escapeSQLStringLiteral(paramString5));
    }
    localStringBuffer.append("'");
    if (paramCalendar1 != null)
    {
      localStringBuffer.append(" AND ");
      localStringBuffer.append(a(paramString1));
      localStringBuffer.append(" >= ?");
      paramVector.add(new Long(paramCalendar1.getTime().getTime()));
    }
    if (paramCalendar2 != null)
    {
      localStringBuffer.append(" AND ");
      localStringBuffer.append(a(paramString1));
      localStringBuffer.append(" <= ?");
      paramVector.add(new Long(paramCalendar2.getTime().getTime()));
    }
    localStringBuffer.append(") ");
    return localStringBuffer.toString();
  }
  
  private static com.ffusion.beans.DateTime a(String paramString, Locale paramLocale)
    throws ParseException
  {
    Date localDate = DateFormatUtil.getFormatter("MM/dd/yyyy").parse(paramString);
    return new com.ffusion.beans.DateTime(localDate, paramLocale);
  }
  
  private static StringBuffer a(ReportCriteria paramReportCriteria, Vector paramVector, String paramString)
    throws DCException
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    StringBuffer localStringBuffer = new StringBuffer();
    if (localProperties != null)
    {
      Set localSet = localProperties.keySet();
      Iterator localIterator = localSet.iterator();
      String str1 = null;
      String str2 = null;
      String str3 = null;
      String str4 = null;
      String str5 = null;
      while (localIterator.hasNext())
      {
        str1 = (String)localIterator.next();
        str2 = localProperties.getProperty(str1);
        if ((str2.trim().length() != 0) && (!str1.equals("LockboxNumber")))
        {
          str3 = null;
          str4 = null;
          if (paramString.equals("LockboxSummary"))
          {
            if ((!str1.equals("StartDate")) && (!str1.equals("EndDate")))
            {
              if (!str1.equals("DataClassification")) {
                continue;
              }
              str3 = "dcLockboxSummary.DataClassification";
              if (str2.equals("I")) {
                str4 = " ='I' ";
              } else {
                str4 = " ='P' ";
              }
            }
          }
          else if (paramString.equals("LockboxDepositReport"))
          {
            if ((!str1.equals("StartDate")) && (!str1.equals("EndDate"))) {
              if (str1.equals("MinimumAmount"))
              {
                str3 = "dcLBTransactions.Amount";
                if (!Currency.isValid(str2, paramReportCriteria.getLocale())) {
                  throw new DCException(str1 + " is not right currency format.");
                }
                str4 = ">=" + str2;
              }
              else if (str1.equals("MaximumAmount"))
              {
                if (!Currency.isValid(str2, paramReportCriteria.getLocale())) {
                  throw new DCException(str1 + " is not right currency format.");
                }
                str3 = "dcLBTransactions.Amount";
                str4 = "<=" + str2;
              }
              else
              {
                if (!str1.equals("DataClassification")) {
                  continue;
                }
                str3 = "dcLBTransactions.DataClassification";
                if (str2.equals("I")) {
                  str4 = " ='I' ";
                } else {
                  str4 = " ='P' ";
                }
              }
            }
          }
          else if (paramString.equals("DepositItemSearch"))
          {
            if ((!str1.equals("StartDate")) && (!str1.equals("EndDate"))) {
              if (str1.equals("Payor"))
              {
                str3 = "dcLBCreditItems.Payor";
                str4 = "='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("StartCheckNumber"))
              {
                str3 = "dcLBCreditItems.CheckNumber";
                str4 = ">='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("EndCheckNumber"))
              {
                str3 = "dcLBCreditItems.CheckNumber";
                str4 = "<='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("MinimumAmount"))
              {
                if (!Currency.isValid(str2, paramReportCriteria.getLocale())) {
                  throw new DCException(str1 + " is not right currency format.");
                }
                str3 = "dcLBCreditItems.Amount";
                str4 = ">=" + str2;
              }
              else if (str1.equals("MaximumAmount"))
              {
                if (!Currency.isValid(str2, paramReportCriteria.getLocale())) {
                  throw new DCException(str1 + " is not right currency format.");
                }
                str3 = "dcLBCreditItems.Amount";
                str4 = "<=" + str2;
              }
              else if (str1.equals("documentType"))
              {
                str3 = "dcLBCreditItems.DocumentType";
                str4 = "='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("StartCouponAccountNumber"))
              {
                str3 = "dcLBCreditItems.CouponAccountNum";
                str4 = ">='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("EndCouponAccountNumber"))
              {
                str3 = "dcLBCreditItems.CouponAccountNum";
                str4 = "<='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("MinimumCouponAmount1"))
              {
                if (!Currency.isValid(str2, paramReportCriteria.getLocale())) {
                  throw new DCException(str1 + " is not right currency format.");
                }
                str3 = "dcLBCreditItems.CouponAmount1";
                str4 = ">=" + str2;
              }
              else if (str1.equals("MaximumCouponAmount1"))
              {
                if (!Currency.isValid(str2, paramReportCriteria.getLocale())) {
                  throw new DCException(str1 + " is not right currency format.");
                }
                str3 = "dcLBCreditItems.CouponAmount1";
                str4 = "<=" + str2;
              }
              else if (str1.equals("MinimumCouponAmount2"))
              {
                if (!Currency.isValid(str2, paramReportCriteria.getLocale())) {
                  throw new DCException(str1 + " is not right currency format.");
                }
                str3 = "dcLBCreditItems.CouponAmount2";
                str4 = ">=" + str2;
              }
              else if (str1.equals("MaximumCouponAmount2"))
              {
                if (!Currency.isValid(str2, paramReportCriteria.getLocale())) {
                  throw new DCException(str1 + " is not right currency format.");
                }
                str3 = "dcLBCreditItems.CouponAmount2";
                str4 = "<=" + str2;
              }
              else if (str1.equals("StartCouponDate1"))
              {
                str3 = "dcLBCreditItems.CouponDate1";
                str4 = ">= ? ";
                try
                {
                  paramVector.add(a(str2, paramReportCriteria.getLocale()));
                }
                catch (ParseException localParseException1)
                {
                  throw new DCException(str1 + " is not in MM/DD/YYYY format.", localParseException1);
                }
              }
              else if (str1.equals("EndCouponDate1"))
              {
                str3 = "dcLBCreditItems.CouponDate1";
                str4 = "<= ? ";
                try
                {
                  paramVector.add(a(str2, paramReportCriteria.getLocale()));
                }
                catch (ParseException localParseException2)
                {
                  throw new DCException(str1 + " is not in MM/DD/YYYY format.", localParseException2);
                }
              }
              else if (str1.equals("StartCouponDate2"))
              {
                str3 = "dcLBCreditItems.CouponDate2";
                str4 = ">= ? ";
                try
                {
                  paramVector.add(a(str2, paramReportCriteria.getLocale()));
                }
                catch (ParseException localParseException3)
                {
                  throw new DCException(str1 + " is not in MM/DD/YYYY format.", localParseException3);
                }
              }
              else if (str1.equals("EndCouponDate2"))
              {
                str3 = "dcLBCreditItems.CouponDate2";
                str4 = "<= ? ";
                try
                {
                  paramVector.add(a(str2, paramReportCriteria.getLocale()));
                }
                catch (ParseException localParseException4)
                {
                  throw new DCException(str1 + " is not in MM/DD/YYYY format.", localParseException4);
                }
              }
              else if (str1.equals("StartCouponReferenceNumber"))
              {
                str3 = "dcLBCreditItems.CouponRefNum";
                str4 = ">='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("EndCouponReferenceNumber"))
              {
                str3 = "dcLBCreditItems.CouponRefNum";
                str4 = "<='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("StartCheckRoutingNumber"))
              {
                str3 = "dcLBCreditItems.CheckRoutingNum";
                str4 = ">='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("EndCheckRoutingNumber"))
              {
                str3 = "dcLBCreditItems.CheckRoutingNum";
                str4 = "<='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("StartCheckAccountNumber"))
              {
                str3 = "dcLBCreditItems.CheckAccountNum";
                str4 = ">='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("EndCheckAccountNumber"))
              {
                str3 = "dcLBCreditItems.CheckAccountNum";
                str4 = "<='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("lockboxWorkType"))
              {
                str3 = "dcLBCreditItems.LockboxWorkType";
                str4 = "='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("StartLockboxBatchNumber"))
              {
                str3 = "dcLBCreditItems.LockboxBatchNum";
                str4 = ">='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("EndLockboxBatchNumber"))
              {
                str3 = "dcLBCreditItems.LockboxBatchNum";
                str4 = "<='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("StartLockboxSequenceNumber"))
              {
                str3 = "dcLBCreditItems.LockboxSeqNum";
                str4 = ">='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else if (str1.equals("EndLockboxSequenceNumber"))
              {
                str3 = "dcLBCreditItems.LockboxSeqNum";
                str4 = "<='" + DBUtil.escapeSQLStringLiteral(str2) + "'";
              }
              else
              {
                if (!str1.equals("DataClassification")) {
                  continue;
                }
                str3 = "dcLBCreditItems.DataClassification";
                if (str2.equals("I")) {
                  str4 = " ='I' ";
                } else {
                  str4 = " ='P' ";
                }
              }
            }
          }
          else {
            throw new DCException(paramString + " is an invalid report type. ");
          }
          if (str3 != null)
          {
            str5 = new String(localStringBuffer).trim();
            if (str5.length() != 0) {
              localStringBuffer.append(" AND ");
            }
            localStringBuffer.append(str3);
            localStringBuffer.append(str4);
            localStringBuffer.append(" ");
          }
        }
      }
    }
    return localStringBuffer;
  }
  
  private static String a(ReportSortCriteria paramReportSortCriteria, String paramString)
    throws DCException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramReportSortCriteria != null)
    {
      paramReportSortCriteria.setSortedBy("ORDINAL");
      Iterator localIterator = paramReportSortCriteria.iterator();
      ReportSortCriterion localReportSortCriterion = null;
      String str1 = null;
      String str2 = null;
      String str3 = null;
      int i1 = 0;
      while (localIterator.hasNext())
      {
        localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        str1 = localReportSortCriterion.getName();
        str2 = null;
        if (paramString.equals("LockboxSummary"))
        {
          if (str1.equals("AccountNumber"))
          {
            str2 = "dcAccount.AccountID";
          }
          else
          {
            if (!str1.equals("ProcessingDate")) {
              continue;
            }
            str2 = "dcLockboxSummary.DataDate";
          }
        }
        else if (paramString.equals("LockboxDepositReport"))
        {
          if (str1.equals("AccountNumber"))
          {
            str2 = "dcAccount.AccountID";
          }
          else if (str1.equals("ProcessingDate"))
          {
            str2 = "dcLBTransactions.DataDate";
          }
          else
          {
            if (!str1.equals("LockboxNumber")) {
              continue;
            }
            str2 = "dcLBTransactions.LockboxNumber";
          }
        }
        else if (paramString.equals("DepositItemSearch"))
        {
          if (str1.equals("AccountNumber"))
          {
            str2 = "dcAccount.AccountID";
          }
          else if (str1.equals("ProcessingDate"))
          {
            str2 = "dcLBCreditItems.DataDate";
          }
          else if (str1.equals("Payor"))
          {
            str2 = "dcLBCreditItems.Payor";
          }
          else if (str1.equals("CheckNumber"))
          {
            str2 = "dcLBCreditItems.CheckNumber";
          }
          else if (str1.equals("documentType"))
          {
            str2 = "dcLBCreditItems.DocumentType";
          }
          else if (str1.equals("couponAccountNumber"))
          {
            str2 = "dcLBCreditItems.CouponAccountNum";
          }
          else if (str1.equals("couponAmount1"))
          {
            str2 = "dcLBCreditItems.CouponAmount1";
          }
          else if (str1.equals("couponAmount2"))
          {
            str2 = "dcLBCreditItems.CouponAmount2";
          }
          else if (str1.equals("couponDate1"))
          {
            str2 = "dcLBCreditItems.CouponDate1";
          }
          else if (str1.equals("couponDate2"))
          {
            str2 = "dcLBCreditItems.CouponDate2";
          }
          else if (str1.equals("couponReferenceNumber"))
          {
            str2 = "dcLBCreditItems.CouponRefNum";
          }
          else if (str1.equals("checkRoutingNumber"))
          {
            str2 = "dcLBCreditItems.CheckRoutingNum";
          }
          else if (str1.equals("checkAccountNumber"))
          {
            str2 = "dcLBCreditItems.CheckAccountNum";
          }
          else if (str1.equals("lockboxWorkType"))
          {
            str2 = "dcLBCreditItems.LockboxWorkType";
          }
          else if (str1.equals("lockboxBatchNumber"))
          {
            str2 = "dcLBCreditItems.LockboxBatchNum";
          }
          else
          {
            if (!str1.equals("lockboxSequenceNumber")) {
              continue;
            }
            str2 = "dcLBCreditItems.LockboxSeqNum";
          }
        }
        else {
          throw new DCException(paramString + " is not a valid lockbox report type.");
        }
        str3 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
        if (str2 != null)
        {
          if (i1++ > 0) {
            localStringBuffer.append(',');
          }
          localStringBuffer.append(str2);
          localStringBuffer.append(" ");
          localStringBuffer.append(str3);
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  private static String a(String paramString1, String paramString2, String paramString3)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(paramString1);
    if ((paramString2 != null) && (paramString2.length() != 0))
    {
      localStringBuffer.append(" WHERE ");
      localStringBuffer.append(paramString2);
    }
    if ((paramString3 != null) && (paramString3.length() != 0))
    {
      localStringBuffer.append(" ORDER BY ");
      localStringBuffer.append(paramString3);
    }
    return localStringBuffer.toString();
  }
  
  private static void a(ReportResult paramReportResult, ResultSet paramResultSet, int paramInt)
    throws RptException
  {
    int i1 = 0;
    Locale localLocale = paramReportResult.getLocale();
    try
    {
      ReportRow localReportRow = null;
      localObject = null;
      while (paramResultSet.next())
      {
        localReportRow = new ReportRow(localLocale);
        if (i1 % 2 == 1) {
          localReportRow.set("CELLBACKGROUND", "reportDataShaded");
        }
        ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
        localReportRow.setDataItems(localReportDataItems);
        String str1 = paramResultSet.getString(3);
        ReportDataItem localReportDataItem = new ReportDataItem(localLocale);
        localObject = paramResultSet.getTimestamp(7);
        com.ffusion.beans.DateTime localDateTime = localObject == null ? null : new com.ffusion.beans.DateTime((Date)localObject, localLocale);
        localReportDataItem.setData(localDateTime);
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(localLocale);
        String str2 = paramResultSet.getString(9);
        localReportDataItem.setData(str2);
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(localLocale);
        Currency localCurrency = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), str1, localLocale);
        localReportDataItem.setData(localCurrency);
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(localLocale);
        str2 = paramResultSet.getString(11);
        localReportDataItem.setData(str2);
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(localLocale);
        str2 = paramResultSet.getString(13);
        localReportDataItem.setData(str2);
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(localLocale);
        localCurrency = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(14), str1, localLocale);
        localReportDataItem.setData(localCurrency);
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(localLocale);
        localCurrency = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(15), str1, localLocale);
        localReportDataItem.setData(localCurrency);
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(localLocale);
        localObject = paramResultSet.getTimestamp(16);
        localDateTime = localObject == null ? null : new com.ffusion.beans.DateTime((Date)localObject, localLocale);
        localReportDataItem.setData(localDateTime);
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(localLocale);
        localObject = paramResultSet.getTimestamp(17);
        localDateTime = localObject == null ? null : new com.ffusion.beans.DateTime((Date)localObject, localLocale);
        localReportDataItem.setData(localDateTime);
        localReportDataItems.add(localReportDataItem);
        localReportDataItem = new ReportDataItem(localLocale);
        str2 = paramResultSet.getString(18);
        localReportDataItem.setData(str2);
        localReportDataItems.add(localReportDataItem);
        paramReportResult.addRow(localReportRow);
        i1++;
      }
    }
    catch (SQLException localSQLException)
    {
      Object localObject = new RptException(100, "Unable to retrieve lockbox report data from database", localSQLException);
      throw ((Throwable)localObject);
    }
    if ((paramInt > 0) && (i1 >= paramInt))
    {
      HashMap localHashMap = paramReportResult.getProperties();
      if (localHashMap == null)
      {
        localHashMap = new HashMap();
        paramReportResult.setProperties(localHashMap);
      }
      localHashMap.put("TRUNCATED", new Integer(paramInt));
    }
  }
  
  private static void jdField_if(ReportResult paramReportResult, ResultSet paramResultSet, int paramInt, LockboxAccounts paramLockboxAccounts, String paramString)
    throws RptException
  {
    int i1 = 0;
    Locale localLocale = paramReportResult.getLocale();
    try
    {
      localObject1 = null;
      String str1 = null;
      com.ffusion.beans.DateTime localDateTime = null;
      Currency localCurrency = null;
      while (paramResultSet.next())
      {
        ReportRow localReportRow = new ReportRow(localLocale);
        if (i1 % 2 == 1) {
          localReportRow.set("CELLBACKGROUND", "reportDataShaded");
        }
        ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
        localReportRow.setDataItems(localReportDataItems);
        String str2 = paramResultSet.getString(1);
        String str3 = paramResultSet.getString(2);
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < paramLockboxAccounts.size(); i2++)
        {
          localObject2 = (LockboxAccount)paramLockboxAccounts.get(i2);
          if ((str2.equals(((LockboxAccount)localObject2).getAccountID())) && (str3.equals(((LockboxAccount)localObject2).getBankID())))
          {
            localStringBuffer.append(((LockboxAccount)localObject2).getRoutingNumber());
            localStringBuffer.append(" : ");
            try
            {
              localStringBuffer.append(AccountUtil.buildAccountDisplayText(((LockboxAccount)localObject2).getAccountID(), localLocale));
            }
            catch (UtilException localUtilException)
            {
              DebugLog.throwing("Error while constructing account display string for report type, '" + paramString + "'.", localUtilException);
              localStringBuffer.append(((LockboxAccount)localObject2).getAccountID());
            }
            localStringBuffer.append(" - ");
            localStringBuffer.append(((LockboxAccount)localObject2).getNickname());
            localStringBuffer.append(" - ");
            localStringBuffer.append(((LockboxAccount)localObject2).getCurrencyType());
            break;
          }
        }
        localObject1 = new ReportDataItem(localLocale);
        ((ReportDataItem)localObject1).setData(localStringBuffer.toString());
        localReportDataItems.add(localObject1);
        localObject1 = new ReportDataItem(localLocale);
        localDateTime = new com.ffusion.beans.DateTime(paramResultSet.getDate(8), localLocale);
        ((ReportDataItem)localObject1).setData(localDateTime);
        localReportDataItems.add(localObject1);
        localObject1 = new ReportDataItem(localLocale);
        str1 = paramResultSet.getString(4);
        ((ReportDataItem)localObject1).setData(str1);
        localReportDataItems.add(localObject1);
        String str4 = paramResultSet.getString(3);
        localObject1 = new ReportDataItem(localLocale);
        localCurrency = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(9), str4, localLocale);
        ((ReportDataItem)localObject1).setData(localCurrency);
        localReportDataItems.add(localObject1);
        localObject1 = new ReportDataItem(localLocale);
        localCurrency = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), str4, localLocale);
        ((ReportDataItem)localObject1).setData(localCurrency);
        localReportDataItems.add(localObject1);
        localObject1 = new ReportDataItem(localLocale);
        localCurrency = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), str4, localLocale);
        ((ReportDataItem)localObject1).setData(localCurrency);
        localReportDataItems.add(localObject1);
        localObject1 = new ReportDataItem(localLocale);
        localCurrency = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(13), str4, localLocale);
        ((ReportDataItem)localObject1).setData(localCurrency);
        localReportDataItems.add(localObject1);
        localObject1 = new ReportDataItem(localLocale);
        Object localObject2 = new Integer(paramResultSet.getInt(14));
        ((ReportDataItem)localObject1).setData(localObject2);
        localReportDataItems.add(localObject1);
        localObject1 = new ReportDataItem(localLocale);
        localCurrency = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(15), str4, localLocale);
        ((ReportDataItem)localObject1).setData(localCurrency);
        localReportDataItems.add(localObject1);
        paramReportResult.addRow(localReportRow);
        i1++;
      }
    }
    catch (SQLException localSQLException)
    {
      Object localObject1 = new RptException(100, "Unable to retrieve lockbox report data from database", localSQLException);
      throw ((Throwable)localObject1);
    }
    if ((paramInt > 0) && (i1 >= paramInt))
    {
      HashMap localHashMap = paramReportResult.getProperties();
      if (localHashMap == null)
      {
        localHashMap = new HashMap();
        paramReportResult.setProperties(localHashMap);
      }
      localHashMap.put("TRUNCATED", new Integer(paramInt));
    }
  }
  
  private static void jdField_do(ReportResult paramReportResult, ResultSet paramResultSet, int paramInt, LockboxAccounts paramLockboxAccounts, String paramString)
    throws SQLException, RptException
  {
    HashMap localHashMap = null;
    ArrayList localArrayList = null;
    Object localObject1 = null;
    int i1 = 0;
    Object localObject2;
    while (paramResultSet.next())
    {
      localObject2 = new Object[7];
      String str1 = paramResultSet.getString(1);
      String str2 = paramResultSet.getString(2);
      String str3 = paramResultSet.getString(4);
      com.ffusion.beans.DateTime localDateTime = new com.ffusion.beans.DateTime(paramResultSet.getDate(5), paramReportResult.getLocale());
      localObject2 = a((Object[])localObject2, paramResultSet, paramReportResult.getLocale(), paramLockboxAccounts, paramString);
      if ((localHashMap == null) || (localObject1 == null))
      {
        localHashMap = new HashMap();
        localArrayList = new ArrayList();
        localObject1 = localDateTime;
      }
      else if (!localDateTime.isSameDayInYearAs(localObject1))
      {
        i1 = a(paramReportResult, localArrayList, i1, paramInt);
        localHashMap = new HashMap();
        localArrayList = new ArrayList();
        localObject1 = localDateTime;
        if ((paramInt > 0) && (i1 >= paramInt))
        {
          localObject3 = paramReportResult.getProperties();
          if (localObject3 == null)
          {
            localObject3 = new HashMap();
            paramReportResult.setProperties((HashMap)localObject3);
          }
          ((HashMap)localObject3).put("TRUNCATED", new Integer(paramInt));
          break;
        }
      }
      Object localObject3 = new AccountKey(str2, str1, str3);
      Object[] arrayOfObject = (Object[])localHashMap.get(localObject3);
      if (arrayOfObject == null)
      {
        arrayOfObject = new Object[7];
        localHashMap.put(localObject3, arrayOfObject);
        localArrayList.add(arrayOfObject);
      }
      for (int i2 = 0; i2 < localObject2.length; i2++) {
        if (localObject2[i2] != null) {
          arrayOfObject[i2] = localObject2[i2];
        }
      }
    }
    i1 = a(paramReportResult, localArrayList, i1, paramInt);
    if ((paramInt > 0) && (i1 >= paramInt))
    {
      localObject2 = paramReportResult.getProperties();
      if (localObject2 == null)
      {
        localObject2 = new HashMap();
        paramReportResult.setProperties((HashMap)localObject2);
      }
      ((HashMap)localObject2).put("TRUNCATED", new Integer(paramInt));
    }
  }
  
  private static void a(ReportResult paramReportResult, ResultSet paramResultSet, int paramInt, LockboxAccounts paramLockboxAccounts, String paramString)
    throws SQLException, RptException
  {
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    int i1 = 0;
    Object localObject5;
    while (paramResultSet.next())
    {
      localObject4 = new Object[7];
      localObject5 = paramResultSet.getString(1);
      String str1 = paramResultSet.getString(2);
      String str2 = paramResultSet.getString(4);
      com.ffusion.beans.DateTime localDateTime = new com.ffusion.beans.DateTime(paramResultSet.getDate(5), paramReportResult.getLocale());
      AccountKey localAccountKey = new AccountKey(str1, (String)localObject5, str2);
      localObject4 = a((Object[])localObject4, paramResultSet, paramReportResult.getLocale(), paramLockboxAccounts, paramString);
      if ((localObject2 == null) || (localObject1 == null))
      {
        localObject2 = localAccountKey;
        localObject1 = localDateTime;
        localObject3 = localObject4;
      }
      else if ((!localDateTime.isSameDayInYearAs(localObject1)) || (!localObject2.equals(localAccountKey)))
      {
        ArrayList localArrayList = new ArrayList(1);
        localArrayList.add(localObject3);
        i1 = a(paramReportResult, localArrayList, i1, paramInt);
        if ((paramInt > 0) && (i1 >= paramInt))
        {
          HashMap localHashMap = paramReportResult.getProperties();
          if (localHashMap == null)
          {
            localHashMap = new HashMap();
            paramReportResult.setProperties(localHashMap);
          }
          localHashMap.put("TRUNCATED", new Integer(paramInt));
          localObject3 = null;
          break;
        }
        localObject3 = localObject4;
        localObject2 = localAccountKey;
        localObject1 = localDateTime;
      }
      else
      {
        for (int i2 = 0; i2 < localObject4.length; i2++) {
          if (localObject4[i2] != null) {
            localObject3[i2] = localObject4[i2];
          }
        }
      }
    }
    Object localObject4 = new ArrayList(1);
    if (localObject3 != null) {
      ((ArrayList)localObject4).add(localObject3);
    }
    i1 = a(paramReportResult, (ArrayList)localObject4, i1, paramInt);
    if ((paramInt > 0) && (i1 >= paramInt))
    {
      localObject5 = paramReportResult.getProperties();
      if (localObject5 == null)
      {
        localObject5 = new HashMap();
        paramReportResult.setProperties((HashMap)localObject5);
      }
      ((HashMap)localObject5).put("TRUNCATED", new Integer(paramInt));
    }
  }
  
  private static Object[] a(Object[] paramArrayOfObject, ResultSet paramResultSet, Locale paramLocale, LockboxAccounts paramLockboxAccounts, String paramString)
    throws SQLException
  {
    paramArrayOfObject[0] = new com.ffusion.beans.DateTime(paramResultSet.getDate(5), paramLocale);
    String str1 = paramResultSet.getString(2);
    for (int i1 = 0; i1 < paramLockboxAccounts.size(); i1++)
    {
      LockboxAccount localLockboxAccount = (LockboxAccount)paramLockboxAccounts.get(i1);
      if (localLockboxAccount.getAccountID().equals(str1)) {
        try
        {
          paramArrayOfObject[1] = (localLockboxAccount.getRoutingNumber() + " : " + AccountUtil.buildAccountDisplayText(localLockboxAccount.getAccountID(), paramLocale) + " - " + localLockboxAccount.getNickname() + " - " + localLockboxAccount.getCurrencyType());
        }
        catch (UtilException localUtilException)
        {
          DebugLog.throwing("Error while constructing account display string for report type, '" + paramString + "'.", localUtilException);
          paramArrayOfObject[1] = (localLockboxAccount.getRoutingNumber() + " : " + localLockboxAccount.getAccountID() + " - " + localLockboxAccount.getNickname() + " - " + localLockboxAccount.getCurrencyType());
        }
      }
    }
    String str2 = paramResultSet.getString(3);
    paramArrayOfObject[2] = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(6), str2, paramLocale);
    int i2 = paramResultSet.getInt(8);
    if (!paramResultSet.wasNull()) {
      paramArrayOfObject[3] = new Integer(i2);
    }
    paramArrayOfObject[4] = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(10), str2, paramLocale);
    paramArrayOfObject[5] = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(11), str2, paramLocale);
    paramArrayOfObject[6] = DCUtil.getCurrencyColumn(paramResultSet.getBigDecimal(12), str2, paramLocale);
    return paramArrayOfObject;
  }
  
  private static int a(ReportResult paramReportResult, ArrayList paramArrayList, int paramInt1, int paramInt2)
    throws RptException
  {
    for (int i1 = 0; i1 < paramArrayList.size(); i1++)
    {
      Object[] arrayOfObject = (Object[])paramArrayList.get(i1);
      Locale localLocale = paramReportResult.getLocale();
      ReportDataItem localReportDataItem = null;
      ReportRow localReportRow = new ReportRow(localLocale);
      if (paramInt1 % 2 == 1) {
        localReportRow.set("CELLBACKGROUND", "reportDataShaded");
      }
      ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
      localReportRow.setDataItems(localReportDataItems);
      for (int i2 = 0; i2 < arrayOfObject.length; i2++)
      {
        localReportDataItem = new ReportDataItem(localLocale);
        localReportDataItem.setData(arrayOfObject[i2]);
        localReportDataItems.add(localReportDataItem);
      }
      paramReportResult.addRow(localReportRow);
      paramInt1++;
      if ((paramInt2 > 0) && (paramInt1 >= paramInt2)) {
        break;
      }
    }
    return paramInt1;
  }
  
  private static String a(LockboxAccounts paramLockboxAccounts)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = paramLockboxAccounts.iterator();
    if (!localIterator.hasNext()) {
      return localStringBuffer.toString();
    }
    localStringBuffer.append("( ");
    while (localIterator.hasNext())
    {
      localStringBuffer.append("( ");
      LockboxAccount localLockboxAccount = (LockboxAccount)localIterator.next();
      localStringBuffer.append("dcAccount.AccountID");
      localStringBuffer.append(" = '");
      localStringBuffer.append(localLockboxAccount.getAccountID());
      localStringBuffer.append("' AND ");
      localStringBuffer.append("dcAccount.BankID");
      localStringBuffer.append(" = '");
      localStringBuffer.append(localLockboxAccount.getBankID());
      localStringBuffer.append("') ");
      if (localIterator.hasNext()) {
        localStringBuffer.append(" OR ");
      }
    }
    localStringBuffer.append(" ) ");
    return localStringBuffer.toString();
  }
  
  private static PreparedStatement a(Connection paramConnection, String paramString1, String paramString2, String paramString3, Vector paramVector, int paramInt)
    throws Exception
  {
    String str = a(paramString1, paramString2, paramString3);
    PreparedStatement localPreparedStatement = DCUtil.prepareStatement(paramConnection, str, 1004, 1007);
    int i1 = 0;
    while (!paramVector.isEmpty())
    {
      Object localObject;
      if ((paramVector.get(0) instanceof Long))
      {
        localObject = (Long)paramVector.remove(0);
        i1++;
        DCUtil.fillTimestampColumn(localPreparedStatement, i1, ((Long)localObject).longValue());
      }
      else if ((paramVector.get(0) instanceof com.ffusion.beans.DateTime))
      {
        localObject = (com.ffusion.beans.DateTime)paramVector.remove(0);
        i1++;
        DCUtil.fillDate(localPreparedStatement, i1, (com.ffusion.beans.DateTime)localObject);
      }
      else
      {
        throw new DCException("Invalid object found in dates vector when building query.");
      }
    }
    if (paramInt >= 0) {
      localPreparedStatement.setMaxRows(paramInt);
    }
    return localPreparedStatement;
  }
  
  public static IReportResult getReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DCException
  {
    a(paramReportCriteria, paramHashMap);
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str1 = localProperties2.getProperty("REPORTTYPE");
    ReportResult localReportResult = null;
    Connection localConnection = null;
    try
    {
      localConnection = DCAdapter.getDBConnection();
    }
    catch (Exception localException1)
    {
      localObject1 = "Unable to get database connection for running report.";
      localObject2 = new DCException(301, (String)localObject1, localException1);
      throw ((Throwable)localObject2);
    }
    PreparedStatement localPreparedStatement = null;
    Object localObject1 = null;
    Object localObject2 = new Vector();
    try
    {
      Properties localProperties3 = paramReportCriteria.getSearchCriteria();
      ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
      if (str1.equals("LockboxSummary")) {
        localReportSortCriteria = a(localReportSortCriteria);
      }
      String str2 = a(localReportSortCriteria, str1);
      String str3 = null;
      int i1 = 0;
      try
      {
        String str4 = (String)localProperties2.get("MAXDISPLAYSIZE");
        if (str4 != null) {
          i1 = Integer.parseInt(str4);
        }
      }
      catch (NumberFormatException localNumberFormatException) {}
      Locale localLocale = paramReportCriteria.getLocale();
      localReportResult = new ReportResult(localLocale);
      a(paramReportCriteria, str1);
      LockboxAccounts localLockboxAccounts = (LockboxAccounts)paramHashMap.get("LOCKBOX_ACCOUNTS");
      a(paramReportCriteria, localLockboxAccounts, localLocale);
      localReportResult.init(paramReportCriteria);
      String str5;
      if (str1.equals("DepositItemSearch"))
      {
        str3 = "SELECT dcAccount.BankID, dcAccount.AccountID, dcAccount.CurrencyCode, dcLockbox.LockboxNumber, dcLBCreditItems.DCCreditItemIndex, dcLBCreditItems.ItemID, dcLBCreditItems.DataDate, dcLBCreditItems.DocumentType, dcLBCreditItems.Payor, dcLBCreditItems.Amount, dcLBCreditItems.CheckNumber, dcLBCreditItems.CheckDate, dcLBCreditItems.CouponAccountNum, dcLBCreditItems.CouponAmount1, dcLBCreditItems.CouponAmount2, dcLBCreditItems.CouponDate1, dcLBCreditItems.CouponDate2, dcLBCreditItems.CouponRefNum, dcLBCreditItems.CheckRoutingNum, dcLBCreditItems.CheckAccountNum, dcLBCreditItems.LockboxWorkType, dcLBCreditItems.LockboxBatchNum, dcLBCreditItems.LockboxSeqNum, dcLBCreditItems.Memo, dcLBCreditItems.ImmedAvailAmount, dcLBCreditItems.OneDayAvailAmount, dcLBCreditItems.MoreOneDayAvailAm, dcLBCreditItems.ValueDateTime, dcLBCreditItems.BankRefNum, dcLBCreditItems.CustRefNum, dcLBCreditItems.ExtendABeanXMLID FROM DC_Account dcAccount, DC_Lockbox dcLockbox, DC_LBCreditItems dcLBCreditItems ";
        localObject3 = a(paramReportCriteria, str1, (Vector)localObject2, paramHashMap);
        localPreparedStatement = a(localConnection, str3, (String)localObject3, str2, (Vector)localObject2, i1);
        localObject1 = DBUtil.executeQuery(localPreparedStatement, str3);
        str5 = localProperties2.getProperty("TITLE");
        if ((str5 == null) || (str5.length() <= 0)) {
          str5 = "Lockbox Credit Item Search";
        }
        a(localReportResult, str5);
        a(localReportResult, localLocale);
        a(localReportResult, (ResultSet)localObject1, i1);
      }
      else if (str1.equals("LockboxDepositReport"))
      {
        str3 = "SELECT dcAccount.AccountID, dcAccount.BankID, dcAccount.CurrencyCode, dcLBTransactions.LockboxNumber, dcLBTransactions.DCTransactionIndex, dcLBTransactions.TransID, dcLBTransactions.TransTypeID, dcLBTransactions.DataDate, dcLBTransactions.Amount, dcLBTransactions.Description, dcLBTransactions.ImmedAvailAmount, dcLBTransactions.OneDayAvailAmount, dcLBTransactions.MoreOneDayAvailAm, dcLBTransactions.NumRejectedChecks, dcLBTransactions.RejectedAmount, dcLBTransactions.ValueDateTime, dcLBTransactions.BankRefNum, dcLBTransactions.CustRefNum, dcLBTransactions.ExtendABeanXMLID FROM DC_Account dcAccount, DC_LBTransactions dcLBTransactions ";
        localObject3 = a(paramReportCriteria, str1, (Vector)localObject2, paramHashMap);
        localPreparedStatement = a(localConnection, str3, (String)localObject3, str2, (Vector)localObject2, i1);
        localObject1 = DBUtil.executeQuery(localPreparedStatement, str3);
        str5 = localProperties2.getProperty("TITLE");
        if ((str5 == null) || (str5.length() <= 0)) {
          str5 = "Lockbox Deposit Report";
        }
        a(localReportResult, str5);
        jdField_if(localReportResult, localLocale);
        jdField_if(localReportResult, (ResultSet)localObject1, i1, localLockboxAccounts, str1);
      }
      else if (str1.equals("LockboxSummary"))
      {
        str3 = "SELECT dcAccount.BankID, dcAccount.AccountID, dcAccount.CurrencyCode, dcAccount.RoutingNumber, dcLockboxSummary.DataDate, dcLockboxSummary.TotalCredits, dcLockboxSummary.TotalDebits, dcLockboxSummary.TotalNumCredits, dcLockboxSummary.TotalNumDebits, dcLockboxSummary.ImmediateFloat, dcLockboxSummary.OneDayFloat, dcLockboxSummary.TwoDayFloat FROM DC_Account dcAccount, DC_LockboxSummary dcLockboxSummary ";
        localObject3 = a(paramReportCriteria, str1, (Vector)localObject2, paramHashMap);
        localPreparedStatement = a(localConnection, str3, (String)localObject3, str2, (Vector)localObject2, 0);
        localObject1 = DBUtil.executeQuery(localPreparedStatement, str3);
        str5 = localProperties2.getProperty("TITLE");
        if ((str5 == null) || (str5.length() <= 0)) {
          str5 = "Lockbox Summary Report";
        }
        a(localReportResult, str5);
        jdField_do(localReportResult, paramReportCriteria.getLocale());
        ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localReportSortCriteria.get(0);
        try
        {
          if (localReportSortCriterion.getName().equals("AccountNumber")) {
            a(localReportResult, (ResultSet)localObject1, i1, localLockboxAccounts, str1);
          } else {
            jdField_do(localReportResult, (ResultSet)localObject1, i1, localLockboxAccounts, str1);
          }
        }
        catch (SQLException localSQLException2)
        {
          DCException localDCException1 = new DCException(302, "Unable to retrieve lockbox report data from database", localSQLException2);
          throw localDCException1;
        }
      }
      else
      {
        localObject3 = "The report type requested '" + str1 + "', is not a valid Lockbox " + "report type.  Currently the valid reporting types are " + "DepositItemSearch" + ", " + "LockboxDepositReport" + " and " + "LockboxSummary" + ".";
        throw new DCException(319, (String)localObject3);
      }
      Object localObject3 = localReportResult;
      return localObject3;
    }
    catch (RptException localRptException1)
    {
      localReportResult.setError(localRptException1);
      throw new DCException(313, "Exception while running report.", localRptException1);
    }
    catch (SQLException localSQLException1)
    {
      localReportResult.setError(localSQLException1);
      throw new DCException(302, "SQLException while running report.", localSQLException1);
    }
    catch (Exception localException2)
    {
      localReportResult.setError(localException2);
      throw new DCException(313, "Exception while running report.", localException2);
    }
    finally
    {
      DBUtil.closeResultSet((ResultSet)localObject1);
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      if (localConnection != null) {
        try
        {
          DCAdapter.releaseDBConnection(localConnection);
        }
        catch (DCException localDCException2)
        {
          DebugLog.throwing("DCLockboxReport.getReportData", localDCException2);
        }
      }
      try
      {
        localReportResult.fini();
      }
      catch (RptException localRptException2)
      {
        String str6 = "Exception while cleaning up ReportResult bean";
        DCException localDCException3 = new DCException(313, str6, localRptException2);
        DebugLog.throwing("DCLockboxReport.getReportData", localDCException3);
        throw localDCException3;
      }
    }
  }
  
  private static void a(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DCException
  {
    String str = "DCLockboxReport.validateCriteria";
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties == null)
    {
      localObject1 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
      localObject2 = new DCException(314, (String)localObject1);
      DebugLog.throwing(str, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    Object localObject1 = paramReportCriteria.getReportOptions();
    if (localObject1 == null)
    {
      localObject2 = "The report criteria used in a call to getAccountReportData did not contain a valid report options object.  This object is required for report retrieval.";
      localObject3 = new DCException(315, (String)localObject2);
      DebugLog.throwing(str, (Throwable)localObject3);
      throw ((Throwable)localObject3);
    }
    Object localObject2 = paramReportCriteria.getSortCriteria();
    Object localObject4;
    if (localObject2 == null)
    {
      localObject3 = "A sort criteria object was not found within the given report criteria.";
      localObject4 = new DCException(316, (String)localObject3);
      DebugLog.throwing(str, (Throwable)localObject4);
      throw ((Throwable)localObject4);
    }
    Object localObject3 = ((Properties)localObject1).getProperty("REPORTTYPE");
    if (localObject3 == null)
    {
      localObject4 = "The report options contained within the Report Criteria used in a call to getAccountReportData does not contain a report type.";
      DCException localDCException = new DCException(318, (String)localObject4);
      DebugLog.throwing(str, localDCException);
      throw localDCException;
    }
    if (((String)localObject3).equals("LockboxSummary"))
    {
      jdField_if(localProperties, paramHashMap);
      a(localProperties, paramHashMap);
      jdField_for(localProperties, paramHashMap);
    }
    else if (((String)localObject3).equals("LockboxDepositReport"))
    {
      jdField_if(localProperties, paramHashMap);
      a(localProperties, paramHashMap);
      jdField_for(localProperties, paramHashMap);
      jdField_do(localProperties, paramHashMap);
      jdField_new(localProperties, paramHashMap);
      jdField_int(localProperties, paramHashMap);
    }
    else if (((String)localObject3).equals("DepositItemSearch"))
    {
      jdField_if(localProperties, paramHashMap);
      a(localProperties, paramHashMap);
      jdField_for(localProperties, paramHashMap);
      jdField_do(localProperties, paramHashMap);
      jdField_new(localProperties, paramHashMap);
      jdField_int(localProperties, paramHashMap);
      a(localProperties, paramReportCriteria.getLocale(), paramHashMap);
    }
  }
  
  private static void a(HashMap paramHashMap, String paramString)
    throws DCException
  {
    if (paramHashMap == null)
    {
      String str = "extra is null when it is required ";
      if (paramString != null) {
        str = str + "(" + paramString + ")";
      }
      DCException localDCException = new DCException(317, str);
      DebugLog.throwing("DCLockboxReport.checkExtraNotNull", localDCException);
      throw localDCException;
    }
  }
  
  private static void jdField_if(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str1 = "DCLockboxReport.validateLockboxAccounts";
    String str2 = paramProperties.getProperty("LockboxNumber");
    Object localObject1;
    Object localObject2;
    if ((str2 == null) || (str2.equals("")))
    {
      localObject1 = "No lockbox numbers specified in the search criteria";
      localObject2 = new DCException(342, (String)localObject1);
      DebugLog.throwing(str1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    Object localObject3;
    if (jdField_if(str2))
    {
      a(paramHashMap, "attempt to retrieve lockbox accounts");
      localObject1 = (LockboxAccounts)paramHashMap.get("LOCKBOX_ACCOUNTS");
      if ((localObject1 == null) || (((LockboxAccounts)localObject1).isEmpty()))
      {
        localObject2 = "All lockbox accounts are specified in the search criteria but no lockbox accounts are specified in the extra hashmap.";
        localObject3 = new DCException(343, (String)localObject2);
        DebugLog.throwing(str1, (Throwable)localObject3);
        throw ((Throwable)localObject3);
      }
    }
    else
    {
      localObject1 = new StringTokenizer(str2, ",");
      while (((StringTokenizer)localObject1).hasMoreTokens())
      {
        localObject2 = ((StringTokenizer)localObject1).nextToken();
        localObject3 = new StringTokenizer((String)localObject2, ":");
        if (((StringTokenizer)localObject3).countTokens() < 3)
        {
          String str3 = "The lockbox account (value '" + (String)localObject2 + "') was not passed in properly as search criteria.";
          DCException localDCException = new DCException(329, str3);
          DebugLog.throwing(str1, localDCException);
          throw localDCException;
        }
      }
    }
  }
  
  private static void a(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str1 = "DCLockboxReport.validateDateRange";
    a(paramHashMap, "attempt to retrieve start dates, end dates, lockbox accounts for report");
    HashMap localHashMap = (HashMap)paramHashMap.get("StartDates");
    if (localHashMap == null)
    {
      localObject1 = "com.ffusion.beans.reporting.ReportConsts.START_DATES_FOR_REPORT not in extra hashmap.";
      localObject2 = new DCException(322, (String)localObject1);
      DebugLog.throwing(str1, (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    Object localObject1 = (HashMap)paramHashMap.get("EndDates");
    if (localObject1 == null)
    {
      localObject2 = "com.ffusion.beans.reporting.ReportConsts.END_DATES_FOR_REPORT not in extra hashmap.";
      localObject3 = new DCException(323, (String)localObject2);
      DebugLog.throwing(str1, (Throwable)localObject3);
      throw ((Throwable)localObject3);
    }
    Object localObject2 = paramProperties.getProperty("LockboxNumber");
    Object localObject3 = new HashSet();
    Object localObject5;
    Object localObject6;
    Object localObject7;
    Object localObject8;
    String str2;
    if (jdField_if((String)localObject2))
    {
      localObject4 = (LockboxAccounts)paramHashMap.get("LOCKBOX_ACCOUNTS");
      for (int i1 = 0; i1 < ((LockboxAccounts)localObject4).size(); i1++)
      {
        localObject5 = (LockboxAccount)((LockboxAccounts)localObject4).get(i1);
        localObject6 = ((LockboxAccount)localObject5).getRoutingNumber();
        if (localObject6 == null)
        {
          localObject7 = "Lockbox account (id '" + ((LockboxAccount)localObject5).getAccountID() + "') missing routing number.";
          localObject8 = new DCException(328, (String)localObject7);
          DebugLog.throwing(str1, (Throwable)localObject8);
          throw ((Throwable)localObject8);
        }
        ((HashSet)localObject3).add(localObject6);
      }
    }
    else
    {
      localObject4 = new StringTokenizer((String)localObject2, ",");
      while (((StringTokenizer)localObject4).hasMoreTokens())
      {
        str2 = ((StringTokenizer)localObject4).nextToken();
        localObject5 = new StringTokenizer(str2, ":");
        ((StringTokenizer)localObject5).nextToken();
        ((StringTokenizer)localObject5).nextToken();
        ((StringTokenizer)localObject5).nextToken();
        localObject6 = ((StringTokenizer)localObject5).nextToken();
        ((HashSet)localObject3).add(localObject6);
      }
    }
    Object localObject4 = ((HashSet)localObject3).iterator();
    while (((Iterator)localObject4).hasNext())
    {
      str2 = (String)((Iterator)localObject4).next();
      localObject5 = (com.ffusion.util.beans.DateTime)localHashMap.get(str2);
      localObject6 = (com.ffusion.util.beans.DateTime)localHashMap.get(str2);
      if (localObject5 == null)
      {
        localObject7 = "No start date found for routing number " + str2 + ".";
        localObject8 = new DCException(320, (String)localObject7);
        DebugLog.throwing(str1, (Throwable)localObject8);
        throw ((Throwable)localObject8);
      }
      if (localObject6 == null)
      {
        localObject7 = "No end date found for routing number " + str2 + ".";
        localObject8 = new DCException(321, (String)localObject7);
        DebugLog.throwing(str1, (Throwable)localObject8);
        throw ((Throwable)localObject8);
      }
      if (((com.ffusion.util.beans.DateTime)localObject6).before(localObject5))
      {
        localObject7 = "The end date " + localObject6 + " is invalid - it is before the start date (" + localObject5 + ")";
        localObject8 = new DCException(359, (String)localObject7);
        DebugLog.throwing(str1, (Throwable)localObject8);
        throw ((Throwable)localObject8);
      }
      localObject7 = new com.ffusion.util.beans.DateTime((Calendar)localObject6, ((com.ffusion.util.beans.DateTime)localObject6).getLocale());
      ((com.ffusion.util.beans.DateTime)localObject7).add(5, -31);
      if (((com.ffusion.util.beans.DateTime)localObject5).before(localObject7))
      {
        localObject8 = "The date range for routing number " + str2 + " is too long - " + "the maximum range covered is " + 31 + " days.";
        DCException localDCException = new DCException(357, (String)localObject8);
        DebugLog.throwing(str1, localDCException);
        throw localDCException;
      }
    }
  }
  
  private static void jdField_for(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str1 = "DCLockboxReport.validateDataClassification";
    String str2 = paramProperties.getProperty("DataClassification");
    String str3;
    DCException localDCException;
    if (str2 == null)
    {
      str3 = "The data classification is missing.";
      localDCException = new DCException(330, str3);
      DebugLog.throwing(str1, localDCException);
      throw localDCException;
    }
    if ((!str2.equals("P")) && (!str2.equals("I")))
    {
      str3 = "The data classification '" + str2 + "' is invalid.";
      localDCException = new DCException(331, str3);
      DebugLog.throwing(str1, localDCException);
      throw localDCException;
    }
  }
  
  private static void jdField_do(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str1 = "DCLockboxReport.validatePayor";
    String str2 = paramProperties.getProperty("Payor");
    if ((str2 != null) && (str2.length() > 80))
    {
      String str3 = "Payor is too long (" + str2.length() + " characters)" + " - maximum is " + 80 + " characters.";
      DCException localDCException = new DCException(344, str3);
      DebugLog.throwing(str1, localDCException);
      throw localDCException;
    }
  }
  
  private static void jdField_new(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str1 = "DCLockboxReport.validateCheckNumberRange";
    String str2 = paramProperties.getProperty("StartCheckNumber");
    Object localObject;
    if ((str2 != null) && (str2.length() > 40))
    {
      str3 = "Start check number is too long (" + str2.length() + " characters)" + " - maximum is " + 40 + " characters.";
      localObject = new DCException(345, str3);
      DebugLog.throwing(str1, (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    String str3 = paramProperties.getProperty("EndCheckNumber");
    if ((str3 != null) && (str3.length() > 40))
    {
      localObject = "End check number is too long (" + str3.length() + " characters)" + " - maximum is " + 40 + " characters.";
      DCException localDCException = new DCException(345, (String)localObject);
      DebugLog.throwing(str1, localDCException);
      throw localDCException;
    }
  }
  
  private static void jdField_int(Properties paramProperties, HashMap paramHashMap)
    throws DCException
  {
    String str1 = paramProperties.getProperty("MinimumAmount");
    String str2 = paramProperties.getProperty("MaximumAmount");
    a(str1, "Minimum amount", 332);
    a(str2, "Maximum amount", 333);
  }
  
  private static void a(Properties paramProperties, Locale paramLocale, HashMap paramHashMap)
    throws DCException
  {
    String str1 = "DCLockboxReport.validateOtherCriteria";
    String str2 = paramProperties.getProperty("documentType");
    a(str2, "Document type", 40, 346);
    String str3 = paramProperties.getProperty("StartCouponAccountNumber");
    String str4 = paramProperties.getProperty("EndCouponAccountNumber");
    a(str3, "Start coupon account number", 40, 347);
    a(str4, "End coupon account number", 40, 347);
    String str5 = paramProperties.getProperty("MinimumCouponAmount1");
    String str6 = paramProperties.getProperty("MaximumCouponAmount1");
    String str7 = paramProperties.getProperty("MinimumCouponAmount2");
    String str8 = paramProperties.getProperty("MaximumCouponAmount2");
    a(str5, "Minimum coupon amount 1", 348);
    a(str6, "Maximum coupon amount 1", 348);
    a(str7, "Minimum coupon amount 2", 348);
    a(str8, "Maximum coupon amount 2", 348);
    String str9 = paramProperties.getProperty("StartCouponDate1");
    String str10 = paramProperties.getProperty("EndCouponDate1");
    String str11 = paramProperties.getProperty("StartCouponDate2");
    String str12 = paramProperties.getProperty("EndCouponDate2");
    a(str9, "Start coupon date 1", 355, paramLocale);
    a(str10, "End coupon date 1", 355, paramLocale);
    a(str11, "Start coupon date 2", 356, paramLocale);
    a(str12, "End coupon date 2", 356, paramLocale);
    String str13 = paramProperties.getProperty("StartCouponReferenceNumber");
    String str14 = paramProperties.getProperty("StartCouponReferenceNumber");
    a(str13, "Start coupon reference number", 40, 349);
    a(str14, "End coupon reference number", 40, 349);
    String str15 = paramProperties.getProperty("StartCheckRoutingNumber");
    String str16 = paramProperties.getProperty("EndCheckRoutingNumber");
    a(str15, "Start check routing number", 40, 350);
    a(str16, "End check routing number", 40, 350);
    String str17 = paramProperties.getProperty("StartCheckAccountNumber");
    String str18 = paramProperties.getProperty("EndCheckAccountNumber");
    a(str17, "Start check account number", 40, 351);
    a(str18, "End check account number", 40, 351);
    String str19 = paramProperties.getProperty("lockboxWorkType");
    a(str19, "Lockbox work type", 40, 352);
    String str20 = paramProperties.getProperty("StartLockboxBatchNumber");
    String str21 = paramProperties.getProperty("EndLockboxBatchNumber");
    a(str21, "Start lockbox batch number", 40, 353);
    a(str21, "End lockbox batch number", 40, 353);
    String str22 = paramProperties.getProperty("StartLockboxSequenceNumber");
    String str23 = paramProperties.getProperty("EndLockboxSequenceNumber");
    a(str22, "Start lockbox sequence number", 40, 354);
    a(str23, "End lockbox sequence number", 40, 354);
  }
  
  private static void a(String paramString1, String paramString2, int paramInt)
    throws DCException
  {
    String str1 = "DCLockboxReport.validateAmountHelper";
    String str2 = "0123456789 .,";
    if ((paramString1 != null) && (paramString1.length() != 0))
    {
      Object localObject;
      for (int i1 = 0; i1 < paramString1.length(); i1++)
      {
        char c1 = paramString1.charAt(i1);
        if (str2.indexOf(c1) == -1)
        {
          localObject = paramString2 + " contains an invalid character '" + c1 + "'";
          DCException localDCException = new DCException(paramInt, (String)localObject);
          DebugLog.throwing(str1, localDCException);
          throw localDCException;
        }
      }
      try
      {
        Double.parseDouble(paramString1);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        String str3 = paramString2 + " '" + paramString1 + "' is not a valid amount.";
        localObject = new DCException(paramInt, str3);
        DebugLog.throwing(str1, (Throwable)localObject);
        throw ((Throwable)localObject);
      }
    }
  }
  
  private static void a(String paramString1, String paramString2, int paramInt1, int paramInt2)
    throws DCException
  {
    String str1 = "DCLockboxReport.validateCriterionLengthHelper";
    if ((paramString1 != null) && (paramString1.length() > paramInt1))
    {
      String str2 = paramString2 + " is too long (" + paramString1.length() + " characters)" + " - maximum is " + paramInt1 + " characters.";
      DCException localDCException = new DCException(paramInt2, str2);
      DebugLog.throwing(str1, localDCException);
      throw localDCException;
    }
  }
  
  private static void a(String paramString1, String paramString2, int paramInt, Locale paramLocale)
    throws DCException
  {
    String str1 = "DCLockboxReport.validateDateHelper";
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      return;
    }
    try
    {
      jdField_do(paramString1, paramLocale);
    }
    catch (ParseException localParseException)
    {
      String str2 = paramString2 + " is not a valid date.";
      DCException localDCException = new DCException(paramInt, str2);
      DebugLog.throwing(str1, localDCException);
      throw localDCException;
    }
  }
  
  private static com.ffusion.beans.DateTime jdField_do(String paramString, Locale paramLocale)
    throws ParseException
  {
    Date localDate = DateFormatUtil.getFormatter("MM/dd/yyyy").parse(paramString);
    return new com.ffusion.beans.DateTime(localDate, paramLocale);
  }
  
  private static com.ffusion.beans.DateTime jdField_if(String paramString, Locale paramLocale)
    throws ParseException
  {
    Date localDate = DateFormatUtil.getFormatter("HH:mm:ss").parse(paramString);
    return new com.ffusion.beans.DateTime(localDate, paramLocale);
  }
  
  private static void a(ReportResult paramReportResult, String paramString)
    throws RptException
  {
    ReportHeading localReportHeading = new ReportHeading(paramReportResult.getLocale());
    localReportHeading.setLabel(paramString);
    localReportHeading.setJustification("LEFT");
    paramReportResult.setHeading(localReportHeading);
  }
  
  private static void a(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(10);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumns localReportColumns = new ReportColumns(paramLocale);
    ReportColumn localReportColumn = new ReportColumn(paramReportResult.getLocale());
    ArrayList localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2100, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2101, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(8);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2102, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(2);
    localArrayList.add(ReportConsts.getColumnName(2103, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(2122, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(11);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2104, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2105, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2106, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(11);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2120, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2121, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(2);
    localArrayList.add(ReportConsts.getColumnName(2109, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(2122, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void jdField_if(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(9);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumns localReportColumns = new ReportColumns(paramLocale);
    ReportColumn localReportColumn = new ReportColumn(paramReportResult.getLocale());
    ArrayList localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2110, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(28);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2100, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(8);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(2);
    localArrayList.add(ReportConsts.getColumnName(2111, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(2122, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2112, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(9);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(2);
    localArrayList.add(ReportConsts.getColumnName(2113, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(2123, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(9);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(2);
    localArrayList.add(ReportConsts.getColumnName(2114, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(2123, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(9);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(2);
    localArrayList.add(ReportConsts.getColumnName(2115, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(2123, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(9);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2116, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.Integer");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(9);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2117, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(9);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void jdField_do(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(paramLocale);
    localReportDataDimensions.setNumRows(-1);
    localReportDataDimensions.setNumColumns(7);
    paramReportResult.setDataDimensions(localReportDataDimensions);
    ReportColumns localReportColumns = new ReportColumns(paramLocale);
    ReportColumn localReportColumn = new ReportColumn(paramReportResult.getLocale());
    ArrayList localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2100, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.util.beans.DateTime");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(10);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2110, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setJustification("LEFT");
    localReportColumn.setWidthAsPercent(30);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(1);
    localArrayList.add(ReportConsts.getColumnName(2118, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(2);
    localArrayList.add(ReportConsts.getColumnName(2122, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(2119, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.Integer");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(2);
    localArrayList.add(ReportConsts.getColumnName(2113, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(2123, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(2);
    localArrayList.add(ReportConsts.getColumnName(2114, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(2123, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
    localReportColumn = new ReportColumn(paramReportResult.getLocale());
    localArrayList = new ArrayList(2);
    localArrayList.add(ReportConsts.getColumnName(2115, paramLocale));
    localArrayList.add(ReportConsts.getColumnName(2123, paramLocale));
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("com.ffusion.beans.Currency");
    localReportColumn.setJustification("RIGHT");
    localReportColumn.setWidthAsPercent(12);
    paramReportResult.addColumn(localReportColumn);
  }
  
  private static void a(ReportCriteria paramReportCriteria, String paramString)
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    Enumeration localEnumeration = localProperties.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      String str2 = localProperties.getProperty(str1);
      if ((str2 == null) || (str2.length() <= 0)) {
        paramReportCriteria.setHiddenSearchCriterion(str1, true);
      }
    }
    paramReportCriteria.setHiddenSearchCriterion("other1", true);
    paramReportCriteria.setHiddenSearchCriterion("other2", true);
    paramReportCriteria.setHiddenSearchCriterion("other1_rangeStart", true);
    paramReportCriteria.setHiddenSearchCriterion("other1_rangeEnd", true);
    paramReportCriteria.setHiddenSearchCriterion("other2_rangeStart", true);
    paramReportCriteria.setHiddenSearchCriterion("other2_rangeEnd", true);
  }
  
  private static void a(ReportCriteria paramReportCriteria, LockboxAccounts paramLockboxAccounts, Locale paramLocale)
  {
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    String str1 = localProperties2.getProperty("REPORTTYPE");
    String str2 = localProperties1.getProperty("DataClassification");
    if (str2.equalsIgnoreCase("I")) {
      paramReportCriteria.setDisplayValue("DataClassification", ReportConsts.getText(2102, paramLocale));
    } else {
      paramReportCriteria.setDisplayValue("DataClassification", ReportConsts.getText(2101, paramLocale));
    }
    str2 = localProperties1.getProperty("LockboxNumber");
    if (str2 == null) {
      str2 = "ALL";
    }
    if (jdField_if(str2))
    {
      paramReportCriteria.setDisplayValue("LockboxNumber", ReportConsts.getText(2100, paramLocale));
    }
    else
    {
      StringTokenizer localStringTokenizer1 = new StringTokenizer(str2, ",");
      int i1 = localStringTokenizer1.countTokens();
      if (i1 >= 1)
      {
        StringBuffer localStringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < i1; i2++)
        {
          String str3 = localStringTokenizer1.nextToken();
          StringTokenizer localStringTokenizer2 = new StringTokenizer(str3, ":");
          int i3 = localStringTokenizer2.countTokens();
          if (i3 >= 2)
          {
            String str4 = localStringTokenizer2.nextToken();
            String str5 = localStringTokenizer2.nextToken();
            String str6 = null;
            String str7 = null;
            if (i3 >= 3) {
              str6 = localStringTokenizer2.nextToken();
            }
            if (i3 == 4) {
              str7 = localStringTokenizer2.nextToken();
            }
            if (localStringBuffer.length() > 0) {
              localStringBuffer.append("&&");
            }
            if ((str7 == null) || (str7.trim().length() <= 0))
            {
              try
              {
                localStringBuffer.append(AccountUtil.buildAccountDisplayText(str4, paramLocale));
              }
              catch (UtilException localUtilException1)
              {
                DebugLog.throwing("Error while constructing account display string for report type, '" + str1 + "'.", localUtilException1);
                localStringBuffer.append(str4);
              }
            }
            else
            {
              localStringBuffer.append(str7);
              localStringBuffer.append(" ");
              localStringBuffer.append(":");
              localStringBuffer.append(" ");
              try
              {
                localStringBuffer.append(AccountUtil.buildAccountDisplayText(str4, paramLocale));
              }
              catch (UtilException localUtilException2)
              {
                DebugLog.throwing("Error while constructing account display string for report type, '" + str1 + "'.", localUtilException2);
                localStringBuffer.append(str4);
              }
            }
            if (paramLockboxAccounts != null) {
              for (int i4 = 0; i4 < paramLockboxAccounts.size(); i4++)
              {
                LockboxAccount localLockboxAccount = (LockboxAccount)paramLockboxAccounts.get(i4);
                if ((str4.equals(localLockboxAccount.getAccountID())) && (str5.equals(localLockboxAccount.getBankID())))
                {
                  String str8;
                  if (str6 == null)
                  {
                    str8 = localLockboxAccount.getNickname();
                    localStringBuffer.append(" - ");
                    localStringBuffer.append(str8);
                    localStringBuffer.append(" - ");
                    localStringBuffer.append(localLockboxAccount.getCurrencyType());
                    break;
                  }
                  if ((str7 == null) || (str7.equals(localLockboxAccount.getRoutingNumber())))
                  {
                    str8 = localLockboxAccount.getNickname();
                    if ((str8 != null) && (str8.length() > 0))
                    {
                      localStringBuffer.append(" - ");
                      localStringBuffer.append(str8);
                      if (str6.trim().length() > 0)
                      {
                        localStringBuffer.append(":");
                        localStringBuffer.append(str6);
                      }
                    }
                    localStringBuffer.append(" - ");
                    localStringBuffer.append(localLockboxAccount.getCurrencyType());
                    break;
                  }
                }
              }
            }
          }
        }
        paramReportCriteria.setDisplayValue("LockboxNumber", localStringBuffer.toString());
      }
    }
    str2 = localProperties1.getProperty("DataClassification");
    if (str2.equals("I")) {
      paramReportCriteria.setDisplayValue("DataClassification", ReportConsts.getText(10027, paramLocale));
    } else {
      paramReportCriteria.setDisplayValue("DataClassification", ReportConsts.getText(10026, paramLocale));
    }
  }
  
  private static ReportSortCriteria a(ReportSortCriteria paramReportSortCriteria)
  {
    ReportSortCriteria localReportSortCriteria = new ReportSortCriteria();
    if (paramReportSortCriteria == null)
    {
      localReportSortCriteria.add(new ReportSortCriterion(1, "AccountNumber", true));
      localReportSortCriteria.add(new ReportSortCriterion(2, "ProcessingDate", true));
    }
    paramReportSortCriteria.setSortedBy("ORDINAL");
    Object localObject = null;
    for (int i1 = 0; i1 < paramReportSortCriteria.size(); i1++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)paramReportSortCriteria.get(i1);
      String str = localReportSortCriterion.getName();
      if ((str.equals("AccountNumber")) || (str.equals("ProcessingDate")))
      {
        localObject = localReportSortCriterion;
        break;
      }
    }
    if (localObject == null)
    {
      localReportSortCriteria.add(new ReportSortCriterion(1, "AccountNumber", true));
      localReportSortCriteria.add(new ReportSortCriterion(2, "ProcessingDate", true));
    }
    else if (localObject.getName().equals("AccountNumber"))
    {
      localReportSortCriteria.add(new ReportSortCriterion(1, "AccountNumber", true));
      localReportSortCriteria.add(new ReportSortCriterion(2, "ProcessingDate", true));
    }
    else if (localObject.getName().equals("ProcessingDate"))
    {
      localReportSortCriteria.add(new ReportSortCriterion(1, "ProcessingDate", true));
      localReportSortCriteria.add(new ReportSortCriterion(2, "AccountNumber", true));
    }
    return localReportSortCriteria;
  }
  
  private static boolean jdField_if(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    while (localStringTokenizer.hasMoreTokens())
    {
      String str = localStringTokenizer.nextToken();
      if ("ALL".equals(str)) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.dataconsolidator.adapter.DCLockboxReport
 * JD-Core Version:    0.7.0.1
 */