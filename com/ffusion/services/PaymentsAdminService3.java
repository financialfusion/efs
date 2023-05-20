package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface PaymentsAdminService3
  extends PaymentsAdminService2
{
  public abstract ArrayList getGlobalPayeeGroups(SecureUser paramSecureUser, HashMap paramHashMap)
    throws PAException;
  
  public abstract Payees searchGlobalPayees(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws PAException;
  
  public abstract Payee getGlobalPayee(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws PAException;
  
  public abstract void addGlobalPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws PAException;
  
  public abstract Payee modifyGlobalPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws PAException;
  
  public abstract void deleteGlobalPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws PAException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.PaymentsAdminService3
 * JD-Core Version:    0.7.0.1
 */