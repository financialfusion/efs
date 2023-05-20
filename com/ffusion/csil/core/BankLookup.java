package com.ffusion.csil.core;

import com.ffusion.banklookup.beans.FinancialInstitution;
import com.ffusion.banklookup.beans.FinancialInstitutions;
import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.BankLookupHandler;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;

public class BankLookup
  extends Initialize
{
  private static Entitlement azG = new Entitlement("BC Bank Lookup Search", null, null);
  private static Entitlement azK = new Entitlement("BC Bank Lookup Administration", null, null);
  private static final String azJ = "com.ffusion.util.logging.audit.banklookup";
  private static final String azF = "AuditMessage_1";
  private static final String azL = "AuditMessage_2";
  private static final String azI = "AuditMessage_3";
  private static final String azH = "AuditMessage_4";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.BankLookup.initialize");
    BankLookupHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return BankLookupHandler.getService();
  }
  
  public static FinancialInstitutions getFinancialInstitutions(SecureUser paramSecureUser, FinancialInstitution paramFinancialInstitution, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankLookupHandler.getFinancialInstitutions";
    FinancialInstitutions localFinancialInstitutions = null;
    long l = System.currentTimeMillis();
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azG))
    {
      localFinancialInstitutions = BankLookupHandler.getFinancialInstitutions(paramSecureUser, paramFinancialInstitution, paramInt, paramHashMap);
    }
    else
    {
      Object[] arrayOfObject = new Object[0];
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banklookup", "AuditMessage_1", arrayOfObject);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException("FileImporter processFiles failed.", 20001);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localFinancialInstitutions;
  }
  
  public static FinancialInstitution updateFinancialInstitution(SecureUser paramSecureUser, FinancialInstitution paramFinancialInstitution, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "com.ffusion.csil.handlers.BankLookupHandler.updateFinancialInstitutions";
    FinancialInstitution localFinancialInstitution = null;
    long l = System.currentTimeMillis();
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azK))
    {
      localFinancialInstitution = BankLookupHandler.updateFinancialInstitution(paramSecureUser, paramFinancialInstitution, paramHashMap);
    }
    else
    {
      arrayOfObject = new Object[0];
      localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banklookup", "AuditMessage_2", arrayOfObject);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException("BankLookup Update FinancialInstitution failed.", 20001);
    }
    PerfLog.log(str1, l, true);
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = paramFinancialInstitution.getInstitutionName();
    LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banklookup", "AuditMessage_3", arrayOfObject);
    String str2 = TrackingIDGenerator.GetNextID();
    audit(paramSecureUser, localLocalizableString, str2, 5281);
    debug(paramSecureUser, str1);
    return localFinancialInstitution;
  }
  
  public static ArrayList getCountries(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.BankLookupHandler.getCountries";
    long l = System.currentTimeMillis();
    ArrayList localArrayList = null;
    if (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), azK))
    {
      localArrayList = BankLookupHandler.getCountries(paramSecureUser, paramHashMap);
    }
    else
    {
      Object[] arrayOfObject = new Object[0];
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.banklookup", "AuditMessage_4", arrayOfObject);
      logEntitlementFault(paramSecureUser, localLocalizableString, TrackingIDGenerator.GetNextID());
      throw new CSILException("banklookup get countries failed.", 20001);
    }
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localArrayList;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.BankLookup
 * JD-Core Version:    0.7.0.1
 */