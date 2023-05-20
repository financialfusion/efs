package com.ffusion.util.beans;

import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class LocalizableList
  extends ArrayList
  implements ILocalizable
{
  private String aw = null;
  private String av = null;
  
  public LocalizableList(String paramString1, String paramString2)
  {
    this.aw = paramString1;
    this.av = paramString2;
  }
  
  public LocalizableList()
  {
    this.aw = "com.ffusion.beans.user.resources";
    this.av = "ListSeperator";
  }
  
  public Object localize(Locale paramLocale)
  {
    ResourceBundle localResourceBundle = ResourceUtil.getBundle(this.aw, paramLocale);
    if (localResourceBundle == null)
    {
      DebugLog.log(Level.WARNING, "Unable to get the resource bundle '" + this.aw + "'");
      return this.av;
    }
    String str = localResourceBundle.getString(this.av);
    if (str == null)
    {
      DebugLog.log(Level.WARNING, "Unable to get the resource '" + this.av + "' in the bundle '" + this.aw + "'");
      return this.av;
    }
    StringBuffer localStringBuffer = new StringBuffer("");
    for (int i = 0; i < size(); i++)
    {
      Object localObject = get(i);
      if ((localObject instanceof ILocalizable)) {
        localObject = ((ILocalizable)localObject).localize(paramLocale);
      }
      localStringBuffer.append(localObject.toString());
      if (i != size() - 1) {
        localStringBuffer.append(str);
      }
    }
    return localStringBuffer.toString();
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LocalizableList))) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    LocalizableList localLocalizableList = (LocalizableList)paramObject;
    if (localLocalizableList.size() != size()) {
      return false;
    }
    for (int i = 0; i < localLocalizableList.size(); i++)
    {
      Object localObject = localLocalizableList.get(i);
      if (!localObject.equals(get(i))) {
        return false;
      }
    }
    return true;
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.LocalizableList
 * JD-Core Version:    0.7.0.1
 */