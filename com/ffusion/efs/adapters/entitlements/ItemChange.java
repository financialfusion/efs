package com.ffusion.efs.adapters.entitlements;

class ItemChange
{
  public Object item;
  public long time;
  
  public ItemChange(Object paramObject, long paramLong)
  {
    this.item = paramObject;
    this.time = paramLong;
  }
  
  public boolean equals(Object paramObject)
  {
    try
    {
      ItemChange localItemChange = (ItemChange)paramObject;
      return (localItemChange.time == this.time) && (localItemChange.item.equals(this.item));
    }
    catch (Exception localException) {}
    return false;
  }
  
  public int hashCode()
  {
    return this.item.hashCode();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.efs.adapters.entitlements.ItemChange
 * JD-Core Version:    0.7.0.1
 */