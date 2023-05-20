package com.ffusion.tw.plugins;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.FundsTransactionTemplate;
import com.ffusion.tw.interfaces.ITransactionWarehousePlugin;
import java.util.HashMap;
import java.util.Locale;

public class TemplatesPlugin
  implements ITransactionWarehousePlugin
{
  public void initialize(HashMap paramHashMap)
    throws Throwable
  {}
  
  public IApprovable getApprovable(int paramInt, String paramString, HashMap paramHashMap)
    throws Throwable
  {
    FundsTransactionTemplate localFundsTransactionTemplate = null;
    if (paramString == null) {
      return getApprovable(paramInt, paramHashMap);
    }
    if ((paramInt == 1001) || (paramInt == 1007) || (paramInt == 1003))
    {
      localFundsTransactionTemplate = new FundsTransactionTemplate(Locale.getDefault());
      localFundsTransactionTemplate.fromXML(paramString);
    }
    return localFundsTransactionTemplate;
  }
  
  public IApprovable getApprovable(int paramInt, HashMap paramHashMap)
    throws Throwable
  {
    FundsTransactionTemplate localFundsTransactionTemplate = null;
    if ((paramInt == 1001) || (paramInt == 1007) || (paramInt == 1003)) {
      localFundsTransactionTemplate = new FundsTransactionTemplate(Locale.getDefault());
    }
    return localFundsTransactionTemplate;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tw.plugins.TemplatesPlugin
 * JD-Core Version:    0.7.0.1
 */