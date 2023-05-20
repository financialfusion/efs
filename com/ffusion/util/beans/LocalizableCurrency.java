package com.ffusion.util.beans;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DecimalFormatUtil;
import com.ffusion.util.ILocalizable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.StringTokenizer;

public class LocalizableCurrency
  implements ILocalizable
{
  private static final String aB = "¤";
  private static final String az = ",";
  private Currency ax;
  private boolean ay;
  private boolean aA;
  
  public LocalizableCurrency(Currency paramCurrency)
  {
    this.ax = paramCurrency;
    this.ay = true;
    this.aA = true;
  }
  
  public Object localize(Locale paramLocale)
  {
    String str1 = "";
    String str2 = "";
    DecimalFormat localDecimalFormat = DecimalFormatUtil.getFormatter(this.ax.getFormat(), paramLocale, null, this.ax.getCurrencyCode());
    try
    {
      if ((!this.ay) && (this.aA))
      {
        str2 = jdMethod_if(localDecimalFormat.toPattern(), "¤");
        localDecimalFormat = new DecimalFormat(str2);
      }
      else if (!this.aA)
      {
        str2 = jdMethod_if(localDecimalFormat.toPattern(), "¤,");
        localDecimalFormat = new DecimalFormat(str2);
      }
      str1 = localDecimalFormat.format(this.ax.getAmountValue().doubleValue());
    }
    catch (Throwable localThrowable)
    {
      return "";
    }
    return str1;
  }
  
  public void setShowCurrencySymbol(boolean paramBoolean)
  {
    this.ay = paramBoolean;
  }
  
  public void setShowSeparatorAndSymbol(boolean paramBoolean)
  {
    this.aA = paramBoolean;
  }
  
  private static String jdMethod_if(String paramString1, String paramString2)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString1, paramString2);
    StringBuffer localStringBuffer = new StringBuffer("");
    while (localStringTokenizer.hasMoreTokens()) {
      localStringBuffer.append(localStringTokenizer.nextToken().trim());
    }
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.LocalizableCurrency
 * JD-Core Version:    0.7.0.1
 */