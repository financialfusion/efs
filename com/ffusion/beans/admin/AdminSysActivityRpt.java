package com.ffusion.beans.admin;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.bcreport.ReportLogRecord;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class AdminSysActivityRpt
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  public static final String ADMIN_SYS_ACTIVITY_RPT = "ADMIN_SYS_ACTIVITY_RPT";
  public static final String AUDIT_LOG_RECORDS = "AUDIT_LOG_RECORDS";
  public static final String USERS = "USERS";
  private ReportLogRecords Sd = null;
  private BusinessEmployees Se = null;
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  
  public AdminSysActivityRpt(Locale paramLocale)
  {
    super.setLocale(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof AdminSysActivityRpt))) {
      return false;
    }
    AdminSysActivityRpt localAdminSysActivityRpt = (AdminSysActivityRpt)paramObject;
    boolean bool1;
    if (this.Sd == null) {
      bool1 = localAdminSysActivityRpt.getAuditLogRecords() == null;
    } else {
      bool1 = this.Sd.equals(localAdminSysActivityRpt.getAuditLogRecords());
    }
    if (!bool1) {
      return false;
    }
    boolean bool2;
    if (this.Se == null) {
      bool2 = localAdminSysActivityRpt.getUsers() == null;
    } else {
      bool2 = this.Se.equals(localAdminSysActivityRpt.getUsers());
    }
    return bool2;
  }
  
  public void setAuditLogRecords(ReportLogRecords paramReportLogRecords)
  {
    this.Sd = paramReportLogRecords;
  }
  
  public void setUsers(BusinessEmployees paramBusinessEmployees)
  {
    this.Se = paramBusinessEmployees;
  }
  
  public ReportLogRecords getAuditLogRecords()
  {
    return this.Sd;
  }
  
  public BusinessEmployees getUsers()
  {
    return this.Se;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',');
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t');
    }
    return localStringBuffer;
  }
  
  protected StringBuffer getDelimitedReport(char paramChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.Sd != null)
    {
      Iterator localIterator = this.Sd.iterator();
      localStringBuffer.append(ReportConsts.getColumnName(10, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(14, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(11, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(12, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(15, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(13, this.locale));
      localStringBuffer.append(_lineSeparator);
      while (localIterator.hasNext())
      {
        ReportLogRecord localReportLogRecord = (ReportLogRecord)localIterator.next();
        String str1 = ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale);
        localReportLogRecord.setDateFormat(str1);
        localStringBuffer.append(localReportLogRecord.getTranDate());
        localStringBuffer.append(paramChar);
        String str2 = ResourceUtil.getString("TimeFormat", "com.ffusion.beans.user.resources", this.locale);
        localReportLogRecord.setDateFormat(str2);
        localStringBuffer.append(localReportLogRecord.getTranDate());
        localStringBuffer.append(paramChar);
        BusinessEmployee localBusinessEmployee = this.Se.getByID(localReportLogRecord.getUserID());
        localStringBuffer.append(localBusinessEmployee == null ? localReportLogRecord.getUserID() : localBusinessEmployee.getName());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localReportLogRecord.getTranTypeText());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localReportLogRecord.getTranID());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localReportLogRecord.getMessage());
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
  
  public void set(AdminSysActivityRpt paramAdminSysActivityRpt)
  {
    super.set(paramAdminSysActivityRpt);
    setAuditLogRecords(paramAdminSysActivityRpt.getAuditLogRecords());
    setUsers(paramAdminSysActivityRpt.getUsers());
    setLocale(paramAdminSysActivityRpt.locale);
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ADMIN_SYS_ACTIVITY_RPT");
    if (this.Sd != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "AUDIT_LOG_RECORDS");
      localStringBuffer.append(this.Sd.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "AUDIT_LOG_RECORDS");
    }
    if (this.Se != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "USERS");
      localStringBuffer.append(this.Se.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "USERS");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ADMIN_SYS_ACTIVITY_RPT");
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
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("AUDIT_LOG_RECORDS"))
      {
        if (AdminSysActivityRpt.this.Sd == null) {
          AdminSysActivityRpt.this.Sd = new ReportLogRecords();
        }
        AdminSysActivityRpt.this.Sd.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("USERS"))
      {
        if (AdminSysActivityRpt.this.Se == null) {
          AdminSysActivityRpt.this.Se = new BusinessEmployees(AdminSysActivityRpt.this.locale);
        }
        AdminSysActivityRpt.this.Se.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.admin.AdminSysActivityRpt
 * JD-Core Version:    0.7.0.1
 */