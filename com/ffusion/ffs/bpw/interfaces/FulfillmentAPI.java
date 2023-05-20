package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.db.FFSConnectionHolder;

public abstract interface FulfillmentAPI
{
  public abstract void start()
    throws Exception;
  
  public abstract void startPmtBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void startCustBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract int addCustomers(CustomerInfo[] paramArrayOfCustomerInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract int modifyCustomers(CustomerInfo[] paramArrayOfCustomerInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract int deleteCustomers(CustomerInfo[] paramArrayOfCustomerInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void endCustBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void startPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void addPayees(PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void modPayees(PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void deletePayees(PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void endPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void startCustomerPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void addCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void modCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void deleteCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void endCustomerPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void addPayments(PmtInfo[] paramArrayOfPmtInfo, PayeeRouteInfo[] paramArrayOfPayeeRouteInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void endPmtBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void shutdown()
    throws Exception;
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.FulfillmentAPI
 * JD-Core Version:    0.7.0.1
 */