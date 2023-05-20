package com.ffusion.util.beans;

import com.ffusion.util.ILocalizable;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.settings.AccountSettings;
import java.util.Locale;

public class LocalizableAccountID
  implements ILocalizable
{
  private String au = null;
  private String as = null;
  private String at = null;
  private String ar = null;
  private String aq = null;
  
  public LocalizableAccountID(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
  {
    if ((paramString1 == null) || (paramString1.length() == 0)) {
      throw new IllegalArgumentException("Invalid account number specified.");
    }
    if ((paramString2 == null) || (paramString2.length() == 0)) {
      throw new IllegalArgumentException("Invalid format resource bundle name specified.");
    }
    if ((paramString3 == null) || (paramString3.length() == 0)) {
      throw new IllegalArgumentException("Invalid format resource bundle key pecified.");
    }
    if ((paramString4 == null) || (paramString4.length() == 0)) {
      throw new IllegalArgumentException("Invalid account type resource bundle name specified.");
    }
    if ((paramString5 == null) || (paramString5.length() == 0)) {
      throw new IllegalArgumentException("Invalid account type key specified.");
    }
    this.au = paramString1;
    this.as = paramString2;
    this.at = paramString3;
    this.ar = paramString4;
    this.aq = paramString5;
  }
  
  public Object localize(Locale paramLocale)
  {
    try
    {
      return AccountSettings.buildAccountDisplayText(this.au, this.as, this.at, this.ar, this.aq, paramLocale);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception: LocalizableAccountID.localize ", localException);
    }
    return null;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LocalizableAccountID))) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    LocalizableAccountID localLocalizableAccountID = (LocalizableAccountID)paramObject;
    return ((this.au == localLocalizableAccountID.au) || ((this.au != null) && (this.au.equals(localLocalizableAccountID.au)))) && ((this.as == localLocalizableAccountID.as) || ((this.as != null) && (this.as.equals(localLocalizableAccountID.as)))) && ((this.at == localLocalizableAccountID.at) || ((this.at != null) && (this.at.equals(localLocalizableAccountID.at)))) && ((this.ar == localLocalizableAccountID.ar) || ((this.ar != null) && (this.ar.equals(localLocalizableAccountID.ar)))) && ((this.aq == localLocalizableAccountID.aq) || ((this.aq != null) && (this.aq.equals(localLocalizableAccountID.aq))));
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.LocalizableAccountID
 * JD-Core Version:    0.7.0.1
 */