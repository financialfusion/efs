package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fx.FXRates;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.csil.core.FX;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;

public class AccountSummaries
  extends FilteredList
{
  public static final String ACCOUNT_SUMMARIES = "ACCOUNT_SUMMARIES";
  private SecureUser jdField_case;
  protected String datetype = "SHORT";
  private String jdField_byte = "";
  private FXRates jdField_char = null;
  
  public AccountSummary createAccountSummary()
  {
    AccountSummary localAccountSummary = new AccountSummary();
    super.add(localAccountSummary);
    return localAccountSummary;
  }
  
  public DepositAcctSummary createDepositAcctSummary()
  {
    DepositAcctSummary localDepositAcctSummary = new DepositAcctSummary();
    super.add(localDepositAcctSummary);
    return localDepositAcctSummary;
  }
  
  public AssetAcctSummary createAssetAcctSummary()
  {
    AssetAcctSummary localAssetAcctSummary = new AssetAcctSummary();
    super.add(localAssetAcctSummary);
    return localAssetAcctSummary;
  }
  
  public CreditCardAcctSummary createCreditCardAcctSummary()
  {
    CreditCardAcctSummary localCreditCardAcctSummary = new CreditCardAcctSummary();
    super.add(localCreditCardAcctSummary);
    return localCreditCardAcctSummary;
  }
  
  public LoanAcctSummary createLoanAcctSummary()
  {
    LoanAcctSummary localLoanAcctSummary = new LoanAcctSummary();
    super.add(localLoanAcctSummary);
    return localLoanAcctSummary;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      AccountSummary localAccountSummary = (AccountSummary)localIterator.next();
      localAccountSummary.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public void setSecureUser(SecureUser paramSecureUser)
  {
    this.jdField_case = paramSecureUser;
  }
  
  public SecureUser getSecureUser()
  {
    return this.jdField_case;
  }
  
  public String getOpeningLedgerTotalString()
  {
    Currency localCurrency = getOpeningLedgerTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getOpeningLedgerTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof DepositAcctSummary))
      {
        DepositAcctSummary localDepositAcctSummary = (DepositAcctSummary)localObject2;
        Currency localCurrency2 = localDepositAcctSummary.getOpeningLedger();
        if (localCurrency2 != null)
        {
          if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
            localCurrency2 = convertCurrency(localCurrency2);
          }
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getClosingLedgerTotalString()
  {
    Currency localCurrency = getClosingLedgerTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getClosingLedgerTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof DepositAcctSummary))
      {
        DepositAcctSummary localDepositAcctSummary = (DepositAcctSummary)localObject2;
        Currency localCurrency2 = localDepositAcctSummary.getClosingLedger();
        if (localCurrency2 != null)
        {
          if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
            localCurrency2 = convertCurrency(localCurrency2);
          }
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getTotalDebitsTotalString()
  {
    Currency localCurrency = getTotalDebitsTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getTotalDebitsTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof DepositAcctSummary))
      {
        DepositAcctSummary localDepositAcctSummary = (DepositAcctSummary)localObject2;
        Currency localCurrency2 = localDepositAcctSummary.getTotalDebits();
        if (localCurrency2 != null)
        {
          if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
            localCurrency2 = convertCurrency(localCurrency2);
          }
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getTotalCreditsTotalString()
  {
    Currency localCurrency = getTotalCreditsTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getTotalCreditsTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof DepositAcctSummary))
      {
        DepositAcctSummary localDepositAcctSummary = (DepositAcctSummary)localObject2;
        Currency localCurrency2 = localDepositAcctSummary.getTotalCredits();
        if (localCurrency2 != null)
        {
          if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
            localCurrency2 = convertCurrency(localCurrency2);
          }
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getBookValueTotalString()
  {
    Currency localCurrency = getBookValueTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getBookValueTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof AssetAcctSummary))
      {
        AssetAcctSummary localAssetAcctSummary = (AssetAcctSummary)localObject2;
        Currency localCurrency2 = localAssetAcctSummary.getBookValue();
        if (localCurrency2 != null)
        {
          if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
            localCurrency2 = convertCurrency(localCurrency2);
          }
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getMarketValueTotalString()
  {
    Currency localCurrency = getMarketValueTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getMarketValueTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof AssetAcctSummary))
      {
        AssetAcctSummary localAssetAcctSummary = (AssetAcctSummary)localObject2;
        Currency localCurrency2 = localAssetAcctSummary.getMarketValue();
        if (localCurrency2 != null)
        {
          if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
            localCurrency2 = convertCurrency(localCurrency2);
          }
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getCreditCardAvailCreditTotalString()
  {
    Currency localCurrency = getCreditCardAvailCreditTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getCreditCardAvailCreditTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof CreditCardAcctSummary))
      {
        CreditCardAcctSummary localCreditCardAcctSummary = (CreditCardAcctSummary)localObject2;
        Currency localCurrency2 = localCreditCardAcctSummary.getAvailCredit();
        if (localCurrency2 != null)
        {
          if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
            localCurrency2 = convertCurrency(localCurrency2);
          }
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getCreditCardAmtDueTotalString()
  {
    Currency localCurrency = getCreditCardAmtDueTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getCreditCardAmtDueTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof CreditCardAcctSummary))
      {
        CreditCardAcctSummary localCreditCardAcctSummary = (CreditCardAcctSummary)localObject2;
        Currency localCurrency2 = localCreditCardAcctSummary.getAmtDue();
        if (localCurrency2 != null)
        {
          if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
            localCurrency2 = convertCurrency(localCurrency2);
          }
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getLoanAvailCreditTotalString()
  {
    Currency localCurrency = getLoanAvailCreditTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getLoanAvailCreditTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof LoanAcctSummary))
      {
        LoanAcctSummary localLoanAcctSummary = (LoanAcctSummary)localObject2;
        Currency localCurrency2 = localLoanAcctSummary.getAvailCredit();
        if (localCurrency2 != null)
        {
          if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
            localCurrency2 = convertCurrency(localCurrency2);
          }
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getLoanAmtDueTotalString()
  {
    Currency localCurrency = getLoanAmtDueTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getLoanAmtDueTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof LoanAcctSummary))
      {
        LoanAcctSummary localLoanAcctSummary = (LoanAcctSummary)localObject2;
        Currency localCurrency2 = localLoanAcctSummary.getAmtDue();
        if (localCurrency2 != null)
        {
          if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
            localCurrency2 = convertCurrency(localCurrency2);
          }
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACCOUNT_SUMMARIES");
    for (int i = 0; i < size(); i++)
    {
      AccountSummary localAccountSummary = (AccountSummary)get(i);
      localStringBuffer.append(localAccountSummary.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ACCOUNT_SUMMARIES");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, boolean paramBoolean)
  {
    continueXMLParsing(paramXMLHandler);
  }
  
  public Currency getConsumerPriorDayBalanceTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof DepositAcctSummary))
      {
        DepositAcctSummary localDepositAcctSummary = (DepositAcctSummary)localObject2;
        Currency localCurrency2 = localDepositAcctSummary.getClosingLedger();
        if (localCurrency2 != null)
        {
          if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
            localCurrency2 = convertCurrency(localCurrency2);
          }
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public Currency getConsumerCurrentBalanceTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      Currency localCurrency2 = null;
      Object localObject3;
      if ((localObject2 instanceof DepositAcctSummary))
      {
        localObject3 = (DepositAcctSummary)localObject2;
        localCurrency2 = ((DepositAcctSummary)localObject3).getCurrentLedger();
      }
      else if ((localObject2 instanceof CreditCardAcctSummary))
      {
        localObject3 = (CreditCardAcctSummary)localObject2;
        localCurrency2 = ((CreditCardAcctSummary)localObject3).getCurrentBalance();
      }
      else if ((localObject2 instanceof LoanAcctSummary))
      {
        localObject3 = (LoanAcctSummary)localObject2;
        localCurrency2 = ((LoanAcctSummary)localObject3).getCurrentBalance();
      }
      if (localCurrency2 != null)
      {
        if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
          localCurrency2 = convertCurrency(localCurrency2);
        }
        localCurrency1.addAmount(localCurrency2);
        if (str == null) {
          str = localCurrency2.getFormat();
        }
      }
    }
    return localCurrency1;
  }
  
  public Currency getConsumerAvailableBalanceTotal()
  {
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_case.getBaseCurrency(), this.locale);
    String str = null;
    Object localObject1 = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject2 = localIterator.next();
      if ((localObject2 instanceof DepositAcctSummary))
      {
        DepositAcctSummary localDepositAcctSummary = (DepositAcctSummary)localObject2;
        Currency localCurrency2 = localDepositAcctSummary.getCurrentAvailBal();
        if (localCurrency2 != null)
        {
          if (!this.jdField_case.getBaseCurrency().equals(localCurrency2.getCurrencyCode())) {
            localCurrency2 = convertCurrency(localCurrency2);
          }
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getDisplayOpeningLedgerTotalString()
  {
    Currency localCurrency = getDisplayOpeningLedgerTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getDisplayOpeningLedgerTotal()
  {
    if ((this.jdField_byte == null) || (this.jdField_byte.trim().length() == 0)) {
      return getOpeningLedgerTotal();
    }
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_byte, this.locale);
    String str = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof DepositAcctSummary))
      {
        DepositAcctSummary localDepositAcctSummary = (DepositAcctSummary)localObject;
        Currency localCurrency2 = localDepositAcctSummary.getDisplayOpeningLedger();
        if (localCurrency2 != null)
        {
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getDisplayClosingLedgerTotalString()
  {
    Currency localCurrency = getDisplayClosingLedgerTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getDisplayClosingLedgerTotal()
  {
    if ((this.jdField_byte == null) || (this.jdField_byte.trim().length() == 0)) {
      return getClosingLedgerTotal();
    }
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_byte, this.locale);
    String str = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof DepositAcctSummary))
      {
        DepositAcctSummary localDepositAcctSummary = (DepositAcctSummary)localObject;
        Currency localCurrency2 = localDepositAcctSummary.getDisplayClosingLedger();
        if (localCurrency2 != null)
        {
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getDisplayTotalDebitsTotalString()
  {
    Currency localCurrency = getDisplayTotalDebitsTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getDisplayTotalDebitsTotal()
  {
    if ((this.jdField_byte == null) || (this.jdField_byte.trim().length() == 0)) {
      return getTotalDebitsTotal();
    }
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_byte, this.locale);
    String str = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof DepositAcctSummary))
      {
        DepositAcctSummary localDepositAcctSummary = (DepositAcctSummary)localObject;
        Currency localCurrency2 = localDepositAcctSummary.getDisplayTotalDebits();
        if (localCurrency2 != null)
        {
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getDisplayTotalCreditsTotalString()
  {
    Currency localCurrency = getDisplayTotalCreditsTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getDisplayTotalCreditsTotal()
  {
    if ((this.jdField_byte == null) || (this.jdField_byte.trim().length() == 0)) {
      return getTotalCreditsTotal();
    }
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_byte, this.locale);
    String str = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof DepositAcctSummary))
      {
        DepositAcctSummary localDepositAcctSummary = (DepositAcctSummary)localObject;
        Currency localCurrency2 = localDepositAcctSummary.getDisplayTotalCredits();
        if (localCurrency2 != null)
        {
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getDisplayBookValueTotalString()
  {
    Currency localCurrency = getDisplayBookValueTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getDisplayBookValueTotal()
  {
    if ((this.jdField_byte == null) || (this.jdField_byte.trim().length() == 0)) {
      return getBookValueTotal();
    }
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_byte, this.locale);
    String str = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof AssetAcctSummary))
      {
        AssetAcctSummary localAssetAcctSummary = (AssetAcctSummary)localObject;
        Currency localCurrency2 = localAssetAcctSummary.getDisplayBookValue();
        if (localCurrency2 != null)
        {
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getDisplayMarketValueTotalString()
  {
    Currency localCurrency = getDisplayMarketValueTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getDisplayMarketValueTotal()
  {
    if ((this.jdField_byte == null) || (this.jdField_byte.trim().length() == 0)) {
      return getMarketValueTotal();
    }
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_byte, this.locale);
    String str = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof AssetAcctSummary))
      {
        AssetAcctSummary localAssetAcctSummary = (AssetAcctSummary)localObject;
        Currency localCurrency2 = localAssetAcctSummary.getDisplayMarketValue();
        if (localCurrency2 != null)
        {
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getDisplayCreditCardAvailCreditTotalString()
  {
    Currency localCurrency = getDisplayCreditCardAvailCreditTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getDisplayCreditCardAvailCreditTotal()
  {
    if ((this.jdField_byte == null) || (this.jdField_byte.trim().length() == 0)) {
      return getCreditCardAvailCreditTotal();
    }
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_byte, this.locale);
    String str = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof CreditCardAcctSummary))
      {
        CreditCardAcctSummary localCreditCardAcctSummary = (CreditCardAcctSummary)localObject;
        Currency localCurrency2 = localCreditCardAcctSummary.getDisplayAvailCredit();
        if (localCurrency2 != null)
        {
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getDisplayCreditCardAmtDueTotalString()
  {
    Currency localCurrency = getDisplayCreditCardAmtDueTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getDisplayCreditCardAmtDueTotal()
  {
    if ((this.jdField_byte == null) || (this.jdField_byte.trim().length() == 0)) {
      return getCreditCardAmtDueTotal();
    }
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_byte, this.locale);
    String str = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof CreditCardAcctSummary))
      {
        CreditCardAcctSummary localCreditCardAcctSummary = (CreditCardAcctSummary)localObject;
        Currency localCurrency2 = localCreditCardAcctSummary.getDisplayAmtDue();
        if (localCurrency2 != null)
        {
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getDisplayLoanAvailCreditTotalString()
  {
    Currency localCurrency = getDisplayLoanAvailCreditTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getDisplayLoanAvailCreditTotal()
  {
    if ((this.jdField_byte == null) || (this.jdField_byte.trim().length() == 0)) {
      return getLoanAvailCreditTotal();
    }
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_byte, this.locale);
    String str = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof LoanAcctSummary))
      {
        LoanAcctSummary localLoanAcctSummary = (LoanAcctSummary)localObject;
        Currency localCurrency2 = localLoanAcctSummary.getDisplayAvailCredit();
        if (localCurrency2 != null)
        {
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public String getDisplayLoanAmtDueTotalString()
  {
    Currency localCurrency = getDisplayLoanAmtDueTotal();
    if (localCurrency != null) {
      return String.valueOf(localCurrency.doubleValue());
    }
    return null;
  }
  
  public Currency getDisplayLoanAmtDueTotal()
  {
    if ((this.jdField_byte == null) || (this.jdField_byte.trim().length() == 0)) {
      return getLoanAmtDueTotal();
    }
    Currency localCurrency1 = new Currency(new BigDecimal(0.0D), this.jdField_byte, this.locale);
    String str = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Object localObject = localIterator.next();
      if ((localObject instanceof LoanAcctSummary))
      {
        LoanAcctSummary localLoanAcctSummary = (LoanAcctSummary)localObject;
        Currency localCurrency2 = localLoanAcctSummary.getDisplayAmtDue();
        if (localCurrency2 != null)
        {
          localCurrency1.addAmount(localCurrency2);
          if (str == null) {
            str = localCurrency2.getFormat();
          }
        }
      }
    }
    return localCurrency1;
  }
  
  public void setDisplayCurrency(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public void setDisplayFXRates(FXRates paramFXRates)
  {
    this.jdField_char = paramFXRates;
  }
  
  protected Currency convertCurrency(Currency paramCurrency)
  {
    if (this.jdField_char == null)
    {
      try
      {
        HashMap localHashMap = new HashMap();
        paramCurrency = FX.convertToBaseCurrency(this.jdField_case, paramCurrency, localHashMap);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
    }
    else
    {
      BigDecimal localBigDecimal = AccountUtil.getConversionFXRate(this.jdField_char, paramCurrency.getCurrencyCode(), this.jdField_case.getBaseCurrency());
      paramCurrency = AccountUtil.convertCurrency(paramCurrency.getCurrencyCode(), this.jdField_case.getBaseCurrency(), localBigDecimal, paramCurrency);
    }
    return paramCurrency;
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      Object localObject;
      if (paramString.equals("ACCOUNT_SUMMARY"))
      {
        localObject = AccountSummaries.this.createAccountSummary();
        ((AccountSummary)localObject).continueXMLParsing(getHandler());
      }
      else if (paramString.equals("DEPOSIT_ACCT_SUMMARY"))
      {
        localObject = AccountSummaries.this.createDepositAcctSummary();
        ((DepositAcctSummary)localObject).continueXMLParsing(getHandler());
      }
      else if (paramString.equals("ASSET_ACCT_SUMMARY"))
      {
        localObject = AccountSummaries.this.createAssetAcctSummary();
        ((AssetAcctSummary)localObject).continueXMLParsing(getHandler());
      }
      else if (paramString.equals("CREDIT_CARD_ACCT_SUMMARY"))
      {
        localObject = AccountSummaries.this.createCreditCardAcctSummary();
        ((CreditCardAcctSummary)localObject).continueXMLParsing(getHandler());
      }
      else if (paramString.equals("LOAN_ACCT_SUMMARY"))
      {
        localObject = AccountSummaries.this.createLoanAcctSummary();
        ((LoanAcctSummary)localObject).continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AccountSummaries
 * JD-Core Version:    0.7.0.1
 */