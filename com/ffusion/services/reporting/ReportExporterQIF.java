package com.ffusion.services.reporting;

import com.ffusion.beans.Currency;
import com.ffusion.beans.accounts.Account;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.RptException;
import com.ffusion.util.DateFormatUtil;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class ReportExporterQIF
  extends ReportExporterBase
{
  protected static final int CAT_COL_CAT_NAME = 0;
  protected static final int CAT_COL_PARENT_CAT_NAME = 1;
  protected static final int CAT_COL_DESC = 2;
  protected static final int CAT_COL_TAX_RELATED = 3;
  protected static final int CAT_COL_TYPE = 4;
  protected static final int NUM_CAT_COLUMNS = 5;
  protected static final int TRAN_COL_DATE = 0;
  protected static final int TRAN_COL_AMOUNT = 1;
  protected static final int TRAN_COL_REF_NUM = 2;
  protected static final int TRAN_COL_PAYEEPAYOR = 3;
  protected static final int TRAN_COL_DESC = 4;
  protected static final int TRAN_COL_MEMO = 5;
  protected static final int TRAN_COL_ISSUE_DATE = 6;
  protected static final int TRAN_COL_STATUS = 7;
  protected static final int TRAN_COL_PAYEE = 8;
  protected static final int TRAN_COL_PARENT_CAT_NAME = 9;
  protected static final int TRAN_COL_CAT_NAME = 10;
  protected static final int TRAN_COL_ID = 11;
  protected static final int NUM_TRAN_COLUMNS = 12;
  protected static final String EOR = "^";
  protected static final String HDR_CATEGORY = "!Type:Cat";
  protected static final String HDR_TRN_BANK = "!Type:Bank";
  protected static final String HDR_TRN_CCARD = "!Type:CCard";
  protected StringBuffer _buff = null;
  protected int _columnNumber = 0;
  protected HashMap _columnNames = null;
  protected ArrayList _dataItems = null;
  protected ArrayList _tranRows = new ArrayList();
  protected boolean _needsCatRpt = false;
  protected int[] _catCols = new int[5];
  protected int[] _tranCols = new int[12];
  protected String _tranHdrType = null;
  
  public ReportExporterQIF(ReportCriteria paramReportCriteria, HashMap paramHashMap1, HashMap paramHashMap2)
    throws RptException
  {
    super(paramReportCriteria, paramHashMap1);
    String str1 = paramReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    if (str1.equals("Account Register Export Transactions")) {
      this._needsCatRpt = true;
    }
    Account localAccount = (Account)paramHashMap2.get("Account");
    if (localAccount == null)
    {
      String str2 = "Missing required Account field in the extra HashMap for QIF exporter.";
      DebugLog.log(str2);
      throw new RptException(100, str2);
    }
    switch (Account.getAccountSystemTypeFromGroup(Account.getAccountGroupFromType(localAccount.getTypeValue())))
    {
    case 4: 
      this._tranHdrType = "!Type:CCard";
      break;
    default: 
      this._tranHdrType = "!Type:Bank";
    }
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
    if (this._needsCatRpt) {
      this._buff.append("!Type:Cat").append(getEOLString());
    } else {
      this._buff.append(this._tranHdrType).append(getEOLString());
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
      if (this._needsCatRpt)
      {
        this._catCols[0] = getColumnPosition("name", true);
        this._catCols[1] = getColumnPosition("parent_name", false);
        this._catCols[2] = getColumnPosition("description", false);
        this._catCols[3] = getColumnPosition("tax_related", false);
        this._catCols[4] = getColumnPosition("type", false);
      }
      else
      {
        this._tranCols[0] = getColumnPosition("Date", false);
        this._tranCols[1] = getColumnPosition("Amount", true);
        this._tranCols[2] = getColumnPosition("Reference Number", false);
        this._tranCols[3] = getColumnPosition("Payee/Payor", false);
        this._tranCols[4] = getColumnPosition("Description", false);
        this._tranCols[5] = getColumnPosition("Memo", false);
        this._tranCols[6] = getColumnPosition("date_issued", false);
        this._tranCols[7] = getColumnPosition("STATUS", false);
        this._tranCols[8] = getColumnPosition("payee_name", false);
        this._tranCols[9] = getColumnPosition("parent_category_name", false);
        this._tranCols[10] = getColumnPosition("category_name", false);
        this._tranCols[11] = getColumnPosition("reg_transaction_id", false);
      }
      this._columnNumber = 0;
      return;
    }
    Object localObject;
    Integer localInteger1;
    if (this._needsCatRpt)
    {
      this._buff.append("N");
      if (this._catCols[1] != -1)
      {
        localObject = (String)this._dataItems.get(this._catCols[1]);
        if (localObject != null) {
          this._buff.append((String)localObject).append(":");
        }
      }
      this._buff.append((String)this._dataItems.get(this._catCols[0])).append(getEOLString());
      if (this._catCols[2] != -1)
      {
        localObject = (String)this._dataItems.get(this._catCols[2]);
        if (localObject != null) {
          this._buff.append("D").append((String)localObject).append(getEOLString());
        }
      }
      if (this._catCols[3] != -1)
      {
        localObject = (Boolean)this._dataItems.get(this._catCols[3]);
        if ((localObject != null) && (((Boolean)localObject).booleanValue())) {
          this._buff.append("T").append(getEOLString());
        }
      }
      localObject = "E";
      if (this._catCols[4] != -1)
      {
        localInteger1 = (Integer)this._dataItems.get(this._catCols[4]);
        if (localInteger1 != null) {
          switch (localInteger1.intValue())
          {
          case 0: 
          case 2: 
            localObject = "I";
            break;
          default: 
            localObject = "E";
          }
        }
      }
      this._buff.append((String)localObject).append(getEOLString());
      this._buff.append("^").append(getEOLString());
      this._dataItems = null;
    }
    else
    {
      if (this._tranRows.size() > 0)
      {
        localObject = (ArrayList)this._tranRows.get(0);
        localInteger1 = (Integer)((ArrayList)localObject).get(this._tranCols[11]);
        Integer localInteger2 = (Integer)this._dataItems.get(this._tranCols[11]);
        if (localInteger1.intValue() != localInteger2.intValue()) {
          exportTransactionRow();
        }
      }
      this._tranRows.add(this._dataItems);
      this._dataItems = null;
      if (this._tranCols[11] == -1) {
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
    DateTime localDateTime = null;
    if (this._tranCols[6] != -1) {
      localDateTime = (DateTime)localArrayList.get(this._tranCols[6]);
    } else if (this._tranCols[0] != -1) {
      localDateTime = (DateTime)localArrayList.get(this._tranCols[0]);
    }
    if (localDateTime != null) {
      str1 = DateFormatUtil.getFormatter("MM/dd/yyyy").format(localDateTime.getTime());
    }
    Currency localCurrency = (Currency)localArrayList.get(this._tranCols[1]);
    String str2 = null;
    if (this._tranCols[7] != -1)
    {
      localObject1 = (Integer)localArrayList.get(this._tranCols[7]);
      if (localObject1 != null) {
        switch (((Integer)localObject1).intValue())
        {
        case 1: 
          str2 = "*";
          break;
        case 2: 
        case 3: 
          str2 = "X";
        }
      }
    }
    Object localObject1 = null;
    if (this._tranCols[2] != -1) {
      localObject1 = (String)localArrayList.get(this._tranCols[2]);
    }
    String str3 = null;
    int i = 0;
    if (this._tranCols[3] != -1) {
      str3 = (String)localArrayList.get(this._tranCols[3]);
    }
    if ((str3 == null) && (this._tranCols[8] != -1)) {
      str3 = (String)localArrayList.get(this._tranCols[8]);
    }
    if ((str3 == null) && (this._tranCols[4] != -1))
    {
      str3 = (String)localArrayList.get(this._tranCols[4]);
      i = 1;
    }
    String str4 = null;
    if (this._tranCols[5] != -1) {
      str4 = (String)localArrayList.get(this._tranCols[5]);
    }
    if ((i == 0) && (str4 == null) && (this._tranCols[4] != -1))
    {
      str4 = (String)localArrayList.get(this._tranCols[4]);
      i = 1;
    }
    Object localObject2 = null;
    Object localObject3;
    Object localObject4;
    Object localObject5;
    Object localObject6;
    if (localArrayList.size() == 1)
    {
      if (this._tranCols[9] != -1) {
        localObject2 = (String)localArrayList.get(this._tranCols[9]);
      }
      if (this._tranCols[10] != -1)
      {
        localObject3 = (String)localArrayList.get(this._tranCols[10]);
        if (localObject3 != null) {
          if (localObject2 != null) {
            localObject2 = (String)localObject2 + ":" + (String)localObject3;
          } else {
            localObject2 = localObject3;
          }
        }
      }
    }
    else
    {
      localObject3 = localCurrency.getAmountValue();
      localObject4 = this._tranRows.iterator();
      ((Iterator)localObject4).next();
      while (((Iterator)localObject4).hasNext())
      {
        localObject5 = (ArrayList)((Iterator)localObject4).next();
        localObject6 = (Currency)((ArrayList)localObject5).get(this._tranCols[1]);
        if (localObject6 != null) {
          localObject3 = ((BigDecimal)localObject3).add(((Currency)localObject6).getAmountValue());
        }
      }
      localCurrency = new Currency((BigDecimal)localObject3, localCurrency.getLocale());
    }
    if (str1 != null) {
      this._buff.append("D").append(str1).append(getEOLString());
    }
    if (localCurrency != null)
    {
      localObject3 = localCurrency.getFormat();
      localCurrency.setFormat("DECIMAL");
      this._buff.append("T").append(localCurrency.getCurrencyStringNoSymbolNoComma()).append(getEOLString());
      localCurrency.setFormat((String)localObject3);
    }
    if (str2 != null) {
      this._buff.append("C").append(str2).append(getEOLString());
    }
    if (localObject1 != null) {
      this._buff.append("N").append((String)localObject1).append(getEOLString());
    }
    if (str3 != null) {
      this._buff.append("P").append(str3).append(getEOLString());
    }
    if (str4 != null) {
      this._buff.append("M").append(str4).append(getEOLString());
    }
    if (this._tranRows.size() == 1)
    {
      if (localObject2 != null) {
        this._buff.append("L").append((String)localObject2).append(getEOLString());
      }
    }
    else
    {
      localObject3 = this._tranRows.iterator();
      while (((Iterator)localObject3).hasNext())
      {
        localObject4 = (ArrayList)((Iterator)localObject3).next();
        localObject5 = null;
        if (this._tranCols[9] != -1) {
          localObject5 = (String)((ArrayList)localObject4).get(this._tranCols[9]);
        }
        if (this._tranCols[10] != -1)
        {
          localObject6 = (String)((ArrayList)localObject4).get(this._tranCols[10]);
          if (localObject6 != null) {
            if (localObject5 != null) {
              localObject5 = (String)localObject5 + ":" + (String)localObject6;
            } else {
              localObject5 = localObject6;
            }
          }
        }
        localObject6 = (Currency)((ArrayList)localObject4).get(this._tranCols[1]);
        if (localObject5 != null) {
          this._buff.append("S").append((String)localObject5).append(getEOLString());
        }
        if (localObject6 != null)
        {
          String str5 = ((Currency)localObject6).getFormat();
          ((Currency)localObject6).setFormat("DECIMAL");
          this._buff.append("$");
          this._buff.append(((Currency)localObject6).getCurrencyStringNoSymbolNoComma());
          this._buff.append(getEOLString());
          ((Currency)localObject6).setFormat(str5);
        }
      }
    }
    this._buff.append("^").append(getEOLString());
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
      String str = "The column name \"" + paramString + "\" was not found in the report to be exported.  This column name is required for the proper export " + "of this report in QIF format.";
      DebugLog.log(str);
      throw new RptException(100, str);
    }
    return i;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.reporting.ReportExporterQIF
 * JD-Core Version:    0.7.0.1
 */