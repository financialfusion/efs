package com.ffusion.csil.handlers;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.beans.dataconsolidator.ImportedFile;
import com.ffusion.beans.dataconsolidator.ImportedFiles;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.dataconsolidator.adapter.DCException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.DataConsolidator3;
import com.ffusion.util.logging.DebugLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DataConsolidatorHandler
{
  private static final String jdField_do = "Data Consolidator";
  private static DataConsolidator3 jdField_if = null;
  private static HashMap a = new HashMap();
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    String str = "DataConsolidatorHandler.initialize";
    HashMap localHashMap = HandlerUtil.getServiceSettings(paramHashMap, "Data Consolidator", str, 20107);
    jdField_if = (DataConsolidator3)HandlerUtil.instantiateService(localHashMap, str, 20107);
    try
    {
      jdField_if.initialize();
    }
    catch (DCException localDCException)
    {
      CSILException localCSILException = new CSILException(-1009, 20107, localDCException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static Object getService()
  {
    return jdField_if;
  }
  
  public static void cleanup(String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "DataConsolidatorHandler.cleanup";
    try
    {
      jdField_if.cleanup(paramString, paramInt, paramHashMap);
    }
    catch (DCException localDCException)
    {
      CSILException localCSILException = new CSILException(-1009, 20111, localDCException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static AccountHistories getHistory(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_if.getHistory(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (DCException localDCException)
    {
      CSILException localCSILException = new CSILException(-1009, 1027, localDCException);
      throw localCSILException;
    }
  }
  
  public static AccountSummaries getSummary(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    try
    {
      return jdField_if.getSummary(paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (DCException localDCException)
    {
      CSILException localCSILException = new CSILException(-1009, 1027, localDCException);
      throw localCSILException;
    }
  }
  
  public static IReportResult getAccountReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "DataConsolidatorHandler.getAccountReportData";
    try
    {
      return jdField_if.getAccountReportData(paramReportCriteria, paramHashMap);
    }
    catch (DCException localDCException)
    {
      CSILException localCSILException = new CSILException(-1009, localDCException.getCode(), localDCException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static void undoFile(ImportedFile paramImportedFile, ImportedFiles paramImportedFiles, HashMap paramHashMap)
    throws CSILException
  {
    String str = "DataConsolidatorHandler.undoFile";
    try
    {
      jdField_if.undoFile(paramImportedFile, paramImportedFiles, paramHashMap);
    }
    catch (DCException localDCException)
    {
      CSILException localCSILException = new CSILException(-1009, 21007, localDCException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ImportedFiles getImportedFileList(SecureUser paramSecureUser, int paramInt, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "DataConsolidatorHandler.getImportedFileList";
    try
    {
      return jdField_if.getImportedFileList(paramInt, paramCalendar1, paramCalendar2, paramHashMap);
    }
    catch (DCException localDCException)
    {
      CSILException localCSILException = new CSILException(-1009, 1027, localDCException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ImportedFiles getDependentFiles(SecureUser paramSecureUser, ImportedFile paramImportedFile, HashMap paramHashMap)
    throws CSILException
  {
    String str = "DataConsolidatorHandler.getDependentFiles";
    try
    {
      return jdField_if.getDependentFiles(paramImportedFile, paramHashMap);
    }
    catch (DCException localDCException)
    {
      CSILException localCSILException = new CSILException(-1009, 1027, localDCException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static BAITypeCodeInfo getBAITypeCodeInfo(int paramInt)
    throws CSILException
  {
    String str = "DataConsolidatorHandler.getBAITypeCodeInfo";
    try
    {
      return jdField_if.getBAITypeCodeInfo(paramInt);
    }
    catch (DCException localDCException)
    {
      CSILException localCSILException = new CSILException(-1009, 1027, localDCException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getBAITypeCodeInfoList(int paramInt)
    throws CSILException
  {
    String str = "DataConsolidatorHandler.getBAITypeCodeInfoList";
    try
    {
      return jdField_if.getBAITypeCodeInfoList(paramInt);
    }
    catch (DCException localDCException)
    {
      CSILException localCSILException = new CSILException(-1009, 1027, localDCException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static int getBAICode(String paramString1, String paramString2)
    throws CSILException
  {
    String str = "DataConsolidatorHandler.getBAICode";
    try
    {
      return jdField_if.getBAICode(paramString1, paramString2);
    }
    catch (DCException localDCException)
    {
      CSILException localCSILException = new CSILException(-1009, 1027, localDCException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getBAITypeCodesForTransaction(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "DataConsolidatorHandler.getBAITypeCodesForTransaction";
    try
    {
      return jdField_if.getBAITypeCodesForTransaction(paramInt, paramHashMap);
    }
    catch (DCException localDCException)
    {
      CSILException localCSILException = new CSILException(-1009, 1027, localDCException);
      DebugLog.throwing(str, localCSILException);
      throw localCSILException;
    }
  }
  
  public static ArrayList getBAITypeCodesByTransactionType(int paramInt)
    throws CSILException
  {
    String str = Integer.toString(paramInt);
    ArrayList localArrayList1 = (ArrayList)a.get(str);
    if (localArrayList1 != null) {
      return localArrayList1;
    }
    ArrayList localArrayList2 = getBAITypeCodeInfoList(2);
    if ((localArrayList2 == null) || (localArrayList2.isEmpty()))
    {
      localArrayList1 = new ArrayList(0);
      a.put(str, localArrayList1);
      return localArrayList1;
    }
    localArrayList1 = new ArrayList();
    for (int i = 0; i < localArrayList2.size(); i++)
    {
      BAITypeCodeInfo localBAITypeCodeInfo = (BAITypeCodeInfo)localArrayList2.get(i);
      if (localBAITypeCodeInfo.getTransactionType() == paramInt) {
        localArrayList1.add(Integer.toString(localBAITypeCodeInfo.getCode()));
      }
    }
    localArrayList1.trimToSize();
    a.put(str, localArrayList1);
    return localArrayList1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.handlers.DataConsolidatorHandler
 * JD-Core Version:    0.7.0.1
 */