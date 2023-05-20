package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.Wire;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecWireInfo;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.bpw.master.LimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.master.WireProcessor;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.scheduling.db.ScheduleConstants;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;

public class RecWireHandler
  implements DBConsts, FFSConst, ScheduleConstants, BPWResource
{
  private PropertyConfig jc = null;
  private WireProcessor jb = null;
  private int i9 = 0;
  private boolean ja = true;
  
  public RecWireHandler()
  {
    String str1 = null;
    try
    {
      str1 = this.jc.otherProperties.getProperty("bpw.wire.audit");
      if (str1 == null) {
        this.i9 = 0;
      } else {
        this.i9 = Integer.parseInt(str1);
      }
      String str2 = this.jc.otherProperties.getProperty("bpw.wires.config.supportrelease", "true");
      this.ja = (str2.equalsIgnoreCase("TRUE"));
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireApprovalHandler. Invalid Audit log level value", str1, 0);
      this.i9 = 0;
    }
    if (!this.ja) {
      this.jb = new WireProcessor();
    }
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log(" RecWireHandler.eventHander: ", " begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    boolean bool = false;
    jdMethod_do(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool);
    FFSDebug.log(" RecWireHandler.eventHander: ", " end", 6);
  }
  
  private void jdMethod_do(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log(" RecWireHandler.processEvent: ", " begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    Object localObject1 = null;
    try
    {
      if (paramInt != 0) {
        if (paramInt == 1)
        {
          for (int i = 0; i < paramEventInfoArray._array.length; i++)
          {
            str1 = paramEventInfoArray._array[i].InstructionID;
            FFSDebug.log(" RecWireHandler.processEvent: ", "processing eventSeq: " + paramInt + ",RecSrvrTid: " + str1, 6);
            ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(paramEventInfoArray._array[i].FIId, "RECWIRETRN", str1, paramFFSConnectionHolder);
            Object localObject3;
            if ((localScheduleInfo != null) && (localScheduleInfo.StatusOption != 1))
            {
              Object localObject2;
              if ("Close".equalsIgnoreCase(localScheduleInfo.Status))
              {
                FFSDebug.log(" RecWireHandler.processEvent: ", "schedule closed ", ",for recwire: ", str1, 6);
                localObject2 = new RecWireInfo();
                ((WireInfo)localObject2).setSrvrTid(str1);
                localObject2 = Wire.getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject2, true);
                ((WireInfo)localObject2).setFiID(paramEventInfoArray._array[0].FIId);
                localObject3 = paramEventInfoArray._array[0].InstructionType;
                ((WireInfo)localObject2).setWireType((String)localObject3);
                ((WireInfo)localObject2).setPrcStatus("POSTEDON");
                localObject1 = localObject2;
                boolean bool2 = true;
                Wire.updateStatus(paramFFSConnectionHolder, (WireInfo)localObject2, "POSTEDON", bool2);
              }
              else
              {
                if (!paramBoolean)
                {
                  localObject2 = new WireInfo();
                  ((WireInfo)localObject2).setSrvrTid(str1);
                  localObject2 = Wire.getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject2, true);
                  if (!LimitCheckApprovalProcessor.checkEntitlementWire(paramFFSConnectionHolder, (WireInfo)localObject2, null))
                  {
                    localObject3 = "RecWireHandler.processEvents failed to process: Entitlement check failed. RecWireId = " + str1;
                    FFSDebug.log((String)localObject3, 6);
                    Object localObject4 = new RecWireInfo();
                    ((WireInfo)localObject4).setSrvrTid(str1);
                    localObject4 = Wire.getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject4, true);
                    ((WireInfo)localObject4).setFiID(paramEventInfoArray._array[0].FIId);
                    String str2 = paramEventInfoArray._array[0].InstructionType;
                    ((WireInfo)localObject4).setWireType(str2);
                    ((WireInfo)localObject4).setPrcStatus("FAILEDON");
                    boolean bool3 = true;
                    Wire.updateStatus(paramFFSConnectionHolder, (WireInfo)localObject4, "FAILEDON", bool3);
                    ScheduleInfo.delete(paramFFSConnectionHolder, str1, "RECWIRETRN");
                    if (this.i9 < 1) {
                      continue;
                    }
                    ((WireInfo)localObject2).setFiID(((WireInfo)localObject4).getFiID());
                    ((WireInfo)localObject2).setWireType(((WireInfo)localObject4).getWireType());
                    ((WireInfo)localObject2).setPrcStatus(((WireInfo)localObject4).getPrcStatus());
                    jdMethod_int(paramFFSConnectionHolder, (WireInfo)localObject2, "Recurring wire processing failed, not entitled to submit wires.");
                    continue;
                  }
                }
                FFSDebug.log(" RecWireHandler.processEvent: ", "creating single ", "intance from recurring model: ", str1, 6);
                localObject2 = null;
                if (localScheduleInfo.CurInstanceNum == localScheduleInfo.InstanceCount) {
                  localObject2 = Wire.generateSingleWireFromRecWire(paramFFSConnectionHolder, str1, BPWUtil.getDateBeanFormat(localScheduleInfo.NextInstanceDate), 1);
                } else {
                  localObject2 = Wire.generateSingleWireFromRecWire(paramFFSConnectionHolder, str1, BPWUtil.getDateBeanFormat(localScheduleInfo.NextInstanceDate), 2);
                }
                if (!paramBoolean)
                {
                  FFSDebug.log(" RecWireHandler.processEvent: ", "wireInfo for processWireAdd" + localObject2, 6);
                  WireProcessor.limitCheckWireAdd(paramFFSConnectionHolder, (WireInfo)localObject2, null);
                }
                FFSDebug.log(" RecWireHandler.processEvent: ", "wireInfo before status update" + localObject2, 6);
                Wire.updateStatus(paramFFSConnectionHolder, (WireInfo)localObject2);
                if ((!this.ja) && (((WireInfo)localObject2).getPrcStatus().equals("CREATED"))) {
                  localObject2 = this.jb.processSingleWireInAutoReleaseMode(paramFFSConnectionHolder, (WireInfo)localObject2);
                }
                localObject1 = localObject2;
                if ((localObject2 == null) || (((WireInfo)localObject2).getStatusCode() != 0))
                {
                  localObject3 = " RecWireHandler.processEvent: FAILED: COULD NOT FIND THE RecSrvrTid: " + str1 + " in BPW_RecWireInfo TABLE";
                  FFSDebug.log("ERRORCODE:20050", (String)localObject3, 0);
                  FFSDebug.log(" RecWireHandler.processEvent: ", "canceling schedule ", "for recurring model: ", str1, 6);
                  ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECWIRETRN", str1);
                  if (this.i9 >= 1) {
                    jdMethod_int(paramFFSConnectionHolder, (WireInfo)localObject2, "Recurring wire processing failed, wire transfer is not found in database");
                  }
                }
                else
                {
                  ((WireInfo)localObject2).setProcessedBy("BPTW");
                  if (this.i9 >= 4) {
                    if ("Close".equalsIgnoreCase(localScheduleInfo.Status)) {
                      jdMethod_for(paramFFSConnectionHolder, (WireInfo)localObject2, "The recurring model has been completed");
                    } else {
                      jdMethod_for(paramFFSConnectionHolder, (WireInfo)localObject2, "A single wire transfer instance for the recurring model has been created.");
                    }
                  }
                }
              }
            }
            else
            {
              FFSDebug.log(" RecWireHandler.processEvent: ", "no schedule found for the ", "recurringrecurring model: " + str1, ". This model will be closed", 6);
              boolean bool1 = true;
              localObject3 = new RecWireInfo();
              ((WireInfo)localObject3).setSrvrTid(str1);
              localObject3 = Wire.getWireInfo(paramFFSConnectionHolder, (WireInfo)localObject3, true);
              ((WireInfo)localObject3).setFiID(paramEventInfoArray._array[0].FIId);
              ((WireInfo)localObject3).setWireType(paramEventInfoArray._array[0].InstructionType);
              localObject1 = localObject3;
              Wire.updateStatus(paramFFSConnectionHolder, (WireInfo)localObject3, "POSTEDON", bool1);
            }
          }
          FFSDebug.log(" RecWireHandler.processEvent: ", "batch processing completed", 6);
        }
        else if (paramInt == 2)
        {
          FFSDebug.log(" RecWireHandler.processEvent: ", "schedule processing completed", "eventSequence: " + paramInt, 6);
        }
      }
    }
    catch (Throwable localThrowable)
    {
      String str1 = " RecWireHandler.processEvent: Failed. Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1, 0);
      if (this.i9 >= 1) {
        jdMethod_int(paramFFSConnectionHolder, localObject1, null);
      }
      throw new FFSException(localThrowable, str1);
    }
    FFSDebug.log(" RecWireHandler.processEvent: ", " end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log(" RecWireHandler.resubmitEventHandler: begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length + ",instructionType: " + paramEventInfoArray._array[0].InstructionType);
    boolean bool = true;
    jdMethod_do(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool);
    FFSDebug.log(" RecWireHandler.resubmitEventHandler: end");
  }
  
  private void jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, String paramString)
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
      str2 = "The recurring model has been completed";
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
      localAuditLogRecord = new AuditLogRecord(paramWireInfo.getUserId(), null, null, str2, paramWireInfo.getExtId(), 4004, i, new BigDecimal(str6), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str3, str4, str5, paramWireInfo.getFromBankId(), -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (Exception localException)
    {
      String str7 = str1 + " failed " + localException.toString();
      FFSDebug.log(str7 + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str7);
    }
  }
  
  private void jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, String paramString)
  {
    String str1 = "FailedWireHandler.logError";
    try
    {
      if (paramString != null) {
        jdMethod_for(paramFFSConnectionHolder, paramWireInfo, paramString);
      } else {
        jdMethod_for(paramFFSConnectionHolder, paramWireInfo, "Recurring Wire processing failed, unknown error occurred");
      }
    }
    catch (Exception localException)
    {
      String str2 = str1 + " failed " + localException.toString();
      FFSDebug.log(str2 + FFSDebug.stackTrace(localException), 0);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.RecWireHandler
 * JD-Core Version:    0.7.0.1
 */