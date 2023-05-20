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
import java.util.Date;

public class ON_USHandler
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
  {}
  
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
    System.out.println("The length of customerpayees in customerpayee add requests: " + paramArrayOfCustomerPayeeInfo.length);
  }
  
  public void modCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    System.out.println("The length of customerpayees in customerpayee mod requests: " + paramArrayOfCustomerPayeeInfo.length);
  }
  
  public void deleteCustomerPayees(CustomerPayeeInfo[] paramArrayOfCustomerPayeeInfo, PayeeInfo[] paramArrayOfPayeeInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    System.out.println("The length of customerpayees in customerpayee cancel requests: " + paramArrayOfCustomerPayeeInfo.length);
  }
  
  public void addPayments(PmtInfo[] paramArrayOfPmtInfo, PayeeRouteInfo[] paramArrayOfPayeeRouteInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    System.out.println("The length of payments: " + paramArrayOfPmtInfo.length);
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(new BufferedWriter(new FileWriter("ON_USPayments.out")), true);
      for (int i = 0; i < paramArrayOfPmtInfo.length; i++)
      {
        Date localDate = new Date();
        localPrintWriter.println(localDate + "Payment SrvrTID = " + paramArrayOfPmtInfo[i].SrvrTID + ", PayeeID = " + paramArrayOfPmtInfo[i].PayeeID + ", Payment amount = " + paramArrayOfPmtInfo[i].getAmt());
      }
      localPrintWriter.close();
    }
    catch (Exception localException)
    {
      throw new Exception("Exception in building ON_US payment out file." + localException.toString());
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.fulfill.ON_USHandler
 * JD-Core Version:    0.7.0.1
 */