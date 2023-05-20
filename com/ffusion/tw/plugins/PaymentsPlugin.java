package com.ffusion.tw.plugins;

import com.ffusion.approvals.IApprovable;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.beans.billpay.Payments;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.beans.billpay.RecPayments;
import com.ffusion.tw.interfaces.ITransactionWarehousePlugin;
import java.util.HashMap;
import java.util.Locale;

public class PaymentsPlugin
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
    if (paramInt == 3)
    {
      localObject = (Payment)new Payments(Locale.getDefault()).create();
      ((IApprovable)localObject).fromXML(paramString);
    }
    else if (paramInt == 4)
    {
      localObject = (RecPayment)new RecPayments(Locale.getDefault()).create();
      ((IApprovable)localObject).fromXML(paramString);
    }
    return localObject;
  }
  
  public IApprovable getApprovable(int paramInt, HashMap paramHashMap)
    throws Throwable
  {
    Object localObject = null;
    if (paramInt == 3) {
      localObject = (Payment)new Payments(Locale.getDefault()).create();
    } else if (paramInt == 4) {
      localObject = (RecPayment)new RecPayments(Locale.getDefault()).create();
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tw.plugins.PaymentsPlugin
 * JD-Core Version:    0.7.0.1
 */