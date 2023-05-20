package com.ffusion.fileimporter.fileadapters;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fileimporter.ErrorMessages;
import com.ffusion.beans.fileimporter.ImportError;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.FIException;
import com.ffusion.efs.adapters.profile.ReportTransactionAdapter;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.filemonitor.FMException;
import com.ffusion.util.beans.filemonitor.FMLogRecord;
import com.ffusion.util.filemonitor.FMLog;
import com.ffusion.util.logging.DebugLog;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

public class ACHAdapter
  implements IFileAdapter
{
  public static final String FILE_UPLOAD_ERROR_MESSAGE = "FileUploadErrorMessage";
  public static final String FILE_CONTENT = "FileContent";
  
  public void initialize(HashMap paramHashMap)
    throws FIException
  {
    String str = "ACHAdapter : initialize";
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
    String str1 = "ACHAdapter.processFile";
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    String str2 = null;
    StringBuffer localStringBuffer = new StringBuffer();
    createFMLogRecordForACH(paramHashMap, "In Process");
    try
    {
      for (str2 = localBufferedReader.readLine(); str2 != null; str2 = localBufferedReader.readLine())
      {
        localStringBuffer.append(str2);
        localStringBuffer.append(System.getProperty("line.separator"));
      }
      paramHashMap.put("FileContent", localStringBuffer);
      ACHImportAdapter localACHImportAdapter = new ACHImportAdapter();
      localACHImportAdapter.setValidateForUploadOnly("true");
      paramHashMap.remove("ACHCOMPANY");
      localACHImportAdapter.processFile(localStringBuffer, paramHashMap);
      localObject = (ArrayList)paramHashMap.get("ImportErrors");
      if ((localObject != null) && (!((ArrayList)localObject).isEmpty()))
      {
        paramHashMap.put("PARSE_ERRORS", localObject);
        paramHashMap.put("FileUploadErrorMessage", "The following errors occurred while trying to process your file:");
        createFMLogRecordForACH(paramHashMap, "Failed");
      }
    }
    catch (Exception localException)
    {
      Object localObject = "Unable to upload ACH file";
      createFMLogRecordForACH(paramHashMap, "Failed");
      if (((localException instanceof CSILException)) && (((CSILException)localException).why != null) && (((CSILException)localException).why.length() > 0)) {
        localObject = ((CSILException)localException).why;
      }
      FIException localFIException = new FIException("com.ffusion.fileimporter.ACHAdapter.processFile", 2, (String)localObject, localException);
      DebugLog.throwing("com.ffusion.fileimporter.ACHAdapter.processFile", localFIException);
      throw localFIException;
    }
  }
  
  public static void createFMLogRecordForACH(HashMap paramHashMap, String paramString)
  {
    String str = "ACHAdapter.createFMLogRecordForACH";
    FMLogRecord localFMLogRecord = jdMethod_int("ACH", paramHashMap);
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
  
  public static IReportResult generateReport(SecureUser paramSecureUser, HashMap paramHashMap)
    throws Exception
  {
    ReportCriteria localReportCriteria = (ReportCriteria)paramHashMap.get("ReportCriteria");
    return generateReport(paramSecureUser, localReportCriteria, paramHashMap);
  }
  
  public static IReportResult generateReport(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws Exception
  {
    if (paramHashMap == null) {
      paramHashMap = new HashMap();
    }
    String str = (String)paramHashMap.get("FileUploadErrorMessage");
    Object localObject;
    if (str != null)
    {
      localObject = paramSecureUser.getLocale();
      ReportResult localReportResult = new ReportResult((Locale)localObject);
      localReportResult.init(paramReportCriteria);
      setFileUploadInfoReportData(localReportResult, paramSecureUser, paramHashMap);
      setFileUploadStatusReportData(localReportResult, paramHashMap);
      localReportResult.fini();
      return localReportResult;
    }
    if (paramHashMap.get("TRACKING_ID") != null)
    {
      localObject = paramReportCriteria.getSearchCriteria();
      ((Properties)localObject).setProperty("TrackingId", (String)paramHashMap.get("TRACKING_ID"));
    }
    return ReportTransactionAdapter.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
  }
  
  public static void setFileUploadInfoReportData(ReportResult paramReportResult, SecureUser paramSecureUser, HashMap paramHashMap)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(3);
    localReportDataDimensions.setNumRows(1);
    localReportResult.setDataDimensions(localReportDataDimensions);
    ReportHeading localReportHeading = new ReportHeading(localLocale);
    localReportHeading.setLabel(ReportConsts.getColumnName(2473, localLocale));
    localReportResult.setSectionHeading(localReportHeading);
    ReportColumn localReportColumn1 = new ReportColumn(localLocale);
    ArrayList localArrayList1 = new ArrayList();
    localArrayList1.add(ReportConsts.getColumnName(2474, localLocale));
    localReportColumn1.setLabels(localArrayList1);
    localReportColumn1.setDataType("java.lang.String");
    localReportColumn1.setWidthAsPercent(5);
    localReportResult.addColumn(localReportColumn1);
    ReportColumn localReportColumn2 = new ReportColumn(localLocale);
    ArrayList localArrayList2 = new ArrayList();
    localArrayList2.add(ReportConsts.getColumnName(2475, localLocale));
    localReportColumn2.setLabels(localArrayList2);
    localReportColumn2.setDataType("java.lang.String");
    localReportColumn2.setWidthAsPercent(5);
    localReportResult.addColumn(localReportColumn2);
    ReportColumn localReportColumn3 = new ReportColumn(localLocale);
    ArrayList localArrayList3 = new ArrayList();
    localArrayList3.add(ReportConsts.getColumnName(2485, localLocale));
    localReportColumn3.setLabels(localArrayList3);
    localReportColumn3.setDataType("java.lang.String");
    localReportColumn3.setWidthAsPercent(8);
    localReportResult.addColumn(localReportColumn3);
    ReportRow localReportRow = new ReportRow(localLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    localReportRow.setDataItems(localReportDataItems);
    ReportDataItem localReportDataItem = localReportDataItems.add();
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    String str = localSimpleDateFormat.format(new Date());
    jdMethod_try(localReportDataItem, str);
    localReportDataItem = localReportDataItems.add();
    jdMethod_try(localReportDataItem, paramSecureUser.getUserName());
    localReportDataItem = localReportDataItems.add();
    jdMethod_try(localReportDataItem, (String)paramHashMap.get("FILE_NAME"));
    localReportResult.addRow(localReportRow);
  }
  
  public static void setFileUploadStatusReportData(ReportResult paramReportResult, HashMap paramHashMap)
    throws Exception
  {
    Locale localLocale = paramReportResult.getLocale();
    ReportResult localReportResult1 = new ReportResult(localLocale);
    paramReportResult.addSubReport(localReportResult1);
    ReportDataDimensions localReportDataDimensions = new ReportDataDimensions(localLocale);
    localReportDataDimensions.setNumColumns(1);
    localReportDataDimensions.setNumRows(-1);
    localReportResult1.setDataDimensions(localReportDataDimensions);
    ReportHeading localReportHeading = new ReportHeading(localLocale);
    localReportHeading.setLabel(ReportConsts.getColumnName(1856, localLocale));
    localReportResult1.setSectionHeading(localReportHeading);
    ReportColumn localReportColumn = new ReportColumn(localLocale);
    ArrayList localArrayList = new ArrayList();
    String str1 = (String)paramHashMap.get("FileUploadErrorMessage");
    if (str1 != null) {
      localArrayList.add(ReportConsts.getColumnName(2486, localLocale));
    } else {
      localArrayList.add(ReportConsts.getColumnName(2484, localLocale));
    }
    localReportColumn.setLabels(localArrayList);
    localReportColumn.setDataType("java.lang.String");
    localReportColumn.setWidthAsPercent(10);
    localReportColumn.setReportColumnProperty("DATASTYLE", "errorcolor reportDataSubtotal");
    localReportColumn.setReportColumnProperty("HTML_FORCE_SPACES", "true");
    localReportResult1.addColumn(localReportColumn);
    ReportRow localReportRow = new ReportRow(localLocale);
    ReportDataItems localReportDataItems = new ReportDataItems(localLocale);
    localReportRow.setDataItems(localReportDataItems);
    ReportDataItem localReportDataItem = localReportDataItems.add();
    if (str1 != null) {
      jdMethod_try(localReportDataItem, str1);
    } else {
      jdMethod_try(localReportDataItem, "");
    }
    localReportResult1.addRow(localReportRow);
    ErrorMessages localErrorMessages = (ErrorMessages)paramHashMap.get("PARSE_ERRORS");
    if (localErrorMessages != null)
    {
      String str2 = null;
      localErrorMessages.sort();
      ReportResult localReportResult2 = null;
      for (int i = 0; i < localErrorMessages.size(); i++)
      {
        ImportError localImportError = (ImportError)localErrorMessages.get(i);
        if ((str2 == null) || (!str2.equals(localImportError.getTitle())))
        {
          localReportResult2 = new ReportResult(localLocale);
          paramReportResult.addSubReport(localReportResult2);
          localObject1 = new ReportDataDimensions(localLocale);
          ((ReportDataDimensions)localObject1).setNumColumns(1);
          ((ReportDataDimensions)localObject1).setNumRows(-1);
          localReportResult2.setDataDimensions((ReportDataDimensions)localObject1);
          str2 = localImportError.getTitle();
          localObject2 = new ReportHeading(localLocale);
          ((ReportHeading)localObject2).setLabel(str2);
          localReportResult2.setSectionHeading((ReportHeading)localObject2);
          localObject3 = new ReportColumn(localLocale);
          ((ReportColumn)localObject3).setDataType("java.lang.String");
          ((ReportColumn)localObject3).setWidthAsPercent(100);
          ((ReportColumn)localObject3).setReportColumnProperty("DATASTYLE", "errorcolor reportDataSubtotal");
          localReportResult2.addColumn((ReportColumn)localObject3);
        }
        Object localObject1 = new ReportRow(localLocale);
        Object localObject2 = new ReportDataItems(localLocale);
        ((ReportRow)localObject1).setDataItems((ReportDataItems)localObject2);
        Object localObject3 = ((ReportDataItems)localObject2).add();
        jdMethod_try((ReportDataItem)localObject3, localImportError.getMessage());
        localReportResult2.addRow((ReportRow)localObject1);
      }
    }
  }
  
  private static void jdMethod_try(ReportDataItem paramReportDataItem, Object paramObject)
  {
    if (paramObject == null) {
      paramReportDataItem.setData("");
    } else {
      paramReportDataItem.setData(paramObject);
    }
  }
  
  private static FMLogRecord jdMethod_int(String paramString, HashMap paramHashMap)
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
      localFMLogRecord.setToSystem("CBS ACH");
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
  
  public static IReportResult getReportData(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws Exception
  {
    String str = "ACHAdapter.getReportData";
    return ReportTransactionAdapter.getReportData(paramSecureUser, paramReportCriteria, paramHashMap);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.ACHAdapter
 * JD-Core Version:    0.7.0.1
 */