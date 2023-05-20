package com.ffusion.beans.messages;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class MessageQueueResponses
  extends FilteredList
{
  public boolean add(Object paramObject)
  {
    if ((paramObject instanceof MessageQueueResponse))
    {
      MessageQueueResponse localMessageQueueResponse = (MessageQueueResponse)paramObject;
      return super.add(localMessageQueueResponse);
    }
    return false;
  }
  
  public MessageQueueResponse create()
  {
    MessageQueueResponse localMessageQueueResponse = new MessageQueueResponse();
    add(localMessageQueueResponse);
    return localMessageQueueResponse;
  }
  
  public MessageQueueResponse createNoAdd()
  {
    MessageQueueResponse localMessageQueueResponse = new MessageQueueResponse();
    return localMessageQueueResponse;
  }
  
  public MessageQueueResponse getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MessageQueueResponse localMessageQueueResponse = (MessageQueueResponse)localIterator.next();
      if (localMessageQueueResponse.getResponseID().equals(paramString))
      {
        localObject = localMessageQueueResponse;
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
      MessageQueueResponse localMessageQueueResponse = (MessageQueueResponse)localIterator.next();
      if (localMessageQueueResponse.getResponseID().equals(paramString))
      {
        remove(localMessageQueueResponse);
        break;
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageQueueResponses
 * JD-Core Version:    0.7.0.1
 */