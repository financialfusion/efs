package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.custimpl.transfers.TransferFundsResult;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import java.util.ArrayList;
import java.util.Hashtable;

public class TransferFundsProcessingRsltHandler
  extends TransferFundsBaseHandler
{
  private TransferFundsResult iH = null;
  
  public TransferFundsProcessingRsltHandler()
  {
    aa();
    this.iH = new TransferFundsResult();
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    a(paramInt, paramEventInfoArray, paramFFSConnectionHolder, false, "TransferFundsProcessingRsltHandler.eventHandler:");
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    a(paramInt, paramEventInfoArray, paramFFSConnectionHolder, true, "TransferFundsProcessingRsltHandler.resubmitEventHandler:");
  }
  
  protected void b(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    jdMethod_if(paramFFSConnectionHolder, false, paramString);
  }
  
  protected void c(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    jdMethod_if(paramFFSConnectionHolder, true, paramString);
  }
  
  private void jdMethod_if(FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean, String paramString)
    throws Exception
  {
    String str = DBConnCache.getNewBatchKey();
    DBConnCache.bind(str, paramFFSConnectionHolder);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(this.iw);
    BPWUtil.setFIIDList(localArrayList, this.iH);
    this.iH.processTransferFundsRslt(str, new Hashtable());
    DBConnCache.unbind(str);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.TransferFundsProcessingRsltHandler
 * JD-Core Version:    0.7.0.1
 */