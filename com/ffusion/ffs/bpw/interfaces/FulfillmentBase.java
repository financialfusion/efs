package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.util.FFSDebug;
import java.util.HashMap;

public class FulfillmentBase
  implements FulfillmentAPI
{
  private int ft;
  private Object fr = new Object();
  private boolean fs = false;
  
  public final void lock()
  {
    synchronized (this.fr)
    {
      if (this.fs) {
        try
        {
          this.fr.wait();
        }
        catch (InterruptedException localInterruptedException) {}
      } else {
        this.fs = true;
      }
    }
  }
  
  public final void unlock()
  {
    synchronized (this.fr)
    {
      this.fs = false;
      this.fr.notify();
    }
  }
  
  public void start()
    throws Exception
  {}
  
  public void shutdown()
    throws Exception
  {}
  
  public void startPmtBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "FulfillmentBase.startPmtBatch(dbh) ";
    String str2 = str1 + "Error: This is an obsolete API which uses default FIID 1000.  The new API with FIID as parameter should be used.";
    FFSDebug.log(str2, 0);
    startPmtBatch(paramFFSConnectionHolder, "1000", null);
  }
  
  public void endPmtBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "FulfillmentBase.endPmtBatch(dbh) ";
    String str2 = str1 + "Error: This is an obsolete API which uses default FIID 1000.  The new API with FIID as parameter should be used.";
    FFSDebug.log(str2, 0);
    endPmtBatch(paramFFSConnectionHolder, "1000", null);
  }
  
  public void startPmtBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, HashMap paramHashMap)
    throws Exception
  {}
  
  public void endPmtBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, HashMap paramHashMap)
    throws Exception
  {}
  
  public void addPayments(PmtInfo[] paramArrayOfPmtInfo, PayeeRouteInfo[] paramArrayOfPayeeRouteInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void startCustBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "FulfillmentBase.startCustBatch(dbh) ";
    String str2 = str1 + "Error: This is an obsolete API which uses default FIID 1000.  The new API with FIID as parameter should be used.";
    FFSDebug.log(str2, 0);
    startCustBatch(paramFFSConnectionHolder, "1000", null);
  }
  
  public void endCustBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "FulfillmentBase.endCustBatch(dbh) ";
    String str2 = str1 + "Error: This is an obsolete API which uses default FIID 1000.  The new API with FIID as parameter should be used.";
    FFSDebug.log(str2, 0);
    endCustBatch(paramFFSConnectionHolder, "1000", null);
  }
  
  public void startCustBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, HashMap paramHashMap)
    throws Exception
  {}
  
  public void endCustBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, HashMap paramHashMap)
    throws Exception
  {}
  
  public int addCustomers(CustomerInfo[] paramArrayOfCustomerInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    return 0;
  }
  
  public int modifyCustomers(CustomerInfo[] paramArrayOfCustomerInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    return 0;
  }
  
  public int deleteCustomers(CustomerInfo[] paramArrayOfCustomerInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    return 0;
  }
  
  public void startPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "FulfillmentBase.startPayeeBatch(dbh) ";
    String str2 = str1 + "Error: This is an obsolete API which uses default FIID 1000.  The new API with FIID as parameter should be used.";
    FFSDebug.log(str2, 0);
    startPayeeBatch(paramFFSConnectionHolder, "1000", null);
  }
  
  public void endPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "FulfillmentBase.endPayeeBatch(dbh) ";
    String str2 = str1 + "Error: This is an obsolete API which uses default FIID 1000.  The new API with FIID as parameter should be used.";
    FFSDebug.log(str2, 0);
    endPayeeBatch(paramFFSConnectionHolder, "1000", null);
  }
  
  public void startPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, HashMap paramHashMap)
    throws Exception
  {}
  
  public void endPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, HashMap paramHashMap)
    throws Exception
  {}
  
  public void addPayees(PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void modPayees(PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void deletePayees(PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void startCustomerPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "FulfillmentBase.startCustomerPayeeBatch(dbh) ";
    String str2 = str1 + "Error: This is an obsolete API which uses default FIID 1000.  The new API with FIID as parameter should be used.";
    FFSDebug.log(str2, 0);
    startCustomerPayeeBatch(paramFFSConnectionHolder, "1000", null);
  }
  
  public void endCustomerPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str1 = "FulfillmentBase.endCustomerPayeeBatch(dbh) ";
    String str2 = str1 + "Error: This is an obsolete API which uses default FIID 1000.  The new API with FIID as parameter should be used.";
    FFSDebug.log(str2, 0);
    endCustomerPayeeBatch(paramFFSConnectionHolder, "1000", null);
  }
  
  public void startCustomerPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, HashMap paramHashMap)
    throws Exception
  {}
  
  public void endCustomerPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder, String paramString, HashMap paramHashMap)
    throws Exception
  {}
  
  public void addCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void modCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void deleteCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.FulfillmentBase
 * JD-Core Version:    0.7.0.1
 */