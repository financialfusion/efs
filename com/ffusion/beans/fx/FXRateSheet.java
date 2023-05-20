package com.ffusion.beans.fx;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import java.util.Locale;
import java.util.StringTokenizer;

public class FXRateSheet
  extends ExtendABean
{
  private String y;
  private FXRates z;
  
  public String getBaseCurrencyCode()
  {
    return this.y;
  }
  
  public void setBaseCurrencyCode(String paramString)
  {
    this.y = paramString;
  }
  
  public FXRates getRates()
  {
    return this.z;
  }
  
  public void setRates(FXRates paramFXRates)
  {
    this.z = paramFXRates;
  }
  
  public FXRateSheet(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("BASE_CURRENCY_CODE")) {
        return this.y.equals(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "BASE_CURRENCY_CODE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    FXRateSheet localFXRateSheet = (FXRateSheet)paramObject;
    int i = 1;
    if (paramString.equals("BASE_CURRENCY_CODE")) {
      i = this.y.compareTo(localFXRateSheet.getBaseCurrencyCode());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof FXRateSheet)) {
      return false;
    }
    FXRateSheet localFXRateSheet = (FXRateSheet)paramObject;
    return (areObjectsEqual(this.y, localFXRateSheet.getBaseCurrencyCode())) && (areObjectsEqual(this.z, localFXRateSheet.getRates()));
  }
  
  public void set(FXRateSheet paramFXRateSheet)
  {
    super.set(paramFXRateSheet);
    setBaseCurrencyCode(paramFXRateSheet.getBaseCurrencyCode());
    setRates(paramFXRateSheet.getRates());
    setLocale(paramFXRateSheet.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("BASE_CURRENCY_CODE"))
      {
        this.y = paramString2;
      }
      else if (paramString1.equals("FX_RATES"))
      {
        if (this.z == null) {
          this.z = new FXRates(this.locale);
        }
        this.z.fromXML(paramString2);
      }
      else
      {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException) {}
    return bool;
  }
  
  public static boolean areObjectsEqual(Object paramObject1, Object paramObject2)
  {
    if (paramObject1 == paramObject2) {
      return true;
    }
    if ((paramObject1 == null) || (paramObject2 == null)) {
      return false;
    }
    return paramObject1.equals(paramObject2);
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("baseCurrencyCode=").append(this.y);
    localStringBuffer.append(" rates=").append(this.z);
    return localStringBuffer.toString();
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
    XMLHandler.appendBeginTag(localStringBuffer, "FX_RATESHEET");
    XMLHandler.appendTag(localStringBuffer, "BASE_CURRENCY_CODE", this.y);
    if (this.z != null) {
      localStringBuffer.append(this.z.toXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "FX_RATESHEET");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      FXRateSheet.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("FX_RATES"))
      {
        if (FXRateSheet.this.z == null) {
          FXRateSheet.this.z = new FXRates(FXRateSheet.this.locale);
        }
        FXRateSheet.this.z.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.beans.fx.FXRateSheet
 * JD-Core Version:    0.7.0.1
 */