package com.ffusion.services.reporting;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.RptException;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLUtil;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.beans.InvalidDateTimeException;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.TimeZone;

public class ReportExporterQFX
  extends ReportExporterBase
{
  protected static final int TRAN_COL_DATE = 0;
  protected static final int TRAN_COL_AMOUNT = 1;
  protected static final int TRAN_COL_REF_NUM = 2;
  protected static final int TRAN_COL_PAYEEPAYOR = 3;
  protected static final int TRAN_COL_DESC = 4;
  protected static final int TRAN_COL_MEMO = 5;
  protected static final int TRAN_COL_ISSUE_DATE = 6;
  protected static final int TRAN_COL_PAYEE = 7;
  protected static final int TRAN_COL_TRAN_ID = 8;
  protected static final int TRAN_COL_REG_ID = 9;
  protected static final int TRAN_COL_TRAN_TYPE = 10;
  protected static final int TRAN_COL_REG_TYPE = 11;
  protected static final int NUM_TRAN_COLUMNS = 12;
  protected static final String VALUE_UNKNOWN = "unknown";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.reporting.export_qfx";
  public static final String TRANSACTION_TYPE_ABBR = "TransactionTypeAbbr";
  public static final String REG_TRANSACTION_TYPE_ABBR = "RegTransactionTypeAbbr";
  public static final String TRAN_TYPE_DEFAULT = "OTHER";
  public static final String OFX_ACCOUNT_TYPE_MAPPING = "OFXAccountTypeMapping";
  protected StringBuffer _buff = null;
  protected int _columnNumber = 0;
  protected HashMap _columnNames = null;
  protected ArrayList _dataItems = null;
  protected ArrayList _tranRows = new ArrayList();
  protected boolean _needsCatRpt = false;
  protected int[] _tranCols = new int[12];
  protected String _qfxBankName = null;
  protected String _qfxBankID = null;
  protected String _qfxUserID = null;
  protected boolean _isCCAcct = false;
  protected SimpleDateFormat _formatDate = null;
  protected Currency _lastBalance = null;
  protected Account _account = null;
  protected DateTime _startDate = null;
  protected DateTime _endDate = null;
  protected DateTime _lastTransactionDate = null;
  protected Locale _locale = null;
  protected String _exportFormat = null;
  
  public ReportExporterQFX(ReportCriteria paramReportCriteria, HashMap paramHashMap1, HashMap paramHashMap2)
    throws RptException
  {
    super(paramReportCriteria, paramHashMap1);
    this._locale = paramReportCriteria.getLocale();
    Properties localProperties = paramReportCriteria.getReportOptions();
    this._buff = new StringBuffer();
    String str1 = localProperties.getProperty("REPORTTYPE");
    if (str1.equals("Account Register Export Transactions")) {
      this._needsCatRpt = true;
    }
    this._exportFormat = localProperties.getProperty("FORMAT");
    this._account = ((Account)paramHashMap2.get("Account"));
    if (this._account == null)
    {
      String str2 = "Missing required Account field in the extra HashMap for QFX exporter.";
      DebugLog.log(str2);
      throw new RptException(100, str2);
    }
    this._isCCAcct = (Account.getAccountSystemTypeFromGroup(Account.getAccountGroupFromType(this._account.getTypeValue())) == 4);
    this._qfxBankName = localProperties.getProperty("OPT_QFX_BANK_NAME");
    this._qfxBankID = localProperties.getProperty("OPT_QFX_BANK_ID");
    this._qfxUserID = localProperties.getProperty("OPT_QFX_USER_ID");
    this._formatDate = new SimpleDateFormat("yyyyMMddHHmmss");
    this._formatDate.setTimeZone(TimeZone.getTimeZone("UTC"));
  }
  
  protected Object doFlushToObject()
    throws RptException
  {
    String str = this._buff.toString();
    this._buff.setLength(0);
    return str;
  }
  
  protected void doGenerateReportResultHeading(String paramString1, String paramString2)
    throws RptException
  {}
  
  protected void doGenerateReportResultSectionHeading(String paramString1, String paramString2)
    throws RptException
  {}
  
  protected void doGenerateReportHeader(String paramString1, String paramString2, String paramString3, String paramString4, ReportCriteria paramReportCriteria)
    throws RptException
  {
    Date localDate = new Date();
    String str1 = this._formatDate.format(localDate);
    String str2 = paramReportCriteria.getDisplayValue("StartDate");
    String str3 = paramReportCriteria.getDisplayValue("EndDate");
    if ((str2 != null) && (str3 != null) && (str2.length() != 0) && (str3.length() != 0)) {
      try
      {
        this._startDate = new DateTime(str2, this._locale, "MM/dd/yyyy HH:mm:ss");
        this._endDate = new DateTime(str3, this._locale, "MM/dd/yyyy HH:mm:ss");
      }
      catch (InvalidDateTimeException localInvalidDateTimeException)
      {
        String str4 = "Invalid required start or end dates in the search criteria for QFX exporter.";
        DebugLog.log(str4);
        throw new RptException(100, str4, localInvalidDateTimeException);
      }
    }
    this._buff.append("OFXHEADER:100").append(getEOLString());
    this._buff.append("DATA:OFXSGML").append(getEOLString());
    this._buff.append("VERSION:102").append(getEOLString());
    this._buff.append("SECURITY:NONE").append(getEOLString());
    this._buff.append("ENCODING:USASCII").append(getEOLString());
    this._buff.append("CHARSET:1252").append(getEOLString());
    this._buff.append("COMPRESSION:NONE").append(getEOLString());
    this._buff.append("OLDFILEUID:NONE").append(getEOLString());
    this._buff.append("NEWFILEUID:NONE").append(getEOLString());
    this._buff.append(getEOLString());
    this._buff.append("<OFX>").append(getEOLString());
    this._buff.append("<SIGNONMSGSRSV1>").append(getEOLString());
    this._buff.append("<SONRS>").append(getEOLString());
    this._buff.append("<STATUS>").append(getEOLString());
    this._buff.append("<CODE>0").append(getEOLString());
    this._buff.append("<SEVERITY>INFO").append(getEOLString());
    this._buff.append("</STATUS>").append(getEOLString());
    this._buff.append("<DTSERVER>").append(XMLUtil.XMLEncode(str1)).append(getEOLString());
    this._buff.append("<LANGUAGE>ENG").append(getEOLString());
    if (((this._qfxBankName != null) || (this._qfxBankID != null)) && (this._exportFormat != "OFX"))
    {
      this._buff.append("<FI>").append(getEOLString());
      if (this._qfxBankName != null) {
        this._buff.append("<ORG>").append(this._qfxBankName).append(getEOLString());
      }
      if (this._qfxBankID != null) {
        this._buff.append("<FID>").append(this._qfxBankID).append(getEOLString());
      }
      this._buff.append("</FI>").append(getEOLString());
    }
    if ((this._qfxBankID != null) && (this._exportFormat != "OFX")) {
      this._buff.append("<INTU.BID>").append(this._qfxBankID).append(getEOLString());
    }
    if ((this._qfxUserID != null) && (this._exportFormat != "OFX")) {
      this._buff.append("<INTU.USERID>").append(this._qfxUserID).append(getEOLString());
    }
    this._buff.append("</SONRS>").append(getEOLString());
    this._buff.append("</SIGNONMSGSRSV1>").append(getEOLString());
    this._buff.append(this._isCCAcct ? "<CREDITCARDMSGSRSV1>" : "<BANKMSGSRSV1>").append(getEOLString());
    this._buff.append(this._isCCAcct ? "<CCSTMTTRNRS>" : "<STMTTRNRS>").append(getEOLString());
    this._buff.append("<TRNUID>0").append(getEOLString());
    this._buff.append("<STATUS>").append(getEOLString());
    this._buff.append("<CODE>0").append(getEOLString());
    this._buff.append("<SEVERITY>INFO").append(getEOLString());
    this._buff.append("</STATUS>").append(getEOLString());
    this._buff.append(this._isCCAcct ? "<CCSTMTRS>" : "<STMTRS>").append(getEOLString());
    this._buff.append("<CURDEF>USD").append(getEOLString());
    if (this._isCCAcct)
    {
      this._buff.append("<CCACCTFROM>").append(getEOLString());
      this._buff.append("<ACCTID>").append(XMLUtil.XMLEncode(this._account.getDisplayText(this._exportFormat))).append(getEOLString());
      this._buff.append("</CCACCTFROM>").append(getEOLString());
    }
    else
    {
      this._buff.append("<BANKACCTFROM>").append(getEOLString());
      this._buff.append("<BANKID>").append(XMLUtil.XMLEncode(this._account.getRoutingNum())).append(getEOLString());
      this._buff.append("<ACCTID>").append(XMLUtil.XMLEncode(this._account.getDisplayText(this._exportFormat))).append(getEOLString());
      this._buff.append("<ACCTTYPE>").append(XMLUtil.XMLEncode(getOFXAccountTypeMapping(this._account.getTypeValue()))).append(getEOLString());
      this._buff.append("</BANKACCTFROM>").append(getEOLString());
    }
    this._buff.append("<BANKTRANLIST>").append(getEOLString());
    if (this._startDate != null) {
      this._buff.append("<DTSTART>").append(XMLUtil.XMLEncode(this._formatDate.format(this._startDate.getTime()))).append(getEOLString());
    }
    if (this._endDate != null) {
      this._buff.append("<DTEND>").append(XMLUtil.XMLEncode(this._formatDate.format(this._endDate.getTime()))).append(getEOLString());
    }
  }
  
  protected void doGenerateReportFooter(String paramString)
    throws RptException
  {
    this._buff.append("</BANKTRANLIST>").append(getEOLString());
    this._buff.append("<LEDGERBAL>").append(getEOLString());
    if (this._endDate == null) {
      this._endDate = this._lastTransactionDate;
    }
    if (this._endDate == null) {
      this._endDate = new DateTime(this._locale);
    }
    if (this._lastBalance == null)
    {
      Currency localCurrency = null;
      Object localObject = null;
      if (this._account.getCurrentBalance() != null)
      {
        localCurrency = this._account.getCurrentBalance().getAmountValue();
        localObject = this._account.getCurrentBalance().getDateValue();
      }
      this._buff.append("<BALAMT>");
      this._buff.append(XMLUtil.XMLEncode(localCurrency == null ? "0" : localCurrency.getAmountValue().toString()));
      this._buff.append(getEOLString());
      if (localObject == null) {
        localObject = this._endDate;
      }
      this._buff.append("<DTASOF>").append(XMLUtil.XMLEncode(this._formatDate.format(((DateTime)localObject).getTime()))).append(getEOLString());
    }
    else
    {
      this._buff.append("<BALAMT>");
      this._buff.append(XMLUtil.XMLEncode(this._lastBalance.getAmountValue().toString()));
      this._buff.append(getEOLString());
      this._buff.append("<DTASOF>");
      this._buff.append(XMLUtil.XMLEncode(this._formatDate.format(this._endDate.getTime())));
      this._buff.append(getEOLString());
    }
    this._buff.append("</LEDGERBAL>").append(getEOLString());
    this._buff.append(this._isCCAcct ? "</CCSTMTRS>" : "</STMTRS>").append(getEOLString());
    this._buff.append(this._isCCAcct ? "</CCSTMTTRNRS>" : "</STMTTRNRS>").append(getEOLString());
    this._buff.append(this._isCCAcct ? "</CREDITCARDMSGSRSV1>" : "</BANKMSGSRSV1>").append(getEOLString());
    this._buff.append("</OFX>").append(getEOLString());
  }
  
  protected void doGenerateReportResultColumnHeading(ArrayList paramArrayList, float paramFloat, String paramString)
    throws RptException
  {
    String str = (String)paramArrayList.get(0);
    this._columnNames.put(str, new Integer(this._columnNumber));
    this._columnNumber += 1;
  }
  
  protected void doGenerateReportResultRowData(Object paramObject, String paramString1, float paramFloat, String paramString2, String paramString3, String paramString4, boolean paramBoolean)
    throws RptException
  {
    this._dataItems.add(paramObject);
  }
  
  protected void doInitReportResultTable()
    throws RptException
  {
    this._columnNumber = 0;
    this._columnNames = new HashMap();
  }
  
  protected void doFiniReportResultTable()
    throws RptException
  {
    this._columnNames = null;
    if (this._needsCatRpt) {
      this._needsCatRpt = false;
    } else {
      exportTransactionRow();
    }
    this._dataItems = null;
  }
  
  protected void doInitReportResultRow()
    throws RptException
  {
    this._dataItems = new ArrayList();
  }
  
  protected void doFiniReportResultRow()
    throws RptException
  {
    if (this._columnNumber != 0)
    {
      if (!this._needsCatRpt)
      {
        this._tranCols[0] = getColumnPosition("Date", false);
        this._tranCols[1] = getColumnPosition("Amount", true);
        this._tranCols[2] = getColumnPosition("Reference Number", false);
        this._tranCols[3] = getColumnPosition("Payee/Payor", false);
        this._tranCols[4] = getColumnPosition("Description", false);
        this._tranCols[5] = getColumnPosition("Memo", false);
        this._tranCols[6] = getColumnPosition("date_issued", false);
        this._tranCols[7] = getColumnPosition("payee_name", false);
        this._tranCols[8] = getColumnPosition("ID", false);
        this._tranCols[9] = getColumnPosition("reg_transaction_id", false);
        this._tranCols[10] = getColumnPosition("Type", false);
        this._tranCols[11] = getColumnPosition("Type", false);
      }
      this._columnNumber = 0;
      return;
    }
    if (!this._needsCatRpt)
    {
      if (this._tranRows.size() > 0)
      {
        ArrayList localArrayList = (ArrayList)this._tranRows.get(0);
        Integer localInteger1 = (Integer)localArrayList.get(this._tranCols[9]);
        Integer localInteger2 = (Integer)this._dataItems.get(this._tranCols[9]);
        if (localInteger1.intValue() != localInteger2.intValue()) {
          exportTransactionRow();
        }
      }
      this._tranRows.add(this._dataItems);
      this._dataItems = null;
      if (this._tranCols[9] == -1) {
        exportTransactionRow();
      }
    }
  }
  
  protected void doWriteBlankLine()
    throws RptException
  {}
  
  protected void doWriteParagraph(String paramString)
    throws RptException
  {}
  
  protected void doAddPageBreak()
    throws RptException
  {}
  
  protected void exportTransactionRow()
  {
    if (this._tranRows.size() == 0) {
      return;
    }
    ArrayList localArrayList = (ArrayList)this._tranRows.get(0);
    String str1 = null;
    int i = 0;
    if (this._tranCols[10] != -1)
    {
      localObject1 = (Integer)localArrayList.get(this._tranCols[10]);
      if (localObject1 != null)
      {
        str1 = getTransactionTypeAbbr(((Integer)localObject1).intValue());
        i = ((Integer)localObject1).intValue() == 3 ? 1 : 0;
      }
    }
    if ((str1 == null) && (this._tranCols[11] != -1))
    {
      localObject1 = (Integer)localArrayList.get(this._tranCols[11]);
      if (localObject1 != null)
      {
        str1 = getRegTransactionTypeAbbr(((Integer)localObject1).intValue());
        i = ((Integer)localObject1).intValue() == 3 ? 1 : 0;
      }
    }
    if (str1 == null) {
      str1 = "OTHER";
    }
    Object localObject1 = null;
    DateTime localDateTime = null;
    if (this._tranCols[6] != -1) {
      localDateTime = (DateTime)localArrayList.get(this._tranCols[6]);
    } else if (this._tranCols[0] != -1) {
      localDateTime = (DateTime)localArrayList.get(this._tranCols[0]);
    }
    if (localDateTime != null) {
      localObject1 = this._formatDate.format(localDateTime.getTime());
    }
    this._lastTransactionDate = new DateTime(localDateTime, this._locale);
    Currency localCurrency = (Currency)localArrayList.get(this._tranCols[1]);
    BigDecimal localBigDecimal = localCurrency.getAmountValue();
    if (localArrayList.size() > 1)
    {
      localObject2 = this._tranRows.iterator();
      ((Iterator)localObject2).next();
      while (((Iterator)localObject2).hasNext())
      {
        localObject3 = (ArrayList)((Iterator)localObject2).next();
        localObject4 = (Currency)((ArrayList)localObject3).get(this._tranCols[1]);
        if (localObject4 != null) {
          localBigDecimal = localBigDecimal.add(((Currency)localObject4).getAmountValue());
        }
      }
      localCurrency = new Currency(localBigDecimal, localCurrency.getCurrencyCode(), localCurrency.getLocale());
    }
    Object localObject2 = localCurrency.getAmountValue().toString();
    Object localObject3 = null;
    if (this._tranCols[8] != -1) {
      localObject3 = (String)localArrayList.get(this._tranCols[8]);
    }
    if ((localObject3 == null) && (this._tranCols[9] != -1))
    {
      localObject4 = (Integer)localArrayList.get(this._tranCols[9]);
      if (localObject4 != null) {
        localObject3 = ((Integer)localObject4).toString();
      }
    }
    Object localObject4 = null;
    if (this._tranCols[2] != -1)
    {
      localObject4 = (String)localArrayList.get(this._tranCols[2]);
      if ((localObject4 != null) && (((String)localObject4).length() == 0)) {
        localObject4 = null;
      }
    }
    String str2 = null;
    if (this._tranCols[3] != -1)
    {
      str2 = (String)localArrayList.get(this._tranCols[3]);
      if ((str2 != null) && (str2.length() == 0)) {
        str2 = null;
      }
    }
    if ((str2 == null) && (this._tranCols[7] != -1))
    {
      str2 = (String)localArrayList.get(this._tranCols[7]);
      if ((str2 != null) && (str2.length() == 0)) {
        str2 = null;
      }
    }
    if ((str2 == null) && (this._tranCols[4] != -1))
    {
      str2 = (String)localArrayList.get(this._tranCols[4]);
      if ((str2 != null) && (str2.length() == 0)) {
        str2 = null;
      }
    }
    String str3 = null;
    if (this._tranCols[5] != -1)
    {
      str3 = (String)localArrayList.get(this._tranCols[5]);
      if ((str3 != null) && (str3.length() == 0)) {
        str3 = null;
      }
    }
    if ((str3 == null) && (this._tranCols[4] != -1))
    {
      str3 = (String)localArrayList.get(this._tranCols[4]);
      if ((str3 != null) && (str3.length() == 0)) {
        str3 = null;
      }
    }
    if (localObject1 == null) {
      localObject1 = "unknown";
    } else {
      localObject1 = XMLUtil.XMLEncode((String)localObject1);
    }
    localObject2 = XMLUtil.XMLEncode((String)localObject2);
    if (localObject3 == null) {
      localObject3 = "unknown";
    } else {
      localObject3 = XMLUtil.XMLEncode((String)localObject3);
    }
    if (localObject4 != null)
    {
      int j = i != 0 ? 12 : 32;
      if (((String)localObject4).length() > j) {
        localObject4 = ((String)localObject4).substring(0, j);
      }
      localObject4 = XMLUtil.XMLEncode((String)localObject4);
    }
    if (str2 != null)
    {
      if (str2.length() > 32) {
        str2 = str2.substring(0, 32);
      }
      str2 = XMLUtil.XMLEncode(str2);
    }
    if (str3 != null)
    {
      if (str3.length() > 255) {
        str3 = str3.substring(0, 255);
      }
      str3 = XMLUtil.XMLEncode(str3);
    }
    this._buff.append("<STMTTRN>").append(getEOLString());
    this._buff.append("<TRNTYPE>").append(str1).append(getEOLString());
    this._buff.append("<DTPOSTED>").append((String)localObject1).append(getEOLString());
    this._buff.append("<TRNAMT>").append((String)localObject2).append(getEOLString());
    this._buff.append("<FITID>").append((String)localObject3).append(getEOLString());
    if (localObject4 != null) {
      this._buff.append(i != 0 ? "<CHECKNUM>" : "<REFNUM>").append((String)localObject4).append(getEOLString());
    }
    if (str2 != null) {
      this._buff.append("<NAME>").append(str2).append(getEOLString());
    }
    if (str3 != null) {
      this._buff.append("<MEMO>").append(str3).append(getEOLString());
    }
    this._buff.append("</STMTTRN>").append(getEOLString());
    this._tranRows.clear();
  }
  
  protected int getColumnPosition(String paramString, boolean paramBoolean)
    throws RptException
  {
    Integer localInteger = (Integer)this._columnNames.get(paramString);
    int i = -1;
    if (localInteger != null)
    {
      i = localInteger.intValue();
    }
    else if (paramBoolean)
    {
      String str = "The column name \"" + paramString + "\" was not found in the report to be exported.  This column name is required for the proper export " + "of this report in QFX format.";
      DebugLog.log(str);
      throw new RptException(100, str);
    }
    return i;
  }
  
  protected String getTransactionTypeAbbr(int paramInt)
  {
    String str = null;
    try
    {
      str = ResourceUtil.getString("TransactionTypeAbbr" + paramInt, "com.ffusion.beans.reporting.export_qfx", this._locale);
    }
    catch (MissingResourceException localMissingResourceException) {}
    return str;
  }
  
  protected String getRegTransactionTypeAbbr(int paramInt)
  {
    String str = null;
    try
    {
      str = ResourceUtil.getString("RegTransactionTypeAbbr" + paramInt, "com.ffusion.beans.reporting.export_qfx", this._locale);
    }
    catch (MissingResourceException localMissingResourceException)
    {
      str = getTransactionTypeAbbr(paramInt);
    }
    return str;
  }
  
  protected String getOFXAccountTypeMapping(int paramInt)
  {
    String str = null;
    try
    {
      str = ResourceUtil.getString("OFXAccountTypeMapping" + paramInt, "com.ffusion.beans.reporting.export_qfx", this._locale);
    }
    catch (MissingResourceException localMissingResourceException) {}
    return str;
  }
  
  public String getEOLString()
  {
    return "\r\n";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterQFX
 * JD-Core Version:    0.7.0.1
 */