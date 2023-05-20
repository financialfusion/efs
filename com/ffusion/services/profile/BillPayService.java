package com.ffusion.services.profile;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.billpay.Payee;
import com.ffusion.beans.billpay.Payees;
import com.ffusion.efs.adapters.profile.PayeeAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.ProfileBillPay;
import java.util.HashMap;
import java.util.ListIterator;

public class BillPayService
  implements ProfileBillPay
{
  public void initialize()
    throws ProfileException
  {}
  
  public Payees getPayees(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return PayeeAdapter.getCustomerPayees(paramSecureUser);
  }
  
  public Payees addPayees(SecureUser paramSecureUser, Payees paramPayees, HashMap paramHashMap)
    throws ProfileException
  {
    ListIterator localListIterator = paramPayees.listIterator();
    while (localListIterator.hasNext()) {
      PayeeAdapter.addCustomerPayee(paramSecureUser, (Payee)localListIterator.next(), paramHashMap);
    }
    return paramPayees;
  }
  
  public Payee modifyPayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws ProfileException
  {
    PayeeAdapter.modifyCustomerPayee(paramSecureUser, paramPayee);
    return paramPayee;
  }
  
  public Payee deletePayee(SecureUser paramSecureUser, Payee paramPayee, HashMap paramHashMap)
    throws ProfileException
  {
    PayeeAdapter.deleteCustomerPayee(paramSecureUser, paramPayee);
    return paramPayee;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.BillPayService
 * JD-Core Version:    0.7.0.1
 */