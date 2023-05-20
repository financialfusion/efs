package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface BankEmployeeAdmin8
  extends BankEmployeeAdmin7
{
  public abstract boolean getInfoForAuditing(BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void resetBankEmployeePassword(SecureUser paramSecureUser, BankEmployee paramBankEmployee, String paramString, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void modifyBankEmployeePasswordStatus(SecureUser paramSecureUser, BankEmployee paramBankEmployee, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void modifyBankEmployeePassword(SecureUser paramSecureUser, BankEmployee paramBankEmployee, String paramString1, String paramString2, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BankEmployeeAdmin8
 * JD-Core Version:    0.7.0.1
 */