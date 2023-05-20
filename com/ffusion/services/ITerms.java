package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.services.terms.TermsException;
import java.util.HashMap;

public abstract interface ITerms
{
  public abstract void initialize(HashMap paramHashMap)
    throws TermsException;
  
  public abstract void resetTermsAcceptance(SecureUser paramSecureUser, AffiliateBanks paramAffiliateBanks, HashMap paramHashMap)
    throws TermsException;
  
  public abstract boolean checkTermsAcceptance(SecureUser paramSecureUser, HashMap paramHashMap)
    throws TermsException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ITerms
 * JD-Core Version:    0.7.0.1
 */