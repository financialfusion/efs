package com.ffusion.ffs.bpw.fulfill;

import com.ffusion.ffs.bpw.db.Fulfillment;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.FulfillmentAPI;
import com.ffusion.ffs.bpw.interfaces.FulfillmentBase;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWRegistryUtil;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.ArrayList;

public class FulfillAgent
  implements FFSConst
{
  private static FulfillmentInfo[] jdField_long;
  private static int jdField_try;
  private static ArrayList[] jdField_for = null;
  private static ArrayList[] jdField_else = null;
  private static ArrayList[] jdField_if = null;
  private static ArrayList[] jdField_char = null;
  private static ArrayList[] a = null;
  private static ArrayList[] jdField_byte = null;
  private static ArrayList[] jdField_goto = null;
  private static ArrayList[] jdField_do = null;
  private static ArrayList[] jdField_int = null;
  private static FulfillmentAPI[] jdField_new = null;
  private static int jdField_case;
  
  public FulfillAgent()
    throws FFSException
  {
    jdField_long = Fulfillment.getFulfillmentInfos();
    jdField_case = BPWUtil.getPropertyConfig().BatchSize;
    if (jdField_case <= 0) {
      jdField_case = 1000;
    }
    if ((jdField_long != null) && (jdField_long.length > 0))
    {
      int i = jdField_long.length;
      jdField_new = new FulfillmentAPI[i];
      jdField_try = jdField_long[0].RouteID;
      double d = jdField_long[0].PaymentCost;
      jdField_for = new ArrayList[i];
      jdField_else = new ArrayList[i];
      jdField_if = new ArrayList[i];
      jdField_char = new ArrayList[i];
      a = new ArrayList[i];
      jdField_goto = new ArrayList[i];
      jdField_do = new ArrayList[i];
      jdField_int = new ArrayList[i];
      jdField_byte = new ArrayList[i];
      for (int j = 1; j < i; j++) {
        if (jdField_long[j].PaymentCost < d)
        {
          d = jdField_long[j].PaymentCost;
          jdField_try = jdField_long[j].RouteID;
        }
      }
      FFSDebug.log("Default routeID set to " + jdField_try, 6);
    }
  }
  
  public void start()
    throws BPWException
  {
    if (jdField_long == null) {
      throw new BPWException("No fulfillment system for payment!");
    }
    for (int i = 0; i < jdField_long.length; i++) {
      jdField_if(i);
    }
  }
  
  private void jdField_if(int paramInt)
    throws BPWException
  {
    try
    {
      String str = jdField_long[paramInt].HandlerName;
      Class localClass = Class.forName(str);
      FulfillmentAPI localFulfillmentAPI = (FulfillmentAPI)localClass.newInstance();
      jdField_new[paramInt] = localFulfillmentAPI;
      localFulfillmentAPI.start();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** An error occurred when starting FulfillmentAgent:Failed to start a handler. Handler class=" + jdField_long[paramInt].HandlerName);
    }
  }
  
  public void stop()
  {
    if (jdField_long == null) {
      return;
    }
    int i = jdField_long.length;
    for (int j = 0; j < i; j++)
    {
      try
      {
        if (jdField_new[j] != null) {
          jdField_new[j].shutdown();
        }
      }
      catch (Exception localException)
      {
        FFSDebug.log(localException, "*** FulfillAgent.stop encountered error trying to shutdown a handler:\n");
      }
      if (jdField_for[j] != null) {
        a(j);
      }
    }
    jdField_long = null;
    jdField_new = null;
    jdField_for = null;
    jdField_else = null;
    jdField_if = null;
    jdField_char = null;
    a = null;
    jdField_byte = null;
  }
  
  private static void a(int paramInt)
  {
    jdField_for[paramInt].clear();
    jdField_else[paramInt].clear();
    jdField_if[paramInt].clear();
    jdField_char[paramInt].clear();
    a[paramInt].clear();
    jdField_byte[paramInt].clear();
    jdField_for[paramInt] = null;
    jdField_else[paramInt] = null;
    jdField_if[paramInt] = null;
    jdField_char[paramInt] = null;
    a[paramInt] = null;
    jdField_byte[paramInt] = null;
  }
  
  public String findPaymentInstructionType(String paramString, int paramInt)
  {
    return BPWRegistryUtil.findPaymentInstructionType(paramString, paramInt);
  }
  
  public int getDefaultFulfillment()
  {
    return jdField_try;
  }
  
  public int getRouteID(int paramInt)
  {
    return jdField_long[paramInt].RouteID;
  }
  
  public void startBatch() {}
  
  public static int findFulfillIndex(String paramString1, String paramString2)
  {
    int i = BPWRegistryUtil.findFulfillmentRouteID(paramString1, paramString2);
    for (int j = 0; j < jdField_long.length; j++) {
      if (jdField_long[j].RouteID == i) {
        return j;
      }
    }
    return -1;
  }
  
  public void startPmtBatch(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    int i = findFulfillIndex(paramString1, paramString2);
    if (jdField_new[i] == null) {
      jdField_if(i);
    }
    jdField_for[i] = new ArrayList(jdField_case);
    jdField_if[i] = new ArrayList(jdField_case);
    jdField_char[i] = new ArrayList(jdField_case);
    a[i] = new ArrayList(jdField_case);
    jdField_else[i] = new ArrayList(jdField_case);
    jdField_goto[i] = new ArrayList(jdField_case);
    jdField_do[i] = new ArrayList(jdField_case);
    jdField_int[i] = new ArrayList(jdField_case);
    jdField_byte[i] = new ArrayList(jdField_case);
    try
    {
      if ((jdField_new[i] instanceof FulfillmentBase)) {
        ((FulfillmentBase)jdField_new[i]).startPmtBatch(paramFFSConnectionHolder, paramString1, null);
      } else {
        jdField_new[i].startPmtBatch(paramFFSConnectionHolder);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.startPmtBatch failed:");
      throw new BPWException(localException.toString());
    }
  }
  
  public void endPmtBatch(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    int i = findFulfillIndex(paramString1, paramString2);
    try
    {
      if ((jdField_new[i] instanceof FulfillmentBase)) {
        ((FulfillmentBase)jdField_new[i]).endPmtBatch(paramFFSConnectionHolder, paramString1, null);
      } else {
        jdField_new[i].endPmtBatch(paramFFSConnectionHolder);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.endPmtBatch failed:");
      throw new BPWException(localException.toString());
    }
    finally
    {
      a(i);
    }
  }
  
  public void startPayeeBatch(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    int i = findFulfillIndex(paramString1, paramString2);
    jdField_else[i] = a();
    try
    {
      if ((jdField_new[i] instanceof FulfillmentBase)) {
        ((FulfillmentBase)jdField_new[i]).startPayeeBatch(paramFFSConnectionHolder, paramString1, null);
      } else {
        jdField_new[i].startPayeeBatch(paramFFSConnectionHolder);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.startPayeeBatch failed:");
      throw new BPWException(localException.toString());
    }
  }
  
  public void endPayeeBatch(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    int i = findFulfillIndex(paramString1, paramString2);
    try
    {
      if ((jdField_new[i] instanceof FulfillmentBase)) {
        ((FulfillmentBase)jdField_new[i]).endPayeeBatch(paramFFSConnectionHolder, paramString1, null);
      } else {
        jdField_new[i].endPayeeBatch(paramFFSConnectionHolder);
      }
    }
    catch (Exception localException)
    {
      localException = localException;
      FFSDebug.log(localException, "*** FulfillAgent.endPayeeBatch failed:");
      throw new BPWException(localException.toString());
    }
    finally {}
  }
  
  public void addPayees(PayeeInfo[] paramArrayOfPayeeInfo, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    int i = findFulfillIndex(paramString1, paramString2);
    try
    {
      jdField_new[i].addPayees(paramArrayOfPayeeInfo, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.addPayees failed:");
      throw new BPWException(localException.toString());
    }
  }
  
  public void startCustomerPayeeBatch(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    int i = findFulfillIndex(paramString1, paramString2);
    jdField_if[i] = a();
    jdField_char[i] = a();
    a[i] = a();
    try
    {
      if ((jdField_new[i] instanceof FulfillmentBase)) {
        ((FulfillmentBase)jdField_new[i]).startCustomerPayeeBatch(paramFFSConnectionHolder, paramString1, null);
      } else {
        jdField_new[i].startCustomerPayeeBatch(paramFFSConnectionHolder);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.startCustomerPayeeBatch failed:");
      throw new BPWException(localException.toString());
    }
  }
  
  public void endCustomerPayeeBatch(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    int i = findFulfillIndex(paramString1, paramString2);
    try
    {
      if ((jdField_new[i] instanceof FulfillmentBase)) {
        ((FulfillmentBase)jdField_new[i]).endCustomerPayeeBatch(paramFFSConnectionHolder, paramString1, null);
      } else {
        jdField_new[i].endCustomerPayeeBatch(paramFFSConnectionHolder);
      }
    }
    catch (Exception localException)
    {
      localException = localException;
      FFSDebug.log(localException, "*** FulfillAgent.endCustomerPayeeBatch failed:");
      throw new BPWException(localException.toString());
    }
    finally {}
  }
  
  public void addCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    int i = findFulfillIndex(paramString1, paramString2);
    try
    {
      jdField_new[i].addCustomerPayees(paramArrayOfCustomerPayeeInfo, paramArrayOfPayeeInfo, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.addCustomerPayees failed:");
      throw new BPWException(localException.toString());
    }
  }
  
  public void modCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    int i = findFulfillIndex(paramString1, paramString2);
    try
    {
      jdField_new[i].modCustomerPayees(paramArrayOfCustomerPayeeInfo, paramArrayOfPayeeInfo, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.modCustomerPayees failed:");
      throw new BPWException(localException.toString());
    }
  }
  
  public void deleteCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    int i = findFulfillIndex(paramString1, paramString2);
    try
    {
      jdField_new[i].deleteCustomerPayees(paramArrayOfCustomerPayeeInfo, paramArrayOfPayeeInfo, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.deleteCustomerPayees failed:");
      throw new BPWException(localException.toString());
    }
  }
  
  public void startCustomerBatch(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    int i = findFulfillIndex(paramString1, paramString2);
    jdField_goto[i] = a();
    jdField_do[i] = a();
    jdField_int[i] = a();
    try
    {
      if ((jdField_new[i] instanceof FulfillmentBase)) {
        ((FulfillmentBase)jdField_new[i]).startCustBatch(paramFFSConnectionHolder, paramString1, null);
      } else {
        jdField_new[i].startCustBatch(paramFFSConnectionHolder);
      }
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.startCustomer failed:");
      throw new BPWException(localException.toString());
    }
  }
  
  public void endCustomerBatch(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    int i = findFulfillIndex(paramString1, paramString2);
    try
    {
      if ((jdField_new[i] instanceof FulfillmentBase)) {
        ((FulfillmentBase)jdField_new[i]).endCustBatch(paramFFSConnectionHolder, paramString1, null);
      } else {
        jdField_new[i].endCustBatch(paramFFSConnectionHolder);
      }
    }
    catch (Exception localException)
    {
      localException = localException;
      FFSDebug.log(localException, "*** FulfillAgent.endCustomerAddBatch failed:", 0);
      throw new BPWException(localException.toString());
    }
    finally {}
  }
  
  public void addCustomers(CustomerInfo[] paramArrayOfCustomerInfo, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log(" FulfillAgent.addCustomers start", 6);
    try
    {
      int i = findFulfillIndex(paramString1, paramString2);
      jdField_new[i].addCustomers(paramArrayOfCustomerInfo, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.addCustomers failed:");
      throw new BPWException(localException.toString());
    }
    FFSDebug.log(" FulfillAgent.addCustomers end", 6);
  }
  
  public void modCustomers(CustomerInfo[] paramArrayOfCustomerInfo, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log(" FulfillAgent.modCustomers start", 6);
    try
    {
      int i = findFulfillIndex(paramString1, paramString2);
      jdField_new[i].modifyCustomers(paramArrayOfCustomerInfo, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.modCustomer failed:");
      throw new BPWException(localException.toString());
    }
    FFSDebug.log(" FulfillAgent.modCustomers end", 6);
  }
  
  public void deleteCustomers(CustomerInfo[] paramArrayOfCustomerInfo, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log(" FulfillAgent.deleteCustomers start", 6);
    try
    {
      int i = findFulfillIndex(paramString1, paramString2);
      jdField_new[i].deleteCustomers(paramArrayOfCustomerInfo, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.deleteCustomers failed:");
      throw new BPWException(localException.toString());
    }
    FFSDebug.log(" FulfillAgent.deleteCustomers end", 6);
  }
  
  public void addPayments(PmtInfo[] paramArrayOfPmtInfo, PayeeRouteInfo[] paramArrayOfPayeeRouteInfo, String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log(" FulfillAgent.addPayments start", 6);
    try
    {
      int i = findFulfillIndex(paramString1, paramString2);
      jdField_new[i].addPayments(paramArrayOfPmtInfo, paramArrayOfPayeeRouteInfo, paramFFSConnectionHolder);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "*** FulfillAgent.addPayments failed:");
      throw new BPWException(localException.toString());
    }
    FFSDebug.log(" FulfillAgent.addPayments end", 6);
  }
  
  private static final ArrayList a()
  {
    return new ArrayList(jdField_case);
  }
  
  public static ArrayList getCustomerCache(int paramInt)
  {
    return jdField_goto[paramInt];
  }
  
  public static ArrayList getCustomerModCache(int paramInt)
  {
    return jdField_goto[paramInt];
  }
  
  public static ArrayList getCustomerCancCache(int paramInt)
  {
    return jdField_goto[paramInt];
  }
  
  public static ArrayList getPmtCache(int paramInt)
  {
    return jdField_for[paramInt];
  }
  
  public static ArrayList getPayeeCache(int paramInt)
  {
    return jdField_else[paramInt];
  }
  
  public static ArrayList getCustomerPayeeCache(int paramInt)
  {
    return jdField_if[paramInt];
  }
  
  public static ArrayList getCustomerPayeeModCache(int paramInt)
  {
    return jdField_char[paramInt];
  }
  
  public static ArrayList getCustomerPayeeCancCache(int paramInt)
  {
    return jdField_char[paramInt];
  }
  
  public static ArrayList getPayeeRouteCache(int paramInt)
  {
    return jdField_byte[paramInt];
  }
  
  public static int getCustomerCacheSize(int paramInt)
  {
    return jdField_goto[paramInt] == null ? 0 : jdField_goto[paramInt].size();
  }
  
  public static int getCustomerModCacheSize(int paramInt)
  {
    return jdField_do[paramInt] == null ? 0 : jdField_goto[paramInt].size();
  }
  
  public static int getCustomerCancCacheSize(int paramInt)
  {
    return jdField_int[paramInt] == null ? 0 : jdField_goto[paramInt].size();
  }
  
  public static int getPmtCacheSize(int paramInt)
  {
    return jdField_for[paramInt] == null ? 0 : jdField_for[paramInt].size();
  }
  
  public static int getPayeeCacheSize(int paramInt)
  {
    return jdField_else == null ? 0 : jdField_else[paramInt].size();
  }
  
  public static int getCustomerPayeeCacheSize(int paramInt)
  {
    return jdField_if[paramInt] == null ? 0 : jdField_if[paramInt].size();
  }
  
  public static int getCustomerPayeeModCacheSize(int paramInt)
  {
    return jdField_char[paramInt] == null ? 0 : jdField_char[paramInt].size();
  }
  
  public static int getCustomerPayeeCancCacheSize(int paramInt)
  {
    return a[paramInt] == null ? 0 : a[paramInt].size();
  }
  
  public static int getPayeeRouteCacheSize(int paramInt)
  {
    return jdField_byte[paramInt] == null ? 0 : jdField_byte[paramInt].size();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.fulfill.FulfillAgent
 * JD-Core Version:    0.7.0.1
 */