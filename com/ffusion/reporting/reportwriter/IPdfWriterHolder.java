package com.ffusion.reporting.reportwriter;

import com.lowagie.text.pdf.PdfWriter;

public abstract interface IPdfWriterHolder
{
  public abstract void setPdfWriter(PdfWriter paramPdfWriter);
  
  public abstract PdfWriter getPdfWriter();
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.reporting.reportwriter.IPdfWriterHolder
 * JD-Core Version:    0.7.0.1
 */