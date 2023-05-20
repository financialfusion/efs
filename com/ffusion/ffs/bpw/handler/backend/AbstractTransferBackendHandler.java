package com.ffusion.ffs.bpw.handler.backend;

import com.ffusion.ffs.bpw.interfaces.ITransferBackendHandler;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.master.BPWExternalProcessor;
import com.ffusion.ffs.bpw.util.BPWConfigWrapper;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Properties;

public abstract class AbstractTransferBackendHandler
  implements ITransferBackendHandler
{
  private static String jdField_if = "Transfer-Handler";
  protected HashMap a = null;
  public static final String STR_TEMP_DIR_NAME = "temp";
  public static final String DEFAULT_EXPORT_DIR = "export";
  public static final String DEFAULT_ERROR_DIR = "error";
  
  public AbstractTransferBackendHandler()
  {
    BPWConfigWrapper localBPWConfigWrapper = (BPWConfigWrapper)FFSRegistry.lookup("BPWCONFIGWRAPPER");
    Properties localProperties = null;
    try
    {
      localProperties = localBPWConfigWrapper.getBackendHandlerParameters(jdField_if);
    }
    catch (FFSException localFFSException)
    {
      String str = "Error while getting Backend Handler params using config wrapper";
      FFSDebug.log(localFFSException, str);
      localProperties = null;
    }
    if (localProperties != null) {
      this.a = new HashMap(localProperties);
    } else {
      this.a = new HashMap();
    }
  }
  
  public void startProcessTransfer(EventInfo paramEventInfo, HashMap paramHashMap) {}
  
  public abstract void processTransfer(TransferInfo[] paramArrayOfTransferInfo, HashMap paramHashMap)
    throws Exception;
  
  public void endProcessTransfer(EventInfo paramEventInfo, HashMap paramHashMap) {}
  
  public void updateBPWWithResult(TransferInfo[] paramArrayOfTransferInfo)
    throws Exception
  {
    if ((paramArrayOfTransferInfo == null) || (paramArrayOfTransferInfo.length == 0)) {
      return;
    }
    BPWExternalProcessor localBPWExternalProcessor = new BPWExternalProcessor();
    localBPWExternalProcessor.processTransferBackendlRslt(paramArrayOfTransferInfo);
  }
  
  public static String getProperty(String paramString1, String paramString2)
  {
    String str = null;
    try
    {
      FFSProperties localFFSProperties = (FFSProperties)FFSRegistry.lookup("FFSPROPS");
      str = localFFSProperties.getProperty(paramString1, paramString2);
    }
    catch (Exception localException)
    {
      return null;
    }
    return str;
  }
  
  public static String getFileNameBase(String paramString)
    throws Exception
  {
    File localFile = new File(paramString);
    if (!localFile.exists())
    {
      localFile.mkdir();
    }
    else if (!localFile.isDirectory())
    {
      FFSException localFFSException = new FFSException("Error: Export Directory" + localFile.getAbsolutePath() + " is not a directory.");
      FFSDebug.log(localFFSException.getLocalizedMessage());
      throw localFFSException;
    }
    return paramString + File.separator;
  }
  
  protected void a(String paramString1, String paramString2, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("AbstractTransferBackendHandler.writeRecordContents: Writing a record to file. file name: " + paramString1, 6);
    FFSDebug.log("AbstractTransferBackendHandler.writeRecordContents: Writing a record to file. record content: " + paramString2, 6);
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString1, paramBoolean));
      localPrintWriter.write(paramString2);
      localPrintWriter.close();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "AbstractTransferBackendHandler.writeRecordContents: Can't write Transfer record to the  file! Terminating process! fullFileName: " + paramString1, 0);
      throw new FFSException("AbstractTransferBackendHandler.writeRecordContents: Can't write Transfer record to the  file! Terminating process! fullFileName: " + paramString1);
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.handler.backend.AbstractTransferBackendHandler
 * JD-Core Version:    0.7.0.1
 */