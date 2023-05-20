package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.io.Serializable;
import java.util.HashMap;

public abstract interface AffiliateBankAdmin
  extends Serializable
{
  public abstract AffiliateBanks getAffiliateBankNames(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract AffiliateBanks getAffiliateBanks(SecureUser paramSecureUser, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract AffiliateBank getAffiliateBankByID(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract AffiliateBank addAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void deleteAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void modifyAffiliateBank(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void initialize(String paramString)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AffiliateBankAdmin
 * JD-Core Version:    0.7.0.1
 */