package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.Register;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.TaskUtil;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExportRegisterTransactions
  extends BaseTask
  implements Task
{
  public static final String DEBIT_LINES = "Dr";
  public static final String CREDIT_LINES = "Cr";
  public static final String ALL_LINES = "All";
  protected String _format = null;
  protected String _tranStatus = null;
  protected String _refNum = null;
  protected String _startDate = null;
  protected String _endDate = null;
  protected String _dateFormat = null;
  protected String _debitCreditOption = "All";
  protected String _qfxBankName = null;
  protected String _qfxBankID = null;
  protected String _qfxUserID = null;
  protected String _accountName = "Account";
  protected String _dateRangeType = null;
  protected String _dateRange = null;
  protected String _userLocale = "UserLocale";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Account localAccount = (Account)localHttpSession.getAttribute(this._accountName);
    String str1 = this.taskErrorURL;
    if (localSecureUser == null) {
      this.error = 20009;
    } else if (localAccount == null) {
      this.error = 20001;
    } else if ((this._format == null) || (this._format.length() == 0)) {
      this.error = 76;
    } else {
      try
      {
        this.error = 0;
        str1 = "";
        Properties localProperties1 = new Properties();
        ReportSortCriteria localReportSortCriteria = new ReportSortCriteria();
        Properties localProperties2 = new Properties();
        String str2 = "Account";
        String str3 = localAccount.getID();
        localProperties1.setProperty(str2, str3);
        str2 = "Date Range Type";
        localProperties1.setProperty(str2, getDateRangeType());
        if (getDateRangeType().equals("Relative")) {
          localProperties1.setProperty("Date Range", this._dateRange);
        }
        UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute(this._userLocale);
        String str4 = null;
        String str5 = null;
        if (localUserLocale != null)
        {
          str4 = localUserLocale.getDateFormat();
          str5 = localUserLocale.getTimeFormat();
        }
        else
        {
          str4 = "MM/dd/yyyy";
          str5 = "HH:mm";
        }
        if (this._startDate != null)
        {
          localObject1 = null;
          try
          {
            localObject1 = DateFormatUtil.getFormatter(str4).parse(this._startDate);
          }
          catch (ParseException localParseException1)
          {
            this.error = 44;
            return this.taskErrorURL;
          }
          this._startDate = DateFormatUtil.getFormatter("MM/dd/yyyy").format((Date)localObject1);
          str2 = "StartDate";
          str3 = this._startDate;
          localProperties1.setProperty(str2, str3);
        }
        if (this._endDate != null)
        {
          localObject1 = null;
          try
          {
            localObject1 = DateFormatUtil.getFormatter(str4).parse(this._endDate);
          }
          catch (ParseException localParseException2)
          {
            this.error = 44;
            return this.taskErrorURL;
          }
          this._endDate = DateFormatUtil.getFormatter("MM/dd/yyyy").format((Date)localObject1);
          str2 = "EndDate";
          str3 = this._endDate;
          localProperties1.setProperty(str2, str3);
        }
        if ((this._refNum != null) && (this._refNum.length() != 0))
        {
          str2 = "RefNumber";
          str3 = this._refNum;
          localProperties1.setProperty(str2, str3);
        }
        if (this._tranStatus != null)
        {
          str2 = "Status";
          str3 = this._tranStatus;
          localProperties1.setProperty(str2, str3);
        }
        if (this._debitCreditOption.equals("Dr"))
        {
          str2 = "MaximumAmount";
          str3 = "0";
          localProperties1.setProperty(str2, str3);
        }
        else if (this._debitCreditOption.equals("Cr"))
        {
          str2 = "MinimumAmount";
          str3 = "0";
          localProperties1.setProperty(str2, str3);
        }
        localReportSortCriteria.create(1, "ProcessingDate", true);
        Object localObject1 = "REPORTCATEGORY";
        String str6 = "Generic";
        localProperties2.setProperty((String)localObject1, str6);
        localObject1 = "REPORTTYPE";
        str6 = "Account Register Export Transactions";
        localProperties2.setProperty((String)localObject1, str6);
        localObject1 = "TITLE";
        str6 = ReportConsts.getText(2310, localSecureUser.getLocale());
        localProperties2.setProperty((String)localObject1, str6);
        if (this._dateFormat != null)
        {
          localObject1 = "DATEFORMAT";
          str6 = this._dateFormat;
          localProperties2.setProperty((String)localObject1, str6);
        }
        localObject1 = "PAGEWIDTH";
        str6 = "750";
        localProperties2.setProperty((String)localObject1, str6);
        localObject1 = "PAGEWIDTH_TEXT";
        str6 = "125";
        localProperties2.setProperty((String)localObject1, str6);
        localProperties2.setProperty("SHOWTITLE", "TRUE");
        localProperties2.setProperty("SHOWCOMPANYNAME", "TRUE");
        localProperties2.setProperty("SHOWDATE", "TRUE");
        localProperties2.setProperty("DATEFORMAT", ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", localSecureUser.getLocale()));
        localProperties2.setProperty("SHOWTIME", "TRUE");
        localProperties2.setProperty("TIMEFORMAT", ResourceUtil.getString("TimeFormat", "com.ffusion.beans.user.resources", localSecureUser.getLocale()));
        localProperties2.setProperty("SHOWHEADER", "TRUE");
        HashMap localHashMap = new HashMap();
        if (this._format.equalsIgnoreCase("QIF"))
        {
          localProperties2.setProperty("FORMAT", "QIF");
        }
        else if ((this._format.equalsIgnoreCase("QFX")) || (this._format.equalsIgnoreCase("QBO")))
        {
          localProperties2.setProperty("DATEFORMAT", "MM/dd/yyyy HH:mm:ss");
          localProperties2.setProperty("TIMEFORMAT", "");
          if (this._format.equalsIgnoreCase("QFX")) {
            localProperties2.setProperty("FORMAT", "QFX");
          } else {
            localProperties2.setProperty("FORMAT", "QBO");
          }
          localObject2 = new HashMap();
          localObject2 = EntitlementsUtil.allowEntitlementBypass((HashMap)localObject2);
          localObject3 = AffiliateBankAdmin.getAffiliateBankByID(localSecureUser, localSecureUser.getAffiliateIDValue(), (HashMap)localObject2);
          localObject4 = this._qfxBankName != null ? this._qfxBankName : ((AffiliateBank)localObject3).getAffiliateBankName();
          localObject5 = this._qfxBankID != null ? this._qfxBankID : ((AffiliateBank)localObject3).getBrandID();
          if (localObject4 != null) {
            localProperties2.setProperty("OPT_QFX_BANK_NAME", (String)localObject4);
          }
          if (localObject5 != null) {
            localProperties2.setProperty("OPT_QFX_BANK_ID", (String)localObject5);
          }
          if (this._qfxUserID != null) {
            localProperties2.setProperty("OPT_QFX_USER_ID", this._qfxUserID);
          }
        }
        else if (this._format.equalsIgnoreCase("IIF"))
        {
          localProperties2.setProperty("FORMAT", "IIF");
        }
        else if (this._format.equalsIgnoreCase("COMMA"))
        {
          localProperties2.setProperty("FORMAT", "COMMA");
        }
        else if (this._format.equalsIgnoreCase("TAB"))
        {
          localProperties2.setProperty("FORMAT", "TAB");
        }
        else if (this._format.equalsIgnoreCase("PDF"))
        {
          localProperties2.setProperty("FORMAT", "PDF");
          localProperties2.setProperty("ORIENTATION", "LANDSCAPE");
        }
        else if (this._format.equalsIgnoreCase("HTML"))
        {
          localProperties2.setProperty("FORMAT", "HTML");
        }
        else if (this._format.equalsIgnoreCase("BAI2"))
        {
          localProperties2.setProperty("FORMAT", "BAI2");
        }
        else if (this._format.equalsIgnoreCase("TEXT"))
        {
          localProperties2.setProperty("FORMAT", "TEXT");
        }
        else if (this._format.equalsIgnoreCase("OFX"))
        {
          localProperties2.setProperty("DATEFORMAT", "MM/dd/yyyy HH:mm:ss");
          localProperties2.setProperty("TIMEFORMAT", "");
          localProperties2.setProperty("FORMAT", "OFX");
        }
        localHashMap.put("Account", localAccount);
        localProperties2.setProperty("DESTINATION", "USER_FILE_SYSTEM");
        localProperties2.setProperty("STYLESHEET_NAME", (String)localHttpSession.getAttribute("ReportStyleSheetName"));
        Object localObject2 = "CRLF";
        if (TaskUtil.getOSType(paramHttpServletRequest).equals("Unix")) {
          localObject2 = "LF";
        }
        localProperties2.setProperty("EOL_STYLE", (String)localObject2);
        Object localObject3 = new ReportCriteria(localProperties1, localReportSortCriteria, localProperties2);
        Object localObject4 = new ArrayList();
        ((ArrayList)localObject4).add("Account");
        ((ArrayList)localObject4).add("Date Range Type");
        ((ArrayList)localObject4).add("StartDate");
        ((ArrayList)localObject4).add("EndDate");
        ((ArrayList)localObject4).add("RefNumber");
        ((ArrayList)localObject4).add("Status");
        ((ArrayList)localObject4).add("MinimumAmount");
        ((ArrayList)localObject4).add("MaximumAmount");
        ((ReportCriteria)localObject3).setSearchCriteriaOrder((ArrayList)localObject4);
        if (localSecureUser.getLocale() != null) {
          ((ReportCriteria)localObject3).setDisplayValue("1", ReportConsts.getCriteriaName("Account Register Export Transactions", "ProcessingDate", localSecureUser.getLocale()));
        }
        ((ReportCriteria)localObject3).setHttpServletResponse(paramHttpServletResponse);
        Object localObject5 = UserAdmin.getLastExportedDateTime(localSecureUser, localAccount, "ConsumerAccountRegister", localHashMap);
        localHashMap.put("LAST EXPORT DATE", localObject5);
        localHashMap.put("UserLocale", localUserLocale);
        IReportResult localIReportResult = Register.getReportData(localSecureUser, (ReportCriteria)localObject3, localHashMap);
        if ((localSecureUser.getUserType() == 1) && (localSecureUser.getAgent() == null))
        {
          DateTime localDateTime = new DateTime(Calendar.getInstance(localUserLocale.getLocale()), localUserLocale.getLocale());
          if (localDateTime == null) {
            localDateTime = new DateTime(new Date(), localAccount.getLocale());
          }
          UserAdmin.setLastExportedDateTime(localSecureUser, localAccount, "ConsumerAccountRegister", localDateTime, localHashMap);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    return str1;
  }
  
  public void setDateRange(String paramString)
  {
    this._dateRange = paramString;
  }
  
  public String getDateRange()
  {
    return this._dateRange;
  }
  
  public void setDateRangeType(String paramString)
  {
    this._dateRangeType = paramString;
  }
  
  public String getDateRangeType()
  {
    return this._dateRangeType;
  }
  
  public void setFormat(String paramString)
  {
    this._format = paramString;
  }
  
  public String getFormat()
  {
    return this._format;
  }
  
  public void setIncludeType(String paramString)
  {
    if (paramString.equalsIgnoreCase("Reconciled")) {
      this._tranStatus = paramString;
    } else if (paramString.equalsIgnoreCase("Unreconciled")) {
      this._tranStatus = paramString;
    } else {
      this._tranStatus = "All";
    }
  }
  
  public String getReferenceNumber()
  {
    return this._refNum;
  }
  
  public void setReferenceNumber(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this._refNum = paramString;
    } else {
      paramString = null;
    }
  }
  
  public String getStartDate()
  {
    return this._startDate;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this._startDate = paramString;
    } else {
      this._startDate = null;
    }
  }
  
  public String getEndDate()
  {
    return this._endDate;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this._endDate = paramString;
    } else {
      this._endDate = null;
    }
  }
  
  public String getDateFormat()
  {
    return this._dateFormat;
  }
  
  public void setDateFormat(String paramString)
  {
    this._dateFormat = paramString;
  }
  
  public String getDebitCreditOption()
  {
    return this._debitCreditOption;
  }
  
  public void setDebitCreditOption(String paramString)
  {
    if (("Dr".equalsIgnoreCase(paramString)) || ("Cr".equalsIgnoreCase(paramString))) {
      this._debitCreditOption = paramString;
    } else {
      this._debitCreditOption = "All";
    }
  }
  
  public void setQFXBankName(String paramString)
  {
    this._qfxBankName = paramString;
  }
  
  public String getQFXBankName()
  {
    return this._qfxBankName;
  }
  
  public void setQFXBankID(String paramString)
  {
    this._qfxBankID = paramString;
  }
  
  public String getQFXBankID()
  {
    return this._qfxBankID;
  }
  
  public void setQFXUserID(String paramString)
  {
    this._qfxUserID = paramString;
  }
  
  public String getQFXUserID()
  {
    return this._qfxUserID;
  }
  
  public void setAccountName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this._accountName = "Account";
    } else {
      this._accountName = paramString;
    }
  }
  
  public String getAccountName()
  {
    return this._accountName;
  }
  
  public void setUserLocaleName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this._userLocale = "UserLocale";
    } else {
      this._userLocale = paramString;
    }
  }
  
  public String getUserLocaleName()
  {
    return this._userLocale;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.ExportRegisterTransactions
 * JD-Core Version:    0.7.0.1
 */