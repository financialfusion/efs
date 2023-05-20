package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.backend.BackendStatus;
import com.ffusion.ffs.bpw.backend.MonitorBackend;
import com.ffusion.ffs.bpw.backend.ToBackendRunnable;
import com.ffusion.ffs.bpw.custimpl.ToBackend;
import com.ffusion.ffs.bpw.db.BPWXferExtraInfo;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.db.Trans;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.IntraTrnRslt;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.TransferCalloutHandlerBase;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.TransferIntraMap;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctFromUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBCCAcctToUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeBankAcctToAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeCCAcctToAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraCanRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraCanRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeStatusAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRsCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeXferPrcStsAggregate;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

public class XferProcessor2
  implements FFSConst, DBConsts, OFXConsts, BPWResource
{
  private PropertyConfig b = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
  private int j = 2000;
  private int g = 3;
  private boolean h = false;
  private boolean d = false;
  private int c = 1;
  private static List k = new Vector();
  private static List i = new Vector();
  private boolean f = false;
  private boolean e = false;
  
  public XferProcessor2()
  {
    String str = this.b.otherProperties.getProperty("intra.immediate.supportTimeout", "false");
    try
    {
      this.f = (str.equalsIgnoreCase("true"));
    }
    catch (Throwable localThrowable)
    {
      this.f = false;
    }
    this.c = this.b.LogLevel;
    this.e = this.b.getEnforceEnrollment();
  }
  
  public TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData)
    throws BPWException
  {
    return processIntraTrnRqV1(paramTypeIntraTrnRqV1, paramTypeUserData, paramTypeUserData._tran_id);
  }
  
  public TypeIntraTrnRsV1 processIntraTrnRqV1(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    String str2;
    try
    {
      if ((this.e) && (!Customer.validCustomer(paramTypeUserData._uid)))
      {
        String str1 = "This customer, customerID=" + paramTypeUserData._uid + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
        FFSDebug.log(str1, 0);
        throw new BPWException(str1);
      }
    }
    catch (Exception localException1)
    {
      str2 = "*** XferProcessor2.processIntraTrnRqV1 failed:";
      FFSDebug.log(str2 + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = null;
    try
    {
      str2 = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.__memberName;
      if (str2.equals("IntraRq")) {
        localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1, paramTypeUserData, paramString);
      } else if (str2.equals("IntraModRq")) {
        localTypeIntraTrnRsV1 = jdMethod_do(paramTypeIntraTrnRqV1, paramTypeUserData, paramString);
      } else if (str2.equals("IntraCanRq")) {
        localTypeIntraTrnRsV1 = a(paramTypeIntraTrnRqV1, paramTypeUserData, paramString);
      } else {
        throw new BPWException("Wrong request type!");
      }
    }
    catch (Exception localException2)
    {
      String str3 = "*** XferProcessor2.processIntraTrnRqV1 failed:";
      FFSDebug.log(str3 + localException2.toString());
      localTypeIntraTrnRsV1 = a(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000);
    }
    return localTypeIntraTrnRsV1;
  }
  
  private TypeIntraTrnRsV1 jdMethod_if(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    FFSDebug.log("XferProcessor2.processXferRqV1: call", 6);
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("XferProcessor2.processXferRqV1:Can not get DB Connection.");
    }
    TypeXferInfoAggregate localTypeXferInfoAggregate = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraRq.XferInfo;
    EnumCurrencyEnum localEnumCurrencyEnum = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraRq.CurDef;
    try
    {
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm.TrnUID))
      {
        if (this.c >= 3)
        {
          localObject1 = new a(null);
          a(localTypeXferInfoAggregate, paramTypeUserData, (a)localObject1, null, localEnumCurrencyEnum);
          str1 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).b, ((a)localObject1).c);
          String str2 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).jdField_null, ((a)localObject1).f);
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(914, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4601, ((a)localObject1).jdField_char, ((a)localObject1).jdField_for, ((a)localObject1).jdField_goto, str1, str2, ((a)localObject1).jdField_new, ((a)localObject1).a, "DUPLICATE", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        localTypeIntraTrnRsV1 = a(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2019);
        Object localObject1 = localTypeIntraTrnRsV1;
        return localObject1;
      }
      localTypeIntraTrnRsV1 = processXferRqV1(localFFSConnectionHolder, localTypeXferInfoAggregate, paramTypeUserData, paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, null, paramString, localEnumCurrencyEnum);
      localFFSConnectionHolder.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "*** XferProcessor2.processXferRqV1 failed: ";
      FFSDebug.log(localOFXException, str1);
      localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "*** XferProcessor2.processXferRqV1 failed: ";
      FFSDebug.log(localBPWException, str1);
      if (localBPWException.getErrorCode() == -1) {
        localTypeIntraTrnRsV1 = a(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000);
      } else {
        localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str1 = "*** XferProcessor2.processXferRqV1 failed: ";
      FFSDebug.log(localException, str1);
      localTypeIntraTrnRsV1 = a(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeIntraTrnRsV1;
  }
  
  public TypeIntraTrnRsV1 processXferRqV1(FFSConnectionHolder paramFFSConnectionHolder, TypeXferInfoAggregate paramTypeXferInfoAggregate, TypeUserData paramTypeUserData, TypeTrnRqCm paramTypeTrnRqCm, String paramString1, String paramString2, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = null;
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("XferProcessor2.processXferRqV1:AuditAgent is null!!");
    }
    HashMap localHashMap = new HashMap();
    a locala = new a(null);
    a(paramTypeXferInfoAggregate, paramTypeUserData, locala, paramString1, paramEnumCurrencyEnum);
    Object localObject1 = "WILLPROCESSON";
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    String str1 = paramString1 != null ? "AddIntraTransferFromScheduleOFX200" : "AddIntraTransferOFX200";
    Object localObject3;
    Object localObject4;
    Object localObject5;
    try
    {
      String str2 = paramString2;
      if ((paramString1 != null) && (paramString1.length() > 0))
      {
        str2 = BPWTrackingIDGenerator.getNextId();
        locala.jdField_long = str2;
      }
      localObject3 = new XferInstruction("", locala.jdField_int, locala.h, locala.jdField_char, locala.jdField_for, locala.jdField_new, locala.jdField_else, locala.b, locala.c, locala.jdField_null, locala.f, "", String.valueOf(locala.e), "WILLPROCESSON", str2, paramString1, paramTypeUserData._submittedBy, "", locala.a, locala.jdField_do);
      locala.jdField_goto = XferInstruction.create(paramFFSConnectionHolder, (XferInstruction)localObject3);
      ((XferInstruction)localObject3).SrvrTID = locala.jdField_goto;
      localObject4 = null;
      if ((paramTypeUserData._privateTagContainer != null) && (!paramTypeUserData._privateTagContainer.isEmpty())) {
        localObject4 = BPWUtil.convertHashTableToMap(paramTypeUserData._privateTagContainer);
      }
      ((XferInstruction)localObject3).extraFields = localObject4;
      localObject5 = TransferIntraMap.mapIntraRqToTransferInfo(paramTypeXferInfoAggregate, paramTypeUserData, paramTypeTrnRqCm.TrnUID, "IntraRq", locala.jdField_goto, paramEnumCurrencyEnum);
      try
      {
        localTransferCalloutHandlerBase.begin((TransferInfo)localObject5, str1);
      }
      catch (FFSException localFFSException)
      {
        try
        {
          localTransferCalloutHandlerBase.failure((TransferInfo)localObject5, str1);
        }
        catch (Throwable localThrowable4) {}
        FFSDebug.log(((TransferInfo)localObject5).getStatusMsg(), 6);
        localTypeIntraTrnRsV1 = jdMethod_if(paramTypeTrnRqCm, 2000, ((TransferInfo)localObject5).getStatusMsg());
        return localTypeIntraTrnRsV1;
      }
      try
      {
        String str3 = RecXferProcessor2.doLimitChecking(paramFFSConnectionHolder, (XferInstruction)localObject3, localHashMap, false);
        String str4;
        String str5;
        LocalizableString localLocalizableString;
        if ((!"WILLPROCESSON".equals(str3)) && (!"APPROVAL_PENDING".equals(str3)))
        {
          if (this.c >= 3)
          {
            str4 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
            str5 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
            localLocalizableString = BPWLocaleUtil.getLocalizedMessage(401, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
            logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4600, locala.jdField_char, locala.jdField_for, locala.jdField_goto, str4, str5, locala.jdField_new, locala.a, str3, localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
          }
          throw new OFXException(((XferInstruction)localObject3).getStatusMsg(), 2000);
        }
        ((XferInstruction)localObject3).Status = str3;
        XferInstruction.updateStatus(paramFFSConnectionHolder, locala.jdField_goto, ((XferInstruction)localObject3).Status);
        a(paramTypeUserData._privateTagContainer, paramFFSConnectionHolder, locala.jdField_goto, "Add");
        if (this.c >= 3)
        {
          str4 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
          str5 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
          localLocalizableString = BPWLocaleUtil.getLocalizedMessage(913, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4600, locala.jdField_char, locala.jdField_for, locala.jdField_goto, str4, str5, locala.jdField_new, locala.a, str3, localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        if (!"FAILEDON".equals(localObject1)) {
          locala.d = "WILLPROCESSON";
        } else {
          locala.d = "FAILEDON";
        }
        localObject1 = str3;
        localTypeIntraTrnRsV1 = a(paramTypeTrnRqCm, paramTypeXferInfoAggregate, locala, paramString1, paramTypeUserData, paramEnumCurrencyEnum);
        localAuditAgent.saveXferRsV1(localTypeIntraTrnRsV1, locala.jdField_int, paramFFSConnectionHolder, locala.d);
        if ("WILLPROCESSON".equalsIgnoreCase((String)localObject1)) {
          if (paramTypeXferInfoAggregate.DtDueExists)
          {
            scheduleXferAddRq(paramFFSConnectionHolder, locala);
          }
          else if (this.h)
          {
            locala.e = DBUtil.getCurrentStartDate();
            scheduleXferAddRq(paramFFSConnectionHolder, locala);
          }
        }
        paramFFSConnectionHolder.conn.commit();
        try
        {
          if (localTypeIntraTrnRsV1.IntraTrnRs.TrnRsCm.Status.Code == 0) {
            localTransferCalloutHandlerBase.end((TransferInfo)localObject5, str1);
          } else {
            localTransferCalloutHandlerBase.failure((TransferInfo)localObject5, str1);
          }
        }
        catch (Throwable localThrowable5) {}
      }
      catch (Throwable localThrowable3)
      {
        try
        {
          localTransferCalloutHandlerBase.failure((TransferInfo)localObject5, str1);
        }
        catch (Throwable localThrowable6) {}
        throw localThrowable3;
      }
    }
    catch (OFXException localOFXException)
    {
      throw localOFXException;
    }
    catch (BPWException localBPWException)
    {
      throw localBPWException;
    }
    catch (Throwable localThrowable1)
    {
      localObject3 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("Process IntraXfer failed. Error: " + (String)localObject3);
      throw new BPWException((String)localObject3);
    }
    if ((!paramTypeXferInfoAggregate.DtDueExists) && (!this.h) && ("WILLPROCESSON".equalsIgnoreCase((String)localObject1))) {
      try
      {
        XferInstruction.updateStatus(paramFFSConnectionHolder, locala.jdField_goto, "IMMED_INPROCESS");
        if (this.c >= 4)
        {
          localObject2 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
          localObject3 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
          localObject4 = BPWLocaleUtil.getLocalizedMessage(915, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4607, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject2, (String)localObject3, locala.jdField_new, locala.a, "IMMED_INPROCESS", (ILocalizable)localObject4, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        paramFFSConnectionHolder.conn.commit();
        Object localObject2 = new IntraTrnRslt();
        localObject3 = null;
        if ((paramTypeUserData._privateTagContainer != null) && (!paramTypeUserData._privateTagContainer.isEmpty())) {
          localObject3 = BPWUtil.convertHashTableToMap(paramTypeUserData._privateTagContainer);
        }
        if (this.f)
        {
          FFSDebug.log("XferProcessor2.processXferRqV1 Calling doImmediateXfer", 6);
          localObject2 = jdMethod_if(paramFFSConnectionHolder, locala, (HashMap)localObject3);
        }
        else
        {
          FFSDebug.log("XferProcessor2.processXferRqV1 Calling doImmediateXferWithoutTimeout", 6);
          localObject2 = a(paramFFSConnectionHolder, locala, (HashMap)localObject3);
        }
        Object localObject6;
        if (((IntraTrnRslt)localObject2).status != -1)
        {
          localObject4 = new BackendProcessor();
          ((BackendProcessor)localObject4).processImmIntraTrnRslt(paramFFSConnectionHolder, (IntraTrnRslt)localObject2, locala.h);
          localObject5 = SrvrTrans.findResponseBySrvrTID(locala.jdField_goto, paramFFSConnectionHolder);
          localObject6 = localObject5[1];
          BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
          localTypeIntraTrnRsV1 = (TypeIntraTrnRsV1)localBPWMsgBroker.parseMsg((String)localObject6, "IntraTrnRsV1", "OFX200");
        }
        else
        {
          FFSDebug.log("Process IntraXfer failed at the backend.");
          if (this.d)
          {
            RsCmBuilder.updateXferRsDatePosted(locala.g, localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs);
            localAuditAgent.updateXferRsV1(localTypeIntraTrnRsV1, paramFFSConnectionHolder, "WILLPROCESSON");
            scheduleXferAddRq(paramFFSConnectionHolder, locala);
            if (this.c >= 4)
            {
              localObject4 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
              localObject5 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
              localObject6 = BPWLocaleUtil.getLocalizedMessage(916, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
              logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4607, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject4, (String)localObject5, locala.jdField_new, locala.a, "WILLPROCESSON", (ILocalizable)localObject6, paramTypeUserData._agentID, paramTypeUserData._agentType);
            }
          }
          else
          {
            locala.jdField_if = 2000;
            locala.d = "FAILEDON";
            if (this.c >= 1)
            {
              localObject4 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
              localObject5 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
              localObject6 = BPWLocaleUtil.getLocalizedMessage(917, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
              logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4607, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject4, (String)localObject5, locala.jdField_new, locala.a, locala.d, (ILocalizable)localObject6, paramTypeUserData._agentID, paramTypeUserData._agentType);
            }
            RsCmBuilder.updateRsXferPrcSts(locala.d, locala.g, localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsUn.IntraRs.XferPrcSts);
            RsCmBuilder.updateStatusV1Aggregate(localTypeIntraTrnRsV1.IntraTrnRs.TrnRsCm.Status, locala.jdField_if);
            XferInstruction.updateStatus(paramFFSConnectionHolder, locala.jdField_goto, locala.d);
            localAuditAgent.updateXferRsV1(localTypeIntraTrnRsV1, paramFFSConnectionHolder, locala.d);
          }
        }
      }
      catch (Throwable localThrowable2)
      {
        localObject3 = FFSDebug.stackTrace(localThrowable2);
        FFSDebug.log("Process IntraXfer failed. Error: " + (String)localObject3);
        throw new BPWException((String)localObject3);
      }
    }
    return localTypeIntraTrnRsV1;
  }
  
  private TypeIntraTrnRsV1 jdMethod_do(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    FFSDebug.log("XferProcessor2.processXferRqV1Mod: call", 6);
    TypeIntraTrnRsV1 localTypeIntraTrnRsV11 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("XferProcessor2.processXferRqV1Mod:Can not get DB Connection.");
    }
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    TypeXferInfoAggregate localTypeXferInfoAggregate = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.XferInfo;
    EnumCurrencyEnum localEnumCurrencyEnum = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.CurDef;
    try
    {
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm.TrnUID))
      {
        if (this.c >= 3)
        {
          localObject1 = new a(null);
          a(localTypeXferInfoAggregate, paramTypeUserData, (a)localObject1, null, localEnumCurrencyEnum);
          String str1 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).b, ((a)localObject1).c);
          String str3 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).jdField_null, ((a)localObject1).f);
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(919, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4604, ((a)localObject1).jdField_char, ((a)localObject1).jdField_for, ((a)localObject1).jdField_goto, str1, str3, ((a)localObject1).jdField_new, ((a)localObject1).a, "DUPLICATE", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        localTypeIntraTrnRsV11 = a(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2019);
        localObject1 = localTypeIntraTrnRsV11;
        return localObject1;
      }
      Object localObject1 = TransferIntraMap.mapIntraRqToTransferInfo(paramTypeIntraTrnRqV1, paramTypeUserData);
      try
      {
        localTransferCalloutHandlerBase.begin((TransferInfo)localObject1, "ModifyIntraTransferOFX200");
      }
      catch (FFSException localFFSException)
      {
        try
        {
          localTransferCalloutHandlerBase.failure((TransferInfo)localObject1, "ModifyIntraTransferOFX200");
        }
        catch (Throwable localThrowable2) {}
        FFSDebug.log(((TransferInfo)localObject1).getStatusMsg(), 6);
        localTypeIntraTrnRsV11 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000, ((TransferInfo)localObject1).getStatusMsg());
        TypeIntraTrnRsV1 localTypeIntraTrnRsV12 = localTypeIntraTrnRsV11;
        return localTypeIntraTrnRsV12;
      }
      try
      {
        localTypeIntraTrnRsV11 = processXferRqV1Mod(localFFSConnectionHolder, localTypeXferInfoAggregate, paramTypeUserData, paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraModRq.SrvrTID, null, paramString, localEnumCurrencyEnum);
        localFFSConnectionHolder.conn.commit();
        try
        {
          localObject1 = TransferIntraMap.mapIntraRsToTransferInfo(localTypeIntraTrnRsV11, paramTypeUserData);
          if (((TransferInfo)localObject1).getStatusCode() == 0) {
            localTransferCalloutHandlerBase.end((TransferInfo)localObject1, "ModifyIntraTransferOFX200");
          } else {
            localTransferCalloutHandlerBase.failure((TransferInfo)localObject1, "ModifyIntraTransferOFX200");
          }
        }
        catch (Throwable localThrowable1) {}
      }
      catch (Exception localException2)
      {
        try
        {
          localTransferCalloutHandlerBase.failure((TransferInfo)localObject1, "ModifyIntraTransferOFX200");
        }
        catch (Throwable localThrowable3) {}
        throw localException2;
      }
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "*** XferProcessor2.processXferRqV1Mod failed: ";
      FFSDebug.log(localOFXException, str2);
      localTypeIntraTrnRsV11 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      str2 = "*** XferProcessor2.processXferRqV1Mod failed: ";
      FFSDebug.log(localBPWException, str2);
      if (localBPWException.getErrorCode() == -1) {
        localTypeIntraTrnRsV11 = a(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000);
      } else {
        localTypeIntraTrnRsV11 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException1)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** XferProcessor2.processXferRqV1Mod failed: ";
      FFSDebug.log(localException1, str2);
      localTypeIntraTrnRsV11 = a(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeIntraTrnRsV11;
  }
  
  public TypeIntraTrnRsV1 processXferRqV1Mod(FFSConnectionHolder paramFFSConnectionHolder, TypeXferInfoAggregate paramTypeXferInfoAggregate, TypeUserData paramTypeUserData, TypeTrnRqCm paramTypeTrnRqCm, String paramString1, String paramString2, String paramString3, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    TypeIntraTrnRsV1 localTypeIntraTrnRsV11 = null;
    a locala = new a(null);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("XferProcessor2.processXferRqV1Mod:AuditAgent is null!!");
    }
    a(paramTypeXferInfoAggregate, paramTypeUserData, locala, paramString2, paramEnumCurrencyEnum);
    locala.jdField_goto = paramString1;
    try
    {
      XferInstruction localXferInstruction = XferInstruction.getXferInstruction(paramFFSConnectionHolder, paramString1);
      if (localXferInstruction == null)
      {
        locala.jdField_if = 2018;
        locala.d = "FAILEDON";
        localTypeIntraTrnRsV11 = a(paramTypeTrnRqCm, paramTypeXferInfoAggregate, locala);
        return localTypeIntraTrnRsV11;
      }
      if (localXferInstruction.Status.equals("CANCELEDON") == true)
      {
        locala.jdField_if = 2017;
        locala.d = "FAILEDON";
        localTypeIntraTrnRsV11 = a(paramTypeTrnRqCm, paramTypeXferInfoAggregate, locala);
        return localTypeIntraTrnRsV11;
      }
      int m = 1;
      int n = a(paramFFSConnectionHolder, locala);
      if (n == 0) {
        ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "INTRATRN", locala.jdField_goto);
      } else if ("APPROVAL_PENDING".equals(localXferInstruction.Status) != true) {
        if (("APPROVAL_REJECTED".equals(localXferInstruction.Status) == true) || ("APPROVAL_NOT_ALLOWED".equals(localXferInstruction.Status) == true))
        {
          m = 0;
        }
        else
        {
          locala.jdField_if = 2016;
          locala.d = "FAILEDON";
          localTypeIntraTrnRsV11 = a(paramTypeTrnRqCm, paramTypeXferInfoAggregate, locala);
          return localTypeIntraTrnRsV11;
        }
      }
      if (m == 1)
      {
        localObject1 = RecXferProcessor2.deleteLimit(paramFFSConnectionHolder, localXferInstruction, new HashMap(), "CANCELEDON", false);
        if (!"LIMIT_REVERT_SUCCEEDED".equals(localObject1))
        {
          if (this.c >= 3)
          {
            localObject2 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
            str = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
            localObject3 = BPWLocaleUtil.getLocalizedMessage(402, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
            logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4603, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject2, str, locala.jdField_new, locala.a, (String)localObject1, (ILocalizable)localObject3, paramTypeUserData._agentID, paramTypeUserData._agentType);
          }
          throw new OFXException(localXferInstruction.getStatusMsg(), 2000);
        }
      }
      Object localObject1 = new XferInstruction(locala.jdField_goto, locala.jdField_int, locala.h, locala.jdField_char, locala.jdField_for, locala.jdField_new, locala.jdField_else, locala.b, locala.c, locala.jdField_null, locala.f, "", String.valueOf(locala.e), "WILLPROCESSON", paramString3, paramString2, paramTypeUserData._submittedBy, FFSUtil.getDateString("yyyyMMddHHmmss"), locala.a, locala.jdField_do);
      Object localObject2 = null;
      if ((paramTypeUserData._privateTagContainer != null) && (!paramTypeUserData._privateTagContainer.isEmpty())) {
        localObject2 = BPWUtil.convertHashTableToMap(paramTypeUserData._privateTagContainer);
      }
      ((XferInstruction)localObject1).extraFields = localObject2;
      String str = RecXferProcessor2.doLimitChecking(paramFFSConnectionHolder, (XferInstruction)localObject1, new HashMap(), false);
      if ((!"WILLPROCESSON".equals(str)) && (!"APPROVAL_PENDING".equals(str)))
      {
        if (this.c >= 3)
        {
          localObject3 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
          localObject4 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
          localObject5 = BPWLocaleUtil.getLocalizedMessage(401, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4603, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject3, (String)localObject4, locala.jdField_new, locala.a, str, (ILocalizable)localObject5, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        throw new OFXException(((XferInstruction)localObject1).getStatusMsg(), 2000);
      }
      if (this.c >= 3)
      {
        localObject3 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
        localObject4 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
        localObject5 = BPWLocaleUtil.getLocalizedMessage(918, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4603, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject3, (String)localObject4, locala.jdField_new, locala.a, "MODIFIED", (ILocalizable)localObject5, paramTypeUserData._agentID, paramTypeUserData._agentType);
      }
      locala.jdField_if = 0;
      locala.d = "WILLPROCESSON";
      localTypeIntraTrnRsV11 = a(paramTypeTrnRqCm, paramTypeXferInfoAggregate, locala);
      ((XferInstruction)localObject1).Status = str;
      if ("WILLPROCESSON".equals(str) == true)
      {
        if (!locala.jdField_byte) {
          locala.e = DBUtil.getCurrentStartDate();
        }
        scheduleXferAddRq(paramFFSConnectionHolder, locala);
      }
      XferInstruction.modify(paramFFSConnectionHolder, locala.jdField_goto, (XferInstruction)localObject1);
      a(paramTypeUserData._privateTagContainer, paramFFSConnectionHolder, locala.jdField_goto, "Mod");
      Object localObject3 = SrvrTrans.findResponseBySrvrTID(locala.jdField_goto, paramFFSConnectionHolder);
      Object localObject4 = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
      Object localObject5 = (TypeIntraTrnRsV1)((BPWMsgBroker)localObject4).parseMsg(localObject3[1], "IntraTrnRsV1", "OFX200");
      if (localObject5 != null)
      {
        TypeIntraTrnRsV1 localTypeIntraTrnRsV12 = a(paramTypeTrnRqCm, paramTypeXferInfoAggregate, locala, ((TypeIntraTrnRsV1)localObject5).IntraTrnRs.IntraTrnRsUn.IntraRs.RecSrvrTID, paramTypeUserData, paramEnumCurrencyEnum);
        localAuditAgent.modXferRsV1(localTypeIntraTrnRsV12, (XferInstruction)localObject1, paramFFSConnectionHolder);
      }
    }
    catch (OFXException localOFXException)
    {
      throw localOFXException;
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.getMessage());
    }
    return localTypeIntraTrnRsV11;
  }
  
  private TypeIntraTrnRsV1 a(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    FFSDebug.log("XferProcessor2.processXferRqV1Canc: call", 6);
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = null;
    String str1 = null;
    String str2 = null;
    String str3 = null;
    String str4 = null;
    String str5 = "0.0";
    String str6 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("XferProcessor2.processXferRqV1Canc:Can not get DB Connection.");
    }
    try
    {
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm.TrnUID))
      {
        if (this.c >= 3)
        {
          localObject1 = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraCanRq.SrvrTID;
          localObject2 = XferInstruction.getXferInstruction(localFFSConnectionHolder, (String)localObject1);
          if (localObject2 != null)
          {
            str3 = ((XferInstruction)localObject2).BankID;
            str4 = ((XferInstruction)localObject2).fromBankID;
            str5 = ((XferInstruction)localObject2).Amount;
            str6 = ((XferInstruction)localObject2).CurDef;
            str1 = BPWUtil.getAccountIDWithAccountType(((XferInstruction)localObject2).AcctDebitID, ((XferInstruction)localObject2).AcctDebitType);
            str2 = BPWUtil.getAccountIDWithAccountType(((XferInstruction)localObject2).AcctCreditID, ((XferInstruction)localObject2).AcctCreditType);
            localObject2 = null;
          }
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(922, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4606, str5, str6, (String)localObject1, str1, str2, str3, str4, "DUPLICATE", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        localTypeIntraTrnRsV1 = a(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2019);
        Object localObject1 = localTypeIntraTrnRsV1;
        return localObject1;
      }
      localTypeIntraTrnRsV1 = processXferRqV1Canc(localFFSConnectionHolder, paramTypeUserData, paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqUn.IntraCanRq.SrvrTID, paramString, false);
      localFFSConnectionHolder.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject2 = "*** XferProcessor2.processXferRqV1Canc failed: ";
      FFSDebug.log(localException, (String)localObject2);
      localTypeIntraTrnRsV1 = a(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeIntraTrnRsV1;
  }
  
  public TypeIntraTrnRsV1 processXferRqV1Canc(FFSConnectionHolder paramFFSConnectionHolder, TypeUserData paramTypeUserData, TypeTrnRqCm paramTypeTrnRqCm, String paramString1, String paramString2, boolean paramBoolean)
    throws BPWException, OFXException
  {
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = null;
    String str1 = null;
    String str2 = null;
    a locala = new a(null);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("XferProcessor2.processXferRqV1Canc:AuditAgent is null!!");
    }
    locala.jdField_goto = paramString1;
    locala.d = "CANCELEDON";
    locala.jdField_long = paramString2;
    locala.h = DBUtil.getFIId(paramTypeUserData);
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    String str3 = paramBoolean ? "CancelIntraTransferFromScheduleOFX200" : "CancelIntraTransferOFX200";
    TransferInfo localTransferInfo = null;
    try
    {
      XferInstruction localXferInstruction = XferInstruction.getXferInstruction(paramFFSConnectionHolder, paramString1);
      if (localXferInstruction == null)
      {
        locala.jdField_if = 2018;
        locala.d = "FAILEDON";
        localTypeIntraTrnRsV1 = a(paramTypeTrnRqCm, locala);
        return localTypeIntraTrnRsV1;
      }
      if (localXferInstruction.Status.equals("CANCELEDON") == true)
      {
        locala.jdField_if = 0;
        locala.d = "CANCELEDON";
        localTypeIntraTrnRsV1 = a(paramTypeTrnRqCm, locala);
        return localTypeIntraTrnRsV1;
      }
      try
      {
        localTransferInfo = TransferIntraMap.mapXferInstToTransferInfo(localXferInstruction, "Current", "INTERNAL", "Cancel");
        localTransferCalloutHandlerBase.begin(localTransferInfo, str3);
      }
      catch (FFSException localFFSException)
      {
        try
        {
          localTransferCalloutHandlerBase.failure(localTransferInfo, str3);
        }
        catch (Throwable localThrowable1) {}
        FFSDebug.log(localTransferInfo.getStatusMsg(), 6);
        locala.jdField_if = 2000;
        locala.d = "FAILEDON";
        localTypeIntraTrnRsV1 = a(paramTypeTrnRqCm, locala);
        localTypeIntraTrnRsV1.IntraTrnRs.TrnRsCm.Status.Message = localTransferInfo.getStatusMsg();
        return localTypeIntraTrnRsV1;
      }
      try
      {
        int m = 1;
        int n = a(paramFFSConnectionHolder, locala);
        if (n == 0) {
          ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "INTRATRN", locala.jdField_goto);
        } else if ("APPROVAL_PENDING".equals(localXferInstruction.Status) != true) {
          if (("APPROVAL_REJECTED".equals(localXferInstruction.Status) == true) || ("APPROVAL_NOT_ALLOWED".equals(localXferInstruction.Status) == true))
          {
            m = 0;
          }
          else
          {
            locala.jdField_if = 2016;
            locala.d = "FAILEDON";
            try
            {
              localTransferInfo.setStatusCode(locala.jdField_if);
              localTransferInfo.setPrcStatus(locala.d);
              localTransferCalloutHandlerBase.failure(localTransferInfo, str3);
            }
            catch (Throwable localThrowable2) {}
            localTypeIntraTrnRsV1 = a(paramTypeTrnRqCm, locala);
            return localTypeIntraTrnRsV1;
          }
        }
        locala.jdField_if = 0;
        locala.d = "CANCELEDON";
        localTypeIntraTrnRsV1 = a(paramTypeTrnRqCm, locala);
        Object localObject1;
        Object localObject2;
        if (m == 1)
        {
          localObject1 = RecXferProcessor2.deleteLimit(paramFFSConnectionHolder, localXferInstruction, new HashMap(), "CANCELEDON", false);
          if (!"LIMIT_REVERT_SUCCEEDED".equals(localObject1))
          {
            if (this.c >= 3)
            {
              str1 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
              str2 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
              localObject2 = BPWLocaleUtil.getLocalizedMessage(402, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
              logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4605, localXferInstruction.Amount, localXferInstruction.CurDef, paramString1, str1, str2, localXferInstruction.BankID, localXferInstruction.fromBankID, (String)localObject1, (ILocalizable)localObject2, paramTypeUserData._agentID, paramTypeUserData._agentType);
            }
            throw new OFXException(localXferInstruction.getStatusMsg(), 2000);
          }
        }
        if (this.c >= 3)
        {
          str1 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
          str2 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
          localObject1 = BPWLocaleUtil.getLocalizedMessage(921, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4605, localXferInstruction.Amount, localXferInstruction.CurDef, paramString1, str1, str2, localXferInstruction.BankID, localXferInstruction.fromBankID, locala.d, (ILocalizable)localObject1, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        int i1 = XferInstruction.updateStatus(paramFFSConnectionHolder, locala.jdField_goto, locala.d);
        if (i1 <= 0)
        {
          FFSDebug.log("*** XferProcessor2.processXferRqV1Canc: Intra Transfer status is not updated!!  SrvrTID=" + locala.jdField_goto + " Status=" + locala.d + " Response=" + localTypeIntraTrnRsV1, 0);
          localObject2 = new StringWriter();
          new Exception("Stack Trace").printStackTrace(new PrintWriter((Writer)localObject2));
          String str4 = ((StringWriter)localObject2).toString();
          FFSDebug.log(str4, 0);
        }
        localAuditAgent.cancelOFX200XferRsV1(locala.jdField_goto, paramFFSConnectionHolder);
        try
        {
          localTransferInfo.setStatusCode(0);
          localTransferInfo.setPrcStatus("CANCELEDON");
          localTransferCalloutHandlerBase.end(localTransferInfo, str3);
        }
        catch (Exception localException4) {}
      }
      catch (Exception localException2)
      {
        try
        {
          localTransferInfo.setStatusCode(locala.jdField_if);
          localTransferInfo.setPrcStatus(locala.d);
          localTransferCalloutHandlerBase.failure(localTransferInfo, str3);
        }
        catch (Exception localException3) {}
        throw localException2;
      }
    }
    catch (OFXException localOFXException)
    {
      throw localOFXException;
    }
    catch (Exception localException1)
    {
      throw new BPWException(localException1.getMessage());
    }
    return localTypeIntraTrnRsV1;
  }
  
  private void a(TypeXferInfoAggregate paramTypeXferInfoAggregate, TypeUserData paramTypeUserData, a parama, String paramString, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    parama.jdField_void = paramString;
    parama.jdField_long = paramTypeUserData._tran_id;
    if (paramTypeXferInfoAggregate.BCCAcctFromUn.__memberName.equals("BankAcctFrom"))
    {
      parama.a = paramTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.BankID;
      parama.jdField_do = paramTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.BranchID;
      parama.b = paramTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.AcctID;
      parama.c = MsgBuilder.getAcctType(paramTypeXferInfoAggregate.BCCAcctFromUn.BankAcctFrom.AcctType);
      parama.jdField_new = parama.a;
      parama.jdField_else = parama.jdField_do;
    }
    else
    {
      parama.b = paramTypeXferInfoAggregate.BCCAcctFromUn.CCAcctFrom.AcctID;
      parama.c = "CREDITCARD";
    }
    if (paramTypeXferInfoAggregate.BCCAcctToUn.__memberName.equals("BankAcctTo"))
    {
      parama.jdField_new = paramTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.BankID;
      parama.jdField_else = paramTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.BranchID;
      parama.jdField_null = paramTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.AcctID;
      parama.f = MsgBuilder.getAcctType(paramTypeXferInfoAggregate.BCCAcctToUn.BankAcctTo.AcctType);
    }
    else
    {
      parama.jdField_null = paramTypeXferInfoAggregate.BCCAcctToUn.CCAcctTo.AcctID;
      parama.f = "CREDITCARD";
    }
    String str1 = String.valueOf(paramTypeXferInfoAggregate.TrnAmt);
    try
    {
      if (!FFSUtil.isPositive(str1)) {
        throw new OFXException("Invalid amount", 2012);
      }
      parama.jdField_char = str1;
    }
    catch (Exception localException1)
    {
      throw new OFXException("Invalid amount", 2012);
    }
    parama.jdField_for = BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum);
    parama.jdField_int = paramTypeUserData._uid;
    parama.h = DBUtil.getFIId(paramTypeUserData);
    parama.jdField_byte = paramTypeXferInfoAggregate.DtDueExists;
    int m = DBUtil.getCurrentStartDate();
    if (paramTypeXferInfoAggregate.DtDueExists)
    {
      String str3;
      if (paramTypeXferInfoAggregate.DtDue != null)
      {
        String str2 = paramTypeXferInfoAggregate.DtDue.substring(0, 8);
        if (BPWUtil.validateDate(str2, "yyyyMMdd") == true)
        {
          parama.e = BPWUtil.getDateDBFormat(str2);
          if (parama.e < m)
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
    else
    {
      parama.e = m;
    }
    try
    {
      int n = SmartCalendar.getPayday(parama.h, parama.e / 100, "BookTransfer");
      parama.g = (Integer.toString(n) + "000000");
    }
    catch (Exception localException2)
    {
      throw new OFXException("Date format not correct", 2000);
    }
    parama.jdField_goto = "0";
    parama.jdField_if = 0;
    parama.d = "WILLPROCESSON";
    parama.jdField_case = paramTypeUserData._submittedBy;
  }
  
  public void scheduleXferAddRq(FFSConnectionHolder paramFFSConnectionHolder, a parama)
    throws BPWException
  {
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = parama.h;
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = 10;
    localScheduleInfo.StartDate = parama.e;
    localScheduleInfo.LogID = parama.jdField_long;
    localScheduleInfo.InstanceCount = 1;
    try
    {
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "INTRATRN", parama.jdField_goto, localScheduleInfo, "BookTransfer");
    }
    catch (FFSException localFFSException)
    {
      str = "Exception in XferProcessor2.scheduleXferAddRq: " + localFFSException.getMessage();
      throw new BPWException(str, localFFSException.getErrCode());
    }
    catch (Exception localException)
    {
      String str = "Exception in XferProcessor2.scheduleXferAddRq: " + localException.getMessage();
      throw new BPWException(str);
    }
  }
  
  private int a(FFSConnectionHolder paramFFSConnectionHolder, a parama)
    throws BPWException
  {
    try
    {
      ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(parama.h, "INTRATRN", parama.jdField_goto, paramFFSConnectionHolder);
      if (localScheduleInfo == null) {
        return 1;
      }
      if (!localScheduleInfo.Status.equals("Active")) {
        return 3;
      }
    }
    catch (Exception localException)
    {
      String str = "Exception in XferProcessor2.getScheduleXferStatus: " + localException.getMessage();
      throw new BPWException(str);
    }
    return 0;
  }
  
  private IntraTrnRslt a(FFSConnectionHolder paramFFSConnectionHolder, a parama, HashMap paramHashMap)
    throws Exception
  {
    FFSDebug.log("XferProcessor2.doImmediateXferWithoutTimeout starts ....", 6);
    DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
    String str1 = DBConnCache.save(paramFFSConnectionHolder);
    boolean bool = false;
    IntraTrnInfo[] arrayOfIntraTrnInfo = { new IntraTrnInfo(parama.jdField_int, parama.jdField_new, parama.jdField_else, parama.b, parama.c, parama.jdField_null, parama.f, parama.jdField_char, parama.jdField_for, String.valueOf(parama.e), parama.jdField_goto, parama.jdField_long, Integer.toString(0), 1, false, str1, parama.jdField_void, "", parama.h, parama.a, parama.jdField_do) };
    arrayOfIntraTrnInfo[0].submittedBy = parama.jdField_case;
    if ((paramHashMap != null) && (paramHashMap.size() != 0)) {
      arrayOfIntraTrnInfo[0].extraFields = paramHashMap;
    }
    IntraTrnRslt localIntraTrnRslt = new IntraTrnRslt(arrayOfIntraTrnInfo[0].srvrTid, -1, arrayOfIntraTrnInfo[0].eventSequence, str1);
    try
    {
      ToBackend localToBackend = new ToBackend();
      try
      {
        localIntraTrnRslt = localToBackend.ProcessImmediateIntraTrn(arrayOfIntraTrnInfo[0]);
      }
      catch (NoSuchMethodException localNoSuchMethodException)
      {
        localIntraTrnRslt.status = localToBackend.ProcessIntraTrn(arrayOfIntraTrnInfo);
      }
    }
    catch (Throwable localThrowable)
    {
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("*** ToBackend direct call failed: " + str3);
      FFSDebug.console("*** ToBackend direct call failed: " + str3);
    }
    finally
    {
      if (localDBConnCache != null) {
        DBConnCache.unbind(str1);
      }
    }
    if (0 != localIntraTrnRslt.status)
    {
      parama.d = XferProcessor.readStatusFromCode(localIntraTrnRslt.status);
      FFSDebug.log("XferProcessor2.doImmediateXferWithoutTimeout", "Reverting limits for a failed transaction: ", parama.jdField_goto, 6);
      String str2 = RecXferProcessor2.deleteLimit(paramFFSConnectionHolder, XferInstruction.getXferInstruction(paramFFSConnectionHolder, parama.jdField_goto), new HashMap(), parama.d, true);
      FFSDebug.log("XferProcessor2.doImmediateXferWithoutTimeout", ": Reverted limits for a failed transaction. status ", str2, 6);
    }
    if (localIntraTrnRslt.status != -1)
    {
      parama.jdField_if = localIntraTrnRslt.status;
      bool = true;
    }
    FFSDebug.log("XferProcessor2.doImmediateXferWithoutTimeout complete isBackendDone: " + bool, 6);
    return localIntraTrnRslt;
  }
  
  private IntraTrnRslt jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, a parama, HashMap paramHashMap)
    throws Exception
  {
    FFSDebug.log("XferProcessor2.doImmediateXfer starts.... ", 6);
    boolean bool = false;
    IntraTrnRslt localIntraTrnRslt = null;
    for (int m = 0; m < this.g; m++)
    {
      String str = DBConnCache.save(paramFFSConnectionHolder);
      IntraTrnInfo[] arrayOfIntraTrnInfo = { new IntraTrnInfo(parama.jdField_int, parama.jdField_new, parama.jdField_else, parama.b, parama.c, parama.jdField_null, parama.f, parama.jdField_char, parama.jdField_for, String.valueOf(parama.e), parama.jdField_goto, parama.jdField_long, Integer.toString(0), 1, false, str, parama.jdField_void, "", parama.h, parama.a, parama.jdField_do) };
      arrayOfIntraTrnInfo[0].submittedBy = parama.jdField_case;
      if ((paramHashMap != null) && (paramHashMap.size() != 0)) {
        arrayOfIntraTrnInfo[0].extraFields = paramHashMap;
      }
      if (m > 0) {
        arrayOfIntraTrnInfo[0].possibleDuplicate = true;
      }
      localIntraTrnRslt = new IntraTrnRslt(arrayOfIntraTrnInfo[0].srvrTid, -1, arrayOfIntraTrnInfo[0].eventSequence, str);
      BackendStatus localBackendStatus = new BackendStatus(localIntraTrnRslt, false);
      MonitorBackend localMonitorBackend = new MonitorBackend(this.j);
      Thread localThread1 = new Thread(localMonitorBackend);
      k.add(localThread1);
      localThread1.start();
      ToBackendRunnable localToBackendRunnable = new ToBackendRunnable(arrayOfIntraTrnInfo, localBackendStatus, localMonitorBackend);
      Thread localThread2 = new Thread(localToBackendRunnable);
      i.add(localThread2);
      localThread2.start();
      try
      {
        localThread1.join();
        if ((localThread2 != null) && (localThread2.isAlive())) {
          try
          {
            localThread2.interrupt();
          }
          catch (Exception localException1) {}
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("XferProcessor2.doImmediateXfer: InterruptedException", 6);
      }
      finally
      {
        DBConnCache.unbind(str);
      }
      if (localBackendStatus.isProcessComplete())
      {
        localIntraTrnRslt = localBackendStatus.getResult();
        localIntraTrnRslt.srvrTid = parama.jdField_goto;
        parama.jdField_if = localIntraTrnRslt.status;
        bool = true;
        try
        {
          if (localThread2 != null) {
            i.remove(localThread2);
          }
          if (localThread1 != null) {
            k.remove(localThread1);
          }
        }
        catch (Exception localException3)
        {
          FFSDebug.log("XferProcessor2.doImmediateXfer Error: " + FFSDebug.stackTrace(localException3));
        }
      }
    }
    FFSDebug.log("XferProcessor2.doImmediateXfer complete isBackendDone: " + bool, 6);
    return localIntraTrnRslt;
  }
  
  public static void shutdown()
  {
    int m = k.size();
    int n = i.size();
    ListIterator localListIterator = k.listIterator();
    Thread localThread;
    while (localListIterator.hasNext())
    {
      localThread = (Thread)localListIterator.next();
      if (localThread != null) {
        try
        {
          localThread.interrupt();
        }
        catch (Exception localException1) {}
      }
    }
    k.clear();
    FFSDebug.log("XferProcessor2.shutdown: Removed monitor transfer threads: " + m, 6);
    localListIterator = i.listIterator();
    while (localListIterator.hasNext())
    {
      localThread = (Thread)localListIterator.next();
      if (localThread != null) {
        try
        {
          localThread.interrupt();
        }
        catch (Exception localException2) {}
      }
    }
    i.clear();
    FFSDebug.log("XferProcessor2.shutdown: Removed transfer threads: " + n, 6);
  }
  
  private TypeIntraTrnRsV1 a(TypeTrnRqCm paramTypeTrnRqCm, TypeXferInfoAggregate paramTypeXferInfoAggregate, a parama, String paramString, TypeUserData paramTypeUserData, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws Exception
  {
    TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsUnExists = true;
    TypeIntraTrnRsUn localTypeIntraTrnRsUn = new TypeIntraTrnRsUn();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn = localTypeIntraTrnRsUn;
    localTypeIntraTrnRsUn.__memberName = "IntraRs";
    TypeIntraRsAggregate localTypeIntraRsAggregate = new TypeIntraRsAggregate();
    localTypeIntraTrnRsUn.IntraRs = localTypeIntraRsAggregate;
    if (paramEnumCurrencyEnum != null) {
      localTypeIntraRsAggregate.CurDef = paramEnumCurrencyEnum;
    } else {
      localTypeIntraRsAggregate.CurDef = MsgBuilder.getOFX200CurrencyEnum(BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum));
    }
    localTypeIntraRsAggregate.SrvrTID = parama.jdField_goto;
    if (paramString == null)
    {
      localTypeIntraRsAggregate.RecSrvrTIDExists = false;
    }
    else
    {
      localTypeIntraRsAggregate.RecSrvrTIDExists = true;
      localTypeIntraRsAggregate.RecSrvrTID = paramString;
    }
    localTypeIntraRsAggregate.XferInfo = paramTypeXferInfoAggregate;
    localTypeIntraRsAggregate.IntraRsDateUnExists = false;
    localTypeIntraRsAggregate.XferPrcStsExists = true;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new TypeXferPrcStsAggregate();
    localTypeIntraRsAggregate.XferPrcSts = localTypeXferPrcStsAggregate;
    int m = Integer.parseInt(parama.g.substring(0, 8));
    String str = SmartCalendar.getPayday(parama.h, m, "BookTransfer") + "000000";
    RsCmBuilder.updateRsXferPrcSts(parama.d, parama.g, localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs.XferPrcSts);
    RsCmBuilder.updateXferRsDateXferPrj(str, localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraRs);
    localTypeIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, parama.jdField_if);
    return new TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
  }
  
  private TypeIntraTrnRsV1 a(TypeTrnRqCm paramTypeTrnRqCm, TypeXferInfoAggregate paramTypeXferInfoAggregate, a parama)
    throws Exception
  {
    TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsUnExists = true;
    TypeIntraTrnRsUn localTypeIntraTrnRsUn = new TypeIntraTrnRsUn();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn = localTypeIntraTrnRsUn;
    localTypeIntraTrnRsUn.__memberName = "IntraModRs";
    TypeIntraModRsAggregate localTypeIntraModRsAggregate = new TypeIntraModRsAggregate();
    localTypeIntraTrnRsUn.IntraModRs = localTypeIntraModRsAggregate;
    localTypeIntraModRsAggregate.SrvrTID = parama.jdField_goto;
    localTypeIntraModRsAggregate.XferInfo = paramTypeXferInfoAggregate;
    localTypeIntraModRsAggregate.CurDefExists = true;
    localTypeIntraModRsAggregate.CurDef = MsgBuilder.getOFX200CurrencyEnum(parama.jdField_for);
    localTypeIntraModRsAggregate.XferPrcStsExists = true;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new TypeXferPrcStsAggregate();
    localTypeIntraModRsAggregate.XferPrcSts = localTypeXferPrcStsAggregate;
    RsCmBuilder.updateRsXferPrcSts(parama.d, parama.g, localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn.IntraModRs.XferPrcSts);
    localTypeIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, parama.jdField_if);
    return new TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
  }
  
  private TypeIntraTrnRsV1 a(TypeTrnRqCm paramTypeTrnRqCm, a parama)
  {
    TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsUnExists = true;
    TypeIntraTrnRsUn localTypeIntraTrnRsUn = new TypeIntraTrnRsUn();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsUn = localTypeIntraTrnRsUn;
    localTypeIntraTrnRsUn.__memberName = "IntraCanRs";
    TypeIntraCanRsAggregate localTypeIntraCanRsAggregate = new TypeIntraCanRsAggregate();
    localTypeIntraTrnRsUn.IntraCanRs = localTypeIntraCanRsAggregate;
    localTypeIntraCanRsAggregate.SrvrTID = parama.jdField_goto;
    localTypeIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, parama.jdField_if);
    return new TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
  }
  
  private TypeIntraTrnRsV1 a(TypeTrnRqCm paramTypeTrnRqCm, int paramInt)
  {
    TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsUnExists = false;
    localTypeIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt);
    return new TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
  }
  
  private TypeIntraTrnRsV1 jdMethod_if(TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
    throws BPWException
  {
    try
    {
      TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
      localTypeIntraTrnRsV1Aggregate.IntraTrnRsUnExists = false;
      localTypeIntraTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt, paramString);
      return new TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
    }
    catch (Exception localException)
    {
      String str = FFSDebug.stackTrace(localException);
      FFSDebug.log("XferProcessor2.buildXferErrorRs: failed", 0);
      throw new BPWException(str);
    }
  }
  
  public IntraTrnInfo getIntraById(String paramString)
    throws BPWException
  {
    FFSDebug.log("XferProcessor2.getIntraById : start", 6);
    IntraTrnInfo localIntraTrnInfo1 = null;
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      localIntraTrnInfo1 = new IntraTrnInfo();
      localIntraTrnInfo1.statusCode = 16504;
      localIntraTrnInfo1.statusMsg = BPWLocaleUtil.getMessage(16504, new String[] { "ID " }, "API_MESSAGE");
      FFSDebug.log("XferProcessor2.getIntraById failed : null or empty ID passed", 0);
      return localIntraTrnInfo1;
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("XferProcessor2.getIntraById: failed to get DB Connection ");
    }
    try
    {
      localIntraTrnInfo1 = XferInstruction.getIntraById(paramString, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      IntraTrnInfo localIntraTrnInfo2 = localIntraTrnInfo1;
      return localIntraTrnInfo2;
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "XferProcessor2.getIntraById: failed ";
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new BPWException(str + localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public IntraTrnInfo[] getIntraById(String[] paramArrayOfString)
    throws BPWException
  {
    FFSDebug.log("XferProcessor2.getIntraById (multiple): start", 6);
    IntraTrnInfo localIntraTrnInfo = null;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localIntraTrnInfo = new IntraTrnInfo();
      localIntraTrnInfo.statusCode = 16504;
      localIntraTrnInfo.statusMsg = BPWLocaleUtil.getMessage(16504, new String[] { "ID[] " }, "API_MESSAGE");
      FFSDebug.log("XferProcessor2.getIntraById (multiple): failed  null or empty ID array passed", 0);
      localObject1 = new IntraTrnInfo[] { localIntraTrnInfo };
      return localObject1;
    }
    Object localObject1 = new FFSConnectionHolder();
    ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
    if (((FFSConnectionHolder)localObject1).conn == null) {
      throw new BPWException("XferProcessor2.getIntraById (multiple): failed to get DB Connection ");
    }
    try
    {
      IntraTrnInfo[] arrayOfIntraTrnInfo = XferInstruction.getIntraById(paramArrayOfString, (FFSConnectionHolder)localObject1);
      ((FFSConnectionHolder)localObject1).conn.commit();
      localObject2 = arrayOfIntraTrnInfo;
      return localObject2;
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      Object localObject2 = "XferProcessor2.getIntraById (multiple): failed ";
      FFSDebug.log((String)localObject2 + FFSDebug.stackTrace(localException), 0);
      throw new BPWException((String)localObject2 + localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
  }
  
  public IntraTrnInfo[] getIntraByRecSrvrTId(String[] paramArrayOfString, boolean paramBoolean)
    throws BPWException
  {
    FFSDebug.log("XferProcessor2.getIntraByRecSrvrTId (multiple): start", 6);
    IntraTrnInfo localIntraTrnInfo = null;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localIntraTrnInfo = new IntraTrnInfo();
      localIntraTrnInfo.statusCode = 16504;
      localIntraTrnInfo.statusMsg = BPWLocaleUtil.getMessage(16504, new String[] { "ID[] " }, "API_MESSAGE");
      FFSDebug.log("XferProcessor2.getIntraByRecSrvrTId (multiple): failed  null or empty ID array passed", 0);
      localObject1 = new IntraTrnInfo[] { localIntraTrnInfo };
      return localObject1;
    }
    Object localObject1 = new FFSConnectionHolder();
    ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
    if (((FFSConnectionHolder)localObject1).conn == null) {
      throw new BPWException("XferProcessor2.getIntraByRecSrvrTId (multiple): failed to get DB Connection ");
    }
    try
    {
      IntraTrnInfo[] arrayOfIntraTrnInfo = XferInstruction.getIntraByRecSrvrTId(paramArrayOfString, paramBoolean, (FFSConnectionHolder)localObject1);
      ((FFSConnectionHolder)localObject1).conn.commit();
      localObject2 = arrayOfIntraTrnInfo;
      return localObject2;
    }
    catch (Exception localException)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      Object localObject2 = "XferProcessor2.getIntraByRecSrvrTId (multiple): failed ";
      FFSDebug.log((String)localObject2 + FFSDebug.stackTrace(localException), 0);
      throw new BPWException((String)localObject2 + localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
  }
  
  public static void logTransAuditLog(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, ILocalizable paramILocalizable, String paramString12, String paramString13)
    throws FFSException
  {
    BigDecimal localBigDecimal = FFSUtil.getBigDecimal(paramString4);
    logTransAuditLog(paramFFSConnectionHolder, paramString1, paramString2, paramString3, paramInt, localBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramILocalizable, paramString12, paramString13);
  }
  
  public static void logTransAuditLog(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, int paramInt, BigDecimal paramBigDecimal, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, ILocalizable paramILocalizable, String paramString11, String paramString12)
    throws FFSException
  {
    int m = 0;
    try
    {
      m = Integer.parseInt(paramString2);
    }
    catch (Exception localException)
    {
      String str = "XferProcessor2: businessId not valid, should be represented as integer";
      FFSDebug.log(str, 0);
      throw new FFSException(str);
    }
    TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), paramString1, paramString11, paramString12, paramILocalizable, paramString3, paramInt, m, paramBigDecimal, paramString4, paramString5, paramString10, paramString7, paramString8, paramString6, paramString9, 0);
  }
  
  public static void logTransAuditLogError(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, ILocalizable paramILocalizable, String paramString12, String paramString13)
    throws FFSException
  {
    String str = "XferProcessor2.logTransAuditLogError";
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      FFSDebug.log(str + ":starts");
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str + ":Can not get DB Connection.");
      }
      logTransAuditLog(localFFSConnectionHolder, paramString1, paramString2, paramString3, paramInt, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramILocalizable, paramString12, paramString13);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      FFSDebug.log(str + ":has failed" + FFSDebug.stackTrace(localException), 0);
      if (localFFSConnectionHolder.conn != null) {
        localFFSConnectionHolder.conn.rollback();
      }
      throw new FFSException(localException.toString());
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        try
        {
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable)
        {
          FFSDebug.log(str, " Failed to free the connection ", 0);
        }
      }
    }
  }
  
  private void a(Hashtable paramHashtable, FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    String str = null;
    try
    {
      FFSDebug.log("XferProcessor2.processXtraInfo start: ", 6);
      if (paramString2.equals("Add"))
      {
        if ((paramHashtable != null) && (!paramHashtable.isEmpty())) {
          BPWXferExtraInfo.insertHashtable(paramString1, paramHashtable, paramFFSConnectionHolder);
        }
      }
      else if (paramString2.equals("Mod")) {
        if ((paramHashtable != null) && (!paramHashtable.isEmpty())) {
          BPWXferExtraInfo.updateHashtable(paramString1, paramHashtable, paramFFSConnectionHolder);
        } else {
          BPWXferExtraInfo.deleteAllWithSrvrTID(paramString1, paramFFSConnectionHolder);
        }
      }
    }
    catch (Exception localException)
    {
      str = "XferProcessor2.processXtraInfo : failed ";
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new BPWException(str + localException.toString());
    }
    FFSDebug.log("XferProcessor2.processXtraInfo end: ", 6);
  }
  
  private class a
  {
    public String jdField_new;
    public String a;
    public String jdField_else;
    public String jdField_do;
    public String b;
    public String c;
    public String jdField_null;
    public String f;
    public String jdField_char;
    public String jdField_for;
    public String jdField_int;
    public String h;
    public int e;
    public boolean jdField_byte;
    public String jdField_long;
    public String jdField_goto;
    public String g;
    public String d;
    public int jdField_try;
    public int jdField_if;
    public String jdField_void;
    public String jdField_case;
    
    private a() {}
    
    a(XferProcessor2.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.XferProcessor2
 * JD-Core Version:    0.7.0.1
 */