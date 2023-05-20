package com.ffusion.ffs.bpw.handler;

import com.ffusion.ffs.bpw.custimpl.WireApprovalResult;
import com.ffusion.ffs.bpw.db.DBConnCache;
import com.ffusion.ffs.bpw.interfaces.BPWResource;
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
import java.util.Hashtable;

public class WireApprovalRsltHandler
  implements DBConsts, FFSConst, BPWResource
{
  private WireApprovalResult jz = null;
  private boolean jw;
  private ArrayList jx = null;
  private boolean jy = false;
  
  public WireApprovalRsltHandler()
  {
    PropertyConfig localPropertyConfig = (PropertyConfig)FFSRegistry.lookup("PROPERTYCONFIG");
    this.jz = new WireApprovalResult();
    this.jy = Boolean.valueOf(localPropertyConfig.otherProperties.getProperty("bpw.wire.funds.approval.support", "false")).booleanValue();
  }
  
  public void eventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    if (!this.jy)
    {
      FFSDebug.log("WireApprovalRsltHandler.eventHandler: ", "Funds approval is not supported", 6);
      return;
    }
    FFSDebug.log("WireApprovalRsltHandler.eventHandler: ", "begin, eventSeq: " + paramInt, 6);
    boolean bool = false;
    processEvents(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool);
    FFSDebug.log("WireApprovalRsltHandler.eventHandler: ", "end", 6);
  }
  
  public void resubmitEventHandler(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    FFSDebug.log("WireApprovalRsltHandler.resubmitEventHandler: ", "begin, eventSeq: " + paramInt, 6);
    boolean bool = true;
    processEvents(paramInt, paramEventInfoArray, paramFFSConnectionHolder, bool);
    FFSDebug.log("WireApprovalRsltHandler.resubmitEventHandler: ", "end", 6);
  }
  
  public void processEvents(int paramInt, EventInfoArray paramEventInfoArray, FFSConnectionHolder paramFFSConnectionHolder, boolean paramBoolean)
    throws Exception
  {
    if (!this.jy)
    {
      FFSDebug.log("WireApprovalRsltHandler.processEvents: ", "Funds approval is not supported", 6);
      return;
    }
    FFSDebug.log("WireApprovalRsltHandler.processEvents: ", "begin, eventSeq: " + paramInt, 6);
    if (paramInt == 0) {
      this.jw = false;
    } else if (paramInt == 1) {
      this.jw = true;
    } else if (paramInt == 2)
    {
      if (this.jw)
      {
        ArrayList localArrayList = new ArrayList();
        localArrayList.add(paramEventInfoArray._array[0].FIId);
        BPWUtil.setFIIDList(localArrayList, this.jz);
        d(paramFFSConnectionHolder);
      }
    }
    else if ((paramInt == 3) || (paramInt != 4)) {}
    FFSDebug.log("WireApprovalRsltHandler.processEvents: ", "end", 6);
  }
  
  private void d(FFSConnectionHolder paramFFSConnectionHolder)
    throws Exception
  {
    String str = DBConnCache.getNewBatchKey();
    DBConnCache.bind(str, paramFFSConnectionHolder);
    Hashtable localHashtable = new Hashtable();
    this.jz.processWireApprovalRslt(str, localHashtable);
    DBConnCache.unbind(str);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.WireApprovalRsltHandler
 * JD-Core Version:    0.7.0.1
 */