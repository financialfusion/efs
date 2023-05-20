package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.affiliatebank.AffiliateBanks;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;
import java.util.logging.Level;

public class Terms
  extends Initialize
{
  private static final Entitlement axC = new Entitlement("BCTermsAdministration", null, null);
  private static final String axz = "com.ffusion.util.logging.audit.terms";
  private static final String axA = "AuditMessage_1";
  private static final String axB = "AuditMessage_2";
  private static final String axy = "AuditMessage_3";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.Terms.initialize");
    com.ffusion.csil.handlers.Terms.initialize(paramHashMap);
  }
  
  public static void resetTermsAcceptance(SecureUser paramSecureUser, AffiliateBanks paramAffiliateBanks, HashMap paramHashMap)
    throws CSILException
  {
    LocalizableString localLocalizableString = null;
    String str1 = "com.ffusion.csil.core.Terms.resetTermsAcceptance";
    String str2;
    if (paramSecureUser != null)
    {
      if ((paramSecureUser.getUserType() != 2) || ((paramSecureUser.getUserType() == 2) && (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), axC))))
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.terms", "AuditMessage_1", null);
        Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
      if ((paramAffiliateBanks == null) || (paramAffiliateBanks.getSize().equals("0")))
      {
        str2 = "The AffiliateBanks specified is the wrong type or is null.";
        DebugLog.log(Level.FINE, str2);
        throw new CSILException(str1, 91004, str2);
      }
      long l = System.currentTimeMillis();
      com.ffusion.csil.handlers.Terms.resetTermsAcceptance(paramSecureUser, paramAffiliateBanks, paramHashMap);
      PerfLog.log(str1, l, true);
      jdMethod_for(paramSecureUser, paramAffiliateBanks);
    }
    else
    {
      str2 = "The SecureUser specified is the wrong type or is null.";
      DebugLog.log(Level.FINE, str2);
      throw new CSILException(str1, 103, str2);
    }
  }
  
  public static boolean checkTermsAcceptance(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    LocalizableString localLocalizableString = null;
    String str1 = "com.ffusion.csil.core.Terms.checkTermsAcceptance";
    if (paramSecureUser != null)
    {
      if (paramSecureUser.getUserType() == 2)
      {
        localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.terms", "AuditMessage_2", null);
        Initialize.logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
        throw new CSILException(str1, 20001);
      }
      long l = System.currentTimeMillis();
      boolean bool = com.ffusion.csil.handlers.Terms.checkTermsAcceptance(paramSecureUser, paramHashMap);
      PerfLog.log(str1, l, true);
      return bool;
    }
    String str2 = "The SecureUser specified is the wrong type or is null.";
    DebugLog.log(Level.FINE, str2);
    throw new CSILException(str1, 103, str2);
  }
  
  private static void jdMethod_for(SecureUser paramSecureUser, AffiliateBanks paramAffiliateBanks)
  {
    String str1 = "AuditMessage_3";
    for (int i = 0; i < paramAffiliateBanks.size(); i++)
    {
      AffiliateBank localAffiliateBank = (AffiliateBank)paramAffiliateBanks.get(i);
      String str2 = localAffiliateBank.getAffiliateBankName().trim();
      Object[] arrayOfObject = new Object[1];
      arrayOfObject[0] = str2;
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.terms", str1, arrayOfObject);
      Initialize.audit(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID(), 6201);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Terms
 * JD-Core Version:    0.7.0.1
 */