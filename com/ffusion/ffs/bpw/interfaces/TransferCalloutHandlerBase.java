package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;

public abstract class TransferCalloutHandlerBase
  implements FFSConst
{
  public abstract void start();
  
  public abstract void stop();
  
  public abstract void begin(TransferInfo paramTransferInfo, String paramString)
    throws FFSException;
  
  public abstract void end(TransferInfo paramTransferInfo, String paramString);
  
  public abstract void failure(TransferInfo paramTransferInfo, String paramString);
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.TransferCalloutHandlerBase
 * JD-Core Version:    0.7.0.1
 */