package com.ffusion.ffs.bpw.handler.backend;

import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.TransferInfo;
import com.ffusion.ffs.bpw.master.BPWExternalProcessor;
import com.ffusion.ffs.bpw.serviceMsg.BPWMsgBroker;
import com.ffusion.ffs.bpw.util.swift.SWIFTParser;
import com.ffusion.ffs.scheduling.db.EventInfo;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.interfaces.IMBInstance;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

public class SWIFTTransferBackendHandler
  extends AbstractTransferBackendHandler
{
  private String jdField_new = null;
  private String jdField_int = null;
  private String jdField_case = null;
  private String jdField_byte = null;
  private IMBInstance jdField_else = null;
  private SWIFTParser jdField_try = null;
  private BPWFIInfo jdField_char = null;
  private String jdField_goto = "MT103";
  
  public SWIFTTransferBackendHandler()
  {
    BPWMsgBroker localBPWMsgBroker = (BPWMsgBroker)FFSRegistry.lookup("BPWMSGBROKER");
    this.jdField_else = localBPWMsgBroker.getIMBInstance();
    try
    {
      this.jdField_try = SWIFTParser.getInstance();
      this.jdField_try.initialize(this.jdField_else);
    }
    catch (Exception localException)
    {
      String str = localException.getMessage();
      FFSDebug.log(localException, str);
    }
    if (this.a.get("SWIFT_MESSAGE_TYPE") != null) {
      this.jdField_goto = ((String)this.a.get("SWIFT_MESSAGE_TYPE"));
    }
  }
  
  public void startProcessTransfer(EventInfo paramEventInfo, HashMap paramHashMap)
  {
    this.jdField_char = ((BPWFIInfo)paramHashMap.get("BPW_FI_INFO"));
  }
  
  public void processTransfer(TransferInfo[] paramArrayOfTransferInfo, HashMap paramHashMap)
    throws Exception
  {
    int i = 0;
    if ((paramArrayOfTransferInfo != null) && ((i = paramArrayOfTransferInfo.length) > 0))
    {
      for (int j = 0; j < i; j++) {
        if (paramArrayOfTransferInfo[j].getEventId().equals(String.valueOf(0)))
        {
          jdMethod_do();
          this.jdField_new = a(paramHashMap, this.jdField_case, this.jdField_byte);
        }
        else if (paramArrayOfTransferInfo[j].getEventId().equals(String.valueOf(2)))
        {
          jdMethod_if();
        }
        else
        {
          jdMethod_if(paramArrayOfTransferInfo[j]);
        }
      }
      callBPW(paramArrayOfTransferInfo);
    }
  }
  
  private void jdMethod_do()
    throws Exception
  {
    String str1 = getProperty("swift.export.dir", "export");
    this.jdField_int = getFileNameBase(str1);
    String str2 = this.jdField_int + "temp";
    this.jdField_case = getFileNameBase(str2);
    String str3 = getProperty("swift.error.dir", "error");
    this.jdField_byte = getFileNameBase(str3);
  }
  
  private String a(HashMap paramHashMap, String paramString1, String paramString2)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyMMdd.HHmm");
    String str1 = localSimpleDateFormat.format(new Date());
    String str2 = null;
    String str3 = "" + this.jdField_char.getFIId() + "." + str1;
    str2 = a(paramString1, str3);
    String str4 = str3 + "." + str2 + "." + "SWIFT";
    String str5 = paramString1 + str4;
    File localFile1 = new File(str5);
    if (localFile1.exists())
    {
      FFSDebug.log("SWIFTTransferBackendHandler.prepareExportFile, export SWIFT file exist: " + str5, 0);
      String str6 = paramString2 + str4 + "." + String.valueOf(System.currentTimeMillis());
      File localFile2 = new File(str6);
      localFile1.renameTo(localFile2);
      FFSDebug.log("SWIFTTransferBackendHandler.prepareExportFile, the existing file has been moved to  " + str6, 0);
    }
    return str5;
  }
  
  private String a(String paramString1, String paramString2)
  {
    File localFile1 = new File(paramString1);
    String[] arrayOfString1 = localFile1.list();
    File localFile2 = new File(this.jdField_int);
    String[] arrayOfString2 = localFile2.list();
    int i = 0;
    if ((arrayOfString1 != null) && (arrayOfString1.length > 0)) {
      i = arrayOfString1.length;
    }
    if ((arrayOfString2 != null) && (arrayOfString2.length > 0)) {
      i += arrayOfString2.length;
    }
    String[] arrayOfString3 = new String[i];
    if ((arrayOfString1 != null) && (arrayOfString1.length > 0)) {
      System.arraycopy(arrayOfString1, 0, arrayOfString3, 0, arrayOfString1.length);
    }
    if ((arrayOfString2 != null) && (arrayOfString2.length > 0)) {
      System.arraycopy(arrayOfString2, 0, arrayOfString3, arrayOfString1.length, arrayOfString2.length);
    }
    DecimalFormat localDecimalFormat = new DecimalFormat("000000");
    int j = 1;
    String str = localDecimalFormat.format(j);
    for (int k = 0; k < i; k++) {
      if ((arrayOfString3[k].startsWith(paramString2 + "." + str)) && (arrayOfString3[k].endsWith(".SWIFT"))) {
        str = localDecimalFormat.format(++j);
      }
    }
    str = localDecimalFormat.format(j);
    return str;
  }
  
  private void jdMethod_if()
  {
    String str1 = "SWIFTTransferBackendHandler.moveToExport: ";
    File localFile1 = new File(this.jdField_new);
    String str2 = localFile1.getName();
    String str3 = this.jdField_int + str2;
    File localFile2 = new File(str3);
    if (localFile2.exists())
    {
      FFSDebug.log(str1 + " export swift file exist: " + str3, 0);
      String str4 = this.jdField_byte + str2 + ".SWIFT" + "." + String.valueOf(System.currentTimeMillis());
      File localFile3 = new File(str4);
      localFile2.renameTo(localFile3);
      FFSDebug.log(str1 + " the existing file has been moved to  " + str4, 0);
    }
    localFile1.renameTo(localFile2);
  }
  
  private void jdMethod_if(TransferInfo paramTransferInfo)
  {
    String str1 = "com.ffusion.ffs.bpw.handler.backend.SWIFTTransferBackendHandler.processOneTransfer";
    paramTransferInfo.setPrcStatus("POSTEDON");
    paramTransferInfo.setDatePosted(paramTransferInfo.getDateToPost());
    TransferInfo[] arrayOfTransferInfo = new TransferInfo[1];
    arrayOfTransferInfo[0] = paramTransferInfo;
    try
    {
      String str2 = this.jdField_try.build(arrayOfTransferInfo, this.jdField_goto);
      a(this.jdField_new, str2, true);
    }
    catch (Exception localException)
    {
      String str3 = str1 + " : Exception while building message String from TransferInfo object: " + paramTransferInfo + "and writing them to file.";
      FFSDebug.log(localException, str3);
      paramTransferInfo.setPrcStatus("BACKENDFAILED");
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
 * Qualified Name:     com.ffusion.ffs.bpw.handler.backend.SWIFTTransferBackendHandler
 * JD-Core Version:    0.7.0.1
 */