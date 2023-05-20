package com.ffusion.tw.plugins;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.banking.RecTransfer;
import com.ffusion.beans.banking.Transfer;
import com.ffusion.tw.interfaces.ITransactionWarehousePlugin;
import java.util.HashMap;
import java.util.Locale;

public class TransfersPlugin
  implements ITransactionWarehousePlugin
{
  public void initialize(HashMap paramHashMap)
    throws Throwable
  {}
  
  public IApprovable getApprovable(int paramInt, String paramString, HashMap paramHashMap)
    throws Throwable
  {
    Object localObject = null;
    if (paramString == null) {
      return getApprovable(paramInt, paramHashMap);
    }
    if (paramInt == 1)
    {
      localObject = new Transfer(Locale.getDefault());
      ((IApprovable)localObject).fromXML(paramString);
    }
    else if (paramInt == 2)
    {
      localObject = new RecTransfer(Locale.getDefault());
      ((IApprovable)localObject).fromXML(paramString);
    }
    return localObject;
  }
  
  public IApprovable getApprovable(int paramInt, HashMap paramHashMap)
    throws Throwable
  {
    Object localObject = null;
    if (paramInt == 1) {
      localObject = new Transfer(Locale.getDefault());
    } else if (paramInt == 2) {
      localObject = new RecTransfer(Locale.getDefault());
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tw.plugins.TransfersPlugin
 * JD-Core Version:    0.7.0.1
 */