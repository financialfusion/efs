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

public class CreditCardAcctSummary
  extends AccountSummary
{
  public static final String CREDIT_CARD_ACCT_SUMMARY = "CREDIT_CARD_ACCT_SUMMARY";
  public static final String AVAIL_CREDIT = "AVAIL_CREDIT";
  public static final String AMT_DUE = "AMT_DUE";
  public static final String INTEREST_RATE = "INTEREST_RATE";
  public static final String DUE_DATE = "DUE_DATE";
  public static final String CARD_HOLDER_NAME = "CARD_HOLDER_NAME";
  public static final String CARD_EXP_DATE = "CARD_EXP_DATE";
  public static final String CREDIT_LIMIT = "CREDIT_LIMIT";
  public static final String LAST_PAYMENT_AMT = "LAST_PAYMENT_AMT";
  public static final String NEXT_PAYMENT_MIN_AMT = "NEXT_PAYMENT_MIN_AMT";
  public static final String NEXT_PAYMENT_DUE = "NEXT_PAYMENT_DUE";
  public static final String LAST_PAYMENT_DATE = "LAST_PAYMENT_DATE";
  public static final String CURRENT_BALANCE = "CURRENT_BALANCE";
  public static final String LAST_ADVANCE_DATE = "LAST_ADVANCE_DATE";
  public static final String LAST_ADVANCE_AMT = "LAST_ADVANCE_AMT";
  public static final String PAYOFF_AMOUNT = "PAYOFF_AMOUNT";
  private Currency a2d;
  private Currency a2f;
  private float a2l = -1.0F;
  private DateTime a2k;
  private String a2a;
  private DateTime a2b;
  private Currency a2i;
  private Currency a17;
  private Currency a2e;
  private DateTime a19;
  private DateTime a2j;
  private Currency a2c;
  private DateTime a16;
  private Currency a18;
  private Currency a2h;
  private Currency a14;
  private Currency a15;
  private Currency a2g;
  
  public Currency getAvailCredit()
  {
    return this.a2d;
  }
  
  public void setAvailCredit(Currency paramCurrency)
  {
    this.a2d = paramCurrency;
  }
  
  public void setAvailCredit(String paramString)
  {
    if (this.a2d == null) {
      this.a2d = new Currency(paramString, this.locale);
    } else {
      this.a2d.fromString(paramString);
    }
  }
  
  public Currency getAmtDue()
  {
    return this.a2f;
  }
  
  public void setAmtDue(Currency paramCurrency)
  {
    this.a2f = paramCurrency;
  }
  
  public void setAmtDue(String paramString)
  {
    if (this.a2f == null) {
      this.a2f = new Currency(paramString, this.locale);
    } else {
      this.a2f.fromString(paramString);
    }
  }
  
  public float getInterestRate()
  {
    return this.a2l;
  }
  
  public String getInterestRateString()
  {
    if (this.a2l == -1.0F) {
      return null;
    }
    NumberFormat localNumberFormat = NumberFormat.getNumberInstance(this.locale);
    localNumberFormat.setMinimumFractionDigits(5);
    localNumberFormat.setMaximumFractionDigits(5);
    return localNumberFormat.format(this.a2l);
  }
  
  public void setInterestRate(float paramFloat)
  {
    this.a2l = paramFloat;
  }
  
  public DateTime getDueDate()
  {
    return this.a2k;
  }
  
  public void setDueDate(DateTime paramDateTime)
  {
    this.a2k = paramDateTime;
  }
  
  public void setDueDate(String paramString)
  {
    try
    {
      if (this.a2k == null) {
        this.a2k = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a2k.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public String getCardHolderName()
  {
    return this.a2a;
  }
  
  public void setCardHolderName(String paramString)
  {
    this.a2a = paramString;
  }
  
  public DateTime getCardExpDate()
  {
    return this.a2b;
  }
  
  public void setCardExpDate(DateTime paramDateTime)
  {
    this.a2b = paramDateTime;
  }
  
  public void setCardExpDate(String paramString)
  {
    try
    {
      if (this.a2b == null) {
        this.a2b = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a2b.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Currency getCreditLimit()
  {
    return this.a2i;
  }
  
  public void setCreditLimit(Currency paramCurrency)
  {
    this.a2i = paramCurrency;
  }
  
  public void setCreditLimit(String paramString)
  {
    if (this.a2i == null) {
      this.a2i = new Currency(paramString, this.locale);
    } else {
      this.a2i.fromString(paramString);
    }
  }
  
  public Currency getLastPaymentAmt()
  {
    return this.a17;
  }
  
  public void setLastPaymentAmt(Currency paramCurrency)
  {
    this.a17 = paramCurrency;
  }
  
  public void setLastPaymentAmt(String paramString)
  {
    if (this.a17 == null) {
      this.a17 = new Currency(paramString, this.locale);
    } else {
      this.a17.fromString(paramString);
    }
  }
  
  public Currency getNextPaymentMinAmt()
  {
    return this.a2e;
  }
  
  public void setNextPaymentMinAmt(Currency paramCurrency)
  {
    this.a2e = paramCurrency;
  }
  
  public void setNextPaymentMinAmt(String paramString)
  {
    if (this.a17 == null) {
      this.a17 = new Currency(paramString, this.locale);
    } else {
      this.a17.fromString(paramString);
    }
  }
  
  public DateTime getNextPaymentDue()
  {
    return this.a19;
  }
  
  public void setNextPaymentDue(DateTime paramDateTime)
  {
    this.a19 = paramDateTime;
  }
  
  public void setNextPaymentDue(String paramString)
  {
    try
    {
      if (this.a19 == null) {
        this.a19 = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a19.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getLastPaymentDate()
  {
    return this.a2j;
  }
  
  public void setLastPaymentDate(DateTime paramDateTime)
  {
    this.a2j = paramDateTime;
  }
  
  public void setLastPaymentDate(String paramString)
  {
    try
    {
      if (this.a2j == null) {
        this.a2j = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a2j.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Currency getCurrentBalance()
  {
    return this.a2c;
  }
  
  public void setCurrentBalance(Currency paramCurrency)
  {
    this.a2c = paramCurrency;
  }
  
  public void setCurrentBalance(String paramString)
  {
    if (this.a2c == null) {
      this.a2c = new Currency(paramString, this.locale);
    } else {
      this.a2c.fromString(paramString);
    }
  }
  
  public DateTime getLastAdvanceDate()
  {
    return this.a16;
  }
  
  public void setLastAdvanceDate(DateTime paramDateTime)
  {
    this.a16 = paramDateTime;
  }
  
  public void setLastAdvanceDate(String paramString)
  {
    try
    {
      if (this.a16 == null) {
        this.a16 = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a16.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public Currency getLastAdvanceAmt()
  {
    return this.a18;
  }
  
  public void setLastAdvanceAmt(Currency paramCurrency)
  {
    this.a18 = paramCurrency;
  }
  
  public void setLastAdvanceAmt(String paramString)
  {
    if (this.a18 == null) {
      this.a18 = new Currency(paramString, this.locale);
    } else {
      this.a18.fromString(paramString);
    }
  }
  
  public Currency getPayoffAmount()
  {
    return this.a2h;
  }
  
  public void setPayoffAmount(Currency paramCurrency)
  {
    this.a2h = paramCurrency;
  }
  
  public void setPayoffAmount(String paramString)
  {
    if (this.a2h == null) {
      this.a2h = new Currency(paramString, this.locale);
    } else {
      this.a2h.fromString(paramString);
    }
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a2d != null) {
      this.a2d.setLocale(paramLocale);
    }
    if (this.a2f != null) {
      this.a2f.setLocale(paramLocale);
    }
    if (this.a2k != null) {
      this.a2k.setLocale(paramLocale);
    }
    if (this.a2b != null) {
      this.a2b.setLocale(paramLocale);
    }
    if (this.a2i != null) {
      this.a2i.setLocale(paramLocale);
    }
    if (this.a17 != null) {
      this.a17.setLocale(paramLocale);
    }
    if (this.a2e != null) {
      this.a2e.setLocale(paramLocale);
    }
    if (this.a19 != null) {
      this.a19.setLocale(paramLocale);
    }
    if (this.a2j != null) {
      this.a2j.setLocale(paramLocale);
    }
    if (this.a2c != null) {
      this.a2c.setLocale(paramLocale);
    }
    if (this.a16 != null) {
      this.a16.setLocale(paramLocale);
    }
    if (this.a18 != null) {
      this.a18.setLocale(paramLocale);
    }
    if (this.a2h != null) {
      this.a2h.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.a2k != null) {
      this.a2k.setFormat(paramString);
    }
    if (this.a2b != null) {
      this.a2b.setFormat(paramString);
    }
    if (this.a19 != null) {
      this.a19.setFormat(paramString);
    }
    if (this.a2j != null) {
      this.a2j.setFormat(paramString);
    }
    if (this.a16 != null) {
      this.a16.setFormat(paramString);
    }
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    CreditCardAcctSummary localCreditCardAcctSummary = (CreditCardAcctSummary)paramObject;
    int i = 1;
    if (paramString.equals("AVAIL_CREDIT")) {
      i = Currency.compare(this.a2d, localCreditCardAcctSummary.getAvailCredit());
    } else if (paramString.equals("AMT_DUE")) {
      i = Currency.compare(this.a2f, localCreditCardAcctSummary.getAmtDue());
    } else if ((paramString.equals("INTEREST_RATE")) && (this.a2l != -1.0F) && (localCreditCardAcctSummary.a2l != -1.0F)) {
      i = this.a2l == localCreditCardAcctSummary.getInterestRate() ? 0 : this.a2l < localCreditCardAcctSummary.getInterestRate() ? -1 : 1;
    } else if ((paramString.equals("DUE_DATE")) && (this.a2k != null) && (localCreditCardAcctSummary.getDueDate() != null)) {
      i = this.a2k.compare(localCreditCardAcctSummary.getDueDate(), paramString);
    } else if ((paramString.equals("CARD_HOLDER_NAME")) && (this.a2a != null) && (localCreditCardAcctSummary.getCardHolderName() != null)) {
      i = localCollator.compare(this.a2a, localCreditCardAcctSummary.getCardHolderName());
    } else if ((paramString.equals("CARD_EXP_DATE")) && (this.a2b != null) && (localCreditCardAcctSummary.getCardExpDate() != null)) {
      i = this.a2b.compare(localCreditCardAcctSummary.getCardExpDate(), paramString);
    } else if ((paramString.equals("CREDIT_LIMIT")) && (this.a2i != null) && (localCreditCardAcctSummary.getCreditLimit() != null)) {
      i = Currency.compare(this.a2i, localCreditCardAcctSummary.getCreditLimit());
    } else if ((paramString.equals("LAST_PAYMENT_AMT")) && (this.a17 != null) && (localCreditCardAcctSummary.getLastPaymentAmt() != null)) {
      i = this.a17.compareTo(localCreditCardAcctSummary.getLastPaymentAmt());
    } else if ((paramString.equals("NEXT_PAYMENT_MIN_AMT")) && (this.a2e != null) && (localCreditCardAcctSummary.getNextPaymentMinAmt() != null)) {
      i = this.a2e.compareTo(localCreditCardAcctSummary.getNextPaymentMinAmt());
    } else if ((paramString.equals("NEXT_PAYMENT_DUE")) && (this.a19 != null) && (localCreditCardAcctSummary.getNextPaymentDue() != null)) {
      i = this.a19.compare(localCreditCardAcctSummary.getNextPaymentDue(), paramString);
    } else if ((paramString.equals("LAST_PAYMENT_DATE")) && (this.a2j != null) && (localCreditCardAcctSummary.getLastPaymentDate() != null)) {
      i = this.a2j.compare(localCreditCardAcctSummary.getLastPaymentDate(), paramString);
    } else if ((paramString.equals("CURRENT_BALANCE")) && (this.a2c != null) && (localCreditCardAcctSummary.getCurrentBalance() != null)) {
      i = this.a2c.compareTo(localCreditCardAcctSummary.getCurrentBalance());
    } else if ((paramString.equals("LAST_ADVANCE_DATE")) && (this.a16 != null) && (localCreditCardAcctSummary.getLastAdvanceDate() != null)) {
      i = this.a16.compare(localCreditCardAcctSummary.getLastAdvanceDate(), paramString);
    } else if ((paramString.equals("LAST_ADVANCE_AMT")) && (this.a18 != null) && (localCreditCardAcctSummary.getLastAdvanceAmt() != null)) {
      i = this.a18.compareTo(localCreditCardAcctSummary.getLastAdvanceAmt());
    } else if ((paramString.equals("PAYOFF_AMOUNT")) && (this.a2h != null) && (localCreditCardAcctSummary.getPayoffAmount() != null)) {
      i = this.a2h.compareTo(localCreditCardAcctSummary.getPayoffAmount());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("AVAIL_CREDIT")) && (this.a2d != null)) {
      return this.a2d.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AMT_DUE")) && (this.a2f != null)) {
      return this.a2f.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("INTEREST_RATE")) && (this.a2l != -1.0F)) {
      return isFilterable(Float.toString(this.a2l), paramString2, Float.valueOf(paramString3));
    }
    if ((paramString1.equals("DUE_DATE")) && (this.a2k != null)) {
      return this.a2k.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("CARD_HOLDER_NAME")) && (this.a2a != null)) {
      return isFilterable(this.a2a, paramString2, paramString3);
    }
    if ((paramString1.equals("CARD_EXP_DATE")) && (this.a2b != null)) {
      return this.a2b.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("CREDIT_LIMIT")) && (this.a2i != null)) {
      return this.a2i.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("LAST_PAYMENT_AMT")) && (this.a17 != null)) {
      return this.a17.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("NEXT_PAYMENT_MIN_AMT")) && (this.a2e != null)) {
      return this.a2e.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("NEXT_PAYMENT_DUE")) && (this.a19 != null)) {
      return this.a19.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("LAST_PAYMENT_DATE")) && (this.a2j != null)) {
      return this.a2j.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("CURRENT_BALANCE")) && (this.a2c != null)) {
      return this.a2c.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("LAST_ADVANCE_DATE")) && (this.a16 != null)) {
      return this.a16.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("LAST_ADVANCE_AMT")) && (this.a18 != null)) {
      return this.a18.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("PAYOFF_AMOUNT")) && (this.a2h != null)) {
      return this.a2h.isFilterable(paramString1 + paramString2 + paramString3);
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
      this.a2l = Float.parseFloat(paramString2);
    }
    else if (paramString1.equals("DUE_DATE"))
    {
      if (this.a2k == null)
      {
        this.a2k = new DateTime(this.locale);
        this.a2k.setFormat(this.datetype);
      }
      this.a2k.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("CARD_HOLDER_NAME"))
    {
      this.a2a = paramString2;
    }
    else if (paramString1.equals("CARD_EXP_DATE"))
    {
      if (this.a2b == null)
      {
        this.a2b = new DateTime(this.locale);
        this.a2b.setFormat(this.datetype);
      }
      this.a2b.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("CREDIT_LIMIT"))
    {
      setCreditLimit(paramString2);
    }
    else if (paramString1.equals("LAST_PAYMENT_AMT"))
    {
      setLastPaymentAmt(paramString2);
    }
    else if (paramString1.equals("NEXT_PAYMENT_MIN_AMT"))
    {
      setNextPaymentMinAmt(paramString2);
    }
    else if (paramString1.equals("NEXT_PAYMENT_DUE"))
    {
      if (this.a19 == null)
      {
        this.a19 = new DateTime(this.locale);
        this.a19.setFormat(this.datetype);
      }
      this.a19.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("LAST_PAYMENT_DATE"))
    {
      if (this.a2j == null)
      {
        this.a2j = new DateTime(this.locale);
        this.a2j.setFormat(this.datetype);
      }
      this.a2j.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("CURRENT_BALANCE"))
    {
      setCurrentBalance(paramString2);
    }
    else if (paramString1.equals("LAST_ADVANCE_DATE"))
    {
      if (this.a16 == null)
      {
        this.a16 = new DateTime(this.locale);
        this.a16.setFormat(this.datetype);
      }
      this.a16.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("LAST_ADVANCE_AMT"))
    {
      setLastAdvanceAmt(paramString2);
    }
    else if (paramString1.equals("PAYOFF_AMOUNT"))
    {
      setPayoffAmount(paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "CREDIT_CARD_ACCT_SUMMARY");
    if (this.a2d != null) {
      XMLHandler.appendTag(localStringBuffer, "AVAIL_CREDIT", this.a2d.doubleValue());
    }
    if (this.a2f != null) {
      XMLHandler.appendTag(localStringBuffer, "AMT_DUE", this.a2f.doubleValue());
    }
    if (this.a2l != -1.0F) {
      XMLHandler.appendTag(localStringBuffer, "INTEREST_RATE", getInterestRateString());
    }
    if (this.a2k != null) {
      XMLHandler.appendTag(localStringBuffer, "DUE_DATE", this.a2k.toXMLFormat());
    }
    if (this.a2a != null) {
      XMLHandler.appendTag(localStringBuffer, "CARD_HOLDER_NAME", this.a2a);
    }
    if (this.a2b != null) {
      XMLHandler.appendTag(localStringBuffer, "CARD_EXP_DATE", this.a2b.toXMLFormat());
    }
    if (this.a2i != null) {
      XMLHandler.appendTag(localStringBuffer, "CREDIT_LIMIT", this.a2i.doubleValue());
    }
    if (this.a17 != null) {
      XMLHandler.appendTag(localStringBuffer, "LAST_PAYMENT_AMT", this.a17.doubleValue());
    }
    if (this.a2e != null) {
      XMLHandler.appendTag(localStringBuffer, "NEXT_PAYMENT_MIN_AMT", this.a2e.doubleValue());
    }
    if (this.a19 != null) {
      XMLHandler.appendTag(localStringBuffer, "NEXT_PAYMENT_DUE", this.a19.toXMLFormat());
    }
    if (this.a2j != null) {
      XMLHandler.appendTag(localStringBuffer, "LAST_PAYMENT_DATE", this.a2j.toXMLFormat());
    }
    if (this.a2c != null) {
      XMLHandler.appendTag(localStringBuffer, "CURRENT_BALANCE", this.a2c.doubleValue());
    }
    if (this.a16 != null) {
      XMLHandler.appendTag(localStringBuffer, "LAST_ADVANCE_DATE", this.a16.toXMLFormat());
    }
    if (this.a18 != null) {
      XMLHandler.appendTag(localStringBuffer, "LAST_ADVANCE_AMT", this.a18.doubleValue());
    }
    if (this.a2h != null) {
      XMLHandler.appendTag(localStringBuffer, "PAYOFF_AMOUNT", this.a2h.doubleValue());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "CREDIT_CARD_ACCT_SUMMARY");
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
    this.a2g = convertDisplay(this.a2f);
    this.a15 = convertDisplay(this.a2d);
    this.a14 = convertDisplay(this.a2c);
  }
  
  public Currency getDisplayAmtDue()
  {
    return this.a2g;
  }
  
  public Currency getDisplayAvailCredit()
  {
    return this.a15;
  }
  
  public Currency getDisplayCurrentBalance()
  {
    return this.a14;
  }
  
  public String getCurrencyCode(Accounts paramAccounts)
  {
    String str = super.getCurrencyCode(paramAccounts);
    if (str != null) {
      return str;
    }
    if (this.a2d != null) {
      return this.a2d.getCurrencyCode();
    }
    if (this.a2f != null) {
      return this.a2f.getCurrencyCode();
    }
    if (this.a2i != null) {
      return this.a2i.getCurrencyCode();
    }
    if (this.a2e != null) {
      return this.a2e.getCurrencyCode();
    }
    if (this.a17 != null) {
      return this.a17.getCurrencyCode();
    }
    if (this.a2c != null) {
      return this.a2c.getCurrencyCode();
    }
    if (this.a18 != null) {
      return this.a18.getCurrencyCode();
    }
    if (this.a2h != null) {
      return this.a2h.getCurrencyCode();
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.CreditCardAcctSummary
 * JD-Core Version:    0.7.0.1
 */