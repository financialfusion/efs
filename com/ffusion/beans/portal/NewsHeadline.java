package com.ffusion.beans.portal;

import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.net.URLDecoder;

public class NewsHeadline
  implements Serializable
{
  public static final String NEWS_HEADLINE = "NEWS_HEADLINE";
  public static final String HEADLINE = "HEADLINE";
  public static final String STORY_ID = "STORY_ID";
  public static final String STORY = "STORY";
  public static final String SOURCE = "SOURCE";
  private String jdField_do = "";
  private String jdField_new = "";
  private String jdField_try = "";
  private String a = "";
  private String jdField_int = "";
  private String jdField_for = "";
  private String jdField_if = "";
  
  public void setHeadline(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getHeadline()
  {
    return this.jdField_do;
  }
  
  public void setStory(String paramString)
  {
    this.jdField_try = paramString;
  }
  
  public String getStory()
  {
    return this.jdField_try;
  }
  
  public void setStoryID(String paramString)
  {
    this.jdField_new = paramString;
  }
  
  public String getStoryID()
  {
    return this.jdField_new;
  }
  
  public void setSource(String paramString)
  {
    this.a = paramString;
  }
  
  public String getSource()
  {
    return this.a;
  }
  
  public void setDate(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getDate()
  {
    return this.jdField_int;
  }
  
  public void setTime(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getTime()
  {
    return this.jdField_for;
  }
  
  public void setURL(String paramString)
  {
    try
    {
      this.jdField_if = URLDecoder.decode(paramString);
    }
    catch (Exception localException)
    {
      this.jdField_if = paramString;
    }
  }
  
  public String getURL()
  {
    return this.jdField_if;
  }
  
  public void set(NewsHeadline paramNewsHeadline)
  {
    this.jdField_do = paramNewsHeadline.getHeadline();
    this.jdField_new = paramNewsHeadline.getStoryID();
    this.jdField_try = paramNewsHeadline.getStory();
    this.a = paramNewsHeadline.getSource();
    this.jdField_int = paramNewsHeadline.getDate();
    this.jdField_for = paramNewsHeadline.getTime();
    this.jdField_if = paramNewsHeadline.getURL();
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "NEWS_HEADLINE");
    XMLHandler.appendTag(localStringBuffer, "HEADLINE", this.jdField_do);
    XMLHandler.appendTag(localStringBuffer, "STORY", this.jdField_try);
    XMLHandler.appendTag(localStringBuffer, "STORY_ID", this.jdField_new);
    XMLHandler.appendTag(localStringBuffer, "SOURCE", this.a);
    XMLHandler.appendEndTag(localStringBuffer, "NEWS_HEADLINE");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  private class a
    extends XMLHandler
  {
    String jdField_int = "";
    
    private a() {}
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("HEADLINE")) {
        NewsHeadline.this.jdField_do = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("STORY_ID")) {
        NewsHeadline.this.jdField_new = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("STORY")) {
        NewsHeadline.this.jdField_try = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("SOURCE")) {
        NewsHeadline.this.a = this.jdField_int;
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.jdField_int = new String(paramArrayOfChar, paramInt1, paramInt2);
    }
    
    a(NewsHeadline.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.NewsHeadline
 * JD-Core Version:    0.7.0.1
 */