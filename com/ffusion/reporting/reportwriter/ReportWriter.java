package com.ffusion.reporting.reportwriter;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.RptException;
import com.ffusion.services.Reporting4;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.PdfWriter;
import java.io.OutputStream;
import java.util.Properties;

public abstract class ReportWriter
{
  private String RO;
  private Reporting4 RR;
  private Document RQ;
  private IPdfWriterHolder RP;
  
  public ReportWriter(ReportCriteria paramReportCriteria, Reporting4 paramReporting4)
  {
    setFormat(paramReportCriteria.getReportOptions().getProperty("FORMAT"));
    setService(paramReporting4);
    if (this.RO.equals("PDF")) {
      this.RQ = new Document();
    }
  }
  
  public void open(ReportMetadata paramReportMetadata)
    throws RptException
  {
    ReportCriteria localReportCriteria = paramReportMetadata.getReportCriteria();
    if ((this.RO != null) && (!this.RO.equals("PDF")) && (!this.RO.equals("CSV"))) {
      jdMethod_int(localReportCriteria);
    }
  }
  
  public void close(ReportMetadata paramReportMetadata)
    throws RptException
  {
    ReportCriteria localReportCriteria = paramReportMetadata.getReportCriteria();
    if ((this.RO != null) && (!this.RO.equals("PDF")) && (!this.RO.equals("CSV"))) {
      jdMethod_for(localReportCriteria);
    }
    Object localObject = this.RR.closeReport(localReportCriteria.getSecureUser(), this.RO, paramReportMetadata, null);
    writeObject(localObject);
  }
  
  protected abstract void writeObject(Object paramObject)
    throws RptException;
  
  public void write(Object paramObject, ReportMetadata paramReportMetadata)
    throws RptException
  {
    SecureUser localSecureUser = null;
    if ((paramReportMetadata != null) && (paramReportMetadata.getReportCriteria() != null)) {
      localSecureUser = paramReportMetadata.getReportCriteria().getSecureUser();
    }
    Object localObject = this.RR.exportPiece(localSecureUser, paramObject, this.RO, paramReportMetadata, null);
    writeObject(localObject);
  }
  
  private void jdMethod_int(ReportCriteria paramReportCriteria)
    throws RptException
  {
    SecureUser localSecureUser = null;
    if (paramReportCriteria != null) {
      localSecureUser = paramReportCriteria.getSecureUser();
    }
    Object localObject = this.RR.exportHeaderOptions(localSecureUser, paramReportCriteria, this.RO, null);
    if (localObject != null) {
      writeObject(localObject);
    }
  }
  
  private void jdMethod_for(ReportCriteria paramReportCriteria)
    throws RptException
  {
    SecureUser localSecureUser = null;
    if (paramReportCriteria != null) {
      localSecureUser = paramReportCriteria.getSecureUser();
    }
    Object localObject = this.RR.exportFooterOptions(localSecureUser, paramReportCriteria, this.RO, null);
    if (localObject != null) {
      writeObject(localObject);
    }
  }
  
  void jdMethod_for(OutputStream paramOutputStream, ReportMetadata paramReportMetadata)
    throws RptException
  {
    try
    {
      PdfWriter localPdfWriter = PdfWriter.getInstance(this.RQ, paramOutputStream);
      if (this.RP != null) {
        this.RP.setPdfWriter(localPdfWriter);
      }
      ReportCriteria localReportCriteria = paramReportMetadata.getReportCriteria();
      this.RR.exportHeaderOptions(localReportCriteria.getSecureUser(), localReportCriteria, "PDF", null);
    }
    catch (DocumentException localDocumentException)
    {
      throw new RptException(200, localDocumentException);
    }
  }
  
  void jdMethod_for(ReportMetadata paramReportMetadata)
    throws RptException
  {
    if (this.RQ != null) {
      this.RQ.close();
    }
  }
  
  public String getFormat()
  {
    return this.RO;
  }
  
  public void setFormat(String paramString)
  {
    this.RO = paramString;
  }
  
  public Reporting4 getService()
  {
    return this.RR;
  }
  
  public void setService(Reporting4 paramReporting4)
  {
    this.RR = paramReporting4;
  }
  
  public Document getPdfDocument()
  {
    return this.RQ;
  }
  
  public void setPdfWriterHolder(IPdfWriterHolder paramIPdfWriterHolder)
  {
    this.RP = paramIPdfWriterHolder;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.reporting.reportwriter.ReportWriter
 * JD-Core Version:    0.7.0.1
 */