package com.ffusion.services.reporting;

import com.ffusion.beans.reporting.Image;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.jtf.UrlEncryptor;
import com.ffusion.reporting.RptException;
import com.ffusion.util.Codecs;
import com.ffusion.util.HTMLUtil;
import com.ffusion.util.ILocalizable;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;

public class ReportExporterHTML
  extends ReportExporterBase
{
  protected static final String TEXT_SPACE = " ";
  protected static final String TEXT_TAB = "\t";
  protected static final String QUOTE = "\"";
  protected static final String PERCENT = "%";
  protected static String LINE_SEPARATOR = System.getProperty("line.separator");
  protected static final String HTML_NEWLINE = "<br>";
  protected static final String HTML_SPACE = "&nbsp;";
  protected static final String PARAGRAPH_OPEN = "<p";
  protected static final String PARAGRAPH_CLOSE = "</p>";
  protected static final String BRACKET_CLOSE = ">";
  protected static final String TABLE_OPEN = "<table";
  protected static final String TABLE_CLOSE = "</table>";
  protected static final String TABLE_ROW_OPEN = "<tr>";
  protected static final String TABLE_ROW_CLOSE = "</tr>";
  protected static final String TABLE_DATA_OPEN = "<td";
  protected static final String TABLE_DATA_CLOSE = "</td>";
  protected static final String HTML_OPEN = "<html>";
  protected static final String HTML_CLOSE = "</html>";
  protected static final String HEAD_OPEN = "<head>";
  protected static final String HEAD_CLOSE = "</head>";
  protected static final String TITLE_OPEN = "<title>";
  protected static final String TITLE_CLOSE = "</title>";
  protected static final String BODY_OPEN = "<body>";
  protected static final String BODY_CLOSE = "</body>";
  protected static final String STYLE_OPEN = "<style><!-- ";
  protected static final String STYLE_CLOSE = " --></style>";
  protected static final String DOCTYPE = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD// HTML 4.01 Transitional//EN\">";
  protected static final String METADATA = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">";
  protected static final String DOC_STYLESHEET = "<link rel=\"stylesheet\" type=\"text/css\" href=\"./FF_admin.css\">";
  protected static final String ALIGNMENT = "align=";
  protected static final String WIDTH = "width=";
  protected static final String CLASS = "class=";
  protected static final String COLSPAN = "colspan=";
  protected static final String TABLE_BORDER = "border=";
  protected static final String TABLE_CELLSPACING = "cellspacing=";
  protected static final String TABLE_CELLPADDING = "cellpadding=";
  protected static final String CLASS_REPORT_HEADING = "\"reportHeading\"";
  protected static final String CLASS_REPORT_SECTION_HEADING = "\"reportSectionHeading\"";
  protected static final String CLASS_REPORT_COLUMN_HEADING = "\"reportColumnHeading\"";
  protected static final String CLASS_REPORT_HEADER = "\"reportHeader\"";
  protected static final String CLASS_REPORT_FOOTER = "\"reportFooter\"";
  protected static final String CLASS_REPORT_DATA = "\"reportData\"";
  protected static final String CLASS_REPORT_PARAGRAPH = "\"reportParagraph\"";
  protected static final String ALIGN_CENTER = "\"center\"";
  protected static final String ALIGN_LEFT = "\"left\"";
  protected static final String ALIGN_RIGHT = "\"right\"";
  protected static final String TABLE_WIDTH = "\"100%\"";
  protected static final String HALF_TABLE_WIDTH = "\"50%\"";
  protected static final String TABLE_NO_BORDER = "\"0\"";
  protected static final String TABLE_CELLSPACING_WIDTH = "\"2\"";
  protected static final String TABLE_NO_CELLSPACING = "\"0\"";
  protected static final String TABLE_CELLPADDING_WIDTH = "\"2\"";
  protected static final String TABLE_NO_CELLPADDING = "\"0\"";
  private HashMap jdField_char = new HashMap();
  public static String BOUNDARY_STRING = "==TYSEdfqlek1234SERT123dfgtj.12EFLGheldjferlkjer1Y94df";
  public UrlEncryptor _urlEncryptor = null;
  private boolean jdField_case = false;
  private String jdField_else = null;
  private StringBuffer jdField_byte = null;
  private boolean jdField_try = true;
  
  public ReportExporterHTML(ReportCriteria paramReportCriteria, HashMap paramHashMap, boolean paramBoolean, String paramString)
    throws RptException
  {
    super(paramReportCriteria, paramHashMap);
    this.jdField_case = paramBoolean;
    String str1 = paramReportCriteria.getReportOptions().getProperty("SHOW_ALTERNATE_TEXT_FOR_IMAGE");
    if ((str1 != null) && (str1.equals("FALSE"))) {
      this.jdField_try = false;
    }
    String str2 = paramReportCriteria.getReportOptions().getProperty("PAGEWIDTH");
    int i = 0;
    try
    {
      i = Integer.parseInt(str2);
    }
    catch (Exception localException) {}
    if (i == 0) {
      this.jdField_else = "\"100%\"";
    } else {
      this.jdField_else = ("\"" + i + "\"");
    }
    this._urlEncryptor = ((UrlEncryptor)this._criteria.get("URL_ENCRYPTOR"));
    HashMap localHashMap = (HashMap)this._criteria.get("ReportImageList");
    if (localHashMap != null) {
      this.jdField_char = localHashMap;
    }
    if (this.jdField_case)
    {
      this.jdField_byte.append("Content-Type:multipart/related;boundary=\"").append(BOUNDARY_STRING).append("\"; type=\"text/html\"").append("\r\n").append("\r\n");
      this.jdField_byte.append("--").append(BOUNDARY_STRING).append("\r\n");
      this.jdField_byte.append("Content-Type: text/html;").append("\r\n").append("\r\n");
      this.jdField_byte.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD// HTML 4.01 Transitional//EN\">");
      this.jdField_byte.append("<html>");
      this.jdField_byte.append("<head>");
      this.jdField_byte.append("<title>").append(this._criteria.getReportOptions().getProperty("TITLE")).append("</title>");
      this.jdField_byte.append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">");
      if (paramString != null)
      {
        try
        {
          String str3 = getEOLString();
          BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
          this.jdField_byte.append("<style><!-- ").append(str3);
          for (;;)
          {
            String str4 = localBufferedReader.readLine();
            if (str4 == null) {
              break;
            }
            this.jdField_byte.append(str4).append(str3);
          }
          localBufferedReader.close();
          localBufferedReader = null;
        }
        catch (FileNotFoundException localFileNotFoundException)
        {
          throw new RptException(306, localFileNotFoundException);
        }
        catch (IOException localIOException)
        {
          throw new RptException(306, localIOException);
        }
        this.jdField_byte.append(" --></style>");
      }
      this.jdField_byte.append("</head>");
    }
  }
  
  protected Object doFlushToObject()
    throws RptException
  {
    String str = this.jdField_byte.toString();
    this.jdField_byte.setLength(0);
    return str;
  }
  
  protected String convertDataToString(Object paramObject, String paramString, boolean paramBoolean)
  {
    String str = super.convertDataToString(paramObject, paramString, paramBoolean);
    if (paramObject != null)
    {
      str = HTMLUtil.encode(str);
      str = a(str, LINE_SEPARATOR, "<br>");
      if (paramBoolean)
      {
        str = a(str, "\t", "&nbsp;");
        str = a(str, " ", "&nbsp;");
      }
      if (("com.ffusion.beans.reporting.Image".equals(paramString)) && (paramObject != null)) {
        str = a((Image)paramObject);
      }
    }
    return str;
  }
  
  private static String a(String paramString1, String paramString2, String paramString3)
  {
    int i = 0;
    int j = 0;
    StringBuffer localStringBuffer = new StringBuffer();
    while ((i < paramString1.length()) && ((j = paramString1.indexOf(paramString2, i)) >= 0))
    {
      localStringBuffer.append(paramString1.substring(i, j)).append(paramString3);
      i = j + paramString2.length();
    }
    if (i < paramString1.length()) {
      localStringBuffer.append(paramString1.substring(i));
    }
    return localStringBuffer.toString();
  }
  
  private String a(Image paramImage)
  {
    Locale localLocale = this._criteria.getLocale();
    ILocalizable localILocalizable = paramImage.getAlternateText();
    String str1 = "";
    if ((localILocalizable != null) && (this.jdField_try)) {
      str1 = (String)localILocalizable.localize(localLocale);
    }
    String str2 = this._criteria.getReportOptions().getProperty("SecureServletPath");
    if (str2 == null) {
      str2 = "";
    }
    String str3 = "" + (this.jdField_char.size() + 1);
    String str4 = jdMethod_do("ImageID=" + str3);
    String str5 = str2 + "DisplayImage?" + str4;
    String str6 = "<IMG SRC=\"" + str5 + "\" ALT=\"" + str1 + "\" BORDER=\"1\"/>";
    this.jdField_char.put(this.jdField_case ? str5 : str3, paramImage);
    return str6;
  }
  
  private String jdMethod_do(String paramString)
  {
    if (this._urlEncryptor == null) {
      return paramString;
    }
    return this._urlEncryptor.encrypt(paramString);
  }
  
  protected void doGenerateReportHeader(String paramString1, String paramString2, String paramString3, String paramString4, ReportCriteria paramReportCriteria)
    throws RptException
  {
    try
    {
      if (this.jdField_case)
      {
        this.jdField_byte.append("<body>");
        this.jdField_byte.append("<table").append(" ").append("width=").append(this.jdField_else).append(" ");
        this.jdField_byte.append(jdMethod_for("CENTER")).append(" ");
        this.jdField_byte.append("border=").append("\"0\"").append(" ");
        this.jdField_byte.append("cellspacing=").append("\"2\"").append(" ");
        this.jdField_byte.append("cellpadding=").append("\"0\"").append(" ");
        this.jdField_byte.append(">");
        this.jdField_byte.append("<tr>").append("<td").append(" ");
        this.jdField_byte.append("class=").append("\"").append("tbrd_ltr dkback").append("\"");
        this.jdField_byte.append(">");
      }
      String str1 = jdMethod_for("LEFT");
      this.jdField_byte.append("<table");
      this.jdField_byte.append(" ").append("width=").append(this.jdField_else).append(">");
      if (paramString1 != null)
      {
        this.jdField_byte.append("<tr>");
        this.jdField_byte.append("<td").append(" ");
        this.jdField_byte.append("class=").append("\"reportHeader\"");
        this.jdField_byte.append(" ").append(str1);
        this.jdField_byte.append(" ").append("width=").append("\"100%\"");
        this.jdField_byte.append(" ").append("colspan=").append("\"").append("2").append("\"").append(">");
        this.jdField_byte.append(paramString1).append("</td>");
        this.jdField_byte.append("</tr>");
      }
      if (paramString2 != null)
      {
        this.jdField_byte.append("<tr>");
        this.jdField_byte.append("<td").append(" ");
        this.jdField_byte.append("class=").append("\"reportHeader\"");
        this.jdField_byte.append(" ").append(str1);
        this.jdField_byte.append(" ").append("width=").append("\"100%\"");
        this.jdField_byte.append(" ").append("colspan=").append("\"").append("2").append("\"").append(">");
        this.jdField_byte.append(paramString2).append("</td>");
        this.jdField_byte.append("</tr>");
      }
      if (paramString3 != null)
      {
        this.jdField_byte.append("<tr>");
        this.jdField_byte.append("<td").append(" ");
        this.jdField_byte.append("class=").append("\"reportHeader\"");
        this.jdField_byte.append(" ").append(str1);
        this.jdField_byte.append(" ").append("width=").append("\"100%\"");
        this.jdField_byte.append(" ").append("colspan=").append("\"").append("2").append("\"").append(">");
        this.jdField_byte.append(paramString3);
        if (paramString4 != null) {
          this.jdField_byte.append("&nbsp;");
        } else {
          this.jdField_byte.append("</td>").append("</tr>");
        }
      }
      if (paramString4 != null)
      {
        if (paramString3 == null)
        {
          this.jdField_byte.append("<tr>");
          this.jdField_byte.append("<td").append(" ");
          this.jdField_byte.append("class=").append("\"reportHeader\"");
          this.jdField_byte.append(" ").append(str1);
          this.jdField_byte.append(" ").append("width=").append("\"100%\"");
          this.jdField_byte.append(" ").append("colspan=").append("\"").append("2").append("\"").append(">");
        }
        this.jdField_byte.append(paramString4).append("</td>").append("</tr>");
      }
      this.jdField_byte.append("<tr>");
      this.jdField_byte.append("<td").append(" ").append("colspan=").append("\"").append("2").append("\"").append(">").append("&nbsp;");
      this.jdField_byte.append("</td>").append("</tr>");
      Properties localProperties1 = paramReportCriteria.getSearchCriteria();
      String str2 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
      ArrayList localArrayList1 = paramReportCriteria.getSearchCriteriaOrder();
      Iterator localIterator = localArrayList1.iterator();
      ArrayList localArrayList2 = new ArrayList();
      ArrayList localArrayList3 = new ArrayList();
      String str3;
      String str4;
      String str5;
      while (localIterator.hasNext())
      {
        localObject1 = (String)localIterator.next();
        str3 = localProperties1.getProperty((String)localObject1);
        if ((!((String)localObject1).endsWith("_HIDE")) && (!paramReportCriteria.isSearchCriterionHidden((String)localObject1)) && ((!((String)localObject1).equals("TransactionType")) || (!str3.equals("------------------------------"))))
        {
          str4 = ReportConsts.getCriteriaName(str2, (String)localObject1, this._criteria.getLocale());
          str5 = paramReportCriteria.getDisplayValue((String)localObject1);
          if (str5 == null) {
            if (str3 == null) {
              str5 = new String();
            } else {
              str5 = str3.toString();
            }
          }
          if (str5.length() != 0) {
            if (str5.indexOf("&&") == -1)
            {
              localArrayList2.add(str4);
              localArrayList3.add(str5);
            }
            else
            {
              localObject2 = new StringTokenizer(str5, "&&");
              while (((StringTokenizer)localObject2).hasMoreTokens())
              {
                localArrayList2.add(str4);
                localArrayList3.add(((StringTokenizer)localObject2).nextToken());
              }
            }
          }
        }
      }
      Object localObject1 = localProperties1.propertyNames();
      while (((Enumeration)localObject1).hasMoreElements())
      {
        str3 = (String)((Enumeration)localObject1).nextElement();
        str4 = localProperties1.getProperty(str3);
        if ((!localArrayList1.contains(str3)) && (!str3.endsWith("_HIDE")) && (!paramReportCriteria.isSearchCriterionHidden(str3)))
        {
          str5 = ReportConsts.getCriteriaName(str2, str3, this._criteria.getLocale());
          localObject2 = paramReportCriteria.getDisplayValue(str3);
          if (localObject2 == null) {
            if (str4 == null) {
              localObject2 = new String();
            } else {
              localObject2 = str4.toString();
            }
          }
          if (((String)localObject2).length() != 0) {
            if (((String)localObject2).indexOf("&&") == -1)
            {
              localArrayList2.add(str5);
              localArrayList3.add(localObject2);
            }
            else
            {
              localObject3 = new StringTokenizer((String)localObject2, "&&");
              while (((StringTokenizer)localObject3).hasMoreTokens())
              {
                localArrayList2.add(str5);
                localArrayList3.add(((StringTokenizer)localObject3).nextToken());
              }
            }
          }
        }
      }
      int i = (int)Math.ceil(localArrayList2.size() / 2.0D);
      for (int j = 0; j < i; j++)
      {
        this.jdField_byte.append("<tr>");
        this.jdField_byte.append("<td").append(" ");
        this.jdField_byte.append("class=").append("\"reportHeader\"");
        this.jdField_byte.append(" ").append("width=").append("\"50%\"");
        this.jdField_byte.append(" ").append(str1).append(">");
        this.jdField_byte.append(HTMLUtil.encode((String)localArrayList2.get(j))).append(": ").append(HTMLUtil.encode((String)localArrayList3.get(j)));
        this.jdField_byte.append("</td>");
        if (j + i < localArrayList2.size())
        {
          this.jdField_byte.append("<td").append(" ");
          this.jdField_byte.append("class=").append("\"reportHeader\"");
          this.jdField_byte.append(" ").append("width=").append("\"50%\"");
          this.jdField_byte.append(" ").append(str1).append(">");
          this.jdField_byte.append(HTMLUtil.encode((String)localArrayList2.get(j + i))).append(": ").append(HTMLUtil.encode((String)localArrayList3.get(j + i)));
          this.jdField_byte.append("</td>").append("</tr>");
        }
        else
        {
          this.jdField_byte.append("<td").append(" ");
          this.jdField_byte.append("class=").append("\"reportHeader\"");
          this.jdField_byte.append(" ").append(str1).append(">");
          this.jdField_byte.append("</td>").append("</tr>");
        }
      }
      ReportSortCriteria localReportSortCriteria = paramReportCriteria.getSortCriteria();
      int k = 0;
      Object localObject2 = localReportSortCriteria.iterator();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (ReportSortCriterion)((Iterator)localObject2).next();
        String str6 = ReportConsts.getCriteriaName(str2, ((ReportSortCriterion)localObject3).getName(), this._criteria.getLocale()).trim();
        if (str6.length() != 0) {
          k++;
        }
      }
      if (k > 0)
      {
        this.jdField_byte.append("<tr>");
        this.jdField_byte.append("<td").append(" ");
        this.jdField_byte.append("class=").append("\"reportHeader\"");
        this.jdField_byte.append(" ").append(str1);
        this.jdField_byte.append(" ").append("width=").append("\"100%\"");
        this.jdField_byte.append(" ").append("colspan=").append("\"").append("2").append("\"").append(">");
        this.jdField_byte.append(ReportConsts.getText(10020, this._criteria.getLocale())).append(" ");
        localObject3 = new ArrayList();
        int m = 0;
        int n = 0;
        for (int i1 = 0; n < k; i1++)
        {
          localObject2 = localReportSortCriteria.iterator();
          while (((Iterator)localObject2).hasNext())
          {
            ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)((Iterator)localObject2).next();
            String str9 = ReportConsts.getCriteriaName(str2, localReportSortCriterion.getName(), this._criteria.getLocale()).trim();
            if (str9.length() != 0) {
              if (localReportSortCriterion.getOrdinal() == i1)
              {
                n++;
                if (((ArrayList)localObject3).contains(str9)) {
                  break;
                }
                if (m > 0) {
                  this.jdField_byte.append(", ");
                }
                this.jdField_byte.append(str9);
                ((ArrayList)localObject3).add(str9);
                m++;
                break;
              }
            }
          }
        }
        this.jdField_byte.append("</td>").append("</tr>");
      }
      Object localObject3 = ReportConsts.getText(3250, this._criteria.getLocale());
      Properties localProperties2 = this._criteria.getReportOptions();
      String str7 = localProperties2.getProperty("ORIENTATION", "PORTRAIT");
      String str8 = ReportConsts.getText("PORTRAIT".equals(str7) ? 3252 : 3251, this._criteria.getLocale());
      boolean bool = Boolean.valueOf(localProperties2.getProperty("SHOWORIENTATION", "TRUE")).booleanValue();
      if (bool)
      {
        this.jdField_byte.append("<tr>");
        this.jdField_byte.append("<td").append(" ");
        this.jdField_byte.append("class=").append("\"reportHeader\"");
        this.jdField_byte.append(" ").append(str1);
        this.jdField_byte.append(" ").append("width=").append("\"100%\"");
        this.jdField_byte.append(" ").append("colspan=").append("\"").append("2").append("\"").append(">");
        this.jdField_byte.append((String)localObject3).append(": ").append(str8).append("</td>");
        this.jdField_byte.append("</tr>");
      }
      this.jdField_byte.append("</table>");
      if (this.jdField_case)
      {
        this.jdField_byte.append("</td>").append("</tr>");
        this.jdField_byte.append("<tr>").append("<td").append(" ");
        this.jdField_byte.append("class=").append("\"").append("tbrd_ltr").append("\"");
        this.jdField_byte.append(">");
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doGenerateReportFooter(String paramString)
    throws RptException
  {
    try
    {
      if (this.jdField_case)
      {
        this.jdField_byte.append("</td>").append("</tr>");
        this.jdField_byte.append("<tr>").append("<td").append(" ");
        this.jdField_byte.append("class=").append("\"").append("tbrd_full dkback").append("\"");
        this.jdField_byte.append(">");
      }
      if (paramString != null)
      {
        String str = jdMethod_for("LEFT");
        this.jdField_byte.append("<table");
        this.jdField_byte.append(" ").append("width=").append(this.jdField_else).append(">");
        this.jdField_byte.append("<tr>");
        this.jdField_byte.append("<td").append(" ");
        this.jdField_byte.append("class=").append("\"reportFooter\"");
        this.jdField_byte.append(" ").append(str).append(">");
        this.jdField_byte.append(HTMLUtil.encode(paramString)).append("</td>");
        this.jdField_byte.append("</tr>");
        this.jdField_byte.append("</table>");
      }
      if (this.jdField_case) {
        this.jdField_byte.append("</td>").append("</tr>").append("</table>");
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doGenerateReportResultHeading(String paramString1, String paramString2)
    throws RptException
  {
    try
    {
      String str = jdMethod_for(paramString2);
      this.jdField_byte.append("<p");
      this.jdField_byte.append(" ");
      this.jdField_byte.append("class=").append("\"reportHeading\"");
      this.jdField_byte.append(" ");
      this.jdField_byte.append(str);
      this.jdField_byte.append(" ");
      this.jdField_byte.append(">");
      this.jdField_byte.append(HTMLUtil.encode(paramString1));
      this.jdField_byte.append("</p>");
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doGenerateReportResultSectionHeading(String paramString1, String paramString2)
    throws RptException
  {
    try
    {
      String str = jdMethod_for(paramString2);
      this.jdField_byte.append("<p");
      this.jdField_byte.append(" ");
      this.jdField_byte.append("class=").append("\"reportSectionHeading\"");
      this.jdField_byte.append(" ");
      this.jdField_byte.append(str);
      this.jdField_byte.append(" ");
      this.jdField_byte.append(">");
      this.jdField_byte.append(HTMLUtil.encode(paramString1));
      this.jdField_byte.append("</p>");
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doGenerateReportResultRowData(Object paramObject, String paramString1, float paramFloat, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws RptException
  {
    try
    {
      String str1 = jdMethod_for(paramString2);
      StringBuffer localStringBuffer = new StringBuffer();
      if (this._criteria != null)
      {
        Properties localProperties = this._criteria.getReportOptions();
        String str2 = localProperties.getProperty("PRINTER_READY");
        if ((paramString4 != null) && ((str2 == null) || (!str2.equals("TRUE")))) {
          localStringBuffer.append(" ").append("reportDataOnlineDisplayShaded");
        }
      }
      this.jdField_byte.append("<td");
      this.jdField_byte.append(" ");
      if (paramString3 == null) {
        this.jdField_byte.append("class=").append("\"").append("reportData").append(localStringBuffer).append("\"").append(" ");
      } else {
        this.jdField_byte.append("class=").append("\"").append(paramString3).append(localStringBuffer).append("\"").append(" ");
      }
      this.jdField_byte.append(str1).append(" ");
      this.jdField_byte.append("width=").append("\"").append(paramFloat).append("%").append("\"").append(" ");
      this.jdField_byte.append(">");
      this.jdField_byte.append(convertDataToString(paramObject, paramString1, paramBoolean));
      this.jdField_byte.append("</td>");
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doGenerateReportResultColumnHeading(ArrayList paramArrayList, float paramFloat, String paramString)
    throws RptException
  {
    try
    {
      int i = paramArrayList.size();
      String str = jdMethod_for(paramString);
      this.jdField_byte.append("<td").append(" ");
      this.jdField_byte.append(str).append(" ");
      this.jdField_byte.append("width=").append("\"").append(paramFloat).append("%").append("\"").append(" ");
      this.jdField_byte.append(">");
      this.jdField_byte.append("<table").append(" ").append("width=").append("\"100%\"").append(" ");
      this.jdField_byte.append("class=").append("\"reportColumnHeading\"").append(" ");
      this.jdField_byte.append("border=").append("\"0\"").append(" ");
      this.jdField_byte.append("cellspacing=").append("\"2\"").append(" ");
      this.jdField_byte.append("cellpadding=").append("\"0\"").append(" ");
      this.jdField_byte.append(">");
      for (int j = 0; j < i; j++)
      {
        this.jdField_byte.append("<tr>");
        this.jdField_byte.append("<td").append(" ");
        this.jdField_byte.append(str).append(" ");
        this.jdField_byte.append(">");
        this.jdField_byte.append(HTMLUtil.encode((String)paramArrayList.get(j)));
        this.jdField_byte.append("</td>");
        this.jdField_byte.append("</tr>");
      }
      this.jdField_byte.append("</table>");
      this.jdField_byte.append("</td>");
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doInitReportResultTable()
    throws RptException
  {
    try
    {
      this.jdField_byte.append("<table");
      this.jdField_byte.append(" ").append("width=").append(this.jdField_else).append(" ");
      this.jdField_byte.append("border=").append("\"0\"").append(" ");
      this.jdField_byte.append("cellspacing=").append("\"0\"").append(" ");
      this.jdField_byte.append("cellpadding=").append("\"2\"").append(" ");
      this.jdField_byte.append(">");
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doFiniReportResultTable()
    throws RptException
  {
    try
    {
      this.jdField_byte.append("</table>");
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doInitReportResultRow()
    throws RptException
  {
    try
    {
      this.jdField_byte.append("<tr>");
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doFiniReportResultRow()
    throws RptException
  {
    try
    {
      this.jdField_byte.append("</tr>");
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doWriteBlankLine()
    throws RptException
  {
    try
    {
      this.jdField_byte.append("<br>");
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doWriteParagraph(String paramString)
    throws RptException
  {
    if (paramString == null) {
      return;
    }
    try
    {
      this.jdField_byte.append("<p").append(" ").append("class=").append("\"reportParagraph\"").append(" ").append(">");
      this.jdField_byte.append(HTMLUtil.encode(paramString)).append("</p>");
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
  }
  
  protected void doAddPageBreak()
    throws RptException
  {}
  
  protected void doFiniReport()
    throws RptException
  {
    if (this.jdField_case)
    {
      this.jdField_byte.append("</body>").append("</html>");
      Set localSet = this.jdField_char.keySet();
      Iterator localIterator = localSet.iterator();
      while (localIterator.hasNext())
      {
        String str1 = (String)localIterator.next();
        Image localImage = (Image)this.jdField_char.get(str1);
        this.jdField_byte.append("\r\n").append("\r\n");
        this.jdField_byte.append("--").append(BOUNDARY_STRING).append("\r\n");
        this.jdField_byte.append("Content-Type: ").append(localImage.getImageType()).append("\r\n");
        this.jdField_byte.append("Content-Location: ").append(str1).append("\r\n");
        this.jdField_byte.append("Content-Transfer-Encoding: base64").append("\r\n").append("\r\n");
        String str2 = new String(Codecs.base64Encode(localImage.getImageData()));
        this.jdField_byte.append(str2);
      }
      this.jdField_byte.append("\r\n").append("\r\n").append("--").append(BOUNDARY_STRING).append("--").append("\r\n");
    }
  }
  
  private String jdMethod_for(String paramString)
    throws Exception
  {
    StringBuffer localStringBuffer = new StringBuffer("align=");
    String str = "\"left\"";
    if (paramString != null) {
      if (paramString.equalsIgnoreCase("RIGHT")) {
        str = "\"right\"";
      } else if (paramString.equalsIgnoreCase("CENTER")) {
        str = "\"center\"";
      }
    }
    localStringBuffer.append(str);
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterHTML
 * JD-Core Version:    0.7.0.1
 */