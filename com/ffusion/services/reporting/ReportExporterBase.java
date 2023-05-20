package com.ffusion.services.reporting;

import com.ffusion.beans.Currency;
import com.ffusion.beans.reporting.Image;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportColumns;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportDataDimensions;
import com.ffusion.beans.reporting.ReportDataItem;
import com.ffusion.beans.reporting.ReportDataItems;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportResults;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportRows;
import com.ffusion.reporting.RptException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.DateTime;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public abstract class ReportExporterBase
{
  protected static final int TS_NONE = 0;
  protected static final int TS_COLDEF = 1;
  protected static final int TS_OK = 2;
  protected static final int TS_ROW = 3;
  protected static final String DATATYPE_NULL = "";
  protected static final String DEFAULT_DATE_FORMAT = "MM/dd/yyyy";
  protected static final String EOL_STR_LF = "\n";
  protected static final String EOL_STR_CR = "\r";
  protected static final String EOL_STR_CRLF = "\r\n";
  protected static final String DEFAULT_EOL_STR = "\n";
  private boolean a = false;
  private ArrayList jdField_if = null;
  private ArrayList jdField_do = null;
  private ArrayList jdField_for = null;
  protected int _numReportResultColumns = 0;
  protected float[] _columnWidths = null;
  protected ArrayList _columnDataStyles = null;
  protected ArrayList _columnDataForceSpaces = null;
  protected int _tableState = 0;
  protected String _dateFormat = "MM/dd/yyyy";
  protected HashMap _styleMap = null;
  protected String _eolString = "\n";
  protected ReportCriteria _criteria = null;
  
  protected ReportExporterBase(ReportCriteria paramReportCriteria, HashMap paramHashMap)
  {
    if (paramReportCriteria != null)
    {
      Properties localProperties = paramReportCriteria.getReportOptions();
      String str1 = localProperties.getProperty("DATEFORMAT");
      if (str1 != null) {
        this._dateFormat = str1;
      }
      String str2 = localProperties.getProperty("EOL_STYLE");
      if (str2 != null) {
        if (str2.equalsIgnoreCase("LF")) {
          this._eolString = "\n";
        } else if (str2.equalsIgnoreCase("CRLF")) {
          this._eolString = "\r\n";
        }
      }
    }
    this._criteria = paramReportCriteria;
    this._styleMap = paramHashMap;
  }
  
  protected ReportExporterBase(ReportCriteria paramReportCriteria)
  {
    this(paramReportCriteria, null);
  }
  
  public void exportResult(ReportResult paramReportResult)
    throws RptException
  {
    ReportHeading localReportHeading1 = paramReportResult.getHeading();
    if (localReportHeading1 != null) {
      exportReportResultHeading(localReportHeading1);
    }
    ReportResults localReportResults = paramReportResult.getSubReports();
    if (localReportResults != null)
    {
      int i = localReportResults.size();
      for (int j = 0; j < i; j++)
      {
        localObject1 = (ReportResult)localReportResults.get(j);
        exportResult((ReportResult)localObject1);
      }
    }
    ReportHeading localReportHeading2 = paramReportResult.getSectionHeading();
    if (localReportHeading2 != null) {
      exportReportResultSectionHeading(localReportHeading2);
    }
    ReportRows localReportRows = paramReportResult.getRows();
    Object localObject1 = paramReportResult.getColumns();
    ReportDataDimensions localReportDataDimensions = paramReportResult.getDataDimensions();
    int k = 0;
    Object localObject3;
    if (localReportDataDimensions == null)
    {
      if ((localReportRows != null) || (localObject1 != null)) {
        throw new RptException(101);
      }
    }
    else
    {
      k = localReportDataDimensions.getNumColumns();
      if (((k == 0) && (localObject1 != null)) || ((localObject1 == null) && (localReportRows != null))) {
        throw new RptException(102);
      }
      if ((localObject1 != null) && (k != ((ReportColumns)localObject1).size())) {
        throw new RptException(103);
      }
      for (int m = 0; m < k; m++)
      {
        Object localObject2 = ((ReportColumns)localObject1).get(m);
        if ((localObject2 != null) && ((localObject2 instanceof ReportColumn)))
        {
          localObject3 = (ReportColumn)localObject2;
          exportReportColumn((ReportColumn)localObject3);
        }
      }
      if (localReportRows != null)
      {
        m = localReportRows.size();
        for (int n = 0; n < m; n++)
        {
          localObject3 = localReportRows.get(n);
          if ((localObject3 != null) && ((localObject3 instanceof ReportRow)))
          {
            this.a = true;
            ReportRow localReportRow = (ReportRow)localObject3;
            exportReportRow(localReportRow);
          }
        }
      }
      jdField_if();
    }
    HashMap localHashMap = paramReportResult.getProperties();
    if ((localHashMap != null) && (localHashMap.containsKey("TRUNCATED")))
    {
      String str = localHashMap.get("TRUNCATED").toString();
      localObject3 = MessageFormat.format(ReportConsts.getText(10023, paramReportResult.getLocale()), new Object[] { str });
      writeParagraph((String)localObject3);
    }
  }
  
  public void exportReportColumn(ReportColumn paramReportColumn)
    throws RptException
  {
    if ((this._tableState != 0) && (this._tableState != 1)) {
      jdField_if();
    }
    if (paramReportColumn == null) {
      return;
    }
    if (this._tableState == 0)
    {
      this.jdField_if = new ArrayList();
      this._tableState = 1;
    }
    this.jdField_if.add(paramReportColumn);
  }
  
  private void a()
    throws RptException
  {
    if (this._tableState != 1) {
      throw new RptException(300, "exportReportColumns: " + this._tableState);
    }
    this._numReportResultColumns = this.jdField_if.size();
    this.jdField_do = new ArrayList(this._numReportResultColumns);
    this._columnWidths = new float[this._numReportResultColumns];
    this.jdField_for = new ArrayList(this._numReportResultColumns);
    this._columnDataStyles = new ArrayList(this._numReportResultColumns);
    this._columnDataForceSpaces = new ArrayList(this._numReportResultColumns);
    float f = 0.0F;
    int i = 1;
    Object localObject1;
    Object localObject2;
    for (int j = 0; j < this._numReportResultColumns; j++)
    {
      ReportColumn localReportColumn = (ReportColumn)this.jdField_if.get(j);
      this._columnWidths[j] = localReportColumn.getWidthAsPercent();
      f += this._columnWidths[j];
      localObject1 = localReportColumn.getJustification();
      if (localObject1 == null) {
        localObject1 = "LEFT";
      }
      this.jdField_for.add(localObject1);
      localObject2 = localReportColumn.getDataType();
      if (localObject2 == null) {
        localObject2 = "java.lang.String";
      }
      this.jdField_do.add(localObject2);
      String str = localReportColumn.getReportColumnProperty("DATASTYLE");
      this._columnDataStyles.add(str);
      Boolean localBoolean = Boolean.valueOf(localReportColumn.getReportColumnProperty("HTML_FORCE_SPACES"));
      this._columnDataForceSpaces.add(localBoolean);
      if (localReportColumn.getLabels() != null) {
        i = 0;
      }
    }
    for (j = 0; j < this._numReportResultColumns; j++) {
      this._columnWidths[j] = (this._columnWidths[j] / f * 100.0F);
    }
    jdMethod_new();
    if (i != 0) {
      return;
    }
    jdField_for();
    Iterator localIterator = this.jdField_if.iterator();
    for (int k = 0; localIterator.hasNext(); k++)
    {
      localObject1 = (ReportColumn)localIterator.next();
      localObject2 = ((ReportColumn)localObject1).getLabels();
      if (localObject2 == null)
      {
        localObject2 = new ArrayList();
        ((ArrayList)localObject2).add(new String(""));
      }
      else
      {
        int m = ((ArrayList)localObject2).size();
        if (m == 0) {
          ((ArrayList)localObject2).add(new String(""));
        } else {
          for (int n = 0; n < m; n++)
          {
            Object localObject3 = ((ArrayList)localObject2).get(n);
            if (localObject3 == null) {
              ((ArrayList)localObject2).set(n, new String(""));
            } else if (!(localObject3 instanceof String)) {
              ((ArrayList)localObject2).set(n, new String(""));
            }
          }
        }
      }
      a((ArrayList)localObject2, this._columnWidths[k], (String)this.jdField_for.get(k));
    }
    jdMethod_int();
  }
  
  public void exportReportRow(ReportRow paramReportRow)
    throws RptException
  {
    if ((this._tableState == 0) || (this._tableState == 3)) {
      throw new RptException(300, "exportReportRow: " + this._tableState);
    }
    if ((this._tableState != 2) && (this._tableState != 1)) {
      throw new RptException(300, "exportReportRow: " + this._tableState);
    }
    if (paramReportRow.getForcedPageBreak()) {
      addPageBreakInTable();
    }
    if (this._tableState == 1) {
      a();
    }
    ReportDataItems localReportDataItems = paramReportRow.getDataItems();
    if (localReportDataItems == null) {
      throw new RptException(110);
    }
    int i = localReportDataItems.size();
    if (i != this._numReportResultColumns) {
      throw new RptException(106);
    }
    String str = (String)paramReportRow.get("CELLBACKGROUND");
    jdField_for();
    for (int j = 0; j < i; j++)
    {
      ReportDataItem localReportDataItem = (ReportDataItem)localReportDataItems.get(j);
      Object localObject = null;
      if (localReportDataItem != null) {
        localObject = localReportDataItem.getData();
      }
      a(localObject, (String)this.jdField_do.get(j));
      a(localObject, (String)this.jdField_do.get(j), this._columnWidths[j], (String)this.jdField_for.get(j), (String)this._columnDataStyles.get(j), str, ((Boolean)this._columnDataForceSpaces.get(j)).booleanValue());
    }
    jdMethod_int();
  }
  
  public void exportHeader(ReportCriteria paramReportCriteria)
    throws RptException
  {
    if ((this._tableState == 2) || (this._tableState == 1)) {
      jdField_if();
    }
    if (this._tableState != 0) {
      throw new RptException(300, "exportHeader: " + this._tableState);
    }
    if (paramReportCriteria != null)
    {
      Properties localProperties = paramReportCriteria.getReportOptions();
      if (localProperties != null)
      {
        String str1 = null;
        int i = 0;
        String str2 = null;
        str1 = localProperties.getProperty("SHOWTITLE");
        if ((str1 != null) && (str1.equals("TRUE"))) {
          i = 1;
        }
        if (i != 0) {
          str2 = localProperties.getProperty("TITLE");
        }
        int j = 0;
        int k = 0;
        str1 = localProperties.getProperty("SHOWDATE");
        if ((str1 != null) && (str1.equals("TRUE"))) {
          j = 1;
        }
        str1 = localProperties.getProperty("SHOWTIME");
        if ((str1 != null) && (str1.equals("TRUE"))) {
          k = 1;
        }
        Locale localLocale = null;
        if ((localProperties.getProperty("Language") != null) && (localProperties.getProperty("Country") != null)) {
          localLocale = new Locale(localProperties.getProperty("Language"), localProperties.getProperty("Country"));
        }
        String str3 = null;
        String str4 = null;
        DateFormat localDateFormat;
        if ((j != 0) && (k != 0))
        {
          localDateFormat = null;
          localObject = null;
          if (localLocale != null) {
            localDateFormat = DateFormatUtil.getFormatter(localProperties.getProperty("DATEFORMAT"), localLocale);
          } else {
            localDateFormat = DateFormatUtil.getFormatter(localProperties.getProperty("DATEFORMAT"));
          }
          str3 = localDateFormat.format(new Date());
          if (localLocale != null) {
            localObject = DateFormatUtil.getFormatter(localProperties.getProperty("TIMEFORMAT"), localLocale);
          } else {
            localObject = DateFormatUtil.getFormatter(localProperties.getProperty("TIMEFORMAT"));
          }
          str4 = ((DateFormat)localObject).format(new Date());
        }
        else if (j != 0)
        {
          localDateFormat = null;
          if (localLocale != null) {
            localDateFormat = DateFormatUtil.getFormatter(localProperties.getProperty("DATEFORMAT"), localLocale);
          } else {
            localDateFormat = DateFormatUtil.getFormatter(localProperties.getProperty("DATEFORMAT"));
          }
          str3 = localDateFormat.format(new Date());
        }
        else if (k != 0)
        {
          localDateFormat = null;
          if (localLocale != null) {
            localDateFormat = DateFormatUtil.getFormatter(localProperties.getProperty("TIMEFORMAT"), localLocale);
          } else {
            localDateFormat = DateFormatUtil.getFormatter(localProperties.getProperty("TIMEFORMAT"));
          }
          str4 = localDateFormat.format(new Date());
        }
        int m = 0;
        str1 = localProperties.getProperty("SHOWCOMPANYNAME");
        if ((str1 != null) && (str1.equals("TRUE"))) {
          m = 1;
        }
        Object localObject = null;
        if (m != 0) {
          localObject = localProperties.getProperty("COMPANYNAME");
        }
        a(str2, (String)localObject, str3, str4, paramReportCriteria);
      }
    }
  }
  
  public void exportFooter(ReportCriteria paramReportCriteria)
    throws RptException
  {
    if ((this._tableState == 2) || (this._tableState == 1))
    {
      jdField_do();
      jdField_if();
    }
    if (this._tableState != 0) {
      throw new RptException(300, "exportFooter: " + this._tableState);
    }
    if (paramReportCriteria != null)
    {
      Properties localProperties = paramReportCriteria.getReportOptions();
      if (localProperties != null)
      {
        String str1 = localProperties.getProperty("SHOWEXTRAFOOTERLINE");
        int i = 0;
        if ((str1 != null) && (str1.equals("TRUE"))) {
          i = 1;
        }
        String str2 = null;
        if (i != 0) {
          str2 = localProperties.getProperty("EXTRAFOOTERLINE");
        }
        a(str2);
      }
    }
  }
  
  public void exportReportResultHeading(ReportHeading paramReportHeading)
    throws RptException
  {
    if ((this._tableState == 2) || (this._tableState == 1)) {
      jdField_if();
    }
    if (this._tableState != 0) {
      throw new RptException(300, "exportReportResultHeading: " + this._tableState);
    }
    String str1 = paramReportHeading.getLabel();
    if (str1 == null) {
      throw new RptException(105);
    }
    String str2 = paramReportHeading.getJustification();
    if (str2 == null) {
      str2 = "LEFT";
    }
    a(str1, str2);
  }
  
  public void exportReportResultSectionHeading(ReportHeading paramReportHeading)
    throws RptException
  {
    if ((this._tableState == 2) || (this._tableState == 1)) {
      jdField_if();
    }
    if (this._tableState != 0) {
      throw new RptException(300, "exportReportResultSectionHeading: " + this._tableState);
    }
    String str1 = paramReportHeading.getLabel();
    if (str1 == null) {
      throw new RptException(105);
    }
    String str2 = paramReportHeading.getJustification();
    if (str2 == null) {
      str2 = "LEFT";
    }
    jdField_if(str1, str2);
  }
  
  public void writeBlankLine()
    throws RptException
  {
    if ((this._tableState == 2) || (this._tableState == 1)) {
      jdField_if();
    }
    if (this._tableState != 0) {
      throw new RptException(300, "writeBlankLine: " + this._tableState);
    }
    doWriteBlankLine();
  }
  
  public void writeParagraph(String paramString)
    throws RptException
  {
    if ((this._tableState == 2) || (this._tableState == 1)) {
      jdField_if();
    }
    if (this._tableState != 0) {
      throw new RptException(300, "writeParagraph: " + this._tableState);
    }
    doWriteParagraph(paramString);
  }
  
  private void jdField_do()
    throws RptException
  {
    if (!this.a)
    {
      writeBlankLine();
      writeParagraph(ReportConsts.getText(10054, this._criteria.getLocale()));
      this.a = true;
    }
  }
  
  public void finiReport()
    throws RptException
  {
    if ((this._tableState == 2) || (this._tableState == 1)) {
      jdField_if();
    }
    if (this._tableState != 0) {
      throw new RptException(300, "finiReport: " + this._tableState);
    }
    jdField_do();
    doFiniReport();
  }
  
  public Object flushToObject()
    throws RptException
  {
    return doFlushToObject();
  }
  
  protected String convertDataToString(Object paramObject, String paramString)
  {
    if (paramObject == null) {
      return "";
    }
    if (paramString.equals("com.ffusion.util.beans.DateTime")) {
      ((DateTime)paramObject).setFormat(this._dateFormat);
    } else if (paramString.equals("com.ffusion.beans.Currency")) {
      return ((Currency)paramObject).getCurrencyStringNoSymbol_1();
    }
    return paramObject.toString();
  }
  
  protected String convertDataToString(Object paramObject, String paramString, boolean paramBoolean)
  {
    String str = convertDataToString(paramObject, paramString);
    if (!paramBoolean) {
      return jdField_if(str);
    }
    return str;
  }
  
  private String jdField_if(String paramString)
  {
    String str = "";
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    int k;
    char c;
    for (int j = paramString.indexOf("\n", i); (i < paramString.length()) && (j >= 0); j = paramString.indexOf("\n", i))
    {
      str = paramString.substring(i, j);
      for (k = 0; k < str.length(); k++)
      {
        c = str.charAt(k);
        if (!Character.isWhitespace(c))
        {
          if (str.endsWith("\r")) {
            str = str.substring(k, str.length() - 1);
          } else {
            str = str.substring(k);
          }
          localStringBuffer.append(str);
          break;
        }
      }
      localStringBuffer.append(this._eolString);
      i = j + "\n".length();
    }
    if (i < paramString.length())
    {
      str = paramString.substring(i);
      for (k = 0; k < str.length(); k++)
      {
        c = str.charAt(k);
        if (!Character.isWhitespace(c))
        {
          if (str.endsWith("\r")) {
            str = str.substring(k, str.length() - 1);
          } else {
            str = str.substring(k);
          }
          localStringBuffer.append(str);
          break;
        }
      }
    }
    return localStringBuffer.toString();
  }
  
  private void a(Object paramObject, String paramString)
    throws RptException
  {
    if (paramObject == null) {
      return;
    }
    if (paramString.equalsIgnoreCase("java.lang.String"))
    {
      if (!(paramObject instanceof String)) {
        throw new RptException(107);
      }
    }
    else if (paramString.equalsIgnoreCase("java.lang.Boolean"))
    {
      if (!(paramObject instanceof Boolean)) {
        throw new RptException(107);
      }
    }
    else if (paramString.equalsIgnoreCase("java.lang.Character"))
    {
      if (!(paramObject instanceof Character)) {
        throw new RptException(107);
      }
    }
    else if (paramString.equalsIgnoreCase("java.lang.Double"))
    {
      if (!(paramObject instanceof Double)) {
        throw new RptException(107);
      }
    }
    else if (paramString.equalsIgnoreCase("java.lang.Float"))
    {
      if (!(paramObject instanceof Float)) {
        throw new RptException(107);
      }
    }
    else if (paramString.equalsIgnoreCase("java.lang.Integer"))
    {
      if (!(paramObject instanceof Integer)) {
        throw new RptException(107);
      }
    }
    else if (paramString.equalsIgnoreCase("java.lang.Long"))
    {
      if (!(paramObject instanceof Long)) {
        throw new RptException(107);
      }
    }
    else if (paramString.equalsIgnoreCase("java.lang.Short"))
    {
      if (!(paramObject instanceof Short)) {
        throw new RptException(107);
      }
    }
    else if (paramString.equalsIgnoreCase("java.math.BigDecimal"))
    {
      if (!(paramObject instanceof BigDecimal)) {
        throw new RptException(107);
      }
    }
    else if (paramString.equalsIgnoreCase("com.ffusion.util.beans.DateTime"))
    {
      if (!(paramObject instanceof DateTime)) {
        throw new RptException(107);
      }
    }
    else if (paramString.equalsIgnoreCase("com.ffusion.beans.Currency"))
    {
      if (!(paramObject instanceof Currency)) {
        throw new RptException(107);
      }
    }
    else if (paramString.equalsIgnoreCase("com.ffusion.beans.reporting.Image"))
    {
      if (!(paramObject instanceof Image)) {
        throw new RptException(107);
      }
    }
    else {
      throw new RptException(109);
    }
  }
  
  private void jdMethod_new()
    throws RptException
  {
    if (this._tableState != 1) {
      throw new RptException(300, "initReportResultTable: " + this._tableState);
    }
    doInitReportResultTable();
    this._tableState = 2;
  }
  
  private void jdField_if()
    throws RptException
  {
    if (this._tableState == 3) {
      throw new RptException(300, "finiReportResultTable: " + this._tableState);
    }
    if (this._tableState == 1) {
      a();
    }
    if (this._tableState == 2)
    {
      doFiniReportResultTable();
      this._tableState = 0;
    }
  }
  
  private void jdField_for()
    throws RptException
  {
    if (this._tableState != 2) {
      throw new RptException(300, "initReportResultRow: " + this._tableState);
    }
    doInitReportResultRow();
    this._tableState = 3;
  }
  
  private void jdMethod_int()
    throws RptException
  {
    if (this._tableState != 3) {
      throw new RptException(300, "finiReportResultRow: " + this._tableState);
    }
    doFiniReportResultRow();
    this._tableState = 2;
  }
  
  private void a(String paramString1, String paramString2)
    throws RptException
  {
    if (this._tableState != 0) {
      jdField_if();
    }
    doGenerateReportResultHeading(paramString1, paramString2);
  }
  
  private void jdField_if(String paramString1, String paramString2)
    throws RptException
  {
    if (this._tableState != 0) {
      jdField_if();
    }
    doGenerateReportResultSectionHeading(paramString1, paramString2);
  }
  
  private void a(String paramString1, String paramString2, String paramString3, String paramString4, ReportCriteria paramReportCriteria)
    throws RptException
  {
    if (this._tableState != 0) {
      jdField_if();
    }
    doGenerateReportHeader(paramString1, paramString2, paramString3, paramString4, paramReportCriteria);
  }
  
  private void a(String paramString)
    throws RptException
  {
    if (this._tableState != 0)
    {
      jdField_do();
      jdField_if();
    }
    doGenerateReportFooter(paramString);
  }
  
  private void a(ArrayList paramArrayList, float paramFloat, String paramString)
    throws RptException
  {
    if (this._tableState != 3) {
      throw new RptException(300, "generateReportResultColumnHeading: " + this._tableState);
    }
    doGenerateReportResultColumnHeading(paramArrayList, paramFloat, paramString);
  }
  
  private void a(Object paramObject, String paramString1, float paramFloat, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws RptException
  {
    if (this._tableState != 3) {
      throw new RptException(300, "generateReportResultRowData: " + this._tableState);
    }
    doGenerateReportResultRowData(paramObject, paramString1, paramFloat, paramString2, paramString3, paramString4, paramBoolean);
    this.a = true;
  }
  
  protected void addPageBreak()
    throws RptException
  {
    doAddPageBreak();
  }
  
  protected void addPageBreakInTable()
    throws RptException
  {
    int i = this._tableState;
    if (this._tableState == 3) {
      this._tableState = 2;
    }
    if ((this._tableState != 2) && (this._tableState != 1)) {
      throw new RptException(300, "exportReportRow: " + this._tableState);
    }
    jdField_if();
    addPageBreak();
    this._tableState = 1;
    a();
    if (i == 3) {
      this._tableState = i;
    }
  }
  
  public String getEOLString()
  {
    return this._eolString;
  }
  
  protected abstract Object doFlushToObject()
    throws RptException;
  
  protected abstract void doGenerateReportResultHeading(String paramString1, String paramString2)
    throws RptException;
  
  protected abstract void doGenerateReportResultSectionHeading(String paramString1, String paramString2)
    throws RptException;
  
  protected abstract void doGenerateReportHeader(String paramString1, String paramString2, String paramString3, String paramString4, ReportCriteria paramReportCriteria)
    throws RptException;
  
  protected abstract void doGenerateReportFooter(String paramString)
    throws RptException;
  
  protected abstract void doGenerateReportResultColumnHeading(ArrayList paramArrayList, float paramFloat, String paramString)
    throws RptException;
  
  protected abstract void doGenerateReportResultRowData(Object paramObject, String paramString1, float paramFloat, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws RptException;
  
  protected abstract void doInitReportResultTable()
    throws RptException;
  
  protected abstract void doFiniReportResultTable()
    throws RptException;
  
  protected abstract void doInitReportResultRow()
    throws RptException;
  
  protected abstract void doFiniReportResultRow()
    throws RptException;
  
  protected abstract void doWriteBlankLine()
    throws RptException;
  
  protected abstract void doWriteParagraph(String paramString)
    throws RptException;
  
  protected abstract void doAddPageBreak()
    throws RptException;
  
  protected void doFiniReport()
    throws RptException
  {}
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterBase
 * JD-Core Version:    0.7.0.1
 */