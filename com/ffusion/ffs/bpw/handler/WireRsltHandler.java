package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.custimpl.WireBackendResult;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import java.util.ArrayList;
import java.util.Hashtable;

public class WireRsltHandler
  implements DBConsts, FFSConst, BPWResource
{
  private WireBackendResult ie = null;
  private boolean id;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("WireRsltHandler.eventHandler: ", "begin, eventSeq: " + paramInt, 6);
    boolean bool = false;
    processEvents(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool);
    FFSDebug.log("WireRsltHandler.eventHandler: ", "end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("WireRsltHandler.resubmitEventHandler: ", "begin, eventSeq: " + paramInt, 6);
    boolean bool = true;
    processEvents(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool);
    FFSDebug.log("WireRsltHandler.resubmitEventHandler: ", "end", 6);
  }
  
  public void processEvents(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws Exception
  {
    FFSDebug.log("WireRsltHandler.processEvents: ", "begin, eventSeq: " + paramInt, 6);
    if (paramInt == 0) {
      this.id = false;
    } else if (paramInt == 1) {
      this.id = true;
    } else if (paramInt == 2)
    {
      if (this.id)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(paramEventInfoArray._array[0].FIId);
        BPWUtil.setFIIDList(localArrayList, this.ie);
        String str = DBConnCache.getNewBatchKey();
        DBConnCache.bind(str, paramFFSConnectionHolder);
        Hashtable localHashtable = new Hashtable();
        this.ie.processWireRslt(str, localHashtable);
        DBConnCache.unbind(str);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("WireRsltHandler.processEvents: ", "end", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.WireRsltHandler
 * JD-Core Version:    0.7.0.1
 */