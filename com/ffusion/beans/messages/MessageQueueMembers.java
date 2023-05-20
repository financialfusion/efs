package com.ffusion.beans.messages;

import com.ffusion.beans.IdCollection;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class MessageQueueMembers
  extends FilteredList
  implements IdCollection
{
  public boolean add(Object paramObject)
  {
    boolean bool = false;
    if ((paramObject instanceof MessageQueueMember))
    {
      bool = super.add(paramObject);
    }
    else if ((paramObject instanceof BankEmployee))
    {
      MessageQueueMember localMessageQueueMember = new MessageQueueMember((BankEmployee)paramObject);
      bool = super.add(localMessageQueueMember);
    }
    return bool;
  }
  
  public BankEmployee create()
  {
    MessageQueueMember localMessageQueueMember1 = new MessageQueueMember();
    MessageQueueMember localMessageQueueMember2 = localMessageQueueMember1;
    add(localMessageQueueMember2);
    return localMessageQueueMember2;
  }
  
  public BankEmployee createNoAdd()
  {
    MessageQueueMember localMessageQueueMember1 = new MessageQueueMember();
    MessageQueueMember localMessageQueueMember2 = localMessageQueueMember1;
    return localMessageQueueMember2;
  }
  
  public Object getElementByID(String paramString)
  {
    return getByID(paramString);
  }
  
  public void removeByElementID(String paramString)
  {
    removeByID(paramString);
  }
  
  public BankEmployee getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BankEmployee localBankEmployee = (BankEmployee)localIterator.next();
      if (localBankEmployee.getId().equals(paramString))
      {
        localObject = localBankEmployee;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByID(String paramString)
  {
    remove(getByID(paramString));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.messages.MessageQueueMembers
 * JD-Core Version:    0.7.0.1
 */