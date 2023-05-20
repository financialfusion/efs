package com.ffusion.beans.messages;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;
import java.util.Locale;

public class Messages
  extends FilteredList
  implements Localeable, XMLable
{
  public static final String MESSAGES = "MESSAGES";
  protected String datetype = "SHORT";
  
  public Messages() {}
  
  public Messages(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
  }
  
  public Message create()
  {
    Message localMessage = new Message(this.locale);
    add(localMessage);
    return localMessage;
  }
  
  public Message createNoAdd()
  {
    Message localMessage = new Message(this.locale);
    return localMessage;
  }
  
  public boolean add(Object paramObject)
  {
    if ((paramObject instanceof Message))
    {
      Message localMessage = (Message)paramObject;
      localMessage.setLocale(this.locale);
      return super.add(localMessage);
    }
    return false;
  }
  
  public Message getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Message localMessage = (Message)localIterator.next();
      if (localMessage.getID().equals(paramString))
      {
        localObject = localMessage;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByID(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Message localMessage = (Message)localIterator.next();
      if (localMessage.getID().equals(paramString))
      {
        remove(localMessage);
        break;
      }
    }
  }
  
  public Object clone()
  {
    Messages localMessages = new Messages(this.locale);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Message localMessage1 = (Message)localIterator.next();
      Message localMessage2 = new Message(this.locale);
      localMessage2.set(localMessage1);
      localMessages.add(localMessage2);
    }
    return localMessages;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Message localMessage = (Message)localIterator.next();
      localMessage.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
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
    XMLHandler.appendBeginTag(localStringBuffer, "MESSAGES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Message)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "MESSAGES");
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
  
  public void set(Messages paramMessages)
  {
    clear();
    Iterator localIterator = paramMessages.iterator();
    while (localIterator.hasNext())
    {
      Message localMessage1 = (Message)localIterator.next();
      Message localMessage2 = new Message(this.locale);
      localMessage2.set(localMessage1);
      add(localMessage2);
    }
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("MESSAGE"))
      {
        Message localMessage = Messages.this.create();
        localMessage.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.Messages
 * JD-Core Version:    0.7.0.1
 */