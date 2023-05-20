package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.custimpl.RevertFundsAllocResult;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import java.util.ArrayList;

public class RevertFundsAllocRsltHandler
  implements DBConsts, FFSConst
{
  private RevertFundsAllocResult jT = null;
  private boolean jR;
  private static int jU = 1;
  private String jS = "RevertFundsRslt_";
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== RevertFundsAllocRsltHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      this.jR = false;
    } else if (paramInt == 1) {
      this.jR = true;
    } else if (paramInt == 2)
    {
      if (this.jR)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(paramEventInfoArray._array[0].FIId);
        BPWUtil.setFIIDList(localArrayList, this.jT);
        DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
        String str = this.jS + Integer.toString(jU);
        jU += 1;
        DBConnCache.bind(str, paramFFSConnectionHolder);
        this.jT.ProcessRevertFundsAllocRslt(str);
        DBConnCache.unbind(str);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("=== RevertFundsAllocRsltHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("==== RevertFundsAllocRsltHandler.resubmitEventHandler: begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      this.jR = false;
    } else if (paramInt == 1) {
      this.jR = true;
    } else if (paramInt == 2)
    {
      if (this.jR)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(paramEventInfoArray._array[0].FIId);
        BPWUtil.setFIIDList(localArrayList, this.jT);
        DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
        String str = this.jS + Integer.toString(jU);
        jU += 1;
        DBConnCache.bind(str, paramFFSConnectionHolder);
        this.jT.ProcessRevertFundsAllocRslt(str);
        DBConnCache.unbind(str);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("==== RevertFundsAllocRsltHandler.resubmitEventHandler: end", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.RevertFundsAllocRsltHandler
 * JD-Core Version:    0.7.0.1
 */