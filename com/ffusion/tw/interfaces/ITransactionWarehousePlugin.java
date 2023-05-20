package com.ffusion.tw.interfaces;

import com.ffusion.approvals.IApprovable;
import java.util.HashMap;

public abstract interface ITransactionWarehousePlugin
{
  public abstract void initialize(HashMap paramHashMap)
    throws Throwable;
  
  public abstract IApprovable getApprovable(int paramInt, String paramString, HashMap paramHashMap)
    throws Throwable;
  
  public abstract IApprovable getApprovable(int paramInt, HashMap paramHashMap)
    throws Throwable;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tw.interfaces.ITransactionWarehousePlugin
 * JD-Core Version:    0.7.0.1
 */