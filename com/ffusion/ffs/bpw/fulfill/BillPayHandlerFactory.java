package com.ffusion.ffs.bpw.fulfill;

import com.ffusion.ffs.bpw.db.DBUtil;
import com.ffusion.ffs.bpw.db.Fulfillment;
import com.ffusion.ffs.bpw.interfaces.BPWException;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.IImmediateBillPayHandler;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.util.FFSDebug;

public class BillPayHandlerFactory
{
  public static IImmediateBillPayHandler getImmediateBillPayHandler(int paramInt1, int paramInt2)
    throws BPWException
  {
    FFSConnectionHolder localFFSConnectionHolder = new FFSConnectionHolder();
    try
    {
      localFFSConnectionHolder.conn = DBUtil.getConnection();
      FulfillmentInfo localFulfillmentInfo = Fulfillment.findByRouteID(paramInt1, localFFSConnectionHolder);
      Class localClass = Class.forName(localFulfillmentInfo.getImmediateHandlerName());
      IImmediateBillPayHandler localIImmediateBillPayHandler1 = (IImmediateBillPayHandler)localClass.newInstance();
      FFSDebug.log("*** BillPayHandlerFactory.getImmediateBillPayHandler handler class name: " + localIImmediateBillPayHandler1.getClass().getName(), 6);
      IImmediateBillPayHandler localIImmediateBillPayHandler2 = localIImmediateBillPayHandler1;
      return localIImmediateBillPayHandler2;
    }
    catch (Exception localException1)
    {
      FFSDebug.log("*** BillPayHandlerFactory.getImmediateBillPayHandler failed:" + localException1.toString());
      throw new BPWException(localException1.toString());
    }
    finally
    {
      try
      {
        DBUtil.freeConnection(localFFSConnectionHolder.conn);
      }
      catch (Exception localException2)
      {
        FFSDebug.log("*** BillPayHandlerFactory.getImmediateBillPayHandler failed: " + localException2.toString());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.fulfill.BillPayHandlerFactory
 * JD-Core Version:    0.7.0.1
 */