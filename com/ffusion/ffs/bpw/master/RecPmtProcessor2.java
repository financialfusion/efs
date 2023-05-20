package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.BPWExtraInfo;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.RecPmtInstruction;
import com.ffusion.ffs.bpw.db.RecSrvrTIDToSrvrTID;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.db.Trans;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.BankingDays;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.ScheduleConstants;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumIDScopeEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtCancRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtCancRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtCancRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecPmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;

public class RecPmtProcessor2
  implements DBConsts, BPWResource, OFXConsts, FFSConst, ScheduleConstants
{
  private int ae = 4;
  private boolean af = false;
  private PmtProcessor2 ad;
  private String ac = "";
  
  public RecPmtProcessor2() {}
  
  public RecPmtProcessor2(PmtProcessor2 paramPmtProcessor2)
  {
    this.ad = paramPmtProcessor2;
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.ae = localPropertyConfig.LogLevel;
    this.af = localPropertyConfig.UseExtdPayeeID;
  }
  
  public TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    return processRecPmtTrnRqV1(paramTypeRecPmtTrnRqV1, paramTypeUserData, null);
  }
  
  public TypeRecPmtTrnRsV1 processRecPmtTrnRqV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = null;
    try
    {
      String str1 = null;
      if (paramTypeUserData != null) {
        str1 = paramTypeUserData._tran_id;
      }
      str2 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.__memberName;
      if (str2.equals("RecPmtRq")) {
        localTypeRecPmtTrnRsV1 = jdMethod_do(paramTypeRecPmtTrnRqV1, paramTypeUserData, str1);
      } else if (str2.equals("RecPmtModRq")) {
        localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1, paramTypeUserData, str1);
      } else if (str2.equals("RecPmtCancRq")) {
        localTypeRecPmtTrnRsV1 = a(paramTypeRecPmtTrnRqV1, paramTypeUserData, str1);
      } else {
        throw new OFXException("Unsupported request type", 2000);
      }
    }
    catch (OFXException localOFXException)
    {
      localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException)
    {
      String str2 = "*** RecPmtProcessor.processRecPmtTrnRqV1 failed:";
      FFSDebug.log(localException, str2);
      FFSDebug.console(str2 + localException.toString());
      localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    return localTypeRecPmtTrnRsV1;
  }
  
  public RecPmtInfo[] getRecPmtById(String[] paramArrayOfString)
    throws Exception
  {
    FFSDebug.log("RecPmtProcessor2.getRecPmtById (multiple): start ", 6);
    RecPmtInfo localRecPmtInfo = null;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localRecPmtInfo = new RecPmtInfo();
      localRecPmtInfo.statusCode = 16504;
      localRecPmtInfo.statusMsg = "Rec ID Array  is null or empty";
      FFSDebug.log("RecPmtProcessor2.getRecPmtById (multiple): failed  null or empty id array passed", 0);
      localObject1 = new RecPmtInfo[] { localRecPmtInfo };
      return localObject1;
    }
    Object localObject1 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      if (((FFSConnectionHolder)localObject1).conn == null) {
        throw new BPWException("Null connection returned");
      }
    }
    catch (Exception localException1)
    {
      String str1 = "RecPmtProcessor2.getRecPmtById (multiple) : failed to get DB connection ";
      FFSDebug.log(str1 + FFSDebug.stackTrace(localException1), 0);
      throw new BPWException(str1 + localException1.toString());
    }
    RecPmtInfo[] arrayOfRecPmtInfo = null;
    try
    {
      arrayOfRecPmtInfo = RecPmtInstruction.getRecPmtById(paramArrayOfString, (FFSConnectionHolder)localObject1);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException2)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      String str2 = "RecPmtProcessor2.getRecPmtById (multiple): failed ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localException2), 0);
      throw new BPWException(str2 + localException2.toString());
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    return arrayOfRecPmtInfo;
  }
  
  private TypeRecPmtTrnRsV1 jdMethod_do(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    String str1 = PmtProcessor.getUID(paramTypeUserData);
    String str2 = DBUtil.getFIId(paramTypeUserData);
    FFSDebug.log("RecPmtProcessor.processRecPmtAddRqV1 start, uid=" + str1, 6);
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = null;
    FFSConnection[] arrayOfFFSConnection = DBUtil.getConnections(2);
    FFSConnectionHolder localFFSConnectionHolder1 = new FFSConnectionHolder();
    localFFSConnectionHolder1.conn = arrayOfFFSConnection[0];
    FFSConnectionHolder localFFSConnectionHolder2 = new FFSConnectionHolder();
    localFFSConnectionHolder2.conn = arrayOfFFSConnection[1];
    try
    {
      TypePmtInfoAggregate localTypePmtInfoAggregate = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo;
      localObject1 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.CurDef;
      str3 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.InitialAmtExists ? String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.InitialAmt) : String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.TrnAmt);
      String str4 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.FinalAmtExists ? String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.FinalAmt) : String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.TrnAmt);
      if ((!FFSUtil.isPositive(str3)) || (!FFSUtil.isPositive(str4)))
      {
        localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2012, " ");
        localObject2 = localTypeRecPmtTrnRsV1;
        return localObject2;
      }
      Object localObject2 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.RecurrInst;
      RecPmtInstruction localRecPmtInstruction = a(localTypePmtInfoAggregate, str1, str2, str3, str4, (TypeRecurrInstAggregate)localObject2, paramString, paramTypeUserData, (EnumCurrencyEnum)localObject1);
      this.ac = localRecPmtInstruction.getStatus();
      localRecPmtInstruction.setRecSrvrTID();
      String str5 = localRecPmtInstruction.getRecSrvrTID();
      ScheduleInfo localScheduleInfo = createScheduleInfo(localRecPmtInstruction, str2, (TypeRecurrInstAggregate)localObject2);
      if (localFFSConnectionHolder1.conn == null) {
        throw new Exception("Can not get DB Connection.");
      }
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm.TrnUID))
      {
        if (this.ae >= 3)
        {
          int i = Integer.parseInt(localRecPmtInstruction.getCustomerID());
          BigDecimal localBigDecimal1 = FFSUtil.getBigDecimal(localRecPmtInstruction.getRecPmtInfo().getAmt());
          localObject4 = BPWUtil.getAccountIDWithAccountType(localRecPmtInstruction.getAcctDebitID(), localRecPmtInstruction.getAcctDebitType());
          localObject5 = BPWLocaleUtil.getMessage(812, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject6 = new String[] { localObject5 };
          LocalizableString localLocalizableString1 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject6, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString1, paramString, 4409, i, localBigDecimal1, localRecPmtInstruction.getRecPmtInfo().CurDef, str5, "DUPLICATE", localRecPmtInstruction.getPayAcct(), null, (String)localObject4, null, 0);
        }
        localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2019, " ");
        localFFSConnectionHolder1.conn.commit();
        localObject3 = localTypeRecPmtTrnRsV1;
        return localObject3;
      }
      Object localObject3 = this.ad.processImplicitPayeeRq(localTypePmtInfoAggregate, str1, localFFSConnectionHolder1, localFFSConnectionHolder2, paramString);
      if (localObject3 == null) {
        throw new BPWException("Could not create payeeID!");
      }
      int j = Integer.parseInt(localTypePmtInfoAggregate.PayeeLstID);
      Object localObject4 = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      if (localObject4 == null) {
        throw new BPWException("AuditAgent is null!!");
      }
      ScheduleInfo.createSchedule(localFFSConnectionHolder1, "RECPMTTRN", str5, localScheduleInfo);
      localRecPmtInstruction.setPayeeID(((PayeeInfo)localObject3).PayeeID);
      localRecPmtInstruction.setPayeeListID(j);
      localRecPmtInstruction.storeToDB(localFFSConnectionHolder1);
      a(localFFSConnectionHolder1, paramTypeUserData, str5, "add");
      localTypeRecPmtTrnRsV1 = buildRecPmtAddRsV1(paramTypeRecPmtTrnRqV1, str5, Integer.toString(j), (PayeeInfo)localObject3, localRecPmtInstruction.getRecPmtInfo().CurDef);
      ((AuditAgent)localObject4).saveRecPmtRsV1(localTypeRecPmtTrnRsV1, str1, localFFSConnectionHolder1);
      Object localObject5 = jdMethod_if(paramTypeRecPmtTrnRqV1, str5, j, ((PayeeInfo)localObject3).PayeeID, localRecPmtInstruction.getRecPmtInfo().CurDef);
      Object localObject6 = this.ad.processPmtAddRqV1(localFFSConnectionHolder1, localFFSConnectionHolder2, (TypePmtTrnRqV1)localObject5, paramTypeUserData, paramString, str5);
      RecSrvrTIDToSrvrTID.create(localFFSConnectionHolder1, str5, ((TypePmtTrnRsV1)localObject6).PmtTrnRs.PmtTrnRsV1Un.PmtRs.SrvrTID);
      if (this.ae >= 3)
      {
        int k = Integer.parseInt(localRecPmtInstruction.getCustomerID());
        BigDecimal localBigDecimal2 = FFSUtil.getBigDecimal(localRecPmtInstruction.getRecPmtInfo().getAmt());
        String str6 = BPWUtil.getAccountIDWithAccountType(localRecPmtInstruction.getAcctDebitID(), localRecPmtInstruction.getAcctDebitType());
        String str7 = BPWLocaleUtil.getMessage(811, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString = { str7 };
        LocalizableString localLocalizableString2 = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString2, paramString, 4408, k, localBigDecimal2, localRecPmtInstruction.getRecPmtInfo().CurDef, str5, localRecPmtInstruction.getStatus(), localRecPmtInstruction.getPayAcct(), null, str6, null, 0);
      }
      localFFSConnectionHolder1.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder1.conn.rollback();
      localObject1 = "*** RecPmtProcessor.processRecPmtAddRqV1 failed:";
      str3 = localOFXException.toString();
      FFSDebug.log(localOFXException, (String)localObject1);
      FFSDebug.console((String)localObject1 + str3);
      localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder1.conn.rollback();
      localObject1 = "*** RecPmtProcessor.processRecPmtAddRqV1 failed:";
      str3 = localBPWException.toString();
      FFSDebug.log(localBPWException, (String)localObject1);
      FFSDebug.console((String)localObject1 + str3);
      if (localBPWException.getErrorCode() == -1) {
        localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, "Server internal error");
      } else {
        localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder1.conn.rollback();
      Object localObject1 = "*** RecPmtProcessor.processRecPmtAddRqV1 failed:";
      String str3 = localException.toString();
      FFSDebug.log(localException, (String)localObject1);
      FFSDebug.console((String)localObject1 + str3);
      localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    finally
    {
      localFFSConnectionHolder2.conn.rollback();
      DBUtil.freeConnection(localFFSConnectionHolder1.conn);
      DBUtil.freeConnection(localFFSConnectionHolder2.conn);
    }
    FFSDebug.log("RecPmtProcessor.processRecPmtAddRqV1 end, uid=" + str1, 6);
    return localTypeRecPmtTrnRsV1;
  }
  
  private TypeRecPmtTrnRsV1 jdMethod_if(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    String str1 = PmtProcessor.getUID(paramTypeUserData);
    String str2 = DBUtil.getFIId(paramTypeUserData);
    FFSDebug.log("RecPmtProcessor.processRecPmtModRqV1 start, uid=" + str1, 6);
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV1 = null;
    FFSConnection[] arrayOfFFSConnection = DBUtil.getConnections(2);
    FFSConnectionHolder localFFSConnectionHolder1 = new FFSConnectionHolder();
    localFFSConnectionHolder1.conn = arrayOfFFSConnection[0];
    FFSConnectionHolder localFFSConnectionHolder2 = new FFSConnectionHolder();
    localFFSConnectionHolder2.conn = arrayOfFFSConnection[1];
    try
    {
      TypePmtInfoAggregate localTypePmtInfoAggregate = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo;
      localObject1 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.CurDef;
      str3 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.InitialAmtExists ? String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.InitialAmt) : String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.TrnAmt);
      String str4 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.FinalAmtExists ? String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.FinalAmt) : String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.TrnAmt);
      if ((!FFSUtil.isPositive(str3)) || (!FFSUtil.isPositive(str4)))
      {
        localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2012, " ");
        localObject2 = localTypeRecPmtTrnRsV1;
        return localObject2;
      }
      Object localObject2 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.RecurrInst;
      RecPmtInstruction localRecPmtInstruction = a(localTypePmtInfoAggregate, str1, str2, str3, str4, (TypeRecurrInstAggregate)localObject2, paramString, paramTypeUserData, (EnumCurrencyEnum)localObject1);
      this.ac = localRecPmtInstruction.getStatus();
      String str5 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.RecSrvrTID;
      localRecPmtInstruction.setRecSrvrTID(str5);
      ScheduleInfo localScheduleInfo = createScheduleInfo(localRecPmtInstruction, str2, (TypeRecurrInstAggregate)localObject2);
      if (localFFSConnectionHolder1.conn == null) {
        throw new Exception("Can not get DB Connection.");
      }
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm.TrnUID))
      {
        if (this.ae >= 3)
        {
          int i = Integer.parseInt(localRecPmtInstruction.getCustomerID());
          BigDecimal localBigDecimal = FFSUtil.getBigDecimal(localRecPmtInstruction.getRecPmtInfo().getAmt());
          localObject4 = BPWUtil.getAccountIDWithAccountType(localRecPmtInstruction.getAcctDebitID(), localRecPmtInstruction.getAcctDebitType());
          String str6 = BPWLocaleUtil.getMessage(814, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject5 = new String[] { str6 };
          LocalizableString localLocalizableString1 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject5, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString1, paramString, 4411, i, localBigDecimal, localRecPmtInstruction.getRecPmtInfo().CurDef, str5, "DUPLICATE", localRecPmtInstruction.getPayAcct(), null, (String)localObject4, null, 0);
        }
        localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2019, " ");
        localFFSConnectionHolder1.conn.commit();
        localObject3 = localTypeRecPmtTrnRsV1;
        return localObject3;
      }
      Object localObject3 = this.ad.processImplicitPayeeRq(localTypePmtInfoAggregate, str1, localFFSConnectionHolder1, localFFSConnectionHolder2, paramString);
      if (!paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.PayeeLstIDExists) {
        throw new OFXException(" ", 10519);
      }
      if (localObject3 == null) {
        throw new BPWException("Could not create payeeID.");
      }
      int j = Integer.parseInt(localTypePmtInfoAggregate.PayeeLstID);
      Object localObject4 = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      if (localObject4 == null) {
        throw new BPWException("AuditAgent is null!!");
      }
      int k = ScheduleInfo.checkScheduleStatus(str5, str2, "RECPMTTRN", localFFSConnectionHolder1);
      if (k == 0)
      {
        ScheduleInfo.modifySchedule(localFFSConnectionHolder1, "RECPMTTRN", str5, localScheduleInfo);
      }
      else
      {
        localFFSConnectionHolder1.conn.rollback();
        localObject5 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, k, " ");
        return localObject5;
      }
      localRecPmtInstruction.setPayeeID(((PayeeInfo)localObject3).PayeeID);
      localRecPmtInstruction.setPayeeListID(j);
      localRecPmtInstruction.updateRecPmt(localFFSConnectionHolder1);
      a(localFFSConnectionHolder1, paramTypeUserData, str5, "mod");
      localTypeRecPmtTrnRsV1 = buildRecPmtModRsV1(paramTypeRecPmtTrnRqV1, Integer.toString(j), (PayeeInfo)localObject3, localRecPmtInstruction.getRecPmtInfo().CurDef);
      Object localObject5 = localRecPmtInstruction.getRecPmtInfo();
      ((AuditAgent)localObject4).modRecPmtRsV1(localTypeRecPmtTrnRsV1, (RecPmtInfo)localObject5, localFFSConnectionHolder1);
      boolean bool = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.ModPending;
      Object localObject7;
      Object localObject6;
      Object localObject8;
      Object localObject9;
      if (bool)
      {
        String[] arrayOfString1 = RecSrvrTIDToSrvrTID.getSrvrTIDs(localFFSConnectionHolder1, str5);
        if (arrayOfString1 != null) {
          for (int n = 0; n < arrayOfString1.length; n++)
          {
            localObject7 = PmtInstruction.getStatus(arrayOfString1[n], localFFSConnectionHolder1);
            if ((localObject7 != null) && (("WILLPROCESSON".equalsIgnoreCase((String)localObject7) == true) || ("APPROVAL_PENDING".equalsIgnoreCase((String)localObject7) == true) || ("APPROVAL_REJECTED".equals(localObject7) == true) || ("APPROVAL_NOT_ALLOWED".equals(localObject7) == true)))
            {
              FFSDebug.log("RecPmtProcessor2.processRecPmtModRqV1", ": stat: ", (String)localObject7, 6);
              TypePmtTrnRqV1 localTypePmtTrnRqV1 = a(paramTypeRecPmtTrnRqV1, arrayOfString1[n]);
              this.ad.processPmtCancRqV1(localFFSConnectionHolder1, localTypePmtTrnRqV1, paramTypeUserData, paramString);
              RecSrvrTIDToSrvrTID.delete(localFFSConnectionHolder1, str5, arrayOfString1[n]);
            }
          }
        }
        localObject6 = a(paramTypeRecPmtTrnRqV1, str5, j, ((PayeeInfo)localObject3).PayeeID, localRecPmtInstruction.getRecPmtInfo().CurDef);
        localObject7 = this.ad.processPmtAddRqV1(localFFSConnectionHolder1, localFFSConnectionHolder2, (TypePmtTrnRqV1)localObject6, paramTypeUserData, paramString, str5);
        RecSrvrTIDToSrvrTID.create(localFFSConnectionHolder1, str5, ((TypePmtTrnRsV1)localObject7).PmtTrnRs.PmtTrnRsV1Un.PmtRs.SrvrTID);
        if (this.ae >= 3)
        {
          int i1 = Integer.parseInt(localRecPmtInstruction.getCustomerID());
          localObject8 = FFSUtil.getBigDecimal(localRecPmtInstruction.getRecPmtInfo().getAmt());
          localObject9 = BPWUtil.getAccountIDWithAccountType(localRecPmtInstruction.getAcctDebitID(), localRecPmtInstruction.getAcctDebitType());
          String str8 = BPWLocaleUtil.getMessage(811, null, "BILLPAY_AUDITLOG_MESSAGE");
          String[] arrayOfString2 = { str8 };
          LocalizableString localLocalizableString2 = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString2, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString2, paramString, 4408, i1, (BigDecimal)localObject8, localRecPmtInstruction.getRecPmtInfo().CurDef, str5, localRecPmtInstruction.getStatus(), localRecPmtInstruction.getPayAcct(), null, (String)localObject9, null, 0);
        }
      }
      if (this.ae >= 3)
      {
        int m = Integer.parseInt(localRecPmtInstruction.getCustomerID());
        localObject6 = FFSUtil.getBigDecimal(localRecPmtInstruction.getRecPmtInfo().getAmt());
        localObject7 = BPWUtil.getAccountIDWithAccountType(localRecPmtInstruction.getAcctDebitID(), localRecPmtInstruction.getAcctDebitType());
        String str7 = BPWLocaleUtil.getMessage(813, null, "BILLPAY_AUDITLOG_MESSAGE");
        localObject8 = new String[] { str7 };
        localObject9 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject8, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, (ILocalizable)localObject9, paramString, 4410, m, (BigDecimal)localObject6, localRecPmtInstruction.getRecPmtInfo().CurDef, str5, "MODIFIED", localRecPmtInstruction.getPayAcct(), null, (String)localObject7, null, 0);
      }
      localFFSConnectionHolder1.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder1.conn.rollback();
      localObject1 = "*** RecPmtProcessor.processRecPmtModRqV1 failed:";
      str3 = localOFXException.toString();
      FFSDebug.log(localOFXException, (String)localObject1);
      FFSDebug.console((String)localObject1 + str3);
      localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder1.conn.rollback();
      localObject1 = "*** RecPmtProcessor.processRecPmtModRqV1 failed:";
      str3 = localBPWException.toString();
      FFSDebug.log(localBPWException, (String)localObject1);
      FFSDebug.console((String)localObject1 + str3);
      if (localBPWException.getErrorCode() == -1) {
        localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, "Server internal error");
      } else {
        localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder1.conn.rollback();
      Object localObject1 = "*** RecPmtProcessor.processRecPmtModRqV1 failed:";
      String str3 = localException.toString();
      FFSDebug.log(localException, (String)localObject1);
      FFSDebug.console((String)localObject1 + str3);
      localTypeRecPmtTrnRsV1 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    finally
    {
      localFFSConnectionHolder2.conn.rollback();
      DBUtil.freeConnection(localFFSConnectionHolder1.conn);
      DBUtil.freeConnection(localFFSConnectionHolder2.conn);
    }
    FFSDebug.log("RecPmtProcessor.processRecPmtModRqV1 end, uid=" + str1, 6);
    return localTypeRecPmtTrnRsV1;
  }
  
  private TypeRecPmtTrnRsV1 a(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    String str1 = PmtProcessor.getUID(paramTypeUserData);
    String str2 = DBUtil.getFIId(paramTypeUserData);
    FFSDebug.log("RecPmtProcessor2.processRecPmtCancRqV1", ": start, uid: " + str1, 6);
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV11 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      String str3 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtCancRq.RecSrvrTID;
      if (this.ae >= 3)
      {
        localObject1 = new RecPmtInstruction(str3, localFFSConnectionHolder);
      }
      else
      {
        localObject1 = new RecPmtInstruction();
        ((RecPmtInstruction)localObject1).setRecSrvrTID(str3);
      }
      ((RecPmtInstruction)localObject1).setCustomerID(str1);
      int i = ScheduleInfo.checkScheduleStatus(str3, str2, "RECPMTTRN", localFFSConnectionHolder);
      if (i != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV12 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, i, " ");
        return localTypeRecPmtTrnRsV12;
      }
      if (localFFSConnectionHolder.conn == null) {
        throw new BPWException("RecPmtProcessor.processRecPmtCancRqV1:Can not get DB Connection.");
      }
      Object localObject3;
      Object localObject4;
      Object localObject5;
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm.TrnUID))
      {
        if (this.ae >= 3)
        {
          int j = Integer.parseInt(((RecPmtInstruction)localObject1).getCustomerID());
          BigDecimal localBigDecimal1 = FFSUtil.getBigDecimal(((RecPmtInstruction)localObject1).getRecPmtInfo().getAmt());
          localObject3 = BPWUtil.getAccountIDWithAccountType(((RecPmtInstruction)localObject1).getAcctDebitID(), ((RecPmtInstruction)localObject1).getAcctDebitType());
          String str5 = BPWLocaleUtil.getMessage(816, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject4 = new String[] { str5 };
          localObject5 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject4, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, (ILocalizable)localObject5, paramString, 4413, j, localBigDecimal1, ((RecPmtInstruction)localObject1).getRecPmtInfo().CurDef, str3, "DUPLICATE", ((RecPmtInstruction)localObject1).getPayAcct(), null, (String)localObject3, null, 0);
        }
        localTypeRecPmtTrnRsV11 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2019, " ");
        localFFSConnectionHolder.conn.commit();
        localObject2 = localTypeRecPmtTrnRsV11;
        return localObject2;
      }
      if (ScheduleInfo.cancelSchedule(localFFSConnectionHolder, "RECPMTTRN", str3) == 0) {
        throw new BPWException("Cannot cancel pmt in process!!");
      }
      Object localObject2 = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      if (localObject2 == null) {
        throw new BPWException("AuditAgent is null!!");
      }
      localTypeRecPmtTrnRsV11 = buildRecPmtCancRsV1(paramTypeRecPmtTrnRqV1);
      RecPmtInstruction.updateStatus(localFFSConnectionHolder, str3, "CANCELEDON");
      this.ac = "CANCELEDON";
      ((AuditAgent)localObject2).cancelOFX200RecPmtRsV1(str3, localFFSConnectionHolder);
      boolean bool = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtCancRq.CanPending;
      if (bool)
      {
        localObject3 = RecSrvrTIDToSrvrTID.getSrvrTIDs(localFFSConnectionHolder, str3);
        if (localObject3 != null) {
          for (int m = 0; m < localObject3.length; m++)
          {
            localObject4 = PmtInstruction.getStatus(localObject3[m], localFFSConnectionHolder);
            if (localObject4 != null)
            {
              localObject5 = a(paramTypeRecPmtTrnRqV1, localObject3[m]);
              this.ad.processPmtCancRqV1(localFFSConnectionHolder, (TypePmtTrnRqV1)localObject5, paramTypeUserData, paramString);
            }
          }
        }
      }
      if (this.ae >= 3)
      {
        int k = Integer.parseInt(((RecPmtInstruction)localObject1).getCustomerID());
        BigDecimal localBigDecimal2 = FFSUtil.getBigDecimal(((RecPmtInstruction)localObject1).getRecPmtInfo().getAmt());
        localObject4 = BPWUtil.getAccountIDWithAccountType(((RecPmtInstruction)localObject1).getAcctDebitID(), ((RecPmtInstruction)localObject1).getAcctDebitType());
        localObject5 = BPWLocaleUtil.getMessage(815, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString = { localObject5 };
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString, paramString, 4412, k, localBigDecimal2, ((RecPmtInstruction)localObject1).getRecPmtInfo().CurDef, str3, this.ac, ((RecPmtInstruction)localObject1).getPayAcct(), null, (String)localObject4, null, 0);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      localTypeRecPmtTrnRsV11 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject1 = "*** RecPmtProcessor.processRecPmtCancRqV1 failed:";
      String str4 = localException.toString();
      FFSDebug.log(localException, (String)localObject1);
      FFSDebug.console((String)localObject1 + str4);
      localTypeRecPmtTrnRsV11 = jdMethod_int(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("RecPmtProcessor.processRecPmtCancRqV1 end, uid=" + str1, 6);
    return localTypeRecPmtTrnRsV11;
  }
  
  private RecPmtInstruction a(TypePmtInfoAggregate paramTypePmtInfoAggregate, String paramString1, String paramString2, String paramString3, String paramString4, TypeRecurrInstAggregate paramTypeRecurrInstAggregate, String paramString5, TypeUserData paramTypeUserData, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws Exception, BPWException, OFXException
  {
    RecPmtInstruction localRecPmtInstruction = new RecPmtInstruction();
    localRecPmtInstruction.setCustomerID(paramString1);
    localRecPmtInstruction.setFIID(paramString2);
    localRecPmtInstruction.setStatus("WILLPROCESSON");
    localRecPmtInstruction.setLogID(paramString5);
    localRecPmtInstruction.setSubmittedBy(paramTypeUserData._submittedBy);
    localRecPmtInstruction.mapFromPmtInfoV1(paramTypePmtInfoAggregate, paramEnumCurrencyEnum);
    localRecPmtInstruction.setInitialAmount(paramString3);
    localRecPmtInstruction.setFinalAmount(paramString4);
    int i = CommonProcessor.mapOFX200FreqToBPWFreq(paramTypeRecurrInstAggregate.Freq.value());
    localRecPmtInstruction.setFrequency(i);
    int j;
    String str3;
    String str2;
    if (paramTypePmtInfoAggregate.DtDue != null)
    {
      String str1 = paramTypePmtInfoAggregate.DtDue.substring(0, 8);
      if (BPWUtil.validateDate(str1, "yyyyMMdd") == true)
      {
        int k = DBUtil.getCurrentStartDate();
        j = BPWUtil.getDateDBFormat(str1);
        if (j < k)
        {
          str3 = BPWLocaleUtil.getMessage(100002, null, "OFX_MESSAGE");
          throw new BPWException(str3, 100002);
        }
        localRecPmtInstruction.setStartDate(j);
      }
      else
      {
        str2 = BPWLocaleUtil.getMessage(100000, null, "OFX_MESSAGE");
        throw new BPWException(str2, 100000);
      }
    }
    else
    {
      str2 = BPWLocaleUtil.getMessage(100000, null, "OFX_MESSAGE");
      throw new BPWException(str2, 100000);
    }
    int m;
    if (paramTypeRecurrInstAggregate.NInstsExists)
    {
      m = paramTypeRecurrInstAggregate.NInsts;
      if ((m == 1) || (m < 0))
      {
        str3 = BPWLocaleUtil.getMessage(100001, null, "OFX_MESSAGE");
        throw new BPWException(str3, 100001);
      }
    }
    else
    {
      m = 0;
    }
    localRecPmtInstruction.setInstanceCount(m);
    int n = CommonProcessor.getEndDate(j, i, m);
    localRecPmtInstruction.setEndDate(n);
    return localRecPmtInstruction;
  }
  
  public ScheduleInfo createScheduleInfo(RecPmtInstruction paramRecPmtInstruction, String paramString, TypeRecurrInstAggregate paramTypeRecurrInstAggregate)
    throws Exception
  {
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramString;
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = paramRecPmtInstruction.getFrequency();
    localScheduleInfo.StartDate = paramRecPmtInstruction.getStartDate();
    localScheduleInfo.InstanceCount = paramRecPmtInstruction.getInstanceCount();
    localScheduleInfo.NextInstanceDate = paramRecPmtInstruction.getStartDate();
    localScheduleInfo.LogID = paramRecPmtInstruction.getLogID();
    localScheduleInfo.CurInstanceNum = 1;
    if (!paramTypeRecurrInstAggregate.NInstsExists) {
      localScheduleInfo.Perpetual = 1;
    }
    return localScheduleInfo;
  }
  
  public TypeRecPmtTrnRsV1 buildRecPmtAddRsV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, String paramString1, String paramString2, PayeeInfo paramPayeeInfo, String paramString3)
    throws Exception
  {
    TypeRecPmtTrnRsV1Aggregate localTypeRecPmtTrnRsV1Aggregate = new TypeRecPmtTrnRsV1Aggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUnExists = true;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn = new TypeRecPmtTrnRsUn();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.__memberName = "RecPmtRs";
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs = new TypeRecPmtRsAggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.RecSrvrTID = paramString1;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.PayeeLstID = paramString2;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.CurDef = MsgBuilder.getOFX200CurrencyEnum(paramString3);
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.ExtdPayeeExists = false;
    if ((!this.af) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.ExtdPayeeExists = true;
      localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.ExtdPayee = new TypeExtdPayeeAggregate();
      localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.ExtdPayeeCmExists = true;
      localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.ExtdPayeeCm = new TypeExtdPayeeCm();
      localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.ExtdPayeeCm.PayeeID = (!this.af ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.ExtdPayeeCm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.ExtdPayeeCm.IDScope = EnumIDScopeEnum.USER;
      } else {
        localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.ExtdPayee.ExtdPayeeCm.IDScope = EnumIDScopeEnum.GLOBAL;
      }
    }
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.PmtInfo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.PmtInfo.PayeeLstIDExists = true;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.PmtInfo.PayeeLstID = paramString2;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.RecurrInst = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.RecurrInst;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.InitialAmtExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.InitialAmtExists;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.InitialAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.InitialAmt;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.FinalAmtExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.FinalAmtExists;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtRs.FinalAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.FinalAmt;
    localTypeRecPmtTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, null);
    return new TypeRecPmtTrnRsV1(localTypeRecPmtTrnRsV1Aggregate);
  }
  
  public TypeRecPmtTrnRsV1 buildRecPmtModRsV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, String paramString1, PayeeInfo paramPayeeInfo, String paramString2)
    throws Exception
  {
    TypeRecPmtTrnRsV1Aggregate localTypeRecPmtTrnRsV1Aggregate = new TypeRecPmtTrnRsV1Aggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUnExists = true;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn = new TypeRecPmtTrnRsUn();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.__memberName = "RecPmtModRs";
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs = new TypeRecPmtModRsAggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.RecSrvrTID = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.RecSrvrTID;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.CurDefExists = true;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.CurDef = MsgBuilder.getOFX200CurrencyEnum(paramString2);
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.PmtInfo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.PmtInfo.PayeeLstIDExists = true;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.PmtInfo.PayeeLstID = paramString1;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.RecurrInst = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.RecurrInst;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.InitialAmtExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.InitialAmtExists;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.InitialAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.InitialAmt;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.FinalAmtExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.FinalAmtExists;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.FinalAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.FinalAmt;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtModRs.ModPending = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.ModPending;
    localTypeRecPmtTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, null);
    return new TypeRecPmtTrnRsV1(localTypeRecPmtTrnRsV1Aggregate);
  }
  
  public TypeRecPmtTrnRsV1 buildRecPmtCancRsV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1)
    throws Exception
  {
    TypeRecPmtTrnRsV1Aggregate localTypeRecPmtTrnRsV1Aggregate = new TypeRecPmtTrnRsV1Aggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUnExists = true;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn = new TypeRecPmtTrnRsUn();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.__memberName = "RecPmtCancRs";
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtCancRs = new TypeRecPmtCancRsAggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtCancRs.RecSrvrTID = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtCancRq.RecSrvrTID;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUn.RecPmtCancRs.CanPending = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtCancRq.CanPending;
    localTypeRecPmtTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, null);
    return new TypeRecPmtTrnRsV1(localTypeRecPmtTrnRsV1Aggregate);
  }
  
  private TypeRecPmtTrnRsV1 jdMethod_int(TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
  {
    TypeRecPmtTrnRsV1Aggregate localTypeRecPmtTrnRsV1Aggregate = new TypeRecPmtTrnRsV1Aggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsUnExists = false;
    localTypeRecPmtTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt, paramString);
    return new TypeRecPmtTrnRsV1(localTypeRecPmtTrnRsV1Aggregate);
  }
  
  private TypePmtTrnRqV1 jdMethod_if(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, String paramString1, int paramInt, String paramString2, String paramString3)
    throws Exception
  {
    TypePmtTrnRqV1 localTypePmtTrnRqV1 = new TypePmtTrnRqV1();
    localTypePmtTrnRqV1.PmtTrnRq = new TypePmtTrnRqV1Aggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn = new TypePmtTrnRqUn();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.__memberName = "PmtRq";
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq = new TypePmtRqAggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.CurDefExists = true;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.CurDef = MsgBuilder.getOFX200CurrencyEnum(paramString3);
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo = new TypePmtInfoAggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.BankAcctFrom = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.BankAcctFrom;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.BankAcctTo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.BankAcctTo;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.BankAcctToExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.BankAcctToExists;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.TrnAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.TrnAmt;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.PayeeUn = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.PayeeUn;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.ExtdPmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.ExtdPmt;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.PayAcct = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.PayAcct;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.DtDue = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.DtDue;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.Memo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.Memo;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.MemoExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.MemoExists;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.BillRefInfo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.BillRefInfo;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.BillRefInfoExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.PmtInfo.BillRefInfoExists;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.PayeeLstIDExists = true;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.PayeeLstID = String.valueOf(paramInt);
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.PayeeUn.PayeeID = paramString2;
    if (paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.InitialAmtExists) {
      localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.TrnAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtRq.InitialAmt;
    }
    localTypePmtTrnRqV1.PmtTrnRq.TrnRqCm = new TypeTrnRqCm();
    localTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TrnUID = "0";
    return localTypePmtTrnRqV1;
  }
  
  private TypePmtTrnRqV1 a(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, String paramString1, int paramInt, String paramString2, String paramString3)
    throws Exception
  {
    TypePmtTrnRqV1 localTypePmtTrnRqV1 = new TypePmtTrnRqV1();
    localTypePmtTrnRqV1.PmtTrnRq = new TypePmtTrnRqV1Aggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn = new TypePmtTrnRqUn();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.__memberName = "PmtRq";
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq = new TypePmtRqAggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.CurDefExists = true;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.CurDef = MsgBuilder.getOFX200CurrencyEnum(paramString3);
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo = new TypePmtInfoAggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.BankAcctFrom = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.BankAcctFrom;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.BankAcctTo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.BankAcctTo;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.BankAcctToExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.BankAcctToExists;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.TrnAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.TrnAmt;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.PayeeUn = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.PayeeUn;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.ExtdPmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.ExtdPmt;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.PayAcct = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.PayAcct;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.DtDue = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.DtDue;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.Memo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.Memo;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.MemoExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.MemoExists;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.BillRefInfo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.BillRefInfo;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.BillRefInfoExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.PmtInfo.BillRefInfoExists;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.PayeeLstIDExists = true;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.PayeeLstID = String.valueOf(paramInt);
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.PayeeUn.PayeeID = paramString2;
    if (paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.InitialAmtExists) {
      localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.TrnAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqUn.RecPmtModRq.InitialAmt;
    }
    localTypePmtTrnRqV1.PmtTrnRq.TrnRqCm = new TypeTrnRqCm();
    localTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TrnUID = "0";
    return localTypePmtTrnRqV1;
  }
  
  private TypePmtTrnRqV1 a(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, String paramString)
  {
    TypePmtTrnRqV1 localTypePmtTrnRqV1 = new TypePmtTrnRqV1();
    localTypePmtTrnRqV1.PmtTrnRq = new TypePmtTrnRqV1Aggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn = new TypePmtTrnRqUn();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.__memberName = "PmtCancRq";
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtCancRq = new TypePmtCancRqAggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtCancRq.SrvrTID = paramString;
    localTypePmtTrnRqV1.PmtTrnRq.TrnRqCm = new TypeTrnRqCm();
    localTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TrnUID = "0";
    return localTypePmtTrnRqV1;
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, TypeUserData paramTypeUserData, String paramString1, String paramString2)
    throws BPWException
  {
    FFSDebug.log("RecPmtProcessor2.processExtdPmtInfo: start, recSrvrTID =" + paramString1 + ", mode =" + paramString2, 6);
    try
    {
      if ((paramTypeUserData._userDefined != null) && ((paramTypeUserData._userDefined instanceof Hashtable))) {
        if (paramString2.equals("mod")) {
          BPWExtraInfo.processImplicitMod(paramFFSConnectionHolder, DBUtil.getFIId(paramTypeUserData), paramString1, "IFXRECPMT", (Hashtable)paramTypeUserData._userDefined);
        } else if ((paramString2.equals("add")) && (((Hashtable)paramTypeUserData._userDefined).size() > 0)) {
          BPWExtraInfo.processXtraInfo(paramFFSConnectionHolder, DBUtil.getFIId(paramTypeUserData), paramString1, "IFXRECPMT", (Hashtable)paramTypeUserData._userDefined);
        }
      }
    }
    catch (Exception localException)
    {
      String str = "*** PmtProcessor2.processExtdPmtInfo: failed ";
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new BPWException(localException + localException.toString());
    }
    FFSDebug.log("RecPmtProcessor2.processExtdPmtInfo: end", 6);
  }
  
  public static String deleteLimit(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, HashMap paramHashMap, String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("RecPmtProcessor2.deleteLimit", ": starts....", 6);
    String str = null;
    int i = LimitCheckApprovalProcessor.processPmtDelete(paramFFSConnectionHolder, paramPmtInfo, paramHashMap);
    str = LimitCheckApprovalProcessor.mapToStatus(i);
    if ((paramBoolean) && ("LIMIT_REVERT_SUCCEEDED".equals(str)))
    {
      paramPmtInfo.Status = paramString;
      PmtInstruction.updateStatus(paramFFSConnectionHolder, paramPmtInfo.SrvrTID, paramString);
    }
    else if ((paramBoolean) && (!"LIMIT_REVERT_SUCCEEDED".equals(str)))
    {
      paramPmtInfo.Status = str;
      PmtInstruction.updateStatus(paramFFSConnectionHolder, paramPmtInfo.SrvrTID, str);
    }
    else
    {
      paramPmtInfo.Status = str;
    }
    FFSDebug.log("RecPmtProcessor2.deleteLimit", ": ends....", 6);
    return str;
  }
  
  public static String doLimitChecking(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo, HashMap paramHashMap, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("RecPmtProcessor2.doLimitChecking", ": starts....", 6);
    String str = null;
    if (paramPmtInfo.payeeInfo == null) {
      try
      {
        paramPmtInfo.payeeInfo = Payee.findPayeeByID(paramFFSConnectionHolder, paramPmtInfo.PayeeID);
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "RecPmtProcessor2.doLimitChecking: Failed to find a payee " + localException.toString(), 0);
        throw new FFSException(localException.toString());
      }
    }
    int i = LimitCheckApprovalProcessor.processPmtAdd(paramFFSConnectionHolder, paramPmtInfo, paramHashMap);
    str = LimitCheckApprovalProcessor.mapToStatus(i);
    if ((paramBoolean) && (!"WILLPROCESSON".equalsIgnoreCase(str)))
    {
      if ("APPROVAL_PENDING".equalsIgnoreCase(str)) {
        paramPmtInfo.Status = "APPROVAL_PENDING";
      }
      PmtInstruction.updateStatus(paramFFSConnectionHolder, paramPmtInfo.SrvrTID, str);
    }
    FFSDebug.log("RecPmtProcessor2.doLimitChecking", ": ends....", 6);
    return str;
  }
  
  public static void processApprovalResult(String paramString1, String paramString2, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "RecPmtProcessor2.processApprovalResult: ";
    FFSDebug.log(str1, ": Starts ...", 6);
    int i = 0;
    String str2 = null;
    if ((paramString2 == null) || (paramString2.trim().length() == 0))
    {
      i = 17504;
      str2 = "Decision from Approval System is null";
      throw new FFSException(i, str1 + str2);
    }
    if ((paramString1 == null) || (paramString1.trim().length() == 0))
    {
      i = 17501;
      str2 = "Transaction id is null";
      throw new FFSException(i, str1 + str2);
    }
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    int j = localPropertyConfig.LogLevel;
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      PmtInfo localPmtInfo = PmtInstruction.getPmtInfo(paramString1, localFFSConnectionHolder);
      if (localPmtInfo == null)
      {
        i = 17502;
        str2 = "Transaction id is invalid";
        throw new FFSException(i, str1 + str2);
      }
      localObject1 = localPmtInfo.Status;
      int k = -1;
      if (!"APPROVAL_PENDING".equalsIgnoreCase((String)localObject1))
      {
        i = 17503;
        str2 = "Transaction not waiting for Approval";
        throw new FFSException(i, str1 + str2);
      }
      int m;
      Object localObject3;
      Object localObject4;
      if (paramString2.compareTo("Approved") == 0)
      {
        Object localObject2;
        if (localPmtInfo.StartDate < DBUtil.getCurrentStartDate())
        {
          FFSDebug.log(str1, ": The payment's due date ", "has expired.  SrvrTid: " + localPmtInfo.SrvrTID + "Due date: " + localPmtInfo.StartDate, 0);
          localObject2 = BPWLocaleUtil.getMessage(100002, null, "OFX_MESSAGE");
          throw new FFSException(100002, (String)localObject2);
        }
        FFSDebug.log(str1, ": Processing Approved Payment", 6);
        localObject1 = (localPmtInfo.getImmediateFundAllocation() != null) && (localPmtInfo.getImmediateFundAllocation().booleanValue()) ? "FUNDSALLOCACTIVE" : "WILLPROCESSON";
        k = 500;
        PmtInstruction.updateStatus(localFFSConnectionHolder, paramString1, (String)localObject1);
        if ((localPmtInfo.getImmediateFundAllocation() != null) && (localPmtInfo.getImmediateFundAllocation().booleanValue())) {
          try
          {
            localObject2 = Payee.findPayeeByID(localFFSConnectionHolder, localPmtInfo.getPayeeID());
            new PaymentProcessor().a(localPmtInfo, (PayeeInfo)localObject2, localFFSConnectionHolder, new HashMap());
          }
          catch (Exception localException1)
          {
            String str4 = str1 + ": Failed to do immediate fund allocation" + ". Error: " + FFSDebug.stackTrace(localException1);
            FFSDebug.log(str4);
            throw new FFSException(str4);
          }
        } else {
          addScheduleForSinglePmt(localFFSConnectionHolder, localPmtInfo);
        }
      }
      else if (paramString2.compareTo("Rejected") == 0)
      {
        FFSDebug.log(str1, ": Processing rejected Payment", 6);
        m = LimitCheckApprovalProcessor.processPmtReject(localFFSConnectionHolder, localPmtInfo, paramHashMap);
        try
        {
          SrvrTrans.updatePmtStatus(localFFSConnectionHolder, paramString1, "FAILEDON");
        }
        catch (Exception localException2)
        {
          String str5 = str1 + ": Failed to update payment history" + ". Error: " + FFSDebug.stackTrace(localException2);
          FFSDebug.log(str5);
          throw new FFSException(str5);
        }
        localObject3 = LimitCheckApprovalProcessor.mapToStatus(m);
        if ("LIMIT_REVERT_SUCCEEDED".equals(localObject3))
        {
          localObject1 = "APPROVAL_REJECTED";
          k = 501;
          PmtInstruction.updateStatus(localFFSConnectionHolder, paramString1, (String)localObject1);
        }
        else
        {
          localObject1 = localObject3;
          k = 402;
          PmtInstruction.updateStatus(localFFSConnectionHolder, paramString1, (String)localObject1);
          if (j >= 1)
          {
            int n = Integer.parseInt(localPmtInfo.CustomerID);
            localObject4 = FFSUtil.getBigDecimal(localPmtInfo.getAmt());
            String str7 = BPWUtil.getAccountIDWithAccountType(localPmtInfo.AcctDebitID, localPmtInfo.AcctDebitType);
            LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(k, null, "BILLPAY_AUDITLOG_MESSAGE");
            TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), localPmtInfo.submittedBy, null, null, localLocalizableString, localPmtInfo.LogID, 4400, n, (BigDecimal)localObject4, localPmtInfo.CurDef, localPmtInfo.SrvrTID, (String)localObject1, localPmtInfo.PayAcct, null, str7, null, 0);
          }
          localFFSConnectionHolder.conn.commit();
          i = 17505;
          str2 = "Limit Revert Failed";
          throw new FFSException(i, str1 + str2);
        }
      }
      else
      {
        i = 17506;
        str2 = "Decision from Approval System is invalid";
        throw new FFSException(i, str1 + str2);
      }
      if (j >= 4)
      {
        m = Integer.parseInt(localPmtInfo.CustomerID);
        localObject3 = FFSUtil.getBigDecimal(localPmtInfo.getAmt());
        String str6 = BPWUtil.getAccountIDWithAccountType(localPmtInfo.AcctDebitID, localPmtInfo.AcctDebitType);
        localObject4 = BPWLocaleUtil.getLocalizedMessage(k, null, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), localPmtInfo.submittedBy, null, null, (ILocalizable)localObject4, localPmtInfo.LogID, 4400, m, (BigDecimal)localObject3, localPmtInfo.CurDef, localPmtInfo.SrvrTID, (String)localObject1, localPmtInfo.PayAcct, null, str6, null, 0);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      Object localObject1 = "*** " + str1 + " failed: ";
      String str3 = null;
      localFFSConnectionHolder.conn.rollback();
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log((String)localObject1, str3, 0);
      throw localFFSException;
    }
    finally
    {
      if (localFFSConnectionHolder != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  public static void addScheduleForSinglePmt(FFSConnectionHolder paramFFSConnectionHolder, PmtInfo paramPmtInfo)
    throws BPWException
  {
    FFSDebug.log("RecPmtProcessor2.addScheduleForSinglePmt", ": Starts ....", 6);
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramPmtInfo.FIID;
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = 10;
    localScheduleInfo.StartDate = paramPmtInfo.StartDate;
    localScheduleInfo.LogID = paramPmtInfo.LogID;
    localScheduleInfo.InstanceCount = 1;
    String str;
    try
    {
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "FUNDTRN", paramPmtInfo.SrvrTID, localScheduleInfo);
    }
    catch (Exception localException1)
    {
      str = "RecPmtProcessor2.addScheduleForSinglePmt: Failed. Error: " + FFSDebug.stackTrace(localException1);
      throw new BPWException(str);
    }
    try
    {
      localScheduleInfo.Frequency = 11;
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      localScheduleInfo.InstanceCount = localPropertyConfig.getFundsAllocRetries();
      localScheduleInfo.NextInstanceDate = localScheduleInfo.StartDate;
      ScheduleInfo.moveNextInstanceDate(localScheduleInfo);
      localScheduleInfo.StartDate = localScheduleInfo.NextInstanceDate;
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "RECFUNDTRN", paramPmtInfo.SrvrTID, localScheduleInfo);
    }
    catch (Exception localException2)
    {
      str = "RecPmtProcessor2.addScheduleForSinglePmt: Failed. Error: " + FFSDebug.stackTrace(localException2);
      throw new BPWException(str);
    }
  }
  
  public BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "RecPmtProcessor2.getBankingDaysInRange: ";
    FFSDebug.log(str1 + "start. FIID = " + paramBankingDays.getFiID() + ", startDate= " + DBUtil.calendarToString(paramBankingDays.getStartDate()) + ", endDate = " + DBUtil.calendarToString(paramBankingDays.getEndDate()), 6);
    int i = (int)((paramBankingDays.getEndDate().getTimeInMillis() - paramBankingDays.getStartDate().getTimeInMillis()) / 86400000L + 1L);
    boolean[] arrayOfBoolean = new boolean[i];
    int j = 0;
    Calendar localCalendar1 = Calendar.getInstance();
    Calendar localCalendar2 = (Calendar)paramBankingDays.getStartDate().clone();
    Calendar localCalendar3 = paramBankingDays.getEndDate();
    while ((localCalendar2.get(6) < localCalendar1.get(6)) && (localCalendar2.get(1) <= localCalendar1.get(1)))
    {
      arrayOfBoolean[j] = false;
      j++;
      localCalendar2.add(5, 1);
    }
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new BPWException("Null connection returned");
      }
    }
    catch (Exception localException1)
    {
      String str2 = "RecPmtProcessor2.getBankingDaysInRange: failed to get DB connection ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localException1), 0);
      throw new BPWException(str2 + localException1.toString());
    }
    int k = DBUtil.getCurrentStartDate() / 100;
    int m = DBUtil.getCurrentStartDate() / 100;
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    boolean bool = localPropertyConfig.SupportImmediatePmt;
    FulfillmentInfo[] arrayOfFulfillmentInfo = BPWRegistryUtil.getAllFunfillmentInfo();
    try
    {
      if (!bool) {
        k = DBUtil.getNextRunDate(localFFSConnectionHolder, paramBankingDays.getFiID(), "FUNDTRN");
      }
      if (arrayOfFulfillmentInfo != null) {
        for (int n = 0; n < arrayOfFulfillmentInfo.length; n++)
        {
          str3 = BPWRegistryUtil.findPaymentInstructionType(paramBankingDays.getFiID(), arrayOfFulfillmentInfo[n].RouteID);
          m = DBUtil.getNextRunDate(localFFSConnectionHolder, paramBankingDays.getFiID(), str3);
          if (k > m) {
            k = m;
          }
        }
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException2)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = "RecPmtProcessor2.getBankingDaysInRange: failed ";
      FFSDebug.log(str3 + FFSDebug.stackTrace(localException2), 0);
      throw new BPWException(str3 + localException2.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    try
    {
      while ((localCalendar2.before(localCalendar3)) || (localCalendar2.equals(localCalendar3)))
      {
        int i1 = BPWUtil.calendarToDueDateFormatInt(localCalendar2);
        if ((SmartCalendar.isBusinessDay(paramBankingDays.getFiID(), i1, "BillPay")) && (i1 >= k)) {
          arrayOfBoolean[j] = true;
        } else {
          arrayOfBoolean[j] = false;
        }
        j++;
        localCalendar2.add(5, 1);
      }
    }
    catch (Exception localException3)
    {
      throw new FFSException(localException3, localException3.toString());
    }
    paramBankingDays.setBankingDays(arrayOfBoolean);
    return paramBankingDays;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.RecPmtProcessor2
 * JD-Core Version:    0.7.0.1
 */