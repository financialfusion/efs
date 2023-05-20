package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import java.util.HashMap;

public class Authenticate
  extends Initialize
{
  public static void initialize(HashMap paramHashMap)
  {
    debug("com.ffusion.csil.handlers.Authenticate.initialize");
  }
  
  public static SecureUser authenticate(String paramString1, String paramString2, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Authenticate.authenticate");
    SecureUser localSecureUser = new SecureUser();
    localSecureUser.setUserName(paramString1);
    localSecureUser.setPassword(paramString2);
    localSecureUser.setBankID(paramString3);
    return localSecureUser;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Authenticate
 * JD-Core Version:    0.7.0.1
 */