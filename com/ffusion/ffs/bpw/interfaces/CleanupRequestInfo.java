package com.ffusion.ffs.bpw.interfaces;

import java.util.StringTokenizer;

public class CleanupRequestInfo
  extends BPWInfoBase
  implements Cloneable
{
  protected String qK;
  protected String[] qJ;
  protected String[] qH;
  protected String qI;
  
  public void setCustomerId(String paramString)
  {
    this.qK = paramString;
  }
  
  public String getCustomerId()
  {
    return this.qK;
  }
  
  public void setPaymentTypeList(String[] paramArrayOfString)
  {
    this.qJ = paramArrayOfString;
  }
  
  public String[] getPaymentTypeList()
  {
    return this.qJ;
  }
  
  public void setPaymentTypeList(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      return;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    int i = localStringTokenizer.countTokens();
    String[] arrayOfString = new String[i];
    int j = 0;
    while ((localStringTokenizer.hasMoreTokens()) && (j < i)) {
      arrayOfString[(j++)] = localStringTokenizer.nextToken().trim();
    }
    this.qJ = arrayOfString;
  }
  
  public String getPaymentTypeList(String paramString)
  {
    if (this.qJ == null) {
      return null;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < this.qJ.length; i++)
    {
      if (i > 0) {
        localStringBuffer.append(paramString.trim());
      }
      localStringBuffer.append(this.qJ[i]);
    }
    return localStringBuffer.toString();
  }
  
  public void setAgeInDaysList(String[] paramArrayOfString)
  {
    this.qH = paramArrayOfString;
  }
  
  public String[] getAgeInDaysList()
  {
    return this.qH;
  }
  
  public void setAgeInDaysList(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      return;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    int i = localStringTokenizer.countTokens();
    String[] arrayOfString = new String[i];
    int j = 0;
    while ((localStringTokenizer.hasMoreTokens()) && (j < i)) {
      arrayOfString[(j++)] = localStringTokenizer.nextToken().trim();
    }
    this.qH = arrayOfString;
  }
  
  public String getAgeInDaysList(String paramString)
  {
    if (this.qH == null) {
      return null;
    }
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < this.qH.length; i++)
    {
      if (i > 0) {
        localStringBuffer.append(paramString.trim());
      }
      localStringBuffer.append(this.qH[i]);
    }
    return localStringBuffer.toString();
  }
  
  public void setMemo(String paramString)
  {
    this.qI = paramString;
  }
  
  public String getMemo()
  {
    return this.qI;
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.qK != null) {
      localStringBuffer.append("customerId=").append(getCustomerId()).append(", ");
    } else {
      localStringBuffer.append("customerId=").append(", ");
    }
    if (this.qJ != null) {
      localStringBuffer.append("paymentTypeList=").append(getPaymentTypeList(";")).append(", ");
    } else {
      localStringBuffer.append("paymentTypeList=").append(", ");
    }
    if (this.qH != null) {
      localStringBuffer.append("ageInDaysList=").append(getAgeInDaysList(";")).append(", ");
    } else {
      localStringBuffer.append("ageInDaysList=").append(", ");
    }
    if (this.qI != null) {
      localStringBuffer.append("memo=").append(this.qI).append(", ");
    } else {
      localStringBuffer.append("memo=").append(", ");
    }
    return localStringBuffer.toString();
  }
  
  public Object clone()
  {
    CleanupRequestInfo localCleanupRequestInfo = new CleanupRequestInfo();
    localCleanupRequestInfo.qK = this.qK;
    localCleanupRequestInfo.qJ = this.qJ;
    localCleanupRequestInfo.qH = this.qH;
    localCleanupRequestInfo.qI = this.qI;
    return localCleanupRequestInfo;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.CleanupRequestInfo
 * JD-Core Version:    0.7.0.1
 */