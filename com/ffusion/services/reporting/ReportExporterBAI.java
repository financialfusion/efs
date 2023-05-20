package com.ffusion.services.reporting;

import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.RptException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReportExporterBAI
  extends ReportExporterBase
{
  ReportExporterBase Z = null;
  
  public ReportExporterBAI(ReportCriteria paramReportCriteria, String paramString, HashMap paramHashMap)
  {
    super(paramReportCriteria);
    if ((paramString.equals("DepositDetail")) || (paramString.equals("TransactionDetail")) || (paramString.equals("AccountHistory")) || (paramString.equals("CreditReport")) || (paramString.equals("DebitReport"))) {
      this.Z = new ReportExporterBAITransactions(paramReportCriteria, paramHashMap);
    } else if ((paramString.equals("BalanceSummaryReport")) || (paramString.equals("BalanceDetailOnlyReport")) || (paramString.equals("BalanceDetailReport"))) {
      this.Z = new ReportExporterBAIBalanceDetail(paramReportCriteria, paramHashMap);
    } else if (paramString.equals("CustomSummaryReport")) {
      this.Z = new ReportExporterBAICustomSummary(paramReportCriteria, paramHashMap);
    }
  }
  
  protected Object doFlushToObject()
    throws RptException
  {
    return this.Z.doFlushToObject();
  }
  
  protected void doGenerateReportHeader(String paramString1, String paramString2, String paramString3, String paramString4, ReportCriteria paramReportCriteria)
    throws RptException
  {
    this.Z.doGenerateReportHeader(paramString1, paramString2, paramString3, paramString4, paramReportCriteria);
  }
  
  protected void doGenerateReportFooter(String paramString)
    throws RptException
  {
    this.Z.doGenerateReportFooter(paramString);
  }
  
  protected void doGenerateReportResultHeading(String paramString1, String paramString2)
    throws RptException
  {
    this.Z.doGenerateReportResultHeading(paramString1, paramString2);
  }
  
  protected void doGenerateReportResultSectionHeading(String paramString1, String paramString2)
    throws RptException
  {
    this.Z.doGenerateReportResultSectionHeading(paramString1, paramString2);
  }
  
  protected void doGenerateReportResultRowData(Object paramObject, String paramString1, float paramFloat, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws RptException
  {
    this.Z.doGenerateReportResultRowData(paramObject, paramString1, paramFloat, paramString2, paramString3, paramString4, paramBoolean);
  }
  
  protected void doGenerateReportResultColumnHeading(ArrayList paramArrayList, float paramFloat, String paramString)
    throws RptException
  {
    this.Z.doGenerateReportResultColumnHeading(paramArrayList, paramFloat, paramString);
  }
  
  protected void doInitReportResultTable()
    throws RptException
  {
    this.Z.doInitReportResultTable();
  }
  
  protected void doFiniReportResultTable()
    throws RptException
  {
    this.Z.doFiniReportResultTable();
  }
  
  protected void doInitReportResultRow()
    throws RptException
  {
    this.Z.doInitReportResultRow();
  }
  
  protected void doFiniReportResultRow()
    throws RptException
  {
    this.Z.doFiniReportResultRow();
  }
  
  protected void doWriteBlankLine()
    throws RptException
  {
    this.Z.doWriteBlankLine();
  }
  
  protected void doWriteParagraph(String paramString)
    throws RptException
  {
    this.Z.doWriteParagraph(paramString);
  }
  
  protected void doAddPageBreak()
    throws RptException
  {}
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterBAI
 * JD-Core Version:    0.7.0.1
 */