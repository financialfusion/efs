package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.db.FFSConnectionHolder;

public abstract interface ICashConAdapter
{
  public abstract void start()
    throws Exception;
  
  public abstract void processOneCutOff(FFSConnectionHolder paramFFSConnectionHolder, CutOffInfo paramCutOffInfo, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws Exception;
  
  public abstract void processRunNow(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws Exception;
  
  public abstract void shutdown()
    throws Exception;
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ICashConAdapter
 * JD-Core Version:    0.7.0.1
 */