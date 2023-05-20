package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;

public abstract interface SignOn3
  extends SignOn2
{
  public abstract boolean signOn(SecureUser paramSecureUser, String paramString1, String paramString2)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.SignOn3
 * JD-Core Version:    0.7.0.1
 */