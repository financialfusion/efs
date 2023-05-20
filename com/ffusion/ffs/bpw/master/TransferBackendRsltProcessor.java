package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.ExternalTransferAccount;
import com.ffusion.ffs.bpw.db.Transfer;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.AuditLogRecord;
import java.math.BigDecimal;

public class TransferBackendRsltProcessor
  implements BPWResource, FFSConst, DBConsts
{
  protected int bG;
  
  public void processRslt(TransferInfo[] paramArrayOfTransferInfo)
    throws FFSException
  {
    String str1 = "TransferBackendRsltProcessor.ProcessRslt: ";
    if ((paramArrayOfTransferInfo == null) || (paramArrayOfTransferInfo.length == 0))
    {
      FFSDebug.log(str1 + "Empty result batch received, nothing to process!", 0);
      return;
    }
    FFSDebug.log(str1, "start, transferRslts.length: " + paramArrayOfTransferInfo.length, 6);
    this.bG = jdMethod_byte();
    int i = 1;
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str2;
    if (paramArrayOfTransferInfo[0].getDbTransKey() != null)
    {
      DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
      str2 = paramArrayOfTransferInfo[0].getDbTransKey();
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(str2);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      i = 0;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      FFSDebug.log("ERRORCODE:20020", str1, "Failed to obtain connection to bpw database....", 0);
      throw new BPWException(str1 + "Failed to obtain connection to bpw database....");
    }
    try
    {
      for (int j = 0; j < paramArrayOfTransferInfo.length; j++) {
        if (paramArrayOfTransferInfo[j] == null)
        {
          FFSDebug.log(str1 + "Invalid transfer result #: " + j, 0);
          paramArrayOfTransferInfo[j] = new TransferInfo();
          paramArrayOfTransferInfo[j].setStatusCode(16000);
          paramArrayOfTransferInfo[j].setStatusMsg(" is null");
        }
        else if ((paramArrayOfTransferInfo[j].getSrvrTId() == null) || (paramArrayOfTransferInfo[j].getSrvrTId().length() == 0))
        {
          FFSDebug.log(str1, "Invalid srvrTid for transfer result # " + j, 0);
          paramArrayOfTransferInfo[j].setStatusCode(16010);
          paramArrayOfTransferInfo[j].setStatusMsg("TransferInfo contains null SrvrTId");
        }
        else
        {
          FFSDebug.log(str1, "Processing transfer result: " + paramArrayOfTransferInfo[j], 6);
          a(localFFSConnectionHolder, paramArrayOfTransferInfo[j]);
          FFSDebug.log(str1, "Processed transfer result: " + paramArrayOfTransferInfo[j], 6);
        }
      }
      if (i == 0) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Throwable localThrowable)
    {
      if (i == 0) {
        localFFSConnectionHolder.conn.rollback();
      }
      str2 = str1 + "Failed. Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, 0);
      throw new FFSException(str2);
    }
    finally
    {
      if (i == 0)
      {
        FFSDebug.log(str1, "Releasing DB connection", 6);
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    if (paramTransferInfo == null)
    {
      FFSDebug.log("TransferBackendRsltProcessor.processOneTransferRslt: Empty backend response !", 0);
      return;
    }
    FFSDebug.log("TransferBackendRsltProcessor.processOneTransferRslt: ", "start, tranferRslt: ", paramTransferInfo.toString(), 6);
    try
    {
      TransferInfo localTransferInfo = new TransferInfo();
      localTransferInfo.setSrvrTId(paramTransferInfo.getSrvrTId());
      localObject1 = paramTransferInfo.getLastProcessId();
      localObject2 = paramTransferInfo.getAmount();
      String str1 = paramTransferInfo.getToAmount();
      localTransferInfo = Transfer.getTransferInfo(paramFFSConnectionHolder, localTransferInfo, false);
      if (localTransferInfo.getStatusCode() == 0)
      {
        if (localTransferInfo.getSubmittedBy() != null) {
          paramTransferInfo.setSubmittedBy(localTransferInfo.getSubmittedBy());
        }
        if (localObject2 == null) {
          paramTransferInfo.setAmount(localTransferInfo.getAmount());
        }
        paramTransferInfo.setAmountCurrency(localTransferInfo.getAmountCurrency());
        if (str1 == null) {
          paramTransferInfo.setToAmount(localTransferInfo.getToAmount());
        }
        paramTransferInfo.setToAmountCurrency(localTransferInfo.getToAmountCurrency());
        paramTransferInfo.setBankFromRtn(localTransferInfo.getBankFromRtn());
        paramTransferInfo.setBankToRtn(localTransferInfo.getBankToRtn());
        paramTransferInfo.setAccountToId(localTransferInfo.getAccountToId());
        paramTransferInfo.setAccountFromId(localTransferInfo.getAccountFromId());
        paramTransferInfo.setTransferDest(localTransferInfo.getTransferDest());
        paramTransferInfo.setAccountFromNum(localTransferInfo.getAccountFromNum());
        paramTransferInfo.setAccountFromType(localTransferInfo.getAccountFromType());
        paramTransferInfo.setAccountToNum(localTransferInfo.getAccountToNum());
        paramTransferInfo.setAccountToType(localTransferInfo.getAccountToType());
        paramTransferInfo.setLogId(localTransferInfo.getLogId());
      }
      if (paramTransferInfo.getLogId() == null) {
        paramTransferInfo.setLogId(BPWTrackingIDGenerator.getNextId());
      }
      localTransferInfo.setLastProcessId((String)localObject1);
      if (localTransferInfo.getStatusCode() != 0)
      {
        str2 = "TransferBackendRsltProcessor.processOneTransferRslt: Process transfer result failed, Reason =" + localTransferInfo.getStatusMsg();
        FFSDebug.log(str2, 6);
        if (this.bG >= 1)
        {
          localObject3 = BPWLocaleUtil.getLocalizableMessage(1051, new Object[] { localTransferInfo.getStatusMsg() }, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
          jdMethod_if(paramTransferInfo, (ILocalizable)localObject3);
        }
        return;
      }
      String str2 = paramTransferInfo.getPrcStatus();
      Object localObject3 = localTransferInfo.getPrcStatus();
      FFSDebug.log("TransferBackendRsltProcessor.processOneTransferRslt: ", "rsltStatus: ", str2, 6);
      FFSDebug.log("TransferBackendRsltProcessor.processOneTransferRslt: ", "currStatus: ", (String)localObject3, 6);
      LocalizableString localLocalizableString;
      if ((str2 == null) || (str2.length() == 0))
      {
        FFSDebug.log("TransferBackendRsltProcessor.processOneTransferRslt:  invalid status passed from backend :", str2, ", for transfer :", paramTransferInfo.toString(), 0);
        if (this.bG >= 1)
        {
          localLocalizableString = BPWLocaleUtil.getLocalizableMessage(1052, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
          jdMethod_if(paramFFSConnectionHolder, paramTransferInfo, localLocalizableString);
        }
        if (!a(paramFFSConnectionHolder, localTransferInfo, this.bG))
        {
          localTransferInfo.setPrcStatus("LIMIT_REVERT_FAILED");
          Transfer.updateStatusFromAdapter(paramFFSConnectionHolder, localTransferInfo);
        }
        return;
      }
      if (str2.equals("BACKENDFAILED"))
      {
        FFSDebug.log("TransferBackendRsltProcessor.processOneTransferRslt:  backend failed transfer " + paramTransferInfo.toString(), 0);
        if (!a(paramFFSConnectionHolder, localTransferInfo, this.bG))
        {
          FFSDebug.log("TransferBackendRsltProcessor.processOneTransferRslt: failed to revert limit of backend failed transfer", 0);
          localTransferInfo.setPrcStatus("LIMIT_REVERT_FAILED");
          Transfer.updateStatusFromAdapter(paramFFSConnectionHolder, localTransferInfo);
          if (this.bG >= 4)
          {
            localLocalizableString = BPWLocaleUtil.getLocalizableMessage(1053, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
            jdMethod_if(paramFFSConnectionHolder, paramTransferInfo, localLocalizableString);
          }
          return;
        }
        Transfer.updateStatusFromAdapter(paramFFSConnectionHolder, paramTransferInfo);
        if (this.bG >= 4)
        {
          localLocalizableString = BPWLocaleUtil.getLocalizableMessage(1054, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
          jdMethod_if(paramFFSConnectionHolder, paramTransferInfo, localLocalizableString);
        }
        return;
      }
      if (str2.equals(localObject3))
      {
        FFSDebug.log("TransferBackendRsltProcessor.processOneTransferRslt: Transfer is already processed.", 6);
        return;
      }
      if ((localObject3 == null) || (((String)localObject3).length() == 0) || ((!((String)localObject3).equals("WILLPROCESSON")) && (!((String)localObject3).equals("INPROCESS")) && (!((String)localObject3).equals("BACKENDPENDING"))))
      {
        FFSDebug.log("TransferBackendRsltProcessor.processOneTransferRslt: Invalid status in BPW db :", (String)localObject3, ", for transfer :", localTransferInfo.toString(), 0);
        if (this.bG >= 1)
        {
          localLocalizableString = BPWLocaleUtil.getLocalizableMessage(1055, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
          jdMethod_if(paramFFSConnectionHolder, localTransferInfo, localLocalizableString);
        }
        if (!a(paramFFSConnectionHolder, localTransferInfo, this.bG))
        {
          FFSDebug.log("TransferBackendRsltProcessor.processOneTransferRslt: failed to revert limit of invalid status transfer", 6);
          localTransferInfo.setPrcStatus("LIMIT_REVERT_FAILED");
          Transfer.updateStatusFromAdapter(paramFFSConnectionHolder, localTransferInfo);
          if (this.bG >= 4)
          {
            localLocalizableString = BPWLocaleUtil.getLocalizableMessage(1053, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
            jdMethod_if(paramFFSConnectionHolder, localTransferInfo, localLocalizableString);
          }
        }
        return;
      }
      if ((localObject2 != null) && (str1 != null)) {
        Transfer.updateStatusAndAmountFromAdapter(paramFFSConnectionHolder, paramTransferInfo);
      } else {
        Transfer.updateStatusFromAdapter(paramFFSConnectionHolder, paramTransferInfo);
      }
      if (this.bG >= 4)
      {
        localLocalizableString = BPWLocaleUtil.getLocalizableMessage(1056, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        jdMethod_if(paramFFSConnectionHolder, paramTransferInfo, localLocalizableString);
      }
    }
    catch (FFSException localFFSException)
    {
      FFSDebug.log("TransferBackendRsltProcessor.processOneTransferRslt: " + localFFSException.toString(), 0);
      if (this.bG >= 1)
      {
        localObject1 = BPWLocaleUtil.getLocalizableMessage(1057, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        jdMethod_if(paramFFSConnectionHolder, paramTransferInfo, (ILocalizable)localObject1);
      }
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      Object localObject2;
      Object localObject1 = "TransferBackendRsltProcessor.processOneTransferRslt:  Error:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, 0);
      if (this.bG >= 1)
      {
        localObject2 = BPWLocaleUtil.getLocalizableMessage(1058, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        jdMethod_if(paramFFSConnectionHolder, paramTransferInfo, (ILocalizable)localObject2);
      }
      throw new FFSException((String)localObject1);
    }
    FFSDebug.log("TransferBackendRsltProcessor.processOneTransferRslt: ", " done", 6);
  }
  
  private int jdMethod_byte()
  {
    String str = "TransferBackendRsltProcessor.getAuditLogLevel: ";
    int i = 0;
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      i = localPropertyConfig.LogLevel;
    }
    catch (Exception localException)
    {
      FFSDebug.log(str, "Invalid Audit log level value", 0);
      i = 0;
    }
    return i;
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, ILocalizable paramILocalizable)
  {
    a(paramFFSConnectionHolder, paramTransferInfo, paramILocalizable);
  }
  
  private void jdMethod_if(TransferInfo paramTransferInfo, ILocalizable paramILocalizable)
  {
    String str1 = "TransferBackendRsltProcessor.doErrorAuditLog: ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        String str2 = str1 + "Can not get DB Connection.";
        FFSDebug.log(str2, 0);
        throw new FFSException(str2);
      }
      a(localFFSConnectionHolder, paramTransferInfo, paramILocalizable);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      String str3 = str1 + " failed " + localException.toString();
      FFSDebug.log(str3 + FFSDebug.stackTrace(localException), 0);
      localFFSConnectionHolder.conn.rollback();
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, ILocalizable paramILocalizable)
  {
    String str1 = "TransferBackendRsltProcessor.doAuditLogCommon: ";
    String str2 = null;
    int i = 0;
    if (paramTransferInfo == null) {
      return;
    }
    try
    {
      str2 = paramTransferInfo.getAmount();
      if ((str2 == null) || (str2.trim().length() == 0)) {
        str2 = "-1";
      }
      if ((paramTransferInfo.getCustomerId() != null) && (paramTransferInfo.getCustomerId().length() > 0)) {
        if (paramTransferInfo.getCustomerId().equals(paramTransferInfo.getSubmittedBy())) {
          i = 0;
        } else {
          i = Integer.parseInt(paramTransferInfo.getCustomerId());
        }
      }
      String str3 = null;
      str4 = null;
      String str5 = null;
      String str6 = null;
      ExtTransferAcctInfo localExtTransferAcctInfo = new ExtTransferAcctInfo();
      String str7 = null;
      if ("ITOE".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
        str7 = paramTransferInfo.getAccountToId();
      } else if ("ETOI".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
        str7 = paramTransferInfo.getAccountFromId();
      }
      if (str7 != null)
      {
        localExtTransferAcctInfo.setAcctId(str7);
        localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo);
        if (localExtTransferAcctInfo.getStatusCode() == 0) {
          if ("ITOE".equals(paramTransferInfo.getTransferDest()))
          {
            str3 = localExtTransferAcctInfo.buildAcctId();
            str4 = localExtTransferAcctInfo.getAcctBankRtn();
          }
          else if ("ETOI".equals(paramTransferInfo.getTransferDest()))
          {
            str5 = localExtTransferAcctInfo.buildAcctId();
            str6 = localExtTransferAcctInfo.getAcctBankRtn();
          }
        }
      }
      if (str3 == null)
      {
        str3 = paramTransferInfo.buildToAcctId();
        str4 = paramTransferInfo.getBankToRtn();
      }
      if (str5 == null)
      {
        str5 = paramTransferInfo.buildFromAcctId();
        str6 = paramTransferInfo.getBankFromRtn();
      }
      AuditLogRecord localAuditLogRecord = new AuditLogRecord(paramTransferInfo.getSubmittedBy(), null, null, paramILocalizable, paramTransferInfo.getLogId(), 5257, i, new BigDecimal(str2), paramTransferInfo.getAmountCurrency(), paramTransferInfo.getSrvrTId(), paramTransferInfo.getPrcStatus(), str3, str4, str5, str6, -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (Exception localException)
    {
      localException = localException;
      String str4 = str1 + " failed " + localException.toString();
      FFSDebug.log(str4 + FFSDebug.stackTrace(localException), 0);
    }
    finally {}
  }
  
  private boolean a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, int paramInt)
  {
    String str = "TransferBackendRsltProcessor.revertLimit: ";
    FFSDebug.log(str + "start", 6);
    try
    {
      int i = LimitCheckApprovalProcessor.processExternalTransferReject(paramFFSConnectionHolder, paramTransferInfo, null);
      localObject = LimitCheckApprovalProcessor.mapToStatus(i);
      FFSDebug.log(str, "retStatus LimitCheck :" + (String)localObject, 6);
      if ("LIMIT_REVERT_FAILED".equals(localObject))
      {
        FFSDebug.log(str, "Limit Revert Failed", 6);
        paramTransferInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        paramTransferInfo.setStatusCode(17505);
        paramTransferInfo.setStatusMsg(BPWLocaleUtil.getMessage(17505, null, "TRANSFER_MESSAGE"));
        if (paramInt >= 1)
        {
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizableMessage(1047, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
          jdMethod_if(paramTransferInfo, localLocalizableString);
        }
        FFSDebug.log(str, "returning from here", 6);
        return false;
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject;
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 6);
      if (paramInt >= 1)
      {
        localObject = BPWLocaleUtil.getLocalizableMessage(1047, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        paramTransferInfo.setPrcStatus("LIMIT_REVERT_FAILED");
        jdMethod_if(paramTransferInfo, (ILocalizable)localObject);
      }
      return false;
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.TransferBackendRsltProcessor
 * JD-Core Version:    0.7.0.1
 */