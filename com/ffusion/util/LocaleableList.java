package com.ffusion.util;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Locale;

public class LocaleableList
  extends ArrayList
  implements Localeable
{
  public static final Locale DEFAULT_LOCALE = ;
  protected Locale locale = DEFAULT_LOCALE;
  protected transient Collator collator = null;
  
  public LocaleableList(Locale paramLocale)
  {
    setLocale(paramLocale);
  }
  
  public LocaleableList()
  {
    setLocale(DEFAULT_LOCALE);
  }
  
  public LocaleableList(Collection paramCollection)
  {
    this(DEFAULT_LOCALE, paramCollection);
  }
  
  public LocaleableList(Locale paramLocale, Collection paramCollection)
  {
    setLocale(paramLocale);
    addAll(paramCollection);
  }
  
  public LocaleableList(int paramInt)
  {
    this(DEFAULT_LOCALE, paramInt);
  }
  
  public LocaleableList(Locale paramLocale, int paramInt)
  {
    super(paramInt);
    setLocale(paramLocale);
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
      Iterator localIterator = super.iterator();
      Object localObject = null;
      while (localIterator.hasNext())
      {
        localObject = localIterator.next();
        if ((localObject != null) && ((localObject instanceof Localeable))) {
          ((Localeable)localObject).setLocale(this.locale);
        }
      }
      this.collator = null;
    }
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
  
  public void add(int paramInt, Object paramObject)
  {
    if ((paramObject != null) && ((paramObject instanceof Localeable))) {
      ((Localeable)paramObject).setLocale(this.locale);
    }
    super.add(paramInt, paramObject);
  }
  
  public boolean add(Object paramObject)
  {
    if ((paramObject != null) && ((paramObject instanceof Localeable))) {
      ((Localeable)paramObject).setLocale(this.locale);
    }
    return super.add(paramObject);
  }
  
  public boolean addAll(Collection paramCollection)
  {
    if (paramCollection != null)
    {
      Iterator localIterator = paramCollection.iterator();
      Object localObject = null;
      while (localIterator.hasNext())
      {
        localObject = localIterator.next();
        if ((localObject != null) && ((localObject instanceof Localeable))) {
          ((Localeable)localObject).setLocale(this.locale);
        }
      }
    }
    return super.addAll(paramCollection);
  }
  
  public boolean addAll(int paramInt, Collection paramCollection)
  {
    if (paramCollection != null)
    {
      Iterator localIterator = paramCollection.iterator();
      Object localObject = null;
      while (localIterator.hasNext())
      {
        localObject = localIterator.next();
        if ((localObject != null) && ((localObject instanceof Localeable))) {
          ((Localeable)localObject).setLocale(this.locale);
        }
      }
    }
    return super.addAll(paramInt, paramCollection);
  }
  
  public Object set(int paramInt, Object paramObject)
  {
    if ((paramObject != null) && ((paramObject instanceof Localeable))) {
      ((Localeable)paramObject).setLocale(this.locale);
    }
    return super.set(paramInt, paramObject);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.LocaleableList
 * JD-Core Version:    0.7.0.1
 */