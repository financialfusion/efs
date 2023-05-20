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

public class WireApprovalHandler
  implements BPWResource, FFSConst, DBConsts
{
  private WireApproval jj = null;
  private ArrayList jg = null;
  private PropertyConfig ji = null;
  private int jf = 0;
  private boolean jh = false;
  
  public WireApprovalHandler()
  {
    String str = null;
    try
    {
      str = this.ji.otherProperties.getProperty("bpw.wire.audit");
      if (str == null) {
        this.jf = 0;
      } else {
        this.jf = Integer.parseInt(str);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireApprovalHandler. Invalid Audit log level value", str, 0);
      this.jf = 0;
    }
    this.jh = Boolean.valueOf(this.ji.otherProperties.getProperty("bpw.wire.funds.approval.support", "false")).booleanValue();
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    if (!this.jh)
    {
      FFSDebug.log("WireApprovalHandler.eventHandler: ", "Funds approval is not supported", 6);
      return;
    }
    FFSDebug.log("WireApprovalHandler.eventHandler: begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    jdMethod_for(paramInt, paramEventInfoArray, paramFFSConnectionHolder, false);
    FFSDebug.log("WireApprovalHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    if (!this.jh)
    {
      FFSDebug.log("WireApprovalHandler.eventHandler: ", "Funds approval is not supported", 6);
      return;
    }
    FFSDebug.log("WireApprovalHandler.resubmitEventHandler: begin, ", "eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    jdMethod_for(paramInt, paramEventInfoArray, paramFFSConnectionHolder, false);
    FFSDebug.log("WireApprovalHandler.resubmitEventHandler: end", 6);
  }
  
  private void jdMethod_for(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws Exception
  {
    FFSDebug.log("WireApprovalHandler.processEvent: ", " begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    String str3;
    Object localObject;
    if (paramInt == 0)
    {
      this.jg = new ArrayList();
      String str1 = paramEventInfoArray._array[0].InstructionID;
      str3 = paramEventInfoArray._array[0].EventID;
      localObject = new WireInfo();
      ((WireInfo)localObject).setSrvrTid(str1);
      ((WireInfo)localObject).setEventId(str3);
      ((WireInfo)localObject).setEventSequence(paramInt);
      ((WireInfo)localObject).setPossibleDuplicate(paramBoolean);
      ((WireInfo)localObject).setFiID(paramEventInfoArray._array[0].FIId);
      ((WireInfo)localObject).setWireState(paramEventInfoArray._array[0].InstructionType);
      this.jg.add(localObject);
    }
    else
    {
      if (paramInt == 1) {
        for (int i = 0; i < paramEventInfoArray._array.length; i++)
        {
          str3 = paramEventInfoArray._array[i].InstructionID;
          localObject = paramEventInfoArray._array[i].EventID;
          FFSDebug.log("WireApprovalHandler.processEvent: ", "eventSeq: " + paramInt + ",srvrTID: " + str3, 6);
          WireInfo localWireInfo = null;
          try
          {
            localWireInfo = new WireInfo();
            localWireInfo.setSrvrTid(str3);
            localWireInfo.setFiID(paramEventInfoArray._array[0].FIId);
            localWireInfo.setWireState(paramEventInfoArray._array[0].InstructionType);
            localWireInfo = Wire.getWireInfo(paramFFSConnectionHolder, localWireInfo);
            String str4;
            if (localWireInfo == null)
            {
              str4 = "ERRORCODE:20000WireApprovalHandler.processEvent:  FAILED: CAN NOT FIND THE SrvrTID: " + str3 + " in BPW_WireInfo";
              FFSDebug.log(str4, 0);
            }
            else
            {
              localWireInfo = Wire.populateCustomerAndFIInfo(paramFFSConnectionHolder, localWireInfo);
              localWireInfo.setProcessedBy("BPTW");
              str4 = localWireInfo.getPrcStatus();
              FFSDebug.log("WireHandler.processEvents, wire currStatus: ", str4, 6);
              if ((!"WILLPROCESSON".equalsIgnoreCase(str4)) && (!"NOFUNDS".equalsIgnoreCase(str4))) {
                localWireInfo.setPossibleDuplicate(true);
              } else {
                localWireInfo.setPossibleDuplicate(paramBoolean);
              }
              localWireInfo.setSrvrTid(str3);
              localWireInfo.setEventId((String)localObject);
              localWireInfo.setEventSequence(paramInt);
              localWireInfo.setFiID(paramEventInfoArray._array[0].FIId);
              localWireInfo.setWireState(paramEventInfoArray._array[0].InstructionType);
              localWireInfo.setPrcStatus("INFUNDSAPPROVAL");
              Wire.updateStatus(paramFFSConnectionHolder, localWireInfo);
              this.jg.add(localWireInfo);
              if (this.jf >= 4) {
                jdMethod_new(paramFFSConnectionHolder, localWireInfo, null);
              }
            }
          }
          catch (Throwable localThrowable)
          {
            String str5 = "WireApprovalHandler.processEvent: Failed:" + FFSDebug.stackTrace(localThrowable);
            FFSDebug.log(str5, 0);
            if (this.jf >= 1) {
              jdMethod_for(localWireInfo);
            }
            throw new FFSException(str5);
          }
        }
      }
      if (paramInt == 2)
      {
        String str2 = paramEventInfoArray._array[0].InstructionID;
        str3 = paramEventInfoArray._array[0].EventID;
        localObject = new WireInfo();
        ((WireInfo)localObject).setSrvrTid(str2);
        ((WireInfo)localObject).setEventId(str3);
        ((WireInfo)localObject).setEventSequence(paramInt);
        ((WireInfo)localObject).setPossibleDuplicate(paramBoolean);
        ((WireInfo)localObject).setFiID(paramEventInfoArray._array[0].FIId);
        this.jg.add(localObject);
        jdMethod_if(paramFFSConnectionHolder, this.jg);
        this.jg = null;
      }
      else if ((paramInt != 3) && (paramInt == 4))
      {
        jdMethod_if(paramFFSConnectionHolder, this.jg);
      }
    }
    FFSDebug.log("WireApprovalHandler.processEvent: ", " end", 6);
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ArrayList paramArrayList)
    throws Exception
  {
    String str = DBConnCache.getNewBatchKey();
    DBConnCache.bind(str, paramFFSConnectionHolder);
    WireInfo[] arrayOfWireInfo = (WireInfo[])paramArrayList.toArray(new WireInfo[0]);
    Hashtable localHashtable = new Hashtable();
    for (int i = 0; i < arrayOfWireInfo.length; i++) {
      arrayOfWireInfo[i].setDbTransKey(str);
    }
    this.jj.addWiresApproval(arrayOfWireInfo, localHashtable);
    DBConnCache.unbind(str);
    paramArrayList.clear();
  }
  
  private void jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, String paramString)
    throws Exception
  {
    String str1 = "WireApprovalHandler.doAuditLogging";
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
      str2 = "The single wire transfer is in the process of funds approval";
    }
    try
    {
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
      localAuditLogRecord = new AuditLogRecord(str6, null, null, str2, paramWireInfo.getExtId(), 4002, i, new BigDecimal(str7), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str3, str4, str5, paramWireInfo.getFromBankId(), -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (Exception localException)
    {
      String str8 = str1 + " failed " + localException.toString();
      FFSDebug.log(str8 + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str8);
    }
  }
  
  private void jdMethod_for(WireInfo paramWireInfo)
  {
    String str1 = "WireApprovalHandler.logError";
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
      jdMethod_new(localFFSConnectionHolder, paramWireInfo, "Wire approval processing failed, unknown error occurred");
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
 * Qualified Name:     com.ffusion.ffs.bpw.handler.WireApprovalHandler
 * JD-Core Version:    0.7.0.1
 */