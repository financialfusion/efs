package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.ArrayList;

public class CustPayeeRoute
  implements FFSConst, DBConsts, BPWResource
{
  public String CustPayeeRouteID;
  public String CustomerID;
  public int PayeeListID;
  public int RouteID;
  public String Status;
  public String Submitdate;
  
  public static void addCustPayeeRoute(String paramString, int paramInt1, int paramInt2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayeeRoute.addCustPayeeRoute start, customerID=" + paramString + ",payeeListID=" + paramInt1 + ",routeID=" + paramInt2, 6);
    String str1 = DBUtil.getNextIndexString("CustPayeeRouteID");
    Object[] arrayOfObject = { str1, paramString, new Integer(paramInt1), new Integer(paramInt2), "NEW", DBUtil.getCurrentLogDate() };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_CustPayeeRoute(CustPayeeRouteID,CustomerID, PayeeListID, RouteID, Status, Submitdate) VALUES(?,?,?,?,?,?)", arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** CustPayeeRoute.addCustPayeeRoute failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("CustPayeeRoute.addCustPayeeRoute done", 6);
  }
  
  public static void insertCustPayeeRoute(String paramString, int paramInt1, int paramInt2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayeeRoute.insertCustPayeeRoute start, customerID=" + paramString + ",payeeListID=" + paramInt1 + ",routeID=" + paramInt2, 6);
    String str1 = DBUtil.getNextIndexString("CustPayeeRouteID");
    Object[] arrayOfObject = { str1, paramString, new Integer(paramInt1), new Integer(paramInt2), "ACTIVE", DBUtil.getCurrentLogDate() };
    int i = -1;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "INSERT INTO BPW_CustPayeeRoute(CustPayeeRouteID,CustomerID, PayeeListID, RouteID, Status, Submitdate) VALUES(?,?,?,?,?,?)", arrayOfObject);
    }
    catch (Exception localException)
    {
      String str2 = "*** CustPayeeRoute.insertCustPayeeRoute failed:";
      FFSDebug.log(str2 + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("CustPayeeRoute.insertCustPayeeRoute done", 6);
  }
  
  public static CustPayeeRoute getActiveCustPayeeRoute(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayeeRoute.getActiveCustPayeeRoute start, customerID=" + paramString + ",payeeListID=" + paramInt, 6);
    CustPayeeRoute localCustPayeeRoute = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      Object[] arrayOfObject = { paramString, new Integer(paramInt) };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustPayeeRouteID, RouteID, Status, Submitdate from BPW_CustPayeeRoute  WHERE CustomerID = ? AND PayeeListID = ?", arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCustPayeeRoute = new CustPayeeRoute();
        localCustPayeeRoute.CustomerID = paramString;
        localCustPayeeRoute.PayeeListID = paramInt;
        localCustPayeeRoute.CustPayeeRouteID = localFFSResultSet.getColumnString(1);
        localCustPayeeRoute.RouteID = localFFSResultSet.getColumnInt(2);
        localCustPayeeRoute.Status = localFFSResultSet.getColumnString(3);
        localCustPayeeRoute.Submitdate = localFFSResultSet.getColumnString(4);
        if ((localCustPayeeRoute.Status.equals("ACTIVE")) || (localCustPayeeRoute.Status.equals("INPROCESS")) || (localCustPayeeRoute.Status.indexOf("MOD") != -1)) {
          break;
        }
        if ((localCustPayeeRoute.Status.indexOf("CANC") != -1) || (!localCustPayeeRoute.Status.equals("CLOSED"))) {}
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
        FFSDebug.log("*** CustPayeeRoute.getActiveCustPayeeRoute failed:" + localException2.toString());
      }
    }
    if (localCustPayeeRoute != null) {
      FFSDebug.log("CustPayeeRoute.getActiveCustPayeeRoute done, customerID=" + paramString + ",payeeListID=" + paramInt + ",routeID=" + localCustPayeeRoute.RouteID + ",Status=" + localCustPayeeRoute.Status, 6);
    }
    return localCustPayeeRoute;
  }
  
  public static CustPayeeRoute getCustPayeeRoute2(String paramString, int paramInt1, int paramInt2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayeeRoute.getCustPayeeRoute2 start, customerID=" + paramString + ",payeeListID=" + paramInt1, 6);
    CustPayeeRoute localCustPayeeRoute = null;
    FFSResultSet localFFSResultSet = null;
    try
    {
      Object[] arrayOfObject = { paramString, new Integer(paramInt1), new Integer(paramInt2) };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustPayeeRouteID, Status, Submitdate from BPW_CustPayeeRoute  WHERE CustomerID = ? AND PayeeListID = ? AND RouteID = ?", arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCustPayeeRoute = new CustPayeeRoute();
        localCustPayeeRoute.CustomerID = paramString;
        localCustPayeeRoute.PayeeListID = paramInt1;
        localCustPayeeRoute.CustPayeeRouteID = localFFSResultSet.getColumnString(1);
        localCustPayeeRoute.RouteID = paramInt2;
        localCustPayeeRoute.Status = localFFSResultSet.getColumnString(2);
        localCustPayeeRoute.Submitdate = localFFSResultSet.getColumnString(3);
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
        FFSDebug.log("*** CustPayeeRoute.getCustPayeeRoute2 failed:" + localException2.toString());
      }
    }
    if (localCustPayeeRoute != null) {
      FFSDebug.log("CustPayeeRoute.getCustPayeeRoute2 done, customerID=" + paramString + ",payeeListID=" + paramInt1 + ",routeID=" + localCustPayeeRoute.RouteID + ",Status=" + localCustPayeeRoute.Status, 6);
    }
    return localCustPayeeRoute;
  }
  
  public static CustPayeeRoute[] getCustPayeeRoute(String paramString, int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayeeRoute.getCustPayeeRoute start, customerID=" + paramString + ",payeeListID=" + paramInt, 6);
    CustPayeeRoute localCustPayeeRoute = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      Object[] arrayOfObject = { paramString, new Integer(paramInt) };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustPayeeRouteID, RouteID, Status, Submitdate from BPW_CustPayeeRoute  WHERE CustomerID = ? AND PayeeListID = ?", arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCustPayeeRoute = new CustPayeeRoute();
        localCustPayeeRoute.CustomerID = paramString;
        localCustPayeeRoute.PayeeListID = paramInt;
        localCustPayeeRoute.CustPayeeRouteID = localFFSResultSet.getColumnString(1);
        localCustPayeeRoute.RouteID = localFFSResultSet.getColumnInt(2);
        localCustPayeeRoute.Status = localFFSResultSet.getColumnString(3);
        localCustPayeeRoute.Submitdate = localFFSResultSet.getColumnString(4);
        localArrayList.add(localCustPayeeRoute);
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
        FFSDebug.log("*** CustPayeeRoute.getCustPayeeRoute failed:" + localException2.toString());
      }
    }
    FFSDebug.log("CustPayeeRoute.getCustPayeeRoute done, customerID=" + paramString + ",payeeListID=" + paramInt + ",size=" + localArrayList.size(), 6);
    return (CustPayeeRoute[])localArrayList.toArray(new CustPayeeRoute[0]);
  }
  
  public static CustPayeeRoute[] getAllCustPayeeRoute(String paramString, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayeeRoute.getAllCustPayeeRoute start, customerID=" + paramString, 6);
    CustPayeeRoute localCustPayeeRoute = null;
    FFSResultSet localFFSResultSet = null;
    ArrayList localArrayList = new ArrayList();
    try
    {
      Object[] arrayOfObject = { paramString };
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustPayeeRouteID, PayeeListID, RouteID, Status, Submitdate FROM BPW_CustPayeeRoute  WHERE CustomerID=? ", arrayOfObject);
      while (localFFSResultSet.getNextRow())
      {
        localCustPayeeRoute = new CustPayeeRoute();
        localCustPayeeRoute.CustomerID = paramString;
        localCustPayeeRoute.CustPayeeRouteID = localFFSResultSet.getColumnString(1);
        localCustPayeeRoute.PayeeListID = localFFSResultSet.getColumnInt(2);
        localCustPayeeRoute.RouteID = localFFSResultSet.getColumnInt(3);
        localCustPayeeRoute.Status = localFFSResultSet.getColumnString(4);
        localCustPayeeRoute.Submitdate = localFFSResultSet.getColumnString(5);
        localArrayList.add(localCustPayeeRoute);
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
        FFSDebug.log("*** CustPayeeRoute.getAllCustPayeeRoute failed:" + localException2.toString());
      }
    }
    FFSDebug.log("CustPayeeRoute.getAllCustPayeeRoute done, customerID=" + paramString + ",size=" + localArrayList.size(), 6);
    return (CustPayeeRoute[])localArrayList.toArray(new CustPayeeRoute[0]);
  }
  
  public static int updateCustPayeeRouteStatus(String paramString1, int paramInt1, int paramInt2, String paramString2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayeeRoute.updateCustPayeeRouteStatus start, customerID=" + paramString1 + ",payeeListID=" + paramInt1 + ",routeID=" + paramInt2 + ",status=" + paramString2, 6);
    int i = 0;
    Object localObject1;
    if (paramInt2 == 0)
    {
      localObject1 = null;
      try
      {
        Object[] arrayOfObject1 = { paramString1, new Integer(paramInt1) };
        localObject1 = DBUtil.openResultSet(paramFFSConnectionHolder, "SELECT CustPayeeRouteID, RouteID, Status, Submitdate from BPW_CustPayeeRoute  WHERE CustomerID = ? AND PayeeListID = ?", arrayOfObject1);
        while (((FFSResultSet)localObject1).getNextRow()) {
          i++;
        }
        if (i > 1) {
          throw new BPWException("*** CustPayeeRoute.updateCustPayeeRouteStatus: " + i + " records found, must provide routeID to " + "specify the record");
        }
        Object[] arrayOfObject2 = { paramString2, paramString1, new Integer(paramInt1) };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustPayeeRoute SET Status = ?  WHERE CustomerID = ? AND PayeeListID = ?", arrayOfObject2);
      }
      catch (Exception localException2)
      {
        throw new BPWException(localException2.toString() + FFSDebug.stackTrace(localException2));
      }
      finally
      {
        try
        {
          if (localObject1 != null) {
            ((FFSResultSet)localObject1).close();
          }
        }
        catch (Exception localException3)
        {
          FFSDebug.log("*** CustPayeeRoute.updateCustPayeeRouteStatus failed:" + localException3.toString());
        }
      }
    }
    else if (paramString2.equals("CLOSED"))
    {
      deleteCustPayeeRoute(paramString1, paramInt1, paramInt2, paramFFSConnectionHolder);
    }
    else
    {
      try
      {
        localObject1 = new Object[] { paramString2, paramString1, new Integer(paramInt1), new Integer(paramInt2) };
        i = DBUtil.executeStatement(paramFFSConnectionHolder, "UPDATE BPW_CustPayeeRoute SET Status = ?  WHERE CustomerID = ? AND PayeeListID = ? AND RouteID = ?", (Object[])localObject1);
      }
      catch (Exception localException1)
      {
        FFSDebug.log("*** CustPayeeRoute.updateCustPayeeRouteStatus failed: " + localException1.toString() + FFSDebug.stackTrace(localException1));
        throw new BPWException(localException1.toString() + FFSDebug.stackTrace(localException1));
      }
    }
    FFSDebug.log("CustPayeeRoute.updateCustPayeeRouteStatus done, customerID=" + paramString1 + ",payeeListID=" + paramInt1 + ",routeID=" + paramInt2 + ",status=" + paramString2, 6);
    return i;
  }
  
  public static int deleteCustPayeeRoute(String paramString, int paramInt1, int paramInt2, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("CustPayeeRoute.deleteCustPayeeRoute start, customerID=" + paramString + ",payeeListID=" + paramInt1 + ",routeID=" + paramInt2, 6);
    Object[] arrayOfObject = { paramString, new Integer(paramInt1), new Integer(paramInt2) };
    int i = 0;
    try
    {
      i = DBUtil.executeStatement(paramFFSConnectionHolder, "DELETE FROM BPW_CustPayeeRoute  WHERE CustomerID = ? AND PayeeListID = ? AND RouteID = ?", arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** CustPayeeRoute.deleteCustPayeeRoute failed: " + localException.toString() + FFSDebug.stackTrace(localException));
      throw new BPWException(localException.toString() + FFSDebug.stackTrace(localException));
    }
    FFSDebug.log("CustPayeeRoute.deleteCustPayeeRoute done, customerID=" + paramString + ",payeeListID=" + paramInt1 + ",routeID=" + paramInt2, 6);
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.CustPayeeRoute
 * JD-Core Version:    0.7.0.1
 */