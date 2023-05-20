package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class WireReleaseInfo
  implements Serializable
{
  protected String jdField_new;
  protected String jdField_char;
  protected String[] jdField_byte;
  protected String a;
  protected String jdField_try;
  protected String jdField_int;
  protected String jdField_for;
  protected int jdField_case;
  protected int jdField_do = -1;
  protected String jdField_if;
  
  public String getFIId()
  {
    return this.jdField_new;
  }
  
  public void setFIId(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String getCustomerId()
  {
    return this.jdField_char;
  }
  
  public void setCustomerId(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String[] getSubmittedBy()
  {
    return this.jdField_byte;
  }
  
  public void setSubmittedBy(String[] paramArrayOfString)
  {
    this.jdField_byte = paramArrayOfString;
  }
  
  public String getStartDate()
  {
    return this.a;
  }
  
  public void setStartDate(String paramString)
  {
    this.a = paramString;
  }
  
  public String getEndDate()
  {
    return this.jdField_try;
  }
  
  public void setEndDate(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getMinAmount()
  {
    return this.jdField_int;
  }
  
  public void setMinAmount(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getMaxAmount()
  {
    return this.jdField_for;
  }
  
  public void setMaxAmount(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public int getWireCount()
  {
    return this.jdField_case;
  }
  
  public void setWireCount(int paramInt)
  {
    this.jdField_case = paramInt;
  }
  
  public int getStatusCode()
  {
    return this.jdField_do;
  }
  
  public void setStatusCode(int paramInt)
  {
    this.jdField_do = paramInt;
  }
  
  public String getStatusMsg()
  {
    return this.jdField_if;
  }
  
  public void setStatusMsg(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.jdField_new != null) {
      localStringBuffer.append("fIId=").append(this.jdField_new);
    } else {
      localStringBuffer.append("fIId=");
    }
    if (this.jdField_char != null) {
      localStringBuffer.append(",customerId=").append(this.jdField_char);
    } else {
      localStringBuffer.append(",customerId=");
    }
    if ((this.jdField_byte != null) && (this.jdField_byte.length > 0))
    {
      localStringBuffer.append(",submittedBy={");
      for (int i = 0; i < this.jdField_byte.length; i++)
      {
        if (i > 0) {
          localStringBuffer.append(":");
        }
        localStringBuffer.append(this.jdField_byte[i]);
      }
      localStringBuffer.append("}");
    }
    else
    {
      localStringBuffer.append(",submittedBy=");
    }
    if (this.a != null) {
      localStringBuffer.append(",startDate=").append(this.a);
    } else {
      localStringBuffer.append(",startDate=");
    }
    if (this.jdField_try != null) {
      localStringBuffer.append(",endDate=").append(this.jdField_try);
    } else {
      localStringBuffer.append(",endDate=");
    }
    if (this.jdField_int != null) {
      localStringBuffer.append(",minAmount=").append(this.jdField_int);
    } else {
      localStringBuffer.append(",minAmount=");
    }
    if (this.jdField_for != null) {
      localStringBuffer.append(",maxAmount=").append(this.jdField_for);
    } else {
      localStringBuffer.append(",maxAmount=");
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.WireReleaseInfo
 * JD-Core Version:    0.7.0.1
 */