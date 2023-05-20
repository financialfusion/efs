package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.ACHBatch;
import com.ffusion.ffs.bpw.db.ACHPayee;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo;
import com.ffusion.ffs.bpw.master.LimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.util.HashMap;

public class RecACHBatchHandler
{
  private int a;
  
  public RecACHBatchHandler()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.a = localPropertyConfig.LogLevel;
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder, false);
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("=== RecACHBatchHandler.eventHander: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length, 6);
    try
    {
      String str1;
      if (paramInt != 0)
      {
        if (paramInt == 1) {
          for (int i = 0; i < paramEventInfoArray._array.length; i++)
          {
            str1 = paramEventInfoArray._array[i].InstructionID;
            FFSDebug.log("=== RecACHBatchHandler.eventHander: eventSeq=" + paramInt + ",RecBatchId=" + str1, 6);
            ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(paramEventInfoArray._array[i].FIId, "RECACHBATCHTRN", str1, paramFFSConnectionHolder);
            ACHBatchInfo localACHBatchInfo1 = ACHBatch.getACHBatchById(paramFFSConnectionHolder, str1, true);
            if ((localScheduleInfo != null) && (localScheduleInfo.StatusOption != 1))
            {
              if (localScheduleInfo.Status.compareTo("Close") == 0)
              {
                boolean bool1 = true;
                RecACHBatchInfo localRecACHBatchInfo1 = new RecACHBatchInfo();
                localRecACHBatchInfo1.setBatchId(str1);
                ACHBatch.updateACHBatchStatus(localRecACHBatchInfo1, "POSTEDON", paramFFSConnectionHolder, bool1);
                if (this.a >= 4)
                {
                  localRecACHBatchInfo1 = (RecACHBatchInfo)ACHBatch.getACHBatch(localRecACHBatchInfo1, paramFFSConnectionHolder, bool1, false, false);
                  a(paramFFSConnectionHolder, localRecACHBatchInfo1, "ScheduleInfo for an ACH recurring batch is already closed");
                }
              }
              else
              {
                String str2;
                Object localObject1;
                if ((!paramBoolean) && (!LimitCheckApprovalProcessor.checkEntitlementACHBatch(localACHBatchInfo1, null)))
                {
                  str2 = "RecACHBatchHandler.eventHandler failed to process: Entitlement check failed. RecBatchId= " + str1;
                  FFSDebug.log(str2, 6);
                  boolean bool3 = true;
                  localObject1 = new RecACHBatchInfo();
                  ((RecACHBatchInfo)localObject1).setBatchId(str1);
                  ACHBatch.updateACHBatchStatus((ACHBatchInfo)localObject1, "FAILEDON", paramFFSConnectionHolder, bool3);
                  ScheduleInfo.delete(paramFFSConnectionHolder, str1, "RECACHBATCHTRN");
                  if (this.a >= 4)
                  {
                    localObject1 = (RecACHBatchInfo)ACHBatch.getACHBatch((ACHBatchInfo)localObject1, paramFFSConnectionHolder, bool3, false, false);
                    a(paramFFSConnectionHolder, (ACHBatchInfo)localObject1, "Processing ACH recurring batch failed.  Not entitled to submit an ACH batch.");
                  }
                }
                else
                {
                  str2 = ACHBatch.getEffectiveDateFromBatch(localACHBatchInfo1);
                  int j = Integer.parseInt(str2) * 100;
                  j = ScheduleInfo.computeFutureDate(j, localScheduleInfo.Frequency, localScheduleInfo.CurInstanceNum - 1);
                  j /= 100;
                  j = SmartCalendar.getACHPayday(localACHBatchInfo1.getFiId(), j);
                  str2 = Integer.toString(j);
                  str2 = str2.substring(2);
                  localObject1 = Integer.toString(j);
                  ACHBatchInfo localACHBatchInfo2 = ACHBatch.generateACHBatchFromACHRecBatch(paramFFSConnectionHolder, str1, (String)localObject1, str2);
                  String str3;
                  if ((localACHBatchInfo2 == null) || (localACHBatchInfo2.getStatusCode() != 0))
                  {
                    str3 = "*** RecACHBatchHandler.eventHandler failed: could not find the RecBatchId= " + str1 + " in ACH_RecBatch table";
                    FFSDebug.log(str3, 0);
                    ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECACHBATCHTRN", str1);
                  }
                  else
                  {
                    str3 = BPWUtil.getDateBeanFormat(localScheduleInfo.NextInstanceDate);
                    int k = localACHBatchInfo2.getBatchHeaderFieldValueShort(2);
                    HashMap localHashMap1 = ACHBatch.getInfosForBatch(paramFFSConnectionHolder, localACHBatchInfo2);
                    localScheduleInfo.NextInstanceDate = ACHBatch.getNextInstanceDateInBPWWarehouse(str3, k, localHashMap1);
                    ScheduleInfo.modifySchedule(paramFFSConnectionHolder, localScheduleInfo.ScheduleID, localScheduleInfo);
                    String str4 = "WILLPROCESSON";
                    Object localObject2;
                    if (!paramBoolean)
                    {
                      localObject2 = ACHPayee.getACHPayeeInfoInBatch(paramFFSConnectionHolder, localACHBatchInfo2);
                      HashMap localHashMap2 = new HashMap();
                      localHashMap2.put("ACHPayeeInfos", localObject2);
                      int m = LimitCheckApprovalProcessor.processACHBatchAdd(paramFFSConnectionHolder, localACHBatchInfo2, localHashMap2);
                      str4 = LimitCheckApprovalProcessor.mapToStatus(m);
                      ACHBatch.updateACHBatchStatus(localACHBatchInfo2, str4, paramFFSConnectionHolder, false);
                      a(paramFFSConnectionHolder, localACHBatchInfo2, "Add a next single batch for an ACH recurring batch");
                    }
                    if (str4.compareTo("WILLPROCESSON") == 0)
                    {
                      localObject2 = new ScheduleInfo();
                      ((ScheduleInfo)localObject2).Status = "Active";
                      ((ScheduleInfo)localObject2).Frequency = 10;
                      ((ScheduleInfo)localObject2).StartDate = ACHBatch.getNextInstanceDateForScheduleInfo(str3, k, localHashMap1);
                      ((ScheduleInfo)localObject2).InstanceCount = 1;
                      ((ScheduleInfo)localObject2).LogID = localACHBatchInfo2.getLogId();
                      ((ScheduleInfo)localObject2).FIId = localScheduleInfo.FIId;
                      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "ACHBATCHTRN", localACHBatchInfo2.getBatchId(), (ScheduleInfo)localObject2);
                    }
                    else
                    {
                      FFSDebug.log("=== RecACHBatchHandler.eventHander: eventSeq=" + paramInt + ",status =" + str4, 6);
                    }
                  }
                }
              }
            }
            else
            {
              boolean bool2 = true;
              RecACHBatchInfo localRecACHBatchInfo2 = new RecACHBatchInfo();
              localRecACHBatchInfo2.setBatchId(str1);
              ACHBatch.updateACHBatchStatus(localRecACHBatchInfo2, "POSTEDON", paramFFSConnectionHolder, bool2);
              if (this.a >= 4)
              {
                localRecACHBatchInfo2 = (RecACHBatchInfo)ACHBatch.getACHBatch(localRecACHBatchInfo2, paramFFSConnectionHolder, bool2, false, false);
                a(paramFFSConnectionHolder, localRecACHBatchInfo2, "Complete an ACH recurring batch");
              }
            }
          }
        }
        if (paramInt != 2) {}
      }
      FFSDebug.log("==== RecACHBatchHandler.eventHander: end", 6);
    }
    catch (Exception localException)
    {
      str1 = "*** RecACHBatchHandler.eventHandler failed. Error: " + FFSDebug.stackTrace(localException);
      FFSDebug.log(str1, 0);
      throw new FFSException(localException, str1);
    }
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== RecACHBatchHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType);
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder, true);
    FFSDebug.log("=== RecACHBatchHandler.resubmitEventHandler: end");
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, String paramString)
    throws FFSException
  {
    String str1 = "RecACHBatchHandler.doTransAuditLog:";
    if (this.a >= 4)
    {
      long l = paramACHBatchInfo.getNonOffBatchCreditSum() + paramACHBatchInfo.getNonOffBatchDebitSum();
      BigDecimal localBigDecimal = new BigDecimal(l).movePointLeft(2);
      int i = 0;
      try
      {
        i = Integer.parseInt(paramACHBatchInfo.getCustomerId());
      }
      catch (NumberFormatException localNumberFormatException)
      {
        localObject = str1 + " CustomerId is not an integer - " + paramACHBatchInfo.getCustomerId() + " - " + localNumberFormatException.toString();
        FFSDebug.log((String)localObject + FFSDebug.stackTrace(localNumberFormatException), 0);
        throw new FFSException(localNumberFormatException, (String)localObject);
      }
      String str2 = paramString + ", Batch Category = " + paramACHBatchInfo.getBatchCategory() + ", Batch type = " + paramACHBatchInfo.getBatchType() + ", Batch balanced type= " + paramACHBatchInfo.getBatchBalanceType();
      FFSDebug.log(str1 + str2, 6);
      Object localObject = new AuditLogRecord(paramACHBatchInfo.getSubmittedBy(), null, null, str2, paramACHBatchInfo.getLogId(), 4302, i, localBigDecimal, null, paramACHBatchInfo.getBatchId(), paramACHBatchInfo.getBatchStatus(), null, null, null, null, 0);
      TransAuditLog.logTransAuditLog((AuditLogRecord)localObject, paramFFSConnectionHolder.conn.getConnection());
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.RecACHBatchHandler
 * JD-Core Version:    0.7.0.1
 */