package com.ffusion.beans.ach;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

public class ACHBatches
  extends FilteredList
  implements Localeable, XMLable, Serializable
{
  public static final String ACHBATCHES = "ACHBATCHES";
  public static final String NAME = "NAME";
  public static final String GROUPID = "GROUPID";
  protected String datetype = "SHORT";
  
  public ACHBatches() {}
  
  public ACHBatches(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public Object create()
  {
    ACHBatch localACHBatch = new ACHBatch();
    add(localACHBatch);
    return localACHBatch;
  }
  
  public Object createNoAdd()
  {
    return new ACHBatch(this.locale);
  }
  
  public boolean add(Object paramObject)
  {
    ACHBatch localACHBatch = (ACHBatch)paramObject;
    localACHBatch.setLocale(this.locale);
    return super.add(localACHBatch);
  }
  
  public ACHBatch getByGroupID(String paramString)
  {
    return (ACHBatch)getFirstByFilter("GROUPID=" + paramString);
  }
  
  public ACHBatch getByID(String paramString)
  {
    ACHBatch localACHBatch = new ACHBatch();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localACHBatch = (ACHBatch)localIterator.next();
      if (localACHBatch.getID().equals(paramString)) {
        return localACHBatch;
      }
    }
    localACHBatch = null;
    return localACHBatch;
  }
  
  public ACHBatch getByName(String paramString)
  {
    ACHBatch localACHBatch = new ACHBatch();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localACHBatch = (ACHBatch)localIterator.next();
      if (localACHBatch.getName().trim().compareTo(paramString.trim()) == 0) {
        return localACHBatch;
      }
    }
    localACHBatch = null;
    return localACHBatch;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ACHBatch localACHBatch = (ACHBatch)localIterator.next();
      localACHBatch.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACHBATCHES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ACHBatch)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ACHBATCHES");
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
      if (paramString.equals("ACHBATCH"))
      {
        ACHBatch localACHBatch = new ACHBatch(ACHBatches.this.locale);
        ACHBatches.this.add(localACHBatch);
        localACHBatch.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHBatches
 * JD-Core Version:    0.7.0.1
 */