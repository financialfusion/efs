package com.ffusion.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class TransactionTypes
  extends ArrayList
{
  public void sortByName()
  {
    Collections.sort(this, TransactionType.getDescNameComparator());
  }
  
  public TransactionType getByName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionType localTransactionType = (TransactionType)localIterator.next();
      if (paramString.equals(localTransactionType.getName()))
      {
        localObject = localTransactionType;
        break;
      }
    }
    return localObject;
  }
  
  public TransactionType getById(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionType localTransactionType = (TransactionType)localIterator.next();
      if (paramInt == localTransactionType.getId())
      {
        localObject = localTransactionType;
        break;
      }
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.TransactionTypes
 * JD-Core Version:    0.7.0.1
 */