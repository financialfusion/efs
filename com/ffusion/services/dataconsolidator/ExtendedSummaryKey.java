package com.ffusion.services.dataconsolidator;

import com.ffusion.beans.accounts.AccountKey;

class ExtendedSummaryKey
  extends AccountKey
{
  public Integer typeCode = null;
  
  public ExtendedSummaryKey(String paramString1, String paramString2, String paramString3, Integer paramInteger)
  {
    super(paramString1, paramString2, paramString3);
    this.typeCode = paramInteger;
  }
  
  public int hashCode()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(getAccountID());
    localStringBuffer.append(",");
    localStringBuffer.append(getBankID());
    localStringBuffer.append(",");
    localStringBuffer.append(getRoutingNumber());
    localStringBuffer.append(this.typeCode.toString());
    return localStringBuffer.toString().hashCode();
  }
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if ((paramObject == null) || (!(paramObject instanceof ExtendedSummaryKey))) {
      return false;
    }
    ExtendedSummaryKey localExtendedSummaryKey = (ExtendedSummaryKey)paramObject;
    return ((this.typeCode != null) || (localExtendedSummaryKey.typeCode == null)) && (this.typeCode.equals(localExtendedSummaryKey.typeCode)) && (super.equals(paramObject));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.dataconsolidator.ExtendedSummaryKey
 * JD-Core Version:    0.7.0.1
 */