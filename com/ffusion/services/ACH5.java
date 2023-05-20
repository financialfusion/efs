package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.ach.ACHBatches;
import com.ffusion.csil.CSILException;
import com.ffusion.util.beans.PagingContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public abstract interface ACH5
  extends ACH4
{
  public abstract HashSet getEnabledSECCodes(SecureUser paramSecureUser, String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException;
  
  public abstract void setEnabledSECCodes(SecureUser paramSecureUser, String paramString, int paramInt, HashSet paramHashSet, HashMap paramHashMap)
    throws CSILException;
  
  public abstract String getDefaultEffectiveDate(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ArrayList getDefaultACHFrequencies(SecureUser paramSecureUser)
    throws CSILException;
  
  public abstract ACHBatches getPagedApprovalACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getNextApprovalACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getPreviousApprovalACHBatches(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract ACHBatches getPagedACHBatchHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.ACH5
 * JD-Core Version:    0.7.0.1
 */