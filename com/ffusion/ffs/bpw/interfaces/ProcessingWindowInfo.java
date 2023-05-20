package com.ffusion.ffs.bpw.interfaces;

public class ProcessingWindowInfo
  extends BPWInfoBase
{
  protected String m6;
  protected String m8;
  protected String m9;
  protected String m3;
  protected String m4;
  protected String m2;
  protected String na;
  protected String m5;
  protected String m7;
  protected String m1;
  
  public String getWindowId()
  {
    return this.m6;
  }
  
  public void setWindowId(String paramString)
  {
    this.m6 = paramString;
  }
  
  public String getFIID()
  {
    return this.m8;
  }
  
  public void setFIID(String paramString)
  {
    this.m8 = paramString;
  }
  
  public String getCustomerId()
  {
    return this.m9;
  }
  
  public void setCustomerId(String paramString)
  {
    this.m9 = paramString;
  }
  
  public String getStartTime()
  {
    return this.m3;
  }
  
  public void setStartTime(String paramString)
  {
    this.m3 = paramString;
  }
  
  public String getCloseTime()
  {
    return this.m4;
  }
  
  public void setCloseTime(String paramString)
  {
    this.m4 = paramString;
  }
  
  public String getPmtType()
  {
    return this.m2;
  }
  
  public void setPmtType(String paramString)
  {
    this.m2 = paramString;
  }
  
  public String getPmtSubType()
  {
    return this.na;
  }
  
  public void setPmtSubType(String paramString)
  {
    this.na = paramString;
  }
  
  public String getDateCreate()
  {
    return this.m5;
  }
  
  public void setDateCreate(String paramString)
  {
    this.m5 = paramString;
  }
  
  public String getDescription()
  {
    return this.m7;
  }
  
  public void setDescription(String paramString)
  {
    this.m7 = paramString;
  }
  
  public String getProcessStatus()
  {
    return this.m1;
  }
  
  public void setProcessStatus(String paramString)
  {
    this.m1 = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("windowId=").append(this.m6).append(", ");
    localStringBuffer.append("fIID=").append(this.m8).append(", ");
    if (this.m9 != null) {
      localStringBuffer.append("customerId=").append(this.m9).append(", ");
    } else {
      localStringBuffer.append("customerId=").append(", ");
    }
    if (this.m3 != null) {
      localStringBuffer.append("startTime=").append(this.m3).append(", ");
    } else {
      localStringBuffer.append("startTime=").append(", ");
    }
    if (this.m4 != null) {
      localStringBuffer.append("closeTime=").append(this.m4).append(", ");
    } else {
      localStringBuffer.append("closeTime=").append(", ");
    }
    if (this.m2 != null) {
      localStringBuffer.append("pmtType=").append(this.m2).append(", ");
    } else {
      localStringBuffer.append("pmtType=").append(", ");
    }
    if (this.na != null) {
      localStringBuffer.append("pmtSubType=").append(this.na).append(", ");
    } else {
      localStringBuffer.append("pmtSubType=").append(", ");
    }
    if (this.m5 != null) {
      localStringBuffer.append("dateCreate=").append(this.m5).append(", ");
    } else {
      localStringBuffer.append("dateCreate=").append(", ");
    }
    if (this.m7 != null) {
      localStringBuffer.append("description=").append(this.m7).append(", ");
    } else {
      localStringBuffer.append("description=").append(", ");
    }
    if (this.submittedBy != null) {
      localStringBuffer.append("submittedBy=").append(this.submittedBy).append(", ");
    } else {
      localStringBuffer.append("submittedBy=").append(", ");
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ProcessingWindowInfo
 * JD-Core Version:    0.7.0.1
 */