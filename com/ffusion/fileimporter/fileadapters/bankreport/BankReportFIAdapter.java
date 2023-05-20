package com.ffusion.fileimporter.fileadapters.bankreport;

import com.ffusion.csil.CSILException;
import com.ffusion.csil.FIException;
import com.ffusion.csil.core.BankReport;
import com.ffusion.fileimporter.fileadapters.IFileAdapter;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import com.ffusion.util.filemonitor.FMLog;
import com.ffusion.util.logging.DebugLog;
import java.io.InputStream;
import java.util.HashMap;

public class BankReportFIAdapter
  implements IFileAdapter
{
  public void initialize(HashMap paramHashMap)
    throws FIException
  {}
  
  public void open(HashMap paramHashMap)
    throws FIException
  {}
  
  public void close(HashMap paramHashMap)
    throws FIException
  {}
  
  public void processFile(InputStream paramInputStream, HashMap paramHashMap)
    throws FIException
  {
    String str1 = "com.ffusion.fileimporter.fileadapters.bankreport.BankReportFIAdapter.processFile";
    jdMethod_for(paramHashMap, "In Process");
    try
    {
      BankReport.processBankReportFile(paramInputStream, paramHashMap);
    }
    catch (Exception localException)
    {
      String str2 = "Unable to upload BankReport file";
      jdMethod_for(paramHashMap, "Failed");
      if (((localException instanceof CSILException)) && (((CSILException)localException).why != null) && (((CSILException)localException).why.length() > 0)) {
        str2 = ((CSILException)localException).why;
      }
      FIException localFIException = new FIException(str1, 2, str2, localException);
      DebugLog.throwing(str1, localFIException);
      throw localFIException;
    }
    jdMethod_for(paramHashMap, "Complete");
  }
  
  private static void jdMethod_for(HashMap paramHashMap, String paramString)
  {
    String str = "BankReportFIAdapter.createFMLogRecordForBankReport";
    FMLogRecord localFMLogRecord = jdMethod_for("BankReport", paramHashMap);
    localFMLogRecord.setStatus(paramString);
    try
    {
      FMLog.log(localFMLogRecord);
    }
    catch (FMException localFMException)
    {
      DebugLog.throwing(str, localFMException);
    }
  }
  
  private static FMLogRecord jdMethod_for(String paramString, HashMap paramHashMap)
  {
    String str1 = (String)paramHashMap.get("FILE_NAME");
    String str2 = (String)paramHashMap.get("FROM_SYSTEM");
    String str3 = (String)paramHashMap.get("TO_SYSTEM");
    String str4 = (String)paramHashMap.get("HOST_NAME");
    String str5 = (String)paramHashMap.get("ACTIVITY_TYPE");
    FMLogRecord localFMLogRecord = new FMLogRecord();
    localFMLogRecord.setFileType(paramString);
    localFMLogRecord.setFileName(str1);
    localFMLogRecord.setHostName(str4);
    localFMLogRecord.setFromSystem(str2);
    localFMLogRecord.setToSystem(str3);
    if (str3 == null) {
      localFMLogRecord.setToSystem("CBS Bank Reports");
    } else {
      localFMLogRecord.setToSystem(str3);
    }
    localFMLogRecord.setActivityType(str5);
    try
    {
      ILocalizable localILocalizable = (ILocalizable)paramHashMap.get("COMMENT");
      localFMLogRecord.setLocalizableMessage(localILocalizable);
    }
    catch (ClassCastException localClassCastException)
    {
      String str6 = (String)paramHashMap.get("COMMENT");
      localFMLogRecord.setMessage(str6);
    }
    localFMLogRecord.setMillis(System.currentTimeMillis());
    return localFMLogRecord;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.bankreport.BankReportFIAdapter
 * JD-Core Version:    0.7.0.1
 */