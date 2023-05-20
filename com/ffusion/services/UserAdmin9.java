package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.user.Users;
import com.ffusion.efs.adapters.profile.ProfileException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface UserAdmin9
  extends UserAdmin8
{
  public abstract Users getUsersByIds(SecureUser paramSecureUser, ArrayList paramArrayList, HashMap paramHashMap)
    throws ProfileException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.UserAdmin9
 * JD-Core Version:    0.7.0.1
 */