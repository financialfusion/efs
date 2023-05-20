package com.ffusion.services;

import com.ffusion.beans.fx.FXCurrencies;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.fx.FXRateSheet;
import com.ffusion.fx.FXException;
import com.ffusion.util.beans.DateTime;
import java.sql.Connection;
import java.util.HashMap;

public abstract interface IForeignExchangeService
{
  public abstract void initialize(HashMap paramHashMap)
    throws FXException;
  
  public abstract void shutdown()
    throws FXException;
  
  public abstract FXRate getFXRate(int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRate getFXRate(int paramInt, String paramString1, String paramString2, DateTime paramDateTime, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRate getFXRate(int paramInt1, Connection paramConnection, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRate getFXRate(int paramInt1, String paramString1, String paramString2, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRate getFXRate(int paramInt1, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRate getClosestFXRate(int paramInt1, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRate getClosestFXRate(int paramInt1, Connection paramConnection, String paramString1, String paramString2, DateTime paramDateTime, int paramInt2, String paramString3, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRateSheet getFXRateSheet(int paramInt, String paramString, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRateSheet getFXRateSheet(int paramInt1, String paramString1, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRateSheet getFXRateSheet(int paramInt1, String paramString1, DateTime paramDateTime, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRateSheet getFXRateSheetForTarget(int paramInt1, String paramString1, int paramInt2, String paramString2, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXCurrencies getCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXCurrencies getBaseCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXCurrencies getBaseCurrenciesGivenTarget(int paramInt, String paramString, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXCurrencies getTargetCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException;
  
  public abstract void cleanup(int paramInt, HashMap paramHashMap)
    throws FXException;
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.services.IForeignExchangeService
 * JD-Core Version:    0.7.0.1
 */