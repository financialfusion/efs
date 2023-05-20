package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.FundsAllocator;
import com.ffusion.ffs.bpw.db.BPWExtraInfo;
import com.ffusion.ffs.bpw.db.CustPayee;
import com.ffusion.ffs.bpw.db.CustPayeeRoute;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PmtExtraInfo;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.Trans;
import com.ffusion.ffs.bpw.fulfill.FulfillAgent;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.BankInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.FundsAllocInfo;
import com.ffusion.ffs.bpw.interfaces.FundsAllocRslt;
import com.ffusion.ffs.bpw.interfaces.OFXConsts;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.serviceMsg.MsgBuilder;
import com.ffusion.ffs.bpw.serviceMsg.RsCmBuilder;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.ofx.interfaces.TypeUserData;
import com.ffusion.ffs.scheduling.db.ScheduleConstants;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumCurrencyEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumIDScopeEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.EnumPmtProcessStatusEnum;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeExtdPayeeCm;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePayeeUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtCancRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtCancRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtInfoAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtModRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtModRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtPrcStsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRqAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtRsAggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqUn;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRqV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Aggregate;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypePmtTrnRsV1Un;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.OFX200.TypeTrnRqCm;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Hashtable;

public class PmtProcessor2
  implements DBConsts, FFSConst, OFXConsts, BPWResource, ScheduleConstants
{
  public static final String MEMBER_NAME_PAYEE_ID = "PayeeID";
  public static final String MEMBER_NAME_PAYEE = "Payee";
  private PropertyConfig ap = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
  private int al = 1;
  private boolean ao = false;
  private boolean am = false;
  private boolean an = false;
  private String ak = "";
  
  public TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData)
    throws Exception
  {
    return processPmtTrnRqV1(paramTypePmtTrnRqV1, paramTypeUserData, null);
  }
  
  public TypePmtTrnRsV1 processPmtTrnRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = null;
    try
    {
      String str1 = null;
      if (paramTypeUserData != null) {
        str1 = paramTypeUserData._tran_id;
      }
      str2 = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.__memberName;
      if (str2.equals("PmtRq")) {
        localTypePmtTrnRsV1 = processPmtAddRqV1(paramTypePmtTrnRqV1, paramTypeUserData, str1);
      } else if (str2.equals("PmtModRq")) {
        localTypePmtTrnRsV1 = processPmtModRqV1(paramTypePmtTrnRqV1, paramTypeUserData, str1);
      } else if (str2.equals("PmtCancRq")) {
        localTypePmtTrnRsV1 = processPmtCancRqV1(paramTypePmtTrnRqV1, paramTypeUserData, str1);
      } else {
        throw new OFXException("Unsupported request type", 2000);
      }
    }
    catch (OFXException localOFXException)
    {
      localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException)
    {
      String str2 = "*** PmtProcessor2.processPmtTrnRqV1 failed:";
      FFSDebug.log(localException, str2);
      FFSDebug.console(str2 + localException.toString());
      localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    return localTypePmtTrnRsV1;
  }
  
  public TypePmtTrnRsV1 processPmtAddRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    FFSDebug.log("PmtProcessor2.processPmtAddRqV1 start, uid=" + getUID(paramTypeUserData), 6);
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = null;
    FFSConnection[] arrayOfFFSConnection = DBUtil.getConnections(2);
    FFSConnectionHolder localFFSConnectionHolder1 = new FFSConnectionHolder();
    localFFSConnectionHolder1.conn = arrayOfFFSConnection[0];
    FFSConnectionHolder localFFSConnectionHolder2 = new FFSConnectionHolder();
    localFFSConnectionHolder2.conn = arrayOfFFSConnection[1];
    if ((localFFSConnectionHolder1.conn == null) || (localFFSConnectionHolder2.conn == null)) {
      throw new Exception("Can not get DB Connection.");
    }
    try
    {
      localTypePmtTrnRsV1 = processPmtAddRqV1(localFFSConnectionHolder1, localFFSConnectionHolder2, paramTypePmtTrnRqV1, paramTypeUserData, paramString, null);
      localFFSConnectionHolder1.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder1.conn.rollback();
      str1 = "*** PmtProcessor2.processPmtAddRqV1 failed:";
      str2 = localOFXException.toString();
      FFSDebug.log(localOFXException, str1);
      FFSDebug.console(str1 + str2);
      localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder1.conn.rollback();
      str1 = "*** PmtProcessor2.processPmtAddRqV1 failed:";
      str2 = localBPWException.toString();
      FFSDebug.log(localBPWException, str1);
      FFSDebug.console(str1 + str2);
      if (localBPWException.getErrorCode() == -1) {
        localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2000, "Server internal error");
      } else {
        localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder1.conn.rollback();
      String str1 = "*** PmtProcessor2.processPmtAddRqV1 failed:";
      String str2 = localException.toString();
      FFSDebug.log(localException, str1);
      FFSDebug.console(str1 + str2);
      localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2000, "Server internal error");
    }
    finally
    {
      localFFSConnectionHolder2.conn.rollback();
      DBUtil.freeConnection(localFFSConnectionHolder1.conn);
      DBUtil.freeConnection(localFFSConnectionHolder2.conn);
    }
    FFSDebug.log("PmtProcessor2.processPmtAddRqV1 end, uid=" + getUID(paramTypeUserData), 6);
    return localTypePmtTrnRsV1;
  }
  
  public TypePmtTrnRsV1 processPmtAddRqV1(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData, String paramString1, String paramString2)
    throws Exception, BPWException, OFXException
  {
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = null;
    String str1 = getUID(paramTypeUserData);
    String str2 = DBUtil.getFIId(paramTypeUserData);
    TypePmtInfoAggregate localTypePmtInfoAggregate = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo;
    EnumCurrencyEnum localEnumCurrencyEnum = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.CurDef;
    HashMap localHashMap1 = new HashMap();
    String str3 = paramString1;
    if ((paramString2 != null) && (paramString2.length() > 0)) {
      str3 = BPWTrackingIDGenerator.getNextId();
    }
    PmtInstruction localPmtInstruction = createPmtInstr(localTypePmtInfoAggregate, str1, str2, str3, paramString2, paramTypeUserData, localEnumCurrencyEnum);
    this.ak = localPmtInstruction.getPmtInfo().Status;
    localPmtInstruction.setSrvrTID();
    String str4 = localPmtInstruction.getSrvrTID();
    ScheduleInfo localScheduleInfo = createScheduleInfo(localTypePmtInfoAggregate, str1, str2, str3);
    Object localObject2;
    if ((!paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TrnUID.equals("0")) && (Trans.checkDuplicateTIDAndSaveTID(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TrnUID)))
    {
      if (this.al >= 3)
      {
        int i = Integer.parseInt(localPmtInstruction.getCustomerID());
        localObject1 = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
        str5 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
        String str6 = BPWLocaleUtil.getMessage(804, null, "BILLPAY_AUDITLOG_MESSAGE");
        localObject2 = new String[] { str6 };
        localObject3 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject2, "BILLPAY_AUDITLOG_MESSAGE");
        logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, (ILocalizable)localObject3, str3, 4401, i, (BigDecimal)localObject1, localPmtInstruction.getPmtInfo().CurDef, str4, "DUPLICATE", localPmtInstruction.getPayAcct(), null, str5, null);
      }
      localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2019, " ");
      return localTypePmtTrnRsV1;
    }
    PayeeInfo localPayeeInfo = processImplicitPayeeRq(localTypePmtInfoAggregate, str1, paramFFSConnectionHolder1, paramFFSConnectionHolder2, str3);
    if (localPayeeInfo == null) {
      throw new BPWException("Could not create payeeID!");
    }
    localPmtInstruction.setPayeeID(localPayeeInfo.PayeeID);
    localPmtInstruction.setPayeeListID(Integer.parseInt(localTypePmtInfoAggregate.PayeeLstID));
    localPmtInstruction.setRouteID(localPayeeInfo.RouteID);
    Object localObject1 = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localObject1 == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    localTypePmtTrnRsV1 = buildPmtAddRs(paramTypePmtTrnRqV1, str2, str4, localTypePmtInfoAggregate.PayeeLstID, localPayeeInfo, localPmtInstruction.getPmtInfo().CurDef);
    if (paramString2 != null)
    {
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.RecSrvrTIDExists = true;
      localTypePmtTrnRsV1.PmtTrnRs.PmtTrnRsV1Un.PmtRs.RecSrvrTID = paramString2;
    }
    String str5 = null;
    str5 = RecPmtProcessor2.doLimitChecking(paramFFSConnectionHolder1, localPmtInstruction.getPmtInfo(), localHashMap1, false);
    if ((!"WILLPROCESSON".equals(str5)) && (!"APPROVAL_PENDING".equals(str5)))
    {
      if (this.al >= 3)
      {
        int j = Integer.parseInt(localPmtInstruction.getCustomerID());
        localObject2 = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
        localObject3 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
        LocalizableString localLocalizableString1 = BPWLocaleUtil.getLocalizedMessage(400, null, "BILLPAY_AUDITLOG_MESSAGE");
        logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString1, str3, 4400, j, (BigDecimal)localObject2, localPmtInstruction.getPmtInfo().CurDef, str4, str5, localPmtInstruction.getPayAcct(), null, (String)localObject3, null);
      }
      String str7 = localPmtInstruction.getPmtInfo().getStatusMsg();
      throw new OFXException(str7, 2000);
    }
    int k = localPmtInstruction.getStartDate();
    boolean bool = false;
    if ((k % 100 != 0) && ("WILLPROCESSON".equals(str5))) {
      bool = true;
    }
    localPmtInstruction.setStatus(str5);
    if ((this.am) && (bool) && ("WILLPROCESSON".equals(str5))) {
      localPmtInstruction.setStatus("FUNDSALLOCACTIVE");
    }
    localPmtInstruction.storeToDB(paramFFSConnectionHolder1);
    jdMethod_int(paramTypeUserData._privateTagContainer, paramFFSConnectionHolder1, localPmtInstruction.getSrvrTID(), "Add");
    FFSDebug.log("PmtProcessor2.processPmtAddRqV1 start, supportImmediatePmt=" + this.am + ",isImmediate=" + bool, 6);
    jdMethod_if(paramFFSConnectionHolder1, paramTypeUserData, str4, "add");
    Object localObject3 = "WILLPROCESSON";
    if ((!"WILLPROCESSON".equalsIgnoreCase(str5)) && (!"APPROVAL_PENDING".equalsIgnoreCase(str5))) {
      localObject3 = "FAILEDON";
    }
    ((AuditAgent)localObject1).savePmtRsV1(localTypePmtTrnRsV1, str1, (String)localObject3, paramFFSConnectionHolder1);
    Object localObject5;
    String str8;
    String[] arrayOfString;
    LocalizableString localLocalizableString2;
    if ((this.am) && (bool) && ("WILLPROCESSON".equals(str5)))
    {
      Object localObject4;
      if (this.al >= 3)
      {
        int m = Integer.parseInt(localPmtInstruction.getCustomerID());
        localObject4 = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
        localObject5 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
        str8 = BPWLocaleUtil.getMessage(805, null, "BILLPAY_AUDITLOG_MESSAGE");
        arrayOfString = new String[] { str8 };
        localLocalizableString2 = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(paramFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString2, str3, 4402, m, (BigDecimal)localObject4, localPmtInstruction.getPmtInfo().CurDef, str4, localPmtInstruction.getStatus(), localPmtInstruction.getPayAcct(), null, (String)localObject5, null, 0);
      }
      try
      {
        paramFFSConnectionHolder1.conn.commit();
      }
      catch (Throwable localThrowable)
      {
        paramFFSConnectionHolder1.conn.rollback();
        localObject4 = FFSDebug.stackTrace(localThrowable);
        FFSDebug.log("***PmtProcessor2.processPmtAddRqV1: (imm) failed:" + (String)localObject4);
        localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2000, "Server internal error");
        return localTypePmtTrnRsV1;
      }
      HashMap localHashMap2 = null;
      if ((paramTypeUserData._privateTagContainer != null) && (!paramTypeUserData._privateTagContainer.isEmpty())) {
        localHashMap2 = BPWUtil.convertHashTableToMap(paramTypeUserData._privateTagContainer);
      }
      int i1 = a(localPmtInstruction, localPayeeInfo, paramFFSConnectionHolder1, localHashMap2);
      if ((i1 != 0) && (i1 != 1))
      {
        localObject5 = new PmtTrnRslt();
        ((PmtTrnRslt)localObject5).status = i1;
        ((PmtTrnRslt)localObject5).srvrTid = str4;
        BackendProcessor.updateFundsStatus(localTypePmtTrnRsV1, (PmtTrnRslt)localObject5);
      }
    }
    else if ("WILLPROCESSON".equalsIgnoreCase(str5))
    {
      FFSDebug.log("PmtProcessor2.processPmtAddRqV1", ": Creating schedule for payment: ", str4, 6);
      ScheduleInfo.createSchedule(paramFFSConnectionHolder1, "FUNDTRN", str4, localScheduleInfo);
      localScheduleInfo.Frequency = 11;
      localScheduleInfo.InstanceCount = this.ap.getFundsAllocRetries();
      localScheduleInfo.NextInstanceDate = localScheduleInfo.StartDate;
      ScheduleInfo.moveNextInstanceDate(localScheduleInfo);
      localScheduleInfo.StartDate = localScheduleInfo.NextInstanceDate;
      ScheduleInfo.createSchedule(paramFFSConnectionHolder1, "RECFUNDTRN", str4, localScheduleInfo);
    }
    if (this.al >= 3)
    {
      int n = Integer.parseInt(localPmtInstruction.getCustomerID());
      BigDecimal localBigDecimal = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
      localObject5 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
      str8 = BPWLocaleUtil.getMessage(803, null, "BILLPAY_AUDITLOG_MESSAGE");
      arrayOfString = new String[] { str8 };
      localLocalizableString2 = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
      TransAuditLog.logTransAuditLog(paramFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString2, str3, 4400, n, localBigDecimal, localPmtInstruction.getPmtInfo().CurDef, str4, localPmtInstruction.getStatus(), localPmtInstruction.getPayAcct(), null, (String)localObject5, null, 0);
    }
    return localTypePmtTrnRsV1;
  }
  
  public TypePmtTrnRsV1 processPmtModRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    FFSDebug.log("PmtProcessor2.processPmtModRqV1 start, uid=" + getUID(paramTypeUserData), 6);
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = null;
    FFSConnection[] arrayOfFFSConnection = DBUtil.getConnections(2);
    FFSConnectionHolder localFFSConnectionHolder1 = new FFSConnectionHolder();
    localFFSConnectionHolder1.conn = arrayOfFFSConnection[0];
    FFSConnectionHolder localFFSConnectionHolder2 = new FFSConnectionHolder();
    localFFSConnectionHolder2.conn = arrayOfFFSConnection[1];
    if ((localFFSConnectionHolder1.conn == null) || (localFFSConnectionHolder2.conn == null)) {
      throw new Exception("Can not get DB Connection.");
    }
    try
    {
      localTypePmtTrnRsV1 = processPmtModRqV1(localFFSConnectionHolder1, localFFSConnectionHolder2, paramTypePmtTrnRqV1, paramTypeUserData, paramString);
      localFFSConnectionHolder1.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder1.conn.rollback();
      str1 = "*** PmtProcessor2.processPmtModRqV1 failed for payment SrvrTID=" + paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.SrvrTID + ":";
      str2 = localOFXException.toString();
      FFSDebug.log(localOFXException, str1);
      FFSDebug.console(str1 + str2);
      localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (BPWException localBPWException)
    {
      localFFSConnectionHolder1.conn.rollback();
      str1 = "*** PmtProcessor2.processPmtModRqV1 failed for payment SrvrTID=" + paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.SrvrTID + ":";
      str2 = localBPWException.toString();
      FFSDebug.log(localBPWException, str1);
      FFSDebug.console(str1 + str2);
      if (localBPWException.getErrorCode() == -1) {
        localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2000, " ");
      } else {
        localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2000, BPWUtil.getErrorMessageWithCode(localBPWException.getErrorCode(), localBPWException.getExceptionMsg()));
      }
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder1.conn.rollback();
      String str1 = "*** PmtProcessor2.processPmtModRqV1 failed for payment SrvrTID=" + paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.SrvrTID + ":";
      String str2 = localException.toString();
      FFSDebug.log(localException, str1);
      FFSDebug.console(str1 + str2);
      localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2000, " ");
    }
    finally
    {
      localFFSConnectionHolder2.conn.rollback();
      DBUtil.freeConnection(localFFSConnectionHolder1.conn);
      DBUtil.freeConnection(localFFSConnectionHolder2.conn);
    }
    FFSDebug.log("PmtProcessor2.processPmtModRqV1 end, uid=" + getUID(paramTypeUserData), 6);
    return localTypePmtTrnRsV1;
  }
  
  public TypePmtTrnRsV1 processPmtModRqV1(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception, BPWException, OFXException
  {
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = null;
    String str1 = getUID(paramTypeUserData);
    String str2 = DBUtil.getFIId(paramTypeUserData);
    TypePmtInfoAggregate localTypePmtInfoAggregate = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.PmtInfo;
    EnumCurrencyEnum localEnumCurrencyEnum = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.CurDef;
    PmtInstruction localPmtInstruction = createPmtInstr(localTypePmtInfoAggregate, str1, str2, paramString, null, paramTypeUserData, localEnumCurrencyEnum);
    String str3 = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.SrvrTID;
    localPmtInstruction.setSrvrTID(str3);
    this.ak = localPmtInstruction.getStatus();
    ScheduleInfo localScheduleInfo = createScheduleInfo(localTypePmtInfoAggregate, str1, str2, paramString);
    if ((!paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TrnUID.equals("0")) && (Trans.checkDuplicateTIDAndSaveTID(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TrnUID)))
    {
      if (this.al >= 3)
      {
        int i = Integer.parseInt(localPmtInstruction.getCustomerID());
        localObject1 = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
        localObject2 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
        String str4 = BPWLocaleUtil.getMessage(807, null, "BILLPAY_AUDITLOG_MESSAGE");
        localObject3 = new String[] { str4 };
        LocalizableString localLocalizableString1 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject3, "BILLPAY_AUDITLOG_MESSAGE");
        logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString1, paramString, 4404, i, (BigDecimal)localObject1, localPmtInstruction.getPmtInfo().CurDef, str3, "DUPLICATE", localPmtInstruction.getPayAcct(), null, (String)localObject2, null);
      }
      localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2019, " ");
      return localTypePmtTrnRsV1;
    }
    if (!paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.PmtInfo.PayeeLstIDExists) {
      throw new OFXException(" ", 10519);
    }
    PayeeInfo localPayeeInfo = processImplicitPayeeRq(localTypePmtInfoAggregate, str1, paramFFSConnectionHolder1, paramFFSConnectionHolder2, paramString);
    if (localPayeeInfo == null) {
      throw new BPWException("Could not create payeeID.");
    }
    Object localObject1 = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localObject1 == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    localPmtInstruction.setPayeeID(localPayeeInfo.PayeeID);
    localPmtInstruction.setPayeeListID(Integer.parseInt(localTypePmtInfoAggregate.PayeeLstID));
    localPmtInstruction.setRouteID(localPayeeInfo.RouteID);
    Object localObject2 = PmtInstruction.getPmtInfo(str3, paramFFSConnectionHolder1);
    if (localObject2 == null) {
      return jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2018, " ");
    }
    if (((PmtInfo)localObject2).Status.equals("CANCELEDON")) {
      return jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2017, " ");
    }
    int j = 1;
    if ("APPROVAL_PENDING".equals(((PmtInfo)localObject2).Status) != true)
    {
      if ((a(str3, str2, "FUNDTRN", paramFFSConnectionHolder1) == 0) || (a(str3, str2, "RECFUNDTRN", paramFFSConnectionHolder1) == 0)) {
        throw new BPWException("Cannot cancel pmt in process!!");
      }
      if (("WILLPROCESSON".equals(((PmtInfo)localObject2).Status) != true) && ("APPROVAL_PENDING".equals(((PmtInfo)localObject2).Status) != true)) {
        if (("APPROVAL_REJECTED".equals(((PmtInfo)localObject2).Status) == true) || ("APPROVAL_NOT_ALLOWED".equals(((PmtInfo)localObject2).Status) == true)) {
          j = 0;
        } else {
          return jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2016, " ");
        }
      }
    }
    int k;
    BigDecimal localBigDecimal;
    Object localObject4;
    Object localObject5;
    if (j == 1)
    {
      localObject3 = RecPmtProcessor2.deleteLimit(paramFFSConnectionHolder1, (PmtInfo)localObject2, new HashMap(), "CANCELEDON", false);
      if (!"LIMIT_REVERT_SUCCEEDED".equals(localObject3))
      {
        FFSDebug.log("PmtProcessor2.processPmtModRqV1Faled to revert limit", 0);
        if (this.al >= 3)
        {
          k = Integer.parseInt(localPmtInstruction.getCustomerID());
          localBigDecimal = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
          localObject4 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
          localObject5 = BPWLocaleUtil.getLocalizedMessage(402, null, "BILLPAY_AUDITLOG_MESSAGE");
          logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, (ILocalizable)localObject5, paramString, 4403, k, localBigDecimal, localPmtInstruction.getPmtInfo().CurDef, str3, (String)localObject3, localPmtInstruction.getPayAcct(), null, (String)localObject4, null);
        }
        throw new OFXException(localPmtInstruction.getPmtInfo().getStatusMsg(), 2000);
      }
    }
    Object localObject3 = RecPmtProcessor2.doLimitChecking(paramFFSConnectionHolder1, localPmtInstruction.getPmtInfo(), new HashMap(), false);
    if ((!"WILLPROCESSON".equals(localObject3)) && (!"APPROVAL_PENDING".equals(localObject3)))
    {
      if (this.al >= 3)
      {
        k = Integer.parseInt(localPmtInstruction.getCustomerID());
        localBigDecimal = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
        localObject4 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
        localObject5 = BPWLocaleUtil.getLocalizedMessage(400, null, "BILLPAY_AUDITLOG_MESSAGE");
        logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, (ILocalizable)localObject5, paramString, 4403, k, localBigDecimal, localPmtInstruction.getPmtInfo().CurDef, str3, (String)localObject3, localPmtInstruction.getPayAcct(), null, (String)localObject4, null);
      }
      throw new OFXException(localPmtInstruction.getPmtInfo().getStatusMsg(), 2000);
    }
    this.ak = ((String)localObject3);
    localPmtInstruction.setStatus(this.ak);
    if ((localScheduleInfo != null) && ("WILLPROCESSON".equals(localObject3)))
    {
      localScheduleInfo.NextInstanceDate = localScheduleInfo.StartDate;
      ScheduleInfo.createSchedule(paramFFSConnectionHolder1, "FUNDTRN", str3, localScheduleInfo);
      localScheduleInfo.Frequency = 11;
      localScheduleInfo.InstanceCount = this.ap.getFundsAllocRetries();
      localScheduleInfo.NextInstanceDate = localScheduleInfo.StartDate;
      ScheduleInfo.moveNextInstanceDate(localScheduleInfo);
      localScheduleInfo.StartDate = localScheduleInfo.NextInstanceDate;
      ScheduleInfo.createSchedule(paramFFSConnectionHolder1, "RECFUNDTRN", str3, localScheduleInfo);
    }
    localPmtInstruction.getPmtInfo().lastModified = FFSUtil.getDateString("yyyyMMddHHmmss");
    localPmtInstruction.updatePmt(paramFFSConnectionHolder1);
    jdMethod_if(paramFFSConnectionHolder1, paramTypeUserData, str3, "mod");
    localTypePmtTrnRsV1 = buildPmtModRs(paramTypePmtTrnRqV1, str2, localTypePmtInfoAggregate.PayeeLstID, localPayeeInfo, localPmtInstruction.getPmtInfo().CurDef);
    PmtInfo localPmtInfo = localPmtInstruction.getPmtInfo();
    jdMethod_int(paramTypeUserData._privateTagContainer, paramFFSConnectionHolder1, localPmtInfo.SrvrTID, "Mod");
    ((AuditAgent)localObject1).modPmtRsV1(localTypePmtTrnRsV1, localPmtInfo, paramFFSConnectionHolder1);
    if (this.al >= 3)
    {
      int m = Integer.parseInt(localPmtInstruction.getCustomerID());
      localObject4 = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
      localObject5 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
      String str5 = BPWLocaleUtil.getMessage(806, null, "BILLPAY_AUDITLOG_MESSAGE");
      String[] arrayOfString = { str5 };
      LocalizableString localLocalizableString2 = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
      TransAuditLog.logTransAuditLog(paramFFSConnectionHolder1.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString2, paramString, 4403, m, (BigDecimal)localObject4, localPmtInstruction.getPmtInfo().CurDef, str3, "MODIFIED", localPmtInstruction.getPayAcct(), null, (String)localObject5, null, 0);
    }
    return localTypePmtTrnRsV1;
  }
  
  public TypePmtTrnRsV1 processPmtCancRqV1(TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws Exception
  {
    FFSDebug.log("PmtProcessor2.processPmtCancRqV1 start, uid=" + getUID(paramTypeUserData), 6);
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    if (localFFSConnectionHolder.conn == null) {
      throw new BPWException("PmtProcessor2.processPmtCancRqV1:Can not get DB Connection.");
    }
    try
    {
      localTypePmtTrnRsV1 = processPmtCancRqV1(localFFSConnectionHolder, paramTypePmtTrnRqV1, paramTypeUserData, paramString);
      localFFSConnectionHolder.conn.commit();
    }
    catch (OFXException localOFXException)
    {
      localFFSConnectionHolder.conn.rollback();
      localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, localOFXException.getErrorCode(), localOFXException.getExceptionMsg());
    }
    catch (Exception localException)
    {
      localFFSConnectionHolder.conn.rollback();
      String str1 = "*** PmtProcessor2.processPmtCancRqV1 failed: ";
      String str2 = localException.toString();
      FFSDebug.log(localException, str1);
      FFSDebug.console(str1 + str2);
      localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2000, " ");
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log("PmtProcessor2.processPmtCancRqV1 end, uid=" + getUID(paramTypeUserData), 6);
    return localTypePmtTrnRsV1;
  }
  
  public TypePmtTrnRsV1 processPmtCancRqV1(FFSConnectionHolder paramFFSConnectionHolder, TypePmtTrnRqV1 paramTypePmtTrnRqV1, TypeUserData paramTypeUserData, String paramString)
    throws OFXException, Exception
  {
    TypePmtTrnRsV1 localTypePmtTrnRsV1 = null;
    String str1 = getUID(paramTypeUserData);
    String str2 = DBUtil.getFIId(paramTypeUserData);
    String str3 = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtCancRq.SrvrTID;
    PmtInstruction localPmtInstruction = new PmtInstruction(str3, paramFFSConnectionHolder);
    this.ak = localPmtInstruction.getStatus();
    if (localPmtInstruction.getCustomerID() == null) {
      throw new OFXException(" ", 2018);
    }
    Object localObject2;
    Object localObject3;
    if ((!paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TrnUID.equals("0")) && (Trans.checkDuplicateTIDAndSaveTID(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm.TrnUID)))
    {
      if (this.al >= 3)
      {
        int i = Integer.parseInt(localPmtInstruction.getCustomerID());
        localObject1 = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
        String str4 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
        String str5 = BPWLocaleUtil.getMessage(809, null, "BILLPAY_AUDITLOG_MESSAGE");
        localObject2 = new String[] { str5 };
        localObject3 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject2, "BILLPAY_AUDITLOG_MESSAGE");
        logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, (ILocalizable)localObject3, paramString, 4406, i, (BigDecimal)localObject1, localPmtInstruction.getPmtInfo().CurDef, str3, "DUPLICATE", localPmtInstruction.getPayAcct(), null, str4, null);
      }
      localTypePmtTrnRsV1 = jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2019, " ");
      return localTypePmtTrnRsV1;
    }
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    if (localAuditAgent == null) {
      throw new BPWException("AuditAgent is null!!");
    }
    localTypePmtTrnRsV1 = buildPmtCancRs(paramTypePmtTrnRqV1);
    Object localObject1 = PmtInstruction.getStatus(str3, paramFFSConnectionHolder);
    int j = 1;
    if (localObject1 == null) {
      return jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2018, " ");
    }
    if (((String)localObject1).compareTo("FUNDSREVERTACTIVE") == 0) {
      throw new OFXException("Previous cancel had error, check with CSR!", 2017);
    }
    if (((String)localObject1).compareTo("CANCELEDON") == 0) {
      return jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2017, " ");
    }
    int k;
    if (((String)localObject1).compareTo("WILLPROCESSON") == 0)
    {
      if ((a(str3, str2, "FUNDTRN", paramFFSConnectionHolder) == 0) || (a(str3, str2, "RECFUNDTRN", paramFFSConnectionHolder) == 0)) {
        throw new BPWException("Cannot cancel pmt in process!!");
      }
      k = PmtInstruction.updateStatus(paramFFSConnectionHolder, str3, "CANCELEDON");
      if (k <= 0)
      {
        FFSDebug.log("*** PmtProcessor2. processPmtCancRqV1: Payment status is not updated!!  SrvrTID=" + str3 + "Status=" + "CANCELEDON" + "Response=" + localTypePmtTrnRsV1, 0);
        localObject2 = new StringWriter();
        new Exception("Stack Trace").printStackTrace(new PrintWriter((Writer)localObject2));
        localObject3 = ((StringWriter)localObject2).toString();
        FFSDebug.log((String)localObject3, 0);
      }
      this.ak = "CANCELEDON";
      localAuditAgent.cancelOFX200PmtRsV1(str3, paramFFSConnectionHolder);
    }
    else if (((String)localObject1).compareTo("APPROVAL_PENDING") == 0)
    {
      PmtInstruction.updateStatus(paramFFSConnectionHolder, str3, "CANCELEDON");
      this.ak = "CANCELEDON";
      localAuditAgent.cancelOFX200PmtRsV1(str3, paramFFSConnectionHolder);
    }
    else if (((String)localObject1).compareTo("APPROVAL_REJECTED") == 0)
    {
      PmtInstruction.updateStatus(paramFFSConnectionHolder, str3, "CANCELEDON");
      this.ak = "CANCELEDON";
      localAuditAgent.cancelOFX200PmtRsV1(str3, paramFFSConnectionHolder);
      j = 0;
    }
    else if (((String)localObject1).compareTo("APPROVAL_NOT_ALLOWED") == 0)
    {
      j = 0;
      PmtInstruction.updateStatus(paramFFSConnectionHolder, str3, "CANCELEDON");
      this.ak = "CANCELEDON";
      localAuditAgent.cancelOFX200PmtRsV1(str3, paramFFSConnectionHolder);
    }
    else if (((String)localObject1).compareTo("INFUNDSALLOC") == 0)
    {
      PmtInstruction.updateStatus(paramFFSConnectionHolder, str3, "CANCELFUNDS");
      this.ak = "CANCELFUNDS";
    }
    else if (((String)localObject1).compareTo("FUNDSALLOCATED") == 0)
    {
      j = 0;
      k = localPmtInstruction.getRouteID();
      localObject2 = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
      localObject3 = ((FulfillAgent)localObject2).findPaymentInstructionType(str2, k);
      if (a(str3, str2, (String)localObject3, paramFFSConnectionHolder) == 0) {
        throw new BPWException("Cannot cancel pmt in process!!");
      }
      PmtInstruction.updateStatus(paramFFSConnectionHolder, str3, "FUNDSREVERTACTIVE");
      this.ak = "FUNDSREVERTACTIVE";
      localAuditAgent.cancelOFX200PmtRsV1(str3, paramFFSConnectionHolder);
    }
    else if (((String)localObject1).compareTo("FUNDSALLOCACTIVE") == 0)
    {
      k = localPmtInstruction.getRouteID();
      localObject2 = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
      localObject3 = ((FulfillAgent)localObject2).findPaymentInstructionType(str2, k);
      a(str3, str2, (String)localObject3, paramFFSConnectionHolder);
      a(str3, str2, "FUNDTRN", paramFFSConnectionHolder);
      a(str3, str2, "RECFUNDTRN", paramFFSConnectionHolder);
      j = 0;
      PmtInstruction.updateStatus(paramFFSConnectionHolder, str3, "FUNDSREVERTACTIVE");
      this.ak = "FUNDSREVERTACTIVE";
      localAuditAgent.cancelOFX151PmtRsV1(str3, paramFFSConnectionHolder);
    }
    else if ((((String)localObject1).compareTo("NOFUNDSON") == 0) || (((String)localObject1).compareTo("NOFUNDSON_NOTIF") == 0))
    {
      a(str3, str2, "FUNDTRN", paramFFSConnectionHolder);
      a(str3, str2, "RECFUNDTRN", paramFFSConnectionHolder);
      k = PmtInstruction.updateStatus(paramFFSConnectionHolder, str3, "CANCELEDON");
      if (k <= 0)
      {
        FFSDebug.log("*** PmtProcessor2. processPmtCancRqV1: Payment status is not updated!!  SrvrTID=" + str3 + "Status=" + "CANCELEDON" + "Response=" + localTypePmtTrnRsV1, 0);
        localObject2 = new StringWriter();
        new Exception("Stack Trace").printStackTrace(new PrintWriter((Writer)localObject2));
        localObject3 = ((StringWriter)localObject2).toString();
        FFSDebug.log((String)localObject3, 0);
      }
      this.ak = "CANCELEDON";
      localAuditAgent.cancelOFX200PmtRsV1(str3, paramFFSConnectionHolder);
    }
    else
    {
      return jdMethod_new(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, 2016, " ");
    }
    String str6;
    int n;
    Object localObject5;
    if (j != 0)
    {
      str6 = RecPmtProcessor2.deleteLimit(paramFFSConnectionHolder, localPmtInstruction.getPmtInfo(), new HashMap(), "CANCELEDON", false);
      if (!"LIMIT_REVERT_SUCCEEDED".equals(str6))
      {
        if (this.al >= 3)
        {
          n = Integer.parseInt(localPmtInstruction.getCustomerID());
          localObject3 = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
          String str8 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
          localObject5 = BPWLocaleUtil.getLocalizedMessage(402, null, "BILLPAY_AUDITLOG_MESSAGE");
          logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, (ILocalizable)localObject5, paramString, 4405, n, (BigDecimal)localObject3, localPmtInstruction.getPmtInfo().CurDef, str3, str6, localPmtInstruction.getPayAcct(), null, str8, null);
        }
        throw new OFXException(localPmtInstruction.getPmtInfo().getStatusMsg(), 2000);
      }
    }
    Object localObject4;
    if ((((String)localObject1).compareTo("FUNDSALLOCATED") == 0) || (((String)localObject1).compareTo("FUNDSALLOCACTIVE") == 0))
    {
      paramFFSConnectionHolder.conn.commit();
      str6 = DBConnCache.save(paramFFSConnectionHolder);
      n = ((String)localObject1).compareTo("FUNDSALLOCACTIVE") == 0 ? 1 : 0;
      int i1 = PmtProcessor.revertFunds(str3, str6, n, paramFFSConnectionHolder);
      DBConnCache.unbind(str6);
      if (i1 == 0)
      {
        int i2 = PmtInstruction.updateStatus(paramFFSConnectionHolder, str3, "CANCELEDON");
        if (i2 <= 0)
        {
          FFSDebug.log("*** PmtProcessor2. processPmtCancRqV1: Payment status is not updated!!  SrvrTID=" + str3 + "Status=" + "CANCELEDON" + "Response=" + localTypePmtTrnRsV1, 0);
          localObject5 = new StringWriter();
          new Exception("Stack Trace").printStackTrace(new PrintWriter((Writer)localObject5));
          String str9 = ((StringWriter)localObject5).toString();
          FFSDebug.log(str9, 0);
        }
        localObject5 = RecPmtProcessor2.deleteLimit(paramFFSConnectionHolder, localPmtInstruction.getPmtInfo(), new HashMap(), "CANCELEDON", true);
        if (!"LIMIT_REVERT_SUCCEEDED".equals(localObject5))
        {
          if (this.al >= 3)
          {
            int i3 = Integer.parseInt(localPmtInstruction.getCustomerID());
            BigDecimal localBigDecimal2 = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
            String str10 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
            LocalizableString localLocalizableString2 = BPWLocaleUtil.getLocalizedMessage(402, null, "BILLPAY_AUDITLOG_MESSAGE");
            logTransAuditLogError(paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString2, paramString, 4405, i3, localBigDecimal2, localPmtInstruction.getPmtInfo().CurDef, str3, (String)localObject5, localPmtInstruction.getPayAcct(), null, str10, null);
          }
          throw new OFXException(localPmtInstruction.getPmtInfo().getStatusMsg(), 2000);
        }
        this.ak = "CANCELEDON";
      }
      else
      {
        localObject4 = new FundsAllocRslt(str1, str3, i1);
        FundAllocRsltProcessor.ProcessOneFundRevertRslt((FundsAllocRslt)localObject4, paramFFSConnectionHolder);
      }
    }
    if (this.al >= 3)
    {
      int m = Integer.parseInt(localPmtInstruction.getCustomerID());
      BigDecimal localBigDecimal1 = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
      String str7 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
      localObject4 = BPWLocaleUtil.getMessage(808, null, "BILLPAY_AUDITLOG_MESSAGE");
      localObject5 = new String[] { localObject4 };
      LocalizableString localLocalizableString1 = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject5, "BILLPAY_AUDITLOG_MESSAGE");
      TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), paramTypeUserData._submittedBy, paramTypeUserData._agentID, paramTypeUserData._agentType, localLocalizableString1, paramString, 4405, m, localBigDecimal1, localPmtInstruction.getPmtInfo().CurDef, str3, this.ak, localPmtInstruction.getPayAcct(), null, str7, null, 0);
    }
    FFSDebug.log("PAyment Calcel done SrvrTID=" + str3, 6);
    return localTypePmtTrnRsV1;
  }
  
  private int a(PmtInstruction paramPmtInstruction, PayeeInfo paramPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder, HashMap paramHashMap)
    throws Exception
  {
    PmtInfo localPmtInfo = paramPmtInstruction.getPmtInfo();
    BankInfo localBankInfo = BPWRegistryUtil.getBankInfo(localPmtInfo.BankID);
    PropertyConfig localPropertyConfig = null;
    String str1 = null;
    if (localBankInfo == null)
    {
      localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      str2 = localPropertyConfig.otherProperties.getProperty("bpw.pmt.bankinfo.lookup.level", String.valueOf("STRICT"));
      if (str2.equalsIgnoreCase("STRICT"))
      {
        localObject = "*** PmtProcessor2.doImmediateFundsAllocation failed:could not find the BankID in BPW_Bank, bpw.pmt.bankinfo.lookup.level = STRICT ";
        FFSDebug.console((String)localObject);
        FFSDebug.log((String)localObject, 0);
        throw new Exception((String)localObject);
      }
      if (str2.equalsIgnoreCase("LENIENT"))
      {
        localObject = "*** PmtProcessor2.doImmediateFundsAllocation : bpw.pmt.bankinfo.lookup.level = LENIENT ";
        FFSDebug.log((String)localObject, 1);
      }
      else if (str2.equalsIgnoreCase("NONE"))
      {
        localObject = "*** PmtProcessor2.doImmediateFundsAllocation : bpw.pmt.bankinfo.lookup.level = NONE ";
        FFSDebug.log((String)localObject, 6);
      }
      else
      {
        localObject = "*** PmtProcessor2.doImmediateFundsAllocation failed: Incorrect value for bpw.pmt.bankinfo.lookup.level =" + str2;
        FFSDebug.console((String)localObject);
        FFSDebug.log((String)localObject);
        throw new Exception((String)localObject);
      }
      str1 = String.valueOf(-1);
    }
    else
    {
      str1 = localBankInfo.debitGLAcct;
    }
    String str2 = DBConnCache.save(paramFFSConnectionHolder);
    Object localObject = new FundsAllocInfo(localPmtInfo.FIID, localPmtInfo.CustomerID, localPmtInfo.BankID, localPmtInfo.AcctDebitID, localPmtInfo.AcctDebitType, localPmtInfo.getAmt(), localPmtInfo.CurDef, localPmtInfo.SrvrTID, localPmtInfo.PayeeID, paramPayeeInfo.PayeeName, 1, false, str1, str2, localPmtInfo.RecSrvrTID);
    if ((paramHashMap != null) && (paramHashMap.size() != 0)) {
      ((FundsAllocInfo)localObject).extraFields = paramHashMap;
    }
    FundsAllocator localFundsAllocator = new FundsAllocator();
    int i = -1;
    String str3 = "WILLPROCESSON";
    try
    {
      i = localFundsAllocator.processFundsAllocation((FundsAllocInfo)localObject);
      FundsAllocRslt localFundsAllocRslt = new FundsAllocRslt(localPmtInfo.CustomerID, localPmtInfo.SrvrTID, i, str2);
      FundAllocRsltProcessor.ProcessOneFundAllocRsltImmediate(localFundsAllocRslt, paramPmtInstruction, paramFFSConnectionHolder);
    }
    catch (Throwable localThrowable)
    {
      if (str3.equals("FUNDSALLOCATED"))
      {
        PmtProcessor.revertFunds(localPmtInfo.SrvrTID, str2, false, paramFFSConnectionHolder);
        FFSDebug.log("doImmediateFundsAllocation Fund reverted for: " + localPmtInfo.SrvrTID, 0);
      }
      throw new Exception("AddPayment Failed" + FFSDebug.stackTrace(localThrowable));
    }
    DBConnCache.unbind(str2);
    return i;
  }
  
  private int a(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    int i = ScheduleInfo.checkScheduleStatus(paramString1, paramString2, paramString3, paramFFSConnectionHolder);
    if (i == 0) {
      return ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, paramString3, paramString1);
    }
    return 0;
  }
  
  public PayeeInfo processImplicitPayeeRq(TypePmtInfoAggregate paramTypePmtInfoAggregate, String paramString1, FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, String paramString2)
    throws OFXException, Exception
  {
    int i = jdMethod_int();
    boolean bool1 = paramTypePmtInfoAggregate.PayeeLstIDExists;
    boolean bool2 = paramTypePmtInfoAggregate.PayeeUn.__memberName.equals("PayeeID");
    boolean bool3 = paramTypePmtInfoAggregate.PayeeUn.__memberName.equals("Payee");
    String str1 = paramTypePmtInfoAggregate.NameOnAcctExists ? paramTypePmtInfoAggregate.NameOnAcct : null;
    int j = 0;
    if (bool1) {
      try
      {
        j = Integer.parseInt(paramTypePmtInfoAggregate.PayeeLstID);
      }
      catch (Exception localException)
      {
        throw new OFXException(" ", 10519);
      }
    }
    Object localObject1;
    Object localObject2;
    Object localObject3;
    String str2;
    PayeeInfo localPayeeInfo;
    if ((bool1) && (bool2))
    {
      j = Integer.parseInt(paramTypePmtInfoAggregate.PayeeLstID);
      localObject1 = new CustPayee();
      ((CustPayee)localObject1).findCustPayeeByPayeeListID(paramString1, j, paramFFSConnectionHolder1);
      localObject2 = ((CustPayee)localObject1).getStatus();
      if (localObject2 == null) {
        throw new OFXException(" ", 10519);
      }
      if (((String)localObject2).equals("CLOSED")) {
        throw new OFXException(" ", 10519);
      }
      localObject3 = CustPayeeRoute.getActiveCustPayeeRoute(paramString1, j, paramFFSConnectionHolder1);
      if (localObject3 == null) {
        throw new OFXException(" ", 10519);
      }
      if ((((CustPayeeRoute)localObject3).Status.equals("CLOSED")) || (((CustPayeeRoute)localObject3).Status.equals("CANC")) || (((CustPayeeRoute)localObject3).Status.equals("PENDING")) || (((CustPayeeRoute)localObject3).Status.equals("CANC_INPROCESS"))) {
        throw new OFXException(" ", 10519);
      }
      if ((((CustPayeeRoute)localObject3).Status.equals("FAILEDON")) || (((CustPayeeRoute)localObject3).Status.equals("ERROR"))) {
        throw new OFXException(" ", 10519);
      }
      str2 = ((CustPayee)localObject1).getPayeeID();
      if (str2 == null) {
        throw new OFXException(" ", 10519);
      }
      if (!((CustPayee)localObject1).getPayAcct().equals(paramTypePmtInfoAggregate.PayAcct)) {
        throw new OFXException(" ", 10503);
      }
      localPayeeInfo = Payee.findPayeeByID(str2, paramFFSConnectionHolder1);
      if (localPayeeInfo == null) {
        throw new OFXException(" ", 10519);
      }
      if ((localPayeeInfo.Status.equals("INACTIVE")) || (localPayeeInfo.Status.equals("CLOSED"))) {
        throw new OFXException("Payee is not Active", 2000);
      }
      int k = Payee.findRoute(str2, paramFFSConnectionHolder1);
      if (k == -1) {
        throw new OFXException(" ", 10519);
      }
      return localPayeeInfo;
    }
    if ((bool1) && (bool3))
    {
      localObject1 = new CustomerPayeeInfo(paramString1, "", j, paramTypePmtInfoAggregate.PayAcct, str1, "", 0, "", "", -1, -1);
      localObject2 = new PayeeProcessor2();
      localPayeeInfo = ((PayeeProcessor2)localObject2).modPayee(paramFFSConnectionHolder1, paramFFSConnectionHolder2, true, (CustomerPayeeInfo)localObject1, paramTypePmtInfoAggregate.PayeeUn.Payee, paramString2);
      paramTypePmtInfoAggregate.PayeeLstID = Integer.toString(((CustomerPayeeInfo)localObject1).PayeeListID);
      localPayeeInfo.RouteID = i;
      return localPayeeInfo;
    }
    if (!bool1)
    {
      localObject1 = new Payee();
      localObject2 = null;
      if (bool2)
      {
        localObject2 = paramTypePmtInfoAggregate.PayeeUn.PayeeID;
        if (!this.ao) {
          localPayeeInfo = Payee.findPayeeByID((String)localObject2, paramFFSConnectionHolder1);
        } else {
          localPayeeInfo = Payee.findPayeeByExtendedID((String)localObject2, paramFFSConnectionHolder1);
        }
        if (localPayeeInfo == null) {
          throw new OFXException(" ", 10510);
        }
        if ((localPayeeInfo.Status.equals("INACTIVE")) || (localPayeeInfo.Status.equals("CLOSED"))) {
          throw new OFXException("Payee is not Active", 2000);
        }
        ((Payee)localObject1).setPayeeInfo(localPayeeInfo);
      }
      else if (bool3)
      {
        if (!this.an)
        {
          ((Payee)localObject1).setRouteID(i);
          localObject2 = ((Payee)localObject1).addPayee(paramTypePmtInfoAggregate.PayeeUn.Payee, paramFFSConnectionHolder1, paramString2);
        }
        else
        {
          localObject2 = ((Payee)localObject1).matchGlobalPayee(paramTypePmtInfoAggregate.PayeeUn.Payee, paramFFSConnectionHolder1);
          if (localObject2 == null)
          {
            ((Payee)localObject1).setRouteID(i);
            ((Payee)localObject1).addPayeeNoMatch(paramTypePmtInfoAggregate.PayeeUn.Payee, paramFFSConnectionHolder1, paramString2);
            localObject2 = ((Payee)localObject1).getPayeeID();
          }
        }
        localPayeeInfo = Payee.findPayeeByID((String)localObject2, paramFFSConnectionHolder1);
        if ((localPayeeInfo.Status.equals("INACTIVE")) || (localPayeeInfo.Status.equals("CLOSED"))) {
          throw new OFXException("Payee is not Active", 2000);
        }
      }
      else
      {
        throw new OFXException("Unsupported request type", 2000);
      }
      localObject3 = new CustPayee();
      str2 = paramTypePmtInfoAggregate.PayAcct;
      j = ((CustPayee)localObject3).addCustPayee(paramString1, localPayeeInfo.PayeeID, str2, str1, localPayeeInfo.RouteID, paramFFSConnectionHolder1, paramFFSConnectionHolder2);
      paramTypePmtInfoAggregate.PayeeLstID = Integer.toString(j);
      return localPayeeInfo;
    }
    return null;
  }
  
  public PmtInstruction createPmtInstr(TypePmtInfoAggregate paramTypePmtInfoAggregate, String paramString1, String paramString2, String paramString3, String paramString4, TypeUserData paramTypeUserData, EnumCurrencyEnum paramEnumCurrencyEnum)
    throws OFXException, Exception, BPWException
  {
    PmtInstruction localPmtInstruction = new PmtInstruction();
    localPmtInstruction.setCustomerID(paramString1);
    localPmtInstruction.setStatus("WILLPROCESSON");
    if (paramTypePmtInfoAggregate.DtDue != null)
    {
      String str1 = paramTypePmtInfoAggregate.DtDue.substring(0, 8);
      if (BPWUtil.validateDate(str1, "yyyyMMdd") == true)
      {
        int i = DBUtil.getCurrentStartDate();
        int j = BPWUtil.getDateDBFormat(str1);
        if (j < i)
        {
          String str2 = BPWLocaleUtil.getMessage(100002, null, "OFX_MESSAGE");
          throw new BPWException(str2, 100002);
        }
        if (j == i) {
          j++;
        }
        localPmtInstruction.setStartDate(j);
      }
      else
      {
        localObject = BPWLocaleUtil.getMessage(100000, null, "OFX_MESSAGE");
        throw new BPWException((String)localObject, 100000);
      }
    }
    else
    {
      localObject = BPWLocaleUtil.getMessage(100000, null, "OFX_MESSAGE");
      throw new BPWException((String)localObject, 100000);
    }
    if (paramString4 == null) {
      localPmtInstruction.mapFromPmtInfoV1(paramTypePmtInfoAggregate, "Current", paramString4, paramString2, paramTypeUserData, paramEnumCurrencyEnum);
    } else {
      localPmtInstruction.mapFromPmtInfoV1(paramTypePmtInfoAggregate, "Recurring", paramString4, paramString2, paramTypeUserData, paramEnumCurrencyEnum);
    }
    Object localObject = localPmtInstruction.getPmtInfo();
    ((PmtInfo)localObject).LogID = paramString3;
    return localPmtInstruction;
  }
  
  public ScheduleInfo createScheduleInfo(TypePmtInfoAggregate paramTypePmtInfoAggregate, String paramString1, String paramString2, String paramString3)
    throws OFXException, Exception
  {
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramString2;
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = 10;
    String str = paramTypePmtInfoAggregate.DtDue.substring(0, 8) + "00";
    try
    {
      localScheduleInfo.StartDate = Integer.parseInt(str);
    }
    catch (Exception localException)
    {
      throw new OFXException("Date format not correct", 2000);
    }
    localScheduleInfo.InstanceCount = 1;
    localScheduleInfo.LogID = paramString3;
    localScheduleInfo.CurInstanceNum = 1;
    return localScheduleInfo;
  }
  
  public TypePmtTrnRsV1 buildPmtAddRs(TypePmtTrnRqV1 paramTypePmtTrnRqV1, String paramString1, String paramString2, String paramString3, PayeeInfo paramPayeeInfo, String paramString4)
    throws Exception
  {
    TypePmtTrnRsV1Aggregate localTypePmtTrnRsV1Aggregate = new TypePmtTrnRsV1Aggregate();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists = true;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un = new TypePmtTrnRsV1Un();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName = "PmtRs";
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs = new TypePmtRsAggregate();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.SrvrTID = paramString2;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PayeeLstID = paramString3;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CurDef = MsgBuilder.getOFX200CurrencyEnum(paramString4);
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = false;
    if ((!this.ao) || (!paramPayeeInfo.ExtdPayeeID.equals("0")))
    {
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayeeExists = true;
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee = new TypeExtdPayeeAggregate();
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCmExists = true;
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm = new TypeExtdPayeeCm();
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm.PayeeID = (!this.ao ? paramPayeeInfo.PayeeID : paramPayeeInfo.ExtdPayeeID);
      localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm.Name = paramPayeeInfo.PayeeName;
      if (paramPayeeInfo.PayeeType == 3) {
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm.IDScope = EnumIDScopeEnum.USER;
      } else {
        localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.ExtdPayee.ExtdPayeeCm.IDScope = EnumIDScopeEnum.GLOBAL;
      }
    }
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.CheckNumExists = false;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.RecSrvrTIDExists = false;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstIDExists = true;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtInfo.PayeeLstID = paramString3;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts = new TypePmtPrcStsAggregate();
    String str = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtRq.PmtInfo.DtDue.substring(0, 8);
    int i;
    try
    {
      i = Integer.parseInt(str);
    }
    catch (Exception localException)
    {
      throw new OFXException("Date format not correct", 2000);
    }
    int j = SmartCalendar.getPayday(paramString1, i);
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.DtPmtPrc = ("" + j + "000000");
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtRs.PmtPrcSts.PmtPrcCode = EnumPmtProcessStatusEnum.WILLPROCESSON;
    localTypePmtTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, null);
    return new TypePmtTrnRsV1(localTypePmtTrnRsV1Aggregate);
  }
  
  public TypePmtTrnRsV1 buildPmtModRs(TypePmtTrnRqV1 paramTypePmtTrnRqV1, String paramString1, String paramString2, PayeeInfo paramPayeeInfo, String paramString3)
    throws Exception
  {
    TypePmtTrnRsV1Aggregate localTypePmtTrnRsV1Aggregate = new TypePmtTrnRsV1Aggregate();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists = true;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un = new TypePmtTrnRsV1Un();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName = "PmtModRs";
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs = new TypePmtModRsAggregate();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.SrvrTID = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.SrvrTID;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.CurDefExists = true;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.CurDef = MsgBuilder.getOFX200CurrencyEnum(paramString3);
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtInfo = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.PmtInfo;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtInfo.PayeeLstIDExists = true;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtInfo.PayeeLstID = paramString2;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtPrcSts = new TypePmtPrcStsAggregate();
    String str = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtModRq.PmtInfo.DtDue.substring(0, 8);
    int i;
    try
    {
      i = Integer.parseInt(str);
    }
    catch (Exception localException)
    {
      throw new OFXException("Date format not correct", 2000);
    }
    int j = SmartCalendar.getPayday(paramString1, i);
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtPrcSts.DtPmtPrc = ("" + j + "000000");
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtModRs.PmtPrcSts.PmtPrcCode = EnumPmtProcessStatusEnum.WILLPROCESSON;
    localTypePmtTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, null);
    return new TypePmtTrnRsV1(localTypePmtTrnRsV1Aggregate);
  }
  
  public TypePmtTrnRsV1 buildPmtCancRs(TypePmtTrnRqV1 paramTypePmtTrnRqV1)
    throws Exception
  {
    TypePmtTrnRsV1Aggregate localTypePmtTrnRsV1Aggregate = new TypePmtTrnRsV1Aggregate();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists = true;
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un = new TypePmtTrnRsV1Un();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.__memberName = "PmtCancRs";
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtCancRs = new TypePmtCancRsAggregate();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1Un.PmtCancRs.SrvrTID = paramTypePmtTrnRqV1.PmtTrnRq.PmtTrnRqUn.PmtCancRq.SrvrTID;
    localTypePmtTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypePmtTrnRqV1.PmtTrnRq.TrnRqCm, null);
    return new TypePmtTrnRsV1(localTypePmtTrnRsV1Aggregate);
  }
  
  private TypePmtTrnRsV1 jdMethod_new(TypeTrnRqCm paramTypeTrnRqCm, int paramInt, String paramString)
  {
    TypePmtTrnRsV1Aggregate localTypePmtTrnRsV1Aggregate = new TypePmtTrnRsV1Aggregate();
    localTypePmtTrnRsV1Aggregate.PmtTrnRsV1UnExists = false;
    localTypePmtTrnRsV1Aggregate.TrnRsCm = RsCmBuilder.buildTrnRsCmV1(paramTypeTrnRqCm, paramInt, paramString);
    return new TypePmtTrnRsV1(localTypePmtTrnRsV1Aggregate);
  }
  
  public static String getUID(TypeUserData paramTypeUserData)
  {
    return paramTypeUserData._uid;
  }
  
  private int jdMethod_int()
    throws BPWException
  {
    FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
    if (localFulfillAgent == null) {
      throw new BPWException("FulfillAgent is null!!");
    }
    return localFulfillAgent.getDefaultFulfillment();
  }
  
  public PmtInfo getPmtById(String paramString)
    throws Exception
  {
    FFSDebug.log("PmtProcessor2.getPmtById : start ", 6);
    PmtInfo localPmtInfo1 = null;
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      localPmtInfo1 = new PmtInfo();
      localPmtInfo1.statusCode = 16504;
      localPmtInfo1.statusMsg = "ID  is null or empty";
      FFSDebug.log("PmtProcessor2.getPmtById : failed null or empty id passed", 0);
      return localPmtInfo1;
    }
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    String str;
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new Exception("Null connection returned");
      }
    }
    catch (Exception localException1)
    {
      str = "PmtProcessor2.getPmtById : failed to get DB connection";
      FFSDebug.log(str + FFSDebug.stackTrace(localException1), 0);
      throw new BPWException(str + localException1.toString());
    }
    try
    {
      localPmtInfo1 = PmtInstruction.getPmtInfo(paramString, localFFSConnectionHolder);
      if (localPmtInfo1 == null)
      {
        localPmtInfo1 = new PmtInfo();
        localPmtInfo1.statusCode = 17020;
        localPmtInfo1.statusMsg = "No Records Found";
      }
      else
      {
        localPmtInfo1.statusCode = 0;
        localPmtInfo1.statusMsg = "Success";
      }
      localFFSConnectionHolder.conn.commit();
      PmtInfo localPmtInfo2 = localPmtInfo1;
      return localPmtInfo2;
    }
    catch (Exception localException2)
    {
      str = "PmtProcessor2.getPmtById : failed ";
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log(str + FFSDebug.stackTrace(localException2), 0);
      throw new BPWException(str + localException2.toString());
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public PmtInfo[] getPmtById(String[] paramArrayOfString)
    throws Exception
  {
    FFSDebug.log("PmtProcessor2.getPmtById (multiple): start ", 6);
    PmtInfo localPmtInfo = null;
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      localPmtInfo = new PmtInfo();
      localPmtInfo.statusCode = 16504;
      localPmtInfo.statusMsg = "ID Array is null or empty";
      FFSDebug.log("PmtProcessor2.getPmtById (multiple): failed null or empty id array passed", 0);
      localObject1 = new PmtInfo[] { localPmtInfo };
      return localObject1;
    }
    Object localObject1 = new FFSConnectionHolder();
    Object localObject2;
    try
    {
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      if (((FFSConnectionHolder)localObject1).conn == null) {
        throw new Exception("Null connection returned");
      }
    }
    catch (Exception localException1)
    {
      localObject2 = "PmtProcessor2.getPmtById (multiple) : failed to get DB connection";
      FFSDebug.log((String)localObject2 + FFSDebug.stackTrace(localException1), 0);
      throw new BPWException((String)localObject2 + localException1.toString());
    }
    try
    {
      PmtInfo[] arrayOfPmtInfo = PmtInstruction.getPmtInfo(paramArrayOfString, (FFSConnectionHolder)localObject1);
      ((FFSConnectionHolder)localObject1).conn.commit();
      localObject2 = arrayOfPmtInfo;
      return localObject2;
    }
    catch (Exception localException2)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      localObject2 = "PmtProcessor2.getPmtById (multiple): failed ";
      FFSDebug.log((String)localObject2 + FFSDebug.stackTrace(localException2), 0);
      throw new BPWException((String)localObject2 + localException2.toString());
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
  }
  
  private static void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, TypeUserData paramTypeUserData, String paramString1, String paramString2)
    throws BPWException
  {
    FFSDebug.log("PmtProcessor2.processExtdPmtInfo: start, srvrTID =" + paramString1 + ", mode =" + paramString2, 6);
    try
    {
      if ((paramTypeUserData._userDefined != null) && ((paramTypeUserData._userDefined instanceof Hashtable))) {
        if (paramString2.equals("mod")) {
          BPWExtraInfo.processImplicitMod(paramFFSConnectionHolder, DBUtil.getFIId(paramTypeUserData), paramString1, "IFXPMT", (Hashtable)paramTypeUserData._userDefined);
        } else if ((paramString2.equals("add")) && (((Hashtable)paramTypeUserData._userDefined).size() > 0)) {
          BPWExtraInfo.processXtraInfo(paramFFSConnectionHolder, DBUtil.getFIId(paramTypeUserData), paramString1, "IFXPMT", (Hashtable)paramTypeUserData._userDefined);
        }
      }
    }
    catch (Exception localException)
    {
      String str = "*** PmtProcessor2.processExtdPmtInfo: failed ";
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new BPWException(localException + localException.toString());
    }
    FFSDebug.log("PmtProcessor2.processExtdPmtInfo: end", 6);
  }
  
  private void jdMethod_int(Hashtable paramHashtable, FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws BPWException
  {
    String str = null;
    try
    {
      FFSDebug.log("PmtProcessor2.processXtraPmtInfo start: ", 6);
      if (paramString2.equals("Add"))
      {
        if ((paramHashtable != null) && (!paramHashtable.isEmpty())) {
          PmtExtraInfo.insertHashtable(paramString1, paramHashtable, paramFFSConnectionHolder);
        }
      }
      else if (paramString2.equals("Mod")) {
        if ((paramHashtable != null) && (!paramHashtable.isEmpty())) {
          PmtExtraInfo.updateHashtable(paramString1, paramHashtable, paramFFSConnectionHolder);
        } else {
          PmtExtraInfo.deleteAllWithSrvrTID(paramString1, paramFFSConnectionHolder);
        }
      }
    }
    catch (Exception localException)
    {
      str = "PmtProcessor2.processXtraPmtInfo : failed ";
      FFSDebug.log(str + FFSDebug.stackTrace(localException), 0);
      throw new BPWException(str + localException.toString());
    }
    FFSDebug.log("PmtProcessor2.processXtraPmtInfo end: ", 6);
  }
  
  public static void logTransAuditLogError(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11, String paramString12)
    throws FFSException
  {
    String str = "PmProcessor2.logTransAuditLogError";
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      FFSDebug.log(str + ":starts");
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str + ":Can not get DB Connection.");
      }
      TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), paramString1, paramString2, paramString3, paramString4, paramString5, paramInt1, paramInt2, paramBigDecimal, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, paramString12, 0);
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
  
  public static void logTransAuditLogError(String paramString1, String paramString2, String paramString3, ILocalizable paramILocalizable, String paramString4, int paramInt1, int paramInt2, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11)
    throws FFSException
  {
    String str = "PmProcessor2.logTransAuditLogError";
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      FFSDebug.log(str + ":starts");
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str + ":Can not get DB Connection.");
      }
      TransAuditLog.logTransAuditLog(localFFSConnectionHolder.conn.getConnection(), paramString1, paramString2, paramString3, paramILocalizable, paramString4, paramInt1, paramInt2, paramBigDecimal, paramString5, paramString6, paramString7, paramString8, paramString9, paramString10, paramString11, 0);
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
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PmtProcessor2
 * JD-Core Version:    0.7.0.1
 */