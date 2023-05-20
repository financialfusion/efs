package com.ffusion.beans.cashcon;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class CashConAccounts
  extends FilteredList
{
  public boolean add(Object paramObject)
  {
    CashConAccount localCashConAccount = (CashConAccount)paramObject;
    return super.add(localCashConAccount);
  }
  
  public CashConAccount getByID(String paramString)
  {
    CashConAccount localCashConAccount = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        localCashConAccount = (CashConAccount)localIterator.next();
        if (paramString.equals(localCashConAccount.getBPWID())) {
          return localCashConAccount;
        }
      }
    }
    return null;
  }
  
  public CashConAccount getDefaultAccount()
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      CashConAccount localCashConAccount = (CashConAccount)localIterator.next();
      if (localCashConAccount.getDefaultAccount())
      {
        localObject = localCashConAccount;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByID(String paramString)
  {
    CashConAccount localCashConAccount = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        localCashConAccount = (CashConAccount)localIterator.next();
        if (paramString.equals(localCashConAccount.getNumber())) {
          localIterator.remove();
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.CashConAccounts
 * JD-Core Version:    0.7.0.1
 */