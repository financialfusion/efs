package com.ffusion.util.beans;

import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.logging.DebugLog;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class LocalizableString
  implements ILocalizable
{
  private String aj = null;
  private String ai = null;
  private Object[] ak = null;
  
  public LocalizableString(String paramString1, String paramString2, Object[] paramArrayOfObject)
  {
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      throw new IllegalArgumentException("Invalid resource bundle name.");
    }
    if ((!paramString1.equalsIgnoreCase("dummy")) && ((paramString2 == null) || (paramString2.length() == 0))) {
      throw new IllegalArgumentException("Invalid resource key name.");
    }
    this.aj = paramString1;
    this.ai = paramString2;
    this.ak = paramArrayOfObject;
  }
  
  public Object localize(Locale paramLocale)
  {
    if (this.aj.equalsIgnoreCase("dummy")) {
      return this.ai;
    }
    ResourceBundle localResourceBundle = ResourceUtil.getBundle(this.aj, paramLocale);
    if (localResourceBundle == null)
    {
      DebugLog.log(Level.WARNING, "Unable to get the resource bundle '" + this.aj + "'");
      return this.ai;
    }
    String str;
    try
    {
      str = localResourceBundle.getString(this.ai);
      if (str == null)
      {
        DebugLog.log(Level.WARNING, "Unable to get the resource '" + this.ai + "' in the bundle '" + this.aj + "'");
        return this.ai;
      }
    }
    catch (Exception localException)
    {
      DebugLog.log(Level.WARNING, "Unable to get the resource '" + this.ai + "' in the bundle '" + this.aj + "'");
      return this.ai;
    }
    if ((this.ak == null) || (this.ak.length == 0)) {
      return str;
    }
    Object[] arrayOfObject = new Object[this.ak.length];
    for (int i = 0; i < this.ak.length; i++)
    {
      Object localObject = this.ak[i];
      if ((localObject instanceof ILocalizable)) {
        localObject = ((ILocalizable)localObject).localize(paramLocale);
      }
      arrayOfObject[i] = localObject;
    }
    return MessageFormat.format(str, arrayOfObject);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LocalizableString))) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    LocalizableString localLocalizableString = (LocalizableString)paramObject;
    int i = 1;
    if (this.ak == localLocalizableString.ak) {
      i = 1;
    } else if ((this.ak != null) && (localLocalizableString.ak != null))
    {
      if (this.ak.length != localLocalizableString.ak.length) {
        i = 0;
      } else {
        for (int j = 0; j < this.ak.length; j++) {
          if (this.ak[j] != localLocalizableString.ak[j]) {
            if ((this.ak[j] != null) && (localLocalizableString.ak[j] != null))
            {
              if (!this.ak[j].equals(localLocalizableString.ak[j]))
              {
                i = 0;
                break;
              }
            }
            else
            {
              i = 0;
              break;
            }
          }
        }
      }
    }
    else {
      i = 0;
    }
    return ((this.aj == localLocalizableString.aj) || ((this.aj != null) && (this.aj.equals(localLocalizableString.aj)))) && ((this.ai == localLocalizableString.ai) || ((this.ai != null) && (this.ai.equals(localLocalizableString.ai)))) && (i != 0);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.LocalizableString
 * JD-Core Version:    0.7.0.1
 */