package com.ffusion.reporting.reportwriter;

import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.ReportFileExtensions;
import com.ffusion.reporting.ReportHTTPHeaders;
import com.ffusion.reporting.RptException;
import com.ffusion.services.Reporting4;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class UserFileReportWriter
  extends ReportWriter
  implements ReportHTTPHeaders, ReportFileExtensions
{
  private static final DateFormat Tf = new SimpleDateFormat("yyyyMMdd-HHmmss");
  HttpServletResponse Te;
  ServletOutputStream Td;
  PrintWriter Tc;
  
  public UserFileReportWriter(ReportCriteria paramReportCriteria, Reporting4 paramReporting4, HttpServletResponse paramHttpServletResponse)
    throws RptException
  {
    super(paramReportCriteria, paramReporting4);
    this.Te = paramHttpServletResponse;
  }
  
  public void open(ReportMetadata paramReportMetadata)
    throws RptException
  {
    ReportCriteria localReportCriteria = paramReportMetadata.getReportCriteria();
    String str1 = localReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    if (str1 != null)
    {
      localObject = new StringBuffer(str1.length());
      for (int i = 0; i < str1.length(); i++)
      {
        char c = str1.charAt(i);
        if (!Character.isWhitespace(c)) {
          ((StringBuffer)localObject).append(c);
        }
      }
      str1 = ((StringBuffer)localObject).toString();
    }
    if ((str1 == null) || (str1.length() == 0)) {
      str1 = "report";
    }
    Object localObject = Tf.format(new Date());
    if ((getFormat().equals("COMMA")) || (getFormat().equals("CSV")))
    {
      this.Te.setContentType("application/vnd.ms-excel; charset=UTF-8");
      this.Te.setHeader("Content-Disposition", "attachment;filename=" + str1 + (String)localObject + ".csv");
    }
    else if (getFormat().equals("TAB"))
    {
      this.Te.setContentType("text/plain; charset=UTF-8");
      this.Te.setHeader("Content-Disposition", "attachment;filename=" + str1 + (String)localObject + ".txt");
    }
    else if (getFormat().equals("PDF"))
    {
      this.Te.setContentType("application/pdf");
      this.Te.setHeader("Content-Disposition", "attachment;filename=" + str1 + (String)localObject + ".pdf");
    }
    else if (getFormat().equals("BAI2"))
    {
      this.Te.setContentType("text/plain; charset=UTF-8");
      this.Te.setHeader("Content-Disposition", "attachment;filename=BAI2-" + (String)localObject + ".txt");
    }
    else if (getFormat().equals("TEXT"))
    {
      this.Te.setContentType("text/plain; charset=UTF-8");
      this.Te.setHeader("Content-Disposition", "attachment;filename=" + str1 + (String)localObject + ".txt");
    }
    else if (getFormat().equals("QIF"))
    {
      this.Te.setContentType("application/text; charset=UTF-8");
      this.Te.setHeader("Content-Disposition", "attachment;filename=" + str1 + (String)localObject + ".qif");
    }
    else if (getFormat().equals("QFX"))
    {
      this.Te.setContentType("application/vnd.intu.QFX; charset=UTF-8");
      this.Te.setHeader("Content-Disposition", "attachment;filename=" + str1 + (String)localObject + ".qfx");
    }
    else if (getFormat().equals("QBO"))
    {
      this.Te.setContentType("application/vnd.intu.QBO; charset=UTF-8");
      this.Te.setHeader("Content-Disposition", "attachment;filename=" + str1 + (String)localObject + ".qbo");
    }
    else if (getFormat().equals("IIF"))
    {
      this.Te.setContentType("application/binary; charset=UTF-8");
      this.Te.setHeader("Content-Disposition", "attachment;filename=" + str1 + (String)localObject + ".iif");
    }
    else if (getFormat().equals("OFX"))
    {
      this.Te.setContentType("application/x-ofx; charset=UTF-8");
      this.Te.setHeader("Content-Disposition", "attachment;filename=" + str1 + (String)localObject + ".ofx");
    }
    else
    {
      this.Te.setContentType("text/html; charset=UTF-8");
      this.Te.setHeader("Content-Disposition", "attachment;filename=" + str1 + (String)localObject + ".mhtml");
    }
    try
    {
      String str2 = getFormat();
      if ("PDF".equals(str2))
      {
        this.Td = this.Te.getOutputStream();
        jdMethod_for(this.Td, paramReportMetadata);
      }
      else
      {
        this.Tc = this.Te.getWriter();
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
      if (this.Td != null) {
        this.Td.flush();
      }
      if (this.Tc != null) {
        this.Tc.flush();
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
        this.Tc.print((String)paramObject);
      } else if ((paramObject.getClass().isArray()) && (paramObject.getClass().getComponentType().equals(Byte.TYPE))) {
        this.Td.write((byte[])paramObject);
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
 * Qualified Name:     com.ffusion.reporting.reportwriter.UserFileReportWriter
 * JD-Core Version:    0.7.0.1
 */