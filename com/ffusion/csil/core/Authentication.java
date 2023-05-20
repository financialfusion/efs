package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.authentication.Credentials;
import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.HashMap;

public class Authentication
  extends Initialize
{
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "Authentication.initialize";
    try
    {
      com.ffusion.csil.handlers.Authentication.initialize(paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getSecrets(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "Authentication.getSecrets";
    long l = System.currentTimeMillis();
    ArrayList localArrayList = null;
    try
    {
      localArrayList = com.ffusion.csil.handlers.Authentication.getSecrets(paramSecureUser, paramHashMap1, paramHashMap2);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw new CSILException(str, 90199, localCSILException);
    }
    if (localArrayList == null) {
      localArrayList = new ArrayList();
    }
    PerfLog.log(str, l, true);
    return localArrayList;
  }
  
  public static Credentials getCredentialRequests(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "Authentication.getCredentialRequests";
    long l = System.currentTimeMillis();
    EntitlementCachedAdapter localEntitlementCachedAdapter = Entitlements.U();
    paramHashMap2.put("EntCachedAdapter", localEntitlementCachedAdapter);
    Credentials localCredentials = null;
    try
    {
      localCredentials = com.ffusion.csil.handlers.Authentication.getCredentialRequests(paramSecureUser, paramHashMap1, paramHashMap2);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    paramHashMap2.remove("EntCachedAdapter");
    PerfLog.log(str, l, true);
    return localCredentials;
  }
  
  public static void validateCredentials(SecureUser paramSecureUser, Credentials paramCredentials, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Authentication.validateCredentials";
    long l = System.currentTimeMillis();
    try
    {
      com.ffusion.csil.handlers.Authentication.validateCredentials(paramSecureUser, paramCredentials, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
    PerfLog.log(str, l, true);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Authentication
 * JD-Core Version:    0.7.0.1
 */