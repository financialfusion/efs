package com.ffusion.ffs.bpw.util;

import com.ffusion.ffs.bpw.achagent.ACHAgent;
import com.ffusion.ffs.bpw.achagent.ACHRecordRegulator;
import com.ffusion.ffs.bpw.audit.FMLogAgent;
import com.ffusion.ffs.bpw.interfaces.ACHCompanyInfo;
import com.ffusion.ffs.bpw.interfaces.ACHFIInfo;
import com.ffusion.ffs.bpw.interfaces.ACHRecordInfo;
import com.ffusion.ffs.db.FFSConnection;
import com.ffusion.ffs.db.FFSConnectionHolder;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.ACHBatchCache;
import com.ffusion.ffs.util.ACHFileCache;
import com.ffusion.ffs.util.FFSDebug;
import com.ffusion.ffs.util.FFSProperties;
import com.ffusion.ffs.util.FFSRegistry;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeADVFileHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeBatchControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeBatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeFileControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeFileHeaderRecord;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;

public class ACHAdapterUtil
  implements ACHAdapterConsts
{
  private static final char[] gG = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
  private static final HashMap gF = new HashMap();
  
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
  
  public static void log(String paramString)
  {
    FFSDebug.log("ACH Adapter: " + paramString, 6);
  }
  
  public static void log(Throwable paramThrowable, String paramString)
  {
    FFSDebug.log(paramThrowable, "ACH Adapter: " + paramString, 6);
  }
  
  public static void log(String paramString, int paramInt)
  {
    FFSDebug.log("ACH Adapter: " + paramString, paramInt);
  }
  
  public static void warn(String paramString)
  {
    FFSDebug.log("WARNING! ACH Adapter: " + paramString, 0);
  }
  
  public static String getProperty(String paramString)
  {
    String str = null;
    try
    {
      FFSProperties localFFSProperties = (FFSProperties)FFSRegistry.lookup("FFSPROPS");
      str = localFFSProperties.getProperty(paramString);
    }
    catch (Exception localException)
    {
      return null;
    }
    return str;
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
  
  public static int getPropertyInt(String paramString, int paramInt)
  {
    int i = paramInt;
    try
    {
      FFSProperties localFFSProperties = (FFSProperties)FFSRegistry.lookup("FFSPROPS");
      String str = localFFSProperties.getProperty(paramString);
      if (str != null) {
        i = Integer.parseInt(str);
      }
    }
    catch (Exception localException) {}
    return i;
  }
  
  public static char getAlphabetFromInt(int paramInt)
  {
    return gG[paramInt];
  }
  
  public static int getIntFromAlphabet(char paramChar)
  {
    Integer localInteger = (Integer)gF.get(new Character(paramChar));
    return localInteger == null ? -1 : localInteger.intValue();
  }
  
  public static String getACHOptionalField(String paramString)
  {
    if (paramString == null) {
      return " ";
    }
    return paramString;
  }
  
  public static String getNextModifier(String paramString1, String paramString2)
    throws FFSException
  {
    return getNextModifier(paramString1, paramString2, "");
  }
  
  public static String getNextModifier(String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    String str1 = "ACHAdapterUtil.getNextModifier: ";
    if (paramString3 == null) {
      paramString3 = "";
    } else {
      paramString3 = "." + paramString3;
    }
    FFSDebug.log(str1 + "exportFileBase =" + paramString1 + ", partialExportFileName =" + paramString2, 6);
    File localFile1 = new File(paramString1);
    int i = getIntFromAlphabet('A');
    String[] arrayOfString1 = localFile1.list();
    String str2 = null;
    File localFile2 = null;
    String[] arrayOfString2 = null;
    int j = paramString1.lastIndexOf("temp", paramString1.length());
    if (j != -1) {
      str2 = paramString1.substring(0, j);
    }
    if (str2 != null)
    {
      localFile2 = new File(str2);
      arrayOfString2 = localFile2.list();
    }
    int k = 0;
    int m = 0;
    int n = 0;
    if ((arrayOfString2 != null) && (arrayOfString2.length > 0))
    {
      k = arrayOfString2.length;
      m = 1;
    }
    if ((arrayOfString1 != null) && (arrayOfString1.length > 0))
    {
      k += arrayOfString1.length;
      n = 1;
    }
    String[] arrayOfString3 = new String[k];
    if (m != 0) {
      System.arraycopy(arrayOfString2, 0, arrayOfString3, 0, arrayOfString2.length);
    }
    int i1 = 0;
    if (m != 0) {
      i1 = arrayOfString2.length;
    }
    if (n != 0) {
      System.arraycopy(arrayOfString1, 0, arrayOfString3, i1, arrayOfString1.length);
    }
    int i2;
    if (arrayOfString3.length > 0)
    {
      i2 = paramString2.length();
      for (int i3 = 0; i3 < arrayOfString3.length; i3++) {
        if ((arrayOfString3[i3].startsWith(paramString2)) && (arrayOfString3[i3].endsWith(".ACH" + paramString3)))
        {
          char c = arrayOfString3[i3].charAt(i2);
          int i4 = getIntFromAlphabet(c);
          if (i4 >= i) {
            i = i4 + 1;
          }
        }
      }
    }
    try
    {
      i2 = getAlphabetFromInt(i);
    }
    catch (ArrayIndexOutOfBoundsException localArrayIndexOutOfBoundsException)
    {
      FFSDebug.log(localArrayIndexOutOfBoundsException, "ACHAdapterUtil.getNextModifier: Run out of the modifiers! Terminating process!", 0);
      throw new FFSException("ACHAdapterUtil.getNextModifier: Run out of the modifiers! Terminating process!");
    }
    return "" + i2;
  }
  
  public static void writeFileControl(String paramString1, ACHFileCache paramACHFileCache, ACHAgent paramACHAgent, String paramString2)
    throws FFSException
  {
    int i = 0;
    int j = (int)paramACHFileCache.recCount % 10;
    if (j != 0) {
      i = 10 - j;
    }
    TypeFileControlRecord localTypeFileControlRecord = new TypeFileControlRecord();
    localTypeFileControlRecord.Record_Type_Code = 9;
    localTypeFileControlRecord.Batch_Count = paramACHFileCache.fileBatchCount;
    localTypeFileControlRecord.Block_Count = ((int)((paramACHFileCache.recCount + i) / 10L));
    localTypeFileControlRecord.FileEntry_Addenda_Count = ((int)paramACHFileCache.fileEntryCount);
    localTypeFileControlRecord.Total_Debits = paramACHFileCache.fileDebitSum.longValue();
    localTypeFileControlRecord.Total_Credits = paramACHFileCache.fileCreditSum.longValue();
    localTypeFileControlRecord.Entry_Hash = paramACHFileCache.fileHash;
    localTypeFileControlRecord.File_Reserved = " ";
    writeMBRecord(paramString1, localTypeFileControlRecord, 9, "", true, paramACHAgent, paramString2);
    StringBuffer localStringBuffer = new StringBuffer();
    for (int k = 0; k < i; k++)
    {
      localStringBuffer.append("9999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999999");
      localStringBuffer.append(System.getProperty("line.separator"));
    }
    writeRecordContents(paramString1, localStringBuffer.toString(), true);
  }
  
  public static void writeBatchHeader(String paramString1, ACHFileCache paramACHFileCache, ACHAgent paramACHAgent, String paramString2, ACHCompanyInfo paramACHCompanyInfo, ACHFIInfo paramACHFIInfo, short paramShort, String paramString3)
    throws FFSException
  {
    TypeBatchHeaderRecord localTypeBatchHeaderRecord = new TypeBatchHeaderRecord();
    localTypeBatchHeaderRecord.Record_Type_Code = 5;
    localTypeBatchHeaderRecord.Service_Class_Code = paramShort;
    localTypeBatchHeaderRecord.Company_Name = paramACHCompanyInfo.getCompName();
    localTypeBatchHeaderRecord.Company_Discretionary_Data = paramACHCompanyInfo.getCompName();
    localTypeBatchHeaderRecord.Company_Identification = paramACHCompanyInfo.getCompACHId();
    localTypeBatchHeaderRecord.Standard_Entry_Class_Code = "CIE";
    localTypeBatchHeaderRecord.Company_Entry_Description = "BANK TRANS";
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM yy");
    String str = localSimpleDateFormat.format(new Date());
    localTypeBatchHeaderRecord.Company_Descriptive_Date = str;
    localTypeBatchHeaderRecord.Company_Descriptive_DateExists = true;
    localTypeBatchHeaderRecord.Effective_Entry_Date = paramString3.substring(2);
    localTypeBatchHeaderRecord.Originator_Status_Code = "1";
    localTypeBatchHeaderRecord.Originating_DFI_Identification = paramACHFIInfo.getODFIACHId8();
    localTypeBatchHeaderRecord.Batch_Number = paramACHFileCache.fileBatchCount;
    writeMBRecord(paramString1, localTypeBatchHeaderRecord, 5, "CIE", true, paramACHAgent, paramString2);
  }
  
  public static void writeBatchControl(String paramString1, ACHFileCache paramACHFileCache, ACHAgent paramACHAgent, String paramString2, ACHCompanyInfo paramACHCompanyInfo, ACHFIInfo paramACHFIInfo, String paramString3, short paramShort)
    throws FFSException
  {
    ACHBatchCache localACHBatchCache = paramACHFileCache.batchCache;
    TypeBatchControlRecord localTypeBatchControlRecord = new TypeBatchControlRecord();
    localTypeBatchControlRecord.Record_Type_Code = 8;
    localTypeBatchControlRecord.Service_Class_Code = paramShort;
    localTypeBatchControlRecord.Entry_Addenda_Count = localACHBatchCache.batchEntryCount;
    localTypeBatchControlRecord.Entry_Hash = localACHBatchCache.batchHash;
    localTypeBatchControlRecord.Total_Debits = localACHBatchCache.batchDebitSum.longValue();
    localTypeBatchControlRecord.Total_Credits = localACHBatchCache.batchCreditSum.longValue();
    localTypeBatchControlRecord.Company_Identification = paramACHCompanyInfo.getCompACHId();
    localTypeBatchControlRecord.Message_Authentication_Code = paramString3;
    localTypeBatchControlRecord.Message_Authentication_CodeExists = true;
    localTypeBatchControlRecord.Reserved6 = " ";
    localTypeBatchControlRecord.Originating_DFI_Identification = paramACHFIInfo.getODFIACHId8();
    localTypeBatchControlRecord.Batch_Number = paramACHFileCache.fileBatchCount;
    writeMBRecord(paramString1, localTypeBatchControlRecord, 8, "CIE", true, paramACHAgent, paramString2);
  }
  
  public static void writeMBRecord(String paramString1, Object paramObject, int paramInt, String paramString2, boolean paramBoolean, ACHAgent paramACHAgent, String paramString3)
    throws FFSException
  {
    ACHRecordInfo localACHRecordInfo = new ACHRecordInfo();
    localACHRecordInfo.setRecord(paramObject);
    localACHRecordInfo.setRecordType(paramInt);
    localACHRecordInfo.setClassCode(paramString2);
    localACHRecordInfo.setAchVersion(paramString3);
    writeACHRecord(paramString1, localACHRecordInfo, paramBoolean, paramACHAgent);
  }
  
  public static void writeACHRecord(String paramString, ACHRecordInfo paramACHRecordInfo, boolean paramBoolean, ACHAgent paramACHAgent)
    throws FFSException
  {
    if (paramACHRecordInfo.getRecordContent() == null) {
      buildACHRecord(paramACHRecordInfo, paramACHAgent);
    }
    writeRecordContents(paramString, paramACHRecordInfo.getRecordContent(), paramBoolean);
  }
  
  public static void buildACHRecord(ACHRecordInfo paramACHRecordInfo, ACHAgent paramACHAgent)
    throws FFSException
  {
    try
    {
      ACHAgent.build(paramACHRecordInfo, true, true, true);
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "ACHAdapterUtil.buildACHRecord: Can't build ACH record! Terminating process!", 0);
      throw new FFSException("ACHAdapterUtil.buildACHRecord: Can't build ACH record! Terminating process! Reason: " + localException.toString());
    }
    if (paramACHRecordInfo.getStatusCode() != 0)
    {
      FFSDebug.log("ACHAdapterUtil.buildACHRecord: Can't build ACH header record! Terminating process!", 0);
      FFSDebug.log("ACHAdapterUtil.buildACHRecord: error code: " + paramACHRecordInfo.getStatusCode(), 0);
      FFSDebug.log("ACHAdapterUtil.buildACHRecord: error message: " + paramACHRecordInfo.getStatusMsg(), 0);
      throw new FFSException(paramACHRecordInfo.getStatusCode(), paramACHRecordInfo.getStatusMsg());
    }
  }
  
  public static void writeRecordContents(String paramString1, String paramString2, boolean paramBoolean)
    throws FFSException
  {
    FFSDebug.log("ACHAdapterUtil.writeRecordContents: Writing a record to ACH file. file name: " + paramString1, 6);
    FFSDebug.log("ACHAdapterUtil.writeRecordContents: Writing a record to ACH file. record content: " + paramString2, 6);
    try
    {
      PrintWriter localPrintWriter = new PrintWriter(new FileWriter(paramString1, paramBoolean));
      localPrintWriter.write(paramString2);
      localPrintWriter.close();
    }
    catch (Exception localException)
    {
      FFSDebug.log(localException, "ACHAdapterUtil.writeRecordContents: Can't write ACH File header record to the ACH file! Terminating process! fullFileName: " + paramString1, 0);
      throw new FFSException("ACHAdapterUtil.writeRecordContents: Can't write ACH File header record to the ACH file! Terminating process! fullFileName: " + paramString1);
    }
  }
  
  public static String prepareExportFile(ACHFIInfo paramACHFIInfo, String paramString1, String paramString2, boolean paramBoolean, ACHAgent paramACHAgent, String paramString3)
    throws FFSException
  {
    return prepareExportFile(paramACHFIInfo, paramString1, paramString2, paramBoolean, paramACHAgent, paramString3, false, "");
  }
  
  public static String prepareExportFile(ACHFIInfo paramACHFIInfo, String paramString1, String paramString2, boolean paramBoolean, ACHAgent paramACHAgent, String paramString3, String paramString4)
    throws FFSException
  {
    return prepareExportFile(paramACHFIInfo, paramString1, paramString2, paramBoolean, paramACHAgent, paramString3, false, paramString4);
  }
  
  public static String prepareExportFile(ACHFIInfo paramACHFIInfo, String paramString1, String paramString2, boolean paramBoolean1, ACHAgent paramACHAgent, String paramString3, boolean paramBoolean2)
    throws FFSException
  {
    return prepareExportFile(paramACHFIInfo, paramString1, paramString2, paramBoolean1, paramACHAgent, paramString3, paramBoolean2, "");
  }
  
  public static String prepareExportFile(ACHFIInfo paramACHFIInfo, String paramString1, String paramString2, boolean paramBoolean1, ACHAgent paramACHAgent, String paramString3, boolean paramBoolean2, String paramString4)
    throws FFSException
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyMMddHHmm");
    String str1 = localSimpleDateFormat.format(new Date());
    String str2 = str1.substring(0, 6);
    String str3 = str1.substring(6);
    String str4 = paramACHFIInfo.getODFIACHId() + "." + str2 + "." + str3 + ".";
    String str5 = getNextModifier(paramString1, str4, paramString4);
    String str6 = str4 + str5;
    String str7 = paramString1 + str6 + ".ACH";
    if (paramBoolean2 == true) {
      str7 = str7 + ".PossibleDuplicate";
    }
    if ((paramString4 != null) && (paramString4.length() > 0)) {
      str7 = str7 + "." + paramString4;
    }
    File localFile = new File(str7);
    if (localFile.exists())
    {
      FFSDebug.log("ACHBatchAdapter.prepareExportFile, export ACH file exist: " + str7, 0);
      localObject1 = paramString2 + str6 + ".ACH" + "." + String.valueOf(System.currentTimeMillis()) + paramString4;
      localObject2 = new File((String)localObject1);
      localFile.renameTo((File)localObject2);
      FFSDebug.log("ACHBatchAdapter.prepareExportFile, the existing file has been moved to  " + (String)localObject1, 0);
    }
    Object localObject1 = null;
    Object localObject2 = "";
    if (paramBoolean1 == true)
    {
      localObject1 = buildADVFileHeader(paramACHFIInfo, str2, str3, str5);
      localObject2 = "ADV";
    }
    else
    {
      localObject1 = buildNonADVFileHeader(paramACHFIInfo, str2, str3, str5);
    }
    writeMBRecord(str7, localObject1, 1, (String)localObject2, false, paramACHAgent, paramString3);
    return str7;
  }
  
  public static Object buildNonADVFileHeader(ACHFIInfo paramACHFIInfo, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    TypeFileHeaderRecord localTypeFileHeaderRecord = new TypeFileHeaderRecord();
    localTypeFileHeaderRecord.Record_Type_Code = 1;
    localTypeFileHeaderRecord.Priority_Code = 1;
    localTypeFileHeaderRecord.Immediate_Destination = J(paramACHFIInfo.getRDFIACHId());
    localTypeFileHeaderRecord.Immediate_Origin = J(paramACHFIInfo.getODFIACHId());
    localTypeFileHeaderRecord.File_Creation_Date = paramString1;
    localTypeFileHeaderRecord.File_Creation_Time = paramString2;
    localTypeFileHeaderRecord.File_Creation_TimeExists = true;
    localTypeFileHeaderRecord.File_Identification_Modifier = paramString3;
    localTypeFileHeaderRecord.Record_Size = 94;
    localTypeFileHeaderRecord.Blocking_Factor = 10;
    localTypeFileHeaderRecord.Format_Code = 1;
    localTypeFileHeaderRecord.Immediate_Destination_Name = getACHOptionalField(paramACHFIInfo.getRDFIName());
    localTypeFileHeaderRecord.Immediate_Destination_NameExists = true;
    localTypeFileHeaderRecord.Immediate_Origin_Name = getACHOptionalField(paramACHFIInfo.getODFIName());
    localTypeFileHeaderRecord.Immediate_Origin_NameExists = true;
    localTypeFileHeaderRecord.Reference_Code = "        ";
    localTypeFileHeaderRecord.Reference_CodeExists = true;
    return localTypeFileHeaderRecord;
  }
  
  public static Object buildADVFileHeader(ACHFIInfo paramACHFIInfo, String paramString1, String paramString2, String paramString3)
    throws FFSException
  {
    TypeADVFileHeaderRecord localTypeADVFileHeaderRecord = new TypeADVFileHeaderRecord();
    localTypeADVFileHeaderRecord.Record_Type_Code = 1;
    localTypeADVFileHeaderRecord.Priority_Code = 1;
    localTypeADVFileHeaderRecord.Immediate_Destination = J(paramACHFIInfo.getRDFIACHId());
    localTypeADVFileHeaderRecord.Immediate_Origin = J(paramACHFIInfo.getODFIACHId());
    localTypeADVFileHeaderRecord.File_Creation_Date = paramString1;
    localTypeADVFileHeaderRecord.File_Creation_Time = paramString2;
    localTypeADVFileHeaderRecord.File_Creation_TimeExists = true;
    localTypeADVFileHeaderRecord.File_Identification_Modifier = paramString3;
    localTypeADVFileHeaderRecord.Record_Size = 94;
    localTypeADVFileHeaderRecord.Blocking_Factor = 10;
    localTypeADVFileHeaderRecord.Format_Code = 1;
    localTypeADVFileHeaderRecord.Immediate_Destination_Name = paramACHFIInfo.getRDFIName();
    localTypeADVFileHeaderRecord.Immediate_Origin_Name = paramACHFIInfo.getODFIName();
    localTypeADVFileHeaderRecord.Reference_Code = "ADV File";
    localTypeADVFileHeaderRecord.Reference_CodeExists = true;
    return localTypeADVFileHeaderRecord;
  }
  
  public static String[] generateFileCreationDateTime()
  {
    String[] arrayOfString = new String[2];
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyMMddHHmm");
    String str1 = localSimpleDateFormat.format(new Date());
    String str2 = str1.substring(0, 6);
    arrayOfString[0] = str2;
    String str3 = str1.substring(6);
    arrayOfString[1] = str3;
    return arrayOfString;
  }
  
  private static String J(String paramString)
    throws FFSException
  {
    String str = paramString;
    if ((paramString != null) && (paramString.length() == 8)) {
      str = BPWUtil.convertToABA9(paramString);
    }
    return ACHRecordRegulator.regulateACHId(str);
  }
  
  public static void doFMLoggingForACH(FFSConnectionHolder paramFFSConnectionHolder, String paramString1, String paramString2)
  {
    FFSDebug.log("ACHAdapterutil.doFMLoggingForACH : Starting FM logging", 6);
    FMLogRecord localFMLogRecord = new FMLogRecord();
    try
    {
      localFMLogRecord.setMillis(System.currentTimeMillis());
      localFMLogRecord.setFileType("ACH");
      localFMLogRecord.setFileName(paramString1);
      localFMLogRecord.setHostName(InetAddress.getLocalHost().getHostName());
      localFMLogRecord.setActivityType("Transfer");
      localFMLogRecord.setFromSystem("BPW");
      localFMLogRecord.setToSystem("ACH");
      localFMLogRecord.setStatus("Complete");
      LinkedList localLinkedList = new LinkedList();
      localLinkedList.add(localFMLogRecord);
      FMLogAgent.writeRecords(paramFFSConnectionHolder.conn.getConnection(), localLinkedList);
    }
    catch (Throwable localThrowable)
    {
      String str = FFSDebug.stackTrace(localThrowable);
      FFSDebug.log("*** " + paramString2 + ", File Monitor Logging failed :" + str, 0);
    }
    finally
    {
      FFSDebug.log("ACHAdapterutil.doFMLoggingForACH : End ", 6);
    }
  }
  
  public static boolean allowsMinimumHeaderControl()
  {
    boolean bool = false;
    String str = getProperty("bpw.ach.file.allow.minimum.header.control", "N");
    if ((str != null) && (str.equalsIgnoreCase("Y"))) {
      bool = true;
    }
    FFSDebug.log("ACHAdapterUtil.allowsMinimumHeaderControl: " + bool, 6);
    return bool;
  }
  
  static
  {
    for (int i = 0; i < gG.length; i++) {
      gF.put(new Character(gG[i]), new Integer(i));
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.util.ACHAdapterUtil
 * JD-Core Version:    0.7.0.1
 */