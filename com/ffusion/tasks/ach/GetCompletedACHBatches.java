package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatch;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.PagingBaseTask;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;
import java.util.Iterator;
import javax.servlet.http.HttpSession;

public class GetCompletedACHBatches
  extends PagingBaseTask
  implements Task
{
  protected String achType = "ACHBatch";
  protected String companyID;
  
  public GetCompletedACHBatches()
  {
    this.sortedBy = "DATE,REVERSE";
    this.collectionName = "CompletedACHBatches";
    this.viewAll = true;
  }
  
  public FilteredList getPaged(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatches localACHBatches = ACH.getPagedCompletedACHBatches(paramSecureUser, this.companyID, this.achType, paramPagingContext, paramHashMap);
    if ((localACHBatches != null) && (this.datetype != null)) {
      localACHBatches.setDateFormat(this.datetype);
    }
    return localACHBatches;
  }
  
  public FilteredList getNext(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatches localACHBatches = ACH.getNextCompletedACHBatches(paramSecureUser, this.companyID, this.achType, paramPagingContext, paramHashMap);
    if ((localACHBatches != null) && (this.datetype != null)) {
      localACHBatches.setDateFormat(this.datetype);
    }
    return localACHBatches;
  }
  
  public FilteredList getPrevious(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatches localACHBatches = ACH.getPreviousCompletedACHBatches(paramSecureUser, this.companyID, this.achType, paramPagingContext, paramHashMap);
    if ((localACHBatches != null) && (this.datetype != null)) {
      localACHBatches.setDateFormat(this.datetype);
    }
    return localACHBatches;
  }
  
  protected void postProcessList(FilteredList paramFilteredList, HttpSession paramHttpSession)
  {
    if (paramFilteredList == null) {
      return;
    }
    ACHBatches localACHBatches = (ACHBatches)paramFilteredList;
    setRecurringInfo(localACHBatches, paramHttpSession);
  }
  
  protected void setRecurringInfo(ACHBatches paramACHBatches, HttpSession paramHttpSession)
  {
    if (paramACHBatches == null) {
      return;
    }
    Iterator localIterator = paramACHBatches.iterator();
    ACHBatches localACHBatches = (ACHBatches)paramHttpSession.getAttribute("RecACHBatches");
    ACHBatch localACHBatch1 = null;
    ACHBatch localACHBatch2 = null;
    while ((localIterator.hasNext()) && (localACHBatches != null))
    {
      localACHBatch1 = (ACHBatch)localIterator.next();
      if (localACHBatch1.getRecID() != null)
      {
        localACHBatch2 = localACHBatches.getByID(localACHBatch1.getRecID());
        localACHBatch1.set("Frequency", localACHBatch2.getFrequency());
        localACHBatch1.set("RemainingTransfers", localACHBatch2.getRemainingPayments());
      }
      else
      {
        localACHBatch1.set("Frequency", "");
        localACHBatch1.set("RemainingTransfers", "");
      }
    }
  }
  
  public String getCompanyID()
  {
    return this.companyID;
  }
  
  public void setCompanyID(String paramString)
  {
    if ((paramString != null) && (!paramString.equals(this.companyID))) {
      this.viewChanged = true;
    }
    this.companyID = paramString;
  }
  
  public final void setACHType(String paramString)
  {
    this.achType = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetCompletedACHBatches
 * JD-Core Version:    0.7.0.1
 */