package com.ffusion.beans.util;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Collection;
import java.util.Iterator;

public class LanguageDefns
  extends FilteredList
{
  public static final String LANGUAGEDEFNS = "LANGUAGEDEFNS";
  
  public LanguageDefns(Collection paramCollection)
  {
    Iterator localIterator = paramCollection.iterator();
    while (localIterator.hasNext()) {
      try
      {
        LanguageDefn localLanguageDefn = (LanguageDefn)localIterator.next();
        add(localLanguageDefn.clone());
      }
      catch (ClassCastException localClassCastException) {}
    }
  }
  
  public LanguageDefns() {}
  
  public String toString()
  {
    String str = System.getProperty("line.separator");
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("StateProvinceDefns Bean:" + str);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      LanguageDefn localLanguageDefn = (LanguageDefn)localIterator.next();
      localStringBuffer.append(localLanguageDefn.toString());
    }
    return localStringBuffer.toString();
  }
  
  public LanguageDefn add()
  {
    LanguageDefn localLanguageDefn = new LanguageDefn();
    super.add(localLanguageDefn);
    return localLanguageDefn;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "LANGUAGEDEFNS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((LanguageDefn)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "LANGUAGEDEFNS");
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
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("LANGUAGEDEFN"))
      {
        LanguageDefn localLanguageDefn = LanguageDefns.this.add();
        localLanguageDefn.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.LanguageDefns
 * JD-Core Version:    0.7.0.1
 */