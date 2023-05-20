package com.ffusion.beans.business;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class PendingTransactions
  extends FilteredList
{
  public PendingTransaction getPendingTransactionByItemID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      PendingTransaction localPendingTransaction = (PendingTransaction)localIterator.next();
      if (localPendingTransaction.getItemID().equals(paramString))
      {
        localObject = localPendingTransaction;
        break;
      }
    }
    return localObject;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.PendingTransactions
 * JD-Core Version:    0.7.0.1
 */