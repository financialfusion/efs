package com.ffusion.beans.messages;

import com.ffusion.util.Sortable;

public class GlobalMessageFilter
  implements Sortable, GlobalMessageConsts
{
  int aoZ;
  String ao0;
  
  public GlobalMessageFilter() {}
  
  public GlobalMessageFilter(int paramInt, String paramString)
  {
    this.aoZ = paramInt;
    this.ao0 = paramString;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    GlobalMessageFilter localGlobalMessageFilter = (GlobalMessageFilter)paramObject;
    int i = 1;
    if (paramString.equals("FILTERTYPE"))
    {
      if (this.aoZ == localGlobalMessageFilter.getFilterType()) {
        i = 0;
      } else {
        i = this.aoZ > localGlobalMessageFilter.getFilterType() ? 1 : -1;
      }
    }
    else if (paramString.equals("FILTERVALUE")) {
      i = this.ao0.compareTo(localGlobalMessageFilter.getFilterValue());
    }
    return i;
  }
  
  public int getFilterType()
  {
    return this.aoZ;
  }
  
  public void setFilterType(int paramInt)
  {
    this.aoZ = paramInt;
  }
  
  public String getFilterValue()
  {
    return this.ao0;
  }
  
  public void setFilterValue(String paramString)
  {
    this.ao0 = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.GlobalMessageFilter
 * JD-Core Version:    0.7.0.1
 */