package com.ffusion.beans;

import com.ffusion.beans.fx.FXCurrencyFormat;
import com.ffusion.fx.FXException;
import com.ffusion.fx.FXUtil;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.ReadWriteLock;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Locale;

public class DecimalFormatUtil
{
  private static HashMap a = new HashMap();
  private static ReadWriteLock jdField_if = new ReadWriteLock();
  private static HashMap jdField_do = new HashMap();
  
  public static DecimalFormat getFormatter(String paramString)
  {
    return getFormatter(paramString, LocaleUtil.getDefaultLocale(), null);
  }
  
  public static DecimalFormat getFormatter(String paramString1, Locale paramLocale, String paramString2)
  {
    return getFormatter(paramString1, paramLocale, paramString2, "USD");
  }
  
  public static DecimalFormat getFormatter(String paramString1, Locale paramLocale, String paramString2, String paramString3)
  {
    DecimalFormat localDecimalFormat = null;
    try
    {
      if ((paramString1 == null) || (paramString1.length() == 0)) {
        paramString1 = "CURRENCY";
      }
      if (paramLocale == null) {
        paramLocale = LocaleUtil.getDefaultLocale();
      }
      StringBuffer localStringBuffer = new StringBuffer(paramString1);
      localStringBuffer.append(paramLocale.toString()).toString();
      String str = paramString3;
      jdField_if.getReadLock();
      localDecimalFormat = (DecimalFormat)a.get(str);
      if (localDecimalFormat == null)
      {
        jdField_if.releaseLock();
        jdField_if.getWriteLock();
        if (paramString1.equals("CURRENCY"))
        {
          localDecimalFormat = (DecimalFormat)DecimalFormat.getCurrencyInstance(paramLocale);
          DecimalFormatSymbols localDecimalFormatSymbols = localDecimalFormat.getDecimalFormatSymbols();
          try
          {
            FXCurrencyFormat localFXCurrencyFormat = FXUtil.getFormat(paramString3, jdField_do);
            localDecimalFormatSymbols.setCurrencySymbol(localFXCurrencyFormat.getSymbol());
            localDecimalFormatSymbols.setDecimalSeparator(localFXCurrencyFormat.getDecimalSeparator().charAt(0));
            localDecimalFormatSymbols.setMonetaryDecimalSeparator(localFXCurrencyFormat.getDecimalSeparator().charAt(0));
            localDecimalFormatSymbols.setGroupingSeparator(localFXCurrencyFormat.getThousandSeparator().charAt(0));
            int i = FXUtil.getNumDecimals(paramString3, jdField_do);
            localDecimalFormat.setMinimumFractionDigits(i);
            localDecimalFormat.setMaximumFractionDigits(i);
          }
          catch (FXException localFXException) {}
          localDecimalFormat.setDecimalFormatSymbols(localDecimalFormatSymbols);
        }
        else
        {
          localDecimalFormat = (DecimalFormat)DecimalFormat.getInstance(paramLocale);
        }
        a.put(str, localDecimalFormat);
      }
      if ((paramString1.equals("MASK")) && (paramString2 != null) && (!localDecimalFormat.toPattern().equals(paramString2))) {
        localDecimalFormat.applyPattern(paramString2);
      }
    }
    finally
    {
      jdField_if.releaseLock();
    }
    return localDecimalFormat;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.beans.DecimalFormatUtil
 * JD-Core Version:    0.7.0.1
 */