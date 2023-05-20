package com.ffusion.util.beans;

import com.ffusion.util.ILocalizable;
import java.util.HashMap;
import java.util.Locale;

public class LocalizableProperty
  implements ILocalizable
{
  private String af = null;
  private HashMap ah = null;
  private String ag = null;
  
  public LocalizableProperty(String paramString1, HashMap paramHashMap, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      throw new IllegalArgumentException("Invalid property root specified.");
    }
    if (paramHashMap == null) {
      throw new IllegalArgumentException("Invalid properties collection specified.");
    }
    this.af = paramString1;
    this.ah = paramHashMap;
    this.ag = paramString2;
  }
  
  public Object localize(Locale paramLocale)
  {
    String str = this.af + "_" + paramLocale.getLanguage() + "_" + paramLocale.getCountry();
    if (this.ah.containsKey(str)) {
      return this.ah.get(str);
    }
    str = this.af + "_" + paramLocale.getLanguage();
    if (this.ah.containsKey(str)) {
      return this.ah.get(str);
    }
    str = this.af;
    if (this.ah.containsKey(str)) {
      return this.ah.get(str);
    }
    return this.ag;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LocalizableProperty))) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    LocalizableProperty localLocalizableProperty = (LocalizableProperty)paramObject;
    return ((this.af == localLocalizableProperty.af) || ((this.af != null) && (this.af.equals(localLocalizableProperty.af)))) && ((this.ah == localLocalizableProperty.ah) || ((this.ah != null) && (this.ah.equals(localLocalizableProperty.ah)))) && ((this.ag == localLocalizableProperty.ag) || ((this.ag != null) && (this.ag.equals(localLocalizableProperty.ag))));
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.LocalizableProperty
 * JD-Core Version:    0.7.0.1
 */