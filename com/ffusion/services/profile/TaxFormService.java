package com.ffusion.services.profile;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.ach.TaxForms;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.efs.adapters.profile.TaxFormAdapter;
import java.util.HashMap;

public class TaxFormService
  implements com.ffusion.services.TaxFormService
{
  public void initialize()
    throws ProfileException
  {}
  
  public TaxForms getTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException
  {
    return TaxFormAdapter.getTaxForms(paramSecureUser);
  }
  
  public TaxForm addTaxForm(SecureUser paramSecureUser, TaxForm paramTaxForm, HashMap paramHashMap)
    throws ProfileException
  {
    return TaxFormAdapter.addTaxForm(paramSecureUser, paramTaxForm);
  }
  
  public void deleteTaxForm(SecureUser paramSecureUser, TaxForm paramTaxForm, HashMap paramHashMap)
    throws ProfileException
  {
    TaxFormAdapter.deleteTaxForm(paramSecureUser, paramTaxForm);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.profile.TaxFormService
 * JD-Core Version:    0.7.0.1
 */