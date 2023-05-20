package com.ffusion.util.beans;

import com.ffusion.util.CollatorUtil;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.Localeable;
import java.text.Collator;
import java.util.Locale;

public abstract class LocaleableBean
  implements Localeable
{
  public static final Locale DEFAULT_LOCALE = ;
  protected Locale locale = DEFAULT_LOCALE;
  protected transient Collator collator = null;
  
  public LocaleableBean(Locale paramLocale)
  {
    setLocale(paramLocale);
  }
  
  public LocaleableBean()
  {
    setLocale(DEFAULT_LOCALE);
  }
  
  public void setLocale(String paramString)
  {
    setLocale(LocaleUtil.getLocaleFromLocaleID(paramString));
  }
  
  public synchronized void setLocale(Locale paramLocale)
  {
    if (paramLocale == null) {
      paramLocale = DEFAULT_LOCALE;
    }
    if (!paramLocale.equals(this.locale))
    {
      this.locale = paramLocale;
      this.collator = null;
    }
  }
  
  public void setCollator(Collator paramCollator)
  {
    this.collator = (paramCollator == null ? CollatorUtil.getCollator(this.locale) : paramCollator);
  }
  
  public Collator getCollator()
  {
    return (Collator)doGetCollator().clone();
  }
  
  protected Collator doGetCollator()
  {
    if (this.collator == null) {
      synchronized (this)
      {
        if (this.collator == null) {
          this.collator = CollatorUtil.getCollator(this.locale, getCollatorStrength());
        }
      }
    }
    return this.collator;
  }
  
  public String getLocaleLanguage()
  {
    if (this.locale == null) {
      this.locale = DEFAULT_LOCALE;
    }
    if ((this.locale.getLanguage() == null) && (this.locale.getCountry() == null)) {
      return "";
    }
    if ((this.locale.getCountry() == null) || (this.locale.getCountry().length() == 0)) {
      return this.locale.getLanguage();
    }
    return this.locale.getLanguage() + "_" + this.locale.getCountry();
  }
  
  public Locale getLocale()
  {
    if (this.locale == null) {
      setLocale(DEFAULT_LOCALE);
    }
    return this.locale;
  }
  
  public int getCollatorStrength()
  {
    return CollatorUtil.getDefaultStrength(this.locale);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.LocaleableBean
 * JD-Core Version:    0.7.0.1
 */