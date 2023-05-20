package com.ffusion.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.ReportIdentifications;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.reporting.adapter.IReportingAdapter;
import com.ffusion.util.Strings;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.db.PoolException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class ReportingAdapter
  implements IReportingAdapter
{
  public static final String REPORTINGADAPTER_SETTINGS = "REPORTINGADAPTER_SETTINGS";
  public static final String DB_PROPERTIES = "DB_PROPERTIES";
  public static final String INIT_FILE = "INIT_FILE";
  protected static final int MAX_CRITERIA_VALUE_SIZE = 255;
  private String jdField_byte;
  private static final String jdField_int = "rep_id";
  private static final String d = "insert into RptReport( reportID, reportName, description, userID, userType ) values( ?, ?, ?, ?, ? )";
  private static final String jdField_try = "update RptReport set reportName = ?, description = ? where reportID = ?";
  private static final String jdField_do = "insert into RptSearchCriteria( reportID, ordinal, criteriaName, criteriaValue ) values( ?, ?, ?, ? )";
  private static final String b = "insert into RptSortCriteria( reportID, ordinal, criteriaName, ascDesc ) values( ?, ?, ?, ? )";
  private static final String jdField_char = "insert into RptOptions( reportID, optionName, optionValue ) values( ?, ?, ? )";
  private static final String jdField_case = "Select criteriaName, criteriaValue, ordinal from RptSearchCriteria where reportID = ? ORDER BY criteriaName, ordinal";
  private static final String jdField_for = "select ordinal, criteriaName, ascDesc from RptSortCriteria where reportID = ?";
  private static final String jdField_goto = "select optionName, optionValue from RptOptions where reportID = ?";
  private static final String jdField_null = "delete from RptSearchCriteria where reportID = ?";
  private static final String e = "delete from RptSortCriteria where reportID = ?";
  private static final String jdField_else = "delete from RptOptions where reportID = ?";
  private static final String jdField_new = "select reportID, reportName, description from RptReport where userID = ? and userType = ?";
  private static final String c = "select reportID, description from RptReport where userID = ? and userType = ? and reportName = ?";
  private static final String jdField_void = "select reportID, reportName, description from RptReport where reportID = ?";
  private static final String jdField_long = "delete from RptReport where reportID = ?";
  private static final String jdField_if = "delete from RptReport where userID = ? and userType = ?";
  private static final String a = "select userID from RptReport where reportID = ? and userID = ? and userType = ?";
  
  public void initialize(HashMap paramHashMap)
    throws RptException
  {
    if (paramHashMap == null) {
      throw new RptException(1, "No reporting adapter settings in reporting configuration");
    }
    Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
    if (localProperties == null) {
      throw new RptException(1, "No database properties for reporting adapter in reporting configuration");
    }
    try
    {
      this.jdField_byte = ConnectionPool.init(localProperties);
    }
    catch (Exception localException)
    {
      throw new RptException(1, localException);
    }
  }
  
  public ReportIdentifications getReports(SecureUser paramSecureUser, HashMap paramHashMap)
    throws RptException
  {
    if (paramSecureUser == null) {
      throw new RptException(15);
    }
    Connection localConnection = a();
    ReportIdentifications localReportIdentifications1 = new ReportIdentifications();
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select reportID, reportName, description from RptReport where userID = ? and userType = ?");
      localPreparedStatement.setInt(1, paramSecureUser.getProfileID());
      localPreparedStatement.setInt(2, paramSecureUser.getUserType());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select reportID, reportName, description from RptReport where userID = ? and userType = ?");
      while (localResultSet.next()) {
        localReportIdentifications1.create(localResultSet.getInt(1), localResultSet.getString(2), localResultSet.getString(3));
      }
      ReportIdentifications localReportIdentifications2 = localReportIdentifications1;
      return localReportIdentifications2;
    }
    catch (Exception localException)
    {
      throw new RptException(5, localException);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_byte, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public ReportIdentification getReport(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws RptException
  {
    if (paramSecureUser == null) {
      throw new RptException(15);
    }
    if (paramString == null) {
      throw new RptException(8);
    }
    Connection localConnection = a();
    ReportIdentification localReportIdentification1 = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select reportID, description from RptReport where userID = ? and userType = ? and reportName = ?");
      localPreparedStatement.setInt(1, paramSecureUser.getProfileID());
      localPreparedStatement.setInt(2, paramSecureUser.getUserType());
      localPreparedStatement.setString(3, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select reportID, description from RptReport where userID = ? and userType = ? and reportName = ?");
      if (localResultSet.next()) {
        localReportIdentification1 = new ReportIdentification(localResultSet.getInt(1), paramString, localResultSet.getString(2));
      }
      ReportIdentification localReportIdentification2 = localReportIdentification1;
      return localReportIdentification2;
    }
    catch (Exception localException)
    {
      throw new RptException(5, localException);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_byte, localConnection, localPreparedStatement, localResultSet);
    }
  }
  
  public Report getReportCriteria(SecureUser paramSecureUser, ReportIdentification paramReportIdentification)
    throws RptException
  {
    if (paramSecureUser == null) {
      throw new RptException(15);
    }
    if (paramReportIdentification == null) {
      throw new RptException(3);
    }
    Connection localConnection = a();
    if (!a(localConnection, paramReportIdentification))
    {
      try
      {
        DBUtil.returnConnection(this.jdField_byte, localConnection);
      }
      catch (Exception localException1) {}
      throw new RptException(3);
    }
    if (!a(localConnection, paramSecureUser, paramReportIdentification))
    {
      try
      {
        DBUtil.returnConnection(this.jdField_byte, localConnection);
      }
      catch (Exception localException2) {}
      throw new RptException(6);
    }
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    Properties localProperties1 = new Properties();
    ReportSortCriteria localReportSortCriteria = new ReportSortCriteria();
    Properties localProperties2 = new Properties();
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "Select criteriaName, criteriaValue, ordinal from RptSearchCriteria where reportID = ? ORDER BY criteriaName, ordinal");
      localPreparedStatement.setInt(1, paramReportIdentification.getReportID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "Select criteriaName, criteriaValue, ordinal from RptSearchCriteria where reportID = ? ORDER BY criteriaName, ordinal");
      StringBuffer localStringBuffer = null;
      Object localObject1 = null;
      while (localResultSet.next())
      {
        String str1 = localResultSet.getString(1);
        String str2 = localResultSet.getString(2) == null ? "" : localResultSet.getString(2);
        if (localObject1 == null)
        {
          localStringBuffer = new StringBuffer(str2);
          localObject1 = str1;
        }
        else if (str1.equals(localObject1))
        {
          localStringBuffer.append(str2);
        }
        else
        {
          localProperties1.setProperty(localObject1, localStringBuffer.toString().trim());
          localStringBuffer = new StringBuffer(str2);
          localObject1 = str1;
        }
      }
      if (localObject1 != null) {
        localProperties1.setProperty(localObject1, localStringBuffer.toString().trim());
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    catch (Exception localException3)
    {
      DBUtil.closeAll(this.jdField_byte, localConnection, localPreparedStatement, localResultSet);
      throw new RptException(5, localException3);
    }
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select ordinal, criteriaName, ascDesc from RptSortCriteria where reportID = ?");
      localPreparedStatement.setInt(1, paramReportIdentification.getReportID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select ordinal, criteriaName, ascDesc from RptSortCriteria where reportID = ?");
      while (localResultSet.next()) {
        localReportSortCriteria.create(localResultSet.getInt(1), localResultSet.getString(2), localResultSet.getBoolean(3));
      }
      DBUtil.closeAll(localPreparedStatement, localResultSet);
    }
    catch (Exception localException4)
    {
      DBUtil.closeAll(this.jdField_byte, localConnection, localPreparedStatement, localResultSet);
      throw new RptException(5, localException4);
    }
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select optionName, optionValue from RptOptions where reportID = ?");
      localPreparedStatement.setInt(1, paramReportIdentification.getReportID());
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select optionName, optionValue from RptOptions where reportID = ?");
      while (localResultSet.next()) {
        if (localResultSet.getString(2) != null) {
          localProperties2.setProperty(localResultSet.getString(1), localResultSet.getString(2).trim());
        } else {
          localProperties2.setProperty(localResultSet.getString(1), new String());
        }
      }
    }
    catch (Exception localException5)
    {
      throw new RptException(5, localException5);
    }
    finally
    {
      DBUtil.closeAll(this.jdField_byte, localConnection, localPreparedStatement, localResultSet);
    }
    ReportCriteria localReportCriteria = new ReportCriteria(localProperties1, localReportSortCriteria, localProperties2);
    localReportCriteria.setLocale(paramSecureUser.getLocale());
    return new Report(paramReportIdentification, localReportCriteria, null);
  }
  
  public void addReport(SecureUser paramSecureUser, Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    if (paramSecureUser == null) {
      throw new RptException(15);
    }
    if (paramReport == null) {
      throw new RptException(3);
    }
    if (paramReport.getReportID() == null) {
      throw new RptException(4);
    }
    Connection localConnection = a(false);
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into RptReport( reportID, reportName, description, userID, userType ) values( ?, ?, ?, ?, ? )");
      paramReport.getReportID().setReportID(DBUtil.getNextId(localConnection, ConnectionPool.getDBType(this.jdField_byte), "rep_id"));
      localPreparedStatement.setInt(1, paramReport.getReportID().getReportID());
      localPreparedStatement.setString(2, paramReport.getReportID().getReportName());
      localPreparedStatement.setString(3, paramReport.getReportID().getDescription());
      localPreparedStatement.setInt(4, paramSecureUser.getProfileID());
      localPreparedStatement.setInt(5, paramSecureUser.getUserType());
      DBUtil.executeUpdate(localPreparedStatement, "insert into RptReport( reportID, reportName, description, userID, userType ) values( ?, ?, ?, ?, ? )");
      a(localConnection, paramReport);
      jdField_int(localConnection, paramReport);
      a(localConnection, paramReport, paramHashMap);
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement);
      return;
    }
    catch (Exception localException2)
    {
      DBUtil.rollback(localConnection);
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      throw new RptException(5, localException2);
    }
    finally
    {
      try
      {
        DBUtil.returnConnection(this.jdField_byte, localConnection);
      }
      catch (Exception localException3) {}
    }
  }
  
  public void modifyReport(SecureUser paramSecureUser, Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    if (paramSecureUser == null) {
      throw new RptException(15);
    }
    if (paramReport == null) {
      throw new RptException(3);
    }
    Connection localConnection = a(false);
    if (!a(localConnection, paramReport.getReportID()))
    {
      try
      {
        DBUtil.returnConnection(this.jdField_byte, localConnection);
      }
      catch (Exception localException1) {}
      throw new RptException(3);
    }
    if (!a(localConnection, paramSecureUser, paramReport.getReportID()))
    {
      try
      {
        DBUtil.returnConnection(this.jdField_byte, localConnection);
      }
      catch (Exception localException2) {}
      throw new RptException(6);
    }
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update RptReport set reportName = ?, description = ? where reportID = ?");
      localPreparedStatement.setString(1, paramReport.getReportID().getReportName());
      localPreparedStatement.setString(2, paramReport.getReportID().getDescription());
      localPreparedStatement.setInt(3, paramReport.getReportID().getReportID());
      DBUtil.executeUpdate(localPreparedStatement, "update RptReport set reportName = ?, description = ? where reportID = ?");
      jdField_if(localConnection, paramReport);
      jdField_do(localConnection, paramReport);
      jdField_for(localConnection, paramReport);
      a(localConnection, paramReport);
      jdField_int(localConnection, paramReport);
      a(localConnection, paramReport, paramHashMap);
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement);
      return;
    }
    catch (Exception localException4)
    {
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      DBUtil.rollback(localConnection);
      throw new RptException(5, localException4);
    }
    finally
    {
      try
      {
        DBUtil.returnConnection(this.jdField_byte, localConnection);
      }
      catch (Exception localException5) {}
    }
  }
  
  public void deleteReport(SecureUser paramSecureUser, ReportIdentification paramReportIdentification, HashMap paramHashMap)
    throws RptException
  {
    if (paramSecureUser == null) {
      throw new RptException(15);
    }
    if (paramReportIdentification == null) {
      throw new RptException(3);
    }
    Connection localConnection = a();
    if (!a(localConnection, paramReportIdentification))
    {
      try
      {
        DBUtil.returnConnection(this.jdField_byte, localConnection);
      }
      catch (Exception localException1) {}
      throw new RptException(3);
    }
    if (!a(localConnection, paramSecureUser, paramReportIdentification))
    {
      try
      {
        DBUtil.returnConnection(this.jdField_byte, localConnection);
      }
      catch (Exception localException2) {}
      throw new RptException(6);
    }
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from RptReport where reportID = ?");
      localPreparedStatement.setInt(1, paramReportIdentification.getReportID());
      DBUtil.executeUpdate(localPreparedStatement, "delete from RptReport where reportID = ?");
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement);
      return;
    }
    catch (Exception localException4)
    {
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      DBUtil.rollback(localConnection);
      throw new RptException(5, localException4);
    }
    finally
    {
      try
      {
        DBUtil.returnConnection(this.jdField_byte, localConnection);
      }
      catch (Exception localException5) {}
    }
  }
  
  public void deleteUser(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws RptException
  {
    Connection localConnection = a();
    PreparedStatement localPreparedStatement = null;
    try
    {
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from RptReport where userID = ? and userType = ?");
      localPreparedStatement.setInt(1, paramInt1);
      localPreparedStatement.setInt(2, paramInt2);
      DBUtil.executeUpdate(localPreparedStatement, "delete from RptReport where userID = ? and userType = ?");
      DBUtil.commit(localConnection);
      DBUtil.closeStatement(localPreparedStatement);
      return;
    }
    catch (Exception localException2)
    {
      if (localPreparedStatement != null) {
        DBUtil.closeStatement(localPreparedStatement);
      }
      DBUtil.rollback(localConnection);
      throw new RptException(5, localException2);
    }
    finally
    {
      try
      {
        DBUtil.returnConnection(this.jdField_byte, localConnection);
      }
      catch (Exception localException3) {}
    }
  }
  
  private Connection a()
    throws RptException
  {
    try
    {
      return DBUtil.getConnection(this.jdField_byte, true, 2);
    }
    catch (PoolException localPoolException)
    {
      throw new RptException(2, localPoolException);
    }
  }
  
  private Connection a(boolean paramBoolean)
    throws RptException
  {
    try
    {
      return DBUtil.getConnection(this.jdField_byte, paramBoolean, 2);
    }
    catch (PoolException localPoolException)
    {
      throw new RptException(2, localPoolException);
    }
  }
  
  private boolean a(Connection paramConnection, ReportIdentification paramReportIdentification)
  {
    if (paramReportIdentification == null) {
      return false;
    }
    try
    {
      PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select reportID, reportName, description from RptReport where reportID = ?");
      localPreparedStatement.setInt(1, paramReportIdentification.getReportID());
      ResultSet localResultSet = DBUtil.executeQuery(localPreparedStatement, "select reportID, reportName, description from RptReport where reportID = ?");
      for (boolean bool = false; localResultSet.next(); bool = true) {}
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      return bool;
    }
    catch (Exception localException) {}
    return false;
  }
  
  private void a(Connection paramConnection, Report paramReport)
    throws Exception
  {
    if ((paramReport.getReportCriteria() == null) || (paramReport.getReportCriteria().getReportOptions() == null)) {
      return;
    }
    Properties localProperties = paramReport.getReportCriteria().getReportOptions();
    if (localProperties == null) {
      return;
    }
    Iterator localIterator = localProperties.keySet().iterator();
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into RptOptions( reportID, optionName, optionValue ) values( ?, ?, ? )");
    try
    {
      localPreparedStatement.setInt(1, paramReport.getReportID().getReportID());
      while (localIterator.hasNext())
      {
        String str = (String)localIterator.next();
        localPreparedStatement.setString(2, str);
        localPreparedStatement.setString(3, localProperties.getProperty(str));
        DBUtil.executeUpdate(localPreparedStatement, "insert into RptOptions( reportID, optionName, optionValue ) values( ?, ?, ? )");
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private void jdField_int(Connection paramConnection, Report paramReport)
    throws Exception
  {
    if ((paramReport.getReportCriteria() == null) || (paramReport.getReportCriteria().getSortCriteria() == null)) {
      return;
    }
    ReportSortCriteria localReportSortCriteria = paramReport.getReportCriteria().getSortCriteria();
    if (localReportSortCriteria == null) {
      return;
    }
    Iterator localIterator = localReportSortCriteria.iterator();
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into RptSortCriteria( reportID, ordinal, criteriaName, ascDesc ) values( ?, ?, ?, ? )");
    try
    {
      localPreparedStatement.setInt(1, paramReport.getReportID().getReportID());
      while (localIterator.hasNext())
      {
        ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)localIterator.next();
        String str = localReportSortCriterion.getName();
        if ((str != null) && (str.length() > 0))
        {
          localPreparedStatement.setInt(2, localReportSortCriterion.getOrdinal());
          localPreparedStatement.setString(3, localReportSortCriterion.getName());
          localPreparedStatement.setBoolean(4, localReportSortCriterion.getAsc());
          DBUtil.executeUpdate(localPreparedStatement, "insert into RptSortCriteria( reportID, ordinal, criteriaName, ascDesc ) values( ?, ?, ?, ? )");
        }
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private void a(Connection paramConnection, Report paramReport, HashMap paramHashMap)
    throws Exception
  {
    UserLocale localUserLocale = null;
    String str1 = null;
    String str2 = null;
    if ((paramHashMap != null) && (paramHashMap.containsKey("UserLocale")))
    {
      localUserLocale = (UserLocale)paramHashMap.get("UserLocale");
      str1 = localUserLocale.getDateFormat();
      str2 = localUserLocale.getTimeFormat();
    }
    if ((paramReport.getReportCriteria() == null) || (paramReport.getReportCriteria().getSearchCriteria() == null)) {
      return;
    }
    Properties localProperties1 = paramReport.getReportCriteria().getSearchCriteria();
    if (localProperties1 == null) {
      return;
    }
    Properties localProperties2 = new Properties();
    localProperties2.putAll(localProperties1);
    if (str1 != null) {
      localProperties2.setProperty("DateFormat", str1);
    }
    if (str2 != null) {
      localProperties2.setProperty("TimeFormat", str2);
    }
    Iterator localIterator = localProperties2.keySet().iterator();
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, "insert into RptSearchCriteria( reportID, ordinal, criteriaName, criteriaValue ) values( ?, ?, ?, ? )");
    try
    {
      localPreparedStatement.setInt(1, paramReport.getReportID().getReportID());
      while (localIterator.hasNext())
      {
        String str3 = (String)localIterator.next();
        String str4 = localProperties2.getProperty(str3);
        ArrayList localArrayList = Strings.breakupString(str4, 255);
        for (int i = 0; i < localArrayList.size(); i++)
        {
          localPreparedStatement.setInt(2, i + 1);
          localPreparedStatement.setString(3, str3);
          localPreparedStatement.setString(4, (String)localArrayList.get(i));
          DBUtil.executeUpdate(localPreparedStatement, "insert into RptSearchCriteria( reportID, ordinal, criteriaName, criteriaValue ) values( ?, ?, ?, ? )");
        }
      }
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private void jdField_if(Connection paramConnection, Report paramReport)
    throws Exception
  {
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from RptOptions where reportID = ?");
    try
    {
      localPreparedStatement.setInt(1, paramReport.getReportID().getReportID());
      DBUtil.executeUpdate(localPreparedStatement, "delete from RptOptions where reportID = ?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private void jdField_do(Connection paramConnection, Report paramReport)
    throws Exception
  {
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from RptSortCriteria where reportID = ?");
    try
    {
      localPreparedStatement.setInt(1, paramReport.getReportID().getReportID());
      DBUtil.executeUpdate(localPreparedStatement, "delete from RptSortCriteria where reportID = ?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private void jdField_for(Connection paramConnection, Report paramReport)
    throws Exception
  {
    PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, "delete from RptSearchCriteria where reportID = ?");
    try
    {
      localPreparedStatement.setInt(1, paramReport.getReportID().getReportID());
      DBUtil.executeUpdate(localPreparedStatement, "delete from RptSearchCriteria where reportID = ?");
    }
    finally
    {
      DBUtil.closeStatement(localPreparedStatement);
    }
  }
  
  private boolean a(Connection paramConnection, SecureUser paramSecureUser, ReportIdentification paramReportIdentification)
  {
    try
    {
      PreparedStatement localPreparedStatement = DBUtil.prepareStatement(paramConnection, "select userID from RptReport where reportID = ? and userID = ? and userType = ?");
      localPreparedStatement.setInt(1, paramReportIdentification.getReportID());
      localPreparedStatement.setInt(2, paramSecureUser.getProfileID());
      localPreparedStatement.setInt(3, paramSecureUser.getUserType());
      ResultSet localResultSet = DBUtil.executeQuery(localPreparedStatement, "select reportID, reportName, description from RptReport where reportID = ?");
      boolean bool = localResultSet.next();
      DBUtil.closeAll(localPreparedStatement, localResultSet);
      return bool;
    }
    catch (Exception localException) {}
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.reporting.ReportingAdapter
 * JD-Core Version:    0.7.0.1
 */