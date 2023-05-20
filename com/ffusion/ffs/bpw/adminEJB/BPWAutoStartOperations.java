package com.ffusion.ffs.bpw.adminEJB;

import org.omg.CORBA.portable.IDLEntity;

public abstract interface BPWAutoStartOperations
  extends IDLEntity
{
  public abstract void run();
  
  public abstract void start();
  
  public abstract void stop();
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB.BPWAutoStartOperations
 * JD-Core Version:    0.7.0.1
 */