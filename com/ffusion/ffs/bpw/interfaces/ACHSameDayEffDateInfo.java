package com.ffusion.ffs.bpw.interfaces;

import java.util.HashSet;

public class ACHSameDayEffDateInfo
  extends BPWInfoBase
{
  protected int qy;
  protected String qw;
  protected HashSet qx;
  
  public int getObjectType()
  {
    return this.qy;
  }
  
  public void setObjectType(int paramInt)
  {
    this.qy = paramInt;
  }
  
  public String getObjectId()
  {
    return this.qw;
  }
  
  public void setObjectId(String paramString)
  {
    this.qw = paramString;
  }
  
  public HashSet getSECs()
  {
    return this.qx;
  }
  
  public void setSECs(HashSet paramHashSet)
  {
    this.qx = paramHashSet;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHSameDayEffDateInfo
 * JD-Core Version:    0.7.0.1
 */