package com.ffusion.services;

import com.ffusion.beans.SecureUser;

public abstract interface BillPay4
  extends BillPay3, SignOn3
{
  public abstract void setSecureUser(SecureUser paramSecureUser);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BillPay4
 * JD-Core Version:    0.7.0.1
 */