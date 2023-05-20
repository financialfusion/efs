package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.custimpl.CustomBackend;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;

public class CustomBackendHandler
  implements DBConsts, FFSConst, BPWResource
{
  public CustomBackend _custImpl = null;
  private boolean is;
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("CustomBackendHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      this.is = false;
    } else if (paramInt == 1) {
      this.is = true;
    } else if (paramInt == 2)
    {
      if (this.is) {
        this._custImpl.processCustomImpl();
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("CustomBackendHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("==== CustomBackendHandler.resubmitEventHandler: begin, eventSeq=" + paramInt);
    if (paramInt == 0) {
      this.is = false;
    } else if (paramInt == 1) {
      this.is = true;
    } else if (paramInt == 2)
    {
      if (this.is) {
        this._custImpl.processCustomImpl();
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("==== CustomBackendHandler.resubmitEventHandler: end");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.CustomBackendHandler
 * JD-Core Version:    0.7.0.1
 */