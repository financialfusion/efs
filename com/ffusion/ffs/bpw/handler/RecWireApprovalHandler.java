package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Wire;
import com.ffusion.ffs.bpw.interfaces.BPWBankInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.WireInfo;
import com.ffusion.ffs.bpw.interfaces.WirePayeeInfo;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;

public class RecWireApprovalHandler
  implements FFSConst, DBConsts, BPWResource
{
  private String i7 = null;
  private String i5 = "RecWireApproval_";
  private PropertyConfig i6 = null;
  private boolean i4 = false;
  private int i3 = 0;
  
  public RecWireApprovalHandler()
  {
    String str = null;
    try
    {
      str = this.i6.otherProperties.getProperty("bpw.wire.audit");
      if (str == null) {
        this.i3 = 0;
      } else {
        this.i3 = Integer.parseInt(str);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("WireApprovalHandler. Invalid Audit log level value", str, 0);
      this.i3 = 0;
    }
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("RecWireApprovalHandler.eventHandler: begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    jdMethod_if(paramInt, paramEventInfoArray, paramFFSConnectionHolder, false);
    FFSDebug.log("RecWireApprovalHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("RecWireApprovalHandler.resubmitEventHandler: begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    jdMethod_if(paramInt, paramEventInfoArray, paramFFSConnectionHolder, true);
    FFSDebug.log("RecWireApprovalHandler.resubmitEventHandler: end", 6);
  }
  
  private void jdMethod_if(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws Exception
  {
    FFSDebug.log("RecWireApprovalHandler.processEvents: ", "begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    try
    {
      this.i7 = DBUtil.getNextIndexString("BatchDBTransID");
      if (paramInt != 0)
      {
        if (paramInt == 1)
        {
          PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
          for (int i = 0; i < paramEventInfoArray._array.length; i++)
          {
            String str2 = paramEventInfoArray._array[i].InstructionID;
            String str3 = paramEventInfoArray._array[i].EventID;
            String str4 = this.i5 + this.i7;
            WireInfo localWireInfo = new WireInfo();
            localWireInfo.setSrvrTid(str2);
            localWireInfo.setEventId(str3);
            localWireInfo.setEventSequence(paramInt);
            localWireInfo.setPossibleDuplicate(paramBoolean);
            localWireInfo.setDbTransKey(str4);
            localWireInfo.setFiID(paramEventInfoArray._array[i].FIId);
            localWireInfo.setWireType(paramEventInfoArray._array[i].InstructionType);
            localWireInfo = Wire.getWireInfo(paramFFSConnectionHolder, localWireInfo);
            Object localObject;
            if (localWireInfo == null)
            {
              localObject = "RecWireApprovalHandler.processEvents:  Failed: could not find WireInfo for srvrTID: " + str2 + " in BPW_WireInfo";
              FFSDebug.log((String)localObject, 0);
            }
            else
            {
              localWireInfo = Wire.populateCustomerAndFIInfo(paramFFSConnectionHolder, localWireInfo);
              localObject = ScheduleInfo.getScheduleInfo(paramEventInfoArray._array[i].FIId, "RECWIREAPPROVALTRN", str2, paramFFSConnectionHolder);
              if ((localObject != null) && (((ScheduleInfo)localObject).StatusOption != 1))
              {
                String str5 = localWireInfo.getPrcStatus();
                if (!"WILLPROCESSON".equalsIgnoreCase(str5)) {
                  if ((!"NOFUNDS".equalsIgnoreCase(str5)) && (!"NOFUNDSON_NOTIF".equalsIgnoreCase(str5)))
                  {
                    ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECWIREAPPROVALTRN", str2);
                    if (this.i3 >= 4) {
                      jdMethod_do(paramFFSConnectionHolder, localWireInfo, "The RECWIREAPPROVALTRN schedule has been canceled for this wire transfer.");
                    }
                  }
                  else
                  {
                    ScheduleInfo.moveNextInstanceDate((ScheduleInfo)localObject, -1);
                    ScheduleInfo localScheduleInfo = a(((ScheduleInfo)localObject).NextInstanceDate, ((ScheduleInfo)localObject).LogID, localWireInfo.getFiID());
                    String str6 = ScheduleInfo.createSchedule(paramFFSConnectionHolder, "WIREAPPROVALTRN", str2, localScheduleInfo);
                    if (this.i3 >= 4) {
                      jdMethod_do(paramFFSConnectionHolder, localWireInfo, "A new WIREAPPROVALTRN schedule has been created for this wire transfer.");
                    }
                  }
                }
              }
            }
          }
        }
        if ((paramInt == 2) || (paramInt == 3) || (paramInt != 4)) {}
      }
      String str1;
      FFSDebug.log("RecWireApprovalHandler.processEvents: ", " end", 6);
    }
    catch (Throwable localThrowable)
    {
      str1 = "RecWireApprovalHandler.processEvents:  Faield. error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str1, 0);
      throw new FFSException(str1);
    }
  }
  
  private ScheduleInfo a(int paramInt, String paramString1, String paramString2)
    throws OFXException, Exception
  {
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramString2;
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = 10;
    localScheduleInfo.StartDate = paramInt;
    localScheduleInfo.NextInstanceDate = paramInt;
    localScheduleInfo.InstanceCount = 1;
    localScheduleInfo.LogID = paramString1;
    localScheduleInfo.CurInstanceNum = 1;
    return localScheduleInfo;
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, WireInfo paramWireInfo, String paramString)
    throws Exception
  {
    String str1 = "RecWireApprovalHandler.doAuditLogging";
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
      localAuditLogRecord = new AuditLogRecord(paramWireInfo.getUserId(), null, null, str2, paramWireInfo.getExtId(), 4014, i, new BigDecimal(str6), paramWireInfo.getOrigCurrency(), paramWireInfo.getSrvrTid(), paramWireInfo.getPrcStatus(), str3, str4, str5, paramWireInfo.getFromBankId(), -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (Exception localException)
    {
      String str7 = str1 + " failed " + localException.toString();
      FFSDebug.log(str7 + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str7);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.RecWireApprovalHandler
 * JD-Core Version:    0.7.0.1
 */