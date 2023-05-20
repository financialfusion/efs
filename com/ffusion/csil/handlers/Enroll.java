package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.user.User;
import com.ffusion.csil.CSILException;
import java.util.HashMap;

public class Enroll
  extends Initialize
{
  public static void initialize(HashMap paramHashMap)
  {
    debug("com.ffusion.csil.handlers.Enroll.initialize");
  }
  
  public static Object getService()
  {
    return null;
  }
  
  public static HashMap enroll(HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Enroll.enroll");
    checkExtra(paramHashMap2);
    com.ffusion.services.Enroll localEnroll = (com.ffusion.services.Enroll)paramHashMap2.get("SERVICE");
    if ((localEnroll == null) || (!(localEnroll instanceof com.ffusion.services.Enroll)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = localEnroll.enroll(paramHashMap1);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramHashMap1;
  }
  
  public static Accounts activateAccount(SecureUser paramSecureUser, User paramUser, Accounts paramAccounts, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Enroll.activateAccount");
    checkExtra(paramHashMap);
    com.ffusion.services.Enroll localEnroll = (com.ffusion.services.Enroll)paramHashMap.get("SERVICE");
    if ((localEnroll == null) || (!(localEnroll instanceof com.ffusion.services.Enroll)))
    {
      debug("Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    int i = localEnroll.activateAccount(paramUser, paramAccounts);
    if (i != 0) {
      throwing(-1009, i);
    }
    return paramAccounts;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Enroll
 * JD-Core Version:    0.7.0.1
 */