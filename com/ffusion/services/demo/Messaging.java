package com.ffusion.services.demo;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.messages.Message;
import com.ffusion.beans.messages.MessageThreads;
import com.ffusion.beans.messages.Messages;
import com.ffusion.services.Messaging2;
import com.ffusion.util.XMLHandler;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

public class Messaging
  extends Service
  implements Messaging2
{
  Messages aV = null;
  
  public void setSettings(String paramString) {}
  
  public String getSettings()
  {
    return "";
  }
  
  public void setUserName(String paramString) {}
  
  public void setPassword(String paramString) {}
  
  public int getMessages(Messages paramMessages)
  {
    if (this.aV == null)
    {
      this.aV = new Messages();
      jdMethod_char();
    }
    Object localObject;
    if (this.aV != null)
    {
      localObject = this.aV.iterator();
      while (((Iterator)localObject).hasNext())
      {
        Message localMessage1 = (Message)((Iterator)localObject).next();
        paramMessages.add(localMessage1);
      }
    }
    if (paramMessages.size() > 0)
    {
      localObject = new GregorianCalendar();
      int i = ((GregorianCalendar)localObject).get(1);
      int j = ((GregorianCalendar)localObject).get(2);
      Iterator localIterator = paramMessages.iterator();
      while (localIterator.hasNext())
      {
        Message localMessage2 = (Message)localIterator.next();
        DateTime localDateTime = localMessage2.getDateValue();
        localDateTime.set(1, i);
        localDateTime.set(2, j);
        if (localDateTime.after(localObject)) {
          localDateTime.add(2, -1);
        }
      }
      paramMessages.setSortedBy("DATE");
    }
    return 0;
  }
  
  public int getMessages(Object paramObject, Messages paramMessages)
  {
    return 0;
  }
  
  public int sendMessage(Message paramMessage)
  {
    if (this.aV == null) {
      this.aV = new Messages();
    }
    Message localMessage = this.aV.create();
    GregorianCalendar localGregorianCalendar = new GregorianCalendar();
    paramMessage.setDate(localGregorianCalendar.getTime());
    paramMessage.setTo("Customer Service");
    paramMessage.setFrom("Teri Bowman");
    paramMessage.setID(Integer.toString((int)(Math.random() * 100000.0D)));
    localMessage.set(paramMessage);
    return 0;
  }
  
  public int sendMessage(Message paramMessage, Object paramObject)
  {
    return 0;
  }
  
  public int deleteMessage(Object paramObject)
  {
    if ((paramObject instanceof Message))
    {
      Message localMessage1 = (Message)paramObject;
      Message localMessage2 = this.aV.getByID(localMessage1.getID());
      if (localMessage2 != null) {
        this.aV.remove(localMessage2);
      }
    }
    return 0;
  }
  
  private void jdMethod_char()
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new MessagingXMLHandler(), getXMLReader(this, this.m_URL));
    }
    catch (Throwable localThrowable) {}
  }
  
  public int markMessageAsRead(Object paramObject)
  {
    if ((paramObject instanceof Message))
    {
      Message localMessage = (Message)paramObject;
      GregorianCalendar localGregorianCalendar = new GregorianCalendar();
      localMessage.setReadDate(localGregorianCalendar.getTime());
    }
    return 0;
  }
  
  public int getNumberOfUnreadMessages(Object paramObject)
  {
    MessageThreads localMessageThreads = null;
    if ((paramObject instanceof MessageThreads)) {
      localMessageThreads = (MessageThreads)paramObject;
    } else {
      return 8502;
    }
    int i = 0;
    if (this.aV == null) {
      return 0;
    }
    Iterator localIterator = this.aV.iterator();
    while (localIterator.hasNext())
    {
      Message localMessage = (Message)localIterator.next();
      if ((localMessage.getReadDateValue() == null) && (localMessage.getFromType().equals("0"))) {
        i++;
      }
    }
    localMessageThreads.setNewCount(i);
    return 0;
  }
  
  protected class MessagingXMLHandler
    extends XMLHandler
  {
    protected MessagingXMLHandler() {}
    
    public void startElement(String paramString)
    {
      if ((paramString.equals("MESSAGES")) && (Messaging.this.aV != null)) {
        Messaging.this.aV.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.demo.Messaging
 * JD-Core Version:    0.7.0.1
 */