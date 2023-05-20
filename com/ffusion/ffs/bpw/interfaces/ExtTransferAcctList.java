package com.ffusion.ffs.bpw.interfaces;

public class ExtTransferAcctList
  extends BPWInfoBase
{
  protected String pr;
  protected String[] px;
  protected String[] py;
  protected String pn;
  protected String pq;
  protected String pt;
  protected String pp;
  protected String pv;
  protected String pw;
  protected String ps;
  protected String pu;
  ExtTransferAcctInfo[] po;
  
  public String getFIId()
  {
    return this.pr;
  }
  
  public void setFIId(String paramString)
  {
    this.pr = paramString;
  }
  
  public String[] getCustomerId()
  {
    return this.px;
  }
  
  public void setCustomerId(String[] paramArrayOfString)
  {
    this.px = paramArrayOfString;
  }
  
  public String[] getCompId()
  {
    return this.py;
  }
  
  public void setCompId(String[] paramArrayOfString)
  {
    this.py = paramArrayOfString;
  }
  
  public String getAcctBankRtn()
  {
    return this.pn;
  }
  
  public void setAcctBankRtn(String paramString)
  {
    this.pn = paramString;
  }
  
  public String getRecipientId()
  {
    return this.pq;
  }
  
  public void setRecipientId(String paramString)
  {
    this.pq = paramString;
  }
  
  public String getRecipientName()
  {
    return this.pt;
  }
  
  public void setRecipientType(String paramString)
  {
    this.pp = paramString;
  }
  
  public String getRecipientType()
  {
    return this.pp;
  }
  
  public void setRecipientName(String paramString)
  {
    this.pt = paramString;
  }
  
  public String getPrenoteStatus()
  {
    return this.pv;
  }
  
  public void setPrenoteStatus(String paramString)
  {
    this.pv = paramString;
  }
  
  public String getNickName()
  {
    return this.pw;
  }
  
  public void setNickName(String paramString)
  {
    this.pw = paramString;
  }
  
  public String getAcctNum()
  {
    return this.ps;
  }
  
  public void setAcctNum(String paramString)
  {
    this.ps = paramString;
  }
  
  public String getAcctType()
  {
    return this.pu;
  }
  
  public void setAcctType(String paramString)
  {
    this.pu = paramString;
  }
  
  public ExtTransferAcctInfo[] getExtTransferAccts()
  {
    return this.po;
  }
  
  public void setExtTransferAccts(ExtTransferAcctInfo[] paramArrayOfExtTransferAcctInfo)
  {
    this.po = paramArrayOfExtTransferAcctInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ExtTransferAcctList
 * JD-Core Version:    0.7.0.1
 */