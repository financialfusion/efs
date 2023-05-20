package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.math.BigDecimal;
import java.text.Collator;
import java.text.NumberFormat;
import java.util.Locale;

public class LoanAcctSummary
  extends AccountSummary
{
  public static final String LOAN_ACCT_SUMMARY = "LOAN_ACCT_SUMMARY";
  public static final String AVAIL_CREDIT = "AVAIL_CREDIT";
  public static final String AMT_DUE = "AMT_DUE";
  public static final String INTEREST_RATE = "INTEREST_RATE";
  public static final String DUE_DATE = "DUE_DATE";
  public static final String MATURITY_DATE = "MATURITY_DATE";
  public static final String ACCRUED_INTEREST = "ACCRUED_INTEREST";
  public static final String OPENING_BAL = "OPENING_BAL";
  public static final String COLLATERAL_DESCRIPTION = "COLLATERAL_DESCRIPTION";
  public static final String PRINCIPAL_PAST_DUE = "PRINCIPAL_PAST_DUE";
  public static final String INTEREST_PAST_DUE = "INTEREST_PAST_DUE";
  public static final String LATE_FEES = "LATE_FEES";
  public static final String NEXT_PRINCIPAL_AMT = "NEXT_PRINCIPAL_AMT";
  public static final String NEXT_INTEREST_AMT = "NEXT_INTEREST_AMT";
  public static final String OPEN_DATE = "OPEN_DATE";
  public static final String CURRENT_BALANCE = "CURRENT_BALANCE";
  public static final String NEXT_PAYMENT_DATE = "NEXT_PAYMENT_DATE";
  public static final String NEXT_PAYMENT_AMT = "NEXT_PAYMENT_AMT";
  public static final String INTEREST_YTD = "INTEREST_YTD";
  public static final String PRIOR_YEAR_INTEREST = "PRIOR_YEAR_INTEREST";
  public static final String LOAN_TERM = "LOAN_TERM";
  public static final String TODAYS_PAYOFF = "TODAYS_PAYOFF";
  public static final String PAYOFF_GOOD_THRU = "PAYOFF_GOOD_THRU";
  private Currency a2w;
  private Currency a2D;
  private float a2n = -1.0F;
  private DateTime a2E;
  private DateTime a2C;
  private Currency a2A;
  private Currency a2K;
  private String a2p;
  private Currency a2y;
  private Currency a2t;
  private Currency a2F;
  private Currency a2q;
  private Currency a2H;
  private DateTime a2r;
  private Currency a2G;
  private DateTime a2o;
  private Currency a2v;
  private Currency a2u;
  private Currency a2J;
  private String a2z;
  private Currency a2B;
  private DateTime a2I;
  private Currency a2x;
  private Currency a2m;
  private Currency a2s;
  
  public Currency getAvailCredit()
  {
    return this.a2w;
  }
  
  public void setAvailCredit(Currency paramCurrency)
  {
    this.a2w = paramCurrency;
  }
  
  public void setAvailCredit(String paramString)
  {
    if (this.a2w == null) {
      this.a2w = new Currency(paramString, this.locale);
    } else {
      this.a2w.fromString(paramString);
    }
  }
  
  public Currency getAmtDue()
  {
    return this.a2D;
  }
  
  public void setAmtDue(Currency paramCurrency)
  {
    this.a2D = paramCurrency;
  }
  
  public void setAmtDue(String paramString)
  {
    if (this.a2D == null) {
      this.a2D = new Currency(paramString, this.locale);
    } else {
      this.a2D.fromString(paramString);
    }
  }
  
  public float getInterestRate()
  {
    return this.a2n;
  }
  
  public String getInterestRateString()
  {
    if (this.a2n == -1.0F) {
      return null;
    }
    NumberFormat localNumberFormat = NumberFormat.getNumberInstance(this.locale);
    localNumberFormat.setMinimumFractionDigits(5);
    localNumberFormat.setMaximumFractionDigits(5);
    return localNumberFormat.format(this.a2n);
  }
  
  public void setInterestRate(float paramFloat)
  {
    this.a2n = paramFloat;
  }
  
  public DateTime getDueDate()
  {
    return this.a2E;
  }
  
  public void setDueDate(DateTime paramDateTime)
  {
    this.a2E = paramDateTime;
  }
  
  public void setDueDate(String paramString)
  {
    try
    {
      if (this.a2E == null) {
        this.a2E = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a2E.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getMaturityDate()
  {
    return this.a2C;
  }
  
  public void setMaturityDate(DateTime paramDateTime)
  {
    this.a2C = paramDateTime;
  }
  
  public void setMaturityDate(String paramString)
  {
    try
    {
      if (this.a2C == null) {
        this.a2C = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a2C.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Currency getAccruedInterest()
  {
    return this.a2A;
  }
  
  public void setAccruedInterest(Currency paramCurrency)
  {
    this.a2A = paramCurrency;
  }
  
  public void setAccruedInterest(String paramString)
  {
    if (this.a2A == null) {
      this.a2A = new Currency(paramString, this.locale);
    } else {
      this.a2A.fromString(paramString);
    }
  }
  
  public Currency getOpeningBal()
  {
    return this.a2K;
  }
  
  public void setOpeningBal(Currency paramCurrency)
  {
    this.a2K = paramCurrency;
  }
  
  public void setOpeningBal(String paramString)
  {
    if (this.a2K == null) {
      this.a2K = new Currency(paramString, this.locale);
    } else {
      this.a2K.fromString(paramString);
    }
  }
  
  public String getCollateralDescription()
  {
    return this.a2p;
  }
  
  public void setCollateralDescription(String paramString)
  {
    this.a2p = paramString;
  }
  
  public Currency getPrincipalPastDue()
  {
    return this.a2y;
  }
  
  public void setPrincipalPastDue(Currency paramCurrency)
  {
    this.a2y = paramCurrency;
  }
  
  public void setPrincipalPastDue(String paramString)
  {
    if (this.a2y == null) {
      this.a2y = new Currency(paramString, this.locale);
    } else {
      this.a2y.fromString(paramString);
    }
  }
  
  public Currency getInterestPastDue()
  {
    return this.a2t;
  }
  
  public void setInterestPastDue(Currency paramCurrency)
  {
    this.a2t = paramCurrency;
  }
  
  public void setInterestPastDue(String paramString)
  {
    if (this.a2t == null) {
      this.a2t = new Currency(paramString, this.locale);
    } else {
      this.a2t.fromString(paramString);
    }
  }
  
  public Currency getLateFees()
  {
    return this.a2F;
  }
  
  public void setLateFees(Currency paramCurrency)
  {
    this.a2F = paramCurrency;
  }
  
  public void setLateFees(String paramString)
  {
    if (this.a2F == null) {
      this.a2F = new Currency(paramString, this.locale);
    } else {
      this.a2F.fromString(paramString);
    }
  }
  
  public Currency getNextPrincipalAmt()
  {
    return this.a2q;
  }
  
  public void setNextPrincipalAmt(Currency paramCurrency)
  {
    this.a2q = paramCurrency;
  }
  
  public void setNextPrincipalAmt(String paramString)
  {
    if (this.a2q == null) {
      this.a2q = new Currency(paramString, this.locale);
    } else {
      this.a2q.fromString(paramString);
    }
  }
  
  public Currency getNextInterestAmt()
  {
    return this.a2H;
  }
  
  public void setNextInterestAmt(Currency paramCurrency)
  {
    this.a2H = paramCurrency;
  }
  
  public void setNextInterestAmt(String paramString)
  {
    if (this.a2H == null) {
      this.a2H = new Currency(paramString, this.locale);
    } else {
      this.a2H.fromString(paramString);
    }
  }
  
  public DateTime getOpenDate()
  {
    return this.a2r;
  }
  
  public void setOpenDate(DateTime paramDateTime)
  {
    this.a2r = paramDateTime;
  }
  
  public void setOpenDate(String paramString)
  {
    try
    {
      if (this.a2r == null) {
        this.a2r = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a2r.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Currency getCurrentBalance()
  {
    return this.a2G;
  }
  
  public void setCurrentBalance(Currency paramCurrency)
  {
    this.a2G = paramCurrency;
  }
  
  public void setCurrentBalance(String paramString)
  {
    if (this.a2G == null) {
      this.a2G = new Currency(paramString, this.locale);
    } else {
      this.a2G.fromString(paramString);
    }
  }
  
  public DateTime getNextPaymentDate()
  {
    return this.a2o;
  }
  
  public void setNextPaymentDate(DateTime paramDateTime)
  {
    this.a2o = paramDateTime;
  }
  
  public void setNextPaymentDate(String paramString)
  {
    try
    {
      if (this.a2o == null) {
        this.a2o = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a2o.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Currency getNextPaymentAmt()
  {
    return this.a2v;
  }
  
  public void setNextPaymentAmt(Currency paramCurrency)
  {
    this.a2v = paramCurrency;
  }
  
  public void setNextPaymentAmt(String paramString)
  {
    if (this.a2v == null) {
      this.a2v = new Currency(paramString, this.locale);
    } else {
      this.a2v.fromString(paramString);
    }
  }
  
  public Currency getInterestYTD()
  {
    return this.a2u;
  }
  
  public void setInterestYTD(Currency paramCurrency)
  {
    this.a2u = paramCurrency;
  }
  
  public void setInterestYTD(String paramString)
  {
    if (this.a2u == null) {
      this.a2u = new Currency(paramString, this.locale);
    } else {
      this.a2u.fromString(paramString);
    }
  }
  
  public Currency getPriorYearInterest()
  {
    return this.a2J;
  }
  
  public void setPriorYearInterest(Currency paramCurrency)
  {
    this.a2J = paramCurrency;
  }
  
  public void setPriorYearInterest(String paramString)
  {
    if (this.a2J == null) {
      this.a2J = new Currency(paramString, this.locale);
    } else {
      this.a2J.fromString(paramString);
    }
  }
  
  public String getLoanTerm()
  {
    return this.a2z;
  }
  
  public void setLoanTerm(String paramString)
  {
    this.a2z = paramString;
  }
  
  public Currency getTodaysPayoff()
  {
    return this.a2B;
  }
  
  public void setTodaysPayoff(Currency paramCurrency)
  {
    this.a2B = paramCurrency;
  }
  
  public void setTodaysPayoff(String paramString)
  {
    if (this.a2B == null) {
      this.a2B = new Currency(paramString, this.locale);
    } else {
      this.a2B.fromString(paramString);
    }
  }
  
  public DateTime getPayoffGoodThru()
  {
    return this.a2I;
  }
  
  public void setPayoffGoodThru(DateTime paramDateTime)
  {
    this.a2I = paramDateTime;
  }
  
  public void setPayoffGoodThru(String paramString)
  {
    try
    {
      if (this.a2I == null) {
        this.a2I = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a2I.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a2w != null) {
      this.a2w.setLocale(paramLocale);
    }
    if (this.a2D != null) {
      this.a2D.setLocale(paramLocale);
    }
    if (this.a2E != null) {
      this.a2E.setLocale(paramLocale);
    }
    if (this.a2C != null) {
      this.a2C.setLocale(paramLocale);
    }
    if (this.a2A != null) {
      this.a2A.setLocale(paramLocale);
    }
    if (this.a2K != null) {
      this.a2K.setLocale(paramLocale);
    }
    if (this.a2y != null) {
      this.a2y.setLocale(paramLocale);
    }
    if (this.a2t != null) {
      this.a2t.setLocale(paramLocale);
    }
    if (this.a2F != null) {
      this.a2F.setLocale(paramLocale);
    }
    if (this.a2q != null) {
      this.a2q.setLocale(paramLocale);
    }
    if (this.a2H != null) {
      this.a2H.setLocale(paramLocale);
    }
    if (this.a2r != null) {
      this.a2r.setLocale(paramLocale);
    }
    if (this.a2G != null) {
      this.a2G.setLocale(paramLocale);
    }
    if (this.a2o != null) {
      this.a2o.setLocale(paramLocale);
    }
    if (this.a2v != null) {
      this.a2v.setLocale(paramLocale);
    }
    if (this.a2u != null) {
      this.a2u.setLocale(paramLocale);
    }
    if (this.a2J != null) {
      this.a2J.setLocale(paramLocale);
    }
    if (this.a2B != null) {
      this.a2B.setLocale(paramLocale);
    }
    if (this.a2I != null) {
      this.a2I.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.a2E != null) {
      this.a2E.setFormat(paramString);
    }
    if (this.a2C != null) {
      this.a2C.setFormat(paramString);
    }
    if (this.a2r != null) {
      this.a2r.setFormat(paramString);
    }
    if (this.a2o != null) {
      this.a2o.setFormat(paramString);
    }
    if (this.a2I != null) {
      this.a2I.setFormat(paramString);
    }
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    LoanAcctSummary localLoanAcctSummary = (LoanAcctSummary)paramObject;
    int i = 1;
    if (paramString.equals("AVAIL_CREDIT")) {
      i = Currency.compare(this.a2w, localLoanAcctSummary.getAvailCredit());
    } else if (paramString.equals("AMT_DUE")) {
      i = Currency.compare(this.a2D, localLoanAcctSummary.getAmtDue());
    } else if ((paramString.equals("INTEREST_RATE")) && (this.a2n != -1.0F) && (localLoanAcctSummary.a2n != -1.0F)) {
      i = this.a2n == localLoanAcctSummary.getInterestRate() ? 0 : this.a2n < localLoanAcctSummary.getInterestRate() ? -1 : 1;
    } else if ((paramString.equals("DUE_DATE")) && (this.a2E != null) && (localLoanAcctSummary.getDueDate() != null)) {
      i = this.a2E.compare(localLoanAcctSummary.getDueDate(), paramString);
    } else if ((paramString.equals("MATURITY_DATE")) && (this.a2C != null) && (localLoanAcctSummary.getMaturityDate() != null)) {
      i = this.a2C.compare(localLoanAcctSummary.getMaturityDate(), paramString);
    } else if ((paramString.equals("ACCRUED_INTEREST")) && (this.a2A != null) && (localLoanAcctSummary.getAccruedInterest() != null)) {
      i = this.a2A.compareTo(localLoanAcctSummary.getAccruedInterest());
    } else if ((paramString.equals("OPENING_BAL")) && (this.a2K != null) && (localLoanAcctSummary.getOpeningBal() != null)) {
      i = this.a2K.compareTo(localLoanAcctSummary.getOpeningBal());
    } else if ((paramString.equals("COLLATERAL_DESCRIPTION")) && (this.a2p != null) && (localLoanAcctSummary.getCollateralDescription() != null)) {
      i = localCollator.compare(this.a2p, localLoanAcctSummary.getCollateralDescription());
    } else if ((paramString.equals("PRINCIPAL_PAST_DUE")) && (this.a2y != null) && (localLoanAcctSummary.getPrincipalPastDue() != null)) {
      i = this.a2y.compareTo(localLoanAcctSummary.getPrincipalPastDue());
    } else if ((paramString.equals("INTEREST_PAST_DUE")) && (this.a2t != null) && (localLoanAcctSummary.getInterestPastDue() != null)) {
      i = this.a2t.compareTo(localLoanAcctSummary.getInterestPastDue());
    } else if ((paramString.equals("LATE_FEES")) && (this.a2F != null) && (localLoanAcctSummary.getLateFees() != null)) {
      i = this.a2F.compareTo(localLoanAcctSummary.getLateFees());
    } else if ((paramString.equals("NEXT_PRINCIPAL_AMT")) && (this.a2q != null) && (localLoanAcctSummary.getNextPrincipalAmt() != null)) {
      i = this.a2q.compareTo(localLoanAcctSummary.getNextPrincipalAmt());
    } else if ((paramString.equals("NEXT_INTEREST_AMT")) && (this.a2H != null) && (localLoanAcctSummary.getNextInterestAmt() != null)) {
      i = this.a2H.compareTo(localLoanAcctSummary.getNextInterestAmt());
    } else if ((paramString.equals("OPEN_DATE")) && (this.a2r != null) && (localLoanAcctSummary.getOpenDate() != null)) {
      i = this.a2r.compare(localLoanAcctSummary.getOpenDate(), paramString);
    } else if ((paramString.equals("CURRENT_BALANCE")) && (this.a2G != null) && (localLoanAcctSummary.getCurrentBalance() != null)) {
      i = this.a2G.compareTo(localLoanAcctSummary.getCurrentBalance());
    } else if ((paramString.equals("NEXT_PAYMENT_DATE")) && (this.a2o != null) && (localLoanAcctSummary.getNextPaymentDate() != null)) {
      i = this.a2o.compare(localLoanAcctSummary.getNextPaymentDate(), paramString);
    } else if ((paramString.equals("NEXT_PAYMENT_AMT")) && (this.a2v != null) && (localLoanAcctSummary.getNextPaymentAmt() != null)) {
      i = this.a2v.compareTo(localLoanAcctSummary.getNextPaymentAmt());
    } else if ((paramString.equals("INTEREST_YTD")) && (this.a2u != null) && (localLoanAcctSummary.getInterestYTD() != null)) {
      i = this.a2u.compareTo(localLoanAcctSummary.getInterestYTD());
    } else if ((paramString.equals("PRIOR_YEAR_INTEREST")) && (this.a2J != null) && (localLoanAcctSummary.getPriorYearInterest() != null)) {
      i = this.a2J.compareTo(localLoanAcctSummary.getPriorYearInterest());
    } else if ((paramString.equals("LOAN_TERM")) && (this.a2z != null) && (localLoanAcctSummary.getLoanTerm() != null)) {
      i = localCollator.compare(this.a2z, localLoanAcctSummary.getLoanTerm());
    } else if ((paramString.equals("TODAYS_PAYOFF")) && (this.a2B != null) && (localLoanAcctSummary.getTodaysPayoff() != null)) {
      i = this.a2B.compareTo(localLoanAcctSummary.getTodaysPayoff());
    } else if ((paramString.equals("PAYOFF_GOOD_THRU")) && (this.a2I != null) && (localLoanAcctSummary.getPayoffGoodThru() != null)) {
      i = this.a2I.compare(localLoanAcctSummary.getPayoffGoodThru(), paramString);
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("AVAIL_CREDIT")) && (this.a2w != null)) {
      return this.a2w.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AMT_DUE")) && (this.a2D != null)) {
      return this.a2D.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("INTEREST_RATE")) && (this.a2n != -1.0F)) {
      return isFilterable(Float.toString(this.a2n), paramString2, Float.valueOf(paramString3));
    }
    if ((paramString1.equals("DUE_DATE")) && (this.a2E != null)) {
      return this.a2E.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("MATURITY_DATE")) && (this.a2C != null)) {
      return this.a2C.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("ACCRUED_INTEREST")) && (this.a2A != null)) {
      return this.a2A.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("OPENING_BAL")) && (this.a2K != null)) {
      return this.a2K.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("COLLATERAL_DESCRIPTION")) && (this.a2p != null)) {
      return isFilterable(this.a2p, paramString2, paramString3);
    }
    if ((paramString1.equals("PRINCIPAL_PAST_DUE")) && (this.a2y != null)) {
      return this.a2y.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("INTEREST_PAST_DUE")) && (this.a2t != null)) {
      return this.a2t.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("LATE_FEES")) && (this.a2F != null)) {
      return this.a2F.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("NEXT_PRINCIPAL_AMT")) && (this.a2q != null)) {
      return this.a2q.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("NEXT_INTEREST_AMT")) && (this.a2H != null)) {
      return this.a2H.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("OPEN_DATE")) && (this.a2r != null)) {
      return this.a2r.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("CURRENT_BALANCE")) && (this.a2G != null)) {
      return this.a2G.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("NEXT_PAYMENT_DATE")) && (this.a2o != null)) {
      return this.a2o.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("NEXT_PAYMENT_AMT")) && (this.a2v != null)) {
      return this.a2v.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("INTEREST_YTD")) && (this.a2u != null)) {
      return this.a2u.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("PRIOR_YEAR_INTEREST")) && (this.a2J != null)) {
      return this.a2J.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("LOAN_TERM")) && (this.a2z != null)) {
      return isFilterable(this.a2z, paramString2, paramString3);
    }
    if ((paramString1.equals("TODAYS_PAYOFF")) && (this.a2B != null)) {
      return this.a2B.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("PAYOFF_GOOD_THRU")) && (this.a2I != null)) {
      return this.a2I.isFilterable(paramString1 + paramString2 + paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("AVAIL_CREDIT"))
    {
      setAvailCredit(paramString2);
    }
    else if (paramString1.equals("AMT_DUE"))
    {
      setAmtDue(paramString2);
    }
    else if (paramString1.equals("INTEREST_RATE"))
    {
      this.a2n = Float.parseFloat(paramString2);
    }
    else if (paramString1.equals("DUE_DATE"))
    {
      if (this.a2E == null)
      {
        this.a2E = new DateTime(this.locale);
        this.a2E.setFormat(this.datetype);
      }
      this.a2E.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("MATURITY_DATE"))
    {
      if (this.a2C == null)
      {
        this.a2C = new DateTime(this.locale);
        this.a2C.setFormat(this.datetype);
      }
      this.a2C.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("ACCRUED_INTEREST"))
    {
      setAccruedInterest(paramString2);
    }
    else if (paramString1.equals("OPENING_BAL"))
    {
      setOpeningBal(paramString2);
    }
    else if (paramString1.equals("COLLATERAL_DESCRIPTION"))
    {
      this.a2p = paramString2;
    }
    else if (paramString1.equals("PRINCIPAL_PAST_DUE"))
    {
      setPrincipalPastDue(paramString2);
    }
    else if (paramString1.equals("INTEREST_PAST_DUE"))
    {
      setInterestPastDue(paramString2);
    }
    else if (paramString1.equals("LATE_FEES"))
    {
      setLateFees(paramString2);
    }
    else if (paramString1.equals("NEXT_PRINCIPAL_AMT"))
    {
      setNextPrincipalAmt(paramString2);
    }
    else if (paramString1.equals("NEXT_INTEREST_AMT"))
    {
      setNextInterestAmt(paramString2);
    }
    else if (paramString1.equals("OPEN_DATE"))
    {
      if (this.a2r == null)
      {
        this.a2r = new DateTime(this.locale);
        this.a2r.setFormat(this.datetype);
      }
      this.a2r.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("CURRENT_BALANCE"))
    {
      setCurrentBalance(paramString2);
    }
    else if (paramString1.equals("NEXT_PAYMENT_DATE"))
    {
      if (this.a2o == null)
      {
        this.a2o = new DateTime(this.locale);
        this.a2o.setFormat(this.datetype);
      }
      this.a2o.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("NEXT_PAYMENT_AMT"))
    {
      setNextPaymentAmt(paramString2);
    }
    else if (paramString1.equals("INTEREST_YTD"))
    {
      setInterestYTD(paramString2);
    }
    else if (paramString1.equals("PRIOR_YEAR_INTEREST"))
    {
      setPriorYearInterest(paramString2);
    }
    else if (paramString1.equals("LOAN_TERM"))
    {
      setLoanTerm(paramString2);
    }
    else if (paramString1.equals("TODAYS_PAYOFF"))
    {
      setTodaysPayoff(paramString2);
    }
    else if (paramString1.equals("PAYOFF_GOOD_THRU"))
    {
      if (this.a2I == null)
      {
        this.a2I = new DateTime(this.locale);
        this.a2I.setFormat(this.datetype);
      }
      this.a2I.fromXMLFormat(paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOAN_ACCT_SUMMARY");
    if (this.a2w != null) {
      XMLHandler.appendTag(localStringBuffer, "AVAIL_CREDIT", this.a2w.doubleValue());
    }
    if (this.a2D != null) {
      XMLHandler.appendTag(localStringBuffer, "AMT_DUE", this.a2D.doubleValue());
    }
    if (this.a2n != -1.0F) {
      XMLHandler.appendTag(localStringBuffer, "INTEREST_RATE", getInterestRateString());
    }
    if (this.a2E != null) {
      XMLHandler.appendTag(localStringBuffer, "DUE_DATE", this.a2E.toXMLFormat());
    }
    if (this.a2C != null) {
      XMLHandler.appendTag(localStringBuffer, "MATURITY_DATE", this.a2C.toXMLFormat());
    }
    if (this.a2A != null) {
      XMLHandler.appendTag(localStringBuffer, "ACCRUED_INTEREST", this.a2A.doubleValue());
    }
    if (this.a2K != null) {
      XMLHandler.appendTag(localStringBuffer, "OPENING_BAL", this.a2K.doubleValue());
    }
    if (this.a2p != null) {
      XMLHandler.appendTag(localStringBuffer, "COLLATERAL_DESCRIPTION", this.a2p);
    }
    if (this.a2y != null) {
      XMLHandler.appendTag(localStringBuffer, "PRINCIPAL_PAST_DUE", this.a2y.doubleValue());
    }
    if (this.a2t != null) {
      XMLHandler.appendTag(localStringBuffer, "INTEREST_PAST_DUE", this.a2t.doubleValue());
    }
    if (this.a2F != null) {
      XMLHandler.appendTag(localStringBuffer, "LATE_FEES", this.a2F.doubleValue());
    }
    if (this.a2q != null) {
      XMLHandler.appendTag(localStringBuffer, "NEXT_PRINCIPAL_AMT", this.a2q.doubleValue());
    }
    if (this.a2H != null) {
      XMLHandler.appendTag(localStringBuffer, "NEXT_INTEREST_AMT", this.a2H.doubleValue());
    }
    if (this.a2r != null) {
      XMLHandler.appendTag(localStringBuffer, "OPEN_DATE", this.a2r.toXMLFormat());
    }
    if (this.a2G != null) {
      XMLHandler.appendTag(localStringBuffer, "CURRENT_BALANCE", this.a2G.doubleValue());
    }
    if (this.a2o != null) {
      XMLHandler.appendTag(localStringBuffer, "NEXT_PAYMENT_DATE", this.a2o.toXMLFormat());
    }
    if (this.a2v != null) {
      XMLHandler.appendTag(localStringBuffer, "NEXT_PAYMENT_AMT", this.a2v.doubleValue());
    }
    if (this.a2u != null) {
      XMLHandler.appendTag(localStringBuffer, "INTEREST_YTD", this.a2u.doubleValue());
    }
    if (this.a2J != null) {
      XMLHandler.appendTag(localStringBuffer, "PRIOR_YEAR_INTEREST", this.a2J.doubleValue());
    }
    if (this.a2z != null) {
      XMLHandler.appendTag(localStringBuffer, "LOAN_TERM", this.a2z);
    }
    if (this.a2B != null) {
      XMLHandler.appendTag(localStringBuffer, "TODAYS_PAYOFF", this.a2B.doubleValue());
    }
    if (this.a2I != null) {
      XMLHandler.appendTag(localStringBuffer, "PAYOFF_GOOD_THRU", this.a2I.toXMLFormat());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "LOAN_ACCT_SUMMARY");
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
  
  public void calculateDisplayBalances(String paramString, BigDecimal paramBigDecimal)
  {
    super.calculateDisplayBalances(paramString, paramBigDecimal);
    this.a2s = convertDisplay(this.a2D);
    this.a2m = convertDisplay(this.a2w);
    this.a2x = convertDisplay(this.a2G);
  }
  
  public Currency getDisplayAmtDue()
  {
    return this.a2s;
  }
  
  public Currency getDisplayAvailCredit()
  {
    return this.a2m;
  }
  
  public Currency getDisplayCurrentBalance()
  {
    return this.a2x;
  }
  
  public String getCurrencyCode(Accounts paramAccounts)
  {
    String str = super.getCurrencyCode(paramAccounts);
    if (str != null) {
      return str;
    }
    if (this.a2A != null) {
      return this.a2A.getCurrencyCode();
    }
    if (this.a2K != null) {
      return this.a2K.getCurrencyCode();
    }
    if (this.a2y != null) {
      return this.a2y.getCurrencyCode();
    }
    if (this.a2t != null) {
      return this.a2t.getCurrencyCode();
    }
    if (this.a2F != null) {
      return this.a2F.getCurrencyCode();
    }
    if (this.a2q != null) {
      return this.a2q.getCurrencyCode();
    }
    if (this.a2A != null) {
      return this.a2A.getCurrencyCode();
    }
    if (this.a2A != null) {
      return this.a2A.getCurrencyCode();
    }
    if (this.a2H != null) {
      return this.a2H.getCurrencyCode();
    }
    if (this.a2G != null) {
      return this.a2G.getCurrencyCode();
    }
    if (this.a2v != null) {
      return this.a2v.getCurrencyCode();
    }
    if (this.a2u != null) {
      return this.a2u.getCurrencyCode();
    }
    if (this.a2J != null) {
      return this.a2J.getCurrencyCode();
    }
    if (this.a2B != null) {
      return this.a2B.getCurrencyCode();
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.LoanAcctSummary
 * JD-Core Version:    0.7.0.1
 */