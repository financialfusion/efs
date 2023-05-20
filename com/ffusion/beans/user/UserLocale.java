package com.ffusion.beans.user;

import com.ffusion.util.LocaleUtil;
import com.ffusion.util.UserLocaleConsts;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Locale;

public class UserLocale
  implements UserLocaleConsts, Serializable
{
  protected String language = LocaleUtil.getDefaultLocale().getLanguage();
  protected String country = LocaleUtil.getDefaultLocale().getCountry();
  protected String dateFormat = "MM/dd/yyyy";
  protected String dateFormatName;
  protected String zoneFormat = "z";
  protected String zoneFormatName;
  protected String timeFormat = "H:mm";
  protected String timeFormatName;
  protected String dateTimeFormat = "MM/dd/yyyy/H:mm";
  protected String dateTimeFormatName;
  protected Locale locale;
  protected String localeName;
  
  public UserLocale() {}
  
  public UserLocale(Locale paramLocale)
  {
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.locale = paramLocale;
    this.language = paramLocale.getLanguage().toLowerCase();
    this.country = paramLocale.getCountry().toUpperCase();
    this.localeName = (this.language + "-" + this.country);
  }
  
  public UserLocale(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      throw new IllegalArgumentException();
    }
    this.language = paramString;
    this.country = "";
    this.locale = new Locale(paramString, this.country);
  }
  
  public UserLocale(String paramString1, String paramString2)
  {
    if ((paramString1 == null) || (paramString1.equals(""))) {
      throw new IllegalArgumentException();
    }
    this.language = paramString1.toLowerCase();
    this.country = paramString2.toUpperCase();
    this.locale = new Locale(paramString1, paramString2);
    this.localeName = (this.language + "_" + this.country);
  }
  
  public void setLanguage(String paramString)
  {
    this.language = paramString.toLowerCase();
  }
  
  public void setCountry(String paramString)
  {
    this.country = paramString.toUpperCase();
  }
  
  public void setLocale(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public void setLocaleName(String paramString)
  {
    this.localeName = paramString;
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateFormat = paramString;
  }
  
  public void setZoneFormat(String paramString)
  {
    this.zoneFormat = paramString;
  }
  
  public void setZoneFormatName(String paramString)
  {
    this.zoneFormatName = paramString;
  }
  
  public void setDateFormatName(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      this.dateFormatName = this.dateFormat;
    } else {
      this.dateFormatName = paramString;
    }
  }
  
  public void setTimeFormat(String paramString)
  {
    this.timeFormat = paramString;
  }
  
  public void setTimeFormatName(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      this.timeFormatName = this.timeFormat;
    } else {
      this.timeFormatName = paramString;
    }
  }
  
  public void setDateTimeFormat(String paramString)
  {
    this.dateTimeFormat = paramString;
  }
  
  public void setDateTimeFormatName(String paramString)
  {
    if ((paramString == null) || (paramString.equals(""))) {
      this.dateTimeFormatName = this.dateTimeFormat;
    } else {
      this.dateTimeFormatName = paramString;
    }
  }
  
  public Locale getLocale()
  {
    return this.locale;
  }
  
  public String getLocaleName()
  {
    return this.localeName;
  }
  
  public String getLanguage()
  {
    return this.language;
  }
  
  public String getCountry()
  {
    return this.country;
  }
  
  public String getDateFormat()
  {
    return this.dateFormat;
  }
  
  public String getDateFormatName()
  {
    return this.dateFormatName;
  }
  
  public String getZoneFormat()
  {
    return this.zoneFormat;
  }
  
  public String getZoneFormatName()
  {
    return this.zoneFormatName;
  }
  
  public String getTimeFormat()
  {
    return this.timeFormat;
  }
  
  public String getTimeFormatName()
  {
    return this.timeFormatName;
  }
  
  public String getDateTimeFormat()
  {
    return this.dateTimeFormat;
  }
  
  public String getDateTimeFormatName()
  {
    return this.dateTimeFormatName;
  }
  
  public void set(UserLocale paramUserLocale)
  {
    setLocale(paramUserLocale.getLocale());
    setLanguage(paramUserLocale.getLanguage());
    setCountry(paramUserLocale.getCountry());
    setDateFormat(paramUserLocale.getDateFormat());
    setDateFormatName(paramUserLocale.getDateFormatName());
    setTimeFormat(paramUserLocale.getTimeFormat());
    setTimeFormatName(paramUserLocale.getTimeFormatName());
    setDateTimeFormat(paramUserLocale.getDateTimeFormat());
    setDateTimeFormatName(paramUserLocale.getDateTimeFormatName());
    setLocaleName(this.language + "-" + this.country);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("Language")) {
      this.language = paramString2;
    } else if (paramString1.equals("Country")) {
      this.country = paramString2;
    } else if (paramString1.equals("DateFormat")) {
      this.dateFormat = paramString2;
    } else if (paramString1.equals("DateFormatName")) {
      this.dateFormatName = paramString2;
    } else if (paramString1.equals("TimeFormat")) {
      this.timeFormat = paramString2;
    } else if (paramString1.equals("TimeFormatName")) {
      this.timeFormatName = paramString2;
    } else if (paramString1.equals("DateTimeFormat")) {
      this.dateTimeFormat = paramString2;
    } else if (paramString1.equals("DateTimeFormatName")) {
      this.dateTimeFormatName = paramString2;
    } else if (paramString1.equals("LocaleName")) {
      this.localeName = paramString2;
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "USERINFO");
    XMLHandler.appendTag(localStringBuffer, "Language", this.language);
    XMLHandler.appendTag(localStringBuffer, "Country", this.country);
    XMLHandler.appendTag(localStringBuffer, "DateFormat", this.dateFormat);
    XMLHandler.appendTag(localStringBuffer, "DateFormatName", this.dateFormatName);
    XMLHandler.appendTag(localStringBuffer, "TimeFormat", this.timeFormat);
    XMLHandler.appendTag(localStringBuffer, "TimeFormatName", this.timeFormatName);
    XMLHandler.appendTag(localStringBuffer, "DateTimeFormat", this.dateTimeFormat);
    XMLHandler.appendTag(localStringBuffer, "DateTimeFormatName", this.dateTimeFormatName);
    XMLHandler.appendTag(localStringBuffer, "LocaleName", this.localeName);
    XMLHandler.appendEndTag(localStringBuffer, "USERINFO");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public class a
    extends XMLHandler
  {
    public a() {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.UserLocale
 * JD-Core Version:    0.7.0.1
 */