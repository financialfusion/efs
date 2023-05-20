package com.ffusion.beans.fx;

import com.ffusion.beans.Currency;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.XMLStrings;
import java.util.Iterator;
import java.util.Locale;

public class FXRates
  extends FilteredList
  implements XMLStrings, XMLable
{
  public FXRates(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public FXRate add()
  {
    FXRate localFXRate = new FXRate(this.locale);
    add(localFXRate);
    return localFXRate;
  }
  
  public FXRate getByCurrencyCode(String paramString1, String paramString2)
  {
    if ((paramString1 != null) && (paramString2 != null))
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        FXRate localFXRate = (FXRate)localIterator.next();
        if (paramString2.equals(localFXRate.getTargetCurrencyCode()))
        {
          Currency localCurrency1 = localFXRate.getSellPrice();
          Currency localCurrency2 = localFXRate.getBuyPrice();
          if (((localCurrency1 != null) && (paramString1.equals(localCurrency1.getCurrencyCode()))) || ((localCurrency2 != null) && (paramString1.equals(localCurrency2.getCurrencyCode())))) {
            return localFXRate;
          }
        }
      }
    }
    return null;
  }
  
  public FXRate getByTargetCurrencyCode(String paramString)
  {
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        FXRate localFXRate = (FXRate)localIterator.next();
        if (paramString.equals(localFXRate.getTargetCurrencyCode())) {
          return localFXRate;
        }
      }
    }
    return null;
  }
  
  public FXRate getByBaseCurrencyCode(String paramString)
  {
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        FXRate localFXRate = (FXRate)localIterator.next();
        Currency localCurrency1 = localFXRate.getSellPrice();
        Currency localCurrency2 = localFXRate.getBuyPrice();
        if (((localCurrency1 != null) && (paramString.equals(localCurrency1.getCurrencyCode()))) || ((localCurrency2 != null) && (paramString.equals(localCurrency2.getCurrencyCode())))) {
          return localFXRate;
        }
      }
    }
    return null;
  }
  
  public FXRates getRatesByCurrencyCode(String paramString1, String paramString2)
  {
    FXRates localFXRates = null;
    if ((paramString1 != null) && (paramString2 != null))
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        if (localFXRates == null) {
          localFXRates = new FXRates(this.locale);
        }
        FXRate localFXRate = (FXRate)localIterator.next();
        if (paramString2.equals(localFXRate.getTargetCurrencyCode()))
        {
          Currency localCurrency1 = localFXRate.getSellPrice();
          Currency localCurrency2 = localFXRate.getBuyPrice();
          if (((localCurrency1 != null) && (paramString1.equals(localCurrency1.getCurrencyCode()))) || ((localCurrency2 != null) && (paramString1.equals(localCurrency2.getCurrencyCode())))) {
            localFXRates.add(localFXRate);
          }
        }
      }
    }
    return localFXRates;
  }
  
  public FXRates getRatesByTargetCurrencyCode(String paramString)
  {
    Iterator localIterator = iterator();
    FXRates localFXRates = null;
    while (localIterator.hasNext())
    {
      if (localFXRates == null) {
        localFXRates = new FXRates(this.locale);
      }
      FXRate localFXRate = (FXRate)localIterator.next();
      if (paramString.equals(localFXRate.getTargetCurrencyCode())) {
        localFXRates.add(localFXRate);
      }
    }
    return localFXRates;
  }
  
  public FXRates getRatesByBaseCurrencyCode(String paramString)
  {
    Iterator localIterator = iterator();
    FXRates localFXRates = null;
    while (localIterator.hasNext())
    {
      if (localFXRates == null) {
        localFXRates = new FXRates(this.locale);
      }
      FXRate localFXRate = (FXRate)localIterator.next();
      Currency localCurrency1 = localFXRate.getSellPrice();
      Currency localCurrency2 = localFXRate.getBuyPrice();
      if (((localCurrency1 != null) && (paramString.equals(localCurrency1.getCurrencyCode()))) || ((localCurrency2 != null) && (paramString.equals(localCurrency2.getCurrencyCode())))) {
        localFXRates.add(localFXRate);
      }
    }
    return localFXRates;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof FXRates)) {
      return false;
    }
    FXRates localFXRates = (FXRates)paramObject;
    if (size() != localFXRates.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localFXRates.locale;
    if (localLocale1 != null)
    {
      if (localLocale2 != null)
      {
        if (!localLocale1.equals(localLocale2)) {
          return false;
        }
      }
      else {
        return false;
      }
    }
    else if (localLocale2 != null) {
      return false;
    }
    return containsAll(localFXRates);
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "FX_RATES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((FXRate)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "FX_RATES");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("FX_RATE"))
      {
        FXRate localFXRate = new FXRate(FXRates.this.locale);
        FXRates.this.add(localFXRate);
        localFXRate.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.beans.fx.FXRates
 * JD-Core Version:    0.7.0.1
 */