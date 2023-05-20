package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CashCon;
import com.ffusion.tasks.PagingBaseTask;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public class GetPendingCashCons
  extends PagingBaseTask
  implements Task
{
  public GetPendingCashCons()
  {
    this.sortedBy = "SUBMITDATE";
    this.collectionName = "PendingCashCons";
  }
  
  public FilteredList getPaged(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    CashCons localCashCons = CashCon.getPagedPendingCashCons(paramSecureUser, paramPagingContext, paramHashMap);
    if ((localCashCons != null) && (this.datetype != null)) {
      localCashCons.setDateFormat(this.datetype);
    }
    return localCashCons;
  }
  
  public FilteredList getNext(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    CashCons localCashCons = CashCon.getNextPendingCashCons(paramSecureUser, paramPagingContext, paramHashMap);
    if ((localCashCons != null) && (this.datetype != null)) {
      localCashCons.setDateFormat(this.datetype);
    }
    return localCashCons;
  }
  
  public FilteredList getPrevious(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    CashCons localCashCons = CashCon.getPreviousPendingCashCons(paramSecureUser, paramPagingContext, paramHashMap);
    if ((localCashCons != null) && (this.datetype != null)) {
      localCashCons.setDateFormat(this.datetype);
    }
    return localCashCons;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.GetPendingCashCons
 * JD-Core Version:    0.7.0.1
 */