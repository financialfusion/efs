package com.ffusion.ffs.bpw.interfaces;

public class ETFACHFileInfo
  extends BPWInfoBase
{
  protected String lX = null;
  protected String l4 = null;
  protected String lW = null;
  protected String lY = null;
  protected String l0 = null;
  protected String l1 = null;
  protected String l3 = null;
  protected String l2 = null;
  protected String l5 = null;
  protected String lV = null;
  protected String lZ = null;
  
  public String getFileId()
  {
    return this.lX;
  }
  
  public void setFileId(String paramString)
  {
    this.lX = paramString;
  }
  
  public String getCreateDate()
  {
    return this.l4;
  }
  
  public void setCreateDate(String paramString)
  {
    this.l4 = paramString;
  }
  
  public String getFileStatus()
  {
    return this.lW;
  }
  
  public void setFileStatus(String paramString)
  {
    this.lW = paramString;
  }
  
  public String getBatchCount()
  {
    return this.lY;
  }
  
  public void setBatchCount(String paramString)
  {
    this.lY = paramString;
  }
  
  public String getRecordCount()
  {
    return this.l0;
  }
  
  public void setRecordCount(String paramString)
  {
    this.l0 = paramString;
  }
  
  public String getTotalDebits()
  {
    return this.l1;
  }
  
  public void setTotalDebits(String paramString)
  {
    this.l1 = paramString;
  }
  
  public String getNumberOfDebits()
  {
    return this.l3;
  }
  
  public void setNumberOfDebits(String paramString)
  {
    this.l3 = paramString;
  }
  
  public String getTotalCredits()
  {
    return this.l2;
  }
  
  public void setTotalCredits(String paramString)
  {
    this.l2 = paramString;
  }
  
  public String getNumberOfCredits()
  {
    return this.l5;
  }
  
  public void setNumberOfCredits(String paramString)
  {
    this.l5 = paramString;
  }
  
  public String getProcessId()
  {
    return this.lV;
  }
  
  public void setProcessId(String paramString)
  {
    this.lV = paramString;
  }
  
  public String getExportFileName()
  {
    return this.lZ;
  }
  
  public void setExportFileName(String paramString)
  {
    this.lZ = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ETFACHFileInfo
 * JD-Core Version:    0.7.0.1
 */