package com.ffusion.ffs.bpw.adminEJB;

import com.ffusion.ffs.bpw.BPWServer;
import com.ffusion.ffs.bpw.config.BPWAdmin;
import com.ffusion.ffs.bpw.config.BPWAdminImpl;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.BPWStatistics;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowList;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.ScheduleCategoryInfo;
import com.ffusion.ffs.bpw.interfaces.ScheduleHist;
import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
import com.ffusion.ffs.bpw.interfaces.SmartCalendarFile;
import com.ffusion.ffs.bpw.master.BPWEngine;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSDBProperties;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.Scheduler;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class BPWAdminBean
  implements SessionBean, BPWResource
{
  private SessionContext _ctx = null;
  private static BPWAdmin _bpwAdmin = null;
  
  public void setSessionContext(SessionContext paramSessionContext)
    throws RemoteException
  {
    this._ctx = paramSessionContext;
  }
  
  public void ejbRemove()
    throws RemoteException
  {}
  
  public void ejbPassivate()
    throws RemoteException
  {}
  
  public void ejbActivate()
    throws RemoteException
  {}
  
  public void ejbCreate()
    throws RemoteException
  {
    if (_bpwAdmin == null) {
      _bpwAdmin = new BPWAdminImpl();
    }
  }
  
  private boolean startServer(FFSProperties paramFFSProperties)
    throws Exception
  {
    boolean bool = false;
    try
    {
      FFSProperties localFFSProperties = null;
      String str = null;
      try
      {
        localFFSProperties = BPWServer.getProps();
        str = localFFSProperties.getProperty("ServerName");
        if (str == null)
        {
          prn("Failed to get BPW Server properties for ServerName");
          prn("Using the Default Server ...");
          str = "Default Server";
        }
      }
      catch (Exception localException2)
      {
        prn("Failed to get BPW Server properties for ServerName,  error =" + localException2.getMessage());
        prn("Using the Default Server ...");
        str = "Default Server";
      }
      BPWAdminImpl localBPWAdminImpl = new BPWAdminImpl(paramFFSProperties);
      PropertyConfig localPropertyConfig = localBPWAdminImpl.getPropertyConfig(str);
      localBPWAdminImpl.addClusterSettings(localPropertyConfig);
      InstructionType[] arrayOfInstructionType = localBPWAdminImpl.getInstructionTypes();
      localPropertyConfig.otherProperties = ((FFSProperties)localPropertyConfig.otherProperties.clone());
      localBPWAdminImpl.addClusterOtherProperties(localPropertyConfig);
      prn("\t  PropertyConfig:.......");
      localPropertyConfig.print();
      prn("\t  Other Properties:.......");
      prn("" + localPropertyConfig.otherProperties);
      prn("\n\t ");
      prn("\t ");
      prn("\t Calling  Start on  BPTW Server........");
      start(localPropertyConfig.otherProperties, localPropertyConfig, arrayOfInstructionType);
      prn("\tBPTW Server started successfully ........");
      bool = true;
    }
    catch (Exception localException1)
    {
      prn(stack(localException1));
      throw localException1;
    }
    return bool;
  }
  
  public void start(PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
    throws FFSException
  {
    start(paramPropertyConfig.otherProperties, paramPropertyConfig, paramArrayOfInstructionType);
  }
  
  /**
   * @deprecated
   */
  public void start(FFSProperties paramFFSProperties, PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
    throws FFSException
  {
    try
    {
      if (BPWServer.isAlive()) {
        throw new FFSException("**** BPWServer is already started! ");
      }
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** BPWServer not initialized");
      }
      localBPWServer.start(paramFFSProperties, paramPropertyConfig, paramArrayOfInstructionType);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new FFSException("**** Fail to start BPWServer : " + localException.toString());
    }
  }
  
  public void refresh(PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
    throws FFSException
  {
    refresh(paramPropertyConfig.otherProperties, paramPropertyConfig, paramArrayOfInstructionType);
  }
  
  /**
   * @deprecated
   */
  public void refresh(FFSProperties paramFFSProperties, PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if ((localBPWServer != null) && (BPWServer.isAlive()))
      {
        localBPWServer.stop();
        localBPWServer.start(paramFFSProperties, paramPropertyConfig, paramArrayOfInstructionType);
      }
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Fail to refresh BPWServer: " + localException.toString());
    }
  }
  
  public void stop()
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** BPWServer not initialized");
      }
      localBPWServer.stop();
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Fail to stop BPWServer: " + localException.toString());
    }
  }
  
  public String startScheduler()
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** Failed to start Scheduler. BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("**** Failed to start Scheduler. BPWServer is not running");
      }
      return localBPWServer.startScheduler();
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Failed to start Scheduler: " + FFSDebug.stackTrace(localException));
    }
  }
  
  public String stopScheduler()
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** Failed to stop Scheduler. BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("****Failed to stop Scheduler. BPWServer is not running");
      }
      return localBPWServer.stopScheduler();
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Failed to stop Scheduler: " + FFSDebug.stackTrace(localException));
    }
  }
  
  public String refreshScheduler()
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** Failed to refresh Scheduler. BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("****Failed to refresh Scheduler.  BPWServer is not running");
      }
      return localBPWServer.refreshScheduler();
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Failed to refresh Scheduler: " + FFSDebug.stackTrace(localException));
    }
  }
  
  public void registerPropertyConfig(PropertyConfig paramPropertyConfig)
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** Failed to register property config. BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("**** Failed to register property config. BPWServer is not running");
      }
      localBPWServer.registerPropertyConfig(paramPropertyConfig);
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Failed to register property config: " + FFSDebug.stackTrace(localException));
    }
  }
  
  public void startEngine(String paramString)
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** Failed to start BWP Engine. BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("**** Failed to start BWP Engine. BPWServer is not running");
      }
      localBPWServer.startEngine(paramString);
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Failed to start BWP Engine: " + FFSDebug.stackTrace(localException));
    }
  }
  
  public void stopEngine(String paramString)
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** Failed to stop BWP Engine. BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("**** Failed to stop BWP Engine. BPWServer is not running");
      }
      localBPWServer.stopEngine(paramString);
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Failed to stop BWP Engine: " + FFSDebug.stackTrace(localException));
    }
  }
  
  public void stopLimitCheckApprovalProcessor(String paramString)
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** Failed to stop Limit Check Processor. BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("**** Failed to stop Limit Check Processor. BPWServer is not running");
      }
      localBPWServer.stopLimitCheckApprovalProcessor(paramString);
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Failed to stop Limit Check Processor: " + FFSDebug.stackTrace(localException));
    }
  }
  
  public void startLimitCheckApprovalProcessor(String paramString)
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** Failed to start Limit Check Processor. BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("**** Failed to start Limit Check Processor. BPWServer is not running");
      }
      localBPWServer.startLimitCheckApprovalProcessor(paramString);
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Failed to start Limit Check Processor: " + FFSDebug.stackTrace(localException));
    }
  }
  
  public void runBatchProcess(String paramString1, String paramString2)
    throws FFSException
  {
    BPWServer localBPWServer = BPWServer.getInstance();
    if (localBPWServer == null) {
      throw new FFSException("**** BPWServer not initialized");
    }
    if (!BPWServer.isAlive()) {
      throw new FFSException("**** BPWServer is not running");
    }
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
      localScheduler.handleExternalScheduleReq(paramString1, paramString2);
    } else {
      try
      {
        localBPWServer.runBatchProcessOnRemoteServer(paramString1, paramString2);
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "", 0);
        throw new FFSException(localException.getMessage());
      }
    }
  }
  
  public void updateScheduleRunTimeConfig(InstructionType paramInstructionType)
    throws FFSException
  {
    BPWServer localBPWServer = BPWServer.getInstance();
    if (localBPWServer == null) {
      throw new FFSException("**** BPWServer not initialized");
    }
    if (!BPWServer.isAlive()) {
      throw new FFSException("**** BPWServer is not running");
    }
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
      localScheduler.updateScheduleRunTime(paramInstructionType);
    } else {
      try
      {
        localBPWServer.updateScheduleRunTimeConfigOnRemoteServer(paramInstructionType);
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "", 0);
        throw new FFSException(localException.getMessage());
      }
    }
  }
  
  public void updateScheduleProcessingConfig(InstructionType paramInstructionType)
    throws FFSException
  {
    BPWServer localBPWServer = BPWServer.getInstance();
    if (localBPWServer == null) {
      throw new FFSException("**** BPWServer not initialized");
    }
    if (!BPWServer.isAlive()) {
      throw new FFSException("**** BPWServer is not running");
    }
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
      localScheduler.updateScheduleProcessing(paramInstructionType);
    } else {
      try
      {
        localBPWServer.updateScheduleProcessingOnRemoteServer(paramInstructionType);
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "", 0);
        throw new FFSException(localException.getMessage());
      }
    }
  }
  
  public void updateScheduleConfig(InstructionType paramInstructionType)
    throws FFSException
  {
    BPWServer localBPWServer = BPWServer.getInstance();
    if (localBPWServer == null) {
      throw new FFSException("**** BPWServer not initialized");
    }
    if (!BPWServer.isAlive()) {
      throw new FFSException("**** BPWServer is not running");
    }
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
      localScheduler.updateSchedule(paramInstructionType, false);
    } else {
      try
      {
        localBPWServer.updateScheduleOnRemoteServer(paramInstructionType);
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "", 0);
        throw new FFSException(localException.getMessage());
      }
    }
  }
  
  public InstructionType getScheduleConfig(String paramString1, String paramString2)
    throws FFSException
  {
    BPWServer localBPWServer = BPWServer.getInstance();
    if (localBPWServer == null) {
      throw new FFSException("**** BPWServer not initialized");
    }
    if (!BPWServer.isAlive()) {
      throw new FFSException("**** BPWServer is not running");
    }
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    if (localScheduler == null) {
      throw new FFSException("**** BPWServer Scheduler not initialized");
    }
    return localScheduler.getInstructionType(paramString1, paramString2);
  }
  
  public InstructionType[] getScheduleConfig()
    throws FFSException
  {
    BPWServer localBPWServer = BPWServer.getInstance();
    if (localBPWServer == null) {
      throw new FFSException("**** BPWServer not initialized");
    }
    if (!BPWServer.isAlive()) {
      throw new FFSException("**** BPWServer is not running");
    }
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    if (localScheduler == null) {
      throw new FFSException("**** BPWServer Scheduler not initialized");
    }
    return localScheduler.getInstructionTypes();
  }
  
  public void refreshSmartCalendar()
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("**** BPWServer is not running");
      }
      Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
      localScheduler.refreshSmartCalendar();
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Failed to refresh smart calendar: " + FFSDebug.stackTrace(localException));
    }
  }
  
  public SchedulerInfo getSchedulerInfo(String paramString1, String paramString2)
    throws FFSException
  {
    BPWServer localBPWServer = BPWServer.getInstance();
    if (localBPWServer == null) {
      throw new FFSException("**** BPWServer not initialized");
    }
    if (!BPWServer.isAlive()) {
      throw new FFSException("**** BPWServer is not running");
    }
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    SchedulerInfo localSchedulerInfo = null;
    if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
      localSchedulerInfo = localScheduler.getSchedulerInfo(paramString1, paramString2);
    } else {
      try
      {
        localSchedulerInfo = localBPWServer.getSchedulerInfoOnRemoteServer(paramString1, paramString2);
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "", 0);
        throw new FFSException(localException.getMessage());
      }
    }
    return localSchedulerInfo;
  }
  
  public SchedulerInfo[] getSchedulerInfo()
    throws FFSException
  {
    BPWServer localBPWServer = BPWServer.getInstance();
    if (localBPWServer == null) {
      throw new FFSException("**** BPWServer not initialized");
    }
    if (!BPWServer.isAlive()) {
      throw new FFSException("**** BPWServer is not running");
    }
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    SchedulerInfo[] arrayOfSchedulerInfo = null;
    if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
      arrayOfSchedulerInfo = localScheduler.getSchedulerInfo();
    } else {
      try
      {
        arrayOfSchedulerInfo = localBPWServer.getSchedulerInfoOnRemoteServer();
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "", 0);
        throw new FFSException(localException.getMessage());
      }
    }
    return arrayOfSchedulerInfo;
  }
  
  public ScheduleHist[] getScheduleHist(String paramString1, String paramString2, ScheduleHist paramScheduleHist)
    throws FFSException
  {
    BPWServer localBPWServer = BPWServer.getInstance();
    if (localBPWServer == null) {
      throw new FFSException("**** BPWServer not initialized");
    }
    if (!BPWServer.isAlive()) {
      throw new FFSException("**** BPWServer is not running");
    }
    return Scheduler.getScheduleHist(paramString1, paramString2, paramScheduleHist);
  }
  
  public BPWStatistics getStatistics()
    throws FFSException
  {
    BPWStatistics localBPWStatistics = null;
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** BPWServer not initialized");
      }
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Fail to get statistics:" + localException.toString());
    }
    return localBPWStatistics;
  }
  
  public long getFreeMem()
    throws FFSException
  {
    long l = 0L;
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** BPWServer not initialized");
      }
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Fail to get free memory:" + localException.toString());
    }
    return l;
  }
  
  public long getTotalMem()
    throws FFSException
  {
    long l = 0L;
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** BPWServer not initialized");
      }
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Fail to get total memory:" + localException.toString());
    }
    return l;
  }
  
  public double getHeapUsage()
    throws FFSException
  {
    double d = 0.0D;
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** BPWServer not initialized");
      }
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Fail to get heap usage:" + localException.toString());
    }
    return d;
  }
  
  public boolean ping()
    throws FFSException
  {
    boolean bool = true;
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** BPWServer not initialized");
      }
      bool = BPWServer.isAlive();
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Fail to ping BPWServer:" + localException.toString());
    }
    return bool;
  }
  
  public PayeeInfo[] searchGlobalPayees(String paramString)
    throws FFSException
  {
    PayeeInfo[] arrayOfPayeeInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      if (!BPWServer.isAlive()) {
        throw new FFSException("**** BPWServer is not alive");
      }
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      arrayOfPayeeInfo = Payee.searchGlobalPayees(paramString, localFFSConnectionHolder);
    }
    catch (Exception localException1)
    {
      throw new FFSException("**** Fail to find global payees : " + localException1.toString());
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("Error release connection to the connection pool: " + FFSDebug.stackTrace(localException2));
      }
    }
    return arrayOfPayeeInfo;
  }
  
  public void resubmitEvent(String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "BPWAdminBean.resubmitEvent(insType, logDate) ";
    String str2 = str1 + "Error: This is an obsolete API which uses default FIID 1000.  The new API with FIID as parameter should be used.";
    FFSDebug.log(str2, 0);
    resubmitEvent("1000", paramString1, paramString2);
  }
  
  public void resubmitEvent(String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("**** BPWServer is not alive");
      }
      localBPWServer.resubmitEvent(paramString1, paramString2, paramString3);
    }
    catch (Exception localException)
    {
      throw new FFSException("**** Fail to stop BPWServer: " + localException.toString());
    }
  }
  
  public void resubmitEvent(String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("**** BPWServer is not alive");
      }
      localBPWServer.resubmitEvent(paramString1, paramString2, paramString3, paramString4);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      throw new FFSException("**** Fail to resubmit event to BPWServer:  " + localException.toString());
    }
  }
  
  public String addPayee(FFSDBProperties paramFFSDBProperties, PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws FFSException
  {
    return BPWServer.addPayee(paramFFSDBProperties, paramPayeeInfo, paramPayeeRouteInfo);
  }
  
  public void updatePayee(FFSDBProperties paramFFSDBProperties, PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws FFSException
  {
    BPWServer.updatePayee(paramFFSDBProperties, paramPayeeInfo, paramPayeeRouteInfo);
  }
  
  public void deletePayee(FFSDBProperties paramFFSDBProperties, String paramString)
    throws FFSException
  {
    BPWServer.deletePayee(paramFFSDBProperties, paramString);
  }
  
  public PayeeRouteInfo getPayeeRoute(FFSDBProperties paramFFSDBProperties, String paramString, int paramInt)
    throws FFSException
  {
    return BPWServer.getPayeeRoute(paramFFSDBProperties, paramString, paramInt);
  }
  
  public PayeeInfo findPayeeByID(FFSDBProperties paramFFSDBProperties, String paramString)
    throws FFSException
  {
    return BPWServer.findPayeeByID(paramFFSDBProperties, paramString);
  }
  
  public FulfillmentInfo[] getAllFulfillmentInfo(FFSDBProperties paramFFSDBProperties)
    throws FFSException
  {
    return BPWServer.getAllFulfillmentInfo(paramFFSDBProperties);
  }
  
  public String[] getGlobalPayeeGroups()
    throws FFSException
  {
    BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
    return localBPWEngine.getGlobalPayeeGroups();
  }
  
  public PayeeInfo[] searchGlobalPayees(PayeeInfo paramPayeeInfo, int paramInt)
    throws FFSException, RemoteException
  {
    BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
    return localBPWEngine.searchGlobalPayees(paramPayeeInfo, paramInt);
  }
  
  public PayeeInfo getGlobalPayee(String paramString)
    throws RemoteException, FFSException
  {
    BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
    return localBPWEngine.getGlobalPayee(paramString);
  }
  
  public PayeeInfo updateGlobalPayee(PayeeInfo paramPayeeInfo)
    throws RemoteException, FFSException
  {
    BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
    return localBPWEngine.updateGlobalPayee(paramPayeeInfo);
  }
  
  public FulfillmentInfo[] getFulfillmentSystems()
    throws FFSException, RemoteException
  {
    FulfillmentInfo[] arrayOfFulfillmentInfo = null;
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    arrayOfFulfillmentInfo = BPWRegistryUtil.getAllFunfillmentInfo();
    if (arrayOfFulfillmentInfo == null) {
      arrayOfFulfillmentInfo = new FulfillmentInfo[0];
    }
    return arrayOfFulfillmentInfo;
  }
  
  public void addFulfillmentInfo(FFSDBProperties paramFFSDBProperties, FulfillmentInfo paramFulfillmentInfo)
    throws FFSException
  {
    BPWServer.addFulfillmentInfo(paramFFSDBProperties, paramFulfillmentInfo);
  }
  
  public void updateFulfillmentInfo(FFSDBProperties paramFFSDBProperties, FulfillmentInfo paramFulfillmentInfo)
    throws FFSException
  {
    BPWServer.updateFulfillmentInfo(paramFFSDBProperties, paramFulfillmentInfo);
  }
  
  public void deleteFulfillmentInfo(FFSDBProperties paramFFSDBProperties, int paramInt)
    throws FFSException
  {
    BPWServer.deleteFulfillmentInfo(paramFFSDBProperties, paramInt);
  }
  
  public void setDebugLevel(int paramInt)
    throws FFSException
  {
    BPWServer.setDebugLevel(paramInt);
  }
  
  public void prn(String paramString)
  {
    System.out.println(new Date().toString() + ":BPWAdminBean: " + paramString);
    FFSDebug.log("BPWAdminBean: " + paramString);
  }
  
  public String stack(Throwable paramThrowable)
  {
    StringWriter localStringWriter = new StringWriter();
    paramThrowable.printStackTrace(new PrintWriter(localStringWriter));
    return localStringWriter.toString();
  }
  
  public ProcessingWindowInfo addProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException, RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWAdminImpl localBPWAdminImpl = new BPWAdminImpl();
      return localBPWAdminImpl.addProcessingWindow(paramProcessingWindowInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** BPWAdminBean.addProcessingWindow failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ProcessingWindowInfo modProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException, RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWAdminImpl localBPWAdminImpl = new BPWAdminImpl();
      return localBPWAdminImpl.modProcessingWindow(paramProcessingWindowInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** BPWAdminBean.modProcessingWindow failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ProcessingWindowInfo delProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException, RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWAdminImpl localBPWAdminImpl = new BPWAdminImpl();
      return localBPWAdminImpl.delProcessingWindow(paramProcessingWindowInfo);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** BPWAdminBean.delProcessingWindow failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public ProcessingWindowList getProcessingWindows(ProcessingWindowList paramProcessingWindowList)
    throws FFSException, RemoteException
  {
    if (!BPWServer.isAlive()) {
      throw new RemoteException("Server is not running!");
    }
    try
    {
      BPWAdminImpl localBPWAdminImpl = new BPWAdminImpl();
      return localBPWAdminImpl.getProcessingWindows(paramProcessingWindowList);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** BPWAdminBean.getProcessingWindows failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public InstructionType[] getScheduleConfigByCategory(InstructionType paramInstructionType)
    throws FFSException
  {
    InstructionType[] arrayOfInstructionType = null;
    try
    {
      if (!BPWServer.isAlive()) {
        throw new FFSException("Server is not running!");
      }
      Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
      if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
        arrayOfInstructionType = localScheduler.getScheduleConfigByCategory(paramInstructionType);
      } else {
        try
        {
          BPWServer localBPWServer = BPWServer.getInstance();
          arrayOfInstructionType = localBPWServer.getScheduleConfigByCategoryOnRemoteServer(paramInstructionType);
        }
        catch (Exception localException)
        {
          FFSDebug.log(localException, "", 0);
          throw new FFSException(localException.getMessage());
        }
      }
      return arrayOfInstructionType;
    }
    catch (Throwable localThrowable)
    {
      String str = "*** BPWAdminBean.getScheduleConfigByCategory failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
  }
  
  public void addScheduleConfig(InstructionType paramInstructionType)
    throws FFSException, RemoteException
  {
    BPWServer localBPWServer = BPWServer.getInstance();
    if (localBPWServer == null) {
      throw new FFSException("**** BPWServer not initialized");
    }
    if (!BPWServer.isAlive()) {
      throw new FFSException("**** BPWServer is not running");
    }
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    try
    {
      if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
        localScheduler.addSchedule(paramInstructionType);
      } else {
        try
        {
          localBPWServer.addScheduleOnRemoteServer(paramInstructionType);
        }
        catch (Exception localException)
        {
          FFSDebug.log(localException, "", 0);
          throw new FFSException(localException.getMessage());
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str = "*** BPWAdminBean.addScheduleConfig failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public void deleteScheduleConfig(InstructionType paramInstructionType)
    throws FFSException, RemoteException
  {
    BPWServer localBPWServer = BPWServer.getInstance();
    if (localBPWServer == null) {
      throw new FFSException("**** BPWServer not initialized");
    }
    if (!BPWServer.isAlive()) {
      throw new FFSException("**** BPWServer is not running");
    }
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    try
    {
      if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
        localScheduler.deleteSchedule(paramInstructionType);
      } else {
        try
        {
          localBPWServer.deleteScheduleOnRemoteServer(paramInstructionType);
        }
        catch (Exception localException)
        {
          FFSDebug.log(localException, "", 0);
          throw new FFSException(localException.getMessage());
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str = "*** BPWAdminBean.deleteScheduleConfig failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public CutOffInfo deleteCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException, RemoteException
  {
    CutOffInfo localCutOffInfo = null;
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (!BPWServer.isAlive()) {
        throw new RemoteException("Server is not running!");
      }
      localObject = (Scheduler)FFSRegistry.lookup("SCHEDULER");
      if ((localObject != null) && (((Scheduler)localObject).schIsRunning() == true)) {
        localCutOffInfo = ((Scheduler)localObject).deleteCutOff(paramCutOffInfo);
      } else {
        try
        {
          localCutOffInfo = localBPWServer.deleteCutOffOnRemoteServer(paramCutOffInfo);
        }
        catch (Exception localException)
        {
          FFSDebug.log(localException, "", 0);
          throw new FFSException(localException.getMessage());
        }
      }
      return localCutOffInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = "*** BPWAdminBean.deleteCutOff failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject);
      throw new RemoteException((String)localObject);
    }
  }
  
  public CutOffInfo addCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException, RemoteException
  {
    CutOffInfo localCutOffInfo = null;
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("**** BPWServer is not running");
      }
      localObject = (Scheduler)FFSRegistry.lookup("SCHEDULER");
      if ((localObject != null) && (((Scheduler)localObject).schIsRunning() == true)) {
        localCutOffInfo = ((Scheduler)localObject).addCutOff(paramCutOffInfo);
      } else {
        try
        {
          localCutOffInfo = localBPWServer.addCutOffOnRemoteServer(paramCutOffInfo);
        }
        catch (Exception localException)
        {
          FFSDebug.log(localException, "", 0);
          throw new FFSException(localException.getMessage());
        }
      }
      return localCutOffInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = "*** BPWAdminBean.addCutOff failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject);
      throw new RemoteException((String)localObject);
    }
  }
  
  public CutOffInfo modCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException, RemoteException
  {
    CutOffInfo localCutOffInfo = null;
    try
    {
      BPWServer localBPWServer = BPWServer.getInstance();
      if (localBPWServer == null) {
        throw new FFSException("**** BPWServer not initialized");
      }
      if (!BPWServer.isAlive()) {
        throw new FFSException("**** BPWServer is not running");
      }
      localObject = (Scheduler)FFSRegistry.lookup("SCHEDULER");
      if ((localObject != null) && (((Scheduler)localObject).schIsRunning() == true)) {
        localCutOffInfo = ((Scheduler)localObject).modCutOff(paramCutOffInfo);
      } else {
        try
        {
          localCutOffInfo = localBPWServer.modCutOffOnRemoteServer(paramCutOffInfo);
        }
        catch (Exception localException)
        {
          FFSDebug.log(localException, "", 0);
          throw new FFSException(localException.getMessage());
        }
      }
      return localCutOffInfo;
    }
    catch (Throwable localThrowable)
    {
      Object localObject = "*** BPWAdminBean.modCutOff failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject);
      throw new RemoteException((String)localObject);
    }
  }
  
  /* Error */
  public CutOffInfo getCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException, RemoteException
  {
    // Byte code:
    //   0: invokestatic 44	com/ffusion/ffs/bpw/BPWServer:getInstance	()Lcom/ffusion/ffs/bpw/BPWServer;
    //   3: astore_2
    //   4: invokestatic 40	com/ffusion/ffs/bpw/BPWServer:isAlive	()Z
    //   7: ifne +13 -> 20
    //   10: new 146	java/rmi/RemoteException
    //   13: dup
    //   14: ldc 147
    //   16: invokespecial 148	java/rmi/RemoteException:<init>	(Ljava/lang/String;)V
    //   19: athrow
    //   20: invokestatic 40	com/ffusion/ffs/bpw/BPWServer:isAlive	()Z
    //   23: ifne +13 -> 36
    //   26: new 41	com/ffusion/ffs/interfaces/FFSException
    //   29: dup
    //   30: ldc 87
    //   32: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   35: athrow
    //   36: ldc 88
    //   38: invokestatic 89	com/ffusion/ffs/util/FFSRegistry:lookup	(Ljava/lang/String;)Ljava/lang/Object;
    //   41: checkcast 90	com/ffusion/ffs/scheduling/Scheduler
    //   44: astore_3
    //   45: aload_3
    //   46: ifnull +17 -> 63
    //   49: aload_3
    //   50: invokevirtual 91	com/ffusion/ffs/scheduling/Scheduler:schIsRunning	()Z
    //   53: iconst_1
    //   54: if_icmpne +9 -> 63
    //   57: aload_3
    //   58: aload_1
    //   59: invokevirtual 195	com/ffusion/ffs/scheduling/Scheduler:getCutOff	(Lcom/ffusion/ffs/bpw/interfaces/CutOffInfo;)Lcom/ffusion/ffs/bpw/interfaces/CutOffInfo;
    //   62: areturn
    //   63: aload_2
    //   64: aload_1
    //   65: invokevirtual 196	com/ffusion/ffs/bpw/BPWServer:getCutOffOnRemoteServer	(Lcom/ffusion/ffs/bpw/interfaces/CutOffInfo;)Lcom/ffusion/ffs/bpw/interfaces/CutOffInfo;
    //   68: areturn
    //   69: astore 4
    //   71: aload 4
    //   73: ldc 32
    //   75: iconst_0
    //   76: invokestatic 94	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/Throwable;Ljava/lang/String;I)V
    //   79: new 41	com/ffusion/ffs/interfaces/FFSException
    //   82: dup
    //   83: aload 4
    //   85: invokevirtual 19	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   88: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   91: athrow
    //   92: astore_2
    //   93: new 15	java/lang/StringBuffer
    //   96: dup
    //   97: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   100: ldc 197
    //   102: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   105: aload_2
    //   106: invokestatic 58	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   109: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   112: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   115: astore_3
    //   116: aload_3
    //   117: invokestatic 125	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;)V
    //   120: new 146	java/rmi/RemoteException
    //   123: dup
    //   124: aload_3
    //   125: invokespecial 148	java/rmi/RemoteException:<init>	(Ljava/lang/String;)V
    //   128: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	129	0	this	BPWAdminBean
    //   0	129	1	paramCutOffInfo	CutOffInfo
    //   3	61	2	localBPWServer	BPWServer
    //   92	14	2	localThrowable	Throwable
    //   44	81	3	localObject	Object
    //   69	15	4	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   63	68	69	java/lang/Exception
    //   0	62	92	java/lang/Throwable
    //   63	68	92	java/lang/Throwable
    //   69	92	92	java/lang/Throwable
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CutOffInfoList getCutOffList(com.ffusion.ffs.bpw.interfaces.CutOffInfoList paramCutOffInfoList)
    throws FFSException, RemoteException
  {
    // Byte code:
    //   0: invokestatic 44	com/ffusion/ffs/bpw/BPWServer:getInstance	()Lcom/ffusion/ffs/bpw/BPWServer;
    //   3: astore_2
    //   4: invokestatic 40	com/ffusion/ffs/bpw/BPWServer:isAlive	()Z
    //   7: ifne +13 -> 20
    //   10: new 146	java/rmi/RemoteException
    //   13: dup
    //   14: ldc 147
    //   16: invokespecial 148	java/rmi/RemoteException:<init>	(Ljava/lang/String;)V
    //   19: athrow
    //   20: invokestatic 40	com/ffusion/ffs/bpw/BPWServer:isAlive	()Z
    //   23: ifne +13 -> 36
    //   26: new 41	com/ffusion/ffs/interfaces/FFSException
    //   29: dup
    //   30: ldc 87
    //   32: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   35: athrow
    //   36: ldc 88
    //   38: invokestatic 89	com/ffusion/ffs/util/FFSRegistry:lookup	(Ljava/lang/String;)Ljava/lang/Object;
    //   41: checkcast 90	com/ffusion/ffs/scheduling/Scheduler
    //   44: astore_3
    //   45: aload_3
    //   46: ifnull +17 -> 63
    //   49: aload_3
    //   50: invokevirtual 91	com/ffusion/ffs/scheduling/Scheduler:schIsRunning	()Z
    //   53: iconst_1
    //   54: if_icmpne +9 -> 63
    //   57: aload_3
    //   58: aload_1
    //   59: invokevirtual 198	com/ffusion/ffs/scheduling/Scheduler:getCutOffList	(Lcom/ffusion/ffs/bpw/interfaces/CutOffInfoList;)Lcom/ffusion/ffs/bpw/interfaces/CutOffInfoList;
    //   62: areturn
    //   63: aload_2
    //   64: aload_1
    //   65: invokevirtual 199	com/ffusion/ffs/bpw/BPWServer:getCutOffListOnRemoteServer	(Lcom/ffusion/ffs/bpw/interfaces/CutOffInfoList;)Lcom/ffusion/ffs/bpw/interfaces/CutOffInfoList;
    //   68: areturn
    //   69: astore 4
    //   71: aload 4
    //   73: ldc 32
    //   75: iconst_0
    //   76: invokestatic 94	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/Throwable;Ljava/lang/String;I)V
    //   79: new 41	com/ffusion/ffs/interfaces/FFSException
    //   82: dup
    //   83: aload 4
    //   85: invokevirtual 19	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   88: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   91: athrow
    //   92: astore_2
    //   93: new 15	java/lang/StringBuffer
    //   96: dup
    //   97: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   100: ldc 200
    //   102: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   105: aload_2
    //   106: invokestatic 58	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   109: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   112: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   115: astore_3
    //   116: aload_3
    //   117: invokestatic 125	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;)V
    //   120: new 146	java/rmi/RemoteException
    //   123: dup
    //   124: aload_3
    //   125: invokespecial 148	java/rmi/RemoteException:<init>	(Ljava/lang/String;)V
    //   128: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	129	0	this	BPWAdminBean
    //   0	129	1	paramCutOffInfoList	com.ffusion.ffs.bpw.interfaces.CutOffInfoList
    //   3	61	2	localBPWServer	BPWServer
    //   92	14	2	localThrowable	Throwable
    //   44	81	3	localObject	Object
    //   69	15	4	localException	Exception
    // Exception table:
    //   from	to	target	type
    //   63	68	69	java/lang/Exception
    //   0	62	92	java/lang/Throwable
    //   63	68	92	java/lang/Throwable
    //   69	92	92	java/lang/Throwable
  }
  
  /* Error */
  public ScheduleCategoryInfo getScheduleCategoryInfo(String paramString1, String paramString2)
    throws FFSException
  {
    // Byte code:
    //   0: invokestatic 40	com/ffusion/ffs/bpw/BPWServer:isAlive	()Z
    //   3: ifne +13 -> 16
    //   6: new 41	com/ffusion/ffs/interfaces/FFSException
    //   9: dup
    //   10: ldc 147
    //   12: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   15: athrow
    //   16: ldc 88
    //   18: invokestatic 89	com/ffusion/ffs/util/FFSRegistry:lookup	(Ljava/lang/String;)Ljava/lang/Object;
    //   21: checkcast 90	com/ffusion/ffs/scheduling/Scheduler
    //   24: astore_3
    //   25: aload_3
    //   26: ifnull +18 -> 44
    //   29: aload_3
    //   30: invokevirtual 91	com/ffusion/ffs/scheduling/Scheduler:schIsRunning	()Z
    //   33: iconst_1
    //   34: if_icmpne +10 -> 44
    //   37: aload_3
    //   38: aload_1
    //   39: aload_2
    //   40: invokevirtual 201	com/ffusion/ffs/scheduling/Scheduler:getScheduleCategoryInfo	(Ljava/lang/String;Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/ScheduleCategoryInfo;
    //   43: areturn
    //   44: invokestatic 44	com/ffusion/ffs/bpw/BPWServer:getInstance	()Lcom/ffusion/ffs/bpw/BPWServer;
    //   47: astore 4
    //   49: aload 4
    //   51: aload_1
    //   52: aload_2
    //   53: invokevirtual 202	com/ffusion/ffs/bpw/BPWServer:getScheduleCategoryInfoOnRemoteServer	(Ljava/lang/String;Ljava/lang/String;)Lcom/ffusion/ffs/bpw/interfaces/ScheduleCategoryInfo;
    //   56: areturn
    //   57: astore 4
    //   59: new 15	java/lang/StringBuffer
    //   62: dup
    //   63: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   66: ldc 203
    //   68: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   71: aload 4
    //   73: invokestatic 58	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   76: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   79: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   82: astore 5
    //   84: aload 5
    //   86: iconst_0
    //   87: invokestatic 128	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;I)V
    //   90: new 41	com/ffusion/ffs/interfaces/FFSException
    //   93: dup
    //   94: aload 4
    //   96: invokevirtual 19	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   99: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   102: athrow
    //   103: astore_3
    //   104: new 15	java/lang/StringBuffer
    //   107: dup
    //   108: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   111: ldc 203
    //   113: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   116: aload_3
    //   117: invokestatic 58	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   120: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   123: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   126: astore 4
    //   128: aload 4
    //   130: iconst_0
    //   131: invokestatic 128	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;I)V
    //   134: new 41	com/ffusion/ffs/interfaces/FFSException
    //   137: dup
    //   138: aload 4
    //   140: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   143: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	144	0	this	BPWAdminBean
    //   0	144	1	paramString1	String
    //   0	144	2	paramString2	String
    //   24	14	3	localScheduler	Scheduler
    //   103	14	3	localThrowable	Throwable
    //   47	3	4	localBPWServer	BPWServer
    //   57	38	4	localException	Exception
    //   126	13	4	str1	String
    //   82	3	5	str2	String
    // Exception table:
    //   from	to	target	type
    //   44	56	57	java/lang/Exception
    //   0	43	103	java/lang/Throwable
    //   44	56	103	java/lang/Throwable
    //   57	103	103	java/lang/Throwable
  }
  
  public ScheduleCategoryInfo modScheduleCategoryInfo(ScheduleCategoryInfo paramScheduleCategoryInfo)
    throws FFSException
  {
    try
    {
      if (!BPWServer.isAlive()) {
        throw new FFSException("Server is not running!");
      }
      Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
      if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
        paramScheduleCategoryInfo = localScheduler.modScheduleCategoryInfo(paramScheduleCategoryInfo);
      } else {
        try
        {
          BPWServer localBPWServer = BPWServer.getInstance();
          paramScheduleCategoryInfo = localBPWServer.modScheduleCategoryInfoOnRemoteServer(paramScheduleCategoryInfo);
        }
        catch (Exception localException)
        {
          String str2 = "*** BPWAdminBean.modScheduleCategoryInfo failed:" + FFSDebug.stackTrace(localException);
          FFSDebug.log(str2, 0);
          throw new FFSException(localException.getMessage());
        }
      }
      return paramScheduleCategoryInfo;
    }
    catch (Throwable localThrowable)
    {
      String str1 = "*** BPWAdminBean.modScheduleCategoryInfo failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1, 0);
      throw new FFSException(str1);
    }
  }
  
  public void cleanup(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException
  {
    try
    {
      if (!BPWServer.isAlive()) {
        throw new RemoteException("Server is not running!");
      }
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.cleanup(paramString1, paramString2, paramInt, paramHashMap);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** BPWEngine.cleanup failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public void cleanup(String paramString, ArrayList paramArrayList1, ArrayList paramArrayList2, HashMap paramHashMap)
    throws EJBException, RemoteException
  {
    try
    {
      if (!BPWServer.isAlive()) {
        throw new RemoteException("Server is not running!");
      }
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.cleanup(paramString, paramArrayList1, paramArrayList2, paramHashMap);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** BPWEngine.cleanup failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  public void cleanup(ArrayList paramArrayList1, ArrayList paramArrayList2, ArrayList paramArrayList3, HashMap paramHashMap)
    throws EJBException, RemoteException
  {
    try
    {
      if (!BPWServer.isAlive()) {
        throw new RemoteException("Server is not running!");
      }
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      localBPWEngine.cleanup(paramArrayList1, paramArrayList2, paramArrayList3, paramHashMap);
    }
    catch (Throwable localThrowable)
    {
      String str = "*** BPWEngine.cleanup failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str);
      throw new RemoteException(str);
    }
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.CutOffActivityInfoList getCutOffActivityList(com.ffusion.ffs.bpw.interfaces.CutOffActivityInfoList paramCutOffActivityInfoList)
    throws FFSException
  {
    // Byte code:
    //   0: ldc 211
    //   2: astore_2
    //   3: new 15	java/lang/StringBuffer
    //   6: dup
    //   7: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   10: aload_2
    //   11: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   14: ldc 212
    //   16: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   19: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   22: bipush 6
    //   24: invokestatic 128	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;I)V
    //   27: invokestatic 40	com/ffusion/ffs/bpw/BPWServer:isAlive	()Z
    //   30: ifne +13 -> 43
    //   33: new 41	com/ffusion/ffs/interfaces/FFSException
    //   36: dup
    //   37: ldc 147
    //   39: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   42: athrow
    //   43: ldc 88
    //   45: invokestatic 89	com/ffusion/ffs/util/FFSRegistry:lookup	(Ljava/lang/String;)Ljava/lang/Object;
    //   48: checkcast 90	com/ffusion/ffs/scheduling/Scheduler
    //   51: astore_3
    //   52: aload_3
    //   53: ifnull +17 -> 70
    //   56: aload_3
    //   57: invokevirtual 91	com/ffusion/ffs/scheduling/Scheduler:schIsRunning	()Z
    //   60: iconst_1
    //   61: if_icmpne +9 -> 70
    //   64: aload_3
    //   65: aload_1
    //   66: invokevirtual 213	com/ffusion/ffs/scheduling/Scheduler:getCutOffActivityList	(Lcom/ffusion/ffs/bpw/interfaces/CutOffActivityInfoList;)Lcom/ffusion/ffs/bpw/interfaces/CutOffActivityInfoList;
    //   69: areturn
    //   70: invokestatic 44	com/ffusion/ffs/bpw/BPWServer:getInstance	()Lcom/ffusion/ffs/bpw/BPWServer;
    //   73: astore 4
    //   75: aload 4
    //   77: aload_1
    //   78: invokevirtual 214	com/ffusion/ffs/bpw/BPWServer:getCutOffActivityListOnRemoteServer	(Lcom/ffusion/ffs/bpw/interfaces/CutOffActivityInfoList;)Lcom/ffusion/ffs/bpw/interfaces/CutOffActivityInfoList;
    //   81: areturn
    //   82: astore 4
    //   84: new 15	java/lang/StringBuffer
    //   87: dup
    //   88: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   91: aload_2
    //   92: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   95: ldc 215
    //   97: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   100: aload 4
    //   102: invokestatic 58	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   105: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   108: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   111: astore 5
    //   113: aload 5
    //   115: invokestatic 125	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;)V
    //   118: aload 4
    //   120: athrow
    //   121: astore 4
    //   123: new 15	java/lang/StringBuffer
    //   126: dup
    //   127: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   130: aload_2
    //   131: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   134: ldc 215
    //   136: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   139: aload 4
    //   141: invokestatic 58	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   144: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   147: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   150: astore 5
    //   152: aload 5
    //   154: invokestatic 125	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;)V
    //   157: new 41	com/ffusion/ffs/interfaces/FFSException
    //   160: dup
    //   161: aload 5
    //   163: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   166: athrow
    //   167: astore_3
    //   168: new 15	java/lang/StringBuffer
    //   171: dup
    //   172: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   175: aload_2
    //   176: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   179: ldc 215
    //   181: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   184: aload_3
    //   185: invokestatic 58	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   188: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   191: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   194: astore 4
    //   196: aload 4
    //   198: invokestatic 125	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;)V
    //   201: aload_3
    //   202: athrow
    //   203: astore_3
    //   204: new 15	java/lang/StringBuffer
    //   207: dup
    //   208: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   211: aload_2
    //   212: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   215: ldc 215
    //   217: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   220: aload_3
    //   221: invokestatic 58	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   224: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   227: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   230: astore 4
    //   232: aload 4
    //   234: invokestatic 125	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;)V
    //   237: new 41	com/ffusion/ffs/interfaces/FFSException
    //   240: dup
    //   241: aload 4
    //   243: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   246: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	247	0	this	BPWAdminBean
    //   0	247	1	paramCutOffActivityInfoList	com.ffusion.ffs.bpw.interfaces.CutOffActivityInfoList
    //   2	210	2	str1	String
    //   51	14	3	localScheduler	Scheduler
    //   167	35	3	localFFSException1	FFSException
    //   203	18	3	localThrowable	Throwable
    //   73	3	4	localBPWServer	BPWServer
    //   82	37	4	localFFSException2	FFSException
    //   121	19	4	localException	Exception
    //   194	48	4	str2	String
    //   111	51	5	str3	String
    // Exception table:
    //   from	to	target	type
    //   70	81	82	com/ffusion/ffs/interfaces/FFSException
    //   70	81	121	java/lang/Exception
    //   27	69	167	com/ffusion/ffs/interfaces/FFSException
    //   70	81	167	com/ffusion/ffs/interfaces/FFSException
    //   82	167	167	com/ffusion/ffs/interfaces/FFSException
    //   27	69	203	java/lang/Throwable
    //   70	81	203	java/lang/Throwable
    //   82	167	203	java/lang/Throwable
  }
  
  /* Error */
  public com.ffusion.ffs.bpw.interfaces.ScheduleActivityList getScheduleActivityList(com.ffusion.ffs.bpw.interfaces.ScheduleActivityList paramScheduleActivityList)
    throws FFSException
  {
    // Byte code:
    //   0: ldc 216
    //   2: astore_2
    //   3: new 15	java/lang/StringBuffer
    //   6: dup
    //   7: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   10: aload_2
    //   11: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   14: ldc 212
    //   16: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   19: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   22: bipush 6
    //   24: invokestatic 128	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;I)V
    //   27: invokestatic 40	com/ffusion/ffs/bpw/BPWServer:isAlive	()Z
    //   30: ifne +13 -> 43
    //   33: new 41	com/ffusion/ffs/interfaces/FFSException
    //   36: dup
    //   37: ldc 147
    //   39: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   42: athrow
    //   43: ldc 88
    //   45: invokestatic 89	com/ffusion/ffs/util/FFSRegistry:lookup	(Ljava/lang/String;)Ljava/lang/Object;
    //   48: checkcast 90	com/ffusion/ffs/scheduling/Scheduler
    //   51: astore_3
    //   52: aload_3
    //   53: ifnull +17 -> 70
    //   56: aload_3
    //   57: invokevirtual 91	com/ffusion/ffs/scheduling/Scheduler:schIsRunning	()Z
    //   60: iconst_1
    //   61: if_icmpne +9 -> 70
    //   64: aload_3
    //   65: aload_1
    //   66: invokevirtual 217	com/ffusion/ffs/scheduling/Scheduler:getScheduleActivityList	(Lcom/ffusion/ffs/bpw/interfaces/ScheduleActivityList;)Lcom/ffusion/ffs/bpw/interfaces/ScheduleActivityList;
    //   69: areturn
    //   70: invokestatic 44	com/ffusion/ffs/bpw/BPWServer:getInstance	()Lcom/ffusion/ffs/bpw/BPWServer;
    //   73: astore 4
    //   75: aload 4
    //   77: aload_1
    //   78: invokevirtual 218	com/ffusion/ffs/bpw/BPWServer:getScheduleActivityListOnRemoteServer	(Lcom/ffusion/ffs/bpw/interfaces/ScheduleActivityList;)Lcom/ffusion/ffs/bpw/interfaces/ScheduleActivityList;
    //   81: areturn
    //   82: astore 4
    //   84: new 15	java/lang/StringBuffer
    //   87: dup
    //   88: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   91: aload_2
    //   92: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   95: ldc 215
    //   97: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   100: aload 4
    //   102: invokestatic 58	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   105: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   108: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   111: astore 5
    //   113: aload 5
    //   115: invokestatic 125	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;)V
    //   118: aload 4
    //   120: athrow
    //   121: astore 4
    //   123: new 15	java/lang/StringBuffer
    //   126: dup
    //   127: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   130: aload_2
    //   131: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   134: ldc 215
    //   136: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   139: aload 4
    //   141: invokestatic 58	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   144: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   147: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   150: astore 5
    //   152: aload 5
    //   154: invokestatic 125	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;)V
    //   157: new 41	com/ffusion/ffs/interfaces/FFSException
    //   160: dup
    //   161: aload 5
    //   163: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   166: athrow
    //   167: astore_3
    //   168: new 15	java/lang/StringBuffer
    //   171: dup
    //   172: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   175: aload_2
    //   176: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   179: ldc 215
    //   181: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   184: aload_3
    //   185: invokestatic 58	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   188: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   191: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   194: astore 4
    //   196: aload 4
    //   198: invokestatic 125	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;)V
    //   201: aload_3
    //   202: athrow
    //   203: astore_3
    //   204: new 15	java/lang/StringBuffer
    //   207: dup
    //   208: invokespecial 16	java/lang/StringBuffer:<init>	()V
    //   211: aload_2
    //   212: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   215: ldc 215
    //   217: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   220: aload_3
    //   221: invokestatic 58	com/ffusion/ffs/util/FFSDebug:stackTrace	(Ljava/lang/Throwable;)Ljava/lang/String;
    //   224: invokevirtual 18	java/lang/StringBuffer:append	(Ljava/lang/String;)Ljava/lang/StringBuffer;
    //   227: invokevirtual 20	java/lang/StringBuffer:toString	()Ljava/lang/String;
    //   230: astore 4
    //   232: aload 4
    //   234: invokestatic 125	com/ffusion/ffs/util/FFSDebug:log	(Ljava/lang/String;)V
    //   237: new 41	com/ffusion/ffs/interfaces/FFSException
    //   240: dup
    //   241: aload 4
    //   243: invokespecial 43	com/ffusion/ffs/interfaces/FFSException:<init>	(Ljava/lang/String;)V
    //   246: athrow
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	247	0	this	BPWAdminBean
    //   0	247	1	paramScheduleActivityList	com.ffusion.ffs.bpw.interfaces.ScheduleActivityList
    //   2	210	2	str1	String
    //   51	14	3	localScheduler	Scheduler
    //   167	35	3	localFFSException1	FFSException
    //   203	18	3	localThrowable	Throwable
    //   73	3	4	localBPWServer	BPWServer
    //   82	37	4	localFFSException2	FFSException
    //   121	19	4	localException	Exception
    //   194	48	4	str2	String
    //   111	51	5	str3	String
    // Exception table:
    //   from	to	target	type
    //   70	81	82	com/ffusion/ffs/interfaces/FFSException
    //   70	81	121	java/lang/Exception
    //   27	69	167	com/ffusion/ffs/interfaces/FFSException
    //   70	81	167	com/ffusion/ffs/interfaces/FFSException
    //   82	167	167	com/ffusion/ffs/interfaces/FFSException
    //   27	69	203	java/lang/Throwable
    //   70	81	203	java/lang/Throwable
    //   82	167	203	java/lang/Throwable
  }
  
  public void rerunCutOff(String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException
  {
    String str1 = "BPWAdminBean.rerunCutOff :";
    FFSDebug.log(str1 + "start", 6);
    try
    {
      if (!BPWServer.isAlive()) {
        throw new FFSException("Server is not running!");
      }
      Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
      if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
        localScheduler.rerunCutOff(paramString1, paramString2, paramString3, paramString4);
      } else {
        try
        {
          BPWServer localBPWServer = BPWServer.getInstance();
          localBPWServer.rerunCutOffOnRemoteServer(paramString1, paramString2, paramString3, paramString4);
        }
        catch (FFSException localFFSException2)
        {
          str3 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException2);
          FFSDebug.log(str3);
          throw localFFSException2;
        }
        catch (Exception localException)
        {
          String str3 = str1 + "failed:" + FFSDebug.stackTrace(localException);
          FFSDebug.log(str3);
          throw new FFSException(str3);
        }
      }
    }
    catch (FFSException localFFSException1)
    {
      str2 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException1);
      FFSDebug.log(str2);
      throw localFFSException1;
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
    FFSDebug.log(str1 + "end", 6);
  }
  
  public String getGeneratedFileName(String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "BPWAdminBean.getGeneratedFileName :";
    FFSDebug.log(str1 + "start", 6);
    String str2 = null;
    try
    {
      if (!BPWServer.isAlive()) {
        throw new FFSException("Server is not running!");
      }
      Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
      if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
        str2 = localScheduler.getGeneratedFileName(paramString1, paramString2, paramString3);
      } else {
        try
        {
          BPWServer localBPWServer = BPWServer.getInstance();
          str2 = localBPWServer.getGeneratedFileNameOnRemoteServer(paramString1, paramString2, paramString3);
        }
        catch (FFSException localFFSException2)
        {
          str4 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException2);
          FFSDebug.log(str4);
          throw localFFSException2;
        }
        catch (Exception localException)
        {
          String str4 = str1 + "failed:" + FFSDebug.stackTrace(localException);
          FFSDebug.log(str4);
          throw new FFSException(str4);
        }
      }
    }
    catch (FFSException localFFSException1)
    {
      str3 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException1);
      FFSDebug.log(str3);
      throw localFFSException1;
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + "failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3);
      throw new FFSException(str3);
    }
    FFSDebug.log(str1 + "end, fileName =" + str2, 6);
    return str2;
  }
  
  public SmartCalendarFile importCalendar(SmartCalendarFile paramSmartCalendarFile)
    throws FFSException
  {
    String str1 = "BPWAdminBean.importCalendar :";
    String str2 = paramSmartCalendarFile.getFiId();
    FFSDebug.log(str1 + "start for smart calendar with FIID = " + str2, 6);
    try
    {
      SmartCalendar.addSCCalendar(paramSmartCalendarFile);
    }
    catch (Exception localException)
    {
      String str3 = str1 + "failed for smart calendar with FIID = " + str2 + FFSDebug.stackTrace(localException);
      FFSDebug.log(str3);
      throw new FFSException(str3);
    }
    FFSDebug.log(str1 + "end for smart calendar with FIID =" + str2, 6);
    return paramSmartCalendarFile;
  }
  
  public SmartCalendarFile exportCalendar(SmartCalendarFile paramSmartCalendarFile)
    throws FFSException
  {
    String str1 = "BPWAdminBean.exportCalendar :";
    String str2 = paramSmartCalendarFile.getFiId();
    FFSDebug.log(str1 + "start for smart calendar with FIID = " + str2, 6);
    try
    {
      paramSmartCalendarFile = SmartCalendar.getSCFileFromSCDB(paramSmartCalendarFile);
    }
    catch (Exception localException)
    {
      String str3 = str1 + "failed for smart calendar with FIID = " + str2 + FFSDebug.stackTrace(localException);
      FFSDebug.log(str3);
      throw new FFSException(str3);
    }
    FFSDebug.log(str1 + "end for smart calendar with FIID =" + str2, 6);
    return paramSmartCalendarFile;
  }
  
  public PayeeInfo addGlobalPayee(PayeeInfo paramPayeeInfo)
    throws FFSException
  {
    String str1 = "BPWAdminBean.addGlobalPayee :";
    FFSDebug.log(str1 + "start", 6);
    try
    {
      if (!BPWServer.isAlive()) {
        throw new FFSException("Server is not running!");
      }
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      paramPayeeInfo = localBPWEngine.addGlobalPayee(paramPayeeInfo);
    }
    catch (FFSException localFFSException)
    {
      str2 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
    FFSDebug.log(str1 + "end", 6);
    return paramPayeeInfo;
  }
  
  public PayeeInfo deleteGlobalPayee(PayeeInfo paramPayeeInfo)
    throws FFSException
  {
    String str1 = "BPWAdminBean.deleteGlobalPayee :";
    FFSDebug.log(str1 + "start", 6);
    try
    {
      if (!BPWServer.isAlive()) {
        throw new FFSException("Server is not running!");
      }
      BPWEngine localBPWEngine = (BPWEngine)FFSRegistry.lookup("BPWENGINE");
      paramPayeeInfo = localBPWEngine.deleteGlobalPayee(paramPayeeInfo);
    }
    catch (FFSException localFFSException)
    {
      str2 = str1 + "failed:" + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
    FFSDebug.log(str1 + "end", 6);
    return paramPayeeInfo;
  }
  
  static
  {
    FFSProperties localFFSProperties = null;
    BPWAdminBean localBPWAdminBean = new BPWAdminBean();
    try
    {
      localFFSProperties = BPWServer.getProps();
    }
    catch (Exception localException1)
    {
      localBPWAdminBean.prn("Failed to get BPW Server properties for auto start. Error: " + localBPWAdminBean.stack(localException1));
      localBPWAdminBean.prn("Try to start BPW Server manually using BPW admin console");
    }
    try
    {
      if (localFFSProperties != null)
      {
        String str1 = localFFSProperties.getProperty("AutoStart", "false");
        localBPWAdminBean.prn("AutoStart: " + str1);
        if (str1.equalsIgnoreCase("true"))
        {
          localBPWAdminBean.prn("\t Starting BPW Server Please wait.......");
          boolean bool = localBPWAdminBean.startServer(localFFSProperties);
          localBPWAdminBean.prn("\t BPW Server started: " + bool);
        }
        else
        {
          localBPWAdminBean.prn("BPW Server setup not to start with the start of the App Server.");
        }
      }
    }
    catch (Exception localException2)
    {
      String str2 = "Failed to start BPW Server with the startup of the AppServer. Error: " + localBPWAdminBean.stack(localException2);
      localBPWAdminBean.prn(str2);
    }
    finally
    {
      localFFSProperties = null;
      localBPWAdminBean = null;
    }
  }
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB.BPWAdminBean
 * JD-Core Version:    0.7.0.1
 */