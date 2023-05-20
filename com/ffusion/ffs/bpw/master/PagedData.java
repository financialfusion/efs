package com.ffusion.ffs.bpw.master;

import com.ffusion.ffs.bpw.interfaces.PagingInfo;
import com.ffusion.ffs.interfaces.FFSException;

public abstract interface PagedData
{
  public abstract PagingInfo getPagedData(PagingInfo paramPagingInfo)
    throws FFSException;
  
  public abstract String getTmpTableName()
    throws FFSException;
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.master.PagedData
 * JD-Core Version:    0.7.0.1
 */