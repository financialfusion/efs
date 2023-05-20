package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.util.CurrencyEstimate;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class AcctCashFlowFcstRpt
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  public static final String ACCT_CASH_FLOW_FCST_RPT = "ACCT_CASH_FLOW_FCST_RPT";
  private AccountHistories Ss = null;
  private Accounts Sv = null;
  private ArrayList St = null;
  private DateTime Su = null;
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  
  public AcctCashFlowFcstRpt(Locale paramLocale)
  {
    super.setLocale(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof AcctCashFlowFcstRpt))) {
      return false;
    }
    AcctCashFlowFcstRpt localAcctCashFlowFcstRpt = (AcctCashFlowFcstRpt)paramObject;
    AccountHistories localAccountHistories = localAcctCashFlowFcstRpt.getAccountHistories();
    Accounts localAccounts = localAcctCashFlowFcstRpt.getAccounts();
    boolean bool1 = false;
    boolean bool2 = false;
    if (this.Ss == null) {
      bool1 = localAccountHistories == null;
    } else {
      bool1 = this.Ss.equals(localAccountHistories);
    }
    if (this.Sv == null) {
      bool2 = localAccounts == null;
    } else {
      bool2 = this.Sv.equals(localAccounts);
    }
    return (bool1) && (bool2);
  }
  
  private void F()
  {
    if (this.Sv == null) {
      return;
    }
    if (this.Ss == null) {
      return;
    }
    if (this.Su == null) {
      return;
    }
    this.St = new ArrayList(this.Sv.size());
    Iterator localIterator1 = this.Sv.iterator();
    while (localIterator1.hasNext())
    {
      Account localAccount = (Account)localIterator1.next();
      jdMethod_for(localAccount);
      CurrencyEstimate localCurrencyEstimate = new CurrencyEstimate();
      Iterator localIterator2 = this.Ss.iterator();
      while (localIterator2.hasNext())
      {
        AccountHistory localAccountHistory = (AccountHistory)localIterator2.next();
        localCurrencyEstimate.addPoint(localAccountHistory.getHistoryDate(), localAccountHistory.getCurrentLedger());
      }
      localCurrencyEstimate.calculateRegression();
      this.St.add(localCurrencyEstimate.getEstimated(this.Su));
    }
    this.Ss.setFilter("");
  }
  
  private void jdMethod_for(Account paramAccount)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("and,").append("ACCOUNT_ID").append("==").append(paramAccount.getID());
    if (paramAccount.getRoutingNum() != null) {
      localStringBuffer.append(",").append("ROUTING_NUMBER").append("==").append(paramAccount.getRoutingNum());
    }
    if (paramAccount.getBankID() != null) {
      localStringBuffer.append(",").append("BANK_ID").append("==").append(paramAccount.getBankID());
    }
    this.Ss.setFilter(localStringBuffer.toString());
  }
  
  public void setForecastDate(DateTime paramDateTime)
  {
    this.Su = paramDateTime;
    F();
  }
  
  public ArrayList getForecastBalances()
  {
    return this.St;
  }
  
  public void setAccountHistories(AccountHistories paramAccountHistories)
  {
    this.Ss = paramAccountHistories;
    F();
  }
  
  public AccountHistories getAccountHistories()
  {
    return this.Ss;
  }
  
  public void setAccounts(Accounts paramAccounts)
  {
    this.Sv = paramAccounts;
    F();
  }
  
  public Accounts getAccounts()
  {
    return this.Sv;
  }
  
  private void jdMethod_new(StringBuffer paramStringBuffer, Currency paramCurrency)
  {
    if (paramCurrency != null) {
      paramStringBuffer.append(paramCurrency.removeCharFromString(paramCurrency.toString(), ','));
    }
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
    if ((this.Sv != null) && (this.Ss != null) && (this.St != null) && (this.Sv.size() == this.St.size()))
    {
      localStringBuffer.append(ReportConsts.getColumnName(150, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(151, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(152, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(153, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(154, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(155, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(156, this.locale));
      localStringBuffer.append(_lineSeparator);
      for (int i = 0; i < this.Sv.size(); i++)
      {
        Account localAccount = (Account)this.Sv.get(i);
        Currency localCurrency1 = (Currency)this.St.get(i);
        jdMethod_for(localAccount);
        String str1 = localAccount.getID();
        if (str1 != null) {
          localStringBuffer.append(str1);
        }
        localStringBuffer.append(paramChar);
        String str2 = localAccount.getBankID();
        if (str2 != null) {
          localStringBuffer.append(str2);
        }
        localStringBuffer.append(paramChar);
        String str3 = localAccount.getRoutingNum();
        if (str3 != null) {
          localStringBuffer.append(str3);
        }
        localStringBuffer.append(paramChar);
        Currency localCurrency2 = null;
        AccountHistory localAccountHistory;
        if (this.Ss.size() > 0)
        {
          localAccountHistory = (AccountHistory)this.Ss.get(0);
          localCurrency2 = localAccountHistory.getCurrentLedger();
          if (localCurrency2 != null) {
            jdMethod_new(localStringBuffer, localCurrency2);
          }
        }
        localStringBuffer.append(paramChar);
        if (this.Ss.size() > 0)
        {
          localAccountHistory = (AccountHistory)this.Ss.get(this.Ss.size() - 1);
          Currency localCurrency3 = localAccountHistory.getCurrentLedger();
          if (localCurrency3 != null) {
            jdMethod_new(localStringBuffer, localCurrency3);
          }
        }
        localStringBuffer.append(paramChar);
        if (localCurrency1 != null) {
          jdMethod_new(localStringBuffer, localCurrency1);
        }
        localStringBuffer.append(paramChar);
        if (localCurrency1 != null)
        {
          double d = localCurrency1.doubleValue() - localCurrency2.doubleValue();
          Locale localLocale = localCurrency2.getLocale();
          Currency localCurrency4 = new Currency();
          localCurrency4.setLocale(localLocale);
          localCurrency4.setAmount(new BigDecimal(d));
          jdMethod_new(localStringBuffer, localCurrency4);
        }
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
  
  public void set(AcctCashFlowFcstRpt paramAcctCashFlowFcstRpt)
  {
    super.set(paramAcctCashFlowFcstRpt);
    setAccountHistories(paramAcctCashFlowFcstRpt.getAccountHistories());
    setAccounts(paramAcctCashFlowFcstRpt.getAccounts());
    setLocale(paramAcctCashFlowFcstRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACCT_CASH_FLOW_FCST_RPT");
    if (this.Ss != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "ACCOUNT_HISTORIES");
      localStringBuffer.append(this.Ss.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "ACCOUNT_HISTORIES");
    }
    if (this.Sv != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "ACCOUNTS");
      localStringBuffer.append(this.Sv.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "ACCOUNTS");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACCT_CASH_FLOW_FCST_RPT");
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
      if (paramString.equals("ACCOUNT_HISTORIES"))
      {
        if (AcctCashFlowFcstRpt.this.Ss == null) {
          AcctCashFlowFcstRpt.this.Ss = new AccountHistories();
        }
        AcctCashFlowFcstRpt.this.Ss.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("ACCOUNTS"))
      {
        if (AcctCashFlowFcstRpt.this.Sv == null) {
          AcctCashFlowFcstRpt.this.Sv = new Accounts();
        }
        AcctCashFlowFcstRpt.this.Sv.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AcctCashFlowFcstRpt
 * JD-Core Version:    0.7.0.1
 */