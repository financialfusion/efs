package com.ffusion.ffs.bpw.handler.backend;

import com.ffusion.ffs.bpw.custimpl.transfers.TransferACHAdapter;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.DBConsts;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.master.BPWExternalProcessor;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.util.FFSDebug;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Hashtable;

public class ACHTransferBackendHandler
  extends AbstractTransferBackendHandler
{
  TransferACHAdapter jdField_for = null;
  PrintWriter jdField_do = null;
  
  public void startProcessTransfer(EventInfo paramEventInfo, HashMap paramHashMap)
  {
    String str1 = "com.ffusion.ffs.bpw.handler.backend.ACHTransferBackendHandler.startProcessTransfer";
    this.jdField_for = new TransferACHAdapter();
    String str2 = (String)paramHashMap.get("PROCESS_ID");
    boolean bool1 = paramEventInfo.createEmptyFile;
    BPWFIInfo localBPWFIInfo = (BPWFIInfo)paramHashMap.get("BPW_FI_INFO");
    ACHFIInfo localACHFIInfo = (ACHFIInfo)paramHashMap.get("ACH_FI_INFO");
    boolean bool2 = ((Boolean)paramHashMap.get("DO_AUDIT_LOG")).booleanValue();
    boolean bool3 = ((Boolean)paramHashMap.get("RERUN_CUTOFF")).booleanValue();
    try
    {
      this.jdField_for.startProcessTransfer(localBPWFIInfo, localACHFIInfo, bool1, bool3, bool2, str2);
    }
    catch (Exception localException)
    {
      String str3 = str1 + ": Exception thrown by TransferACHAdapter.startProcessTransfer:";
      FFSDebug.log(localException, str3);
    }
  }
  
  public void processTransfer(TransferInfo[] paramArrayOfTransferInfo, HashMap paramHashMap)
    throws Exception
  {
    String str1 = "com.ffusion.ffs.bpw.handler.backend.ACHTransferBackendHandler.processTransfer";
    try
    {
      this.jdField_for.processTransfer(paramArrayOfTransferInfo, new Hashtable(paramHashMap));
      a(paramArrayOfTransferInfo);
    }
    catch (Exception localException)
    {
      String str2 = str1 + " : Exception while building message String from TransferInfo objects: " + paramArrayOfTransferInfo;
      FFSDebug.log(localException, str2);
      throw localException;
    }
  }
  
  private void a(TransferInfo[] paramArrayOfTransferInfo)
    throws Exception
  {
    if ((paramArrayOfTransferInfo == null) || (paramArrayOfTransferInfo.length == 0)) {
      return;
    }
    int i = paramArrayOfTransferInfo.length;
    for (int j = 0; j < i; j++) {
      if (paramArrayOfTransferInfo[j].getEventId().equals(String.valueOf(0))) {
        a(paramArrayOfTransferInfo[j].getFIId());
      } else if (paramArrayOfTransferInfo[j].getEventId().equals(String.valueOf(2))) {
        a();
      } else {
        a(paramArrayOfTransferInfo[j]);
      }
    }
  }
  
  private void a(String paramString)
  {
    String str1 = "ETFTRN_" + paramString + ".dat";
    String str2 = "temp" + File.separator + str1;
    boolean bool = false;
    try
    {
      File localFile = new File(str2);
      if (localFile.exists()) {
        bool = true;
      }
      this.jdField_do = new PrintWriter(new FileWriter(str2, bool));
    }
    catch (Throwable localThrowable)
    {
      FFSDebug.log("TransferBackendHandler.loadFile: Failed:", FFSDebug.stackTrace(localThrowable), 6);
      return;
    }
    FFSDebug.log("Successfully created the data file =" + str2, 6);
  }
  
  private void a(TransferInfo paramTransferInfo)
  {
    if (this.jdField_do == null) {
      return;
    }
    String str1 = ";";
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(DBConsts.LINE_SEPARATOR);
    localStringBuffer.append(paramTransferInfo.getSrvrTId());
    localStringBuffer.append(str1);
    localStringBuffer.append(paramTransferInfo.getPrcStatus());
    localStringBuffer.append(str1);
    localStringBuffer.append(paramTransferInfo.getDatePosted());
    localStringBuffer.append(str1);
    localStringBuffer.append(paramTransferInfo.getConfirmNum());
    localStringBuffer.append(str1);
    String str2 = paramTransferInfo.getConfirmMsg();
    if (str2 != null)
    {
      int i = str2.indexOf(DBConsts.LINE_SEPARATOR);
      if (i != -1) {
        localStringBuffer.append(str2.substring(0, i));
      } else {
        localStringBuffer.append(paramTransferInfo.getConfirmMsg());
      }
    }
    else
    {
      localStringBuffer.append("");
    }
    this.jdField_do.write(localStringBuffer.toString());
  }
  
  private void a()
  {
    if (this.jdField_do != null) {
      this.jdField_do.close();
    }
  }
  
  public void callBPW(TransferInfo[] paramArrayOfTransferInfo)
    throws Exception
  {
    if ((paramArrayOfTransferInfo == null) || (paramArrayOfTransferInfo.length == 0)) {
      return;
    }
    BPWExternalProcessor localBPWExternalProcessor = new BPWExternalProcessor();
    localBPWExternalProcessor.processTransferBackendlRslt(paramArrayOfTransferInfo);
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.backend.ACHTransferBackendHandler
 * JD-Core Version:    0.7.0.1
 */