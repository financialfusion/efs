package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class RecACHBatchHist
  implements Serializable
{
  protected String jdField_goto = null;
  protected String jdField_byte = null;
  protected RecACHBatchInfo[] jdField_new = null;
  protected String jdField_do = null;
  protected String jdField_case = null;
  protected int jdField_try = -1;
  protected String jdField_int = null;
  protected long jdField_if = 0L;
  protected long a = 0L;
  protected String jdField_for = null;
  protected String jdField_else = null;
  protected String jdField_char = null;
  
  public String getCompId()
  {
    return this.jdField_goto;
  }
  
  public void setCompId(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getFIId()
  {
    return this.jdField_byte;
  }
  
  public void setFIId(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public RecACHBatchInfo[] getBatches()
  {
    return this.jdField_new;
  }
  
  public RecACHBatchInfo getBatchAt(int paramInt)
  {
    if (this.jdField_new == null) {
      return null;
    }
    if (paramInt < this.jdField_new.length) {
      return this.jdField_new[paramInt];
    }
    return null;
  }
  
  public void setBatches(RecACHBatchInfo[] paramArrayOfRecACHBatchInfo)
  {
    this.jdField_new = paramArrayOfRecACHBatchInfo;
  }
  
  public String getStartDate()
  {
    return this.jdField_do;
  }
  
  public void setStartDate(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getEndDate()
  {
    return this.jdField_case;
  }
  
  public void setEndDate(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public int getStatusCode()
  {
    return this.jdField_try;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.jdField_try = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.jdField_int;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public long getTotalBatches()
  {
    return this.jdField_if;
  }
  
  public void setTotalBatches(long paramLong)
  {
    this.jdField_if = paramLong;
  }
  
  public long getBatchPageSize()
  {
    return this.a;
  }
  
  public void setBatchPageSize(long paramLong)
  {
    this.a = paramLong;
  }
  
  public String getRequiredStatus()
  {
    return this.jdField_char;
  }
  
  public void setRequiredStatus(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getHistId()
  {
    return this.jdField_for;
  }
  
  public void setHistId(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getCursorId()
  {
    return this.jdField_else;
  }
  
  public void setCursorId(String paramString)
  {
    this.jdField_else = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.RecACHBatchHist
 * JD-Core Version:    0.7.0.1
 */