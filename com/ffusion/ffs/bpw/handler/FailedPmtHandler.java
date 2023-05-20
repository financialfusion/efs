package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.FailedPayment;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
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
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.beans.LocalizableString;
import java.math.BigDecimal;
import java.util.ArrayList;

public class FailedPmtHandler
  implements DBConsts, FFSConst, BPWResource
{
  private boolean it;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder, false);
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws Exception
  {
    FFSDebug.log("=== FailedPmtHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      this.it = false;
    } else if (paramInt == 1) {
      this.it = true;
    } else if (paramInt == 2)
    {
      if ((this.it) || (paramBoolean))
      {
        String str1 = paramEventInfoArray._array[0].FIId;
        String str2 = paramEventInfoArray._array[0].InstructionType;
        FailedPayment localFailedPayment = new FailedPayment();
        a(paramFFSConnectionHolder, str1, str2, 12, localFailedPayment);
        a(paramFFSConnectionHolder, str1, str2, 11, localFailedPayment);
        a(paramFFSConnectionHolder, str1, str2, 13, localFailedPayment);
        a(paramFFSConnectionHolder, str1, str2, 14, localFailedPayment);
        a(paramFFSConnectionHolder, str1, str2, 15, localFailedPayment);
        a(paramFFSConnectionHolder, str1, str2, 16, localFailedPayment);
        a(paramFFSConnectionHolder, str1, str2, "FUNDSREVERTED", localFailedPayment);
        a(paramFFSConnectionHolder, str1, str2, "FUNDSREVERTFAILED", localFailedPayment);
        a(paramFFSConnectionHolder, str1, str2, "NOFUNDSON", localFailedPayment);
        a(paramFFSConnectionHolder, str1, str2, "FUNDSFAILEDON", localFailedPayment);
        a(paramFFSConnectionHolder, str1, str2, "LIMIT_REVERT_FAILED", localFailedPayment);
        a(paramFFSConnectionHolder, str1, str2, "LIMIT_CHECK_FAILED", localFailedPayment);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("=== FailedPmtHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== FailedPmtHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType, 6);
    if (paramInt == 0)
    {
      this.it = false;
    }
    else if (paramInt == 1)
    {
      this.it = true;
    }
    else if (paramInt == 2)
    {
      int i = paramEventInfoArray._array[0].ScheduleFlag;
      if (i == -1)
      {
        if (this.it)
        {
          String str1 = paramEventInfoArray._array[0].FIId;
          String str2 = paramEventInfoArray._array[0].InstructionType;
          String str3 = paramEventInfoArray._array[0].InstructionID;
          FailedPayment localFailedPayment = new FailedPayment();
          a(paramFFSConnectionHolder, str1, str2, 12, str3, localFailedPayment);
          a(paramFFSConnectionHolder, str1, str2, 11, str3, localFailedPayment);
          a(paramFFSConnectionHolder, str1, str2, 13, str3, localFailedPayment);
          a(paramFFSConnectionHolder, str1, str2, 14, str3, localFailedPayment);
          a(paramFFSConnectionHolder, str1, str2, 15, str3, localFailedPayment);
          a(paramFFSConnectionHolder, str1, str2, 16, str3, localFailedPayment);
        }
      }
      else {
        eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder, true);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("=== FailedPmtHandler.resubmitEventHandler: end", 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, FailedPayment paramFailedPayment)
    throws Exception
  {
    try
    {
      int i = 12;
      if (paramString3.equals("FUNDSFAILEDON")) {
        i = 12;
      } else if (paramString3.equals("NOFUNDSON")) {
        i = 11;
      } else if (paramString3.equals("FUNDSREVERTED")) {
        i = 13;
      } else if (paramString3.equals("FUNDSREVERTFAILED")) {
        i = 14;
      } else if (paramString3.equals("LIMIT_REVERT_FAILED")) {
        i = 15;
      } else if (paramString3.equals("LIMIT_CHECK_FAILED")) {
        i = 16;
      }
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      LocalizableString localLocalizableString = null;
      Object[] arrayOfObject = new Object[1];
      int j = 4455;
      if (localPropertyConfig.LogLevel >= 4)
      {
        if (paramString2.equals("CHECKFREE_PMTTRN"))
        {
          arrayOfObject[0] = BPWLocaleUtil.getLocalizableMessage(200, null, "BILLPAY_AUDITLOG_MESSAGE");
          j = 4456;
        }
        else if (paramString2.equals("METAVANTE_PMTTRN"))
        {
          arrayOfObject[0] = BPWLocaleUtil.getLocalizableMessage(201, null, "BILLPAY_AUDITLOG_MESSAGE");
          j = 4458;
        }
        else if (paramString2.equals("ON_US_PMTTRN"))
        {
          arrayOfObject[0] = BPWLocaleUtil.getLocalizableMessage(202, null, "BILLPAY_AUDITLOG_MESSAGE");
          j = 4460;
        }
        else if (paramString2.equals("ORCC_PMTTRN"))
        {
          arrayOfObject[0] = BPWLocaleUtil.getLocalizableMessage(203, null, "BILLPAY_AUDITLOG_MESSAGE");
          j = 4462;
        }
        else if (paramString2.equals("RPPS_PMTTRN"))
        {
          arrayOfObject[0] = BPWLocaleUtil.getLocalizableMessage(204, null, "BILLPAY_AUDITLOG_MESSAGE");
          j = 4464;
        }
        else if (paramString2.equals("BANKSIM_PMTTRN"))
        {
          arrayOfObject[0] = BPWLocaleUtil.getLocalizableMessage(205, null, "BILLPAY_AUDITLOG_MESSAGE");
          j = 4466;
        }
        else if (paramString2.equals("SAMPLE_PMTTRN"))
        {
          arrayOfObject[0] = BPWLocaleUtil.getLocalizableMessage(206, null, "BILLPAY_AUDITLOG_MESSAGE");
          j = 4468;
        }
        else
        {
          arrayOfObject[0] = paramString2;
        }
        localLocalizableString = BPWLocaleUtil.getLocalizableMessage(824, arrayOfObject, "BILLPAY_AUDITLOG_MESSAGE");
      }
      int k = localPropertyConfig.getBatchSize();
      PmtInfo[] arrayOfPmtInfo = PmtInstruction.getPmtBatchByStatus(paramString1, paramString3, k, paramFFSConnectionHolder);
      int m = arrayOfPmtInfo.length;
      while (m != 0)
      {
        ArrayList localArrayList = new ArrayList();
        for (int n = 0; n < m; n++)
        {
          PmtInstruction.updateStatus(paramFFSConnectionHolder, arrayOfPmtInfo[n].SrvrTID, paramString3 + "_NOTIF");
          String str1 = EventInfo.createEvent(paramFFSConnectionHolder, arrayOfPmtInfo[n].SrvrTID, paramString1, paramString2, "Submitted", i, null);
          localArrayList.add(str1);
          if (localPropertyConfig.LogLevel >= 4)
          {
            int i1 = Integer.parseInt(arrayOfPmtInfo[n].CustomerID);
            BigDecimal localBigDecimal = FFSUtil.getBigDecimal(arrayOfPmtInfo[n].getAmt());
            String str2 = BPWUtil.getAccountIDWithAccountType(arrayOfPmtInfo[n].AcctDebitID, arrayOfPmtInfo[n].AcctDebitType);
            TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), arrayOfPmtInfo[n].submittedBy, null, null, localLocalizableString, arrayOfPmtInfo[n].LogID, j, i1, localBigDecimal, arrayOfPmtInfo[n].CurDef, arrayOfPmtInfo[n].SrvrTID, paramString3 + "_NOTIF", arrayOfPmtInfo[n].PayAcct, null, str2, null, 0);
          }
        }
        paramFFSConnectionHolder.conn.commit();
        paramFailedPayment.processFailedPayments(arrayOfPmtInfo);
        for (n = 0; n < m; n++) {
          EventInfoLog.createEventInfoLog(paramFFSConnectionHolder, (String)localArrayList.get(n), arrayOfPmtInfo[n].SrvrTID, paramString1, paramString2, i, null);
        }
        paramFFSConnectionHolder.conn.commit();
        if (PmtInstruction.isStatusBatchDone(paramString1, paramString3))
        {
          m = 0;
        }
        else
        {
          arrayOfPmtInfo = PmtInstruction.getPmtBatchByStatus(paramString1, paramString3, k, paramFFSConnectionHolder);
          m = arrayOfPmtInfo.length;
        }
      }
    }
    finally
    {
      PmtInstruction.clearStatusBatch(paramString1, paramString3);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt, FailedPayment paramFailedPayment)
    throws Exception
  {
    try
    {
      EventInfo[] arrayOfEventInfo = null;
      arrayOfEventInfo = EventInfo.retrieveEventInfo(paramFFSConnectionHolder, "Submitted", paramString1, paramString2, paramInt);
      if (arrayOfEventInfo != null)
      {
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
              PmtInstruction localPmtInstruction = PmtInstruction.getPmtInstr((String)localObject1, paramFFSConnectionHolder);
              Object localObject2;
              if (localPmtInstruction == null)
              {
                localObject2 = "*** FailedPmtHandler.recoverFromCrash failed: could not find the SrvrTID=" + (String)localObject1 + " in BPW_PmtInstruction";
                FFSDebug.console((String)localObject2);
                FFSDebug.log((String)localObject2);
              }
              else
              {
                localObject2 = localPmtInstruction.getPmtInfo();
                if (arrayOfEventInfo[m].ScheduleFlag == 12) {
                  ((PmtInfo)localObject2).Status = "FUNDSFAILEDON";
                } else if (arrayOfEventInfo[m].ScheduleFlag == 11) {
                  ((PmtInfo)localObject2).Status = "NOFUNDSON";
                } else if (arrayOfEventInfo[m].ScheduleFlag == 13) {
                  ((PmtInfo)localObject2).Status = "FUNDSREVERTED";
                } else if (arrayOfEventInfo[m].ScheduleFlag == 14) {
                  ((PmtInfo)localObject2).Status = "FUNDSREVERTFAILED";
                } else if (arrayOfEventInfo[m].ScheduleFlag == 15) {
                  ((PmtInfo)localObject2).Status = "LIMIT_REVERT_FAILED";
                } else if (arrayOfEventInfo[m].ScheduleFlag == 16) {
                  ((PmtInfo)localObject2).Status = "LIMIT_CHECK_FAILED";
                }
                localArrayList.add(localObject2);
                m++;
              }
            }
            localObject1 = (PmtInfo[])localArrayList.toArray(new PmtInfo[localArrayList.size()]);
            paramFailedPayment.processFailedPayments((PmtInfo[])localObject1);
            paramFFSConnectionHolder.conn.commit();
          }
          if (EventInfo.isBatchDone(paramString1, paramString2)) {
            arrayOfEventInfo = new EventInfo[0];
          } else {
            arrayOfEventInfo = EventInfo.retrieveEventInfo(paramFFSConnectionHolder, "Submitted", paramString1, paramString2, paramInt);
          }
        }
      }
    }
    finally
    {
      EventInfo.clearBatch(paramString1, paramString2);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt, String paramString3, FailedPayment paramFailedPayment)
    throws Exception
  {
    try
    {
      EventInfo[] arrayOfEventInfo = null;
      arrayOfEventInfo = EventInfoLog.retrieveEventInfoLogs(paramFFSConnectionHolder, paramInt, paramString1, paramString2, paramString3);
      if (arrayOfEventInfo != null) {
        while ((arrayOfEventInfo != null) && (arrayOfEventInfo.length > 0))
        {
          ArrayList localArrayList = new ArrayList();
          int i = arrayOfEventInfo.length;
          int j = 0;
          while (j < i)
          {
            localObject1 = arrayOfEventInfo[j].InstructionID;
            PmtInstruction localPmtInstruction = PmtInstruction.getPmtInstr((String)localObject1, paramFFSConnectionHolder);
            Object localObject2;
            if (localPmtInstruction == null)
            {
              localObject2 = "*** FailedPmtHandler.resubmitFailPmts failed: could not find the SrvrTID=" + (String)localObject1 + " in BPW_PmtInstruction";
              FFSDebug.console((String)localObject2);
              FFSDebug.log((String)localObject2);
            }
            else
            {
              localObject2 = localPmtInstruction.getPmtInfo();
              if (arrayOfEventInfo[j].ScheduleFlag == 12) {
                ((PmtInfo)localObject2).Status = "FUNDSFAILEDON";
              } else if (arrayOfEventInfo[j].ScheduleFlag == 11) {
                ((PmtInfo)localObject2).Status = "NOFUNDSON";
              } else if (arrayOfEventInfo[j].ScheduleFlag == 13) {
                ((PmtInfo)localObject2).Status = "FUNDSREVERTED";
              } else if (arrayOfEventInfo[j].ScheduleFlag == 14) {
                ((PmtInfo)localObject2).Status = "FUNDSREVERTFAILED";
              } else if (arrayOfEventInfo[j].ScheduleFlag == 15) {
                ((PmtInfo)localObject2).Status = "LIMIT_REVERT_FAILED";
              } else if (arrayOfEventInfo[j].ScheduleFlag == 16) {
                ((PmtInfo)localObject2).Status = "LIMIT_CHECK_FAILED";
              }
              localArrayList.add(localObject2);
              j++;
            }
          }
          Object localObject1 = (PmtInfo[])localArrayList.toArray(new PmtInfo[localArrayList.size()]);
          paramFailedPayment.processFailedPayments((PmtInfo[])localObject1);
          paramFFSConnectionHolder.conn.commit();
          if (EventInfoLog.isBatchDone(paramString1, paramString2)) {
            arrayOfEventInfo = new EventInfo[0];
          } else {
            arrayOfEventInfo = EventInfoLog.retrieveEventInfoLogs(paramFFSConnectionHolder, paramInt, paramString1, paramString2, paramString3);
          }
        }
      }
    }
    finally
    {
      EventInfoLog.clearBatch(paramString1, paramString2);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.FailedPmtHandler
 * JD-Core Version:    0.7.0.1
 */