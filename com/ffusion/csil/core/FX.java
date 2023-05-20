package com.ffusion.csil.core;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fx.FXCurrencies;
import com.ffusion.beans.fx.FXCurrencyFormat;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.fx.FXRateSheet;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.handlers.FXHandler;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.fx.FXException;
import com.ffusion.fx.FXUtil;
import com.ffusion.services.IForeignExchangeService;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.HashMap;

public class FX
  extends Initialize
{
  private static final int asQ = 2;
  private static final String asO = "default";
  private static HashMap asN;
  private static HashMap asP;
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      EntitlementCachedAdapter localEntitlementCachedAdapter = Entitlements.U();
      paramHashMap.put("ENTITLEMENT_CACHED_ADAPTER", localEntitlementCachedAdapter);
      FXHandler.initialize(paramHashMap);
      localEntitlementCachedAdapter.setFXService((IForeignExchangeService)FXHandler.getService());
      FXUtil.initialize(paramHashMap);
    }
    catch (Throwable localThrowable)
    {
      DebugLog.throwing("Could not initialize the foreign exchange module.", localThrowable);
    }
  }
  
  public static FXRate getFXRate(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getFXRate";
    FXRate localFXRate = null;
    try
    {
      localFXRate = FXHandler.getFXRate(paramSecureUser.getProfileID(), paramString1, paramString2, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    return localFXRate;
  }
  
  public static FXRate getFXRate(SecureUser paramSecureUser, String paramString1, String paramString2, DateTime paramDateTime, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getFXRate";
    FXRate localFXRate = null;
    try
    {
      localFXRate = FXHandler.getFXRate(paramSecureUser.getProfileID(), paramString1, paramString2, paramDateTime, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    return localFXRate;
  }
  
  public static FXRate getFXRate(SecureUser paramSecureUser, String paramString1, String paramString2, int paramInt, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getFXRate";
    FXRate localFXRate = null;
    try
    {
      localFXRate = FXHandler.getFXRate(paramSecureUser, paramString1, paramString2, paramInt, paramString3, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    return localFXRate;
  }
  
  public static FXRate getFXRate(SecureUser paramSecureUser, String paramString1, String paramString2, DateTime paramDateTime, int paramInt, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getFXRate";
    FXRate localFXRate = null;
    try
    {
      localFXRate = FXHandler.getFXRate(paramSecureUser, paramString1, paramString2, paramDateTime, paramInt, paramString3, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    return localFXRate;
  }
  
  public static FXRate getClosestFXRate(SecureUser paramSecureUser, String paramString1, String paramString2, DateTime paramDateTime, int paramInt, String paramString3, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getClosestFXRate";
    FXRate localFXRate = null;
    try
    {
      localFXRate = FXHandler.getClosestFXRate(paramSecureUser, paramString1, paramString2, paramDateTime, paramInt, paramString3, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    return localFXRate;
  }
  
  public static FXRateSheet getFXRateSheet(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getFXRateSheet";
    FXRateSheet localFXRateSheet = null;
    try
    {
      localFXRateSheet = FXHandler.getFXRateSheet(paramSecureUser.getProfileID(), paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    return localFXRateSheet;
  }
  
  public static FXRateSheet getFXRateSheet(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getFXRateSheet";
    FXRateSheet localFXRateSheet = null;
    try
    {
      localFXRateSheet = FXHandler.getFXRateSheet(paramSecureUser, paramString1, paramInt, paramString2, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    return localFXRateSheet;
  }
  
  public static FXRateSheet getFXRateSheet(SecureUser paramSecureUser, String paramString1, DateTime paramDateTime, int paramInt, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getFXRateSheet";
    FXRateSheet localFXRateSheet = null;
    try
    {
      localFXRateSheet = FXHandler.getFXRateSheet(paramSecureUser, paramString1, paramDateTime, paramInt, paramString2, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    return localFXRateSheet;
  }
  
  public static FXRateSheet getFXRateSheetForTarget(SecureUser paramSecureUser, String paramString1, int paramInt, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getFXRateSheetForTarget";
    FXRateSheet localFXRateSheet = null;
    try
    {
      localFXRateSheet = FXHandler.getFXRateSheetForTarget(paramSecureUser, paramString1, paramInt, paramString2, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    return localFXRateSheet;
  }
  
  public static FXCurrencies getCurrencies(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getCurrencies";
    FXCurrencies localFXCurrencies = null;
    try
    {
      localFXCurrencies = FXHandler.getCurrencies(paramSecureUser.getProfileID(), paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    if (localFXCurrencies != null) {
      localFXCurrencies.setLocale(paramSecureUser.getLocale());
    }
    return localFXCurrencies;
  }
  
  public static FXCurrencies getBaseCurrencies(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    FXCurrencies localFXCurrencies = null;
    String str = "FX.getBaseCurrencies";
    try
    {
      localFXCurrencies = FXHandler.getBaseCurrencies(paramSecureUser.getProfileID(), paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    if (localFXCurrencies != null) {
      localFXCurrencies.setLocale(paramSecureUser.getLocale());
    }
    return localFXCurrencies;
  }
  
  public static FXCurrencies getBaseCurrenciesGivenTarget(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException
  {
    FXCurrencies localFXCurrencies = null;
    String str = "FX.getBaseCurrenciesGivenTarget";
    try
    {
      localFXCurrencies = FXHandler.getBaseCurrenciesGivenTarget(paramSecureUser.getProfileID(), paramString, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    if (localFXCurrencies != null) {
      localFXCurrencies.setLocale(paramSecureUser.getLocale());
    }
    return localFXCurrencies;
  }
  
  public static FXCurrencies getTargetCurrencies(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    FXCurrencies localFXCurrencies = null;
    String str = "FX.getTargetCurrencies";
    try
    {
      localFXCurrencies = FXHandler.getTargetCurrencies(paramSecureUser.getProfileID(), paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
    if (localFXCurrencies != null) {
      localFXCurrencies.setLocale(paramSecureUser.getLocale());
    }
    return localFXCurrencies;
  }
  
  public static int getNumDecimals(String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getNumDecimals";
    try
    {
      return FXUtil.getNumDecimals(paramString, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = new CSILException(str, -1009, localFXException);
      localCSILException.setServiceError(localFXException.getErrorCode());
      throw localCSILException;
    }
  }
  
  public static FXCurrencyFormat getFormat(String paramString, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getFormat";
    try
    {
      return FXUtil.getFormat(paramString, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = new CSILException(str, -1009, localFXException);
      localCSILException.setServiceError(localFXException.getErrorCode());
      throw localCSILException;
    }
  }
  
  public static FXCurrencyFormat getFormat(String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.getFormat";
    try
    {
      return FXUtil.getFormat(paramString1, paramString2, paramHashMap);
    }
    catch (FXException localFXException)
    {
      CSILException localCSILException = new CSILException(str, -1009, localFXException);
      localCSILException.setServiceError(localFXException.getErrorCode());
      throw localCSILException;
    }
  }
  
  public static void cleanup(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "FX.cleanup";
    try
    {
      FXHandler.cleanup(paramInt, paramHashMap);
    }
    catch (CSILException localCSILException)
    {
      debug(str + " failed.");
      throw localCSILException;
    }
  }
  
  public static Currency convertToBaseCurrency(SecureUser paramSecureUser, Currency paramCurrency, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "FX.convertToBaseCurrency";
    HashMap localHashMap = new HashMap();
    if (paramSecureUser == null) {
      throw new CSILException(str1, 34101, "The secure user object specified was null.");
    }
    if (paramCurrency == null) {
      throw new CSILException(str1, 34102, "The Currency value specified was null.");
    }
    String str2 = null;
    if (paramHashMap != null) {
      str2 = (String)paramHashMap.get("TARGET_CURRENCY_CODE");
    }
    if (str2 == null) {
      str2 = paramSecureUser.getBaseCurrency();
    }
    if ((str2 == null) || (paramCurrency.getCurrencyCode() == null)) {
      throw new CSILException(str1, 34103, "One or more currency codes specified were null.");
    }
    FXRate localFXRate = null;
    String str3 = null;
    if (paramHashMap != null) {
      str3 = (String)paramHashMap.get("OBJECT_TYPE");
    }
    if (str3 != null)
    {
      int i = Integer.parseInt(str3);
      String str4 = (String)paramHashMap.get("OBJECT_ID");
      localFXRate = getFXRate(paramSecureUser, paramCurrency.getCurrencyCode(), str2, i, str4, paramHashMap);
    }
    else
    {
      localFXRate = getFXRate(paramSecureUser, paramCurrency.getCurrencyCode(), str2, paramHashMap);
    }
    if (localFXRate == null) {
      throw new CSILException(str1, 34105, "There is no rate data for the base/target currency specified.");
    }
    BigDecimal localBigDecimal = paramCurrency.getAmountValue().multiply(localFXRate.getBuyPrice().getAmountValue());
    int j = getNumDecimals(str2, localHashMap);
    localBigDecimal = localBigDecimal.setScale(j, 6);
    Currency localCurrency = new Currency(localBigDecimal, paramSecureUser.getLocale());
    localCurrency.setCurrencyCode(str2);
    return localCurrency;
  }
  
  public static Currency convertToBaseCurrency(SecureUser paramSecureUser, Currency paramCurrency, DateTime paramDateTime, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "FX.convertToBaseCurrency";
    if (paramSecureUser == null) {
      throw new CSILException(str1, 34101, "The secure user object specified was null.");
    }
    if (paramCurrency == null) {
      throw new CSILException(str1, 34102, "The Currency value specified was null.");
    }
    if ((paramSecureUser.getBaseCurrency() == null) || (paramCurrency.getCurrencyCode() == null)) {
      throw new CSILException(str1, 34103, "One or more currency codes specified were null.");
    }
    if (paramDateTime == null) {
      throw new CSILException(str1, 34104, "The DateTime object specified was null.");
    }
    FXRate localFXRate = null;
    String str2 = (String)paramHashMap.get("OBJECT_TYPE");
    if (str2 != null)
    {
      int i = Integer.parseInt(str2);
      String str3 = (String)paramHashMap.get("OBJECT_ID");
      localFXRate = getFXRate(paramSecureUser, paramCurrency.getCurrencyCode(), paramSecureUser.getBaseCurrency(), paramDateTime, i, str3, paramHashMap);
    }
    else
    {
      localFXRate = getFXRate(paramSecureUser, paramCurrency.getCurrencyCode(), paramSecureUser.getBaseCurrency(), paramDateTime, paramHashMap);
    }
    if (localFXRate == null) {
      throw new CSILException(str1, 34106, "There is no rate data for the base/target currency and date specified.");
    }
    BigDecimal localBigDecimal = paramCurrency.getAmountValue().multiply(localFXRate.getBuyPrice().getAmountValue());
    int j = getNumDecimals(paramSecureUser.getBaseCurrency(), paramHashMap);
    localBigDecimal = localBigDecimal.setScale(j, 6);
    Currency localCurrency = new Currency(localBigDecimal, paramSecureUser.getLocale());
    localCurrency.setCurrencyCode(paramSecureUser.getBaseCurrency());
    return localCurrency;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.FX
 * JD-Core Version:    0.7.0.1
 */