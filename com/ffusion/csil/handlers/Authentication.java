package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.authentication.Credentials;
import com.ffusion.csil.CSILException;
import com.ffusion.services.IAuthentication;
import com.ffusion.services.authentication.AuthException;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.HashMap;

public class Authentication
{
  private static final String a = "Authentication";
  private static Object jdField_if = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Authentication.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Authentication", str, 20107);
    jdField_if = HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      ((IAuthentication)jdField_if).initialize(paramHashMap);
    }
    catch (AuthException localAuthException)
    {
      CSILException localCSILException = a(localAuthException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getSecrets(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Authentication.getSecrets";
    try
    {
      return ((IAuthentication)jdField_if).getSecrets(paramSecureUser, paramHashMap1, paramHashMap2);
    }
    catch (AuthException localAuthException)
    {
      CSILException localCSILException = a(localAuthException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Credentials getCredentialRequests(SecureUser paramSecureUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Authentication.getCredentialRequests";
    try
    {
      return ((IAuthentication)jdField_if).getCredentialRequests(paramSecureUser, paramHashMap1, paramHashMap2);
    }
    catch (AuthException localAuthException)
    {
      CSILException localCSILException = a(localAuthException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void validateCredentials(SecureUser paramSecureUser, Credentials paramCredentials, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Authentication.validateCredentials";
    try
    {
      ((IAuthentication)jdField_if).validateCredentials(paramSecureUser, paramCredentials, paramHashMap);
    }
    catch (AuthException localAuthException)
    {
      CSILException localCSILException = a(localAuthException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  private static CSILException a(AuthException paramAuthException)
  {
    CSILException localCSILException;
    if (paramAuthException.getErrorCode() == 1) {
      localCSILException = new CSILException(90000, paramAuthException);
    } else if (paramAuthException.getErrorCode() == 2) {
      localCSILException = new CSILException(90001, paramAuthException);
    } else if (paramAuthException.getErrorCode() == 3) {
      localCSILException = new CSILException(90002, paramAuthException);
    } else if (paramAuthException.getErrorCode() == 4) {
      localCSILException = new CSILException(90003, paramAuthException);
    } else if (paramAuthException.getErrorCode() == 5) {
      localCSILException = new CSILException(90004, paramAuthException);
    } else if (paramAuthException.getErrorCode() == 6) {
      localCSILException = new CSILException(90005, paramAuthException);
    } else if (paramAuthException.getErrorCode() == 7) {
      localCSILException = new CSILException(90006, paramAuthException);
    } else if (paramAuthException.getErrorCode() == 8) {
      localCSILException = new CSILException(90007, paramAuthException);
    } else if (paramAuthException.getErrorCode() == 9) {
      localCSILException = new CSILException(90009, paramAuthException);
    } else if (paramAuthException.getErrorCode() == 10) {
      localCSILException = new CSILException(90010, paramAuthException);
    } else {
      localCSILException = new CSILException(90199, paramAuthException);
    }
    return localCSILException;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Authentication
 * JD-Core Version:    0.7.0.1
 */