package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.RecPmtInstruction;
import com.ffusion.ffs.bpw.db.RecSrvrTrans;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.db.TempHist;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWExtdHist;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.ScheduleConstants;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumPmtProcessStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRqV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTokenRqV1Un;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class PmtSyncProcessor
  implements DBConsts, OFXConsts, BPWResource, ScheduleConstants
{
  private PmtProcessor m = null;
  PagedBillPay l = null;
  
  public PmtSyncProcessor(PmtProcessor paramPmtProcessor)
  {
    this.m = paramPmtProcessor;
    this.l = new PagedBillPay();
  }
  
  public TypePmtSyncRsV1 processPmtSyncRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    TypePmtSyncRsV1 localTypePmtSyncRsV1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    String str1 = "";
    try
    {
      String str2 = paramTypePmtSyncRqV1.PmtSyncRq.SyncRqV1Cm.TokenRqV1Un.__memberName;
      if (str2.equals("Refresh"))
      {
        localTypePmtSyncRsV1 = processPmtRefreshRqV1(paramTypePmtSyncRqV1, paramTypeUserData, str1);
      }
      else if (str2.equals("Token"))
      {
        localTypePmtSyncRsV1 = processPmtTokenRqV1(paramTypePmtSyncRqV1, paramTypeUserData, str1);
      }
      else
      {
        if (str2.equals("TokenOnly")) {
          throw new OFXException("Token only requests not supported", 2000);
        }
        throw new OFXException("Unsupported request type", 2000);
      }
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log("=== User Error, OFXException:" + localOFXException.getExceptionMsg());
      localTypePmtSyncRsV1 = a(paramTypePmtSyncRqV1);
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = localException.toString();
      FFSDebug.log("*** PmtSyncProcessor.processPmtSyncRqV1 failed:" + str3);
      localTypePmtSyncRsV1 = a(paramTypePmtSyncRqV1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypePmtSyncRsV1;
  }
  
  private TypePmtSyncRsV1 a(TypePmtSyncRqV1 paramTypePmtSyncRqV1)
  {
    TypePmtSyncRsV1 localTypePmtSyncRsV1 = new TypePmtSyncRsV1();
    localTypePmtSyncRsV1.PmtSyncRs = new TypePmtSyncRsV1Aggregate();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsV1Cm.Token = "-1";
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom = paramTypePmtSyncRqV1.PmtSyncRq.BankAcctFrom;
    return localTypePmtSyncRsV1;
  }
  
  public TypePmtSyncRsV1 processPmtRefreshRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    TypePmtSyncRsV1 localTypePmtSyncRsV1 = new TypePmtSyncRsV1();
    localTypePmtSyncRsV1.PmtSyncRs = new TypePmtSyncRsV1Aggregate();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsV1Cm.Token = "0";
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom = paramTypePmtSyncRqV1.PmtSyncRq.BankAcctFrom;
    try
    {
      AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      if (localAuditAgent == null) {
        throw new BPWException("AuditAgent is null!!");
      }
      String str1 = paramTypePmtSyncRqV1.PmtSyncRq.BankAcctFrom.AcctID;
      String str2 = MsgBuilder.getAcctType(paramTypePmtSyncRqV1.PmtSyncRq.BankAcctFrom.AcctType);
      String[] arrayOfString = localAuditAgent.getSrvrTrans(paramTypeUserData._uid, str1, str2, "OFX151PmtSync");
      int i = arrayOfString.length;
      TypePmtTrnRsV1[] arrayOfTypePmtTrnRsV1 = new TypePmtTrnRsV1[i];
      for (int j = 0; j < i; j++)
      {
        String str3 = (String)arrayOfString[j];
        BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
        arrayOfTypePmtTrnRsV1[j] = ((TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str3, "PmtTrnRsV1", "OFX151"));
      }
      if (paramTypePmtSyncRqV1.PmtSyncRq.PmtTrnRq == null)
      {
        localTypePmtSyncRsV1.PmtSyncRs.PmtTrnRs = new TypePmtTrnRsV1Aggregate[i];
        for (j = 0; j < i; j++) {
          localTypePmtSyncRsV1.PmtSyncRs.PmtTrnRs[j] = arrayOfTypePmtTrnRsV1[j].PmtTrnRs;
        }
      }
      j = paramTypePmtSyncRqV1.PmtSyncRq.PmtTrnRq.length;
      int k = j + i;
      localTypePmtSyncRsV1.PmtSyncRs.PmtTrnRs = new TypePmtTrnRsV1Aggregate[k];
      for (int n = 0; n < j; n++)
      {
        TypePmtTrnRqV1 localTypePmtTrnRqV1 = new TypePmtTrnRqV1(paramTypePmtSyncRqV1.PmtSyncRq.PmtTrnRq[n]);
        localTypePmtSyncRsV1.PmtSyncRs.PmtTrnRs[n] = this.m.processPmtTrnRqV1(localTypePmtTrnRqV1, paramTypeUserData, paramString).PmtTrnRs;
      }
      for (n = j; n < j + i; n++) {
        localTypePmtSyncRsV1.PmtSyncRs.PmtTrnRs[n] = arrayOfTypePmtTrnRsV1[(n - j)].PmtTrnRs;
      }
    }
    catch (Exception localException)
    {
      throw new Exception(localException.toString());
    }
    return localTypePmtSyncRsV1;
  }
  
  public TypePmtSyncRsV1 processPendingPmtRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
    throws Exception
  {
    TypePmtSyncRsV1 localTypePmtSyncRsV1 = null;
    FFSDebug.log("PmtSyncProcessor.processPendingPmtRqV1: begin", 6);
    String str1 = paramTypePmtSyncRqV1.PmtSyncRq.BankAcctFrom.AcctID;
    String str2 = MsgBuilder.getAcctType(paramTypePmtSyncRqV1.PmtSyncRq.BankAcctFrom.AcctType);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localTypePmtSyncRsV1 = a(paramTypeUserData._uid, str1, str2, paramInt, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      String str3 = "PmtSyncProcessor.processPendingPmtRqV1 failed : ";
      throw new Exception(str3 + localException.getMessage());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypePmtSyncRsV1;
  }
  
  private TypePmtSyncRsV1 a(String paramString1, String paramString2, String paramString3, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("PmtSyncProcessor.processPendingPmtRqV1: acctID=" + paramString2 + ",uid=" + paramString1, 6);
    TypePmtSyncRsV1 localTypePmtSyncRsV1 = new TypePmtSyncRsV1();
    localTypePmtSyncRsV1.PmtSyncRs = new TypePmtSyncRsV1Aggregate();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsV1Cm.Token = "0";
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom = new TypeBankAcctFromV1Aggregate();
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom.AcctID = paramString2;
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom.AcctType = MsgBuilder.getOFX151AcctEnum(paramString3);
    Vector localVector = new Vector();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    int i = ScheduleInfo.getInstanceDate(paramInt);
    String[] arrayOfString1 = localAuditAgent.getPendingSrvrTrans(paramString1, paramString2, paramString3, "OFX151PmtSync");
    int j = arrayOfString1.length;
    FFSDebug.log("PmtSyncProcessor.processPendingPmtRqV1: getPendingSrvrTrans=" + j, 6);
    Object localObject;
    for (int k = 0; k < j; k++)
    {
      String str1 = (String)arrayOfString1[k];
      TypePmtTrnRsV1 localTypePmtTrnRsV1 = (TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str1, "PmtTrnRsV1", "OFX151");
      localObject = localSimpleDateFormat.parse(localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
      int i1 = ScheduleInfo.convertInstanceDateToNum((Date)localObject);
      FFSDebug.log("PmtSyncProcessor.processPendingPmtRqV1: dtDue=" + i1, 6);
      if (i1 <= i) {
        localVector.addElement(localTypePmtTrnRsV1.PmtTrnRs);
      }
    }
    arrayOfString1 = localAuditAgent.getPendingRecSrvrTrans(paramString1, paramString2, paramString3, "OFX151RecPmtSync");
    j = arrayOfString1.length;
    FFSDebug.log("PmtSyncProcessor.processPendingPmtRqV1: getPendingRecSrvrTrans=" + j, 6);
    for (int n = 0; n < j; n++)
    {
      localObject = (String)arrayOfString1[n];
      TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = (TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg((String)localObject, "RecPmtTrnRsV1", "OFX151");
      String str2 = "1000";
      RecPmtInstruction localRecPmtInstruction = RecPmtInstruction.getRecPmtInstr(localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecSrvrTID, paramFFSConnectionHolder);
      str2 = localRecPmtInstruction.getFIID();
      ScheduleInfo localScheduleInfo = new ScheduleInfo();
      Date localDate = localSimpleDateFormat.parse(localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
      localScheduleInfo.StartDate = ScheduleInfo.convertInstanceDateToNum(localDate);
      localScheduleInfo.NextInstanceDate = localScheduleInfo.StartDate;
      localScheduleInfo.Frequency = CommonProcessor.mapOFX151FreqToBPWFreq(localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.Freq.value());
      localScheduleInfo.InstanceCount = localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInsts;
      localScheduleInfo.CurInstanceNum = 1;
      if (!localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInstsExists) {
        localScheduleInfo.Perpetual = 1;
      }
      String[] arrayOfString2 = ScheduleInfo.getPendingDates(localScheduleInfo, paramInt);
      localScheduleInfo.CurInstanceNum = localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInsts;
      ScheduleInfo.computeNextInstanceDate(localScheduleInfo);
      String str3 = Integer.toString(localScheduleInfo.NextInstanceDate) + "0000";
      for (int i2 = 0; i2 < arrayOfString2.length; i2++)
      {
        TypeRecPmtRsV1Aggregate localTypeRecPmtRsV1Aggregate = localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs;
        TypePmtTrnRsV1Aggregate localTypePmtTrnRsV1Aggregate = new TypePmtTrnRsV1Aggregate();
        localTypePmtTrnRsV1Aggregate.TrnRsV1Cm = localTypeRecPmtTrnRsV1.RecPmtTrnRs.TrnRsV1Cm;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists = true;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un = new TypePmtTrnRsV1Un();
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__preValueTag = "PMTRS";
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName = "PmtRs";
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs = new TypePmtRsV1Aggregate();
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.SrvrTID = "0";
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PayeeLstID = localTypeRecPmtRsV1Aggregate.PayeeLstID;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CurDef = localTypeRecPmtRsV1Aggregate.CurDef;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee = localTypeRecPmtRsV1Aggregate.ExtdPayee;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = localTypeRecPmtRsV1Aggregate.ExtdPayeeExists;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CheckNumExists = false;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts = new TypePmtPrcStsAggregate();
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = EnumPmtProcessStatusEnum.WILLPROCESSON;
        int i3 = Integer.parseInt(arrayOfString2[i2].substring(0, 8));
        int i4 = SmartCalendar.getPayday(str2, i3) * 100;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = ("" + i4 + "0000");
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTID = localTypeRecPmtRsV1Aggregate.RecSrvrTID;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTIDExists = true;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo = new TypePmtInfoV1Aggregate();
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctFrom = localTypeRecPmtRsV1Aggregate.PmtInfo.BankAcctFrom;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.TrnAmt = localTypeRecPmtRsV1Aggregate.PmtInfo.TrnAmt;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeUn = localTypeRecPmtRsV1Aggregate.PmtInfo.PayeeUn;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstID = localTypeRecPmtRsV1Aggregate.PmtInfo.PayeeLstID;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstIDExists = localTypeRecPmtRsV1Aggregate.PmtInfo.PayeeLstIDExists;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctTo = localTypeRecPmtRsV1Aggregate.PmtInfo.BankAcctTo;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctToExists = localTypeRecPmtRsV1Aggregate.PmtInfo.BankAcctToExists;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.ExtdPmt = localTypeRecPmtRsV1Aggregate.PmtInfo.ExtdPmt;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayAcct = localTypeRecPmtRsV1Aggregate.PmtInfo.PayAcct;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue = localTypeRecPmtRsV1Aggregate.PmtInfo.DtDue;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.Memo = localTypeRecPmtRsV1Aggregate.PmtInfo.Memo;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.MemoExists = localTypeRecPmtRsV1Aggregate.PmtInfo.MemoExists;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BillRefInfo = localTypeRecPmtRsV1Aggregate.PmtInfo.BillRefInfo;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BillRefInfoExists = localTypeRecPmtRsV1Aggregate.PmtInfo.BillRefInfoExists;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue = arrayOfString2[i2];
        if ((localTypeRecPmtRsV1Aggregate.FinalAmtExists) && (str3.equals(arrayOfString2[i2]))) {
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.TrnAmt = localTypeRecPmtRsV1Aggregate.FinalAmt;
        }
        localVector.addElement(localTypePmtTrnRsV1Aggregate);
      }
    }
    localTypePmtSyncRsV1.PmtSyncRs.PmtTrnRs = ((TypePmtTrnRsV1Aggregate[])localVector.toArray(new TypePmtTrnRsV1Aggregate[0]));
    return localTypePmtSyncRsV1;
  }
  
  public TypePmtSyncRsV1 processPmtTokenRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    return processPmtRefreshRqV1(paramTypePmtSyncRqV1, paramTypeUserData, paramString);
  }
  
  public TypePmtSyncRsV1[] getPendingPmtsByCustomerID(String paramString, int paramInt)
    throws Exception
  {
    FFSDebug.log("PmtSyncProcessor.getPendingPmtsByCustomerID: custId=" + paramString + ",pendingDay=" + paramInt, 6);
    TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV1 = null;
    ArrayList localArrayList = new ArrayList();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      SrvrTrans[] arrayOfSrvrTrans = SrvrTrans.getAcctByCustomerID(paramString, "OFX151PmtSync", localFFSConnectionHolder);
      for (int i = 0; i < arrayOfSrvrTrans.length; i++)
      {
        String str2 = arrayOfSrvrTrans[i].getAcctID();
        String str3 = arrayOfSrvrTrans[i].getAcctType();
        TypePmtSyncRsV1 localTypePmtSyncRsV1 = a(paramString, str2, str3, paramInt, localFFSConnectionHolder);
        localArrayList.add(localTypePmtSyncRsV1);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      String str1 = "PmtSyncProcessor.getPendingPmtsByCustomerID failed : ";
      throw new Exception(str1 + localException.getMessage());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    arrayOfTypePmtSyncRsV1 = (TypePmtSyncRsV1[])localArrayList.toArray(new TypePmtSyncRsV1[0]);
    return arrayOfTypePmtSyncRsV1;
  }
  
  public TypePmtSyncRsV1[] getPendingPmtsAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
    throws Exception
  {
    FFSDebug.log("PmtSyncProcessor.getPendingPmtsAndHistoryByCustomerID: custId=" + paramString + ",pendingDay=" + paramInt1 + ",pastDay=" + paramInt2, 6);
    TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV1 = null;
    ArrayList localArrayList = new ArrayList();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      SrvrTrans[] arrayOfSrvrTrans = SrvrTrans.getAcctByCustomerID(paramString, "OFX151PmtSync", localFFSConnectionHolder);
      for (int i = 0; i < arrayOfSrvrTrans.length; i++)
      {
        String str2 = arrayOfSrvrTrans[i].getAcctID();
        String str3 = arrayOfSrvrTrans[i].getAcctType();
        TypePmtSyncRsV1 localTypePmtSyncRsV1 = a(paramString, str2, str3, paramInt1, paramInt2, localFFSConnectionHolder);
        localArrayList.add(localTypePmtSyncRsV1);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      String str1 = "PmtSyncProcessor.getPendingPmtsAndHistoryByCustomerID failed : ";
      throw new Exception(str1 + localException.getMessage());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    arrayOfTypePmtSyncRsV1 = (TypePmtSyncRsV1[])localArrayList.toArray(new TypePmtSyncRsV1[0]);
    return arrayOfTypePmtSyncRsV1;
  }
  
  private TypePmtSyncRsV1 a(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("PmtSyncProcessor.processPendingPmtAndHistoryRqV1: begin", 6);
    TypePmtSyncRsV1 localTypePmtSyncRsV1 = new TypePmtSyncRsV1();
    localTypePmtSyncRsV1.PmtSyncRs = new TypePmtSyncRsV1Aggregate();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsV1Cm.Token = "0";
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom = new TypeBankAcctFromV1Aggregate();
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom.AcctID = paramString2;
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom.AcctType = MsgBuilder.getOFX151AcctEnum(paramString3);
    Vector localVector = new Vector();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    int i = ScheduleInfo.getInstanceDate(-paramInt2);
    int j = ScheduleInfo.getInstanceDate(paramInt1);
    if (i > j) {
      throw new Exception("pastDay should not be ahead of the pendingDay!");
    }
    String[] arrayOfString1 = localAuditAgent.getSrvrTrans(paramString1, paramString2, paramString3, "OFX151PmtSync");
    int k = arrayOfString1.length;
    FFSDebug.log("PmtSyncProcessor.processPendingPmtAndHistoryRqV1: getSrvrTrans=" + k, 6);
    Object localObject;
    for (int n = 0; n < k; n++)
    {
      String str1 = (String)arrayOfString1[n];
      TypePmtTrnRsV1 localTypePmtTrnRsV1 = (TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str1, "PmtTrnRsV1", "OFX151");
      localObject = localSimpleDateFormat.parse(localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
      int i2 = ScheduleInfo.convertInstanceDateToNum((Date)localObject);
      FFSDebug.log("PmtSyncProcessor.processPendingPmtAndHistoryRqV1: dtDue=" + i2 + ",dtPending=" + j + ",dtPast=" + i, 6);
      if ((i2 <= j) && (i2 >= i)) {
        localVector.addElement(localTypePmtTrnRsV1.PmtTrnRs);
      }
    }
    if (paramInt1 > 0)
    {
      arrayOfString1 = localAuditAgent.getPendingRecSrvrTrans(paramString1, paramString2, paramString3, "OFX151RecPmtSync");
      k = arrayOfString1.length;
      FFSDebug.log("PmtSyncProcessor.processPendingPmtAndHistoryRqV1: getPendingRecSrvrTrans=" + k, 6);
      for (int i1 = 0; i1 < k; i1++)
      {
        localObject = (String)arrayOfString1[i1];
        TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = (TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg((String)localObject, "RecPmtTrnRsV1", "OFX151");
        String str2 = "1000";
        RecPmtInstruction localRecPmtInstruction = RecPmtInstruction.getRecPmtInstr(localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecSrvrTID, paramFFSConnectionHolder);
        str2 = localRecPmtInstruction.getFIID();
        ScheduleInfo localScheduleInfo = new ScheduleInfo();
        Date localDate = localSimpleDateFormat.parse(localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
        localScheduleInfo.StartDate = ScheduleInfo.convertInstanceDateToNum(localDate);
        localScheduleInfo.NextInstanceDate = localScheduleInfo.StartDate;
        localScheduleInfo.Frequency = CommonProcessor.mapOFX151FreqToBPWFreq(localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.Freq.value());
        localScheduleInfo.InstanceCount = localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInsts;
        localScheduleInfo.CurInstanceNum = 1;
        if (!localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInstsExists) {
          localScheduleInfo.Perpetual = 1;
        }
        String[] arrayOfString2 = ScheduleInfo.getPendingDates(localScheduleInfo, paramInt1);
        localScheduleInfo.CurInstanceNum = localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst.NInsts;
        ScheduleInfo.computeNextInstanceDate(localScheduleInfo);
        String str3 = Integer.toString(localScheduleInfo.NextInstanceDate) + "0000";
        for (int i3 = 0; i3 < arrayOfString2.length; i3++)
        {
          TypeRecPmtRsV1Aggregate localTypeRecPmtRsV1Aggregate = localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsV1Un.RecPmtRs;
          TypePmtTrnRsV1Aggregate localTypePmtTrnRsV1Aggregate = new TypePmtTrnRsV1Aggregate();
          localTypePmtTrnRsV1Aggregate.TrnRsV1Cm = localTypeRecPmtTrnRsV1.RecPmtTrnRs.TrnRsV1Cm;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists = true;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un = new TypePmtTrnRsV1Un();
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__preValueTag = "PMTRS";
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName = "PmtRs";
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs = new TypePmtRsV1Aggregate();
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.SrvrTID = "0";
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PayeeLstID = localTypeRecPmtRsV1Aggregate.PayeeLstID;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CurDef = localTypeRecPmtRsV1Aggregate.CurDef;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee = localTypeRecPmtRsV1Aggregate.ExtdPayee;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = localTypeRecPmtRsV1Aggregate.ExtdPayeeExists;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CheckNumExists = false;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts = new TypePmtPrcStsAggregate();
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = EnumPmtProcessStatusEnum.WILLPROCESSON;
          int i4 = Integer.parseInt(arrayOfString2[i3].substring(0, 8));
          int i5 = SmartCalendar.getPayday(str2, i4) * 100;
          if (i5 >= i)
          {
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = ("" + i5 + "0000");
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTID = localTypeRecPmtRsV1Aggregate.RecSrvrTID;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTIDExists = true;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo = new TypePmtInfoV1Aggregate();
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctFrom = localTypeRecPmtRsV1Aggregate.PmtInfo.BankAcctFrom;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.TrnAmt = localTypeRecPmtRsV1Aggregate.PmtInfo.TrnAmt;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeUn = localTypeRecPmtRsV1Aggregate.PmtInfo.PayeeUn;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstID = localTypeRecPmtRsV1Aggregate.PmtInfo.PayeeLstID;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstIDExists = localTypeRecPmtRsV1Aggregate.PmtInfo.PayeeLstIDExists;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctTo = localTypeRecPmtRsV1Aggregate.PmtInfo.BankAcctTo;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctToExists = localTypeRecPmtRsV1Aggregate.PmtInfo.BankAcctToExists;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.ExtdPmt = localTypeRecPmtRsV1Aggregate.PmtInfo.ExtdPmt;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayAcct = localTypeRecPmtRsV1Aggregate.PmtInfo.PayAcct;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue = localTypeRecPmtRsV1Aggregate.PmtInfo.DtDue;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.Memo = localTypeRecPmtRsV1Aggregate.PmtInfo.Memo;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.MemoExists = localTypeRecPmtRsV1Aggregate.PmtInfo.MemoExists;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BillRefInfo = localTypeRecPmtRsV1Aggregate.PmtInfo.BillRefInfo;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BillRefInfoExists = localTypeRecPmtRsV1Aggregate.PmtInfo.BillRefInfoExists;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue = arrayOfString2[i3];
            if ((localTypeRecPmtRsV1Aggregate.FinalAmtExists) && (str3.equals(arrayOfString2[i3]))) {
              localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.TrnAmt = localTypeRecPmtRsV1Aggregate.FinalAmt;
            }
            localVector.addElement(localTypePmtTrnRsV1Aggregate);
          }
        }
      }
    }
    localTypePmtSyncRsV1.PmtSyncRs.PmtTrnRs = ((TypePmtTrnRsV1Aggregate[])localVector.toArray(new TypePmtTrnRsV1Aggregate[0]));
    return localTypePmtSyncRsV1;
  }
  
  public BPWHist getPmtHistory(BPWHist paramBPWHist)
    throws Exception
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    long l1 = 0L;
    if (paramBPWHist == null) {
      throw new Exception("BPWHist object is Null");
    }
    str1 = paramBPWHist.getCustId();
    str2 = paramBPWHist.getAcctId();
    str3 = paramBPWHist.getStartDate();
    if ((str3 != null) && (str3.trim().length() > 7)) {
      str3 = str3.substring(0, 8);
    } else {
      str3 = null;
    }
    str4 = paramBPWHist.getEndDate();
    if ((str4 != null) && (str4.trim().length() > 7)) {
      str4 = str4.substring(0, 8);
    } else {
      str4 = null;
    }
    str5 = paramBPWHist.getHistId();
    str6 = paramBPWHist.getCursorId();
    l1 = paramBPWHist.getPageSize();
    FFSDebug.log("PmtSyncProcessor.getPmtHistory: start custId=" + str1 + ",histId=" + str5, 6);
    if ((str3 != null) && (str4 != null) && (Integer.parseInt(str3) > Integer.parseInt(str4)))
    {
      paramBPWHist.setStatusCode(17020);
      paramBPWHist.setStatusMsg("Start date can not be after end date");
      return paramBPWHist;
    }
    if (str6 == null) {
      str6 = "0";
    }
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    int i = localPropertyConfig.getBatchSize();
    if (l1 > i)
    {
      paramBPWHist.setStatusCode(17030);
      paramBPWHist.setStatusMsg("Paging size bigger than system batch size");
      return paramBPWHist;
    }
    if ((str5 != null) && (str5.trim().equals(""))) {
      str5 = null;
    }
    if ((str5 == null) || (str5.equals("")) || (str5.equals("0")))
    {
      str5 = a(str1, str2, str3, str4, l1, paramBPWHist);
      str6 = "0";
      a(str5, str6, l1, paramBPWHist);
    }
    else
    {
      a(str5, str6, l1, paramBPWHist);
    }
    if (paramBPWHist.getTrans().length > 0)
    {
      paramBPWHist.setStatusCode(0);
      paramBPWHist.setStatusMsg("Success");
    }
    else
    {
      paramBPWHist.setStatusCode(17020);
      paramBPWHist.setStatusMsg("No Records Found");
    }
    FFSDebug.log("PmtSyncProcessor.getPmtHistory: done, custId=" + str1 + ",histId=" + str5, 6);
    return paramBPWHist;
  }
  
  private String a(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, BPWHist paramBPWHist)
    throws Exception
  {
    FFSDebug.log("PmtSyncProcessor.makeHistory: start custId=" + paramString1 + ",acctId=" + paramString2 + ",startDate=" + paramString3 + ",endDate=" + paramString4, 6);
    if ((paramString1 == null) || (paramString1.trim().length() == 0)) {
      throw new BPWException("Customer Id is null in BPWHist object");
    }
    String str1 = null;
    int i;
    if (paramString3 == null) {
      i = 0;
    } else {
      i = Integer.parseInt(paramString3 + "00");
    }
    int j;
    if (paramString4 == null)
    {
      int k = DBUtil.getCurrentStartDate();
      j = k + 1000001;
    }
    else
    {
      j = Integer.parseInt(paramString4 + "01");
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    int n = 0;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i1 = localPropertyConfig.getBatchSize();
      str1 = DBUtil.getNextIndexString("HistID");
      PmtInfo[] arrayOfPmtInfo = null;
      if ((paramBPWHist instanceof BPWExtdHist))
      {
        paramBPWHist.setHistId(str1);
        arrayOfPmtInfo = PmtInstruction.getHist(localFFSConnectionHolder, (BPWExtdHist)paramBPWHist, i, j);
      }
      else
      {
        arrayOfPmtInfo = PmtInstruction.getHist(localFFSConnectionHolder, paramString1, paramString2, i, j, paramBPWHist.getRequiredStatus(), str1, paramLong);
      }
      int i2 = arrayOfPmtInfo.length;
      Object localObject1;
      String str7;
      while (i2 != 0)
      {
        String str3 = "PMT";
        for (i4 = 0; i4 < arrayOfPmtInfo.length; i4++)
        {
          localObject1 = arrayOfPmtInfo[i4];
          String str4 = ((PmtInfo)localObject1).SrvrTID;
          int i6 = ((PmtInfo)localObject1).StartDate;
          n++;
          if (n % i1 == 0) {
            localFFSConnectionHolder.conn.commit();
          }
          String str6 = DBUtil.getCursor(i6, n);
          str7 = ((PmtInfo)localObject1).getAmt();
          TempHist.create(localFFSConnectionHolder, str1, str6, str4, str3, i6, str7);
        }
        if (PmtInstruction.isBatchDone(str1))
        {
          i2 = 0;
        }
        else
        {
          arrayOfPmtInfo = PmtInstruction.getHist(localFFSConnectionHolder, paramString1, paramString2, i, j, paramBPWHist.getRequiredStatus(), str1, paramLong);
          i2 = arrayOfPmtInfo.length;
        }
      }
      int i3 = DBUtil.getCurrentStartDate();
      int i4 = (j - i3) / 100;
      if (i4 >= 0)
      {
        localObject1 = RecPmtInstruction.getHist(localFFSConnectionHolder, paramString1, paramString2, i, j, paramBPWHist.getRequiredStatus(), str1, paramLong);
        int i5 = localObject1.length;
        while (i5 != 0)
        {
          String str5 = "RECPMT";
          for (int i7 = 0; i7 < localObject1.length; i7++)
          {
            str7 = localObject1[i7];
            String str8 = str7.RecSrvrTID;
            ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(str7.FIID, "RECPMTTRN", str7.RecSrvrTID, localFFSConnectionHolder);
            if (localScheduleInfo != null)
            {
              localScheduleInfo.InstanceCount -= localScheduleInfo.CurInstanceNum - 1;
              localScheduleInfo.CurInstanceNum = 1;
              String[] arrayOfString = ScheduleInfo.getPendingDatesByStartAndEndDate(localScheduleInfo, i, j);
              localScheduleInfo.CurInstanceNum = localScheduleInfo.InstanceCount;
              ScheduleInfo.computeNextInstanceDate(localScheduleInfo);
              String str9 = Integer.toString(localScheduleInfo.NextInstanceDate) + "0000";
              for (int i8 = 0; i8 < arrayOfString.length; i8++)
              {
                int i9 = Integer.parseInt(arrayOfString[i8].substring(0, 10));
                n++;
                if (n % i1 == 0) {
                  localFFSConnectionHolder.conn.commit();
                }
                String str10 = DBUtil.getCursor(i9, n);
                String str11 = str7.getAmt();
                if (str9.equals(arrayOfString[i8])) {
                  str11 = str7.getFinalAmount();
                }
                TempHist.create(localFFSConnectionHolder, str1, str10, str8, str5, i9, str11);
              }
            }
          }
          if (RecPmtInstruction.isBatchDone(str1))
          {
            i5 = 0;
          }
          else
          {
            localObject1 = RecPmtInstruction.getHist(localFFSConnectionHolder, paramString1, paramString2, i, j, paramBPWHist.getRequiredStatus(), str1, paramLong);
            i5 = localObject1.length;
          }
        }
      }
      paramBPWHist.setHistId(str1);
      paramBPWHist.setTotalTrans(n);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      str2 = FFSDebug.stackTrace(localException);
      FFSDebug.log("*** PmtSyncProcessor.makeHistory failed:" + str2);
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("*** PmtSyncProcessor.makeHistory failed:" + str2);
    }
    finally
    {
      PmtInstruction.clearBatch(str1);
      RecPmtInstruction.clearBatch(str1);
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("PmtSyncProcessor.makeHistory: done custId=" + paramString1 + ",acctId=" + paramString2 + ",startDate=" + paramString3 + ",endDate=" + paramString4, 6);
    return str1;
  }
  
  private void a(String paramString1, String paramString2, long paramLong, BPWHist paramBPWHist)
    throws Exception
  {
    FFSDebug.log("PmtSyncProcessor.getHistory: start histId=" + paramString1 + ",cursorId=" + paramString2 + ",pageSize=" + paramLong, 6);
    ArrayList localArrayList = new ArrayList();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      TempHist[] arrayOfTempHist = TempHist.get(localFFSConnectionHolder, paramString1, paramString2, paramLong);
      for (int i = 0; i < arrayOfTempHist.length; i++)
      {
        TempHist localTempHist = arrayOfTempHist[i];
        String str2;
        Object localObject1;
        Object localObject2;
        if (localTempHist.RecordType.equals("PMT"))
        {
          str2 = localTempHist.RecordExtId;
          localObject1 = PmtInstruction.getPmtInstr(str2, localFFSConnectionHolder);
          localObject2 = ((PmtInstruction)localObject1).getPmtInfo();
          ((PmtInfo)localObject2).payeeInfo = Payee.findPayeeByID(((PmtInstruction)localObject1).getPayeeID(), localFFSConnectionHolder);
          localArrayList.add(localObject2);
        }
        else if (localTempHist.RecordType.equals("RECPMT"))
        {
          str2 = localTempHist.RecordExtId;
          localObject1 = RecPmtInstruction.getRecPmtInstr(str2, localFFSConnectionHolder);
          localObject2 = ((RecPmtInstruction)localObject1).getRecPmtInfo();
          PmtInfo localPmtInfo = new PmtInfo("", ((RecPmtInfo)localObject2).CustomerID, ((RecPmtInfo)localObject2).FIID, ((RecPmtInfo)localObject2).PayeeID, ((RecPmtInfo)localObject2).PayAcct, ((RecPmtInfo)localObject2).PayeeListID, localTempHist.Amount, ((RecPmtInfo)localObject2).BankID, ((RecPmtInfo)localObject2).AcctDebitID, ((RecPmtInfo)localObject2).AcctDebitType, ((RecPmtInfo)localObject2).Memo, ((RecPmtInfo)localObject2).ExtdPmtInfo, ((RecPmtInfo)localObject2).DateCreate, ((RecPmtInfo)localObject2).CurDef, "WILLPROCESSON", localTempHist.DueDate, "Recurring", str2, null, null, ((RecPmtInfo)localObject2).submittedBy);
          localPmtInfo.payeeInfo = Payee.findPayeeByID(((RecPmtInstruction)localObject1).getPayeeID(), localFFSConnectionHolder);
          RecSrvrTrans localRecSrvrTrans = RecSrvrTrans.getRecSrvrTransById(str2, localFFSConnectionHolder);
          localPmtInfo.StatusDt = localRecSrvrTrans.getSubmitdate();
          int j = SmartCalendar.getPayday(((RecPmtInfo)localObject2).FIID, localTempHist.DueDate / 100);
          localPmtInfo.AdjustedDueDt = String.valueOf(j);
          localArrayList.add(localPmtInfo);
        }
        else
        {
          throw new Exception("PmtSyncProcessor.getHistory: Invalid RecordType" + localTempHist.RecordType);
        }
      }
      if (arrayOfTempHist.length > 0) {
        paramBPWHist.setCursorId(arrayOfTempHist[(arrayOfTempHist.length - 1)].CursorId);
      }
      paramBPWHist.trans = ((PmtInfo[])localArrayList.toArray(new PmtInfo[0]));
      if (paramBPWHist.getTotalTrans() == 0L) {
        paramBPWHist.setTotalTrans(TempHist.getCount(localFFSConnectionHolder, paramString1));
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      str1 = localException.toString();
      FFSDebug.log("*** PmtSyncProcessor.getHistory failed:" + str1);
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str1 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("*** PmtSyncProcessor.getHistory failed:" + str1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("PmtSyncProcessor.getHistory: done histId=" + paramString1 + ",cursorId=" + paramString2 + ",pageSize=" + paramLong, 6);
  }
  
  public PagingInfo getPagedBillPayments(PagingInfo paramPagingInfo)
    throws Exception
  {
    String str1 = "getPagedBillPayments";
    try
    {
      paramPagingInfo = this.l.getPagedData(paramPagingInfo);
    }
    catch (Throwable localThrowable)
    {
      String str2 = str1 + "failed";
      FFSDebug.log(localThrowable, str2, 0);
      throw new BPWException(localThrowable, str2);
    }
    return paramPagingInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PmtSyncProcessor
 * JD-Core Version:    0.7.0.1
 */