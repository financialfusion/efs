package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.achagent.ACHAgent;
import com.ffusion.ffs.bpw.interfaces.ACHAddendaInfo;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.ACHBatchCache;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Hashtable;

public class ACHRecord
  implements FFSConst, DBConsts, SQLConsts, ACHConsts, BPWResource
{
  public static int addACHRecord(FFSConnectionHolder paramFFSConnectionHolder, ACHRecordInfo paramACHRecordInfo, ACHBatchCache paramACHBatchCache, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHRecord.addACHRecord";
    int i = 0;
    FFSDebug.log(str1 + "::" + "Going to check Addenda Indicator value", 6);
    if (!paramACHRecordInfo.isValidAddendaIndicator())
    {
      paramACHRecordInfo.setStatusCode(22560);
      paramACHRecordInfo.setStatusMsg("Invalid Addenda Indicator value, not 0 or 1");
      return i;
    }
    if ((paramACHRecordInfo.hasAddenda() == true) && ((paramACHRecordInfo.getAddenda() == null) || (paramACHRecordInfo.getAddenda().length == 0)))
    {
      paramACHRecordInfo.setStatusCode(22500);
      paramACHRecordInfo.setStatusMsg("Addenda record indicator value is 1 but no Addenda record follows:");
      FFSDebug.log(str1 + " " + "Addenda record indicator value is 1 but no Addenda record follows:", 0);
      return i;
    }
    if ((!paramACHRecordInfo.hasAddenda()) && (paramACHRecordInfo.getAddenda() != null) && (paramACHRecordInfo.getAddenda().length != 0))
    {
      paramACHRecordInfo.setStatusCode(22510);
      paramACHRecordInfo.setStatusMsg("Addenda record indicator value is 0 but Addenda record follows:");
      FFSDebug.log(str1 + "ACHBatchProcessor.addACHBatch, " + "Addenda record indicator value is 0 but Addenda record follows:", 0);
      return i;
    }
    if (!a(paramACHRecordInfo))
    {
      FFSDebug.log(str1 + " failed to validate ACHRecordInfo", 0);
      return 0;
    }
    FFSDebug.log(str1 + "::" + "Going to add ACH Record", 6);
    a(paramFFSConnectionHolder, paramACHRecordInfo, paramACHBatchCache.orgDfiRTN, paramBoolean);
    if (paramACHRecordInfo.getStatusCode() != 0) {
      return i;
    }
    i++;
    FFSDebug.log(str1 + ":: ACH Record added", 6);
    String str2 = paramACHRecordInfo.getRecordId();
    String str3 = paramACHRecordInfo.getClassCode();
    String str4 = paramACHRecordInfo.getLogId();
    if (paramACHRecordInfo.getAddenda() != null)
    {
      Long localLong = BPWUtil.composeTraceNum(paramACHBatchCache.orgDfiRTN, str2);
      Integer localInteger = BPWUtil.composeAddendaEntryDetailSeqNum(str2);
      ACHAddendaInfo[] arrayOfACHAddendaInfo = paramACHRecordInfo.getAddenda();
      for (int j = 0; j < arrayOfACHAddendaInfo.length; j = (short)(j + 1))
      {
        ACHAddendaInfo localACHAddendaInfo = arrayOfACHAddendaInfo[j];
        localACHAddendaInfo.setRecordId(str2);
        localACHAddendaInfo.setFieldValueObject("Trace_Number", localLong);
        localACHAddendaInfo.setFieldValueObject("Addenda_Sequence_Number", new Short((short)(j + 1)));
        localACHAddendaInfo.setFieldValueObject("Entry_Detail_Sequence_Number", localInteger);
        if (localACHAddendaInfo.getClassCode() == null) {
          localACHAddendaInfo.setClassCode(str3);
        }
        localACHAddendaInfo.setLogId(str4);
        ACHAddenda.addAddenda(localACHAddendaInfo, paramFFSConnectionHolder, paramBoolean);
        if (localACHAddendaInfo.getStatusCode() != 0)
        {
          paramACHRecordInfo.setStatusCode(localACHAddendaInfo.getStatusCode());
          paramACHRecordInfo.setStatusMsg(localACHAddendaInfo.getStatusMsg());
          return i;
        }
        i++;
      }
    }
    FFSDebug.log(str1 + ":: updating cache", "with validation information", 6);
    updateBatchCache(paramACHBatchCache, paramACHRecordInfo, i, 1);
    return i;
  }
  
  private static ACHRecordInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHRecordInfo paramACHRecordInfo, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHRecord.addRecordOnly ";
    String str2 = null;
    String str3 = null;
    Object[] arrayOfObject = null;
    FFSDebug.log(str1, ": start.", 6);
    try
    {
      if (paramACHRecordInfo.getStatusCode() != 0) {
        return paramACHRecordInfo;
      }
      if (paramBoolean)
      {
        str2 = "INSERT INTO ACH_RecRecord (RecRecordId, BatchId, RDFIACHId, PayeeId,AddendaCount, Amount, CheckDigit, CheckSerialNum, RecordDiscData, DocRefNum, ItemResearchNum, PmtType, RecordType, TransCode, RecordSeqNum, TerminalCity, TerminalState, TraceNum, OrigTraceNum, SettlementDate, SubmittedBy, SubmitDate, RecordStatus, SrvClassCode, RecvCompId, BankAcctId, CardNum, CardExpireDate, CardTransCode, ItemTypeInd, ProcessControl, RecordContent, PayAcct, PayeeName, BankAcctType, Frequency, StartDate, EndDate, PmtsCount, LogId, RecordCategory, PairedId, DirtyFlag,OffsetAccountID,TaxFormId,Memo)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        str3 = DBUtil.getNextIndexStringWithPadding("RecRecordID", 32, '0');
      }
      else
      {
        str2 = "INSERT INTO ACH_Record (RecordId, BatchId, RDFIACHId, PayeeId, AddendaCount, Amount, CheckDigit, CheckSerialNum, RecordDiscData, DocRefNum, ItemResearchNum, PmtType, RecordType, TransCode, RecordSeqNum, TerminalCity, TerminalState, TraceNum, OrigTraceNum, EffectiveDate, SettlementDate, SubmittedBy, SubmitDate, RecordStatus, SrvClassCode, RecvCompId, BankAcctId, CardNum, CardExpireDate, CardTransCode, ItemTypeInd, ProcessControl, RecordContent, PayAcct, PayeeName, BankAcctType, LogId, RecordCategory, PairedId, DirtyFlag, OffsetAccountID,TaxFormId,Memo)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        str3 = DBUtil.getNextIndexStringWithPadding("RecordID", 32, '0');
      }
      String str4 = BPWUtil.composeTraceNumStr(paramString, str3);
      long l = 0L;
      try
      {
        l = Long.parseLong(str4);
      }
      catch (Exception localException2) {}
      Long localLong = new Long(l);
      paramACHRecordInfo.setRecordId(str3);
      paramACHRecordInfo.setFieldValueObject("Trace_Number", localLong);
      arrayOfObject = a(paramACHRecordInfo, str4, paramBoolean);
      FFSDebug.log("ACHRecord.addRecordInfo:: Num of arg=" + arrayOfObject.length, 6);
      FFSDebug.log(str1 + ":: recordId=", paramACHRecordInfo.getRecordId(), 6);
      FFSDebug.log(str1 + ":: batchId=", paramACHRecordInfo.getBatchId(), 6);
      FFSDebug.log(str1 + ":: RDFIID=", paramACHRecordInfo.getFieldValueString("Receiving_DFI_Identification"), 6);
      FFSDebug.log(str1 + ":recordContent=" + paramACHRecordInfo.getRecordContent(), 6);
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException1)
    {
      String str5 = "*** " + str1 + ": failed:";
      String str6 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str6, 0);
      throw new FFSException(localException1, str5);
    }
    paramACHRecordInfo.setStatusCode(0);
    paramACHRecordInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + ": done", 6);
    return paramACHRecordInfo;
  }
  
  public static ACHRecordInfo updateACHRecord(FFSConnectionHolder paramFFSConnectionHolder, ACHRecordInfo paramACHRecordInfo, ACHBatchCache paramACHBatchCache, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHRecord.updateACHRecord: ";
    FFSDebug.log(str1 + ": start, batchId=", paramACHRecordInfo.getBatchId(), "  recordId=", paramACHRecordInfo.getRecordId(), 6);
    if (!a(paramACHRecordInfo))
    {
      FFSDebug.log(str1 + "failed to validate ACHRecordInfo", 0);
      return paramACHRecordInfo;
    }
    try
    {
      String str2 = paramACHRecordInfo.getRecordId();
      localObject = jdMethod_do(paramFFSConnectionHolder, paramACHRecordInfo.getBatchId(), str2, paramBoolean);
      if (localObject == null)
      {
        paramACHRecordInfo.setStatusCode(16020);
        String str3 = "ACHRecord  record not found";
        paramACHRecordInfo.setStatusMsg(str3);
        FFSDebug.log(str1 + " failed, ", str3, 0);
        return paramACHRecordInfo;
      }
      int i = 0;
      if (paramACHRecordInfo.getAddenda() != null) {
        i = ((ACHRecordInfo)localObject).getAddenda().length;
      }
      updateBatchCache(paramACHBatchCache, (ACHRecordInfo)localObject, 1, -1);
      int j = 0;
      ACHAddendaInfo[] arrayOfACHAddendaInfo = paramACHRecordInfo.getAddenda();
      int k = 0;
      if (arrayOfACHAddendaInfo != null) {
        k = arrayOfACHAddendaInfo.length;
      }
      Long localLong = BPWUtil.composeTraceNum(paramACHBatchCache.orgDfiRTN, str2);
      Integer localInteger = BPWUtil.composeAddendaEntryDetailSeqNum(str2);
      int m = 0;
      for (int n = 0; n < k; n++)
      {
        FFSDebug.log(str1 + "::" + "Processing addenda:" + (n + 1), 6);
        ACHAddendaInfo localACHAddendaInfo = arrayOfACHAddendaInfo[n];
        if ((localACHAddendaInfo == null) || (localACHAddendaInfo.getAction() == null))
        {
          FFSDebug.log(str1 + "::" + "No value or action found. Skipping detail addenda:" + (n + 1), 6);
        }
        else
        {
          String str5 = localACHAddendaInfo.getAction();
          if (str5.compareTo("add") == 0)
          {
            localACHAddendaInfo.setFieldValueObject("Trace_Number", localLong);
            localACHAddendaInfo.setFieldValueObject("Entry_Detail_Sequence_Number", localInteger);
            localACHAddendaInfo.setRecordId(paramACHRecordInfo.getRecordId());
            if (localACHAddendaInfo.getClassCode() == null) {
              localACHAddendaInfo.setClassCode(paramACHRecordInfo.getClassCode());
            }
            ACHAddenda.addAddenda(localACHAddendaInfo, paramFFSConnectionHolder, paramBoolean);
            j++;
            m = 1;
            paramACHRecordInfo.updateAddendaInArray(localACHAddendaInfo);
          }
          else if (str5.compareTo("mod") == 0)
          {
            localACHAddendaInfo.setFieldValueObject("Trace_Number", localLong);
            localACHAddendaInfo.setFieldValueObject("Entry_Detail_Sequence_Number", localInteger);
            ACHAddenda.updateACHAddenda(paramFFSConnectionHolder, localACHAddendaInfo, paramBoolean);
            m = 1;
          }
          else if (str5.compareTo("del") == 0)
          {
            ACHAddenda.canACHAddenda(paramFFSConnectionHolder, localACHAddendaInfo, paramBoolean);
            j--;
            m = 1;
            paramACHRecordInfo.updateAddendaInArray(localACHAddendaInfo);
          }
          if (localACHAddendaInfo.getStatusCode() != 0)
          {
            paramACHRecordInfo.setStatusCode(localACHAddendaInfo.getStatusCode());
            paramACHRecordInfo.setStatusMsg(localACHAddendaInfo.getStatusMsg());
            return paramACHRecordInfo;
          }
        }
      }
      if (m == 1) {
        a(paramFFSConnectionHolder, paramACHRecordInfo, paramBoolean);
      }
      a(paramACHRecordInfo, (ACHRecordInfo)localObject);
      paramACHRecordInfo.setFieldValueObject("Number_Of_Addenda_Records", new Integer(i + j));
      a(paramFFSConnectionHolder, paramACHRecordInfo, paramACHBatchCache, paramBoolean);
      if (paramACHRecordInfo.getStatusCode() != 0) {
        return paramACHRecordInfo;
      }
      updateBatchCache(paramACHBatchCache, paramACHRecordInfo, j + 1, 1);
    }
    catch (Exception localException)
    {
      Object localObject = "***  " + str1 + " : failed:";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, (String)localObject);
    }
    paramACHRecordInfo.setStatusCode(0);
    paramACHRecordInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + ": done", 6);
    return paramACHRecordInfo;
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, ACHRecordInfo paramACHRecordInfo, boolean paramBoolean)
    throws Exception
  {
    String str = "ACHRecord.recalcuateAddendaSeqNum: ";
    FFSDebug.log(str + ": start, batchId=", paramACHRecordInfo.getBatchId(), "  recordId=", paramACHRecordInfo.getRecordId(), 6);
    ACHAddendaInfo[] arrayOfACHAddendaInfo = ACHAddenda.getACHAddendas(paramACHRecordInfo.getRecordId(), paramACHRecordInfo.getClassCode(), paramFFSConnectionHolder, true, paramBoolean);
    for (int i = 0; i < arrayOfACHAddendaInfo.length; i = (short)(i + 1))
    {
      ACHAddendaInfo localACHAddendaInfo = arrayOfACHAddendaInfo[i];
      localACHAddendaInfo.setFieldValueObject("Addenda_Sequence_Number", new Short((short)(i + 1)));
      ACHAddenda.updateACHAddenda(paramFFSConnectionHolder, localACHAddendaInfo, paramBoolean);
    }
    FFSDebug.log(str + ": done", 6);
  }
  
  private static ACHRecordInfo a(FFSConnectionHolder paramFFSConnectionHolder, ACHRecordInfo paramACHRecordInfo, ACHBatchCache paramACHBatchCache, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHRecord.updateRecordOnly";
    String str2 = "UPDATE ACH_Record set PayeeId =?, RDFIACHId =?, AddendaCount =?, Amount =?, CheckDigit =?, CheckSerialNum =?, RecordDiscData =?, DocRefNum =?, ItemResearchNum =?, PmtType =?, RecordType =?, TransCode =?, RecordSeqNum =?, TerminalCity =?, TerminalState =?, TraceNum =?, OrigTraceNum =?, EffectiveDate =?, SettlementDate =?, SubmittedBy =?, SubmitDate =?, SrvClassCode =?, RecvCompId =?, BankAcctId =?, CardNum =?, CardExpireDate=?, CardTransCode=?, ItemTypeInd=?, ProcessControl=?, RecordContent=?, PayAcct=?, PayeeName=?, DirtyFlag=?, OffsetAccountID=?, BankAcctType = ? WHERE RecordId = ?";
    String str3 = paramACHRecordInfo.getRecordId();
    FFSDebug.log(str1 + ": start, batchId=", paramACHRecordInfo.getBatchId(), "  recordId=", paramACHRecordInfo.getRecordId(), 6);
    if (paramBoolean) {
      str2 = "UPDATE ACH_RecRecord set PayeeId =?, RDFIACHId =?, AddendaCount =?, Amount =?, CheckDigit =?, CheckSerialNum =?, RecordDiscData =?, DocRefNum =?, ItemResearchNum =?, PmtType =?, RecordType =?, TransCode =?, RecordSeqNum =?, TerminalCity =?, TerminalState =?, TraceNum =?, OrigTraceNum =?, EffectiveDate =?, SettlementDate =?, SubmittedBy =?, SubmitDate =?, SrvClassCode =?, RecvCompId =?, BankAcctId =?, CardNum =?, CardExpireDate=?, CardTransCode=?, ItemTypeInd=?, ProcessControl=?, RecordContent=?, PayAcct=?, PayeeName=?, Frequency=?, StartDate=?, EndDate=?, PmtsCount=?, DirtyFlag=?, OffsetAccountID=?, BankAcctType = ? WHERE RecRecordId = ? ";
    } else {
      str2 = "UPDATE ACH_Record set PayeeId =?, RDFIACHId =?, AddendaCount =?, Amount =?, CheckDigit =?, CheckSerialNum =?, RecordDiscData =?, DocRefNum =?, ItemResearchNum =?, PmtType =?, RecordType =?, TransCode =?, RecordSeqNum =?, TerminalCity =?, TerminalState =?, TraceNum =?, OrigTraceNum =?, EffectiveDate =?, SettlementDate =?, SubmittedBy =?, SubmitDate =?, SrvClassCode =?, RecvCompId =?, BankAcctId =?, CardNum =?, CardExpireDate=?, CardTransCode=?, ItemTypeInd=?, ProcessControl=?, RecordContent=?, PayAcct=?, PayeeName=?, DirtyFlag=?, OffsetAccountID=?, BankAcctType = ? WHERE RecordId = ?";
    }
    try
    {
      FFSDebug.log(str1 + ":: recordId=", str3, 6);
      FFSDebug.log(str1 + ":: batchId=", paramACHRecordInfo.getBatchId(), 6);
      String str4 = BPWUtil.composeTraceNumStr(paramACHBatchCache.orgDfiRTN, str3);
      long l = 0L;
      try
      {
        l = Long.parseLong(str4);
      }
      catch (Exception localException2) {}
      Long localLong = new Long(l);
      paramACHRecordInfo.setRecordId(str3);
      paramACHRecordInfo.setFieldValueObject("Trace_Number", localLong);
      Object[] arrayOfObject = a(paramACHRecordInfo, paramBoolean);
      FFSDebug.log("*** " + str1 + " :recordContent=" + paramACHRecordInfo.getRecordContent(), 6);
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException1)
    {
      String str5 = "***  " + str1 + " : failed:";
      String str6 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str6, 0);
      throw new FFSException(localException1, str5);
    }
    paramACHRecordInfo.setStatusCode(0);
    paramACHRecordInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + ": done", 6);
    return paramACHRecordInfo;
  }
  
  private static ACHRecordInfo jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHRecord.getRecordById";
    ACHRecordInfo localACHRecordInfo = null;
    long l = Long.parseLong(paramString2) - 1L;
    String str2 = new Long(l).toString();
    ACHRecordInfo[] arrayOfACHRecordInfo = getACHRecordsByChunk(paramString1, str2, 1L, paramFFSConnectionHolder, true, paramBoolean);
    if ((arrayOfACHRecordInfo == null) || (arrayOfACHRecordInfo.length == 0) || (arrayOfACHRecordInfo[0] == null))
    {
      String str3 = "ACHRecord  record not found. batchId: " + paramString1 + ", recordId: " + paramString2;
      FFSDebug.log(str1 + " failed, ", str3, 0);
      return null;
    }
    localACHRecordInfo = arrayOfACHRecordInfo[0];
    return localACHRecordInfo;
  }
  
  public static ACHRecordInfo canACHRecord(FFSConnectionHolder paramFFSConnectionHolder, ACHRecordInfo paramACHRecordInfo, ACHBatchCache paramACHBatchCache, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHRecord.canACHRecord ";
    ACHRecordInfo localACHRecordInfo = jdMethod_do(paramFFSConnectionHolder, paramACHRecordInfo.getBatchId(), paramACHRecordInfo.getRecordId(), paramBoolean);
    if (localACHRecordInfo == null)
    {
      paramACHRecordInfo.setStatusCode(16020);
      String str2 = "ACHRecord  record not found";
      paramACHRecordInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + " failed, ", str2, 0);
      return paramACHRecordInfo;
    }
    int i = 1;
    ACHAddendaInfo[] arrayOfACHAddendaInfo = localACHRecordInfo.getAddenda();
    if (arrayOfACHAddendaInfo != null) {
      i += arrayOfACHAddendaInfo.length;
    }
    updateBatchCache(paramACHBatchCache, localACHRecordInfo, i, -1);
    updateACHRecordStatus(paramACHRecordInfo, "CANCELEDON", paramFFSConnectionHolder, paramBoolean);
    if (paramACHRecordInfo.getStatusCode() != 0) {
      return paramACHRecordInfo;
    }
    return paramACHRecordInfo;
  }
  
  public static ACHRecordInfo updateACHRecordStatus(ACHRecordInfo paramACHRecordInfo, String paramString, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHRecord.updateACHRecordStatus";
    String str2 = "UPDATE ACH_Record set RecordStatus = ?  WHERE RecordId = ?";
    String str3 = null;
    FFSDebug.log(str1 + ": start, " + " new status: " + paramString + " isRecurring: " + paramBoolean, 6);
    if (paramBoolean) {
      str2 = "UPDATE ACH_RecRecord set RecordStatus = ?  WHERE RecRecordId = ?";
    } else {
      str2 = "UPDATE ACH_Record set RecordStatus = ?  WHERE RecordId = ?";
    }
    Object localObject1;
    if (paramFFSConnectionHolder == null)
    {
      paramACHRecordInfo.setStatusCode(16000);
      localObject1 = "FFSConnectionHolder object  is null";
      paramACHRecordInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + " failed, " + (String)localObject1, 0);
      return paramACHRecordInfo;
    }
    try
    {
      str3 = paramACHRecordInfo.getRecordId();
      FFSDebug.log(str1 + ":: recordId=", str3, 6);
      localObject1 = new ACHAddendaInfo();
      ((ACHAddendaInfo)localObject1).setRecordId(str3);
      localObject2 = new Object[] { paramString, paramACHRecordInfo.getRecordId() };
      DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject2);
    }
    catch (Exception localException)
    {
      Object localObject2 = "***  " + str1 + ": failed:";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, (String)localObject2);
    }
    paramACHRecordInfo.setStatusCode(0);
    paramACHRecordInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + ": done", 6);
    return paramACHRecordInfo;
  }
  
  public static void delACHRecord(FFSConnectionHolder paramFFSConnectionHolder, ACHRecordInfo paramACHRecordInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHRecord.delACHRecord ";
    String str2 = null;
    try
    {
      if (paramBoolean) {
        str2 = "DELETE FROM ACH_RecRecord WHERE RecRecordId = ?";
      } else {
        str2 = "DELETE FROM ACH_Record WHERE RecordId = ?";
      }
      Object[] arrayOfObject = { paramACHRecordInfo.getRecordId() };
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
  
  public static int delACHRecord(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHRecord.delACHRecord ";
    String str2 = null;
    try
    {
      if (paramBoolean) {
        str2 = "DELETE FROM ACH_RecRecord WHERE BatchId = ?";
      } else {
        str2 = "DELETE FROM ACH_Record WHERE BatchId = ?";
      }
      Object[] arrayOfObject = { paramString };
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      return i;
    }
    catch (Exception localException)
    {
      String str3 = "***  " + str1 + ": failed:";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str3);
    }
  }
  
  public static ACHRecordInfo[] getACHRecordsByChunk(String paramString1, String paramString2, long paramLong, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    return getACHRecordsByChunk(paramString1, paramString2, paramLong, paramFFSConnectionHolder, paramBoolean1, paramBoolean2, false);
  }
  
  public static ACHRecordInfo[] getACHRecordsByChunk(String paramString1, String paramString2, long paramLong, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws FFSException
  {
    String str1 = "ACHRecord.getACHRecordByChunk ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    ACHRecordInfo[] arrayOfACHRecordInfo = null;
    ACHRecordInfo localACHRecordInfo = null;
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    int j = 0;
    int k = 0;
    FFSDebug.log(str1 + "ACHRecord.getACHRecordByChunk: start, " + "Batch Id = " + paramString1 + "startRecId = " + paramString2 + "pageSize = " + paramLong + "parseRecord = " + paramBoolean1 + "isRecurring = " + paramBoolean2, "dirtyOnly = " + paramBoolean3, 6);
    String str3;
    if (paramString1 == null)
    {
      str3 = "batchId  is null";
      FFSDebug.log(str1 + " failed, " + str3, 0);
      return null;
    }
    if (paramFFSConnectionHolder == null)
    {
      str3 = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1 + " failed, " + str3, 0);
      return null;
    }
    try
    {
      str3 = null;
      if ((paramString2 == null) || (paramString2.trim().length() == 0)) {
        paramString2 = "0";
      }
      paramString2 = FFSUtil.padWithChar(paramString2, 32, true, '0');
      localObject1 = new Object[] { paramString1, paramString2 };
      if (paramBoolean2) {
        str2 = "SELECT RecRecordId, BatchId, RDFIACHId, AddendaCount,  Amount, CheckDigit, CheckSerialNum, RecordDiscData, DocRefNum,  ItemResearchNum, PmtType, RecordType, TransCode, RecordSeqNum,  TerminalCity, TerminalState, TraceNum, OrigTraceNum, TransDate,  SettlementDate, SubmittedBy, SubmitDate,  RecordStatus, SrvClassCode, RecvCompId, BankAcctId, CardNum,  CardExpireDate, CardTransCode, ItemTypeInd, ProcessControl,  RecordContent,LogId, BankAcctType, PayAcct, PayeeName, Frequency,  StartDate, EndDate, PmtsCount, PayeeId, RecordCategory, PairedId, DirtyFlag, OffsetAccountID,TaxFormId,Memo  FROM ACH_RecRecord  WHERE BatchId = ? AND  RecRecordId > ? AND RecordStatus != 'CANCELEDON'";
      } else {
        str2 = "SELECT RecordId, BatchId, RDFIACHId,AddendaCount,  Amount, CheckDigit, CheckSerialNum, RecordDiscData, DocRefNum,  ItemResearchNum, PmtType, RecordType, TransCode, RecordSeqNum,  TerminalCity, TerminalState, TraceNum, OrigTraceNum, TransDate,  EffectiveDate, SettlementDate, SubmittedBy, SubmitDate,  RecordStatus, SrvClassCode, RecvCompId, BankAcctId, CardNum,  CardExpireDate, CardTransCode, ItemTypeInd, ProcessControl,  RecordContent,LogId, BankAcctType, PayAcct, PayeeName, PayeeId,  RecordCategory, PairedId, DirtyFlag, OffsetAccountID,TaxFormId,Memo   FROM ACH_Record  WHERE BatchId = ? AND  RecordId > ? AND RecordStatus != 'CANCELEDON'";
      }
      if (paramBoolean3 == true)
      {
        localObject1 = new Object[3];
        localObject1[0] = paramString1;
        localObject1[1] = paramString2;
        localObject1[2] = new Integer(1);
        if (paramBoolean2) {
          str2 = str2 + " AND DirtyFlag = ?";
        } else {
          str2 = str2 + " AND DirtyFlag = ?";
        }
      }
      if (paramBoolean2) {
        str2 = str2 + " ORDER BY RecRecordId";
      } else {
        str2 = str2 + " ORDER BY RecordId";
      }
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      while (localFFSResultSet.getNextRow())
      {
        k = 0;
        localACHRecordInfo = new ACHRecordInfo();
        a(localACHRecordInfo, localFFSResultSet, paramBoolean1, paramBoolean2);
        localArrayList.add(localACHRecordInfo);
        i++;
        j++;
        str3 = localACHRecordInfo.getRecordId();
        localObject2 = ACHAddenda.getACHAddendas(str3, localACHRecordInfo.getClassCode(), paramFFSConnectionHolder, paramBoolean1, paramBoolean2);
        if (localObject2 != null) {
          k = localObject2.length;
        }
        localACHRecordInfo.setAddenda((ACHAddendaInfo[])localObject2);
        j += k;
        FFSDebug.log(str1 + "::", "pageSize=" + paramLong + "recordCount=" + i + "recordAddendaCount" + j, 6);
        localACHRecordInfo.setStatusCode(0);
        localACHRecordInfo.setStatusMsg("Success");
        if ((paramLong != 0L) && (j >= paramLong)) {
          FFSDebug.log(str1 + " Exceeds pageSize. Break", 6);
        }
      }
      if (i == 0) {
        FFSDebug.log(str1 + ":: No record found", 1);
      }
    }
    catch (Exception localException1)
    {
      Object localObject1 = "***  " + str1 + ": failed:";
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
        FFSDebug.log("***  " + str1 + ": failed to close:" + localException2.toString(), 0);
      }
    }
    arrayOfACHRecordInfo = (ACHRecordInfo[])localArrayList.toArray(new ACHRecordInfo[0]);
    FFSDebug.log(str1 + ": len=" + arrayOfACHRecordInfo.length, 6);
    FFSDebug.log(str1 + ": done", 6);
    return arrayOfACHRecordInfo;
  }
  
  private static void a(ACHRecordInfo paramACHRecordInfo, FFSResultSet paramFFSResultSet, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    if (paramBoolean2 == true)
    {
      int i = paramFFSResultSet.getColumnInt("Frequency");
      int j = Integer.parseInt(paramFFSResultSet.getColumnString("PmtsCount"));
      paramACHRecordInfo.setRecordId(paramFFSResultSet.getColumnString("RecRecordId"));
      paramACHRecordInfo.setBatchId(paramFFSResultSet.getColumnString("BatchId"));
      paramACHRecordInfo.setRecordType(6);
      paramACHRecordInfo.setClassCode(paramFFSResultSet.getColumnString("SrvClassCode"));
      paramACHRecordInfo.setSubmitDate(paramFFSResultSet.getColumnString("SettlementDate"));
      paramACHRecordInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
      paramACHRecordInfo.setSettlementDate(paramFFSResultSet.getColumnString("SubmitDate"));
      paramACHRecordInfo.setRecordStatus(paramFFSResultSet.getColumnString("RecordStatus"));
      paramACHRecordInfo.setRecordContent(paramFFSResultSet.getColumnString("RecordContent"));
      paramACHRecordInfo.setStartDate(BPWUtil.getDateBeanFormat(paramFFSResultSet.getColumnInt("StartDate")));
      paramACHRecordInfo.setEndDate(BPWUtil.getDateBeanFormat(paramFFSResultSet.getColumnInt("EndDate")));
      paramACHRecordInfo.setPmtsCount(j);
      paramACHRecordInfo.setFrequency(FFSUtil.getFreqString(i));
      paramACHRecordInfo.setPayeeId(paramFFSResultSet.getColumnString("PayeeId"));
      paramACHRecordInfo.setPayeeName(paramFFSResultSet.getColumnString("PayeeName"));
      paramACHRecordInfo.setPayeeAcct(paramFFSResultSet.getColumnString("PayAcct"));
      paramACHRecordInfo.setBankAcctType(paramFFSResultSet.getColumnString("BankAcctType"));
      paramACHRecordInfo.setRecordCategory(paramFFSResultSet.getColumnString("RecordCategory"));
      paramACHRecordInfo.setPairedID(paramFFSResultSet.getColumnString("PairedId"));
      paramACHRecordInfo.setDirtyFlag(paramFFSResultSet.getColumnInt("DirtyFlag"));
      paramACHRecordInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
      paramACHRecordInfo.setOffsetAccountID(paramFFSResultSet.getColumnString("OffsetAccountID"));
      paramACHRecordInfo.setTaxFormId(paramFFSResultSet.getColumnString("TaxFormId"));
      Hashtable localHashtable2 = FFSUtil.stringToHashtable(paramFFSResultSet.getColumnString("Memo"));
      paramACHRecordInfo.setMemo(localHashtable2);
    }
    else
    {
      paramACHRecordInfo.setRecordId(paramFFSResultSet.getColumnString("RecordId"));
      paramACHRecordInfo.setBatchId(paramFFSResultSet.getColumnString("BatchId"));
      paramACHRecordInfo.setRecordType(6);
      paramACHRecordInfo.setClassCode(paramFFSResultSet.getColumnString("SrvClassCode"));
      paramACHRecordInfo.setSubmitDate(paramFFSResultSet.getColumnString("SubmitDate"));
      paramACHRecordInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
      paramACHRecordInfo.setStartDate(paramFFSResultSet.getColumnString("EffectiveDate"));
      paramACHRecordInfo.setSettlementDate(paramFFSResultSet.getColumnString("SettlementDate"));
      paramACHRecordInfo.setRecordStatus(paramFFSResultSet.getColumnString("RecordStatus"));
      paramACHRecordInfo.setRecordContent(paramFFSResultSet.getColumnString("RecordContent"));
      paramACHRecordInfo.setPayeeId(paramFFSResultSet.getColumnString("PayeeId"));
      paramACHRecordInfo.setPayeeName(paramFFSResultSet.getColumnString("PayeeName"));
      paramACHRecordInfo.setPayeeAcct(paramFFSResultSet.getColumnString("PayAcct"));
      paramACHRecordInfo.setBankAcctType(paramFFSResultSet.getColumnString("BankAcctType"));
      paramACHRecordInfo.setRecordCategory(paramFFSResultSet.getColumnString("RecordCategory"));
      paramACHRecordInfo.setPairedID(paramFFSResultSet.getColumnString("PairedId"));
      paramACHRecordInfo.setDirtyFlag(paramFFSResultSet.getColumnInt("DirtyFlag"));
      paramACHRecordInfo.setLogId(paramFFSResultSet.getColumnString("LogId"));
      paramACHRecordInfo.setOffsetAccountID(paramFFSResultSet.getColumnString("OffsetAccountID"));
      paramACHRecordInfo.setTaxFormId(paramFFSResultSet.getColumnString("TaxFormId"));
      Hashtable localHashtable1 = FFSUtil.stringToHashtable(paramFFSResultSet.getColumnString("Memo"));
      paramACHRecordInfo.setMemo(localHashtable1);
    }
    if (paramBoolean1 == true) {
      ACHAgent.parse(paramACHRecordInfo, false);
    }
    paramACHRecordInfo.setStatusCode(0);
    paramACHRecordInfo.setStatusMsg("Success");
  }
  
  private static Object[] a(ACHRecordInfo paramACHRecordInfo, String paramString, boolean paramBoolean)
    throws FFSException
  {
    ACHAgent.build(paramACHRecordInfo, false, false);
    if (paramBoolean == true)
    {
      arrayOfObject = new Object[] { paramACHRecordInfo.getRecordId(), paramACHRecordInfo.getBatchId(), paramACHRecordInfo.getFieldValueObject("Receiving_DFI_Identification") == null ? null : paramACHRecordInfo.getFieldValueObject("Receiving_DFI_Identification").toString(), paramACHRecordInfo.getPayeeId(), paramACHRecordInfo.getAddendaCount(), paramACHRecordInfo.getFieldValueObject("Amount") == null ? null : paramACHRecordInfo.getFieldValueObject("Amount").toString(), paramACHRecordInfo.getFieldValueObject("Check_Digit") == null ? null : paramACHRecordInfo.getFieldValueObject("Check_Digit").toString(), paramACHRecordInfo.getFieldValueObject("Check_Serial_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("Check_Serial_Number").toString(), paramACHRecordInfo.getFieldValueObject("Discretionary_Data") == null ? null : paramACHRecordInfo.getFieldValueObject("Discretionary_Data").toString(), paramACHRecordInfo.getFieldValueObject("Doc_Ref_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("Doc_Ref_Number").toString(), paramACHRecordInfo.getFieldValueObject("Item_Research_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("Item_Research_Number").toString(), paramACHRecordInfo.getFieldValueObject("Payment_Type_Code") == null ? null : paramACHRecordInfo.getFieldValueObject("Payment_Type_Code").toString(), paramACHRecordInfo.getFieldValueObject("Record_Type_Code") == null ? null : paramACHRecordInfo.getFieldValueObject("Record_Type_Code").toString(), paramACHRecordInfo.getFieldValueObject("Transaction_Code") == null ? null : paramACHRecordInfo.getFieldValueObject("Transaction_Code").toString(), paramACHRecordInfo.getFieldValueObject("Sequence_Number_Within_Batch") == null ? null : new Integer(paramACHRecordInfo.getFieldValueObject("Sequence_Number_Within_Batch").toString()), paramACHRecordInfo.getFieldValueObject("Terminal_City4") == null ? null : paramACHRecordInfo.getFieldValueObject("Terminal_City4").toString(), paramACHRecordInfo.getFieldValueObject("Terminal_State") == null ? null : paramACHRecordInfo.getFieldValueObject("Terminal_State").toString(), paramString, paramACHRecordInfo.getFieldValueObject("Original_Entry_Trace_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("Original_Entry_Trace_Number").toString(), paramACHRecordInfo.getSettlementDate(), paramACHRecordInfo.getSubmittedBy(), FFSUtil.getDateString(), "WILLPROCESSON", paramACHRecordInfo.getClassCode(), paramACHRecordInfo.getFieldValueObject("Receiving_Company_Identification_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("Receiving_Company_Identification_Number").toString(), paramACHRecordInfo.getFieldValueObject("DFI_Account_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("DFI_Account_Number").toString(), paramACHRecordInfo.getFieldValueObject("Individual_Card_Account_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("Individual_Card_Account_Number").toString(), paramACHRecordInfo.getFieldValueObject("Card_Exp_Date") == null ? null : paramACHRecordInfo.getFieldValueObject("Card_Exp_Date").toString(), paramACHRecordInfo.getFieldValueObject("Card_Transaction_Type_Code") == null ? null : paramACHRecordInfo.getFieldValueObject("Card_Transaction_Type_Code").toString(), paramACHRecordInfo.getFieldValueObject("Item_Type_Indicator") == null ? null : paramACHRecordInfo.getFieldValueObject("Item_Type_Indicator").toString(), paramACHRecordInfo.getFieldValueObject("Process_Control_Field") == null ? null : paramACHRecordInfo.getFieldValueObject("Process_Control_Field").toString(), paramACHRecordInfo.getRecordContent(), paramACHRecordInfo.getPayeeAcctForClassCode(), paramACHRecordInfo.getPayeeNameForClassCode(), paramACHRecordInfo.getBankAcctType(), new Integer(FFSUtil.getFreqInt(paramACHRecordInfo.getFrequency())), new Integer(BPWUtil.getDateDBFormat(paramACHRecordInfo.getStartDate())), new Integer(BPWUtil.getDateDBFormat(paramACHRecordInfo.getEndDate())), new Integer(paramACHRecordInfo.getPmtsCount()), paramACHRecordInfo.getLogId(), paramACHRecordInfo.getRecordCategory(), paramACHRecordInfo.getPairedID(), new Integer(0), paramACHRecordInfo.getOffsetAccountID(), paramACHRecordInfo.getTaxFormId(), paramACHRecordInfo.getMemo() == null ? null : FFSUtil.hashtableToString(paramACHRecordInfo.getMemo()) };
      return arrayOfObject;
    }
    Object[] arrayOfObject = { paramACHRecordInfo.getRecordId(), paramACHRecordInfo.getBatchId(), paramACHRecordInfo.getFieldValueObject("Receiving_DFI_Identification") == null ? null : paramACHRecordInfo.getFieldValueObject("Receiving_DFI_Identification").toString(), paramACHRecordInfo.getPayeeId(), paramACHRecordInfo.getAddendaCount(), paramACHRecordInfo.getFieldValueObject("Amount") == null ? null : paramACHRecordInfo.getFieldValueObject("Amount").toString(), paramACHRecordInfo.getFieldValueObject("Check_Digit") == null ? null : paramACHRecordInfo.getFieldValueObject("Check_Digit").toString(), paramACHRecordInfo.getFieldValueObject("Check_Serial_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("Check_Serial_Number").toString(), paramACHRecordInfo.getFieldValueObject("Discretionary_Data") == null ? null : paramACHRecordInfo.getFieldValueObject("Discretionary_Data").toString(), paramACHRecordInfo.getFieldValueObject("Doc_Ref_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("Doc_Ref_Number").toString(), paramACHRecordInfo.getFieldValueObject("Item_Research_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("Item_Research_Number").toString(), paramACHRecordInfo.getFieldValueObject("Payment_Type_Code") == null ? null : paramACHRecordInfo.getFieldValueObject("Payment_Type_Code").toString(), paramACHRecordInfo.getFieldValueObject("Record_Type_Code") == null ? null : paramACHRecordInfo.getFieldValueObject("Record_Type_Code").toString(), paramACHRecordInfo.getFieldValueObject("Transaction_Code") == null ? null : paramACHRecordInfo.getFieldValueObject("Transaction_Code").toString(), paramACHRecordInfo.getFieldValueObject("Sequence_Number_Within_Batch") == null ? null : new Integer(paramACHRecordInfo.getFieldValueObject("Sequence_Number_Within_Batch").toString()), paramACHRecordInfo.getFieldValueObject("Terminal_City4") == null ? null : paramACHRecordInfo.getFieldValueObject("Terminal_City4").toString(), paramACHRecordInfo.getFieldValueObject("Terminal_State") == null ? null : paramACHRecordInfo.getFieldValueObject("Terminal_State").toString(), paramString, paramACHRecordInfo.getFieldValueObject("Original_Entry_Trace_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("Original_Entry_Trace_Number").toString(), paramACHRecordInfo.getStartDate(), paramACHRecordInfo.getSettlementDate(), paramACHRecordInfo.getSubmittedBy(), FFSUtil.getDateString(), "WILLPROCESSON", paramACHRecordInfo.getClassCode(), paramACHRecordInfo.getFieldValueObject("Receiving_Company_Identification_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("Receiving_Company_Identification_Number").toString(), paramACHRecordInfo.getFieldValueObject("DFI_Account_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("DFI_Account_Number").toString(), paramACHRecordInfo.getFieldValueObject("Individual_Card_Account_Number") == null ? null : paramACHRecordInfo.getFieldValueObject("Individual_Card_Account_Number").toString(), paramACHRecordInfo.getFieldValueObject("Card_Exp_Date") == null ? null : paramACHRecordInfo.getFieldValueObject("Card_Exp_Date").toString(), paramACHRecordInfo.getFieldValueObject("Card_Transaction_Type_Code") == null ? null : paramACHRecordInfo.getFieldValueObject("Card_Transaction_Type_Code").toString(), paramACHRecordInfo.getFieldValueObject("Item_Type_Indicator") == null ? null : paramACHRecordInfo.getFieldValueObject("Item_Type_Indicator").toString(), paramACHRecordInfo.getFieldValueObject("Process_Control_Field") == null ? null : paramACHRecordInfo.getFieldValueObject("Process_Control_Field").toString(), paramACHRecordInfo.getRecordContent(), paramACHRecordInfo.getPayeeAcctForClassCode(), paramACHRecordInfo.getPayeeNameForClassCode(), paramACHRecordInfo.getBankAcctType(), paramACHRecordInfo.getLogId(), paramACHRecordInfo.getRecordCategory(), paramACHRecordInfo.getPairedID(), new Integer(0), paramACHRecordInfo.getOffsetAccountID(), paramACHRecordInfo.getTaxFormId(), paramACHRecordInfo.getMemo() == null ? null : FFSUtil.hashtableToString(paramACHRecordInfo.getMemo()) };
    return arrayOfObject;
  }
  
  private static Object[] a(ACHRecordInfo paramACHRecordInfo, boolean paramBoolean)
    throws FFSException
  {
    String str = paramACHRecordInfo.getFieldValueString("Check_Serial_Number");
    Object localObject = paramACHRecordInfo.getFieldValueObject("Number_Of_Addenda_Records");
    if (localObject == null) {
      localObject = paramACHRecordInfo.getAddendaCount();
    }
    ACHAgent.build(paramACHRecordInfo, false, false);
    if (paramBoolean == true)
    {
      arrayOfObject = new Object[] { paramACHRecordInfo.getPayeeId(), a(paramACHRecordInfo.getFieldValueObject("Receiving_DFI_Identification")), localObject, a(paramACHRecordInfo.getFieldValueObject("Amount")), a(paramACHRecordInfo.getFieldValueObject("Check_Digit")), str, a(paramACHRecordInfo.getFieldValueObject("Discretionary_Data")), a(paramACHRecordInfo.getFieldValueObject("Doc_Ref_Number")), a(paramACHRecordInfo.getFieldValueObject("Item_Research_Number")), a(paramACHRecordInfo.getFieldValueObject("Payment_Type_Code")), a(paramACHRecordInfo.getFieldValueObject("Record_Type_Code")), a(paramACHRecordInfo.getFieldValueObject("Transaction_Code")), ao(a(paramACHRecordInfo.getFieldValueObject("Sequence_Number_Within_Batch"))), a(paramACHRecordInfo.getFieldValueObject("Terminal_City4")), a(paramACHRecordInfo.getFieldValueObject("Terminal_State")), a(paramACHRecordInfo.getFieldValueObject("Trace_Number")), a(paramACHRecordInfo.getFieldValueObject("Original_Entry_Trace_Number")), paramACHRecordInfo.getStartDate(), paramACHRecordInfo.getSettlementDate(), paramACHRecordInfo.getSubmittedBy(), FFSUtil.getDateString(), paramACHRecordInfo.getClassCode(), a(paramACHRecordInfo.getFieldValueObject("Receiving_Company_Identification_Number")), a(paramACHRecordInfo.getFieldValueObject("DFI_Account_Number")), a(paramACHRecordInfo.getFieldValueObject("Individual_Card_Account_Number")), a(paramACHRecordInfo.getFieldValueObject("Card_Exp_Date")), a(paramACHRecordInfo.getFieldValueObject("Card_Transaction_Type_Code")), a(paramACHRecordInfo.getFieldValueObject("Item_Type_Indicator")), a(paramACHRecordInfo.getFieldValueObject("Process_Control_Field")), paramACHRecordInfo.getRecordContent(), paramACHRecordInfo.getPayeeAcctForClassCode(), paramACHRecordInfo.getPayeeNameForClassCode(), new Integer(FFSUtil.getFreqInt(paramACHRecordInfo.getFrequency())), new Integer(BPWUtil.getDateDBFormat(paramACHRecordInfo.getStartDate())), new Integer(BPWUtil.getDateDBFormat(paramACHRecordInfo.getEndDate())), new Integer(paramACHRecordInfo.getPmtsCount()), new Integer(0), paramACHRecordInfo.getOffsetAccountID(), paramACHRecordInfo.getBankAcctType(), paramACHRecordInfo.getRecordId() };
      return arrayOfObject;
    }
    Object[] arrayOfObject = { paramACHRecordInfo.getPayeeId(), a(paramACHRecordInfo.getFieldValueObject("Receiving_DFI_Identification")), localObject, a(paramACHRecordInfo.getFieldValueObject("Amount")), a(paramACHRecordInfo.getFieldValueObject("Check_Digit")), str, a(paramACHRecordInfo.getFieldValueObject("Discretionary_Data")), a(paramACHRecordInfo.getFieldValueObject("Doc_Ref_Number")), a(paramACHRecordInfo.getFieldValueObject("Item_Research_Number")), a(paramACHRecordInfo.getFieldValueObject("Payment_Type_Code")), a(paramACHRecordInfo.getFieldValueObject("Record_Type_Code")), a(paramACHRecordInfo.getFieldValueObject("Transaction_Code")), ao(a(paramACHRecordInfo.getFieldValueObject("Sequence_Number_Within_Batch"))), a(paramACHRecordInfo.getFieldValueObject("Terminal_City4")), a(paramACHRecordInfo.getFieldValueObject("Terminal_State")), a(paramACHRecordInfo.getFieldValueObject("Trace_Number")), a(paramACHRecordInfo.getFieldValueObject("Original_Entry_Trace_Number")), paramACHRecordInfo.getStartDate(), paramACHRecordInfo.getSettlementDate(), paramACHRecordInfo.getSubmittedBy(), FFSUtil.getDateString(), paramACHRecordInfo.getClassCode(), a(paramACHRecordInfo.getFieldValueObject("Receiving_Company_Identification_Number")), a(paramACHRecordInfo.getFieldValueObject("DFI_Account_Number")), a(paramACHRecordInfo.getFieldValueObject("Individual_Card_Account_Number")), a(paramACHRecordInfo.getFieldValueObject("Card_Exp_Date")), a(paramACHRecordInfo.getFieldValueObject("Card_Transaction_Type_Code")), a(paramACHRecordInfo.getFieldValueObject("Item_Type_Indicator")), a(paramACHRecordInfo.getFieldValueObject("Process_Control_Field")), paramACHRecordInfo.getRecordContent(), paramACHRecordInfo.getPayeeAcctForClassCode(), paramACHRecordInfo.getPayeeNameForClassCode(), new Integer(0), paramACHRecordInfo.getOffsetAccountID(), paramACHRecordInfo.getBankAcctType(), paramACHRecordInfo.getRecordId() };
    return arrayOfObject;
  }
  
  public static void updateBatchCache(ACHBatchCache paramACHBatchCache, ACHRecordInfo paramACHRecordInfo, int paramInt1, int paramInt2)
    throws FFSException
  {
    if (paramACHRecordInfo.isDebit() == true) {
      paramACHBatchCache.batchDebitSum = paramACHBatchCache.batchDebitSum.add(new BigDecimal(paramACHRecordInfo.getFieldValueDouble("Amount") * paramInt2));
    } else {
      paramACHBatchCache.batchCreditSum = paramACHBatchCache.batchCreditSum.add(new BigDecimal(paramACHRecordInfo.getFieldValueDouble("Amount") * paramInt2));
    }
    paramACHBatchCache.batchHash += paramACHRecordInfo.getFieldValueLong("Receiving_DFI_Identification") * paramInt2;
    paramACHBatchCache.batchEntryCount += paramInt1 * paramInt2;
    paramACHBatchCache.recordCount += paramInt2;
    if (paramACHRecordInfo.getOffsetAccountID() == null)
    {
      if (paramACHRecordInfo.isDebit() == true) {
        paramACHBatchCache.nonOffBatchDebitSum = paramACHBatchCache.nonOffBatchDebitSum.add(new BigDecimal(paramACHRecordInfo.getFieldValueDouble("Amount") * paramInt2));
      } else {
        paramACHBatchCache.nonOffBatchCreditSum = paramACHBatchCache.nonOffBatchCreditSum.add(new BigDecimal(paramACHRecordInfo.getFieldValueDouble("Amount") * paramInt2));
      }
      paramACHBatchCache.nonOffBatchEntryCount += paramInt1 * paramInt2;
    }
  }
  
  private static void a(ACHRecordInfo paramACHRecordInfo1, ACHRecordInfo paramACHRecordInfo2)
  {
    short s = 0;
    ACHAddendaInfo[] arrayOfACHAddendaInfo = paramACHRecordInfo2.getAddenda();
    if ((arrayOfACHAddendaInfo != null) && (arrayOfACHAddendaInfo.length != 0)) {
      s = 1;
    } else {
      s = 0;
    }
    paramACHRecordInfo1.setFieldValueObject("Addenda_Record_Indicator", new Short(s));
  }
  
  public static ACHRecordInfo[] getRecordsByPayee(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "ACHRecord.getRecordsByPayee: ";
    String str2 = null;
    FFSResultSet localFFSResultSet = null;
    ACHRecordInfo[] arrayOfACHRecordInfo = null;
    ACHRecordInfo localACHRecordInfo = null;
    ArrayList localArrayList = new ArrayList();
    int i = 0;
    FFSDebug.log(str1 + "start, " + "Payee Id = " + paramString + "isRecurring = " + paramBoolean2, 6);
    Object localObject1;
    if (paramString == null)
    {
      localObject1 = "payeeId  is null";
      FFSDebug.log(str1 + " failed, " + (String)localObject1, 0);
      return null;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1 + " failed, " + (String)localObject1, 0);
      return null;
    }
    try
    {
      localObject1 = new Object[] { paramString, "WILLPROCESSON" };
      if (paramBoolean2) {
        str2 = "SELECT a.RecRecordId, a.BatchId, a.RDFIACHId, a.AddendaCount,  a.Amount, a.CheckDigit, a.CheckSerialNum, a.RecordDiscData, a.DocRefNum,  a.ItemResearchNum, a.PmtType, a.RecordType, a.TransCode, a.RecordSeqNum,  a.TerminalCity, a.TerminalState, a.TraceNum, a.OrigTraceNum, a.TransDate,  a.SettlementDate, a.SubmittedBy, a.SubmitDate,  a.RecordStatus, a.SrvClassCode, a.RecvCompId, a.BankAcctId, a.CardNum,  a.CardExpireDate, a.CardTransCode, a.ItemTypeInd, a.ProcessControl,  a.RecordContent, a.LogId, a.BankAcctType, a.PayAcct, a.PayeeName, a.Frequency,  a.StartDate, a.EndDate, a.PmtsCount, a.PayeeId, a.RecordCategory, a.PairedId, a.OffsetAccountID,  a.TaxFormId, a.Memo  FROM ACH_RecRecord a, ACH_RecBatch b WHERE a.PayeeId = ? AND a.BatchId = b.RecBatchId AND b.BatchStatus = ? AND a.RecordStatus != 'CANCELEDON' ORDER BY a.BatchId, a.RecRecordId";
      } else {
        str2 = "SELECT a.RecordId, a.BatchId, a.RDFIACHId,a.AddendaCount,  a.Amount, a.CheckDigit, a.CheckSerialNum, a.RecordDiscData, a.DocRefNum,  a.ItemResearchNum, a.PmtType, a.RecordType, a.TransCode, a.RecordSeqNum,  a.TerminalCity, a.TerminalState, a.TraceNum, a.OrigTraceNum, a.TransDate,  a.EffectiveDate, a.SettlementDate, a.SubmittedBy, a.SubmitDate,  a.RecordStatus, a.SrvClassCode, a.RecvCompId, a.BankAcctId, a.CardNum,  a.CardExpireDate, a.CardTransCode, a.ItemTypeInd, a.ProcessControl,  a.RecordContent,a.LogId, a.BankAcctType, a.PayAcct, a.PayeeName, a.PayeeId,  a.RecordCategory, a.PairedId, a.OffsetAccountID,  a.TaxFormId, a.Memo  FROM ACH_Record a, ACH_Batch b WHERE a.PayeeId = ? AND a.BatchId = b.BatchId AND b.BatchStatus = ? AND a.RecordStatus != 'CANCELEDON' ORDER BY a.BatchId, a.RecordId";
      }
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      while (localFFSResultSet.getNextRow())
      {
        localACHRecordInfo = new ACHRecordInfo();
        a(localACHRecordInfo, localFFSResultSet, paramBoolean1, paramBoolean2);
        localArrayList.add(localACHRecordInfo);
        i++;
        FFSDebug.log(str1 + "::", "recordCount=" + i, 6);
        localACHRecordInfo.setStatusCode(0);
        localACHRecordInfo.setStatusMsg("Success");
      }
      if (i == 0) {
        FFSDebug.log(str1 + ":: No record found", 1);
      }
    }
    catch (Exception localException1)
    {
      String str3 = "***  " + str1 + ": failed:";
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
        FFSDebug.log("***  " + str1 + ": failed to close:" + localException2.toString(), 0);
      }
    }
    arrayOfACHRecordInfo = (ACHRecordInfo[])localArrayList.toArray(new ACHRecordInfo[0]);
    FFSDebug.log(str1 + ": len=" + arrayOfACHRecordInfo.length, 6);
    FFSDebug.log(str1 + ": done", 6);
    return arrayOfACHRecordInfo;
  }
  
  public static void setACHRecordDirtyFlag(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    String str1 = "ACHRecord.setACHRecordDirtyFlag: ";
    String str2 = "UPDATE ACH_Record set DirtyFlag = ?  WHERE PayeeId = ? and BatchId in  ( SELECT BatchId from ACH_Batch WHERE BatchStatus = 'WILLPROCESSON')";
    FFSDebug.log(str1 + "start...", 6);
    Object localObject;
    if (paramFFSConnectionHolder == null)
    {
      localObject = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1 + " failed, " + (String)localObject, 0);
      return;
    }
    try
    {
      FFSDebug.log(str1 + ":: payeeId=", paramString, 6);
      localObject = new Object[] { new Integer(1), paramString };
      str2 = "UPDATE ACH_RecRecord set DirtyFlag = ?  WHERE PayeeId = ? and BatchId in  ( SELECT RecBatchId from ACH_RecBatch WHERE BatchStatus = 'WILLPROCESSON')";
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      FFSDebug.log(str1 + "Recurring records set to dirty: " + i, 6);
      str2 = "UPDATE ACH_Record set DirtyFlag = ?  WHERE PayeeId = ? and BatchId in  ( SELECT BatchId from ACH_Batch WHERE BatchStatus = 'WILLPROCESSON')";
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, (Object[])localObject);
      FFSDebug.log(str1 + "Single records set to dirty: " + i, 6);
    }
    catch (Exception localException)
    {
      String str3 = "***  " + str1 + ": failed:";
      String str4 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str4, 0);
      throw new FFSException(localException, str3);
    }
    FFSDebug.log(str1 + ": done", 6);
  }
  
  public static int getNumberDistinctPairIdsOfBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHRecord.getNumberDistinctPairIdsOfBatch: ";
    String str2 = "SELECT COUNT(DISTINCT PairedId) FROM ACH_Record WHERE BatchId = ? AND RecordStatus != 'CANCELEDON'";
    if (paramBoolean) {
      str2 = "SELECT COUNT(DISTINCT PairedId) FROM ACH_RecRecord WHERE BatchId = ? AND RecordStatus != 'CANCELEDON'";
    }
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    FFSDebug.log(str1 + "start, " + "BatchId = " + paramString, 6);
    Object localObject1;
    if (paramString == null)
    {
      localObject1 = "batcHId  is null";
      FFSDebug.log(str1 + " failed, " + (String)localObject1, 0);
      return 0;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1 + " failed, " + (String)localObject1, 0);
      return 0;
    }
    try
    {
      localObject1 = new Object[] { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
    }
    catch (Exception localException1)
    {
      String str3 = "***  " + str1 + ": failed:";
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
        FFSDebug.log("***  " + str1 + ": failed to close:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1 + ": done. Record count = " + i, 6);
    return i;
  }
  
  public static int getNumberOfRecordsInBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHRecord.getNumberOfRecordsInBatch: ";
    String str2 = "SELECT COUNT(RecordId) FROM ACH_Record WHERE BatchId = ? AND RecordStatus != 'CANCELEDON'";
    if (paramBoolean) {
      str2 = "SELECT COUNT(RecRecordId) FROM ACH_RecRecord WHERE BatchId = ? AND RecordStatus != 'CANCELEDON'";
    }
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    FFSDebug.log(str1 + "start. BatchId = " + paramString, 6);
    Object localObject1;
    if (paramString == null)
    {
      localObject1 = "batcHId  is null";
      FFSDebug.log(str1 + " failed, " + (String)localObject1, 0);
      return 0;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1 + " failed, " + (String)localObject1, 0);
      return 0;
    }
    try
    {
      localObject1 = new Object[] { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
    }
    catch (Exception localException1)
    {
      String str3 = "***  " + str1 + ": failed:";
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
        FFSDebug.log("***  " + str1 + ": failed to close:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1 + ": done. Record count = " + i, 6);
    return i;
  }
  
  public static int getNumberOfRecordsInBatchByRecordCategory(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, boolean paramBoolean, String paramString2)
    throws FFSException
  {
    String str1 = "ACHRecord.getNumberOfRecordsInBatchByCategory: ";
    String str2 = "SELECT COUNT(RecordId) FROM ACH_Record WHERE BatchId = ? AND RecordCategory = ? AND RecordStatus != 'CANCELEDON'";
    if (paramBoolean) {
      str2 = "SELECT COUNT(RecRecordId) FROM ACH_RecRecord WHERE BatchId = ? AND RecordCategory = ? AND RecordStatus != 'CANCELEDON'";
    }
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    FFSDebug.log(str1 + "start. BatchId = " + paramString1 + ", Record Category = " + paramString2, 6);
    Object localObject1;
    if (paramString1 == null)
    {
      localObject1 = "batcHId  is null";
      FFSDebug.log(str1 + " failed, " + (String)localObject1, 0);
      return 0;
    }
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1 + " failed, " + (String)localObject1, 0);
      return 0;
    }
    try
    {
      localObject1 = new Object[] { paramString1, paramString2 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
    }
    catch (Exception localException1)
    {
      String str3 = "***  " + str1 + ": failed:";
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
        FFSDebug.log("***  " + str1 + ": failed to close:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1 + ": done. Record count = " + i, 6);
    return i;
  }
  
  public static int getNumberOfRecordsInBatchWithLaterPrenotePayeeSubmittedDate(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "ACHRecord.getNumberOfRecordsInBatchWithLaterPrenotePayeeSubmittedDate: ";
    String str2 = "SELECT COUNT(RecordId) FROM ACH_Record a, ACH_Payee c WHERE a.BatchId = ? AND a.PayeeId = c.PayeeId AND a.RecordStatus != 'CANCELEDON' AND ( c.PrenoteCredStatus = 'Pending' OR   c.PrenoteDebStatus = 'Pending' ) AND c.Status != 'CLOSED' AND  ( ( (c.PrenoteSubmitDate IS NOT NULL) AND (c.PrenoteSubmitDate > ?) ) OR  ( (c.PrenoteSubmitDate IS NULL) AND ";
    if (paramBoolean) {
      str2 = "SELECT COUNT(RecRecordId) FROM ACH_RecRecord a, ACH_RecBatch b, ACH_Payee c WHERE a.BatchId = ? AND a.BatchId = b.RecBatchId AND a.PayeeId = c.PayeeId AND a.RecordStatus != 'CANCELEDON' AND ( c.PrenoteCredStatus = 'Pending' OR   c.PrenoteDebStatus = 'Pending' ) AND c.Status != 'CLOSED' AND  ( ( (c.PrenoteSubmitDate IS NOT NULL) AND (c.PrenoteSubmitDate > ?) ) OR  ( (c.PrenoteSubmitDate IS NULL) AND ";
    }
    FFSResultSet localFFSResultSet = null;
    int i = 0;
    FFSDebug.log(str1 + "start. BatchId = " + paramString1 + ", BatchDate = " + paramString2, 6);
    String str3;
    if (paramString1 == null)
    {
      str3 = "batchId  is null";
      FFSDebug.log(str1 + " failed, " + str3, 0);
      return 0;
    }
    if (paramFFSConnectionHolder == null)
    {
      str3 = "FFSConnectionHolder object  is null";
      FFSDebug.log(str1 + " failed, " + str3, 0);
      return 0;
    }
    try
    {
      str3 = FFSUtil.getDateString("yyyy/MM/dd");
      str2 = str2 + " ('" + str3 + "' >= '" + paramString2 + "') ) )";
      localObject1 = new Object[] { paramString1, paramString2 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, (Object[])localObject1);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
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
    FFSDebug.log(str1 + ": done. Record count = " + i, 6);
    return i;
  }
  
  public static void generateACHRecordsFromACHRecRecords(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "ACHRecord.generateACHRecordsFromACHRecRecords:";
    FFSDebug.log(str1 + ": start, " + "recBatchId= " + paramString1 + ", batchId= " + paramString2, 6);
    if (paramFFSConnectionHolder == null)
    {
      localObject1 = str1 + "failed, FFSConnectionHolder object is Null";
      FFSDebug.log((String)localObject1, 0);
      throw new FFSException((String)localObject1);
    }
    Object localObject1 = null;
    int i = 0;
    String str2 = null;
    String str3 = null;
    Object[] arrayOfObject = null;
    try
    {
      String str4 = "SELECT RecRecordId,BatchId,PayeeId,RDFIACHId,AddendaCount,Amount,CheckDigit,CheckSerialNum,RecordDiscData,DocRefNum,ItemResearchNum,PmtType,RecordType,RecordCategory,PairedId,RecordContent,TransCode,RecvCompId,CardNum,CardExpireDate,CardTransCode,PayAcct,PayeeName,BankAcctId,BankAcctType,ItemTypeInd,ProcessControl,RecordSeqNum,TerminalCity,TerminalState,TraceNum,OrigTraceNum,TransDate,EffectiveDate,SettlementDate,SubmittedBy,SubmitDate,RecordStatus,SrvClassCode,Frequency,StartDate,EndDate,PmtsCount,LogId,LastModified,DirtyFlag,OffsetAccountID,TaxFormId,Memo  From ACH_RecRecord  Where BatchId = ?  And RecordStatus != 'CANCELEDON'  Order by RecRecordId ";
      localObject2 = new Object[] { paramString1 };
      localObject1 = DBUtil.openResultSet(paramFFSConnectionHolder, str4, (Object[])localObject2);
      while (((FFSResultSet)localObject1).getNextRow())
      {
        str2 = ((FFSResultSet)localObject1).getColumnString("RecRecordId");
        str3 = DBUtil.getNextIndexStringWithPadding("RecordID", 32, '0');
        str4 = "INSERT INTO ACH_Record (RecordId, BatchId, RDFIACHId, PayeeId, AddendaCount, Amount, CheckDigit, CheckSerialNum, RecordDiscData, DocRefNum, ItemResearchNum, PmtType, RecordType, TransCode, RecordSeqNum, TerminalCity, TerminalState, TraceNum, OrigTraceNum, EffectiveDate, SettlementDate, SubmittedBy, SubmitDate, RecordStatus, SrvClassCode, RecvCompId, CardTransCode, ItemTypeInd, ProcessControl, RecordContent, LogId, RecordCategory, PairedId, DirtyFlag, OffsetAccountID,TaxFormId,Memo)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        arrayOfObject = a(str3, paramString2, (FFSResultSet)localObject1);
        DBUtil.executeStatement(paramFFSConnectionHolder, str4, arrayOfObject);
        i++;
        i += ACHAddenda.generateACHAddendasFromACHRecAddendas(paramFFSConnectionHolder, str2, str3);
      }
    }
    catch (FFSException localFFSException)
    {
      localObject2 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject2, 0);
      throw localFFSException;
    }
    catch (Exception localException1)
    {
      Object localObject2 = "***  " + str1 + ": failed:";
      String str5 = FFSDebug.stackTrace(localException1);
      FFSDebug.log(str5, 0);
      throw new FFSException(localException1, (String)localObject2);
    }
    finally
    {
      try
      {
        if (localObject1 != null)
        {
          ((FFSResultSet)localObject1).close();
          localObject1 = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("***  " + str1 + ": failed to close:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1 + ": end, recordCount =" + i, 6);
  }
  
  private static Object[] a(String paramString1, String paramString2, FFSResultSet paramFFSResultSet)
    throws FFSException
  {
    Object[] arrayOfObject = { paramString1, paramString2, paramFFSResultSet.getColumnString("RDFIACHId"), paramFFSResultSet.getColumnString("PayeeId"), ao(paramFFSResultSet.getColumnString("AddendaCount")), paramFFSResultSet.getColumnString("Amount"), paramFFSResultSet.getColumnString("CheckDigit"), paramFFSResultSet.getColumnString("CheckSerialNum"), paramFFSResultSet.getColumnString("RecordDiscData"), paramFFSResultSet.getColumnString("DocRefNum"), paramFFSResultSet.getColumnString("ItemResearchNum"), paramFFSResultSet.getColumnString("PmtType"), paramFFSResultSet.getColumnString("RecordType"), paramFFSResultSet.getColumnString("TransCode"), ao(paramFFSResultSet.getColumnString("RecordSeqNum")), paramFFSResultSet.getColumnString("TerminalCity"), paramFFSResultSet.getColumnString("TerminalState"), paramFFSResultSet.getColumnString("TraceNum"), paramFFSResultSet.getColumnString("OrigTraceNum"), paramFFSResultSet.getColumnString("EffectiveDate"), paramFFSResultSet.getColumnString("SettlementDate"), paramFFSResultSet.getColumnString("SubmittedBy"), FFSUtil.getDateString(), "WILLPROCESSON", paramFFSResultSet.getColumnString("SrvClassCode"), paramFFSResultSet.getColumnString("RecvCompId"), paramFFSResultSet.getColumnString("CardTransCode"), paramFFSResultSet.getColumnString("ItemTypeInd"), paramFFSResultSet.getColumnString("ProcessControl"), paramFFSResultSet.getColumnString("RecordContent"), paramFFSResultSet.getColumnString("LogId"), paramFFSResultSet.getColumnString("RecordCategory"), paramFFSResultSet.getColumnString("PairedId"), new Integer(0), paramFFSResultSet.getColumnString("OffsetAccountID"), paramFFSResultSet.getColumnString("TaxFormId"), paramFFSResultSet.getColumnString("Memo") };
    return arrayOfObject;
  }
  
  private static boolean a(ACHRecordInfo paramACHRecordInfo)
  {
    String str = "ACHRecord.validateACHRecordInfo: ";
    FFSDebug.log(str + "start", 6);
    Object localObject = paramACHRecordInfo.getFieldValueObject("Amount");
    if (localObject == null)
    {
      paramACHRecordInfo.setStatusCode(23910);
      paramACHRecordInfo.setStatusMsg("Amount field doesn't exist");
      FFSDebug.log(str + "failed - " + paramACHRecordInfo.getStatusMsg());
      return false;
    }
    BigDecimal localBigDecimal = null;
    try
    {
      if ((localObject instanceof Long))
      {
        localBigDecimal = FFSUtil.setDefaultScale(BigDecimal.valueOf(((Long)localObject).longValue()).movePointLeft(2));
      }
      else if ((localObject instanceof String))
      {
        localBigDecimal = FFSUtil.setDefaultScale(FFSUtil.getBigDecimal((String)localObject).movePointLeft(2));
      }
      else if ((localObject instanceof Integer))
      {
        localBigDecimal = FFSUtil.setDefaultScale(BigDecimal.valueOf(((Integer)localObject).longValue()).movePointLeft(2));
      }
      else if ((localObject instanceof Short))
      {
        localBigDecimal = FFSUtil.setDefaultScale(BigDecimal.valueOf(((Short)localObject).longValue()).movePointLeft(2));
      }
      else
      {
        paramACHRecordInfo.setStatusCode(23930);
        paramACHRecordInfo.setStatusMsg("Type of Amount field is unknown - " + localObject.getClass().getName());
        FFSDebug.log(str + "failed - " + paramACHRecordInfo.getStatusMsg(), 0);
        return false;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      paramACHRecordInfo.setStatusCode(23920);
      paramACHRecordInfo.setStatusMsg("Invalid value in Amount field");
      FFSDebug.log(str + "failed - " + paramACHRecordInfo.getStatusMsg(), 0);
      return false;
    }
    if (localBigDecimal.compareTo(ACHConsts.MAX_RECORD_AMOUNT_BD) > 0)
    {
      paramACHRecordInfo.setStatusCode(23940);
      paramACHRecordInfo.setStatusMsg("Amount value exceeds the limit 99999999.99");
      FFSDebug.log(str + "failed - " + paramACHRecordInfo.getStatusMsg(), 0);
    }
    return true;
  }
  
  private static String a(Object paramObject)
  {
    if (paramObject != null) {
      return paramObject.toString();
    }
    return null;
  }
  
  private static Integer ao(String paramString)
  {
    if (paramString != null) {
      try
      {
        return new Integer(paramString);
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.ACHRecord
 * JD-Core Version:    0.7.0.1
 */