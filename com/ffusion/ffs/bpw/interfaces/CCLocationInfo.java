package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.util.FFSUtil;
import java.math.BigDecimal;

public class CCLocationInfo
  extends BPWInfoBase
{
  protected String vd;
  protected String vb;
  protected String u5;
  protected String vf;
  protected String u9;
  protected String u4;
  protected String vp;
  protected String vc;
  protected String vk;
  protected String u8;
  protected String ve;
  protected String vg;
  protected String u6;
  protected String u3;
  protected String vl;
  protected String va;
  protected String u7;
  protected String vm;
  protected String vo;
  protected String vq;
  protected String vh;
  protected String vn;
  protected String vj;
  protected String vi;
  
  public String getLocationId()
  {
    return this.vd;
  }
  
  public void setLocationId(String paramString)
  {
    this.vd = paramString;
  }
  
  public String getLocationName()
  {
    return this.vb;
  }
  
  public void setLocationName(String paramString)
  {
    this.vb = paramString;
  }
  
  public String getCCLocationId()
  {
    return this.u5;
  }
  
  public void setCCLocationId(String paramString)
  {
    this.u5 = paramString;
  }
  
  public String getCompId()
  {
    return this.vf;
  }
  
  public void setCompId(String paramString)
  {
    this.vf = paramString;
  }
  
  public String getConcentrateAcctId()
  {
    return this.u9;
  }
  
  public void setConcentrateAcctId(String paramString)
  {
    this.u9 = paramString;
  }
  
  public String getDisburseAcctId()
  {
    return this.u4;
  }
  
  public void setDisburseAcctId(String paramString)
  {
    this.u4 = paramString;
  }
  
  public String getBankRtn()
  {
    return this.vp;
  }
  
  public void setBankRtn(String paramString)
  {
    this.vp = paramString;
  }
  
  public String getBankName()
  {
    return this.vc;
  }
  
  public void setBankName(String paramString)
  {
    this.vc = paramString;
  }
  
  public String getAccountNum()
  {
    return this.vk;
  }
  
  public void setAccountNum(String paramString)
  {
    this.vk = paramString;
  }
  
  public String getAccountType()
  {
    return this.u8;
  }
  
  public void setAccountType(String paramString)
  {
    this.u8 = paramString;
  }
  
  public String getDepositMin()
  {
    return this.ve;
  }
  
  public void setDepositMin(String paramString)
  {
    this.ve = paramString;
  }
  
  public String getDepositMax()
  {
    return this.vg;
  }
  
  public void setDepositMax(String paramString)
  {
    this.vg = paramString;
  }
  
  public String getAnticipatoryDepos()
  {
    return this.u6;
  }
  
  public long getAnticipatoryDeposLong()
  {
    long l = 0L;
    try
    {
      BigDecimal localBigDecimal = FFSUtil.getBigDecimal(this.u6, 0);
      l = localBigDecimal.longValue();
    }
    catch (Exception localException) {}
    return l;
  }
  
  public void setAnticipatoryDepos(String paramString)
  {
    this.u6 = paramString;
  }
  
  public String getThresholdDeposAmt()
  {
    return this.u3;
  }
  
  public long getThresholdDeposAmtLong()
  {
    long l = 0L;
    try
    {
      BigDecimal localBigDecimal = FFSUtil.getBigDecimal(this.u3, 0);
      l = localBigDecimal.longValue();
    }
    catch (Exception localException) {}
    return l;
  }
  
  public void setThresholdDeposAmt(String paramString)
  {
    this.u3 = paramString;
  }
  
  public String getConsolidateDepos()
  {
    return this.vl;
  }
  
  public void setConsolidateDepos(String paramString)
  {
    this.vl = paramString;
  }
  
  public String getDepositPrenote()
  {
    return this.va;
  }
  
  public void setDepositPrenote(String paramString)
  {
    this.va = paramString;
  }
  
  public String getDepPrenSubDate()
  {
    return this.u7;
  }
  
  public void setDepPrenSubDate(String paramString)
  {
    this.u7 = paramString;
  }
  
  public String getDepPrenoteStatus()
  {
    return this.vm;
  }
  
  public void setDepPrenoteStatus(String paramString)
  {
    this.vm = paramString;
  }
  
  public String getDisbursePrenote()
  {
    return this.vo;
  }
  
  public void setDisbursePrenote(String paramString)
  {
    this.vo = paramString;
  }
  
  public String getDisPrenSubDate()
  {
    return this.vq;
  }
  
  public void setDisPrenSubDate(String paramString)
  {
    this.vq = paramString;
  }
  
  public String getDisPrenoteStatus()
  {
    return this.vh;
  }
  
  public void setDisPrenoteStatus(String paramString)
  {
    this.vh = paramString;
  }
  
  public String getMemo()
  {
    return this.vn;
  }
  
  public void setMemo(String paramString)
  {
    this.vn = paramString;
  }
  
  public String getStatus()
  {
    return this.vj;
  }
  
  public void setStatus(String paramString)
  {
    this.vj = paramString;
  }
  
  public String getLastRequestTime()
  {
    return this.vi;
  }
  
  public void setLastRequestTime(String paramString)
  {
    this.vi = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CCLocationInfo
 * JD-Core Version:    0.7.0.1
 */