package com.ffusion.reporting.reportwriter;

import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.ReportHTTPHeaders;
import com.ffusion.reporting.RptException;
import com.ffusion.services.Reporting4;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class HTTPReportWriter
  extends ReportWriter
  implements ReportHTTPHeaders
{
  HttpServletResponse Tl;
  ServletOutputStream Tk;
  PrintWriter Tj;
  
  public HTTPReportWriter(ReportCriteria paramReportCriteria, Reporting4 paramReporting4, HttpServletResponse paramHttpServletResponse)
    throws RptException
  {
    super(paramReportCriteria, paramReporting4);
    this.Tl = paramHttpServletResponse;
  }
  
  public void open(ReportMetadata paramReportMetadata)
    throws RptException
  {
    if ((getFormat().equals("COMMA")) || (getFormat().equals("CSV")))
    {
      this.Tl.setContentType("text/plain; charset=UTF-8");
      this.Tl.setHeader("Content-Disposition", "inline");
    }
    else if (getFormat().equals("TAB"))
    {
      this.Tl.setContentType("text/plain; charset=UTF-8");
      this.Tl.setHeader("Content-Disposition", "inline");
    }
    else if (getFormat().equals("PDF"))
    {
      this.Tl.setContentType("application/pdf");
      this.Tl.setHeader("Content-Disposition", "inline");
    }
    else if (getFormat().equals("BAI2"))
    {
      this.Tl.setContentType("text/plain; charset=UTF-8");
      this.Tl.setHeader("Content-Disposition", "inline");
    }
    else if (getFormat().equals("TEXT"))
    {
      this.Tl.setContentType("text/plain; charset=UTF-8");
      this.Tl.setHeader("Content-Disposition", "inline");
    }
    else if (getFormat().equals("QIF"))
    {
      this.Tl.setContentType("application/text; charset=UTF-8");
      this.Tl.setHeader("Content-Disposition", "inline");
    }
    else if (getFormat().equals("QFX"))
    {
      this.Tl.setContentType("application/vnd.intu.QFX; charset=UTF-8");
      this.Tl.setHeader("Content-Disposition", "inline");
    }
    else if (getFormat().equals("QBO"))
    {
      this.Tl.setContentType("application/vnd.intu.QBO; charset=UTF-8");
      this.Tl.setHeader("Content-Disposition", "inline");
    }
    else if (getFormat().equals("IIF"))
    {
      this.Tl.setContentType("application/binary; charset=UTF-8");
      this.Tl.setHeader("Content-Disposition", "inline");
    }
    else if (getFormat().equals("OFX"))
    {
      this.Tl.setContentType("application/x-ofx; charset=UTF-8");
      this.Tl.setHeader("Content-Disposition", "inline");
    }
    else
    {
      this.Tl.setContentType("text/html; charset=UTF-8");
      this.Tl.setHeader("Content-Disposition", "inline");
    }
    try
    {
      String str = getFormat();
      if ("PDF".equals(str))
      {
        this.Tk = this.Tl.getOutputStream();
        jdMethod_for(this.Tk, paramReportMetadata);
      }
      else
      {
        this.Tj = this.Tl.getWriter();
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
      if (this.Tk != null) {
        this.Tk.flush();
      }
      if (this.Tj != null) {
        this.Tj.flush();
      }
    }
    catch (IOException localIOException)
    {
      throw new RptException(202, localIOException);
    }
  }
  
  public void writeObject(Object paramObject)
    throws RptException
  {
    try
    {
      if (paramObject == null) {
        return;
      }
      if ((paramObject instanceof String)) {
        this.Tj.print((String)paramObject);
      } else if ((paramObject.getClass().isArray()) && (paramObject.getClass().getComponentType().equals(Byte.TYPE))) {
        this.Tk.write((byte[])paramObject);
      } else {
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
 * Qualified Name:     com.ffusion.reporting.reportwriter.HTTPReportWriter
 * JD-Core Version:    0.7.0.1
 */