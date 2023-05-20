package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;

public class Authenticate
  extends Initialize
{
  public static void initialize(HashMap paramHashMap)
  {
    debug("com.ffusion.csil.core.Authenticate.initialize");
    com.ffusion.csil.handlers.Authenticate.initialize(paramHashMap);
  }
  
  public static SecureUser authenticate(String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Authenticate";
    long l = System.currentTimeMillis();
    SecureUser localSecureUser = com.ffusion.csil.handlers.Authenticate.authenticate(paramString1, paramString2, paramString3, paramHashMap);
    PerfLog.log(str, l, true);
    debug(localSecureUser, str);
    return localSecureUser;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Authenticate
 * JD-Core Version:    0.7.0.1
 */