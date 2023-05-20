package com.ffusion.beans.fx;

import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;

public class FXCurrency
  extends ExtendABean
{
  private static final String N = "com.ffusion.beans.fx.resources";
  private String O;
  private String M;
  
  public String getCurrencyCode()
  {
    return this.O;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.O = paramString;
  }
  
  public String getDescription()
  {
    String str1 = "CurrencyDesc_" + this.O;
    String str2 = "";
    try
    {
      str2 = ResourceUtil.getString(str1, "com.ffusion.beans.fx.resources", this.locale);
    }
    catch (Exception localException)
    {
      return this.M;
    }
    return str2;
  }
  
  public void setDescription(String paramString)
  {
    this.M = paramString;
  }
  
  public FXCurrency(Locale paramLocale)
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
      if (str1.equalsIgnoreCase("CURRENCY_CODE")) {
        return this.O.equals(str2);
      }
      if (str1.equalsIgnoreCase("DESCRIPTION")) {
        return this.M.equals(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "CURRENCY_CODE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    FXCurrency localFXCurrency = (FXCurrency)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("CURRENCY_CODE")) && (this.O != null) && (localFXCurrency.getCurrencyCode() != null)) {
      i = localCollator.compare(getCurrencyCode(), localFXCurrency.getCurrencyCode());
    } else if ((paramString.equals("DESCRIPTION")) && (this.M != null) && (localFXCurrency.getDescription() != null)) {
      i = localCollator.compare(getDescription(), localFXCurrency.getDescription());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof FXCurrency)) {
      return false;
    }
    FXCurrency localFXCurrency = (FXCurrency)paramObject;
    return (areObjectsEqual(this.O, localFXCurrency.getCurrencyCode())) && (areObjectsEqual(this.M, localFXCurrency.getDescription()));
  }
  
  public void set(FXCurrency paramFXCurrency)
  {
    super.set(paramFXCurrency);
    setCurrencyCode(paramFXCurrency.getCurrencyCode());
    setDescription(paramFXCurrency.getDescription());
    setLocale(paramFXCurrency.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("CURRENCY_CODE")) {
        this.O = paramString2;
      } else if (paramString1.equals("DESCRIPTION")) {
        this.M = paramString2;
      } else {
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
    localStringBuffer.append("currencyCode=").append(this.O);
    localStringBuffer.append(" description=").append(this.M);
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
    XMLHandler.appendBeginTag(localStringBuffer, "FX_CURRENCY");
    XMLHandler.appendTag(localStringBuffer, "CURRENCY_CODE", this.O);
    XMLHandler.appendTag(localStringBuffer, "DESCRIPTION", this.M);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "FX_CURRENCY");
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
      FXCurrency.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.beans.fx.FXCurrency
 * JD-Core Version:    0.7.0.1
 */