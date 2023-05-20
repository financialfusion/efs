package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.db.BPWRecXferExtraInfo;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.RecSrvrTIDToSrvrTID;
import com.ffusion.ffs.bpw.db.RecXferInstruction;
import com.ffusion.ffs.bpw.db.Trans;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.InstructionTypeStatus;
import com.ffusion.ffs.scheduling.db.ScheduleConstants;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBCCAcctFromV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBCCAcctToV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctToV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCAcctToAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraCanRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraCanRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraModRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeRecurrInstAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeStatusV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferPrcStsAggregate;
import com.ffusion.util.beans.LocalizableString;
import java.util.HashMap;
import java.util.Hashtable;

public class RecXferProcessor
  implements FFSConst, ScheduleConstants, DBConsts, OFXConsts, BPWResource
{
  private XferProcessor s;
  private PropertyConfig r;
  private int q = 1;
  private boolean p = false;
  
  public RecXferProcessor(XferProcessor paramXferProcessor)
  {
    this.s = paramXferProcessor;
    this.r = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
    this.q = this.r.LogLevel;
    this.p = this.r.getEnforceEnrollment();
  }
  
  public TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws BPWException
  {
    return processRecIntraTrnRqV1(paramTypeRecIntraTrnRqV1, paramTypeUserData, paramTypeUserData._tran_id);
  }
  
  public TypeRecIntraTrnRsV1 processRecIntraTrnRqV1(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    try
    {
      if ((this.p) && (!Customer.validCustomer(paramTypeUserData._uid)))
      {
        String str1 = "This customer, customerID=" + paramTypeUserData._uid + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
        FFSDebug.log(str1, 0);
        throw new Exception(str1);
      }
    }
    catch (Exception localException1)
    {
      str2 = "*** RecXferProcessor.processRecIntraTrnRqV1 failed:";
      FFSDebug.log(str2 + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    String str2 = "";
    if (paramString != null) {
      str2 = paramString;
    }
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1;
    try
    {
      String str3 = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.__memberName;
      if (str3.equals("RecIntraRq")) {
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1, paramTypeUserData, str2);
      } else if (str3.equals("RecIntraModRq")) {
        localTypeRecIntraTrnRsV1 = jdMethod_do(paramTypeRecIntraTrnRqV1, paramTypeUserData, str2);
      } else if (str3.equals("RecIntraCanRq")) {
        localTypeRecIntraTrnRsV1 = a(paramTypeRecIntraTrnRqV1, paramTypeUserData, str2);
      } else {
        throw new BPWException("Wrong request type!");
      }
    }
    catch (Exception localException2)
    {
      String str4 = "*** RecXferProcessor.processRecIntraTrnRqV1 failed:";
      FFSDebug.log(str4 + localException2.toString());
      localTypeRecIntraTrnRsV1 = a(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
    }
    return localTypeRecIntraTrnRsV1;
  }
  
  private TypeRecIntraTrnRsV1 jdMethod_if(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    FFSDebug.log("RecXferProcessor.processRecXferRqV1: call", 6);
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("RecXferProcessor.processRecXferRqV1:Can not get DB Connection.");
    }
    TypeXferInfoV1Aggregate localTypeXferInfoV1Aggregate = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.IntraRq.XferInfo;
    EnumCurrencyEnum localEnumCurrencyEnum = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.IntraRq.CurDef;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.RecurrInst;
    TypeIntraTrnRsV1[] arrayOfTypeIntraTrnRsV1 = null;
    try
    {
      Object localObject1;
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm.TrnUID))
      {
        if (this.q >= 3)
        {
          localObject1 = new a(null);
          a(localTypeXferInfoV1Aggregate, localTypeRecurrInstAggregate, paramTypeUserData, (a)localObject1, paramString, localEnumCurrencyEnum);
          str1 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).e, ((a)localObject1).f);
          String str2 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).c, ((a)localObject1).j);
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(906, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          RecXferProcessor2.logTransAuditLog(localFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4701, ((a)localObject1).jdField_goto, ((a)localObject1).jdField_for, ((a)localObject1).d, str1, str2, ((a)localObject1).jdField_new, ((a)localObject1).a, "DUPLICATE", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        localTypeRecIntraTrnRsV1 = a(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2019);
        localFFSConnectionHolder.conn.commit();
        localObject1 = localTypeRecIntraTrnRsV1;
        return localObject1;
      }
      localTypeRecIntraTrnRsV1 = processRecXferRqV1(localFFSConnectionHolder, localTypeXferInfoV1Aggregate, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, localTypeRecurrInstAggregate, arrayOfTypeIntraTrnRsV1, paramString, localEnumCurrencyEnum);
      if (localTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsV1Cm.Status.Code == 0)
      {
        localObject1 = this.s.processXferRqV1(localFFSConnectionHolder, localTypeXferInfoV1Aggregate, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecSrvrTID, paramString, localEnumCurrencyEnum);
        RecSrvrTIDToSrvrTID.create(localFFSConnectionHolder, localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsV1Un.RecIntraRs.RecSrvrTID, ((TypeIntraTrnRsV1)localObject1).IntraTrnRs.IntraTrnRsV1Un.IntraRs.SrvrTID);
        arrayOfTypeIntraTrnRsV1 = new TypeIntraTrnRsV1[1];
        arrayOfTypeIntraTrnRsV1[0] = localObject1;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "RecXferProcessor.processRecXferRqV1 failed: ";
      FFSDebug.log(localOFXException, str1);
      localTypeRecIntraTrnRsV1 = jdMethod_do(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "RecXferProcessor.processRecXferRqV1 failed: ";
      FFSDebug.log(localBPWException, str1);
      if (localBPWException.getErrorCode() == -1) {
        localTypeRecIntraTrnRsV1 = a(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
      } else {
        localTypeRecIntraTrnRsV1 = jdMethod_do(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str1 = "RecXferProcessor.processRecXferRqV1 failed: ";
      FFSDebug.log(localException, str1);
      localTypeRecIntraTrnRsV1 = a(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeRecIntraTrnRsV1;
  }
  
  public TypeRecIntraTrnRsV1 processRecXferRqV1(FFSConnectionHolder paramFFSConnectionHolder, TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, TypeUserData paramTypeUserData, TypeTrnRqCm paramTypeTrnRqCm, TypeRecurrInstAggregate paramTypeRecurrInstAggregate, TypeIntraTrnRsV1[] paramArrayOfTypeIntraTrnRsV1, String paramString, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = null;
    a locala = new a(null);
    a(paramTypeXferInfoV1Aggregate, paramTypeRecurrInstAggregate, paramTypeUserData, locala, paramString, paramEnumCurrencyEnum);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("RecXferProcessor.processXferRqV1:AuditAgent is null!!");
    }
    try
    {
      RecXferInstruction localRecXferInstruction = new RecXferInstruction("", locala.jdField_int, locala.l, locala.jdField_goto, locala.jdField_for, locala.jdField_new, locala.jdField_long, locala.e, locala.f, locala.c, locala.j, locala.jdField_else, locala.h, locala.jdField_null, locala.b, "", "WILLPROCESSON", paramString, paramTypeUserData._submittedBy, locala.a, locala.jdField_do);
      locala.d = RecXferInstruction.create(paramFFSConnectionHolder, localRecXferInstruction);
      jdMethod_if(paramTypeUserData._privateTagContainer, paramFFSConnectionHolder, locala.d, "Add");
      jdMethod_if(paramFFSConnectionHolder, locala);
      if (this.q >= 3)
      {
        String str1 = BPWUtil.getAccountIDWithAccountType(locala.e, locala.f);
        String str2 = BPWUtil.getAccountIDWithAccountType(locala.c, locala.j);
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(905, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        RecXferProcessor2.logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4700, locala.jdField_goto, locala.jdField_for, locala.d, str1, str2, locala.jdField_new, locala.a, "WILLPROCESSON", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
      }
      localTypeRecIntraTrnRsV1 = a(paramTypeTrnRqCm, paramTypeXferInfoV1Aggregate, locala, paramTypeRecurrInstAggregate, paramTypeUserData, paramEnumCurrencyEnum);
      localAuditAgent.saveRecXferRsV1(localTypeRecIntraTrnRsV1, locala.jdField_int, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.getMessage());
    }
    return localTypeRecIntraTrnRsV1;
  }
  
  private TypeRecIntraTrnRsV1 jdMethod_do(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    FFSDebug.log("RecXferProcessor.processXferRqV1Mod: call", 6);
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("RecXferProcessor.processRecXferRqV1Mod:Can not get DB Connection.");
    }
    TypeXferInfoV1Aggregate localTypeXferInfoV1Aggregate = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.IntraRq.XferInfo;
    EnumCurrencyEnum localEnumCurrencyEnum = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.IntraRq.CurDef;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecurrInst;
    try
    {
      Object localObject1;
      String str2;
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm.TrnUID))
      {
        if (this.q >= 3)
        {
          localObject1 = new a(null);
          a(localTypeXferInfoV1Aggregate, localTypeRecurrInstAggregate, paramTypeUserData, (a)localObject1, paramString, localEnumCurrencyEnum);
          String str1 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).e, ((a)localObject1).f);
          str2 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).c, ((a)localObject1).j);
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(908, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          RecXferProcessor2.logTransAuditLog(localFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4703, ((a)localObject1).jdField_goto, ((a)localObject1).jdField_for, ((a)localObject1).d, str1, str2, ((a)localObject1).jdField_new, ((a)localObject1).a, "DUPLICATE", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        localTypeRecIntraTrnRsV1 = a(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2019);
        localFFSConnectionHolder.conn.commit();
        localObject1 = localTypeRecIntraTrnRsV1;
        return localObject1;
      }
      localTypeRecIntraTrnRsV1 = processRecXferRqV1Mod(localFFSConnectionHolder, localTypeXferInfoV1Aggregate, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecSrvrTID, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.ModPending, localTypeRecurrInstAggregate, paramString, localEnumCurrencyEnum);
      if (localTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsV1Cm.Status.Code == 0)
      {
        FFSDebug.log("RecXferProcessor.processXferRqV1Mod: ModPending=" + paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.ModPending);
        if (paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.ModPending)
        {
          localObject1 = null;
          localObject1 = RecSrvrTIDToSrvrTID.getSrvrTIDs(localFFSConnectionHolder, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecSrvrTID);
          if (localObject1 != null) {
            for (int i = 0; i < localObject1.length; i++)
            {
              str2 = XferInstruction.getStatus(localFFSConnectionHolder, localObject1[i]);
              FFSDebug.log("RecXferProcessor.processXferRqV1Mod", ": srvrTIDs[i]: ", localObject1[i], 6);
              FFSDebug.log("RecXferProcessor.processXferRqV1Mod", ": status: ", str2, 6);
              if ((str2 != null) && (("WILLPROCESSON".equalsIgnoreCase(str2) == true) || ("APPROVAL_PENDING".equalsIgnoreCase(str2) == true) || ("APPROVAL_REJECTED".equals(str2) == true) || ("APPROVAL_NOT_ALLOWED".equals(str2) == true))) {
                this.s.processXferRqV1Canc(localFFSConnectionHolder, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, localObject1[i], paramString);
              }
            }
          }
          FFSDebug.log("RecXferProcessor.processXferRqV1Mod", ": Dleleted all pending transfer ", 6);
          localObject2 = this.s.processXferRqV1(localFFSConnectionHolder, localTypeXferInfoV1Aggregate, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecSrvrTID, paramString, localEnumCurrencyEnum);
          FFSDebug.log("RecXferProcessor.processXferRqV1Mod", ": added new transfer", 6);
          RecSrvrTIDToSrvrTID.create(localFFSConnectionHolder, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraModRq.RecSrvrTID, ((TypeIntraTrnRsV1)localObject2).IntraTrnRs.IntraTrnRsV1Un.IntraRs.SrvrTID);
        }
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject2 = "RecXferProcessor.processRecXferRqV1Mod failed: ";
      FFSDebug.log(localOFXException, (String)localObject2);
      localTypeRecIntraTrnRsV1 = jdMethod_do(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      localObject2 = "RecXferProcessor.processRecXferRqV1Mod failed: ";
      FFSDebug.log(localBPWException, (String)localObject2);
      if (localBPWException.getErrorCode() == -1) {
        localTypeRecIntraTrnRsV1 = a(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
      } else {
        localTypeRecIntraTrnRsV1 = jdMethod_do(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject2 = "RecXferProcessor.processRecXferRqV1Mod failed: ";
      FFSDebug.log(localException, (String)localObject2);
      localTypeRecIntraTrnRsV1 = a(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeRecIntraTrnRsV1;
  }
  
  public TypeRecIntraTrnRsV1 processRecXferRqV1Mod(FFSConnectionHolder paramFFSConnectionHolder, TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, TypeUserData paramTypeUserData, TypeTrnRqCm paramTypeTrnRqCm, String paramString1, boolean paramBoolean, TypeRecurrInstAggregate paramTypeRecurrInstAggregate, String paramString2, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws Exception, BPWException, OFXException
  {
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = null;
    a locala = new a(null);
    a(paramTypeXferInfoV1Aggregate, paramTypeRecurrInstAggregate, paramTypeUserData, locala, paramString2, paramEnumCurrencyEnum);
    locala.jdField_char = paramBoolean;
    locala.d = paramString1;
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("RecXferProcessor.processRecXferRqV1Mod:AuditAgent is null!!");
    }
    try
    {
      int i = a(paramFFSConnectionHolder, locala);
      if (i == 0)
      {
        locala.jdField_if = 0;
        locala.g = "WILLPROCESSON";
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeTrnRqCm, paramTypeXferInfoV1Aggregate, locala, paramTypeRecurrInstAggregate, paramTypeUserData, paramEnumCurrencyEnum);
      }
      else if (i == 1)
      {
        locala.jdField_if = 2018;
        locala.g = "FAILEDON";
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeTrnRqCm, paramTypeXferInfoV1Aggregate, locala, paramTypeRecurrInstAggregate, paramTypeUserData, paramEnumCurrencyEnum);
      }
      else if (i == 2)
      {
        locala.jdField_if = 2017;
        locala.g = "FAILEDON";
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeTrnRqCm, paramTypeXferInfoV1Aggregate, locala, paramTypeRecurrInstAggregate, paramTypeUserData, paramEnumCurrencyEnum);
      }
      else if (i == 3)
      {
        locala.jdField_if = 2016;
        locala.g = "FAILEDON";
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeTrnRqCm, paramTypeXferInfoV1Aggregate, locala, paramTypeRecurrInstAggregate, paramTypeUserData, paramEnumCurrencyEnum);
      }
      if (this.q >= 3)
      {
        localObject = BPWUtil.getAccountIDWithAccountType(locala.e, locala.f);
        String str = BPWUtil.getAccountIDWithAccountType(locala.c, locala.j);
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(907, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        RecXferProcessor2.logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4702, locala.jdField_goto, locala.jdField_for, locala.d, (String)localObject, str, locala.jdField_new, locala.a, "MODIFIED", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
      }
      if (i != 0) {
        return localTypeRecIntraTrnRsV1;
      }
      Object localObject = new RecXferInstruction("", locala.jdField_int, locala.l, locala.jdField_goto, locala.jdField_for, locala.jdField_new, locala.jdField_long, locala.e, locala.f, locala.c, locala.j, locala.jdField_else, locala.h, locala.jdField_null, locala.b, "", "WILLPROCESSON", paramString2, paramTypeUserData._submittedBy, locala.a, locala.jdField_do);
      RecXferInstruction.modify(paramFFSConnectionHolder, locala.d, (RecXferInstruction)localObject);
      jdMethod_if(paramTypeUserData._privateTagContainer, paramFFSConnectionHolder, locala.d, "Mod");
      localAuditAgent.modRecXferRsV1(localTypeRecIntraTrnRsV1, (RecXferInstruction)localObject, paramFFSConnectionHolder);
    }
    catch (OFXException localOFXException)
    {
      throw new OFXException(localOFXException.getExceptionMsg(), localOFXException.getErrorCode());
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.getMessage());
    }
    return localTypeRecIntraTrnRsV1;
  }
  
  private TypeRecIntraTrnRsV1 a(TypeRecIntraTrnRqV1 paramTypeRecIntraTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    FFSDebug.log("RecXferProcessor.processRecXferRqV1Canc: call", 6);
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("RecXferProcessor.processRecXferRqV1Canc:Can not get DB Connection.");
    }
    try
    {
      Object localObject1;
      Object localObject2;
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm.TrnUID))
      {
        if (this.q >= 3)
        {
          localObject1 = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.RecurrInst;
          TypeXferInfoV1Aggregate localTypeXferInfoV1Aggregate = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.IntraRq.XferInfo;
          localObject2 = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraRq.IntraRq.CurDef;
          a locala = new a(null);
          a(localTypeXferInfoV1Aggregate, (TypeRecurrInstAggregate)localObject1, paramTypeUserData, locala, paramString, (EnumCurrencyEnum)localObject2);
          String str2 = BPWUtil.getAccountIDWithAccountType(locala.e, locala.f);
          String str3 = BPWUtil.getAccountIDWithAccountType(locala.c, locala.j);
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(911, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          RecXferProcessor2.logTransAuditLog(localFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4705, locala.jdField_goto, locala.jdField_for, locala.d, str2, str3, locala.jdField_new, locala.a, "DUPLICATE", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        localTypeRecIntraTrnRsV1 = a(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2019);
        localFFSConnectionHolder.conn.commit();
        localObject1 = localTypeRecIntraTrnRsV1;
        return localObject1;
      }
      localTypeRecIntraTrnRsV1 = processRecXferRqV1Canc(localFFSConnectionHolder, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraCanRq.RecSrvrTID, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraCanRq.CanPending, paramString);
      if (localTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsV1Cm.Status.Code == 0)
      {
        FFSDebug.log("RecXferProcessor.processXferRqV1Mod: CanPending=" + paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraCanRq.CanPending);
        if (paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraCanRq.CanPending)
        {
          localObject1 = null;
          localObject1 = RecSrvrTIDToSrvrTID.getSrvrTIDs(localFFSConnectionHolder, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqV1Un.RecIntraCanRq.RecSrvrTID);
          if (localObject1 != null) {
            for (int i = 0; i < localObject1.length; i++)
            {
              localObject2 = XferInstruction.getStatus(localFFSConnectionHolder, localObject1[i]);
              if ((localObject2 != null) && (("WILLPROCESSON".equalsIgnoreCase((String)localObject2) == true) || ("APPROVAL_PENDING".equalsIgnoreCase((String)localObject2) == true) || ("APPROVAL_REJECTED".equals(localObject2) == true) || ("APPROVAL_NOT_ALLOWED".equals(localObject2) == true))) {
                this.s.processXferRqV1Canc(localFFSConnectionHolder, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, localObject1[i], paramString);
              }
            }
          }
        }
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "RecXferProcessor.processRecXferRqV1Canc failed: ";
      FFSDebug.log(localOFXException, str1);
      localTypeRecIntraTrnRsV1 = jdMethod_do(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "RecXferProcessor.processRecXferRqV1Canc failed: ";
      FFSDebug.log(localBPWException, str1);
      if (localBPWException.getErrorCode() == -1) {
        localTypeRecIntraTrnRsV1 = a(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
      } else {
        localTypeRecIntraTrnRsV1 = jdMethod_do(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str1 = "RecXferProcessor.processRecXferRqV1Canc failed: ";
      FFSDebug.log(localException, str1);
      localTypeRecIntraTrnRsV1 = a(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeRecIntraTrnRsV1;
  }
  
  public TypeRecIntraTrnRsV1 processRecXferRqV1Canc(FFSConnectionHolder paramFFSConnectionHolder, TypeUserData paramTypeUserData, TypeTrnRqCm paramTypeTrnRqCm, String paramString1, boolean paramBoolean, String paramString2)
    throws Exception
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = "0.0";
    String str6 = null;
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = null;
    a locala = new a(null);
    locala.d = paramString1;
    locala.i = paramBoolean;
    locala.g = "CANCELEDON";
    locala.l = DBUtil.getFIId(paramTypeUserData);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("RecXferProcessor.processRecXferRqV1Canc:AuditAgent is null!!");
    }
    try
    {
      int i = jdMethod_do(paramFFSConnectionHolder, locala);
      if (i == 0)
      {
        locala.jdField_if = 0;
        locala.g = "CANCELEDON";
        localTypeRecIntraTrnRsV1 = a(paramTypeTrnRqCm, locala);
        locala.jdField_if = 2018;
      }
      else if (i == 1)
      {
        locala.jdField_if = 2018;
        locala.g = "FAILEDON";
        localTypeRecIntraTrnRsV1 = a(paramTypeTrnRqCm, locala);
      }
      else if (i == 2)
      {
        locala.jdField_if = 2017;
        locala.g = "FAILEDON";
        localTypeRecIntraTrnRsV1 = a(paramTypeTrnRqCm, locala);
      }
      else if (i == 3)
      {
        locala.jdField_if = 2016;
        locala.g = "FAILEDON";
        localTypeRecIntraTrnRsV1 = a(paramTypeTrnRqCm, locala);
      }
      if (this.q >= 3)
      {
        RecXferInstruction localRecXferInstruction = RecXferInstruction.getRecXferInstruction(paramFFSConnectionHolder, paramString1);
        if (localRecXferInstruction != null)
        {
          str1 = localRecXferInstruction.BankID;
          str2 = localRecXferInstruction.fromBankID;
          str3 = BPWUtil.getAccountIDWithAccountType(localRecXferInstruction.AcctDebitID, localRecXferInstruction.AcctDebitType);
          str4 = BPWUtil.getAccountIDWithAccountType(localRecXferInstruction.AcctCreditID, localRecXferInstruction.AcctCreditType);
          str5 = localRecXferInstruction.Amount;
          str6 = localRecXferInstruction.CurDef;
          localRecXferInstruction = null;
        }
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(910, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        RecXferProcessor2.logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4704, str5, str6, locala.d, str3, str4, str1, str2, locala.g, localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
      }
      if (i != 0) {
        return localTypeRecIntraTrnRsV1;
      }
      RecXferInstruction.updateStatus(paramFFSConnectionHolder, locala.d, locala.g);
      localAuditAgent.cancelOFX151RecXferRsV1(locala.d, paramFFSConnectionHolder);
    }
    catch (OFXException localOFXException)
    {
      throw new OFXException(localOFXException.getExceptionMsg(), localOFXException.getErrorCode());
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.getMessage());
    }
    return localTypeRecIntraTrnRsV1;
  }
  
  private void a(TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, TypeRecurrInstAggregate paramTypeRecurrInstAggregate, TypeUserData paramTypeUserData, a parama, String paramString, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    parama.jdField_void = paramString;
    if (paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.__memberName.equals("BankAcctFrom"))
    {
      parama.a = paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.BankID;
      parama.jdField_do = paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.BranchID;
      parama.e = paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.AcctID;
      parama.f = MsgBuilder.getAcctType(paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.AcctType);
    }
    else
    {
      parama.e = paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.CCAcctFrom.AcctID;
      parama.f = "CREDITCARD";
    }
    if (paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.__memberName.equals("BankAcctTo"))
    {
      parama.jdField_new = paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.BankID;
      parama.jdField_long = paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.BranchID;
      parama.c = paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.AcctID;
      parama.j = MsgBuilder.getAcctType(paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.AcctType);
    }
    else
    {
      parama.c = paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.CCAcctTo.AcctID;
      parama.j = "CREDITCARD";
    }
    String str1 = "" + paramTypeXferInfoV1Aggregate.TrnAmt;
    try
    {
      if (!FFSUtil.isPositive(str1)) {
        throw new OFXException("Invalid amount", 2012);
      }
      parama.jdField_goto = str1;
    }
    catch (Exception localException)
    {
      throw new OFXException("Invalid amount", 2012);
    }
    parama.jdField_for = BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum);
    parama.jdField_int = paramTypeUserData._uid;
    parama.l = DBUtil.getFIId(paramTypeUserData);
    parama.jdField_byte = paramTypeXferInfoV1Aggregate.DtDueExists;
    int i = DBUtil.getCurrentStartDate();
    String str3;
    if (paramTypeXferInfoV1Aggregate.DtDueExists)
    {
      if (paramTypeXferInfoV1Aggregate.DtDue != null)
      {
        String str2 = paramTypeXferInfoV1Aggregate.DtDue.substring(0, 8);
        if (BPWUtil.validateDate(str2, "yyyyMMdd") == true)
        {
          parama.h = BPWUtil.getDateDBFormat(str2);
          if (parama.h < i)
          {
            str3 = BPWLocaleUtil.getMessage(100002, null, "OFX_MESSAGE");
            throw new BPWException(str3, 100002);
          }
        }
        else
        {
          str3 = BPWLocaleUtil.getMessage(100000, null, "OFX_MESSAGE");
          throw new BPWException(str3, 100000);
        }
      }
      else
      {
        str3 = BPWLocaleUtil.getMessage(100000, null, "OFX_MESSAGE");
        throw new BPWException(str3, 100000);
      }
    }
    else {
      parama.h = i;
    }
    parama.k = (Integer.toString(parama.h) + "0000");
    parama.b = paramTypeRecurrInstAggregate.NInsts;
    if ((parama.b == 1) || (parama.b < 0))
    {
      str3 = BPWLocaleUtil.getMessage(100001, null, "OFX_MESSAGE");
      throw new BPWException(str3, 100001);
    }
    parama.jdField_case = paramTypeRecurrInstAggregate.NInstsExists;
    parama.jdField_else = CommonProcessor.mapOFX151FreqToBPWFreq(paramTypeRecurrInstAggregate.Freq.value());
    parama.jdField_null = CommonProcessor.getEndDate(parama.h, parama.jdField_else, parama.b);
    parama.jdField_if = 0;
    parama.g = "WILLPROCESSON";
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, a parama)
    throws BPWException
  {
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = parama.l;
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = parama.jdField_else;
    localScheduleInfo.StartDate = parama.h;
    localScheduleInfo.InstanceCount = parama.b;
    localScheduleInfo.NextInstanceDate = parama.h;
    localScheduleInfo.LogID = parama.jdField_void;
    if (!parama.jdField_case) {
      localScheduleInfo.Perpetual = 1;
    }
    try
    {
      localScheduleInfo.CurInstanceNum = 1;
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "RECINTRATRN", parama.d, localScheduleInfo, "BookTransfer");
    }
    catch (Exception localException)
    {
      String str = "Exception in RecXferProcessor.scheduleRecXferAddRq: " + localException.getMessage();
      throw new BPWException(str);
    }
  }
  
  private int a(FFSConnectionHolder paramFFSConnectionHolder, a parama)
    throws Exception
  {
    ScheduleInfo localScheduleInfo1 = new ScheduleInfo();
    localScheduleInfo1.FIId = parama.l;
    try
    {
      ScheduleInfo localScheduleInfo2 = ScheduleInfo.getScheduleInfo(parama.l, "RECINTRATRN", parama.d, paramFFSConnectionHolder);
      if (localScheduleInfo2 == null) {
        return 1;
      }
      if ((localScheduleInfo2.Status.equals("Processing") == true) && (localScheduleInfo2.StatusOption == 1)) {
        return 1;
      }
      if (!localScheduleInfo2.Status.equals("Active"))
      {
        localObject = RecXferInstruction.getStatus(paramFFSConnectionHolder, parama.d);
        if (((String)localObject).compareTo("CANCELEDON") == 0) {
          return 2;
        }
        return 3;
      }
      localObject = InstructionTypeStatus.get(parama.l, "RECINTRATRN", paramFFSConnectionHolder);
      if ((localObject != null) && (((InstructionTypeStatus)localObject).SchedulerStatus.equals("DispatcherStarted")))
      {
        int i = DBUtil.getCurrentStartDate();
        if (localScheduleInfo2.WorkInstanceDate < i) {
          throw new OFXException("Record locked for modification by scheduler, try again later", 2000);
        }
      }
      localScheduleInfo1.InstructionID = parama.d;
      localScheduleInfo1.Status = "Active";
      localScheduleInfo1.Frequency = parama.jdField_else;
      localScheduleInfo1.InstanceCount = parama.b;
      localScheduleInfo1.CurInstanceNum = 1;
      if (parama.jdField_byte)
      {
        localScheduleInfo1.StartDate = parama.h;
        ScheduleInfo.computeNextInstanceDate(localScheduleInfo1);
      }
      else
      {
        localScheduleInfo1.StartDate = localScheduleInfo2.StartDate;
        localScheduleInfo1.NextInstanceDate = localScheduleInfo2.NextInstanceDate;
      }
      if (!parama.jdField_case) {
        localScheduleInfo1.Perpetual = 1;
      }
      ScheduleInfo.modifySchedule(paramFFSConnectionHolder, "RECINTRATRN", parama.d, localScheduleInfo1, "BookTransfer");
    }
    catch (OFXException localOFXException)
    {
      throw new OFXException(localOFXException.getExceptionMsg(), localOFXException.getErrorCode());
    }
    catch (Exception localException)
    {
      Object localObject = "Exception in RecXferProcessor.scheduleRecXferModRq: " + localException.getMessage();
      throw new BPWException((String)localObject);
    }
    return 0;
  }
  
  private int jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, a parama)
    throws Exception
  {
    try
    {
      ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(parama.l, "RECINTRATRN", parama.d, paramFFSConnectionHolder);
      if (localScheduleInfo == null) {
        return 1;
      }
      if ((localScheduleInfo.Status.equals("Processing") == true) && (localScheduleInfo.StatusOption == 1)) {
        return 1;
      }
      if (!localScheduleInfo.Status.equals("Active"))
      {
        localObject = RecXferInstruction.getStatus(paramFFSConnectionHolder, parama.d);
        if (((String)localObject).compareTo("CANCELEDON") == 0) {
          return 2;
        }
        return 3;
      }
      localObject = InstructionTypeStatus.get(parama.l, "RECINTRATRN", paramFFSConnectionHolder);
      if ((localObject != null) && (((InstructionTypeStatus)localObject).SchedulerStatus.equals("DispatcherStarted")))
      {
        int i = DBUtil.getCurrentStartDate();
        if (localScheduleInfo.WorkInstanceDate < i) {
          throw new OFXException("Record locked for modification by scheduler, try again later", 2000);
        }
      }
      if (ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECINTRATRN", parama.d) == 0) {
        throw new BPWException("Cannot cancel pmt in process!!");
      }
    }
    catch (OFXException localOFXException)
    {
      throw new OFXException(localOFXException.getExceptionMsg(), localOFXException.getErrorCode());
    }
    catch (Exception localException)
    {
      Object localObject = "Exception in RecXferProcessor.scheduleRecXferCanRq: " + localException.getMessage();
      throw new BPWException((String)localObject);
    }
    return 0;
  }
  
  private TypeRecIntraTrnRsV1 a(TypeTrnRqCm paramTypeTrnRqCm, TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, a parama, TypeRecurrInstAggregate paramTypeRecurrInstAggregate, TypeUserData paramTypeUserData, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException
  {
    TypeRecIntraTrnRsV1Aggregate localTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1UnExists = true;
    TypeRecIntraRsV1Un localTypeRecIntraRsV1Un = new TypeRecIntraRsV1Un();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un = localTypeRecIntraRsV1Un;
    localTypeRecIntraRsV1Un.__memberName = "RecIntraRs";
    TypeRecIntraRsV1Aggregate localTypeRecIntraRsV1Aggregate = new TypeRecIntraRsV1Aggregate();
    localTypeRecIntraRsV1Un.RecIntraRs = localTypeRecIntraRsV1Aggregate;
    TypeIntraRsV1Aggregate localTypeIntraRsV1Aggregate = new TypeIntraRsV1Aggregate();
    localTypeRecIntraRsV1Aggregate.IntraRs = localTypeIntraRsV1Aggregate;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = new TypeRecurrInstAggregate();
    localTypeRecIntraRsV1Aggregate.RecurrInst = localTypeRecurrInstAggregate;
    localTypeRecIntraRsV1Aggregate.RecSrvrTID = parama.d;
    localTypeRecurrInstAggregate.NInsts = parama.b;
    localTypeRecurrInstAggregate.NInstsExists = parama.jdField_case;
    localTypeRecurrInstAggregate.Freq = paramTypeRecurrInstAggregate.Freq;
    if (paramEnumCurrencyEnum != null) {
      localTypeIntraRsV1Aggregate.CurDef = paramEnumCurrencyEnum;
    } else {
      localTypeIntraRsV1Aggregate.CurDef = MsgBuilder.getOFX151CurrencyEnum(BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum));
    }
    localTypeIntraRsV1Aggregate.SrvrTID = parama.d;
    localTypeIntraRsV1Aggregate.RecSrvrTID = parama.d;
    if (parama.d != null) {
      localTypeIntraRsV1Aggregate.RecSrvrTIDExists = true;
    } else {
      localTypeIntraRsV1Aggregate.RecSrvrTIDExists = false;
    }
    localTypeIntraRsV1Aggregate.XferInfo = paramTypeXferInfoV1Aggregate;
    localTypeIntraRsV1Aggregate.XferPrcStsExists = true;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new TypeXferPrcStsAggregate();
    localTypeIntraRsV1Aggregate.XferPrcSts = localTypeXferPrcStsAggregate;
    RsCmBuilder.updateRsXferPrcSts(parama.g, parama.k, localTypeIntraRsV1Aggregate.XferPrcSts);
    RsCmBuilder.updateXferRsDateXferPrj(Integer.toString(parama.h) + "0000", localTypeIntraRsV1Aggregate);
    localTypeRecIntraTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, parama.jdField_if);
    return new TypeRecIntraTrnRsV1(localTypeRecIntraTrnRsV1Aggregate);
  }
  
  private TypeRecIntraTrnRsV1 jdMethod_if(TypeTrnRqCm paramTypeTrnRqCm, TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, a parama, TypeRecurrInstAggregate paramTypeRecurrInstAggregate, TypeUserData paramTypeUserData, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException
  {
    TypeRecIntraTrnRsV1Aggregate localTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1UnExists = true;
    TypeRecIntraRsV1Un localTypeRecIntraRsV1Un = new TypeRecIntraRsV1Un();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un = localTypeRecIntraRsV1Un;
    localTypeRecIntraRsV1Un.__memberName = "RecIntraModRs";
    TypeRecIntraModRsV1Aggregate localTypeRecIntraModRsV1Aggregate = new TypeRecIntraModRsV1Aggregate();
    localTypeRecIntraRsV1Un.RecIntraModRs = localTypeRecIntraModRsV1Aggregate;
    localTypeRecIntraModRsV1Aggregate.RecSrvrTID = parama.d;
    localTypeRecIntraModRsV1Aggregate.ModPending = parama.jdField_char;
    TypeIntraRsV1Aggregate localTypeIntraRsV1Aggregate = new TypeIntraRsV1Aggregate();
    localTypeRecIntraModRsV1Aggregate.IntraRs = localTypeIntraRsV1Aggregate;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = new TypeRecurrInstAggregate();
    localTypeRecIntraModRsV1Aggregate.RecurrInst = localTypeRecurrInstAggregate;
    localTypeRecurrInstAggregate.NInsts = parama.b;
    localTypeRecurrInstAggregate.NInstsExists = parama.jdField_case;
    localTypeRecurrInstAggregate.Freq = paramTypeRecurrInstAggregate.Freq;
    if (paramEnumCurrencyEnum != null) {
      localTypeIntraRsV1Aggregate.CurDef = paramEnumCurrencyEnum;
    } else {
      localTypeIntraRsV1Aggregate.CurDef = MsgBuilder.getOFX151CurrencyEnum(BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum));
    }
    localTypeIntraRsV1Aggregate.SrvrTID = parama.d;
    localTypeIntraRsV1Aggregate.RecSrvrTID = parama.d;
    if (parama.d != null) {
      localTypeIntraRsV1Aggregate.RecSrvrTIDExists = true;
    } else {
      localTypeIntraRsV1Aggregate.RecSrvrTIDExists = false;
    }
    localTypeIntraRsV1Aggregate.XferInfo = paramTypeXferInfoV1Aggregate;
    localTypeIntraRsV1Aggregate.XferPrcStsExists = true;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new TypeXferPrcStsAggregate();
    localTypeIntraRsV1Aggregate.XferPrcSts = localTypeXferPrcStsAggregate;
    RsCmBuilder.updateRsXferPrcSts(parama.g, parama.k, localTypeIntraRsV1Aggregate.XferPrcSts);
    RsCmBuilder.updateXferRsDateXferPrj(Integer.toString(parama.h) + "0000", localTypeIntraRsV1Aggregate);
    localTypeRecIntraTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, parama.jdField_if);
    return new TypeRecIntraTrnRsV1(localTypeRecIntraTrnRsV1Aggregate);
  }
  
  private TypeRecIntraTrnRsV1 a(TypeTrnRqCm paramTypeTrnRqCm, a parama)
  {
    TypeRecIntraTrnRsV1Aggregate localTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1UnExists = true;
    TypeRecIntraRsV1Un localTypeRecIntraRsV1Un = new TypeRecIntraRsV1Un();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1Un = localTypeRecIntraRsV1Un;
    localTypeRecIntraRsV1Un.__memberName = "RecIntraCanRs";
    TypeRecIntraCanRsV1Aggregate localTypeRecIntraCanRsV1Aggregate = new TypeRecIntraCanRsV1Aggregate();
    localTypeRecIntraRsV1Un.RecIntraCanRs = localTypeRecIntraCanRsV1Aggregate;
    localTypeRecIntraCanRsV1Aggregate.RecSrvrTID = parama.d;
    localTypeRecIntraCanRsV1Aggregate.CanPending = parama.i;
    localTypeRecIntraTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, parama.jdField_if);
    return new TypeRecIntraTrnRsV1(localTypeRecIntraTrnRsV1Aggregate);
  }
  
  private TypeRecIntraTrnRsV1 a(TypeTrnRqCm paramTypeTrnRqCm, int paramInt)
  {
    TypeRecIntraTrnRsV1Aggregate localTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1UnExists = false;
    localTypeRecIntraTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt);
    return new TypeRecIntraTrnRsV1(localTypeRecIntraTrnRsV1Aggregate);
  }
  
  private TypeRecIntraTrnRsV1 jdMethod_do(TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
  {
    TypeRecIntraTrnRsV1Aggregate localTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsV1UnExists = false;
    localTypeRecIntraTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt, paramString);
    return new TypeRecIntraTrnRsV1(localTypeRecIntraTrnRsV1Aggregate);
  }
  
  public void processApprovalResult(String paramString1, String paramString2, HashMap paramHashMap)
    throws FFSException
  {
    String str = "RecXferProcessor.processApprovalResult: ";
    FFSDebug.log(str, ": Starts ...", 6);
    RecXferProcessor2.processApprovalResult(paramString1, paramString2, paramHashMap);
    FFSDebug.log(str, ": done ...", 6);
  }
  
  private void jdMethod_if(Hashtable paramHashtable, FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    String str = null;
    try
    {
      FFSDebug.log("RecXferProcessor.processXtraInfo start: ", 6);
      if (paramString2.equals("Add"))
      {
        if ((paramHashtable != null) && (!paramHashtable.isEmpty())) {
          BPWRecXferExtraInfo.insertHashtable(paramString1, paramHashtable, paramFFSConnectionHolder);
        }
      }
      else if (paramString2.equals("Mod")) {
        if ((paramHashtable != null) && (!paramHashtable.isEmpty())) {
          BPWRecXferExtraInfo.updateHashtable(paramString1, paramHashtable, paramFFSConnectionHolder);
        } else {
          BPWRecXferExtraInfo.deleteAllWithRecSrvrTID(paramString1, paramFFSConnectionHolder);
        }
      }
    }
    catch (Exception localException)
    {
      str = "RecXferProcessor.processXtraInfo : failed ";
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new BPWException(str + localException.toString());
    }
    FFSDebug.log("RecXferProcessor.processXtraInfo end: ", 6);
  }
  
  private class a
  {
    public int b;
    public boolean jdField_case;
    public int jdField_else;
    public String jdField_new;
    public String a;
    public String jdField_long;
    public String jdField_do;
    public String e;
    public String f;
    public String c;
    public String j;
    public String jdField_goto;
    public String jdField_for;
    public String jdField_int;
    public String l;
    public int h;
    public int jdField_null;
    public boolean jdField_byte;
    public String d;
    public boolean jdField_char;
    public boolean i;
    public String jdField_void;
    public String k;
    public String g;
    public int jdField_try;
    public int jdField_if;
    
    private a() {}
    
    a(RecXferProcessor.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.RecXferProcessor
 * JD-Core Version:    0.7.0.1
 */