package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Initialize
{
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.handlers.Initialize.initialize");
  }
  
  public static void debug(String paramString)
  {
    DebugLog.log(Level.FINE, paramString);
  }
  
  public static void debug(SecureUser paramSecureUser, String paramString)
  {
    if (paramSecureUser != null) {
      DebugLog.log(Level.FINE, paramSecureUser.getId() + ": " + paramString);
    } else {
      DebugLog.log(Level.FINE, paramString);
    }
  }
  
  public static void throwing(int paramInt1, int paramInt2)
    throws CSILException
  {
    CSILException localCSILException = new CSILException(paramInt1, paramInt2);
    DebugLog.throwing("EXCEPTION code = " + paramInt1 + " ServiceError = " + paramInt2, localCSILException);
    throw localCSILException;
  }
  
  public static void throwing(String paramString, int paramInt)
    throws CSILException
  {
    CSILException localCSILException = new CSILException(paramString, paramInt);
    DebugLog.throwing("EXCEPTION code = " + paramInt + " Where = " + paramString, localCSILException);
    throw localCSILException;
  }
  
  public static void throwing(int paramInt, Exception paramException)
    throws CSILException
  {
    CSILException localCSILException = new CSILException(paramInt, paramException);
    DebugLog.throwing("EXCEPTION code = " + paramInt, localCSILException);
    throw localCSILException;
  }
  
  public static void throwing(int paramInt)
    throws CSILException
  {
    CSILException localCSILException = new CSILException(paramInt);
    DebugLog.throwing("EXCEPTION code = " + paramInt, localCSILException);
    throw localCSILException;
  }
  
  public static void checkExtra(HashMap paramHashMap)
    throws CSILException
  {
    if (paramHashMap == null)
    {
      debug("'extra' is null - Missing required parameter: extra.'SERVICE'");
      throwing(-1009, -1010);
    }
    if (DebugLog.getLogger().isLoggable(Level.FINEST))
    {
      Iterator localIterator = paramHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        Object localObject = paramHashMap.get(str1);
        if (localObject != null)
        {
          String str2 = localObject.getClass().getName();
          String str3 = localObject.toString();
          DebugLog.log(Level.FINEST, "(" + str2 + ")" + str1 + "=" + str3);
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.Initialize
 * JD-Core Version:    0.7.0.1
 */