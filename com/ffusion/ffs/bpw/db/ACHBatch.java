package com.ffusion.ffs.bpw.db;

import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.limits.CustomLimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.interfaces.ACHBatchHist;
import com.ffusion.ffs.bpw.interfaces.ACHBatchInfo;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHHistInfo;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecACHBatchInfo;
import com.ffusion.ffs.bpw.master.LimitCheckApprovalProcessor;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.entitlements.EntitlementsUtil;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class ACHBatch
  implements FFSConst, DBConsts, SQLConsts, ACHConsts
{
  private static final String j9 = " ORDER BY BatchId";
  private static final String ka = " ORDER BY RecBatchId";
  
  public static ACHBatchInfo addACHBatch(ACHBatchInfo paramACHBatchInfo, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    String str1 = null;
    String str2 = null;
    Object[] arrayOfObject = null;
    String str3 = "ACHBatch.addACHBatch";
    FFSDebug.log(str3 + ": Starts. Recurring: " + paramBoolean, 6);
    if (paramFFSConnectionHolder == null)
    {
      paramACHBatchInfo.setStatusCode(16000);
      String str4 = "FFSConnectionHolder object  is null";
      paramACHBatchInfo.setStatusMsg(str4);
      FFSDebug.log(str3, str4, 0);
      return paramACHBatchInfo;
    }
    try
    {
      if (paramBoolean)
      {
        str1 = "INSERT INTO ACH_RecBatch (RecBatchId, CompId, CompData, CompACHId, ODFIACHId, BatchType, BatchCategory, BatchBalanceType, EffectiveDate, SettlementDate, CompEntryDesc, DescpDate, BatchNum, SrvClassCode, StdClassCode, CountryCode, OriginCurrCode, DestCurrCode, TotalDebit, TotalCredit, MsgAuthCode, RecordCount, EntryHash, SubmitDate, SubmittedBy, BatchStatus, FrgnExInd, FrgnExRefInd, FrgnExRef, OrigStatusCode, LogId,Frequency, StartDate, EndDate, PmtsCount, Memo, OffsetAccountID, HeaderCompName, LastModifier, NonOffTotalCredit, NonOffTotalDebit, NonOffRecordCount )VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?)";
        str2 = DBUtil.getNextIndexStringWithPadding("BatchID", 32, '0');
        paramACHBatchInfo.setBatchId(str2);
      }
      else
      {
        str1 = "INSERT INTO ACH_Batch (BatchId, CompId, CompData, CompACHId, ODFIACHId, BatchType, BatchCategory, BatchBalanceType, EffectiveDate, SettlementDate, CompEntryDesc, DescpDate, BatchNum, SrvClassCode, StdClassCode, CountryCode, OriginCurrCode, DestCurrCode, TotalDebit, TotalCredit, MsgAuthCode, RecordCount, EntryHash, SubmitDate, SubmittedBy, BatchStatus, FrgnExInd, FrgnExRefInd, FrgnExRef, OrigStatusCode, LogId,ExportFileName, DueDate, DtProcessed, Memo,OffsetAccountID, HeaderCompName, LastModifier, NonOffTotalCredit, NonOffTotalDebit, NonOffRecordCount )VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        str2 = DBUtil.getNextIndexStringWithPadding("BatchID", 32, '0');
        paramACHBatchInfo.setBatchId(str2);
      }
      arrayOfObject = jdMethod_try(paramACHBatchInfo, paramBoolean);
      FFSDebug.log(str3 + ":: compId=", paramACHBatchInfo.getCompId(), 6);
      FFSDebug.log(str3 + ":: batchId=", paramACHBatchInfo.getBatchId(), 6);
      FFSDebug.log(str3 + ":: BatchType=", paramACHBatchInfo.getBatchType(), 6);
      FFSDebug.log(str3 + ":: BatchCategory=", paramACHBatchInfo.getBatchCategory(), 6);
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str5 = "*** " + str3 + ": failed:";
      String str6 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str6, 0);
      throw new FFSException(localException, str5);
    }
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setStatusMsg("Success");
    FFSDebug.log(str3 + ": done", 6);
    return paramACHBatchInfo;
  }
  
  public static ACHBatchInfo updateACHBatchWithControl(ACHBatchInfo paramACHBatchInfo, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatch.updateACHBatchWithControl (isRecurring: " + paramBoolean + ")";
    String str2 = "UPDATE ACH_Batch set TotalDebit = ?, TotalCredit = ?, MsgAuthCode = ?,  RecordCount = ?, EntryHash = ?, NonOffTotalCredit = ?, NonOffTotalDebit = ?,  NonOffRecordCount = ?  WHERE BatchId = ?";
    Object[] arrayOfObject = null;
    FFSDebug.log(str1 + ": Start", 6);
    if (paramACHBatchInfo.getBatchControl() == null)
    {
      paramACHBatchInfo.setStatusCode(17060);
      paramACHBatchInfo.setStatusMsg("Batch control in the ACHBatchInfo is NULL.");
      FFSDebug.log(str1, "Batch control in the ACHBatchInfo is NULL.", 0);
      return paramACHBatchInfo;
    }
    if (paramFFSConnectionHolder == null)
    {
      paramACHBatchInfo.setStatusCode(16000);
      String str3 = "FFSConnectionHolder object  is null";
      paramACHBatchInfo.setStatusMsg(str3);
      FFSDebug.log(str1, str3, 0);
      return paramACHBatchInfo;
    }
    if (paramBoolean) {
      str2 = "UPDATE ACH_RecBatch set TotalDebit = ?, TotalCredit = ?, MsgAuthCode = ?,  RecordCount = ?, EntryHash = ? , NonOffTotalCredit = ?, NonOffTotalDebit = ?, NonOffRecordCount = ?  WHERE RecBatchId = ?";
    } else {
      str2 = "UPDATE ACH_Batch set TotalDebit = ?, TotalCredit = ?, MsgAuthCode = ?,  RecordCount = ?, EntryHash = ?, NonOffTotalCredit = ?, NonOffTotalDebit = ?,  NonOffRecordCount = ?  WHERE BatchId = ?";
    }
    arrayOfObject = jdMethod_do(paramACHBatchInfo);
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + ": failed:";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException, str4);
    }
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setStatusMsg("Success");
    FFSDebug.log(str1, ": done", 6);
    return paramACHBatchInfo;
  }
  
  private static void a(ACHBatchInfo paramACHBatchInfo, FFSResultSet paramFFSResultSet, boolean paramBoolean, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    Object localObject;
    int i;
    if (paramBoolean == true)
    {
      localObject = (RecACHBatchInfo)paramACHBatchInfo;
      i = paramFFSResultSet.getColumnInt("Frequency");
      Hashtable localHashtable = FFSUtil.stringToHashtable(paramFFSResultSet.getColumnString("Memo"));
      ((RecACHBatchInfo)localObject).setBatchId(paramFFSResultSet.getColumnString("RecBatchId"));
      ((RecACHBatchInfo)localObject).setBatchType(paramFFSResultSet.getColumnString("BatchType"));
      ((RecACHBatchInfo)localObject).setBatchCategory(paramFFSResultSet.getColumnString("BatchCategory"));
      ((RecACHBatchInfo)localObject).setBatchBalanceType(paramFFSResultSet.getColumnString("BatchBalanceType"));
      ((RecACHBatchInfo)localObject).setClassCode(paramFFSResultSet.getColumnString("StdClassCode"));
      ((RecACHBatchInfo)localObject).setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
      ((RecACHBatchInfo)localObject).setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
      ((RecACHBatchInfo)localObject).setBatchStatus(paramFFSResultSet.getColumnString("BatchStatus"));
      ((RecACHBatchInfo)localObject).setCompId(paramFFSResultSet.getColumnString("CompId"));
      ((RecACHBatchInfo)localObject).setCompAchId(paramFFSResultSet.getColumnString("CompACHId"));
      ((RecACHBatchInfo)localObject).setFiId(paramFFSResultSet.getColumnString("FIId"));
      ((RecACHBatchInfo)localObject).setCustomerId(paramFFSResultSet.getColumnString("CustomerId"));
      ((RecACHBatchInfo)localObject).setOdfiId(paramFFSResultSet.getColumnString("ODFIACHId"));
      ((RecACHBatchInfo)localObject).setCompName(paramFFSResultSet.getColumnString("CompName"));
      ((RecACHBatchInfo)localObject).setOdfiRTN(paramFFSResultSet.getColumnString("FIRTN"));
      ((RecACHBatchInfo)localObject).setOdfiName(paramFFSResultSet.getColumnString("ODFIName"));
      ((RecACHBatchInfo)localObject).setTotalBatchSize(paramFFSResultSet.getColumnInt("RecordCount"));
      ((RecACHBatchInfo)localObject).setFrequency(FFSUtil.getFreqString(i));
      ((RecACHBatchInfo)localObject).setStartDate(BPWUtil.getDateBeanFormat(paramFFSResultSet.getColumnInt("StartDate")));
      ((RecACHBatchInfo)localObject).setEndDate(BPWUtil.getDateBeanFormat(paramFFSResultSet.getColumnInt("EndDate")));
      ((RecACHBatchInfo)localObject).setPmtsCount(paramFFSResultSet.getColumnInt("PmtsCount"));
      ((RecACHBatchInfo)localObject).setLogId(paramFFSResultSet.getColumnString("LogId"));
      ((RecACHBatchInfo)localObject).setMemo(localHashtable);
      ((RecACHBatchInfo)localObject).setOffsetAccountID(paramFFSResultSet.getColumnString("OffsetAccountID"));
      ((RecACHBatchInfo)localObject).setHeaderCompName(paramFFSResultSet.getColumnString("HeaderCompName"));
      ((RecACHBatchInfo)localObject).setLastModifier(paramFFSResultSet.getColumnString("LastModifier"));
      ((RecACHBatchInfo)localObject).setNonOffBatchCreditSum(paramFFSResultSet.getColumnLong("NonOffTotalCredit"));
      ((RecACHBatchInfo)localObject).setNonOffBatchDebitSum(paramFFSResultSet.getColumnLong("NonOffTotalDebit"));
      ((RecACHBatchInfo)localObject).setNonOffBatchEntryCount(paramFFSResultSet.getColumnInt("NonOffRecordCount"));
      int k = paramFFSResultSet.getColumnInt("BatchNum");
      ((RecACHBatchInfo)localObject).createBatchHeader();
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(2, paramFFSResultSet.getColumnString("SrvClassCode"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(14, paramFFSResultSet.getColumnString("CountryCode"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(5, paramFFSResultSet.getColumnString("CompACHId"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(6, paramFFSResultSet.getColumnString("StdClassCode"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(15, paramFFSResultSet.getColumnString("OriginCurrCode"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(16, paramFFSResultSet.getColumnString("DestCurrCode"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(9, paramFFSResultSet.getColumnString("EffectiveDate"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(10, paramFFSResultSet.getColumnString("SettlementDate"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(11, ((RecACHBatchInfo)localObject).getOdfiRTN());
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(12, String.valueOf(k));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(17, paramFFSResultSet.getColumnString("FrgnExInd"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(18, paramFFSResultSet.getColumnString("FrgnExRefInd"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(19, paramFFSResultSet.getColumnString("FrgnExRef"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(13, paramFFSResultSet.getColumnString("OrigStatusCode"));
      setCompNameInBatchHeader((ACHBatchInfo)localObject);
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(4, paramFFSResultSet.getColumnString("CompData"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(7, paramFFSResultSet.getColumnString("CompEntryDesc"));
      ((RecACHBatchInfo)localObject).setBatchHeaderFieldValueObject(8, paramFFSResultSet.getColumnString("DescpDate"));
      int m = paramFFSResultSet.getColumnInt("RecordCount");
      ((RecACHBatchInfo)localObject).createBatchControl();
      ((RecACHBatchInfo)localObject).setBatchControlFieldValueObject(2, paramFFSResultSet.getColumnString("SrvClassCode"));
      ((RecACHBatchInfo)localObject).setBatchControlFieldValueObject(20, String.valueOf(m));
      ((RecACHBatchInfo)localObject).setBatchControlFieldValueObject(21, paramFFSResultSet.getColumnString("EntryHash"));
      ((RecACHBatchInfo)localObject).setBatchControlFieldValueObject(22, paramFFSResultSet.getColumnString("TotalDebit"));
      ((RecACHBatchInfo)localObject).setBatchControlFieldValueObject(23, paramFFSResultSet.getColumnString("TotalCredit"));
      ((RecACHBatchInfo)localObject).setBatchControlFieldValueObject(11, ((RecACHBatchInfo)localObject).getOdfiRTN());
      ((RecACHBatchInfo)localObject).setBatchControlFieldValueObject(12, String.valueOf(k));
      ((RecACHBatchInfo)localObject).setBatchControlFieldValueObject(5, paramFFSResultSet.getColumnString("CompACHId"));
      ((RecACHBatchInfo)localObject).setBatchControlFieldValueObject(25, paramFFSResultSet.getColumnString("MsgAuthCode"));
    }
    else
    {
      localObject = FFSUtil.stringToHashtable(paramFFSResultSet.getColumnString("Memo"));
      paramACHBatchInfo.setBatchId(paramFFSResultSet.getColumnString("BatchId"));
      paramACHBatchInfo.setBatchType(paramFFSResultSet.getColumnString("BatchType"));
      paramACHBatchInfo.setBatchCategory(paramFFSResultSet.getColumnString("BatchCategory"));
      paramACHBatchInfo.setBatchBalanceType(paramFFSResultSet.getColumnString("BatchBalanceType"));
      paramACHBatchInfo.setClassCode(paramFFSResultSet.getColumnString("StdClassCode"));
      paramACHBatchInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
      paramACHBatchInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
      paramACHBatchInfo.setBatchStatus(paramFFSResultSet.getColumnString("BatchStatus"));
      paramACHBatchInfo.setCompId(paramFFSResultSet.getColumnString("CompId"));
      paramACHBatchInfo.setCompAchId(paramFFSResultSet.getColumnString("CompACHId"));
      paramACHBatchInfo.setOdfiId(paramFFSResultSet.getColumnString("ODFIACHId"));
      paramACHBatchInfo.setFiId(paramFFSResultSet.getColumnString("FIId"));
      paramACHBatchInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerId"));
      paramACHBatchInfo.setCompName(paramFFSResultSet.getColumnString("CompName"));
      paramACHBatchInfo.setOdfiRTN(paramFFSResultSet.getColumnString("FIRTN"));
      paramACHBatchInfo.setOdfiName(paramFFSResultSet.getColumnString("ODFIName"));
      paramACHBatchInfo.setTotalBatchSize(paramFFSResultSet.getColumnInt("RecordCount"));
      paramACHBatchInfo.setExportFileName(paramFFSResultSet.getColumnString("ExportFileName"));
      paramACHBatchInfo.setDueDate(BPWUtil.getDateBeanFormat(paramFFSResultSet.getColumnInt("DueDate")));
      paramACHBatchInfo.setDtProcessed(BPWUtil.getDateBeanFormat(paramFFSResultSet.getColumnInt("DtProcessed")));
      paramACHBatchInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
      paramACHBatchInfo.setMemo((Hashtable)localObject);
      paramACHBatchInfo.setOffsetAccountID(paramFFSResultSet.getColumnString("OffsetAccountID"));
      paramACHBatchInfo.setHeaderCompName(paramFFSResultSet.getColumnString("HeaderCompName"));
      paramACHBatchInfo.setLastModifier(paramFFSResultSet.getColumnString("LastModifier"));
      paramACHBatchInfo.setNonOffBatchCreditSum(paramFFSResultSet.getColumnLong("NonOffTotalCredit"));
      paramACHBatchInfo.setNonOffBatchDebitSum(paramFFSResultSet.getColumnLong("NonOffTotalDebit"));
      paramACHBatchInfo.setNonOffBatchEntryCount(paramFFSResultSet.getColumnInt("NonOffRecordCount"));
      i = paramFFSResultSet.getColumnInt("BatchNum");
      paramACHBatchInfo.createBatchHeader();
      paramACHBatchInfo.setBatchHeaderFieldValueObject(2, paramFFSResultSet.getColumnString("SrvClassCode"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(14, paramFFSResultSet.getColumnString("CountryCode"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(5, paramFFSResultSet.getColumnString("CompACHId"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(6, paramFFSResultSet.getColumnString("StdClassCode"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(15, paramFFSResultSet.getColumnString("OriginCurrCode"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(16, paramFFSResultSet.getColumnString("DestCurrCode"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(9, paramFFSResultSet.getColumnString("EffectiveDate"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(10, paramFFSResultSet.getColumnString("SettlementDate"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(11, paramACHBatchInfo.getOdfiRTN());
      paramACHBatchInfo.setBatchHeaderFieldValueObject(12, String.valueOf(i));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(17, paramFFSResultSet.getColumnString("FrgnExInd"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(18, paramFFSResultSet.getColumnString("FrgnExRefInd"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(19, paramFFSResultSet.getColumnString("FrgnExRef"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(13, paramFFSResultSet.getColumnString("OrigStatusCode"));
      setCompNameInBatchHeader(paramACHBatchInfo);
      paramACHBatchInfo.setBatchHeaderFieldValueObject(4, paramFFSResultSet.getColumnString("CompData"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(7, paramFFSResultSet.getColumnString("CompEntryDesc"));
      paramACHBatchInfo.setBatchHeaderFieldValueObject(8, paramFFSResultSet.getColumnString("DescpDate"));
      int j = paramFFSResultSet.getColumnInt("RecordCount");
      paramACHBatchInfo.createBatchControl();
      paramACHBatchInfo.setBatchControlFieldValueObject(2, paramFFSResultSet.getColumnString("SrvClassCode"));
      paramACHBatchInfo.setBatchControlFieldValueObject(20, String.valueOf(j));
      paramACHBatchInfo.setBatchControlFieldValueObject(21, paramFFSResultSet.getColumnString("EntryHash"));
      paramACHBatchInfo.setBatchControlFieldValueObject(22, paramFFSResultSet.getColumnString("TotalDebit"));
      paramACHBatchInfo.setBatchControlFieldValueObject(23, paramFFSResultSet.getColumnString("TotalCredit"));
      paramACHBatchInfo.setBatchControlFieldValueObject(11, paramACHBatchInfo.getOdfiRTN());
      paramACHBatchInfo.setBatchControlFieldValueObject(12, String.valueOf(i));
      paramACHBatchInfo.setBatchControlFieldValueObject(5, paramFFSResultSet.getColumnString("CompACHId"));
      paramACHBatchInfo.setBatchControlFieldValueObject(25, paramFFSResultSet.getColumnString("MsgAuthCode"));
      if (paramACHBatchInfo.getBatchType().compareTo("Recurring") == 0)
      {
        String[] arrayOfString = RecSrvrTIDToSrvrTID.getRecSrvrTIDs(paramFFSConnectionHolder, paramACHBatchInfo.getBatchId(), 1);
        if ((arrayOfString != null) && (arrayOfString.length == 1)) {
          paramACHBatchInfo.setRecBatchId(arrayOfString[0]);
        }
      }
    }
  }
  
  private static Object[] jdMethod_try(ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
  {
    String str = paramACHBatchInfo.getOdfiId();
    if (paramBoolean == true)
    {
      localObject = (RecACHBatchInfo)paramACHBatchInfo;
      Object[] arrayOfObject = { ((RecACHBatchInfo)localObject).getBatchId(), ((RecACHBatchInfo)localObject).getCompId(), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(4), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(5), str, ((RecACHBatchInfo)localObject).getBatchType(), ((RecACHBatchInfo)localObject).getBatchCategory(), ((RecACHBatchInfo)localObject).getBatchBalanceType(), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(9), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(10), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(7), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(8), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(12), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(2) == null ? null : ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(2).toString(), ((RecACHBatchInfo)localObject).getClassCode(), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(14), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(15), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(16), ((RecACHBatchInfo)localObject).getBatchControlFieldValueObject(22) == null ? null : ((RecACHBatchInfo)localObject).getBatchControlFieldValueObject(22).toString(), ((RecACHBatchInfo)localObject).getBatchControlFieldValueObject(23) == null ? null : ((RecACHBatchInfo)localObject).getBatchControlFieldValueObject(23).toString(), ((RecACHBatchInfo)localObject).getBatchControlFieldValueObject(25), ((RecACHBatchInfo)localObject).getBatchControlFieldValueObject(20), ((RecACHBatchInfo)localObject).getBatchControlFieldValueObject(21) == null ? null : ((RecACHBatchInfo)localObject).getBatchControlFieldValueObject(21).toString(), FFSUtil.getDateString(), ((RecACHBatchInfo)localObject).getSubmittedBy(), paramACHBatchInfo.getBatchStatus(), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(17), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(18) == null ? null : ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(18).toString(), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(19), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(13), ((RecACHBatchInfo)localObject).getLogId(), new Integer(FFSUtil.getFreqInt(((RecACHBatchInfo)localObject).getFrequency())), new Integer(BPWUtil.getDateDBFormat(((RecACHBatchInfo)localObject).getStartDate())), new Integer(BPWUtil.getDateDBFormat(((RecACHBatchInfo)localObject).getEndDate())), new Integer(((RecACHBatchInfo)localObject).getPmtsCount()), FFSUtil.hashtableToString(((RecACHBatchInfo)localObject).getMemo()), ((RecACHBatchInfo)localObject).getOffsetAccountID(), ((RecACHBatchInfo)localObject).getHeaderCompName(), ((RecACHBatchInfo)localObject).getLastModifier(), new Long(((RecACHBatchInfo)localObject).getNonOffBatchCreditSum()) == null ? null : new Long(((RecACHBatchInfo)localObject).getNonOffBatchCreditSum()).toString(), new Long(((RecACHBatchInfo)localObject).getNonOffBatchDebitSum()) == null ? null : new Long(((RecACHBatchInfo)localObject).getNonOffBatchDebitSum()).toString(), new Integer(((RecACHBatchInfo)localObject).getNonOffBatchEntryCount()) };
      return arrayOfObject;
    }
    Object localObject = { paramACHBatchInfo.getBatchId(), paramACHBatchInfo.getCompId(), paramACHBatchInfo.getBatchHeaderFieldValueObject(4), paramACHBatchInfo.getBatchHeaderFieldValueObject(5), str, paramACHBatchInfo.getBatchType(), paramACHBatchInfo.getBatchCategory(), paramACHBatchInfo.getBatchBalanceType(), paramACHBatchInfo.getBatchHeaderFieldValueObject(9), paramACHBatchInfo.getBatchHeaderFieldValueObject(10), paramACHBatchInfo.getBatchHeaderFieldValueObject(7), paramACHBatchInfo.getBatchHeaderFieldValueObject(8), paramACHBatchInfo.getBatchHeaderFieldValueObject(12), String.valueOf(paramACHBatchInfo.getBatchHeaderFieldValueObject(2)), paramACHBatchInfo.getClassCode(), paramACHBatchInfo.getBatchHeaderFieldValueObject(14), paramACHBatchInfo.getBatchHeaderFieldValueObject(15), paramACHBatchInfo.getBatchHeaderFieldValueObject(16), String.valueOf(paramACHBatchInfo.getBatchControlFieldValueObject(22)), String.valueOf(paramACHBatchInfo.getBatchControlFieldValueObject(23)), paramACHBatchInfo.getBatchControlFieldValueObject(25), paramACHBatchInfo.getBatchControlFieldValueObject(20), String.valueOf(paramACHBatchInfo.getBatchControlFieldValueObject(21)), FFSUtil.getDateString(), paramACHBatchInfo.getSubmittedBy(), paramACHBatchInfo.getBatchStatus(), paramACHBatchInfo.getBatchHeaderFieldValueObject(17), String.valueOf(paramACHBatchInfo.getBatchHeaderFieldValueObject(18)), paramACHBatchInfo.getBatchHeaderFieldValueObject(19), paramACHBatchInfo.getBatchHeaderFieldValueObject(13), paramACHBatchInfo.getLogId(), paramACHBatchInfo.getExportFileName(), new Integer(BPWUtil.getDateDBFormat(paramACHBatchInfo.getDueDate())), new Integer(BPWUtil.getDateDBFormat(paramACHBatchInfo.getDtProcessed())), FFSUtil.hashtableToString(paramACHBatchInfo.getMemo()), paramACHBatchInfo.getOffsetAccountID(), paramACHBatchInfo.getHeaderCompName(), paramACHBatchInfo.getLastModifier(), String.valueOf(paramACHBatchInfo.getNonOffBatchCreditSum()), String.valueOf(paramACHBatchInfo.getNonOffBatchDebitSum()), new Integer(paramACHBatchInfo.getNonOffBatchEntryCount()) };
    return localObject;
  }
  
  private static Object[] jdMethod_do(ACHBatchInfo paramACHBatchInfo)
  {
    Object[] arrayOfObject = { paramACHBatchInfo.getBatchControlFieldValueObject(22) == null ? null : paramACHBatchInfo.getBatchControlFieldValueObject(22).toString(), paramACHBatchInfo.getBatchControlFieldValueObject(23) == null ? null : paramACHBatchInfo.getBatchControlFieldValueObject(23).toString(), paramACHBatchInfo.getBatchControlFieldValueObject(25), paramACHBatchInfo.getBatchControlFieldValueObject(20), paramACHBatchInfo.getBatchControlFieldValueObject(21) == null ? null : paramACHBatchInfo.getBatchControlFieldValueObject(21).toString(), new Long(paramACHBatchInfo.getNonOffBatchCreditSum()).toString(), new Long(paramACHBatchInfo.getNonOffBatchDebitSum()).toString(), new Integer(paramACHBatchInfo.getNonOffBatchEntryCount()), paramACHBatchInfo.getBatchId() };
    return arrayOfObject;
  }
  
  private static Object[] jdMethod_new(ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
  {
    if (paramBoolean == true)
    {
      localObject = (RecACHBatchInfo)paramACHBatchInfo;
      Object[] arrayOfObject = { ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(4), ((RecACHBatchInfo)localObject).getBatchBalanceType(), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(9), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(10), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(7), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(8), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(14), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(15), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(16), FFSUtil.getDateString(), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(17), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(18) == null ? null : ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(18).toString(), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(19), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(13), new Integer(FFSUtil.getFreqInt(((RecACHBatchInfo)localObject).getFrequency())), new Integer(BPWUtil.getDateDBFormat(((RecACHBatchInfo)localObject).getStartDate())), new Integer(BPWUtil.getDateDBFormat(((RecACHBatchInfo)localObject).getEndDate())), new Integer(((RecACHBatchInfo)localObject).getPmtsCount()), FFSUtil.hashtableToString(((RecACHBatchInfo)localObject).getMemo()), ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(2) == null ? null : ((RecACHBatchInfo)localObject).getBatchHeaderFieldValueObject(2).toString(), ((RecACHBatchInfo)localObject).getOffsetAccountID(), ((RecACHBatchInfo)localObject).getHeaderCompName(), ((RecACHBatchInfo)localObject).getLastModifier(), ((RecACHBatchInfo)localObject).getBatchId() };
      return arrayOfObject;
    }
    Object localObject = { paramACHBatchInfo.getBatchHeaderFieldValueObject(4), paramACHBatchInfo.getBatchBalanceType(), paramACHBatchInfo.getBatchHeaderFieldValueObject(9), paramACHBatchInfo.getBatchHeaderFieldValueObject(10), paramACHBatchInfo.getBatchHeaderFieldValueObject(7), paramACHBatchInfo.getBatchHeaderFieldValueObject(8), paramACHBatchInfo.getBatchHeaderFieldValueObject(14), paramACHBatchInfo.getBatchHeaderFieldValueObject(15), paramACHBatchInfo.getBatchHeaderFieldValueObject(16), FFSUtil.getDateString(), paramACHBatchInfo.getBatchHeaderFieldValueObject(17), paramACHBatchInfo.getBatchHeaderFieldValueObject(18) == null ? null : paramACHBatchInfo.getBatchHeaderFieldValueObject(18).toString(), paramACHBatchInfo.getBatchHeaderFieldValueObject(19), paramACHBatchInfo.getBatchHeaderFieldValueObject(13), new Integer(BPWUtil.getDateDBFormat(paramACHBatchInfo.getDueDate())), new Integer(BPWUtil.getDateDBFormat(paramACHBatchInfo.getDtProcessed())), FFSUtil.hashtableToString(paramACHBatchInfo.getMemo()), paramACHBatchInfo.getBatchHeaderFieldValueObject(2) == null ? null : paramACHBatchInfo.getBatchHeaderFieldValueObject(2).toString(), paramACHBatchInfo.getOffsetAccountID(), paramACHBatchInfo.getHeaderCompName(), paramACHBatchInfo.getLastModifier(), paramACHBatchInfo.getBatchId() };
    return localObject;
  }
  
  public static ACHBatchInfo updateHeaderAndGenInfo(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatch.updateHeaderAndGenInfo";
    String str2 = "";
    if (paramBoolean == true) {
      str2 = "UPDATE ACH_RecBatch set CompData =?, BatchBalanceType = ?, EffectiveDate=?, SettlementDate=?, CompEntryDesc=?, DescpDate=?, CountryCode=?, OriginCurrCode=?, DestCurrCode=?, SubmitDate=?, FrgnExInd=?, FrgnExRefInd=?, FrgnExRef=?, OrigStatusCode=?, Frequency=?, StartDate=?, EndDate=?, PmtsCount=?, Memo=?, SrvClassCode=?, OffsetAccountID=?, HeaderCompName=?, LastModifier =? WHERE RecBatchId = ? AND BatchStatus IN ('WILLPROCESSON','APPROVAL_REJECTED','APPROVAL_PENDING')";
    } else {
      str2 = "UPDATE ACH_Batch set CompData =?, BatchBalanceType = ?, EffectiveDate=?, SettlementDate=?, CompEntryDesc=?, DescpDate=?, CountryCode=?, OriginCurrCode=?, DestCurrCode=?, SubmitDate=?, FrgnExInd=?, FrgnExRefInd=?, FrgnExRef=?, OrigStatusCode=?, DueDate=?, DtProcessed=?, Memo=?, SrvClassCode=?, OffsetAccountID=?, HeaderCompName=?, LastModifier =? WHERE BatchId = ? AND BatchStatus IN ('WILLPROCESSON','APPROVAL_REJECTED','APPROVAL_PENDING')";
    }
    FFSDebug.log(str1 + ": start, " + " batchId = " + paramACHBatchInfo.getBatchId() + " isRecurring = " + paramBoolean, 6);
    Object localObject;
    if (paramFFSConnectionHolder == null)
    {
      paramACHBatchInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramACHBatchInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1 + ", " + (String)localObject, 0);
      return paramACHBatchInfo;
    }
    try
    {
      FFSDebug.log(str1 + ":: compId=", paramACHBatchInfo.getCompId(), 6);
      FFSDebug.log(str1 + ":: batchId=", paramACHBatchInfo.getBatchId(), 6);
      FFSDebug.log(str1 + ":: BatchType=", paramACHBatchInfo.getBatchType(), 6);
      FFSDebug.log(str1 + ":: BatchCategory=", paramACHBatchInfo.getBatchCategory(), 6);
      localObject = jdMethod_new(paramACHBatchInfo, paramBoolean);
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      if (i == 0)
      {
        paramACHBatchInfo.setStatusCode(17180);
        str4 = "ACHBatch No record has been modified.";
        paramACHBatchInfo.setStatusMsg(str4);
        FFSDebug.log(str1 + " failed, ", str4, 0);
        return paramACHBatchInfo;
      }
    }
    catch (Exception localException)
    {
      String str3 = "***  " + str1 + " : failed:";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str3);
    }
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + ": done", 6);
    return paramACHBatchInfo;
  }
  
  public static ACHBatchInfo canACHBatch(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, boolean paramBoolean)
    throws FFSException
  {
    return updateACHBatchStatus(paramACHBatchInfo, "CANCELEDON", paramFFSConnectionHolder, paramBoolean);
  }
  
  public static ACHBatchInfo updateACHBatchStatus(ACHBatchInfo paramACHBatchInfo, String paramString, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatch.updateACHBatchStatus (isRecurring: " + paramBoolean + ")";
    String str2 = "UPDATE ACH_Batch set BatchStatus = ?  WHERE BatchId = ?";
    String str3 = null;
    FFSDebug.log(str1 + ": start, " + " new status: " + paramString, " isRecurring: " + paramBoolean, 6);
    if (paramBoolean) {
      str2 = "UPDATE ACH_RecBatch set BatchStatus = ?  WHERE RecBatchId = ?";
    } else {
      str2 = "UPDATE ACH_Batch set BatchStatus = ?  WHERE BatchId = ?";
    }
    Object localObject;
    if (paramFFSConnectionHolder == null)
    {
      paramACHBatchInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramACHBatchInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1 + " failed, " + (String)localObject, 0);
      return paramACHBatchInfo;
    }
    try
    {
      str3 = paramACHBatchInfo.getBatchId();
      FFSDebug.log(str1 + ":: batchId=", str3, 6);
      localObject = new Object[] { paramString, str3 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
    }
    catch (Exception localException)
    {
      String str4 = "***  " + str1 + ": failed:";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException, str4);
    }
    paramACHBatchInfo.setBatchStatus(paramString);
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + ": done", 6);
    return paramACHBatchInfo;
  }
  
  public static ACHBatchInfo updateACHBatchPostedOn(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, String paramString1, int paramInt, String paramString2)
    throws FFSException
  {
    String str1 = "ACHBatch.updateACHBatchStatusAndExportFileName ";
    String str2 = "UPDATE ACH_Batch set BatchStatus = ?,  ExportFileName = ?, BatchNum = ? , DtProcessed = ?, ProcessId = ?  WHERE BatchId = ?";
    String str3 = null;
    FFSDebug.log(str1 + ": start, " + " exportFileName: " + paramString1, " batchNum: " + paramInt, " processId: " + paramString2, 6);
    Object localObject;
    if (paramFFSConnectionHolder == null)
    {
      paramACHBatchInfo.setStatusCode(16000);
      localObject = "FFSConnectionHolder object  is null";
      paramACHBatchInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1 + " failed, " + (String)localObject, 0);
      return paramACHBatchInfo;
    }
    try
    {
      str3 = paramACHBatchInfo.getBatchId();
      FFSDebug.log(str1 + ":: batchId=", str3, 6);
      localObject = new Object[] { "POSTEDON", paramString1, new Integer(paramInt), new Integer(BPWUtil.getTodayDBFormat()), paramString2, str3 };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      paramACHBatchInfo.setBatchStatus("POSTEDON");
    }
    catch (Exception localException)
    {
      String str4 = "***  " + str1 + ": failed:";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException, str4);
    }
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + ": done", 6);
    return paramACHBatchInfo;
  }
  
  public static int updateStatusByFIACHId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    int i = 0;
    try
    {
      i = jdMethod_if(paramFFSConnectionHolder, paramString1, paramString2, false);
    }
    catch (Exception localException)
    {
      String str1 = "*** ACHBatch.updateStatusByFIACHId: failed:";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    FFSDebug.log("ACHBatch.updateStatusByFIACHId: done", 6);
    return i;
  }
  
  public static int updateRecBatchStatusByFIACHId(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    int i = 0;
    try
    {
      i = jdMethod_if(paramFFSConnectionHolder, paramString1, paramString2, true);
    }
    catch (Exception localException)
    {
      String str1 = "*** ACHBatch.updateRecBatchStatusByFIACHId: failed:";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, str1);
    }
    FFSDebug.log("ACHBatch.updateRecBatchStatusByFIACHId: done", 6);
    return i;
  }
  
  private static int jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, boolean paramBoolean)
    throws Exception
  {
    String str = null;
    FFSDebug.log("ACHBatch.updateStatusByFIACHId: start, fIId=" + paramString1, 6);
    if (paramString1 == null) {
      return 16000;
    }
    if (paramString2 == null) {
      return 16000;
    }
    if (paramFFSConnectionHolder == null) {
      return 16000;
    }
    Object[] arrayOfObject = { paramString2, paramString1 };
    if (paramBoolean) {
      str = "UPDATE ACH_RecBatch set BatchStatus = ?  WHERE ODFIACHId = ?";
    } else {
      str = "UPDATE ACH_Batch set BatchStatus = ?  WHERE ODFIACHId = ?";
    }
    DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    FFSDebug.log("ACHBatch.updateStatusByFIACHId: done", 6);
    return 0;
  }
  
  private static ACHBatchInfo jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatch.getACHBatchByIdForHistory ";
    FFSDebug.log(str1 + " start", 6);
    ACHBatchInfo localACHBatchInfo = getACHBatchById(paramFFSConnectionHolder, paramString, paramBoolean);
    if (localACHBatchInfo.getStatusCode() != 0) {
      return localACHBatchInfo;
    }
    if ((localACHBatchInfo.getBatchType().equals("Recurring")) && (localACHBatchInfo.getBatchStatus().equals("WILLPROCESSON")))
    {
      String[] arrayOfString = RecSrvrTIDToSrvrTID.getRecSrvrTIDs(paramFFSConnectionHolder, paramString, 1);
      String str2 = "";
      if ((arrayOfString != null) && (arrayOfString.length != 0)) {
        str2 = arrayOfString[0];
      }
      localACHBatchInfo = getACHBatchById(paramFFSConnectionHolder, str2, true);
      localACHBatchInfo.setBatchType("Recurring");
    }
    return localACHBatchInfo;
  }
  
  public static ACHBatchInfo getACHBatchById(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str = "ACHBatch.getACHBatchById ";
    FFSDebug.log(str + " start", 6);
    Object localObject = null;
    if (paramBoolean) {
      localObject = new RecACHBatchInfo();
    } else {
      localObject = new ACHBatchInfo();
    }
    ((ACHBatchInfo)localObject).setBatchId(paramString);
    return getACHBatch((ACHBatchInfo)localObject, paramFFSConnectionHolder, paramBoolean, false, false);
  }
  
  public static ACHBatchInfo getACHBatch(ACHBatchInfo paramACHBatchInfo, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws FFSException
  {
    return getACHBatch(paramACHBatchInfo, paramFFSConnectionHolder, paramBoolean1, paramBoolean2, paramBoolean3, false);
  }
  
  public static ACHBatchInfo getACHBatch(ACHBatchInfo paramACHBatchInfo, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
    throws FFSException
  {
    return getACHBatch(paramACHBatchInfo, paramFFSConnectionHolder, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4, true);
  }
  
  public static ACHBatchInfo getACHBatch(ACHBatchInfo paramACHBatchInfo, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, boolean paramBoolean5)
    throws FFSException
  {
    String str1 = "ACHBatch.getACHBatch ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    if (paramBoolean1)
    {
      if (paramBoolean5) {
        str2 = "SELECT a.RecBatchId, a.CompId, a.CompData, c.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.Frequency, a.StartDate, a.EndDate, a.PmtsCount, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier, a.NonOffTotalCredit, a.NonOffTotalDebit,  a.NonOffRecordCount  FROM ACH_RecBatch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d  WHERE a.CompId = b.CompId AND b.ACHFIId = c.ACHFIId AND c.FIId = d.FIId AND RecBatchId = ? AND BatchStatus != 'CANCELEDON' AND BatchStatus != 'ACHTEMPORARY'";
      } else {
        str2 = "SELECT a.RecBatchId, a.CompId, a.CompData, c.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.Frequency, a.StartDate, a.EndDate, a.PmtsCount, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier, a.NonOffTotalCredit, a.NonOffTotalDebit,  a.NonOffRecordCount  FROM ACH_RecBatch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d  WHERE a.CompId = b.CompId AND b.ACHFIId = c.ACHFIId AND c.FIId = d.FIId AND RecBatchId = ? ";
      }
    }
    else if (paramBoolean5) {
      str2 = "SELECT a.BatchId, a.CompId, a.CompData, c.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate,a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode,a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash,a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName,d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef,a.OrigStatusCode,  a.SrvClassCode, a.ExportFileName,  a.DueDate, a.DtProcessed, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier, a.NonOffTotalCredit, a.NonOffTotalDebit,  a.NonOffRecordCount  FROM ACH_Batch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d  WHERE a.CompId = b.CompId AND b.ACHFIId = c.ACHFIId AND c.FIId = d.FIId AND BatchId = ? AND BatchStatus != 'CANCELEDON' AND BatchStatus != 'ACHTEMPORARY'";
    } else {
      str2 = "SELECT a.BatchId, a.CompId, a.CompData, c.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate,a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode,a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash,a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName,d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef,a.OrigStatusCode,  a.SrvClassCode, a.ExportFileName,  a.DueDate, a.DtProcessed, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier, a.NonOffTotalCredit, a.NonOffTotalDebit,  a.NonOffRecordCount  FROM ACH_Batch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d  WHERE a.CompId = b.CompId AND b.ACHFIId = c.ACHFIId AND c.FIId = d.FIId AND BatchId = ? ";
    }
    FFSDebug.log(str1 + ": start," + " isRecurring = " + paramBoolean1 + "; loadRecords = " + paramBoolean2 + "; parseRecord = " + paramBoolean3 + "; dirtyOnly = " + paramBoolean4 + "; batchId = " + paramACHBatchInfo.getBatchId() + "; excludeCanceled = " + paramBoolean5, 6);
    if (paramFFSConnectionHolder == null)
    {
      paramACHBatchInfo.setStatusCode(16000);
      str3 = "FFSConnectionHolder  is null";
      paramACHBatchInfo.setStatusMsg(str3);
      FFSDebug.log(str1 + ", " + str3, 0);
      return paramACHBatchInfo;
    }
    String str3 = paramACHBatchInfo.getBatchId();
    Object[] arrayOfObject = { str3 };
    try
    {
      if ((paramACHBatchInfo.getRecordCursor() == 0L) || (paramACHBatchInfo.getBatchHeader() == null) || (paramACHBatchInfo.getBatchControl() == null))
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
        if (localFFSResultSet.getNextRow())
        {
          a(paramACHBatchInfo, localFFSResultSet, paramBoolean1, paramFFSConnectionHolder);
          paramACHBatchInfo.setStatusCode(0);
          paramACHBatchInfo.setStatusMsg("Success");
        }
        else
        {
          paramACHBatchInfo.setStatusCode(16020);
          paramACHBatchInfo.setStatusMsg(" record not foundfor batch id:" + str3);
          ACHBatchInfo localACHBatchInfo1 = paramACHBatchInfo;
          return localACHBatchInfo1;
        }
      }
      if (paramBoolean2 == true)
      {
        int i = 0;
        localObject1 = null;
        localObject1 = ACHRecord.getACHRecordsByChunk(str3, paramACHBatchInfo.getRecordCursorStr(), paramACHBatchInfo.getBatchPageSize(), paramFFSConnectionHolder, paramBoolean3, paramBoolean1, paramBoolean4);
        if (((localObject1 == null) || (localObject1.length == 0)) && (paramACHBatchInfo.getBatchType().compareTo("Recurring") == 0))
        {
          String[] arrayOfString = RecSrvrTIDToSrvrTID.getRecSrvrTIDs(paramFFSConnectionHolder, str3, 1);
          if ((arrayOfString == null) || (arrayOfString.length == 0))
          {
            paramACHBatchInfo.setStatusCode(16020);
            paramACHBatchInfo.setStatusMsg(" record not foundfor batch id:" + str3);
            ACHBatchInfo localACHBatchInfo2 = paramACHBatchInfo;
            return localACHBatchInfo2;
          }
          localObject1 = ACHRecord.getACHRecordsByChunk(arrayOfString[0], paramACHBatchInfo.getRecordCursorStr(), paramACHBatchInfo.getBatchPageSize(), paramFFSConnectionHolder, paramBoolean3, true, paramBoolean4);
        }
        if (localObject1 != null) {
          i = localObject1.length;
        }
        paramACHBatchInfo.setRecords((ACHRecordInfo[])localObject1);
        FFSDebug.log(str1 + ", recCount=" + i, 6);
        if (i == 0)
        {
          paramACHBatchInfo.setStatusCode(16020);
          paramACHBatchInfo.setStatusMsg("ACHRecord  record not found for batch id:" + str3);
        }
        else
        {
          long l = Long.parseLong(localObject1[(i - 1)].getRecordId());
          paramACHBatchInfo.setRecordCursor(l);
        }
      }
    }
    catch (Exception localException1)
    {
      Object localObject1 = "***  " + str1 + ": failed:";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, (String)localObject1);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("***  " + str1 + ": failed to close:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1 + "Done.", 6);
    return paramACHBatchInfo;
  }
  
  public static String getBatchStatus(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    return jdMethod_if(paramString, paramFFSConnectionHolder, false);
  }
  
  public static String getRecBatchStatus(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    return jdMethod_if(paramString, paramFFSConnectionHolder, true);
  }
  
  private static String jdMethod_if(String paramString, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    String str1 = null;
    FFSResultSet localFFSResultSet = null;
    String str2 = null;
    if (paramString == null) {
      return null;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = "FFSConnectionHolder  is null";
      FFSDebug.log("ACHBatch.getBatchStatus, " + (String)localObject1, 0);
      return null;
    }
    Object localObject1 = { paramString };
    if (paramBoolean) {
      str1 = "SELECT BatchStatus  FROM ACH_RecBatch  WHERE RecBatchId = ?";
    } else {
      str1 = "SELECT BatchStatus  FROM ACH_Batch  WHERE BatchId = ?";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, (Object[])localObject1);
      if (localFFSResultSet.getNextRow()) {
        if (paramBoolean) {
          str2 = localFFSResultSet.getColumnString("BatchStatus");
        } else {
          str2 = localFFSResultSet.getColumnString("BatchStatus");
        }
      }
    }
    catch (Exception localException1)
    {
      String str3 = "*** ACHBatch.getBatchStatus: failed:";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** ACHBatch.getBatchStatus: failed to close:" + localException2.toString(), 0);
      }
    }
    return str2;
  }
  
  public static int getBatchSize(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    return a(paramString, paramFFSConnectionHolder, false);
  }
  
  public static int getRecBatchSize(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    return a(paramString, paramFFSConnectionHolder, true);
  }
  
  private static int a(String paramString, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    String str1 = null;
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    if (paramString == null) {
      return 0;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = "FFSConnectionHolder  is null";
      FFSDebug.log("ACHBatch.getBatchSize, " + (String)localObject1, 0);
      return -1;
    }
    Object localObject1 = { paramString };
    if (paramBoolean) {
      str1 = "SELECT RecordCount FROM ACH_RecBatch WHERE RecBatchId = ?";
    } else {
      str1 = "SELECT RecordCount FROM ACH_Batch WHERE BatchId = ?";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, (Object[])localObject1);
      if (localFFSResultSet.getNextRow()) {
        if (paramBoolean) {
          i = localFFSResultSet.getColumnInt("RecordCount");
        } else {
          i = localFFSResultSet.getColumnInt("RecordCount");
        }
      }
    }
    catch (Exception localException1)
    {
      String str2 = "*** ACHBatch.getBatchSize: failed:";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, str2);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** ACHBatch.getBatchSize: failed to close:" + localException2.toString(), 0);
      }
    }
    return i;
  }
  
  public static ACHBatchHist getACHBatchHistory(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchHist paramACHBatchHist, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("ACHBatch.getACHBatchHistory : start", 6);
    paramACHBatchHist = a(paramACHBatchHist, "getACHBatchHistory");
    if (paramACHBatchHist.getStatusCode() != 0) {
      return paramACHBatchHist;
    }
    return jdMethod_if(paramFFSConnectionHolder, paramACHBatchHist, null, paramBoolean);
  }
  
  private static ACHBatchHist jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchHist paramACHBatchHist, String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("ACHBatch.getACHBatchHistoryCommon : start", 6);
    FFSDebug.log("ACHBatch.getACHBatchHistoryCommon : Param Values", 6);
    FFSDebug.log("StartDate : " + paramACHBatchHist.getStartDate() + "\nEndDate : " + paramACHBatchHist.getEndDate() + "\nStartEffectiveDate : " + paramACHBatchHist.getStartEffectiveDate() + "\nEndEffectiveDate : " + paramACHBatchHist.getEndEffectiveDate() + "\nCompId : " + paramACHBatchHist.getCompId() + "\nFIId : " + paramACHBatchHist.getFIId(), 6);
    if (paramACHBatchHist.getBatchIdList() != null)
    {
      a(paramFFSConnectionHolder, paramACHBatchHist, paramBoolean);
    }
    else
    {
      if ((paramACHBatchHist.getHistId() == null) || (paramACHBatchHist.getHistId().trim().length() == 0))
      {
        int i = a(paramFFSConnectionHolder, paramACHBatchHist, paramString, paramBoolean);
        if (!paramBoolean) {
          a(paramFFSConnectionHolder, paramACHBatchHist, paramString, i);
        }
        if (paramACHBatchHist.getAscending() == true) {
          paramACHBatchHist.setCursorId("0");
        } else {
          paramACHBatchHist.setCursorId("999999999");
        }
      }
      jdMethod_if(paramFFSConnectionHolder, paramACHBatchHist, paramBoolean);
    }
    return paramACHBatchHist;
  }
  
  private static String a(String paramString, ACHBatchHist paramACHBatchHist, ArrayList paramArrayList)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramString != null) {
      localStringBuffer.append(paramString);
    }
    DBUtil.appendToCondition(localStringBuffer, paramACHBatchHist.getFIId(), " AND c.FIId=? AND c.FIStatus != 'CLOSED'", paramArrayList);
    DBUtil.appendToCondition(localStringBuffer, paramACHBatchHist.getODFIACHId(), " AND c.ODFIACHId =? ", paramArrayList);
    DBUtil.appendToCondition(localStringBuffer, paramACHBatchHist.getCustomerId(), " AND b.CustomerId =? ", paramArrayList);
    DBUtil.appendToCondition(localStringBuffer, paramACHBatchHist.getCompId(), " AND a.CompId =?", paramArrayList);
    DBUtil.appendToCondition(localStringBuffer, paramACHBatchHist.getLogId(), " AND a.LogId =?", paramArrayList);
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHBatchHist.getSubmittedByList(), " AND a.SubmittedBy IN (", paramArrayList);
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHBatchHist.getBatchCategoryList(), " AND BatchCategory IN (", paramArrayList);
    return localStringBuffer.toString();
  }
  
  private static String a(String paramString, ACHBatchHist paramACHBatchHist, ArrayList paramArrayList, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer(a(paramString, paramACHBatchHist, paramArrayList));
    DBUtil.appendArrayToCondition(localStringBuffer, paramACHBatchHist.getBatchStatusList(), " AND BatchStatus IN (", paramArrayList);
    if (paramBoolean == true)
    {
      DBUtil.appendToCondition(localStringBuffer, paramACHBatchHist.getPayeeId(), " AND RecBatchId in ( SELECT BatchId FROM ACH_RecRecord WHERE PayeeId = ?  AND RecordStatus != 'CANCELEDON' )", paramArrayList);
      Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramACHBatchHist.getStartDate()));
      Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramACHBatchHist.getEndDate()));
      DBUtil.appendToCondition(localStringBuffer, localInteger1, " AND StartDate >= ?", paramArrayList);
      DBUtil.appendToCondition(localStringBuffer, localInteger2, " AND StartDate <= ?", paramArrayList);
    }
    else
    {
      DBUtil.appendToCondition(localStringBuffer, paramACHBatchHist.getPayeeId(), " AND BatchId in ( select BatchId from ACH_Record where PayeeId = ?  AND RecordStatus != 'CANCELEDON' )", paramArrayList);
      DBUtil.appendToCondition(localStringBuffer, paramACHBatchHist.getStartEffectiveDate(), " AND EffectiveDate >= ? ", paramArrayList);
      DBUtil.appendToCondition(localStringBuffer, paramACHBatchHist.getEndEffectiveDate(), " AND EffectiveDate <= ? ", paramArrayList);
    }
    return localStringBuffer.toString();
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchHist paramACHBatchHist, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "ACHBatch.makeFutureHistoryForRecBatch: ";
    FFSDebug.log(str1 + "start ", 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      str2 = paramACHBatchHist.getViewerId();
      EntitlementGroupMember localEntitlementGroupMember = null;
      i = (paramACHBatchHist.getViewEntitlementCheck()) && (str2 != null) && (LimitCheckApprovalProcessor.doLimitCheck()) ? 1 : 0;
      if (i != 0) {
        localEntitlementGroupMember = CustomLimitCheckApprovalProcessor.getEntitlementGroupMember(paramACHBatchHist.getViewerId());
      }
      int j = 0;
      String[] arrayOfString1 = paramACHBatchHist.getBatchStatusList();
      if (arrayOfString1 != null)
      {
        int k = arrayOfString1.length;
        for (int m = 0; m < k; m++) {
          if ("WILLPROCESSON".equals(arrayOfString1[m]))
          {
            j = 1;
            break;
          }
        }
      }
      j = 1;
      if (j != 0)
      {
        Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramACHBatchHist.getStartDate()));
        Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramACHBatchHist.getEndDate()));
        PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        int n = localPropertyConfig.getBatchSize();
        int i1 = DBUtil.getCurrentStartDate();
        int i2 = BPWUtil.getDateDBFormat(paramACHBatchHist.getEndDate());
        int i3 = (i2 - i1) / 10000;
        if (i3 >= 0)
        {
          StringBuffer localStringBuffer = new StringBuffer("SELECT a.RecBatchId, a.CompId, a.CompData, c.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.Frequency, a.StartDate, a.EndDate, a.PmtsCount, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier, a.NonOffTotalCredit, a.NonOffTotalDebit,  a.NonOffRecordCount  FROM ACH_RecBatch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d  WHERE BatchStatus != 'CANCELEDON' AND BatchStatus != 'ACHTEMPORARY' AND a.CompId = b.CompId AND b.ACHFIId = c.ACHFIId  AND c.FIId = d.FIId ");
          ArrayList localArrayList = new ArrayList();
          localStringBuffer.append(" AND a.BatchStatus ='WILLPROCESSON' ");
          localStringBuffer.append(a(paramString, paramACHBatchHist, localArrayList));
          DBUtil.appendToCondition(localStringBuffer, paramACHBatchHist.getPayeeId(), " AND RecBatchId in ( SELECT BatchId FROM ACH_RecRecord WHERE PayeeId = ?  AND RecordStatus != 'CANCELEDON' )", localArrayList);
          localArrayList.add(localInteger2);
          localStringBuffer.append(" AND StartDate <= ?");
          localArrayList.add(localInteger1);
          localStringBuffer.append(" AND EndDate >= ?");
          localStringBuffer.append(" ORDER BY RecBatchId");
          Object[] arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
          String str3 = localStringBuffer.toString();
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
          String str4 = null;
          String str5 = null;
          ScheduleInfo localScheduleInfo = null;
          RecACHBatchInfo localRecACHBatchInfo = null;
          while (localFFSResultSet.getNextRow())
          {
            localRecACHBatchInfo = new RecACHBatchInfo();
            a(localRecACHBatchInfo, localFFSResultSet, true, paramFFSConnectionHolder);
            if ((i == 0) || (LimitCheckApprovalProcessor.checkEntitlementACHBatchViewing(localRecACHBatchInfo, localEntitlementGroupMember, null)))
            {
              str4 = localRecACHBatchInfo.getBatchId();
              str5 = localRecACHBatchInfo.getFiId();
              localScheduleInfo = ScheduleInfo.getScheduleInfo(str5, "RECACHBATCHTRN", str4, paramFFSConnectionHolder);
              if ((localScheduleInfo != null) && (localScheduleInfo.StatusOption != 1))
              {
                String str6 = localRecACHBatchInfo.getStartDate();
                int i4 = Integer.parseInt(str6) * 100;
                localScheduleInfo.NextInstanceDate = ScheduleInfo.computeFutureDate(i4, localScheduleInfo.Frequency, localScheduleInfo.CurInstanceNum - 1);
                localScheduleInfo.InstanceCount -= localScheduleInfo.CurInstanceNum - 1;
                localScheduleInfo.CurInstanceNum = 1;
                String[] arrayOfString2 = ScheduleInfo.getPendingDatesByStartAndEndDate(localScheduleInfo, localInteger1.intValue(), localInteger2.intValue());
                for (int i5 = 0; i5 < arrayOfString2.length; i5++)
                {
                  int i6 = Integer.parseInt(arrayOfString2[i5].substring(0, 10));
                  paramInt++;
                  String str7 = DBUtil.getCursor(i6, paramInt);
                  TempHist.create(paramFFSConnectionHolder, paramACHBatchHist.getHistId(), str7, str4, "RECACH", i6, null);
                  if (paramInt % n == 0) {
                    paramFFSConnectionHolder.conn.commit();
                  }
                }
              }
            }
          }
        }
      }
      paramACHBatchHist.setTotalBatches(paramInt);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      String str2 = "***" + str1 + "failed : ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localException1), 0);
      throw new FFSException(localException1, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log("***" + str1 + "failed to close ResultSet " + FFSDebug.stackTrace(localException2), 0);
        }
      }
      FFSDebug.log(str1 + "end", 6);
    }
  }
  
  private static int a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchHist paramACHBatchHist, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatch.makeBatchHistory: ";
    FFSDebug.log(str1 + " start. Recurring: " + paramBoolean, 6);
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    EntitlementGroupMember localEntitlementGroupMember = null;
    String str2 = paramACHBatchHist.getViewerId();
    i = (paramACHBatchHist.getViewEntitlementCheck()) && (str2 != null) && (LimitCheckApprovalProcessor.doLimitCheck()) ? 1 : 0;
    if (i != 0) {
      localEntitlementGroupMember = CustomLimitCheckApprovalProcessor.getEntitlementGroupMember(paramACHBatchHist.getViewerId());
    }
    int j = 0;
    try
    {
      String str3 = null;
      int k = 0;
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int m = localPropertyConfig.getBatchSize();
      ArrayList localArrayList = new ArrayList();
      StringBuffer localStringBuffer = new StringBuffer();
      if (paramBoolean == true) {
        localStringBuffer.append("SELECT a.RecBatchId, a.CompId, a.CompData, c.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.Frequency, a.StartDate, a.EndDate, a.PmtsCount, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier, a.NonOffTotalCredit, a.NonOffTotalDebit,  a.NonOffRecordCount  FROM ACH_RecBatch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d  WHERE BatchStatus != 'CANCELEDON' AND BatchStatus != 'ACHTEMPORARY' AND a.CompId = b.CompId AND b.ACHFIId = c.ACHFIId  AND c.FIId = d.FIId ");
      } else {
        localStringBuffer.append("SELECT a.BatchId, a.CompId, a.CompData, c.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate,a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode,a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash,a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName,d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef,a.OrigStatusCode,  a.SrvClassCode, a.ExportFileName,  a.DueDate, a.DtProcessed, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier, a.NonOffTotalCredit, a.NonOffTotalDebit,  a.NonOffRecordCount  FROM ACH_Batch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d  WHERE a.BatchStatus != 'CANCELEDON' AND a.BatchStatus != 'ACHTEMPORARY' AND a.CompId = b.CompId AND b.ACHFIId = c.ACHFIId  AND c.FIId = d.FIId ");
      }
      localStringBuffer.append(a(paramString, paramACHBatchHist, localArrayList, paramBoolean));
      if (paramBoolean == true) {
        localStringBuffer.append(" ORDER BY RecBatchId");
      } else {
        localStringBuffer.append(" ORDER BY BatchId");
      }
      String str5 = localStringBuffer.toString();
      Object[] arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
      String str6 = DBUtil.getNextIndexString("HistID");
      paramACHBatchHist.setHistId(str6);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str5, arrayOfObject);
      Object localObject1 = null;
      while (localFFSResultSet.getNextRow())
      {
        if (paramBoolean) {
          localObject1 = new RecACHBatchInfo();
        } else {
          localObject1 = new ACHBatchInfo();
        }
        a((ACHBatchInfo)localObject1, localFFSResultSet, paramBoolean, paramFFSConnectionHolder);
        if ((i == 0) || (LimitCheckApprovalProcessor.checkEntitlementACHBatchViewing((ACHBatchInfo)localObject1, localEntitlementGroupMember, null)))
        {
          String str7 = null;
          String str8 = null;
          if (paramBoolean == true)
          {
            k = BPWUtil.getDateDBFormat(((RecACHBatchInfo)localObject1).getStartDate());
            str7 = ((RecACHBatchInfo)localObject1).getBatchId();
            str8 = "RECACH";
          }
          else
          {
            k = BPWUtil.getDateDBFormat(((ACHBatchInfo)localObject1).getDueDate());
            str7 = ((ACHBatchInfo)localObject1).getBatchId();
            str8 = "ACH";
          }
          j++;
          str3 = DBUtil.getCursor(k, j);
          TempHist.create(paramFFSConnectionHolder, str6, str3, str7, str8, k, null);
          if (j % m == 0) {
            paramFFSConnectionHolder.conn.commit();
          }
        }
      }
      paramFFSConnectionHolder.conn.commit();
      paramACHBatchHist.setTotalBatches(j);
      FFSDebug.log(str1 + "end", 6);
    }
    catch (Exception localException1)
    {
      String str4 = "***" + str1 + "failed : ";
      FFSDebug.log(str4 + FFSDebug.stackTrace(localException1), 0);
      throw new FFSException(localException1, str4);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log("***" + str1 + "failed to close ResultSet " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    return j;
  }
  
  private static ACHBatchHist a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchHist paramACHBatchHist, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatch.getACHBatchHistoryByIds: ";
    FFSDebug.log(str1 + " start", 6);
    String str2 = "";
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    EntitlementGroupMember localEntitlementGroupMember = null;
    String str3 = paramACHBatchHist.getViewerId();
    i = (paramACHBatchHist.getViewEntitlementCheck()) && (str3 != null) && (LimitCheckApprovalProcessor.doLimitCheck()) ? 1 : 0;
    if (i != 0) {
      localEntitlementGroupMember = CustomLimitCheckApprovalProcessor.getEntitlementGroupMember(paramACHBatchHist.getViewerId());
    }
    try
    {
      int j = 0;
      localObject1 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int k = ((PropertyConfig)localObject1).getBatchSize();
      if ((paramACHBatchHist.getBatchIdList() != null) && (paramACHBatchHist.getBatchIdList().length > k))
      {
        paramACHBatchHist.setStatusCode(19510);
        paramACHBatchHist.setStatusMsg("List size is bigger than batch size. Please increase batch size and the memory of the database server.");
        localObject2 = paramACHBatchHist;
        return localObject2;
      }
      Object localObject2 = new ArrayList();
      StringBuffer localStringBuffer = new StringBuffer();
      String str4 = "";
      int m = 0;
      if (paramACHBatchHist.getAscending() == true)
      {
        if (paramBoolean == true) {
          localStringBuffer = localStringBuffer.append("SELECT a.RecBatchId, a.CompId, a.CompData, a.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.Frequency, a.StartDate, a.EndDate, a.PmtsCount, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier , a.NonOffTotalCredit, a.NonOffTotalDebit, a.NonOffRecordCount  FROM ACH_RecBatch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d WHERE a.CompId = b.CompId AND a.ODFIACHId = c.ODFIACHId AND c.FIId = d.FIId AND BatchStatus != 'CANCELEDON' AND BatchStatus != 'ACHTEMPORARY' AND StartDate > ? and RecBatchId > ? ");
        } else {
          localStringBuffer = localStringBuffer.append("SELECT a.BatchId, a.CompId, a.CompData, a.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.ExportFileName,  a.DueDate, a.DtProcessed, a.Memo, c.FIId, b.CustomerId, a.OffsetAccountID,  a.HeaderCompName, a.LastModifier , a.NonOffTotalCredit, a.NonOffTotalDebit, a.NonOffRecordCount  FROM ACH_Batch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d WHERE a.CompId = b.CompId AND a.ODFIACHId = c.ODFIACHId AND c.FIId = d.FIId AND BatchStatus != 'CANCELEDON' AND BatchStatus != 'ACHTEMPORARY' AND DtProcessed > ? and BatchId > ? ");
        }
        m = 0;
        str4 = FFSUtil.padWithChar("0", 32, true, '0');
      }
      else
      {
        if (paramBoolean == true) {
          localStringBuffer = localStringBuffer.append("SELECT a.RecBatchId, a.CompId, a.CompData, a.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.Frequency, a.StartDate, a.EndDate, a.PmtsCount, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier  FROM ACH_RecBatch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d WHERE a.CompId = b.CompId AND a.ODFIACHId = c.ODFIACHId AND c.FIId = d.FIId AND BatchStatus != 'CANCELEDON' AND BatchStatus != 'ACHTEMPORARY' AND StartDate < ? and RecBatchId < ? ");
        } else {
          localStringBuffer = localStringBuffer.append("SELECT a.BatchId, a.CompId, a.CompData, a.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.ExportFileName,  a.DueDate, a.DtProcessed, a.Memo, c.FIId, b.CustomerId, a.OffsetAccountID,  a.HeaderCompName, a.LastModifier  FROM ACH_Batch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d WHERE a.CompId = b.CompId AND a.ODFIACHId = c.ODFIACHId AND c.FIId = d.FIId AND BatchStatus != 'CANCELEDON' AND BatchStatus != 'ACHTEMPORARY' AND DtProcessed < ? and BatchId < ? ");
        }
        m = 2147483647;
        str4 = FFSUtil.padWithChar("9", 32, true, '9');
      }
      FFSDebug.log(str1 + " dtProcessedCursor: " + m, 6);
      FFSDebug.log(str1 + " batchIdCursor: " + str4, 6);
      ((ArrayList)localObject2).add(new Integer(m));
      ((ArrayList)localObject2).add(str4);
      localStringBuffer.append(a(str2, paramACHBatchHist, (ArrayList)localObject2, paramBoolean));
      if (paramBoolean == true) {
        DBUtil.appendArrayToCondition(localStringBuffer, paramACHBatchHist.getBatchIdList(), " AND RecBatchId IN (", (ArrayList)localObject2);
      } else {
        DBUtil.appendArrayToCondition(localStringBuffer, paramACHBatchHist.getBatchIdList(), " AND BatchId IN (", (ArrayList)localObject2);
      }
      if (paramACHBatchHist.getAscending() == true)
      {
        if (paramBoolean == true) {
          localStringBuffer.append(" ORDER BY StartDate ASC, RecBatchId ASC");
        } else {
          localStringBuffer.append(" ORDER BY DtProcessed ASC, BatchId ASC");
        }
      }
      else if (paramBoolean == true) {
        localStringBuffer.append(" ORDER BY StartDate DESC, RecBatchId DESC");
      } else {
        localStringBuffer.append(" ORDER BY DtProcessed DESC, BatchId DESC");
      }
      String str5 = localStringBuffer.toString();
      Object[] arrayOfObject = (Object[])((ArrayList)localObject2).toArray(new Object[0]);
      String str6 = DBUtil.getNextIndexString("HistID");
      paramACHBatchHist.setHistId(str6);
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str5, arrayOfObject);
      ArrayList localArrayList = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        j++;
        Object localObject3 = null;
        if (paramBoolean == true) {
          localObject3 = new RecACHBatchInfo();
        } else {
          localObject3 = new ACHBatchInfo();
        }
        a((ACHBatchInfo)localObject3, localFFSResultSet, paramBoolean, paramFFSConnectionHolder);
        if ((i == 0) || (LimitCheckApprovalProcessor.checkEntitlementACHBatchViewing((ACHBatchInfo)localObject3, localEntitlementGroupMember, null)))
        {
          ((ACHBatchInfo)localObject3).setStatusCode(0);
          ((ACHBatchInfo)localObject3).setStatusMsg("Success");
          localArrayList.add(localObject3);
        }
      }
      paramACHBatchHist.setTotalBatches(j);
      paramACHBatchHist.setBatchPageSize(j);
      paramACHBatchHist.setStatusCode(0);
      paramACHBatchHist.setStatusMsg("Success");
      if (paramBoolean == true) {
        paramACHBatchHist.setBatches((RecACHBatchInfo[])localArrayList.toArray(new RecACHBatchInfo[0]));
      } else {
        paramACHBatchHist.setBatches((ACHBatchInfo[])localArrayList.toArray(new ACHBatchInfo[0]));
      }
      FFSDebug.log(str1 + "end", 6);
    }
    catch (Exception localException1)
    {
      Object localObject1 = "****" + str1 + "failed : ";
      FFSDebug.log((String)localObject1 + FFSDebug.stackTrace(localException1), 0);
      throw new FFSException(localException1, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log("****" + str1 + "failed to close ResultSet " + FFSDebug.stackTrace(localException2), 0);
        }
      }
    }
    return paramACHBatchHist;
  }
  
  private static void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchHist paramACHBatchHist, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("ACHBatch.getHistory : start", 6);
    FFSDebug.log("ACHBatch.getHistory : Parameter values \nHistId : " + paramACHBatchHist.getHistId() + "\nCursorId : " + paramACHBatchHist.getCursorId() + "\nBatch Page Size : " + paramACHBatchHist.getBatchPageSize(), 6);
    ArrayList localArrayList = new ArrayList();
    ACHBatchInfo localACHBatchInfo = null;
    try
    {
      TempHist[] arrayOfTempHist = TempHist.get(paramFFSConnectionHolder, paramACHBatchHist.getHistId(), paramACHBatchHist.getCursorId(), paramACHBatchHist.getBatchPageSize(), paramACHBatchHist.getAscending());
      int i = arrayOfTempHist.length;
      for (int j = 0; j < i; j++)
      {
        if (arrayOfTempHist[j].RecordType.equals("ACH"))
        {
          localACHBatchInfo = jdMethod_for(paramFFSConnectionHolder, arrayOfTempHist[j].RecordExtId, false);
          if ((localACHBatchInfo.getStatusCode() == 0) && (localACHBatchInfo.getBatchType() != null) && (localACHBatchInfo.getBatchType().equals("Recurring"))) {
            localACHBatchInfo.setDtProcessed(BPWUtil.getDateBeanFormat(arrayOfTempHist[j].DueDate));
          }
          localACHBatchInfo.setRecordCursor(arrayOfTempHist[j].CursorId);
        }
        else if (arrayOfTempHist[j].RecordType.equals("RECACH"))
        {
          localACHBatchInfo = getACHBatchById(paramFFSConnectionHolder, arrayOfTempHist[j].RecordExtId, true);
          localACHBatchInfo.setDtProcessed(BPWUtil.getDateBeanFormat(arrayOfTempHist[j].DueDate));
          localACHBatchInfo.setRecordCursor(arrayOfTempHist[j].CursorId);
          localACHBatchInfo.setBatchType("Recurring");
          localACHBatchInfo.setRecBatchId(arrayOfTempHist[j].RecordExtId);
        }
        else
        {
          localACHBatchInfo = new ACHBatchInfo();
          localACHBatchInfo.setStatusCode(23190);
          localACHBatchInfo.setStatusMsg("Invalid History Record Type");
          FFSDebug.log("ACHBatch.getHistory : Invalid History Record Type : RecordExtId = " + arrayOfTempHist[j].RecordExtId + "RecordType = " + arrayOfTempHist[j].RecordType, 0);
        }
        if ((localACHBatchInfo != null) && (localACHBatchInfo.getStatusCode() == 0)) {
          localArrayList.add(localACHBatchInfo);
        }
      }
      if (paramBoolean) {
        paramACHBatchHist.setBatches((RecACHBatchInfo[])localArrayList.toArray(new RecACHBatchInfo[0]));
      } else {
        paramACHBatchHist.setBatches((ACHBatchInfo[])localArrayList.toArray(new ACHBatchInfo[0]));
      }
      if (paramACHBatchHist.getTotalBatches() == 0L) {
        paramACHBatchHist.setTotalBatches(TempHist.getCount(paramFFSConnectionHolder, paramACHBatchHist.getHistId()));
      }
      if (localArrayList.size() == 0)
      {
        FFSDebug.log("ACHBatch.getHistory : No history record found", 6);
        paramACHBatchHist.setStatusCode(16020);
        paramACHBatchHist.setStatusMsg("ACHBatchHistory  record not found");
      }
      else
      {
        paramACHBatchHist.setCursorId(arrayOfTempHist[(arrayOfTempHist.length - 1)].CursorId);
        paramACHBatchHist.setStatusCode(0);
        paramACHBatchHist.setStatusMsg("Success");
      }
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      String str = "ACHBatch.getHistory : failed:  " + localException.getMessage();
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, str);
    }
  }
  
  private static ACHBatchHist a(ACHBatchHist paramACHBatchHist, String paramString)
  {
    if (paramACHBatchHist == null)
    {
      paramACHBatchHist = new ACHBatchHist();
      paramACHBatchHist.setStatusCode(16000);
      paramACHBatchHist.setStatusMsg("ACHBatchHist  is null");
      FFSDebug.log("ACHBatch." + paramString + " : " + "Null ACHBatchHist passed", 0);
      return paramACHBatchHist;
    }
    a(paramACHBatchHist);
    String str;
    if (!BPWUtil.checkFrontEndDateFormat(paramACHBatchHist.getStartDate()))
    {
      paramACHBatchHist.setStatusCode(17205);
      str = paramString + ":" + "Invalid start date value for getting histories." + ": " + paramACHBatchHist.getStartDate();
      paramACHBatchHist.setStatusMsg(str);
      FFSDebug.log(str, 0);
      return paramACHBatchHist;
    }
    if (!BPWUtil.checkFrontEndDateFormat(paramACHBatchHist.getEndDate()))
    {
      paramACHBatchHist.setStatusCode(17206);
      str = paramString + ":" + "Invalid end date value for getting histories." + ": " + paramACHBatchHist.getEndDate();
      paramACHBatchHist.setStatusMsg(str);
      FFSDebug.log(str, 0);
      return paramACHBatchHist;
    }
    FFSDebug.log("ACHBatch." + paramString + " : StartDate:", paramACHBatchHist.getStartDate(), "EndDate:", paramACHBatchHist.getEndDate(), 6);
    if (Integer.parseInt(paramACHBatchHist.getStartDate()) > Integer.parseInt(paramACHBatchHist.getEndDate()))
    {
      paramACHBatchHist.setStatusCode(17080);
      paramACHBatchHist.setStatusMsg("Start date cannot be after end date");
      FFSDebug.log("ACHBatch." + paramString + " : " + " Start Date is later than End Date", 0);
      return paramACHBatchHist;
    }
    paramACHBatchHist.setStatusCode(0);
    paramACHBatchHist.setStatusMsg("Success");
    return paramACHBatchHist;
  }
  
  private static void a(ACHBatchHist paramACHBatchHist)
  {
    if ((paramACHBatchHist.getStartDate() == null) || (paramACHBatchHist.getStartDate().trim().length() == 0)) {
      paramACHBatchHist.setStartDate(new String("0000000000"));
    } else if (paramACHBatchHist.getStartDate().trim().length() == 8) {
      paramACHBatchHist.setStartDate(paramACHBatchHist.getStartDate().trim() + "00");
    }
    if ((paramACHBatchHist.getEndDate() == null) || (paramACHBatchHist.getEndDate().trim().length() == 0))
    {
      Integer localInteger = new Integer(DBUtil.getCurrentStartDate() + 1000000);
      paramACHBatchHist.setEndDate(localInteger.toString());
    }
    else if (paramACHBatchHist.getEndDate().trim().length() == 8)
    {
      paramACHBatchHist.setEndDate(paramACHBatchHist.getEndDate().trim() + "00");
    }
  }
  
  public static String getNextACHBatchId()
    throws Exception
  {
    return DBUtil.getNextIndexStringWithPadding("BatchID", 32, '0');
  }
  
  public static ACHBatchInfo generateACHBatchFromACHRecBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    ACHBatchInfo localACHBatchInfo = getACHBatchById(paramFFSConnectionHolder, paramString1, true);
    if (localACHBatchInfo.getStatusCode() != 0) {
      return localACHBatchInfo;
    }
    localACHBatchInfo.setDueDate(paramString2);
    localACHBatchInfo.setDtProcessed(DBUtil.getACHPayday(localACHBatchInfo.getFiId(), paramString2));
    localACHBatchInfo.setBatchType("Recurring");
    localACHBatchInfo.setBatchHeaderFieldValueObject(9, paramString3);
    localACHBatchInfo.setLogId(BPWTrackingIDGenerator.getNextId());
    localACHBatchInfo.setRecBatchId(paramString1);
    localACHBatchInfo = addACHBatch(localACHBatchInfo, paramFFSConnectionHolder, false);
    if (localACHBatchInfo.getStatusCode() != 0) {
      return localACHBatchInfo;
    }
    updateACHBatchStatus(localACHBatchInfo, "WILLPROCESSON", paramFFSConnectionHolder, false);
    if (localACHBatchInfo.getStatusCode() != 0) {
      return localACHBatchInfo;
    }
    RecSrvrTIDToSrvrTID.create(paramFFSConnectionHolder, paramString1, localACHBatchInfo.getBatchId(), 1);
    ACHRecord.generateACHRecordsFromACHRecRecords(paramFFSConnectionHolder, paramString1, localACHBatchInfo.getBatchId());
    localACHBatchInfo = getACHBatch(localACHBatchInfo, paramFFSConnectionHolder, false, true, true);
    return localACHBatchInfo;
  }
  
  public static void delACHBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatch.delACHBatch ";
    String str2 = null;
    try
    {
      if (paramBoolean) {
        str2 = "DELETE FROM ACH_RecBatch WHERE RecBatchId = ?";
      } else {
        str2 = "DELETE FROM ACH_Batch WHERE BatchId = ?";
      }
      Object[] arrayOfObject = { paramString };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str3 = "***  " + str1 + ": failed:";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str3);
    }
  }
  
  public static int cleanIncompleteBatches(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    String str1 = "ACHBatch.cleanIncompleteBatches ";
    int i = 0;
    String str2 = null;
    if (paramFFSConnectionHolder == null)
    {
      String str3 = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1 + " failed, " + str3, 0);
      throw new FFSException(16000, str3);
    }
    try
    {
      paramInt += 1440;
      str2 = FFSUtil.getCutOffTime(paramInt);
      i += jdMethod_do(paramFFSConnectionHolder, str2, false);
      i += jdMethod_do(paramFFSConnectionHolder, str2, true);
    }
    catch (Exception localException)
    {
      String str4 = "*** " + str1 + ": failed:";
      String str5 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException, str4);
    }
    return i;
  }
  
  private static int jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatch.cleanIncompleteBatches (isRecurring) ";
    int i = 0;
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    String str3 = null;
    if (paramBoolean) {
      str2 = "SELECT RecBatchId  FROM ACH_RecBatch  WHERE BatchStatus = 'ACHTEMPORARY' AND SubmitDate < ?";
    } else {
      str2 = "SELECT BatchId  FROM ACH_Batch  WHERE BatchStatus = 'ACHTEMPORARY' AND SubmitDate < ?";
    }
    try
    {
      int j = 0;
      FFSDebug.log(str1, "cutOffTime:" + paramString, 6);
      FFSDebug.log(str1, "SQL:", str2, 6);
      localObject1 = new Object[] { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      while (localFFSResultSet.getNextRow())
      {
        if (paramBoolean) {
          str3 = localFFSResultSet.getColumnString("RecBatchId");
        } else {
          str3 = localFFSResultSet.getColumnString("BatchId");
        }
        FFSDebug.log(str1, "Batch ID:", str3, 6);
        localObject2 = ACHRecord.getACHRecordsByChunk(str3, null, 0L, paramFFSConnectionHolder, false, paramBoolean);
        if (localObject2 != null) {
          j = localObject2.length;
        }
        FFSDebug.log(str1, "recCount:" + j, 6);
        if (j <= 200) {
          ACHRecord.delACHRecord(paramFFSConnectionHolder, str3, paramBoolean);
        } else {
          for (int k = 0; k < j; k++)
          {
            ACHRecord.delACHRecord(paramFFSConnectionHolder, localObject2[k], paramBoolean);
            if (k % 200 == 0)
            {
              FFSDebug.log(str1, "Before chunk commit, i:" + k, 6);
              paramFFSConnectionHolder.conn.commit();
            }
          }
        }
        delACHBatch(paramFFSConnectionHolder, str3, paramBoolean);
        paramFFSConnectionHolder.conn.commit();
        i++;
      }
    }
    catch (Exception localException1)
    {
      Object localObject1 = "*** " + str1 + ": failed:";
      Object localObject2 = FFSDebug.stackTrace(localException1);
      FFSDebug.log((String)localObject2, 0);
      throw new FFSException(localException1, (String)localObject1);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** " + str1 + " close rset failed : " + FFSDebug.stackTrace(localException2), 0);
      }
    }
    return i;
  }
  
  public static boolean checkDirtyBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = null;
    String str2 = "ACHBatch.checkDirtyBatch: ";
    FFSResultSet localFFSResultSet = null;
    boolean bool = false;
    FFSDebug.log(str2 + ": Start. batchId:" + paramString, 6);
    if (paramString == null) {
      return bool;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = "FFSConnectionHolder  is null";
      FFSDebug.log(str2 + (String)localObject1, 0);
      return bool;
    }
    Object localObject1 = { paramString };
    if (paramBoolean) {
      str1 = "SELECT batch.RecBatchId  FROM ACH_RecBatch batch, ACH_RecRecord record  WHERE batch.RecBatchId = ? AND batch.RecBatchId = record.BatchId AND record.DirtyFlag = 1";
    } else {
      str1 = "SELECT batch.BatchId FROM ACH_Batch batch, ACH_Record record  WHERE batch.BatchId = ? AND batch.BatchId = record.BatchId AND record.DirtyFlag = 1";
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, (Object[])localObject1);
      if (localFFSResultSet.getNextRow()) {
        bool = true;
      }
    }
    catch (Exception localException1)
    {
      String str3 = "*** " + str2 + " failed:";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("***" + str2 + " failed to close:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str2 + ": Done. is dirty:" + bool, 6);
    return bool;
  }
  
  public static String getACHBatchType(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHBatch.getBatchType: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    String str3 = null;
    FFSDebug.log(str1 + ": Starts. batchId: " + paramString, 6);
    if (paramString == null) {
      return null;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = "FFSConnectionHolder  is null";
      FFSDebug.log(str1 + (String)localObject1, 0);
      return null;
    }
    Object localObject1 = { paramString };
    str2 = "SELECT BatchType  FROM ACH_Batch  WHERE BatchId = ?";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (localFFSResultSet.getNextRow()) {
        str3 = localFFSResultSet.getColumnString("BatchType");
      }
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed:";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException1, str4);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** " + str1 + "failed to close:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1 + ": Done. batchType: " + str3, 6);
    return str3;
  }
  
  public static String[] getUnprocessedSingleIds(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHBatch.getUnprocessedSingleIds: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    FFSDebug.log(str1 + ": Starts. recBatchId: " + paramString, 6);
    if (paramString == null) {
      return null;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = "FFSConnectionHolder  is null";
      FFSDebug.log(str1 + (String)localObject1, 0);
      return null;
    }
    Object localObject1 = { paramString, new Integer(1) };
    str2 = "SELECT BatchId  FROM ACH_Batch  WHERE ( BatchStatus = 'WILLPROCESSON' OR BatchStatus = 'APPROVAL_PENDING' OR BatchStatus = 'APPROVAL_REJECTED' ) AND BatchId in ( SELECT SrvrTID FROM BPW_RecSrvrTIDToSrvrTID WHERE RecSrvrTID=? AND IndexType=?)";
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      while (localFFSResultSet.getNextRow()) {
        localArrayList.add(localFFSResultSet.getColumnString("BatchId"));
      }
    }
    catch (Exception localException1)
    {
      String str3 = "*** " + str1 + " failed:";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** " + str1 + "failed to close:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1 + ": Done. ", 6);
    return (String[])localArrayList.toArray(new String[0]);
  }
  
  public static ACHBatchInfo[] getActiveACHBatchesByPayeeId(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatch.getActiveACHBatchesByPayeeId: ";
    FFSDebug.log(str1 + "start. PayeeId: " + paramString, 6);
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    String str2 = "SELECT a.BatchId, a.CompId, a.CompData, a.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.ExportFileName,  a.DueDate, a.DtProcessed, a.Memo, c.FIId, b.CustomerId, a.OffsetAccountID,  a.HeaderCompName, a.LastModifier , a.NonOffTotalCredit, a.NonOffTotalDebit, a.NonOffRecordCount  FROM ACH_Batch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d, ACH_Record e WHERE a.CompId = b.CompId AND a.BatchId = e.BatchId AND e.PayeeId = ?  AND a.ODFIACHId = c.ODFIACHId AND c.FIId = d.FIId AND a.BatchStatus != 'CANCELEDON' AND e.RecordStatus != 'CANCELEDON' AND a.BatchStatus != 'ACHTEMPORARY' AND a.BatchStatus != 'POSTEDON' AND a.BatchStatus != 'LIMIT_CHECK_FAILED' AND a.BatchStatus != 'LIMIT_REVERT_FAILED'";
    if (paramBoolean == true) {
      str2 = "SELECT a.RecBatchId, a.CompId, a.CompData, a.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.Frequency, a.StartDate, a.EndDate, a.PmtsCount, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier , a.NonOffTotalCredit, a.NonOffTotalDebit, a.NonOffRecordCount  FROM ACH_RecBatch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d, ACH_RecRecord e  WHERE a.CompId = b.CompId AND a.RecBatchId = e.BatchId  AND e.PayeeId = ?  AND a.ODFIACHId = c.ODFIACHId AND c.FIId = d.FIId AND BatchStatus != 'CANCELEDON' AND e.RecordStatus != 'CANCELEDON' AND BatchStatus != 'ACHTEMPORARY' AND a.BatchStatus != 'POSTEDON' AND a.BatchStatus != 'LIMIT_CHECK_FAILED' AND a.BatchStatus != 'LIMIT_REVERT_FAILED'";
    }
    Object[] arrayOfObject = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        Object localObject1 = null;
        if (!paramBoolean) {
          localObject1 = new ACHBatchInfo();
        } else {
          localObject1 = new RecACHBatchInfo();
        }
        a((ACHBatchInfo)localObject1, localFFSResultSet, paramBoolean, paramFFSConnectionHolder);
        localArrayList.add(localObject1);
      }
    }
    catch (Exception localException1)
    {
      String str3 = "*** " + str1 + " failed: " + localException1.toString();
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** " + str1 + "failed to close:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1 + ": done. ", 6);
    return (ACHBatchInfo[])localArrayList.toArray(new ACHBatchInfo[0]);
  }
  
  public static int getBatchLeadDays(int paramInt, CustomerInfo paramCustomerInfo)
  {
    String str = "ACHBatch.getBatchLeadDays :";
    FFSDebug.log(str + " srvClassCode =" + paramInt + " CustomerId: " + paramCustomerInfo.customerID, 6);
    int i = 0;
    if (paramInt == 220)
    {
      i = paramCustomerInfo.ACHCreditLeadDays;
    }
    else if (paramInt == 225)
    {
      i = paramCustomerInfo.ACHDebitLeadDays;
    }
    else if (paramInt == 200)
    {
      i = paramCustomerInfo.ACHCreditLeadDays;
      if (paramCustomerInfo.ACHCreditLeadDays < paramCustomerInfo.ACHDebitLeadDays) {
        i = paramCustomerInfo.ACHDebitLeadDays;
      }
    }
    FFSDebug.log(str + " batchLeadDays =" + i, 6);
    return i;
  }
  
  public static int getNextInstanceDateForScheduleInfo(String paramString, int paramInt, HashMap paramHashMap)
    throws FFSException
  {
    int i = 0;
    BPWFIInfo localBPWFIInfo = (BPWFIInfo)paramHashMap.get("BPW_FIINFO");
    int j = localBPWFIInfo.getACHTransWareHouse();
    if (j == 1) {
      i = a(localBPWFIInfo);
    } else {
      i = getNextInstanceDateInBPWWarehouse(paramString, paramInt, paramHashMap);
    }
    return i;
  }
  
  private static int a(BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    String str1 = "ACHBatch.getNextInstanceDateInBankWarehouse: ";
    FFSDebug.log(str1 + " start. FIID: " + paramBPWFIInfo.getFIId(), 6);
    int i = 0;
    try
    {
      i = SmartCalendar.getACHPayDayOfToday(paramBPWFIInfo.getFIId());
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + "failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str1 + str3, 0);
      throw new FFSException(localException, str2);
    }
    i *= 100;
    FFSDebug.log(str1 + "done. NextInstanceDate: " + i, 6);
    return i;
  }
  
  public static int getNextInstanceDateInBPWWarehouse(String paramString, int paramInt, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "ACHBatch.getNextInstanceDateInBPWWarehouse: ";
    FFSDebug.log(str1 + " start. Date: " + paramString + " ServiceClassCode: " + paramInt, 6);
    int i = 0;
    try
    {
      CustomerInfo localCustomerInfo = (CustomerInfo)paramHashMap.get("BPW_CUSTOMER");
      localObject = (BPWFIInfo)paramHashMap.get("BPW_FIINFO");
      int j = getBatchLeadDays(paramInt, localCustomerInfo);
      int k = SmartCalendar.getACHPayDayOfToday(((BPWFIInfo)localObject).getFIId());
      int m = Integer.parseInt(paramString);
      i = m;
      for (int n = 0; n < j; n++)
      {
        i = SmartCalendar.getACHBusinessDay(((BPWFIInfo)localObject).getFIId(), i, false);
        if (i < k)
        {
          i = k;
          break;
        }
      }
    }
    catch (Exception localException)
    {
      Object localObject = "*** " + str1 + "failed: ";
      String str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, 0);
      throw new FFSException(localException, (String)localObject);
    }
    i *= 100;
    FFSDebug.log(str1 + "done. NextInstanceDate: " + i, 6);
    return i;
  }
  
  public static ACHBatchInfo[] getIncompleteBatches(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatch.getIncompleteBatches ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    Vector localVector = new Vector();
    if (paramBoolean) {
      str2 = "SELECT a.RecBatchId, a.CompId, a.CompData, c.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.Frequency, a.StartDate, a.EndDate, a.PmtsCount, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier, a.NonOffTotalCredit, a.NonOffTotalDebit, a.NonOffRecordCount  FROM ACH_RecBatch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d WHERE a.CompId = b.CompId AND b.ACHFIId = c.ACHFIId AND c.FIId = d.FIId  AND a.BatchStatus = 'ACHTEMPORARY' AND a.SubmitDate < ?";
    } else {
      str2 = "SELECT a.BatchId, a.CompId, a.CompData, c.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.ExportFileName,  a.DueDate, a.DtProcessed, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier , a.NonOffTotalCredit, a.NonOffTotalDebit, a.NonOffRecordCount  FROM ACH_Batch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d WHERE a.CompId = b.CompId AND b.ACHFIId = c.ACHFIId AND c.FIId = d.FIId  AND a.BatchStatus = 'ACHTEMPORARY' AND a.SubmitDate < ?";
    }
    try
    {
      FFSDebug.log(str1 + "isRecurring: " + paramBoolean, 6);
      FFSDebug.log(str1, "cutOffTime:" + paramString, 6);
      FFSDebug.log(str1, "SQL:", str2, 6);
      Object[] arrayOfObject = { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        if (paramBoolean == true)
        {
          localObject1 = new RecACHBatchInfo();
          a((ACHBatchInfo)localObject1, localFFSResultSet, paramBoolean, paramFFSConnectionHolder);
          localVector.addElement(localObject1);
        }
        else
        {
          localObject1 = new ACHBatchInfo();
          a((ACHBatchInfo)localObject1, localFFSResultSet, paramBoolean, paramFFSConnectionHolder);
          localVector.addElement(localObject1);
        }
      }
    }
    catch (Exception localException1)
    {
      Object localObject1 = "*** " + str1 + ": failed:";
      String str3 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException1, (String)localObject1);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** " + str1 + " close rset failed : " + FFSDebug.stackTrace(localException2), 0);
      }
    }
    FFSDebug.log(str1 + "done. Number of batches: " + localVector.size(), 6);
    if (localVector.size() == 0) {
      return null;
    }
    if (paramBoolean == true) {
      return (RecACHBatchInfo[])localVector.toArray(new RecACHBatchInfo[0]);
    }
    return (ACHBatchInfo[])localVector.toArray(new ACHBatchInfo[0]);
  }
  
  public static void doTransAuditLog(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, String paramString1, String paramString2, int paramInt)
    throws FFSException
  {
    String str1 = "ACHBatch.doTransAuditLog:";
    long l = paramACHBatchInfo.getNonOffBatchCreditSum() + paramACHBatchInfo.getNonOffBatchDebitSum();
    BigDecimal localBigDecimal = BigDecimal.valueOf(l).movePointLeft(2);
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
    String str2 = paramString1 + ", Batch Category = " + paramACHBatchInfo.getBatchCategory() + ", Batch type = " + paramACHBatchInfo.getBatchType() + ", Batch balanced type= " + paramACHBatchInfo.getBatchBalanceType();
    FFSDebug.log(str1 + str2, 6);
    Object localObject = new AuditLogRecord(paramString2, null, null, str2, paramACHBatchInfo.getLogId(), paramInt, i, localBigDecimal, null, paramACHBatchInfo.getBatchId(), paramACHBatchInfo.getBatchStatus(), null, null, null, null, 0);
    TransAuditLog.logTransAuditLog((AuditLogRecord)localObject, paramFFSConnectionHolder.conn.getConnection());
  }
  
  public static boolean checkCompEntryDesc(ACHBatchInfo paramACHBatchInfo)
  {
    String str1 = (String)paramACHBatchInfo.getBatchHeaderFieldValueObject(7);
    if (str1 == null)
    {
      paramACHBatchInfo.setStatusCode(16000);
      paramACHBatchInfo.setStatusMsg("CompEntryDesc  is null");
      return false;
    }
    str1 = str1.trim();
    String str2 = paramACHBatchInfo.getClassCode();
    if (str2 == null)
    {
      paramACHBatchInfo.setStatusCode(16000);
      paramACHBatchInfo.setStatusMsg("ClassCode  is null");
      return false;
    }
    if (str2.equals("XCK"))
    {
      if (str1.compareTo("NO CHECK") != 0)
      {
        paramACHBatchInfo.setStatusCode(17208);
        paramACHBatchInfo.setStatusMsg("Company Entry Description for XCK records must contain NO CHECK, But we got :" + str1);
        return false;
      }
    }
    else if ((str2.equals("RCK")) && (!"ACH_BATCH_REVERSAL".equals(paramACHBatchInfo.getBatchCategory())) && (str1.compareTo("REDEPCHECK") != 0))
    {
      paramACHBatchInfo.setStatusCode(17209);
      paramACHBatchInfo.setStatusMsg("Company Entry Description for RCK records must contain REDEPCHECK, But we got :" + str1);
      return false;
    }
    paramACHBatchInfo.setStatusCode(0);
    paramACHBatchInfo.setStatusMsg("Success");
    return true;
  }
  
  public static void setCompNameInBatchHeader(ACHBatchInfo paramACHBatchInfo)
  {
    String str = paramACHBatchInfo.getHeaderCompName();
    FFSDebug.log("ACHBatch.setCompNameInBatchHeader : HeaderCompName =" + str, 6);
    if ((str == null) || (str.trim().length() == 0)) {
      str = paramACHBatchInfo.getCompName();
    }
    FFSDebug.log("ACHBatch.setCompNameInBatchHeader : CompanyName =" + str, 6);
    paramACHBatchInfo.setBatchHeaderFieldValueObject(3, str);
  }
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, int paramInt, boolean paramBoolean, String paramString2)
    throws FFSException
  {
    String str1 = "ACHBatch.cleanup ";
    FFSDebug.log(str1 + " start...", 6);
    int i = 1;
    if ((paramString1 != null) && (paramString1.trim().length() != 0)) {
      i = 0;
    } else {
      i = 1;
    }
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String[] arrayOfString = null;
    int j = 0;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd00");
    String str6 = localSimpleDateFormat.format(localCalendar.getTime());
    int k = Integer.parseInt(str6);
    FFSDebug.log(str1 + "AgeInDate format = " + str6, 6);
    Object[] arrayOfObject = null;
    if (i != 0) {
      arrayOfObject = new Object[] { new Integer(str6), paramString2 };
    } else {
      arrayOfObject = new Object[] { new Integer(str6), paramString2, paramString1 };
    }
    try
    {
      if (paramBoolean)
      {
        int m;
        if (i != 0)
        {
          arrayOfString = jdMethod_void(paramFFSConnectionHolder, String.valueOf(k), paramString1);
          for (m = 0; m < arrayOfString.length; m++) {
            RecSrvrTIDToSrvrTID.deleteByRecSrvrTID(paramFFSConnectionHolder, arrayOfString[m]);
          }
          str2 = "DELETE FROM ACH_RecAddenda WHERE RecordId IN (SELECT B.RecRecordId FROM ACH_RecRecord B WHERE (B.BatchId IN (SELECT C.RecBatchId FROM ACH_RecBatch C WHERE C.BatchStatus = 'POSTEDON' AND C.StartDate <= ? AND C.BatchCategory = ? AND C.CompId IN (SELECT D.CompId FROM ACH_Company D ))))";
          str3 = "DELETE FROM ACH_RecRecord WHERE (BatchId IN (SELECT B.RecBatchId FROM ACH_RecBatch B WHERE B.BatchStatus = 'POSTEDON' AND B.StartDate <= ? AND B.BatchCategory = ? AND B.CompId IN (SELECT C.CompId FROM ACH_Company C )))";
          str4 = "DELETE FROM ACH_RecBatch WHERE (BatchStatus = 'POSTEDON' AND StartDate <= ? AND BatchCategory = ? AND CompId IN (SELECT B.CompId FROM ACH_Company B ))";
          str5 = "DELETE FROM SCH_EventInfoLog WHERE InstructionType = 'RECACHBATCHTRN' AND (InstructionID IN (SELECT B.BatchId FROM ACH_Batch B WHERE B.BatchStatus = 'POSTEDON' AND B.DueDate <= ? AND B.BatchCategory = ? AND B.CompId IN (SELECT C.CompId FROM ACH_Company C )))";
        }
        else
        {
          arrayOfString = jdMethod_void(paramFFSConnectionHolder, String.valueOf(k), paramString1);
          for (m = 0; m < arrayOfString.length; m++) {
            RecSrvrTIDToSrvrTID.deleteByRecSrvrTID(paramFFSConnectionHolder, arrayOfString[m]);
          }
          str2 = "DELETE FROM ACH_RecAddenda WHERE RecordId IN (SELECT B.RecRecordId FROM ACH_RecRecord B WHERE (B.BatchId IN (SELECT C.RecBatchId FROM ACH_RecBatch C WHERE C.BatchStatus = 'POSTEDON' AND C.StartDate <= ? AND C.BatchCategory = ? AND C.CompId IN (SELECT D.CompId FROM ACH_Company D WHERE D.CustomerId = ?))))";
          str3 = "DELETE FROM ACH_RecRecord WHERE (BatchId IN (SELECT B.RecBatchId FROM ACH_RecBatch B WHERE B.BatchStatus = 'POSTEDON' AND B.StartDate <= ? AND B.BatchCategory = ? AND B.CompId IN (SELECT C.CompId FROM ACH_Company C WHERE C.CustomerId = ?)))";
          str4 = "DELETE FROM ACH_RecBatch WHERE (BatchStatus = 'POSTEDON' AND StartDate <= ? AND BatchCategory = ? AND CompId IN (SELECT B.CompId FROM ACH_Company B WHERE B.CustomerId = ?))";
          str5 = "DELETE FROM SCH_EventInfoLog WHERE InstructionType = 'RECACHBATCHTRN' AND (InstructionID IN (SELECT B.BatchId FROM ACH_Batch B WHERE B.BatchStatus = 'POSTEDON' AND B.DueDate <= ? AND B.BatchCategory = ? AND B.CompId IN (SELECT C.CompId FROM ACH_Company C WHERE C.CustomerId = ?)))";
        }
      }
      else if (i != 0)
      {
        str2 = "DELETE FROM ACH_Addenda WHERE RecordId IN (SELECT B.RecordId FROM ACH_Record B WHERE (B.BatchId IN (SELECT C.BatchId FROM ACH_Batch C WHERE C.BatchStatus = 'POSTEDON' AND C.DueDate <= ? AND C.BatchCategory = ? AND C.CompId IN (SELECT D.CompId FROM ACH_Company D ))))";
        str3 = "DELETE FROM ACH_Record WHERE (BatchId IN (SELECT B.BatchId FROM ACH_Batch B WHERE B.BatchStatus = 'POSTEDON' AND B.DueDate <= ? AND B.BatchCategory = ? AND B.CompId IN (SELECT C.CompId FROM ACH_Company C )))";
        str4 = "DELETE FROM ACH_Batch WHERE (BatchStatus = 'POSTEDON' AND DueDate <= ? AND BatchCategory = ? AND CompId IN (SELECT B.CompId FROM ACH_Company B ))";
        str5 = "DELETE FROM SCH_EventInfoLog WHERE InstructionType = 'ACHBATCHTRN' AND (InstructionID IN (SELECT B.BatchId FROM ACH_Batch B WHERE B.BatchStatus = 'POSTEDON' AND B.DueDate <= ? AND B.BatchCategory = ? AND B.CompId IN (SELECT C.CompId FROM ACH_Company C )))";
      }
      else
      {
        str2 = "DELETE FROM ACH_Addenda WHERE RecordId IN (SELECT B.RecordId FROM ACH_Record B WHERE (B.BatchId IN (SELECT C.BatchId FROM ACH_Batch C WHERE C.BatchStatus = 'POSTEDON' AND C.DueDate <= ? AND C.BatchCategory = ? AND C.CompId IN (SELECT D.CompId FROM ACH_Company D WHERE D.CustomerId = ?))))";
        str3 = "DELETE FROM ACH_Record WHERE (BatchId IN (SELECT B.BatchId FROM ACH_Batch B WHERE B.BatchStatus = 'POSTEDON' AND B.DueDate <= ? AND B.BatchCategory = ? AND B.CompId IN (SELECT C.CompId FROM ACH_Company C WHERE C.CustomerId = ?)))";
        str4 = "DELETE FROM ACH_Batch WHERE (BatchStatus = 'POSTEDON' AND DueDate <= ? AND BatchCategory = ? AND CompId IN (SELECT B.CompId FROM ACH_Company B WHERE B.CustomerId = ?))";
        str5 = "DELETE FROM SCH_EventInfoLog WHERE InstructionType = 'ACHBATCHTRN' AND (InstructionID IN (SELECT B.BatchId FROM ACH_Batch B WHERE B.BatchStatus = 'POSTEDON' AND B.DueDate <= ? AND B.BatchCategory = ? AND B.CompId IN (SELECT C.CompId FROM ACH_Company C WHERE C.CustomerId = ?)))";
      }
      j = DBUtil.executeStatement(paramFFSConnectionHolder, str5, arrayOfObject);
      j += DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      j += DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      j += DBUtil.executeStatement(paramFFSConnectionHolder, str4, arrayOfObject);
    }
    catch (FFSException localFFSException)
    {
      str7 = "*** " + str1 + " failed: ";
      str8 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str7, str8, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str7 = "***  " + str1 + ": failed:";
      String str8 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str8, 0);
      throw new FFSException(localException, str7);
    }
    FFSDebug.log(str1 + "end, No of rows deleted = " + j, 6);
    return j;
  }
  
  private static String[] jdMethod_void(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "ACHBatch.getRecBatchId ";
    FFSDebug.log(str1 + " start...", 6);
    String str2 = null;
    Object[] arrayOfObject = null;
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    if ((paramString2 != null) && (paramString2.trim().length() != 0))
    {
      str2 = "SELECT A.RecBatchId FROM ACH_RecBatch A WHERE (A.BatchStatus = 'POSTEDON' AND A.StartDate <= ? AND A.CompId IN (SELECT B.CompId FROM ACH_Company B WHERE B.CustomerId = ?))";
      arrayOfObject = new Object[] { new Integer(paramString1), paramString2 };
    }
    else
    {
      str2 = "SELECT A.RecBatchId FROM ACH_RecBatch A WHERE (A.BatchStatus = 'POSTEDON' AND A.StartDate <= ? AND A.CompId IN (SELECT B.CompId FROM ACH_Company B ))";
      arrayOfObject = new Object[] { new Integer(paramString1) };
    }
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        localArrayList.add(localFFSResultSet.getColumnString("RecBatchId"));
      }
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(str1 + ": failed:" + localFFSException.toString());
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      FFSDebug.log(str1 + ": failed:" + localException1.toString());
      throw new FFSException(localException1.toString());
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log(str1 + ": failed:" + localException2.toString());
      }
    }
    FFSDebug.log(str1 + ": end.", 6);
    return (String[])localArrayList.toArray(new String[localArrayList.size()]);
  }
  
  public static boolean isTransactionPendingForThisCompany(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHBatch.isTransactionPendingForThisCompany: ";
    FFSDebug.log(str1 + " start...", 6);
    String str2 = "SELECT BatchId from ACH_Batch  WHERE CompId = ? AND BatchStatus IN (?, ?) ";
    FFSResultSet localFFSResultSet = null;
    Object[] arrayOfObject = { paramString, "WILLPROCESSON", "APPROVAL_PENDING" };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      boolean bool;
      if (localFFSResultSet.getNextRow())
      {
        FFSDebug.log(str1 + ": end," + " isTransactionPendingForThisCompany = true", 6);
        bool = true;
        return bool;
      }
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      str2 = "SELECT RecBatchId from ACH_RecBatch  WHERE CompId = ? AND BatchStatus IN (?, ?) ";
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        FFSDebug.log(str1 + ": end," + " isTransactionPendingForThisCompany = true", 6);
        bool = true;
        return bool;
      }
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log(str1 + ": failed:" + localFFSException.toString());
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      String str3 = "*** " + str1 + ": failed:";
      String str4 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException1, str3);
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log(str1 + ": failed:" + localException2.toString());
      }
    }
    FFSDebug.log(str1 + ": end," + " isTransactionPendingForThisCompany = false", 6);
    return false;
  }
  
  public static HashMap getInfosForBatch(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    String str = "ACHBatchProcessor.getInfosForbatch: ";
    ACHCompanyInfo localACHCompanyInfo = ACHCompany.getCompanyInfo(paramACHBatchInfo.getCompId(), paramFFSConnectionHolder);
    if (localACHCompanyInfo.getStatusCode() != 0)
    {
      paramACHBatchInfo.setStatusCode(localACHCompanyInfo.getStatusCode());
      paramACHBatchInfo.setStatusMsg(localACHCompanyInfo.getStatusMsg());
      FFSDebug.log(str, " failed:", paramACHBatchInfo.getStatusMsg(), 0);
      return null;
    }
    paramACHBatchInfo.setCompAchId(localACHCompanyInfo.getCompACHId());
    paramACHBatchInfo.setOdfiId(localACHCompanyInfo.getODFIACHId());
    paramACHBatchInfo.setCompName(localACHCompanyInfo.getCompName());
    paramACHBatchInfo.setBatchHeaderFieldValueObject(5, localACHCompanyInfo.getCompACHId());
    CustomerInfo localCustomerInfo = null;
    try
    {
      localCustomerInfo = Customer.getCustomerInfo(localACHCompanyInfo.getCustomerId(), paramFFSConnectionHolder, paramACHBatchInfo);
    }
    catch (Exception localException)
    {
      FFSDebug.log(str + " failed tp get customer: " + localException.toString());
      throw new FFSException(localException, str + "failed, " + localException.getMessage());
    }
    if (localCustomerInfo == null)
    {
      paramACHBatchInfo.setStatusCode(19130);
      localObject = BPWLocaleUtil.getMessage(19130, new String[] { localACHCompanyInfo.getCustomerId() }, "TRANSFER_MESSAGE");
      paramACHBatchInfo.setStatusMsg((String)localObject);
      return null;
    }
    paramACHBatchInfo.setCustomerId(localACHCompanyInfo.getCustomerId());
    Object localObject = BPWFI.getBPWFIInfo(paramFFSConnectionHolder, localCustomerInfo.fIID);
    if (((BPWFIInfo)localObject).getStatusCode() != 0)
    {
      paramACHBatchInfo.setStatusCode(((BPWFIInfo)localObject).getStatusCode());
      paramACHBatchInfo.setStatusMsg(((BPWFIInfo)localObject).getStatusMsg());
      FFSDebug.log(str, " failed:", paramACHBatchInfo.getStatusMsg(), 0);
      return null;
    }
    paramACHBatchInfo.setOdfiRTN(((BPWFIInfo)localObject).getFIRTN());
    paramACHBatchInfo.setFiId(((BPWFIInfo)localObject).getFIId());
    paramACHBatchInfo.setBatchHeaderFieldValueObject(11, paramACHBatchInfo.getOdfiRTN());
    HashMap localHashMap = new HashMap();
    localHashMap.put("BPW_FIINFO", localObject);
    localHashMap.put("BPW_CUSTOMER", localCustomerInfo);
    localHashMap.put("ACH_COMPANY", localACHCompanyInfo);
    return localHashMap;
  }
  
  public static String getEffectiveDateFromBatch(ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    String str = (String)paramACHBatchInfo.getBatchHeaderFieldValueObject(9);
    if (str == null)
    {
      paramACHBatchInfo.setStatusCode(23610);
      paramACHBatchInfo.setStatusMsg("Invalid processing date");
      return null;
    }
    str = BPWUtil.getDateInNewFormat(str, "yyMMdd", "yyyyMMdd");
    return str;
  }
  
  public static boolean isSameDayEffDateAllowed(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo)
    throws FFSException
  {
    String str1 = "ACHBatch.isSameDayEffDateAllowed: ";
    FFSDebug.log(str1 + "start. SECCode=" + paramACHBatchInfo.getClassCode() + ", Category: " + paramACHBatchInfo.getBatchCategory(), 6);
    String str2 = paramACHBatchInfo.getClassCode();
    String str3 = paramACHBatchInfo.getBatchCategory();
    if (str3 != null) {
      if (str3.equals("ACH_BATCH_CHILD_SUPPORT")) {
        str2 = "CCD + DED";
      } else if (str3.equals("ACH_BATCH_TAX")) {
        str2 = "CCD + TXP";
      }
    }
    String[] arrayOfString = EntitlementsUtil.getACHSECEntitlementWithAddenda(str2);
    if (arrayOfString[0] == null)
    {
      arrayOfString = new String[2];
      arrayOfString[0] = str2;
      arrayOfString[1] = str2;
    }
    else if (arrayOfString[0] == null)
    {
      arrayOfString[0] = str2;
      arrayOfString[1] = str2;
    }
    ACHSameDayEffDateInfo localACHSameDayEffDateInfo = new ACHSameDayEffDateInfo();
    localACHSameDayEffDateInfo.setObjectId(paramACHBatchInfo.getCompId());
    localACHSameDayEffDateInfo.setObjectType(1);
    localACHSameDayEffDateInfo = ACHSameDayEffDate.getACHSameDayEffDateInfo(paramFFSConnectionHolder, localACHSameDayEffDateInfo);
    HashSet localHashSet = localACHSameDayEffDateInfo.getSECs();
    if (localACHSameDayEffDateInfo.getSECs() != null)
    {
      if ((!localHashSet.contains(arrayOfString[0])) && ((arrayOfString[0].equals(arrayOfString[1]) == true) || (!localHashSet.contains(arrayOfString[1]))))
      {
        paramACHBatchInfo.setStatusCode(27004);
        paramACHBatchInfo.setStatusMsg("ACHBatch's Effective Entry Date should not be business day of the same day");
        FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg(), 0);
        return false;
      }
    }
    else
    {
      paramACHBatchInfo.setStatusCode(27004);
      paramACHBatchInfo.setStatusMsg("ACHBatch's Effective Entry Date should not be business day of the same day");
      FFSDebug.log(str1 + paramACHBatchInfo.getStatusMsg(), 0);
      return false;
    }
    FFSDebug.log(str1 + " done.", 6);
    return true;
  }
  
  public static void createSessionData(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String[] arrayOfString = (String[])localHashMap2.get("TransType");
    String str1 = arrayOfString[0];
    boolean bool = false;
    if (str1.equalsIgnoreCase("RECACH")) {
      bool = true;
    }
    a(paramPagingInfo, localStringBuffer, localArrayList, bool);
    String str2 = localStringBuffer.toString();
    paramPagingInfo.setTotalTrans(0);
    if (str1.equalsIgnoreCase("ACH"))
    {
      a(paramFFSConnectionHolder, paramPagingInfo, str2, (ArrayList)localArrayList.clone(), false);
      if (paramPagingInfo.getStatusCode() != 0) {
        return;
      }
      a(paramFFSConnectionHolder, paramPagingInfo, null, paramPagingInfo.getTotalTrans());
      if (paramPagingInfo.getStatusCode() == 0) {}
    }
    else if (str1.equalsIgnoreCase("RECACH"))
    {
      a(paramFFSConnectionHolder, paramPagingInfo, str2, (ArrayList)localArrayList.clone(), true);
      if (paramPagingInfo.getStatusCode() != 0) {}
    }
  }
  
  private static String ak(String paramString)
  {
    if (paramString == null) {
      return "0";
    }
    if (paramString.length() == 10) {
      return paramString.substring(2, 8);
    }
    return paramString.substring(2);
  }
  
  private static void a(PagingInfo paramPagingInfo, StringBuffer paramStringBuffer, ArrayList paramArrayList, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatch.createPagingSearchCriteria: ";
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String str2 = (String)localHashMap2.get("StartEffectiveDate");
    if (str2 == null) {
      str2 = paramPagingInfo.getStartDate();
    }
    str2 = ak(str2);
    String str3 = (String)localHashMap2.get("EndEffectiveDate");
    if (str3 == null) {
      str3 = paramPagingInfo.getEndDate();
    }
    str3 = ak(str3);
    localHashMap2.put("StartEffectiveDate", str2);
    localHashMap2.put("EndEffectiveDate", str3);
    paramStringBuffer.append(a(localHashMap2, paramStringBuffer.toString(), paramArrayList));
    DBUtil.appendArrayToCondition(paramStringBuffer, (String[])localHashMap2.get("StatusList"), " AND BatchStatus IN (", paramArrayList);
    if (paramBoolean == true)
    {
      DBUtil.appendToCondition(paramStringBuffer, (String)localHashMap2.get("PayeeId"), " AND RecBatchId in ( SELECT BatchId FROM ACH_RecRecord WHERE PayeeId = ?  AND RecordStatus != 'CANCELEDON' )", paramArrayList);
      Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramPagingInfo.getStartDate()));
      Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramPagingInfo.getEndDate()));
      DBUtil.appendToCondition(paramStringBuffer, localInteger1, " AND StartDate >= ?", paramArrayList);
      DBUtil.appendToCondition(paramStringBuffer, localInteger2, " AND StartDate <= ?", paramArrayList);
    }
    else
    {
      DBUtil.appendToCondition(paramStringBuffer, (String)localHashMap2.get("PayeeId"), " AND BatchId in ( select BatchId from ACH_Record where PayeeId = ?  AND RecordStatus != 'CANCELEDON' )", paramArrayList);
      DBUtil.appendToCondition(paramStringBuffer, (String)localHashMap2.get("StartEffectiveDate"), " AND EffectiveDate >= ? ", paramArrayList);
      DBUtil.appendToCondition(paramStringBuffer, (String)localHashMap2.get("EndEffectiveDate"), " AND EffectiveDate <= ? ", paramArrayList);
    }
    FFSDebug.log(str1 + " : where clause as " + paramStringBuffer.toString(), 6);
    FFSDebug.log(str1 + " : end. ", 6);
  }
  
  private static String a(HashMap paramHashMap, String paramString, ArrayList paramArrayList)
  {
    StringBuffer localStringBuffer1 = new StringBuffer();
    if (paramString != null) {
      localStringBuffer1.append(paramString);
    }
    DBUtil.appendToCondition(localStringBuffer1, (String)paramHashMap.get("FIID"), " AND c.FIId=? AND c.FIStatus != 'CLOSED'", paramArrayList);
    DBUtil.appendToCondition(localStringBuffer1, (String)paramHashMap.get("ODFIACHId"), " AND c.ODFIACHId =? ", paramArrayList);
    DBUtil.appendToCondition(localStringBuffer1, (String)paramHashMap.get("CustomerId"), " AND b.CustomerId =? ", paramArrayList);
    DBUtil.appendToCondition(localStringBuffer1, (String)paramHashMap.get("CompId"), " AND a.CompId =?", paramArrayList);
    HashMap localHashMap = (HashMap)paramHashMap.get("CompanyIdSECs");
    if ((localHashMap != null) && (localHashMap.size() > 0))
    {
      StringBuffer localStringBuffer2 = new StringBuffer();
      Set localSet = localHashMap.keySet();
      Iterator localIterator = localSet.iterator();
      String str1 = null;
      String[] arrayOfString = null;
      int i = 1;
      while (localIterator.hasNext())
      {
        if (i == 1) {
          i = 0;
        } else {
          localStringBuffer2.append(" OR ");
        }
        str1 = (String)localIterator.next();
        arrayOfString = (String[])localHashMap.get(str1);
        localStringBuffer2.append("(");
        DBUtil.appendToCondition(localStringBuffer2, str1, " a.CompACHId = ?", paramArrayList);
        DBUtil.appendArrayToCondition(localStringBuffer2, arrayOfString, " AND StdClassCode IN (", paramArrayList);
        localStringBuffer2.append(")");
      }
      String str2 = localStringBuffer2.toString();
      if (str2.length() > 0)
      {
        str2 = " AND (" + str2 + " ) ";
        localStringBuffer1.append(str2);
      }
    }
    DBUtil.appendToCondition(localStringBuffer1, (String)paramHashMap.get("LogId"), " AND a.LogId =?", paramArrayList);
    DBUtil.appendArrayToCondition(localStringBuffer1, (String[])paramHashMap.get("SubmittedBys"), " AND a.SubmittedBy IN (", paramArrayList);
    DBUtil.appendArrayToCondition(localStringBuffer1, (String[])paramHashMap.get("CategoryList"), " AND BatchCategory IN (", paramArrayList);
    return localStringBuffer1.toString();
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHBatch.createSessionDataACH: ";
    FFSDebug.log(str1 + " start", 6);
    FFSResultSet localFFSResultSet = null;
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      String str3 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str3 == null) || (str3.trim().length() == 0))
      {
        str3 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str3);
      }
      StringBuffer localStringBuffer = new StringBuffer();
      if (paramBoolean == true) {
        localStringBuffer.append("SELECT a.RecBatchId, a.CompId, a.CompData, c.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.Frequency, a.StartDate, a.EndDate, a.PmtsCount, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier, a.NonOffTotalCredit, a.NonOffTotalDebit,  a.NonOffRecordCount  FROM ACH_RecBatch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d  WHERE BatchStatus != 'CANCELEDON' AND BatchStatus != 'ACHTEMPORARY' AND a.CompId = b.CompId AND b.ACHFIId = c.ACHFIId  AND c.FIId = d.FIId ");
      } else {
        localStringBuffer.append("SELECT a.BatchId, a.CompId, a.CompData, c.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate,a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode,a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash,a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName,d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef,a.OrigStatusCode,  a.SrvClassCode, a.ExportFileName,  a.DueDate, a.DtProcessed, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier, a.NonOffTotalCredit, a.NonOffTotalDebit,  a.NonOffRecordCount  FROM ACH_Batch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d  WHERE a.BatchStatus != 'CANCELEDON' AND a.BatchStatus != 'ACHTEMPORARY' AND a.CompId = b.CompId AND b.ACHFIId = c.ACHFIId  AND c.FIId = d.FIId ");
      }
      if (paramString != null) {
        localStringBuffer.append(paramString);
      }
      if (paramBoolean == true) {
        localStringBuffer.append(" ORDER BY RecBatchId");
      } else {
        localStringBuffer.append(" ORDER BY BatchId");
      }
      FFSDebug.log(str1 + "Sql Statement: " + localStringBuffer.toString(), 6);
      for (int k = 0; k < paramArrayList.size(); k++) {
        FFSDebug.log(str1 + " Sql Param:" + k + " :" + String.valueOf(paramArrayList.get(k)), 6);
      }
      Object[] arrayOfObject = paramArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      Object localObject1 = null;
      ACHHistInfo localACHHistInfo = null;
      int m = BPWUtil.getSessionSize();
      while (localFFSResultSet.getNextRow())
      {
        j++;
        if (j > m)
        {
          paramPagingInfo.setStatusCode(28020);
          paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
          break;
        }
        String str4 = null;
        if (paramBoolean == true)
        {
          localObject1 = new RecACHBatchInfo();
          str4 = "RECACH";
        }
        else
        {
          localObject1 = new ACHBatchInfo();
          str4 = "ACH";
        }
        a(paramFFSConnectionHolder, (ACHBatchInfo)localObject1, localFFSResultSet, paramBoolean);
        localACHHistInfo = new ACHHistInfo();
        localACHHistInfo.setObjInfo(localObject1);
        localACHHistInfo.setCursorId(String.valueOf(j));
        ACHTempHist.createTempHist(paramFFSConnectionHolder, str3, j, str4, localACHHistInfo);
        if (j % i == 0) {
          paramFFSConnectionHolder.conn.commit();
        }
      }
      paramPagingInfo.setTotalTrans(j);
      paramFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1 + " : end", 6);
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + " failed : ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException)
        {
          FFSDebug.log(str1 + " failed to close ResultSet " + FFSDebug.stackTrace(localException), 0);
        }
      }
    }
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, ACHBatchInfo paramACHBatchInfo, FFSResultSet paramFFSResultSet, boolean paramBoolean)
    throws Exception
  {
    if (!paramBoolean) {
      paramACHBatchInfo.setBatchId(paramFFSResultSet.getColumnString("BatchId"));
    } else {
      paramACHBatchInfo.setBatchId(paramFFSResultSet.getColumnString("RecBatchId"));
    }
    paramACHBatchInfo = getACHBatch(paramACHBatchInfo, paramFFSConnectionHolder, paramBoolean, false, false);
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "ACHBatch.makeSessionDateForFutureHistory: ";
    FFSDebug.log(str1 + "start ", 6);
    FFSResultSet localFFSResultSet = null;
    String str2 = paramPagingInfo.getPagingContext().getSessionId();
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      str2 = DBUtil.getNextIndexString("SessionID");
      paramPagingInfo.getPagingContext().setSessionId(str2);
    }
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    try
    {
      int i = 0;
      localObject1 = (String[])localHashMap2.get("StatusList");
      if (localObject1 != null)
      {
        int j = localObject1.length;
        for (int k = 0; k < j; k++) {
          if ("WILLPROCESSON".equals(localObject1[k]))
          {
            i = 1;
            break;
          }
        }
      }
      i = 1;
      if (i != 0)
      {
        Integer localInteger1 = new Integer(BPWUtil.getDateDBFormat(paramPagingInfo.getStartDate()));
        Integer localInteger2 = new Integer(BPWUtil.getDateDBFormat(paramPagingInfo.getEndDate()));
        PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        int m = localPropertyConfig.getBatchSize();
        int n = DBUtil.getCurrentStartDate();
        int i1 = BPWUtil.getDateDBFormat(paramPagingInfo.getEndDate());
        int i2 = (i1 - n) / 10000;
        if (i2 >= 0)
        {
          StringBuffer localStringBuffer = new StringBuffer("SELECT a.RecBatchId, a.CompId, a.CompData, c.ODFIACHId, a.BatchType, a.BatchCategory, a.BatchBalanceType, a.EffectiveDate, a.SettlementDate, a.CompEntryDesc, a.DescpDate, a.BatchNum, a.StdClassCode, a.CountryCode, a.OriginCurrCode, a.DestCurrCode, a.TotalDebit, a.TotalCredit, a.MsgAuthCode, a.RecordCount, a.EntryHash, a.SubmitDate, a.SubmittedBy, a.BatchStatus, a.LogId, a.CompACHId, b.CompName, d.FIRTN,  c.ODFIName, a.FrgnExInd, a.FrgnExRefInd, a.FrgnExRef, a.OrigStatusCode,  a.SrvClassCode, a.Frequency, a.StartDate, a.EndDate, a.PmtsCount, a.Memo, c.FIId, b.CustomerId,  a.OffsetAccountID, a.HeaderCompName, a.LastModifier, a.NonOffTotalCredit, a.NonOffTotalDebit,  a.NonOffRecordCount  FROM ACH_RecBatch a, ACH_Company b, ACH_FIInfo c, BPW_FIInfo d  WHERE BatchStatus != 'CANCELEDON' AND BatchStatus != 'ACHTEMPORARY' AND a.CompId = b.CompId AND b.ACHFIId = c.ACHFIId  AND c.FIId = d.FIId ");
          ArrayList localArrayList = new ArrayList();
          localStringBuffer.append(" AND a.BatchStatus ='WILLPROCESSON' ");
          localStringBuffer.append(a(localHashMap2, paramString, localArrayList));
          DBUtil.appendToCondition(localStringBuffer, (String)localHashMap2.get("PayeeId"), " AND RecBatchId in ( SELECT BatchId FROM ACH_RecRecord WHERE PayeeId = ?  AND RecordStatus != 'CANCELEDON' )", localArrayList);
          localArrayList.add(localInteger2);
          localStringBuffer.append(" AND StartDate <= ?");
          localArrayList.add(localInteger1);
          localStringBuffer.append(" AND EndDate >= ?");
          localStringBuffer.append(" ORDER BY RecBatchId");
          Object[] arrayOfObject = (Object[])localArrayList.toArray(new Object[0]);
          String str3 = localStringBuffer.toString();
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str3, arrayOfObject);
          String str4 = null;
          String str5 = null;
          ScheduleInfo localScheduleInfo = null;
          RecACHBatchInfo localRecACHBatchInfo = null;
          ACHHistInfo localACHHistInfo = null;
          int i3 = BPWUtil.getSessionSize();
          label711:
          while (localFFSResultSet.getNextRow())
          {
            localRecACHBatchInfo = new RecACHBatchInfo();
            a(paramFFSConnectionHolder, localRecACHBatchInfo, localFFSResultSet, true);
            str4 = localRecACHBatchInfo.getBatchId();
            str5 = localRecACHBatchInfo.getFiId();
            localScheduleInfo = ScheduleInfo.getScheduleInfo(str5, "RECACHBATCHTRN", str4, paramFFSConnectionHolder);
            if ((localScheduleInfo != null) && (localScheduleInfo.StatusOption != 1))
            {
              String str6 = localRecACHBatchInfo.getStartDate();
              int i4 = Integer.parseInt(str6) * 100;
              localScheduleInfo.NextInstanceDate = ScheduleInfo.computeFutureDate(i4, localScheduleInfo.Frequency, localScheduleInfo.CurInstanceNum - 1);
              localScheduleInfo.InstanceCount -= localScheduleInfo.CurInstanceNum - 1;
              localScheduleInfo.CurInstanceNum = 1;
              String[] arrayOfString = ScheduleInfo.getPendingDatesByStartAndEndDate(localScheduleInfo, localInteger1.intValue(), localInteger2.intValue());
              for (int i5 = 0;; i5++)
              {
                if (i5 >= arrayOfString.length) {
                  break label711;
                }
                int i6 = Integer.parseInt(arrayOfString[i5].substring(0, 10));
                i6 = SmartCalendar.getACHPayday(localRecACHBatchInfo.getFiId(), i6 / 100) * 100;
                paramInt++;
                if (paramInt > i3)
                {
                  paramPagingInfo.setStatusCode(28020);
                  paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
                  break;
                }
                localRecACHBatchInfo = new RecACHBatchInfo();
                a(paramFFSConnectionHolder, localRecACHBatchInfo, localFFSResultSet, true);
                localRecACHBatchInfo.setDueDate(String.valueOf(i6));
                localACHHistInfo = new ACHHistInfo();
                localACHHistInfo.setObjInfo(localRecACHBatchInfo);
                localACHHistInfo.setCursorId(String.valueOf(paramInt));
                ACHTempHist.createTempHist(paramFFSConnectionHolder, str2, paramInt, "RECACH", localACHHistInfo);
                if (paramInt % m == 0) {
                  paramFFSConnectionHolder.conn.commit();
                }
              }
            }
          }
        }
      }
      paramPagingInfo.setTotalTrans(paramInt);
      paramFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException1)
    {
      Object localObject1 = "***" + str1 + "failed : ";
      FFSDebug.log((String)localObject1 + FFSDebug.stackTrace(localException1), 0);
      throw new FFSException(localException1, (String)localObject1);
    }
    finally
    {
      if (localFFSResultSet != null) {
        try
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        catch (Exception localException2)
        {
          FFSDebug.log("***" + str1 + "failed to close ResultSet " + FFSDebug.stackTrace(localException2), 0);
        }
      }
      FFSDebug.log(str1 + "end", 6);
    }
  }
  
  public static PagingInfo getSessionPage(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "ACHBatch.getSessionPage";
    FFSDebug.log(str1 + " : start", 6);
    String str2 = paramPagingInfo.getPagingContext().getSessionId();
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      paramPagingInfo.setStatusCode(16000);
      paramPagingInfo.setStatusMsg("sessionId is null");
      FFSDebug.log(str1 + " : " + "Null sessionId passed", 0);
      return paramPagingInfo;
    }
    try
    {
      ArrayList localArrayList = ACHTempHist.getSessionPage(paramFFSConnectionHolder, paramPagingInfo);
      paramPagingInfo.setPagingResult(localArrayList);
      ACHTempHist.getBounds(paramFFSConnectionHolder, paramPagingInfo);
      paramPagingInfo.setStatusCode(0);
      return paramPagingInfo;
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + " failed : ";
      FFSDebug.log(str3 + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str3);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ACHBatch
 * JD-Core Version:    0.7.0.1
 */