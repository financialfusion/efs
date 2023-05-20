package com.ffusion.ffs.bpw.db;

import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSResultSet;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.Vector;

public class Fulfillment
  implements FFSConst, DBConsts
{
  private FulfillmentInfo yc;
  
  public Fulfillment()
  {
    this.yc = new FulfillmentInfo();
  }
  
  public Fulfillment(FulfillmentInfo paramFulfillmentInfo)
  {
    this.yc = paramFulfillmentInfo;
  }
  
  public FulfillmentInfo getFulfillmentInfo()
  {
    return this.yc;
  }
  
  public void setRouteID()
    throws Exception
  {
    this.yc.RouteID = DBUtil.getNextIndex("RouteID");
  }
  
  public void setRouteID(int paramInt)
  {
    this.yc.RouteID = paramInt;
  }
  
  public void setRouteID(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    this.yc.RouteID = DBUtil.getNextIndex(paramFFSConnectionHolder, "RouteID");
  }
  
  public int getRouteID()
  {
    return this.yc.RouteID;
  }
  
  public void setFulfillmentSystemName(String paramString)
  {
    this.yc.FulfillmentSystemName = paramString;
  }
  
  public String getFulfillmentSystemName()
  {
    return this.yc.FulfillmentSystemName;
  }
  
  public void storeToDB(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Fulfillment.storeToDB start, name= " + this.yc.FulfillmentSystemName, 6);
    addFulfillmentSystem(this.yc, paramFFSConnectionHolder);
    FFSDebug.log("Fulfillment.storeToDB done, name= " + this.yc.FulfillmentSystemName, 6);
  }
  
  public static void addFulfillmentSystem(FulfillmentInfo paramFulfillmentInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Fulfillment.addFulfillmentSystem start, name= " + paramFulfillmentInfo.FulfillmentSystemName, 6);
    String str = "INSERT INTO BPW_Fulfillment( FulfillmentSystemName, RouteID, PaymentCost, HandlerName, ImmediateFundAllocation, ImmediateProcessing, ImmediateHandlerName ) VALUES(?,?,?,?,?,?,?)";
    Object[] arrayOfObject = { paramFulfillmentInfo.FulfillmentSystemName, new Integer(paramFulfillmentInfo.RouteID), new Double(paramFulfillmentInfo.PaymentCost), paramFulfillmentInfo.HandlerName, paramFulfillmentInfo.getImmediateFundAllocation(), paramFulfillmentInfo.getImmediateProcessing(), paramFulfillmentInfo.getImmediateHandlerName() };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fulfillment.addFulfillmentSystem failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("Fulfillment.addFulfillmentSystem done, name= " + paramFulfillmentInfo.FulfillmentSystemName, 6);
  }
  
  public static FulfillmentInfo[] getFulfillmentInfos()
    throws BPWException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = DBUtil.getConnection();
    try
    {
      FulfillmentInfo[] arrayOfFulfillmentInfo = getFulfillmentInfos(localFFSConnectionHolder);
      return arrayOfFulfillmentInfo;
    }
    catch (BPWException localBPWException)
    {
      FFSDebug.log("*** Fulfillment.getFulfillmentInfos failed:" + localBPWException.toString());
      throw localBPWException;
    }
    finally
    {
      DBUtil.freeConnection(localFFSConnectionHolder.conn);
    }
  }
  
  public static FulfillmentInfo[] getFulfillmentInfos(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Fulfillment.getFulfillmentInfos start", 6);
    String str = "SELECT FulfillmentSystemName,RouteID, PaymentCost, HandlerName, ImmediateFundAllocation, ImmediateProcessing, ImmediateHandlerName FROM BPW_Fulfillment ";
    Vector localVector = new Vector();
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, null);
      while (localFFSResultSet.getNextRow())
      {
        FulfillmentInfo localFulfillmentInfo = new FulfillmentInfo();
        localFulfillmentInfo.FulfillmentSystemName = localFFSResultSet.getColumnString(1);
        localFulfillmentInfo.RouteID = localFFSResultSet.getColumnInt(2);
        localFulfillmentInfo.PaymentCost = localFFSResultSet.getColumnDouble(3);
        localFulfillmentInfo.HandlerName = localFFSResultSet.getColumnString(4);
        localFulfillmentInfo.setImmediateFundAllocation(localFFSResultSet.getColumnString(5));
        localFulfillmentInfo.setImmediateProcessing(localFFSResultSet.getColumnString(6));
        localFulfillmentInfo.setImmediateHandlerName(localFFSResultSet.getColumnString(7));
        localVector.addElement(localFulfillmentInfo);
      }
    }
    catch (Exception localException1)
    {
      throw new BPWException(localException1.toString());
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
        FFSDebug.log("*** Fulfillment.getFulfillmentInfos failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Fulfillment.getFulfillmentInfos done, found " + localVector.size() + " fulfillments", 6);
    if (localVector.isEmpty()) {
      return null;
    }
    return (FulfillmentInfo[])localVector.toArray(new FulfillmentInfo[0]);
  }
  
  public static FulfillmentInfo findByRouteID(int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Fulfillment.findByRouteID start, routeID=" + paramInt, 6);
    FulfillmentInfo localFulfillmentInfo = new FulfillmentInfo();
    String str = "SELECT FulfillmentSystemName,PaymentCost, HandlerName, ImmediateFundAllocation, ImmediateProcessing, ImmediateHandlerName FROM BPW_Fulfillment WHERE RouteID = ?";
    Object[] arrayOfObject = { new Integer(paramInt) };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, arrayOfObject);
      if (localFFSResultSet.getNextRow())
      {
        localFulfillmentInfo.RouteID = paramInt;
        localFulfillmentInfo.FulfillmentSystemName = localFFSResultSet.getColumnString(1);
        localFulfillmentInfo.PaymentCost = localFFSResultSet.getColumnDouble(2);
        localFulfillmentInfo.HandlerName = localFFSResultSet.getColumnString(3);
        localFulfillmentInfo.setImmediateFundAllocation(localFFSResultSet.getColumnString(4));
        localFulfillmentInfo.setImmediateProcessing(localFFSResultSet.getColumnString(5));
        localFulfillmentInfo.setImmediateHandlerName(localFFSResultSet.getColumnString(6));
      }
      else
      {
        localFulfillmentInfo = null;
      }
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Fulfillment.findByRouteID failed: " + localException1.toString());
      throw new BPWException(localException1.toString());
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
        FFSDebug.log("*** Fulfillment.findByRouteID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Fulfillment.findByRouteID done, routeID=" + paramInt, 6);
    return localFulfillmentInfo;
  }
  
  public void update(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Fulfillment.update start, routeID=" + this.yc.RouteID, 6);
    String str = "UPDATE BPW_Fulfillment SET FulfillmentSystemName=?, PaymentCost=?, HandlerName=?, ImmediateFundAllocation=?, ImmediateProcessing=?, ImmediateHandlerName=? WHERE RouteID=?";
    Object[] arrayOfObject = { this.yc.FulfillmentSystemName, new Double(this.yc.PaymentCost), this.yc.HandlerName, this.yc.getImmediateFundAllocation(), this.yc.getImmediateProcessing(), this.yc.getImmediateHandlerName(), new Integer(this.yc.RouteID) };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fulfillment.update failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("Fulfillment.update done, routeID=" + this.yc.RouteID, 6);
  }
  
  public static void update(FulfillmentInfo paramFulfillmentInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Fulfillment.update start, routeID=" + paramFulfillmentInfo.RouteID, 6);
    String str = "UPDATE BPW_Fulfillment SET FulfillmentSystemName=?, PaymentCost=?, HandlerName=?, ImmediateFundAllocation=?, ImmediateProcessing=?, ImmediateHandlerName=? WHERE RouteID=?";
    Object[] arrayOfObject = { paramFulfillmentInfo.FulfillmentSystemName, new Double(paramFulfillmentInfo.PaymentCost), paramFulfillmentInfo.HandlerName, paramFulfillmentInfo.getImmediateFundAllocation(), paramFulfillmentInfo.getImmediateProcessing(), paramFulfillmentInfo.getImmediateHandlerName(), new Integer(paramFulfillmentInfo.RouteID) };
    try
    {
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str, arrayOfObject);
    }
    catch (Exception localException)
    {
      FFSDebug.log("*** Fulfillment.update failed: " + localException.toString());
      throw new BPWException(localException.toString());
    }
    FFSDebug.log("Fulfillment.update done, routeID=" + paramFulfillmentInfo.RouteID, 6);
  }
  
  public static void delete(int paramInt, FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Fulfillment.delete start, routeID=" + paramInt, 6);
    String str1 = "SELECT PayeeID FROM BPW_Payee WHERE RouteID = ?";
    String str2 = "DELETE FROM BPW_Fulfillment WHERE RouteID =?";
    Object[] arrayOfObject = { new Integer(paramInt) };
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
      if (localFFSResultSet.getNextRow()) {
        throw new BPWException("Can not delete bacause there are payees routed to it.");
      }
      int i = DBUtil.executeStatement(paramFFSConnectionHolder, str2, arrayOfObject);
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Fulfillment.delete failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
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
      catch (Exception localException3)
      {
        FFSDebug.log("*** Fulfillment.delete failed:" + localException3.toString());
      }
    }
    localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str1, arrayOfObject);
    }
    catch (Exception localException2)
    {
      FFSDebug.log("*** Fulfillment.delete failed:" + localException2.toString());
      throw new BPWException(localException2.toString());
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
      catch (Exception localException4)
      {
        FFSDebug.log("*** Fulfillment.delete failed:" + localException4.toString());
      }
    }
    FFSDebug.log("Fulfillment.delete done, routeID=" + paramInt, 6);
  }
  
  public int getMaxRouteID(FFSConnectionHolder paramFFSConnectionHolder)
    throws BPWException
  {
    FFSDebug.log("Fulfillment.getMaxRouteID ", 6);
    int i = 0;
    String str = "SELECT MAX(RouteID) FROM BPW_Fulfillment";
    FFSResultSet localFFSResultSet = null;
    try
    {
      localFFSResultSet = DBUtil.openResultSet(paramFFSConnectionHolder, str, null);
      if (localFFSResultSet.getNextRow()) {
        i = localFFSResultSet.getColumnInt(1);
      }
      if (i != 0) {
        i += 1;
      }
      localFFSResultSet.close();
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** Fulfillment.getMaxRouteID failed: " + localException1.toString());
      throw new BPWException(localException1.toString());
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
        FFSDebug.log("*** Fulfillment.getMaxRouteID failed:" + localException2.toString());
      }
    }
    FFSDebug.log("Fulfillment.getMaxRouteID done, routeID=" + i, 6);
    return i;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.db.Fulfillment
 * JD-Core Version:    0.7.0.1
 */