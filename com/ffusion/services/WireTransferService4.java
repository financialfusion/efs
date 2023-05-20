package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireHistories;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface WireTransferService4
  extends WireTransferService3
{
  public abstract WireTransfers getAllWireTemplates(SecureUser paramSecureUser, int paramInt, HashMap paramHashMap)
    throws CSILException;
  
  public abstract HashMap getWiresConfiguration(SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireHistories getApprovalWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract boolean isBatchDeletable(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireHistories getPagedWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfers getPagedWireTemplates(SecureUser paramSecureUser, PagingContext paramPagingContext, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfers getDuplicateWires(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.WireTransferService4
 * JD-Core Version:    0.7.0.1
 */