package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.scheduling.db.EventInfo;
import java.util.HashMap;

public abstract interface ITransferBackendHandler
{
  public abstract void startProcessTransfer(EventInfo paramEventInfo, HashMap paramHashMap);
  
  public abstract void processTransfer(TransferInfo[] paramArrayOfTransferInfo, HashMap paramHashMap)
    throws Exception;
  
  public abstract void endProcessTransfer(EventInfo paramEventInfo, HashMap paramHashMap);
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ITransferBackendHandler
 * JD-Core Version:    0.7.0.1
 */