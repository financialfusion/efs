package com.ffusion.efs.adapters.profile;

public abstract interface Encrypt
{
  public abstract String dbEncrypt(String paramString)
    throws Exception;
  
  public abstract String dbDecrypt(String paramString)
    throws Exception;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.efs.adapters.profile.Encrypt
 * JD-Core Version:    0.7.0.1
 */