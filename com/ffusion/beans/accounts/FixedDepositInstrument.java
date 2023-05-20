package com.ffusion.beans.accounts;

import com.ffusion.beans.Contact;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.text.Collator;
import java.text.NumberFormat;
import java.util.Locale;

public class FixedDepositInstrument
  extends ExtendABean
  implements SettlementInstructionTypes
{
  public static final String FIXED_DEP_INSTRUMENT = "FIXED_DEP_INSTRUMENT";
  public static final String INSTRUMENT_NUM = "INSTRUMENT_NUM";
  public static final String INSTRUMENT_BANK_NAME = "INSTRUMENT_BANK_NAME";
  public static final String CURRENCY = "CURRENCY";
  public static final String PRINCIPAL_AMT = "PRINCIPAL_AMT";
  public static final String INTEREST_RATE = "INTEREST_RATE";
  public static final String ACCRUED_INTEREST = "ACCRUED_INTEREST";
  public static final String MATURITY_DATE = "MATURITY_DATE";
  public static final String INTEREST_AT_MATURITY = "INTEREST_AT_MATURITY";
  public static final String PROCEEDS_AT_MATURITY = "PROCEEDS_AT_MATURITY";
  public static final String VALUE_DATE = "VALUE_DATE";
  public static final String DAYS_IN_TERM = "DAYS_IN_TERM";
  public static final String RESTRICTED_AMT = "RESTRICTED_AMT";
  public static final String NUM_ROLLOVERS = "NUM_ROLLOVERS";
  public static final String STMT_MAIL_ADDR1 = "STMT_MAIL_ADDR1";
  public static final String STMT_MAIL_ADDR2 = "STMT_MAIL_ADDR2";
  public static final String STMT_MAIL_ADDR3 = "STMT_MAIL_ADDR3";
  public static final String SETTLEMENT_INSTR_TYPE = "SETTLEMENT_INSTR_TYPE";
  public static final String SETTLEMENT_TGT_RTG_NUM = "SETTLEMENT_TGT_RTG_NUM";
  public static final String SETTLEMENT_TGT_ACCT_NUM = "SETTLEMENT_TGT_ACCT_NUM";
  public static final String DATA_DATE = "DATA_DATE";
  private String a1G;
  private String a1t;
  private String a1F;
  private String a1w;
  private String a1x;
  private String a1I;
  private String a1y;
  private Currency a1M;
  private float a1J = -1.0F;
  private Currency a1N;
  private DateTime a1z;
  private Currency a1O;
  private Currency a1s;
  private DateTime a1B;
  private int a1A = -1;
  private Currency a1P;
  private int a1u = -1;
  private Contact a1E;
  private Contact a1D;
  private Contact a1C;
  private int a1H = 0;
  private String a1v;
  private String a1L;
  private DateTime a1K;
  
  public String getAccountID()
  {
    return this.a1G;
  }
  
  public String getAccountNumber()
  {
    return this.a1t;
  }
  
  public void setAccountID(String paramString)
  {
    this.a1G = paramString;
  }
  
  public void setAccountNumber(String paramString)
  {
    this.a1t = paramString;
  }
  
  public String getBankID()
  {
    return this.a1F;
  }
  
  public void setBankID(String paramString)
  {
    this.a1F = paramString;
  }
  
  public String getRoutingNumber()
  {
    return this.a1w;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.a1w = paramString;
  }
  
  public String getInstrumentNumber()
  {
    return this.a1x;
  }
  
  public void setInstrumentNumber(String paramString)
  {
    this.a1x = paramString;
  }
  
  public String getInstrumentBankName()
  {
    return this.a1I;
  }
  
  public void setInstrumentBankName(String paramString)
  {
    this.a1I = paramString;
  }
  
  public String getCurrency()
  {
    return this.a1y;
  }
  
  public void setCurrency(String paramString)
  {
    this.a1y = paramString;
  }
  
  public Currency getPrincipalAmount()
  {
    return this.a1M;
  }
  
  public void setPrincipalAmount(Currency paramCurrency)
  {
    this.a1M = paramCurrency;
  }
  
  public float getInterestRate()
  {
    return this.a1J;
  }
  
  public String getInterestRateString()
  {
    if (this.a1J == -1.0F) {
      return null;
    }
    NumberFormat localNumberFormat = NumberFormat.getNumberInstance(this.locale);
    localNumberFormat.setMinimumFractionDigits(5);
    localNumberFormat.setMaximumFractionDigits(5);
    return localNumberFormat.format(this.a1J);
  }
  
  public void setInterestRate(float paramFloat)
  {
    this.a1J = paramFloat;
  }
  
  public Currency getAccruedInterest()
  {
    return this.a1N;
  }
  
  public void setAccruedInterest(Currency paramCurrency)
  {
    this.a1N = paramCurrency;
  }
  
  public DateTime getMaturityDate()
  {
    return this.a1z;
  }
  
  public void setMaturityDate(DateTime paramDateTime)
  {
    this.a1z = paramDateTime;
  }
  
  public Currency getInterestAtMaturity()
  {
    return this.a1O;
  }
  
  public void setInterestAtMaturity(Currency paramCurrency)
  {
    this.a1O = paramCurrency;
  }
  
  public Currency getProceedsAtMaturity()
  {
    return this.a1s;
  }
  
  public void setProceedsAtMaturity(Currency paramCurrency)
  {
    this.a1s = paramCurrency;
  }
  
  public DateTime getValueDate()
  {
    return this.a1B;
  }
  
  public void setValueDate(DateTime paramDateTime)
  {
    this.a1B = paramDateTime;
  }
  
  public int getDaysInTerm()
  {
    return this.a1A;
  }
  
  public void setDaysInTerm(int paramInt)
  {
    this.a1A = paramInt;
  }
  
  public Currency getRestrictedAmount()
  {
    return this.a1P;
  }
  
  public void setRestrictedAmount(Currency paramCurrency)
  {
    this.a1P = paramCurrency;
  }
  
  public int getNumberOfRollovers()
  {
    return this.a1u;
  }
  
  public void setNumberOfRollovers(int paramInt)
  {
    this.a1u = paramInt;
  }
  
  public Contact getStatementMailingAddr1()
  {
    return this.a1E;
  }
  
  public void setStatementMailingAddr1(Contact paramContact)
  {
    this.a1E = paramContact;
  }
  
  public Contact getStatementMailingAddr2()
  {
    return this.a1D;
  }
  
  public void setStatementMailingAddr2(Contact paramContact)
  {
    this.a1D = paramContact;
  }
  
  public Contact getStatementMailingAddr3()
  {
    return this.a1C;
  }
  
  public void setStatementMailingAddr3(Contact paramContact)
  {
    this.a1C = paramContact;
  }
  
  public int getSettlementInstructionType()
  {
    return this.a1H;
  }
  
  public void setSettlementInstructionType(int paramInt)
  {
    this.a1H = paramInt;
  }
  
  public void setSettlementInstructionType(String paramString)
  {
    try
    {
      setSettlementInstructionType(Integer.parseInt(paramString));
    }
    catch (Exception localException) {}
  }
  
  public String getSettlementTargetRoutingNumber()
  {
    return this.a1v;
  }
  
  public void setSettlementTargetRoutingNumber(String paramString)
  {
    this.a1v = paramString;
  }
  
  public String getSettlementTargetAccountNumber()
  {
    return this.a1L;
  }
  
  public void setSettlementTargetAccountNumber(String paramString)
  {
    this.a1L = paramString;
  }
  
  public DateTime getDataDate()
  {
    return this.a1K;
  }
  
  public void setDataDate(DateTime paramDateTime)
  {
    this.a1K = paramDateTime;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a1M != null) {
      this.a1M.setLocale(paramLocale);
    }
    if (this.a1N != null) {
      this.a1N.setLocale(paramLocale);
    }
    if (this.a1z != null) {
      this.a1z.setLocale(paramLocale);
    }
    if (this.a1O != null) {
      this.a1O.setLocale(paramLocale);
    }
    if (this.a1s != null) {
      this.a1s.setLocale(paramLocale);
    }
    if (this.a1B != null) {
      this.a1B.setLocale(paramLocale);
    }
    if (this.a1P != null) {
      this.a1P.setLocale(paramLocale);
    }
    if (this.a1E != null) {
      this.a1E.setLocale(paramLocale);
    }
    if (this.a1D != null) {
      this.a1D.setLocale(paramLocale);
    }
    if (this.a1C != null) {
      this.a1C.setLocale(paramLocale);
    }
    if (this.a1K != null) {
      this.a1K.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    if (this.a1z != null) {
      this.a1z.setFormat(paramString);
    }
    if (this.a1B != null) {
      this.a1B.setFormat(paramString);
    }
    if (this.a1K != null) {
      this.a1K.setFormat(paramString);
    }
    super.setDateFormat(paramString);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "VALUE_DATE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    FixedDepositInstrument localFixedDepositInstrument = (FixedDepositInstrument)paramObject;
    int i = 1;
    if (paramString.equals("ACCOUNTID"))
    {
      i = numStringCompare(getAccountID(), localFixedDepositInstrument.getAccountID());
    }
    else if (paramString.equals("ACCOUNTNUMBER"))
    {
      i = numStringCompare(getAccountNumber(), localFixedDepositInstrument.getAccountNumber());
    }
    else if ((paramString.equals("BANKID")) && (getBankID() != null) && (localFixedDepositInstrument.getBankID() != null))
    {
      i = localCollator.compare(getBankID(), localFixedDepositInstrument.getBankID());
    }
    else if ((paramString.equals("ROUTINGNUM")) && (getRoutingNumber() != null) && (localFixedDepositInstrument.getRoutingNumber() != null))
    {
      i = localCollator.compare(getRoutingNumber(), localFixedDepositInstrument.getRoutingNumber());
    }
    else if (paramString.equals("INSTRUMENT_NUM"))
    {
      i = numStringCompare(getInstrumentNumber(), localFixedDepositInstrument.getInstrumentNumber());
    }
    else if ((paramString.equals("INSTRUMENT_BANK_NAME")) && (getInstrumentBankName() != null) && (localFixedDepositInstrument.getInstrumentBankName() != null))
    {
      i = localCollator.compare(getInstrumentBankName(), localFixedDepositInstrument.getInstrumentBankName());
    }
    else if ((paramString.equals("CURRENCY")) && (getCurrency() != null) && (localFixedDepositInstrument.getCurrency() != null))
    {
      i = localCollator.compare(getCurrency(), localFixedDepositInstrument.getCurrency());
    }
    else if ((paramString.equals("PRINCIPAL_AMT")) && (getPrincipalAmount() != null) && (localFixedDepositInstrument.getPrincipalAmount() != null))
    {
      i = getPrincipalAmount().compareTo(localFixedDepositInstrument.getPrincipalAmount());
    }
    else if ((paramString.equals("INTEREST_RATE")) && (getInterestRate() != -1.0F) && (localFixedDepositInstrument.getInterestRate() != -1.0F))
    {
      float f = getInterestRate() - localFixedDepositInstrument.getInterestRate();
      i = f == 0.0F ? 0 : f < 0.0F ? -1 : 1;
    }
    else if ((paramString.equals("ACCRUED_INTEREST")) && (getAccruedInterest() != null) && (localFixedDepositInstrument.getAccruedInterest() != null))
    {
      i = getAccruedInterest().compareTo(localFixedDepositInstrument.getAccruedInterest());
    }
    else if ((paramString.equals("MATURITY_DATE")) && (getMaturityDate() != null) && (localFixedDepositInstrument.getMaturityDate() != null))
    {
      i = getMaturityDate().equals(localFixedDepositInstrument.getMaturityDate()) ? 0 : getMaturityDate().before(localFixedDepositInstrument.getMaturityDate()) ? -1 : 1;
    }
    else if ((paramString.equals("INTEREST_AT_MATURITY")) && (getInterestAtMaturity() != null) && (localFixedDepositInstrument.getInterestAtMaturity() != null))
    {
      i = getInterestAtMaturity().compareTo(localFixedDepositInstrument.getInterestAtMaturity());
    }
    else if ((paramString.equals("PROCEEDS_AT_MATURITY")) && (getProceedsAtMaturity() != null) && (localFixedDepositInstrument.getProceedsAtMaturity() != null))
    {
      i = getProceedsAtMaturity().compareTo(localFixedDepositInstrument.getProceedsAtMaturity());
    }
    else if ((paramString.equals("VALUE_DATE")) && (getValueDate() != null) && (localFixedDepositInstrument.getValueDate() != null))
    {
      i = getValueDate().equals(localFixedDepositInstrument.getValueDate()) ? 0 : getValueDate().before(localFixedDepositInstrument.getValueDate()) ? -1 : 1;
    }
    else
    {
      int j;
      if ((paramString.equals("DAYS_IN_TERM")) && (getDaysInTerm() != -1) && (localFixedDepositInstrument.getDaysInTerm() != -1))
      {
        j = getDaysInTerm() - localFixedDepositInstrument.getDaysInTerm();
        i = j == 0 ? 0 : j < 0 ? -1 : 1;
      }
      else if ((paramString.equals("RESTRICTED_AMT")) && (getRestrictedAmount() != null) && (localFixedDepositInstrument.getRestrictedAmount() != null))
      {
        i = getRestrictedAmount().compareTo(localFixedDepositInstrument.getRestrictedAmount());
      }
      else if ((paramString.equals("NUM_ROLLOVERS")) && (getNumberOfRollovers() != -1) && (localFixedDepositInstrument.getNumberOfRollovers() != -1))
      {
        j = getNumberOfRollovers() - localFixedDepositInstrument.getNumberOfRollovers();
        i = j == 0 ? 0 : j < 0 ? -1 : 1;
      }
      else if ((paramString.equals("STMT_MAIL_ADDR1")) && (getStatementMailingAddr1() != null) && (localFixedDepositInstrument.getStatementMailingAddr1() != null))
      {
        i = getStatementMailingAddr1().compareTo(localFixedDepositInstrument.getStatementMailingAddr1());
      }
      else if ((paramString.equals("STMT_MAIL_ADDR2")) && (getStatementMailingAddr2() != null) && (localFixedDepositInstrument.getStatementMailingAddr2() != null))
      {
        i = getStatementMailingAddr2().compareTo(localFixedDepositInstrument.getStatementMailingAddr2());
      }
      else if ((paramString.equals("STMT_MAIL_ADDR3")) && (getStatementMailingAddr3() != null) && (localFixedDepositInstrument.getStatementMailingAddr3() != null))
      {
        i = getStatementMailingAddr3().compareTo(localFixedDepositInstrument.getStatementMailingAddr3());
      }
      else if (paramString.equals("SETTLEMENT_INSTR_TYPE"))
      {
        j = getSettlementInstructionType() - localFixedDepositInstrument.getSettlementInstructionType();
        i = j == 0 ? 0 : j < 0 ? -1 : 1;
      }
      else if (paramString.equals("SETTLEMENT_TGT_RTG_NUM"))
      {
        i = numStringCompare(getSettlementTargetRoutingNumber(), localFixedDepositInstrument.getSettlementTargetRoutingNumber());
      }
      else if (paramString.equals("SETTLEMENT_TGT_ACCT_NUM"))
      {
        i = numStringCompare(getSettlementTargetAccountNumber(), localFixedDepositInstrument.getSettlementTargetAccountNumber());
      }
      else if ((paramString.equals("DATA_DATE")) && (getDataDate() != null) && (localFixedDepositInstrument.getDataDate() != null))
      {
        i = getDataDate().equals(localFixedDepositInstrument.getDataDate()) ? 0 : getDataDate().before(localFixedDepositInstrument.getDataDate()) ? -1 : 1;
      }
      else
      {
        i = super.compare(paramObject, paramString);
      }
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ACCOUNTID")) && (this.a1G != null)) {
      return isFilterable(this.a1G, paramString2, paramString3);
    }
    if ((paramString1.equals("ACCOUNTNUMBER")) && (this.a1t != null)) {
      return isFilterable(this.a1t, paramString2, paramString3);
    }
    if ((paramString1.equals("BANKID")) && (this.a1F != null)) {
      return isFilterable(this.a1F, paramString2, paramString3);
    }
    if ((paramString1.equals("ROUTINGNUM")) && (this.a1w != null)) {
      return isFilterable(this.a1w, paramString2, paramString3);
    }
    if ((paramString1.equals("INSTRUMENT_NUM")) && (this.a1x != null)) {
      return isFilterable(this.a1x, paramString2, paramString3);
    }
    if ((paramString1.equals("CURRENCY")) && (this.a1y != null)) {
      return isFilterable(this.a1y, paramString2, paramString3);
    }
    if ((paramString1.equals("PRINCIPAL_AMT")) && (this.a1M != null)) {
      return this.a1M.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("INTEREST_RATE")) && (this.a1J != -1.0F)) {
      return isFilterable(getInterestRateString(), paramString2, paramString3);
    }
    if ((paramString1.equals("ACCRUED_INTEREST")) && (this.a1N != null)) {
      return this.a1N.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("MATURITY_DATE")) && (this.a1z != null)) {
      return this.a1z.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("INTEREST_AT_MATURITY")) && (this.a1O != null)) {
      return this.a1O.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("PROCEEDS_AT_MATURITY")) && (this.a1s != null)) {
      return this.a1s.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("VALUE_DATE")) && (this.a1B != null)) {
      return this.a1B.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("DAYS_IN_TERM")) && (this.a1A != -1)) {
      return isFilterable(String.valueOf(this.a1A), paramString2, paramString3);
    }
    if ((paramString1.equals("RESTRICTED_AMT")) && (this.a1P != null)) {
      return this.a1P.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("NUM_ROLLOVERS")) && (this.a1u != -1)) {
      return isFilterable(String.valueOf(this.a1u), paramString2, paramString3);
    }
    if (paramString1.equals("SETTLEMENT_INSTR_TYPE")) {
      return isFilterable(String.valueOf(this.a1H), paramString2, paramString3);
    }
    if ((paramString1.equals("SETTLEMENT_TGT_RTG_NUM")) && (this.a1v != null)) {
      return isFilterable(this.a1v, paramString2, paramString3);
    }
    if ((paramString1.equals("SETTLEMENT_TGT_ACCT_NUM")) && (this.a1L != null)) {
      return isFilterable(this.a1L, paramString2, paramString3);
    }
    if ((paramString1.equals("DATA_DATE")) && (this.a1K != null)) {
      return this.a1K.isFilterable("VALUE" + paramString2 + paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ACCOUNTID"))
    {
      setAccountID(paramString2);
    }
    else if (paramString1.equals("ACCOUNTNUMBER"))
    {
      setAccountNumber(paramString2);
    }
    else if (paramString1.equals("BANKID"))
    {
      setBankID(paramString2);
    }
    else if (paramString1.equals("ROUTINGNUM"))
    {
      setRoutingNumber(paramString2);
    }
    else if (paramString1.equals("INSTRUMENT_NUM"))
    {
      setInstrumentNumber(paramString2);
    }
    else if (paramString1.equals("INSTRUMENT_BANK_NAME"))
    {
      setInstrumentBankName(paramString2);
    }
    else if (paramString1.equals("CURRENCY"))
    {
      setCurrency(paramString2);
    }
    else if (paramString1.equals("PRINCIPAL_AMT"))
    {
      if (this.a1M == null) {
        this.a1M = new Currency(paramString2, this.locale);
      } else {
        this.a1M.fromString(paramString2);
      }
    }
    else if (paramString1.equals("INTEREST_RATE"))
    {
      this.a1J = Float.parseFloat(paramString2);
    }
    else if (paramString1.equals("ACCRUED_INTEREST"))
    {
      if (this.a1N == null) {
        this.a1N = new Currency(paramString2, this.locale);
      } else {
        this.a1N.fromString(paramString2);
      }
    }
    else if (paramString1.equals("MATURITY_DATE"))
    {
      if (this.a1z == null)
      {
        this.a1z = new DateTime(this.locale);
        this.a1z.setFormat(this.datetype);
      }
      this.a1z.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("INTEREST_AT_MATURITY"))
    {
      if (this.a1O == null) {
        this.a1O = new Currency(paramString2, this.locale);
      } else {
        this.a1O.fromString(paramString2);
      }
    }
    else if (paramString1.equals("PROCEEDS_AT_MATURITY"))
    {
      if (this.a1s == null) {
        this.a1s = new Currency(paramString2, this.locale);
      } else {
        this.a1s.fromString(paramString2);
      }
    }
    else if (paramString1.equals("VALUE_DATE"))
    {
      if (this.a1B == null)
      {
        this.a1B = new DateTime(this.locale);
        this.a1B.setFormat(this.datetype);
      }
      this.a1B.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("DAYS_IN_TERM"))
    {
      this.a1A = Integer.parseInt(paramString2);
    }
    else if (paramString1.equals("RESTRICTED_AMT"))
    {
      if (this.a1P == null) {
        this.a1P = new Currency(paramString2, this.locale);
      } else {
        this.a1P.fromString(paramString2);
      }
    }
    else if (paramString1.equals("NUM_ROLLOVERS"))
    {
      this.a1u = Integer.parseInt(paramString2);
    }
    else if (paramString1.equals("STMT_MAIL_ADDR1"))
    {
      if (this.a1E == null) {
        this.a1E = new Contact();
      }
      this.a1E.setXML(paramString2);
    }
    else if (paramString1.equals("STMT_MAIL_ADDR2"))
    {
      if (this.a1D == null) {
        this.a1D = new Contact();
      }
      this.a1D.setXML(paramString2);
    }
    else if (paramString1.equals("STMT_MAIL_ADDR3"))
    {
      if (this.a1C == null) {
        this.a1C = new Contact();
      }
      this.a1C.setXML(paramString2);
    }
    else if (paramString1.equals("SETTLEMENT_INSTR_TYPE"))
    {
      this.a1H = Integer.parseInt(paramString2);
    }
    else if (paramString1.equals("SETTLEMENT_TGT_RTG_NUM"))
    {
      this.a1v = paramString2;
    }
    else if (paramString1.equals("SETTLEMENT_TGT_ACCT_NUM"))
    {
      this.a1L = paramString2;
    }
    else if (paramString1.equals("DATA_DATE"))
    {
      if (this.a1K == null)
      {
        this.a1K = new DateTime(this.locale);
        this.a1K.setFormat(this.datetype);
      }
      this.a1K.fromXMLFormat(paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "FIXED_DEP_INSTRUMENT");
    if (this.a1G != null) {
      XMLHandler.appendTag(localStringBuffer, "ACCOUNTID", this.a1G);
    }
    if (this.a1t != null) {
      XMLHandler.appendTag(localStringBuffer, "ACCOUNTNUMBER", this.a1t);
    }
    if (this.a1F != null) {
      XMLHandler.appendTag(localStringBuffer, "BANKID", this.a1F);
    }
    if (this.a1w != null) {
      XMLHandler.appendTag(localStringBuffer, "ROUTINGNUM", this.a1w);
    }
    if (this.a1x != null) {
      XMLHandler.appendTag(localStringBuffer, "INSTRUMENT_NUM", this.a1x);
    }
    if (this.a1I != null) {
      XMLHandler.appendTag(localStringBuffer, "INSTRUMENT_BANK_NAME", this.a1I);
    }
    if (this.a1y != null) {
      XMLHandler.appendTag(localStringBuffer, "CURRENCY", this.a1y);
    }
    if (this.a1M != null) {
      XMLHandler.appendTag(localStringBuffer, "PRINCIPAL_AMT", this.a1M.doubleValue());
    }
    if (this.a1J != -1.0F) {
      XMLHandler.appendTag(localStringBuffer, "INTEREST_RATE", getInterestRateString());
    }
    if (this.a1N != null) {
      XMLHandler.appendTag(localStringBuffer, "ACCRUED_INTEREST", this.a1N.doubleValue());
    }
    if (this.a1z != null) {
      XMLHandler.appendTag(localStringBuffer, "MATURITY_DATE", this.a1z.toXMLFormat());
    }
    if (this.a1O != null) {
      XMLHandler.appendTag(localStringBuffer, "INTEREST_AT_MATURITY", this.a1O.doubleValue());
    }
    if (this.a1s != null) {
      XMLHandler.appendTag(localStringBuffer, "PROCEEDS_AT_MATURITY", this.a1s.doubleValue());
    }
    if (this.a1B != null) {
      XMLHandler.appendTag(localStringBuffer, "VALUE_DATE", this.a1B.toXMLFormat());
    }
    if (this.a1A != -1) {
      XMLHandler.appendTag(localStringBuffer, "DAYS_IN_TERM", this.a1A);
    }
    if (this.a1P != null) {
      XMLHandler.appendTag(localStringBuffer, "RESTRICTED_AMT", this.a1P.doubleValue());
    }
    if (this.a1u != -1) {
      XMLHandler.appendTag(localStringBuffer, "NUM_ROLLOVERS", this.a1u);
    }
    if (this.a1E != null) {
      XMLHandler.appendTag(localStringBuffer, "STMT_MAIL_ADDR1", this.a1E.getXML());
    }
    if (this.a1D != null) {
      XMLHandler.appendTag(localStringBuffer, "STMT_MAIL_ADDR2", this.a1D.getXML());
    }
    if (this.a1C != null) {
      XMLHandler.appendTag(localStringBuffer, "STMT_MAIL_ADDR3", this.a1C.getXML());
    }
    if (this.a1H != 0) {
      XMLHandler.appendTag(localStringBuffer, "SETTLEMENT_INSTR_TYPE", this.a1H);
    }
    if (this.a1v != null) {
      XMLHandler.appendTag(localStringBuffer, "SETTLEMENT_TGT_RTG_NUM", this.a1v);
    }
    if (this.a1L != null) {
      XMLHandler.appendTag(localStringBuffer, "SETTLEMENT_TGT_ACCT_NUM", this.a1L);
    }
    if (this.a1K != null) {
      XMLHandler.appendTag(localStringBuffer, "DATA_DATE", this.a1K.toXMLFormat());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "FIXED_DEP_INSTRUMENT");
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
    extends ExtendABean.InternalXMLHandler
  {
    a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("STMT_MAIL_ADDR1"))
      {
        FixedDepositInstrument.this.a1E = new Contact();
        FixedDepositInstrument.this.a1E.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("STMT_MAIL_ADDR2"))
      {
        FixedDepositInstrument.this.a1D = new Contact();
        FixedDepositInstrument.this.a1D.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("STMT_MAIL_ADDR3"))
      {
        FixedDepositInstrument.this.a1C = new Contact();
        FixedDepositInstrument.this.a1C.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.FixedDepositInstrument
 * JD-Core Version:    0.7.0.1
 */