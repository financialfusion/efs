package com.ffusion.beans.admin;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.bankemployee.BankEmployee;
import com.ffusion.beans.bankemployee.BankEmployees;
import com.ffusion.beans.bcreport.ReportLogRecord;
import com.ffusion.beans.bcreport.ReportLogRecords;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.user.BusinessEmployee;
import com.ffusion.beans.user.BusinessEmployees;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class AdminOBORpt
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  public static final String ADMIN_OBO_RPT = "ADMIN_OBO_RPT";
  public static final String AUDIT_LOG_RECORDS = "AUDIT_LOG_RECORDS";
  public static final String AGENTS = "AGENTS";
  public static final String USERS = "USERS";
  private ReportLogRecords R2 = null;
  private BusinessEmployees R3 = null;
  private BankEmployees R4 = null;
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  
  public AdminOBORpt(Locale paramLocale)
  {
    super.setLocale(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof AdminOBORpt))) {
      return false;
    }
    AdminOBORpt localAdminOBORpt = (AdminOBORpt)paramObject;
    boolean bool1 = false;
    if (this.R2 == null) {
      bool1 = localAdminOBORpt.getAuditLogRecords() == null;
    } else {
      bool1 = this.R2.equals(localAdminOBORpt.getAuditLogRecords());
    }
    if (!bool1) {
      return false;
    }
    boolean bool2 = false;
    if (this.R4 == null) {
      bool2 = localAdminOBORpt.getAgents() == null;
    } else {
      bool2 = this.R4.equals(localAdminOBORpt.getAgents());
    }
    if (!bool2) {
      return false;
    }
    boolean bool3 = false;
    if (this.R3 == null) {
      bool3 = localAdminOBORpt.getUsers() == null;
    } else {
      bool3 = this.R3.equals(localAdminOBORpt.getUsers());
    }
    return bool3;
  }
  
  public void setAuditLogRecords(ReportLogRecords paramReportLogRecords)
  {
    this.R2 = paramReportLogRecords;
  }
  
  public void setAgents(BankEmployees paramBankEmployees)
  {
    this.R4 = paramBankEmployees;
  }
  
  public void setUsers(BusinessEmployees paramBusinessEmployees)
  {
    this.R3 = paramBusinessEmployees;
  }
  
  public ReportLogRecords getAuditLogRecords()
  {
    return this.R2;
  }
  
  public BankEmployees getAgents()
  {
    return this.R4;
  }
  
  public BusinessEmployees getUsers()
  {
    return this.R3;
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
    if (this.R2 != null)
    {
      Iterator localIterator = this.R2.iterator();
      localStringBuffer.append(ReportConsts.getColumnName(1, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(2, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(3, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(4, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(6, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(5, this.locale));
      localStringBuffer.append(_lineSeparator);
      while (localIterator.hasNext())
      {
        ReportLogRecord localReportLogRecord = (ReportLogRecord)localIterator.next();
        localStringBuffer.append(localReportLogRecord.getTranDate());
        localStringBuffer.append(paramChar);
        String str = localReportLogRecord.getAgentID();
        BankEmployee localBankEmployee = this.R4.getByID(str);
        localStringBuffer.append(localBankEmployee == null ? str : localBankEmployee.getName());
        localStringBuffer.append(paramChar);
        BusinessEmployee localBusinessEmployee = this.R3.getByID(localReportLogRecord.getUserID());
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
  
  public void set(AdminOBORpt paramAdminOBORpt)
  {
    super.set(paramAdminOBORpt);
    setAuditLogRecords(paramAdminOBORpt.getAuditLogRecords());
    setUsers(paramAdminOBORpt.getUsers());
    setAgents(paramAdminOBORpt.getAgents());
    setLocale(paramAdminOBORpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ADMIN_OBO_RPT");
    if (this.R2 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "AUDIT_LOG_RECORDS");
      XMLHandler.appendEndTag(localStringBuffer, "AUDIT_LOG_RECORDS");
    }
    if (this.R4 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "AGENTS");
      localStringBuffer.append(this.R4.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "AGENTS");
    }
    if (this.R3 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "USERS");
      localStringBuffer.append(this.R3.toXML());
      XMLHandler.appendEndTag(localStringBuffer, "USERS");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ADMIN_OBO_RPT");
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
        if (AdminOBORpt.this.R2 == null) {
          AdminOBORpt.this.R2 = new ReportLogRecords();
        }
        AdminOBORpt.this.R2.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("AGENTS"))
      {
        if (AdminOBORpt.this.R4 == null) {
          AdminOBORpt.this.R4 = new BankEmployees(AdminOBORpt.this.locale);
        }
        AdminOBORpt.this.R4.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("USERS"))
      {
        if (AdminOBORpt.this.R3 == null) {
          AdminOBORpt.this.R3 = new BusinessEmployees(AdminOBORpt.this.locale);
        }
        AdminOBORpt.this.R3.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.admin.AdminOBORpt
 * JD-Core Version:    0.7.0.1
 */