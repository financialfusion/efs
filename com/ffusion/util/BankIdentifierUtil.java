package com.ffusion.util;

import java.util.HashMap;

public class BankIdentifierUtil
  implements IBankIdentifier
{
  private static final int a = 35;
  
  public void initialize(HashMap paramHashMap)
    throws Exception
  {}
  
  public boolean validateBankIdentifier(String paramString)
    throws Exception
  {
    return a(paramString, false);
  }
  
  public boolean isValid(String paramString)
    throws Exception
  {
    return a(paramString, false);
  }
  
  public boolean isValidCheckZeros(String paramString)
    throws Exception
  {
    return a(paramString, true);
  }
  
  private static boolean a(String paramString, boolean paramBoolean)
  {
    if (paramString == null) {
      return false;
    }
    if (paramString.length() > 35) {
      return false;
    }
    if ((paramBoolean) && (paramString.matches("[0]+"))) {
      return false;
    }
    return paramString.matches("[0-9a-zA-Z]+");
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.BankIdentifierUtil
 * JD-Core Version:    0.7.0.1
 */