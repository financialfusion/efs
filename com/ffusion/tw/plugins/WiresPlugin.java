package com.ffusion.tw.plugins;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.tw.interfaces.ITransactionWarehousePlugin;
import java.util.HashMap;
import java.util.Locale;

public class WiresPlugin
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
    if ((paramInt == 5) || (paramInt == 6))
    {
      localObject = new WireTransfer(Locale.getDefault());
      ((IApprovable)localObject).fromXML(paramString);
    }
    else if (paramInt == 10)
    {
      localObject = new WireBatch(Locale.getDefault());
      ((IApprovable)localObject).fromXML(paramString);
    }
    return localObject;
  }
  
  public IApprovable getApprovable(int paramInt, HashMap paramHashMap)
    throws Throwable
  {
    Object localObject = null;
    if ((paramInt == 5) || (paramInt == 6)) {
      localObject = new WireTransfer(Locale.getDefault());
    } else if (paramInt == 10) {
      localObject = new WireBatch(Locale.getDefault());
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tw.plugins.WiresPlugin
 * JD-Core Version:    0.7.0.1
 */