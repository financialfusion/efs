package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.Reporting;
import com.ffusion.reporting.ReportFileExtensions;
import com.ffusion.reporting.ReportHTTPHeaders;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.BAIExportSettingsXMLUtil;
import com.ffusion.util.entitlements.EntitlementsUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExportReport
  extends BaseTask
  implements ReportingConsts, ReportHTTPHeaders, ReportFileExtensions
{
  private static final DateFormat Tb = null;
  private String S9 = "COMMA";
  private String Ta = "Report";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Report localReport = (Report)localHttpSession.getAttribute(this.Ta);
    if ((this.S9 == null) || (this.S9.length() == 0))
    {
      this.error = 76;
      return this.taskErrorURL;
    }
    try
    {
      HashMap localHashMap1 = new HashMap();
      localHashMap1.put("UserLocale", localHttpSession.getAttribute("UserLocale"));
      if (localReport != null)
      {
        localHashMap1.put("REPORTCRITERIA", localReport.getReportCriteria());
        localObject1 = localReport.getReportCriteria().getReportOptions().getProperty("REPORTTYPE");
        localHashMap1.put("REPORTTYPE", localObject1);
      }
      Object localObject3;
      if (this.S9.equals("BAI2"))
      {
        localObject1 = (Business)localHttpSession.getAttribute("Business");
        if (localObject1 == null)
        {
          this.error = 74;
          localObject2 = this.taskErrorURL;
          return localObject2;
        }
        localObject2 = new HashMap();
        localObject2 = EntitlementsUtil.allowEntitlementBypass((HashMap)localObject2);
        localObject3 = AffiliateBankAdmin.getAffiliateBankByID(localSecureUser, ((Business)localObject1).getAffiliateBankID(), (HashMap)localObject2);
        localHashMap1.put("AFFILIATE_BANK_ROUTING_NUMBER", ((AffiliateBank)localObject3).getAffiliateRoutingNum());
        HashMap localHashMap2 = null;
        try
        {
          localHashMap2 = BAIExportSettingsXMLUtil.getBAIExportSettings(localSecureUser, (Business)localObject1);
        }
        catch (Exception localException)
        {
          DebugLog.throwing("The call to retrieve BAI Export Settings failed.", localException);
          this.error = 3526;
          String str = this.taskErrorURL;
          return str;
        }
        if ((localHashMap2 != null) && (!localHashMap2.isEmpty())) {
          localHashMap1.putAll(localHashMap2);
        }
      }
      localObject1 = Reporting.exportReport(localSecureUser, localReport, this.S9, localHashMap1);
      Object localObject2 = localReport.getReportCriteria().getReportOptions().getProperty("TITLE");
      if (localObject2 != null)
      {
        localObject3 = new StringBuffer(((String)localObject2).length());
        for (int i = 0; i < ((String)localObject2).length(); i++)
        {
          char c = ((String)localObject2).charAt(i);
          if (!Character.isWhitespace(c)) {
            ((StringBuffer)localObject3).append(c);
          }
        }
        localObject2 = ((StringBuffer)localObject3).toString();
      }
      else
      {
        localObject2 = "report";
      }
      if ((this.S9.equals("COMMA")) || (this.S9.equals("CSV"))) {
        jdMethod_for(paramHttpServletResponse, (String)localObject2, localObject1.toString());
      } else if (this.S9.equals("TAB")) {
        jdMethod_int(paramHttpServletResponse, localObject1.toString());
      } else if (this.S9.equals("BAI2")) {
        jdMethod_int(paramHttpServletResponse, (String)localObject2, localObject1.toString());
      } else if (this.S9.equals("HTML")) {
        localHttpSession.setAttribute("ExportedReport", localObject1);
      } else if (this.S9.equals("PDF")) {
        jdMethod_for(paramHttpServletResponse, (byte[])localObject1);
      } else if (this.S9.equals("TEXT")) {
        jdMethod_for(paramHttpServletResponse, localObject1.toString());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      Object localObject1 = super.getServiceErrorURL();
      return localObject1;
    }
    finally
    {
      if (localReport != null) {
        localReport.setReportResult(null);
      }
    }
    return super.getSuccessURL();
  }
  
  private void jdMethod_for(HttpServletResponse paramHttpServletResponse, String paramString1, String paramString2)
    throws IOException
  {
    String str = Tb.format(new Date());
    if ((paramString1 == null) || (paramString1.length() <= 0)) {
      paramString1 = "EXPORT";
    }
    paramHttpServletResponse.setContentType("application/vnd.ms-excel; charset=UTF-8; charset=UTF-8");
    paramHttpServletResponse.setHeader("Content-Disposition", "attachment;filename=" + paramString1 + str + ".csv");
    PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
    localPrintWriter.println(paramString2);
    localPrintWriter.flush();
    localPrintWriter.close();
  }
  
  private void jdMethod_int(HttpServletResponse paramHttpServletResponse, String paramString)
    throws IOException
  {
    paramHttpServletResponse.setContentType("text/plain; charset=UTF-8");
    paramHttpServletResponse.setHeader("Content-Disposition", "attachment;filename=EXPORT.txt");
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    localServletOutputStream.println(paramString);
    localServletOutputStream.flush();
    localServletOutputStream.close();
  }
  
  private void jdMethod_int(HttpServletResponse paramHttpServletResponse, String paramString1, String paramString2)
    throws IOException
  {
    if ((paramString1 == null) || (paramString1.length() <= 0)) {
      paramString1 = "EXPORT";
    }
    String str = Tb.format(new Date());
    paramHttpServletResponse.setContentType("text/plain; charset=UTF-8; charset=UTF-8");
    paramHttpServletResponse.setHeader("Content-Disposition", "attachment;filename=BAI2-" + str + ".txt");
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    localServletOutputStream.println(paramString2);
    localServletOutputStream.flush();
    localServletOutputStream.close();
  }
  
  private void jdMethod_for(HttpServletResponse paramHttpServletResponse, String paramString)
    throws IOException
  {
    paramHttpServletResponse.setContentType("text/plain; charset=UTF-8");
    paramHttpServletResponse.setHeader("Content-Disposition", "attachment;filename=EXPORT.txt");
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    localServletOutputStream.println(paramString);
    localServletOutputStream.flush();
    localServletOutputStream.close();
  }
  
  private void jdMethod_for(HttpServletResponse paramHttpServletResponse, byte[] paramArrayOfByte)
    throws IOException
  {
    paramHttpServletResponse.setContentType("application/pdf");
    paramHttpServletResponse.setHeader("Content-Disposition", "attachment;filename=EXPORT.pdf");
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    localServletOutputStream.write(paramArrayOfByte);
    localServletOutputStream.flush();
    localServletOutputStream.close();
  }
  
  public void setExportFormat(String paramString)
  {
    this.S9 = paramString;
  }
  
  public void setReportName(String paramString)
  {
    this.Ta = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.ExportReport
 * JD-Core Version:    0.7.0.1
 */