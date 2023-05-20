package com.ffusion.tw.plugins;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.tw.interfaces.ITransactionWarehousePlugin;
import java.util.HashMap;
import java.util.Locale;

public class ACHPlugin
  implements ITransactionWarehousePlugin
{
  public void initialize(HashMap paramHashMap)
    throws Throwable
  {}
  
  public IApprovable getApprovable(int paramInt, String paramString, HashMap paramHashMap)
    throws Throwable
  {
    ACHBatch localACHBatch = null;
    if (paramString == null) {
      return getApprovable(paramInt, paramHashMap);
    }
    if ((paramInt == 7) || (paramInt == 8))
    {
      localACHBatch = new ACHBatch(Locale.getDefault());
      localACHBatch.fromXML(paramString);
    }
    return localACHBatch;
  }
  
  public IApprovable getApprovable(int paramInt, HashMap paramHashMap)
    throws Throwable
  {
    ACHBatch localACHBatch = null;
    if ((paramInt == 7) || (paramInt == 8)) {
      localACHBatch = new ACHBatch(Locale.getDefault());
    }
    return localACHBatch;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tw.plugins.ACHPlugin
 * JD-Core Version:    0.7.0.1
 */