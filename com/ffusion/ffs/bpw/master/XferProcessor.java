package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
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
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBCCAcctFromV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBCCAcctToV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctFromV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeBankAcctToV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCAcctFromAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeCCAcctToAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraCanRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraCanRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraModRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraModRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRqV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeIntraTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRqCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeTrnRsV1Cm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferInfoV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX151.TypeXferPrcStsAggregate;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

public class XferProcessor
  implements FFSConst, DBConsts, OFXConsts, BPWResource
{
  private PropertyConfig S = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
  private int aa = 2000;
  private int X = 3;
  private boolean Y = false;
  private boolean U = false;
  private int T = 1;
  private static List ab = new Vector();
  private static List Z = new Vector();
  private boolean W = false;
  private boolean V = false;
  
  public XferProcessor()
  {
    String str = this.S.otherProperties.getProperty("intra.immediate.supportTimeout", "false");
    try
    {
      this.W = (str.equalsIgnoreCase("true"));
    }
    catch (Throwable localThrowable)
    {
      this.W = false;
    }
    this.T = this.S.LogLevel;
    this.V = this.S.getEnforceEnrollment();
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
      if ((this.V) && (!Customer.validCustomer(paramTypeUserData._uid)))
      {
        String str1 = "This customer, customerID=" + paramTypeUserData._uid + ", is not allowed to process transactions " + "on this server.  Either the customer " + "is not enrolled or is inactive.";
        FFSDebug.log(str1, 0);
        throw new BPWException(str1);
      }
    }
    catch (Exception localException1)
    {
      str2 = "*** XferProcessor.processIntraTrnRqV1 failed:";
      FFSDebug.log(str2 + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = null;
    try
    {
      str2 = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.__memberName;
      if (str2.equals("IntraRq")) {
        localTypeIntraTrnRsV1 = jdMethod_do(paramTypeIntraTrnRqV1, paramTypeUserData, paramString);
      } else if (str2.equals("IntraModRq")) {
        localTypeIntraTrnRsV1 = a(paramTypeIntraTrnRqV1, paramTypeUserData, paramString);
      } else if (str2.equals("IntraCanRq")) {
        localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1, paramTypeUserData, paramString);
      } else {
        throw new BPWException("Wrong request type!");
      }
    }
    catch (Exception localException2)
    {
      String str3 = "*** XferProcessor.processIntraTrnRqV1 failed:";
      FFSDebug.log(str3 + localException2.toString());
      localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000);
    }
    return localTypeIntraTrnRsV1;
  }
  
  private TypeIntraTrnRsV1 jdMethod_do(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    FFSDebug.log("XferProcessor.processXferRqV1: call", 6);
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("XferProcessor.processXferRqV1:Can not get DB Connection.");
    }
    TypeXferInfoV1Aggregate localTypeXferInfoV1Aggregate = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraRq.XferInfo;
    EnumCurrencyEnum localEnumCurrencyEnum = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraRq.CurDef;
    try
    {
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm.TrnUID))
      {
        if (this.T >= 3)
        {
          localObject1 = new a(null);
          a(localTypeXferInfoV1Aggregate, paramTypeUserData, (a)localObject1, null, localEnumCurrencyEnum);
          str1 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).b, ((a)localObject1).c);
          String str2 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).jdField_null, ((a)localObject1).f);
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(914, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          XferProcessor2.logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4601, ((a)localObject1).jdField_char, ((a)localObject1).jdField_for, ((a)localObject1).jdField_goto, str1, str2, ((a)localObject1).jdField_new, ((a)localObject1).a, "DUPLICATE", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2019);
        Object localObject1 = localTypeIntraTrnRsV1;
        return localObject1;
      }
      localTypeIntraTrnRsV1 = processXferRqV1(localFFSConnectionHolder, localTypeXferInfoV1Aggregate, paramTypeUserData, paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, null, paramString, localEnumCurrencyEnum);
      localFFSConnectionHolder.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "*** XferProcessor.processXferRqV1 failed: ";
      FFSDebug.log(localOFXException, str1);
      localTypeIntraTrnRsV1 = jdMethod_for(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "*** XferProcessor.processXferRqV1 failed: ";
      FFSDebug.log(localBPWException, str1);
      if (localBPWException.getErrorCode() == -1) {
        localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000);
      } else {
        localTypeIntraTrnRsV1 = jdMethod_for(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str1 = "*** XferProcessor.processXferRqV1 failed: ";
      FFSDebug.log(localException, str1);
      localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeIntraTrnRsV1;
  }
  
  public TypeIntraTrnRsV1 processXferRqV1(FFSConnectionHolder paramFFSConnectionHolder, TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, TypeUserData paramTypeUserData, TypeTrnRqCm paramTypeTrnRqCm, String paramString1, String paramString2, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = null;
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("XferProcessor.processXferRqV1:AuditAgent is null!!");
    }
    HashMap localHashMap = new HashMap();
    a locala = new a(null);
    a(paramTypeXferInfoV1Aggregate, paramTypeUserData, locala, paramString1, paramEnumCurrencyEnum);
    Object localObject1 = "WILLPROCESSON";
    Object localObject3;
    Object localObject4;
    Object localObject5;
    Object localObject6;
    Object localObject7;
    try
    {
      String str = paramString2;
      if ((paramString1 != null) && (paramString1.length() > 0))
      {
        str = BPWTrackingIDGenerator.getNextId();
        locala.jdField_long = str;
      }
      localObject3 = new XferInstruction("", locala.jdField_int, locala.h, locala.jdField_char, locala.jdField_for, locala.jdField_new, locala.jdField_else, locala.b, locala.c, locala.jdField_null, locala.f, "", String.valueOf(locala.e), "WILLPROCESSON", str, paramString1, paramTypeUserData._submittedBy, "", locala.a, locala.jdField_do);
      FFSDebug.log("XferProcessor.processXferRqV1", ": Checking for limits for transaction. srvrTID: ", locala.jdField_goto, 6);
      locala.jdField_goto = XferInstruction.create(paramFFSConnectionHolder, (XferInstruction)localObject3);
      ((XferInstruction)localObject3).SrvrTID = locala.jdField_goto;
      localObject4 = null;
      if ((paramTypeUserData._privateTagContainer != null) && (!paramTypeUserData._privateTagContainer.isEmpty())) {
        localObject4 = BPWUtil.convertHashTableToMap(paramTypeUserData._privateTagContainer);
      }
      ((XferInstruction)localObject3).extraFields = localObject4;
      localObject5 = RecXferProcessor2.doLimitChecking(paramFFSConnectionHolder, (XferInstruction)localObject3, localHashMap, false);
      LocalizableString localLocalizableString;
      if ((!"WILLPROCESSON".equals(localObject5)) && (!"APPROVAL_PENDING".equals(localObject5)))
      {
        if (this.T >= 3)
        {
          localObject6 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
          localObject7 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
          localLocalizableString = BPWLocaleUtil.getLocalizedMessage(401, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          XferProcessor2.logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4600, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject6, (String)localObject7, locala.jdField_new, locala.a, (String)localObject5, localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        throw new OFXException(((XferInstruction)localObject3).getStatusMsg(), 2000);
      }
      ((XferInstruction)localObject3).Status = ((String)localObject5);
      XferInstruction.updateStatus(paramFFSConnectionHolder, locala.jdField_goto, ((XferInstruction)localObject3).Status);
      jdMethod_for(paramTypeUserData._privateTagContainer, paramFFSConnectionHolder, locala.jdField_goto, "Add");
      if (this.T >= 3)
      {
        localObject6 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
        localObject7 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
        localLocalizableString = BPWLocaleUtil.getLocalizedMessage(913, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        XferProcessor2.logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4600, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject6, (String)localObject7, locala.jdField_new, locala.a, (String)localObject5, localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
      }
      if (!"FAILEDON".equals(localObject1)) {
        locala.d = "WILLPROCESSON";
      } else {
        locala.d = "FAILEDON";
      }
      localObject1 = localObject5;
      localTypeIntraTrnRsV1 = a(paramTypeTrnRqCm, paramTypeXferInfoV1Aggregate, locala, paramString1, paramTypeUserData, paramEnumCurrencyEnum);
      localAuditAgent.saveXferRsV1(localTypeIntraTrnRsV1, locala.jdField_int, paramFFSConnectionHolder, locala.d);
      if ("WILLPROCESSON".equalsIgnoreCase((String)localObject1)) {
        if (paramTypeXferInfoV1Aggregate.DtDueExists)
        {
          a(paramFFSConnectionHolder, locala);
        }
        else if (this.Y)
        {
          locala.e = DBUtil.getCurrentStartDate();
          a(paramFFSConnectionHolder, locala);
        }
      }
      paramFFSConnectionHolder.conn.commit();
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
    if ((!paramTypeXferInfoV1Aggregate.DtDueExists) && (!this.Y) && ("WILLPROCESSON".equalsIgnoreCase((String)localObject1))) {
      try
      {
        XferInstruction.updateStatus(paramFFSConnectionHolder, locala.jdField_goto, "IMMED_INPROCESS");
        if (this.T >= 4)
        {
          localObject2 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
          localObject3 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
          localObject4 = BPWLocaleUtil.getLocalizedMessage(915, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          XferProcessor2.logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4607, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject2, (String)localObject3, locala.jdField_new, locala.a, "IMMED_INPROCESS", (ILocalizable)localObject4, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        paramFFSConnectionHolder.conn.commit();
        Object localObject2 = new IntraTrnRslt();
        localObject3 = null;
        if ((paramTypeUserData._privateTagContainer != null) && (!paramTypeUserData._privateTagContainer.isEmpty())) {
          localObject3 = BPWUtil.convertHashTableToMap(paramTypeUserData._privateTagContainer);
        }
        if (this.W)
        {
          FFSDebug.log("XferProcessor.processXferRqV1 Calling doImmediateXfer", 6);
          localObject2 = jdMethod_if(paramFFSConnectionHolder, locala, (HashMap)localObject3);
        }
        else
        {
          FFSDebug.log("XferProcessor.processXferRqV1 Calling doImmediateXferWithoutTimeout", 6);
          localObject2 = a(paramFFSConnectionHolder, locala, (HashMap)localObject3);
        }
        if (((IntraTrnRslt)localObject2).status != -1)
        {
          localObject4 = new BackendProcessor();
          ((BackendProcessor)localObject4).processImmIntraTrnRslt(paramFFSConnectionHolder, (IntraTrnRslt)localObject2, locala.h);
          localObject5 = SrvrTrans.findResponseBySrvrTID(locala.jdField_goto, paramFFSConnectionHolder);
          localObject6 = localObject5[1];
          localObject7 = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
          localTypeIntraTrnRsV1 = (TypeIntraTrnRsV1)((BPWMsgBroker)localObject7).parseMsg((String)localObject6, "IntraTrnRsV1", "OFX151");
        }
        else
        {
          FFSDebug.log("Process IntraXfer failed at the backend.");
          if (this.U)
          {
            RsCmBuilder.updateXferRsDatePosted(locala.g, localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs);
            localAuditAgent.updateXferRsV1(localTypeIntraTrnRsV1, paramFFSConnectionHolder, "WILLPROCESSON");
            a(paramFFSConnectionHolder, locala);
            if (this.T >= 4)
            {
              localObject4 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
              localObject5 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
              localObject6 = BPWLocaleUtil.getLocalizedMessage(916, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
              XferProcessor2.logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4607, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject4, (String)localObject5, locala.jdField_new, locala.a, "WILLPROCESSON", (ILocalizable)localObject6, paramTypeUserData._agentID, paramTypeUserData._agentType);
            }
          }
          else
          {
            locala.jdField_if = 2000;
            locala.d = "FAILEDON";
            if (this.T >= 1)
            {
              localObject4 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
              localObject5 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
              localObject6 = BPWLocaleUtil.getLocalizedMessage(917, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
              XferProcessor2.logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4607, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject4, (String)localObject5, locala.jdField_new, locala.a, locala.d, (ILocalizable)localObject6, paramTypeUserData._agentID, paramTypeUserData._agentType);
            }
            RsCmBuilder.updateRsXferPrcSts(locala.d, locala.g, localTypeIntraTrnRsV1.IntraTrnRs.IntraTrnRsV1Un.IntraRs.XferPrcSts);
            RsCmBuilder.updateStatusV1Aggregate(localTypeIntraTrnRsV1.IntraTrnRs.TrnRsV1Cm.Status, locala.jdField_if);
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
  
  private TypeIntraTrnRsV1 a(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    FFSDebug.log("XferProcessor.processXferRqV1Mod: call", 6);
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("XferProcessor.processXferRqV1Mod:Can not get DB Connection.");
    }
    TypeXferInfoV1Aggregate localTypeXferInfoV1Aggregate = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraModRq.XferInfo;
    EnumCurrencyEnum localEnumCurrencyEnum = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraModRq.CurDef;
    try
    {
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm.TrnUID))
      {
        if (this.T >= 3)
        {
          localObject1 = new a(null);
          a(localTypeXferInfoV1Aggregate, paramTypeUserData, (a)localObject1, null, localEnumCurrencyEnum);
          str1 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).b, ((a)localObject1).c);
          String str2 = BPWUtil.getAccountIDWithAccountType(((a)localObject1).jdField_null, ((a)localObject1).f);
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(919, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          XferProcessor2.logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4604, ((a)localObject1).jdField_char, ((a)localObject1).jdField_for, ((a)localObject1).jdField_goto, str1, str2, ((a)localObject1).jdField_new, ((a)localObject1).a, "DUPLICATE", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2019);
        Object localObject1 = localTypeIntraTrnRsV1;
        return localObject1;
      }
      localTypeIntraTrnRsV1 = processXferRqV1Mod(localFFSConnectionHolder, localTypeXferInfoV1Aggregate, paramTypeUserData, paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraModRq.SrvrTID, null, paramString, localEnumCurrencyEnum);
      localFFSConnectionHolder.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "*** XferProcessor.processXferRqV1Mod failed: ";
      FFSDebug.log(localOFXException, str1);
      localTypeIntraTrnRsV1 = jdMethod_for(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder.conn.rollback();
      str1 = "*** XferProcessor.processXferRqV1Mod failed: ";
      FFSDebug.log(localBPWException, str1);
      if (localBPWException.getErrorCode() == -1) {
        localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000);
      } else {
        localTypeIntraTrnRsV1 = jdMethod_for(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str1 = "*** XferProcessor.processXferRqV1Mod failed: ";
      FFSDebug.log(localException, str1);
      localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeIntraTrnRsV1;
  }
  
  public TypeIntraTrnRsV1 processXferRqV1Mod(FFSConnectionHolder paramFFSConnectionHolder, TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, TypeUserData paramTypeUserData, TypeTrnRqCm paramTypeTrnRqCm, String paramString1, String paramString2, String paramString3, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    TypeIntraTrnRsV1 localTypeIntraTrnRsV11 = null;
    a locala = new a(null);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("XferProcessor.processXferRqV1Mod:AuditAgent is null!!");
    }
    a(paramTypeXferInfoV1Aggregate, paramTypeUserData, locala, paramString2, paramEnumCurrencyEnum);
    locala.jdField_goto = paramString1;
    try
    {
      XferInstruction localXferInstruction = XferInstruction.getXferInstruction(paramFFSConnectionHolder, paramString1);
      if (localXferInstruction == null)
      {
        locala.jdField_if = 2018;
        locala.d = "FAILEDON";
        localTypeIntraTrnRsV11 = a(paramTypeTrnRqCm, paramTypeXferInfoV1Aggregate, locala);
        return localTypeIntraTrnRsV11;
      }
      if (localXferInstruction.Status.equals("CANCELEDON") == true)
      {
        locala.jdField_if = 2017;
        locala.d = "FAILEDON";
        localTypeIntraTrnRsV11 = a(paramTypeTrnRqCm, paramTypeXferInfoV1Aggregate, locala);
        return localTypeIntraTrnRsV11;
      }
      int i = 1;
      int j = jdMethod_if(paramFFSConnectionHolder, locala);
      if (j == 0) {
        ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "INTRATRN", locala.jdField_goto);
      } else if ("APPROVAL_PENDING".equals(localXferInstruction.Status) != true) {
        if (("APPROVAL_REJECTED".equals(localXferInstruction.Status) == true) || ("APPROVAL_NOT_ALLOWED".equals(localXferInstruction.Status) == true))
        {
          i = 0;
        }
        else
        {
          locala.jdField_if = 2016;
          locala.d = "FAILEDON";
          localTypeIntraTrnRsV11 = a(paramTypeTrnRqCm, paramTypeXferInfoV1Aggregate, locala);
          return localTypeIntraTrnRsV11;
        }
      }
      if (i == 1)
      {
        localObject1 = RecXferProcessor2.deleteLimit(paramFFSConnectionHolder, localXferInstruction, new HashMap(), "CANCELEDON", false);
        if (!"LIMIT_REVERT_SUCCEEDED".equals(localObject1))
        {
          if (this.T >= 3)
          {
            localObject2 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
            str = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
            localObject3 = BPWLocaleUtil.getLocalizedMessage(402, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
            XferProcessor2.logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4603, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject2, str, locala.jdField_new, locala.a, (String)localObject1, (ILocalizable)localObject3, paramTypeUserData._agentID, paramTypeUserData._agentType);
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
        if (this.T >= 3)
        {
          localObject3 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
          localObject4 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
          localObject5 = BPWLocaleUtil.getLocalizedMessage(401, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          XferProcessor2.logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4603, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject3, (String)localObject4, locala.jdField_new, locala.a, str, (ILocalizable)localObject5, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        throw new OFXException(((XferInstruction)localObject1).getStatusMsg(), 2000);
      }
      if (this.T >= 3)
      {
        localObject3 = BPWUtil.getAccountIDWithAccountType(locala.b, locala.c);
        localObject4 = BPWUtil.getAccountIDWithAccountType(locala.jdField_null, locala.f);
        localObject5 = BPWLocaleUtil.getLocalizedMessage(918, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        XferProcessor2.logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4603, locala.jdField_char, locala.jdField_for, locala.jdField_goto, (String)localObject3, (String)localObject4, locala.jdField_new, locala.a, "MODIFIED", (ILocalizable)localObject5, paramTypeUserData._agentID, paramTypeUserData._agentType);
      }
      locala.jdField_if = 0;
      locala.d = "WILLPROCESSON";
      localTypeIntraTrnRsV11 = a(paramTypeTrnRqCm, paramTypeXferInfoV1Aggregate, locala);
      ((XferInstruction)localObject1).Status = str;
      if ("WILLPROCESSON".equals(str) == true)
      {
        if (!locala.jdField_byte) {
          locala.e = DBUtil.getCurrentStartDate();
        }
        a(paramFFSConnectionHolder, locala);
      }
      XferInstruction.modify(paramFFSConnectionHolder, locala.jdField_goto, (XferInstruction)localObject1);
      jdMethod_for(paramTypeUserData._privateTagContainer, paramFFSConnectionHolder, locala.jdField_goto, "Mod");
      Object localObject3 = SrvrTrans.findResponseBySrvrTID(locala.jdField_goto, paramFFSConnectionHolder);
      Object localObject4 = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
      Object localObject5 = (TypeIntraTrnRsV1)((BPWMsgBroker)localObject4).parseMsg(localObject3[1], "IntraTrnRsV1", "OFX151");
      if (localObject5 != null)
      {
        TypeIntraTrnRsV1 localTypeIntraTrnRsV12 = a(paramTypeTrnRqCm, paramTypeXferInfoV1Aggregate, locala, ((TypeIntraTrnRsV1)localObject5).IntraTrnRs.IntraTrnRsV1Un.IntraRs.RecSrvrTID, paramTypeUserData, paramEnumCurrencyEnum);
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
  
  private TypeIntraTrnRsV1 jdMethod_if(TypeIntraTrnRqV1 paramTypeIntraTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws BPWException
  {
    String str1 = null;
    String str2 = null;
    String str3 = "0.0";
    String str4 = null;
    String str5 = null;
    String str6 = null;
    FFSDebug.log("XferProcessor.processXferRqV1Canc: call", 6);
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("XferProcessor.processXferRqV1Canc:Can not get DB Connection.");
    }
    try
    {
      if (Trans.checkDuplicateTIDAndSaveTID(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm.TrnUID))
      {
        if (this.T >= 3)
        {
          localObject1 = paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraCanRq.SrvrTID;
          localObject2 = XferInstruction.getXferInstruction(localFFSConnectionHolder, (String)localObject1);
          if (localObject2 != null)
          {
            str1 = BPWUtil.getAccountIDWithAccountType(((XferInstruction)localObject2).AcctDebitID, ((XferInstruction)localObject2).AcctDebitType);
            str2 = BPWUtil.getAccountIDWithAccountType(((XferInstruction)localObject2).AcctCreditID, ((XferInstruction)localObject2).AcctCreditType);
            str3 = ((XferInstruction)localObject2).Amount;
            str4 = ((XferInstruction)localObject2).CurDef;
            str5 = ((XferInstruction)localObject2).BankID;
            str6 = ((XferInstruction)localObject2).fromBankID;
            localObject2 = null;
          }
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(922, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          XferProcessor2.logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, paramTypeUserData._tran_id, 4606, str3, str4, (String)localObject1, str1, str2, str5, str6, "DUPLICATE", localLocalizableString, paramTypeUserData._agentID, paramTypeUserData._agentType);
        }
        localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2019);
        Object localObject1 = localTypeIntraTrnRsV1;
        return localObject1;
      }
      localTypeIntraTrnRsV1 = processXferRqV1Canc(localFFSConnectionHolder, paramTypeUserData, paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, paramTypeIntraTrnRqV1.IntraTrnRq.IntraTrnRqV1Un.IntraCanRq.SrvrTID, paramString);
      localFFSConnectionHolder.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      localTypeIntraTrnRsV1 = jdMethod_for(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject2 = "*** XferProcessor.processXferRqV1Canc failed: ";
      FFSDebug.log(localException, (String)localObject2);
      localTypeIntraTrnRsV1 = jdMethod_if(paramTypeIntraTrnRqV1.IntraTrnRq.TrnRqCm, 2000);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return localTypeIntraTrnRsV1;
  }
  
  public TypeIntraTrnRsV1 processXferRqV1Canc(FFSConnectionHolder paramFFSConnectionHolder, TypeUserData paramTypeUserData, TypeTrnRqCm paramTypeTrnRqCm, String paramString1, String paramString2)
    throws BPWException, OFXException
  {
    String str1 = null;
    String str2 = null;
    TypeIntraTrnRsV1 localTypeIntraTrnRsV1 = null;
    a locala = new a(null);
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("XferProcessor.processXferRqV1Canc:AuditAgent is null!!");
    }
    locala.jdField_goto = paramString1;
    locala.d = "CANCELEDON";
    locala.jdField_long = paramString2;
    locala.h = DBUtil.getFIId(paramTypeUserData);
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
      int i = 1;
      int j = jdMethod_if(paramFFSConnectionHolder, locala);
      if (j == 0) {
        ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "INTRATRN", locala.jdField_goto);
      } else if ("APPROVAL_PENDING".equals(localXferInstruction.Status) != true) {
        if (("APPROVAL_REJECTED".equals(localXferInstruction.Status) == true) || ("APPROVAL_NOT_ALLOWED".equals(localXferInstruction.Status) == true))
        {
          i = 0;
        }
        else
        {
          locala.jdField_if = 2016;
          locala.d = "FAILEDON";
          localTypeIntraTrnRsV1 = a(paramTypeTrnRqCm, locala);
          return localTypeIntraTrnRsV1;
        }
      }
      locala.jdField_if = 0;
      locala.d = "CANCELEDON";
      localTypeIntraTrnRsV1 = a(paramTypeTrnRqCm, locala);
      Object localObject1;
      Object localObject2;
      if (i == 1)
      {
        localObject1 = RecXferProcessor2.deleteLimit(paramFFSConnectionHolder, localXferInstruction, new HashMap(), "CANCELEDON", false);
        if (!"LIMIT_REVERT_SUCCEEDED".equals(localObject1))
        {
          if (this.T >= 3)
          {
            str1 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
            str2 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
            localObject2 = BPWLocaleUtil.getLocalizedMessage(402, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
            XferProcessor2.logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4605, localXferInstruction.Amount, localXferInstruction.CurDef, paramString1, str1, str2, localXferInstruction.BankID, localXferInstruction.fromBankID, (String)localObject1, (ILocalizable)localObject2, paramTypeUserData._agentID, paramTypeUserData._agentType);
          }
          throw new OFXException(localXferInstruction.getStatusMsg(), 2000);
        }
      }
      if (this.T >= 3)
      {
        str1 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctDebitID, localXferInstruction.AcctDebitType);
        str2 = BPWUtil.getAccountIDWithAccountType(localXferInstruction.AcctCreditID, localXferInstruction.AcctCreditType);
        localObject1 = BPWLocaleUtil.getLocalizedMessage(921, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        XferProcessor2.logTransAuditLog(paramFFSConnectionHolder, paramTypeUserData._submittedBy, paramTypeUserData._uid, locala.jdField_long, 4605, localXferInstruction.Amount, localXferInstruction.CurDef, paramString1, str1, str2, localXferInstruction.BankID, localXferInstruction.fromBankID, locala.d, (ILocalizable)localObject1, paramTypeUserData._agentID, paramTypeUserData._agentType);
      }
      int k = XferInstruction.updateStatus(paramFFSConnectionHolder, locala.jdField_goto, locala.d);
      if (k <= 0)
      {
        FFSDebug.log("*** XferProcessor.processXferRqV1Canc: Intra Transfer status is not updated!!  SrvrTID=" + locala.jdField_goto + " Status=" + locala.d + " Response=" + localTypeIntraTrnRsV1, 0);
        localObject2 = new StringWriter();
        new Exception("Stack Trace").printStackTrace(new PrintWriter((Writer)localObject2));
        String str3 = ((StringWriter)localObject2).toString();
        FFSDebug.log(str3, 0);
      }
      localAuditAgent.cancelOFX151XferRsV1(locala.jdField_goto, paramFFSConnectionHolder);
    }
    catch (OFXException localOFXException)
    {
      throw localOFXException;
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.getMessage());
    }
    return localTypeIntraTrnRsV1;
  }
  
  private void a(TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, TypeUserData paramTypeUserData, a parama, String paramString, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws BPWException, OFXException
  {
    parama.jdField_void = paramString;
    parama.jdField_long = paramTypeUserData._tran_id;
    if (paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.__memberName.equals("BankAcctFrom"))
    {
      parama.a = paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.BankID;
      parama.jdField_do = paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.BranchID;
      parama.b = paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.AcctID;
      parama.c = MsgBuilder.getAcctType(paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.BankAcctFrom.AcctType);
      parama.jdField_new = parama.a;
      parama.jdField_else = parama.jdField_do;
    }
    else
    {
      parama.b = paramTypeXferInfoV1Aggregate.BCCAcctFromV1Un.CCAcctFrom.AcctID;
      parama.c = "CREDITCARD";
    }
    if (paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.__memberName.equals("BankAcctTo"))
    {
      parama.jdField_new = paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.BankID;
      parama.jdField_else = paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.BranchID;
      parama.jdField_null = paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.AcctID;
      parama.f = MsgBuilder.getAcctType(paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.BankAcctTo.AcctType);
    }
    else
    {
      parama.jdField_null = paramTypeXferInfoV1Aggregate.BCCAcctToV1Un.CCAcctTo.AcctID;
      parama.f = "CREDITCARD";
    }
    String str1 = "" + paramTypeXferInfoV1Aggregate.TrnAmt;
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
    parama.jdField_byte = paramTypeXferInfoV1Aggregate.DtDueExists;
    int i = DBUtil.getCurrentStartDate();
    if (paramTypeXferInfoV1Aggregate.DtDueExists)
    {
      String str3;
      if (paramTypeXferInfoV1Aggregate.DtDue != null)
      {
        String str2 = paramTypeXferInfoV1Aggregate.DtDue.substring(0, 8);
        if (BPWUtil.validateDate(str2, "yyyyMMdd") == true)
        {
          parama.e = BPWUtil.getDateDBFormat(str2);
          if (parama.e < i)
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
      parama.e = i;
    }
    try
    {
      int j = SmartCalendar.getPayday(parama.h, parama.e / 100, "BookTransfer");
      parama.g = (Integer.toString(j) + "000000");
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
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, a parama)
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
      str = "Exception in XferProcessor.scheduleXferAddRq: " + localFFSException.getMessage();
      throw new BPWException(str, localFFSException.getErrCode());
    }
    catch (Exception localException)
    {
      String str = "Exception in XferProcessor.scheduleXferAddRq: " + localException.getMessage();
      throw new BPWException(str);
    }
  }
  
  private int jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, a parama)
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
      String str = "Exception in XferProcessor.getScheduleXferStatus: " + localException.getMessage();
      throw new BPWException(str);
    }
    return 0;
  }
  
  private IntraTrnRslt a(FFSConnectionHolder paramFFSConnectionHolder, a parama, HashMap paramHashMap)
    throws Exception
  {
    FFSDebug.log("XferProcessor.doImmediateXferWithoutTimeout starts ....", 6);
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
      parama.d = readStatusFromCode(localIntraTrnRslt.status);
      FFSDebug.log("XferProcessor.doImmediateXferWithoutTimeout", "Reverting limits for a failed transaction: ", parama.jdField_goto, 6);
      String str2 = RecXferProcessor2.deleteLimit(paramFFSConnectionHolder, XferInstruction.getXferInstruction(paramFFSConnectionHolder, parama.jdField_goto), new HashMap(), parama.d, false);
      FFSDebug.log("XferProcessor.doImmediateXferWithoutTimeout", ": Reverted limits for a failed transaction. status ", str2, 6);
    }
    if (localIntraTrnRslt.status != -1)
    {
      parama.jdField_if = localIntraTrnRslt.status;
      bool = true;
    }
    FFSDebug.log("XferProcessor.doImmediateXferWithoutTimeout complete isBackendDone: " + bool, 6);
    return localIntraTrnRslt;
  }
  
  private IntraTrnRslt jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, a parama, HashMap paramHashMap)
    throws Exception
  {
    FFSDebug.log("XferProcessor.doImmediateXfer starts.... ", 6);
    String str1 = "";
    boolean bool = false;
    IntraTrnRslt localIntraTrnRslt = null;
    Thread localThread1 = null;
    Thread localThread2 = null;
    try
    {
      for (int i = 0; i < this.X; i++)
      {
        str1 = DBConnCache.save(paramFFSConnectionHolder);
        IntraTrnInfo[] arrayOfIntraTrnInfo = { new IntraTrnInfo(parama.jdField_int, parama.jdField_new, parama.jdField_else, parama.b, parama.c, parama.jdField_null, parama.f, parama.jdField_char, parama.jdField_for, String.valueOf(parama.e), parama.jdField_goto, parama.jdField_long, Integer.toString(0), 1, false, str1, parama.jdField_void, "", parama.h, parama.a, parama.jdField_do) };
        arrayOfIntraTrnInfo[0].submittedBy = parama.jdField_case;
        if ((paramHashMap != null) && (paramHashMap.size() != 0)) {
          arrayOfIntraTrnInfo[0].extraFields = paramHashMap;
        }
        if (i > 0) {
          arrayOfIntraTrnInfo[0].possibleDuplicate = true;
        }
        localIntraTrnRslt = new IntraTrnRslt(arrayOfIntraTrnInfo[0].srvrTid, -1, arrayOfIntraTrnInfo[0].eventSequence, str1);
        BackendStatus localBackendStatus = new BackendStatus(localIntraTrnRslt, false);
        MonitorBackend localMonitorBackend = new MonitorBackend(this.aa);
        localThread2 = new Thread(localMonitorBackend);
        ab.add(localThread2);
        localThread2.start();
        ToBackendRunnable localToBackendRunnable = new ToBackendRunnable(arrayOfIntraTrnInfo, localBackendStatus, localMonitorBackend);
        localThread1 = new Thread(localToBackendRunnable);
        Z.add(localThread1);
        localThread1.start();
        try
        {
          localThread2.join();
          if ((localThread1 != null) && (localThread1.isAlive())) {
            try
            {
              localThread1.interrupt();
            }
            catch (Exception localException) {}
          }
        }
        catch (Throwable localThrowable2)
        {
          String str2 = FFSDebug.stackTrace(localThrowable2);
          FFSDebug.log("XferProcessor.doImmediateXfer: InterruptedException. Error: " + str2, 6);
        }
        DBConnCache.unbind(str1);
        if (localBackendStatus.isProcessComplete())
        {
          localIntraTrnRslt = localBackendStatus.getResult();
          localIntraTrnRslt.srvrTid = parama.jdField_goto;
          parama.jdField_if = localIntraTrnRslt.status;
          bool = true;
          try
          {
            if (localThread1 != null) {
              Z.remove(localThread1);
            }
            if (localThread2 != null) {
              ab.remove(localThread2);
            }
          }
          catch (Throwable localThrowable3)
          {
            FFSDebug.log("xferProcessor.doImmediateXfer Error: " + FFSDebug.stackTrace(localThrowable3));
          }
        }
      }
    }
    catch (Throwable localThrowable1)
    {
      FFSDebug.log("xferProcessor.doImmediateXfer Error: " + FFSDebug.stackTrace(localThrowable1));
    }
    finally
    {
      try
      {
        if (localThread1 != null)
        {
          try
          {
            localThread1.interrupt();
          }
          catch (Throwable localThrowable4) {}
          Z.remove(localThread1);
        }
        if (localThread2 != null)
        {
          try
          {
            localThread1.interrupt();
          }
          catch (Throwable localThrowable5) {}
          ab.remove(localThread2);
        }
      }
      catch (Throwable localThrowable6)
      {
        FFSDebug.log("xferProcessor.doImmediateXfer Error: " + FFSDebug.stackTrace(localThrowable6));
      }
    }
    FFSDebug.log("XferProcessor.doImmediateXfer complete isBackendDone: " + bool, 6);
    return localIntraTrnRslt;
  }
  
  public static void shutdown()
  {
    int i = ab.size();
    int j = Z.size();
    ListIterator localListIterator = ab.listIterator();
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
    ab.clear();
    FFSDebug.log("xferProcessor.shutdown: Removed monitor transfer threads: " + i, 6);
    localListIterator = Z.listIterator();
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
    Z.clear();
    FFSDebug.log("xferProcessor.shutdown: Removed transfer threads: " + j, 6);
  }
  
  private TypeIntraTrnRsV1 a(TypeTrnRqCm paramTypeTrnRqCm, TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, a parama, String paramString, TypeUserData paramTypeUserData, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws Exception
  {
    TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1UnExists = true;
    TypeIntraTrnRsV1Un localTypeIntraTrnRsV1Un = new TypeIntraTrnRsV1Un();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un = localTypeIntraTrnRsV1Un;
    localTypeIntraTrnRsV1Un.__memberName = "IntraRs";
    TypeIntraRsV1Aggregate localTypeIntraRsV1Aggregate = new TypeIntraRsV1Aggregate();
    localTypeIntraTrnRsV1Un.IntraRs = localTypeIntraRsV1Aggregate;
    if (paramEnumCurrencyEnum != null) {
      localTypeIntraRsV1Aggregate.CurDef = paramEnumCurrencyEnum;
    } else {
      localTypeIntraRsV1Aggregate.CurDef = MsgBuilder.getOFX151CurrencyEnum(BPWUtil.validateCurrencyEnum(paramEnumCurrencyEnum));
    }
    localTypeIntraRsV1Aggregate.SrvrTID = parama.jdField_goto;
    if (paramString == null)
    {
      localTypeIntraRsV1Aggregate.RecSrvrTIDExists = false;
    }
    else
    {
      localTypeIntraRsV1Aggregate.RecSrvrTIDExists = true;
      localTypeIntraRsV1Aggregate.RecSrvrTID = paramString;
    }
    localTypeIntraRsV1Aggregate.XferInfo = paramTypeXferInfoV1Aggregate;
    localTypeIntraRsV1Aggregate.IntraRsV1DateUnExists = false;
    localTypeIntraRsV1Aggregate.XferPrcStsExists = true;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new TypeXferPrcStsAggregate();
    localTypeIntraRsV1Aggregate.XferPrcSts = localTypeXferPrcStsAggregate;
    int i = Integer.parseInt(parama.g.substring(0, 8));
    String str = SmartCalendar.getPayday(parama.h, i, "BookTransfer") + "000000";
    RsCmBuilder.updateRsXferPrcSts(parama.d, parama.g, localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs.XferPrcSts);
    RsCmBuilder.updateXferRsDateXferPrj(str, localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraRs);
    localTypeIntraTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, parama.jdField_if);
    return new TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
  }
  
  private TypeIntraTrnRsV1 a(TypeTrnRqCm paramTypeTrnRqCm, TypeXferInfoV1Aggregate paramTypeXferInfoV1Aggregate, a parama)
    throws Exception
  {
    TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1UnExists = true;
    TypeIntraTrnRsV1Un localTypeIntraTrnRsV1Un = new TypeIntraTrnRsV1Un();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un = localTypeIntraTrnRsV1Un;
    localTypeIntraTrnRsV1Un.__memberName = "IntraModRs";
    TypeIntraModRsV1Aggregate localTypeIntraModRsV1Aggregate = new TypeIntraModRsV1Aggregate();
    localTypeIntraTrnRsV1Un.IntraModRs = localTypeIntraModRsV1Aggregate;
    localTypeIntraModRsV1Aggregate.SrvrTID = parama.jdField_goto;
    localTypeIntraModRsV1Aggregate.CurDefExists = true;
    localTypeIntraModRsV1Aggregate.CurDef = MsgBuilder.getOFX151CurrencyEnum(parama.jdField_for);
    localTypeIntraModRsV1Aggregate.XferInfo = paramTypeXferInfoV1Aggregate;
    localTypeIntraModRsV1Aggregate.XferPrcStsExists = true;
    TypeXferPrcStsAggregate localTypeXferPrcStsAggregate = new TypeXferPrcStsAggregate();
    localTypeIntraModRsV1Aggregate.XferPrcSts = localTypeXferPrcStsAggregate;
    RsCmBuilder.updateRsXferPrcSts(parama.d, parama.g, localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un.IntraModRs.XferPrcSts);
    localTypeIntraTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, parama.jdField_if);
    return new TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
  }
  
  private TypeIntraTrnRsV1 a(TypeTrnRqCm paramTypeTrnRqCm, a parama)
  {
    TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1UnExists = true;
    TypeIntraTrnRsV1Un localTypeIntraTrnRsV1Un = new TypeIntraTrnRsV1Un();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1Un = localTypeIntraTrnRsV1Un;
    localTypeIntraTrnRsV1Un.__memberName = "IntraCanRs";
    TypeIntraCanRsV1Aggregate localTypeIntraCanRsV1Aggregate = new TypeIntraCanRsV1Aggregate();
    localTypeIntraTrnRsV1Un.IntraCanRs = localTypeIntraCanRsV1Aggregate;
    localTypeIntraCanRsV1Aggregate.SrvrTID = parama.jdField_goto;
    localTypeIntraTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, parama.jdField_if);
    return new TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
  }
  
  private TypeIntraTrnRsV1 jdMethod_if(TypeTrnRqCm paramTypeTrnRqCm, int paramInt)
  {
    TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
    localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1UnExists = false;
    localTypeIntraTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt);
    return new TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
  }
  
  private TypeIntraTrnRsV1 jdMethod_for(TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
    throws BPWException
  {
    try
    {
      TypeIntraTrnRsV1Aggregate localTypeIntraTrnRsV1Aggregate = new TypeIntraTrnRsV1Aggregate();
      localTypeIntraTrnRsV1Aggregate.IntraTrnRsV1UnExists = false;
      localTypeIntraTrnRsV1Aggregate.TrnRsV1Cm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt, paramString);
      return new TypeIntraTrnRsV1(localTypeIntraTrnRsV1Aggregate);
    }
    catch (Exception localException)
    {
      String str = FFSDebug.stackTrace(localException);
      FFSDebug.log("XferProcessor.buildXferErrorRs: failed", 0);
      throw new BPWException(str);
    }
  }
  
  public static String readStatusFromCode(int paramInt)
  {
    String str;
    switch (paramInt)
    {
    case 0: 
      str = "POSTEDON";
      break;
    case 10504: 
      str = "NOFUNDSON";
      break;
    default: 
      str = "FAILEDON";
    }
    return str;
  }
  
  public IntraTrnInfo getIntraById(String paramString)
    throws BPWException
  {
    FFSDebug.log("XferProcessor.getIntraById : start", 6);
    IntraTrnInfo localIntraTrnInfo1 = null;
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      localIntraTrnInfo1 = new IntraTrnInfo();
      localIntraTrnInfo1.statusCode = 16504;
      localIntraTrnInfo1.statusMsg = BPWLocaleUtil.getMessage(16504, new String[] { "ID " }, "API_MESSAGE");
      FFSDebug.log("XferProcessor.getIntraById failed : null or empty ID passed", 0);
      return localIntraTrnInfo1;
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("XferProcessor.getIntraById: failed to get DB Connection ");
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
      String str = "XferProcessor.getIntraById: failed ";
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
    FFSDebug.log("XferProcessor.getIntraById (multiple): start", 6);
    IntraTrnInfo localIntraTrnInfo = null;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localIntraTrnInfo = new IntraTrnInfo();
      localIntraTrnInfo.statusCode = 16504;
      localIntraTrnInfo.statusMsg = BPWLocaleUtil.getMessage(16504, new String[] { "ID[] " }, "API_MESSAGE");
      FFSDebug.log("XferProcessor.getIntraById (multiple): failed  null or empty ID array passed", 0);
      localObject1 = new IntraTrnInfo[] { localIntraTrnInfo };
      return localObject1;
    }
    Object localObject1 = new FFSConnectionHolder();
    ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
    if (((FFSConnectionHolder)localObject1).conn == null) {
      throw new BPWException("XferProcessor.getIntraById (multiple): failed to get DB Connection ");
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
      Object localObject2 = "XferProcessor.getIntraById (multiple): failed ";
      FFSDebug.log((String)localObject2 + FFSDebug.stackTrace(localException), 0);
      throw new BPWException((String)localObject2 + localException.toString());
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
  }
  
  private void jdMethod_for(Hashtable paramHashtable, FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    String str = null;
    try
    {
      FFSDebug.log("XferProcessor.processXtraInfo start: ", 6);
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
      str = "XferProcessor.processXtraInfo : failed ";
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new BPWException(str + localException.toString());
    }
    FFSDebug.log("XferProcessor.processXtraInfo end: ", 6);
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
    
    a(XferProcessor.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.XferProcessor
 * JD-Core Version:    0.7.0.1
 */