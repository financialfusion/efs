package com.ffusion.services;

import com.ffusion.beans.lockbox.LockboxAccount;
import com.ffusion.beans.lockbox.LockboxCreditItems;
import com.ffusion.beans.lockbox.LockboxTransactions;
import com.ffusion.services.lockbox.LBoxException;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface Lockbox3
  extends Lockbox2
{
  public abstract LockboxTransactions getPagedTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxTransactions getNextTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxTransactions getPreviousTransactions(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxCreditItems getPagedCreditItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxCreditItems getNextCreditItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException;
  
  public abstract LockboxCreditItems getPreviousCreditItems(LockboxAccount paramLockboxAccount, PagingContext paramPagingContext, HashMap paramHashMap)
    throws LBoxException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.Lockbox3
 * JD-Core Version:    0.7.0.1
 */