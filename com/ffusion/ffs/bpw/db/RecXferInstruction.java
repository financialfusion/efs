package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.interfaces.AccountTransactions;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWExtdHist;
import com.ffusion.ffs.bpw.interfaces.BPWInfoBase;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
import com.ffusion.ffs.bpw.interfaces.TransferIntraMap;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate;
import com.ffusion.util.beans.PagingContext;
import com.ffusion.util.enums.UserAssignedAmount;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class RecXferInstruction
  extends BPWInfoBase
  implements FFSConst, DBConsts, BPWResource
{
  public String RecSrvrTID;
  public String CustomerID;
  public String FIID;
  public String Amount;
  public String CurDef;
  public String ToAmount;
  public String ToAmtCurrency;
  public UserAssignedAmount userAssignedAmount;
  public String BankID;
  public String BranchID;
  public String AcctDebitID;
  public String AcctDebitType;
  public String AcctCreditID;
  public String AcctCreditType;
  public int Frequency;
  public int StartDate;
  public int EndDate;
  public int InstanceCount;
  public String DateCreate;
  public String Status;
  public String LogID;
  public String SubmittedBy;
  public String fromBankID;
  public String fromBranchID;
  private static Hashtable wV = new Hashtable();
  private static String wT = " AND RecSrvrTID in (Select RecSrvrTID from BPW_RecXferExtraInfo where ";
  private static final String wW = " ORDER BY CAST(RecSrvrTID AS INTEGER) ASC";
  private static int wU = 0;
  
  public RecXferInstruction() {}
  
  public RecXferInstruction(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16)
  {
    this(paramString1, paramString2, paramString3, paramString4, null, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramInt1, paramInt2, paramInt3, paramInt4, paramString11, paramString12, paramString13, paramString14, paramString15, paramString16);
  }
  
  public RecXferInstruction(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, "0.00", null, UserAssignedAmount.SINGLE, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramInt1, paramInt2, paramInt3, paramInt4, paramString12, paramString13, paramString14, paramString15, paramString16, paramString17);
  }
  
  public RecXferInstruction(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, UserAssignedAmount paramUserAssignedAmount, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19)
  {
    this.RecSrvrTID = paramString1;
    this.CustomerID = paramString2;
    this.FIID = paramString3;
    this.Amount = paramString4;
    this.CurDef = BPWUtil.validateCurrencyString(paramString5);
    this.ToAmount = paramString6;
    this.ToAmtCurrency = BPWUtil.validateCurrencyString(paramString7);
    this.userAssignedAmount = paramUserAssignedAmount;
    this.BankID = paramString8;
    this.BranchID = paramString9;
    this.AcctDebitID = paramString10;
    this.AcctDebitType = paramString11;
    this.AcctCreditID = paramString12;
    this.AcctCreditType = paramString13;
    this.Frequency = paramInt1;
    this.StartDate = paramInt2;
    this.EndDate = paramInt3;
    this.InstanceCount = paramInt4;
    this.DateCreate = paramString14;
    this.Status = paramString15;
    this.LogID = paramString16;
    this.SubmittedBy = paramString17;
    this.fromBankID = paramString18;
    this.fromBranchID = paramString19;
  }
  
  public static String create(FFSConnectionHolder paramFFSConnectionHolder, RecXferInstruction paramRecXferInstruction)
    throws FFSException
  {
    FFSDebug.log("RecXferInstruction.create: start", 6);
    String str1 = "INSERT INTO BPW_RecXferInstruction (RecSrvrTID,CustomerID,FIID,Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,Frequency,StartDate,EndDate,InstanceCount,DateCreate,Status,LastModified,LogID,SubmittedBy,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String str2;
    try
    {
      str2 = DBUtil.getNextIndexString("RecSrvrTID");
    }
    catch (BPWException localBPWException)
    {
      localObject = "*** RecXferInstruction.create failed:";
      FFSDebug.log((String)localObject + localBPWException.toString());
      throw new FFSException(localBPWException.toString());
    }
    int i = 0;
    if (paramRecXferInstruction.userAssignedAmount != null) {
      i = paramRecXferInstruction.userAssignedAmount.getValue();
    }
    Object localObject = { str2, paramRecXferInstruction.CustomerID, paramRecXferInstruction.FIID, paramRecXferInstruction.Amount, paramRecXferInstruction.BankID, paramRecXferInstruction.BranchID, paramRecXferInstruction.AcctDebitID, paramRecXferInstruction.AcctDebitType, paramRecXferInstruction.AcctCreditID, paramRecXferInstruction.AcctCreditType, new Integer(paramRecXferInstruction.Frequency), new Integer(paramRecXferInstruction.StartDate), new Integer(paramRecXferInstruction.EndDate), new Integer(paramRecXferInstruction.InstanceCount), DBUtil.getCurrentLogDate(), paramRecXferInstruction.Status, FFSUtil.getDateString("yyyyMMddHHmmss"), paramRecXferInstruction.LogID, paramRecXferInstruction.SubmittedBy, paramRecXferInstruction.fromBankID, paramRecXferInstruction.fromBranchID, paramRecXferInstruction.CurDef, paramRecXferInstruction.ToAmount, paramRecXferInstruction.ToAmtCurrency, new Integer(i) };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** RecXferInstruction.create: failed:";
      FFSDebug.log(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("RecXferInstruction.create: done, RecSrvrTID=" + str2, 6);
    return str2;
  }
  
  public static String create(FFSConnectionHolder paramFFSConnectionHolder, RecTransferInfo paramRecTransferInfo)
    throws FFSException
  {
    FFSDebug.log("RecXferInstruction.create: start", 6);
    String str1 = "INSERT INTO BPW_RecXferInstruction (RecSrvrTID,CustomerID,FIID,Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,Frequency,StartDate,EndDate,InstanceCount,DateCreate,Status,LastModified,LogID,SubmittedBy,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String str2;
    try
    {
      str2 = DBUtil.getNextIndexString("RecSrvrTID");
    }
    catch (BPWException localBPWException)
    {
      str4 = "*** RecXferInstruction.create failed:";
      FFSDebug.log(str4 + localBPWException.toString());
      throw new FFSException(localBPWException.toString());
    }
    String str3 = paramRecTransferInfo.getAmount();
    if (paramRecTransferInfo.getIsAmountEstimated()) {
      str3 = "0.0";
    }
    String str4 = paramRecTransferInfo.getToAmount();
    if (paramRecTransferInfo.getIsToAmountEstimated()) {
      str4 = "0.00";
    }
    int i = 0;
    if (paramRecTransferInfo.getUserAssignedAmount() != null) {
      i = paramRecTransferInfo.getUserAssignedAmount().getValue();
    }
    XferInstruction.jdMethod_byte(paramFFSConnectionHolder, paramRecTransferInfo);
    XferInstruction.jdMethod_case(paramFFSConnectionHolder, paramRecTransferInfo);
    Object[] arrayOfObject = { str2, paramRecTransferInfo.getCustomerId(), paramRecTransferInfo.getFIId(), new Double(str3).toString(), paramRecTransferInfo.getBankToRtn(), paramRecTransferInfo.getBranchID(), paramRecTransferInfo.getAccountFromNum(), paramRecTransferInfo.getAccountFromType(), paramRecTransferInfo.getAccountToNum(), paramRecTransferInfo.getAccountToType(), new Integer(FFSUtil.getFreqInt(paramRecTransferInfo.getFrequency())), new Integer(BPWUtil.getDateDBFormat(paramRecTransferInfo.getStartDate())), new Integer(BPWUtil.getDateDBFormat(paramRecTransferInfo.getEndDate())), new Integer(paramRecTransferInfo.getPmtsCount()), DBUtil.getCurrentLogDate(), paramRecTransferInfo.getPrcStatus(), FFSUtil.getDateString("yyyyMMddHHmmss"), paramRecTransferInfo.getLogId(), paramRecTransferInfo.getSubmittedBy(), paramRecTransferInfo.getBankFromRtn(), paramRecTransferInfo.getBranchID(), paramRecTransferInfo.getAmountCurrency(), str4, paramRecTransferInfo.getToAmountCurrency(), new Integer(i) };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
      paramRecTransferInfo.setSrvrTId(str2);
      jdMethod_for(paramFFSConnectionHolder, paramRecTransferInfo);
    }
    catch (Exception localException)
    {
      String str5 = "*** RecXferInstruction.create: failed:";
      FFSDebug.log(str5 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("RecXferInstruction.create: done, RecSrvrTID=" + str2, 6);
    return str2;
  }
  
  private static void jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, RecTransferInfo paramRecTransferInfo)
    throws Exception
  {
    String str1 = "XferInstruction.addSrvrTrans";
    FFSDebug.log(str1 + ": start", 6);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException(str1 + ":AuditAgent is null!!");
    }
    TypeRecIntraTrnRqV1 localTypeRecIntraTrnRqV1 = TransferIntraMap.mapRecTransferInfoToRecIntraRq(paramRecTransferInfo, new TypeUserData());
    String str2 = paramRecTransferInfo.getAction();
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1;
    if (str2.equals("Add"))
    {
      localTypeRecIntraTrnRsV1 = jdMethod_if(localTypeRecIntraTrnRqV1, paramRecTransferInfo);
      localAuditAgent.saveRecXferRsV1(localTypeRecIntraTrnRsV1, paramRecTransferInfo.getCustomerId(), paramFFSConnectionHolder);
    }
    else if (str2.equals("Modify"))
    {
      localTypeRecIntraTrnRsV1 = a(localTypeRecIntraTrnRqV1, paramRecTransferInfo);
      RecXferInstruction localRecXferInstruction = TransferIntraMap.mapRecTransferInfoToRecXferInst(paramRecTransferInfo);
      localAuditAgent.modRecXferRsV1(localTypeRecIntraTrnRsV1, localRecXferInstruction, paramFFSConnectionHolder);
    }
    else if (str2.equals("Cancel"))
    {
      localAuditAgent.cancelOFX200RecXferRsV1(paramRecTransferInfo.getSrvrTId(), paramFFSConnectionHolder);
    }
    FFSDebug.log(str1 + ": done, SrvrTID=" + paramRecTransferInfo.getSrvrTId(), 6);
  }
  
  private static TypeRecIntraTrnRsV1 jdMethod_if(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, RecTransferInfo paramRecTransferInfo)
    throws BPWException
  {
    TypeTrnRqCm localTypeTrnRqCm = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm;
    TypeXferInfoAggregate localTypeXferInfoAggregate = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.IntraRq.XferInfo;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate1 = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.RecurrInst;
    EnumCurrencyEnum localEnumCurrencyEnum = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.IntraRq.CurDef;
    TypeRecIntraTrnRsV1Aggregate localTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsUnExists = true;
    TypeRecIntraRsUn localTypeRecIntraRsUn = new TypeRecIntraRsUn();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn = localTypeRecIntraRsUn;
    localTypeRecIntraRsUn.__memberName = "RecIntraRs";
    TypeRecIntraRsAggregate localTypeRecIntraRsAggregate = new TypeRecIntraRsAggregate();
    localTypeRecIntraRsUn.RecIntraRs = localTypeRecIntraRsAggregate;
    TypeIntraRsAggregate localTypeIntraRsAggregate = new TypeIntraRsAggregate();
    localTypeRecIntraRsAggregate.IntraRs = localTypeIntraRsAggregate;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate2 = new TypeRecurrInstAggregate();
    localTypeRecIntraRsAggregate.RecurrInst = localTypeRecurrInstAggregate2;
    localTypeRecIntraRsAggregate.RecSrvrTID = paramRecTransferInfo.getSrvrTId();
    localTypeRecurrInstAggregate2.NInsts = paramRecTransferInfo.getPmtsCount();
    localTypeRecurrInstAggregate2.NInstsExists = true;
    localTypeRecurrInstAggregate2.Freq = localTypeRecurrInstAggregate1.Freq;
    if (localEnumCurrencyEnum != null) {
      localTypeIntraRsAggregate.CurDef = localEnumCurrencyEnum;
    } else {
      localTypeIntraRsAggregate.CurDef = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyEnum(localEnumCurrencyEnum));
    }
    localTypeIntraRsAggregate.SrvrTID = paramRecTransferInfo.getSrvrTId();
    localTypeIntraRsAggregate.RecSrvrTID = paramRecTransferInfo.getSrvrTId();
    if (paramRecTransferInfo.getSrvrTId() != null) {
      localTypeIntraRsAggregate.RecSrvrTIDExists = true;
    } else {
      localTypeIntraRsAggregate.RecSrvrTIDExists = false;
    }
    localTypeIntraRsAggregate.XferInfo = localTypeXferInfoAggregate;
    localTypeIntraRsAggregate.XferPrcStsExists = true;
    localTypeIntraRsAggregate.XferPrcSts = new TypeXferPrcStsAggregate();
    RsCmBuilder.updateRsXferPrcSts(paramRecTransferInfo.getPrcStatus(), paramRecTransferInfo.getStartDate() + "000000", localTypeIntraRsAggregate.XferPrcSts);
    RsCmBuilder.updateXferRsDateXferPrj(paramRecTransferInfo.getStartDate() + "000000", localTypeIntraRsAggregate);
    localTypeRecIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(localTypeTrnRqCm, paramRecTransferInfo.getStatusCode());
    return new TypeRecIntraTrnRsV1(localTypeRecIntraTrnRsV1Aggregate);
  }
  
  private static TypeRecIntraTrnRsV1 a(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, RecTransferInfo paramRecTransferInfo)
    throws BPWException
  {
    TypeTrnRqCm localTypeTrnRqCm = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm;
    TypeXferInfoAggregate localTypeXferInfoAggregate = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.IntraRq.XferInfo;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate1 = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecurrInst;
    EnumCurrencyEnum localEnumCurrencyEnum = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.IntraRq.CurDef;
    TypeRecIntraTrnRsV1Aggregate localTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsUnExists = true;
    TypeRecIntraRsUn localTypeRecIntraRsUn = new TypeRecIntraRsUn();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn = localTypeRecIntraRsUn;
    localTypeRecIntraRsUn.__memberName = "RecIntraModRs";
    TypeRecIntraModRsAggregate localTypeRecIntraModRsAggregate = new TypeRecIntraModRsAggregate();
    localTypeRecIntraRsUn.RecIntraModRs = localTypeRecIntraModRsAggregate;
    localTypeRecIntraModRsAggregate.RecSrvrTID = paramRecTransferInfo.getSrvrTId();
    localTypeRecIntraModRsAggregate.ModPending = true;
    TypeIntraRsAggregate localTypeIntraRsAggregate = new TypeIntraRsAggregate();
    localTypeRecIntraModRsAggregate.IntraRs = localTypeIntraRsAggregate;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate2 = new TypeRecurrInstAggregate();
    localTypeRecIntraModRsAggregate.RecurrInst = localTypeRecurrInstAggregate2;
    localTypeRecurrInstAggregate2.NInsts = paramRecTransferInfo.getPmtsCount();
    localTypeRecurrInstAggregate2.NInstsExists = true;
    localTypeRecurrInstAggregate2.Freq = localTypeRecurrInstAggregate1.Freq;
    if (localEnumCurrencyEnum != null) {
      localTypeIntraRsAggregate.CurDef = localEnumCurrencyEnum;
    } else {
      localTypeIntraRsAggregate.CurDef = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyEnum(localEnumCurrencyEnum));
    }
    localTypeIntraRsAggregate.SrvrTID = paramRecTransferInfo.getSrvrTId();
    localTypeIntraRsAggregate.RecSrvrTID = paramRecTransferInfo.getSrvrTId();
    if (paramRecTransferInfo.getSrvrTId() != null) {
      localTypeIntraRsAggregate.RecSrvrTIDExists = true;
    } else {
      localTypeIntraRsAggregate.RecSrvrTIDExists = false;
    }
    localTypeIntraRsAggregate.XferInfo = localTypeXferInfoAggregate;
    localTypeIntraRsAggregate.XferPrcStsExists = true;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new TypeXferPrcStsAggregate();
    localTypeIntraRsAggregate.XferPrcSts = localTypeXferPrcStsAggregate;
    RsCmBuilder.updateRsXferPrcSts(paramRecTransferInfo.getPrcStatus(), paramRecTransferInfo.getStartDate() + "000000", localTypeIntraRsAggregate.XferPrcSts);
    RsCmBuilder.updateXferRsDateXferPrj(paramRecTransferInfo.getStartDate() + "000000", localTypeIntraRsAggregate);
    localTypeRecIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(localTypeTrnRqCm, paramRecTransferInfo.getStatusCode());
    return new TypeRecIntraTrnRsV1(localTypeRecIntraTrnRsV1Aggregate);
  }
  
  public static RecXferInstruction getRecXferInstruction(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("RecXferInstruction.getRecXferInstruction: start, RecSrvrTID=" + paramString, 6);
    String str = "SELECT Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,Frequency,StartDate,EndDate,InstanceCount,DateCreate,CustomerID,Status,LogID,FIID,SubmittedBy,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount FROM BPW_RecXferInstruction WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { paramString };
    RecXferInstruction localRecXferInstruction = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localRecXferInstruction = new RecXferInstruction();
        localRecXferInstruction.RecSrvrTID = paramString;
        localRecXferInstruction.Amount = localFFSResultSet.getColumnString(1);
        localRecXferInstruction.BankID = localFFSResultSet.getColumnString(2);
        localRecXferInstruction.BranchID = localFFSResultSet.getColumnString(3);
        localRecXferInstruction.AcctDebitID = localFFSResultSet.getColumnString(4);
        localRecXferInstruction.AcctDebitType = localFFSResultSet.getColumnString(5);
        localRecXferInstruction.AcctCreditID = localFFSResultSet.getColumnString(6);
        localRecXferInstruction.AcctCreditType = localFFSResultSet.getColumnString(7);
        localRecXferInstruction.Frequency = localFFSResultSet.getColumnInt(8);
        localRecXferInstruction.StartDate = localFFSResultSet.getColumnInt(9);
        localRecXferInstruction.EndDate = localFFSResultSet.getColumnInt(10);
        localRecXferInstruction.InstanceCount = localFFSResultSet.getColumnInt(11);
        localRecXferInstruction.DateCreate = localFFSResultSet.getColumnString(12);
        localRecXferInstruction.CustomerID = localFFSResultSet.getColumnString(13);
        localRecXferInstruction.Status = localFFSResultSet.getColumnString(14);
        localRecXferInstruction.LogID = localFFSResultSet.getColumnString(15);
        localRecXferInstruction.FIID = localFFSResultSet.getColumnString(16);
        localRecXferInstruction.SubmittedBy = localFFSResultSet.getColumnString(17);
        localRecXferInstruction.fromBankID = localFFSResultSet.getColumnString(18);
        localRecXferInstruction.fromBranchID = localFFSResultSet.getColumnString(19);
        localRecXferInstruction.CurDef = localFFSResultSet.getColumnString(20);
        localRecXferInstruction.ToAmount = localFFSResultSet.getColumnString(21);
        localRecXferInstruction.ToAmtCurrency = localFFSResultSet.getColumnString(22);
        localRecXferInstruction.userAssignedAmount = UserAssignedAmount.getEnum(localFFSResultSet.getColumnInt(23));
        HashMap localHashMap = jdMethod_case(localRecXferInstruction.RecSrvrTID, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0)) {
          localRecXferInstruction.extraFields = localHashMap;
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** RecXferInstruction.getRecXferInstruction:" + FFSDebug.stackTrace(localException1));
      throw new FFSException(FFSDebug.stackTrace(localException1));
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
        FFSDebug.log("*** RecXferInstruction.getRecXferInstruction:" + FFSDebug.stackTrace(localException2));
      }
    }
    FFSDebug.log("RecXferInstruction.getRecXferInstruction: done", 6);
    return localRecXferInstruction;
  }
  
  public static RecXferInstruction getRecXferModel(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    RecXferInstruction localRecXferInstruction = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      String str = "RecXferInstruction:getRecXferModel";
      FFSDebug.log(str + "Start.", 6);
      localRecXferInstruction = getRecXferInstruction(paramFFSConnectionHolder, paramString);
      StringBuffer localStringBuffer = new StringBuffer("SELECT Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,DateCreate,DateToPost,CustomerID,Status,LogID,RecSrvrTID,FIID,SubmittedBy,LastModified,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount FROM BPW_XferInstruction WHERE RecSrvrTID=?");
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(paramString);
      localStringBuffer.append(" AND Status ='WILLPROCESSON'");
      localStringBuffer.append(" ORDER BY DateToPost");
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), localArrayList.toArray());
      int i = 2147483647;
      ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(localRecXferInstruction.FIID, "RECINTRATRN", paramString, paramFFSConnectionHolder);
      if (localFFSResultSet.getNextRow())
      {
        i = BPWUtil.getDateDBFormat(localFFSResultSet.getColumnString("DateToPost"));
      }
      else if (localScheduleInfo != null)
      {
        localScheduleInfo.NextInstanceDate = ScheduleInfo.computeFutureDate(localScheduleInfo.NextInstanceDate, localScheduleInfo.Frequency, 1);
        i = localScheduleInfo.NextInstanceDate;
        localScheduleInfo.CurInstanceNum += 1;
      }
      else
      {
        throw new FFSException(str + " Unable to determine the next instance date");
      }
      String[] arrayOfString = null;
      Integer localInteger1 = new Integer(i);
      Integer localInteger2 = new Integer(2147483647);
      if (localScheduleInfo.Perpetual != 1)
      {
        arrayOfString = ScheduleInfo.getPendingDatesByStartAndEndDate(localScheduleInfo, localInteger1.intValue(), localInteger2.intValue());
        localRecXferInstruction.InstanceCount = (arrayOfString.length + 1);
      }
      localRecXferInstruction.StartDate = i;
      localRecXferInstruction.setStatusCode(0);
      localRecXferInstruction.setStatusMsg("Success");
      FFSDebug.log(str + "Done.", 6);
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** RecXferInstruction.getRecXferModel:" + FFSDebug.stackTrace(localException1));
      throw new FFSException(FFSDebug.stackTrace(localException1));
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
        FFSDebug.log("*** RecXferInstruction.getRecXferModel:" + FFSDebug.stackTrace(localException2));
      }
    }
    FFSDebug.log("RecXferInstruction.getRecXferModel: done", 6);
    return localRecXferInstruction;
  }
  
  public static void modify(FFSConnectionHolder paramFFSConnectionHolder, String paramString, RecXferInstruction paramRecXferInstruction)
    throws FFSException, BPWException
  {
    FFSDebug.log("RecXferInstruction.modify: start, RecSrvrTID=" + paramString, 6);
    int i = 0;
    if (paramRecXferInstruction.userAssignedAmount != null) {
      i = paramRecXferInstruction.userAssignedAmount.getValue();
    }
    String str1 = "UPDATE BPW_RecXferInstruction SET Amount=?,BankID=?,BranchID=?,AcctDebitID=?,AcctDebitType=?,AcctCreditID=?,AcctCreditType=?,Frequency=?,StartDate=?,EndDate=?,InstanceCount=?,CustomerID=?,Status=?,LastModified=?, SubmittedBy=?,FromBankID=?,FromBranchID=?,CurDef=?,ToAmount=?, ToAmountCurrency=?, UserAssignedAmount=? WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { paramRecXferInstruction.Amount, paramRecXferInstruction.BankID, paramRecXferInstruction.BranchID, paramRecXferInstruction.AcctDebitID, paramRecXferInstruction.AcctDebitType, paramRecXferInstruction.AcctCreditID, paramRecXferInstruction.AcctCreditType, new Integer(paramRecXferInstruction.Frequency), new Integer(paramRecXferInstruction.StartDate), new Integer(paramRecXferInstruction.EndDate), new Integer(paramRecXferInstruction.InstanceCount), paramRecXferInstruction.CustomerID, paramRecXferInstruction.Status, FFSUtil.getDateString("yyyyMMddHHmmss"), paramRecXferInstruction.SubmittedBy, paramRecXferInstruction.fromBankID, paramRecXferInstruction.fromBranchID, paramRecXferInstruction.CurDef, paramRecXferInstruction.ToAmount, paramRecXferInstruction.ToAmtCurrency, new Integer(i), paramString };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** RecXferInstruction.modify failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("RecXferInstruction.modify: done", 6);
  }
  
  public static void modify(FFSConnectionHolder paramFFSConnectionHolder, RecTransferInfo paramRecTransferInfo)
    throws FFSException, BPWException
  {
    String str1 = paramRecTransferInfo.getSrvrTId();
    FFSDebug.log("RecXferInstruction.modify(RecTransferInfo): start, RecSrvrTID=" + str1, 6);
    String str2 = paramRecTransferInfo.getAmount();
    if (paramRecTransferInfo.getIsAmountEstimated()) {
      str2 = "0.00";
    }
    String str3 = paramRecTransferInfo.getToAmount();
    if (paramRecTransferInfo.getIsToAmountEstimated()) {
      str3 = "0.00";
    }
    int i = 0;
    if (paramRecTransferInfo.getUserAssignedAmount() != null) {
      i = paramRecTransferInfo.getUserAssignedAmount().getValue();
    }
    XferInstruction.jdMethod_byte(paramFFSConnectionHolder, paramRecTransferInfo);
    XferInstruction.jdMethod_case(paramFFSConnectionHolder, paramRecTransferInfo);
    String str4 = "UPDATE BPW_RecXferInstruction SET Amount=?,BankID=?,BranchID=?,AcctDebitID=?,AcctDebitType=?,AcctCreditID=?,AcctCreditType=?,Frequency=?,StartDate=?,EndDate=?,InstanceCount=?,CustomerID=?,Status=?,LastModified=?, SubmittedBy=?,FromBankID=?,FromBranchID=?,CurDef=?,ToAmount=?, ToAmountCurrency=?, UserAssignedAmount=? WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { str2, paramRecTransferInfo.getBankToRtn(), paramRecTransferInfo.getBranchID(), paramRecTransferInfo.getAccountFromNum(), paramRecTransferInfo.getAccountFromType(), paramRecTransferInfo.getAccountToNum(), paramRecTransferInfo.getAccountToType(), new Integer(FFSUtil.getFreqInt(paramRecTransferInfo.getFrequency())), new Integer(BPWUtil.getDateDBFormat(paramRecTransferInfo.getStartDate())), new Integer(BPWUtil.getDateDBFormat(paramRecTransferInfo.getEndDate())), new Integer(paramRecTransferInfo.getPmtsCount()), paramRecTransferInfo.getCustomerId(), paramRecTransferInfo.getPrcStatus(), FFSUtil.getDateString("yyyyMMddHHmmss"), paramRecTransferInfo.getSubmittedBy(), paramRecTransferInfo.getBankFromRtn(), null, paramRecTransferInfo.getAmountCurrency(), str3, paramRecTransferInfo.getToAmountCurrency(), new Integer(i), str1 };
    try
    {
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, str4, arrayOfObject);
      if (j > 0) {
        jdMethod_for(paramFFSConnectionHolder, paramRecTransferInfo);
      }
    }
    catch (Exception localException)
    {
      String str5 = "*** RecXferInstruction.modify failed:";
      FFSDebug.log(str5 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("RecXferInstruction.modify: done", 6);
  }
  
  public static String getStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException, BPWException
  {
    FFSDebug.log("RecXferInstruction.getStatus: start, RecSrvrTID=" + paramString, 6);
    String str1 = null;
    String str2 = "SELECT Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,Frequency,StartDate,EndDate,InstanceCount,DateCreate,CustomerID,Status,LogID,FIID,SubmittedBy,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount FROM BPW_RecXferInstruction WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        str1 = localFFSResultSet.getColumnString(14);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** RecXferInstruction.getStatus:" + localException1.toString());
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
        FFSDebug.log("*** RecXferInstruction.getStatus:" + localException2.toString());
      }
    }
    FFSDebug.log("RecXferInstruction.getStatus: done, status=" + str1, 6);
    return str1;
  }
  
  public static void updateStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException, BPWException
  {
    FFSDebug.log("RecXferInstruction.updateStatus: start, RecSrvrTID=" + paramString1, 6);
    String str1 = "UPDATE BPW_RecXferInstruction SET Status=? WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { paramString2, paramString1 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** RecXferInstruction.updateStatus failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("RecXferInstruction.updateStatus: done", 6);
  }
  
  public static void updateStatus(FFSConnectionHolder paramFFSConnectionHolder, RecTransferInfo paramRecTransferInfo)
    throws FFSException, BPWException
  {
    String str1 = paramRecTransferInfo.getSrvrTId();
    FFSDebug.log("RecXferInstruction.updateStatus(RecTransferInfo): start, RecSrvrTID=" + str1, 6);
    String str2 = "UPDATE BPW_RecXferInstruction SET Status=? WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { paramRecTransferInfo.getPrcStatus(), str1 };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
      if (i > 0) {
        jdMethod_for(paramFFSConnectionHolder, paramRecTransferInfo);
      }
    }
    catch (Exception localException)
    {
      String str3 = "*** RecXferInstruction.updateStatus(RecTransferInfo) failed:";
      FFSDebug.log(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("RecXferInstruction.updateStatus(RecTransferInfo): done", 6);
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException, BPWException
  {
    FFSDebug.log("RecXferInstruction.delete: start, RecSrvrTID=" + paramString, 6);
    String str1 = "DELETE FROM BPW_RecXferInstruction WHERE RecSrvrTID=?";
    Object[] arrayOfObject = { paramString };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** RecXferInstruction.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("RecXferInstruction.delete: done", 6);
  }
  
  public static String[] getOldRecSrvrTID(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    Vector localVector = new Vector();
    FFSDebug.log("RecXferInstruction.getOldRecSrvrTID: start ageDay=" + paramInt, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd00");
    String str1 = localSimpleDateFormat.format(localCalendar.getTime());
    int i = Integer.parseInt(str1);
    String str2 = "SELECT RecSrvrTID FROM BPW_RecXferInstruction WHERE EndDate <= ? AND Status IN (?,?) ";
    Object[] arrayOfObject = { new Integer(i), "POSTEDON", "CANCELEDON" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        String str3 = localFFSResultSet.getColumnString(1);
        localVector.addElement(str3);
      }
    }
    catch (Exception localException1)
    {
      String str4 = "*** RecXferInstruction.getOldRecSrvrTID failed:";
      System.out.println(str4 + localException1.toString());
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
        FFSDebug.log("*** RecXferInstruction.getOldRecSrvrTID:" + localException2.toString());
      }
    }
    FFSDebug.log("RecXferInstruction.getOldRecSrvrTID: rows=" + localVector.size(), 6);
    if (localVector.isEmpty()) {
      return null;
    }
    return (String[])localVector.toArray(new String[0]);
  }
  
  public static String[] getOldSrvrTIDByCustId(FFSConnectionHolder paramFFSConnectionHolder, int paramInt, String paramString)
    throws FFSException
  {
    String str1 = "RecXferInstruction.getOldSrvrTIDByCustId: ";
    Vector localVector = new Vector();
    FFSDebug.log(str1, "start ageDay=" + paramInt, 6);
    String str2 = "SELECT RecSrvrTID FROM BPW_RecXferInstruction WHERE EndDate <= ? AND CustomerID = ? AND Status IN (?,?)";
    Object[] arrayOfObject = { new Integer(paramInt), paramString, "POSTEDON", "CANCELEDON" };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        String str3 = localFFSResultSet.getColumnString(1);
        localVector.addElement(str3);
      }
    }
    catch (Exception localException1)
    {
      String str4 = "*** " + str1 + " failed:";
      System.out.println(str4 + localException1.toString());
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
        FFSDebug.log("*** ", str1 + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1, "rows=" + localVector.size(), 6);
    if (localVector.isEmpty()) {
      return null;
    }
    return (String[])localVector.toArray(new String[0]);
  }
  
  public static RecXferInstruction[] getHist(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4, long paramLong)
    throws Exception
  {
    FFSDebug.log("RecXferInstruction.getHist start, custID=" + paramString1 + ",acctID=" + paramString2 + ",startDate=" + paramInt1 + ",endDate=" + paramInt2, 6);
    RecXferInstruction localRecXferInstruction = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    long l = 0L;
    String str = "SELECT RecSrvrTID, Frequency, StartDate, EndDate, InstanceCount, Status,FIID FROM BPW_RecXferInstruction WHERE CustomerID = ? AND StartDate <= ? AND (EndDate >= ? OR EndDate = -1) ";
    if ((paramString2 != null) && (paramString2.trim().length() > 0)) {
      str = str + "AND AcctDebitID = '" + paramString2 + "' ";
    }
    str = str + " AND Status = 'WILLPROCESSON' ";
    boolean bool = true;
    if ((paramString3 != null) && (paramString3.length() > 0)) {
      bool = DBUtil.checkStatusQuery(paramString3, "WILLPROCESSON");
    }
    if (bool) {
      try
      {
        Object[] arrayOfObject = { paramString1, new Integer(paramInt2), new Integer(paramInt1) };
        if (wV.get(paramString4) == null)
        {
          localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
          wV.put(paramString4, localFFSResultSet);
        }
        else
        {
          localFFSResultSet = (FFSResultSet)wV.get(paramString4);
        }
        while (localFFSResultSet.getNextRow())
        {
          localRecXferInstruction = new RecXferInstruction();
          localRecXferInstruction.RecSrvrTID = localFFSResultSet.getColumnString(1);
          localRecXferInstruction.Frequency = localFFSResultSet.getColumnInt(2);
          localRecXferInstruction.StartDate = localFFSResultSet.getColumnInt(3);
          localRecXferInstruction.EndDate = localFFSResultSet.getColumnInt(4);
          localRecXferInstruction.InstanceCount = localFFSResultSet.getColumnInt(5);
          localRecXferInstruction.Status = localFFSResultSet.getColumnString(6);
          localRecXferInstruction.FIID = localFFSResultSet.getColumnString(7);
          HashMap localHashMap = jdMethod_case(localRecXferInstruction.RecSrvrTID, paramFFSConnectionHolder);
          if ((localHashMap != null) && (localHashMap.size() != 0)) {
            localRecXferInstruction.extraFields = localHashMap;
          }
          localArrayList.add(localRecXferInstruction);
          l += 1L;
          if (l == paramLong)
          {
            RecXferInstruction[] arrayOfRecXferInstruction = (RecXferInstruction[])localArrayList.toArray(new RecXferInstruction[0]);
            return arrayOfRecXferInstruction;
          }
        }
        localFFSResultSet.close();
        wV.remove(paramString4);
      }
      catch (Exception localException)
      {
        localException = localException;
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
        if (wV != null) {
          wV.remove(paramString4);
        }
        throw new Exception(localException.toString() + FFSDebug.stackTrace(localException));
      }
      finally {}
    }
    FFSDebug.log("RecXferInstruction.getHist done, rows=" + l, 6);
    return (RecXferInstruction[])localArrayList.toArray(new RecXferInstruction[0]);
  }
  
  public static boolean isBatchDone(String paramString)
  {
    return !wV.containsKey(paramString);
  }
  
  public static void clearBatch(String paramString)
  {
    try
    {
      if (wV.containsKey(paramString))
      {
        FFSResultSet localFFSResultSet = (FFSResultSet)wV.get(paramString);
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
        wV.remove(paramString);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PmtInstruction.clearBatch failed:" + localException.toString());
    }
  }
  
  private static HashMap jdMethod_case(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("RecXferInstruction.getXferExtraInfo start :RecSrvrTID = " + paramString, 6);
    String str = null;
    HashMap localHashMap = null;
    if (paramFFSConnectionHolder == null)
    {
      str = "*** RecXferInstruction.getXferExtraInfo failed:ConnectionHolder is null ";
      FFSDebug.log(str, 0);
      throw new BPWException(str);
    }
    try
    {
      localHashMap = BPWRecXferExtraInfo.getHashMap(paramString, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      str = "*** RecXferInstruction.getXferExtraInfo failed:" + localException.toString();
      FFSDebug.log(str, 0);
      throw new BPWException(str);
    }
    FFSDebug.log("RecXferInstruction.getXferExtraInfo end :", 6);
    return localHashMap;
  }
  
  public static RecXferInstruction[] getHist(FFSConnectionHolder paramFFSConnectionHolder, BPWExtdHist paramBPWExtdHist, int paramInt1, int paramInt2)
    throws Exception
  {
    String str1 = "";
    String str2 = paramBPWExtdHist.getAcctId();
    String str3 = paramBPWExtdHist.getBankId();
    String str4 = paramBPWExtdHist.getFromBankId();
    String str5 = paramBPWExtdHist.getAcctType();
    String str6 = paramBPWExtdHist.getAcctCreditId();
    String str7 = paramBPWExtdHist.getAcctCreditType();
    String str8 = paramBPWExtdHist.getRecSrvrTid();
    String str9 = paramBPWExtdHist.getRequiredStatus();
    String str10 = paramBPWExtdHist.getAcctOperator();
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    String str11 = "SELECT RecSrvrTID, Status, FIID  FROM BPW_RecXferInstruction WHERE CustomerID = ? AND StartDate <= ? AND (EndDate >= ? OR EndDate = -1) ";
    if ((!str10.trim().equals("AND")) && (!str10.trim().equals("OR"))) {
      str10 = "AND";
    }
    if ((str2 != null) && (str2.trim().length() != 0)) {
      i = 1;
    }
    if ((str6 != null) && (str6.trim().length() != 0)) {
      j = 1;
    }
    if ((str5 != null) && (str5.trim().length() != 0)) {
      k = 1;
    }
    if ((str7 != null) && (str7.trim().length() != 0)) {
      m = 1;
    }
    if ((i != 0) && (j != 0))
    {
      if ((k != 0) && (m != 0)) {
        str11 = str11 + " AND ((AcctDebitID = '" + str2 + "'" + " AND AcctDebitType = '" + str5 + "'" + ") " + str10 + " (AcctCreditID = '" + str6 + "'" + " AND AcctCreditType = '" + str7 + "'" + "))";
      } else {
        str11 = str11 + " AND (AcctDebitID = '" + str2 + "' " + str10 + " AcctCreditID = '" + str6 + "'" + ")";
      }
    }
    else if (i != 0)
    {
      if (k != 0) {
        str11 = str11 + " AND AcctDebitID = '" + str2 + "'" + " AND AcctDebitType = '" + str5 + "'";
      } else {
        str11 = str11 + " AND AcctDebitID = '" + str2 + "'";
      }
    }
    else if (j != 0) {
      if (m != 0) {
        str11 = str11 + " AND (AcctCreditID = '" + str6 + "'" + " AND AcctCreditType = '" + str7 + "'" + ")";
      } else {
        str11 = str11 + " AND (AcctCreditID = '" + str6 + "'" + ")";
      }
    }
    String str12 = "";
    if ((str9 != null) && (str9.length() > 0)) {
      str12 = DBUtil.buildStatusQueryString(str9);
    }
    str11 = str11 + str12;
    if ((str3 != null) && (!str3.trim().equals(""))) {
      str1 = str1 + " And BankID = '" + str3 + "' ";
    }
    if ((str4 != null) && (!str4.trim().equals(""))) {
      str1 = str1 + " And FromBankID = '" + str4 + "' ";
    }
    if ((str8 != null) && (!str8.trim().equals(""))) {
      str1 = str1 + DBUtil.buildWhereClauseStringColumn(str8, "RecSrvrTID");
    }
    if ((str1 != null) && (!str1.trim().equals(""))) {
      str11 = str11 + str1;
    }
    String str13 = "";
    String str14 = "";
    ArrayList localArrayList = null;
    if ((paramBPWExtdHist.getExtraInfo() != null) && (paramBPWExtdHist.getExtraInfo().size() > 0))
    {
      localObject1 = paramBPWExtdHist.getExtraInfo().keySet();
      if (localObject1 == null) {
        return null;
      }
      localObject2 = ((Set)localObject1).iterator();
      if (localObject2 == null) {
        return null;
      }
      int n = 1;
      String str15 = "";
      while (((Iterator)localObject2).hasNext())
      {
        str14 = (String)((Iterator)localObject2).next();
        localArrayList = (ArrayList)paramBPWExtdHist.getExtraInfo().get(str14);
        if (n != 0) {
          n = 0;
        } else {
          str13 = str13 + " And ";
        }
        str15 = "";
        for (int i1 = 0; i1 < localArrayList.size(); i1++) {
          if (i1 == 0) {
            str15 = str15 + " ( Name ='" + str14 + "' And Value = '" + localArrayList.get(i1) + "' )";
          } else {
            str15 = str15 + " OR ( Name ='" + str14 + "' And Value = '" + localArrayList.get(i1) + "' )";
          }
        }
        if ((str15 != null) && (!str15.trim().equals("")))
        {
          str15 = "(" + str15 + ")";
          str13 = str13 + str15;
        }
      }
    }
    Object localObject1 = null;
    if ((str13 != null) && (!str13.trim().equals("")))
    {
      localObject1 = wT + str13 + " )";
      str11 = str11 + (String)localObject1;
    }
    localObject1 = null;
    Object localObject2 = { paramBPWExtdHist.getCustId(), new Integer(paramInt2), new Integer(paramInt1) };
    return jdMethod_if(paramFFSConnectionHolder, str11, (Object[])localObject2, paramBPWExtdHist.getHistId(), paramBPWExtdHist.getPageSize());
  }
  
  private static RecXferInstruction[] jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, Object[] paramArrayOfObject, String paramString2, long paramLong)
    throws Exception
  {
    FFSDebug.log("RecXferInstruction.executeHistQuery start", 6);
    RecXferInstruction localRecXferInstruction = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    long l = 0L;
    HashMap localHashMap = null;
    try
    {
      if (wV.get(paramString2) == null)
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString1, paramArrayOfObject);
        wV.put(paramString2, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)wV.get(paramString2);
      }
      while (localFFSResultSet.getNextRow())
      {
        localRecXferInstruction = new RecXferInstruction();
        localRecXferInstruction.RecSrvrTID = localFFSResultSet.getColumnString("RecSrvrTID");
        localRecXferInstruction.Status = localFFSResultSet.getColumnString("Status");
        localRecXferInstruction.FIID = localFFSResultSet.getColumnString("FIID");
        localHashMap = BPWRecXferExtraInfo.getHashMap(localRecXferInstruction.RecSrvrTID, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0)) {
          localRecXferInstruction.extraFields = localHashMap;
        }
        localArrayList.add(localRecXferInstruction);
        l += 1L;
        if (l == paramLong)
        {
          RecXferInstruction[] arrayOfRecXferInstruction = (RecXferInstruction[])localArrayList.toArray(new RecXferInstruction[0]);
          return arrayOfRecXferInstruction;
        }
      }
      localFFSResultSet.close();
      wV.remove(paramString2);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      if (wV != null) {
        wV.remove(paramString2);
      }
      throw new Exception(localException.toString() + FFSDebug.stackTrace(localException));
    }
    finally {}
    FFSDebug.log("RecXferInstruction.executeHistQuery done, rows=" + l, 6);
    return (RecXferInstruction[])localArrayList.toArray(new RecXferInstruction[0]);
  }
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "RecXferInstruction.cleanup ";
    int i = 0;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd00");
    String str2 = localSimpleDateFormat.format(localCalendar.getTime());
    int j = Integer.parseInt(str2);
    String[] arrayOfString = null;
    if ((paramString != null) && (paramString.trim().length() != 0)) {
      arrayOfString = getOldSrvrTIDByCustId(paramFFSConnectionHolder, j, paramString);
    } else {
      arrayOfString = getOldRecSrvrTID(paramFFSConnectionHolder, paramInt);
    }
    if (arrayOfString != null) {
      for (int k = 0; k < arrayOfString.length; k++)
      {
        EventInfoLog.delete(paramFFSConnectionHolder, arrayOfString[k], "RECINTRATRN");
        delete(paramFFSConnectionHolder, arrayOfString[k]);
        RecSrvrTrans.cancelRecSrvrTrans(arrayOfString[k], paramFFSConnectionHolder);
        RecSrvrTIDToSrvrTID.deleteByRecSrvrTID(paramFFSConnectionHolder, arrayOfString[k]);
      }
    }
    FFSDebug.log(str1 + "end, No of rows deleted = " + i, 6);
    return i;
  }
  
  public static void createSessionDataRecXfer(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "RecXferInstruction.createSessionDataRecXfer";
    FFSDebug.log(str1 + " : start", 6);
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      RecTransferInfo localRecTransferInfo = null;
      String str3 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str3 == null) || (str3.trim().length() == 0))
      {
        str3 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str3);
      }
      Object localObject1 = paramArrayList.get(0);
      int k = 0;
      int m = 0;
      if ((localObject1 instanceof Integer)) {
        k = ((Integer)localObject1).intValue();
      } else {
        try
        {
          k = Integer.parseInt((String)localObject1);
        }
        catch (NumberFormatException localNumberFormatException1) {}
      }
      localObject1 = paramArrayList.get(1);
      if ((localObject1 instanceof Integer)) {
        m = ((Integer)localObject1).intValue();
      } else {
        try
        {
          m = Integer.parseInt((String)localObject1);
        }
        catch (NumberFormatException localNumberFormatException2) {}
      }
      int n = DBUtil.getCurrentStartDate();
      int i1 = k;
      int i2 = (i1 - n) / 10000;
      if (i2 >= 0)
      {
        StringBuffer localStringBuffer = new StringBuffer("SELECT RecSrvrTID, CustomerID, Amount, FIID, AcctDebitID, AcctDebitType,  AcctCreditID, AcctCreditType, Frequency, StartDate, EndDate, Status, CurDef,  ToAmount, ToAmountCurrency, UserAssignedAmount, SubmittedBy  FROM BPW_RecXferInstruction WHERE StartDate <= cast (? as Integer) AND (EndDate >= cast (? as Integer) OR EndDate = -1) ");
        if (paramString != null) {
          localStringBuffer.append(paramString);
        }
        String[] arrayOfString1 = (String[])localHashMap2.get("StatusList");
        if ((arrayOfString1 != null) && (arrayOfString1.length > 0))
        {
          localStringBuffer.append(" AND Status IN (?");
          paramArrayList.add(arrayOfString1[0]);
          for (i3 = 1; i3 < arrayOfString1.length; i3++)
          {
            localStringBuffer.append(", ?");
            paramArrayList.add(arrayOfString1[i3]);
          }
          localStringBuffer.append(") ");
        }
        localStringBuffer.append(" ORDER BY CAST(RecSrvrTID AS INTEGER) ASC");
        FFSDebug.log(str1 + "Sql Statement: " + localStringBuffer.toString(), 6);
        for (int i3 = 0; i3 < paramArrayList.size(); i3++) {
          FFSDebug.log(str1 + " Sql Param:" + i3 + " :" + String.valueOf(paramArrayList.get(i3)), 6);
        }
        Object[] arrayOfObject = paramArrayList.toArray();
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
        int i4 = aj();
        while (localFFSResultSet.getNextRow())
        {
          j++;
          if (j > i4)
          {
            paramPagingInfo.setStatusCode(28020);
            paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
            break;
          }
          localRecTransferInfo = new RecTransferInfo();
          a(localRecTransferInfo, localFFSResultSet);
          ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(localRecTransferInfo.getFIId(), "RECINTRATRN", localRecTransferInfo.getSrvrTId(), paramFFSConnectionHolder);
          if (localScheduleInfo != null)
          {
            localScheduleInfo.InstanceCount -= localScheduleInfo.CurInstanceNum - 1;
            localScheduleInfo.CurInstanceNum = 1;
            String[] arrayOfString2 = ScheduleInfo.getPendingDatesByStartAndEndDate(localScheduleInfo, m, k);
            for (int i5 = 0; i5 < arrayOfString2.length; i5++)
            {
              String str4 = arrayOfString2[i5].substring(0, 10);
              j++;
              if (j % i == 0) {
                paramFFSConnectionHolder.conn.commit();
              }
              localRecTransferInfo.setDateToPost(str4);
              TransferTempHist.createTempHist(paramFFSConnectionHolder, str3, j, localRecTransferInfo, true);
              if (j % i == 0) {
                paramFFSConnectionHolder.conn.commit();
              }
            }
          }
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
  
  public static void createSessionDataRecTransferModel(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "RecXferInstruction.createSessionDataRecTransferModel";
    FFSDebug.log(str1 + " : start", 6);
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      RecTransferInfo localRecTransferInfo = null;
      String str3 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str3 == null) || (str3.trim().length() == 0))
      {
        str3 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str3);
      }
      StringBuffer localStringBuffer = new StringBuffer("SELECT RecSrvrTID, CustomerID, Amount, FIID, AcctDebitID, AcctDebitType,  AcctCreditID, AcctCreditType, Frequency, StartDate, EndDate, Status, CurDef,  ToAmount, ToAmountCurrency, UserAssignedAmount, SubmittedBy  FROM BPW_RecXferInstruction WHERE StartDate <= cast (? as Integer) AND (EndDate >= cast (? as Integer) OR EndDate = -1) ");
      if (paramString != null) {
        localStringBuffer.append(paramString);
      }
      String[] arrayOfString = (String[])localHashMap2.get("StatusList");
      if ((arrayOfString != null) && (arrayOfString.length > 0))
      {
        localStringBuffer.append(" AND Status IN (?");
        paramArrayList.add(arrayOfString[0]);
        for (k = 1; k < arrayOfString.length; k++)
        {
          localStringBuffer.append(", ?");
          paramArrayList.add(arrayOfString[k]);
        }
        localStringBuffer.append(") ");
      }
      localStringBuffer.append(" ORDER BY CAST(RecSrvrTID AS INTEGER) ASC");
      FFSDebug.log(str1 + "Sql Statement: " + localStringBuffer.toString(), 6);
      for (int k = 0; k < paramArrayList.size(); k++) {
        FFSDebug.log(str1 + " Sql Param:" + k + " :" + String.valueOf(paramArrayList.get(k)), 6);
      }
      Object[] arrayOfObject = paramArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      int m = aj();
      while (localFFSResultSet.getNextRow())
      {
        j++;
        if (j > m)
        {
          paramPagingInfo.setStatusCode(28020);
          paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
          break;
        }
        localRecTransferInfo = new RecTransferInfo();
        a(localRecTransferInfo, localFFSResultSet);
        localRecTransferInfo.setTransferType("Recmodel");
        ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(localRecTransferInfo.getFIId(), "RECINTRATRN", localRecTransferInfo.getSrvrTId(), paramFFSConnectionHolder);
        if (localScheduleInfo != null)
        {
          String str4 = localScheduleInfo.getFirstInstanceDateInRange(Integer.parseInt(paramPagingInfo.getStartDate()), Integer.parseInt(paramPagingInfo.getEndDate()));
          if (str4 != null) {
            localRecTransferInfo.setDateToPost(str4);
          }
        }
        else
        {
          TransferTempHist.createTempHist(paramFFSConnectionHolder, str3, j, localRecTransferInfo, true);
          if (j % i == 0) {
            paramFFSConnectionHolder.conn.commit();
          }
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
  
  private static int aj()
  {
    if (wU == 0)
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      String str = localPropertyConfig.otherProperties.getProperty("bpw.paging.maximum.sessionsize", "1000");
      wU = Integer.parseInt(str);
    }
    return wU;
  }
  
  private static void a(RecTransferInfo paramRecTransferInfo, FFSResultSet paramFFSResultSet)
    throws Exception
  {
    paramRecTransferInfo.setSrvrTId(paramFFSResultSet.getColumnString("RecSrvrTID"));
    paramRecTransferInfo.setTransferType("Repetitive");
    paramRecTransferInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerID"));
    paramRecTransferInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    paramRecTransferInfo.setFIId(paramFFSResultSet.getColumnString("FIID"));
    paramRecTransferInfo.setAmount(paramFFSResultSet.getColumnString("Amount"));
    paramRecTransferInfo.setAmountCurrency(paramFFSResultSet.getColumnString("CurDef"));
    paramRecTransferInfo.setToAmount(paramFFSResultSet.getColumnString("ToAmount"));
    paramRecTransferInfo.setToAmountCurrency(paramFFSResultSet.getColumnString("ToAmountCurrency"));
    paramRecTransferInfo.setUserAssignedAmount(UserAssignedAmount.getEnum(paramFFSResultSet.getColumnInt("UserAssignedAmount")));
    paramRecTransferInfo.setAccountFromNum(paramFFSResultSet.getColumnString("AcctDebitID"));
    paramRecTransferInfo.setAccountFromType(paramFFSResultSet.getColumnString("AcctDebitType"));
    paramRecTransferInfo.setAccountToNum(paramFFSResultSet.getColumnString("AcctCreditID"));
    paramRecTransferInfo.setAccountToType(paramFFSResultSet.getColumnString("AcctCreditType"));
    paramRecTransferInfo.setStartDate(paramFFSResultSet.getColumnString("StartDate"));
    paramRecTransferInfo.setEndDate(paramFFSResultSet.getColumnString("EndDate"));
    paramRecTransferInfo.setPrcStatus(paramFFSResultSet.getColumnString("Status"));
    paramRecTransferInfo.setFrequency(paramFFSResultSet.getColumnString("Frequency"));
    String str1 = paramRecTransferInfo.getFrequency();
    try
    {
      int i = Integer.parseInt(str1);
      str2 = FFSUtil.getFreqString(i);
      paramRecTransferInfo.setFrequency(str2);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "populateRecTransferInfo getFreqString failed : ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localThrowable), 0);
    }
    paramRecTransferInfo.setTransferDest("INTERNAL");
  }
  
  private static String a(AccountTransactions paramAccountTransactions, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "getSQLFromCriteria";
    FFSDebug.log(str1, ": done", 6);
    StringBuffer localStringBuffer = new StringBuffer(paramString);
    try
    {
      ExtTransferAcctInfo localExtTransferAcctInfo = paramAccountTransactions.getSearchForAccount();
      localStringBuffer.append(" WHERE ");
      if (paramAccountTransactions.getAccountScope().equals("FromAccount"))
      {
        localStringBuffer.append(" CustomerID = ?");
        paramArrayList.add(localExtTransferAcctInfo.getCustomerId());
        localStringBuffer.append(" AND AcctDebitID = ?");
        paramArrayList.add(localExtTransferAcctInfo.getAcctNum());
        localStringBuffer.append(" AND AcctDebitType = ?");
        paramArrayList.add(localExtTransferAcctInfo.getAcctType());
      }
      else if (paramAccountTransactions.getAccountScope().equals("ToAccount"))
      {
        localStringBuffer.append(" CustomerID = ?");
        paramArrayList.add(localExtTransferAcctInfo.getCustomerId());
        localStringBuffer.append(" AND AcctDebitID = ?");
        paramArrayList.add(localExtTransferAcctInfo.getAcctNum());
        localStringBuffer.append(" AND AcctDebitType = ?");
        paramArrayList.add(localExtTransferAcctInfo.getAcctType());
      }
      else if (paramAccountTransactions.getAccountScope().equals("FromAccountAndToAccount"))
      {
        localStringBuffer.append("( CustomerID = ?");
        paramArrayList.add(localExtTransferAcctInfo.getCustomerId());
        localStringBuffer.append(" AND AcctDebitID = ?");
        paramArrayList.add(localExtTransferAcctInfo.getAcctNum());
        localStringBuffer.append(" AND AcctDebitType = ? )");
        paramArrayList.add(localExtTransferAcctInfo.getAcctType());
        localStringBuffer.append(" OR (CustomerID = ?");
        paramArrayList.add(localExtTransferAcctInfo.getCustomerId());
        localStringBuffer.append(" AND AcctDebitID = ?");
        paramArrayList.add(localExtTransferAcctInfo.getAcctNum());
        localStringBuffer.append(" AND AcctDebitType = ? )");
        paramArrayList.add(localExtTransferAcctInfo.getAcctType());
      }
      if ((paramAccountTransactions.getTransactionType().equals("TRANSFER")) || (paramAccountTransactions.getTransactionType().equals("TEMPLATE")) || (paramAccountTransactions.getTransactionType().equals("TRANSFERANDTEMPLATE")))
      {
        localStringBuffer.append(" AND Status IN (?, ?, ?, ?, ?, ?, ?)");
        paramArrayList.add("WILLPROCESSON");
        paramArrayList.add("BATCH_INPROCESS");
        paramArrayList.add("IMMED_INPROCESS");
        paramArrayList.add("INPROCESS");
        paramArrayList.add("FUNDSPROCESSED");
        paramArrayList.add("APPROVAL_PENDING");
        paramArrayList.add("APPROVAL_REJECTED");
      }
      FFSDebug.log(str1, "getSQLFromCriteria:" + paramString, 6);
      return localStringBuffer.toString();
    }
    catch (Exception localException)
    {
      String str2 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localException, str2);
    }
  }
  
  public static ArrayList getPendingTransfersByAccount(AccountTransactions paramAccountTransactions)
    throws FFSException
  {
    String str1 = "Transfer.getTransfersByAccount";
    FFSDebug.log(str1, ": start", 6);
    String str2 = null;
    ArrayList localArrayList1 = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList2 = new ArrayList();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      str2 = "SELECT RecSrvrTID,Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,Frequency,StartDate,EndDate,InstanceCount,DateCreate,CustomerID,Status,LogID,FIID,SubmittedBy,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount FROM BPW_RecXferInstruction";
      str2 = a(paramAccountTransactions, str2, localArrayList1);
      Object[] arrayOfObject = localArrayList1.toArray();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localObject1 = new RecTransferInfo();
        a((RecTransferInfo)localObject1, localFFSResultSet);
        localArrayList2.add(localObject1);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1, str3, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
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
        try
        {
          if (localFFSConnectionHolder != null) {
            DBUtil.freeConnection(localFFSConnectionHolder.conn);
          }
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log(str1, " Failed to free connection", 6);
        }
      }
      catch (Exception localException)
      {
        String str4 = str1 + " failed: ";
        String str5 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str4, str5, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return localArrayList2;
  }
  
  public static long getPendingTransfersCountByAccount(AccountTransactions paramAccountTransactions)
    throws FFSException
  {
    String str1 = "Transfer.getTransfersCountByAccount";
    FFSDebug.log(str1, ": start", 6);
    String str2 = null;
    ArrayList localArrayList = new ArrayList();
    long l = 0L;
    FFSResultSet localFFSResultSet = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      str2 = "SELECT count(*) as TRANSFERCOUNT FROM BPW_RecXferInstruction";
      str2 = a(paramAccountTransactions, str2, localArrayList);
      Object[] arrayOfObject = localArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str2, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        l = localFFSResultSet.getColumnLong("TRANSFERCOUNT");
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable1)
    {
      String str3 = str1 + " failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str3, str4, 0);
      throw new FFSException(localThrowable1, str3);
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
        try
        {
          if (localFFSConnectionHolder != null) {
            DBUtil.freeConnection(localFFSConnectionHolder.conn);
          }
        }
        catch (Throwable localThrowable2)
        {
          FFSDebug.log(str1, " Failed to free connection", 6);
        }
      }
      catch (Exception localException)
      {
        String str5 = str1 + " failed: ";
        String str6 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str5, str6, 0);
      }
    }
    FFSDebug.log(str1, ": done", 6);
    return l;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.RecXferInstruction
 * JD-Core Version:    0.7.0.1
 */