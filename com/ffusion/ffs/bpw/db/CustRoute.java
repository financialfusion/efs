package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.CustomerRouteInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.ArrayList;

public class CustRoute
  implements FFSConst, DBConsts, BPWResource
{
  public static boolean validateCustomer(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    boolean bool = false;
    FFSDebug.log("CustRoute.validateCustomer start, customerID=" + paramString, 6);
    try
    {
      FulfillmentInfo[] arrayOfFulfillmentInfo = Fulfillment.getFulfillmentInfos(paramFFSConnectionHolder);
      for (int i = 0; i < arrayOfFulfillmentInfo.length; i++) {
        if (validateCustomer(paramString, arrayOfFulfillmentInfo[i].RouteID, paramFFSConnectionHolder))
        {
          bool = true;
          break;
        }
      }
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("CustRoute.validateCustomer start, customerID=" + paramString + ",status=" + bool, 6);
    return bool;
  }
  
  public static boolean validateCustomer(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    boolean bool = false;
    FFSDebug.log("CustRoute.validateCustomer start, customerID=" + paramString + ",routeID=" + paramInt, 6);
    try
    {
      CustomerRouteInfo localCustomerRouteInfo = getCustomerRoute(paramString, paramInt, paramFFSConnectionHolder);
      if (localCustomerRouteInfo == null) {
        bool = true;
      } else if ((localCustomerRouteInfo.Status.equals("ACTIVE")) || (localCustomerRouteInfo.Status.equals("NEW")) || (localCustomerRouteInfo.Status.equals("INPROCESS")) || (localCustomerRouteInfo.Status.equals("MOD")) || (localCustomerRouteInfo.Status.equals("ENABLE"))) {
        bool = true;
      }
    }
    catch (Exception localException)
    {
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("CustRoute.validateCustomer start, customerID=" + paramString + ",routeID=" + paramInt + ",status=" + bool, 6);
    return bool;
  }
  
  public static void createAll(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws BPWException
  {
    FFSDebug.log("CustRoute.createAll start, customerID=" + paramString, 6);
    FulfillmentInfo[] arrayOfFulfillmentInfo = Fulfillment.getFulfillmentInfos(paramFFSConnectionHolder);
    for (int i = 0; i < arrayOfFulfillmentInfo.length; i++) {
      create(paramFFSConnectionHolder, paramString, arrayOfFulfillmentInfo[i].RouteID);
    }
    FFSDebug.log("CustRoute.createAll done", 6);
  }
  
  public static int create(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws BPWException
  {
    FFSDebug.log("CustRoute.create start, customerID=" + paramString + ",routeID=" + paramInt, 6);
    Object[] arrayOfObject = { paramString, new Integer(paramInt), "NEW", DBUtil.getCurrentLogDate() };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_CustomerRoute(CustomerID, RouteID, Status, Submitdate) VALUES(?,?,?,?)", arrayOfObject);
    }
    catch (Exception localException)
    {
      String str = "*** CustRoute.create failed:";
      FFSDebug.log(str + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("CustRoute.create done", 6);
    return i;
  }
  
  public static int insert(FFSConnectionHolder paramFFSConnectionHolder, String paramString, int paramInt)
    throws BPWException
  {
    FFSDebug.log("CustRoute.insert start, customerID=" + paramString + ",routeID=" + paramInt, 6);
    Object[] arrayOfObject = { paramString, new Integer(paramInt), "ACTIVE", DBUtil.getCurrentLogDate() };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_CustomerRoute(CustomerID, RouteID, Status, Submitdate) VALUES(?,?,?,?)", arrayOfObject);
    }
    catch (Exception localException)
    {
      String str = "*** CustRoute.insert failed:";
      FFSDebug.log(str + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("CustRoute.insert done", 6);
    return i;
  }
  
  public static CustomerRouteInfo getCustomerRoute(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustRoute.getCustomerRoute start, customerID=" + paramString + " routeID=" + paramInt, 6);
    CustomerRouteInfo localCustomerRouteInfo1 = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      Object[] arrayOfObject = { paramString, new Integer(paramInt) };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "select Status, Submitdate from BPW_CustomerRoute  where CustomerID=? and RouteID=?", arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localCustomerRouteInfo1 = new CustomerRouteInfo();
        localCustomerRouteInfo1.CustomerID = paramString;
        localCustomerRouteInfo1.RouteID = paramInt;
        localCustomerRouteInfo1.Status = localFFSResultSet.getColumnString(1);
        localCustomerRouteInfo1.SubmitDate = localFFSResultSet.getColumnString(2);
      }
      else
      {
        CustomerRouteInfo localCustomerRouteInfo2 = null;
        return localCustomerRouteInfo2;
      }
      if (localFFSResultSet.getNextRow()) {
        throw new Exception("More than one customer having the same customerID: " + paramString);
      }
    }
    catch (Exception localException1)
    {
      throw new BPWException(localException1.toString() + FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** CustRoute.getCustomerRoute failed:" + localException2.toString());
      }
    }
    FFSDebug.log("CustRoute.getCustomerRoute done, customerID=" + paramString + " routeID=" + paramInt, 6);
    return localCustomerRouteInfo1;
  }
  
  public static CustomerRouteInfo[] getAllCustomerRoute(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustRoute.getAllCustomerRoute start, customerID=" + paramString, 6);
    CustomerRouteInfo localCustomerRouteInfo = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      Object[] arrayOfObject = { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "select RouteID, Status, Submitdate from BPW_CustomerRoute  where CustomerID=? ", arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCustomerRouteInfo = new CustomerRouteInfo();
        localCustomerRouteInfo.CustomerID = paramString;
        localCustomerRouteInfo.RouteID = localFFSResultSet.getColumnInt(1);
        localCustomerRouteInfo.Status = localFFSResultSet.getColumnString(2);
        localCustomerRouteInfo.SubmitDate = localFFSResultSet.getColumnString(3);
        localArrayList.add(localCustomerRouteInfo);
      }
    }
    catch (Exception localException1)
    {
      throw new BPWException(localException1.toString() + FFSDebug.stackTrace(localException1));
    }
    finally
    {
      try
      {
        if (localFFSResultSet != null)
        {
          localFFSResultSet.close();
          localFFSResultSet = null;
        }
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** CustRoute.getAllCustomerRoute failed:" + localException2.toString());
      }
    }
    FFSDebug.log("CustRoute.getAllCustomerRoute done, customerID=" + paramString + ",size=" + localArrayList.size(), 6);
    return (CustomerRouteInfo[])localArrayList.toArray(new CustomerRouteInfo[0]);
  }
  
  public static int updateCustRouteStatus(String paramString1, int paramInt, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustRoute.updateCustRouteStatus start, customerID=" + paramString1 + ",routeID=" + paramInt + ",status=" + paramString2, 6);
    Object[] arrayOfObject = { paramString2, paramString1, new Integer(paramInt) };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustomerRoute SET Status = ?  WHERE CustomerID = ? and RouteID = ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustRoute.updateCustRouteStatus failed: " + localException.toString() + FFSDebug.stackTrace(localException));
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("CustRoute.updateCustRouteStatus done, customerID=" + paramString1, 6);
    return i;
  }
  
  public static void updateAllCustRouteStatus(String paramString1, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustRoute.updateAllCustRouteStatus start, customerID=" + paramString1 + ",status=" + paramString2, 6);
    FulfillmentInfo[] arrayOfFulfillmentInfo = Fulfillment.getFulfillmentInfos(paramFFSConnectionHolder);
    for (int i = 0; i < arrayOfFulfillmentInfo.length; i++)
    {
      Object[] arrayOfObject = { paramString2, paramString1, new Integer(arrayOfFulfillmentInfo[i].RouteID) };
      int j = 0;
      try
      {
        j = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustomerRoute SET Status = ?  WHERE CustomerID = ? and RouteID = ?", arrayOfObject);
      }
      catch (Exception localException)
      {
        FFSDebug.log("*** CustRoute.updateAllCustRouteStatus failed: " + localException.toString() + FFSDebug.stackTrace(localException));
        throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
      }
    }
    FFSDebug.log("CustRoute.updateAllCustRouteStatus done, customerID=" + paramString1, 6);
  }
  
  public static int deleteCustRoute(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustRoute.deleteCustRoute start, customerID=" + paramString + ",routeID=" + paramInt, 6);
    Object[] arrayOfObject = { paramString, new Integer(paramInt) };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_CustomerRoute  WHERE CustomerID = ? and RouteID = ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustRoute.deleteCustRoute failed: " + localException.toString() + FFSDebug.stackTrace(localException));
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("CustRoute.deleteCustRoute done, customerID=" + paramString + ",routeID=" + paramInt, 6);
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.CustRoute
 * JD-Core Version:    0.7.0.1
 */