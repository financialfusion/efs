package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.systemadmin.SAException;
import java.util.HashMap;

public abstract interface SystemAdmin
{
  public abstract void initialize(HashMap paramHashMap)
    throws SAException;
  
  public abstract HashMap getCumulativeDataRetentionSettings(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws SAException;
  
  public abstract HashMap getDataRetentionSettings(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws SAException;
  
  public abstract void setDataRetentionSettings(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap1, HashMap paramHashMap2)
    throws SAException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.SystemAdmin
 * JD-Core Version:    0.7.0.1
 */