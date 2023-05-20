package com.ffusion.util.beans;

import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.ResourceUtil;
import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class LocalizableDate
  implements ILocalizable
{
  private long al = -1L;
  private TimeZone ap = null;
  private boolean am = false;
  private String ao = null;
  private Locale an = null;
  
  public LocalizableDate(DateTime paramDateTime, boolean paramBoolean)
  {
    this(paramDateTime.getTime().getTime(), paramDateTime.getTimeZone(), paramBoolean);
    this.an = paramDateTime.getLocale();
  }
  
  public LocalizableDate(long paramLong, TimeZone paramTimeZone, boolean paramBoolean)
  {
    this(paramLong, paramTimeZone, paramBoolean, "com.ffusion.beans.user.resources");
  }
  
  public LocalizableDate(long paramLong, TimeZone paramTimeZone, boolean paramBoolean, String paramString)
  {
    this.al = paramLong;
    this.ap = paramTimeZone;
    this.am = paramBoolean;
    this.ao = paramString;
  }
  
  public Object localize(Locale paramLocale)
  {
    String str = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", paramLocale);
    if (this.am) {
      str = str + " " + ResourceUtil.getString("TimeFormat", "com.ffusion.beans.user.resources", paramLocale);
    }
    DateFormat localDateFormat;
    if (this.ap == null) {
      localDateFormat = DateFormatUtil.getFormatter(str, paramLocale);
    } else {
      localDateFormat = DateFormatUtil.getFormatter(str, paramLocale, this.ap);
    }
    return localDateFormat.format(new Date(this.al));
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LocalizableDate))) {
      return false;
    }
    if (paramObject == this) {
      return true;
    }
    LocalizableDate localLocalizableDate = (LocalizableDate)paramObject;
    if ((this.an != null) && (localLocalizableDate.an != null) && (localize(this.an).equals(localLocalizableDate.localize(localLocalizableDate.an)))) {
      return true;
    }
    if (localLocalizableDate.al != this.al) {
      return false;
    }
    return localLocalizableDate.ap.equals(this.ap);
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.beans.LocalizableDate
 * JD-Core Version:    0.7.0.1
 */