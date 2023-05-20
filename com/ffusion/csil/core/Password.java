package com.ffusion.csil.core;

import com.ffusion.csil.CSILException;
import java.util.HashMap;

public class Password
  extends Initialize
{
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.csil.handlers.Password.initialize(paramHashMap);
  }
  
  public static int validatePassword(String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    return com.ffusion.csil.handlers.Password.validatePassword(paramString, paramInt, paramHashMap);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.Password
 * JD-Core Version:    0.7.0.1
 */