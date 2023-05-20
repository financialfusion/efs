package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.db.TempHist;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.master.PagedACH;
import com.ffusion.ffs.bpw.master.PagedBillPay;
import com.ffusion.ffs.bpw.master.PagedCashCon;
import com.ffusion.ffs.bpw.master.PagedTmpTable;
import com.ffusion.ffs.bpw.master.PagedTransfer;
import com.ffusion.ffs.bpw.master.PagedWire;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;

public class TempHistCleanupHandler
  implements DBConsts, FFSConst, BPWResource
{
  private PropertyConfig iu = null;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    this.iu = ((PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG"));
    String str1 = this.iu.otherProperties.getProperty("bpw.history.cleanup.interval", "60");
    int i = 60;
    if ((str1 == null) || (str1.trim().length() == 0))
    {
      String str2 = "No cleanup interval specified default value (one hour) will be used";
      FFSDebug.log(str2);
      i = 60;
    }
    else
    {
      try
      {
        i = Integer.parseInt(str1);
      }
      catch (Throwable localThrowable)
      {
        i = 60;
      }
    }
    FFSDebug.log("TempHistCleanupHandler.eventHandler: begin", 6);
    summaryCleanup(paramFFSConnectionHolder, i);
    FFSDebug.log("TempHistCleanupHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("TempHistCleanupHandler.resubmitEventHandler: begin", 6);
    eventHandler(paramInt, paramEventInfoArray, paramFFSConnectionHolder);
    FFSDebug.log("TempHistCleanupHandler.resubmitEventHandler: end", 6);
  }
  
  public void summaryCleanup(FFSConnectionHolder paramFFSConnectionHolder, int paramInt)
    throws FFSException
  {
    Object localObject = null;
    localObject = new PagedACH();
    ((PagedTmpTable)localObject).cleanup(paramFFSConnectionHolder, paramInt);
    localObject = new PagedCashCon();
    ((PagedTmpTable)localObject).cleanup(paramFFSConnectionHolder, paramInt);
    localObject = new PagedWire();
    ((PagedTmpTable)localObject).cleanup(paramFFSConnectionHolder, paramInt);
    localObject = new PagedTransfer();
    ((PagedTmpTable)localObject).cleanup(paramFFSConnectionHolder, paramInt);
    localObject = new PagedBillPay();
    ((PagedTmpTable)localObject).cleanup(paramFFSConnectionHolder, paramInt);
    TempHist.cleanAll(paramFFSConnectionHolder, paramInt);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.TempHistCleanupHandler
 * JD-Core Version:    0.7.0.1
 */