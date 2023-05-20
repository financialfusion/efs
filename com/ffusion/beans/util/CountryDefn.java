package com.ffusion.beans.util;

import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.ExtendABean;
import com.ffusion.util.logging.DebugLog;
import java.text.Collator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class CountryDefn
  extends ExtendABean
{
  public static final String SORT_FIELD_BANKLOOKUP_NAME = "SORT_BY_BANKLOOKUP";
  public static final String SORT_FIELD_COUNTRYCODE = "SORT_BY_COUNTRYCODE";
  public static final String SORT_FIELD_NAME = "SORT_BY_NAME";
  public static final String COUNTRY_RESOURCE_BUNDLE = "com.ffusion.util.states";
  public static final String COUNTRY_RESOURCE_PROPERTY_PREFIX = "Country";
  public static final String COUNTRY_RESOURCE_PROPERTY_LISTNAME = "CountryList";
  private String a;
  private String jdField_if;
  
  public CountryDefn() {}
  
  public CountryDefn(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public String getCountryCode()
  {
    return this.a;
  }
  
  public void setCountryCode(String paramString)
  {
    this.a = paramString;
  }
  
  public String getBankLookupCountry()
  {
    return this.jdField_if;
  }
  
  public void setBankLookupCountry(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getName()
  {
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.states", this.locale);
    if (localResourceBundle == null)
    {
      DebugLog.log("Cannot find resource bundle com.ffusion.util.states.");
      return this.jdField_if;
    }
    try
    {
      String str = localResourceBundle.getString("Country" + this.a);
      return str;
    }
    catch (MissingResourceException localMissingResourceException)
    {
      DebugLog.log("Cannot find country " + this.a + " in resource bundle " + "com.ffusion.util.states" + ".");
    }
    return this.jdField_if;
  }
  
  public String toString()
  {
    String str = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("CountryDefn Bean:" + str);
    localStringBuffer.append("   countryCode:" + this.a + str);
    localStringBuffer.append("   bankLookupCountry:" + this.jdField_if + str);
    return localStringBuffer.toString();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    if ((paramString.equals("SORT_BY_NAME")) && ((paramObject instanceof CountryDefn)))
    {
      if (paramObject == null) {
        return 1;
      }
      return doGetCollator().compare(getName(), ((CountryDefn)paramObject).getName());
    }
    if ((paramString.equals("SORT_BY_BANKLOOKUP")) && ((paramObject instanceof CountryDefn)))
    {
      if (paramObject == null) {
        return 1;
      }
      return getBankLookupCountry().compareTo(((CountryDefn)paramObject).getBankLookupCountry());
    }
    if ((paramString.equals("SORT_BY_COUNTRYCODE")) && ((paramObject instanceof CountryDefn)))
    {
      if (paramObject == null) {
        return 1;
      }
      return getCountryCode().compareTo(((CountryDefn)paramObject).getCountryCode());
    }
    return super.compare(paramObject, paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.CountryDefn
 * JD-Core Version:    0.7.0.1
 */