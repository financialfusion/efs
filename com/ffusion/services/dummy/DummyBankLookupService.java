package com.ffusion.services.dummy;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.services.BankLookupService;
import java.util.ArrayList;
import java.util.HashMap;

public class DummyBankLookupService
  implements BankLookupService
{
  public void initialize()
    throws CSILException
  {}
  
  public FinancialInstitutions getFinancialInstitutions(SecureUser paramSecureUser, FinancialInstitution paramFinancialInstitution, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    return null;
  }
  
  public FinancialInstitution updateFinancialInstitution(SecureUser paramSecureUser, FinancialInstitution paramFinancialInstitution, HashMap paramHashMap)
    throws CSILException
  {
    return null;
  }
  
  public ArrayList getCountries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.dummy.DummyBankLookupService
 * JD-Core Version:    0.7.0.1
 */