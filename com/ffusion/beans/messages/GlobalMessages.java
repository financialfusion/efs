package com.ffusion.beans.messages;

import com.ffusion.util.FilteredList;
import java.util.Iterator;
import java.util.Locale;

public class GlobalMessages
  extends FilteredList
{
  public GlobalMessages() {}
  
  public GlobalMessages(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public GlobalMessage add()
  {
    GlobalMessage localGlobalMessage = new GlobalMessage(this.locale);
    add(localGlobalMessage);
    return localGlobalMessage;
  }
  
  public GlobalMessage create()
  {
    GlobalMessage localGlobalMessage = new GlobalMessage(this.locale);
    return localGlobalMessage;
  }
  
  public void removeByID(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      removeByID(i);
    }
    catch (Exception localException) {}
  }
  
  public void removeByID(int paramInt)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      GlobalMessage localGlobalMessage = (GlobalMessage)localIterator.next();
      if (localGlobalMessage.getGlobalMsgIDValue() == paramInt)
      {
        remove(localGlobalMessage);
        break;
      }
    }
  }
  
  public GlobalMessage getByID(String paramString)
  {
    try
    {
      int i = Integer.parseInt(paramString);
      return getByID(i);
    }
    catch (Exception localException) {}
    return null;
  }
  
  public GlobalMessage getByID(int paramInt)
  {
    Object localObject = null;
    int i = 0;
    Iterator localIterator = iterator();
    while (localIterator.hasNext() == true)
    {
      GlobalMessage localGlobalMessage = (GlobalMessage)localIterator.next();
      if (localGlobalMessage.getGlobalMsgIDValue() == paramInt)
      {
        localObject = localGlobalMessage;
        break;
      }
      i++;
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.GlobalMessages
 * JD-Core Version:    0.7.0.1
 */