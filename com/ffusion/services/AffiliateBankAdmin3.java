package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface AffiliateBankAdmin3
  extends AffiliateBankAdmin2
{
  public abstract ArrayList getRestrictedCalculations(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void saveRestrictedCalculations(SecureUser paramSecureUser, int paramInt, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract AffiliateBank getAffiliateBankByBPWID(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.AffiliateBankAdmin3
 * JD-Core Version:    0.7.0.1
 */