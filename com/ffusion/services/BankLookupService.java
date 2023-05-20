package com.ffusion.services;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface BankLookupService
{
  public abstract void initialize()
    throws CSILException;
  
  public abstract FinancialInstitutions getFinancialInstitutions(SecureUser paramSecureUser, FinancialInstitution paramFinancialInstitution, int paramInt, HashMap paramHashMap)
    throws CSILException;
  
  public abstract FinancialInstitution updateFinancialInstitution(SecureUser paramSecureUser, FinancialInstitution paramFinancialInstitution, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ArrayList getCountries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.BankLookupService
 * JD-Core Version:    0.7.0.1
 */