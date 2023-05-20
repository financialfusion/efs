package com.ffusion.services.dummy;

import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AccountHistories;
import com.ffusion.beans.accounts.AccountSummaries;
import com.ffusion.beans.dataconsolidator.ImportedFile;
import com.ffusion.beans.dataconsolidator.ImportedFiles;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.dataconsolidator.adapter.DCException;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.DataConsolidator3;
import com.ffusion.services.dataconsolidator.BaseDataConsolidatorService;
import java.util.Calendar;
import java.util.HashMap;

public class DummyDataConsolidatorService
  extends BaseDataConsolidatorService
  implements DataConsolidator3
{
  public void initialize()
    throws DCException
  {
    super.initialize();
  }
  
  public void cleanup(String paramString, int paramInt, HashMap paramHashMap)
    throws DCException
  {}
  
  public IReportResult getAccountReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DCException
  {
    return null;
  }
  
  public void undoFile(ImportedFile paramImportedFile, ImportedFiles paramImportedFiles, HashMap paramHashMap)
    throws DCException
  {}
  
  public ImportedFiles getImportedFileList(int paramInt, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return null;
  }
  
  public ImportedFiles getDependentFiles(ImportedFile paramImportedFile, HashMap paramHashMap)
    throws DCException
  {
    return null;
  }
  
  public AccountHistories getHistory(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return null;
  }
  
  public AccountSummaries getSummary(Account paramAccount, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException
  {
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.dummy.DummyDataConsolidatorService
 * JD-Core Version:    0.7.0.1
 */