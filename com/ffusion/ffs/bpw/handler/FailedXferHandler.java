package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.FailedTransfer;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.beans.LocalizableString;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class FailedXferHandler
  implements DBConsts, FFSConst, BPWResource
{
  private boolean hW;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder, false);
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws Exception
  {
    FFSDebug.log("=== FailedXferHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      this.hW = false;
    } else if (paramInt == 1) {
      this.hW = true;
    } else if (paramInt == 2)
    {
      if ((this.hW) || (paramBoolean))
      {
        String str1 = paramEventInfoArray._array[0].FIId;
        String str2 = paramEventInfoArray._array[0].InstructionType;
        FailedTransfer localFailedTransfer = new FailedTransfer();
        a(paramFFSConnectionHolder, str1, str2, 10, localFailedTransfer);
        a(paramFFSConnectionHolder, str1, str2, 12, localFailedTransfer);
        a(paramFFSConnectionHolder, str1, str2, 11, localFailedTransfer);
        a(paramFFSConnectionHolder, str1, str2, 15, localFailedTransfer);
        a(paramFFSConnectionHolder, str1, str2, 16, localFailedTransfer);
        a(paramFFSConnectionHolder, str1, str2, "FAILEDON", localFailedTransfer);
        a(paramFFSConnectionHolder, str1, str2, "NOFUNDSON", localFailedTransfer);
        a(paramFFSConnectionHolder, str1, str2, "FUNDSFAILEDON", localFailedTransfer);
        a(paramFFSConnectionHolder, str1, str2, "LIMIT_REVERT_FAILED", localFailedTransfer);
        a(paramFFSConnectionHolder, str1, str2, "LIMIT_CHECK_FAILED", localFailedTransfer);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("=== FailedXferHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== FailedXferHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType, 6);
    if (paramInt == 0)
    {
      this.hW = false;
    }
    else if (paramInt == 1)
    {
      this.hW = true;
    }
    else if (paramInt == 2)
    {
      int i = paramEventInfoArray._array[0].ScheduleFlag;
      if (i == -1)
      {
        if (this.hW)
        {
          String str1 = paramEventInfoArray._array[0].FIId;
          String str2 = paramEventInfoArray._array[0].InstructionType;
          String str3 = paramEventInfoArray._array[0].InstructionID;
          FailedTransfer localFailedTransfer = new FailedTransfer();
          a(paramFFSConnectionHolder, str1, str2, 10, str3, localFailedTransfer);
          a(paramFFSConnectionHolder, str1, str2, 12, str3, localFailedTransfer);
          a(paramFFSConnectionHolder, str1, str2, 11, str3, localFailedTransfer);
          a(paramFFSConnectionHolder, str1, str2, 15, str3, localFailedTransfer);
          a(paramFFSConnectionHolder, str1, str2, 16, str3, localFailedTransfer);
        }
      }
      else {
        eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder, true);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("=== FailedXferHandler.resubmitEventHandler: end", 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, FailedTransfer paramFailedTransfer)
    throws Exception
  {
    try
    {
      int i = 12;
      if (paramString3.equals("FUNDSFAILEDON")) {
        i = 12;
      } else if (paramString3.equals("NOFUNDSON")) {
        i = 11;
      } else if (paramString3.equals("FAILEDON")) {
        i = 10;
      } else if (paramString3.equals("LIMIT_REVERT_FAILED")) {
        i = 15;
      } else if (paramString3.equals("LIMIT_CHECK_FAILED")) {
        i = 16;
      }
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int j = localPropertyConfig.getBatchSize();
      IntraTrnInfo[] arrayOfIntraTrnInfo = XferInstruction.getXferBatchByStatus(paramString1, paramString3, j, paramFFSConnectionHolder);
      int k = arrayOfIntraTrnInfo.length;
      while (k != 0)
      {
        ArrayList localArrayList = new ArrayList();
        for (int m = 0; m < k; m++)
        {
          XferInstruction.updateStatus(paramFFSConnectionHolder, arrayOfIntraTrnInfo[m].srvrTid, paramString3 + "_NOTIF");
          String str1 = EventInfo.createEvent(paramFFSConnectionHolder, arrayOfIntraTrnInfo[m].srvrTid, paramString1, paramString2, "Submitted", i, null);
          localArrayList.add(str1);
          if (localPropertyConfig.LogLevel >= 4)
          {
            int n = Integer.parseInt(arrayOfIntraTrnInfo[m].customerId);
            BigDecimal localBigDecimal = new BigDecimal(arrayOfIntraTrnInfo[m].amount);
            String str2 = BPWUtil.getAccountIDWithAccountType(arrayOfIntraTrnInfo[m].acctIdFrom, arrayOfIntraTrnInfo[m].acctTypeFrom);
            String str3 = BPWUtil.getAccountIDWithAccountType(arrayOfIntraTrnInfo[m].acctIdTo, arrayOfIntraTrnInfo[m].acctTypeTo);
            String str4 = BPWLocaleUtil.getMessage(925, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
            String[] arrayOfString = { str4 };
            LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BOOKTRANSFER_AUDITLOG_MESSAGE");
            TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), arrayOfIntraTrnInfo[m].submittedBy, null, null, localLocalizableString, arrayOfIntraTrnInfo[m].logId, 4650, n, localBigDecimal, arrayOfIntraTrnInfo[m].curDef, arrayOfIntraTrnInfo[m].srvrTid, paramString3 + "_NOTIF", str3, arrayOfIntraTrnInfo[m].bankId, str2, arrayOfIntraTrnInfo[m].fromBankId, 0);
          }
        }
        paramFFSConnectionHolder.conn.commit();
        paramFailedTransfer.processFailedTransfers(arrayOfIntraTrnInfo);
        for (m = 0; m < k; m++) {
          EventInfoLog.createEventInfoLog(paramFFSConnectionHolder, (String)localArrayList.get(m), arrayOfIntraTrnInfo[m].srvrTid, paramString1, paramString2, i, null);
        }
        paramFFSConnectionHolder.conn.commit();
        if (XferInstruction.isStatusBatchDone(paramString1, paramString3))
        {
          k = 0;
        }
        else
        {
          arrayOfIntraTrnInfo = XferInstruction.getXferBatchByStatus(paramString1, paramString3, j, paramFFSConnectionHolder);
          k = arrayOfIntraTrnInfo.length;
        }
      }
    }
    finally
    {
      XferInstruction.clearStatusBatch(paramString1, paramString3);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt, FailedTransfer paramFailedTransfer)
    throws Exception
  {
    try
    {
      EventInfo[] arrayOfEventInfo = null;
      arrayOfEventInfo = EventInfo.retrieveEventInfo(paramFFSConnectionHolder, "Submitted", paramString1, paramString2, paramInt);
      if (arrayOfEventInfo != null)
      {
        String str = DBConnCache.save(paramFFSConnectionHolder);
        InstructionType localInstructionType = BPWRegistryUtil.getInstructionType(paramString1, paramString2);
        int i = localInstructionType.FileBasedRecovery;
        while ((arrayOfEventInfo != null) && (arrayOfEventInfo.length > 0))
        {
          int j = 1;
          ArrayList localArrayList = new ArrayList();
          int k = arrayOfEventInfo.length;
          int m = 0;
          Object localObject1;
          if (i == 0)
          {
            localObject1 = EventInfoLog.getByEventID(paramFFSConnectionHolder, arrayOfEventInfo[(k - 1)].EventID);
            if (localObject1 != null) {
              j = 0;
            }
          }
          if (j != 0)
          {
            while (m < k)
            {
              EventInfoLog.updateEventInfoLog(paramFFSConnectionHolder, arrayOfEventInfo[m].EventID, arrayOfEventInfo[m].InstructionID, arrayOfEventInfo[m].FIId, arrayOfEventInfo[m].InstructionType, arrayOfEventInfo[m].ScheduleFlag, arrayOfEventInfo[m].LogID);
              localObject1 = arrayOfEventInfo[m].InstructionID;
              XferInstruction localXferInstruction = XferInstruction.getXferInstruction(paramFFSConnectionHolder, (String)localObject1);
              Object localObject2;
              if (localXferInstruction == null)
              {
                localObject2 = "*** FailedXferHandler.recoverFromCrash failed: could not find the SrvrTID=" + (String)localObject1 + " in BPW_XferInstruction";
                FFSDebug.console((String)localObject2);
                FFSDebug.log((String)localObject2);
              }
              else
              {
                localObject2 = new IntraTrnInfo(localXferInstruction.CustomerID, localXferInstruction.BankID, localXferInstruction.BranchID, localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType, localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType, localXferInstruction.Amount, localXferInstruction.CurDef, localXferInstruction.DateToPost, (String)localObject1, localXferInstruction.LogID, arrayOfEventInfo[m].EventID, 1, true, str, localXferInstruction.RecSrvrTID, localXferInstruction.Status, localXferInstruction.FIID, localXferInstruction.fromBankID, localXferInstruction.fromBranchID);
                if (localXferInstruction.extraFields != null) {
                  ((IntraTrnInfo)localObject2).extraFields = ((HashMap)localXferInstruction.extraFields);
                }
                if (arrayOfEventInfo[m].ScheduleFlag == 12) {
                  ((IntraTrnInfo)localObject2).status = "FUNDSFAILEDON";
                } else if (arrayOfEventInfo[m].ScheduleFlag == 11) {
                  ((IntraTrnInfo)localObject2).status = "NOFUNDSON";
                } else if (arrayOfEventInfo[m].ScheduleFlag == 10) {
                  ((IntraTrnInfo)localObject2).status = "FAILEDON";
                } else if (arrayOfEventInfo[m].ScheduleFlag == 15) {
                  ((IntraTrnInfo)localObject2).status = "LIMIT_REVERT_FAILED";
                } else if (arrayOfEventInfo[m].ScheduleFlag == 16) {
                  ((IntraTrnInfo)localObject2).status = "LIMIT_CHECK_FAILED";
                }
                localArrayList.add(localObject2);
                m++;
              }
            }
            localObject1 = (IntraTrnInfo[])localArrayList.toArray(new IntraTrnInfo[localArrayList.size()]);
            paramFailedTransfer.processFailedTransfers((IntraTrnInfo[])localObject1);
            paramFFSConnectionHolder.conn.commit();
          }
          if (EventInfo.isBatchDone(paramString1, paramString2)) {
            arrayOfEventInfo = new EventInfo[0];
          } else {
            arrayOfEventInfo = EventInfo.retrieveEventInfo(paramFFSConnectionHolder, "Submitted", paramString1, paramString2, paramInt);
          }
        }
        DBConnCache.unbind(str);
      }
    }
    finally
    {
      EventInfo.clearBatch(paramString1, paramString2);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt, String paramString3, FailedTransfer paramFailedTransfer)
    throws Exception
  {
    try
    {
      EventInfo[] arrayOfEventInfo = null;
      arrayOfEventInfo = EventInfoLog.retrieveEventInfoLogs(paramFFSConnectionHolder, paramInt, paramString1, paramString2, paramString3);
      if (arrayOfEventInfo != null)
      {
        String str = DBConnCache.save(paramFFSConnectionHolder);
        while ((arrayOfEventInfo != null) && (arrayOfEventInfo.length > 0))
        {
          ArrayList localArrayList = new ArrayList();
          int i = arrayOfEventInfo.length;
          int j = 0;
          while (j < i)
          {
            localObject1 = arrayOfEventInfo[j].InstructionID;
            XferInstruction localXferInstruction = XferInstruction.getXferInstruction(paramFFSConnectionHolder, (String)localObject1);
            Object localObject2;
            if (localXferInstruction == null)
            {
              localObject2 = "*** FailedXferHandler.resubmitFailXfers failed: could not find the SrvrTID=" + (String)localObject1 + " in BPW_XferInstruction";
              FFSDebug.console((String)localObject2);
              FFSDebug.log((String)localObject2);
            }
            else
            {
              localObject2 = new IntraTrnInfo(localXferInstruction.CustomerID, localXferInstruction.BankID, localXferInstruction.BranchID, localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType, localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType, localXferInstruction.Amount, localXferInstruction.CurDef, localXferInstruction.DateToPost, (String)localObject1, localXferInstruction.LogID, arrayOfEventInfo[j].EventID, 1, true, str, localXferInstruction.RecSrvrTID, localXferInstruction.Status, localXferInstruction.FIID, localXferInstruction.fromBankID, localXferInstruction.fromBranchID);
              if (localXferInstruction.extraFields != null) {
                ((IntraTrnInfo)localObject2).extraFields = ((HashMap)localXferInstruction.extraFields);
              }
              if (arrayOfEventInfo[j].ScheduleFlag == 12) {
                ((IntraTrnInfo)localObject2).status = "FUNDSFAILEDON";
              } else if (arrayOfEventInfo[j].ScheduleFlag == 11) {
                ((IntraTrnInfo)localObject2).status = "NOFUNDSON";
              } else if (arrayOfEventInfo[j].ScheduleFlag == 10) {
                ((IntraTrnInfo)localObject2).status = "FAILEDON";
              } else if (arrayOfEventInfo[j].ScheduleFlag == 15) {
                ((IntraTrnInfo)localObject2).status = "LIMIT_REVERT_FAILED";
              } else if (arrayOfEventInfo[j].ScheduleFlag == 16) {
                ((IntraTrnInfo)localObject2).status = "LIMIT_CHECK_FAILED";
              }
              localArrayList.add(localObject2);
              j++;
            }
          }
          Object localObject1 = (IntraTrnInfo[])localArrayList.toArray(new IntraTrnInfo[localArrayList.size()]);
          paramFailedTransfer.processFailedTransfers((IntraTrnInfo[])localObject1);
          paramFFSConnectionHolder.conn.commit();
          if (EventInfoLog.isBatchDone(paramString1, paramString2)) {
            arrayOfEventInfo = new EventInfo[0];
          } else {
            arrayOfEventInfo = EventInfoLog.retrieveEventInfoLogs(paramFFSConnectionHolder, paramInt, paramString1, paramString2, paramString3);
          }
        }
        DBConnCache.unbind(str);
      }
    }
    finally
    {
      EventInfoLog.clearBatch(paramString1, paramString2);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.FailedXferHandler
 * JD-Core Version:    0.7.0.1
 */