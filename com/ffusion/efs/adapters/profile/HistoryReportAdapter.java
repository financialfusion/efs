package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.SecureUser;
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
import com.ffusion.beans.reporting.ReportRows;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMembers;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.reporting.RptException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.db.DBUtil;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

public class HistoryReportAdapter
  implements ProfileDefines
{
  private static final String COMMENT_ID = "COMMENT-";
  private static final String agK = "COMMENT";
  private static final String agJ = "com.ffusion.beans.history.resources";
  private static final String agG = "_";
  private static final String agF = " and modifier_type=" + String.valueOf(0);
  private static final String agI = " and modifier_type=" + String.valueOf(1);
  private static final String agH = " and ( id_type=9 or id_type=5 or id_type=14 or id_type=17 or id_type=12 or id_type=16 or id_type=15 or id_type=22 )";
  private static final String agE = "select AFFILIATE_BANK_ID from AFFILIATE_ASSIGN a where a.EMPLOYEE_ID=bank_emp.employee_id";
  
  public ReportResult getBCReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BCReportException, RptException
  {
    String str1 = "HistoryReportAdapter.getBCReportData";
    Locale localLocale = null;
    String str2 = "";
    Object localObject1 = null;
    if ((paramHashMap != null) && (paramHashMap.containsKey("UserLocale")))
    {
      localObject2 = (UserLocale)paramHashMap.get("UserLocale");
      localLocale = ((UserLocale)localObject2).getLocale();
      str2 = ((UserLocale)localObject2).getDateFormat();
    }
    else
    {
      localLocale = paramSecureUser.getLocale();
      str2 = "MM/dd/yyyy";
    }
    Object localObject2 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Timestamp localTimestamp1 = null;
    Timestamp localTimestamp2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = "date_changed";
    String str7 = null;
    boolean bool1 = true;
    boolean bool2 = false;
    int i = -1;
    int j = -1;
    ArrayList localArrayList = null;
    String str8 = null;
    boolean bool3 = false;
    ReportResult localReportResult = new ReportResult(localLocale);
    try
    {
      localObject2 = DBUtil.getConnection(Profile.poolName, true, 2);
      Properties localProperties1 = paramReportCriteria.getSearchCriteria();
      if (localProperties1 == null)
      {
        localObject3 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
        throw new Exception((String)localObject3);
      }
      Object localObject3 = paramReportCriteria.getSortCriteria();
      ReportSortCriterion localReportSortCriterion = null;
      if ((localObject3 != null) && (((ReportSortCriteria)localObject3).size() != 0))
      {
        localReportSortCriterion = (ReportSortCriterion)((ReportSortCriteria)localObject3).get(0);
        if ("User".equals(localReportSortCriterion.getName())) {
          str6 = "user_name";
        }
      }
      Properties localProperties2 = paramReportCriteria.getReportOptions();
      Object localObject4;
      if (localProperties2 == null)
      {
        localObject4 = "The report criteria used in a call to getBCReportData did not contain a valid report options object. This object is required for report retrieval.";
        throw new Exception((String)localObject4);
      }
      str3 = localProperties2.getProperty("REPORTTYPE");
      if (str3 == null)
      {
        localObject4 = "The report options contained within the Report Criteria used in a call to getBCReportData does not contain a report type.";
        throw new Exception((String)localObject4);
      }
      try
      {
        Object localObject5;
        if (str3.equals("Market Segment History Report"))
        {
          j = 3;
          i = Integer.parseInt(localProperties1.getProperty("MarketSegments"));
          localObject4 = (String)paramHashMap.get("MarketSegmentNameForReport");
          paramReportCriteria.setDisplayValue("MarketSegments", (String)localObject4);
          localObject5 = new Object[] { localObject4 };
          str7 = MessageFormat.format(ReportConsts.getText(10200, localLocale), (Object[])localObject5);
        }
        else
        {
          Object localObject6;
          if (str3.equals("Service Package History Report"))
          {
            j = 4;
            i = Integer.parseInt(localProperties1.getProperty("ServicePackages"));
            localObject4 = (String)paramHashMap.get("ServicePackageNameForReport");
            localObject5 = (String)paramHashMap.get("MarketSegmentNameForReport");
            paramReportCriteria.setDisplayValue("MarketSegments", (String)localObject5);
            paramReportCriteria.setDisplayValue("ServicePackages", (String)localObject4);
            localObject6 = new Object[] { localObject4 };
            str7 = MessageFormat.format(ReportConsts.getText(10201, localLocale), (Object[])localObject6);
          }
          else
          {
            Object localObject8;
            if (str3.equals("Bank Employee History Report"))
            {
              j = 18;
              localObject4 = localProperties1.getProperty("BankEmployee");
              i = Integer.parseInt((String)localObject4);
              localObject5 = (String)paramHashMap.get("BankEmployeeNameForReport");
              localObject6 = (String)paramHashMap.get("BankEmployeeJobTypeForReport");
              localObject7 = (String)paramHashMap.get("BankEmployeeStatusForReport");
              paramReportCriteria.setDisplayValue("BankEmployee", (String)localObject5);
              if (localObject6 == null) {
                localObject6 = ReportConsts.getText(10106, localLocale);
              }
              if (localObject7 != null) {
                switch (Integer.parseInt((String)localObject7))
                {
                case 0: 
                  localObject7 = ReportConsts.getText(10002, localLocale);
                  break;
                case 1: 
                  localObject7 = ReportConsts.getText(10107, localLocale);
                  break;
                case 2: 
                  localObject7 = ReportConsts.getText(10019, localLocale);
                  break;
                default: 
                  localObject7 = ReportConsts.getText(10106, localLocale);
                  break;
                }
              } else {
                localObject7 = ReportConsts.getText(10106, localLocale);
              }
              localObject8 = new Object[] { localObject5, localObject6, localObject7 };
              str7 = MessageFormat.format(ReportConsts.getText(10202, localLocale), (Object[])localObject8);
            }
            else if (str3.equals("Customer History Report"))
            {
              j = 1;
              bool2 = true;
              i = Integer.parseInt(localProperties1.getProperty("User"));
              localObject4 = (String)paramHashMap.get("CustomerNameForReport");
              localObject5 = (String)paramHashMap.get("BusinessNameForReport");
              localObject6 = (String)paramHashMap.get("CustomerIDForReport");
              localObject7 = new Object[] { localObject4 };
              paramReportCriteria.setDisplayValue("User", MessageFormat.format(ReportConsts.getText(10108, localLocale), (Object[])localObject7));
              if (((String)localObject5).length() > 0)
              {
                localObject8 = new Object[] { localObject4, localObject5, localObject6 };
                str7 = MessageFormat.format(ReportConsts.getText(10215, localLocale), (Object[])localObject8);
              }
              else
              {
                str7 = MessageFormat.format(ReportConsts.getText(10203, localLocale), (Object[])localObject7);
              }
            }
            else if (str3.equals("Business History Report"))
            {
              bool2 = true;
              j = 2;
              i = Integer.parseInt(localProperties1.getProperty("Business"));
              localObject4 = Profile.getBusinessNameAndCustId((Connection)localObject2, String.valueOf(i), localLocale);
              localObject5 = (String)paramHashMap.get("MarketSegmentNameForReport");
              localObject6 = (String)paramHashMap.get("ServicePackageNameForReport");
              paramReportCriteria.setDisplayValue("Business", (String)localObject4);
              if (localObject5 != null) {
                paramReportCriteria.setDisplayValue("MarketSegments", (String)localObject5);
              }
              if (localObject6 != null) {
                paramReportCriteria.setDisplayValue("ServicePackages", (String)localObject6);
              }
              localObject7 = new Object[] { localObject4 };
              str7 = MessageFormat.format(ReportConsts.getText(10205, localLocale), (Object[])localObject7);
              localObject8 = "on";
              if (((String)localObject8).equals(localProperties1.getProperty("IncludeDivisionsAndGroups")))
              {
                paramReportCriteria.setDisplayValue("IncludeDivisionsAndGroups", ReportConsts.getText(2308, localLocale));
                bool2 = true;
                localArrayList = (ArrayList)paramHashMap.get("DivisionsAndGroupsForReport");
                if (localArrayList == null)
                {
                  localObject9 = "Unable to find the list of division and group ids in the extra HashMap";
                  throw new Exception((String)localObject9);
                }
              }
              else
              {
                localArrayList = new ArrayList();
                paramReportCriteria.setDisplayValue("IncludeDivisionsAndGroups", ReportConsts.getText(2309, localLocale));
              }
            }
            else if (str3.equals("Transaction History Report"))
            {
              j = -1;
              str5 = " and ( id_type=9 or id_type=5 or id_type=14 or id_type=17 or id_type=12 or id_type=16 or id_type=15 or id_type=22 )";
              str8 = localProperties1.getProperty("TrackingId").trim();
              if (str8.length() > 20) {
                throw new BCReportException(32);
              }
              bool1 = false;
              localObject4 = new Object[] { str8 };
              str7 = MessageFormat.format(ReportConsts.getText(10206, localLocale), (Object[])localObject4);
              if (str8 == null)
              {
                localObject5 = "The tracking id is missing from the search criteria for a transaction history report.";
                throw new Exception((String)localObject5);
              }
            }
            else if (str3.equals("Managed Participant History Report"))
            {
              j = 8;
              i = Integer.parseInt(localProperties1.getProperty("ManagedParticipant"));
              localObject4 = Profile.getBusinessNameAndCustId((Connection)localObject2, localProperties1.getProperty("Business"), localLocale);
              bool1 = false;
              localObject5 = (String)paramHashMap.get("ACHCompanyNameForReport");
              localObject6 = (String)paramHashMap.get("ManagedParticipantNameForReport");
              paramReportCriteria.setDisplayValue("Business", (String)localObject4);
              paramReportCriteria.setDisplayValue("ACHCompanyID", (String)localObject5);
              paramReportCriteria.setDisplayValue("ManagedParticipant", (String)localObject6);
              paramReportCriteria.setHiddenSearchCriterion("ACHCompanyCoID", true);
              localObject7 = new Object[] { localObject6 };
              str7 = MessageFormat.format(ReportConsts.getText(10207, localLocale), (Object[])localObject7);
            }
            else if (str3.equals("Beneficiary History Report"))
            {
              j = 13;
              i = Integer.parseInt(localProperties1.getProperty("Beneficiary"));
              localObject4 = Profile.getBusinessNameAndCustId((Connection)localObject2, localProperties1.getProperty("Business"), localLocale);
              localObject5 = (String)paramHashMap.get("BeneficiaryNameForReport");
              paramReportCriteria.setDisplayValue("Business", (String)localObject4);
              paramReportCriteria.setDisplayValue("Beneficiary", (String)localObject5);
              localObject6 = new Object[] { localObject5 };
              str7 = MessageFormat.format(ReportConsts.getText(10208, localLocale), (Object[])localObject6);
              bool1 = false;
            }
            else if (str3.equals("Payee History Report"))
            {
              j = 10;
              i = Integer.parseInt(localProperties1.getProperty("Payee"));
              bool1 = false;
              localObject4 = Profile.getBusinessNameAndCustId((Connection)localObject2, localProperties1.getProperty("Business"), localLocale);
              localObject5 = (String)paramHashMap.get("CustomerNameForReport");
              if ((localObject4 != null) && (!((String)localObject4).equals("")) && (localObject5 != null) && (!((String)localObject5).equals(""))) {
                throw new BCReportException(31);
              }
              localObject6 = (String)paramHashMap.get("PayeeNameForReport");
              if (localObject4 != null) {
                paramReportCriteria.setDisplayValue("Business", (String)localObject4);
              }
              if (localObject5 != null) {
                paramReportCriteria.setDisplayValue("Consumer", (String)localObject5);
              }
              if (localObject6 != null) {
                paramReportCriteria.setDisplayValue("Payee", (String)localObject6);
              }
              localObject7 = new Object[] { localObject6 };
              str7 = MessageFormat.format(ReportConsts.getText(10209, localLocale), (Object[])localObject7);
            }
            else if (str3.equals("ACH Company History Report"))
            {
              j = 11;
              i = Integer.parseInt(localProperties1.getProperty("ACHCompanyID"));
              localObject4 = (String)paramHashMap.get("ACHCompanyNameForReport");
              localObject5 = Profile.getBusinessNameAndCustId((Connection)localObject2, localProperties1.getProperty("Business"), localLocale);
              paramReportCriteria.setDisplayValue("Business", (String)localObject5);
              paramReportCriteria.setDisplayValue("ACHCompanyID", (String)localObject4);
              paramReportCriteria.setHiddenSearchCriterion("ACHCompanyCoID", true);
              localObject6 = new Object[] { localObject4 };
              str7 = MessageFormat.format(ReportConsts.getText(10210, localLocale), (Object[])localObject6);
            }
            else if (str3.equals("Account Group History Report"))
            {
              j = 26;
              localObject4 = localProperties1.getProperty("AccountGroup");
              i = Integer.parseInt((String)localObject4);
              localObject5 = (String)paramHashMap.get("AccountGroupNameForReport");
              localObject6 = (String)paramHashMap.get("BusinessNameForReport");
              paramReportCriteria.setDisplayValue("Business", (String)localObject6);
              paramReportCriteria.setDisplayValue("AccountGroup", (String)localObject5);
              localObject7 = new Object[] { localObject5 };
              str7 = MessageFormat.format(ReportConsts.getText(10214, localLocale), (Object[])localObject7);
            }
            else if (str3.equals("Cash Concentration Company History Report"))
            {
              j = 23;
              localObject4 = localProperties1.getProperty("CashConCompany");
              try
              {
                i = Integer.parseInt((String)localObject4);
              }
              catch (NumberFormatException localNumberFormatException2)
              {
                int m = ((String)localObject4).length();
                if (m > 9)
                {
                  localObject4 = ((String)localObject4).substring(m - 9);
                  i = Integer.parseInt((String)localObject4);
                }
              }
              String str9 = (String)paramHashMap.get("CashConCompanyNameForReport");
              String str12 = (String)paramHashMap.get("BusinessNameForReport");
              paramReportCriteria.setDisplayValue("Business", str12);
              paramReportCriteria.setDisplayValue("CashConCompany", str9);
              localObject7 = new Object[] { str9 };
              str7 = MessageFormat.format(ReportConsts.getText(10211, localLocale), (Object[])localObject7);
            }
            else if (str3.equals("External Transfer ACH Batch Information Report"))
            {
              j = 24;
              localObject4 = localProperties1.getProperty("ExtTransferCompany");
              try
              {
                i = Integer.parseInt((String)localObject4);
              }
              catch (NumberFormatException localNumberFormatException3)
              {
                int n = ((String)localObject4).length();
                if (n > 9)
                {
                  localObject4 = ((String)localObject4).substring(n - 9);
                  i = Integer.parseInt((String)localObject4);
                }
              }
              String str10 = Profile.getBusinessNameAndCustId((Connection)localObject2, localProperties1.getProperty("Business"), localLocale);
              String str13 = (String)paramHashMap.get("ExtTransferCompanyNameForReport");
              paramReportCriteria.setDisplayValue("Business", str10);
              paramReportCriteria.setDisplayValue("ExtTransferCompany", str13);
              localObject7 = new Object[] { str13 };
              str7 = MessageFormat.format(ReportConsts.getText(10212, localLocale), (Object[])localObject7);
            }
            else if (str3.equals("External Transfer Account Report"))
            {
              j = 25;
              localObject4 = localProperties1.getProperty("Account");
              try
              {
                i = Integer.parseInt((String)localObject4);
              }
              catch (NumberFormatException localNumberFormatException4)
              {
                int i1 = ((String)localObject4).length();
                if (i1 > 9)
                {
                  localObject4 = ((String)localObject4).substring(i1 - 9);
                  i = Integer.parseInt((String)localObject4);
                }
              }
              bool2 = true;
              str11 = Profile.getBusinessNameAndCustId((Connection)localObject2, localProperties1.getProperty("Business"), localLocale);
              String str14 = (String)paramHashMap.get("CustomerNameForReport");
              if ((str11 != null) && (!str11.equals("")) && (str14 != null) && (!str14.equals(""))) {
                throw new BCReportException(31);
              }
              if (str11 != null) {
                paramReportCriteria.setDisplayValue("Business", str11);
              }
              if (str14 != null) {
                paramReportCriteria.setDisplayValue("Consumer", str14);
              }
              localObject7 = (String)paramHashMap.get("AccountNameForReport");
              paramReportCriteria.setDisplayValue("Account", (String)localObject7);
              paramReportCriteria.setHiddenSearchCriterion("ExtTransferCompany", true);
              localObject8 = new Object[] { localObject7 };
              str7 = MessageFormat.format(ReportConsts.getText(10213, localLocale), (Object[])localObject8);
            }
            else
            {
              localObject4 = "The report options contained within the Report Criteria used in a call to getBCReportData specify the report type as:" + str3 + ". This is not a valid type for a History report.";
              throw new Exception((String)localObject4);
            }
          }
        }
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        str11 = "The search criteria specifying the id of the history item to query is missing or its format is not valid for a report of type: " + str3;
        throw new Exception(str11);
      }
      str4 = localProperties2.getProperty("MAXDISPLAYSIZE");
      int k = 0;
      String str11 = localProperties1.getProperty("Affiliate Bank");
      if ((str11 != null) && (!str11.equals("AllAffiliateBanks")))
      {
        try
        {
          k = Integer.parseInt(str11);
        }
        catch (NumberFormatException localNumberFormatException5)
        {
          throw new Exception("Invalid affiliate bank ID.");
        }
        str15 = (String)paramHashMap.get("AffiliateBankNameForReport");
        paramReportCriteria.setDisplayValue("Affiliate Bank", str15);
      }
      else
      {
        paramReportCriteria.setDisplayValue("Affiliate Bank", ReportConsts.getText(10048, localLocale));
      }
      String str15 = localProperties1.getProperty("StartDate");
      Object localObject7 = localProperties1.getProperty("EndDate");
      try
      {
        localTimestamp1 = e(str15, "MM/dd/yyyy HH:mm:ss");
        localTimestamp2 = e((String)localObject7, "MM/dd/yyyy HH:mm:ss");
      }
      catch (ParseException localParseException)
      {
        throw new Exception("Invalid format for date and time search criteria.");
      }
      localReportResult.init(paramReportCriteria);
      ReportHeading localReportHeading = new ReportHeading(localLocale);
      Object localObject9 = new ReportDataDimensions(localLocale);
      localReportHeading.setLabel(str7);
      localReportResult.setHeading(localReportHeading);
      if (bool3 == true)
      {
        ((ReportDataDimensions)localObject9).setNumRows(0);
      }
      else
      {
        i2 = jdMethod_for(localReportResult, localLocale, j);
        ((ReportDataDimensions)localObject9).setNumColumns(i2);
        ((ReportDataDimensions)localObject9).setNumRows(-1);
      }
      localReportResult.setDataDimensions((ReportDataDimensions)localObject9);
      if (!bool2)
      {
        if ((bool3 == true) && (j == 2)) {
          localPreparedStatement = jdMethod_for((Connection)localObject2, str5, localTimestamp1, localTimestamp2, str6);
        } else {
          localPreparedStatement = jdMethod_for((Connection)localObject2, str5, bool1, str8, k, localTimestamp1, localTimestamp2, str6);
        }
      }
      else {
        localPreparedStatement = jdMethod_for((Connection)localObject2, localArrayList, k, localTimestamp1, localTimestamp2, str6);
      }
      localResultSet = jdMethod_for(localPreparedStatement, i, j, bool2, localArrayList, str8, str4, localTimestamp1, localTimestamp2);
      int i2 = jdMethod_for((Connection)localObject2, localResultSet, localReportResult, bool3, j);
      if (str4 != null)
      {
        int i3 = Integer.parseInt(str4);
        if (i2 == i3)
        {
          HashMap localHashMap = new HashMap();
          localHashMap.put("TRUNCATED", new Integer(i2));
          localReportResult.setProperties(localHashMap);
        }
      }
    }
    catch (BCReportException localBCReportException)
    {
      throw new BCReportException(localBCReportException, str1, localBCReportException.code);
    }
    catch (Exception localException)
    {
      localReportResult.setError(localException);
      throw new BCReportException(localException, str1, 23);
    }
    finally
    {
      DBUtil.closeAll(Profile.poolName, (Connection)localObject2, localPreparedStatement, localResultSet);
      localReportResult.fini();
    }
    return localReportResult;
  }
  
  private PreparedStatement jdMethod_for(Connection paramConnection, String paramString1, boolean paramBoolean, String paramString2, int paramInt, Timestamp paramTimestamp1, Timestamp paramTimestamp2, String paramString3)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    String str = "select z.user_name,modifier_type,date_changed,column_changed,old_value,new_value,history_comment from history, ";
    try
    {
      StringBuffer localStringBuffer = new StringBuffer(str);
      if (paramBoolean)
      {
        localStringBuffer.append("bank_employee");
        localStringBuffer.append(" z where history.modifier_id=z.employee_id");
      }
      else
      {
        localStringBuffer.append("customer z ");
        if (paramInt != 0)
        {
          localStringBuffer.append(" left outer join business_employee be on be.directory_id=z.directory_id ");
          localStringBuffer.append(" left outer join business b on be.business_id = b.business_id ");
        }
        localStringBuffer.append(" where history.modifier_id=z.directory_id");
      }
      if (paramString2 == null) {
        localStringBuffer.append(" and id=? and id_type=? ");
      } else {
        localStringBuffer.append(" and id_str=? ");
      }
      if ((paramInt != 0) && (!paramBoolean))
      {
        localStringBuffer.append(" and (z.");
        localStringBuffer.append("affiliate_bank_id");
        localStringBuffer.append(" = ");
        localStringBuffer.append(paramInt);
        localStringBuffer.append(" or b.");
        localStringBuffer.append("affiliate_bank_id");
        localStringBuffer.append(" = ");
        localStringBuffer.append(paramInt);
        localStringBuffer.append(") ");
      }
      if (paramTimestamp1 != null) {
        Profile.appendSQLSelectDate(localStringBuffer, "date_changed", paramTimestamp1, ">=", true);
      }
      if (paramTimestamp2 != null) {
        Profile.appendSQLSelectDate(localStringBuffer, "date_changed", paramTimestamp2, "<=", true);
      }
      if ((paramString1 != null) && (paramString1.length() != 0)) {
        localStringBuffer.append(paramString1);
      }
      if ((paramString3 != null) && (paramString3.length() != 0))
      {
        localStringBuffer.append(" ORDER BY ");
        localStringBuffer.append(paramString3);
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      throw new Exception(localException.getMessage());
    }
    return localPreparedStatement;
  }
  
  private PreparedStatement jdMethod_for(Connection paramConnection, String paramString1, Timestamp paramTimestamp1, Timestamp paramTimestamp2, String paramString2)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    String str = "select z.user_name,modifier_type,date_changed,column_changed,old_value,new_value,history_comment, id, business_name from history, business, ";
    try
    {
      StringBuffer localStringBuffer = new StringBuffer(str);
      localStringBuffer.append("bank_employee");
      localStringBuffer.append(" z where (history.modifier_id=z.employee_id and business.directory_id=id ");
      localStringBuffer.append(" and id_type=? ");
      if (paramTimestamp1 != null) {
        Profile.appendSQLSelectDate(localStringBuffer, "date_changed", paramTimestamp1, ">=", true);
      }
      if (paramTimestamp2 != null) {
        Profile.appendSQLSelectDate(localStringBuffer, "date_changed", paramTimestamp2, "<=", true);
      }
      if ((paramString1 != null) && (paramString1.length() != 0)) {
        localStringBuffer.append(paramString1);
      }
      if ((paramString2 != null) && (paramString2.length() != 0))
      {
        localStringBuffer.append(" ORDER BY id, ");
        localStringBuffer.append(paramString2);
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer.toString());
    }
    catch (Exception localException)
    {
      throw new Exception(localException.getMessage());
    }
    return localPreparedStatement;
  }
  
  private PreparedStatement jdMethod_for(Connection paramConnection, ArrayList paramArrayList, int paramInt, Timestamp paramTimestamp1, Timestamp paramTimestamp2, String paramString)
    throws Exception
  {
    PreparedStatement localPreparedStatement = null;
    StringBuffer localStringBuffer1 = null;
    StringBuffer localStringBuffer2 = null;
    StringBuffer localStringBuffer3 = null;
    StringBuffer localStringBuffer4 = null;
    StringBuffer localStringBuffer5 = null;
    String str1 = "select z.user_name,z.customer_type as CUSTOMER_TYPE,modifier_type,date_changed,column_changed,old_value,new_value,history_comment from history , customer z  where modifier_type=" + String.valueOf(1) + " and id=? and id_type=? and modifier_id=z.directory_id ";
    String str2 = "select z.user_name,'d' as CUSTOMER_TYPE,modifier_type,date_changed,column_changed,old_value,new_value,history_comment from history, bank_employee z where modifier_type=" + String.valueOf(0) + " and id=? and id_type=? and modifier_id=z.employee_id ";
    String str3 = " select '' as user_name,'d' as CUSTOMER_TYPE,modifier_type,date_changed,column_changed,old_value,new_value,history_comment  from history where modifier_type =" + String.valueOf(2) + " and id=? and id_type=? ";
    String str4 = "select z.user_name,z.customer_type as CUSTOMER_TYPE,modifier_type,date_changed,column_changed,old_value,new_value,history_comment from history, customer z where modifier_id=z.directory_id and id=? and id_type=? and modifier_type=" + String.valueOf(1);
    String str5 = "select bankemp.user_name,'d' as CUSTOMER_TYPE,modifier_type,date_changed,column_changed,old_value,new_value,history_comment from history, bank_employee bankemp where modifier_id=bankemp.employee_id and id=? and id_type=? and modifier_type=" + String.valueOf(0);
    String str6 = "select z.user_name,'d' as CUSTOMER_TYPE,modifier_type,date_changed,column_changed,old_value,new_value,history_comment from history, customer z, entitlement_group grp where modifier_id=z.directory_id and modifier_type=" + String.valueOf(1) + " and ( id_type=" + String.valueOf(6) + " or id_type=" + String.valueOf(7) + " )" + " and id=grp.ent_group_id ";
    String str7 = "select '' as user_name,'d' as CUSTOMER_TYPE, modifier_type,date_changed,column_changed,old_value,new_value,history_comment  from history where id=? and modifier_type =" + String.valueOf(2) + " and id_type=" + String.valueOf(2);
    String str8 = "select '' as user_name,'d' as CUSTOMER_TYPE,modifier_type,date_changed,column_changed,old_value,new_value,history_comment from history, entitlement_group grp where modifier_type=" + String.valueOf(2) + " and ( id_type=" + String.valueOf(6) + " or id_type=" + String.valueOf(7) + " )" + " and id=grp.ent_group_id ";
    try
    {
      if (paramArrayList == null)
      {
        localStringBuffer1 = new StringBuffer(str1);
        localStringBuffer2 = new StringBuffer(str2);
        localStringBuffer3 = new StringBuffer(str3);
      }
      else
      {
        localStringBuffer1 = new StringBuffer(str4);
        localStringBuffer2 = new StringBuffer(str5);
        localStringBuffer3 = new StringBuffer(str6);
        localStringBuffer4 = new StringBuffer(str7);
        localStringBuffer5 = new StringBuffer(str8);
        if (paramArrayList.size() != 0)
        {
          localStringBuffer3.append(" and ( ");
          localStringBuffer5.append(" and ( ");
          int i = 0;
          Iterator localIterator = paramArrayList.iterator();
          while (localIterator.hasNext())
          {
            String str9 = (String)localIterator.next();
            if (i != 0)
            {
              localStringBuffer3.append(" OR ");
              localStringBuffer5.append(" OR ");
            }
            else
            {
              i = 1;
            }
            localStringBuffer3.append(" grp.ent_group_id=");
            localStringBuffer3.append(str9);
            localStringBuffer5.append(" id= ");
            localStringBuffer5.append(str9);
          }
          localStringBuffer3.append(" ) ");
          localStringBuffer5.append(" ) ");
        }
      }
      if (paramTimestamp1 != null)
      {
        Profile.appendSQLSelectDate(localStringBuffer1, "date_changed", paramTimestamp1, ">=", true);
        Profile.appendSQLSelectDate(localStringBuffer2, "date_changed", paramTimestamp1, ">=", true);
        Profile.appendSQLSelectDate(localStringBuffer3, "date_changed", paramTimestamp1, ">=", true);
        if (localStringBuffer4 != null) {
          Profile.appendSQLSelectDate(localStringBuffer4, "date_changed", paramTimestamp1, ">=", true);
        }
        if (localStringBuffer5 != null) {
          Profile.appendSQLSelectDate(localStringBuffer5, "date_changed", paramTimestamp1, ">=", true);
        }
      }
      if (paramTimestamp2 != null)
      {
        Profile.appendSQLSelectDate(localStringBuffer1, "date_changed", paramTimestamp2, "<=", true);
        Profile.appendSQLSelectDate(localStringBuffer2, "date_changed", paramTimestamp2, "<=", true);
        Profile.appendSQLSelectDate(localStringBuffer3, "date_changed", paramTimestamp2, "<=", true);
        if (localStringBuffer4 != null) {
          Profile.appendSQLSelectDate(localStringBuffer4, "date_changed", paramTimestamp2, "<=", true);
        }
        if (localStringBuffer5 != null) {
          Profile.appendSQLSelectDate(localStringBuffer5, "date_changed", paramTimestamp2, "<=", true);
        }
      }
      localStringBuffer1.append(" UNION ");
      localStringBuffer1.append(localStringBuffer2);
      if ((paramArrayList == null) || (paramArrayList.size() != 0))
      {
        localStringBuffer1.append(" UNION ");
        localStringBuffer1.append(localStringBuffer3);
      }
      if (localStringBuffer4 != null)
      {
        localStringBuffer1.append(" UNION ");
        localStringBuffer1.append(localStringBuffer4);
      }
      if ((localStringBuffer5 != null) && (paramArrayList != null) && (paramArrayList.size() != 0))
      {
        localStringBuffer1.append(" UNION ");
        localStringBuffer1.append(localStringBuffer5);
      }
      if ((paramString != null) && (paramString.length() != 0))
      {
        localStringBuffer1.append(" ORDER BY ");
        localStringBuffer1.append(paramString);
      }
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
    }
    catch (Exception localException)
    {
      throw new Exception(localException.getMessage());
    }
    return localPreparedStatement;
  }
  
  private ResultSet jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2, boolean paramBoolean, ArrayList paramArrayList, String paramString1, String paramString2, Timestamp paramTimestamp1, Timestamp paramTimestamp2)
    throws Exception
  {
    ResultSet localResultSet = null;
    int i = 1;
    try
    {
      if (paramInt1 >= 0) {
        i = Profile.setStatementInt(paramPreparedStatement, i, paramInt1, false);
      }
      i = Profile.setStatementString(paramPreparedStatement, i, paramString1, "tracking_id", true);
      i = Profile.setStatementInt(paramPreparedStatement, i, paramInt2, true);
      i = Profile.setStatementDate(paramPreparedStatement, i, paramTimestamp1, true);
      i = Profile.setStatementDate(paramPreparedStatement, i, paramTimestamp2, true);
      if (paramBoolean)
      {
        i = Profile.setStatementInt(paramPreparedStatement, i, paramInt1, false);
        i = Profile.setStatementInt(paramPreparedStatement, i, paramInt2, false);
        i = Profile.setStatementDate(paramPreparedStatement, i, paramTimestamp1, true);
        i = Profile.setStatementDate(paramPreparedStatement, i, paramTimestamp2, true);
        if (paramArrayList == null)
        {
          i = Profile.setStatementInt(paramPreparedStatement, i, paramInt1, false);
          i = Profile.setStatementInt(paramPreparedStatement, i, paramInt2, false);
          i = Profile.setStatementDate(paramPreparedStatement, i, paramTimestamp1, true);
          i = Profile.setStatementDate(paramPreparedStatement, i, paramTimestamp2, true);
        }
        else
        {
          if (paramArrayList.size() > 0)
          {
            i = Profile.setStatementDate(paramPreparedStatement, i, paramTimestamp1, true);
            i = Profile.setStatementDate(paramPreparedStatement, i, paramTimestamp2, true);
          }
          i = Profile.setStatementInt(paramPreparedStatement, i, paramInt1, false);
          i = Profile.setStatementDate(paramPreparedStatement, i, paramTimestamp1, true);
          i = Profile.setStatementDate(paramPreparedStatement, i, paramTimestamp2, true);
          if (paramArrayList.size() > 0)
          {
            i = Profile.setStatementDate(paramPreparedStatement, i, paramTimestamp1, true);
            i = Profile.setStatementDate(paramPreparedStatement, i, paramTimestamp2, true);
          }
        }
      }
      try
      {
        if (paramString2 != null) {
          paramPreparedStatement.setMaxRows(Integer.parseInt(paramString2));
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        String str = "The report option: MaxDisplaySize, specified for the history report is not a valid number ";
        throw new Exception(str);
      }
      localResultSet = DBUtil.executeQuery(paramPreparedStatement, "");
    }
    catch (Exception localException)
    {
      throw new Exception(localException.getMessage());
    }
    return localResultSet;
  }
  
  private int jdMethod_for(Connection paramConnection, ResultSet paramResultSet, ReportResult paramReportResult, boolean paramBoolean, int paramInt)
    throws Exception
  {
    String str1 = "HistoryReportAdapter.populateReportFromResultSet";
    String str2 = null;
    String str3 = null;
    Locale localLocale = paramReportResult.getLocale();
    ReportRows localReportRows = new ReportRows(localLocale);
    int i = 0;
    ReportResult localReportResult = new ReportResult(localLocale);
    try
    {
      String str5 = "";
      for (int j = 0; paramResultSet.next(); j++)
      {
        if ((paramBoolean == true) && (str5.compareTo(Profile.getRSString(paramResultSet, "business_name")) != 0))
        {
          str5 = Profile.getRSString(paramResultSet, "business_name");
          localReportResult = new ReportResult(localLocale);
          paramReportResult.addSubReport(localReportResult);
          localObject1 = new ReportHeading(localLocale);
          localObject2 = new ReportDataDimensions(localLocale);
          int k = jdMethod_for(localReportResult, localLocale, paramInt);
          ((ReportDataDimensions)localObject2).setNumColumns(k);
          ((ReportDataDimensions)localObject2).setNumRows(-1);
          localReportResult.setDataDimensions((ReportDataDimensions)localObject2);
          ((ReportHeading)localObject1).setLabel(str5);
          localReportResult.setHeading((ReportHeading)localObject1);
          i++;
          j = 0;
        }
        Object localObject1 = new ReportRow(localLocale);
        if (j % 2 == 1) {
          ((ReportRow)localObject1).set("CELLBACKGROUND", "reportDataShaded");
        }
        Object localObject2 = new ReportDataItems(localLocale);
        ReportDataItem localReportDataItem = null;
        DateTime localDateTime = new DateTime(paramResultSet.getTimestamp("date_changed"), localLocale);
        localDateTime.setFormat(ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", localLocale));
        str2 = localDateTime.toString();
        localDateTime.setFormat("HH:mm");
        str3 = localDateTime.toString();
        if (str2 == null)
        {
          ((ReportDataItems)localObject2).add(null);
        }
        else
        {
          localReportDataItem = ((ReportDataItems)localObject2).add();
          localReportDataItem.setData(str2);
        }
        if (str3 == null)
        {
          ((ReportDataItems)localObject2).add(null);
        }
        else
        {
          localReportDataItem = ((ReportDataItems)localObject2).add();
          localReportDataItem.setData(str3);
        }
        if (Profile.getRSString(paramResultSet, "column_changed") == null)
        {
          ((ReportDataItems)localObject2).add(null);
        }
        else
        {
          localReportDataItem = ((ReportDataItems)localObject2).add();
          localReportDataItem.setData(Profile.getRSString(paramResultSet, "column_changed"));
        }
        if (Profile.getRSString(paramResultSet, "old_value") == null)
        {
          ((ReportDataItems)localObject2).add(null);
        }
        else
        {
          localReportDataItem = ((ReportDataItems)localObject2).add();
          localReportDataItem.setData(Profile.getRSString(paramResultSet, "old_value"));
        }
        if (Profile.getRSString(paramResultSet, "new_value") == null)
        {
          ((ReportDataItems)localObject2).add(null);
        }
        else
        {
          localReportDataItem = ((ReportDataItems)localObject2).add();
          localReportDataItem.setData(Profile.getRSString(paramResultSet, "new_value"));
        }
        String str6 = Profile.getRSString(paramResultSet, "modifier_type");
        if ((Profile.getRSString(paramResultSet, "user_name") == null) || (str6.equals("2")))
        {
          ((ReportDataItems)localObject2).add(null);
        }
        else
        {
          localReportDataItem = ((ReportDataItems)localObject2).add();
          localReportDataItem.setData(Profile.getRSString(paramResultSet, "user_name"));
        }
        String str7;
        if ((paramInt == 2) || (paramInt == 1)) {
          if (str6 == null)
          {
            ((ReportDataItems)localObject2).add(null);
          }
          else
          {
            localReportDataItem = ((ReportDataItems)localObject2).add();
            str7 = new String();
            if (str6.equals("0"))
            {
              str7 = ReportConsts.getText(3200, localLocale);
            }
            else if (str6.equals("1"))
            {
              String str4 = paramResultSet.getString("CUSTOMER_TYPE");
              if (str4.equals("2")) {
                str7 = ReportConsts.getText(3203, localLocale);
              } else {
                str7 = ReportConsts.getText(3201, localLocale);
              }
            }
            else if (str6.equals("2"))
            {
              str7 = ReportConsts.getText(3202, localLocale);
            }
            localReportDataItem.setData(str7);
          }
        }
        if (Profile.getRSString(paramResultSet, "history_comment") == null)
        {
          ((ReportDataItems)localObject2).add(null);
        }
        else
        {
          localReportDataItem = ((ReportDataItems)localObject2).add();
          str7 = Profile.getRSString(paramResultSet, "history_comment");
          str7 = jdMethod_try(str7, localLocale);
          localReportDataItem.setData(str7);
        }
        ((ReportRow)localObject1).setDataItems((ReportDataItems)localObject2);
        if (paramBoolean == true) {
          localReportResult.addRow((ReportRow)localObject1);
        } else {
          paramReportResult.addRow((ReportRow)localObject1);
        }
        i++;
      }
    }
    catch (Exception localException)
    {
      throw new BCReportException(localException, str1, 23);
    }
    return i;
  }
  
  private int jdMethod_for(ReportResult paramReportResult, Locale paramLocale, int paramInt)
    throws RptException
  {
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    ArrayList localArrayList1 = new ArrayList();
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(14);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(900, paramLocale));
    localReportColumn.setLabels(localArrayList2);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    ArrayList localArrayList3 = new ArrayList();
    localArrayList3.add(ReportConsts.getColumnName(901, paramLocale));
    localReportColumn.setLabels(localArrayList3);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    ArrayList localArrayList4 = new ArrayList();
    localArrayList4.add(ReportConsts.getColumnName(902, paramLocale));
    localReportColumn.setLabels(localArrayList4);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    ArrayList localArrayList5 = new ArrayList();
    localArrayList5.add(ReportConsts.getColumnName(903, paramLocale));
    localReportColumn.setLabels(localArrayList5);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    ArrayList localArrayList6 = new ArrayList();
    localArrayList6.add(ReportConsts.getColumnName(904, paramLocale));
    localReportColumn.setLabels(localArrayList6);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    ArrayList localArrayList7 = new ArrayList();
    localArrayList7.add(ReportConsts.getColumnName(905, paramLocale));
    localReportColumn.setLabels(localArrayList7);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    if ((paramInt == 2) || (paramInt == 1))
    {
      localArrayList8 = new ArrayList();
      localArrayList8.add(ReportConsts.getColumnName(907, paramLocale));
      localReportColumn.setLabels(localArrayList8);
      paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    }
    ArrayList localArrayList8 = new ArrayList();
    localArrayList8.add(ReportConsts.getColumnName(906, paramLocale));
    localReportColumn.setLabels(localArrayList8);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    return paramReportResult.getColumns().size();
  }
  
  private static Timestamp e(String paramString1, String paramString2)
    throws ParseException
  {
    if ((paramString1 != null) && (paramString1.length() != 0))
    {
      DateFormat localDateFormat = DateFormatUtil.getFormatter(paramString2);
      Date localDate = localDateFormat.parse(paramString1);
      return new Timestamp(localDate.getTime());
    }
    return null;
  }
  
  private ArrayList jdMethod_null(int paramInt)
    throws BCReportException
  {
    String str = "getDivisionAndGroupIds()";
    ArrayList localArrayList = new ArrayList();
    EntitlementGroup localEntitlementGroup1;
    try
    {
      localEntitlementGroup1 = Entitlements.getEntitlementGroup(paramInt);
    }
    catch (CSILException localCSILException)
    {
      throw new BCReportException(localCSILException, str, 23);
    }
    EntitlementGroups localEntitlementGroups = new EntitlementGroups();
    jdMethod_new(localEntitlementGroup1, localEntitlementGroups);
    if ((localEntitlementGroups != null) && (localEntitlementGroups.size() != 0))
    {
      Iterator localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        EntitlementGroup localEntitlementGroup2 = (EntitlementGroup)localIterator.next();
        localArrayList.add(String.valueOf(localEntitlementGroup2.getGroupId()));
      }
    }
    else
    {
      return null;
    }
    return localArrayList;
  }
  
  private void jdMethod_new(EntitlementGroup paramEntitlementGroup, EntitlementGroups paramEntitlementGroups)
    throws BCReportException
  {
    String str = "recurseDivisionsAndGroups";
    if ((paramEntitlementGroup.getEntGroupType().equals("Division")) || (paramEntitlementGroup.getEntGroupType().equals("Group"))) {
      paramEntitlementGroups.add(paramEntitlementGroup);
    }
    Iterator localIterator = null;
    EntitlementGroups localEntitlementGroups = null;
    EntitlementGroup localEntitlementGroup = null;
    try
    {
      localEntitlementGroups = Entitlements.getChildrenEntitlementGroups(paramEntitlementGroup.getGroupId());
      if (localEntitlementGroups != null)
      {
        localIterator = localEntitlementGroups.iterator();
        while (localIterator.hasNext())
        {
          localEntitlementGroup = (EntitlementGroup)localIterator.next();
          jdMethod_new(localEntitlementGroup, paramEntitlementGroups);
        }
      }
    }
    catch (CSILException localCSILException)
    {
      throw new BCReportException(localCSILException, str, 23);
    }
  }
  
  private ArrayList jdMethod_void(int paramInt)
    throws BCReportException
  {
    String str = "getPackagesFromSegment";
    try
    {
      ArrayList localArrayList = new ArrayList();
      EntitlementGroups localEntitlementGroups = Entitlements.getChildrenByGroupType(paramInt, "ServicesPackage");
      Iterator localIterator = localEntitlementGroups.iterator();
      while (localIterator.hasNext())
      {
        EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
        localArrayList.add(new Integer(localEntitlementGroup.getGroupId()));
      }
      return localArrayList;
    }
    catch (Exception localException)
    {
      throw new BCReportException(localException, str, 23);
    }
  }
  
  private ArrayList jdMethod_case(ArrayList paramArrayList)
    throws BCReportException
  {
    String str1 = "getBusIdsFromPackages";
    try
    {
      if ((paramArrayList == null) || (paramArrayList.size() == 0)) {
        return null;
      }
      ArrayList localArrayList = new ArrayList();
      for (int i = 0; i < paramArrayList.size(); i++)
      {
        Integer localInteger = (Integer)paramArrayList.get(i);
        int j = localInteger.intValue();
        EntitlementGroups localEntitlementGroups = Entitlements.getChildrenByGroupType(j, "BusinessAdmin");
        Iterator localIterator = localEntitlementGroups.iterator();
        while (localIterator.hasNext())
        {
          EntitlementGroup localEntitlementGroup = (EntitlementGroup)localIterator.next();
          String str2 = ((EntitlementGroupMember)Entitlements.getMembers(localEntitlementGroup.getGroupId()).get(0)).getId();
          localArrayList.add(Integer.valueOf(str2));
        }
      }
      return localArrayList;
    }
    catch (Exception localException)
    {
      throw new BCReportException(localException, str1, 23);
    }
  }
  
  private static String jdMethod_try(String paramString, Locale paramLocale)
  {
    StringBuffer localStringBuffer = new StringBuffer("COMMENT-");
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, " ");
    String str1 = "";
    int i = 0;
    for (int j = 0; localStringTokenizer.hasMoreTokens(); j++)
    {
      if (j > 0) {
        localStringBuffer.append("_");
      }
      localStringBuffer.append(localStringTokenizer.nextToken());
    }
    try
    {
      i = Integer.parseInt(ResourceUtil.getString(localStringBuffer.toString(), "com.ffusion.beans.history.resources", paramLocale));
    }
    catch (Exception localException1)
    {
      return paramString;
    }
    String str2 = "COMMENT" + i;
    try
    {
      str1 = ResourceUtil.getString(str2, "com.ffusion.beans.history.resources", paramLocale);
    }
    catch (Exception localException2)
    {
      return paramString;
    }
    if ((str1 == null) || (str1.equals(""))) {
      return paramString;
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.HistoryReportAdapter
 * JD-Core Version:    0.7.0.1
 */