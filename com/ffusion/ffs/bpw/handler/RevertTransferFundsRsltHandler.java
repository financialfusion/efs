package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.custimpl.transfers.TransferFundsResult;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import java.util.ArrayList;
import java.util.Hashtable;

public class RevertTransferFundsRsltHandler
  extends TransferFundsBaseHandler
{
  private TransferFundsResult iG = null;
  
  public RevertTransferFundsRsltHandler()
  {
    aa();
    this.iG = new TransferFundsResult();
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    a(paramInt, paramEventInfoArray, paramFFSConnectionHolder, false, "RevertTransferFundsRsltHandler.eventHandler:");
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    a(paramInt, paramEventInfoArray, paramFFSConnectionHolder, true, "RevertTransferFundsRsltHandler.resubmitEventHandler:");
  }
  
  protected void b(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    a(paramFFSConnectionHolder, false, paramString);
  }
  
  protected void c(FFSConnectionHolder paramFFSConnectionHolder, String paramString)
    throws Exception
  {
    a(paramFFSConnectionHolder, true, paramString);
  }
  
  private void a(FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean, String paramString)
    throws Exception
  {
    String str = DBConnCache.getNewBatchKey();
    DBConnCache.bind(str, paramFFSConnectionHolder);
    ArrayList localArrayList = new ArrayList();
    localArrayList.add(this.iw);
    BPWUtil.setFIIDList(localArrayList, this.iG);
    this.iG.processRevertTransferFundsRslt(str, new Hashtable());
    DBConnCache.unbind(str);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.RevertTransferFundsRsltHandler
 * JD-Core Version:    0.7.0.1
 */