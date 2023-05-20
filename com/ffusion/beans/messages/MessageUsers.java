package com.ffusion.beans.messages;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class MessageUsers
  extends FilteredList
{
  public MessageUser add()
  {
    MessageUser localMessageUser = new MessageUser();
    add(localMessageUser);
    return localMessageUser;
  }
  
  public MessageUser create()
  {
    MessageUser localMessageUser = new MessageUser();
    return localMessageUser;
  }
  
  public MessageUser getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      MessageUser localMessageUser = (MessageUser)localIterator.next();
      if (localMessageUser.getUserID().equals(paramString))
      {
        localObject = localMessageUser;
        break;
      }
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageUsers
 * JD-Core Version:    0.7.0.1
 */