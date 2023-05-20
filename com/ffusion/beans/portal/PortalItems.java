package com.ffusion.beans.portal;

import com.ffusion.beans.ToXml;
import com.ffusion.util.XMLHandler;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class PortalItems
  extends ArrayList
  implements ToXml
{
  public static final String PORTAL_ITEMS = "PORTAL_ITEMS";
  public static final String PORTAL_ITEM = "PORTAL_ITEM";
  private String b = null;
  
  public void setCurrentItem(String paramString)
  {
    this.b = paramString;
  }
  
  public String getCurrentItem()
  {
    return this.b;
  }
  
  public String getContainsItem()
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      Object localObject = get(j);
      if (localObject.equals(this.b)) {
        return "true";
      }
    }
    return "false";
  }
  
  public String getContainsItemInverse()
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      Object localObject = get(j);
      if (localObject.equals(this.b)) {
        return "false";
      }
    }
    return "true";
  }
  
  public String getTop()
  {
    if (size() > 0) {
      return get(0).toString();
    }
    return null;
  }
  
  public String getBottom()
  {
    int i = size();
    if (i > 0) {
      return get(i - 1).toString();
    }
    return null;
  }
  
  public void setMoveUp(String paramString)
  {
    int i = size();
    for (int j = 1; j < i; j++)
    {
      Object localObject = get(j);
      if (localObject.equals(paramString))
      {
        remove(j);
        add(j - 1, paramString);
        break;
      }
    }
  }
  
  public void setMoveDown(String paramString)
  {
    int i = size();
    for (int j = 0; j < i - 1; j++)
    {
      Object localObject = get(j);
      if (localObject.equals(paramString))
      {
        remove(j);
        add(j + 1, paramString);
        break;
      }
    }
  }
  
  public void setAdd(String paramString)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      Object localObject = get(j);
      if (localObject.equals(paramString)) {
        return;
      }
    }
    add(paramString);
  }
  
  public void setRemove(String paramString)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      Object localObject = get(j);
      if (localObject.equals(paramString))
      {
        remove(j);
        break;
      }
    }
  }
  
  public boolean contains(String paramString)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      Object localObject = get(j);
      if (localObject.equals(paramString)) {
        return true;
      }
    }
    return false;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PORTAL_ITEMS");
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      XMLHandler.appendTag(localStringBuffer, "PORTAL_ITEM", (String)localIterator.next());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PORTAL_ITEMS");
    return localStringBuffer.toString();
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
  
  private class a
    extends XMLHandler
  {
    String jdField_int = "";
    
    private a() {}
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("PORTAL_ITEM")) {
        PortalItems.this.add(this.jdField_int);
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      this.jdField_int = new String(paramArrayOfChar, paramInt1, paramInt2);
    }
    
    a(PortalItems.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.PortalItems
 * JD-Core Version:    0.7.0.1
 */