package com.ffusion.beans.fx;

import com.ffusion.beans.Currency;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.ExtendABean;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;

public class FXRate
  extends ExtendABean
{
  public static final String DEFAULT_OBJECT_ID = "0";
  public static final int OBJECT_SYSTEM = 0;
  public static final int OBJECT_AFFILIATE_BANK = 1;
  public static final int OBJECT_SVC_PACKAGE = 2;
  public static final int OBJECT_BUSINESS = 3;
  public static final int OBJECT_USER = 4;
  private static final String L = "com.ffusion.beans.fx.resources";
  private String H;
  private DateTime F;
  private Currency J;
  private Currency K;
  private int I = 0;
  private String G = "0";
  
  public String getTargetCurrencyCode()
  {
    return this.H;
  }
  
  public void setTargetCurrencyCode(String paramString)
  {
    this.H = paramString;
  }
  
  public DateTime getAsOfDate()
  {
    return this.F;
  }
  
  public void setAsOfDate(DateTime paramDateTime)
  {
    this.F = paramDateTime;
  }
  
  public Currency getBuyPrice()
  {
    return this.J;
  }
  
  public void setBuyPrice(Currency paramCurrency)
  {
    this.J = paramCurrency;
  }
  
  public Currency getSellPrice()
  {
    return this.K;
  }
  
  public void setSellPrice(Currency paramCurrency)
  {
    this.K = paramCurrency;
  }
  
  public FXRate(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.J != null) {
      this.J.setLocale(paramLocale);
    }
    if (this.K != null) {
      this.K.setLocale(paramLocale);
    }
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() == 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("TARGET_CURRENCY_CODE")) {
        return this.H.equals(str2);
      }
      if (str1.equalsIgnoreCase("BUY_PRICE")) {
        return this.J.equals(new BigDecimal(str2));
      }
      if (str1.equalsIgnoreCase("SELL_PRICE")) {
        return this.K.equals(new BigDecimal(str2));
      }
      if (str1.equalsIgnoreCase("AS_OF_DATE")) {
        return this.F.isFilterable(paramString);
      }
      if (str1.equalsIgnoreCase("OBJECT_TYPE")) {
        try
        {
          int i = Integer.parseInt(str2);
          return i == this.I;
        }
        catch (Exception localException)
        {
          return false;
        }
      }
      if (str1.equalsIgnoreCase("OBJECT_ID")) {
        return this.G.equals(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "TARGET_CURRENCY_CODE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    FXRate localFXRate = (FXRate)paramObject;
    int i = 1;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("TARGET_CURRENCY_CODE")) && (this.H != null) && (localFXRate.getTargetCurrencyCode() != null)) {
      i = localCollator.compare(getTargetCurrencyCode(), localFXRate.getTargetCurrencyCode());
    } else if ((paramString.equals("BUY_PRICE")) && (this.J != null) && (localFXRate.getBuyPrice() != null)) {
      i = getBuyPrice().compareTo(localFXRate.getBuyPrice());
    } else if ((paramString.equals("SELL_PRICE")) && (this.K != null) && (localFXRate.getSellPrice() != null)) {
      i = getSellPrice().compareTo(localFXRate.getSellPrice());
    } else if ((paramString.equals("AS_OF_DATE")) && (this.F != null) && (localFXRate.getAsOfDate() != null)) {
      i = getAsOfDate().compare(localFXRate.getAsOfDate());
    } else if (paramString.equals("OBJECT_TYPE")) {
      i = this.I - localFXRate.I;
    } else if (paramString.equals("OBJECT_ID")) {
      i = this.G.compareTo(localFXRate.G);
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof FXRate)) {
      return false;
    }
    FXRate localFXRate = (FXRate)paramObject;
    if ((this.I != localFXRate.I) || (!this.G.equals(localFXRate.G))) {
      return false;
    }
    return (areObjectsEqual(this.H, localFXRate.getTargetCurrencyCode())) && (areObjectsEqual(this.J, localFXRate.getBuyPrice())) && (areObjectsEqual(this.K, localFXRate.getSellPrice())) && (areObjectsEqual(this.F, localFXRate.getAsOfDate()));
  }
  
  public void set(FXRate paramFXRate)
  {
    super.set(paramFXRate);
    setObjectType(paramFXRate.getObjectTypeValue());
    setObjectID(paramFXRate.getObjectID());
    setTargetCurrencyCode(paramFXRate.getTargetCurrencyCode());
    setBuyPrice(paramFXRate.getBuyPrice());
    setSellPrice(paramFXRate.getSellPrice());
    setAsOfDate(paramFXRate.getAsOfDate());
    setLocale(paramFXRate.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("TARGET_CURRENCY_CODE"))
      {
        this.H = paramString2;
      }
      else if (paramString1.equals("BUY_PRICE"))
      {
        this.J = new Currency(paramString2, this.locale);
      }
      else if (paramString1.equals("SELL_PRICE"))
      {
        this.K = new Currency(paramString2, this.locale);
      }
      else if (paramString1.equals("AS_OF_DATE"))
      {
        if (this.F == null)
        {
          this.F = new DateTime(this.locale);
          this.F.setFormat(this.datetype);
        }
        this.F.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("OBJECT_TYPE"))
      {
        setObjectType(paramString2);
      }
      else if (paramString1.equals("OBJECT_ID"))
      {
        setObjectID(paramString2);
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
    localStringBuffer.append("targetCurrencyCode=").append(this.H);
    localStringBuffer.append(" buyPrice=").append(this.J);
    localStringBuffer.append(" sellPrice=").append(this.K);
    localStringBuffer.append(" asOfDate=").append(this.F);
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
    XMLHandler.appendBeginTag(localStringBuffer, "FX_RATE");
    XMLHandler.appendTag(localStringBuffer, "TARGET_CURRENCY_CODE", this.H);
    XMLHandler.appendBeginTag(localStringBuffer, "OBJECT_TYPE");
    localStringBuffer.append(this.I);
    XMLHandler.appendEndTag(localStringBuffer, "OBJECT_TYPE");
    XMLHandler.appendBeginTag(localStringBuffer, "OBJECT_ID");
    localStringBuffer.append(this.G);
    XMLHandler.appendEndTag(localStringBuffer, "OBJECT_ID");
    if (this.J != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "BUY_PRICE");
      localStringBuffer.append(this.J.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "BUY_PRICE");
    }
    if (this.K != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "SELL_PRICE");
      localStringBuffer.append(this.K.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "SELL_PRICE");
    }
    if (this.F != null) {
      XMLHandler.appendTag(localStringBuffer, "AS_OF_DATE", this.F.toXMLFormat());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "FX_RATE");
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
  
  public int getObjectTypeValue()
  {
    return this.I;
  }
  
  public String getObjectType()
  {
    return Integer.toString(this.I);
  }
  
  public void setObjectType(int paramInt)
  {
    this.I = paramInt;
  }
  
  public void setObjectType(String paramString)
    throws NumberFormatException
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      setObjectType(0);
    }
    int i = Integer.parseInt(paramString);
    setObjectType(i);
  }
  
  public String getObjectID()
  {
    return this.G;
  }
  
  public void setObjectID(String paramString)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      this.G = "0";
    }
    this.G = paramString;
  }
  
  public String getTargetCurrencyDescription()
  {
    String str1 = "CurrencyDesc_" + this.H;
    String str2 = "";
    try
    {
      str2 = ResourceUtil.getString(str1, "com.ffusion.beans.fx.resources", this.locale);
    }
    catch (Exception localException)
    {
      return this.H;
    }
    return str2;
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      FXRate.this.set(getElement(), str);
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("BUY_PRICE"))
      {
        if (FXRate.this.J == null) {
          FXRate.this.J = new Currency("0", FXRate.this.locale);
        }
        FXRate.this.J.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("SELL_PRICE"))
      {
        if (FXRate.this.K == null) {
          FXRate.this.K = new Currency("0", FXRate.this.locale);
        }
        FXRate.this.K.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.beans.fx.FXRate
 * JD-Core Version:    0.7.0.1
 */