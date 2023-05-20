package com.ffusion.beans.bcreport;

import com.ffusion.beans.DateTime;
import java.math.BigDecimal;

public class TransactionReportRecord
{
  protected static String dateFormat = "SHORT";
  protected int businessId;
  protected DateTime tranDate;
  protected String userName;
  protected String fromAcctId;
  protected String toAcctId;
  protected String currencyCode;
  protected BigDecimal amount;
  protected String status;
  protected String tranId;
  protected String srvrTid;
  protected String recSrvrTid;
  protected String lastModAction;
  protected String lastModUser;
  protected String backEndId;
  
  public TransactionReportRecord(int paramInt, DateTime paramDateTime, String paramString1, String paramString2, String paramString3, String paramString4, BigDecimal paramBigDecimal, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9, String paramString10, String paramString11)
  {
    this.businessId = paramInt;
    this.tranDate = paramDateTime;
    this.userName = paramString1;
    this.fromAcctId = paramString3;
    this.toAcctId = paramString2;
    this.currencyCode = paramString4;
    this.amount = paramBigDecimal;
    this.status = paramString5;
    this.tranId = paramString6;
    this.srvrTid = paramString7;
    this.recSrvrTid = paramString8;
    this.lastModAction = paramString9;
    this.lastModUser = paramString10;
    this.backEndId = paramString11;
  }
  
  public int getBusinessId()
  {
    return this.businessId;
  }
  
  public void setBusinessId(int paramInt)
  {
    this.businessId = paramInt;
  }
  
  public DateTime getTranDate()
  {
    return this.tranDate;
  }
  
  public void setTranDate(DateTime paramDateTime)
  {
    this.tranDate = paramDateTime;
  }
  
  public String getUserName()
  {
    return this.userName;
  }
  
  public void setUserName(String paramString)
  {
    this.userName = paramString;
  }
  
  public String getFromAcctId()
  {
    return this.fromAcctId;
  }
  
  public void setFromAcctId(String paramString)
  {
    this.fromAcctId = paramString;
  }
  
  public String getToAcctId()
  {
    return this.toAcctId;
  }
  
  public void setToAcctId(String paramString)
  {
    this.toAcctId = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.currencyCode;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.currencyCode = paramString;
  }
  
  public String getTranId()
  {
    return this.tranId;
  }
  
  public void setTranID(String paramString)
  {
    this.tranId = paramString;
  }
  
  public void setAmount(BigDecimal paramBigDecimal)
  {
    this.amount = paramBigDecimal;
  }
  
  public String getAmountString()
  {
    if (this.amount == null) {
      return null;
    }
    return this.amount.toString();
  }
  
  public BigDecimal getAmount()
  {
    return this.amount;
  }
  
  public void setSrvrTid(String paramString)
  {
    this.srvrTid = paramString;
  }
  
  public String getSrvrTid()
  {
    return this.srvrTid;
  }
  
  public void setRecSrvrTid(String paramString)
  {
    this.recSrvrTid = paramString;
  }
  
  public String getRecSrvrTid()
  {
    return this.recSrvrTid;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setLastModAction(String paramString)
  {
    this.lastModAction = paramString;
  }
  
  public String getLastModAction()
  {
    return this.lastModAction;
  }
  
  public void setLastModUser(String paramString)
  {
    this.lastModUser = paramString;
  }
  
  public String getLastModUser()
  {
    return this.lastModUser;
  }
  
  public String getBackEndId()
  {
    return this.backEndId;
  }
  
  public void setBackEndId(String paramString)
  {
    this.backEndId = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("businessId=" + Integer.toString(getBusinessId()) + ",tranDate=" + getTranDate().toString() + ",userName=" + getUserName() + ",fromAcctId=" + getFromAcctId() + ",toAcctId=" + getToAcctId() + ",currencyCode=" + getCurrencyCode() + ",amount=" + getAmountString() + ",status=" + getStatus() + ",tranID=" + getTranId() + ",srvrTid=" + getSrvrTid() + ",recSrvrTid=" + getRecSrvrTid() + ",lastModAction=" + getLastModAction() + ",lastModUser=" + getLastModUser() + ",backEndId=" + getBackEndId());
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.TransactionReportRecord
 * JD-Core Version:    0.7.0.1
 */