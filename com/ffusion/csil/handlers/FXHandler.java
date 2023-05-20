package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fx.FXCurrencies;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.fx.FXRateSheet;
import com.ffusion.csil.CSILException;
import com.ffusion.fx.FXException;
import com.ffusion.services.IForeignExchangeService;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.logging.DebugLog;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

public class FXHandler
  extends Initialize
{
  private static final String a6t = "Foreign Exchange";
  private static final String a6s = "DB_PROPERTIES";
  private static IForeignExchangeService a6u = null;
  
  public static void initialize(HashMap paramHashMap)
  {
    String str = "FXHandler.initialize";
    try
    {
      HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Foreign Exchange", str, 20107);
      a6u = (IForeignExchangeService)HandlerUtil.instantiateService(localHashMap, str, 20107);
      localHashMap.put("ENTITLEMENT_CACHED_ADAPTER", paramHashMap.get("ENTITLEMENT_CACHED_ADAPTER"));
      Properties localProperties = (Properties)paramHashMap.get("DB_PROPERTIES");
      localHashMap.put("DB_PROPERTIES", localProperties);
      a6u.initialize(localHashMap);
    }
    catch (Exception localException)
    {
      a6u = null;
      DebugLog.log(Level.SEVERE, "FXHandler.initialize:" + localException.toString());
    }
  }
  
  public static Object getService()
  {
    return a6u;
  }
  
  public static FXRate getFXRate(int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    FXRate localFXRate = null;
    try
    {
      localFXRate = a6u.getFXRate(paramInt, paramString1, paramString2, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getFXRate", localCSILException);
      throw localCSILException;
    }
    return localFXRate;
  }
  
  public static FXRate getFXRate(SecureUser paramSecureUser, String paramString1, String paramString2, int paramInt, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a6u.getFXRate(paramSecureUser.getProfileID(), paramString1, paramString2, paramInt, paramString3, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getFXRate", localCSILException);
      throw localCSILException;
    }
  }
  
  public static FXRate getFXRate(SecureUser paramSecureUser, String paramString1, String paramString2, DateTime paramDateTime, int paramInt, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a6u.getFXRate(paramSecureUser.getProfileID(), paramString1, paramString2, paramDateTime, paramInt, paramString3, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getFXRate", localCSILException);
      throw localCSILException;
    }
  }
  
  public static FXRate getFXRate(int paramInt, String paramString1, String paramString2, DateTime paramDateTime, HashMap paramHashMap)
    throws CSILException
  {
    FXRate localFXRate = null;
    try
    {
      localFXRate = a6u.getFXRate(paramInt, paramString1, paramString2, paramDateTime, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getFXRate", localCSILException);
      throw localCSILException;
    }
    return localFXRate;
  }
  
  public static FXRate getClosestFXRate(SecureUser paramSecureUser, String paramString1, String paramString2, DateTime paramDateTime, int paramInt, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return a6u.getClosestFXRate(paramSecureUser.getProfileID(), paramString1, paramString2, paramDateTime, paramInt, paramString3, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getFXRate", localCSILException);
      throw localCSILException;
    }
  }
  
  public static FXRateSheet getFXRateSheet(int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    FXRateSheet localFXRateSheet = null;
    try
    {
      localFXRateSheet = a6u.getFXRateSheet(paramInt, paramString, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getFXRateSheet", localCSILException);
      throw localCSILException;
    }
    return localFXRateSheet;
  }
  
  public static FXRateSheet getFXRateSheet(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    FXRateSheet localFXRateSheet = null;
    try
    {
      localFXRateSheet = a6u.getFXRateSheet(paramSecureUser.getProfileID(), paramString1, paramInt, paramString2, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getFXRateSheet", localCSILException);
      throw localCSILException;
    }
    return localFXRateSheet;
  }
  
  public static FXRateSheet getFXRateSheet(SecureUser paramSecureUser, String paramString1, DateTime paramDateTime, int paramInt, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    FXRateSheet localFXRateSheet = null;
    try
    {
      localFXRateSheet = a6u.getFXRateSheet(paramSecureUser.getProfileID(), paramString1, paramDateTime, paramInt, paramString2, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getFXRateSheet", localCSILException);
      throw localCSILException;
    }
    return localFXRateSheet;
  }
  
  public static FXRateSheet getFXRateSheetForTarget(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    FXRateSheet localFXRateSheet = null;
    try
    {
      localFXRateSheet = a6u.getFXRateSheetForTarget(paramSecureUser.getProfileID(), paramString1, paramInt, paramString2, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getFXRateSheetForTarget", localCSILException);
      throw localCSILException;
    }
    return localFXRateSheet;
  }
  
  public static FXCurrencies getCurrencies(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    FXCurrencies localFXCurrencies = null;
    try
    {
      localFXCurrencies = a6u.getCurrencies(paramInt, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getCurrencies", localCSILException);
      throw localCSILException;
    }
    return localFXCurrencies;
  }
  
  public static FXCurrencies getBaseCurrencies(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    FXCurrencies localFXCurrencies = null;
    try
    {
      localFXCurrencies = a6u.getBaseCurrencies(paramInt, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getBaseCurrencies", localCSILException);
      throw localCSILException;
    }
    return localFXCurrencies;
  }
  
  public static FXCurrencies getBaseCurrenciesGivenTarget(int paramInt, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    FXCurrencies localFXCurrencies = null;
    try
    {
      localFXCurrencies = a6u.getBaseCurrenciesGivenTarget(paramInt, paramString, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getBaseCurrenciesGivenTarget", localCSILException);
      throw localCSILException;
    }
    return localFXCurrencies;
  }
  
  public static FXCurrencies getTargetCurrencies(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    FXCurrencies localFXCurrencies = null;
    try
    {
      localFXCurrencies = a6u.getTargetCurrencies(paramInt, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.getTargetCurrencies", localCSILException);
      throw localCSILException;
    }
    return localFXCurrencies;
  }
  
  public static void cleanup(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      a6u.cleanup(paramInt, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = jdMethod_for(localFXException);
      DebugLog.throwing("FXHandler.cleanup", localCSILException);
      throw localCSILException;
    }
  }
  
  private static CSILException jdMethod_for(FXException paramFXException)
  {
    CSILException localCSILException = new CSILException(-1009, paramFXException.getErrorCode(), paramFXException);
    return localCSILException;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.FXHandler
 * JD-Core Version:    0.7.0.1
 */