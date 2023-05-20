package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.csil.CSILException;
import java.util.HashMap;

public class Profile
  extends Initialize
{
  public static void initialize(HashMap paramHashMap)
  {
    debug("com.ffusion.csil.handlers.Profile.initialize");
    try
    {
      com.ffusion.efs.adapters.profile.Profile.initialize(paramHashMap);
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
  
  public static Accounts getAccounts(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Profile
 * JD-Core Version:    0.7.0.1
 */