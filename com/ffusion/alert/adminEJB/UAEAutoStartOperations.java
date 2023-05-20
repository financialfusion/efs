package com.ffusion.alert.adminEJB;

import org.omg.CORBA.portable.IDLEntity;

public abstract interface UAEAutoStartOperations
  extends IDLEntity
{
  public abstract void run();
  
  public abstract void start();
  
  public abstract void stop();
}


/* Location:           D:\drops\jd\jars\UAEAdminEJB20.jar
 * Qualified Name:     com.ffusion.alert.adminEJB.UAEAutoStartOperations
 * JD-Core Version:    0.7.0.1
 */