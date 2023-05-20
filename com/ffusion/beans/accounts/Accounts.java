package com.ffusion.beans.accounts;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.SecureUser;
import com.ffusion.beans.fx.FXRates;
import com.ffusion.beans.util.AccountUtil;
import com.ffusion.csil.core.FX;
import com.ffusion.util.FilteredList;
import com.ffusion.util.Strings;
import com.ffusion.util.XMLHandler;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class Accounts
  extends FilteredList
{
  public static final String ACCOUNTS = "ACCOUNTS";
  protected String datetype = "SHORT";
  protected String currencyFormat = "CURRENCY";
  private SecureUser jdField_char;
  private String jdField_byte = "USD";
  private String jdField_case = "";
  private FXRates jdField_else = null;
  
  public Accounts() {}
  
  public Accounts(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
  }
  
  public Account create(String paramString1, String paramString2, int paramInt)
  {
    Account localAccount = new Account(this.locale, paramString1, paramString2, paramInt);
    super.add(localAccount);
    return localAccount;
  }
  
  public Account create(String paramString1, String paramString2, String paramString3, int paramInt)
  {
    Account localAccount = new Account(this.locale, paramString1, paramString2, paramString3, paramInt);
    super.add(localAccount);
    return localAccount;
  }
  
  public Account create(String paramString, int paramInt)
  {
    Account localAccount = new Account(this.locale, paramString, paramInt);
    super.add(localAccount);
    return localAccount;
  }
  
  public Account createNoAdd(String paramString, int paramInt)
  {
    Account localAccount = new Account(this.locale, paramString, paramInt);
    return localAccount;
  }
  
  public Account create(String paramString)
  {
    Account localAccount = new Account(this.locale, paramString);
    super.add(localAccount);
    return localAccount;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      localAccount.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public void setSecureUser(SecureUser paramSecureUser)
  {
    this.jdField_char = paramSecureUser;
  }
  
  public SecureUser getSecureUser()
  {
    return this.jdField_char;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public String getCurrencyCode()
  {
    return this.jdField_byte;
  }
  
  public boolean add(Object paramObject)
  {
    Account localAccount = (Account)paramObject;
    localAccount.setLocale(this.locale);
    return super.add(localAccount);
  }
  
  public void set(Accounts paramAccounts)
  {
    Iterator localIterator = paramAccounts.iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if (localAccount != null) {
        add(localAccount);
      }
    }
  }
  
  public Account getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if (localAccount.getID().equals(paramString))
      {
        localObject = localAccount;
        break;
      }
    }
    return localObject;
  }
  
  public Account getByAccountNumberAndType(String paramString, int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if ((localAccount.getNumber().equals(paramString)) && (localAccount.getTypeValue() == paramInt))
      {
        localObject = localAccount;
        break;
      }
    }
    return localObject;
  }
  
  public Account getByNumberAndBankID(String paramString1, String paramString2)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if ((localAccount.getNumber().equals(paramString1)) && (localAccount.getBankID().equals(paramString2)))
      {
        localObject = localAccount;
        break;
      }
    }
    return localObject;
  }
  
  public Account getByNumberAndBankIDAndRoutingNumber(String paramString1, String paramString2, String paramString3)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if ((localAccount.getNumber().equals(paramString1)) && (localAccount.getBankID().equals(paramString2)) && (Strings.compareTo(localAccount.getRoutingNum(), paramString3) == 0))
      {
        localObject = localAccount;
        break;
      }
    }
    return localObject;
  }
  
  public Account getByNumberAndRoutingNum(String paramString1, String paramString2)
  {
    Object localObject = null;
    if ((paramString1 != null) && (paramString2 != null))
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        if ((paramString1.equals(localAccount.getNumber())) && (Strings.compareTo(paramString2, localAccount.getRoutingNum()) == 0))
        {
          localObject = localAccount;
          break;
        }
      }
    }
    return localObject;
  }
  
  public Account getByNumberAndBIC(String paramString1, String paramString2)
  {
    Object localObject = null;
    if ((paramString1 != null) && (paramString2 != null))
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        Account localAccount = (Account)localIterator.next();
        if ((paramString1.equals(localAccount.getNumber())) && (Strings.compareTo(paramString2, localAccount.getBicAccount()) == 0))
        {
          localObject = localAccount;
          break;
        }
      }
    }
    return localObject;
  }
  
  public Account getByIDAndBankIDAndRoutingNum(String paramString1, String paramString2, String paramString3)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if ((localAccount.getID().equals(paramString1)) && (localAccount.getBankID().equals(paramString2)) && (Strings.compareTo(localAccount.getRoutingNum(), paramString3) == 0))
      {
        localObject = localAccount;
        break;
      }
    }
    return localObject;
  }
  
  public Account getByIDAndRoutingNum(String paramString1, String paramString2)
  {
    Object localObject = null;
    Account localAccount = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localAccount = (Account)localIterator.next();
      if ((localAccount.getID().equals(paramString1)) && (localAccount.getRoutingNum().equals(paramString2))) {
        localObject = localAccount;
      }
    }
    return localObject;
  }
  
  public Account getByTransactionID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if (localAccount.getTransactionID().equals(paramString))
      {
        localObject = localAccount;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if (localAccount.getID().equals(paramString))
      {
        localObject = localAccount;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
  
  public String getAccountsClosingBalanceTotal()
  {
    double d = 0.0D;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      Balance localBalance = localAccount.getClosingBalance();
      if ((localBalance != null) && (localBalance.getAmountValue() != null))
      {
        Currency localCurrency = localBalance.getAmountValue();
        if ((this.jdField_char != null) && (!localCurrency.getCurrencyCode().equals(this.jdField_char.getBaseCurrency()))) {
          localCurrency = convertCurrency(localCurrency);
        }
        d += localCurrency.doubleValue();
      }
    }
    return String.valueOf(d);
  }
  
  public String getAccountsCurrentBalanceTotal()
  {
    double d = 0.0D;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      Balance localBalance = localAccount.getCurrentBalance();
      if ((localBalance != null) && (localBalance.getAmountValue() != null))
      {
        Currency localCurrency = localBalance.getAmountValue();
        if ((this.jdField_char != null) && (!localCurrency.getCurrencyCode().equals(this.jdField_char.getBaseCurrency()))) {
          localCurrency = convertCurrency(localCurrency);
        }
        d += localCurrency.doubleValue();
      }
    }
    return String.valueOf(d);
  }
  
  public String getAccountsIntradayCurrentBalanceTotal()
  {
    double d = 0.0D;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      Balance localBalance = localAccount.getIntradayCurrentBalance();
      if ((localBalance != null) && (localBalance.getAmountValue() != null))
      {
        Currency localCurrency = localBalance.getAmountValue();
        if ((this.jdField_char != null) && (!localCurrency.getCurrencyCode().equals(this.jdField_char.getBaseCurrency()))) {
          localCurrency = convertCurrency(localCurrency);
        }
        d += localCurrency.doubleValue();
      }
    }
    return String.valueOf(d);
  }
  
  public Currency getDisplayAccountsAvailableBalanceTotalCurrency()
  {
    if ((this.jdField_case == null) || (this.jdField_case.trim().length() == 0)) {
      return getAccountsAvailableBalanceTotalCurrency();
    }
    Currency localCurrency = new Currency(new BigDecimal(getDisplayAccountsAvailableBalanceTotal()), this.jdField_case, this.locale);
    return localCurrency;
  }
  
  public String getAccountsAvailableBalanceTotal()
  {
    double d = 0.0D;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      Balance localBalance = localAccount.getAvailableBalance();
      if (localBalance != null)
      {
        Currency localCurrency = localBalance.getAmountValue();
        if ((this.jdField_char != null) && (!localCurrency.getCurrencyCode().equals(this.jdField_char.getBaseCurrency()))) {
          localCurrency = convertCurrency(localCurrency);
        }
        d += localCurrency.doubleValue();
      }
    }
    return String.valueOf(d);
  }
  
  public String getAccountsIntradayAvailableBalanceTotal()
  {
    double d = 0.0D;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      Balance localBalance = localAccount.getIntradayAvailableBalance();
      if (localBalance != null)
      {
        Currency localCurrency = localBalance.getAmountValue();
        if ((this.jdField_char != null) && (!localCurrency.getCurrencyCode().equals(this.jdField_char.getBaseCurrency()))) {
          localCurrency = convertCurrency(localCurrency);
        }
        d += localCurrency.doubleValue();
      }
    }
    return String.valueOf(d);
  }
  
  public Currency getAccountsAvailableBalanceTotalCurrency()
  {
    Currency localCurrency;
    if (this.jdField_char != null) {
      localCurrency = new Currency(new BigDecimal(getAccountsAvailableBalanceTotal()), this.jdField_char.getBaseCurrency(), this.locale);
    } else {
      localCurrency = new Currency(new BigDecimal(getAccountsAvailableBalanceTotal()), this.jdField_byte, this.locale);
    }
    return localCurrency;
  }
  
  public Currency getAccountsClosingBalanceTotalCurrency()
  {
    Currency localCurrency;
    if (this.jdField_char != null) {
      localCurrency = new Currency(new BigDecimal(getAccountsClosingBalanceTotal()), this.jdField_char.getBaseCurrency(), this.locale);
    } else {
      localCurrency = new Currency(new BigDecimal(getAccountsClosingBalanceTotal()), this.jdField_byte, this.locale);
    }
    return localCurrency;
  }
  
  public Currency getAccountsCurrentBalanceTotalCurrency()
  {
    Currency localCurrency;
    if (this.jdField_char != null) {
      localCurrency = new Currency(new BigDecimal(getAccountsCurrentBalanceTotal()), this.jdField_char.getBaseCurrency(), this.locale);
    } else {
      localCurrency = new Currency(new BigDecimal(getAccountsCurrentBalanceTotal()), this.jdField_byte, this.locale);
    }
    return localCurrency;
  }
  
  public Currency getAccountsIntradayCurrentBalanceTotalCurrency()
  {
    Currency localCurrency;
    if (this.jdField_char != null) {
      localCurrency = new Currency(new BigDecimal(getAccountsIntradayCurrentBalanceTotal()), this.jdField_char.getBaseCurrency(), this.locale);
    } else {
      localCurrency = new Currency(new BigDecimal(getAccountsIntradayCurrentBalanceTotal()), this.jdField_byte, this.locale);
    }
    return localCurrency;
  }
  
  public String getAccountsCurrentBalanceTotalFormatted()
  {
    Currency localCurrency;
    if (this.jdField_char != null) {
      localCurrency = new Currency(new BigDecimal(getAccountsCurrentBalanceTotal()), this.jdField_char.getBaseCurrency(), this.locale);
    } else {
      localCurrency = new Currency(new BigDecimal(getAccountsCurrentBalanceTotal()), this.jdField_byte, this.locale);
    }
    return localCurrency.toString();
  }
  
  public String getAccountsAvailableBalanceTotalFormatted()
  {
    Currency localCurrency;
    if (this.jdField_char != null) {
      localCurrency = new Currency(new BigDecimal(getAccountsAvailableBalanceTotal()), this.jdField_char.getBaseCurrency(), this.locale);
    } else {
      localCurrency = new Currency(new BigDecimal(getAccountsAvailableBalanceTotal()), this.jdField_byte, this.locale);
    }
    return localCurrency.toString();
  }
  
  public void setFormat(String paramString)
  {
    this.currencyFormat = paramString;
  }
  
  public Account getPrimaryAccount()
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if (localAccount.getPrimaryAccount().equals("1"))
      {
        localObject = localAccount;
        break;
      }
    }
    return localObject;
  }
  
  public Accounts getCoreAccounts()
  {
    Accounts localAccounts = new Accounts();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if (localAccount.getCoreAccount().equals("1")) {
        localAccounts.add(localAccount);
      }
    }
    return localAccounts;
  }
  
  public Accounts getNonCoreAccounts()
  {
    Accounts localAccounts = new Accounts();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      if (localAccount.getCoreAccount().equals("0")) {
        localAccounts.add(localAccount);
      }
    }
    return localAccounts;
  }
  
  public int getNumTransactionsTotal()
  {
    int i = 0;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      i += localAccount.getNumTransactions();
    }
    return i;
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACCOUNTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Account)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ACCOUNTS");
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
  
  public String getDisplayAccountsClosingBalanceTotal()
  {
    if ((this.jdField_case == null) || (this.jdField_case.trim().length() == 0)) {
      return getAccountsClosingBalanceTotal();
    }
    double d = 0.0D;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      Balance localBalance = localAccount.getDisplayClosingBalance();
      if ((localBalance != null) && (localBalance.getAmountValue() != null))
      {
        Currency localCurrency = localBalance.getAmountValue();
        d += localCurrency.doubleValue();
      }
    }
    return String.valueOf(d);
  }
  
  public String getDisplayAccountsCurrentBalanceTotal()
  {
    if ((this.jdField_case == null) || (this.jdField_case.trim().length() == 0)) {
      return getAccountsCurrentBalanceTotal();
    }
    double d = 0.0D;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      Balance localBalance = localAccount.getDisplayCurrentBalance();
      if ((localBalance != null) && (localBalance.getAmountValue() != null))
      {
        Currency localCurrency = localBalance.getAmountValue();
        d += localCurrency.doubleValue();
      }
    }
    return String.valueOf(d);
  }
  
  public String getDisplayAccountsIntradayCurrentBalanceTotal()
  {
    if ((this.jdField_case == null) || (this.jdField_case.trim().length() == 0)) {
      return getAccountsIntradayCurrentBalanceTotal();
    }
    double d = 0.0D;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      Balance localBalance = localAccount.getDisplayIntradayCurrentBalance();
      if ((localBalance != null) && (localBalance.getAmountValue() != null))
      {
        Currency localCurrency = localBalance.getAmountValue();
        d += localCurrency.doubleValue();
      }
    }
    return String.valueOf(d);
  }
  
  public String getDisplayAccountsAvailableBalanceTotal()
  {
    if ((this.jdField_case == null) || (this.jdField_case.trim().length() == 0)) {
      return getAccountsAvailableBalanceTotal();
    }
    double d = 0.0D;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      Balance localBalance = localAccount.getDisplayAvailableBalance();
      if ((localBalance != null) && (localBalance.getAmountValue() != null))
      {
        Currency localCurrency = localBalance.getAmountValue();
        d += localCurrency.doubleValue();
      }
    }
    return String.valueOf(d);
  }
  
  public String getDisplayAccountsIntradayAvailableBalanceTotal()
  {
    if ((this.jdField_case == null) || (this.jdField_case.trim().length() == 0)) {
      return getAccountsIntradayAvailableBalanceTotal();
    }
    double d = 0.0D;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Account localAccount = (Account)localIterator.next();
      Balance localBalance = localAccount.getDisplayIntradayAvailableBalance();
      if ((localBalance != null) && (localBalance.getAmountValue() != null))
      {
        Currency localCurrency = localBalance.getAmountValue();
        d += localCurrency.doubleValue();
      }
    }
    return String.valueOf(d);
  }
  
  public Currency getDisplayAccountsClosingBalanceTotalCurrency()
  {
    if ((this.jdField_case == null) || (this.jdField_case.trim().length() == 0)) {
      return getAccountsClosingBalanceTotalCurrency();
    }
    Currency localCurrency = new Currency(new BigDecimal(getDisplayAccountsClosingBalanceTotal()), this.jdField_case, this.locale);
    return localCurrency;
  }
  
  public Currency getDisplayAccountsCurrentBalanceTotalCurrency()
  {
    if ((this.jdField_case == null) || (this.jdField_case.trim().length() == 0)) {
      return getAccountsCurrentBalanceTotalCurrency();
    }
    Currency localCurrency = new Currency(new BigDecimal(getDisplayAccountsCurrentBalanceTotal()), this.jdField_case, this.locale);
    return localCurrency;
  }
  
  public Currency getDisplayAccountsIntradayCurrentBalanceTotalCurrency()
  {
    if ((this.jdField_case == null) || (this.jdField_case.trim().length() == 0)) {
      return getAccountsIntradayCurrentBalanceTotalCurrency();
    }
    Currency localCurrency = new Currency(new BigDecimal(getDisplayAccountsIntradayCurrentBalanceTotal()), this.jdField_case, this.locale);
    return localCurrency;
  }
  
  public String getDisplayAccountsCurrentBalanceTotalFormatted()
  {
    if ((this.jdField_case == null) || (this.jdField_case.trim().length() == 0)) {
      return getAccountsCurrentBalanceTotalFormatted();
    }
    Currency localCurrency = new Currency(new BigDecimal(getDisplayAccountsCurrentBalanceTotal()), this.jdField_case, this.locale);
    return localCurrency.toString();
  }
  
  public String getDisplayAccountsAvailableBalanceTotalFormatted()
  {
    if ((this.jdField_case == null) || (this.jdField_case.trim().length() == 0)) {
      return getAccountsAvailableBalanceTotalFormatted();
    }
    Currency localCurrency = new Currency(new BigDecimal(getDisplayAccountsAvailableBalanceTotal()), this.jdField_case, this.locale);
    return localCurrency.toString();
  }
  
  public String getDisplayCurrency()
  {
    return this.jdField_case;
  }
  
  public void setDisplayCurrency(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public void setDisplayFXRates(FXRates paramFXRates)
  {
    this.jdField_else = paramFXRates;
  }
  
  protected Currency convertCurrency(Currency paramCurrency)
  {
    if (this.jdField_else == null)
    {
      try
      {
        HashMap localHashMap = new HashMap();
        paramCurrency = FX.convertToBaseCurrency(this.jdField_char, paramCurrency, localHashMap);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
    }
    else
    {
      BigDecimal localBigDecimal = AccountUtil.getConversionFXRate(this.jdField_else, paramCurrency.getCurrencyCode(), this.jdField_char.getBaseCurrency());
      paramCurrency = AccountUtil.convertCurrency(paramCurrency.getCurrencyCode(), this.jdField_char.getBaseCurrency(), localBigDecimal, paramCurrency);
    }
    return paramCurrency;
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("ACCOUNT"))
      {
        Account localAccount = Accounts.this.create(null);
        localAccount.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.Accounts
 * JD-Core Version:    0.7.0.1
 */