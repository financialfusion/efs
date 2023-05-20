package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.BPWServer;
import com.ffusion.ffs.bpw.audit.TransAuditLog;
import com.ffusion.ffs.bpw.db.CustPayeeRoute;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PayeeToRoute;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.db.SrvrTrans;
import com.ffusion.ffs.bpw.fulfill.FulfillAgent;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PmtTrnRslt;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.master.BackendProcessor;
import com.ffusion.ffs.bpw.util.BPWLocaleUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.ffs.util.FFSUtil;
import com.ffusion.util.beans.LocalizableString;
import java.math.BigDecimal;
import java.util.ArrayList;

public class PmtHandler
  implements DBConsts, FFSConst, BPWResource
{
  private boolean i2 = true;
  
  public PmtHandler()
  {
    try
    {
      String str = BPWServer.getPropertyValue("bpw.failpayee.tofailpmt", "true");
      this.i2 = (!str.equalsIgnoreCase("false"));
    }
    catch (Throwable localThrowable)
    {
      this.i2 = true;
    }
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder, false);
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws Exception
  {
    FFSDebug.log("=== PmtHandler.eventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType, 6);
    String str1 = paramEventInfoArray._array[0].FIId;
    String str2 = paramEventInfoArray._array[0].InstructionType;
    FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
    if (localFulfillAgent == null) {
      throw new Exception("FulfillAgent is null!!");
    }
    int i = FulfillAgent.findFulfillIndex(str1, str2);
    if (i == -1) {
      throw new Exception("Invalid instruction type: " + str2);
    }
    ArrayList localArrayList1 = FulfillAgent.getPmtCache(i);
    ArrayList localArrayList2 = FulfillAgent.getPayeeRouteCache(i);
    if (paramInt == 0)
    {
      if ((FulfillAgent.getPmtCacheSize(i) > 0) || (FulfillAgent.getPayeeRouteCacheSize(i) > 0)) {
        throw new Exception("Payment cache is not empty! Time interval maybe too short.");
      }
      try
      {
        localFulfillAgent.startPmtBatch(str1, str2, paramFFSConnectionHolder);
      }
      catch (Exception localException1)
      {
        paramFFSConnectionHolder.conn.rollback();
        throw new Exception(localException1.toString());
      }
      try
      {
        CustomerHandler localCustomerHandler = new CustomerHandler();
        localCustomerHandler.handleCustomers(str1, str2, paramFFSConnectionHolder);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("Failed to handle customers. Error: " + FFSDebug.stackTrace(localException2), 0);
        throw new Exception("Failed to handle customers. Error: " + FFSDebug.stackTrace(localException2));
      }
      try
      {
        PayeeHandler localPayeeHandler = new PayeeHandler();
        localPayeeHandler.handlePayees(str1, str2, paramFFSConnectionHolder);
      }
      catch (Exception localException3)
      {
        FFSDebug.log("PmtHandler.eventHandler failed:" + FFSDebug.stackTrace(localException3), 0);
        throw new Exception("PmtHandler.eventHandler failed:" + FFSDebug.stackTrace(localException3));
      }
    }
    else
    {
      Object localObject1;
      if (paramInt == 1) {
        for (int j = 0; j < paramEventInfoArray._array.length; j++)
        {
          localObject1 = paramEventInfoArray._array[j].InstructionID;
          FFSDebug.log("=== PmtHandler.eventHandler: eventSeq=" + paramInt + ",srvrTID=" + (String)localObject1, 6);
          try
          {
            PmtInstruction localPmtInstruction = PmtInstruction.getPmtInstr((String)localObject1, paramFFSConnectionHolder);
            if (localPmtInstruction == null)
            {
              localObject2 = "*** PmtHandler.eventHandler failed: could not find the SrvrTID=" + (String)localObject1 + " in BPW_PmtInstruction";
              FFSDebug.console((String)localObject2);
              FFSDebug.log((String)localObject2);
            }
            else
            {
              localObject2 = localPmtInstruction.getPmtInfo();
              int k = localFulfillAgent.getRouteID(i);
              CustPayeeRoute localCustPayeeRoute = CustPayeeRoute.getCustPayeeRoute2(((PmtInfo)localObject2).CustomerID, ((PmtInfo)localObject2).PayeeListID, k, paramFFSConnectionHolder);
              String str3 = ((PmtInfo)localObject2).Status;
              Object localObject4;
              if ((this.i2 == true) && ((localCustPayeeRoute == null) || (localCustPayeeRoute.Status.equals("FAILEDON") == true) || (localCustPayeeRoute.Status.equals("ERROR") == true)))
              {
                localObject3 = new PmtTrnRslt(((PmtInfo)localObject2).CustomerID, (String)localObject1, 2000, "Invalid payee", ((PmtInfo)localObject2).ExtdPmtInfo);
                ((PmtTrnRslt)localObject3).logID = ((PmtInfo)localObject2).LogID;
                localObject4 = new BackendProcessor();
                ((BackendProcessor)localObject4).processOneFailedPmt((PmtTrnRslt)localObject3, ((PmtInfo)localObject2).LogID, ((PmtInfo)localObject2).FIID, paramFFSConnectionHolder);
              }
              else
              {
                PmtInstruction.updateStatus(paramFFSConnectionHolder, (String)localObject1, "BATCH_INPROCESS");
                str3 = "BATCH_INPROCESS";
                if (paramBoolean) {
                  SrvrTrans.updatePmtStatus(paramFFSConnectionHolder, (String)localObject1, "WILLPROCESSON");
                }
                localObject3 = localPmtInstruction.getPayeeID();
                localObject4 = Payee.findLinkPayeeID((String)localObject3, paramFFSConnectionHolder);
                if (localObject4 != null) {
                  localObject3 = localObject4;
                }
                PayeeRouteInfo localPayeeRouteInfo = PayeeToRoute.getPayeeRoute((String)localObject3, localPmtInstruction.getRouteID(), paramFFSConnectionHolder);
                ((PmtInfo)localObject2).payeeInfo = Payee.findPayeeByID((String)localObject3, paramFFSConnectionHolder);
                if (((PmtInfo)localObject2).payeeInfo == null)
                {
                  String str4 = "*** PmtHandler.eventHandler failed: could not find the PayeeID=" + (String)localObject3 + " in BPW_Payee for pmt of SrvrTID=" + (String)localObject1;
                  FFSDebug.console(str4);
                  FFSDebug.log(str4);
                  continue;
                }
                localArrayList1.add(localObject2);
                localArrayList2.add(localPayeeRouteInfo);
              }
              Object localObject3 = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
              if (((PropertyConfig)localObject3).LogLevel >= 4)
              {
                localObject4 = new Object[1];
                int m = 4455;
                if (paramEventInfoArray._array[0].InstructionType.equals("CHECKFREE_PMTTRN"))
                {
                  localObject4[0] = BPWLocaleUtil.getLocalizableMessage(200, null, "BILLPAY_AUDITLOG_MESSAGE");
                  m = 4456;
                }
                else if (paramEventInfoArray._array[0].InstructionType.equals("METAVANTE_PMTTRN"))
                {
                  localObject4[0] = BPWLocaleUtil.getLocalizableMessage(201, null, "BILLPAY_AUDITLOG_MESSAGE");
                  m = 4458;
                }
                else if (paramEventInfoArray._array[0].InstructionType.equals("ON_US_PMTTRN"))
                {
                  localObject4[0] = BPWLocaleUtil.getLocalizableMessage(202, null, "BILLPAY_AUDITLOG_MESSAGE");
                  m = 4460;
                }
                else if (paramEventInfoArray._array[0].InstructionType.equals("ORCC_PMTTRN"))
                {
                  localObject4[0] = BPWLocaleUtil.getLocalizableMessage(203, null, "BILLPAY_AUDITLOG_MESSAGE");
                  m = 4462;
                }
                else if (paramEventInfoArray._array[0].InstructionType.equals("RPPS_PMTTRN"))
                {
                  localObject4[0] = BPWLocaleUtil.getLocalizableMessage(204, null, "BILLPAY_AUDITLOG_MESSAGE");
                  m = 4464;
                }
                else if (paramEventInfoArray._array[0].InstructionType.equals("BANKSIM_PMTTRN"))
                {
                  localObject4[0] = BPWLocaleUtil.getLocalizableMessage(205, null, "BILLPAY_AUDITLOG_MESSAGE");
                  m = 4466;
                }
                else if (paramEventInfoArray._array[0].InstructionType.equals("SAMPLE_PMTTRN"))
                {
                  localObject4[0] = BPWLocaleUtil.getLocalizableMessage(206, null, "BILLPAY_AUDITLOG_MESSAGE");
                  m = 4468;
                }
                else
                {
                  localObject4[0] = paramEventInfoArray._array[0].InstructionType;
                }
                int n = Integer.parseInt(((PmtInfo)localObject2).CustomerID);
                BigDecimal localBigDecimal = FFSUtil.getBigDecimal(((PmtInfo)localObject2).getAmt());
                String str5 = BPWUtil.getAccountIDWithAccountType(((PmtInfo)localObject2).AcctDebitID, ((PmtInfo)localObject2).AcctDebitType);
                LocalizableString localLocalizableString = BPWLocaleUtil.getLocalizableMessage(823, (Object[])localObject4, "BILLPAY_AUDITLOG_MESSAGE");
                TransAuditLog.logTransAuditLog(paramFFSConnectionHolder.conn.getConnection(), ((PmtInfo)localObject2).submittedBy, null, null, localLocalizableString, ((PmtInfo)localObject2).LogID, m, n, localBigDecimal, ((PmtInfo)localObject2).CurDef, (String)localObject1, str3, ((PmtInfo)localObject2).PayAcct, null, str5, null, 0);
              }
            }
          }
          catch (Exception localException6)
          {
            Object localObject2 = "PmtHandler.eventHandler failed: " + localException6.toString();
            FFSDebug.log((String)localObject2);
            throw new Exception(localException6.toString());
          }
        }
      }
      if (paramInt == 4) {
        try
        {
          PmtInfo[] arrayOfPmtInfo = (PmtInfo[])localArrayList1.toArray(new PmtInfo[0]);
          localObject1 = (PayeeRouteInfo[])localArrayList2.toArray(new PayeeRouteInfo[0]);
          localArrayList1.clear();
          localArrayList2.clear();
          localFulfillAgent.addPayments(arrayOfPmtInfo, (PayeeRouteInfo[])localObject1, str1, str2, paramFFSConnectionHolder);
        }
        catch (Exception localException4)
        {
          FFSDebug.log("*** PmtHandler.eventHandler failed:" + localException4.toString());
          throw new Exception(localException4.toString());
        }
      } else if (paramInt == 2) {
        try
        {
          localFulfillAgent.endPmtBatch(str1, str2, paramFFSConnectionHolder);
        }
        catch (Exception localException5)
        {
          FFSDebug.log("PmtHandler.eventHandler failed:" + FFSDebug.stackTrace(localException5), 0);
          throw new Exception("PmtHandler.eventHandler failed:" + FFSDebug.stackTrace(localException5));
        }
      }
    }
    FFSDebug.log("=== PmtHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== PmtHandler.resubmitEventHandler: begin, eventSeq=" + paramInt + ",length=" + paramEventInfoArray._array.length + ",instructionType=" + paramEventInfoArray._array[0].InstructionType, 6);
    String str1 = paramEventInfoArray._array[0].FIId;
    String str2 = paramEventInfoArray._array[0].InstructionType;
    FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
    if (localFulfillAgent == null) {
      throw new Exception("FulfillAgent is null!!");
    }
    int i = FulfillAgent.findFulfillIndex(str1, str2);
    if (paramInt == 0)
    {
      int j = paramEventInfoArray._array[0].ScheduleFlag;
      if (j == -1)
      {
        try
        {
          localFulfillAgent.startPmtBatch(str1, str2, paramFFSConnectionHolder);
        }
        catch (Exception localException1)
        {
          paramFFSConnectionHolder.conn.rollback();
          throw new Exception(localException1.toString());
        }
        String str3 = paramEventInfoArray._array[0].InstructionID;
        try
        {
          CustomerHandler localCustomerHandler = new CustomerHandler();
          localCustomerHandler.resubmitCustomers(str1, str2, str3, paramFFSConnectionHolder);
        }
        catch (Exception localException2)
        {
          FFSDebug.log("Failed to handle customers. Error: " + FFSDebug.stackTrace(localException2), 0);
          throw new Exception("Failed to handle customers. Error: " + FFSDebug.stackTrace(localException2));
        }
        try
        {
          PayeeHandler localPayeeHandler = new PayeeHandler();
          localPayeeHandler.resubmitPayees(str1, str2, str3, paramFFSConnectionHolder);
        }
        catch (Exception localException3)
        {
          FFSDebug.log("PmtHandler.resubmitEventHandler failed:" + FFSDebug.stackTrace(localException3), 0);
          throw new Exception("PmtHandler.resubmitEventHandler failed:" + FFSDebug.stackTrace(localException3));
        }
      }
      else
      {
        eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder);
      }
    }
    else
    {
      eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder, true);
    }
    FFSDebug.log("=== PmtHandler.resubmitEventHandler: end", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.PmtHandler
 * JD-Core Version:    0.7.0.1
 */