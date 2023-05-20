package com.ffusion.csil.core;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.dataconsolidator.BAITypeCodeInfo;
import com.ffusion.beans.dataconsolidator.ImportedFile;
import com.ffusion.beans.dataconsolidator.ImportedFiles;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.Entitlement;
import com.ffusion.csil.handlers.DataConsolidatorHandler;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableList;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.logging.PerfLog;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class DataConsolidator
  extends Initialize
{
  private static final String axs = "com.ffusion.util.logging.audit.dataconsolidator";
  private static final String axw = "AuditMessage_1.1";
  private static final String axv = "AuditMessage_1.2";
  private static final String axu = "AuditListItem_1";
  private static final String axr = "AuditList_1";
  private static final String axt = "AuditEntFault_1";
  
  public static void initialize(HashMap paramHashMap)
    throws CSILException
  {
    debug("com.ffusion.csil.core.DataConsolidator.initialize");
    DataConsolidatorHandler.initialize(paramHashMap);
  }
  
  public static Object getService()
  {
    return DataConsolidatorHandler.getService();
  }
  
  public static void cleanup(String paramString, int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    String str = "DataConsolidator.cleanup";
    long l = System.currentTimeMillis();
    DataConsolidatorHandler.cleanup(paramString, paramInt, paramHashMap);
    PerfLog.log(str, l, true);
    debug(str);
  }
  
  public static IReportResult getAccountReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws CSILException
  {
    String str = "DataConsolidator.getAccountReportData";
    debug(str);
    return DataConsolidatorHandler.getAccountReportData(paramReportCriteria, paramHashMap);
  }
  
  public static ImportedFiles getImportedFileList(SecureUser paramSecureUser, int paramInt, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "csil.core.DataConsolidator.getImportedFileList";
    long l = System.currentTimeMillis();
    paramHashMap.put("LOCALE", paramSecureUser.getLocale());
    ImportedFiles localImportedFiles = DataConsolidatorHandler.getImportedFileList(paramSecureUser, paramInt, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localImportedFiles;
  }
  
  public static ImportedFiles getDependentFiles(SecureUser paramSecureUser, ImportedFile paramImportedFile, HashMap paramHashMap)
    throws CSILException
  {
    String str = "csil.core.DataConsolidator.getDependentFiles";
    long l = System.currentTimeMillis();
    ImportedFiles localImportedFiles = DataConsolidatorHandler.getDependentFiles(paramSecureUser, paramImportedFile, paramHashMap);
    PerfLog.log(str, l, true);
    debug(paramSecureUser, str);
    return localImportedFiles;
  }
  
  public static AccountHistories getHistory(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "DataConsolidator.getHistory";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    AccountHistories localAccountHistories = DataConsolidatorHandler.getHistory(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    PerfLog.log(str, l, true);
    return localAccountHistories;
  }
  
  public static AccountSummaries getSummary(SecureUser paramSecureUser, Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws CSILException
  {
    String str = "DataConsolidator.getSummary";
    debug(paramSecureUser, str);
    long l = System.currentTimeMillis();
    AccountSummaries localAccountSummaries = DataConsolidatorHandler.getSummary(paramSecureUser, paramAccount, paramCalendar1, paramCalendar2, paramHashMap);
    localAccountSummaries.setSecureUser(paramSecureUser);
    PerfLog.log(str, l, true);
    return localAccountSummaries;
  }
  
  public static void undoFile(SecureUser paramSecureUser, ImportedFile paramImportedFile, ImportedFiles paramImportedFiles, HashMap paramHashMap)
    throws CSILException
  {
    String str1 = "DataConsolidator.undoFile";
    debug(str1);
    long l = System.currentTimeMillis();
    Entitlement localEntitlement = new Entitlement("BC Undo File", null, null);
    if (!Entitlements.checkEntitlement(EntitlementsUtil.getEntitlementGroupMember(paramSecureUser), localEntitlement))
    {
      DebugLog.log("The user is not entitled to use the undo file functionality. The call to DataConsolidator.undoFile has failed for this reason.");
      localObject1 = new LocalizableString("com.ffusion.util.logging.audit.dataconsolidator", "AuditEntFault_1", null);
      logEntitlementFault(paramSecureUser, (ILocalizable)localObject1, TrackingIDGenerator.GetNextID());
      throw new CSILException("DataConsolidator.undoFile", 20001);
    }
    EntitlementsUtil.OBOViewOnlyCheck(paramSecureUser);
    DataConsolidatorHandler.undoFile(paramImportedFile, paramImportedFiles, paramHashMap);
    Object localObject1 = new Object[3];
    localObject1[0] = paramImportedFile.getFileName();
    localObject1[1] = new LocalizableDate(paramImportedFile.getImportTimeValue(), true);
    String str2 = "AuditMessage_1.1";
    if (paramImportedFiles.size() != 0)
    {
      localObject2 = new LocalizableList("com.ffusion.util.logging.audit.dataconsolidator", "AuditList_1");
      localObject1[2] = localObject2;
      str2 = "AuditMessage_1.2";
      for (int i = 0; i < paramImportedFiles.size(); i++)
      {
        ImportedFile localImportedFile = (ImportedFile)paramImportedFiles.get(i);
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localImportedFile.getFileName();
        arrayOfObject[1] = new LocalizableDate(localImportedFile.getImportTimeValue(), true);
        ((LocalizableList)localObject2).add(new LocalizableString("com.ffusion.util.logging.audit.dataconsolidator", "AuditListItem_1", arrayOfObject));
      }
    }
    Object localObject2 = new LocalizableString("com.ffusion.util.logging.audit.dataconsolidator", str2, (Object[])localObject1);
    String str3 = TrackingIDGenerator.GetNextID();
    PerfLog.log(str1, l, true);
    audit(paramSecureUser, (ILocalizable)localObject2, str3, 2250);
  }
  
  public static BAITypeCodeInfo getBAITypeCodeInfo(int paramInt)
    throws CSILException
  {
    return DataConsolidatorHandler.getBAITypeCodeInfo(paramInt);
  }
  
  public static ArrayList getBAITypeCodeInfoList(int paramInt)
    throws CSILException
  {
    String str = "DataConsolidator.getBAITypeCodeInfoList";
    long l = System.currentTimeMillis();
    ArrayList localArrayList = DataConsolidatorHandler.getBAITypeCodeInfoList(paramInt);
    PerfLog.log(str, l, true);
    debug(str);
    return localArrayList;
  }
  
  public static int getBAICode(String paramString1, String paramString2)
    throws CSILException
  {
    return DataConsolidatorHandler.getBAICode(paramString1, paramString2);
  }
  
  public static ArrayList getBAITypeCodesForTransaction(int paramInt, HashMap paramHashMap)
    throws CSILException
  {
    return DataConsolidatorHandler.getBAITypeCodesForTransaction(paramInt, paramHashMap);
  }
  
  protected static ArrayList getBAITypeCodesByTransactionType(int paramInt)
    throws CSILException
  {
    return DataConsolidatorHandler.getBAITypeCodesByTransactionType(paramInt);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.csil.core.DataConsolidator
 * JD-Core Version:    0.7.0.1
 */