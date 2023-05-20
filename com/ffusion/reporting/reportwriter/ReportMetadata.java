package com.ffusion.reporting.reportwriter;

import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportColumns;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportRow;
import java.util.Locale;

public class ReportMetadata
{
  private ReportCriteria a = null;
  private ReportDataDimensions jdField_for = null;
  private ReportColumns jdField_if = null;
  private int jdField_int = 0;
  private int jdField_do = 0;
  
  public ReportMetadata(Locale paramLocale)
  {
    this.jdField_if = new ReportColumns(paramLocale);
  }
  
  public ReportCriteria getReportCriteria()
  {
    return this.a;
  }
  
  public void setReportCriteria(ReportCriteria paramReportCriteria)
  {
    this.a = paramReportCriteria;
  }
  
  public ReportDataDimensions getReportDataDimensions()
  {
    return this.jdField_for;
  }
  
  public void setReportDataDimensions(ReportDataDimensions paramReportDataDimensions)
  {
    this.jdField_for = paramReportDataDimensions;
  }
  
  public void addColumn(ReportColumn paramReportColumn)
  {
    this.jdField_if.add(paramReportColumn);
    this.jdField_int += 1;
  }
  
  public int getNumColumnsAdded()
  {
    return this.jdField_int;
  }
  
  public void addRow(ReportRow paramReportRow)
  {
    this.jdField_do += 1;
  }
  
  public int getNumRowsAdded()
  {
    return this.jdField_do;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.reporting.reportwriter.ReportMetadata
 * JD-Core Version:    0.7.0.1
 */