package com.ffusion.services;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.user.User;
import java.io.Serializable;
import java.util.HashMap;

public abstract interface Enroll
  extends Serializable
{
  public abstract int enroll(HashMap paramHashMap);
  
  public abstract void setInitURL(String paramString);
  
  public abstract int activateAccount(User paramUser, Accounts paramAccounts);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Enroll
 * JD-Core Version:    0.7.0.1
 */