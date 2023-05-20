package com.ffusion.fx.adapter;

import com.ffusion.beans.fx.FXCurrencies;
import com.ffusion.beans.fx.FXCurrency;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.fx.FXRateSheet;
import com.ffusion.fx.FXException;
import com.ffusion.util.beans.DateTime;
import java.util.HashMap;

public abstract interface IFXAdapter
{
  public abstract void initialize(HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRate getFXRate(int paramInt, String paramString1, String paramString2, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRate getFXRate(int paramInt, String paramString1, String paramString2, DateTime paramDateTime, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXRateSheet getFXRateSheet(int paramInt, String paramString, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXCurrencies getCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXCurrencies getBaseCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXCurrencies getBaseCurrenciesGivenTarget(int paramInt, String paramString, HashMap paramHashMap)
    throws FXException;
  
  public abstract FXCurrencies getTargetCurrencies(int paramInt, HashMap paramHashMap)
    throws FXException;
  
  public abstract void addCurrency(FXCurrency paramFXCurrency, HashMap paramHashMap)
    throws FXException;
  
  public abstract void modifyCurrency(FXCurrency paramFXCurrency, HashMap paramHashMap)
    throws FXException;
  
  public abstract void removeCurrency(FXCurrency paramFXCurrency, HashMap paramHashMap)
    throws FXException;
  
  public abstract void addFXRate(String paramString, FXRate paramFXRate, HashMap paramHashMap)
    throws FXException;
  
  public abstract void modifyFXRate(String paramString, FXRate paramFXRate, HashMap paramHashMap)
    throws FXException;
  
  public abstract void removeFXRate(String paramString, FXRate paramFXRate, HashMap paramHashMap)
    throws FXException;
  
  public abstract void addFXRateSheet(String paramString, FXRateSheet paramFXRateSheet, HashMap paramHashMap)
    throws FXException;
  
  public abstract void modifyFXRateSheet(String paramString, FXRateSheet paramFXRateSheet, HashMap paramHashMap)
    throws FXException;
  
  public abstract void removeFXRateSheet(String paramString, HashMap paramHashMap)
    throws FXException;
  
  public abstract void removeFXRateSheet(String paramString, DateTime paramDateTime, HashMap paramHashMap)
    throws FXException;
  
  public abstract void cleanup(int paramInt, HashMap paramHashMap)
    throws FXException;
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.fx.adapter.IFXAdapter
 * JD-Core Version:    0.7.0.1
 */