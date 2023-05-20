package com.ffusion.beans.messages;

import com.ffusion.util.FilteredList;

public class MessageCounts
  extends FilteredList
{
  public MessageCount create()
  {
    MessageCount localMessageCount = new MessageCount();
    add(localMessageCount);
    return localMessageCount;
  }
  
  public MessageCount createNoAdd()
  {
    MessageCount localMessageCount = new MessageCount();
    return localMessageCount;
  }
  
  public boolean add(Object paramObject)
  {
    if ((paramObject instanceof MessageCount))
    {
      MessageCount localMessageCount = (MessageCount)paramObject;
      return super.add(localMessageCount);
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageCounts
 * JD-Core Version:    0.7.0.1
 */