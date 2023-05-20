package com.ffusion.ffs.bpw.master;

import com.ffusion.csil.beans.entitlements.Limits;
import com.ffusion.ffs.bpw.BPWServer;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.custimpl.ToBackend;
import com.ffusion.ffs.bpw.custimpl.ifx.transfer.TransformIFXRecXferInq;
import com.ffusion.ffs.bpw.custimpl.transfers.TransferFundsProcessing;
import com.ffusion.ffs.bpw.db.ACHSameDayEffDate;
import com.ffusion.ffs.bpw.db.BPWFI;
import com.ffusion.ffs.bpw.db.BPWRecXferExtraInfo;
import com.ffusion.ffs.bpw.db.BPWXferExtraInfo;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.ExternalTransferAccount;
import com.ffusion.ffs.bpw.db.RecSrvrTIDToSrvrTID;
import com.ffusion.ffs.bpw.db.RecXferInstruction;
import com.ffusion.ffs.bpw.db.SmartCalendar;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.db.Trans;
import com.ffusion.ffs.bpw.db.Transfer;
import com.ffusion.ffs.bpw.db.TransferBatch;
import com.ffusion.ffs.bpw.db.XferInstruction;
import com.ffusion.ffs.bpw.interfaces.ACHConsts;
import com.ffusion.ffs.bpw.interfaces.AccountTransactions;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWHist;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.BankingDays;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.ExtTransferAcctInfo;
import com.ffusion.ffs.bpw.interfaces.IntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.IntraTrnRslt;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.RecIntraTrnInfo;
import com.ffusion.ffs.bpw.interfaces.RecTransferInfo;
import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
import com.ffusion.ffs.bpw.interfaces.TransferBatchInfo;
import com.ffusion.ffs.bpw.interfaces.TransferCalloutHandlerBase;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.interfaces.TransferIntraMap;
import com.ffusion.ffs.bpw.interfaces.ValueInfo;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWTrackingIDGenerator;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.Scheduler;
import com.ffusion.ffs.scheduling.db.InstructionTypeStatus;
import com.ffusion.ffs.scheduling.db.ScheduleConstants;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.CommBankIdentifier;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.enums.UserAssignedAmount;
import com.ffusion.util.logging.AuditLogRecord;
import com.ffusion.util.logging.AuditLogUtil;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import org.apache.commons.lang.StringUtils;

public class TransferProcessor
  implements DBConsts, FFSConst, ACHConsts, BPWResource, ScheduleConstants
{
  private static PropertyConfig b5;
  private int b6 = 0;
  private String ce;
  private PagedData ci = null;
  private boolean b7 = true;
  private boolean cd = false;
  private boolean cb = true;
  private boolean ch = false;
  private boolean cg = false;
  private boolean b8 = false;
  private boolean b9 = false;
  private boolean ca = false;
  private boolean cf = false;
  private RecXferProcessor2 cc;
  
  public TransferProcessor()
  {
    b5 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.b6 = b5.LogLevel;
    FFSDebug.log("TransferProcessor: Audit log level value" + this.b6, 6);
    try
    {
      String str1 = b5.otherProperties.getProperty("bpw.transfer.manageCustomers", "BPW");
      if (str1.equalsIgnoreCase("EXTERNAL")) {
        this.b7 = false;
      }
      String str2 = b5.otherProperties.getProperty("bpw.transfer.enforce.valid.due.date", "false");
      if (str2.equalsIgnoreCase("true")) {
        this.cd = true;
      }
      this.cb = (!b5.NoImmediateTransfer);
      String str3 = b5.otherProperties.getProperty("bpw.transfer.ItoE.support.immediate", "false");
      if (str3.equals("true")) {
        this.ch = true;
      }
      str3 = b5.otherProperties.getProperty("bpw.transfer.EtoI.support.immediate", "false");
      if (str3.equals("true")) {
        this.cg = true;
      }
      String str4 = b5.otherProperties.getProperty("bpw.transfer.ItoE.immediate.funds.support", "false");
      if (str4.equals("true")) {
        this.b8 = true;
      }
      str4 = b5.otherProperties.getProperty("bpw.transfer.ItoE.scheduled.funds.support", "false");
      if (str4.equalsIgnoreCase("true")) {
        this.b9 = true;
      }
      str4 = b5.otherProperties.getProperty("bpw.transfer.EtoI.immediate.funds.support", "false");
      if (str4.equalsIgnoreCase("true")) {
        this.ca = true;
      }
      str4 = b5.otherProperties.getProperty("bpw.transfer.EtoI.scheduled.funds.support", "false");
      if (str4.equalsIgnoreCase("true")) {
        this.cf = true;
      }
      this.ce = b5.otherProperties.getProperty("bpw.transaction.base.currency", "USD");
    }
    catch (Exception localException) {}
    FFSDebug.log("TransferProcessor: bpwManagedCustomer = " + this.b7, 6);
    this.ci = new PagedTransfer();
    this.cc = new RecXferProcessor2(new XferProcessor2());
  }
  
  public PagingInfo getPagedTransfer(PagingInfo paramPagingInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.getPagedTransfer ";
    FFSDebug.log(str1 + "start...", 6);
    try
    {
      this.ci.getPagedData(paramPagingInfo);
    }
    catch (FFSException localFFSException)
    {
      str2 = "*** " + str1 + " failed: ";
      str3 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str2, str3, 0);
      throw localFFSException;
    }
    catch (Exception localException)
    {
      String str2 = "*** " + str1 + "failed: ";
      String str3 = FFSDebug.stackTrace(localException);
      FFSDebug.log(str3, 0);
      throw new FFSException(localException, str2);
    }
    return paramPagingInfo;
  }
  
  public TransferInfo addTransfer(TransferInfo paramTransferInfo)
    throws FFSException
  {
    FFSDebug.log("TransferProcessor.addTransfer: ", ": Start ", 6);
    if (paramTransferInfo == null)
    {
      paramTransferInfo = new TransferInfo();
      paramTransferInfo.setStatusCode(16000);
      str1 = "TransferInfo object  is null";
      paramTransferInfo.setStatusMsg(str1);
      FFSDebug.log("TransferProcessor.addTransfer: ", str1, 0);
      return paramTransferInfo;
    }
    if (jdMethod_goto(paramTransferInfo.getSubmittedBy())) {
      paramTransferInfo.setSubmittedBy(paramTransferInfo.getOriginatingUserId());
    }
    String str1 = jdMethod_do(paramTransferInfo);
    if (jdMethod_goto(str1))
    {
      String str2 = BPWLocaleUtil.getMessage(21261, null, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusCode(21261);
      paramTransferInfo.setStatusMsg(str2);
      FFSDebug.log("TransferProcessor.addTransfer: " + str2, 0);
      return paramTransferInfo;
    }
    boolean bool = jdMethod_byte(paramTransferInfo);
    String str3;
    if ((bool) && (jdMethod_goto(paramTransferInfo.getTemplateNickName())))
    {
      paramTransferInfo.setStatusCode(16010);
      str3 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "Template Name" }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusMsg(str3);
      FFSDebug.log("TransferProcessor.addTransfer, ", str3, 0);
      return paramTransferInfo;
    }
    if ((bool) && (Transfer.isDuplicateTemplate(paramTransferInfo.getTemplateNickName(), paramTransferInfo.getSubmittedBy(), paramTransferInfo.getSrvrTId(), false)))
    {
      paramTransferInfo.setStatusCode(21710);
      str3 = BPWLocaleUtil.getMessage(21710, new String[] { paramTransferInfo.getTemplateNickName() }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusMsg(str3);
      FFSDebug.log("TransferProcessor.addTransfer, " + str3, 0);
      return paramTransferInfo;
    }
    if (str1.equalsIgnoreCase("ETOE"))
    {
      str3 = BPWLocaleUtil.getMessage(21262, new String[] { str1 }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusCode(21262);
      paramTransferInfo.setStatusMsg(str3);
      FFSDebug.log("TransferProcessor.addTransfer: " + str3, 0);
      return paramTransferInfo;
    }
    if ((jdMethod_char(paramTransferInfo)) && (!bool))
    {
      int i = Integer.parseInt(FFSUtil.getDateString("yyyyMMdd"));
      int j = Integer.parseInt(paramTransferInfo.getDateDue());
      if (paramTransferInfo.getProcessType() == 0) {
        if ((this.cb) && (j <= i)) {
          paramTransferInfo.setProcessType(2);
        } else {
          paramTransferInfo.setProcessType(1);
        }
      }
      if (paramTransferInfo.getProcessType() == 2)
      {
        if (j > i)
        {
          paramTransferInfo.setStatusCode(17203);
          paramTransferInfo.setStatusMsg("Invalid due date value for batch.");
          FFSDebug.log("TransferProcessor.addTransfer: " + paramTransferInfo.getStatusMsg(), 0);
          return paramTransferInfo;
        }
        paramTransferInfo.setDateDue(null);
      }
    }
    else if ((!str1.equalsIgnoreCase("ITOE")) && (!str1.equalsIgnoreCase("ETOI")) && (!str1.equalsIgnoreCase("ITOI")))
    {
      String str4 = BPWLocaleUtil.getMessage(21262, new String[] { str1 }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusCode(21262);
      paramTransferInfo.setStatusMsg(str4);
      FFSDebug.log("TransferProcessor.addTransfer: " + str4, 0);
      return paramTransferInfo;
    }
    try
    {
      if (bool) {
        paramTransferInfo.setDateDue(null);
      }
      paramTransferInfo = jdMethod_do(paramTransferInfo, false);
    }
    catch (Throwable localThrowable)
    {
      String str5 = "*** TransferProcessor.addTransfer failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str5 + str6, 0);
      throw new FFSException(localThrowable, str5);
    }
    return paramTransferInfo;
  }
  
  public RecTransferInfo addRecTransfer(RecTransferInfo paramRecTransferInfo)
    throws FFSException
  {
    if (paramRecTransferInfo == null)
    {
      paramRecTransferInfo = new RecTransferInfo();
      paramRecTransferInfo.setStatusCode(16000);
      str1 = "RecTransferInfo object  is null";
      paramRecTransferInfo.setStatusMsg(str1);
      FFSDebug.log("TransferProcessor.addRecTransfer, " + str1, 0);
      return paramRecTransferInfo;
    }
    String str1 = paramRecTransferInfo.getTransferType();
    int i = 0;
    if ((str1 != null) && (str1.trim().equalsIgnoreCase("RECTEMPLATE"))) {
      i = 1;
    }
    if ((i != 0) && (Transfer.isDuplicateTemplate(paramRecTransferInfo.getTemplateNickName(), paramRecTransferInfo.getSubmittedBy(), paramRecTransferInfo.getSrvrTId(), false)))
    {
      paramRecTransferInfo.setStatusCode(21710);
      str2 = BPWLocaleUtil.getMessage(21710, new String[] { paramRecTransferInfo.getTemplateNickName() }, "TRANSFER_MESSAGE");
      paramRecTransferInfo.setStatusMsg(str2);
      FFSDebug.log("TransferProcessor.addTransfer, " + str2, 0);
      return paramRecTransferInfo;
    }
    String str2 = jdMethod_do(paramRecTransferInfo);
    String str3;
    if (jdMethod_goto(str2) == true)
    {
      str3 = BPWLocaleUtil.getMessage(21261, null, "TRANSFER_MESSAGE");
      paramRecTransferInfo.setStatusCode(21261);
      paramRecTransferInfo.setStatusMsg(str3);
      FFSDebug.log("TransferProcessor.addRecTransfer" + str3, 0);
      return paramRecTransferInfo;
    }
    if (str2.equalsIgnoreCase("ETOE"))
    {
      str3 = BPWLocaleUtil.getMessage(21262, new String[] { str2 }, "TRANSFER_MESSAGE");
      paramRecTransferInfo.setStatusCode(21262);
      paramRecTransferInfo.setStatusMsg(str3);
      FFSDebug.log("TransferProcessor.addRecTransfer" + str3, 0);
      return paramRecTransferInfo;
    }
    try
    {
      jdMethod_do(paramRecTransferInfo, true);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** TransferProcessor.addRecTransfer failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      throw new FFSException(str4);
    }
    return paramRecTransferInfo;
  }
  
  private TransferInfo jdMethod_do(TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.addTransfer(.. ,boolean):";
    FFSDebug.log(str1, " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    FFSDebug.log(str1, " transferInfo=" + paramTransferInfo, 6);
    if (!a(paramTransferInfo, paramBoolean))
    {
      if (this.b6 >= 1) {
        a(paramTransferInfo, "Add", null, paramBoolean);
      }
      return paramTransferInfo;
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      Object localObject1;
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = str1 + "TransferProcessor.addTransfer: " + "Can not get DB Connection.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      if (paramBoolean)
      {
        a((RecTransferInfo)paramTransferInfo, localFFSConnectionHolder, true);
        if (paramTransferInfo.getStatusCode() != 0)
        {
          if (this.b6 >= 1) {
            a(paramTransferInfo, "Add", null, true);
          }
          localObject1 = paramTransferInfo;
          return localObject1;
        }
      }
      else
      {
        a(paramTransferInfo, localFFSConnectionHolder, true);
        if (paramTransferInfo.getStatusCode() != 0)
        {
          if (this.b6 >= 1) {
            a(paramTransferInfo, "Add", null, false);
          }
          localObject1 = paramTransferInfo;
          return localObject1;
        }
      }
      paramTransferInfo.setAction("Add");
      if ((jdMethod_char(paramTransferInfo)) && (!jdMethod_byte(paramTransferInfo)))
      {
        if (paramBoolean) {
          paramTransferInfo = a(localFFSConnectionHolder, localFFSConnectionHolder, (RecTransferInfo)paramTransferInfo);
        } else {
          paramTransferInfo = a(localFFSConnectionHolder, localFFSConnectionHolder, paramTransferInfo, false);
        }
      }
      else {
        paramTransferInfo = jdMethod_if(localFFSConnectionHolder, localFFSConnectionHolder, paramTransferInfo, paramBoolean, true);
      }
      if (paramTransferInfo.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject1 = paramTransferInfo;
        return localObject1;
      }
      FFSDebug.log(str1, " commit change", 6);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** TransferProcessor.addTransfer failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, " end ", 6);
    return paramTransferInfo;
  }
  
  private TransferInfo jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, TransferInfo paramTransferInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "TransferProcessor.addTransfer(conn,):";
    FFSDebug.log(str1, " Start ", 6);
    String str2 = null;
    String str3 = paramTransferInfo.getTransferType();
    boolean bool1 = "TEMPLATE".equalsIgnoreCase(str3);
    boolean bool2 = "RECTEMPLATE".equalsIgnoreCase(str3);
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    String str4 = paramBoolean1 ? "AddRecTransfer" : "AddTransfer";
    try
    {
      FFSDebug.log(str1, " transferInfo.getLogId()=", paramTransferInfo.getLogId(), 6);
      if ((paramTransferInfo.getLogId() == null) || (paramTransferInfo.getLogId().trim().length() == 0))
      {
        localObject1 = DBUtil.getNextIndexString("LogID");
        paramTransferInfo.setLogId((String)localObject1);
      }
      if (paramFFSConnectionHolder1.conn == null)
      {
        localObject1 = str1 + "TransferProcessor.addTransfer: " + "DB Connection for main transaction is null.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      if (paramFFSConnectionHolder2.conn == null)
      {
        localObject1 = str1 + "TransferProcessor.addTransfer: " + "DB Connection for activity logging is null.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      Object localObject1 = BPWFI.getBPWFIInfo(paramFFSConnectionHolder1, paramTransferInfo.getFIId());
      if (((BPWFIInfo)localObject1).getStatusCode() != 0)
      {
        FFSDebug.log(str1 + ((BPWFIInfo)localObject1).getStatusMsg(), 0);
        paramTransferInfo.setStatusCode(((BPWFIInfo)localObject1).getStatusCode());
        paramTransferInfo.setStatusMsg(((BPWFIInfo)localObject1).getStatusMsg());
        return paramTransferInfo;
      }
      localObject2 = null;
      String str5;
      if (this.b7 == true)
      {
        localObject2 = Customer.getCustomerInfo(paramTransferInfo.getCustomerId(), paramFFSConnectionHolder1, paramTransferInfo);
        if ((localObject2 == null) || (((CustomerInfo)localObject2).getCustomerID() == null))
        {
          paramTransferInfo.setStatusCode(19130);
          str5 = BPWLocaleUtil.getMessage(19130, new String[] { paramTransferInfo.getCustomerId() }, "TRANSFER_MESSAGE");
          paramTransferInfo.setStatusMsg(str5);
          FFSDebug.log(str1 + paramTransferInfo.getStatusMsg(), 0);
          return paramTransferInfo;
        }
        if (((CustomerInfo)localObject2).getStatusCode() != 0)
        {
          paramTransferInfo.setStatusCode(((CustomerInfo)localObject2).getStatusCode());
          paramTransferInfo.setStatusMsg(((CustomerInfo)localObject2).getStatusMsg());
          FFSDebug.log(str1 + ((CustomerInfo)localObject2).getStatusMsg(), 0);
          return paramTransferInfo;
        }
      }
      else
      {
        localObject2 = new CustomerInfo();
        ((CustomerInfo)localObject2).customerID = paramTransferInfo.getCustomerId();
      }
      if (jdMethod_do(paramTransferInfo) == null)
      {
        str5 = BPWLocaleUtil.getMessage(21262, null, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusCode(21262);
        paramTransferInfo.setStatusMsg(str5);
        return paramTransferInfo;
      }
      if ((paramBoolean2) && (Trans.checkDuplicateTIDAndSaveTID(paramTransferInfo.getTrnId())))
      {
        paramTransferInfo.setStatusCode(19220);
        paramTransferInfo.setStatusMsg("Duplicate TrnID");
        paramTransferInfo.setTransferType("DUPLICATE");
        if (this.b6 >= 1) {
          a(paramFFSConnectionHolder2, paramTransferInfo, "AddDup", paramBoolean1);
        }
        paramFFSConnectionHolder2.conn.commit();
        return paramTransferInfo;
      }
      if (paramTransferInfo.getTypeDetail() != null) {
        jdMethod_int(paramTransferInfo);
      }
      jdMethod_do(paramFFSConnectionHolder1, paramTransferInfo);
      if (paramTransferInfo.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed to handle accounts due to: ", paramTransferInfo.getStatusMsg(), 0);
        return paramTransferInfo;
      }
      if (paramTransferInfo.getTypeDetail() == null) {
        jdMethod_new(paramTransferInfo);
      }
      int i = paramTransferInfo.getProcessType();
      jdMethod_case(paramTransferInfo);
      a(paramFFSConnectionHolder1, paramTransferInfo, (BPWFIInfo)localObject1);
      String str7;
      int k;
      if (bool1 == true)
      {
        str7 = FFSUtil.getDateString("yyyyMMdd");
        paramTransferInfo.setDateDue(str7);
        paramTransferInfo.setDateToPost(str7);
        paramTransferInfo.setProcessDate(str7);
      }
      else if (bool2 == true)
      {
        str7 = FFSUtil.getDateString("yyyyMMdd");
        RecTransferInfo localRecTransferInfo = (RecTransferInfo)paramTransferInfo;
        localRecTransferInfo.setStartDate(str7);
        localRecTransferInfo.setEndDate(str7);
      }
      else if (!paramBoolean1)
      {
        a(paramTransferInfo, paramFFSConnectionHolder1);
        if (paramTransferInfo.getStatusCode() != 0) {
          return paramTransferInfo;
        }
        int j = Integer.parseInt(paramTransferInfo.getProcessDate());
        k = Integer.parseInt(FFSUtil.getDateString("yyyyMMdd"));
        int m = Integer.parseInt(paramTransferInfo.getDateDue());
        if ((i == 2) && (m > k) && (j > k))
        {
          paramTransferInfo.setStatusCode(17203);
          paramTransferInfo.setStatusMsg("Invalid due date value for batch.");
          FFSDebug.log(str1 + paramTransferInfo.getStatusMsg(), 0);
          return paramTransferInfo;
        }
      }
      jdMethod_do(paramFFSConnectionHolder1, paramTransferInfo, true);
      if (paramTransferInfo.getStatusCode() != 0) {
        return paramTransferInfo;
      }
      str2 = DBUtil.getNewTransId("SrvrTID", 18);
      paramTransferInfo.setSrvrTId(str2);
      paramTransferInfo.setDateCreate(DBUtil.getCurrentLogDate());
      String str8 = paramTransferInfo.getOriginatingUserId();
      if ((str8 == null) || (str8.trim().length() == 0)) {
        paramTransferInfo.setOriginatingUserId(paramTransferInfo.getSubmittedBy());
      }
      if (!a(localTransferCalloutHandlerBase, paramTransferInfo, str4))
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str4);
        return paramTransferInfo;
      }
      try
      {
        if ((!paramBoolean1) && (!bool1) && (!bool2))
        {
          FFSDebug.log(str1, "before limit checking, transferInfo:" + paramTransferInfo, 6);
          k = 0;
          if ("N".equalsIgnoreCase(((CustomerInfo)localObject2).getVirtualCustomer())) {
            k = LimitCheckApprovalProcessor.processExternalTransferAdd(paramFFSConnectionHolder1, paramTransferInfo, null);
          }
          String str9 = LimitCheckApprovalProcessor.mapToStatus(k);
          FFSDebug.log(str1, "retStatus LimitCheck:" + str9, 6);
          if (("LIMIT_CHECK_FAILED".equals(str9)) || ("LIMIT_REVERT_FAILED".equals(str9)) || ("APPROVAL_FAILED".equals(str9)))
          {
            paramTransferInfo.setPrcStatus(str9);
            jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str4);
            return paramTransferInfo;
          }
          if ("APPROVAL_PENDING".equals(str9)) {
            paramTransferInfo.setPrcStatus("APPROVAL_PENDING");
          } else {
            paramTransferInfo.setPrcStatus("WILLPROCESSON");
          }
        }
        paramTransferInfo = Transfer.addTransfer(paramFFSConnectionHolder1, paramTransferInfo, paramBoolean1, (BPWFIInfo)localObject1, (CustomerInfo)localObject2);
        if (paramTransferInfo.getStatusCode() != 0)
        {
          if (this.b6 >= 1) {
            a(paramTransferInfo, "Add", null, paramBoolean1);
          }
          jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str4);
          return paramTransferInfo;
        }
        if ((paramBoolean1 == true) && (!bool2))
        {
          jdMethod_if(paramFFSConnectionHolder1, (RecTransferInfo)paramTransferInfo, (BPWFIInfo)localObject1);
          FFSDebug.log(str1, "Creating single instance", 6);
          TransferInfo localTransferInfo = Transfer.generateSingleTransferFromRecTransfer(paramFFSConnectionHolder1, ((RecTransferInfo)paramTransferInfo).getSrvrTId(), ((RecTransferInfo)paramTransferInfo).getStartDate(), (BPWFIInfo)localObject1, (CustomerInfo)localObject2);
          if (!a(localTransferCalloutHandlerBase, localTransferInfo, "AddTransferFromSchedule"))
          {
            jdMethod_if(localTransferCalloutHandlerBase, localTransferInfo, "AddTransferFromSchedule");
            paramTransferInfo.setStatusCode(localTransferInfo.getStatusCode());
            paramTransferInfo.setStatusMsg(localTransferInfo.getStatusMsg());
            jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str4);
            return paramTransferInfo;
          }
          try
          {
            int n = LimitCheckApprovalProcessor.processExternalTransferAdd(paramFFSConnectionHolder1, localTransferInfo, null);
            String str10 = LimitCheckApprovalProcessor.mapToStatus(n);
            FFSDebug.log(str1, "retStatus LimitCheck:" + str10, 6);
            if (("LIMIT_CHECK_FAILED".equals(str10)) || ("LIMIT_REVERT_FAILED".equals(str10)) || ("APPROVAL_FAILED".equals(str10)))
            {
              localTransferInfo.setPrcStatus(str10);
              paramTransferInfo.setPrcStatus(localTransferInfo.getPrcStatus());
              paramTransferInfo.setStatusCode(localTransferInfo.getStatusCode());
              paramTransferInfo.setStatusMsg(localTransferInfo.getStatusMsg());
              paramTransferInfo.setExtInfo(localTransferInfo.getExtInfo());
              if (this.b6 >= 1) {
                a(paramTransferInfo, "Add", null, paramBoolean1);
              }
              jdMethod_if(localTransferCalloutHandlerBase, localTransferInfo, "AddTransferFromSchedule");
              jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str4);
              return paramTransferInfo;
            }
            if ("APPROVAL_PENDING".equals(str10)) {
              localTransferInfo.setPrcStatus("APPROVAL_PENDING");
            } else {
              localTransferInfo.setPrcStatus("WILLPROCESSON");
            }
            Transfer.updateStatus(paramFFSConnectionHolder1, localTransferInfo, false);
            if (this.b6 >= 3)
            {
              FFSDebug.log(str1, "audit logging3", 6);
              a(paramFFSConnectionHolder1, paramTransferInfo, "Add", paramBoolean1);
              a(paramFFSConnectionHolder1, localTransferInfo, "Add", false);
            }
            localTransferInfo.setAction("Add");
            jdMethod_if(paramFFSConnectionHolder1, localTransferInfo, true);
            if (localTransferInfo.getStatusCode() != 0)
            {
              jdMethod_if(localTransferCalloutHandlerBase, localTransferInfo, "AddTransferFromSchedule");
              paramTransferInfo.setPrcStatus("FAILEDON");
              Transfer.updateStatusAction(paramFFSConnectionHolder1, paramTransferInfo, true);
              if (this.b6 >= 4) {
                a(paramFFSConnectionHolder1, paramTransferInfo, "Add", true);
              }
              paramFFSConnectionHolder1.conn.commit();
              paramTransferInfo.setStatusCode(localTransferInfo.getStatusCode());
              paramTransferInfo.setStatusMsg(localTransferInfo.getStatusMsg());
              paramTransferInfo.setExtInfo(localTransferInfo.getExtInfo());
              jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str4);
              return paramTransferInfo;
            }
            paramFFSConnectionHolder1.conn.commit();
            jdMethod_do(localTransferCalloutHandlerBase, localTransferInfo, "AddTransferFromSchedule");
            jdMethod_do(localTransferCalloutHandlerBase, paramTransferInfo, str4);
          }
          catch (Throwable localThrowable3)
          {
            jdMethod_if(localTransferCalloutHandlerBase, localTransferInfo, str4);
            throw localThrowable3;
          }
        }
        if (((!paramBoolean1) || (bool1 == true) || (bool2 == true)) && (this.b6 >= 3))
        {
          FFSDebug.log(str1, "audit logging3", 6);
          a(paramFFSConnectionHolder1, paramTransferInfo, "Add", paramBoolean1);
        }
        if ((!paramBoolean1) && (!bool1) && (!bool2))
        {
          FFSDebug.log(str1, "check if immediate funds processing supported", 6);
          jdMethod_if(paramFFSConnectionHolder1, paramTransferInfo, true);
          if (paramTransferInfo.getStatusCode() != 0)
          {
            jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str4);
            return paramTransferInfo;
          }
          paramFFSConnectionHolder1.conn.commit();
          jdMethod_do(localTransferCalloutHandlerBase, paramTransferInfo, str4);
        }
        paramTransferInfo.setStatusCode(0);
        paramTransferInfo.setStatusMsg("Success");
      }
      catch (Throwable localThrowable2)
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str4);
        throw localThrowable2;
      }
    }
    catch (Throwable localThrowable1)
    {
      if (this.b6 >= 1)
      {
        localObject2 = BPWLocaleUtil.getLocalizableMessage(1021, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        a(paramTransferInfo, "Add", (ILocalizable)localObject2, paramBoolean1);
      }
      Object localObject2 = "*** TransferProcessor.addTransfer failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject2 + str6, 0);
      throw new FFSException(localThrowable1, (String)localObject2);
    }
    FFSDebug.log(str1, "done", 6);
    return paramTransferInfo;
  }
  
  private void jdMethod_new(TransferInfo paramTransferInfo)
  {
    ExtTransferAcctInfo localExtTransferAcctInfo = jdMethod_if(paramTransferInfo);
    if ((localExtTransferAcctInfo != null) && (localExtTransferAcctInfo.getRecipientType() != null)) {
      if (localExtTransferAcctInfo.getRecipientType().equals("PERSONAL")) {
        paramTransferInfo.setTypeDetail("PPD");
      } else {
        paramTransferInfo.setTypeDetail("CCD");
      }
    }
  }
  
  private void jdMethod_int(TransferInfo paramTransferInfo)
  {
    ExtTransferAcctInfo localExtTransferAcctInfo = jdMethod_if(paramTransferInfo);
    if ((localExtTransferAcctInfo != null) && (paramTransferInfo.getTypeDetail() != null)) {
      if (paramTransferInfo.getTypeDetail().equals("PPD")) {
        localExtTransferAcctInfo.setRecipientType("PERSONAL");
      } else {
        localExtTransferAcctInfo.setRecipientType("BUSINESS");
      }
    }
  }
  
  private ExtTransferAcctInfo jdMethod_if(TransferInfo paramTransferInfo)
  {
    ExtTransferAcctInfo localExtTransferAcctInfo = null;
    if (paramTransferInfo.getTransferDest() != null) {
      if (paramTransferInfo.getTransferDest().equals("ETOI")) {
        localExtTransferAcctInfo = paramTransferInfo.getAccountFromInfo();
      } else {
        localExtTransferAcctInfo = paramTransferInfo.getAccountToInfo();
      }
    }
    return localExtTransferAcctInfo;
  }
  
  public TransferInfo[] addTransfers(TransferInfo[] paramArrayOfTransferInfo)
    throws FFSException
  {
    String str = "TransferProcessor.addTransfers:";
    FFSDebug.log(str, " Start ", 6);
    a(paramArrayOfTransferInfo, false);
    FFSDebug.log(str, " Done", 6);
    return paramArrayOfTransferInfo;
  }
  
  public RecTransferInfo[] addRecTransfers(RecTransferInfo[] paramArrayOfRecTransferInfo)
    throws FFSException
  {
    String str = "TransferProcessor.addRecTransfers:";
    FFSDebug.log(str, " Start ", 6);
    a(paramArrayOfRecTransferInfo, true);
    FFSDebug.log(str, " Done", 6);
    return paramArrayOfRecTransferInfo;
  }
  
  private TransferInfo[] a(TransferInfo[] paramArrayOfTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.addTransfers (boolean):";
    FFSDebug.log(str1, " Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder1 = null;
    FFSConnectionHolder localFFSConnectionHolder2 = null;
    String str2 = null;
    String str3 = null;
    int i = 0;
    if ((paramArrayOfTransferInfo == null) || (paramArrayOfTransferInfo.length == 0))
    {
      FFSDebug.log(str1, "failed: Empty transfer list is passed", 0);
      return null;
    }
    try
    {
      FFSConnection[] arrayOfFFSConnection = DBUtil.getConnections(2);
      if ((arrayOfFFSConnection == null) || (arrayOfFFSConnection.length < 2))
      {
        String str4 = str1 + "Can not get DB Connections.";
        FFSDebug.log(str4, 0);
        throw new FFSException(str4);
      }
      localFFSConnectionHolder1 = new FFSConnectionHolder();
      localFFSConnectionHolder1.conn = arrayOfFFSConnection[0];
      localFFSConnectionHolder2 = new FFSConnectionHolder();
      localFFSConnectionHolder2.conn = arrayOfFFSConnection[1];
      i = paramArrayOfTransferInfo.length;
      for (int j = 0; j < i; j++)
      {
        paramArrayOfTransferInfo[j].setStatusCode(-1);
        paramArrayOfTransferInfo[j].setStatusMsg("");
      }
      for (j = 0; j < i; j++) {
        if (paramArrayOfTransferInfo[j] == null)
        {
          if (paramBoolean) {
            paramArrayOfTransferInfo[j] = new RecTransferInfo();
          } else {
            paramArrayOfTransferInfo[j] = new TransferInfo();
          }
          paramArrayOfTransferInfo[j].setStatusCode(16000);
          localObject1 = "Transfer number " + (j + 1) + " in the array" + " is null";
          paramArrayOfTransferInfo[j].setStatusMsg((String)localObject1);
          FFSDebug.log(str1 + (String)localObject1, 0);
        }
        else
        {
          if (paramBoolean)
          {
            a((RecTransferInfo)paramArrayOfTransferInfo[j], localFFSConnectionHolder1, true);
          }
          else
          {
            str3 = paramArrayOfTransferInfo[j].getTransferType();
            if ((str3 != null) && (str3.trim().equalsIgnoreCase("TEMPLATE"))) {
              paramArrayOfTransferInfo[j].setDateDue(null);
            }
            a(paramArrayOfTransferInfo[j], localFFSConnectionHolder1, true);
          }
          TransferInfo[] arrayOfTransferInfo;
          if (paramArrayOfTransferInfo[j].getStatusCode() != 0)
          {
            localFFSConnectionHolder1.conn.rollback();
            localFFSConnectionHolder2.conn.rollback();
            if (this.b6 >= 1) {
              a(paramArrayOfTransferInfo[j], "Add", null, paramBoolean);
            }
            localObject1 = "Error occured at transfer#:" + (j + 1) + paramArrayOfTransferInfo[j].getStatusMsg();
            FFSDebug.log((String)localObject1, 0);
            arrayOfTransferInfo = paramArrayOfTransferInfo;
            return arrayOfTransferInfo;
          }
          if (!a(paramArrayOfTransferInfo[j], paramBoolean))
          {
            localFFSConnectionHolder1.conn.rollback();
            localFFSConnectionHolder2.conn.rollback();
            if (this.b6 >= 1) {
              a(paramArrayOfTransferInfo[j], "Add", null, paramBoolean);
            }
            localObject1 = "Error occured at transfer#:" + (j + 1) + paramArrayOfTransferInfo[j].getStatusMsg();
            FFSDebug.log((String)localObject1, 0);
            arrayOfTransferInfo = paramArrayOfTransferInfo;
            return arrayOfTransferInfo;
          }
          if ((paramArrayOfTransferInfo[j].getLogId() == null) || (paramArrayOfTransferInfo[j].getLogId().trim().length() == 0))
          {
            str2 = DBUtil.getNextIndexString("LogID");
            paramArrayOfTransferInfo[j].setLogId(str2);
          }
          paramArrayOfTransferInfo[j] = jdMethod_if(localFFSConnectionHolder1, localFFSConnectionHolder2, paramArrayOfTransferInfo[j], paramBoolean, true);
          if (paramArrayOfTransferInfo[j].getStatusCode() != 0)
          {
            localFFSConnectionHolder1.conn.rollback();
            localFFSConnectionHolder2.conn.rollback();
            if (this.b6 >= 1) {
              a(paramArrayOfTransferInfo[j], "Add", null, paramBoolean);
            }
            localObject1 = paramArrayOfTransferInfo;
            return localObject1;
          }
        }
      }
      localFFSConnectionHolder1.conn.commit();
      localFFSConnectionHolder2.conn.commit();
      FFSDebug.log(str1, " Done", 6);
    }
    catch (Throwable localThrowable1)
    {
      localFFSConnectionHolder1.conn.rollback();
      localFFSConnectionHolder2.conn.commit();
      String str5 = " failed: ";
      Object localObject1 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log("*** ", str1, str5, (String)localObject1, 0);
      throw new FFSException(localThrowable1, str5);
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder1.conn);
      }
      catch (Throwable localThrowable2)
      {
        FFSDebug.log(str1, " Failed to free the first connection ", 0);
      }
      DBUtil.freeConnection(localFFSConnectionHolder2.conn);
    }
    return paramArrayOfTransferInfo;
  }
  
  public TransferInfo modTransfer(TransferInfo paramTransferInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.modTransfer: ";
    FFSDebug.log(str1, "Start ", 6);
    String str2 = null;
    if (paramTransferInfo == null)
    {
      paramTransferInfo = new TransferInfo();
      paramTransferInfo.setStatusCode(16000);
      String str3 = BPWLocaleUtil.getMessage(16000, new String[] { "TransferInfo" }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusMsg(str3);
      FFSDebug.log(str1, str3, 0);
      return paramTransferInfo;
    }
    str2 = paramTransferInfo.getTransferType();
    int i = 0;
    if ((str2 != null) && (str2.trim().equalsIgnoreCase("TEMPLATE"))) {
      i = 1;
    }
    if ((i != 0) && (Transfer.isDuplicateTemplate(paramTransferInfo.getTemplateNickName(), paramTransferInfo.getSubmittedBy(), paramTransferInfo.getSrvrTId(), true)))
    {
      paramTransferInfo.setStatusCode(21710);
      localObject = BPWLocaleUtil.getMessage(21710, new String[] { paramTransferInfo.getTemplateNickName() }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusMsg((String)localObject);
      FFSDebug.log("TransferProcessor.addTransfer, " + (String)localObject, 0);
      return paramTransferInfo;
    }
    Object localObject = new TransferInfo();
    ((TransferInfo)localObject).setSrvrTId(paramTransferInfo.getSrvrTId());
    localObject = Transfer.getTransferInfo((TransferInfo)localObject, false, false);
    if (((TransferInfo)localObject).getStatusCode() == 16020)
    {
      paramTransferInfo.setAction("Modify");
      return jdMethod_try(paramTransferInfo);
    }
    ((TransferInfo)localObject).setTrnId(paramTransferInfo.getTrnId());
    paramTransferInfo.setPrcStatus(((TransferInfo)localObject).getPrcStatus());
    if (jdMethod_goto(paramTransferInfo.getSubmittedBy()) == true) {
      paramTransferInfo.setSubmittedBy(((TransferInfo)localObject).getSubmittedBy());
    }
    if (jdMethod_goto(paramTransferInfo.getFIId()) == true) {
      paramTransferInfo.setFIId(((TransferInfo)localObject).getFIId());
    }
    if (jdMethod_goto(paramTransferInfo.getCustomerId()) == true) {
      paramTransferInfo.setCustomerId(((TransferInfo)localObject).getCustomerId());
    }
    if (jdMethod_goto(paramTransferInfo.getLogId()) == true) {
      paramTransferInfo.setLogId(((TransferInfo)localObject).getLogId());
    }
    try
    {
      str2 = paramTransferInfo.getTransferType();
      if ((str2 != null) && (str2.trim().equalsIgnoreCase("TEMPLATE"))) {
        paramTransferInfo.setDateDue(null);
      }
      paramTransferInfo = a(paramTransferInfo, (TransferInfo)localObject, false);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** TransferProcessor.modTransfer failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4 + str5, 0);
      throw new FFSException(localThrowable, str4);
    }
    return paramTransferInfo;
  }
  
  private TransferInfo jdMethod_try(TransferInfo paramTransferInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.modIntraTransfer(transferInfo): ";
    FFSDebug.log(str1, "Start", 6);
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    String str2 = "ModifyIntraTransferOFX200";
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str1 + "Can not get DB Connection.");
      }
      Object localObject2;
      if (paramTransferInfo.getSrvrTId() == null)
      {
        paramTransferInfo.setStatusCode(16010);
        localObject1 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "SrvrTId" }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg((String)localObject1);
        FFSDebug.log(str1, (String)localObject1, 0);
        localObject2 = paramTransferInfo;
        return localObject2;
      }
      if (!a(paramTransferInfo, false))
      {
        if (this.b6 >= 1) {
          a(paramTransferInfo, "Mod", null, false);
        }
        localObject1 = paramTransferInfo;
        return localObject1;
      }
      Object localObject1 = XferInstruction.getXferInstruction(localFFSConnectionHolder, paramTransferInfo.getSrvrTId());
      TransferInfo localTransferInfo;
      if (localObject1 == null)
      {
        paramTransferInfo.setStatusCode(21260);
        paramTransferInfo.setPrcStatus("FAILEDON");
        localObject2 = BPWLocaleUtil.getMessage(21260, new String[] { paramTransferInfo.getSrvrTId() }, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        paramTransferInfo.setStatusMsg((String)localObject2);
        FFSDebug.log(str1, (String)localObject2, 0);
        localTransferInfo = paramTransferInfo;
        return localTransferInfo;
      }
      if (jdMethod_goto(paramTransferInfo.getLogId()) == true) {
        paramTransferInfo.setLogId(((XferInstruction)localObject1).LogID);
      }
      if (jdMethod_goto(paramTransferInfo.getTransferDest()) == true) {
        paramTransferInfo.setTransferDest("ITOI");
      }
      if (Trans.checkDuplicateTIDAndSaveTID(paramTransferInfo.getTrnId()))
      {
        if (this.b6 >= 1) {
          a(localFFSConnectionHolder, paramTransferInfo, "ModDup", false);
        }
        localFFSConnectionHolder.conn.commit();
        paramTransferInfo.setStatusCode(19220);
        paramTransferInfo.setStatusMsg("Duplicate TrnID");
        localObject2 = paramTransferInfo;
        return localObject2;
      }
      jdMethod_else(paramTransferInfo);
      if (!a(localTransferCalloutHandlerBase, paramTransferInfo, str2))
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
        localObject2 = paramTransferInfo;
        return localObject2;
      }
      try
      {
        localObject1 = XferInstruction.getXferInstruction(localFFSConnectionHolder, paramTransferInfo.getSrvrTId());
        if (localObject1 == null)
        {
          paramTransferInfo.setStatusCode(21260);
          paramTransferInfo.setPrcStatus("FAILEDON");
          localObject2 = BPWLocaleUtil.getMessage(21260, new String[] { paramTransferInfo.getSrvrTId() }, "BOOKTRANSFER_AUDITLOG_MESSAGE");
          paramTransferInfo.setStatusMsg((String)localObject2);
          FFSDebug.log(str1, (String)localObject2, 0);
          localTransferInfo = paramTransferInfo;
          return localTransferInfo;
        }
        if (((XferInstruction)localObject1).Status.equals("CANCELEDON"))
        {
          paramTransferInfo.setStatusCode(2017);
          paramTransferInfo.setPrcStatus("CANCELEDON");
          localObject2 = paramTransferInfo;
          return localObject2;
        }
        int i = 1;
        int j = jdMethod_int(localFFSConnectionHolder, paramTransferInfo);
        if (j == 0) {
          ScheduleInfo.cancelSchedule(localFFSConnectionHolder, "INTRATRN", paramTransferInfo.getSrvrTId());
        } else if (!"APPROVAL_PENDING".equals(((XferInstruction)localObject1).Status)) {
          if (("APPROVAL_REJECTED".equals(((XferInstruction)localObject1).Status)) || ("APPROVAL_NOT_ALLOWED".equals(((XferInstruction)localObject1).Status)))
          {
            i = 0;
          }
          else
          {
            paramTransferInfo.setStatusCode(2016);
            paramTransferInfo.setPrcStatus("FAILEDON");
            localObject3 = paramTransferInfo;
            return localObject3;
          }
        }
        if (i != 0)
        {
          localObject3 = TransferIntraMap.mapTranInfoToIntraTrnInfo(paramTransferInfo);
          j = LimitCheckApprovalProcessor.processIntraTrnDelete(localFFSConnectionHolder, (IntraTrnInfo)localObject3, new HashMap());
          localObject4 = LimitCheckApprovalProcessor.mapToStatus(j);
          FFSDebug.log(str1, "retStatus LimitRevert:" + (String)localObject4, 6);
          if (!"LIMIT_REVERT_SUCCEEDED".equals(localObject4))
          {
            paramTransferInfo.setPrcStatus((String)localObject4);
            paramTransferInfo.setStatusCode(((IntraTrnInfo)localObject3).getStatusCode());
            paramTransferInfo.setStatusMsg(((IntraTrnInfo)localObject3).getStatusMsg());
            if (this.b6 >= 3)
            {
              localObject5 = BPWLocaleUtil.getLocalizedMessage(402, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
              a(paramTransferInfo, "Mod", (ILocalizable)localObject5, false);
            }
            jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
            Object localObject5 = paramTransferInfo;
            return localObject5;
          }
        }
        Object localObject3 = TransferIntraMap.mapTranInfoToIntraTrnInfo(paramTransferInfo);
        ((IntraTrnInfo)localObject3).status = "WILLPROCESSON";
        ((IntraTrnInfo)localObject3).lastModified = FFSUtil.getDateString("yyyyMMddHHmmss");
        Object localObject4 = new HashMap();
        ((HashMap)localObject4).put("DateCreate", "");
        int k = LimitCheckApprovalProcessor.processIntraTrnAdd(localFFSConnectionHolder, (IntraTrnInfo)localObject3, (HashMap)localObject4);
        String str5 = LimitCheckApprovalProcessor.mapToStatus(k);
        if ((!"WILLPROCESSON".equals(str5)) && (!"APPROVAL_PENDING".equals(str5)))
        {
          paramTransferInfo.setPrcStatus(str5);
          paramTransferInfo.setStatusCode(((IntraTrnInfo)localObject3).getStatusCode());
          paramTransferInfo.setStatusMsg(((IntraTrnInfo)localObject3).getStatusMsg());
          if ((((HashMap)localObject4).get("FAILED_LIMITS") != null) && ((((HashMap)localObject4).get("FAILED_LIMITS") instanceof Limits)))
          {
            localObject6 = paramTransferInfo.getExtInfo();
            ((Hashtable)localObject6).put("NOT_ALLOWED_APPROVAL_LIMITS", ((HashMap)localObject4).get("FAILED_LIMITS"));
          }
          if (this.b6 >= 3)
          {
            localObject6 = BPWLocaleUtil.getLocalizedMessage(401, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
            a(paramTransferInfo, "Mod", (ILocalizable)localObject6, false);
          }
          jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
          localObject6 = paramTransferInfo;
          return localObject6;
        }
        if (this.b6 >= 3) {
          a(localFFSConnectionHolder, paramTransferInfo, "Mod", false);
        }
        paramTransferInfo.setStatusCode(0);
        if ("APPROVAL_PENDING".equals(str5)) {
          paramTransferInfo.setPrcStatus("APPROVAL_PENDING");
        } else {
          paramTransferInfo.setPrcStatus("WILLPROCESSON");
        }
        if ("WILLPROCESSON".equals(str5)) {
          jdMethod_new(localFFSConnectionHolder, paramTransferInfo);
        }
        XferInstruction.modify(localFFSConnectionHolder, paramTransferInfo);
        a(paramTransferInfo);
        Object localObject6 = paramTransferInfo.getExtInfo();
        if ((localObject6 != null) && (!((Hashtable)localObject6).isEmpty())) {
          BPWXferExtraInfo.updateHashtable(paramTransferInfo.getSrvrTId(), (Hashtable)localObject6, localFFSConnectionHolder);
        } else {
          BPWXferExtraInfo.deleteAllWithSrvrTID(paramTransferInfo.getSrvrTId(), localFFSConnectionHolder);
        }
        localFFSConnectionHolder.conn.commit();
        if (paramTransferInfo.getStatusCode() == 0) {
          jdMethod_do(localTransferCalloutHandlerBase, paramTransferInfo, str2);
        } else {
          jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
        }
      }
      catch (Exception localException)
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
        throw localException;
      }
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = "*** " + str1 + "failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3 + str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, " Done", 6);
    return paramTransferInfo;
  }
  
  public RecTransferInfo modRecTransfer(RecTransferInfo paramRecTransferInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.modRecTransfer: ";
    if (paramRecTransferInfo == null)
    {
      paramRecTransferInfo = new RecTransferInfo();
      paramRecTransferInfo.setStatusCode(16000);
      str2 = BPWLocaleUtil.getMessage(16000, new String[] { "RecTransferInfo" }, "TRANSFER_MESSAGE");
      paramRecTransferInfo.setStatusMsg(str2);
      FFSDebug.log(str1 + str2, 0);
      return paramRecTransferInfo;
    }
    String str2 = paramRecTransferInfo.getTransferType();
    int i = 0;
    if ((str2 != null) && (str2.trim().equalsIgnoreCase("RECTEMPLATE"))) {
      i = 1;
    }
    if ((i != 0) && (Transfer.isDuplicateTemplate(paramRecTransferInfo.getTemplateNickName(), paramRecTransferInfo.getSubmittedBy(), paramRecTransferInfo.getSrvrTId(), true)))
    {
      paramRecTransferInfo.setStatusCode(21710);
      localObject = BPWLocaleUtil.getMessage(21710, new String[] { paramRecTransferInfo.getTemplateNickName() }, "TRANSFER_MESSAGE");
      paramRecTransferInfo.setStatusMsg((String)localObject);
      FFSDebug.log("TransferProcessor.addTransfer, " + (String)localObject, 0);
      return paramRecTransferInfo;
    }
    Object localObject = new RecTransferInfo();
    ((RecTransferInfo)localObject).setSrvrTId(paramRecTransferInfo.getSrvrTId());
    localObject = (RecTransferInfo)Transfer.getTransferInfo((TransferInfo)localObject, true, false);
    if (((RecTransferInfo)localObject).getStatusCode() == 16020)
    {
      paramRecTransferInfo.setAction("Modify");
      return jdMethod_if(paramRecTransferInfo);
    }
    ((RecTransferInfo)localObject).setTrnId(paramRecTransferInfo.getTrnId());
    paramRecTransferInfo.setPrcStatus(((RecTransferInfo)localObject).getPrcStatus());
    if (jdMethod_goto(paramRecTransferInfo.getSubmittedBy()) == true) {
      paramRecTransferInfo.setSubmittedBy(((RecTransferInfo)localObject).getSubmittedBy());
    }
    if (jdMethod_goto(paramRecTransferInfo.getFIId()) == true) {
      paramRecTransferInfo.setFIId(((RecTransferInfo)localObject).getFIId());
    }
    if (jdMethod_goto(paramRecTransferInfo.getCustomerId()) == true) {
      paramRecTransferInfo.setCustomerId(((RecTransferInfo)localObject).getCustomerId());
    }
    if (jdMethod_goto(paramRecTransferInfo.getLogId()) == true) {
      paramRecTransferInfo.setLogId(((RecTransferInfo)localObject).getLogId());
    }
    try
    {
      a(paramRecTransferInfo, (TransferInfo)localObject, true);
    }
    catch (Throwable localThrowable)
    {
      String str3 = "*** TransferProcessor.modRecTransfer failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3 + str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    return paramRecTransferInfo;
  }
  
  private RecTransferInfo jdMethod_if(RecTransferInfo paramRecTransferInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.modRecIntraTransfer(recTransferInfo): ";
    FFSDebug.log(str1, "Start", 6);
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    String str2 = "ModifyIntraRecTransferOFX200";
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str1 + "Can not get DB Connection.");
      }
      Object localObject2;
      if (paramRecTransferInfo.getSrvrTId() == null)
      {
        paramRecTransferInfo.setStatusCode(16010);
        localObject1 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "SrvrTId" }, "TRANSFER_MESSAGE");
        paramRecTransferInfo.setStatusMsg((String)localObject1);
        FFSDebug.log(str1, (String)localObject1, 0);
        localObject2 = paramRecTransferInfo;
        return localObject2;
      }
      if (!a(paramRecTransferInfo, true))
      {
        if (this.b6 >= 1) {
          a(paramRecTransferInfo, "Mod", null, true);
        }
        localObject1 = paramRecTransferInfo;
        return localObject1;
      }
      Object localObject1 = RecXferInstruction.getRecXferInstruction(localFFSConnectionHolder, paramRecTransferInfo.getSrvrTId());
      if (localObject1 == null)
      {
        paramRecTransferInfo.setStatusCode(21260);
        paramRecTransferInfo.setPrcStatus("FAILEDON");
        localObject2 = BPWLocaleUtil.getMessage(21260, new String[] { paramRecTransferInfo.getSrvrTId() }, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        paramRecTransferInfo.setStatusMsg((String)localObject2);
        FFSDebug.log(str1, (String)localObject2, 0);
        localObject3 = paramRecTransferInfo;
        return localObject3;
      }
      if (jdMethod_goto(paramRecTransferInfo.getLogId()) == true) {
        paramRecTransferInfo.setLogId(((RecXferInstruction)localObject1).LogID);
      }
      if (jdMethod_goto(paramRecTransferInfo.getTransferDest()) == true) {
        paramRecTransferInfo.setTransferDest("ITOI");
      }
      a(paramRecTransferInfo, localFFSConnectionHolder, false);
      if (Trans.checkDuplicateTIDAndSaveTID(paramRecTransferInfo.getTrnId()))
      {
        if (this.b6 >= 1) {
          a(localFFSConnectionHolder, paramRecTransferInfo, "ModDup", false);
        }
        localFFSConnectionHolder.conn.commit();
        paramRecTransferInfo.setStatusCode(19220);
        paramRecTransferInfo.setStatusMsg("Duplicate TrnID");
        localObject2 = paramRecTransferInfo;
        return localObject2;
      }
      if (!a(localTransferCalloutHandlerBase, paramRecTransferInfo, str2))
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramRecTransferInfo, str2);
        localObject2 = paramRecTransferInfo;
        return localObject2;
      }
      try
      {
        int i = jdMethod_if(localFFSConnectionHolder, paramRecTransferInfo);
        if (i == 0)
        {
          paramRecTransferInfo.setStatusCode(0);
          paramRecTransferInfo.setPrcStatus("WILLPROCESSON");
        }
        else if (i == 1)
        {
          paramRecTransferInfo.setStatusCode(2018);
          paramRecTransferInfo.setPrcStatus("FAILEDON");
        }
        else if (i == 2)
        {
          paramRecTransferInfo.setStatusCode(2017);
          paramRecTransferInfo.setPrcStatus("FAILEDON");
        }
        else if (i == 3)
        {
          paramRecTransferInfo.setStatusCode(2016);
          paramRecTransferInfo.setPrcStatus("FAILEDON");
        }
        if (this.b6 >= 3) {
          a(localFFSConnectionHolder, paramRecTransferInfo, "Mod", true);
        }
        if (i != 0)
        {
          localObject3 = paramRecTransferInfo;
          return localObject3;
        }
        RecXferInstruction.modify(localFFSConnectionHolder, paramRecTransferInfo);
        a(paramRecTransferInfo);
        localObject3 = paramRecTransferInfo.getExtInfo();
        if ((localObject3 != null) && (!((Hashtable)localObject3).isEmpty())) {
          BPWRecXferExtraInfo.updateHashtable(paramRecTransferInfo.getSrvrTId(), (Hashtable)localObject3, localFFSConnectionHolder);
        } else {
          BPWRecXferExtraInfo.deleteAllWithRecSrvrTID(paramRecTransferInfo.getSrvrTId(), localFFSConnectionHolder);
        }
        if (paramRecTransferInfo.getStatusCode() == 0)
        {
          String[] arrayOfString = null;
          arrayOfString = RecSrvrTIDToSrvrTID.getSrvrTIDs(localFFSConnectionHolder, paramRecTransferInfo.getSrvrTId());
          FFSDebug.log(str1, ": srvrTIDs: " + arrayOfString, 6);
          if (arrayOfString != null)
          {
            FFSDebug.log(str1, ": srvrTIDs #: " + arrayOfString.length, 6);
            for (int j = 0; j < arrayOfString.length; j++)
            {
              String str4 = XferInstruction.getStatus(localFFSConnectionHolder, arrayOfString[j]);
              FFSDebug.log(str1, ": srvrTIDs[i]: ", arrayOfString[j], 6);
              FFSDebug.log(str1, ": status: ", str4, 6);
              if ((str4 != null) && (("WILLPROCESSON".equalsIgnoreCase(str4)) || ("APPROVAL_PENDING".equalsIgnoreCase(str4)) || ("APPROVAL_REJECTED".equals(str4)) || ("APPROVAL_NOT_ALLOWED".equals(str4))))
              {
                TransferInfo localTransferInfo2 = new TransferInfo();
                localTransferInfo2.setSrvrTId(arrayOfString[j]);
                a(localTransferInfo2, true, localFFSConnectionHolder);
              }
            }
          }
          FFSDebug.log(str1, ": Deleted all pending transfer ", 6);
          TransferInfo localTransferInfo1 = (TransferInfo)paramRecTransferInfo.clone();
          localTransferInfo1.setSourceRecSrvrTId(paramRecTransferInfo.getSrvrTId());
          localTransferInfo1.setSrvrTId(null);
          a(localFFSConnectionHolder, localFFSConnectionHolder, localTransferInfo1, true);
          FFSDebug.log(str1, ": added new transfer", 6);
          if (localTransferInfo1.getStatusCode() != 0)
          {
            paramRecTransferInfo.setPrcStatus(localTransferInfo1.getPrcStatus());
            paramRecTransferInfo.setStatusCode(localTransferInfo1.getStatusCode());
            paramRecTransferInfo.setStatusMsg(localTransferInfo1.getStatusMsg());
          }
          else
          {
            RecSrvrTIDToSrvrTID.create(localFFSConnectionHolder, paramRecTransferInfo.getSrvrTId(), localTransferInfo1.getSrvrTId());
          }
        }
        if (paramRecTransferInfo.getStatusCode() == 0)
        {
          localFFSConnectionHolder.conn.commit();
          jdMethod_do(localTransferCalloutHandlerBase, paramRecTransferInfo, str2);
        }
        else
        {
          localFFSConnectionHolder.conn.rollback();
          jdMethod_if(localTransferCalloutHandlerBase, paramRecTransferInfo, str2);
        }
      }
      catch (Exception localException)
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramRecTransferInfo, str2);
        throw localException;
      }
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = "*** " + str1 + "failed: ";
      Object localObject3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3 + (String)localObject3, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, " Done", 6);
    return paramRecTransferInfo;
  }
  
  private TransferInfo a(TransferInfo paramTransferInfo1, TransferInfo paramTransferInfo2, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.modTransfer(boolean): ";
    FFSDebug.log(str1, "Start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    String str2 = paramTransferInfo1.getSrvrTId();
    Object localObject1;
    if (str2 == null)
    {
      paramTransferInfo1.setStatusCode(16010);
      localObject1 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "SrvrTId" }, "TRANSFER_MESSAGE");
      paramTransferInfo1.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      if (this.b6 >= 1) {
        a(paramTransferInfo1, "Mod", null, paramBoolean);
      }
      return paramTransferInfo1;
    }
    if (!a(paramTransferInfo1, paramBoolean))
    {
      if (this.b6 >= 1) {
        a(paramTransferInfo1, "Mod", null, paramBoolean);
      }
      return paramTransferInfo1;
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str1 + "Can not get DB Connection.");
      }
      if (paramBoolean)
      {
        a((RecTransferInfo)paramTransferInfo1, localFFSConnectionHolder, false);
        if (paramTransferInfo1.getStatusCode() != 0)
        {
          if (this.b6 >= 1) {
            a(paramTransferInfo1, "Mod", null, true);
          }
          localObject1 = paramTransferInfo1;
          return localObject1;
        }
      }
      else
      {
        a(paramTransferInfo1, localFFSConnectionHolder, false);
        if (paramTransferInfo1.getStatusCode() != 0)
        {
          if (this.b6 >= 1) {
            a(paramTransferInfo1, "Mod", null, false);
          }
          localObject1 = paramTransferInfo1;
          return localObject1;
        }
      }
      paramTransferInfo1 = a(localFFSConnectionHolder, localFFSConnectionHolder, paramTransferInfo1, paramTransferInfo2, paramBoolean, true);
      if (paramTransferInfo1.getStatusCode() != 0)
      {
        localFFSConnectionHolder.conn.rollback();
        localObject1 = paramTransferInfo1;
        return localObject1;
      }
      FFSDebug.log(str1, "commit change", 6);
      localFFSConnectionHolder.conn.commit();
      paramTransferInfo1.setStatusCode(0);
      paramTransferInfo1.setStatusMsg("Success");
      FFSDebug.log(str1, "Done", 6);
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = str1 + "failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3 + str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    return paramTransferInfo1;
  }
  
  private TransferInfo a(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, TransferInfo paramTransferInfo1, TransferInfo paramTransferInfo2, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "TransferProcessor.modTransfer(conn,):";
    FFSDebug.log(str1, " Start ", 6);
    String[] arrayOfString = null;
    String str2 = paramTransferInfo1.getTransferType();
    boolean bool1 = "TEMPLATE".equalsIgnoreCase(str2);
    boolean bool2 = "RECTEMPLATE".equalsIgnoreCase(str2);
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    String str3 = paramBoolean1 ? "ModifyRecTransfer" : "ModifyTransfer";
    try
    {
      String str4 = paramTransferInfo1.getLogId();
      if ((str4 == null) || (str4.trim().length() == 0))
      {
        str4 = DBUtil.getNextIndexString("LogID");
        paramTransferInfo1.setLogId(str4);
      }
      FFSDebug.log(str1, " logID: ", str4, 6);
      if (paramFFSConnectionHolder1.conn == null)
      {
        localObject1 = str1 + "TransferProcessor.addTransfer: " + "DB Connection for main transaction is null.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      if (paramFFSConnectionHolder2.conn == null)
      {
        localObject1 = str1 + "TransferProcessor.addTransfer: " + "DB Connection for activity logging is null.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      localObject1 = BPWFI.getBPWFIInfo(paramFFSConnectionHolder1, paramTransferInfo1.getFIId());
      if (((BPWFIInfo)localObject1).getStatusCode() != 0)
      {
        FFSDebug.log(str1 + ((BPWFIInfo)localObject1).getStatusMsg(), 0);
        paramTransferInfo1.setStatusCode(((BPWFIInfo)localObject1).getStatusCode());
        paramTransferInfo1.setStatusMsg(((BPWFIInfo)localObject1).getStatusMsg());
        localObject2 = paramTransferInfo1;
        return localObject2;
      }
      localObject2 = null;
      if (this.b7 == true)
      {
        localObject2 = Customer.getCustomerInfo(paramTransferInfo1.getCustomerId(), paramFFSConnectionHolder1, paramTransferInfo1);
        if ((localObject2 == null) || (((CustomerInfo)localObject2).getCustomerID() == null))
        {
          paramTransferInfo1.setStatusCode(19130);
          localObject3 = BPWLocaleUtil.getMessage(19130, new String[] { paramTransferInfo1.getCustomerId() }, "TRANSFER_MESSAGE");
          paramTransferInfo1.setStatusMsg((String)localObject3);
          FFSDebug.log(str1 + paramTransferInfo1.getStatusMsg(), 0);
          localObject4 = paramTransferInfo1;
          return localObject4;
        }
        if (((CustomerInfo)localObject2).getStatusCode() != 0)
        {
          paramTransferInfo1.setStatusCode(((CustomerInfo)localObject2).getStatusCode());
          paramTransferInfo1.setStatusMsg(((CustomerInfo)localObject2).getStatusMsg());
          FFSDebug.log(str1 + ((CustomerInfo)localObject2).getStatusMsg(), 0);
          localObject3 = paramTransferInfo1;
          return localObject3;
        }
      }
      else
      {
        localObject2 = new CustomerInfo();
        ((CustomerInfo)localObject2).customerID = paramTransferInfo1.getCustomerId();
      }
      Object localObject3 = jdMethod_do(paramTransferInfo1);
      Object localObject4 = paramTransferInfo2.getTransferDest();
      Object localObject5;
      Object localObject7;
      if (jdMethod_goto((String)localObject3) == true)
      {
        localObject5 = BPWLocaleUtil.getMessage(21261, null, "TRANSFER_MESSAGE");
        paramTransferInfo1.setStatusCode(21261);
        paramTransferInfo1.setStatusMsg((String)localObject5);
        FFSDebug.log(str1 + (String)localObject5, 0);
        localObject7 = paramTransferInfo1;
        return localObject7;
      }
      if ((!((String)localObject3).equalsIgnoreCase("ITOE")) && (!((String)localObject3).equalsIgnoreCase("ETOI")) && ((((String)localObject3).equalsIgnoreCase("ITOI") != true) || ((!bool1) && (!bool2))))
      {
        localObject5 = BPWLocaleUtil.getMessage(21262, new String[] { localObject3 }, "TRANSFER_MESSAGE");
        paramTransferInfo1.setStatusCode(21262);
        paramTransferInfo1.setStatusMsg((String)localObject5);
        FFSDebug.log(str1 + (String)localObject5, 0);
        localObject7 = paramTransferInfo1;
        return localObject7;
      }
      if ((!((String)localObject3).equalsIgnoreCase((String)localObject4)) && (!bool1) && (!bool2))
      {
        localObject5 = BPWLocaleUtil.getMessage(21263, new String[] { localObject4, localObject3 }, "TRANSFER_MESSAGE");
        paramTransferInfo1.setStatusCode(21263);
        paramTransferInfo1.setStatusMsg((String)localObject5);
        FFSDebug.log(str1 + (String)localObject5, 0);
        localObject7 = paramTransferInfo1;
        return localObject7;
      }
      if ((paramBoolean2) && (Trans.checkDuplicateTIDAndSaveTID(paramTransferInfo1.getTrnId())))
      {
        paramTransferInfo1.setStatusCode(19220);
        paramTransferInfo1.setStatusMsg("Duplicate TrnID");
        paramTransferInfo1.setTransferType("DUPLICATE");
        if (this.b6 >= 1) {
          a(paramFFSConnectionHolder2, paramTransferInfo1, "ModDup", paramBoolean1);
        }
        paramFFSConnectionHolder2.conn.commit();
        localObject5 = paramTransferInfo1;
        return localObject5;
      }
      if (paramTransferInfo1.getTypeDetail() != null) {
        jdMethod_int(paramTransferInfo1);
      }
      a(paramFFSConnectionHolder1, paramTransferInfo1, paramTransferInfo2, paramBoolean1);
      if (paramTransferInfo1.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "failed to handle accounts due to: ", paramTransferInfo1.getStatusMsg(), 0);
        localObject5 = paramTransferInfo1;
        return localObject5;
      }
      if (paramTransferInfo1.getTypeDetail() == null) {
        jdMethod_new(paramTransferInfo1);
      }
      if (paramTransferInfo2.getPrcStatus().equals("WILLPROCESSON")) {
        jdMethod_case(paramTransferInfo1);
      }
      if (bool1 == true)
      {
        paramTransferInfo1.setDateDue(paramTransferInfo2.getDateDue());
        paramTransferInfo1.setDateToPost(paramTransferInfo2.getDateToPost());
        paramTransferInfo1.setProcessDate(paramTransferInfo2.getProcessDate());
      }
      else if (bool2 == true)
      {
        localObject5 = (RecTransferInfo)paramTransferInfo1;
        localObject7 = (RecTransferInfo)paramTransferInfo2;
        ((RecTransferInfo)localObject5).setStartDate(((RecTransferInfo)localObject7).getStartDate());
        ((RecTransferInfo)localObject5).setEndDate(((RecTransferInfo)localObject7).getEndDate());
      }
      else if (!paramBoolean1)
      {
        a(paramTransferInfo1, paramFFSConnectionHolder1);
        if (paramTransferInfo1.getStatusCode() != 0)
        {
          localObject5 = paramTransferInfo1;
          return localObject5;
        }
      }
      jdMethod_do(paramFFSConnectionHolder1, paramTransferInfo1, false);
      if (paramTransferInfo1.getStatusCode() != 0)
      {
        localObject5 = paramTransferInfo1;
        return localObject5;
      }
      paramTransferInfo1.setAction("Modify");
      paramTransferInfo1.setFundsRetry(0);
      if (!jdMethod_byte(paramTransferInfo1, paramBoolean1))
      {
        localObject5 = "failed: " + paramTransferInfo1.getStatusMsg();
        FFSDebug.log(str1, (String)localObject5, 0);
        paramFFSConnectionHolder1.conn.rollback();
        if (this.b6 >= 1) {
          a(paramTransferInfo1, "Mod", null, paramBoolean1);
        }
        localObject7 = paramTransferInfo1;
        return localObject7;
      }
      Object localObject8;
      TransferInfo localTransferInfo2;
      if ((!bool2) && (!bool1))
      {
        boolean bool3 = jdMethod_if(paramTransferInfo1, paramBoolean1, paramFFSConnectionHolder1);
        if (!bool3)
        {
          paramTransferInfo1.setStatusCode(21070);
          localObject7 = BPWLocaleUtil.getMessage(21070, null, "TRANSFER_MESSAGE");
          localObject8 = (String)localObject7 + " SrvrTId: " + paramTransferInfo1.getSrvrTId();
          paramTransferInfo1.setStatusMsg((String)localObject8);
          FFSDebug.log(str1 + ", " + (String)localObject8, 0);
          if (this.b6 >= 1) {
            a(paramTransferInfo1, "Mod", null, paramBoolean1);
          }
          localTransferInfo2 = paramTransferInfo1;
          return localTransferInfo2;
        }
      }
      if (!a(localTransferCalloutHandlerBase, paramTransferInfo1, str3))
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo1, str3);
        TransferInfo localTransferInfo1 = paramTransferInfo1;
        return localTransferInfo1;
      }
      try
      {
        if ((paramBoolean1 == true) && (!bool2)) {
          a(paramFFSConnectionHolder1, (RecTransferInfo)paramTransferInfo1, (BPWFIInfo)localObject1);
        }
        if ((!paramBoolean1) && (!bool1) && (!bool2))
        {
          int i = LimitCheckApprovalProcessor.processExternalTransferDelete(paramFFSConnectionHolder1, paramTransferInfo2, null);
          localObject7 = LimitCheckApprovalProcessor.mapToStatus(i);
          FFSDebug.log(str1, "retStatus LimitCheck: " + (String)localObject7, 6);
          if ("LIMIT_REVERT_FAILED".equals(localObject7))
          {
            FFSDebug.log(str1, " Limit Revert Failed.", 6);
            if (this.b6 >= 1) {
              a(paramTransferInfo2, "Mod", null, paramBoolean1);
            }
            paramTransferInfo1.setPrcStatus((String)localObject7);
            paramTransferInfo1.setStatusCode(paramTransferInfo2.getStatusCode());
            paramTransferInfo1.setStatusMsg(paramTransferInfo2.getStatusMsg());
            jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo1, str3);
            localObject8 = paramTransferInfo1;
            return localObject8;
          }
          if ("FUNDSPROCESSED".equals(paramTransferInfo2.getPrcStatus()))
          {
            paramTransferInfo2.setAction("Modify");
            jdMethod_for(paramFFSConnectionHolder1, paramTransferInfo2);
            paramTransferInfo1.setPrcStatus(paramTransferInfo2.getPrcStatus());
            paramTransferInfo1.setAction(paramTransferInfo2.getAction());
            if (paramTransferInfo2.getStatusCode() != 0)
            {
              FFSDebug.log(str1, paramTransferInfo2.getStatusMsg(), 0);
              paramTransferInfo1.setStatusCode(paramTransferInfo2.getStatusCode());
              paramTransferInfo1.setStatusMsg(paramTransferInfo2.getStatusMsg());
              jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo1, str3);
              localObject8 = paramTransferInfo1;
              return localObject8;
            }
          }
          i = LimitCheckApprovalProcessor.processExternalTransferAdd(paramFFSConnectionHolder1, paramTransferInfo1, null);
          localObject7 = LimitCheckApprovalProcessor.mapToStatus(i);
          FFSDebug.log(str1, "retStatus LimitCheck: " + (String)localObject7, 6);
          if (("LIMIT_CHECK_FAILED".equals(localObject7)) || ("LIMIT_REVERT_FAILED".equals(localObject7)) || ("APPROVAL_FAILED".equals(localObject7)))
          {
            if (this.b6 >= 1) {
              a(paramTransferInfo1, "Mod", null, paramBoolean1);
            }
            jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo1, str3);
            localObject8 = paramTransferInfo1;
            return localObject8;
          }
          if ("APPROVAL_PENDING".equals(localObject7)) {
            paramTransferInfo1.setPrcStatus("APPROVAL_PENDING");
          } else {
            paramTransferInfo1.setPrcStatus("WILLPROCESSON");
          }
          FFSDebug.log(str1, " single transfer before status update: " + paramTransferInfo2, 6);
          Transfer.updateStatusAction(paramFFSConnectionHolder1, paramTransferInfo1, false);
        }
        paramTransferInfo1 = Transfer.updateTransfer(paramFFSConnectionHolder1, paramTransferInfo1, paramBoolean1, (BPWFIInfo)localObject1, (CustomerInfo)localObject2);
        Object localObject6;
        if (paramTransferInfo1.getStatusCode() != 0)
        {
          if (this.b6 >= 1) {
            a(paramTransferInfo1, "Mod", null, paramBoolean1);
          }
          jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo1, str3);
          localObject6 = paramTransferInfo1;
          return localObject6;
        }
        if ((paramBoolean1 == true) && (!bool2))
        {
          paramFFSConnectionHolder1.conn.commit();
          jdMethod_do(localTransferCalloutHandlerBase, paramTransferInfo1, str3);
          localObject6 = (RecTransferInfo)paramTransferInfo1;
          localObject7 = new ArrayList();
          arrayOfString = Transfer.getSrvrTIDsForRecSrvrTID(paramFFSConnectionHolder1, ((RecTransferInfo)localObject6).getSrvrTId());
          FFSDebug.log(str1, " Number of singleSrvrTIds = " + arrayOfString.length, 6);
          ((RecTransferInfo)localObject6).setSingleIds(arrayOfString);
          if ((arrayOfString != null) && (arrayOfString.length != 0))
          {
            localObject8 = new TransferInfo();
            localTransferInfo2 = null;
            String str5 = ((RecTransferInfo)localObject6).getFIId();
            if ((str5 == null) || (str5.trim().length() == 0)) {
              str5 = ((BPWFIInfo)localObject1).getFIId();
            }
            String str6 = ((RecTransferInfo)localObject6).getCustomerId();
            if ((str6 == null) || (str6.trim().length() == 0)) {
              str6 = ((CustomerInfo)localObject2).customerID;
            }
            for (int j = 0; j < arrayOfString.length; j++)
            {
              ((TransferInfo)localObject8).setSrvrTId(arrayOfString[j]);
              localObject8 = Transfer.getTransferInfo(paramFFSConnectionHolder1, (TransferInfo)localObject8, false);
              if (((TransferInfo)localObject8).getStatusCode() == 0)
              {
                FFSDebug.log(str1, " Single transfer Instance for limit revert: " + localObject8, 6);
                localTransferInfo2 = (TransferInfo)((RecTransferInfo)localObject6).clone();
                localTransferInfo2.setSrvrTId(arrayOfString[j]);
                localTransferInfo2.setSourceRecSrvrTId(paramTransferInfo1.getSrvrTId());
                localTransferInfo2.setTransferType("Recurring");
                localTransferInfo2.setDateDue(((RecTransferInfo)localObject6).getStartDate());
                Transfer.getValidTransferDueDate(paramFFSConnectionHolder1, localTransferInfo2);
                if (!a(localTransferCalloutHandlerBase, localTransferInfo2, "ModifyTransferFromSchedule")) {
                  FFSDebug.log(str1, " begin instance callout ignored ", 6);
                }
                try
                {
                  int k = LimitCheckApprovalProcessor.processExternalTransferDelete(paramFFSConnectionHolder1, (TransferInfo)localObject8, null);
                  localTransferInfo2.setPrcStatus(((TransferInfo)localObject8).getPrcStatus());
                  String str7 = LimitCheckApprovalProcessor.mapToStatus(k);
                  FFSDebug.log(str1, "retStatus LimitCheck: " + str7, 6);
                  if ("LIMIT_REVERT_FAILED".equals(str7))
                  {
                    FFSDebug.log(str1, " Limit Revert Failed.", 6);
                    ((ArrayList)localObject7).add(((TransferInfo)localObject8).getSrvrTId());
                    paramTransferInfo1.setExtInfo(((TransferInfo)localObject8).getExtInfo());
                    if (this.b6 >= 1) {
                      a((TransferInfo)localObject8, "Mod", null, false);
                    }
                    localTransferInfo2.setPrcStatus(((TransferInfo)localObject8).getPrcStatus());
                    localTransferInfo2.setStatusCode(((TransferInfo)localObject8).getStatusCode());
                    localTransferInfo2.setStatusMsg(((TransferInfo)localObject8).getStatusMsg());
                    ((TransferInfo)localObject8).setPrcStatus("FAILEDON");
                    Transfer.updateStatus(paramFFSConnectionHolder1, (TransferInfo)localObject8, false);
                    paramFFSConnectionHolder1.conn.commit();
                    jdMethod_if(localTransferCalloutHandlerBase, localTransferInfo2, "ModifyTransferFromSchedule");
                  }
                  else
                  {
                    if (((TransferInfo)localObject8).getPrcStatus().equals("FUNDSPROCESSED"))
                    {
                      FFSDebug.log(str1, " Single transfer instance in funds revert.", 6);
                      ((TransferInfo)localObject8).setAction("Modify");
                      jdMethod_for(paramFFSConnectionHolder1, (TransferInfo)localObject8);
                      localTransferInfo2.setPrcStatus(((TransferInfo)localObject8).getPrcStatus());
                      localTransferInfo2.setAction(((TransferInfo)localObject8).getAction());
                      if (((TransferInfo)localObject8).getStatusCode() != 0)
                      {
                        ((ArrayList)localObject7).add(((TransferInfo)localObject8).getSrvrTId());
                        paramTransferInfo1.setExtInfo(((TransferInfo)localObject8).getExtInfo());
                        FFSDebug.log(str1, paramTransferInfo1.getStatusMsg(), 0);
                        if (this.b6 >= 1) {
                          a(paramTransferInfo1, "Mod", null, paramBoolean1);
                        }
                        localTransferInfo2.setStatusCode(((TransferInfo)localObject8).getStatusCode());
                        localTransferInfo2.setStatusMsg(((TransferInfo)localObject8).getStatusMsg());
                        jdMethod_if(localTransferCalloutHandlerBase, localTransferInfo2, "ModifyTransferFromSchedule");
                        continue;
                      }
                    }
                    FFSDebug.log(str1, "Checking limits for single transfer instance.", 6);
                    k = LimitCheckApprovalProcessor.processExternalTransferAdd(paramFFSConnectionHolder1, localTransferInfo2, null);
                    str7 = LimitCheckApprovalProcessor.mapToStatus(k);
                    FFSDebug.log(str1, "retStatus LimitCheck: " + str7, 6);
                    if (("LIMIT_CHECK_FAILED".equals(str7)) || ("LIMIT_REVERT_FAILED".equals(str7)) || ("APPROVAL_FAILED".equals(str7)))
                    {
                      ((ArrayList)localObject7).add(localTransferInfo2.getSrvrTId());
                      paramTransferInfo1.setExtInfo(localTransferInfo2.getExtInfo());
                      if (this.b6 >= 1) {
                        a(localTransferInfo2, "Mod", null, false);
                      }
                      localTransferInfo2.setPrcStatus(str7);
                      Transfer.updateStatus(paramFFSConnectionHolder1, localTransferInfo2, false);
                      paramFFSConnectionHolder1.conn.commit();
                      jdMethod_if(localTransferCalloutHandlerBase, localTransferInfo2, "ModifyTransferFromSchedule");
                    }
                    else
                    {
                      if ("APPROVAL_PENDING".equals(str7)) {
                        localTransferInfo2.setPrcStatus("APPROVAL_PENDING");
                      } else {
                        localTransferInfo2.setPrcStatus("WILLPROCESSON");
                      }
                      FFSDebug.log(str1, " single transfer instance before status update: " + localObject8, 6);
                      localTransferInfo2.setAction("Modify");
                      Transfer.updateStatusAction(paramFFSConnectionHolder1, localTransferInfo2, false);
                      Transfer.updateTransfer(paramFFSConnectionHolder1, localTransferInfo2, false, (BPWFIInfo)localObject1, (CustomerInfo)localObject2);
                      if (localTransferInfo2.getStatusCode() != 0)
                      {
                        if (this.b6 >= 1) {
                          a((TransferInfo)localObject8, "Mod", null, false);
                        }
                        ((ArrayList)localObject7).add(localTransferInfo2.getSrvrTId());
                        paramTransferInfo1.setExtInfo(localTransferInfo2.getExtInfo());
                        localTransferInfo2.setPrcStatus("FAILEDON");
                        Transfer.updateStatus(paramFFSConnectionHolder1, localTransferInfo2, false);
                        paramFFSConnectionHolder1.conn.commit();
                        jdMethod_if(localTransferCalloutHandlerBase, localTransferInfo2, "ModifyTransferFromSchedule");
                      }
                      else
                      {
                        if (this.b6 >= 3) {
                          a(paramFFSConnectionHolder2, localTransferInfo2, "Mod", false);
                        }
                        localTransferInfo2.setAction("Modify");
                        jdMethod_if(paramFFSConnectionHolder1, localTransferInfo2, false);
                        if (localTransferInfo2.getStatusCode() != 0)
                        {
                          ((ArrayList)localObject7).add(localTransferInfo2.getSrvrTId());
                          paramTransferInfo1.setExtInfo(localTransferInfo2.getExtInfo());
                          jdMethod_if(localTransferCalloutHandlerBase, localTransferInfo2, "ModifyTransferFromSchedule");
                        }
                        else
                        {
                          paramFFSConnectionHolder1.conn.commit();
                          jdMethod_do(localTransferCalloutHandlerBase, localTransferInfo2, "ModifyTransferFromSchedule");
                        }
                      }
                    }
                  }
                }
                catch (Throwable localThrowable3)
                {
                  ((ArrayList)localObject7).add(localTransferInfo2.getSrvrTId());
                  jdMethod_if(localTransferCalloutHandlerBase, localTransferInfo2, "ModifyTransferFromSchedule");
                }
              }
            }
            if (((ArrayList)localObject7).size() != 0)
            {
              StringBuffer localStringBuffer = new StringBuffer();
              Iterator localIterator = ((ArrayList)localObject7).iterator();
              while (localIterator.hasNext())
              {
                if (localStringBuffer.length() != 0) {
                  localStringBuffer.append(',');
                }
                localStringBuffer.append(localIterator.next());
              }
              paramTransferInfo1.setStatusCode(21550);
              paramTransferInfo1.setStatusMsg(BPWLocaleUtil.getMessage(21550, new String[] { localStringBuffer.toString() }, "TRANSFER_MESSAGE"));
            }
          }
        }
        if ((!paramBoolean1) && (!bool1) && (!bool2))
        {
          FFSDebug.log(str1, "Check if immediate funds processing supported", 6);
          jdMethod_if(paramFFSConnectionHolder1, paramTransferInfo1, false);
          if (paramTransferInfo1.getStatusCode() != 0)
          {
            jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo1, str3);
            localObject6 = paramTransferInfo1;
            return localObject6;
          }
          paramFFSConnectionHolder1.conn.commit();
          jdMethod_do(localTransferCalloutHandlerBase, paramTransferInfo1, str3);
        }
        paramTransferInfo1.setStatusCode(0);
        paramTransferInfo1.setStatusMsg("Success");
      }
      catch (Throwable localThrowable2)
      {
        FFSDebug.log(str1, "Exception thrown. Calling calloutHandler.failure", 0);
        jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo1, str3);
        throw localThrowable2;
      }
      FFSDebug.log(str1, " Done", 6);
    }
    catch (Throwable localThrowable1)
    {
      localThrowable1 = localThrowable1;
      if (this.b6 >= 1)
      {
        localObject1 = BPWLocaleUtil.getLocalizableMessage(1021, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        a(paramTransferInfo1, "Mod", (ILocalizable)localObject1, paramBoolean1);
      }
      Object localObject1 = "*** TransferProcessor.modTransfer failed: ";
      Object localObject2 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1 + (String)localObject2, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    finally {}
    return paramTransferInfo1;
  }
  
  public TransferInfo cancTransfer(TransferInfo paramTransferInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.cancTransfer: ";
    FFSDebug.log(str1, " starts ", 6);
    if (paramTransferInfo == null)
    {
      paramTransferInfo = new RecTransferInfo();
      paramTransferInfo.setStatusCode(16000);
      localObject1 = "TransferInfo object  is null";
      paramTransferInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, (String)localObject1, 0);
      return paramTransferInfo;
    }
    if ("ETOE".equalsIgnoreCase(paramTransferInfo.getTransferDest()))
    {
      localObject1 = BPWLocaleUtil.getMessage(21262, new String[] { paramTransferInfo.getTransferDest() }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusCode(21262);
      paramTransferInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1 + (String)localObject1, 0);
      return paramTransferInfo;
    }
    Object localObject1 = new TransferInfo();
    ((TransferInfo)localObject1).setSrvrTId(paramTransferInfo.getSrvrTId());
    localObject1 = Transfer.getTransferInfo((TransferInfo)localObject1, false, false);
    Hashtable localHashtable = paramTransferInfo.getExtInfo();
    Object localObject2;
    Object localObject3;
    if ((localHashtable != null) && (localHashtable.size() > 0))
    {
      localObject2 = ((TransferInfo)localObject1).getExtInfo();
      if (localObject2 == null)
      {
        ((TransferInfo)localObject1).setExtInfo(localHashtable);
      }
      else
      {
        localObject3 = localHashtable.keys();
        while (((Enumeration)localObject3).hasMoreElements())
        {
          String str2 = (String)((Enumeration)localObject3).nextElement();
          if (!((Hashtable)localObject2).containsKey(str2)) {
            ((Hashtable)localObject2).put(str2, localHashtable.get(str2));
          }
        }
      }
    }
    if (((TransferInfo)localObject1).getStatusCode() == 16020)
    {
      localObject2 = null;
      localObject2 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject2).conn = DBUtil.getConnection();
      try
      {
        localObject3 = a(paramTransferInfo, false, (FFSConnectionHolder)localObject2);
        return localObject3;
      }
      catch (FFSException localFFSException)
      {
        throw localFFSException;
      }
      finally
      {
        if (((FFSConnectionHolder)localObject2).conn != null) {
          ((FFSConnectionHolder)localObject2).conn = DBUtil.getConnection();
        }
      }
    }
    ((TransferInfo)localObject1).setTrnId(paramTransferInfo.getTrnId());
    if (jdMethod_goto(paramTransferInfo.getSubmittedBy()) == true) {
      paramTransferInfo.setSubmittedBy(((TransferInfo)localObject1).getSubmittedBy());
    }
    localObject1 = jdMethod_else((TransferInfo)localObject1, false);
    FFSDebug.log(str1, " end ", 6);
    paramTransferInfo.setPrcStatus(((TransferInfo)localObject1).getPrcStatus());
    paramTransferInfo.setStatusCode(((TransferInfo)localObject1).getStatusCode());
    paramTransferInfo.setStatusMsg(((TransferInfo)localObject1).getStatusMsg());
    return localObject1;
  }
  
  private TransferInfo a(TransferInfo paramTransferInfo, boolean paramBoolean, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "TransferProcessor.cancIntraTransfer(transferInfo): ";
    FFSDebug.log(str1, "Start", 6);
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    String str2 = paramBoolean ? "CancelIntraTransferFromScheduleOFX200" : "CancelIntraTransferOFX200";
    try
    {
      Object localObject2;
      if (paramTransferInfo.getSrvrTId() == null)
      {
        paramTransferInfo.setStatusCode(16010);
        localObject1 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "SrvrTId" }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg((String)localObject1);
        FFSDebug.log(str1, (String)localObject1, 0);
        localObject2 = paramTransferInfo;
        return localObject2;
      }
      if ((!paramBoolean) && (Trans.checkDuplicateTIDAndSaveTID(paramTransferInfo.getTrnId())))
      {
        if (this.b6 >= 1) {
          a(paramFFSConnectionHolder, paramTransferInfo, "CanDup", false);
        }
        paramFFSConnectionHolder.conn.commit();
        paramTransferInfo.setStatusCode(19220);
        paramTransferInfo.setStatusMsg("Duplicate TrnID");
        localObject1 = paramTransferInfo;
        return localObject1;
      }
      Object localObject1 = XferInstruction.getXferInstruction(paramFFSConnectionHolder, paramTransferInfo.getSrvrTId());
      if (localObject1 == null)
      {
        paramTransferInfo.setStatusCode(21260);
        localObject2 = BPWLocaleUtil.getMessage(21260, new String[] { paramTransferInfo.getSrvrTId() }, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        paramTransferInfo.setStatusMsg((String)localObject2);
        FFSDebug.log(str1, (String)localObject2, 0);
        TransferInfo localTransferInfo = paramTransferInfo;
        return localTransferInfo;
      }
      if (((XferInstruction)localObject1).Status.equals("CANCELEDON"))
      {
        paramTransferInfo.setStatusCode(0);
        paramTransferInfo.setPrcStatus("CANCELEDON");
        localObject2 = paramTransferInfo;
        return localObject2;
      }
      paramTransferInfo = TransferIntraMap.mapXferInstToTransferInfo((XferInstruction)localObject1, "Current", "INTERNAL", "Cancel");
      if (!a(localTransferCalloutHandlerBase, paramTransferInfo, str2))
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
        localObject2 = paramTransferInfo;
        return localObject2;
      }
      try
      {
        int i = 1;
        int j = jdMethod_int(paramFFSConnectionHolder, paramTransferInfo);
        Object localObject3;
        Object localObject4;
        if (j == 0)
        {
          ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "INTRATRN", paramTransferInfo.getSrvrTId());
        }
        else
        {
          localObject3 = paramTransferInfo.getPrcStatus();
          if (!"APPROVAL_PENDING".equals(localObject3)) {
            if (("APPROVAL_REJECTED".equals(localObject3)) || ("APPROVAL_NOT_ALLOWED".equals(localObject3)))
            {
              i = 0;
            }
            else
            {
              paramTransferInfo.setStatusCode(2016);
              paramTransferInfo.setPrcStatus("FAILEDON");
              jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
              localObject4 = paramTransferInfo;
              return localObject4;
            }
          }
        }
        Object localObject5;
        if (i != 0)
        {
          localObject3 = TransferIntraMap.mapTranInfoToIntraTrnInfo(paramTransferInfo);
          j = LimitCheckApprovalProcessor.processIntraTrnDelete(paramFFSConnectionHolder, (IntraTrnInfo)localObject3, new HashMap());
          localObject4 = LimitCheckApprovalProcessor.mapToStatus(j);
          FFSDebug.log(str1, "retStatus LimitRevert:" + (String)localObject4, 6);
          if (!"LIMIT_REVERT_SUCCEEDED".equals(localObject4))
          {
            paramTransferInfo.setPrcStatus((String)localObject4);
            paramTransferInfo.setStatusCode(((IntraTrnInfo)localObject3).getStatusCode());
            paramTransferInfo.setStatusMsg(((IntraTrnInfo)localObject3).getStatusMsg());
            if (this.b6 >= 3)
            {
              localObject5 = BPWLocaleUtil.getLocalizedMessage(402, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
              a(paramTransferInfo, "Can", (ILocalizable)localObject5, false);
            }
            jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
            localObject5 = paramTransferInfo;
            return localObject5;
          }
        }
        paramTransferInfo.setAction("Cancel");
        paramTransferInfo.setPrcStatus("CANCELEDON");
        if (this.b6 >= 3) {
          a(paramFFSConnectionHolder, paramTransferInfo, "Can", false);
        }
        int k = XferInstruction.updateStatus(paramFFSConnectionHolder, paramTransferInfo);
        if (k <= 0)
        {
          localObject4 = str1 + ": Intra Transfer status is not updated!! SrvrTID=" + paramTransferInfo.getSrvrTId() + " Status=" + paramTransferInfo.getPrcStatus();
          localObject5 = new StringWriter();
          new Exception("Stack Trace").printStackTrace(new PrintWriter((Writer)localObject5));
          String str5 = ((StringWriter)localObject5).toString();
          FFSDebug.log((String)localObject4 + " -- " + str5, 0);
        }
        FFSDebug.log(str1, paramTransferInfo.getStatusMsg(), 6);
        if (paramTransferInfo.getStatusCode() != 0)
        {
          localObject4 = "*** " + str1 + "failed: ";
          FFSDebug.log((String)localObject4, paramTransferInfo.getStatusMsg(), 0);
          paramFFSConnectionHolder.conn.rollback();
          localObject5 = paramTransferInfo;
          return localObject5;
        }
        paramFFSConnectionHolder.conn.commit();
        jdMethod_do(localTransferCalloutHandlerBase, paramTransferInfo, str2);
      }
      catch (Exception localException)
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
        throw localException;
      }
    }
    catch (Throwable localThrowable)
    {
      localThrowable = localThrowable;
      paramFFSConnectionHolder.conn.rollback();
      String str3 = "*** " + str1 + "failed: ";
      String str4 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3 + str4, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally {}
    FFSDebug.log(str1, " Done", 6);
    return paramTransferInfo;
  }
  
  private int jdMethod_int(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws BPWException
  {
    try
    {
      ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(paramTransferInfo.getFIId(), "INTRATRN", paramTransferInfo.getSrvrTId(), paramFFSConnectionHolder);
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
  
  public RecTransferInfo cancRecTransfer(RecTransferInfo paramRecTransferInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.cancRecTransfer: ";
    FFSDebug.log(str1, " starts ", 6);
    if (paramRecTransferInfo == null)
    {
      paramRecTransferInfo = new RecTransferInfo();
      paramRecTransferInfo.setStatusCode(16000);
      localObject = BPWLocaleUtil.getMessage(16000, new String[] { "RecTransferInfo" }, "TRANSFER_MESSAGE");
      paramRecTransferInfo.setStatusMsg((String)localObject);
      FFSDebug.log("TransferProcessor.cancRecTransfer, ", (String)localObject, 0);
      return paramRecTransferInfo;
    }
    if ("ETOE".equalsIgnoreCase(paramRecTransferInfo.getTransferDest()))
    {
      localObject = BPWLocaleUtil.getMessage(21262, new String[] { paramRecTransferInfo.getTransferDest() }, "TRANSFER_MESSAGE");
      paramRecTransferInfo.setStatusCode(21262);
      paramRecTransferInfo.setStatusMsg((String)localObject);
      FFSDebug.log(str1 + (String)localObject, 0);
      return paramRecTransferInfo;
    }
    Object localObject = new RecTransferInfo();
    ((RecTransferInfo)localObject).setSrvrTId(paramRecTransferInfo.getSrvrTId());
    localObject = (RecTransferInfo)Transfer.getTransferInfo((TransferInfo)localObject, true, false);
    Hashtable localHashtable1 = paramRecTransferInfo.getExtInfo();
    if ((localHashtable1 != null) && (localHashtable1.size() > 0))
    {
      Hashtable localHashtable2 = ((RecTransferInfo)localObject).getExtInfo();
      if (localHashtable2 == null)
      {
        ((RecTransferInfo)localObject).setExtInfo(localHashtable1);
      }
      else
      {
        Enumeration localEnumeration = localHashtable1.keys();
        while (localEnumeration.hasMoreElements())
        {
          String str2 = (String)localEnumeration.nextElement();
          if (!localHashtable2.containsKey(str2)) {
            localHashtable2.put(str2, localHashtable1.get(str2));
          }
        }
      }
    }
    if (((RecTransferInfo)localObject).getStatusCode() == 16020) {
      return a(paramRecTransferInfo);
    }
    ((RecTransferInfo)localObject).setTrnId(paramRecTransferInfo.getTrnId());
    localObject = (RecTransferInfo)jdMethod_else((TransferInfo)localObject, true);
    paramRecTransferInfo.setPrcStatus(((RecTransferInfo)localObject).getPrcStatus());
    paramRecTransferInfo.setStatusCode(((RecTransferInfo)localObject).getStatusCode());
    paramRecTransferInfo.setStatusMsg(((RecTransferInfo)localObject).getStatusMsg());
    FFSDebug.log(str1, " end ", 6);
    return localObject;
  }
  
  private RecTransferInfo a(RecTransferInfo paramRecTransferInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.cancRecIntraTransfer(recTransferInfo): ";
    FFSDebug.log(str1, "Start", 6);
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    String str2 = "CancelIntraRecTransferOFX200";
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str1 + "Can not get DB Connection.");
      }
      Object localObject2;
      if (paramRecTransferInfo.getSrvrTId() == null)
      {
        paramRecTransferInfo.setStatusCode(16010);
        localObject1 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "SrvrTId" }, "TRANSFER_MESSAGE");
        paramRecTransferInfo.setStatusMsg((String)localObject1);
        FFSDebug.log(str1, (String)localObject1, 0);
        localObject2 = paramRecTransferInfo;
        return localObject2;
      }
      Object localObject1 = RecXferInstruction.getRecXferInstruction(localFFSConnectionHolder, paramRecTransferInfo.getSrvrTId());
      if (localObject1 == null)
      {
        paramRecTransferInfo.setStatusCode(21260);
        paramRecTransferInfo.setPrcStatus("FAILEDON");
        localObject2 = BPWLocaleUtil.getMessage(21260, new String[] { paramRecTransferInfo.getSrvrTId() }, "BOOKTRANSFER_AUDITLOG_MESSAGE");
        paramRecTransferInfo.setStatusMsg((String)localObject2);
        FFSDebug.log(str1, (String)localObject2, 0);
        localObject3 = paramRecTransferInfo;
        return localObject3;
      }
      if (jdMethod_goto(paramRecTransferInfo.getLogId()) == true) {
        paramRecTransferInfo.setLogId(((RecXferInstruction)localObject1).LogID);
      }
      if (Trans.checkDuplicateTIDAndSaveTID(paramRecTransferInfo.getTrnId()))
      {
        if (this.b6 >= 1) {
          a(localFFSConnectionHolder, paramRecTransferInfo, "CanDup", true);
        }
        localFFSConnectionHolder.conn.commit();
        paramRecTransferInfo.setStatusCode(19220);
        paramRecTransferInfo.setStatusMsg("Duplicate TrnID");
        localObject2 = paramRecTransferInfo;
        return localObject2;
      }
      paramRecTransferInfo = TransferIntraMap.mapRecXferInstToRecTransferInfo((RecXferInstruction)localObject1);
      paramRecTransferInfo.setTransferType("Recurring");
      if (!a(localTransferCalloutHandlerBase, paramRecTransferInfo, str2))
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramRecTransferInfo, str2);
        localObject2 = paramRecTransferInfo;
        return localObject2;
      }
      try
      {
        int i = a(localFFSConnectionHolder, paramRecTransferInfo);
        if (i == 0)
        {
          paramRecTransferInfo.setStatusCode(0);
          paramRecTransferInfo.setPrcStatus("CANCELEDON");
        }
        else if (i == 1)
        {
          paramRecTransferInfo.setStatusCode(2018);
          paramRecTransferInfo.setPrcStatus("FAILEDON");
        }
        else if (i == 2)
        {
          paramRecTransferInfo.setStatusCode(2017);
          paramRecTransferInfo.setPrcStatus("FAILEDON");
        }
        else if (i == 3)
        {
          paramRecTransferInfo.setStatusCode(2016);
          paramRecTransferInfo.setPrcStatus("FAILEDON");
        }
        if (i != 0)
        {
          localObject3 = paramRecTransferInfo;
          return localObject3;
        }
        if (this.b6 >= 3) {
          a(localFFSConnectionHolder, paramRecTransferInfo, "Can", true);
        }
        paramRecTransferInfo.setAction("Cancel");
        RecXferInstruction.updateStatus(localFFSConnectionHolder, paramRecTransferInfo);
        if (paramRecTransferInfo.getStatusCode() == 0)
        {
          localObject3 = RecSrvrTIDToSrvrTID.getSrvrTIDs(localFFSConnectionHolder, paramRecTransferInfo.getSrvrTId());
          FFSDebug.log(str1, ": srvrTIDs: " + localObject3, 6);
          if (localObject3 != null)
          {
            FFSDebug.log(str1, ": srvrTIDs #: " + localObject3.length, 6);
            for (int j = 0; j < localObject3.length; j++)
            {
              String str4 = XferInstruction.getStatus(localFFSConnectionHolder, localObject3[j]);
              FFSDebug.log(str1, ": srvrTIDs[i]: ", localObject3[j], 6);
              FFSDebug.log(str1, ": status: ", str4, 6);
              if ((str4 != null) && (("WILLPROCESSON".equalsIgnoreCase(str4)) || ("APPROVAL_PENDING".equalsIgnoreCase(str4)) || ("APPROVAL_REJECTED".equals(str4)) || ("APPROVAL_NOT_ALLOWED".equals(str4))))
              {
                TransferInfo localTransferInfo = new TransferInfo();
                localTransferInfo.setSrvrTId(localObject3[j]);
                a(localTransferInfo, true, localFFSConnectionHolder);
                if (localTransferInfo.getStatusCode() != 0)
                {
                  paramRecTransferInfo.setPrcStatus(localTransferInfo.getPrcStatus());
                  paramRecTransferInfo.setStatusCode(localTransferInfo.getStatusCode());
                  paramRecTransferInfo.setStatusMsg(localTransferInfo.getStatusMsg());
                }
              }
            }
          }
        }
        if (paramRecTransferInfo.getStatusCode() == 0)
        {
          localFFSConnectionHolder.conn.commit();
          jdMethod_do(localTransferCalloutHandlerBase, paramRecTransferInfo, str2);
        }
        else
        {
          localFFSConnectionHolder.conn.rollback();
          jdMethod_if(localTransferCalloutHandlerBase, paramRecTransferInfo, str2);
        }
      }
      catch (Exception localException)
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramRecTransferInfo, str2);
        throw localException;
      }
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str3 = "*** " + str1 + "failed: ";
      Object localObject3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3 + (String)localObject3, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, " Done", 6);
    return paramRecTransferInfo;
  }
  
  private int a(FFSConnectionHolder paramFFSConnectionHolder, RecTransferInfo paramRecTransferInfo)
    throws Exception
  {
    try
    {
      ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(paramRecTransferInfo.getFIId(), "RECINTRATRN", paramRecTransferInfo.getSrvrTId(), paramFFSConnectionHolder);
      if (localScheduleInfo == null) {
        return 1;
      }
      if ((localScheduleInfo.Status.equals("Processing") == true) && (localScheduleInfo.StatusOption == 1)) {
        return 1;
      }
      if (!localScheduleInfo.Status.equals("Active"))
      {
        localObject = RecXferInstruction.getStatus(paramFFSConnectionHolder, paramRecTransferInfo.getSrvrTId());
        if (((String)localObject).compareTo("CANCELEDON") == 0) {
          return 2;
        }
        return 3;
      }
      localObject = InstructionTypeStatus.get(paramRecTransferInfo.getFIId(), "RECINTRATRN", paramFFSConnectionHolder);
      if ((localObject != null) && (((InstructionTypeStatus)localObject).SchedulerStatus.equals("DispatcherStarted")))
      {
        int i = DBUtil.getCurrentStartDate();
        if (localScheduleInfo.WorkInstanceDate < i) {
          throw new OFXException("Record locked for modification by scheduler, try again later", 2000);
        }
      }
      if (ScheduleInfo.cancelSchedule(paramFFSConnectionHolder, "RECINTRATRN", paramRecTransferInfo.getSrvrTId()) == 0) {
        throw new BPWException("Cannot cancel transfer in process!!");
      }
    }
    catch (OFXException localOFXException)
    {
      throw new OFXException(localOFXException.getExceptionMsg(), localOFXException.getErrorCode());
    }
    catch (Exception localException)
    {
      Object localObject = "Exception in TransferProcessor.cancelRecIntraSchedule: " + localException.getMessage();
      throw new BPWException((String)localObject);
    }
    return 0;
  }
  
  private TransferInfo jdMethod_else(TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.cancTransfer(isRec): ";
    FFSDebug.log(str1, "Start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str1 + "Can not get DB Connection.");
      }
      String str2;
      if (paramTransferInfo.getSrvrTId() == null)
      {
        paramTransferInfo.setStatusCode(16010);
        str2 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "SrvrTId" }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str2);
        FFSDebug.log(str1, str2, 0);
        if (this.b6 >= 1) {
          a(paramTransferInfo, "Can", null, paramBoolean);
        }
        localObject1 = paramTransferInfo;
        return localObject1;
      }
      paramTransferInfo = jdMethod_do(localFFSConnectionHolder, localFFSConnectionHolder, paramTransferInfo, paramBoolean, true);
      FFSDebug.log(str1, paramTransferInfo.getStatusMsg(), 6);
      if (paramTransferInfo.getStatusCode() != 0)
      {
        str2 = "*** " + str1 + "failed: ";
        FFSDebug.log(str2, paramTransferInfo.getStatusMsg(), 0);
        localFFSConnectionHolder.conn.rollback();
        localObject1 = paramTransferInfo;
        return localObject1;
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject1 = "*** " + str1 + "failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject1 + str3, 0);
      throw new FFSException(localThrowable, (String)localObject1);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log(str1, " Done", 6);
    return paramTransferInfo;
  }
  
  private TransferInfo jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, TransferInfo paramTransferInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "TransferProcessor.cancTransfer(conn): ";
    FFSDebug.log(str1, "Start", 6);
    String[] arrayOfString = null;
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    String str2 = paramBoolean1 ? "CancelRecTransfer" : "CancelTransfer";
    if (paramTransferInfo.getStatusCode() != 0)
    {
      if (this.b6 >= 1) {
        a(paramTransferInfo, "Can", null, paramBoolean1);
      }
      return paramTransferInfo;
    }
    String str3 = paramTransferInfo.getTransferType();
    boolean bool1 = "TEMPLATE".equalsIgnoreCase(str3);
    boolean bool2 = "RECTEMPLATE".equalsIgnoreCase(str3);
    try
    {
      String str4 = paramTransferInfo.getSrvrTId();
      Object localObject1;
      if ((paramBoolean2) && (Trans.checkDuplicateTIDAndSaveTID(paramTransferInfo.getTrnId())))
      {
        if (this.b6 >= 1) {
          a(paramFFSConnectionHolder2, paramTransferInfo, "CanDup", paramBoolean1);
        }
        paramFFSConnectionHolder2.conn.commit();
        paramTransferInfo.setStatusCode(19220);
        paramTransferInfo.setStatusMsg("Duplicate TrnID");
        localObject1 = paramTransferInfo;
        return localObject1;
      }
      Object localObject3;
      if (!jdMethod_byte(paramTransferInfo, paramBoolean1))
      {
        localObject1 = "failed: " + paramTransferInfo.getStatusMsg();
        FFSDebug.log(str1, (String)localObject1, 0);
        if (this.b6 >= 1) {
          a(paramTransferInfo, "Can", null, paramBoolean1);
        }
        localObject3 = paramTransferInfo;
        return localObject3;
      }
      paramTransferInfo.setAction("Cancel");
      paramTransferInfo.setFundsRetry(0);
      Object localObject4;
      if ((!bool2) && (!bool1))
      {
        boolean bool3 = jdMethod_if(paramTransferInfo, paramBoolean1, paramFFSConnectionHolder1);
        if (!bool3)
        {
          paramTransferInfo.setStatusCode(21060);
          localObject3 = BPWLocaleUtil.getMessage(21060, null, "TRANSFER_MESSAGE");
          localObject4 = (String)localObject3 + " SrvrTId: " + str4;
          paramTransferInfo.setStatusMsg((String)localObject4);
          FFSDebug.log(str1 + (String)localObject4, 0);
          if (this.b6 >= 1) {
            a(paramTransferInfo, "Can", null, paramBoolean1);
          }
          TransferInfo localTransferInfo2 = paramTransferInfo;
          return localTransferInfo2;
        }
      }
      if (!a(localTransferCalloutHandlerBase, paramTransferInfo, str2))
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
        TransferInfo localTransferInfo1 = paramTransferInfo;
        return localTransferInfo1;
      }
      try
      {
        if ((paramBoolean1 == true) && (!bool2)) {
          jdMethod_new(paramFFSConnectionHolder1, paramTransferInfo.getSrvrTId());
        }
        if ((!paramBoolean1) && (!bool1) && (!bool2))
        {
          int i = LimitCheckApprovalProcessor.processExternalTransferDelete(paramFFSConnectionHolder1, paramTransferInfo, new HashMap());
          localObject3 = LimitCheckApprovalProcessor.mapToStatus(i);
          FFSDebug.log(str1, "retStatus LimitCheck:" + (String)localObject3, 6);
          if ("LIMIT_REVERT_FAILED".equals(localObject3))
          {
            FFSDebug.log(str1, " Limit Revert Failed.", 6);
            if (this.b6 >= 1) {
              a(paramTransferInfo, "Can", null, paramBoolean1);
            }
            jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
            localObject4 = paramTransferInfo;
            return localObject4;
          }
          if ("FUNDSPROCESSED".equals(paramTransferInfo.getPrcStatus()))
          {
            jdMethod_for(paramFFSConnectionHolder1, paramTransferInfo);
            if (paramTransferInfo.getStatusCode() != 0)
            {
              FFSDebug.log(str1, paramTransferInfo.getStatusMsg(), 0);
              jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
              localObject4 = paramTransferInfo;
              return localObject4;
            }
          }
        }
        Transfer.cancelTransfer(paramFFSConnectionHolder1, paramTransferInfo, paramBoolean1);
        if (paramTransferInfo.getStatusCode() != 0)
        {
          String str5 = "failed: " + paramTransferInfo.getStatusMsg();
          FFSDebug.log(str1, str5, 0);
          if (this.b6 >= 1) {
            a(paramTransferInfo, "Can", null, paramBoolean1);
          }
          if ((!paramBoolean1) && (!bool1) && (!bool2))
          {
            localObject3 = (TransferInfo)paramTransferInfo.clone();
            ((TransferInfo)localObject3).setPrcStatus("FAILEDON");
            Transfer.updateStatusAction(paramFFSConnectionHolder1, (TransferInfo)localObject3, false);
            paramFFSConnectionHolder1.conn.commit();
          }
          jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
          localObject3 = paramTransferInfo;
          return localObject3;
        }
        paramFFSConnectionHolder1.conn.commit();
        jdMethod_do(localTransferCalloutHandlerBase, paramTransferInfo, str2);
      }
      catch (Throwable localThrowable2)
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
        throw localThrowable2;
      }
      if ((paramBoolean1 == true) && (!bool2))
      {
        localObject2 = (RecTransferInfo)paramTransferInfo;
        arrayOfString = Transfer.getSrvrTIDsForRecSrvrTID(paramFFSConnectionHolder1, ((RecTransferInfo)localObject2).getSrvrTId());
        FFSDebug.log(str1, " Number of singleSrvrTIds:" + arrayOfString.length, 6);
        ((RecTransferInfo)localObject2).setSingleIds(arrayOfString);
        localObject3 = new TransferInfo();
        localObject4 = new ArrayList();
        for (int k = 0; k < arrayOfString.length; k++)
        {
          ((TransferInfo)localObject3).setSrvrTId(arrayOfString[k]);
          localObject3 = Transfer.getTransferInfo(paramFFSConnectionHolder1, (TransferInfo)localObject3, false);
          if (((TransferInfo)localObject3).getStatusCode() == 0)
          {
            FFSDebug.log(str1, " single transfer Instance for limit revert:" + localObject3, 6);
            if (!a(localTransferCalloutHandlerBase, (TransferInfo)localObject3, "CancelTransferFromSchedule")) {
              FFSDebug.log(str1, " begin instance callout ignored ", 6);
            }
            try
            {
              int m = LimitCheckApprovalProcessor.processExternalTransferDelete(paramFFSConnectionHolder1, (TransferInfo)localObject3, null);
              String str7 = LimitCheckApprovalProcessor.mapToStatus(m);
              FFSDebug.log(str1, "retStatus LimitCheck: " + str7, 6);
              if ("LIMIT_REVERT_FAILED".equals(str7))
              {
                FFSDebug.log(str1, "Limit Revert Failed.", 6);
                ((ArrayList)localObject4).add(((TransferInfo)localObject3).getSrvrTId());
                paramTransferInfo.setExtInfo(((TransferInfo)localObject3).getExtInfo());
                if (this.b6 >= 1) {
                  a(paramTransferInfo, "Can", null, false);
                }
                ((TransferInfo)localObject3).setPrcStatus("FAILEDON");
                Transfer.updateStatus(paramFFSConnectionHolder1, (TransferInfo)localObject3, false);
                paramFFSConnectionHolder1.conn.commit();
                jdMethod_if(localTransferCalloutHandlerBase, (TransferInfo)localObject3, "CancelTransferFromSchedule");
              }
              else
              {
                if (((TransferInfo)localObject3).getPrcStatus().equals("FUNDSPROCESSED"))
                {
                  FFSDebug.log(str1, "Single transfer instance in funds revert.", 6);
                  ((TransferInfo)localObject3).setAction("Cancel");
                  jdMethod_for(paramFFSConnectionHolder1, (TransferInfo)localObject3);
                  if (((TransferInfo)localObject3).getStatusCode() != 0)
                  {
                    ((ArrayList)localObject4).add(((TransferInfo)localObject3).getSrvrTId());
                    paramTransferInfo.setExtInfo(((TransferInfo)localObject3).getExtInfo());
                    FFSDebug.log(str1, paramTransferInfo.getStatusMsg(), 0);
                    if (this.b6 >= 1) {
                      a(paramTransferInfo, "Can", null, false);
                    }
                    jdMethod_if(localTransferCalloutHandlerBase, (TransferInfo)localObject3, "CancelTransferFromSchedule");
                    continue;
                  }
                }
                Transfer.cancelTransfer(paramFFSConnectionHolder1, (TransferInfo)localObject3, false);
                if (((TransferInfo)localObject3).getStatusCode() != 0)
                {
                  ((ArrayList)localObject4).add(((TransferInfo)localObject3).getSrvrTId());
                  ((TransferInfo)localObject3).setPrcStatus("FAILEDON");
                  Transfer.updateStatus(paramFFSConnectionHolder1, (TransferInfo)localObject3, false);
                  paramFFSConnectionHolder1.conn.commit();
                  jdMethod_if(localTransferCalloutHandlerBase, (TransferInfo)localObject3, "CancelTransferFromSchedule");
                  if (this.b6 >= 1) {
                    a((TransferInfo)localObject2, "Can", null, false);
                  }
                }
                else
                {
                  paramFFSConnectionHolder1.conn.commit();
                  jdMethod_do(localTransferCalloutHandlerBase, (TransferInfo)localObject3, "CancelTransferFromSchedule");
                }
              }
            }
            catch (Throwable localThrowable3)
            {
              ((ArrayList)localObject4).add(arrayOfString[k]);
              jdMethod_if(localTransferCalloutHandlerBase, (TransferInfo)localObject3, "CancelTransferFromSchedule");
            }
          }
        }
        if (((ArrayList)localObject4).size() != 0)
        {
          StringBuffer localStringBuffer = new StringBuffer();
          Iterator localIterator = ((ArrayList)localObject4).iterator();
          while (localIterator.hasNext())
          {
            if (localStringBuffer.length() != 0) {
              localStringBuffer.append(',');
            }
            localStringBuffer.append(localIterator.next());
          }
          paramTransferInfo.setStatusCode(21560);
          paramTransferInfo.setStatusMsg(BPWLocaleUtil.getMessage(21560, new String[] { localStringBuffer.toString() }, "TRANSFER_MESSAGE"));
        }
      }
      jdMethod_if(paramFFSConnectionHolder1, paramTransferInfo);
      paramTransferInfo.setPrcStatus("CANCELEDON");
      if (this.b6 >= 3)
      {
        a(paramFFSConnectionHolder2, paramTransferInfo, "Can", paramBoolean1);
        if ((paramBoolean1 == true) && (!bool2) && (arrayOfString != null))
        {
          localObject2 = new TransferInfo();
          for (int j = 0; j < arrayOfString.length; j++)
          {
            ((TransferInfo)localObject2).setSrvrTId(arrayOfString[j]);
            localObject2 = Transfer.getTransferInfo(paramFFSConnectionHolder1, (TransferInfo)localObject2, false, false);
            a(paramFFSConnectionHolder2, (TransferInfo)localObject2, "Can", false);
          }
        }
      }
      paramFFSConnectionHolder1.conn.commit();
    }
    catch (Throwable localThrowable1)
    {
      localThrowable1 = localThrowable1;
      if (this.b6 >= 1)
      {
        localObject2 = BPWLocaleUtil.getLocalizableMessage(1021, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        a(paramTransferInfo, "Can", (ILocalizable)localObject2, paramBoolean1);
      }
      Object localObject2 = "*** " + str1 + "failed: ";
      String str6 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject2 + str6, 0);
      throw new FFSException(localThrowable1, (String)localObject2);
    }
    finally {}
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log(str1, " Done", 6);
    return paramTransferInfo;
  }
  
  private TransferInfo a(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, TransferInfo paramTransferInfo, boolean paramBoolean1, boolean paramBoolean2)
    throws FFSException
  {
    String str1 = "TransferProcessor.cancTransferTemplate(conn): ";
    FFSDebug.log(str1, "Start", 6);
    if (paramTransferInfo.getStatusCode() != 0)
    {
      if (this.b6 >= 1) {
        a(paramTransferInfo, "Can", null, paramBoolean1);
      }
      return paramTransferInfo;
    }
    try
    {
      Object localObject1;
      if ((paramBoolean2) && (Trans.checkDuplicateTIDAndSaveTID(paramTransferInfo.getTrnId())))
      {
        if (this.b6 >= 1) {
          a(paramFFSConnectionHolder2, paramTransferInfo, "CanDup", paramBoolean1);
        }
        paramTransferInfo.setStatusCode(19220);
        paramTransferInfo.setStatusMsg("Duplicate TrnID");
        localObject1 = paramTransferInfo;
        return localObject1;
      }
      if (!jdMethod_byte(paramTransferInfo, paramBoolean1))
      {
        localObject1 = "failed: " + paramTransferInfo.getStatusMsg();
        FFSDebug.log(str1, (String)localObject1, 0);
        if (this.b6 >= 1) {
          a(paramTransferInfo, "Can", null, paramBoolean1);
        }
        localObject2 = paramTransferInfo;
        return localObject2;
      }
      paramTransferInfo.setAction("Cancel");
      paramTransferInfo.setFundsRetry(0);
      try
      {
        Transfer.cancelTransfer(paramFFSConnectionHolder1, paramTransferInfo, paramBoolean1);
        if (paramTransferInfo.getStatusCode() != 0)
        {
          localObject1 = "failed: " + paramTransferInfo.getStatusMsg();
          FFSDebug.log(str1, (String)localObject1, 0);
          if (this.b6 >= 1) {
            a(paramTransferInfo, "Can", null, paramBoolean1);
          }
          localObject2 = paramTransferInfo;
          return localObject2;
        }
      }
      catch (Throwable localThrowable1)
      {
        throw localThrowable1;
      }
      jdMethod_if(paramFFSConnectionHolder1, paramTransferInfo);
      paramTransferInfo.setPrcStatus("CANCELEDON");
      if (this.b6 >= 3) {
        a(paramFFSConnectionHolder2, paramTransferInfo, "Can", paramBoolean1);
      }
    }
    catch (Throwable localThrowable2)
    {
      localThrowable2 = localThrowable2;
      if (this.b6 >= 1)
      {
        localObject2 = BPWLocaleUtil.getLocalizableMessage(1021, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        a(paramTransferInfo, "Can", (ILocalizable)localObject2, paramBoolean1);
      }
      Object localObject2 = "*** " + str1 + "failed: ";
      String str2 = FFSDebug.stackTrace(localThrowable2);
      FFSDebug.log((String)localObject2 + str2, 0);
      throw new FFSException(localThrowable2, (String)localObject2);
    }
    finally {}
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log(str1, " Done", 6);
    return paramTransferInfo;
  }
  
  public TransferInfo getTransferBySrvrTId(String paramString)
    throws FFSException
  {
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      TransferInfo localTransferInfo = new TransferInfo();
      localTransferInfo.setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "srvrIId" }, "TRANSFER_MESSAGE");
      localTransferInfo.setStatusMsg(str);
      FFSDebug.log("TransferProcessor.getTransferBySrvrTId, " + str, 0);
      return localTransferInfo;
    }
    return jdMethod_byte(paramString, false);
  }
  
  public RecTransferInfo getRecTransferBySrvrTId(String paramString)
    throws FFSException
  {
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      RecTransferInfo localRecTransferInfo = new RecTransferInfo();
      localRecTransferInfo.setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "srvrTId" }, "TRANSFER_MESSAGE");
      localRecTransferInfo.setStatusMsg(str);
      FFSDebug.log("TransferProcessor.getRecTransferBySrvrTId, " + str, 0);
      return localRecTransferInfo;
    }
    return (RecTransferInfo)jdMethod_byte(paramString, true);
  }
  
  private TransferInfo jdMethod_byte(String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.getTransferBySrvrTId: ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1 = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException("TransferProcessor.getTransferBySrvrTId: Can not get DB Connection.");
      }
      if (paramBoolean) {
        localObject1 = new RecTransferInfo();
      } else {
        localObject1 = new TransferInfo();
      }
      ((TransferInfo)localObject1).setSrvrTId(paramString);
      localObject1 = Transfer.getTransferInfo(localFFSConnectionHolder, (TransferInfo)localObject1, paramBoolean);
      if (((TransferInfo)localObject1).getStatusCode() == 16020)
      {
        Object localObject2;
        if (paramBoolean)
        {
          localObject2 = RecXferInstruction.getRecXferInstruction(localFFSConnectionHolder, paramString);
          if (localObject2 != null)
          {
            ((RecTransferInfo)localObject1).populate(((RecXferInstruction)localObject2).RecSrvrTID, ((RecXferInstruction)localObject2).FIID, ((RecXferInstruction)localObject2).CustomerID, ((RecXferInstruction)localObject2).BankID, ((RecXferInstruction)localObject2).BranchID, ((RecXferInstruction)localObject2).AcctDebitID, ((RecXferInstruction)localObject2).AcctDebitType, ((RecXferInstruction)localObject2).AcctCreditID, ((RecXferInstruction)localObject2).AcctCreditType, String.valueOf(((RecXferInstruction)localObject2).Amount), String.valueOf(((RecXferInstruction)localObject2).StartDate), String.valueOf(((RecXferInstruction)localObject2).EndDate), FFSUtil.getFreqString(((RecXferInstruction)localObject2).Frequency), ((RecXferInstruction)localObject2).InstanceCount, ((RecXferInstruction)localObject2).LogID, ((RecXferInstruction)localObject2).Status, "Repetitive", ((RecXferInstruction)localObject2).SubmittedBy, "INTERNAL");
            ((TransferInfo)localObject1).setAmountCurrency(((RecXferInstruction)localObject2).CurDef);
            ((TransferInfo)localObject1).setToAmount(String.valueOf(((RecXferInstruction)localObject2).ToAmount));
            ((TransferInfo)localObject1).setToAmountCurrency(((RecXferInstruction)localObject2).ToAmtCurrency);
            ((TransferInfo)localObject1).setUserAssignedAmount(((RecXferInstruction)localObject2).userAssignedAmount);
            ((TransferInfo)localObject1).setStatusCode(0);
            ((TransferInfo)localObject1).setStatusMsg("Success");
            if ((((RecXferInstruction)localObject2).extraFields != null) && ((((RecXferInstruction)localObject2).extraFields instanceof HashMap))) {
              ((TransferInfo)localObject1).setExtInfo(new Hashtable((HashMap)((RecXferInstruction)localObject2).extraFields));
            }
          }
        }
        else
        {
          localObject2 = XferInstruction.getIntraById(paramString, localFFSConnectionHolder);
          if (((IntraTrnInfo)localObject2).statusCode != 17020)
          {
            ((TransferInfo)localObject1).populate(((IntraTrnInfo)localObject2).srvrTid, ((IntraTrnInfo)localObject2).fiId, ((IntraTrnInfo)localObject2).customerId, ((IntraTrnInfo)localObject2).bankId, ((IntraTrnInfo)localObject2).branchId, ((IntraTrnInfo)localObject2).acctIdFrom, ((IntraTrnInfo)localObject2).acctTypeFrom, ((IntraTrnInfo)localObject2).acctIdTo, ((IntraTrnInfo)localObject2).acctTypeTo, ((IntraTrnInfo)localObject2).amount, ((IntraTrnInfo)localObject2).dateToPost, ((IntraTrnInfo)localObject2).dateToPost, ((IntraTrnInfo)localObject2).recSrvrTid, ((IntraTrnInfo)localObject2).logId, ((IntraTrnInfo)localObject2).status, "Current", ((IntraTrnInfo)localObject2).submittedBy, "INTERNAL");
            ((TransferInfo)localObject1).setAmountCurrency(((IntraTrnInfo)localObject2).curDef);
            ((TransferInfo)localObject1).setToAmount(String.valueOf(((IntraTrnInfo)localObject2).toAmount));
            ((TransferInfo)localObject1).setToAmountCurrency(((IntraTrnInfo)localObject2).toAmtCurrency);
            ((TransferInfo)localObject1).setUserAssignedAmount(((IntraTrnInfo)localObject2).userAssignedAmount);
            ((TransferInfo)localObject1).setStatusCode(0);
            ((TransferInfo)localObject1).setStatusMsg("Success");
            if ((((IntraTrnInfo)localObject2).extraFields != null) && ((((IntraTrnInfo)localObject2).extraFields instanceof HashMap))) {
              ((TransferInfo)localObject1).setExtInfo(new Hashtable((HashMap)((IntraTrnInfo)localObject2).extraFields));
            }
          }
        }
      }
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** TransferProcessor.getTransferBySrvrTId failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, " Done", 6);
    return localObject1;
  }
  
  public TransferInfo[] getTransfersBySrvrTId(String[] paramArrayOfString)
    throws FFSException
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      TransferInfo[] arrayOfTransferInfo = new TransferInfo[1];
      arrayOfTransferInfo[0] = new TransferInfo();
      arrayOfTransferInfo[0].setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "srvrTId" }, "TRANSFER_MESSAGE");
      arrayOfTransferInfo[0].setStatusMsg(str);
      FFSDebug.log("TransferProcessor.getTransfersBySrvrTId, " + str, 0);
      return arrayOfTransferInfo;
    }
    return jdMethod_do(paramArrayOfString, false);
  }
  
  public TransferInfo[] getTransfersByRecSrvrTId(String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.getTransfersByRecSrvrTId:";
    if ((paramString == null) || ((paramString != null) && (paramString.trim().length() == 0)))
    {
      localObject1 = new TransferInfo[1];
      localObject1[0] = new TransferInfo();
      localObject1[0].setStatusCode(16000);
      localObject2 = BPWLocaleUtil.getMessage(16000, new String[] { "recSrvrTId" }, "TRANSFER_MESSAGE");
      localObject1[0].setStatusMsg((String)localObject2);
      FFSDebug.log(str1, (String)localObject2, 0);
      return localObject1;
    }
    Object localObject1 = null;
    Object localObject2 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      if (((FFSConnectionHolder)localObject1).conn == null) {
        throw new FFSException(str1 + "Can not get DB Connection.");
      }
      localObject2 = Transfer.getTransfersByRecSrvrTId((FFSConnectionHolder)localObject1, paramString, paramBoolean);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Throwable localThrowable)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      String str2 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    return localObject2;
  }
  
  public TransferInfo[] getTransfersByRecSrvrTId(String[] paramArrayOfString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.getTransfersByRecSrvrTId:";
    if ((paramArrayOfString == null) || ((paramArrayOfString != null) && (paramArrayOfString.length == 0)))
    {
      localObject1 = new TransferInfo[1];
      localObject1[0] = new TransferInfo();
      localObject1[0].setStatusCode(16000);
      localObject2 = BPWLocaleUtil.getMessage(16000, new String[] { "recSrvrTId" }, "TRANSFER_MESSAGE");
      localObject1[0].setStatusMsg((String)localObject2);
      FFSDebug.log(str1, (String)localObject2, 0);
      return localObject1;
    }
    Object localObject1 = null;
    Object localObject2 = null;
    try
    {
      localObject1 = new FFSConnectionHolder();
      ((FFSConnectionHolder)localObject1).conn = DBUtil.getDirtyReadConnection();
      if (((FFSConnectionHolder)localObject1).conn == null) {
        throw new FFSException(str1 + "Can not get DB Connection.");
      }
      localObject2 = Transfer.getTransfersByRecSrvrTId((FFSConnectionHolder)localObject1, paramArrayOfString, paramBoolean);
      ((FFSConnectionHolder)localObject1).conn.commit();
    }
    catch (Throwable localThrowable)
    {
      ((FFSConnectionHolder)localObject1).conn.rollback();
      String str2 = str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
    }
    return localObject2;
  }
  
  public RecTransferInfo[] getRecTransfersBySrvrTId(String[] paramArrayOfString)
    throws FFSException
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      arrayOfRecTransferInfo = new RecTransferInfo[1];
      arrayOfRecTransferInfo[0] = new RecTransferInfo();
      arrayOfRecTransferInfo[0].setStatusCode(16000);
      localObject1 = BPWLocaleUtil.getMessage(16000, new String[] { "srvrTId" }, "TRANSFER_MESSAGE");
      arrayOfRecTransferInfo[0].setStatusMsg((String)localObject1);
      FFSDebug.log("TransferProcessor.getRecTransfersBySrvrTId, " + (String)localObject1, 0);
      return arrayOfRecTransferInfo;
    }
    RecTransferInfo[] arrayOfRecTransferInfo = (RecTransferInfo[])jdMethod_do(paramArrayOfString, true);
    Object localObject1 = new ArrayList();
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < arrayOfRecTransferInfo.length; i++) {
      if (arrayOfRecTransferInfo[i].getStatusCode() == 16020) {
        localArrayList.add(arrayOfRecTransferInfo[i].getSrvrTId());
      } else {
        ((ArrayList)localObject1).add(arrayOfRecTransferInfo[i]);
      }
    }
    if (localArrayList.size() > 0)
    {
      localObject2 = new String[localArrayList.size()];
      localArrayList.toArray((Object[])localObject2);
      RecIntraTrnInfo[] arrayOfRecIntraTrnInfo = this.cc.getRecIntraById((String[])localObject2);
      for (int j = 0; j < arrayOfRecIntraTrnInfo.length; j++)
      {
        RecTransferInfo localRecTransferInfo = TransformIFXRecXferInq.getRecTransferInfoFromRecIntraTrnInfo(arrayOfRecIntraTrnInfo[j]);
        if (localRecTransferInfo.getStatusCode() == 17020)
        {
          localRecTransferInfo.setStatusCode(16020);
          localRecTransferInfo.setStatusMsg(" record not found");
        }
        ((ArrayList)localObject1).add(localRecTransferInfo);
      }
    }
    Object localObject2 = new RecTransferInfo[((ArrayList)localObject1).size()];
    ((ArrayList)localObject1).toArray((Object[])localObject2);
    return localObject2;
  }
  
  private TransferInfo[] jdMethod_do(String[] paramArrayOfString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.getTransfersBySrvrTId: ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1 = null;
    Object localObject2 = null;
    ArrayList localArrayList = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str1 + "Can not get DB Connection.");
      }
      localArrayList = new ArrayList();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        if (paramBoolean) {
          localObject2 = new RecTransferInfo();
        } else {
          localObject2 = new TransferInfo();
        }
        ((TransferInfo)localObject2).setSrvrTId(paramArrayOfString[i]);
        localObject2 = Transfer.getTransferInfo(localFFSConnectionHolder, (TransferInfo)localObject2, paramBoolean, false);
        localArrayList.add(localObject2);
      }
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1, "Done", 6);
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = str1 + "failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    if (paramBoolean) {
      localObject1 = (RecTransferInfo[])localArrayList.toArray(new RecTransferInfo[0]);
    } else {
      localObject1 = (TransferInfo[])localArrayList.toArray(new TransferInfo[0]);
    }
    return localObject1;
  }
  
  public TransferInfo getTransferByTrackingId(String paramString)
    throws FFSException
  {
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      TransferInfo localTransferInfo = new TransferInfo();
      localTransferInfo.setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "trackingId" }, "TRANSFER_MESSAGE");
      localTransferInfo.setStatusMsg(str);
      FFSDebug.log("TransferProcessor.getTransferBySrvrTId, " + str, 0);
      return localTransferInfo;
    }
    return jdMethod_int(paramString, false);
  }
  
  public RecTransferInfo getRecTransferByTrackingId(String paramString)
    throws FFSException
  {
    if ((paramString == null) || (paramString.trim().length() == 0))
    {
      RecTransferInfo localRecTransferInfo = new RecTransferInfo();
      localRecTransferInfo.setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "trackingId" }, "TRANSFER_MESSAGE");
      localRecTransferInfo.setStatusMsg(str);
      FFSDebug.log("TransferProcessor.getRecTransferByTrackingId, " + str, 0);
      return localRecTransferInfo;
    }
    return (RecTransferInfo)jdMethod_int(paramString, true);
  }
  
  private TransferInfo jdMethod_int(String paramString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.getTransferByTrackingId(boolean): ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1 = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str1 + "Can not get DB Connection.");
      }
      if (paramBoolean) {
        localObject1 = new RecTransferInfo();
      } else {
        localObject1 = new TransferInfo();
      }
      ((TransferInfo)localObject1).setLogId(paramString);
      localObject1 = Transfer.getTransferInfoByTrackingId(localFFSConnectionHolder, (TransferInfo)localObject1, paramBoolean, true);
      localFFSConnectionHolder.conn.commit();
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** " + str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, " Done", 6);
    return localObject1;
  }
  
  public TransferInfo[] getTransfersByTrackingId(String[] paramArrayOfString)
    throws FFSException
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      TransferInfo[] arrayOfTransferInfo = new TransferInfo[1];
      arrayOfTransferInfo[0] = new TransferInfo();
      arrayOfTransferInfo[0].setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "trackingId" }, "TRANSFER_MESSAGE");
      arrayOfTransferInfo[0].setStatusMsg(str);
      FFSDebug.log("TransferProcessor.getTransfersByTrackingId, " + str, 0);
      return arrayOfTransferInfo;
    }
    return jdMethod_if(paramArrayOfString, false);
  }
  
  public RecTransferInfo[] getRecTransfersByTrackingId(String[] paramArrayOfString)
    throws FFSException
  {
    if ((paramArrayOfString == null) || (paramArrayOfString.length == 0))
    {
      RecTransferInfo[] arrayOfRecTransferInfo = new RecTransferInfo[1];
      arrayOfRecTransferInfo[0] = new RecTransferInfo();
      arrayOfRecTransferInfo[0].setStatusCode(16000);
      String str = BPWLocaleUtil.getMessage(16000, new String[] { "trackingId" }, "TRANSFER_MESSAGE");
      arrayOfRecTransferInfo[0].setStatusMsg(str);
      FFSDebug.log("TransferProcessor.getRecTransfersByTrackingId, " + str, 0);
      return arrayOfRecTransferInfo;
    }
    return (RecTransferInfo[])jdMethod_if(paramArrayOfString, true);
  }
  
  private TransferInfo[] jdMethod_if(String[] paramArrayOfString, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.getTransfersByTrackingId(boolean): ";
    FFSConnectionHolder localFFSConnectionHolder = null;
    Object localObject1 = null;
    Object localObject2 = null;
    ArrayList localArrayList = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      if (localFFSConnectionHolder.conn == null) {
        throw new FFSException(str1 + "Can not get DB Connection.");
      }
      localArrayList = new ArrayList();
      for (int i = 0; i < paramArrayOfString.length; i++)
      {
        if (paramBoolean) {
          localObject2 = new RecTransferInfo();
        } else {
          localObject2 = new TransferInfo();
        }
        ((TransferInfo)localObject2).setLogId(paramArrayOfString[i]);
        localObject2 = Transfer.getTransferInfoByTrackingId(localFFSConnectionHolder, (TransferInfo)localObject2, paramBoolean, true);
        localArrayList.add(localObject2);
      }
      localFFSConnectionHolder.conn.commit();
      FFSDebug.log(str1, " Done", 6);
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str2 = "*** " + str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2 + str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    if (paramBoolean) {
      localObject1 = (RecTransferInfo[])localArrayList.toArray(new RecTransferInfo[0]);
    } else {
      localObject1 = (TransferInfo[])localArrayList.toArray(new TransferInfo[0]);
    }
    return localObject1;
  }
  
  public BPWHist getTransferHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("TransferProcessor.getTransferHistory : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramBPWHist = Transfer.getTransferHistory(localFFSConnectionHolder, paramBPWHist, false);
      localFFSConnectionHolder.conn.commit();
      BPWHist localBPWHist = paramBPWHist;
      return localBPWHist;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "TransferProcessor.getTransferHistory failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWHist getRecTransfers(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("TransferProcessor.getRecTransfers : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramBPWHist = Transfer.getRecTransfers(localFFSConnectionHolder, paramBPWHist);
      localFFSConnectionHolder.conn.commit();
      BPWHist localBPWHist = paramBPWHist;
      return localBPWHist;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "TransferProcessor.getRecTransfers failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public BPWHist getRecTransferHistory(BPWHist paramBPWHist)
    throws FFSException
  {
    FFSDebug.log("TransferProcessor.getRecTransferHistory : start ", 6);
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      paramBPWHist = Transfer.getTransferHistory(localFFSConnectionHolder, paramBPWHist, true);
      localFFSConnectionHolder.conn.commit();
      BPWHist localBPWHist = paramBPWHist;
      return localBPWHist;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      String str = "TransferProcessor.getRecTransferHistory failed " + localThrowable.toString();
      FFSDebug.log(str + FFSDebug.stackTrace(localThrowable), 0);
      throw new FFSException(localThrowable, str);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private boolean jdMethod_byte(TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.hasRequiredStatus: ";
    FFSDebug.log(str1, "Start", 6);
    String str2 = paramTransferInfo.getSrvrTId();
    String str3 = Transfer.getStatus(str2, paramBoolean);
    paramTransferInfo.setPrcStatus(str3);
    if (str3 == null)
    {
      paramTransferInfo.setStatusCode(16020);
      str4 = " record not found SrvrTId: " + str2;
      paramTransferInfo.setStatusMsg(str4);
      FFSDebug.log(str1 + ", " + str4, 0);
      return false;
    }
    String str4 = paramTransferInfo.getTransferType();
    String[] arrayOfString = null;
    if (paramBoolean)
    {
      if ("RECTEMPLATE".equalsIgnoreCase(str4))
      {
        arrayOfString = new String[1];
        arrayOfString[0] = "RECTEMPLATE";
      }
      else
      {
        arrayOfString = new String[3];
        arrayOfString[0] = "WILLPROCESSON";
        arrayOfString[1] = "APPROVAL_PENDING";
        arrayOfString[2] = "APPROVAL_REJECTED";
      }
    }
    else if ("TEMPLATE".equalsIgnoreCase(str4))
    {
      arrayOfString = new String[1];
      arrayOfString[0] = "TEMPLATE";
    }
    else
    {
      arrayOfString = new String[6];
      arrayOfString[0] = "WILLPROCESSON";
      arrayOfString[1] = "APPROVAL_PENDING";
      arrayOfString[2] = "APPROVAL_REJECTED";
      arrayOfString[3] = "FUNDSPROCESSED";
      arrayOfString[4] = "NOFUNDS";
      arrayOfString[5] = "IMMED_INPROCESS";
    }
    FFSDebug.log(str1 + "status=", str3, 6);
    int i = 0;
    for (int j = 0; j < arrayOfString.length; j++) {
      if (str3.equals(arrayOfString[j]))
      {
        i = 1;
        break;
      }
    }
    if (i == 0)
    {
      paramTransferInfo.setStatusCode(19170);
      String str5 = "Record is not in required status for the requested processing. SrvrTId: " + str2;
      paramTransferInfo.setStatusMsg(str5);
      FFSDebug.log(str1 + ", " + str5, 0);
      return false;
    }
    FFSDebug.log(str1, "Done", 6);
    return true;
  }
  
  private void a(RecTransferInfo paramRecTransferInfo, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.validateRecurringInfo";
    jdMethod_char(paramRecTransferInfo, true);
    if (paramRecTransferInfo.getStatusCode() != 0) {
      return;
    }
    jdMethod_for(paramFFSConnectionHolder, paramRecTransferInfo, paramBoolean);
    if (paramRecTransferInfo.getStatusCode() != 0) {
      return;
    }
    String str2 = paramRecTransferInfo.getFrequency();
    if (str2 == null)
    {
      paramRecTransferInfo.setStatusCode(19100);
      paramRecTransferInfo.setStatusMsg("Frequency missing in recurring wire");
      return;
    }
    int i = FFSUtil.getFreqInt(str2);
    String str3;
    if (i == -1)
    {
      paramRecTransferInfo.setStatusCode(17200);
      str3 = "Invalid frequency value for recurring-batch. :" + str2;
      paramRecTransferInfo.setStatusMsg(str3);
      FFSDebug.log(str1, str3, 0);
      return;
    }
    if (!"RECTEMPLATE".equalsIgnoreCase(paramRecTransferInfo.getTransferType()))
    {
      if (paramRecTransferInfo.getPmtsCount() <= 1)
      {
        paramRecTransferInfo.setStatusCode(19350);
        paramRecTransferInfo.setStatusMsg("For a recurring model number of payments must be greater than one");
        return;
      }
      str3 = paramRecTransferInfo.getStartDate();
      String str4;
      if (str3 == null)
      {
        paramRecTransferInfo.setStatusCode(21030);
        str4 = BPWLocaleUtil.getMessage(21030, null, "TRANSFER_MESSAGE");
        paramRecTransferInfo.setStatusMsg(str4);
        return;
      }
      if (!BPWUtil.checkDateBeanFormat(str3))
      {
        paramRecTransferInfo.setStatusCode(17201);
        str4 = "Invalid start date value for recurring-batch. :" + str3;
        paramRecTransferInfo.setStatusMsg(str4);
        FFSDebug.log(str1, str4, 0);
        return;
      }
      int j = BPWUtil.getDateDBFormat(str3);
      int k = BPWUtil.getTodayDBFormat();
      if (k > j)
      {
        j = k;
        paramRecTransferInfo.setStartDate(BPWUtil.getDateBeanFormat(j));
      }
      try
      {
        int m = CommonProcessor.getEndDate(DBUtil.getPayday(paramRecTransferInfo.getFIId(), j), i, paramRecTransferInfo.getPmtsCount());
        paramRecTransferInfo.setEndDate(BPWUtil.getDateBeanFormat(m));
      }
      catch (Throwable localThrowable)
      {
        paramRecTransferInfo.setStatusCode(17202);
        String str5 = "Invalid values for recurring-batch. :" + j;
        paramRecTransferInfo.setStatusMsg(str5);
        FFSDebug.log(str1, str5, 0);
        return;
      }
    }
    paramRecTransferInfo.setStatusCode(0);
    paramRecTransferInfo.setStatusMsg("Success");
  }
  
  private void jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.validateCommonInfo: ";
    FFSDebug.log(str1 + "Starts ...", 6);
    String str2 = null;
    if ("ITOE".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
      str2 = paramTransferInfo.getAccountToId();
    } else if ("ETOI".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
      str2 = paramTransferInfo.getAccountFromId();
    }
    String str3 = str2;
    if ((str3 != null) && (str3.trim().length() != 0))
    {
      ExtTransferAcctInfo localExtTransferAcctInfo = new ExtTransferAcctInfo();
      localExtTransferAcctInfo.setAcctId(str3);
      if (paramBoolean) {
        localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo);
      } else {
        localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo, false, true);
      }
      if (jdMethod_goto(paramTransferInfo.getCustomerId()))
      {
        paramTransferInfo.setStatusCode(16010);
        str4 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "CustomerId" }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str4);
        return;
      }
      String str4 = "N";
      if (paramTransferInfo.getCustomerInfo() != null) {
        str4 = paramTransferInfo.getCustomerInfo().getVirtualCustomer();
      }
      String str5;
      if ((str4.equalsIgnoreCase("N")) && ((localExtTransferAcctInfo.getCustomerId() == null) || (!localExtTransferAcctInfo.getCustomerId().equals(paramTransferInfo.getCustomerId()))))
      {
        paramTransferInfo.setStatusCode(19130);
        str5 = BPWLocaleUtil.getMessage(19130, null, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str5);
        return;
      }
      if (localExtTransferAcctInfo.getStatusCode() == 16020)
      {
        paramTransferInfo.setStatusCode(21080);
        str5 = BPWLocaleUtil.getMessage(21080, null, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str5);
        return;
      }
      if ("ITOE".equals(paramTransferInfo.getTransferDest()))
      {
        paramTransferInfo.setAccountToNum(localExtTransferAcctInfo.getAcctNum());
        paramTransferInfo.setAccountToType(localExtTransferAcctInfo.getAcctType());
      }
      else if ("ETOI".equals(paramTransferInfo.getTransferDest()))
      {
        paramTransferInfo.setAccountFromNum(localExtTransferAcctInfo.getAcctNum());
        paramTransferInfo.setAccountFromType(localExtTransferAcctInfo.getAcctType());
      }
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    if (!this.b7)
    {
      if ((paramBPWFIInfo.getACHSameDayEffDate() != null) && (paramBPWFIInfo.getACHSameDayEffDate().equalsIgnoreCase("Y"))) {
        paramTransferInfo.setProcessLeadNumber(0);
      } else {
        paramTransferInfo.setProcessLeadNumber(1);
      }
    }
    else if (ACHSameDayEffDate.isSameDayEffDate(paramFFSConnectionHolder, 0, paramTransferInfo.getCustomerId(), paramTransferInfo.getTypeDetail())) {
      paramTransferInfo.setProcessLeadNumber(0);
    } else {
      paramTransferInfo.setProcessLeadNumber(1);
    }
  }
  
  private void a(TransferInfo paramTransferInfo, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.validateTransferInfo: ";
    jdMethod_char(paramTransferInfo, false);
    if (paramTransferInfo.getStatusCode() != 0) {
      return;
    }
    jdMethod_for(paramFFSConnectionHolder, paramTransferInfo, paramBoolean);
    if (paramTransferInfo.getStatusCode() != 0) {
      return;
    }
    String str2 = paramTransferInfo.getDateDue();
    if ((str2 != null) && (str2.length() == 10))
    {
      int i = str2.lastIndexOf("00");
      if (i != -1) {
        str2 = str2.substring(0, i);
      }
    }
    if (str2 == null)
    {
      paramTransferInfo.setDateDue("" + DBUtil.getCurrentStartDate() / 100);
    }
    else if (!BPWUtil.checkDateBeanFormat(str2))
    {
      paramTransferInfo.setStatusCode(17203);
      String str3 = "Invalid due date value for batch. :" + str2;
      paramTransferInfo.setStatusMsg(str3);
      return;
    }
    FFSDebug.log(str1, "DateDue: " + paramTransferInfo.getDateDue(), 6);
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
  }
  
  private void jdMethod_else(TransferInfo paramTransferInfo)
  {
    String str = "TransferProcessor.validateIntraDates: ";
    FFSDebug.log(str + "Starts ...", 6);
    if ((jdMethod_goto(paramTransferInfo.getDateDue())) && ((paramTransferInfo instanceof RecTransferInfo))) {
      paramTransferInfo.setDateDue(((RecTransferInfo)paramTransferInfo).getStartDate());
    }
    int i = DBUtil.getCurrentStartDate();
    int j = 0;
    if (BPWUtil.validateDate(paramTransferInfo.getDateDue(), "yyyyMMdd"))
    {
      j = BPWUtil.getDateDBFormat(paramTransferInfo.getDateDue());
      if (j < i)
      {
        paramTransferInfo.setStatusCode(17203);
        paramTransferInfo.setStatusMsg("Invalid due date value for batch.");
        FFSDebug.log(str + paramTransferInfo.getStatusMsg(), 0);
      }
    }
    else
    {
      paramTransferInfo.setStatusCode(17203);
      paramTransferInfo.setStatusMsg("Invalid due date value for batch.");
      FFSDebug.log(str + paramTransferInfo.getStatusMsg(), 0);
      return;
    }
    if (paramTransferInfo.getProcessType() == 2)
    {
      if (j > i)
      {
        paramTransferInfo.setStatusCode(17203);
        paramTransferInfo.setStatusMsg("Invalid due date value for batch.");
        FFSDebug.log(str + paramTransferInfo.getStatusMsg(), 0);
        return;
      }
      paramTransferInfo.setDateToPost(paramTransferInfo.getDateDue());
    }
    else
    {
      try
      {
        int k = SmartCalendar.getPayday(paramTransferInfo.getFIId(), Integer.parseInt(paramTransferInfo.getDateDue()), "BookTransfer");
        paramTransferInfo.setDateToPost(Integer.toString(k));
      }
      catch (Exception localException)
      {
        paramTransferInfo.setStatusCode(19200);
        paramTransferInfo.setStatusMsg("Error Calculating the Pay Day");
        FFSDebug.log(str + paramTransferInfo.getStatusMsg(), 0);
        return;
      }
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log(str + "Done.", 6);
  }
  
  private void a(TransferInfo paramTransferInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "TransferProcessor.validateDates: ";
    FFSDebug.log(str1 + "Starts ...", 6);
    String str2 = paramTransferInfo.getDateToPost();
    Object localObject;
    if (jdMethod_goto(str2) == true)
    {
      try
      {
        int i = Transfer.getValidTransferDueDate(paramFFSConnectionHolder, paramTransferInfo);
        if ((this.cd) && (i != Integer.parseInt(paramTransferInfo.getDateDue())))
        {
          paramTransferInfo.setStatusCode(17203);
          paramTransferInfo.setStatusMsg("Invalid due date value for batch.");
          FFSDebug.log(str1 + paramTransferInfo.getStatusMsg(), 0);
          return;
        }
        FFSDebug.log(str1, "Calculated DateToPost = ", paramTransferInfo.getDateToPost(), 6);
      }
      catch (Throwable localThrowable1)
      {
        localObject = "Exception in " + str1;
        String str4 = FFSDebug.stackTrace(localThrowable1);
        FFSDebug.log((String)localObject + str4, 0);
        paramTransferInfo.setStatusCode(19200);
        paramTransferInfo.setStatusMsg("Error Calculating the Pay Day");
        return;
      }
    }
    else
    {
      if (str2.length() == 10)
      {
        int j = str2.lastIndexOf("00");
        if (j != -1) {
          str2 = str2.substring(0, j);
        }
      }
      String str3 = null;
      localObject = (TransferInfo)paramTransferInfo.clone();
      ((TransferInfo)localObject).setDateDue(str2);
      try
      {
        int k = Transfer.getValidTransferDueDate(paramFFSConnectionHolder, (TransferInfo)localObject);
        str3 = String.valueOf(k);
      }
      catch (Throwable localThrowable2)
      {
        String str6 = "Exception in " + str1;
        String str7 = FFSDebug.stackTrace(localThrowable2);
        FFSDebug.log(str6 + str7, 0);
      }
      if (!str2.equals(str3))
      {
        String str5 = BPWLocaleUtil.getMessage(21480, null, "TRANSFER_MESSAGE");
        FFSDebug.log(str5, 0);
        paramTransferInfo.setStatusCode(21480);
        paramTransferInfo.setStatusMsg(str5);
        return;
      }
      paramTransferInfo.setProcessDate(((TransferInfo)localObject).getProcessDate());
      FFSDebug.log(str1, "Validated DateToPost = " + str2, 6);
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + "Done.", 6);
  }
  
  private void jdMethod_char(TransferInfo paramTransferInfo, boolean paramBoolean)
    throws BPWException
  {
    String str1 = "TransferProcessor.validateTransferType: ";
    String str2 = paramTransferInfo.getTransferType();
    String str3;
    if (str2 == null)
    {
      paramTransferInfo.setStatusCode(21000);
      str3 = BPWLocaleUtil.getMessage(21000, null, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusMsg(str3);
      return;
    }
    if (("Repetitive".equalsIgnoreCase(str2)) || ("RECTEMPLATE".equalsIgnoreCase(str2)))
    {
      if (!paramBoolean)
      {
        str3 = BPWLocaleUtil.getMessage(21010, new String[] { str2 }, "TRANSFER_MESSAGE");
        FFSDebug.log("***", str1, str3, 0);
        paramTransferInfo.setStatusCode(21010);
        paramTransferInfo.setStatusMsg(str3);
      }
    }
    else if (("Current".equalsIgnoreCase(str2)) || ("TEMPLATE".equalsIgnoreCase(str2)))
    {
      if (paramBoolean)
      {
        paramTransferInfo.setStatusCode(21010);
        str3 = BPWLocaleUtil.getMessage(21010, new String[] { str2 }, "TRANSFER_MESSAGE");
        FFSDebug.log("***", str1, str3, 0);
        paramTransferInfo.setStatusMsg(str3);
      }
    }
    else
    {
      str3 = BPWLocaleUtil.getMessage(21020, new String[] { str2 }, "TRANSFER_MESSAGE");
      FFSDebug.log("***", str1, str3, 0);
      paramTransferInfo.setStatusCode(21020);
      paramTransferInfo.setStatusMsg(str3);
      return;
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
  }
  
  private boolean jdMethod_new(String paramString, boolean paramBoolean)
  {
    try
    {
      paramString = paramString.trim();
      if (paramBoolean == true)
      {
        if (!paramString.equalsIgnoreCase("EXTERNAL")) {
          return false;
        }
      }
      else if (!paramString.equalsIgnoreCase("INTERNAL")) {
        return false;
      }
    }
    catch (Exception localException) {}
    return true;
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, String paramString, boolean paramBoolean)
    throws Exception
  {
    String str1 = "TransferProcessor.logActivity: ";
    FFSDebug.log(str1, "starts", 6);
    String str2 = null;
    String str3 = null;
    String str4 = null;
    AuditLogRecord localAuditLogRecord = null;
    Object localObject1 = null;
    int i = 0;
    str2 = paramTransferInfo.getPrcStatus();
    str3 = paramTransferInfo.getTransferType();
    if ((str3.equals("TEMPLATE")) || (str3.equals("RECTEMPLATE"))) {
      return;
    }
    Object localObject2;
    if ("Add".equalsIgnoreCase(paramString))
    {
      localObject2 = jdMethod_new(paramTransferInfo, paramBoolean);
      i = ((a)localObject2).a;
      localObject1 = ((a)localObject2).jdField_if;
    }
    else if ("Mod".equalsIgnoreCase(paramString))
    {
      localObject2 = jdMethod_try(paramTransferInfo, paramBoolean);
      i = ((a)localObject2).a;
      localObject1 = ((a)localObject2).jdField_if;
    }
    else if ("Can".equalsIgnoreCase(paramString))
    {
      localObject2 = jdMethod_case(paramTransferInfo, paramBoolean);
      i = ((a)localObject2).a;
      localObject1 = ((a)localObject2).jdField_if;
    }
    else if ("AddDup".equalsIgnoreCase(paramString))
    {
      localObject2 = jdMethod_int(paramTransferInfo, paramBoolean);
      i = ((a)localObject2).a;
      localObject1 = ((a)localObject2).jdField_if;
    }
    else if ("ModDup".equalsIgnoreCase(paramString))
    {
      localObject2 = jdMethod_if(paramTransferInfo, paramBoolean);
      i = ((a)localObject2).a;
      localObject1 = ((a)localObject2).jdField_if;
    }
    else if ("CanDup".equalsIgnoreCase(paramString))
    {
      localObject2 = jdMethod_for(paramTransferInfo, paramBoolean);
      i = ((a)localObject2).a;
      localObject1 = ((a)localObject2).jdField_if;
    }
    else if ("ProcessApprvlRslt".equalsIgnoreCase(paramString))
    {
      i = 5200;
      localObject2 = paramTransferInfo.getPrcStatus();
      if (((String)localObject2).equals("WILLPROCESSON")) {
        localObject1 = BPWLocaleUtil.getLocalizableMessage(1016, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      } else if (((String)localObject2).equals("APPROVAL_REJECTED")) {
        localObject1 = BPWLocaleUtil.getLocalizableMessage(1017, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      }
    }
    else if ("ProcessFunds".equalsIgnoreCase(paramString))
    {
      i = 5252;
      localObject1 = BPWLocaleUtil.getLocalizableMessage(1019, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    }
    else if ("RevertFunds".equalsIgnoreCase(paramString))
    {
      i = 5254;
      localObject1 = BPWLocaleUtil.getLocalizableMessage(1020, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    }
    else if ("AddImmediate".equalsIgnoreCase(paramString))
    {
      i = 4607;
      localObject1 = BPWLocaleUtil.getLocalizableMessage(915, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
    }
    else
    {
      localObject1 = new LocalizableString("dummy", "", null);
    }
    str4 = paramTransferInfo.getSubmittedBy();
    FFSDebug.log("logActivity: userId", str4, 6);
    try
    {
      int j = 0;
      str5 = null;
      String str6 = null;
      String str7 = null;
      String str8 = null;
      ExtTransferAcctInfo localExtTransferAcctInfo = new ExtTransferAcctInfo();
      String str9 = null;
      if ("ITOE".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
        str9 = paramTransferInfo.getAccountToId();
      } else if ("ETOI".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
        str9 = paramTransferInfo.getAccountFromId();
      }
      if (str9 != null)
      {
        localExtTransferAcctInfo.setAcctId(str9);
        localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo);
        if (localExtTransferAcctInfo.getStatusCode() == 0) {
          if ("ITOE".equals(paramTransferInfo.getTransferDest()))
          {
            str5 = localExtTransferAcctInfo.buildAcctId();
            str6 = localExtTransferAcctInfo.getAcctBankRtn();
          }
          else if ("ETOI".equals(paramTransferInfo.getTransferDest()))
          {
            str7 = localExtTransferAcctInfo.buildAcctId();
            str8 = localExtTransferAcctInfo.getAcctBankRtn();
          }
        }
      }
      if (str5 == null)
      {
        str5 = paramTransferInfo.buildToAcctId();
        str6 = paramTransferInfo.getBankToRtn();
      }
      if (str7 == null)
      {
        str7 = paramTransferInfo.buildFromAcctId();
        str8 = paramTransferInfo.getBankFromRtn();
      }
      j = Integer.parseInt(paramTransferInfo.getCustomerId());
      String str10 = paramTransferInfo.getAmount();
      if (str10 == null) {
        str10 = "0.00";
      }
      String str11 = paramTransferInfo.getToAmount();
      if (str11 == null) {
        str11 = "0.00";
      }
      localAuditLogRecord = new AuditLogRecord(str4, 0, null, null, (ILocalizable)localObject1, paramTransferInfo.getLogId(), i, j, new BigDecimal(str10), paramTransferInfo.getAmountCurrency(), new BigDecimal(str11), paramTransferInfo.getToAmountCurrency(), paramTransferInfo.getUserAssignedAmount(), paramTransferInfo.getSrvrTId(), paramTransferInfo.getPrcStatus(), str5, str6, str7, str8, AuditLogUtil.getModuleFromTranType(i));
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      String str5 = str1 + " failed " + localNumberFormatException.toString();
      FFSDebug.log(str5 + FFSDebug.stackTrace(localNumberFormatException), 0);
      throw new FFSException(localNumberFormatException, str5);
    }
    paramTransferInfo.setPrcStatus(str2);
    FFSDebug.log(str1, "Done", 6);
  }
  
  private a jdMethod_new(TransferInfo paramTransferInfo, boolean paramBoolean)
  {
    a locala = new a(null);
    String str = paramTransferInfo.getTransferType();
    if (jdMethod_char(paramTransferInfo)) {
      locala.a = 4600;
    } else {
      locala.a = 5200;
    }
    if (paramBoolean)
    {
      if (jdMethod_char(paramTransferInfo))
      {
        locala.a = 4700;
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(905, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
      }
      else
      {
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1000, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      }
    }
    else if ((str != null) && (str.equalsIgnoreCase("Current")))
    {
      if (jdMethod_char(paramTransferInfo)) {
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(913, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
      } else {
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1001, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      }
    }
    else if (jdMethod_char(paramTransferInfo)) {
      locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(913, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
    } else {
      locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1002, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    }
    return locala;
  }
  
  private a jdMethod_try(TransferInfo paramTransferInfo, boolean paramBoolean)
  {
    a locala = new a(null);
    String str = paramTransferInfo.getTransferType();
    if (jdMethod_char(paramTransferInfo)) {
      locala.a = 4603;
    } else {
      locala.a = 5201;
    }
    paramTransferInfo.setPrcStatus("MODIFIED");
    if (paramBoolean)
    {
      if (jdMethod_char(paramTransferInfo))
      {
        locala.a = 4702;
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(907, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
      }
      else
      {
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1003, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      }
    }
    else if ((str != null) && (str.equalsIgnoreCase("Current")))
    {
      if (jdMethod_char(paramTransferInfo)) {
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(918, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
      } else {
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1004, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      }
    }
    else if (jdMethod_char(paramTransferInfo)) {
      locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(918, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
    } else {
      locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1005, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    }
    return locala;
  }
  
  private a jdMethod_case(TransferInfo paramTransferInfo, boolean paramBoolean)
  {
    a locala = new a(null);
    String str = paramTransferInfo.getTransferType();
    if (jdMethod_char(paramTransferInfo)) {
      locala.a = 4605;
    } else {
      locala.a = 5202;
    }
    if (paramBoolean)
    {
      if (jdMethod_char(paramTransferInfo))
      {
        locala.a = 4704;
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(910, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
      }
      else
      {
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1006, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      }
    }
    else if ((str != null) && (str.equalsIgnoreCase("Current")))
    {
      if (jdMethod_char(paramTransferInfo)) {
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(921, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
      } else {
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1007, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      }
    }
    else if (jdMethod_char(paramTransferInfo)) {
      locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(921, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
    } else {
      locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1008, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    }
    return locala;
  }
  
  private a jdMethod_int(TransferInfo paramTransferInfo, boolean paramBoolean)
  {
    a locala = new a(null);
    if (jdMethod_char(paramTransferInfo)) {
      locala.a = 4601;
    } else {
      locala.a = 5203;
    }
    paramTransferInfo.setPrcStatus("DUPLICATE");
    if (paramBoolean)
    {
      if (jdMethod_char(paramTransferInfo))
      {
        locala.a = 4701;
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(914, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
      }
      else
      {
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1009, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      }
    }
    else if (jdMethod_char(paramTransferInfo)) {
      locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(906, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
    } else {
      locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1010, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    }
    return locala;
  }
  
  private a jdMethod_if(TransferInfo paramTransferInfo, boolean paramBoolean)
  {
    a locala = new a(null);
    if (jdMethod_char(paramTransferInfo)) {
      locala.a = 4604;
    } else {
      locala.a = 5204;
    }
    paramTransferInfo.setPrcStatus("DUPLICATE");
    if (paramBoolean)
    {
      if (jdMethod_char(paramTransferInfo))
      {
        locala.a = 4703;
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(919, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
      }
      else
      {
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1011, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      }
    }
    else if (jdMethod_char(paramTransferInfo)) {
      locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(908, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
    } else {
      locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1012, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    }
    return locala;
  }
  
  private a jdMethod_for(TransferInfo paramTransferInfo, boolean paramBoolean)
  {
    a locala = new a(null);
    if (jdMethod_char(paramTransferInfo)) {
      locala.a = 4606;
    } else {
      locala.a = 5205;
    }
    paramTransferInfo.setPrcStatus("DUPLICATE");
    if (paramBoolean)
    {
      if (jdMethod_char(paramTransferInfo))
      {
        locala.a = 4705;
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(922, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
      }
      else
      {
        locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1013, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      }
    }
    else if (jdMethod_char(paramTransferInfo)) {
      locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(911, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
    } else {
      locala.jdField_if = BPWLocaleUtil.getLocalizableMessage(1014, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    }
    return locala;
  }
  
  private void a(TransferInfo paramTransferInfo, String paramString, ILocalizable paramILocalizable, boolean paramBoolean)
  {
    String str1 = "TransferProcessor.logError: ";
    FFSDebug.log(str1, "starts", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    AuditLogRecord localAuditLogRecord = null;
    String str2 = null;
    String str3 = null;
    int i = 0;
    if ((paramTransferInfo.getLogId() == null) || (paramTransferInfo.getLogId().trim().length() == 0)) {
      return;
    }
    if ((paramTransferInfo.getSubmittedBy() == null) || (paramTransferInfo.getSubmittedBy().trim().length() == 0)) {
      return;
    }
    str3 = paramTransferInfo.getTransferType();
    if ((str3 == null) || (str3.equals("TEMPLATE")) || (str3.equals("RECTEMPLATE"))) {
      return;
    }
    int j = 1018;
    int k = 5200;
    if ("Add".equalsIgnoreCase(paramString))
    {
      if (jdMethod_char(paramTransferInfo))
      {
        k = 4600;
        j = 901;
      }
      else
      {
        k = 5200;
        j = 1030;
      }
    }
    else if ("Mod".equalsIgnoreCase(paramString))
    {
      if (jdMethod_char(paramTransferInfo))
      {
        k = 4603;
        j = 929;
      }
      else
      {
        k = 5201;
        j = 1031;
      }
    }
    else if ("Can".equalsIgnoreCase(paramString))
    {
      if (jdMethod_char(paramTransferInfo))
      {
        k = 4605;
        j = 930;
      }
      else
      {
        k = 5202;
        j = 1032;
      }
    }
    else if ("AddImmediate".equalsIgnoreCase(paramString))
    {
      k = 4607;
      j = 917;
    }
    else if ("ProcessFunds".equalsIgnoreCase(paramString))
    {
      k = 5252;
      j = 1034;
    }
    else if ("RevertFunds".equalsIgnoreCase(paramString))
    {
      k = 5254;
      j = 1033;
    }
    String str5;
    Object localObject1;
    if (paramILocalizable == null)
    {
      int m = 1021;
      str5 = "EXTERNALTRANSFER_AUDITLOG_MESSAGE";
      if (jdMethod_char(paramTransferInfo))
      {
        m = 928;
        str5 = "BOOKTRANSFER_AUDITLOG_MESSAGE";
      }
      localObject1 = new Object[1];
      if ((paramTransferInfo.getStatusMsg() != null) && (paramTransferInfo.getStatusMsg().length() > 0)) {
        localObject1[0] = paramTransferInfo.getStatusMsg();
      } else {
        localObject1[0] = BPWLocaleUtil.getLocalizableMessage(m, null, str5);
      }
      paramILocalizable = BPWLocaleUtil.getLocalizableMessage(j, (Object[])localObject1, str5);
    }
    str2 = paramTransferInfo.getSubmittedBy();
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        str4 = str1 + "Can not get DB Connection.";
        FFSDebug.log(str4, 0);
      }
      String str4 = null;
      str5 = null;
      localObject1 = null;
      String str6 = null;
      ExtTransferAcctInfo localExtTransferAcctInfo = new ExtTransferAcctInfo();
      String str7 = null;
      if ("ITOE".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
        str7 = paramTransferInfo.getAccountToId();
      } else if ("ETOI".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
        str7 = paramTransferInfo.getAccountFromId();
      }
      localExtTransferAcctInfo.setAcctId(str7);
      localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(localFFSConnectionHolder, localExtTransferAcctInfo);
      if (localExtTransferAcctInfo.getStatusCode() == 0) {
        if ("ITOE".equals(paramTransferInfo.getTransferDest()))
        {
          str4 = localExtTransferAcctInfo.buildAcctId();
          str5 = localExtTransferAcctInfo.getAcctBankRtn();
        }
        else if ("ETOI".equals(paramTransferInfo.getTransferDest()))
        {
          localObject1 = localExtTransferAcctInfo.buildAcctId();
          str6 = localExtTransferAcctInfo.getAcctBankRtn();
        }
      }
      if (str4 == null)
      {
        str4 = paramTransferInfo.buildToAcctId();
        str5 = paramTransferInfo.getBankToRtn();
      }
      if (localObject1 == null)
      {
        localObject1 = paramTransferInfo.buildFromAcctId();
        str6 = paramTransferInfo.getBankFromRtn();
      }
      if ((paramTransferInfo.getCustomerId() != null) && (paramTransferInfo.getCustomerId().length() > 0)) {
        if (paramTransferInfo.getCustomerId().equals(paramTransferInfo.getSubmittedBy())) {
          i = 0;
        } else {
          i = Integer.parseInt(paramTransferInfo.getCustomerId());
        }
      }
      FFSDebug.log(str1, "LogId=", paramTransferInfo.getLogId(), 6);
      localAuditLogRecord = new AuditLogRecord(str2, 0, null, null, paramILocalizable, paramTransferInfo.getLogId(), k, i, new BigDecimal(paramTransferInfo.getAmount()), paramTransferInfo.getAmountCurrency(), new BigDecimal(paramTransferInfo.getToAmount()), paramTransferInfo.getToAmountCurrency(), paramTransferInfo.getUserAssignedAmount(), paramTransferInfo.getSrvrTId(), paramTransferInfo.getPrcStatus(), str4, str5, (String)localObject1, str6, AuditLogUtil.getModuleFromTranType(k));
      TransAuditLog.logTransAuditLog(localAuditLogRecord, localFFSConnectionHolder.conn.getConnection());
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException)
    {
      str5 = str1 + " failed " + localException.toString();
      FFSDebug.log(str5 + FFSDebug.stackTrace(localException), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1, "Done", 6);
  }
  
  private boolean a(TransferInfo paramTransferInfo, boolean paramBoolean)
    throws BPWException
  {
    if (paramTransferInfo == null)
    {
      paramTransferInfo = new TransferInfo();
      paramTransferInfo.setStatusCode(16000);
      str1 = BPWLocaleUtil.getMessage(16000, new String[] { "TransferInfo" }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusMsg(str1);
      return false;
    }
    String str1 = paramTransferInfo.getFIId();
    if (jdMethod_goto(str1))
    {
      paramTransferInfo.setStatusCode(16010);
      str2 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "FIId" }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusMsg(str2);
      return false;
    }
    String str2 = paramTransferInfo.getCustomerId();
    if (jdMethod_goto(str2))
    {
      paramTransferInfo.setStatusCode(16010);
      str3 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "CustomerId" }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusMsg(str3);
      return false;
    }
    String str3 = paramTransferInfo.getLogId();
    if (jdMethod_goto(str3))
    {
      paramTransferInfo.setStatusCode(16010);
      str4 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "Tracking Id" }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusMsg(str4);
      return false;
    }
    String str4 = paramTransferInfo.getAccountFromId();
    String str5 = paramTransferInfo.getAccountFromNum();
    String str6 = paramTransferInfo.getAccountFromType();
    String str7 = paramTransferInfo.getBankFromRtn();
    ExtTransferAcctInfo localExtTransferAcctInfo1 = paramTransferInfo.getAccountFromInfo();
    if ((jdMethod_goto(str4)) && (localExtTransferAcctInfo1 == null) && ((jdMethod_goto(str5)) || (jdMethod_goto(str6)) || (jdMethod_goto(str7))))
    {
      paramTransferInfo.setStatusCode(16010);
      str8 = "From account data in TransferInfo object contains null ";
      paramTransferInfo.setStatusMsg(str8);
      return false;
    }
    String str8 = paramTransferInfo.getAccountToId();
    String str9 = paramTransferInfo.getAccountToNum();
    String str10 = paramTransferInfo.getAccountToType();
    String str11 = paramTransferInfo.getBankToRtn();
    ExtTransferAcctInfo localExtTransferAcctInfo2 = paramTransferInfo.getAccountToInfo();
    if ((jdMethod_goto(str8)) && (localExtTransferAcctInfo2 == null) && ((jdMethod_goto(str9)) || (jdMethod_goto(str10)) || (jdMethod_goto(str11))))
    {
      paramTransferInfo.setStatusCode(16010);
      str12 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "To Account Data" }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusMsg(str12);
      return false;
    }
    String str12 = paramTransferInfo.getTransferType();
    if (jdMethod_goto(str12))
    {
      if (paramBoolean) {
        str12 = "Repetitive";
      } else {
        str12 = "Current";
      }
      paramTransferInfo.setTransferType(str12);
    }
    String str13 = paramTransferInfo.getAmount();
    String str14 = paramTransferInfo.getAmountCurrency();
    String str15 = paramTransferInfo.getToAmount();
    String str16 = paramTransferInfo.getToAmountCurrency();
    if (paramTransferInfo.getUserAssignedAmount() == UserAssignedAmount.SINGLE)
    {
      if (jdMethod_byte(paramTransferInfo))
      {
        if (jdMethod_goto(str13)) {
          paramTransferInfo.setAmount("0.00");
        }
      }
      else if ((jdMethod_goto(str13)) || (str13.equals("0.00")))
      {
        paramTransferInfo.setStatusCode(16010);
        str17 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "Amount" }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str17);
        return false;
      }
      if (jdMethod_goto(str15)) {
        paramTransferInfo.setToAmount("0.00");
      }
      if (jdMethod_goto(str14)) {
        paramTransferInfo.setAmountCurrency(this.ce);
      }
      if ((!jdMethod_goto(str15)) && (!str15.equals("0.00")) && (!str15.equals(str13)))
      {
        paramTransferInfo.setStatusCode(21735);
        str17 = BPWLocaleUtil.getMessage(21735, new String[] { str13, str15 }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str17);
        return false;
      }
      if ((!jdMethod_goto(str16)) && (!str16.equals(str14)))
      {
        paramTransferInfo.setStatusCode(21736);
        str17 = BPWLocaleUtil.getMessage(21736, new String[] { str14, str16 }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str17);
        return false;
      }
    }
    if (paramTransferInfo.getUserAssignedAmount() == UserAssignedAmount.FROM)
    {
      if (jdMethod_byte(paramTransferInfo))
      {
        if (jdMethod_goto(str13)) {
          paramTransferInfo.setAmount("0.00");
        }
      }
      else if ((jdMethod_goto(str13)) || (str13.equals("0.00")))
      {
        paramTransferInfo.setStatusCode(16010);
        str17 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "Amount" }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str17);
        return false;
      }
      if (jdMethod_goto(str14))
      {
        paramTransferInfo.setStatusCode(16010);
        str17 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "AmountCurrency" }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str17);
        return false;
      }
      if (jdMethod_goto(str15)) {
        paramTransferInfo.setToAmount("0.00");
      }
      if (jdMethod_goto(str16))
      {
        paramTransferInfo.setStatusCode(16010);
        str17 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "ToAmountCurrency" }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str17);
        return false;
      }
      if (str14.equals(str16))
      {
        paramTransferInfo.setStatusCode(21737);
        str17 = BPWLocaleUtil.getMessage(21737, new String[] { str14 }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str17);
        return false;
      }
    }
    if (paramTransferInfo.getUserAssignedAmount() == UserAssignedAmount.TO)
    {
      if (jdMethod_byte(paramTransferInfo))
      {
        if (jdMethod_goto(str15)) {
          paramTransferInfo.setToAmount("0.00");
        }
      }
      else if ((jdMethod_goto(str15)) || (str15.equals("0.00")))
      {
        paramTransferInfo.setStatusCode(16010);
        str17 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "ToAmount" }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str17);
        return false;
      }
      if (jdMethod_goto(str16))
      {
        paramTransferInfo.setStatusCode(16010);
        str17 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "ToAmountCurrency" }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str17);
        return false;
      }
      if (jdMethod_goto(str13)) {
        paramTransferInfo.setAmount("0.00");
      }
      if (jdMethod_goto(str14))
      {
        paramTransferInfo.setStatusCode(16010);
        str17 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "AmountCurrency" }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str17);
        return false;
      }
      if (str16.equals(str14))
      {
        paramTransferInfo.setStatusCode(21737);
        str17 = BPWLocaleUtil.getMessage(21737, new String[] { str16 }, "TRANSFER_MESSAGE");
        paramTransferInfo.setStatusMsg(str17);
        return false;
      }
    }
    String str17 = paramTransferInfo.getSubmittedBy();
    if ((str17 == null) || (str17.trim().length() == 0))
    {
      paramTransferInfo.setStatusCode(16010);
      String str18 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferInfo", "SubmittedBy" }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusMsg(str18);
      return false;
    }
    return true;
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, RecTransferInfo paramRecTransferInfo, BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.createSchedule: ";
    FFSDebug.log(str1, "Starts", 6);
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramRecTransferInfo.getFIId();
    localScheduleInfo.Status = "Active";
    try
    {
      localScheduleInfo.Frequency = FFSUtil.getFreqInt(paramRecTransferInfo.getFrequency());
      localScheduleInfo.InstanceCount = paramRecTransferInfo.getPmtsCount();
      localScheduleInfo.CurInstanceNum = 1;
      localScheduleInfo.LogID = paramRecTransferInfo.getLogId();
      localScheduleInfo.NextInstanceDate = Transfer.getNextInstanceDateInBPWWarehouse(paramFFSConnectionHolder, paramRecTransferInfo.getStartDate(), paramBPWFIInfo, paramRecTransferInfo.getTransferDest());
      localScheduleInfo.StartDate = localScheduleInfo.NextInstanceDate;
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "RECETFTRN", paramRecTransferInfo.getSrvrTId(), localScheduleInfo);
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** " + str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    FFSDebug.log(str1, "Done", 6);
  }
  
  private void jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.createIntraSchedule: ";
    FFSDebug.log(str1, "Starts", 6);
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramTransferInfo.getFIId();
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = 10;
    localScheduleInfo.StartDate = BPWUtil.getDateDBFormat(paramTransferInfo.getDateDue());
    localScheduleInfo.LogID = paramTransferInfo.getLogId();
    localScheduleInfo.InstanceCount = 1;
    try
    {
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "INTRATRN", paramTransferInfo.getSrvrTId(), localScheduleInfo, "BookTransfer");
    }
    catch (Throwable localThrowable)
    {
      String str2 = "*** " + str1 + " failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str2, str3, 0);
      throw new FFSException(localThrowable, str2);
    }
    FFSDebug.log(str1, "Done", 6);
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, RecTransferInfo paramRecTransferInfo)
    throws FFSException
  {
    ScheduleInfo localScheduleInfo = new ScheduleInfo();
    localScheduleInfo.FIId = paramRecTransferInfo.getFIId();
    localScheduleInfo.Status = "Active";
    localScheduleInfo.Frequency = FFSUtil.getFreqInt(paramRecTransferInfo.getFrequency());
    localScheduleInfo.StartDate = BPWUtil.getDateDBFormat(paramRecTransferInfo.getStartDate());
    localScheduleInfo.InstanceCount = paramRecTransferInfo.getPmtsCount();
    localScheduleInfo.NextInstanceDate = localScheduleInfo.StartDate;
    localScheduleInfo.CurInstanceNum = 1;
    localScheduleInfo.LogID = paramRecTransferInfo.getLogId();
    if (paramRecTransferInfo.getPmtsCount() == 999) {
      localScheduleInfo.Perpetual = 1;
    }
    try
    {
      ScheduleInfo.createSchedule(paramFFSConnectionHolder, "RECINTRATRN", paramRecTransferInfo.getSrvrTId(), localScheduleInfo, "BookTransfer");
    }
    catch (Exception localException)
    {
      String str = "Exception in RecXferProcessor.scheduleRecXferAddRq: " + localException.getMessage();
      throw new BPWException(str);
    }
  }
  
  private void jdMethod_new(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws FFSException
  {
    ScheduleInfo.cancelSchedule(paramFFSConnectionHolder.conn, "RECETFTRN", paramString);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, RecTransferInfo paramRecTransferInfo, BPWFIInfo paramBPWFIInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.modifyRecSchedule";
    String str2 = "RECETFTRN";
    ScheduleInfo localScheduleInfo = ScheduleInfo.getScheduleInfo(paramRecTransferInfo.getFIId(), str2, paramRecTransferInfo.getSrvrTId(), paramFFSConnectionHolder);
    if ((localScheduleInfo == null) || (localScheduleInfo.Status.equals("Processing")))
    {
      String str3 = "*** " + str1 + " failed: Can't find schedule for: " + " InstructionType: " + str2 + " InstructionId: " + paramRecTransferInfo.getSrvrTId();
      FFSDebug.log(str3, 0);
      throw new FFSException(str3);
    }
    try
    {
      localScheduleInfo.Frequency = FFSUtil.getFreqInt(paramRecTransferInfo.getFrequency());
      localScheduleInfo.InstanceCount = paramRecTransferInfo.getPmtsCount();
      localScheduleInfo.NextInstanceDate = Transfer.getNextInstanceDateInBPWWarehouse(paramFFSConnectionHolder, paramRecTransferInfo.getStartDate(), paramBPWFIInfo, paramRecTransferInfo.getTransferDest());
      localScheduleInfo.StartDate = localScheduleInfo.NextInstanceDate;
      ScheduleInfo.modifySchedule(paramFFSConnectionHolder, localScheduleInfo.ScheduleID, localScheduleInfo);
    }
    catch (Throwable localThrowable)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str4, str5, 0);
      throw new FFSException(localThrowable, str4);
    }
  }
  
  private int jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, RecTransferInfo paramRecTransferInfo)
    throws BPWException
  {
    try
    {
      String str1 = paramRecTransferInfo.getFIId();
      str2 = paramRecTransferInfo.getSrvrTId();
      ScheduleInfo localScheduleInfo1 = ScheduleInfo.getScheduleInfo(str1, "RECINTRATRN", str2, paramFFSConnectionHolder);
      if (localScheduleInfo1 == null) {
        return 1;
      }
      if ((localScheduleInfo1.Status.equals("Processing")) && (localScheduleInfo1.StatusOption == 1)) {
        return 1;
      }
      if (!localScheduleInfo1.Status.equals("Active"))
      {
        localObject = RecXferInstruction.getStatus(paramFFSConnectionHolder, str2);
        if (((String)localObject).compareTo("CANCELEDON") == 0) {
          return 2;
        }
        return 3;
      }
      Object localObject = InstructionTypeStatus.get(str1, "RECINTRATRN", paramFFSConnectionHolder);
      if ((localObject != null) && (((InstructionTypeStatus)localObject).SchedulerStatus.equals("DispatcherStarted")))
      {
        int i = DBUtil.getCurrentStartDate();
        if (localScheduleInfo1.WorkInstanceDate < i) {
          throw new OFXException("Record locked for modification by scheduler, try again later", 2000);
        }
      }
      ScheduleInfo localScheduleInfo2 = new ScheduleInfo();
      localScheduleInfo2.FIId = paramRecTransferInfo.getFIId();
      localScheduleInfo2.InstructionID = str2;
      localScheduleInfo2.Status = "Active";
      localScheduleInfo2.Frequency = FFSUtil.getFreqInt(paramRecTransferInfo.getFrequency());
      localScheduleInfo2.InstanceCount = paramRecTransferInfo.getPmtsCount();
      localScheduleInfo2.CurInstanceNum = 1;
      if (paramRecTransferInfo.getDateDue() != null)
      {
        localScheduleInfo2.StartDate = BPWUtil.getDateDBFormat(paramRecTransferInfo.getStartDate());
        ScheduleInfo.computeNextInstanceDate(localScheduleInfo2);
      }
      else
      {
        localScheduleInfo2.StartDate = localScheduleInfo1.StartDate;
        localScheduleInfo2.NextInstanceDate = localScheduleInfo1.NextInstanceDate;
      }
      if (paramRecTransferInfo.getPmtsCount() == 999) {
        localScheduleInfo2.Perpetual = 1;
      }
      ScheduleInfo.modifySchedule(paramFFSConnectionHolder, "RECINTRATRN", str2, localScheduleInfo2, "BookTransfer");
    }
    catch (Exception localException)
    {
      String str2 = "Exception in RecXferProcessor.modifyRecIntraSchedule: " + localException.getMessage();
      throw new BPWException(str2);
    }
    return 0;
  }
  
  public void processApprovalResult(String paramString1, String paramString2, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "TransferProcessor.processApprovalResult: ";
    FFSDebug.log(str1, "start", 6);
    FFSConnectionHolder localFFSConnectionHolder = null;
    int i = 0;
    String str2 = null;
    FFSDebug.log(str1, "srvrTid:", paramString1, " decision:", paramString2, 6);
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
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      TransferInfo localTransferInfo = new TransferInfo();
      localTransferInfo.setSrvrTId(paramString1);
      localTransferInfo = Transfer.getTransferInfo(localFFSConnectionHolder, localTransferInfo, false);
      if (localTransferInfo.getStatusCode() != 0)
      {
        i = 17502;
        str2 = "Transaction id is invalid";
        throw new FFSException(i, str1 + str2);
      }
      FFSDebug.log(str1, "transferInfo:" + localTransferInfo, 6);
      if (!localTransferInfo.getPrcStatus().equalsIgnoreCase("APPROVAL_PENDING"))
      {
        i = 17503;
        str2 = "Transaction not waiting for Approval";
        throw new FFSException(i, str1 + str2);
      }
      Object localObject1;
      String str6;
      if (paramString2.compareTo("Approved") == 0)
      {
        String str3 = localTransferInfo.getDateToPost();
        localObject1 = String.valueOf(DBUtil.getCurrentStartDate() / 100);
        FFSDebug.log(str1, " processDate:", str3, " , currentDate:", (String)localObject1, 6);
        localTransferInfo.setPrcStatus("WILLPROCESSON");
        Transfer.updateStatus(localFFSConnectionHolder, localTransferInfo, false);
      }
      else if (paramString2.compareTo("Rejected") == 0)
      {
        int j = LimitCheckApprovalProcessor.processExternalTransferReject(localFFSConnectionHolder, localTransferInfo, paramHashMap);
        if (LimitCheckApprovalProcessor.mapToStatus(j).equals("LIMIT_REVERT_SUCCEEDED"))
        {
          FFSDebug.log(str1, "LIMIT REVERT SUCCEEDED", 6);
          localTransferInfo.setPrcStatus("APPROVAL_REJECTED");
          Transfer.updateStatus(localFFSConnectionHolder, localTransferInfo, false);
        }
        else
        {
          FFSDebug.log(str1, "LIMIT REVERT FAILED", 6);
          try
          {
            if (this.b6 >= 1)
            {
              localObject1 = BPWLocaleUtil.getLocalizableMessage(1047, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
              a(localTransferInfo, "ProcessApprvlRslt", (ILocalizable)localObject1, false);
              localFFSConnectionHolder.conn.commit();
            }
          }
          catch (Exception localException2)
          {
            str6 = "*** " + str1 + " failed to write audit log.";
            String str7 = null;
            localFFSConnectionHolder.conn.rollback();
            str7 = FFSDebug.stackTrace(localException2);
            FFSDebug.log(str6, str7, 0);
          }
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
      try
      {
        if (this.b6 >= 4)
        {
          a(localFFSConnectionHolder, localTransferInfo, "ProcessApprvlRslt", false);
          localFFSConnectionHolder.conn.commit();
        }
      }
      catch (Exception localException1)
      {
        str5 = "*** " + str1 + " failed to write audit log.";
        str6 = null;
        localFFSConnectionHolder.conn.rollback();
        str6 = FFSDebug.stackTrace(localException1);
        FFSDebug.log(str5, str6, 0);
      }
      if (localTransferInfo.getStatusCode() != 0) {
        localFFSConnectionHolder.conn.rollback();
      } else {
        localFFSConnectionHolder.conn.commit();
      }
      FFSDebug.log(str1, "Done", 6);
    }
    catch (FFSException localFFSException)
    {
      String str4 = "*** " + str1 + " failed: ";
      String str5 = null;
      localFFSConnectionHolder.conn.rollback();
      str5 = FFSDebug.stackTrace(localFFSException);
      FFSDebug.log(str4, str5, 0);
      throw localFFSException;
    }
    finally
    {
      if (localFFSConnectionHolder != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.validatePrenoteStatus :";
    FFSDebug.log(str1, " start", 6);
    try
    {
      ExtTransferAcctInfo localExtTransferAcctInfo = paramTransferInfo.getAccountFromInfo();
      String str2;
      if (localExtTransferAcctInfo == null)
      {
        str2 = paramTransferInfo.getAccountFromId();
        if (!jdMethod_goto(str2))
        {
          localExtTransferAcctInfo = new ExtTransferAcctInfo();
          localExtTransferAcctInfo.setAcctId(str2);
          if (paramBoolean) {
            localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo);
          } else {
            localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo, false, true);
          }
        }
      }
      if (localExtTransferAcctInfo != null)
      {
        if (a(localExtTransferAcctInfo, paramTransferInfo)) {}
      }
      else {
        FFSDebug.log(str1 + "FromAccount: " + "Unable to validate prenote status.", 3);
      }
      localExtTransferAcctInfo = paramTransferInfo.getAccountToInfo();
      if (localExtTransferAcctInfo == null)
      {
        str2 = paramTransferInfo.getAccountToId();
        if (!jdMethod_goto(str2))
        {
          localExtTransferAcctInfo = new ExtTransferAcctInfo();
          localExtTransferAcctInfo.setAcctId(str2);
          if (paramBoolean) {
            localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo);
          } else {
            localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo, false, true);
          }
        }
      }
      if (localExtTransferAcctInfo != null)
      {
        if (a(localExtTransferAcctInfo, paramTransferInfo)) {}
      }
      else {
        FFSDebug.log(str1 + "ToAccount: " + "Unable to validate prenote status.", 3);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(str1, "failed, " + FFSDebug.stackTrace(localException), 0);
      throw new FFSException(localException, localException.getMessage());
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log(str1, "Done", 6);
  }
  
  private static boolean a(ExtTransferAcctInfo paramExtTransferAcctInfo, TransferInfo paramTransferInfo)
  {
    String str1 = "TransferProcessor.checkAcctPrenoteStatus: ";
    String str2 = paramExtTransferAcctInfo.getPrenoteStatus();
    String str3 = paramExtTransferAcctInfo.getPrenote();
    FFSDebug.log(str1, " prenote = ", str3, 6);
    FFSDebug.log(str1, " prenoteStatus = ", str2, 6);
    if ((str3 != null) && (str3.trim().equalsIgnoreCase("Y")))
    {
      if (str2 == null)
      {
        paramTransferInfo.setStatusCode(21140);
        paramTransferInfo.setStatusMsg("The Prenote for this external transfer account has not been processed yet, AcctId =" + paramExtTransferAcctInfo.getAcctId());
        return false;
      }
      if (!str2.equalsIgnoreCase("Success"))
      {
        FFSDebug.log(str1, " prenoteStatus not success", 6);
        String str4 = null;
        if ("ITOE".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
          str4 = paramTransferInfo.getAccountToId();
        } else if ("ETOI".equalsIgnoreCase(paramTransferInfo.getTransferDest())) {
          str4 = paramTransferInfo.getAccountFromId();
        }
        if (str2.equalsIgnoreCase("Failed"))
        {
          paramTransferInfo.setStatusCode(21150);
          paramTransferInfo.setStatusMsg("Prenote process has failed, AcctId =" + str4);
          return false;
        }
        if (str2.equalsIgnoreCase("Pending"))
        {
          paramTransferInfo.setStatusCode(21170);
          paramTransferInfo.setStatusMsg("Prenote process is pending, AcctId =" + str4);
          return false;
        }
      }
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    return true;
  }
  
  public int getValidTransferDueDate(TransferInfo paramTransferInfo)
    throws FFSException
  {
    return getValidTransferDueDate(paramTransferInfo, new HashMap());
  }
  
  public int getValidTransferDueDate(TransferInfo paramTransferInfo, HashMap paramHashMap)
    throws FFSException
  {
    String str1 = "TransferProcessor.getValidTransferDueDate: ";
    FFSDebug.log(str1 + "start. DueDate: " + paramTransferInfo.getDateDue(), 6);
    int i = Integer.parseInt(paramTransferInfo.getDateDue());
    FFSConnectionHolder localFFSConnectionHolder = null;
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = str1 + "Can not get DB Connection.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      Object localObject1 = BPWFI.getBPWFIInfo(localFFSConnectionHolder, paramTransferInfo.getFIId());
      if (((BPWFIInfo)localObject1).getStatusCode() != 0)
      {
        paramTransferInfo.setStatusCode(((BPWFIInfo)localObject1).getStatusCode());
        paramTransferInfo.setStatusMsg(((BPWFIInfo)localObject1).getStatusMsg());
        FFSDebug.log(str1 + ((BPWFIInfo)localObject1).getStatusMsg(), 0);
        throw new FFSException(paramTransferInfo.getStatusCode(), paramTransferInfo.getStatusMsg());
      }
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      paramHashMap.put("BPW_FIINFO", localObject1);
      localObject2 = null;
      if (this.b7 == true)
      {
        localObject2 = Customer.getCustomerInfo(paramTransferInfo.getCustomerId(), localFFSConnectionHolder, paramTransferInfo);
        if ((localObject2 == null) || (((CustomerInfo)localObject2).getStatusCode() != 0))
        {
          str2 = BPWLocaleUtil.getMessage(19130, new String[] { paramTransferInfo.getCustomerId() }, "TRANSFER_MESSAGE");
          paramTransferInfo.setStatusCode(19130);
          paramTransferInfo.setStatusMsg(str2);
          FFSDebug.log(str1 + str2, 0);
          throw new FFSException(paramTransferInfo.getStatusCode(), paramTransferInfo.getStatusMsg());
        }
      }
      a(localFFSConnectionHolder, paramTransferInfo, (BPWFIInfo)localObject1);
      i = Transfer.getValidTransferDueDate(localFFSConnectionHolder, paramTransferInfo);
      localFFSConnectionHolder.conn.commit();
    }
    catch (FFSException localFFSException)
    {
      localFFSConnectionHolder.conn.rollback();
      FFSDebug.log(str1 + localFFSException.toString(), 0);
      throw localFFSException;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject2 = str1 + "failed: ";
      String str2 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2 + str2, 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
    FFSDebug.log(str1 + "done. Valid due date: " + i, 6);
    return i;
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean)
    throws Exception
  {
    String str1 = "TransferProcessor:processTransferFundsImmediately: ";
    if ((paramTransferInfo.getFundsProcessing() == 2) && (paramTransferInfo.getProcessType() == 2) && (paramTransferInfo.getPrcStatus().equals("WILLPROCESSON")))
    {
      FFSDebug.log(str1 + "Starts", 6);
      String str2 = paramTransferInfo.getProcessDate();
      if (str2.length() > 8) {
        str2 = str2.substring(0, 8);
      }
      int i = Integer.parseInt(str2);
      int j = Integer.parseInt(paramTransferInfo.getDateDue());
      int k = Integer.parseInt(FFSUtil.getDateString("yyyyMMdd"));
      FFSDebug.log(str1 + "processDate= " + i, 6);
      FFSDebug.log(str1 + "dateDue= " + j, 6);
      FFSDebug.log(str1 + "today= " + k, 6);
      if ((i == k) || (j == k))
      {
        if (paramBoolean == true)
        {
          paramTransferInfo.setPrcStatus("IMMED_INPROCESS");
          Transfer.updateStatusAction(paramFFSConnectionHolder, paramTransferInfo, false);
          if (this.b6 >= 4) {
            a(paramFFSConnectionHolder, paramTransferInfo, "Add", false);
          }
          paramFFSConnectionHolder.conn.commit();
        }
        paramTransferInfo.setPrcStatus("INFUNDSPROCESSING");
        Transfer.updateStatusAction(paramFFSConnectionHolder, paramTransferInfo, false);
        try
        {
          TransferInfo[] arrayOfTransferInfo = { paramTransferInfo };
          a(paramFFSConnectionHolder, arrayOfTransferInfo);
          FFSDebug.log(str1 + "prcStatus= " + paramTransferInfo.getPrcStatus(), 6);
        }
        catch (Throwable localThrowable)
        {
          int m = LimitCheckApprovalProcessor.processExternalTransferReject(paramFFSConnectionHolder, paramTransferInfo, new HashMap());
          String str4 = LimitCheckApprovalProcessor.mapToStatus(m);
          FFSDebug.log(str1, "retStatus LimitRevert:" + str4, 6);
          paramTransferInfo.setPrcStatus("FUNDSFAILED");
          paramTransferInfo.setAction("Failed");
          Transfer.updateStatusAction(paramFFSConnectionHolder, paramTransferInfo, false);
          if (this.b6 >= 1)
          {
            localObject2 = BPWLocaleUtil.getLocalizableMessage(51030, null, "TRANSFER_MESSAGE");
            a(paramTransferInfo, "ProcessFunds", (ILocalizable)localObject2, false);
          }
          paramFFSConnectionHolder.conn.commit();
          Object localObject2 = BPWLocaleUtil.getMessage(21340, new String[] { paramTransferInfo.toString(), localThrowable.toString() }, "TRANSFER_MESSAGE");
          FFSDebug.log(str1, (String)localObject2, 0);
          paramTransferInfo.setStatusCode(21340);
          paramTransferInfo.setStatusMsg((String)localObject2);
          return;
        }
        String str3 = Transfer.getStatus(paramFFSConnectionHolder, paramTransferInfo.getSrvrTId(), false);
        Object localObject1;
        if (str3.equalsIgnoreCase("INFUNDSPROCESSING"))
        {
          paramTransferInfo.setPrcStatus("FUNDSFAILED");
          paramTransferInfo.setAction("Failed");
          Transfer.updateStatusAction(paramFFSConnectionHolder, paramTransferInfo, false);
          if (this.b6 >= 1)
          {
            localObject1 = BPWLocaleUtil.getLocalizableMessage(51030, null, "TRANSFER_MESSAGE");
            a(paramTransferInfo, "ProcessFunds", (ILocalizable)localObject1, false);
          }
          localObject1 = BPWLocaleUtil.getMessage(21500, new String[] { str3 }, "TRANSFER_MESSAGE");
          paramTransferInfo.setStatusCode(21500);
          paramTransferInfo.setStatusMsg((String)localObject1);
          FFSDebug.log(str1, (String)localObject1, 0);
          paramFFSConnectionHolder.conn.commit();
          return;
        }
        if ((str3.equalsIgnoreCase("FUNDSFAILEDON")) || (str3.equalsIgnoreCase("FUNDSFAILED")))
        {
          paramTransferInfo.setPrcStatus("FUNDSFAILED");
          paramTransferInfo.setAction("Failed");
          Transfer.updateStatusAction(paramFFSConnectionHolder, paramTransferInfo, false);
          if (this.b6 >= 1)
          {
            localObject1 = BPWLocaleUtil.getLocalizableMessage(51030, null, "TRANSFER_MESSAGE");
            a(paramTransferInfo, "ProcessFunds", (ILocalizable)localObject1, false);
          }
          localObject1 = BPWLocaleUtil.getMessage(21340, new String[] { paramTransferInfo.toString(), "unknown error" }, "TRANSFER_MESSAGE");
          paramTransferInfo.setStatusCode(21340);
          paramTransferInfo.setStatusMsg((String)localObject1);
          FFSDebug.log(str1, (String)localObject1, 0);
          paramFFSConnectionHolder.conn.commit();
          return;
        }
        if ((str3.equalsIgnoreCase("FUNDSPENDING")) || (str3.equalsIgnoreCase("FUNDSPROCESSED")) || (str3.equalsIgnoreCase("NOFUNDS")) || (str3.equalsIgnoreCase("WILLPROCESSON")))
        {
          paramTransferInfo.setStatusCode(0);
          paramTransferInfo.setStatusMsg("Success");
          paramFFSConnectionHolder.conn.commit();
          paramTransferInfo.setPrcStatus(str3);
        }
        else
        {
          localObject1 = str1 + "failed process funds in backendhandler";
          FFSDebug.log(str1, (String)localObject1, 0);
          throw new FFSException((String)localObject1);
        }
      }
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log(str1 + "Done", 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo[] paramArrayOfTransferInfo)
    throws Exception
  {
    String str1 = "TransferProcessor.doImmediateFundsProcessing: ";
    FFSDebug.log(str1 + "starts", 6);
    TransferFundsProcessing localTransferFundsProcessing = new TransferFundsProcessing();
    Hashtable localHashtable = null;
    String str2 = DBConnCache.save(paramFFSConnectionHolder);
    for (int i = 0; i < paramArrayOfTransferInfo.length; i++)
    {
      paramArrayOfTransferInfo[i].setDbTransKey(str2);
      paramArrayOfTransferInfo[i].setEventId("1");
      paramArrayOfTransferInfo[i].setPossibleDuplicate(false);
      jdMethod_goto(paramArrayOfTransferInfo[i]);
    }
    try
    {
      localTransferFundsProcessing.processImmediateTransferFunds(paramArrayOfTransferInfo, localHashtable);
    }
    catch (Throwable localThrowable)
    {
      String str3 = str1 + "Failed." + " Error: " + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log(str3, 0);
      throw new FFSException(localThrowable, str3);
    }
    finally
    {
      DBConnCache.unbind(str2);
    }
    FFSDebug.log(str1, "done.", 6);
  }
  
  private void jdMethod_goto(TransferInfo paramTransferInfo)
  {
    Hashtable localHashtable = paramTransferInfo.getExtInfo();
    if (localHashtable != null)
    {
      Iterator localIterator = localHashtable.values().iterator();
      while (localIterator.hasNext())
      {
        Object localObject = localIterator.next();
        if ((localObject instanceof ValueInfo)) {
          ((ValueInfo)localObject).setAction(null);
        }
      }
    }
  }
  
  private void jdMethod_for(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws Exception
  {
    String str1 = "TransferProcessor.processRevertFundsImmediately: ";
    FFSDebug.log(str1 + "Starts", 6);
    TransferFundsProcessing localTransferFundsProcessing = new TransferFundsProcessing();
    Hashtable localHashtable = null;
    paramTransferInfo.setPrcStatus("INFUNDSREVERT");
    Transfer.updateStatusAction(paramFFSConnectionHolder, paramTransferInfo, false);
    String str2 = DBConnCache.save(paramFFSConnectionHolder);
    Object localObject1;
    try
    {
      paramTransferInfo.setDbTransKey(str2);
      paramTransferInfo.setEventId("1");
      paramTransferInfo.setPossibleDuplicate(false);
      TransferInfo[] arrayOfTransferInfo = { paramTransferInfo };
      localTransferFundsProcessing.revertImmediateTransferFunds(arrayOfTransferInfo, localHashtable);
      FFSDebug.log(str1, "prcStatus = " + paramTransferInfo.getPrcStatus(), 6);
      if (this.b6 >= 4) {
        a(paramFFSConnectionHolder, paramTransferInfo, "RevertFunds", false);
      }
    }
    catch (Throwable localThrowable)
    {
      paramTransferInfo.setPrcStatus("FUNDSREVERTFAILED");
      paramTransferInfo.setAction("Failed");
      Transfer.updateStatusAction(paramFFSConnectionHolder, paramTransferInfo, false);
      if (this.b6 >= 1)
      {
        localObject1 = BPWLocaleUtil.getLocalizableMessage(51020, null, "TRANSFER_MESSAGE");
        a(paramTransferInfo, "RevertFunds", (ILocalizable)localObject1, false);
      }
      localObject1 = BPWLocaleUtil.getMessage(21390, new String[] { localThrowable.toString() }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusCode(21390);
      paramTransferInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, (String)localObject1, 0);
      paramFFSConnectionHolder.conn.commit();
      return;
    }
    finally
    {
      DBConnCache.unbind(str2);
    }
    String str3 = Transfer.getStatus(paramFFSConnectionHolder, paramTransferInfo.getSrvrTId(), false);
    if ((str3.equalsIgnoreCase("FUNDSREVERTFAILED")) || (str3.equalsIgnoreCase("FUNDSREVERTPENDING")) || (str3.equalsIgnoreCase("INFUNDSREVERT")))
    {
      paramTransferInfo.setPrcStatus("FUNDSREVERTFAILED");
      paramTransferInfo.setAction("Failed");
      Transfer.updateStatusAction(paramFFSConnectionHolder, paramTransferInfo, false);
      if (this.b6 >= 1)
      {
        localObject1 = BPWLocaleUtil.getLocalizableMessage(51020, null, "TRANSFER_MESSAGE");
        a(paramTransferInfo, "RevertFunds", (ILocalizable)localObject1, false);
      }
      localObject1 = null;
      int i = 0;
      if (str3.equalsIgnoreCase("FUNDSREVERTFAILED"))
      {
        localObject1 = BPWLocaleUtil.getMessage(21390, new String[] { "unknown reasons" }, "TRANSFER_MESSAGE");
        i = 21390;
      }
      else
      {
        localObject1 = BPWLocaleUtil.getMessage(21490, new String[] { str3 }, "TRANSFER_MESSAGE");
        i = 21490;
      }
      paramTransferInfo.setStatusCode(i);
      paramTransferInfo.setStatusMsg((String)localObject1);
      FFSDebug.log(str1, (String)localObject1, 0);
      paramFFSConnectionHolder.conn.commit();
      return;
    }
    if (str3.equalsIgnoreCase("FUNDSREVERTED"))
    {
      paramTransferInfo.setStatusCode(0);
      paramTransferInfo.setStatusMsg("Success");
      paramFFSConnectionHolder.conn.commit();
      paramTransferInfo.setPrcStatus("WILLPROCESSON");
    }
    else
    {
      localObject1 = str1 + "failed revert funds in backend handler";
      throw new FFSException((String)localObject1);
    }
    FFSDebug.log(str1, "Done.", 6);
  }
  
  private boolean jdMethod_goto(String paramString)
  {
    return StringUtils.trimToNull(paramString) == null;
  }
  
  private boolean jdMethod_byte(TransferInfo paramTransferInfo)
  {
    String str = paramTransferInfo.getTransferType();
    if (str == null) {
      return false;
    }
    return (str.trim().equals("TEMPLATE")) || (str.trim().equals("RECTEMPLATE"));
  }
  
  private boolean jdMethod_char(TransferInfo paramTransferInfo)
  {
    String str = paramTransferInfo.getTransferDest();
    return (str != null) && ((str.equalsIgnoreCase("ITOI")) || (str.equalsIgnoreCase("INTERNAL")));
  }
  
  private void jdMethod_case(TransferInfo paramTransferInfo)
  {
    String str = paramTransferInfo.getTransferDest();
    int i = paramTransferInfo.getProcessType();
    int j = paramTransferInfo.getFundsProcessing();
    if (str.equalsIgnoreCase("ITOE"))
    {
      if (i == 0) {
        if (this.ch == true) {
          i = 2;
        } else {
          i = 1;
        }
      }
      if (j == 0) {
        if (i == 2)
        {
          if (this.b8 == true) {
            j = 2;
          } else {
            j = 1;
          }
        }
        else if (i == 1)
        {
          if (this.b9 == true) {
            j = 2;
          } else {
            j = 1;
          }
        }
        else {
          j = 1;
        }
      }
    }
    else if (str.equalsIgnoreCase("ETOI"))
    {
      if (i == 0) {
        if (this.cg == true) {
          i = 2;
        } else {
          i = 1;
        }
      }
      if (j == 0) {
        if (i == 2)
        {
          if (this.ca == true) {
            j = 2;
          } else {
            j = 1;
          }
        }
        else if (i == 1)
        {
          if (this.cf == true) {
            j = 2;
          } else {
            j = 1;
          }
        }
        else {
          j = 1;
        }
      }
    }
    paramTransferInfo.setFundsProcessing(j);
    paramTransferInfo.setProcessType(i);
    FFSDebug.log("TransferProcessor: transferInfo.FundsProcessing =" + paramTransferInfo.getFundsProcessing(), 6);
    FFSDebug.log("TransferProcessor: transferInfo.ProcessType =" + paramTransferInfo.getProcessType(), 6);
  }
  
  private String jdMethod_do(TransferInfo paramTransferInfo)
  {
    String str1 = "TransferProcessor.getTransferDestination";
    String str2 = paramTransferInfo.getTransferDest();
    if (jdMethod_goto(str2) == true)
    {
      FFSConnectionHolder localFFSConnectionHolder = null;
      try
      {
        localFFSConnectionHolder = new FFSConnectionHolder();
        localFFSConnectionHolder.conn = DBUtil.getConnection();
        if (localFFSConnectionHolder.conn == null)
        {
          localObject1 = str1 + "Can not get DB Connection.";
          FFSDebug.log((String)localObject1, 0);
          str3 = str2;
          return str3;
        }
        Object localObject1 = new StringBuffer();
        str3 = paramTransferInfo.getBankFromRtn();
        ExtTransferAcctInfo localExtTransferAcctInfo1 = paramTransferInfo.getAccountFromInfo();
        String str4 = paramTransferInfo.getAccountFromId();
        if (jdMethod_goto(str3) == true)
        {
          if (localExtTransferAcctInfo1 == null) {
            if (!jdMethod_goto(str4))
            {
              localExtTransferAcctInfo1 = new ExtTransferAcctInfo();
              localExtTransferAcctInfo1.setAcctId(str4);
              localExtTransferAcctInfo1 = ExternalTransferAccount.getExternalTransferAccount(localFFSConnectionHolder, localExtTransferAcctInfo1, false, true);
              if (localExtTransferAcctInfo1.getStatusCode() == 16020)
              {
                FFSDebug.log("TransferProcessor: Unable to determine transferInfo.TransferDest:", " FromAccount by ID not found.", 0);
                str5 = null;
                return str5;
              }
              if (jdMethod_try(localFFSConnectionHolder, localExtTransferAcctInfo1.getAcctBankRtn()))
              {
                FFSDebug.log("TransferProcessor: Unable to determine transferInfo.TransferDest:", " FromAccountID is internal and invalid.", 0);
                str5 = null;
                return str5;
              }
            }
            else
            {
              FFSDebug.log("TransferProcessor: Unable to determine transferInfo.TransferDest:", " FromAccount information missing.", 0);
              str5 = null;
              return str5;
            }
          }
          str3 = localExtTransferAcctInfo1.getAcctBankRtn();
        }
        if (jdMethod_try(localFFSConnectionHolder, str3) == true) {
          ((StringBuffer)localObject1).append("I");
        } else {
          ((StringBuffer)localObject1).append("E");
        }
        ((StringBuffer)localObject1).append("TO");
        String str5 = paramTransferInfo.getBankToRtn();
        ExtTransferAcctInfo localExtTransferAcctInfo2 = paramTransferInfo.getAccountToInfo();
        String str6 = paramTransferInfo.getAccountToId();
        if (jdMethod_goto(str5) == true)
        {
          if (localExtTransferAcctInfo2 == null)
          {
            String str7;
            if (!jdMethod_goto(str6))
            {
              localExtTransferAcctInfo2 = new ExtTransferAcctInfo();
              localExtTransferAcctInfo2.setAcctId(str6);
              localExtTransferAcctInfo2 = ExternalTransferAccount.getExternalTransferAccount(localFFSConnectionHolder, localExtTransferAcctInfo2, false, true);
              if (localExtTransferAcctInfo2.getStatusCode() == 16020)
              {
                FFSDebug.log("TransferProcessor: Unable to determine transferInfo.TransferDest:", " ToAccount by ID not found.", 0);
                str7 = null;
                return str7;
              }
            }
            else
            {
              FFSDebug.log("TransferProcessor: Unable to determine transferInfo.TransferDest:", " ToAccount information missing.", 0);
              str7 = null;
              return str7;
            }
          }
          str5 = localExtTransferAcctInfo2.getAcctBankRtn();
        }
        if (jdMethod_try(localFFSConnectionHolder, str5) == true) {
          ((StringBuffer)localObject1).append("I");
        } else {
          ((StringBuffer)localObject1).append("E");
        }
        str2 = ((StringBuffer)localObject1).toString();
        paramTransferInfo.setTransferDest(str2);
        FFSDebug.log(str1 + "TransferDest = [", str2 + "]", 0);
        localFFSConnectionHolder.conn.commit();
      }
      catch (Throwable localThrowable)
      {
        localFFSConnectionHolder.conn.rollback();
        String str3 = str1 + "failed: " + FFSDebug.stackTrace(localThrowable);
        FFSDebug.log(str3, 0);
      }
      finally
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
    else
    {
      str2 = str2.trim();
    }
    return str2;
  }
  
  private boolean jdMethod_try(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    BPWFIInfo localBPWFIInfo = BPWFI.getBPWFIInfoByRTN(paramFFSConnectionHolder, paramString);
    return localBPWFIInfo.getStatusCode() == 0;
  }
  
  private void jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws Exception
  {
    String str1 = "TransferProcessor.handleAddTransferAccount: ";
    FFSDebug.log(str1, "Start", 6);
    String str2 = paramTransferInfo.getTransferDest();
    boolean bool1 = false;
    boolean bool2 = false;
    String str3 = paramTransferInfo.getTransferType();
    int i = 0;
    if ((str3 != null) && ((str3.trim().equalsIgnoreCase("TEMPLATE")) || (str3.trim().equalsIgnoreCase("RECTEMPLATE")))) {
      i = 1;
    }
    if (str2.equalsIgnoreCase("ITOE"))
    {
      bool1 = true;
      bool2 = true;
    }
    else if ((str2.equalsIgnoreCase("ITOI")) && (i != 0))
    {
      bool1 = true;
      bool2 = false;
    }
    else if (!str2.equalsIgnoreCase("ETOI"))
    {
      str4 = BPWLocaleUtil.getMessage(21262, new String[] { str2 }, "TRANSFER_MESSAGE");
      paramTransferInfo.setStatusCode(21262);
      paramTransferInfo.setStatusMsg(str4);
      FFSDebug.log(str1 + str4, 0);
      return;
    }
    String str4 = paramTransferInfo.getAccountFromId();
    ExtTransferAcctInfo localExtTransferAcctInfo1 = paramTransferInfo.getAccountFromInfo();
    String str5;
    if (str4 != null)
    {
      if (bool1 == true)
      {
        str5 = BPWLocaleUtil.getMessage(21670, new String[] { str2, "FromAccount" }, "TRANSFER_MESSAGE");
        FFSDebug.log(str1, str5, 0);
        paramTransferInfo.setStatusCode(21670);
        paramTransferInfo.setStatusMsg(str5);
        return;
      }
      localExtTransferAcctInfo1 = new ExtTransferAcctInfo();
      localExtTransferAcctInfo1.setAcctId(str4);
      localExtTransferAcctInfo1 = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo1);
      if (localExtTransferAcctInfo1.getStatusCode() == 16020)
      {
        str5 = BPWLocaleUtil.getMessage(21080, null, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "FromAccount: " + str5, 0);
        paramTransferInfo.setStatusCode(21080);
        paramTransferInfo.setStatusMsg(str5);
        return;
      }
      if (!localExtTransferAcctInfo1.getAcctDest().equalsIgnoreCase("EXTERNAL"))
      {
        str5 = BPWLocaleUtil.getMessage(21580, new String[] { localExtTransferAcctInfo1.getAcctDest(), str2 }, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "FromAccount: " + str5, 0);
        paramTransferInfo.setStatusCode(21580);
        paramTransferInfo.setStatusMsg(str5);
        return;
      }
      paramTransferInfo.setAccountFromInfo(localExtTransferAcctInfo1);
      paramTransferInfo.setBankFromRtn(null);
      paramTransferInfo.setBankFromRtnType(null);
      paramTransferInfo.setAccountFromNum(null);
      paramTransferInfo.setAccountFromType(null);
    }
    else if (localExtTransferAcctInfo1 != null)
    {
      a(paramFFSConnectionHolder, paramTransferInfo, bool1, bool2, true, str1);
      if (paramTransferInfo.getStatusCode() == 0) {}
    }
    else
    {
      if (!bool1)
      {
        str5 = BPWLocaleUtil.getMessage(21180, null, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "FromAccount: " + str5, 0);
        paramTransferInfo.setStatusCode(21180);
        paramTransferInfo.setStatusMsg(str5);
        return;
      }
      if (jdMethod_goto(paramTransferInfo.getBankFromRtnType()) == true) {
        if (CommBankIdentifier.getBankIdentifierFlag()) {
          paramTransferInfo.setBankFromRtnType("OTHER");
        } else {
          paramTransferInfo.setBankFromRtnType("FEDABA");
        }
      }
      if (paramTransferInfo.getBankFromRtnType().equals("FEDABA")) {
        try
        {
          boolean bool3 = BPWUtil.validateABA(paramTransferInfo.getBankFromRtn());
          if (!bool3)
          {
            FFSDebug.log(str1 + "FromAccount: " + "Invalid BankRTN", 0);
            paramTransferInfo.setStatusCode(23670);
            paramTransferInfo.setStatusMsg("Invalid BankRTN");
            return;
          }
        }
        catch (FFSException localFFSException)
        {
          paramTransferInfo.setStatusCode(23670);
          paramTransferInfo.setStatusMsg("Invalid BankRTN");
          FFSDebug.log(str1 + "FromAccount: " + localFFSException.toString(), 0);
          return;
        }
      }
    }
    String str6 = paramTransferInfo.getAccountToId();
    String str7 = paramTransferInfo.getAccountToNum();
    String str8 = paramTransferInfo.getAccountToType();
    String str9 = paramTransferInfo.getBankToRtn();
    ExtTransferAcctInfo localExtTransferAcctInfo2 = paramTransferInfo.getAccountToInfo();
    String str10;
    if (str6 != null)
    {
      if (!bool2)
      {
        str10 = BPWLocaleUtil.getMessage(21670, new String[] { str2, "ToAccount" }, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + str10, 0);
        paramTransferInfo.setStatusCode(21670);
        paramTransferInfo.setStatusMsg(str10);
        return;
      }
      localExtTransferAcctInfo2 = new ExtTransferAcctInfo();
      localExtTransferAcctInfo2.setAcctId(str6);
      localExtTransferAcctInfo2 = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo2);
      if (localExtTransferAcctInfo2.getStatusCode() == 16020)
      {
        str10 = BPWLocaleUtil.getMessage(21080, null, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "ToAccount: " + str10, 0);
        paramTransferInfo.setStatusCode(21080);
        paramTransferInfo.setStatusMsg(str10);
        return;
      }
      if (!localExtTransferAcctInfo2.getAcctDest().equalsIgnoreCase("EXTERNAL"))
      {
        str10 = BPWLocaleUtil.getMessage(21580, new String[] { localExtTransferAcctInfo2.getAcctDest(), str2 }, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "ToAccount: " + str10, 0);
        paramTransferInfo.setStatusCode(21580);
        paramTransferInfo.setStatusMsg(str10);
        return;
      }
      paramTransferInfo.setAccountToInfo(localExtTransferAcctInfo2);
    }
    else if (localExtTransferAcctInfo2 != null)
    {
      a(paramFFSConnectionHolder, paramTransferInfo, bool1, bool2, false, str1);
      if (paramTransferInfo.getStatusCode() == 0) {}
    }
    else
    {
      if (bool2 == true)
      {
        str10 = BPWLocaleUtil.getMessage(21180, null, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "ToAccount: " + str10, 0);
        paramTransferInfo.setStatusCode(21180);
        paramTransferInfo.setStatusMsg(str10);
        return;
      }
      localExtTransferAcctInfo2 = new ExtTransferAcctInfo(str9, str7, str8);
      paramTransferInfo.setAccountToInfo(localExtTransferAcctInfo2);
      a(paramFFSConnectionHolder, paramTransferInfo, bool1, bool2, false, str1);
      if (paramTransferInfo.getStatusCode() != 0) {
        return;
      }
    }
    paramTransferInfo.setStatusCode(0);
    paramTransferInfo.setStatusMsg("Success");
    FFSDebug.log(str1, "Done", 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, String paramString)
    throws Exception
  {
    String str1 = paramTransferInfo.getTransferDest();
    ExtTransferAcctInfo localExtTransferAcctInfo;
    if (paramBoolean3 == true)
    {
      localExtTransferAcctInfo = paramTransferInfo.getAccountFromInfo();
      String str2 = localExtTransferAcctInfo.getAcctBankRtn();
      String str3 = localExtTransferAcctInfo.getAcctNum();
      String str4 = localExtTransferAcctInfo.getAcctType();
      if ((jdMethod_goto(str2)) || (jdMethod_goto(str3)) || (jdMethod_goto(str4)))
      {
        String str5 = BPWLocaleUtil.getMessage(21264, null, "TRANSFER_MESSAGE");
        FFSDebug.log(paramString, str5, 0);
        paramTransferInfo.setStatusCode(21264);
        paramTransferInfo.setStatusMsg(str5);
        return;
      }
      if (paramBoolean1 == true)
      {
        paramTransferInfo.setAccountFromId(null);
        paramTransferInfo.setBankFromRtn(str2);
        paramTransferInfo.setAccountFromNum(str3);
        paramTransferInfo.setAccountFromType(str4);
        if (jdMethod_goto(localExtTransferAcctInfo.getBankRtnType()) == true) {
          if (CommBankIdentifier.getBankIdentifierFlag()) {
            paramTransferInfo.setBankFromRtnType("OTHER");
          } else {
            paramTransferInfo.setBankFromRtnType("FEDABA");
          }
        }
      }
      else
      {
        localExtTransferAcctInfo.setAcctDest("EXTERNAL");
        localExtTransferAcctInfo.setAcctScope("UNMANAGED");
        localExtTransferAcctInfo.setLogId(BPWTrackingIDGenerator.getNextId());
        localExtTransferAcctInfo.setCustomerId(paramTransferInfo.getCustomerId());
        if (jdMethod_goto(localExtTransferAcctInfo.getSubmittedBy())) {
          localExtTransferAcctInfo.setSubmittedBy(paramTransferInfo.getSubmittedBy());
        }
        if (jdMethod_goto(localExtTransferAcctInfo.getBankRtnType()) == true) {
          localExtTransferAcctInfo.setBankRtnType("FEDABA");
        }
        if (jdMethod_else(localExtTransferAcctInfo.getPrenote())) {
          localExtTransferAcctInfo.setPrenoteType(jdMethod_if(str1, paramTransferInfo.getTypeDetail(), localExtTransferAcctInfo.getRecipientType()));
        }
        localExtTransferAcctInfo = ExternalTransferAccount.add(paramFFSConnectionHolder, localExtTransferAcctInfo);
        if (localExtTransferAcctInfo.getStatusCode() != 0)
        {
          FFSDebug.log(paramString + "Failed to add FromAccount: " + localExtTransferAcctInfo.getStatusMsg(), 0);
          paramTransferInfo.setStatusCode(localExtTransferAcctInfo.getStatusCode());
          paramTransferInfo.setStatusMsg(localExtTransferAcctInfo.getStatusMsg());
          return;
        }
        paramTransferInfo.setAccountFromId(localExtTransferAcctInfo.getAcctId());
        paramTransferInfo.setBankFromRtn(null);
        paramTransferInfo.setBankFromRtnType(null);
        paramTransferInfo.setAccountFromNum(null);
        paramTransferInfo.setAccountFromType(null);
      }
    }
    else
    {
      localExtTransferAcctInfo = paramTransferInfo.getAccountToInfo();
      if (paramBoolean2 == true) {
        localExtTransferAcctInfo.setAcctDest("EXTERNAL");
      } else {
        localExtTransferAcctInfo.setAcctDest("INTERNAL");
      }
      localExtTransferAcctInfo.setAcctScope("UNMANAGED");
      localExtTransferAcctInfo.setLogId(BPWTrackingIDGenerator.getNextId());
      localExtTransferAcctInfo.setCustomerId(paramTransferInfo.getCustomerId());
      if (jdMethod_goto(localExtTransferAcctInfo.getSubmittedBy())) {
        localExtTransferAcctInfo.setSubmittedBy(paramTransferInfo.getSubmittedBy());
      }
      if (jdMethod_goto(localExtTransferAcctInfo.getBankRtnType()) == true) {
        localExtTransferAcctInfo.setBankRtnType("FEDABA");
      }
      if (jdMethod_else(localExtTransferAcctInfo.getPrenote())) {
        localExtTransferAcctInfo.setPrenoteType(jdMethod_if(str1, paramTransferInfo.getTypeDetail(), localExtTransferAcctInfo.getRecipientType()));
      }
      localExtTransferAcctInfo = ExternalTransferAccount.add(paramFFSConnectionHolder, localExtTransferAcctInfo);
      if (localExtTransferAcctInfo.getStatusCode() != 0)
      {
        FFSDebug.log(paramString + "Failed to add ToAccount: " + localExtTransferAcctInfo.getStatusMsg(), 0);
        paramTransferInfo.setStatusCode(localExtTransferAcctInfo.getStatusCode());
        paramTransferInfo.setStatusMsg(localExtTransferAcctInfo.getStatusMsg());
        return;
      }
      paramTransferInfo.setAccountToId(localExtTransferAcctInfo.getAcctId());
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo1, TransferInfo paramTransferInfo2, boolean paramBoolean)
    throws Exception
  {
    String str1 = "TransferProcessor.handleModTransferAccount: ";
    FFSDebug.log(str1, "Start", 6);
    String str2 = paramTransferInfo1.getTransferDest();
    String str3 = paramTransferInfo1.getTransferType();
    int i = 0;
    if ((str3 != null) && ((str3.trim().equalsIgnoreCase("TEMPLATE")) || (str3.trim().equalsIgnoreCase("RECTEMPLATE")))) {
      i = 1;
    }
    boolean bool1 = false;
    boolean bool2 = false;
    if (str2.equalsIgnoreCase("ITOE"))
    {
      bool1 = true;
      bool2 = true;
    }
    else if (!str2.equalsIgnoreCase("ETOI"))
    {
      if ((str2.equalsIgnoreCase("ITOI")) && (i != 0))
      {
        bool1 = true;
        bool2 = false;
      }
      else
      {
        str4 = BPWLocaleUtil.getMessage(21262, new String[] { str2 }, "TRANSFER_MESSAGE");
        paramTransferInfo1.setStatusCode(21262);
        paramTransferInfo1.setStatusMsg(str4);
        FFSDebug.log(str1 + str4, 0);
        return;
      }
    }
    String str4 = paramTransferInfo1.getAccountFromId();
    ExtTransferAcctInfo localExtTransferAcctInfo = paramTransferInfo1.getAccountFromInfo();
    String str5;
    String str7;
    String str8;
    String str9;
    if (str4 != null)
    {
      if (bool1 == true)
      {
        str5 = BPWLocaleUtil.getMessage(21670, new String[] { str2, "FromAccount" }, "TRANSFER_MESSAGE");
        FFSDebug.log(str1, str5, 0);
        paramTransferInfo1.setStatusCode(21670);
        paramTransferInfo1.setStatusMsg(str5);
        return;
      }
      localExtTransferAcctInfo = new ExtTransferAcctInfo();
      localExtTransferAcctInfo.setAcctId(str4);
      localExtTransferAcctInfo = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo, false, true);
      if (localExtTransferAcctInfo.getStatusCode() == 16020)
      {
        str5 = BPWLocaleUtil.getMessage(21080, null, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "FromAccount: " + str5, 0);
        paramTransferInfo1.setStatusCode(21080);
        paramTransferInfo1.setStatusMsg(str5);
        return;
      }
      if (!localExtTransferAcctInfo.getAcctDest().equalsIgnoreCase("EXTERNAL"))
      {
        str5 = BPWLocaleUtil.getMessage(21580, new String[] { localExtTransferAcctInfo.getAcctDest(), str2 }, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "FromAccount: " + str5, 0);
        paramTransferInfo1.setStatusCode(21580);
        paramTransferInfo1.setStatusMsg(str5);
        return;
      }
      paramTransferInfo1.setAccountFromInfo(localExtTransferAcctInfo);
      paramTransferInfo1.setBankFromRtn(null);
      paramTransferInfo1.setBankFromRtnType(null);
      paramTransferInfo1.setAccountFromNum(null);
      paramTransferInfo1.setAccountFromType(null);
    }
    else if (localExtTransferAcctInfo != null)
    {
      str5 = localExtTransferAcctInfo.getAcctBankRtn();
      localObject = localExtTransferAcctInfo.getAcctNum();
      str7 = localExtTransferAcctInfo.getAcctType();
      if ((jdMethod_goto(str5)) || (jdMethod_goto((String)localObject)) || (jdMethod_goto(str7)))
      {
        str8 = BPWLocaleUtil.getMessage(21264, null, "TRANSFER_MESSAGE");
        FFSDebug.log(str1, str8, 0);
        paramTransferInfo1.setStatusCode(21264);
        paramTransferInfo1.setStatusMsg(str8);
        return;
      }
      str8 = localExtTransferAcctInfo.getAction();
      if ("mod".equalsIgnoreCase(str8))
      {
        if (bool1 == true)
        {
          paramTransferInfo1.setAccountFromId(null);
          paramTransferInfo1.setBankFromRtn(str5);
          paramTransferInfo1.setAccountFromNum((String)localObject);
          paramTransferInfo1.setAccountFromType(str7);
          if (jdMethod_goto(localExtTransferAcctInfo.getBankRtnType()) == true) {
            if (CommBankIdentifier.getBankIdentifierFlag()) {
              paramTransferInfo1.setBankFromRtnType("OTHER");
            } else {
              paramTransferInfo1.setBankFromRtnType("FEDABA");
            }
          }
        }
        else
        {
          if (jdMethod_try(paramFFSConnectionHolder, str5) == true)
          {
            str9 = BPWLocaleUtil.getMessage(21265, new String[] { str2 }, "TRANSFER_MESSAGE");
            FFSDebug.log(str1, str9, 0);
            paramTransferInfo1.setStatusCode(21265);
            paramTransferInfo1.setStatusMsg(str9);
            return;
          }
          if (localExtTransferAcctInfo.getAcctScope().equals("UNMANAGED"))
          {
            localExtTransferAcctInfo.setAcctId(paramTransferInfo2.getAccountFromId());
            localExtTransferAcctInfo.setAcctDest("EXTERNAL");
            if (jdMethod_goto(localExtTransferAcctInfo.getSubmittedBy())) {
              localExtTransferAcctInfo.setSubmittedBy(paramTransferInfo1.getSubmittedBy());
            }
            if (jdMethod_goto(localExtTransferAcctInfo.getBankRtnType()) == true) {
              localExtTransferAcctInfo.setBankRtnType("FEDABA");
            }
            if (jdMethod_else(localExtTransferAcctInfo.getPrenote())) {
              localExtTransferAcctInfo.setPrenoteType(jdMethod_if(str2, paramTransferInfo1.getTypeDetail(), localExtTransferAcctInfo.getRecipientType()));
            }
            localExtTransferAcctInfo = ExternalTransferAccount.modify(paramFFSConnectionHolder, localExtTransferAcctInfo, false);
            if (localExtTransferAcctInfo.getStatusCode() != 0)
            {
              FFSDebug.log(str1 + "Failed to modify FromAccount: " + localExtTransferAcctInfo.getStatusMsg(), 0);
              paramTransferInfo1.setStatusCode(localExtTransferAcctInfo.getStatusCode());
              paramTransferInfo1.setStatusMsg(localExtTransferAcctInfo.getStatusMsg());
              return;
            }
            paramTransferInfo1.setBankFromRtn(null);
            paramTransferInfo1.setBankFromRtnType(null);
            paramTransferInfo1.setAccountFromNum(null);
            paramTransferInfo1.setAccountFromType(null);
            paramTransferInfo1.setAccountFromId(localExtTransferAcctInfo.getAcctId());
          }
        }
      }
      else if ("add".equalsIgnoreCase(str8))
      {
        a(paramFFSConnectionHolder, paramTransferInfo1, bool1, bool2, true, str1);
        if (paramTransferInfo1.getStatusCode() == 0) {}
      }
      else if (bool1 == true)
      {
        paramTransferInfo1.setAccountFromId(null);
        paramTransferInfo1.setBankFromRtn(str5);
        paramTransferInfo1.setAccountFromNum((String)localObject);
        paramTransferInfo1.setAccountFromType(str7);
        if (jdMethod_goto(localExtTransferAcctInfo.getBankRtnType()) == true) {
          if (CommBankIdentifier.getBankIdentifierFlag()) {
            paramTransferInfo1.setBankFromRtnType("OTHER");
          } else {
            paramTransferInfo1.setBankFromRtnType("FEDABA");
          }
        }
      }
      else
      {
        paramTransferInfo1.setBankFromRtn(null);
        paramTransferInfo1.setBankFromRtnType(null);
        paramTransferInfo1.setAccountFromNum(null);
        paramTransferInfo1.setAccountFromType(null);
        paramTransferInfo1.setAccountFromId(paramTransferInfo2.getAccountFromId());
      }
    }
    else
    {
      if (!bool1)
      {
        str5 = BPWLocaleUtil.getMessage(21180, null, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "FromAccount: " + str5, 0);
        paramTransferInfo1.setStatusCode(21180);
        paramTransferInfo1.setStatusMsg(str5);
        return;
      }
      if (jdMethod_goto(paramTransferInfo1.getBankFromRtnType()) == true) {
        if (CommBankIdentifier.getBankIdentifierFlag()) {
          paramTransferInfo1.setBankFromRtnType("OTHER");
        } else {
          paramTransferInfo1.setBankFromRtnType("FEDABA");
        }
      }
      if (paramTransferInfo1.getBankFromRtnType().equals("FEDABA")) {
        try
        {
          boolean bool3 = BPWUtil.validateABA(paramTransferInfo1.getBankFromRtn());
          if (!bool3)
          {
            FFSDebug.log(str1 + "FromAccount: " + "Invalid BankRTN", 0);
            paramTransferInfo1.setStatusCode(23670);
            paramTransferInfo1.setStatusMsg("Invalid BankRTN");
            return;
          }
        }
        catch (FFSException localFFSException)
        {
          paramTransferInfo1.setStatusCode(23670);
          paramTransferInfo1.setStatusMsg("Invalid BankRTN");
          FFSDebug.log(str1 + "FromAccount: " + localFFSException.toString(), 0);
          return;
        }
      }
    }
    String str6 = paramTransferInfo1.getAccountToId();
    Object localObject = paramTransferInfo1.getAccountToInfo();
    if (str6 != null)
    {
      localObject = new ExtTransferAcctInfo();
      ((ExtTransferAcctInfo)localObject).setAcctId(str6);
      localObject = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, (ExtTransferAcctInfo)localObject, false, true);
      if (((ExtTransferAcctInfo)localObject).getStatusCode() == 16020)
      {
        str7 = BPWLocaleUtil.getMessage(21080, new String[] { str2 }, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "ToAccount: " + str7, 0);
        paramTransferInfo1.setStatusCode(21080);
        paramTransferInfo1.setStatusMsg(str7);
        return;
      }
      if (((ExtTransferAcctInfo)localObject).getAcctScope().equalsIgnoreCase("UNMANAGED"))
      {
        if (!str6.equals(paramTransferInfo2.getAccountToId()))
        {
          str7 = BPWLocaleUtil.getMessage(21570, new String[] { str6, paramTransferInfo2.getAccountToId() }, "TRANSFER_MESSAGE");
          FFSDebug.log(str1 + "ToAccount: " + str7, 0);
          paramTransferInfo1.setStatusCode(21570);
          paramTransferInfo1.setStatusMsg(str7);
        }
      }
      else if (((ExtTransferAcctInfo)localObject).getAcctScope().equalsIgnoreCase("MANAGED"))
      {
        if (!bool2)
        {
          str7 = BPWLocaleUtil.getMessage(21670, new String[] { str2, "ToAccount" }, "TRANSFER_MESSAGE");
          FFSDebug.log(str1 + str7, 0);
          paramTransferInfo1.setStatusCode(21670);
          paramTransferInfo1.setStatusMsg(str7);
        }
      }
      else
      {
        str7 = BPWLocaleUtil.getMessage(21080, new String[] { str2 }, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "ToAccount: " + str7, 0);
        paramTransferInfo1.setStatusCode(21080);
        paramTransferInfo1.setStatusMsg(str7);
        return;
      }
      if (!jdMethod_new(((ExtTransferAcctInfo)localObject).getAcctDest(), bool2))
      {
        str7 = BPWLocaleUtil.getMessage(21580, new String[] { ((ExtTransferAcctInfo)localObject).getAcctDest(), str2 }, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "ToAccount: " + str7, 0);
        paramTransferInfo1.setStatusCode(21580);
        paramTransferInfo1.setStatusMsg(str7);
        return;
      }
      paramTransferInfo1.setAccountToInfo((ExtTransferAcctInfo)localObject);
    }
    else if (localObject != null)
    {
      str7 = ((ExtTransferAcctInfo)localObject).getAcctBankRtn();
      str8 = ((ExtTransferAcctInfo)localObject).getAcctNum();
      str9 = ((ExtTransferAcctInfo)localObject).getAcctType();
      if ((jdMethod_goto(str7)) || (jdMethod_goto(str8)) || (jdMethod_goto(str9)))
      {
        str10 = BPWLocaleUtil.getMessage(21264, null, "TRANSFER_MESSAGE");
        FFSDebug.log(str1, str10, 0);
        paramTransferInfo1.setStatusCode(21264);
        paramTransferInfo1.setStatusMsg(str10);
        return;
      }
      String str10 = ((ExtTransferAcctInfo)localObject).getAction();
      if ("mod".equalsIgnoreCase(str10))
      {
        if (bool2 == true)
        {
          ((ExtTransferAcctInfo)localObject).setAcctDest("EXTERNAL");
          if (jdMethod_try(paramFFSConnectionHolder, str7) == true)
          {
            String str11 = BPWLocaleUtil.getMessage(21265, new String[] { str2 }, "TRANSFER_MESSAGE");
            FFSDebug.log(str1, str11, 0);
            paramTransferInfo1.setStatusCode(21265);
            paramTransferInfo1.setStatusMsg(str11);
          }
        }
        else
        {
          ((ExtTransferAcctInfo)localObject).setAcctDest("INTERNAL");
        }
        if (((ExtTransferAcctInfo)localObject).getAcctScope().equals("UNMANAGED"))
        {
          ((ExtTransferAcctInfo)localObject).setAcctId(paramTransferInfo2.getAccountToId());
          if (jdMethod_goto(((ExtTransferAcctInfo)localObject).getSubmittedBy())) {
            ((ExtTransferAcctInfo)localObject).setSubmittedBy(paramTransferInfo1.getSubmittedBy());
          }
          if (jdMethod_goto(((ExtTransferAcctInfo)localObject).getBankRtnType()) == true) {
            ((ExtTransferAcctInfo)localObject).setBankRtnType("FEDABA");
          }
          if (jdMethod_else(((ExtTransferAcctInfo)localObject).getPrenote())) {
            ((ExtTransferAcctInfo)localObject).setPrenoteType(jdMethod_if(str2, paramTransferInfo1.getTypeDetail(), ((ExtTransferAcctInfo)localObject).getRecipientType()));
          }
          localObject = ExternalTransferAccount.modify(paramFFSConnectionHolder, (ExtTransferAcctInfo)localObject, false);
          if (((ExtTransferAcctInfo)localObject).getStatusCode() != 0)
          {
            FFSDebug.log(str1 + "Failed to modify ToAccount: " + ((ExtTransferAcctInfo)localObject).getStatusMsg(), 0);
            paramTransferInfo1.setStatusCode(((ExtTransferAcctInfo)localObject).getStatusCode());
            paramTransferInfo1.setStatusMsg(((ExtTransferAcctInfo)localObject).getStatusMsg());
            return;
          }
          paramTransferInfo1.setAccountToId(((ExtTransferAcctInfo)localObject).getAcctId());
        }
      }
      else if ("add".equalsIgnoreCase(str10))
      {
        a(paramFFSConnectionHolder, paramTransferInfo1, bool1, bool2, false, str1);
        if (paramTransferInfo1.getStatusCode() == 0) {}
      }
      else
      {
        paramTransferInfo1.setAccountToId(paramTransferInfo2.getAccountToId());
      }
    }
    else
    {
      if (bool2 == true)
      {
        str7 = BPWLocaleUtil.getMessage(21180, null, "TRANSFER_MESSAGE");
        FFSDebug.log(str1 + "ToAccount: " + str7, 0);
        paramTransferInfo1.setStatusCode(21180);
        paramTransferInfo1.setStatusMsg(str7);
        return;
      }
      localObject = new ExtTransferAcctInfo(paramTransferInfo1.getBankToRtn(), paramTransferInfo1.getAccountToNum(), paramTransferInfo1.getAccountToType());
      paramTransferInfo1.setAccountToInfo((ExtTransferAcctInfo)localObject);
      a(paramFFSConnectionHolder, paramTransferInfo1, bool1, bool2, false, str1);
      if (paramTransferInfo1.getStatusCode() != 0) {
        return;
      }
    }
    FFSDebug.log(str1, "Done", 6);
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo)
    throws Exception
  {
    String str1 = "TransferProcessor.handleCancTransferAccount: ";
    FFSDebug.log(str1, "Start", 6);
    String str2 = paramTransferInfo.getAccountFromId();
    ExtTransferAcctInfo localExtTransferAcctInfo1 = paramTransferInfo.getAccountFromInfo();
    if (localExtTransferAcctInfo1 == null)
    {
      if (!jdMethod_goto(str2))
      {
        localExtTransferAcctInfo1 = new ExtTransferAcctInfo();
        localExtTransferAcctInfo1.setAcctId(str2);
        localExtTransferAcctInfo1 = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo1, false, true);
      }
    }
    else {
      localExtTransferAcctInfo1.setStatusCode(0);
    }
    if ((localExtTransferAcctInfo1 != null) && (localExtTransferAcctInfo1.getStatusCode() == 0) && ("UNMANAGED".equals(localExtTransferAcctInfo1.getAcctScope())))
    {
      localExtTransferAcctInfo1 = ExternalTransferAccount.cancel(paramFFSConnectionHolder, localExtTransferAcctInfo1, false);
      if (localExtTransferAcctInfo1.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "Failed to cancel FromAccount: " + localExtTransferAcctInfo1.getStatusMsg(), 0);
        paramTransferInfo.setStatusCode(localExtTransferAcctInfo1.getStatusCode());
        paramTransferInfo.setStatusMsg(localExtTransferAcctInfo1.getStatusMsg());
        return;
      }
    }
    String str3 = paramTransferInfo.getAccountToId();
    ExtTransferAcctInfo localExtTransferAcctInfo2 = paramTransferInfo.getAccountToInfo();
    if (localExtTransferAcctInfo2 == null)
    {
      if (!jdMethod_goto(str3))
      {
        localExtTransferAcctInfo2 = new ExtTransferAcctInfo();
        localExtTransferAcctInfo2.setAcctId(str3);
        localExtTransferAcctInfo2 = ExternalTransferAccount.getExternalTransferAccount(paramFFSConnectionHolder, localExtTransferAcctInfo2, false, true);
      }
    }
    else {
      localExtTransferAcctInfo2.setStatusCode(0);
    }
    if ((localExtTransferAcctInfo2 != null) && (localExtTransferAcctInfo2.getStatusCode() == 0) && ("UNMANAGED".equals(localExtTransferAcctInfo2.getAcctScope())))
    {
      localExtTransferAcctInfo2 = ExternalTransferAccount.cancel(paramFFSConnectionHolder, localExtTransferAcctInfo2, false);
      if (localExtTransferAcctInfo2.getStatusCode() != 0)
      {
        FFSDebug.log(str1 + "Failed to cancel ToAccount: " + localExtTransferAcctInfo2.getStatusMsg(), 0);
        paramTransferInfo.setStatusCode(localExtTransferAcctInfo2.getStatusCode());
        paramTransferInfo.setStatusMsg(localExtTransferAcctInfo2.getStatusMsg());
        return;
      }
    }
    FFSDebug.log(str1, "Done", 6);
  }
  
  private String jdMethod_if(String paramString1, String paramString2, String paramString3)
  {
    String str = null;
    if ((!jdMethod_goto(paramString1)) && (!jdMethod_goto(paramString2))) {
      if (paramString2.equals("WEB")) {
        str = "WEB";
      } else if (paramString2.equals("TEL")) {
        str = "TEL";
      } else if (paramString1.equals("ITOE"))
      {
        if (paramString2.equals("CCD")) {
          str = "CCD - Credit";
        } else if (paramString2.equals("PPD")) {
          str = "PPD - Credit";
        }
      }
      else if (paramString2.equals("CCD")) {
        str = "CCD - Debit";
      } else if (paramString2.equals("PPD")) {
        str = "PPD - Debit";
      }
    }
    if ((str == null) && (paramString3 != null)) {
      if (paramString3.equals("BUSINESS")) {
        str = "CCD - Credit";
      } else {
        str = "PPD - Credit";
      }
    }
    return str;
  }
  
  private boolean jdMethod_else(String paramString)
  {
    boolean bool = false;
    if ((paramString != null) && (paramString.trim().equalsIgnoreCase("Y"))) {
      bool = true;
    }
    return bool;
  }
  
  private TransferInfo a(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, TransferInfo paramTransferInfo, boolean paramBoolean)
    throws FFSException
  {
    String str1 = "TransferProcessor.addIntraTransfer(conn,):";
    FFSDebug.log(str1, " Start ", 6);
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    String str2 = "AddIntraTransferOFX200";
    if (paramBoolean) {
      str2 = "AddIntraTransferFromScheduleOFX200";
    }
    try
    {
      FFSDebug.log(str1, " transferInfo.getLogId()=", paramTransferInfo.getLogId(), 6);
      if ((paramTransferInfo.getLogId() == null) || (paramTransferInfo.getLogId().trim().length() == 0))
      {
        localObject1 = DBUtil.getNextIndexString("LogID");
        paramTransferInfo.setLogId((String)localObject1);
      }
      if (paramFFSConnectionHolder1.conn == null)
      {
        localObject1 = str1 + "TransferProcessor.addIntraTransfer: DB Connection for main transaction is null.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      if (paramFFSConnectionHolder2.conn == null)
      {
        localObject1 = str1 + "TransferProcessor.addIntraTransfer: DB Connection for activity logging is null.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      Object localObject1 = BPWFI.getBPWFIInfo(paramFFSConnectionHolder1, paramTransferInfo.getFIId());
      if (((BPWFIInfo)localObject1).getStatusCode() != 0)
      {
        FFSDebug.log(str1 + ((BPWFIInfo)localObject1).getStatusMsg(), 0);
        paramTransferInfo.setStatusCode(((BPWFIInfo)localObject1).getStatusCode());
        paramTransferInfo.setStatusMsg(((BPWFIInfo)localObject1).getStatusMsg());
        return paramTransferInfo;
      }
      if ((!paramBoolean) && (Trans.checkDuplicateTIDAndSaveTID(paramTransferInfo.getTrnId())))
      {
        paramTransferInfo.setStatusCode(19220);
        paramTransferInfo.setStatusMsg("Duplicate TrnID");
        paramTransferInfo.setTransferType("DUPLICATE");
        if (this.b6 >= 1) {
          a(paramFFSConnectionHolder2, paramTransferInfo, "AddDup", false);
        }
        paramFFSConnectionHolder2.conn.commit();
        return paramTransferInfo;
      }
      jdMethod_else(paramTransferInfo);
      if (paramTransferInfo.getStatusCode() != 0) {
        return paramTransferInfo;
      }
      paramTransferInfo.setDateCreate(DBUtil.getCurrentLogDate());
      if (!a(localTransferCalloutHandlerBase, paramTransferInfo, str2))
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
        return paramTransferInfo;
      }
      str3 = "WILLPROCESSON";
      Object localObject2;
      Object localObject3;
      try
      {
        paramTransferInfo.setPrcStatus("WILLPROCESSON");
        paramTransferInfo.setAction("Add");
        String str4 = XferInstruction.create(paramFFSConnectionHolder1, paramTransferInfo);
        paramTransferInfo.setSrvrTId(str4);
        FFSDebug.log(str1, "before limit checking, transferInfo: " + paramTransferInfo, 6);
        localObject2 = TransferIntraMap.mapTranInfoToIntraTrnInfo(paramTransferInfo);
        localObject3 = new HashMap();
        int i = LimitCheckApprovalProcessor.processIntraTrnAdd(paramFFSConnectionHolder1, (IntraTrnInfo)localObject2, (HashMap)localObject3);
        str3 = LimitCheckApprovalProcessor.mapToStatus(i);
        FFSDebug.log(str1, "retStatus LimitCheck:" + str3, 6);
        if ((!"WILLPROCESSON".equals(str3)) && (!"APPROVAL_PENDING".equals(str3)))
        {
          paramTransferInfo.setPrcStatus(str3);
          paramTransferInfo.setStatusCode(((IntraTrnInfo)localObject2).getStatusCode());
          paramTransferInfo.setStatusMsg(((IntraTrnInfo)localObject2).getStatusMsg());
          if ((((HashMap)localObject3).get("FAILED_LIMITS") != null) && ((((HashMap)localObject3).get("FAILED_LIMITS") instanceof Limits)))
          {
            localObject4 = paramTransferInfo.getExtInfo();
            if (localObject4 != null) {
              ((Hashtable)localObject4).put("NOT_ALLOWED_APPROVAL_LIMITS", ((HashMap)localObject3).get("FAILED_LIMITS"));
            }
          }
          if (this.b6 >= 3)
          {
            localObject4 = BPWLocaleUtil.getLocalizedMessage(401, null, "BOOKTRANSFER_AUDITLOG_MESSAGE");
            a(paramTransferInfo, "Add", (ILocalizable)localObject4, false);
          }
          jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
          return paramTransferInfo;
        }
        if ("APPROVAL_PENDING".equals(str3)) {
          paramTransferInfo.setPrcStatus("APPROVAL_PENDING");
        } else {
          paramTransferInfo.setPrcStatus("WILLPROCESSON");
        }
        XferInstruction.updateStatus(paramFFSConnectionHolder1, str4, str3);
        a(paramTransferInfo);
        Object localObject4 = paramTransferInfo.getExtInfo();
        if ((localObject4 != null) && (!((Hashtable)localObject4).isEmpty())) {
          BPWXferExtraInfo.insertHashtable(str4, (Hashtable)localObject4, paramFFSConnectionHolder1);
        }
        if (paramTransferInfo.getStatusCode() != 0)
        {
          if (this.b6 >= 1) {
            a(paramTransferInfo, "Add", null, false);
          }
          jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
          return paramTransferInfo;
        }
        if (this.b6 >= 3) {
          a(paramFFSConnectionHolder1, paramTransferInfo, "Add", false);
        }
        if (("WILLPROCESSON".equalsIgnoreCase(str3)) && (paramTransferInfo.getProcessType() != 2)) {
          if (paramTransferInfo.getDateDue() != null)
          {
            jdMethod_new(paramFFSConnectionHolder1, paramTransferInfo);
          }
          else
          {
            paramTransferInfo.setDateDue(String.valueOf(DBUtil.getCurrentStartDate()));
            jdMethod_new(paramFFSConnectionHolder1, paramTransferInfo);
          }
        }
        paramFFSConnectionHolder1.conn.commit();
        if (paramTransferInfo.getStatusCode() == 0) {
          jdMethod_do(localTransferCalloutHandlerBase, paramTransferInfo, str2);
        } else {
          jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
        }
      }
      catch (Throwable localThrowable2)
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramTransferInfo, str2);
        throw localThrowable2;
      }
      if ((paramTransferInfo.getProcessType() == 2) && ("WILLPROCESSON".equalsIgnoreCase(str3))) {
        try
        {
          XferInstruction.updateStatus(paramFFSConnectionHolder1, paramTransferInfo.getSrvrTId(), "IMMED_INPROCESS");
          if (this.b6 >= 3) {
            a(paramFFSConnectionHolder1, paramTransferInfo, "AddImmediate", false);
          }
          paramFFSConnectionHolder1.conn.commit();
          IntraTrnRslt localIntraTrnRslt = new IntraTrnRslt();
          localObject2 = BPWUtil.convertHashTableToMap(paramTransferInfo.getExtInfo());
          localIntraTrnRslt = a(paramFFSConnectionHolder1, paramTransferInfo, (HashMap)localObject2);
          paramTransferInfo.setPrcStatus(XferProcessor.readStatusFromCode(localIntraTrnRslt.status));
          if (localIntraTrnRslt.status != -1)
          {
            localObject3 = new BackendProcessor();
            ((BackendProcessor)localObject3).processImmIntraTrnRslt(paramFFSConnectionHolder1, localIntraTrnRslt, paramTransferInfo.getFIId());
            paramTransferInfo.setEstimatedAmount(false);
          }
          else
          {
            FFSDebug.log("Process IntraXfer failed at the backend.");
            localObject3 = BPWLocaleUtil.getMessage(21340, new String[] { paramTransferInfo.toString(), "backend error" }, "TRANSFER_MESSAGE");
            paramTransferInfo.setStatusCode(21340);
            paramTransferInfo.setStatusMsg((String)localObject3);
            if (this.b6 >= 1) {
              a(paramTransferInfo, "AddImmediate", null, false);
            }
            XferInstruction.updateStatus(paramFFSConnectionHolder1, paramTransferInfo.getSrvrTId(), paramTransferInfo.getPrcStatus());
            SrvrTrans.updateTransferStatus(paramFFSConnectionHolder1, true, paramTransferInfo.getProcessDate(), paramTransferInfo.getPrcStatus(), localIntraTrnRslt);
          }
          paramTransferInfo.setStatusCode(0);
          paramTransferInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "TRANSFER_MESSAGE"));
        }
        catch (Throwable localThrowable3)
        {
          localObject2 = FFSDebug.stackTrace(localThrowable3);
          FFSDebug.log("Process IntraXfer failed. Error: " + (String)localObject2);
          throw new BPWException((String)localObject2);
        }
      }
    }
    catch (Throwable localThrowable1)
    {
      String str3 = "*** TransferProcessor.addIntraTransfer failed: ";
      String str5 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str3 + str5, 0);
      throw new FFSException(localThrowable1, str3);
    }
    FFSDebug.log(str1, "done", 6);
    return paramTransferInfo;
  }
  
  private void a(TransferInfo paramTransferInfo)
  {
    String str = paramTransferInfo.getMemo();
    if ((str != null) && (str.trim().length() > 0))
    {
      Hashtable localHashtable = paramTransferInfo.getExtInfo();
      if (localHashtable == null)
      {
        localHashtable = new Hashtable();
        paramTransferInfo.setExtInfo(localHashtable);
      }
      localHashtable.put("Memo", str);
    }
  }
  
  private IntraTrnRslt a(FFSConnectionHolder paramFFSConnectionHolder, TransferInfo paramTransferInfo, HashMap paramHashMap)
    throws Exception
  {
    FFSDebug.log("TransferProcessor.doImmediateXferWithoutTimeout starts ....", 6);
    DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
    String str1 = DBConnCache.save(paramFFSConnectionHolder);
    boolean bool = false;
    IntraTrnInfo[] arrayOfIntraTrnInfo = { new IntraTrnInfo(paramTransferInfo.getCustomerId(), paramTransferInfo.getBankToRtn(), null, paramTransferInfo.getAccountFromNum(), paramTransferInfo.getAccountFromType(), paramTransferInfo.getAccountToNum(), paramTransferInfo.getAccountToType(), paramTransferInfo.getAmount(), paramTransferInfo.getAmountCurrency(), paramTransferInfo.getToAmount(), paramTransferInfo.getToAmountCurrency(), paramTransferInfo.getUserAssignedAmount(), paramTransferInfo.getDateToPost(), paramTransferInfo.getSrvrTId(), paramTransferInfo.getLogId(), Integer.toString(0), 1, false, str1, null, "", paramTransferInfo.getFIId(), paramTransferInfo.getBankFromRtn(), paramTransferInfo.getBranchID()) };
    arrayOfIntraTrnInfo[0].submittedBy = paramTransferInfo.getSubmittedBy();
    if ((paramHashMap != null) && (paramHashMap.size() != 0)) {
      arrayOfIntraTrnInfo[0].extraFields = paramHashMap;
    }
    IntraTrnRslt localIntraTrnRslt = new IntraTrnRslt(arrayOfIntraTrnInfo[0].srvrTid, -1, arrayOfIntraTrnInfo[0].eventSequence, str1);
    String str3;
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
      str3 = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("*** ToBackend direct call failed: " + str3);
      FFSDebug.console("*** ToBackend direct call failed: " + str3);
    }
    finally
    {
      if (localDBConnCache != null) {
        DBConnCache.unbind(str1);
      }
    }
    String str2 = XferProcessor.readStatusFromCode(localIntraTrnRslt.status);
    if (0 != localIntraTrnRslt.status)
    {
      FFSDebug.log("TransferProcessor.doImmediateXferWithoutTimeout", "Reverting limits for a failed transaction: ", paramTransferInfo.getSrvrTId(), 6);
      str3 = RecXferProcessor2.deleteLimit(paramFFSConnectionHolder, XferInstruction.getXferInstruction(paramFFSConnectionHolder, paramTransferInfo.getSrvrTId()), new HashMap(), str2, true);
      FFSDebug.log("TransferProcessor.doImmediateXferWithoutTimeout", ": Reverted limits for a failed transaction. status ", str3, 6);
      String str4 = BPWLocaleUtil.getMessage(901, new String[] { "backend error" }, "BOOKTRANSFER_AUDITLOG_MESSAGE");
      localIntraTrnRslt.setStatusMsg(str4);
      FFSDebug.log("TransferProcessor.doImmediateXferWithoutTimeout: status msg set to result: " + str4, 6);
    }
    if (localIntraTrnRslt.status != -1) {
      bool = true;
    }
    FFSDebug.log("TransferProcessor.doImmediateXferWithoutTimeout complete isBackendDone: " + bool, 6);
    return localIntraTrnRslt;
  }
  
  private TransferInfo a(FFSConnectionHolder paramFFSConnectionHolder1, FFSConnectionHolder paramFFSConnectionHolder2, RecTransferInfo paramRecTransferInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.addRecIntraTransfer(conn,):";
    FFSDebug.log(str1, " Start ", 6);
    TransferCalloutHandlerBase localTransferCalloutHandlerBase = (TransferCalloutHandlerBase)FFSRegistry.lookup("TRANSFERCALLOUTHANDLER");
    String str2 = "AddIntraRecTransferOFX200";
    try
    {
      FFSDebug.log(str1, " recTransferInfo.getLogId()=", paramRecTransferInfo.getLogId(), 6);
      if ((paramRecTransferInfo.getLogId() == null) || (paramRecTransferInfo.getLogId().trim().length() == 0))
      {
        localObject1 = DBUtil.getNextIndexString("LogID");
        paramRecTransferInfo.setLogId((String)localObject1);
      }
      if (paramFFSConnectionHolder1.conn == null)
      {
        localObject1 = str1 + "TransferProcessor.addIntraTransfer: DB Connection for main transaction is null.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      if (paramFFSConnectionHolder2.conn == null)
      {
        localObject1 = str1 + "TransferProcessor.addIntraTransfer: DB Connection for activity logging is null.";
        FFSDebug.log((String)localObject1, 0);
        throw new FFSException((String)localObject1);
      }
      Object localObject1 = BPWFI.getBPWFIInfo(paramFFSConnectionHolder1, paramRecTransferInfo.getFIId());
      if (((BPWFIInfo)localObject1).getStatusCode() != 0)
      {
        FFSDebug.log(str1 + ((BPWFIInfo)localObject1).getStatusMsg(), 0);
        paramRecTransferInfo.setStatusCode(((BPWFIInfo)localObject1).getStatusCode());
        paramRecTransferInfo.setStatusMsg(((BPWFIInfo)localObject1).getStatusMsg());
        return paramRecTransferInfo;
      }
      if (Trans.checkDuplicateTIDAndSaveTID(paramRecTransferInfo.getTrnId()))
      {
        paramRecTransferInfo.setStatusCode(19220);
        paramRecTransferInfo.setStatusMsg("Duplicate TrnID");
        paramRecTransferInfo.setTransferType("DUPLICATE");
        if (this.b6 >= 1) {
          a(paramFFSConnectionHolder2, paramRecTransferInfo, "AddDup", true);
        }
        paramFFSConnectionHolder2.conn.commit();
        return paramRecTransferInfo;
      }
      paramRecTransferInfo.setDateCreate(DBUtil.getCurrentLogDate());
      if (!a(localTransferCalloutHandlerBase, paramRecTransferInfo, str2))
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramRecTransferInfo, str2);
        return paramRecTransferInfo;
      }
      try
      {
        paramRecTransferInfo.setPrcStatus("WILLPROCESSON");
        paramRecTransferInfo.setAction("Add");
        String str3 = RecXferInstruction.create(paramFFSConnectionHolder1, paramRecTransferInfo);
        a(paramRecTransferInfo);
        localObject2 = paramRecTransferInfo.getExtInfo();
        if ((localObject2 != null) && (!((Hashtable)localObject2).isEmpty())) {
          BPWRecXferExtraInfo.insertHashtable(str3, (Hashtable)localObject2, paramFFSConnectionHolder1);
        }
        jdMethod_do(paramFFSConnectionHolder1, paramRecTransferInfo);
        if (this.b6 >= 3) {
          a(paramFFSConnectionHolder1, paramRecTransferInfo, "Add", true);
        }
        if (paramRecTransferInfo.getStatusCode() == 0)
        {
          TransferInfo localTransferInfo = (TransferInfo)paramRecTransferInfo.clone();
          localTransferInfo.setSourceRecSrvrTId(paramRecTransferInfo.getSrvrTId());
          localTransferInfo.setSrvrTId(null);
          a(paramFFSConnectionHolder1, paramFFSConnectionHolder2, localTransferInfo, true);
          if (localTransferInfo.getStatusCode() != 0)
          {
            paramRecTransferInfo.setPrcStatus(localTransferInfo.getPrcStatus());
            paramRecTransferInfo.setStatusCode(localTransferInfo.getStatusCode());
            paramRecTransferInfo.setStatusMsg(localTransferInfo.getStatusMsg());
            Hashtable localHashtable = paramRecTransferInfo.getExtInfo();
            if (localHashtable == null) {
              localHashtable = new Hashtable();
            }
            localHashtable.putAll(new HashMap(localTransferInfo.getExtInfo()));
            paramRecTransferInfo.setExtInfo(localHashtable);
          }
          else
          {
            RecSrvrTIDToSrvrTID.create(paramFFSConnectionHolder1, paramRecTransferInfo.getSrvrTId(), localTransferInfo.getSrvrTId());
          }
        }
        if (paramRecTransferInfo.getStatusCode() == 0)
        {
          paramFFSConnectionHolder1.conn.commit();
          jdMethod_do(localTransferCalloutHandlerBase, paramRecTransferInfo, str2);
        }
        else
        {
          paramFFSConnectionHolder1.conn.rollback();
          jdMethod_if(localTransferCalloutHandlerBase, paramRecTransferInfo, str2);
        }
      }
      catch (Throwable localThrowable2)
      {
        jdMethod_if(localTransferCalloutHandlerBase, paramRecTransferInfo, str2);
        throw localThrowable2;
      }
    }
    catch (Throwable localThrowable1)
    {
      paramFFSConnectionHolder1.conn.rollback();
      String str4 = "*** TransferProcessor.addRecIntraTransfer failed: ";
      Object localObject2 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log(str4 + (String)localObject2, 0);
      throw new FFSException(localThrowable1, str4);
    }
    FFSDebug.log(str1, "done", 6);
    return paramRecTransferInfo;
  }
  
  private boolean a(TransferCalloutHandlerBase paramTransferCalloutHandlerBase, TransferInfo paramTransferInfo, String paramString)
  {
    TransferInfo localTransferInfo = (TransferInfo)paramTransferInfo.clone();
    try
    {
      paramTransferCalloutHandlerBase.begin(localTransferInfo, paramString);
      return true;
    }
    catch (FFSException localFFSException)
    {
      String str = localTransferInfo.getStatusMsg();
      if ((str == null) || (str.trim().length() == 0))
      {
        str = "Transfer action aborted during begin callout.";
        try
        {
          str = BPWLocaleUtil.getMessage(21510, new String[] { paramTransferInfo.getAction() }, "TRANSFER_MESSAGE");
        }
        catch (Exception localException) {}
      }
      paramTransferInfo.setStatusCode(21510);
      paramTransferInfo.setStatusMsg(str);
    }
    return false;
  }
  
  private void jdMethod_do(TransferCalloutHandlerBase paramTransferCalloutHandlerBase, TransferInfo paramTransferInfo, String paramString)
  {
    try
    {
      paramTransferCalloutHandlerBase.end((TransferInfo)paramTransferInfo.clone(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  private void jdMethod_if(TransferCalloutHandlerBase paramTransferCalloutHandlerBase, TransferInfo paramTransferInfo, String paramString)
  {
    try
    {
      paramTransferCalloutHandlerBase.failure((TransferInfo)paramTransferInfo.clone(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  private boolean jdMethod_if(TransferInfo paramTransferInfo, boolean paramBoolean, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    if (paramBoolean) {
      return a((RecTransferInfo)paramTransferInfo, paramFFSConnectionHolder);
    }
    return jdMethod_if(paramTransferInfo, paramFFSConnectionHolder);
  }
  
  private boolean jdMethod_if(TransferInfo paramTransferInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str1 = "TransferProcessor.isSingleTransferModifiable: ";
    FFSDebug.log(str1 + " SvrvTID = " + paramTransferInfo.getSrvrTId() + ", Status = " + paramTransferInfo.getPrcStatus(), 6);
    boolean bool = true;
    String str2 = paramTransferInfo.getPrcStatus();
    if (str2 != null)
    {
      if ((str2.compareTo("APPROVAL_PENDING") != 0) && (str2.compareTo("APPROVAL_REJECTED") != 0))
      {
        String str3 = jdMethod_for(paramTransferInfo);
        if (str3 != null)
        {
          FFSDebug.log(str1 + "Instruction name = " + str3, 6);
          int i = Integer.parseInt(paramTransferInfo.getProcessDate());
          SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd00");
          int j = Integer.parseInt(localSimpleDateFormat.format(new Date()));
          if (i <= j) {
            if (ScheduleInfo.isScheduleRunning(paramFFSConnectionHolder, paramTransferInfo.getFIId(), str3))
            {
              FFSDebug.log(str1 + "The " + str3 + " schedule is currently running. " + "The transfer is not modifiable.", 6);
              bool = false;
            }
            else if (jdMethod_do(paramFFSConnectionHolder, paramTransferInfo.getFIId(), str3))
            {
              FFSDebug.log(str1 + "A schedule will run soon. The transfer is not modifiable.", 6);
              bool = false;
            }
          }
        }
        else
        {
          FFSDebug.log(str1 + "No instruction Name foundfor the transfer", 6);
          bool = false;
        }
      }
    }
    else {
      bool = false;
    }
    FFSDebug.log(str1 + bool, 6);
    return bool;
  }
  
  private boolean jdMethod_do(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
    throws FFSException
  {
    String[] arrayOfString = new String[1];
    boolean bool = false;
    arrayOfString[0] = paramString2;
    Calendar localCalendar1 = Calendar.getInstance();
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar2.add(12, DBUtil.getTransferScheduleInterval());
    Calendar[] arrayOfCalendar = DBUtil.getScheduleRunDates(paramFFSConnectionHolder, localCalendar1, localCalendar2, paramString1, arrayOfString);
    if ((arrayOfCalendar != null) && (arrayOfCalendar.length > 0) && (DBUtil.isCalendarInRange(arrayOfCalendar[0], localCalendar1, localCalendar2))) {
      bool = true;
    }
    return bool;
  }
  
  private String jdMethod_for(TransferInfo paramTransferInfo)
  {
    String str1 = null;
    String str2 = paramTransferInfo.getPrcStatus();
    if (str2 == null) {
      return null;
    }
    if (str2.compareTo("INFUNDSPROCESSING") == 0) {
      return null;
    }
    if (str2.compareTo("FUNDSPROCESSED") == 0)
    {
      str1 = "ETFTRN";
    }
    else
    {
      int i = paramTransferInfo.getFundsProcessing() == 2 ? 1 : 0;
      int j = 0;
      int k = paramTransferInfo.getProcessType();
      j = k == 2 ? 1 : 0;
      if ((i != 0) && (j == 0)) {
        str1 = "ETFFUNDTRN";
      } else {
        str1 = "ETFTRN";
      }
    }
    return str1;
  }
  
  private boolean a(RecTransferInfo paramRecTransferInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws FFSException
  {
    String str = "TransferProcessor.isRecurringTransferModifiable: ";
    FFSDebug.log(str + " RecSvrvTID = " + paramRecTransferInfo.getSrvrTId(), 6);
    SchedulerInfo localSchedulerInfo = jdMethod_try(paramRecTransferInfo.getFIId(), true);
    if (!ScheduleInfo.canModify(paramFFSConnectionHolder, localSchedulerInfo.InstructionType, paramRecTransferInfo.getSrvrTId(), paramRecTransferInfo.getFIId(), localSchedulerInfo.NextRunTime)) {
      return false;
    }
    String[] arrayOfString = { "WILLPROCESSON", "FUNDSPROCESSED", "INFUNDSPROCESSING", "NOFUNDS", "IMMED_INPROCESS" };
    TransferInfo[] arrayOfTransferInfo = Transfer.getTransfersByRecSrvrTId(paramFFSConnectionHolder, paramRecTransferInfo.getSrvrTId(), true, arrayOfString);
    if ((arrayOfTransferInfo != null) && (arrayOfTransferInfo.length > 0))
    {
      int i = arrayOfTransferInfo.length;
      if ((i == 1) && (arrayOfTransferInfo[0] != null) && (arrayOfTransferInfo[0].getStatusCode() == 16020)) {
        return true;
      }
      for (int j = 0; j < i; j++) {
        if (!jdMethod_if(arrayOfTransferInfo[j], paramFFSConnectionHolder))
        {
          FFSDebug.log(str + " A single transfer of the recurring model is not " + "modifiable.", 6);
          return false;
        }
      }
    }
    FFSDebug.log(str + "The recurring transfer is modifiable.", 6);
    return true;
  }
  
  private SchedulerInfo jdMethod_try(String paramString, boolean paramBoolean)
    throws FFSException
  {
    SchedulerInfo localSchedulerInfo = null;
    Scheduler localScheduler = (Scheduler)FFSRegistry.lookup("SCHEDULER");
    String str = null;
    if (paramBoolean == true) {
      str = "RECETFTRN";
    } else {
      str = "ETFTRN";
    }
    if ((localScheduler != null) && (localScheduler.schIsRunning() == true)) {
      localSchedulerInfo = localScheduler.getSchedulerInfo(str, paramString);
    } else {
      try
      {
        BPWServer localBPWServer = BPWServer.getInstance();
        localSchedulerInfo = localBPWServer.getSchedulerInfoOnRemoteServer(str, paramString);
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "", 0);
        return null;
      }
    }
    if (localSchedulerInfo == null) {
      FFSDebug.log(str + " " + "Instruction type does not exist" + " for FIID =" + paramString, 0);
    }
    return localSchedulerInfo;
  }
  
  private boolean a(TransferBatchInfo paramTransferBatchInfo, boolean paramBoolean)
    throws BPWException
  {
    String str1;
    if (paramTransferBatchInfo == null)
    {
      paramTransferBatchInfo = new TransferBatchInfo();
      paramTransferBatchInfo.setStatusCode(16000);
      str1 = BPWLocaleUtil.getMessage(16000, new String[] { "transferBatchInfo" }, "TRANSFER_MESSAGE");
      paramTransferBatchInfo.setStatusMsg(str1);
      return false;
    }
    if ((paramTransferBatchInfo.getFIID() == null) || (paramTransferBatchInfo.getFIID().trim().length() == 0))
    {
      paramTransferBatchInfo.setStatusCode(16010);
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { "transferBatchInfo", "FIID" }, "TRANSFER_MESSAGE");
      paramTransferBatchInfo.setStatusMsg(str1);
      return false;
    }
    if ((paramTransferBatchInfo.getCustomerId() == null) || (paramTransferBatchInfo.getCustomerId().trim().length() == 0))
    {
      paramTransferBatchInfo.setStatusCode(16010);
      str1 = BPWLocaleUtil.getMessage(16010, new String[] { "transferBatchInfo", "CustomerId" }, "TRANSFER_MESSAGE");
      paramTransferBatchInfo.setStatusMsg(str1);
      return false;
    }
    if (!paramBoolean)
    {
      str1 = paramTransferBatchInfo.getBatchType();
      if ((str1 == null) || (str1.trim().length() == 0))
      {
        paramTransferBatchInfo.setStatusCode(16010);
        String str2 = BPWLocaleUtil.getMessage(16010, new String[] { "transferBatchInfo", "batchType" }, "TRANSFER_MESSAGE");
        paramTransferBatchInfo.setStatusMsg(str2);
        return false;
      }
      paramTransferBatchInfo.setBatchType(paramTransferBatchInfo.getBatchType().toUpperCase());
    }
    return true;
  }
  
  private void a(TransferBatchInfo paramTransferBatchInfo, String paramString, ILocalizable paramILocalizable)
  {
    String str1 = "TransferProcessor.logTransferBatchError";
    FFSConnectionHolder localFFSConnectionHolder = null;
    int i = paramTransferBatchInfo.getStatusCode();
    AuditLogRecord localAuditLogRecord = null;
    int j = 0;
    String str2 = null;
    int k = 1018;
    int m = 5210;
    if ("Add".equalsIgnoreCase(paramString))
    {
      m = 5210;
      k = 1036;
      str2 = paramTransferBatchInfo.getSubmittedBy();
    }
    else if ("Mod".equalsIgnoreCase(paramString))
    {
      m = 5211;
      k = 1037;
    }
    else if ("Can".equalsIgnoreCase(paramString))
    {
      m = 5212;
      k = 1038;
    }
    if (str2 == null) {
      str2 = paramTransferBatchInfo.getSubmittedBy();
    }
    Object localObject1;
    if (paramILocalizable == null)
    {
      localObject1 = new Object[1];
      int n = 0;
      switch (i)
      {
      case 19210: 
        n = 1039;
        break;
      case 23170: 
        n = 1040;
        break;
      case 19130: 
        n = 1041;
        break;
      case 19220: 
        n = 1042;
        break;
      case 19310: 
        n = 1043;
        break;
      case 16020: 
        n = 1044;
        break;
      case 17180: 
        n = 1045;
        break;
      case 19311: 
        n = 1046;
      default: 
        if ((paramTransferBatchInfo.getStatusMsg() == null) || (paramTransferBatchInfo.getStatusMsg().length() <= 0)) {
          n = 1021;
        }
        break;
      }
      if (n != 0) {
        localObject1[0] = BPWLocaleUtil.getLocalizableMessage(n, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
      } else {
        localObject1[0] = paramTransferBatchInfo.getStatusMsg();
      }
      paramILocalizable = BPWLocaleUtil.getLocalizableMessage(k, (Object[])localObject1, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    }
    try
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      if (localFFSConnectionHolder.conn == null)
      {
        localObject1 = str1 + "Can not get DB Connection.";
        FFSDebug.log((String)localObject1, 0);
      }
      if ((paramTransferBatchInfo.getCustomerId() != null) && (paramTransferBatchInfo.getCustomerId().length() > 0)) {
        if (paramTransferBatchInfo.getCustomerId().equals(paramTransferBatchInfo.getSubmittedBy())) {
          j = 0;
        } else {
          try
          {
            j = Integer.parseInt(paramTransferBatchInfo.getCustomerId());
          }
          catch (Exception localException1)
          {
            j = 0;
          }
        }
      }
      String str3 = paramTransferBatchInfo.getTotalAmount();
      if ((str3 == null) || (str3.trim().length() == 0)) {
        str3 = "-1";
      }
      localAuditLogRecord = new AuditLogRecord(str2, paramTransferBatchInfo.getAgentId(), paramTransferBatchInfo.getAgentType(), paramILocalizable, paramTransferBatchInfo.getLogId(), m, j, new BigDecimal(str3), paramTransferBatchInfo.getAmountCurrency(), paramTransferBatchInfo.getBatchID(), paramTransferBatchInfo.getBatchStatus(), null, null, null, null, -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, localFFSConnectionHolder.conn.getConnection());
      localFFSConnectionHolder.conn.commit();
    }
    catch (Exception localException2)
    {
      String str4 = "TransferProcessor.addTransferBatch failed " + localException2.toString();
      FFSDebug.log(str4 + FFSDebug.stackTrace(localException2), 0);
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, TransferBatchInfo paramTransferBatchInfo, String paramString)
    throws Exception
  {
    String str1 = null;
    String str2 = null;
    AuditLogRecord localAuditLogRecord = null;
    LocalizableString localLocalizableString = null;
    str1 = paramTransferBatchInfo.getBatchStatus();
    int i = 5210;
    int j = 0;
    if ("Add".equalsIgnoreCase(paramString))
    {
      i = 5210;
      j = 1025;
      str2 = paramTransferBatchInfo.getSubmittedBy();
    }
    else if ("Mod".equalsIgnoreCase(paramString))
    {
      paramTransferBatchInfo.setBatchStatus("MODIFIED");
      i = 5211;
      j = 1027;
    }
    else if ("Can".equalsIgnoreCase(paramString))
    {
      i = 5212;
      j = 1029;
    }
    else if ("AddDup".equalsIgnoreCase(paramString))
    {
      paramTransferBatchInfo.setBatchStatus("DUPLICATE");
      i = 5213;
      j = 1024;
      str2 = paramTransferBatchInfo.getSubmittedBy();
    }
    else if ("ModDup".equalsIgnoreCase(paramString))
    {
      paramTransferBatchInfo.setBatchStatus("DUPLICATE");
      i = 5214;
      j = 1026;
    }
    else if ("CanDup".equalsIgnoreCase(paramString))
    {
      paramTransferBatchInfo.setBatchStatus("DUPLICATE");
      i = 5215;
      j = 1028;
    }
    if (j != 0) {
      localLocalizableString = BPWLocaleUtil.getLocalizableMessage(j, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
    } else {
      localLocalizableString = new LocalizableString("dummy", "", null);
    }
    if (str2 == null) {
      str2 = paramTransferBatchInfo.getSubmittedBy();
    }
    try
    {
      int k = 0;
      if (paramTransferBatchInfo.getCustomerId().equals(paramTransferBatchInfo.getSubmittedBy())) {
        k = 0;
      } else {
        k = Integer.parseInt(paramTransferBatchInfo.getCustomerId());
      }
      str3 = paramTransferBatchInfo.getTotalAmount();
      if ((str3 == null) || (str3.trim().length() == 0)) {
        str3 = "-1";
      }
      localAuditLogRecord = new AuditLogRecord(str2, paramTransferBatchInfo.getAgentId(), paramTransferBatchInfo.getAgentType(), localLocalizableString, paramTransferBatchInfo.getLogId(), i, k, new BigDecimal(str3), paramTransferBatchInfo.getAmountCurrency(), paramTransferBatchInfo.getBatchID(), paramTransferBatchInfo.getBatchStatus(), null, null, null, null, -1);
      TransAuditLog.logTransAuditLog(localAuditLogRecord, paramFFSConnectionHolder.conn.getConnection());
    }
    catch (NumberFormatException localNumberFormatException)
    {
      String str3 = "TransferProcessor.addTransferBatch failed " + localNumberFormatException.toString();
      FFSDebug.log(str3 + FFSDebug.stackTrace(localNumberFormatException), 0);
      throw new FFSException(localNumberFormatException, str3);
    }
    paramTransferBatchInfo.setBatchStatus(str1);
  }
  
  private void a(TransferBatchInfo paramTransferBatchInfo)
    throws BPWException
  {
    String str1 = paramTransferBatchInfo.getSubmitDate();
    if (str1 == null)
    {
      paramTransferBatchInfo.setSubmitDate("" + DBUtil.getCurrentStartDate() / 100);
    }
    else if (!BPWUtil.checkDateBeanFormat(str1))
    {
      paramTransferBatchInfo.setStatusCode(19210);
      String str2 = BPWLocaleUtil.getMessage(19210, null, "TRANSFER_MESSAGE");
      paramTransferBatchInfo.setStatusMsg(str2);
      return;
    }
    paramTransferBatchInfo.setStatusCode(0);
    paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "TRANSFER_MESSAGE"));
  }
  
  public TransferBatchInfo addTransferBatch(TransferBatchInfo paramTransferBatchInfo)
    throws FFSException
  {
    try
    {
      FFSDebug.log("TransferProcessor.addTransferBatch start...", 6);
      String str1 = "TransferProcessor.addTransferBatch:";
      FFSDebug.log(str1, " Start ", 6);
      localObject1 = null;
      FFSConnectionHolder localFFSConnectionHolder = null;
      String str2 = null;
      TransferInfo[] arrayOfTransferInfo = null;
      int i = 0;
      BigDecimal localBigDecimal1 = new BigDecimal(0.0D);
      Object localObject2;
      if (paramTransferBatchInfo == null)
      {
        paramTransferBatchInfo.setStatusCode(16000);
        localObject2 = BPWLocaleUtil.getMessage(16000, new String[] { "transferBatchInfo" }, "TRANSFER_MESSAGE");
        paramTransferBatchInfo.setStatusMsg((String)localObject2);
        FFSDebug.log("TransferProcessor.addTransferBatch, " + (String)localObject2, 0);
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Add", null);
        }
        return paramTransferBatchInfo;
      }
      if (!a(paramTransferBatchInfo, false))
      {
        FFSDebug.log("***", str1, "failed:", paramTransferBatchInfo.getStatusMsg(), 0);
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Add", null);
        }
        return paramTransferBatchInfo;
      }
      if ((paramTransferBatchInfo.getBatchType().equals("TEMPLATE")) && (Transfer.isDuplicateTemplate(paramTransferBatchInfo.getBatchName(), paramTransferBatchInfo.getSubmittedBy(), paramTransferBatchInfo.getBatchID(), false)))
      {
        paramTransferBatchInfo.setStatusCode(21710);
        localObject2 = BPWLocaleUtil.getMessage(21710, new String[] { paramTransferBatchInfo.getBatchName() }, "TRANSFER_MESSAGE");
        paramTransferBatchInfo.setStatusMsg((String)localObject2);
        FFSDebug.log("TransferProcessor.addTransferBatch, " + (String)localObject2, 0);
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Add", null);
        }
        return paramTransferBatchInfo;
      }
      a(paramTransferBatchInfo);
      if (paramTransferBatchInfo.getStatusCode() != 0)
      {
        FFSDebug.log("***", str1, "failed:", paramTransferBatchInfo.getStatusMsg(), 0);
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Add", null);
        }
        return paramTransferBatchInfo;
      }
      arrayOfTransferInfo = paramTransferBatchInfo.getTransfers();
      i = arrayOfTransferInfo.length;
      if ((arrayOfTransferInfo == null) || (arrayOfTransferInfo.length == 0))
      {
        FFSDebug.log(str1, "failed: ", "The transfer batch does not contain any transfers.", 0);
        paramTransferBatchInfo.setStatusCode(19310);
        paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19310, null, "TRANSFER_MESSAGE"));
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Add", null);
        }
        return paramTransferBatchInfo;
      }
      try
      {
        localObject2 = DBUtil.getConnections(2);
        if ((localObject2 == null) || (localObject2.length < 2))
        {
          localObject3 = str1 + "Can not get DB Connection.";
          FFSDebug.log((String)localObject3, 0);
          throw new FFSException((String)localObject3);
        }
        localObject1 = new FFSConnectionHolder();
        ((FFSConnectionHolder)localObject1).conn = localObject2[0];
        localFFSConnectionHolder = new FFSConnectionHolder();
        localFFSConnectionHolder.conn = localObject2[1];
        if (Trans.checkDuplicateTIDAndSaveTID(paramTransferBatchInfo.getTrnId()))
        {
          paramTransferBatchInfo.setStatusCode(19220);
          paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19220, null, "TRANSFER_MESSAGE"));
          if (this.b6 >= 1) {
            a(paramTransferBatchInfo, "AddDup", null);
          }
          localObject3 = paramTransferBatchInfo;
          return localObject3;
        }
        paramTransferBatchInfo.setTransferCount(String.valueOf(i));
        paramTransferBatchInfo = TransferBatch.addTransferBatch((FFSConnectionHolder)localObject1, paramTransferBatchInfo);
        if (paramTransferBatchInfo.getStatusCode() != 0)
        {
          ((FFSConnectionHolder)localObject1).conn.rollback();
          if (this.b6 >= 1) {
            a(paramTransferBatchInfo, "Add", null);
          }
          localObject3 = paramTransferBatchInfo;
          return localObject3;
        }
        if (!paramTransferBatchInfo.getBatchType().equals("TEMPLATE")) {
          ((FFSConnectionHolder)localObject1).conn.commit();
        }
        str2 = paramTransferBatchInfo.getBatchID();
        localObject3 = null;
        int j = 1;
        for (int k = 0; k < i; k++)
        {
          arrayOfTransferInfo[k].setBatchId(str2);
          TransferInfo localTransferInfo = null;
          if (paramTransferBatchInfo.getBatchType().equals("TEMPLATE"))
          {
            if ((arrayOfTransferInfo[k].getLogId() == null) || (arrayOfTransferInfo[k].getLogId().trim().equals(""))) {
              arrayOfTransferInfo[k].setLogId(paramTransferBatchInfo.getLogId());
            }
            if (a(arrayOfTransferInfo[k], false)) {
              localTransferInfo = jdMethod_if((FFSConnectionHolder)localObject1, localFFSConnectionHolder, arrayOfTransferInfo[k], false, true);
            } else if (this.b6 >= 1) {
              a(localTransferInfo, "Add", null, false);
            }
          }
          else
          {
            if ((arrayOfTransferInfo[k].getLogId() == null) || (arrayOfTransferInfo[k].getLogId().trim().equals(""))) {
              arrayOfTransferInfo[k].setLogId(DBUtil.getNextIndexString("LogID"));
            }
            localTransferInfo = addTransfer(arrayOfTransferInfo[k]);
          }
          arrayOfTransferInfo[k].setStatusCode(localTransferInfo.getStatusCode());
          arrayOfTransferInfo[k].setStatusMsg(localTransferInfo.getStatusMsg());
          if (arrayOfTransferInfo[k].getStatusCode() == 0)
          {
            String str4 = arrayOfTransferInfo[k].getUserAssignedAmountValue();
            String str5 = arrayOfTransferInfo[k].getUserAssignedAmountCurrency();
            if (localObject3 == null) {
              localObject3 = str5;
            } else if (!((String)localObject3).equals(str5)) {
              j = 0;
            }
            BigDecimal localBigDecimal2 = new BigDecimal(str4);
            localBigDecimal1 = localBigDecimal1.add(localBigDecimal2);
          }
        }
        paramTransferBatchInfo.setBatchStatus("CREATED");
        if (j != 0)
        {
          paramTransferBatchInfo.setTotalAmount(String.valueOf(localBigDecimal1));
          paramTransferBatchInfo.setAmountCurrency((String)localObject3);
        }
        else
        {
          paramTransferBatchInfo.setTotalAmount("0.00");
          paramTransferBatchInfo.setAmountCurrency(null);
        }
        paramTransferBatchInfo = TransferBatch.updateTransferBatch((FFSConnectionHolder)localObject1, paramTransferBatchInfo);
        if (this.b6 >= 3) {
          a(localFFSConnectionHolder, paramTransferBatchInfo, "Add");
        }
        ((FFSConnectionHolder)localObject1).conn.commit();
        localFFSConnectionHolder.conn.commit();
        paramTransferBatchInfo.setStatusCode(0);
        paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "TRANSFER_MESSAGE"));
        FFSDebug.log(str1, " Done", 6);
      }
      catch (Throwable localThrowable2)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        localFFSConnectionHolder.conn.rollback();
        if (this.b6 >= 1)
        {
          localObject3 = BPWLocaleUtil.getLocalizableMessage(1048, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
          a(paramTransferBatchInfo, "Add", (ILocalizable)localObject3);
        }
        Object localObject3 = "*** TransferProcessor.addTransferBatch failed: ";
        String str3 = FFSDebug.stackTrace(localThrowable2);
        FFSDebug.log((String)localObject3 + str3, 0);
        throw new FFSException(localThrowable2, (String)localObject3);
      }
      finally
      {
        try
        {
          DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
          DBUtil.freeConnection(localFFSConnectionHolder.conn);
        }
        catch (Throwable localThrowable3)
        {
          FFSDebug.log(str1, " Failed to free the first connection ", 0);
        }
      }
      FFSDebug.log(str1, " end ", 6);
      return paramTransferBatchInfo;
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = "*** addTransferBatch:: failed:" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1);
      throw new FFSException((String)localObject1);
    }
  }
  
  public TransferBatchInfo modifyTransferBatch(TransferBatchInfo paramTransferBatchInfo)
    throws FFSException
  {
    String str1 = "TransferProcessor.modifyTransferBatch:";
    FFSConnectionHolder localFFSConnectionHolder1 = null;
    FFSConnectionHolder localFFSConnectionHolder2 = null;
    try
    {
      FFSDebug.log(str1, " Start ", 6);
      String str2 = null;
      localObject1 = null;
      int i = 0;
      BigDecimal localBigDecimal1 = new BigDecimal(0.0D);
      BigDecimal localBigDecimal2 = null;
      TransferBatchInfo localTransferBatchInfo1 = null;
      TransferInfo localTransferInfo = null;
      String str4 = null;
      str2 = paramTransferBatchInfo.getBatchID();
      Object localObject3;
      if (str2 == null)
      {
        paramTransferBatchInfo.setStatusCode(16010);
        localObject2 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferBatchInfo", "BatchId" }, "TRANSFER_MESSAGE");
        paramTransferBatchInfo.setStatusMsg((String)localObject2);
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Mod", null);
        }
        localObject3 = paramTransferBatchInfo;
        return localObject3;
      }
      if ((paramTransferBatchInfo.getBatchType() != null) && (!paramTransferBatchInfo.getBatchType().equals("TEMPLATE")))
      {
        paramTransferBatchInfo = new TransferBatchInfo();
        paramTransferBatchInfo.setStatusCode(21730);
        paramTransferBatchInfo.setStatusCode(21730);
        localObject2 = BPWLocaleUtil.getMessage(21730, new String[] { paramTransferBatchInfo.getBatchType() }, "TRANSFER_MESSAGE");
        paramTransferBatchInfo.setStatusMsg((String)localObject2);
        localObject3 = paramTransferBatchInfo;
        return localObject3;
      }
      if (!a(paramTransferBatchInfo, false))
      {
        FFSDebug.log("***", str1, "failed:", paramTransferBatchInfo.getStatusMsg(), 0);
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Mod", null);
        }
        localObject2 = paramTransferBatchInfo;
        return localObject2;
      }
      a(paramTransferBatchInfo);
      if (paramTransferBatchInfo.getStatusCode() != 0)
      {
        FFSDebug.log("***", str1, "failed:", paramTransferBatchInfo.getStatusMsg(), 0);
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Mod", null);
        }
        localObject2 = paramTransferBatchInfo;
        return localObject2;
      }
      if (Transfer.isDuplicateTemplate(paramTransferBatchInfo.getBatchName(), paramTransferBatchInfo.getSubmittedBy(), paramTransferBatchInfo.getBatchID(), true))
      {
        paramTransferBatchInfo.setStatusCode(21710);
        localObject2 = BPWLocaleUtil.getMessage(21710, new String[] { paramTransferBatchInfo.getBatchName() }, "TRANSFER_MESSAGE");
        paramTransferBatchInfo.setStatusMsg((String)localObject2);
        FFSDebug.log("TransferProcessor.addTransferBatch, " + (String)localObject2, 0);
        if (this.b6 >= 1)
        {
          a(paramTransferBatchInfo, "Mod", null);
          localObject3 = paramTransferBatchInfo;
          return localObject3;
        }
      }
      localObject1 = paramTransferBatchInfo.getTransfers();
      if ((localObject1 == null) || (localObject1.length == 0))
      {
        FFSDebug.log(str1, "failed: ", "The transfer batch does not contain any transfers.", 0);
        paramTransferBatchInfo.setStatusCode(19310);
        paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19310, null, "TRANSFER_MESSAGE"));
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Mod", null);
        }
        localObject2 = paramTransferBatchInfo;
        return localObject2;
      }
      Object localObject2 = DBUtil.getConnections(2);
      if ((localObject2 == null) || (localObject2.length < 2))
      {
        localObject3 = str1 + "Can not get DB Connections.";
        FFSDebug.log((String)localObject3, 0);
        throw new FFSException((String)localObject3);
      }
      localFFSConnectionHolder1 = new FFSConnectionHolder();
      localFFSConnectionHolder1.conn = localObject2[0];
      localFFSConnectionHolder2 = new FFSConnectionHolder();
      localFFSConnectionHolder2.conn = localObject2[1];
      if (Trans.checkDuplicateTIDAndSaveTID(paramTransferBatchInfo.getTrnId()))
      {
        paramTransferBatchInfo.setStatusCode(19220);
        paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19220, null, "TRANSFER_MESSAGE"));
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "ModDup", null);
        }
        localObject3 = paramTransferBatchInfo;
        return localObject3;
      }
      localTransferBatchInfo1 = getTransferBatchById(str2);
      if (localTransferBatchInfo1.getStatusCode() == 16020)
      {
        FFSDebug.log("***", str1, "failed:", localTransferBatchInfo1.getStatusMsg(), 0);
        paramTransferBatchInfo.setStatusCode(17090);
        paramTransferBatchInfo.setStatusMsg("batch does not exist");
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Mod", null);
        }
        localObject3 = paramTransferBatchInfo;
        return localObject3;
      }
      if ((localTransferBatchInfo1.getBatchStatus() != null) && (localTransferBatchInfo1.getBatchStatus().equals("CANCELEDON")))
      {
        paramTransferBatchInfo.setStatusCode(21734);
        paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(21734, null, "TRANSFER_MESSAGE"));
        localObject3 = paramTransferBatchInfo;
        return localObject3;
      }
      localBigDecimal1 = new BigDecimal(localTransferBatchInfo1.getTotalAmount());
      int j = 0;
      if (localBigDecimal1.intValue() == 0) {
        j = 1;
      }
      try
      {
        i = Integer.parseInt(localTransferBatchInfo1.getTransferCount());
      }
      catch (Throwable localThrowable2)
      {
        i = 0;
      }
      paramTransferBatchInfo.setTransferCount(String.valueOf(i));
      paramTransferBatchInfo = TransferBatch.updateTransferBatch(localFFSConnectionHolder1, paramTransferBatchInfo);
      if (paramTransferBatchInfo.getStatusCode() != 0)
      {
        FFSDebug.log("***", str1, "failed:", paramTransferBatchInfo.getStatusMsg(), 0);
        localFFSConnectionHolder1.conn.rollback();
        localFFSConnectionHolder2.conn.rollback();
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Mod", null);
        }
        TransferBatchInfo localTransferBatchInfo2 = paramTransferBatchInfo;
        return localTransferBatchInfo2;
      }
      int k = 1;
      Object localObject4 = null;
      for (int m = 0; m < localObject1.length; m++) {
        if (localObject1[m] != null)
        {
          str4 = localObject1[m].getAction();
          if ((localObject1[m].getLogId() == null) || (localObject1[m].getLogId().trim().equals(""))) {
            localObject1[m].setLogId(paramTransferBatchInfo.getLogId());
          }
          String str5 = localObject1[m].getUserAssignedAmountValue();
          String str6 = localObject1[m].getUserAssignedAmountCurrency();
          if (localObject4 == null) {
            localObject4 = str6;
          } else if ((!localObject4.equals(str6)) && (!str4.equalsIgnoreCase("del"))) {
            k = 0;
          }
          Object localObject5;
          if ((str4 == null) || (str4.trim().length() == 0))
          {
            localObject1[m].setStatusCode(0);
          }
          else if (str4.equalsIgnoreCase("add"))
          {
            localObject1[m].setBatchId(str2);
            FFSDebug.log("Adding Transfer Template: " + localObject1[m], 6);
            if (a(localObject1[m], false))
            {
              localObject1[m] = jdMethod_if(localFFSConnectionHolder1, localFFSConnectionHolder2, localObject1[m], false, false);
              localBigDecimal2 = new BigDecimal(str5);
              localBigDecimal1 = localBigDecimal1.add(localBigDecimal2);
              i++;
            }
            else if (this.b6 >= 1)
            {
              a(localObject1[m], "Add", null, false);
            }
          }
          else
          {
            Object localObject6;
            if (str4.equalsIgnoreCase("mod"))
            {
              if (localTransferInfo == null) {
                localTransferInfo = new TransferInfo();
              }
              localTransferInfo = getTransferBySrvrTId(localObject1[m].getSrvrTId());
              if (localTransferInfo.getStatusCode() != 0)
              {
                localFFSConnectionHolder1.conn.rollback();
                localFFSConnectionHolder2.conn.rollback();
                localObject5 = new StringBuffer();
                ((StringBuffer)localObject5).append(" failed ").append(localTransferInfo.getStatusMsg());
                ((StringBuffer)localObject5).append(" SrvrTid:").append(localTransferInfo.getSrvrTId());
                FFSDebug.log(str1, ((StringBuffer)localObject5).toString(), 0);
                paramTransferBatchInfo.setStatusCode(localTransferInfo.getStatusCode());
                paramTransferBatchInfo.setStatusMsg(localTransferInfo.getStatusMsg());
                if (this.b6 >= 1) {
                  a(paramTransferBatchInfo, "Mod", null);
                }
                localObject6 = paramTransferBatchInfo;
                return localObject6;
              }
              if (j == 0)
              {
                localBigDecimal2 = new BigDecimal(localTransferInfo.getUserAssignedAmountValue());
                localBigDecimal1 = localBigDecimal1.subtract(localBigDecimal2);
              }
              FFSDebug.log("Modifying Template: " + localObject1[m], 6);
              if (a(localObject1[m], false))
              {
                localObject1[m] = a(localFFSConnectionHolder1, localFFSConnectionHolder2, localObject1[m], localTransferInfo, false, false);
                if ((localObject1[m] == null) || (localObject1[m].getStatusCode() != 0))
                {
                  localFFSConnectionHolder1.conn.rollback();
                  localFFSConnectionHolder2.conn.rollback();
                  localObject5 = new StringBuffer();
                  ((StringBuffer)localObject5).append(": modify batch failed: ").append(localObject1[m].getStatusMsg());
                  ((StringBuffer)localObject5).append(", SrvrTid:").append(localObject1[m].getSrvrTId());
                  FFSDebug.log(str1, ((StringBuffer)localObject5).toString(), 0);
                  paramTransferBatchInfo.setStatusCode(localObject1[m].getStatusCode());
                  paramTransferBatchInfo.setStatusMsg(localObject1[m].getStatusMsg());
                  if (this.b6 >= 1) {
                    a(paramTransferBatchInfo, "Mod", null);
                  }
                  localObject6 = paramTransferBatchInfo;
                  return localObject6;
                }
                localBigDecimal2 = new BigDecimal(str5);
                localBigDecimal1 = localBigDecimal1.add(localBigDecimal2);
              }
              else if (this.b6 >= 1)
              {
                a(localObject1[m], "Add", null, false);
              }
            }
            else if (str4.equalsIgnoreCase("del"))
            {
              if (localTransferInfo == null) {
                localTransferInfo = new TransferInfo();
              }
              localTransferInfo = getTransferBySrvrTId(localObject1[m].getSrvrTId());
              if (localTransferInfo.getStatusCode() != 0)
              {
                localFFSConnectionHolder1.conn.rollback();
                localFFSConnectionHolder2.conn.rollback();
                localObject5 = new StringBuffer();
                ((StringBuffer)localObject5).append(" failed ").append(localTransferInfo.getStatusMsg());
                ((StringBuffer)localObject5).append(" SrvrTid:").append(localTransferInfo.getSrvrTId());
                FFSDebug.log(str1, ((StringBuffer)localObject5).toString(), 0);
                paramTransferBatchInfo.setStatusCode(localTransferInfo.getStatusCode());
                paramTransferBatchInfo.setStatusMsg(localTransferInfo.getStatusMsg());
                if (this.b6 >= 1) {
                  a(paramTransferBatchInfo, "Mod", null);
                }
                localObject6 = paramTransferBatchInfo;
                return localObject6;
              }
              if (j == 0)
              {
                localBigDecimal2 = new BigDecimal(localTransferInfo.getUserAssignedAmountValue());
                localBigDecimal1 = localBigDecimal1.subtract(localBigDecimal2);
              }
              FFSDebug.log("Canceling Wire: " + localObject1[m], 6);
              localObject1[m] = a(localFFSConnectionHolder1, localFFSConnectionHolder2, localObject1[m], false, false);
              i--;
            }
            else
            {
              localObject5 = new StringBuffer();
              ((StringBuffer)localObject5).append("Invalid action.").append(" '");
              ((StringBuffer)localObject5).append(str4).append("' in Transfer template number:" + (m + 1));
              FFSDebug.log(str1, ((StringBuffer)localObject5).toString(), 0);
              paramTransferBatchInfo.setStatusCode(17180);
              localObject6 = BPWLocaleUtil.getMessage(17180, new String[] { "Transfer #:", "" + (m + 1) }, "TRANSFER_MESSAGE");
              paramTransferBatchInfo.setStatusMsg((String)localObject6);
              if (this.b6 >= 1) {
                a(paramTransferBatchInfo, "Mod", null);
              }
              localFFSConnectionHolder1.conn.rollback();
              localFFSConnectionHolder2.conn.rollback();
              TransferBatchInfo localTransferBatchInfo4 = paramTransferBatchInfo;
              return localTransferBatchInfo4;
            }
          }
          if (localObject1[m].getStatusCode() != 0)
          {
            localFFSConnectionHolder1.conn.rollback();
            localFFSConnectionHolder2.conn.rollback();
            paramTransferBatchInfo.setStatusCode(localObject1[m].getStatusCode());
            paramTransferBatchInfo.setStatusMsg(localObject1[m].getStatusMsg());
            if (this.b6 >= 1) {
              a(paramTransferBatchInfo, "Mod", null);
            }
            localObject5 = paramTransferBatchInfo;
            return localObject5;
          }
        }
      }
      if (i == 0)
      {
        localFFSConnectionHolder1.conn.rollback();
        localFFSConnectionHolder2.conn.rollback();
        paramTransferBatchInfo.setStatusCode(19311);
        paramTransferBatchInfo.setStatusMsg("All transfers of the transfer batch are deleted.");
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Mod", null);
        }
        TransferBatchInfo localTransferBatchInfo3 = paramTransferBatchInfo;
        return localTransferBatchInfo3;
      }
      paramTransferBatchInfo.setBatchStatus("MODIFIED");
      paramTransferBatchInfo.setTransferCount(String.valueOf(i));
      if (k != 0)
      {
        paramTransferBatchInfo.setTotalAmount(String.valueOf(localBigDecimal1));
        paramTransferBatchInfo.setAmountCurrency(localObject4);
      }
      else
      {
        paramTransferBatchInfo.setTotalAmount("0.00");
        paramTransferBatchInfo.setAmountCurrency(null);
      }
      paramTransferBatchInfo = TransferBatch.updateTransferBatch(localFFSConnectionHolder1, paramTransferBatchInfo);
      if (this.b6 >= 3) {
        a(localFFSConnectionHolder2, paramTransferBatchInfo, "Mod");
      }
      localFFSConnectionHolder1.conn.commit();
      localFFSConnectionHolder2.conn.commit();
      paramTransferBatchInfo.setStatusCode(0);
      paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "TRANSFER_MESSAGE"));
      FFSDebug.log(str1, " Done", 6);
    }
    catch (Throwable localThrowable1)
    {
      if (localFFSConnectionHolder1 != null) {
        localFFSConnectionHolder1.conn.rollback();
      }
      if (localFFSConnectionHolder2 != null) {
        localFFSConnectionHolder2.conn.rollback();
      }
      if (this.b6 >= 1)
      {
        localObject1 = BPWLocaleUtil.getLocalizableMessage(1049, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
        a(paramTransferBatchInfo, "Mod", (ILocalizable)localObject1);
      }
      Object localObject1 = "*** TransferProcessor.modTransferBatch failed: ";
      String str3 = FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1 + str3, 0);
      throw new FFSException(localThrowable1, (String)localObject1);
    }
    finally
    {
      try
      {
        if (localFFSConnectionHolder1 != null) {
          DBUtil.freeConnection(localFFSConnectionHolder1.conn);
        }
      }
      catch (Throwable localThrowable3)
      {
        FFSDebug.log(str1, " Failed to free connection", 6);
      }
      if (localFFSConnectionHolder2 != null) {
        DBUtil.freeConnection(localFFSConnectionHolder2.conn);
      }
    }
    FFSDebug.log(str1, " end ", 6);
    return paramTransferBatchInfo;
  }
  
  public TransferBatchInfo cancelTransferBatch(TransferBatchInfo paramTransferBatchInfo)
    throws FFSException
  {
    try
    {
      FFSDebug.log("TransferProcessor.cancelTransferBatch start...", 6);
      String str1 = "TransferProcessor.cancelTransferBatch:";
      FFSDebug.log(str1, " Start ", 6);
      localObject1 = null;
      String str2 = null;
      TransferBatchInfo localTransferBatchInfo1 = new TransferBatchInfo();
      Object localObject2;
      if (paramTransferBatchInfo == null)
      {
        paramTransferBatchInfo = new TransferBatchInfo();
        paramTransferBatchInfo.setStatusCode(16000);
        paramTransferBatchInfo.setStatusCode(16010);
        localObject2 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferProcessor", "TransferBatchInfo" }, "TRANSFER_MESSAGE");
        paramTransferBatchInfo.setStatusMsg((String)localObject2);
        return paramTransferBatchInfo;
      }
      if ((paramTransferBatchInfo.getBatchType() != null) && (!paramTransferBatchInfo.getBatchType().equals("TEMPLATE")))
      {
        paramTransferBatchInfo = new TransferBatchInfo();
        paramTransferBatchInfo.setStatusCode(21730);
        paramTransferBatchInfo.setStatusCode(21730);
        localObject2 = BPWLocaleUtil.getMessage(21730, new String[] { paramTransferBatchInfo.getBatchType() }, "TRANSFER_MESSAGE");
        paramTransferBatchInfo.setStatusMsg((String)localObject2);
        return paramTransferBatchInfo;
      }
      str2 = paramTransferBatchInfo.getBatchID();
      if (str2 == null)
      {
        paramTransferBatchInfo.setStatusCode(16010);
        localObject2 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferBatchInfo", "batchId" }, "TRANSFER_MESSAGE");
        paramTransferBatchInfo.setStatusMsg((String)localObject2);
        if (this.b6 >= 1) {
          a(paramTransferBatchInfo, "Can", null);
        }
        return paramTransferBatchInfo;
      }
      try
      {
        localObject1 = new FFSConnectionHolder();
        ((FFSConnectionHolder)localObject1).conn = DBUtil.getConnection();
        if (((FFSConnectionHolder)localObject1).conn == null)
        {
          localObject2 = str1 + "Can not get DB Connection.";
          FFSDebug.log((String)localObject2, 0);
          throw new FFSException((String)localObject2);
        }
        if (Trans.checkDuplicateTIDAndSaveTID(paramTransferBatchInfo.getTrnId()))
        {
          paramTransferBatchInfo.setStatusCode(19220);
          paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(19220, null, "WIRE_MESSAGE"));
          if (this.b6 >= 1) {
            a((FFSConnectionHolder)localObject1, paramTransferBatchInfo, "CanDup");
          }
          ((FFSConnectionHolder)localObject1).conn.commit();
          localObject2 = paramTransferBatchInfo;
          return localObject2;
        }
        localObject2 = paramTransferBatchInfo.getTrnId();
        localTransferBatchInfo1 = getTransferBatchById(paramTransferBatchInfo.getBatchID());
        if (localTransferBatchInfo1.getStatusCode() != 0)
        {
          if (localTransferBatchInfo1.getStatusCode() == 16020)
          {
            FFSDebug.log("***", str1, "failed:", localTransferBatchInfo1.getStatusMsg(), 0);
            localTransferBatchInfo1.setStatusCode(17090);
            localTransferBatchInfo1.setStatusMsg("batch does not exist");
          }
          ((FFSConnectionHolder)localObject1).conn.rollback();
          if (this.b6 >= 1) {
            a(localTransferBatchInfo1, "Can", null);
          }
          localObject3 = localTransferBatchInfo1;
          return localObject3;
        }
        if ((localTransferBatchInfo1.getBatchStatus() != null) && (localTransferBatchInfo1.getBatchStatus().equals("CANCELEDON")))
        {
          paramTransferBatchInfo.setStatusCode(21734);
          paramTransferBatchInfo.setStatusMsg(BPWLocaleUtil.getMessage(21734, null, "TRANSFER_MESSAGE"));
          localObject3 = paramTransferBatchInfo;
          return localObject3;
        }
        localTransferBatchInfo1.setTrnId((String)localObject2);
        localObject3 = localTransferBatchInfo1.getTransfers();
        str3 = null;
        if (localObject3 != null) {
          for (int i = 0; i < localObject3.length; i++)
          {
            localObject3[i] = a((FFSConnectionHolder)localObject1, (FFSConnectionHolder)localObject1, localObject3[i], false, false);
            str3 = localObject3[i].getPrcStatus();
            if (i == 0) {
              localTransferBatchInfo1.setBatchStatus(str3);
            }
            if (localObject3[i].getStatusCode() != 0)
            {
              ((FFSConnectionHolder)localObject1).conn.rollback();
              localTransferBatchInfo1.setBatchStatus(str3);
              localTransferBatchInfo1.setStatusCode(localObject3[i].getStatusCode());
              localTransferBatchInfo1.setStatusMsg(localObject3[i].getStatusMsg());
              if (this.b6 >= 1) {
                a(localTransferBatchInfo1, "Can", null);
              }
              TransferBatchInfo localTransferBatchInfo3 = localTransferBatchInfo1;
              return localTransferBatchInfo3;
            }
            localObject3[i].setPrcStatus("CANCELEDON");
          }
        }
        localTransferBatchInfo1.setTransfers((TransferInfo[])localObject3);
        localTransferBatchInfo1.setBatchStatus("CANCELEDON");
        TransferBatch.updateTransferBatch((FFSConnectionHolder)localObject1, localTransferBatchInfo1);
        if (localTransferBatchInfo1.getStatusCode() != 0)
        {
          ((FFSConnectionHolder)localObject1).conn.rollback();
          if (this.b6 >= 1) {
            a(localTransferBatchInfo1, "Can", null);
          }
          TransferBatchInfo localTransferBatchInfo2 = localTransferBatchInfo1;
          return localTransferBatchInfo2;
        }
        if (this.b6 >= 3) {
          a((FFSConnectionHolder)localObject1, localTransferBatchInfo1, "Can");
        }
        ((FFSConnectionHolder)localObject1).conn.commit();
        localTransferBatchInfo1.setStatusCode(0);
        localTransferBatchInfo1.setStatusMsg(BPWLocaleUtil.getMessage(0, null, "TRANSFER_MESSAGE"));
        FFSDebug.log(str1, " Done", 6);
      }
      catch (Throwable localThrowable2)
      {
        ((FFSConnectionHolder)localObject1).conn.rollback();
        if (this.b6 >= 1)
        {
          localObject3 = BPWLocaleUtil.getLocalizableMessage(1050, null, "EXTERNALTRANSFER_AUDITLOG_MESSAGE");
          a(localTransferBatchInfo1, "Can", (ILocalizable)localObject3);
        }
        Object localObject3 = "*** TransferProcessor.canTransferBatch failed: ";
        String str3 = FFSDebug.stackTrace(localThrowable2);
        FFSDebug.log((String)localObject3, str3, 0);
        throw new FFSException(localThrowable2, (String)localObject3);
      }
      finally
      {
        DBUtil.freeConnection(((FFSConnectionHolder)localObject1).conn);
      }
      return localTransferBatchInfo1;
    }
    catch (Throwable localThrowable1)
    {
      Object localObject1 = "*** cancelTransferBatch:: failed:" + FFSDebug.stackTrace(localThrowable1);
      FFSDebug.log((String)localObject1);
      throw new FFSException((String)localObject1);
    }
  }
  
  public TransferBatchInfo getTransferBatchById(String paramString)
    throws FFSException
  {
    FFSDebug.log("TransferProcessor.getTransferBatchById start...", 6);
    FFSDebug.log("TransferProcessor.TransferProcessor : start ", 6);
    TransferBatchInfo localTransferBatchInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      if (paramString == null)
      {
        localTransferBatchInfo = new TransferBatchInfo();
        localTransferBatchInfo.setStatusCode(16010);
        localObject1 = BPWLocaleUtil.getMessage(16010, new String[] { "TransferBatchInfo", "BatchId" }, "TRANSFER_MESSAGE");
        localTransferBatchInfo.setStatusMsg((String)localObject1);
        localObject2 = localTransferBatchInfo;
        return localObject2;
      }
      localTransferBatchInfo = TransferBatch.getTransferBatchById(localFFSConnectionHolder, paramString, true);
      localFFSConnectionHolder.conn.commit();
      Object localObject1 = localTransferBatchInfo;
      return localObject1;
    }
    catch (Throwable localThrowable)
    {
      Object localObject2 = "*** getTransferBatchById:: failed:" + FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2);
      throw new FFSException((String)localObject2);
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  public AccountTransactions accountHasPendingTransfers(AccountTransactions paramAccountTransactions)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      FFSDebug.log("TransferProcessor.accountHasPendingTransfers start...", 6);
      paramAccountTransactions = a(paramAccountTransactions);
      if (paramAccountTransactions.getStatusCode() != 0)
      {
        localObject1 = paramAccountTransactions;
        return localObject1;
      }
      localFFSConnectionHolder.conn = DBUtil.getDirtyReadConnection();
      Object localObject1 = ExternalTransferAccount.getExtTrnAcctIdByAcctRtnCustStatus(localFFSConnectionHolder, paramAccountTransactions.getSearchForAccount());
      if (localObject1 != null) {
        paramAccountTransactions.getSearchForAccount().setAcctId((String)localObject1);
      }
      localObject2 = null;
      long l = 0L;
      if (paramAccountTransactions.getIncludeTransactions() == 1)
      {
        localObject2 = Transfer.getPendingTransfersByAccount(paramAccountTransactions, false);
        ((ArrayList)localObject2).addAll(Transfer.getPendingTransfersByAccount(paramAccountTransactions, true));
        ((ArrayList)localObject2).addAll(XferInstruction.getPendingTransfersByAccount(paramAccountTransactions));
        ((ArrayList)localObject2).addAll(RecXferInstruction.getPendingTransfersByAccount(paramAccountTransactions));
        paramAccountTransactions.setTotalTransactions(((ArrayList)localObject2).size());
        if (((ArrayList)localObject2).size() > 0) {
          paramAccountTransactions.setTransferInfo((TransferInfo[])((ArrayList)localObject2).toArray(new TransferInfo[((ArrayList)localObject2).size()]));
        }
      }
      else
      {
        l = Transfer.getPendingTransfersCountByAccount(paramAccountTransactions, false);
        l += Transfer.getPendingTransfersCountByAccount(paramAccountTransactions, true);
        l += XferInstruction.getPendingTransfersCountByAccount(paramAccountTransactions);
        l += RecXferInstruction.getPendingTransfersCountByAccount(paramAccountTransactions);
        paramAccountTransactions.setTotalTransactions(l);
      }
      localFFSConnectionHolder.conn.commit();
      AccountTransactions localAccountTransactions = paramAccountTransactions;
      return localAccountTransactions;
    }
    catch (Throwable localThrowable)
    {
      localFFSConnectionHolder.conn.rollback();
      Object localObject2 = "*** TransferProcessor.accountHasPendingTransfers failed: ";
      String str = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log((String)localObject2, str, 0);
      throw new FFSException(localThrowable, (String)localObject2);
    }
    finally
    {
      if (localFFSConnectionHolder.conn != null) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  private AccountTransactions a(AccountTransactions paramAccountTransactions)
    throws FFSException
  {
    try
    {
      FFSDebug.log("TransferProcessor.validateSearchCriteria start...", 6);
      String str1;
      if (paramAccountTransactions == null)
      {
        localObject = new AccountTransactions();
        ((AccountTransactions)localObject).setStatusCode(16000);
        str1 = BPWLocaleUtil.getMessage(16000, new String[] { "AccountTransactions" }, "TRANSFER_MESSAGE");
        ((AccountTransactions)localObject).setStatusMsg(str1);
        return localObject;
      }
      Object localObject = paramAccountTransactions.getSearchForAccount();
      if (localObject == null)
      {
        paramAccountTransactions.setStatusCode(16010);
        str1 = BPWLocaleUtil.getMessage(16010, new String[] { "AccountTransactions", "searchAcct" }, "TRANSFER_MESSAGE");
        paramAccountTransactions.setStatusMsg(str1);
        return paramAccountTransactions;
      }
      int i = 0;
      if ((jdMethod_goto(((ExtTransferAcctInfo)localObject).getAcctNum())) || (jdMethod_goto(((ExtTransferAcctInfo)localObject).getAcctType())) || (jdMethod_goto(((ExtTransferAcctInfo)localObject).getAcctBankRtn())) || (jdMethod_goto(((ExtTransferAcctInfo)localObject).getCustomerId()))) {
        i = 1;
      }
      String str3;
      if ((i != 0) && (jdMethod_goto(((ExtTransferAcctInfo)localObject).getAcctId())))
      {
        paramAccountTransactions.setStatusCode(21731);
        str3 = BPWLocaleUtil.getMessage(21731, null, "TRANSFER_MESSAGE");
        paramAccountTransactions.setStatusMsg(str3);
        return paramAccountTransactions;
      }
      if (jdMethod_goto(paramAccountTransactions.getTransactionType())) {
        paramAccountTransactions.setTransactionType("TRANSFERANDTEMPLATE");
      }
      if (jdMethod_goto(paramAccountTransactions.getAccountScope())) {
        paramAccountTransactions.setAccountScope("FromAccountAndToAccount");
      }
      if (paramAccountTransactions.getIncludeTransactions() != 1) {
        paramAccountTransactions.setIncludeTransactions(0);
      }
      if ((!paramAccountTransactions.getTransactionType().equals("TRANSFER")) && (!paramAccountTransactions.getTransactionType().equals("TEMPLATE")) && (!paramAccountTransactions.getTransactionType().equals("TRANSFERANDTEMPLATE")))
      {
        paramAccountTransactions.setStatusCode(21732);
        str3 = BPWLocaleUtil.getMessage(21732, new String[] { paramAccountTransactions.getTransactionType() }, "TRANSFER_MESSAGE");
        paramAccountTransactions.setStatusMsg(str3);
        return paramAccountTransactions;
      }
      if ((!paramAccountTransactions.getAccountScope().equals("FromAccount")) && (!paramAccountTransactions.getAccountScope().equals("ToAccount")) && (!paramAccountTransactions.getAccountScope().equals("FromAccountAndToAccount")))
      {
        paramAccountTransactions.setStatusCode(21733);
        str3 = BPWLocaleUtil.getMessage(21733, new String[] { paramAccountTransactions.getAccountScope() }, "TRANSFER_MESSAGE");
        paramAccountTransactions.setStatusMsg(str3);
        return paramAccountTransactions;
      }
      paramAccountTransactions.setStatusCode(0);
      paramAccountTransactions.setStatusMsg("Success");
      return paramAccountTransactions;
    }
    catch (Exception localException)
    {
      String str2 = "*** validateSearchCriteria:: failed:" + FFSDebug.stackTrace(localException);
      FFSDebug.log(str2);
      throw new FFSException(str2);
    }
  }
  
  public BankingDays getBankingDaysInRange(BankingDays paramBankingDays, HashMap paramHashMap)
    throws FFSException
  {
    String str = "TransferProcessor.getBankingDaysInRange: ";
    FFSDebug.log(str + "start: CompId= " + paramBankingDays.getCompId() + ", SEC= " + paramBankingDays.getSec() + ", startDate= " + DBUtil.calendarToString(paramBankingDays.getStartDate()) + ", endDate = " + DBUtil.calendarToString(paramBankingDays.getEndDate()), 6);
    TransferInfo localTransferInfo = new TransferInfo();
    localTransferInfo.setFIId(paramBankingDays.getFiID());
    localTransferInfo.setCustomerId(paramBankingDays.getCustomerID());
    localTransferInfo.setDateDue(String.valueOf(BPWUtil.calendarToDueDateFormatInt(paramBankingDays.getStartDate())));
    int i = getValidTransferDueDate(localTransferInfo, paramHashMap);
    Calendar localCalendar1 = null;
    try
    {
      localCalendar1 = BPWUtil.dateIntToCalendar(i, "yyyyMMdd");
    }
    catch (Exception localException1) {}
    int j = (int)((paramBankingDays.getEndDate().getTimeInMillis() - paramBankingDays.getStartDate().getTimeInMillis()) / 86400000L + 1L);
    boolean[] arrayOfBoolean = new boolean[j];
    Calendar localCalendar2 = (Calendar)paramBankingDays.getStartDate().clone();
    Calendar localCalendar3 = paramBankingDays.getEndDate();
    int k = 0;
    while (localCalendar2.before(localCalendar1))
    {
      arrayOfBoolean[k] = false;
      k++;
      localCalendar2.add(5, 1);
    }
    if (localCalendar2.equals(localCalendar1))
    {
      arrayOfBoolean[k] = true;
      k++;
      localCalendar2.add(5, 1);
    }
    BPWFIInfo localBPWFIInfo = (BPWFIInfo)paramHashMap.get("BPW_FIINFO");
    while ((localCalendar2.before(localCalendar3)) || (localCalendar2.equals(localCalendar3)))
    {
      int m = BPWUtil.calendarToDueDateFormatInt(localCalendar2);
      try
      {
        int n = Transfer.getTransferPayday(localBPWFIInfo.getFIId(), m);
        if (m == n) {
          arrayOfBoolean[k] = true;
        } else {
          arrayOfBoolean[k] = false;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log(str + localException2.toString(), 0);
      }
      k++;
      localCalendar2.add(5, 1);
    }
    paramBankingDays.setBankingDays(arrayOfBoolean);
    return paramBankingDays;
  }
  
  private class a
  {
    private int a;
    private ILocalizable jdField_if;
    
    private a() {}
    
    a(TransferProcessor.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.TransferProcessor
 * JD-Core Version:    0.7.0.1
 */