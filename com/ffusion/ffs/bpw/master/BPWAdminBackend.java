package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.db.Fulfillment;
import com.ffusion.ffs.bpw.db.Payee;
import com.ffusion.ffs.bpw.db.PayeeToRoute;
import com.ffusion.ffs.bpw.db.PmtInstruction;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.db.ConnectionFactory;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.db.FFSDBConst;
import com.ffusion.ffs.db.FFSDBProperties;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.Scheduler;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class BPWAdminBackend
  implements FFSConst, FFSDBConst, DBConsts
{
  private static FFSDBProperties b4;
  
  public static final void releaseResources()
  {
    b4 = null;
  }
  
  public static final void setDBProperties(FFSDBProperties paramFFSDBProperties)
  {
    b4 = paramFFSDBProperties;
  }
  
  private static final FFSConnection jdMethod_else()
    throws FFSException
  {
    FFSConnection localFFSConnection = ConnectionFactory.getNewConnection(b4);
    localFFSConnection.setAutoCommit(false);
    return localFFSConnection;
  }
  
  private static final FFSConnection a(FFSDBProperties paramFFSDBProperties)
    throws FFSException
  {
    FFSConnection localFFSConnection = ConnectionFactory.getNewConnection(paramFFSDBProperties);
    localFFSConnection.setAutoCommit(false);
    return localFFSConnection;
  }
  
  public static final void resubmitEvent(String paramString1, String paramString2)
    throws FFSException
  {
    String str1 = "BPWAdminBackend.resubmitEvent(insType, logDate) ";
    String str2 = str1 + "Error: This is an obsolete API which uses default FIID 1000.  The new API with FIID as parameter should be used.";
    FFSDebug.log(str2, 0);
    FFSConnection localFFSConnection = jdMethod_else();
    Scheduler localScheduler = new Scheduler();
    try
    {
      localScheduler.resubmitEvent(localFFSConnection, "1000", paramString1, paramString2);
      localFFSConnection.commit();
      return;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnection.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        localFFSConnection.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public static final void resubmitEvent(String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    FFSConnection localFFSConnection = jdMethod_else();
    Scheduler localScheduler = new Scheduler();
    try
    {
      localScheduler.resubmitEvent(localFFSConnection, paramString3, paramString1, paramString2);
      localFFSConnection.commit();
      return;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnection.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        localFFSConnection.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public static final PayeeInfo[] searchGlobalPayees(String paramString)
    throws OutOfMemoryError, FFSException
  {
    arrayOfPayeeInfo = null;
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = jdMethod_else();
    try
    {
      arrayOfPayeeInfo = Payee.searchGlobalPayees(paramString, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      return arrayOfPayeeInfo;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public static final PayeeRouteInfo getPayeeRoute(String paramString, int paramInt)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = jdMethod_else();
    localPayeeRouteInfo = null;
    try
    {
      localPayeeRouteInfo = PayeeToRoute.getPayeeRoute(paramString, paramInt, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      return localPayeeRouteInfo;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public static final String addPayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws FFSException
  {
    String str = null;
    Payee localPayee = new Payee(paramPayeeInfo);
    PayeeInfo[] arrayOfPayeeInfo = searchGlobalPayees(paramPayeeInfo.PayeeName);
    if ((paramPayeeInfo.Addr2 == null) || (paramPayeeInfo.Addr2.length() <= 0)) {
      paramPayeeInfo.Addr2 = null;
    }
    if ((paramPayeeInfo.Addr3 == null) || (paramPayeeInfo.Addr3.length() <= 0)) {
      paramPayeeInfo.Addr3 = null;
    }
    if ((paramPayeeInfo.Phone == null) || (paramPayeeInfo.Phone.length() <= 0)) {
      paramPayeeInfo.Phone = null;
    }
    str = localPayee.matchPayee(arrayOfPayeeInfo);
    if (str == null)
    {
      FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
      localFFSConnectionHolder.conn = jdMethod_else();
      try
      {
        localPayee.setPayeeID();
        if ((paramPayeeInfo.LinkPayeeID != null) && (paramPayeeInfo.LinkPayeeID.length() > 0))
        {
          localObject1 = Payee.findPayeeByID(paramPayeeInfo.LinkPayeeID, localFFSConnectionHolder);
          if ((((PayeeInfo)localObject1).LinkPayeeID != null) && (((PayeeInfo)localObject1).LinkPayeeID.length() > 0)) {
            throw new FFSException("Can not link to a virtual payee!");
          }
        }
        localPayee.setStatus("ACTIVE");
        if ((localPayee.getPayeeInfo() != null) && (localPayee.getPayeeInfo().getPayeeRouteInfo() == null)) {
          localPayee.getPayeeInfo().setPayeeRouteInfo(paramPayeeRouteInfo);
        }
        localPayee.storeToDB(localFFSConnectionHolder);
        str = localPayee.getPayeeID();
        paramPayeeRouteInfo.PayeeID = str;
        Object localObject1 = new PayeeToRoute(paramPayeeRouteInfo);
        ((PayeeToRoute)localObject1).storeToDB(localFFSConnectionHolder);
        localFFSConnectionHolder.conn.commit();
        try
        {
          localFFSConnectionHolder.conn.close();
        }
        catch (Throwable localThrowable1)
        {
          localThrowable1.printStackTrace();
        }
        paramPayeeInfo.PayeeID = str;
      }
      catch (BPWException localBPWException)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localBPWException.getErrorCode(), localBPWException.getMessage());
      }
      catch (Throwable localThrowable2)
      {
        localFFSConnectionHolder.conn.rollback();
        throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
      }
      finally
      {
        try
        {
          localFFSConnectionHolder.conn.close();
        }
        catch (Throwable localThrowable3)
        {
          localThrowable3.printStackTrace();
        }
      }
    }
    else
    {
      paramPayeeRouteInfo.PayeeID = str;
      updatePayee(paramPayeeInfo, paramPayeeRouteInfo);
    }
    return str;
  }
  
  public static final void updatePayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = jdMethod_else();
    try
    {
      Payee.updatePayee(localFFSConnectionHolder, paramPayeeInfo);
      PayeeToRoute localPayeeToRoute = new PayeeToRoute(paramPayeeRouteInfo);
      localPayeeToRoute.update(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      return;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public static final void deletePayee(String paramString)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = jdMethod_else();
    try
    {
      boolean bool1 = PmtInstruction.hasPendingPmt(paramString, localFFSConnectionHolder);
      boolean bool2 = Payee.hasPendingLink(paramString, localFFSConnectionHolder);
      if ((bool1) || (bool2)) {
        throw new FFSException("Can not delete this payee since it's being refered.");
      }
      PayeeInfo localPayeeInfo = Payee.findPayeeByID(paramString, localFFSConnectionHolder);
      Payee localPayee = new Payee(localPayeeInfo);
      PayeeRouteInfo localPayeeRouteInfo = PayeeToRoute.getPayeeRoute(paramString, localPayeeInfo.RouteID, localFFSConnectionHolder);
      PayeeToRoute localPayeeToRoute = new PayeeToRoute(localPayeeRouteInfo);
      localPayeeToRoute.removeFromDB(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      int i = PayeeToRoute.findRouteID(paramString, localFFSConnectionHolder);
      if (i == -1)
      {
        localPayee.removeFromDB(localFFSConnectionHolder);
      }
      else
      {
        localPayee.setRouteID(i);
        localPayee.update(localFFSConnectionHolder);
      }
      localFFSConnectionHolder.conn.commit();
      return;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public static final PayeeInfo findPayeeByID(String paramString)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = jdMethod_else();
    localPayeeInfo = null;
    try
    {
      localPayeeInfo = Payee.findPayeeByID(paramString, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      return localPayeeInfo;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public static final FulfillmentInfo[] getAllFulfillmentInfo()
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = jdMethod_else();
    arrayOfFulfillmentInfo = null;
    try
    {
      arrayOfFulfillmentInfo = Fulfillment.getFulfillmentInfos(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      return arrayOfFulfillmentInfo;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public static final FulfillmentInfo[] getAllFulfillmentInfo(FFSDBProperties paramFFSDBProperties)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = a(paramFFSDBProperties);
    arrayOfFulfillmentInfo = null;
    try
    {
      arrayOfFulfillmentInfo = Fulfillment.getFulfillmentInfos(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      return arrayOfFulfillmentInfo;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public static void addFulfillmentInfo(FFSDBProperties paramFFSDBProperties, FulfillmentInfo paramFulfillmentInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = a(paramFFSDBProperties);
    Fulfillment localFulfillment = new Fulfillment(paramFulfillmentInfo);
    try
    {
      localFulfillment.setRouteID();
      localFulfillment.storeToDB(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      return;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public static void updateFulfillmentInfo(FFSDBProperties paramFFSDBProperties, FulfillmentInfo paramFulfillmentInfo)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = a(paramFFSDBProperties);
    Fulfillment localFulfillment = new Fulfillment(paramFulfillmentInfo);
    try
    {
      localFulfillment.update(localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      return;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
  
  public static void deleteFulfillmentInfo(FFSDBProperties paramFFSDBProperties, int paramInt)
    throws FFSException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    localFFSConnectionHolder.conn = a(paramFFSDBProperties);
    try
    {
      Fulfillment.delete(paramInt, localFFSConnectionHolder);
      localFFSConnectionHolder.conn.commit();
      return;
    }
    catch (Throwable localThrowable2)
    {
      localFFSConnectionHolder.conn.rollback();
      throw new FFSException(localThrowable2, FFSDebug.stackTrace(localThrowable2));
    }
    finally
    {
      try
      {
        localFFSConnectionHolder.conn.close();
      }
      catch (Throwable localThrowable3)
      {
        localThrowable3.printStackTrace();
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.BPWAdminBackend
 * JD-Core Version:    0.7.0.1
 */