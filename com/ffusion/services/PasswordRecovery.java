package com.ffusion.services;

import com.ffusion.beans.user.User;
import com.ffusion.passwordrecovery.PasswordRecoveryException;
import java.util.HashMap;

public abstract interface PasswordRecovery
{
  public abstract void initialize(HashMap paramHashMap)
    throws PasswordRecoveryException;
  
  public abstract User lookup(HashMap paramHashMap1, HashMap paramHashMap2)
    throws PasswordRecoveryException;
  
  public abstract boolean validate(User paramUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws PasswordRecoveryException;
  
  public abstract void reset(User paramUser, HashMap paramHashMap1, HashMap paramHashMap2)
    throws PasswordRecoveryException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.PasswordRecovery
 * JD-Core Version:    0.7.0.1
 */