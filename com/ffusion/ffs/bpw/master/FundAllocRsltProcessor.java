package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.audit.AuditAgent;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.CustPayeeRoute;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PayeeToRoute;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.fulfill.BillPayHandlerFactory;
import com.ffusion.ffs.bpw.fulfill.FulfillAgent;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.FundsAllocRslt;
import com.ffusion.ffs.bpw.interfaces.IImmediateBillPayHandler;
import com.ffusion.ffs.bpw.interfaces.OFXException;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.scheduling.db.ScheduleInfo;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

public class FundAllocRsltProcessor
  implements BPWResource
{
  public static void ProcessFundAllocRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws Exception
  {
    if ((paramArrayOfFundsAllocRslt == null) || (paramArrayOfFundsAllocRslt.length == 0)) {
      throw new Exception("Invalid fundsAllocInfoRslt is passed: null");
    }
    if (paramArrayOfFundsAllocRslt.length == 0) {
      FFSDebug.log("Empty fund allocation response list");
    }
    int i = 1;
    FFSConnectionHolder localFFSConnectionHolder = null;
    if (paramArrayOfFundsAllocRslt[0].batchKey != null) {
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramArrayOfFundsAllocRslt[0].batchKey);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      i = 0;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      FFSDebug.log("Failed to obtain connection to bpw database....");
      throw new BPWException("Failed to obtain connection to bpw database....");
    }
    try
    {
      for (int j = 0; j < paramArrayOfFundsAllocRslt.length; j++) {
        if (paramArrayOfFundsAllocRslt[j] == null) {
          FFSDebug.log("*** FundAllocRsltProcessor.ProcessFundAllocRslt: null data");
        } else if ((paramArrayOfFundsAllocRslt[j].srvrTid == null) || (paramArrayOfFundsAllocRslt[j].srvrTid == "")) {
          FFSDebug.log("*** FundAllocRsltProcessor.ProcessFundAllocRslt: null or empty srvrTID");
        } else {
          ProcessOneFundAllocRslt(paramArrayOfFundsAllocRslt[j], localFFSConnectionHolder);
        }
      }
      if (i == 0) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to obtain connection to bpw database....");
      throw new Exception("Failed to obtain connection to bpw database...." + localException);
    }
    finally
    {
      if (i == 0) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  public static void ProcessOneFundAllocRslt(FundsAllocRslt paramFundsAllocRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = paramFundsAllocRslt.srvrTid;
    String str2 = jdMethod_case(paramFundsAllocRslt.status);
    PmtInstruction localPmtInstruction = PmtInstruction.getPmtInstr(str1, paramFFSConnectionHolder);
    if (localPmtInstruction == null)
    {
      FFSDebug.log("*** FundAllocRsltProcessor.ProcessOneFundAllocRslt: SrvrTID " + str1 + " not found!");
      FFSDebug.console("*** FundAllocRsltProcessor.ProcessOneFundAllocRslt: SrvrTID " + str1 + " not found!");
      return;
    }
    String str3 = localPmtInstruction.getStatus();
    Object localObject3;
    Object localObject5;
    Object localObject6;
    if (str3.compareTo("CANCELFUNDS") == 0)
    {
      if (str2.equals("FUNDSALLOCATED"))
      {
        PmtInstruction.updateStatus(paramFFSConnectionHolder, str1, "INFUNDSREVERT");
        i = FundsAllocProcessor.revertFundImmediate(localPmtInstruction.getPmtInfo(), paramFundsAllocRslt.batchKey, false, null, paramFFSConnectionHolder);
        if ((i == 0) || (i == 2000))
        {
          localObject1 = new FundsAllocRslt();
          ((FundsAllocRslt)localObject1).status = i;
          ((FundsAllocRslt)localObject1).srvrTid = localPmtInstruction.getSrvrTID();
          ProcessOneFundRevertRslt((FundsAllocRslt)localObject1, paramFFSConnectionHolder);
        }
      }
      int i = PmtInstruction.updateStatus(paramFFSConnectionHolder, str1, "CANCELEDON");
      Object localObject2;
      if (i == 0)
      {
        FFSDebug.log("*** FundAllocRsltProcessor.ProcessOneFundAllocRslt: Payment status is not updated!!  SrvrTID=" + str1 + "Status=" + "CANCELEDON", 0);
        localObject1 = new StringWriter();
        new Exception("Stack Trace").printStackTrace(new PrintWriter((Writer)localObject1));
        localObject2 = ((StringWriter)localObject1).toString();
        FFSDebug.log((String)localObject2, 0);
      }
      jdMethod_else(str1, paramFFSConnectionHolder);
      Object localObject1 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      if (((PropertyConfig)localObject1).LogLevel >= 4)
      {
        localObject2 = localPmtInstruction.getPmtInfo();
        int k = Integer.parseInt(localPmtInstruction.getCustomerID());
        localObject3 = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
        String str4 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
        localObject5 = BPWLocaleUtil.getMessage(704, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString = { localObject5 };
        localObject6 = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), ((PmtInfo)localObject2).submittedBy, null, null, (ILocalizable)localObject6, localPmtInstruction.getPmtInfo().LogID, 4450, k, (BigDecimal)localObject3, localPmtInstruction.getPmtInfo().CurDef, str1, "CANCELEDON", localPmtInstruction.getPayAcct(), null, str4, null, 0);
      }
    }
    else if (str3.compareTo("CANCELEDON") != 0)
    {
      if ((!str3.equals("INFUNDSALLOC")) && (!str3.equals("NOFUNDSON")) && (!str3.equalsIgnoreCase("FUNDSALLOCACTIVE")))
      {
        FFSDebug.log("*** FundAllocRsltProcessor.ProcessOneFundAllocRslt: pmt not in INFUNDSALLOC/NOFUNDSON/FUNDSALLOCACTIVE state, SrvrTID=" + str1 + ",Status=" + str3);
        FFSDebug.console("*** FundAllocRsltProcessor.ProcessOneFundAllocRslt: pmt not in INFUNDSALLOC/NOFUNDSON/FUNDSALLOCACTIVE state, SrvrTID=" + str1 + ",Status=" + str3);
        return;
      }
      PmtInfo localPmtInfo = localPmtInstruction.getPmtInfo();
      boolean bool2;
      if (str2.equals("FUNDSALLOCATED"))
      {
        int j = localPmtInstruction.getRouteID();
        bool2 = false;
        Boolean localBoolean = localPmtInfo.getImmediateProcessing();
        localObject3 = CustPayeeRoute.getActiveCustPayeeRoute(localPmtInfo.CustomerID, localPmtInfo.PayeeListID, paramFFSConnectionHolder);
        if (localObject3 != null) {
          j = ((CustPayeeRoute)localObject3).RouteID;
        }
        localPmtInstruction.setRouteID(j);
        bool2 = localBoolean == null ? false : localBoolean.booleanValue();
        int m = 4450;
        if ((str3.equals("NOFUNDSON")) || (str3.equals("WILLPROCESSON"))) {
          m = 4452;
        }
        localObject5 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        Object localObject7;
        if (((PropertyConfig)localObject5).LogLevel >= 4)
        {
          int n = Integer.parseInt(localPmtInfo.CustomerID);
          localObject6 = FFSUtil.getBigDecimal(localPmtInfo.getAmt());
          String str6 = BPWUtil.getAccountIDWithAccountType(localPmtInfo.AcctDebitID, localPmtInfo.AcctDebitType);
          String str7 = BPWLocaleUtil.getMessage(704, null, "BILLPAY_AUDITLOG_MESSAGE");
          localObject7 = new String[] { str7 };
          LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, (String[])localObject7, "BILLPAY_AUDITLOG_MESSAGE");
          TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), localPmtInfo.submittedBy, null, null, localLocalizableString, localPmtInfo.LogID, m, n, (BigDecimal)localObject6, localPmtInfo.CurDef, str1, str2, localPmtInfo.PayAcct, null, str6, null, 0);
        }
        if (bool2)
        {
          ScheduleInfo.delete(paramFFSConnectionHolder, str1, "RECFUNDTRN");
          localPmtInstruction.setStatus("BATCH_INPROCESS");
          localPmtInstruction.updatePmt(paramFFSConnectionHolder);
          a(localPmtInfo, j, paramFFSConnectionHolder);
        }
        else
        {
          String str5 = jdMethod_else(localPmtInfo.FIID, j);
          localPmtInstruction.setStatus(str2);
          localPmtInstruction.updatePmt(paramFFSConnectionHolder);
          boolean bool4 = ScheduleInfo.checkExist(localPmtInfo.FIID, str5, str1, paramFFSConnectionHolder);
          boolean bool5 = EventInfo.checkExist(paramFFSConnectionHolder, str5, str1, 0);
          boolean bool6 = EventInfoLog.checkExist(paramFFSConnectionHolder, str5, str1, 0);
          if ((!bool4) && (!bool5) && (!bool6))
          {
            localObject7 = new ScheduleInfo();
            ((ScheduleInfo)localObject7).FIId = localPmtInfo.FIID;
            ((ScheduleInfo)localObject7).Status = "Active";
            ((ScheduleInfo)localObject7).Frequency = 10;
            ((ScheduleInfo)localObject7).StartDate = localPmtInfo.StartDate;
            ((ScheduleInfo)localObject7).InstanceCount = 1;
            ((ScheduleInfo)localObject7).LogID = localPmtInfo.LogID;
            ScheduleInfo.createSchedule(paramFFSConnectionHolder, str5, str1, (ScheduleInfo)localObject7);
          }
          else
          {
            FFSDebug.log("*** FundAllocRsltProcessor.ProcessOneFundAllocRslt: duplicate results of fundsallocation SrvrTID=" + str1 + ", no new pmt" + " schedule is created.");
          }
          ScheduleInfo.delete(paramFFSConnectionHolder, str1, "RECFUNDTRN");
        }
      }
      else if (str2.equals("BACKENDPENDING"))
      {
        if (str3.equalsIgnoreCase("FUNDSALLOCACTIVE"))
        {
          localPmtInstruction.setStatus("INFUNDSALLOC");
          localPmtInstruction.updatePmt(paramFFSConnectionHolder);
        }
        ScheduleInfo.delete(paramFFSConnectionHolder, str1, "RECFUNDTRN");
      }
      else if (str2.equals("SCHEDULE"))
      {
        boolean bool1 = ScheduleInfo.checkExist(localPmtInfo.FIID, "FUNDTRN", str1, paramFFSConnectionHolder);
        bool2 = EventInfo.checkExist(paramFFSConnectionHolder, "FUNDTRN", str1, 0);
        boolean bool3 = EventInfoLog.checkExist(paramFFSConnectionHolder, "FUNDTRN", str1, 0);
        Object localObject4;
        if ((!bool1) && (!bool2) && (!bool3))
        {
          localObject3 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
          localObject4 = new ScheduleInfo();
          localObject5 = Integer.toString(localPmtInfo.getStartDate()).substring(0, 8) + "00";
          localPmtInstruction.setStartDate(Integer.parseInt((String)localObject5));
          localPmtInstruction.setStatus("WILLPROCESSON");
          localPmtInstruction.updatePmt(paramFFSConnectionHolder);
          ((ScheduleInfo)localObject4).FIId = localPmtInfo.getFIID();
          ((ScheduleInfo)localObject4).Status = "Active";
          ((ScheduleInfo)localObject4).Frequency = 10;
          try
          {
            ((ScheduleInfo)localObject4).StartDate = Integer.parseInt((String)localObject5);
          }
          catch (Exception localException)
          {
            throw new OFXException("Date format not correct", 2000);
          }
          ((ScheduleInfo)localObject4).InstanceCount = 1;
          ((ScheduleInfo)localObject4).LogID = localPmtInfo.getLogID();
          ((ScheduleInfo)localObject4).CurInstanceNum = 1;
          ScheduleInfo.createSchedule(paramFFSConnectionHolder, "FUNDTRN", str1, (ScheduleInfo)localObject4);
          ((ScheduleInfo)localObject4).Frequency = 11;
          ((ScheduleInfo)localObject4).InstanceCount = ((PropertyConfig)localObject3).getFundsAllocRetries();
          ((ScheduleInfo)localObject4).NextInstanceDate = ((ScheduleInfo)localObject4).StartDate;
          ScheduleInfo.moveNextInstanceDate((ScheduleInfo)localObject4);
          ((ScheduleInfo)localObject4).StartDate = ((ScheduleInfo)localObject4).NextInstanceDate;
          ScheduleInfo.createSchedule(paramFFSConnectionHolder, "RECFUNDTRN", str1, (ScheduleInfo)localObject4);
        }
        else
        {
          localObject3 = new BackendProcessor();
          localObject4 = new PmtTrnRslt();
          ((PmtTrnRslt)localObject4).status = paramFundsAllocRslt.status;
          ((PmtTrnRslt)localObject4).srvrTid = str1;
          ((PmtTrnRslt)localObject4).customerID = paramFundsAllocRslt.customerID;
          ((PmtTrnRslt)localObject4).logID = localPmtInfo.LogID;
          ((BackendProcessor)localObject3).processOneFundsRslt((PmtTrnRslt)localObject4, paramFFSConnectionHolder);
          ScheduleInfo.delete(paramFFSConnectionHolder, str1, "RECFUNDTRN");
        }
      }
      else
      {
        BackendProcessor localBackendProcessor;
        PmtTrnRslt localPmtTrnRslt;
        if (str2.equals("FAILEDON"))
        {
          FundsAllocProcessor.revertLimit(paramFFSConnectionHolder, localPmtInfo, 4451);
          localBackendProcessor = new BackendProcessor();
          localPmtTrnRslt = new PmtTrnRslt();
          localPmtTrnRslt.status = paramFundsAllocRslt.status;
          localPmtTrnRslt.srvrTid = str1;
          localPmtTrnRslt.customerID = paramFundsAllocRslt.customerID;
          localPmtTrnRslt.logID = localPmtInstruction.getPmtInfo().LogID;
          localBackendProcessor.processOneFundsRslt(localPmtTrnRslt, paramFFSConnectionHolder);
          ScheduleInfo.delete(paramFFSConnectionHolder, str1, "RECFUNDTRN");
        }
        else
        {
          localBackendProcessor = new BackendProcessor();
          localPmtTrnRslt = new PmtTrnRslt();
          localPmtTrnRslt.status = paramFundsAllocRslt.status;
          localPmtTrnRslt.srvrTid = str1;
          localPmtTrnRslt.customerID = paramFundsAllocRslt.customerID;
          localPmtTrnRslt.logID = localPmtInfo.LogID;
          localBackendProcessor.processOneFundsRslt(localPmtTrnRslt, paramFFSConnectionHolder);
          if (!str2.equals("NOFUNDSON")) {
            ScheduleInfo.delete(paramFFSConnectionHolder, str1, "RECFUNDTRN");
          }
        }
      }
    }
  }
  
  public static void ProcessOneFundAllocRsltImmediate(FundsAllocRslt paramFundsAllocRslt, PmtInstruction paramPmtInstruction, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "FundAllocRsltProcessor.ProcessOneFundAllocRsltImmediate";
    String str2 = paramFundsAllocRslt.srvrTid;
    String str3 = jdMethod_case(paramFundsAllocRslt.status);
    String str4 = PmtInstruction.getStatus(paramPmtInstruction.getSrvrTID(), paramFFSConnectionHolder);
    PmtInfo localPmtInfo = paramPmtInstruction.getPmtInfo();
    if ((!str4.equalsIgnoreCase("INFUNDSALLOC")) && (!str4.equalsIgnoreCase("NOFUNDSON")) && (!str4.equalsIgnoreCase("FUNDSALLOCACTIVE")))
    {
      FFSDebug.log("*** " + str1 + ": pmt not in INFUNDSALLOC/NOFUNDSON/FUNDSALLOCACTIVE state, SrvrTID=" + str2 + ",Status=" + str4);
      FFSDebug.console("*** " + str1 + ": pmt not in INFUNDSALLOC/NOFUNDSON/FUNDSALLOCACTIVE state, SrvrTID=" + str2 + ",Status=" + str4);
      return;
    }
    Object localObject3;
    if (str3.equals("FUNDSALLOCATED"))
    {
      int i = paramPmtInstruction.getRouteID();
      boolean bool = false;
      localObject3 = localPmtInfo.getImmediateProcessing();
      CustPayeeRoute localCustPayeeRoute = CustPayeeRoute.getActiveCustPayeeRoute(localPmtInfo.CustomerID, localPmtInfo.PayeeListID, paramFFSConnectionHolder);
      if (localCustPayeeRoute != null) {
        i = localCustPayeeRoute.RouteID;
      }
      paramPmtInstruction.setRouteID(i);
      bool = localObject3 == null ? false : ((Boolean)localObject3).booleanValue();
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      Object localObject4;
      if (localPropertyConfig.LogLevel >= 4)
      {
        int j = Integer.parseInt(localPmtInfo.CustomerID);
        localObject4 = FFSUtil.getBigDecimal(localPmtInfo.getAmt());
        String str6 = BPWUtil.getAccountIDWithAccountType(localPmtInfo.AcctDebitID, localPmtInfo.AcctDebitType);
        String str7 = BPWLocaleUtil.getMessage(705, null, "BILLPAY_AUDITLOG_MESSAGE");
        String[] arrayOfString = { str7 };
        LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
        TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), localPmtInfo.submittedBy, null, null, localLocalizableString, localPmtInfo.LogID, 4451, j, (BigDecimal)localObject4, localPmtInfo.CurDef, str2, str3, localPmtInfo.PayAcct, null, str6, null, 0);
      }
      if (bool)
      {
        paramPmtInstruction.setStatus("BATCH_INPROCESS");
        paramPmtInstruction.updatePmt(paramFFSConnectionHolder);
        a(paramPmtInstruction.getPmtInfo(), i, paramFFSConnectionHolder);
      }
      else
      {
        String str5 = jdMethod_else(localPmtInfo.FIID, i);
        paramPmtInstruction.setStatus(str3);
        paramPmtInstruction.updatePmt(paramFFSConnectionHolder);
        localObject4 = new ScheduleInfo();
        ((ScheduleInfo)localObject4).FIId = localPmtInfo.FIID;
        ((ScheduleInfo)localObject4).Status = "Active";
        ((ScheduleInfo)localObject4).Frequency = 10;
        ((ScheduleInfo)localObject4).StartDate = localPmtInfo.StartDate;
        ((ScheduleInfo)localObject4).InstanceCount = 1;
        ((ScheduleInfo)localObject4).LogID = localPmtInfo.LogID;
        ScheduleInfo.createSchedule(paramFFSConnectionHolder, str5, str2, (ScheduleInfo)localObject4);
      }
    }
    else
    {
      Object localObject1;
      Object localObject2;
      if (str3.equals("SCHEDULE"))
      {
        localObject1 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
        localObject2 = new ScheduleInfo();
        localObject3 = Integer.toString(localPmtInfo.getStartDate()).substring(0, 8) + "00";
        paramPmtInstruction.setStartDate(Integer.parseInt((String)localObject3));
        paramPmtInstruction.setStatus("WILLPROCESSON");
        paramPmtInstruction.updatePmt(paramFFSConnectionHolder);
        ((ScheduleInfo)localObject2).FIId = localPmtInfo.getFIID();
        ((ScheduleInfo)localObject2).Status = "Active";
        ((ScheduleInfo)localObject2).Frequency = 10;
        try
        {
          ((ScheduleInfo)localObject2).StartDate = Integer.parseInt((String)localObject3);
        }
        catch (Exception localException)
        {
          throw new OFXException("Date format not correct", 2000);
        }
        ((ScheduleInfo)localObject2).InstanceCount = 1;
        ((ScheduleInfo)localObject2).LogID = localPmtInfo.getLogID();
        ((ScheduleInfo)localObject2).CurInstanceNum = 1;
        ScheduleInfo.createSchedule(paramFFSConnectionHolder, "FUNDTRN", str2, (ScheduleInfo)localObject2);
        ((ScheduleInfo)localObject2).Frequency = 11;
        ((ScheduleInfo)localObject2).InstanceCount = ((PropertyConfig)localObject1).getFundsAllocRetries();
        ((ScheduleInfo)localObject2).NextInstanceDate = ((ScheduleInfo)localObject2).StartDate;
        ScheduleInfo.moveNextInstanceDate((ScheduleInfo)localObject2);
        ((ScheduleInfo)localObject2).StartDate = ((ScheduleInfo)localObject2).NextInstanceDate;
        ScheduleInfo.createSchedule(paramFFSConnectionHolder, "RECFUNDTRN", str2, (ScheduleInfo)localObject2);
      }
      else if (str3.equals("FAILEDON"))
      {
        FundsAllocProcessor.revertLimit(paramFFSConnectionHolder, localPmtInfo, 4451);
        localObject1 = new BackendProcessor();
        localObject2 = new PmtTrnRslt();
        ((PmtTrnRslt)localObject2).status = paramFundsAllocRslt.status;
        ((PmtTrnRslt)localObject2).srvrTid = str2;
        ((PmtTrnRslt)localObject2).customerID = paramFundsAllocRslt.customerID;
        ((PmtTrnRslt)localObject2).logID = paramPmtInstruction.getPmtInfo().LogID;
        ((BackendProcessor)localObject1).processOneFundsRslt((PmtTrnRslt)localObject2, paramFFSConnectionHolder);
      }
      else if (str3.equals("BACKENDPENDING"))
      {
        paramPmtInstruction.setStatus("INFUNDSALLOC");
        paramPmtInstruction.updatePmt(paramFFSConnectionHolder);
      }
      else
      {
        localObject1 = new BackendProcessor();
        localObject2 = new PmtTrnRslt();
        ((PmtTrnRslt)localObject2).status = paramFundsAllocRslt.status;
        ((PmtTrnRslt)localObject2).srvrTid = str2;
        ((PmtTrnRslt)localObject2).customerID = paramFundsAllocRslt.customerID;
        ((PmtTrnRslt)localObject2).logID = paramPmtInstruction.getPmtInfo().LogID;
        ((BackendProcessor)localObject1).processOneFundsRslt((PmtTrnRslt)localObject2, paramFFSConnectionHolder);
      }
    }
  }
  
  public static void ProcessFundRevertRslt(FundsAllocRslt[] paramArrayOfFundsAllocRslt)
    throws Exception
  {
    if ((paramArrayOfFundsAllocRslt == null) || (paramArrayOfFundsAllocRslt.length == 0)) {
      throw new Exception("Invalid fundsAllocInfoRslt is passed: null");
    }
    if (paramArrayOfFundsAllocRslt.length == 0) {
      FFSDebug.log("Empty fund allocation response list");
    }
    int i = 1;
    FFSConnectionHolder localFFSConnectionHolder = null;
    if (paramArrayOfFundsAllocRslt[0].batchKey != null) {
      localFFSConnectionHolder = (FFSConnectionHolder)DBConnCache.lookup(paramArrayOfFundsAllocRslt[0].batchKey);
    }
    if (localFFSConnectionHolder == null)
    {
      localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      i = 0;
    }
    if (localFFSConnectionHolder.conn == null)
    {
      FFSDebug.log("Failed to obtain connection to bpw database....");
      throw new BPWException("Failed to obtain connection to bpw database....");
    }
    try
    {
      for (int j = 0; j < paramArrayOfFundsAllocRslt.length; j++) {
        if (paramArrayOfFundsAllocRslt[j] == null) {
          FFSDebug.log("*** FundAllocRsltProcessor.ProcessFundRevertRslt: null data");
        } else if ((paramArrayOfFundsAllocRslt[j].srvrTid == null) || (paramArrayOfFundsAllocRslt[j].srvrTid == "")) {
          FFSDebug.log("*** FundAllocRsltProcessor.ProcessFundRevertRslt: null or empty srvrTID");
        } else {
          ProcessOneFundRevertRslt(paramArrayOfFundsAllocRslt[j], localFFSConnectionHolder);
        }
      }
      if (i == 0) {
        localFFSConnectionHolder.conn.commit();
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log("Failed to obtain connection to bpw database....");
      throw new Exception("Failed to obtain connection to bpw database...." + localException);
    }
    finally
    {
      if (i == 0) {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
    }
  }
  
  public static void ProcessOneFundRevertRslt(FundsAllocRslt paramFundsAllocRslt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = paramFundsAllocRslt.srvrTid;
    PmtInstruction localPmtInstruction = PmtInstruction.getPmtInstr(str1, paramFFSConnectionHolder);
    if (localPmtInstruction == null)
    {
      FFSDebug.log("*** FundAllocRsltProcessor.ProcessOneFundRevertRslt: SrvrTID " + str1 + " not found!");
      FFSDebug.console("*** FundAllocRsltProcessor.ProcessOneFundRevertRslt: SrvrTID " + str1 + " not found!");
      return;
    }
    String str2 = localPmtInstruction.getStatus();
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    Object localObject;
    if (localPropertyConfig.LogLevel >= 4)
    {
      localObject = localPmtInstruction.getPmtInfo();
      int i = Integer.parseInt(localPmtInstruction.getCustomerID());
      BigDecimal localBigDecimal = FFSUtil.getBigDecimal(localPmtInstruction.getAmt());
      String str3 = BPWUtil.getAccountIDWithAccountType(localPmtInstruction.getAcctDebitID(), localPmtInstruction.getAcctDebitType());
      String str4 = BPWLocaleUtil.getMessage(706, null, "BILLPAY_AUDITLOG_MESSAGE");
      String[] arrayOfString = { str4 };
      LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizedMessage(300, arrayOfString, "BILLPAY_AUDITLOG_MESSAGE");
      TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), ((PmtInfo)localObject).submittedBy, null, null, localLocalizableString, localPmtInstruction.getPmtInfo().LogID, 4453, i, localBigDecimal, localPmtInstruction.getPmtInfo().CurDef, str1, str2, localPmtInstruction.getPayAcct(), null, str3, null, 0);
    }
    if (str2.compareTo("CANCELEDON") != 0)
    {
      if ((!str2.equals("INFUNDSREVERT")) && (!str2.equals("FUNDSREVERTACTIVE")))
      {
        FFSDebug.log("*** FundAllocRsltProcessor.ProcessOneFundRevertRslt: pmt not in INFUNDSREVERT/FUNDSREVERTACTIVE state, SrvrTID=" + str1 + ",Status=" + str2);
        FFSDebug.console("*** FundAllocRsltProcessor.ProcessOneFundRevertRslt: pmt not in INFUNDSREVERT/FUNDSREVERTACTIVE state, SrvrTID=" + str1 + ",Status=" + str2);
        return;
      }
      if ((!str2.equalsIgnoreCase("FUNDSREVERTED")) && (!str2.equalsIgnoreCase("FUNDSREVERTFAILED")))
      {
        localObject = new BackendProcessor();
        PmtTrnRslt localPmtTrnRslt = new PmtTrnRslt();
        localPmtTrnRslt.status = paramFundsAllocRslt.status;
        localPmtTrnRslt.srvrTid = str1;
        localPmtTrnRslt.customerID = localPmtInstruction.getCustomerID();
        localPmtTrnRslt.logID = localPmtInstruction.getPmtInfo().getLogID();
        ((BackendProcessor)localObject).processOneFundsRevertRslt(localPmtTrnRslt, paramFFSConnectionHolder);
      }
    }
  }
  
  private static void a(PmtInfo paramPmtInfo, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    IImmediateBillPayHandler localIImmediateBillPayHandler = BillPayHandlerFactory.getImmediateBillPayHandler(paramInt, 0);
    ArrayList localArrayList = new ArrayList();
    PmtInfo[] arrayOfPmtInfo = null;
    HashMap localHashMap = new HashMap();
    PayeeInfo localPayeeInfo = paramPmtInfo.getPayeeInfo();
    if (localPayeeInfo == null)
    {
      localPayeeInfo = Payee.findPayeeByID(paramPmtInfo.getPayeeID(), paramFFSConnectionHolder);
      if (localPayeeInfo == null) {
        throw new Exception("Payee does not exist: " + paramPmtInfo.getPayeeID());
      }
      paramPmtInfo.setPayeeInfo(localPayeeInfo);
    }
    CustPayeeRoute localCustPayeeRoute = CustPayeeRoute.getActiveCustPayeeRoute(paramPmtInfo.getCustomerID(), paramPmtInfo.getPayeeListID(), paramFFSConnectionHolder);
    if (localCustPayeeRoute == null) {
      throw new Exception("CustPayeeRoute does not exist for payee: " + paramPmtInfo.getPayeeID());
    }
    PayeeRouteInfo localPayeeRouteInfo = PayeeToRoute.getPayeeRoute(paramPmtInfo.getPayeeID(), localCustPayeeRoute.RouteID, paramFFSConnectionHolder);
    if (localPayeeRouteInfo == null) {
      throw new Exception("PayeeRoute does not exist for payee: " + paramPmtInfo.getPayeeID());
    }
    localPayeeInfo.setPayeeRouteInfo(localPayeeRouteInfo);
    localArrayList.add(paramPmtInfo);
    String str1 = DBConnCache.save(paramFFSConnectionHolder);
    FFSDebug.log("Processing bill payment using handler class name: " + localIImmediateBillPayHandler.getClass().getName(), 6);
    arrayOfPmtInfo = localIImmediateBillPayHandler.processBillPayment((PmtInfo[])localArrayList.toArray(new PmtInfo[0]), str1, localHashMap);
    DBConnCache.unbind(str1);
    if ((arrayOfPmtInfo != null) && (arrayOfPmtInfo.length > 0))
    {
      PmtInfo localPmtInfo = arrayOfPmtInfo[0];
      String str2 = localPmtInfo.getStatus();
      FFSDebug.log("Payment result status = " + str2, 6);
      if ((str2.equalsIgnoreCase("PROCESSEDON")) || (str2.equalsIgnoreCase("FAILEDON")) || (str2.equalsIgnoreCase("SCHEDULE")))
      {
        BackendProcessor localBackendProcessor = new BackendProcessor();
        PmtTrnRslt localPmtTrnRslt = new PmtTrnRslt();
        localPmtTrnRslt.status = aD(localPmtInfo.getStatus());
        localPmtTrnRslt.srvrTid = localPmtInfo.getSrvrTID();
        localPmtTrnRslt.customerID = localPmtInfo.getCustomerID();
        localPmtTrnRslt.logID = localPmtInfo.getLogID();
        localBackendProcessor.processOnePmtRslt(localPmtTrnRslt, paramFFSConnectionHolder);
      }
    }
  }
  
  private static int aD(String paramString)
  {
    if (paramString.equalsIgnoreCase("PROCESSEDON")) {
      return 0;
    }
    if (paramString.equalsIgnoreCase("FAILEDON")) {
      return 2000;
    }
    if (paramString.equalsIgnoreCase("BACKENDPENDING")) {
      return 1;
    }
    if (paramString.equalsIgnoreCase("NOFUNDSON")) {
      return 10504;
    }
    if (paramString.equalsIgnoreCase("SCHEDULE")) {
      return 99999;
    }
    if (paramString.equalsIgnoreCase("PROCESSEDON")) {
      return 99998;
    }
    return 2000;
  }
  
  private static String jdMethod_case(int paramInt)
  {
    if (paramInt == 0) {
      return "FUNDSALLOCATED";
    }
    if (paramInt == 1) {
      return "BACKENDPENDING";
    }
    if (paramInt == 10504) {
      return "NOFUNDSON";
    }
    if (paramInt == 2000) {
      return "FAILEDON";
    }
    if (paramInt == 99999) {
      return "SCHEDULE";
    }
    if (paramInt == 99998) {
      return "PROCESSEDON";
    }
    return "FAILEDON";
  }
  
  private static String jdMethod_else(String paramString, int paramInt)
    throws Exception
  {
    FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
    if (localFulfillAgent == null) {
      throw new Exception("FulfillAgent is null!!");
    }
    return localFulfillAgent.findPaymentInstructionType(paramString, paramInt);
  }
  
  private static void jdMethod_else(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    AuditAgent localAuditAgent = (AuditAgent)FFSRegistry.lookup("AUDITAGENT");
    String[] arrayOfString = SrvrTrans.findResponseBySrvrTID(paramString, paramFFSConnectionHolder);
    if (arrayOfString[0] == null) {
      throw new BPWException(" ** Response not found!");
    }
    if (arrayOfString[0].startsWith("OFX151")) {
      localAuditAgent.cancelOFX151PmtRsV1(paramString, paramFFSConnectionHolder);
    } else if (arrayOfString[0].startsWith("OFX200")) {
      localAuditAgent.cancelOFX200PmtRsV1(paramString, paramFFSConnectionHolder);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.FundAllocRsltProcessor
 * JD-Core Version:    0.7.0.1
 */