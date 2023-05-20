package com.ffusion.util;

import java.util.HashMap;

public abstract interface IBankIdentifier
{
  public abstract void initialize(HashMap paramHashMap)
    throws Exception;
  
  public abstract boolean validateBankIdentifier(String paramString)
    throws Exception;
  
  public abstract boolean isValid(String paramString)
    throws Exception;
  
  public abstract boolean isValidCheckZeros(String paramString)
    throws Exception;
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.IBankIdentifier
 * JD-Core Version:    0.7.0.1
 */