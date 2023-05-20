package com.ffusion.beans.aggregation;

import com.ffusion.beans.IdCollection;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class Institutions
  extends FilteredList
  implements IdCollection
{
  public static final String INSTITUTIONS = "INSTITUTIONS";
  
  protected Institutions() {}
  
  public Institutions(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public Institution add()
  {
    Institution localInstitution = new Institution(this.locale);
    add(localInstitution);
    return localInstitution;
  }
  
  public Institution create()
  {
    Institution localInstitution = new Institution(this.locale);
    return localInstitution;
  }
  
  public Institution getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Institution localInstitution = (Institution)localIterator.next();
      if (localInstitution.getID().equalsIgnoreCase(paramString))
      {
        localObject = localInstitution;
        break;
      }
    }
    return localObject;
  }
  
  public Object getElementByID(String paramString)
  {
    return getByID(paramString);
  }
  
  public void removeByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Institution localInstitution = (Institution)localIterator.next();
      if (localInstitution.getID().equalsIgnoreCase(paramString))
      {
        localObject = localInstitution;
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
    XMLHandler.appendBeginTag(localStringBuffer, "INSTITUTIONS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Institution)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "INSTITUTIONS");
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
      if (paramString.equals("INSTITUTION"))
      {
        Institution localInstitution = Institutions.this.add();
        localInstitution.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.aggregation.Institutions
 * JD-Core Version:    0.7.0.1
 */