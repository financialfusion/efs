package com.ffusion.ffs.bpw.backend;

import com.ffusion.ffs.bpw.interfaces.IntraTrnRslt;

public class BackendStatus
{
  private IntraTrnRslt a;
  private boolean jdField_if;
  
  public BackendStatus(IntraTrnRslt paramIntraTrnRslt, boolean paramBoolean)
  {
    this.a = paramIntraTrnRslt;
    this.jdField_if = paramBoolean;
  }
  
  public void setResult(IntraTrnRslt paramIntraTrnRslt)
  {
    this.a = paramIntraTrnRslt;
    this.jdField_if = true;
  }
  
  public IntraTrnRslt getResult()
  {
    return this.a;
  }
  
  public boolean isProcessComplete()
  {
    return this.jdField_if;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.backend.BackendStatus
 * JD-Core Version:    0.7.0.1
 */