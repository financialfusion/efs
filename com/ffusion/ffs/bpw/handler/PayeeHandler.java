package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.db.CustPayee;
import com.ffusion.ffs.bpw.db.CustPayeeRoute;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.fulfill.FulfillAgent;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoLog;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import java.util.ArrayList;

public class PayeeHandler
  implements DBConsts, FFSConst, BPWResource
{
  public void handlePayees(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== PayeeHandler.handlePayees: begin, instructionType=" + paramString2, 6);
    FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
    if (localFulfillAgent == null) {
      throw new BPWException("FulfillAgent is null!!");
    }
    int i = FulfillAgent.findFulfillIndex(paramString1, paramString2);
    int j = localFulfillAgent.getRouteID(i);
    try
    {
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int k = localPropertyConfig.getBatchSize();
      CustPayee.setBatchSize(k);
      Payee.setBatchSize(k);
      ArrayList localArrayList1 = FulfillAgent.getPayeeCache(i);
      localFulfillAgent.startCustomerPayeeBatch(paramString1, paramString2, paramFFSConnectionHolder);
      ArrayList localArrayList2 = FulfillAgent.getCustomerPayeeCache(i);
      a(paramFFSConnectionHolder, paramString1, paramString2, j, 4, localFulfillAgent, localArrayList2, localArrayList1);
      a(paramFFSConnectionHolder, paramString1, paramString2, "NEW", j, localFulfillAgent, localArrayList2, localArrayList1);
      localArrayList2 = FulfillAgent.getCustomerPayeeModCache(i);
      a(paramFFSConnectionHolder, paramString1, paramString2, j, 5, localFulfillAgent, localArrayList2, localArrayList1);
      a(paramFFSConnectionHolder, paramString1, paramString2, j, 6, localFulfillAgent, localArrayList2, localArrayList1);
      a(paramFFSConnectionHolder, paramString1, paramString2, j, 7, localFulfillAgent, localArrayList2, localArrayList1);
      a(paramFFSConnectionHolder, paramString1, paramString2, "MOD", j, localFulfillAgent, localArrayList2, localArrayList1);
      localArrayList2 = FulfillAgent.getCustomerPayeeCancCache(i);
      a(paramFFSConnectionHolder, paramString1, paramString2, j, 8, localFulfillAgent, localArrayList2, localArrayList1);
      a(paramFFSConnectionHolder, paramString1, paramString2, "CANC", j, localFulfillAgent, localArrayList2, localArrayList1);
      a(paramFFSConnectionHolder, paramString1, paramString2, j, localFulfillAgent, localArrayList2, localArrayList1);
      localFulfillAgent.endCustomerPayeeBatch(paramString1, paramString2, paramFFSConnectionHolder);
      localFulfillAgent.startPayeeBatch(paramString1, paramString2, paramFFSConnectionHolder);
      a(paramFFSConnectionHolder, paramString1, paramString2, 9, localFulfillAgent, localArrayList1);
      a(paramFFSConnectionHolder, paramString1, paramString2, "NEW", localFulfillAgent, localArrayList1);
      localFulfillAgent.endPayeeBatch(paramString1, paramString2, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      localException = localException;
      FFSDebug.log("PayeeHandler.handlePayees failed. Error: " + FFSDebug.stackTrace(localException));
      throw new Exception(localException.toString());
    }
    finally {}
    FFSDebug.log("=== PayeeHandler.handlePayees: end, instructionType=" + paramString2, 6);
  }
  
  public void resubmitPayees(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== PayeeHandler.resubmitPayees: begin, instructionType=" + paramString2 + ",logDate=" + paramString3, 6);
    try
    {
      FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
      if (localFulfillAgent == null) {
        throw new BPWException("FulfillAgent is null!!");
      }
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      CustPayee.setBatchSize(i);
      Payee.setBatchSize(i);
      int j = FulfillAgent.findFulfillIndex(paramString1, paramString2);
      int k = localFulfillAgent.getRouteID(j);
      ArrayList localArrayList1 = FulfillAgent.getPayeeCache(j);
      ArrayList localArrayList2 = FulfillAgent.getCustomerPayeeCache(j);
      localFulfillAgent.startCustomerPayeeBatch(paramString1, paramString2, paramFFSConnectionHolder);
      a(paramFFSConnectionHolder, paramString1, paramString2, k, 4, paramString3, localFulfillAgent, localArrayList2, localArrayList1);
      a(paramFFSConnectionHolder, paramString1, paramString2, k, 5, paramString3, localFulfillAgent, localArrayList2, localArrayList1);
      a(paramFFSConnectionHolder, paramString1, paramString2, k, 6, paramString3, localFulfillAgent, localArrayList2, localArrayList1);
      a(paramFFSConnectionHolder, paramString1, paramString2, k, 7, paramString3, localFulfillAgent, localArrayList2, localArrayList1);
      a(paramFFSConnectionHolder, paramString1, paramString2, k, 8, paramString3, localFulfillAgent, localArrayList2, localArrayList1);
      localFulfillAgent.endCustomerPayeeBatch(paramString1, paramString2, paramFFSConnectionHolder);
      localFulfillAgent.startPayeeBatch(paramString1, paramString2, paramFFSConnectionHolder);
      a(paramFFSConnectionHolder, paramString1, paramString2, 9, paramString3, localFulfillAgent, localArrayList1);
      localFulfillAgent.endPayeeBatch(paramString1, paramString2, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      localException = localException;
      FFSDebug.log("PayeeHandler.resubmitPayees failed. Error: " + FFSDebug.stackTrace(localException));
      throw new Exception(localException.toString());
    }
    finally {}
    FFSDebug.log("=== PayeeHandler.resubmitPayees: end, instructionType=" + paramString2, 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2, FulfillAgent paramFulfillAgent, ArrayList paramArrayList1, ArrayList paramArrayList2)
    throws Exception
  {
    try
    {
      EventInfo[] arrayOfEventInfo = null;
      arrayOfEventInfo = EventInfo.retrieveEventInfo(paramFFSConnectionHolder, "Submitted", paramString1, paramString2, paramInt2);
      if (arrayOfEventInfo != null)
      {
        InstructionType localInstructionType = BPWRegistryUtil.getInstructionType(paramString1, paramString2);
        int i = localInstructionType.FileBasedRecovery;
        while ((arrayOfEventInfo != null) && (arrayOfEventInfo.length > 0))
        {
          int j = 1;
          int k = arrayOfEventInfo.length;
          int m = 0;
          Object localObject1;
          if (i == 0)
          {
            localObject1 = EventInfoLog.getByEventID(paramFFSConnectionHolder, arrayOfEventInfo[(k - 1)].EventID);
            if (localObject1 != null) {
              j = 0;
            }
          }
          if (j != 0)
          {
            while (m < k)
            {
              EventInfoLog.updateEventInfoLog(paramFFSConnectionHolder, arrayOfEventInfo[m].EventID, arrayOfEventInfo[m].InstructionID, arrayOfEventInfo[m].FIId, arrayOfEventInfo[m].InstructionType, arrayOfEventInfo[m].ScheduleFlag, arrayOfEventInfo[m].LogID);
              localObject1 = new CustPayee();
              ((CustPayee)localObject1).findCustPayeeByPayeeListID(arrayOfEventInfo[m].InstructionID, Integer.parseInt(arrayOfEventInfo[m].LogID), paramFFSConnectionHolder);
              localObject2 = Payee.findPayeeByID(((CustPayee)localObject1).getCustPayeeInfo().PayeeID, paramFFSConnectionHolder);
              CustomerPayeeInfo localCustomerPayeeInfo = ((CustPayee)localObject1).getCustPayeeInfo();
              if ((localObject2 != null) && (localCustomerPayeeInfo != null))
              {
                paramArrayList2.add(localObject2);
                String str = "INPROCESS";
                if (arrayOfEventInfo[m].ScheduleFlag == 4)
                {
                  localCustomerPayeeInfo.Status = "NEW";
                  str = "INPROCESS";
                }
                else if (arrayOfEventInfo[m].ScheduleFlag == 5)
                {
                  localCustomerPayeeInfo.Status = "MODACCT";
                  str = "INPROCESS";
                }
                else if (arrayOfEventInfo[m].ScheduleFlag == 6)
                {
                  localCustomerPayeeInfo.Status = "MODPAYEE";
                  str = "INPROCESS";
                }
                else if (arrayOfEventInfo[m].ScheduleFlag == 7)
                {
                  localCustomerPayeeInfo.Status = "MODBOTH";
                  str = "INPROCESS";
                }
                else if (arrayOfEventInfo[m].ScheduleFlag == 8)
                {
                  localCustomerPayeeInfo.Status = "CANC";
                  str = "CANC_INPROCESS";
                }
                CustPayeeRoute.updateCustPayeeRouteStatus(localCustomerPayeeInfo.CustomerID, localCustomerPayeeInfo.PayeeListID, paramInt1, str, paramFFSConnectionHolder);
                paramArrayList1.add(localCustomerPayeeInfo);
              }
              m++;
            }
            localObject1 = (PayeeInfo[])paramArrayList2.toArray(new PayeeInfo[0]);
            Object localObject2 = (CustomerPayeeInfo[])paramArrayList1.toArray(new CustomerPayeeInfo[0]);
            if (paramInt2 == 4) {
              paramFulfillAgent.addCustomerPayees((CustomerPayeeInfo[])localObject2, (PayeeInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
            } else if (paramInt2 == 5) {
              paramFulfillAgent.modCustomerPayees((CustomerPayeeInfo[])localObject2, (PayeeInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
            } else if (paramInt2 == 6) {
              paramFulfillAgent.modCustomerPayees((CustomerPayeeInfo[])localObject2, (PayeeInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
            } else if (paramInt2 == 7) {
              paramFulfillAgent.modCustomerPayees((CustomerPayeeInfo[])localObject2, (PayeeInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
            } else if (paramInt2 == 8) {
              paramFulfillAgent.deleteCustomerPayees((CustomerPayeeInfo[])localObject2, (PayeeInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
            }
            paramArrayList2.clear();
            paramArrayList1.clear();
            paramFFSConnectionHolder.conn.commit();
          }
          if (EventInfo.isBatchDone(paramString1, paramString2)) {
            arrayOfEventInfo = new EventInfo[0];
          } else {
            arrayOfEventInfo = EventInfo.retrieveEventInfo(paramFFSConnectionHolder, "Submitted", paramString1, paramString2, paramInt2);
          }
        }
      }
    }
    finally
    {
      EventInfo.clearBatch(paramString1, paramString2);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, FulfillAgent paramFulfillAgent, ArrayList paramArrayList1, ArrayList paramArrayList2)
    throws Exception
  {
    try
    {
      EventInfo[] arrayOfEventInfo = null;
      arrayOfEventInfo = EventInfoLog.retrieveEventInfoLogs(paramFFSConnectionHolder, paramInt2, paramString1, paramString2, paramString3);
      if (arrayOfEventInfo != null) {
        while ((arrayOfEventInfo != null) && (arrayOfEventInfo.length > 0))
        {
          int i = arrayOfEventInfo.length;
          for (int j = 0; j < i; j++)
          {
            localObject1 = new CustPayee();
            ((CustPayee)localObject1).findCustPayeeByPayeeListID(arrayOfEventInfo[j].InstructionID, Integer.parseInt(arrayOfEventInfo[j].LogID), paramFFSConnectionHolder);
            localObject2 = Payee.findPayeeByID(((CustPayee)localObject1).getCustPayeeInfo().PayeeID, paramFFSConnectionHolder);
            CustomerPayeeInfo localCustomerPayeeInfo = ((CustPayee)localObject1).getCustPayeeInfo();
            if ((localObject2 != null) && (localCustomerPayeeInfo != null))
            {
              paramArrayList2.add(localObject2);
              String str = "INPROCESS";
              if (arrayOfEventInfo[j].ScheduleFlag == 4)
              {
                localCustomerPayeeInfo.Status = "NEW";
                str = "INPROCESS";
              }
              else if (arrayOfEventInfo[j].ScheduleFlag == 5)
              {
                localCustomerPayeeInfo.Status = "MODACCT";
                str = "INPROCESS";
              }
              else if (arrayOfEventInfo[j].ScheduleFlag == 6)
              {
                localCustomerPayeeInfo.Status = "MODPAYEE";
                str = "INPROCESS";
              }
              else if (arrayOfEventInfo[j].ScheduleFlag == 7)
              {
                localCustomerPayeeInfo.Status = "MODBOTH";
                str = "INPROCESS";
              }
              else if (arrayOfEventInfo[j].ScheduleFlag == 8)
              {
                localCustomerPayeeInfo.Status = "CANC";
                str = "CANC_INPROCESS";
              }
              CustPayeeRoute.updateCustPayeeRouteStatus(localCustomerPayeeInfo.CustomerID, localCustomerPayeeInfo.PayeeListID, paramInt1, str, paramFFSConnectionHolder);
              paramArrayList1.add(localCustomerPayeeInfo);
            }
          }
          Object localObject1 = (PayeeInfo[])paramArrayList2.toArray(new PayeeInfo[0]);
          Object localObject2 = (CustomerPayeeInfo[])paramArrayList1.toArray(new CustomerPayeeInfo[0]);
          if (paramInt2 == 4) {
            paramFulfillAgent.addCustomerPayees((CustomerPayeeInfo[])localObject2, (PayeeInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
          } else if (paramInt2 == 5) {
            paramFulfillAgent.modCustomerPayees((CustomerPayeeInfo[])localObject2, (PayeeInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
          } else if (paramInt2 == 6) {
            paramFulfillAgent.modCustomerPayees((CustomerPayeeInfo[])localObject2, (PayeeInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
          } else if (paramInt2 == 7) {
            paramFulfillAgent.modCustomerPayees((CustomerPayeeInfo[])localObject2, (PayeeInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
          } else if (paramInt2 == 8) {
            paramFulfillAgent.deleteCustomerPayees((CustomerPayeeInfo[])localObject2, (PayeeInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
          }
          paramArrayList2.clear();
          paramArrayList1.clear();
          paramFFSConnectionHolder.conn.commit();
          if (EventInfoLog.isBatchDone(paramString1, paramString2)) {
            arrayOfEventInfo = new EventInfo[0];
          } else {
            arrayOfEventInfo = EventInfoLog.retrieveEventInfoLogs(paramFFSConnectionHolder, paramInt2, paramString1, paramString2, paramString3);
          }
        }
      }
    }
    finally
    {
      EventInfoLog.clearBatch(paramString1, paramString2);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, int paramInt, FulfillAgent paramFulfillAgent, ArrayList paramArrayList1, ArrayList paramArrayList2)
    throws Exception
  {
    try
    {
      int i = 4;
      String str1 = "INPROCESS";
      if (paramString3.equals("NEW")) {
        str1 = "INPROCESS";
      } else if (paramString3.equals("MOD")) {
        str1 = "INPROCESS";
      } else if (paramString3.equals("CANC")) {
        str1 = "CANC_INPROCESS";
      }
      CustomerPayeeInfo[] arrayOfCustomerPayeeInfo = CustPayee.findCustPayeesWithPaymentByStatus(paramString3, paramInt, paramFFSConnectionHolder);
      int j = arrayOfCustomerPayeeInfo.length;
      while (j != 0)
      {
        ArrayList localArrayList = new ArrayList();
        paramFFSConnectionHolder.conn.commit();
        for (int k = 0; k < j; k++)
        {
          localObject1 = arrayOfCustomerPayeeInfo[k].PayeeID;
          String str2 = Payee.findLinkPayeeID((String)localObject1, paramFFSConnectionHolder);
          if (str2 != null) {
            localObject1 = str2;
          }
          PayeeInfo localPayeeInfo = Payee.findPayeeByID((String)localObject1, paramFFSConnectionHolder);
          CustPayeeRoute.updateCustPayeeRouteStatus(arrayOfCustomerPayeeInfo[k].CustomerID, arrayOfCustomerPayeeInfo[k].PayeeListID, paramInt, str1, paramFFSConnectionHolder);
          if (arrayOfCustomerPayeeInfo[k].Status.equals("NEW")) {
            i = 4;
          }
          if (arrayOfCustomerPayeeInfo[k].Status.equals("MODACCT")) {
            i = 5;
          } else if (arrayOfCustomerPayeeInfo[k].Status.equals("MODPAYEE")) {
            i = 6;
          } else if (arrayOfCustomerPayeeInfo[k].Status.equals("MODBOTH")) {
            i = 7;
          } else if (arrayOfCustomerPayeeInfo[k].Status.equals("CANC")) {
            i = 8;
          }
          String str3 = EventInfo.createEvent(paramFFSConnectionHolder, arrayOfCustomerPayeeInfo[k].CustomerID, paramString1, paramString2, "Submitted", i, Integer.toString(arrayOfCustomerPayeeInfo[k].PayeeListID));
          localArrayList.add(str3);
          paramArrayList2.add(localPayeeInfo);
          paramArrayList1.add(arrayOfCustomerPayeeInfo[k]);
        }
        paramFFSConnectionHolder.conn.commit();
        PayeeInfo[] arrayOfPayeeInfo = (PayeeInfo[])paramArrayList2.toArray(new PayeeInfo[0]);
        Object localObject1 = (CustomerPayeeInfo[])paramArrayList1.toArray(new CustomerPayeeInfo[0]);
        if (paramString3.equals("NEW")) {
          paramFulfillAgent.addCustomerPayees((CustomerPayeeInfo[])localObject1, arrayOfPayeeInfo, paramString1, paramString2, paramFFSConnectionHolder);
        } else if (paramString3.equals("MOD")) {
          paramFulfillAgent.modCustomerPayees((CustomerPayeeInfo[])localObject1, arrayOfPayeeInfo, paramString1, paramString2, paramFFSConnectionHolder);
        } else if (paramString3.equals("CANC")) {
          paramFulfillAgent.deleteCustomerPayees((CustomerPayeeInfo[])localObject1, arrayOfPayeeInfo, paramString1, paramString2, paramFFSConnectionHolder);
        }
        paramArrayList2.clear();
        paramArrayList1.clear();
        for (int m = 0; m < j; m++)
        {
          if (arrayOfCustomerPayeeInfo[m].Status.equals("NEW")) {
            i = 4;
          }
          if (arrayOfCustomerPayeeInfo[m].Status.equals("MODACCT")) {
            i = 5;
          } else if (arrayOfCustomerPayeeInfo[m].Status.equals("MODPAYEE")) {
            i = 6;
          } else if (arrayOfCustomerPayeeInfo[m].Status.equals("MODBOTH")) {
            i = 7;
          } else if (arrayOfCustomerPayeeInfo[m].Status.equals("CANC")) {
            i = 8;
          }
          EventInfoLog.createEventInfoLog(paramFFSConnectionHolder, (String)localArrayList.get(m), arrayOfCustomerPayeeInfo[m].CustomerID, paramString1, paramString2, i, Integer.toString(arrayOfCustomerPayeeInfo[m].PayeeListID));
        }
        paramFFSConnectionHolder.conn.commit();
        if (CustPayee.isBatchDone(paramString3, paramInt))
        {
          j = 0;
        }
        else
        {
          arrayOfCustomerPayeeInfo = CustPayee.findCustPayeesWithPaymentByStatus(paramString3, paramInt, paramFFSConnectionHolder);
          j = arrayOfCustomerPayeeInfo.length;
        }
      }
    }
    finally
    {
      CustPayee.clearBatch(paramString3, paramInt);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt, FulfillAgent paramFulfillAgent, ArrayList paramArrayList1, ArrayList paramArrayList2)
    throws Exception
  {
    try
    {
      CustomerPayeeInfo[] arrayOfCustomerPayeeInfo = CustPayee.findCustPayeesWithPaymentByStatus("PENDING", paramInt, paramFFSConnectionHolder);
      int i = arrayOfCustomerPayeeInfo.length;
      while (i != 0)
      {
        int j = 0;
        Object localObject1;
        for (int k = 0; k < i; k++)
        {
          localObject1 = Payee.findPayeeByID(arrayOfCustomerPayeeInfo[k].PayeeID, paramFFSConnectionHolder);
          if (!PmtInstruction.hasPendingPmt(arrayOfCustomerPayeeInfo[k].CustomerID, arrayOfCustomerPayeeInfo[k].PayeeListID, paramFFSConnectionHolder))
          {
            CustPayeeRoute.updateCustPayeeRouteStatus(arrayOfCustomerPayeeInfo[k].CustomerID, arrayOfCustomerPayeeInfo[k].PayeeListID, paramInt, "CANC_INPROCESS", paramFFSConnectionHolder);
            String str = EventInfo.createEvent(paramFFSConnectionHolder, arrayOfCustomerPayeeInfo[k].CustomerID, paramString1, paramString2, "Submitted", 8, Integer.toString(arrayOfCustomerPayeeInfo[k].PayeeListID));
            EventInfoLog.createEventInfoLog(paramFFSConnectionHolder, str, arrayOfCustomerPayeeInfo[k].CustomerID, paramString1, paramString2, 8, Integer.toString(arrayOfCustomerPayeeInfo[k].PayeeListID));
            paramArrayList2.add(localObject1);
            paramArrayList1.add(arrayOfCustomerPayeeInfo[k]);
            j = 1;
          }
        }
        if (j != 0)
        {
          PayeeInfo[] arrayOfPayeeInfo = (PayeeInfo[])paramArrayList2.toArray(new PayeeInfo[0]);
          localObject1 = (CustomerPayeeInfo[])paramArrayList1.toArray(new CustomerPayeeInfo[0]);
          paramFulfillAgent.deleteCustomerPayees((CustomerPayeeInfo[])localObject1, arrayOfPayeeInfo, paramString1, paramString2, paramFFSConnectionHolder);
        }
        paramArrayList2.clear();
        paramArrayList1.clear();
        paramFFSConnectionHolder.conn.commit();
        if (CustPayee.isBatchDone("PENDING", paramInt))
        {
          i = 0;
        }
        else
        {
          arrayOfCustomerPayeeInfo = CustPayee.findCustPayeesWithPaymentByStatus("PENDING", paramInt, paramFFSConnectionHolder);
          i = arrayOfCustomerPayeeInfo.length;
        }
      }
    }
    finally
    {
      CustPayee.clearBatch("PENDING", paramInt);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt, FulfillAgent paramFulfillAgent, ArrayList paramArrayList)
    throws Exception
  {
    try
    {
      EventInfo[] arrayOfEventInfo = null;
      arrayOfEventInfo = EventInfo.retrieveEventInfo(paramFFSConnectionHolder, "Submitted", paramString1, paramString2, paramInt);
      if (arrayOfEventInfo != null) {
        while ((arrayOfEventInfo != null) && (arrayOfEventInfo.length > 0))
        {
          int i = arrayOfEventInfo.length;
          for (int j = 0; j < i; j++)
          {
            EventInfoLog.updateEventInfoLog(paramFFSConnectionHolder, arrayOfEventInfo[j].EventID, arrayOfEventInfo[j].InstructionID, arrayOfEventInfo[j].FIId, arrayOfEventInfo[j].InstructionType, arrayOfEventInfo[j].ScheduleFlag, arrayOfEventInfo[j].LogID);
            localObject1 = Payee.findPayeeByID(arrayOfEventInfo[j].InstructionID, paramFFSConnectionHolder);
            if (localObject1 != null)
            {
              ((PayeeInfo)localObject1).Status = "NEW";
              String str = "INPROCESS";
              Payee.updateStatus(((PayeeInfo)localObject1).PayeeID, str, paramFFSConnectionHolder);
              paramArrayList.add(localObject1);
            }
          }
          Object localObject1 = (PayeeInfo[])paramArrayList.toArray(new PayeeInfo[0]);
          paramFulfillAgent.addPayees((PayeeInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
          paramArrayList.clear();
          paramFFSConnectionHolder.conn.commit();
          if (EventInfo.isBatchDone(paramString1, paramString2)) {
            arrayOfEventInfo = new EventInfo[0];
          } else {
            arrayOfEventInfo = EventInfo.retrieveEventInfo(paramFFSConnectionHolder, "Submitted", paramString1, paramString2, paramInt);
          }
        }
      }
    }
    finally
    {
      EventInfo.clearBatch(paramString1, paramString2);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt, String paramString3, FulfillAgent paramFulfillAgent, ArrayList paramArrayList)
    throws Exception
  {
    try
    {
      EventInfo[] arrayOfEventInfo = null;
      arrayOfEventInfo = EventInfoLog.retrieveEventInfoLogs(paramFFSConnectionHolder, paramInt, paramString1, paramString2, paramString3);
      if (arrayOfEventInfo != null) {
        while ((arrayOfEventInfo != null) && (arrayOfEventInfo.length > 0))
        {
          int i = arrayOfEventInfo.length;
          for (int j = 0; j < i; j++)
          {
            localObject1 = Payee.findPayeeByID(arrayOfEventInfo[j].InstructionID, paramFFSConnectionHolder);
            if (localObject1 != null)
            {
              ((PayeeInfo)localObject1).Status = "NEW";
              String str = "INPROCESS";
              Payee.updateStatus(((PayeeInfo)localObject1).PayeeID, str, paramFFSConnectionHolder);
              paramArrayList.add(localObject1);
            }
          }
          Object localObject1 = (PayeeInfo[])paramArrayList.toArray(new PayeeInfo[0]);
          paramFulfillAgent.addPayees((PayeeInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
          paramArrayList.clear();
          paramFFSConnectionHolder.conn.commit();
          if (EventInfoLog.isBatchDone(paramString1, paramString2)) {
            arrayOfEventInfo = new EventInfo[0];
          } else {
            arrayOfEventInfo = EventInfoLog.retrieveEventInfoLogs(paramFFSConnectionHolder, paramInt, paramString1, paramString2, paramString3);
          }
        }
      }
    }
    finally
    {
      EventInfoLog.clearBatch(paramString1, paramString2);
    }
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, FulfillAgent paramFulfillAgent, ArrayList paramArrayList)
    throws Exception
  {
    try
    {
      int i = 9;
      String str1 = "INPROCESS";
      if (paramString3.equals("NEW"))
      {
        str1 = "INPROCESS";
        i = 9;
      }
      PayeeInfo[] arrayOfPayeeInfo1 = Payee.findPayeesByStatus(paramString3, paramFFSConnectionHolder);
      int j = arrayOfPayeeInfo1.length;
      while (j != 0)
      {
        ArrayList localArrayList = new ArrayList();
        for (int k = 0; k < j; k++)
        {
          Payee.updateStatus(arrayOfPayeeInfo1[k].PayeeID, str1, paramFFSConnectionHolder);
          String str2 = EventInfo.createEvent(paramFFSConnectionHolder, arrayOfPayeeInfo1[k].PayeeID, paramString1, paramString2, "Submitted", i, null);
          localArrayList.add(str2);
          paramArrayList.add(arrayOfPayeeInfo1[k]);
        }
        paramFFSConnectionHolder.conn.commit();
        PayeeInfo[] arrayOfPayeeInfo2 = (PayeeInfo[])paramArrayList.toArray(new PayeeInfo[0]);
        paramFulfillAgent.addPayees(arrayOfPayeeInfo2, paramString1, paramString2, paramFFSConnectionHolder);
        paramArrayList.clear();
        for (int m = 0; m < j; m++) {
          EventInfoLog.createEventInfoLog(paramFFSConnectionHolder, (String)localArrayList.get(m), arrayOfPayeeInfo1[m].PayeeID, paramString1, paramString2, i, null);
        }
        paramFFSConnectionHolder.conn.commit();
        if (Payee.isBatchDone(paramString3))
        {
          j = 0;
        }
        else
        {
          arrayOfPayeeInfo1 = Payee.findPayeesByStatus(paramString3, paramFFSConnectionHolder);
          j = arrayOfPayeeInfo1.length;
        }
      }
    }
    finally
    {
      Payee.clearBatch(paramString3);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.PayeeHandler
 * JD-Core Version:    0.7.0.1
 */