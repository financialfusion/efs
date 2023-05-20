package com.ffusion.csil.core;

import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.handlers.PasswordRecoveryHandler;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;

public class PasswordRecovery
  extends Initialize
{
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    PasswordRecoveryHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return PasswordRecoveryHandler.getService();
  }
  
  public static User lookup(HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "PasswordRecovery.lookup";
    long l = System.currentTimeMillis();
    User localUser = PasswordRecoveryHandler.lookup(paramHashMap1, paramHashMap2);
    PerfLog.log(str, l, true);
    return localUser;
  }
  
  public static boolean validate(User paramUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "PasswordRecovery.validate";
    long l = System.currentTimeMillis();
    boolean bool = PasswordRecoveryHandler.validate(paramUser, paramHashMap1, paramHashMap2);
    PerfLog.log(str, l, true);
    return bool;
  }
  
  public static void reset(User paramUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "PasswordRecovery.reset";
    long l = System.currentTimeMillis();
    PasswordRecoveryHandler.reset(paramUser, paramHashMap1, paramHashMap2);
    PerfLog.log(str, l, true);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.PasswordRecovery
 * JD-Core Version:    0.7.0.1
 */