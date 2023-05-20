package com.ffusion.services.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.Accounts;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportColumn;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportHeading;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.ReportIdentifications;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.beans.reporting.ReportRow;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementExpression;
import com.ffusion.csil.core.SmartCalendar;
import com.ffusion.reporting.IReportResult;
import com.ffusion.reporting.ReportFileExtensions;
import com.ffusion.reporting.ReportingAdapter;
import com.ffusion.reporting.RptException;
import com.ffusion.reporting.adapter.IReportingAdapter;
import com.ffusion.reporting.reportwriter.HTTPReportWriter;
import com.ffusion.reporting.reportwriter.ReportMetadata;
import com.ffusion.reporting.reportwriter.ReportWriter;
import com.ffusion.reporting.reportwriter.ServerFileReportWriter;
import com.ffusion.reporting.reportwriter.UserFileReportWriter;
import com.ffusion.services.Reporting4;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLTag;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.beans.BankIdentifier;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.http.HttpServletResponse;

public class ReportingService
  implements Reporting4, ExportFormats, ReportFileExtensions
{
  private static final String S2 = ",";
  private static final String S5 = System.getProperty("line.separator");
  private static final String S8 = "yyyyMMdd-HHmmss";
  private static final char[] S3 = { '\\', '/', ',', ':' };
  private static final int S1 = 1024;
  private static final String S6 = "default";
  private IReportingAdapter S7;
  private HashMap S4 = new HashMap();
  private HashMap S0 = new HashMap();
  private HashMap SY = new HashMap();
  private String SZ = null;
  public static final String DIR_USER = "USER";
  public static final String DIR_BANK = "BANK";
  public static final int MAXIMUM_NUMBER_OF_DAYS_IN_SEARCH = 31;
  public static final String ALLOWED_AMOUNT_CHARACTERS = "0123456789 .,";
  public static final String INIT_XML_BASE_NAME = "reporting";
  
  public void initialize()
    throws RptException
  {
    ArrayList localArrayList = null;
    if (this.S7 != null) {
      return;
    }
    localArrayList = LocaleUtil.getXMLFilesWithBase("reporting");
    InputStream localInputStream = null;
    String str1 = null;
    try
    {
      localInputStream = ResourceUtil.getResourceAsStream(this, "reporting.xml");
      str1 = Strings.streamToString(localInputStream, "UTF-8");
    }
    catch (Exception localException1)
    {
      throw new RptException(1, localException1);
    }
    finally
    {
      if (localInputStream != null)
      {
        try
        {
          localInputStream.close();
        }
        catch (Exception localException3) {}
        localInputStream = null;
      }
    }
    XMLTag localXMLTag1 = null;
    String str2 = null;
    try
    {
      localXMLTag1 = new XMLTag(true);
      localXMLTag1.build(str1);
      str1 = null;
    }
    catch (Exception localException2)
    {
      throw new RptException(1, "Error when parsing XML file reporting.xml", localException2);
    }
    XMLTag localXMLTag2 = localXMLTag1.getContainedTag("REPORTINGADAPTER");
    if (localXMLTag2 == null) {
      throw new RptException(1, "No 'REPORTINGADAPTER' tag found in file reporting.xml");
    }
    XMLTag localXMLTag3 = localXMLTag2.getContainedTag("JAVA_CLASS");
    if (localXMLTag3 != null) {
      str2 = localXMLTag3.getTagContent();
    }
    localXMLTag3 = localXMLTag2.getContainedTag("REPORTINGADAPTER_SETTINGS");
    HashMap localHashMap1 = localXMLTag3 == null ? null : XMLUtil.tagToHashMap(localXMLTag3);
    try
    {
      if ((str2 == null) || (str2.length() <= 0))
      {
        DebugLog.log("Reporting adapter class not defined, using default class " + IReportingAdapter.class.getName());
        this.S7 = new ReportingAdapter();
      }
      else
      {
        Class localClass = Class.forName(str2);
        this.S7 = ((IReportingAdapter)localClass.newInstance());
      }
      this.S7.initialize(localHashMap1);
    }
    catch (ClassNotFoundException localClassNotFoundException)
    {
      throw new RptException(1, "Unable to load reporting adapter class", localClassNotFoundException);
    }
    catch (ClassCastException localClassCastException)
    {
      throw new RptException(1, "Class '" + str2 + "' is not of type " + IReportingAdapter.class.getName(), localClassCastException);
    }
    catch (RptException localRptException)
    {
      throw localRptException;
    }
    catch (Exception localException4)
    {
      throw new RptException(1, localException4);
    }
    XMLTag localXMLTag4 = localXMLTag1.getContainedTag("BANK_REPORTS");
    if (localXMLTag4 == null) {
      throw new RptException(1, "No 'BANK_REPORTS' tag found in file reporting.xml");
    }
    HashMap localHashMap2 = XMLUtil.tagToHashMap(localXMLTag4);
    if (!localHashMap2.containsKey("LOCATION")) {
      throw new RptException(1, "No 'LOCATION' tag found in tag 'BANK_REPORTS' in reporting.xml");
    }
    this.SZ = ((String)localHashMap2.get("LOCATION"));
    jdMethod_for("reporting.xml", "reporting", localXMLTag1, true, true, true);
    int i = 0;
    int j = localArrayList.size();
    while (i < j)
    {
      String str3 = (String)localArrayList.get(i);
      try
      {
        str1 = null;
        localInputStream = ResourceUtil.getResourceAsStream(this, str3);
        str1 = Strings.streamToString(localInputStream, "UTF-8");
      }
      catch (Exception localException5)
      {
        throw new RptException(1, localException5);
      }
      finally
      {
        if (localInputStream != null)
        {
          try
          {
            localInputStream.close();
          }
          catch (Exception localException7) {}
          localInputStream = null;
        }
      }
      localXMLTag1 = null;
      try
      {
        localXMLTag1 = new XMLTag(true);
        localXMLTag1.build(str1);
        str1 = null;
      }
      catch (Exception localException6)
      {
        throw new RptException(1, "Error when parsing XML file " + str3, localException6);
      }
      jdMethod_for(str3, "reporting", localXMLTag1, false, false, false);
      i++;
    }
  }
  
  private void jdMethod_for(XMLTag paramXMLTag, String paramString)
    throws RptException
  {
    ArrayList localArrayList1 = paramXMLTag.getContainedTagList();
    if (localArrayList1 == null)
    {
      DebugLog.log("Could not obtain the settings for StyleDefinition. Using default values");
    }
    else
    {
      HashMap localHashMap1 = new HashMap();
      int i = localArrayList1.size();
      for (int j = 0; j < i; j++)
      {
        XMLTag localXMLTag1 = (XMLTag)localArrayList1.get(j);
        if (localXMLTag1 == null)
        {
          DebugLog.log("Could not obtain the value for StyleDefinitionUsing default values.");
        }
        else
        {
          XMLTag localXMLTag2 = localXMLTag1.getContainedTag("Name");
          if (localXMLTag2 == null)
          {
            DebugLog.log("Could not obtain the value for Name from the XML file. Using defaults.");
          }
          else
          {
            String str1 = localXMLTag2.getTagContent();
            XMLTag localXMLTag3 = localXMLTag1.getContainedTag("FormatList");
            if (localXMLTag3 == null)
            {
              DebugLog.log("Could not obtain the values for FormatList Using defaults ");
            }
            else
            {
              ArrayList localArrayList2 = localXMLTag3.getContainedTagList();
              if (localArrayList2 == null)
              {
                DebugLog.log("Could not obtain the values for FormatList Using defaults ");
              }
              else
              {
                int k = localArrayList2.size();
                for (int m = 0; m < k; m++)
                {
                  XMLTag localXMLTag4 = (XMLTag)localArrayList2.get(m);
                  Properties localProperties = XMLUtil.tagToProperties(localXMLTag4);
                  String str2 = localProperties.getProperty("FormatType");
                  if (str2 != null)
                  {
                    HashMap localHashMap2;
                    if (localHashMap1.containsKey(str2))
                    {
                      localHashMap2 = (HashMap)localHashMap1.get(str2);
                    }
                    else
                    {
                      localHashMap2 = new HashMap();
                      localHashMap1.put(str2, localHashMap2);
                    }
                    localHashMap2.put(str1, localProperties);
                  }
                }
              }
            }
          }
        }
      }
      this.S0.put(paramString, localHashMap1);
    }
  }
  
  private void jdMethod_for(XMLTag paramXMLTag, String paramString1, String paramString2)
    throws RptException
  {
    HashMap localHashMap1 = new HashMap();
    boolean bool = "default".equals(paramString1);
    XMLTag localXMLTag1 = paramXMLTag.getContainedTag("REPORTDEFAULTCRITERIA_LIST");
    if (localXMLTag1 == null) {
      throw new RptException(1, "No 'REPORTDEFAULTCRITERIA_LIST' tag found in file " + paramString2);
    }
    ArrayList localArrayList1 = localXMLTag1.getContainedTagList();
    for (int i = 0; i < localArrayList1.size(); i++)
    {
      XMLTag localXMLTag2 = (XMLTag)localArrayList1.get(i);
      if (localXMLTag2.getContainedTag("NAME") == null) {
        throw new RptException(1, "No 'NAME' tag found in file " + paramString2);
      }
      String str = localXMLTag2.getContainedTag("NAME").getTagContent();
      XMLTag localXMLTag3 = localXMLTag2.getContainedTag("REPORTCRITERIA");
      if (localXMLTag3 == null) {
        throw new RptException(1, "No 'REPORTCRITERIA' tag found in file " + paramString2);
      }
      ReportCriteria localReportCriteria = null;
      if (bool) {
        localReportCriteria = new ReportCriteria();
      } else {
        try
        {
          localReportCriteria = getDefaultReportCriteria(str);
        }
        catch (RptException localRptException)
        {
          DebugLog.log("No default report criteria exists for report type \"" + str + "\".");
          return;
        }
      }
      if (localXMLTag3.getContainedTag("ENTITLEMENT_TYPE_LIST") != null)
      {
        localObject1 = localXMLTag3.getContainedTag("ENTITLEMENT_TYPE_LIST");
        localObject2 = getEntitlementExpressionFromXMLTag((XMLTag)localObject1);
        localReportCriteria.setEntitlementExpression((EntitlementExpression)localObject2);
      }
      Object localObject3;
      if (localXMLTag3.getContainedTag("SEARCH_CRITERIA_LIST") != null)
      {
        localObject1 = XMLUtil.tagToHashMap(localXMLTag3.getContainedTag("SEARCH_CRITERIA_LIST"));
        localObject2 = (ArrayList)((HashMap)localObject1).get("CRITERIA");
        if (localObject2 != null) {
          for (int j = 0; j < ((ArrayList)localObject2).size(); j++)
          {
            HashMap localHashMap2 = (HashMap)((ArrayList)localObject2).get(j);
            localObject3 = (String)localHashMap2.get("NAME");
            localReportCriteria.set((String)localObject3, (String)localHashMap2.get("VALUE"), true, false);
            localReportCriteria.addToSearchCriteria((String)localObject3);
          }
        }
      }
      Object localObject1 = new ReportSortCriteria();
      ArrayList localArrayList2;
      int k;
      if (localXMLTag3.getContainedTag("SORT_CRITERIA_LIST") != null)
      {
        localObject2 = XMLUtil.tagToHashMap(localXMLTag3.getContainedTag("SORT_CRITERIA_LIST"));
        localArrayList2 = (ArrayList)((HashMap)localObject2).get("SORT_CRITERION");
        if (localArrayList2 != null) {
          for (k = 0; k < localArrayList2.size(); k++)
          {
            localObject3 = (HashMap)localArrayList2.get(k);
            ((ReportSortCriteria)localObject1).create(Integer.parseInt((String)((HashMap)localObject3).get("ORDINAL")), (String)((HashMap)localObject3).get("CRITERIONNAME"), Boolean.valueOf((String)((HashMap)localObject3).get("ASC")).booleanValue());
          }
        }
      }
      localReportCriteria.setSortCriteria((ReportSortCriteria)localObject1);
      Object localObject2 = localXMLTag3.getContainedTag("REPORT_OPTIONS");
      if (localObject2 != null)
      {
        localArrayList2 = ((XMLTag)localObject2).getContainedTagList();
        if (localArrayList2 != null) {
          for (k = 0; k < localArrayList2.size(); k++)
          {
            localObject3 = (XMLTag)localArrayList2.get(k);
            localReportCriteria.set(((XMLTag)localObject3).getTagName(), ((XMLTag)localObject3).getTagContent(), false, true);
          }
        }
      }
      localHashMap1.put(str, localReportCriteria);
    }
    this.S4.put(paramString1, localHashMap1);
  }
  
  public ReportCriteria getDefaultReportCriteria(String paramString)
    throws RptException
  {
    try
    {
      return (ReportCriteria)((ReportCriteria)((HashMap)this.S4.get("default")).get(paramString)).clone();
    }
    catch (Exception localException)
    {
      throw new RptException(8, "No default report criteria found for report type '" + paramString + "'. tag found in file ");
    }
  }
  
  public ReportCriteria getDefaultReportCriteria(String paramString, Locale paramLocale, HashMap paramHashMap)
    throws RptException
  {
    try
    {
      ReportCriteria localReportCriteria1 = getDefaultReportCriteria(paramString);
      ReportCriteria localReportCriteria2 = null;
      HashMap localHashMap = null;
      if (paramLocale.getLanguage().length() > 0)
      {
        StringBuffer localStringBuffer = new StringBuffer(paramLocale.getLanguage());
        localHashMap = (HashMap)this.S4.get(localStringBuffer.toString());
        if (localHashMap != null)
        {
          localReportCriteria2 = localReportCriteria1;
          localReportCriteria1 = (ReportCriteria)((ReportCriteria)localHashMap.get(paramString)).clone();
          if (localReportCriteria1 != null) {
            localReportCriteria1.setLocale(new Locale(paramLocale.getLanguage(), ""));
          }
          localReportCriteria1 = localReportCriteria1 == null ? localReportCriteria2 : localReportCriteria1;
        }
        if (paramLocale.getCountry().length() > 0)
        {
          localStringBuffer.append('_');
          localStringBuffer.append(paramLocale.getCountry());
          localHashMap = (HashMap)this.S4.get(localStringBuffer.toString());
          if (localHashMap != null)
          {
            localReportCriteria2 = localReportCriteria1;
            localReportCriteria1 = (ReportCriteria)((ReportCriteria)localHashMap.get(paramString)).clone();
            if (localReportCriteria1 != null) {
              localReportCriteria1.setLocale(paramLocale);
            }
            localReportCriteria1 = localReportCriteria1 == null ? localReportCriteria2 : localReportCriteria1;
          }
        }
      }
      return localReportCriteria1;
    }
    catch (Exception localException)
    {
      throw new RptException(8, "No default report criteria found for report type '" + paramString + "'.");
    }
  }
  
  public ReportIdentifications getReports(SecureUser paramSecureUser, HashMap paramHashMap)
    throws RptException
  {
    return this.S7.getReports(paramSecureUser, paramHashMap);
  }
  
  public ReportIdentification getReport(SecureUser paramSecureUser, String paramString, HashMap paramHashMap)
    throws RptException
  {
    return this.S7.getReport(paramSecureUser, paramString, paramHashMap);
  }
  
  public Report getReportCriteria(SecureUser paramSecureUser, ReportIdentification paramReportIdentification)
    throws RptException
  {
    Report localReport = this.S7.getReportCriteria(paramSecureUser, paramReportIdentification);
    ArrayList localArrayList = getDefaultReportCriteria(localReport.getReportCriteria().getReportOptions().getProperty("REPORTTYPE")).getSearchCriteriaOrder();
    localReport.getReportCriteria().setSearchCriteriaOrder(localArrayList);
    return localReport;
  }
  
  public void addReport(SecureUser paramSecureUser, Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    this.S7.addReport(paramSecureUser, paramReport, paramHashMap);
  }
  
  public void modifyReport(SecureUser paramSecureUser, Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    this.S7.modifyReport(paramSecureUser, paramReport, paramHashMap);
  }
  
  public void deleteReport(SecureUser paramSecureUser, ReportIdentification paramReportIdentification, HashMap paramHashMap)
    throws RptException
  {
    this.S7.deleteReport(paramSecureUser, paramReportIdentification, paramHashMap);
  }
  
  public Object exportReport(Report paramReport, String paramString, HashMap paramHashMap)
    throws RptException
  {
    if ((paramString.equals("COMMA")) || (paramString.equals("CSV"))) {
      return jdMethod_case(paramReport, paramHashMap);
    }
    if (paramString.equals("BAI2")) {
      return jdMethod_byte(paramReport, paramHashMap);
    }
    if (paramString.equals("PDF")) {
      return jdMethod_long(paramReport, paramHashMap);
    }
    if (paramString.equals("HTML")) {
      return jdMethod_try(paramReport, paramHashMap);
    }
    if (paramString.equals("TEXT")) {
      return jdMethod_int(paramReport, paramHashMap);
    }
    if (paramString.equals("QIF")) {
      return jdMethod_char(paramReport, paramHashMap);
    }
    if (paramString.equals("QFX")) {
      return jdMethod_for(paramReport, paramHashMap);
    }
    if (paramString.equals("QBO")) {
      return jdMethod_goto(paramReport, paramHashMap);
    }
    if (paramString.equals("IIF")) {
      return jdMethod_else(paramReport, paramHashMap);
    }
    throw new RptException(7);
  }
  
  public Object exportHeaderOptions(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, String paramString, HashMap paramHashMap)
    throws RptException
  {
    ReportExporterBase localReportExporterBase = (ReportExporterBase)paramReportCriteria.getExporter();
    if (localReportExporterBase == null) {
      throw new RptException(10);
    }
    if (paramString.equals("PDF"))
    {
      ReportExporterPDF localReportExporterPDF = (ReportExporterPDF)localReportExporterBase;
      localReportExporterPDF.initPDFHeaderFooter();
      return null;
    }
    if (paramString.equals("CSV")) {
      return null;
    }
    localReportExporterBase.exportHeader(paramReportCriteria);
    return localReportExporterBase.flushToObject();
  }
  
  public Object exportFooterOptions(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, String paramString, HashMap paramHashMap)
    throws RptException
  {
    if ((paramString.equals("PDF")) || (paramString.equals("CSV"))) {
      return null;
    }
    ReportExporterBase localReportExporterBase = (ReportExporterBase)paramReportCriteria.getExporter();
    if (localReportExporterBase == null) {
      throw new RptException(10);
    }
    localReportExporterBase.exportFooter(paramReportCriteria);
    return localReportExporterBase.flushToObject();
  }
  
  public void prepareForReport(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, HashMap paramHashMap)
    throws RptException
  {
    paramReportCriteria.setExporter(null);
    paramReportCriteria.setReportWriter(null);
    Properties localProperties = paramReportCriteria.getReportOptions();
    String str1 = localProperties.getProperty("FORMAT");
    if (str1 == null)
    {
      str1 = "HTML";
      localProperties.setProperty("FORMAT", "HTML");
    }
    String str2 = localProperties.getProperty("DESTINATION");
    if (str2 == null)
    {
      str2 = "OBJECT";
      localProperties.setProperty("DESTINATION", str2);
    }
    Object localObject1 = null;
    HashMap localHashMap = getFormatMap(paramSecureUser);
    Object localObject3;
    String str3;
    if (str1.equals("HTML"))
    {
      boolean bool = !str2.equals("OBJECT");
      localObject3 = null;
      str3 = getCSSDir(paramSecureUser);
      if ((bool) && (str3 != null))
      {
        String str4 = localProperties.getProperty("STYLESHEET_NAME");
        if (str4 != null) {
          localObject3 = str3 + File.separator + str4;
        }
      }
      localObject1 = new ReportExporterHTML(paramReportCriteria, localHashMap.containsKey(str1) ? (HashMap)localHashMap.get(str1) : null, bool, (String)localObject3);
    }
    else if ((str1.equals("COMMA")) || (str1.equals("CSV")))
    {
      localObject1 = new ReportExporterComma(paramReportCriteria);
    }
    else if (str1.equals("TAB"))
    {
      localObject1 = new ReportExporterTab(paramReportCriteria);
    }
    else if (str1.equals("PDF"))
    {
      localObject1 = new ReportExporterPDF(paramReportCriteria, localHashMap.containsKey(str1) ? (HashMap)localHashMap.get(str1) : null);
    }
    else if (str1.equals("BAI2"))
    {
      localObject2 = localProperties.getProperty("REPORTTYPE");
      localObject1 = new ReportExporterBAI(paramReportCriteria, (String)localObject2, paramHashMap);
    }
    else if (str1.equals("TEXT"))
    {
      localObject1 = new ReportExporterText(paramReportCriteria);
    }
    else if (str1.equals("QIF"))
    {
      localObject1 = new ReportExporterQIF(paramReportCriteria, localHashMap.containsKey(str1) ? (HashMap)localHashMap.get(str1) : null, paramHashMap);
    }
    else if ((str1.equals("QFX")) || (str1.equals("QBO")))
    {
      localObject1 = new ReportExporterQFX(paramReportCriteria, localHashMap.containsKey(str1) ? (HashMap)localHashMap.get(str1) : null, paramHashMap);
    }
    else if (str1.equals("IIF"))
    {
      localObject1 = new ReportExporterIIF(paramReportCriteria, localHashMap.containsKey(str1) ? (HashMap)localHashMap.get(str1) : null, paramHashMap);
    }
    else if (str1.equals("OFX"))
    {
      localObject1 = new ReportExporterQFX(paramReportCriteria, localHashMap.containsKey(str1) ? (HashMap)localHashMap.get(str1) : null, paramHashMap);
    }
    paramReportCriteria.setExporter(localObject1);
    if (str2.equals("OBJECT")) {
      return;
    }
    Object localObject2 = null;
    if ((str2.equals("HTTP")) || (str2.equals("USER_FILE_SYSTEM")))
    {
      localObject3 = paramReportCriteria.getHttpServletResponse();
      if (localObject3 == null) {
        throw new RptException(203);
      }
      if (str2.equals("HTTP")) {
        localObject2 = new HTTPReportWriter(paramReportCriteria, this, (HttpServletResponse)localObject3);
      } else {
        localObject2 = new UserFileReportWriter(paramReportCriteria, this, (HttpServletResponse)localObject3);
      }
    }
    else if (str2.equals("SERVER_FILE_SYSTEM"))
    {
      localObject3 = localProperties.getProperty("FILENAME");
      if (localObject3 == null) {
        throw new RptException(11);
      }
      if (!w((String)localObject3)) {
        throw new RptException(12, (String)localObject3);
      }
      str3 = getReportDirectory(paramSecureUser, "USER", true) + File.separator + (String)localObject3 + "-" + DateFormatUtil.getFormatter("yyyyMMdd-HHmmss").format(new Date()) + v(str1);
      localObject2 = new ServerFileReportWriter(paramReportCriteria, this, str3);
    }
    if (str1.equals("PDF"))
    {
      ((ReportExporterPDF)localObject1).setPDFDocument(((ReportWriter)localObject2).getPdfDocument());
      ((ReportWriter)localObject2).setPdfWriterHolder((ReportExporterPDF)localObject1);
    }
    paramReportCriteria.setSecureUser(paramSecureUser);
    paramReportCriteria.setReportWriter((ReportWriter)localObject2);
  }
  
  public Object exportPiece(SecureUser paramSecureUser, Object paramObject1, String paramString, Object paramObject2, HashMap paramHashMap)
    throws RptException
  {
    if (paramObject1 == null) {
      return null;
    }
    Object localObject1 = null;
    if (paramObject2 == null) {
      throw new RptException(301);
    }
    if (!(paramObject2 instanceof ReportMetadata)) {
      throw new RptException(302);
    }
    ReportMetadata localReportMetadata = (ReportMetadata)paramObject2;
    Object localObject2 = localReportMetadata.getReportCriteria().getExporter();
    if (localObject2 == null) {
      throw new RptException(303, localReportMetadata.getClass().getName());
    }
    if (!(localObject2 instanceof ReportExporterBase)) {
      throw new RptException(304, localObject2.getClass().getName());
    }
    ReportExporterBase localReportExporterBase = (ReportExporterBase)localObject2;
    if ((paramObject1 instanceof ReportHeading))
    {
      ReportHeading localReportHeading = (ReportHeading)paramObject1;
      if (localReportHeading.isSectionHeading()) {
        localReportExporterBase.exportReportResultSectionHeading(localReportHeading);
      } else {
        localReportExporterBase.exportReportResultHeading(localReportHeading);
      }
    }
    else if ((paramObject1 instanceof ReportColumn))
    {
      localReportExporterBase.exportReportColumn((ReportColumn)paramObject1);
    }
    else if ((paramObject1 instanceof ReportRow))
    {
      localReportExporterBase.exportReportRow((ReportRow)paramObject1);
    }
    else if ((paramObject1 instanceof String))
    {
      localReportExporterBase.writeParagraph((String)paramObject1);
    }
    else
    {
      throw new RptException(305, paramObject1.getClass().getName());
    }
    localObject1 = localReportExporterBase.flushToObject();
    return localObject1;
  }
  
  public Object closeReport(SecureUser paramSecureUser, String paramString, Object paramObject, HashMap paramHashMap)
    throws RptException
  {
    Object localObject1 = null;
    if (paramObject == null) {
      throw new RptException(301);
    }
    if (!(paramObject instanceof ReportMetadata)) {
      throw new RptException(302);
    }
    ReportMetadata localReportMetadata = (ReportMetadata)paramObject;
    Object localObject2 = localReportMetadata.getReportCriteria().getExporter();
    if (localObject2 == null) {
      throw new RptException(303, localReportMetadata.getClass().getName());
    }
    if (!(localObject2 instanceof ReportExporterBase)) {
      throw new RptException(304, localObject2.getClass().getName());
    }
    ReportExporterBase localReportExporterBase = (ReportExporterBase)localObject2;
    localReportExporterBase.finiReport();
    localObject1 = localReportExporterBase.flushToObject();
    return localObject1;
  }
  
  public void deleteUser(int paramInt1, int paramInt2, HashMap paramHashMap)
    throws RptException
  {
    this.S7.deleteUser(paramInt1, paramInt2, paramHashMap);
  }
  
  private StringBuffer jdMethod_case(Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ReportExporterComma localReportExporterComma = (ReportExporterComma)paramReport.getReportCriteria().getExporter();
    try
    {
      IReportResult localIReportResult = paramReport.getReportResult();
      if (localIReportResult != null) {
        if ((localIReportResult instanceof ReportResult))
        {
          localReportExporterComma.exportResult((ReportResult)localIReportResult);
          localReportExporterComma.finiReport();
          localStringBuffer.append((String)localReportExporterComma.flushToObject());
        }
        else
        {
          Object localObject = localIReportResult.export("COMMA", paramHashMap);
          if ((localObject != null) && ((localObject instanceof StringBuffer))) {
            localStringBuffer.append(localObject.toString()).append(S5);
          }
        }
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
    return localStringBuffer;
  }
  
  private StringBuffer jdMethod_new(Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ReportExporterTab localReportExporterTab = (ReportExporterTab)paramReport.getReportCriteria().getExporter();
    try
    {
      IReportResult localIReportResult = paramReport.getReportResult();
      if (localIReportResult != null) {
        if ((localIReportResult instanceof ReportResult))
        {
          localReportExporterTab.exportResult((ReportResult)localIReportResult);
          localReportExporterTab.finiReport();
          localStringBuffer.append((String)localReportExporterTab.flushToObject());
        }
        else
        {
          Object localObject = localIReportResult.export("TAB", paramHashMap);
          if ((localObject != null) && ((localObject instanceof StringBuffer))) {
            localStringBuffer.append(localObject.toString()).append(S5);
          }
        }
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
    return localStringBuffer;
  }
  
  private byte[] jdMethod_long(Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    Document localDocument = null;
    ByteArrayOutputStream localByteArrayOutputStream = null;
    PdfWriter localPdfWriter = null;
    try
    {
      IReportResult localIReportResult = paramReport.getReportResult();
      if (localIReportResult != null)
      {
        Object localObject1;
        if ((localIReportResult instanceof ReportResult))
        {
          localObject1 = paramReport.getReportCriteria();
          localDocument = new Document();
          localByteArrayOutputStream = new ByteArrayOutputStream();
          localPdfWriter = PdfWriter.getInstance(localDocument, localByteArrayOutputStream);
          ReportExporterPDF localReportExporterPDF = (ReportExporterPDF)((ReportCriteria)localObject1).getExporter();
          localReportExporterPDF.setPDFDocument(localDocument);
          localReportExporterPDF.setPdfWriter(localPdfWriter);
          localReportExporterPDF.initPDFHeaderFooter();
          localReportExporterPDF.exportResult((ReportResult)localIReportResult);
          localReportExporterPDF.finiReport();
        }
        else
        {
          localObject1 = localIReportResult.export("PDF", paramHashMap);
        }
      }
    }
    catch (Exception localException1)
    {
      if ((localException1 instanceof RptException)) {
        throw ((RptException)localException1);
      }
      throw new RptException(100, localException1);
    }
    finally
    {
      try
      {
        if ((localDocument != null) && (localDocument.isOpen())) {
          localDocument.close();
        }
        if (localPdfWriter != null) {
          localPdfWriter.close();
        }
      }
      catch (Exception localException2) {}
    }
    if (localByteArrayOutputStream != null) {
      return localByteArrayOutputStream.toByteArray();
    }
    return null;
  }
  
  private StringBuffer jdMethod_try(Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    StringBuffer localStringBuffer = null;
    try
    {
      ReportExporterHTML localReportExporterHTML = (ReportExporterHTML)paramReport.getReportCriteria().getExporter();
      localStringBuffer = new StringBuffer();
      ReportCriteria localReportCriteria = paramReport.getReportCriteria();
      IReportResult localIReportResult = paramReport.getReportResult();
      if (localIReportResult != null) {
        if ((localIReportResult instanceof ReportResult))
        {
          localReportExporterHTML.exportResult((ReportResult)localIReportResult);
          localReportExporterHTML.finiReport();
          localStringBuffer.append((String)localReportExporterHTML.flushToObject());
        }
        else
        {
          Object localObject = localIReportResult.export("HTML", paramHashMap);
        }
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
    return localStringBuffer;
  }
  
  private StringBuffer jdMethod_byte(Report paramReport, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = new StringBuffer(1024);
    IReportResult localIReportResult = paramReport.getReportResult();
    if (localIReportResult != null)
    {
      Object localObject = localIReportResult.export("BAI2", paramHashMap);
      if ((localObject != null) && ((localObject instanceof StringBuffer))) {
        localStringBuffer.append(localObject.toString()).append(S5);
      }
    }
    return localStringBuffer;
  }
  
  private StringBuffer jdMethod_int(Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ReportExporterBase localReportExporterBase = (ReportExporterBase)paramReport.getReportCriteria().getExporter();
    try
    {
      IReportResult localIReportResult = paramReport.getReportResult();
      if (localIReportResult != null) {
        if ((localIReportResult instanceof ReportResult))
        {
          localReportExporterBase.exportResult((ReportResult)localIReportResult);
          localReportExporterBase.finiReport();
          localStringBuffer.append((String)localReportExporterBase.flushToObject());
        }
        else
        {
          Object localObject = localIReportResult.export("TAB", paramHashMap);
          if ((localObject != null) && ((localObject instanceof StringBuffer))) {
            localStringBuffer.append(localObject.toString()).append(S5);
          }
        }
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
    return localStringBuffer;
  }
  
  private StringBuffer jdMethod_char(Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ReportExporterBase localReportExporterBase = (ReportExporterBase)paramReport.getReportCriteria().getExporter();
    try
    {
      IReportResult localIReportResult = paramReport.getReportResult();
      if (localIReportResult != null) {
        if ((localIReportResult instanceof ReportResult))
        {
          localReportExporterBase.exportResult((ReportResult)localIReportResult);
          localReportExporterBase.finiReport();
          localStringBuffer.append((String)localReportExporterBase.flushToObject());
        }
        else
        {
          Object localObject = localIReportResult.export("QIF", paramHashMap);
          if ((localObject != null) && ((localObject instanceof StringBuffer))) {
            localStringBuffer.append(localObject.toString()).append(S5);
          }
        }
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
    return localStringBuffer;
  }
  
  private StringBuffer jdMethod_for(Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    return jdMethod_for(paramReport, paramHashMap, "QFX");
  }
  
  private StringBuffer jdMethod_goto(Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    return jdMethod_for(paramReport, paramHashMap, "QBO");
  }
  
  private StringBuffer jdMethod_for(Report paramReport, HashMap paramHashMap, String paramString)
    throws RptException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ReportExporterBase localReportExporterBase = (ReportExporterBase)paramReport.getReportCriteria().getExporter();
    try
    {
      IReportResult localIReportResult = paramReport.getReportResult();
      if (localIReportResult != null) {
        if ((localIReportResult instanceof ReportResult))
        {
          localReportExporterBase.exportResult((ReportResult)localIReportResult);
          localReportExporterBase.finiReport();
          localStringBuffer.append((String)localReportExporterBase.flushToObject());
        }
        else
        {
          Object localObject = localIReportResult.export(paramString, paramHashMap);
          if ((localObject != null) && ((localObject instanceof StringBuffer))) {
            localStringBuffer.append(localObject.toString()).append(S5);
          }
        }
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
    return localStringBuffer;
  }
  
  private StringBuffer jdMethod_else(Report paramReport, HashMap paramHashMap)
    throws RptException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    ReportExporterBase localReportExporterBase = (ReportExporterBase)paramReport.getReportCriteria().getExporter();
    try
    {
      IReportResult localIReportResult = paramReport.getReportResult();
      if (localIReportResult != null) {
        if ((localIReportResult instanceof ReportResult))
        {
          localReportExporterBase.exportResult((ReportResult)localIReportResult);
          localReportExporterBase.finiReport();
          localStringBuffer.append((String)localReportExporterBase.flushToObject());
        }
        else
        {
          Object localObject = localIReportResult.export("IIF", paramHashMap);
          if ((localObject != null) && ((localObject instanceof StringBuffer))) {
            localStringBuffer.append(localObject.toString()).append(S5);
          }
        }
      }
    }
    catch (Exception localException)
    {
      if ((localException instanceof RptException)) {
        throw ((RptException)localException);
      }
      throw new RptException(100, localException);
    }
    return localStringBuffer;
  }
  
  public void calculateDateRange(SecureUser paramSecureUser, Accounts paramAccounts, ReportCriteria paramReportCriteria, HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3)
    throws RptException
  {
    if (paramSecureUser == null) {
      throw new RptException(15);
    }
    if (paramReportCriteria == null) {
      throw new RptException(9);
    }
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    if (localProperties == null)
    {
      localObject = new RptException(111);
      DebugLog.throwing("Search criteria is null.", (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    Object localObject = localProperties.getProperty("Date Range Type");
    if (localObject == null) {
      localObject = "Absolute";
    }
    if ((!"Absolute".equalsIgnoreCase((String)localObject)) && (!"Relative".equalsIgnoreCase((String)localObject)) && (!"Since Last Export".equalsIgnoreCase((String)localObject)))
    {
      RptException localRptException = new RptException(112);
      DebugLog.throwing("Date range type is not Absolute, Relative, or Since Last Export", localRptException);
      throw localRptException;
    }
    if ("Absolute".equalsIgnoreCase((String)localObject))
    {
      jdMethod_for(paramReportCriteria, paramAccounts, paramHashMap1, paramHashMap2, paramHashMap3);
      paramReportCriteria.setHiddenSearchCriterion("Date Range Type", true);
      paramReportCriteria.setHiddenSearchCriterion("Date Range", true);
    }
    else if ("Relative".equalsIgnoreCase((String)localObject))
    {
      jdMethod_int(paramSecureUser, paramReportCriteria, paramAccounts, paramHashMap1, paramHashMap2, paramHashMap3);
      paramReportCriteria.setHiddenSearchCriterion("Date Range Type", true);
    }
    else if ("Since Last Export".equalsIgnoreCase((String)localObject))
    {
      jdMethod_for(paramSecureUser, paramReportCriteria, paramAccounts, paramHashMap1, paramHashMap2, paramHashMap3);
      paramReportCriteria.setHiddenSearchCriterion("Date Range Type", true);
      paramReportCriteria.setHiddenSearchCriterion("Date Range", true);
      paramReportCriteria.setHiddenSearchCriterion("EndDate", true);
    }
    paramReportCriteria.setHiddenSearchCriterion("StartTime", true);
    paramReportCriteria.setHiddenSearchCriterion("EndTime", true);
    paramReportCriteria.setHiddenSearchCriterion("TimeFormat", true);
    paramReportCriteria.setHiddenSearchCriterion("DateFormat", true);
  }
  
  private void jdMethod_for(String paramString, Calendar paramCalendar1, Calendar paramCalendar2, HashMap paramHashMap)
    throws RptException
  {
    if ((paramString.equalsIgnoreCase("Current Day")) || (paramString.equalsIgnoreCase("Today(new)")))
    {
      e(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Yesterday"))
    {
      jdMethod_try(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Current Week"))
    {
      jdMethod_case(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Current 4 Weeks"))
    {
      d(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Current 7 Days"))
    {
      jdMethod_byte(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Last Week"))
    {
      c(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Last 4 Weeks"))
    {
      jdMethod_int(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Last 7 Days"))
    {
      f(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Last 30 Days"))
    {
      h(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Last Month"))
    {
      jdMethod_new(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Next Week"))
    {
      jdMethod_else(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Next 4 Weeks"))
    {
      jdMethod_long(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Next Month"))
    {
      g(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Current 30 Days"))
    {
      jdMethod_void(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Last 60 Days"))
    {
      jdMethod_char(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Last 90 Days"))
    {
      b(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("Current Month"))
    {
      jdMethod_goto(paramCalendar1, paramCalendar2);
      return;
    }
    if (paramString.equalsIgnoreCase("CurrentMonthIncFuture"))
    {
      jdMethod_null(paramCalendar1, paramCalendar2);
      return;
    }
    throw new RptException(115);
  }
  
  private void g(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    localCalendar.add(2, 1);
    localCalendar.set(5, 1);
    paramCalendar1.setTime(localCalendar.getTime());
    localCalendar.set(5, localCalendar.getActualMaximum(5));
    paramCalendar2.setTime(localCalendar.getTime());
  }
  
  private void d(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    localCalendar.add(5, -21);
    switch (localCalendar.get(7))
    {
    case 7: 
      localCalendar.add(5, -1);
    case 6: 
      localCalendar.add(5, -1);
    case 5: 
      localCalendar.add(5, -1);
    case 4: 
      localCalendar.add(5, -1);
    case 3: 
      localCalendar.add(5, -1);
    case 2: 
      localCalendar.add(5, -1);
    case 1: 
      paramCalendar1.setTime(localCalendar.getTime());
    }
    localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    switch (localCalendar.get(7))
    {
    case 1: 
      localCalendar.add(5, 1);
    case 2: 
      localCalendar.add(5, 1);
    case 3: 
      localCalendar.add(5, 1);
    case 4: 
      localCalendar.add(5, 1);
    case 5: 
      localCalendar.add(5, 1);
    case 6: 
      localCalendar.add(5, 1);
    case 7: 
      paramCalendar2.setTime(localCalendar.getTime());
    }
  }
  
  private void jdMethod_case(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    switch (localCalendar.get(7))
    {
    case 7: 
      localCalendar.add(5, -1);
    case 6: 
      localCalendar.add(5, -1);
    case 5: 
      localCalendar.add(5, -1);
    case 4: 
      localCalendar.add(5, -1);
    case 3: 
      localCalendar.add(5, -1);
    case 2: 
      localCalendar.add(5, -1);
    case 1: 
      paramCalendar1.setTime(localCalendar.getTime());
    }
    localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    switch (localCalendar.get(7))
    {
    case 1: 
      localCalendar.add(5, 1);
    case 2: 
      localCalendar.add(5, 1);
    case 3: 
      localCalendar.add(5, 1);
    case 4: 
      localCalendar.add(5, 1);
    case 5: 
      localCalendar.add(5, 1);
    case 6: 
      localCalendar.add(5, 1);
    case 7: 
      paramCalendar2.setTime(localCalendar.getTime());
    }
  }
  
  private void jdMethod_long(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    paramCalendar1.setTime(localCalendar.getTime());
    localCalendar.add(5, 27);
    paramCalendar2.setTime(localCalendar.getTime());
  }
  
  private void jdMethod_else(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    localCalendar.add(5, 7);
    Date localDate = localCalendar.getTime();
    switch (localCalendar.get(7))
    {
    case 7: 
      localCalendar.add(5, -1);
    case 6: 
      localCalendar.add(5, -1);
    case 5: 
      localCalendar.add(5, -1);
    case 4: 
      localCalendar.add(5, -1);
    case 3: 
      localCalendar.add(5, -1);
    case 2: 
      localCalendar.add(5, -1);
    case 1: 
      paramCalendar1.setTime(localCalendar.getTime());
    }
    localCalendar.setTime(localDate);
    switch (localCalendar.get(7))
    {
    case 1: 
      localCalendar.add(5, 1);
    case 2: 
      localCalendar.add(5, 1);
    case 3: 
      localCalendar.add(5, 1);
    case 4: 
      localCalendar.add(5, 1);
    case 5: 
      localCalendar.add(5, 1);
    case 6: 
      localCalendar.add(5, 1);
    case 7: 
      paramCalendar2.setTime(localCalendar.getTime());
    }
  }
  
  private void jdMethod_new(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    localCalendar.add(2, -1);
    localCalendar.set(5, 1);
    paramCalendar1.setTime(localCalendar.getTime());
    localCalendar.set(5, localCalendar.getActualMaximum(5));
    paramCalendar2.setTime(localCalendar.getTime());
  }
  
  private void jdMethod_int(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    localCalendar.add(5, -28);
    switch (localCalendar.get(7))
    {
    case 7: 
      localCalendar.add(5, -1);
    case 6: 
      localCalendar.add(5, -1);
    case 5: 
      localCalendar.add(5, -1);
    case 4: 
      localCalendar.add(5, -1);
    case 3: 
      localCalendar.add(5, -1);
    case 2: 
      localCalendar.add(5, -1);
    case 1: 
      paramCalendar1.setTime(localCalendar.getTime());
    }
    localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    localCalendar.add(5, -7);
    switch (localCalendar.get(7))
    {
    case 1: 
      localCalendar.add(5, 1);
    case 2: 
      localCalendar.add(5, 1);
    case 3: 
      localCalendar.add(5, 1);
    case 4: 
      localCalendar.add(5, 1);
    case 5: 
      localCalendar.add(5, 1);
    case 6: 
      localCalendar.add(5, 1);
    case 7: 
      paramCalendar2.setTime(localCalendar.getTime());
    }
  }
  
  private void c(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    localCalendar.add(5, -7);
    Date localDate = localCalendar.getTime();
    switch (localCalendar.get(7))
    {
    case 7: 
      localCalendar.add(5, -1);
    case 6: 
      localCalendar.add(5, -1);
    case 5: 
      localCalendar.add(5, -1);
    case 4: 
      localCalendar.add(5, -1);
    case 3: 
      localCalendar.add(5, -1);
    case 2: 
      localCalendar.add(5, -1);
    case 1: 
      paramCalendar1.setTime(localCalendar.getTime());
    }
    localCalendar.setTime(localDate);
    switch (localCalendar.get(7))
    {
    case 1: 
      localCalendar.add(5, 1);
    case 2: 
      localCalendar.add(5, 1);
    case 3: 
      localCalendar.add(5, 1);
    case 4: 
      localCalendar.add(5, 1);
    case 5: 
      localCalendar.add(5, 1);
    case 6: 
      localCalendar.add(5, 1);
    case 7: 
      paramCalendar2.setTime(localCalendar.getTime());
    }
  }
  
  private void f(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.setTime(new Date());
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar1.add(5, -7);
    localCalendar2.add(5, -1);
    paramCalendar1.setTime(localCalendar1.getTime());
    paramCalendar2.setTime(localCalendar2.getTime());
  }
  
  private void h(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.setTime(new Date());
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar1.add(5, -30);
    localCalendar2.add(5, -1);
    paramCalendar1.setTime(localCalendar1.getTime());
    paramCalendar2.setTime(localCalendar2.getTime());
  }
  
  private void jdMethod_void(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.setTime(new Date());
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar1.add(5, -29);
    paramCalendar1.setTime(localCalendar1.getTime());
    paramCalendar2.setTime(localCalendar2.getTime());
  }
  
  private void jdMethod_byte(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.setTime(new Date());
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar1.add(5, -6);
    paramCalendar1.setTime(localCalendar1.getTime());
    paramCalendar2.setTime(localCalendar2.getTime());
  }
  
  private void e(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    paramCalendar1.setTime(new Date());
    paramCalendar2.setTime(paramCalendar1.getTime());
  }
  
  private void jdMethod_try(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(new Date());
    localCalendar.add(5, -1);
    paramCalendar1.setTime(localCalendar.getTime());
    paramCalendar2.setTime(paramCalendar1.getTime());
  }
  
  private void jdMethod_char(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.setTime(new Date());
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar1.add(5, -60);
    localCalendar2.add(5, -1);
    paramCalendar1.setTime(localCalendar1.getTime());
    paramCalendar2.setTime(localCalendar2.getTime());
  }
  
  private void b(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.setTime(new Date());
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar1.add(5, -90);
    localCalendar2.add(5, -1);
    paramCalendar1.setTime(localCalendar1.getTime());
    paramCalendar2.setTime(localCalendar2.getTime());
  }
  
  private void jdMethod_goto(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.setTime(new Date());
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar1.set(5, 1);
    paramCalendar1.setTime(localCalendar1.getTime());
    paramCalendar2.setTime(localCalendar2.getTime());
  }
  
  private void jdMethod_null(Calendar paramCalendar1, Calendar paramCalendar2)
  {
    Calendar localCalendar1 = Calendar.getInstance();
    localCalendar1.setTime(new Date());
    Calendar localCalendar2 = Calendar.getInstance();
    localCalendar1.set(5, 1);
    paramCalendar1.setTime(localCalendar1.getTime());
    localCalendar2.set(5, localCalendar2.getActualMaximum(5));
    paramCalendar2.setTime(localCalendar2.getTime());
  }
  
  private static com.ffusion.beans.DateTime jdMethod_new(String paramString1, String paramString2, Locale paramLocale)
    throws ParseException
  {
    Date localDate = DateFormatUtil.getFormatter(paramString2).parse(paramString1);
    return new com.ffusion.beans.DateTime(localDate, paramLocale);
  }
  
  private static com.ffusion.beans.DateTime jdMethod_int(String paramString1, String paramString2, Locale paramLocale)
    throws ParseException
  {
    Date localDate = DateFormatUtil.getFormatter(paramString2).parse(paramString1);
    return new com.ffusion.beans.DateTime(localDate, paramLocale);
  }
  
  private void jdMethod_for(ReportCriteria paramReportCriteria, Accounts paramAccounts, HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3)
    throws RptException
  {
    Properties localProperties1 = paramReportCriteria.getSearchCriteria();
    UserLocale localUserLocale = null;
    String str1 = null;
    String str2 = null;
    Locale localLocale = null;
    Object localObject1 = null;
    Properties localProperties2 = paramReportCriteria.getReportOptions();
    str1 = localProperties2.getProperty("DATEFORMAT");
    str2 = localProperties2.getProperty("TIMEFORMAT");
    if ((str1 != null) && (str2 != null) && (paramHashMap3 != null) && (paramHashMap3.containsKey("UserLocale")))
    {
      localUserLocale = (UserLocale)paramHashMap3.get("UserLocale");
      localLocale = localUserLocale.getLocale();
    }
    else if ((paramHashMap3 != null) && (paramHashMap3.containsKey("UserLocale")))
    {
      localUserLocale = (UserLocale)paramHashMap3.get("UserLocale");
      localLocale = localUserLocale.getLocale();
      str1 = localUserLocale.getDateFormat();
      str2 = localUserLocale.getTimeFormat();
    }
    else
    {
      localLocale = Locale.getDefault();
      str1 = "MM/dd/yyyy";
      str2 = "HH:mm";
    }
    String str3 = "MM/dd/yyyy";
    String str4 = "HH:mm";
    String str5 = "MM/dd/yyyy HH:mm:ss";
    com.ffusion.beans.DateTime localDateTime = null;
    String str6 = localProperties1.getProperty("StartDate");
    Object localObject5;
    if ((str6 != null) && (str6.length() != 0) && (!str6.equalsIgnoreCase(str3)))
    {
      try
      {
        localDateTime = jdMethod_new(str6, str3, localLocale);
      }
      catch (ParseException localParseException1)
      {
        localObject3 = new RptException(113, localParseException1);
        DebugLog.throwing("Unable to parse start date.", (Throwable)localObject3);
        throw ((Throwable)localObject3);
      }
      localObject2 = localProperties1.getProperty("StartTime");
      if ((localObject2 != null) && (((String)localObject2).trim().length() != 0) && (!((String)localObject2).trim().equalsIgnoreCase(str4)))
      {
        localObject3 = null;
        try
        {
          localObject3 = jdMethod_int((String)localObject2, str4, localLocale);
        }
        catch (ParseException localParseException2)
        {
          localObject5 = new RptException(117, localParseException2);
          DebugLog.throwing("Unable to parse start time.", (Throwable)localObject5);
          throw ((Throwable)localObject5);
        }
        localDateTime.set(11, ((com.ffusion.beans.DateTime)localObject3).get(11));
        localDateTime.set(12, ((com.ffusion.beans.DateTime)localObject3).get(12));
        localDateTime.set(13, ((com.ffusion.beans.DateTime)localObject3).get(13));
        localDateTime.set(14, ((com.ffusion.beans.DateTime)localObject3).get(14));
      }
      else
      {
        localDateTime.set(11, 0);
        localDateTime.set(12, 0);
        localDateTime.set(13, 0);
        localDateTime.set(14, 0);
      }
    }
    Object localObject2 = null;
    Object localObject3 = localProperties1.getProperty("EndDate");
    Object localObject4;
    if ((localObject3 != null) && (((String)localObject3).length() != 0) && (!((String)localObject3).equalsIgnoreCase(str3)))
    {
      try
      {
        localObject2 = jdMethod_new((String)localObject3, str3, localLocale);
      }
      catch (ParseException localParseException3)
      {
        localObject5 = new RptException(114, localParseException3);
        DebugLog.throwing("Unable to parse end date.", (Throwable)localObject5);
        throw ((Throwable)localObject5);
      }
      localObject4 = localProperties1.getProperty("EndTime");
      if ((localObject4 != null) && (((String)localObject4).trim().length() != 0) && (!((String)localObject4).trim().equalsIgnoreCase(str4)))
      {
        localObject5 = null;
        try
        {
          localObject5 = jdMethod_int((String)localObject4, str4, localLocale);
        }
        catch (ParseException localParseException4)
        {
          RptException localRptException = new RptException(118, localParseException4);
          DebugLog.throwing("Unable to parse end time.", localRptException);
          throw localRptException;
        }
        ((com.ffusion.beans.DateTime)localObject2).set(11, ((com.ffusion.beans.DateTime)localObject5).get(11));
        ((com.ffusion.beans.DateTime)localObject2).set(12, ((com.ffusion.beans.DateTime)localObject5).get(12));
        ((com.ffusion.beans.DateTime)localObject2).set(13, ((com.ffusion.beans.DateTime)localObject5).get(13));
        ((com.ffusion.beans.DateTime)localObject2).set(14, ((com.ffusion.beans.DateTime)localObject5).get(14));
      }
      else
      {
        ((com.ffusion.beans.DateTime)localObject2).set(11, 23);
        ((com.ffusion.beans.DateTime)localObject2).set(12, 59);
        ((com.ffusion.beans.DateTime)localObject2).set(13, 59);
        ((com.ffusion.beans.DateTime)localObject2).set(14, 999);
      }
    }
    if ((localObject2 != null) && (localDateTime != null) && (((com.ffusion.beans.DateTime)localObject2).compare(localDateTime) < 0))
    {
      localObject4 = new RptException(121);
      DebugLog.throwing("Start date is after end date", (Throwable)localObject4);
      throw ((Throwable)localObject4);
    }
    if (paramAccounts != null)
    {
      localObject4 = paramAccounts.iterator();
      while (((Iterator)localObject4).hasNext())
      {
        localObject5 = (Account)((Iterator)localObject4).next();
        String str7 = ((Account)localObject5).getRoutingNum();
        if ((localDateTime != null) && (!paramHashMap1.containsKey(((Account)localObject5).getRoutingNum()))) {
          paramHashMap1.put(str7, localDateTime);
        }
        if ((localObject2 != null) && (!paramHashMap2.containsKey(((Account)localObject5).getRoutingNum()))) {
          paramHashMap2.put(str7, localObject2);
        }
      }
    }
    if (localDateTime != null)
    {
      str6 = DateFormatUtil.getFormatter(str5).format(localDateTime.getTime());
      localProperties1.setProperty("StartDate", str6);
      localObject4 = DateFormatUtil.getFormatter(str1 + " " + str2).format(localDateTime.getTime());
      paramReportCriteria.setDisplayValue("StartDate", (String)localObject4);
    }
    else
    {
      localProperties1.remove("StartDate");
    }
    if (localObject2 != null)
    {
      localObject3 = DateFormatUtil.getFormatter(str5).format(((com.ffusion.beans.DateTime)localObject2).getTime());
      localProperties1.setProperty("EndDate", (String)localObject3);
      localObject4 = DateFormatUtil.getFormatter(str1 + " " + str2).format(((com.ffusion.beans.DateTime)localObject2).getTime());
      paramReportCriteria.setDisplayValue("EndDate", (String)localObject4);
    }
    else
    {
      localProperties1.remove("EndDate");
    }
  }
  
  private void jdMethod_int(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, Accounts paramAccounts, HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3)
    throws RptException
  {
    UserLocale localUserLocale = null;
    String str1 = null;
    String str2 = null;
    Locale localLocale = null;
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    str1 = localProperties1.getProperty("DATEFORMAT");
    str2 = localProperties1.getProperty("TIMEFORMAT");
    if ((str1 != null) && (str2 != null) && (paramHashMap3 != null) && (paramHashMap3.containsKey("UserLocale")))
    {
      localUserLocale = (UserLocale)paramHashMap3.get("UserLocale");
      localLocale = localUserLocale.getLocale();
    }
    else if ((paramHashMap3 != null) && (paramHashMap3.containsKey("UserLocale")))
    {
      localUserLocale = (UserLocale)paramHashMap3.get("UserLocale");
      localLocale = localUserLocale.getLocale();
      str1 = localUserLocale.getDateFormat();
      str2 = localUserLocale.getTimeFormat();
    }
    else
    {
      localLocale = Locale.getDefault();
      str1 = "MM/dd/yyyy";
      str2 = "HH:mm";
    }
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    String str3 = localProperties2.getProperty("Date Range");
    jdMethod_for(paramReportCriteria, localLocale);
    if ((str3 == null) || (str3.length() == 0))
    {
      localObject1 = new RptException(115);
      DebugLog.throwing("Null or empty date range value.", (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
    Object localObject3;
    Object localObject2;
    Object localObject5;
    Object localObject4;
    Object localObject6;
    if (paramAccounts == null)
    {
      if ("Previous Business Day".equalsIgnoreCase(str3))
      {
        localObject3 = new BankIdentifier(localLocale);
        ((BankIdentifier)localObject3).setBankDirectoryType("FedABA");
        ((BankIdentifier)localObject3).setBankDirectoryId(null);
        try
        {
          Date localDate1 = SmartCalendar.getPreviousDay(paramSecureUser, (BankIdentifier)localObject3, new Date(), paramHashMap3);
          localObject1 = new com.ffusion.beans.DateTime(localDate1, localLocale);
          localObject2 = new com.ffusion.beans.DateTime(localDate1, localLocale);
        }
        catch (CSILException localCSILException1)
        {
          localObject5 = new RptException(119);
          DebugLog.throwing("Cannot get previous business day.", localCSILException1);
          throw ((Throwable)localObject5);
        }
      }
      else if ("Current Session".equals(str3))
      {
        localObject3 = localProperties2.getProperty("SessionLoginTime_HIDE");
        localObject1 = Calendar.getInstance();
        localObject2 = Calendar.getInstance();
        ((Calendar)localObject2).setTime(new Date());
        try
        {
          ((Calendar)localObject1).setTimeInMillis(Long.parseLong((String)localObject3));
        }
        catch (Exception localException)
        {
          throw new RptException(122);
        }
      }
      else
      {
        localObject1 = Calendar.getInstance();
        localObject2 = Calendar.getInstance();
        jdMethod_for(str3, (Calendar)localObject1, (Calendar)localObject2, paramHashMap3);
      }
      if (!"Current Session".equals(str3))
      {
        ((Calendar)localObject1).set(11, 0);
        ((Calendar)localObject1).set(12, 0);
        ((Calendar)localObject1).set(13, 0);
        ((Calendar)localObject1).set(14, 0);
        ((Calendar)localObject2).set(11, 23);
        ((Calendar)localObject2).set(12, 59);
        ((Calendar)localObject2).set(13, 59);
        ((Calendar)localObject2).set(14, 999);
      }
      localObject3 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").format(((Calendar)localObject1).getTime());
      localProperties2.setProperty("StartDate", (String)localObject3);
      localObject4 = DateFormatUtil.getFormatter(str1 + " " + str2).format(((Calendar)localObject1).getTime());
      paramReportCriteria.setDisplayValue("StartDate", (String)localObject4);
      localObject5 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").format(((Calendar)localObject2).getTime());
      localProperties2.setProperty("EndDate", (String)localObject5);
      localObject6 = DateFormatUtil.getFormatter(str1 + " " + str2).format(((Calendar)localObject2).getTime());
      paramReportCriteria.setDisplayValue("EndDate", (String)localObject6);
      return;
    }
    Object localObject1 = paramAccounts.iterator();
    while (((Iterator)localObject1).hasNext())
    {
      localObject2 = (Account)((Iterator)localObject1).next();
      localObject3 = ((Account)localObject2).getRoutingNum();
      if (!paramHashMap1.containsKey(localObject3))
      {
        localObject4 = Calendar.getInstance();
        localObject5 = Calendar.getInstance();
        if ("Previous Business Day".equalsIgnoreCase(str3))
        {
          localObject6 = new BankIdentifier(localLocale);
          ((BankIdentifier)localObject6).setBankDirectoryType("FedABA");
          ((BankIdentifier)localObject6).setBankDirectoryId((String)localObject3);
          try
          {
            Date localDate2 = SmartCalendar.getPreviousDay(paramSecureUser, (BankIdentifier)localObject6, new Date(), paramHashMap3);
            localObject4 = new com.ffusion.beans.DateTime(localDate2, localLocale);
            localObject5 = new com.ffusion.beans.DateTime(localDate2, localLocale);
          }
          catch (CSILException localCSILException2)
          {
            localObject7 = new RptException(119);
            DebugLog.throwing("Cannot get previous business day.", localCSILException2);
            throw ((Throwable)localObject7);
          }
        }
        else
        {
          jdMethod_for(str3, (Calendar)localObject4, (Calendar)localObject5, paramHashMap3);
        }
        ((Calendar)localObject4).set(11, 0);
        ((Calendar)localObject4).set(12, 0);
        ((Calendar)localObject4).set(13, 0);
        ((Calendar)localObject4).set(14, 0);
        ((Calendar)localObject5).set(11, 23);
        ((Calendar)localObject5).set(12, 59);
        ((Calendar)localObject5).set(13, 59);
        ((Calendar)localObject5).set(14, 999);
        if (!paramHashMap1.containsKey(localObject3))
        {
          paramHashMap1.put(localObject3, new com.ffusion.beans.DateTime(((Calendar)localObject4).getTime(), localLocale));
          paramHashMap2.put(localObject3, new com.ffusion.beans.DateTime(((Calendar)localObject5).getTime(), localLocale));
        }
        localObject6 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").format(((Calendar)localObject4).getTime());
        localProperties2.setProperty("StartDate", (String)localObject6);
        String str4 = DateFormatUtil.getFormatter(str1 + " " + str2).format(((Calendar)localObject4).getTime());
        paramReportCriteria.setDisplayValue("StartDate", str4);
        Object localObject7 = DateFormatUtil.getFormatter("MM/dd/yyyy HH:mm:ss").format(((Calendar)localObject5).getTime());
        localProperties2.setProperty("EndDate", (String)localObject7);
        String str5 = DateFormatUtil.getFormatter(str1 + " " + str2).format(((Calendar)localObject5).getTime());
        paramReportCriteria.setDisplayValue("EndDate", str5);
      }
    }
  }
  
  private void jdMethod_for(SecureUser paramSecureUser, ReportCriteria paramReportCriteria, Accounts paramAccounts, HashMap paramHashMap1, HashMap paramHashMap2, HashMap paramHashMap3)
    throws RptException
  {
    UserLocale localUserLocale = null;
    String str1 = null;
    String str2 = null;
    Locale localLocale = null;
    String str3 = "MM/dd/yyyy HH:mm:ss";
    Properties localProperties1 = paramReportCriteria.getReportOptions();
    str1 = localProperties1.getProperty("DATEFORMAT");
    str2 = localProperties1.getProperty("TIMEFORMAT");
    if ((str1 != null) && (str2 != null) && (paramHashMap3 != null) && (paramHashMap3.containsKey("UserLocale")))
    {
      localUserLocale = (UserLocale)paramHashMap3.get("UserLocale");
      localLocale = localUserLocale.getLocale();
    }
    else if ((paramHashMap3 != null) && (paramHashMap3.containsKey("UserLocale")))
    {
      localUserLocale = (UserLocale)paramHashMap3.get("UserLocale");
      localLocale = localUserLocale.getLocale();
      str1 = localUserLocale.getDateFormat();
      str2 = localUserLocale.getTimeFormat();
    }
    else
    {
      localLocale = Locale.getDefault();
      str1 = "MM/dd/yyyy";
      str2 = "HH:mm";
    }
    Properties localProperties2 = paramReportCriteria.getSearchCriteria();
    com.ffusion.util.beans.DateTime localDateTime1 = (com.ffusion.util.beans.DateTime)paramHashMap3.get("LAST EXPORT DATE");
    if (localDateTime1 == null)
    {
      localDateTime1 = new com.ffusion.util.beans.DateTime(new Date(), localLocale);
      localDateTime1.add(5, -30);
      localDateTime1.set(11, 0);
      localDateTime1.set(12, 0);
      localDateTime1.set(13, 0);
      localDateTime1.set(14, 0);
    }
    String str4 = DateFormatUtil.getFormatter(str1 + " " + str2).format(localDateTime1.getTime());
    Object[] arrayOfObject = new Object[1];
    arrayOfObject[0] = str4;
    LocalizableString localLocalizableString = null;
    localLocalizableString = new LocalizableString("com.ffusion.beans.reporting.reports", "Text_Since_Last_Export", arrayOfObject);
    paramReportCriteria.getSearchCriteria().setProperty("Date Range", "");
    paramReportCriteria.setDisplayValue("Date Range", (String)localLocalizableString.localize(localLocale));
    com.ffusion.util.beans.DateTime localDateTime2 = new com.ffusion.util.beans.DateTime(localDateTime1, localLocale);
    localDateTime2.add(14, 1);
    com.ffusion.util.beans.DateTime localDateTime3 = new com.ffusion.util.beans.DateTime(new Date(), localLocale);
    String str5 = DateFormatUtil.getFormatter(str3).format(localDateTime2.getTime());
    localProperties2.setProperty("StartDate", str5);
    String str6 = DateFormatUtil.getFormatter(str1 + " " + str2).format(localDateTime2.getTime());
    paramReportCriteria.setDisplayValue("StartDate", str6);
    String str7 = DateFormatUtil.getFormatter(str3).format(localDateTime3.getTime());
    localProperties2.setProperty("EndDate", str7);
    String str8 = DateFormatUtil.getFormatter(str1 + " " + str2).format(localDateTime3.getTime());
    paramReportCriteria.setDisplayValue("EndDate", str8);
    paramHashMap3.put("CURRENT EXPORT DATE", localDateTime3);
  }
  
  private void jdMethod_for(ReportCriteria paramReportCriteria, Locale paramLocale)
  {
    Properties localProperties = paramReportCriteria.getSearchCriteria();
    String str = localProperties.getProperty("Date Range");
    if (str != null) {
      if ("Current Day".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10073, paramLocale));
      } else if ("Yesterday".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10059, paramLocale));
      } else if ("Current Week".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10060, paramLocale));
      } else if ("Current 4 Weeks".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10061, paramLocale));
      } else if ("Current 7 Days".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10062, paramLocale));
      } else if ("Last Week".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10063, paramLocale));
      } else if ("Last 4 Weeks".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10064, paramLocale));
      } else if ("Last 7 Days".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10065, paramLocale));
      } else if ("Last 30 Days".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10066, paramLocale));
      } else if ("Last Month".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10067, paramLocale));
      } else if ("Next Week".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10068, paramLocale));
      } else if ("Next 4 Weeks".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10069, paramLocale));
      } else if ("Next Month".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10070, paramLocale));
      } else if ("Previous Business Day".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10071, paramLocale));
      } else if ("Today(new)".equals(str)) {
        paramReportCriteria.setDisplayValue("Date Range", ReportConsts.getText(10072, paramLocale));
      }
    }
  }
  
  protected String getReportDirectory(SecureUser paramSecureUser, String paramString, boolean paramBoolean)
    throws RptException
  {
    if (paramSecureUser == null) {
      throw new RptException(15);
    }
    String str1 = paramSecureUser.getBankID();
    String str2 = String.valueOf(paramSecureUser.getProfileID());
    if (!w(str1)) {
      throw new RptException(13, str1);
    }
    if (!w(str2)) {
      throw new RptException(14, str2);
    }
    StringBuffer localStringBuffer = new StringBuffer(this.SZ + File.separator);
    if (paramString == null) {
      throw new RptException(120, paramString);
    }
    if (paramString.equals("USER")) {
      localStringBuffer.append(str1).append(File.separator).append(str2);
    } else if (paramString.equals("BANK")) {
      localStringBuffer.append(str1);
    } else {
      throw new RptException(120, paramString);
    }
    String str3 = localStringBuffer.toString();
    if (paramBoolean)
    {
      File localFile = new File(str3);
      if ((!localFile.exists()) && (!localFile.mkdirs())) {
        throw new RptException(16, str3);
      }
    }
    return str3;
  }
  
  private String v(String paramString)
  {
    String str = null;
    if (paramString.equals("HTML")) {
      str = ".mhtml";
    } else if (paramString.equals("PDF")) {
      str = ".pdf";
    } else if ((paramString.equals("COMMA")) || (paramString.equals("CSV"))) {
      str = ".csv";
    } else if (paramString.equals("TAB")) {
      str = ".txt";
    } else if (paramString.equals("BAI2")) {
      str = ".txt";
    } else if (paramString.equals("TEXT")) {
      str = ".txt";
    } else if (paramString.equals("QIF")) {
      str = ".qif";
    } else if (paramString.equals("QFX")) {
      str = ".qfx";
    } else if (paramString.equals("IIF")) {
      str = ".iif";
    }
    return str;
  }
  
  private boolean w(String paramString)
  {
    if (paramString == null) {
      return true;
    }
    for (int i = 0; i < S3.length; i++) {
      if (paramString.indexOf(S3[i]) != -1) {
        return false;
      }
    }
    return true;
  }
  
  private void jdMethod_for(String paramString1, String paramString2, XMLTag paramXMLTag, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
    throws RptException
  {
    XMLTag localXMLTag1 = null;
    XMLTag localXMLTag2 = null;
    XMLTag localXMLTag3 = null;
    XMLTag localXMLTag4 = null;
    String str = LocaleUtil.getLocaleIDFromXMLFileName(paramString1, paramString2);
    str = str.length() == 0 ? "default" : str;
    localXMLTag1 = paramXMLTag.getContainedTag("REPORTINGADAPTER");
    if ((paramBoolean1) && (localXMLTag1 == null)) {
      throw new RptException(1, "No 'REPORTINGADAPTER' tag found in file reporting.xml");
    }
    if (localXMLTag1 != null) {
      localXMLTag2 = localXMLTag1.getContainedTag("REPORTDEFAULTCRITERIA_LIST");
    }
    if ((paramBoolean1) || (localXMLTag2 != null)) {
      jdMethod_for(localXMLTag1, str, paramString1);
    }
    localXMLTag3 = paramXMLTag.getContainedTag("StyleDefinitionList");
    if ((paramBoolean2) && (localXMLTag3 == null)) {
      DebugLog.log("Could not obtain the settings for StyleDefinitionList from the XML file. Using default values.");
    } else if (localXMLTag3 != null) {
      jdMethod_for(localXMLTag3, str);
    }
    localXMLTag4 = paramXMLTag.getContainedTag("REPORT_EXPORT");
    if ((paramBoolean3) && (localXMLTag4 == null)) {
      throw new RptException(1, "No 'REPORT_EXPORT' tag found in file " + paramString1);
    }
    if (localXMLTag4 != null)
    {
      HashMap localHashMap = XMLUtil.tagToHashMap(localXMLTag4);
      if (localHashMap.containsKey("HTML_CSS_LOCATION")) {
        this.SY.put(str, (String)localHashMap.get("HTML_CSS_LOCATION"));
      }
    }
  }
  
  public String getCSSDir(SecureUser paramSecureUser)
  {
    String str1 = (String)this.SY.get("default");
    String str2 = null;
    Locale localLocale = paramSecureUser.getLocale();
    if (localLocale.getLanguage().length() > 0)
    {
      StringBuffer localStringBuffer = new StringBuffer(localLocale.getLanguage());
      str2 = str1;
      str1 = (String)this.SY.get(localStringBuffer.toString());
      str1 = (str1 == null) || (str1.length() == 0) ? str2 : str1;
      if (localLocale.getCountry().length() > 0)
      {
        localStringBuffer.append('_');
        localStringBuffer.append(localLocale.getCountry());
        str2 = str1;
        str1 = (String)this.SY.get(localStringBuffer.toString());
        str1 = (str1 == null) || (str1.length() == 0) ? str2 : str1;
      }
    }
    return str1;
  }
  
  public HashMap getFormatMap(SecureUser paramSecureUser)
  {
    HashMap localHashMap1 = (HashMap)this.S0.get("default");
    HashMap localHashMap2 = null;
    Locale localLocale = paramSecureUser.getLocale();
    if (localLocale.getLanguage().length() > 0)
    {
      StringBuffer localStringBuffer = new StringBuffer(localLocale.getLanguage());
      localHashMap2 = localHashMap1;
      localHashMap1 = (HashMap)this.S0.get(localStringBuffer.toString());
      localHashMap1 = localHashMap1 == null ? localHashMap2 : localHashMap1;
      if (localLocale.getCountry().length() > 0)
      {
        localStringBuffer.append('_');
        localStringBuffer.append(localLocale.getCountry());
        localHashMap2 = localHashMap1;
        localHashMap1 = (HashMap)this.S0.get(localStringBuffer.toString());
        localHashMap1 = localHashMap1 == null ? localHashMap2 : localHashMap1;
      }
    }
    return localHashMap1;
  }
  
  public EntitlementExpression getEntitlementExpressionFromXMLTag(XMLTag paramXMLTag)
    throws RptException
  {
    String str = (String)paramXMLTag.getAttribute("OPERATION");
    int i = -1;
    if ((str == null) || (str.equals("AND"))) {
      i = 1;
    } else if (str.equals("OR")) {
      i = 0;
    }
    ArrayList localArrayList1 = new ArrayList();
    Iterator localIterator = paramXMLTag.getContainedTagList().iterator();
    while (localIterator.hasNext())
    {
      XMLTag localXMLTag = (XMLTag)localIterator.next();
      if (localXMLTag.getTagName().equals("ENTITLEMENT_TYPE_LIST"))
      {
        localArrayList1.add(getEntitlementExpressionFromXMLTag(localXMLTag));
      }
      else if (localXMLTag.getTagName().equals("ENTITLEMENT_TYPE"))
      {
        ArrayList localArrayList2 = new ArrayList();
        localArrayList2.add(localXMLTag.getTagContent());
        localArrayList1.add(new EntitlementExpression(2, localArrayList2));
      }
      else
      {
        throw new RptException(1, "Error when parsing XML file reporting.xml. Expected either ENTITLEMENT_TYPE_LIST or ENTITLEMENT_TYPE tag.");
      }
    }
    return new EntitlementExpression(i, localArrayList1);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportingService
 * JD-Core Version:    0.7.0.1
 */