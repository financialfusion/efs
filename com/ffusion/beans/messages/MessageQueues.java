package com.ffusion.beans.messages;

import com.ffusion.beans.IdCollection;
import com.ffusion.util.FilteredList;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

public class MessageQueues
  extends FilteredList
  implements IdCollection
{
  public String currentStatusID;
  public String currentProductID;
  public String currentEmployeeID;
  
  public MessageQueues() {}
  
  public MessageQueues(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public String getIsMember()
  {
    String str = "false";
    MessageQueue localMessageQueue = null;
    if ((this.currentProductID != null) && (this.currentStatusID != null)) {
      localMessageQueue = getByStatProdID(this.currentStatusID, this.currentProductID);
    }
    if (localMessageQueue != null)
    {
      MessageQueueMembers localMessageQueueMembers1 = localMessageQueue.getActiveQueueMembers();
      MessageQueueMembers localMessageQueueMembers2 = localMessageQueue.getInactiveQueueMembers();
      if ((localMessageQueueMembers1.getByID(this.currentEmployeeID) != null) || (localMessageQueueMembers2.getByID(this.currentEmployeeID) != null)) {
        str = "true";
      }
    }
    return str;
  }
  
  public boolean add(Object paramObject)
  {
    if ((paramObject instanceof MessageQueue))
    {
      MessageQueue localMessageQueue = (MessageQueue)paramObject;
      return super.add(localMessageQueue);
    }
    return false;
  }
  
  public MessageQueue create()
  {
    MessageQueue localMessageQueue = new MessageQueue();
    add(localMessageQueue);
    return localMessageQueue;
  }
  
  public MessageQueue createNoAdd()
  {
    MessageQueue localMessageQueue = new MessageQueue();
    return localMessageQueue;
  }
  
  public void addMessageQueue(MessageQueue paramMessageQueue)
  {
    add(paramMessageQueue);
  }
  
  public Object getElementByID(String paramString)
  {
    return getByID(paramString);
  }
  
  public MessageQueue getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MessageQueue localMessageQueue = (MessageQueue)localIterator.next();
      if (localMessageQueue.getQueueID().equals(paramString))
      {
        localObject = localMessageQueue;
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
      MessageQueue localMessageQueue = (MessageQueue)localIterator.next();
      if (localMessageQueue.getId().equals(paramString))
      {
        remove(localMessageQueue);
        break;
      }
    }
  }
  
  public MessageQueue getByStatProdID(String paramString1, String paramString2)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MessageQueue localMessageQueue = (MessageQueue)localIterator.next();
      if ((localMessageQueue.getQueueStatusID().equals(paramString1)) && (localMessageQueue.getQueueProductID().equals(paramString2)))
      {
        localObject = localMessageQueue;
        break;
      }
    }
    return localObject;
  }
  
  public MessageQueue getByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MessageQueue localMessageQueue = (MessageQueue)localIterator.next();
      if (localMessageQueue.getQueueName().equals(paramString))
      {
        localObject = localMessageQueue;
        break;
      }
    }
    return localObject;
  }
  
  public MessageQueue getByName(String paramString1, String paramString2)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MessageQueue localMessageQueue = (MessageQueue)localIterator.next();
      String str = localMessageQueue.getQueueName(paramString1);
      if ((str != null) && (str.equals(paramString2)))
      {
        localObject = localMessageQueue;
        break;
      }
    }
    return localObject;
  }
  
  public void setRemoveFromFilteredItems(String paramString)
  {
    StringTokenizer localStringTokenizer = null;
    if (this.filteredList != null)
    {
      Iterator localIterator = this.filteredList.iterator();
      while (localIterator.hasNext())
      {
        MessageQueue localMessageQueue = (MessageQueue)localIterator.next();
        String str1 = localMessageQueue.getQueueID();
        int i = 0;
        localStringTokenizer = new StringTokenizer(paramString, ", ");
        while (localStringTokenizer.hasMoreTokens())
        {
          String str2 = localStringTokenizer.nextToken();
          if (str2.equals(str1))
          {
            i = 1;
            break;
          }
        }
        if (i != 0) {
          localIterator.remove();
        }
      }
    }
  }
  
  public void set(MessageQueues paramMessageQueues)
  {
    clear();
    Iterator localIterator = paramMessageQueues.iterator();
    while (localIterator.hasNext())
    {
      MessageQueue localMessageQueue1 = (MessageQueue)localIterator.next();
      MessageQueue localMessageQueue2 = new MessageQueue();
      localMessageQueue2.set(localMessageQueue1);
      add(localMessageQueue2);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageQueues
 * JD-Core Version:    0.7.0.1
 */