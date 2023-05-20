package com.ffusion.tasks.ach;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.ACH;
import com.ffusion.tasks.PagingBaseTask;
import com.ffusion.util.FilteredList;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public class GetApprovalACHBatches
  extends PagingBaseTask
  implements Task
{
  protected String achType = "ACHBatch";
  protected String companyID;
  
  public GetApprovalACHBatches()
  {
    this.sortedBy = "DATE";
    this.collectionName = "PendingApprovalACHBatches";
    this.viewAll = true;
  }
  
  public FilteredList getPaged(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatches localACHBatches = ACH.getPagedApprovalACHBatches(paramSecureUser, this.companyID, this.achType, paramPagingContext, paramHashMap);
    if ((localACHBatches != null) && (this.datetype != null)) {
      localACHBatches.setDateFormat(this.datetype);
    }
    return localACHBatches;
  }
  
  public FilteredList getNext(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatches localACHBatches = ACH.getNextApprovalACHBatches(paramSecureUser, this.companyID, this.achType, paramPagingContext, paramHashMap);
    if ((localACHBatches != null) && (this.datetype != null)) {
      localACHBatches.setDateFormat(this.datetype);
    }
    return localACHBatches;
  }
  
  public FilteredList getPrevious(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException
  {
    ACHBatches localACHBatches = ACH.getPreviousApprovalACHBatches(paramSecureUser, this.companyID, this.achType, paramPagingContext, paramHashMap);
    if ((localACHBatches != null) && (this.datetype != null)) {
      localACHBatches.setDateFormat(this.datetype);
    }
    return localACHBatches;
  }
  
  public final void setACHType(String paramString)
  {
    this.achType = paramString;
  }
  
  public void setReload(String paramString)
  {
    boolean bool = Boolean.valueOf(paramString).booleanValue();
    if (bool == true) {
      this.viewChanged = true;
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.ach.GetApprovalACHBatches
 * JD-Core Version:    0.7.0.1
 */