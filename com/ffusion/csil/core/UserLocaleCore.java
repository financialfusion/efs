package com.ffusion.csil.core;

import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.handlers.UserLocaleHandler;
import com.ffusion.util.UserLocaleConsts;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;

public class UserLocaleCore
  extends Initialize
  implements UserLocaleConsts
{
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {}
  
  public static UserLocale getUserLocale(String paramString1, String paramString2)
    throws CSILException
  {
    String str = "UserLocaleCore.getUserLocale";
    long l = System.currentTimeMillis();
    UserLocale localUserLocale = UserLocaleHandler.getUserLocale(paramString1, paramString2);
    PerfLog.log(str, l, true);
    return localUserLocale;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.UserLocaleCore
 * JD-Core Version:    0.7.0.1
 */