package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Date;
import java.util.Properties;

public class PropertyConfig
  implements Serializable, DBConsts
{
  public String serverName = "Default Server";
  public boolean NoImmediateTransfer;
  public boolean SupportImmediatePmt;
  public boolean RouteTimedOutToBatch;
  public boolean UseExtdPayeeID;
  public int Retries;
  public int Timeout;
  public String PurgeWakeupTime;
  public int DayToPurgeClosedInstruction;
  public int DayToPurgeEventHistory;
  public int DayToPurgeActivityLog;
  public String MBConn_dbType;
  public String MBConn_host;
  public String MBConn_port;
  public String MBConn_user;
  public String MBConn_passwd;
  public String MBConn_dbname;
  public String MBConn_url;
  public int LogLevel;
  public String WireProcessingMode;
  public int StartPayeeListID;
  public int FundsAllocRetries;
  public int FundsApprovalRetries;
  public int ConcurrentEventProcesses;
  public int MaxPoolSize;
  public int OptimalPoolSize;
  public int DebugLevel;
  public int BatchSize;
  public int MinuteNotToRecover;
  public boolean EnforceEnrollment;
  public boolean SupportCluster = true;
  public long SchWaitTime = 60000L;
  public boolean OneCustPayeeToOnePersonalPayee;
  public boolean EnforcePayment;
  public long AutoStartWaitTime = 60000L;
  public boolean ServletDebug = true;
  public static final String PropsFile = "AutoStartBPW_locale.properties";
  public String BPWServ_userName;
  public String BPWServ_password;
  public String BPWServ_protocol;
  public String BPWServ_host;
  public int BPWServ_port;
  public String BPWServ_nameContext;
  public String BPWServ_jndiName = "bpw.BPWAdminHome";
  public String BPWServ_dbType;
  public String BPWServ_dbHost;
  public String BPWServ_dbPort;
  public String BPWServ_dbUser;
  public String BPWServ_dbPassword;
  public String BPWServ_dbName;
  public int ThreadPoolMaximumSize = 64;
  public int ThreadPoolInitialSize = 16;
  public FFSProperties otherProperties;
  public ServerGeneralConfig clusterConfig = null;
  
  public static PropertyConfig getRegisteredProperties()
  {
    return (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
  }
  
  public PropertyConfig() {}
  
  public PropertyConfig(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "AutoStartBPW_locale.properties";
    }
    try
    {
      Properties localProperties = FFSUtil.getProperties(paramString);
      jdMethod_if(localProperties);
    }
    catch (Exception localException)
    {
      System.out.println("Failed to create PropertyConfig from property File: " + paramString);
      System.out.println("Error: ");
      localException.printStackTrace();
    }
  }
  
  public PropertyConfig(Properties paramProperties)
  {
    jdMethod_if(paramProperties);
  }
  
  public void reset()
  {
    this.serverName = "Default Server";
    this.NoImmediateTransfer = false;
    this.SupportImmediatePmt = false;
    this.RouteTimedOutToBatch = false;
    this.UseExtdPayeeID = false;
    this.Retries = 3;
    this.Timeout = 60000;
    this.PurgeWakeupTime = "12:00 AM";
    this.DayToPurgeClosedInstruction = 30;
    this.DayToPurgeEventHistory = 30;
    this.DayToPurgeActivityLog = 30;
    this.MBConn_dbType = "ASA";
    this.MBConn_host = "localhost";
    this.MBConn_port = "3021";
    this.MBConn_user = null;
    this.MBConn_passwd = null;
    this.MBConn_url = null;
    this.MBConn_dbname = "mbbpw";
    this.LogLevel = 0;
    this.WireProcessingMode = "ASYNC";
    this.StartPayeeListID = 0;
    this.FundsAllocRetries = 3;
    this.FundsApprovalRetries = 3;
    this.ConcurrentEventProcesses = 0;
    this.MaxPoolSize = 128;
    this.OptimalPoolSize = 16;
    this.DebugLevel = 0;
    this.BatchSize = 1000;
    this.MinuteNotToRecover = 240;
    this.EnforceEnrollment = false;
    this.SupportCluster = true;
    this.SchWaitTime = 60000L;
    this.OneCustPayeeToOnePersonalPayee = false;
    this.EnforcePayment = false;
    this.AutoStartWaitTime = 60000L;
    this.ServletDebug = true;
    if (this.otherProperties != null) {
      this.otherProperties.clear();
    } else {
      this.otherProperties = new FFSProperties();
    }
  }
  
  private void jdMethod_if(Properties paramProperties)
  {
    String str = null;
    str = (String)paramProperties.remove("NoImmediateTransfer");
    this.NoImmediateTransfer = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramProperties.remove("OneCustPayeeToOnePersonalPayee");
    this.OneCustPayeeToOnePersonalPayee = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramProperties.remove("EnforcePayment");
    this.EnforcePayment = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramProperties.remove("SupportImmediatePmt");
    this.SupportImmediatePmt = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramProperties.remove("RouteTimedOutToBatch");
    this.RouteTimedOutToBatch = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramProperties.remove("UseExtdPayeeID");
    this.UseExtdPayeeID = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramProperties.remove("StartPayeeListID");
    if ((str == null) || (str.length() <= 0)) {
      this.StartPayeeListID = 0;
    } else {
      this.StartPayeeListID = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("FundsAllocRetries");
    if ((str == null) || (str.length() <= 0)) {
      this.FundsAllocRetries = 3;
    } else {
      this.FundsAllocRetries = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("FundsApprovalRetries");
    if ((str == null) || (str.length() <= 0)) {
      this.FundsApprovalRetries = 3;
    } else {
      this.FundsApprovalRetries = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("ConcurrentEventProcesses");
    if ((str == null) || (str.length() <= 0)) {
      this.ConcurrentEventProcesses = 0;
    } else {
      this.ConcurrentEventProcesses = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("ThreadPoolMaximumSize");
    if ((str == null) || (str.length() <= 0)) {
      this.ThreadPoolMaximumSize = 64;
    } else {
      this.ThreadPoolMaximumSize = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("OptimalPoolSize");
    if ((str == null) || (str.length() <= 0)) {
      this.OptimalPoolSize = 16;
    } else {
      this.OptimalPoolSize = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("MaxPoolSize");
    if ((str == null) || (str.length() <= 0)) {
      this.MaxPoolSize = 128;
    } else {
      this.MaxPoolSize = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("ThreadPoolInitialSize");
    if ((str == null) || (str.length() <= 0)) {
      this.ThreadPoolInitialSize = 32;
    } else {
      this.ThreadPoolInitialSize = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("Retries");
    if ((str == null) || (str.length() <= 0)) {
      this.Retries = 3;
    } else {
      this.Retries = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("Timeout");
    if ((str == null) || (str.length() <= 0)) {
      this.Timeout = 60000;
    } else {
      this.Timeout = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("bpw.wire.processing.mode");
    this.WireProcessingMode = ((str == null) || (str.length() <= 0) ? "ASYNC" : str);
    str = (String)paramProperties.remove("PurgeWakeupTime");
    this.PurgeWakeupTime = ((str == null) || (str.length() <= 0) ? "12:00 AM" : str);
    str = (String)paramProperties.remove("DayToPurgeClosedInstruction");
    if ((str == null) || (str.length() <= 0)) {
      this.DayToPurgeClosedInstruction = 30;
    } else {
      this.DayToPurgeClosedInstruction = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("DayToPurgeEventHistory");
    if ((str == null) || (str.length() <= 0)) {
      this.DayToPurgeEventHistory = 30;
    } else {
      this.DayToPurgeEventHistory = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("DayToPurgeActivityLog");
    if ((str == null) || (str.length() <= 0)) {
      this.DayToPurgeActivityLog = 30;
    } else {
      this.DayToPurgeActivityLog = Integer.parseInt(str);
    }
    str = (String)paramProperties.remove("MBConn_dbType");
    if ((str == null) || (str.length() <= 0)) {
      this.MBConn_dbType = "ASA";
    } else {
      this.MBConn_dbType = str;
    }
    int i = (this.MBConn_dbType.equalsIgnoreCase("ORACLE:oci8")) || (this.MBConn_dbType.equalsIgnoreCase("DB2:app")) || (this.MBConn_dbType.equalsIgnoreCase("DB2:un2")) ? 1 : 0;
    str = (String)paramProperties.remove("MBConn_host");
    if ((str == null) || (str.length() <= 0))
    {
      if (i == 0) {
        this.MBConn_host = "localhost";
      }
    }
    else {
      this.MBConn_host = str;
    }
    str = (String)paramProperties.remove("MBConn_port");
    if ((str == null) || (str.length() <= 0))
    {
      if (i == 0) {
        this.MBConn_port = "3021";
      }
    }
    else {
      this.MBConn_port = str;
    }
    str = (String)paramProperties.remove("MBConn_dbname");
    if ((str == null) || (str.length() <= 0)) {
      this.MBConn_dbname = "mbbpw";
    } else {
      this.MBConn_dbname = str;
    }
    str = (String)paramProperties.remove("MBConn_user");
    this.MBConn_user = str;
    str = (String)paramProperties.remove("MBConn_passwd");
    if ((str != null) && (str.length() > 0)) {
      this.MBConn_passwd = str;
    }
    str = (String)paramProperties.remove("MBConn_url");
    this.MBConn_url = str;
    str = (String)paramProperties.remove("BPW.DB.TYPE");
    if ((str == null) || (str.length() <= 0)) {
      this.BPWServ_dbType = "ASA";
    } else {
      this.BPWServ_dbType = str;
    }
    str = (String)paramProperties.remove("BPW.DB.HOST");
    if ((str == null) || (str.length() <= 0)) {
      this.BPWServ_dbHost = "localhost";
    } else {
      this.BPWServ_dbHost = str;
    }
    str = (String)paramProperties.remove("BPW.DB.PORT");
    this.BPWServ_dbPort = ((str == null) || (str.length() <= 0) ? "3022" : str);
    str = (String)paramProperties.remove("BPW.DB.NAME");
    if ((str == null) || (str.length() <= 0)) {
      this.BPWServ_dbName = "bpw";
    } else {
      this.BPWServ_dbName = str;
    }
    str = (String)paramProperties.remove("BPW.DB.USER");
    this.BPWServ_dbUser = (str == null ? "" : str);
    str = (String)paramProperties.remove("BPW.DB.PASSWORD");
    this.BPWServ_dbPassword = (str == null ? "" : str);
    str = (String)paramProperties.remove("BPWServ_userName");
    this.BPWServ_userName = (str == null ? "" : str);
    str = (String)paramProperties.remove("BPWServ_password");
    this.BPWServ_password = str;
    str = (String)paramProperties.remove("BPWServ_protocol");
    this.BPWServ_protocol = ((str == null) || (str.length() <= 0) ? "iiop" : str);
    str = (String)paramProperties.remove("BPWServ_host");
    this.BPWServ_host = ((str == null) || (str.length() <= 0) ? "localhost" : str);
    str = (String)paramProperties.remove("BPWServ_port");
    this.BPWServ_port = ((str == null) || (str.length() <= 0) ? 900 : Integer.parseInt(str));
    str = (String)paramProperties.remove("BPWServ_nameContext");
    this.BPWServ_nameContext = ((str == null) || (str.length() <= 0) ? "com.ibm.websphere.naming.WsnInitialContextFactory" : str);
    str = (String)paramProperties.remove("BPW.ADMIN.JNDI.NAME");
    this.BPWServ_jndiName = ((str == null) || (str.length() <= 0) ? "bpw.BPWAdminHome" : str);
    str = (String)paramProperties.remove("SchWaitTime");
    if ((str == null) || (str.length() <= 0)) {
      this.SchWaitTime = 60000L;
    } else {
      this.SchWaitTime = Long.parseLong(str);
    }
    str = (String)paramProperties.remove("SupportCluster");
    this.SupportCluster = ((str == null) || (str.length() <= 0) || (str.equalsIgnoreCase("true")));
    str = (String)paramProperties.remove("EnforceEnrollment");
    this.EnforceEnrollment = ((str != null) && (str.equalsIgnoreCase("true")));
    str = (String)paramProperties.remove("LogLevel");
    this.LogLevel = ((str == null) || (str.length() <= 0) ? 0 : Integer.parseInt(str));
    str = (String)paramProperties.remove("DebugLevel");
    this.DebugLevel = ((str == null) || (str.length() <= 0) ? 0 : Integer.parseInt(str));
    str = (String)paramProperties.remove("BatchSize");
    this.BatchSize = ((str == null) || (str.length() <= 0) ? 1000 : Integer.parseInt(str));
    str = (String)paramProperties.remove("MinuteNotToRecover");
    this.MinuteNotToRecover = ((str == null) || (str.length() <= 0) ? 240 : Integer.parseInt(str));
  }
  
  public void toFile(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      paramString = "AutoStartBPW_locale.properties";
    }
    try
    {
      Properties localProperties = new Properties();
      localProperties.setProperty("NoImmediateTransfer", "" + this.NoImmediateTransfer);
      String str = new Date().toString();
      FFSUtil.saveProperties(localProperties, paramString, str);
    }
    catch (Exception localException)
    {
      System.out.println("Failed to save PropertyConfig to property File: " + paramString);
      System.out.println("Error: ");
      localException.printStackTrace();
    }
  }
  
  public boolean getNoImmediateTransfer()
  {
    return this.NoImmediateTransfer;
  }
  
  public boolean getOneCustPayeeToOnePersonalPayee()
  {
    return this.OneCustPayeeToOnePersonalPayee;
  }
  
  public boolean getSupportImmediatePmt()
  {
    return this.SupportImmediatePmt;
  }
  
  public boolean getRouteTimedOutToBatch()
  {
    return this.RouteTimedOutToBatch;
  }
  
  public boolean getUseExtdPayeeID()
  {
    return this.UseExtdPayeeID;
  }
  
  public int getRetries()
  {
    return this.Retries;
  }
  
  public int getTimeout()
  {
    return this.Timeout;
  }
  
  public String getPurgeWakeupTime()
  {
    return this.PurgeWakeupTime;
  }
  
  public String getWireProcessingMode()
  {
    return this.WireProcessingMode;
  }
  
  public int getDayToPurgeClosedInstruction()
  {
    return this.DayToPurgeClosedInstruction;
  }
  
  public int getDayToPurgeEventHistory()
  {
    return this.DayToPurgeEventHistory;
  }
  
  public int getDayToPurgeActivityLog()
  {
    return this.DayToPurgeActivityLog;
  }
  
  public String getDBType()
  {
    return this.MBConn_dbType;
  }
  
  public String getHost()
  {
    return this.MBConn_host;
  }
  
  public String getPort()
  {
    return this.MBConn_port;
  }
  
  public String getDBName()
  {
    return this.MBConn_dbname;
  }
  
  public String getUser()
  {
    return this.MBConn_user;
  }
  
  public String getPassword()
  {
    return this.MBConn_passwd;
  }
  
  public String getServerUserName()
  {
    return this.BPWServ_userName;
  }
  
  public String getServerPassword()
  {
    return this.BPWServ_password;
  }
  
  public String getServerProtocol()
  {
    return this.BPWServ_protocol;
  }
  
  public String getServerHost()
  {
    return this.BPWServ_host;
  }
  
  public int getServerPort()
  {
    return this.BPWServ_port;
  }
  
  public String getNameContext()
  {
    return this.BPWServ_nameContext;
  }
  
  public int getLogLevel()
  {
    return this.LogLevel;
  }
  
  public int getDebugLevel()
  {
    return this.DebugLevel;
  }
  
  public void setDebugLevel(int paramInt)
  {
    this.DebugLevel = paramInt;
  }
  
  public int getBatchSize()
  {
    return this.BatchSize;
  }
  
  public int getMinuteNotToRecover()
  {
    return this.MinuteNotToRecover;
  }
  
  public boolean getEnforceEnrollment()
  {
    return this.EnforceEnrollment;
  }
  
  public void setEnforceEnrollment(boolean paramBoolean)
  {
    this.EnforceEnrollment = paramBoolean;
  }
  
  public boolean getEnforcePayment()
  {
    return this.EnforcePayment;
  }
  
  public void setEnforcePayment(boolean paramBoolean)
  {
    this.EnforcePayment = paramBoolean;
  }
  
  public boolean getSupportCluster()
  {
    return this.SupportCluster;
  }
  
  public void setSupportCluster(boolean paramBoolean)
  {
    this.SupportCluster = paramBoolean;
  }
  
  public int getStartPayeeListID()
  {
    return this.StartPayeeListID;
  }
  
  public int getFundsAllocRetries()
  {
    return this.FundsAllocRetries;
  }
  
  public int getFundsApprovalRetries()
  {
    return this.FundsApprovalRetries;
  }
  
  public int getConcurrentEventProcesses()
  {
    return this.ConcurrentEventProcesses;
  }
  
  public int getMaxPoolSize()
  {
    return this.MaxPoolSize;
  }
  
  public int getOptimalPoolSize()
  {
    return this.OptimalPoolSize;
  }
  
  public int getThreadPoolMaximumSize()
  {
    return this.ThreadPoolMaximumSize;
  }
  
  public int getThreadPoolInitialSize()
  {
    return this.ThreadPoolInitialSize;
  }
  
  public void setNoImmediateTransfer(boolean paramBoolean)
  {
    this.NoImmediateTransfer = paramBoolean;
  }
  
  public void setOneCustPayeeToOnePersonalPayee(boolean paramBoolean)
  {
    this.OneCustPayeeToOnePersonalPayee = paramBoolean;
  }
  
  public void setSupportImmediatePmt(boolean paramBoolean)
  {
    this.SupportImmediatePmt = paramBoolean;
  }
  
  public void setRouteTimedOutToBatch(boolean paramBoolean)
  {
    this.RouteTimedOutToBatch = paramBoolean;
  }
  
  public void setUseExtdPayeeID(boolean paramBoolean)
  {
    this.UseExtdPayeeID = paramBoolean;
  }
  
  public void setRetries(int paramInt)
  {
    this.Retries = paramInt;
  }
  
  public void setTimeout(int paramInt)
  {
    this.Timeout = paramInt;
  }
  
  public void setPurgeWakeupTime(String paramString)
  {
    this.PurgeWakeupTime = paramString;
  }
  
  public void setWireProcessingMode(String paramString)
  {
    this.WireProcessingMode = paramString;
  }
  
  public void setDayToPurgeClosedInstruction(int paramInt)
    throws Exception
  {
    if (paramInt > 0) {
      this.DayToPurgeClosedInstruction = paramInt;
    } else {
      throw new Exception("PropertyConfig.setDayToPurgeClosedInstruction: Negative value is not allowed.");
    }
  }
  
  public void setDayToPurgeEventHistory(int paramInt)
    throws Exception
  {
    if (paramInt > 0) {
      this.DayToPurgeEventHistory = paramInt;
    } else {
      throw new Exception("PropertyConfig.setDayToPurgeEventHistory: Negative value is not allowed.");
    }
  }
  
  public void setDayToPurgeActivityLog(int paramInt)
    throws Exception
  {
    if (paramInt > 0) {
      this.DayToPurgeActivityLog = paramInt;
    } else {
      throw new Exception("PropertyConfig.setDayToPurgeActivityLog: Negative value is not allowed.");
    }
  }
  
  public void setDBType(String paramString)
  {
    this.MBConn_dbType = paramString;
  }
  
  public void setHost(String paramString)
  {
    this.MBConn_host = paramString;
  }
  
  public void setPort(String paramString)
  {
    this.MBConn_port = paramString;
  }
  
  public void setDatabaseName(String paramString)
  {
    this.MBConn_dbname = paramString;
  }
  
  public void setUser(String paramString)
  {
    this.MBConn_user = paramString;
  }
  
  public void setPassword(String paramString)
  {
    this.MBConn_passwd = paramString;
  }
  
  public void setServerUserName(String paramString)
  {
    this.BPWServ_userName = paramString;
  }
  
  public void setServerPassword(String paramString)
  {
    this.BPWServ_password = paramString;
  }
  
  public void setServerProtocol(String paramString)
  {
    this.BPWServ_protocol = paramString;
  }
  
  public void setServerHost(String paramString)
  {
    this.BPWServ_host = paramString;
  }
  
  public void setServerPort(int paramInt)
  {
    this.BPWServ_port = paramInt;
  }
  
  public void setNameContext(String paramString)
  {
    this.BPWServ_nameContext = paramString;
  }
  
  public void setLogLevel(int paramInt)
  {
    this.LogLevel = paramInt;
  }
  
  public void setBatchSize(int paramInt)
  {
    this.BatchSize = paramInt;
  }
  
  public void setMinuteNotToRecover(int paramInt)
  {
    this.MinuteNotToRecover = paramInt;
  }
  
  public void setStartPayeeListID(int paramInt)
  {
    this.StartPayeeListID = paramInt;
  }
  
  public void setFundsAllocRetries(int paramInt)
  {
    this.FundsAllocRetries = paramInt;
  }
  
  public void setFundsApprovalRetries(int paramInt)
  {
    this.FundsApprovalRetries = paramInt;
  }
  
  public void setConcurrentEventProcesses(int paramInt)
  {
    this.ConcurrentEventProcesses = paramInt;
  }
  
  public void setMaxPoolSize(int paramInt)
  {
    this.MaxPoolSize = paramInt;
  }
  
  public void setOptimalPoolSize(int paramInt)
  {
    this.OptimalPoolSize = paramInt;
  }
  
  public void setThreadPoolMaximumSize(int paramInt)
  {
    this.ThreadPoolMaximumSize = paramInt;
  }
  
  public void setThreadPoolInitialSize(int paramInt)
  {
    this.ThreadPoolInitialSize = paramInt;
  }
  
  public boolean getServletDebug()
  {
    return this.ServletDebug;
  }
  
  public void setServletDebug(boolean paramBoolean)
  {
    this.ServletDebug = paramBoolean;
  }
  
  public long getSchWaitTime()
  {
    return this.SchWaitTime;
  }
  
  public void setSchWaitTime(long paramLong)
  {
    this.SchWaitTime = paramLong;
  }
  
  public long getAutoStartWaitTime()
  {
    return this.AutoStartWaitTime;
  }
  
  public void setAutoStartWaitTime(long paramLong)
  {
    this.AutoStartWaitTime = paramLong;
  }
  
  public String getDefaultValue(String paramString)
  {
    String str = null;
    if (paramString.equals("NoImmediateTransfer")) {
      str = "false";
    }
    if (paramString.equals("OneCustPayeeToOnePersonalPayee")) {
      str = "false";
    }
    if (paramString.equals("EnforcePayment")) {
      str = "false";
    }
    if (paramString.equals("SupportImmediatePmt")) {
      str = "false";
    }
    if (paramString.equals("UseExtdPayeeID")) {
      str = "false";
    }
    if (paramString.equals("StartPayeeListID")) {
      str = Integer.toString(this.StartPayeeListID);
    }
    if (paramString.equals("FundsAllocRetries")) {
      str = Integer.toString(this.FundsAllocRetries);
    }
    if (paramString.equals("FundsApprovalRetries")) {
      str = Integer.toString(this.FundsApprovalRetries);
    }
    if (paramString.equals("ConcurrentEventProcesses")) {
      str = Integer.toString(this.ConcurrentEventProcesses);
    }
    if (paramString.equals("MaxPoolSize")) {
      str = Integer.toString(this.MaxPoolSize);
    }
    if (paramString.equals("OptimalPoolSize")) {
      str = Integer.toString(this.OptimalPoolSize);
    }
    if (paramString.equals("ThreadPoolMaximumSize")) {
      str = Integer.toString(this.ThreadPoolMaximumSize);
    }
    if (paramString.equals("ThreadPoolInitialSize")) {
      str = Integer.toString(this.ThreadPoolInitialSize);
    }
    if (paramString.equals("Retries")) {
      str = Integer.toString(this.Retries);
    }
    if (paramString.equals("Timeout")) {
      str = Integer.toString(this.Timeout);
    }
    if (paramString.equals("PurgeWakeupTime")) {
      str = this.PurgeWakeupTime;
    }
    if (paramString.equals("bpw.wire.processing.mode")) {
      str = this.WireProcessingMode;
    }
    if (paramString.equals("DayToPurgeClosedInstruction")) {
      str = Integer.toString(this.DayToPurgeClosedInstruction);
    }
    if (paramString.equals("DayToPurgeEventHistory")) {
      str = Integer.toString(this.DayToPurgeEventHistory);
    }
    if (paramString.equals("DayToPurgeActivityLog")) {
      str = Integer.toString(this.DayToPurgeActivityLog);
    }
    if (paramString.equals("MBConn_dbType")) {
      str = this.MBConn_dbType;
    }
    if (paramString.equals("MBConn_host")) {
      str = this.MBConn_host;
    }
    if (paramString.equals("MBConn_port")) {
      str = this.MBConn_port;
    }
    if (paramString.equals("MBConn_dbname")) {
      str = this.MBConn_dbname;
    }
    if (paramString.equals("MBConn_user")) {
      str = this.MBConn_user;
    }
    if (paramString.equals("MBConn_passwd")) {
      str = this.MBConn_passwd;
    }
    if (paramString.equals("MBConn_url")) {
      str = this.MBConn_url;
    }
    if (paramString.equals("BPWServ_userName")) {
      str = this.BPWServ_userName;
    }
    if (paramString.equals("BPWServ_password")) {
      str = this.BPWServ_password;
    }
    if (paramString.equals("BPWServ_protocol")) {
      str = this.BPWServ_protocol;
    }
    if (paramString.equals("BPWServ_host")) {
      str = this.BPWServ_host;
    }
    if (paramString.equals("BPWServ_port")) {
      str = Integer.toString(this.BPWServ_port);
    }
    if (paramString.equals("BPWServ_nameContext")) {
      str = this.BPWServ_nameContext;
    }
    if (paramString.equals("LogLevel")) {
      str = Integer.toString(this.LogLevel);
    }
    if (paramString.equals("DebugLevel")) {
      str = Integer.toString(this.DebugLevel);
    }
    if (paramString.equals("BatchSize")) {
      str = Integer.toString(this.BatchSize);
    }
    if (paramString.equals("MinuteNotToRecover")) {
      str = Integer.toString(this.MinuteNotToRecover);
    }
    return str;
  }
  
  public void print()
  {
    System.out.println("NoImmediateTransfer=" + this.NoImmediateTransfer);
    System.out.println("OneCustPayeeToOnePersonalPayee=" + this.OneCustPayeeToOnePersonalPayee);
    System.out.println("EnforcePayment=" + this.EnforcePayment);
    System.out.println("SupportImmediatePmt=" + this.SupportImmediatePmt);
    System.out.println("RouteTimedOutToBatch=" + this.RouteTimedOutToBatch);
    System.out.println("UseExtdPayeeID=" + this.UseExtdPayeeID);
    System.out.println("Retries=" + this.Retries);
    System.out.println("Timeout=" + this.Timeout);
    System.out.println("PurgeWakeupTime=" + this.PurgeWakeupTime);
    System.out.println("DayToPurgeClosedInstruction=" + this.DayToPurgeClosedInstruction);
    System.out.println("DayToPurgeEventHistory=" + this.DayToPurgeEventHistory);
    System.out.println("DayToPurgeActivityLog=" + this.DayToPurgeActivityLog);
    System.out.println("MBConn_dbType=" + this.MBConn_dbType);
    System.out.println("MBConn_host=" + this.MBConn_host);
    System.out.println("MBConn_port=" + this.MBConn_port);
    System.out.println("MBConn_dbname=" + this.MBConn_dbname);
    System.out.println("LogLevel=" + this.LogLevel);
    System.out.println("StartPayeeListID=" + this.StartPayeeListID);
    System.out.println("FundsAllocRetries=" + this.FundsAllocRetries);
    System.out.println("FundsApprovalRetries=" + this.FundsApprovalRetries);
    System.out.println("ConcurrentEventProcesses=" + this.ConcurrentEventProcesses);
    System.out.println("MaxPoolSize=" + this.MaxPoolSize);
    System.out.println("OptimalPoolSize=" + this.OptimalPoolSize);
    System.out.println("DebugLevel=" + this.DebugLevel);
    System.out.println("BatchSize=" + this.BatchSize);
    System.out.println("MinuteNotToRecover=" + this.MinuteNotToRecover);
    System.out.println("EnforceEnrollment=" + this.EnforceEnrollment);
    System.out.println("SupportCluster=" + this.SupportCluster);
    System.out.println("SchWaitTime=" + this.SchWaitTime);
    System.out.println("wireProcessingMode=" + this.WireProcessingMode);
    System.out.println("AutoStartWaitTime=" + this.AutoStartWaitTime);
    System.out.println("BPWServ_protocol=" + this.BPWServ_protocol);
    System.out.println("BPWServ_host=" + this.BPWServ_host);
    System.out.println("BPWServ_port=" + this.BPWServ_port);
    System.out.println("BPWServ_nameContext=" + this.BPWServ_nameContext);
    System.out.println("BPWServ_jndiName=" + this.BPWServ_jndiName);
    System.out.println("BPWServ_dbType=" + this.BPWServ_dbType);
    System.out.println("BPWServ_dbHost=" + this.BPWServ_dbHost);
    System.out.println("BPWServ_dbPort=" + this.BPWServ_dbPort);
    System.out.println("BPWServ_dbName=" + this.BPWServ_dbName);
  }
  
  public void print(PrintWriter paramPrintWriter)
  {
    paramPrintWriter.println("NoImmediateTransfer=" + this.NoImmediateTransfer);
    paramPrintWriter.println("OneCustPayeeToOnePersonalPayee=" + this.OneCustPayeeToOnePersonalPayee);
    paramPrintWriter.println("EnforcePayment=" + this.EnforcePayment);
    paramPrintWriter.println("SupportImmediatePmt=" + this.SupportImmediatePmt);
    paramPrintWriter.println("RouteTimedOutToBatch=" + this.RouteTimedOutToBatch);
    paramPrintWriter.println("UseExtdPayeeID=" + this.UseExtdPayeeID);
    paramPrintWriter.println("Retries=" + this.Retries);
    paramPrintWriter.println("Timeout=" + this.Timeout);
    paramPrintWriter.println("PurgeWakeupTime=" + this.PurgeWakeupTime);
    paramPrintWriter.println("DayToPurgeClosedInstruction=" + this.DayToPurgeClosedInstruction);
    paramPrintWriter.println("DayToPurgeEventHistory=" + this.DayToPurgeEventHistory);
    paramPrintWriter.println("DayToPurgeActivityLog=" + this.DayToPurgeActivityLog);
    paramPrintWriter.println("MBConn_dbType=" + this.MBConn_dbType);
    paramPrintWriter.println("MBConn_host=" + this.MBConn_host);
    paramPrintWriter.println("MBConn_port=" + this.MBConn_port);
    paramPrintWriter.println("MBConn_dbname=" + this.MBConn_dbname);
    paramPrintWriter.println("LogLevel=" + this.LogLevel);
    paramPrintWriter.println("StartPayeeListID=" + this.StartPayeeListID);
    paramPrintWriter.println("FundsAllocRetries=" + this.FundsAllocRetries);
    paramPrintWriter.println("FundsApprovalRetries=" + this.FundsApprovalRetries);
    paramPrintWriter.println("ConcurrentEventProcesses=" + this.ConcurrentEventProcesses);
    paramPrintWriter.println("MaxPoolSize=" + this.MaxPoolSize);
    paramPrintWriter.println("OptimalPoolSize=" + this.OptimalPoolSize);
    paramPrintWriter.println("DebugLevel=" + this.DebugLevel);
    paramPrintWriter.println("BatchSize=" + this.BatchSize);
    paramPrintWriter.println("MinuteNotToRecover=" + this.MinuteNotToRecover);
    paramPrintWriter.println("EnforceEnrollment=" + this.EnforceEnrollment);
    paramPrintWriter.println("SupportCluster=" + this.SupportCluster);
    paramPrintWriter.println("SchWaitTime=" + this.SchWaitTime);
    paramPrintWriter.println("wireProcessingMode=" + this.WireProcessingMode);
    paramPrintWriter.println("AutoStartWaitTime=" + this.AutoStartWaitTime);
    paramPrintWriter.println("BPWServ_protocol=" + this.BPWServ_protocol);
    paramPrintWriter.println("BPWServ_host=" + this.BPWServ_host);
    paramPrintWriter.println("BPWServ_port=" + this.BPWServ_port);
    paramPrintWriter.println("BPWServ_nameContext=" + this.BPWServ_nameContext);
    paramPrintWriter.println("BPWServ_jndiName=" + this.BPWServ_jndiName);
    paramPrintWriter.println("BPWServ_dbType=" + this.BPWServ_dbType);
    paramPrintWriter.println("BPWServ_dbHost=" + this.BPWServ_dbHost);
    paramPrintWriter.println("BPWServ_dbPort=" + this.BPWServ_dbPort);
    paramPrintWriter.println("BPWServ_dbName=" + this.BPWServ_dbName);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.PropertyConfig
 * JD-Core Version:    0.7.0.1
 */