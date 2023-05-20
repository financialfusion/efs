package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.bpw.util.BPWUtil;

public class WireHistoryInfo
  extends BPWInfoBase
{
  protected String rr;
  protected String rH;
  protected String rE;
  protected String rA;
  protected String rw;
  protected String rF;
  protected String rq;
  protected String rI;
  protected WirePayeeInfo rC;
  protected String ru;
  protected String rp;
  protected String rG;
  protected String rt;
  protected String rz;
  protected String rx;
  protected String rB;
  protected String rD;
  protected String rs;
  protected String ry;
  protected Object rv;
  
  public String getCursorId()
  {
    return this.rr;
  }
  
  public void setCursorId(String paramString)
  {
    this.rr = paramString;
  }
  
  public String getDate()
  {
    return this.rH;
  }
  
  public void setDate(String paramString)
  {
    this.rH = paramString;
  }
  
  public String getTransType()
  {
    return this.rE;
  }
  
  public void setTransType(String paramString)
  {
    this.rE = paramString;
  }
  
  public String getStatus()
  {
    return this.rA;
  }
  
  public void setStatus(String paramString)
  {
    this.rA = paramString;
  }
  
  public String getId()
  {
    return this.rw;
  }
  
  public void setId(String paramString)
  {
    this.rw = paramString;
  }
  
  public String getRecId()
  {
    return this.rF;
  }
  
  public void setRecId(String paramString)
  {
    this.rF = paramString;
  }
  
  public String getExtId()
  {
    return this.rq;
  }
  
  public void setExtId(String paramString)
  {
    this.rq = paramString;
  }
  
  public String getPayeeId()
  {
    return this.rI;
  }
  
  public void setPayeeId(String paramString)
  {
    this.rI = paramString;
  }
  
  public WirePayeeInfo getPayeeInfo()
  {
    return this.rC;
  }
  
  public void setPayeeInfo(WirePayeeInfo paramWirePayeeInfo)
  {
    this.rC = paramWirePayeeInfo;
  }
  
  public String getFromAcctId()
  {
    return this.ru;
  }
  
  public void setFromAcctId(String paramString)
  {
    this.ru = paramString;
  }
  
  public String getFromAcctNum()
  {
    return this.rp;
  }
  
  public void setFromAcctNum(String paramString)
  {
    this.rp = paramString;
  }
  
  public String getFromAcctType()
  {
    return this.rG;
  }
  
  public void setFromAcctType(String paramString)
  {
    this.rG = paramString;
  }
  
  public void buildFromAcctId()
    throws Exception
  {
    this.ru = BPWUtil.getAccountIDWithAccountType(this.rp, this.rG);
  }
  
  public String getAmount()
  {
    return this.rt;
  }
  
  public void setAmount(String paramString)
  {
    this.rt = paramString;
  }
  
  public String getDestination()
  {
    return this.rz;
  }
  
  public void setDestination(String paramString)
  {
    this.rz = paramString;
  }
  
  public String getSource()
  {
    return this.rx;
  }
  
  public void setSource(String paramString)
  {
    this.rx = paramString;
  }
  
  public String getTemplateId()
  {
    return this.rB;
  }
  
  public void setTemplateId(String paramString)
  {
    this.rB = paramString;
  }
  
  public String getBusinessId()
  {
    return this.rD;
  }
  
  public void setBusinessId(String paramString)
  {
    this.rD = paramString;
  }
  
  public String getUserId()
  {
    return this.rs;
  }
  
  public void setUserId(String paramString)
  {
    this.rs = paramString;
  }
  
  public String getWireName()
  {
    return this.ry;
  }
  
  public void setWireName(String paramString)
  {
    this.ry = paramString;
  }
  
  public Object getWireObject()
  {
    return this.rv;
  }
  
  public void setWireObject(Object paramObject)
  {
    this.rv = paramObject;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("id=").append(this.rw).append(", ");
    localStringBuffer.append("recId=").append(this.rF).append(", ");
    localStringBuffer.append("extId=").append(this.rq).append(", ");
    localStringBuffer.append("payeeId=").append(this.rI).append(", ");
    localStringBuffer.append("fromAcctId=").append(this.ru).append(", ");
    localStringBuffer.append("transType=").append(this.rE).append(", ");
    localStringBuffer.append("status=").append(this.rA).append(", ");
    localStringBuffer.append("amount=").append(this.rt).append(", ");
    localStringBuffer.append("destination=").append(this.rz).append(", ");
    localStringBuffer.append("source=").append(this.rx).append(", ");
    localStringBuffer.append("businessId=").append(this.rD).append(", ");
    localStringBuffer.append("userId=").append(this.rs).append(", ");
    localStringBuffer.append("date=").append(this.rH).append(", ");
    localStringBuffer.append("templateId=").append(this.rB).append(", ");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.WireHistoryInfo
 * JD-Core Version:    0.7.0.1
 */