package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.WireApproval;
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
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;

public class WireRevertApprovalHandler
  implements BPWResource, FFSConst, DBConsts
{
  private WireApproval h1 = null;
  PropertyConfig h0 = null;
  private ArrayList hY = null;
  private int hX = 0;
  private boolean hZ = false;
  
  public WireRevertApprovalHandler()
  {
    String str = null;
    try
    {
      str = this.h0.otherProperties.getProperty("bpw.wire.audit");
      if (str == null) {
        this.hX = 0;
      } else {
        this.hX = Integer.parseInt(str);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireApprovalHandler. Invalid Audit log level value", str, 0);
      this.hX = 0;
    }
    this.hZ = Boolean.valueOf(this.h0.otherProperties.getProperty("bpw.wire.funds.approval.support", "false")).booleanValue();
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    if (!this.hZ)
    {
      FFSDebug.log("WireRevertApprovalHandler.eventHandler: ", "Funds approval is not supported", 6);
      return;
    }
    FFSDebug.log("WireRevertApprovalHandler.eventHandler: ", " begin, eventSeq: " + paramInt, ", length: " + paramEventInfoArray._array.length, 6);
    boolean bool = false;
    revertWires(paramFFSConnectionHolder, paramInt, paramEventInfoArray, bool);
    FFSDebug.log("WireRevertApprovalHandler.eventHandler: ", " end, eventSeq: " + paramInt, ", length: " + paramEventInfoArray._array.length, 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    if (!this.hZ)
    {
      FFSDebug.log("WireRevertApprovalHandler.resubmitEventHandler:", "Funds approval is not supported", 6);
      return;
    }
    FFSDebug.log("= WireRevertApprovalHandler.resubmitEventHandler: ", "begin, eventSeq: " + paramInt, ",length: " + paramEventInfoArray._array.length, 6);
    boolean bool = true;
    revertWires(paramFFSConnectionHolder, paramInt, paramEventInfoArray, bool);
    FFSDebug.log("WireRevertApprovalHandler.resubmitEventHandler: end", 6);
  }
  
  public void revertWires(FFSConnectionHolder paramFFSConnectionHolder, int paramInt, EventInfoArray paramEventInfoArray, boolean paramBoolean)
    throws Exception
  {
    FFSDebug.log("WireRevertApprovalHandler.revertWires: ", " begin, eventSeq: " + paramInt, ", length: " + paramEventInfoArray._array.length, 6);
    Object localObject1;
    Object localObject2;
    if (paramInt == 0)
    {
      this.hY = new ArrayList();
      String str1 = paramEventInfoArray._array[0].InstructionID;
      localObject1 = paramEventInfoArray._array[0].EventID;
      localObject2 = new WireInfo();
      ((WireInfo)localObject2).setSrvrTid(str1);
      ((WireInfo)localObject2).setEventId((String)localObject1);
      ((WireInfo)localObject2).setEventSequence(paramInt);
      ((WireInfo)localObject2).setPossibleDuplicate(paramBoolean);
      ((WireInfo)localObject2).setFiID(paramEventInfoArray._array[0].FIId);
      ((WireInfo)localObject2).setWireType(paramEventInfoArray._array[0].InstructionType);
      this.hY.add(localObject2);
    }
    else
    {
      String str3;
      int k;
      if (paramInt == 1) {
        for (int i = 0; i < paramEventInfoArray._array.length; i++)
        {
          localObject1 = paramEventInfoArray._array[i].InstructionID;
          localObject2 = paramEventInfoArray._array[i].EventID;
          FFSDebug.log("WireRevertApprovalHandler.revertWires: ", "processing eventSeq: " + paramInt, ", srvrTID: ", (String)localObject1, 6);
          try
          {
            WireInfo localWireInfo = new WireInfo();
            localWireInfo.setSrvrTid(paramEventInfoArray._array[i].InstructionID);
            localWireInfo.setFiID(paramEventInfoArray._array[i].FIId);
            localWireInfo = Wire.getWireInfo(paramFFSConnectionHolder, localWireInfo);
            if (localWireInfo == null)
            {
              str3 = "FAILED. COULD NOT FIND SrvrTID: " + (String)localObject1 + " in BPW_WireInfo";
              FFSDebug.log("ERRORCODE:20000", "WireRevertApprovalHandler.revertWires: ", str3, 0);
              if (this.hX >= 1) {
                jdMethod_do(localWireInfo, "Wire revert processing failed, wire transfer is not found in database");
              }
            }
            else
            {
              localWireInfo = Wire.populateCustomerAndFIInfo(paramFFSConnectionHolder, localWireInfo);
              localWireInfo.setProcessedBy("BPTW");
              localWireInfo.setEventId(paramEventInfoArray._array[i].EventID);
              localWireInfo.setFiID(paramEventInfoArray._array[i].FIId);
              localWireInfo.setWireType(paramEventInfoArray._array[i].InstructionType);
              localWireInfo.setEventSequence(paramInt);
              localWireInfo.setPossibleDuplicate(paramBoolean);
              str3 = "INFUNDSREVERT";
              localWireInfo.setPrcStatus(str3);
              k = 0;
              Wire.updateStatus(paramFFSConnectionHolder, localWireInfo, "INFUNDSREVERT", k);
              this.hY.add(localWireInfo);
              if (this.hX >= 4) {
                jdMethod_if(paramFFSConnectionHolder, localWireInfo, null);
              }
            }
          }
          catch (Throwable localThrowable)
          {
            str3 = "WireRevertApprovalHandler.revertWires: Faield. Error: " + FFSDebug.stackTrace(localThrowable);
            FFSDebug.log(str3);
            throw new FFSException(str3);
          }
        }
      }
      String str2;
      if (paramInt == 2)
      {
        str2 = paramEventInfoArray._array[0].EventID;
        localObject1 = new WireInfo();
        ((WireInfo)localObject1).setEventId(str2);
        ((WireInfo)localObject1).setEventSequence(paramInt);
        ((WireInfo)localObject1).setPossibleDuplicate(paramBoolean);
        this.hY.add(localObject1);
        localObject2 = (WireInfo[])this.hY.toArray(new WireInfo[0]);
        Hashtable localHashtable = new Hashtable();
        str3 = DBConnCache.getNewBatchKey();
        DBConnCache.bind(str3, paramFFSConnectionHolder);
        for (k = 0; k < localObject2.length; k++) {
          localObject2[k].setDbTransKey(str3);
        }
        this.h1.revertWireApproval((WireInfo[])localObject2, localHashtable);
        DBConnCache.unbind(str3);
        this.hY = null;
      }
      else if ((paramInt != 3) && (paramInt == 4))
      {
        str2 = DBConnCache.getNewBatchKey();
        DBConnCache.bind(str2, paramFFSConnectionHolder);
        localObject1 = (WireInfo[])this.hY.toArray(new WireInfo[0]);
        localObject2 = new Hashtable();
        for (int j = 0; j < localObject1.length; j++) {
          localObject1[j].setDbTransKey(str2);
        }
        this.h1.revertWireApproval((WireInfo[])localObject1, (Hashtable)localObject2);
        DBConnCache.unbind(str2);
        this.hY.clear();
      }
    }
    FFSDebug.log("WireRevertApprovalHandler.revertWires: ", "end", 6);
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, String paramString)
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
      str2 = "The single wire transfer is in the process of funds revert";
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
      localAuditLogRecord = new AuditLogRecord(paramWireInfo.getUserId(), null, null, str2, paramWireInfo.getExtId(), 4005, i, new BigDecimal(str6), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str3, str4, str5, paramWireInfo.getFromBankId(), -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (Exception localException)
    {
      String str7 = str1 + " failed " + localException.toString();
      FFSDebug.log(str7 + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str7);
    }
  }
  
  private void jdMethod_do(WireInfo paramWireInfo, String paramString)
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
        jdMethod_if(localFFSConnectionHolder, paramWireInfo, paramString);
      } else {
        jdMethod_if(localFFSConnectionHolder, paramWireInfo, "Recurring Wire processing failed, unknown error occurred");
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
 * Qualified Name:     com.ffusion.ffs.bpw.handler.WireRevertApprovalHandler
 * JD-Core Version:    0.7.0.1
 */