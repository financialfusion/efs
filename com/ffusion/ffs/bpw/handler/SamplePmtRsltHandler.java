package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.custimpl.SamplePmtResult;
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

public class SamplePmtRsltHandler
  implements DBConsts, FFSConst, BPWResource
{
  private SamplePmtResult jI = null;
  private boolean jH;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("=== SamplePmtRsltHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      this.jH = false;
    } else if (paramInt == 1) {
      this.jH = true;
    } else if (paramInt == 2)
    {
      if (this.jH)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(paramEventInfoArray._array[0].FIId);
        BPWUtil.setFIIDList(localArrayList, this.jI);
        DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
        String str = DBConnCache.save(paramFFSConnectionHolder);
        this.jI.ProcessPmtRslt(str);
        DBConnCache.unbind(str);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("=== SamplePmtRsltHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("==== SamplePmtRsltHandler.resubmitEventHandler: begin, eventSeq=" + paramInt);
    if (paramInt == 0) {
      this.jH = false;
    } else if (paramInt == 1) {
      this.jH = true;
    } else if (paramInt == 2)
    {
      if (this.jH)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(paramEventInfoArray._array[0].FIId);
        BPWUtil.setFIIDList(localArrayList, this.jI);
        DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
        String str = DBConnCache.save(paramFFSConnectionHolder);
        this.jI.ProcessPmtRslt(str);
        DBConnCache.unbind(str);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("==== SamplePmtRsltHandler.resubmitEventHandler: end");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.SamplePmtRsltHandler
 * JD-Core Version:    0.7.0.1
 */