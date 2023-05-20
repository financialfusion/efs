package com.ffusion.beans.fx;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import java.util.Locale;
import java.util.StringTokenizer;

public class FXCurrencyFormat
  extends ExtendABean
{
  private String E;
  private String D = "$";
  private String A = ",";
  private String B = ".";
  private String C = "START";
  public static final String POSITION_START = "START";
  public static final String POSITION_END = "END";
  public static final String DEFAULT_POSITION = "START";
  public static final String DEFAULT_SYMBOL = "$";
  public static final String DEFAULT_THOUSANDS_SEPARATOR = ",";
  public static final String DEFAULT_DECIMAL_SEPARATOR = ".";
  
  public String getCode()
  {
    return this.E;
  }
  
  public void setCode(String paramString)
  {
    this.E = paramString;
  }
  
  public String getSymbol()
  {
    return this.D;
  }
  
  public void setSymbol(String paramString)
  {
    this.D = paramString;
  }
  
  public String getThousandSeparator()
  {
    return this.A;
  }
  
  public void setThousandSeparator(String paramString)
  {
    this.A = paramString;
  }
  
  public String getDecimalSeparator()
  {
    return this.B;
  }
  
  public void setDecimalSeparator(String paramString)
  {
    this.B = paramString;
  }
  
  public String getSymbolPosition()
  {
    return this.C;
  }
  
  public void setSymbolPosition(String paramString)
  {
    if ((paramString != null) && ((paramString.equals("START")) || (paramString.equals("END")))) {
      this.C = paramString;
    }
  }
  
  public FXCurrencyFormat() {}
  
  public FXCurrencyFormat(Locale paramLocale)
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
      if (str1.equalsIgnoreCase("CODE")) {
        return this.E.equals(str2);
      }
      if (str1.equalsIgnoreCase("SYMBOL")) {
        return this.D.equals(str2);
      }
      if (str1.equalsIgnoreCase("THOUSAND_SEPARATOR")) {
        return this.A.equals(str2);
      }
      if (str1.equalsIgnoreCase("DECIMAL_SEPARATOR")) {
        return this.B.equals(str2);
      }
      if (str1.equalsIgnoreCase("SYMBOL_POSITION")) {
        return this.C.equals(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "CODE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    FXCurrencyFormat localFXCurrencyFormat = (FXCurrencyFormat)paramObject;
    int i = 1;
    if (paramString.equals("CODE")) {
      i = this.E.compareTo(localFXCurrencyFormat.getCode());
    } else if (paramString.equals("SYMBOL")) {
      i = this.D.compareTo(localFXCurrencyFormat.getSymbol());
    } else if (paramString.equals("THOUSAND_SEPARATOR")) {
      i = this.A.compareTo(localFXCurrencyFormat.getThousandSeparator());
    } else if (paramString.equals("DECIMAL_SEPARATOR")) {
      i = this.B.compareTo(localFXCurrencyFormat.getDecimalSeparator());
    } else if (paramString.equals("SYMBOL_POSITION")) {
      i = this.C.compareTo(localFXCurrencyFormat.getSymbolPosition());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof FXCurrencyFormat)) {
      return false;
    }
    FXCurrencyFormat localFXCurrencyFormat = (FXCurrencyFormat)paramObject;
    return (areObjectsEqual(this.E, localFXCurrencyFormat.getCode())) && (areObjectsEqual(this.D, localFXCurrencyFormat.getSymbol())) && (areObjectsEqual(this.A, localFXCurrencyFormat.getThousandSeparator())) && (areObjectsEqual(this.B, localFXCurrencyFormat.getDecimalSeparator())) && (areObjectsEqual(this.C, localFXCurrencyFormat.getSymbolPosition()));
  }
  
  public void set(FXCurrencyFormat paramFXCurrencyFormat)
  {
    super.set(paramFXCurrencyFormat);
    setCode(paramFXCurrencyFormat.getCode());
    setSymbol(paramFXCurrencyFormat.getSymbol());
    setThousandSeparator(paramFXCurrencyFormat.getThousandSeparator());
    setDecimalSeparator(paramFXCurrencyFormat.getDecimalSeparator());
    setSymbolPosition(paramFXCurrencyFormat.getSymbolPosition());
    setLocale(paramFXCurrencyFormat.locale);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("CODE")) {
        this.E = paramString2;
      } else if (paramString1.equals("SYMBOL")) {
        this.D = paramString2;
      } else if (paramString1.equals("THOUSAND_SEPARATOR")) {
        this.A = paramString2;
      } else if (paramString1.equals("DECIMAL_SEPARATOR")) {
        this.B = paramString2;
      } else if (paramString1.equals("SYMBOL_POSITION"))
      {
        if ((paramString2 != null) && ((paramString2.equals("START")) || (paramString2.equals("END")))) {
          this.C = paramString2;
        }
      }
      else {
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
    localStringBuffer.append("code=").append(this.E);
    localStringBuffer.append(" symbol=").append(this.D);
    localStringBuffer.append(" thousandSeparator=").append(this.A);
    localStringBuffer.append(" decimalSeparator=").append(this.B);
    localStringBuffer.append(" symbolPosition=").append(this.C);
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
    XMLHandler.appendBeginTag(localStringBuffer, "CURRENCY");
    XMLHandler.appendTag(localStringBuffer, "CODE", this.E);
    XMLHandler.appendTag(localStringBuffer, "SYMBOL", this.D);
    XMLHandler.appendTag(localStringBuffer, "THOUSAND_SEPARATOR", this.A);
    XMLHandler.appendTag(localStringBuffer, "DECIMAL_SEPARATOR", this.B);
    XMLHandler.appendTag(localStringBuffer, "SYMBOL_POSITION", this.C);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "CURRENCY");
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
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      FXCurrencyFormat.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.beans.fx.FXCurrencyFormat
 * JD-Core Version:    0.7.0.1
 */