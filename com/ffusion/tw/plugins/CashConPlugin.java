package com.ffusion.tw.plugins;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.cashcon.CashCon;
import com.ffusion.tw.interfaces.ITransactionWarehousePlugin;
import java.util.HashMap;
import java.util.Locale;

public class CashConPlugin
  implements ITransactionWarehousePlugin
{
  public void initialize(HashMap paramHashMap)
    throws Throwable
  {}
  
  public IApprovable getApprovable(int paramInt, String paramString, HashMap paramHashMap)
    throws Throwable
  {
    CashCon localCashCon = null;
    if (paramString == null) {
      return getApprovable(paramInt, paramHashMap);
    }
    if (paramInt == 11)
    {
      localCashCon = new CashCon(Locale.getDefault());
      localCashCon.fromXML(paramString);
    }
    return localCashCon;
  }
  
  public IApprovable getApprovable(int paramInt, HashMap paramHashMap)
    throws Throwable
  {
    CashCon localCashCon = null;
    if (paramInt == 11) {
      localCashCon = new CashCon(Locale.getDefault());
    }
    return localCashCon;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tw.plugins.CashConPlugin
 * JD-Core Version:    0.7.0.1
 */