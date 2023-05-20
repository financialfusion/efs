package com.ffusion.ffs.bpw.fulfill;

import com.ffusion.ffs.bpw.interfaces.CustomerInfo;
import com.ffusion.ffs.bpw.interfaces.CustomerPayeeInfo;
import com.ffusion.ffs.bpw.interfaces.FulfillmentAPI;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.PmtInfo;
import com.ffusion.ffs.db.FFSConnectionHolder;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

public class SampleFulfillmentHandler
  implements FulfillmentAPI
{
  public void start()
    throws Exception
  {}
  
  public void shutdown()
    throws Exception
  {}
  
  public void addPayees(PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    System.out.println("====================================================================================================");
    System.out.println("  Begin of SampleFulfillmentHandler.addPayees  .....");
    System.out.println("  SampleFulfillmentHandler addPayees will communicate with the fulfillment system in backend .");
    for (int i = 0; i < paramArrayOfPayeeInfo.length; i++)
    {
      System.out.println("  ** PayeeInfo :  " + i);
      System.out.println("       PayeeID    =   " + paramArrayOfPayeeInfo[i].PayeeID);
      System.out.println("       PayeeName  =   " + paramArrayOfPayeeInfo[i].PayeeName);
      System.out.println("       Addr1      =   " + paramArrayOfPayeeInfo[i].Addr1);
      System.out.println("       City       =   " + paramArrayOfPayeeInfo[i].City);
      System.out.println("       State      =   " + paramArrayOfPayeeInfo[i].State);
      System.out.println("       Zipcode    =   " + paramArrayOfPayeeInfo[i].Zipcode);
      System.out.println("       Status     =   " + paramArrayOfPayeeInfo[i].Status);
    }
    System.out.println("  End of SampleFulfillmentHandler.addPayees  .....");
    System.out.println("====================================================================================================");
  }
  
  public void modPayees(PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void deletePayees(PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void startPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void endPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void startPmtBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void endPmtBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void startCustomerPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void endCustomerPayeeBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void startCustBatch(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {}
  
  public void endCustBatch(FFSConnectionHolder paramFFSConnectionHolder)
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
  
  public void addCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    System.out.println("====================================================================================================");
    System.out.println("  Begin of SampleFulfillmentHandler.....");
    System.out.println("  SampleFulfillmentHandler addCustomerPayees will communicate with the fulfillment system in backend .");
    System.out.println("  The length of customerpayees in customerpayee add requests: " + paramArrayOfCustomerPayeeInfo.length);
    for (int i = 0; i < paramArrayOfCustomerPayeeInfo.length; i++)
    {
      System.out.println("  -- CustomerPayeeInfo :  " + i);
      System.out.println("        CustomerID  =   " + paramArrayOfCustomerPayeeInfo[i].CustomerID);
      System.out.println("        PayeeID     =   " + paramArrayOfCustomerPayeeInfo[i].PayeeID);
      System.out.println("        PayeeListID =   " + paramArrayOfCustomerPayeeInfo[i].PayeeListID);
      System.out.println("        PayAcct     =   " + paramArrayOfCustomerPayeeInfo[i].PayAcct);
      System.out.println("        Status      =   " + paramArrayOfCustomerPayeeInfo[i].Status);
    }
    for (i = 0; i < paramArrayOfPayeeInfo.length; i++)
    {
      System.out.println("  ** PayeeInfo :  " + i);
      System.out.println("       PayeeID    =   " + paramArrayOfPayeeInfo[i].PayeeID);
      System.out.println("       PayeeName  =   " + paramArrayOfPayeeInfo[i].PayeeName);
      System.out.println("       Addr1      =   " + paramArrayOfPayeeInfo[i].Addr1);
      System.out.println("       City       =   " + paramArrayOfPayeeInfo[i].City);
      System.out.println("       State      =   " + paramArrayOfPayeeInfo[i].State);
      System.out.println("       Zipcode    =   " + paramArrayOfPayeeInfo[i].Zipcode);
      System.out.println("       Status     =   " + paramArrayOfPayeeInfo[i].Status);
    }
    System.out.println("  SampleFulfillmentHandler addCustomerPayees will communicate with the fulfillment system in backend .");
    System.out.println("  End of SampleFulfillmentHandler.....");
    System.out.println("====================================================================================================");
  }
  
  public void modCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    System.out.println("====================================================================================================");
    System.out.println("  Begin of SampleFulfillmentHandler.....");
    System.out.println("  SampleFulfillmentHandler modCustomerPayees will communicate with the fulfillment system in backend .");
    System.out.println("  The length of customerpayees in customerpayee add requests: " + paramArrayOfCustomerPayeeInfo.length);
    for (int i = 0; i < paramArrayOfCustomerPayeeInfo.length; i++)
    {
      System.out.println("  -- CustomerPayeeInfo :  " + i);
      System.out.println("        CustomerID  =   " + paramArrayOfCustomerPayeeInfo[i].CustomerID);
      System.out.println("        PayeeID     =   " + paramArrayOfCustomerPayeeInfo[i].PayeeID);
      System.out.println("        PayeeListID =   " + paramArrayOfCustomerPayeeInfo[i].PayeeListID);
      System.out.println("        PayAcct     =   " + paramArrayOfCustomerPayeeInfo[i].PayAcct);
      System.out.println("        Status      =   " + paramArrayOfCustomerPayeeInfo[i].Status);
    }
    for (i = 0; i < paramArrayOfPayeeInfo.length; i++)
    {
      System.out.println("  ** PayeeInfo :  " + i);
      System.out.println("       PayeeID    =   " + paramArrayOfPayeeInfo[i].PayeeID);
      System.out.println("       PayeeName  =   " + paramArrayOfPayeeInfo[i].PayeeName);
      System.out.println("       Addr1      =   " + paramArrayOfPayeeInfo[i].Addr1);
      System.out.println("       City       =   " + paramArrayOfPayeeInfo[i].City);
      System.out.println("       State      =   " + paramArrayOfPayeeInfo[i].State);
      System.out.println("       Zipcode    =   " + paramArrayOfPayeeInfo[i].Zipcode);
      System.out.println("       Status     =   " + paramArrayOfPayeeInfo[i].Status);
    }
    System.out.println("  SampleFulfillmentHandler modCustomerPayees will communicate with the fulfillment system in backend .");
    System.out.println("  End of SampleFulfillmentHandler.....");
    System.out.println("====================================================================================================");
  }
  
  public void deleteCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    System.out.println("====================================================================================================");
    System.out.println("  Begin of SampleFulfillmentHandler.....");
    System.out.println("  SampleFulfillmentHandler deleteCustomerPayees will communicate with the fulfillment system in backend .");
    System.out.println("  The length of customerpayees in customerpayee add requests: " + paramArrayOfCustomerPayeeInfo.length);
    for (int i = 0; i < paramArrayOfCustomerPayeeInfo.length; i++)
    {
      System.out.println("  -- CustomerPayeeInfo :  " + i);
      System.out.println("        CustomerID  =   " + paramArrayOfCustomerPayeeInfo[i].CustomerID);
      System.out.println("        PayeeID     =   " + paramArrayOfCustomerPayeeInfo[i].PayeeID);
      System.out.println("        PayeeListID =   " + paramArrayOfCustomerPayeeInfo[i].PayeeListID);
      System.out.println("        PayAcct     =   " + paramArrayOfCustomerPayeeInfo[i].PayAcct);
      System.out.println("        Status      =   " + paramArrayOfCustomerPayeeInfo[i].Status);
    }
    for (i = 0; i < paramArrayOfPayeeInfo.length; i++)
    {
      System.out.println("  ** PayeeInfo :  " + i);
      System.out.println("       PayeeID    =   " + paramArrayOfPayeeInfo[i].PayeeID);
      System.out.println("       PayeeName  =   " + paramArrayOfPayeeInfo[i].PayeeName);
      System.out.println("       Addr1      =   " + paramArrayOfPayeeInfo[i].Addr1);
      System.out.println("       City       =   " + paramArrayOfPayeeInfo[i].City);
      System.out.println("       State      =   " + paramArrayOfPayeeInfo[i].State);
      System.out.println("       Zipcode    =   " + paramArrayOfPayeeInfo[i].Zipcode);
      System.out.println("       Status     =   " + paramArrayOfPayeeInfo[i].Status);
    }
    System.out.println("  SampleFulfillmentHandler deleteCustomerPayees will communicate with the fulfillment system in backend .");
    System.out.println("  End of SampleFulfillmentHandler.....");
    System.out.println("====================================================================================================");
  }
  
  public void addPayments(PmtInfo[] paramArrayOfPmtInfo, PayeeRouteInfo[] paramArrayOfPayeeRouteInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    System.out.println("====================================================================================================");
    System.out.println("  Begin of SampleFulfillmentHandler.....");
    System.out.println("  SampleFulfillmentHandler addPayments will communicate with the fulfillment system in backend .");
    System.out.println("  The length of payments: " + paramArrayOfPmtInfo.length);
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter("SampleFulfillment Payments.out")), true);
      for (int i = 0; i < paramArrayOfPmtInfo.length; i++) {
        localPrintWriter.println(paramArrayOfPmtInfo[i].SrvrTID + "|" + paramArrayOfPmtInfo[i].CustomerID + "|" + paramArrayOfPmtInfo[i].Memo);
      }
      localPrintWriter.close();
    }
    catch (Exception localException)
    {
      throw new Exception("Exception in building payment out file in  Sample Fulfillment System." + localException.toString());
    }
    System.out.println("  SampleFulfillmentHandler addPayments will communicate with the fulfillment system in backend .");
    System.out.println("  End of SampleFulfillmentHandler.....");
    System.out.println("====================================================================================================");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.fulfill.SampleFulfillmentHandler
 * JD-Core Version:    0.7.0.1
 */