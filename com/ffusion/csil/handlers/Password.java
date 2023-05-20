package com.ffusion.csil.handlers;

import com.ffusion.csil.CSILException;
import com.ffusion.efs.adapters.profile.ProfileException;
import com.ffusion.services.IPassword;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;

public class Password
{
  private static final String a = "Password";
  private static Object jdField_if = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Password.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Password", str, 20107);
    jdField_if = HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      ((IPassword)jdField_if).initialize(paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(str, a(localProfileException), localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static int validatePassword(String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "com.ffusion.csil.handlers.Password.validatePassword";
    try
    {
      return ((IPassword)jdField_if).validatePassword(paramString, paramInt, paramHashMap);
    }
    catch (ProfileException localProfileException)
    {
      CSILException localCSILException = new CSILException(str, a(localProfileException), localProfileException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  private static int a(ProfileException paramProfileException)
  {
    switch (paramProfileException.code)
    {
    }
    return -1009;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Password
 * JD-Core Version:    0.7.0.1
 */