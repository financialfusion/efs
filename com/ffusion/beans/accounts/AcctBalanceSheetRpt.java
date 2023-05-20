package com.ffusion.beans.accounts;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class AcctBalanceSheetRpt
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  public static final String ACCT_BALANCE_SHEET_RPT = "ACCT_BALANCE_SHEET_RPT";
  public static final String REQ_ACC = "REQ_ACC";
  public static final String ACC_PENDING_TRANS = "ACC_PENDING_TRANS";
  private Accounts Sy = null;
  private Accounts Sz = null;
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  
  public AcctBalanceSheetRpt(Locale paramLocale)
  {
    super.setLocale(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof AcctBalanceSheetRpt))) {
      return false;
    }
    AcctBalanceSheetRpt localAcctBalanceSheetRpt = (AcctBalanceSheetRpt)paramObject;
    Accounts localAccounts1 = localAcctBalanceSheetRpt.getRequestedAccounts();
    Accounts localAccounts2 = localAcctBalanceSheetRpt.getAccountsWithPendingTrans();
    boolean bool1 = false;
    boolean bool2 = false;
    if (this.Sy == null) {
      bool1 = localAccounts1 == null;
    } else {
      bool1 = this.Sy.equals(localAccounts1);
    }
    if (this.Sz == null) {
      bool2 = localAccounts2 == null;
    } else {
      bool2 = this.Sz.equals(localAccounts2);
    }
    return (bool1) && (bool2);
  }
  
  public void setRequestedAccounts(Accounts paramAccounts)
  {
    this.Sy = paramAccounts;
  }
  
  public Accounts getRequestedAccounts()
  {
    return this.Sy;
  }
  
  public void setAccountsWithPendingTrans(Accounts paramAccounts)
  {
    this.Sz = paramAccounts;
  }
  
  public Accounts getAccountsWithPendingTrans()
  {
    return this.Sz;
  }
  
  private void jdMethod_case(StringBuffer paramStringBuffer, Currency paramCurrency)
  {
    if (paramCurrency != null) {
      paramStringBuffer.append(paramCurrency.removeCharFromString(paramCurrency.toString(), ','));
    }
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',', paramHashMap);
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t', paramHashMap);
    }
    return localStringBuffer;
  }
  
  protected StringBuffer getDelimitedReport(char paramChar, HashMap paramHashMap)
  {
    String str1 = (String)paramHashMap.get("REPORTTYPE");
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.Sy != null)
    {
      Object localObject1;
      Object localObject2;
      if (str1.equals("BalanceSheetReport"))
      {
        localStringBuffer.append(ReportConsts.getColumnName(140, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(141, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(142, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(143, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(144, this.locale));
        localStringBuffer.append(_lineSeparator);
        localObject1 = this.Sy.iterator();
        while (((Iterator)localObject1).hasNext())
        {
          localObject2 = (Account)((Iterator)localObject1).next();
          String str2 = ((Account)localObject2).getID();
          if (str2 != null) {
            localStringBuffer.append(str2);
          }
          localStringBuffer.append(paramChar);
          String str3 = ((Account)localObject2).getBankID();
          if (str3 != null) {
            localStringBuffer.append(str3);
          }
          localStringBuffer.append(paramChar);
          String str4 = ((Account)localObject2).getRoutingNum();
          if (str4 != null) {
            localStringBuffer.append(str4);
          }
          localStringBuffer.append(paramChar);
          String str5 = ((Account)localObject2).getType();
          if (str5 != null) {
            localStringBuffer.append(str5);
          }
          localStringBuffer.append(paramChar);
          Balance localBalance = ((Account)localObject2).getCurrentBalance();
          if (localBalance != null)
          {
            Currency localCurrency = localBalance.getAmountValue();
            if (localCurrency != null) {
              jdMethod_case(localStringBuffer, localCurrency);
            }
          }
          localStringBuffer.append(_lineSeparator);
        }
      }
      else if (str1.equals("BalanceSheetSummary"))
      {
        localStringBuffer.append(ReportConsts.getColumnName(656, this.locale));
        localStringBuffer.append(_lineSeparator);
        localStringBuffer.append(ReportConsts.getColumnName(667, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(668, this.locale));
        localStringBuffer.append(_lineSeparator);
        this.Sy.setFilter("TYPESTRING==Savings");
        if (this.Sy.size() != 0)
        {
          localStringBuffer.append(ReportConsts.getColumnName(657, this.locale));
          localStringBuffer.append(paramChar);
          localObject1 = this.Sy.getAccountsCurrentBalanceTotalCurrency();
          jdMethod_case(localStringBuffer, (Currency)localObject1);
          localStringBuffer.append(_lineSeparator);
        }
        this.Sy.setFilter("TYPESTRING==Checking");
        if (this.Sy.size() != 0)
        {
          localStringBuffer.append(ReportConsts.getColumnName(658, this.locale));
          localStringBuffer.append(paramChar);
          localObject1 = this.Sy.getAccountsCurrentBalanceTotalCurrency();
          jdMethod_case(localStringBuffer, (Currency)localObject1);
          localStringBuffer.append(_lineSeparator);
        }
        this.Sy.setFilter("ACCOUNTGROUP=1,and,TYPESTRING!!Savings,and,TYPESTRING!!Checking");
        if (this.Sy.size() != 0)
        {
          localStringBuffer.append(ReportConsts.getColumnName(659, this.locale));
          localStringBuffer.append(paramChar);
          localObject1 = this.Sy.getAccountsCurrentBalanceTotalCurrency();
          jdMethod_case(localStringBuffer, (Currency)localObject1);
          localStringBuffer.append(_lineSeparator);
        }
        this.Sy.setFilter("TYPESTRING=Fixed Deposit");
        if (this.Sy.size() != 0)
        {
          localStringBuffer.append(ReportConsts.getColumnName(660, this.locale));
          localStringBuffer.append(paramChar);
          localObject1 = this.Sy.getAccountsCurrentBalanceTotalCurrency();
          jdMethod_case(localStringBuffer, (Currency)localObject1);
          localStringBuffer.append(_lineSeparator);
        }
        this.Sy.setFilter("ACCOUNTGROUP=2,and,TYPESTRING!!Savings,and,TYPESTRING!!Checking");
        if (this.Sy.size() != 0)
        {
          localStringBuffer.append(ReportConsts.getColumnName(661, this.locale));
          localStringBuffer.append(paramChar);
          localObject1 = this.Sy.getAccountsCurrentBalanceTotalCurrency();
          jdMethod_case(localStringBuffer, (Currency)localObject1);
          localStringBuffer.append(_lineSeparator);
        }
        localStringBuffer.append(_lineSeparator);
        localStringBuffer.append(ReportConsts.getColumnName(662, this.locale));
        localStringBuffer.append(paramChar);
        this.Sy.setFilter("ACCOUNTGROUP=1,or,ACCOUNTGROUP=2");
        localObject1 = this.Sy.getAccountsCurrentBalanceTotalCurrency();
        jdMethod_case(localStringBuffer, (Currency)localObject1);
        localStringBuffer.append(_lineSeparator);
        localStringBuffer.append(_lineSeparator);
        localStringBuffer.append(ReportConsts.getColumnName(663, this.locale));
        localStringBuffer.append(_lineSeparator);
        localStringBuffer.append(ReportConsts.getColumnName(667, this.locale));
        localStringBuffer.append(paramChar);
        localStringBuffer.append(ReportConsts.getColumnName(668, this.locale));
        localStringBuffer.append(_lineSeparator);
        this.Sy.setFilter("ACCOUNTGROUP=4");
        if (this.Sy.size() != 0)
        {
          localStringBuffer.append(ReportConsts.getColumnName(664, this.locale));
          localStringBuffer.append(paramChar);
          localObject2 = this.Sy.getAccountsCurrentBalanceTotalCurrency();
          jdMethod_case(localStringBuffer, (Currency)localObject2);
          localStringBuffer.append(_lineSeparator);
        }
        this.Sy.setFilter("ACCOUNTGROUP=3");
        if (this.Sy.size() != 0)
        {
          localStringBuffer.append(ReportConsts.getColumnName(665, this.locale));
          localStringBuffer.append(paramChar);
          localObject2 = this.Sy.getAccountsCurrentBalanceTotalCurrency();
          jdMethod_case(localStringBuffer, (Currency)localObject2);
          localStringBuffer.append(_lineSeparator);
        }
        localStringBuffer.append(_lineSeparator);
        localStringBuffer.append(ReportConsts.getColumnName(666, this.locale));
        localStringBuffer.append(paramChar);
        this.Sy.setFilter("ACCOUNTGROUP=3,or,ACCOUNTGROUP=4");
        localObject2 = this.Sy.getAccountsCurrentBalanceTotalCurrency();
        jdMethod_case(localStringBuffer, (Currency)localObject2);
        this.Sy.setFilter("All");
      }
    }
    return localStringBuffer;
  }
  
  public void set(AcctBalanceSheetRpt paramAcctBalanceSheetRpt)
  {
    super.set(paramAcctBalanceSheetRpt);
    setRequestedAccounts(paramAcctBalanceSheetRpt.getRequestedAccounts());
    setAccountsWithPendingTrans(paramAcctBalanceSheetRpt.getAccountsWithPendingTrans());
    setLocale(paramAcctBalanceSheetRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACCT_BALANCE_SHEET_RPT");
    if (this.Sy != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "REQ_ACC");
      localStringBuffer.append(this.Sy.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "REQ_ACC");
    }
    if (this.Sz != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "ACC_PENDING_TRANS");
      localStringBuffer.append(this.Sz.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "ACC_PENDING_TRANS");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACCT_BALANCE_SHEET_RPT");
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
      if (paramString.equals("REQ_ACC"))
      {
        if (AcctBalanceSheetRpt.this.Sy == null) {
          AcctBalanceSheetRpt.this.Sy = new Accounts();
        }
        AcctBalanceSheetRpt.this.Sy.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("ACC_PENDING_TRANS"))
      {
        if (AcctBalanceSheetRpt.this.Sz == null) {
          AcctBalanceSheetRpt.this.Sz = new Accounts();
        }
        AcctBalanceSheetRpt.this.Sz.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AcctBalanceSheetRpt
 * JD-Core Version:    0.7.0.1
 */