package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface ProfileBillPay
{
  public abstract void initialize()
    throws ProfileException;
  
  public abstract Payees getPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Payees addPayees(SecureUser paramSecureUser, Payees paramPayees, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Payee modifyPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract Payee deletePayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ProfileBillPay
 * JD-Core Version:    0.7.0.1
 */