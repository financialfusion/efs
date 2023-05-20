package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.BPWRecXferExtraInfo;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.RecSrvrTIDToSrvrTID;
import com.ffusion.ffs.bpw.db.RecXferInstruction;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.db.Trans;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.BankingDays;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecIntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.TransferCalloutHandlerBase;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.TransferIntraMap;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
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
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumFreqEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctToUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctToAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctToAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraCanRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeRecIntraCanRsAggregate;
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
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStatusAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;

public class RecXferProcessor2
  implements FFSConst, ScheduleConstants, DBConsts, OFXConsts, BPWResource
{
  private XferProcessor2 R;
  private PropertyConfig Q;
  private int P = 1;
  private boolean O = false;
  
  public RecXferProcessor2(XferProcessor2 paramXferProcessor2)
  {
    this.R = paramXferProcessor2;
    this.Q = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
    this.P = this.Q.LogLevel;
    this.O = this.Q.getEnforceEnrollment();
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
      if ((this.O) && (!Customer.validCustomer(paramTypeUserData._uid)))
      {
        String str1 = "This customer, customerID=" + paramTypeUserData._uid + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
        FFSDebug.log(str1, 0);
        throw new BPWException(str1);
      }
    }
    catch (Exception localException1)
    {
      str2 = "*** RecXferProcessor2.processRecIntraTrnRqV1 failed:";
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
      String str3 = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.__memberName;
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
      localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
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
    TypeXferInfoAggregate localTypeXferInfoAggregate = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.IntraRq.XferInfo;
    EnumCurrencyEnum localEnumCurrencyEnum = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.IntraRq.CurDef;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.RecurrInst;
    com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1[] arrayOfTypeIntraTrnRsV1 = null;
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    try
    {
      Object localObject2;
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm.TrnUID))
      {
        if (this.P >= 3)
        {
          localObject1 = new a(null);
          a(localTypeXferInfoAggregate, localTypeRecurrInstAggregate, paramTypeUserData, (a)localObject1, paramString, localEnumCurrencyEnum);
          String str1 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).e, ((a)localObject1).f);
          localObject2 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).c, ((a)localObject1).j);
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(906, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          logTransAuditLog(localFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4701, ((a)localObject1).jdField_goto, ((a)localObject1).jdField_for, ((a)localObject1).d, str1, (String)localObject2, ((a)localObject1).jdField_new, ((a)localObject1).a, "DUPLICATE", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2019);
        localFFSConnectionHolder.conn.commit();
        localObject1 = localTypeRecIntraTrnRsV1;
        return localObject1;
      }
      localTypeRecIntraTrnRsV1 = processRecXferRqV1(localFFSConnectionHolder, localTypeXferInfoAggregate, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, localTypeRecurrInstAggregate, arrayOfTypeIntraTrnRsV1, paramString, localEnumCurrencyEnum);
      Object localObject1 = TransferIntraMap.mapRecIntraRqToRecTransferInfo(paramTypeRecIntraTrnRqV1, paramTypeUserData);
      try
      {
        localTransferCalloutHandlerBase.begin((TransferInfo)localObject1, "AddIntraRecTransferOFX200");
      }
      catch (FFSException localFFSException)
      {
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
        localObject2 = localTypeRecIntraTrnRsV1;
        return localObject2;
      }
      try
      {
        if (localTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsCm.Status.Code == 0)
        {
          com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = this.R.processXferRqV1(localFFSConnectionHolder, localTypeXferInfoAggregate, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecSrvrTID, paramString, localEnumCurrencyEnum);
          RecSrvrTIDToSrvrTID.create(localFFSConnectionHolder, localTypeRecIntraTrnRsV1.RecIntraTrnRs.RecIntraRsUn.RecIntraRs.RecSrvrTID, localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.SrvrTID);
          arrayOfTypeIntraTrnRsV1 = new com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1[1];
          arrayOfTypeIntraTrnRsV1[0] = localTypeIntraTrnRsV1;
        }
        localFFSConnectionHolder.conn.commit();
        try
        {
          localTransferCalloutHandlerBase.end((TransferInfo)localObject1, "AddIntraRecTransferOFX200");
        }
        catch (Throwable localThrowable1) {}
      }
      catch (Exception localException2)
      {
        try
        {
          localTransferCalloutHandlerBase.failure((TransferInfo)localObject1, "AddIntraRecTransferOFX200");
        }
        catch (Throwable localThrowable2) {}
        throw localException2;
      }
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "RecXferProcessor.processRecXferRqV1 failed: ";
      FFSDebug.log(localOFXException, str2);
      localTypeRecIntraTrnRsV1 = jdMethod_for(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "RecXferProcessor.processRecXferRqV1 failed: ";
      FFSDebug.log(localBPWException, str2);
      if (localBPWException.getErrorCode() == -1) {
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
      } else {
        localTypeRecIntraTrnRsV1 = jdMethod_for(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "RecXferProcessor.processRecXferRqV1 failed: ";
      FFSDebug.log(localException1, str2);
      localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeRecIntraTrnRsV1;
  }
  
  public TypeRecIntraTrnRsV1 processRecXferRqV1(FFSConnectionHolder paramFFSConnectionHolder, TypeXferInfoAggregate paramTypeXferInfoAggregate, TypeUserData paramTypeUserData, TypeTrnRqCm paramTypeTrnRqCm, TypeRecurrInstAggregate paramTypeRecurrInstAggregate, com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1[] paramArrayOfTypeIntraTrnRsV1, String paramString, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = null;
    a locala = new a(null);
    a(paramTypeXferInfoAggregate, paramTypeRecurrInstAggregate, paramTypeUserData, locala, paramString, paramEnumCurrencyEnum);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("RecXferProcessor.processXferRqV1:AuditAgent is null!!");
    }
    try
    {
      RecXferInstruction localRecXferInstruction = new RecXferInstruction("", locala.jdField_int, locala.l, locala.jdField_goto, locala.jdField_for, locala.jdField_new, locala.jdField_long, locala.e, locala.f, locala.c, locala.j, locala.jdField_else, locala.h, locala.jdField_null, locala.b, "", "WILLPROCESSON", paramString, paramTypeUserData._submittedBy, locala.a, locala.jdField_do);
      locala.d = RecXferInstruction.create(paramFFSConnectionHolder, localRecXferInstruction);
      jdMethod_do(paramTypeUserData._privateTagContainer, paramFFSConnectionHolder, locala.d, "Add");
      jdMethod_if(paramFFSConnectionHolder, locala);
      if (this.P >= 3)
      {
        String str1 = BPWUtil.getAccountIDWithAccountType(locala.e, locala.f);
        String str2 = BPWUtil.getAccountIDWithAccountType(locala.c, locala.j);
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(905, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4700, locala.jdField_goto, locala.jdField_for, locala.d, str1, str2, locala.jdField_new, locala.a, "WILLPROCESSON", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
      }
      localTypeRecIntraTrnRsV1 = a(paramTypeTrnRqCm, paramTypeXferInfoAggregate, locala, paramTypeRecurrInstAggregate, paramTypeUserData, paramEnumCurrencyEnum);
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
    TypeXferInfoAggregate localTypeXferInfoAggregate = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.IntraRq.XferInfo;
    EnumCurrencyEnum localEnumCurrencyEnum = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.IntraRq.CurDef;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecurrInst;
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    try
    {
      Object localObject2;
      Object localObject3;
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm.TrnUID))
      {
        if (this.P >= 3)
        {
          localObject1 = new a(null);
          a(localTypeXferInfoAggregate, localTypeRecurrInstAggregate, paramTypeUserData, (a)localObject1, paramString, localEnumCurrencyEnum);
          String str1 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).e, ((a)localObject1).f);
          localObject2 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).c, ((a)localObject1).j);
          localObject3 = BPWLocaleUtil.getLocalizedMessage(908, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          logTransAuditLog(localFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4703, ((a)localObject1).jdField_goto, ((a)localObject1).jdField_for, ((a)localObject1).d, str1, (String)localObject2, ((a)localObject1).jdField_new, ((a)localObject1).a, "DUPLICATE", (ILocalizable)localObject3, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2019);
        localFFSConnectionHolder.conn.commit();
        localObject1 = localTypeRecIntraTrnRsV1;
        return localObject1;
      }
      Object localObject1 = TransferIntraMap.mapRecIntraRqToRecTransferInfo(paramTypeRecIntraTrnRqV1, paramTypeUserData);
      try
      {
        localTransferCalloutHandlerBase.begin((TransferInfo)localObject1, "ModifyIntraRecTransferOFX200");
      }
      catch (FFSException localFFSException)
      {
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
        localObject2 = localTypeRecIntraTrnRsV1;
        return localObject2;
      }
      try
      {
        localTypeRecIntraTrnRsV1 = processRecXferRqV1Mod(localFFSConnectionHolder, localTypeXferInfoAggregate, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecSrvrTID, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.ModPending, localTypeRecurrInstAggregate, paramString, localEnumCurrencyEnum);
        if (localTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsCm.Status.Code == 0)
        {
          FFSDebug.log("RecXferProcessor.processXferRqV1Mod: ModPending=" + paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.ModPending);
          if (paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.ModPending)
          {
            String[] arrayOfString = null;
            arrayOfString = RecSrvrTIDToSrvrTID.getSrvrTIDs(localFFSConnectionHolder, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecSrvrTID);
            FFSDebug.log("RecXferProcessor2.processXferRqV1Mod", ": srvrTIDs: " + arrayOfString, 6);
            if (arrayOfString != null)
            {
              FFSDebug.log("RecXferProcessor2.processXferRqV1Mod", ": srvrTIDs #: " + arrayOfString.length, 6);
              for (int i = 0; i < arrayOfString.length; i++)
              {
                localObject3 = XferInstruction.getStatus(localFFSConnectionHolder, arrayOfString[i]);
                FFSDebug.log("RecXferProcessor2.processXferRqV1Mod", ": srvrTIDs[i]: ", arrayOfString[i], 6);
                FFSDebug.log("RecXferProcessor2.processXferRqV1Mod", ": status: ", (String)localObject3, 6);
                if ((localObject3 != null) && (("WILLPROCESSON".equalsIgnoreCase((String)localObject3) == true) || ("APPROVAL_PENDING".equalsIgnoreCase((String)localObject3) == true) || ("APPROVAL_REJECTED".equals(localObject3) == true) || ("APPROVAL_NOT_ALLOWED".equals(localObject3) == true))) {
                  this.R.processXferRqV1Canc(localFFSConnectionHolder, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, arrayOfString[i], paramString, true);
                }
              }
            }
            FFSDebug.log("RecXferProcessor2.processXferRqV1Mod", ": Deleted all pending transfer ", 6);
            com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = this.R.processXferRqV1(localFFSConnectionHolder, localTypeXferInfoAggregate, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecSrvrTID, paramString, localEnumCurrencyEnum);
            FFSDebug.log("RecXferProcessor2.processXferRqV1Mod", ": added new transfer", 6);
            RecSrvrTIDToSrvrTID.create(localFFSConnectionHolder, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraModRq.RecSrvrTID, localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.SrvrTID);
          }
        }
        localFFSConnectionHolder.conn.commit();
        try
        {
          localTransferCalloutHandlerBase.end((TransferInfo)localObject1, "ModifyIntraRecTransferOFX200");
        }
        catch (Throwable localThrowable1) {}
      }
      catch (Exception localException2)
      {
        try
        {
          localTransferCalloutHandlerBase.failure((TransferInfo)localObject1, "ModifyIntraRecTransferOFX200");
        }
        catch (Throwable localThrowable2) {}
        throw localException2;
      }
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "RecXferProcessor.processRecXferRqV1Mod failed: ";
      FFSDebug.log(localOFXException, str2);
      localTypeRecIntraTrnRsV1 = jdMethod_for(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "RecXferProcessor.processRecXferRqV1Mod failed: ";
      FFSDebug.log(localBPWException, str2);
      if (localBPWException.getErrorCode() == -1) {
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
      } else {
        localTypeRecIntraTrnRsV1 = jdMethod_for(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "RecXferProcessor.processRecXferRqV1Mod failed: ";
      FFSDebug.log(localException1, str2);
      localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeRecIntraTrnRsV1;
  }
  
  public TypeRecIntraTrnRsV1 processRecXferRqV1Mod(FFSConnectionHolder paramFFSConnectionHolder, TypeXferInfoAggregate paramTypeXferInfoAggregate, TypeUserData paramTypeUserData, TypeTrnRqCm paramTypeTrnRqCm, String paramString1, boolean paramBoolean, TypeRecurrInstAggregate paramTypeRecurrInstAggregate, String paramString2, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws Exception, BPWException, OFXException
  {
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = null;
    a locala = new a(null);
    a(paramTypeXferInfoAggregate, paramTypeRecurrInstAggregate, paramTypeUserData, locala, paramString2, paramEnumCurrencyEnum);
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
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeTrnRqCm, paramTypeXferInfoAggregate, locala, paramTypeRecurrInstAggregate, paramTypeUserData, paramEnumCurrencyEnum);
      }
      else if (i == 1)
      {
        locala.jdField_if = 2018;
        locala.g = "FAILEDON";
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeTrnRqCm, paramTypeXferInfoAggregate, locala, paramTypeRecurrInstAggregate, paramTypeUserData, paramEnumCurrencyEnum);
      }
      else if (i == 2)
      {
        locala.jdField_if = 2017;
        locala.g = "FAILEDON";
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeTrnRqCm, paramTypeXferInfoAggregate, locala, paramTypeRecurrInstAggregate, paramTypeUserData, paramEnumCurrencyEnum);
      }
      else if (i == 3)
      {
        locala.jdField_if = 2016;
        locala.g = "FAILEDON";
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeTrnRqCm, paramTypeXferInfoAggregate, locala, paramTypeRecurrInstAggregate, paramTypeUserData, paramEnumCurrencyEnum);
      }
      if (this.P >= 3)
      {
        localObject = BPWUtil.getAccountIDWithAccountType(locala.e, locala.f);
        String str = BPWUtil.getAccountIDWithAccountType(locala.c, locala.j);
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(907, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4702, locala.jdField_goto, locala.jdField_for, locala.d, (String)localObject, str, locala.jdField_new, locala.a, "MODIFIED", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
      }
      if (i != 0) {
        return localTypeRecIntraTrnRsV1;
      }
      Object localObject = new RecXferInstruction("", locala.jdField_int, locala.l, locala.jdField_goto, locala.jdField_for, locala.jdField_new, locala.jdField_long, locala.e, locala.f, locala.c, locala.j, locala.jdField_else, locala.h, locala.jdField_null, locala.b, "", "WILLPROCESSON", paramString2, paramTypeUserData._submittedBy, locala.a, locala.jdField_do);
      RecXferInstruction.modify(paramFFSConnectionHolder, locala.d, (RecXferInstruction)localObject);
      jdMethod_do(paramTypeUserData._privateTagContainer, paramFFSConnectionHolder, locala.d, "Mod");
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
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    try
    {
      Object localObject2;
      Object localObject3;
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm.TrnUID))
      {
        if (this.P >= 3)
        {
          localObject1 = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.RecurrInst;
          TypeXferInfoAggregate localTypeXferInfoAggregate = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.IntraRq.XferInfo;
          localObject2 = paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraRq.IntraRq.CurDef;
          localObject3 = new a(null);
          a(localTypeXferInfoAggregate, (TypeRecurrInstAggregate)localObject1, paramTypeUserData, (a)localObject3, paramString, (EnumCurrencyEnum)localObject2);
          String str2 = BPWUtil.getAccountIDWithAccountType(((a)localObject3).e, ((a)localObject3).f);
          String str3 = BPWUtil.getAccountIDWithAccountType(((a)localObject3).c, ((a)localObject3).j);
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(911, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          logTransAuditLog(localFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4705, ((a)localObject3).jdField_goto, ((a)localObject3).jdField_for, ((a)localObject3).d, str2, str3, ((a)localObject3).jdField_new, ((a)localObject3).a, "DUPLICATE", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2019);
        localFFSConnectionHolder.conn.commit();
        localObject1 = localTypeRecIntraTrnRsV1;
        return localObject1;
      }
      Object localObject1 = TransferIntraMap.mapRecIntraRqToRecTransferInfo(paramTypeRecIntraTrnRqV1, paramTypeUserData);
      try
      {
        localTransferCalloutHandlerBase.begin((TransferInfo)localObject1, "CancelIntraRecTransferOFX200");
      }
      catch (FFSException localFFSException)
      {
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
        localObject2 = localTypeRecIntraTrnRsV1;
        return localObject2;
      }
      try
      {
        localTypeRecIntraTrnRsV1 = processRecXferRqV1Canc(localFFSConnectionHolder, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraCanRq.RecSrvrTID, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraCanRq.CanPending, paramString);
        if (localTypeRecIntraTrnRsV1.RecIntraTrnRs.TrnRsCm.Status.Code == 0)
        {
          FFSDebug.log("RecXferProcessor.processXferRqV1Mod: CanPending=" + paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraCanRq.CanPending);
          if (paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraCanRq.CanPending)
          {
            String[] arrayOfString = null;
            arrayOfString = RecSrvrTIDToSrvrTID.getSrvrTIDs(localFFSConnectionHolder, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.RecIntraTrnRqUn.RecIntraCanRq.RecSrvrTID);
            FFSDebug.log("RecXferProcessor2.processRecXferRqV1Canc", ": srvrTIDs: " + arrayOfString, 6);
            if (arrayOfString != null)
            {
              FFSDebug.log("RecXferProcessor2.processRecXferRqV1Canc", ": srvrTIDs #: " + arrayOfString.length, 6);
              for (int i = 0; i < arrayOfString.length; i++)
              {
                localObject3 = XferInstruction.getStatus(localFFSConnectionHolder, arrayOfString[i]);
                FFSDebug.log("RecXferProcessor2.processRecXferRqV1Canc", ": srvrTIDs[i]: ", arrayOfString[i], 6);
                FFSDebug.log("RecXferProcessor2.processRecXferRqV1Canc", ": status: ", (String)localObject3, 6);
                if ((localObject3 != null) && (("WILLPROCESSON".equalsIgnoreCase((String)localObject3) == true) || ("APPROVAL_PENDING".equalsIgnoreCase((String)localObject3) == true) || ("APPROVAL_REJECTED".equals(localObject3) == true) || ("APPROVAL_NOT_ALLOWED".equals(localObject3) == true))) {
                  this.R.processXferRqV1Canc(localFFSConnectionHolder, paramTypeUserData, paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, arrayOfString[i], paramString, true);
                }
              }
            }
          }
        }
        localFFSConnectionHolder.conn.commit();
        try
        {
          localTransferCalloutHandlerBase.end((TransferInfo)localObject1, "CancelIntraRecTransferOFX200");
        }
        catch (Throwable localThrowable1) {}
      }
      catch (Exception localException2)
      {
        try
        {
          localTransferCalloutHandlerBase.failure((TransferInfo)localObject1, "CancelIntraRecTransferOFX200");
        }
        catch (Throwable localThrowable2) {}
        throw localException2;
      }
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "RecXferProcessor.processRecXferRqV1Canc failed: ";
      FFSDebug.log(localOFXException, str1);
      localTypeRecIntraTrnRsV1 = jdMethod_for(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "RecXferProcessor.processRecXferRqV1Canc failed: ";
      FFSDebug.log(localBPWException, str1);
      if (localBPWException.getErrorCode() == -1) {
        localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
      } else {
        localTypeRecIntraTrnRsV1 = jdMethod_for(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder.conn.rollback();
      String str1 = "RecXferProcessor.processRecXferRqV1Canc failed: ";
      FFSDebug.log(localException1, str1);
      localTypeRecIntraTrnRsV1 = jdMethod_if(paramTypeRecIntraTrnRqV1.RecIntraTrnRq.TrnRqCm, 2000);
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
    TypeRecIntraTrnRsV1 localTypeRecIntraTrnRsV1 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    BigDecimal localBigDecimal = null;
    String str5 = null;
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
      if (this.P >= 3)
      {
        RecXferInstruction localRecXferInstruction = RecXferInstruction.getRecXferInstruction(paramFFSConnectionHolder, paramString1);
        if (localRecXferInstruction != null)
        {
          str1 = localRecXferInstruction.BankID;
          str2 = localRecXferInstruction.fromBankID;
          str3 = BPWUtil.getAccountIDWithAccountType(localRecXferInstruction.AcctDebitID, localRecXferInstruction.AcctDebitType);
          str4 = BPWUtil.getAccountIDWithAccountType(localRecXferInstruction.AcctCreditID, localRecXferInstruction.AcctCreditType);
          localBigDecimal = FFSUtil.getBigDecimal(localRecXferInstruction.Amount);
          str5 = localRecXferInstruction.CurDef;
          localRecXferInstruction = null;
        }
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(910, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        a(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4704, localBigDecimal, str5, paramString1, str3, str4, str1, str2, locala.g, localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
      }
      if (i != 0) {
        return localTypeRecIntraTrnRsV1;
      }
      RecXferInstruction.updateStatus(paramFFSConnectionHolder, locala.d, locala.g);
      localAuditAgent.cancelOFX200RecXferRsV1(locala.d, paramFFSConnectionHolder);
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
  
  private void a(TypeXferInfoAggregate paramTypeXferInfoAggregate, TypeRecurrInstAggregate paramTypeRecurrInstAggregate, TypeUserData paramTypeUserData, a parama, String paramString, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    parama.jdField_void = paramString;
    if (paramTypeXferInfoAggregate.BCCAcctFromUn.__memberName.equals("BankAcctFrom"))
    {
      parama.a = paramTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.BankID;
      parama.jdField_do = paramTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.BranchID;
      parama.e = paramTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.AcctID;
      parama.f = MsgBuilder.getAcctType(paramTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.AcctType);
    }
    else
    {
      parama.e = paramTypeXferInfoAggregate.BCCAcctFromUn.CCAcctFrom.AcctID;
      parama.f = "CREDITCARD";
    }
    if (paramTypeXferInfoAggregate.BCCAcctToUn.__memberName.equals("BankAcctTo"))
    {
      parama.jdField_new = paramTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.BankID;
      parama.jdField_long = paramTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.BranchID;
      parama.c = paramTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.AcctID;
      parama.j = MsgBuilder.getAcctType(paramTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.AcctType);
    }
    else
    {
      parama.c = paramTypeXferInfoAggregate.BCCAcctToUn.CCAcctTo.AcctID;
      parama.j = "CREDITCARD";
    }
    String str1 = "" + paramTypeXferInfoAggregate.TrnAmt;
    try
    {
      if (FFSUtil.isPositive(str1)) {
        parama.jdField_goto = str1;
      } else {
        throw new OFXException("Invalid amount", 2012);
      }
    }
    catch (Exception localException)
    {
      throw new OFXException("Invalid amount", 2012);
    }
    parama.jdField_for = BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum);
    parama.jdField_int = paramTypeUserData._uid;
    parama.l = DBUtil.getFIId(paramTypeUserData);
    parama.jdField_byte = paramTypeXferInfoAggregate.DtDueExists;
    int i = DBUtil.getCurrentStartDate();
    String str3;
    if (paramTypeXferInfoAggregate.DtDueExists)
    {
      if (paramTypeXferInfoAggregate.DtDue != null)
      {
        String str2 = paramTypeXferInfoAggregate.DtDue.substring(0, 8);
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
    parama.jdField_else = CommonProcessor.mapOFX200FreqToBPWFreq(paramTypeRecurrInstAggregate.Freq.value());
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
  
  private TypeRecIntraTrnRsV1 a(TypeTrnRqCm paramTypeTrnRqCm, TypeXferInfoAggregate paramTypeXferInfoAggregate, a parama, TypeRecurrInstAggregate paramTypeRecurrInstAggregate, TypeUserData paramTypeUserData, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException
  {
    TypeRecIntraTrnRsV1Aggregate localTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsUnExists = true;
    TypeRecIntraRsUn localTypeRecIntraRsUn = new TypeRecIntraRsUn();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn = localTypeRecIntraRsUn;
    localTypeRecIntraRsUn.__memberName = "RecIntraRs";
    TypeRecIntraRsAggregate localTypeRecIntraRsAggregate = new TypeRecIntraRsAggregate();
    localTypeRecIntraRsUn.RecIntraRs = localTypeRecIntraRsAggregate;
    TypeIntraRsAggregate localTypeIntraRsAggregate = new TypeIntraRsAggregate();
    localTypeRecIntraRsAggregate.IntraRs = localTypeIntraRsAggregate;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = new TypeRecurrInstAggregate();
    localTypeRecIntraRsAggregate.RecurrInst = localTypeRecurrInstAggregate;
    localTypeRecIntraRsAggregate.RecSrvrTID = parama.d;
    localTypeRecurrInstAggregate.NInsts = parama.b;
    localTypeRecurrInstAggregate.NInstsExists = parama.jdField_case;
    localTypeRecurrInstAggregate.Freq = paramTypeRecurrInstAggregate.Freq;
    if (paramEnumCurrencyEnum != null) {
      localTypeIntraRsAggregate.CurDef = paramEnumCurrencyEnum;
    } else {
      localTypeIntraRsAggregate.CurDef = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum));
    }
    localTypeIntraRsAggregate.SrvrTID = parama.d;
    localTypeIntraRsAggregate.RecSrvrTID = parama.d;
    if (parama.d != null) {
      localTypeIntraRsAggregate.RecSrvrTIDExists = true;
    } else {
      localTypeIntraRsAggregate.RecSrvrTIDExists = false;
    }
    localTypeIntraRsAggregate.XferInfo = paramTypeXferInfoAggregate;
    localTypeIntraRsAggregate.XferPrcStsExists = true;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new TypeXferPrcStsAggregate();
    localTypeIntraRsAggregate.XferPrcSts = localTypeXferPrcStsAggregate;
    RsCmBuilder.updateRsXferPrcSts(parama.g, parama.k, localTypeIntraRsAggregate.XferPrcSts);
    RsCmBuilder.updateXferRsDateXferPrj(Integer.toString(parama.h) + "0000", localTypeIntraRsAggregate);
    localTypeRecIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, parama.jdField_if);
    return new TypeRecIntraTrnRsV1(localTypeRecIntraTrnRsV1Aggregate);
  }
  
  private TypeRecIntraTrnRsV1 jdMethod_if(TypeTrnRqCm paramTypeTrnRqCm, TypeXferInfoAggregate paramTypeXferInfoAggregate, a parama, TypeRecurrInstAggregate paramTypeRecurrInstAggregate, TypeUserData paramTypeUserData, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException
  {
    TypeRecIntraTrnRsV1Aggregate localTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsUnExists = true;
    TypeRecIntraRsUn localTypeRecIntraRsUn = new TypeRecIntraRsUn();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn = localTypeRecIntraRsUn;
    localTypeRecIntraRsUn.__memberName = "RecIntraModRs";
    TypeRecIntraModRsAggregate localTypeRecIntraModRsAggregate = new TypeRecIntraModRsAggregate();
    localTypeRecIntraRsUn.RecIntraModRs = localTypeRecIntraModRsAggregate;
    localTypeRecIntraModRsAggregate.RecSrvrTID = parama.d;
    localTypeRecIntraModRsAggregate.ModPending = parama.jdField_char;
    TypeIntraRsAggregate localTypeIntraRsAggregate = new TypeIntraRsAggregate();
    localTypeRecIntraModRsAggregate.IntraRs = localTypeIntraRsAggregate;
    TypeRecurrInstAggregate localTypeRecurrInstAggregate = new TypeRecurrInstAggregate();
    localTypeRecIntraModRsAggregate.RecurrInst = localTypeRecurrInstAggregate;
    localTypeRecurrInstAggregate.NInsts = parama.b;
    localTypeRecurrInstAggregate.NInstsExists = parama.jdField_case;
    localTypeRecurrInstAggregate.Freq = paramTypeRecurrInstAggregate.Freq;
    if (paramEnumCurrencyEnum != null) {
      localTypeIntraRsAggregate.CurDef = paramEnumCurrencyEnum;
    } else {
      localTypeIntraRsAggregate.CurDef = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum));
    }
    localTypeIntraRsAggregate.SrvrTID = parama.d;
    localTypeIntraRsAggregate.RecSrvrTID = parama.d;
    if (parama.d != null) {
      localTypeIntraRsAggregate.RecSrvrTIDExists = true;
    } else {
      localTypeIntraRsAggregate.RecSrvrTIDExists = false;
    }
    localTypeIntraRsAggregate.XferInfo = paramTypeXferInfoAggregate;
    localTypeIntraRsAggregate.XferPrcStsExists = true;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new TypeXferPrcStsAggregate();
    localTypeIntraRsAggregate.XferPrcSts = localTypeXferPrcStsAggregate;
    RsCmBuilder.updateRsXferPrcSts(parama.g, parama.k, localTypeIntraRsAggregate.XferPrcSts);
    RsCmBuilder.updateXferRsDateXferPrj(Integer.toString(parama.h) + "0000", localTypeIntraRsAggregate);
    localTypeRecIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, parama.jdField_if);
    return new TypeRecIntraTrnRsV1(localTypeRecIntraTrnRsV1Aggregate);
  }
  
  private TypeRecIntraTrnRsV1 a(TypeTrnRqCm paramTypeTrnRqCm, a parama)
  {
    TypeRecIntraTrnRsV1Aggregate localTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsUnExists = true;
    TypeRecIntraRsUn localTypeRecIntraRsUn = new TypeRecIntraRsUn();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsUn = localTypeRecIntraRsUn;
    localTypeRecIntraRsUn.__memberName = "RecIntraCanRs";
    TypeRecIntraCanRsAggregate localTypeRecIntraCanRsAggregate = new TypeRecIntraCanRsAggregate();
    localTypeRecIntraRsUn.RecIntraCanRs = localTypeRecIntraCanRsAggregate;
    localTypeRecIntraCanRsAggregate.RecSrvrTID = parama.d;
    localTypeRecIntraCanRsAggregate.CanPending = parama.i;
    localTypeRecIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, parama.jdField_if);
    return new TypeRecIntraTrnRsV1(localTypeRecIntraTrnRsV1Aggregate);
  }
  
  public RecIntraTrnInfo getRecIntraById(String paramString)
    throws BPWException
  {
    FFSDebug.log("RecXferProcessor2.getRecIntraById : start", 6);
    RecIntraTrnInfo localRecIntraTrnInfo1 = null;
    RecXferInstruction localRecXferInstruction = null;
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      localRecIntraTrnInfo1 = new RecIntraTrnInfo();
      localRecIntraTrnInfo1.statusCode = 16504;
      localRecIntraTrnInfo1.statusMsg = BPWLocaleUtil.getMessage(16504, new String[] { "ID " }, "API_MESSAGE");
      FFSDebug.log("RecXferProcessor2.getRecIntraById failed : null or empty ID passed", 0);
      return localRecIntraTrnInfo1;
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("RecXferProcessor2.getRecIntraById: failed to get DB Connection ");
    }
    try
    {
      localRecXferInstruction = RecXferInstruction.getRecXferInstruction(localFFSConnectionHolder, paramString);
      if (localRecXferInstruction == null)
      {
        localRecIntraTrnInfo1 = new RecIntraTrnInfo();
        localRecIntraTrnInfo1.statusCode = 17020;
        localRecIntraTrnInfo1.statusMsg = BPWLocaleUtil.getMessage(17020, null, "API_MESSAGE");
        FFSDebug.log("RecXferProcessor2.getRecIntraById :no values were found for the ID", 6);
      }
      else
      {
        localRecIntraTrnInfo1 = a(localRecXferInstruction);
      }
      localFFSConnectionHolder.conn.commit();
      RecIntraTrnInfo localRecIntraTrnInfo2 = localRecIntraTrnInfo1;
      return localRecIntraTrnInfo2;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "RecXferProcessor2.getRecIntraById: failed ";
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new BPWException(str + localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public RecIntraTrnInfo[] getRecIntraById(String[] paramArrayOfString)
    throws BPWException
  {
    FFSDebug.log("RecXferProcessor2.getIntraById (multiple): start", 6);
    RecIntraTrnInfo localRecIntraTrnInfo = null;
    RecXferInstruction localRecXferInstruction = null;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localRecIntraTrnInfo = new RecIntraTrnInfo();
      localRecIntraTrnInfo.statusCode = 16504;
      localRecIntraTrnInfo.statusMsg = BPWLocaleUtil.getMessage(16504, new String[] { "ID[] " }, "API_MESSAGE");
      FFSDebug.log("RecXferProcessor2.getRecIntraById (multiple): failed  null or empty ID array passed", 0);
      localObject1 = new RecIntraTrnInfo[] { localRecIntraTrnInfo };
      return localObject1;
    }
    Object localObject1 = new FFSConnectionHolder();
    ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
    if (((FFSConnectionHolder)localObject1).conn == null) {
      throw new BPWException("RecXferProcessor2.getRecIntraById (multiple): failed to get DB Connection ");
    }
    try
    {
      ArrayList localArrayList = new ArrayList();
      int i = paramArrayOfString.length;
      for (int j = 0; j < i; j++)
      {
        localRecXferInstruction = RecXferInstruction.getRecXferInstruction((FFSConnectionHolder)localObject1, paramArrayOfString[j]);
        if (localRecXferInstruction == null)
        {
          localRecIntraTrnInfo = new RecIntraTrnInfo();
          localRecIntraTrnInfo.recSrvrTid = paramArrayOfString[j];
          localRecIntraTrnInfo.statusCode = 17020;
          localRecIntraTrnInfo.statusMsg = BPWLocaleUtil.getMessage(17020, null, "API_MESSAGE");
          FFSDebug.log("RecXferProcessor2.getRecIntraById (multiple):  no values were found for the ID's", 6);
        }
        else
        {
          localRecIntraTrnInfo = a(localRecXferInstruction);
        }
        localArrayList.add(localRecIntraTrnInfo);
      }
      ((FFSConnectionHolder)localObject1).conn.commit();
      RecIntraTrnInfo[] arrayOfRecIntraTrnInfo = (RecIntraTrnInfo[])localArrayList.toArray(new RecIntraTrnInfo[0]);
      return arrayOfRecIntraTrnInfo;
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      String str = "RecXferProcessor2.getRecIntraById (multiple): failed ";
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new BPWException(str + localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
  }
  
  private TypeRecIntraTrnRsV1 jdMethod_if(TypeTrnRqCm paramTypeTrnRqCm, int paramInt)
  {
    TypeRecIntraTrnRsV1Aggregate localTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsUnExists = false;
    localTypeRecIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt);
    return new TypeRecIntraTrnRsV1(localTypeRecIntraTrnRsV1Aggregate);
  }
  
  private TypeRecIntraTrnRsV1 jdMethod_for(TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
  {
    TypeRecIntraTrnRsV1Aggregate localTypeRecIntraTrnRsV1Aggregate = new TypeRecIntraTrnRsV1Aggregate();
    localTypeRecIntraTrnRsV1Aggregate.RecIntraRsUnExists = false;
    localTypeRecIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt, paramString);
    return new TypeRecIntraTrnRsV1(localTypeRecIntraTrnRsV1Aggregate);
  }
  
  private RecIntraTrnInfo a(RecXferInstruction paramRecXferInstruction)
    throws BPWException
  {
    RecIntraTrnInfo localRecIntraTrnInfo = new RecIntraTrnInfo();
    localRecIntraTrnInfo.recSrvrTid = paramRecXferInstruction.RecSrvrTID;
    localRecIntraTrnInfo.logId = paramRecXferInstruction.LogID;
    localRecIntraTrnInfo.setSubmittedBy(paramRecXferInstruction.SubmittedBy);
    localRecIntraTrnInfo.customerId = paramRecXferInstruction.CustomerID;
    localRecIntraTrnInfo.fiId = paramRecXferInstruction.FIID;
    localRecIntraTrnInfo.bankId = paramRecXferInstruction.BankID;
    localRecIntraTrnInfo.fromBankId = paramRecXferInstruction.fromBankID;
    localRecIntraTrnInfo.branchId = paramRecXferInstruction.BranchID;
    localRecIntraTrnInfo.fromBranchId = paramRecXferInstruction.fromBranchID;
    localRecIntraTrnInfo.acctIdFrom = paramRecXferInstruction.AcctDebitID;
    localRecIntraTrnInfo.acctTypeFrom = paramRecXferInstruction.AcctDebitType;
    localRecIntraTrnInfo.acctIdTo = paramRecXferInstruction.AcctCreditID;
    localRecIntraTrnInfo.acctTypeTo = paramRecXferInstruction.AcctCreditType;
    localRecIntraTrnInfo.startDate = paramRecXferInstruction.StartDate;
    localRecIntraTrnInfo.endDate = paramRecXferInstruction.EndDate;
    localRecIntraTrnInfo.nInsts = paramRecXferInstruction.InstanceCount;
    if (localRecIntraTrnInfo.nInsts > 0) {
      localRecIntraTrnInfo.nInstsExists = true;
    } else {
      localRecIntraTrnInfo.nInstsExists = false;
    }
    localRecIntraTrnInfo.freq = paramRecXferInstruction.Frequency;
    localRecIntraTrnInfo.amount = String.valueOf(paramRecXferInstruction.Amount);
    localRecIntraTrnInfo.curDef = paramRecXferInstruction.CurDef;
    localRecIntraTrnInfo.dateToPost = String.valueOf(paramRecXferInstruction.StartDate);
    localRecIntraTrnInfo.srvrTid = null;
    localRecIntraTrnInfo.status = paramRecXferInstruction.Status;
    localRecIntraTrnInfo.statusCode = 0;
    localRecIntraTrnInfo.statusMsg = BPWLocaleUtil.getMessage(0, new String[] { "ID " }, "API_MESSAGE");
    if (paramRecXferInstruction.extraFields != null) {
      localRecIntraTrnInfo.extraFields = paramRecXferInstruction.extraFields;
    }
    return localRecIntraTrnInfo;
  }
  
  public static void logTransAuditLog(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, ILocalizable paramILocalizable, String paramString12, String paramString13)
    throws FFSException
  {
    BigDecimal localBigDecimal = FFSUtil.getBigDecimal(paramString4);
    a(paramFFSConnectionHolder, paramString1, paramString2, paramString3, paramInt, localBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramILocalizable, paramString12, paramString13);
  }
  
  protected static void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, int paramInt, BigDecimal paramBigDecimal, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, ILocalizable paramILocalizable, String paramString11, String paramString12)
    throws FFSException
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString2);
    }
    catch (Exception localException)
    {
      String str = "XferProcessor2.logTransAuditLog: businessId not valid, should be represented as integer";
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
    TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), paramString1, paramString11, paramString12, paramILocalizable, paramString3, paramInt, i, paramBigDecimal, paramString4, paramString5, paramString10, paramString7, paramString8, paramString6, paramString9, 0);
  }
  
  public static String deleteLimit(FFSConnectionHolder paramFFSConnectionHolder, XferInstruction paramXferInstruction, HashMap paramHashMap, String paramString, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("RecXferProcessor2.deleteLimit", ": starts....", 6);
    String str = null;
    IntraTrnInfo localIntraTrnInfo = mapToIntraTrnInfo(paramXferInstruction, paramHashMap);
    int i = LimitCheckApprovalProcessor.processIntraTrnDelete(paramFFSConnectionHolder, localIntraTrnInfo, paramHashMap);
    paramXferInstruction.setStatusCode(localIntraTrnInfo.getStatusCode());
    paramXferInstruction.setStatusMsg(localIntraTrnInfo.getStatusMsg());
    str = LimitCheckApprovalProcessor.mapToStatus(i);
    if ((paramBoolean) && ("LIMIT_REVERT_SUCCEEDED".equals(str)))
    {
      paramXferInstruction.Status = paramString;
      XferInstruction.updateStatus(paramFFSConnectionHolder, localIntraTrnInfo.srvrTid, paramXferInstruction.Status);
    }
    else if ((paramBoolean) && (!"LIMIT_REVERT_SUCCEEDED".equals(str)))
    {
      paramXferInstruction.Status = str;
      XferInstruction.updateStatus(paramFFSConnectionHolder, localIntraTrnInfo.srvrTid, str);
    }
    else
    {
      paramXferInstruction.Status = str;
    }
    FFSDebug.log("RecXferProcessor2.deleteLimit", ": ends....", 6);
    return str;
  }
  
  public static IntraTrnInfo mapToIntraTrnInfo(XferInstruction paramXferInstruction, HashMap paramHashMap)
  {
    IntraTrnInfo localIntraTrnInfo = new IntraTrnInfo();
    localIntraTrnInfo.customerId = paramXferInstruction.CustomerID;
    localIntraTrnInfo.bankId = paramXferInstruction.BankID;
    localIntraTrnInfo.fromBankId = paramXferInstruction.fromBankID;
    localIntraTrnInfo.branchId = paramXferInstruction.BranchID;
    localIntraTrnInfo.fromBranchId = paramXferInstruction.fromBranchID;
    localIntraTrnInfo.acctIdFrom = paramXferInstruction.AcctDebitID;
    localIntraTrnInfo.acctTypeFrom = paramXferInstruction.AcctDebitType;
    localIntraTrnInfo.acctIdTo = paramXferInstruction.AcctCreditID;
    localIntraTrnInfo.acctTypeTo = paramXferInstruction.AcctCreditType;
    localIntraTrnInfo.amount = paramXferInstruction.Amount;
    localIntraTrnInfo.curDef = paramXferInstruction.CurDef;
    localIntraTrnInfo.toAmount = paramXferInstruction.ToAmount;
    localIntraTrnInfo.toAmtCurrency = paramXferInstruction.ToAmtCurrency;
    localIntraTrnInfo.userAssignedAmount = paramXferInstruction.userAssignedAmount;
    localIntraTrnInfo.dateToPost = paramXferInstruction.DateToPost;
    localIntraTrnInfo.srvrTid = paramXferInstruction.SrvrTID;
    localIntraTrnInfo.logId = paramXferInstruction.LogID;
    localIntraTrnInfo.status = paramXferInstruction.Status;
    localIntraTrnInfo.recSrvrTid = paramXferInstruction.RecSrvrTID;
    localIntraTrnInfo.lastModified = paramXferInstruction.LastModified;
    localIntraTrnInfo.submittedBy = paramXferInstruction.SubmittedBy;
    localIntraTrnInfo.fiId = paramXferInstruction.FIID;
    localIntraTrnInfo.extraFields = paramXferInstruction.extraFields;
    paramHashMap.put("DateCreate", paramXferInstruction.DateCreate);
    return localIntraTrnInfo;
  }
  
  public static void processApprovalResult(String paramString1, String paramString2, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "RecXferProcessor2.processApprovalResult: ";
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
      XferInstruction localXferInstruction = XferInstruction.getXferInstruction(localFFSConnectionHolder, paramString1);
      if (localXferInstruction == null)
      {
        i = 17502;
        str2 = "Transaction id is invalid";
        throw new FFSException(i, str1 + str2);
      }
      localObject1 = localXferInstruction.Status;
      int k = -1;
      if (!"APPROVAL_PENDING".equalsIgnoreCase(localXferInstruction.Status))
      {
        i = 17503;
        str2 = "Transaction not waiting for Approval";
        throw new FFSException(i, str1 + str2);
      }
      String str4;
      Object localObject2;
      if (paramString2.compareTo("Approved") == 0)
      {
        FFSDebug.log(str1, ": Processing Approved Transfer", 6);
        localObject1 = "WILLPROCESSON";
        k = 502;
        XferInstruction.updateStatus(localFFSConnectionHolder, paramString1, (String)localObject1);
        a(localFFSConnectionHolder, localXferInstruction);
      }
      else if (paramString2.compareTo("Rejected") == 0)
      {
        FFSDebug.log(str1, ": Processing rejected Transfer", 6);
        IntraTrnInfo localIntraTrnInfo = mapToIntraTrnInfo(localXferInstruction, paramHashMap);
        int n = LimitCheckApprovalProcessor.processIntraTrnReject(localFFSConnectionHolder, localIntraTrnInfo, paramHashMap);
        try
        {
          a(localFFSConnectionHolder, paramString1, "FAILEDON");
        }
        catch (Exception localException)
        {
          String str5 = str1 + ": Failed to update transfer history" + ". Error: " + FFSDebug.stackTrace(localException);
          FFSDebug.log(str5);
          throw new FFSException(str5);
        }
        str4 = LimitCheckApprovalProcessor.mapToStatus(n);
        if ("LIMIT_REVERT_SUCCEEDED".equals(str4))
        {
          localObject1 = "APPROVAL_REJECTED";
          k = 503;
          XferInstruction.updateStatus(localFFSConnectionHolder, paramString1, (String)localObject1);
        }
        else
        {
          localObject1 = str4;
          k = 402;
          XferInstruction.updateStatus(localFFSConnectionHolder, paramString1, (String)localObject1);
          if (j >= 1)
          {
            int i1 = Integer.parseInt(localXferInstruction.CustomerID);
            localObject2 = new BigDecimal(localXferInstruction.Amount);
            String str7 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
            String str8 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
            LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(k, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
            TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), localXferInstruction.SubmittedBy, null, null, localLocalizableString, localXferInstruction.LogID, 4600, i1, (BigDecimal)localObject2, localXferInstruction.CurDef, localXferInstruction.SrvrTID, (String)localObject1, str8, localXferInstruction.BankID, str7, localXferInstruction.fromBankID, 0);
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
        int m = Integer.parseInt(localXferInstruction.CustomerID);
        BigDecimal localBigDecimal = new BigDecimal(localXferInstruction.Amount);
        str4 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
        String str6 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
        localObject2 = BPWLocaleUtil.getLocalizedMessage(k, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), localXferInstruction.SubmittedBy, null, null, (ILocalizable)localObject2, localXferInstruction.LogID, 4600, m, localBigDecimal, localXferInstruction.CurDef, localXferInstruction.SrvrTID, (String)localObject1, str6, localXferInstruction.BankID, str4, localXferInstruction.fromBankID, 0);
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
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, XferInstruction paramXferInstruction)
    throws BPWException
  {
    FFSDebug.log("RecXferProcessor2.addScheduleForSingleXfer", ": Starts ....", 6);
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramXferInstruction.FIID;
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = 10;
    localScheduleInfo.StartDate = Integer.parseInt(paramXferInstruction.DateToPost.substring(0, 8) + "00");
    localScheduleInfo.LogID = paramXferInstruction.LogID;
    localScheduleInfo.InstanceCount = 1;
    try
    {
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "INTRATRN", paramXferInstruction.SrvrTID, localScheduleInfo);
    }
    catch (Exception localException)
    {
      String str = "RecXferProcessor2.addScheduleForSingleXfer: Failed. Error: " + FFSDebug.stackTrace(localException);
      throw new BPWException(str);
    }
  }
  
  private static void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    FFSDebug.log("RecXferProcessor2.updateXferHistory", ": Starts ....", 6);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new FFSException("RecXferProcessor2.updateXferHistory: AuditAgent is null!!");
    }
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    if (localBPWMsgBroker == null) {
      throw new FFSException("RecXferProcessor2.updateXferHistory: BPWMsgBroker is null!!");
    }
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString1, paramFFSConnectionHolder);
    Object localObject;
    if (arrayOfString[0] == null)
    {
      localObject = "RecXferProcessor2.updateXferHistory: Failed: could not find the SrvrTID: " + paramString1 + " in BPW_SrvrTrans";
      FFSDebug.log((String)localObject, 6);
      throw new BPWException((String)localObject);
    }
    if (arrayOfString[0].startsWith("OFX151"))
    {
      localObject = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "IntraTrnRsV1", "OFX151");
      if (localObject != null) {
        localAuditAgent.updateXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1)localObject, paramFFSConnectionHolder, paramString2);
      }
    }
    else if (arrayOfString[0].startsWith("OFX200"))
    {
      localObject = (com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg(arrayOfString[1], "IntraTrnRsV1", "OFX200");
      if (localObject != null) {
        localAuditAgent.updateXferRsV1((com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1)localObject, paramFFSConnectionHolder, paramString2);
      }
    }
  }
  
  public static String doLimitChecking(FFSConnectionHolder paramFFSConnectionHolder, XferInstruction paramXferInstruction, HashMap paramHashMap, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("RecXferProcessor2.doLimitChecking", ": starts....", 6);
    String str = null;
    IntraTrnInfo localIntraTrnInfo = mapToIntraTrnInfo(paramXferInstruction, paramHashMap);
    int i = LimitCheckApprovalProcessor.processIntraTrnAdd(paramFFSConnectionHolder, localIntraTrnInfo, paramHashMap);
    paramXferInstruction.setStatusCode(localIntraTrnInfo.getStatusCode());
    paramXferInstruction.setStatusMsg(localIntraTrnInfo.getStatusMsg());
    str = LimitCheckApprovalProcessor.mapToStatus(i);
    FFSDebug.log("RecXferProcessor2.doLimitChecking", ": status: ", str, 6);
    if ((paramBoolean) && (!"WILLPROCESSON".equalsIgnoreCase(str)))
    {
      if ("APPROVAL_PENDING".equalsIgnoreCase(str)) {
        paramXferInstruction.Status = "APPROVAL_PENDING";
      }
      XferInstruction.updateStatus(paramFFSConnectionHolder, localIntraTrnInfo.srvrTid, str);
    }
    FFSDebug.log("RecXferProcessor2.doLimitChecking", ": ends....", 6);
    return str;
  }
  
  private void jdMethod_do(Hashtable paramHashtable, FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    String str = null;
    try
    {
      FFSDebug.log("RecXferProcessor2.processXtraInfo start: ", 6);
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
      str = "RecXferProcessor2.processXtraInfo : failed ";
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new BPWException(str + localException.toString());
    }
    FFSDebug.log("RecXferProcessor2.processXtraInfo end: ", 6);
  }
  
  public BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "RecXferProcessor2.getBankingDaysInRange: ";
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
      String str2 = "RecXferProcessor2.getBankingDaysInRange: failed to get DB connection ";
      FFSDebug.log(str2 + FFSDebug.stackTrace(localException1), 0);
      throw new BPWException(str2 + localException1.toString());
    }
    int k = DBUtil.getCurrentStartDate() / 100;
    boolean bool = this.Q.NoImmediateTransfer;
    try
    {
      if (bool) {
        k = DBUtil.getNextRunDate(localFFSConnectionHolder, paramBankingDays.getFiID(), "INTRATRN");
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException2)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = "RecXferProcessor2.getBankingDaysInRange: failed ";
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
        int m = BPWUtil.calendarToDueDateFormatInt(localCalendar2);
        if ((SmartCalendar.isBusinessDay(paramBankingDays.getFiID(), m, "BookTransfer")) && (m >= k)) {
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
    
    a(RecXferProcessor2.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.RecXferProcessor2
 * JD-Core Version:    0.7.0.1
 */