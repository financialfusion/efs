package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.db.FFSConnectionHolder;

public abstract interface IACHFileAdapter
{
  public abstract void start(String paramString1, String paramString2)
    throws Exception;
  
  public abstract void processACHFiles(ACHFileInfo[] paramArrayOfACHFileInfo, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception;
  
  public abstract void shutdown()
    throws Exception;
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.IACHFileAdapter
 * JD-Core Version:    0.7.0.1
 */