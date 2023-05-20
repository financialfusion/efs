package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.HashMap;

public abstract interface Messaging7
  extends Messaging6
{
  public abstract void modifyMessageThreadStatus(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Messaging7
 * JD-Core Version:    0.7.0.1
 */