package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class InterACHInfo
  implements Serializable
{
  public BPWFIInfo bpwFIInfo = new BPWFIInfo();
  public ACHFIInfo achFIInfo = new ACHFIInfo();
  public ACHCompanyInfo achCompInfo = new ACHCompanyInfo();
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.InterACHInfo
 * JD-Core Version:    0.7.0.1
 */