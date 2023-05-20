package com.ffusion.beans.accounts;

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

public class AccountHistoryRpt
  extends ExtendABean
  implements IReportResult, ExportFormats, Serializable
{
  public static final String ACCT_HISTORY_RPT = "ACCT_HISTORY_RPT";
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  private Accounts Sn = null;
  private AccountHistories Sm = null;
  
  public AccountHistoryRpt(Locale paramLocale)
  {
    super.setLocale(paramLocale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof AccountHistoryRpt))) {
      return false;
    }
    AccountHistoryRpt localAccountHistoryRpt = (AccountHistoryRpt)paramObject;
    AccountHistories localAccountHistories = localAccountHistoryRpt.getAccountHistories();
    Accounts localAccounts = localAccountHistoryRpt.getAccounts();
    boolean bool1 = false;
    boolean bool2 = false;
    if (this.Sm == null) {
      bool1 = localAccountHistories == null;
    } else {
      bool1 = this.Sm.equals(localAccountHistories);
    }
    if (this.Sn == null) {
      bool2 = localAccounts == null;
    } else {
      bool2 = this.Sn.equals(localAccounts);
    }
    return (bool1) && (bool2);
  }
  
  public void setAccounts(Accounts paramAccounts)
  {
    this.Sn = paramAccounts;
  }
  
  public Accounts getAccounts()
  {
    return this.Sn;
  }
  
  public void setAccountHistories(AccountHistories paramAccountHistories)
  {
    this.Sm = paramAccountHistories;
  }
  
  public AccountHistories getAccountHistories()
  {
    return this.Sm;
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
    if (this.Sm != null)
    {
      localStringBuffer.append(ReportConsts.getColumnName(80, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(82, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(83, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(84, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(85, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(86, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(87, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(88, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(89, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(90, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(91, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(92, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(93, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(94, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(95, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(96, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(97, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(98, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(134, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(99, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(100, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(101, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(102, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(103, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(104, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(105, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(106, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(107, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(108, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(109, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(110, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(111, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(112, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(113, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(114, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(115, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(116, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(117, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(118, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(119, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(120, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(121, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(122, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(123, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(124, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(125, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(126, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(127, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(128, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(129, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(130, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(131, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(132, this.locale));
      localStringBuffer.append(paramChar);
      localStringBuffer.append(ReportConsts.getColumnName(133, this.locale));
      localStringBuffer.append(_lineSeparator);
      Iterator localIterator = this.Sm.iterator();
      Account localAccount = null;
      AccountHistory localAccountHistory = null;
      while (localIterator.hasNext())
      {
        localAccountHistory = (AccountHistory)localIterator.next();
        if ((localAccount == null) || (!jdMethod_for(localAccount, localAccountHistory)))
        {
          localAccount = this.Sn.getByNumberAndRoutingNum(localAccountHistory.getAccountNumber(), localAccountHistory.getRoutingNumber());
          localStringBuffer.append(_lineSeparator).append(localAccount.getRoutingNum()).append(paramChar);
          localStringBuffer.append(localAccount.getNumber()).append(paramChar).append(localAccount.getNickName());
          localStringBuffer.append(_lineSeparator);
        }
        localStringBuffer.append(localAccountHistory.getHistoryDate());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getCurrentLedger());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getOpeningLedger());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgOpeningLedgerMTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgOpeningLedgerYTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getClosingLedger());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgClosingLedgerMTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgClosingLedgerYTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgClosingLedgerYTDPrevMonth());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgMonth());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAggregateBalAdjustment());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getNetPositionACH());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getOpenAvailSameDayACHDTC());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getOpeningAvail());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgOpenAvailMTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgOpenAvailYTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgAvailPrevMonth());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getDisbursingOpeningAvailBal());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getClosingAvail());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgClosingAvailMTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgClosingAvailPrevMonth());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgClosingAvailYTDPrevMonth());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgClosingAvailYTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getLoanBal());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getTotalInvestmentPosition());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getCurrentAvailCRSSurpressed());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getCurrentAvail());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgCurrentAvailMTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgCurrentAvailYTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getTotalFloat());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getTargetBal());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAdjustedBal());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAdjustedBalMTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAdjustedBalYTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getZeroDayFloat());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getOneDayFloat());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getFloatAdjusted());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getTwoOrMoreDayFloat());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getThreeOrMoreDayFloat());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAdjustmentToBal());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgAdjustmentToBalMTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgAdjustmentToBalYTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getFourDayFloat());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getFiveDayFloat());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getSixDayFloat());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgOneDayFloatMTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgOneDayFloatYTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgTwoDayFloatMTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getAvgTwoDayFloatYTD());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getTransferCalculation());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getTargetBalDeficiency());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getTotalFundingRequirement());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getTotalChecksPaid());
        localStringBuffer.append(paramChar);
        jdMethod_for(localStringBuffer, localAccountHistory.getGrandTotalCreditMinusDebit());
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
  
  private boolean jdMethod_for(Account paramAccount, AccountHistory paramAccountHistory)
  {
    if (!paramAccount.getNumber().equals(paramAccountHistory.getAccountNumber())) {
      return false;
    }
    if ((paramAccount.getRoutingNum() != null) || (paramAccountHistory.getRoutingNumber() != null))
    {
      if ((paramAccount.getRoutingNum() == null) || (paramAccountHistory.getRoutingNumber() == null)) {
        return false;
      }
      if (!paramAccount.getRoutingNum().equals(paramAccountHistory.getRoutingNumber())) {
        return false;
      }
    }
    if ((paramAccount.getBankID() != null) || (paramAccountHistory.getBankID() != null))
    {
      if ((paramAccount.getBankID() == null) || (paramAccountHistory.getBankID() == null)) {
        return false;
      }
      if (!paramAccount.getBankID().equals(paramAccountHistory.getBankID())) {
        return false;
      }
    }
    return true;
  }
  
  private void jdMethod_for(StringBuffer paramStringBuffer, Currency paramCurrency)
  {
    if (paramCurrency != null)
    {
      paramCurrency.setFormat("DECIMAL");
      paramStringBuffer.append(paramCurrency.toString());
    }
  }
  
  public void set(AccountHistoryRpt paramAccountHistoryRpt)
  {
    super.set(paramAccountHistoryRpt);
    setAccounts(paramAccountHistoryRpt.getAccounts());
    setAccountHistories(paramAccountHistoryRpt.getAccountHistories());
    setLocale(paramAccountHistoryRpt.locale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACCT_HISTORY_RPT");
    if (this.Sm != null) {
      localStringBuffer.append(this.Sm.getXML());
    }
    if (this.Sn != null) {
      localStringBuffer.append(this.Sn.getXML());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACCT_HISTORY_RPT");
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
        if (AccountHistoryRpt.this.Sm == null) {
          AccountHistoryRpt.this.Sm = new AccountHistories();
        }
        AccountHistoryRpt.this.Sm.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("ACCOUNTS"))
      {
        AccountHistoryRpt.this.Sn = new Accounts(AccountHistoryRpt.this.locale);
        AccountHistoryRpt.this.Sn.continueXMLParsing(getHandler(), false);
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AccountHistoryRpt
 * JD-Core Version:    0.7.0.1
 */