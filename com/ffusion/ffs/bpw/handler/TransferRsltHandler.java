package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.custimpl.transfers.TransferBackendResult;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.Hashtable;

public class TransferRsltHandler
  implements DBConsts, FFSConst, BPWResource
{
  private TransferBackendResult jv = null;
  private boolean ju = false;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str = "TransferRsltHandler.eventHandler: ";
    FFSDebug.log(str + " start", 6);
    boolean bool = false;
    processEvent(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool);
    FFSDebug.log(str + " done.", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str = "TransferRsltHandler.resubmitEventHandler: ";
    FFSDebug.log(str + " start", 6);
    boolean bool = true;
    processEvent(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool);
    FFSDebug.log(str + " done.", 6);
  }
  
  public void processEvent(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws Exception
  {
    String str;
    Object localObject;
    if (paramInt == 0)
    {
      str = paramEventInfoArray._array[0].FIId;
      localObject = paramEventInfoArray._array[0].processId;
      this.jv.setFIID(str);
      this.jv.setProcessId((String)localObject);
      this.ju = true;
    }
    else if ((paramInt == 2) && (this.ju))
    {
      str = DBConnCache.getNewBatchKey();
      DBConnCache.bind(str, paramFFSConnectionHolder);
      localObject = new Hashtable();
      this.jv.processTransferRslt(str, (Hashtable)localObject);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.TransferRsltHandler
 * JD-Core Version:    0.7.0.1
 */