package com.ffusion.ffs.bpw.handler.backend;

import com.ffusion.ffs.bpw.custimpl.transfers.TransferBackendHandler;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.util.FFSDebug;
import java.util.HashMap;
import java.util.Hashtable;

public class CompatibleACHTransferBackendHandler
  extends AbstractTransferBackendHandler
{
  TransferBackendHandler jdField_long = null;
  
  public void processTransfer(TransferInfo[] paramArrayOfTransferInfo, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "com.ffusion.ffs.bpw.handler.backend.CompatibleACHTransferBackendHandler.processTransfer";
    try
    {
      this.jdField_long.processTransfer(paramArrayOfTransferInfo, new Hashtable(paramHashMap));
    }
    catch (Exception localException)
    {
      String str2 = str1 + " : Exception while building message String from TransferInfo objects: " + paramArrayOfTransferInfo;
      FFSDebug.log(localException, str2);
      throw localException;
    }
  }
  
  public void startProcessTransfer(EventInfo paramEventInfo, HashMap paramHashMap)
  {
    String str1 = "CompatibleACHTransferBackendHandler.startProcessTransfer";
    String str2 = (String)paramHashMap.get("PROCESS_ID");
    boolean bool1 = paramEventInfo.createEmptyFile;
    BPWFIInfo localBPWFIInfo = (BPWFIInfo)paramHashMap.get("BPW_FI_INFO");
    ACHFIInfo localACHFIInfo = (ACHFIInfo)paramHashMap.get("ACH_FI_INFO");
    int i = paramEventInfo.fileBasedRecovery;
    boolean bool2 = ((Boolean)paramHashMap.get("DO_AUDIT_LOG")).booleanValue();
    boolean bool3 = ((Boolean)paramHashMap.get("RERUN_CUTOFF")).booleanValue();
    try
    {
      this.jdField_long.startProcessTransfer(localBPWFIInfo, localACHFIInfo, bool1, bool3, bool2, str2);
    }
    catch (Exception localException)
    {
      String str3 = str1 + ": Exception thrown by " + this.jdField_long.getClass().getName() + ".startProcessTransfer:";
      FFSDebug.log(localException, str3);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.backend.CompatibleACHTransferBackendHandler
 * JD-Core Version:    0.7.0.1
 */