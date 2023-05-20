package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;

public class ProcessingWindowList
  implements Serializable
{
  protected String[] jdField_new;
  protected String[] jdField_int;
  protected String[] jdField_for;
  protected String[] a;
  protected int jdField_do;
  protected String jdField_if;
  ProcessingWindowInfo[] jdField_try;
  
  public String[] getFIId()
  {
    return this.jdField_new;
  }
  
  public void setFIId(String[] paramArrayOfString)
  {
    this.jdField_new = paramArrayOfString;
  }
  
  public String[] getCustomerId()
  {
    return this.jdField_int;
  }
  
  public void setCustomerId(String[] paramArrayOfString)
  {
    this.jdField_int = paramArrayOfString;
  }
  
  public String[] getPmtType()
  {
    return this.jdField_for;
  }
  
  public void setPmtType(String[] paramArrayOfString)
  {
    this.jdField_for = paramArrayOfString;
  }
  
  public String[] getPmtSubType()
  {
    return this.a;
  }
  
  public void setPmtSubType(String[] paramArrayOfString)
  {
    this.a = paramArrayOfString;
  }
  
  public ProcessingWindowInfo[] getProcessingWindows()
  {
    return this.jdField_try;
  }
  
  public void setProcessingWindows(ProcessingWindowInfo[] paramArrayOfProcessingWindowInfo)
  {
    this.jdField_try = paramArrayOfProcessingWindowInfo;
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
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ProcessingWindowList
 * JD-Core Version:    0.7.0.1
 */