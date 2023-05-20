package com.ffusion.beans.history;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class Histories
  extends FilteredList
{
  protected String datetype = "SHORT";
  private static final String jdField_byte = "HISTORYLIST";
  
  protected Histories() {}
  
  public Histories(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
  }
  
  public History add()
  {
    History localHistory = new History(this.locale);
    add(localHistory);
    return localHistory;
  }
  
  public History create()
  {
    History localHistory = new History(this.locale);
    return localHistory;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      History localHistory = (History)localIterator.next();
      localHistory.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public History getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      History localHistory = (History)localIterator.next();
      if (localHistory.getId().equals(paramString))
      {
        localObject = localHistory;
        break;
      }
    }
    return localObject;
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
    XMLHandler.appendBeginTag(localStringBuffer, "HISTORYLIST");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((History)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "HISTORYLIST");
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
  
  public void set(Histories paramHistories)
  {
    Iterator localIterator = paramHistories.iterator();
    while (localIterator.hasNext())
    {
      History localHistory = (History)localIterator.next();
      if (localHistory != null) {
        add(localHistory);
      }
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
      if (paramString.equals("HISTORYINFO"))
      {
        History localHistory = Histories.this.add();
        localHistory.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.history.Histories
 * JD-Core Version:    0.7.0.1
 */