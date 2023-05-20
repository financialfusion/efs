package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.ToBackend;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsUn;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.enums.UserAssignedAmount;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class XferHandler
  implements DBConsts, FFSConst, BPWResource
{
  private ArrayList hQ = null;
  private ToBackend hP = null;
  private PropertyConfig hR = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
  private int hO = 1;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== XferHandler.eventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length, 6);
    String str3;
    Object localObject1;
    if (paramInt == 0)
    {
      this.hQ = new ArrayList();
      String str1 = paramEventInfoArray._array[0].InstructionID;
      str3 = paramEventInfoArray._array[0].EventID;
      localObject1 = new IntraTrnInfo("", "", "", "", "", "", "", "", "", str1, "", str3, paramInt, false, null, null, "", paramEventInfoArray._array[0].FIId);
      this.hQ.add(localObject1);
    }
    else
    {
      if (paramInt == 1) {
        for (int i = 0; i < paramEventInfoArray._array.length; i++)
        {
          str3 = paramEventInfoArray._array[i].InstructionID;
          localObject1 = paramEventInfoArray._array[i].EventID;
          FFSDebug.log("=== XferHandler.eventHandler: eventSeq=" + paramInt + ",srvrTID=" + str3, 6);
          try
          {
            XferInstruction localXferInstruction = XferInstruction.getXferInstruction(paramFFSConnectionHolder, str3);
            if (localXferInstruction == null)
            {
              String str4 = "*** XferHandler.eventHandler failed: could not find the SrvrTID=" + str3 + " in BPW_XferInstruction";
              FFSDebug.console(str4);
              FFSDebug.log(str4);
            }
            else
            {
              boolean bool;
              if (localXferInstruction.Status.compareTo("WILLPROCESSON") == 0) {
                bool = false;
              } else {
                bool = true;
              }
              IntraTrnInfo localIntraTrnInfo = new IntraTrnInfo(localXferInstruction.CustomerID, localXferInstruction.BankID, localXferInstruction.BranchID, localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType, localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType, localXferInstruction.Amount, localXferInstruction.CurDef, localXferInstruction.ToAmount, localXferInstruction.ToAmtCurrency, localXferInstruction.userAssignedAmount, localXferInstruction.DateToPost, str3, localXferInstruction.LogID, (String)localObject1, paramInt, bool, null, localXferInstruction.RecSrvrTID, localXferInstruction.Status, localXferInstruction.FIID, localXferInstruction.fromBankID, localXferInstruction.fromBranchID);
              localIntraTrnInfo.submittedBy = localXferInstruction.SubmittedBy;
              if (localXferInstruction.extraFields != null) {
                localIntraTrnInfo.extraFields = ((HashMap)localXferInstruction.extraFields);
              }
              XferInstruction.updateStatus(paramFFSConnectionHolder, str3, "BATCH_INPROCESS");
              Object localObject5;
              Object localObject6;
              Object localObject7;
              if (this.hO >= 4)
              {
                localObject2 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
                localObject3 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
                localObject4 = new BigDecimal(localXferInstruction.Amount);
                localObject5 = localXferInstruction.ToAmount;
                if (localObject5 == null) {
                  localObject5 = "0.00";
                }
                localObject6 = new BigDecimal((String)localObject5);
                localObject7 = BPWLocaleUtil.getLocalizedMessage(924, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
                jdMethod_if(paramFFSConnectionHolder, localXferInstruction.SubmittedBy, Integer.parseInt(localXferInstruction.CustomerID), localXferInstruction.LogID, 4650, (BigDecimal)localObject4, localXferInstruction.CurDef, (BigDecimal)localObject6, localXferInstruction.ToAmtCurrency, localXferInstruction.userAssignedAmount, localXferInstruction.SrvrTID, (String)localObject2, (String)localObject3, localXferInstruction.BankID, localXferInstruction.fromBankID, "BATCH_INPROCESS", (ILocalizable)localObject7);
              }
              Object localObject2 = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
              if (localObject2 == null) {
                throw new FFSException("XferHandler.eventHandler:AuditAgent is null!!");
              }
              Object localObject3 = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
              if (localObject3 == null) {
                throw new FFSException("XferHandler.eventHandler:BPWMsgBroker is null!!");
              }
              Object localObject4 = SrvrTrans.findResponseBySrvrTID(str3, paramFFSConnectionHolder);
              if (localObject4[0] == null)
              {
                localObject5 = "*** XferHandler.eventHandler failed: could not find the SrvrTID=" + str3 + " in BPW_SrvrTrans";
                FFSDebug.console((String)localObject5);
                FFSDebug.log((String)localObject5);
              }
              else
              {
                if (localObject4[0].startsWith("OFX151"))
                {
                  localObject5 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)((BPWMsgBroker)localObject3).parseMsg(localObject4[1], "IntraTrnRsV1", "OFX151");
                  if (localObject5 != null)
                  {
                    localObject6 = new SimpleDateFormat("yyyyMMddHHmmss");
                    localObject7 = ((SimpleDateFormat)localObject6).format(new Date());
                    RsCmBuilder.updateXferRsDatePosted((String)localObject7, ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject5).IntraTrnRs.IntraTrnRsV1Un.IntraRs);
                    ((AuditAgent)localObject2).updateXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject5, paramFFSConnectionHolder, "BATCH_INPROCESS");
                  }
                }
                else if (localObject4[0].startsWith("OFX200"))
                {
                  localObject5 = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)((BPWMsgBroker)localObject3).parseMsg(localObject4[1], "IntraTrnRsV1", "OFX200");
                  if (localObject5 != null)
                  {
                    localObject6 = new SimpleDateFormat("yyyyMMddHHmmss");
                    localObject7 = ((SimpleDateFormat)localObject6).format(new Date());
                    RsCmBuilder.updateXferRsDatePosted((String)localObject7, ((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject5).IntraTrnRs.IntraTrnRsUn.IntraRs);
                    ((AuditAgent)localObject2).updateXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject5, paramFFSConnectionHolder, "BATCH_INPROCESS");
                  }
                }
                this.hQ.add(localIntraTrnInfo);
              }
            }
          }
          catch (Exception localException)
          {
            FFSDebug.log("*** XferHandler.eventHandler failed:" + localException.toString());
            throw new FFSException(localException.toString());
          }
        }
      }
      if (paramInt == 2)
      {
        String str2 = paramEventInfoArray._array[0].InstructionID;
        str3 = paramEventInfoArray._array[0].EventID;
        localObject1 = new IntraTrnInfo("", "", "", "", "", "", "", "", "", str2, "", str3, paramInt, false, null, null, "", paramEventInfoArray._array[0].FIId);
        this.hQ.add(localObject1);
        c(paramFFSConnectionHolder);
      }
      else if ((paramInt != 3) && (paramInt == 4))
      {
        c(paramFFSConnectionHolder);
      }
    }
    FFSDebug.log("=== XferHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("==== XferHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length);
    String str3;
    Object localObject1;
    if (paramInt == 0)
    {
      this.hQ = new ArrayList();
      String str1 = paramEventInfoArray._array[0].InstructionID;
      str3 = paramEventInfoArray._array[0].EventID;
      localObject1 = new IntraTrnInfo("", "", "", "", "", "", "", "", "", str1, paramEventInfoArray._array[0].LogID, str3, paramInt, true, null, null, "", paramEventInfoArray._array[0].FIId);
      this.hQ.add(localObject1);
    }
    else
    {
      if (paramInt == 1) {
        for (int i = 0; i < paramEventInfoArray._array.length; i++)
        {
          str3 = paramEventInfoArray._array[i].InstructionID;
          localObject1 = paramEventInfoArray._array[i].EventID;
          FFSDebug.log("==== XferHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",srvrTID=" + str3);
          try
          {
            XferInstruction localXferInstruction = XferInstruction.getXferInstruction(paramFFSConnectionHolder, str3);
            Object localObject2;
            if (localXferInstruction == null)
            {
              localObject2 = "*** XferHandler.resubmitEventHandler failed: could not find the SrvrTID=" + str3 + " in BPW_XferInstruction";
              FFSDebug.console((String)localObject2);
              FFSDebug.log((String)localObject2);
            }
            else
            {
              localObject2 = new IntraTrnInfo(localXferInstruction.CustomerID, localXferInstruction.BankID, localXferInstruction.BranchID, localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType, localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType, localXferInstruction.Amount, localXferInstruction.CurDef, localXferInstruction.ToAmount, localXferInstruction.ToAmtCurrency, localXferInstruction.userAssignedAmount, localXferInstruction.DateToPost, str3, localXferInstruction.LogID, (String)localObject1, paramInt, true, null, localXferInstruction.RecSrvrTID, localXferInstruction.Status, localXferInstruction.FIID, localXferInstruction.fromBankID, localXferInstruction.fromBranchID);
              ((IntraTrnInfo)localObject2).submittedBy = localXferInstruction.SubmittedBy;
              if (localXferInstruction.extraFields != null) {
                ((IntraTrnInfo)localObject2).extraFields = ((HashMap)localXferInstruction.extraFields);
              }
              XferInstruction.updateStatus(paramFFSConnectionHolder, str3, "BATCH_INPROCESS");
              this.hQ.add(localObject2);
              if (this.hO >= 4)
              {
                String str4 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
                String str5 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
                BigDecimal localBigDecimal1 = new BigDecimal(localXferInstruction.Amount);
                String str6 = localXferInstruction.ToAmount;
                if (str6 == null) {
                  str6 = "0.00";
                }
                BigDecimal localBigDecimal2 = new BigDecimal(str6);
                LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(924, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
                jdMethod_if(paramFFSConnectionHolder, localXferInstruction.SubmittedBy, Integer.parseInt(localXferInstruction.CustomerID), localXferInstruction.LogID, 4650, localBigDecimal1, localXferInstruction.CurDef, localBigDecimal2, localXferInstruction.ToAmtCurrency, localXferInstruction.userAssignedAmount, localXferInstruction.SrvrTID, str4, str5, localXferInstruction.BankID, localXferInstruction.fromBankID, "BATCH_INPROCESS", localLocalizableString);
              }
            }
          }
          catch (Exception localException)
          {
            throw new FFSException(localException.getMessage());
          }
        }
      }
      if (paramInt == 2)
      {
        String str2 = paramEventInfoArray._array[0].InstructionID;
        str3 = paramEventInfoArray._array[0].EventID;
        localObject1 = new IntraTrnInfo("", "", "", "", "", "", "", "", "", str2, "", str3, paramInt, true, null, null, "", paramEventInfoArray._array[0].FIId);
        this.hQ.add(localObject1);
        c(paramFFSConnectionHolder);
      }
      else if ((paramInt != 3) && (paramInt == 4))
      {
        c(paramFFSConnectionHolder);
      }
    }
    FFSDebug.log("==== XferHandler.resubmitEventHandler: end");
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, int paramInt1, String paramString2, int paramInt2, BigDecimal paramBigDecimal1, String paramString3, BigDecimal paramBigDecimal2, String paramString4, UserAssignedAmount paramUserAssignedAmount, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, ILocalizable paramILocalizable)
    throws FFSException
  {
    TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), paramString1, null, null, paramILocalizable, paramString2, paramInt2, paramInt1, paramBigDecimal1, paramString3, paramBigDecimal2, paramString4, paramUserAssignedAmount, paramString5, paramString10, paramString7, paramString8, paramString6, paramString9, 0);
  }
  
  private int c(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str = DBConnCache.getNewBatchKey();
    DBConnCache.bind(str, paramFFSConnectionHolder);
    IntraTrnInfo[] arrayOfIntraTrnInfo = (IntraTrnInfo[])this.hQ.toArray(new IntraTrnInfo[0]);
    for (int i = 0; i < arrayOfIntraTrnInfo.length; i++) {
      arrayOfIntraTrnInfo[i].batchKey = str;
    }
    i = this.hP.ProcessIntraTrn(arrayOfIntraTrnInfo);
    DBConnCache.unbind(str);
    this.hQ.clear();
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.XferHandler
 * JD-Core Version:    0.7.0.1
 */