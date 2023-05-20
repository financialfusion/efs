package com.ffusion.ffs.bpw.interfaces;

public class CCBatchHistInfo
  extends BPWInfoBase
{
  protected String lB;
  protected String lE;
  protected String lv;
  protected String lz;
  protected String lC;
  protected int lx;
  protected int lD;
  protected String ly;
  protected String lA;
  protected String lw;
  
  public String getBatchHistId()
  {
    return this.lB;
  }
  
  public String getCompId()
  {
    return this.lE;
  }
  
  public String getProcessId()
  {
    return this.lv;
  }
  
  public String getEffectiveEntryDate()
  {
    return this.lz;
  }
  
  public String getBatchNumber()
  {
    return this.lC;
  }
  
  public int getNumberOfDeposits()
  {
    return this.lx;
  }
  
  public int getNumberOfDisburses()
  {
    return this.lD;
  }
  
  public String getTotalCreditAmount()
  {
    return this.ly;
  }
  
  public String getTotalDebitAmount()
  {
    return this.lA;
  }
  
  public String getTransactionType()
  {
    return this.lw;
  }
  
  public void setBatchHistId(String paramString)
  {
    this.lB = paramString;
  }
  
  public void setCompId(String paramString)
  {
    this.lE = paramString;
  }
  
  public void setProcessId(String paramString)
  {
    this.lv = paramString;
  }
  
  public void setEffectiveEntryDate(String paramString)
  {
    this.lz = paramString;
  }
  
  public void setBatchNumber(String paramString)
  {
    this.lC = paramString;
  }
  
  public void setNumberOfDeposits(int paramInt)
  {
    this.lx = paramInt;
  }
  
  public void setNumberOfDisburses(int paramInt)
  {
    this.lD = paramInt;
  }
  
  public void setTotalCreditAmount(String paramString)
  {
    this.ly = paramString;
  }
  
  public void setTotalDebitAmount(String paramString)
  {
    this.lA = paramString;
  }
  
  public void setTransactionType(String paramString)
  {
    this.lw = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCBatchHistInfo
 * JD-Core Version:    0.7.0.1
 */