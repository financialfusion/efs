package com.ffusion.reporting.reportwriter;

import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.RptException;
import com.ffusion.services.Reporting4;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class ServerFileReportWriter
  extends ReportWriter
{
  private String Tg;
  private Writer Th;
  private OutputStream Ti;
  
  public ServerFileReportWriter(ReportCriteria paramReportCriteria, Reporting4 paramReporting4, String paramString)
    throws RptException
  {
    super(paramReportCriteria, paramReporting4);
    this.Tg = paramString;
  }
  
  public void open(ReportMetadata paramReportMetadata)
    throws RptException
  {
    try
    {
      if (getFormat().equals("PDF"))
      {
        this.Ti = new BufferedOutputStream(new FileOutputStream(this.Tg, false));
        jdMethod_for(this.Ti, paramReportMetadata);
      }
      else
      {
        this.Th = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.Tg), "UTF-8"));
      }
    }
    catch (IOException localIOException)
    {
      throw new RptException(202, localIOException);
    }
    super.open(paramReportMetadata);
  }
  
  public void close(ReportMetadata paramReportMetadata)
    throws RptException
  {
    super.close(paramReportMetadata);
    if (getFormat().equals("PDF")) {
      jdMethod_for(paramReportMetadata);
    }
    try
    {
      if (this.Ti != null)
      {
        this.Ti.close();
        this.Ti = null;
      }
      if (this.Th != null)
      {
        this.Th.close();
        this.Th = null;
      }
    }
    catch (IOException localIOException)
    {
      throw new RptException(202, localIOException);
    }
  }
  
  protected void writeObject(Object paramObject)
    throws RptException
  {
    try
    {
      if (paramObject == null) {
        return;
      }
      if ((paramObject instanceof String))
      {
        if (this.Th == null) {
          throw new RptException(204, paramObject.getClass().getName());
        }
        this.Th.write((String)paramObject);
      }
      else if ((paramObject.getClass().isArray()) && (paramObject.getClass().getComponentType().equals(Byte.TYPE)))
      {
        if (this.Ti == null) {
          throw new RptException(204, paramObject.getClass().getName());
        }
        this.Ti.write((byte[])paramObject);
      }
      else
      {
        throw new RptException(201, paramObject.getClass().getName());
      }
    }
    catch (IOException localIOException)
    {
      throw new RptException(202, localIOException);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.reporting.reportwriter.ServerFileReportWriter
 * JD-Core Version:    0.7.0.1
 */