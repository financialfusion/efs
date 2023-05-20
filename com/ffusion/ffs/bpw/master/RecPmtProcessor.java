package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.RecPmtInstruction;
import com.ffusion.ffs.bpw.db.RecSrvrTIDToSrvrTID;
import com.ffusion.ffs.bpw.db.Trans;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecPmtInfo;
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
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumIDScopeEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPayeeV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeExtdPayeeV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtCancRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypePmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtCancRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtCancRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtModRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecPmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.math.BigDecimal;
import java.util.HashMap;

public class RecPmtProcessor
  implements DBConsts, BPWResource, OFXConsts, FFSConst, ScheduleConstants
{
  private int jdField_null = 4;
  private boolean jdField_void = false;
  private PmtProcessor jdField_long;
  private String jdField_goto = "";
  
  public RecPmtProcessor() {}
  
  public RecPmtProcessor(PmtProcessor paramPmtProcessor)
  {
    this.jdField_long = paramPmtProcessor;
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.jdField_null = localPropertyConfig.LogLevel;
    this.jdField_void = localPropertyConfig.UseExtdPayeeID;
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
      str2 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.__memberName;
      if (str2.equals("RecPmtRq")) {
        localTypeRecPmtTrnRsV1 = a(paramTypeRecPmtTrnRqV1, paramTypeUserData, str1);
      } else if (str2.equals("RecPmtModRq")) {
        localTypeRecPmtTrnRsV1 = jdMethod_do(paramTypeRecPmtTrnRqV1, paramTypeUserData, str1);
      } else if (str2.equals("RecPmtCancRq")) {
        localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1, paramTypeUserData, str1);
      } else {
        throw new OFXException("Unsupported request type", 2000);
      }
    }
    catch (OFXException localOFXException)
    {
      localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException)
    {
      String str2 = "*** RecPmtProcessor.processRecPmtTrnRqV1 failed:";
      FFSDebug.log(localException, str2);
      FFSDebug.console(str2 + localException.toString());
      localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    return localTypeRecPmtTrnRsV1;
  }
  
  private TypeRecPmtTrnRsV1 a(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
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
      TypePmtInfoV1Aggregate localTypePmtInfoV1Aggregate = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo;
      localObject1 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.CurDef;
      str3 = "0.0";
      String str4 = "0.0";
      if (paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.InitialAmtExists) {
        str3 = String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.InitialAmt);
      } else {
        str3 = String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.TrnAmt);
      }
      if (!FFSUtil.isPositive(str3))
      {
        localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2012, " ");
        localObject2 = localTypeRecPmtTrnRsV1;
        return localObject2;
      }
      if (paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.FinalAmtExists) {
        str4 = String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.FinalAmt);
      } else {
        str4 = String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.TrnAmt);
      }
      if (!FFSUtil.isPositive(str4))
      {
        localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2012, " ");
        localObject2 = localTypeRecPmtTrnRsV1;
        return localObject2;
      }
      Object localObject2 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.RecurrInst;
      RecPmtInstruction localRecPmtInstruction = a(localTypePmtInfoV1Aggregate, str1, str2, str3, str4, (TypeRecurrInstAggregate)localObject2, paramString, paramTypeUserData._submittedBy, (EnumCurrencyEnum)localObject1);
      this.jdField_goto = localRecPmtInstruction.getStatus();
      localRecPmtInstruction.setRecSrvrTID();
      String str5 = localRecPmtInstruction.getRecSrvrTID();
      ScheduleInfo localScheduleInfo = createScheduleInfo(localRecPmtInstruction, str2, (TypeRecurrInstAggregate)localObject2);
      if ((localFFSConnectionHolder1.conn == null) || (localFFSConnectionHolder2.conn == null)) {
        throw new Exception("Can not get DB Connection.");
      }
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm.TrnUID))
      {
        if (this.jdField_null >= 3)
        {
          int i = Integer.parseInt(localRecPmtInstruction.getCustomerID());
          BigDecimal localBigDecimal1 = FFSUtil.getBigDecimal(localRecPmtInstruction.getAmt());
          localObject4 = BPWUtil.getAccountIDWithAccountType(localRecPmtInstruction.getAcctDebitID(), localRecPmtInstruction.getAcctDebitType());
          localObject5 = BPWLocaleUtil.getMessage(812, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject6 = new String[] { localObject5 };
          LocalizableString localLocalizableString1 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject6, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString1, paramString, 4409, i, localBigDecimal1, localRecPmtInstruction.getRecPmtInfo().CurDef, str5, "DUPLICATE", localRecPmtInstruction.getPayAcct(), null, (String)localObject4, null, 0);
        }
        localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2019, " ");
        localFFSConnectionHolder1.conn.commit();
        localObject3 = localTypeRecPmtTrnRsV1;
        return localObject3;
      }
      Object localObject3 = this.jdField_long.processImplicitPayeeRq(localTypePmtInfoV1Aggregate, str1, localFFSConnectionHolder1, localFFSConnectionHolder2, paramString);
      if (localObject3 == null) {
        throw new BPWException("Could not create payeeID!");
      }
      int j = Integer.parseInt(localTypePmtInfoV1Aggregate.PayeeLstID);
      Object localObject4 = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
      if (localObject4 == null) {
        throw new BPWException("AuditAgent is null!!");
      }
      ScheduleInfo.createSchedule(localFFSConnectionHolder1, "RECPMTTRN", str5, localScheduleInfo);
      localRecPmtInstruction.setPayeeID(((PayeeInfo)localObject3).PayeeID);
      localRecPmtInstruction.setPayeeListID(j);
      localRecPmtInstruction.storeToDB(localFFSConnectionHolder1);
      localTypeRecPmtTrnRsV1 = buildRecPmtAddRsV1(paramTypeRecPmtTrnRqV1, str5, Integer.toString(j), (PayeeInfo)localObject3, localRecPmtInstruction.getRecPmtInfo().CurDef);
      ((AuditAgent)localObject4).saveRecPmtRsV1(localTypeRecPmtTrnRsV1, str1, localFFSConnectionHolder1);
      Object localObject5 = jdMethod_if(paramTypeRecPmtTrnRqV1, str5, j, ((PayeeInfo)localObject3).PayeeID, localRecPmtInstruction.getRecPmtInfo().CurDef);
      Object localObject6 = this.jdField_long.processPmtAddRqV1(localFFSConnectionHolder1, localFFSConnectionHolder2, (TypePmtTrnRqV1)localObject5, paramTypeUserData, paramString, str5);
      RecSrvrTIDToSrvrTID.create(localFFSConnectionHolder1, str5, ((TypePmtTrnRsV1)localObject6).PmtTrnRs.PmtTrnRsV1Un.PmtRs.SrvrTID);
      if (this.jdField_null >= 3)
      {
        int k = Integer.parseInt(localRecPmtInstruction.getCustomerID());
        BigDecimal localBigDecimal2 = FFSUtil.getBigDecimal(localRecPmtInstruction.getAmt());
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
      localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder1.conn.rollback();
      localObject1 = "*** RecPmtProcessor.processRecPmtAddRqV1 failed:";
      str3 = localBPWException.toString();
      FFSDebug.log(localBPWException, (String)localObject1);
      FFSDebug.console((String)localObject1 + str3);
      if (localBPWException.getErrorCode() == -1) {
        localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, "Server internal error");
      } else {
        localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder1.conn.rollback();
      Object localObject1 = "*** RecPmtProcessor.processRecPmtAddRqV1 failed:";
      String str3 = localException.toString();
      FFSDebug.log(localException, (String)localObject1);
      FFSDebug.console((String)localObject1 + str3);
      localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, "Server internal error");
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
  
  private TypeRecPmtTrnRsV1 jdMethod_do(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
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
      TypePmtInfoV1Aggregate localTypePmtInfoV1Aggregate = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo;
      localObject1 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.CurDef;
      str3 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.InitialAmtExists ? String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.InitialAmt) : String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.TrnAmt);
      String str4 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.FinalAmtExists ? String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.FinalAmt) : String.valueOf(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.FinalAmt);
      if ((!FFSUtil.isPositive(str3)) || (!FFSUtil.isPositive(str4)))
      {
        localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2012, " ");
        localObject2 = localTypeRecPmtTrnRsV1;
        return localObject2;
      }
      Object localObject2 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.RecurrInst;
      RecPmtInstruction localRecPmtInstruction = a(localTypePmtInfoV1Aggregate, str1, str2, str3, str4, (TypeRecurrInstAggregate)localObject2, paramString, paramTypeUserData._submittedBy, (EnumCurrencyEnum)localObject1);
      this.jdField_goto = localRecPmtInstruction.getStatus();
      String str5 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.RecSrvrTID;
      localRecPmtInstruction.setRecSrvrTID(str5);
      ScheduleInfo localScheduleInfo = createScheduleInfo(localRecPmtInstruction, str2, (TypeRecurrInstAggregate)localObject2);
      if ((localFFSConnectionHolder1.conn == null) || (localFFSConnectionHolder2.conn == null)) {
        throw new Exception("Can not get DB Connection.");
      }
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm.TrnUID))
      {
        if (this.jdField_null >= 3)
        {
          int i = Integer.parseInt(localRecPmtInstruction.getCustomerID());
          BigDecimal localBigDecimal = FFSUtil.getBigDecimal(localRecPmtInstruction.getAmt());
          localObject4 = BPWUtil.getAccountIDWithAccountType(localRecPmtInstruction.getAcctDebitID(), localRecPmtInstruction.getAcctDebitType());
          String str6 = BPWLocaleUtil.getMessage(814, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject5 = new String[] { str6 };
          LocalizableString localLocalizableString1 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject5, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString1, paramString, 4411, i, localBigDecimal, localRecPmtInstruction.getRecPmtInfo().CurDef, str5, "DUPLICATE", localRecPmtInstruction.getPayAcct(), null, (String)localObject4, null, 0);
        }
        localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2019, " ");
        localFFSConnectionHolder1.conn.commit();
        localObject3 = localTypeRecPmtTrnRsV1;
        return localObject3;
      }
      Object localObject3 = this.jdField_long.processImplicitPayeeRq(localTypePmtInfoV1Aggregate, str1, localFFSConnectionHolder1, localFFSConnectionHolder2, paramString);
      if (!paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.PayeeLstIDExists) {
        throw new OFXException(" ", 10519);
      }
      if (localObject3 == null) {
        throw new BPWException("Could not create payeeID.");
      }
      int j = Integer.parseInt(localTypePmtInfoV1Aggregate.PayeeLstID);
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
        localObject5 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, k, " ");
        return localObject5;
      }
      localRecPmtInstruction.setPayeeID(((PayeeInfo)localObject3).PayeeID);
      localRecPmtInstruction.setPayeeListID(j);
      localRecPmtInstruction.updateRecPmt(localFFSConnectionHolder1);
      localTypeRecPmtTrnRsV1 = buildRecPmtModRsV1(paramTypeRecPmtTrnRqV1, Integer.toString(j), (PayeeInfo)localObject3, localRecPmtInstruction.getRecPmtInfo().CurDef);
      Object localObject5 = localRecPmtInstruction.getRecPmtInfo();
      ((AuditAgent)localObject4).modRecPmtRsV1(localTypeRecPmtTrnRsV1, (RecPmtInfo)localObject5, localFFSConnectionHolder1);
      boolean bool = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.ModPending;
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
              TypePmtTrnRqV1 localTypePmtTrnRqV1 = a(paramTypeRecPmtTrnRqV1, arrayOfString1[n]);
              this.jdField_long.processPmtCancRqV1(localFFSConnectionHolder1, localTypePmtTrnRqV1, paramTypeUserData, paramString);
              RecSrvrTIDToSrvrTID.delete(localFFSConnectionHolder1, str5, arrayOfString1[n]);
            }
          }
        }
        localObject6 = a(paramTypeRecPmtTrnRqV1, str5, j, ((PayeeInfo)localObject3).PayeeID, localRecPmtInstruction.getRecPmtInfo().CurDef);
        localObject7 = this.jdField_long.processPmtAddRqV1(localFFSConnectionHolder1, localFFSConnectionHolder2, (TypePmtTrnRqV1)localObject6, paramTypeUserData, paramString, str5);
        RecSrvrTIDToSrvrTID.create(localFFSConnectionHolder1, str5, ((TypePmtTrnRsV1)localObject7).PmtTrnRs.PmtTrnRsV1Un.PmtRs.SrvrTID);
        if (this.jdField_null >= 3)
        {
          int i1 = Integer.parseInt(localRecPmtInstruction.getCustomerID());
          localObject8 = FFSUtil.getBigDecimal(localRecPmtInstruction.getAmt());
          localObject9 = BPWUtil.getAccountIDWithAccountType(localRecPmtInstruction.getAcctDebitID(), localRecPmtInstruction.getAcctDebitType());
          String str8 = BPWLocaleUtil.getMessage(811, null, "BILLPAY_AUDITLOG_MESSAGE");
          String[] arrayOfString2 = { str8 };
          LocalizableString localLocalizableString2 = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString2, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString2, paramString, 4408, i1, (BigDecimal)localObject8, localRecPmtInstruction.getRecPmtInfo().CurDef, str5, localRecPmtInstruction.getStatus(), localRecPmtInstruction.getPayAcct(), null, (String)localObject9, null, 0);
        }
      }
      if (this.jdField_null >= 3)
      {
        int m = Integer.parseInt(localRecPmtInstruction.getCustomerID());
        localObject6 = FFSUtil.getBigDecimal(localRecPmtInstruction.getAmt());
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
      localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder1.conn.rollback();
      localObject1 = "*** RecPmtProcessor.processRecPmtModRqV1 failed:";
      str3 = localBPWException.toString();
      FFSDebug.log(localBPWException, (String)localObject1);
      FFSDebug.console((String)localObject1 + str3);
      if (localBPWException.getErrorCode() == -1) {
        localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, "Server internal error");
      } else {
        localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder1.conn.rollback();
      Object localObject1 = "*** RecPmtProcessor.processRecPmtModRqV1 failed:";
      String str3 = localException.toString();
      FFSDebug.log(localException, (String)localObject1);
      FFSDebug.console((String)localObject1 + str3);
      localTypeRecPmtTrnRsV1 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, "Server internal error");
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
  
  private TypeRecPmtTrnRsV1 jdMethod_if(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    String str1 = PmtProcessor.getUID(paramTypeUserData);
    String str2 = DBUtil.getFIId(paramTypeUserData);
    FFSDebug.log("RecPmtProcessor.processRecPmtCancRqV1 start, uid=" + str1, 6);
    TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV11 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      String str3 = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtCancRq.RecSrvrTID;
      if (this.jdField_null >= 3)
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
        TypeRecPmtTrnRsV1 localTypeRecPmtTrnRsV12 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, i, " ");
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
        if (this.jdField_null >= 3)
        {
          int j = Integer.parseInt(((RecPmtInstruction)localObject1).getCustomerID());
          BigDecimal localBigDecimal1 = FFSUtil.getBigDecimal(((RecPmtInstruction)localObject1).getAmt());
          localObject3 = BPWUtil.getAccountIDWithAccountType(((RecPmtInstruction)localObject1).getAcctDebitID(), ((RecPmtInstruction)localObject1).getAcctDebitType());
          String str5 = BPWLocaleUtil.getMessage(816, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject4 = new String[] { str5 };
          localObject5 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject4, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, (ILocalizable)localObject5, paramString, 4413, j, localBigDecimal1, ((RecPmtInstruction)localObject1).getRecPmtInfo().CurDef, str3, "DUPLICATE", ((RecPmtInstruction)localObject1).getPayAcct(), null, (String)localObject3, null, 0);
        }
        localTypeRecPmtTrnRsV11 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2019, " ");
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
      this.jdField_goto = "CANCELEDON";
      ((AuditAgent)localObject2).cancelOFX151RecPmtRsV1(str3, localFFSConnectionHolder);
      boolean bool = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtCancRq.CanPending;
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
              this.jdField_long.processPmtCancRqV1(localFFSConnectionHolder, (TypePmtTrnRqV1)localObject5, paramTypeUserData, paramString);
            }
          }
        }
      }
      if (this.jdField_null >= 3)
      {
        int k = Integer.parseInt(((RecPmtInstruction)localObject1).getCustomerID());
        BigDecimal localBigDecimal2 = FFSUtil.getBigDecimal(((RecPmtInstruction)localObject1).getAmt());
        localObject4 = BPWUtil.getAccountIDWithAccountType(((RecPmtInstruction)localObject1).getAcctDebitID(), ((RecPmtInstruction)localObject1).getAcctDebitType());
        localObject5 = BPWLocaleUtil.getMessage(815, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString = { localObject5 };
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString, paramString, 4412, k, localBigDecimal2, ((RecPmtInstruction)localObject1).getRecPmtInfo().CurDef, str3, this.jdField_goto, ((RecPmtInstruction)localObject1).getPayAcct(), null, (String)localObject4, null, 0);
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      localTypeRecPmtTrnRsV11 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject1 = "*** RecPmtProcessor.processRecPmtCancRqV1 failed:";
      String str4 = localException.toString();
      FFSDebug.log(localException, (String)localObject1);
      FFSDebug.console((String)localObject1 + str4);
      localTypeRecPmtTrnRsV11 = jdMethod_if(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("RecPmtProcessor.processRecPmtCancRqV1 end, uid=" + str1, 6);
    return localTypeRecPmtTrnRsV11;
  }
  
  private RecPmtInstruction a(TypePmtInfoV1Aggregate paramTypePmtInfoV1Aggregate, String paramString1, String paramString2, String paramString3, String paramString4, TypeRecurrInstAggregate paramTypeRecurrInstAggregate, String paramString5, String paramString6, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws Exception, BPWException, OFXException
  {
    RecPmtInstruction localRecPmtInstruction = new RecPmtInstruction();
    localRecPmtInstruction.setCustomerID(paramString1);
    localRecPmtInstruction.setFIID(paramString2);
    localRecPmtInstruction.setStatus("WILLPROCESSON");
    localRecPmtInstruction.setLogID(paramString5);
    localRecPmtInstruction.mapFromPmtInfoV1(paramTypePmtInfoV1Aggregate, paramEnumCurrencyEnum);
    localRecPmtInstruction.setInitialAmount(paramString3);
    localRecPmtInstruction.setFinalAmount(paramString4);
    int i = CommonProcessor.mapOFX151FreqToBPWFreq(paramTypeRecurrInstAggregate.Freq.value());
    localRecPmtInstruction.setFrequency(i);
    localRecPmtInstruction.setSubmittedBy(paramString6);
    int j;
    String str3;
    String str2;
    if (paramTypePmtInfoV1Aggregate.DtDue != null)
    {
      String str1 = paramTypePmtInfoV1Aggregate.DtDue.substring(0, 8);
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
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1UnExists = true;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un = new TypeRecPmtTrnRsV1Un();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.__memberName = "RecPmtRs";
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs = new TypeRecPmtRsV1Aggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.RecSrvrTID = paramString1;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.PayeeLstID = paramString2;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.CurDef = MsgBuilder.getOFX151CurrencyEnum(paramString3);
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayeeExists = false;
    if ((!this.jdField_void) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayeeExists = true;
      localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee = new TypeExtdPayeeV1Aggregate();
      localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.ExtdPayeeV1CmExists = true;
      localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.ExtdPayeeV1Cm = new TypeExtdPayeeV1Cm();
      localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.ExtdPayeeV1Cm.PayeeID = (!this.jdField_void ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.ExtdPayeeV1Cm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = EnumIDScopeEnum.USER;
      } else {
        localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.ExtdPayee.ExtdPayeeV1Cm.IDScope = EnumIDScopeEnum.GLOBAL;
      }
    }
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.PayeeLstIDExists = true;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.PmtInfo.PayeeLstID = paramString2;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.RecurrInst = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.RecurrInst;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.InitialAmtExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.InitialAmtExists;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.InitialAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.InitialAmt;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.FinalAmtExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.FinalAmtExists;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtRs.FinalAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.FinalAmt;
    localTypeRecPmtTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, null);
    return new TypeRecPmtTrnRsV1(localTypeRecPmtTrnRsV1Aggregate);
  }
  
  public TypeRecPmtTrnRsV1 buildRecPmtModRsV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, String paramString1, PayeeInfo paramPayeeInfo, String paramString2)
    throws Exception
  {
    TypeRecPmtTrnRsV1Aggregate localTypeRecPmtTrnRsV1Aggregate = new TypeRecPmtTrnRsV1Aggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1UnExists = true;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un = new TypeRecPmtTrnRsV1Un();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.__memberName = "RecPmtModRs";
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs = new TypeRecPmtModRsV1Aggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.RecSrvrTID = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.RecSrvrTID;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.CurDefExists = true;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.CurDef = MsgBuilder.getOFX151CurrencyEnum(paramString2);
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.PmtInfo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.PmtInfo.PayeeLstIDExists = true;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.PmtInfo.PayeeLstID = paramString1;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.RecurrInst = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.RecurrInst;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.InitialAmtExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.InitialAmtExists;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.InitialAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.InitialAmt;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.FinalAmtExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.FinalAmtExists;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.FinalAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.FinalAmt;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtModRs.ModPending = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.ModPending;
    localTypeRecPmtTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, null);
    return new TypeRecPmtTrnRsV1(localTypeRecPmtTrnRsV1Aggregate);
  }
  
  public TypeRecPmtTrnRsV1 buildRecPmtCancRsV1(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1)
    throws Exception
  {
    TypeRecPmtTrnRsV1Aggregate localTypeRecPmtTrnRsV1Aggregate = new TypeRecPmtTrnRsV1Aggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1UnExists = true;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un = new TypeRecPmtTrnRsV1Un();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.__memberName = "RecPmtCancRs";
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtCancRs = new TypeRecPmtCancRsV1Aggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtCancRs.RecSrvrTID = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtCancRq.RecSrvrTID;
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1Un.RecPmtCancRs.CanPending = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtCancRq.CanPending;
    localTypeRecPmtTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeRecPmtTrnRqV1.RecPmtTrnRq.TrnRqCm, null);
    return new TypeRecPmtTrnRsV1(localTypeRecPmtTrnRsV1Aggregate);
  }
  
  public RecPmtInfo[] getRecPmtById(String[] paramArrayOfString)
    throws Exception
  {
    FFSDebug.log("RecPmtProcessor.getRecPmtById (multiple): start ", 6);
    RecPmtInfo localRecPmtInfo = null;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localRecPmtInfo = new RecPmtInfo();
      localRecPmtInfo.statusCode = 16504;
      localRecPmtInfo.statusMsg = "Rec ID Array  is null or empty";
      FFSDebug.log("RecPmtProcessor.getRecPmtById (multiple): failed  null or empty id array passed", 0);
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
      String str1 = "RecPmtProcessor.getRecPmtById (multiple) : failed to get DB connection ";
      FFSDebug.log(str1 + FFSDebug.stackTrace(localException1), 0);
      throw new BPWException(str1 + localException1.toString());
    }
    RecPmtInfo[] arrayOfRecPmtInfo = null;
    try
    {
      arrayOfRecPmtInfo = RecPmtInstruction.getRecPmtById(paramArrayOfString, (FFSConnectionHolder)localObject1);
      FFSDebug.log("infoArray[0].rec freq = " + arrayOfRecPmtInfo[0].getRecFrequencyValue(), 6);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Exception localException2)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      String str2 = "RecPmtProcessor.getRecPmtById (multiple): failed ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localException2), 0);
      throw new BPWException(str2 + localException2.toString());
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    return arrayOfRecPmtInfo;
  }
  
  private TypeRecPmtTrnRsV1 jdMethod_if(TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
  {
    TypeRecPmtTrnRsV1Aggregate localTypeRecPmtTrnRsV1Aggregate = new TypeRecPmtTrnRsV1Aggregate();
    localTypeRecPmtTrnRsV1Aggregate.RecPmtTrnRsV1UnExists = false;
    localTypeRecPmtTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt, paramString);
    return new TypeRecPmtTrnRsV1(localTypeRecPmtTrnRsV1Aggregate);
  }
  
  private TypePmtTrnRqV1 jdMethod_if(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, String paramString1, int paramInt, String paramString2, String paramString3)
    throws Exception
  {
    TypePmtTrnRqV1 localTypePmtTrnRqV1 = new TypePmtTrnRqV1();
    localTypePmtTrnRqV1.PmtTrnRq = new TypePmtTrnRqV1Aggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un = new TypePmtTrnRqV1Un();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.__memberName = "PmtRq";
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq = new TypePmtRqV1Aggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.CurDefExists = true;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.CurDef = MsgBuilder.getOFX151CurrencyEnum(paramString3);
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo = new TypePmtInfoV1Aggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.BankAcctFrom = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.BankAcctFrom;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.BankAcctTo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.BankAcctTo;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.BankAcctToExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.BankAcctToExists;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.TrnAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.TrnAmt;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.PayeeUn = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.PayeeUn;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.ExtdPmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.ExtdPmt;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.PayAcct = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.PayAcct;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.DtDue = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.DtDue;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.Memo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.Memo;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.MemoExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.MemoExists;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.BillRefInfo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.BillRefInfo;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.BillRefInfoExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.PmtInfo.BillRefInfoExists;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.PayeeLstIDExists = true;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.PayeeLstID = String.valueOf(paramInt);
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.PayeeUn.PayeeID = paramString2;
    if (paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.InitialAmtExists) {
      localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.TrnAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtRq.InitialAmt;
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
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un = new TypePmtTrnRqV1Un();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.__memberName = "PmtRq";
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq = new TypePmtRqV1Aggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.CurDefExists = true;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.CurDef = MsgBuilder.getOFX151CurrencyEnum(paramString3);
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo = new TypePmtInfoV1Aggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.BankAcctFrom = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.BankAcctFrom;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.BankAcctTo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.BankAcctTo;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.BankAcctToExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.BankAcctToExists;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.TrnAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.TrnAmt;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.PayeeUn = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.PayeeUn;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.ExtdPmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.ExtdPmt;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.PayAcct = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.PayAcct;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.DtDue = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.DtDue;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.Memo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.Memo;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.MemoExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.MemoExists;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.BillRefInfo = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.BillRefInfo;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.BillRefInfoExists = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.PmtInfo.BillRefInfoExists;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.PayeeLstIDExists = true;
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.PayeeLstID = String.valueOf(paramInt);
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.PayeeUn.PayeeID = paramString2;
    if (paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.InitialAmtExists) {
      localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtRq.PmtInfo.TrnAmt = paramTypeRecPmtTrnRqV1.RecPmtTrnRq.RecPmtTrnRqV1Un.RecPmtModRq.InitialAmt;
    }
    localTypePmtTrnRqV1.PmtTrnRq.TrnRqCm = new TypeTrnRqCm();
    localTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TrnUID = "0";
    return localTypePmtTrnRqV1;
  }
  
  private TypePmtTrnRqV1 a(TypeRecPmtTrnRqV1 paramTypeRecPmtTrnRqV1, String paramString)
  {
    TypePmtTrnRqV1 localTypePmtTrnRqV1 = new TypePmtTrnRqV1();
    localTypePmtTrnRqV1.PmtTrnRq = new TypePmtTrnRqV1Aggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un = new TypePmtTrnRqV1Un();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.__memberName = "PmtCancRq";
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtCancRq = new TypePmtCancRqV1Aggregate();
    localTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqV1Un.PmtCancRq.SrvrTID = paramString;
    localTypePmtTrnRqV1.PmtTrnRq.TrnRqCm = new TypeTrnRqCm();
    localTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TrnUID = "0";
    return localTypePmtTrnRqV1;
  }
  
  public void processApprovalResult(String paramString1, String paramString2, HashMap paramHashMap)
    throws FFSException
  {
    String str = "RecPmtProcessor.processApprovalResult: ";
    FFSDebug.log(str, ": Starts ...", 6);
    RecPmtProcessor2.processApprovalResult(paramString1, paramString2, paramHashMap);
    FFSDebug.log(str, ": done ...", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.RecPmtProcessor
 * JD-Core Version:    0.7.0.1
 */