package com.ffusion.beans.portal;

import com.ffusion.util.XMLHandler;
import java.io.Reader;
import java.util.ArrayList;

public class PortalContent
  extends ArrayList
{
  public static final String CONTENT_SERVER_URL = "CONTENT_SERVER_URL";
  public static final String CONTENT_SERVER_USERNAME = "CONTENT_SERVER_USERNAME";
  public static final String CONTENT_SERVER_PASSWORD = "CONTENT_SERVER_PASSWORD";
  public static final String CONTENT_LOAD_MAX_TIME = "CONTENT_LOAD_MAX_TIME";
  public static final String STOCK_NEWS_URL = "STOCK_NEWS_URL";
  private String jdField_for;
  private String jdField_int;
  private String a;
  private String jdField_if;
  private String jdField_do;
  
  public void setContentServerURL(String paramString)
  {
    this.jdField_for = paramString;
  }
  
  public String getContentServerURL()
  {
    return this.jdField_for;
  }
  
  public void setContentServerUsername(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getContentServerUsername()
  {
    return this.jdField_int;
  }
  
  public void setContentServerPassword(String paramString)
  {
    this.a = paramString;
  }
  
  public String getContentServerPassword()
  {
    return this.a;
  }
  
  public String getContentServerMaxLoadTime()
  {
    return this.jdField_if;
  }
  
  public void setContentServerMaxLoadTime(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public String getStockNewsURL()
  {
    return this.jdField_do;
  }
  
  public void setStockNewsURL(String paramString)
  {
    this.jdField_do = paramString;
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
  
  private class a
    extends XMLHandler
  {
    private String jdField_int = "";
    
    private a() {}
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("CONTENT_SERVER_URL")) {
        PortalContent.this.jdField_for = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("CONTENT_SERVER_USERNAME")) {
        PortalContent.this.jdField_int = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("CONTENT_SERVER_PASSWORD")) {
        PortalContent.this.a = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("CONTENT_LOAD_MAX_TIME")) {
        PortalContent.this.jdField_if = this.jdField_int;
      } else if (paramString.equalsIgnoreCase("STOCK_NEWS_URL")) {
        PortalContent.this.jdField_do = this.jdField_int;
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.jdField_int = new String(paramArrayOfChar, paramInt1, paramInt2);
    }
    
    a(PortalContent.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.PortalContent
 * JD-Core Version:    0.7.0.1
 */