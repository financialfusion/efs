package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.TaxForm;
import com.ffusion.beans.ach.TaxForms;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface TaxFormService
{
  public abstract void initialize()
    throws ProfileException;
  
  public abstract TaxForms getTaxForms(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract TaxForm addTaxForm(SecureUser paramSecureUser, TaxForm paramTaxForm, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void deleteTaxForm(SecureUser paramSecureUser, TaxForm paramTaxForm, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.TaxFormService
 * JD-Core Version:    0.7.0.1
 */