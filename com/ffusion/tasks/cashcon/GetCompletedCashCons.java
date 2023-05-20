package com.ffusion.tasks.cashcon;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.cashcon.CashCons;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.CashCon;
import com.ffusion.tasks.PagingBaseTask;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public class GetCompletedCashCons
  extends PagingBaseTask
  implements Task
{
  public GetCompletedCashCons()
  {
    this.sortedBy = "SUBMITDATE,REVERSE";
    this.collectionName = "CompletedCashCons";
  }
  
  public FilteredList getPaged(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    CashCons localCashCons = CashCon.getPagedCompletedCashCons(paramSecureUser, paramPagingContext, paramHashMap);
    if ((localCashCons != null) && (this.datetype != null)) {
      localCashCons.setDateFormat(this.datetype);
    }
    return localCashCons;
  }
  
  public FilteredList getNext(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    CashCons localCashCons = CashCon.getNextCompletedCashCons(paramSecureUser, paramPagingContext, paramHashMap);
    if ((localCashCons != null) && (this.datetype != null)) {
      localCashCons.setDateFormat(this.datetype);
    }
    return localCashCons;
  }
  
  public FilteredList getPrevious(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    CashCons localCashCons = CashCon.getPreviousCompletedCashCons(paramSecureUser, paramPagingContext, paramHashMap);
    if ((localCashCons != null) && (this.datetype != null)) {
      localCashCons.setDateFormat(this.datetype);
    }
    return localCashCons;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.cashcon.GetCompletedCashCons
 * JD-Core Version:    0.7.0.1
 */