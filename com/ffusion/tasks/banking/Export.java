package com.ffusion.tasks.banking;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLUtil;
import java.io.IOException;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.TimeZone;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Export
  extends BaseTask
  implements Task
{
  protected static String FORMAT = "FORMAT";
  protected static String STARTDATE = "STARTDATE";
  protected static String ENDDATE = "ENDDATE";
  protected static String ENDBEFORESTART = "ENDBEFORESTART";
  protected static String HASTRANSACTIONS = "HASTRANSACTIONS";
  protected String validate;
  protected boolean processFlag = false;
  protected boolean initFlag = false;
  protected Locale locale;
  protected String startDateStr;
  protected String endDateStr;
  protected DateTime startDate;
  protected DateTime endDate;
  protected DateTime firstDate;
  protected DateTime lastDate;
  protected String formatStr;
  protected int format = 0;
  protected String datetype = "SHORT";
  protected boolean asMemo = false;
  protected String noTransactionsURL;
  protected String comma_delimiter = ",";
  protected String eol_delimiter = "\r";
  protected String transactionsName = "Transactions";
  protected boolean useTransactionsName = false;
  protected Transactions transactionsToUse = null;
  protected String qfx_bankName = "";
  protected String qfx_bankID = "";
  protected String qfx_userID = "";
  protected static final String ACCTTYPE_UNKNOWN = "UNKNOWN";
  protected String[] acctTypes = { "CHECKING", "SAVINGS", "CREDITLINE", "MONEYMRKT", "CREDITCARD" };
  protected int[] acctMap = { 1, 2, 7, 12, 3 };
  protected static String[] transType = { "CREDIT", "DEBIT", "INT", "DIV", "FEE", "SRVCHG", "DEP", "ATM", "POS", "XFER", "CHECK", "PAYMENT", "CASH", "DIRECTDEP", "DIRECTDEBIT", "REPEATPMT", "OTHER", "POS", "ATM", "INT", "FEE" };
  protected static int[] transMap = { 4, 5, 11, 18, 10, 23, 1, 7, 9, 16, 3, 25, 28, 29, 12, 13, 30, 8, 6, 24, 22 };
  public static final int UNKNOWN = 0;
  public static final int OFX = 1;
  public static final int QIF98 = 2;
  public static final int QIF99 = 3;
  public static final int COMMA_INT = 4;
  public static final int QFX99 = 5;
  public static final int OFX_MMAS = 6;
  public static final int QFX_QBO = 7;
  public static final String OFX_STR = "OFX";
  public static final String QIF98_STR = "QIF98";
  public static final String QIF99_STR = "QIF99";
  public static final String COMMA_STR = "COMMA";
  public static final String QFX99_STR = "QFX99";
  public static final String OFX_MMAS_STR = "OFX_MMAS";
  public static final String QFX_QBO_STR = "QFX_QBO";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    this.locale = ((Locale)localHttpSession.getAttribute("java.util.Locale"));
    Account localAccount = (Account)localHttpSession.getAttribute("Account");
    this.error = 0;
    this.transactionsToUse = null;
    if (this.useTransactionsName == true) {
      this.transactionsToUse = ((Transactions)localHttpSession.getAttribute(this.transactionsName));
    }
    String str = this.taskErrorURL;
    if (this.locale == null) {
      this.error = 41;
    } else if (localAccount == null) {
      this.error = 1002;
    } else if (this.initFlag) {
      str = init(localHttpSession);
    } else {
      str = process(paramHttpServletResponse, localAccount);
    }
    return str;
  }
  
  protected String init(HttpSession paramHttpSession)
  {
    this.initFlag = false;
    return this.successURL;
  }
  
  protected String process(HttpServletResponse paramHttpServletResponse, Account paramAccount)
    throws IOException
  {
    String str = null;
    boolean bool = true;
    if (this.validate != null) {
      bool = validateInput();
    }
    if (bool)
    {
      if (this.processFlag)
      {
        Transactions localTransactions = getTransactionsToExport(paramAccount);
        if (this.error != 0)
        {
          str = this.taskErrorURL;
        }
        else if (localTransactions == null)
        {
          this.error = 1024;
          str = this.taskErrorURL;
        }
        else
        {
          export(paramHttpServletResponse, paramAccount, localTransactions);
          str = "";
        }
      }
      else
      {
        str = this.successURL;
      }
    }
    else {
      str = this.taskErrorURL;
    }
    return str;
  }
  
  protected boolean validateInput()
  {
    int i = this.validate.indexOf(HASTRANSACTIONS) != -1 ? 1 : 0;
    DateTime localDateTime1 = new DateTime(Calendar.getInstance(), Locale.US);
    if (((this.validate.indexOf(FORMAT) != -1) || (i != 0)) && (this.format == 0) && (this.formatStr == null)) {
      this.error = 1020;
    }
    DateTime localDateTime2;
    if ((this.error == 0) && ((this.validate.indexOf(STARTDATE) != -1) || (i != 0)))
    {
      localDateTime2 = null;
      if (this.startDateStr == null) {
        this.error = 1021;
      } else {
        try
        {
          localDateTime2 = new DateTime(this.startDateStr, this.locale, this.datetype);
          if ((localDateTime2 == null) || (!localDateTime2.isValid()) || (localDateTime2.after(localDateTime1))) {
            this.error = 1021;
          }
        }
        catch (InvalidDateTimeException localInvalidDateTimeException1)
        {
          this.error = 1021;
        }
      }
    }
    if ((this.error == 0) && ((this.validate.indexOf(ENDDATE) != -1) || (i != 0)))
    {
      localDateTime2 = null;
      if (this.endDateStr == null) {
        this.error = 1022;
      } else {
        try
        {
          localDateTime2 = new DateTime(this.endDateStr, this.locale, this.datetype);
          if ((localDateTime2 == null) || (!localDateTime2.isValid()) || (localDateTime2.after(localDateTime1))) {
            this.error = 1022;
          }
        }
        catch (InvalidDateTimeException localInvalidDateTimeException2)
        {
          this.error = 1022;
        }
      }
    }
    setStartDate();
    setEndDate();
    if ((this.error == 0) && ((this.validate.indexOf(STARTDATE) != -1) || (this.validate.indexOf(ENDDATE) != -1) || (i != 0)) && (this.startDate != null) && (this.endDate != null) && (!this.startDate.before(this.endDate))) {
      this.error = 1023;
    }
    this.validate = null;
    return this.error == 0;
  }
  
  protected Transactions getTransactionsToExport(Account paramAccount)
  {
    Transactions localTransactions1 = null;
    if (!setStartDate())
    {
      this.error = 1021;
    }
    else if (!setEndDate())
    {
      this.error = 1022;
    }
    else if (!setFormat())
    {
      this.error = 1020;
    }
    else
    {
      Transactions localTransactions2 = paramAccount.getTransactions();
      if ((this.useTransactionsName) && (this.transactionsToUse != null)) {
        localTransactions2 = this.transactionsToUse;
      }
      localTransactions1 = getTransactionsToExport(localTransactions2);
      this.error = 0;
    }
    return localTransactions1;
  }
  
  protected Transactions getTransactionsToExport(Transactions paramTransactions)
  {
    Transactions localTransactions = new Transactions(this.locale);
    if (paramTransactions != null)
    {
      Iterator localIterator = paramTransactions.iterator();
      while (localIterator.hasNext())
      {
        Transaction localTransaction = (Transaction)localIterator.next();
        if (((this.startDate == null) || (!this.startDate.after(localTransaction.getDateValue()))) && ((this.endDate == null) || (!this.endDate.before(localTransaction.getDateValue())))) {
          localTransactions.add(localTransaction);
        }
      }
    }
    return localTransactions;
  }
  
  protected boolean setStartDate()
  {
    boolean bool = true;
    try
    {
      if (this.startDateStr == null)
      {
        this.startDate = new DateTime(this.startDateStr, this.locale, this.datetype);
      }
      else
      {
        if (this.startDate == null) {
          this.startDate = new DateTime(this.startDateStr, this.locale, this.datetype);
        }
        this.startDate.fromString(this.startDateStr);
      }
      this.startDate.set(11, 0);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.startDate = null;
      this.startDateStr = null;
      bool = false;
    }
    return bool;
  }
  
  protected boolean setEndDate()
  {
    boolean bool = true;
    try
    {
      if (this.endDateStr == null)
      {
        this.endDate = new DateTime(this.endDateStr, this.locale, this.datetype);
      }
      else
      {
        if (this.endDate == null) {
          this.endDate = new DateTime(this.endDateStr, this.locale, this.datetype);
        }
        this.endDate.fromString(this.endDateStr);
      }
      this.endDate.set(11, 23);
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      this.startDate = null;
      this.startDateStr = null;
      bool = false;
    }
    return bool;
  }
  
  protected boolean setFormat()
  {
    boolean bool = true;
    if (this.formatStr.equals("OFX")) {
      this.format = 1;
    } else if (this.formatStr.equals("QIF98")) {
      this.format = 2;
    } else if (this.formatStr.equals("QIF99")) {
      this.format = 3;
    } else if (this.formatStr.equals("COMMA")) {
      this.format = 4;
    } else if (this.formatStr.equals("QFX99")) {
      this.format = 5;
    } else if (this.formatStr.equals("QFX_QBO")) {
      this.format = 7;
    } else if (this.formatStr.equals("OFX_MMAS")) {
      this.format = 6;
    } else {
      bool = false;
    }
    return bool;
  }
  
  protected void export(HttpServletResponse paramHttpServletResponse, Account paramAccount, Transactions paramTransactions)
    throws IOException
  {
    try
    {
      switch (this.format)
      {
      case 1: 
      case 5: 
      case 6: 
      case 7: 
        exportOfx(paramHttpServletResponse, paramAccount, paramTransactions);
        break;
      case 2: 
        exportQif(paramHttpServletResponse, paramTransactions, false, paramAccount);
        break;
      case 3: 
        exportQif(paramHttpServletResponse, paramTransactions, true, paramAccount);
        break;
      case 4: 
        exportComma(paramHttpServletResponse, paramAccount, paramTransactions);
      }
    }
    catch (Exception localException) {}
  }
  
  protected void exportQif(HttpServletResponse paramHttpServletResponse, Transactions paramTransactions, boolean paramBoolean)
    throws IOException
  {
    exportQif(paramHttpServletResponse, paramTransactions, paramBoolean, null);
  }
  
  protected void exportQif(HttpServletResponse paramHttpServletResponse, Transactions paramTransactions, boolean paramBoolean, Account paramAccount)
    throws IOException
  {
    paramHttpServletResponse.setContentType("application/text; charset=UTF-8");
    paramHttpServletResponse.setHeader("Content-disposition", "inline;filename=EXPORT.QIF");
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    Iterator localIterator = paramTransactions.iterator();
    if (paramAccount != null) {
      if (paramAccount.isFilterable("Credit")) {
        localServletOutputStream.println("!Type:CCard");
      } else {
        localServletOutputStream.println("!Type:Bank");
      }
    }
    while (localIterator.hasNext())
    {
      Transaction localTransaction = (Transaction)localIterator.next();
      StringBuffer localStringBuffer = new StringBuffer();
      if (paramBoolean) {
        localTransaction.setDateFormat("MM/dd/yyyy");
      } else {
        localTransaction.setDateFormat("SHORT");
      }
      if (localTransaction.getDate() != null)
      {
        localStringBuffer.append('D');
        localStringBuffer.append(localTransaction.getDate());
        localStringBuffer.append("\r\n");
      }
      if (localTransaction.getDescription() != null)
      {
        if (this.asMemo) {
          localStringBuffer.append('M');
        } else {
          localStringBuffer.append('P');
        }
        localStringBuffer.append(localTransaction.getDescription());
        localStringBuffer.append("\r\n");
      }
      if ((localTransaction.getMemo() != null) && (!this.asMemo))
      {
        localStringBuffer.append('M');
        localStringBuffer.append(localTransaction.getMemo());
        localStringBuffer.append("\r\n");
      }
      String str = getTransactionCheckNum(localTransaction);
      if (str != null)
      {
        localStringBuffer.append('N');
        localStringBuffer.append(str);
        localStringBuffer.append("\r\n");
      }
      localStringBuffer.append('T');
      localStringBuffer.append(formatAmount(localTransaction.getAmountValue()));
      localStringBuffer.append("\r\n^");
      localServletOutputStream.println(localStringBuffer.toString());
    }
    localServletOutputStream.flush();
    localServletOutputStream.close();
  }
  
  protected void exportComma(HttpServletResponse paramHttpServletResponse, Account paramAccount, Transactions paramTransactions)
    throws IOException
  {
    paramHttpServletResponse.setContentType("application/text; charset=UTF-8");
    paramHttpServletResponse.setHeader("Content-disposition", "inline;filename=EXPORT.DELIM");
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    Iterator localIterator = paramTransactions.iterator();
    while (localIterator.hasNext())
    {
      Transaction localTransaction = (Transaction)localIterator.next();
      StringBuffer localStringBuffer = new StringBuffer();
      localTransaction.setDateFormat(ResourceUtil.getString("DateFormat", "com.ffusion.beans.user.resources", this.locale));
      if (localTransaction.getDate() != null) {
        localStringBuffer.append(localTransaction.getDate());
      }
      localStringBuffer.append(this.comma_delimiter);
      if (localTransaction.getDescription() != null) {
        localStringBuffer.append(localTransaction.getDescription());
      }
      localStringBuffer.append(this.comma_delimiter);
      String str = null;
      str = getTransactionCheckNum(localTransaction);
      if (str != null) {
        localStringBuffer.append(str);
      }
      localStringBuffer.append(this.comma_delimiter);
      localStringBuffer.append(formatAmount(localTransaction.getAmountValue()));
      localStringBuffer.append(this.eol_delimiter);
      localServletOutputStream.println(localStringBuffer.toString());
    }
    localServletOutputStream.flush();
    localServletOutputStream.close();
  }
  
  protected static String getTransactionCheckNum(Transaction paramTransaction)
  {
    return paramTransaction.getReferenceNumber();
  }
  
  protected void exportOfx(HttpServletResponse paramHttpServletResponse, Account paramAccount, Transactions paramTransactions)
    throws IOException
  {
    if (this.format == 5)
    {
      paramHttpServletResponse.setContentType("application/vnd.intuit.QFX");
      paramHttpServletResponse.setHeader("Content-disposition", "inline;filename=EXPORT.QFX");
    }
    else if (this.format == 7)
    {
      paramHttpServletResponse.setContentType("application/vnd.intuit.QBO");
      paramHttpServletResponse.setHeader("Content-disposition", "inline;filename=EXPORT.QBO");
    }
    else if (this.format == 6)
    {
      paramHttpServletResponse.setContentType("application/x-ofc");
      paramHttpServletResponse.setHeader("Content-disposition", "inline;filename=EXPORT.OFC");
    }
    else
    {
      paramHttpServletResponse.setContentType("application/x-ofx");
      paramHttpServletResponse.setHeader("Content-disposition", "inline;filename=EXPORT.OFX");
    }
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd120000");
    TimeZone localTimeZone = TimeZone.getTimeZone("UTC");
    localSimpleDateFormat.setTimeZone(localTimeZone);
    GregorianCalendar localGregorianCalendar = new GregorianCalendar(localTimeZone);
    String str = localSimpleDateFormat.format(localGregorianCalendar.getTime());
    StringBuffer localStringBuffer = new StringBuffer();
    if (paramAccount.getTypeValue() == 3) {
      exportOfxCredit(paramTransactions, paramAccount, localSimpleDateFormat, str, localStringBuffer);
    } else {
      exportOfxOther(paramTransactions, paramAccount, localSimpleDateFormat, str, localStringBuffer);
    }
    ServletOutputStream localServletOutputStream = paramHttpServletResponse.getOutputStream();
    localServletOutputStream.println(localStringBuffer.toString());
    localServletOutputStream.flush();
    localServletOutputStream.close();
  }
  
  protected static void exportOfxHeader(StringBuffer paramStringBuffer)
  {
    paramStringBuffer.append("OFXHEADER:100\r\n");
    paramStringBuffer.append("DATA:OFXSGML\r\n");
    paramStringBuffer.append("VERSION:102\r\n");
    paramStringBuffer.append("SECURITY:NONE\r\n");
    paramStringBuffer.append("ENCODING:USASCII\r\n");
    paramStringBuffer.append("CHARSET:1252\r\n");
    paramStringBuffer.append("COMPRESSION:NONE\r\n");
    paramStringBuffer.append("OLDFILEUID:NONE\r\n");
    paramStringBuffer.append("NEWFILEUID:NONE\r\n");
    paramStringBuffer.append("\r\n");
  }
  
  protected void exportOfxSignOn(StringBuffer paramStringBuffer, String paramString)
  {
    paramStringBuffer.append("<SIGNONMSGSRSV1>\r\n");
    paramStringBuffer.append("<SONRS>\r\n");
    paramStringBuffer.append("<STATUS>\r\n");
    paramStringBuffer.append("<CODE>0\r\n");
    paramStringBuffer.append("<SEVERITY>INFO\r\n");
    paramStringBuffer.append("</STATUS>\r\n");
    paramStringBuffer.append("<DTSERVER>" + XMLUtil.XMLEncode(paramString) + "\r\n");
    paramStringBuffer.append("<LANGUAGE>ENG\r\n");
    if ((this.format == 5) || (this.format == 7))
    {
      if (((this.qfx_bankName != null) && (this.qfx_bankName.length() > 0)) || ((this.qfx_bankID != null) && (this.qfx_bankID.length() > 0)))
      {
        paramStringBuffer.append("<FI>\r\n");
        if ((this.qfx_bankName != null) && (this.qfx_bankName.length() > 0)) {
          paramStringBuffer.append("<ORG>" + this.qfx_bankName + "\r\n");
        }
        if ((this.qfx_bankID != null) && (this.qfx_bankID.length() > 0)) {
          paramStringBuffer.append("<FID>" + this.qfx_bankID + "\r\n");
        }
        paramStringBuffer.append("</FI>\r\n");
      }
      if ((this.qfx_bankID != null) && (this.qfx_bankID.length() > 0)) {
        paramStringBuffer.append("<INTU.BID>" + this.qfx_bankID + "\r\n");
      }
      if ((this.qfx_userID != null) && (this.qfx_userID.length() > 0)) {
        paramStringBuffer.append("<INTU.USERID>" + this.qfx_userID + "\r\n");
      }
    }
    paramStringBuffer.append("</SONRS>\r\n");
    paramStringBuffer.append("</SIGNONMSGSRSV1>\r\n");
  }
  
  protected void exportOfxCredit(Transactions paramTransactions, Account paramAccount, SimpleDateFormat paramSimpleDateFormat, String paramString, StringBuffer paramStringBuffer)
  {
    exportOfxHeader(paramStringBuffer);
    paramStringBuffer.append("<OFX>\r\n");
    exportOfxSignOn(paramStringBuffer, paramString);
    paramStringBuffer.append("<CREDITCARDMSGSRSV1>\r\n");
    paramStringBuffer.append("<CCSTMTTRNRS>\r\n");
    if ((this.format == 5) || (this.format == 7)) {
      paramStringBuffer.append("<TRNUID>0\r\n");
    } else {
      paramStringBuffer.append("<TRNUID>1\r\n");
    }
    paramStringBuffer.append("<STATUS>\r\n");
    paramStringBuffer.append("<CODE>0\r\n");
    paramStringBuffer.append("<SEVERITY>INFO\r\n");
    paramStringBuffer.append("</STATUS>\r\n");
    paramStringBuffer.append("<CCSTMTRS>\r\n");
    paramStringBuffer.append("<CURDEF>" + XMLUtil.XMLEncode(paramAccount.getCurrencyCode()) + "\r\n");
    paramStringBuffer.append("<CCACCTFROM>\r\n");
    paramStringBuffer.append("<ACCTID>" + XMLUtil.XMLEncode(paramAccount.getNumber()) + "\r\n");
    paramStringBuffer.append("</CCACCTFROM>\r\n");
    paramStringBuffer.append("<BANKTRANLIST>\r\n");
    paramStringBuffer.append("<DTSTART>" + XMLUtil.XMLEncode(paramSimpleDateFormat.format(this.startDate.getTime())) + "\r\n");
    paramStringBuffer.append("<DTEND>" + XMLUtil.XMLEncode(paramSimpleDateFormat.format(this.endDate.getTime())) + "\r\n");
    Currency localCurrency = addExportTransactions(paramTransactions, paramSimpleDateFormat, paramStringBuffer);
    paramStringBuffer.append("</BANKTRANLIST>\r\n");
    paramStringBuffer.append("<LEDGERBAL>\r\n");
    if (localCurrency == null)
    {
      if ((this.format == 5) || (this.format == 7)) {
        paramStringBuffer.append("<BALAMT>" + XMLUtil.XMLEncode(formatAmount(paramAccount.getCurrentBalance().getAmountValue())) + "\r\n");
      } else {
        paramStringBuffer.append("<BALAMT>0\r\n");
      }
    }
    else {
      paramStringBuffer.append("<BALAMT>" + XMLUtil.XMLEncode(formatAmount(localCurrency)) + "\r\n");
    }
    paramStringBuffer.append("<DTASOF>" + XMLUtil.XMLEncode(paramSimpleDateFormat.format(this.endDate.getTime())) + "\r\n");
    paramStringBuffer.append("</LEDGERBAL>\r\n");
    paramStringBuffer.append("</CCSTMTRS>\r\n");
    paramStringBuffer.append("</CCSTMTTRNRS>\r\n");
    paramStringBuffer.append("</CREDITCARDMSGSRSV1>\r\n");
    paramStringBuffer.append("</OFX>\r\n");
  }
  
  protected void exportOfxOther(Transactions paramTransactions, Account paramAccount, SimpleDateFormat paramSimpleDateFormat, String paramString, StringBuffer paramStringBuffer)
  {
    exportOfxHeader(paramStringBuffer);
    paramStringBuffer.append("<OFX>\r\n");
    exportOfxSignOn(paramStringBuffer, paramString);
    paramStringBuffer.append("<BANKMSGSRSV1>\r\n");
    paramStringBuffer.append("<STMTTRNRS>\r\n");
    if ((this.format == 5) || (this.format == 7)) {
      paramStringBuffer.append("<TRNUID>0\r\n");
    } else {
      paramStringBuffer.append("<TRNUID>1\r\n");
    }
    paramStringBuffer.append("<STATUS>\r\n");
    paramStringBuffer.append("<CODE>0\r\n");
    paramStringBuffer.append("<SEVERITY>INFO\r\n");
    paramStringBuffer.append("</STATUS>\r\n");
    paramStringBuffer.append("<STMTRS>\r\n");
    paramStringBuffer.append("<CURDEF>" + XMLUtil.XMLEncode(paramAccount.getCurrencyCode()) + "\r\n");
    paramStringBuffer.append("<BANKACCTFROM>\r\n");
    paramStringBuffer.append("<BANKID>" + XMLUtil.XMLEncode(paramAccount.getBankID()) + "\r\n");
    paramStringBuffer.append("<ACCTID>" + XMLUtil.XMLEncode(paramAccount.getNumber()) + "\r\n");
    paramStringBuffer.append("<ACCTTYPE>" + XMLUtil.XMLEncode(getAccountType(paramAccount.getTypeValue())) + "\r\n");
    paramStringBuffer.append("</BANKACCTFROM>\r\n");
    paramStringBuffer.append("<BANKTRANLIST>\r\n");
    paramStringBuffer.append("<DTSTART>" + XMLUtil.XMLEncode(paramSimpleDateFormat.format(this.startDate.getTime())) + "\r\n");
    paramStringBuffer.append("<DTEND>" + XMLUtil.XMLEncode(paramSimpleDateFormat.format(this.endDate.getTime())) + "\r\n");
    Currency localCurrency = addExportTransactions(paramTransactions, paramSimpleDateFormat, paramStringBuffer);
    paramStringBuffer.append("</BANKTRANLIST>\r\n");
    paramStringBuffer.append("<LEDGERBAL>\r\n");
    if (localCurrency == null)
    {
      if ((this.format == 5) || (this.format == 7)) {
        paramStringBuffer.append("<BALAMT>" + XMLUtil.XMLEncode(formatAmount(paramAccount.getCurrentBalance().getAmountValue())) + "\r\n");
      } else {
        paramStringBuffer.append("<BALAMT>0\r\n");
      }
    }
    else {
      paramStringBuffer.append("<BALAMT>" + XMLUtil.XMLEncode(formatAmount(localCurrency)) + "\r\n");
    }
    paramStringBuffer.append("<DTASOF>" + XMLUtil.XMLEncode(paramSimpleDateFormat.format(this.endDate.getTime())) + "\r\n");
    paramStringBuffer.append("</LEDGERBAL>\r\n");
    paramStringBuffer.append("</STMTRS>\r\n");
    paramStringBuffer.append("</STMTTRNRS>\r\n");
    paramStringBuffer.append("</BANKMSGSRSV1>\r\n");
    paramStringBuffer.append("</OFX>\r\n");
  }
  
  protected Currency addExportTransactions(Transactions paramTransactions, SimpleDateFormat paramSimpleDateFormat, StringBuffer paramStringBuffer)
  {
    Transaction localTransaction = null;
    Iterator localIterator = paramTransactions.iterator();
    while (localIterator.hasNext())
    {
      localTransaction = (Transaction)localIterator.next();
      paramStringBuffer.append("<STMTTRN>\r\n");
      paramStringBuffer.append("<TRNTYPE>");
      paramStringBuffer.append(XMLUtil.XMLEncode(getTransType(localTransaction.getTypeValue())));
      paramStringBuffer.append("\r\n");
      if (localTransaction.getDate() == null)
      {
        paramStringBuffer.append("<DTPOSTED>unknown\r\n");
      }
      else
      {
        paramStringBuffer.append("<DTPOSTED>");
        paramStringBuffer.append(XMLUtil.XMLEncode(paramSimpleDateFormat.format(localTransaction.getDateValue().getTime())));
        paramStringBuffer.append("\r\n");
      }
      paramStringBuffer.append("<TRNAMT>");
      paramStringBuffer.append(XMLUtil.XMLEncode(formatAmount(localTransaction.getAmountValue())));
      paramStringBuffer.append("\r\n");
      if (localTransaction.getID() == null)
      {
        paramStringBuffer.append("<FITID>unknown\r\n");
      }
      else
      {
        paramStringBuffer.append("<FITID>");
        paramStringBuffer.append(XMLUtil.XMLEncode(localTransaction.getID()));
        paramStringBuffer.append("\r\n");
      }
      localObject = getTransactionCheckNum(localTransaction);
      if ((localObject != null) && (((String)localObject).length() > 0))
      {
        if (localTransaction.getTypeValue() == 3)
        {
          paramStringBuffer.append("<CHECKNUM>");
          if (((String)localObject).length() > 12) {
            localObject = ((String)localObject).substring(0, 12);
          }
        }
        else
        {
          paramStringBuffer.append("<REFNUM>");
          if (((String)localObject).length() > 32) {
            localObject = ((String)localObject).substring(0, 32);
          }
        }
        paramStringBuffer.append(XMLUtil.XMLEncode((String)localObject));
        paramStringBuffer.append("\r\n");
      }
      String str1 = localTransaction.getDescription();
      if ((str1 != null) && (str1.length() > 0))
      {
        if (this.asMemo)
        {
          paramStringBuffer.append("<MEMO>");
          if (str1.length() > 255) {
            str1 = str1.substring(0, 255);
          }
        }
        else
        {
          paramStringBuffer.append("<NAME>");
          if (str1.length() > 32) {
            str1 = str1.substring(0, 32);
          }
        }
        paramStringBuffer.append(XMLUtil.XMLEncode(str1));
        paramStringBuffer.append("\r\n");
      }
      String str2 = localTransaction.getMemo();
      if ((str2 != null) && (str2.length() > 0) && (!this.asMemo))
      {
        paramStringBuffer.append("<MEMO>");
        if (str2.length() > 255) {
          str2 = str2.substring(0, 255);
        }
        paramStringBuffer.append(XMLUtil.XMLEncode(str2));
        paramStringBuffer.append("\r\n");
      }
      paramStringBuffer.append("</STMTTRN>\r\n");
    }
    Object localObject = null;
    if (localTransaction != null) {
      localObject = localTransaction.getRunningBalanceValue();
    }
    return localObject;
  }
  
  protected static String formatAmount(Currency paramCurrency)
  {
    NumberFormat localNumberFormat = NumberFormat.getInstance();
    localNumberFormat.setGroupingUsed(false);
    localNumberFormat.setMinimumFractionDigits(2);
    localNumberFormat.setMaximumFractionDigits(2);
    return localNumberFormat.format(paramCurrency.doubleValue());
  }
  
  protected String getAccountType(int paramInt)
  {
    for (int i = 0; i < this.acctTypes.length; i++) {
      if (paramInt == this.acctMap[i]) {
        return this.acctTypes[i];
      }
    }
    return "UNKNOWN";
  }
  
  protected static String getTransType(int paramInt)
  {
    for (int i = 0; i < transType.length; i++) {
      if (paramInt == transMap[i]) {
        return transType[i];
      }
    }
    return "OTHER";
  }
  
  public void setFormat(String paramString)
  {
    this.formatStr = paramString;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public void setInitialize(String paramString)
  {
    this.initFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setDescriptionAsMemo(String paramString)
  {
    this.asMemo = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setProcess(String paramString)
  {
    this.processFlag = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setValidate(String paramString)
  {
    this.validate = null;
    if (!paramString.equals("")) {
      this.validate = paramString.toUpperCase();
    }
  }
  
  public void setStartDate(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.startDateStr = null;
    } else {
      this.startDateStr = paramString;
    }
  }
  
  public String getStartDate()
  {
    return this.startDateStr;
  }
  
  public void setEndDate(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.endDateStr = null;
    } else {
      this.endDateStr = paramString;
    }
  }
  
  public String getEndDate()
  {
    return this.endDateStr;
  }
  
  public String getFirstDate()
  {
    if (this.firstDate != null) {
      return this.firstDate.toString();
    }
    return null;
  }
  
  public String getLastDate()
  {
    if (this.lastDate != null) {
      return this.lastDate.toString();
    }
    return null;
  }
  
  public void setTransactionsName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.transactionsName = null;
    } else {
      this.transactionsName = paramString;
    }
  }
  
  public void setUseTransactionsName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.useTransactionsName = false;
    } else {
      this.useTransactionsName = Boolean.valueOf(paramString).booleanValue();
    }
  }
  
  public void setCommaDelimiter(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.comma_delimiter = ",";
    } else {
      this.comma_delimiter = paramString;
    }
  }
  
  public void setEOLDelimiter(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.eol_delimiter = "\r";
    } else {
      this.eol_delimiter = paramString;
    }
  }
  
  public void setQFXBankName(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.qfx_bankName = "";
    } else {
      this.qfx_bankName = paramString;
    }
  }
  
  public void setQFXBankID(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.qfx_bankID = "";
    } else {
      this.qfx_bankID = paramString;
    }
  }
  
  public void setQFXUserID(String paramString)
  {
    if ((paramString == null) || (paramString.length() == 0)) {
      this.qfx_userID = "";
    } else {
      this.qfx_userID = paramString;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.Export
 * JD-Core Version:    0.7.0.1
 */