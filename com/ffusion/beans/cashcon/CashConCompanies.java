package com.ffusion.beans.cashcon;

import com.ffusion.util.FilteredList;
import java.util.Iterator;

public class CashConCompanies
  extends FilteredList
{
  public boolean add(Object paramObject)
  {
    CashConCompany localCashConCompany = (CashConCompany)paramObject;
    return super.add(localCashConCompany);
  }
  
  public CashConCompany getByID(String paramString)
  {
    CashConCompany localCashConCompany = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        localCashConCompany = (CashConCompany)localIterator.next();
        if (paramString.equals(localCashConCompany.getBPWID())) {
          return localCashConCompany;
        }
      }
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.CashConCompanies
 * JD-Core Version:    0.7.0.1
 */