package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import com.ffusion.util.logging.PerfLog;
import java.util.HashMap;

public class Profile
  extends Initialize
{
  public static void initialize(HashMap paramHashMap)
  {
    debug("com.ffusion.csil.core.Profile.initialize");
    com.ffusion.csil.handlers.Profile.initialize(paramHashMap);
  }
  
  public static Accounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "Profile.GetAccounts";
    long l = System.currentTimeMillis();
    Accounts localAccounts = com.ffusion.csil.handlers.Profile.getAccounts(paramSecureUser, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localAccounts;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Profile
 * JD-Core Version:    0.7.0.1
 */