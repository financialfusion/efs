package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class BPWHist
  implements Serializable
{
  public String custId = null;
  public String fiId = null;
  public String payeeId = null;
  public String acctId = null;
  public Object[] trans = null;
  public String startDate = null;
  public String endDate = null;
  public String version = null;
  public String[] submittedBy = null;
  public String[] userId = null;
  public String[] statusList = null;
  public String transType = null;
  public String transScope = null;
  public String[] dest = null;
  public String templateId = null;
  public int statusCode = -1;
  public String statusMsg = null;
  public String transSource = null;
  public String minAmount;
  public String maxAmount;
  public String selectionCriteria = null;
  public long totalTrans = 0L;
  public long pageSize = 0L;
  public String histId = null;
  public String cursorId = null;
  public String requiredStatus = null;
  public String category = null;
  
  public String[] getSubmittedBy()
  {
    return this.submittedBy;
  }
  
  public void setSubmittedBy(String[] paramArrayOfString)
  {
    this.submittedBy = paramArrayOfString;
  }
  
  public String[] getUserId()
  {
    return this.userId;
  }
  
  public void setUserId(String[] paramArrayOfString)
  {
    this.userId = paramArrayOfString;
  }
  
  public String getTransSource()
  {
    return this.transSource;
  }
  
  public void setTransSource(String paramString)
  {
    this.transSource = paramString;
  }
  
  public String[] getStatusList()
  {
    return this.statusList;
  }
  
  public void setStatusList(String[] paramArrayOfString)
  {
    this.statusList = paramArrayOfString;
  }
  
  public String getTransType()
  {
    return this.transType;
  }
  
  public void setTransType(String paramString)
  {
    this.transType = paramString;
  }
  
  public String getFiId()
  {
    return this.fiId;
  }
  
  public void setFiId(String paramString)
  {
    this.fiId = paramString;
  }
  
  public String getTransScope()
  {
    return this.transScope;
  }
  
  public void setTransScope(String paramString)
  {
    this.transScope = paramString;
  }
  
  public String[] getDest()
  {
    return this.dest;
  }
  
  public void setDest(String[] paramArrayOfString)
  {
    this.dest = paramArrayOfString;
  }
  
  public String getTemplateId()
  {
    return this.templateId;
  }
  
  public void setTemplateId(String paramString)
  {
    this.templateId = paramString;
  }
  
  public String getRequiredStatus()
  {
    return this.requiredStatus;
  }
  
  public void setRequiredStatus(String paramString)
  {
    this.requiredStatus = paramString;
  }
  
  public String getCustId()
  {
    return this.custId;
  }
  
  public void setCustId(String paramString)
  {
    this.custId = paramString;
  }
  
  public String getPayeeId()
  {
    return this.payeeId;
  }
  
  public void setPayeeId(String paramString)
  {
    this.payeeId = paramString;
  }
  
  public String getAcctId()
  {
    return this.acctId;
  }
  
  public void setAcctId(String paramString)
  {
    this.acctId = paramString;
  }
  
  public Object[] getTrans()
  {
    return this.trans;
  }
  
  public Object getTrans(int paramInt)
  {
    if ((this.trans == null) || (paramInt < 0) || (paramInt >= this.trans.length)) {
      return null;
    }
    return this.trans[paramInt];
  }
  
  public void setTrans(Object[] paramArrayOfObject)
  {
    this.trans = paramArrayOfObject;
  }
  
  public String getStartDate()
  {
    return this.startDate;
  }
  
  public void setStartDate(String paramString)
  {
    this.startDate = paramString;
  }
  
  public String getEndDate()
  {
    return this.endDate;
  }
  
  public void setEndDate(String paramString)
  {
    this.endDate = paramString;
  }
  
  public String getVersion()
  {
    return this.version;
  }
  
  public void setVersion(String paramString)
  {
    this.version = paramString;
  }
  
  public int getStatusCode()
  {
    return this.statusCode;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.statusCode = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.statusMsg;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.statusMsg = paramString;
  }
  
  public String getSelectionCriteria()
  {
    return this.selectionCriteria;
  }
  
  public void setSelectionCriteria(String paramString)
  {
    this.selectionCriteria = paramString;
  }
  
  public long getTotalTrans()
  {
    return this.totalTrans;
  }
  
  public void setTotalTrans(long paramLong)
  {
    this.totalTrans = paramLong;
  }
  
  public long getPageSize()
  {
    return this.pageSize;
  }
  
  public void setPageSize(long paramLong)
  {
    this.pageSize = paramLong;
  }
  
  public String getHistId()
  {
    return this.histId;
  }
  
  public void setHistId(String paramString)
  {
    this.histId = paramString;
  }
  
  public String getCursorId()
  {
    return this.cursorId;
  }
  
  public void setCursorId(String paramString)
  {
    this.cursorId = paramString;
  }
  
  public String getMinAmount()
  {
    return this.minAmount;
  }
  
  public void setMinAmount(String paramString)
  {
    this.minAmount = paramString;
  }
  
  public String getMaxAmount()
  {
    return this.maxAmount;
  }
  
  public void setMaxAmount(String paramString)
  {
    this.maxAmount = paramString;
  }
  
  public String getCategory()
  {
    return this.category;
  }
  
  public void setCategory(String paramString)
  {
    this.category = paramString;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.BPWHist
 * JD-Core Version:    0.7.0.1
 */