package com.ffusion.util;

import java.text.Collator;
import java.util.Locale;

public class CollatorUtil
{
  public static int getDefaultStrength(Locale paramLocale)
  {
    return 0;
  }
  
  public static Collator getCollator()
  {
    return getCollator(null, getDefaultStrength(null));
  }
  
  public static Collator getCollator(Locale paramLocale)
  {
    return getCollator(paramLocale, getDefaultStrength(paramLocale));
  }
  
  public static Collator getCollator(int paramInt)
  {
    return getCollator(null, paramInt);
  }
  
  public static Collator getCollator(Locale paramLocale, int paramInt)
  {
    if (paramLocale == null) {
      paramLocale = LocaleUtil.getDefaultLocale();
    }
    Collator localCollator = Collator.getInstance(paramLocale);
    localCollator.setStrength(paramInt);
    return new CollatorWrapper(localCollator);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.CollatorUtil
 * JD-Core Version:    0.7.0.1
 */