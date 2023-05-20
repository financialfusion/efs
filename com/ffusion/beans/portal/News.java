package com.ffusion.beans.portal;

import com.ffusion.beans.ToXml;
import com.ffusion.util.XMLHandler;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class News
  extends ArrayList
  implements ToXml
{
  public static final String NEWS = "NEWS";
  private boolean jdField_for = false;
  
  public boolean removeNewsCategory(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      NewsHeadlines localNewsHeadlines = (NewsHeadlines)localIterator.next();
      if (localNewsHeadlines.getCategory().equalsIgnoreCase(paramString))
      {
        remove(localNewsHeadlines);
        return true;
      }
    }
    return false;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "NEWS");
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localStringBuffer.append(((NewsHeadlines)localIterator.next()).toXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "NEWS");
    return XMLHandler.format(localStringBuffer.toString());
  }
  
  public void setInNews(boolean paramBoolean)
  {
    this.jdField_for = paramBoolean;
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  public void startXMLParsing(Reader paramReader)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramReader);
    }
    catch (Exception localException) {}
  }
  
  public void startXMLParsing(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramString);
    }
    catch (Exception localException) {}
  }
  
  private class a
    extends XMLHandler
  {
    private boolean jdField_int = false;
    private String jdField_try = "";
    private String jdField_new = "";
    
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("NEWS"))
      {
        this.jdField_int = true;
      }
      else if (((News.this.jdField_for) || (this.jdField_int)) && (paramString.equalsIgnoreCase("NEWS_HEADLINES")))
      {
        NewsHeadlines localNewsHeadlines = new NewsHeadlines();
        if (!this.jdField_try.equals("")) {
          localNewsHeadlines.setCategory(this.jdField_try);
        }
        if (!this.jdField_new.equals("")) {
          localNewsHeadlines.setMaxHeadlines(this.jdField_new);
        }
        localNewsHeadlines.continueXMLParsing(getHandler());
        News.this.add(localNewsHeadlines);
      }
    }
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("NEWS"))
      {
        this.jdField_int = false;
        News.this.jdField_for = false;
      }
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      if (paramString1.equalsIgnoreCase("CATEGORY")) {
        this.jdField_try = paramString2;
      } else if (paramString1.equalsIgnoreCase("MAX_HEADLINES")) {
        this.jdField_new = paramString2;
      }
    }
    
    a(News.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.News
 * JD-Core Version:    0.7.0.1
 */