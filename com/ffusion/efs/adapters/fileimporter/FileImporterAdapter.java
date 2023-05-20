package com.ffusion.efs.adapters.fileimporter;

import com.ffusion.beans.fileimporter.FieldDefinition;
import com.ffusion.beans.fileimporter.FieldDefinitions;
import com.ffusion.beans.fileimporter.MappingDefinition;
import com.ffusion.beans.fileimporter.MappingDefinitions;
import com.ffusion.csil.FIException;
import com.ffusion.util.db.ConnectionPool;
import com.ffusion.util.db.DBUtil;
import com.ffusion.util.logging.DebugLog;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class FileImporterAdapter
  implements FileImporterDefines
{
  public static final String SQL_ADD_MAPPING_DEFINITION = "insert into FI_MappingDefn (MappingID, BusinessID, Name, Description, OutputFormatName, InputFormatType, FieldDelimiterType, RecordDelimiterType, NumHeaderLines, RecordLength, NumHeaderChars, DateFormatType, DateSeparatorType, MoneyFormatType, MatchRecordsBy, UpdateEntriesBy) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
  public static final String SQL_ADD_FIELD_DEFINITION = "insert into FI_FieldDefn (FieldID, MappingID, Name, FieldNumber, FieldStart, FieldEnd, DefaultValue) values (?, ?, ?, ?, ?, ?, ?)";
  public static final String SQL_REMOVE_MAPPING_DEFINITION = "delete from FI_MappingDefn where MappingID = ? and BusinessID = ?";
  public static final String SQL_REMOVE_FIELD_DEFINITIONS = "delete from FI_FieldDefn where MappingID = ?";
  public static final String SQL_MODIFY_MAPPING_DEFINITION = "update FI_MappingDefn set Name = ?, Description = ?, OutputFormatName = ?, InputFormatType = ?, FieldDelimiterType = ?, RecordDelimiterType = ?, NumHeaderLines = ?, RecordLength = ?, NumHeaderChars = ?, DateFormatType = ?, DateSeparatorType = ?, MoneyFormatType = ?, MatchRecordsBy = ?, UpdateEntriesBy = ? where MappingID = ? and BusinessID = ?";
  public static final String SQL_GET_MAPPING_NAMES = "select Name from FI_MappingDefn where BusinessID = ?";
  public static final String SQL_GET_MAPPING_DEFINITION = "select * from FI_MappingDefn where BusinessID = ? and Name = ?";
  public static final String SQL_GET_MAPPING_DEFINITIONS = "select * from FI_MappingDefn where BusinessID = ?";
  public static final String SQL_GET_FIELD_DEFINITIONS = "select * from FI_FieldDefn where MappingID = ?";
  public static final String SQL_REMOVE_BUSINESS = "delete from FI_MappingDefn where BusinessID = ?";
  protected String poolName;
  protected String dbType;
  
  public synchronized void initialize(HashMap paramHashMap)
    throws FIException
  {
    String str1 = "FileImporterAdapter.initialize";
    if ((this.poolName != null) && (this.poolName.length() > 0)) {
      return;
    }
    try
    {
      Properties localProperties = new Properties();
      Iterator localIterator = paramHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        String str3 = (String)paramHashMap.get(str2);
        localProperties.setProperty(str2, str3);
      }
      this.poolName = ConnectionPool.init(localProperties);
      this.dbType = localProperties.getProperty("ConnectionType");
    }
    catch (Exception localException)
    {
      handleError(localException, str1, "Unable to initialize profile adapter", 8);
    }
  }
  
  public void isInitialized()
    throws FIException
  {
    if ((this.poolName == null) || (this.poolName.length() == 0)) {
      throw new FIException("FileImporterAdapter.isInitialized", 8, "File importer adapter is not initialized");
    }
  }
  
  public void addMappingDefinition(int paramInt, MappingDefinition paramMappingDefinition, HashMap paramHashMap)
    throws FIException
  {
    String str = "FileImporterAdapter.addMappingDefinition";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into FI_MappingDefn (MappingID, BusinessID, Name, Description, OutputFormatName, InputFormatType, FieldDelimiterType, RecordDelimiterType, NumHeaderLines, RecordLength, NumHeaderChars, DateFormatType, DateSeparatorType, MoneyFormatType, MatchRecordsBy, UpdateEntriesBy) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
      paramMappingDefinition.setMappingID(DBUtil.getNextId(localConnection, this.dbType, "MappingID"));
      localPreparedStatement.setInt(1, paramMappingDefinition.getMappingID());
      localPreparedStatement.setInt(2, paramInt);
      localPreparedStatement.setString(3, paramMappingDefinition.getName());
      localPreparedStatement.setString(4, paramMappingDefinition.getDescription());
      localPreparedStatement.setString(5, paramMappingDefinition.getOutputFormatName());
      localPreparedStatement.setInt(6, paramMappingDefinition.getInputFormatType());
      localPreparedStatement.setInt(7, paramMappingDefinition.getFieldDelimiterType());
      localPreparedStatement.setInt(8, paramMappingDefinition.getRecordDelimiterType());
      localPreparedStatement.setInt(9, paramMappingDefinition.getNumHeaderLines());
      localPreparedStatement.setInt(10, paramMappingDefinition.getRecordLength());
      localPreparedStatement.setInt(11, paramMappingDefinition.getNumHeaderChars());
      localPreparedStatement.setInt(12, paramMappingDefinition.getDateFormatType());
      localPreparedStatement.setInt(13, paramMappingDefinition.getDateSeparatorType());
      localPreparedStatement.setInt(14, paramMappingDefinition.getMoneyFormatType());
      localPreparedStatement.setInt(15, paramMappingDefinition.getMatchRecordsBy());
      localPreparedStatement.setInt(16, paramMappingDefinition.getUpdateRecordsBy());
      DBUtil.executeUpdate(localPreparedStatement, "insert into FI_MappingDefn (MappingID, BusinessID, Name, Description, OutputFormatName, InputFormatType, FieldDelimiterType, RecordDelimiterType, NumHeaderLines, RecordLength, NumHeaderChars, DateFormatType, DateSeparatorType, MoneyFormatType, MatchRecordsBy, UpdateEntriesBy) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
      DBUtil.closeStatement(localPreparedStatement);
      localPreparedStatement = null;
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into FI_FieldDefn (FieldID, MappingID, Name, FieldNumber, FieldStart, FieldEnd, DefaultValue) values (?, ?, ?, ?, ?, ?, ?)");
      Iterator localIterator = paramMappingDefinition.getFieldDefinitions().iterator();
      while (localIterator.hasNext())
      {
        int i = DBUtil.getNextId(localConnection, this.dbType, "FieldID");
        FieldDefinition localFieldDefinition = (FieldDefinition)localIterator.next();
        localPreparedStatement.setInt(1, i);
        localPreparedStatement.setInt(2, paramMappingDefinition.getMappingID());
        localPreparedStatement.setString(3, localFieldDefinition.getName());
        localPreparedStatement.setInt(4, localFieldDefinition.getFieldNumber());
        localPreparedStatement.setInt(5, localFieldDefinition.getFieldStart());
        localPreparedStatement.setInt(6, localFieldDefinition.getFieldEnd());
        localPreparedStatement.setString(7, localFieldDefinition.getDefaultValue());
        DBUtil.executeUpdate(localPreparedStatement, "insert into FI_FieldDefn (FieldID, MappingID, Name, FieldNumber, FieldStart, FieldEnd, DefaultValue) values (?, ?, ?, ?, ?, ?, ?)");
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      handleError(localException, str, "Unable to add mapping definition", 9600);
    }
    finally
    {
      DBUtil.closeAll(this.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public void removeMappingDefinition(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws FIException
  {
    String str = "FileImporterAdapter.removeMappingDefinition";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from FI_MappingDefn where MappingID = ? and BusinessID = ?");
      localPreparedStatement.setInt(1, paramInt2);
      localPreparedStatement.setInt(2, paramInt1);
      int i = DBUtil.executeUpdate(localPreparedStatement, "delete from FI_MappingDefn where MappingID = ? and BusinessID = ?");
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      handleError(localException, str, "Unable to remove mapping definition", 9600);
    }
    finally
    {
      DBUtil.closeAll(this.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public void modifyMappingDefinition(int paramInt, MappingDefinition paramMappingDefinition, HashMap paramHashMap)
    throws FIException
  {
    String str = "FileImporterAdapter.modifyMappingDefinition";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.poolName, false, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "update FI_MappingDefn set Name = ?, Description = ?, OutputFormatName = ?, InputFormatType = ?, FieldDelimiterType = ?, RecordDelimiterType = ?, NumHeaderLines = ?, RecordLength = ?, NumHeaderChars = ?, DateFormatType = ?, DateSeparatorType = ?, MoneyFormatType = ?, MatchRecordsBy = ?, UpdateEntriesBy = ? where MappingID = ? and BusinessID = ?");
      localPreparedStatement.setString(1, paramMappingDefinition.getName());
      localPreparedStatement.setString(2, paramMappingDefinition.getDescription());
      localPreparedStatement.setString(3, paramMappingDefinition.getOutputFormatName());
      localPreparedStatement.setInt(4, paramMappingDefinition.getInputFormatType());
      localPreparedStatement.setInt(5, paramMappingDefinition.getFieldDelimiterType());
      localPreparedStatement.setInt(6, paramMappingDefinition.getRecordDelimiterType());
      localPreparedStatement.setInt(7, paramMappingDefinition.getNumHeaderLines());
      localPreparedStatement.setInt(8, paramMappingDefinition.getRecordLength());
      localPreparedStatement.setInt(9, paramMappingDefinition.getNumHeaderChars());
      localPreparedStatement.setInt(10, paramMappingDefinition.getDateFormatType());
      localPreparedStatement.setInt(11, paramMappingDefinition.getDateSeparatorType());
      localPreparedStatement.setInt(12, paramMappingDefinition.getMoneyFormatType());
      localPreparedStatement.setInt(13, paramMappingDefinition.getMatchRecordsBy());
      localPreparedStatement.setInt(14, paramMappingDefinition.getUpdateRecordsBy());
      localPreparedStatement.setInt(15, paramMappingDefinition.getMappingID());
      localPreparedStatement.setInt(16, paramInt);
      int i = DBUtil.executeUpdate(localPreparedStatement, "update FI_MappingDefn set Name = ?, Description = ?, OutputFormatName = ?, InputFormatType = ?, FieldDelimiterType = ?, RecordDelimiterType = ?, NumHeaderLines = ?, RecordLength = ?, NumHeaderChars = ?, DateFormatType = ?, DateSeparatorType = ?, MoneyFormatType = ?, MatchRecordsBy = ?, UpdateEntriesBy = ? where MappingID = ? and BusinessID = ?");
      if (i > 0)
      {
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from FI_FieldDefn where MappingID = ?");
        localPreparedStatement.setInt(1, paramMappingDefinition.getMappingID());
        DBUtil.executeUpdate(localPreparedStatement, "delete from FI_FieldDefn where MappingID = ?");
        DBUtil.closeStatement(localPreparedStatement);
        localPreparedStatement = null;
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "insert into FI_FieldDefn (FieldID, MappingID, Name, FieldNumber, FieldStart, FieldEnd, DefaultValue) values (?, ?, ?, ?, ?, ?, ?)");
        Iterator localIterator = paramMappingDefinition.getFieldDefinitions().iterator();
        while (localIterator.hasNext())
        {
          FieldDefinition localFieldDefinition = (FieldDefinition)localIterator.next();
          localFieldDefinition.setFieldID(DBUtil.getNextId(localConnection, this.dbType, "FieldID"));
          localPreparedStatement.setInt(1, localFieldDefinition.getFieldID());
          localPreparedStatement.setInt(2, paramMappingDefinition.getMappingID());
          localPreparedStatement.setString(3, localFieldDefinition.getName());
          localPreparedStatement.setInt(4, localFieldDefinition.getFieldNumber());
          localPreparedStatement.setInt(5, localFieldDefinition.getFieldStart());
          localPreparedStatement.setInt(6, localFieldDefinition.getFieldEnd());
          localPreparedStatement.setString(7, localFieldDefinition.getDefaultValue());
          DBUtil.executeUpdate(localPreparedStatement, "insert into FI_FieldDefn (FieldID, MappingID, Name, FieldNumber, FieldStart, FieldEnd, DefaultValue) values (?, ?, ?, ?, ?, ?, ?)");
        }
      }
      DBUtil.commit(localConnection);
    }
    catch (Exception localException)
    {
      DBUtil.rollback(localConnection);
      handleError(localException, str, "Unable to modify mapping definition", 9600);
    }
    finally
    {
      DBUtil.closeAll(this.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public ArrayList getMappingNames(int paramInt, HashMap paramHashMap)
    throws FIException
  {
    String str = "FileImporterAdapter.getMappingNames";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    ArrayList localArrayList = new ArrayList();
    try
    {
      localConnection = DBUtil.getConnection(this.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select Name from FI_MappingDefn where BusinessID = ?");
      localPreparedStatement.setInt(1, paramInt);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select Name from FI_MappingDefn where BusinessID = ?");
      while (localResultSet.next()) {
        localArrayList.add(localResultSet.getString(1));
      }
    }
    catch (Exception localException)
    {
      handleError(localException, str, "Unable to get mapping names", 9600);
    }
    finally
    {
      DBUtil.closeAll(this.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localArrayList;
  }
  
  public MappingDefinition getMappingDefinition(int paramInt, String paramString, HashMap paramHashMap)
    throws FIException
  {
    String str = "FileImporterAdapter.getMappingDefinition";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    ResultSet localResultSet = null;
    isInitialized();
    MappingDefinition localMappingDefinition = null;
    try
    {
      localConnection = DBUtil.getConnection(this.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from FI_MappingDefn where BusinessID = ? and Name = ?");
      localPreparedStatement.setInt(1, paramInt);
      localPreparedStatement.setString(2, paramString);
      localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from FI_MappingDefn where BusinessID = ? and Name = ?");
      if (localResultSet.next())
      {
        localMappingDefinition = new MappingDefinition();
        int i = localResultSet.getInt("MappingID");
        localMappingDefinition.setMappingID(i);
        localMappingDefinition.setName(localResultSet.getString("Name"));
        localMappingDefinition.setDescription(localResultSet.getString("Description"));
        localMappingDefinition.setOutputFormatName(localResultSet.getString("OutputFormatName"));
        localMappingDefinition.setInputFormatType(localResultSet.getInt("InputFormatType"));
        localMappingDefinition.setFieldDelimiterType(localResultSet.getInt("FieldDelimiterType"));
        localMappingDefinition.setRecordDelimiterType(localResultSet.getInt("RecordDelimiterType"));
        localMappingDefinition.setNumHeaderLines(localResultSet.getInt("NumHeaderLines"));
        localMappingDefinition.setRecordLength(localResultSet.getInt("RecordLength"));
        localMappingDefinition.setNumHeaderChars(localResultSet.getInt("NumHeaderChars"));
        localMappingDefinition.setDateFormatType(localResultSet.getInt("DateFormatType"));
        localMappingDefinition.setDateSeparatorType(localResultSet.getInt("DateSeparatorType"));
        localMappingDefinition.setMoneyFormatType(localResultSet.getInt("MoneyFormatType"));
        localMappingDefinition.setMatchRecordsBy(localResultSet.getInt("MatchRecordsBy"));
        localMappingDefinition.setUpdateRecordsBy(localResultSet.getInt("UpdateEntriesBy"));
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localPreparedStatement = null;
        localResultSet = null;
        FieldDefinitions localFieldDefinitions = localMappingDefinition.getFieldDefinitions();
        localPreparedStatement = DBUtil.prepareStatement(localConnection, "select * from FI_FieldDefn where MappingID = ?");
        localPreparedStatement.setInt(1, i);
        localResultSet = DBUtil.executeQuery(localPreparedStatement, "select * from FI_FieldDefn where MappingID = ?");
        while (localResultSet.next())
        {
          FieldDefinition localFieldDefinition = new FieldDefinition();
          localFieldDefinition.setFieldID(localResultSet.getInt("FieldID"));
          localFieldDefinition.setName(localResultSet.getString("Name"));
          localFieldDefinition.setFieldNumber(localResultSet.getInt("FieldNumber"));
          localFieldDefinition.setFieldStart(localResultSet.getInt("FieldStart"));
          localFieldDefinition.setFieldEnd(localResultSet.getInt("FieldEnd"));
          localFieldDefinition.setDefaultValue(localResultSet.getString("DefaultValue"));
          localFieldDefinitions.add(localFieldDefinition);
        }
        DBUtil.closeAll(localPreparedStatement, localResultSet);
        localPreparedStatement = null;
        localResultSet = null;
      }
    }
    catch (Exception localException)
    {
      handleError(localException, str, "Unable to get mapping definition", 9600);
    }
    finally
    {
      DBUtil.closeAll(this.poolName, localConnection, localPreparedStatement, localResultSet);
    }
    return localMappingDefinition;
  }
  
  public MappingDefinitions getMappingDefinitions(int paramInt, HashMap paramHashMap)
    throws FIException
  {
    String str = "FileImporterAdapter.getMappingDefinitions";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement1 = null;
    PreparedStatement localPreparedStatement2 = null;
    ResultSet localResultSet1 = null;
    ResultSet localResultSet2 = null;
    isInitialized();
    MappingDefinitions localMappingDefinitions = new MappingDefinitions();
    try
    {
      localConnection = DBUtil.getConnection(this.poolName, true, 2);
      localPreparedStatement1 = DBUtil.prepareStatement(localConnection, "select * from FI_MappingDefn where BusinessID = ?");
      localPreparedStatement1.setInt(1, paramInt);
      localResultSet1 = DBUtil.executeQuery(localPreparedStatement1, "select * from FI_MappingDefn where BusinessID = ?");
      while (localResultSet1.next())
      {
        MappingDefinition localMappingDefinition = new MappingDefinition();
        int i = localResultSet1.getInt("MappingID");
        localMappingDefinition.setMappingID(i);
        localMappingDefinition.setName(localResultSet1.getString("Name"));
        localMappingDefinition.setDescription(localResultSet1.getString("Description"));
        localMappingDefinition.setOutputFormatName(localResultSet1.getString("OutputFormatName"));
        localMappingDefinition.setInputFormatType(localResultSet1.getInt("InputFormatType"));
        localMappingDefinition.setFieldDelimiterType(localResultSet1.getInt("FieldDelimiterType"));
        localMappingDefinition.setRecordDelimiterType(localResultSet1.getInt("RecordDelimiterType"));
        localMappingDefinition.setNumHeaderLines(localResultSet1.getInt("NumHeaderLines"));
        localMappingDefinition.setRecordLength(localResultSet1.getInt("RecordLength"));
        localMappingDefinition.setNumHeaderChars(localResultSet1.getInt("NumHeaderChars"));
        localMappingDefinition.setDateFormatType(localResultSet1.getInt("DateFormatType"));
        localMappingDefinition.setDateSeparatorType(localResultSet1.getInt("DateSeparatorType"));
        localMappingDefinition.setMoneyFormatType(localResultSet1.getInt("MoneyFormatType"));
        localMappingDefinition.setMatchRecordsBy(localResultSet1.getInt("MatchRecordsBy"));
        localMappingDefinition.setUpdateRecordsBy(localResultSet1.getInt("UpdateEntriesBy"));
        FieldDefinitions localFieldDefinitions = localMappingDefinition.getFieldDefinitions();
        localPreparedStatement2 = DBUtil.prepareStatement(localConnection, "select * from FI_FieldDefn where MappingID = ?");
        localPreparedStatement2.setInt(1, i);
        localResultSet2 = DBUtil.executeQuery(localPreparedStatement2, "select * from FI_FieldDefn where MappingID = ?");
        while (localResultSet2.next())
        {
          FieldDefinition localFieldDefinition = new FieldDefinition();
          localFieldDefinition.setFieldID(localResultSet2.getInt("FieldID"));
          localFieldDefinition.setName(localResultSet2.getString("Name"));
          localFieldDefinition.setFieldNumber(localResultSet2.getInt("FieldNumber"));
          localFieldDefinition.setFieldStart(localResultSet2.getInt("FieldStart"));
          localFieldDefinition.setFieldEnd(localResultSet2.getInt("FieldEnd"));
          localFieldDefinition.setDefaultValue(localResultSet2.getString("DefaultValue"));
          localFieldDefinitions.add(localFieldDefinition);
        }
        localMappingDefinitions.add(localMappingDefinition);
      }
    }
    catch (Exception localException)
    {
      handleError(localException, str, "Unable to get mapping definition", 9600);
    }
    finally
    {
      DBUtil.closeAll(localPreparedStatement2, localResultSet2);
      DBUtil.closeAll(this.poolName, localConnection, localPreparedStatement1, localResultSet1);
    }
    return localMappingDefinitions;
  }
  
  public void removeBusiness(int paramInt, HashMap paramHashMap)
    throws FIException
  {
    String str = "FileImporterAdapter.removeBusiness";
    Connection localConnection = null;
    PreparedStatement localPreparedStatement = null;
    isInitialized();
    try
    {
      localConnection = DBUtil.getConnection(this.poolName, true, 2);
      localPreparedStatement = DBUtil.prepareStatement(localConnection, "delete from FI_MappingDefn where BusinessID = ?");
      localPreparedStatement.setInt(1, paramInt);
      DBUtil.executeUpdate(localPreparedStatement, "delete from FI_MappingDefn where BusinessID = ?");
    }
    catch (Exception localException)
    {
      handleError(localException, str, "Unable to remove business", 9600);
    }
    finally
    {
      DBUtil.closeAll(this.poolName, localConnection, localPreparedStatement);
    }
  }
  
  public void handleError(Exception paramException, String paramString1, String paramString2, int paramInt)
    throws FIException
  {
    DebugLog.throwing("FileImporterAdapter.handleError: ", paramException);
    if ((paramException instanceof FIException)) {
      throw ((FIException)paramException);
    }
    throw new FIException(9600, paramException);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.fileimporter.FileImporterAdapter
 * JD-Core Version:    0.7.0.1
 */