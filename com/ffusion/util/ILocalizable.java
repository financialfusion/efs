package com.ffusion.util;

import java.io.Serializable;
import java.util.Locale;

public abstract interface ILocalizable
  extends Serializable
{
  public abstract Object localize(Locale paramLocale);
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.ILocalizable
 * JD-Core Version:    0.7.0.1
 */