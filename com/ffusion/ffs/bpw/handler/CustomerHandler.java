package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.db.CustRoute;
import com.ffusion.ffs.bpw.db.Customer;
import com.ffusion.ffs.bpw.fulfill.FulfillAgent;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
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

public class CustomerHandler
  implements DBConsts, FFSConst, BPWResource
{
  public void handleCustomers(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== CustomerHandler.handleCustomers: begin, instructionType=" + paramString2, 6);
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
      Customer.setBatchSize(k);
      localFulfillAgent.startCustomerBatch(paramString1, paramString2, paramFFSConnectionHolder);
      ArrayList localArrayList1 = FulfillAgent.getCustomerCache(i);
      a(paramFFSConnectionHolder, paramString1, paramString2, j, 1, localFulfillAgent, localArrayList1);
      a(paramFFSConnectionHolder, paramString1, paramString2, "NEW", j, localFulfillAgent, localArrayList1);
      ArrayList localArrayList2 = FulfillAgent.getCustomerModCache(i);
      a(paramFFSConnectionHolder, paramString1, paramString2, j, 2, localFulfillAgent, localArrayList2);
      a(paramFFSConnectionHolder, paramString1, paramString2, "MOD", j, localFulfillAgent, localArrayList2);
      ArrayList localArrayList3 = FulfillAgent.getCustomerCancCache(i);
      a(paramFFSConnectionHolder, paramString1, paramString2, j, 3, localFulfillAgent, localArrayList3);
      a(paramFFSConnectionHolder, paramString1, paramString2, "CANC", j, localFulfillAgent, localArrayList3);
      CustomerInfo[] arrayOfCustomerInfo1 = new CustomerInfo[0];
      arrayOfCustomerInfo1 = Customer.findCustomersWithPaymentByStatus("DISABLE", j, paramFFSConnectionHolder);
      FFSDebug.log("CustomerHandler: got deactivated customers: " + arrayOfCustomerInfo1.length, 6);
      int m = arrayOfCustomerInfo1.length;
      while (m != 0)
      {
        for (int n = 0; n < m; n++) {
          CustRoute.updateCustRouteStatus(arrayOfCustomerInfo1[n].customerID, j, "INACTIVE", paramFFSConnectionHolder);
        }
        paramFFSConnectionHolder.conn.commit();
        if (Customer.isBatchDone("DISABLE", j))
        {
          m = 0;
        }
        else
        {
          arrayOfCustomerInfo1 = Customer.findCustomersWithPaymentByStatus("DISABLE", j, paramFFSConnectionHolder);
          m = arrayOfCustomerInfo1.length;
        }
      }
      CustomerInfo[] arrayOfCustomerInfo2 = new CustomerInfo[0];
      arrayOfCustomerInfo2 = Customer.findCustomersWithPaymentByStatus("ENABLE", j, paramFFSConnectionHolder);
      FFSDebug.log("CustomerHandler: got activated customers: " + arrayOfCustomerInfo2.length, 6);
      int i1 = arrayOfCustomerInfo2.length;
      while (i1 != 0)
      {
        for (int i2 = 0; i2 < i1; i2++) {
          CustRoute.updateCustRouteStatus(arrayOfCustomerInfo2[i2].customerID, j, "ACTIVE", paramFFSConnectionHolder);
        }
        paramFFSConnectionHolder.conn.commit();
        if (Customer.isBatchDone("ENABLE", j))
        {
          i1 = 0;
        }
        else
        {
          arrayOfCustomerInfo2 = Customer.findCustomersWithPaymentByStatus("ENABLE", j, paramFFSConnectionHolder);
          i1 = arrayOfCustomerInfo2.length;
        }
      }
      localFulfillAgent.endCustomerBatch(paramString1, paramString2, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      FFSDebug.log("CustomerHandler.handleCustomers failed. Error: " + FFSDebug.stackTrace(localException));
      throw new Exception(localException.toString());
    }
    finally
    {
      Customer.clearBatch("DISABLE", j);
      Customer.clearBatch("ENABLE", j);
    }
    FFSDebug.log("=== CustomerHandler.handleCustomers: end, instructionType=" + paramString2, 6);
  }
  
  public void resubmitCustomers(String paramString1, String paramString2, String paramString3, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== CustomerHandler.resubmitCustomers: begin, instructionType=" + paramString2, 6);
    try
    {
      FulfillAgent localFulfillAgent = (FulfillAgent)FFSRegistry.lookup("FULFILLAGENT");
      if (localFulfillAgent == null) {
        throw new BPWException("FulfillAgent is null!!");
      }
      PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
      int i = localPropertyConfig.getBatchSize();
      Customer.setBatchSize(i);
      int j = FulfillAgent.findFulfillIndex(paramString1, paramString2);
      int k = localFulfillAgent.getRouteID(j);
      localFulfillAgent.startCustomerBatch(paramString1, paramString2, paramFFSConnectionHolder);
      ArrayList localArrayList1 = FulfillAgent.getCustomerCache(j);
      a(paramFFSConnectionHolder, paramString1, paramString2, k, 1, paramString3, localFulfillAgent, localArrayList1);
      ArrayList localArrayList2 = FulfillAgent.getCustomerModCache(j);
      a(paramFFSConnectionHolder, paramString1, paramString2, k, 2, paramString3, localFulfillAgent, localArrayList2);
      ArrayList localArrayList3 = FulfillAgent.getCustomerCancCache(j);
      a(paramFFSConnectionHolder, paramString1, paramString2, k, 3, paramString3, localFulfillAgent, localArrayList3);
      localFulfillAgent.endCustomerBatch(paramString1, paramString2, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      localException = localException;
      FFSDebug.log("CustomerHandler.resubmitCustomers failed. Error: " + FFSDebug.stackTrace(localException));
      throw new Exception(localException.toString());
    }
    finally {}
    FFSDebug.log("=== CustomerHandler.resubmitCustomers: end, instructionType=" + paramString2, 6);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2, FulfillAgent paramFulfillAgent, ArrayList paramArrayList)
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
              localObject1 = Customer.getCustomerByID(arrayOfEventInfo[m].InstructionID, paramFFSConnectionHolder);
              if (localObject1 != null)
              {
                String str = null;
                if (paramInt2 == 1)
                {
                  ((CustomerInfo)localObject1).status = "NEW";
                  str = "INPROCESS";
                }
                else if (paramInt2 == 2)
                {
                  ((CustomerInfo)localObject1).status = "MOD";
                  str = "INPROCESS";
                }
                else if (paramInt2 == 3)
                {
                  ((CustomerInfo)localObject1).status = "CANC";
                  str = "CANC_INPROCESS";
                }
                CustRoute.updateCustRouteStatus(((CustomerInfo)localObject1).customerID, paramInt1, str, paramFFSConnectionHolder);
                paramArrayList.add(localObject1);
              }
              m++;
            }
            localObject1 = (CustomerInfo[])paramArrayList.toArray(new CustomerInfo[0]);
            if (paramInt2 == 1) {
              paramFulfillAgent.addCustomers((CustomerInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
            } else if (paramInt2 == 2) {
              paramFulfillAgent.modCustomers((CustomerInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
            } else if (paramInt2 == 3) {
              paramFulfillAgent.deleteCustomers((CustomerInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
            }
            paramArrayList.clear();
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
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, int paramInt1, int paramInt2, String paramString3, FulfillAgent paramFulfillAgent, ArrayList paramArrayList)
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
            localObject1 = Customer.getCustomerByID(arrayOfEventInfo[j].InstructionID, paramFFSConnectionHolder);
            if (localObject1 != null)
            {
              String str = null;
              if (paramInt2 == 1)
              {
                ((CustomerInfo)localObject1).status = "NEW";
                str = "INPROCESS";
              }
              else if (paramInt2 == 2)
              {
                ((CustomerInfo)localObject1).status = "MOD";
                str = "INPROCESS";
              }
              else if (paramInt2 == 3)
              {
                ((CustomerInfo)localObject1).status = "CANC";
                str = "CANC_INPROCESS";
              }
              CustRoute.updateCustRouteStatus(((CustomerInfo)localObject1).customerID, paramInt1, str, paramFFSConnectionHolder);
              paramArrayList.add(localObject1);
            }
          }
          Object localObject1 = (CustomerInfo[])paramArrayList.toArray(new CustomerInfo[0]);
          if (paramInt2 == 1) {
            paramFulfillAgent.addCustomers((CustomerInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
          } else if (paramInt2 == 2) {
            paramFulfillAgent.modCustomers((CustomerInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
          } else if (paramInt2 == 3) {
            paramFulfillAgent.deleteCustomers((CustomerInfo[])localObject1, paramString1, paramString2, paramFFSConnectionHolder);
          }
          paramArrayList.clear();
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
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, String paramString3, int paramInt, FulfillAgent paramFulfillAgent, ArrayList paramArrayList)
    throws Exception
  {
    try
    {
      int i = 1;
      String str1 = null;
      if (paramString3.equals("NEW"))
      {
        i = 1;
        str1 = "INPROCESS";
      }
      else if (paramString3.equals("MOD"))
      {
        i = 2;
        str1 = "INPROCESS";
      }
      else if (paramString3.equals("CANC"))
      {
        i = 3;
        str1 = "CANC_INPROCESS";
      }
      CustomerInfo[] arrayOfCustomerInfo1 = new CustomerInfo[0];
      arrayOfCustomerInfo1 = Customer.findCustomersWithPaymentByStatus(paramString3, paramInt, paramFFSConnectionHolder);
      FFSDebug.log("CustomerHandler: got " + paramString3 + " customers: " + arrayOfCustomerInfo1.length, 6);
      int j = arrayOfCustomerInfo1.length;
      while (j != 0)
      {
        ArrayList localArrayList = new ArrayList();
        for (int k = 0; k < j; k++)
        {
          CustRoute.updateCustRouteStatus(arrayOfCustomerInfo1[k].customerID, paramInt, str1, paramFFSConnectionHolder);
          String str2 = EventInfo.createEvent(paramFFSConnectionHolder, arrayOfCustomerInfo1[k].customerID, paramString1, paramString2, "Submitted", i, null);
          localArrayList.add(str2);
          paramArrayList.add(arrayOfCustomerInfo1[k]);
        }
        paramFFSConnectionHolder.conn.commit();
        CustomerInfo[] arrayOfCustomerInfo2 = (CustomerInfo[])paramArrayList.toArray(new CustomerInfo[0]);
        if (i == 1) {
          paramFulfillAgent.addCustomers(arrayOfCustomerInfo2, paramString1, paramString2, paramFFSConnectionHolder);
        } else if (i == 2) {
          paramFulfillAgent.modCustomers(arrayOfCustomerInfo2, paramString1, paramString2, paramFFSConnectionHolder);
        } else if (i == 3) {
          paramFulfillAgent.deleteCustomers(arrayOfCustomerInfo2, paramString1, paramString2, paramFFSConnectionHolder);
        }
        paramArrayList.clear();
        for (int m = 0; m < j; m++) {
          EventInfoLog.createEventInfoLog(paramFFSConnectionHolder, (String)localArrayList.get(m), arrayOfCustomerInfo1[m].customerID, paramString1, paramString2, i, null);
        }
        paramFFSConnectionHolder.conn.commit();
        if (Customer.isBatchDone(paramString3, paramInt))
        {
          j = 0;
        }
        else
        {
          arrayOfCustomerInfo1 = Customer.findCustomersWithPaymentByStatus(paramString3, paramInt, paramFFSConnectionHolder);
          j = arrayOfCustomerInfo1.length;
        }
      }
    }
    finally
    {
      Customer.clearBatch(paramString3, paramInt);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.CustomerHandler
 * JD-Core Version:    0.7.0.1
 */