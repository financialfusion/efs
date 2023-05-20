package com.ffusion.beans.reporting;

import com.ffusion.beans.ExtendABean;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.RptException;
import com.ffusion.reporting.reportwriter.ReportMetadata;
import com.ffusion.reporting.reportwriter.ReportWriter;
import com.ffusion.util.XMLHandler;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Locale;

public class ReportResult
  extends ExtendABean
  implements IReportResult
{
  static final String RY = "REPORT_RESULT";
  public static final String RPT_HEADING = "RPT_HEADING";
  public static final String SECTION_HEADING = "SECTION_HEADING";
  private ReportHeading RU;
  private ReportResults RV;
  private ReportHeading R1;
  private ReportDataDimensions RS;
  private ReportColumns RT;
  private ReportRows RX;
  private HashMap R0;
  private ReportWriter RW;
  private ReportMetadata RZ;
  
  public ReportResult(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void init(ReportCriteria paramReportCriteria)
    throws RptException
  {
    if (paramReportCriteria == null) {
      throw new RptException(111);
    }
    jdMethod_for(paramReportCriteria.getReportWriter());
    if (this.RW != null)
    {
      this.RZ = new ReportMetadata(this.locale);
      this.RZ.setReportCriteria(paramReportCriteria);
      this.RW.open(this.RZ);
    }
  }
  
  public void fini()
    throws RptException
  {
    if (this.RW != null)
    {
      if ((this.R0 != null) && (this.R0.containsKey("TRUNCATED")))
      {
        String str1 = this.R0.get("TRUNCATED").toString();
        String str2 = MessageFormat.format(ReportConsts.getText(10023, this.locale), new Object[] { str1 });
        this.RW.write(str2, this.RZ);
      }
      this.RW.close(this.RZ);
      this.RW = null;
    }
  }
  
  public ReportHeading getHeading()
  {
    return this.RU;
  }
  
  public void setHeading(ReportHeading paramReportHeading)
    throws RptException
  {
    if (this.RW == null)
    {
      this.RU = paramReportHeading;
    }
    else
    {
      if (paramReportHeading != null) {
        paramReportHeading.jdMethod_case(false);
      }
      this.RW.write(paramReportHeading, this.RZ);
    }
  }
  
  public ReportHeading getSectionHeading()
  {
    return this.R1;
  }
  
  public void setSectionHeading(ReportHeading paramReportHeading)
    throws RptException
  {
    if (paramReportHeading != null) {
      paramReportHeading.jdMethod_case(true);
    }
    if (this.RW == null)
    {
      this.R1 = paramReportHeading;
    }
    else
    {
      if (paramReportHeading != null) {
        paramReportHeading.jdMethod_case(true);
      }
      this.RW.write(paramReportHeading, this.RZ);
    }
  }
  
  public ReportDataDimensions getDataDimensions()
  {
    return this.RS;
  }
  
  public void setDataDimensions(ReportDataDimensions paramReportDataDimensions)
  {
    this.RS = paramReportDataDimensions;
    if (this.RZ != null) {
      this.RZ.setReportDataDimensions(paramReportDataDimensions);
    }
  }
  
  public HashMap getProperties()
  {
    return this.R0;
  }
  
  public void setProperties(HashMap paramHashMap)
  {
    this.R0 = paramHashMap;
  }
  
  public ReportRows getRows()
  {
    return this.RX;
  }
  
  public void addRow(ReportRow paramReportRow)
    throws RptException
  {
    if (this.RW == null)
    {
      if (this.RX == null) {
        this.RX = new ReportRows(this.locale);
      }
      this.RX.add(paramReportRow);
    }
    else
    {
      this.RW.write(paramReportRow, this.RZ);
    }
  }
  
  public ReportColumns getColumns()
  {
    return this.RT;
  }
  
  public void addColumn(ReportColumn paramReportColumn)
    throws RptException
  {
    if (this.RT == null) {
      this.RT = new ReportColumns(this.locale);
    }
    this.RT.add(paramReportColumn);
    if (this.RW != null)
    {
      this.RZ.addColumn(paramReportColumn);
      this.RW.write(paramReportColumn, this.RZ);
    }
  }
  
  public ReportResults getSubReports()
  {
    return this.RV;
  }
  
  public void addSubReport(ReportResult paramReportResult)
  {
    if (this.RW == null)
    {
      if (this.RV == null) {
        this.RV = new ReportResults(this.locale);
      }
      this.RV.add(paramReportResult);
    }
    else
    {
      paramReportResult.jdMethod_for(this.RW);
      if (this.RZ != null)
      {
        ReportMetadata localReportMetadata = new ReportMetadata(this.locale);
        localReportMetadata.setReportCriteria(this.RZ.getReportCriteria());
        paramReportResult.RZ = localReportMetadata;
      }
    }
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.RU == null) {
      localStringBuffer.append("rptHeading=null");
    } else {
      localStringBuffer.append("rptHeading=").append(this.RU.toString());
    }
    if (this.RV == null) {
      localStringBuffer.append("subReports=null");
    } else {
      localStringBuffer.append("subReports=").append(this.RV.toString());
    }
    if (this.R1 != null) {
      localStringBuffer.append("sectionHeading=").append(this.R1.toString());
    }
    if (this.RS != null) {
      localStringBuffer.append("dimensions=").append(this.RS.toString());
    }
    if (this.RT != null) {
      localStringBuffer.append("columns=").append(this.RT.toString());
    }
    if (this.RX != null) {
      localStringBuffer.append("rows=").append(this.RX.toString());
    }
    return localStringBuffer.toString();
  }
  
  public int compare(Object paramObject, String paramString)
  {
    ReportResult localReportResult = (ReportResult)paramObject;
    int i = 1;
    if (paramString.equals("RPT_HEADING")) {
      i = this.RU.compareTo(localReportResult.getHeading());
    } else if (paramString.equals("REPORT_RESULTS"))
    {
      if (this.RV.equals(localReportResult.getSubReports())) {
        i = 0;
      }
    }
    else if (paramString.equals("SECTION_HEADING")) {
      i = this.R1.compareTo(localReportResult.getSectionHeading());
    } else if (paramString.equals("REPORT_DATA_DIMENSIONS")) {
      i = this.RS.compareTo(localReportResult.getDataDimensions());
    } else if (paramString.equals("REPORT_COLUMNS"))
    {
      if (this.RT.equals(localReportResult.getColumns())) {
        i = 0;
      }
    }
    else if (paramString.equals("REPORT_ROWS"))
    {
      if (this.RX.equals(localReportResult.getRows())) {
        i = 0;
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "REPORT_RESULT");
    if (this.RU != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "RPT_HEADING");
      localStringBuffer.append(this.RU.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "RPT_HEADING");
    }
    if (this.RV != null) {
      localStringBuffer.append(this.RV.getXML());
    }
    if (this.R1 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "SECTION_HEADING");
      localStringBuffer.append(this.R1.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "SECTION_HEADING");
    }
    if (this.RS != null) {
      localStringBuffer.append(this.RS.getXML());
    }
    if (this.RT != null) {
      localStringBuffer.append(this.RT.getXML());
    }
    if (this.RX != null) {
      localStringBuffer.append(this.RX.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "REPORT_RESULT");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public boolean set(String paramString1, String paramString2, String paramString3)
  {
    return super.set(paramString1, paramString2);
  }
  
  public void setError(Throwable paramThrowable)
  {
    try
    {
      if (this.RW != null)
      {
        String str = ReportConsts.getText(10047, this.locale);
        this.RW.write(str, this.RZ);
      }
    }
    catch (Throwable localThrowable) {}
  }
  
  private void jdMethod_for(ReportWriter paramReportWriter)
  {
    this.RW = paramReportWriter;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    return null;
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("RPT_HEADING"))
      {
        if (ReportResult.this.RU == null) {
          ReportResult.this.RU = new ReportHeading(ReportResult.this.locale);
        }
        ReportResult.this.RU.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("REPORT_RESULTS"))
      {
        if (ReportResult.this.RV == null) {
          ReportResult.this.RV = new ReportResults(ReportResult.this.locale);
        }
        ReportResult.this.RV.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("SECTION_HEADING"))
      {
        if (ReportResult.this.R1 == null) {
          ReportResult.this.R1 = new ReportHeading(ReportResult.this.locale);
        }
        ReportResult.this.R1.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("REPORT_DATA_DIMENSIONS"))
      {
        if (ReportResult.this.RS == null) {
          ReportResult.this.RS = new ReportDataDimensions(ReportResult.this.locale);
        }
        ReportResult.this.RS.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("REPORT_COLUMNS"))
      {
        if (ReportResult.this.RT == null) {
          ReportResult.this.RT = new ReportColumns(ReportResult.this.locale);
        }
        ReportResult.this.RT.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("REPORT_ROWS"))
      {
        if (ReportResult.this.RX == null) {
          ReportResult.this.RX = new ReportRows(ReportResult.this.locale);
        }
        String[] arrayOfString = null;
        if (ReportResult.this.RT != null)
        {
          int i = ReportResult.this.RT.size();
          arrayOfString = new String[i];
          for (int j = 0; j < i; j++)
          {
            arrayOfString[j] = ((ReportColumn)ReportResult.this.RT.get(j)).getDataType();
            if (arrayOfString[j] == null) {
              arrayOfString[j] = "java.lang.String";
            }
          }
        }
        ReportResult.this.RX.jdMethod_for(getHandler(), arrayOfString);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.reporting.ReportResult
 * JD-Core Version:    0.7.0.1
 */