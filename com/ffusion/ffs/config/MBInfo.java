package com.ffusion.ffs.config;

import java.io.Serializable;

public class MBInfo
  implements Serializable
{
  private static final long jdField_do = 12345678910L;
  private MBConfigInfo a = null;
  private MBConfigInfo jdField_if = null;
  
  public MBInfo()
  {
    this.a = new MBConfigInfo();
    this.jdField_if = new MBConfigInfo();
  }
  
  public MBInfo(MBConfigInfo paramMBConfigInfo1, MBConfigInfo paramMBConfigInfo2)
  {
    this.a = paramMBConfigInfo1;
    this.jdField_if = paramMBConfigInfo2;
  }
  
  public MBConfigInfo getDesignInfo()
  {
    return this.a;
  }
  
  public MBConfigInfo getRuntimeInfo()
  {
    return this.jdField_if;
  }
  
  public void setDesignInfo(MBConfigInfo paramMBConfigInfo)
  {
    this.a = paramMBConfigInfo;
  }
  
  public void setRuntimeInfo(MBConfigInfo paramMBConfigInfo)
  {
    this.jdField_if = paramMBConfigInfo;
  }
}


/* Location:           D:\drops\jd\jars\ffscore.jar
 * Qualified Name:     com.ffusion.ffs.config.MBInfo
 * JD-Core Version:    0.7.0.1
 */