package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConnectionInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowList;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSDBProperties;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import java.util.ArrayList;

public class DBPropertyConfig
  implements DBConsts
{
  public static final char SERVER_NAME_SEPARATOR = '|';
  private static final String km = "SELECT PropertyName, Value FROM BPW_PropertyConfig";
  private static final String kl = "INSERT INTO BPW_PropertyConfig(PropertyName, Value )VALUES ( ?, ? ) ";
  private static final String ko = "DELETE FROM BPW_PropertyConfig WHERE PropertyName=? ";
  private static final String kn = "UPDATE BPW_PropertyConfig SET Value=? WHERE PropertyName=?";
  public static final String ADD_PROCESSING_WINDOW = "INSERT INTO BPW_ProcessWindow (WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy) VALUES (?,?,?,?,?,?,?,?,?,?) ";
  public static final String MOD_PROCESSING_WINDOW = "UPDATE BPW_ProcessWindow set StartTime = ?, CloseTime = ?, PmtType = ?, PmtSubType = ?, Description = ? WHERE WindowID = ? ";
  public static final String DEL_PROCESSING_WINDOW = "DELETE FROM BPW_ProcessWindow WHERE WindowID = ? ";
  public static final String GET_PROCESS_WINDOW_CONST = "SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow";
  public static final String GET_PROCESS_WINDOW_BY_FIID_CUSTID_PMTTYPE_SUBTYPE_TIMES = "SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow WHERE FIID = ?  AND PmtType = ?  AND PmtSubType = ?  AND StartTime = ?  AND CloseTime = ? ";
  public static final String GET_BANK_WINDOW_COVERING_BUSINESS_WINDOW = "SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow WHERE FIID = ?  AND CustomerID is NULL  AND PmtType = ?  AND PmtSubType = ?  AND ( StartTime <= ?      AND CloseTime >= ? ) ";
  public static final String GET_BUSINESS_WINDOW_OUTSIDE_BANK_WINDOW = "SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow WHERE FIID = ? AND CustomerID is not NULL AND PmtType = ?  AND PmtSubType = ? AND (StartTime < ? OR CloseTime > ?)  AND WindowID not in (SELECT distinct  a.WindowID           from BPW_ProcessWindow a, BPW_ProcessWindow b           WHERE a.FIID= ?  AND a.PmtType = ?           AND a.PmtSubType = ?  AND a.FIID = b.FIID           AND a.PmtType = b.PmtType  AND a.PmtSubType = b.PmtSubType           AND a.CustomerID is not null  AND b.CustomerID is NULL           AND ( (a.StartTime >= b.StartTime)          AND (a.CloseTime <= b.CloseTime) )           AND b.WindowID <> ? ) ";
  public static final String GET_PROCESS_WINDOW_BY_FIID_PMTTYPE_SUBTYPE = "SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow WHERE FIID = ?  AND PmtType = ?  AND PmtSubType = ? ";
  
  public static final FFSProperties readPropertyConfig(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSProperties localFFSProperties = new FFSProperties();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT PropertyName, Value FROM BPW_PropertyConfig", new Object[0]);
      String str1 = null;
      String str2 = null;
      while (localFFSResultSet.getNextRow())
      {
        str1 = localFFSResultSet.getColumnString(1);
        str2 = localFFSResultSet.getColumnString(2);
        if (str2 == null) {
          str2 = "";
        }
        localFFSProperties.put(str1, str2);
      }
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
    }
    return localFFSProperties;
  }
  
  public static final FFSProperties readPropertyConfig(FFSProperties paramFFSProperties)
  {
    FFSDebug.log("*** DBPropertyconfig.readPropertyConfig started", 6);
    FFSProperties localFFSProperties = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      FFSDBProperties localFFSDBProperties = new FFSDBProperties(paramFFSProperties);
      localFFSConnectionHolder.conn = DBUtil.getMyOwnConnection(localFFSDBProperties);
      localFFSProperties = readPropertyConfig(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder.conn.rollback();
      String str1 = FFSDebug.stackTrace(localException1);
      FFSDebug.log("*** DBPropertyConfi.readPropertyConfig failes:" + str1);
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.close();
      }
      catch (Exception localException2)
      {
        String str2 = FFSDebug.stackTrace(localException2);
        FFSDebug.log("*** DBPropertyConfi.readPropertyConfig failes:" + str2);
      }
    }
    FFSDebug.log("*** DBPropertyconfig.readPropertyConfig done", 6);
    return localFFSProperties;
  }
  
  public static final void addPropertyConfig(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    try
    {
      Object[] arrayOfObject = { paramString1, paramString2 };
      DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_PropertyConfig(PropertyName, Value )VALUES ( ?, ? ) ", arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log("Failed to add (" + paramString1 + ", " + paramString2 + ") : " + localFFSException.toString(), 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to add (" + paramString1 + ", " + paramString2 + ") : " + localException.toString(), 0);
      throw new FFSException(localException.toString());
    }
  }
  
  public static final void updatePropertyConfig(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    try
    {
      Object[] arrayOfObject = { paramString2, paramString1 };
      DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_PropertyConfig SET Value=? WHERE PropertyName=?", arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
  }
  
  public static final void deletePropertyConfig(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    try
    {
      Object[] arrayOfObject = { paramString };
      DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_PropertyConfig WHERE PropertyName=? ", arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.toString());
    }
  }
  
  public static DBConnectionInfo getBPWServerDBInfo(FFSProperties paramFFSProperties)
  {
    DBConnectionInfo localDBConnectionInfo = new DBConnectionInfo();
    String str = (String)paramFFSProperties.remove("BPW.DB.TYPE");
    if ((str == null) || (str.length() <= 0)) {
      localDBConnectionInfo.dbType = "ASA";
    } else if (str.equalsIgnoreCase("ORACLE:thin")) {
      localDBConnectionInfo.dbType = "ORACLE:thin";
    } else if (str.equalsIgnoreCase("ORACLE:oci8")) {
      localDBConnectionInfo.dbType = "ORACLE:oci8";
    } else if (str.equalsIgnoreCase("ASA")) {
      localDBConnectionInfo.dbType = "ASA";
    } else if (str.equalsIgnoreCase("ASE")) {
      localDBConnectionInfo.dbType = "ASE";
    } else if (str.equalsIgnoreCase("MSSQL:thin")) {
      localDBConnectionInfo.dbType = "MSSQL:thin";
    } else {
      localDBConnectionInfo.dbType = str;
    }
    str = (String)paramFFSProperties.remove("BPW.DB.HOST");
    if ((str == null) || (str.length() <= 0)) {
      localDBConnectionInfo.host = "localhost";
    } else {
      localDBConnectionInfo.host = str;
    }
    str = (String)paramFFSProperties.remove("BPW.DB.PORT");
    localDBConnectionInfo.port = ((str == null) || (str.length() <= 0) ? Integer.parseInt("3022") : Integer.parseInt(str));
    str = (String)paramFFSProperties.remove("BPW.DB.NAME");
    if ((str == null) || (str.length() <= 0)) {
      localDBConnectionInfo.dbName = "bpw";
    } else {
      localDBConnectionInfo.dbName = str;
    }
    str = (String)paramFFSProperties.remove("BPW.DB.USER");
    localDBConnectionInfo.userName = (str == null ? "" : str);
    str = (String)paramFFSProperties.remove("BPW.DB.PASSWORD");
    localDBConnectionInfo.password = (str == null ? "" : str);
    return localDBConnectionInfo;
  }
  
  public static DBConnectionInfo getMBRuntimeDBInfo(FFSProperties paramFFSProperties)
  {
    DBConnectionInfo localDBConnectionInfo = new DBConnectionInfo();
    String str = (String)paramFFSProperties.remove("MBConn_dbType");
    if ((str == null) || (str.length() <= 0)) {
      localDBConnectionInfo.dbType = "ASA";
    } else if (str.equalsIgnoreCase("ORACLE:thin")) {
      localDBConnectionInfo.dbType = "ORACLE:thin";
    } else if (str.equalsIgnoreCase("ORACLE:oci8")) {
      localDBConnectionInfo.dbType = "ORACLE:oci8";
    } else if (str.equalsIgnoreCase("ASA")) {
      localDBConnectionInfo.dbType = "ASA";
    } else if (str.equalsIgnoreCase("ASE")) {
      localDBConnectionInfo.dbType = "ASE";
    } else if (str.equalsIgnoreCase("MSSQL:thin")) {
      localDBConnectionInfo.dbType = "MSSQL:thin";
    } else {
      localDBConnectionInfo.dbType = str;
    }
    int i = (localDBConnectionInfo.dbType.equalsIgnoreCase("ORACLE:oci8")) || (localDBConnectionInfo.dbType.equalsIgnoreCase("DB2:app")) || (localDBConnectionInfo.dbType.equalsIgnoreCase("DB2:un2")) ? 1 : 0;
    str = (String)paramFFSProperties.remove("MBConn_host");
    if ((str == null) || (str.length() <= 0))
    {
      if (i == 0) {
        localDBConnectionInfo.host = "localhost";
      }
    }
    else {
      localDBConnectionInfo.host = str;
    }
    str = (String)paramFFSProperties.remove("MBConn_port");
    if ((str == null) || (str.length() <= 0))
    {
      if (i == 0) {
        localDBConnectionInfo.port = Integer.parseInt("3021");
      }
    }
    else {
      localDBConnectionInfo.port = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("MBConn_dbname");
    if ((str == null) || (str.length() <= 0)) {
      localDBConnectionInfo.dbName = "mbbpw";
    } else {
      localDBConnectionInfo.dbName = str;
    }
    str = (String)paramFFSProperties.remove("MBConn_user");
    localDBConnectionInfo.userName = str;
    str = (String)paramFFSProperties.remove("MBConn_passwd");
    if ((str != null) && (str.length() > 0)) {
      localDBConnectionInfo.password = str;
    }
    str = (String)paramFFSProperties.remove("MBConn_url");
    if ((str != null) && (str.length() > 0)) {
      localDBConnectionInfo.url = str;
    }
    return localDBConnectionInfo;
  }
  
  public static PropertyConfig createPropertyConfig(FFSProperties paramFFSProperties, String paramString)
  {
    PropertyConfig localPropertyConfig = createPropertyConfig(paramFFSProperties);
    localPropertyConfig.serverName = ((paramString == null) || (paramString.length() <= 0) ? "Default Server" : paramString);
    return localPropertyConfig;
  }
  
  public static PropertyConfig createPropertyConfig(FFSProperties paramFFSProperties)
  {
    PropertyConfig localPropertyConfig = new PropertyConfig();
    String str = null;
    str = (String)paramFFSProperties.remove("NoImmediateTransfer");
    localPropertyConfig.NoImmediateTransfer = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramFFSProperties.remove("OneCustPayeeToOnePersonalPayee");
    localPropertyConfig.OneCustPayeeToOnePersonalPayee = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramFFSProperties.remove("EnforcePayment");
    localPropertyConfig.EnforcePayment = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramFFSProperties.remove("SupportImmediatePmt");
    localPropertyConfig.SupportImmediatePmt = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramFFSProperties.remove("RouteTimedOutToBatch");
    localPropertyConfig.RouteTimedOutToBatch = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramFFSProperties.remove("UseExtdPayeeID");
    localPropertyConfig.UseExtdPayeeID = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramFFSProperties.remove("StartPayeeListID");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.StartPayeeListID = 0;
    } else {
      localPropertyConfig.StartPayeeListID = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("FundsAllocRetries");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.FundsAllocRetries = 3;
    } else {
      localPropertyConfig.FundsAllocRetries = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("FundsApprovalRetries");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.FundsApprovalRetries = 3;
    } else {
      localPropertyConfig.FundsApprovalRetries = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("ConcurrentEventProcesses");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.ConcurrentEventProcesses = 0;
    } else {
      localPropertyConfig.ConcurrentEventProcesses = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("ThreadPoolMaximumSize");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.ThreadPoolMaximumSize = 64;
    } else {
      localPropertyConfig.ThreadPoolMaximumSize = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("OptimalPoolSize");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.OptimalPoolSize = 16;
    } else {
      localPropertyConfig.OptimalPoolSize = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("MaxPoolSize");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.MaxPoolSize = 128;
    } else {
      localPropertyConfig.MaxPoolSize = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("ThreadPoolInitialSize");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.ThreadPoolInitialSize = 32;
    } else {
      localPropertyConfig.ThreadPoolInitialSize = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("Retries");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.Retries = 3;
    } else {
      localPropertyConfig.Retries = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("Timeout");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.Timeout = 60000;
    } else {
      localPropertyConfig.Timeout = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("PurgeWakeupTime");
    localPropertyConfig.PurgeWakeupTime = ((str == null) || (str.length() <= 0) ? "12:00 AM" : str);
    str = (String)paramFFSProperties.remove("DayToPurgeClosedInstruction");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.DayToPurgeClosedInstruction = 30;
    } else {
      localPropertyConfig.DayToPurgeClosedInstruction = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("DayToPurgeEventHistory");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.DayToPurgeEventHistory = 30;
    } else {
      localPropertyConfig.DayToPurgeEventHistory = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("DayToPurgeActivityLog");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.DayToPurgeActivityLog = 30;
    } else {
      localPropertyConfig.DayToPurgeActivityLog = Integer.parseInt(str);
    }
    str = (String)paramFFSProperties.remove("BPWServ_userName");
    localPropertyConfig.BPWServ_userName = (str == null ? "" : str);
    str = (String)paramFFSProperties.remove("BPWServ_password");
    localPropertyConfig.BPWServ_password = str;
    str = (String)paramFFSProperties.remove("BPWServ_protocol");
    localPropertyConfig.BPWServ_protocol = ((str == null) || (str.length() <= 0) ? "iiop" : str);
    str = (String)paramFFSProperties.remove("BPWServ_host");
    localPropertyConfig.BPWServ_host = ((str == null) || (str.length() <= 0) ? "localhost" : str);
    str = (String)paramFFSProperties.remove("BPWServ_port");
    localPropertyConfig.BPWServ_port = ((str == null) || (str.length() <= 0) ? 900 : Integer.parseInt(str));
    str = (String)paramFFSProperties.remove("BPWServ_nameContext");
    localPropertyConfig.BPWServ_nameContext = ((str == null) || (str.length() <= 0) ? "com.ibm.websphere.naming.WsnInitialContextFactory" : str);
    str = (String)paramFFSProperties.remove("BPW.ADMIN.JNDI.NAME");
    localPropertyConfig.BPWServ_jndiName = ((str == null) || (str.length() <= 0) ? "bpw.BPWAdminHome" : str);
    str = (String)paramFFSProperties.remove("SchWaitTime");
    if ((str == null) || (str.length() <= 0)) {
      localPropertyConfig.SchWaitTime = 60000L;
    } else {
      localPropertyConfig.SchWaitTime = Long.parseLong(str);
    }
    str = (String)paramFFSProperties.remove("SupportCluster");
    localPropertyConfig.SupportCluster = ((str == null) || (str.length() <= 0) || (str.equalsIgnoreCase("true")));
    str = (String)paramFFSProperties.remove("EnforceEnrollment");
    localPropertyConfig.EnforceEnrollment = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramFFSProperties.remove("LogLevel");
    localPropertyConfig.LogLevel = ((str == null) || (str.length() <= 0) ? 0 : Integer.parseInt(str));
    str = (String)paramFFSProperties.remove("DebugLevel");
    localPropertyConfig.DebugLevel = ((str == null) || (str.length() <= 0) ? 0 : Integer.parseInt(str));
    str = (String)paramFFSProperties.remove("BatchSize");
    localPropertyConfig.BatchSize = ((str == null) || (str.length() <= 0) ? 1000 : Integer.parseInt(str));
    str = (String)paramFFSProperties.remove("MinuteNotToRecover");
    localPropertyConfig.MinuteNotToRecover = ((str == null) || (str.length() <= 0) ? 240 : Integer.parseInt(str));
    localPropertyConfig.otherProperties = paramFFSProperties;
    return localPropertyConfig;
  }
  
  public static ProcessingWindowInfo addProcessingWindow(FFSConnectionHolder paramFFSConnectionHolder, ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str1 = "DBPropertyConfig.addProcessingWindow: ";
    String str2 = null;
    String str3 = null;
    Object[] arrayOfObject = null;
    FFSDebug.log(str1, "Starts.", 6);
    String str4;
    if (paramProcessingWindowInfo == null)
    {
      paramProcessingWindowInfo = new ProcessingWindowInfo();
      paramProcessingWindowInfo.setStatusCode(16000);
      str4 = "ProcessingWindowInfo object  is null";
      paramProcessingWindowInfo.setStatusMsg(str4);
      FFSDebug.log(str1, str4, 0);
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    if (paramFFSConnectionHolder == null)
    {
      paramProcessingWindowInfo.setStatusCode(16000);
      str4 = "FFSConnectionHolder object  is null";
      paramProcessingWindowInfo.setStatusMsg(str4);
      FFSDebug.log(str1, str4, 0);
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    if ((paramProcessingWindowInfo.getFIID() == null) || (paramProcessingWindowInfo.getFIID().trim().length() == 0))
    {
      paramProcessingWindowInfo.setStatusCode(16010);
      str4 = "FIID of ProcessingWindowInfo  contains null ";
      paramProcessingWindowInfo.setStatusMsg(str4);
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      FFSDebug.log(str1, str4, 0);
      return paramProcessingWindowInfo;
    }
    jdMethod_new(paramFFSConnectionHolder, paramProcessingWindowInfo);
    if (paramProcessingWindowInfo.getStatusCode() != 0)
    {
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    jdMethod_do(paramFFSConnectionHolder, paramProcessingWindowInfo);
    if (paramProcessingWindowInfo.getStatusCode() != 0)
    {
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    if (paramProcessingWindowInfo.getCustomerId() != null)
    {
      a(paramFFSConnectionHolder, paramProcessingWindowInfo);
      if (paramProcessingWindowInfo.getStatusCode() != 0)
      {
        paramProcessingWindowInfo.setProcessStatus("FAILED");
        return paramProcessingWindowInfo;
      }
    }
    try
    {
      jdMethod_for(paramFFSConnectionHolder, paramProcessingWindowInfo);
      if (paramProcessingWindowInfo.getStatusCode() != 0) {
        return paramProcessingWindowInfo;
      }
      str2 = "INSERT INTO BPW_ProcessWindow (WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy) VALUES (?,?,?,?,?,?,?,?,?,?) ";
      str3 = DBUtil.getNewTransId("PROCESSINGWINDOWID", 18);
      paramProcessingWindowInfo.setWindowId(str3);
      arrayOfObject = jdMethod_do(paramProcessingWindowInfo);
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      paramProcessingWindowInfo.setStatusCode(0);
      paramProcessingWindowInfo.setStatusMsg("Success");
      paramProcessingWindowInfo.setProcessStatus("SUCCESS");
    }
    catch (Throwable localThrowable)
    {
      String str5 = str1 + " failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5, str6, 0);
      throw new FFSException(localThrowable, str5);
    }
    return paramProcessingWindowInfo;
  }
  
  public static ProcessingWindowInfo modProcessingWindow(FFSConnectionHolder paramFFSConnectionHolder, ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str1 = "DBPropertyConfig.modProcessingWindow: ";
    String str2 = null;
    FFSDebug.log(str1, "Starts.", 6);
    if (paramProcessingWindowInfo == null)
    {
      paramProcessingWindowInfo = new ProcessingWindowInfo();
      paramProcessingWindowInfo.setStatusCode(16000);
      localObject = "ProcessingWindowInfo object  is null";
      paramProcessingWindowInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    if (paramFFSConnectionHolder == null)
    {
      paramProcessingWindowInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramProcessingWindowInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    if ((paramProcessingWindowInfo.getWindowId() == null) || (paramProcessingWindowInfo.getWindowId().trim().length() == 0))
    {
      paramProcessingWindowInfo.setStatusCode(16010);
      localObject = "WindowID of ProcessingWindowInfo  contains null ";
      paramProcessingWindowInfo.setStatusMsg((String)localObject);
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      FFSDebug.log(str1, (String)localObject, 0);
      return paramProcessingWindowInfo;
    }
    if (paramProcessingWindowInfo.getCustomerId() != null)
    {
      a(paramFFSConnectionHolder, paramProcessingWindowInfo);
      if (paramProcessingWindowInfo.getStatusCode() != 0)
      {
        paramProcessingWindowInfo.setProcessStatus("FAILED");
        return paramProcessingWindowInfo;
      }
    }
    else
    {
      jdMethod_int(paramFFSConnectionHolder, paramProcessingWindowInfo);
      if (paramProcessingWindowInfo.getStatusCode() != 0)
      {
        paramProcessingWindowInfo.setProcessStatus("FAILED");
        return paramProcessingWindowInfo;
      }
    }
    jdMethod_new(paramFFSConnectionHolder, paramProcessingWindowInfo);
    if (paramProcessingWindowInfo.getStatusCode() != 0)
    {
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    jdMethod_do(paramFFSConnectionHolder, paramProcessingWindowInfo);
    if (paramProcessingWindowInfo.getStatusCode() != 0)
    {
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    Object localObject = { paramProcessingWindowInfo.getStartTime(), paramProcessingWindowInfo.getCloseTime(), paramProcessingWindowInfo.getPmtType(), paramProcessingWindowInfo.getPmtSubType(), paramProcessingWindowInfo.getDescription(), paramProcessingWindowInfo.getWindowId() };
    try
    {
      str2 = "UPDATE BPW_ProcessWindow set StartTime = ?, CloseTime = ?, PmtType = ?, PmtSubType = ?, Description = ? WHERE WindowID = ? ";
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      if (i == 0)
      {
        paramProcessingWindowInfo.setStatusCode(16020);
        paramProcessingWindowInfo.setStatusMsg(" record not found");
        paramProcessingWindowInfo.setProcessStatus("FAILED");
      }
      else
      {
        paramProcessingWindowInfo.setStatusCode(0);
        paramProcessingWindowInfo.setStatusMsg("Success");
        paramProcessingWindowInfo.setProcessStatus("SUCCESS");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    return paramProcessingWindowInfo;
  }
  
  public static ProcessingWindowInfo delProcessingWindow(FFSConnectionHolder paramFFSConnectionHolder, ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str1 = "DBPropertyConfig.delProcessingWindow: ";
    String str2 = null;
    FFSDebug.log(str1, "Starts.", 6);
    if (paramProcessingWindowInfo == null)
    {
      paramProcessingWindowInfo = new ProcessingWindowInfo();
      paramProcessingWindowInfo.setStatusCode(16000);
      localObject = "ProcessingWindowInfo object  is null";
      paramProcessingWindowInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    if (paramFFSConnectionHolder == null)
    {
      paramProcessingWindowInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramProcessingWindowInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1, (String)localObject, 0);
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    if ((paramProcessingWindowInfo.getWindowId() == null) || (paramProcessingWindowInfo.getWindowId().trim().length() == 0))
    {
      paramProcessingWindowInfo.setStatusCode(16010);
      localObject = "WindowID of ProcessingWindowInfo  contains null ";
      paramProcessingWindowInfo.setStatusMsg((String)localObject);
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      FFSDebug.log(str1, (String)localObject, 0);
      return paramProcessingWindowInfo;
    }
    Object localObject = { paramProcessingWindowInfo.getWindowId() };
    try
    {
      str2 = "DELETE FROM BPW_ProcessWindow WHERE WindowID = ? ";
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      if (i == 0)
      {
        paramProcessingWindowInfo.setStatusCode(16020);
        paramProcessingWindowInfo.setStatusMsg(" record not found");
        paramProcessingWindowInfo.setProcessStatus("FAILED");
      }
      else
      {
        paramProcessingWindowInfo.setStatusCode(0);
        paramProcessingWindowInfo.setStatusMsg("Success");
        paramProcessingWindowInfo.setProcessStatus("SUCCESS");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    return paramProcessingWindowInfo;
  }
  
  public static ProcessingWindowList getProcessingWindows(FFSConnectionHolder paramFFSConnectionHolder, ProcessingWindowList paramProcessingWindowList)
    throws FFSException
  {
    String str1 = "DBPropertyConfig.getProcessingWindows: ";
    StringBuffer localStringBuffer = null;
    Object[] arrayOfObject = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList1 = null;
    String[] arrayOfString1 = null;
    String[] arrayOfString2 = null;
    String[] arrayOfString3 = null;
    String[] arrayOfString4 = null;
    ArrayList localArrayList2 = null;
    ProcessingWindowInfo localProcessingWindowInfo = null;
    FFSDebug.log(str1, "Starts.", 6);
    String str2;
    if (paramProcessingWindowList == null)
    {
      paramProcessingWindowList = new ProcessingWindowList();
      paramProcessingWindowList.setStatusCode(16000);
      str2 = "ProcessingWindowList object  is null";
      paramProcessingWindowList.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return paramProcessingWindowList;
    }
    if (paramFFSConnectionHolder == null)
    {
      paramProcessingWindowList.setStatusCode(16000);
      str2 = "FFSConnectionHolder object  is null";
      paramProcessingWindowList.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return paramProcessingWindowList;
    }
    arrayOfString1 = paramProcessingWindowList.getFIId();
    if ((arrayOfString1 == null) || (arrayOfString1.length == 0))
    {
      paramProcessingWindowList.setStatusCode(16010);
      str2 = "FIID of ProcessingWindowList  contains null ";
      paramProcessingWindowList.setStatusMsg(str2);
      FFSDebug.log(str1, str2, 0);
      return paramProcessingWindowList;
    }
    localStringBuffer = new StringBuffer();
    localArrayList1 = new ArrayList();
    localStringBuffer.append("SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow");
    int i;
    if (arrayOfString1.length == 1)
    {
      localStringBuffer.append(" WHERE FIID = ?");
      localArrayList1.add(arrayOfString1[0]);
    }
    else
    {
      localStringBuffer.append(" AND FIID IN ('");
      localStringBuffer.append(arrayOfString1[0]);
      localStringBuffer.append("'");
      for (i = 1; i < arrayOfString1.length; i++)
      {
        localStringBuffer.append(", '");
        localStringBuffer.append(arrayOfString1[i]);
        localStringBuffer.append("'");
      }
      localStringBuffer.append(")");
    }
    arrayOfString2 = paramProcessingWindowList.getCustomerId();
    if ((arrayOfString2 == null) || (arrayOfString2.length == 0))
    {
      localStringBuffer.append(" AND CustomerID is NULL ");
    }
    else if (arrayOfString2.length == 1)
    {
      localStringBuffer.append(" AND CustomerID = ? ");
      localArrayList1.add(arrayOfString2[0]);
    }
    else
    {
      localStringBuffer.append(" AND CustomerID IN ('");
      localStringBuffer.append(arrayOfString2[0]);
      localStringBuffer.append("'");
      for (i = 1; i < arrayOfString2.length; i++)
      {
        localStringBuffer.append(", '");
        localStringBuffer.append(arrayOfString2[i]);
        localStringBuffer.append("'");
      }
      localStringBuffer.append(")");
    }
    arrayOfString3 = paramProcessingWindowList.getPmtType();
    if ((arrayOfString3 != null) && (arrayOfString3.length > 0)) {
      if (arrayOfString3.length == 1)
      {
        localStringBuffer.append(" AND PmtType = ? ");
        localArrayList1.add(arrayOfString3[0].toUpperCase());
      }
      else
      {
        localStringBuffer.append(" AND PmtType IN ('");
        localStringBuffer.append(arrayOfString3[0].toUpperCase());
        localStringBuffer.append("'");
        for (i = 1; i < arrayOfString3.length; i++)
        {
          localStringBuffer.append(", '");
          localStringBuffer.append(arrayOfString3[i].toUpperCase());
          localStringBuffer.append("'");
        }
        localStringBuffer.append(")");
      }
    }
    arrayOfString4 = paramProcessingWindowList.getPmtSubType();
    if ((arrayOfString4 != null) && (arrayOfString4.length > 0) && (arrayOfString4[0] != null) && (arrayOfString4[0].length() > 0)) {
      if (arrayOfString4.length == 1)
      {
        localStringBuffer.append(" AND PmtSubType = ? ");
        localArrayList1.add(arrayOfString4[0].toUpperCase());
      }
      else
      {
        localStringBuffer.append(" AND PmtSubType IN ('");
        localStringBuffer.append(arrayOfString4[0].toUpperCase());
        localStringBuffer.append("'");
        for (i = 1; i < arrayOfString4.length; i++)
        {
          localStringBuffer.append(", '");
          localStringBuffer.append(arrayOfString4[i].toUpperCase());
          localStringBuffer.append("'");
        }
        localStringBuffer.append(")");
      }
    }
    if (localArrayList1.size() > 0) {
      arrayOfObject = localArrayList1.toArray(new Object[0]);
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      localArrayList2 = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        localProcessingWindowInfo = new ProcessingWindowInfo();
        a(localProcessingWindowInfo, localFFSResultSet);
        localArrayList2.add(localProcessingWindowInfo);
      }
      if (localArrayList2.size() == 0)
      {
        paramProcessingWindowList.setStatusCode(16020);
        paramProcessingWindowList.setStatusMsg(" record not found");
      }
      else
      {
        paramProcessingWindowList.setProcessingWindows((ProcessingWindowInfo[])localArrayList2.toArray(new ProcessingWindowInfo[0]));
        paramProcessingWindowList.setStatusCode(0);
        paramProcessingWindowList.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    return paramProcessingWindowList;
  }
  
  private static Object[] jdMethod_do(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str = null;
    str = paramProcessingWindowInfo.getCustomerId();
    if ((str == null) || (str.trim().length() == 0)) {
      str = null;
    }
    Object[] arrayOfObject = { paramProcessingWindowInfo.getWindowId(), paramProcessingWindowInfo.getFIID(), str, paramProcessingWindowInfo.getStartTime(), paramProcessingWindowInfo.getCloseTime(), paramProcessingWindowInfo.getPmtType().toUpperCase(), paramProcessingWindowInfo.getPmtSubType().toUpperCase(), DBUtil.getCurrentLogDate(), paramProcessingWindowInfo.getDescription(), paramProcessingWindowInfo.getSubmittedBy() };
    return arrayOfObject;
  }
  
  private static void jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str1 = "DBPropertyConfig.checkDuplicateProcessingWindow: ";
    FFSDebug.log(str1, "start. " + paramProcessingWindowInfo, 6);
    ArrayList localArrayList = new ArrayList();
    String str2 = null;
    Object[] arrayOfObject = null;
    String str3 = null;
    localArrayList.add(paramProcessingWindowInfo.getFIID());
    localArrayList.add(paramProcessingWindowInfo.getPmtType());
    localArrayList.add(paramProcessingWindowInfo.getPmtSubType());
    localArrayList.add(paramProcessingWindowInfo.getStartTime());
    localArrayList.add(paramProcessingWindowInfo.getCloseTime());
    FFSResultSet localFFSResultSet = null;
    if ((paramProcessingWindowInfo.getCustomerId() != null) && (paramProcessingWindowInfo.getCustomerId().trim().length() > 0))
    {
      str2 = "SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow WHERE FIID = ?  AND PmtType = ?  AND PmtSubType = ?  AND StartTime = ?  AND CloseTime = ?  AND CustomerID = ? ";
      localArrayList.add(paramProcessingWindowInfo.getCustomerId());
    }
    else
    {
      str2 = "SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow WHERE FIID = ?  AND PmtType = ?  AND PmtSubType = ?  AND StartTime = ?  AND CloseTime = ?  AND CustomerID is NULL ";
    }
    arrayOfObject = localArrayList.toArray(new Object[0]);
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        str3 = localFFSResultSet.getColumnString("WindowID");
        if ((paramProcessingWindowInfo.getWindowId() != null) && (str3.equals(paramProcessingWindowInfo.getWindowId())))
        {
          paramProcessingWindowInfo.setStatusCode(0);
          paramProcessingWindowInfo.setStatusMsg("Success");
        }
        else
        {
          paramProcessingWindowInfo.setStatusCode(19700);
          paramProcessingWindowInfo.setStatusMsg("Duplicate record.");
          FFSDebug.log("************", str1, "Duplicate record.", "WindowID=", str3, 0);
        }
      }
      else
      {
        paramProcessingWindowInfo.setStatusCode(0);
        paramProcessingWindowInfo.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      String str4 = str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str6 = str1 + " failed: ";
        String str7 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str6, str7, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str1 = "DBPropertyConfig.validateBusinessProcessingWindow: ";
    FFSDebug.log(str1, "start. " + paramProcessingWindowInfo, 6);
    String str2 = null;
    Object[] arrayOfObject = { paramProcessingWindowInfo.getFIID(), paramProcessingWindowInfo.getPmtType().toUpperCase(), paramProcessingWindowInfo.getPmtSubType().toUpperCase(), paramProcessingWindowInfo.getStartTime(), paramProcessingWindowInfo.getCloseTime() };
    FFSResultSet localFFSResultSet = null;
    str2 = "SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow WHERE FIID = ?  AND CustomerID is NULL  AND PmtType = ?  AND PmtSubType = ?  AND ( StartTime <= ?      AND CloseTime >= ? ) ";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        paramProcessingWindowInfo.setStatusCode(0);
        paramProcessingWindowInfo.setStatusMsg("Success");
      }
      else if (jdMethod_if(paramFFSConnectionHolder, paramProcessingWindowInfo))
      {
        paramProcessingWindowInfo.setStatusCode(19710);
        paramProcessingWindowInfo.setStatusMsg("Invalid business window, it falls outside corresponding bank window.");
      }
      else
      {
        paramProcessingWindowInfo.setStatusCode(0);
        paramProcessingWindowInfo.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1, "done", 6);
  }
  
  private static void jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str1 = "DBPropertyConfig.validateBankProcessingWindow: ";
    FFSDebug.log(str1, "start. " + paramProcessingWindowInfo, 6);
    String str2 = null;
    Object[] arrayOfObject = { paramProcessingWindowInfo.getFIID(), paramProcessingWindowInfo.getPmtType().toUpperCase(), paramProcessingWindowInfo.getPmtSubType().toUpperCase(), paramProcessingWindowInfo.getStartTime(), paramProcessingWindowInfo.getCloseTime(), paramProcessingWindowInfo.getFIID(), paramProcessingWindowInfo.getPmtType().toUpperCase(), paramProcessingWindowInfo.getPmtSubType().toUpperCase(), paramProcessingWindowInfo.getWindowId() };
    FFSResultSet localFFSResultSet = null;
    str2 = "SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow WHERE FIID = ? AND CustomerID is not NULL AND PmtType = ?  AND PmtSubType = ? AND (StartTime < ? OR CloseTime > ?)  AND WindowID not in (SELECT distinct  a.WindowID           from BPW_ProcessWindow a, BPW_ProcessWindow b           WHERE a.FIID= ?  AND a.PmtType = ?           AND a.PmtSubType = ?  AND a.FIID = b.FIID           AND a.PmtType = b.PmtType  AND a.PmtSubType = b.PmtSubType           AND a.CustomerID is not null  AND b.CustomerID is NULL           AND ( (a.StartTime >= b.StartTime)          AND (a.CloseTime <= b.CloseTime) )           AND b.WindowID <> ? ) ";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        paramProcessingWindowInfo.setStatusCode(19720);
        paramProcessingWindowInfo.setStatusMsg("Invalid bank window, some business window falls outside this bank window.");
      }
      else
      {
        paramProcessingWindowInfo.setStatusCode(0);
        paramProcessingWindowInfo.setStatusMsg("Success");
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1, "done", 6);
  }
  
  private static boolean jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str1 = "DBPropertyConfig.existsBankProcessingWindow: ";
    FFSDebug.log(str1, "start. " + paramProcessingWindowInfo, 6);
    boolean bool = false;
    StringBuffer localStringBuffer = null;
    Object[] arrayOfObject = { paramProcessingWindowInfo.getFIID(), paramProcessingWindowInfo.getPmtType().toUpperCase(), paramProcessingWindowInfo.getPmtSubType().toUpperCase() };
    FFSResultSet localFFSResultSet = null;
    localStringBuffer = new StringBuffer();
    localStringBuffer.append("SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow");
    localStringBuffer.append(" WHERE FIID = ? AND PmtType = ? AND PmtSubType = ? ");
    localStringBuffer.append(" AND CustomerID is NULL ");
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        bool = true;
      } else {
        bool = false;
      }
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str4 = str1 + " failed: ";
        String str5 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str4, str5, 0);
      }
    }
    FFSDebug.log(str1, "done", 6);
    return bool;
  }
  
  private static void a(ProcessingWindowInfo paramProcessingWindowInfo, FFSResultSet paramFFSResultSet)
    throws Exception
  {
    paramProcessingWindowInfo.setWindowId(paramFFSResultSet.getColumnString("WindowID"));
    paramProcessingWindowInfo.setFIID(paramFFSResultSet.getColumnString("FIID"));
    paramProcessingWindowInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerID"));
    paramProcessingWindowInfo.setStartTime(paramFFSResultSet.getColumnString("StartTime"));
    paramProcessingWindowInfo.setCloseTime(paramFFSResultSet.getColumnString("CloseTime"));
    paramProcessingWindowInfo.setPmtType(paramFFSResultSet.getColumnString("PmtType"));
    paramProcessingWindowInfo.setPmtSubType(paramFFSResultSet.getColumnString("PmtSubType"));
    paramProcessingWindowInfo.setDateCreate(paramFFSResultSet.getColumnString("DateCreate"));
    paramProcessingWindowInfo.setDescription(paramFFSResultSet.getColumnString("Description"));
    paramProcessingWindowInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
  }
  
  private static void jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str1 = "DBPropertyConfig.checkCustFI ";
    String str2 = paramProcessingWindowInfo.getCustomerId();
    String str3 = paramProcessingWindowInfo.getFIID();
    BPWFIInfo localBPWFIInfo = null;
    CustomerInfo localCustomerInfo = null;
    FFSDebug.log(str1 + ": CustID:", str2, " FIID:", str3, 6);
    try
    {
      localBPWFIInfo = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, str3);
      String str4;
      if (localBPWFIInfo.getFIId() == null)
      {
        str4 = "FIId does not exist :" + str3;
        paramProcessingWindowInfo.setStatusCode(23170);
        paramProcessingWindowInfo.setStatusMsg(str4);
        FFSDebug.log(str1 + "failed, ", str4, 0);
        return;
      }
      if ((str2 != null) && (str2.trim().length() > 0))
      {
        localCustomerInfo = Customer.getCustomerInfo(str2, paramFFSConnectionHolder, paramProcessingWindowInfo);
        if (localCustomerInfo == null)
        {
          str4 = BPWLocaleUtil.getMessage(19130, new String[] { str2 }, "TRANSFER_MESSAGE");
          paramProcessingWindowInfo.setStatusCode(19130);
          paramProcessingWindowInfo.setStatusMsg(str4);
          FFSDebug.log(str1 + "failed, ", str4, 0);
          return;
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str5 = "*** " + str1 + " failed: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5, 0);
      throw new FFSException(localThrowable, str5);
    }
    paramProcessingWindowInfo.setStatusCode(0);
    paramProcessingWindowInfo.setStatusMsg("Success");
  }
  
  private static boolean jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str1 = "DBPropertyConfig.checkOverlap: ";
    FFSDebug.log(str1, "start. " + paramProcessingWindowInfo, 6);
    ArrayList localArrayList = new ArrayList();
    String str2 = null;
    Object[] arrayOfObject = null;
    ProcessingWindowInfo localProcessingWindowInfo = null;
    localArrayList.add(paramProcessingWindowInfo.getFIID());
    localArrayList.add(paramProcessingWindowInfo.getPmtType());
    localArrayList.add(paramProcessingWindowInfo.getPmtSubType());
    FFSResultSet localFFSResultSet = null;
    if ((paramProcessingWindowInfo.getCustomerId() != null) && (paramProcessingWindowInfo.getCustomerId().trim().length() > 0))
    {
      str2 = "SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow WHERE FIID = ?  AND PmtType = ?  AND PmtSubType = ?  AND CustomerID = ? ";
      localArrayList.add(paramProcessingWindowInfo.getCustomerId());
    }
    else
    {
      str2 = "SELECT  WindowID, FIID, CustomerID, StartTime, CloseTime, PmtType, PmtSubType, DateCreate, Description, SubmittedBy  FROM BPW_ProcessWindow WHERE FIID = ?  AND PmtType = ?  AND PmtSubType = ?  AND CustomerID is NULL ";
    }
    arrayOfObject = localArrayList.toArray(new Object[0]);
    try
    {
      localProcessingWindowInfo = new ProcessingWindowInfo();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        a(localProcessingWindowInfo, localFFSResultSet);
        if ((paramProcessingWindowInfo.getWindowId() == null) || (!paramProcessingWindowInfo.getWindowId().equals(localProcessingWindowInfo.getWindowId())))
        {
          FFSDebug.log(str1, "existingWindow:" + localProcessingWindowInfo, 6);
          if (a(localProcessingWindowInfo, paramProcessingWindowInfo))
          {
            paramProcessingWindowInfo.setStatusCode(19750);
            paramProcessingWindowInfo.setStatusMsg("This processing window overlaps an existing processing window of same type.");
            bool = true;
            return bool;
          }
        }
      }
      paramProcessingWindowInfo.setStatusCode(0);
      paramProcessingWindowInfo.setStatusMsg("Success");
      FFSDebug.log(str1, ": done", 6);
      boolean bool = false;
      return bool;
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException)
      {
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
  }
  
  private static boolean a(ProcessingWindowInfo paramProcessingWindowInfo1, ProcessingWindowInfo paramProcessingWindowInfo2)
  {
    String str1 = "DBPropertyConfig.isOverLapping: ";
    FFSDebug.log(str1, "start. ", 6);
    String str2 = paramProcessingWindowInfo1.getStartTime();
    String str3 = paramProcessingWindowInfo1.getCloseTime();
    String str4 = paramProcessingWindowInfo2.getStartTime();
    String str5 = paramProcessingWindowInfo2.getCloseTime();
    FFSDebug.log(str1, "existingWindow.StartTime=" + str2, "existingWindow.CloseTime=" + str3, "processingWindow.StartTime=" + str4, "processingWindow.CloseTime=" + str5, 6);
    return ((str4.compareTo(str2) >= 0) && (str4.compareTo(str3) <= 0)) || ((str4.compareTo(str2) <= 0) && (str5.compareTo(str2) >= 0));
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.DBPropertyConfig
 * JD-Core Version:    0.7.0.1
 */