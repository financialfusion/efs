package com.ffusion.beans.messages;

import com.ffusion.beans.DateTime;
import com.ffusion.util.FilteredList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class MessageThreads
  extends FilteredList
{
  private int jdField_byte = 0;
  protected String datetype = "SHORT";
  
  public MessageThreads(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public boolean add(Object paramObject)
  {
    if ((paramObject instanceof MessageThread))
    {
      MessageThread localMessageThread = (MessageThread)paramObject;
      localMessageThread.setLocale(this.locale);
      return super.add(localMessageThread);
    }
    return false;
  }
  
  public MessageThread create()
  {
    MessageThread localMessageThread = new MessageThread(this.locale);
    add(localMessageThread);
    return localMessageThread;
  }
  
  public MessageThread createNoAdd()
  {
    MessageThread localMessageThread = new MessageThread(this.locale);
    return localMessageThread;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MessageThread localMessageThread = (MessageThread)localIterator.next();
      localMessageThread.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public MessageThread getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MessageThread localMessageThread = (MessageThread)localIterator.next();
      if (localMessageThread.getThreadID().equals(paramString))
      {
        localObject = localMessageThread;
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
      MessageThread localMessageThread = (MessageThread)localIterator.next();
      if (localMessageThread.getThreadID().equals(paramString))
      {
        remove(localMessageThread);
        break;
      }
    }
  }
  
  public String getNewCount()
  {
    return String.valueOf(this.jdField_byte);
  }
  
  public int getNewCountValue()
  {
    return this.jdField_byte;
  }
  
  public void setNewCount(String paramString)
  {
    Integer localInteger = new Integer(paramString);
    this.jdField_byte = localInteger.intValue();
  }
  
  public void setNewCount(int paramInt)
  {
    this.jdField_byte = paramInt;
  }
  
  public String getOldestCaseInDays()
  {
    float f1 = 0.0F;
    MessageThread localMessageThread = null;
    DateTime localDateTime = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localMessageThread = (MessageThread)localIterator.next();
      localDateTime = localMessageThread.getCreateDateValue();
      float f2 = localDateTime.getDiff(Calendar.getInstance(), 3) * -1.0F;
      if (f2 > f1) {
        f1 = f2;
      }
    }
    return Long.toString(f1);
  }
  
  public void set(MessageThreads paramMessageThreads)
  {
    clear();
    Iterator localIterator = paramMessageThreads.iterator();
    while (localIterator.hasNext())
    {
      MessageThread localMessageThread1 = (MessageThread)localIterator.next();
      MessageThread localMessageThread2 = new MessageThread(this.locale);
      localMessageThread2.set(localMessageThread1);
      add(localMessageThread2);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageThreads
 * JD-Core Version:    0.7.0.1
 */