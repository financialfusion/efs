package com.ffusion.fileimporter.fileadapters;

import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ImportError;
import com.ffusion.csil.FIException;
import com.ffusion.msgbroker.factory.MBInstanceFactory;
import com.ffusion.msgbroker.interfaces.IMBInstance;
import com.ffusion.msgbroker.interfaces.IMBMessage;
import com.ffusion.msgbroker.interfaces.IParser;
import com.ffusion.msgbroker.interfaces.MBException;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import com.ffusion.util.filemonitor.FMLog;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class PPayCheckRecordFileAdapter
  implements IFileAdapter
{
  private String aRy = null;
  private String aRw = null;
  private String aRu = null;
  private boolean aRr;
  private String aRq = null;
  private String aRz = null;
  private String aRn = null;
  private String aRp = null;
  private String aRt = null;
  private String aRs = null;
  private String aRv = null;
  private String aRo = null;
  private IMBInstance aRx = null;
  
  public synchronized void initialize(HashMap paramHashMap)
    throws FIException
  {
    initializeMessageBroker(paramHashMap);
  }
  
  public void open(HashMap paramHashMap)
    throws FIException
  {}
  
  public void close(HashMap paramHashMap)
    throws FIException
  {}
  
  public void processFile(InputStream paramInputStream, HashMap paramHashMap)
    throws FIException
  {
    String str1 = "PPayCheckRecordFileAdapter.processFile";
    String str2 = readFile("processFile", paramInputStream);
    ErrorMessages localErrorMessages = new ErrorMessages();
    paramHashMap.put("ImportErrors", localErrorMessages);
    FMLogRecord localFMLogRecord = jdMethod_new("Positive Pay Check Record", paramHashMap);
    localFMLogRecord.setStatus("In Process");
    try
    {
      FMLog.log(localFMLogRecord);
    }
    catch (FMException localFMException1)
    {
      DebugLog.throwing(str1, localFMException1);
    }
    parseFileUsingMessageBroker(str2, localErrorMessages);
    localFMLogRecord = jdMethod_new("Positive Pay Check Record", paramHashMap);
    String str3 = localErrorMessages.isEmpty() ? "Complete" : "Failed";
    localFMLogRecord.setStatus(str3);
    try
    {
      FMLog.log(localFMLogRecord);
    }
    catch (FMException localFMException2)
    {
      DebugLog.throwing(str1, localFMException2);
    }
    if (localErrorMessages.size() != 0) {
      throw new FIException("CustomPPayCheckRecord.process", 9604);
    }
  }
  
  protected void initializeMessageBroker(HashMap paramHashMap)
    throws FIException
  {
    String str1 = "PPayCheckRecordFileAdapter : initialize";
    HashMap localHashMap = null;
    try
    {
      localHashMap = (HashMap)paramHashMap.get("MESSAGEBROKER");
    }
    catch (Exception localException)
    {
      FIException localFIException1 = new FIException(str1, 9610, "The adaptersettings hashmap is null", localException);
      DebugLog.throwing("The adaptersettings hashmap is null", localFIException1);
      throw localFIException1;
    }
    if (localHashMap == null)
    {
      DebugLog.log("MB Settings HashMaps cannot be null. ");
      throw new FIException(str1, 9610, "MB Settings HashMaps cannot be null");
    }
    int i = 0;
    this.aRp = ((String)localHashMap.get("USERID"));
    this.aRt = ((String)localHashMap.get("PASSWORD"));
    this.aRu = ((String)localHashMap.get("DBTYPE"));
    this.aRy = ((String)localHashMap.get("MESSAGESET"));
    this.aRw = ((String)localHashMap.get("MESSAGENAME"));
    if ((this.aRp == null) || (this.aRt == null) || (this.aRu == null) || (this.aRy == null) || (this.aRw == null)) {
      i = 1;
    }
    int j = 0;
    String str2 = null;
    if (localHashMap.get("CTXFACTORY") != null)
    {
      j = 1;
      this.aRs = ((String)localHashMap.get("CTXFACTORY"));
      this.aRv = ((String)localHashMap.get("JNDIURL"));
      this.aRo = ((String)localHashMap.get("JNDICTX"));
      if ((this.aRv == null) || (this.aRo == null)) {
        i = 1;
      }
    }
    else
    {
      this.aRn = ((String)localHashMap.get("DBNAME"));
      if (this.aRn == null) {
        i = 1;
      }
      if ((this.aRu != null) && (!this.aRu.equals("DB2:AS390")))
      {
        str2 = (String)localHashMap.get("USETHINDRIVER");
        this.aRq = ((String)localHashMap.get("MACHINE"));
        this.aRz = ((String)localHashMap.get("PORTNUM"));
        if ((str2 == null) || (this.aRq == null) || (this.aRz == null)) {
          i = 1;
        }
      }
    }
    if (i != 0)
    {
      DebugLog.log("All connection fields required to connect to the database being used must be non-null");
      throw new FIException(str1, 9610, "All necessary MB connection params are required");
    }
    if ((str2 != null) && (str2.equalsIgnoreCase("true"))) {
      this.aRr = true;
    } else {
      this.aRr = false;
    }
    FIException localFIException2;
    try
    {
      if ((this.aRu.equalsIgnoreCase("ASA")) || (this.aRu.equalsIgnoreCase("ASE"))) {
        if (j != 0) {
          this.aRx = MBInstanceFactory.createHAInstanceFromJConnect(this.aRp, this.aRt, this.aRs, this.aRv, this.aRo);
        } else {
          this.aRx = MBInstanceFactory.createInstanceFromJConnect(this.aRp, this.aRt, this.aRq, this.aRz, this.aRn);
        }
      }
      if ((this.aRu.equalsIgnoreCase("ORACLE:OCI8")) || (this.aRu.equalsIgnoreCase("ORACLE:THIN"))) {
        this.aRx = MBInstanceFactory.createInstanceFromOracle(this.aRp, this.aRt, this.aRq, this.aRz, this.aRn, this.aRr);
      }
      if (this.aRu.equalsIgnoreCase("DB2:NET")) {
        this.aRx = MBInstanceFactory.createInstanceFromDB2(this.aRp, this.aRt, this.aRq, this.aRz, this.aRn, false);
      }
      if (this.aRu.equalsIgnoreCase("DB2:APP")) {
        this.aRx = MBInstanceFactory.createInstanceFromDB2(this.aRp, this.aRt, this.aRq, this.aRz, this.aRn, true);
      }
      if (this.aRu.equalsIgnoreCase("DB2:UN2")) {
        this.aRx = MBInstanceFactory.createInstanceFromDB2UniversalDriver(this.aRp, this.aRt, this.aRn);
      }
      if (this.aRu.equalsIgnoreCase("DB2:AS390")) {
        this.aRx = MBInstanceFactory.createInstanceFromDB2390(this.aRp, this.aRt, this.aRn);
      }
      if (this.aRu.equalsIgnoreCase("MSSQL:THIN")) {
        this.aRx = MBInstanceFactory.createInstanceFromMSSQL(this.aRp, this.aRt, this.aRq, this.aRz, this.aRn);
      }
    }
    catch (MBException localMBException1)
    {
      localFIException2 = new FIException(9600, localMBException1);
      DebugLog.throwing("Could not connect to DB", localFIException2);
      throw localFIException2;
    }
    if (this.aRx == null)
    {
      DebugLog.log("The MBInstanceFactory returned a null instance,or failed due to an unsupported db type");
      throw new FIException(str1, 9601, "MBInstanceFactory returned a null instance,or failed due to an unsupported db type");
    }
    try
    {
      this.aRx.loadMessageSet(this.aRy);
    }
    catch (MBException localMBException2)
    {
      localFIException2 = new FIException(str1, 9605, "Could not load MB message set", localMBException2);
      DebugLog.throwing("Could not load the MB message set", localFIException2);
      throw localFIException2;
    }
  }
  
  protected String readFile(String paramString, InputStream paramInputStream)
    throws FIException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    StringBuffer localStringBuffer = new StringBuffer();
    try
    {
      for (String str = localBufferedReader.readLine(); str != null; str = localBufferedReader.readLine())
      {
        localStringBuffer.append(str);
        localStringBuffer.append("\r\n");
      }
    }
    catch (IOException localIOException)
    {
      FIException localFIException = new FIException(paramString, 9608, "InputStream readLine failed.", localIOException);
      DebugLog.throwing("InputStream readLine failed", localFIException);
      throw localFIException;
    }
    return localStringBuffer.toString();
  }
  
  protected void parseFileUsingMessageBroker(String paramString, ErrorMessages paramErrorMessages)
    throws FIException
  {
    String str = "PPayCheckRecordFileAdapter : processFile";
    if (this.aRx == null)
    {
      DebugLog.log("MB Instance is null. Cannot proceed with parse.");
      throw new FIException(str, 9603, "MB Instance is null, cannot proceed.");
    }
    IParser localIParser = null;
    try
    {
      localIParser = this.aRx.createParser();
    }
    catch (MBException localMBException1)
    {
      FIException localFIException = new FIException(9603, localMBException1);
      DebugLog.throwing("Unable to create a MB check recordfile parser", localFIException);
      throw localFIException;
    }
    if (localIParser == null)
    {
      DebugLog.log("MB parser used to parse checkRecord file is null");
      throw new FIException(str, 9603, "createParser returned null");
    }
    IMBMessage localIMBMessage = null;
    try
    {
      localIMBMessage = localIParser.parseToIDL(paramString, this.aRy, this.aRw);
      if (localIMBMessage == null)
      {
        DebugLog.log("MB parseToIDL returned null");
        throw new FIException(str, 9604, "MB parseToIDL returned null");
      }
    }
    catch (MBException localMBException2)
    {
      paramErrorMessages.add(new ImportError(3, "Parsing Errors", localMBException2.getMessage(), null, null, null));
    }
  }
  
  private static FMLogRecord jdMethod_new(String paramString, HashMap paramHashMap)
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
    if (str3 == null) {
      localFMLogRecord.setToSystem("CBS Positive Pay");
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
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.PPayCheckRecordFileAdapter
 * JD-Core Version:    0.7.0.1
 */