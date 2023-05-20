package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.ExternalTransferAccount;
import com.ffusion.ffs.bpw.db.Transfer;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
import com.ffusion.ffs.bpw.interfaces.TransferCalloutHandlerBase;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.master.LimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
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
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;

public class RecExternalTransferHandler
  implements DBConsts, FFSConst, ScheduleConstants, BPWResource
{
  private PropertyConfig h7 = null;
  private int h6 = 0;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    FFSDebug.log(" RecExternalTransferHandler.eventHander: ", " begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    boolean bool = false;
    a(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool);
    FFSDebug.log(" RecExternalTransferHandler.eventHander: ", " end", 6);
  }
  
  private void a(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log(" RecExternalTransferHandler.processEvent: ", " begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length, 6);
    Object localObject1 = null;
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    TransferInfo localTransferInfo = null;
    String str1 = "Transfer action aborted during begin callout.";
    try
    {
      str1 = BPWLocaleUtil.getMessage(21510, new String[] { "Add" }, "TRANSFER_MESSAGE");
    }
    catch (Exception localException1) {}
    try
    {
      if (paramInt != 0) {
        if (paramInt == 1)
        {
          for (int i = 0; i < paramEventInfoArray._array.length; i++)
          {
            str2 = paramEventInfoArray._array[i].InstructionID;
            FFSDebug.log(" RecExternalTransferHandler.processEvent: ", "processing eventSeq: " + paramInt + ",RecSrvrTid: " + str2, 6);
            ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(paramEventInfoArray._array[i].FIId, "RECETFTRN", str2, paramFFSConnectionHolder);
            Object localObject3;
            if ((localScheduleInfo != null) && (localScheduleInfo.StatusOption != 1))
            {
              FFSDebug.log(" RecExternalTransferHandler.processEvent: ", " sinfo.StatusOption: " + localScheduleInfo.StatusOption, 6);
              Object localObject2;
              if ("Close".equalsIgnoreCase(localScheduleInfo.Status))
              {
                FFSDebug.log(" RecExternalTransferHandler.processEvent: ", "schedule closed ", ",for rectransfer: ", str2, 6);
                localObject2 = new RecTransferInfo();
                ((RecTransferInfo)localObject2).setSrvrTId(str2);
                ((RecTransferInfo)localObject2).setFIId(paramEventInfoArray._array[0].FIId);
                localObject3 = paramEventInfoArray._array[0].InstructionType;
                ((RecTransferInfo)localObject2).setTransferType((String)localObject3);
                ((RecTransferInfo)localObject2).setPrcStatus("POSTEDON");
                localObject1 = localObject2;
                boolean bool2 = true;
                Transfer.updateStatus(paramFFSConnectionHolder, (TransferInfo)localObject2, bool2);
              }
              else
              {
                localObject1 = new TransferInfo();
                ((TransferInfo)localObject1).setSrvrTId(str2);
                localObject1 = Transfer.getTransferInfo(paramFFSConnectionHolder, (TransferInfo)localObject1, true);
                Object localObject4;
                if ((!paramBoolean) && (!LimitCheckApprovalProcessor.checkEntitlementExtTrn((TransferInfo)localObject1, null)))
                {
                  localObject2 = "RecExternalTransferHandler.processEvents failed to process: Entitlement check failed. RecTransferId = " + str2;
                  FFSDebug.log((String)localObject2, 6);
                  localObject3 = new RecTransferInfo();
                  ((RecTransferInfo)localObject3).setSrvrTId(str2);
                  ((RecTransferInfo)localObject3).setFIId(paramEventInfoArray._array[0].FIId);
                  localObject4 = paramEventInfoArray._array[0].InstructionType;
                  ((RecTransferInfo)localObject3).setTransferType((String)localObject4);
                  ((RecTransferInfo)localObject3).setPrcStatus("FAILEDON");
                  boolean bool3 = true;
                  Transfer.updateStatus(paramFFSConnectionHolder, (TransferInfo)localObject3, bool3);
                  ScheduleInfo.delete(paramFFSConnectionHolder, str2, "RECETFTRN");
                  if (this.h6 >= 1)
                  {
                    ((TransferInfo)localObject1).setFIId(((RecTransferInfo)localObject3).getFIId());
                    ((TransferInfo)localObject1).setTransferType(((RecTransferInfo)localObject3).getTransferType());
                    ((TransferInfo)localObject1).setPrcStatus(((RecTransferInfo)localObject3).getPrcStatus());
                    LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizableMessage(1067, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
                    jdMethod_for(paramFFSConnectionHolder, (TransferInfo)localObject1, localLocalizableString);
                  }
                }
                else
                {
                  FFSDebug.log(" RecExternalTransferHandler.processEvent: ", "creating single ", "intance from recurring model: ", str2, 6);
                  localObject2 = BPWUtil.getDateBeanFormat(localScheduleInfo.NextInstanceDate);
                  localObject3 = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, ((TransferInfo)localObject1).getFIId());
                  localObject4 = Customer.getCustomerByID(((TransferInfo)localObject1).getCustomerId(), paramFFSConnectionHolder);
                  localObject1 = null;
                  localObject1 = Transfer.generateSingleTransferFromRecTransfer(paramFFSConnectionHolder, str2, BPWUtil.getDateBeanFormat(localScheduleInfo.NextInstanceDate), (BPWFIInfo)localObject3, (CustomerInfo)localObject4);
                  try
                  {
                    localTransferInfo = (TransferInfo)((TransferInfo)localObject1).clone();
                    localTransferCalloutHandlerBase.begin(localTransferInfo, "AddTransferFromSchedule");
                  }
                  catch (Throwable localThrowable2)
                  {
                    ((TransferInfo)localObject1).setPrcStatus("FAILEDON");
                    Transfer.updateStatus(paramFFSConnectionHolder, (TransferInfo)localObject1, false);
                    if (localTransferInfo.getStatusCode() != 0)
                    {
                      ((TransferInfo)localObject1).setStatusCode(localTransferInfo.getStatusCode());
                      ((TransferInfo)localObject1).setStatusMsg(localTransferInfo.getStatusMsg());
                    }
                    else
                    {
                      ((TransferInfo)localObject1).setStatusCode(21510);
                      ((TransferInfo)localObject1).setStatusMsg(str1);
                    }
                    try
                    {
                      localTransferInfo = (TransferInfo)((TransferInfo)localObject1).clone();
                      localTransferCalloutHandlerBase.failure(localTransferInfo, "AddTransferFromSchedule");
                    }
                    catch (Throwable localThrowable5) {}
                    continue;
                  }
                  try
                  {
                    localScheduleInfo.NextInstanceDate = Transfer.getNextInstanceDateInBPWWarehouse(paramFFSConnectionHolder, (String)localObject2, (BPWFIInfo)localObject3, ((TransferInfo)localObject1).getTransferDest());
                    ScheduleInfo.modifySchedule(paramFFSConnectionHolder, localScheduleInfo.ScheduleID, localScheduleInfo);
                    Object localObject6;
                    if (!paramBoolean)
                    {
                      FFSDebug.log(" RecExternalTransferHandler.processEvent: ", "transferInfo for processExternalTransferAdd" + localObject1, 6);
                      int j = LimitCheckApprovalProcessor.processExternalTransferAdd(paramFFSConnectionHolder, (TransferInfo)localObject1, null);
                      localObject6 = LimitCheckApprovalProcessor.mapToStatus(j);
                      FFSDebug.log(" RecExternalTransferHandler.processEvent: ", "retStatus", (String)localObject6, 6);
                      if (("LIMIT_CHECK_FAILED".equals(localObject6)) || ("LIMIT_REVERT_FAILED".equals(localObject6)) || ("APPROVAL_FAILED".equals(localObject6))) {
                        ((TransferInfo)localObject1).setPrcStatus((String)localObject6);
                      } else if (((TransferInfo)localObject1).getStatusCode() == 1) {
                        ((TransferInfo)localObject1).setPrcStatus("APPROVAL_PENDING");
                      } else {
                        ((TransferInfo)localObject1).setPrcStatus("WILLPROCESSON");
                      }
                    }
                    FFSDebug.log(" RecExternalTransferHandler.processEvent: ", "transferInfo before status update" + localObject1, 6);
                    Transfer.updateStatus(paramFFSConnectionHolder, (TransferInfo)localObject1, false);
                    Object localObject5;
                    if ((localObject1 == null) || (((TransferInfo)localObject1).getStatusCode() != 0))
                    {
                      localObject5 = " RecExternalTransferHandler.processEvent: FAILED: COULD NOT FIND THE RecSrvrTid: " + str2 + " in BPW_RecTransferInfo TABLE";
                      FFSDebug.log("ERRORCODE:21720", (String)localObject5, 0);
                      FFSDebug.log(" RecExternalTransferHandler.processEvent: ", "canceling schedule ", "for recurring model: ", str2, 6);
                      ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECETFTRN", str2);
                      if (this.h6 >= 1)
                      {
                        localObject6 = BPWLocaleUtil.getLocalizableMessage(1068, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
                        jdMethod_for(paramFFSConnectionHolder, (TransferInfo)localObject1, (ILocalizable)localObject6);
                      }
                      try
                      {
                        localTransferInfo = (TransferInfo)((TransferInfo)localObject1).clone();
                        localTransferCalloutHandlerBase.failure(localTransferInfo, "AddTransferFromSchedule");
                      }
                      catch (Throwable localThrowable6) {}
                    }
                    else
                    {
                      ((TransferInfo)localObject1).setLastProcessId("BPTW");
                      if (this.h6 >= 4)
                      {
                        localObject5 = null;
                        if ("Close".equalsIgnoreCase(localScheduleInfo.Status))
                        {
                          try
                          {
                            localObject5 = BPWLocaleUtil.getLocalizableMessage(1023, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
                          }
                          catch (Exception localException2) {}
                          jdMethod_do(paramFFSConnectionHolder, (TransferInfo)localObject1, (ILocalizable)localObject5);
                        }
                        else
                        {
                          try
                          {
                            localObject5 = BPWLocaleUtil.getLocalizableMessage(1022, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
                          }
                          catch (Exception localException3) {}
                          jdMethod_do(paramFFSConnectionHolder, (TransferInfo)localObject1, (ILocalizable)localObject5);
                        }
                      }
                      try
                      {
                        localTransferInfo = (TransferInfo)((TransferInfo)localObject1).clone();
                        localTransferCalloutHandlerBase.end(localTransferInfo, "AddTransferFromSchedule");
                      }
                      catch (Throwable localThrowable3) {}
                    }
                  }
                  catch (Throwable localThrowable4)
                  {
                    try
                    {
                      localTransferInfo = (TransferInfo)((TransferInfo)localObject1).clone();
                      localTransferCalloutHandlerBase.failure(localTransferInfo, "AddTransferFromSchedule");
                    }
                    catch (Throwable localThrowable7) {}
                    throw localThrowable4;
                  }
                }
              }
            }
            else
            {
              FFSDebug.log(" RecExternalTransferHandler.processEvent: ", "no schedule found for the ", "recurringrecurring model: " + str2, ". This model will be closed", 6);
              boolean bool1 = true;
              localObject3 = new RecTransferInfo();
              ((RecTransferInfo)localObject3).setSrvrTId(str2);
              ((RecTransferInfo)localObject3).setFIId(paramEventInfoArray._array[0].FIId);
              ((RecTransferInfo)localObject3).setTransferType(paramEventInfoArray._array[0].InstructionType);
              ((RecTransferInfo)localObject3).setPrcStatus("POSTEDON");
              localObject1 = localObject3;
              Transfer.updateStatus(paramFFSConnectionHolder, (TransferInfo)localObject3, bool1);
            }
          }
          FFSDebug.log(" RecExternalTransferHandler.processEvent: ", "batch processing completed", 6);
        }
        else if (paramInt == 2)
        {
          FFSDebug.log(" RecExternalTransferHandler.processEvent: ", "schedule processing completed", "eventSequence: " + paramInt, 6);
        }
      }
    }
    catch (Throwable localThrowable1)
    {
      String str2 = " RecExternalTransferHandler.processEvent: Failed. Error: " + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str2, 0);
      if (this.h6 >= 1) {
        jdMethod_for(paramFFSConnectionHolder, (TransferInfo)localObject1, null);
      }
      throw new FFSException(localThrowable1, str2);
    }
    FFSDebug.log(" RecExternalTransferHandler.processEvent: ", " end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log(" RecExternalTransferHandler.resubmitEventHandler: begin, eventSeq: " + paramInt + ",length: " + paramEventInfoArray._array.length + ",instructionType: " + paramEventInfoArray._array[0].InstructionType);
    boolean bool = true;
    a(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool);
    FFSDebug.log(" RecExternalTransferHandler.resubmitEventHandler: end");
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, ILocalizable paramILocalizable)
    throws Exception
  {
    String str1 = "RecExternalTransferHandler.doAuditLogging";
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    int i = 0;
    AuditLogRecord localAuditLogRecord = null;
    FFSDebug.log(str1, "start", 6);
    FFSDebug.log(str1, "transferInfo:", paramTransferInfo.toString(), 6);
    if (paramTransferInfo == null) {
      return;
    }
    try
    {
      str6 = paramTransferInfo.getAmount();
      if ((str6 == null) || (str6.trim().length() == 0)) {
        str6 = "-1";
      }
      ExtTransferAcctInfo localExtTransferAcctInfo = new ExtTransferAcctInfo();
      str8 = null;
      if ("ITOE".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
        str8 = paramTransferInfo.getAccountToId();
      } else if ("ETOI".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
        str8 = paramTransferInfo.getAccountFromId();
      }
      localExtTransferAcctInfo.setAcctId(str8);
      localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo, false, true);
      if (localExtTransferAcctInfo.getStatusCode() == 0)
      {
        str2 = localExtTransferAcctInfo.buildAcctId();
        str3 = localExtTransferAcctInfo.getAcctBankRtn();
      }
      else
      {
        str2 = paramTransferInfo.buildToAcctId();
      }
      str5 = paramTransferInfo.getBankFromRtn();
      str4 = paramTransferInfo.buildFromAcctId();
      if ((paramTransferInfo.getCustomerId().equals(paramTransferInfo.getSubmittedBy())) || (paramTransferInfo.getCustomerId().equals(paramTransferInfo.getSubmittedBy())))
      {
        i = 0;
        FFSDebug.log("logActivity: Consumer", 6);
      }
      else
      {
        i = Integer.parseInt(paramTransferInfo.getCustomerId());
        FFSDebug.log("logActivity: Business", 6);
      }
      str7 = paramTransferInfo.getSubmittedBy();
      localAuditLogRecord = new AuditLogRecord(str7, null, null, paramILocalizable, paramTransferInfo.getLogId(), 5250, i, new BigDecimal(str6), paramTransferInfo.getAmountCurrency(), paramTransferInfo.getSrvrTId(), paramTransferInfo.getPrcStatus(), str2, str3, str4, str5, 0);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (Exception localException)
    {
      String str8 = str1 + " failed " + localException.toString();
      FFSDebug.log(str8 + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str8);
    }
  }
  
  private void jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, ILocalizable paramILocalizable)
  {
    String str1 = "FailedRecExternalTransferHandler.logError";
    try
    {
      if (paramILocalizable == null) {
        paramILocalizable = BPWLocaleUtil.getLocalizableMessage(1066, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      }
      jdMethod_do(paramFFSConnectionHolder, paramTransferInfo, paramILocalizable);
    }
    catch (Exception localException)
    {
      String str2 = str1 + " failed " + localException.toString();
      FFSDebug.log(str2 + FFSDebug.stackTrace(localException), 0);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.RecExternalTransferHandler
 * JD-Core Version:    0.7.0.1
 */