package com.ffusion.beans.user;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.Filterable;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;

public class CustomTag
  extends ExtendABean
  implements Sortable, Filterable, Serializable
{
  public static final String TAG = "TAG";
  public static final String TAGNAME = "TAG_NAME";
  public static final String VALUE = "VALUE";
  public static final String ACTIVEDATE = "ACTIVE_DATE";
  public static final String EXPIREDATE = "EXPIRE_DATE";
  protected String tagName;
  protected String value;
  protected DateTime activeDate;
  protected DateTime expireDate;
  
  public CustomTag(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public CustomTag(Locale paramLocale, String paramString)
  {
    super(paramLocale);
    this.tagName = paramString;
  }
  
  public void setTagName(String paramString)
  {
    this.tagName = paramString;
  }
  
  public String getTagName()
  {
    return this.tagName;
  }
  
  public void setValue(String paramString)
  {
    this.value = paramString;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setActiveDate(DateTime paramDateTime)
  {
    this.activeDate = paramDateTime;
  }
  
  public void setActiveDate(String paramString)
  {
    try
    {
      if (this.activeDate == null) {
        this.activeDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.activeDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getActiveDateValue()
  {
    return this.activeDate;
  }
  
  public String getActiveDate()
  {
    return this.activeDate.toString();
  }
  
  public void setExpireDate(DateTime paramDateTime)
  {
    this.expireDate = paramDateTime;
  }
  
  public void setExpireDate(String paramString)
  {
    try
    {
      if (this.expireDate == null) {
        this.expireDate = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.expireDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getExpireDateValue()
  {
    return this.expireDate;
  }
  
  public String getExpireDate()
  {
    return this.expireDate.toString();
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.activeDate != null) {
      this.activeDate.setLocale(paramLocale);
    }
    if (this.expireDate != null) {
      this.expireDate.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.activeDate != null) {
      this.activeDate.setFormat(paramString);
    }
    if (this.expireDate != null) {
      this.expireDate.setFormat(paramString);
    }
  }
  
  public void set(CustomTag paramCustomTag)
  {
    super.set(paramCustomTag);
    setTagName(paramCustomTag.getTagName());
    setValue(paramCustomTag.getValue());
    if (paramCustomTag.getActiveDateValue() != null) {
      setActiveDate((DateTime)paramCustomTag.getActiveDateValue().clone());
    }
    if (paramCustomTag.getExpireDateValue() != null) {
      setExpireDate((DateTime)paramCustomTag.getExpireDateValue().clone());
    }
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("TAG_NAME")) {
      this.tagName = paramString2;
    } else if (paramString1.equals("VALUE")) {
      this.value = paramString2;
    } else if (paramString1.equals("ACTIVE_DATE")) {
      setActiveDate(paramString2);
    } else if (paramString1.equals("EXPIRE_DATE")) {
      setExpireDate(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    CustomTag localCustomTag = (CustomTag)paramObject;
    int i;
    if (this == localCustomTag) {
      i = 0;
    } else {
      i = getTagName().compareTo(localCustomTag.getTagName());
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    CustomTag localCustomTag = (CustomTag)paramObject;
    int i = 0;
    Collator localCollator = doGetCollator();
    if ((paramString.equals("TAG_NAME")) && (getTagName() != null) && (localCustomTag.getTagName() != null)) {
      i = localCollator.compare(getTagName(), localCustomTag.getTagName());
    } else if ((paramString.equals("VALUE")) && (getValue() != null) && (localCustomTag.getValue() != null)) {
      i = localCollator.compare(getValue(), localCustomTag.getValue());
    } else if ((paramString.equals("ACTIVE_DATE")) && (getActiveDateValue() != null) && (localCustomTag.getActiveDateValue() != null))
    {
      if (getActiveDateValue().equals(localCustomTag.getActiveDateValue())) {
        i = 0;
      } else if (getActiveDateValue().after(localCustomTag.getActiveDateValue())) {
        i = 1;
      } else {
        i = -1;
      }
    }
    else if ((paramString.equals("EXPIRE_DATE")) && (getExpireDateValue() != null) && (localCustomTag.getExpireDateValue() != null))
    {
      if (getExpireDateValue().equals(localCustomTag.getExpireDateValue())) {
        i = 0;
      } else if (getExpireDateValue().after(localCustomTag.getExpireDateValue())) {
        i = 1;
      } else {
        i = -1;
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("TAG_NAME")) && (getTagName() != null)) {
      return isFilterable(getTagName(), paramString2, paramString3);
    }
    if ((paramString1.equals("VALUE")) && (getValue() != null)) {
      return isFilterable(getValue(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "TAG");
    XMLHandler.appendTag(localStringBuffer, "TAG_NAME", this.tagName);
    XMLHandler.appendTag(localStringBuffer, "VALUE", this.value);
    if (this.activeDate != null) {
      XMLHandler.appendTag(localStringBuffer, "ACTIVE_DATE", this.activeDate.toXMLFormat());
    }
    if (this.expireDate != null) {
      XMLHandler.appendTag(localStringBuffer, "EXPIRE_DATE", this.expireDate.toXMLFormat());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "TAG");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.CustomTag
 * JD-Core Version:    0.7.0.1
 */