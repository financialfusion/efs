package com.ffusion.efs.adapters.profile;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
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
import com.ffusion.beans.util.UserUtil;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyList;
import com.ffusion.csil.beans.entitlements.EntitlementTypePropertyLists;
import com.ffusion.csil.core.Entitlements;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.util.CollatorUtil;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.AuditLogUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.Collator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class BusinessReportAdapter
  implements ProfileDefines
{
  static String akl = " select b.directory_id from business b where  b.directory_id=? and not exists ( select * from customer_directory cd2,  business_employee be2 where cd2.directory_id=be2.directory_id and  be2.business_id=b.directory_id and cd2.last_active_date>=?) and b.bank_id=? ";
  static String aki = " select b.directory_id from business b where not exists (select * from customer_directory cd2, business_employee be2 where cd2.directory_id=be2.directory_id and be2.business_id=b.directory_id and cd2.last_active_date >= ?) and b.bank_id=?";
  static String akn = " select b.directory_id, b.business_name, cd.cust_id,  c.first_name, c.last_name, c.work_phone, c.email_address,  MAX(temp1.last_active_date)  from business b, business_employee be, customer c, customer_directory cd, (select  be1.business_id business_id, cd1.last_active_date last_active_date from  customer_directory cd1, business_employee be1 where cd1.directory_id=be1.directory_id  and be1.business_id in ( ";
  static String akm = " ) ) temp1 where b.directory_id=cd.directory_id and b.directory_id=be.business_id and be.directory_id=c.directory_id and cd.account_status=1 and be.primary_user='1' and b.directory_id in ( ";
  static String akj = " ) and temp1.business_id=b.business_id group  by b.directory_id, b.business_name, cd.cust_id, c.first_name, c.last_name,  c.work_phone, c.email_address ";
  private static final String aks = "SELECT * FROM (    SELECT    t3.business_id business_id,    t3.low_business_name low_business_name,    t3.business_name business_name,    t3.cust_id cust_id,    a.MODULE module_id,    t3.first_name first_name,    t3.last_name last_name,    t3.user_name user_name,    MAX(a.LOG_DATE) last_used    FROM AUDIT_LOG a,    (       SELECT       t2.business_id,       t2.business_name,       t2.low_business_name,       c2.cust_id,       t2.first_name,       t2.last_name,       t2.user_name       FROM customer_directory c2,       (          SELECT          b1.business_id,          b1.business_name,          b1.low_business_name,          t1.first_name,          t1.last_name,          t1.user_name          FROM business b1,          (             SELECT             b2.business_id, c1.first_name, c1.last_name, c1.user_name             FROM business_employee b2, customer c1             WHERE b2.directory_id = c1.directory_id             AND b2.primary_user = ''1''          )          t1          WHERE b1.business_id = t1.business_id          AND b1.bank_id = ?          {0}       )       t2       WHERE c2.directory_id = t2.business_id    )    t3    WHERE a.BUSINESS_ID = t3.business_id    {1}    GROUP BY t3.business_id,    t3.low_business_name,    t3.business_name,    t3.cust_id,    a.MODULE,    t3.first_name,    t3.last_name,    t3.user_name ) t4 WHERE t4.last_used < ? {2}";
  private static final String akh = "business_id";
  private static final String ako = "low_business_name";
  private static final String akq = "business_name";
  private static final String akp = "cust_id";
  private static final String akf = "module_id";
  private static final String ake = "first_name";
  private static final String akg = "last_name";
  private static final String akk = "user_name";
  private static final String akr = "last_used";
  
  public IReportResult getBCReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws BCReportException
  {
    String str1 = "BusinessReportAdapter.getBCReportData";
    String str2 = null;
    String str3 = null;
    Object localObject1 = null;
    Object localObject2 = null;
    boolean bool = false;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    int i = paramSecureUser.getBankIDValue();
    int j = 0;
    int m = -1;
    int n = 0;
    Locale localLocale = paramSecureUser.getLocale();
    localReportResult = new ReportResult(localLocale);
    Connection localConnection = null;
    try
    {
      localConnection = DBUtil.getConnection(Profile.poolName, true, 2);
      Properties localProperties1 = paramReportCriteria.getSearchCriteria();
      if (localProperties1 == null)
      {
        localObject3 = "A search criteria object was not found within the given report criteria.  Without a search criteria object, a report cannot be run.";
        throw new Exception((String)localObject3);
      }
      Object localObject3 = paramReportCriteria.getSortCriteria();
      Properties localProperties2 = paramReportCriteria.getReportOptions();
      Object localObject4;
      if (localProperties2 == null)
      {
        localObject4 = "The report criteria used in a call to getBCReportData did not contain a valid report options object. This object is required for report retrieval.";
        throw new Exception((String)localObject4);
      }
      str2 = localProperties2.getProperty("REPORTTYPE");
      if (str2 == null)
      {
        localObject4 = "The report options contained within the Report Criteria used in a call to getBCReportData does not contain a report type.";
        throw new Exception((String)localObject4);
      }
      str3 = localProperties2.getProperty("MAXDISPLAYSIZE");
      if (str3 != null) {
        m = Integer.parseInt(str3);
      }
      try
      {
        n = jdMethod_for(paramSecureUser, paramReportCriteria, localLocale);
        jdMethod_for(paramReportCriteria, str2, localLocale);
        str5 = Profile.getSearchCriteria(paramReportCriteria.getSearchCriteria(), "Business", "AllBusinesses");
        if (str5.equals("AllBusinesses"))
        {
          str4 = ReportConsts.getText(10015, localLocale);
          bool = true;
        }
        else
        {
          str4 = Profile.getBusinessNameAndCustId(localConnection, str5, localLocale);
        }
        paramReportCriteria.setDisplayValue("Business", str4);
        int k = Integer.parseInt(Profile.getSearchCriteria(paramReportCriteria.getSearchCriteria(), "InactivePeriod", "30"));
        localObject4 = new Object[] { Integer.toString(k) };
        str6 = MessageFormat.format(ReportConsts.getText(10074, localLocale), (Object[])localObject4);
        paramReportCriteria.setDisplayValue("InactivePeriod", str6);
        localReportResult.init(paramReportCriteria);
        localObject5 = new ReportHeading(localLocale);
        ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
        localObject2 = str4;
        ((ReportHeading)localObject5).setLabel(localObject2);
        localReportResult.setHeading((ReportHeading)localObject5);
        int i1 = 0;
        if (str2.equals("Inactive Business Report")) {
          i1 = jdMethod_int(localReportResult, localLocale);
        } else if (str2.equals("Inactive Business Module Report")) {
          i1 = jdMethod_for(localReportResult, localLocale);
        }
        localReportDataDimensions.setNumColumns(i1);
        localReportDataDimensions.setNumRows(-1);
        localReportResult.setDataDimensions(localReportDataDimensions);
        if (str2.equals("Inactive Business Report")) {
          j = jdMethod_for(localReportResult, localConnection, str5, bool, n, i, k, m, localLocale, localProperties1, (ReportSortCriteria)localObject3);
        } else if (str2.equals("Inactive Business Module Report")) {
          j = jdMethod_for(paramSecureUser, localReportResult, localConnection, localProperties1, (ReportSortCriteria)localObject3, i, n, bool, str5, k, m, localLocale);
        }
      }
      catch (NumberFormatException localNumberFormatException)
      {
        Object localObject5 = "The search criteria specifying the id of business to query is missing or its format is not valid for a report of type: " + str2;
        throw new Exception((String)localObject5);
      }
      if ((m != -1) && (j == m))
      {
        HashMap localHashMap = new HashMap();
        localHashMap.put("TRUNCATED", new Integer(j));
        localReportResult.setProperties(localHashMap);
      }
      return localReportResult;
    }
    catch (Exception localException2)
    {
      localReportResult.setError(localException2);
      throw new BCReportException(localException2, str1, 29);
    }
    finally
    {
      DBUtil.returnConnection(Profile.poolName, localConnection);
      try
      {
        localReportResult.fini();
      }
      catch (Exception localException3) {}
    }
  }
  
  private void jdMethod_for(ReportCriteria paramReportCriteria, String paramString, Locale paramLocale)
  {
    if ("Inactive Business Module Report".equals(paramString))
    {
      String str1 = Profile.getSearchCriteria(paramReportCriteria.getSearchCriteria(), "Module", "AllModules");
      if ("AllModules".equals(str1))
      {
        paramReportCriteria.setDisplayValue("Module", ReportConsts.getText(10044, paramLocale));
      }
      else
      {
        int i = AuditLogUtil.getModuleIdFromModuleName(str1, paramLocale);
        String str2 = AuditLogUtil.getModuleNameFromModule(i, paramLocale);
        paramReportCriteria.setDisplayValue("Module", str2);
      }
    }
  }
  
  private int jdMethod_for(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, Locale paramLocale)
    throws ProfileException
  {
    int i = 0;
    String str1 = Profile.getSearchCriteria(paramReportCriteria.getSearchCriteria(), "Affiliate Bank", "AllAffiliateBanks");
    String str2 = null;
    if ("AllAffiliateBanks".equals(str1))
    {
      str2 = ReportConsts.getText(10048, paramLocale);
    }
    else
    {
      i = Integer.parseInt(str1);
      AffiliateBank localAffiliateBank = AffiliateBankAdapter.getAffiliateBankByID(paramSecureUser, i);
      if (localAffiliateBank != null) {
        str2 = localAffiliateBank.getAffiliateBankName();
      }
    }
    if ((str2 != null) && (str2.length() > 0)) {
      paramReportCriteria.setDisplayValue("Affiliate Bank", str2);
    }
    return i;
  }
  
  private static int jdMethod_for(ReportResult paramReportResult, ResultSet paramResultSet, int paramInt)
    throws Exception
  {
    String str1 = "BusinessReportAdapter.populateReportFromResultSet";
    Object localObject = null;
    Locale localLocale = paramReportResult.getLocale();
    ReportRows localReportRows = new ReportRows(localLocale);
    int i = 0;
    int j = paramInt;
    String str2 = null;
    int k = 0;
    Timestamp localTimestamp = null;
    Calendar localCalendar = Calendar.getInstance(localLocale);
    com.ffusion.beans.DateTime localDateTime = new com.ffusion.beans.DateTime(localLocale);
    try
    {
      while (paramResultSet.next())
      {
        ReportRow localReportRow = new ReportRow(localLocale);
        if (j % 2 == 1) {
          localReportRow.set("CELLBACKGROUND", "reportDataShaded");
        }
        ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
        ReportDataItem localReportDataItem = null;
        Profile.addItem(paramResultSet.getString(2), localReportDataItems, localReportDataItem);
        Profile.addItem(paramResultSet.getString(3), localReportDataItems, localReportDataItem);
        localTimestamp = paramResultSet.getTimestamp(8);
        if (localTimestamp != null)
        {
          localDateTime = new com.ffusion.beans.DateTime(localTimestamp, localLocale);
          str2 = String.valueOf(-1 * (int)localDateTime.getDiff(localCalendar, 3));
        }
        else
        {
          str2 = " ";
        }
        Profile.addItem(str2, localReportDataItems, localReportDataItem);
        str2 = UserUtil.getFullName(paramResultSet.getString(4), paramResultSet.getString(5), localLocale);
        Profile.addItem(str2, localReportDataItems, localReportDataItem);
        Profile.addItem(paramResultSet.getString(6), localReportDataItems, localReportDataItem);
        Profile.addItem(paramResultSet.getString(7), localReportDataItems, localReportDataItem);
        localReportRow.setDataItems(localReportDataItems);
        paramReportResult.addRow(localReportRow);
        i++;
        j++;
      }
    }
    catch (Exception localException)
    {
      throw new BCReportException(localException, str1, 29);
    }
    return i;
  }
  
  private int jdMethod_int(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    ArrayList localArrayList1 = new ArrayList();
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(14);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(2601, paramLocale));
    localReportColumn.setLabels(localArrayList2);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(2602, paramLocale));
    localReportColumn.setLabels(localArrayList2);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(2603, paramLocale));
    localReportColumn.setLabels(localArrayList2);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(2604, paramLocale));
    localReportColumn.setLabels(localArrayList2);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(2605, paramLocale));
    localReportColumn.setLabels(localArrayList2);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(2606, paramLocale));
    localReportColumn.setLabels(localArrayList2);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    return paramReportResult.getColumns().size();
  }
  
  private int jdMethod_for(ReportResult paramReportResult, Locale paramLocale)
    throws RptException
  {
    ReportColumn localReportColumn = new ReportColumn(paramLocale);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(14);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(2601, paramLocale));
    localReportColumn.setLabels(localArrayList);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(2602, paramLocale));
    localReportColumn.setLabels(localArrayList);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(2607, paramLocale));
    localReportColumn.setLabels(localArrayList);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(2603, paramLocale));
    localReportColumn.setLabels(localArrayList);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(2604, paramLocale));
    localReportColumn.setLabels(localArrayList);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    localArrayList = new ArrayList();
    localArrayList.add(ReportConsts.getColumnName(2608, paramLocale));
    localReportColumn.setLabels(localArrayList);
    paramReportResult.addColumn((ReportColumn)localReportColumn.clone());
    return paramReportResult.getColumns().size();
  }
  
  private static String jdMethod_for(ReportSortCriteria paramReportSortCriteria, String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = "ASC";
    String str2 = "BUSINESS_NAME";
    String str3 = null;
    if ((paramReportSortCriteria != null) && (paramReportSortCriteria.size() > 0))
    {
      paramReportSortCriteria.setSortedBy("ORDINAL");
      Iterator localIterator = paramReportSortCriteria.iterator();
      while (localIterator.hasNext())
      {
        ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        str1 = localReportSortCriterion.getAsc() ? "ASC" : "DESC";
        str2 = localReportSortCriterion.getName();
        str3 = null;
        if (str2.equals("Business"))
        {
          str3 = "business_name";
        }
        else if (str2.equals("Customer Id"))
        {
          str3 = "cust_id";
        }
        else if (str2.equals("Inactive for"))
        {
          if (paramString.equals("Inactive Business Report")) {
            str3 = "max(temp1.last_active_date)";
          } else if (paramString.equals("Inactive Business Module Report")) {
            str3 = "max(temp1.log_date)";
          }
        }
        else
        {
          if (!str2.equals("Module")) {
            continue;
          }
          str3 = "MODULE";
        }
        localStringBuffer.append(str3);
        localStringBuffer.append(" ");
        localStringBuffer.append(str1);
        if (localIterator.hasNext()) {
          localStringBuffer.append(", ");
        }
      }
    }
    else
    {
      str3 = "business_name";
      localStringBuffer.append(str3);
      localStringBuffer.append(" ");
      localStringBuffer.append(str1);
    }
    return localStringBuffer.toString();
  }
  
  private static int jdMethod_for(ReportResult paramReportResult, Connection paramConnection, String paramString, boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, Locale paramLocale, Properties paramProperties, ReportSortCriteria paramReportSortCriteria)
    throws Exception
  {
    String str1 = "BusinessReportAdapter.getInactiveBusinessResult";
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Object localObject4 = null;
    int i = -1;
    int k = 1;
    StringBuffer localStringBuffer1 = null;
    int j;
    try
    {
      Calendar localCalendar = null;
      StringBuffer localStringBuffer2 = new StringBuffer();
      ArrayList localArrayList = new ArrayList();
      if (!paramBoolean)
      {
        i = Integer.parseInt(paramString);
        localStringBuffer1 = new StringBuffer(akl);
      }
      else
      {
        localStringBuffer1 = new StringBuffer(aki);
      }
      if (paramInt1 != 0)
      {
        localStringBuffer1.append(" and ");
        localStringBuffer1.append("b.affiliate_bank_id");
        localStringBuffer1.append(" = ");
        localStringBuffer1.append(paramInt1);
      }
      localPreparedStatement1 = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
      if (!paramBoolean) {
        k = Profile.setStatementInt(localPreparedStatement1, k, i, false);
      }
      localCalendar = Calendar.getInstance(paramLocale);
      localCalendar.add(5, -1 * paramInt3);
      k = Profile.setStatementDate(localPreparedStatement1, k, new java.sql.Date(localCalendar.getTime().getTime()), false);
      if (paramInt4 != -1) {
        localPreparedStatement1.setMaxRows(paramInt4);
      }
      k = Profile.setStatementString(localPreparedStatement1, k, String.valueOf(paramInt2), "bank_id", false);
      localResultSet1 = localPreparedStatement1.executeQuery();
      for (j = 0; localResultSet1.next(); j++)
      {
        i = localResultSet1.getInt(1);
        if (j == 0)
        {
          localStringBuffer2.append(i);
        }
        else if (j < 1000)
        {
          localStringBuffer2.append("," + i);
        }
        else
        {
          localStringBuffer2.append("," + i);
          j = -1;
          localArrayList.add(localStringBuffer2.toString());
          localStringBuffer2 = new StringBuffer();
        }
      }
      if (localStringBuffer2.length() > 0)
      {
        localArrayList.add(localStringBuffer2.toString());
        localStringBuffer2 = new StringBuffer();
      }
      localStringBuffer1.setLength(0);
      j = 0;
      String str2 = jdMethod_for(paramReportSortCriteria, "Inactive Business Report");
      for (int m = 0; m < localArrayList.size(); m++)
      {
        localStringBuffer1.append(akn);
        localStringBuffer1.append((String)localArrayList.get(m) + akm);
        localStringBuffer1.append((String)localArrayList.get(m) + akj);
        if ((str2 != null) && (str2.length() != 0))
        {
          localStringBuffer1.append(" ORDER BY ");
          localStringBuffer1.append(str2);
        }
        try
        {
          localPreparedStatement2 = DBUtil.prepareStatement(paramConnection, localStringBuffer1.toString());
          localResultSet2 = localPreparedStatement2.executeQuery();
          localStringBuffer1.setLength(0);
          j += jdMethod_for(paramReportResult, localResultSet2, j);
        }
        catch (Exception localException2)
        {
          paramReportResult.setError(localException2);
          throw new BCReportException(localException2, str1, 29);
        }
        finally {}
      }
    }
    catch (Exception localException1)
    {
      paramReportResult.setError(localException1);
      throw new BCReportException(localException1, str1, 29);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement1, localResultSet1);
    }
    return j;
  }
  
  private static int jdMethod_for(SecureUser paramSecureUser, ReportResult paramReportResult, Connection paramConnection, Properties paramProperties, ReportSortCriteria paramReportSortCriteria, int paramInt1, int paramInt2, boolean paramBoolean, String paramString, int paramInt3, int paramInt4, Locale paramLocale)
    throws Exception
  {
    int i = 0;
    String str = null;
    str = jdMethod_for(paramProperties, paramReportSortCriteria, paramString, paramBoolean, paramInt2, paramLocale);
    i = jdMethod_for(paramSecureUser, paramReportResult, paramConnection, str, paramProperties, paramReportSortCriteria, paramInt1, paramInt2, paramBoolean, paramString, paramInt4, paramLocale);
    return i;
  }
  
  private static String jdMethod_for(Properties paramProperties, ReportSortCriteria paramReportSortCriteria, String paramString, boolean paramBoolean, int paramInt, Locale paramLocale)
    throws CSILException, IllegalArgumentException
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    str2 = jdMethod_for(paramBoolean, paramInt);
    str3 = jdMethod_for(paramProperties, paramLocale);
    str4 = jdMethod_for(paramReportSortCriteria);
    str1 = MessageFormat.format("SELECT * FROM (    SELECT    t3.business_id business_id,    t3.low_business_name low_business_name,    t3.business_name business_name,    t3.cust_id cust_id,    a.MODULE module_id,    t3.first_name first_name,    t3.last_name last_name,    t3.user_name user_name,    MAX(a.LOG_DATE) last_used    FROM AUDIT_LOG a,    (       SELECT       t2.business_id,       t2.business_name,       t2.low_business_name,       c2.cust_id,       t2.first_name,       t2.last_name,       t2.user_name       FROM customer_directory c2,       (          SELECT          b1.business_id,          b1.business_name,          b1.low_business_name,          t1.first_name,          t1.last_name,          t1.user_name          FROM business b1,          (             SELECT             b2.business_id, c1.first_name, c1.last_name, c1.user_name             FROM business_employee b2, customer c1             WHERE b2.directory_id = c1.directory_id             AND b2.primary_user = ''1''          )          t1          WHERE b1.business_id = t1.business_id          AND b1.bank_id = ?          {0}       )       t2       WHERE c2.directory_id = t2.business_id    )    t3    WHERE a.BUSINESS_ID = t3.business_id    {1}    GROUP BY t3.business_id,    t3.low_business_name,    t3.business_name,    t3.cust_id,    a.MODULE,    t3.first_name,    t3.last_name,    t3.user_name ) t4 WHERE t4.last_used < ? {2}", new Object[] { str2, str3, str4 });
    return str1;
  }
  
  private static String jdMethod_for(boolean paramBoolean, int paramInt)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (!paramBoolean) {
      localStringBuffer.append(" AND b1.business_id = ? ");
    }
    if (paramInt != 0) {
      localStringBuffer.append(" AND b1.affiliate_bank_id = ? ");
    }
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_for(Properties paramProperties, Locale paramLocale)
    throws CSILException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str = Profile.getSearchCriteria(paramProperties, "Module", "AllModules");
    Object localObject = null;
    if ("AllModules".equals(str))
    {
      localObject = Q();
    }
    else
    {
      localObject = new ArrayList();
      ((List)localObject).add(String.valueOf(AuditLogUtil.getModuleIdFromModuleName(str, paramLocale)));
    }
    localStringBuffer.append(" AND a.MODULE IN ( ");
    localStringBuffer.append(jdMethod_for((List)localObject));
    localStringBuffer.append(" ) ");
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_for(List paramList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    char c = ' ';
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      localStringBuffer.append(c);
      localStringBuffer.append((String)localIterator.next());
      localStringBuffer.append(' ');
      c = ',';
    }
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_for(ReportSortCriteria paramReportSortCriteria)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramReportSortCriteria == null)
    {
      localStringBuffer.append(jdMethod_for(localStringBuffer.toString(), "low_business_name", true));
      localStringBuffer.append(jdMethod_for(localStringBuffer.toString(), "module_id", true));
    }
    else
    {
      String str = paramReportSortCriteria.getSortedBy();
      paramReportSortCriteria.setSortedBy("ORDINAL");
      Iterator localIterator = paramReportSortCriteria.iterator();
      while (localIterator.hasNext())
      {
        ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        if ("Business".equals(localReportSortCriterion.getName())) {
          localStringBuffer.append(jdMethod_for(localStringBuffer.toString(), "low_business_name", localReportSortCriterion.getAsc()));
        } else if ("Customer Id".equals(localReportSortCriterion.getName())) {
          localStringBuffer.append(jdMethod_for(localStringBuffer.toString(), "cust_id", localReportSortCriterion.getAsc()));
        } else if ("Inactive for".equals(localReportSortCriterion.getName())) {
          localStringBuffer.append(jdMethod_for(localStringBuffer.toString(), "last_used", !localReportSortCriterion.getAsc()));
        } else if ("Module".equals(localReportSortCriterion.getName())) {
          localStringBuffer.append(jdMethod_for(localStringBuffer.toString(), "module_id", localReportSortCriterion.getAsc()));
        }
      }
      paramReportSortCriteria.setSortedBy(str);
    }
    return localStringBuffer.toString();
  }
  
  private static String jdMethod_for(String paramString1, String paramString2, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString1.trim().toLowerCase().startsWith("order by")) {
      localStringBuffer.append(" , ");
    } else {
      localStringBuffer.append(" ORDER BY ");
    }
    localStringBuffer.append(' ');
    localStringBuffer.append(paramString2);
    localStringBuffer.append(' ');
    if (paramBoolean) {
      localStringBuffer.append(" ASC ");
    } else {
      localStringBuffer.append(" DESC ");
    }
    return localStringBuffer.toString();
  }
  
  private static int jdMethod_for(SecureUser paramSecureUser, ReportResult paramReportResult, Connection paramConnection, String paramString1, Properties paramProperties, ReportSortCriteria paramReportSortCriteria, int paramInt1, int paramInt2, boolean paramBoolean, String paramString2, int paramInt3, Locale paramLocale)
    throws Exception
  {
    int i = 0;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      com.ffusion.util.beans.DateTime localDateTime1 = new com.ffusion.util.beans.DateTime(paramLocale);
      jdMethod_for(localDateTime1);
      com.ffusion.util.beans.DateTime localDateTime2 = jdMethod_for(paramProperties, localDateTime1, paramLocale);
      localPreparedStatement = DBUtil.prepareStatement(paramConnection, paramString1);
      int j = 1;
      if ((paramInt3 > 0) && (!jdMethod_int(paramReportSortCriteria, "Module"))) {
        localPreparedStatement.setMaxRows(paramInt3);
      }
      localPreparedStatement.setString(j++, Integer.toString(paramInt1));
      j = jdMethod_for(localPreparedStatement, j, paramInt2, paramBoolean, paramString2);
      j = jdMethod_for(localPreparedStatement, j, paramProperties, paramReportSortCriteria);
      localPreparedStatement.setTimestamp(j++, new Timestamp(localDateTime2.getTime().getTime()));
      localResultSet = DBUtil.executeQuery(localPreparedStatement, paramString1);
      if (jdMethod_int(paramReportSortCriteria, "Module")) {
        i = jdMethod_for(paramSecureUser, paramReportResult, localResultSet, paramProperties, paramReportSortCriteria, localDateTime1, paramInt3, paramLocale);
      } else {
        i = jdMethod_for(paramSecureUser, paramReportResult, localResultSet, paramProperties, localDateTime1, paramLocale);
      }
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    return i;
  }
  
  private static com.ffusion.util.beans.DateTime jdMethod_for(Properties paramProperties, com.ffusion.util.beans.DateTime paramDateTime, Locale paramLocale)
  {
    com.ffusion.util.beans.DateTime localDateTime = new com.ffusion.util.beans.DateTime(paramDateTime, paramLocale);
    jdMethod_for(localDateTime);
    int i = 0;
    try
    {
      i = Integer.parseInt(paramProperties.getProperty("InactivePeriod"));
      i *= -1;
    }
    catch (NumberFormatException localNumberFormatException)
    {
      i = 0;
    }
    localDateTime.add(6, i);
    return localDateTime;
  }
  
  private static void jdMethod_for(com.ffusion.util.beans.DateTime paramDateTime)
  {
    com.ffusion.util.beans.DateTime localDateTime = paramDateTime;
    localDateTime.clear(10);
    localDateTime.clear(11);
    localDateTime.clear(12);
    localDateTime.clear(13);
    localDateTime.clear(14);
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt1, int paramInt2, boolean paramBoolean, String paramString)
    throws SQLException
  {
    int i = paramInt1;
    if (!paramBoolean)
    {
      int j = -1;
      try
      {
        j = Integer.parseInt(paramString);
      }
      catch (NumberFormatException localNumberFormatException)
      {
        j = -1;
      }
      paramPreparedStatement.setInt(i++, j);
    }
    if (paramInt2 != 0) {
      paramPreparedStatement.setInt(i++, paramInt2);
    }
    return i;
  }
  
  private static int jdMethod_for(PreparedStatement paramPreparedStatement, int paramInt, Properties paramProperties, ReportSortCriteria paramReportSortCriteria)
    throws SQLException
  {
    int i = paramInt;
    return i;
  }
  
  private static int jdMethod_for(SecureUser paramSecureUser, ReportResult paramReportResult, ResultSet paramResultSet, Properties paramProperties, ReportSortCriteria paramReportSortCriteria, com.ffusion.util.beans.DateTime paramDateTime, int paramInt, Locale paramLocale)
    throws CSILException, RptException, SQLException
  {
    int i = 0;
    Map localMap = P();
    b localb = new b(paramReportSortCriteria, paramLocale);
    ArrayList localArrayList = new ArrayList();
    while (paramResultSet.next())
    {
      a locala = jdMethod_for(paramResultSet, paramDateTime, paramLocale);
      if (jdMethod_for(paramSecureUser, localMap, locala)) {
        localArrayList.add(locala);
      }
    }
    Collections.sort(localArrayList, localb);
    i = jdMethod_for(paramReportResult, localArrayList, paramInt, paramLocale);
    return i;
  }
  
  private static int jdMethod_for(SecureUser paramSecureUser, ReportResult paramReportResult, ResultSet paramResultSet, Properties paramProperties, com.ffusion.util.beans.DateTime paramDateTime, Locale paramLocale)
    throws CSILException, RptException, SQLException
  {
    int i = 0;
    Map localMap = P();
    while (paramResultSet.next())
    {
      a locala = jdMethod_for(paramResultSet, paramDateTime, paramLocale);
      if (jdMethod_for(paramSecureUser, localMap, locala))
      {
        ReportRow localReportRow = new ReportRow(paramLocale);
        ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
        ReportDataItem localReportDataItem = null;
        Profile.addItem(locala.jdMethod_new(), localReportDataItems, localReportDataItem);
        Profile.addItem(locala.jdMethod_if(), localReportDataItems, localReportDataItem);
        Profile.addItem(locala.jdMethod_for(), localReportDataItems, localReportDataItem);
        Profile.addItem(Integer.toString(locala.a()), localReportDataItems, localReportDataItem);
        Profile.addItem(locala.jdMethod_byte(), localReportDataItems, localReportDataItem);
        Profile.addItem(locala.jdMethod_do(), localReportDataItems, localReportDataItem);
        localReportRow.setDataItems(localReportDataItems);
        paramReportResult.addRow(localReportRow);
        i++;
      }
    }
    return i;
  }
  
  private static a jdMethod_for(ResultSet paramResultSet, com.ffusion.util.beans.DateTime paramDateTime, Locale paramLocale)
    throws SQLException
  {
    a locala = new a(null);
    locala.a(paramResultSet.getInt("business_id"));
    locala.a(paramResultSet.getString("low_business_name"));
    locala.jdMethod_for(paramResultSet.getString("business_name"));
    locala.jdMethod_if(paramResultSet.getString("cust_id"));
    locala.jdMethod_new(AuditLogUtil.getModuleNameFromModule(paramResultSet.getInt("module_id"), paramLocale));
    locala.jdMethod_if(jdMethod_for(paramDateTime, jdMethod_new(paramResultSet, paramLocale)));
    locala.jdMethod_int(jdMethod_try(paramResultSet, paramLocale));
    locala.jdMethod_do(paramResultSet.getString("user_name"));
    return locala;
  }
  
  private static com.ffusion.util.beans.DateTime jdMethod_new(ResultSet paramResultSet, Locale paramLocale)
    throws SQLException
  {
    com.ffusion.util.beans.DateTime localDateTime = new com.ffusion.util.beans.DateTime(paramLocale);
    localDateTime.setTime(paramResultSet.getTimestamp("last_used"));
    jdMethod_for(localDateTime);
    return localDateTime;
  }
  
  private static int jdMethod_for(com.ffusion.util.beans.DateTime paramDateTime1, com.ffusion.util.beans.DateTime paramDateTime2)
  {
    int i = 0;
    i = (int)paramDateTime1.getDiff(paramDateTime2, 3);
    return i;
  }
  
  private static String jdMethod_try(ResultSet paramResultSet, Locale paramLocale)
    throws SQLException
  {
    String str1 = "";
    String str2 = paramResultSet.getString("first_name");
    String str3 = paramResultSet.getString("last_name");
    str1 = UserUtil.getFullName(str2, str3, paramLocale);
    return str1;
  }
  
  private static int jdMethod_for(ReportResult paramReportResult, List paramList, int paramInt, Locale paramLocale)
    throws RptException
  {
    int i = 0;
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      a locala = (a)localIterator.next();
      ReportRow localReportRow = new ReportRow(paramLocale);
      ReportDataItems localReportDataItems = new ReportDataItems(paramLocale);
      ReportDataItem localReportDataItem = null;
      Profile.addItem(locala.jdMethod_new(), localReportDataItems, localReportDataItem);
      Profile.addItem(locala.jdMethod_if(), localReportDataItems, localReportDataItem);
      Profile.addItem(locala.jdMethod_for(), localReportDataItems, localReportDataItem);
      Profile.addItem(Integer.toString(locala.a()), localReportDataItems, localReportDataItem);
      Profile.addItem(locala.jdMethod_byte(), localReportDataItems, localReportDataItem);
      Profile.addItem(locala.jdMethod_do(), localReportDataItems, localReportDataItem);
      localReportRow.setDataItems(localReportDataItems);
      paramReportResult.addRow(localReportRow);
      i++;
      if ((paramInt > 0) && (i >= paramInt)) {
        break;
      }
    }
    return i;
  }
  
  private static boolean jdMethod_int(ReportSortCriteria paramReportSortCriteria, String paramString)
  {
    boolean bool = false;
    if (paramString != null)
    {
      Iterator localIterator = paramReportSortCriteria.iterator();
      while (localIterator.hasNext())
      {
        ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        if (paramString.equals(localReportSortCriterion.getName()))
        {
          bool = true;
          break;
        }
      }
    }
    return bool;
  }
  
  private static Map P()
    throws CSILException
  {
    HashMap localHashMap = new HashMap();
    try
    {
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties("module", "yes");
      Iterator localIterator = localEntitlementTypePropertyLists.iterator();
      while (localIterator.hasNext())
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
        localHashMap.put(localEntitlementTypePropertyList.getPropertyValue("module_name", 0), localEntitlementTypePropertyList.getOperationName());
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.FINE, "Inactive Business Module Report: Failed to retrieve module information due to an error in the Entitlements system.");
      throw localCSILException;
    }
    return localHashMap;
  }
  
  private static List Q()
    throws CSILException
  {
    HashSet localHashSet = new HashSet();
    try
    {
      EntitlementTypePropertyLists localEntitlementTypePropertyLists = Entitlements.getEntitlementTypesWithProperties("module", "yes");
      Iterator localIterator = localEntitlementTypePropertyLists.iterator();
      while (localIterator.hasNext())
      {
        EntitlementTypePropertyList localEntitlementTypePropertyList = (EntitlementTypePropertyList)localIterator.next();
        localHashSet.add(Integer.toString(AuditLogUtil.getModuleIdFromModuleName(localEntitlementTypePropertyList.getPropertyValue("module_name", 0))));
      }
    }
    catch (CSILException localCSILException)
    {
      DebugLog.log(Level.FINE, "Inactive Business Module Report: Failed to retrieve module ID information due to an error in the Entitlements system.");
      throw localCSILException;
    }
    return new ArrayList(localHashSet);
  }
  
  private static boolean jdMethod_for(SecureUser paramSecureUser, Map paramMap, a parama)
  {
    boolean bool = true;
    return bool;
  }
  
  private static class b
    implements Comparator
  {
    private ReportSortCriteria a;
    private Locale jdField_if;
    private Collator jdField_do;
    
    public b(ReportSortCriteria paramReportSortCriteria, Locale paramLocale)
    {
      this.a = a(paramReportSortCriteria);
      this.jdField_if = (paramLocale == null ? Locale.getDefault() : paramLocale);
      this.jdField_do = CollatorUtil.getCollator(this.jdField_if);
    }
    
    public int compare(Object paramObject1, Object paramObject2)
    {
      int i = 0;
      BusinessReportAdapter.a locala1 = (BusinessReportAdapter.a)paramObject1;
      BusinessReportAdapter.a locala2 = (BusinessReportAdapter.a)paramObject2;
      Iterator localIterator = this.a.iterator();
      while ((i == 0) && (localIterator.hasNext()))
      {
        ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        if ("InactiveFor".equals(localReportSortCriterion.getName()))
        {
          i = a(locala1.a() - locala2.a(), localReportSortCriterion.getAsc());
        }
        else
        {
          String str1 = null;
          String str2 = null;
          if ("LowBusinessName".equals(localReportSortCriterion.getName()))
          {
            str1 = locala1.jdMethod_int();
            str2 = locala2.jdMethod_int();
          }
          else if ("CustomerID".equals(localReportSortCriterion.getName()))
          {
            str1 = locala1.jdField_if();
            str2 = locala2.jdField_if();
          }
          else if ("ModuleName".equals(localReportSortCriterion.getName()))
          {
            str1 = locala1.jdMethod_for();
            str2 = locala2.jdMethod_for();
          }
          else if ("PrimaryContactRealName".equals(localReportSortCriterion.getName()))
          {
            str1 = locala1.jdMethod_byte();
            str2 = locala2.jdMethod_byte();
          }
          else if ("PrimaryContactUserName".equals(localReportSortCriterion.getName()))
          {
            str1 = locala1.jdField_do();
            str2 = locala2.jdField_do();
          }
          i = a(a(str1, str2), localReportSortCriterion.getAsc());
        }
      }
      return i;
    }
    
    private static ReportSortCriteria a(ReportSortCriteria paramReportSortCriteria)
    {
      ReportSortCriteria localReportSortCriteria = new ReportSortCriteria();
      if ((paramReportSortCriteria != null) && (paramReportSortCriteria.size() > 0))
      {
        String str = paramReportSortCriteria.getSortedBy();
        paramReportSortCriteria.setSortedBy("ORDINAL");
        int i = 0;
        Iterator localIterator = paramReportSortCriteria.iterator();
        while (localIterator.hasNext())
        {
          ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localIterator.next();
          if ("Business".equals(localReportSortCriterion.getName())) {
            localReportSortCriteria.add(new ReportSortCriterion(i, "LowBusinessName", localReportSortCriterion.getAsc()));
          } else if ("Customer Id".equals(localReportSortCriterion.getName())) {
            localReportSortCriteria.add(new ReportSortCriterion(i, "CustomerID", localReportSortCriterion.getAsc()));
          } else if ("Inactive for".equals(localReportSortCriterion.getName())) {
            localReportSortCriteria.add(new ReportSortCriterion(i, "InactiveFor", localReportSortCriterion.getAsc()));
          } else if ("Module".equals(localReportSortCriterion.getName())) {
            localReportSortCriteria.add(new ReportSortCriterion(i, "ModuleName", localReportSortCriterion.getAsc()));
          }
          i++;
        }
        paramReportSortCriteria.setSortedBy(str);
      }
      return localReportSortCriteria;
    }
    
    private static int a(int paramInt, boolean paramBoolean)
    {
      int i = paramInt;
      if (!paramBoolean) {
        if (i < 0) {
          i = 1;
        } else if (i > 0) {
          i = -1;
        }
      }
      return i;
    }
    
    private int a(String paramString1, String paramString2)
    {
      if ((paramString1 == null) && (paramString2 == null)) {
        return 0;
      }
      if (paramString1 == null) {
        return -1;
      }
      if (paramString2 == null) {
        return 1;
      }
      String str1 = paramString1.replace('-', ' ');
      String str2 = paramString2.replace('-', ' ');
      StringTokenizer localStringTokenizer1 = new StringTokenizer(str1, " ");
      StringTokenizer localStringTokenizer2 = new StringTokenizer(str2, " ");
      while ((localStringTokenizer1.hasMoreTokens()) && (localStringTokenizer2.hasMoreTokens()))
      {
        String str3 = localStringTokenizer1.nextToken();
        String str4 = localStringTokenizer2.nextToken();
        int i = this.jdField_do.compare(str3, str4);
        if (i != 0) {
          return i;
        }
      }
      if ((!localStringTokenizer1.hasMoreTokens()) && (localStringTokenizer2.hasMoreTokens())) {
        return -1;
      }
      if ((localStringTokenizer1.hasMoreTokens()) && (!localStringTokenizer2.hasMoreTokens())) {
        return 1;
      }
      return 0;
    }
  }
  
  private static class a
  {
    public static final String jdField_for = "BusinessName";
    public static final String jdField_case = "LowBusinessName";
    public static final String jdField_goto = "CustomerID";
    public static final String jdField_char = "ModuleName";
    public static final String jdField_int = "InactiveFor";
    public static final String jdField_do = "PrimaryContactRealName";
    public static final String jdField_long = "PrimaryContactUserName";
    private String jdField_else;
    private String jdField_void;
    private String jdField_new;
    private String jdField_try;
    private String jdField_byte;
    private String jdField_if;
    private int a;
    private int jdField_null;
    
    private a() {}
    
    public void jdField_for(String paramString)
    {
      this.jdField_else = paramString;
    }
    
    public String jdField_new()
    {
      return this.jdField_else;
    }
    
    public void a(String paramString)
    {
      this.jdField_void = paramString;
    }
    
    public String jdField_int()
    {
      return this.jdField_void;
    }
    
    public void jdField_if(String paramString)
    {
      this.jdField_new = paramString;
    }
    
    public String jdField_if()
    {
      return this.jdField_new;
    }
    
    public void jdField_new(String paramString)
    {
      this.jdField_try = paramString;
    }
    
    public String jdField_for()
    {
      return this.jdField_try;
    }
    
    public void jdField_int(String paramString)
    {
      this.jdField_byte = paramString;
    }
    
    public String jdField_byte()
    {
      return this.jdField_byte;
    }
    
    public void jdField_do(String paramString)
    {
      this.jdField_if = paramString;
    }
    
    public String jdField_do()
    {
      return this.jdField_if;
    }
    
    public void jdField_if(int paramInt)
    {
      this.a = paramInt;
    }
    
    public int a()
    {
      return this.a;
    }
    
    public void a(int paramInt)
    {
      this.jdField_null = paramInt;
    }
    
    public int jdField_try()
    {
      return this.jdField_null;
    }
    
    a(BusinessReportAdapter.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.BusinessReportAdapter
 * JD-Core Version:    0.7.0.1
 */