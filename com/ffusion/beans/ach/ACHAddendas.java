package com.ffusion.beans.ach;

import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class ACHAddendas
  extends ArrayList
  implements XMLable, Serializable
{
  public static final String ACHADDENDAS = "ACHADDENDAS";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  
  public Object create()
  {
    ACHAddenda localACHAddenda = new ACHAddenda();
    String str = localACHAddenda.getID();
    if ((str == null) || (str.length() == 0)) {
      localACHAddenda.setID(Long.toString(System.currentTimeMillis()));
    }
    add(localACHAddenda);
    return localACHAddenda;
  }
  
  public Object createNoAdd()
  {
    return new ACHAddenda();
  }
  
  public boolean add(Object paramObject)
  {
    ACHAddenda localACHAddenda = (ACHAddenda)paramObject;
    String str = localACHAddenda.getID();
    if ((str == null) || (str.length() == 0)) {
      localACHAddenda.setID(Long.toString(System.currentTimeMillis()));
    }
    return super.add(localACHAddenda);
  }
  
  public ACHAddenda getByID(String paramString)
  {
    int i = size();
    for (int j = 0; j < i; j++)
    {
      ACHAddenda localACHAddenda = (ACHAddenda)get(j);
      if (localACHAddenda.getID().equals(paramString)) {
        return localACHAddenda;
      }
    }
    return null;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACHADDENDAS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ACHAddenda)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ACHADDENDAS");
    return localStringBuffer.toString();
  }
  
  public String getAddendaString()
  {
    String str1 = "";
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ACHAddenda localACHAddenda = (ACHAddenda)localIterator.next();
      if (localACHAddenda.getPmtRelatedInfo() != null)
      {
        String str2 = localACHAddenda.getPmtRelatedInfo();
        localStringBuffer.append(str2);
        if ((localIterator.hasNext()) && (str2.length() < 80)) {
          for (int i = 80 - str2.length(); i > 0; i--) {
            localStringBuffer.append(" ");
          }
        }
      }
    }
    str1 = localStringBuffer.toString();
    return str1;
  }
  
  public String getAddendaStringSegmented()
  {
    String str1 = "";
    String str2 = getAddendaString().trim();
    str1 = Strings.replaceStr(str2, "\\", "\\" + _lineSeparator);
    return str1.trim();
  }
  
  public void setAddendaString(String paramString)
  {
    clear();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    while (i < paramString.length())
    {
      char c = paramString.charAt(i);
      i++;
      if (c >= ' ') {
        localStringBuffer.append(c);
      }
    }
    paramString = localStringBuffer.toString();
    ACHAddenda localACHAddenda;
    while (paramString.length() > 80)
    {
      String str = paramString.substring(0, 80);
      paramString = paramString.substring(80);
      localACHAddenda = (ACHAddenda)create();
      localACHAddenda.setPmtRelatedInfo(str);
    }
    if (paramString.length() > 0)
    {
      localACHAddenda = (ACHAddenda)create();
      localACHAddenda.setPmtRelatedInfo(paramString);
    }
  }
  
  public String toXML()
  {
    return getXML();
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
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public boolean remove(Object paramObject)
  {
    boolean bool = false;
    if (paramObject != null) {
      for (int i = 0; i < size(); i++) {
        if (paramObject == get(i))
        {
          remove(i);
          bool = true;
          break;
        }
      }
    }
    return bool;
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("ACHADDENDA"))
      {
        ACHAddenda localACHAddenda = new ACHAddenda();
        ACHAddendas.this.add(localACHAddenda);
        localACHAddenda.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHAddendas
 * JD-Core Version:    0.7.0.1
 */