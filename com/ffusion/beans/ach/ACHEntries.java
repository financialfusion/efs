package com.ffusion.beans.ach;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Serializable;
import java.util.Iterator;

public class ACHEntries
  extends FilteredList
  implements XMLable, Serializable
{
  public Object create()
  {
    ACHEntry localACHEntry = new ACHEntry();
    String str = localACHEntry.getID();
    if ((str == null) || (str.length() == 0)) {
      localACHEntry.setID(Long.toString(System.currentTimeMillis()));
    }
    add(localACHEntry);
    return localACHEntry;
  }
  
  public Object createNoAdd()
  {
    ACHEntry localACHEntry = new ACHEntry();
    String str = localACHEntry.getID();
    if ((str == null) || (str.length() == 0)) {
      localACHEntry.setID(Long.toString(System.currentTimeMillis()));
    }
    return localACHEntry;
  }
  
  public boolean add(Object paramObject)
  {
    ACHEntry localACHEntry = (ACHEntry)paramObject;
    return super.add(localACHEntry);
  }
  
  public ACHEntry getByID(String paramString)
  {
    Object localObject = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        ACHEntry localACHEntry = (ACHEntry)localIterator.next();
        if (paramString.equals(localACHEntry.getID()))
        {
          localObject = localACHEntry;
          break;
        }
      }
    }
    return localObject;
  }
  
  public ACHEntry getByTrackingID(String paramString)
  {
    Object localObject = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        ACHEntry localACHEntry = (ACHEntry)localIterator.next();
        if (paramString.equals(localACHEntry.getTrackingID()))
        {
          localObject = localACHEntry;
          break;
        }
      }
    }
    return localObject;
  }
  
  public int validate(int paramInt, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4)
  {
    int i = 0;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ACHEntry localACHEntry = (ACHEntry)localIterator.next();
      if (localACHEntry.getActiveValue()) {
        if (!localACHEntry.validate(paramInt, paramBoolean1, paramBoolean2, paramBoolean3, paramBoolean4)) {
          i++;
        }
      }
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACHENTRIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ACHEntry)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ACHENTRIES");
    return localStringBuffer.toString();
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
      if (paramString.equals("ACHENTRY"))
      {
        ACHEntry localACHEntry = new ACHEntry();
        ACHEntries.this.add(localACHEntry);
        localACHEntry.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHEntries
 * JD-Core Version:    0.7.0.1
 */