package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.RecXferInstruction;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.ScheduleConstants;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsDateUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSyncRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeSyncRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTokenRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

public class XferSyncProcessor2
  implements DBConsts, OFXConsts, BPWResource, ScheduleConstants
{
  XferProcessor2 av = null;
  private PropertyConfig au;
  
  public XferSyncProcessor2(XferProcessor2 paramXferProcessor2)
  {
    this.av = paramXferProcessor2;
    this.au = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
  }
  
  public TypeIntraSyncRsV1 processIntraSyncRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    String str1 = "";
    try
    {
      String str2 = paramTypeIntraSyncRqV1.IntraSyncRq.SyncRqCm.TokenRqUn.__memberName;
      if (str2.equals("Refresh"))
      {
        localTypeIntraSyncRsV1 = processIntraRefreshRqV1(paramTypeIntraSyncRqV1, paramTypeUserData, str1);
      }
      else if (str2.equals("Token"))
      {
        localTypeIntraSyncRsV1 = processIntraTokenRqV1(paramTypeIntraSyncRqV1, paramTypeUserData, str1, localFFSConnectionHolder);
      }
      else
      {
        if (str2.equals("TokenOnly")) {
          throw new BPWException("This server doesn't support token only request.");
        }
        throw new BPWException("Unsupported sync option!");
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = "XferSyncProcessor.processIntraSyncRqV1 failed : ";
      FFSDebug.log(localException, str3);
      localTypeIntraSyncRsV1 = a(paramTypeIntraSyncRqV1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeIntraSyncRsV1;
  }
  
  private TypeIntraSyncRsV1 a(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1)
  {
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = new TypeIntraSyncRsV1();
    localTypeIntraSyncRsV1.IntraSyncRs = new TypeIntraSyncRsV1Aggregate();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsCm = new TypeSyncRsCm();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsCm.Token = "-1";
    localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn = new TypeBCCAcctFromUn();
    localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.__memberName = paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.__memberName;
    localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.CCAcctFrom = paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.CCAcctFrom;
    localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.BankAcctFrom = paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.BankAcctFrom;
    return localTypeIntraSyncRsV1;
  }
  
  public TypeIntraSyncRsV1 processIntraRefreshRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = new TypeIntraSyncRsV1();
    localTypeIntraSyncRsV1.IntraSyncRs = new TypeIntraSyncRsV1Aggregate();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsCm = new TypeSyncRsCm();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsCm.Token = "0";
    localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn = new TypeBCCAcctFromUn();
    localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.__memberName = paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.__memberName;
    localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.CCAcctFrom = paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.CCAcctFrom;
    localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.BankAcctFrom = paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.BankAcctFrom;
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    String str1;
    String str2;
    if (paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.__memberName.equals("BankAcctFrom"))
    {
      str1 = paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.BankAcctFrom.AcctID;
      str2 = MsgBuilder.getAcctType(paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.BankAcctFrom.AcctType);
    }
    else
    {
      str1 = paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.CCAcctFrom.AcctID;
      str2 = "CREDITCARD";
    }
    String str3 = MsgBuilder.getBankID(paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn);
    String[] arrayOfString = null;
    if (str3 == null) {
      arrayOfString = localAuditAgent.getSrvrTrans(paramTypeUserData._uid, str1, str2, "OFX200XferSync");
    } else {
      arrayOfString = localAuditAgent.getSrvrTrans(paramTypeUserData._uid, str1, str2, "OFX200XferSync", str3);
    }
    int i = arrayOfString.length;
    TypeIntraTrnRsV1[] arrayOfTypeIntraTrnRsV1 = new TypeIntraTrnRsV1[i];
    for (int j = 0; j < i; j++)
    {
      String str4 = (String)arrayOfString[j];
      BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
      arrayOfTypeIntraTrnRsV1[j] = ((TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg(str4, "IntraTrnRsV1", "OFX200"));
    }
    j = arrayOfTypeIntraTrnRsV1.length;
    if (paramTypeIntraSyncRqV1.IntraSyncRq.IntraTrnRq == null)
    {
      localTypeIntraSyncRsV1.IntraSyncRs.IntraTrnRs = new TypeIntraTrnRsV1Aggregate[j];
      for (k = 0; k < j; k++) {
        localTypeIntraSyncRsV1.IntraSyncRs.IntraTrnRs[k] = arrayOfTypeIntraTrnRsV1[k].IntraTrnRs;
      }
    }
    int k = paramTypeIntraSyncRqV1.IntraSyncRq.IntraTrnRq.length;
    int m = k + j;
    localTypeIntraSyncRsV1.IntraSyncRs.IntraTrnRs = new TypeIntraTrnRsV1Aggregate[m];
    for (int n = 0; n < k; n++)
    {
      TypeIntraTrnRqV1 localTypeIntraTrnRqV1 = new TypeIntraTrnRqV1(paramTypeIntraSyncRqV1.IntraSyncRq.IntraTrnRq[n]);
      localTypeIntraSyncRsV1.IntraSyncRs.IntraTrnRs[n] = this.av.processIntraTrnRqV1(localTypeIntraTrnRqV1, paramTypeUserData, paramString).IntraTrnRs;
    }
    for (n = k; n < k + j; n++) {
      localTypeIntraSyncRsV1.IntraSyncRs.IntraTrnRs[n] = arrayOfTypeIntraTrnRsV1[(n - k)].IntraTrnRs;
    }
    return localTypeIntraSyncRsV1;
  }
  
  public TypeIntraSyncRsV1 processIntraTokenRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    return processIntraRefreshRqV1(paramTypeIntraSyncRqV1, paramTypeUserData, paramString);
  }
  
  public TypeIntraSyncRsV1 processIntraTokenOnlyRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("XferSyncProcessor.processIntraTokenOnlyRqV1: begin", 6);
    String str1 = paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.BankAcctFrom.AcctID;
    String str2 = MsgBuilder.getAcctType(paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.BankAcctFrom.AcctType);
    int i = 0;
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = new TypeIntraSyncRsV1();
    localTypeIntraSyncRsV1.IntraSyncRs = new TypeIntraSyncRsV1Aggregate();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsCm = new TypeSyncRsCm();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsCm.Token = Integer.toString(i);
    localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.BankAcctFrom = paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.BankAcctFrom;
    FFSDebug.log("XferSyncProcessor.processIntraTokenOnlyRqV1: end", 6);
    return localTypeIntraSyncRsV1;
  }
  
  public String getUID(TypeUserData paramTypeUserData)
  {
    return paramTypeUserData._uid;
  }
  
  public TypeIntraSyncRsV1 processPendingIntraRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, int paramInt)
    throws Exception
  {
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = null;
    FFSDebug.log("XferSyncProcessor2.processPendingIntraRqV1: begin", 6);
    String str1;
    String str2;
    if (paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.__memberName.equals("BankAcctFrom"))
    {
      str1 = paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.BankAcctFrom.AcctID;
      str2 = MsgBuilder.getAcctType(paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.BankAcctFrom.AcctType);
    }
    else
    {
      str1 = paramTypeIntraSyncRqV1.IntraSyncRq.BCCAcctFromUn.CCAcctFrom.AcctID;
      str2 = "CREDITCARD";
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localTypeIntraSyncRsV1 = jdMethod_do(paramTypeUserData._uid, str1, str2, paramInt, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      String str3 = "XferSyncProcessor.getPendingIntrasByCustomerID failed : ";
      throw new Exception(str3 + localException.getMessage());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeIntraSyncRsV1;
  }
  
  private TypeIntraSyncRsV1 jdMethod_do(String paramString1, String paramString2, String paramString3, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("XferSyncProcessor2.processPendingIntraRqV1: pendingDay=" + paramInt + ",AcctID=" + paramString2 + ",uid=" + paramString1, 6);
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = new TypeIntraSyncRsV1();
    localTypeIntraSyncRsV1.IntraSyncRs = new TypeIntraSyncRsV1Aggregate();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsCm = new TypeSyncRsCm();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsCm.Token = "0";
    localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn = new TypeBCCAcctFromUn();
    if (paramString3.equals("CREDITCARD"))
    {
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.__memberName = "CCAcctFrom";
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.CCAcctFrom = new TypeCCAcctFromAggregate();
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.CCAcctFrom.AcctID = paramString2;
    }
    else
    {
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.__memberName = "BankAcctFrom";
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.BankAcctFrom = new TypeBankAcctFromAggregate();
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.BankAcctFrom.AcctID = paramString2;
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.BankAcctFrom.AcctType = MsgBuilder.getOFX200AcctEnum(paramString3);
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    int i = ScheduleInfo.getInstanceDate(0);
    int j = ScheduleInfo.getInstanceDate(paramInt);
    String[] arrayOfString1 = localAuditAgent.getPendingSrvrTrans(paramString1, paramString2, paramString3, "OFX200XferSync");
    int k = arrayOfString1.length;
    FFSDebug.log("XferSyncProcessor2.processPendingIntraRqV1: getPendingSrvrTrans=" + k, 6);
    Vector localVector = new Vector();
    Object localObject;
    for (int m = 0; m < k; m++)
    {
      String str1 = (String)arrayOfString1[m];
      TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = (TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg(str1, "IntraTrnRsV1", "OFX200");
      if (localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo.DtDue == null) {
        localObject = localSimpleDateFormat.parse(localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferPrcSts.DtXferPrc.substring(0, 8) + "000000", new ParsePosition(0));
      } else {
        localObject = localSimpleDateFormat.parse(localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
      }
      int i1 = ScheduleInfo.convertInstanceDateToNum((Date)localObject);
      FFSDebug.log("XferSyncProcessor2.processPendingPmtRqV1: dtDue=" + i1, 6);
      if (i1 <= j) {
        localVector.addElement(localTypeIntraTrnRsV1.IntraTrnRs);
      }
    }
    arrayOfString1 = localAuditAgent.getPendingRecSrvrTrans(paramString1, paramString2, paramString3, "OFX200RecXferSync");
    k = arrayOfString1.length;
    FFSDebug.log("XferSyncProcessor2.processPendingIntraRqV1: getPendingRecSrvrTrans=" + k, 6);
    for (int n = 0; n < k; n++)
    {
      localObject = (String)arrayOfString1[n];
      TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = (TypeRecIntraTrnRsV1)localBPWMsgBroker.parseMsg((String)localObject, "RecIntraTrnRsV1", "OFX200");
      String str2 = "1000";
      RecXferInstruction localRecXferInstruction = RecXferInstruction.getRecXferInstruction(paramFFSConnectionHolder, localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.RecSrvrTID);
      str2 = localRecXferInstruction.FIID;
      ScheduleInfo localScheduleInfo = new ScheduleInfo();
      Date localDate = localSimpleDateFormat.parse(localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.XferInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
      localScheduleInfo.NextInstanceDate = ScheduleInfo.convertInstanceDateToNum(localDate);
      localScheduleInfo.Frequency = CommonProcessor.mapOFX200FreqToBPWFreq(localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst.Freq.value());
      localScheduleInfo.InstanceCount = localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst.NInsts;
      localScheduleInfo.CurInstanceNum = 1;
      if (!localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst.NInstsExists) {
        localScheduleInfo.Perpetual = 1;
      }
      String[] arrayOfString2 = ScheduleInfo.getPendingDates(localScheduleInfo, paramInt);
      for (int i2 = 0; i2 < arrayOfString2.length; i2++)
      {
        int i3 = Integer.parseInt(arrayOfString2[i2].substring(0, 8));
        int i4 = SmartCalendar.getPayday(str2, i3, "BookTransfer") * 100;
        TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
        localTypeIntraTrnRsV1Aggregate.TrnRsCm = localTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsCm;
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsUnExists = true;
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn = new TypeIntraTrnRsUn();
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.__preValueTag = "INTRARS";
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.__memberName = "IntraRs";
        TypeIntraRsAggregate localTypeIntraRsAggregate = localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs;
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs = new TypeIntraRsAggregate(localTypeIntraRsAggregate.CurDef, localTypeIntraRsAggregate.SrvrTID, localTypeIntraRsAggregate.XferInfo, localTypeIntraRsAggregate.IntraRsDateUn, localTypeIntraRsAggregate.IntraRsDateUnExists, localTypeIntraRsAggregate.RecSrvrTID, localTypeIntraRsAggregate.RecSrvrTIDExists, localTypeIntraRsAggregate.XferPrcSts, localTypeIntraRsAggregate.XferPrcStsExists);
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.SrvrTID = "0";
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferInfo = new TypeXferInfoAggregate(localTypeIntraRsAggregate.XferInfo.BCCAcctFromUn, localTypeIntraRsAggregate.XferInfo.BCCAcctToUn, localTypeIntraRsAggregate.XferInfo.TrnAmt, localTypeIntraRsAggregate.XferInfo.DtDue, localTypeIntraRsAggregate.XferInfo.DtDueExists);
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferInfo.DtDue = arrayOfString2[i2];
        if ((localTypeIntraRsAggregate.XferInfo.DtDueExists) && (localTypeIntraRsAggregate.IntraRsDateUn.DtPosted == null))
        {
          localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.IntraRsDateUn = new TypeIntraRsDateUn(localTypeIntraRsAggregate.IntraRsDateUn.__preValueTag, localTypeIntraRsAggregate.IntraRsDateUn.__memberName, localTypeIntraRsAggregate.IntraRsDateUn.DtXferPrj, localTypeIntraRsAggregate.IntraRsDateUn.DtPosted);
          localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.IntraRsDateUn.DtXferPrj = ("" + i4 + "0000");
        }
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferPrcSts = new TypeXferPrcStsAggregate();
        RsCmBuilder.updateRsXferPrcSts("WILLPROCESSON", arrayOfString2[i2], localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferPrcSts);
        localVector.addElement(localTypeIntraTrnRsV1Aggregate);
      }
    }
    localTypeIntraSyncRsV1.IntraSyncRs.IntraTrnRs = ((TypeIntraTrnRsV1Aggregate[])localVector.toArray(new TypeIntraTrnRsV1Aggregate[0]));
    return localTypeIntraSyncRsV1;
  }
  
  public TypeIntraSyncRsV1[] getPendingIntrasByCustomerID(String paramString, int paramInt)
    throws Exception
  {
    FFSDebug.log("XferSyncProcessor2.getPendingIntrasByCustomerID: custId=" + paramString + ",pendingDay=" + paramInt, 6);
    TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = null;
    ArrayList localArrayList = new ArrayList();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      SrvrTrans[] arrayOfSrvrTrans = SrvrTrans.getAcctByCustomerID(paramString, "OFX200XferSync", localFFSConnectionHolder);
      for (int i = 0; i < arrayOfSrvrTrans.length; i++)
      {
        String str2 = arrayOfSrvrTrans[i].getAcctID();
        String str3 = arrayOfSrvrTrans[i].getAcctType();
        TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = jdMethod_do(paramString, str2, str3, paramInt, localFFSConnectionHolder);
        localArrayList.add(localTypeIntraSyncRsV1);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      String str1 = "XferSyncProcessor.getPendingIntrasByCustomerID failed : ";
      throw new Exception(str1 + localException.getMessage());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    arrayOfTypeIntraSyncRsV1 = (TypeIntraSyncRsV1[])localArrayList.toArray(new TypeIntraSyncRsV1[0]);
    return arrayOfTypeIntraSyncRsV1;
  }
  
  public TypeIntraSyncRsV1[] getPendingIntrasAndHistoryByCustomerID(String paramString, int paramInt1, int paramInt2)
    throws Exception
  {
    FFSDebug.log("XferSyncProcessor2.getPendingIntrasAndHistoryByCustomerID: custId=" + paramString + ",pendingDay=" + paramInt1 + ",pastDay=" + paramInt2, 6);
    TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = null;
    ArrayList localArrayList = new ArrayList();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      SrvrTrans[] arrayOfSrvrTrans = SrvrTrans.getAcctByCustomerID(paramString, "OFX200XferSync", localFFSConnectionHolder);
      for (int i = 0; i < arrayOfSrvrTrans.length; i++)
      {
        String str2 = arrayOfSrvrTrans[i].getAcctID();
        String str3 = arrayOfSrvrTrans[i].getAcctType();
        TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = jdMethod_do(paramString, str2, str3, paramInt1, paramInt2, localFFSConnectionHolder);
        localArrayList.add(localTypeIntraSyncRsV1);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      String str1 = "XferSyncProcessor2.getPendingIntrasAndHistoryByCustomerID failed : ";
      throw new Exception(str1 + localException.getMessage());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    arrayOfTypeIntraSyncRsV1 = (TypeIntraSyncRsV1[])localArrayList.toArray(new TypeIntraSyncRsV1[0]);
    return arrayOfTypeIntraSyncRsV1;
  }
  
  private TypeIntraSyncRsV1 jdMethod_do(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("XferSyncProcessor2.processPendingIntraAndHistoryRqV1: pendingDay=" + paramInt1 + ",AcctID=" + paramString2 + ",uid=" + paramString1, 6);
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = new TypeIntraSyncRsV1();
    localTypeIntraSyncRsV1.IntraSyncRs = new TypeIntraSyncRsV1Aggregate();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsCm = new TypeSyncRsCm();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsCm.Token = "0";
    localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn = new TypeBCCAcctFromUn();
    if (paramString3.equals("CREDITCARD"))
    {
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.__memberName = "CCAcctFrom";
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.CCAcctFrom = new TypeCCAcctFromAggregate();
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.CCAcctFrom.AcctID = paramString2;
    }
    else
    {
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.__memberName = "BankAcctFrom";
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.BankAcctFrom = new TypeBankAcctFromAggregate();
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.BankAcctFrom.AcctID = paramString2;
      localTypeIntraSyncRsV1.IntraSyncRs.BCCAcctFromUn.BankAcctFrom.AcctType = MsgBuilder.getOFX200AcctEnum(paramString3);
    }
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
    String[] arrayOfString1 = localAuditAgent.getSrvrTrans(paramString1, paramString2, paramString3, "OFX200XferSync");
    int k = arrayOfString1.length;
    FFSDebug.log("XferSyncProcessor2.processPendingIntraAndHistoryRqV1: getSrvrTrans=" + k, 6);
    Vector localVector = new Vector();
    Object localObject;
    for (int m = 0; m < k; m++)
    {
      String str1 = (String)arrayOfString1[m];
      TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = (TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg(str1, "IntraTrnRsV1", "OFX200");
      if (localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo.DtDue == null) {
        localObject = localSimpleDateFormat.parse(localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferPrcSts.DtXferPrc.substring(0, 8) + "000000", new ParsePosition(0));
      } else {
        localObject = localSimpleDateFormat.parse(localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
      }
      int i1 = ScheduleInfo.convertInstanceDateToNum((Date)localObject);
      FFSDebug.log("XferSyncProcessor2.processPendingIntraAndHistoryRqV1: dtDue=" + i1 + ",dtPending=" + j + ",dtPast=" + i, 6);
      if ((i1 <= j) && (i1 > i)) {
        localVector.addElement(localTypeIntraTrnRsV1.IntraTrnRs);
      }
    }
    if (paramInt1 > 0)
    {
      arrayOfString1 = localAuditAgent.getPendingRecSrvrTrans(paramString1, paramString2, paramString3, "OFX200RecXferSync");
      k = arrayOfString1.length;
      FFSDebug.log("XferSyncProcessor2.processPendingIntraAndHistoryRqV1: getPendingRecSrvrTrans=" + k, 6);
      for (int n = 0; n < k; n++)
      {
        localObject = (String)arrayOfString1[n];
        TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = (TypeRecIntraTrnRsV1)localBPWMsgBroker.parseMsg((String)localObject, "RecIntraTrnRsV1", "OFX200");
        String str2 = "1000";
        RecXferInstruction localRecXferInstruction = RecXferInstruction.getRecXferInstruction(paramFFSConnectionHolder, localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.RecSrvrTID);
        str2 = localRecXferInstruction.FIID;
        ScheduleInfo localScheduleInfo = new ScheduleInfo();
        Date localDate = localSimpleDateFormat.parse(localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs.XferInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
        localScheduleInfo.NextInstanceDate = ScheduleInfo.convertInstanceDateToNum(localDate);
        localScheduleInfo.Frequency = CommonProcessor.mapOFX200FreqToBPWFreq(localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst.Freq.value());
        localScheduleInfo.InstanceCount = localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst.NInsts;
        localScheduleInfo.CurInstanceNum = 1;
        if (!localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecurrInst.NInstsExists) {
          localScheduleInfo.Perpetual = 1;
        }
        String[] arrayOfString2 = ScheduleInfo.getPendingDates(localScheduleInfo, paramInt1);
        for (int i2 = 0; i2 < arrayOfString2.length; i2++)
        {
          int i3 = Integer.parseInt(arrayOfString2[i2].substring(0, 8));
          int i4 = SmartCalendar.getPayday(str2, i3, "BookTransfer") * 100;
          if (i4 >= i)
          {
            TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
            localTypeIntraTrnRsV1Aggregate.TrnRsCm = localTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsCm;
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsUnExists = true;
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn = new TypeIntraTrnRsUn();
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.__preValueTag = "INTRARS";
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.__memberName = "IntraRs";
            TypeIntraRsAggregate localTypeIntraRsAggregate = localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.IntraRs;
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs = new TypeIntraRsAggregate(localTypeIntraRsAggregate.CurDef, localTypeIntraRsAggregate.SrvrTID, localTypeIntraRsAggregate.XferInfo, localTypeIntraRsAggregate.IntraRsDateUn, localTypeIntraRsAggregate.IntraRsDateUnExists, localTypeIntraRsAggregate.RecSrvrTID, localTypeIntraRsAggregate.RecSrvrTIDExists, localTypeIntraRsAggregate.XferPrcSts, localTypeIntraRsAggregate.XferPrcStsExists);
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.SrvrTID = "0";
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferInfo = new TypeXferInfoAggregate(localTypeIntraRsAggregate.XferInfo.BCCAcctFromUn, localTypeIntraRsAggregate.XferInfo.BCCAcctToUn, localTypeIntraRsAggregate.XferInfo.TrnAmt, localTypeIntraRsAggregate.XferInfo.DtDue, localTypeIntraRsAggregate.XferInfo.DtDueExists);
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferInfo.DtDue = arrayOfString2[i2];
            if ((localTypeIntraRsAggregate.XferInfo.DtDueExists) && (localTypeIntraRsAggregate.IntraRsDateUn.DtPosted == null))
            {
              localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.IntraRsDateUn = new TypeIntraRsDateUn(localTypeIntraRsAggregate.IntraRsDateUn.__preValueTag, localTypeIntraRsAggregate.IntraRsDateUn.__memberName, localTypeIntraRsAggregate.IntraRsDateUn.DtXferPrj, localTypeIntraRsAggregate.IntraRsDateUn.DtPosted);
              localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.IntraRsDateUn.DtXferPrj = ("" + i4 + "0000");
            }
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferPrcSts = new TypeXferPrcStsAggregate();
            RsCmBuilder.updateRsXferPrcSts("WILLPROCESSON", arrayOfString2[i2], localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferPrcSts);
            localVector.addElement(localTypeIntraTrnRsV1Aggregate);
          }
        }
      }
    }
    localTypeIntraSyncRsV1.IntraSyncRs.IntraTrnRs = ((TypeIntraTrnRsV1Aggregate[])localVector.toArray(new TypeIntraTrnRsV1Aggregate[0]));
    return localTypeIntraSyncRsV1;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.XferSyncProcessor2
 * JD-Core Version:    0.7.0.1
 */