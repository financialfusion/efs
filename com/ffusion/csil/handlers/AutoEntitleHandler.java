package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroup;
import com.ffusion.csil.beans.entitlements.EntitlementGroupMember;
import com.ffusion.csil.beans.entitlements.Entitlements;
import com.ffusion.services.autoentitle.AutoEntitleException;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class AutoEntitleHandler
  extends Initialize
{
  private static final String a6Q = "AutoEntitle";
  private static com.ffusion.services.AutoEntitle a6R = null;
  private static final String a6P = ",";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "AutoEntitle.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "AutoEntitle", str, 20107);
    a6R = (com.ffusion.services.AutoEntitle)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      a6R.initialize();
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(20107, localAutoEntitleException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return a6R;
  }
  
  public static com.ffusion.beans.autoentitle.AutoEntitle getSettings(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = new com.ffusion.beans.autoentitle.AutoEntitle();
      localAutoEntitle.setAffiliateBank(paramAffiliateBank);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      return a6R.getSettings(paramSecureUser, localAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.getSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.autoentitle.AutoEntitle getSettings(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = new com.ffusion.beans.autoentitle.AutoEntitle();
      localAutoEntitle.setEntitlementGroup(paramEntitlementGroup);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      return a6R.getSettings(paramSecureUser, localAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.getSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.autoentitle.AutoEntitle getSettings(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = new com.ffusion.beans.autoentitle.AutoEntitle();
      localAutoEntitle.setEntitlementGroupMember(paramEntitlementGroupMember);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      return a6R.getSettings(paramSecureUser, localAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.getSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.autoentitle.AutoEntitle getSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      return a6R.getSettings(paramSecureUser, paramAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.getSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.autoentitle.AutoEntitle getCumulativeSettings(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = new com.ffusion.beans.autoentitle.AutoEntitle();
      localAutoEntitle.setAffiliateBank(paramAffiliateBank);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      return a6R.getCumulativeSettings(paramSecureUser, localAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.getCumulativeSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.autoentitle.AutoEntitle getCumulativeSettings(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = new com.ffusion.beans.autoentitle.AutoEntitle();
      localAutoEntitle.setEntitlementGroup(paramEntitlementGroup);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      return a6R.getCumulativeSettings(paramSecureUser, localAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.getCumulativeSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.autoentitle.AutoEntitle getCumulativeSettings(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = new com.ffusion.beans.autoentitle.AutoEntitle();
      localAutoEntitle.setEntitlementGroupMember(paramEntitlementGroupMember);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      return a6R.getCumulativeSettings(paramSecureUser, localAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.getCumulativeSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.autoentitle.AutoEntitle getCumulativeSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      return a6R.getCumulativeSettings(paramSecureUser, paramAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.getCumulativeSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.autoentitle.AutoEntitle getCumulativeParentSettings(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = new com.ffusion.beans.autoentitle.AutoEntitle();
      localAutoEntitle.setEntitlementGroup(paramEntitlementGroup);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      return a6R.getCumulativeParentSettings(paramSecureUser, localAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.getCumulativeParentSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.autoentitle.AutoEntitle getCumulativeParentSettings(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = new com.ffusion.beans.autoentitle.AutoEntitle();
      localAutoEntitle.setEntitlementGroupMember(paramEntitlementGroupMember);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      return a6R.getCumulativeParentSettings(paramSecureUser, localAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.getCumulativeParentSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static com.ffusion.beans.autoentitle.AutoEntitle getCumulativeParentSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      return a6R.getCumulativeParentSettings(paramSecureUser, paramAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.getCumulativeParentSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteSettings(SecureUser paramSecureUser, AffiliateBank paramAffiliateBank, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = new com.ffusion.beans.autoentitle.AutoEntitle();
      localAutoEntitle.setAffiliateBank(paramAffiliateBank);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      a6R.deleteSettings(paramSecureUser, localAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.deleteSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteSettings(SecureUser paramSecureUser, EntitlementGroup paramEntitlementGroup, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = new com.ffusion.beans.autoentitle.AutoEntitle();
      localAutoEntitle.setEntitlementGroup(paramEntitlementGroup);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      a6R.deleteSettings(paramSecureUser, localAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.deleteSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteSettings(SecureUser paramSecureUser, EntitlementGroupMember paramEntitlementGroupMember, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      com.ffusion.beans.autoentitle.AutoEntitle localAutoEntitle = new com.ffusion.beans.autoentitle.AutoEntitle();
      localAutoEntitle.setEntitlementGroupMember(paramEntitlementGroupMember);
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      a6R.deleteSettings(paramSecureUser, localAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.deleteSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void deleteSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      a6R.deleteSettings(paramSecureUser, paramAutoEntitle, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.deleteSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  public static void setSettings(SecureUser paramSecureUser, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle1, com.ffusion.beans.autoentitle.AutoEntitle paramAutoEntitle2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      if (paramHashMap == null) {
        paramHashMap = new HashMap();
      }
      a6R.setSettings(paramSecureUser, paramAutoEntitle1, paramAutoEntitle2, paramHashMap);
    }
    catch (AutoEntitleException localAutoEntitleException)
    {
      CSILException localCSILException = new CSILException(22000, v(localAutoEntitleException.code), localAutoEntitleException);
      DebugLog.throwing("AutoEntitle.setSettings", localCSILException);
      throw localCSILException;
    }
  }
  
  private static int v(int paramInt)
  {
    switch (paramInt)
    {
    case 1: 
      return 19003;
    }
    return 16002;
  }
  
  public static Entitlements filterEntitlements(Entitlements paramEntitlements)
    throws CSILException
  {
    return a6R.filterEntitlements(paramEntitlements);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.AutoEntitleHandler
 * JD-Core Version:    0.7.0.1
 */