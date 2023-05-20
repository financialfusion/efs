package com.ffusion.ffs.bpw.interfaces;

public class ETFACHEntryInfo
  extends BPWInfoBase
{
  protected String q9 = null;
  protected String rd = null;
  protected String rc = null;
  protected String rb = null;
  protected String rf = null;
  protected String q8 = null;
  protected String re = null;
  protected String ra = null;
  protected String q7 = null;
  
  public String getEntryId()
  {
    return this.q9;
  }
  
  public void setEntryId(String paramString)
  {
    this.q9 = paramString;
  }
  
  public String getBatchId()
  {
    return this.rd;
  }
  
  public void setBatchId(String paramString)
  {
    this.rd = paramString;
  }
  
  public String getTransactionCode()
  {
    return this.rc;
  }
  
  public void setTransactionCode(String paramString)
  {
    this.rc = paramString;
  }
  
  public String getRecvDFIIdentification()
  {
    return this.rb;
  }
  
  public void setRecvDFIIdentification(String paramString)
  {
    this.rb = paramString;
  }
  
  public String getDFIAccountNumber()
  {
    return this.rf;
  }
  
  public void setDFIAccountNumber(String paramString)
  {
    this.rf = paramString;
  }
  
  public String getRcvCompIndvName()
  {
    return this.q8;
  }
  
  public void setRcvCompIndvName(String paramString)
  {
    this.q8 = paramString;
  }
  
  public String getIdentificationNumber()
  {
    return this.re;
  }
  
  public void setIdentificationNumber(String paramString)
  {
    this.re = paramString;
  }
  
  public String getAmount()
  {
    return this.ra;
  }
  
  public void setAmount(String paramString)
  {
    this.ra = paramString;
  }
  
  public String getTraceNumber()
  {
    return this.q7;
  }
  
  public void setTraceNumber(String paramString)
  {
    this.q7 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ETFACHEntryInfo
 * JD-Core Version:    0.7.0.1
 */