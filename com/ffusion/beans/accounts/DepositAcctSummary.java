package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class DepositAcctSummary
  extends AccountSummary
{
  public static final String DEPOSIT_ACCT_SUMMARY = "DEPOSIT_ACCT_SUMMARY";
  public static final String TOTAL_CREDITS = "TOTAL_CREDITS";
  public static final String TOTAL_CREDIT_AMT_MTD = "TOTAL_CREDIT_AMT_MTD";
  public static final String NUM_CREDIT_MTD = "NUM_CREDIT_MTD";
  public static final String CREDITS_NOT_DETAILED = "CREDITS_NOT_DETAILED";
  public static final String NUM_CREDITS_NOT_DETAILED = "NUM_CREDITS_NOT_DETAILED";
  public static final String DEPOSITS_SUBJECT_TO_FLOAT = "DEPOSITS_SUBJECT_TO_FLOAT";
  public static final String NUM_DEPOSITS_SUBJECT_TO_FLOAT = "NUM_DEPOSITS_SUBJECT_TO_FLOAT";
  public static final String TOTAL_ADJ_CREDITS_YTD = "TOTAL_ADJ_CREDITS_YTD";
  public static final String NUM_ADJ_CREDITS_YTD = "NUM_ADJ_CREDITS_YTD";
  public static final String TOTAL_LOCKBOX_DEPOSITS = "TOTAL_LOCKBOX_DEPOSITS";
  public static final String NUM_LOCKBOX_DEPOSITS = "NUM_LOCKBOX_DEPOSITS";
  public static final String TOTAL_DEBITS = "TOTAL_DEBITS";
  public static final String TOTAL_DEBIT_AMT_MTD = "TOTAL_DEBIT_AMT_MTD";
  public static final String NUM_DEBIT_MTD = "NUM_DEBIT_MTD";
  public static final String TODAYS_TOTAL_DEBITS = "TODAYS_TOTAL_DEBITS";
  public static final String NUM_TODAYS_TOTAL_DEBITS = "NUM_TODAYS_TOTAL_DEBITS";
  public static final String TOTAL_DEBITS_LESS_WIRE_AND_CHARGE = "TOTAL_DEBITS_LESS_WIRE_AND_CHARGE";
  public static final String NUM_DEBITS_LESS_WIRE_AND_CHARGE = "NUM_DEBITS_LESS_WIRE_AND_CHARGE";
  public static final String TOTAL_ADJ_DEBITS_YTD = "TOTAL_ADJ_DEBITS_YTD";
  public static final String NUM_ADJ_DEBITS_YTD = "NUM_ADJ_DEBITS_YTD";
  public static final String TOTAL_DEBITS_EXCLUDE_RETURNS = "TOTAL_DEBITS_EXCLUDE_RETURNS";
  public static final String NUM_DEBITS_EXCLUDE_RETURNS = "NUM_DEBITS_EXCLUDE_RETURNS";
  public static final String IMMED_AVAIL_AMT = "IMMED_AVAIL_AMT";
  public static final String ONE_DAY_AVAIL_AMT = "ONE_DAY_AVAIL_AMT";
  public static final String MORE_THAN_ONE_DAY_AVAIL_AMT = "MORE_THAN_ONE_DAY_AVAIL_AMT";
  public static final String AVAIL_OVERDRAFT = "AVAIL_OVERDRAFT";
  public static final String RESTRICTED_CASH = "RESTRICTED_CASH";
  public static final String ACCRUED_INTEREST = "ACCRUED_INTEREST";
  public static final String ACCRUED_DIVIDEND = "ACCRUED_DIVIDEND";
  public static final String TOTAL_OVERDRAFT_AMT = "TOTAL_OVERDRAFT_AMT";
  public static final String NEXT_OVERDRAFT_PMT_DATE = "NEXT_OVERDRAFT_PMT_DATE";
  public static final String INTEREST_RATE = "INTEREST_RATE";
  public static final String OPENING_LEDGER = "OPENING_LEDGER";
  public static final String CLOSING_LEDGER = "CLOSING_LEDGER";
  public static final String CURRENT_AVAIL_BAL = "CURRENT_AVAIL_BAL";
  public static final String LEDGER_BAL = "LEDGER_BAL";
  public static final String ONE_DAY_FLOAT = "ONE_DAY_FLOAT";
  public static final String TWO_DAY_FLOAT = "TWO_DAY_FLOAT";
  public static final String TOTAL_FLOAT = "TOTAL_FLOAT";
  public static final String CURRENT_LEDGER = "CURRENT_LEDGER";
  public static final String INTEREST_YTD = "INTEREST_YTD";
  public static final String PRIOR_YEAR_INTEREST = "PRIOR_YEAR_INTEREST";
  private Currency a26;
  private Currency a2M;
  private int a2T = -1;
  private Currency a21;
  private int a3l = -1;
  private Currency a3d;
  private int a3j = -1;
  private Currency a2L;
  private int a3h = -1;
  private Currency a3f;
  private int a2V = -1;
  private Currency a2Y;
  private Currency a29;
  private int a2X = -1;
  private Currency a28;
  private int a3w = -1;
  private Currency a3v;
  private int a2P = -1;
  private Currency a27;
  private int a3p = -1;
  private Currency a22;
  private int a3e = -1;
  private Currency a3t;
  private Currency a3u;
  private Currency a3n;
  private Currency a2R;
  private Currency a2W;
  private Currency a3g;
  private Currency a20;
  private Currency a24;
  private DateTime a3o;
  private float a2S = -1.0F;
  private Currency a2Q;
  private Currency a3k;
  private Currency a3m;
  private Currency a3a;
  private Currency a25;
  private Currency a3q;
  private Currency a3s;
  private int a2U = -1;
  private int a3x = -1;
  private Currency a3c;
  private Currency a3r;
  private Currency a2O;
  private Currency a23;
  private Currency a3b;
  private Currency a2N;
  private Currency a3i;
  private Currency a2Z;
  
  public Currency getTotalCredits()
  {
    return this.a26;
  }
  
  public void setTotalCredits(Currency paramCurrency)
  {
    this.a26 = paramCurrency;
  }
  
  public void setTotalCredits(String paramString)
  {
    if (this.a26 == null) {
      this.a26 = new Currency(paramString, this.locale);
    } else {
      this.a26.fromString(paramString);
    }
  }
  
  public Currency getTotalCreditAmtMTD()
  {
    return this.a2M;
  }
  
  public void setTotalCreditAmtMTD(Currency paramCurrency)
  {
    this.a2M = paramCurrency;
  }
  
  public void setTotalCreditAmtMTD(String paramString)
  {
    if (this.a2M == null) {
      this.a2M = new Currency(paramString, this.locale);
    } else {
      this.a2M.fromString(paramString);
    }
  }
  
  public int getNumCreditMTD()
  {
    return this.a2T;
  }
  
  public void setNumCreditMTD(int paramInt)
  {
    this.a2T = paramInt;
  }
  
  public void setNumCreditMTD(String paramString)
  {
    try
    {
      this.a2T = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public Currency getCreditsNotDetailed()
  {
    return this.a21;
  }
  
  public void setCreditsNotDetailed(Currency paramCurrency)
  {
    this.a21 = paramCurrency;
  }
  
  public void setCreditsNotDetailed(String paramString)
  {
    if (this.a21 == null) {
      this.a21 = new Currency(paramString, this.locale);
    } else {
      this.a21.fromString(paramString);
    }
  }
  
  public int getNumCreditsNotDetailed()
  {
    return this.a3l;
  }
  
  public void setNumCreditsNotDetailed(int paramInt)
  {
    this.a3l = paramInt;
  }
  
  public void setNumCreditsNotDetailed(String paramString)
  {
    try
    {
      this.a3l = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public Currency getDepositsSubjectToFloat()
  {
    return this.a3d;
  }
  
  public void setDepositsSubjectToFloat(Currency paramCurrency)
  {
    this.a3d = paramCurrency;
  }
  
  public void setDepositsSubjectToFloat(String paramString)
  {
    if (this.a3d == null) {
      this.a3d = new Currency(paramString, this.locale);
    } else {
      this.a3d.fromString(paramString);
    }
  }
  
  public int getNumDepositsSubjectToFloat()
  {
    return this.a3j;
  }
  
  public void setNumDepositsSubjectToFloat(int paramInt)
  {
    this.a3j = paramInt;
  }
  
  public void setNumDepositsSubjectToFloat(String paramString)
  {
    try
    {
      this.a3j = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public Currency getTotalAdjCreditsYTD()
  {
    return this.a2L;
  }
  
  public void setTotalAdjCreditsYTD(Currency paramCurrency)
  {
    this.a2L = paramCurrency;
  }
  
  public void setTotalAdjCreditsYTD(String paramString)
  {
    if (this.a2L == null) {
      this.a2L = new Currency(paramString, this.locale);
    } else {
      this.a2L.fromString(paramString);
    }
  }
  
  public int getNumAdjCreditsYTD()
  {
    return this.a3h;
  }
  
  public void setNumAdjCreditsYTD(int paramInt)
  {
    this.a3h = paramInt;
  }
  
  public void setNumAdjCreditsYTD(String paramString)
  {
    try
    {
      this.a3h = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public Currency getTotalLockboxDeposits()
  {
    return this.a3f;
  }
  
  public void setTotalLockboxDeposits(Currency paramCurrency)
  {
    this.a3f = paramCurrency;
  }
  
  public void setTotalLockboxDeposits(String paramString)
  {
    if (this.a3f == null) {
      this.a3f = new Currency(paramString, this.locale);
    } else {
      this.a3f.fromString(paramString);
    }
  }
  
  public int getNumLockboxDeposits()
  {
    return this.a2V;
  }
  
  public void setNumLockboxDeposits(int paramInt)
  {
    this.a2V = paramInt;
  }
  
  public void setNumLockboxDeposits(String paramString)
  {
    try
    {
      this.a2V = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public Currency getTotalDebits()
  {
    return this.a2Y;
  }
  
  public void setTotalDebits(Currency paramCurrency)
  {
    this.a2Y = paramCurrency;
  }
  
  public void setTotalDebits(String paramString)
  {
    if (this.a2Y == null) {
      this.a2Y = new Currency(paramString, this.locale);
    } else {
      this.a2Y.fromString(paramString);
    }
  }
  
  public Currency getTotalDebitAmtMTD()
  {
    return this.a29;
  }
  
  public void setTotalDebitAmtMTD(Currency paramCurrency)
  {
    this.a29 = paramCurrency;
  }
  
  public void setTotalDebitAmtMTD(String paramString)
  {
    if (this.a29 == null) {
      this.a29 = new Currency(paramString, this.locale);
    } else {
      this.a29.fromString(paramString);
    }
  }
  
  public int getNumDebitMTD()
  {
    return this.a2X;
  }
  
  public void setNumDebitMTD(int paramInt)
  {
    this.a2X = paramInt;
  }
  
  public void setNumDebitMTD(String paramString)
  {
    try
    {
      this.a2X = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public Currency getTodaysTotalDebits()
  {
    return this.a28;
  }
  
  public void setTodaysTotalDebits(Currency paramCurrency)
  {
    this.a28 = paramCurrency;
  }
  
  public void setTodaysTotalDebits(String paramString)
  {
    if (this.a28 == null) {
      this.a28 = new Currency(paramString, this.locale);
    } else {
      this.a28.fromString(paramString);
    }
  }
  
  public int getNumTodaysTotalDebits()
  {
    return this.a3w;
  }
  
  public void setNumTodaysTotalDebits(int paramInt)
  {
    this.a3w = paramInt;
  }
  
  public void setNumTodaysTotalDebits(String paramString)
  {
    try
    {
      this.a3w = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public Currency getTotalDebitsLessWireAndCharge()
  {
    return this.a3v;
  }
  
  public void setTotalDebitsLessWireAndCharge(Currency paramCurrency)
  {
    this.a3v = paramCurrency;
  }
  
  public void setTotalDebitsLessWireAndCharge(String paramString)
  {
    if (this.a3v == null) {
      this.a3v = new Currency(paramString, this.locale);
    } else {
      this.a3v.fromString(paramString);
    }
  }
  
  public int getNumDebitsLessWireAndCharge()
  {
    return this.a2P;
  }
  
  public void setNumDebitsLessWireAndCharge(int paramInt)
  {
    this.a2P = paramInt;
  }
  
  public void setNumDebitsLessWireAndCharge(String paramString)
  {
    try
    {
      this.a2P = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public Currency getTotalAdjDebitsYTD()
  {
    return this.a27;
  }
  
  public void setTotalAdjDebitsYTD(Currency paramCurrency)
  {
    this.a27 = paramCurrency;
  }
  
  public void setTotalAdjDebitsYTD(String paramString)
  {
    if (this.a27 == null) {
      this.a27 = new Currency(paramString, this.locale);
    } else {
      this.a27.fromString(paramString);
    }
  }
  
  public int getNumAdjDebitsYTD()
  {
    return this.a3p;
  }
  
  public void setNumAdjDebitsYTD(int paramInt)
  {
    this.a3p = paramInt;
  }
  
  public void setNumAdjDebitsYTD(String paramString)
  {
    try
    {
      this.a3p = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public Currency getTotalDebitsExcludeReturns()
  {
    return this.a22;
  }
  
  public void setTotalDebitsExcludeReturns(Currency paramCurrency)
  {
    this.a22 = paramCurrency;
  }
  
  public void setTotalDebitsExcludeReturns(String paramString)
  {
    if (this.a22 == null) {
      this.a22 = new Currency(paramString, this.locale);
    } else {
      this.a22.fromString(paramString);
    }
  }
  
  public int getNumDebitsExcludeReturns()
  {
    return this.a3e;
  }
  
  public void setNumDebitsExcludeReturns(int paramInt)
  {
    this.a3e = paramInt;
  }
  
  public void setNumDebitsExcludeReturns(String paramString)
  {
    try
    {
      this.a3e = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException) {}
  }
  
  public Currency getImmedAvailAmt()
  {
    return this.a3t;
  }
  
  public void setImmedAvailAmt(Currency paramCurrency)
  {
    this.a3t = paramCurrency;
  }
  
  public void setImmedAvailAmt(String paramString)
  {
    if (this.a3t == null) {
      this.a3t = new Currency(paramString, this.locale);
    } else {
      this.a3t.fromString(paramString);
    }
  }
  
  public Currency getOneDayAvailAmt()
  {
    return this.a3u;
  }
  
  public void setOneDayAvailAmt(Currency paramCurrency)
  {
    this.a3u = paramCurrency;
  }
  
  public void setOneDayAvailAmt(String paramString)
  {
    if (this.a3u == null) {
      this.a3u = new Currency(paramString, this.locale);
    } else {
      this.a3u.fromString(paramString);
    }
  }
  
  public Currency getMoreThanOneDayAvailAmt()
  {
    return this.a3n;
  }
  
  public void setMoreThanOneDayAvailAmt(Currency paramCurrency)
  {
    this.a3n = paramCurrency;
  }
  
  public void setMoreThanOneDayAvailAmt(String paramString)
  {
    if (this.a3n == null) {
      this.a3n = new Currency(paramString, this.locale);
    } else {
      this.a3n.fromString(paramString);
    }
  }
  
  public Currency getAvailOverdraft()
  {
    return this.a2R;
  }
  
  public void setAvailOverdraft(Currency paramCurrency)
  {
    this.a2R = paramCurrency;
  }
  
  public void setAvailOverdraft(String paramString)
  {
    if (this.a2R == null) {
      this.a2R = new Currency(paramString, this.locale);
    } else {
      this.a2R.fromString(paramString);
    }
  }
  
  public Currency getRestrictedCash()
  {
    return this.a2W;
  }
  
  public void setRestrictedCash(Currency paramCurrency)
  {
    this.a2W = paramCurrency;
  }
  
  public void setRestrictedCash(String paramString)
  {
    if (this.a2W == null) {
      this.a2W = new Currency(paramString, this.locale);
    } else {
      this.a2W.fromString(paramString);
    }
  }
  
  public Currency getAccruedInterest()
  {
    return this.a3g;
  }
  
  public void setAccruedInterest(Currency paramCurrency)
  {
    this.a3g = paramCurrency;
  }
  
  public void setAccruedInterest(String paramString)
  {
    if (this.a3g == null) {
      this.a3g = new Currency(paramString, this.locale);
    } else {
      this.a3g.fromString(paramString);
    }
  }
  
  public Currency getAccruedDividend()
  {
    return this.a20;
  }
  
  public void setAccruedDividend(Currency paramCurrency)
  {
    this.a20 = paramCurrency;
  }
  
  public void setAccruedDividend(String paramString)
  {
    if (this.a20 == null) {
      this.a20 = new Currency(paramString, this.locale);
    } else {
      this.a20.fromString(paramString);
    }
  }
  
  public Currency getTotalOverdraftAmt()
  {
    return this.a24;
  }
  
  public void setTotalOverdraftAmt(Currency paramCurrency)
  {
    this.a24 = paramCurrency;
  }
  
  public void setTotalOverdraftAmt(String paramString)
  {
    if (this.a24 == null) {
      this.a24 = new Currency(paramString, this.locale);
    } else {
      this.a24.fromString(paramString);
    }
  }
  
  public DateTime getNextOverdraftPmtDate()
  {
    return this.a3o;
  }
  
  public void setNextOverdraftPmtDate(DateTime paramDateTime)
  {
    this.a3o = paramDateTime;
  }
  
  public void setNextOverdraftPmtDate(String paramString)
  {
    try
    {
      if (this.a3o == null) {
        this.a3o = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a3o.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public float getInterestRate()
  {
    return this.a2S;
  }
  
  public String getInterestRateString()
  {
    if (this.a2S == -1.0F) {
      return null;
    }
    NumberFormat localNumberFormat = NumberFormat.getNumberInstance(this.locale);
    localNumberFormat.setMinimumFractionDigits(5);
    localNumberFormat.setMaximumFractionDigits(5);
    return localNumberFormat.format(this.a2S);
  }
  
  public void setInterestRate(float paramFloat)
  {
    this.a2S = paramFloat;
  }
  
  public Currency getOpeningLedger()
  {
    return this.a2Q;
  }
  
  public void setOpeningLedger(Currency paramCurrency)
  {
    this.a2Q = paramCurrency;
  }
  
  public void setOpeningLedger(String paramString)
  {
    if (this.a2Q == null) {
      this.a2Q = new Currency(paramString, this.locale);
    } else {
      this.a2Q.fromString(paramString);
    }
  }
  
  public Currency getClosingLedger()
  {
    return this.a3k;
  }
  
  public void setClosingLedger(Currency paramCurrency)
  {
    this.a3k = paramCurrency;
  }
  
  public void setClosingLedger(String paramString)
  {
    if (this.a3k == null) {
      this.a3k = new Currency(paramString, this.locale);
    } else {
      this.a3k.fromString(paramString);
    }
  }
  
  public Currency getCurrentAvailBal()
  {
    return this.a3m;
  }
  
  public void setCurrentAvailBal(Currency paramCurrency)
  {
    this.a3m = paramCurrency;
  }
  
  public void setCurrentAvailBal(String paramString)
  {
    if (this.a3m == null) {
      this.a3m = new Currency(paramString, this.locale);
    } else {
      this.a3m.fromString(paramString);
    }
  }
  
  public Currency getLedgerBal()
  {
    return this.a3a;
  }
  
  public void setLedgerBal(Currency paramCurrency)
  {
    this.a3a = paramCurrency;
  }
  
  public void setLedgerBal(String paramString)
  {
    if (this.a3a == null) {
      this.a3a = new Currency(paramString, this.locale);
    } else {
      this.a3a.fromString(paramString);
    }
  }
  
  public Currency getOneDayFloat()
  {
    return this.a25;
  }
  
  public void setOneDayFloat(Currency paramCurrency)
  {
    this.a25 = paramCurrency;
  }
  
  public void setOneDayFloat(String paramString)
  {
    if (this.a25 == null) {
      this.a25 = new Currency(paramString, this.locale);
    } else {
      this.a25.fromString(paramString);
    }
  }
  
  public Currency getTwoDayFloat()
  {
    return this.a3q;
  }
  
  public void setTwoDayFloat(Currency paramCurrency)
  {
    this.a3q = paramCurrency;
  }
  
  public void setTwoDayFloat(String paramString)
  {
    if (this.a3q == null) {
      this.a3q = new Currency(paramString, this.locale);
    } else {
      this.a3q.fromString(paramString);
    }
  }
  
  public Currency getTotalFloat()
  {
    return this.a3s;
  }
  
  public void setTotalFloat(Currency paramCurrency)
  {
    this.a3s = paramCurrency;
  }
  
  public void setTotalFloat(String paramString)
  {
    if (this.a3s == null) {
      this.a3s = new Currency(paramString, this.locale);
    } else {
      this.a3s.fromString(paramString);
    }
  }
  
  public Currency getCurrentLedger()
  {
    return this.a3c;
  }
  
  public void setCurrentLedger(Currency paramCurrency)
  {
    this.a3c = paramCurrency;
  }
  
  public void setCurrentLedger(String paramString)
  {
    if (this.a3c == null) {
      this.a3c = new Currency(paramString, this.locale);
    } else {
      this.a3c.fromString(paramString);
    }
  }
  
  public Currency getInterestYTD()
  {
    return this.a3r;
  }
  
  public void setInterestYTD(Currency paramCurrency)
  {
    this.a3r = paramCurrency;
  }
  
  public void setInterestYTD(String paramString)
  {
    if (this.a3r == null) {
      this.a3r = new Currency(paramString, this.locale);
    } else {
      this.a3r.fromString(paramString);
    }
  }
  
  public Currency getPriorYearInterest()
  {
    return this.a2O;
  }
  
  public void setPriorYearInterest(Currency paramCurrency)
  {
    this.a2O = paramCurrency;
  }
  
  public void setPriorYearInterest(String paramString)
  {
    if (this.a2O == null) {
      this.a2O = new Currency(paramString, this.locale);
    } else {
      this.a2O.fromString(paramString);
    }
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a26 != null) {
      this.a26.setLocale(paramLocale);
    }
    if (this.a2M != null) {
      this.a2M.setLocale(paramLocale);
    }
    if (this.a21 != null) {
      this.a21.setLocale(paramLocale);
    }
    if (this.a3d != null) {
      this.a3d.setLocale(paramLocale);
    }
    if (this.a2L != null) {
      this.a2L.setLocale(paramLocale);
    }
    if (this.a3f != null) {
      this.a3f.setLocale(paramLocale);
    }
    if (this.a2Y != null) {
      this.a2Y.setLocale(paramLocale);
    }
    if (this.a29 != null) {
      this.a29.setLocale(paramLocale);
    }
    if (this.a28 != null) {
      this.a28.setLocale(paramLocale);
    }
    if (this.a3v != null) {
      this.a3v.setLocale(paramLocale);
    }
    if (this.a27 != null) {
      this.a27.setLocale(paramLocale);
    }
    if (this.a22 != null) {
      this.a22.setLocale(paramLocale);
    }
    if (this.a3t != null) {
      this.a3t.setLocale(paramLocale);
    }
    if (this.a3u != null) {
      this.a3u.setLocale(paramLocale);
    }
    if (this.a3n != null) {
      this.a3n.setLocale(paramLocale);
    }
    if (this.a2R != null) {
      this.a2R.setLocale(paramLocale);
    }
    if (this.a2W != null) {
      this.a2W.setLocale(paramLocale);
    }
    if (this.a3g != null) {
      this.a3g.setLocale(paramLocale);
    }
    if (this.a20 != null) {
      this.a20.setLocale(paramLocale);
    }
    if (this.a24 != null) {
      this.a24.setLocale(paramLocale);
    }
    if (this.a3o != null) {
      this.a3o.setLocale(paramLocale);
    }
    if (this.a2Q != null) {
      this.a2Q.setLocale(paramLocale);
    }
    if (this.a3k != null) {
      this.a3k.setLocale(paramLocale);
    }
    if (this.a3m != null) {
      this.a3m.setLocale(paramLocale);
    }
    if (this.a3a != null) {
      this.a3a.setLocale(paramLocale);
    }
    if (this.a25 != null) {
      this.a25.setLocale(paramLocale);
    }
    if (this.a3q != null) {
      this.a3q.setLocale(paramLocale);
    }
    if (this.a3s != null) {
      this.a3s.setLocale(paramLocale);
    }
    if (this.a3c != null) {
      this.a3c.setLocale(paramLocale);
    }
    if (this.a3r != null) {
      this.a3r.setLocale(paramLocale);
    }
    if (this.a2O != null) {
      this.a2O.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.a3o != null) {
      this.a3o.setFormat(paramString);
    }
  }
  
  public int compare(Object paramObject, String paramString)
  {
    DepositAcctSummary localDepositAcctSummary = (DepositAcctSummary)paramObject;
    int i = 1;
    if (paramString.equals("TOTAL_CREDITS")) {
      i = Currency.compare(this.a26, localDepositAcctSummary.getTotalCredits());
    } else if ((paramString.equals("TOTAL_CREDIT_AMT_MTD")) && (this.a2M != null) && (localDepositAcctSummary.getTotalCreditAmtMTD() != null)) {
      i = this.a2M.compareTo(localDepositAcctSummary.getTotalCreditAmtMTD());
    } else if (paramString.equals("NUM_CREDIT_MTD")) {
      i = this.a2T == localDepositAcctSummary.getNumCreditMTD() ? 0 : this.a2T < localDepositAcctSummary.getNumCreditMTD() ? -1 : 1;
    } else if ((paramString.equals("CREDITS_NOT_DETAILED")) && (this.a21 != null) && (localDepositAcctSummary.getCreditsNotDetailed() != null)) {
      i = this.a21.compareTo(localDepositAcctSummary.getCreditsNotDetailed());
    } else if (paramString.equals("NUM_CREDITS_NOT_DETAILED")) {
      i = this.a3l == localDepositAcctSummary.getNumCreditsNotDetailed() ? 0 : this.a3l < localDepositAcctSummary.getNumCreditsNotDetailed() ? -1 : 1;
    } else if ((paramString.equals("DEPOSITS_SUBJECT_TO_FLOAT")) && (this.a3d != null) && (localDepositAcctSummary.getDepositsSubjectToFloat() != null)) {
      i = this.a3d.compareTo(localDepositAcctSummary.getDepositsSubjectToFloat());
    } else if (paramString.equals("NUM_DEPOSITS_SUBJECT_TO_FLOAT")) {
      i = this.a3j == localDepositAcctSummary.getNumDepositsSubjectToFloat() ? 0 : this.a3j < localDepositAcctSummary.getNumDepositsSubjectToFloat() ? -1 : 1;
    } else if ((paramString.equals("TOTAL_ADJ_CREDITS_YTD")) && (this.a2L != null) && (localDepositAcctSummary.getTotalAdjCreditsYTD() != null)) {
      i = this.a2L.compareTo(localDepositAcctSummary.getTotalAdjCreditsYTD());
    } else if (paramString.equals("NUM_ADJ_CREDITS_YTD")) {
      i = this.a3h == localDepositAcctSummary.getNumAdjCreditsYTD() ? 0 : this.a3h < localDepositAcctSummary.getNumAdjCreditsYTD() ? -1 : 1;
    } else if ((paramString.equals("TOTAL_LOCKBOX_DEPOSITS")) && (this.a3f != null) && (localDepositAcctSummary.getTotalLockboxDeposits() != null)) {
      i = this.a3f.compareTo(localDepositAcctSummary.getTotalLockboxDeposits());
    } else if (paramString.equals("NUM_LOCKBOX_DEPOSITS")) {
      i = this.a2V == localDepositAcctSummary.getNumLockboxDeposits() ? 0 : this.a2V < localDepositAcctSummary.getNumLockboxDeposits() ? -1 : 1;
    } else if (paramString.equals("TOTAL_DEBITS")) {
      i = Currency.compare(this.a2Y, localDepositAcctSummary.getTotalDebits());
    } else if ((paramString.equals("TOTAL_DEBIT_AMT_MTD")) && (this.a29 != null) && (localDepositAcctSummary.getTotalDebitAmtMTD() != null)) {
      i = this.a29.compareTo(localDepositAcctSummary.getTotalDebitAmtMTD());
    } else if ((paramString.equals("TODAYS_TOTAL_DEBITS")) && (this.a28 != null) && (localDepositAcctSummary.getTodaysTotalDebits() != null)) {
      i = this.a28.compareTo(localDepositAcctSummary.getTodaysTotalDebits());
    } else if (paramString.equals("NUM_TODAYS_TOTAL_DEBITS")) {
      i = this.a3w == localDepositAcctSummary.getNumTodaysTotalDebits() ? 0 : this.a3w < localDepositAcctSummary.getNumTodaysTotalDebits() ? -1 : 1;
    } else if ((paramString.equals("TOTAL_DEBITS_LESS_WIRE_AND_CHARGE")) && (this.a3v != null) && (localDepositAcctSummary.getTotalDebitsLessWireAndCharge() != null)) {
      i = this.a3v.compareTo(localDepositAcctSummary.getTotalDebitsLessWireAndCharge());
    } else if (paramString.equals("NUM_DEBITS_LESS_WIRE_AND_CHARGE")) {
      i = this.a2P == localDepositAcctSummary.getNumDebitsLessWireAndCharge() ? 0 : this.a2P < localDepositAcctSummary.getNumDebitsLessWireAndCharge() ? -1 : 1;
    } else if ((paramString.equals("TOTAL_ADJ_DEBITS_YTD")) && (this.a27 != null) && (localDepositAcctSummary.getTotalAdjDebitsYTD() != null)) {
      i = this.a27.compareTo(localDepositAcctSummary.getTotalAdjDebitsYTD());
    } else if (paramString.equals("NUM_ADJ_DEBITS_YTD")) {
      i = this.a3p == localDepositAcctSummary.getNumAdjDebitsYTD() ? 0 : this.a3p < localDepositAcctSummary.getNumAdjDebitsYTD() ? -1 : 1;
    } else if ((paramString.equals("TOTAL_DEBITS_EXCLUDE_RETURNS")) && (this.a22 != null) && (localDepositAcctSummary.getTotalDebitsExcludeReturns() != null)) {
      i = this.a22.compareTo(localDepositAcctSummary.getTotalDebitsExcludeReturns());
    } else if (paramString.equals("NUM_DEBITS_EXCLUDE_RETURNS")) {
      i = this.a3e == localDepositAcctSummary.getNumDebitsExcludeReturns() ? 0 : this.a3e < localDepositAcctSummary.getNumDebitsExcludeReturns() ? -1 : 1;
    } else if ((paramString.equals("IMMED_AVAIL_AMT")) && (this.a3t != null) && (localDepositAcctSummary.getImmedAvailAmt() != null)) {
      i = this.a3t.compareTo(localDepositAcctSummary.getImmedAvailAmt());
    } else if ((paramString.equals("ONE_DAY_AVAIL_AMT")) && (this.a3u != null) && (localDepositAcctSummary.getOneDayAvailAmt() != null)) {
      i = this.a3u.compareTo(localDepositAcctSummary.getOneDayAvailAmt());
    } else if ((paramString.equals("MORE_THAN_ONE_DAY_AVAIL_AMT")) && (this.a3n != null) && (localDepositAcctSummary.getMoreThanOneDayAvailAmt() != null)) {
      i = this.a3n.compareTo(localDepositAcctSummary.getMoreThanOneDayAvailAmt());
    } else if ((paramString.equals("AVAIL_OVERDRAFT")) && (this.a2R != null) && (localDepositAcctSummary.getAvailOverdraft() != null)) {
      i = this.a2R.compareTo(localDepositAcctSummary.getAvailOverdraft());
    } else if ((paramString.equals("RESTRICTED_CASH")) && (this.a2W != null) && (localDepositAcctSummary.getRestrictedCash() != null)) {
      i = this.a2W.compareTo(localDepositAcctSummary.getRestrictedCash());
    } else if ((paramString.equals("ACCRUED_INTEREST")) && (this.a3g != null) && (localDepositAcctSummary.getAccruedInterest() != null)) {
      i = this.a3g.compareTo(localDepositAcctSummary.getAccruedInterest());
    } else if ((paramString.equals("ACCRUED_DIVIDEND")) && (this.a20 != null) && (localDepositAcctSummary.getAccruedDividend() != null)) {
      i = this.a20.compareTo(localDepositAcctSummary.getAccruedDividend());
    } else if ((paramString.equals("TOTAL_OVERDRAFT_AMT")) && (this.a24 != null) && (localDepositAcctSummary.getTotalOverdraftAmt() != null)) {
      i = this.a24.compareTo(localDepositAcctSummary.getTotalOverdraftAmt());
    } else if ((paramString.equals("NEXT_OVERDRAFT_PMT_DATE")) && (this.a3o != null) && (localDepositAcctSummary.getNextOverdraftPmtDate() != null)) {
      i = this.a3o.compare(localDepositAcctSummary.getNextOverdraftPmtDate(), paramString);
    } else if ((paramString.equals("INTEREST_RATE")) && (this.a2S != -1.0F) && (localDepositAcctSummary.a2S != -1.0F)) {
      i = this.a2S == localDepositAcctSummary.getInterestRate() ? 0 : this.a2S < localDepositAcctSummary.getInterestRate() ? -1 : 1;
    } else if (paramString.equals("OPENING_LEDGER")) {
      i = Currency.compare(this.a2Q, localDepositAcctSummary.getOpeningLedger());
    } else if (paramString.equals("CLOSING_LEDGER")) {
      i = Currency.compare(this.a3k, localDepositAcctSummary.getClosingLedger());
    } else if ((paramString.equals("CURRENT_AVAIL_BAL")) && (this.a3m != null) && (localDepositAcctSummary.getCurrentAvailBal() != null)) {
      i = this.a3m.compareTo(localDepositAcctSummary.getCurrentAvailBal());
    } else if ((paramString.equals("LEDGER_BAL")) && (this.a3a != null) && (localDepositAcctSummary.getLedgerBal() != null)) {
      i = this.a3a.compareTo(localDepositAcctSummary.getLedgerBal());
    } else if ((paramString.equals("ONE_DAY_FLOAT")) && (this.a25 != null) && (localDepositAcctSummary.getOneDayFloat() != null)) {
      i = this.a25.compareTo(localDepositAcctSummary.getOneDayFloat());
    } else if ((paramString.equals("TWO_DAY_FLOAT")) && (this.a3q != null) && (localDepositAcctSummary.getTwoDayFloat() != null)) {
      i = this.a3q.compareTo(localDepositAcctSummary.getTwoDayFloat());
    } else if ((paramString.equals("TOTAL_FLOAT")) && (this.a3s != null) && (localDepositAcctSummary.getTotalFloat() != null)) {
      i = this.a3s.compareTo(localDepositAcctSummary.getTotalFloat());
    } else if ((paramString.equals("CURRENT_LEDGER")) && (this.a3c != null) && (localDepositAcctSummary.getCurrentLedger() != null)) {
      i = this.a3c.compareTo(localDepositAcctSummary.getCurrentLedger());
    } else if ((paramString.equals("INTEREST_YTD")) && (this.a3r != null) && (localDepositAcctSummary.getInterestYTD() != null)) {
      i = this.a3r.compareTo(localDepositAcctSummary.getInterestYTD());
    } else if ((paramString.equals("PRIOR_YEAR_INTEREST")) && (this.a2O != null) && (localDepositAcctSummary.getPriorYearInterest() != null)) {
      i = this.a2O.compareTo(localDepositAcctSummary.getPriorYearInterest());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("TOTAL_CREDITS")) && (this.a26 != null)) {
      return this.a26.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TOTAL_CREDIT_AMT_MTD")) && (this.a2M != null)) {
      return this.a2M.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if (paramString1.equals("NUM_CREDIT_MTD")) {
      return isFilterable(Integer.toString(this.a2T), paramString2, paramString3);
    }
    if ((paramString1.equals("CREDITS_NOT_DETAILED")) && (this.a21 != null)) {
      return this.a21.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if (paramString1.equals("NUM_CREDITS_NOT_DETAILED")) {
      return isFilterable(Integer.toString(this.a3l), paramString2, paramString3);
    }
    if ((paramString1.equals("DEPOSITS_SUBJECT_TO_FLOAT")) && (this.a3d != null)) {
      return this.a3d.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if (paramString1.equals("NUM_DEPOSITS_SUBJECT_TO_FLOAT")) {
      return isFilterable(Integer.toString(this.a3j), paramString2, paramString3);
    }
    if ((paramString1.equals("TOTAL_ADJ_CREDITS_YTD")) && (this.a2L != null)) {
      return this.a2L.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if (paramString1.equals("NUM_ADJ_CREDITS_YTD")) {
      return isFilterable(Integer.toString(this.a3h), paramString2, paramString3);
    }
    if ((paramString1.equals("TOTAL_LOCKBOX_DEPOSITS")) && (this.a3f != null)) {
      return this.a3f.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if (paramString1.equals("NUM_LOCKBOX_DEPOSITS")) {
      return isFilterable(Integer.toString(this.a2V), paramString2, paramString3);
    }
    if ((paramString1.equals("TOTAL_DEBITS")) && (this.a2Y != null)) {
      return this.a2Y.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TOTAL_DEBIT_AMT_MTD")) && (this.a29 != null)) {
      return this.a29.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TODAYS_TOTAL_DEBITS")) && (this.a28 != null)) {
      return this.a28.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if (paramString1.equals("NUM_TODAYS_TOTAL_DEBITS")) {
      return isFilterable(Integer.toString(this.a3w), paramString2, paramString3);
    }
    if ((paramString1.equals("TOTAL_DEBITS_LESS_WIRE_AND_CHARGE")) && (this.a3v != null)) {
      return this.a3v.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if (paramString1.equals("NUM_DEBITS_LESS_WIRE_AND_CHARGE")) {
      return isFilterable(Integer.toString(this.a2P), paramString2, paramString3);
    }
    if ((paramString1.equals("TOTAL_ADJ_DEBITS_YTD")) && (this.a27 != null)) {
      return this.a27.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if (paramString1.equals("NUM_ADJ_DEBITS_YTD")) {
      return isFilterable(Integer.toString(this.a3p), paramString2, paramString3);
    }
    if ((paramString1.equals("TOTAL_DEBITS_EXCLUDE_RETURNS")) && (this.a22 != null)) {
      return this.a22.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if (paramString1.equals("NUM_DEBITS_EXCLUDE_RETURNS")) {
      return isFilterable(Integer.toString(this.a3e), paramString2, paramString3);
    }
    if ((paramString1.equals("IMMED_AVAIL_AMT")) && (this.a3t != null)) {
      return this.a3t.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("ONE_DAY_AVAIL_AMT")) && (this.a3u != null)) {
      return this.a3u.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("MORE_THAN_ONE_DAY_AVAIL_AMT")) && (this.a3n != null)) {
      return this.a3n.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AVAIL_OVERDRAFT")) && (this.a2R != null)) {
      return this.a2R.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("RESTRICTED_CASH")) && (this.a2W != null)) {
      return this.a2W.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("ACCRUED_INTEREST")) && (this.a3g != null)) {
      return this.a3g.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("ACCRUED_DIVIDEND")) && (this.a20 != null)) {
      return this.a20.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TOTAL_OVERDRAFT_AMT")) && (this.a24 != null)) {
      return this.a24.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("NEXT_OVERDRAFT_PMT_DATE")) && (this.a3o != null)) {
      return this.a3o.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("INTEREST_RATE")) && (this.a2S != -1.0F)) {
      return isFilterable(Float.toString(this.a2S), paramString2, Float.valueOf(paramString3));
    }
    if ((paramString1.equals("OPENING_LEDGER")) && (this.a2Q != null)) {
      return this.a2Q.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("CLOSING_LEDGER")) && (this.a3k != null)) {
      return this.a3k.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("CURRENT_AVAIL_BAL")) && (this.a3m != null)) {
      return this.a3m.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("LEDGER_BAL")) && (this.a3a != null)) {
      return this.a3a.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("ONE_DAY_FLOAT")) && (this.a25 != null)) {
      return this.a25.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TWO_DAY_FLOAT")) && (this.a3q != null)) {
      return this.a3q.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("TOTAL_FLOAT")) && (this.a3s != null)) {
      return this.a3s.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("CURRENT_LEDGER")) && (this.a3c != null)) {
      return this.a3c.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("INTEREST_YTD")) && (this.a3r != null)) {
      return this.a3r.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("PRIOR_YEAR_INTEREST")) && (this.a2O != null)) {
      return this.a2O.isFilterable(paramString1 + paramString2 + paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("TOTAL_CREDITS"))
    {
      setTotalCredits(paramString2);
    }
    else if (paramString1.equals("TOTAL_CREDIT_AMT_MTD"))
    {
      setTotalCreditAmtMTD(paramString2);
    }
    else if (paramString1.equals("NUM_CREDIT_MTD"))
    {
      setNumCreditMTD(paramString2);
    }
    else if (paramString1.equals("CREDITS_NOT_DETAILED"))
    {
      setCreditsNotDetailed(paramString2);
    }
    else if (paramString1.equals("NUM_CREDITS_NOT_DETAILED"))
    {
      setNumCreditsNotDetailed(paramString2);
    }
    else if (paramString1.equals("DEPOSITS_SUBJECT_TO_FLOAT"))
    {
      setDepositsSubjectToFloat(paramString2);
    }
    else if (paramString1.equals("NUM_DEPOSITS_SUBJECT_TO_FLOAT"))
    {
      setNumDepositsSubjectToFloat(paramString2);
    }
    else if (paramString1.equals("TOTAL_ADJ_CREDITS_YTD"))
    {
      setTotalAdjCreditsYTD(paramString2);
    }
    else if (paramString1.equals("NUM_ADJ_CREDITS_YTD"))
    {
      setNumAdjCreditsYTD(paramString2);
    }
    else if (paramString1.equals("TOTAL_LOCKBOX_DEPOSITS"))
    {
      setTotalLockboxDeposits(paramString2);
    }
    else if (paramString1.equals("NUM_LOCKBOX_DEPOSITS"))
    {
      setNumLockboxDeposits(paramString2);
    }
    else if (paramString1.equals("TOTAL_DEBITS"))
    {
      setTotalDebits(paramString2);
    }
    else if (paramString1.equals("TOTAL_DEBIT_AMT_MTD"))
    {
      setTotalDebitAmtMTD(paramString2);
    }
    else if (paramString1.equals("NUM_DEBIT_MTD"))
    {
      setNumDebitMTD(paramString2);
    }
    else if (paramString1.equals("TODAYS_TOTAL_DEBITS"))
    {
      setTodaysTotalDebits(paramString2);
    }
    else if (paramString1.equals("NUM_TODAYS_TOTAL_DEBITS"))
    {
      setNumTodaysTotalDebits(paramString2);
    }
    else if (paramString1.equals("TOTAL_DEBITS_LESS_WIRE_AND_CHARGE"))
    {
      setTotalDebitsLessWireAndCharge(paramString2);
    }
    else if (paramString1.equals("NUM_DEBITS_LESS_WIRE_AND_CHARGE"))
    {
      setNumDebitsLessWireAndCharge(paramString2);
    }
    else if (paramString1.equals("TOTAL_ADJ_DEBITS_YTD"))
    {
      setTotalAdjDebitsYTD(paramString2);
    }
    else if (paramString1.equals("NUM_ADJ_DEBITS_YTD"))
    {
      setNumAdjDebitsYTD(paramString2);
    }
    else if (paramString1.equals("TOTAL_DEBITS_EXCLUDE_RETURNS"))
    {
      setTotalDebitsExcludeReturns(paramString2);
    }
    else if (paramString1.equals("NUM_DEBITS_EXCLUDE_RETURNS"))
    {
      setNumDebitsExcludeReturns(paramString2);
    }
    else if (paramString1.equals("IMMED_AVAIL_AMT"))
    {
      setImmedAvailAmt(paramString2);
    }
    else if (paramString1.equals("ONE_DAY_AVAIL_AMT"))
    {
      setOneDayAvailAmt(paramString2);
    }
    else if (paramString1.equals("MORE_THAN_ONE_DAY_AVAIL_AMT"))
    {
      setMoreThanOneDayAvailAmt(paramString2);
    }
    else if (paramString1.equals("AVAIL_OVERDRAFT"))
    {
      setAvailOverdraft(paramString2);
    }
    else if (paramString1.equals("RESTRICTED_CASH"))
    {
      setRestrictedCash(paramString2);
    }
    else if (paramString1.equals("ACCRUED_INTEREST"))
    {
      setAccruedInterest(paramString2);
    }
    else if (paramString1.equals("ACCRUED_DIVIDEND"))
    {
      setAccruedDividend(paramString2);
    }
    else if (paramString1.equals("TOTAL_OVERDRAFT_AMT"))
    {
      setTotalOverdraftAmt(paramString2);
    }
    else if (paramString1.equals("NEXT_OVERDRAFT_PMT_DATE"))
    {
      if (this.a3o == null)
      {
        this.a3o = new DateTime(this.locale);
        this.a3o.setFormat(this.datetype);
      }
      this.a3o.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("INTEREST_RATE"))
    {
      this.a2S = Float.parseFloat(paramString2);
    }
    else if (paramString1.equals("OPENING_LEDGER"))
    {
      setOpeningLedger(paramString2);
    }
    else if (paramString1.equals("CLOSING_LEDGER"))
    {
      setClosingLedger(paramString2);
    }
    else if (paramString1.equals("CURRENT_AVAIL_BAL"))
    {
      setCurrentAvailBal(paramString2);
    }
    else if (paramString1.equals("LEDGER_BAL"))
    {
      setLedgerBal(paramString2);
    }
    else if (paramString1.equals("ONE_DAY_FLOAT"))
    {
      setOneDayFloat(paramString2);
    }
    else if (paramString1.equals("TWO_DAY_FLOAT"))
    {
      setTwoDayFloat(paramString2);
    }
    else if (paramString1.equals("TOTAL_FLOAT"))
    {
      setTotalFloat(paramString2);
    }
    else if (paramString1.equals("CURRENT_LEDGER"))
    {
      setCurrentLedger(paramString2);
    }
    else if (paramString1.equals("INTEREST_YTD"))
    {
      setInterestYTD(paramString2);
    }
    else if (paramString1.equals("PRIOR_YEAR_INTEREST"))
    {
      setPriorYearInterest(paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "DEPOSIT_ACCT_SUMMARY");
    if (this.a26 != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_CREDITS", this.a26.doubleValue());
    }
    if (this.a2M != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_CREDIT_AMT_MTD", this.a2M.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_CREDIT_MTD", this.a2T);
    if (this.a21 != null) {
      XMLHandler.appendTag(localStringBuffer, "CREDITS_NOT_DETAILED", this.a21.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_CREDITS_NOT_DETAILED", this.a3l);
    if (this.a3d != null) {
      XMLHandler.appendTag(localStringBuffer, "DEPOSITS_SUBJECT_TO_FLOAT", this.a3d.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_DEPOSITS_SUBJECT_TO_FLOAT", this.a3j);
    if (this.a2L != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_ADJ_CREDITS_YTD", this.a2L.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_ADJ_CREDITS_YTD", this.a3h);
    if (this.a3f != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_LOCKBOX_DEPOSITS", this.a3f.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_LOCKBOX_DEPOSITS", this.a2V);
    if (this.a2Y != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_DEBITS", this.a2Y.doubleValue());
    }
    if (this.a29 != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_DEBIT_AMT_MTD", this.a29.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_DEBIT_MTD", this.a2X);
    if (this.a28 != null) {
      XMLHandler.appendTag(localStringBuffer, "TODAYS_TOTAL_DEBITS", this.a28.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_TODAYS_TOTAL_DEBITS", this.a3w);
    if (this.a3v != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_DEBITS_LESS_WIRE_AND_CHARGE", this.a3v.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_DEBITS_LESS_WIRE_AND_CHARGE", this.a2P);
    if (this.a27 != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_ADJ_DEBITS_YTD", this.a27.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_ADJ_DEBITS_YTD", this.a3p);
    if (this.a22 != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_DEBITS_EXCLUDE_RETURNS", this.a22.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_DEBITS_EXCLUDE_RETURNS", this.a3e);
    if (this.a3t != null) {
      XMLHandler.appendTag(localStringBuffer, "IMMED_AVAIL_AMT", this.a3t.doubleValue());
    }
    if (this.a3u != null) {
      XMLHandler.appendTag(localStringBuffer, "ONE_DAY_AVAIL_AMT", this.a3u.doubleValue());
    }
    if (this.a3n != null) {
      XMLHandler.appendTag(localStringBuffer, "MORE_THAN_ONE_DAY_AVAIL_AMT", this.a3n.doubleValue());
    }
    if (this.a2R != null) {
      XMLHandler.appendTag(localStringBuffer, "AVAIL_OVERDRAFT", this.a2R.doubleValue());
    }
    if (this.a2W != null) {
      XMLHandler.appendTag(localStringBuffer, "RESTRICTED_CASH", this.a2W.doubleValue());
    }
    if (this.a3g != null) {
      XMLHandler.appendTag(localStringBuffer, "ACCRUED_INTEREST", this.a3g.doubleValue());
    }
    if (this.a20 != null) {
      XMLHandler.appendTag(localStringBuffer, "ACCRUED_DIVIDEND", this.a20.doubleValue());
    }
    if (this.a24 != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_OVERDRAFT_AMT", this.a24.doubleValue());
    }
    if (this.a3o != null) {
      XMLHandler.appendTag(localStringBuffer, "NEXT_OVERDRAFT_PMT_DATE", this.a3o.toXMLFormat());
    }
    if (this.a2S != -1.0F) {
      XMLHandler.appendTag(localStringBuffer, "INTEREST_RATE", getInterestRateString());
    }
    if (this.a2Q != null) {
      XMLHandler.appendTag(localStringBuffer, "OPENING_LEDGER", this.a2Q.doubleValue());
    }
    if (this.a3k != null) {
      XMLHandler.appendTag(localStringBuffer, "CLOSING_LEDGER", this.a3k.doubleValue());
    }
    if (this.a3m != null) {
      XMLHandler.appendTag(localStringBuffer, "CURRENT_AVAIL_BAL", this.a3m.doubleValue());
    }
    if (this.a3a != null) {
      XMLHandler.appendTag(localStringBuffer, "LEDGER_BAL", this.a3a.doubleValue());
    }
    if (this.a25 != null) {
      XMLHandler.appendTag(localStringBuffer, "ONE_DAY_FLOAT", this.a25.doubleValue());
    }
    if (this.a3q != null) {
      XMLHandler.appendTag(localStringBuffer, "TWO_DAY_FLOAT", this.a3q.doubleValue());
    }
    if (this.a3s != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_FLOAT", this.a3s.doubleValue());
    }
    if (this.a3c != null) {
      XMLHandler.appendTag(localStringBuffer, "CURRENT_LEDGER", this.a3c.doubleValue());
    }
    if (this.a3r != null) {
      XMLHandler.appendTag(localStringBuffer, "INTEREST_YTD", this.a3r.doubleValue());
    }
    if (this.a2O != null) {
      XMLHandler.appendTag(localStringBuffer, "PRIOR_YEAR_INTEREST", this.a2O.doubleValue());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "DEPOSIT_ACCT_SUMMARY");
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
  
  public int getTotalNumberOfCredits()
  {
    return this.a3x;
  }
  
  public void setTotalNumberOfCredits(int paramInt)
  {
    this.a3x = paramInt;
  }
  
  public int getTotalNumberOfDebits()
  {
    return this.a2U;
  }
  
  public void setTotalNumberOfDebits(int paramInt)
  {
    this.a2U = paramInt;
  }
  
  public void calculateDisplayBalances(String paramString, BigDecimal paramBigDecimal)
  {
    super.calculateDisplayBalances(paramString, paramBigDecimal);
    this.a3i = convertDisplay(this.a3k);
    this.a2N = convertDisplay(this.a2Q);
    this.a2Z = convertDisplay(this.a3c);
    this.a3b = convertDisplay(this.a2Y);
    this.a23 = convertDisplay(this.a26);
  }
  
  public Currency getDisplayClosingLedger()
  {
    return this.a3i;
  }
  
  public Currency getDisplayOpeningLedger()
  {
    return this.a2N;
  }
  
  public Currency getDisplayTotalCredits()
  {
    return this.a23;
  }
  
  public Currency getDisplayTotalDebits()
  {
    return this.a3b;
  }
  
  public Currency getDisplayCurrentLedger()
  {
    return this.a2Z;
  }
  
  public String getCurrencyCode(Accounts paramAccounts)
  {
    String str = super.getCurrencyCode(paramAccounts);
    if (str != null) {
      return str;
    }
    if (this.a26 != null) {
      return this.a26.getCurrencyCode();
    }
    if (this.a2M != null) {
      return this.a2M.getCurrencyCode();
    }
    if (this.a21 != null) {
      return this.a21.getCurrencyCode();
    }
    if (this.a3d != null) {
      return this.a3d.getCurrencyCode();
    }
    if (this.a2L != null) {
      return this.a2L.getCurrencyCode();
    }
    if (this.a3f != null) {
      return this.a3f.getCurrencyCode();
    }
    if (this.a2Y != null) {
      return this.a2Y.getCurrencyCode();
    }
    if (this.a29 != null) {
      return this.a29.getCurrencyCode();
    }
    if (this.a28 != null) {
      return this.a28.getCurrencyCode();
    }
    if (this.a3v != null) {
      return this.a3v.getCurrencyCode();
    }
    if (this.a27 != null) {
      return this.a27.getCurrencyCode();
    }
    if (this.a22 != null) {
      return this.a22.getCurrencyCode();
    }
    if (this.a3t != null) {
      return this.a3t.getCurrencyCode();
    }
    if (this.a3u != null) {
      return this.a3u.getCurrencyCode();
    }
    if (this.a3n != null) {
      return this.a3n.getCurrencyCode();
    }
    if (this.a2R != null) {
      return this.a2R.getCurrencyCode();
    }
    if (this.a2W != null) {
      return this.a2W.getCurrencyCode();
    }
    if (this.a3g != null) {
      return this.a3g.getCurrencyCode();
    }
    if (this.a20 != null) {
      return this.a20.getCurrencyCode();
    }
    if (this.a24 != null) {
      return this.a24.getCurrencyCode();
    }
    if (this.a2Q != null) {
      return this.a2Q.getCurrencyCode();
    }
    if (this.a3k != null) {
      return this.a3k.getCurrencyCode();
    }
    if (this.a3m != null) {
      return this.a3m.getCurrencyCode();
    }
    if (this.a3a != null) {
      return this.a3a.getCurrencyCode();
    }
    if (this.a25 != null) {
      return this.a25.getCurrencyCode();
    }
    if (this.a3q != null) {
      return this.a3q.getCurrencyCode();
    }
    if (this.a3s != null) {
      return this.a3s.getCurrencyCode();
    }
    if (this.a3c != null) {
      return this.a3c.getCurrencyCode();
    }
    if (this.a3r != null) {
      return this.a3r.getCurrencyCode();
    }
    if (this.a2O != null) {
      return this.a2O.getCurrencyCode();
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.DepositAcctSummary
 * JD-Core Version:    0.7.0.1
 */