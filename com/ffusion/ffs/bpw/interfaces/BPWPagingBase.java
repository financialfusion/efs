package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class BPWPagingBase
  implements Serializable
{
  public long totalPayees = 0L;
  public long pageSize = 0L;
  public String cursorId = null;
  
  public long getTotalPayees()
  {
    return this.totalPayees;
  }
  
  public void setTotalPayees(long paramLong)
  {
    this.totalPayees = paramLong;
  }
  
  public long getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(long paramLong)
  {
    this.pageSize = paramLong;
  }
  
  public String getCursorId()
  {
    return this.cursorId;
  }
  
  public void setCursorId(String paramString)
  {
    this.cursorId = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.BPWPagingBase
 * JD-Core Version:    0.7.0.1
 */