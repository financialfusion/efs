package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.FailedWire;
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
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;

public class FailedWireHandler
  implements DBConsts, FFSConst, BPWResource
{
  private FailedWire jE = null;
  private String jG = null;
  private String jD = "Wire_";
  private PropertyConfig jF = null;
  private boolean jB = false;
  private ArrayList jC = null;
  private int jA = 0;
  
  public FailedWireHandler()
  {
    String str = null;
    try
    {
      str = this.jF.otherProperties.getProperty("bpw.wire.audit");
      if (str == null) {
        this.jA = 0;
      } else {
        this.jA = Integer.parseInt(str);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireApprovalHandler. Invalid Audit log level value", str, 0);
      this.jA = 0;
    }
    this.jE = new FailedWire();
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("FailedWireHandler.eventHandler: begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    boolean bool = false;
    jdMethod_new(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool);
    FFSDebug.log("FailedWireHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("FailedWireHandler.resubmitEventHandler: begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    boolean bool = true;
    jdMethod_new(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool);
    FFSDebug.log("FailedWireHandler.resubmitEventHandler: end", 6);
  }
  
  private void jdMethod_new(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws Exception
  {
    FFSDebug.log("FailedWireHandler.processEvents: ", "begin, eventSeq: " + paramInt + ", possible dup: " + paramBoolean + ",length: " + paramEventInfoArray._array.length, 6);
    this.jG = DBUtil.getNextIndexString("BatchDBTransID");
    Object localObject1;
    String str3;
    Object localObject2;
    if (paramInt == 0)
    {
      this.jB = false;
      this.jC = new ArrayList();
      String str1 = paramEventInfoArray._array[0].InstructionID;
      localObject1 = paramEventInfoArray._array[0].EventID;
      str3 = this.jD + this.jG;
      localObject2 = new WireInfo();
      ((WireInfo)localObject2).setSrvrTid(str1);
      ((WireInfo)localObject2).setEventId((String)localObject1);
      ((WireInfo)localObject2).setEventSequence(paramInt);
      ((WireInfo)localObject2).setPossibleDuplicate(paramBoolean);
      ((WireInfo)localObject2).setDbTransKey(str3);
      ((WireInfo)localObject2).setFiID(paramEventInfoArray._array[0].FIId);
      ((WireInfo)localObject2).setWireType(paramEventInfoArray._array[0].InstructionType);
      this.jC.add(localObject2);
    }
    else
    {
      if (paramInt == 1)
      {
        this.jB = true;
        for (int i = 0; i < paramEventInfoArray._array.length; i++)
        {
          localObject1 = null;
          str3 = paramEventInfoArray._array[i].InstructionID;
          localObject2 = paramEventInfoArray._array[i].EventID;
          FFSDebug.log("FailedWireHandler.processEvents: eventSeq: " + paramInt + ",srvrTID: " + str3, 6);
          try
          {
            localObject1 = new WireInfo();
            ((WireInfo)localObject1).setSrvrTid(paramEventInfoArray._array[i].InstructionID);
            ((WireInfo)localObject1).setEventId(paramEventInfoArray._array[i].EventID);
            ((WireInfo)localObject1).setFiID(paramEventInfoArray._array[i].FIId);
            ((WireInfo)localObject1).setWireType(paramEventInfoArray._array[i].InstructionType);
            ((WireInfo)localObject1).setPrcStatus(paramEventInfoArray._array[i].Status);
            localObject1 = Wire.getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject1);
            String str4;
            if (localObject1 == null)
            {
              str4 = "*** FailedWireHandler.processEvents FAILED: CAN NOT FIND THE WIREINFO: " + localObject1 + " IN BPTW DATABASE";
              FFSDebug.log("ERRORCODE:20000", str4, 0);
              if (this.jA >= 1) {
                jdMethod_int((WireInfo)localObject1, "Failed wire processing failed, wire transfer is not found in database");
              }
            }
            else
            {
              localObject1 = Wire.populateCustomerAndFIInfo(paramFFSConnectionHolder, (WireInfo)localObject1);
              ((WireInfo)localObject1).setProcessedBy("BPTW");
              str4 = this.jD + this.jG;
              str5 = ((WireInfo)localObject1).getPrcStatus() + "_NOTIF";
              ((WireInfo)localObject1).setPrcStatus(str5);
              ((WireInfo)localObject1).setDbTransKey(str4);
              ((WireInfo)localObject1).setEventId(paramEventInfoArray._array[i].EventID);
              ((WireInfo)localObject1).setFiID(paramEventInfoArray._array[i].FIId);
              ((WireInfo)localObject1).setWireType(paramEventInfoArray._array[i].InstructionType);
              ((WireInfo)localObject1).setEventSequence(paramInt);
              Wire.updateStatus(paramFFSConnectionHolder, (WireInfo)localObject1);
              this.jC.add(localObject1);
              if (this.jA >= 4) {
                jdMethod_byte(paramFFSConnectionHolder, (WireInfo)localObject1, null);
              }
            }
          }
          catch (Throwable localThrowable)
          {
            String str5 = "FailedWireHandler.processEvents failed:" + FFSDebug.stackTrace(localThrowable);
            FFSDebug.log(str5);
            if (this.jA >= 1) {
              jdMethod_int((WireInfo)localObject1, null);
            }
            throw new FFSException(localThrowable, str5);
          }
        }
      }
      String str2;
      if (paramInt == 2)
      {
        str2 = this.jD + this.jG;
        this.jG = DBUtil.getNextIndexString("BatchDBTransID");
        localObject1 = paramEventInfoArray._array[0].InstructionID;
        str3 = paramEventInfoArray._array[0].EventID;
        localObject2 = new WireInfo();
        ((WireInfo)localObject2).setSrvrTid((String)localObject1);
        ((WireInfo)localObject2).setEventId(str3);
        ((WireInfo)localObject2).setEventSequence(paramInt);
        ((WireInfo)localObject2).setPossibleDuplicate(paramBoolean);
        ((WireInfo)localObject2).setDbTransKey(str2);
        ((WireInfo)localObject2).setFiID(paramEventInfoArray._array[0].FIId);
        ((WireInfo)localObject2).setWireType(paramEventInfoArray._array[0].InstructionType);
        this.jC.add(localObject2);
        if (this.jB) {
          a(paramFFSConnectionHolder, str2, this.jC);
        }
      }
      else if ((paramInt != 3) && (paramInt == 4))
      {
        str2 = this.jD + this.jG;
        this.jG = DBUtil.getNextIndexString("BatchDBTransID");
        if (this.jB) {
          a(paramFFSConnectionHolder, str2, this.jC);
        }
      }
    }
    FFSDebug.log("FailedWireHandler.processEvents: end", 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString, ArrayList paramArrayList)
    throws Exception
  {
    DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
    DBConnCache.bind(paramString, paramFFSConnectionHolder);
    WireInfo[] arrayOfWireInfo = (WireInfo[])paramArrayList.toArray(new WireInfo[0]);
    Hashtable localHashtable = new Hashtable();
    this.jE.processFailedWires(arrayOfWireInfo, localHashtable);
    DBConnCache.unbind(paramString);
    paramArrayList.clear();
  }
  
  private void jdMethod_byte(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, String paramString)
    throws Exception
  {
    String str1 = "FailedWireHandler.doAuditLogging";
    String str2 = paramString;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    int i = 0;
    AuditLogRecord localAuditLogRecord = null;
    if (paramWireInfo == null) {
      return;
    }
    if (paramString == null) {
      str2 = "Notification for the single wire transfer processing failure has been sent";
    }
    try
    {
      str6 = paramWireInfo.getAmount();
      if ((str6 == null) || (str6.trim().length() == 0)) {
        str6 = "-1";
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
      localAuditLogRecord = new AuditLogRecord(paramWireInfo.getUserId(), null, null, str2, paramWireInfo.getExtId(), 4007, i, new BigDecimal(str6), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str3, str4, str5, paramWireInfo.getFromBankId(), -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (Exception localException)
    {
      String str7 = str1 + " failed " + localException.toString();
      FFSDebug.log(str7 + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str7);
    }
  }
  
  private void jdMethod_int(WireInfo paramWireInfo, String paramString)
  {
    String str1 = "FailedWireHandler.logError";
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
        jdMethod_byte(localFFSConnectionHolder, paramWireInfo, paramString);
      } else {
        jdMethod_byte(localFFSConnectionHolder, paramWireInfo, "Failed Wire processing failed, unknown error occurred");
      }
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
 * Qualified Name:     com.ffusion.ffs.bpw.handler.FailedWireHandler
 * JD-Core Version:    0.7.0.1
 */