package com.ffusion.ffs.bpw.interfaces;

import java.util.Hashtable;

public class CCEntryInfo
  extends BPWInfoBase
{
  protected String l9;
  protected String mi;
  protected String md;
  protected String mj;
  protected String me;
  protected String ma;
  protected String l8;
  protected String mh;
  protected String ml;
  protected String mg;
  protected String mc;
  protected String mb;
  protected String mk;
  protected String mf = null;
  protected Hashtable l7;
  
  public String getEntryId()
  {
    return this.l9;
  }
  
  public void setEntryId(String paramString)
  {
    this.l9 = paramString;
  }
  
  public String getLocationId()
  {
    return this.mi;
  }
  
  public void setLocationId(String paramString)
  {
    this.mi = paramString;
  }
  
  public String getAmount()
  {
    return this.md;
  }
  
  public void setAmount(String paramString)
  {
    this.md = paramString;
  }
  
  public String getDueDate()
  {
    return this.mj;
  }
  
  public void setDueDate(String paramString)
  {
    this.mj = paramString;
  }
  
  public String getWillProcessTime()
  {
    return this.me;
  }
  
  public void setWillProcessTime(String paramString)
  {
    this.me = paramString;
  }
  
  public String getProcessedTime()
  {
    return this.ma;
  }
  
  public void setProcessedTime(String paramString)
  {
    this.ma = paramString;
  }
  
  public String getTransactionType()
  {
    return this.l8;
  }
  
  public void setTransactionType(String paramString)
  {
    this.l8 = paramString;
  }
  
  public String getMemo()
  {
    return this.mh;
  }
  
  public void setMemo(String paramString)
  {
    this.mh = paramString;
  }
  
  public String getCreatedDate()
  {
    return this.ml;
  }
  
  public void setCreatedDate(String paramString)
  {
    this.ml = paramString;
  }
  
  public String getEntryCategory()
  {
    return this.mg;
  }
  
  public void setEntryCategory(String paramString)
  {
    this.mg = paramString;
  }
  
  public String getStatus()
  {
    return this.mc;
  }
  
  public void setStatus(String paramString)
  {
    this.mc = paramString;
  }
  
  public String getLastProcessId()
  {
    return this.mb;
  }
  
  public void setLastProcessId(String paramString)
  {
    this.mb = paramString;
  }
  
  public String getCompId()
  {
    return this.mk;
  }
  
  public void setCompId(String paramString)
  {
    this.mk = paramString;
  }
  
  public String getLastModifier()
  {
    return this.mf;
  }
  
  public void setLastModifier(String paramString)
  {
    this.mf = paramString;
  }
  
  public void setExtInfo(Hashtable paramHashtable)
  {
    this.l7 = paramHashtable;
  }
  
  public Hashtable getExtInfo()
  {
    return this.l7;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCEntryInfo
 * JD-Core Version:    0.7.0.1
 */