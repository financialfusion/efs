package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.accounts.AcctTransactionRpt;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.Banking;
import com.ffusion.csil.core.UserAdmin;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.Banking3;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.BAIExportSettingsXMLUtil;
import com.ffusion.tasks.util.TaskUtil;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.entitlements.EntitlementsUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ExportHistory
  extends BaseTask
  implements Task
{
  public static final String DEBIT_LINES = "Dr";
  public static final String CREDIT_LINES = "Cr";
  public static final String ALL_LINES = "All";
  String yQ = null;
  String yW = null;
  String yR = null;
  String y4 = "Account";
  String y1 = null;
  String yT = null;
  String yX = null;
  String y2 = "P";
  String yY = "All";
  String y5 = null;
  String yZ = null;
  String y0 = null;
  ReportCriteria yV = null;
  String y3 = null;
  ReportSortCriterion yU = null;
  boolean yS = false;
  protected boolean _enforceMaxDateRangeCheck = false;
  protected int _maxDaysInDateRange = 90;
  protected boolean _enforceNoFutureDatesCheck = false;
  protected String bankingServiceName = "com.ffusion.services.Banking";
  
  public String getDebitCreditOption()
  {
    return this.yY;
  }
  
  public void setDebitCreditOption(String paramString)
  {
    if (("Dr".equalsIgnoreCase(paramString)) || ("Cr".equalsIgnoreCase(paramString))) {
      this.yY = paramString;
    } else {
      this.yY = "All";
    }
  }
  
  public String getStartDate()
  {
    return this.yW;
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.yW = paramString;
    } else {
      this.yW = null;
    }
  }
  
  public String getEndDate()
  {
    return this.yR;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.yR = paramString;
    } else {
      this.yR = null;
    }
  }
  
  public String getDataClassification()
  {
    return this.y2;
  }
  
  public void setDataClassification(String paramString)
  {
    this.y2 = paramString;
  }
  
  public void setAccountName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.y4 = "Account";
    } else {
      this.y4 = paramString;
    }
  }
  
  public String getAccountName()
  {
    return this.y4;
  }
  
  public void setFDInstrumentNumber(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.y1 = paramString;
    } else {
      this.y1 = null;
    }
  }
  
  public String getFDInstrumentNumber()
  {
    return this.y1;
  }
  
  public void setFDInstrumentBankName(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 0)) {
      this.yT = paramString;
    } else {
      this.yT = null;
    }
  }
  
  public String getFDInstrumentBankName()
  {
    return this.yT;
  }
  
  public void setDateFormat(String paramString)
  {
    this.yX = paramString;
  }
  
  public String getDateFormat()
  {
    return this.yX;
  }
  
  public void setExportFormat(String paramString)
  {
    this.yQ = paramString;
  }
  
  public String getExportFormat()
  {
    return this.yQ;
  }
  
  public void setQFXBankName(String paramString)
  {
    this.y5 = paramString;
  }
  
  public String getQFXBankName()
  {
    return this.y5;
  }
  
  public void setQFXBankID(String paramString)
  {
    this.yZ = paramString;
  }
  
  public String getQFXBankID()
  {
    return this.yZ;
  }
  
  public void setQFXUserID(String paramString)
  {
    this.y0 = paramString;
  }
  
  public String getQFXUserID()
  {
    return this.y0;
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    if (this.yS) {
      return processConsumerBanking(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    }
    return processCorporateBanking(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
  }
  
  public String processCorporateBanking(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.y4);
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Business localBusiness = (Business)localHttpSession.getAttribute("Business");
    GetPagedTransactions localGetPagedTransactions = (GetPagedTransactions)localHttpSession.getAttribute("GetPagedTransactions");
    if (localAccount == null) {
      this.error = 1002;
    } else if (localSecureUser == null) {
      this.error = 1037;
    } else if (localBusiness == null) {
      this.error = 74;
    } else if ((this.yQ == null) || (this.yQ.length() == 0)) {
      this.error = 76;
    } else {
      try
      {
        this.error = 0;
        Properties localProperties1 = new Properties();
        ReportSortCriteria localReportSortCriteria = new ReportSortCriteria();
        localReportSortCriteria.setLocale(localSecureUser.getLocale());
        Properties localProperties2 = new Properties();
        String str2 = "Accounts";
        String str3 = localAccount.getID() + ':' + localAccount.getBankID() + ':' + localAccount.getRoutingNum() + ':' + localAccount.getNickName() + ':' + localAccount.getCurrencyCode();
        localProperties1.setProperty(str2, str3);
        str2 = "Date Range Type";
        localProperties1.setProperty(str2, "Absolute");
        DateFormat localDateFormat1 = DateFormatUtil.getFormatter(this.yX);
        DateFormat localDateFormat2 = DateFormatUtil.getFormatter("MM/dd/yyyy");
        com.ffusion.beans.DateTime localDateTime = null;
        if ((this.yW != null) && (this.yW.length() > 0) && (!this.yW.equalsIgnoreCase(this.yX)))
        {
          str2 = "StartDate";
          try
          {
            localDateTime = new com.ffusion.beans.DateTime(localDateFormat1.parse(this.yW), Locale.getDefault());
            str3 = localDateFormat2.format(localDateTime.getTime());
          }
          catch (ParseException localParseException1)
          {
            this.error = 1021;
            return this.taskErrorURL;
          }
          localProperties1.setProperty(str2, str3);
        }
        if ((this.yR != null) && (this.yR.length() > 0) && (!this.yR.equalsIgnoreCase(this.yX)))
        {
          str2 = "EndDate";
          try
          {
            localDateTime = new com.ffusion.beans.DateTime(localDateFormat1.parse(this.yR), Locale.getDefault());
            str3 = localDateFormat2.format(localDateTime.getTime());
          }
          catch (ParseException localParseException2)
          {
            this.error = 1022;
            return this.taskErrorURL;
          }
          localProperties1.setProperty(str2, str3);
        }
        if (this.y1 != null)
        {
          str2 = "FixedDepositInstrumentNumber";
          str3 = this.y1;
          localProperties1.setProperty(str2, str3);
        }
        if (this.yT != null)
        {
          str2 = "FixedDepositInstrumentBankName";
          str3 = this.yT;
          localProperties1.setProperty(str2, str3);
        }
        if (this.yY.equals("Dr"))
        {
          str2 = "MaximumAmount";
          str3 = "0";
          localProperties1.setProperty(str2, str3);
        }
        else if (this.yY.equals("Cr"))
        {
          str2 = "MinimumAmount";
          str3 = "0";
          localProperties1.setProperty(str2, str3);
        }
        str2 = "TransactionStatus";
        str3 = "Completed";
        localProperties1.setProperty(str2, str3);
        localProperties1.setProperty("DataClassification", this.y2);
        boolean bool = new Boolean(localGetPagedTransactions.getSortCriteriaAsc()).booleanValue();
        localReportSortCriteria.create(1, localGetPagedTransactions.getSortCriteriaName(), bool);
        localReportSortCriteria.create(2, "TransactionIndex", bool);
        String str4 = "REPORTCATEGORY";
        String str5 = "Generic";
        localProperties2.setProperty(str4, str5);
        str4 = "REPORTTYPE";
        str5 = "AccountHistory";
        localProperties2.setProperty(str4, str5);
        if (this.yX != null)
        {
          str4 = "DATEFORMAT";
          str5 = this.yX;
          localProperties2.setProperty(str4, str5);
        }
        str4 = "SHOWEXTRAFOOTERLINE";
        str5 = "TRUE";
        localProperties2.setProperty(str4, str5);
        str4 = "EXTRAFOOTERLINE";
        str5 = ReportConsts.getText(136, localSecureUser.getLocale());
        localProperties2.setProperty(str4, str5);
        str4 = "TITLE";
        str5 = ReportConsts.getText(135, localSecureUser.getLocale());
        localProperties2.setProperty(str4, str5);
        str4 = "PAGEWIDTH";
        str5 = "750";
        localProperties2.setProperty(str4, str5);
        str4 = "PAGEWIDTH_TEXT";
        str5 = "125";
        localProperties2.setProperty(str4, str5);
        localProperties2.setProperty("SHOWTITLE", "TRUE");
        localProperties2.setProperty("SHOWCOMPANYNAME", "TRUE");
        localProperties2.setProperty("SHOWDATE", "TRUE");
        localProperties2.setProperty("SHOWTIME", "TRUE");
        localProperties2.setProperty("SHOWHEADER", "TRUE");
        HashMap localHashMap1 = new HashMap();
        localHashMap1.put("SERVICE", localBanking3);
        Object localObject4;
        if (this.yQ.equalsIgnoreCase("BAI2"))
        {
          localProperties2.setProperty("FORMAT", "BAI2");
          localObject1 = new HashMap();
          localObject1 = EntitlementsUtil.allowEntitlementBypass((HashMap)localObject1);
          localObject2 = AffiliateBankAdmin.getAffiliateBankByID(localSecureUser, localBusiness.getAffiliateBankID(), (HashMap)localObject1);
          localHashMap1.put("AFFILIATE_BANK_ROUTING_NUMBER", ((AffiliateBank)localObject2).getAffiliateRoutingNum());
          try
          {
            HashMap localHashMap2 = BAIExportSettingsXMLUtil.getBAIExportSettings(localSecureUser, localBusiness);
            localHashMap1.putAll(localHashMap2);
          }
          catch (Exception localException)
          {
            DebugLog.throwing("The call to retrieve BAI Export Settings failed.", localException);
            this.error = 3526;
            return this.taskErrorURL;
          }
        }
        else if (this.yQ.equalsIgnoreCase("COMMA"))
        {
          localProperties2.setProperty("FORMAT", "COMMA");
        }
        else if (this.yQ.equalsIgnoreCase("CSV"))
        {
          localProperties2.setProperty("FORMAT", "CSV");
        }
        else if (this.yQ.equalsIgnoreCase("TAB"))
        {
          localProperties2.setProperty("FORMAT", "TAB");
        }
        else if (this.yQ.equalsIgnoreCase("PDF"))
        {
          localProperties2.setProperty("FORMAT", "PDF");
        }
        else if (this.yQ.equalsIgnoreCase("HTML"))
        {
          localProperties2.setProperty("FORMAT", "HTML");
        }
        else if (this.yQ.equalsIgnoreCase("TEXT"))
        {
          localProperties2.setProperty("FORMAT", "TEXT");
        }
        else if (this.yQ.equalsIgnoreCase("QIF"))
        {
          localProperties2.setProperty("FORMAT", "QIF");
          localHashMap1.put("Account", localAccount);
        }
        else if ((this.yQ.equalsIgnoreCase("QFX")) || (this.yQ.equalsIgnoreCase("QBO")))
        {
          if (this.yQ.equalsIgnoreCase("QFX")) {
            localProperties2.setProperty("FORMAT", "QFX");
          } else {
            localProperties2.setProperty("FORMAT", "QBO");
          }
          localObject1 = new HashMap();
          localObject1 = EntitlementsUtil.allowEntitlementBypass((HashMap)localObject1);
          localObject2 = AffiliateBankAdmin.getAffiliateBankByID(localSecureUser, localSecureUser.getAffiliateIDValue(), (HashMap)localObject1);
          localObject3 = this.y5 != null ? this.y5 : ((AffiliateBank)localObject2).getAffiliateBankName();
          localObject4 = this.yZ != null ? this.yZ : ((AffiliateBank)localObject2).getBrandID();
          if (localObject3 != null) {
            localProperties2.setProperty("OPT_QFX_BANK_NAME", (String)localObject3);
          }
          if (localObject4 != null) {
            localProperties2.setProperty("OPT_QFX_BANK_ID", (String)localObject4);
          }
          if (this.y0 != null) {
            localProperties2.setProperty("OPT_QFX_USER_ID", this.y0);
          }
          localHashMap1.put("Account", localAccount);
        }
        else if (this.yQ.equalsIgnoreCase("IIF"))
        {
          localProperties2.setProperty("FORMAT", "IIF");
          localHashMap1.put("Account", localAccount);
        }
        localProperties2.setProperty("DESTINATION", "USER_FILE_SYSTEM");
        localProperties2.setProperty("STYLESHEET_NAME", (String)localHttpSession.getAttribute("ReportStyleSheetName"));
        Object localObject1 = "CRLF";
        if (TaskUtil.getOSType(paramHttpServletRequest).equals("Unix")) {
          localObject1 = "LF";
        }
        localProperties2.setProperty("EOL_STYLE", (String)localObject1);
        Object localObject2 = new ReportCriteria(localProperties1, localReportSortCriteria, localProperties2);
        ((ReportCriteria)localObject2).setLocale(localSecureUser.getLocale());
        ((ReportCriteria)localObject2).setHttpServletResponse(paramHttpServletResponse);
        Object localObject3 = Banking.getReportData(localSecureUser, (ReportCriteria)localObject2, localHashMap1);
        if ((localObject3 instanceof AcctTransactionRpt))
        {
          localHashMap1.put("REPORTTYPE", "AccountHistory");
          localHashMap1.put("REPORTCRITERIA", localObject2);
          jdMethod_for((AcctTransactionRpt)localObject3, localSecureUser, localHashMap1);
          localObject4 = (StringBuffer)((IReportResult)localObject3).export(this.yQ, localHashMap1);
          String str6 = ((StringBuffer)localObject4).toString();
          if ((this.yQ.equals("COMMA")) || (this.yQ.equals("CSV")))
          {
            paramHttpServletResponse.setContentType("application/text; charset=UTF-8");
            paramHttpServletResponse.setHeader("Content-disposition", "inline;filename=EXPORT.DELIM");
          }
          else if (this.yQ.equals("BAI2"))
          {
            paramHttpServletResponse.setContentType("application/text; charset=UTF-8");
            paramHttpServletResponse.setHeader("Content-disposition", "inline;filename=EXPORT.BAI2");
          }
          PrintWriter localPrintWriter = paramHttpServletResponse.getWriter();
          localPrintWriter.println(str6);
          localPrintWriter.flush();
          localPrintWriter.close();
          str1 = this.successURL;
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    if (this.error == 0) {
      str1 = this.successURL;
    }
    return str1;
  }
  
  public String processConsumerBanking(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.taskErrorURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Account localAccount = (Account)localHttpSession.getAttribute(this.y4);
    Banking3 localBanking3 = (Banking3)localHttpSession.getAttribute(this.bankingServiceName);
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    if ((this.yQ == null) || (this.yQ.length() == 0)) {
      this.error = 76;
    } else if ((localAccount == null) && (k())) {
      this.error = 1002;
    } else if (localSecureUser == null) {
      this.error = 1037;
    } else if (localBanking3 == null) {
      this.error = 1000;
    } else {
      try
      {
        this.error = 0;
        if (this.yV == null) {
          this.yV = new ReportCriteria();
        }
        Properties localProperties = new Properties();
        this.error = setFillBlankDateValues();
        if (this.error != 0) {
          return this.taskErrorURL;
        }
        this.error = validateDateRange();
        if (this.error != 0) {
          return this.taskErrorURL;
        }
        DateFormat localDateFormat1 = DateFormatUtil.getFormatter(this.yX);
        DateFormat localDateFormat2 = DateFormatUtil.getFormatter("MM/dd/yyyy");
        String str2 = this.yV.getSearchCriteria().getProperty("StartDate");
        String str3 = this.yV.getSearchCriteria().getProperty("EndDate");
        if ((str2 != null) && (str2.length() > 0) && (!str2.equalsIgnoreCase(this.yX)))
        {
          localObject1 = null;
          str4 = null;
          try
          {
            localObject1 = new com.ffusion.beans.DateTime(localDateFormat1.parse(str2), Locale.getDefault());
            str4 = localDateFormat2.format(((com.ffusion.beans.DateTime)localObject1).getTime());
          }
          catch (ParseException localParseException1)
          {
            this.error = 1021;
            return this.taskErrorURL;
          }
          this.yV.getSearchCriteria().setProperty("StartDate", str4);
        }
        else
        {
          this.yV.getSearchCriteria().remove("StartDate");
        }
        if ((str3 != null) && (str3.length() > 0) && (!str3.equalsIgnoreCase(this.yX)))
        {
          localObject1 = null;
          str4 = null;
          try
          {
            localObject1 = new com.ffusion.beans.DateTime(localDateFormat1.parse(str3), Locale.getDefault());
            str4 = localDateFormat2.format(((com.ffusion.beans.DateTime)localObject1).getTime());
          }
          catch (ParseException localParseException2)
          {
            this.error = 1022;
            return this.taskErrorURL;
          }
          this.yV.getSearchCriteria().setProperty("EndDate", str4);
        }
        else
        {
          this.yV.getSearchCriteria().remove("EndDate");
        }
        Object localObject1 = "REPORTTYPE";
        String str4 = "ConsumerAccountHistory";
        localProperties.setProperty((String)localObject1, str4);
        if (this.yX != null)
        {
          localObject1 = "DATEFORMAT";
          str4 = this.yX;
          localProperties.setProperty((String)localObject1, str4);
        }
        localObject1 = "SHOWEXTRAFOOTERLINE";
        str4 = "TRUE";
        localProperties.setProperty((String)localObject1, str4);
        localObject1 = "EXTRAFOOTERLINE";
        str4 = ReportConsts.getText(136, localSecureUser.getLocale());
        localProperties.setProperty((String)localObject1, str4);
        localObject1 = "TITLE";
        str4 = ReportConsts.getText(135, localSecureUser.getLocale());
        localProperties.setProperty((String)localObject1, str4);
        localObject1 = "PAGEWIDTH";
        str4 = "750";
        localProperties.setProperty((String)localObject1, str4);
        localObject1 = "PAGEWIDTH_TEXT";
        str4 = "125";
        localProperties.setProperty((String)localObject1, str4);
        localProperties.setProperty("SHOWTITLE", "TRUE");
        localProperties.setProperty("SHOWHEADER", "TRUE");
        HashMap localHashMap = new HashMap();
        localHashMap.put("SERVICE", localBanking3);
        if (this.yQ.equalsIgnoreCase("COMMA"))
        {
          localProperties.setProperty("FORMAT", "COMMA");
        }
        else if (this.yQ.equalsIgnoreCase("CSV"))
        {
          localProperties.setProperty("FORMAT", "CSV");
        }
        else if (this.yQ.equalsIgnoreCase("TAB"))
        {
          localProperties.setProperty("FORMAT", "TAB");
        }
        else if (this.yQ.equalsIgnoreCase("PDF"))
        {
          localProperties.setProperty("FORMAT", "PDF");
        }
        else if (this.yQ.equalsIgnoreCase("HTML"))
        {
          localProperties.setProperty("FORMAT", "HTML");
        }
        else if (this.yQ.equalsIgnoreCase("TEXT"))
        {
          localProperties.setProperty("FORMAT", "TEXT");
        }
        else if (this.yQ.equalsIgnoreCase("QIF"))
        {
          localProperties.setProperty("FORMAT", "QIF");
          localHashMap.put("Account", localAccount);
        }
        else if ((this.yQ.equalsIgnoreCase("QFX")) || (this.yQ.equalsIgnoreCase("QBO")))
        {
          if (this.yQ.equalsIgnoreCase("QFX")) {
            localProperties.setProperty("FORMAT", "QFX");
          } else {
            localProperties.setProperty("FORMAT", "QBO");
          }
          localObject2 = new HashMap();
          localObject2 = EntitlementsUtil.allowEntitlementBypass((HashMap)localObject2);
          localObject3 = AffiliateBankAdmin.getAffiliateBankByID(localSecureUser, localSecureUser.getAffiliateIDValue(), (HashMap)localObject2);
          localObject4 = this.y5 != null ? this.y5 : ((AffiliateBank)localObject3).getAffiliateBankName();
          str5 = this.yZ != null ? this.yZ : ((AffiliateBank)localObject3).getBrandID();
          if (localObject4 != null) {
            localProperties.setProperty("OPT_QFX_BANK_NAME", (String)localObject4);
          }
          if (str5 != null) {
            localProperties.setProperty("OPT_QFX_BANK_ID", str5);
          }
          if (this.y0 != null) {
            localProperties.setProperty("OPT_QFX_USER_ID", this.y0);
          }
          localHashMap.put("Account", localAccount);
        }
        else if (this.yQ.equalsIgnoreCase("OFX"))
        {
          localProperties.setProperty("FORMAT", "OFX");
          localHashMap.put("Account", localAccount);
        }
        localProperties.setProperty("DESTINATION", "USER_FILE_SYSTEM");
        localProperties.setProperty("STYLESHEET_NAME", (String)localHttpSession.getAttribute("ReportStyleSheetName"));
        Object localObject2 = "CRLF";
        if (TaskUtil.getOSType(paramHttpServletRequest).equals("Unix")) {
          localObject2 = "LF";
        }
        localProperties.setProperty("EOL_STYLE", (String)localObject2);
        this.yV.setReportOptions(localProperties);
        this.yV.setHttpServletResponse(paramHttpServletResponse);
        Object localObject3 = new HashMap();
        Object localObject4 = new HashMap();
        String str5 = this.yV.getSearchCriteria().getProperty("Accounts");
        if (str5 == null)
        {
          this.error = 1002;
          return this.taskErrorURL;
        }
        StringTokenizer localStringTokenizer = new StringTokenizer(str5, ",");
        int i = localStringTokenizer.countTokens();
        Object localObject7;
        Object localObject8;
        Object localObject9;
        Object localObject10;
        for (int j = 0; j < i; j++)
        {
          str6 = localStringTokenizer.nextToken();
          localObject5 = new StringTokenizer(str6, ":");
          int k = ((StringTokenizer)localObject5).countTokens();
          if (k == 5)
          {
            localObject7 = ((StringTokenizer)localObject5).nextToken();
            localObject8 = ((StringTokenizer)localObject5).nextToken();
            localObject9 = ((StringTokenizer)localObject5).nextToken();
            localObject10 = new Account(Locale.getDefault(), (String)localObject7, (String)localObject9, (String)localObject8);
            String str7 = (String)localObject7 + ":" + (String)localObject8 + ":" + (String)localObject9;
            com.ffusion.util.beans.DateTime localDateTime = UserAdmin.getLastExportedDateTime(localSecureUser, (Account)localObject10, "ConsumerAccountHistory", new HashMap());
            ((HashMap)localObject3).put(str7, localDateTime);
          }
          else
          {
            this.error = 1043;
            return this.taskErrorURL;
          }
        }
        ArrayList localArrayList = new ArrayList();
        String str6 = this.yV.getSearchCriteria().getProperty("Date Range Type");
        localArrayList.add("Accounts");
        if ("Relative".equals(str6)) {
          localArrayList.add("Date Range");
        }
        localArrayList.add("StartDate");
        localArrayList.add("EndDate");
        this.yV.setSearchCriteriaOrder(localArrayList);
        localHashMap.put("LAST EXPORT DATES", localObject3);
        localHashMap.put("CURRENT EXPORT DATES", localObject4);
        Object localObject5 = Banking.getReportData(localSecureUser, this.yV, localHashMap);
        Object localObject6;
        if ((localSecureUser.getUserType() == 1) && (localSecureUser.getAgent() == null))
        {
          localObject6 = (HashMap)localHashMap.get("CURRENT EXPORT DATES");
          if ((localObject6 == null) || (((HashMap)localObject6).size() != ((HashMap)localObject3).size()))
          {
            this.error = 1044;
            return this.taskErrorURL;
          }
          localObject7 = ((HashMap)localObject6).keySet();
          localObject8 = ((Set)localObject7).iterator();
          while (((Iterator)localObject8).hasNext())
          {
            localObject9 = (Account)((Iterator)localObject8).next();
            localObject10 = (com.ffusion.util.beans.DateTime)((HashMap)localObject6).get(localObject9);
            UserAdmin.setLastExportedDateTime(localSecureUser, (Account)localObject9, "ConsumerAccountHistory", (com.ffusion.util.beans.DateTime)localObject10, new HashMap());
          }
        }
        str2 = this.yV.getSearchCriteria().getProperty("StartDate");
        str3 = this.yV.getSearchCriteria().getProperty("EndDate");
        if ((str2 != null) && (str2.length() > 0) && (!str2.equalsIgnoreCase(this.yX)))
        {
          localObject6 = null;
          localObject7 = null;
          try
          {
            localObject6 = new com.ffusion.beans.DateTime(localDateFormat2.parse(str2), Locale.getDefault());
            localObject7 = localDateFormat1.format(((com.ffusion.beans.DateTime)localObject6).getTime());
          }
          catch (ParseException localParseException3) {}
          this.yV.getSearchCriteria().setProperty("StartDate", (String)localObject7);
        }
        else
        {
          this.yV.getSearchCriteria().remove("StartDate");
        }
        if ((str3 != null) && (str3.length() > 0) && (!str3.equalsIgnoreCase(this.yX)))
        {
          localObject6 = null;
          localObject7 = null;
          try
          {
            localObject6 = new com.ffusion.beans.DateTime(localDateFormat2.parse(str3), Locale.getDefault());
            localObject7 = localDateFormat1.format(((com.ffusion.beans.DateTime)localObject6).getTime());
          }
          catch (ParseException localParseException4) {}
          this.yV.getSearchCriteria().setProperty("EndDate", (String)localObject7);
        }
        else
        {
          this.yV.getSearchCriteria().remove("EndDate");
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str1 = this.serviceErrorURL;
      }
    }
    if (this.error == 0) {
      str1 = this.successURL;
    }
    return str1;
  }
  
  private boolean k()
  {
    return (this.yQ.equalsIgnoreCase("OFX")) || (this.yQ.equalsIgnoreCase("QFX")) || (this.yQ.equalsIgnoreCase("QBO")) || (this.yQ.equalsIgnoreCase("QIF")) || (this.yQ.equalsIgnoreCase("IIF"));
  }
  
  public void setClearSearchCriteriaValue(String paramString)
  {
    this.yV.getSearchCriteria().remove(paramString);
  }
  
  public void setClearSearchCriteria(String paramString)
  {
    if ((this.yV == null) || (this.yV.getSearchCriteria() == null)) {
      return;
    }
    this.yV.getSearchCriteria().clear();
    this.y3 = null;
  }
  
  public void setSearchCriteriaKey(String paramString)
  {
    this.y3 = paramString;
  }
  
  public void setSearchCriteriaValue(String paramString)
  {
    if (this.y3 == null) {
      return;
    }
    if (this.yV == null)
    {
      this.yV = new ReportCriteria();
      this.yV.setSearchCriteria(new Properties());
    }
    if ((paramString != null) && (paramString.length() > 0)) {
      this.yV.getSearchCriteria().setProperty(this.y3, paramString);
    } else {
      this.yV.getSearchCriteria().remove(this.y3);
    }
  }
  
  public String getSearchCriteriaValue()
  {
    if (this.y3 == null) {
      return null;
    }
    if (this.yV == null) {
      return null;
    }
    if (this.yV.getSearchCriteria() == null) {
      return null;
    }
    return this.yV.getSearchCriteria().getProperty(this.y3);
  }
  
  public void setSortCriteriaOrdinal(String paramString)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      return;
    }
    if (this.yV == null)
    {
      this.yV = new ReportCriteria();
      this.yV.setSearchCriteria(new Properties());
    }
    for (int j = 0; j < this.yV.getSortCriteria().size(); j++)
    {
      ReportSortCriterion localReportSortCriterion = (ReportSortCriterion)this.yV.getSortCriteria().get(j);
      if (localReportSortCriterion.getOrdinal() == i)
      {
        this.yU = localReportSortCriterion;
        return;
      }
    }
    this.yU = this.yV.getSortCriteria().create(i, "", true);
  }
  
  public void setSortCriteriaName(String paramString)
  {
    if (this.yU == null) {
      return;
    }
    this.yU.setName(paramString);
  }
  
  public void setSortCriteriaAsc(String paramString)
  {
    if (this.yU == null) {
      return;
    }
    try
    {
      this.yU.setAsc(Boolean.valueOf(paramString).booleanValue());
    }
    catch (Exception localException) {}
  }
  
  public void setSortedBy(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, ",");
    String str1 = new String();
    if (localStringTokenizer.hasMoreTokens()) {
      str1 = (String)localStringTokenizer.nextElement();
    }
    int i = 0;
    if (localStringTokenizer.hasMoreTokens())
    {
      String str2 = (String)localStringTokenizer.nextElement();
      if ((str2 != null) && (str2.trim().equalsIgnoreCase("REVERSE"))) {
        i = 1;
      }
    }
    this.yU.setName(str1);
    this.yU.setAsc(i == 0);
  }
  
  public void setClearSortCriteria(String paramString)
  {
    if (this.yV == null) {
      return;
    }
    this.yV.getSortCriteria().clear();
    this.yU = null;
  }
  
  public void setIsConsumerBanking(String paramString)
  {
    this.yS = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setbankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  private static void jdMethod_for(AcctTransactionRpt paramAcctTransactionRpt, SecureUser paramSecureUser, HashMap paramHashMap)
    throws CSILException
  {
    com.ffusion.beans.accounts.Accounts localAccounts1 = com.ffusion.csil.core.Accounts.getAccountsByBusinessEmployee(paramSecureUser, paramHashMap);
    com.ffusion.beans.accounts.Accounts localAccounts2 = paramAcctTransactionRpt.getAccounts();
    Iterator localIterator = localAccounts2.iterator();
    while (localIterator.hasNext())
    {
      Account localAccount1 = (Account)localIterator.next();
      Account localAccount2 = localAccounts1.getByIDAndBankIDAndRoutingNum(localAccount1.getID(), localAccount1.getBankID(), localAccount1.getRoutingNum());
      if (localAccount2 != null) {
        localAccount1.setNickName(localAccount2.getNickName());
      }
    }
  }
  
  protected int validateDateRange()
  {
    int i = 0;
    com.ffusion.beans.DateTime localDateTime1 = null;
    com.ffusion.beans.DateTime localDateTime2 = null;
    String str = this.yV.getSearchCriteria().getProperty("Date Range Type");
    if ("Absolute".equals(str))
    {
      if ((this.yW != null) && (this.yW.length() > 0) && (!this.yW.equalsIgnoreCase(this.yX))) {
        try
        {
          DateFormat localDateFormat1 = DateFormatUtil.getFormatter(this.yX);
          localDateTime1 = new com.ffusion.beans.DateTime(localDateFormat1.parse(this.yW), Locale.getDefault());
        }
        catch (Exception localException1)
        {
          i = 1021;
          return i;
        }
      }
      if ((this.yR != null) && (this.yR.length() > 0) && (!this.yR.equalsIgnoreCase(this.yX))) {
        try
        {
          DateFormat localDateFormat2 = DateFormatUtil.getFormatter(this.yX);
          localDateTime2 = new com.ffusion.beans.DateTime(localDateFormat2.parse(this.yR), Locale.getDefault());
        }
        catch (Exception localException2)
        {
          i = 1022;
          return i;
        }
      }
      if ((localDateTime1 != null) && (localDateTime2 != null) && (localDateTime1.after(localDateTime2))) {
        return 79;
      }
      com.ffusion.beans.DateTime localDateTime3;
      if (this._enforceNoFutureDatesCheck)
      {
        localDateTime3 = new com.ffusion.beans.DateTime();
        if ((localDateTime1 != null) && (localDateTime1.after(localDateTime3))) {
          return 149;
        }
        if ((localDateTime2 != null) && (localDateTime2.after(localDateTime3))) {
          return 150;
        }
      }
      if ((this._enforceMaxDateRangeCheck) && (localDateTime2 != null) && (localDateTime1 != null))
      {
        localDateTime3 = new com.ffusion.beans.DateTime(localDateTime2, Locale.getDefault());
        localDateTime3.add(5, -1 * this._maxDaysInDateRange);
        localDateTime3 = jdMethod_new(localDateTime3.getTime());
        if (localDateTime1.before(localDateTime3)) {
          return 148;
        }
      }
    }
    return i;
  }
  
  protected int setFillBlankDateValues()
  {
    String str1 = this.yV.getSearchCriteria().getProperty("StartDate");
    com.ffusion.beans.DateTime localDateTime1 = null;
    String str2 = this.yV.getSearchCriteria().getProperty("EndDate");
    com.ffusion.beans.DateTime localDateTime2 = null;
    DateFormat localDateFormat = DateFormatUtil.getFormatter(this.yX);
    if (((str2 == null) || (str2.length() == 0) || (str2.equalsIgnoreCase(this.yX))) && ((str1 == null) || (str1.length() == 0) || (str1.equalsIgnoreCase(this.yX))))
    {
      localDateTime2 = jdMethod_try(new Date());
      str2 = localDateFormat.format(localDateTime2.getTime());
      this.yV.getSearchCriteria().setProperty("EndDate", str2);
      localDateTime1 = jdMethod_new(localDateTime2.getTime());
      localDateTime1.add(5, -this._maxDaysInDateRange);
      str1 = localDateFormat.format(localDateTime1.getTime());
      this.yV.getSearchCriteria().setProperty("StartDate", str1);
    }
    else if (((str2 == null) || (str2.length() == 0) || (str2.equalsIgnoreCase(this.yX))) && (str1 != null) && (str1.length() != 0) && (!str1.equalsIgnoreCase(this.yX)))
    {
      try
      {
        localDateTime1 = new com.ffusion.beans.DateTime(localDateFormat.parse(str1), Locale.getDefault());
        localDateTime1 = jdMethod_new(localDateTime1.getTime());
      }
      catch (ParseException localParseException1)
      {
        return 1021;
      }
      localDateTime2 = jdMethod_try(localDateTime1.getTime());
      localDateTime2.add(5, this._maxDaysInDateRange);
      if (localDateTime2.getTimeInMillis() > System.currentTimeMillis()) {
        localDateTime2 = jdMethod_try(new Date());
      }
      str2 = localDateFormat.format(localDateTime2.getTime());
      this.yV.getSearchCriteria().setProperty("EndDate", str2);
    }
    else if ((str2 != null) && (str2.length() != 0) && (!str2.equalsIgnoreCase(this.yX)) && ((str1 == null) || (str1.length() == 0) || (str1.equalsIgnoreCase(this.yX))))
    {
      try
      {
        localDateTime2 = new com.ffusion.beans.DateTime(localDateFormat.parse(str2), Locale.getDefault());
        localDateTime2 = jdMethod_try(localDateTime2.getTime());
      }
      catch (ParseException localParseException2)
      {
        return 1022;
      }
      localDateTime1 = jdMethod_new(localDateTime2.getTime());
      localDateTime1.add(5, -this._maxDaysInDateRange);
      str1 = localDateFormat.format(localDateTime1.getTime());
      this.yV.getSearchCriteria().setProperty("StartDate", str1);
    }
    return 0;
  }
  
  public void setEnforceMaxDateRangeCheck(String paramString)
  {
    this._enforceMaxDateRangeCheck = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setMaxDaysInDateRange(String paramString)
  {
    this._maxDaysInDateRange = Integer.parseInt(paramString);
  }
  
  private com.ffusion.beans.DateTime jdMethod_try(Date paramDate)
  {
    com.ffusion.beans.DateTime localDateTime = new com.ffusion.beans.DateTime(paramDate, Locale.getDefault());
    localDateTime.set(11, 23);
    localDateTime.set(12, 59);
    localDateTime.set(13, 59);
    localDateTime.set(14, 999);
    return localDateTime;
  }
  
  private com.ffusion.beans.DateTime jdMethod_new(Date paramDate)
  {
    com.ffusion.beans.DateTime localDateTime = new com.ffusion.beans.DateTime(paramDate, Locale.getDefault());
    localDateTime.set(11, 0);
    localDateTime.set(12, 0);
    localDateTime.set(13, 0);
    localDateTime.set(14, 0);
    return localDateTime;
  }
  
  public void setEnforceNoFutureDatesCheck(String paramString)
  {
    this._enforceNoFutureDatesCheck = Boolean.valueOf(paramString).booleanValue();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.ExportHistory
 * JD-Core Version:    0.7.0.1
 */