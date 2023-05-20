package com.ffusion.beans.user;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class CustomTags
  extends FilteredList
  implements IMSDefines
{
  public static final String TAGGROUP = "TAG_GROUP";
  protected String datetype = "SHORT";
  
  public CustomTags(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public CustomTag add()
  {
    CustomTag localCustomTag = new CustomTag(this.locale);
    add(localCustomTag);
    return localCustomTag;
  }
  
  public CustomTag add(String paramString)
  {
    CustomTag localCustomTag = new CustomTag(this.locale, paramString);
    add(localCustomTag);
    return localCustomTag;
  }
  
  public CustomTag create()
  {
    CustomTag localCustomTag = new CustomTag(this.locale);
    return localCustomTag;
  }
  
  public CustomTag create(String paramString)
  {
    CustomTag localCustomTag = new CustomTag(this.locale, paramString);
    return localCustomTag;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      CustomTag localCustomTag = (CustomTag)localIterator.next();
      localCustomTag.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public CustomTag getByTagName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      CustomTag localCustomTag = (CustomTag)localIterator.next();
      if (localCustomTag.getTagName().equalsIgnoreCase(paramString))
      {
        localObject = localCustomTag;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByTagName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      CustomTag localCustomTag = (CustomTag)localIterator.next();
      if (localCustomTag.getTagName().equalsIgnoreCase(paramString))
      {
        localObject = localCustomTag;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "TAG_GROUP");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((CustomTag)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "TAG_GROUP");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("TAG"))
      {
        CustomTag localCustomTag = CustomTags.this.add();
        localCustomTag.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.CustomTags
 * JD-Core Version:    0.7.0.1
 */