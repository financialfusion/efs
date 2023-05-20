package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.BPWServer;
import com.ffusion.ffs.bpw.achagent.ACHAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.MacGenerator;
import com.ffusion.ffs.bpw.db.ACHBatch;
import com.ffusion.ffs.bpw.db.ACHCompOffsetAcct;
import com.ffusion.ffs.bpw.db.ACHCompany;
import com.ffusion.ffs.bpw.db.ACHFI;
import com.ffusion.ffs.bpw.db.ACHPayee;
import com.ffusion.ffs.bpw.db.ACHRecord;
import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.DBInstructionType;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.RecSrvrTIDToSrvrTID;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.interfaces.ACHBatchHist;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.BankingDays;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
import com.ffusion.ffs.bpw.util.ACHAdapterUtil;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.Scheduler;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.ACHBatchCache;
import com.ffusion.ffs.util.FFSCacheManager;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeACKEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeARCEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeATXEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeBatchControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeBatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCBREntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCCDEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCIEEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCTXEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeDNEEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeENREntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeMTEEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePBREntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePOPEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePOSEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypePPDEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeRCKEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeSHREntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeTELEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeTRCEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeTRXEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeWEBEntryDetailRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeXCKEntryDetailRecord;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

public class ACHBatchProcessor
  implements DBConsts, FFSConst, ACHConsts, BPWResource
{
  private PropertyConfig bC = null;
  private int bA = 1;
  private FFSCacheManager bB = null;
  private PagedACH bz = null;
  
  public ACHBatchInfo addACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    if (paramACHBatchInfo == null)
    {
      paramACHBatchInfo = new ACHBatchInfo();
      paramACHBatchInfo.setStatusCode(16000);
      String str = "ACHBatchInfo object  is null";
      paramACHBatchInfo.setStatusMsg(str);
      FFSDebug.log("ACHBatchProcessor.getACHBatch, " + str, 0);
      return paramACHBatchInfo;
    }
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setBatchType("Current");
    if (paramACHBatchInfo.getStatusCode() != 0) {
      return paramACHBatchInfo;
    }
    jdMethod_if(paramACHBatchInfo, false);
    return paramACHBatchInfo;
  }
  
  public RecACHBatchInfo addRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.addRecACHBatch";
    if (paramRecACHBatchInfo == null)
    {
      paramRecACHBatchInfo = new RecACHBatchInfo();
      paramRecACHBatchInfo.setStatusCode(16000);
      String str2 = "ACHBatchInfo object  is null";
      paramRecACHBatchInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + ", " + str2, 0);
      return paramRecACHBatchInfo;
    }
    paramRecACHBatchInfo.setStatusCode(0);
    paramRecACHBatchInfo.setBatchType("Repetitive");
    jdMethod_if(paramRecACHBatchInfo, true);
    return paramRecACHBatchInfo;
  }
  
  private ACHBatchInfo jdMethod_if(ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.addBatch ";
    FFSDebug.log(str1 + "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      if (paramACHBatchInfo.getBatchCategory() == null)
      {
        paramACHBatchInfo.setStatusCode(16010);
        paramACHBatchInfo.setStatusMsg(" contains null : batchCategory is null!");
        FFSDebug.log(str1 + ": batchCategory is null!", 0);
        localObject1 = paramACHBatchInfo;
        return localObject1;
      }
      if ("ACH_BATCH_PRENOTE".compareTo(paramACHBatchInfo.getBatchCategory()) == 0)
      {
        paramACHBatchInfo.setStatusCode(23500);
        paramACHBatchInfo.setStatusMsg("Failed to create a prenote batch for the ACHPayee");
        FFSDebug.log(str1 + ": Can't create a prenote batch", 0);
        localObject1 = paramACHBatchInfo;
        return localObject1;
      }
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      Object localObject1 = ACHBatch.getInfosForBatch(localFFSConnectionHolder, paramACHBatchInfo);
      if (localObject1 == null)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject2 = paramACHBatchInfo;
        return localObject2;
      }
      a(localFFSConnectionHolder, paramACHBatchInfo, paramBoolean, (HashMap)localObject1);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject2 = paramACHBatchInfo;
        return localObject2;
      }
      paramACHBatchInfo = a(paramACHBatchInfo, localFFSConnectionHolder, paramBoolean, (HashMap)localObject1);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
      }
      else
      {
        a(localFFSConnectionHolder, paramACHBatchInfo, (HashMap)localObject1, null);
        if (paramACHBatchInfo.getStatusCode() != 0) {
          localFFSConnectionHolder.conn.rollback();
        } else {
          localFFSConnectionHolder.conn.commit();
        }
      }
    }
    catch (FFSException localFFSException)
    {
      Object localObject2 = "*** " + str1 + " failed: ";
      String str2 = null;
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      str2 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject2, str2, 0);
      throw localFFSException;
    }
    finally
    {
      if (localFFSConnectionHolder != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log(str1 + "done.", 6);
    return paramACHBatchInfo;
  }
  
  private ACHBatchInfo a(ACHBatchInfo paramACHBatchInfo, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.addBatchTX: ";
    ACHBatchCache localACHBatchCache = null;
    int i = 1;
    int j = 0;
    int k = 0;
    int m = 0;
    FFSDebug.log(str1 + "start", 6);
    if (!a(paramACHBatchInfo))
    {
      FFSDebug.log(str1 + " " + paramACHBatchInfo.getStatusMsg(), 0);
      return paramACHBatchInfo;
    }
    if ((paramACHBatchInfo.getRecords() == null) || (paramACHBatchInfo.getRecords().length == 0))
    {
      paramACHBatchInfo.setStatusCode(22360);
      paramACHBatchInfo.setStatusMsg("ACHBatchInfo object does not contain records");
      return paramACHBatchInfo;
    }
    if ((paramACHBatchInfo.getTotalBatchSize() > 0L) && (paramACHBatchInfo.getBatchPageSize() > 0L) && (paramACHBatchInfo.getTotalBatchSize() > paramACHBatchInfo.getBatchPageSize())) {
      i = 0;
    }
    if (i != 0)
    {
      if (paramACHBatchInfo.getTotalBatchSize() != paramACHBatchInfo.getRecordAddendaCount())
      {
        paramACHBatchInfo.setStatusCode(17140);
        paramACHBatchInfo.setStatusMsg("Total Batch Size does not match total number of record plus addendas in the batch info");
        return paramACHBatchInfo;
      }
    }
    else if (paramACHBatchInfo.getBatchPageSize() != paramACHBatchInfo.getRecordAddendaCount())
    {
      paramACHBatchInfo.setStatusCode(17150);
      paramACHBatchInfo.setStatusMsg("Batch Page Size does not match total number of record plus addendas in the batch info");
      return paramACHBatchInfo;
    }
    if (paramACHBatchInfo.getRecordCursor() == 0L) {
      j = 1;
    }
    try
    {
      if (paramACHBatchInfo.getBatchId() == null)
      {
        if (j == 0)
        {
          paramACHBatchInfo.setStatusCode(22370);
          paramACHBatchInfo.setStatusMsg("Error, batch id is missing in the ACHBatchInfo");
          FFSDebug.log(str1 + "Error, batch id is missing in the ACHBatchInfo", 0);
          return paramACHBatchInfo;
        }
      }
      else if (j != 0)
      {
        paramACHBatchInfo.setStatusCode(17070);
        paramACHBatchInfo.setStatusMsg("Batch Id should be null on the first call to addACHBatch method");
        FFSDebug.log(str1 + "Batch Id should be null on the first call to addACHBatch method", 0);
        return paramACHBatchInfo;
      }
      if (!this.bB.cacheExists(paramACHBatchInfo.getBatchId()))
      {
        if (j == 0)
        {
          paramACHBatchInfo.setStatusCode(22960);
          paramACHBatchInfo.setStatusMsg("Processing has already been terminated due to some error. Start afresh.");
          FFSDebug.log(str1 + "Batch Id should be null on the first call to addACHBatch method", 0);
          return paramACHBatchInfo;
        }
        localACHBatchCache = new ACHBatchCache();
        localACHBatchCache.batchBalanceType = paramACHBatchInfo.getBatchBalanceType();
        if ((localACHBatchCache.batchBalanceType == null) || ((!localACHBatchCache.batchBalanceType.equals("UnbalancedBatch")) && (!localACHBatchCache.batchBalanceType.equals("BatchBalancedBatch")) && (!localACHBatchCache.batchBalanceType.equals("EntryBalancedBatch"))))
        {
          paramACHBatchInfo.setStatusCode(23820);
          paramACHBatchInfo.setStatusMsg("Unknown batch balance type: " + localACHBatchCache.batchBalanceType);
          FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg(), 6);
          return paramACHBatchInfo;
        }
      }
      else
      {
        localACHBatchCache = (ACHBatchCache)this.bB.getCache(paramACHBatchInfo.getBatchId());
      }
      if (j != 0)
      {
        if (paramACHBatchInfo.getBatchHeader() == null)
        {
          paramACHBatchInfo.setStatusCode(22150);
          paramACHBatchInfo.setStatusMsg("Invalid batch, header is missing:");
          FFSDebug.log(str1 + "Invalid batch, header is missing:", 0);
          return paramACHBatchInfo;
        }
        if (paramACHBatchInfo.getBatchHeader().getRecordType() != 5)
        {
          paramACHBatchInfo.setStatusCode(22160);
          paramACHBatchInfo.setStatusMsg("Invalid batch, header record is not of type Batch Header");
          FFSDebug.log(str1 + "Invalid batch, header record is not of type Batch Header", 0);
          return paramACHBatchInfo;
        }
        if (paramACHBatchInfo.getClassCode() == null)
        {
          paramACHBatchInfo.setStatusCode(22380);
          paramACHBatchInfo.setStatusMsg("Standard Entry Class code is missing in the ACHBatchInfo object");
          FFSDebug.log(str1 + "Standard Entry Class code is missing in the ACHBatchInfo object", 0);
          return paramACHBatchInfo;
        }
        a(paramFFSConnectionHolder, localACHBatchCache, paramACHBatchInfo);
        if ((paramACHBatchInfo.getOffsetAccountID() != null) && (!a(localACHBatchCache.offsetAcctIds, paramACHBatchInfo, paramACHBatchInfo.getOffsetAccountID()))) {
          return paramACHBatchInfo;
        }
        paramACHBatchInfo.setBatchStatus("ACHTEMPORARY");
        ACHBatch.addACHBatch(paramACHBatchInfo, paramFFSConnectionHolder, paramBoolean);
        if (paramACHBatchInfo.getStatusCode() != 0) {
          return paramACHBatchInfo;
        }
        try
        {
          ACHAgent.build(paramACHBatchInfo.getBatchHeader(), false, false);
          paramACHBatchInfo.setBatchContent(null);
        }
        catch (FFSException localFFSException1)
        {
          throw new FFSException(localFFSException1, "Invalid Batch Header record");
        }
        a(paramACHBatchInfo, localACHBatchCache, paramBoolean);
        localACHBatchCache.cacheId = paramACHBatchInfo.getBatchId();
        this.bB.createCache(localACHBatchCache);
      }
      if (paramACHBatchInfo.getRecords() != null) {
        m = paramACHBatchInfo.getRecords().length;
      }
      for (int n = 0; n < m; n++)
      {
        localObject = paramACHBatchInfo.getRecordsAt(n);
        if (((ACHRecordInfo)localObject).getClassCode() == null) {
          ((ACHRecordInfo)localObject).setClassCode(paramACHBatchInfo.getClassCode());
        }
        ((ACHRecordInfo)localObject).setAction("add");
        a(paramFFSConnectionHolder, (ACHRecordInfo)localObject, paramACHBatchInfo, paramBoolean, true);
        if (((ACHRecordInfo)localObject).getStatusCode() != 0)
        {
          paramACHBatchInfo.setStatusCode(((ACHRecordInfo)localObject).getStatusCode());
          paramACHBatchInfo.setStatusMsg(((ACHRecordInfo)localObject).getStatusMsg());
          this.bB.removeCache(paramACHBatchInfo.getBatchId());
          return paramACHBatchInfo;
        }
        if ((!localACHBatchCache.batchBalanceType.equals("UnbalancedBatch")) && (((ACHRecordInfo)localObject).getOffsetAccountID() != null) && (!a(localACHBatchCache.offsetAcctIds, paramACHBatchInfo, ((ACHRecordInfo)localObject).getOffsetAccountID()))) {
          return paramACHBatchInfo;
        }
        ((ACHRecordInfo)localObject).setBatchId(paramACHBatchInfo.getBatchId());
        ((ACHRecordInfo)localObject).setLogId(paramACHBatchInfo.getLogId());
        k += ACHRecord.addACHRecord(paramFFSConnectionHolder, (ACHRecordInfo)localObject, localACHBatchCache, paramBoolean);
        FFSDebug.log(str1 + "New records' payee id:" + ((ACHRecordInfo)localObject).getPayeeId(), 6);
        if (((ACHRecordInfo)localObject).getStatusCode() != 0)
        {
          paramACHBatchInfo.setStatusCode(((ACHRecordInfo)localObject).getStatusCode());
          paramACHBatchInfo.setStatusMsg(((ACHRecordInfo)localObject).getStatusMsg());
          this.bB.removeCache(paramACHBatchInfo.getBatchId());
          return paramACHBatchInfo;
        }
      }
      FFSDebug.log(str1 + "(batchInfo.getRecordCursor() + recordProcessed + 1)=" + (paramACHBatchInfo.getRecordCursor() + k + 1L), 6);
      if ((i != 0) || (paramACHBatchInfo.getRecordCursor() + k == paramACHBatchInfo.getTotalBatchSize()))
      {
        if (paramACHBatchInfo.getBatchControl() == null) {
          throw new FFSException(22170, "Invalid batch, control is missing:");
        }
        jdMethod_for(localACHBatchCache, paramACHBatchInfo);
        if ((!jdMethod_int(localACHBatchCache, paramACHBatchInfo)) || (!jdMethod_do(localACHBatchCache, paramACHBatchInfo)) || (!validateNumberOffsetAccount(paramFFSConnectionHolder, localACHBatchCache, paramACHBatchInfo, paramBoolean)))
        {
          this.bB.removeCache(paramACHBatchInfo.getBatchId());
          return paramACHBatchInfo;
        }
        ACHBatch.updateACHBatchWithControl(paramACHBatchInfo, paramFFSConnectionHolder, paramBoolean);
        if (paramACHBatchInfo.getStatusCode() != 0)
        {
          this.bB.removeCache(paramACHBatchInfo.getBatchId());
          return paramACHBatchInfo;
        }
        ACHBatch.updateACHBatchStatus(paramACHBatchInfo, "WILLPROCESSON", paramFFSConnectionHolder, paramBoolean);
        if (paramACHBatchInfo.getStatusCode() != 0)
        {
          this.bB.removeCache(paramACHBatchInfo.getBatchId());
          return paramACHBatchInfo;
        }
        a(paramFFSConnectionHolder, paramACHBatchInfo, paramHashMap, paramBoolean);
        if (paramACHBatchInfo.getStatusCode() != 0)
        {
          this.bB.removeCache(paramACHBatchInfo.getBatchId());
          return paramACHBatchInfo;
        }
        this.bB.removeCache(paramACHBatchInfo.getBatchId());
      }
      String str2 = "A new " + paramACHBatchInfo.getClassCode() + " ACH batch for ACH Company " + paramACHBatchInfo.getCompAchId() + " has been added in BPTW";
      if (paramBoolean) {
        str2 = "A new recurring " + paramACHBatchInfo.getClassCode() + " ACH batch for ACH Company " + paramACHBatchInfo.getCompAchId() + " has been added in BPTW";
      }
      jdMethod_do(paramFFSConnectionHolder, paramACHBatchInfo, localACHBatchCache, str2, paramBoolean);
      paramACHBatchInfo.setStatusCode(0);
      paramACHBatchInfo.setStatusMsg("Success");
    }
    catch (FFSException localFFSException2)
    {
      localObject = "*** " + str1 + " failed: ";
      str3 = null;
      this.bB.removeCache(paramACHBatchInfo.getBatchId());
      str3 = FFSDebug.stackTrace(localFFSException2);
      FFSDebug.log((String)localObject, str3, 0);
      throw localFFSException2;
    }
    catch (Exception localException)
    {
      Object localObject = "*** " + str1 + "failed: ";
      String str3 = null;
      this.bB.removeCache(paramACHBatchInfo.getBatchId());
      str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject, str3, 0);
      throw new FFSException(localException, (String)localObject);
    }
    FFSDebug.log(str1 + "recordProcessed=" + k, 6);
    FFSDebug.log(str1 + "done", 6);
    paramACHBatchInfo.setRecordCursor(paramACHBatchInfo.getRecordCursor() + k);
    return paramACHBatchInfo;
  }
  
  public ACHBatchInfo modACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.modACHBatch";
    if (paramACHBatchInfo == null)
    {
      paramACHBatchInfo = new ACHBatchInfo();
      paramACHBatchInfo.setStatusCode(16000);
      String str2 = "Passed ACHBatchInfo object  is null";
      paramACHBatchInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + ", " + str2, 0);
      return paramACHBatchInfo;
    }
    paramACHBatchInfo.setStatusCode(0);
    jdMethod_int(paramACHBatchInfo, false);
    return paramACHBatchInfo;
  }
  
  public RecACHBatchInfo modRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.modRecACHBatch";
    if (paramRecACHBatchInfo == null)
    {
      paramRecACHBatchInfo = new RecACHBatchInfo();
      paramRecACHBatchInfo.setStatusCode(16000);
      String str2 = "Passed ACHBatchInfo object  is null";
      paramRecACHBatchInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + ", " + str2, 0);
      return paramRecACHBatchInfo;
    }
    paramRecACHBatchInfo.setStatusCode(0);
    jdMethod_int(paramRecACHBatchInfo, true);
    return paramRecACHBatchInfo;
  }
  
  private ACHBatchInfo jdMethod_int(ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str1 = "ACHBatchProcessor.modACHBatch";
    FFSDebug.log(str1 + "start", 6);
    if (paramACHBatchInfo.getBatchId() == null)
    {
      paramACHBatchInfo.setStatusCode(16000);
      String str2 = "Batch Id in ACHBatchInfo object  is null";
      paramACHBatchInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + " " + str2, 0);
      return paramACHBatchInfo;
    }
    try
    {
      boolean bool = false;
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      localObject1 = ACHBatch.getInfosForBatch(localFFSConnectionHolder, paramACHBatchInfo);
      if (localObject1 == null)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject2 = paramACHBatchInfo;
        return localObject2;
      }
      a(localFFSConnectionHolder, paramACHBatchInfo, paramBoolean, (HashMap)localObject1);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject2 = paramACHBatchInfo;
        return localObject2;
      }
      bool = a(paramACHBatchInfo, paramBoolean, localFFSConnectionHolder);
      if (!bool)
      {
        paramACHBatchInfo.setStatusCode(17170);
        localObject2 = "ACHBatchInfo Can't modify ACHBatch information. It doesn't exist, has been processed, or is being processed BatchId: " + paramACHBatchInfo.getBatchId();
        paramACHBatchInfo.setStatusMsg((String)localObject2);
        FFSDebug.log(str1 + ", " + (String)localObject2, 0);
        localFFSConnectionHolder.conn.rollback();
        ACHBatchInfo localACHBatchInfo = paramACHBatchInfo;
        return localACHBatchInfo;
      }
      if (!a(paramACHBatchInfo))
      {
        FFSDebug.log(str1 + " " + paramACHBatchInfo.getStatusMsg(), 0);
        localFFSConnectionHolder.conn.rollback();
        localObject2 = paramACHBatchInfo;
        return localObject2;
      }
      a(localFFSConnectionHolder, paramACHBatchInfo, (HashMap)localObject1, "del");
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject2 = paramACHBatchInfo;
        return localObject2;
      }
      paramACHBatchInfo = a(paramACHBatchInfo, paramBoolean, localFFSConnectionHolder, (HashMap)localObject1, false);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject2 = paramACHBatchInfo;
        return localObject2;
      }
      a(localFFSConnectionHolder, paramACHBatchInfo, (HashMap)localObject1, "add;mod");
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject2 = paramACHBatchInfo;
        return localObject2;
      }
      localFFSConnectionHolder.conn.commit();
      paramACHBatchInfo.setStatusCode(0);
      paramACHBatchInfo.setStatusMsg("Success");
    }
    catch (FFSException localFFSException)
    {
      localObject1 = "*** " + str1 + " failed: ";
      localObject2 = null;
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      localObject2 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject1, (String)localObject2, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      Object localObject1 = "***  " + str1 + " failed: ";
      Object localObject2 = null;
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      localObject2 = FFSDebug.stackTrace(localException1);
      FFSDebug.log((String)localObject1, (String)localObject2, 0);
      throw new FFSException(localException1, (String)localObject1);
    }
    finally
    {
      if (localFFSConnectionHolder != null) {
        try
        {
          FFSDebug.log(str1 + ": Free connection", 6);
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Exception localException2)
        {
          FFSDebug.log(str1 + localException2.toString(), 0);
        }
      }
    }
    FFSDebug.log(str1 + ": done", 6);
    return paramACHBatchInfo;
  }
  
  private ACHBatchInfo a(ACHBatchInfo paramACHBatchInfo, boolean paramBoolean1, FFSConnectionHolder paramFFSConnectionHolder, HashMap paramHashMap, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.modACHBatch";
    ACHBatchCache localACHBatchCache = null;
    int i = 0;
    FFSDebug.log(str1 + "start", 6);
    String str2 = paramACHBatchInfo.getBatchId();
    try
    {
      ACHBatchInfo localACHBatchInfo = ACHBatch.getACHBatchById(paramFFSConnectionHolder, str2, paramBoolean1);
      if (localACHBatchInfo.getStatusCode() != 0)
      {
        paramACHBatchInfo.setStatusCode(localACHBatchInfo.getStatusCode());
        paramACHBatchInfo.setStatusMsg(localACHBatchInfo.getStatusMsg());
        FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg(), 0);
        return paramACHBatchInfo;
      }
      localACHBatchCache = jdMethod_new(paramFFSConnectionHolder, localACHBatchInfo, paramBoolean1);
      jdMethod_if(localACHBatchCache, paramACHBatchInfo);
      if ((paramACHBatchInfo.getOffsetAccountID() != null) && (!a(localACHBatchCache.offsetAcctIds, paramACHBatchInfo, paramACHBatchInfo.getOffsetAccountID()))) {
        return paramACHBatchInfo;
      }
      paramACHBatchInfo = ACHBatch.updateHeaderAndGenInfo(paramFFSConnectionHolder, paramACHBatchInfo, paramBoolean1);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        String str3 = str1 + " failed to modify ACHBatchInfo with batchId " + paramACHBatchInfo.getBatchId() + ": " + paramACHBatchInfo.getStatusMsg();
        FFSDebug.log(str3, 0);
        paramFFSConnectionHolder.conn.rollback();
        return paramACHBatchInfo;
      }
      try
      {
        ACHAgent.build(paramACHBatchInfo.getBatchHeader(), false, false);
        paramACHBatchInfo.setBatchContent(null);
      }
      catch (FFSException localFFSException2)
      {
        throw new FFSException(localFFSException2, "Invalid Batch Header record");
      }
      int j = 0;
      localObject1 = paramACHBatchInfo.getRecords();
      if (localObject1 != null) {
        j = localObject1.length;
      }
      String str5 = paramACHBatchInfo.getBatchBalanceType();
      if ((str5 == null) || ((!str5.equals("UnbalancedBatch")) && (!str5.equals("BatchBalancedBatch")) && (!str5.equals("EntryBalancedBatch"))))
      {
        paramACHBatchInfo.setStatusCode(23820);
        paramACHBatchInfo.setStatusMsg("Unknown batch balance type: " + localACHBatchCache.batchBalanceType);
        FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg(), 6);
        return paramACHBatchInfo;
      }
      localACHBatchCache.batchBalanceType = str5;
      Object localObject2;
      for (int k = 0; k < j; k++)
      {
        localObject2 = localObject1[k];
        if ((localObject2 == null) || (((ACHRecordInfo)localObject2).getAction() == null))
        {
          FFSDebug.log("ACHBatchProcessor.modACHBatch::No value or action found. Skipping detail record:" + (k + 1), 6);
        }
        else
        {
          if (((ACHRecordInfo)localObject2).getClassCode() == null) {
            ((ACHRecordInfo)localObject2).setClassCode(paramACHBatchInfo.getClassCode());
          }
          String str7 = ((ACHRecordInfo)localObject2).getAction();
          if (str7.compareTo("add") == 0)
          {
            localObject2 = a(paramFFSConnectionHolder, (ACHRecordInfo)localObject2, paramACHBatchInfo, paramBoolean1, true);
            if (((ACHRecordInfo)localObject2).getStatusCode() == 0)
            {
              ((ACHRecordInfo)localObject2).setBatchId(paramACHBatchInfo.getBatchId());
              ACHRecord.addACHRecord(paramFFSConnectionHolder, (ACHRecordInfo)localObject2, localACHBatchCache, paramBoolean1);
            }
            if ((!localACHBatchCache.batchBalanceType.equals("UnbalancedBatch")) && (((ACHRecordInfo)localObject2).getOffsetAccountID() != null) && (!a(localACHBatchCache.offsetAcctIds, paramACHBatchInfo, ((ACHRecordInfo)localObject2).getOffsetAccountID()))) {
              return paramACHBatchInfo;
            }
          }
          else if (str7.compareTo("mod") == 0)
          {
            localObject2 = jdMethod_if(paramFFSConnectionHolder, (ACHRecordInfo)localObject2, paramACHBatchInfo);
            if (((ACHRecordInfo)localObject2).getStatusCode() == 0) {
              localObject2 = ACHRecord.updateACHRecord(paramFFSConnectionHolder, (ACHRecordInfo)localObject2, localACHBatchCache, paramBoolean1);
            }
            if ((!localACHBatchCache.batchBalanceType.equals("UnbalancedBatch")) && (((ACHRecordInfo)localObject2).getOffsetAccountID() != null) && (!a(localACHBatchCache.offsetAcctIds, paramACHBatchInfo, ((ACHRecordInfo)localObject2).getOffsetAccountID()))) {
              return paramACHBatchInfo;
            }
          }
          else if (str7.compareTo("del") == 0)
          {
            localObject2 = ACHRecord.canACHRecord(paramFFSConnectionHolder, (ACHRecordInfo)localObject2, localACHBatchCache, paramBoolean1);
            if (((ACHRecordInfo)localObject2).getStatusCode() == 0) {
              localObject2 = a(paramFFSConnectionHolder, (ACHRecordInfo)localObject2, paramACHBatchInfo);
            }
          }
          if (((ACHRecordInfo)localObject2).getStatusCode() != 0)
          {
            paramACHBatchInfo.setStatusCode(((ACHRecordInfo)localObject2).getStatusCode());
            paramACHBatchInfo.setStatusMsg(((ACHRecordInfo)localObject2).getStatusMsg());
            FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg(), 0);
            return paramACHBatchInfo;
          }
        }
      }
      jdMethod_for(localACHBatchCache, paramACHBatchInfo);
      if (!jdMethod_int(localACHBatchCache, paramACHBatchInfo)) {
        return paramACHBatchInfo;
      }
      if (!jdMethod_do(localACHBatchCache, paramACHBatchInfo)) {
        return paramACHBatchInfo;
      }
      if (!validateNumberOffsetAccount(paramFFSConnectionHolder, localACHBatchCache, paramACHBatchInfo, paramBoolean1))
      {
        this.bB.removeCache(paramACHBatchInfo.getBatchId());
        return paramACHBatchInfo;
      }
      String str6 = ACHAdapterUtil.getProperty("bpw.ach.payee.enforce.prenote.period", "N");
      if ((str6.compareTo("Y") == 0) && (!a(paramFFSConnectionHolder, paramACHBatchInfo, localACHBatchInfo, paramBoolean1))) {
        return paramACHBatchInfo;
      }
      ACHBatch.updateACHBatchWithControl(paramACHBatchInfo, paramFFSConnectionHolder, paramBoolean1);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        paramFFSConnectionHolder.conn.rollback();
        return paramACHBatchInfo;
      }
      a(paramFFSConnectionHolder, paramACHBatchInfo, paramHashMap, paramBoolean1, localACHBatchInfo);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localObject2 = str1 + " failed to modify Schedule with batchId " + paramACHBatchInfo.getBatchId() + ": " + paramACHBatchInfo.getStatusMsg();
        FFSDebug.log((String)localObject2, 0);
        paramFFSConnectionHolder.conn.rollback();
        return paramACHBatchInfo;
      }
      a(paramFFSConnectionHolder, paramACHBatchInfo, localACHBatchCache, "An ACH batch " + paramACHBatchInfo.getBatchId() + " has been modified in BPTW", paramBoolean1);
      paramACHBatchInfo.setStatusCode(0);
      paramACHBatchInfo.setStatusMsg("Success");
    }
    catch (FFSException localFFSException1)
    {
      str4 = "*** " + str1 + " failed: ";
      localObject1 = null;
      localObject1 = FFSDebug.stackTrace(localFFSException1);
      FFSDebug.log(str4, (String)localObject1, 0);
      throw localFFSException1;
    }
    catch (Exception localException)
    {
      String str4 = "***  " + str1 + " failed: ";
      Object localObject1 = null;
      localObject1 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, (String)localObject1, 0);
      throw new FFSException(localException, str4);
    }
    FFSDebug.log(str1 + ": recordProcessed=" + i, 6);
    FFSDebug.log(str1 + ": done", 6);
    return paramACHBatchInfo;
  }
  
  public ACHBatchInfo canACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    if (paramACHBatchInfo == null)
    {
      paramACHBatchInfo = new ACHBatchInfo();
      paramACHBatchInfo.setStatusCode(16000);
      String str = "ACHBatchInfo object  is null";
      paramACHBatchInfo.setStatusMsg(str);
      FFSDebug.log("ACHBatchProcessor.canACHBatch, " + str, 0);
      return paramACHBatchInfo;
    }
    paramACHBatchInfo.setStatusCode(0);
    jdMethod_for(paramACHBatchInfo, false);
    return paramACHBatchInfo;
  }
  
  public RecACHBatchInfo canRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.canRecACHBatch";
    if (paramRecACHBatchInfo == null)
    {
      paramRecACHBatchInfo = new RecACHBatchInfo();
      paramRecACHBatchInfo.setStatusCode(16000);
      String str2 = "ACHBatchInfo object  is null";
      paramRecACHBatchInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + ", " + str2, 0);
      return paramRecACHBatchInfo;
    }
    paramRecACHBatchInfo.setStatusCode(0);
    jdMethod_for(paramRecACHBatchInfo, true);
    return paramRecACHBatchInfo;
  }
  
  private ACHBatchInfo jdMethod_for(ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.canBatch";
    FFSConnectionHolder localFFSConnectionHolder = null;
    FFSDebug.log(str1 + "start", 6);
    String str2;
    if (paramACHBatchInfo == null)
    {
      paramACHBatchInfo = new ACHBatchInfo();
      paramACHBatchInfo.setStatusCode(16000);
      str2 = "Passed ACHBatchInfo object  is null";
      paramACHBatchInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + ", " + str2, 0);
      return paramACHBatchInfo;
    }
    if (paramACHBatchInfo.getBatchId() == null)
    {
      paramACHBatchInfo.setStatusCode(16000);
      str2 = "Batch Id in ACHBatchInfo object  is null";
      paramACHBatchInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + " " + str2, 0);
      return paramACHBatchInfo;
    }
    try
    {
      boolean bool = false;
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      localObject1 = ACHBatch.getInfosForBatch(localFFSConnectionHolder, paramACHBatchInfo);
      Object localObject2;
      if (localObject1 == null)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject2 = paramACHBatchInfo;
        return localObject2;
      }
      a(localFFSConnectionHolder, paramACHBatchInfo);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localObject2 = paramACHBatchInfo;
        return localObject2;
      }
      bool = a(paramACHBatchInfo, paramBoolean, localFFSConnectionHolder);
      if (!bool)
      {
        paramACHBatchInfo.setStatusCode(17170);
        localObject2 = "ACHBatchInfo Can't modify ACHBatch information. It doesn't exist, has been processed, or is being processed BatchId: " + paramACHBatchInfo.getBatchId();
        paramACHBatchInfo.setStatusMsg((String)localObject2);
        FFSDebug.log(str1 + ", " + (String)localObject2, 0);
        localObject3 = paramACHBatchInfo;
        return localObject3;
      }
      paramACHBatchInfo = ACHBatch.getACHBatch(paramACHBatchInfo, localFFSConnectionHolder, paramBoolean, true, true);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject2 = paramACHBatchInfo;
        return localObject2;
      }
      int i = 0;
      Object localObject3 = paramACHBatchInfo.getRecords();
      if (localObject3 != null) {
        i = localObject3.length;
      }
      for (int j = 0; j < i; j++)
      {
        localObject3[j].setAction("del");
        if (localObject3[j].getClassCode() == null) {
          localObject3[j].setClassCode(paramACHBatchInfo.getClassCode());
        }
      }
      a(localFFSConnectionHolder, paramACHBatchInfo, (HashMap)localObject1, null);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        ACHBatchInfo localACHBatchInfo1 = paramACHBatchInfo;
        return localACHBatchInfo1;
      }
      for (int k = 0; k < i; k++)
      {
        ACHRecordInfo localACHRecordInfo = ACHRecord.updateACHRecordStatus(localObject3[k], "CANCELEDON", localFFSConnectionHolder, paramBoolean);
        if (localACHRecordInfo.getStatusCode() == 0)
        {
          localACHRecordInfo = a(localFFSConnectionHolder, localObject3[k], paramACHBatchInfo);
          if (localACHRecordInfo.getStatusCode() != 0)
          {
            paramACHBatchInfo.setStatusCode(localACHRecordInfo.getStatusCode());
            paramACHBatchInfo.setStatusMsg(localACHRecordInfo.getStatusMsg());
            FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg(), 0);
            break;
          }
        }
        else
        {
          paramACHBatchInfo.setStatusCode(localACHRecordInfo.getStatusCode());
          paramACHBatchInfo.setStatusMsg(localACHRecordInfo.getStatusMsg());
          FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg(), 0);
        }
      }
      ACHBatchInfo localACHBatchInfo2;
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localACHBatchInfo2 = paramACHBatchInfo;
        return localACHBatchInfo2;
      }
      paramACHBatchInfo = jdMethod_if(localFFSConnectionHolder, paramACHBatchInfo, paramBoolean);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localACHBatchInfo2 = paramACHBatchInfo;
        return localACHBatchInfo2;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      Object localObject1 = "*** " + str1 + " failed: ";
      String str3 = null;
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject1, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localFFSConnectionHolder != null) {
        try
        {
          FFSDebug.log(str1 + ": Free connection", 6);
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + localException.toString(), 0);
        }
      }
    }
    FFSDebug.log(str1 + ": done", 6);
    return paramACHBatchInfo;
  }
  
  private ACHBatchInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.canBatchOnlyTX: ";
    FFSDebug.log(str1 + "start", 6);
    try
    {
      if (!a(paramACHBatchInfo))
      {
        FFSDebug.log(str1 + " " + paramACHBatchInfo.getStatusMsg(), 0);
        return paramACHBatchInfo;
      }
      jdMethod_if(paramFFSConnectionHolder, paramACHBatchInfo, null, "An ACH batch " + paramACHBatchInfo.getBatchId() + " has been canceled in BPTW", paramBoolean);
      jdMethod_int(paramFFSConnectionHolder, paramACHBatchInfo, paramBoolean);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        paramFFSConnectionHolder.conn.rollback();
        return paramACHBatchInfo;
      }
      ACHBatch.canACHBatch(paramFFSConnectionHolder, paramACHBatchInfo, paramBoolean);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        paramFFSConnectionHolder.conn.rollback();
        return paramACHBatchInfo;
      }
      paramACHBatchInfo.setStatusCode(0);
      paramACHBatchInfo.setStatusMsg("Success");
    }
    catch (FFSException localFFSException)
    {
      String str2 = "*** " + str1 + " failed: ";
      String str3 = null;
      try
      {
        paramFFSConnectionHolder.conn.rollback();
      }
      catch (Exception localException)
      {
        FFSDebug.log(str1 + localException.toString(), 0);
      }
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    FFSDebug.log(str1 + ": done", 6);
    return paramACHBatchInfo;
  }
  
  public ACHBatchInfo getACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.getACHBatch";
    if (paramACHBatchInfo == null)
    {
      paramACHBatchInfo = new ACHBatchInfo();
      paramACHBatchInfo.setStatusCode(16000);
      String str2 = "ACHBatchInfo object  is null";
      paramACHBatchInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + ", " + str2, 0);
      return paramACHBatchInfo;
    }
    paramACHBatchInfo.setStatusCode(0);
    return jdMethod_do(paramACHBatchInfo, false);
  }
  
  public RecACHBatchInfo getRecACHBatch(RecACHBatchInfo paramRecACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.getRecACHBatch";
    if (paramRecACHBatchInfo == null)
    {
      paramRecACHBatchInfo = new RecACHBatchInfo();
      paramRecACHBatchInfo.setStatusCode(16000);
      String str2 = "ACHBatchInfo object  is null";
      paramRecACHBatchInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + ", " + str2, 0);
      return paramRecACHBatchInfo;
    }
    paramRecACHBatchInfo.setStatusCode(0);
    return (RecACHBatchInfo)jdMethod_do(paramRecACHBatchInfo, true);
  }
  
  private ACHBatchInfo jdMethod_do(ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.getBatch (isRecurring: " + paramBoolean + ")";
    FFSDebug.log(str1 + " start", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      paramACHBatchInfo = a(localFFSConnectionHolder, paramACHBatchInfo, paramBoolean, true, true, false);
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        ACHBatchInfo localACHBatchInfo = paramACHBatchInfo;
        return localACHBatchInfo;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str2 = "*** " + str1 + "failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1 + ": done", 6);
    return paramACHBatchInfo;
  }
  
  public ACHBatchInfo getBatchTXFromAdapter(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws FFSException
  {
    return a(paramFFSConnectionHolder, paramACHBatchInfo, paramBoolean1, paramBoolean2, paramBoolean3, true);
  }
  
  private ACHBatchInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.getBatchTX (isRecurring: " + paramBoolean1 + ")";
    FFSDebug.log(str1 + " start", 6);
    if (paramACHBatchInfo.getBatchId() == null)
    {
      paramACHBatchInfo.setStatusCode(16000);
      String str2 = "Batch Id in ACHBatchInfo object  is null";
      paramACHBatchInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + ": " + str2, 0);
      return paramACHBatchInfo;
    }
    try
    {
      a(paramFFSConnectionHolder, paramACHBatchInfo.getBatchId(), paramBoolean1, paramBoolean4);
      paramACHBatchInfo = ACHBatch.getACHBatch(paramACHBatchInfo, paramFFSConnectionHolder, paramBoolean1, paramBoolean2, paramBoolean3);
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + "failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str3);
    }
    if (paramACHBatchInfo.getRecords() != null) {
      FFSDebug.log(str1 + ": record count=" + paramACHBatchInfo.getRecords().length, 6);
    }
    FFSDebug.log(str1 + ": done", 6);
    return paramACHBatchInfo;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.rebuildDirtyBatch batchId: " + paramString + ")";
    FFSDebug.log(str1 + " start", 6);
    try
    {
      if (paramBoolean2 == true)
      {
        String str2 = ACHBatch.getACHBatchType(paramFFSConnectionHolder, paramString);
        if ((str2 != null) && (str2.compareTo("Recurring") == 0))
        {
          localObject1 = RecSrvrTIDToSrvrTID.getRecSrvrTIDs(paramFFSConnectionHolder, paramString, 1);
          if ((localObject1 == null) || (localObject1.length == 0)) {
            return;
          }
          paramString = localObject1[0];
          paramBoolean1 = true;
        }
      }
      boolean bool = ACHBatch.checkDirtyBatch(paramFFSConnectionHolder, paramString, paramBoolean1);
      if (bool == true)
      {
        FFSDebug.log(str1 + " This batch includes dirty records, rebuilding...", 6);
        localObject1 = new ACHBatchInfo();
        if (paramBoolean1 == true) {
          localObject1 = new RecACHBatchInfo();
        }
        ((ACHBatchInfo)localObject1).setBatchId(paramString);
        localObject1 = ACHBatch.getACHBatch((ACHBatchInfo)localObject1, paramFFSConnectionHolder, paramBoolean1, true, true, true);
        if (((ACHBatchInfo)localObject1).getStatusCode() != 0) {
          return;
        }
        localObject2 = ((ACHBatchInfo)localObject1).getRecords();
        if ((localObject2 == null) || (localObject2.length == 0))
        {
          FFSDebug.log(str1 + ": No records, nothing needs to be rebuilt", 6);
          return;
        }
        for (int i = 0; i < localObject2.length; i++)
        {
          Object localObject3 = localObject2[i];
          localObject3.setAddenda(null);
          localObject3.setAction("mod");
        }
        ((ACHBatchInfo)localObject1).setAction("mod");
        HashMap localHashMap = ACHBatch.getInfosForBatch(paramFFSConnectionHolder, (ACHBatchInfo)localObject1);
        if (localHashMap == null)
        {
          paramFFSConnectionHolder.conn.rollback();
          throw new FFSException(((ACHBatchInfo)localObject1).getStatusMsg());
        }
        a((ACHBatchInfo)localObject1, paramBoolean1, paramFFSConnectionHolder, localHashMap, paramBoolean2);
      }
    }
    catch (Exception localException)
    {
      if (paramFFSConnectionHolder.conn != null) {
        paramFFSConnectionHolder.conn.rollback();
      }
      Object localObject1 = "*** " + str1 + "failed: ";
      Object localObject2 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localException, (String)localObject1);
    }
    FFSDebug.log(str1 + ": done", 6);
  }
  
  private boolean jdMethod_do(ACHBatchCache paramACHBatchCache, ACHBatchInfo paramACHBatchInfo)
  {
    ACHRecordInfo localACHRecordInfo = null;
    String str = null;
    localACHRecordInfo = paramACHBatchInfo.getBatchControl();
    if (localACHRecordInfo == null)
    {
      paramACHBatchInfo.setStatusCode(22170);
      paramACHBatchInfo.setStatusMsg("Invalid batch, control is missing:");
      return false;
    }
    str = ((Short)paramACHBatchInfo.getBatchControlFieldValueObject(2)).toString();
    FFSDebug.log("ACHBatchProcessor.validateBatchHeadAndControlInfo:: Service Class code from header:", paramACHBatchCache.serviceClassCode, " from control:", str, 6);
    if ((str == null) || (str.toString().equals("0")))
    {
      paramACHBatchInfo.setBatchControlFieldValueObject(2, paramACHBatchCache.serviceClassCode);
      FFSDebug.log("ACHBatchProcessor.validateBatchHeadAndControlInfo:: Service class code in Control is Null, Assign the header value :" + paramACHBatchCache.serviceClassCode, 6);
    }
    else if (!str.equals(paramACHBatchCache.serviceClassCode))
    {
      paramACHBatchInfo.setStatusCode(22220);
      paramACHBatchInfo.setStatusMsg("Different Service class code in batch header and control");
      return false;
    }
    return true;
  }
  
  private void jdMethod_for(ACHBatchCache paramACHBatchCache, ACHBatchInfo paramACHBatchInfo)
  {
    paramACHBatchInfo.setBatchControlFieldValueObject(22, FFSUtil.setScale(paramACHBatchCache.batchDebitSum, 0).toString());
    paramACHBatchInfo.setBatchControlFieldValueObject(23, FFSUtil.setScale(paramACHBatchCache.batchCreditSum, 0).toString());
    paramACHBatchInfo.setBatchControlFieldValueObject(21, new Long(paramACHBatchCache.batchHash).toString());
    paramACHBatchInfo.setBatchControlFieldValueObject(20, new Integer(paramACHBatchCache.batchEntryCount).toString());
    a(paramACHBatchCache, paramACHBatchInfo);
  }
  
  private void a(ACHBatchCache paramACHBatchCache, ACHBatchInfo paramACHBatchInfo)
  {
    paramACHBatchInfo.setNonOffBatchCreditSum(paramACHBatchCache.nonOffBatchCreditSum.longValue());
    paramACHBatchInfo.setNonOffBatchDebitSum(paramACHBatchCache.nonOffBatchDebitSum.longValue());
    paramACHBatchInfo.setNonOffBatchEntryCount(paramACHBatchCache.nonOffBatchEntryCount);
  }
  
  private boolean jdMethod_int(ACHBatchCache paramACHBatchCache, ACHBatchInfo paramACHBatchInfo)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramACHBatchCache.serviceClassCode);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      paramACHBatchInfo.setStatusCode(22340);
      paramACHBatchInfo.setStatusMsg("Invalid Service class code value");
      return false;
    }
    if (paramACHBatchCache.stdEntryClassCode.equals("ADV")) {
      return true;
    }
    switch (i)
    {
    case 220: 
      if (FFSUtil.isPositive(paramACHBatchCache.batchDebitSum))
      {
        paramACHBatchInfo.setStatusCode(22320);
        paramACHBatchInfo.setStatusMsg("Service class code value is CREDITS ONLY but batch contains debit amount");
        return false;
      }
      break;
    case 225: 
      if (FFSUtil.isPositive(paramACHBatchCache.batchCreditSum))
      {
        paramACHBatchInfo.setStatusCode(22330);
        paramACHBatchInfo.setStatusMsg("Service class code value is DEBITS ONLY but batch contains credit amount");
        return false;
      }
      break;
    case 200: 
      break;
    case 280: 
      break;
    default: 
      paramACHBatchInfo.setStatusCode(22340);
      paramACHBatchInfo.setStatusMsg("Invalid Service class code value");
      return false;
    }
    BigDecimal localBigDecimal1 = paramACHBatchCache.batchDebitSum.movePointLeft(2);
    if (localBigDecimal1.compareTo(ACHConsts.MAX_BATCH_TOTAL_DEBITS) > 0)
    {
      paramACHBatchInfo.setStatusCode(23690);
      paramACHBatchInfo.setStatusMsg("Total debit in ACH Batch has more than 12 positions: " + paramACHBatchCache.batchDebitSum);
      return false;
    }
    BigDecimal localBigDecimal2 = paramACHBatchCache.batchCreditSum.movePointLeft(2);
    if (localBigDecimal2.compareTo(ACHConsts.MAX_BATCH_TOTAL_CREDITS) > 0)
    {
      paramACHBatchInfo.setStatusCode(23700);
      paramACHBatchInfo.setStatusMsg("Total credit in ACH Batch has more than 12 positions: " + paramACHBatchCache.batchCreditSum);
      return false;
    }
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setStatusMsg("Success");
    return true;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.createScheduleForSingle ";
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramACHBatchInfo.getFiId();
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = 10;
    localScheduleInfo.LogID = paramACHBatchInfo.getLogId();
    localScheduleInfo.InstanceCount = 1;
    localScheduleInfo.CurInstanceNum = 1;
    String str2 = "ACHBATCHTRN";
    String str3 = paramACHBatchInfo.getBatchId();
    try
    {
      localScheduleInfo.StartDate = BPWUtil.getDateDBFormat(paramACHBatchInfo.getDueDate());
      String str4 = paramACHBatchInfo.getDueDate();
      int i = paramACHBatchInfo.getBatchHeaderFieldValueShort(2);
      localScheduleInfo.NextInstanceDate = ACHBatch.getNextInstanceDateForScheduleInfo(str4, i, paramHashMap);
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, str2, str3, localScheduleInfo);
    }
    catch (FFSException localFFSException)
    {
      str5 = "*** " + str1 + " failed: ";
      str6 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str5, str6, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str5 = "*** " + str1 + " failed: ";
      String str6 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, str6, 0);
      throw new FFSException(localException, str5);
    }
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    HashMap localHashMap = ACHBatch.getInfosForBatch(paramFFSConnectionHolder, paramACHBatchInfo);
    if (localHashMap == null) {
      throw new FFSException(paramACHBatchInfo.getStatusMsg());
    }
    a(paramFFSConnectionHolder, paramACHBatchInfo, localHashMap);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, HashMap paramHashMap, boolean paramBoolean)
    throws FFSException
  {
    Object localObject;
    if (paramBoolean == true)
    {
      a(paramFFSConnectionHolder, (RecACHBatchInfo)paramACHBatchInfo, paramHashMap);
      localObject = ACHBatch.generateACHBatchFromACHRecBatch(paramFFSConnectionHolder, paramACHBatchInfo.getBatchId(), ((RecACHBatchInfo)paramACHBatchInfo).getStartDate(), ACHBatch.getEffectiveDateFromBatch(paramACHBatchInfo).substring(2));
      if (((ACHBatchInfo)localObject).getStatusCode() != 0)
      {
        paramACHBatchInfo.setStatusCode(((ACHBatchInfo)localObject).getStatusCode());
        paramACHBatchInfo.setStatusMsg(((ACHBatchInfo)localObject).getStatusMsg());
        return;
      }
      String str = a(paramFFSConnectionHolder, (ACHBatchInfo)localObject, false);
      ACHBatch.updateACHBatchStatus((ACHBatchInfo)localObject, str, paramFFSConnectionHolder, false);
      jdMethod_do(paramFFSConnectionHolder, (ACHBatchInfo)localObject, null, "A single ACH batch in a recurring ACH batch has been added in BPTW", false);
      if (str.compareTo("WILLPROCESSON") == 0) {
        a(paramFFSConnectionHolder, (ACHBatchInfo)localObject, paramHashMap);
      }
    }
    else
    {
      localObject = a(paramFFSConnectionHolder, paramACHBatchInfo, false);
      if (("LIMIT_CHECK_FAILED".equals(localObject)) || ("LIMIT_REVERT_FAILED".equals(localObject)) || ("APPROVAL_FAILED".equals(localObject)) || ("APPROVAL_NOT_ALLOWED".equals(localObject)))
      {
        paramACHBatchInfo.setBatchStatus((String)localObject);
        return;
      }
      paramACHBatchInfo.setBatchStatus((String)localObject);
      ACHBatch.updateACHBatchStatus(paramACHBatchInfo, (String)localObject, paramFFSConnectionHolder, false);
      if ("WILLPROCESSON".equals(localObject)) {
        a(paramFFSConnectionHolder, paramACHBatchInfo, paramHashMap);
      }
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, RecACHBatchInfo paramRecACHBatchInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.createScheduleForRec ";
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramRecACHBatchInfo.getFiId();
    localScheduleInfo.Status = "Active";
    try
    {
      localScheduleInfo.Frequency = FFSUtil.getFreqInt(paramRecACHBatchInfo.getFrequency());
      localScheduleInfo.LogID = paramRecACHBatchInfo.getLogId();
      localScheduleInfo.InstanceCount = paramRecACHBatchInfo.getPmtsCount();
      String str2 = "RECACHBATCHTRN";
      localScheduleInfo.CurInstanceNum = 1;
      localScheduleInfo.StartDate = BPWUtil.getDateDBFormat(paramRecACHBatchInfo.getStartDate());
      str3 = paramRecACHBatchInfo.getStartDate();
      int i = paramRecACHBatchInfo.getBatchHeaderFieldValueShort(2);
      localScheduleInfo.NextInstanceDate = ACHBatch.getNextInstanceDateInBPWWarehouse(str3, i, paramHashMap);
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, str2, paramRecACHBatchInfo.getBatchId(), localScheduleInfo);
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localException, str3);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo1, HashMap paramHashMap, boolean paramBoolean, ACHBatchInfo paramACHBatchInfo2)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.modifySchedule";
    try
    {
      Object localObject1;
      if (paramBoolean)
      {
        jdMethod_if(paramFFSConnectionHolder, (RecACHBatchInfo)paramACHBatchInfo1, paramHashMap);
        localObject1 = ACHBatch.getUnprocessedSingleIds(paramFFSConnectionHolder, paramACHBatchInfo1.getBatchId());
        if ((localObject1 != null) && (localObject1.length != 0))
        {
          localObject2 = null;
          for (int i = 0; i < localObject1.length; i++)
          {
            localObject2 = ACHBatch.getACHBatchById(paramFFSConnectionHolder, localObject1[i], false);
            if (((ACHBatchInfo)localObject2).getBatchStatus().equals("WILLPROCESSON")) {
              ScheduleInfo.cancelSchedule(paramFFSConnectionHolder.conn, "ACHBATCHTRN", localObject1[i]);
            }
            RecSrvrTIDToSrvrTID.delete(paramFFSConnectionHolder, paramACHBatchInfo1.getBatchId(), localObject1[i], 1);
            String str4 = jdMethod_try(paramFFSConnectionHolder, (ACHBatchInfo)localObject2, paramBoolean);
            if (str4.compareTo("LIMIT_REVERT_FAILED") == 0)
            {
              String str5 = str1 + ":Could not revert limit for Batch Id:" + localObject1[i];
              FFSDebug.log(str5, 0);
            }
            else
            {
              str4 = "CANCELEDON";
            }
            ACHBatch.updateACHBatchStatus((ACHBatchInfo)localObject2, str4, paramFFSConnectionHolder, false);
          }
        }
        localObject2 = ACHBatch.generateACHBatchFromACHRecBatch(paramFFSConnectionHolder, paramACHBatchInfo1.getBatchId(), ((RecACHBatchInfo)paramACHBatchInfo1).getStartDate(), ACHBatch.getEffectiveDateFromBatch(paramACHBatchInfo1).substring(2));
        if (((ACHBatchInfo)localObject2).getStatusCode() != 0)
        {
          paramACHBatchInfo1.setStatusCode(((ACHBatchInfo)localObject2).getStatusCode());
          paramACHBatchInfo1.setStatusMsg(((ACHBatchInfo)localObject2).getStatusMsg());
          return;
        }
        String str2 = a(paramFFSConnectionHolder, (ACHBatchInfo)localObject2, false);
        ACHBatch.updateACHBatchStatus((ACHBatchInfo)localObject2, str2, paramFFSConnectionHolder, false);
        jdMethod_do(paramFFSConnectionHolder, (ACHBatchInfo)localObject2, null, "A single ACH batch in a recurring ACH batch has been added in BPTW", false);
        if ("WILLPROCESSON".equals(str2))
        {
          paramACHBatchInfo1.setBatchStatus(str2);
          a(paramFFSConnectionHolder, (ACHBatchInfo)localObject2, paramHashMap);
        }
      }
      else
      {
        localObject1 = jdMethod_try(paramFFSConnectionHolder, paramACHBatchInfo2, paramBoolean);
        if ("LIMIT_REVERT_FAILED".equals(localObject1))
        {
          FFSDebug.log(str1, " Limit Revert Failed.", 6);
          paramACHBatchInfo1.setStatusCode(paramACHBatchInfo2.getStatusCode());
          paramACHBatchInfo1.setStatusMsg(paramACHBatchInfo2.getStatusMsg());
          return;
        }
        localObject1 = a(paramFFSConnectionHolder, paramACHBatchInfo1, false);
        if (("LIMIT_CHECK_FAILED".equals(localObject1)) || ("LIMIT_REVERT_FAILED".equals(localObject1)) || ("APPROVAL_FAILED".equals(localObject1)) || ("APPROVAL_NOT_ALLOWED".equals(localObject1)))
        {
          paramACHBatchInfo1.setBatchStatus((String)localObject1);
          return;
        }
        paramACHBatchInfo1.setBatchStatus((String)localObject1);
        ACHBatch.updateACHBatchStatus(paramACHBatchInfo1, (String)localObject1, paramFFSConnectionHolder, false);
        localObject2 = paramACHBatchInfo1.getDueDate();
        int j = paramACHBatchInfo1.getBatchHeaderFieldValueShort(2);
        int k = ACHBatch.getNextInstanceDateForScheduleInfo((String)localObject2, j, paramHashMap);
        int m = ScheduleInfo.modifyScheduleStartDate(paramFFSConnectionHolder, paramACHBatchInfo1.getFiId(), "ACHBATCHTRN", paramACHBatchInfo1.getBatchId(), k);
        if ((m < 1) && (((String)localObject1).compareTo("WILLPROCESSON") == 0)) {
          a(paramFFSConnectionHolder, paramACHBatchInfo1, paramHashMap);
        }
      }
    }
    catch (Exception localException)
    {
      Object localObject2 = "*** " + str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject2, str3, 0);
      throw new FFSException(localException, (String)localObject2);
    }
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, RecACHBatchInfo paramRecACHBatchInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.modifyRecSchedule";
    String str2 = null;
    str2 = "RECACHBATCHTRN";
    ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(paramRecACHBatchInfo.getFiId(), str2, paramRecACHBatchInfo.getBatchId(), paramFFSConnectionHolder);
    String str3;
    if ((localScheduleInfo == null) || (localScheduleInfo.Status.equals("Processing")))
    {
      str3 = "*** " + str1 + " failed: Can't find schedule for: " + " InstructionType: " + str2 + " InstructionId: " + paramRecACHBatchInfo.getBatchId();
      FFSDebug.log(str3, 0);
      throw new FFSException(str3);
    }
    try
    {
      localScheduleInfo.StartDate = BPWUtil.getDateDBFormat(paramRecACHBatchInfo.getStartDate());
      localScheduleInfo.Frequency = FFSUtil.getFreqInt(paramRecACHBatchInfo.getFrequency());
      localScheduleInfo.InstanceCount = paramRecACHBatchInfo.getPmtsCount();
      localScheduleInfo.CurInstanceNum = 1;
      str3 = paramRecACHBatchInfo.getStartDate();
      int i = paramRecACHBatchInfo.getBatchHeaderFieldValueShort(2);
      localScheduleInfo.NextInstanceDate = ACHBatch.getNextInstanceDateInBPWWarehouse(str3, i, paramHashMap);
      ScheduleInfo.modifySchedule(paramFFSConnectionHolder, localScheduleInfo.ScheduleID, localScheduleInfo);
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localException, str4);
    }
  }
  
  private void a(ACHBatchInfo paramACHBatchInfo, ACHBatchCache paramACHBatchCache, boolean paramBoolean)
  {
    if (!paramACHBatchInfo.getClassCode().equals("ADV")) {
      paramACHBatchCache.compACHId = ((String)paramACHBatchInfo.getBatchHeaderFieldValueObject(5));
    } else {
      paramACHBatchCache.compACHId = null;
    }
    paramACHBatchCache.compId = paramACHBatchInfo.getCompId();
    paramACHBatchCache.orgDfiId = paramACHBatchInfo.getOdfiId();
    paramACHBatchCache.batchNo = paramACHBatchInfo.getBatchHeaderFieldValueObject(12).toString();
    paramACHBatchCache.serviceClassCode = paramACHBatchInfo.getBatchHeaderFieldValueObject(2).toString();
    paramACHBatchCache.stdEntryClassCode = paramACHBatchInfo.getClassCode();
    if (paramBoolean) {
      paramACHBatchCache.dueDate = ((RecACHBatchInfo)paramACHBatchInfo).getStartDate();
    } else {
      paramACHBatchCache.dueDate = paramACHBatchInfo.getDtProcessed();
    }
    paramACHBatchCache.settlementDate = ((String)paramACHBatchInfo.getBatchHeaderFieldValueObject(10));
    paramACHBatchCache.orgDfiRTN = paramACHBatchInfo.getOdfiRTN();
  }
  
  private ACHBatchCache jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    ACHBatchCache localACHBatchCache = new ACHBatchCache();
    a(paramACHBatchInfo, localACHBatchCache, false);
    localACHBatchCache.batchDebitSum = BigDecimal.valueOf(((Long)paramACHBatchInfo.getBatchControlFieldValueObject(22)).longValue());
    localACHBatchCache.batchCreditSum = BigDecimal.valueOf(((Long)paramACHBatchInfo.getBatchControlFieldValueObject(23)).longValue());
    localACHBatchCache.batchHash = ((Long)paramACHBatchInfo.getBatchControlFieldValueObject(21)).longValue();
    localACHBatchCache.batchEntryCount = ((Integer)paramACHBatchInfo.getBatchControlFieldValueObject(20)).intValue();
    localACHBatchCache.recordCount = ACHRecord.getNumberOfRecordsInBatch(paramFFSConnectionHolder, paramACHBatchInfo.getBatchId(), paramBoolean);
    a(paramFFSConnectionHolder, localACHBatchCache, paramACHBatchInfo);
    localACHBatchCache.nonOffBatchCreditSum = BigDecimal.valueOf(paramACHBatchInfo.getNonOffBatchCreditSum());
    localACHBatchCache.nonOffBatchDebitSum = BigDecimal.valueOf(paramACHBatchInfo.getNonOffBatchDebitSum());
    localACHBatchCache.nonOffBatchEntryCount = paramACHBatchInfo.getNonOffBatchEntryCount();
    return localACHBatchCache;
  }
  
  public ACHBatchHist getACHBatchHistory(ACHBatchHist paramACHBatchHist)
    throws FFSException
  {
    FFSDebug.log("ACHBatchProcessor.getACHBatchHistory : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramACHBatchHist = ACHBatch.getACHBatchHistory(localFFSConnectionHolder, paramACHBatchHist, false);
      localFFSConnectionHolder.conn.commit();
      ACHBatchHist localACHBatchHist = paramACHBatchHist;
      return localACHBatchHist;
    }
    catch (Exception localException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str = "ACHBatchProcessor.getACHBatchHistory failed " + localException.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public ACHBatchHist getRecACHBatchHistory(ACHBatchHist paramACHBatchHist)
    throws FFSException
  {
    FFSDebug.log("RecACHBatchProcessor.getRecACHBatchHistory : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramACHBatchHist = ACHBatch.getACHBatchHistory(localFFSConnectionHolder, paramACHBatchHist, true);
      localFFSConnectionHolder.conn.commit();
      ACHBatchHist localACHBatchHist = paramACHBatchHist;
      return localACHBatchHist;
    }
    catch (Exception localException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      String str = "ACHBatchProcessor.getRecACHBatchHistory failed " + localException.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private boolean a(BPWFIInfo paramBPWFIInfo, RecACHBatchInfo paramRecACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.validateRecurringInfo";
    if (!validateRecBatchFrequency(paramBPWFIInfo, paramRecACHBatchInfo))
    {
      FFSDebug.log(str1, paramRecACHBatchInfo.getStatusMsg(), 0);
      return false;
    }
    String str2 = paramRecACHBatchInfo.getStartDate();
    String str3 = DBUtil.getACHPayday(paramRecACHBatchInfo.getFiId(), str2);
    int i = BPWUtil.getDateDBFormat(str3);
    String str4 = paramRecACHBatchInfo.getFrequency();
    int j = FFSUtil.getFreqInt(str4);
    try
    {
      int k = CommonProcessor.getEndDate(i, j, paramRecACHBatchInfo.getPmtsCount());
      paramRecACHBatchInfo.setEndDate(BPWUtil.getDateBeanFormat(k));
    }
    catch (Exception localException)
    {
      paramRecACHBatchInfo.setStatusCode(17202);
      String str5 = "Invalid values for recurring-batch. :" + i;
      paramRecACHBatchInfo.setStatusMsg(str5);
      FFSDebug.log(str1, str5, 0);
      return false;
    }
    paramRecACHBatchInfo.setStatusCode(0);
    paramRecACHBatchInfo.setStatusMsg("Success");
    return true;
  }
  
  private boolean jdMethod_if(ACHBatchInfo paramACHBatchInfo)
  {
    String str1 = paramACHBatchInfo.getDueDate();
    try
    {
      paramACHBatchInfo.setDtProcessed(DBUtil.getACHPayday(paramACHBatchInfo.getFiId(), str1));
      paramACHBatchInfo.setStatusCode(0);
      paramACHBatchInfo.setStatusMsg("Success");
    }
    catch (Exception localException)
    {
      paramACHBatchInfo.setStatusCode(17203);
      String str2 = "Invalid due date value for batch. :" + str1 + ". Exception: " + localException.getMessage();
      paramACHBatchInfo.setStatusMsg(str2);
      return false;
    }
    return true;
  }
  
  private boolean a(ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.validateDueOrStartDate: ";
    FFSDebug.log(str1 + " start", 6);
    String str2 = null;
    if (paramBoolean) {
      str2 = ((RecACHBatchInfo)paramACHBatchInfo).getStartDate();
    } else {
      str2 = paramACHBatchInfo.getDueDate();
    }
    if (!BPWUtil.checkDateBeanFormat(str2))
    {
      if (paramBoolean)
      {
        paramACHBatchInfo.setStatusCode(17201);
        paramACHBatchInfo.setStatusMsg("Invalid start date value for recurring-batch. :" + str2);
      }
      else
      {
        paramACHBatchInfo.setStatusCode(17203);
        paramACHBatchInfo.setStatusMsg("Invalid due date value for batch.: " + str2);
      }
      FFSDebug.log(str1, paramACHBatchInfo.getStatusMsg(), 0);
      return false;
    }
    int i = Integer.parseInt(str2);
    String str3 = FFSUtil.getDateString("yyyyMMdd");
    int j = Integer.parseInt(str3);
    if (i < j)
    {
      if (paramBoolean)
      {
        paramACHBatchInfo.setStatusCode(27006);
        paramACHBatchInfo.setStatusMsg("Recurring ACHBatch's StartDate is in the past : " + str2);
      }
      else
      {
        paramACHBatchInfo.setStatusCode(27006);
        paramACHBatchInfo.setStatusMsg("ACHBatch's DueDate is in the past : " + str2);
      }
      FFSDebug.log(str1, paramACHBatchInfo.getStatusMsg(), 0);
      return false;
    }
    String str4 = ACHBatch.getEffectiveDateFromBatch(paramACHBatchInfo);
    int k = Integer.parseInt(str4);
    FFSDebug.log(str1 + "effectiveDate =" + str4 + ", Due/StartDate= " + str2, 6);
    if (i > k)
    {
      if (paramBoolean)
      {
        paramACHBatchInfo.setStatusCode(27007);
        paramACHBatchInfo.setStatusMsg("Recurring ACHBatch's StartDate should not be later than its Effective Entry Date Start Date: " + i + " EffectiveEntryDate: " + k);
      }
      else
      {
        paramACHBatchInfo.setStatusCode(27007);
        paramACHBatchInfo.setStatusMsg("ACHBatch's DueDate should not be later than its Effective Entry Date Due Date: " + i + " EffectiveEntryDate: " + k);
      }
      FFSDebug.log(str1, paramACHBatchInfo.getStatusMsg(), 0);
      return false;
    }
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + " done", 6);
    return true;
  }
  
  private void jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.cancelSchedule: ";
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setStatusMsg("Success");
    String str2 = null;
    String str3 = paramACHBatchInfo.getBatchId();
    if (paramBoolean) {
      str2 = "RECACHBATCHTRN";
    } else {
      str2 = "ACHBATCHTRN";
    }
    ScheduleInfo.cancelSchedule(paramFFSConnectionHolder.conn, str2, str3);
    Object localObject2;
    if (paramBoolean == true)
    {
      localObject1 = ACHBatch.getUnprocessedSingleIds(paramFFSConnectionHolder, str3);
      if (localObject1 == null) {
        return;
      }
      for (int i = 0; i < localObject1.length; i++)
      {
        ScheduleInfo.cancelSchedule(paramFFSConnectionHolder.conn, "ACHBATCHTRN", localObject1[i]);
        localObject2 = ACHBatch.getACHBatchById(paramFFSConnectionHolder, localObject1[i], false);
        if (((ACHBatchInfo)localObject2).getStatusCode() == 0)
        {
          String str5 = jdMethod_try(paramFFSConnectionHolder, (ACHBatchInfo)localObject2, paramBoolean);
          if (str5.compareTo("LIMIT_REVERT_FAILED") == 0)
          {
            String str6 = str1 + ":Could not revert limit for Batch Id:" + localObject1[i];
            FFSDebug.log(str6, 0);
          }
          else
          {
            str5 = "CANCELEDON";
          }
          ACHBatch.updateACHBatchStatus((ACHBatchInfo)localObject2, str5, paramFFSConnectionHolder, false);
          RecSrvrTIDToSrvrTID.delete(paramFFSConnectionHolder, str3, localObject1[i], 1);
        }
      }
    }
    Object localObject1 = ACHBatch.getACHBatchById(paramFFSConnectionHolder, str3, false);
    String str4 = jdMethod_try(paramFFSConnectionHolder, (ACHBatchInfo)localObject1, paramBoolean);
    FFSDebug.log(str1, "status LimitCheck:" + str4, 6);
    if ("LIMIT_REVERT_FAILED".equals(str4))
    {
      FFSDebug.log(str1, " Limit Revert Failed.", 6);
      paramACHBatchInfo.setStatusCode(21050);
      localObject2 = BPWLocaleUtil.getMessage(21050, null, "TRANSFER_MESSAGE");
      paramACHBatchInfo.setStatusMsg((String)localObject2);
      return;
    }
  }
  
  public ACHBatchInfo createPrenoteBatchTX(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    HashMap localHashMap = new HashMap();
    ACHCompanyInfo localACHCompanyInfo = ACHCompany.getCompanyInfo(paramACHPayeeInfo.getCompId(), paramFFSConnectionHolder);
    CustomerInfo localCustomerInfo = Customer.getCustomerInfo(localACHCompanyInfo.getCustomerId(), paramFFSConnectionHolder, paramACHPayeeInfo);
    BPWFIInfo localBPWFIInfo = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, localCustomerInfo.fIID);
    localHashMap.put("BPW_FIINFO", localBPWFIInfo);
    localHashMap.put("BPW_CUSTOMER", localCustomerInfo);
    localHashMap.put("ACH_COMPANY", localACHCompanyInfo);
    return createPrenoteBatchTX(paramFFSConnectionHolder, paramACHPayeeInfo, localHashMap);
  }
  
  public ACHBatchInfo createPrenoteBatchTX(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str = "ACHBatchProcessor.createPrenoteBatch: ";
    FFSDebug.log(str + "start. PayeeId: " + paramACHPayeeInfo.getPayeeID(), 6);
    ACHBatchInfo localACHBatchInfo = null;
    if (("ACH_PRENOTE_DEMAND_CREDIT".equals(paramACHPayeeInfo.getPrenoteDemand())) || ("ACH_PRENOTE_DEMAND_DEBIT".equals(paramACHPayeeInfo.getPrenoteDemand())))
    {
      localACHBatchInfo = a(paramFFSConnectionHolder, paramACHPayeeInfo, paramHashMap);
    }
    else
    {
      paramACHPayeeInfo.setPrenoteDemand("ACH_PRENOTE_DEMAND_CREDIT");
      localACHBatchInfo = a(paramFFSConnectionHolder, paramACHPayeeInfo, paramHashMap);
      if (localACHBatchInfo.getStatusCode() != 0) {
        return localACHBatchInfo;
      }
      paramACHPayeeInfo.setPrenoteDemand("ACH_PRENOTE_DEMAND_DEBIT");
      localACHBatchInfo = a(paramFFSConnectionHolder, paramACHPayeeInfo, paramHashMap);
      paramACHPayeeInfo.setPrenoteDemand("ACH_PRENOTE_DEMAND_BOTH");
    }
    if ((localACHBatchInfo != null) && (localACHBatchInfo.getStatusCode() == 0))
    {
      paramACHPayeeInfo.setPrenoteSubmitDate(BPWUtil.getDateInNewFormat(localACHBatchInfo.getDueDate(), "yyyyMMdd", "yyyy/MM/dd"));
      ACHPayee.updateACHPayeePrenoteSubmitDate(paramFFSConnectionHolder, paramACHPayeeInfo);
    }
    return localACHBatchInfo;
  }
  
  private ACHBatchInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo, HashMap paramHashMap)
    throws FFSException
  {
    ACHBatchInfo localACHBatchInfo = new ACHBatchInfo();
    localACHBatchInfo.setBatchCategory("ACH_BATCH_PRENOTE");
    localACHBatchInfo.setCompId(paramACHPayeeInfo.getCompId());
    localACHBatchInfo.setClassCode(paramACHPayeeInfo.getPrenoteSecCode());
    localACHBatchInfo.setSubmittedBy(paramACHPayeeInfo.getSubmittedBy());
    localACHBatchInfo.setLogId(paramACHPayeeInfo.getLogId());
    localACHBatchInfo.setTotalBatchSize(1L);
    localACHBatchInfo.setBatchType("Current");
    localACHBatchInfo.setBatchBalanceType("UnbalancedBatch");
    ACHCompanyInfo localACHCompanyInfo = (ACHCompanyInfo)paramHashMap.get("ACH_COMPANY");
    BPWFIInfo localBPWFIInfo = (BPWFIInfo)paramHashMap.get("BPW_FIINFO");
    localACHBatchInfo.setFiId(localBPWFIInfo.getFIId());
    localACHBatchInfo.setOdfiId(localACHCompanyInfo.getODFIACHId());
    localACHBatchInfo.setOdfiRTN(localACHCompanyInfo.getODFIACHId8());
    ACHRecordInfo localACHRecordInfo1 = a(paramFFSConnectionHolder, paramACHPayeeInfo, localACHCompanyInfo, localACHBatchInfo);
    localACHBatchInfo.setBatchHeader(localACHRecordInfo1);
    ACHRecordInfo localACHRecordInfo2 = a(paramACHPayeeInfo, localACHCompanyInfo);
    localACHBatchInfo.setBatchControl(localACHRecordInfo2);
    ACHRecordInfo[] arrayOfACHRecordInfo = jdMethod_if(paramACHPayeeInfo);
    if (arrayOfACHRecordInfo == null)
    {
      localACHBatchInfo.setStatusCode(paramACHPayeeInfo.getStatusCode());
      localACHBatchInfo.setStatusMsg(paramACHPayeeInfo.getStatusMsg());
      return localACHBatchInfo;
    }
    localACHBatchInfo.setRecords(arrayOfACHRecordInfo);
    Hashtable localHashtable = new Hashtable();
    localHashtable.put("NAME", "Prenote Batch");
    localACHBatchInfo.setMemo(localHashtable);
    localACHBatchInfo.setAgentId(paramACHPayeeInfo.getAgentId());
    localACHBatchInfo.setAgentType(paramACHPayeeInfo.getAgentType());
    localACHBatchInfo.setAgentName(paramACHPayeeInfo.getAgentName());
    String str = FFSUtil.getDateString("yyyyMMdd");
    int i = Integer.parseInt(str);
    try
    {
      localACHBatchInfo.setDueDate(Integer.toString(SmartCalendar.getACHPayday(localBPWFIInfo.getFIId(), i)));
      localACHBatchInfo.setDtProcessed(localACHBatchInfo.getDueDate());
    }
    catch (Exception localException)
    {
      throw new FFSException(localException, "Failed to get business day of today: " + localException.toString());
    }
    a(localACHBatchInfo, paramFFSConnectionHolder, false, paramHashMap);
    return localACHBatchInfo;
  }
  
  private short jdMethod_int(String paramString)
  {
    if ("ACH_PRENOTE_DEMAND_CREDIT".equals(paramString)) {
      return 220;
    }
    if ("ACH_PRENOTE_DEMAND_DEBIT".equals(paramString)) {
      return 225;
    }
    return 220;
  }
  
  private short a(String paramString1, String paramString2)
  {
    if ("Checking".compareToIgnoreCase(paramString2) == 0)
    {
      if ("ACH_PRENOTE_DEMAND_CREDIT".compareTo(paramString1) == 0) {
        return 23;
      }
      return 28;
    }
    if ("Savings".compareToIgnoreCase(paramString2) == 0)
    {
      if ("ACH_PRENOTE_DEMAND_CREDIT".compareTo(paramString1) == 0) {
        return 33;
      }
      return 38;
    }
    if ("Ledger".compareToIgnoreCase(paramString2) == 0)
    {
      if ("ACH_PRENOTE_DEMAND_CREDIT".compareTo(paramString1) == 0) {
        return 43;
      }
      return 48;
    }
    if ("Loan".compareToIgnoreCase(paramString2) == 0)
    {
      if ("ACH_PRENOTE_DEMAND_CREDIT".compareTo(paramString1) == 0) {
        return 53;
      }
      return 55;
    }
    return 0;
  }
  
  private Object a(ACHPayeeInfo paramACHPayeeInfo)
  {
    Object localObject1 = null;
    String str = paramACHPayeeInfo.getPrenoteSecCode();
    if (str == null) {
      return null;
    }
    Object localObject2;
    if ("ACK".compareTo(str) == 0)
    {
      localObject2 = new TypeACKEntryDetailRecord();
      ((TypeACKEntryDetailRecord)localObject2).Receiving_Company_Name = paramACHPayeeInfo.getPayeeName();
      ((TypeACKEntryDetailRecord)localObject2).Discretionary_Data = "";
      localObject1 = localObject2;
    }
    else if ("ARC".compareTo(str) == 0)
    {
      localObject2 = new TypeARCEntryDetailRecord();
      ((TypeARCEntryDetailRecord)localObject2).Check_Serial_Number = paramACHPayeeInfo.getPayAcct();
      ((TypeARCEntryDetailRecord)localObject2).Individual_Name = paramACHPayeeInfo.getPayeeName();
      localObject1 = localObject2;
    }
    else if ("ATX".compareTo(str) == 0)
    {
      localObject2 = new TypeATXEntryDetailRecord();
      ((TypeATXEntryDetailRecord)localObject2).Receiving_Company_Identification_Number = paramACHPayeeInfo.getPayAcct();
      ((TypeATXEntryDetailRecord)localObject2).Reserved2 = "  ";
      localObject1 = localObject2;
    }
    else if ("CBR".compareTo(str) == 0)
    {
      localObject2 = new TypeCBREntryDetailRecord();
      ((TypeCBREntryDetailRecord)localObject2).Identification_Number = paramACHPayeeInfo.getPayAcct();
      ((TypeCBREntryDetailRecord)localObject2).Identification_NumberExists = true;
      ((TypeCBREntryDetailRecord)localObject2).Receiving_Company_Name = paramACHPayeeInfo.getPayeeName();
      localObject1 = localObject2;
    }
    else if ("CCD".compareTo(str) == 0)
    {
      localObject2 = new TypeCCDEntryDetailRecord();
      ((TypeCCDEntryDetailRecord)localObject2).Identification_Number = paramACHPayeeInfo.getPayAcct();
      ((TypeCCDEntryDetailRecord)localObject2).Identification_NumberExists = true;
      ((TypeCCDEntryDetailRecord)localObject2).Receiving_Company_Name = paramACHPayeeInfo.getPayeeName();
      localObject1 = localObject2;
    }
    else if ("CIE".compareTo(str) == 0)
    {
      localObject2 = new TypeCIEEntryDetailRecord();
      ((TypeCIEEntryDetailRecord)localObject2).Individual_Name = paramACHPayeeInfo.getPayeeName();
      ((TypeCIEEntryDetailRecord)localObject2).Individual_Identification_Number = paramACHPayeeInfo.getPayAcct();
      localObject1 = localObject2;
    }
    else if ("CTX".compareTo(str) == 0)
    {
      localObject2 = new TypeCTXEntryDetailRecord();
      ((TypeCTXEntryDetailRecord)localObject2).Identification_Number = paramACHPayeeInfo.getPayAcct();
      ((TypeCTXEntryDetailRecord)localObject2).Identification_NumberExists = true;
      ((TypeCTXEntryDetailRecord)localObject2).Receiving_Company_Identification_Number = paramACHPayeeInfo.getPayeeName();
      ((TypeCTXEntryDetailRecord)localObject2).Reserved2 = "  ";
      localObject1 = localObject2;
    }
    else if ("DNE".compareTo(str) == 0)
    {
      localObject2 = new TypeDNEEntryDetailRecord();
      ((TypeDNEEntryDetailRecord)localObject2).Individual_Identification_Number = paramACHPayeeInfo.getPayAcct();
      ((TypeDNEEntryDetailRecord)localObject2).Individual_Identification_NumberExists = true;
      ((TypeDNEEntryDetailRecord)localObject2).Individual_Name = paramACHPayeeInfo.getPayeeName();
      localObject1 = localObject2;
    }
    else if ("ENR".compareTo(str) == 0)
    {
      localObject2 = new TypeENREntryDetailRecord();
      localObject1 = localObject2;
    }
    else if ("MTE".compareTo(str) == 0)
    {
      localObject2 = new TypeMTEEntryDetailRecord();
      ((TypeMTEEntryDetailRecord)localObject2).Individual_Name = paramACHPayeeInfo.getPayeeName();
      ((TypeMTEEntryDetailRecord)localObject2).Individual_Identification_Number = paramACHPayeeInfo.getPayAcct();
      localObject1 = localObject2;
    }
    else if ("PBR".compareTo(str) == 0)
    {
      localObject2 = new TypePBREntryDetailRecord();
      ((TypePBREntryDetailRecord)localObject2).Individual_Identification_Number = paramACHPayeeInfo.getPayAcct();
      ((TypePBREntryDetailRecord)localObject2).Individual_Identification_NumberExists = true;
      ((TypePBREntryDetailRecord)localObject2).Individual_Name = paramACHPayeeInfo.getPayeeName();
      localObject1 = localObject2;
    }
    else if ("POP".compareTo(str) == 0)
    {
      localObject2 = new TypePOPEntryDetailRecord();
      localObject1 = localObject2;
    }
    else if ("POS".compareTo(str) == 0)
    {
      localObject2 = new TypePOSEntryDetailRecord();
      localObject1 = localObject2;
    }
    else if ("PPD".compareTo(str) == 0)
    {
      localObject2 = new TypePPDEntryDetailRecord();
      ((TypePPDEntryDetailRecord)localObject2).Individual_Name = paramACHPayeeInfo.getPayeeName();
      ((TypePPDEntryDetailRecord)localObject2).Individual_Identification_Number = paramACHPayeeInfo.getPayAcct();
      ((TypePPDEntryDetailRecord)localObject2).Individual_Identification_NumberExists = true;
      localObject1 = localObject2;
    }
    else if ("RCK".compareTo(str) == 0)
    {
      localObject2 = new TypeRCKEntryDetailRecord();
      ((TypeRCKEntryDetailRecord)localObject2).Check_Serial_Number = paramACHPayeeInfo.getPayAcct();
      ((TypeRCKEntryDetailRecord)localObject2).Individual_Name = paramACHPayeeInfo.getPayeeName();
      localObject1 = localObject2;
    }
    else if ("SHR".compareTo(str) == 0)
    {
      localObject2 = new TypeSHREntryDetailRecord();
      ((TypeSHREntryDetailRecord)localObject2).Card_Transaction_Type_Code = paramACHPayeeInfo.getCardTransCode();
      localObject1 = localObject2;
    }
    else if ("TEL".compareTo(str) == 0)
    {
      localObject2 = new TypeTELEntryDetailRecord();
      ((TypeTELEntryDetailRecord)localObject2).Individual_Identification_Number = paramACHPayeeInfo.getPayAcct();
      ((TypeTELEntryDetailRecord)localObject2).Individual_Identification_NumberExists = true;
      ((TypeTELEntryDetailRecord)localObject2).Individual_Name = paramACHPayeeInfo.getPayeeName();
      localObject1 = localObject2;
    }
    else if ("TRC".compareTo(str) == 0)
    {
      localObject2 = new TypeTRCEntryDetailRecord();
      ((TypeTRCEntryDetailRecord)localObject2).Process_Control_Field = "";
      ((TypeTRCEntryDetailRecord)localObject2).Item_Research_Number = "";
      ((TypeTRCEntryDetailRecord)localObject2).Item_Type_Indicator = "";
      localObject1 = localObject2;
    }
    else if ("TRX".compareTo(str) == 0)
    {
      localObject2 = new TypeTRXEntryDetailRecord();
      ((TypeTRXEntryDetailRecord)localObject2).Identification_Number = paramACHPayeeInfo.getPayAcct();
      ((TypeTRXEntryDetailRecord)localObject2).Identification_NumberExists = true;
      ((TypeTRXEntryDetailRecord)localObject2).Receiving_Company_Identification_Number = paramACHPayeeInfo.getPayeeName();
      ((TypeTRXEntryDetailRecord)localObject2).Reserved2 = "  ";
      localObject1 = localObject2;
    }
    else if ("WEB".compareTo(str) == 0)
    {
      localObject2 = new TypeWEBEntryDetailRecord();
      ((TypeWEBEntryDetailRecord)localObject2).Individual_Identification_Number = paramACHPayeeInfo.getPayAcct();
      ((TypeWEBEntryDetailRecord)localObject2).Individual_Identification_NumberExists = true;
      ((TypeWEBEntryDetailRecord)localObject2).Individual_Name = paramACHPayeeInfo.getPayeeName();
      ((TypeWEBEntryDetailRecord)localObject2).Payment_Type_Code = "";
      localObject1 = localObject2;
    }
    else if ("XCK".compareTo(str) == 0)
    {
      localObject2 = new TypeXCKEntryDetailRecord();
      ((TypeXCKEntryDetailRecord)localObject2).Check_Serial_Number = paramACHPayeeInfo.getPayAcct();
      ((TypeXCKEntryDetailRecord)localObject2).Process_Control_Field = "";
      ((TypeXCKEntryDetailRecord)localObject2).Item_Research_Number = "";
      localObject1 = localObject2;
    }
    return localObject1;
  }
  
  private ACHRecordInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo, ACHCompanyInfo paramACHCompanyInfo, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    String str = "ACHBatchProcessor.createPrenoteBatchHeader: ";
    FFSDebug.log(str + "start", 6);
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    localACHRecordInfo.setRecordType(5);
    TypeBatchHeaderRecord localTypeBatchHeaderRecord = new TypeBatchHeaderRecord();
    localTypeBatchHeaderRecord.Record_Type_Code = 5;
    localTypeBatchHeaderRecord.Service_Class_Code = jdMethod_int(paramACHPayeeInfo.getPrenoteDemand());
    if (paramACHCompanyInfo.getCompName().length() > 16) {
      localTypeBatchHeaderRecord.Company_Name = paramACHCompanyInfo.getCompName().substring(0, 16);
    } else {
      localTypeBatchHeaderRecord.Company_Name = paramACHCompanyInfo.getCompName();
    }
    if (paramACHCompanyInfo.getCompName().length() > 20) {
      localTypeBatchHeaderRecord.Company_Discretionary_Data = paramACHCompanyInfo.getCompName().substring(0, 20);
    } else {
      localTypeBatchHeaderRecord.Company_Discretionary_Data = paramACHCompanyInfo.getCompName();
    }
    if (paramACHCompanyInfo.getCompACHId().length() > 10) {
      localTypeBatchHeaderRecord.Company_Identification = paramACHCompanyInfo.getCompACHId().substring(0, 10);
    } else {
      localTypeBatchHeaderRecord.Company_Identification = paramACHCompanyInfo.getCompACHId();
    }
    localTypeBatchHeaderRecord.Standard_Entry_Class_Code = paramACHPayeeInfo.getPrenoteSecCode();
    localTypeBatchHeaderRecord.Company_Entry_Description = "PRENOTE";
    localTypeBatchHeaderRecord.Company_Descriptive_Date = FFSUtil.getDateString("MM yy");
    localTypeBatchHeaderRecord.Company_Descriptive_DateExists = true;
    localTypeBatchHeaderRecord.Effective_Entry_Date = Integer.toString(jdMethod_for(paramFFSConnectionHolder, paramACHBatchInfo));
    localTypeBatchHeaderRecord.Effective_Entry_Date = localTypeBatchHeaderRecord.Effective_Entry_Date.substring(2);
    localTypeBatchHeaderRecord.Originator_Status_Code = "1";
    localTypeBatchHeaderRecord.Originating_DFI_Identification = paramACHCompanyInfo.getODFIACHId8();
    localACHRecordInfo.setRecord(localTypeBatchHeaderRecord);
    FFSDebug.log(str + "done.", 6);
    return localACHRecordInfo;
  }
  
  private ACHRecordInfo a(ACHPayeeInfo paramACHPayeeInfo, ACHCompanyInfo paramACHCompanyInfo)
  {
    String str = "ACHBatchProcessor.createPrenoteBatchControl: ";
    FFSDebug.log(str + "start", 6);
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    TypeBatchControlRecord localTypeBatchControlRecord = new TypeBatchControlRecord();
    localTypeBatchControlRecord.Record_Type_Code = 8;
    localTypeBatchControlRecord.Message_Authentication_Code = MacGenerator.generateMac(paramACHCompanyInfo.getODFIACHId());
    localTypeBatchControlRecord.Message_Authentication_CodeExists = true;
    localACHRecordInfo.setRecordType(8);
    localACHRecordInfo.setRecord(localTypeBatchControlRecord);
    FFSDebug.log(str + "done", 6);
    return localACHRecordInfo;
  }
  
  private ACHRecordInfo[] jdMethod_if(ACHPayeeInfo paramACHPayeeInfo)
  {
    String str = "ACHBatchProcessor.createPrenoteRecords: ";
    FFSDebug.log(str + "start", 6);
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    localACHRecordInfo.setPayeeId(paramACHPayeeInfo.getPayeeID());
    localACHRecordInfo.setRecordType(6);
    Object localObject = a(paramACHPayeeInfo);
    if (localObject == null)
    {
      paramACHPayeeInfo.setStatusCode(23550);
      paramACHPayeeInfo.setStatusMsg("Unknown SEC Code" + paramACHPayeeInfo.getPrenoteSecCode());
      return null;
    }
    localACHRecordInfo.setRecord(localObject);
    localACHRecordInfo.setFieldValueObject("Addenda_Record_Indicator", new Short((short)0));
    localACHRecordInfo.setFieldValueObject("Amount", new Long(0L));
    localACHRecordInfo.setFieldValueObject("Record_Type_Code", new Short((short)6));
    localACHRecordInfo.setFieldValueObject("Transaction_Code", new Short(a(paramACHPayeeInfo.getPrenoteDemand(), paramACHPayeeInfo.getBankAcctType())));
    localACHRecordInfo.setFieldValueObject("Check_Digit", new Short((short)0));
    localACHRecordInfo.setFieldValueObject("DFI_Account_Number", paramACHPayeeInfo.getDFIAccountNumber(paramACHPayeeInfo.getPrenoteSecCode()));
    localACHRecordInfo.setFieldValueObject("Receiving_DFI_Identification", paramACHPayeeInfo.getBankRTN8());
    localACHRecordInfo.setClassCode(paramACHPayeeInfo.getPrenoteSecCode());
    ACHRecordInfo[] arrayOfACHRecordInfo = new ACHRecordInfo[1];
    arrayOfACHRecordInfo[0] = localACHRecordInfo;
    FFSDebug.log(str + "done.", 6);
    return arrayOfACHRecordInfo;
  }
  
  public ACHBatchInfo cancelPrenoteBatchTX(FFSConnectionHolder paramFFSConnectionHolder, ACHPayeeInfo paramACHPayeeInfo)
    throws FFSException
  {
    String str = "ACHBatchProcessor.cancelPrenoteBatchTX: ";
    FFSDebug.log(str + "start. PayeeId: " + paramACHPayeeInfo.getPayeeID(), 6);
    ACHBatchInfo[] arrayOfACHBatchInfo = ACHBatch.getActiveACHBatchesByPayeeId(paramFFSConnectionHolder, paramACHPayeeInfo.getPayeeID(), false);
    ACHBatchInfo localACHBatchInfo = new ACHBatchInfo();
    localACHBatchInfo.setStatusCode(0);
    localACHBatchInfo.setStatusMsg("Success");
    if (arrayOfACHBatchInfo != null) {
      for (int i = 0; i < arrayOfACHBatchInfo.length; i++) {
        if ((arrayOfACHBatchInfo[i] != null) && (arrayOfACHBatchInfo[i].getBatchCategory().equals("ACH_BATCH_PRENOTE"))) {
          localACHBatchInfo = jdMethod_if(paramFFSConnectionHolder, arrayOfACHBatchInfo[i], false);
        }
      }
    }
    paramACHPayeeInfo.setPrenoteSubmitDate(null);
    ACHPayee.updateACHPayeePrenoteSubmitDate(paramFFSConnectionHolder, paramACHPayeeInfo);
    FFSDebug.log(str + "Done.", 6);
    return localACHBatchInfo;
  }
  
  private ACHBatchInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, HashMap paramHashMap, String paramString)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.processPrenoteBatchesTX: ";
    FFSDebug.log(str1 + "start, doAction:" + paramString, 6);
    int i = 0;
    ACHRecordInfo[] arrayOfACHRecordInfo = paramACHBatchInfo.getRecords();
    if (arrayOfACHRecordInfo != null) {
      i = arrayOfACHRecordInfo.length;
    }
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setStatusMsg("Success");
    for (int j = 0; j < i; j++)
    {
      ACHRecordInfo localACHRecordInfo = arrayOfACHRecordInfo[j];
      if ((localACHRecordInfo == null) || (localACHRecordInfo.getAction() == null))
      {
        FFSDebug.log(str1 + "No value or action found. Skipping detail record:" + (j + 1), 6);
      }
      else
      {
        FFSDebug.log(str1 + "Record id:" + localACHRecordInfo.getRecordId(), 6);
        FFSDebug.log(str1 + "payee id:" + localACHRecordInfo.getPayeeId(), 6);
        FFSDebug.log(str1 + "record action:" + localACHRecordInfo.getAction(), 6);
        String str2 = localACHRecordInfo.getAction();
        if ((paramString == null) || (paramString.indexOf(str2) != -1))
        {
          FFSDebug.log(str1 + "Check acction: " + str2, 6);
          ACHPayeeInfo localACHPayeeInfo;
          Object localObject;
          if (str2.compareTo("add") == 0)
          {
            localACHPayeeInfo = localACHRecordInfo.getPayeeAggregateInfo();
            if ((localACHPayeeInfo != null) && (localACHPayeeInfo.getDoPrenote() == true))
            {
              localObject = createPrenoteBatchTX(paramFFSConnectionHolder, localACHPayeeInfo, paramHashMap);
              if (((ACHBatchInfo)localObject).getStatusCode() != 0)
              {
                paramACHBatchInfo.setStatusCode(((ACHBatchInfo)localObject).getStatusCode());
                paramACHBatchInfo.setStatusMsg(((ACHBatchInfo)localObject).getStatusMsg() + ". Failed to create prenote batch for new payee: " + localACHPayeeInfo.getPayeeID());
                break;
              }
            }
          }
          else if (str2.compareTo("mod") == 0)
          {
            localACHPayeeInfo = ACHPayee.getACHPayeeInfo(paramFFSConnectionHolder, localACHRecordInfo.getPayeeId());
            if ((localACHPayeeInfo.getStatusCode() == 0) && (localACHPayeeInfo.getManagedPayee().equals("N")))
            {
              localObject = localACHRecordInfo.getPayeeAggregateInfo();
              if ((localObject != null) && (!localACHPayeeInfo.comparePrenoteInfo((ACHPayeeInfo)localObject)))
              {
                cancelPrenoteBatchTX(paramFFSConnectionHolder, localACHPayeeInfo);
                if (((ACHPayeeInfo)localObject).getDoPrenote() == true)
                {
                  ACHBatchInfo localACHBatchInfo = createPrenoteBatchTX(paramFFSConnectionHolder, (ACHPayeeInfo)localObject, paramHashMap);
                  if (localACHBatchInfo.getStatusCode() != 0)
                  {
                    paramACHBatchInfo.setStatusCode(localACHBatchInfo.getStatusCode());
                    paramACHBatchInfo.setStatusMsg(localACHBatchInfo.getStatusMsg() + ". Failed to create prenote batch for new payee: " + ((ACHPayeeInfo)localObject).getPayeeID());
                    break;
                  }
                }
              }
            }
          }
          else if (str2.compareTo("del") == 0)
          {
            localACHPayeeInfo = ACHPayee.getACHPayeeInfo(paramFFSConnectionHolder, localACHRecordInfo.getPayeeId());
            if ((localACHPayeeInfo.getStatusCode() == 0) && (localACHPayeeInfo.getManagedPayee().equals("N"))) {
              cancelPrenoteBatchTX(paramFFSConnectionHolder, localACHPayeeInfo);
            }
          }
          else
          {
            FFSDebug.log(str1 + "Unknown action:" + str2 + ".", 6);
          }
        }
      }
    }
    FFSDebug.log(str1 + "done", 6);
    return paramACHBatchInfo;
  }
  
  private void a(ACHRecordInfo paramACHRecordInfo, ACHPayeeInfo paramACHPayeeInfo)
  {
    int i = paramACHRecordInfo.getFieldValueShort("Transaction_Code");
    String str;
    switch (i)
    {
    case 22: 
    case 32: 
    case 42: 
    case 52: 
      if (paramACHPayeeInfo.getPrenoteCreditStatus().equals("Failed"))
      {
        paramACHRecordInfo.setStatusCode(23570);
        str = "Prenote credit status of a ACH payee is failed. PayeeId = " + paramACHPayeeInfo.getPayeeID();
        paramACHRecordInfo.setStatusMsg(str);
      }
      break;
    case 27: 
    case 37: 
    case 47: 
    case 55: 
      if (paramACHPayeeInfo.getPrenoteDebitStatus().equals("Failed"))
      {
        paramACHRecordInfo.setStatusCode(23580);
        str = "Prenote debit status of a ACH payee is failed. PayeeId = " + paramACHPayeeInfo.getPayeeID();
        paramACHRecordInfo.setStatusMsg(str);
      }
      break;
    case 23: 
    case 28: 
    case 33: 
    case 38: 
    case 43: 
    case 48: 
    case 53: 
      break;
    }
  }
  
  private ACHRecordInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHRecordInfo paramACHRecordInfo, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.checkPayeeForNewRecordTX: ";
    paramACHRecordInfo.setStatusCode(0);
    paramACHRecordInfo.setStatusMsg("Success");
    Object localObject1 = null;
    if (paramACHRecordInfo.getPayeeId() != null)
    {
      localObject1 = ACHPayee.getACHPayeeInfo(paramFFSConnectionHolder, paramACHRecordInfo.getPayeeId());
      if (((ACHPayeeInfo)localObject1).getStatusCode() != 0)
      {
        paramACHRecordInfo.setStatusCode(((ACHPayeeInfo)localObject1).getStatusCode());
        localObject2 = ((ACHPayeeInfo)localObject1).getStatusMsg();
        paramACHRecordInfo.setStatusMsg((String)localObject2);
        FFSDebug.log(str1 + (String)localObject2, 0);
        return paramACHRecordInfo;
      }
      if (("Company".equals(((ACHPayeeInfo)localObject1).getPayeeGroup())) && (((ACHPayeeInfo)localObject1).getCompId().compareTo(paramACHBatchInfo.getCompId()) != 0))
      {
        paramACHRecordInfo.setStatusCode(23480);
        localObject2 = "Invalid information in ACHRecordInfo: ACHPayeeInfo.CompID = " + ((ACHPayeeInfo)localObject1).getCompId() + "; " + "ACHBatchCache.CompID = " + paramACHBatchInfo.getCompId();
        paramACHRecordInfo.setStatusMsg((String)localObject2);
        FFSDebug.log(str1 + (String)localObject2, 0);
        return paramACHRecordInfo;
      }
      if (((ACHPayeeInfo)localObject1).getManagedPayee().compareTo("N") == 0)
      {
        localObject2 = paramACHBatchInfo.getBatchCategory();
        if ("ACH_BATCH_PRENOTE".compareTo((String)localObject2) != 0)
        {
          paramACHRecordInfo.setStatusCode(23520);
          paramACHRecordInfo.setStatusMsg("Can't add a new ACH Batch for an unmanaged payee: Unmanaged payee already has an ACH batch");
          return paramACHRecordInfo;
        }
      }
      a(paramACHRecordInfo, (ACHPayeeInfo)localObject1);
      if (paramACHRecordInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + paramACHRecordInfo.getStatusMsg(), 0);
        return paramACHRecordInfo;
      }
      paramACHRecordInfo.setPayeeAggregateInfo((ACHPayeeInfo)localObject1);
      paramACHRecordInfo.setPayeeInfoToMBRecord();
      paramACHRecordInfo.setPayeeAggregateInfo(null);
    }
    else
    {
      localObject2 = null;
      Object localObject3;
      if (paramACHRecordInfo.getPayeeAggregateInfo() != null)
      {
        localObject2 = paramACHRecordInfo.getPayeeAggregateInfo();
        if (((ACHPayeeInfo)localObject2).getCompId().compareTo(paramACHBatchInfo.getCompId()) != 0)
        {
          paramACHRecordInfo.setStatusCode(23480);
          localObject3 = "Invalid information in ACHRecordInfo: ACHPayeeInfo.CompID = " + ((ACHPayeeInfo)localObject2).getCompId() + "; " + "ACHBatchCache.CompID = " + paramACHBatchInfo.getCompId();
          paramACHRecordInfo.setStatusMsg((String)localObject3);
          FFSDebug.log(str1 + (String)localObject3, 0);
          return paramACHRecordInfo;
        }
        ((ACHPayeeInfo)localObject2).setCheckDigit(BPWUtil.calculateCheckDigit(((ACHPayeeInfo)localObject2).getBankRTN8()));
        paramACHRecordInfo.setPayeeInfoToMBRecord();
        if (paramBoolean2 == true)
        {
          localObject3 = new ACHPayeeProcessor();
          localObject2 = ((ACHPayeeProcessor)localObject3).addACHPayeeOnlyTX(paramFFSConnectionHolder, (ACHPayeeInfo)localObject2);
          if (((ACHPayeeInfo)localObject2).getStatusCode() != 0)
          {
            paramACHRecordInfo.setStatusCode(((ACHPayeeInfo)localObject2).getStatusCode());
            String str2 = ". Failed to create free formed payee!";
            if (((ACHPayeeInfo)localObject2).getManagedPayee().equals("Y") == true) {
              str2 = ". Failed to create managed payee!";
            }
            paramACHRecordInfo.setStatusMsg(((ACHPayeeInfo)localObject2).getStatusMsg() + str2);
          }
          else
          {
            paramACHRecordInfo.setPayeeId(((ACHPayeeInfo)localObject2).getPayeeID());
            paramACHRecordInfo.setPayeeAggregateInfo((ACHPayeeInfo)localObject2);
            FFSDebug.log(str1 + "Payee ID: " + ((ACHPayeeInfo)localObject2).getPayeeID(), 6);
          }
        }
        localObject1 = localObject2;
      }
      else if (paramACHRecordInfo.getRecord() == null)
      {
        paramACHRecordInfo.setStatusCode(23480);
        localObject3 = "Invalid information in ACHRecordInfo payeeId, payeeInfo and record are null";
        paramACHRecordInfo.setStatusMsg((String)localObject3);
        FFSDebug.log(str1 + (String)localObject3, 0);
        return paramACHRecordInfo;
      }
    }
    Object localObject2 = ACHAdapterUtil.getProperty("bpw.ach.payee.enforce.prenote.period", "N");
    if ((((String)localObject2).compareTo("Y") == 0) && (!a(paramFFSConnectionHolder, paramACHBatchInfo, (ACHPayeeInfo)localObject1, paramBoolean1)))
    {
      paramACHRecordInfo.setStatusCode(23680);
      paramACHRecordInfo.setStatusMsg("The batch's date must be 6 days or later than the submited date of a prenote of a payee in the batch");
    }
    return paramACHRecordInfo;
  }
  
  private ACHRecordInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ACHRecordInfo paramACHRecordInfo, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.checkPayeeForExistingRecordTX: ";
    if (paramACHRecordInfo.getPayeeId() == null)
    {
      localObject1 = "PayeeId in ACHRecord  contains null ";
      FFSDebug.log(str1 + (String)localObject1, 6);
      return paramACHRecordInfo;
    }
    paramACHRecordInfo.setStatusCode(0);
    paramACHRecordInfo.setStatusMsg("Success");
    Object localObject1 = paramACHRecordInfo.getPayeeAggregateInfo();
    Object localObject2;
    Object localObject3;
    if (localObject1 != null)
    {
      if (((ACHPayeeInfo)localObject1).getPayeeID().compareTo(paramACHRecordInfo.getPayeeId()) != 0)
      {
        paramACHRecordInfo.setStatusCode(23480);
        localObject2 = "Invalid information in ACHRecordInfopayeeIds are not the same in ACHRecordInfo and ACHRecordInfo.payeeInfo";
        paramACHRecordInfo.setStatusMsg((String)localObject2);
        FFSDebug.log(str1 + (String)localObject2, 0);
        return paramACHRecordInfo;
      }
      localObject2 = ACHPayee.getACHPayeeInfo(paramFFSConnectionHolder, paramACHRecordInfo.getPayeeId());
      if (((ACHPayeeInfo)localObject2).getStatusCode() == 0)
      {
        if ("N".equals(((ACHPayeeInfo)localObject2).getManagedPayee()) == true)
        {
          if (((ACHPayeeInfo)localObject1).getCompId().compareTo(paramACHBatchInfo.getCompId()) != 0)
          {
            paramACHRecordInfo.setStatusCode(23480);
            localObject3 = "Invalid information in ACHRecordInfo: PayeeId = " + ((ACHPayeeInfo)localObject1).getPayeeID() + "; " + "ACHPayeeInfo.CompID = " + ((ACHPayeeInfo)localObject1).getCompId() + "; " + "ACHBatchCache.CompID = " + paramACHBatchInfo.getCompId();
            paramACHRecordInfo.setStatusMsg((String)localObject3);
            FFSDebug.log((String)localObject3, 0);
            return paramACHRecordInfo;
          }
          localObject3 = new ACHPayeeProcessor();
          ((ACHPayeeInfo)localObject1).setSubmittedBy(paramACHBatchInfo.getSubmittedBy());
          localObject1 = ((ACHPayeeProcessor)localObject3).modACHPayeeInfoOnlyTX(paramFFSConnectionHolder, (ACHPayeeInfo)localObject1);
          if (((ACHPayeeInfo)localObject1).getStatusCode() != 0)
          {
            String str2 = "Failed to check payee for an existing record: " + ((ACHPayeeInfo)localObject1).getStatusMsg();
            FFSDebug.log(str1 + str2, 0);
            paramACHRecordInfo.setStatusCode(((ACHPayeeInfo)localObject1).getStatusCode());
            paramACHRecordInfo.setStatusMsg(str2);
            return paramACHRecordInfo;
          }
        }
      }
      else
      {
        localObject3 = "Failed to get payee for an existing record: " + ((ACHPayeeInfo)localObject2).getStatusMsg();
        FFSDebug.log(str1 + (String)localObject3, 0);
        paramACHRecordInfo.setStatusCode(((ACHPayeeInfo)localObject2).getStatusCode());
        paramACHRecordInfo.setStatusMsg((String)localObject3);
        return paramACHRecordInfo;
      }
      ((ACHPayeeInfo)localObject1).setCheckDigit(BPWUtil.calculateCheckDigit(((ACHPayeeInfo)localObject1).getBankRTN8()));
      paramACHRecordInfo.setPayeeInfoToMBRecord();
    }
    else
    {
      localObject2 = ACHPayee.getACHPayeeInfo(paramFFSConnectionHolder, paramACHRecordInfo.getPayeeId());
      if (((ACHPayeeInfo)localObject2).getStatusCode() == 0)
      {
        paramACHRecordInfo.setPayeeAggregateInfo((ACHPayeeInfo)localObject2);
        paramACHRecordInfo.setPayeeInfoToMBRecord();
      }
      else
      {
        localObject3 = "Failed to get payee for an existing record: " + ((ACHPayeeInfo)localObject2).getStatusMsg();
        FFSDebug.log(str1 + (String)localObject3, 0);
        paramACHRecordInfo.setStatusCode(((ACHPayeeInfo)localObject2).getStatusCode());
        paramACHRecordInfo.setStatusMsg((String)localObject3);
        return paramACHRecordInfo;
      }
      localObject1 = localObject2;
    }
    return paramACHRecordInfo;
  }
  
  private ACHRecordInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHRecordInfo paramACHRecordInfo, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.checkPayeeForCancelingRecordTX: ";
    if (paramACHRecordInfo.getPayeeId() == null)
    {
      localObject = "PayeeId in ACHRecord  contains null ";
      FFSDebug.log(str1 + (String)localObject, 6);
      return paramACHRecordInfo;
    }
    Object localObject = ACHPayee.getACHPayeeInfo(paramFFSConnectionHolder, paramACHRecordInfo.getPayeeId());
    String str2;
    if (((ACHPayeeInfo)localObject).getStatusCode() == 0)
    {
      if (((ACHPayeeInfo)localObject).getManagedPayee().equals("N"))
      {
        str2 = paramACHBatchInfo.getBatchCategory();
        if (!"ACH_BATCH_PRENOTE".equals(str2))
        {
          ACHPayeeProcessor localACHPayeeProcessor = new ACHPayeeProcessor();
          ((ACHPayeeInfo)localObject).setSubmittedBy(paramACHBatchInfo.getSubmittedBy());
          localObject = localACHPayeeProcessor.canACHPayeeInfoOnlyTX(paramFFSConnectionHolder, (ACHPayeeInfo)localObject);
          if (((ACHPayeeInfo)localObject).getStatusCode() != 0)
          {
            String str3 = "Failed to cancel payee in recordInfo: " + ((ACHPayeeInfo)localObject).getStatusCode();
            FFSDebug.log(str1 + str3, 0);
            return paramACHRecordInfo;
          }
        }
      }
    }
    else
    {
      str2 = "Failed to get payee for an canceling record: " + ((ACHPayeeInfo)localObject).getStatusMsg();
      FFSDebug.log(str1 + str2, 0);
      paramACHRecordInfo.setStatusCode(((ACHPayeeInfo)localObject).getStatusCode());
      paramACHRecordInfo.setStatusMsg(str2);
      return paramACHRecordInfo;
    }
    return paramACHRecordInfo;
  }
  
  private ACHBatchInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.setFIIdByCompId ";
    FFSDebug.log(str1 + "start", 6);
    String str2 = paramACHBatchInfo.getFiId();
    if (paramACHBatchInfo.getFiId() == null) {
      try
      {
        str2 = ACHCompany.getFiIdbyCompId(paramFFSConnectionHolder, paramACHBatchInfo.getCompId());
        if (str2 == null)
        {
          String str3 = "FIId does not exist :" + paramACHBatchInfo.getCompId();
          paramACHBatchInfo.setStatusCode(23170);
          paramACHBatchInfo.setStatusMsg(str3);
        }
        else
        {
          paramACHBatchInfo.setFiId(str2);
          paramACHBatchInfo.setStatusCode(0);
        }
      }
      catch (FFSException localFFSException)
      {
        String str4 = "*** " + str1 + " failed: ";
        String str5 = null;
        str5 = FFSDebug.stackTrace(localFFSException);
        FFSDebug.log(str4, str5, 0);
        throw localFFSException;
      }
    } else {
      FFSDebug.log(str1 + " fiId is already set.", 6);
    }
    FFSDebug.log(str1 + "done. fiId: " + str2, 6);
    return paramACHBatchInfo;
  }
  
  private boolean a(HashSet paramHashSet, ACHBatchInfo paramACHBatchInfo, String paramString)
  {
    String str = "ACHBatchProcessor.checkBatchOffsetAccount:";
    FFSDebug.log(str + " starts ", 6);
    if (paramString == null) {
      return true;
    }
    if (!paramHashSet.contains(paramString))
    {
      paramACHBatchInfo.setStatusCode(23800);
      paramACHBatchInfo.setStatusMsg("The specified ACHCompany, offset account ID do not match");
      FFSDebug.log(str + " Offset Account not found: CompId = " + paramACHBatchInfo.getCompId() + "; AcctId = " + paramString, 0);
      return false;
    }
    FFSDebug.log(str + " done.", 6);
    return true;
  }
  
  public boolean validateNumberOffsetAccount(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchCache paramACHBatchCache, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.validateNumberOffsetAccount: ";
    FFSDebug.log(str1 + "starts.", 6);
    String str2 = paramACHBatchCache.batchBalanceType;
    if (str2 != null)
    {
      int i;
      if (str2.equals("EntryBalancedBatch"))
      {
        if (paramACHBatchCache.batchCreditSum.compareTo(paramACHBatchCache.batchDebitSum) != 0)
        {
          paramACHBatchInfo.setStatusCode(23830);
          paramACHBatchInfo.setStatusMsg("Invalid batch balanced batch, total credit does not match total debit.");
          FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg() + ". Total credit sum: " + paramACHBatchCache.batchCreditSum + ". Total debit sum: " + paramACHBatchCache.batchDebitSum, 0);
          return false;
        }
        i = ACHRecord.getNumberDistinctPairIdsOfBatch(paramFFSConnectionHolder, paramACHBatchInfo.getBatchId(), paramBoolean);
        if (i * 2 != paramACHBatchCache.recordCount)
        {
          paramACHBatchInfo.setStatusCode(23810);
          paramACHBatchInfo.setStatusMsg("Invalid entry balanced batch, number of entry balanced records must be half of the total records in batch");
          FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg() + ". Total records: " + paramACHBatchCache.recordCount + ". Number of balanced entry records: " + i, 0);
          return false;
        }
      }
      else if (str2.equals("BatchBalancedBatch"))
      {
        if (paramACHBatchCache.batchCreditSum.compareTo(paramACHBatchCache.batchDebitSum) != 0)
        {
          paramACHBatchInfo.setStatusCode(23830);
          paramACHBatchInfo.setStatusMsg("Invalid batch balanced batch, total credit does not match total debit.");
          FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg() + ". Total credit sum: " + paramACHBatchCache.batchCreditSum + ". Total debit sum: " + paramACHBatchCache.batchDebitSum, 0);
          return false;
        }
        i = ACHRecord.getNumberOfRecordsInBatchByRecordCategory(paramFFSConnectionHolder, paramACHBatchInfo.getBatchId(), paramBoolean, "BB");
        if (i > 1)
        {
          paramACHBatchInfo.setStatusCode(23790);
          paramACHBatchInfo.setStatusMsg("Invalid batch balanced batch, there must be only one or zero batch balanced record");
          FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg() + ". Total records: " + paramACHBatchCache.recordCount + ". Number of balanced records: " + i, 0);
          return false;
        }
      }
    }
    FFSDebug.log(str1 + "done.", 6);
    return true;
  }
  
  private void jdMethod_if(ACHBatchCache paramACHBatchCache, ACHBatchInfo paramACHBatchInfo)
  {
    a(paramACHBatchInfo, paramACHBatchCache, false);
  }
  
  private boolean a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo1, ACHBatchInfo paramACHBatchInfo2, boolean paramBoolean)
  {
    String str1 = "ACHBatchProcessor.checkPrenoteSixBankingDayDelayForMod: ";
    String str2 = null;
    String str3 = null;
    if (!paramBoolean)
    {
      str2 = paramACHBatchInfo2.getDtProcessed();
      str3 = paramACHBatchInfo1.getDtProcessed();
    }
    else
    {
      str2 = ((RecACHBatchInfo)paramACHBatchInfo2).getStartDate();
      str3 = ((RecACHBatchInfo)paramACHBatchInfo1).getStartDate();
    }
    FFSDebug.log(str1 + "New date of batch: " + str3 + ". Old date of batch: " + str2, 6);
    if ((str2 == null) || (str3 == null))
    {
      if (!paramBoolean)
      {
        paramACHBatchInfo1.setStatusCode(23850);
        paramACHBatchInfo1.setStatusMsg("ACHBatch contains null DtProcessed");
      }
      else
      {
        paramACHBatchInfo1.setStatusCode(23860);
        paramACHBatchInfo1.setStatusMsg("Recurring batch contains null start date");
      }
      return false;
    }
    if (str2.compareTo(str3) > 0) {
      return jdMethod_for(paramFFSConnectionHolder, paramACHBatchInfo1, paramBoolean);
    }
    return true;
  }
  
  private boolean jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
  {
    String str1 = "ACHBatchProcessor.checkPrenoteSixBankingDayDelay: ";
    int i = 0;
    try
    {
      if (!paramBoolean) {
        i = Integer.parseInt(paramACHBatchInfo.getDueDate());
      } else {
        i = Integer.parseInt(((RecACHBatchInfo)paramACHBatchInfo).getStartDate());
      }
      for (int j = 0; j < 6; j++) {
        i = SmartCalendar.getACHBusinessDay(paramACHBatchInfo.getFiId(), i, false);
      }
      String str2 = Integer.toString(i);
      str2 = FFSUtil.convertDateFormat("yyyyMMdd", "yyyy/MM/dd", str2);
      int k = ACHRecord.getNumberOfRecordsInBatchWithLaterPrenotePayeeSubmittedDate(paramFFSConnectionHolder, paramACHBatchInfo.getBatchId(), str2, paramBoolean);
      if (k > 0)
      {
        String str4 = "The batch's date must be 6 days or later than the submited date of a prenote of a payee in the batch. The batch's date: " + paramACHBatchInfo.getDtProcessed();
        paramACHBatchInfo.setStatusCode(23680);
        paramACHBatchInfo.setStatusMsg(str4);
        FFSDebug.log(str1 + str4, 0);
        return false;
      }
    }
    catch (Exception localException)
    {
      String str3 = "Failed to check six banking day delay for the batch batchId: " + paramACHBatchInfo.getBatchId() + ". " + localException.toString();
      FFSDebug.log(str1 + str3, 0);
      paramACHBatchInfo.setStatusCode(23840);
      paramACHBatchInfo.setStatusMsg(str3);
      return false;
    }
    return true;
  }
  
  private boolean a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, ACHPayeeInfo paramACHPayeeInfo, boolean paramBoolean)
  {
    String str1 = "ACHBatchProcessor.checkBatchDateWithMaturedPrenoteDate:";
    if ((paramACHPayeeInfo != null) && (paramACHPayeeInfo.getDoPrenote() == true) && (paramACHBatchInfo.getBatchCategory().compareTo("ACH_BATCH_PRENOTE") != 0))
    {
      String str2 = paramACHPayeeInfo.getPrenoteMaturedDate();
      if (str2 == null) {
        try
        {
          str2 = ACHPayee.getPrenoteMaturedDate(paramFFSConnectionHolder, paramACHPayeeInfo);
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + " Failed to get prenote matured date of payee " + paramACHPayeeInfo.getPayeeID() + " : " + localException.toString(), 0);
          return false;
        }
      }
      String str3 = paramACHBatchInfo.getDueDate();
      if (paramBoolean) {
        str3 = ((RecACHBatchInfo)paramACHBatchInfo).getStartDate();
      }
      FFSDebug.log(str1 + " Prenote matured date: " + str2 + "; Batch DueDate/StartDate: " + str3, 6);
      if (str2.compareTo(str3) > 0) {
        return false;
      }
    }
    return true;
  }
  
  private boolean a(ACHBatchInfo paramACHBatchInfo)
  {
    String str1 = paramACHBatchInfo.getLogId();
    if (str1 == null)
    {
      paramACHBatchInfo.setStatusCode(23870);
      paramACHBatchInfo.setStatusMsg("LogID field is null");
      return false;
    }
    if (str1.length() > 32)
    {
      paramACHBatchInfo.setStatusCode(23880);
      paramACHBatchInfo.setStatusMsg("LogID field is too long: " + str1);
      return false;
    }
    String str2 = paramACHBatchInfo.getSubmittedBy();
    if (str2 == null)
    {
      paramACHBatchInfo.setStatusCode(23890);
      paramACHBatchInfo.setStatusMsg("SubmittedBy field is null");
      return false;
    }
    if (str2.length() > 32)
    {
      paramACHBatchInfo.setStatusCode(23900);
      paramACHBatchInfo.setStatusMsg("SubmittedBy field is too long: " + str2);
      return false;
    }
    return true;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchCache paramACHBatchCache, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    paramACHBatchCache.offsetAcctIds = ACHCompOffsetAcct.getAcctIDsByCompId(paramFFSConnectionHolder, paramACHBatchInfo.getCompId());
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, RecACHBatchInfo paramRecACHBatchInfo, ACHBatchCache paramACHBatchCache, String paramString, int paramInt)
    throws FFSException
  {
    if (this.bA > 3)
    {
      int i = 0;
      switch (paramInt)
      {
      case 4211: 
        i = 4201;
        break;
      case 4212: 
        i = 4202;
        break;
      case 4213: 
        i = 4203;
      }
      String[] arrayOfString = paramRecACHBatchInfo.getSingleIds();
      if (arrayOfString != null) {
        for (int j = 0; j < arrayOfString.length; j++)
        {
          ACHBatchInfo localACHBatchInfo = ACHBatch.getACHBatchById(paramFFSConnectionHolder, arrayOfString[j], false);
          if (localACHBatchInfo.getStatusCode() == 0) {
            a(paramFFSConnectionHolder, localACHBatchInfo, paramACHBatchCache, paramString, i, false);
          }
        }
      }
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, ACHBatchCache paramACHBatchCache, String paramString, int paramInt, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.logTransAuditLog: ";
    if (this.bA >= 3)
    {
      String str2 = paramACHBatchInfo.getCustomerId();
      if (str2 == null)
      {
        localObject = ACHCompany.getCompanyInfo(paramACHBatchInfo.getCompId(), paramFFSConnectionHolder);
        str2 = ((ACHCompanyInfo)localObject).getCustomerId();
      }
      Object localObject = null;
      if (paramACHBatchCache != null)
      {
        localObject = paramACHBatchCache.nonOffBatchDebitSum.movePointLeft(2);
        localObject = ((BigDecimal)localObject).add(paramACHBatchCache.nonOffBatchCreditSum.movePointLeft(2));
      }
      else
      {
        localObject = BigDecimal.valueOf(paramACHBatchInfo.getNonOffBatchDebitSum()).movePointLeft(2);
        localObject = ((BigDecimal)localObject).add(BigDecimal.valueOf(paramACHBatchInfo.getNonOffBatchCreditSum()).movePointLeft(2));
      }
      String str3 = paramString;
      FFSDebug.log(str1 + str3, 6);
      AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramACHBatchInfo.getSubmittedBy(), paramACHBatchInfo.getAgentId(), paramACHBatchInfo.getAgentType(), str3, paramACHBatchInfo.getLogId(), paramInt, Integer.parseInt(str2), (BigDecimal)localObject, null, paramACHBatchInfo.getBatchId(), paramACHBatchInfo.getBatchStatus(), null, null, null, null, 0);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
      if (paramBoolean) {
        a(paramFFSConnectionHolder, (RecACHBatchInfo)paramACHBatchInfo, paramACHBatchCache, paramString, paramInt);
      }
    }
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, ACHBatchCache paramACHBatchCache, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str = "ACHBatchProcessor.logTransAuditLogForAdd:";
    FFSDebug.log(str + " starts.", 6);
    int i = 4201;
    if (paramBoolean) {
      i = 4211;
    }
    a(paramFFSConnectionHolder, paramACHBatchInfo, paramACHBatchCache, paramString, i, paramBoolean);
    FFSDebug.log(str + " done.", 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, ACHBatchCache paramACHBatchCache, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str = "ACHBatchProcessor.logTransAuditLogForMod:";
    FFSDebug.log(str + " starts. " + this.bA, 6);
    int i = 4202;
    if (paramBoolean) {
      i = 4212;
    }
    a(paramFFSConnectionHolder, paramACHBatchInfo, paramACHBatchCache, paramString, i, paramBoolean);
    FFSDebug.log(str + " done.", 6);
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, ACHBatchCache paramACHBatchCache, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.logTransAuditLogForCancel:";
    FFSDebug.log(str1 + " starts.", 6);
    int i = 4203;
    if (paramBoolean) {
      i = 4213;
    }
    String str2 = paramACHBatchInfo.getBatchStatus();
    paramACHBatchInfo.setBatchStatus("CANCELEDON");
    a(paramFFSConnectionHolder, paramACHBatchInfo, paramACHBatchCache, paramString, i, paramBoolean);
    paramACHBatchInfo.setBatchStatus(str2);
    FFSDebug.log(str1 + " done.", 6);
  }
  
  public void processApprovalResult(String paramString1, String paramString2, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.processApprovalResult: ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    ACHBatchInfo localACHBatchInfo = null;
    String str2 = null;
    String str3 = null;
    try
    {
      int i;
      if (paramString1 == null)
      {
        i = 17501;
        str2 = "Transaction id is null";
        throw new FFSException(i, str1 + str2);
      }
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      localACHBatchInfo = ACHBatch.getACHBatchById(localFFSConnectionHolder, paramString1, false);
      if (localACHBatchInfo.getStatusCode() != 0)
      {
        i = 17502;
        str2 = "Transaction id is invalid";
        throw new FFSException(i, str1 + str2);
      }
      if (localACHBatchInfo.getBatchStatus().compareTo("APPROVAL_PENDING") != 0)
      {
        i = 17503;
        str2 = "Transaction not waiting for Approval";
        throw new FFSException(i, str1 + str2);
      }
      if (paramString2 == null)
      {
        i = 17504;
        str2 = "Decision from Approval System is null";
        throw new FFSException(i, str1 + str2);
      }
      if (paramString2.compareTo("Approved") == 0)
      {
        ACHBatch.updateACHBatchStatus(localACHBatchInfo, "WILLPROCESSON", localFFSConnectionHolder, false);
        jdMethod_do(localFFSConnectionHolder, localACHBatchInfo);
      }
      else if (paramString2.compareTo("Rejected") == 0)
      {
        int j = LimitCheckApprovalProcessor.processACHBatchReject(localFFSConnectionHolder, localACHBatchInfo, null);
        str3 = LimitCheckApprovalProcessor.mapToStatus(j);
        if (str3.equals("LIMIT_REVERT_SUCCEEDED"))
        {
          FFSDebug.log(str1, "LIMIT REVERT SUCCEEDED", 6);
          ACHBatch.updateACHBatchStatus(localACHBatchInfo, "APPROVAL_REJECTED", localFFSConnectionHolder, false);
        }
        else
        {
          FFSDebug.log(str1, "LIMIT REVERT FAILED", 6);
          ACHBatch.updateACHBatchStatus(localACHBatchInfo, "LIMIT_REVERT_FAILED", localFFSConnectionHolder, false);
          try
          {
            if (this.bA >= 4)
            {
              jdMethod_do(localFFSConnectionHolder, localACHBatchInfo, null, "Failed to revert limit.", false);
              localFFSConnectionHolder.conn.commit();
            }
          }
          catch (Exception localException2)
          {
            str6 = "*** " + str1 + " failed to write audit log.";
            String str7 = null;
            localFFSConnectionHolder.conn.rollback();
            str7 = FFSDebug.stackTrace(localException2);
            FFSDebug.log(str6, str7, 0);
          }
          i = 17505;
          str2 = "Limit Revert Failed";
          throw new FFSException(i, str1 + str2);
        }
      }
      else
      {
        i = 17506;
        str2 = "Decision from Approval System is invalid";
        throw new FFSException(i, str1 + str2);
      }
      try
      {
        if (this.bA >= 4)
        {
          str3 = localACHBatchInfo.getBatchStatus();
          String str4 = "";
          if (str3.equals("WILLPROCESSON")) {
            str4 = "The transaction is being approved.";
          } else if (str3.equals("APPROVAL_REJECTED")) {
            str4 = "The transaction is being rejected.";
          }
          jdMethod_do(localFFSConnectionHolder, localACHBatchInfo, null, str4, false);
          localFFSConnectionHolder.conn.commit();
        }
      }
      catch (Exception localException1)
      {
        str5 = "*** " + str1 + " failed to write audit log.";
        str6 = null;
        localFFSConnectionHolder.conn.rollback();
        str6 = FFSDebug.stackTrace(localException1);
        FFSDebug.log(str5, str6, 0);
      }
      if (localACHBatchInfo.getStatusCode() != 0) {
        localFFSConnectionHolder.conn.rollback();
      } else {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (FFSException localFFSException)
    {
      String str5 = "*** " + str1 + " failed: ";
      String str6 = null;
      localFFSConnectionHolder.conn.rollback();
      str6 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str5, str6, 0);
      throw localFFSException;
    }
    finally
    {
      if (localFFSConnectionHolder != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  public ACHBatchInfo exportACHBatch(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.exportACHBatch: ";
    FFSDebug.log(str1 + "start", 6);
    if (paramACHBatchInfo == null) {
      throw new FFSException(16000, "ACHBatchInfo is null");
    }
    ACHBatchInfo localACHBatchInfo1 = null;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      paramACHBatchInfo.setStatusCode(0);
      HashMap localHashMap = ACHBatch.getInfosForBatch(localFFSConnectionHolder, paramACHBatchInfo);
      if (localHashMap == null)
      {
        localObject1 = paramACHBatchInfo;
        return localObject1;
      }
      if (paramACHBatchInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1, " failed:", paramACHBatchInfo.getStatusMsg(), 0);
        localObject1 = paramACHBatchInfo;
        return localObject1;
      }
      localObject1 = (ACHCompanyInfo)localHashMap.get("ACH_COMPANY");
      str2 = ((ACHCompanyInfo)localObject1).getODFIACHId();
      ACHFIInfo localACHFIInfo = ACHFI.getACHFIInfo(localFFSConnectionHolder, str2);
      ACHRecordInfo[] arrayOfACHRecordInfo = paramACHBatchInfo.getRecords();
      ACHRecordInfo localACHRecordInfo = null;
      int i = 0;
      if (arrayOfACHRecordInfo != null) {
        i = arrayOfACHRecordInfo.length;
      }
      for (int j = 0; j < i; j++)
      {
        ACHPayeeInfo localACHPayeeInfo = null;
        localACHRecordInfo = arrayOfACHRecordInfo[j];
        if (localACHRecordInfo.getPayeeId() != null)
        {
          localACHPayeeInfo = ACHPayee.getACHPayeeInfo(localFFSConnectionHolder, localACHRecordInfo.getPayeeId(), true);
          if (localACHPayeeInfo.getStatusCode() != 0)
          {
            paramACHBatchInfo.setStatusCode(localACHPayeeInfo.getStatusCode());
            String str3 = localACHPayeeInfo.getStatusMsg();
            paramACHBatchInfo.setStatusMsg(str3);
            FFSDebug.log(str1 + str3, 0);
            ACHBatchInfo localACHBatchInfo2 = paramACHBatchInfo;
            return localACHBatchInfo2;
          }
          localACHRecordInfo.setPayeeAggregateInfo(localACHPayeeInfo);
          localACHRecordInfo.setPayeeInfoToMBRecord();
          localACHRecordInfo.setPayeeAggregateInfo(null);
        }
      }
      ACHBatch.setCompNameInBatchHeader(paramACHBatchInfo);
      localACHBatchInfo1 = jdMethod_do(localFFSConnectionHolder, paramACHBatchInfo, false);
      ACHAgent.build(localACHFIInfo, localACHBatchInfo1);
    }
    catch (FFSException localFFSException)
    {
      if (localFFSConnectionHolder != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      localObject1 = "*** " + str1 + " failed: " + FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject1, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      if (localFFSConnectionHolder != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      Object localObject1 = "*** " + str1 + ": failed:";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, (String)localObject1);
    }
    finally
    {
      if (localFFSConnectionHolder != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    FFSDebug.log(str1 + "end", 6);
    return localACHBatchInfo1;
  }
  
  private SchedulerInfo jdMethod_for(String paramString)
    throws FFSException
  {
    return jdMethod_for(paramString, false);
  }
  
  private SchedulerInfo jdMethod_for(String paramString, boolean paramBoolean)
    throws FFSException
  {
    SchedulerInfo localSchedulerInfo = null;
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    String str = null;
    if (paramBoolean == true) {
      str = "RECACHBATCHTRN";
    } else {
      str = "ACHBATCHTRN";
    }
    if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
      localSchedulerInfo = localScheduler.getSchedulerInfo(str, paramString);
    } else {
      try
      {
        BPWServer localBPWServer = BPWServer.getInstance();
        localSchedulerInfo = localBPWServer.getSchedulerInfoOnRemoteServer(str, paramString);
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "", 0);
        throw new FFSException(localException.getMessage());
      }
    }
    if (localSchedulerInfo == null) {
      FFSDebug.log(str + " " + "Instruction type does not exist" + " for FIID =" + paramString, 0);
    }
    return localSchedulerInfo;
  }
  
  private boolean a(ACHBatchInfo paramACHBatchInfo, boolean paramBoolean, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.canModify: ";
    FFSDebug.log(str1 + "start, isRecurring =" + paramBoolean, 6);
    SchedulerInfo localSchedulerInfo = null;
    localSchedulerInfo = jdMethod_for(paramACHBatchInfo.getFiId());
    if (!paramBoolean)
    {
      localObject = ACHBatch.getBatchStatus(paramACHBatchInfo.getBatchId(), paramFFSConnectionHolder);
      if ((localObject != null) && ((((String)localObject).compareTo("APPROVAL_PENDING") == 0) || (((String)localObject).compareTo("APPROVAL_REJECTED") == 0)))
      {
        FFSDebug.log(str1 + (String)localObject + " can be modofied.", 6);
        return true;
      }
      if ((localSchedulerInfo == null) || (localSchedulerInfo.NextRunTime == null) || (localSchedulerInfo.NextRunTime.compareTo("N/A") == 0))
      {
        FFSDebug.log(str1 + "No ACHBATCHTRN Schedule exists: return true", 6);
        return true;
      }
      FFSDebug.log(str1 + "schedule.NextRunTime =" + localSchedulerInfo.NextRunTime, 6);
      return ScheduleInfo.canModify(paramFFSConnectionHolder, "ACHBATCHTRN", paramACHBatchInfo.getBatchId(), paramACHBatchInfo.getFiId(), localSchedulerInfo.NextRunTime);
    }
    Object localObject = ACHBatch.getUnprocessedSingleIds(paramFFSConnectionHolder, paramACHBatchInfo.getBatchId());
    if (localObject != null)
    {
      int i = localObject.length;
      FFSDebug.log(str1 + "No. of single batch Ids =" + i, 6);
      if ((localSchedulerInfo == null) || (localSchedulerInfo.NextRunTime == null) || (localSchedulerInfo.NextRunTime.compareTo("N/A") == 0))
      {
        FFSDebug.log(str1 + "No ACHBATCHTRN Schedule exists: ", 6);
      }
      else
      {
        FFSDebug.log(str1 + "schedule.NextRunTime =" + localSchedulerInfo.NextRunTime, 6);
        for (int j = 0; j < i; j++)
        {
          String str2 = ACHBatch.getBatchStatus(localObject[j], paramFFSConnectionHolder);
          if ((str2 != null) && ((str2.compareTo("APPROVAL_PENDING") == 0) || (str2.compareTo("APPROVAL_REJECTED") == 0))) {
            FFSDebug.log(str1 + str2 + " can be modofied.", 6);
          } else if (!ScheduleInfo.canModify(paramFFSConnectionHolder, "ACHBATCHTRN", localObject[j], paramACHBatchInfo.getFiId(), localSchedulerInfo.NextRunTime)) {
            return false;
          }
        }
      }
    }
    FFSDebug.log(str1 + "Now getting schedule for RECACHBATCHTRN for batchId =" + paramACHBatchInfo.getBatchId(), 6);
    localSchedulerInfo = jdMethod_for(paramACHBatchInfo.getFiId(), true);
    if ((localSchedulerInfo == null) || (localSchedulerInfo.NextRunTime == null) || (localSchedulerInfo.NextRunTime.compareTo("N/A") == 0))
    {
      FFSDebug.log(str1 + "No RECACHBATCHTRN Schedule exists: return true", 6);
      return true;
    }
    FFSDebug.log(str1 + "schedule.NextRunTime =" + localSchedulerInfo.NextRunTime, 6);
    return ScheduleInfo.canModify(paramFFSConnectionHolder, "RECACHBATCHTRN", paramACHBatchInfo.getBatchId(), paramACHBatchInfo.getFiId(), localSchedulerInfo.NextRunTime);
  }
  
  private String a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.doLimitChecking: ";
    FFSDebug.log(str1 + "start, isRecurring =" + paramBoolean, 6);
    ACHPayeeInfo[] arrayOfACHPayeeInfo = ACHPayee.getACHPayeeInfoInBatch(paramFFSConnectionHolder, paramACHBatchInfo);
    HashMap localHashMap = new HashMap();
    localHashMap.put("ACHPayeeInfos", arrayOfACHPayeeInfo);
    int i = 0;
    if (!"ACH_BATCH_REVERSAL".equals(paramACHBatchInfo.getBatchCategory())) {
      i = LimitCheckApprovalProcessor.processACHBatchAdd(paramFFSConnectionHolder, paramACHBatchInfo, localHashMap);
    }
    String str2 = LimitCheckApprovalProcessor.mapToStatus(i);
    FFSDebug.log(str1 + "end, status =" + str2, 6);
    return str2;
  }
  
  private String jdMethod_try(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.doLimitCheckingDelete: ";
    FFSDebug.log(str1 + "start, isRecurring =" + paramBoolean, 6);
    int i = 0;
    if (!"APPROVAL_REJECTED".equals(paramACHBatchInfo.getBatchStatus())) {
      i = LimitCheckApprovalProcessor.processACHBatchDelete(paramFFSConnectionHolder, paramACHBatchInfo, null);
    }
    paramACHBatchInfo.setStatusCode(paramACHBatchInfo.getStatusCode());
    paramACHBatchInfo.setStatusMsg(paramACHBatchInfo.getStatusMsg());
    String str2 = LimitCheckApprovalProcessor.mapToStatus(i);
    FFSDebug.log(str1 + "end, status =" + str2, 6);
    return str2;
  }
  
  public String getDefaultACHBatchEffectiveDate(String paramString1, String paramString2)
    throws FFSException
  {
    return getDefaultACHBatchEffectiveDate(paramString1, paramString2, new HashMap());
  }
  
  public String getDefaultACHBatchEffectiveDate(String paramString1, String paramString2, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.getDefaultACHBatchEffectiveDate: ";
    FFSDebug.log(str1 + "start, compId =" + paramString1 + ", sec =" + paramString2, 6);
    if ((paramString1 == null) || (paramString2 == null))
    {
      FFSDebug.log(str1 + "failed, compId or sec is null", 6);
      return null;
    }
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      ACHCompanyInfo localACHCompanyInfo = ACHCompany.getCompanyInfo(paramString1, localFFSConnectionHolder);
      if (localACHCompanyInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed. " + localACHCompanyInfo.getStatusMsg(), 0);
        throw new FFSException(localACHCompanyInfo.getStatusCode(), localACHCompanyInfo.getStatusMsg());
      }
      localObject1 = Customer.getCustomerByID(localACHCompanyInfo.getCustomerId(), localFFSConnectionHolder);
      if (((CustomerInfo)localObject1).getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed. " + ((CustomerInfo)localObject1).getStatusMsg(), 0);
        throw new FFSException(((CustomerInfo)localObject1).getStatusCode(), ((CustomerInfo)localObject1).getStatusMsg());
      }
      localObject2 = BPWFI.getBPWFIInfo(localFFSConnectionHolder, ((CustomerInfo)localObject1).fIID);
      if (((BPWFIInfo)localObject2).getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed. " + ((BPWFIInfo)localObject2).getStatusMsg(), 0);
        throw new FFSException(((BPWFIInfo)localObject2).getStatusCode(), ((BPWFIInfo)localObject2).getStatusMsg());
      }
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("BPW_FIINFO", localObject2);
      ACHBatchInfo localACHBatchInfo = new ACHBatchInfo();
      localACHBatchInfo.setCompId(paramString1);
      localACHBatchInfo.setFiId(((BPWFIInfo)localObject2).getFIId());
      localACHBatchInfo.setClassCode(paramString2);
      int i = jdMethod_for(localFFSConnectionHolder, localACHBatchInfo);
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1 + "end, DefaultACHBatchEffectiveDate =" + i, 6);
      String str2 = Integer.toString(i);
      return str2;
    }
    catch (FFSException localFFSException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      localObject1 = "*** " + str1 + " failed: ";
      localObject2 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject1, (String)localObject2, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      Object localObject1 = "*** " + str1 + "failed: ";
      Object localObject2 = FFSDebug.stackTrace(localException);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localException, (String)localObject1);
    }
    finally
    {
      if (localFFSConnectionHolder != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  public boolean validateRecBatchFrequency(BPWFIInfo paramBPWFIInfo, RecACHBatchInfo paramRecACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.validateRecBatchFrequency: ";
    FFSDebug.log(str1 + "start", 6);
    try
    {
      String str2 = paramRecACHBatchInfo.getFrequency();
      if (str2 == null)
      {
        paramRecACHBatchInfo.setStatusCode(23130);
        paramRecACHBatchInfo.setStatusMsg("Frequency missing in recurring batch");
        FFSDebug.log(str1, paramRecACHBatchInfo.getStatusMsg(), 0);
        return false;
      }
      int i = FFSUtil.getFreqInt(str2);
      if (i == -1)
      {
        paramRecACHBatchInfo.setStatusCode(17200);
        String str4 = "Invalid frequency value for recurring-batch. :" + str2;
        paramRecACHBatchInfo.setStatusMsg(str4);
        FFSDebug.log(str1, str4, 0);
        return false;
      }
      if (paramBPWFIInfo.getACHTransWareHouse() == 1)
      {
        int j = Integer.parseInt(paramRecACHBatchInfo.getStartDate());
        j *= 100;
        int k = ScheduleInfo.computeFutureDate(j, FFSUtil.getFreqInt(paramRecACHBatchInfo.getFrequency()), 1);
        String str6 = BPWUtil.getFutureDate(paramRecACHBatchInfo.getStartDate(), "yyyyMMdd", paramBPWFIInfo.getACHMaxNoFutureDays());
        int m = Integer.parseInt(str6) * 100;
        if (k > m)
        {
          paramRecACHBatchInfo.setStatusCode(17200);
          paramRecACHBatchInfo.setStatusMsg("Invalid frequency value for recurring-batch.");
          FFSDebug.log(str1, paramRecACHBatchInfo.getStatusMsg(), 0);
          return false;
        }
      }
    }
    catch (FFSException localFFSException)
    {
      str3 = "*** " + str1 + " failed: ";
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str3, str5, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str3 = "*** " + str1 + "failed: ";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException, str3);
    }
    paramRecACHBatchInfo.setStatusCode(0);
    paramRecACHBatchInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + "end, status =" + paramRecACHBatchInfo.getStatusMsg(), 6);
    return true;
  }
  
  private ACHBatchInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean, HashMap paramHashMap)
    throws FFSException
  {
    String str = "ACHBatchProcessor.validateBatch: ";
    FFSDebug.log(str + "start, isRecurring =" + paramBoolean, 6);
    BPWFIInfo localBPWFIInfo = (BPWFIInfo)paramHashMap.get("BPW_FIINFO");
    if ((!paramBoolean) && (!jdMethod_if(paramACHBatchInfo))) {
      return paramACHBatchInfo;
    }
    if (!"ACH_BATCH_REVERSAL".equals(paramACHBatchInfo.getBatchCategory()))
    {
      int i = jdMethod_if(paramFFSConnectionHolder, paramACHBatchInfo);
      if ((!validateEffectiveDate(paramFFSConnectionHolder, paramACHBatchInfo, localBPWFIInfo, i)) || (!a(paramACHBatchInfo, paramBoolean)))
      {
        FFSDebug.log(str, " failed:", paramACHBatchInfo.getStatusMsg(), 0);
        return paramACHBatchInfo;
      }
    }
    if ((paramBoolean == true) && (!a(localBPWFIInfo, (RecACHBatchInfo)paramACHBatchInfo)))
    {
      FFSDebug.log(str, " failed:", paramACHBatchInfo.getStatusMsg(), 0);
      return paramACHBatchInfo;
    }
    if (!ACHBatch.checkCompEntryDesc(paramACHBatchInfo))
    {
      FFSDebug.log(str, " failed:", paramACHBatchInfo.getStatusMsg(), 0);
      return paramACHBatchInfo;
    }
    if (paramACHBatchInfo.getLastModifier() == null) {
      paramACHBatchInfo.setLastModifier(paramACHBatchInfo.getSubmittedBy());
    }
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setStatusMsg("Success");
    FFSDebug.log(str + "end, status =" + paramACHBatchInfo.getStatusMsg(), 6);
    return paramACHBatchInfo;
  }
  
  public boolean validateEffectiveDate(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, BPWFIInfo paramBPWFIInfo, int paramInt)
    throws FFSException
  {
    String str1 = "ACHBatch.validateEffectiveDate: ";
    FFSDebug.log(str1 + "start", 6);
    try
    {
      String str2 = (String)paramACHBatchInfo.getBatchHeaderFieldValueObject(9);
      if (str2 == null)
      {
        String str3 = " Effective date is null in the ACHBatch";
        FFSDebug.log(str1 + "failed," + str3, 6);
        paramACHBatchInfo.setStatusCode(27002);
        paramACHBatchInfo.setStatusMsg("ACHBatch's Effective Entry Date is null.");
        return false;
      }
      str2 = BPWUtil.getDateInNewFormat(str2, "yyMMdd", "yyyyMMdd");
      int i = Integer.parseInt(str2);
      int j = SmartCalendar.getACHPayday(paramBPWFIInfo.getFIId(), i);
      if (j != i)
      {
        paramACHBatchInfo.setStatusCode(27003);
        paramACHBatchInfo.setStatusMsg("ACHBatch's Effective Entry Date is not a business day: " + str2);
        return false;
      }
      if (paramInt > i)
      {
        paramACHBatchInfo.setStatusCode(27005);
        paramACHBatchInfo.setStatusMsg("Schedule for ACH Batch is already run on the business day of today");
        FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg(), 0);
        return false;
      }
      if (paramInt == i)
      {
        if (!ACHBatch.isSameDayEffDateAllowed(paramFFSConnectionHolder, paramACHBatchInfo))
        {
          FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg(), 0);
          return false;
        }
      }
      else
      {
        int k = Integer.parseInt(FFSUtil.getDateString("yyyyMMdd"));
        if (a(paramBPWFIInfo, i, k))
        {
          paramACHBatchInfo.setStatusCode(25005);
          paramACHBatchInfo.setStatusMsg("This batch's processing date is out of range.");
          return false;
        }
      }
      paramACHBatchInfo.setStatusCode(0);
      paramACHBatchInfo.setStatusMsg("Success");
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(str1 + ": failed:" + localFFSException.toString());
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + ": failed:";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException, str4);
    }
    return true;
  }
  
  private boolean a(BPWFIInfo paramBPWFIInfo, int paramInt1, int paramInt2)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.isEffectiveDateOverUpperbound: ";
    FFSDebug.log(str1 + " start: " + paramInt1 + ", " + paramInt2, 6);
    boolean bool = true;
    try
    {
      int i = paramInt1;
      int j = paramBPWFIInfo.getACHMaxNoFutureDays();
      for (int k = 0; k < j; k++)
      {
        i = SmartCalendar.getACHBusinessDay(paramBPWFIInfo.getFIId(), i, false);
        if (i <= paramInt2)
        {
          bool = false;
          break;
        }
      }
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** " + str1 + " failed: ";
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + "failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    FFSDebug.log(str1 + bool, 6);
    return bool;
  }
  
  private int jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.getNextRunDate: ";
    Object localObject = null;
    try
    {
      String str2 = BPWUtil.getLeadTimeForCutOff();
      String str3 = FFSUtil.convertDateFormat("yyyy/MM/dd HH:mm", "yyyyMMdd", str2);
      int i = Integer.parseInt(str3);
      int j = SmartCalendar.getACHPayday(paramACHBatchInfo.getFiId(), i);
      if (i != j) {
        str2 = FFSUtil.convertDateFormat("yyyyMMdd", "yyyy/MM/dd HH:mm", "" + j);
      }
      FFSDebug.log(str1 + "LeadTime = " + str2, 6);
      localObject = null;
      SchedulerInfo localSchedulerInfo = jdMethod_for(paramACHBatchInfo.getFiId());
      CutOffInfo[] arrayOfCutOffInfo = null;
      boolean bool = false;
      if (localSchedulerInfo != null)
      {
        FFSDebug.log(str1 + "schInfo.NextRunTime =" + localSchedulerInfo.NextRunTime + ", interval = " + localSchedulerInfo.SchedulerInterval, 6);
        if ((localSchedulerInfo.NextRunTime != null) && (localSchedulerInfo.NextRunTime.compareTo("N/A") != 0))
        {
          String str4 = BPWUtil.getDateInNewFormat(localSchedulerInfo.NextRunTime, "EEE MMM dd HH:mm:ss z yyyy", "yyyy/MM/dd HH:mm");
          if ((str4.compareTo(str2) >= 0) && (SmartCalendar.isACHPayDay(paramACHBatchInfo.getFiId(), str4)))
          {
            localObject = str4;
          }
          else
          {
            InstructionType localInstructionType = DBInstructionType.readInstructionType(paramFFSConnectionHolder, paramACHBatchInfo.getFiId(), "ACHBATCHTRN");
            bool = localInstructionType.useCutOffs;
            if (!bool)
            {
              int n = localSchedulerInfo.SchedulerInterval;
              str4 = BPWUtil.addIntervalToDateTime(n, str4, 12);
              FFSDebug.log(str1 + "New nextRunTime =" + str4, 6);
              while ((str4.compareTo(str2) < 0) || (!SmartCalendar.isACHPayDay(paramACHBatchInfo.getFiId(), str4)))
              {
                str4 = BPWUtil.addIntervalToDateTime(n, str4, 12);
                FFSDebug.log(str1 + "New nextRunTime =" + str4, 6);
              }
              if (str4.compareTo(str2) > 0) {
                localObject = str4;
              }
            }
          }
        }
        if ((bool) || (!localSchedulerInfo.SchedulerStatus.equals("DispatcherCompleted")))
        {
          arrayOfCutOffInfo = DBUtil.getCutOffsByFIIDAndInstructionType(paramFFSConnectionHolder, paramACHBatchInfo.getFiId(), "ACHBATCHTRN");
          if (arrayOfCutOffInfo != null)
          {
            int k = arrayOfCutOffInfo.length;
            for (m = 0; m < k; m++)
            {
              String str6 = arrayOfCutOffInfo[m].getNextProcessTime();
              FFSDebug.log(str1 + "nextProcessTime =" + str6, 6);
              while ((!SmartCalendar.isACHPayDay(paramACHBatchInfo.getFiId(), str6)) || (str6.compareTo(str2) < 0)) {
                str6 = BPWUtil.addIntervalToDateTime(1, str6, 4);
              }
              if (localObject == null) {
                localObject = str6;
              } else if ((str6.compareTo(localObject) < 0) && (str6.compareTo(str2) >= 0)) {
                localObject = str6;
              }
            }
          }
        }
      }
      if (localObject == null) {
        localObject = str2;
      }
      String str5 = BPWUtil.getDateInNewFormat(localObject, "yyyy/MM/dd HH:mm", "yyyyMMdd");
      int m = Integer.parseInt(str5);
      return m;
    }
    catch (FFSException localFFSException)
    {
      throw localFFSException;
    }
    catch (Exception localException)
    {
      throw new FFSException(localException.getMessage());
    }
  }
  
  private ACHBatchInfo jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    ACHBatchInfo localACHBatchInfo = (ACHBatchInfo)paramACHBatchInfo.clone();
    ACHRecordInfo[] arrayOfACHRecordInfo = localACHBatchInfo.getRecords();
    ArrayList localArrayList = new ArrayList();
    ACHBatchCache localACHBatchCache = jdMethod_new(paramFFSConnectionHolder, paramACHBatchInfo, paramBoolean);
    jdMethod_if(localACHBatchCache, localACHBatchInfo);
    if (arrayOfACHRecordInfo != null)
    {
      int i = arrayOfACHRecordInfo.length;
      int j = 0;
      for (int k = 0; k < i; k++) {
        if (arrayOfACHRecordInfo[k].getOffsetAccountID() == null)
        {
          localArrayList.add(arrayOfACHRecordInfo[k]);
        }
        else
        {
          int m = 1;
          m += arrayOfACHRecordInfo[k].getAddendaCount().intValue();
          ACHRecord.updateBatchCache(localACHBatchCache, arrayOfACHRecordInfo[k], m, -1);
          j = 1;
        }
      }
      if (j == 1) {
        jdMethod_for(localACHBatchCache, localACHBatchInfo);
      }
    }
    localACHBatchInfo.setRecords((ACHRecordInfo[])localArrayList.toArray(new ACHRecordInfo[0]));
    return localACHBatchInfo;
  }
  
  private int jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    int i = jdMethod_if(paramFFSConnectionHolder, paramACHBatchInfo);
    if (!ACHBatch.isSameDayEffDateAllowed(paramFFSConnectionHolder, paramACHBatchInfo)) {
      try
      {
        i = SmartCalendar.getACHBusinessDay(paramACHBatchInfo.getFiId(), i, true);
      }
      catch (Exception localException)
      {
        String str = "Failed to get business day for an ACHBatch: " + localException.toString();
        throw new FFSException(localException, str);
      }
    }
    return i;
  }
  
  public PagingInfo getPagedACH(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.getPagedACH ";
    FFSDebug.log(str1 + "start...", 6);
    if (paramPagingInfo == null) {
      throw new FFSException("PagingInfo is null !");
    }
    try
    {
      this.bz.getPagedData(paramPagingInfo);
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** " + str1 + " failed: ";
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + "failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    return paramPagingInfo;
  }
  
  public BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "ACHBatchProcessor.getBankingDaysInRange: ";
    FFSDebug.log(str1 + "start: CompId= " + paramBankingDays.getCompId() + ", SEC= " + paramBankingDays.getSec() + ", startDate= " + DBUtil.calendarToString(paramBankingDays.getStartDate()) + ", endDate = " + DBUtil.calendarToString(paramBankingDays.getEndDate()), 6);
    String str2 = getDefaultACHBatchEffectiveDate(paramBankingDays.getCompId(), paramBankingDays.getSec(), paramHashMap);
    Calendar localCalendar1 = null;
    try
    {
      localCalendar1 = BPWUtil.dateIntToCalendar(Integer.parseInt(str2), "yyyyMMdd");
    }
    catch (Exception localException1) {}
    int i = (int)((paramBankingDays.getEndDate().getTimeInMillis() - paramBankingDays.getStartDate().getTimeInMillis()) / 86400000L + 1L);
    boolean[] arrayOfBoolean = new boolean[i];
    Calendar localCalendar2 = (Calendar)paramBankingDays.getStartDate().clone();
    Calendar localCalendar3 = paramBankingDays.getEndDate();
    int j = 0;
    while (localCalendar2.before(localCalendar1))
    {
      arrayOfBoolean[j] = false;
      j++;
      localCalendar2.add(5, 1);
    }
    if (localCalendar2.equals(localCalendar1))
    {
      arrayOfBoolean[j] = true;
      j++;
      localCalendar2.add(5, 1);
    }
    BPWFIInfo localBPWFIInfo = (BPWFIInfo)paramHashMap.get("BPW_FIINFO");
    Calendar localCalendar4 = Calendar.getInstance();
    localCalendar4.add(5, localBPWFIInfo.getACHMaxNoFutureDays());
    Calendar localCalendar5 = localCalendar3;
    if (localCalendar4.before(localCalendar3)) {
      localCalendar5 = localCalendar4;
    }
    while ((localCalendar2.before(localCalendar5)) || (localCalendar2.equals(localCalendar5)))
    {
      int k = BPWUtil.calendarToDueDateFormatInt(localCalendar2);
      try
      {
        int m = SmartCalendar.getACHPayday(localBPWFIInfo.getFIId(), k);
        if (m == k) {
          arrayOfBoolean[j] = true;
        } else {
          arrayOfBoolean[j] = false;
        }
      }
      catch (Exception localException2) {}
      j++;
      localCalendar2.add(5, 1);
    }
    if (localCalendar4.before(localCalendar3)) {
      while ((localCalendar2.before(localCalendar3)) || (localCalendar2.equals(localCalendar3)))
      {
        arrayOfBoolean[j] = false;
        j++;
        localCalendar2.add(5, 1);
      }
    }
    paramBankingDays.setBankingDays(arrayOfBoolean);
    return paramBankingDays;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.ACHBatchProcessor
 * JD-Core Version:    0.7.0.1
 */