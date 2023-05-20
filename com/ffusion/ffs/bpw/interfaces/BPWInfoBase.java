package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;
import org.apache.commons.lang.builder.StandardToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BPWInfoBase
  implements Serializable
{
  private static ToStringStyle lg;
  public Object extraFields = null;
  protected String logId;
  protected String submittedBy;
  protected String submitDate;
  protected int statusCode = 0;
  protected String statusMsg = "Success";
  protected String ld;
  protected String le;
  protected String lc;
  protected int lf = 0;
  protected CustomerInfo customerInfo;
  
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
  
  public String getLogId()
  {
    return this.logId;
  }
  
  public void setLogId(String paramString)
  {
    this.logId = paramString;
  }
  
  public String getSubmittedBy()
  {
    return this.submittedBy;
  }
  
  public void setSubmittedBy(String paramString)
  {
    this.submittedBy = paramString;
  }
  
  public String getSubmitDate()
  {
    return this.submitDate;
  }
  
  public void setSubmitDate(String paramString)
  {
    this.submitDate = paramString;
  }
  
  public String getAgentId()
  {
    return this.ld;
  }
  
  public void setAgentId(String paramString)
  {
    this.ld = paramString;
  }
  
  public String getAgentName()
  {
    return this.le;
  }
  
  public void setAgentName(String paramString)
  {
    this.le = paramString;
  }
  
  public String getAgentType()
  {
    return this.lc;
  }
  
  public void setAgentType(String paramString)
  {
    this.lc = paramString;
  }
  
  public int getVersion()
  {
    return this.lf;
  }
  
  public void setVersion(int paramInt)
  {
    this.lf = paramInt;
  }
  
  public void increaseVersion()
  {
    this.lf += 1;
  }
  
  public CustomerInfo getCustomerInfo()
  {
    return this.customerInfo != null ? this.customerInfo : new CustomerInfo();
  }
  
  public void setCustomerInfo(CustomerInfo paramCustomerInfo)
  {
    this.customerInfo = paramCustomerInfo;
  }
  
  public boolean isCustomerInfoLoaded()
  {
    return (getCustomerInfo() != null) && (getCustomerInfo().getCustomerID() != null);
  }
  
  public String toString()
  {
    return ToStringBuilder.reflectionToString(this, ah());
  }
  
  private static ToStringStyle ah()
  {
    if (lg == null)
    {
      StandardToStringStyle localStandardToStringStyle = new StandardToStringStyle();
      localStandardToStringStyle.setFieldSeparator(";");
      localStandardToStringStyle.setArrayStart("[");
      localStandardToStringStyle.setArrayEnd("]");
      localStandardToStringStyle.setArraySeparator(",");
      lg = localStandardToStringStyle;
    }
    return lg;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.BPWInfoBase
 * JD-Core Version:    0.7.0.1
 */