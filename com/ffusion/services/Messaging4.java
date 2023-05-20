package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.messages.GlobalMessage;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface Messaging4
  extends Messaging3
{
  public abstract void createGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException;
  
  public abstract void sendGlobalMessage(SecureUser paramSecureUser, GlobalMessage paramGlobalMessage, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Messaging4
 * JD-Core Version:    0.7.0.1
 */