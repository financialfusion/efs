package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.TrackingIDGenerator;
import java.util.HashMap;

public class TTYAdmin
{
  public static final String TTY_CHANNEL = "channel";
  public static final String TTY_TRACKINGID = "ttytrackingid";
  private static final String jdField_if = "com.ffusion.util.logging.audit.ttyadmin";
  private static final String jdField_do = "AuditMessage_1";
  private static final String a = "AuditMessage_2";
  
  public static void logAuditRecord(SecureUser paramSecureUser, String paramString1, String paramString2, int paramInt)
  {
    Initialize.audit(paramSecureUser, paramString2, paramString1, paramInt);
  }
  
  public static void logAuditRecord(SecureUser paramSecureUser, String paramString, ILocalizable paramILocalizable, int paramInt)
  {
    Initialize.audit(paramSecureUser, paramILocalizable, paramString, paramInt);
  }
  
  public static boolean isEnabled(HashMap paramHashMap)
  {
    boolean bool = false;
    if ((paramHashMap != null) && (paramHashMap.get("channel") != null) && (((String)paramHashMap.get("channel")).equalsIgnoreCase("TTY"))) {
      bool = true;
    }
    return bool;
  }
  
  public static boolean isEntitled(SecureUser paramSecureUser)
    throws CSILException
  {
    boolean bool = false;
    if ((paramSecureUser != null) && (Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), new Entitlement("TTY", null, null)))) {
      bool = true;
    }
    return bool;
  }
  
  public static void addTTYSupport(HashMap paramHashMap, String paramString)
  {
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    paramHashMap.put("channel", "TTY");
    paramHashMap.put("ttytrackingid", paramString);
  }
  
  public static void removeTTYSupport(HashMap paramHashMap)
  {
    if (paramHashMap != null) {
      paramHashMap.remove("channel");
    }
  }
  
  public static void login(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    if (paramSecureUser != null)
    {
      String str = (String)paramHashMap.get("ttytrackingid");
      if (str == null) {
        str = TrackingIDGenerator.GetNextID();
      }
      paramSecureUser.set("TTY", "TTY");
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ttyadmin", "AuditMessage_1", null);
      logAuditRecord(paramSecureUser, str, localLocalizableString, 5408);
    }
  }
  
  public static void logout(SecureUser paramSecureUser, String paramString)
  {
    if ((paramSecureUser != null) && (isEnabled(paramSecureUser)))
    {
      LocalizableString localLocalizableString = new LocalizableString("com.ffusion.util.logging.audit.ttyadmin", "AuditMessage_2", null);
      logAuditRecord(paramSecureUser, paramString, localLocalizableString, 5409);
    }
  }
  
  public static boolean isEnabled(SecureUser paramSecureUser)
  {
    return paramSecureUser.get("TTY") != null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.TTYAdmin
 * JD-Core Version:    0.7.0.1
 */