package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.beans.billpay.RecPayment;
import com.ffusion.csil.CSILException;
import java.util.HashMap;

public abstract interface BillPay5
  extends BillPay4
{
  public abstract Payee getPayeeByID(SecureUser paramSecureUser, String paramString, HashMap paramHashMap);
  
  public abstract RecPayment getRecPaymentByID(SecureUser paramSecureUser, String paramString, Accounts paramAccounts, Payees paramPayees, HashMap paramHashMap)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BillPay5
 * JD-Core Version:    0.7.0.1
 */