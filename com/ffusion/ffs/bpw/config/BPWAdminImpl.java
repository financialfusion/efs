package com.ffusion.ffs.bpw.config;

import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.db.DBInstructionType;
import com.ffusion.ffs.bpw.db.DBPropertyConfig;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Fulfillment;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PayeeToRoute;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.DBConnectionInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowList;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.ServerGeneralConfig;
import com.ffusion.ffs.bpw.util.BPWConfigWrapper;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.config.ConnPoolInfo;
import com.ffusion.ffs.config.DBConnInfo;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSDBConst;
import com.ffusion.ffs.db.FFSDBProperties;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

public class BPWAdminImpl
  implements BPWAdmin, FFSDBConst, DBConsts
{
  FFSProperties cW;
  private FFSDBProperties cP = null;
  private ArrayList cX = null;
  private HashMap cY = null;
  private FulfillmentInfo[] cU = null;
  private DBConnectionInfo cT = null;
  private DBConnectionInfo cR = null;
  private boolean cQ;
  private boolean cV;
  private ArrayList cS = null;
  private ServerGeneralConfig cZ = null;
  
  public BPWAdminImpl(FFSProperties paramFFSProperties)
    throws FFSException
  {
    this.cP = new FFSDBProperties(paramFFSProperties);
    this.cP._connInfo._dbConnInfo._connectionAutoCommit = false;
    b();
  }
  
  public BPWAdminImpl() {}
  
  public void releaseResources()
  {
    this.cW.clear();
    this.cS.clear();
    this.cY.clear();
    this.cX.clear();
    this.cX = null;
    this.cY = null;
    this.cS = null;
    this.cU = null;
    this.cW = null;
  }
  
  private void b()
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getMyOwnConnection(this.cP);
    try
    {
      try
      {
        jdMethod_long();
      }
      catch (Exception localException)
      {
        String str1 = "Error while initializing BPWConfigWrapper";
        throw new FFSException(localException, str1);
      }
      BPWFIInfo[] arrayOfBPWFIInfo = jdMethod_if(localFFSConnectionHolder);
      this.cS = new ArrayList(arrayOfBPWFIInfo.length);
      this.cS.addAll(Arrays.asList(arrayOfBPWFIInfo));
      this.cY = new HashMap();
      for (int i = 0; i < this.cS.size(); i++)
      {
        localObject1 = ((BPWFIInfo)this.cS.get(i)).getFIId();
        ArrayList localArrayList = jdMethod_byte(localFFSConnectionHolder, (String)localObject1);
        this.cY.put(localObject1, localArrayList);
      }
      this.cW = DBPropertyConfig.readPropertyConfig(localFFSConnectionHolder);
      String str2 = (String)this.cW.remove("BPW.DB.Configured");
      this.cV = Boolean.valueOf(str2).booleanValue();
      str2 = (String)this.cW.remove("MB.Runtime.Configured");
      this.cQ = Boolean.valueOf(str2).booleanValue();
      this.cR = DBPropertyConfig.getMBRuntimeDBInfo(this.cW);
      this.cT = DBPropertyConfig.getBPWServerDBInfo(this.cW);
      Object localObject1 = jdMethod_null();
      this.cX = new ArrayList(localObject1.length);
      this.cX.addAll(Arrays.asList((Object[])localObject1));
      this.cZ = a(this.cX);
      jdMethod_void();
      localFFSConnectionHolder.conn.commit();
    }
    finally
    {
      localFFSConnectionHolder.conn.close();
    }
  }
  
  private void jdMethod_long()
    throws Exception
  {
    try
    {
      String str = "bpwconfig.xml";
      BPWConfigWrapper localBPWConfigWrapper = BPWConfigWrapper.getInstance();
      localBPWConfigWrapper.initialize(str, true);
      FFSRegistry.bind("BPWCONFIGWRAPPER", localBPWConfigWrapper);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fail to start BPWConfigWrapper:" + localException.toString());
      FFSDebug.console("*** Fail to start BPWConfigWrapper:" + localException.toString());
      throw localException;
    }
  }
  
  private static ServerGeneralConfig a(ArrayList paramArrayList)
  {
    ServerGeneralConfig localServerGeneralConfig = new ServerGeneralConfig();
    localServerGeneralConfig.reset();
    for (int i = 0; i < paramArrayList.size(); i++) {
      if (((PropertyConfig)paramArrayList.get(i)).serverName.equals("ALL_SERVERS"))
      {
        PropertyConfig localPropertyConfig = (PropertyConfig)paramArrayList.remove(i);
        localServerGeneralConfig.retries = localPropertyConfig.Retries;
        localServerGeneralConfig.timeout = localPropertyConfig.Timeout;
        localServerGeneralConfig.minuteNotToRecover = localPropertyConfig.MinuteNotToRecover;
        localServerGeneralConfig.fundsAllocRetries = localPropertyConfig.FundsAllocRetries;
        localServerGeneralConfig.batchSize = localPropertyConfig.BatchSize;
        localServerGeneralConfig.enforceEnrollment = localPropertyConfig.EnforceEnrollment;
        localServerGeneralConfig.enforcePayment = localPropertyConfig.EnforcePayment;
        localServerGeneralConfig.startPayeeListID = localPropertyConfig.StartPayeeListID;
        localServerGeneralConfig.noImmediateTransfer = localPropertyConfig.NoImmediateTransfer;
        localServerGeneralConfig.useExtdPayeeID = localPropertyConfig.UseExtdPayeeID;
        localServerGeneralConfig.purgeWakeupTime = localPropertyConfig.PurgeWakeupTime;
        localServerGeneralConfig.closedInstructionAge = localPropertyConfig.DayToPurgeClosedInstruction;
        localServerGeneralConfig.eventHistoryAge = localPropertyConfig.DayToPurgeEventHistory;
        localServerGeneralConfig.activityLogAge = localPropertyConfig.DayToPurgeActivityLog;
        localServerGeneralConfig.supportImmediatePmt = localPropertyConfig.SupportImmediatePmt;
        localServerGeneralConfig.clusterOn = localPropertyConfig.SupportCluster;
        localServerGeneralConfig.clusterDelay = localPropertyConfig.SchWaitTime;
        localServerGeneralConfig.otherProperties = localPropertyConfig.otherProperties;
        break;
      }
    }
    return localServerGeneralConfig;
  }
  
  private void jdMethod_void()
  {
    for (int i = 0; i < this.cX.size(); i++)
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)this.cX.get(i);
      this.cZ.cascade(localPropertyConfig);
    }
  }
  
  public PropertyConfig[] getAllServers()
  {
    return (PropertyConfig[])this.cX.toArray(new PropertyConfig[this.cX.size()]);
  }
  
  public void addServer(PropertyConfig paramPropertyConfig)
    throws FFSException
  {
    this.cX.add(paramPropertyConfig);
    doDeploy();
  }
  
  public PropertyConfig updateServer(PropertyConfig paramPropertyConfig)
    throws FFSException
  {
    if (paramPropertyConfig == null) {
      return null;
    }
    String str = paramPropertyConfig.serverName;
    for (int i = 0; i < this.cX.size(); i++)
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)this.cX.get(i);
      if (localPropertyConfig.serverName.equalsIgnoreCase(str))
      {
        this.cX.set(i, paramPropertyConfig);
        doDeploy();
        break;
      }
    }
    return jdMethod_long(str);
  }
  
  private PropertyConfig jdMethod_long(String paramString)
  {
    for (int i = 0; i < this.cX.size(); i++)
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)this.cX.get(i);
      if (localPropertyConfig.serverName.equalsIgnoreCase(paramString)) {
        return localPropertyConfig;
      }
    }
    return null;
  }
  
  public void deleteServer(String paramString)
    throws FFSException
  {
    for (int i = 0; i < this.cX.size(); i++)
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)this.cX.get(i);
      if (localPropertyConfig.serverName.equalsIgnoreCase(paramString))
      {
        this.cX.remove(i);
        doDeploy();
      }
    }
  }
  
  public PropertyConfig getPropertyConfig(String paramString)
    throws FFSException
  {
    PropertyConfig localPropertyConfig = null;
    if ((paramString == null) || (paramString.length() <= 0)) {
      paramString = "Default Server";
    }
    for (int i = 0; i < this.cX.size(); i++) {
      if (((PropertyConfig)this.cX.get(i)).serverName.equalsIgnoreCase(paramString))
      {
        localPropertyConfig = (PropertyConfig)this.cX.get(i);
        break;
      }
    }
    if (localPropertyConfig == null) {
      throw new FFSException("Server '" + paramString + "' not found.");
    }
    return localPropertyConfig;
  }
  
  public void doDeploy()
    throws FFSException
  {
    FFSProperties localFFSProperties = new FFSProperties();
    PropertyConfig[] arrayOfPropertyConfig = (PropertyConfig[])this.cX.toArray(new PropertyConfig[this.cX.size()]);
    a(localFFSProperties, arrayOfPropertyConfig);
    a(localFFSProperties);
  }
  
  private void a(FFSProperties paramFFSProperties, PropertyConfig[] paramArrayOfPropertyConfig)
  {
    for (int i = 0; i < paramArrayOfPropertyConfig.length; i++) {
      a(paramFFSProperties, paramArrayOfPropertyConfig[i]);
    }
    paramFFSProperties.put("MB.Runtime.Configured", this.cQ ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
    paramFFSProperties.put("MBConn_dbType", this.cR.dbType);
    paramFFSProperties.put("MBConn_host", this.cR.host);
    if (this.cR.port < 0) {
      paramFFSProperties.put("MBConn_port", "");
    } else {
      paramFFSProperties.put("MBConn_port", Integer.toString(this.cR.port));
    }
    paramFFSProperties.put("MBConn_dbname", this.cR.dbName);
    if ((this.cR.userName != null) && (this.cR.userName.length() > 0)) {
      paramFFSProperties.put("MBConn_user", this.cR.userName);
    }
    if ((this.cR.password != null) && (this.cR.password.length() > 0)) {
      paramFFSProperties.put("MBConn_passwd", this.cR.password);
    }
    if ((this.cR.url != null) && (this.cR.url.length() > 0)) {
      paramFFSProperties.put("MBConn_url", this.cR.url);
    }
    paramFFSProperties.put("BPW.DB.Configured", this.cV ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
    paramFFSProperties.put("BPW.DB.NAME", this.cT.dbName);
    paramFFSProperties.put("BPW.DB.TYPE", this.cT.dbType);
    paramFFSProperties.put("BPW.DB.HOST", this.cT.host);
    if (this.cT.port < 0) {
      paramFFSProperties.put("BPW.DB.PORT", "");
    } else {
      paramFFSProperties.put("BPW.DB.PORT", Integer.toString(this.cT.port));
    }
    if ((this.cT.userName != null) && (this.cT.userName.length() > 0)) {
      paramFFSProperties.put("BPW.DB.USER", this.cT.userName);
    }
    if ((this.cT.password != null) && (this.cT.password.length() > 0)) {
      paramFFSProperties.put("BPW.DB.PASSWORD", this.cT.password);
    }
    String str1 = "ALL_SERVERS|";
    paramFFSProperties.put(str1 + "Retries", Integer.toString(this.cZ.retries));
    paramFFSProperties.put(str1 + "Timeout", Integer.toString(this.cZ.timeout));
    paramFFSProperties.put(str1 + "MinuteNotToRecover", Integer.toString(this.cZ.minuteNotToRecover));
    paramFFSProperties.put(str1 + "FundsAllocRetries", Integer.toString(this.cZ.fundsAllocRetries));
    paramFFSProperties.put(str1 + "BatchSize", Integer.toString(this.cZ.batchSize));
    paramFFSProperties.put(str1 + "EnforcePayment", this.cZ.enforcePayment ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
    paramFFSProperties.put(str1 + "EnforceEnrollment", this.cZ.enforceEnrollment ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
    paramFFSProperties.put(str1 + "StartPayeeListID", Integer.toString(this.cZ.startPayeeListID));
    paramFFSProperties.put(str1 + "NoImmediateTransfer", this.cZ.noImmediateTransfer ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
    paramFFSProperties.put(str1 + "UseExtdPayeeID", this.cZ.useExtdPayeeID ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
    paramFFSProperties.put(str1 + "PurgeWakeupTime", this.cZ.purgeWakeupTime);
    paramFFSProperties.put(str1 + "DayToPurgeClosedInstruction", Integer.toString(this.cZ.closedInstructionAge));
    paramFFSProperties.put(str1 + "DayToPurgeEventHistory", Integer.toString(this.cZ.eventHistoryAge));
    paramFFSProperties.put(str1 + "DayToPurgeActivityLog", Integer.toString(this.cZ.activityLogAge));
    paramFFSProperties.put(str1 + "SupportImmediatePmt", this.cZ.supportImmediatePmt ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
    paramFFSProperties.put(str1 + "SchWaitTime", Long.toString(this.cZ.clusterDelay));
    paramFFSProperties.put(str1 + "SupportCluster", this.cZ.clusterOn ? Boolean.TRUE.toString() : Boolean.FALSE.toString());
    Enumeration localEnumeration = this.cZ.otherProperties.keys();
    while (localEnumeration.hasMoreElements())
    {
      String str2 = (String)localEnumeration.nextElement();
      paramFFSProperties.put(str1 + str2, this.cZ.otherProperties.get(str2));
    }
  }
  
  private static final void a(FFSProperties paramFFSProperties, PropertyConfig paramPropertyConfig)
  {
    String str1 = (paramPropertyConfig.serverName == null) || (paramPropertyConfig.serverName.length() <= 0) ? "Default Server" : paramPropertyConfig.serverName;
    String str2 = str1 + '|';
    paramFFSProperties.put(str2 + "NoImmediateTransfer", paramPropertyConfig.NoImmediateTransfer ? "true" : "false");
    paramFFSProperties.put(str2 + "SupportImmediatePmt", paramPropertyConfig.SupportImmediatePmt ? "true" : "false");
    paramFFSProperties.put(str2 + "MaxPoolSize", Integer.toString(paramPropertyConfig.MaxPoolSize));
    paramFFSProperties.put(str2 + "RouteTimedOutToBatch", paramPropertyConfig.RouteTimedOutToBatch ? "true" : "false");
    paramFFSProperties.put(str2 + "UseExtdPayeeID", paramPropertyConfig.UseExtdPayeeID ? "true" : "false");
    paramFFSProperties.put(str2 + "StartPayeeListID", Integer.toString(paramPropertyConfig.StartPayeeListID));
    paramFFSProperties.put(str2 + "FundsAllocRetries", Integer.toString(paramPropertyConfig.FundsAllocRetries));
    paramFFSProperties.put(str2 + "FundsApprovalRetries", Integer.toString(paramPropertyConfig.FundsApprovalRetries));
    paramFFSProperties.put(str2 + "ConcurrentEventProcesses", Integer.toString(paramPropertyConfig.ConcurrentEventProcesses));
    paramFFSProperties.put(str2 + "ThreadPoolMaximumSize", Integer.toString(paramPropertyConfig.ThreadPoolMaximumSize));
    paramFFSProperties.put(str2 + "OptimalPoolSize", Integer.toString(paramPropertyConfig.OptimalPoolSize));
    paramFFSProperties.put(str2 + "ThreadPoolInitialSize", Integer.toString(paramPropertyConfig.ThreadPoolInitialSize));
    paramFFSProperties.put(str2 + "Retries", Integer.toString(paramPropertyConfig.Retries));
    paramFFSProperties.put(str2 + "Timeout", Integer.toString(paramPropertyConfig.Timeout));
    paramFFSProperties.put(str2 + "PurgeWakeupTime", paramPropertyConfig.PurgeWakeupTime);
    paramFFSProperties.put(str2 + "DayToPurgeClosedInstruction", Integer.toString(paramPropertyConfig.DayToPurgeClosedInstruction));
    paramFFSProperties.put(str2 + "DayToPurgeEventHistory", Integer.toString(paramPropertyConfig.DayToPurgeEventHistory));
    paramFFSProperties.put(str2 + "DayToPurgeActivityLog", Integer.toString(paramPropertyConfig.DayToPurgeActivityLog));
    if ((paramPropertyConfig.BPWServ_userName != null) && (paramPropertyConfig.BPWServ_userName.length() > 0)) {
      paramFFSProperties.put(str2 + "BPWServ_userName", paramPropertyConfig.BPWServ_userName);
    }
    if ((paramPropertyConfig.BPWServ_password != null) && (paramPropertyConfig.BPWServ_password.length() > 0)) {
      paramFFSProperties.put(str2 + "BPWServ_password", paramPropertyConfig.BPWServ_password);
    }
    paramFFSProperties.put(str2 + "BPWServ_protocol", paramPropertyConfig.BPWServ_protocol);
    paramFFSProperties.put(str2 + "BPWServ_host", paramPropertyConfig.BPWServ_host);
    paramFFSProperties.put(str2 + "BPWServ_port", Integer.toString(paramPropertyConfig.BPWServ_port));
    paramFFSProperties.put(str2 + "BPWServ_nameContext", paramPropertyConfig.BPWServ_nameContext);
    paramFFSProperties.put(str2 + "BPW.ADMIN.JNDI.NAME", paramPropertyConfig.BPWServ_jndiName);
    paramFFSProperties.put(str2 + "EnforceEnrollment", paramPropertyConfig.EnforceEnrollment ? "true" : "false");
    paramFFSProperties.put(str2 + "EnforcePayment", paramPropertyConfig.EnforcePayment ? "true" : "false");
    paramFFSProperties.put(str2 + "LogLevel", Integer.toString(paramPropertyConfig.LogLevel));
    paramFFSProperties.put(str2 + "DebugLevel", Integer.toString(paramPropertyConfig.DebugLevel));
    paramFFSProperties.put(str2 + "BatchSize", Integer.toString(paramPropertyConfig.BatchSize));
    paramFFSProperties.put(str2 + "MinuteNotToRecover", Integer.toString(paramPropertyConfig.MinuteNotToRecover));
    if ((paramPropertyConfig.otherProperties != null) && (!paramPropertyConfig.otherProperties.isEmpty()))
    {
      Enumeration localEnumeration = paramPropertyConfig.otherProperties.keys();
      while (localEnumeration.hasMoreElements())
      {
        String str3 = (String)localEnumeration.nextElement();
        paramFFSProperties.put(str2 + str3, paramPropertyConfig.otherProperties.get(str3));
      }
    }
  }
  
  private final void a(FFSProperties paramFFSProperties)
    throws FFSException
  {
    Enumeration localEnumeration = paramFFSProperties.keys();
    FFSConnection localFFSConnection = DBUtil.getMyOwnConnection(this.cP);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = localFFSConnection;
    try
    {
      FFSProperties localFFSProperties = DBPropertyConfig.readPropertyConfig(localFFSConnectionHolder);
      while (localEnumeration.hasMoreElements())
      {
        String str1 = (String)localEnumeration.nextElement();
        String str3 = paramFFSProperties.getProperty(str1);
        String str2 = (String)localFFSProperties.remove(str1);
        if (str2 == null) {
          DBPropertyConfig.addPropertyConfig(localFFSConnectionHolder, str1, str3);
        } else if (!str3.equals(str2)) {
          DBPropertyConfig.updatePropertyConfig(localFFSConnectionHolder, str1, str3);
        }
      }
      localEnumeration = localFFSProperties.keys();
      while (localEnumeration.hasMoreElements())
      {
        String str4 = (String)localEnumeration.nextElement();
        DBPropertyConfig.deletePropertyConfig(localFFSConnectionHolder, str4);
      }
      localFFSConnectionHolder.conn.commit();
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
      localFFSConnectionHolder.conn.close();
    }
  }
  
  public void updateInstructionType(InstructionType paramInstructionType)
    throws FFSException
  {
    updateInstructionType(paramInstructionType.FIId, paramInstructionType.InstructionType, paramInstructionType.HandlerClassName, paramInstructionType.SchedulerStartTime, paramInstructionType.SchedulerInterval, paramInstructionType.DispatchStartTime, paramInstructionType.DispatchInterval, paramInstructionType.ResubmitEventSupported == 1, paramInstructionType.ChkTmAutoRecover == 1, paramInstructionType.FileBasedRecovery == 1, paramInstructionType.ActiveFlag == 1, paramInstructionType.RouteID, paramInstructionType.useCutOffs, paramInstructionType.createEmptyFile, paramInstructionType.processOnHolidays, paramInstructionType.category, paramInstructionType.memo);
  }
  
  public void registerInstructionType(InstructionType[] paramArrayOfInstructionType)
    throws FFSException
  {
    for (int i = 0; i < paramArrayOfInstructionType.length; i++) {
      registerInstructionType(paramArrayOfInstructionType[i].FIId, paramArrayOfInstructionType[i].InstructionType, paramArrayOfInstructionType[i].HandlerClassName, paramArrayOfInstructionType[i].SchedulerStartTime, paramArrayOfInstructionType[i].SchedulerInterval, paramArrayOfInstructionType[i].DispatchStartTime, paramArrayOfInstructionType[i].DispatchInterval, paramArrayOfInstructionType[i].ResubmitEventSupported == 1, paramArrayOfInstructionType[i].ChkTmAutoRecover == 1, paramArrayOfInstructionType[i].FileBasedRecovery == 1, paramArrayOfInstructionType[i].ActiveFlag == 1, paramArrayOfInstructionType[i].RouteID, paramArrayOfInstructionType[i].useCutOffs, paramArrayOfInstructionType[i].createEmptyFile, paramArrayOfInstructionType[i].processOnHolidays, paramArrayOfInstructionType[i].category, paramArrayOfInstructionType[i].memo);
    }
  }
  
  public void registerInstructionType(InstructionType paramInstructionType)
    throws FFSException
  {
    registerInstructionType(paramInstructionType.FIId, paramInstructionType.InstructionType, paramInstructionType.HandlerClassName, paramInstructionType.SchedulerStartTime, paramInstructionType.SchedulerInterval, paramInstructionType.DispatchStartTime, paramInstructionType.DispatchInterval, paramInstructionType.ResubmitEventSupported == 1, paramInstructionType.ChkTmAutoRecover == 1, paramInstructionType.FileBasedRecovery == 1, paramInstructionType.ActiveFlag == 1, paramInstructionType.RouteID, paramInstructionType.useCutOffs, paramInstructionType.createEmptyFile, paramInstructionType.processOnHolidays, paramInstructionType.category, paramInstructionType.memo);
  }
  
  public void deleteInstructionType()
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getMyOwnConnection(this.cP);
      DBInstructionType.deleteInstructionType(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw localFFSException;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
  }
  
  public void registerInstructionType(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, String paramString5, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, String paramString6, String paramString7)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getMyOwnConnection(this.cP);
      DBInstructionType.registerInstructionType(localFFSConnectionHolder, paramString1, paramString2, paramString3, paramString4, paramInt1, paramString5, paramInt2, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramInt3, paramBoolean5, paramBoolean6, paramBoolean7, paramString6, paramString7);
      String str = BPWUtil.trimInstructionType(paramString2);
      if (!InstructionType.isStandardInstType(str))
      {
        int i = ScheduleInfo.cancelSchedule(localFFSConnectionHolder, str, "-1", paramString1);
        ScheduleInfo localScheduleInfo = new ScheduleInfo();
        localScheduleInfo.FIId = paramString1;
        localScheduleInfo.Status = "Active";
        localScheduleInfo.StatusOption = 0;
        localScheduleInfo.Frequency = 12;
        localScheduleInfo.StartDate = 0;
        localScheduleInfo.InstanceCount = 1;
        localScheduleInfo.LogID = "-1";
        localScheduleInfo.NextInstanceDate = 1000010100;
        localScheduleInfo.WorkInstanceDate = 0;
        localScheduleInfo.CurInstanceNum = 1;
        localScheduleInfo.Perpetual = 1;
        ScheduleInfo.createInstTypeSchedule(localFFSConnectionHolder, str, "-1", localScheduleInfo);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw localFFSException;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
    InstructionType localInstructionType = new InstructionType(paramString1, paramString2, paramString3, paramString4, paramInt1, paramString5, paramInt2, paramBoolean1 ? 1 : 0, paramBoolean2 ? 1 : 0, paramBoolean3 ? 1 : 0, paramBoolean4 ? 1 : 0, paramInt3);
    localInstructionType.useCutOffs = paramBoolean5;
    localInstructionType.createEmptyFile = paramBoolean6;
    localInstructionType.processOnHolidays = paramBoolean7;
    localInstructionType.category = paramString6;
    localInstructionType.memo = paramString7;
    ArrayList localArrayList = (ArrayList)this.cY.get(paramString1);
    if (localArrayList == null)
    {
      localArrayList = new ArrayList();
      this.cY.put(paramString1, localArrayList);
    }
    localArrayList.add(localInstructionType);
  }
  
  public void updateInstructionType(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, String paramString5, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, String paramString6, String paramString7)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getMyOwnConnection(this.cP);
      DBInstructionType.updateInstructionType(localFFSConnectionHolder, paramString1, paramString2, paramString3, paramString4, paramInt1, paramString5, paramInt2, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, paramInt3, paramBoolean5, paramBoolean6, paramBoolean7, paramString6, paramString7);
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw localFFSException;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
    InstructionType localInstructionType = new InstructionType(paramString1, paramString2, paramString3, paramString4, paramInt1, paramString5, paramInt2, paramBoolean1 ? 1 : 0, paramBoolean2 ? 1 : 0, paramBoolean3 ? 1 : 0, paramBoolean4 ? 1 : 0, paramInt3);
    localInstructionType.useCutOffs = paramBoolean5;
    localInstructionType.createEmptyFile = paramBoolean6;
    localInstructionType.processOnHolidays = paramBoolean7;
    localInstructionType.category = paramString6;
    localInstructionType.memo = paramString7;
    ArrayList localArrayList = (ArrayList)this.cY.get(paramString1);
    int i = localArrayList.size();
    for (int j = 0; j < i; j++) {
      if (((InstructionType)localArrayList.get(j)).InstructionType.equals(paramString2))
      {
        localArrayList.set(j, localInstructionType);
        return;
      }
    }
  }
  
  public void deleteInstructionType(String paramString1, String paramString2)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getMyOwnConnection(this.cP);
      DBInstructionType.deleteInstructionType(localFFSConnectionHolder, paramString1, paramString2);
      localFFSConnectionHolder.conn.commit();
      ArrayList localArrayList1 = jdMethod_byte(localFFSConnectionHolder, paramString1);
      localFFSConnectionHolder.conn.commit();
      this.cY.remove(paramString1);
      this.cY.put(paramString1, localArrayList1);
    }
    catch (FFSException localFFSException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw localFFSException;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
    InstructionType localInstructionType = null;
    ArrayList localArrayList2 = (ArrayList)this.cY.get(paramString1);
    int i = localArrayList2.size();
    for (int j = 0; j < i; j++)
    {
      localInstructionType = (InstructionType)localArrayList2.get(j);
      if (localInstructionType.InstructionType.equals(paramString2))
      {
        localArrayList2.remove(j);
        return;
      }
    }
  }
  
  public InstructionType[] getInstructionTypes(String paramString)
    throws FFSException
  {
    ArrayList localArrayList = (ArrayList)this.cY.get(paramString);
    return localArrayList == null ? new InstructionType[0] : (InstructionType[])localArrayList.toArray(new InstructionType[0]);
  }
  
  public InstructionType[] getInstructionTypes()
    throws FFSException
  {
    InstructionType[] arrayOfInstructionType = null;
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    try
    {
      ArrayList localArrayList = DBInstructionType.readInstructionTypes(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      arrayOfInstructionType = (InstructionType[])localArrayList.toArray(new InstructionType[localArrayList.size()]);
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      localBPWException.printStackTrace(localPrintWriter);
      throw new FFSException(localStringWriter.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
    return arrayOfInstructionType;
  }
  
  private PropertyConfig[] jdMethod_null()
  {
    HashMap localHashMap = a(this.cW);
    String[] arrayOfString = (String[])localHashMap.keySet().toArray(new String[localHashMap.size()]);
    PropertyConfig[] arrayOfPropertyConfig = new PropertyConfig[arrayOfString.length];
    for (int i = 0; i < arrayOfString.length; i++) {
      arrayOfPropertyConfig[i] = DBPropertyConfig.createPropertyConfig((FFSProperties)localHashMap.get(arrayOfString[i]), arrayOfString[i]);
    }
    return arrayOfPropertyConfig;
  }
  
  private static HashMap a(Hashtable paramHashtable)
  {
    HashMap localHashMap = new HashMap();
    Enumeration localEnumeration = paramHashtable.keys();
    String str2 = null;
    String str3 = null;
    Object localObject = null;
    while (localEnumeration.hasMoreElements())
    {
      String str1 = (String)localEnumeration.nextElement();
      str3 = (String)paramHashtable.get(str1);
      int i = str1.indexOf('|');
      if (i >= 0)
      {
        str2 = str1.substring(0, i);
        if (str2.length() <= 0) {
          str2 = "Default Server";
        }
        localObject = str1.substring(i + 1);
      }
      else
      {
        str2 = "Default Server";
        localObject = str1;
      }
      FFSProperties localFFSProperties = (FFSProperties)localHashMap.get(str2);
      if (localFFSProperties == null)
      {
        localFFSProperties = new FFSProperties();
        localHashMap.put(str2, localFFSProperties);
      }
      localFFSProperties.put(localObject, str3);
    }
    return localHashMap;
  }
  
  public FFSProperties getProperties()
  {
    return this.cW;
  }
  
  public DBConnectionInfo getServerDBConnectionInfo()
  {
    return this.cT;
  }
  
  public FFSDBProperties getBPWServerDBProperties()
  {
    return getBPWServerDBProperties(this.cT);
  }
  
  public FFSDBProperties getBPWServerDBProperties(DBConnectionInfo paramDBConnectionInfo)
  {
    FFSDBProperties localFFSDBProperties = new FFSDBProperties();
    ConnPoolInfo localConnPoolInfo = new ConnPoolInfo();
    DBConnInfo localDBConnInfo = new DBConnInfo();
    localConnPoolInfo._dbConnInfo = localDBConnInfo;
    localFFSDBProperties._connProps = this.cW;
    localFFSDBProperties._connInfo = localConnPoolInfo;
    if (localFFSDBProperties._pureProps == null) {
      localFFSDBProperties._pureProps = new FFSProperties();
    }
    localDBConnInfo._databaseName = paramDBConnectionInfo.dbName;
    localDBConnInfo._password = paramDBConnectionInfo.password;
    localDBConnInfo._dbType = paramDBConnectionInfo.dbType;
    localDBConnInfo._user = paramDBConnectionInfo.userName;
    localDBConnInfo._host = paramDBConnectionInfo.host;
    localDBConnInfo._port = paramDBConnectionInfo.port;
    return localFFSDBProperties;
  }
  
  public boolean mbRuntimeConfigured()
  {
    return this.cQ;
  }
  
  public boolean serverDBConfigured()
  {
    return this.cV;
  }
  
  public void setMBRuntimeConfigured(boolean paramBoolean)
  {
    this.cQ = paramBoolean;
  }
  
  public void setServerDBConfigured(boolean paramBoolean)
  {
    this.cV = paramBoolean;
  }
  
  public DBConnectionInfo getBPWServerDBInfo()
  {
    return this.cT;
  }
  
  public DBConnectionInfo getMBRuntimeDBInfo()
  {
    return this.cR;
  }
  
  public void setBPWServerDBInfo(DBConnectionInfo paramDBConnectionInfo)
  {
    this.cT = paramDBConnectionInfo;
  }
  
  public void setMBRumtimeDBInfo(DBConnectionInfo paramDBConnectionInfo)
  {
    this.cR = paramDBConnectionInfo;
  }
  
  public BPWFIInfo[] getFinancialInstitutions()
    throws FFSException
  {
    return (BPWFIInfo[])this.cS.toArray(new BPWFIInfo[this.cS.size()]);
  }
  
  public BPWFIInfo addFinancialInstitution(BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    try
    {
      String str = DBUtil.getNextIndexStringNotFromCacheWithPadding(localFFSConnectionHolder, "FIID", 4, '0');
      localFFSConnectionHolder.conn.commit();
      paramBPWFIInfo.setFIId(str);
      BPWFIInfo localBPWFIInfo1 = BPWFI.createWithGivenID(localFFSConnectionHolder, paramBPWFIInfo);
      localFFSConnectionHolder.conn.commit();
      if ((localBPWFIInfo1.getStatusCode() == 23670) || (localBPWFIInfo1.getStatusCode() == 23300)) {
        throw new FFSException(localBPWFIInfo1.getStatusMsg());
      }
      ArrayList localArrayList1 = new ArrayList();
      localArrayList1.addAll(Arrays.asList(jdMethod_if(localFFSConnectionHolder)));
      this.cS.clear();
      this.cS = localArrayList1;
      ArrayList localArrayList2 = jdMethod_byte(localFFSConnectionHolder, localBPWFIInfo1.getFIId());
      this.cY.put(localBPWFIInfo1.getFIId(), localArrayList2);
      localFFSConnectionHolder.conn.commit();
      BPWFIInfo localBPWFIInfo2 = localBPWFIInfo1;
      return localBPWFIInfo2;
    }
    catch (FFSException localFFSException)
    {
      try
      {
        localFFSConnectionHolder.conn.rollback();
      }
      catch (Exception localException) {}
      throw localFFSException;
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
  }
  
  public BPWFIInfo getFinancialInstitution(String paramString)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    try
    {
      BPWFIInfo localBPWFIInfo1 = BPWFI.getBPWFIInfo(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      BPWFIInfo localBPWFIInfo2 = localBPWFIInfo1;
      return localBPWFIInfo2;
    }
    catch (FFSException localFFSException)
    {
      try {}catch (Exception localException) {}
      throw localFFSException;
    }
    finally
    {
      localFFSConnectionHolder.conn.close();
    }
  }
  
  public BPWFIInfo modifyFinancialInstitution(BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    try
    {
      BPWFIInfo localBPWFIInfo1 = BPWFI.modify(localFFSConnectionHolder, paramBPWFIInfo);
      localFFSConnectionHolder.conn.commit();
      BPWFIInfo localBPWFIInfo2 = localBPWFIInfo1;
      return localBPWFIInfo2;
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    finally
    {
      localFFSConnectionHolder.conn.close();
    }
  }
  
  public void deleteFinancialInstitution(BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    try
    {
      String str = paramBPWFIInfo.getFIId();
      BPWFI.canBPWFIInfo(localFFSConnectionHolder, paramBPWFIInfo);
      localFFSConnectionHolder.conn.commit();
      ArrayList localArrayList = new ArrayList();
      localArrayList.addAll(Arrays.asList(jdMethod_if(localFFSConnectionHolder)));
      localFFSConnectionHolder.conn.commit();
      this.cS.clear();
      this.cS = localArrayList;
      this.cY.remove(str);
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    finally
    {
      localFFSConnectionHolder.conn.close();
    }
  }
  
  private static FFSConnectionHolder jdMethod_if(FFSDBProperties paramFFSDBProperties)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getMyOwnConnection(paramFFSDBProperties);
    localFFSConnectionHolder.conn.setTransactionIsolation(1);
    return localFFSConnectionHolder;
  }
  
  private static BPWFIInfo[] jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    BPWFIInfo[] arrayOfBPWFIInfo = BPWFI.getBPWFIInfoByStatus(paramFFSConnectionHolder, "ACTIVE");
    if ((arrayOfBPWFIInfo.length == 1) && (arrayOfBPWFIInfo[0].getStatusCode() == 16020)) {
      return new BPWFIInfo[0];
    }
    return arrayOfBPWFIInfo;
  }
  
  private static ArrayList jdMethod_byte(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    return DBInstructionType.readInstructionTypes(paramFFSConnectionHolder, paramString);
  }
  
  public FulfillmentInfo[] getFulfillmentSystems()
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    try
    {
      FulfillmentInfo[] arrayOfFulfillmentInfo = Fulfillment.getFulfillmentInfos(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      localObject1 = arrayOfFulfillmentInfo;
      return localObject1;
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject1 = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter((Writer)localObject1);
      localBPWException.printStackTrace(localPrintWriter);
      throw new FFSException(localObject1.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
  }
  
  public void addFulfillmentSystem(FulfillmentInfo paramFulfillmentInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    int i = paramFulfillmentInfo.RouteID;
    if (paramFulfillmentInfo.RouteID <= 0) {
      try
      {
        localFFSConnectionHolder.conn = DBUtil.getMyOwnConnection(this.cP);
        paramFulfillmentInfo.RouteID = DBUtil.getNextIndex(localFFSConnectionHolder, "RouteID");
        localFFSConnectionHolder.conn.commit();
      }
      catch (FFSException localFFSException)
      {
        localFFSException = localFFSException;
        localFFSConnectionHolder.conn.rollback();
        if (localFFSConnectionHolder.conn != null) {
          localFFSConnectionHolder.conn.close();
        }
        throw localFFSException;
      }
      catch (Exception localException)
      {
        localException = localException;
        localFFSConnectionHolder.conn.rollback();
        if (localFFSConnectionHolder.conn != null) {
          localFFSConnectionHolder.conn.close();
        }
        throw new FFSException(localException.toString());
      }
      finally {}
    }
    try
    {
      Fulfillment.addFulfillmentSystem(paramFulfillmentInfo, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (BPWException localBPWException)
    {
      paramFulfillmentInfo.RouteID = i;
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      throw new FFSException(FFSDebug.stackTrace(localBPWException));
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
  }
  
  public void modifyFulfillmentSystem(FulfillmentInfo paramFulfillmentInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    try
    {
      Fulfillment.update(paramFulfillmentInfo, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (BPWException localBPWException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      localBPWException.printStackTrace(localPrintWriter);
      throw new FFSException(localStringWriter.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
  }
  
  public void deleteFulfillmentSystem(FulfillmentInfo paramFulfillmentInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    try
    {
      Fulfillment.delete(paramFulfillmentInfo.RouteID, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (BPWException localBPWException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      localBPWException.printStackTrace(localPrintWriter);
      throw new FFSException(localStringWriter.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
  }
  
  public PayeeRouteInfo getPayeeRouteInfo(String paramString, int paramInt)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    PayeeRouteInfo localPayeeRouteInfo = null;
    try
    {
      localPayeeRouteInfo = PayeeToRoute.getPayeeRoute(paramString, paramInt, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (BPWException localBPWException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      localBPWException.printStackTrace(localPrintWriter);
      throw new FFSException(localStringWriter.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
    return localPayeeRouteInfo;
  }
  
  public String addPayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws FFSException
  {
    String str = null;
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    Payee localPayee = new Payee(paramPayeeInfo);
    PayeeInfo[] arrayOfPayeeInfo = searchGlobalPayees(paramPayeeInfo.PayeeName);
    if ((paramPayeeInfo.Addr2 == null) || (paramPayeeInfo.Addr2.length() <= 0)) {
      paramPayeeInfo.Addr2 = null;
    }
    if ((paramPayeeInfo.Addr3 == null) || (paramPayeeInfo.Addr3.length() <= 0)) {
      paramPayeeInfo.Addr3 = null;
    }
    if ((paramPayeeInfo.Phone == null) || (paramPayeeInfo.Phone.length() <= 0)) {
      paramPayeeInfo.Phone = null;
    }
    str = localPayee.matchPayee(arrayOfPayeeInfo);
    try
    {
      if (str == null)
      {
        localPayee.setPayeeID(localFFSConnectionHolder);
        localFFSConnectionHolder.conn.commit();
        if ((paramPayeeInfo.LinkPayeeID != null) && (paramPayeeInfo.LinkPayeeID.length() > 0))
        {
          localObject1 = Payee.findPayeeByID(paramPayeeInfo.LinkPayeeID, localFFSConnectionHolder);
          if ((((PayeeInfo)localObject1).LinkPayeeID != null) && (((PayeeInfo)localObject1).LinkPayeeID.length() > 0)) {
            throw new FFSException("Can not link to a virtual payee!");
          }
        }
        localPayee.setStatus("ACTIVE");
        localPayee.storeToDB(localFFSConnectionHolder);
        str = localPayee.getPayeeID();
        paramPayeeRouteInfo.PayeeID = str;
        Object localObject1 = new PayeeToRoute(paramPayeeRouteInfo);
        ((PayeeToRoute)localObject1).storeToDB(localFFSConnectionHolder);
      }
      else
      {
        paramPayeeInfo.PayeeID = str;
        paramPayeeRouteInfo.PayeeID = str;
        modifyPayee(paramPayeeInfo, paramPayeeRouteInfo);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      localException.printStackTrace(localPrintWriter);
      throw new FFSException(localStringWriter.toString());
    }
    finally
    {
      localFFSConnectionHolder.conn.close();
    }
    return str;
  }
  
  public void modifyPayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    try
    {
      Payee.updatePayee(localFFSConnectionHolder, paramPayeeInfo);
      PayeeToRoute localPayeeToRoute = new PayeeToRoute(paramPayeeRouteInfo);
      localPayeeToRoute.update(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      StringWriter localStringWriter = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter(localStringWriter);
      localException.printStackTrace(localPrintWriter);
      throw new FFSException(localStringWriter.toString());
    }
    finally
    {
      localFFSConnectionHolder.conn.close();
    }
  }
  
  public PayeeInfo getPayeeByID(String paramString)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    try
    {
      PayeeInfo localPayeeInfo = Payee.findPayeeByID(localFFSConnectionHolder, paramString);
      localFFSConnectionHolder.conn.commit();
      localObject1 = localPayeeInfo;
      return localObject1;
    }
    catch (Exception localException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      Object localObject1 = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter((Writer)localObject1);
      localException.printStackTrace(localPrintWriter);
      throw new FFSException(localObject1.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
  }
  
  public PayeeInfo[] searchGlobalPayees(String paramString)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    try
    {
      PayeeInfo[] arrayOfPayeeInfo = Payee.searchGlobalPayees(paramString, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      localObject1 = arrayOfPayeeInfo;
      return localObject1;
    }
    catch (BPWException localBPWException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      Object localObject1 = new StringWriter();
      PrintWriter localPrintWriter = new PrintWriter((Writer)localObject1);
      localBPWException.printStackTrace(localPrintWriter);
      throw new FFSException(localObject1.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.close();
      }
    }
  }
  
  public void deletePayee(String paramString)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = jdMethod_if(this.cP);
    try
    {
      boolean bool1 = PmtInstruction.hasPendingPmt(paramString, localFFSConnectionHolder);
      boolean bool2 = Payee.hasPendingLink(paramString, localFFSConnectionHolder);
      if ((bool1) || (bool2)) {
        throw new FFSException("Can not delete this payee since it's being refered.");
      }
      localObject1 = Payee.findPayeeByID(paramString, localFFSConnectionHolder);
      Payee localPayee = new Payee((PayeeInfo)localObject1);
      PayeeRouteInfo localPayeeRouteInfo = PayeeToRoute.getPayeeRoute(paramString, ((PayeeInfo)localObject1).RouteID, localFFSConnectionHolder);
      PayeeToRoute localPayeeToRoute = new PayeeToRoute(localPayeeRouteInfo);
      localPayeeToRoute.removeFromDB(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      int i = PayeeToRoute.findRouteID(paramString, localFFSConnectionHolder);
      if (i == -1)
      {
        localPayee.removeFromDB(localFFSConnectionHolder);
      }
      else
      {
        localPayee.setRouteID(i);
        localPayee.update(localFFSConnectionHolder);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      StringWriter localStringWriter = new StringWriter();
      Object localObject1 = new PrintWriter(localStringWriter);
      localException.printStackTrace((PrintWriter)localObject1);
      throw new FFSException(localStringWriter.toString());
    }
    finally
    {
      localFFSConnectionHolder.conn.close();
    }
  }
  
  public ServerGeneralConfig getServerGeneralConfig()
  {
    return this.cZ;
  }
  
  public void updateServerGeneralConfig(ServerGeneralConfig paramServerGeneralConfig)
    throws FFSException
  {
    ServerGeneralConfig localServerGeneralConfig = this.cZ;
    this.cZ = paramServerGeneralConfig;
    try
    {
      doDeploy();
      jdMethod_void();
    }
    catch (FFSException localFFSException)
    {
      this.cZ = localServerGeneralConfig;
      throw localFFSException;
    }
  }
  
  public void addClusterSettings(PropertyConfig paramPropertyConfig)
  {
    if (paramPropertyConfig == null) {
      return;
    }
    paramPropertyConfig.MBConn_dbType = this.cR.dbType;
    paramPropertyConfig.MBConn_host = this.cR.host;
    paramPropertyConfig.MBConn_port = Integer.toString(this.cR.port);
    paramPropertyConfig.MBConn_user = this.cR.userName;
    paramPropertyConfig.MBConn_passwd = this.cR.password;
    paramPropertyConfig.MBConn_dbname = this.cR.dbName;
    paramPropertyConfig.MBConn_url = this.cR.url;
    paramPropertyConfig.BPWServ_dbType = this.cT.dbType;
    paramPropertyConfig.BPWServ_dbHost = this.cT.host;
    paramPropertyConfig.BPWServ_dbPort = Integer.toString(this.cT.port);
    paramPropertyConfig.BPWServ_dbUser = this.cT.userName;
    paramPropertyConfig.BPWServ_dbPassword = this.cT.password;
    paramPropertyConfig.BPWServ_dbName = this.cT.dbName;
    this.cZ.cascade(paramPropertyConfig);
  }
  
  public void addClusterOtherProperties(PropertyConfig paramPropertyConfig)
  {
    if ((paramPropertyConfig == null) || (this.cX == null)) {
      return;
    }
    if (this.cZ.otherProperties == null) {
      return;
    }
    if (paramPropertyConfig.otherProperties == null) {
      paramPropertyConfig.otherProperties = new FFSProperties();
    }
    Enumeration localEnumeration = this.cZ.otherProperties.keys();
    while (localEnumeration.hasMoreElements())
    {
      Object localObject = localEnumeration.nextElement();
      if (!paramPropertyConfig.otherProperties.containsKey(localObject)) {
        paramPropertyConfig.otherProperties.put(localObject, this.cZ.otherProperties.get(localObject));
      }
    }
  }
  
  public ProcessingWindowInfo addProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str1 = "BPWAdminImpl.addProcessingWindow: ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    FFSDebug.log(str1, "Starts.", 6);
    Object localObject1;
    if (paramProcessingWindowInfo == null)
    {
      paramProcessingWindowInfo = new ProcessingWindowInfo();
      paramProcessingWindowInfo.setStatusCode(16000);
      localObject1 = "ProcessingWindowInfo object  is null";
      paramProcessingWindowInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, (String)localObject1, 0);
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    if (!jdMethod_if(paramProcessingWindowInfo)) {
      return paramProcessingWindowInfo;
    }
    if (!a(paramProcessingWindowInfo)) {
      return paramProcessingWindowInfo;
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = str1 + "Can not get DB Connection.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      paramProcessingWindowInfo = DBPropertyConfig.addProcessingWindow(localFFSConnectionHolder, paramProcessingWindowInfo);
      if (paramProcessingWindowInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject1 = paramProcessingWindowInfo;
        return localObject1;
      }
      FFSDebug.log(str1, " newWindow:" + paramProcessingWindowInfo, 6);
      FFSDebug.log(str1, " commit change", 6);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("*** ", str1, str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, " end ", 6);
    return paramProcessingWindowInfo;
  }
  
  public ProcessingWindowInfo modProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str1 = "BPWAdminImpl.modProcessingWindow: ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    FFSDebug.log(str1, "Starts.", 6);
    Object localObject1;
    if (paramProcessingWindowInfo == null)
    {
      paramProcessingWindowInfo = new ProcessingWindowInfo();
      paramProcessingWindowInfo.setStatusCode(16000);
      localObject1 = "ProcessingWindowInfo object  is null";
      paramProcessingWindowInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, (String)localObject1, 0);
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    if (!jdMethod_if(paramProcessingWindowInfo)) {
      return paramProcessingWindowInfo;
    }
    if (!a(paramProcessingWindowInfo)) {
      return paramProcessingWindowInfo;
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = str1 + "Can not get DB Connection.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      paramProcessingWindowInfo = DBPropertyConfig.modProcessingWindow(localFFSConnectionHolder, paramProcessingWindowInfo);
      if (paramProcessingWindowInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject1 = paramProcessingWindowInfo;
        return localObject1;
      }
      FFSDebug.log(str1, " commit change", 6);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("*** ", str1, str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, " end ", 6);
    return paramProcessingWindowInfo;
  }
  
  public ProcessingWindowInfo delProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException
  {
    String str1 = "BPWAdminImpl.delProcessingWindow: ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    FFSDebug.log(str1, "Starts.", 6);
    Object localObject1;
    if (paramProcessingWindowInfo == null)
    {
      paramProcessingWindowInfo = new ProcessingWindowInfo();
      paramProcessingWindowInfo.setStatusCode(16000);
      localObject1 = "ProcessingWindowInfo object  is null";
      paramProcessingWindowInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, (String)localObject1, 0);
      paramProcessingWindowInfo.setProcessStatus("FAILED");
      return paramProcessingWindowInfo;
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = str1 + "Can not get DB Connection.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      paramProcessingWindowInfo = DBPropertyConfig.delProcessingWindow(localFFSConnectionHolder, paramProcessingWindowInfo);
      if (paramProcessingWindowInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject1 = paramProcessingWindowInfo;
        return localObject1;
      }
      FFSDebug.log(str1, " commit change", 6);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("*** ", str1, str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, " end ", 6);
    return paramProcessingWindowInfo;
  }
  
  public ProcessingWindowList getProcessingWindows(ProcessingWindowList paramProcessingWindowList)
    throws FFSException
  {
    String str1 = "BPWAdminImpl.getProcessingWindows: ";
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramProcessingWindowList = DBPropertyConfig.getProcessingWindows(localFFSConnectionHolder, paramProcessingWindowList);
      localFFSConnectionHolder.conn.commit();
      ProcessingWindowList localProcessingWindowList = paramProcessingWindowList;
      return localProcessingWindowList;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = str1 + "failed " + localThrowable.toString();
      FFSDebug.log(str2, FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private boolean jdMethod_if(ProcessingWindowInfo paramProcessingWindowInfo)
  {
    String str;
    if ((paramProcessingWindowInfo.getFIID() == null) || (paramProcessingWindowInfo.getFIID().trim().length() == 0))
    {
      paramProcessingWindowInfo.setStatusCode(16010);
      str = "FIID in ProcessingWindowInfo object contains null ";
      paramProcessingWindowInfo.setStatusMsg(str);
      return false;
    }
    if ((paramProcessingWindowInfo.getStartTime() == null) || (paramProcessingWindowInfo.getStartTime().trim().length() == 0))
    {
      paramProcessingWindowInfo.setStatusCode(16010);
      str = "Start time in ProcessingWindowInfo object contains null ";
      paramProcessingWindowInfo.setStatusMsg(str);
      return false;
    }
    if ((paramProcessingWindowInfo.getCloseTime() == null) || (paramProcessingWindowInfo.getCloseTime().trim().length() == 0))
    {
      paramProcessingWindowInfo.setStatusCode(16010);
      str = "Close time in ProcessingWindowInfo object contains null ";
      paramProcessingWindowInfo.setStatusMsg(str);
      return false;
    }
    return true;
  }
  
  private boolean a(ProcessingWindowInfo paramProcessingWindowInfo)
  {
    String str1 = "BPWAdminImpl.validateWindowTimes: ";
    String str2 = paramProcessingWindowInfo.getStartTime();
    String str3 = paramProcessingWindowInfo.getCloseTime();
    String str4 = "HH:mm:ss";
    String str5;
    if (!FFSUtil.checkTimeBeanFormat(str2, str4))
    {
      paramProcessingWindowInfo.setStatusCode(19730);
      str5 = "Invalid window start time. :" + str2;
      paramProcessingWindowInfo.setStatusMsg(str5);
      return false;
    }
    if (!FFSUtil.checkTimeBeanFormat(str3, str4))
    {
      paramProcessingWindowInfo.setStatusCode(19740);
      str5 = "Invalid window close time. :" + str3;
      paramProcessingWindowInfo.setStatusMsg(str5);
      return false;
    }
    FFSDebug.log(str1, " Done.", 6);
    return true;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.config.BPWAdminImpl
 * JD-Core Version:    0.7.0.1
 */