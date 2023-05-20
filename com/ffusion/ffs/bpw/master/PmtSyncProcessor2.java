package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.RecPmtInstruction;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.ScheduleConstants;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSyncRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSyncRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTokenRqUn;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class PmtSyncProcessor2
  implements DBConsts, OFXConsts, BPWResource, ScheduleConstants
{
  private PmtProcessor2 ax = null;
  private int aw;
  
  public PmtSyncProcessor2(PmtProcessor2 paramPmtProcessor2)
  {
    this.ax = paramPmtProcessor2;
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.aw = localPropertyConfig.LogLevel;
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
      String str2 = paramTypePmtSyncRqV1.PmtSyncRq.SyncRqCm.TokenRqUn.__memberName;
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
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsCm = new TypeSyncRsCm();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsCm.Token = "-1";
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom = paramTypePmtSyncRqV1.PmtSyncRq.BankAcctFrom;
    return localTypePmtSyncRsV1;
  }
  
  public TypePmtSyncRsV1 processPmtRefreshRqV1(TypePmtSyncRqV1 paramTypePmtSyncRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    TypePmtSyncRsV1 localTypePmtSyncRsV1 = new TypePmtSyncRsV1();
    localTypePmtSyncRsV1.PmtSyncRs = new TypePmtSyncRsV1Aggregate();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsCm = new TypeSyncRsCm();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsCm.Token = "0";
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom = paramTypePmtSyncRqV1.PmtSyncRq.BankAcctFrom;
    try
    {
      AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      if (localAuditAgent == null) {
        throw new BPWException("AuditAgent is null!!");
      }
      String str1 = paramTypePmtSyncRqV1.PmtSyncRq.BankAcctFrom.AcctID;
      String str2 = MsgBuilder.getAcctType(paramTypePmtSyncRqV1.PmtSyncRq.BankAcctFrom.AcctType);
      String[] arrayOfString = localAuditAgent.getSrvrTrans(paramTypeUserData._uid, str1, str2, "OFX200PmtSync");
      int i = arrayOfString.length;
      TypePmtTrnRsV1[] arrayOfTypePmtTrnRsV1 = new TypePmtTrnRsV1[i];
      for (int j = 0; j < i; j++)
      {
        String str3 = (String)arrayOfString[j];
        BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
        arrayOfTypePmtTrnRsV1[j] = ((TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str3, "PmtTrnRsV1", "OFX200"));
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
      for (int m = 0; m < j; m++)
      {
        TypePmtTrnRqV1 localTypePmtTrnRqV1 = new TypePmtTrnRqV1(paramTypePmtSyncRqV1.PmtSyncRq.PmtTrnRq[m]);
        localTypePmtSyncRsV1.PmtSyncRs.PmtTrnRs[m] = this.ax.processPmtTrnRqV1(localTypePmtTrnRqV1, paramTypeUserData, paramString).PmtTrnRs;
      }
      for (m = j; m < j + i; m++) {
        localTypePmtSyncRsV1.PmtSyncRs.PmtTrnRs[m] = arrayOfTypePmtTrnRsV1[(m - j)].PmtTrnRs;
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
    FFSDebug.log("PmtSyncProcessor2.processPendingPmtRqV1: begin", 6);
    String str1 = paramTypePmtSyncRqV1.PmtSyncRq.BankAcctFrom.AcctID;
    String str2 = MsgBuilder.getAcctType(paramTypePmtSyncRqV1.PmtSyncRq.BankAcctFrom.AcctType);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localTypePmtSyncRsV1 = jdMethod_for(paramTypeUserData._uid, str1, str2, paramInt, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      String str3 = "PmtSyncProcessor2.processPendingPmtRqV1 failed : ";
      throw new Exception(str3 + localException.getMessage());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypePmtSyncRsV1;
  }
  
  private TypePmtSyncRsV1 jdMethod_for(String paramString1, String paramString2, String paramString3, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("PmtSyncProcessor2.processPendingPmtRqV1: acctID=" + paramString2 + ",uid=" + paramString1, 6);
    TypePmtSyncRsV1 localTypePmtSyncRsV1 = new TypePmtSyncRsV1();
    localTypePmtSyncRsV1.PmtSyncRs = new TypePmtSyncRsV1Aggregate();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsCm = new TypeSyncRsCm();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsCm.Token = "0";
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom = new TypeBankAcctFromAggregate();
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom.AcctID = paramString2;
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom.AcctType = MsgBuilder.getOFX200AcctEnum(paramString3);
    Vector localVector = new Vector();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    int i = ScheduleInfo.getInstanceDate(0);
    int j = ScheduleInfo.getInstanceDate(paramInt);
    String[] arrayOfString1 = localAuditAgent.getPendingSrvrTrans(paramString1, paramString2, paramString3, "OFX200PmtSync");
    int k = arrayOfString1.length;
    FFSDebug.log("PmtSyncProcessor2.processPendingPmtRqV1: getPendingSrvrTrans=" + k, 6);
    Object localObject;
    for (int m = 0; m < k; m++)
    {
      String str1 = (String)arrayOfString1[m];
      TypePmtTrnRsV1 localTypePmtTrnRsV1 = (TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str1, "PmtTrnRsV1", "OFX200");
      localObject = localSimpleDateFormat.parse(localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
      int i1 = ScheduleInfo.convertInstanceDateToNum((Date)localObject);
      FFSDebug.log("PmtSyncProcessor2.processPendingPmtRqV1: dtDue=" + i1, 6);
      if (i1 <= j) {
        localVector.addElement(localTypePmtTrnRsV1.PmtTrnRs);
      }
    }
    arrayOfString1 = localAuditAgent.getPendingRecSrvrTrans(paramString1, paramString2, paramString3, "OFX200RecPmtSync");
    k = arrayOfString1.length;
    FFSDebug.log("PmtSyncProcessor2.processPendingPmtRqV1: getPendingRecSrvrTrans=" + k, 6);
    for (int n = 0; n < k; n++)
    {
      localObject = (String)arrayOfString1[n];
      TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = (TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg((String)localObject, "RecPmtTrnRsV1", "OFX200");
      String str2 = "1000";
      RecPmtInstruction localRecPmtInstruction = RecPmtInstruction.getRecPmtInstr(localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecSrvrTID, paramFFSConnectionHolder);
      str2 = localRecPmtInstruction.getFIID();
      ScheduleInfo localScheduleInfo = new ScheduleInfo();
      Date localDate = localSimpleDateFormat.parse(localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
      localScheduleInfo.StartDate = ScheduleInfo.convertInstanceDateToNum(localDate);
      localScheduleInfo.NextInstanceDate = localScheduleInfo.StartDate;
      localScheduleInfo.Frequency = CommonProcessor.mapOFX200FreqToBPWFreq(localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.Freq.value());
      localScheduleInfo.InstanceCount = localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInsts;
      localScheduleInfo.CurInstanceNum = 1;
      if (!localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInstsExists) {
        localScheduleInfo.Perpetual = 1;
      }
      String[] arrayOfString2 = ScheduleInfo.getPendingDates(localScheduleInfo, paramInt);
      localScheduleInfo.CurInstanceNum = localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInsts;
      ScheduleInfo.computeNextInstanceDate(localScheduleInfo);
      String str3 = Integer.toString(localScheduleInfo.NextInstanceDate) + "0000";
      for (int i2 = 0; i2 < arrayOfString2.length; i2++)
      {
        TypeRecPmtRsAggregate localTypeRecPmtRsAggregate = localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs;
        TypePmtTrnRsV1Aggregate localTypePmtTrnRsV1Aggregate = new TypePmtTrnRsV1Aggregate();
        localTypePmtTrnRsV1Aggregate.TrnRsCm = localTypeRecPmtTrnRsV1.RecPmtTrnRs.TrnRsCm;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists = true;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un = new TypePmtTrnRsV1Un();
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__preValueTag = "PMTRS";
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName = "PmtRs";
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs = new TypePmtRsAggregate();
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.SrvrTID = "0";
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PayeeLstID = localTypeRecPmtRsAggregate.PayeeLstID;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CurDef = localTypeRecPmtRsAggregate.CurDef;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee = localTypeRecPmtRsAggregate.ExtdPayee;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = localTypeRecPmtRsAggregate.ExtdPayeeExists;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CheckNumExists = false;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts = new TypePmtPrcStsAggregate();
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = EnumPmtProcessStatusEnum.WILLPROCESSON;
        int i3 = Integer.parseInt(arrayOfString2[i2].substring(0, 8));
        int i4 = SmartCalendar.getPayday(str2, i3) * 100;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = ("" + i4 + "0000");
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTID = localTypeRecPmtRsAggregate.RecSrvrTID;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTIDExists = true;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo = new TypePmtInfoAggregate();
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctFrom = localTypeRecPmtRsAggregate.PmtInfo.BankAcctFrom;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.TrnAmt = localTypeRecPmtRsAggregate.PmtInfo.TrnAmt;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeUn = localTypeRecPmtRsAggregate.PmtInfo.PayeeUn;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstID = localTypeRecPmtRsAggregate.PmtInfo.PayeeLstID;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstIDExists = localTypeRecPmtRsAggregate.PmtInfo.PayeeLstIDExists;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctTo = localTypeRecPmtRsAggregate.PmtInfo.BankAcctTo;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctToExists = localTypeRecPmtRsAggregate.PmtInfo.BankAcctToExists;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.ExtdPmt = localTypeRecPmtRsAggregate.PmtInfo.ExtdPmt;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayAcct = localTypeRecPmtRsAggregate.PmtInfo.PayAcct;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue = localTypeRecPmtRsAggregate.PmtInfo.DtDue;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.Memo = localTypeRecPmtRsAggregate.PmtInfo.Memo;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.MemoExists = localTypeRecPmtRsAggregate.PmtInfo.MemoExists;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BillRefInfo = localTypeRecPmtRsAggregate.PmtInfo.BillRefInfo;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BillRefInfoExists = localTypeRecPmtRsAggregate.PmtInfo.BillRefInfoExists;
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue = arrayOfString2[i2];
        if ((localTypeRecPmtRsAggregate.FinalAmtExists) && (str3.equals(arrayOfString2[i2]))) {
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.TrnAmt = localTypeRecPmtRsAggregate.FinalAmt;
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
    FFSDebug.log("PmtSyncProcessor2.getPendingPmtsByCustomerID: custId=" + paramString + ",pendingDay=" + paramInt, 6);
    TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV1 = null;
    ArrayList localArrayList = new ArrayList();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      SrvrTrans[] arrayOfSrvrTrans = SrvrTrans.getAcctByCustomerID(paramString, "OFX200PmtSync", localFFSConnectionHolder);
      for (int i = 0; i < arrayOfSrvrTrans.length; i++)
      {
        String str2 = arrayOfSrvrTrans[i].getAcctID();
        String str3 = arrayOfSrvrTrans[i].getAcctType();
        TypePmtSyncRsV1 localTypePmtSyncRsV1 = jdMethod_for(paramString, str2, str3, paramInt, localFFSConnectionHolder);
        localArrayList.add(localTypePmtSyncRsV1);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      String str1 = "PmtSyncProcessor2.getPendingPmtsByCustomerID failed : ";
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
    FFSDebug.log("PmtSyncProcessor2.getPendingPmtsAndHistoryByCustomerID: custId=" + paramString + ",pendingDay=" + paramInt1 + ",pastDay=" + paramInt2, 6);
    TypePmtSyncRsV1[] arrayOfTypePmtSyncRsV1 = null;
    ArrayList localArrayList = new ArrayList();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      SrvrTrans[] arrayOfSrvrTrans = SrvrTrans.getAcctByCustomerID(paramString, "OFX200PmtSync", localFFSConnectionHolder);
      for (int i = 0; i < arrayOfSrvrTrans.length; i++)
      {
        String str2 = arrayOfSrvrTrans[i].getAcctID();
        String str3 = arrayOfSrvrTrans[i].getAcctType();
        TypePmtSyncRsV1 localTypePmtSyncRsV1 = jdMethod_for(paramString, str2, str3, paramInt1, paramInt2, localFFSConnectionHolder);
        localArrayList.add(localTypePmtSyncRsV1);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      String str1 = "PmtSyncProcessor2.getPendingPmtsAndHistoryByCustomerID failed : ";
      throw new Exception(str1 + localException.getMessage());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    arrayOfTypePmtSyncRsV1 = (TypePmtSyncRsV1[])localArrayList.toArray(new TypePmtSyncRsV1[0]);
    return arrayOfTypePmtSyncRsV1;
  }
  
  private TypePmtSyncRsV1 jdMethod_for(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("PmtSyncProcessor2.processPendingPmtAndHistoryRqV1: acctID=" + paramString2 + ",uid=" + paramString1, 6);
    TypePmtSyncRsV1 localTypePmtSyncRsV1 = new TypePmtSyncRsV1();
    localTypePmtSyncRsV1.PmtSyncRs = new TypePmtSyncRsV1Aggregate();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsCm = new TypeSyncRsCm();
    localTypePmtSyncRsV1.PmtSyncRs.SyncRsCm.Token = "0";
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom = new TypeBankAcctFromAggregate();
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom.AcctID = paramString2;
    localTypePmtSyncRsV1.PmtSyncRs.BankAcctFrom.AcctType = MsgBuilder.getOFX200AcctEnum(paramString3);
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
    String[] arrayOfString1 = localAuditAgent.getSrvrTrans(paramString1, paramString2, paramString3, "OFX200PmtSync");
    int k = arrayOfString1.length;
    FFSDebug.log("PmtSyncProcessor2.processPendingPmtAndHistoryRqV1: getSrvrTrans=" + k, 6);
    Object localObject;
    for (int m = 0; m < k; m++)
    {
      String str1 = (String)arrayOfString1[m];
      TypePmtTrnRsV1 localTypePmtTrnRsV1 = (TypePmtTrnRsV1)localBPWMsgBroker.parseMsg(str1, "PmtTrnRsV1", "OFX200");
      localObject = localSimpleDateFormat.parse(localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
      int i1 = ScheduleInfo.convertInstanceDateToNum((Date)localObject);
      FFSDebug.log("PmtSyncProcessor2.processPendingPmtAndHistoryRqV1: dtDue=" + i1 + ",dtPending=" + j + ",dtPast=" + i, 6);
      if ((i1 <= j) && (i1 > i)) {
        localVector.addElement(localTypePmtTrnRsV1.PmtTrnRs);
      }
    }
    if (paramInt1 > 0)
    {
      arrayOfString1 = localAuditAgent.getPendingRecSrvrTrans(paramString1, paramString2, paramString3, "OFX200RecPmtSync");
      k = arrayOfString1.length;
      FFSDebug.log("PmtSyncProcessor2.processPendingPmtAndHistoryRqV1: getPendingRecSrvrTrans=" + k, 6);
      for (int n = 0; n < k; n++)
      {
        localObject = (String)arrayOfString1[n];
        TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = (TypeRecPmtTrnRsV1)localBPWMsgBroker.parseMsg((String)localObject, "RecPmtTrnRsV1", "OFX200");
        String str2 = "1000";
        RecPmtInstruction localRecPmtInstruction = RecPmtInstruction.getRecPmtInstr(localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecSrvrTID, paramFFSConnectionHolder);
        str2 = localRecPmtInstruction.getFIID();
        ScheduleInfo localScheduleInfo = new ScheduleInfo();
        Date localDate = localSimpleDateFormat.parse(localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.PmtInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
        localScheduleInfo.StartDate = ScheduleInfo.convertInstanceDateToNum(localDate);
        localScheduleInfo.NextInstanceDate = localScheduleInfo.StartDate;
        localScheduleInfo.Frequency = CommonProcessor.mapOFX200FreqToBPWFreq(localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.Freq.value());
        localScheduleInfo.InstanceCount = localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInsts;
        localScheduleInfo.CurInstanceNum = 1;
        if (!localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInstsExists) {
          localScheduleInfo.Perpetual = 1;
        }
        String[] arrayOfString2 = ScheduleInfo.getPendingDates(localScheduleInfo, paramInt1);
        localScheduleInfo.CurInstanceNum = localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs.RecurrInst.NInsts;
        ScheduleInfo.computeNextInstanceDate(localScheduleInfo);
        String str3 = Integer.toString(localScheduleInfo.NextInstanceDate) + "0000";
        for (int i2 = 0; i2 < arrayOfString2.length; i2++)
        {
          TypeRecPmtRsAggregate localTypeRecPmtRsAggregate = localTypeRecPmtTrnRsV1.RecPmtTrnRs.RecPmtTrnRsUn.RecPmtRs;
          TypePmtTrnRsV1Aggregate localTypePmtTrnRsV1Aggregate = new TypePmtTrnRsV1Aggregate();
          localTypePmtTrnRsV1Aggregate.TrnRsCm = localTypeRecPmtTrnRsV1.RecPmtTrnRs.TrnRsCm;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists = true;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un = new TypePmtTrnRsV1Un();
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__preValueTag = "PMTRS";
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName = "PmtRs";
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs = new TypePmtRsAggregate();
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.SrvrTID = "0";
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PayeeLstID = localTypeRecPmtRsAggregate.PayeeLstID;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CurDef = localTypeRecPmtRsAggregate.CurDef;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee = localTypeRecPmtRsAggregate.ExtdPayee;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = localTypeRecPmtRsAggregate.ExtdPayeeExists;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CheckNumExists = false;
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts = new TypePmtPrcStsAggregate();
          localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = EnumPmtProcessStatusEnum.WILLPROCESSON;
          int i3 = Integer.parseInt(arrayOfString2[i2].substring(0, 8));
          int i4 = SmartCalendar.getPayday(str2, i3) * 100;
          if (i4 >= i)
          {
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = ("" + i4 + "0000");
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTID = localTypeRecPmtRsAggregate.RecSrvrTID;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTIDExists = true;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo = new TypePmtInfoAggregate();
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctFrom = localTypeRecPmtRsAggregate.PmtInfo.BankAcctFrom;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.TrnAmt = localTypeRecPmtRsAggregate.PmtInfo.TrnAmt;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeUn = localTypeRecPmtRsAggregate.PmtInfo.PayeeUn;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstID = localTypeRecPmtRsAggregate.PmtInfo.PayeeLstID;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstIDExists = localTypeRecPmtRsAggregate.PmtInfo.PayeeLstIDExists;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctTo = localTypeRecPmtRsAggregate.PmtInfo.BankAcctTo;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BankAcctToExists = localTypeRecPmtRsAggregate.PmtInfo.BankAcctToExists;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.ExtdPmt = localTypeRecPmtRsAggregate.PmtInfo.ExtdPmt;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayAcct = localTypeRecPmtRsAggregate.PmtInfo.PayAcct;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue = localTypeRecPmtRsAggregate.PmtInfo.DtDue;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.Memo = localTypeRecPmtRsAggregate.PmtInfo.Memo;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.MemoExists = localTypeRecPmtRsAggregate.PmtInfo.MemoExists;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BillRefInfo = localTypeRecPmtRsAggregate.PmtInfo.BillRefInfo;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.BillRefInfoExists = localTypeRecPmtRsAggregate.PmtInfo.BillRefInfoExists;
            localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.DtDue = arrayOfString2[i2];
            if ((localTypeRecPmtRsAggregate.FinalAmtExists) && (str3.equals(arrayOfString2[i2]))) {
              localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.TrnAmt = localTypeRecPmtRsAggregate.FinalAmt;
            }
            localVector.addElement(localTypePmtTrnRsV1Aggregate);
          }
        }
      }
    }
    localTypePmtSyncRsV1.PmtSyncRs.PmtTrnRs = ((TypePmtTrnRsV1Aggregate[])localVector.toArray(new TypePmtTrnRsV1Aggregate[0]));
    return localTypePmtSyncRsV1;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PmtSyncProcessor2
 * JD-Core Version:    0.7.0.1
 */