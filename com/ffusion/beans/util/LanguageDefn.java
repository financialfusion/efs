package com.ffusion.beans.util;

import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Locale;

public class LanguageDefn
  extends ExtendABean
  implements Cloneable
{
  public static final String LANGUAGEDEFN = "LANGUAGEDEFN";
  public static final String ISDEFAULT = "ISDEFAULT";
  private String a;
  private boolean jdField_if;
  
  public LanguageDefn()
  {
    this.a = null;
    this.jdField_if = false;
  }
  
  public LanguageDefn(Locale paramLocale, boolean paramBoolean)
  {
    this.a = paramLocale.toString();
    this.jdField_if = paramBoolean;
  }
  
  public void setLanguage(String paramString)
  {
    this.a = paramString;
  }
  
  public String getLanguage()
  {
    return this.a;
  }
  
  public void setIsDefault(boolean paramBoolean)
  {
    this.jdField_if = paramBoolean;
  }
  
  public void setIsDefault(String paramString)
  {
    this.jdField_if = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean getIsDefault()
  {
    return this.jdField_if;
  }
  
  public String getDisplayName()
  {
    return ResourceUtil.getString(this.a, "com.ffusion.util.languages", this.locale);
  }
  
  public String toString()
  {
    String str = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("StateProvinceDefn Bean:" + str);
    localStringBuffer.append("   language:" + this.a + str);
    localStringBuffer.append("   isDefault:" + this.jdField_if + str);
    return localStringBuffer.toString();
  }
  
  public Object clone()
  {
    LanguageDefn localLanguageDefn = new LanguageDefn(this.locale, this.jdField_if);
    localLanguageDefn.a = this.a;
    return localLanguageDefn;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "LANGUAGEDEFN");
    if (this.a != null) {
      XMLHandler.appendTag(localStringBuffer, "LANGUAGE", this.a);
    }
    XMLHandler.appendTag(localStringBuffer, "ISDEFAULT", String.valueOf(this.jdField_if));
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "LANGUAGEDEFN");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("LANGUAGE")) {
      this.a = paramString2;
    } else if (paramString1.equals("ISDEFAULT")) {
      this.jdField_if = Boolean.valueOf(paramString2).booleanValue();
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.LanguageDefn
 * JD-Core Version:    0.7.0.1
 */