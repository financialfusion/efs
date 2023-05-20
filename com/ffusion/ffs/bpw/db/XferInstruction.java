package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.interfaces.AccountTransactions;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWExtdHist;
import com.ffusion.ffs.bpw.interfaces.BPWInfoBase;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.TransferIntraMap;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1Aggregate;
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
import java.util.StringTokenizer;
import java.util.Vector;

public class XferInstruction
  extends BPWInfoBase
  implements FFSConst, DBConsts, BPWResource
{
  public String SrvrTID;
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
  public String DateToPost;
  public String DateCreate;
  public String Status;
  public String LogID;
  public String RecSrvrTID;
  public String SubmittedBy;
  public String LastModified;
  public String fromBankID;
  public String fromBranchID;
  private static Hashtable w0 = new Hashtable();
  private static Hashtable w2 = new Hashtable();
  private static final String wZ = "SELECT SrvrTID, BankID, BranchID, CustomerID, Amount, AcctDebitID, AcctDebitType, AcctCreditID, AcctCreditType, DateToPost, Status, RecSrvrTID, FIID, FromBankID, FromBranchID, CurDef, SubmittedBy, ToAmount, ToAmountCurrency, UserAssignedAmount, FROM BPW_XferInstruction WHERE CustomerID = ?";
  private static final String w3 = " ORDER BY CAST(SrvrTID AS INTEGER) ASC";
  private static final String w1 = " ORDER BY CAST(SrvrTID AS INTEGER) DESC";
  private static String wY = " AND SrvrTID in (SELECT SrvrTID from BPW_XferExtraInfo where ";
  private static int wX = 0;
  
  public XferInstruction() {}
  
  public XferInstruction(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17)
  {
    this(paramString1, paramString2, paramString3, paramString4, null, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramString13, paramString14, paramString15, paramString16, paramString17, paramString5, paramString6);
  }
  
  public XferInstruction(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19)
  {
    this(paramString1, paramString2, paramString3, paramString4, null, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramString13, paramString14, paramString15, paramString16, paramString17, paramString18, paramString19);
  }
  
  public XferInstruction(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19, String paramString20)
  {
    this(paramString1, paramString2, paramString3, paramString4, paramString5, "0.00", null, UserAssignedAmount.SINGLE, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, paramString13, paramString14, paramString15, paramString16, paramString17, paramString18, paramString19, paramString20);
  }
  
  public XferInstruction(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, UserAssignedAmount paramUserAssignedAmount, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12, String paramString13, String paramString14, String paramString15, String paramString16, String paramString17, String paramString18, String paramString19, String paramString20, String paramString21, String paramString22)
  {
    this.SrvrTID = paramString1;
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
    this.DateCreate = paramString14;
    this.DateToPost = paramString15;
    this.Status = paramString16;
    this.LogID = paramString17;
    this.RecSrvrTID = paramString18;
    this.SubmittedBy = paramString19;
    this.LastModified = paramString20;
    this.fromBankID = paramString21;
    this.fromBranchID = paramString22;
  }
  
  public static String create(FFSConnectionHolder paramFFSConnectionHolder, XferInstruction paramXferInstruction)
    throws FFSException
  {
    FFSDebug.log("XferInstruction.create: start", 6);
    String str1 = "INSERT INTO BPW_XferInstruction (SrvrTID,CustomerID,FIID,Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,DateCreate,DateToPost,Status,LogID,RecSrvrTID,SubmittedBy,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String str2;
    try
    {
      str2 = DBUtil.getNextIndexString("SrvrTID");
    }
    catch (BPWException localBPWException)
    {
      localObject = "*** XferInstruction.create failed:";
      FFSDebug.log((String)localObject + localBPWException.toString());
      throw new FFSException(localBPWException.toString());
    }
    int i = 0;
    if (paramXferInstruction.userAssignedAmount != null) {
      i = paramXferInstruction.userAssignedAmount.getValue();
    }
    Object localObject = { str2, paramXferInstruction.CustomerID, paramXferInstruction.FIID, paramXferInstruction.Amount, paramXferInstruction.BankID, paramXferInstruction.BranchID, paramXferInstruction.AcctDebitID, paramXferInstruction.AcctDebitType, paramXferInstruction.AcctCreditID, paramXferInstruction.AcctCreditType, DBUtil.getCurrentLogDate(), paramXferInstruction.DateToPost, paramXferInstruction.Status, paramXferInstruction.LogID, paramXferInstruction.RecSrvrTID, paramXferInstruction.SubmittedBy, paramXferInstruction.fromBankID, paramXferInstruction.fromBranchID, paramXferInstruction.CurDef, paramXferInstruction.ToAmount, paramXferInstruction.ToAmtCurrency, new Integer(i) };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, (Object[])localObject);
    }
    catch (Exception localException)
    {
      String str3 = "*** XferInstruction.create: failed:";
      FFSDebug.log(str3 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("XferInstruction.create: done, SrvrTID=" + str2, 6);
    return str2;
  }
  
  public static String create(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    FFSDebug.log("XferInstruction.create: start", 6);
    String str1 = "INSERT INTO BPW_XferInstruction (SrvrTID,CustomerID,FIID,Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,DateCreate,DateToPost,Status,LogID,RecSrvrTID,SubmittedBy,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount ) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    String str2;
    try
    {
      str2 = DBUtil.getNextIndexString("SrvrTID");
    }
    catch (BPWException localBPWException)
    {
      str4 = "*** XferInstruction.create failed:";
      FFSDebug.log(str4 + localBPWException.toString());
      throw new FFSException(localBPWException.toString());
    }
    String str3 = paramTransferInfo.getAmount();
    if (paramTransferInfo.getIsAmountEstimated()) {
      str3 = "0.0";
    }
    String str4 = paramTransferInfo.getToAmount();
    if (paramTransferInfo.getIsToAmountEstimated()) {
      str4 = "0.00";
    }
    int i = 0;
    if (paramTransferInfo.getUserAssignedAmount() != null) {
      i = paramTransferInfo.getUserAssignedAmount().getValue();
    }
    jdMethod_byte(paramFFSConnectionHolder, paramTransferInfo);
    jdMethod_case(paramFFSConnectionHolder, paramTransferInfo);
    Object[] arrayOfObject = { str2, paramTransferInfo.getCustomerId(), paramTransferInfo.getFIId(), new Double(str3).toString(), paramTransferInfo.getBankToRtn(), null, paramTransferInfo.getAccountFromNum(), paramTransferInfo.getAccountFromType(), paramTransferInfo.getAccountToNum(), paramTransferInfo.getAccountToType(), DBUtil.getCurrentLogDate(), String.valueOf(BPWUtil.getDateDBFormat(paramTransferInfo.getDateToPost())), paramTransferInfo.getPrcStatus(), paramTransferInfo.getLogId(), paramTransferInfo.getSourceRecSrvrTId(), paramTransferInfo.getSubmittedBy(), paramTransferInfo.getBankFromRtn(), null, paramTransferInfo.getAmountCurrency(), str4, paramTransferInfo.getToAmountCurrency(), new Integer(i) };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
      paramTransferInfo.setSrvrTId(str2);
      jdMethod_char(paramFFSConnectionHolder, paramTransferInfo);
    }
    catch (Exception localException)
    {
      String str5 = "*** XferInstruction.create: failed:";
      FFSDebug.log(str5 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("XferInstruction.create: done, SrvrTID=" + str2, 6);
    return str2;
  }
  
  protected static void jdMethod_byte(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    FFSDebug.log("XferInstruction.normalizeToAccountInfo() transferInfo: " + paramTransferInfo, 6);
    ExtTransferAcctInfo localExtTransferAcctInfo = a(paramFFSConnectionHolder, paramTransferInfo.getAccountToId(), paramTransferInfo.getAccountToNum(), paramTransferInfo.getAccountToType(), paramTransferInfo.getBankToRtn(), paramTransferInfo.getAccountToInfo());
    paramTransferInfo.setAccountToNum(localExtTransferAcctInfo.getAcctNum());
    paramTransferInfo.setAccountToType(localExtTransferAcctInfo.getAcctType());
    paramTransferInfo.setBankToRtn(localExtTransferAcctInfo.getAcctBankRtn());
  }
  
  protected static void jdMethod_case(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    FFSDebug.log("XferInstruction.normalizeFromAccountInfo() transferInfo: " + paramTransferInfo, 6);
    ExtTransferAcctInfo localExtTransferAcctInfo = a(paramFFSConnectionHolder, paramTransferInfo.getAccountFromId(), paramTransferInfo.getAccountFromNum(), paramTransferInfo.getAccountFromType(), paramTransferInfo.getBankFromRtn(), paramTransferInfo.getAccountFromInfo());
    paramTransferInfo.setAccountFromNum(localExtTransferAcctInfo.getAcctNum());
    paramTransferInfo.setAccountFromType(localExtTransferAcctInfo.getAcctType());
    paramTransferInfo.setBankFromRtn(localExtTransferAcctInfo.getAcctBankRtn());
  }
  
  protected static ExtTransferAcctInfo a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, String paramString4, ExtTransferAcctInfo paramExtTransferAcctInfo)
    throws FFSException
  {
    if ((paramString1 == null) || (paramString1.length() == 0))
    {
      if ((paramString2 == null) || (paramString2.length() == 0))
      {
        if (paramExtTransferAcctInfo == null) {
          return new ExtTransferAcctInfo();
        }
        if ((paramExtTransferAcctInfo.getAcctId() == null) || (paramExtTransferAcctInfo.getAcctId().length() == 0)) {
          return paramExtTransferAcctInfo;
        }
        return ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, paramExtTransferAcctInfo);
      }
      return new ExtTransferAcctInfo(paramString4, paramString2, paramString3);
    }
    paramExtTransferAcctInfo = new ExtTransferAcctInfo();
    paramExtTransferAcctInfo.setAcctId(paramString1);
    return ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, paramExtTransferAcctInfo);
  }
  
  private static void jdMethod_char(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws Exception
  {
    String str1 = "XferInstruction.updateSrvrTrans";
    FFSDebug.log(str1 + ": start", 6);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException(str1 + ":AuditAgent is null!!");
    }
    TypeIntraTrnRqV1 localTypeIntraTrnRqV1 = TransferIntraMap.mapTransferInfoToIntraRq(paramTransferInfo, new TypeUserData());
    String str2 = paramTransferInfo.getAction();
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1;
    if (str2.equals("Add"))
    {
      localTypeIntraTrnRsV1 = a(localTypeIntraTrnRqV1, paramTransferInfo, str2);
      localAuditAgent.saveXferRsV1(localTypeIntraTrnRsV1, paramTransferInfo.getCustomerId(), paramFFSConnectionHolder, paramTransferInfo.getPrcStatus());
    }
    else if (str2.equals("Modify"))
    {
      localTypeIntraTrnRsV1 = a(localTypeIntraTrnRqV1, paramTransferInfo, str2);
      XferInstruction localXferInstruction = TransferIntraMap.mapTransferInfoToXferInst(paramTransferInfo);
      localAuditAgent.modXferRsV1(localTypeIntraTrnRsV1, localXferInstruction, paramFFSConnectionHolder);
    }
    else if (str2.equals("Cancel"))
    {
      localAuditAgent.cancelOFX200XferRsV1(paramTransferInfo.getSrvrTId(), paramFFSConnectionHolder);
    }
    FFSDebug.log(str1 + ": done, SrvrTID=" + paramTransferInfo.getSrvrTId(), 6);
  }
  
  private static TypeIntraTrnRsV1 a(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TransferInfo paramTransferInfo, String paramString)
    throws Exception
  {
    TypeTrnRqCm localTypeTrnRqCm = paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm;
    TypeXferInfoAggregate localTypeXferInfoAggregate = null;
    EnumCurrencyEnum localEnumCurrencyEnum = null;
    if (paramString.equals("Add"))
    {
      localTypeXferInfoAggregate = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraRq.XferInfo;
      localEnumCurrencyEnum = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraRq.CurDef;
    }
    else
    {
      localTypeXferInfoAggregate = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.XferInfo;
      localEnumCurrencyEnum = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.CurDef;
    }
    TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsUnExists = true;
    TypeIntraTrnRsUn localTypeIntraTrnRsUn = new TypeIntraTrnRsUn();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn = localTypeIntraTrnRsUn;
    localTypeIntraTrnRsUn.__memberName = "IntraRs";
    TypeIntraRsAggregate localTypeIntraRsAggregate = new TypeIntraRsAggregate();
    localTypeIntraTrnRsUn.IntraRs = localTypeIntraRsAggregate;
    if (localEnumCurrencyEnum != null) {
      localTypeIntraRsAggregate.CurDef = localEnumCurrencyEnum;
    } else {
      localTypeIntraRsAggregate.CurDef = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyEnum(localEnumCurrencyEnum));
    }
    localTypeIntraRsAggregate.SrvrTID = paramTransferInfo.getSrvrTId();
    if (paramTransferInfo.getSourceRecSrvrTId() == null)
    {
      localTypeIntraRsAggregate.RecSrvrTIDExists = false;
    }
    else
    {
      localTypeIntraRsAggregate.RecSrvrTIDExists = true;
      localTypeIntraRsAggregate.RecSrvrTID = paramTransferInfo.getSourceRecSrvrTId();
    }
    localTypeIntraRsAggregate.XferInfo = localTypeXferInfoAggregate;
    localTypeIntraRsAggregate.IntraRsDateUnExists = false;
    localTypeIntraRsAggregate.XferPrcStsExists = true;
    localTypeIntraRsAggregate.XferPrcSts = new TypeXferPrcStsAggregate();
    int i = Integer.parseInt(paramTransferInfo.getDateToPost().substring(0, 8));
    String str = SmartCalendar.getPayday(paramTransferInfo.getFIId(), i, "BookTransfer") + "000000";
    RsCmBuilder.updateRsXferPrcSts(paramTransferInfo.getPrcStatus(), str, localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferPrcSts);
    RsCmBuilder.updateXferRsDateXferPrj(str, localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs);
    localTypeIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(localTypeTrnRqCm, paramTransferInfo.getStatusCode());
    return new TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
  }
  
  public static XferInstruction getXferInstruction(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    FFSDebug.log("XferInstruction.getXferInstruction: start, SrvrTID=" + paramString, 6);
    String str = "SELECT Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,DateCreate,DateToPost,CustomerID,Status,LogID,RecSrvrTID,FIID,SubmittedBy,LastModified,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount FROM BPW_XferInstruction WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramString };
    XferInstruction localXferInstruction = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localXferInstruction = new XferInstruction();
        localXferInstruction.SrvrTID = paramString;
        localXferInstruction.Amount = localFFSResultSet.getColumnString(1);
        localXferInstruction.BankID = localFFSResultSet.getColumnString(2);
        localXferInstruction.BranchID = localFFSResultSet.getColumnString(3);
        localXferInstruction.AcctDebitID = localFFSResultSet.getColumnString(4);
        localXferInstruction.AcctDebitType = localFFSResultSet.getColumnString(5);
        localXferInstruction.AcctCreditID = localFFSResultSet.getColumnString(6);
        localXferInstruction.AcctCreditType = localFFSResultSet.getColumnString(7);
        localXferInstruction.DateCreate = localFFSResultSet.getColumnString(8);
        localXferInstruction.DateToPost = localFFSResultSet.getColumnString(9);
        localXferInstruction.CustomerID = localFFSResultSet.getColumnString(10);
        localXferInstruction.Status = localFFSResultSet.getColumnString(11);
        localXferInstruction.LogID = localFFSResultSet.getColumnString(12);
        localXferInstruction.RecSrvrTID = localFFSResultSet.getColumnString(13);
        localXferInstruction.FIID = localFFSResultSet.getColumnString(14);
        localXferInstruction.SubmittedBy = localFFSResultSet.getColumnString(15);
        localXferInstruction.LastModified = localFFSResultSet.getColumnString(16);
        localXferInstruction.fromBankID = localFFSResultSet.getColumnString(17);
        localXferInstruction.fromBranchID = localFFSResultSet.getColumnString(18);
        localXferInstruction.CurDef = localFFSResultSet.getColumnString(19);
        localXferInstruction.ToAmount = localFFSResultSet.getColumnString(20);
        localXferInstruction.ToAmtCurrency = localFFSResultSet.getColumnString(21);
        localXferInstruction.userAssignedAmount = UserAssignedAmount.getEnum(localFFSResultSet.getColumnInt(22));
        HashMap localHashMap = jdMethod_char(localXferInstruction.SrvrTID, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0)) {
          localXferInstruction.extraFields = localHashMap;
        }
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** XferInstruction.getXferInstruction failed:" + localException1.toString());
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
        FFSDebug.log("*** XferInstruction.getXferInstruction  failed:" + localException2.toString());
      }
    }
    FFSDebug.log("XferInstruction.getXferInstruction: done", 6);
    return localXferInstruction;
  }
  
  public static void modify(FFSConnectionHolder paramFFSConnectionHolder, String paramString, XferInstruction paramXferInstruction)
    throws FFSException, BPWException
  {
    FFSDebug.log("XferInstruction.modify: start, SrvrTID=" + paramString, 6);
    int i = 0;
    if (paramXferInstruction.userAssignedAmount != null) {
      i = paramXferInstruction.userAssignedAmount.getValue();
    }
    String str1 = "UPDATE BPW_XferInstruction SET Amount=?,BankID=?,BranchID=?,AcctDebitID=?,AcctDebitType=?,AcctCreditID=?,AcctCreditType=?, DateToPost=?, CustomerID=?, Status=?, FromBankID=?, FromBranchID=?, SubmittedBy=?, LastModified=?, CurDef=?,ToAmount=?, ToAmountCurrency=?, UserAssignedAmount=? WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramXferInstruction.Amount, paramXferInstruction.BankID, paramXferInstruction.BranchID, paramXferInstruction.AcctDebitID, paramXferInstruction.AcctDebitType, paramXferInstruction.AcctCreditID, paramXferInstruction.AcctCreditType, paramXferInstruction.DateToPost, paramXferInstruction.CustomerID, paramXferInstruction.Status, paramXferInstruction.fromBankID, paramXferInstruction.fromBranchID, paramXferInstruction.SubmittedBy, paramXferInstruction.LastModified, paramXferInstruction.CurDef, paramXferInstruction.ToAmount, paramXferInstruction.ToAmtCurrency, new Integer(i), paramString };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** XferInstruction.modify failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("XferInstruction.modify: done", 6);
  }
  
  public static void modify(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException, BPWException
  {
    String str1 = paramTransferInfo.getSrvrTId();
    FFSDebug.log("XferInstruction.modify(transferInfo): start, SrvrTID=" + str1, 6);
    String str2 = paramTransferInfo.getAmount();
    if (paramTransferInfo.getIsAmountEstimated()) {
      str2 = "0.00";
    }
    String str3 = paramTransferInfo.getToAmount();
    if (paramTransferInfo.getIsToAmountEstimated()) {
      str3 = "0.00";
    }
    int i = 0;
    if (paramTransferInfo.getUserAssignedAmount() != null) {
      i = paramTransferInfo.getUserAssignedAmount().getValue();
    }
    jdMethod_byte(paramFFSConnectionHolder, paramTransferInfo);
    jdMethod_case(paramFFSConnectionHolder, paramTransferInfo);
    String str4 = "UPDATE BPW_XferInstruction SET Amount=?,BankID=?,BranchID=?,AcctDebitID=?,AcctDebitType=?,AcctCreditID=?,AcctCreditType=?, DateToPost=?, CustomerID=?, Status=?, FromBankID=?, FromBranchID=?, SubmittedBy=?, LastModified=?, CurDef=?,ToAmount=?, ToAmountCurrency=?, UserAssignedAmount=? WHERE SrvrTID=?";
    Object[] arrayOfObject = { str2, paramTransferInfo.getBankToRtn(), paramTransferInfo.getBranchID(), paramTransferInfo.getAccountFromNum(), paramTransferInfo.getAccountFromType(), paramTransferInfo.getAccountToNum(), paramTransferInfo.getAccountToType(), String.valueOf(BPWUtil.getDateDBFormat(paramTransferInfo.getDateToPost())), paramTransferInfo.getCustomerId(), paramTransferInfo.getPrcStatus(), paramTransferInfo.getBankFromRtn(), null, paramTransferInfo.getSubmittedBy(), paramTransferInfo.getLastChangeDate(), paramTransferInfo.getAmountCurrency(), str3, paramTransferInfo.getToAmountCurrency(), new Integer(i), str1 };
    try
    {
      int j = DBUtil.executeStatement(paramFFSConnectionHolder, str4, arrayOfObject);
      if (j > 0) {
        jdMethod_char(paramFFSConnectionHolder, paramTransferInfo);
      }
    }
    catch (Exception localException)
    {
      String str5 = "*** XferInstruction.modify(transferInfo) failed:";
      FFSDebug.log(str5 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("XferInstruction.modify(transferInfo): done", 6);
  }
  
  public static String getStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException, BPWException
  {
    FFSDebug.log("XferInstruction.getStatus: start, srvrTID=" + paramString, 6);
    String str1 = null;
    String str2 = "SELECT Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,DateCreate,DateToPost,CustomerID,Status,LogID,RecSrvrTID,FIID,SubmittedBy,LastModified,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount FROM BPW_XferInstruction WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramString };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow()) {
        str1 = localFFSResultSet.getColumnString(11);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** XferInstruction.getStatus failed:" + localException1.toString());
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
        FFSDebug.log("*** XferInstruction.getStatus  failed:" + localException2.toString());
      }
    }
    FFSDebug.log("XferInstruction.getStatus: done, status=" + str1, 6);
    return str1;
  }
  
  public static int updateStatus(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException, BPWException
  {
    FFSDebug.log("XferInstruction.updateStatus: start, SrvrTID=" + paramString1, 6);
    String str1 = "UPDATE BPW_XferInstruction SET Status=? WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramString2, paramString1 };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** XferInstruction.updateStatus failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("XferInstruction.updateStatus: done", 6);
    return i;
  }
  
  public static int updateStatus(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException, BPWException
  {
    String str1 = paramTransferInfo.getSrvrTId();
    String str2 = paramTransferInfo.getPrcStatus();
    FFSDebug.log("XferInstruction.updateStatus(dbh,transferInfo): start, SrvrTID=" + str1, 6);
    String str3 = "UPDATE BPW_XferInstruction SET Status=? WHERE SrvrTID=?";
    Object[] arrayOfObject = { str2, str1 };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, str3, arrayOfObject);
      if (i > 0) {
        jdMethod_char(paramFFSConnectionHolder, paramTransferInfo);
      }
    }
    catch (Exception localException)
    {
      String str4 = "*** XferInstruction.updateStatus failed:";
      FFSDebug.log(str4 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("XferInstruction.updateStatus: done", 6);
    return i;
  }
  
  public static void updateStatusAndConfirmNum(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3)
    throws FFSException, BPWException
  {
    FFSDebug.log("XferInstruction.updateStatusAndConfirmNum: start, SrvrTID=" + paramString1, 6);
    String str1 = "UPDATE BPW_XferInstruction SET Status=?, ConfirmNum=? WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramString2, paramString3, paramString1 };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** XferInstruction.updateStatusAndConfirmNum failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("XferInstruction.updateStatusAndConfirmNum: done", 6);
  }
  
  public static void updateStatusAndConfirmNum(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException, BPWException
  {
    FFSDebug.log("XferInstruction.updateStatusAndConfirmNum: start, SrvrTID=" + paramTransferInfo.getSrvrTId(), 6);
    String str1 = "UPDATE BPW_XferInstruction SET Status=?, ConfirmNum=?, Amount=?, ToAmount=? WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramTransferInfo.getPrcStatus(), paramTransferInfo.getConfirmNum(), paramTransferInfo.getAmount(), paramTransferInfo.getToAmount(), paramTransferInfo.getSrvrTId() };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** XferInstruction.updateStatusAndConfirmNum failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("XferInstruction.updateStatusAndConfirmNum: done", 6);
  }
  
  public static void delete(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException, BPWException
  {
    FFSDebug.log("XferInstruction.delete: start, SrvrTID=" + paramString, 6);
    String str1 = "DELETE FROM BPW_XferInstruction WHERE SrvrTID=?";
    Object[] arrayOfObject = { paramString };
    try
    {
      DBUtil.executeStatement(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** XferInstruction.delete failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new FFSException(localException.toString());
    }
    FFSDebug.log("XferInstruction.delete: done", 6);
  }
  
  public static String[] getOldSrvrTID(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    Vector localVector = new Vector();
    FFSDebug.log("XferInstruction.getOldSrvrTID: start ageDay=" + paramInt, 6);
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.add(5, -paramInt);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd00");
    String str1 = localSimpleDateFormat.format(localCalendar.getTime());
    String str2 = "SELECT SrvrTID FROM BPW_XferInstruction WHERE DateToPost<= ? AND Status IN (?, ?, ?, ?, ?) ";
    Object[] arrayOfObject = { str1, "NOFUNDSON_NOTIF", "FAILEDON_NOTIF", "FUNDSFAILEDON_NOTIF", "POSTEDON", "CANCELEDON" };
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
      String str4 = "*** XferInstruction.getOldSrvrTID failed:";
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
        FFSDebug.log("*** XferInstruction.getOldSrvrTID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("XferInstruction.getOldSrvrTID: rows=" + localVector.size(), 6);
    if (localVector.isEmpty()) {
      return null;
    }
    return (String[])localVector.toArray(new String[0]);
  }
  
  public static String[] getOldSrvrTIDByCustId(FFSConnectionHolder paramFFSConnectionHolder, int paramInt, String paramString)
    throws FFSException
  {
    String str1 = "XferInstruction.getOldSrvrTIDByCustId: ";
    Vector localVector = new Vector();
    FFSDebug.log(str1, "start ageDate=" + paramInt, " custId=" + paramString, 6);
    String str2 = "SELECT SrvrTID FROM BPW_XferInstruction WHERE DateToPost <= ?  AND CustomerID = ? AND Status IN (?, ?, ?, ?, ?) ";
    Object[] arrayOfObject = { String.valueOf(paramInt), paramString, "NOFUNDSON_NOTIF", "FAILEDON_NOTIF", "FUNDSFAILEDON_NOTIF", "POSTEDON", "CANCELEDON" };
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
        FFSDebug.log("*** ", str1, " failed:" + localException2.toString(), 0);
      }
    }
    FFSDebug.log(str1, " rows=" + localVector.size(), 6);
    if (localVector.isEmpty()) {
      return null;
    }
    return (String[])localVector.toArray(new String[0]);
  }
  
  public static IntraTrnInfo[] getXferBatchByStatus(String paramString1, String paramString2, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("XferInstruction.getXferBatchByStatus: start, fiId=" + paramString1 + ", status=" + paramString2, 6);
    String str = "SELECT SrvrTID,Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,DateToPost,CustomerID,SubmittedBy,LogID,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount FROM BPW_XferInstruction WHERE FIID=? AND Status=?";
    Object[] arrayOfObject = { paramString1, paramString2 };
    ArrayList localArrayList = new ArrayList();
    FFSResultSet localFFSResultSet = null;
    try
    {
      int i = 0;
      if (w2.get(paramString2 + paramString1) == null)
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
        w2.put(paramString2 + paramString1, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)w2.get(paramString2 + paramString1);
      }
      while (localFFSResultSet.getNextRow())
      {
        IntraTrnInfo localIntraTrnInfo = new IntraTrnInfo();
        localIntraTrnInfo.fiId = paramString1;
        localIntraTrnInfo.status = paramString2;
        localIntraTrnInfo.srvrTid = localFFSResultSet.getColumnString(1);
        localIntraTrnInfo.amount = localFFSResultSet.getColumnString(2);
        localIntraTrnInfo.bankId = localFFSResultSet.getColumnString(3);
        localIntraTrnInfo.branchId = localFFSResultSet.getColumnString(4);
        localIntraTrnInfo.acctIdFrom = localFFSResultSet.getColumnString(5);
        localIntraTrnInfo.acctTypeFrom = localFFSResultSet.getColumnString(6);
        localIntraTrnInfo.acctIdTo = localFFSResultSet.getColumnString(7);
        localIntraTrnInfo.acctTypeTo = localFFSResultSet.getColumnString(8);
        localIntraTrnInfo.dateToPost = localFFSResultSet.getColumnString(9);
        localIntraTrnInfo.customerId = localFFSResultSet.getColumnString(10);
        localIntraTrnInfo.submittedBy = localFFSResultSet.getColumnString(11);
        localIntraTrnInfo.logId = localFFSResultSet.getColumnString(12);
        localIntraTrnInfo.fromBankId = localFFSResultSet.getColumnString(13);
        localIntraTrnInfo.fromBranchId = localFFSResultSet.getColumnString(14);
        localIntraTrnInfo.curDef = localFFSResultSet.getColumnString(15);
        localIntraTrnInfo.toAmount = localFFSResultSet.getColumnString(16);
        localIntraTrnInfo.toAmtCurrency = localFFSResultSet.getColumnString(17);
        localIntraTrnInfo.userAssignedAmount = UserAssignedAmount.getEnum(localFFSResultSet.getColumnInt(18));
        localIntraTrnInfo.eventId = "";
        localIntraTrnInfo.eventSequence = 1;
        localIntraTrnInfo.possibleDuplicate = false;
        HashMap localHashMap = jdMethod_char(localIntraTrnInfo.srvrTid, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0)) {
          localIntraTrnInfo.extraFields = localHashMap;
        }
        localArrayList.add(localIntraTrnInfo);
        i++;
        if (i == paramInt)
        {
          IntraTrnInfo[] arrayOfIntraTrnInfo = (IntraTrnInfo[])localArrayList.toArray(new IntraTrnInfo[localArrayList.size()]);
          return arrayOfIntraTrnInfo;
        }
      }
      localFFSResultSet.close();
      w2.remove(paramString2 + paramString1);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null) {
        localFFSResultSet.close();
      }
      w2.remove(paramString2 + paramString1);
      FFSDebug.log("*** XferInstruction.getXferBatchByStatus failed:" + localException.toString());
      throw new Exception(localException.toString());
    }
    finally {}
    FFSDebug.log("XferInstruction.getXferBatchByStatus done, size=" + localArrayList.size(), 6);
    return (IntraTrnInfo[])localArrayList.toArray(new IntraTrnInfo[localArrayList.size()]);
  }
  
  public static boolean isStatusBatchDone(String paramString1, String paramString2)
  {
    return !w2.containsKey(paramString2 + paramString1);
  }
  
  public static void clearStatusBatch(String paramString1, String paramString2)
  {
    try
    {
      if (w2.containsKey(paramString2 + paramString1))
      {
        FFSResultSet localFFSResultSet = (FFSResultSet)w2.get(paramString2 + paramString1);
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
        w2.remove(paramString2 + paramString1);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PmtInstruction.clearStatusBatch failed:" + localException.toString());
    }
  }
  
  public static Vector getXferByStatus(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("XferInstruction.getXferByStatus: start, status=" + paramString, 6);
    String str = "SELECT SrvrTID,Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,DateToPost,CustomerID,SubmittedBy,LogID,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount FROM BPW_XferInstruction WHERE FIID=? AND Status=?";
    Object[] arrayOfObject = { paramString };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        IntraTrnInfo localIntraTrnInfo = new IntraTrnInfo();
        localIntraTrnInfo.fiId = localFFSResultSet.getColumnString("FIID");
        localIntraTrnInfo.status = paramString;
        localIntraTrnInfo.srvrTid = localFFSResultSet.getColumnString(1);
        localIntraTrnInfo.amount = localFFSResultSet.getColumnString(2);
        localIntraTrnInfo.bankId = localFFSResultSet.getColumnString(3);
        localIntraTrnInfo.branchId = localFFSResultSet.getColumnString(4);
        localIntraTrnInfo.acctIdFrom = localFFSResultSet.getColumnString(5);
        localIntraTrnInfo.acctTypeFrom = localFFSResultSet.getColumnString(6);
        localIntraTrnInfo.acctIdTo = localFFSResultSet.getColumnString(7);
        localIntraTrnInfo.acctTypeTo = localFFSResultSet.getColumnString(8);
        localIntraTrnInfo.dateToPost = localFFSResultSet.getColumnString(9);
        localIntraTrnInfo.customerId = localFFSResultSet.getColumnString(10);
        localIntraTrnInfo.submittedBy = localFFSResultSet.getColumnString(11);
        localIntraTrnInfo.logId = localFFSResultSet.getColumnString(12);
        localIntraTrnInfo.fromBankId = localFFSResultSet.getColumnString(13);
        localIntraTrnInfo.fromBranchId = localFFSResultSet.getColumnString(14);
        localIntraTrnInfo.curDef = localFFSResultSet.getColumnString(15);
        localIntraTrnInfo.toAmount = localFFSResultSet.getColumnString(16);
        localIntraTrnInfo.toAmtCurrency = localFFSResultSet.getColumnString(17);
        localIntraTrnInfo.userAssignedAmount = UserAssignedAmount.getEnum(localFFSResultSet.getColumnInt(18));
        localIntraTrnInfo.eventId = "";
        localIntraTrnInfo.eventSequence = 1;
        localIntraTrnInfo.possibleDuplicate = false;
        HashMap localHashMap = jdMethod_char(localIntraTrnInfo.srvrTid, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0)) {
          localIntraTrnInfo.extraFields = localHashMap;
        }
        localVector.addElement(localIntraTrnInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** XferInstruction.getXferByStatus failed:" + localException1.toString());
      throw new Exception(localException1.toString());
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
        FFSDebug.log("*** XferInstruction.getXferByStatus  failed:" + localException2.toString());
      }
    }
    FFSDebug.log("XferInstruction.getXferByStatus done, size=" + localVector.size(), 6);
    return localVector;
  }
  
  public static Vector getXferByStatusAndCustomerID(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("XferInstruction.getXferByStatusAndCustomerID start, status=" + paramString1 + ",CustomerID=" + paramString2, 6);
    String str = "SELECT SrvrTID,Amount,BankID,BranchID, AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,DateToPost,FIID,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount FROM BPW_XferInstruction WHERE Status=? AND CustomerID=? ";
    Object[] arrayOfObject = { paramString1, paramString2 };
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        IntraTrnInfo localIntraTrnInfo = new IntraTrnInfo();
        localIntraTrnInfo.status = paramString1;
        localIntraTrnInfo.srvrTid = localFFSResultSet.getColumnString(1);
        localIntraTrnInfo.amount = localFFSResultSet.getColumnString(2);
        localIntraTrnInfo.bankId = localFFSResultSet.getColumnString(3);
        localIntraTrnInfo.branchId = localFFSResultSet.getColumnString(4);
        localIntraTrnInfo.acctIdFrom = localFFSResultSet.getColumnString(5);
        localIntraTrnInfo.acctTypeFrom = localFFSResultSet.getColumnString(6);
        localIntraTrnInfo.acctIdTo = localFFSResultSet.getColumnString(7);
        localIntraTrnInfo.acctTypeTo = localFFSResultSet.getColumnString(8);
        localIntraTrnInfo.dateToPost = localFFSResultSet.getColumnString(9);
        localIntraTrnInfo.fiId = localFFSResultSet.getColumnString(10);
        localIntraTrnInfo.fromBankId = localFFSResultSet.getColumnString(11);
        localIntraTrnInfo.fromBranchId = localFFSResultSet.getColumnString(12);
        localIntraTrnInfo.curDef = localFFSResultSet.getColumnString(13);
        localIntraTrnInfo.toAmount = localFFSResultSet.getColumnString(14);
        localIntraTrnInfo.toAmtCurrency = localFFSResultSet.getColumnString(15);
        localIntraTrnInfo.userAssignedAmount = UserAssignedAmount.getEnum(localFFSResultSet.getColumnInt(16));
        localIntraTrnInfo.customerId = paramString2;
        localIntraTrnInfo.eventId = "";
        localIntraTrnInfo.eventSequence = 1;
        localIntraTrnInfo.possibleDuplicate = false;
        HashMap localHashMap = jdMethod_char(localIntraTrnInfo.srvrTid, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0)) {
          localIntraTrnInfo.extraFields = localHashMap;
        }
        localVector.addElement(localIntraTrnInfo);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** XferInstruction.getXferByStatusAndCustomerID failed:" + localException1.toString());
      throw new Exception(localException1.toString());
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
        FFSDebug.log("*** XferInstruction.getXferByStatusAndCustomerID  failed:" + localException2.toString());
      }
    }
    FFSDebug.log("XferInstruction.getXferByStatusAndCustomerID done, size=" + localVector.size(), 6);
    return localVector;
  }
  
  public static HashMap getIntraHistoryByRange(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String[] paramArrayOfString, int paramInt1, int paramInt2, long paramLong1, long paramLong2, String paramString2)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap(0);
    StringBuffer localStringBuffer = new StringBuffer("SELECT SrvrTID, BankID, BranchID, CustomerID, Amount, AcctDebitID, AcctDebitType, AcctCreditID, AcctCreditType, DateToPost, Status, RecSrvrTID, FIID, FromBankID, FromBranchID, CurDef, SubmittedBy, ToAmount, ToAmountCurrency, UserAssignedAmount, FROM BPW_XferInstruction WHERE CustomerID = ?");
    IntraTrnInfo localIntraTrnInfo = null;
    long l = 0L;
    try
    {
      FFSDebug.log("XferInstruction.getIntraHistoryByRange start", 6);
      localStringBuffer.append(" AND CAST(SrvrTID AS INTEGER) >= " + paramLong2);
      localStringBuffer.append(" AND CAST(SrvrTID AS INTEGER) <= " + paramLong1);
      localStringBuffer = a(paramArrayOfString, paramInt1, paramInt2, paramString2, localStringBuffer);
      localStringBuffer.append(" ORDER BY CAST(SrvrTID AS INTEGER) ASC");
      String[] arrayOfString = { paramString1 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfString);
      while (localFFSResultSet.getNextRow())
      {
        localIntraTrnInfo = new IntraTrnInfo();
        localIntraTrnInfo.srvrTid = localFFSResultSet.getColumnString(1);
        localIntraTrnInfo.bankId = localFFSResultSet.getColumnString(2);
        localIntraTrnInfo.branchId = localFFSResultSet.getColumnString(3);
        localIntraTrnInfo.customerId = localFFSResultSet.getColumnString(4);
        localIntraTrnInfo.amount = localFFSResultSet.getColumnString(5);
        localIntraTrnInfo.acctIdFrom = localFFSResultSet.getColumnString(6);
        localIntraTrnInfo.acctTypeFrom = localFFSResultSet.getColumnString(7);
        localIntraTrnInfo.acctIdTo = localFFSResultSet.getColumnString(8);
        localIntraTrnInfo.acctTypeTo = localFFSResultSet.getColumnString(9);
        localIntraTrnInfo.dateToPost = localFFSResultSet.getColumnString(10);
        localIntraTrnInfo.status = localFFSResultSet.getColumnString(11);
        localIntraTrnInfo.fiId = localFFSResultSet.getColumnString(12);
        localIntraTrnInfo.fromBankId = localFFSResultSet.getColumnString(13);
        localIntraTrnInfo.fromBranchId = localFFSResultSet.getColumnString(14);
        localIntraTrnInfo.curDef = localFFSResultSet.getColumnString(15);
        localIntraTrnInfo.setSubmittedBy(localFFSResultSet.getColumnString(16));
        localIntraTrnInfo.toAmount = localFFSResultSet.getColumnString(17);
        localIntraTrnInfo.toAmtCurrency = localFFSResultSet.getColumnString(18);
        localIntraTrnInfo.userAssignedAmount = UserAssignedAmount.getEnum(localFFSResultSet.getColumnInt(19));
        localObject1 = jdMethod_char(localIntraTrnInfo.srvrTid, paramFFSConnectionHolder);
        if ((localObject1 != null) && (((HashMap)localObject1).size() != 0)) {
          localIntraTrnInfo.extraFields = localObject1;
        }
        localArrayList.add(localIntraTrnInfo);
        l += 1L;
      }
      if (localArrayList.size() == 0)
      {
        localObject1 = null;
        return localObject1;
      }
      Object localObject1 = (IntraTrnInfo[])localArrayList.toArray(new IntraTrnInfo[0]);
      paramLong2 = Long.parseLong(localObject1[0].srvrTid);
      paramLong1 = Long.parseLong(localObject1[(localArrayList.size() - 1)].srvrTid);
      localHashMap.put("QRYRESULT", localObject1);
      localHashMap.put("MINKEYVAL", new Long(paramLong2));
      localHashMap.put("MAXKEYVAL", new Long(paramLong1));
      localHashMap.put("RECCOUNT", new Long(l));
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** XferInstruction.getIntraHistoryByRange failed:" + localException1.toString());
      throw new Exception(localException1.toString());
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
        FFSDebug.log("*** XferInstruction.getIntraHistoryByRange failed:" + localException2.toString());
      }
    }
    FFSDebug.log("XferInstruction.getIntraHistoryByRange done", 6);
    return localHashMap;
  }
  
  public static HashMap getIntraHistoryNoRange(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String[] paramArrayOfString, int paramInt1, int paramInt2, long paramLong, String paramString2)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap(0);
    StringBuffer localStringBuffer = new StringBuffer("SELECT SrvrTID, BankID, BranchID, CustomerID, Amount, AcctDebitID, AcctDebitType, AcctCreditID, AcctCreditType, DateToPost, Status, RecSrvrTID, FIID, FromBankID, FromBranchID, CurDef, SubmittedBy, ToAmount, ToAmountCurrency, UserAssignedAmount, FROM BPW_XferInstruction WHERE CustomerID = ?");
    long l1 = 0L;
    long l2 = 0L;
    long l3 = 0L;
    IntraTrnInfo localIntraTrnInfo = null;
    int i = 0;
    if (paramLong > 0L) {
      i = 1;
    }
    try
    {
      FFSDebug.log("XferInstruction.getIntraHistoryNoRange start", 6);
      localStringBuffer = a(paramArrayOfString, paramInt1, paramInt2, paramString2, localStringBuffer);
      localStringBuffer.append(" ORDER BY CAST(SrvrTID AS INTEGER) ASC");
      String[] arrayOfString = { paramString1 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfString);
      while (localFFSResultSet.getNextRow())
      {
        localIntraTrnInfo = new IntraTrnInfo();
        localIntraTrnInfo.srvrTid = localFFSResultSet.getColumnString(1);
        localIntraTrnInfo.bankId = localFFSResultSet.getColumnString(2);
        localIntraTrnInfo.branchId = localFFSResultSet.getColumnString(3);
        localIntraTrnInfo.customerId = localFFSResultSet.getColumnString(4);
        localIntraTrnInfo.amount = localFFSResultSet.getColumnString(5);
        localIntraTrnInfo.acctIdFrom = localFFSResultSet.getColumnString(6);
        localIntraTrnInfo.acctTypeFrom = localFFSResultSet.getColumnString(7);
        localIntraTrnInfo.acctIdTo = localFFSResultSet.getColumnString(8);
        localIntraTrnInfo.acctTypeTo = localFFSResultSet.getColumnString(9);
        localIntraTrnInfo.dateToPost = localFFSResultSet.getColumnString(10);
        localIntraTrnInfo.status = localFFSResultSet.getColumnString(11);
        localIntraTrnInfo.fiId = localFFSResultSet.getColumnString(12);
        localIntraTrnInfo.fromBankId = localFFSResultSet.getColumnString(13);
        localIntraTrnInfo.fromBranchId = localFFSResultSet.getColumnString(14);
        localIntraTrnInfo.curDef = localFFSResultSet.getColumnString(15);
        localIntraTrnInfo.setSubmittedBy(localFFSResultSet.getColumnString(16));
        localIntraTrnInfo.toAmount = localFFSResultSet.getColumnString(17);
        localIntraTrnInfo.toAmtCurrency = localFFSResultSet.getColumnString(18);
        localIntraTrnInfo.userAssignedAmount = UserAssignedAmount.getEnum(localFFSResultSet.getColumnInt(19));
        localObject1 = jdMethod_char(localIntraTrnInfo.srvrTid, paramFFSConnectionHolder);
        if ((localObject1 != null) && (((HashMap)localObject1).size() != 0)) {
          localIntraTrnInfo.extraFields = localObject1;
        }
        l1 += 1L;
        if ((i == 0) || (l1 <= paramLong)) {
          localArrayList.add(localIntraTrnInfo);
        }
      }
      if (localArrayList.size() == 0)
      {
        localObject1 = null;
        return localObject1;
      }
      Object localObject1 = (IntraTrnInfo[])localArrayList.toArray(new IntraTrnInfo[0]);
      l3 = Long.parseLong(localObject1[0].srvrTid);
      l2 = Long.parseLong(localIntraTrnInfo.srvrTid);
      localHashMap.put("QRYRESULT", localObject1);
      localHashMap.put("MINKEYVAL", new Long(l3));
      localHashMap.put("MAXKEYVAL", new Long(l2));
      localHashMap.put("RECCOUNT", new Long(l1));
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** XferInstruction.getIntraHistoryNoRange failed:" + localException1.toString());
      throw new Exception(localException1.toString());
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
        FFSDebug.log("*** XferInstruction.getIntraHistoryNoRange failed:" + localException2.toString());
      }
    }
    FFSDebug.log("XferInstruction.getIntraHistoryNoRange done", 6);
    return localHashMap;
  }
  
  public static HashMap getIntraHistoryByDirection(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String[] paramArrayOfString, int paramInt1, int paramInt2, long paramLong1, long paramLong2, String paramString2, String paramString3)
    throws Exception
  {
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    HashMap localHashMap = new HashMap(0);
    StringBuffer localStringBuffer = new StringBuffer("SELECT SrvrTID, BankID, BranchID, CustomerID, Amount, AcctDebitID, AcctDebitType, AcctCreditID, AcctCreditType, DateToPost, Status, RecSrvrTID, FIID, FromBankID, FromBranchID, CurDef, SubmittedBy, ToAmount, ToAmountCurrency, UserAssignedAmount, FROM BPW_XferInstruction WHERE CustomerID = ?");
    long l1 = 0L;
    long l2 = 0L;
    IntraTrnInfo localIntraTrnInfo = null;
    int i = 0;
    if (paramLong1 > 0L) {
      i = 1;
    }
    try
    {
      FFSDebug.log("XferInstruction.getIntraHistoryByDirection start", 6);
      localStringBuffer = a(paramArrayOfString, paramInt1, paramInt2, paramString3, localStringBuffer);
      if (paramString2.equalsIgnoreCase("Prev"))
      {
        localStringBuffer.append(" AND CAST(SrvrTID AS INTEGER) < " + paramLong2);
        localStringBuffer.append(" ORDER BY CAST(SrvrTID AS INTEGER) DESC");
      }
      else
      {
        localStringBuffer.append(" AND CAST(SrvrTID AS INTEGER) > " + paramLong2);
        localStringBuffer.append(" ORDER BY CAST(SrvrTID AS INTEGER) ASC");
      }
      String[] arrayOfString = { paramString1 };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfString);
      while (localFFSResultSet.getNextRow())
      {
        localIntraTrnInfo = new IntraTrnInfo();
        localIntraTrnInfo.srvrTid = localFFSResultSet.getColumnString(1);
        localIntraTrnInfo.bankId = localFFSResultSet.getColumnString(2);
        localIntraTrnInfo.branchId = localFFSResultSet.getColumnString(3);
        localIntraTrnInfo.customerId = localFFSResultSet.getColumnString(4);
        localIntraTrnInfo.amount = localFFSResultSet.getColumnString(5);
        localIntraTrnInfo.acctIdFrom = localFFSResultSet.getColumnString(6);
        localIntraTrnInfo.acctTypeFrom = localFFSResultSet.getColumnString(7);
        localIntraTrnInfo.acctIdTo = localFFSResultSet.getColumnString(8);
        localIntraTrnInfo.acctTypeTo = localFFSResultSet.getColumnString(9);
        localIntraTrnInfo.dateToPost = localFFSResultSet.getColumnString(10);
        localIntraTrnInfo.status = localFFSResultSet.getColumnString(11);
        localIntraTrnInfo.fiId = localFFSResultSet.getColumnString(12);
        localIntraTrnInfo.fromBankId = localFFSResultSet.getColumnString(13);
        localIntraTrnInfo.fromBranchId = localFFSResultSet.getColumnString(14);
        localIntraTrnInfo.curDef = localFFSResultSet.getColumnString(15);
        localIntraTrnInfo.setSubmittedBy(localFFSResultSet.getColumnString(16));
        localIntraTrnInfo.toAmount = localFFSResultSet.getColumnString(17);
        localIntraTrnInfo.toAmtCurrency = localFFSResultSet.getColumnString(18);
        localIntraTrnInfo.userAssignedAmount = UserAssignedAmount.getEnum(localFFSResultSet.getColumnInt(19));
        localObject1 = jdMethod_char(localIntraTrnInfo.srvrTid, paramFFSConnectionHolder);
        if ((localObject1 != null) && (((HashMap)localObject1).size() != 0)) {
          localIntraTrnInfo.extraFields = localObject1;
        }
        l1 += 1L;
        if ((i == 0) || (l1 <= paramLong1)) {
          if (paramString2.equalsIgnoreCase("Prev")) {
            localArrayList.add(0, localIntraTrnInfo);
          } else {
            localArrayList.add(localIntraTrnInfo);
          }
        }
      }
      if (localArrayList.size() == 0)
      {
        localObject1 = null;
        return localObject1;
      }
      Object localObject1 = (IntraTrnInfo[])localArrayList.toArray(new IntraTrnInfo[0]);
      paramLong2 = Long.parseLong(localObject1[0].srvrTid);
      l2 = Long.parseLong(localObject1[(localArrayList.size() - 1)].srvrTid);
      localHashMap.put("QRYRESULT", localObject1);
      localHashMap.put("MINKEYVAL", new Long(paramLong2));
      localHashMap.put("MAXKEYVAL", new Long(l2));
      localHashMap.put("RECCOUNT", new Long(l1));
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** XferInstruction.getIntraHistoryByDirection failed:" + localException1.toString());
      throw new Exception(localException1.toString());
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
        FFSDebug.log("*** XferInstruction.getIntraHistoryByDirection failed:" + localException2.toString());
      }
    }
    FFSDebug.log("XferInstruction.getIntraHistoryByDirection done", 6);
    return localHashMap;
  }
  
  private static String jdMethod_try(String[] paramArrayOfString)
  {
    StringBuffer localStringBuffer = new StringBuffer(new String(""));
    for (int i = 0; i < paramArrayOfString.length; i++)
    {
      localStringBuffer.append("'");
      localStringBuffer.append(paramArrayOfString[i]);
      localStringBuffer.append("'");
      if (paramArrayOfString.length - i != 1) {
        localStringBuffer.append(",");
      }
    }
    return localStringBuffer.toString().trim();
  }
  
  private static String aw(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer(new String(""));
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",", false);
    int i = localStringTokenizer.countTokens();
    for (int j = 0; j < i; j++)
    {
      localStringBuffer.append("'");
      localStringBuffer.append(localStringTokenizer.nextToken());
      localStringBuffer.append("'");
      if (i - j != 1) {
        localStringBuffer.append(",");
      }
    }
    return localStringBuffer.toString().trim();
  }
  
  private static StringBuffer a(String[] paramArrayOfString, int paramInt1, int paramInt2, String paramString, StringBuffer paramStringBuffer)
  {
    String str1 = new String("" + paramInt1).trim();
    String str2 = new String("" + paramInt2).trim();
    if (paramInt1 != 0) {
      paramStringBuffer.append(" AND DateToPost >= '" + str1 + "'");
    }
    paramStringBuffer.append(" AND DateToPost <= '" + str2 + "'");
    if ((paramArrayOfString != null) && (paramArrayOfString.length != 0)) {
      paramStringBuffer.append(" AND AcctDebitID IN (" + jdMethod_try(paramArrayOfString) + ")");
    }
    if ((paramString != null) && (paramString.trim().length() != 0)) {
      paramStringBuffer.append(" AND Status IN (" + aw(paramString) + ")");
    }
    return paramStringBuffer;
  }
  
  public static XferInstruction[] getHist(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, String paramString4, long paramLong)
    throws Exception
  {
    FFSDebug.log("XferInstruction.getHist start, custID=" + paramString1 + ",acctID=" + paramString2 + ",startDate=" + paramInt1 + ",endDate=" + paramInt2 + ",status=" + paramString3, 6);
    String str1 = "SELECT SrvrTID, DateToPost, Status, FIID FROM BPW_XferInstruction WHERE CustomerID = ? AND DateToPost >= ? AND DateToPost <= ? ";
    if ((paramString2 != null) && (paramString2.trim().length() > 0)) {
      str1 = str1 + "AND AcctDebitID = '" + paramString2 + "' ";
    }
    String str2 = "";
    if ((paramString3 != null) && (paramString3.length() > 0)) {
      str2 = DBUtil.buildStatusQueryString(paramString3);
    }
    str1 = str1 + str2;
    XferInstruction localXferInstruction = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    long l = 0L;
    try
    {
      Object[] arrayOfObject = { paramString1, String.valueOf(paramInt1), String.valueOf(paramInt2) };
      if (w0.get(paramString4) == null)
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
        w0.put(paramString4, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)w0.get(paramString4);
      }
      while (localFFSResultSet.getNextRow())
      {
        localXferInstruction = new XferInstruction();
        localXferInstruction.SrvrTID = localFFSResultSet.getColumnString(1);
        localXferInstruction.DateToPost = localFFSResultSet.getColumnString(2);
        localXferInstruction.Status = localFFSResultSet.getColumnString(3);
        localXferInstruction.FIID = localFFSResultSet.getColumnString(4);
        HashMap localHashMap = jdMethod_char(localXferInstruction.SrvrTID, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0)) {
          localXferInstruction.extraFields = localHashMap;
        }
        localArrayList.add(localXferInstruction);
        l += 1L;
        if (l == paramLong)
        {
          XferInstruction[] arrayOfXferInstruction = (XferInstruction[])localArrayList.toArray(new XferInstruction[0]);
          return arrayOfXferInstruction;
        }
      }
      localFFSResultSet.close();
      w0.remove(paramString4);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      if (w0 != null) {
        w0.remove(paramString4);
      }
      throw new Exception(localException.toString() + FFSDebug.stackTrace(localException));
    }
    finally {}
    FFSDebug.log("XferInstruction.getHist done, rows=" + l, 6);
    return (XferInstruction[])localArrayList.toArray(new XferInstruction[0]);
  }
  
  public static XferInstruction[] getHist(FFSConnectionHolder paramFFSConnectionHolder, BPWExtdHist paramBPWExtdHist, int paramInt1, int paramInt2)
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
    String str11 = "SELECT SrvrTID, DateToPost, Status, FIID FROM BPW_XferInstruction WHERE CustomerID = ? AND DateToPost >= ? AND DateToPost <= ? ";
    if ((!str10.trim().equals("AND")) && (!str10.trim().equals("OR"))) {
      str10 = "AND";
    }
    if ((str2 != null) && (str2.trim().length() != 0))
    {
      if ((str5 != null) && (str5.length() != 0))
      {
        if ((str6 != null) && (str6.trim().length() != 0) && (str7 != null) && (str7.length() != 0)) {
          str11 = str11 + " AND ((AcctDebitID = '" + str2 + "'" + " AND AcctDebitType = '" + str5 + "'" + ") " + str10 + " (AcctCreditID = '" + str6 + "'" + " AND AcctCreditType = '" + str7 + "'" + "))";
        } else {
          str11 = str11 + " AND AcctDebitID = '" + str2 + "'" + " AND AcctDebitType = '" + str5 + "'";
        }
      }
      else if ((str6 != null) && (str6.trim().length() != 0)) {
        str11 = str11 + " AND (AcctDebitID = '" + str2 + "' " + str10 + " AcctCreditID = '" + str6 + "'" + ")";
      } else {
        str11 = str11 + " AND AcctDebitID = '" + str2 + "'";
      }
    }
    else if ((str6 != null) && (str6.trim().length() != 0) && (str7 != null) && (str7.length() != 0)) {
      str11 = str11 + " AND (AcctCreditID = '" + str6 + "'" + " AND AcctCreditType = '" + str7 + "'" + ")";
    } else if ((str6 != null) && (str6.trim().length() != 0)) {
      str11 = str11 + " AND (AcctCreditID = '" + str6 + "'" + ")";
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
      int i = 1;
      String str15 = "";
      while (((Iterator)localObject2).hasNext())
      {
        str14 = (String)((Iterator)localObject2).next();
        localArrayList = (ArrayList)paramBPWExtdHist.getExtraInfo().get(str14);
        if (i != 0) {
          i = 0;
        } else {
          str13 = str13 + " And ";
        }
        str15 = "";
        for (int j = 0; j < localArrayList.size(); j++) {
          if (j == 0) {
            str15 = str15 + " ( Name ='" + str14 + "' And Value = '" + localArrayList.get(j) + "' )";
          } else {
            str15 = str15 + " OR ( Name ='" + str14 + "' And Value = '" + localArrayList.get(j) + "' )";
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
      localObject1 = wY + str13 + " )";
      str11 = str11 + (String)localObject1;
    }
    localObject1 = null;
    Object localObject2 = { paramBPWExtdHist.getCustId(), String.valueOf(paramInt1), String.valueOf(paramInt2) };
    return jdMethod_do(paramFFSConnectionHolder, str11, (Object[])localObject2, paramBPWExtdHist.getHistId(), paramBPWExtdHist.getPageSize());
  }
  
  private static XferInstruction[] jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, Object[] paramArrayOfObject, String paramString2, long paramLong)
    throws Exception
  {
    FFSDebug.log("XferInstruction.executeHistQuery start", 6);
    XferInstruction localXferInstruction = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    long l = 0L;
    HashMap localHashMap = null;
    try
    {
      if (w0.get(paramString2) == null)
      {
        localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, paramString1, paramArrayOfObject);
        w0.put(paramString2, localFFSResultSet);
      }
      else
      {
        localFFSResultSet = (FFSResultSet)w0.get(paramString2);
      }
      while (localFFSResultSet.getNextRow())
      {
        localXferInstruction = new XferInstruction();
        localXferInstruction.SrvrTID = localFFSResultSet.getColumnString("SrvrTID");
        localXferInstruction.DateToPost = localFFSResultSet.getColumnString("DateToPost");
        localXferInstruction.Status = localFFSResultSet.getColumnString("Status");
        localXferInstruction.FIID = localFFSResultSet.getColumnString("FIID");
        localHashMap = jdMethod_char(localXferInstruction.SrvrTID, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0)) {
          localXferInstruction.extraFields = localHashMap;
        }
        localArrayList.add(localXferInstruction);
        l += 1L;
        if (l == paramLong)
        {
          XferInstruction[] arrayOfXferInstruction = (XferInstruction[])localArrayList.toArray(new XferInstruction[0]);
          return arrayOfXferInstruction;
        }
      }
      localFFSResultSet.close();
      w0.remove(paramString2);
    }
    catch (Exception localException)
    {
      localException = localException;
      if (localFFSResultSet != null)
      {
        localFFSResultSet.close();
        localFFSResultSet = null;
      }
      if (w0 != null) {
        w0.remove(paramString2);
      }
      throw new Exception(localException.toString() + FFSDebug.stackTrace(localException));
    }
    finally {}
    FFSDebug.log("XferInstruction.executeHistQuery done, rows=" + l, 6);
    return (XferInstruction[])localArrayList.toArray(new XferInstruction[0]);
  }
  
  public static boolean isBatchDone(String paramString)
  {
    return !w0.containsKey(paramString);
  }
  
  public static void clearBatch(String paramString)
  {
    try
    {
      if (w0.containsKey(paramString))
      {
        FFSResultSet localFFSResultSet = (FFSResultSet)w0.get(paramString);
        if (localFFSResultSet != null) {
          localFFSResultSet.close();
        }
        w0.remove(paramString);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** PmtInstruction.clearBatch failed:" + localException.toString());
    }
  }
  
  public static IntraTrnInfo getIntraById(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("XferInstruction.getIntraById: start ", 6);
    IntraTrnInfo localIntraTrnInfo = new IntraTrnInfo();
    String str = "SELECT Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,DateCreate,DateToPost,CustomerID,Status,LogID,RecSrvrTID,FIID,SubmittedBy,LastModified,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount FROM BPW_XferInstruction WHERE SrvrTID=?";
    FFSResultSet localFFSResultSet = null;
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      localIntraTrnInfo.statusCode = 16504;
      localIntraTrnInfo.statusMsg = BPWLocaleUtil.getMessage(16504, new String[] { "ID " }, "OFX_MESSAGE");
      FFSDebug.log("XferInstruction.getIntraById failed : null or empty ID passed", 0);
      return localIntraTrnInfo;
    }
    Object[] arrayOfObject = { paramString };
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localIntraTrnInfo.customerId = localFFSResultSet.getColumnString("CustomerID");
        localIntraTrnInfo.bankId = localFFSResultSet.getColumnString("BankID");
        localIntraTrnInfo.branchId = localFFSResultSet.getColumnString("BranchID");
        localIntraTrnInfo.acctIdFrom = localFFSResultSet.getColumnString("AcctDebitID");
        localIntraTrnInfo.acctTypeFrom = localFFSResultSet.getColumnString("AcctDebitType");
        localIntraTrnInfo.acctIdTo = localFFSResultSet.getColumnString("AcctCreditID");
        localIntraTrnInfo.acctTypeTo = localFFSResultSet.getColumnString("AcctCreditType");
        localIntraTrnInfo.amount = localFFSResultSet.getColumnString("Amount");
        localIntraTrnInfo.dateToPost = localFFSResultSet.getColumnString("DateToPost");
        localIntraTrnInfo.status = localFFSResultSet.getColumnString("Status");
        localIntraTrnInfo.eventId = "";
        localIntraTrnInfo.eventSequence = 1;
        localIntraTrnInfo.possibleDuplicate = false;
        localIntraTrnInfo.statusCode = 0;
        localIntraTrnInfo.statusMsg = BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE");
        localIntraTrnInfo.srvrTid = paramString;
        localIntraTrnInfo.logId = localFFSResultSet.getColumnString("LogID");
        localIntraTrnInfo.setSubmittedBy(localFFSResultSet.getColumnString("SubmittedBy"));
        localIntraTrnInfo.fiId = localFFSResultSet.getColumnString("FIID");
        localIntraTrnInfo.fromBankId = localFFSResultSet.getColumnString("FromBankID");
        localIntraTrnInfo.fromBranchId = localFFSResultSet.getColumnString("FromBranchID");
        localIntraTrnInfo.curDef = localFFSResultSet.getColumnString("CurDef");
        localIntraTrnInfo.toAmount = localFFSResultSet.getColumnString("ToAmount");
        localIntraTrnInfo.toAmtCurrency = localFFSResultSet.getColumnString("ToAmountCurrency");
        localIntraTrnInfo.userAssignedAmount = UserAssignedAmount.getEnum(localFFSResultSet.getColumnInt("UserAssignedAmount"));
        HashMap localHashMap = jdMethod_char(paramString, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0)) {
          localIntraTrnInfo.extraFields = localHashMap;
        }
      }
      else
      {
        localIntraTrnInfo.statusCode = 17020;
        localIntraTrnInfo.statusMsg = BPWLocaleUtil.getMessage(17020, null, "OFX_MESSAGE");
        FFSDebug.log("XferInstruction.getIntraById : no records found", 6);
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** XferInstruction.getIntraById failed:" + FFSDebug.stackTrace(localException1), 0);
      throw localException1;
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
        FFSDebug.log("*** XferInstruction.getIntraById  failed:" + FFSDebug.stackTrace(localException2), 0);
      }
    }
    FFSDebug.log("XferInstruction.getIntraById done", 6);
    return localIntraTrnInfo;
  }
  
  public static IntraTrnInfo[] getIntraByRecSrvrTId(String[] paramArrayOfString, boolean paramBoolean, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    IntraTrnInfo[] arrayOfIntraTrnInfo = null;
    FFSResultSet localFFSResultSet = null;
    String str1 = "XferInstruction.getIntraByRecSrvrTId";
    try
    {
      int i = paramArrayOfString.length;
      if (i > 500)
      {
        arrayOfIntraTrnInfo = new IntraTrnInfo[1];
        arrayOfIntraTrnInfo[0] = new IntraTrnInfo();
        arrayOfIntraTrnInfo[0].setStatusCode(21540);
        arrayOfIntraTrnInfo[0].setStatusMsg(BPWLocaleUtil.getMessage(21540, null, "TRANSFER_MESSAGE"));
        localObject1 = arrayOfIntraTrnInfo;
        return localObject1;
      }
      localObject1 = new StringBuffer(" select ");
      ((StringBuffer)localObject1).append("Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,DateCreate,DateToPost,CustomerID,Status,LogID,RecSrvrTID,FIID,SubmittedBy,LastModified,FromBankID,FromBranchID,SrvrTID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount ").append(" from ").append("BPW_XferInstruction");
      ((StringBuffer)localObject1).append(" where RecSrvrTID in ( ");
      for (int j = 0; j < i; j++) {
        ((StringBuffer)localObject1).append("'").append(paramArrayOfString[j]).append("',");
      }
      ((StringBuffer)localObject1).deleteCharAt(((StringBuffer)localObject1).length() - 1);
      ((StringBuffer)localObject1).append(" )");
      if (paramBoolean) {
        ((StringBuffer)localObject1).append(" and Status !='").append("CANCELEDON").append("'");
      }
      ((StringBuffer)localObject1).append(" order by RecSrvrTID");
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, ((StringBuffer)localObject1).toString(), null);
      localObject2 = new ArrayList();
      while (localFFSResultSet.getNextRow())
      {
        IntraTrnInfo localIntraTrnInfo = new IntraTrnInfo();
        localIntraTrnInfo.customerId = localFFSResultSet.getColumnString("CustomerID");
        localIntraTrnInfo.bankId = localFFSResultSet.getColumnString("BankID");
        localIntraTrnInfo.branchId = localFFSResultSet.getColumnString("BranchID");
        localIntraTrnInfo.acctIdFrom = localFFSResultSet.getColumnString("AcctDebitID");
        localIntraTrnInfo.acctTypeFrom = localFFSResultSet.getColumnString("AcctDebitType");
        localIntraTrnInfo.acctIdTo = localFFSResultSet.getColumnString("AcctCreditID");
        localIntraTrnInfo.acctTypeTo = localFFSResultSet.getColumnString("AcctCreditType");
        localIntraTrnInfo.amount = localFFSResultSet.getColumnString("Amount");
        localIntraTrnInfo.curDef = localFFSResultSet.getColumnString("CurDef");
        localIntraTrnInfo.toAmount = localFFSResultSet.getColumnString("ToAmount");
        localIntraTrnInfo.toAmtCurrency = localFFSResultSet.getColumnString("ToAmountCurrency");
        localIntraTrnInfo.userAssignedAmount = UserAssignedAmount.getEnum(localFFSResultSet.getColumnInt("UserAssignedAmount"));
        localIntraTrnInfo.dateToPost = localFFSResultSet.getColumnString("DateToPost");
        localIntraTrnInfo.status = localFFSResultSet.getColumnString("Status");
        localIntraTrnInfo.setSubmittedBy(localFFSResultSet.getColumnString("SubmittedBy"));
        localIntraTrnInfo.eventId = "";
        localIntraTrnInfo.eventSequence = 1;
        localIntraTrnInfo.possibleDuplicate = false;
        localIntraTrnInfo.statusCode = 0;
        localIntraTrnInfo.statusMsg = BPWLocaleUtil.getMessage(0, null, "OFX_MESSAGE");
        localIntraTrnInfo.srvrTid = localFFSResultSet.getColumnString("SrvrTID");
        localIntraTrnInfo.recSrvrTid = localFFSResultSet.getColumnString("RecSrvrTID");
        localIntraTrnInfo.logId = localFFSResultSet.getColumnString("LogID");
        localIntraTrnInfo.fiId = localFFSResultSet.getColumnString("FIID");
        localIntraTrnInfo.fromBankId = localFFSResultSet.getColumnString("FromBankID");
        localIntraTrnInfo.fromBranchId = localFFSResultSet.getColumnString("FromBranchID");
        HashMap localHashMap = jdMethod_char(localIntraTrnInfo.srvrTid, paramFFSConnectionHolder);
        if ((localHashMap != null) && (localHashMap.size() != 0)) {
          localIntraTrnInfo.extraFields = localHashMap;
        }
        ((ArrayList)localObject2).add(localIntraTrnInfo);
      }
      int k = ((ArrayList)localObject2).size();
      if (k > 0)
      {
        arrayOfIntraTrnInfo = new IntraTrnInfo[k];
        arrayOfIntraTrnInfo = (IntraTrnInfo[])((ArrayList)localObject2).toArray(arrayOfIntraTrnInfo);
      }
      else
      {
        arrayOfIntraTrnInfo = new IntraTrnInfo[1];
        arrayOfIntraTrnInfo[0] = new IntraTrnInfo();
        arrayOfIntraTrnInfo[0].setStatusCode(16020);
        arrayOfIntraTrnInfo[0].setStatusMsg(" record not found");
      }
    }
    catch (Throwable localThrowable)
    {
      Object localObject1 = str1 + "failed: ";
      Object localObject2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1, (String)localObject2, 0);
      throw new FFSException(localThrowable, (String)localObject1);
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
      catch (Exception localException)
      {
        String str2 = str1 + "failed: ";
        String str3 = FFSDebug.stackTrace(localException);
        FFSDebug.log(str2, str3, 0);
      }
    }
    FFSDebug.log(str1, "Done", 6);
    return arrayOfIntraTrnInfo;
  }
  
  public static IntraTrnInfo[] getIntraById(String[] paramArrayOfString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("XferInstruction.getIntraById (multiple): start ", 6);
    ArrayList localArrayList = new ArrayList();
    IntraTrnInfo localIntraTrnInfo = null;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localIntraTrnInfo = new IntraTrnInfo();
      localIntraTrnInfo.statusCode = 16504;
      localIntraTrnInfo.statusMsg = BPWLocaleUtil.getMessage(16504, new String[] { "ID " }, "OFX_MESSAGE");
      FFSDebug.log("XferInstruction.getIntraById (multiple): failed  null or empty ID array passed", 0);
      IntraTrnInfo[] arrayOfIntraTrnInfo = { localIntraTrnInfo };
      return arrayOfIntraTrnInfo;
    }
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      localIntraTrnInfo = getIntraById(paramArrayOfString[j], paramFFSConnectionHolder);
      if (localIntraTrnInfo != null)
      {
        localArrayList.add(localIntraTrnInfo);
        paramFFSConnectionHolder.conn.commit();
      }
    }
    FFSDebug.log("XferInstruction.getIntraById (multiple) done", 6);
    return (IntraTrnInfo[])localArrayList.toArray(new IntraTrnInfo[0]);
  }
  
  private static HashMap jdMethod_char(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("XferInstruction.getXferExtraInfo start :srvrTID = " + paramString, 6);
    String str = null;
    HashMap localHashMap = null;
    if (paramFFSConnectionHolder == null)
    {
      str = "*** XferInstruction.getXferExtraInfo failed:ConnectionHolder is null ";
      FFSDebug.log(str, 0);
      throw new BPWException(str);
    }
    try
    {
      localHashMap = BPWXferExtraInfo.getHashMap(paramString, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      str = "*** XferInstruction.getXferExtraInfo failed:" + localException.toString();
      FFSDebug.log(str, 0);
      throw new BPWException(str);
    }
    FFSDebug.log("XferInstruction.getXferExtraInfo end :", 6);
    return localHashMap;
  }
  
  public static int cleanup(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws FFSException
  {
    String str1 = "XferInstruction.cleanup ";
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
      arrayOfString = getOldSrvrTID(paramFFSConnectionHolder, paramInt);
    }
    if (arrayOfString != null) {
      for (int k = 0; k < arrayOfString.length; k++)
      {
        EventInfoLog.delete(paramFFSConnectionHolder, arrayOfString[k], "INTRATRN");
        delete(paramFFSConnectionHolder, arrayOfString[k]);
        SrvrTrans.cancelSrvrTrans(arrayOfString[k], paramFFSConnectionHolder);
      }
    }
    FFSDebug.log(str1 + "end, No of rows deleted = " + i, 6);
    return i;
  }
  
  public static void createSessionDataPhyInstanceOfRecXfer(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "XferInstruction.createSessionDataPhyInstanceOfRecXfer";
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
      StringBuffer localStringBuffer = new StringBuffer("SELECT SrvrTID, BankID, BranchID, CustomerID, FIID, Amount, AcctDebitID, AcctDebitType, AcctCreditID, AcctCreditType, DateCreate, DateToPost, Status, LogID, ConfirmNum, RecSrvrTID, SubmittedBy, FromBankID, FromBranchID, CurDef, ToAmount, ToAmountCurrency, UserAssignedAmount FROM BPW_XferInstruction WHERE DateToPost <= ? AND DateToPost >= ? ");
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
      localStringBuffer.append(" AND RecSrvrTID IS NOT NULL");
      localStringBuffer.append(" ORDER BY CAST(SrvrTID AS INTEGER) ASC");
      FFSDebug.log(str1 + "Sql Statement: " + localStringBuffer.toString(), 6);
      for (int k = 0; k < paramArrayList.size(); k++) {
        FFSDebug.log(str1 + " Sql Param:" + k + " :" + String.valueOf(paramArrayList.get(k)), 6);
      }
      Object[] arrayOfObject = paramArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      int m = ak();
      while (localFFSResultSet.getNextRow())
      {
        j++;
        if (j > m)
        {
          paramPagingInfo.setStatusCode(28020);
          paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
          break;
        }
        boolean bool = true;
        String str4 = localFFSResultSet.getColumnString("RecSrvrTID");
        FFSDebug.log(str1 + "recSrvrTId: " + str4, 6);
        Object localObject1;
        Object localObject2;
        if ((str4 == null) || (str4.length() == 0))
        {
          localObject1 = localFFSResultSet.getColumnString("SrvrTID");
          if ((localObject1 != null) && (((String)localObject1).length() != 0))
          {
            localObject2 = RecSrvrTIDToSrvrTID.getRecSrvrTIDs(paramFFSConnectionHolder, (String)localObject1);
            FFSDebug.log(str1 + "recSrvrTIDs: " + localObject2, 6);
            if ((localObject2 != null) && (localObject2.length != 0)) {
              str4 = localObject2[0];
            }
          }
        }
        FFSDebug.log(str1 + "recSrvrTId2: " + str4, 6);
        if ((str4 != null) && (str4.length() != 0))
        {
          localRecTransferInfo = new RecTransferInfo();
          localObject1 = RecXferInstruction.getRecXferInstruction(paramFFSConnectionHolder, str4);
          FFSDebug.log(str1 + "recXfer: " + localObject1, 6);
          if (localObject1 != null)
          {
            FFSDebug.log(str1 + "recXfer.Frequency: " + ((RecXferInstruction)localObject1).Frequency, 6);
            localObject2 = FFSUtil.getFreqString(((RecXferInstruction)localObject1).Frequency);
            FFSDebug.log(str1 + "freqstr: " + (String)localObject2, 6);
            ((RecTransferInfo)localRecTransferInfo).setFrequency((String)localObject2);
          }
          localRecTransferInfo.setTransferType("Recurring");
          a(localRecTransferInfo, localFFSResultSet);
          TransferTempHist.createTempHist(paramFFSConnectionHolder, str3, j, localRecTransferInfo, bool);
        }
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
  
  public static void createSessionDataSingleXfer(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo, String paramString, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "XferInstruction.createSessionDataSingleXfer";
    FFSDebug.log(str1 + " : start", 6);
    FFSResultSet localFFSResultSet = null;
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      int j = paramPagingInfo.getTotalTrans();
      TransferInfo localTransferInfo = null;
      String str3 = paramPagingInfo.getPagingContext().getSessionId();
      if ((str3 == null) || (str3.trim().length() == 0))
      {
        str3 = DBUtil.getNextIndexString("SessionID");
        paramPagingInfo.getPagingContext().setSessionId(str3);
      }
      StringBuffer localStringBuffer = new StringBuffer("SELECT SrvrTID, BankID, BranchID, CustomerID, FIID, Amount, AcctDebitID, AcctDebitType, AcctCreditID, AcctCreditType, DateCreate, DateToPost, Status, LogID, ConfirmNum, RecSrvrTID, SubmittedBy, FromBankID, FromBranchID, CurDef, ToAmount, ToAmountCurrency, UserAssignedAmount FROM BPW_XferInstruction WHERE DateToPost <= ? AND DateToPost >= ? ");
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
      localStringBuffer.append(" AND RecSrvrTID IS NULL");
      localStringBuffer.append(" ORDER BY CAST(SrvrTID AS INTEGER) ASC");
      FFSDebug.log(str1 + "Sql Statement: " + localStringBuffer.toString(), 6);
      for (int k = 0; k < paramArrayList.size(); k++) {
        FFSDebug.log(str1 + " Sql Param:" + k + " :" + String.valueOf(paramArrayList.get(k)), 6);
      }
      Object[] arrayOfObject = paramArrayList.toArray();
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, localStringBuffer.toString(), arrayOfObject);
      int m = ak();
      while (localFFSResultSet.getNextRow())
      {
        j++;
        if (j > m)
        {
          paramPagingInfo.setStatusCode(28020);
          paramPagingInfo.setStatusMsg("Server found too much data based upon the search criteria. The number of data records reached the server maximum session size.Please narrow the search criteria to limit the number of records retrieved from the server.");
          break;
        }
        boolean bool = false;
        localTransferInfo = new TransferInfo();
        localTransferInfo.setTransferType("Current");
        a(localTransferInfo, localFFSResultSet);
        TransferTempHist.createTempHist(paramFFSConnectionHolder, str3, j, localTransferInfo, bool);
        if (j % i == 0) {
          paramFFSConnectionHolder.conn.commit();
        }
        paramPagingInfo.setTotalTrans(j);
        paramFFSConnectionHolder.conn.commit();
        FFSDebug.log(str1 + " : end", 6);
      }
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
  
  private static int ak()
  {
    if (wX == 0)
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      String str = localPropertyConfig.otherProperties.getProperty("bpw.paging.maximum.sessionsize", "1000");
      wX = Integer.parseInt(str);
    }
    return wX;
  }
  
  private static void a(TransferInfo paramTransferInfo, FFSResultSet paramFFSResultSet)
    throws Exception
  {
    paramTransferInfo.setSrvrTId(paramFFSResultSet.getColumnString("SrvrTID"));
    paramTransferInfo.setCustomerId(paramFFSResultSet.getColumnString("CustomerID"));
    paramTransferInfo.setSubmittedBy(paramFFSResultSet.getColumnString("SubmittedBy"));
    paramTransferInfo.setFIId(paramFFSResultSet.getColumnString("FIID"));
    paramTransferInfo.setAmount(paramFFSResultSet.getColumnString("Amount"));
    paramTransferInfo.setAmountCurrency(paramFFSResultSet.getColumnString("CurDef"));
    paramTransferInfo.setToAmount(paramFFSResultSet.getColumnString("ToAmount"));
    paramTransferInfo.setToAmountCurrency(paramFFSResultSet.getColumnString("ToAmountCurrency"));
    paramTransferInfo.setUserAssignedAmount(UserAssignedAmount.getEnum(paramFFSResultSet.getColumnInt("UserAssignedAmount")));
    paramTransferInfo.setAccountFromNum(paramFFSResultSet.getColumnString("AcctDebitID"));
    paramTransferInfo.setAccountFromType(paramFFSResultSet.getColumnString("AcctDebitType"));
    paramTransferInfo.setAccountToNum(paramFFSResultSet.getColumnString("AcctCreditID"));
    paramTransferInfo.setAccountToType(paramFFSResultSet.getColumnString("AcctCreditType"));
    paramTransferInfo.setDateToPost(paramFFSResultSet.getColumnString("DateToPost"));
    paramTransferInfo.setPrcStatus(paramFFSResultSet.getColumnString("Status"));
    paramTransferInfo.setTransferDest("INTERNAL");
    paramTransferInfo.setStatusCode(0);
  }
  
  private static String jdMethod_if(AccountTransactions paramAccountTransactions, String paramString, ArrayList paramArrayList)
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
      str2 = "SELECT SrvrTID,Amount,BankID,BranchID,AcctDebitID,AcctDebitType,AcctCreditID,AcctCreditType,DateCreate,DateToPost,CustomerID,Status,LogID,RecSrvrTID,FIID,SubmittedBy,LastModified,FromBankID,FromBranchID,CurDef,ToAmount,ToAmountCurrency,UserAssignedAmount FROM BPW_XferInstruction";
      str2 = jdMethod_if(paramAccountTransactions, str2, localArrayList1);
      Object[] arrayOfObject = localArrayList1.toArray();
      localFFSResultSet = DBUtil.openResultSet(localFFSConnectionHolder, str2, arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localObject1 = new TransferInfo();
        a((TransferInfo)localObject1, localFFSResultSet);
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
      str2 = "SELECT count(*) AS TRANSFERCOUNT FROM BPW_XferInstruction";
      str2 = jdMethod_if(paramAccountTransactions, str2, localArrayList);
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
 * Qualified Name:     com.ffusion.ffs.bpw.db.XferInstruction
 * JD-Core Version:    0.7.0.1
 */