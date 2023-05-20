package com.ffusion.services;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.user.User;
import java.util.HashMap;

public abstract interface Enroll2
  extends Enroll
{
  public static final int ERROR_INIT_FILE_NOT_FOUND = 15000;
  public static final int ERROR_INVALID_INIT_FILE = 15001;
  
  public abstract int enroll(HashMap paramHashMap);
  
  public abstract int initialize(String paramString);
  
  public abstract int activateAccount(User paramUser, Accounts paramAccounts);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Enroll2
 * JD-Core Version:    0.7.0.1
 */