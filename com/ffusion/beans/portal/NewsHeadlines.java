package com.ffusion.beans.portal;

import com.ffusion.util.XMLHandler;
import java.util.ArrayList;
import java.util.Iterator;

public class NewsHeadlines
  extends ArrayList
{
  public static final String NEWS_HEADLINES = "NEWS_HEADLINES";
  public static final String CATEGORY = "CATEGORY";
  public static final String MAX_HEADLINES = "MAX_HEADLINES";
  private String jdField_if = "";
  private int a = 10;
  
  public void setCategory(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getCategory()
  {
    return this.jdField_if;
  }
  
  public void setMaxHeadlines(int paramInt)
  {
    this.a = paramInt;
  }
  
  public void setMaxHeadlines(String paramString)
  {
    try
    {
      this.a = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public String getMaxHeadlines()
  {
    return String.valueOf(this.a);
  }
  
  public int getMaxHeadlinesValue()
  {
    return this.a;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.openTag(localStringBuffer, "NEWS_HEADLINES");
    XMLHandler.appendAttribute(localStringBuffer, "CATEGORY", this.jdField_if);
    XMLHandler.appendAttribute(localStringBuffer, "MAX_HEADLINES", String.valueOf(this.a));
    XMLHandler.closeTag(localStringBuffer, false);
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localStringBuffer.append(((NewsHeadline)localIterator.next()).toXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "NEWS_HEADLINES");
    return XMLHandler.format(localStringBuffer.toString());
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  private class a
    extends XMLHandler
  {
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("NEWS_HEADLINE"))
      {
        NewsHeadline localNewsHeadline = new NewsHeadline();
        localNewsHeadline.continueXMLParsing(getHandler());
        NewsHeadlines.this.add(localNewsHeadline);
      }
    }
    
    a(NewsHeadlines.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.NewsHeadlines
 * JD-Core Version:    0.7.0.1
 */