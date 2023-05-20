package com.ffusion.beans.util;

import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.ExtendABean;
import java.text.Collator;
import java.util.Locale;
import java.util.ResourceBundle;

public class StateProvinceDefn
  extends ExtendABean
{
  public static final String SORT_FIELD_STATEKEY = "SORT_BY_STATEKEY";
  public static final String SORT_FIELD_COUNTRYCODE = "SORT_BY_COUNTRYCODE";
  public static final String SORT_FIELD_NAME = "SORT_BY_NAME";
  private String jdField_do;
  private String a;
  private static final String jdField_if = "USA";
  
  public StateProvinceDefn()
  {
    this.jdField_do = null;
    this.a = null;
  }
  
  public StateProvinceDefn(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public void setCountryCode(String paramString)
  {
    this.a = paramString;
  }
  
  public void setStateKey(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getStateKey()
  {
    return this.jdField_do;
  }
  
  public String getCountryCode()
  {
    return this.a;
  }
  
  public String getName()
  {
    ResourceBundle localResourceBundle = ResourceUtil.getBundle("com.ffusion.util.states", this.locale);
    if (localResourceBundle == null) {
      return this.jdField_do;
    }
    String str;
    if (this.a.equals("USA")) {
      str = localResourceBundle.getString("State" + this.jdField_do);
    } else {
      str = localResourceBundle.getString("State_" + this.a + "_" + this.jdField_do);
    }
    if (str == null) {
      return this.jdField_do;
    }
    return str;
  }
  
  public String toString()
  {
    String str = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("StateProvinceDefn Bean:" + str);
    localStringBuffer.append("   countryCode:" + this.a + str);
    localStringBuffer.append("   stateKey:" + this.jdField_do + str);
    return localStringBuffer.toString();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    if ((paramString.equals("SORT_BY_NAME")) && ((paramObject instanceof StateProvinceDefn)))
    {
      if (paramObject == null) {
        return 1;
      }
      return doGetCollator().compare(getName(), ((StateProvinceDefn)paramObject).getName());
    }
    if ((paramString.equals("SORT_BY_STATEKEY")) && ((paramObject instanceof StateProvinceDefn)))
    {
      if (paramObject == null) {
        return 1;
      }
      return getStateKey().compareTo(((StateProvinceDefn)paramObject).getStateKey());
    }
    if ((paramString.equals("SORT_BY_COUNTRYCODE")) && ((paramObject instanceof StateProvinceDefn)))
    {
      if (paramObject == null) {
        return 1;
      }
      return getCountryCode().compareTo(((StateProvinceDefn)paramObject).getCountryCode());
    }
    return super.compare(paramObject, paramString);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.StateProvinceDefn
 * JD-Core Version:    0.7.0.1
 */