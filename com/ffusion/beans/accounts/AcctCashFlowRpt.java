package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class AcctCashFlowRpt
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  public static final String ACCT_CASH_FLOW_RPT = "ACCT_CASH_FLOW_RPT";
  public static final String BEGINNING_ACCOUNT_HISTORIES = "BEGINNING_ACCOUNT_HISTORIES";
  public static final String ENDING_ACCOUNT_HISTORIES = "ENDING_ACCOUNT_HISTORIES";
  private AccountHistories Sr = null;
  private AccountHistories So = null;
  private Currency Sq = null;
  private Currency Sp = null;
  public static String _lineSeparator = null;
  
  public AcctCashFlowRpt(Locale paramLocale)
  {
    super.setLocale(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof AcctCashFlowRpt)) {
      return false;
    }
    AcctCashFlowRpt localAcctCashFlowRpt = (AcctCashFlowRpt)paramObject;
    return (this.Sr.equals(localAcctCashFlowRpt.getBeginningAccountHistories())) && (this.So.equals(localAcctCashFlowRpt.getEndingAccountHistories()));
  }
  
  public void setBeginningAccountHistories(AccountHistories paramAccountHistories)
  {
    this.Sr = paramAccountHistories;
  }
  
  public AccountHistories getBeginningAccountHistories()
  {
    return this.Sr;
  }
  
  public void setEndingAccountHistories(AccountHistories paramAccountHistories)
  {
    this.So = paramAccountHistories;
  }
  
  public AccountHistories getEndingAccountHistories()
  {
    return this.So;
  }
  
  public ArrayList getReportOutput()
  {
    ArrayList localArrayList = null;
    AccountHistory localAccountHistory1 = null;
    AccountHistory localAccountHistory2 = null;
    for (int i = 0; i < this.Sr.size(); i++)
    {
      localAccountHistory1 = (AccountHistory)this.Sr.get(i);
      localAccountHistory2 = (AccountHistory)this.So.get(i);
      Currency localCurrency1 = localAccountHistory1.getCurrentLedger();
      Currency localCurrency2 = localAccountHistory2.getCurrentLedger();
      Currency localCurrency3 = new Currency();
      if ((localCurrency1 != null) && (localCurrency2 != null))
      {
        double d = localCurrency2.doubleValue() - localCurrency1.doubleValue();
        Locale localLocale = localCurrency2.getLocale();
        localCurrency3.setLocale(localLocale);
        localCurrency3.setAmount(new BigDecimal(d));
      }
      if (localArrayList == null) {
        localArrayList = new ArrayList();
      }
      localArrayList.add(localAccountHistory1);
      localArrayList.add(localAccountHistory2);
      localArrayList.add(localCurrency3);
      if (this.Sq == null) {
        this.Sq = new Currency(new BigDecimal(0.0D), this.locale);
      }
      if (this.Sp == null) {
        this.Sp = new Currency(new BigDecimal(0.0D), this.locale);
      }
      if (localCurrency1 != null) {
        this.Sq.addAmount(localCurrency1);
      }
      if (localCurrency2 != null) {
        this.Sp.addAmount(localCurrency2);
      }
    }
    return localArrayList;
  }
  
  public Currency getTotalBeginningBalance()
  {
    return this.Sq;
  }
  
  public Currency getTotalEndingBalance()
  {
    return this.Sp;
  }
  
  public Currency getTotalNetIncrease()
  {
    if ((this.Sq == null) && (this.Sp == null)) {
      return new Currency(new BigDecimal(0.0D), this.locale);
    }
    double d = 0.0D;
    if (this.Sp == null) {
      d = -this.Sq.doubleValue();
    } else if (this.Sq == null) {
      d = this.Sp.doubleValue();
    } else {
      d = this.Sp.doubleValue() - this.Sq.doubleValue();
    }
    return new Currency(new BigDecimal(d), this.locale);
  }
  
  public DateTime getBeginDate()
  {
    if ((this.Sr == null) || (this.Sr.size() == 0)) {
      return null;
    }
    return ((AccountHistory)this.Sr.get(0)).getHistoryDate();
  }
  
  public DateTime getEndDate()
  {
    if ((this.So == null) || (this.So.size() == 0)) {
      return null;
    }
    return ((AccountHistory)this.So.get(0)).getHistoryDate();
  }
  
  private void jdMethod_int(StringBuffer paramStringBuffer, Currency paramCurrency)
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
    if (_lineSeparator == null) {
      _lineSeparator = System.getProperty("line.separator", "\n");
    }
    StringBuffer localStringBuffer = new StringBuffer();
    if ((this.Sr != null) && (this.So != null) && (this.Sr.size() == this.So.size()))
    {
      localStringBuffer.append(ReportConsts.getColumnName(160, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(161, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(162, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(163, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(164, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(165, this.locale));
      localStringBuffer.append(_lineSeparator);
      for (int i = 0; i < this.Sr.size(); i++)
      {
        AccountHistory localAccountHistory1 = (AccountHistory)this.Sr.get(i);
        AccountHistory localAccountHistory2 = (AccountHistory)this.So.get(i);
        String str1 = localAccountHistory1.getAccountID();
        if (str1 != null) {
          localStringBuffer.append(str1);
        }
        localStringBuffer.append(paramChar);
        String str2 = localAccountHistory1.getBankID();
        if (str2 != null) {
          localStringBuffer.append(str2);
        }
        localStringBuffer.append(paramChar);
        String str3 = localAccountHistory1.getRoutingNumber();
        if (str3 != null) {
          localStringBuffer.append(str3);
        }
        localStringBuffer.append(paramChar);
        Currency localCurrency1 = localAccountHistory1.getCurrentLedger();
        Currency localCurrency2 = localAccountHistory2.getCurrentLedger();
        if (localCurrency1 != null) {
          jdMethod_int(localStringBuffer, localCurrency1);
        }
        localStringBuffer.append(paramChar);
        if ((localCurrency1 != null) && (localCurrency2 != null))
        {
          double d = localCurrency2.doubleValue() - localCurrency1.doubleValue();
          Currency localCurrency3 = new Currency(new BigDecimal(d), null);
          jdMethod_int(localStringBuffer, localCurrency3);
        }
        localStringBuffer.append(paramChar);
        if (localCurrency2 != null) {
          jdMethod_int(localStringBuffer, localCurrency2);
        }
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
  
  public void set(AcctCashFlowRpt paramAcctCashFlowRpt)
  {
    super.set(paramAcctCashFlowRpt);
    setBeginningAccountHistories(paramAcctCashFlowRpt.getBeginningAccountHistories());
    setEndingAccountHistories(paramAcctCashFlowRpt.getEndingAccountHistories());
    setLocale(paramAcctCashFlowRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACCT_CASH_FLOW_RPT");
    if (this.Sr != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "BEGINNING_ACCOUNT_HISTORIES");
      localStringBuffer.append(this.Sr.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "BEGINNING_ACCOUNT_HISTORIES");
    }
    if (this.So != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "ENDING_ACCOUNT_HISTORIES");
      localStringBuffer.append(this.So.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "ENDING_ACCOUNT_HISTORIES");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACCT_CASH_FLOW_RPT");
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
      if (paramString.equals("BEGINNING_ACCOUNT_HISTORIES"))
      {
        if (AcctCashFlowRpt.this.Sr == null) {
          AcctCashFlowRpt.this.Sr = new AccountHistories();
        }
        AcctCashFlowRpt.this.Sr.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("ENDING_ACCOUNT_HISTORIES"))
      {
        if (AcctCashFlowRpt.this.So == null) {
          AcctCashFlowRpt.this.So = new AccountHistories();
        }
        AcctCashFlowRpt.this.So.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AcctCashFlowRpt
 * JD-Core Version:    0.7.0.1
 */