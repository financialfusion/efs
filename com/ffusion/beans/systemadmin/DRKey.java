package com.ffusion.beans.systemadmin;

import com.ffusion.beans.ExtendABean;

public class DRKey
  extends ExtendABean
{
  private String aXq;
  private String aXr;
  
  public DRKey()
  {
    this.aXq = null;
    this.aXr = null;
  }
  
  public DRKey(String paramString1, String paramString2)
  {
    this.aXq = paramString1;
    this.aXr = paramString2;
  }
  
  public boolean equals(Object paramObject)
  {
    boolean bool = true;
    if ((paramObject instanceof DRKey))
    {
      DRKey localDRKey = (DRKey)paramObject;
      bool = (bool) && (((this.aXq == null) && (this.aXq == localDRKey.getDataType())) || (this.aXq.equals(localDRKey.getDataType())));
      bool = (bool) && (((this.aXr == null) && (this.aXr == localDRKey.getDataClass())) || (this.aXr.equals(localDRKey.getDataClass())));
    }
    else
    {
      bool = false;
    }
    return bool;
  }
  
  public String getDataClass()
  {
    return this.aXr;
  }
  
  public String getDataType()
  {
    return this.aXq;
  }
  
  public void setDataClass(String paramString)
  {
    this.aXr = paramString;
  }
  
  public void setDataType(String paramString)
  {
    this.aXq = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.systemadmin.DRKey
 * JD-Core Version:    0.7.0.1
 */