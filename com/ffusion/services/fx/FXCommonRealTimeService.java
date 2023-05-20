package com.ffusion.services.fx;

import com.ffusion.beans.Currency;
import com.ffusion.beans.fx.FXCurrencies;
import com.ffusion.beans.fx.FXCurrency;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.fx.FXRateSheet;
import com.ffusion.beans.fx.FXRates;
import com.ffusion.efs.adapters.entitlements.EntitlementCachedAdapter;
import com.ffusion.fx.FXException;
import com.ffusion.fx.adapter.FXAdapter;
import com.ffusion.services.IForeignExchangeService;
import com.ffusion.util.beans.DateTime;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;

public class FXCommonRealTimeService
  implements IForeignExchangeService
{
  private FXAdapter jdField_byte = new FXAdapter();
  private EntitlementCachedAdapter jdField_try = null;
  private String jdField_new = null;
  public static FXCurrencies dummyCurrenices = new FXCurrencies(Locale.getDefault());
  public static FXRates dummyRates = new FXRates(Locale.getDefault());
  public static String[] currencyCodes = { "USD", "CAD", "GBP", "EUR", "HKD", "INR", "JPY" };
  public static String[][] rateCodes = { { "CAD", "USD" }, { "CAD", "GBP" }, { "CAD", "EUR" }, { "CAD", "HKD" }, { "CAD", "INR" }, { "CAD", "JPY" }, { "USD", "USD" }, { "USD", "GBP" }, { "USD", "EUR" }, { "USD", "HKD" }, { "USD", "INR" }, { "USD", "JPY" }, { "GBP", "CAD" }, { "EUR", "CAD" }, { "JPY", "CAD" } };
  public static BigDecimal[][] rateNums = { { new BigDecimal(1.181D), new BigDecimal(1.138D) }, { new BigDecimal(2.3065D), new BigDecimal(2.2289D) }, { new BigDecimal(1.5664D), new BigDecimal(1.4882D) }, { new BigDecimal(0.1535D), new BigDecimal(0.1441D) }, { new BigDecimal(0.0278D), null }, { new BigDecimal(0.010059D), new BigDecimal(0.009433D) }, { new BigDecimal(0.8487D), new BigDecimal(0.8787D) }, { new BigDecimal(2.0411D), new BigDecimal(1.9724D) }, { new BigDecimal(1.3861D), new BigDecimal(1.317D) }, { new BigDecimal(0.1358D), new BigDecimal(0.1275D) }, { new BigDecimal(0.0246D), null }, { new BigDecimal(0.008900999999999999D), new BigDecimal(0.008347D) }, { new BigDecimal(0.4486D), new BigDecimal(0.4336D) }, { new BigDecimal(0.672D), new BigDecimal(0.6384D) }, { new BigDecimal(106.01000000000001D), new BigDecimal(99.409999999999997D) } };
  
  public void cleanup(int paramInt, HashMap paramHashMap)
    throws FXException
  {}
  
  public FXCurrencies getBaseCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    return dummyCurrenices;
  }
  
  public FXCurrencies getBaseCurrenciesGivenTarget(int paramInt, String paramString, HashMap paramHashMap)
    throws FXException
  {
    return dummyCurrenices;
  }
  
  public FXRate getClosestFXRate(int paramInt1, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    return dummyRates.getByCurrencyCode(paramString1, paramString2);
  }
  
  public FXRate getClosestFXRate(int paramInt1, Connection paramConnection, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    return dummyRates.getByCurrencyCode(paramString1, paramString2);
  }
  
  public FXCurrencies getCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    return dummyCurrenices;
  }
  
  public FXRate getFXRate(int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    return dummyRates.getByCurrencyCode(paramString1, paramString2);
  }
  
  public FXRate getFXRate(int paramInt, String paramString1, String paramString2, DateTime paramDateTime, HashMap paramHashMap)
    throws FXException
  {
    return dummyRates.getByCurrencyCode(paramString1, paramString2);
  }
  
  public FXRate getFXRate(int paramInt1, Connection paramConnection, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    return dummyRates.getByCurrencyCode(paramString1, paramString2);
  }
  
  public FXRate getFXRate(int paramInt1, String paramString1, String paramString2, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    return dummyRates.getByCurrencyCode(paramString1, paramString2);
  }
  
  public FXRate getFXRate(int paramInt1, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException
  {
    return dummyRates.getByCurrencyCode(paramString1, paramString2);
  }
  
  public FXRateSheet getFXRateSheet(int paramInt, String paramString, HashMap paramHashMap)
    throws FXException
  {
    FXRateSheet localFXRateSheet = new FXRateSheet(dummyRates.getLocale());
    localFXRateSheet.setRates(dummyRates.getRatesByBaseCurrencyCode(paramString));
    return localFXRateSheet;
  }
  
  public FXRateSheet getFXRateSheet(int paramInt1, String paramString1, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    FXRateSheet localFXRateSheet = new FXRateSheet(dummyRates.getLocale());
    localFXRateSheet.setRates(dummyRates.getRatesByBaseCurrencyCode(paramString1));
    return localFXRateSheet;
  }
  
  public FXRateSheet getFXRateSheet(int paramInt1, String paramString1, DateTime paramDateTime, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    FXRateSheet localFXRateSheet = new FXRateSheet(dummyRates.getLocale());
    localFXRateSheet.setRates(dummyRates.getRatesByBaseCurrencyCode(paramString1));
    return localFXRateSheet;
  }
  
  public FXRateSheet getFXRateSheetForTarget(int paramInt1, String paramString1, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException
  {
    FXRateSheet localFXRateSheet = new FXRateSheet(dummyRates.getLocale());
    localFXRateSheet.setRates(dummyRates.getRatesByTargetCurrencyCode(paramString1));
    return localFXRateSheet;
  }
  
  public FXCurrencies getTargetCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException
  {
    return dummyCurrenices;
  }
  
  public void initialize(HashMap paramHashMap)
    throws FXException
  {
    this.jdField_try = ((EntitlementCachedAdapter)paramHashMap.get("ENTITLEMENT_CACHED_ADAPTER"));
    if (this.jdField_try == null) {
      throw new FXException(34028, "Could not initialize fx service because settings for entitlement cached adapter is missing.");
    }
  }
  
  public void shutdown()
    throws FXException
  {}
  
  static
  {
    Object localObject;
    for (int i = 0; i < currencyCodes.length; i++)
    {
      localObject = dummyCurrenices.add();
      ((FXCurrency)localObject).setCurrencyCode(currencyCodes[i]);
    }
    for (i = 0; i < rateCodes.length; i++)
    {
      localObject = dummyRates.add();
      ((FXRate)localObject).setTargetCurrencyCode(rateCodes[i][1]);
      if (rateNums[i][0] != null) {
        ((FXRate)localObject).setBuyPrice(new Currency(rateNums[i][0], rateCodes[i][0], dummyCurrenices.getLocale()));
      }
      if (rateNums[i][1] != null) {
        ((FXRate)localObject).setSellPrice(new Currency(rateNums[i][1], rateCodes[i][0], dummyCurrenices.getLocale()));
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.services.fx.FXCommonRealTimeService
 * JD-Core Version:    0.7.0.1
 */