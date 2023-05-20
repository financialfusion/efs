package com.ffusion.services.reporting;

import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.RptException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;

public class ReportExporterIIF
  extends ReportExporterBase
{
  protected static final int TRAN_COL_DATE = 0;
  protected static final int TRAN_COL_AMOUNT = 1;
  protected static final int TRAN_COL_PAYEEPAYOR = 2;
  protected static final int TRAN_COL_DESC = 3;
  protected static final int TRAN_COL_MEMO = 4;
  protected static final int TRAN_COL_ISSUE_DATE = 5;
  protected static final int TRAN_COL_PAYEE = 6;
  protected static final int TRAN_COL_PARENT_CAT_NAME = 7;
  protected static final int TRAN_COL_CAT_NAME = 8;
  protected static final int TRAN_COL_TRAN_ID = 9;
  protected static final int TRAN_COL_REG_ID = 10;
  protected static final int TRAN_COL_TRAN_TYPE = 11;
  protected static final int TRAN_COL_REG_TYPE = 12;
  protected static final int TRAN_COL_REF_NUM = 13;
  protected static final int NUM_TRAN_COLUMNS = 14;
  protected static final String VALUE_UNKNOWN = "Unknown";
  protected static final String TAB = "\t";
  protected static final String HEADER_START = "!";
  protected static final String TRNS = "TRNS";
  protected static final String TRNSID = "TRNSID";
  protected static final String TRNSTYPE = "TRNSTYPE";
  protected static final String DATE = "DATE";
  protected static final String ACCNT = "ACCNT";
  protected static final String NAME = "NAME";
  protected static final String AMOUNT = "AMOUNT";
  protected static final String DOCNUM = "DOCNUM";
  protected static final String MEMO = "MEMO";
  protected static final String SPL = "SPL";
  protected static final String SPLID = "SPLID";
  protected static final String CLASS = "CLASS";
  protected static final String ENDTRNS = "ENDTRNS";
  protected static final String IIF_HEADER_LINE1 = "!TRNS\tTRNSID\tTRNSTYPE\tDATE\tACCNT\tNAME\tAMOUNT\tMEMO\tDOCNUM";
  protected static final String IIF_HEADER_LINE2 = "!SPL\tSPLID\tTRNSTYPE\tDATE\tACCNT\tNAME\tAMOUNT\tMEMO\tDOCNUM\tCLASS";
  protected static final String IIF_HEADER_LINE3 = "!ENDTRNS";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.reporting.export_iif";
  public static final String TRANSACTION_TYPE = "TransactionType";
  public static final String UNKNOWN_ACCOUNT = "UnknownAccountName";
  private static final String jdField_int = "CHECK";
  private static final String jdField_new = "DEPOSIT";
  protected StringBuffer _buff = null;
  protected int _columnNumber = 0;
  protected HashMap _columnNames = null;
  protected ArrayList _dataItems = null;
  protected ArrayList _tranRows = new ArrayList();
  protected boolean _needsCatRpt = false;
  protected int[] _tranCols = new int[14];
  protected Locale _locale = null;
  protected Account _account = null;
  protected String _tranAccnt = null;
  protected String _unknownAcctName = null;
  
  public ReportExporterIIF(ReportCriteria paramReportCriteria, HashMap paramHashMap1, HashMap paramHashMap2)
    throws RptException
  {
    super(paramReportCriteria, paramHashMap1);
    String str1 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    if (str1.equals("Account Register Export Transactions")) {
      this._needsCatRpt = true;
    }
    this._locale = paramReportCriteria.getLocale();
    this._account = ((Account)paramHashMap2.get("Account"));
    if (this._account == null)
    {
      String str2 = "Missing required Account field in the extra HashMap for IIF exporter.";
      DebugLog.log(str2);
      throw new RptException(100, str2);
    }
    this._tranAccnt = this._account.getDisplayText("IIF");
    this._unknownAcctName = getUnknownAccountName();
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
  {}
  
  protected void doGenerateReportFooter(String paramString)
    throws RptException
  {}
  
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
    if (!this._needsCatRpt)
    {
      this._buff.append("!TRNS\tTRNSID\tTRNSTYPE\tDATE\tACCNT\tNAME\tAMOUNT\tMEMO\tDOCNUM").append(getEOLString());
      this._buff.append("!SPL\tSPLID\tTRNSTYPE\tDATE\tACCNT\tNAME\tAMOUNT\tMEMO\tDOCNUM\tCLASS").append(getEOLString());
      this._buff.append("!ENDTRNS").append(getEOLString());
    }
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
        this._tranCols[2] = getColumnPosition("Payee/Payor", false);
        this._tranCols[3] = getColumnPosition("Description", false);
        this._tranCols[4] = getColumnPosition("Memo", false);
        this._tranCols[5] = getColumnPosition("date_issued", false);
        this._tranCols[6] = getColumnPosition("payee_name", false);
        this._tranCols[7] = getColumnPosition("parent_category_name", false);
        this._tranCols[8] = getColumnPosition("category_name", false);
        this._tranCols[9] = getColumnPosition("ID", false);
        this._tranCols[10] = getColumnPosition("reg_transaction_id", false);
        this._tranCols[11] = getColumnPosition("Type", false);
        this._tranCols[12] = getColumnPosition("Type", false);
        this._tranCols[13] = getColumnPosition("Reference Number", false);
      }
      this._columnNumber = 0;
      return;
    }
    if (!this._needsCatRpt)
    {
      if (this._tranRows.size() > 0)
      {
        ArrayList localArrayList = (ArrayList)this._tranRows.get(0);
        Integer localInteger1 = (Integer)localArrayList.get(this._tranCols[10]);
        Integer localInteger2 = (Integer)this._dataItems.get(this._tranCols[10]);
        if (localInteger1.intValue() != localInteger2.intValue()) {
          exportTransactionRow();
        }
      }
      this._tranRows.add(this._dataItems);
      this._dataItems = null;
      if (this._tranCols[10] == -1) {
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
    if (this._tranCols[9] != -1) {
      str1 = (String)localArrayList.get(this._tranCols[9]);
    }
    if ((str1 == null) && (this._tranCols[10] != -1))
    {
      localObject1 = (Integer)localArrayList.get(this._tranCols[10]);
      if (localObject1 != null) {
        str1 = ((Integer)localObject1).toString();
      }
    }
    Object localObject1 = null;
    if (this._tranCols[11] != -1)
    {
      localObject2 = (Integer)localArrayList.get(this._tranCols[11]);
      if (localObject2 != null) {
        localObject1 = getTransactionTypeName(((Integer)localObject2).intValue());
      }
    }
    if ((localObject1 == null) && (this._tranCols[12] != -1))
    {
      localObject2 = (Integer)localArrayList.get(this._tranCols[12]);
      if (localObject2 != null) {
        localObject1 = getTransactionTypeName(((Integer)localObject2).intValue());
      }
    }
    Object localObject2 = null;
    DateTime localDateTime = null;
    if (this._tranCols[5] != -1) {
      localDateTime = (DateTime)localArrayList.get(this._tranCols[5]);
    } else if (this._tranCols[0] != -1) {
      localDateTime = (DateTime)localArrayList.get(this._tranCols[0]);
    }
    if (localDateTime != null) {
      localObject2 = DateFormatUtil.getFormatter("MM/dd/yyyy").format(localDateTime.getTime());
    }
    String str2 = null;
    int i = 0;
    if (this._tranCols[2] != -1) {
      str2 = (String)localArrayList.get(this._tranCols[2]);
    }
    if ((str2 == null) && (this._tranCols[6] != -1)) {
      str2 = (String)localArrayList.get(this._tranCols[6]);
    }
    if ((str2 == null) && (this._tranCols[3] != -1))
    {
      str2 = (String)localArrayList.get(this._tranCols[3]);
      i = 1;
    }
    String str3 = null;
    if (this._tranCols[4] != -1) {
      str3 = (String)localArrayList.get(this._tranCols[4]);
    }
    if ((str3 == null) && (this._tranCols[3] != -1))
    {
      str3 = (String)localArrayList.get(this._tranCols[3]);
      i = 1;
    }
    String str4 = null;
    if (this._tranCols[3] != -1)
    {
      str4 = (String)localArrayList.get(this._tranCols[3]);
      i = 1;
    }
    String str5 = null;
    if (this._tranCols[13] != -1) {
      str5 = (String)localArrayList.get(this._tranCols[13]);
    }
    Currency localCurrency1 = (Currency)localArrayList.get(this._tranCols[1]);
    BigDecimal localBigDecimal = localCurrency1.getAmountValue();
    if (localArrayList.size() > 1)
    {
      localObject3 = this._tranRows.iterator();
      ((Iterator)localObject3).next();
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (ArrayList)((Iterator)localObject3).next();
        localObject5 = (Currency)((ArrayList)localObject4).get(this._tranCols[1]);
        if (localObject5 != null) {
          localBigDecimal = localBigDecimal.add(((Currency)localObject5).getAmountValue());
        }
      }
      localCurrency1 = new Currency(localBigDecimal, localCurrency1.getCurrencyCode(), localCurrency1.getLocale());
    }
    Object localObject3 = localCurrency1.getFormat();
    localCurrency1.setFormat("DECIMAL");
    Object localObject4 = localCurrency1.getCurrencyStringNoSymbolNoComma();
    localCurrency1.setFormat((String)localObject3);
    this._buff.append("TRNS").append("\t");
    this._buff.append(str1 == null ? "" : str1).append("\t");
    this._buff.append(localObject1 == null ? "DEPOSIT" : localCurrency1.isNegative() ? "CHECK" : (String)localObject1).append("\t");
    this._buff.append((String)localObject2).append("\t");
    this._buff.append(this._tranAccnt).append("\t");
    this._buff.append(str2 == null ? "" : str2).append("\t");
    this._buff.append((String)localObject4).append("\t");
    this._buff.append(str3 == null ? "" : str3).append("\t");
    this._buff.append(str5 == null ? "" : str5).append(getEOLString());
    Object localObject5 = this._tranRows.iterator();
    int j = 0;
    while (((Iterator)localObject5).hasNext())
    {
      j++;
      localArrayList = (ArrayList)((Iterator)localObject5).next();
      Currency localCurrency2 = (Currency)localArrayList.get(this._tranCols[1]);
      String str6 = (localCurrency2.isNegative() ? "" : "-") + localCurrency2.getCurrencyStringNoSymbolNoComma();
      String str7 = str1 + "-" + j;
      Object localObject6 = null;
      Object localObject7 = null;
      if (this._tranCols[7] != -1)
      {
        localObject7 = (String)localArrayList.get(this._tranCols[7]);
        localObject6 = localObject7;
      }
      if (this._tranCols[8] != -1)
      {
        String str8 = (String)localArrayList.get(this._tranCols[8]);
        if (str8 != null) {
          if (localObject7 != null)
          {
            localObject7 = (String)localObject7 + ":" + str8;
          }
          else
          {
            localObject7 = str8;
            localObject6 = str8;
          }
        }
      }
      this._buff.append("SPL").append("\t");
      this._buff.append(str7).append("\t");
      this._buff.append(localObject1 == null ? "" : (String)localObject1).append("\t");
      this._buff.append((String)localObject2).append("\t");
      this._buff.append(localObject6 == null ? this._unknownAcctName : localObject6).append("\t");
      this._buff.append(str4 == null ? "" : str4).append("\t");
      this._buff.append(str6).append("\t");
      this._buff.append(str3 == null ? "" : str3).append("\t");
      this._buff.append(str5 == null ? "" : str5).append("\t");
      this._buff.append(localObject7 == null ? "" : (String)localObject7).append(getEOLString());
    }
    this._buff.append("ENDTRNS").append(getEOLString());
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
      String str = "The column name \"" + paramString + "\" was not found in the report to be exported.  This column name is required for the proper export " + "of this report in IIF format.";
      DebugLog.log(str);
      throw new RptException(100, str);
    }
    return i;
  }
  
  protected String getTransactionTypeName(int paramInt)
  {
    String str = null;
    try
    {
      str = ResourceUtil.getString("TransactionType" + paramInt, "com.ffusion.beans.reporting.export_iif", this._locale);
    }
    catch (Throwable localThrowable) {}
    return str;
  }
  
  protected String getUnknownAccountName()
  {
    String str = "Unknown";
    try
    {
      str = ResourceUtil.getString("UnknownAccountName", "com.ffusion.beans.reporting.export_iif", this._locale);
    }
    catch (Throwable localThrowable) {}
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterIIF
 * JD-Core Version:    0.7.0.1
 */