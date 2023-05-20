package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.db.FFSConnectionHolder;

public abstract interface IACHBatchAdapter
{
  public abstract void start(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2, boolean paramBoolean, String paramString3)
    throws Exception;
  
  public abstract void processACHBatches(ACHBatchInfo[] paramArrayOfACHBatchInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void shutdown(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void processEmptyFile(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception;
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.IACHBatchAdapter
 * JD-Core Version:    0.7.0.1
 */