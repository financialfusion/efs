package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.csil.CSILException;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface CashCon1
  extends CashCon
{
  public abstract CashCons getPagedCashConHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.CashCon1
 * JD-Core Version:    0.7.0.1
 */