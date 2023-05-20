package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.RecXferInstruction;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.db.TempHist;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWExtdHist;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.ScheduleConstants;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1DateUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraSyncRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRqV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeSyncRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTokenRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferPrcStsAggregate;
import com.ffusion.util.beans.PagingContext;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

public class XferSyncProcessor
  implements DBConsts, OFXConsts, BPWResource, ScheduleConstants
{
  XferProcessor x = null;
  
  public XferSyncProcessor(XferProcessor paramXferProcessor)
  {
    this.x = paramXferProcessor;
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
      String str2 = paramTypeIntraSyncRqV1.IntraSyncRq.SyncRqV1Cm.TokenRqV1Un.__memberName;
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
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsV1Cm.Token = "-1";
    localTypeIntraSyncRsV1.IntraSyncRs.BankAcctFrom = paramTypeIntraSyncRqV1.IntraSyncRq.BankAcctFrom;
    return localTypeIntraSyncRsV1;
  }
  
  public TypeIntraSyncRsV1 processIntraRefreshRqV1(TypeIntraSyncRqV1 paramTypeIntraSyncRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = new TypeIntraSyncRsV1();
    localTypeIntraSyncRsV1.IntraSyncRs = new TypeIntraSyncRsV1Aggregate();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsV1Cm.Token = "0";
    localTypeIntraSyncRsV1.IntraSyncRs.BankAcctFrom = paramTypeIntraSyncRqV1.IntraSyncRq.BankAcctFrom;
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    String str1 = paramTypeIntraSyncRqV1.IntraSyncRq.BankAcctFrom.AcctID;
    String str2 = MsgBuilder.getAcctType(paramTypeIntraSyncRqV1.IntraSyncRq.BankAcctFrom.AcctType);
    String str3 = paramTypeIntraSyncRqV1.IntraSyncRq.BankAcctFrom.BankID;
    String[] arrayOfString = localAuditAgent.getSrvrTrans(paramTypeUserData._uid, str1, str2, "OFX151XferSync", str3);
    int i = arrayOfString.length;
    TypeIntraTrnRsV1[] arrayOfTypeIntraTrnRsV1 = new TypeIntraTrnRsV1[i];
    for (int j = 0; j < i; j++)
    {
      String str4 = (String)arrayOfString[j];
      BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
      arrayOfTypeIntraTrnRsV1[j] = ((TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg(str4, "IntraTrnRsV1", "OFX151"));
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
      localTypeIntraSyncRsV1.IntraSyncRs.IntraTrnRs[n] = this.x.processIntraTrnRqV1(localTypeIntraTrnRqV1, paramTypeUserData, paramString).IntraTrnRs;
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
    int i = 0;
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = new TypeIntraSyncRsV1();
    localTypeIntraSyncRsV1.IntraSyncRs = new TypeIntraSyncRsV1Aggregate();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsV1Cm.Token = Integer.toString(i);
    localTypeIntraSyncRsV1.IntraSyncRs.BankAcctFrom = paramTypeIntraSyncRqV1.IntraSyncRq.BankAcctFrom;
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
    FFSDebug.log("XferSyncProcessor.processPendingIntraRqV1: begin", 6);
    String str1 = paramTypeIntraSyncRqV1.IntraSyncRq.BankAcctFrom.AcctID;
    String str2 = MsgBuilder.getAcctType(paramTypeIntraSyncRqV1.IntraSyncRq.BankAcctFrom.AcctType);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      localTypeIntraSyncRsV1 = jdMethod_if(paramTypeUserData._uid, str1, str2, paramInt, localFFSConnectionHolder);
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
  
  private TypeIntraSyncRsV1 jdMethod_if(String paramString1, String paramString2, String paramString3, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("XferSyncProcessor.processPendingIntraRqV1: pendingDay=" + paramInt + ",AcctID=" + paramString2 + ",uid=" + paramString1, 6);
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = new TypeIntraSyncRsV1();
    localTypeIntraSyncRsV1.IntraSyncRs = new TypeIntraSyncRsV1Aggregate();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsV1Cm.Token = "0";
    localTypeIntraSyncRsV1.IntraSyncRs.BankAcctFrom = new TypeBankAcctFromV1Aggregate();
    localTypeIntraSyncRsV1.IntraSyncRs.BankAcctFrom.AcctID = paramString2;
    localTypeIntraSyncRsV1.IntraSyncRs.BankAcctFrom.AcctType = MsgBuilder.getOFX151AcctEnum(paramString3);
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    int i = ScheduleInfo.getInstanceDate(paramInt);
    String[] arrayOfString1 = localAuditAgent.getPendingSrvrTrans(paramString1, paramString2, paramString3, "OFX151XferSync");
    int j = arrayOfString1.length;
    FFSDebug.log("XferSyncProcessor.processPendingIntraRqV1: getPendingSrvrTrans=" + j, 6);
    Vector localVector = new Vector();
    Object localObject;
    for (int k = 0; k < j; k++)
    {
      String str1 = (String)arrayOfString1[k];
      TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = (TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg(str1, "IntraTrnRsV1", "OFX151");
      if (localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo.DtDue == null) {
        localObject = localSimpleDateFormat.parse(localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferPrcSts.DtXferPrc.substring(0, 8) + "000000", new ParsePosition(0));
      } else {
        localObject = localSimpleDateFormat.parse(localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
      }
      int n = ScheduleInfo.convertInstanceDateToNum((Date)localObject);
      FFSDebug.log("XferSyncProcessor.processPendingIntraRqV1: dtDue=" + n, 6);
      if (n <= i) {
        localVector.addElement(localTypeIntraTrnRsV1.IntraTrnRs);
      }
    }
    arrayOfString1 = localAuditAgent.getPendingRecSrvrTrans(paramString1, paramString2, paramString3, "OFX151RecXferSync");
    j = arrayOfString1.length;
    FFSDebug.log("XferSyncProcessor.processPendingIntraRqV1: getPendingRecSrvrTrans=" + j, 6);
    for (int m = 0; m < j; m++)
    {
      localObject = (String)arrayOfString1[m];
      TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = (TypeRecIntraTrnRsV1)localBPWMsgBroker.parseMsg((String)localObject, "RecIntraTrnRsV1", "OFX151");
      String str2 = "1000";
      RecXferInstruction localRecXferInstruction = RecXferInstruction.getRecXferInstruction(paramFFSConnectionHolder, localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.RecSrvrTID);
      str2 = localRecXferInstruction.FIID;
      ScheduleInfo localScheduleInfo = new ScheduleInfo();
      Date localDate = localSimpleDateFormat.parse(localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.XferInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
      localScheduleInfo.NextInstanceDate = ScheduleInfo.convertInstanceDateToNum(localDate);
      localScheduleInfo.Frequency = CommonProcessor.mapOFX151FreqToBPWFreq(localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst.Freq.value());
      localScheduleInfo.InstanceCount = localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst.NInsts;
      localScheduleInfo.CurInstanceNum = 1;
      if (!localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst.NInstsExists) {
        localScheduleInfo.Perpetual = 1;
      }
      String[] arrayOfString2 = ScheduleInfo.getPendingDates(localScheduleInfo, paramInt);
      for (int i1 = 0; i1 < arrayOfString2.length; i1++)
      {
        int i2 = Integer.parseInt(arrayOfString2[i1].substring(0, 8));
        int i3 = SmartCalendar.getPayday(str2, i2, "BookTransfer") * 100;
        TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
        localTypeIntraTrnRsV1Aggregate.TrnRsV1Cm = localTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsV1Cm;
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1UnExists = true;
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un = new TypeIntraTrnRsV1Un();
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.__preValueTag = "INTRARS";
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.__memberName = "IntraRs";
        TypeIntraRsV1Aggregate localTypeIntraRsV1Aggregate = localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs;
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs = new TypeIntraRsV1Aggregate(localTypeIntraRsV1Aggregate.CurDef, localTypeIntraRsV1Aggregate.SrvrTID, localTypeIntraRsV1Aggregate.XferInfo, localTypeIntraRsV1Aggregate.IntraRsV1DateUn, localTypeIntraRsV1Aggregate.IntraRsV1DateUnExists, localTypeIntraRsV1Aggregate.RecSrvrTID, localTypeIntraRsV1Aggregate.RecSrvrTIDExists, localTypeIntraRsV1Aggregate.XferPrcSts, localTypeIntraRsV1Aggregate.XferPrcStsExists);
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.SrvrTID = "0";
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferInfo = new TypeXferInfoV1Aggregate(localTypeIntraRsV1Aggregate.XferInfo.BCCAcctFromV1Un, localTypeIntraRsV1Aggregate.XferInfo.BCCAcctToV1Un, localTypeIntraRsV1Aggregate.XferInfo.TrnAmt, localTypeIntraRsV1Aggregate.XferInfo.DtDue, localTypeIntraRsV1Aggregate.XferInfo.DtDueExists);
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferInfo.DtDue = arrayOfString2[i1];
        if ((localTypeIntraRsV1Aggregate.XferInfo.DtDueExists) && (localTypeIntraRsV1Aggregate.IntraRsV1DateUn.DtPosted == null))
        {
          localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.IntraRsV1DateUn = new TypeIntraRsV1DateUn(localTypeIntraRsV1Aggregate.IntraRsV1DateUn.__preValueTag, localTypeIntraRsV1Aggregate.IntraRsV1DateUn.__memberName, localTypeIntraRsV1Aggregate.IntraRsV1DateUn.DtXferPrj, localTypeIntraRsV1Aggregate.IntraRsV1DateUn.DtPosted);
          localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.IntraRsV1DateUn.DtXferPrj = ("" + i3 + "0000");
        }
        localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferPrcSts = new TypeXferPrcStsAggregate();
        RsCmBuilder.updateRsXferPrcSts("WILLPROCESSON", arrayOfString2[i1], localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferPrcSts);
        localVector.addElement(localTypeIntraTrnRsV1Aggregate);
      }
    }
    localTypeIntraSyncRsV1.IntraSyncRs.IntraTrnRs = ((TypeIntraTrnRsV1Aggregate[])localVector.toArray(new TypeIntraTrnRsV1Aggregate[0]));
    return localTypeIntraSyncRsV1;
  }
  
  public TypeIntraSyncRsV1[] getPendingIntrasByCustomerID(String paramString, int paramInt)
    throws Exception
  {
    FFSDebug.log("XferSyncProcessor.getPendingIntrasByCustomerID: custId=" + paramString + ",pendingDay=" + paramInt, 6);
    TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = null;
    ArrayList localArrayList = new ArrayList();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      SrvrTrans[] arrayOfSrvrTrans = SrvrTrans.getAcctByCustomerID(paramString, "OFX151XferSync", localFFSConnectionHolder);
      for (int i = 0; i < arrayOfSrvrTrans.length; i++)
      {
        String str2 = arrayOfSrvrTrans[i].getAcctID();
        String str3 = arrayOfSrvrTrans[i].getAcctType();
        TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = jdMethod_if(paramString, str2, str3, paramInt, localFFSConnectionHolder);
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
    FFSDebug.log("XferSyncProcessor.getPendingIntrasAndHistoryByCustomerID: custId=" + paramString + ",pendingDay=" + paramInt1 + ",pastDay=" + paramInt2, 6);
    TypeIntraSyncRsV1[] arrayOfTypeIntraSyncRsV1 = null;
    ArrayList localArrayList = new ArrayList();
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      SrvrTrans[] arrayOfSrvrTrans = SrvrTrans.getAcctByCustomerID(paramString, "OFX151XferSync", localFFSConnectionHolder);
      for (int i = 0; i < arrayOfSrvrTrans.length; i++)
      {
        String str2 = arrayOfSrvrTrans[i].getAcctID();
        String str3 = arrayOfSrvrTrans[i].getAcctType();
        TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = jdMethod_if(paramString, str2, str3, paramInt1, paramInt2, localFFSConnectionHolder);
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
  
  private TypeIntraSyncRsV1 jdMethod_if(String paramString1, String paramString2, String paramString3, int paramInt1, int paramInt2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("XferSyncProcessor.processPendingIntraAndHistoryRqV1: pendingDay=" + paramInt1 + ",AcctID=" + paramString2 + ",uid=" + paramString1, 6);
    TypeIntraSyncRsV1 localTypeIntraSyncRsV1 = new TypeIntraSyncRsV1();
    localTypeIntraSyncRsV1.IntraSyncRs = new TypeIntraSyncRsV1Aggregate();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsV1Cm = new TypeSyncRsV1Cm();
    localTypeIntraSyncRsV1.IntraSyncRs.SyncRsV1Cm.Token = "0";
    localTypeIntraSyncRsV1.IntraSyncRs.BankAcctFrom = new TypeBankAcctFromV1Aggregate();
    localTypeIntraSyncRsV1.IntraSyncRs.BankAcctFrom.AcctID = paramString2;
    localTypeIntraSyncRsV1.IntraSyncRs.BankAcctFrom.AcctType = MsgBuilder.getOFX151AcctEnum(paramString3);
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
    String[] arrayOfString1 = localAuditAgent.getSrvrTrans(paramString1, paramString2, paramString3, "OFX151XferSync");
    int k = arrayOfString1.length;
    FFSDebug.log("XferSyncProcessor.processPendingIntraAndHistoryRqV1: getSrvrTrans=" + k, 6);
    Vector localVector = new Vector();
    Object localObject;
    for (int m = 0; m < k; m++)
    {
      String str1 = (String)arrayOfString1[m];
      TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = (TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg(str1, "IntraTrnRsV1", "OFX151");
      if (localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo.DtDue == null) {
        localObject = localSimpleDateFormat.parse(localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferPrcSts.DtXferPrc.substring(0, 8) + "000000", new ParsePosition(0));
      } else {
        localObject = localSimpleDateFormat.parse(localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
      }
      int i1 = ScheduleInfo.convertInstanceDateToNum((Date)localObject);
      FFSDebug.log("XferSyncProcessor.processPendingIntraAndHistoryRqV1: dtDue=" + i1 + ",dtPending=" + j + ",dtPast=" + i, 6);
      if ((i1 <= j) && (i1 >= i)) {
        localVector.addElement(localTypeIntraTrnRsV1.IntraTrnRs);
      }
    }
    if (paramInt1 > 0)
    {
      arrayOfString1 = localAuditAgent.getPendingRecSrvrTrans(paramString1, paramString2, paramString3, "OFX151RecXferSync");
      k = arrayOfString1.length;
      FFSDebug.log("XferSyncProcessor.processPendingIntraAndHistoryRqV1: getPendingRecSrvrTrans=" + k, 6);
      for (int n = 0; n < k; n++)
      {
        localObject = (String)arrayOfString1[n];
        TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = (TypeRecIntraTrnRsV1)localBPWMsgBroker.parseMsg((String)localObject, "RecIntraTrnRsV1", "OFX151");
        String str2 = "1000";
        RecXferInstruction localRecXferInstruction = RecXferInstruction.getRecXferInstruction(paramFFSConnectionHolder, localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.RecSrvrTID);
        str2 = localRecXferInstruction.FIID;
        ScheduleInfo localScheduleInfo = new ScheduleInfo();
        Date localDate = localSimpleDateFormat.parse(localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs.XferInfo.DtDue.substring(0, 8) + "000000", new ParsePosition(0));
        localScheduleInfo.NextInstanceDate = ScheduleInfo.convertInstanceDateToNum(localDate);
        localScheduleInfo.Frequency = CommonProcessor.mapOFX151FreqToBPWFreq(localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst.Freq.value());
        localScheduleInfo.InstanceCount = localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst.NInsts;
        localScheduleInfo.CurInstanceNum = 1;
        if (!localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecurrInst.NInstsExists) {
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
            localTypeIntraTrnRsV1Aggregate.TrnRsV1Cm = localTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsV1Cm;
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1UnExists = true;
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un = new TypeIntraTrnRsV1Un();
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.__preValueTag = "INTRARS";
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.__memberName = "IntraRs";
            TypeIntraRsV1Aggregate localTypeIntraRsV1Aggregate = localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.IntraRs;
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs = new TypeIntraRsV1Aggregate(localTypeIntraRsV1Aggregate.CurDef, localTypeIntraRsV1Aggregate.SrvrTID, localTypeIntraRsV1Aggregate.XferInfo, localTypeIntraRsV1Aggregate.IntraRsV1DateUn, localTypeIntraRsV1Aggregate.IntraRsV1DateUnExists, localTypeIntraRsV1Aggregate.RecSrvrTID, localTypeIntraRsV1Aggregate.RecSrvrTIDExists, localTypeIntraRsV1Aggregate.XferPrcSts, localTypeIntraRsV1Aggregate.XferPrcStsExists);
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.SrvrTID = "0";
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferInfo = new TypeXferInfoV1Aggregate(localTypeIntraRsV1Aggregate.XferInfo.BCCAcctFromV1Un, localTypeIntraRsV1Aggregate.XferInfo.BCCAcctToV1Un, localTypeIntraRsV1Aggregate.XferInfo.TrnAmt, localTypeIntraRsV1Aggregate.XferInfo.DtDue, localTypeIntraRsV1Aggregate.XferInfo.DtDueExists);
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferInfo.DtDue = arrayOfString2[i2];
            if ((localTypeIntraRsV1Aggregate.XferInfo.DtDueExists) && (localTypeIntraRsV1Aggregate.IntraRsV1DateUn.DtPosted == null))
            {
              localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.IntraRsV1DateUn = new TypeIntraRsV1DateUn(localTypeIntraRsV1Aggregate.IntraRsV1DateUn.__preValueTag, localTypeIntraRsV1Aggregate.IntraRsV1DateUn.__memberName, localTypeIntraRsV1Aggregate.IntraRsV1DateUn.DtXferPrj, localTypeIntraRsV1Aggregate.IntraRsV1DateUn.DtPosted);
              localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.IntraRsV1DateUn.DtXferPrj = ("" + i4 + "0000");
            }
            localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferPrcSts = new TypeXferPrcStsAggregate();
            RsCmBuilder.updateRsXferPrcSts("WILLPROCESSON", arrayOfString2[i2], localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferPrcSts);
            localVector.addElement(localTypeIntraTrnRsV1Aggregate);
          }
        }
      }
    }
    localTypeIntraSyncRsV1.IntraSyncRs.IntraTrnRs = ((TypeIntraTrnRsV1Aggregate[])localVector.toArray(new TypeIntraTrnRsV1Aggregate[0]));
    return localTypeIntraSyncRsV1;
  }
  
  public BPWHist getIntraHistory(BPWHist paramBPWHist)
    throws Exception
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    long l = 0L;
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
    l = paramBPWHist.getPageSize();
    FFSDebug.log("XferSyncProcessor.getIntraHistory: custId=" + str1 + ",histId=" + str5, 6);
    if ((str3 != null) && (str4 != null) && (Integer.parseInt(str3) > Integer.parseInt(str4)))
    {
      paramBPWHist.setStatusCode(17020);
      paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(17020, null, "API_MESSAGE"));
      return paramBPWHist;
    }
    if (str6 == null) {
      str6 = "0";
    }
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    int i = localPropertyConfig.getBatchSize();
    if (l > i)
    {
      paramBPWHist.setStatusCode(17030);
      paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(17030, null, "API_MESSAGE"));
      return paramBPWHist;
    }
    if (str5.trim().equals("")) {
      str5 = null;
    }
    if ((str5 == null) || (str5.equals("")) || (str5.equals("0")))
    {
      str5 = makeHistory(str1, str2, str3, str4, l, paramBPWHist);
      str6 = "0";
      jdMethod_if(str5, str6, l, paramBPWHist);
    }
    else
    {
      jdMethod_if(str5, str6, l, paramBPWHist);
    }
    if (paramBPWHist.getTrans().length > 0)
    {
      paramBPWHist.setStatusCode(0);
      paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "API_MESSAGE"));
    }
    else
    {
      paramBPWHist.setStatusCode(17020);
      paramBPWHist.setStatusMsg(BPWLocaleUtil.getMessage(17020, null, "API_MESSAGE"));
    }
    FFSDebug.log("XferSyncProcessor.getIntraHistory: done, custId=" + str1 + ",histId=" + str5, 6);
    return paramBPWHist;
  }
  
  public String makeHistory(String paramString1, String paramString2, String paramString3, String paramString4, long paramLong, BPWHist paramBPWHist)
    throws Exception
  {
    FFSDebug.log("XferSyncProcessor.makeHistory: start custId=" + paramString1 + ",acctId=" + paramString2 + ",startDate=" + paramString3 + ",endDate=" + paramString4, 6);
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
      j = k + 1000000;
    }
    else
    {
      j = Integer.parseInt(paramString4 + "00");
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    int m = 0;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int n = localPropertyConfig.getBatchSize();
      str1 = DBUtil.getNextIndexString("HistID");
      int i1 = 0;
      XferInstruction[] arrayOfXferInstruction;
      if ((paramBPWHist instanceof BPWExtdHist))
      {
        i1 = 1;
        paramBPWHist.setHistId(str1);
        arrayOfXferInstruction = XferInstruction.getHist(localFFSConnectionHolder, (BPWExtdHist)paramBPWHist, i, j);
      }
      else
      {
        arrayOfXferInstruction = XferInstruction.getHist(localFFSConnectionHolder, paramString1, paramString2, i, j, paramBPWHist.getRequiredStatus(), str1, paramLong);
      }
      int i2 = arrayOfXferInstruction.length;
      Object localObject1;
      while (i2 != 0)
      {
        String str3 = "INTRA";
        for (i4 = 0; i4 < arrayOfXferInstruction.length; i4++)
        {
          localObject1 = arrayOfXferInstruction[i4];
          String str4 = ((XferInstruction)localObject1).SrvrTID;
          int i6 = Integer.parseInt(((XferInstruction)localObject1).DateToPost);
          m++;
          if (m % n == 0) {
            localFFSConnectionHolder.conn.commit();
          }
          String str6 = DBUtil.getCursor(i6, m);
          TempHist.create(localFFSConnectionHolder, str1, str6, str4, str3, i6, null);
        }
        if (XferInstruction.isBatchDone(str1))
        {
          i2 = 0;
        }
        else
        {
          if (i1 != 0) {
            arrayOfXferInstruction = XferInstruction.getHist(localFFSConnectionHolder, (BPWExtdHist)paramBPWHist, i, j);
          } else {
            arrayOfXferInstruction = XferInstruction.getHist(localFFSConnectionHolder, paramString1, paramString2, i, j, paramBPWHist.getRequiredStatus(), str1, paramLong);
          }
          i2 = arrayOfXferInstruction.length;
        }
      }
      int i3 = DBUtil.getCurrentStartDate();
      int i4 = (j - i3) / 100;
      if (i4 >= 0)
      {
        if (i1 != 0) {
          localObject1 = RecXferInstruction.getHist(localFFSConnectionHolder, (BPWExtdHist)paramBPWHist, i, j);
        } else {
          localObject1 = RecXferInstruction.getHist(localFFSConnectionHolder, paramString1, paramString2, i, j, paramBPWHist.getRequiredStatus(), str1, paramLong);
        }
        int i5 = localObject1.length;
        while (i5 != 0)
        {
          String str5 = "RECINTRA";
          for (int i7 = 0; i7 < localObject1.length; i7++)
          {
            Object localObject2 = localObject1[i7];
            String str7 = localObject2.RecSrvrTID;
            ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(localObject2.FIID, "RECINTRATRN", localObject2.RecSrvrTID, localFFSConnectionHolder);
            if (localScheduleInfo != null)
            {
              localScheduleInfo.InstanceCount -= localScheduleInfo.CurInstanceNum - 1;
              localScheduleInfo.CurInstanceNum = 1;
              String[] arrayOfString = ScheduleInfo.getPendingDatesByStartAndEndDate(localScheduleInfo, i, j);
              for (int i8 = 0; i8 < arrayOfString.length; i8++)
              {
                int i9 = Integer.parseInt(arrayOfString[i8].substring(0, 10));
                m++;
                if (m % n == 0) {
                  localFFSConnectionHolder.conn.commit();
                }
                String str8 = DBUtil.getCursor(i9, m);
                TempHist.create(localFFSConnectionHolder, str1, str8, str7, str5, i9, null);
              }
            }
          }
          if (RecXferInstruction.isBatchDone(str1))
          {
            i5 = 0;
          }
          else
          {
            if (i1 != 0) {
              localObject1 = RecXferInstruction.getHist(localFFSConnectionHolder, (BPWExtdHist)paramBPWHist, i, j);
            } else {
              localObject1 = RecXferInstruction.getHist(localFFSConnectionHolder, paramString1, paramString2, i, j, paramBPWHist.getRequiredStatus(), str1, paramLong);
            }
            i5 = localObject1.length;
          }
        }
      }
      paramBPWHist.setHistId(str1);
      paramBPWHist.setTotalTrans(m);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      localException.printStackTrace();
      str2 = localException.toString();
      FFSDebug.log("*** XferSyncProcessor.makeHistory failed:" + str2);
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("*** XferSyncProcessor.makeHistory failed:" + str2);
    }
    finally
    {
      XferInstruction.clearBatch(str1);
      RecXferInstruction.clearBatch(str1);
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("XferSyncProcessor.makeHistory: done custId=" + paramString1 + ",acctId=" + paramString2 + ",startDate=" + paramString3 + ",endDate=" + paramString4, 6);
    return str1;
  }
  
  private void jdMethod_if(String paramString1, String paramString2, long paramLong, BPWHist paramBPWHist)
    throws Exception
  {
    FFSDebug.log("XferSyncProcessor.getHistory: start histId=" + paramString1 + ",cursorId=" + paramString2 + ",pageSize=" + paramLong, 6);
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
        IntraTrnInfo localIntraTrnInfo;
        if (localTempHist.RecordType.equals("INTRA"))
        {
          str2 = localTempHist.RecordExtId;
          localObject1 = XferInstruction.getXferInstruction(localFFSConnectionHolder, str2);
          if (localObject1 != null)
          {
            localIntraTrnInfo = new IntraTrnInfo(((XferInstruction)localObject1).CustomerID, ((XferInstruction)localObject1).BankID, ((XferInstruction)localObject1).BranchID, ((XferInstruction)localObject1).AcctDebitID, ((XferInstruction)localObject1).AcctDebitType, ((XferInstruction)localObject1).AcctCreditID, ((XferInstruction)localObject1).AcctCreditType, ((XferInstruction)localObject1).Amount, ((XferInstruction)localObject1).CurDef, ((XferInstruction)localObject1).DateToPost, str2, ((XferInstruction)localObject1).LogID, "", 1, false, null, ((XferInstruction)localObject1).RecSrvrTID, ((XferInstruction)localObject1).Status, ((XferInstruction)localObject1).FIID, ((XferInstruction)localObject1).fromBankID, ((XferInstruction)localObject1).fromBranchID);
            localIntraTrnInfo.submittedBy = ((XferInstruction)localObject1).SubmittedBy;
            if (((XferInstruction)localObject1).extraFields != null) {
              localIntraTrnInfo.extraFields = ((HashMap)((XferInstruction)localObject1).extraFields);
            }
            localArrayList.add(localIntraTrnInfo);
          }
        }
        else if (localTempHist.RecordType.equals("RECINTRA"))
        {
          str2 = localTempHist.RecordExtId;
          localObject1 = RecXferInstruction.getRecXferInstruction(localFFSConnectionHolder, str2);
          if (localObject1 != null)
          {
            localIntraTrnInfo = new IntraTrnInfo(((RecXferInstruction)localObject1).CustomerID, ((RecXferInstruction)localObject1).BankID, ((RecXferInstruction)localObject1).BranchID, ((RecXferInstruction)localObject1).AcctDebitID, ((RecXferInstruction)localObject1).AcctDebitType, ((RecXferInstruction)localObject1).AcctCreditID, ((RecXferInstruction)localObject1).AcctCreditType, ((RecXferInstruction)localObject1).Amount, ((RecXferInstruction)localObject1).CurDef, Integer.toString(localTempHist.DueDate), "", ((RecXferInstruction)localObject1).LogID, "", 1, false, null, str2, "WILLPROCESSON", ((RecXferInstruction)localObject1).FIID, ((RecXferInstruction)localObject1).fromBankID, ((RecXferInstruction)localObject1).fromBranchID);
            localIntraTrnInfo.submittedBy = ((RecXferInstruction)localObject1).SubmittedBy;
            if (((RecXferInstruction)localObject1).extraFields != null) {
              localIntraTrnInfo.extraFields = ((RecXferInstruction)localObject1).extraFields;
            }
            localArrayList.add(localIntraTrnInfo);
          }
        }
        else
        {
          throw new Exception("XferSyncProcessor.getHistory: Invalid RecordType" + localTempHist.RecordType);
        }
      }
      if (arrayOfTempHist.length > 0) {
        paramBPWHist.setCursorId(arrayOfTempHist[(arrayOfTempHist.length - 1)].CursorId);
      }
      paramBPWHist.trans = ((IntraTrnInfo[])localArrayList.toArray(new IntraTrnInfo[0]));
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
      FFSDebug.log("*** XferSyncProcessor.getHistory failed:" + str1);
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str1 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("*** XferSyncProcessor.getHistory failed:" + str1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("XferSyncProcessor.getHistory: done histId=" + paramString1 + ",cursorId=" + paramString2 + ",pageSize=" + paramLong, 6);
  }
  
  public static void createSessionData(FFSConnectionHolder paramFFSConnectionHolder, PagingInfo paramPagingInfo)
    throws FFSException
  {
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    StringBuffer localStringBuffer = new StringBuffer();
    ArrayList localArrayList = new ArrayList();
    a(paramPagingInfo, localStringBuffer, localArrayList);
    String str = localStringBuffer.toString();
    String[] arrayOfString = (String[])localHashMap2.get("TransType");
    if ((arrayOfString == null) || (arrayOfString.length == 0))
    {
      XferInstruction.createSessionDataSingleXfer(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      if (paramPagingInfo.getStatusCode() != 0) {
        return;
      }
      XferInstruction.createSessionDataPhyInstanceOfRecXfer(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      if (paramPagingInfo.getStatusCode() != 0) {
        return;
      }
      RecXferInstruction.createSessionDataRecXfer(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      if (paramPagingInfo.getStatusCode() == 0) {}
    }
    else
    {
      if (a(arrayOfString, "Current"))
      {
        XferInstruction.createSessionDataSingleXfer(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
        if (paramPagingInfo.getStatusCode() != 0) {
          return;
        }
      }
      if (a(arrayOfString, "Recurring"))
      {
        XferInstruction.createSessionDataPhyInstanceOfRecXfer(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
        if (paramPagingInfo.getStatusCode() != 0) {
          return;
        }
      }
      if (a(arrayOfString, "Recmodel")) {
        RecXferInstruction.createSessionDataRecTransferModel(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
      }
      if (a(arrayOfString, "Repetitive"))
      {
        RecXferInstruction.createSessionDataRecXfer(paramFFSConnectionHolder, paramPagingInfo, str, (ArrayList)localArrayList.clone());
        if (paramPagingInfo.getStatusCode() != 0) {}
      }
    }
  }
  
  private static void a(PagingInfo paramPagingInfo, StringBuffer paramStringBuffer, ArrayList paramArrayList)
    throws FFSException
  {
    String str1 = "XferSyncProcessor.createPagingSearchCriteria";
    FFSDebug.log(str1 + " : start ", 6);
    HashMap localHashMap1 = paramPagingInfo.getPagingContext().getMap();
    HashMap localHashMap2 = (HashMap)localHashMap1.get("SEARCH_CRITERIA");
    String str2 = null;
    String str3 = null;
    String[] arrayOfString = null;
    String str4 = null;
    String str5 = null;
    String str6 = null;
    String str7 = null;
    String str8 = null;
    if (paramStringBuffer == null) {
      paramStringBuffer = new StringBuffer();
    }
    if (paramArrayList == null) {
      paramArrayList = new ArrayList();
    }
    Integer localInteger1 = null;
    Integer localInteger2 = null;
    str7 = paramPagingInfo.getStartDate();
    str8 = paramPagingInfo.getEndDate();
    if (str7 == null) {
      localInteger1 = new Integer(0);
    } else {
      localInteger1 = new Integer(BPWUtil.getDateDBFormat(str7));
    }
    int i;
    if (str8 == null)
    {
      i = DBUtil.getCurrentStartDate();
      localInteger2 = new Integer(i + 1000000);
    }
    else
    {
      localInteger2 = new Integer(BPWUtil.getDateDBFormat(str8));
    }
    paramArrayList.add(0, localInteger1.toString());
    paramArrayList.add(0, localInteger2.toString());
    str2 = (String)localHashMap2.get("CustomerId");
    if ((str2 != null) && (str2.trim().length() != 0))
    {
      paramStringBuffer.append(" AND CustomerID = ? ");
      paramArrayList.add(str2);
    }
    str6 = (String)localHashMap2.get("AcctId");
    if ((str6 != null) && (str6.trim().length() != 0)) {
      if (str6.indexOf(",") != -1)
      {
        paramStringBuffer.append(" AND (");
        i = 1;
        localObject = new StringTokenizer(str6, ",");
        while (((StringTokenizer)localObject).hasMoreTokens())
        {
          if (i != 0) {
            i = 0;
          } else {
            paramStringBuffer.append(" or ");
          }
          paramStringBuffer.append(" AcctDebitID = ? ");
          paramArrayList.add(((StringTokenizer)localObject).nextToken());
        }
        paramStringBuffer.append(") ");
      }
      else
      {
        paramStringBuffer.append(" AND AcctDebitID = ? ");
        paramArrayList.add(str6);
      }
    }
    str3 = (String)localHashMap2.get("FIID");
    if ((str3 != null) && (str3.trim().length() != 0))
    {
      paramStringBuffer.append(" AND FIID = ? ");
      paramArrayList.add(str3);
    }
    arrayOfString = (String[])localHashMap2.get("SubmittedBys");
    if ((arrayOfString != null) && (arrayOfString.length > 0))
    {
      FFSDebug.log(str1 + " submittedBy.length:" + arrayOfString.length, 6);
      if (arrayOfString.length == 1)
      {
        paramStringBuffer.append(" AND SubmittedBy = ? ");
        paramArrayList.add(arrayOfString[0]);
      }
      else
      {
        paramStringBuffer.append(" AND SubmittedBy IN (?");
        paramArrayList.add(arrayOfString[0]);
        for (i = 1; i < arrayOfString.length; i++)
        {
          paramStringBuffer.append(", ?");
          paramArrayList.add(arrayOfString[i]);
        }
        paramStringBuffer.append(")");
      }
    }
    str4 = (String)localHashMap2.get("MinAmount");
    if ((str4 != null) && (str4.trim().length() != 0))
    {
      paramStringBuffer.append(" AND (cast (Amount as decimal) >= cast (? as decimal) or cast (ToAmount as decimal) >= cast (? as decimal) ) ");
      paramArrayList.add(new Integer(str4));
      paramArrayList.add(new Integer(str4));
    }
    str5 = (String)localHashMap2.get("MaxAmount");
    if ((str5 != null) && (str5.trim().length() != 0))
    {
      paramStringBuffer.append(" AND (cast (Amount as decimal) <= cast (? as decimal) or cast (ToAmount as decimal) <= cast ( ? as decimal) ) ");
      paramArrayList.add(new Integer(str5));
      paramArrayList.add(new Integer(str5));
    }
    ExtTransferAcctInfo localExtTransferAcctInfo = (ExtTransferAcctInfo)localHashMap2.get("FromAcct");
    if ((localExtTransferAcctInfo != null) && (localExtTransferAcctInfo.getAcctNum() != null))
    {
      paramStringBuffer.append(" AND AcctDebitID = ? ");
      paramArrayList.add(localExtTransferAcctInfo.getAcctNum());
    }
    Object localObject = (ExtTransferAcctInfo)localHashMap2.get("ToAcct");
    if ((localObject != null) && (((ExtTransferAcctInfo)localObject).getAcctNum() != null))
    {
      paramStringBuffer.append(" AND AcctCreditID = ? ");
      paramArrayList.add(((ExtTransferAcctInfo)localObject).getAcctNum());
    }
    String str9 = (String)localHashMap2.get("Amount");
    if ((str9 != null) && (str9.trim().length() != 0)) {
      try
      {
        float f = Float.parseFloat(str9);
        if (f != 0.0D)
        {
          NumberFormat localNumberFormat = NumberFormat.getNumberInstance();
          localNumberFormat.setGroupingUsed(false);
          localNumberFormat.setMinimumFractionDigits(2);
          String str12 = localNumberFormat.format(f);
          localNumberFormat.setMinimumFractionDigits(1);
          String str11 = localNumberFormat.format(f);
          if (str11.equals(str12))
          {
            paramArrayList.add(str11);
            paramArrayList.add(str11);
            paramStringBuffer.append(" AND (Amount = ? or ToAmount = ?) ");
          }
          else
          {
            paramArrayList.add(str11);
            paramArrayList.add(str12);
            paramArrayList.add(str11);
            paramArrayList.add(str12);
            paramStringBuffer.append(" AND (Amount = ? or Amount = ? or ToAmount = ? or ToAmount = ?) ");
          }
        }
      }
      catch (NumberFormatException localNumberFormatException) {}
    }
    String str10 = (String)localHashMap2.get("Currency");
    if ((str10 != null) && (str10.trim().length() != 0)) {
      DBUtil.appendStringToCondition(paramStringBuffer, paramArrayList, "a.AmountCurrency", str10);
    }
    FFSDebug.log(str1 + " : where clause as " + paramStringBuffer.toString(), 6);
    FFSDebug.log(str1 + " : end. ", 6);
  }
  
  private static boolean a(String[] paramArrayOfString, String paramString)
  {
    String str = "XferSyncProcessor.containsInArray";
    FFSDebug.log(str + ": Start", 6);
    FFSDebug.log(str + ": aStringList:" + paramArrayOfString, 6);
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
      return false;
    }
    FFSDebug.log(str + ": reqStatus:", paramString, 6);
    if (paramString == null) {
      return false;
    }
    for (int i = 0; i < paramArrayOfString.length; i++) {
      if (paramString.equalsIgnoreCase(paramArrayOfString[i]))
      {
        FFSDebug.log(str + ": reqString FOUND", 6);
        return true;
      }
    }
    FFSDebug.log(str + ": reqString not FOUND", 6);
    FFSDebug.log(str + ": End", 6);
    return false;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.XferSyncProcessor
 * JD-Core Version:    0.7.0.1
 */