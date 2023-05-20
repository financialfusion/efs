package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.custimpl.FundsResult;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import java.util.ArrayList;

public class FundsRsltHandler
  implements DBConsts, FFSConst, BPWResource
{
  private FundsResult ib = null;
  private boolean h9;
  private static int ic = 1;
  private String ia = "FundsRslt_";
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== FundsRsltHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      this.h9 = false;
    } else if (paramInt == 1) {
      this.h9 = true;
    } else if (paramInt == 2)
    {
      if (this.h9)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(paramEventInfoArray._array[0].FIId);
        BPWUtil.setFIIDList(localArrayList, this.ib);
        DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
        String str = this.ia + Integer.toString(ic);
        ic += 1;
        DBConnCache.bind(str, paramFFSConnectionHolder);
        this.ib.ProcessFundsRslt(str);
        DBConnCache.unbind(str);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("=== FundsRsltHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("==== FundsRsltHandler.resubmitEventHandler: begin, eventSeq=" + paramInt);
    if (paramInt == 0) {
      this.h9 = false;
    } else if (paramInt == 1) {
      this.h9 = true;
    } else if (paramInt == 2)
    {
      if (this.h9)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(paramEventInfoArray._array[0].FIId);
        BPWUtil.setFIIDList(localArrayList, this.ib);
        DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
        String str = this.ia + Integer.toString(ic);
        ic += 1;
        DBConnCache.bind(str, paramFFSConnectionHolder);
        this.ib.ProcessFundsRslt(str);
        DBConnCache.unbind(str);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("==== FundsRsltHandler.resubmitEventHandler: end");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.FundsRsltHandler
 * JD-Core Version:    0.7.0.1
 */