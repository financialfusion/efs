package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.wiretransfers.WireBatch;
import com.ffusion.beans.wiretransfers.WireHistories;
import com.ffusion.beans.wiretransfers.WireTransfer;
import com.ffusion.beans.wiretransfers.WireTransfers;
import com.ffusion.csil.CSILException;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface WireTransferService3
  extends WireTransferService2
{
  public abstract WireHistories getCompletedWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireHistories getPendingWireHistories(SecureUser paramSecureUser, PagingContext paramPagingContext, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireBatch getWireBatchById(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws CSILException;
  
  public abstract WireTransfers getFilteredWires(SecureUser paramSecureUser, WireTransfer paramWireTransfer, HashMap paramHashMap)
    throws CSILException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.WireTransferService3
 * JD-Core Version:    0.7.0.1
 */