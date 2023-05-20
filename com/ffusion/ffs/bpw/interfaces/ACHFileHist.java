package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class ACHFileHist
  implements Serializable
{
  protected String jdField_try = null;
  protected String jdField_void = null;
  protected String[] jdField_for = null;
  protected Object[] jdField_int = null;
  protected String a = null;
  protected String jdField_long = null;
  protected String jdField_do = null;
  protected int jdField_new = -1;
  protected String jdField_case = null;
  protected long jdField_else = 0L;
  protected long jdField_byte = 0L;
  protected long b = 0L;
  protected long jdField_char = 0L;
  public String histId = null;
  public String cursorId = null;
  protected String jdField_null = "Next";
  protected String jdField_goto = null;
  protected String[] jdField_if = null;
  
  public String getCustomerId()
  {
    return this.jdField_try;
  }
  
  public void setCustomerId(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getFiId()
  {
    return this.jdField_void;
  }
  
  public void setFiId(String paramString)
  {
    this.jdField_void = paramString;
  }
  
  public String getRequiredStatus()
  {
    return this.jdField_goto;
  }
  
  public void setRequiredStatus(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String[] getStatusList()
  {
    return this.jdField_if;
  }
  
  public void setStatusList(String[] paramArrayOfString)
  {
    this.jdField_if = paramArrayOfString;
  }
  
  public String[] getFileIdList()
  {
    return this.jdField_for;
  }
  
  public void setFileIdList(String[] paramArrayOfString)
  {
    this.jdField_for = paramArrayOfString;
  }
  
  public Object[] getFiles()
  {
    return this.jdField_int;
  }
  
  public void setFiles(Object[] paramArrayOfObject)
  {
    this.jdField_int = paramArrayOfObject;
  }
  
  public String getStartDate()
  {
    return this.a;
  }
  
  public void setStartDate(String paramString)
  {
    this.a = paramString;
  }
  
  public String getEndDate()
  {
    return this.jdField_long;
  }
  
  public void setEndDate(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public String getVersion()
  {
    return this.jdField_do;
  }
  
  public void setVersion(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public int getStatusCode()
  {
    return this.jdField_new;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.jdField_new = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.jdField_case;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public long getTotalFiles()
  {
    return this.jdField_else;
  }
  
  public void setTotalFiles(long paramLong)
  {
    this.jdField_else = paramLong;
  }
  
  public long getFilePageSize()
  {
    return this.jdField_byte;
  }
  
  public void setFilePageSize(long paramLong)
  {
    this.jdField_byte = paramLong;
  }
  
  public long getMaxFileId()
  {
    return this.b;
  }
  
  public void setMaxFileId(long paramLong)
  {
    this.b = paramLong;
  }
  
  public long getMinFileId()
  {
    return this.jdField_char;
  }
  
  public void setMinFileId(long paramLong)
  {
    this.jdField_char = paramLong;
  }
  
  public String getDirection()
  {
    return this.jdField_null;
  }
  
  public void setDirection(String paramString)
  {
    this.jdField_null = paramString;
  }
  
  public String getHistId()
  {
    return this.histId;
  }
  
  public void setHistId(String paramString)
  {
    this.histId = paramString;
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
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHFileHist
 * JD-Core Version:    0.7.0.1
 */