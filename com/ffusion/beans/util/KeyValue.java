package com.ffusion.beans.util;

import com.ffusion.util.CollatorUtil;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.Localeable;
import com.ffusion.util.Sortable;
import java.text.CollationKey;
import java.text.Collator;
import java.util.Locale;

public class KeyValue
  implements Localeable, Sortable
{
  public static final String SORTFIELD_KEY = "Key";
  public static final String SORTFIELD_VALUE = "Value";
  protected String key = null;
  protected String value = null;
  private Object jdField_do = null;
  private Object jdField_int = null;
  private Locale a = null;
  private Collator jdField_for = null;
  private CollationKey jdField_new = null;
  private CollationKey jdField_if = null;
  
  public KeyValue(String paramString1, String paramString2)
  {
    this(paramString1, paramString2, LocaleUtil.getDefaultLocale());
  }
  
  public KeyValue(Object paramObject1, Object paramObject2)
  {
    this(paramObject1, paramObject2, LocaleUtil.getDefaultLocale());
  }
  
  public KeyValue(Object paramObject1, Object paramObject2, Locale paramLocale)
  {
    this.a = paramLocale;
    this.jdField_for = CollatorUtil.getCollator(this.a);
    setKey(paramObject1);
    setValue(paramObject2);
  }
  
  public String getKey()
  {
    return this.key;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public Object getKeyObject()
  {
    return this.jdField_do;
  }
  
  public Object getValueObject()
  {
    return this.jdField_int;
  }
  
  public CollationKey getKeyForCollation()
  {
    return this.jdField_new;
  }
  
  public CollationKey getValueForCollation()
  {
    return this.jdField_if;
  }
  
  public Locale getLocale()
  {
    return this.a;
  }
  
  public void setKey(Object paramObject)
  {
    this.jdField_do = paramObject;
    this.key = this.jdField_do.toString();
    if ((paramObject instanceof String)) {
      this.jdField_new = this.jdField_for.getCollationKey(this.key);
    } else {
      this.jdField_new = null;
    }
  }
  
  public void setValue(Object paramObject)
  {
    this.jdField_int = paramObject;
    this.value = this.jdField_int.toString();
    if ((paramObject instanceof String)) {
      this.jdField_if = this.jdField_for.getCollationKey(this.value);
    } else {
      this.jdField_if = null;
    }
  }
  
  public void setKey(String paramString)
  {
    this.key = paramString;
    this.jdField_new = this.jdField_for.getCollationKey(this.key);
    this.jdField_do = paramString;
  }
  
  public void setValue(String paramString)
  {
    this.value = paramString;
    this.jdField_if = this.jdField_for.getCollationKey(this.value);
    this.jdField_int = this.key;
  }
  
  public void setLocale(Locale paramLocale)
  {
    this.a = paramLocale;
    this.jdField_for = CollatorUtil.getCollator(this.a);
    if ((this.jdField_do instanceof String)) {
      this.jdField_new = this.jdField_for.getCollationKey(this.key);
    }
    if ((this.jdField_int instanceof String)) {
      this.jdField_if = this.jdField_for.getCollationKey(this.value);
    }
  }
  
  public int compare(Object paramObject, String paramString)
  {
    KeyValue localKeyValue = (KeyValue)paramObject;
    int i;
    Comparable localComparable1;
    Comparable localComparable2;
    if ("Value".equals(paramString))
    {
      if (((this.jdField_int instanceof String)) && ((localKeyValue.getValueObject() instanceof String)))
      {
        i = this.jdField_if.compareTo(localKeyValue.getValueForCollation());
      }
      else if (((this.jdField_int instanceof Comparable)) && ((localKeyValue.getValueObject() instanceof Comparable)))
      {
        localComparable1 = (Comparable)this.jdField_int;
        localComparable2 = (Comparable)localKeyValue.getValueObject();
        i = localComparable1.compareTo(localComparable2);
      }
      else
      {
        i = this.value.compareTo(localKeyValue.getValue());
      }
    }
    else if (((this.jdField_do instanceof String)) && ((localKeyValue.getKeyObject() instanceof String)))
    {
      i = this.jdField_new.compareTo(localKeyValue.getKeyForCollation());
    }
    else if (((this.jdField_do instanceof Comparable)) && ((localKeyValue.getKeyObject() instanceof Comparable)))
    {
      localComparable1 = (Comparable)this.jdField_do;
      localComparable2 = (Comparable)localKeyValue.getKeyObject();
      i = localComparable1.compareTo(localComparable2);
    }
    else
    {
      i = this.key.compareTo(localKeyValue.getKey());
    }
    if (i > 0) {
      return 1;
    }
    if (i < 0) {
      return -1;
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.KeyValue
 * JD-Core Version:    0.7.0.1
 */