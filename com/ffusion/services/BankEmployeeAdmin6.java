package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface BankEmployeeAdmin6
  extends BankEmployeeAdmin5
{
  public abstract boolean hasDirectReports(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract String getBankEmployeeCount(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BankEmployeeAdmin6
 * JD-Core Version:    0.7.0.1
 */