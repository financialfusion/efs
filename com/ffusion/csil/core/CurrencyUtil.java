package com.ffusion.csil.core;

import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.business.Business;
import com.ffusion.efs.adapters.profile.AffiliateBankAdapter;
import com.ffusion.efs.adapters.profile.BusinessAdapter;
import com.ffusion.efs.adapters.profile.ProfileException;

public class CurrencyUtil
{
  public static String getCurrencyCodeByBusinessId(int paramInt)
  {
    Business localBusiness = null;
    AffiliateBank localAffiliateBank = null;
    try
    {
      localBusiness = BusinessAdapter.getBusiness(paramInt);
      if (localBusiness != null) {
        localAffiliateBank = AffiliateBankAdapter.getAffiliateBankByID(null, localBusiness.getAffiliateBankID());
      }
    }
    catch (ProfileException localProfileException)
    {
      localProfileException.printStackTrace();
      return null;
    }
    if (localAffiliateBank != null) {
      return localAffiliateBank.getCurrencyCode();
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.CurrencyUtil
 * JD-Core Version:    0.7.0.1
 */