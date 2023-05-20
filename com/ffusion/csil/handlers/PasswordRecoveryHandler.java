package com.ffusion.csil.handlers;

import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.passwordrecovery.PasswordRecoveryException;
import com.ffusion.services.PasswordRecovery;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class PasswordRecoveryHandler
{
  private static final String jdField_if = "Password Recovery";
  private static PasswordRecovery a = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "PasswordRecoveryHandler.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Password Recovery", str, 20107);
    a = (PasswordRecovery)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      a.initialize(paramHashMap);
    }
    catch (PasswordRecoveryException localPasswordRecoveryException)
    {
      CSILException localCSILException = new CSILException(a(localPasswordRecoveryException.code), localPasswordRecoveryException.code, localPasswordRecoveryException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return a;
  }
  
  public static User lookup(HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "PasswordRecoveryHandler.lookup";
    try
    {
      return a.lookup(paramHashMap1, paramHashMap2);
    }
    catch (PasswordRecoveryException localPasswordRecoveryException)
    {
      CSILException localCSILException = new CSILException(a(localPasswordRecoveryException.code), localPasswordRecoveryException.code, localPasswordRecoveryException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static boolean validate(User paramUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "PasswordRecoveryHandler.validate";
    try
    {
      return a.validate(paramUser, paramHashMap1, paramHashMap2);
    }
    catch (PasswordRecoveryException localPasswordRecoveryException)
    {
      CSILException localCSILException = new CSILException(a(localPasswordRecoveryException.code), localPasswordRecoveryException.code, localPasswordRecoveryException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void reset(User paramUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "PasswordRecoveryHandler.reset";
    try
    {
      a.reset(paramUser, paramHashMap1, paramHashMap2);
    }
    catch (PasswordRecoveryException localPasswordRecoveryException)
    {
      CSILException localCSILException = new CSILException(a(localPasswordRecoveryException.code), localPasswordRecoveryException.code, localPasswordRecoveryException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  private static int a(int paramInt)
  {
    switch (paramInt)
    {
    case 33000: 
      return 20107;
    case 33001: 
      return 33001;
    case 33002: 
      return 33002;
    case 33003: 
      return 33003;
    case 33004: 
      return 33004;
    case 33008: 
      return 33008;
    case 33005: 
      return 33005;
    case 33006: 
      return 33006;
    case 33007: 
      return 33007;
    }
    return -1009;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.PasswordRecoveryHandler
 * JD-Core Version:    0.7.0.1
 */