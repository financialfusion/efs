package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Wire;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;

public class WireApprovalRsltProcessor
  implements BPWResource, FFSConst, DBConsts
{
  public static void processWireApprovalRslt(WireInfo[] paramArrayOfWireInfo)
    throws FFSException
  {
    FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRslt: ", " start", 6);
    int i = 0;
    String str1 = null;
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      str1 = localPropertyConfig.otherProperties.getProperty("bpw.wire.audit");
      if (str1 == null) {
        i = 0;
      } else {
        i = Integer.parseInt(str1);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireApprovalHandler. Invalid Audit log level value", str1, 0);
      i = 0;
    }
    if (paramArrayOfWireInfo == null) {
      throw new FFSException(" WireApprovalRsltProcessor.processWireApprovalRslt: Invalid WireInfo is passed: null");
    }
    if (paramArrayOfWireInfo.length == 0)
    {
      FFSDebug.log("ERRORCODE:20010", " WireApprovalRsltProcessor.processWireApprovalRslt: ", "Empty fund approval response list", 0);
      return;
    }
    boolean bool = true;
    FFSConnectionHolder localFFSConnectionHolder = null;
    if ((paramArrayOfWireInfo[0] != null) && (paramArrayOfWireInfo[0].getDbTransKey() != null))
    {
      DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
      String str2 = paramArrayOfWireInfo[0].getDbTransKey();
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(str2);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      bool = false;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      FFSDebug.log("ERRORCODE:20020", " WireApprovalRsltProcessor.processWireApprovalRslt: ", "Failed to obtain connection to bpw database....", 0);
      throw new FFSException(" WireApprovalRsltProcessor.processWireApprovalRslt: Failed to obtain connection to bpw database....");
    }
    try
    {
      FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRslt: ", "Processing wire results. total#: " + paramArrayOfWireInfo.length, 6);
      for (int j = 0; j < paramArrayOfWireInfo.length; j++) {
        if (paramArrayOfWireInfo[j] == null)
        {
          FFSDebug.log("ERRORCODE:20030", " WireApprovalRsltProcessor.processWireApprovalRslt: ", "Invalid wire transfer result #: " + j, 0);
        }
        else if ((paramArrayOfWireInfo[j].getSrvrTid() == null) || (paramArrayOfWireInfo[j].getSrvrTid().length() == 0))
        {
          FFSDebug.log("ERRORCODE:20040", " WireApprovalRsltProcessor.processWireApprovalRslt: ", "Invalid srvrTid for wire ", "transfer result: " + paramArrayOfWireInfo[j], 0);
          if (i >= 1) {
            jdMethod_if(paramArrayOfWireInfo[j], "Process wire approval result failed, wire transfer Id is missing.");
          }
        }
        else
        {
          try
          {
            FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRslt: ", "Processing wire transfer result: " + paramArrayOfWireInfo[j], 6);
            processOneWireApprovalRslt(paramArrayOfWireInfo[j], localFFSConnectionHolder);
            FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRslt: ", "Processed wire transfer result: " + paramArrayOfWireInfo[j], 6);
          }
          catch (Throwable localThrowable2)
          {
            FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRslt: ", " failed to process wire: ", paramArrayOfWireInfo[j].toString(), FFSDebug.stackTrace(localThrowable2), 0);
          }
        }
      }
      FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRslt: ", "Processed all wire transfer results", 6);
      if (!bool) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Throwable localThrowable1)
    {
      if (!bool) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str3 = " WireApprovalRsltProcessor.processWireApprovalRslt: Failed to process wire result list. Error: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable1, str3);
    }
    finally
    {
      if (!bool)
      {
        FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRslt: ", "Releasing DB connection", 6);
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRslt: ", " done, ownConn: " + bool, 6);
  }
  
  public static void processOneWireApprovalRslt(WireInfo paramWireInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    int i = 3;
    int j = 0;
    FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", " start. wireRslt: " + paramWireInfo, 6);
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str1 = (String)localPropertyConfig.otherProperties.get("bpw.wire.funds.approval.retries");
    try
    {
      if (str1 != null) {
        i = Integer.parseInt(str1);
      }
    }
    catch (Throwable localThrowable1)
    {
      i = 3;
    }
    String str2 = null;
    try
    {
      str2 = localPropertyConfig.otherProperties.getProperty("bpw.wire.audit");
      if (str2 == null) {
        j = 0;
      } else {
        j = Integer.parseInt(str2);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireApprovalHandler. Invalid Audit log level value", str2, 0);
      j = 0;
    }
    if (paramWireInfo == null)
    {
      FFSDebug.log("ERRORCODE:20015", " WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "Empty fund approval response ", 0);
      return;
    }
    String str3 = paramWireInfo.getSrvrTid();
    FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", " SrvrTID: ", str3, ",status: ", paramWireInfo.getPrcStatus(), 6);
    String str4 = paramWireInfo.getPrcStatus();
    String str5 = paramWireInfo.getProcessedBy();
    WireInfo localWireInfo = Wire.getWireInfo(paramFFSConnectionHolder, paramWireInfo);
    FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "wireRslt.getPrcStatus(): ", paramWireInfo.getPrcStatus(), 6);
    if (localWireInfo.getStatusCode() == 16020)
    {
      FFSDebug.log("ERRORCODE:20000", " WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "RECORD NOT FOUND IN BPW DB. RECORD: ", paramWireInfo.toString(), 0);
      if (j >= 1) {
        jdMethod_if(paramWireInfo, "Process wire approval result failed, the wire transfer is not found in database.");
      }
      return;
    }
    localWireInfo.setProcessedBy(str5);
    paramWireInfo.setWirePayeeInfo(localWireInfo.getWirePayeeInfo());
    String str6 = localWireInfo.getPrcStatus();
    FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "rsltStatus: ", str4, 6);
    FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "currStatus: ", str6, 6);
    if ((str4 == null) || (str4.length() == 0))
    {
      FFSDebug.log("ERRORCODE:20070", " WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "INVALID STATUS PASSED FROM BACKEND: ", str4, ", for Wire: ", paramWireInfo.toString(), 0);
      if (j >= 1) {
        jdMethod_if(paramWireInfo, "Process wire approval result failed, invalid result status.");
      }
      if (!jdMethod_if(paramFFSConnectionHolder, localWireInfo, j))
      {
        localWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        Wire.updateStatus(paramFFSConnectionHolder, localWireInfo);
      }
      return;
    }
    if ((str6 == null) || (str6.length() == 0))
    {
      FFSDebug.log("ERRORCODE:20060", " WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "INVALID STATUS IN BPW DB: ", str6, ", for Wire: ", paramWireInfo.toString(), 0);
      if (j >= 1) {
        jdMethod_if(paramWireInfo, "Process wire approval result failed, the wire transfer is in invalid state.");
      }
      if (!jdMethod_if(paramFFSConnectionHolder, localWireInfo, j))
      {
        localWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        Wire.updateStatus(paramFFSConnectionHolder, localWireInfo);
      }
      return;
    }
    if ((!"INFUNDSAPPROVAL".equalsIgnoreCase(str6)) && (!"NOFUNDS".equalsIgnoreCase(str6)) && (!"FUNDSPENDING".equalsIgnoreCase(str6)) && (!str6.startsWith("CUSTOM_")) && (!"FUNDSAPPROVALACTIVE".equalsIgnoreCase(str6)) && (!"IMMED_INPROCESS".equalsIgnoreCase(str6)) && (!paramWireInfo.getPossibleDuplicate()))
    {
      FFSDebug.log("ERRORCODE:20060", " WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "INVALID STATUS IN BPW DB: ", str6, ", for Wire: ", paramWireInfo.toString(), 0);
      if (j >= 1) {
        jdMethod_if(paramWireInfo, "Process wire approval result failed, the wire transfer is in invalid state.");
      }
      if (!jdMethod_if(paramFFSConnectionHolder, localWireInfo, j))
      {
        localWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        Wire.updateStatus(paramFFSConnectionHolder, localWireInfo);
      }
      return;
    }
    ScheduleInfo localScheduleInfo;
    Object localObject1;
    Object localObject2;
    if (str4.equalsIgnoreCase("FUNDSAPPROVED"))
    {
      FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "wireRslt.getPrcStatus(): ", paramWireInfo.getPrcStatus(), 6);
      Wire.updateFromApprovalStatus(paramFFSConnectionHolder, paramWireInfo);
      localScheduleInfo = ScheduleInfo.getScheduleInfo(localWireInfo.getFiID(), "WIRETRN", str3, paramFFSConnectionHolder);
      localObject1 = EventInfo.get(paramFFSConnectionHolder, "WIRETRN", str3);
      localObject2 = EventInfoLog.get(paramFFSConnectionHolder, "WIRETRN", str3);
      if (((localScheduleInfo == null) || (localScheduleInfo.StatusOption == 1)) && (localObject1 == null) && (localObject2 == null))
      {
        localObject3 = localWireInfo.getDateToPost();
        localScheduleInfo = new ScheduleInfo();
        localScheduleInfo.FIId = localWireInfo.getFiID();
        localScheduleInfo.Status = "Active";
        localScheduleInfo.Frequency = 10;
        FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "DateToPost: [", (String)localObject3, "]", 6);
        localScheduleInfo.StartDate = Integer.parseInt((String)localObject3);
        FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "sinfo.StartDate: [" + localScheduleInfo.StartDate, "]", 6);
        localScheduleInfo.InstanceCount = 1;
        localScheduleInfo.LogID = localWireInfo.getLogId();
        ScheduleInfo.createSchedule(paramFFSConnectionHolder, "WIRETRN", str3, localScheduleInfo);
      }
      else
      {
        FFSDebug.log("ERRORCODE:20080", " WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "DUPLICATE RESULTS OF WIRE ", "APPROVAL FOR WIRE: ", paramWireInfo.toString(), 0);
        FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "NO WIRE SCHEDULE WILL BE CREATED", 0);
        if (j >= 1) {
          jdMethod_if(paramWireInfo, "Process wire approval result failed, some schedule is already there for the wire transfer.");
        }
      }
      Object localObject3 = ScheduleInfo.getScheduleInfo(localWireInfo.getFiID(), "RECWIREAPPROVALTRN", str3, paramFFSConnectionHolder);
      if ((localObject3 != null) && (((ScheduleInfo)localObject3).StatusOption != 1))
      {
        FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "Deleting schedule: ", ((ScheduleInfo)localObject3).ScheduleID, 6);
        ScheduleInfo.delete(paramFFSConnectionHolder, ((ScheduleInfo)localObject3).ScheduleID);
      }
    }
    else if ("NOFUNDS".equalsIgnoreCase(str4))
    {
      localScheduleInfo = ScheduleInfo.getScheduleInfo(localWireInfo.getFiID(), "RECWIREAPPROVALTRN", str3, paramFFSConnectionHolder);
      if ((localScheduleInfo == null) || (localScheduleInfo.StatusOption == 1))
      {
        FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "Creating a retry schedule ", " for wire: ", paramWireInfo.toString(), 6);
        localObject1 = new ScheduleInfo();
        ((ScheduleInfo)localObject1).FIId = localWireInfo.getFiID();
        ((ScheduleInfo)localObject1).Status = "Active";
        ((ScheduleInfo)localObject1).Frequency = 11;
        localObject2 = localWireInfo.getDateToPost();
        ((ScheduleInfo)localObject1).StartDate = Integer.parseInt((String)localObject2);
        ((ScheduleInfo)localObject1).LogID = localWireInfo.getLogId();
        ((ScheduleInfo)localObject1).InstanceCount = i;
        ((ScheduleInfo)localObject1).NextInstanceDate = ((ScheduleInfo)localObject1).StartDate;
        try
        {
          ScheduleInfo.moveNextInstanceDate((ScheduleInfo)localObject1);
        }
        catch (Throwable localThrowable3)
        {
          if (j >= 1) {
            jdMethod_if(paramWireInfo, "Process wire approval result failed, failed to create retry schedule.");
          }
          throw new FFSException(localThrowable3, " WireApprovalRsltProcessor.processOneWireApprovalRslt: moveNextInstanceDate failed");
        }
        ((ScheduleInfo)localObject1).StartDate = ((ScheduleInfo)localObject1).NextInstanceDate;
        String str7 = ScheduleInfo.createSchedule(paramFFSConnectionHolder, "RECWIREAPPROVALTRN", str3, (ScheduleInfo)localObject1);
        FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "Created a retry schedule ", " for wire: ", paramWireInfo.toString(), " scheduleId: ", str7, 6);
        String str8 = jdMethod_char(paramFFSConnectionHolder, localWireInfo);
        FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "Created a failed wire  schedule ", " for wire: ", paramWireInfo.toString(), " scheduleId: ", str8, 6);
      }
      Wire.updateStatus(paramFFSConnectionHolder, paramWireInfo);
    }
    else if ((str4.equalsIgnoreCase("FUNDSPENDING")) || (str4.startsWith("CUSTOM_")))
    {
      Wire.updateStatus(paramFFSConnectionHolder, paramWireInfo);
    }
    else if (str4.equalsIgnoreCase("FUNDSFAILEDON"))
    {
      Wire.updateStatus(paramFFSConnectionHolder, paramWireInfo);
      localScheduleInfo = ScheduleInfo.getScheduleInfo(localWireInfo.getFiID(), "RECWIREAPPROVALTRN", str3, paramFFSConnectionHolder);
      if ((localScheduleInfo != null) && (localScheduleInfo.StatusOption != 1)) {
        ScheduleInfo.delete(paramFFSConnectionHolder, localScheduleInfo.ScheduleID);
      }
      localObject1 = jdMethod_char(paramFFSConnectionHolder, localWireInfo);
      FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "Created a failed wire  schedule ", " for wire: ", paramWireInfo.toString(), " scheduleId: ", (String)localObject1, 6);
      if (!jdMethod_if(paramFFSConnectionHolder, localWireInfo, j))
      {
        localWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        Wire.updateStatus(paramFFSConnectionHolder, localWireInfo);
      }
    }
    else
    {
      if (!jdMethod_if(paramFFSConnectionHolder, localWireInfo, j))
      {
        localWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        Wire.updateStatus(paramFFSConnectionHolder, localWireInfo);
      }
      if (j >= 1) {
        jdMethod_if(paramWireInfo, "Process wire approval result failed, unknown status.");
      }
      FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", "UNKNOWN STATUS, THIS RESULT WILL ", " BE PROCESSED BY BPW: ", paramWireInfo.toString(), 6);
    }
    if (j >= 4) {
      try
      {
        a(paramFFSConnectionHolder, paramWireInfo, "processOneWireApprovalRslt", str4, false);
      }
      catch (Throwable localThrowable2)
      {
        localObject1 = "WireApprovalRsltProcessor.processOneWireApprovalRslt failed " + localThrowable2.toString();
        FFSDebug.log((String)localObject1 + FFSDebug.stackTrace(localThrowable2), 0);
      }
    }
    FFSDebug.log(" WireApprovalRsltProcessor.processOneWireApprovalRslt: ", " done", 6);
  }
  
  public static void processWireApprovalRsltImmediate(WireInfo[] paramArrayOfWireInfo)
    throws FFSException
  {
    FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRsltImmediate: ", " start", 6);
    int i = 0;
    String str1 = null;
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      str1 = localPropertyConfig.otherProperties.getProperty("bpw.wire.audit");
      if (str1 == null) {
        i = 0;
      } else {
        i = Integer.parseInt(str1);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireApprovalHandler. Invalid Audit log level value", str1, 0);
      i = 0;
    }
    if (paramArrayOfWireInfo == null) {
      throw new FFSException(" WireApprovalRsltProcessor.processWireApprovalRsltImmediate: Invalid WireInfo is passed: null");
    }
    if (paramArrayOfWireInfo.length == 0)
    {
      FFSDebug.log("ERRORCODE:20010", " WireApprovalRsltProcessor.processWireApprovalRsltImmediate: ", "Empty fund approval response list", 0);
      return;
    }
    boolean bool = true;
    FFSConnectionHolder localFFSConnectionHolder = null;
    if ((paramArrayOfWireInfo[0] != null) && (paramArrayOfWireInfo[0].getDbTransKey() != null))
    {
      DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
      String str2 = paramArrayOfWireInfo[0].getDbTransKey();
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(str2);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      bool = false;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      FFSDebug.log("ERRORCODE:20020", " WireApprovalRsltProcessor.processWireApprovalRsltImmediate: ", "Failed to obtain connection to bpw database....", 0);
      throw new FFSException(" WireApprovalRsltProcessor.processWireApprovalRsltImmediate: Failed to obtain connection to bpw database....");
    }
    try
    {
      FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRsltImmediate: ", "Processing wire results. total#: " + paramArrayOfWireInfo.length, 6);
      for (int j = 0; j < paramArrayOfWireInfo.length; j++) {
        if (paramArrayOfWireInfo[j] == null)
        {
          FFSDebug.log("ERRORCODE:20030", " WireApprovalRsltProcessor.processWireApprovalRsltImmediate: ", "Invalid wire transfer result #: " + j, 0);
        }
        else if ((paramArrayOfWireInfo[j].getSrvrTid() == null) || (paramArrayOfWireInfo[j].getSrvrTid().length() == 0))
        {
          FFSDebug.log("ERRORCODE:20040", " WireApprovalRsltProcessor.processWireApprovalRsltImmediate: ", "Invalid srvrTid for wire ", "transfer result: " + paramArrayOfWireInfo[j], 0);
          if (i >= 1) {
            jdMethod_if(paramArrayOfWireInfo[j], "Immediate processing of wire approval result failed, wire transfer Id is missing.");
          }
        }
        else
        {
          FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRsltImmediate: ", "Processing wire transfer result: " + paramArrayOfWireInfo[j], 6);
          try
          {
            processOneWireApprovalRsltImmediate(paramArrayOfWireInfo[j], localFFSConnectionHolder);
          }
          catch (Throwable localThrowable2)
          {
            FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRsltImmediate: ", "Failed to process wire transfer result: " + paramArrayOfWireInfo[j], "> Error: ", FFSDebug.stackTrace(localThrowable2), 0);
            continue;
          }
          FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRsltImmediate: ", "Processed wire transfer result: " + paramArrayOfWireInfo[j], 6);
        }
      }
      FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRsltImmediate: ", "Processed all wire transfer results", 6);
      if (!bool) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Throwable localThrowable1)
    {
      if (!bool) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str3 = " WireApprovalRsltProcessor.processWireApprovalRsltImmediate: Failed to process wire result list. Error: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable1, str3);
    }
    finally
    {
      if (!bool)
      {
        FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRsltImmediate: ", "Releasing DB connection", 6);
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log(" WireApprovalRsltProcessor.processWireApprovalRsltImmediate: ", " done, ownConn: " + bool, 6);
  }
  
  public static void processOneWireApprovalRsltImmediate(WireInfo paramWireInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    int i = 0;
    String str1 = paramWireInfo.getSrvrTid();
    String str2 = paramWireInfo.getPrcStatus();
    String str3 = paramWireInfo.getProcessedBy();
    String str4 = "WireApprovalRsltProcessor.processOneWireApprovalRsltImmediate: ";
    FFSDebug.log(str4, "starts. wireRslt: " + paramWireInfo, 6);
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    String str5 = null;
    try
    {
      str5 = localPropertyConfig.otherProperties.getProperty("bpw.wire.audit");
      if (str5 == null) {
        i = 0;
      } else {
        i = Integer.parseInt(str5);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireApprovalHandler. Invalid Audit log level value", str5, 0);
      i = 0;
    }
    if (paramWireInfo == null)
    {
      FFSDebug.log("ERRORCODE:20015", str4, "Empty fund approval response ", 0);
      return;
    }
    WireInfo localWireInfo = Wire.getWireInfo(paramFFSConnectionHolder, paramWireInfo);
    if (localWireInfo.getStatusCode() == 16020)
    {
      FFSDebug.log("ERRORCODE:20090", str4, "NO RECORD FOUND FOR THIS RESULT: ", paramWireInfo.toString(), 6);
      if (i >= 1) {
        jdMethod_if(paramWireInfo, "Immediate processing wire approval result failed, the wire transfer is not found in database.");
      }
      throw new FFSException(20090, "NO RECORD FOUND FOR THIS RESULT: " + paramWireInfo.toString());
    }
    if (!str2.equalsIgnoreCase("FUNDSAPPROVED"))
    {
      FFSDebug.log(str4, "Immediate wire not approved. calling ", "processOneWireApprovalRslt to update BPW DB", 6);
      processOneWireApprovalRslt(paramWireInfo, paramFFSConnectionHolder);
    }
    else
    {
      Wire.updateFromApprovalStatus(paramFFSConnectionHolder, paramWireInfo);
    }
    paramWireInfo.setWirePayeeInfo(localWireInfo.getWirePayeeInfo());
    if (i >= 4) {
      try
      {
        a(paramFFSConnectionHolder, paramWireInfo, "processOneWireApprovalRsltImmediate", str2, false);
      }
      catch (Throwable localThrowable)
      {
        String str6 = "WireApprovalRsltProcessor.processOneWireApprovalRsltImmediate failed " + localThrowable.toString();
        FFSDebug.log(str6 + FFSDebug.stackTrace(localThrowable), 0);
        throw new FFSException(localThrowable, str6);
      }
    }
    FFSDebug.log(str4, "done", 6);
  }
  
  public static void processWireApprovalRevertRslt(WireInfo[] paramArrayOfWireInfo)
    throws Exception
  {
    FFSDebug.log("WireApprovalRsltProcessor.processWireApprovalRevertRslt: ", "starts. wireInfo: " + paramArrayOfWireInfo, 6);
    String str1;
    if (paramArrayOfWireInfo == null)
    {
      str1 = "WireApprovalRsltProcessor.processWireApprovalRevertRslt: Invalid wireInfo is passed: null";
      FFSDebug.log(str1, 0);
      throw new Exception(str1);
    }
    if (paramArrayOfWireInfo.length == 0)
    {
      str1 = "WireApprovalRsltProcessor.processWireApprovalRevertRslt: Empty wireInfo list";
      FFSDebug.log("ERRORCODE:20120", str1, 0);
      return;
    }
    int i = 0;
    int j = 0;
    String str2 = null;
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      str2 = localPropertyConfig.otherProperties.getProperty("bpw.wire.audit");
      if (str2 == null) {
        j = 0;
      } else {
        j = Integer.parseInt(str2);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireApprovalHandler. Invalid Audit log level value", str2, 0);
      j = 0;
    }
    int k = 1;
    FFSConnectionHolder localFFSConnectionHolder = null;
    if (paramArrayOfWireInfo[0].getDbTransKey() != null)
    {
      DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
      String str3 = paramArrayOfWireInfo[0].getDbTransKey();
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(str3);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      k = 0;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      FFSDebug.log("WireApprovalRsltProcessor.processWireApprovalRevertRslt: ", "Failed to obtain connection to bpw database....", 0);
      throw new FFSException("WireApprovalRsltProcessor.processWireApprovalRevertRslt: Failed to obtain connection to bpw database....");
    }
    try
    {
      for (int m = 0; m < paramArrayOfWireInfo.length; m++)
      {
        i = m;
        if (paramArrayOfWireInfo[m] == null)
        {
          FFSDebug.log("ERRORCODE:20100", "WireApprovalRsltProcessor.processWireApprovalRevertRslt: ", " null data for wireInfo #: " + m, 0);
        }
        else if ((paramArrayOfWireInfo[m].getSrvrTid() == null) || (paramArrayOfWireInfo[m].getSrvrTid().length() == 0))
        {
          FFSDebug.log("WireApprovalRsltProcessor.processWireApprovalRevertRslt: ", "Invalid SrvrTid for wireInfo: ", paramArrayOfWireInfo[m].toString(), 0);
          if (j >= 1) {
            jdMethod_if(paramArrayOfWireInfo[m], "Process wire approval revert result failed, the wire transfer Id is missing.");
          }
        }
        else
        {
          try
          {
            processOneWireApprovalRevertRslt(localFFSConnectionHolder, paramArrayOfWireInfo[m]);
          }
          catch (Throwable localThrowable2)
          {
            FFSDebug.log("WireApprovalRsltProcessor.processWireApprovalRevertRslt: ", " failed to revert wire: ", paramArrayOfWireInfo[m].toString(), FFSDebug.stackTrace(localThrowable2), 0);
          }
        }
      }
      FFSDebug.log("WireApprovalRsltProcessor.processWireApprovalRevertRslt: ", "Processed all wire  transfer approval reverts", 6);
      if (k == 0) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Throwable localThrowable1)
    {
      if (k == 0) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str4 = "WireApprovalRsltProcessor.processWireApprovalRevertRslt: Failed to obtain connection to bpw database...." + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str4, 0);
      if (j >= 1) {
        jdMethod_if(paramArrayOfWireInfo[i], "Process wire approval revert result failed, unknown error occurred.");
      }
      throw new FFSException(localThrowable1, str4);
    }
    finally
    {
      if (k == 0)
      {
        FFSDebug.log("WireApprovalRsltProcessor.processWireApprovalRevertRslt: ", "Releasing DB connection", 6);
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log("WireApprovalRsltProcessor.processWireApprovalRevertRslt: ", "ends. wireInfo: " + paramArrayOfWireInfo, 6);
  }
  
  public static void processOneWireApprovalRevertRslt(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws Exception
  {
    FFSDebug.log("WireApprovalRsltProcessor.processOneWireApprovalRevertRslt: ", "starts. wireInfo: " + paramWireInfo, 6);
    if (paramWireInfo == null)
    {
      FFSDebug.log("ERRORCODE:20015", "WireApprovalRsltProcessor.processOneWireApprovalRevertRslt: ", "Empty fund approval response ", 0);
      return;
    }
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    int i = 0;
    String str1 = null;
    try
    {
      str1 = localPropertyConfig.otherProperties.getProperty("bpw.wire.audit");
      if (str1 == null) {
        i = 0;
      } else {
        i = Integer.parseInt(str1);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireApprovalHandler. Invalid Audit log level value", str1, 0);
      i = 0;
    }
    String str2 = paramWireInfo.getProcessedBy();
    String str3 = paramWireInfo.getSrvrTid();
    WireInfo localWireInfo = Wire.getWireInfo(paramFFSConnectionHolder, paramWireInfo);
    if ((localWireInfo == null) || (localWireInfo.getStatusCode() == 16020))
    {
      FFSDebug.log("ERRORCODE:20000", "WireApprovalRsltProcessor.processOneWireApprovalRevertRslt: ", "WireInfo for SrvrTID ", str3, ", not found!", 0);
      if (i >= 1) {
        jdMethod_if(localWireInfo, "Process wire approval revert result failed, wire transfer is not found in database.");
      }
      return;
    }
    localWireInfo.setProcessedBy(str2);
    String str4 = localWireInfo.getPrcStatus();
    FFSDebug.log("WireApprovalRsltProcessor.processOneWireApprovalRevertRslt: ", "current status of wire:", str4, 6);
    if (str4.compareTo("CANCELEDON") == 0)
    {
      FFSDebug.log("ERRORCODE:20060", "WireApprovalRsltProcessor.processOneWireApprovalRevertRslt: ", "WireInfo for SrvrTID ", str3, ", has invalid status: !", str4, 0);
      if (i >= 1) {
        jdMethod_if(localWireInfo, "Process wire approval revert result failed, wire transfer is in invalid state.");
      }
    }
    else
    {
      if (!"INFUNDSREVERT".equalsIgnoreCase(str4))
      {
        FFSDebug.log("ERRORCODE:20110", "WireApprovalRsltProcessor.processOneWireApprovalRevertRslt: ", " wire not in INFUNDSREVERT state, SrvrTID: ", str3, ", Status: ", str4, 0);
        if (i >= 1) {
          jdMethod_if(localWireInfo, "Process wire approval revert result failed, wire transfer is not in required state.");
        }
        return;
      }
      FFSDebug.log("WireApprovalRsltProcessor.processOneWireApprovalRevertRslt: ", "Before update status:", paramWireInfo.toString(), 6);
      Wire.updateStatus(paramFFSConnectionHolder, paramWireInfo);
      String str5 = jdMethod_char(paramFFSConnectionHolder, localWireInfo);
      FFSDebug.log("WireApprovalRsltProcessor.processOneWireApprovalRevertRslt: ", "Created a failed wire  schedule ", " for wire: ", paramWireInfo.toString(), " scheduleId: ", str5, 6);
    }
    FFSDebug.log("WireApprovalRsltProcessor.processOneWireApprovalRevertRslt: ", "WireInfo.wirePayeeInfo:" + paramWireInfo.getWirePayeeInfo(), 6);
    if (i >= 4) {
      try
      {
        paramWireInfo.setWirePayeeInfo(localWireInfo.getWirePayeeInfo());
        a(paramFFSConnectionHolder, paramWireInfo, "processOneWireApprovalRevertRslt", str4, false);
      }
      catch (Throwable localThrowable)
      {
        String str6 = "WireApprovalRsltProcessor.processOneWireApprovalRevertRslt failed " + localThrowable.toString();
        FFSDebug.log(str6 + FFSDebug.stackTrace(localThrowable), 0);
        throw new FFSException(localThrowable, str6);
      }
    }
    FFSDebug.log("WireApprovalRsltProcessor.processOneWireApprovalRevertRslt: ", " done", 6);
  }
  
  private static String jdMethod_char(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo)
    throws FFSException
  {
    FFSDebug.log("WireApprovalRsltProcessor.createFailedWireSchedule: ", "starts. wireRslt: ", paramWireInfo.toString(), 6);
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramWireInfo.getFiID();
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = 10;
    localScheduleInfo.StartDate = DBUtil.getCurrentStartDate();
    localScheduleInfo.InstanceCount = 1;
    localScheduleInfo.LogID = paramWireInfo.getLogId();
    localScheduleInfo.CurInstanceNum = 1;
    return ScheduleInfo.createSchedule(paramFFSConnectionHolder, "FAILEDWIRETRN", paramWireInfo.getSrvrTid(), localScheduleInfo);
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, String paramString1, String paramString2, boolean paramBoolean)
    throws Exception
  {
    if (paramWireInfo == null) {
      return;
    }
    try
    {
      AuditLogRecord localAuditLogRecord = null;
      str1 = null;
      String str2 = null;
      String str3 = null;
      String str4 = null;
      String str5 = null;
      String str6 = null;
      int i = 0;
      int j = 0;
      if ("processOneWireApprovalRslt".equals(paramString1))
      {
        if ("FUNDSAPPROVED".equalsIgnoreCase(paramString2)) {
          str3 = "Funds have been approved for the single wire transfer.";
        } else if ("NOFUNDS".equalsIgnoreCase(paramString2)) {
          str3 = "Funds have not been approved for the single wire transfer due to no funds.";
        } else if (("FUNDSPENDING".equalsIgnoreCase(paramString2)) || ("CUSTOM_".equalsIgnoreCase(paramString2))) {
          str3 = "Funds approval is pending for the single wire transfer.";
        } else if ("FUNDSFAILEDON".equalsIgnoreCase(paramString2)) {
          str3 = "Funds approval was failed for the single wire transfer.";
        }
        j = 4003;
      }
      else if ("processOneWireApprovalRsltImmediate".equals(paramString1))
      {
        if ("FUNDSAPPROVED".equalsIgnoreCase(paramString2)) {
          str3 = "Funds have been immediately approved for the single wire transfer.";
        } else {
          str3 = "Funds have not been immediately approved for the single wire transfer. It has gone for regular funds approval processing";
        }
        j = 4003;
      }
      else if ("processOneWireApprovalRevertRslt".equals(paramString1))
      {
        if ("INFUNDSREVERT".equalsIgnoreCase(paramString2)) {
          str3 = "Funds approval has been reverted for the single wire transfer.";
        } else {
          str3 = "The single wire transfer was in invalid state to revert funds approval.";
        }
        j = 4006;
      }
      else
      {
        FFSDebug.log("Unknown calling method name, skipping audit logging", 0);
        return;
      }
      str5 = paramWireInfo.getUserId();
      str6 = paramWireInfo.getAmount();
      if ((str6 == null) || (str6.trim().length() == 0)) {
        str6 = "-1";
      }
      if (paramWireInfo.getWirePayeeInfo() != null)
      {
        if (paramWireInfo.getWireDest().equals("HOST")) {
          str1 = "HOST";
        } else {
          str1 = paramWireInfo.getWirePayeeInfo().buildBankAcctId();
        }
        if (paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo() != null) {
          str2 = paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo().getFedRTN();
        }
      }
      if (paramWireInfo.getWireDest().equals("HOST")) {
        str4 = "HOST";
      } else {
        str4 = paramWireInfo.buildFromAcctId();
      }
      if ((paramWireInfo.getCustomerID().equals(paramWireInfo.getUserId())) || (paramWireInfo.getCustomerID().equals(paramWireInfo.getSubmitedBy()))) {
        i = 0;
      } else {
        i = Integer.parseInt(paramWireInfo.getCustomerID());
      }
      localAuditLogRecord = new AuditLogRecord(str5, null, null, str3, paramWireInfo.getExtId(), j, i, new BigDecimal(paramWireInfo.getAmount()), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str1, str2, str4, paramWireInfo.getFromBankId(), -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      String str1 = "WireApprovalRsltProcessor.doAuditLog failed " + localNumberFormatException.toString();
      FFSDebug.log(str1 + FFSDebug.stackTrace(localNumberFormatException), 0);
      throw new FFSException(localNumberFormatException, str1);
    }
  }
  
  private static void jdMethod_if(WireInfo paramWireInfo, String paramString)
  {
    String str1 = "WireApprovalRsltProcessor.doErrorAuditLog";
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    int i = 0;
    AuditLogRecord localAuditLogRecord = null;
    if (paramWireInfo == null) {
      return;
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        String str7 = str1 + "Can not get DB Connection.";
        FFSDebug.log(str7, 0);
      }
      str5 = paramWireInfo.getUserId();
      str6 = paramWireInfo.getAmount();
      if ((str6 == null) || (str6.trim().length() == 0)) {
        str6 = "-1";
      }
      if (paramWireInfo.getWirePayeeInfo() != null)
      {
        if (paramWireInfo.getWireDest().equals("HOST")) {
          str2 = "HOST";
        } else {
          str2 = paramWireInfo.getWirePayeeInfo().buildBankAcctId();
        }
        if (paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo() != null) {
          str3 = paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo().getFedRTN();
        }
      }
      if (paramWireInfo.getWireDest().equals("HOST")) {
        str4 = "HOST";
      } else {
        str4 = paramWireInfo.buildFromAcctId();
      }
      if ((paramWireInfo.getCustomerID().equals(paramWireInfo.getUserId())) || (paramWireInfo.getCustomerID().equals(paramWireInfo.getSubmitedBy()))) {
        i = 0;
      } else {
        i = Integer.parseInt(paramWireInfo.getCustomerID());
      }
      localAuditLogRecord = new AuditLogRecord(str5, null, null, paramString, paramWireInfo.getExtId(), 4003, i, new BigDecimal(str6), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str2, str3, str4, paramWireInfo.getFromBankId(), -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, localFFSConnectionHolder.conn.getConnection());
    }
    catch (Exception localException)
    {
      String str8 = str1 + " failed " + localException.toString();
      FFSDebug.log(str8 + FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private static boolean jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, int paramInt)
  {
    String str1 = "WireApprovalRsltProcessor.revertLimit: ";
    FFSDebug.log(str1 + "start", 6);
    try
    {
      int i = LimitCheckApprovalProcessor.processWireDelete(paramFFSConnectionHolder, paramWireInfo, null);
      String str2 = LimitCheckApprovalProcessor.mapToStatus(i);
      FFSDebug.log(str1, "retStatus LimitCheck:" + str2, 6);
      if ("LIMIT_REVERT_FAILED".equals(str2))
      {
        FFSDebug.log(str1, "Limit Revert Failed", 6);
        paramWireInfo.setStatusCode(19020);
        paramWireInfo.setStatusMsg(BPWLocaleUtil.getMessage(19020, null, "WIRE_MESSAGE"));
        if (paramInt >= 1)
        {
          paramWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
          jdMethod_if(paramWireInfo, "Failed to revert limit.");
        }
        FFSDebug.log(str1, "returning from here", 6);
        return false;
      }
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log(str1 + FFSDebug.stackTrace(localThrowable), 6);
      if (paramInt >= 1)
      {
        paramWireInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        jdMethod_if(paramWireInfo, "Failed to revert limit.");
      }
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.WireApprovalRsltProcessor
 * JD-Core Version:    0.7.0.1
 */