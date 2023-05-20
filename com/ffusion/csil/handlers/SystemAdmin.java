package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.systemadmin.SAConstants;
import com.ffusion.systemadmin.SAException;
import java.util.HashMap;

public class SystemAdmin
  extends Initialize
  implements SAConstants
{
  private static final String a7y = "SystemAdmin";
  private static com.ffusion.services.SystemAdmin a7x = null;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "SystemAdmin.initialize";
    debug(str);
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "SystemAdmin", str, 38001);
    a7x = (com.ffusion.services.SystemAdmin)HandlerUtil.instantiateService(localHashMap, str, 38001);
    try
    {
      a7x.initialize(paramHashMap);
    }
    catch (Exception localException)
    {
      CSILException localCSILException = new CSILException(jdMethod_try(localException), localException);
      throw localCSILException;
    }
  }
  
  public static HashMap getDataRetentionSettings(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SystemAdmin.getDataRetentionSettings";
    HashMap localHashMap = null;
    try
    {
      localHashMap = a7x.getDataRetentionSettings(paramSecureUser, paramInt1, paramInt2, paramHashMap);
    }
    catch (SAException localSAException)
    {
      CSILException localCSILException = new CSILException(jdMethod_try(localSAException), localSAException);
      throwing(str, localCSILException.getCode());
      throw localCSILException;
    }
    return localHashMap;
  }
  
  public static HashMap getCumulativeDataRetentionSettings(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "SystemAdmin.getCumulativeDataRetentionSettings";
    HashMap localHashMap = null;
    try
    {
      localHashMap = a7x.getCumulativeDataRetentionSettings(paramSecureUser, paramInt1, paramInt2, paramHashMap);
    }
    catch (SAException localSAException)
    {
      CSILException localCSILException = new CSILException(jdMethod_try(localSAException), localSAException);
      throwing(str, localCSILException.getCode());
      throw localCSILException;
    }
    return localHashMap;
  }
  
  public static void setDataRetentionSettings(SecureUser paramSecureUser, int paramInt1, int paramInt2, HashMap paramHashMap1, HashMap paramHashMap2)
    throws CSILException
  {
    String str = "SystemAdmin.setDataRetentionSettings";
    try
    {
      a7x.setDataRetentionSettings(paramSecureUser, paramInt1, paramInt2, paramHashMap1, paramHashMap2);
    }
    catch (SAException localSAException)
    {
      CSILException localCSILException = new CSILException(jdMethod_try(localSAException), localSAException);
      throwing(str, localCSILException.getCode());
      throw localCSILException;
    }
  }
  
  private static int jdMethod_try(Exception paramException)
  {
    int i = 38002;
    if ((paramException instanceof SAException))
    {
      SAException localSAException = (SAException)paramException;
      switch (localSAException.getErrorCode())
      {
      case 38201: 
        i = 38002;
        break;
      case 38202: 
        i = 38001;
        break;
      case 38203: 
        i = 38001;
        break;
      case 38205: 
        i = 38000;
        break;
      case 38206: 
        i = 38003;
        break;
      case 38204: 
      default: 
        i = 38002;
      }
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.SystemAdmin
 * JD-Core Version:    0.7.0.1
 */