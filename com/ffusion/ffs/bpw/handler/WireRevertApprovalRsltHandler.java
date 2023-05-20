package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.custimpl.RevertWireApprovalResult;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.util.BPWUtil;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.scheduling.db.EventInfoArray;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import java.util.ArrayList;

public class WireRevertApprovalRsltHandler
  implements DBConsts, FFSConst
{
  private RevertWireApprovalResult h5 = null;
  private boolean h3;
  private boolean h4 = false;
  
  public WireRevertApprovalRsltHandler()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.h5 = new RevertWireApprovalResult();
    this.h4 = Boolean.valueOf(localPropertyConfig.otherProperties.getProperty("bpw.wire.funds.approval.support", "false")).booleanValue();
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    if (!this.h4)
    {
      FFSDebug.log("WireRevertApprovalRsltHandler.eventHandler: ", "Funds approval is not supported", 6);
      return;
    }
    FFSDebug.log("=== WireRevertApprovalRsltHandler.eventHandler: begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      this.h3 = false;
    } else if (paramInt == 1) {
      this.h3 = true;
    } else if (paramInt == 2)
    {
      if (this.h3)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(paramEventInfoArray._array[0].FIId);
        BPWUtil.setFIIDList(localArrayList, this.h5);
        String str = DBConnCache.getNewBatchKey();
        DBConnCache.bind(str, paramFFSConnectionHolder);
        this.h5.revertWireApprovalRslt(str);
        DBConnCache.unbind(str);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("=== WireRevertApprovalRsltHandler.eventHandler: end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    if (!this.h4)
    {
      FFSDebug.log("WireRevertApprovalRsltHandler.resubmitEventHandler: ", "Funds approval is not supported", 6);
      return;
    }
    FFSDebug.log("==== WireRevertApprovalRsltHandler.resubmitEventHandler: begin, eventSeq=" + paramInt, 6);
    if (paramInt == 0) {
      this.h3 = false;
    } else if (paramInt == 1) {
      this.h3 = true;
    } else if (paramInt == 2)
    {
      if (this.h3)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(paramEventInfoArray._array[0].FIId);
        BPWUtil.setFIIDList(localArrayList, this.h5);
        DBConnCache localDBConnCache = (DBConnCache)FFSRegistry.lookup("DBCONNCACHE");
        String str = DBConnCache.getNewBatchKey();
        DBConnCache.bind(str, paramFFSConnectionHolder);
        this.h5.revertWireApprovalRslt(str);
        DBConnCache.unbind(str);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("==== WireRevertApprovalRsltHandler.resubmitEventHandler: end", 6);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.WireRevertApprovalRsltHandler
 * JD-Core Version:    0.7.0.1
 */