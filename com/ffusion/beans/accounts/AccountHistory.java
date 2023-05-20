package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.text.Collator;
import java.util.Locale;

public class AccountHistory
  extends ExtendABean
{
  public static final String ACCOUNT_HISTORY = "ACCOUNT_HISTORY";
  public static final String ACCOUNT_ID = "ACCOUNT_ID";
  public static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public static final String ROUTING_NUMBER = "ROUTING_NUMBER";
  public static final String BANK_ID = "BANK_ID";
  public static final String HISTORY_DATE = "HISTORY_DATE";
  public static final String OPENING_LEDGER = "OPENING_LEDGER";
  public static final String AVG_OPENING_LEDGER_MTD = "AVG_OPENING_LEDGER_MTD";
  public static final String AVG_OPENING_LEDGER_YTD = "AVG_OPENING_LEDGER_YTD";
  public static final String CLOSING_LEDGER = "CLOSING_LEDGER";
  public static final String AVG_CLOSING_LEDGER_MTD = "AVG_CLOSING_LEDGER_MTD";
  public static final String AVG_MONTH = "AVG_MONTH";
  public static final String AGGREGATE_BAL_ADJUSTMENT = "AGGREGATE_BAL_ADJUSTMENT";
  public static final String AVG_CLOSING_LEDGER_YTD_PREV_MONTH = "AVG_CLOSING_LEDGER_YTD_PREV_MONTH";
  public static final String AVG_CLOSING_LEDGER_YTD = "AVG_CLOSING_LEDGER_YTD";
  public static final String CURRENT_LEDGER = "CURRENT_LEDGER";
  public static final String NET_POSITION_ACH = "NET_POSITION_ACH";
  public static final String OPEN_AVAIL_SAME_DAY_ACH_DTC = "OPEN_AVAIL_SAME_DAY_ACH_DTC";
  public static final String OPENING_AVAIL = "OPENING_AVAIL";
  public static final String AVG_OPEN_AVAIL_MTD = "AVG_OPEN_AVAIL_MTD";
  public static final String AVG_OPEN_AVAIL_YTD = "AVG_OPEN_AVAIL_YTD";
  public static final String AVG_AVAIL_PREV_MONTH = "AVG_AVAIL_PREV_MONTH";
  public static final String DISBURSING_OPENING_AVAIL_BAL = "DISBURSING_OPENING_AVAIL_BAL";
  public static final String CLOSING_AVAIL = "CLOSING_AVAIL";
  public static final String AVG_CLOSING_AVAIL_MTD = "AVG_CLOSING_AVAIL_MTD";
  public static final String AVG_CLOSING_AVAIL_PREV_MONTH = "AVG_CLOSING_AVAIL_PREV_MONTH";
  public static final String AVG_CLOSING_AVAIL_YTD_PREV_MONTH = "AVG_CLOSING_AVAIL_YTD_PREV_MONTH";
  public static final String AVG_CLOSING_AVAIL_YTD = "AVG_CLOSING_AVAIL_YTD";
  public static final String LOAN_BAL = "LOAN_BAL";
  public static final String TOTAL_INVESTMENT_POSITION = "TOTAL_INVESTMENT_POSITION";
  public static final String CURRENT_AVAIL_CRS_SURPRESSED = "CURRENT_AVAIL_CRS_SURPRESSED";
  public static final String CURRENT_AVAIL = "CURRENT_AVAIL";
  public static final String AVG_CURRENT_AVAIL_MTD = "AVG_CURRENT_AVAIL_MTD";
  public static final String AVG_CURRENT_AVAIL_YTD = "AVG_CURRENT_AVAIL_YTD";
  public static final String TOTAL_FLOAT = "TOTAL_FLOAT";
  public static final String TARGET_BAL = "TARGET_BAL";
  public static final String ADJUSTED_BAL = "ADJUSTED_BAL";
  public static final String ADJUSTED_BAL_MTD = "ADJUSTED_BAL_MTD";
  public static final String ADJUSTED_BAL_YTD = "ADJUSTED_BAL_YTD";
  public static final String ZERO_DAY_FLOAT = "ZERO_DAY_FLOAT";
  public static final String ONE_DAY_FLOAT = "ONE_DAY_FLOAT";
  public static final String FLOAT_ADJUSTMENT = "FLOAT_ADJUSTMENT";
  public static final String TWO_OR_MORE_DAY_FLOAT = "TWO_OR_MORE_DAY_FLOAT";
  public static final String THREE_OR_MORE_DAY_FLOAT = "THREE_OR_MORE_DAY_FLOAT";
  public static final String ADJUSTMENT_TO_BAL = "ADJUSTMENT_TO_BAL";
  public static final String AVG_ADJUSTMENT_TO_BAL_MTD = "AVG_ADJUSTMENT_TO_BAL_MTD";
  public static final String AVG_ADJUSTMENT_TO_BAL_YTD = "AVG_ADJUSTMENT_TO_BAL_YTD";
  public static final String FOUR_DAY_FLOAT = "FOUR_DAY_FLOAT";
  public static final String FIVE_DAY_FLOAT = "FIVE_DAY_FLOAT";
  public static final String SIX_DAY_FLOAT = "SIX_DAY_FLOAT";
  public static final String AVG_ONE_DAY_FLOAT_MTD = "AVG_ONE_DAY_FLOAT_MTD";
  public static final String AVG_ONE_DAY_FLOAT_YTD = "AVG_ONE_DAY_FLOAT_YTD";
  public static final String AVG_TWO_DAY_FLOAT_MTD = "AVG_TWO_DAY_FLOAT_MTD";
  public static final String AVG_TWO_DAY_FLOAT_YTD = "AVG_TWO_DAY_FLOAT_YTD";
  public static final String TRANSFER_CALCULATION = "TRANSFER_CALCULATION";
  public static final String TARGET_BALDEFICIENCY = "TARGET_BALDEFICIENCY";
  public static final String TOTAL_FUNDING_REQUIREMENT = "TOTAL_FUNDING_REQUIREMENT";
  public static final String TOTAL_CHECKS_PAID = "TOTAL_CHECKS_PAID";
  public static final String NUM_CHECKS_PAID = "NUM_CHECKS_PAID";
  public static final String GRAND_TOTAL_CREDITS_MINUS_GRAND_TOTAL_DEBITS = "GRAND_TOTAL_CREDITS_MINUS_GRAND_TOTAL_DEBITS";
  public static final String GRAND_NUM_CREDITS_MINUS_GRAND_NUM_DEBITS = "GRAND_NUM_CREDITS_MINUS_GRAND_NUM_DEBITS";
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.banking.resources";
  private String a0J;
  private String a1l;
  private String a0K;
  private String a0L;
  private DateTime a0z;
  private Currency a0B;
  private Currency a0w;
  private Currency a09;
  private Currency a1a;
  private Currency a0R;
  private Currency a1f;
  private Currency a1e;
  private Currency a0u;
  private Currency a1m;
  private Currency a0V;
  private Currency a05;
  private Currency a0T;
  private Currency a0N;
  private Currency a0Q;
  private Currency a1k;
  private Currency a0X;
  private Currency a0A;
  private Currency a0Z;
  private Currency a08;
  private Currency a0s;
  private Currency a0x;
  private Currency a0M;
  private Currency a0D;
  private Currency a00;
  private Currency a1i;
  private Currency a0S;
  private Currency a03;
  private Currency a0H;
  private Currency a1d;
  private Currency a1o;
  private Currency a0I;
  private Currency a02;
  private Currency a0G;
  private Currency a0W;
  private Currency a0U;
  private Currency a0r;
  private Currency a1h;
  private Currency a0t;
  private Currency a1c;
  private Currency a0C;
  private Currency a1b;
  private Currency a04;
  private Currency a07;
  private Currency a0E;
  private Currency a0P;
  private Currency a1j;
  private Currency a01;
  private Currency a0F;
  private Currency a0y;
  private Currency a0O;
  private Currency a1n;
  private Currency a0Y;
  private int a06 = -1;
  private Currency a0v;
  private int a1g = -1;
  
  public String getAccountID()
  {
    return this.a0J;
  }
  
  public String getAccountNumber()
  {
    return this.a1l;
  }
  
  public void setAccountID(String paramString)
  {
    this.a0J = paramString;
  }
  
  public void setAccountNumber(String paramString)
  {
    this.a1l = paramString;
  }
  
  public String getRoutingNumber()
  {
    return this.a0K;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.a0K = paramString;
  }
  
  public String getBankID()
  {
    return this.a0L;
  }
  
  public void setBankID(String paramString)
  {
    this.a0L = paramString;
  }
  
  public DateTime getHistoryDate()
  {
    return this.a0z;
  }
  
  public void setHistoryDate(DateTime paramDateTime)
  {
    this.a0z = paramDateTime;
  }
  
  public void setHistoryDate(String paramString)
  {
    try
    {
      if (this.a0z == null) {
        this.a0z = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a0z.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Currency getOpeningLedger()
  {
    return this.a0B;
  }
  
  public void setOpeningLedger(Currency paramCurrency)
  {
    this.a0B = paramCurrency;
  }
  
  public void setOpeningLedger(String paramString)
  {
    if (this.a0B == null) {
      this.a0B = new Currency(paramString, this.locale);
    } else {
      this.a0B.fromString(paramString);
    }
  }
  
  public Currency getAvgOpeningLedgerMTD()
  {
    return this.a0w;
  }
  
  public void setAvgOpeningLedgerMTD(Currency paramCurrency)
  {
    this.a0w = paramCurrency;
  }
  
  public void setAvgOpeningLedgerMTD(String paramString)
  {
    if (this.a0w == null) {
      this.a0w = new Currency(paramString, this.locale);
    } else {
      this.a0w.fromString(paramString);
    }
  }
  
  public Currency getAvgOpeningLedgerYTD()
  {
    return this.a09;
  }
  
  public void setAvgOpeningLedgerYTD(Currency paramCurrency)
  {
    this.a09 = paramCurrency;
  }
  
  public void setAvgOpeningLedgerYTD(String paramString)
  {
    if (this.a09 == null) {
      this.a09 = new Currency(paramString, this.locale);
    } else {
      this.a09.fromString(paramString);
    }
  }
  
  public Currency getClosingLedger()
  {
    return this.a1a;
  }
  
  public void setClosingLedger(Currency paramCurrency)
  {
    this.a1a = paramCurrency;
  }
  
  public void setClosingLedger(String paramString)
  {
    if (this.a1a == null) {
      this.a1a = new Currency(paramString, this.locale);
    } else {
      this.a1a.fromString(paramString);
    }
  }
  
  public Currency getAvgClosingLedgerMTD()
  {
    return this.a0R;
  }
  
  public void setAvgClosingLedgerMTD(Currency paramCurrency)
  {
    this.a0R = paramCurrency;
  }
  
  public void setAvgClosingLedgerMTD(String paramString)
  {
    if (this.a0R == null) {
      this.a0R = new Currency(paramString, this.locale);
    } else {
      this.a0R.fromString(paramString);
    }
  }
  
  public Currency getAvgMonth()
  {
    return this.a1f;
  }
  
  public void setAvgMonth(Currency paramCurrency)
  {
    this.a1f = paramCurrency;
  }
  
  public void setAvgMonth(String paramString)
  {
    if (this.a1f == null) {
      this.a1f = new Currency(paramString, this.locale);
    } else {
      this.a1f.fromString(paramString);
    }
  }
  
  public Currency getAggregateBalAdjustment()
  {
    return this.a1e;
  }
  
  public void setAggregateBalAdjustment(Currency paramCurrency)
  {
    this.a1e = paramCurrency;
  }
  
  public void setAggregateBalAdjustment(String paramString)
  {
    if (this.a1e == null) {
      this.a1e = new Currency(paramString, this.locale);
    } else {
      this.a1e.fromString(paramString);
    }
  }
  
  public Currency getAvgClosingLedgerYTDPrevMonth()
  {
    return this.a0u;
  }
  
  public void setAvgClosingLedgerYTDPrevMonth(Currency paramCurrency)
  {
    this.a0u = paramCurrency;
  }
  
  public void setAvgClosingLedgerYTDPrevMonth(String paramString)
  {
    if (this.a0u == null) {
      this.a0u = new Currency(paramString, this.locale);
    } else {
      this.a0u.fromString(paramString);
    }
  }
  
  public Currency getAvgClosingLedgerYTD()
  {
    return this.a1m;
  }
  
  public void setAvgClosingLedgerYTD(Currency paramCurrency)
  {
    this.a1m = paramCurrency;
  }
  
  public void setAvgClosingLedgerYTD(String paramString)
  {
    if (this.a1m == null) {
      this.a1m = new Currency(paramString, this.locale);
    } else {
      this.a1m.fromString(paramString);
    }
  }
  
  public Currency getCurrentLedger()
  {
    return this.a0V;
  }
  
  public void setCurrentLedger(Currency paramCurrency)
  {
    this.a0V = paramCurrency;
  }
  
  public void setCurrentLedger(String paramString)
  {
    if (this.a0V == null) {
      this.a0V = new Currency(paramString, this.locale);
    } else {
      this.a0V.fromString(paramString);
    }
  }
  
  public Currency getNetPositionACH()
  {
    return this.a05;
  }
  
  public void setNetPositionACH(Currency paramCurrency)
  {
    this.a05 = paramCurrency;
  }
  
  public void setNetPositionACH(String paramString)
  {
    if (this.a05 == null) {
      this.a05 = new Currency(paramString, this.locale);
    } else {
      this.a05.fromString(paramString);
    }
  }
  
  public Currency getOpenAvailSameDayACHDTC()
  {
    return this.a0T;
  }
  
  public void setOpenAvailSameDayACHDTC(Currency paramCurrency)
  {
    this.a0T = paramCurrency;
  }
  
  public void setOpenAvailSameDayACHDTC(String paramString)
  {
    if (this.a0T == null) {
      this.a0T = new Currency(paramString, this.locale);
    } else {
      this.a0T.fromString(paramString);
    }
  }
  
  public Currency getOpeningAvail()
  {
    return this.a0N;
  }
  
  public void setOpeningAvail(Currency paramCurrency)
  {
    this.a0N = paramCurrency;
  }
  
  public void setOpeningAvail(String paramString)
  {
    if (this.a0N == null) {
      this.a0N = new Currency(paramString, this.locale);
    } else {
      this.a0N.fromString(paramString);
    }
  }
  
  public Currency getAvgOpenAvailMTD()
  {
    return this.a0Q;
  }
  
  public void setAvgOpenAvailMTD(Currency paramCurrency)
  {
    this.a0Q = paramCurrency;
  }
  
  public void setAvgOpenAvailMTD(String paramString)
  {
    if (this.a0Q == null) {
      this.a0Q = new Currency(paramString, this.locale);
    } else {
      this.a0Q.fromString(paramString);
    }
  }
  
  public Currency getAvgOpenAvailYTD()
  {
    return this.a1k;
  }
  
  public void setAvgOpenAvailYTD(Currency paramCurrency)
  {
    this.a1k = paramCurrency;
  }
  
  public void setAvgOpenAvailYTD(String paramString)
  {
    if (this.a1k == null) {
      this.a1k = new Currency(paramString, this.locale);
    } else {
      this.a1k.fromString(paramString);
    }
  }
  
  public Currency getAvgAvailPrevMonth()
  {
    return this.a0X;
  }
  
  public void setAvgAvailPrevMonth(Currency paramCurrency)
  {
    this.a0X = paramCurrency;
  }
  
  public void setAvgAvailPrevMonth(String paramString)
  {
    if (this.a0X == null) {
      this.a0X = new Currency(paramString, this.locale);
    } else {
      this.a0X.fromString(paramString);
    }
  }
  
  public Currency getDisbursingOpeningAvailBal()
  {
    return this.a0A;
  }
  
  public void setDisbursingOpeningAvailBal(Currency paramCurrency)
  {
    this.a0A = paramCurrency;
  }
  
  public void setDisbursingOpeningAvailBal(String paramString)
  {
    if (this.a0A == null) {
      this.a0A = new Currency(paramString, this.locale);
    } else {
      this.a0A.fromString(paramString);
    }
  }
  
  public Currency getClosingAvail()
  {
    return this.a0Z;
  }
  
  public void setClosingAvail(Currency paramCurrency)
  {
    this.a0Z = paramCurrency;
  }
  
  public void setClosingAvail(String paramString)
  {
    if (this.a0Z == null) {
      this.a0Z = new Currency(paramString, this.locale);
    } else {
      this.a0Z.fromString(paramString);
    }
  }
  
  public Currency getAvgClosingAvailMTD()
  {
    return this.a08;
  }
  
  public void setAvgClosingAvailMTD(Currency paramCurrency)
  {
    this.a08 = paramCurrency;
  }
  
  public void setAvgClosingAvailMTD(String paramString)
  {
    if (this.a08 == null) {
      this.a08 = new Currency(paramString, this.locale);
    } else {
      this.a08.fromString(paramString);
    }
  }
  
  public Currency getAvgClosingAvailPrevMonth()
  {
    return this.a0s;
  }
  
  public void setAvgClosingAvailPrevMonth(Currency paramCurrency)
  {
    this.a0s = paramCurrency;
  }
  
  public void setAvgClosingAvailPrevMonth(String paramString)
  {
    if (this.a0s == null) {
      this.a0s = new Currency(paramString, this.locale);
    } else {
      this.a0s.fromString(paramString);
    }
  }
  
  public Currency getAvgClosingAvailYTDPrevMonth()
  {
    return this.a0x;
  }
  
  public void setAvgClosingAvailYTDPrevMonth(Currency paramCurrency)
  {
    this.a0x = paramCurrency;
  }
  
  public void setAvgClosingAvailYTDPrevMonth(String paramString)
  {
    if (this.a0x == null) {
      this.a0x = new Currency(paramString, this.locale);
    } else {
      this.a0x.fromString(paramString);
    }
  }
  
  public Currency getAvgClosingAvailYTD()
  {
    return this.a0M;
  }
  
  public void setAvgClosingAvailYTD(Currency paramCurrency)
  {
    this.a0M = paramCurrency;
  }
  
  public void setAvgClosingAvailYTD(String paramString)
  {
    if (this.a0M == null) {
      this.a0M = new Currency(paramString, this.locale);
    } else {
      this.a0M.fromString(paramString);
    }
  }
  
  public Currency getLoanBal()
  {
    return this.a0D;
  }
  
  public void setLoanBal(Currency paramCurrency)
  {
    this.a0D = paramCurrency;
  }
  
  public void setLoanBal(String paramString)
  {
    if (this.a0D == null) {
      this.a0D = new Currency(paramString, this.locale);
    } else {
      this.a0D.fromString(paramString);
    }
  }
  
  public Currency getTotalInvestmentPosition()
  {
    return this.a00;
  }
  
  public void setTotalInvestmentPosition(Currency paramCurrency)
  {
    this.a00 = paramCurrency;
  }
  
  public void setTotalInvestmentPosition(String paramString)
  {
    if (this.a00 == null) {
      this.a00 = new Currency(paramString, this.locale);
    } else {
      this.a00.fromString(paramString);
    }
  }
  
  public Currency getCurrentAvailCRSSurpressed()
  {
    return this.a1i;
  }
  
  public void setCurrentAvailCRSSurpressed(Currency paramCurrency)
  {
    this.a1i = paramCurrency;
  }
  
  public void setCurrentAvailCRSSurpressed(String paramString)
  {
    if (this.a1i == null) {
      this.a1i = new Currency(paramString, this.locale);
    } else {
      this.a1i.fromString(paramString);
    }
  }
  
  public Currency getCurrentAvail()
  {
    return this.a0S;
  }
  
  public void setCurrentAvail(Currency paramCurrency)
  {
    this.a0S = paramCurrency;
  }
  
  public void setCurrentAvail(String paramString)
  {
    if (this.a0S == null) {
      this.a0S = new Currency(paramString, this.locale);
    } else {
      this.a0S.fromString(paramString);
    }
  }
  
  public Currency getAvgCurrentAvailMTD()
  {
    return this.a03;
  }
  
  public void setAvgCurrentAvailMTD(Currency paramCurrency)
  {
    this.a03 = paramCurrency;
  }
  
  public void setAvgCurrentAvailMTD(String paramString)
  {
    if (this.a03 == null) {
      this.a03 = new Currency(paramString, this.locale);
    } else {
      this.a03.fromString(paramString);
    }
  }
  
  public Currency getAvgCurrentAvailYTD()
  {
    return this.a0H;
  }
  
  public void setAvgCurrentAvailYTD(Currency paramCurrency)
  {
    this.a0H = paramCurrency;
  }
  
  public void setAvgCurrentAvailYTD(String paramString)
  {
    if (this.a0H == null) {
      this.a0H = new Currency(paramString, this.locale);
    } else {
      this.a0H.fromString(paramString);
    }
  }
  
  public Currency getTotalFloat()
  {
    return this.a1d;
  }
  
  public void setTotalFloat(Currency paramCurrency)
  {
    this.a1d = paramCurrency;
  }
  
  public void setTotalFloat(String paramString)
  {
    if (this.a1d == null) {
      this.a1d = new Currency(paramString, this.locale);
    } else {
      this.a1d.fromString(paramString);
    }
  }
  
  public Currency getTargetBal()
  {
    return this.a1o;
  }
  
  public void setTargetBal(Currency paramCurrency)
  {
    this.a1o = paramCurrency;
  }
  
  public void setTargetBal(String paramString)
  {
    if (this.a1o == null) {
      this.a1o = new Currency(paramString, this.locale);
    } else {
      this.a1o.fromString(paramString);
    }
  }
  
  public Currency getAdjustedBal()
  {
    return this.a0I;
  }
  
  public void setAdjustedBal(Currency paramCurrency)
  {
    this.a0I = paramCurrency;
  }
  
  public void setAdjustedBal(String paramString)
  {
    if (this.a0I == null) {
      this.a0I = new Currency(paramString, this.locale);
    } else {
      this.a0I.fromString(paramString);
    }
  }
  
  public Currency getAdjustedBalMTD()
  {
    return this.a02;
  }
  
  public void setAdjustedBalMTD(Currency paramCurrency)
  {
    this.a02 = paramCurrency;
  }
  
  public void setAdjustedBalMTD(String paramString)
  {
    if (this.a02 == null) {
      this.a02 = new Currency(paramString, this.locale);
    } else {
      this.a02.fromString(paramString);
    }
  }
  
  public Currency getAdjustedBalYTD()
  {
    return this.a0G;
  }
  
  public void setAdjustedBalYTD(Currency paramCurrency)
  {
    this.a0G = paramCurrency;
  }
  
  public void setAdjustedBalYTD(String paramString)
  {
    if (this.a0G == null) {
      this.a0G = new Currency(paramString, this.locale);
    } else {
      this.a0G.fromString(paramString);
    }
  }
  
  public Currency getZeroDayFloat()
  {
    return this.a0W;
  }
  
  public void setZeroDayFloat(Currency paramCurrency)
  {
    this.a0W = paramCurrency;
  }
  
  public void setZeroDayFloat(String paramString)
  {
    if (this.a0W == null) {
      this.a0W = new Currency(paramString, this.locale);
    } else {
      this.a0W.fromString(paramString);
    }
  }
  
  public Currency getOneDayFloat()
  {
    return this.a0U;
  }
  
  public void setOneDayFloat(Currency paramCurrency)
  {
    this.a0U = paramCurrency;
  }
  
  public void setOneDayFloat(String paramString)
  {
    if (this.a0U == null) {
      this.a0U = new Currency(paramString, this.locale);
    } else {
      this.a0U.fromString(paramString);
    }
  }
  
  public Currency getFloatAdjusted()
  {
    return this.a0r;
  }
  
  public void setFloatAdjusted(Currency paramCurrency)
  {
    this.a0r = paramCurrency;
  }
  
  public void setFloatAdjusted(String paramString)
  {
    if (this.a0r == null) {
      this.a0r = new Currency(paramString, this.locale);
    } else {
      this.a0r.fromString(paramString);
    }
  }
  
  public Currency getTwoOrMoreDayFloat()
  {
    return this.a1h;
  }
  
  public void setTwoOrMoreDayFloat(Currency paramCurrency)
  {
    this.a1h = paramCurrency;
  }
  
  public void setTwoOrMoreDayFloat(String paramString)
  {
    if (this.a1h == null) {
      this.a1h = new Currency(paramString, this.locale);
    } else {
      this.a1h.fromString(paramString);
    }
  }
  
  public Currency getThreeOrMoreDayFloat()
  {
    return this.a0t;
  }
  
  public void setThreeOrMoreDayFloat(Currency paramCurrency)
  {
    this.a0t = paramCurrency;
  }
  
  public void setThreeOrMoreDayFloat(String paramString)
  {
    if (this.a0t == null) {
      this.a0t = new Currency(paramString, this.locale);
    } else {
      this.a0t.fromString(paramString);
    }
  }
  
  public Currency getAdjustmentToBal()
  {
    return this.a1c;
  }
  
  public void setAdjustmentToBal(Currency paramCurrency)
  {
    this.a1c = paramCurrency;
  }
  
  public void setAdjustmentToBal(String paramString)
  {
    if (this.a1c == null) {
      this.a1c = new Currency(paramString, this.locale);
    } else {
      this.a1c.fromString(paramString);
    }
  }
  
  public Currency getAvgAdjustmentToBalMTD()
  {
    return this.a0C;
  }
  
  public void setAvgAdjustmentToBalMTD(Currency paramCurrency)
  {
    this.a0C = paramCurrency;
  }
  
  public void setAvgAdjustmentToBalMTD(String paramString)
  {
    if (this.a0C == null) {
      this.a0C = new Currency(paramString, this.locale);
    } else {
      this.a0C.fromString(paramString);
    }
  }
  
  public Currency getAvgAdjustmentToBalYTD()
  {
    return this.a1b;
  }
  
  public void setAvgAdjustmentToBalYTD(Currency paramCurrency)
  {
    this.a1b = paramCurrency;
  }
  
  public void setAvgAdjustmentToBalYTD(String paramString)
  {
    if (this.a1b == null) {
      this.a1b = new Currency(paramString, this.locale);
    } else {
      this.a1b.fromString(paramString);
    }
  }
  
  public Currency getFourDayFloat()
  {
    return this.a04;
  }
  
  public void setFourDayFloat(Currency paramCurrency)
  {
    this.a04 = paramCurrency;
  }
  
  public void setFourDayFloat(String paramString)
  {
    if (this.a04 == null) {
      this.a04 = new Currency(paramString, this.locale);
    } else {
      this.a04.fromString(paramString);
    }
  }
  
  public Currency getFiveDayFloat()
  {
    return this.a07;
  }
  
  public void setFiveDayFloat(Currency paramCurrency)
  {
    this.a07 = paramCurrency;
  }
  
  public void setFiveDayFloat(String paramString)
  {
    if (this.a07 == null) {
      this.a07 = new Currency(paramString, this.locale);
    } else {
      this.a07.fromString(paramString);
    }
  }
  
  public Currency getSixDayFloat()
  {
    return this.a0E;
  }
  
  public void setSixDayFloat(Currency paramCurrency)
  {
    this.a0E = paramCurrency;
  }
  
  public void setSixDayFloat(String paramString)
  {
    if (this.a0E == null) {
      this.a0E = new Currency(paramString, this.locale);
    } else {
      this.a0E.fromString(paramString);
    }
  }
  
  public Currency getAvgOneDayFloatMTD()
  {
    return this.a0P;
  }
  
  public void setAvgOneDayFloatMTD(Currency paramCurrency)
  {
    this.a0P = paramCurrency;
  }
  
  public void setAvgOneDayFloatMTD(String paramString)
  {
    if (this.a0P == null) {
      this.a0P = new Currency(paramString, this.locale);
    } else {
      this.a0P.fromString(paramString);
    }
  }
  
  public Currency getAvgOneDayFloatYTD()
  {
    return this.a1j;
  }
  
  public void setAvgOneDayFloatYTD(Currency paramCurrency)
  {
    this.a1j = paramCurrency;
  }
  
  public void setAvgOneDayFloatYTD(String paramString)
  {
    if (this.a1j == null) {
      this.a1j = new Currency(paramString, this.locale);
    } else {
      this.a1j.fromString(paramString);
    }
  }
  
  public Currency getAvgTwoDayFloatMTD()
  {
    return this.a01;
  }
  
  public void setAvgTwoDayFloatMTD(Currency paramCurrency)
  {
    this.a01 = paramCurrency;
  }
  
  public void setAvgTwoDayFloatMTD(String paramString)
  {
    if (this.a01 == null) {
      this.a01 = new Currency(paramString, this.locale);
    } else {
      this.a01.fromString(paramString);
    }
  }
  
  public Currency getAvgTwoDayFloatYTD()
  {
    return this.a0F;
  }
  
  public void setAvgTwoDayFloatYTD(Currency paramCurrency)
  {
    this.a0F = paramCurrency;
  }
  
  public void setAvgTwoDayFloatYTD(String paramString)
  {
    if (this.a0F == null) {
      this.a0F = new Currency(paramString, this.locale);
    } else {
      this.a0F.fromString(paramString);
    }
  }
  
  public Currency getTransferCalculation()
  {
    return this.a0y;
  }
  
  public void setTransferCalculation(Currency paramCurrency)
  {
    this.a0y = paramCurrency;
  }
  
  public void setTransferCalculation(String paramString)
  {
    if (this.a0y == null) {
      this.a0y = new Currency(paramString, this.locale);
    } else {
      this.a0y.fromString(paramString);
    }
  }
  
  public Currency getTargetBalDeficiency()
  {
    return this.a0O;
  }
  
  public void setTargetBalDeficiency(Currency paramCurrency)
  {
    this.a0O = paramCurrency;
  }
  
  public void setTargetBalDeficiency(String paramString)
  {
    if (this.a0O == null) {
      this.a0O = new Currency(paramString, this.locale);
    } else {
      this.a0O.fromString(paramString);
    }
  }
  
  public Currency getTotalFundingRequirement()
  {
    return this.a1n;
  }
  
  public void setTotalFundingRequirement(Currency paramCurrency)
  {
    this.a1n = paramCurrency;
  }
  
  public void setTotalFundingRequirement(String paramString)
  {
    if (this.a1n == null) {
      this.a1n = new Currency(paramString, this.locale);
    } else {
      this.a1n.fromString(paramString);
    }
  }
  
  public Currency getTotalChecksPaid()
  {
    return this.a0Y;
  }
  
  public void setTotalChecksPaid(Currency paramCurrency)
  {
    this.a0Y = paramCurrency;
  }
  
  public void setTotalChecksPaid(String paramString)
  {
    if (this.a0Y == null) {
      this.a0Y = new Currency(paramString, this.locale);
    } else {
      this.a0Y.fromString(paramString);
    }
  }
  
  public int getNumChecksPaid()
  {
    return this.a06;
  }
  
  public void setNumChecksPaid(int paramInt)
  {
    this.a06 = paramInt;
  }
  
  public void setNumChecksPaid(String paramString)
  {
    try
    {
      this.a06 = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public Currency getGrandTotalCreditMinusDebit()
  {
    return this.a0v;
  }
  
  public void setGrandTotalCreditMinusDebit(Currency paramCurrency)
  {
    this.a0v = paramCurrency;
  }
  
  public void setGrandTotalCreditMinusDebit(String paramString)
  {
    if (this.a0v == null) {
      this.a0v = new Currency(paramString, this.locale);
    } else {
      this.a0v.fromString(paramString);
    }
  }
  
  public int getGrandNumCreditMinusDebit()
  {
    return this.a1g;
  }
  
  public void setGrandNumCreditMinusDebit(int paramInt)
  {
    this.a1g = paramInt;
  }
  
  public void setGrandNumCreditMinusDebit(String paramString)
  {
    try
    {
      this.a1g = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a0z != null) {
      this.a0z.setLocale(paramLocale);
    }
    if (this.a0B != null) {
      this.a0B.setLocale(paramLocale);
    }
    if (this.a0w != null) {
      this.a0w.setLocale(paramLocale);
    }
    if (this.a09 != null) {
      this.a09.setLocale(paramLocale);
    }
    if (this.a1a != null) {
      this.a1a.setLocale(paramLocale);
    }
    if (this.a0R != null) {
      this.a0R.setLocale(paramLocale);
    }
    if (this.a1f != null) {
      this.a1f.setLocale(paramLocale);
    }
    if (this.a1e != null) {
      this.a1e.setLocale(paramLocale);
    }
    if (this.a0u != null) {
      this.a0u.setLocale(paramLocale);
    }
    if (this.a1m != null) {
      this.a1m.setLocale(paramLocale);
    }
    if (this.a0V != null) {
      this.a0V.setLocale(paramLocale);
    }
    if (this.a05 != null) {
      this.a05.setLocale(paramLocale);
    }
    if (this.a0T != null) {
      this.a0T.setLocale(paramLocale);
    }
    if (this.a0N != null) {
      this.a0N.setLocale(paramLocale);
    }
    if (this.a0Q != null) {
      this.a0Q.setLocale(paramLocale);
    }
    if (this.a1k != null) {
      this.a1k.setLocale(paramLocale);
    }
    if (this.a0X != null) {
      this.a0X.setLocale(paramLocale);
    }
    if (this.a0A != null) {
      this.a0A.setLocale(paramLocale);
    }
    if (this.a0Z != null) {
      this.a0Z.setLocale(paramLocale);
    }
    if (this.a08 != null) {
      this.a08.setLocale(paramLocale);
    }
    if (this.a0s != null) {
      this.a0s.setLocale(paramLocale);
    }
    if (this.a0x != null) {
      this.a0x.setLocale(paramLocale);
    }
    if (this.a0M != null) {
      this.a0M.setLocale(paramLocale);
    }
    if (this.a0D != null) {
      this.a0D.setLocale(paramLocale);
    }
    if (this.a00 != null) {
      this.a00.setLocale(paramLocale);
    }
    if (this.a1i != null) {
      this.a1i.setLocale(paramLocale);
    }
    if (this.a0S != null) {
      this.a0S.setLocale(paramLocale);
    }
    if (this.a03 != null) {
      this.a03.setLocale(paramLocale);
    }
    if (this.a0H != null) {
      this.a0H.setLocale(paramLocale);
    }
    if (this.a1d != null) {
      this.a1d.setLocale(paramLocale);
    }
    if (this.a1o != null) {
      this.a1o.setLocale(paramLocale);
    }
    if (this.a0I != null) {
      this.a0I.setLocale(paramLocale);
    }
    if (this.a02 != null) {
      this.a02.setLocale(paramLocale);
    }
    if (this.a0G != null) {
      this.a0G.setLocale(paramLocale);
    }
    if (this.a0W != null) {
      this.a0W.setLocale(paramLocale);
    }
    if (this.a0U != null) {
      this.a0U.setLocale(paramLocale);
    }
    if (this.a0r != null) {
      this.a0r.setLocale(paramLocale);
    }
    if (this.a1h != null) {
      this.a1h.setLocale(paramLocale);
    }
    if (this.a0t != null) {
      this.a0t.setLocale(paramLocale);
    }
    if (this.a1c != null) {
      this.a1c.setLocale(paramLocale);
    }
    if (this.a0C != null) {
      this.a0C.setLocale(paramLocale);
    }
    if (this.a1b != null) {
      this.a1b.setLocale(paramLocale);
    }
    if (this.a04 != null) {
      this.a04.setLocale(paramLocale);
    }
    if (this.a07 != null) {
      this.a07.setLocale(paramLocale);
    }
    if (this.a0E != null) {
      this.a0E.setLocale(paramLocale);
    }
    if (this.a0P != null) {
      this.a0P.setLocale(paramLocale);
    }
    if (this.a1j != null) {
      this.a1j.setLocale(paramLocale);
    }
    if (this.a01 != null) {
      this.a01.setLocale(paramLocale);
    }
    if (this.a0F != null) {
      this.a0F.setLocale(paramLocale);
    }
    if (this.a0y != null) {
      this.a0y.setLocale(paramLocale);
    }
    if (this.a0O != null) {
      this.a0O.setLocale(paramLocale);
    }
    if (this.a1n != null) {
      this.a1n.setLocale(paramLocale);
    }
    if (this.a0Y != null) {
      this.a0Y.setLocale(paramLocale);
    }
    if (this.a0v != null) {
      this.a0v.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.a0z != null) {
      this.a0z.setFormat(paramString);
    }
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    AccountHistory localAccountHistory = (AccountHistory)paramObject;
    int i = 1;
    if (paramString.equals("ACCOUNT_ID")) {
      i = numStringCompare(this.a0J, localAccountHistory.getAccountID());
    } else if (paramString.equals("ACCOUNT_NUMBER")) {
      i = numStringCompare(this.a1l, localAccountHistory.getAccountNumber());
    } else if (paramString.equals("ROUTING_NUMBER")) {
      i = numStringCompare(this.a0K, localAccountHistory.getRoutingNumber());
    } else if ((paramString.equals("BANK_ID")) && (this.a0L != null) && (localAccountHistory.getBankID() != null)) {
      i = localCollator.compare(this.a0L, localAccountHistory.getBankID());
    } else if ((paramString.equals("HISTORY_DATE")) && (this.a0z != null) && (localAccountHistory.getHistoryDate() != null)) {
      i = this.a0z.compare(localAccountHistory.getHistoryDate(), paramString);
    } else if ((paramString.equals("OPENING_LEDGER")) && (this.a0B != null) && (localAccountHistory.getOpeningLedger() != null)) {
      i = this.a0B.compareTo(localAccountHistory.getOpeningLedger());
    } else if ((paramString.equals("AVG_OPENING_LEDGER_MTD")) && (this.a0w != null) && (localAccountHistory.getAvgOpeningLedgerMTD() != null)) {
      i = this.a0w.compareTo(localAccountHistory.getAvgOpeningLedgerMTD());
    } else if ((paramString.equals("AVG_OPENING_LEDGER_YTD")) && (this.a09 != null) && (localAccountHistory.getAvgOpeningLedgerYTD() != null)) {
      i = this.a09.compareTo(localAccountHistory.getAvgOpeningLedgerYTD());
    } else if ((paramString.equals("CLOSING_LEDGER")) && (this.a1a != null) && (localAccountHistory.getClosingLedger() != null)) {
      i = this.a1a.compareTo(localAccountHistory.getClosingLedger());
    } else if ((paramString.equals("AVG_CLOSING_LEDGER_MTD")) && (this.a0R != null) && (localAccountHistory.getAvgClosingLedgerMTD() != null)) {
      i = this.a0R.compareTo(localAccountHistory.getAvgClosingLedgerMTD());
    } else if ((paramString.equals("AVG_MONTH")) && (this.a1f != null) && (localAccountHistory.getAvgMonth() != null)) {
      i = this.a1f.compareTo(localAccountHistory.getAvgMonth());
    } else if ((paramString.equals("AGGREGATE_BAL_ADJUSTMENT")) && (this.a1e != null) && (localAccountHistory.getAggregateBalAdjustment() != null)) {
      i = this.a1e.compareTo(localAccountHistory.getAggregateBalAdjustment());
    } else if ((paramString.equals("AVG_CLOSING_LEDGER_YTD_PREV_MONTH")) && (this.a0u != null) && (localAccountHistory.getAvgClosingLedgerYTDPrevMonth() != null)) {
      i = this.a0u.compareTo(localAccountHistory.getAvgClosingLedgerYTDPrevMonth());
    } else if ((paramString.equals("AVG_CLOSING_LEDGER_YTD")) && (this.a1m != null) && (localAccountHistory.getAvgClosingLedgerYTD() != null)) {
      i = this.a1m.compareTo(localAccountHistory.getAvgClosingLedgerYTD());
    } else if ((paramString.equals("CURRENT_LEDGER")) && (this.a0V != null) && (localAccountHistory.getCurrentLedger() != null)) {
      i = this.a0V.compareTo(localAccountHistory.getCurrentLedger());
    } else if ((paramString.equals("NET_POSITION_ACH")) && (this.a05 != null) && (localAccountHistory.getNetPositionACH() != null)) {
      i = this.a05.compareTo(localAccountHistory.getNetPositionACH());
    } else if ((paramString.equals("OPEN_AVAIL_SAME_DAY_ACH_DTC")) && (this.a0T != null) && (localAccountHistory.getOpenAvailSameDayACHDTC() != null)) {
      i = this.a0T.compareTo(localAccountHistory.getOpenAvailSameDayACHDTC());
    } else if ((paramString.equals("OPENING_AVAIL")) && (this.a0N != null) && (localAccountHistory.getOpeningAvail() != null)) {
      i = this.a0N.compareTo(localAccountHistory.getOpeningAvail());
    } else if ((paramString.equals("AVG_OPEN_AVAIL_MTD")) && (this.a0Q != null) && (localAccountHistory.getAvgOpenAvailMTD() != null)) {
      i = this.a0Q.compareTo(localAccountHistory.getAvgOpenAvailMTD());
    } else if ((paramString.equals("AVG_OPEN_AVAIL_YTD")) && (this.a1k != null) && (localAccountHistory.getAvgOpenAvailYTD() != null)) {
      i = this.a1k.compareTo(localAccountHistory.getAvgOpenAvailYTD());
    } else if ((paramString.equals("AVG_AVAIL_PREV_MONTH")) && (this.a0X != null) && (localAccountHistory.getAvgAvailPrevMonth() != null)) {
      i = this.a0X.compareTo(localAccountHistory.getAvgAvailPrevMonth());
    } else if ((paramString.equals("DISBURSING_OPENING_AVAIL_BAL")) && (this.a0A != null) && (localAccountHistory.getDisbursingOpeningAvailBal() != null)) {
      i = this.a0A.compareTo(localAccountHistory.getDisbursingOpeningAvailBal());
    } else if ((paramString.equals("CLOSING_AVAIL")) && (this.a0Z != null) && (localAccountHistory.getClosingAvail() != null)) {
      i = this.a0Z.compareTo(localAccountHistory.getClosingAvail());
    } else if ((paramString.equals("AVG_CLOSING_AVAIL_MTD")) && (this.a08 != null) && (localAccountHistory.getAvgClosingAvailMTD() != null)) {
      i = this.a08.compareTo(localAccountHistory.getAvgClosingAvailMTD());
    } else if ((paramString.equals("AVG_CLOSING_AVAIL_PREV_MONTH")) && (this.a0s != null) && (localAccountHistory.getAvgClosingAvailPrevMonth() != null)) {
      i = this.a0s.compareTo(localAccountHistory.getAvgClosingAvailPrevMonth());
    } else if ((paramString.equals("AVG_CLOSING_AVAIL_YTD_PREV_MONTH")) && (this.a0x != null) && (localAccountHistory.getAvgClosingAvailYTDPrevMonth() != null)) {
      i = this.a0x.compareTo(localAccountHistory.getAvgClosingAvailYTDPrevMonth());
    } else if ((paramString.equals("AVG_CLOSING_AVAIL_YTD")) && (this.a0M != null) && (localAccountHistory.getAvgClosingAvailYTD() != null)) {
      i = this.a0M.compareTo(localAccountHistory.getAvgClosingAvailYTD());
    } else if ((paramString.equals("LOAN_BAL")) && (this.a0D != null) && (localAccountHistory.getLoanBal() != null)) {
      i = this.a0D.compareTo(localAccountHistory.getLoanBal());
    } else if ((paramString.equals("TOTAL_INVESTMENT_POSITION")) && (this.a00 != null) && (localAccountHistory.getTotalInvestmentPosition() != null)) {
      i = this.a00.compareTo(localAccountHistory.getTotalInvestmentPosition());
    } else if ((paramString.equals("CURRENT_AVAIL_CRS_SURPRESSED")) && (this.a1i != null) && (localAccountHistory.getCurrentAvailCRSSurpressed() != null)) {
      i = this.a1i.compareTo(localAccountHistory.getCurrentAvailCRSSurpressed());
    } else if ((paramString.equals("CURRENT_AVAIL")) && (this.a0S != null) && (localAccountHistory.getCurrentAvail() != null)) {
      i = this.a0S.compareTo(localAccountHistory.getCurrentAvail());
    } else if ((paramString.equals("AVG_CURRENT_AVAIL_MTD")) && (this.a03 != null) && (localAccountHistory.getAvgCurrentAvailMTD() != null)) {
      i = this.a03.compareTo(localAccountHistory.getAvgCurrentAvailMTD());
    } else if ((paramString.equals("AVG_CURRENT_AVAIL_YTD")) && (this.a0H != null) && (localAccountHistory.getAvgCurrentAvailYTD() != null)) {
      i = this.a0H.compareTo(localAccountHistory.getAvgCurrentAvailYTD());
    } else if ((paramString.equals("TOTAL_FLOAT")) && (this.a1d != null) && (localAccountHistory.getTotalFloat() != null)) {
      i = this.a1d.compareTo(localAccountHistory.getTotalFloat());
    } else if ((paramString.equals("TARGET_BAL")) && (this.a1o != null) && (localAccountHistory.getTargetBal() != null)) {
      i = this.a1o.compareTo(localAccountHistory.getTargetBal());
    } else if ((paramString.equals("ADJUSTED_BAL")) && (this.a0I != null) && (localAccountHistory.getAdjustedBal() != null)) {
      i = this.a0I.compareTo(localAccountHistory.getAdjustedBal());
    } else if ((paramString.equals("ADJUSTED_BAL_MTD")) && (this.a02 != null) && (localAccountHistory.getAdjustedBalMTD() != null)) {
      i = this.a02.compareTo(localAccountHistory.getAdjustedBalMTD());
    } else if ((paramString.equals("ADJUSTED_BAL_YTD")) && (this.a0G != null) && (localAccountHistory.getAdjustedBalYTD() != null)) {
      i = this.a0G.compareTo(localAccountHistory.getAdjustedBalYTD());
    } else if ((paramString.equals("ZERO_DAY_FLOAT")) && (this.a0W != null) && (localAccountHistory.getZeroDayFloat() != null)) {
      i = this.a0W.compareTo(localAccountHistory.getZeroDayFloat());
    } else if ((paramString.equals("ONE_DAY_FLOAT")) && (this.a0U != null) && (localAccountHistory.getOneDayFloat() != null)) {
      i = this.a0U.compareTo(localAccountHistory.getOneDayFloat());
    } else if ((paramString.equals("FLOAT_ADJUSTMENT")) && (this.a0r != null) && (localAccountHistory.getFloatAdjusted() != null)) {
      i = this.a0r.compareTo(localAccountHistory.getFloatAdjusted());
    } else if ((paramString.equals("TWO_OR_MORE_DAY_FLOAT")) && (this.a1h != null) && (localAccountHistory.getTwoOrMoreDayFloat() != null)) {
      i = this.a1h.compareTo(localAccountHistory.getTwoOrMoreDayFloat());
    } else if ((paramString.equals("THREE_OR_MORE_DAY_FLOAT")) && (this.a0t != null) && (localAccountHistory.getThreeOrMoreDayFloat() != null)) {
      i = this.a0t.compareTo(localAccountHistory.getThreeOrMoreDayFloat());
    } else if ((paramString.equals("ADJUSTMENT_TO_BAL")) && (this.a1c != null) && (localAccountHistory.getAdjustmentToBal() != null)) {
      i = this.a1c.compareTo(localAccountHistory.getAdjustmentToBal());
    } else if ((paramString.equals("AVG_ADJUSTMENT_TO_BAL_MTD")) && (this.a0C != null) && (localAccountHistory.getAvgAdjustmentToBalMTD() != null)) {
      i = this.a0C.compareTo(localAccountHistory.getAvgAdjustmentToBalMTD());
    } else if ((paramString.equals("AVG_ADJUSTMENT_TO_BAL_YTD")) && (this.a1b != null) && (localAccountHistory.getAvgAdjustmentToBalYTD() != null)) {
      i = this.a1b.compareTo(localAccountHistory.getAvgAdjustmentToBalYTD());
    } else if ((paramString.equals("FOUR_DAY_FLOAT")) && (this.a04 != null) && (localAccountHistory.getFourDayFloat() != null)) {
      i = this.a04.compareTo(localAccountHistory.getFourDayFloat());
    } else if ((paramString.equals("FIVE_DAY_FLOAT")) && (this.a07 != null) && (localAccountHistory.getFiveDayFloat() != null)) {
      i = this.a07.compareTo(localAccountHistory.getFiveDayFloat());
    } else if ((paramString.equals("SIX_DAY_FLOAT")) && (this.a0E != null) && (localAccountHistory.getSixDayFloat() != null)) {
      i = this.a0E.compareTo(localAccountHistory.getSixDayFloat());
    } else if ((paramString.equals("AVG_ONE_DAY_FLOAT_MTD")) && (this.a0P != null) && (localAccountHistory.getAvgOneDayFloatMTD() != null)) {
      i = this.a0P.compareTo(localAccountHistory.getAvgOneDayFloatMTD());
    } else if ((paramString.equals("AVG_ONE_DAY_FLOAT_YTD")) && (this.a1j != null) && (localAccountHistory.getAvgOneDayFloatYTD() != null)) {
      i = this.a1j.compareTo(localAccountHistory.getAvgOneDayFloatYTD());
    } else if ((paramString.equals("AVG_TWO_DAY_FLOAT_MTD")) && (this.a01 != null) && (localAccountHistory.getAvgTwoDayFloatMTD() != null)) {
      i = this.a01.compareTo(localAccountHistory.getAvgTwoDayFloatMTD());
    } else if ((paramString.equals("AVG_TWO_DAY_FLOAT_YTD")) && (this.a0F != null) && (localAccountHistory.getAvgTwoDayFloatYTD() != null)) {
      i = this.a0F.compareTo(localAccountHistory.getAvgTwoDayFloatYTD());
    } else if ((paramString.equals("TRANSFER_CALCULATION")) && (this.a0y != null) && (localAccountHistory.getTransferCalculation() != null)) {
      i = this.a0y.compareTo(localAccountHistory.getTransferCalculation());
    } else if ((paramString.equals("TARGET_BALDEFICIENCY")) && (this.a0O != null) && (localAccountHistory.getTargetBalDeficiency() != null)) {
      i = this.a0O.compareTo(localAccountHistory.getTargetBalDeficiency());
    } else if ((paramString.equals("TOTAL_FUNDING_REQUIREMENT")) && (this.a1n != null) && (localAccountHistory.getTotalFundingRequirement() != null)) {
      i = this.a1n.compareTo(localAccountHistory.getTotalFundingRequirement());
    } else if ((paramString.equals("TOTAL_CHECKS_PAID")) && (this.a0Y != null) && (localAccountHistory.getTotalChecksPaid() != null)) {
      i = this.a0Y.compareTo(localAccountHistory.getTotalChecksPaid());
    } else if (paramString.equals("NUM_CHECKS_PAID")) {
      i = this.a06 == localAccountHistory.getNumChecksPaid() ? 0 : this.a06 < localAccountHistory.getNumChecksPaid() ? -1 : 1;
    } else if ((paramString.equals("GRAND_TOTAL_CREDITS_MINUS_GRAND_TOTAL_DEBITS")) && (this.a0v != null) && (localAccountHistory.getGrandTotalCreditMinusDebit() != null)) {
      i = this.a0v.compareTo(localAccountHistory.getGrandTotalCreditMinusDebit());
    } else if (paramString.equals("GRAND_NUM_CREDITS_MINUS_GRAND_NUM_DEBITS")) {
      i = this.a1g == localAccountHistory.getGrandNumCreditMinusDebit() ? 0 : this.a1g < localAccountHistory.getGrandNumCreditMinusDebit() ? -1 : 1;
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ACCOUNT_ID")) && (this.a0J != null)) {
      return isFilterable(this.a0J, paramString2, paramString3);
    }
    if ((paramString1.equals("ACCOUNT_NUMBER")) && (this.a1l != null)) {
      return isFilterable(this.a1l, paramString2, paramString3);
    }
    if ((paramString1.equals("ROUTING_NUMBER")) && (this.a0K != null)) {
      return isFilterable(this.a0K, paramString2, paramString3);
    }
    if ((paramString1.equals("BANK_ID")) && (this.a0L != null)) {
      return isFilterable(this.a0L, paramString2, paramString3);
    }
    if ((paramString1.equals("HISTORY_DATE")) && (this.a0z != null)) {
      return this.a0z.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("OPENING_LEDGER")) && (this.a0B != null)) {
      return this.a0B.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_OPENING_LEDGER_MTD")) && (this.a0w != null)) {
      return this.a0w.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_OPENING_LEDGER_YTD")) && (this.a09 != null)) {
      return this.a09.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("CLOSING_LEDGER")) && (this.a1a != null)) {
      return this.a1a.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_CLOSING_LEDGER_MTD")) && (this.a0R != null)) {
      return this.a0R.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_MONTH")) && (this.a1f != null)) {
      return this.a1f.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AGGREGATE_BAL_ADJUSTMENT")) && (this.a1e != null)) {
      return this.a1e.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_CLOSING_LEDGER_YTD_PREV_MONTH")) && (this.a0u != null)) {
      return this.a0u.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_CLOSING_LEDGER_YTD")) && (this.a1m != null)) {
      return this.a1m.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("CURRENT_LEDGER")) && (this.a0V != null)) {
      return this.a0V.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("NET_POSITION_ACH")) && (this.a05 != null)) {
      return this.a05.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("OPEN_AVAIL_SAME_DAY_ACH_DTC")) && (this.a0T != null)) {
      return this.a0T.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("OPENING_AVAIL")) && (this.a0N != null)) {
      return this.a0N.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_OPEN_AVAIL_MTD")) && (this.a0Q != null)) {
      return this.a0Q.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_OPEN_AVAIL_YTD")) && (this.a1k != null)) {
      return this.a1k.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_AVAIL_PREV_MONTH")) && (this.a0X != null)) {
      return this.a0X.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("DISBURSING_OPENING_AVAIL_BAL")) && (this.a0A != null)) {
      return this.a0A.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("CLOSING_AVAIL")) && (this.a0Z != null)) {
      return this.a0Z.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_CLOSING_AVAIL_MTD")) && (this.a08 != null)) {
      return this.a08.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_CLOSING_AVAIL_PREV_MONTH")) && (this.a0s != null)) {
      return this.a0s.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_CLOSING_AVAIL_YTD_PREV_MONTH")) && (this.a0x != null)) {
      return this.a0x.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_CLOSING_AVAIL_YTD")) && (this.a0M != null)) {
      return this.a0M.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("LOAN_BAL")) && (this.a0D != null)) {
      return this.a0D.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TOTAL_INVESTMENT_POSITION")) && (this.a00 != null)) {
      return this.a00.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("CURRENT_AVAIL_CRS_SURPRESSED")) && (this.a1i != null)) {
      return this.a1i.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("CURRENT_AVAIL")) && (this.a0S != null)) {
      return this.a0S.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_CURRENT_AVAIL_MTD")) && (this.a03 != null)) {
      return this.a03.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_CURRENT_AVAIL_YTD")) && (this.a0H != null)) {
      return this.a0H.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TOTAL_FLOAT")) && (this.a1d != null)) {
      return this.a1d.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TARGET_BAL")) && (this.a1o != null)) {
      return this.a1o.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("ADJUSTED_BAL")) && (this.a0I != null)) {
      return this.a0I.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("ADJUSTED_BAL_MTD")) && (this.a02 != null)) {
      return this.a02.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("ADJUSTED_BAL_YTD")) && (this.a0G != null)) {
      return this.a0G.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("ZERO_DAY_FLOAT")) && (this.a0W != null)) {
      return this.a0W.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("ONE_DAY_FLOAT")) && (this.a0U != null)) {
      return this.a0U.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("FLOAT_ADJUSTMENT")) && (this.a0r != null)) {
      return this.a0r.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TWO_OR_MORE_DAY_FLOAT")) && (this.a1h != null)) {
      return this.a1h.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("THREE_OR_MORE_DAY_FLOAT")) && (this.a0t != null)) {
      return this.a0t.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("ADJUSTMENT_TO_BAL")) && (this.a1c != null)) {
      return this.a1c.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_ADJUSTMENT_TO_BAL_MTD")) && (this.a0C != null)) {
      return this.a0C.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_ADJUSTMENT_TO_BAL_YTD")) && (this.a1b != null)) {
      return this.a1b.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("FOUR_DAY_FLOAT")) && (this.a04 != null)) {
      return this.a04.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("FIVE_DAY_FLOAT")) && (this.a07 != null)) {
      return this.a07.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("SIX_DAY_FLOAT")) && (this.a0E != null)) {
      return this.a0E.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_ONE_DAY_FLOAT_MTD")) && (this.a0P != null)) {
      return this.a0P.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_ONE_DAY_FLOAT_YTD")) && (this.a1j != null)) {
      return this.a1j.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_TWO_DAY_FLOAT_MTD")) && (this.a01 != null)) {
      return this.a01.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVG_TWO_DAY_FLOAT_YTD")) && (this.a0F != null)) {
      return this.a0F.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TRANSFER_CALCULATION")) && (this.a0y != null)) {
      return this.a0y.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TARGET_BALDEFICIENCY")) && (this.a0O != null)) {
      return this.a0O.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TOTAL_FUNDING_REQUIREMENT")) && (this.a1n != null)) {
      return this.a1n.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TOTAL_CHECKS_PAID")) && (this.a0Y != null)) {
      return this.a0Y.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if (paramString1.equals("NUM_CHECKS_PAID")) {
      return isFilterable(Integer.toString(this.a06), paramString2, paramString3);
    }
    if ((paramString1.equals("GRAND_TOTAL_CREDITS_MINUS_GRAND_TOTAL_DEBITS")) && (this.a0v != null)) {
      return this.a0v.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if (paramString1.equals("GRAND_NUM_CREDITS_MINUS_GRAND_NUM_DEBITS")) {
      return isFilterable(Integer.toString(this.a1g), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ACCOUNT_ID"))
    {
      this.a0J = paramString2;
    }
    else if (paramString1.equals("ACCOUNT_NUMBER"))
    {
      this.a1l = paramString2;
    }
    else if (paramString1.equals("ROUTING_NUMBER"))
    {
      this.a0K = paramString2;
    }
    else if (paramString1.equals("BANK_ID"))
    {
      this.a0L = paramString2;
    }
    else if (paramString1.equals("HISTORY_DATE"))
    {
      if (this.a0z == null)
      {
        this.a0z = new DateTime(this.locale);
        this.a0z.setFormat(this.datetype);
      }
      this.a0z.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("OPENING_LEDGER"))
    {
      setOpeningLedger(paramString2);
    }
    else if (paramString1.equals("AVG_OPENING_LEDGER_MTD"))
    {
      setAvgOpeningLedgerMTD(paramString2);
    }
    else if (paramString1.equals("AVG_OPENING_LEDGER_YTD"))
    {
      setAvgOpeningLedgerYTD(paramString2);
    }
    else if (paramString1.equals("CLOSING_LEDGER"))
    {
      setClosingLedger(paramString2);
    }
    else if (paramString1.equals("AVG_CLOSING_LEDGER_MTD"))
    {
      setAvgClosingLedgerMTD(paramString2);
    }
    else if (paramString1.equals("AVG_MONTH"))
    {
      setAvgMonth(paramString2);
    }
    else if (paramString1.equals("AGGREGATE_BAL_ADJUSTMENT"))
    {
      setAggregateBalAdjustment(paramString2);
    }
    else if (paramString1.equals("AVG_CLOSING_LEDGER_YTD_PREV_MONTH"))
    {
      setAvgClosingLedgerYTDPrevMonth(paramString2);
    }
    else if (paramString1.equals("AVG_CLOSING_LEDGER_YTD"))
    {
      setAvgClosingLedgerYTD(paramString2);
    }
    else if (paramString1.equals("CURRENT_LEDGER"))
    {
      setCurrentLedger(paramString2);
    }
    else if (paramString1.equals("NET_POSITION_ACH"))
    {
      setNetPositionACH(paramString2);
    }
    else if (paramString1.equals("OPEN_AVAIL_SAME_DAY_ACH_DTC"))
    {
      setOpenAvailSameDayACHDTC(paramString2);
    }
    else if (paramString1.equals("OPENING_AVAIL"))
    {
      setOpeningAvail(paramString2);
    }
    else if (paramString1.equals("AVG_OPEN_AVAIL_MTD"))
    {
      setAvgOpenAvailMTD(paramString2);
    }
    else if (paramString1.equals("AVG_OPEN_AVAIL_YTD"))
    {
      setAvgOpenAvailYTD(paramString2);
    }
    else if (paramString1.equals("AVG_AVAIL_PREV_MONTH"))
    {
      setAvgAvailPrevMonth(paramString2);
    }
    else if (paramString1.equals("DISBURSING_OPENING_AVAIL_BAL"))
    {
      setDisbursingOpeningAvailBal(paramString2);
    }
    else if (paramString1.equals("CLOSING_AVAIL"))
    {
      setClosingAvail(paramString2);
    }
    else if (paramString1.equals("AVG_CLOSING_AVAIL_MTD"))
    {
      setAvgClosingAvailMTD(paramString2);
    }
    else if (paramString1.equals("AVG_CLOSING_AVAIL_PREV_MONTH"))
    {
      setAvgClosingAvailPrevMonth(paramString2);
    }
    else if (paramString1.equals("AVG_CLOSING_AVAIL_YTD_PREV_MONTH"))
    {
      setAvgClosingAvailYTDPrevMonth(paramString2);
    }
    else if (paramString1.equals("AVG_CLOSING_AVAIL_YTD"))
    {
      setAvgClosingAvailYTD(paramString2);
    }
    else if (paramString1.equals("LOAN_BAL"))
    {
      setLoanBal(paramString2);
    }
    else if (paramString1.equals("TOTAL_INVESTMENT_POSITION"))
    {
      setTotalInvestmentPosition(paramString2);
    }
    else if (paramString1.equals("CURRENT_AVAIL_CRS_SURPRESSED"))
    {
      setCurrentAvailCRSSurpressed(paramString2);
    }
    else if (paramString1.equals("CURRENT_AVAIL"))
    {
      setCurrentAvail(paramString2);
    }
    else if (paramString1.equals("AVG_CURRENT_AVAIL_MTD"))
    {
      setAvgCurrentAvailMTD(paramString2);
    }
    else if (paramString1.equals("AVG_CURRENT_AVAIL_YTD"))
    {
      setAvgCurrentAvailYTD(paramString2);
    }
    else if (paramString1.equals("TOTAL_FLOAT"))
    {
      setTotalFloat(paramString2);
    }
    else if (paramString1.equals("TARGET_BAL"))
    {
      setTargetBal(paramString2);
    }
    else if (paramString1.equals("ADJUSTED_BAL"))
    {
      setAdjustedBal(paramString2);
    }
    else if (paramString1.equals("ADJUSTED_BAL_MTD"))
    {
      setAdjustedBalMTD(paramString2);
    }
    else if (paramString1.equals("ADJUSTED_BAL_YTD"))
    {
      setAdjustedBalYTD(paramString2);
    }
    else if (paramString1.equals("ZERO_DAY_FLOAT"))
    {
      setZeroDayFloat(paramString2);
    }
    else if (paramString1.equals("ONE_DAY_FLOAT"))
    {
      setOneDayFloat(paramString2);
    }
    else if (paramString1.equals("FLOAT_ADJUSTMENT"))
    {
      setFloatAdjusted(paramString2);
    }
    else if (paramString1.equals("TWO_OR_MORE_DAY_FLOAT"))
    {
      setTwoOrMoreDayFloat(paramString2);
    }
    else if (paramString1.equals("THREE_OR_MORE_DAY_FLOAT"))
    {
      setThreeOrMoreDayFloat(paramString2);
    }
    else if (paramString1.equals("ADJUSTMENT_TO_BAL"))
    {
      setAdjustmentToBal(paramString2);
    }
    else if (paramString1.equals("AVG_ADJUSTMENT_TO_BAL_MTD"))
    {
      setAvgAdjustmentToBalMTD(paramString2);
    }
    else if (paramString1.equals("AVG_ADJUSTMENT_TO_BAL_YTD"))
    {
      setAvgAdjustmentToBalYTD(paramString2);
    }
    else if (paramString1.equals("FOUR_DAY_FLOAT"))
    {
      setFourDayFloat(paramString2);
    }
    else if (paramString1.equals("FIVE_DAY_FLOAT"))
    {
      setFiveDayFloat(paramString2);
    }
    else if (paramString1.equals("SIX_DAY_FLOAT"))
    {
      setSixDayFloat(paramString2);
    }
    else if (paramString1.equals("AVG_ONE_DAY_FLOAT_MTD"))
    {
      setAvgOneDayFloatMTD(paramString2);
    }
    else if (paramString1.equals("AVG_ONE_DAY_FLOAT_YTD"))
    {
      setAvgOneDayFloatYTD(paramString2);
    }
    else if (paramString1.equals("AVG_TWO_DAY_FLOAT_MTD"))
    {
      setAvgTwoDayFloatMTD(paramString2);
    }
    else if (paramString1.equals("AVG_TWO_DAY_FLOAT_YTD"))
    {
      setAvgTwoDayFloatYTD(paramString2);
    }
    else if (paramString1.equals("TRANSFER_CALCULATION"))
    {
      setTransferCalculation(paramString2);
    }
    else if (paramString1.equals("TARGET_BALDEFICIENCY"))
    {
      setTargetBalDeficiency(paramString2);
    }
    else if (paramString1.equals("TOTAL_FUNDING_REQUIREMENT"))
    {
      setTotalFundingRequirement(paramString2);
    }
    else if (paramString1.equals("TOTAL_CHECKS_PAID"))
    {
      setTotalChecksPaid(paramString2);
    }
    else if (paramString1.equals("NUM_CHECKS_PAID"))
    {
      setNumChecksPaid(paramString2);
    }
    else if (paramString1.equals("GRAND_TOTAL_CREDITS_MINUS_GRAND_TOTAL_DEBITS"))
    {
      setGrandTotalCreditMinusDebit(paramString2);
    }
    else if (paramString1.equals("GRAND_NUM_CREDITS_MINUS_GRAND_NUM_DEBITS"))
    {
      setGrandNumCreditMinusDebit(paramString2);
    }
    else
    {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACCOUNT_HISTORY");
    XMLHandler.appendTag(localStringBuffer, "ACCOUNT_ID", this.a0J);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNT_NUMBER", this.a1l);
    if (this.a0K != null) {
      XMLHandler.appendTag(localStringBuffer, "ROUTING_NUMBER", this.a0K);
    }
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", this.a0L);
    if (this.a0z != null) {
      XMLHandler.appendTag(localStringBuffer, "HISTORY_DATE", this.a0z.toXMLFormat());
    }
    if (this.a0B != null) {
      XMLHandler.appendTag(localStringBuffer, "OPENING_LEDGER", this.a0B.doubleValue());
    }
    if (this.a0w != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_OPENING_LEDGER_MTD", this.a0w.doubleValue());
    }
    if (this.a09 != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_OPENING_LEDGER_YTD", this.a09.doubleValue());
    }
    if (this.a1a != null) {
      XMLHandler.appendTag(localStringBuffer, "CLOSING_LEDGER", this.a1a.doubleValue());
    }
    if (this.a0R != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_CLOSING_LEDGER_MTD", this.a0R.doubleValue());
    }
    if (this.a1f != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_MONTH", this.a1f.doubleValue());
    }
    if (this.a1e != null) {
      XMLHandler.appendTag(localStringBuffer, "AGGREGATE_BAL_ADJUSTMENT", this.a1e.doubleValue());
    }
    if (this.a0u != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_CLOSING_LEDGER_YTD_PREV_MONTH", this.a0u.doubleValue());
    }
    if (this.a1m != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_CLOSING_LEDGER_YTD", this.a1m.doubleValue());
    }
    if (this.a0V != null) {
      XMLHandler.appendTag(localStringBuffer, "CURRENT_LEDGER", this.a0V.doubleValue());
    }
    if (this.a05 != null) {
      XMLHandler.appendTag(localStringBuffer, "NET_POSITION_ACH", this.a05.doubleValue());
    }
    if (this.a0T != null) {
      XMLHandler.appendTag(localStringBuffer, "OPEN_AVAIL_SAME_DAY_ACH_DTC", this.a0T.doubleValue());
    }
    if (this.a0N != null) {
      XMLHandler.appendTag(localStringBuffer, "OPENING_AVAIL", this.a0N.doubleValue());
    }
    if (this.a0Q != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_OPEN_AVAIL_MTD", this.a0Q.doubleValue());
    }
    if (this.a1k != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_OPEN_AVAIL_YTD", this.a1k.doubleValue());
    }
    if (this.a0X != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_AVAIL_PREV_MONTH", this.a0X.doubleValue());
    }
    if (this.a0A != null) {
      XMLHandler.appendTag(localStringBuffer, "DISBURSING_OPENING_AVAIL_BAL", this.a0A.doubleValue());
    }
    if (this.a0Z != null) {
      XMLHandler.appendTag(localStringBuffer, "CLOSING_AVAIL", this.a0Z.doubleValue());
    }
    if (this.a08 != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_CLOSING_AVAIL_MTD", this.a08.doubleValue());
    }
    if (this.a0s != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_CLOSING_AVAIL_PREV_MONTH", this.a0s.doubleValue());
    }
    if (this.a0x != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_CLOSING_AVAIL_YTD_PREV_MONTH", this.a0x.doubleValue());
    }
    if (this.a0M != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_CLOSING_AVAIL_YTD", this.a0M.doubleValue());
    }
    if (this.a0D != null) {
      XMLHandler.appendTag(localStringBuffer, "LOAN_BAL", this.a0D.doubleValue());
    }
    if (this.a00 != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_INVESTMENT_POSITION", this.a00.doubleValue());
    }
    if (this.a1i != null) {
      XMLHandler.appendTag(localStringBuffer, "CURRENT_AVAIL_CRS_SURPRESSED", this.a1i.doubleValue());
    }
    if (this.a0S != null) {
      XMLHandler.appendTag(localStringBuffer, "CURRENT_AVAIL", this.a0S.doubleValue());
    }
    if (this.a03 != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_CURRENT_AVAIL_MTD", this.a03.doubleValue());
    }
    if (this.a0H != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_CURRENT_AVAIL_YTD", this.a0H.doubleValue());
    }
    if (this.a1d != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_FLOAT", this.a1d.doubleValue());
    }
    if (this.a1o != null) {
      XMLHandler.appendTag(localStringBuffer, "TARGET_BAL", this.a1o.doubleValue());
    }
    if (this.a0I != null) {
      XMLHandler.appendTag(localStringBuffer, "ADJUSTED_BAL", this.a0I.doubleValue());
    }
    if (this.a02 != null) {
      XMLHandler.appendTag(localStringBuffer, "ADJUSTED_BAL_MTD", this.a02.doubleValue());
    }
    if (this.a0G != null) {
      XMLHandler.appendTag(localStringBuffer, "ADJUSTED_BAL_YTD", this.a0G.doubleValue());
    }
    if (this.a0W != null) {
      XMLHandler.appendTag(localStringBuffer, "ZERO_DAY_FLOAT", this.a0W.doubleValue());
    }
    if (this.a0U != null) {
      XMLHandler.appendTag(localStringBuffer, "ONE_DAY_FLOAT", this.a0U.doubleValue());
    }
    if (this.a0r != null) {
      XMLHandler.appendTag(localStringBuffer, "FLOAT_ADJUSTMENT", this.a0r.doubleValue());
    }
    if (this.a1h != null) {
      XMLHandler.appendTag(localStringBuffer, "TWO_OR_MORE_DAY_FLOAT", this.a1h.doubleValue());
    }
    if (this.a0t != null) {
      XMLHandler.appendTag(localStringBuffer, "THREE_OR_MORE_DAY_FLOAT", this.a0t.doubleValue());
    }
    if (this.a1c != null) {
      XMLHandler.appendTag(localStringBuffer, "ADJUSTMENT_TO_BAL", this.a1c.doubleValue());
    }
    if (this.a0C != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_ADJUSTMENT_TO_BAL_MTD", this.a0C.doubleValue());
    }
    if (this.a1b != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_ADJUSTMENT_TO_BAL_YTD", this.a1b.doubleValue());
    }
    if (this.a04 != null) {
      XMLHandler.appendTag(localStringBuffer, "FOUR_DAY_FLOAT", this.a04.doubleValue());
    }
    if (this.a07 != null) {
      XMLHandler.appendTag(localStringBuffer, "FIVE_DAY_FLOAT", this.a07.doubleValue());
    }
    if (this.a0E != null) {
      XMLHandler.appendTag(localStringBuffer, "SIX_DAY_FLOAT", this.a0E.doubleValue());
    }
    if (this.a0P != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_ONE_DAY_FLOAT_MTD", this.a0P.doubleValue());
    }
    if (this.a1j != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_ONE_DAY_FLOAT_YTD", this.a1j.doubleValue());
    }
    if (this.a01 != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_TWO_DAY_FLOAT_MTD", this.a01.doubleValue());
    }
    if (this.a0F != null) {
      XMLHandler.appendTag(localStringBuffer, "AVG_TWO_DAY_FLOAT_YTD", this.a0F.doubleValue());
    }
    if (this.a0y != null) {
      XMLHandler.appendTag(localStringBuffer, "TRANSFER_CALCULATION", this.a0y.doubleValue());
    }
    if (this.a0O != null) {
      XMLHandler.appendTag(localStringBuffer, "TARGET_BALDEFICIENCY", this.a0O.doubleValue());
    }
    if (this.a1n != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_FUNDING_REQUIREMENT", this.a1n.doubleValue());
    }
    if (this.a0Y != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_CHECKS_PAID", this.a0Y.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_CHECKS_PAID", this.a06);
    if (this.a0v != null) {
      XMLHandler.appendTag(localStringBuffer, "GRAND_TOTAL_CREDITS_MINUS_GRAND_TOTAL_DEBITS", this.a0v.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "GRAND_NUM_CREDITS_MINUS_GRAND_NUM_DEBITS", this.a1g);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACCOUNT_HISTORY");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new ExtendABean.InternalXMLHandler(this), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AccountHistory
 * JD-Core Version:    0.7.0.1
 */