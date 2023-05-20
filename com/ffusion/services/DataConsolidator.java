package com.ffusion.services;

import com.ffusion.beans.dataconsolidator.ImportedFile;
import com.ffusion.beans.dataconsolidator.ImportedFiles;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.dataconsolidator.adapter.DCException;
import com.ffusion.reporting.IReportResult;
import java.util.Calendar;
import java.util.HashMap;

public abstract interface DataConsolidator
{
  public static final String DC_PROPERTIES_FILENAME = "dataconsolidator.xml";
  
  public abstract void initialize()
    throws DCException;
  
  public abstract void cleanup(String paramString, int paramInt, HashMap paramHashMap)
    throws DCException;
  
  public abstract IReportResult getAccountReportData(ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws DCException;
  
  public abstract ImportedFiles getImportedFileList(int paramInt, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws DCException;
  
  public abstract ImportedFiles getDependentFiles(ImportedFile paramImportedFile, HashMap paramHashMap)
    throws DCException;
  
  public abstract void undoFile(ImportedFile paramImportedFile, ImportedFiles paramImportedFiles, HashMap paramHashMap)
    throws DCException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.DataConsolidator
 * JD-Core Version:    0.7.0.1
 */