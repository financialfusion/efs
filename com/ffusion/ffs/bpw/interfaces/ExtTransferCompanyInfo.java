package com.ffusion.ffs.bpw.interfaces;

public class ExtTransferCompanyInfo
  extends BPWInfoBase
{
  protected String qD;
  protected String qG;
  protected String qA;
  protected String qF;
  protected String qB;
  protected String qC;
  protected String qE;
  protected String qz;
  
  public String getCompId()
  {
    return this.qD;
  }
  
  public void setCompId(String paramString)
  {
    this.qD = paramString;
  }
  
  public String getCustomerId()
  {
    return this.qG;
  }
  
  public void setCustomerId(String paramString)
  {
    this.qG = paramString;
  }
  
  public String getFiId()
  {
    return this.qA;
  }
  
  public void setFiId(String paramString)
  {
    this.qA = paramString;
  }
  
  public String getCompName()
  {
    return this.qF;
  }
  
  public void setCompName(String paramString)
  {
    this.qF = paramString;
  }
  
  public String getCompACHId()
  {
    return this.qB;
  }
  
  public void setCompACHId(String paramString)
  {
    this.qB = paramString;
  }
  
  public String getCompDescription()
  {
    return this.qC;
  }
  
  public void setCompDescription(String paramString)
  {
    this.qC = paramString;
  }
  
  public String getCreateDate()
  {
    return this.qE;
  }
  
  public void setCreateDate(String paramString)
  {
    this.qE = paramString;
  }
  
  public String getStatus()
  {
    return this.qz;
  }
  
  public void setStatus(String paramString)
  {
    this.qz = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("compId=").append(this.qD).append(", ");
    if (this.qG != null) {
      localStringBuffer.append("customerId=").append(this.qG).append(", ");
    } else {
      localStringBuffer.append("customerId=null").append(", ");
    }
    if (this.qA != null) {
      localStringBuffer.append("fiId=").append(this.qA).append(", ");
    } else {
      localStringBuffer.append("fiId=null").append(", ");
    }
    if (this.qF != null) {
      localStringBuffer.append("compName=").append(this.qF).append(", ");
    } else {
      localStringBuffer.append("compName=null").append(", ");
    }
    if (this.qB != null) {
      localStringBuffer.append("compACHId=").append(this.qB).append(", ");
    } else {
      localStringBuffer.append("compACHId=null").append(", ");
    }
    if (this.qC != null) {
      localStringBuffer.append("compDescription=").append(this.qC).append(", ");
    } else {
      localStringBuffer.append("compDescription=null").append(", ");
    }
    if (this.qE != null) {
      localStringBuffer.append("createDate=").append(this.qE).append(", ");
    } else {
      localStringBuffer.append("createDate=null").append(", ");
    }
    if (this.submittedBy != null) {
      localStringBuffer.append("submittedBy=").append(this.submittedBy).append(", ");
    } else {
      localStringBuffer.append("submittedBy=null").append(", ");
    }
    if (this.logId != null) {
      localStringBuffer.append("logId=").append(this.logId).append(", ");
    } else {
      localStringBuffer.append("logId=null").append(", ");
    }
    if (this.qz != null) {
      localStringBuffer.append("status=").append(this.qz).append(", ");
    } else {
      localStringBuffer.append("status=null").append(", ");
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ExtTransferCompanyInfo
 * JD-Core Version:    0.7.0.1
 */