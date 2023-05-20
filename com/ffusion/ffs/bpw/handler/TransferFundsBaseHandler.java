package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.BPWExtraInfo;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.ExternalTransferAccount;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;

public abstract class TransferFundsBaseHandler
  implements BPWResource, FFSConst, DBConsts
{
  protected static final String PROCESSID = "ProcessId";
  protected static final String iA = "PossibleDuplicate";
  protected static final String iC = "DBTransKey";
  protected static final int ix = 1;
  protected PropertyConfig iv = null;
  protected boolean iy = false;
  protected int iz = 0;
  protected String iw = null;
  protected String iD = null;
  protected boolean iB = false;
  
  protected void aa()
  {
    this.iv = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
    if (this.iv.LogLevel >= 4) {
      this.iy = true;
    }
    this.iz = this.iv.getBatchSize();
    if (this.iz < 0) {
      this.iz = 0;
    }
  }
  
  protected void a(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean, String paramString)
    throws Exception
  {
    try
    {
      FFSDebug.log(paramString, " begin ", BPWLocaleUtil.getMessage(21460, new String[] { String.valueOf(paramInt), String.valueOf(paramEventInfoArray._array.length) }, "TRANSFER_MESSAGE"), 6);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log(paramString, FFSDebug.stackTrace(localThrowable), 0);
    }
    if (paramInt == 0)
    {
      this.iw = paramEventInfoArray._array[0].FIId;
      this.iD = paramEventInfoArray._array[0].processId;
      if ((paramBoolean) && (paramEventInfoArray._array[0].fileBasedRecovery == 1)) {
        this.iB = true;
      }
    }
    else if (paramInt == 2)
    {
      if (this.iw == null) {
        this.iw = paramEventInfoArray._array[0].FIId;
      }
      if (this.iD == null) {
        this.iD = paramEventInfoArray._array[0].processId;
      }
      if ((paramBoolean) && (paramEventInfoArray._array[0].fileBasedRecovery == 1)) {
        this.iB = true;
      }
      if ((this.iw == null) || ((this.iw != null) && (this.iw.trim().length() == 0)))
      {
        FFSDebug.log(paramString, BPWLocaleUtil.getMessage(21520, null, "TRANSFER_MESSAGE"), 0);
        return;
      }
      if ((this.iD == null) || ((this.iD != null) && (this.iD.trim().length() == 0)))
      {
        FFSDebug.log(paramString, BPWLocaleUtil.getMessage(21530, null, "TRANSFER_MESSAGE"), 0);
        return;
      }
      if (paramBoolean) {
        c(paramFFSConnectionHolder, paramString);
      } else {
        b(paramFFSConnectionHolder, paramString);
      }
      this.iw = null;
      this.iD = null;
      this.iB = false;
    }
    FFSDebug.log(paramString, " end ", 6);
  }
  
  protected abstract void b(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception;
  
  protected abstract void c(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception;
  
  protected static TransferInfo a(FFSConnectionHolder paramFFSConnectionHolder, ResultSet paramResultSet, HashMap paramHashMap, String paramString)
    throws Exception
  {
    TransferInfo localTransferInfo = new TransferInfo();
    String str = (String)paramHashMap.get("DBTransKey");
    if (str != null) {
      localTransferInfo.setDbTransKey(str);
    }
    str = (String)paramHashMap.get("ProcessId");
    if (str != null) {
      localTransferInfo.setLastProcessId(str);
    } else {
      localTransferInfo.setLastProcessId(paramResultSet.getString("LastProcessId"));
    }
    str = (String)paramHashMap.get("PossibleDuplicate");
    if (str != null) {
      try
      {
        localTransferInfo.setPossibleDuplicate(Boolean.valueOf(str).booleanValue());
      }
      catch (Throwable localThrowable)
      {
        FFSDebug.log(FFSDebug.stackTrace(localThrowable), 0);
      }
    }
    localTransferInfo.setSrvrTId(paramResultSet.getString("SrvrTId"));
    localTransferInfo.setCustomerId(paramResultSet.getString("CustomerId"));
    localTransferInfo.setFIId(paramResultSet.getString("FIId"));
    localTransferInfo.setTransferType(paramResultSet.getString("TransferType"));
    localTransferInfo.setTransferDest(paramResultSet.getString("TransferDest"));
    localTransferInfo.setTransferGroup(paramResultSet.getString("TransferGroup"));
    localTransferInfo.setTransferCategory(paramResultSet.getString("TransferCategory"));
    localTransferInfo.setBankFromRtn(paramResultSet.getString("BankFromRtn"));
    localTransferInfo.setAccountFromNum(paramResultSet.getString("AccountFromNum"));
    localTransferInfo.setAccountFromType(paramResultSet.getString("AccountFromType"));
    localTransferInfo.setAccountToId(paramResultSet.getString("ExternalAcctId"));
    localTransferInfo.setAmount(paramResultSet.getString("Amount"));
    localTransferInfo.setAmountCurrency(paramResultSet.getString("AmountCurrency"));
    localTransferInfo.setOrigAmount(paramResultSet.getString("OrigAmount"));
    localTransferInfo.setOrigCurrency(paramResultSet.getString("OrigCurrency"));
    localTransferInfo.setDateCreate(paramResultSet.getString("DateCreate"));
    localTransferInfo.setDateDue(paramResultSet.getString("DateDue"));
    localTransferInfo.setDateToPost(paramResultSet.getString("DateToPost"));
    localTransferInfo.setDatePosted(paramResultSet.getString("DatePosted"));
    localTransferInfo.setPrcStatus(paramResultSet.getString("Status"));
    localTransferInfo.setMemo(paramResultSet.getString("Memo"));
    localTransferInfo.setTemplateScope(paramResultSet.getString("TemplateScope"));
    localTransferInfo.setTemplateNickName(paramResultSet.getString("TemplateNickName"));
    localTransferInfo.setSourceTemplateId(paramResultSet.getString("SourceTemplateId"));
    localTransferInfo.setSourceRecSrvrTId(paramResultSet.getString("SourceRecSrvrTId"));
    localTransferInfo.setSubmittedBy(paramResultSet.getString("SubmittedBy"));
    localTransferInfo.setLogId(paramResultSet.getString("LogId"));
    localTransferInfo.setOriginatingUserId(paramResultSet.getString("OriginatingUserId"));
    localTransferInfo.setConfirmNum(paramResultSet.getString("ConfirmNum"));
    localTransferInfo.setConfirmMsg(paramResultSet.getString("ConfirmMsg"));
    localTransferInfo.setTypeDetail(paramResultSet.getString("TypeDetail"));
    localTransferInfo.setLastChangeDate(paramResultSet.getString("LastChangeDate"));
    localTransferInfo.setProcessType(paramResultSet.getInt("ProcessType"));
    localTransferInfo.setProcessLeadNumber(paramResultSet.getInt("ProcessLeadNumber"));
    localTransferInfo.setProcessDate(paramResultSet.getString("ProcessDate"));
    localTransferInfo.setAction(paramResultSet.getString("Action"));
    localTransferInfo.setFundsRetry(paramResultSet.getInt("FundsRetry"));
    localTransferInfo.setFundsProcessing(paramResultSet.getInt("FundsProcessing"));
    localTransferInfo.setAccountFromId(paramResultSet.getString("AccountFromId"));
    localTransferInfo.setBankFromRtnType(paramResultSet.getString("BankFromRtnType"));
    localTransferInfo.setProcessNumber(paramResultSet.getInt("ProcessNumber"));
    jdMethod_if(localTransferInfo, paramFFSConnectionHolder, paramString);
    return localTransferInfo;
  }
  
  private static void jdMethod_if(TransferInfo paramTransferInfo, FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    ExtTransferAcctInfo localExtTransferAcctInfo = null;
    Object localObject = null;
    Hashtable localHashtable = null;
    try
    {
      String str1 = paramTransferInfo.getAccountFromId();
      localExtTransferAcctInfo = new ExtTransferAcctInfo();
      if ((str1 != null) && (str1.trim().length() > 0))
      {
        localExtTransferAcctInfo.setAcctId(str1);
        localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo, false, false);
        if (localExtTransferAcctInfo.getStatusCode() != 0)
        {
          paramTransferInfo.setStatusCode(localExtTransferAcctInfo.getStatusCode());
          paramTransferInfo.setStatusMsg(localExtTransferAcctInfo.getStatusMsg());
          return;
        }
        paramTransferInfo.setAccountFromInfo(localExtTransferAcctInfo);
      }
      else
      {
        localExtTransferAcctInfo.setAcctBankRtn(paramTransferInfo.getBankFromRtn());
        localExtTransferAcctInfo.setBankRtnType(paramTransferInfo.getBankFromRtnType());
        localExtTransferAcctInfo.setAcctNum(paramTransferInfo.getAccountFromNum());
        localExtTransferAcctInfo.setAcctType(paramTransferInfo.getAccountFromType());
        paramTransferInfo.setAccountFromInfo(localExtTransferAcctInfo);
      }
      str2 = paramTransferInfo.getAccountToId();
      if ((str2 != null) && (str2.trim().length() > 0))
      {
        localExtTransferAcctInfo = new ExtTransferAcctInfo();
        localExtTransferAcctInfo.setAcctId(str2);
        localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo, false, false);
        if (localExtTransferAcctInfo.getStatusCode() != 0)
        {
          paramTransferInfo.setStatusCode(localExtTransferAcctInfo.getStatusCode());
          paramTransferInfo.setStatusMsg(localExtTransferAcctInfo.getStatusMsg());
          return;
        }
        paramTransferInfo.setAccountToNum(localExtTransferAcctInfo.getAcctNum());
        paramTransferInfo.setAccountToType(localExtTransferAcctInfo.getAcctType());
        paramTransferInfo.setBankToRtn(localExtTransferAcctInfo.getAcctBankRtn());
        paramTransferInfo.setAccountToInfo(localExtTransferAcctInfo);
      }
      localHashtable = BPWExtraInfo.getXtraInfo(paramFFSConnectionHolder, paramTransferInfo.getFIId(), paramTransferInfo.getSrvrTId(), paramTransferInfo.getTransferType().trim() + "_" + "TRANSFER");
      paramTransferInfo.setExtInfo(localHashtable);
      FFSDebug.log(paramString, "populated XtraInfo = " + localHashtable, 6);
      paramTransferInfo.setStatusCode(0);
      paramTransferInfo.setStatusMsg("Success");
    }
    catch (Throwable localThrowable)
    {
      String str2 = paramString + "failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
  }
  
  protected static String Z()
  {
    DateFormat localDateFormat = DateFormatUtil.getFormatter("yyyyMMdd");
    StringBuffer localStringBuffer = new StringBuffer(localDateFormat.format(new Date()));
    localStringBuffer.append("00");
    return localStringBuffer.toString();
  }
  
  protected static void a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, int paramInt, ILocalizable paramILocalizable, String paramString)
  {
    if ((paramTransferInfo == null) || (paramILocalizable == null)) {
      return;
    }
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    int i = 0;
    AuditLogRecord localAuditLogRecord = null;
    try
    {
      str3 = paramTransferInfo.getSubmittedBy();
      str4 = paramTransferInfo.getAmount();
      if ((str4 == null) || (str4.trim().length() == 0)) {
        str4 = "-1";
      }
      str1 = paramTransferInfo.buildToAcctId();
      str2 = paramTransferInfo.buildFromAcctId();
      if (paramTransferInfo.getCustomerId().equals(paramTransferInfo.getSubmittedBy())) {
        i = 0;
      } else {
        i = Integer.parseInt(paramTransferInfo.getCustomerId());
      }
      localAuditLogRecord = new AuditLogRecord(str3, "", "", paramILocalizable, paramTransferInfo.getLogId(), paramInt, i, new BigDecimal(str4), paramTransferInfo.getAmountCurrency(), paramTransferInfo.getSrvrTId(), paramTransferInfo.getPrcStatus(), str1, paramTransferInfo.getBankToRtn(), str2, paramTransferInfo.getBankFromRtn(), -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log(paramString, FFSDebug.stackTrace(localThrowable), 0);
    }
  }
  
  protected static void a(TransferInfo paramTransferInfo, int paramInt, ILocalizable paramILocalizable, String paramString)
  {
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        FFSDebug.log(paramString, BPWLocaleUtil.getMessage(20020, null, "WIRE_MESSAGE"), 0);
        return;
      }
      a(localFFSConnectionHolder, paramTransferInfo, paramInt, paramILocalizable, paramString);
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log(paramString, FFSDebug.stackTrace(localThrowable), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.TransferFundsBaseHandler
 * JD-Core Version:    0.7.0.1
 */