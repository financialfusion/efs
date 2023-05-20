package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface UserAdmin8
  extends UserAdmin7
{
  public abstract ArrayList getBankIdsByServicesPackage(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.UserAdmin8
 * JD-Core Version:    0.7.0.1
 */