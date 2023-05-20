package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.WireBackendHandler;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Wire;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;

public class WireHandler
  implements DBConsts, FFSConst, BPWResource
{
  private WireBackendHandler jp = null;
  private ArrayList jn = null;
  private PropertyConfig jo = null;
  private boolean jm = false;
  private int jl = 0;
  
  public WireHandler()
  {
    String str = null;
    try
    {
      str = this.jo.otherProperties.getProperty("bpw.wire.audit");
      if (str == null) {
        this.jl = 0;
      } else {
        this.jl = Integer.parseInt(str);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireApprovalHandler. Invalid Audit log level value", str, 0);
      this.jl = 0;
    }
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("WireHandler.eventHandler: begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    jdMethod_int(paramInt, paramEventInfoArray, paramFFSConnectionHolder, false);
    FFSDebug.log("WireHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("WireHandler.resubmitEventHandler: begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    jdMethod_int(paramInt, paramEventInfoArray, paramFFSConnectionHolder, true);
    FFSDebug.log("WireHandler.resubmitEventHandler: end", 6);
  }
  
  private void jdMethod_int(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws Exception
  {
    FFSDebug.log("WireHandler.processEvents: begin, eventSeq: " + paramInt + ", possible dup: " + paramBoolean + ",length: " + paramEventInfoArray._array.length, 6);
    Object localObject1;
    Object localObject2;
    if (paramInt == 0)
    {
      this.jm = false;
      this.jn = new ArrayList();
      String str1 = paramEventInfoArray._array[0].InstructionID;
      localObject1 = paramEventInfoArray._array[0].EventID;
      localObject2 = new WireInfo();
      ((WireInfo)localObject2).setSrvrTid(str1);
      ((WireInfo)localObject2).setEventId((String)localObject1);
      ((WireInfo)localObject2).setEventSequence(paramInt);
      ((WireInfo)localObject2).setPossibleDuplicate(paramBoolean);
      ((WireInfo)localObject2).setFiID(paramEventInfoArray._array[0].FIId);
      ((WireInfo)localObject2).setWireType(paramEventInfoArray._array[0].InstructionType);
      this.jn.add(localObject2);
    }
    else
    {
      if (paramInt == 1)
      {
        this.jm = true;
        for (int i = 0; i < paramEventInfoArray._array.length; i++)
        {
          localObject1 = null;
          localObject2 = paramEventInfoArray._array[i].InstructionID;
          String str3 = paramEventInfoArray._array[i].EventID;
          FFSDebug.log("WireHandler.processEvents: eventSeq: " + paramInt + ",srvrTID: " + (String)localObject2 + ", FIID: " + paramEventInfoArray._array[i].FIId, 6);
          try
          {
            localObject1 = new WireInfo();
            ((WireInfo)localObject1).setSrvrTid(paramEventInfoArray._array[i].InstructionID);
            ((WireInfo)localObject1).setEventId(paramEventInfoArray._array[i].EventID);
            ((WireInfo)localObject1).setFiID(paramEventInfoArray._array[i].FIId);
            ((WireInfo)localObject1).setWireState(paramEventInfoArray._array[i].InstructionType);
            ((WireInfo)localObject1).setPrcStatus(paramEventInfoArray._array[i].Status);
            localObject1 = Wire.getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject1);
            String str4;
            if (localObject1 == null)
            {
              str4 = "ERRORCODE:20000 *** WireHandler.processEvents FAILED: CAN NOT FIND THE WIREINFO: " + localObject1 + " IN BPTW DATABASE";
              FFSDebug.log(str4, 0);
              if (this.jl >= 1) {
                jdMethod_for((WireInfo)localObject1, "Wire backend processing failed, wire transfer is not found in database");
              }
            }
            else
            {
              localObject1 = Wire.populateCustomerAndFIInfo(paramFFSConnectionHolder, (WireInfo)localObject1);
              ((WireInfo)localObject1).setProcessedBy("BPTW");
              FFSDebug.log("WireHandler.processEvents, wire status: ", ((WireInfo)localObject1).getPrcStatus(), 6);
              if (!"FUNDSAPPROVED".equalsIgnoreCase(((WireInfo)localObject1).getPrcStatus())) {
                ((WireInfo)localObject1).setPossibleDuplicate(true);
              } else {
                ((WireInfo)localObject1).setPossibleDuplicate(paramBoolean);
              }
              str4 = "INPROCESS";
              ((WireInfo)localObject1).setPrcStatus(str4);
              str5 = FFSUtil.getDateString("yyyyMMddHHmmss");
              ((WireInfo)localObject1).setDatePosted(str5);
              ((WireInfo)localObject1).setEventId(paramEventInfoArray._array[i].EventID);
              ((WireInfo)localObject1).setFiID(paramEventInfoArray._array[i].FIId);
              ((WireInfo)localObject1).setWireState(paramEventInfoArray._array[i].InstructionType);
              ((WireInfo)localObject1).setEventSequence(paramInt);
              Wire.updateToBackendStatus(paramFFSConnectionHolder, (WireInfo)localObject1);
              this.jn.add(localObject1);
              if (this.jl >= 4) {
                jdMethod_try(paramFFSConnectionHolder, (WireInfo)localObject1, null);
              }
            }
          }
          catch (Throwable localThrowable)
          {
            String str5 = "WireHandler.processEvents failed:" + FFSDebug.stackTrace(localThrowable);
            FFSDebug.log(str5);
            if (this.jl >= 1) {
              jdMethod_for((WireInfo)localObject1, null);
            }
            throw new FFSException(localThrowable, str5);
          }
        }
      }
      if (paramInt == 2)
      {
        String str2 = paramEventInfoArray._array[0].InstructionID;
        localObject1 = paramEventInfoArray._array[0].EventID;
        localObject2 = new WireInfo();
        ((WireInfo)localObject2).setSrvrTid(str2);
        ((WireInfo)localObject2).setEventId((String)localObject1);
        ((WireInfo)localObject2).setEventSequence(paramInt);
        ((WireInfo)localObject2).setPossibleDuplicate(paramBoolean);
        ((WireInfo)localObject2).setFiID(paramEventInfoArray._array[0].FIId);
        ((WireInfo)localObject2).setWireState(paramEventInfoArray._array[0].InstructionType);
        this.jn.add(localObject2);
        if (this.jm) {
          jdMethod_do(paramFFSConnectionHolder, this.jn);
        }
        this.jn = null;
      }
      else if ((paramInt != 3) && (paramInt == 4) && (this.jm))
      {
        jdMethod_do(paramFFSConnectionHolder, this.jn);
      }
    }
    FFSDebug.log("WireHandler.processEvents: end", 6);
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, ArrayList paramArrayList)
    throws Exception
  {
    WireInfo[] arrayOfWireInfo = (WireInfo[])paramArrayList.toArray(new WireInfo[0]);
    Hashtable localHashtable = new Hashtable();
    String str = DBConnCache.getNewBatchKey();
    DBConnCache.bind(str, paramFFSConnectionHolder);
    for (int i = 0; i < arrayOfWireInfo.length; i++) {
      arrayOfWireInfo[i].setDbTransKey(str);
    }
    this.jp.processWire(arrayOfWireInfo, localHashtable);
    DBConnCache.unbind(str);
    paramArrayList.clear();
  }
  
  private void jdMethod_try(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, String paramString)
    throws Exception
  {
    String str1 = "WireHandler.doAuditLogging: ";
    String str2 = paramString;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    int i = 0;
    AuditLogRecord localAuditLogRecord = null;
    if (paramWireInfo == null) {
      return;
    }
    if (paramString == null) {
      str2 = "The single wire transfer is in the process of backend processing";
    }
    try
    {
      FFSDebug.log(str1, "wire: " + paramWireInfo, 6);
      str6 = paramWireInfo.getUserId();
      str7 = paramWireInfo.getAmount();
      if ((str7 == null) || (str7.trim().length() == 0)) {
        str7 = "-1";
      }
      if (paramWireInfo.getWirePayeeInfo() != null)
      {
        if (paramWireInfo.getWireDest().equals("HOST")) {
          str3 = "HOST";
        } else {
          str3 = paramWireInfo.getWirePayeeInfo().buildBankAcctId();
        }
        if (paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo() != null) {
          str4 = paramWireInfo.getWirePayeeInfo().getBeneficiaryBankInfo().getFedRTN();
        }
      }
      if (paramWireInfo.getWireDest().equals("HOST")) {
        str5 = "HOST";
      } else {
        str5 = paramWireInfo.buildFromAcctId();
      }
      if ((paramWireInfo.getCustomerID().equals(paramWireInfo.getUserId())) || (paramWireInfo.getCustomerID().equals(paramWireInfo.getSubmitedBy()))) {
        i = 0;
      } else {
        i = Integer.parseInt(paramWireInfo.getCustomerID());
      }
      localAuditLogRecord = new AuditLogRecord(str6, null, null, str2, paramWireInfo.getExtId(), 4001, i, new BigDecimal(str7), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str3, str4, str5, paramWireInfo.getFromBankId(), -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (Exception localException)
    {
      String str8 = str1 + " failed " + localException.toString();
      FFSDebug.log(str8 + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str8);
    }
  }
  
  private void jdMethod_for(WireInfo paramWireInfo, String paramString)
  {
    String str1 = "WireHandler.logError";
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        String str2 = str1 + "Can not get DB Connection.";
        FFSDebug.log(str2, 0);
      }
      if (paramString != null) {
        jdMethod_try(localFFSConnectionHolder, paramWireInfo, paramString);
      } else {
        jdMethod_try(localFFSConnectionHolder, paramWireInfo, "Wire backend processing failed, unknown error occurred");
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      String str3 = str1 + " failed " + localException.toString();
      FFSDebug.log(str3 + FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.WireHandler
 * JD-Core Version:    0.7.0.1
 */