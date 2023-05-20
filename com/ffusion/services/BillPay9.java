package com.ffusion.services;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.csil.CSILException;
import java.util.HashMap;

public abstract interface BillPay9
  extends BillPay8
{
  public abstract Currency getDefaultCurrency()
    throws CSILException;
  
  public abstract Payee getGlobalPayee(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BillPay9
 * JD-Core Version:    0.7.0.1
 */