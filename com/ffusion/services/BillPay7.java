package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.Payment;
import com.ffusion.csil.CSILException;
import java.util.HashMap;

public abstract interface BillPay7
  extends BillPay6
{
  public abstract Payment getPaymentByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BillPay7
 * JD-Core Version:    0.7.0.1
 */