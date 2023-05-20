package com.ffusion.jtf;

public abstract interface UrlEncryptor
{
  public abstract String encrypt(String paramString);
  
  public abstract String decrypt(String paramString);
  
  public abstract void clear();
  
  public abstract void setDesiredEncoding(String paramString);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.UrlEncryptor
 * JD-Core Version:    0.7.0.1
 */