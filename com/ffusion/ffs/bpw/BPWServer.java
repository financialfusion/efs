package com.ffusion.ffs.bpw;

import com.ffusion.ffs.bpw.achagent.ACHAgent;
import com.ffusion.ffs.bpw.adminEJB.IBPWAdmin;
import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.custimpl.BankSimulator;
import com.ffusion.ffs.bpw.custimpl.transfers.TransferCalloutHandler;
import com.ffusion.ffs.bpw.db.Bank;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.fulfill.FulfillAgent;
import com.ffusion.ffs.bpw.interfaces.BPWStatistics;
import com.ffusion.ffs.bpw.interfaces.CutOffActivityInfoList;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfoList;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.ScheduleActivityList;
import com.ffusion.ffs.bpw.interfaces.ScheduleCategoryInfo;
import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
import com.ffusion.ffs.bpw.interfaces.TransferCalloutHandlerBase;
import com.ffusion.ffs.bpw.master.BPWAdminBackend;
import com.ffusion.ffs.bpw.master.BPWEngine;
import com.ffusion.ffs.bpw.master.LimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.master.XferProcessor;
import com.ffusion.ffs.bpw.purge.PurgeProcessor;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.util.BPWConfigWrapper;
import com.ffusion.ffs.bpw.util.BPWEJBUtil;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.config.ConnPoolInfo;
import com.ffusion.ffs.config.MBConfigInfo;
import com.ffusion.ffs.db.ConnectionPool;
import com.ffusion.ffs.db.ConnectionPoolFactory;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSDBProperties;
import com.ffusion.ffs.db.FFSDBUtils;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.NamePlace;
import com.ffusion.ffs.scheduling.Scheduler;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.fx.FXUtil;
import com.ffusion.util.settings.SystemSettings;
import java.io.PrintStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class BPWServer
{
  private static final String jdField_do = "AutoStartBPW_locale.properties";
  private static BPWServer jdField_try = new BPWServer();
  private static int jdField_for = 0;
  private boolean a = false;
  private Thread jdField_new = null;
  private static FFSProperties jdField_int = null;
  private static final Object jdField_if = new Object();
  
  public void start(FFSProperties paramFFSProperties, PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
    throws FFSException
  {
    if (jdField_for == 1) {
      throw new FFSException("BPWServer already started!");
    }
    jdField_int = paramFFSProperties;
    FFSDebug.setDebugLevel(paramPropertyConfig.DebugLevel);
    FFSDebug.console("Starting BPWServer....");
    FFSDebug.log("Starting BPWServer....");
    Object localObject1;
    Object localObject2;
    if (FFSDebug.checkLogLevel(2) == true)
    {
      paramPropertyConfig.print();
      FFSDebug.console("StartDBPool: printing FFSProperties");
      localObject1 = paramFFSProperties.propertyNames();
      while (((Enumeration)localObject1).hasMoreElements())
      {
        localObject2 = (String)((Enumeration)localObject1).nextElement();
        FFSDebug.console((String)localObject2 + "=" + paramFFSProperties.getProperty((String)localObject2));
      }
      FFSDebug.console("");
    }
    FFSDebug.log("\n\tProduct: " + BPWVersion.getProductVersionString() + "\n");
    FFSDebug.console("\n\tProduct: " + BPWVersion.getProductVersionString() + "\n");
    try
    {
      FFSDebug.register(2);
      FFSDebug.console(" ==== Log writer started, Server log file is FFusion.log ....");
      FFSDebug.log(" ==== Log writer started, Server log file is FFusion.log ....");
      FFSRegistry.bind("FFSPROPS", paramFFSProperties);
      a(paramArrayOfInstructionType);
      BPWRegistryUtil.setInstructionTypes(paramArrayOfInstructionType);
      BPWRegistryUtil.setFFSProperties(paramFFSProperties);
      a("BPWDBPOOL", paramFFSProperties, paramPropertyConfig);
      FFSDebug.console(" ==== DBPool started....");
      FFSDebug.log(" ==== DBPool started....");
      jdMethod_case();
      a();
      localObject1 = BPWUtil.getBPWServerDBProperties(paramPropertyConfig, paramFFSProperties);
      BPWAdminBackend.setDBProperties((FFSDBProperties)localObject1);
      localObject2 = BPWAdminBackend.getAllFulfillmentInfo();
      BPWRegistryUtil.setFulfillmentInfo((FulfillmentInfo[])localObject2);
      BPWRegistryUtil.setAllBankInfo(Bank.getAllBankInfo());
      FFSDebug.console(" ==== Bank registration info loaded....");
      FFSDebug.log(" ==== Bank registration info loaded....");
      jdMethod_void("BPWMSGBROKER");
      FFSDebug.console(" ==== BPWMsgBroker started....");
      FFSDebug.log(" ==== BPWMsgBroker started....");
      jdField_try("BPWACHAGENT");
      FFSDebug.console(" ==== BPWACHAgent started....");
      FFSDebug.log(" ==== BPWACHAgent started....");
      jdMethod_byte("FULFILLAGENT");
      FFSDebug.console(" ==== Fulfillment Agent started....");
      FFSDebug.log(" ==== Fulfillment Agent started....");
      try
      {
        jdMethod_char();
      }
      catch (Exception localException2)
      {
        String str = "Error while initializing BPWConfigWrapper";
        throw new FFSException(localException2, str);
      }
      startLimitCheckApprovalProcessor("LIMITCHECKAPPROVALPROCESSOR");
      FXUtil.initialize(null);
      startEngine("BPWENGINE");
      FFSDebug.console(" ==== Server engine started....");
      FFSDebug.log(" ==== Server engine started....");
      jdField_for("AUDITAGENT");
      FFSDebug.console(" ==== Audit Agent started....");
      FFSDebug.log(" ==== Audit Agent started....");
      jdField_int();
      jdField_for();
      jdMethod_else("SCHEDULER");
      c("DBCONNCACHE");
      FFSDebug.console(" ==== DBConnCache set....");
      FFSDebug.log(" ==== DBConnCache set....");
      jdField_int("TRANSFERCALLOUTHANDLER");
    }
    catch (Throwable localThrowable)
    {
      jdField_for = 0;
      FFSDebug.log(localThrowable, "BPWServer cannot be started: ");
      FFSDebug.console("BPWServer cannot be started: " + localThrowable.toString());
      try
      {
        jdMethod_case("SCHEDULER");
      }
      catch (Exception localException1) {}
      throw new FFSException(FFSDebug.stackTrace(localThrowable));
    }
    jdField_for = 1;
    FFSDebug.console("BPWServer started....");
    FFSDebug.log("BPWServer started....");
  }
  
  private void a()
  {
    FFSConnection localFFSConnection = null;
    try
    {
      localFFSConnection = DBUtil.getConnection();
      if (localFFSConnection == null)
      {
        String str1 = "initializeAcctSettings : Cannot get DB Connection.";
        FFSDebug.log(str1, 0);
        throw new FFSException(str1);
      }
      SystemSettings.initialize(localFFSConnection.getConnection());
      localFFSConnection.commit();
      FFSDebug.console(" ==== Account Settings loaded....");
      FFSDebug.log(" ==== Account Settings loaded....");
    }
    catch (Exception localException)
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      String str2 = localPropertyConfig.otherProperties.getProperty("bpw.server.standalone", "N");
      if ((str2 != null) && (str2.equalsIgnoreCase("Y")))
      {
        if (localFFSConnection != null) {
          localFFSConnection.rollback();
        }
        return;
      }
      localException.printStackTrace();
      if (localFFSConnection != null) {
        localFFSConnection.rollback();
      }
      FFSDebug.log("*** Fail to load Account Settings:" + localException.toString());
      FFSDebug.console("***  Fail to load Account Settings:" + localException.toString());
    }
    finally
    {
      if (localFFSConnection != null) {
        DBUtil.freeConnection(localFFSConnection);
      }
    }
  }
  
  public synchronized boolean stop()
    throws Exception
  {
    FFSDebug.console("Stopping BPWServer....");
    FFSDebug.log("Stopping BPWServer....");
    jdField_for = 0;
    try
    {
      SmartCalendar.reset();
      jdMethod_case("SCHEDULER");
      jdMethod_char("BPWMSGBROKER");
      FFSDebug.console(" ==== BPWMsgBroker stopped....");
      FFSDebug.log(" ==== BPWMsgBroker stopped....");
      jdMethod_byte();
      FFSDebug.console(" ==== BPWConfigWrapper removed....");
      FFSDebug.log(" ==== BPWConfigWrapper removed....");
      stopEngine("BPWENGINE");
      FFSDebug.console(" ==== Server engine stopped....");
      FFSDebug.log(" ==== Server engine stopped....");
      jdField_do("AUDITAGENT");
      FFSDebug.console(" ==== Audit Agent stopped....");
      FFSDebug.log(" ==== Audit Agent stopped....");
      jdMethod_null("FULFILLAGENT");
      FFSDebug.console(" ==== Fulfillment Agent stopped....");
      FFSDebug.log(" ==== Fulfillment Agent stopped....");
      jdField_new("DBCONNCACHE");
      FFSDebug.console(" ==== DBConnCache removed....");
      FFSDebug.log(" ==== DBConnCache removed....");
      a("BPWDBPOOL");
      FFSDebug.console(" ==== DBPool stopped....");
      FFSDebug.log(" ==== DBPool stopped....");
      jdField_new();
      stopLimitCheckApprovalProcessor("LIMITCHECKAPPROVALPROCESSOR");
      jdField_if("TRANSFERCALLOUTHANDLER");
      jdField_do();
      String[] arrayOfString = { "BPWENGINE", "FULFILLAGENT", "BPWDBPOOL", "SCHEDULER", "AUDITAGENT", "PURGEENGINE", "PROPERTYCONFIG", "BPWMSGBROKER", "FFSPROPS" };
      for (int i = 0; i < arrayOfString.length; i++) {
        try
        {
          FFSRegistry.unbind(arrayOfString[i]);
        }
        catch (Exception localException2)
        {
          FFSDebug.log("Failed to clean obiect: " + arrayOfString[i]);
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.console("BPWServer can not be stoped:" + localException1.toString());
      FFSDebug.log(localException1, "BPWServer can not be stoped:");
      throw new Exception(localException1.toString());
    }
    FFSDebug.console("BPWServer stopped....");
    FFSDebug.log("BPWServer stopped....");
    return true;
  }
  
  public static synchronized BPWServer getInstance()
  {
    return jdField_try;
  }
  
  private void jdField_for() {}
  
  private void a(String paramString, FFSProperties paramFFSProperties, PropertyConfig paramPropertyConfig)
    throws Exception
  {
    try
    {
      FFSDebug.console(" ==== Read Configuration data....");
      registerPropertyConfig(paramPropertyConfig);
      FFSDBProperties localFFSDBProperties = BPWUtil.getBPWServerDBProperties(paramPropertyConfig, paramFFSProperties);
      int i = paramPropertyConfig.getDebugLevel();
      FFSDebug.setDebugLevel(i);
      localFFSDBProperties._connInfo._maxPoolSize = paramPropertyConfig.getMaxPoolSize();
      localFFSDBProperties._connInfo._optimalPoolSize = paramPropertyConfig.getOptimalPoolSize();
      localFFSDBProperties._connInfo._dbConnInfo._connectionAutoCommit = false;
      localFFSDBProperties._connInfo._dbConnInfo._connectionTimeout = 60L;
      FFSDebug.log("StartDBPool:maxPoolSize=" + localFFSDBProperties._connInfo._maxPoolSize, 6);
      FFSDebug.log("StartDBPool:optimalPoolSize=" + localFFSDBProperties._connInfo._optimalPoolSize, 6);
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup(paramString);
      if (localConnectionPool != null) {
        a(paramString);
      }
      localConnectionPool = ConnectionPoolFactory.createConnectionPool(localFFSDBProperties, "BPWServer");
      FFSRegistry.bind(paramString, localConnectionPool);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to start DB Pool:" + localException.toString());
      FFSDebug.console("*** Fail to start DB Pool:" + localException.toString());
      throw localException;
    }
  }
  
  private void a(String paramString)
    throws Exception
  {
    try
    {
      ConnectionPool localConnectionPool = (ConnectionPool)FFSRegistry.lookup(paramString);
      if (localConnectionPool != null)
      {
        localConnectionPool.closePool();
        FFSRegistry.unbind(paramString);
        localConnectionPool = null;
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to stop DB Pool ." + localException.toString());
      FFSDebug.console("*** Fail to stop DB Pool." + localException.toString());
      throw localException;
    }
  }
  
  private void jdField_do()
    throws Exception
  {
    if (jdField_int != null)
    {
      jdField_int.clear();
      jdField_int = null;
    }
    DBUtil.releaseResources();
    FFSDebug.console(" ==== DBUtil cleaned up....");
    FFSDebug.log(" ==== DBUtil cleaned up....");
    String[] arrayOfString = { "BPWENGINE", "FULFILLAGENT", "BPWDBPOOL", "SCHEDULER", "AUDITAGENT", "PURGEENGINE", "PROPERTYCONFIG", "BPWMSGBROKER", "DBCONNCACHE", "FFSPROPS" };
    for (int i = 0; i < arrayOfString.length; i++) {
      try
      {
        FFSRegistry.unbind(arrayOfString[i]);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("Failed to clean obiect: " + arrayOfString[i] + " Error: " + FFSDebug.stackTrace(localException2));
      }
    }
    try
    {
      FFSDebug.log("Shutting down  XferProcessor.....");
      XferProcessor.shutdown();
      FFSDebug.log(" XferProcessor Shut down successfully .....");
    }
    catch (Exception localException1)
    {
      FFSDebug.log("Failed to shutdown XferProcessor. Error: " + FFSDebug.stackTrace(localException1));
    }
  }
  
  private MBConfigInfo jdField_if()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    MBConfigInfo localMBConfigInfo = new MBConfigInfo();
    localMBConfigInfo.setHost(localPropertyConfig.MBConn_host);
    localMBConfigInfo.setPort(localPropertyConfig.MBConn_port);
    localMBConfigInfo.setUser(localPropertyConfig.MBConn_user);
    localMBConfigInfo.setPassword(localPropertyConfig.MBConn_passwd);
    localMBConfigInfo.setDatabaseName(localPropertyConfig.MBConn_dbname);
    localMBConfigInfo.setDBType(localPropertyConfig.MBConn_dbType);
    if ((localPropertyConfig.MBConn_url != null) && (localPropertyConfig.MBConn_url.trim().length() > 0)) {
      localMBConfigInfo.setUrl(localPropertyConfig.MBConn_url);
    } else {
      try
      {
        localMBConfigInfo.setUrl(FFSDBUtils.getDbUrl(a(localMBConfigInfo)));
      }
      catch (FFSException localFFSException)
      {
        FFSDebug.log("Unable to set mb url type. Using " + localMBConfigInfo.getUrl() + ". Error: " + FFSDebug.stackTrace(localFFSException), 0);
      }
    }
    return localMBConfigInfo;
  }
  
  private FFSDBProperties a(MBConfigInfo paramMBConfigInfo)
  {
    FFSDBProperties localFFSDBProperties = new FFSDBProperties();
    ConnPoolInfo localConnPoolInfo = new ConnPoolInfo();
    localConnPoolInfo._dbConnInfo._host = paramMBConfigInfo.getHost();
    localConnPoolInfo._dbConnInfo._port = Integer.parseInt(paramMBConfigInfo.getPort());
    localConnPoolInfo._dbConnInfo._user = paramMBConfigInfo.getUser();
    localConnPoolInfo._dbConnInfo._password = paramMBConfigInfo.getPassword();
    localConnPoolInfo._dbConnInfo._databaseName = paramMBConfigInfo.getDatabaseName();
    localConnPoolInfo._dbConnInfo._dbType = paramMBConfigInfo.getDBType();
    localConnPoolInfo._dbConnInfo._url = paramMBConfigInfo.getUrl();
    localFFSDBProperties._connInfo = localConnPoolInfo;
    return localFFSDBProperties;
  }
  
  private void jdMethod_void(String paramString)
    throws Exception
  {
    try
    {
      MBConfigInfo localMBConfigInfo = jdField_if();
      BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup(paramString);
      if (localBPWMsgBroker != null) {
        jdMethod_char(paramString);
      }
      localBPWMsgBroker = new BPWMsgBroker();
      localBPWMsgBroker.start(localMBConfigInfo);
      FFSRegistry.bind(paramString, localBPWMsgBroker);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to start BPWMsgBroker:" + localException.toString());
      FFSDebug.console("*** Fail to start BPWMsgBroker:" + localException.toString());
      throw localException;
    }
  }
  
  private void jdMethod_char(String paramString)
    throws Exception
  {
    try
    {
      BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup(paramString);
      if (localBPWMsgBroker != null)
      {
        localBPWMsgBroker.stop();
        FFSRegistry.unbind(paramString);
        localBPWMsgBroker = null;
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to stop BPWMsgBroker:" + localException.toString());
      FFSDebug.console("*** Fail to stop BPWMsgBroker:" + localException.toString());
      throw localException;
    }
  }
  
  private void jdMethod_byte()
    throws Exception
  {
    try
    {
      BPWConfigWrapper localBPWConfigWrapper = (BPWConfigWrapper)FFSRegistry.lookup("BPWCONFIGWRAPPER");
      if (localBPWConfigWrapper != null)
      {
        FFSRegistry.unbind("BPWCONFIGWRAPPER");
        BPWConfigWrapper.invalidate();
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to remove BPWConfigWrapper:" + localException.toString());
      FFSDebug.console("*** Fail to remove BPWConfigWrapper:" + localException.toString());
      throw localException;
    }
  }
  
  private void jdField_try(String paramString)
    throws Exception
  {
    try
    {
      ACHAgent localACHAgent = (ACHAgent)FFSRegistry.lookup(paramString);
      if (localACHAgent != null) {
        jdMethod_long(paramString);
      }
      BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
      if (localBPWMsgBroker == null) {
        throw new FFSException("BPWMsgBroker must be started before ACHAgent!");
      }
      localACHAgent = new ACHAgent();
      Hashtable localHashtable = new Hashtable();
      ACHAgent.start(localBPWMsgBroker.getIMBInstance(), localHashtable);
      FFSRegistry.bind(paramString, localACHAgent);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to start BPWMACHAgent:" + localException.toString());
      FFSDebug.console("*** Fail to start BPWACHAgent:" + localException.toString());
      throw localException;
    }
  }
  
  private void jdMethod_long(String paramString)
    throws Exception
  {
    try
    {
      ACHAgent localACHAgent = (ACHAgent)FFSRegistry.lookup(paramString);
      if (localACHAgent != null)
      {
        ACHAgent.shutdown();
        FFSRegistry.unbind(paramString);
        localACHAgent = null;
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to stop BPWACHAgent:" + localException.toString());
      FFSDebug.console("*** Fail to stop BPWACHAgent:" + localException.toString());
      throw localException;
    }
  }
  
  public void registerPropertyConfig(PropertyConfig paramPropertyConfig)
    throws Exception
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    if (localPropertyConfig != null) {
      FFSRegistry.unbind("PROPERTYCONFIG");
    }
    FFSRegistry.bind("PROPERTYCONFIG", paramPropertyConfig);
  }
  
  public void startEngine(String paramString)
    throws Exception
  {
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup(paramString);
      if (localBPWEngine != null) {
        stopEngine(paramString);
      }
      localBPWEngine = new BPWEngine();
      localBPWEngine.start();
      FFSRegistry.bind(paramString, localBPWEngine);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to start BPWEngine:" + localException.toString());
      FFSDebug.console("*** Fail to start BPWEngine:" + localException.toString());
      throw localException;
    }
  }
  
  public void stopEngine(String paramString)
    throws Exception
  {
    try
    {
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup(paramString);
      if (localBPWEngine != null)
      {
        localBPWEngine.stop();
        FFSRegistry.unbind(paramString);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to stop BPWEngine:" + localException.toString());
      FFSDebug.console("*** Fail to stop BPWEngine:" + localException.toString());
      throw localException;
    }
  }
  
  private void jdField_for(String paramString)
    throws Exception
  {
    try
    {
      AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup(paramString);
      if (localAuditAgent != null) {
        jdField_do(paramString);
      }
      localAuditAgent = new AuditAgent();
      FFSRegistry.bind(paramString, localAuditAgent);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to start AuditAgent:" + localException.toString());
      FFSDebug.console("*** Fail to start AuditAgent:" + localException.toString());
      throw localException;
    }
  }
  
  private void jdField_do(String paramString)
    throws Exception
  {
    try
    {
      AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup(paramString);
      if (localAuditAgent != null) {
        FFSRegistry.unbind(paramString);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to stop AuditAgent:" + localException.toString());
      FFSDebug.console("*** Fail to stop AuditAgent:" + localException.toString());
      throw localException;
    }
  }
  
  public synchronized String startScheduler()
    throws Exception
  {
    String str1 = "SCHEDULER";
    String str2 = null;
    if (this.a) {
      throw new Exception("Scheduler already started!");
    }
    jdMethod_else(str1);
    this.a = true;
    return str2;
  }
  
  public synchronized String stopScheduler()
    throws Exception
  {
    String str1 = "SCHEDULER";
    String str2 = null;
    if (!this.a) {
      throw new Exception("Scheduler is not running!");
    }
    jdMethod_case(str1);
    this.a = false;
    return str2;
  }
  
  public String refreshScheduler()
    throws Exception
  {
    String str1 = "SCHEDULER";
    String str2 = null;
    if (!this.a) {
      throw new Exception("Scheduler is not running!");
    }
    jdMethod_case(str1);
    b(str1);
    this.a = true;
    return str2;
  }
  
  private void b(String paramString)
    throws Exception
  {}
  
  private synchronized void jdMethod_else(String paramString)
    throws Exception
  {
    try
    {
      Scheduler localScheduler = (Scheduler)FFSRegistry.lookup(paramString);
      if (localScheduler != null) {
        jdMethod_case(paramString);
      }
      localObject1 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      String str = jdField_int.getProperty("cluster.scheduler.role", "PRIMARY");
      FFSDebug.console(" ==== Server Scheduler role is " + str);
      FFSDebug.log(" ==== Server Scheduler role is " + str);
      if (str.equalsIgnoreCase("NONE"))
      {
        FFSDebug.console(" ==== Scheduler process will not be run on this Server.");
        FFSDebug.log(" ==== Scheduler process will not be run on this Server.");
        return;
      }
      if ((!str.equalsIgnoreCase("PRIMARY")) && (!str.equalsIgnoreCase("SECONDARY")))
      {
        FFSDebug.log(" ==== Invalid Scheduler Role ( " + str + " ). Use configuration property 'cluster.scheduler.role' to fix this problem.");
        FFSDebug.console(" ==== Invalid Scheduler Role ( " + str + " ). Use configuration property 'cluster.scheduler.role' to fix this problem.");
        FFSDebug.console(" ==== Defaulting to role of PRIMARY ....");
        FFSDebug.log(" ==== Defaulting to role of PRIMARY ....");
        str = "PRIMARY";
      }
      localScheduler = new Scheduler();
      localScheduler.init();
      int i = ((PropertyConfig)localObject1).getBatchSize();
      localScheduler.setBatchSize(i);
      FFSRegistry.bind(paramString, localScheduler);
      Object localObject2;
      if (((PropertyConfig)localObject1).getSupportCluster())
      {
        localObject2 = new StartSchedulerRunnable(this, localScheduler, ((PropertyConfig)localObject1).getSchWaitTime(), str);
        this.jdField_new = new Thread((Runnable)localObject2);
        this.jdField_new.start();
      }
      else
      {
        localScheduler.setLock(true);
        localObject2 = DBUtil.getConnection();
        try
        {
          this.a = localScheduler.start((FFSConnection)localObject2);
          ((FFSConnection)localObject2).commit();
        }
        catch (Exception localException2)
        {
          ((FFSConnection)localObject2).rollback();
          throw localException2;
        }
        finally
        {
          DBUtil.freeConnection((FFSConnection)localObject2);
        }
        FFSDebug.console(" ==== Scheduler started ....");
        FFSDebug.log(" ==== Scheduler started ....");
        startPurger("PURGEENGINE");
        FFSDebug.console(" ==== Purger Engine started ....");
        FFSDebug.log(" ==== Purger Engine started ....");
      }
    }
    catch (Exception localException1)
    {
      Object localObject1 = "*** Fail to start Scheduler:" + FFSDebug.stackTrace(localException1);
      FFSDebug.log((String)localObject1);
      FFSDebug.console((String)localObject1);
      throw localException1;
    }
  }
  
  private synchronized void jdMethod_case(String paramString)
    throws Exception
  {
    try
    {
      Scheduler localScheduler = (Scheduler)FFSRegistry.lookup(paramString);
      if (localScheduler != null)
      {
        if (this.jdField_new != null) {
          try
          {
            FFSDebug.log("BPWServer:stopping scheduler thread, please wait ....");
            localScheduler._keeprunning = false;
            this.jdField_new.interrupt();
            FFSDebug.log("BPWServer:joining scheduler thread, please wait ....", 6);
            this.jdField_new.join(1000L);
          }
          catch (Exception localException2) {}
        }
        if (!this.a)
        {
          FFSDebug.log("BPWServer: Scheduler is not running", 0);
          return;
        }
        FFSDebug.log("BPWServer:Stopping scheduler, please wait ....");
        localScheduler.stop();
        FFSRegistry.unbind(paramString);
        localScheduler = null;
        FFSDebug.log("BPWServer:Scheduler stopped........");
        FFSDebug.console("BPWServer:Scheduler stopped........");
        jdMethod_goto("PURGEENGINE");
        FFSDebug.console(" ==== Purger Engine stopped....");
        FFSDebug.log(" ==== Purger Engine stopped....");
      }
    }
    catch (Exception localException1)
    {
      String str = "*** Fail to stop Scheduler:" + FFSDebug.stackTrace(localException1);
      FFSDebug.log(str);
      FFSDebug.console(str);
      throw localException1;
    }
  }
  
  public void startPurger(String paramString)
    throws Exception
  {
    try
    {
      PurgeProcessor localPurgeProcessor = (PurgeProcessor)FFSRegistry.lookup(paramString);
      if (localPurgeProcessor != null) {
        jdMethod_goto(paramString);
      }
      localPurgeProcessor = new PurgeProcessor();
      localPurgeProcessor.start();
      FFSRegistry.bind(paramString, localPurgeProcessor);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to start Purge Engine:" + FFSDebug.stackTrace(localException));
      FFSDebug.console("*** Fail to start Purge Engine:" + FFSDebug.stackTrace(localException));
      throw localException;
    }
  }
  
  private void jdMethod_goto(String paramString)
    throws Exception
  {
    try
    {
      PurgeProcessor localPurgeProcessor = (PurgeProcessor)FFSRegistry.lookup(paramString);
      if (localPurgeProcessor != null)
      {
        localPurgeProcessor.stop();
        FFSRegistry.unbind(paramString);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to stop Purge Engine:" + localException.toString());
      FFSDebug.console("*** Fail to stop Purge Engine:" + localException.toString());
      throw localException;
    }
  }
  
  private void c(String paramString)
    throws Exception
  {
    try
    {
      DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup(paramString);
      if (localDBConnCache != null) {
        jdField_new(paramString);
      }
      localDBConnCache = new DBConnCache();
      FFSRegistry.bind(paramString, localDBConnCache);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** startDBConnCache:" + FFSDebug.stackTrace(localException));
      FFSDebug.console("*** startDBConnCache:" + FFSDebug.stackTrace(localException));
      throw localException;
    }
  }
  
  private void jdField_new(String paramString)
    throws Exception
  {
    try
    {
      DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup(paramString);
      if (localDBConnCache != null) {
        FFSRegistry.unbind(paramString);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** stopDBConnCache:" + localException.toString());
      FFSDebug.console("*** stopDBConnCache:" + localException.toString());
      throw localException;
    }
  }
  
  private void jdField_int()
    throws Exception
  {
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      String str = localPropertyConfig.otherProperties.getProperty("bpw.banksim.start", "false");
      if ("false".equalsIgnoreCase(str)) {
        return;
      }
      FFSDebug.console(" ====Starting Bank Simulator ....");
      BankSimulator.start();
      FFSDebug.console(" ====Bank Simulator Started....");
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** startBanksim: " + FFSDebug.stackTrace(localException));
      FFSDebug.console("*** startBanksim: " + FFSDebug.stackTrace(localException));
    }
  }
  
  private void jdField_new()
    throws Exception
  {
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      String str = localPropertyConfig.otherProperties.getProperty("bpw.banksim.start", "false");
      if ("false".equalsIgnoreCase(str)) {
        return;
      }
      FFSDebug.console(" ====Stoping Bank Simulator ....");
      BankSimulator.stop();
      FFSDebug.console(" ====Bank Simulator Stopped....");
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** stopBanksim: " + FFSDebug.stackTrace(localException));
      FFSDebug.console("*** stopBanksim: " + FFSDebug.stackTrace(localException));
    }
  }
  
  private void jdMethod_byte(String paramString)
    throws Exception
  {
    try
    {
      FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup(paramString);
      if (localFulfillAgent != null) {
        jdMethod_null(paramString);
      }
      localFulfillAgent = new FulfillAgent();
      localFulfillAgent.start();
      FFSRegistry.bind(paramString, localFulfillAgent);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to start Fulfillment agent:" + localException.toString());
      FFSDebug.console("*** Fail to start Fulfillment agent:" + localException.toString());
      throw localException;
    }
  }
  
  private void jdMethod_null(String paramString)
    throws Exception
  {
    try
    {
      FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup(paramString);
      localFulfillAgent.stop();
      if (localFulfillAgent != null) {
        FFSRegistry.unbind(paramString);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to stop Fulfillment agent:" + localException.toString());
      FFSDebug.console("*** Fail to stop Fulfillment agent:" + localException.toString());
      throw localException;
    }
  }
  
  private void jdField_int(String paramString)
    throws Exception
  {
    try
    {
      Object localObject = (TransferCalloutHandlerBase)FFSRegistry.lookup(paramString);
      if (localObject != null) {
        jdField_if(paramString);
      }
      localObject = new TransferCalloutHandler();
      ((TransferCalloutHandlerBase)localObject).start();
      FFSRegistry.bind(paramString, localObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** startTransferCalloutHandler:" + FFSDebug.stackTrace(localException));
      FFSDebug.console("*** startTransferCalloutHandler:" + FFSDebug.stackTrace(localException));
      throw localException;
    }
  }
  
  private void jdField_if(String paramString)
    throws Exception
  {
    try
    {
      TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup(paramString);
      if (localTransferCalloutHandlerBase != null)
      {
        localTransferCalloutHandlerBase.stop();
        FFSRegistry.unbind(paramString);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** stopTransferCalloutHandler:" + localException.toString());
      FFSDebug.console("*** stopTransferCalloutHandler:" + localException.toString());
      throw localException;
    }
  }
  
  public BPWStatistics getStatistics()
    throws FFSException
  {
    return null;
  }
  
  public long getFreeMem()
    throws FFSException
  {
    return 0L;
  }
  
  public long getTotalMem()
    throws FFSException
  {
    return 0L;
  }
  
  public double getHeapUsage()
    throws FFSException
  {
    return 0.0D;
  }
  
  public static boolean isAlive()
  {
    return jdField_for == 1;
  }
  
  public static FFSProperties getProps()
    throws Exception
  {
    return BPWUtil.convertToProperties(new FFSProperties("AutoStartBPW_locale.properties"));
  }
  
  public Vector getUserAuditLog(String paramString)
    throws FFSException
  {
    return null;
  }
  
  public void setSchedulerStatus(boolean paramBoolean)
  {
    this.a = paramBoolean;
  }
  
  public void resubmitEvent(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception
  {
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      localObject1 = "BPWServer.resubmitEvent Failed: FIID is null";
      throw new FFSException((String)localObject1);
    }
    Object localObject1 = new FFSConnectionHolder();
    ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
    Object localObject2;
    if (((FFSConnectionHolder)localObject1).conn == null)
    {
      localObject2 = "Failed to obtain a connection from from the connection pool";
      System.out.println((String)localObject2);
      throw new FFSException((String)localObject2);
    }
    try
    {
      localObject2 = new Scheduler();
      if ((paramString4 == null) || (paramString4.trim().length() == 0)) {
        ((Scheduler)localObject2).resubmitEvent(((FFSConnectionHolder)localObject1).conn, paramString1.trim(), paramString2, paramString3);
      } else {
        ((Scheduler)localObject2).resubmitEvent(((FFSConnectionHolder)localObject1).conn, paramString1.trim(), paramString2, paramString3, paramString4);
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to resubmitEvent. error: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      if (((FFSConnectionHolder)localObject1).conn != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
  }
  
  public void resubmitEvent(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      localObject1 = "BPWServer.resubmitEvent Failed: FIID is null";
      throw new FFSException((String)localObject1);
    }
    Object localObject1 = new FFSConnectionHolder();
    ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
    Object localObject2;
    if (((FFSConnectionHolder)localObject1).conn == null)
    {
      localObject2 = "Failed to obtain a connection from from the connection pool";
      System.out.println((String)localObject2);
      throw new FFSException((String)localObject2);
    }
    try
    {
      localObject2 = new Scheduler();
      ((Scheduler)localObject2).resubmitEvent(((FFSConnectionHolder)localObject1).conn, paramString1, paramString2, paramString3);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to resubmitEvent. error: " + FFSDebug.stackTrace(localException));
      throw localException;
    }
    finally
    {
      if (((FFSConnectionHolder)localObject1).conn != null) {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
    }
  }
  
  public static String addPayee(FFSDBProperties paramFFSDBProperties, PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws FFSException
  {
    BPWAdminBackend.setDBProperties(paramFFSDBProperties);
    return BPWAdminBackend.addPayee(paramPayeeInfo, paramPayeeRouteInfo);
  }
  
  public static void updatePayee(FFSDBProperties paramFFSDBProperties, PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws FFSException
  {
    BPWAdminBackend.setDBProperties(paramFFSDBProperties);
    BPWAdminBackend.updatePayee(paramPayeeInfo, paramPayeeRouteInfo);
  }
  
  public static FulfillmentInfo getFulfillmentInfo(int paramInt)
    throws FFSException
  {
    return BPWRegistryUtil.getFulfillmentInfo(paramInt);
  }
  
  public static void deletePayee(FFSDBProperties paramFFSDBProperties, String paramString)
    throws FFSException
  {
    BPWAdminBackend.setDBProperties(paramFFSDBProperties);
    BPWAdminBackend.deletePayee(paramString);
  }
  
  public static PayeeRouteInfo getPayeeRoute(FFSDBProperties paramFFSDBProperties, String paramString, int paramInt)
    throws FFSException
  {
    BPWAdminBackend.setDBProperties(paramFFSDBProperties);
    return BPWAdminBackend.getPayeeRoute(paramString, paramInt);
  }
  
  public static PayeeInfo findPayeeByID(FFSDBProperties paramFFSDBProperties, String paramString)
    throws FFSException
  {
    BPWAdminBackend.setDBProperties(paramFFSDBProperties);
    return BPWAdminBackend.findPayeeByID(paramString);
  }
  
  public static final void releaseAdminResources() {}
  
  public static final void setFFSProperties(FFSProperties paramFFSProperties)
  {
    jdField_int = paramFFSProperties;
  }
  
  public static final FFSProperties getProperties()
  {
    return jdField_int;
  }
  
  public static final void setPropertyValue(Object paramObject1, Object paramObject2)
    throws FFSException
  {
    synchronized (jdField_if)
    {
      if (jdField_int == null) {
        throw new FFSException("Server property not initialized.");
      }
      jdField_int.remove(paramObject1);
      jdField_int.put(paramObject1, paramObject2);
    }
  }
  
  public static final String getPropertyValue(String paramString)
    throws FFSException
  {
    String str = null;
    synchronized (jdField_if)
    {
      if (jdField_int == null) {
        throw new FFSException("Server property not initialized.");
      }
      str = jdField_int.getProperty(paramString);
    }
    return str;
  }
  
  public static final String getPropertyValue(String paramString1, String paramString2)
    throws FFSException
  {
    String str = null;
    synchronized (jdField_if)
    {
      if (jdField_int == null) {
        throw new FFSException("Server property not initialized.");
      }
      str = jdField_int.getProperty(paramString1, paramString2);
    }
    return str;
  }
  
  public static final void setDebugLevel(int paramInt)
  {
    FFSDebug.setDebugLevel(paramInt);
  }
  
  public static final FulfillmentInfo[] getAllFulfillmentInfo(FFSDBProperties paramFFSDBProperties)
    throws FFSException
  {
    FulfillmentInfo[] arrayOfFulfillmentInfo = null;
    arrayOfFulfillmentInfo = BPWAdminBackend.getAllFulfillmentInfo(paramFFSDBProperties);
    BPWRegistryUtil.setFulfillmentInfo(arrayOfFulfillmentInfo);
    return arrayOfFulfillmentInfo;
  }
  
  public static void addFulfillmentInfo(FFSDBProperties paramFFSDBProperties, FulfillmentInfo paramFulfillmentInfo)
    throws FFSException
  {
    BPWAdminBackend.addFulfillmentInfo(paramFFSDBProperties, paramFulfillmentInfo);
    BPWRegistryUtil.addFulfillment(paramFulfillmentInfo);
  }
  
  public static void updateFulfillmentInfo(FFSDBProperties paramFFSDBProperties, FulfillmentInfo paramFulfillmentInfo)
    throws FFSException
  {
    BPWAdminBackend.updateFulfillmentInfo(paramFFSDBProperties, paramFulfillmentInfo);
    BPWRegistryUtil.updateFulfillment(paramFulfillmentInfo);
  }
  
  public static void deleteFulfillmentInfo(FFSDBProperties paramFFSDBProperties, int paramInt)
    throws FFSException
  {
    BPWAdminBackend.deleteFulfillmentInfo(paramFFSDBProperties, paramInt);
    BPWRegistryUtil.deleteFulfillment(paramInt);
  }
  
  private static void a(InstructionType[] paramArrayOfInstructionType)
  {
    if (paramArrayOfInstructionType == null) {
      return;
    }
    for (int i = 0; i < paramArrayOfInstructionType.length; i++) {
      paramArrayOfInstructionType[i].InstructionType = BPWUtil.trimInstructionType(paramArrayOfInstructionType[i].InstructionType);
    }
  }
  
  private void jdMethod_case() {}
  
  public void startLimitCheckApprovalProcessor(String paramString)
    throws Exception
  {
    try
    {
      LimitCheckApprovalProcessor localLimitCheckApprovalProcessor = (LimitCheckApprovalProcessor)FFSRegistry.lookup(paramString);
      if (localLimitCheckApprovalProcessor != null) {
        stopLimitCheckApprovalProcessor(paramString);
      }
      localLimitCheckApprovalProcessor = new LimitCheckApprovalProcessor();
      boolean bool = LimitCheckApprovalProcessor.start();
      if (!bool)
      {
        FFSDebug.log("*** Fail to start LimitCheckApprovalProcessor.");
        FFSDebug.console("*** Fail to start LimitCheckApprovalProcessor.");
        throw new FFSException("Failed to start LimitCheckApprovalProcessor");
      }
      FFSRegistry.bind(paramString, localLimitCheckApprovalProcessor);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to start LimitCheckApprovalProcessor:" + localException.toString());
      FFSDebug.console("*** Fail to start LimitCheckApprovalProcessor:" + localException.toString());
      throw localException;
    }
  }
  
  public void stopLimitCheckApprovalProcessor(String paramString)
    throws Exception
  {
    try
    {
      LimitCheckApprovalProcessor localLimitCheckApprovalProcessor = (LimitCheckApprovalProcessor)FFSRegistry.lookup(paramString);
      if (localLimitCheckApprovalProcessor != null)
      {
        FFSRegistry.unbind(paramString);
        LimitCheckApprovalProcessor.stop();
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to stop LimitCheckApprovalProcessor:" + localException.toString());
      FFSDebug.console("*** Fail to stop LimitCheckApprovalProcessor:" + localException.toString());
      throw localException;
    }
  }
  
  public void runBatchProcessOnRemoteServer(String paramString1, String paramString2)
    throws Exception
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new Exception("Exception occured while redirecting runBatchProcess.");
    }
    localIBPWAdmin.runBatchProcess(paramString1, paramString2);
  }
  
  public void updateScheduleRunTimeConfigOnRemoteServer(InstructionType paramInstructionType)
    throws Exception
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new Exception("Exception occured while redirecting updateScheduleRunTimeConfig.");
    }
    localIBPWAdmin.updateScheduleRunTimeConfig(paramInstructionType);
  }
  
  public void updateScheduleProcessingOnRemoteServer(InstructionType paramInstructionType)
    throws Exception
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new Exception("Exception occured while redirecting updateScheduleProcessingConfig.");
    }
    localIBPWAdmin.updateScheduleProcessingConfig(paramInstructionType);
  }
  
  public void updateScheduleOnRemoteServer(InstructionType paramInstructionType)
    throws Exception
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new Exception("Exception occured while redirecting updateScheduleConfig.");
    }
    localIBPWAdmin.updateScheduleConfig(paramInstructionType);
  }
  
  public void addScheduleOnRemoteServer(InstructionType paramInstructionType)
    throws Exception
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new Exception("Exception occured while redirecting addScheduleConfig.");
    }
    localIBPWAdmin.addScheduleConfig(paramInstructionType);
  }
  
  public void deleteScheduleOnRemoteServer(InstructionType paramInstructionType)
    throws Exception
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new Exception("Exception occured while redirecting deleteScheduleConfig.");
    }
    localIBPWAdmin.deleteScheduleConfig(paramInstructionType);
  }
  
  public SchedulerInfo getSchedulerInfoOnRemoteServer(String paramString1, String paramString2)
    throws Exception
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new Exception("Exception occured while redirecting getSchedulerInfo.");
    }
    return localIBPWAdmin.getSchedulerInfo(paramString1, paramString2);
  }
  
  public SchedulerInfo[] getSchedulerInfoOnRemoteServer()
    throws Exception
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new Exception("Exception occured while redirecting getSchedulerInfo.");
    }
    return localIBPWAdmin.getSchedulerInfo();
  }
  
  private IBPWAdmin jdField_try()
    throws Exception
  {
    PropertyConfig localPropertyConfig1 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str1 = localPropertyConfig1.getServerProtocol().trim() + "://" + localPropertyConfig1.getServerHost().trim() + ":" + localPropertyConfig1.getServerPort();
    String str2;
    String str3;
    int i;
    try
    {
      str2 = NamePlace.getValue("EJBProtocol");
      str3 = NamePlace.getValue("EJBHost");
      i = Integer.parseInt(NamePlace.getValue("EJBPort"));
      String str4 = str2 + "://" + str3 + ":" + i;
      if (str1.equalsIgnoreCase(str4)) {
        throw new Exception("Cannot find a server which has the scheduler running.");
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log(localException1, "", 0);
      throw localException1;
    }
    PropertyConfig localPropertyConfig2 = new PropertyConfig();
    try
    {
      localPropertyConfig2.setServerProtocol(str2);
      localPropertyConfig2.setServerHost(str3);
      localPropertyConfig2.setServerPort(i);
      localPropertyConfig2.setServerUserName(NamePlace.getValue("EJBUserName"));
      localPropertyConfig2.setServerPassword(NamePlace.getValue("EJBPassword"));
      localPropertyConfig2.BPWServ_jndiName = NamePlace.getValue("EJBJNDIName");
      localPropertyConfig2.setNameContext(NamePlace.getValue("EJBNameContext"));
    }
    catch (Exception localException2)
    {
      FFSDebug.log(localException2, "", 0);
      throw localException2;
    }
    return BPWEJBUtil.getAdminBean(localPropertyConfig2);
  }
  
  public CutOffInfo addCutOffOnRemoteServer(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
      return localIBPWAdmin.addCutOff(paramCutOffInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new FFSException("Exception occured while redirecting addCutOffOnRemoteServer.");
    }
  }
  
  public CutOffInfo modCutOffOnRemoteServer(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
      return localIBPWAdmin.modCutOff(paramCutOffInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new FFSException("Exception occured while redirecting modCutOffOnRemoteServer.");
    }
  }
  
  public CutOffInfo getCutOffOnRemoteServer(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
      return localIBPWAdmin.getCutOff(paramCutOffInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new FFSException("Exception occured while redirecting getCutOffOnRemoteServer.");
    }
  }
  
  public CutOffInfoList getCutOffListOnRemoteServer(CutOffInfoList paramCutOffInfoList)
    throws FFSException
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
      return localIBPWAdmin.getCutOffList(paramCutOffInfoList);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new FFSException("Exception occured while redirecting getCutOffOnRemoteServer.");
    }
  }
  
  public ScheduleCategoryInfo getScheduleCategoryInfoOnRemoteServer(String paramString1, String paramString2)
    throws FFSException
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
      return localIBPWAdmin.getScheduleCategoryInfo(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      String str = "*** BPWServer.getScheduleCategoryInfoOnRemoteServer failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str, 0);
      throw new FFSException("Exception occured while redirecting getScheduleCategoryInfoOnRemoteServer. Error =" + localException.getMessage());
    }
  }
  
  public ScheduleCategoryInfo modScheduleCategoryInfoOnRemoteServer(ScheduleCategoryInfo paramScheduleCategoryInfo)
    throws FFSException
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
      return localIBPWAdmin.modScheduleCategoryInfo(paramScheduleCategoryInfo);
    }
    catch (Exception localException)
    {
      String str = "BPWServer.modScheduleCategoryInfoOnRemoteServer failed :" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str, 0);
      throw new FFSException("Exception occured while redirecting modScheduleCategoryInfoOnRemoteServer. Error =" + localException.getMessage());
    }
  }
  
  public CutOffInfo deleteCutOffOnRemoteServer(CutOffInfo paramCutOffInfo)
    throws FFSException
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
      return localIBPWAdmin.deleteCutOff(paramCutOffInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "", 0);
      throw new FFSException("Exception occured while redirecting deleteCutOffOnRemoteServer.");
    }
  }
  
  public InstructionType[] getScheduleConfigByCategoryOnRemoteServer(InstructionType paramInstructionType)
    throws FFSException
  {
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
      return localIBPWAdmin.getScheduleConfigByCategory(paramInstructionType);
    }
    catch (Exception localException)
    {
      String str = "BPWServer.getScheduleConfigByCategoryOnRemoteServer failed :" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str, 0);
      throw new FFSException("Exception occured while redirecting getScheduleConfigByCategoryOnRemoteServer. Error =" + localException.getMessage());
    }
  }
  
  public CutOffActivityInfoList getCutOffActivityListOnRemoteServer(CutOffActivityInfoList paramCutOffActivityInfoList)
    throws FFSException
  {
    String str1 = "BPWServer.getCutOffActivityListOnRemoteServer: ";
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
      return localIBPWAdmin.getCutOffActivityList(paramCutOffActivityInfoList);
    }
    catch (FFSException localFFSException)
    {
      str2 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = str1 + "failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException("Exception occured while redirecting getCutOffActivityListOnRemoteServer. Error =" + localException.getMessage());
    }
  }
  
  public ScheduleActivityList getScheduleActivityListOnRemoteServer(ScheduleActivityList paramScheduleActivityList)
    throws FFSException
  {
    String str1 = "BPWServer.getScheduleActivityListOnRemoteServer: ";
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
      return localIBPWAdmin.getScheduleActivityList(paramScheduleActivityList);
    }
    catch (FFSException localFFSException)
    {
      str2 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = str1 + "failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException("Exception occured while redirecting getScheduleActivityListOnRemoteServer. Error =" + localException.getMessage());
    }
  }
  
  public void rerunCutOffOnRemoteServer(String paramString1, String paramString2, String paramString3, String paramString4)
    throws Exception
  {
    String str1 = "BPWServer.rerunCutOffOnRemoteServer: ";
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
      localIBPWAdmin.rerunCutOff(paramString1, paramString2, paramString3, paramString4);
    }
    catch (FFSException localFFSException)
    {
      str2 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = str1 + "failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException("Exception occured while redirecting rerunCutOffOnRemoteServer. Error =" + localException.getMessage());
    }
  }
  
  public String getGeneratedFileNameOnRemoteServer(String paramString1, String paramString2, String paramString3)
    throws Exception
  {
    String str1 = "BPWServer.getGeneratedFileName: ";
    IBPWAdmin localIBPWAdmin = null;
    try
    {
      localIBPWAdmin = jdField_try();
      return localIBPWAdmin.getGeneratedFileName(paramString1, paramString2, paramString3);
    }
    catch (FFSException localFFSException)
    {
      str2 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = str1 + "failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException("Exception occured while redirecting getGeneratedFileName. Error =" + localException.getMessage());
    }
  }
  
  private void jdMethod_char()
    throws Exception
  {
    try
    {
      FFSDebug.console(" ==== Loading BPWConfig Wrapper started....");
      FFSDebug.log(" ==== Loading BPWConfig Wrapper started....");
      BPWConfigWrapper localBPWConfigWrapper = (BPWConfigWrapper)FFSRegistry.lookup("BPWCONFIGWRAPPER");
      if (localBPWConfigWrapper == null)
      {
        FFSDebug.console(" ==== Binding BPWConfig Wrapper....");
        String str = "bpwconfig.xml";
        localBPWConfigWrapper = BPWConfigWrapper.getInstance();
        localBPWConfigWrapper.initialize(str, true);
        FFSRegistry.bind("BPWCONFIGWRAPPER", localBPWConfigWrapper);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to initialize BPWConfigWrapper:" + localException.toString());
      FFSDebug.console("*** Fail to initialize BPWConfigWrapper:" + localException.toString());
      throw localException;
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.BPWServer
 * JD-Core Version:    0.7.0.1
 */