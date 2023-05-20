package com.ffusion.beans.fx;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.XMLStrings;
import java.util.Locale;

public class FXCurrencies
  extends FilteredList
  implements XMLStrings, XMLable
{
  public FXCurrencies(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public FXCurrency add()
  {
    FXCurrency localFXCurrency = new FXCurrency(this.locale);
    add(localFXCurrency);
    return localFXCurrency;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof FXCurrencies)) {
      return false;
    }
    FXCurrencies localFXCurrencies = (FXCurrencies)paramObject;
    if (size() != localFXCurrencies.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localFXCurrencies.locale;
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
    return containsAll(localFXCurrencies);
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
    XMLHandler.appendBeginTag(localStringBuffer, "FX_CURRENCIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((FXCurrency)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "FX_CURRENCIES");
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
      if (paramString.equals("FX_CURRENCY"))
      {
        FXCurrency localFXCurrency = new FXCurrency(FXCurrencies.this.locale);
        FXCurrencies.this.add(localFXCurrency);
        localFXCurrency.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.beans.fx.FXCurrencies
 * JD-Core Version:    0.7.0.1
 */