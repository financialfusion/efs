package com.ffusion.beans.istatements;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.XMLStrings;
import com.ffusion.util.XMLHandler;
import java.util.Locale;

public class StatementSettings
  extends ExtendABean
  implements XMLStrings
{
  protected String id;
  protected String employeeId = "0";
  protected DateTime activatedDate;
  protected String showTerms;
  protected DateTime lastLoginDate;
  protected String requestPDF;
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.activatedDate != null) {
      this.activatedDate.setFormat(paramString);
    }
    if (this.lastLoginDate != null) {
      this.lastLoginDate.setFormat(paramString);
    }
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.activatedDate != null) {
      this.activatedDate.setLocale(paramLocale);
    }
    if (this.lastLoginDate != null) {
      this.lastLoginDate.setLocale(paramLocale);
    }
  }
  
  public void setId(String paramString)
  {
    this.id = paramString;
  }
  
  public String getId()
  {
    return this.id;
  }
  
  public void setEmployeeId(String paramString)
  {
    this.employeeId = paramString;
  }
  
  public String getEmployeeId()
  {
    return this.employeeId;
  }
  
  public void setActivatedDate(String paramString)
  {
    try
    {
      if (this.activatedDate == null)
      {
        this.activatedDate = new DateTime(paramString, this.locale);
        this.activatedDate.setFormat(this.datetype);
      }
      else
      {
        this.activatedDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.activatedDate = null;
    }
  }
  
  public void setActivatedDate(DateTime paramDateTime)
  {
    if (this.activatedDate == null)
    {
      this.activatedDate = new DateTime(this.locale);
      this.activatedDate.setFormat(this.datetype);
    }
    this.activatedDate.setTime(paramDateTime.getTime());
  }
  
  public DateTime getActivatedDateValue()
  {
    return this.activatedDate;
  }
  
  public String getActivatedDate()
  {
    if (this.activatedDate != null) {
      return this.activatedDate.toString();
    }
    return "";
  }
  
  public void setLastLoginDate(String paramString)
  {
    try
    {
      if (this.lastLoginDate == null)
      {
        this.lastLoginDate = new DateTime(paramString, this.locale);
        this.lastLoginDate.setFormat(this.datetype);
      }
      else
      {
        this.lastLoginDate.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.lastLoginDate = null;
    }
  }
  
  public void setLastLoginDate(DateTime paramDateTime)
  {
    if (this.lastLoginDate == null)
    {
      this.lastLoginDate = new DateTime(this.locale);
      this.lastLoginDate.setFormat(this.datetype);
    }
    this.lastLoginDate.setTime(paramDateTime.getTime());
  }
  
  public DateTime getLastLoginDateValue()
  {
    return this.lastLoginDate;
  }
  
  public String getLastLoginDate()
  {
    if (this.lastLoginDate != null) {
      return this.lastLoginDate.toString();
    }
    return "";
  }
  
  public String getShowTerms()
  {
    return this.showTerms;
  }
  
  public void setShowTerms(String paramString)
  {
    this.showTerms = paramString;
  }
  
  public String getRequestPDF()
  {
    return this.requestPDF;
  }
  
  public void setRequestPDF(String paramString)
  {
    this.requestPDF = paramString;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "STATEMENT_SETTINGS");
    XMLHandler.appendTag(localStringBuffer, "USER_ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "EMPLOYEE_ID", this.employeeId);
    XMLHandler.appendTag(localStringBuffer, "ACTIVATION_DATE", this.activatedDate);
    XMLHandler.appendTag(localStringBuffer, "LAST_LOGIN", this.lastLoginDate);
    XMLHandler.appendTag(localStringBuffer, "REQUESTPDF", this.requestPDF);
    XMLHandler.appendTag(localStringBuffer, "SHOWTERMS", this.showTerms);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "STATEMENT_SETTINGS");
    return localStringBuffer.toString();
  }
  
  public void set(StatementSettings paramStatementSettings)
  {
    super.set(paramStatementSettings);
    setId(paramStatementSettings.getId());
    setEmployeeId(paramStatementSettings.getEmployeeId());
    setActivatedDate(paramStatementSettings.getActivatedDate());
    setLastLoginDate(paramStatementSettings.getLastLoginDate());
    setRequestPDF(paramStatementSettings.getRequestPDF());
    setShowTerms(paramStatementSettings.getShowTerms());
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.istatements.StatementSettings
 * JD-Core Version:    0.7.0.1
 */