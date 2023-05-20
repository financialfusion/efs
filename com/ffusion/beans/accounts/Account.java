package com.ffusion.beans.accounts;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Contact;
import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.banking.Transaction;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.beans.util.HistoryTracker;
import com.ffusion.fx.FXUtil;
import com.ffusion.util.ILocalizable;
import com.ffusion.util.LocaleUtil;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.beans.LocalizableAccountID;
import com.ffusion.util.beans.LocalizableDate;
import com.ffusion.util.beans.LocalizableString;
import com.ffusion.util.logging.DebugLog;
import com.ffusion.util.settings.AccountSettings;
import com.ffusion.util.settings.SystemException;
import java.math.BigDecimal;
import java.text.Collator;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.StringTokenizer;
import java.util.logging.Level;

public class Account
  extends ExtendABean
  implements AccountMaskConsts, AccountTypes, AccountStatus, AccountFilters, Comparable, AccountGroups
{
  public static final String ACCT_NUMBER_TYPE_SEPARATOR = "-";
  public static final int SYSTEM_TYPE_DEPOSIT = 1;
  public static final int SYSTEM_TYPE_ASSET = 2;
  public static final int SYSTEM_TYPE_LOAN = 3;
  public static final int SYSTEM_TYPE_CREDITCARD = 4;
  public static final int SYSTEM_TYPE_OTHER = 99;
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.accounts.resources";
  public static final String KEY_ACCOUNT_DISPLAY_TEXT = "AccountDisplayText";
  public static final String KEY_ACCOUNT_DISPLAY_TEXT_ROUTING_NUM = "AccountDisplayTextRoutingNum";
  public static final String KEY_CONSUMER_ACCOUNT_DISPLAY_TEXT = "ConsumerAccountDisplayText";
  public static final String KEY_CONSUMER_ACCOUNT_MENU_DISPLAY_TEXT = "ConsumerAccountMenuDisplayText";
  public static final String KEY_CONSUMER_ACCOUNT_EXTERNAL_XFER_MENU_DISPLAY_TEXT = "ConsumerAccountExtXferMenuDisplayText";
  public static final String KEY_CONSUMER_ACCOUNT_EXTERNAL_MENU_DISPLAY_TEXT = "ConsumerAccountExtMenuDisplayText";
  public static final String KEY_CONSUMER_ACCOUNT_EXPORT_DISPLAY_TEXT = "ConsumerAccountExportDisplayText";
  public static final String KEY_ACCOUNT_DISPLAY_TEXT_NICKNAME_CURR = "AccountDisplayTextNickNameCurrency";
  public static final String KEY_ACCOUNT_DISPLAY_TEXT_CURRENCY = "AccountDisplayTextCurrency";
  public static final String KEY_ACCOUNT_GROUP_DISPLAY_TEXT = "AccountGroupDisplayText";
  public static final String KEY_ACCOUNT_TYPE = "AccountType";
  public static final String KEY_ACCOUNT_STATUS = "AccountStatus";
  public static final String KEY_ACCOUNT_GROUP = "AccountGroup";
  public static final String KEY_POSITIVE_PAY = "PositivePay";
  public static final String KEY_ZBA = "ZBAFlag";
  public static final String KEY_SHOW_PREV_DAY_LEDGER = "ShowPreviousDayOpeningLedger";
  public static final String AVAILABLE_BALANCE_FIELD = "AvailableBalanceField";
  public static final String CURRENT_BALANCE_FIELD = "CurrentBalanceField";
  public static final String TYPESTRING = "TYPESTRING";
  private static final String BEAN_NAME = Account.class.getName();
  private String i1;
  private String jo;
  private String iW;
  private String i6;
  private int i2;
  private int iX;
  private String jh;
  private Balance jg;
  private Balance je;
  private Balance iR;
  private Balance i7;
  private Balance jb;
  private boolean jm;
  private Transactions iZ;
  private ArrayList i5;
  private String jf;
  private String iQ;
  private int iT;
  private int iV;
  private String iK;
  private String jc;
  private String iU;
  private String i3;
  private String iM;
  private String iJ;
  private String jl;
  private String i0;
  private String jj;
  private int jq = -1;
  private Contact iP;
  private boolean i9;
  private boolean iI;
  private boolean ji;
  private boolean ja;
  private String i8;
  private int iS = 0;
  private String iN = null;
  private String jd = "";
  private BigDecimal i4 = new BigDecimal(0.0D);
  private Balance iO;
  private Balance iY;
  private Balance iL;
  private Balance jp;
  private Balance jn;
  private Balance jk;
  public static String[] acctTypeNames = { "Checking", "Savings", "Credit", "Loan", "Mortgage", "HomeEquity", "CreditLine", "CD", "IRA", "Stocks", "Brokerage", "MoneyMarket", "BusinessLoan", "Other" };
  public static String[] acctTransactionFilters = { "TransferTo", "TransferFrom", "Transactions", "BillPay", "ExternalTransferTo", "ExternalTransferFrom" };
  public static final String NOT_PRIMARY = "0";
  public static final String PRIMARY = "1";
  public static final String NOT_CORE = "0";
  public static final String CORE = "1";
  public static final String NOT_CORE_EXT_XFER = "2";
  public static final String NOT_ALLOWED = "0";
  public static final String ALLOWED = "1";
  public static final String NOT_PERSONAL = "0";
  public static final String PERSONAL = "1";
  
  public Account() {}
  
  protected Account(Locale paramLocale, String paramString1, String paramString2, String paramString3, int paramInt)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
    this.i1 = paramString2;
    this.jo = paramString3;
    this.iW = paramString1;
    setType(paramInt);
    this.iZ = new Transactions(paramLocale);
    this.jm = false;
  }
  
  protected Account(Locale paramLocale, String paramString1, String paramString2, int paramInt)
  {
    this(paramLocale, paramString2, paramInt);
    this.iW = paramString1;
  }
  
  protected Account(Locale paramLocale, String paramString, int paramInt)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
    setID(paramString, String.valueOf(paramInt));
    setType(paramInt);
    this.iZ = new Transactions(paramLocale);
    this.jm = false;
  }
  
  protected Account(Locale paramLocale, String paramString)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
    setID(paramString);
    this.iZ = new Transactions(paramLocale);
    this.jm = false;
    this.jg = new Balance(paramLocale);
    this.je = new Balance(paramLocale);
    this.iR = new Balance(paramLocale);
    this.i7 = new Balance(paramLocale);
    this.jb = new Balance(paramLocale);
  }
  
  public Account(Locale paramLocale, String paramString1, String paramString2, String paramString3)
  {
    super(paramLocale);
    setID(paramString1);
    setRoutingNum(paramString2);
    setBankID(paramString3);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "TYPE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    Account localAccount = (Account)paramObject;
    int i = 1;
    if ((paramString.equals("NUMBER")) && (getNumber() != null) && (localAccount.getNumber() != null))
    {
      i = numStringCompare(getNumber(), localAccount.getNumber());
    }
    else if ((paramString.equals("CURRENCY")) && (getCurrencyCode() != null) && (localAccount.getCurrencyCode() != null))
    {
      i = localCollator.compare(getCurrencyCode().toLowerCase(this.locale), localAccount.getCurrencyCode().toLowerCase(this.locale));
    }
    else if ((paramString.equals("NICKNAME")) && (getNickName() != null) && (localAccount.getNickName() != null))
    {
      i = localCollator.compare(getNickName().toLowerCase(this.locale), localAccount.getNickName().toLowerCase(this.locale));
    }
    else if ((paramString.equals("CLOSINGBALANCEAMOUNT")) && (getClosingBalance() != null) && (localAccount.getClosingBalance() != null))
    {
      i = getClosingBalance().compare(localAccount.getClosingBalance(), "AMOUNT");
    }
    else if ((paramString.equals("CURRENTBALANCEAMOUNT")) && (getCurrentBalance() != null) && (localAccount.getCurrentBalance() != null))
    {
      i = getCurrentBalance().compare(localAccount.getCurrentBalance(), "AMOUNT");
    }
    else if ((paramString.equals("AVAILABLEBALANCEAMOUNT")) && (getAvailableBalance() != null) && (localAccount.getAvailableBalance() != null))
    {
      i = getAvailableBalance().compare(localAccount.getAvailableBalance(), "AMOUNT");
    }
    else if ((paramString.equals("CLOSINGBALANCEDATE")) && (getClosingBalance() != null) && (localAccount.getClosingBalance() != null))
    {
      i = getClosingBalance().compare(localAccount.getClosingBalance(), "DATE");
    }
    else if ((paramString.equals("CURRENTBALANCEDATE")) && (getCurrentBalance() != null) && (localAccount.getCurrentBalance() != null))
    {
      i = getCurrentBalance().compare(localAccount.getCurrentBalance(), "DATE");
    }
    else if ((paramString.equals("AVAILABLEBALANCEDATE")) && (getAvailableBalance() != null) && (localAccount.getAvailableBalance() != null))
    {
      i = getAvailableBalance().compare(localAccount.getAvailableBalance(), "DATE");
    }
    else if (paramString.equals("INTRADAYCURRENTBALANCE"))
    {
      if (localAccount.getIntradayCurrentBalance() == null) {
        i = getIntradayCurrentBalance() == null ? 0 : 1;
      } else if (getIntradayCurrentBalance() == null) {
        i = -1;
      } else {
        i = getIntradayCurrentBalance().compare(localAccount.getIntradayCurrentBalance(), "AMOUNT");
      }
    }
    else if (paramString.equals("STATUS"))
    {
      i = getStatus() - localAccount.getStatus();
    }
    else if (paramString.equals("TYPE"))
    {
      i = getTypeValue() - localAccount.getTypeValue();
    }
    else if ((paramString.equals("BANKID")) && (getBankID() != null) && (localAccount.getBankID() != null))
    {
      i = numStringCompare(getBankID(), localAccount.getBankID());
    }
    else if (paramString.equals("TYPESTRING"))
    {
      i = localCollator.compare(getType(), localAccount.getType());
    }
    else if ((paramString.equals("ID")) && (getID() != null) && (localAccount.getID() != null))
    {
      i = localCollator.compare(getID(), localAccount.getID());
    }
    else if (paramString.equals("ACCOUNTGROUPSTRING"))
    {
      i = localCollator.compare(getGroupString(), localAccount.getGroupString());
    }
    else
    {
      int j;
      if ((paramString.equals("ACCOUNTGROUP")) && (getAccountGroup() != 0))
      {
        j = getAccountGroup() - localAccount.getAccountGroup();
        i = j == 0 ? 0 : j < 0 ? -1 : 1;
      }
      else if (paramString.equals("NUM_TRANSACTIONS"))
      {
        j = getNumTransactions() - localAccount.getNumTransactions();
        i = j == 0 ? 0 : j < 0 ? -1 : 1;
      }
      else if ((paramString.equals("BANKNAME")) && (getBankName() != null) && (localAccount.getBankName() != null))
      {
        i = localCollator.compare(getBankName().toLowerCase(this.locale), localAccount.getBankName().toLowerCase(this.locale));
      }
      else if ((paramString.equals("PRIMARYACCOUNT")) && (getPrimaryAccount() != null) && (localAccount.getPrimaryAccount() != null))
      {
        i = localCollator.compare(getPrimaryAccount(), localAccount.getPrimaryAccount());
      }
      else if ((paramString.equals("COREACCOUNT")) && (getCoreAccount() != null) && (localAccount.getCoreAccount() != null))
      {
        i = localCollator.compare(getCoreAccount(), localAccount.getCoreAccount());
      }
      else if ((paramString.equals("ROUTINGNUM")) && (getRoutingNum() != null) && (localAccount.getRoutingNum() != null))
      {
        i = localCollator.compare(getRoutingNum(), localAccount.getRoutingNum());
      }
      else if ((paramString.equals("BICACCOUNT")) && (getBicAccount() != null) && (localAccount.getBicAccount() != null))
      {
        i = localCollator.compare(getBicAccount(), localAccount.getBicAccount());
      }
      else if ((paramString.equals("POSITIVEPAY")) && (getPositivePay() != null) && (localAccount.getPositivePay() != null))
      {
        i = localCollator.compare(getPositivePay(), localAccount.getPositivePay());
      }
      else if ((paramString.equals("PERSONALACCOUNT")) && (getPersonalAccount() != null) && (localAccount.getPersonalAccount() != null))
      {
        i = localCollator.compare(getPersonalAccount(), localAccount.getPersonalAccount());
      }
      else if ((paramString.equals("ZBAFLAG")) && (getZBAFlag() != null) && (localAccount.getZBAFlag() != null))
      {
        i = localCollator.compare(getZBAFlag(), localAccount.getZBAFlag());
      }
      else if ((paramString.equals("SHOWPREVDAYOPENINGLEDGER")) && (this.jj != null) && (localAccount.jj != null))
      {
        i = localCollator.compare(this.jj, localAccount.jj);
      }
      else if ("ISMASTER".equals(paramString))
      {
        i = Boolean.toString(isMaster()).compareTo(Boolean.toString(localAccount.isMaster()));
      }
      else if ("INCLUDEZBACREDITINROLLUP".equals(paramString))
      {
        i = Boolean.toString(getIncludeZBACreditInRollupValue()).compareTo(Boolean.toString(localAccount.getIncludeZBACreditInRollupValue()));
      }
      else if ("INCLUDEZBADEBITINROLLUP".equals(paramString))
      {
        i = Boolean.toString(getIncludeZBADebitInRollupValue()).compareTo(Boolean.toString(localAccount.getIncludeZBADebitInRollupValue()));
      }
      else if ("WITHHOLDNONZEROBALANCESUBACCOUNTS".equals(paramString))
      {
        i = Boolean.toString(getWithholdNonZeroBalanceSubAccountsValue()).compareTo(Boolean.toString(localAccount.getWithholdNonZeroBalanceSubAccountsValue()));
      }
      else if ("LOCATIONID".equals(paramString))
      {
        if (getLocationID() != null) {
          i = localCollator.compare(getLocationID(), localAccount.getLocationID());
        } else if (localAccount.getLocationID() == null) {
          i = 0;
        } else {
          i = localCollator.compare(localAccount.getLocationID(), getLocationID());
        }
      }
      else if ("STRIPPED_ACCOUNT_NUMBER".equals(paramString))
      {
        if (this.iN != null) {
          i = localCollator.compare(this.iN, localAccount.iN);
        } else if (localAccount.iN == null) {
          i = 0;
        } else {
          i = -1;
        }
      }
      else if ("DIRECTORY_ID".equals(paramString))
      {
        i = this.iS - localAccount.iS;
      }
      else if ((paramString.equals("CURRENT_BALANCE")) && (getHashEntry("CURRENT_BALANCE") != null) && (localAccount.getHashEntry("CURRENT_BALANCE") != null))
      {
        i = Currency.compare(new Currency(getHashEntry("CURRENT_BALANCE"), getCurrencyCode(), this.locale), new Currency(localAccount.getHashEntry("CURRENT_BALANCE"), localAccount.getCurrencyCode(), localAccount.locale));
      }
      else if ((paramString.equals("PRIOR_DAY_BALANCE")) && (getHashEntry("PRIOR_DAY_BALANCE") != null) && (localAccount.getHashEntry("PRIOR_DAY_BALANCE") != null))
      {
        i = Currency.compare(new Currency(getHashEntry("PRIOR_DAY_BALANCE"), getCurrencyCode(), this.locale), new Currency(localAccount.getHashEntry("PRIOR_DAY_BALANCE"), localAccount.getCurrencyCode(), localAccount.locale));
      }
      else if ((paramString.equals("AVAILABLE_BALANCE")) && (getHashEntry("AVAILABLE_BALANCE") != null) && (localAccount.getHashEntry("AVAILABLE_BALANCE") != null))
      {
        i = Currency.compare(new Currency(getHashEntry("AVAILABLE_BALANCE"), getCurrencyCode(), this.locale), new Currency(localAccount.getHashEntry("AVAILABLE_BALANCE"), localAccount.getCurrencyCode(), localAccount.locale));
      }
      else if (paramString.equals("AvailableBalanceField"))
      {
        if (localAccount.getAvailableBalance() == null) {
          i = getAvailableBalance() == null ? 0 : 1;
        } else if (getAvailableBalance() == null) {
          i = -1;
        } else {
          i = getAvailableBalance().compare(localAccount.getAvailableBalance(), "AMOUNT");
        }
      }
      else if (paramString.equals("CLOSINGBALANCE"))
      {
        if (localAccount.getClosingBalance() == null) {
          i = getClosingBalance() == null ? 0 : 1;
        } else if (getClosingBalance() == null) {
          i = -1;
        } else {
          i = getClosingBalance().compare(localAccount.getClosingBalance(), "AMOUNT");
        }
      }
      else if (paramString.equals("CurrentBalanceField"))
      {
        if (localAccount.getCurrentBalance() == null) {
          i = getCurrentBalance() == null ? 0 : 1;
        } else if (getCurrentBalance() == null) {
          i = -1;
        } else {
          i = getCurrentBalance().compare(localAccount.getCurrentBalance(), "AMOUNT");
        }
      }
      else if ((paramString.equals("CURRENCY_CODE")) && (getCurrencyCode() != null) && (localAccount.getCurrencyCode() != null))
      {
        i = getCurrencyCode().compareToIgnoreCase(localAccount.getCurrencyCode());
      }
      else
      {
        i = super.compare(paramObject, paramString);
      }
    }
    return i;
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=<>!", true);
    if ((localStringTokenizer.countTokens() == 3) || (localStringTokenizer.countTokens() == 4))
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      String str3 = localStringTokenizer.nextToken();
      if (localStringTokenizer.countTokens() == 1)
      {
        str2 = str2 + str3;
        str3 = localStringTokenizer.nextToken();
      }
      return isFilterablePreParsed(str1, str2, str3);
    }
    if (this.i5 != null) {
      return this.i5.contains(paramString);
    }
    return false;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("NUMBER")) && (getNumber() != null)) {
      return isFilterable(getNumber(), paramString2, paramString3);
    }
    if ((paramString1.equals("NICKNAME")) && (getNickName() != null)) {
      return isFilterable(getNickName(), paramString2, paramString3);
    }
    if ((paramString1.equals("CURRENCY_CODE")) && (getCurrencyCode() != null)) {
      return isFilterable(getCurrencyCode(), paramString2, paramString3);
    }
    if (paramString1.equals("STATUS")) {
      return isFilterable(String.valueOf(getStatus()), paramString2, paramString3);
    }
    if ((paramString1.equals("TYPE")) && (getTypeValue() != 0)) {
      return isFilterable(String.valueOf(getTypeValue()), paramString2, paramString3);
    }
    if ((paramString1.equals("TYPESTRING")) && (getType() != null)) {
      return isFilterable(getType(), paramString2, paramString3);
    }
    if ((paramString1.equals("BANKID")) && (getBankID() != null)) {
      return isFilterable(getBankID(), paramString2, paramString3);
    }
    if ((paramString1.equals("ID")) && (getID() != null)) {
      return isFilterable(getID(), paramString2, paramString3);
    }
    if ((paramString1.equals("BANKNAME")) && (getBankName() != null)) {
      return isFilterable(getBankName(), paramString2, paramString3);
    }
    if ((paramString1.equals("PRIMARYACCOUNT")) && (getPrimaryAccount() != null)) {
      return isFilterable(getPrimaryAccount(), paramString2, paramString3);
    }
    if ((paramString1.equals("COREACCOUNT")) && (getCoreAccount() != null)) {
      return isFilterable(getCoreAccount(), paramString2, paramString3);
    }
    if ((paramString1.equals("ROUTINGNUM")) && (getRoutingNum() != null)) {
      return isFilterable(getRoutingNum(), paramString2, paramString3);
    }
    if ((paramString1.equals("BICACCOUNT")) && (getBicAccount() != null)) {
      return isFilterable(getBicAccount(), paramString2, paramString3);
    }
    if ((paramString1.equals("POSITIVEPAY")) && (getPositivePay() != null)) {
      return isFilterable(getPositivePay(), paramString2, paramString3);
    }
    if ((paramString1.equals("PERSONALACCOUNT")) && (getPersonalAccount() != null)) {
      return isFilterable(getPersonalAccount(), paramString2, paramString3);
    }
    if ((paramString1.equals("ZBAFLAG")) && (getZBAFlag() != null)) {
      return isFilterable(getZBAFlag(), paramString2, paramString3);
    }
    if ((paramString1.equals("SHOWPREVDAYOPENINGLEDGER")) && (getShowPreviousDayOpeningLedger() != null)) {
      return isFilterable(getShowPreviousDayOpeningLedger(), paramString2, paramString3);
    }
    if ((paramString1.equals("ACCOUNTGROUP")) && (getAccountGroup() != 0)) {
      return isFilterable(String.valueOf(getAccountGroup()), paramString2, paramString3);
    }
    if ((paramString1.equals("ACCOUNTGROUPSTRING")) && (getGroupString() != ResourceUtil.getString("AccountGroup0", "com.ffusion.beans.accounts.resources", this.locale))) {
      return isFilterable(String.valueOf(getGroupString()), paramString2, paramString3);
    }
    if (paramString1.equals("NUM_TRANSACTIONS")) {
      return isFilterable(String.valueOf(getNumTransactions()), paramString2, paramString3);
    }
    if (paramString1.equals("ISMASTER")) {
      return isFilterable(String.valueOf(isMaster()), paramString2, paramString3);
    }
    if ("INCLUDEZBACREDITINROLLUP".equals(paramString1)) {
      return isFilterable(String.valueOf(getIncludeZBACreditInRollupValue()), paramString2, paramString3);
    }
    if ("INCLUDEZBADEBITINROLLUP".equals(paramString1)) {
      return isFilterable(String.valueOf(getIncludeZBADebitInRollupValue()), paramString2, paramString3);
    }
    if ("WITHHOLDNONZEROBALANCESUBACCOUNTS".equals(paramString1)) {
      return isFilterable(String.valueOf(getWithholdNonZeroBalanceSubAccountsValue()), paramString2, paramString3);
    }
    if ("DIRECTORY_ID".equals(paramString1)) {
      return isFilterable(String.valueOf(this.iS), paramString2, paramString3);
    }
    if ("STRIPPED_ACCOUNT_NUMBER".equals(paramString1)) {
      return isFilterable(this.iN, paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String getTrackingID()
  {
    return this.jf;
  }
  
  public void setTrackingID(String paramString)
  {
    this.jf = paramString;
  }
  
  public String getID()
  {
    return this.i1;
  }
  
  public void setID(String paramString1, String paramString2)
  {
    try
    {
      setID(AccountSettings.buildAccountId(paramString1, paramString2));
      setNumber(paramString1);
      try
      {
        setType(Integer.parseInt(paramString2));
      }
      catch (Exception localException)
      {
        setType(0);
      }
    }
    catch (SystemException localSystemException) {}
  }
  
  public void setID(String paramString)
  {
    if ((this.i1 != null) && (!this.i1.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "Account.setID.  Cannot modify the value of account ID.  Use constructor");
      return;
    }
    if (paramString != null) {
      this.i1 = paramString;
    }
  }
  
  public void setNumber(String paramString)
  {
    if ((this.jo != null) && (!this.jo.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "Account.setNumber.  Cannot modify the value of account number.  Use constructor");
      return;
    }
    if (paramString != null) {
      this.jo = paramString;
    }
  }
  
  public String getNumber()
  {
    return this.jo;
  }
  
  public String getNumberMasked()
  {
    try
    {
      return AccountSettings.getMaskedAccountNumber(getNumber(), 4, 'x');
    }
    catch (SystemException localSystemException) {}
    return "";
  }
  
  public void setNickName(String paramString)
  {
    this.i6 = paramString;
  }
  
  public String getNickName()
  {
    return this.i6;
  }
  
  public void setType(int paramInt)
  {
    this.i2 = paramInt;
    setTypeFilter(paramInt);
  }
  
  public int getTypeValue()
  {
    return this.i2;
  }
  
  public String getType()
  {
    return getType(this.i2, this.locale);
  }
  
  public static String getType(int paramInt, Locale paramLocale)
  {
    return ResourceUtil.getString("AccountType" + paramInt, "com.ffusion.beans.accounts.resources", paramLocale);
  }
  
  public void setStatus(int paramInt)
  {
    if ((paramInt > -1) && (paramInt < 3)) {
      this.iX = paramInt;
    }
  }
  
  public int getStatus()
  {
    return this.iX;
  }
  
  public String getStatusString()
  {
    return getStatusString(this.iX, this.locale);
  }
  
  public static String getStatusString(int paramInt, Locale paramLocale)
  {
    return ResourceUtil.getString("AccountStatus" + paramInt, "com.ffusion.beans.accounts.resources", paramLocale);
  }
  
  public String getGroupString()
  {
    return ResourceUtil.getString("AccountGroup" + this.iT, "com.ffusion.beans.accounts.resources", this.locale);
  }
  
  public void setTypeFilter(int paramInt)
  {
    if (this.i5 != null) {
      for (int i = this.i5.size() - 1; i >= 0; i--)
      {
        String str = (String)this.i5.get(i);
        for (int j = 0; j < acctTypeNames.length; j++) {
          if (str.equals(acctTypeNames[j]))
          {
            this.i5.remove(i);
            break;
          }
        }
      }
    }
    switch (paramInt)
    {
    case 1: 
      setFilterable("Checking");
      break;
    case 2: 
      setFilterable("Savings");
      break;
    case 3: 
      setFilterable("Credit");
      break;
    case 4: 
      setFilterable("Loan");
      break;
    case 5: 
      setFilterable("Mortgage");
      break;
    case 6: 
      setFilterable("HomeEquity");
      break;
    case 7: 
      setFilterable("CreditLine");
      break;
    case 8: 
      setFilterable("CD");
      break;
    case 9: 
      setFilterable("IRA");
      break;
    case 10: 
      setFilterable("Stocks");
      break;
    case 11: 
      setFilterable("Brokerage");
      break;
    case 12: 
      setFilterable("MoneyMarket");
      break;
    case 13: 
      setFilterable("BusinessLoan");
      break;
    case 15: 
      setFilterable("Other");
      break;
    }
  }
  
  public void setBankID(String paramString)
  {
    if ((this.iW != null) && (!this.iW.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "Account.setBankID.  Cannot modify the value of bank ID.  Use constructor");
      return;
    }
    this.iW = paramString;
  }
  
  public String getBankID()
  {
    return this.iW;
  }
  
  public void setTransactionID(String paramString)
  {
    this.jh = paramString;
  }
  
  public String getTransactionID()
  {
    return this.jh;
  }
  
  public void setClosingBalance(Balance paramBalance)
  {
    this.jg = paramBalance;
  }
  
  public Balance getClosingBalance()
  {
    return this.jg;
  }
  
  public String getClosingBalanceAmount()
  {
    if (this.jg == null) {
      return ResourceUtil.getString("AccountBalanceNoAvailable", "com.ffusion.beans.accounts.resources", this.locale);
    }
    return this.jg.getAmount();
  }
  
  public String getClosingBalanceDate()
  {
    if (this.jg == null) {
      return null;
    }
    return this.jg.getDate(this.datetype);
  }
  
  public void setCurrentBalance(Balance paramBalance)
  {
    this.je = paramBalance;
  }
  
  public Balance getCurrentBalance()
  {
    return this.je;
  }
  
  public String getCurrentBalanceAmount()
  {
    if (this.je == null) {
      return ResourceUtil.getString("AccountBalanceNoAvailable", "com.ffusion.beans.accounts.resources", this.locale);
    }
    return this.je.getAmount();
  }
  
  public String getCurrentBalanceDate()
  {
    if (this.je == null) {
      return null;
    }
    return this.je.getDate(this.datetype);
  }
  
  public void setIntradayCurrentBalance(Balance paramBalance)
  {
    this.iR = paramBalance;
  }
  
  public Balance getIntradayCurrentBalance()
  {
    return this.iR;
  }
  
  public String getIntradayCurrentBalanceAmount()
  {
    if (this.iR == null) {
      return ResourceUtil.getString("AccountBalanceNoAvailable", "com.ffusion.beans.accounts.resources", this.locale);
    }
    return this.iR.getAmount();
  }
  
  public String getIntradayCurrentBalanceDate()
  {
    if (this.iR == null) {
      return null;
    }
    return this.iR.getDate(this.datetype);
  }
  
  public void setAvailableBalance(Balance paramBalance)
  {
    this.i7 = paramBalance;
  }
  
  public Balance getAvailableBalance()
  {
    return this.i7;
  }
  
  public String getAvailableBalanceAmount()
  {
    if (this.i7 == null) {
      return ResourceUtil.getString("AccountBalanceNoAvailable", "com.ffusion.beans.accounts.resources", this.locale);
    }
    return this.i7.getAmount();
  }
  
  public String getAvailableBalanceDate()
  {
    if (this.i7 == null) {
      return null;
    }
    return this.i7.getDate(this.datetype);
  }
  
  public void setIntradayAvailableBalance(Balance paramBalance)
  {
    this.jb = paramBalance;
  }
  
  public Balance getIntradayAvailableBalance()
  {
    return this.jb;
  }
  
  public String getIntradayAvailableBalanceAmount()
  {
    if (this.jb == null) {
      return ResourceUtil.getString("AccountBalanceNoAvailable", "com.ffusion.beans.accounts.resources", this.locale);
    }
    return this.jb.getAmount();
  }
  
  public String getIntradayAvailableBalanceDate()
  {
    if (this.jb == null) {
      return null;
    }
    return this.jb.getDate(this.datetype);
  }
  
  public String getCurrencyCode()
  {
    return this.iQ;
  }
  
  public void setCurrencyCode(String paramString)
  {
    if ((paramString != null) && (paramString.length() > 3)) {
      throw new IllegalArgumentException("Currency code '" + paramString + "' has too many characters.");
    }
    this.iQ = paramString;
  }
  
  public int getAccountGroup()
  {
    return this.iT;
  }
  
  public void setAccountGroup(int paramInt)
  {
    this.iT = paramInt;
  }
  
  public static int getAccountGroupFromType(int paramInt)
  {
    try
    {
      return Integer.parseInt(ResourceUtil.getString("AccountTypeToGroup" + paramInt, "com.ffusion.beans.accounts.resources", LocaleUtil.getDefaultLocale()));
    }
    catch (Exception localException) {}
    return 0;
  }
  
  public static int getAccountSystemTypeFromGroup(int paramInt)
  {
    try
    {
      String str = ResourceUtil.getString("AccountGroupSystemType" + paramInt, "com.ffusion.beans.accounts.resources", LocaleUtil.getDefaultLocale());
      if ((str == null) || (str.length() <= 0)) {
        str = ResourceUtil.getString("AccountGroupSystemType_Default", "com.ffusion.beans.accounts.resources", LocaleUtil.getDefaultLocale());
      }
      return Integer.parseInt(str);
    }
    catch (Exception localException) {}
    return 0;
  }
  
  public String getBankName()
  {
    return this.iK;
  }
  
  public void setBankName(String paramString)
  {
    this.iK = paramString;
  }
  
  public int getNumTransactions()
  {
    return this.iV;
  }
  
  public void setNumTransactions(String paramString)
  {
    try
    {
      this.iV = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public String getPrimaryAccount()
  {
    return this.jc;
  }
  
  public void setPrimaryAccount(String paramString)
  {
    this.jc = paramString;
  }
  
  public String getCoreAccount()
  {
    return this.iU;
  }
  
  public void setCoreAccount(String paramString)
  {
    this.iU = paramString;
  }
  
  public String getRoutingNum()
  {
    return this.i3;
  }
  
  public void setRoutingNum(String paramString)
  {
    if ((this.i3 != null) && (!this.i3.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "Account.setRoutingNumber.  Cannot modify the value of the routing number after setting it.");
      return;
    }
    this.i3 = paramString;
  }
  
  public String getBicAccount()
  {
    return this.iM;
  }
  
  public void setBicAccount(String paramString)
  {
    if ((this.iM != null) && (!this.iM.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "Account.setBicAccount.  Cannot modify the value of the BIC number after setting it.");
      return;
    }
    this.iM = paramString;
  }
  
  public String getPositivePay()
  {
    return this.iJ;
  }
  
  public String getPositivePayString()
  {
    return getPositivePayString(this.iJ, this.locale);
  }
  
  public static String getPositivePayString(String paramString, Locale paramLocale)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      paramString = "1";
    }
    return ResourceUtil.getString("PositivePay" + paramString, "com.ffusion.beans.accounts.resources", paramLocale);
  }
  
  public void setPositivePay(String paramString)
  {
    this.iJ = paramString;
  }
  
  public String getPersonalAccount()
  {
    return this.jl;
  }
  
  public void setPersonalAccount(String paramString)
  {
    this.jl = paramString;
  }
  
  public String getZBAFlag()
  {
    return this.i0;
  }
  
  public String getZBAFlagString()
  {
    return getZBAFlagString(this.i0, this.locale);
  }
  
  public static String getZBAFlagString(String paramString, Locale paramLocale)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      paramString = "B";
    }
    return ResourceUtil.getString("ZBAFlag" + paramString, "com.ffusion.beans.accounts.resources", paramLocale);
  }
  
  public void setZBAFlag(String paramString)
  {
    this.i0 = paramString;
  }
  
  public boolean isMaster()
  {
    return this.i9;
  }
  
  public void setMaster(boolean paramBoolean)
  {
    this.i9 = paramBoolean;
  }
  
  public String getMaster()
  {
    String str = "";
    if (this.i9) {
      str = ResourceUtil.getString("IsMaster_True", "com.ffusion.beans.accounts.resources", this.locale);
    } else {
      str = ResourceUtil.getString("IsMaster_False", "com.ffusion.beans.accounts.resources", this.locale);
    }
    return str;
  }
  
  public boolean getMasterValue()
  {
    return this.i9;
  }
  
  public void setMaster(String paramString)
  {
    this.i9 = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getIncludeZBACreditInRollup()
  {
    String str = "";
    if (this.iI) {
      str = ResourceUtil.getString("ZBACreditInRollup_True", "com.ffusion.beans.accounts.resources", this.locale);
    } else {
      str = ResourceUtil.getString("ZBACreditInRollup_False", "com.ffusion.beans.accounts.resources", this.locale);
    }
    return str;
  }
  
  public boolean getIncludeZBACreditInRollupValue()
  {
    return this.iI;
  }
  
  public void setIncludeZBACreditInRollup(boolean paramBoolean)
  {
    this.iI = paramBoolean;
  }
  
  public void setIncludeZBACreditInRollup(String paramString)
  {
    if (paramString.equalsIgnoreCase("Y")) {
      this.iI = true;
    } else {
      this.iI = false;
    }
  }
  
  public String getIncludeZBADebitInRollup()
  {
    String str = "";
    if (this.ji) {
      str = ResourceUtil.getString("ZBADebitInRollup_True", "com.ffusion.beans.accounts.resources", this.locale);
    } else {
      str = ResourceUtil.getString("ZBADebitInRollup_False", "com.ffusion.beans.accounts.resources", this.locale);
    }
    return str;
  }
  
  public boolean getIncludeZBADebitInRollupValue()
  {
    return this.ji;
  }
  
  public void setIncludeZBADebitInRollup(String paramString)
  {
    if (paramString.equalsIgnoreCase("Y")) {
      this.ji = true;
    } else {
      this.ji = false;
    }
  }
  
  public void setIncludeZBADebitInRollup(boolean paramBoolean)
  {
    this.ji = paramBoolean;
  }
  
  public String getWithholdNonZeroBalanceSubAccounts()
  {
    String str = "";
    if (this.ja) {
      str = ResourceUtil.getString("WithholdNonZeroBalanceSubAccounts_True", "com.ffusion.beans.accounts.resources", this.locale);
    } else {
      str = ResourceUtil.getString("WithholdNonZeroBalanceSubAccounts_False", "com.ffusion.beans.accounts.resources", this.locale);
    }
    return str;
  }
  
  public boolean getWithholdNonZeroBalanceSubAccountsValue()
  {
    return this.ja;
  }
  
  public void setWithholdNonZeroBalanceSubAccounts(boolean paramBoolean)
  {
    this.ja = paramBoolean;
  }
  
  public void setWithholdNonZeroBalanceSubAccounts(String paramString)
  {
    if (paramString.equalsIgnoreCase("Y")) {
      this.ja = true;
    } else {
      this.ja = false;
    }
  }
  
  public String getLocationID()
  {
    return this.i8;
  }
  
  public void setLocationID(String paramString)
  {
    this.i8 = paramString;
  }
  
  public void setShowPreviousDayOpeningLedger(String paramString)
  {
    this.jj = paramString;
  }
  
  public String getShowPreviousDayOpeningLedger()
  {
    return this.jj;
  }
  
  public String getShowPreviousDayOpeningLedgerString()
  {
    return getShowPreviousDayOpeningLedgerString(this.jj, this.locale);
  }
  
  public static String getShowPreviousDayOpeningLedgerString(String paramString, Locale paramLocale)
  {
    if ((paramString == null) || (paramString.length() <= 0)) {
      paramString = "Y";
    }
    return ResourceUtil.getString("ShowPreviousDayOpeningLedger" + paramString, "com.ffusion.beans.accounts.resources", paramLocale);
  }
  
  public int getContactId()
  {
    return this.jq;
  }
  
  public void setContactId(int paramInt)
  {
    this.jq = paramInt;
  }
  
  public Contact getContact()
  {
    return this.iP;
  }
  
  public void setContact(Contact paramContact)
  {
    this.iP = paramContact;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.jg != null) {
      this.jg.setLocale(paramLocale);
    }
    if (this.je != null) {
      this.je.setLocale(paramLocale);
    }
    if (this.iR != null) {
      this.iR.setLocale(paramLocale);
    }
    if (this.i7 != null) {
      this.i7.setLocale(paramLocale);
    }
    if (this.jb != null) {
      this.jb.setLocale(paramLocale);
    }
    if (this.iZ != null) {
      this.iZ.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.jg != null) {
      this.jg.setDateFormat(paramString);
    }
    if (this.je != null) {
      this.je.setDateFormat(paramString);
    }
    if (this.iR != null) {
      this.iR.setDateFormat(paramString);
    }
    if (this.i7 != null) {
      this.i7.setDateFormat(paramString);
    }
    if (this.jb != null) {
      this.jb.setDateFormat(paramString);
    }
    if (this.iZ != null) {
      this.iZ.setDateFormat(paramString);
    }
  }
  
  public void setDownloadedItems(boolean paramBoolean)
  {
    this.jm = paramBoolean;
  }
  
  public void setDownloadedItems(String paramString)
  {
    this.jm = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean getDownloadedItems()
  {
    return this.jm;
  }
  
  public String getSupportsTransactions()
  {
    return String.valueOf(isFilterable("Transactions"));
  }
  
  public int getDirectoryID()
  {
    return this.iS;
  }
  
  public void setDirectoryID(int paramInt)
  {
    this.iS = paramInt;
  }
  
  public void setDirectoryID(String paramString)
  {
    try
    {
      this.iS = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      this.iS = 0;
    }
  }
  
  public String getStrippedAccountNumber()
  {
    return this.iN;
  }
  
  public void setStrippedAccountNumber(String paramString)
  {
    if ((this.iN != null) && (!this.iN.equals(paramString)))
    {
      DebugLog.log(Level.WARNING, "Account.setStrippedAccountNumber.  Cannot modify the value of stripped account number.  Use constructor");
      return;
    }
    if (paramString != null) {
      this.iN = paramString.trim();
    }
  }
  
  public void setFilterable(String paramString)
  {
    if (this.i5 == null) {
      this.i5 = new ArrayList();
    }
    if (!this.i5.contains(paramString)) {
      this.i5.add(paramString);
    }
  }
  
  public void removeFilter(String paramString)
  {
    int i = this.i5.indexOf(paramString);
    if (i != -1) {
      this.i5.remove(i);
    }
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof Account)) {
      return false;
    }
    Account localAccount = (Account)paramObject;
    return ((getID() == localAccount.getID()) || ((getID() != null) && (getID().equals(localAccount.getID())))) && ((getBankID() == localAccount.getBankID()) || ((getBankID() != null) && (getBankID().equals(localAccount.getBankID())))) && ((getRoutingNum() == localAccount.getRoutingNum()) || ((getRoutingNum() != null) && (getRoutingNum().equals(localAccount.getRoutingNum()))));
  }
  
  public Transactions getTransactions()
  {
    return this.iZ;
  }
  
  public void setTransactions(Transactions paramTransactions)
  {
    this.iZ = paramTransactions;
  }
  
  public Transaction findTransaction(String paramString)
  {
    return this.iZ.find(paramString);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("NUMBER")) {
      this.jo = paramString2;
    } else if (paramString1.equals("BANKID")) {
      this.iW = paramString2;
    } else if (paramString1.equals("NICKNAME")) {
      this.i6 = paramString2;
    } else if (paramString1.equals("TYPE")) {
      setType(Integer.parseInt(paramString2));
    } else if (paramString1.equals("STATUS")) {
      this.iX = Integer.parseInt(paramString2);
    } else if (paramString1.equals("CONTACT_ID")) {
      this.jq = Integer.parseInt(paramString2);
    } else if (paramString1.equals("FILTER")) {
      setFilterable(paramString2);
    } else if (paramString1.equals("CURRENCY_CODE")) {
      setCurrencyCode(paramString2);
    } else if (paramString1.equals("ACCOUNTGROUP")) {
      setAccountGroup(Integer.parseInt(paramString2));
    } else if (paramString1.equals("BANKNAME")) {
      this.iK = paramString2;
    } else if (paramString1.equals("PRIMARYACCOUNT")) {
      this.jc = paramString2;
    } else if (paramString1.equals("COREACCOUNT")) {
      this.iU = paramString2;
    } else if (paramString1.equals("ROUTINGNUM")) {
      this.i3 = paramString2;
    } else if (paramString1.equals("BICACCOUNT")) {
      this.iM = paramString2;
    } else if (paramString1.equals("POSITIVEPAY")) {
      this.iJ = paramString2;
    } else if (paramString1.equals("PERSONALACCOUNT")) {
      this.jl = paramString2;
    } else if (paramString1.equals("ZBAFLAG")) {
      this.i0 = paramString2;
    } else if (paramString1.equals("SHOWPREVDAYOPENINGLEDGER")) {
      this.jj = paramString2;
    } else if ("ISMASTER".equals(paramString1)) {
      this.i9 = Boolean.valueOf(paramString2).booleanValue();
    } else if ("INCLUDEZBACREDITINROLLUP".equals(paramString1)) {
      this.iI = Boolean.valueOf(paramString2).booleanValue();
    } else if ("INCLUDEZBADEBITINROLLUP".equals(paramString1)) {
      this.ji = Boolean.valueOf(paramString2).booleanValue();
    } else if ("WITHHOLDNONZEROBALANCESUBACCOUNTS".equals(paramString1)) {
      this.ja = Boolean.valueOf(paramString2).booleanValue();
    } else if ("LOCATIONID".equals(paramString1)) {
      this.i8 = paramString2;
    } else if ("DIRECTORY_ID".equals(paramString1)) {
      setDirectoryID(paramString2);
    } else if ("STRIPPED_ACCOUNT_NUMBER".equals(paramString1)) {
      setStrippedAccountNumber(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    if ((paramString1.equals("NUMBER")) && (this.i2 != 0)) {
      setID(this.jo, String.valueOf(this.i2));
    }
    if ((paramString1.equals("TYPE")) && (this.jo != null) && (this.jo.length() > 0)) {
      setID(this.jo, String.valueOf(this.i2));
    }
    return bool;
  }
  
  public void set(Account paramAccount)
  {
    this.jo = paramAccount.getNumber();
    this.iW = paramAccount.getBankID();
    this.i6 = paramAccount.getNickName();
    this.i2 = paramAccount.getTypeValue();
    this.iX = paramAccount.getStatus();
    this.iQ = paramAccount.getCurrencyCode();
    setID(paramAccount.getID());
    this.jh = paramAccount.getTransactionID();
    this.jg = paramAccount.getClosingBalance();
    this.je = paramAccount.getCurrentBalance();
    this.iR = paramAccount.getIntradayCurrentBalance();
    this.i7 = paramAccount.getAvailableBalance();
    this.jb = paramAccount.getIntradayAvailableBalance();
    this.jm = paramAccount.getDownloadedItems();
    this.iZ = paramAccount.getTransactions();
    if (paramAccount.i5 != null) {
      this.i5 = ((ArrayList)paramAccount.i5.clone());
    } else {
      this.i5 = null;
    }
    this.iT = paramAccount.getAccountGroup();
    this.iV = paramAccount.getNumTransactions();
    this.iQ = paramAccount.getCurrencyCode();
    this.iK = paramAccount.getBankName();
    this.jc = paramAccount.getPrimaryAccount();
    this.iU = paramAccount.getCoreAccount();
    this.i3 = paramAccount.getRoutingNum();
    this.iM = paramAccount.getBicAccount();
    this.iJ = paramAccount.getPositivePay();
    this.jl = paramAccount.getPersonalAccount();
    this.i0 = paramAccount.getZBAFlag();
    this.jj = paramAccount.jj;
    this.jq = paramAccount.jq;
    if (paramAccount.getContact() == null) {
      this.iP = null;
    } else {
      this.iP = ((Contact)paramAccount.getContact().clone());
    }
    this.i9 = paramAccount.isMaster();
    this.iI = paramAccount.getIncludeZBACreditInRollupValue();
    this.ji = paramAccount.getIncludeZBADebitInRollupValue();
    this.ja = paramAccount.getWithholdNonZeroBalanceSubAccountsValue();
    this.i8 = paramAccount.getLocationID();
    this.iN = paramAccount.iN;
    this.iS = paramAccount.iS;
    super.set(paramAccount);
  }
  
  public String getDisplayText()
  {
    String str = null;
    try
    {
      str = AccountSettings.buildAccountDisplayText(getNumber(), "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + getTypeValue(), this.locale);
    }
    catch (SystemException localSystemException)
    {
      str = getNumber();
    }
    return str;
  }
  
  public String getConsumerDisplayText()
  {
    return getConsumerDisplayTextGeneric("ConsumerAccountDisplayText");
  }
  
  public String getConsumerExportDisplayText()
  {
    return getConsumerDisplayTextGeneric("ConsumerAccountExportDisplayText");
  }
  
  public String getConsumerDisplayTextGeneric(String paramString)
  {
    String str1 = null;
    String str2 = getNumber();
    String str3 = getType();
    String str4 = (getAvailableBalance() == null) || (getAvailableBalance().getAmount() == null) ? "" : getAvailableBalance().getAmount();
    if ((str2 != null) && (str2.length() > 0) && (str3 != null) && (str3.length() > 0))
    {
      String str5 = null;
      str5 = ResourceUtil.getString(paramString, "com.ffusion.beans.accounts.resources", this.locale);
      if (str5.length() == 0) {
        str1 = "";
      } else {
        try
        {
          str1 = MessageFormat.format(str5, new Object[] { str2, str3, this.i6, AccountSettings.getMaskedAccountNumber(str2, 4, 'x'), str4 });
        }
        catch (SystemException localSystemException)
        {
          str1 = "";
        }
      }
    }
    else
    {
      str1 = "";
    }
    return str1;
  }
  
  public String getConsumerMenuDisplayText()
  {
    String str1 = null;
    String str2 = getNumber();
    String str3 = getType();
    String str4 = getNickName();
    String str5 = (getAvailableBalance() == null) || (getAvailableBalance().getAmount() == null) ? "" : getAvailableBalance().getAmount();
    if ((str2 != null) && (str2.length() > 0) && (str3 != null) && (str3.length() > 0))
    {
      String str6 = null;
      if ("1".equals(getCoreAccount())) {
        str6 = ResourceUtil.getString("ConsumerAccountMenuDisplayText", "com.ffusion.beans.accounts.resources", this.locale);
      } else if ("2".equals(getCoreAccount())) {
        str6 = ResourceUtil.getString("ConsumerAccountExtXferMenuDisplayText", "com.ffusion.beans.accounts.resources", this.locale);
      } else {
        str6 = ResourceUtil.getString("ConsumerAccountExtMenuDisplayText", "com.ffusion.beans.accounts.resources", this.locale);
      }
      if (str6.length() == 0) {
        str1 = "";
      } else {
        try
        {
          str1 = MessageFormat.format(str6, new Object[] { str2, str3, str4, AccountSettings.getMaskedAccountNumber(str2, 4, 'x'), str5, getBankName() });
        }
        catch (SystemException localSystemException)
        {
          str1 = "";
        }
      }
    }
    else
    {
      str1 = "";
    }
    return str1;
  }
  
  public String getDisplayText(String paramString)
  {
    String str = null;
    try
    {
      str = AccountSettings.buildAccountDisplayTextForExport(paramString, getNumber(), "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + getTypeValue(), getTypeValue(), this.locale);
    }
    catch (SystemException localSystemException)
    {
      str = getNumber();
    }
    return str;
  }
  
  public String getDisplayTextRoutingNum()
  {
    String str1 = null;
    if ((getRoutingNum() != null) && (getRoutingNum().length() > 0))
    {
      String str2 = null;
      try
      {
        str2 = ResourceUtil.getString("AccountDisplayTextRoutingNum", "com.ffusion.beans.accounts.resources", this.locale);
      }
      catch (Exception localException) {}
      if ((str2 == null) || (str2.length() == 0)) {
        str1 = getRoutingNum() + ":" + getDisplayText();
      } else {
        str1 = MessageFormat.format(str2, new Object[] { getRoutingNum(), getDisplayText() });
      }
    }
    else
    {
      return getDisplayText();
    }
    return str1;
  }
  
  public String getDisplayTextRoutingNumNickNameCurrency()
  {
    String str1 = getDisplayTextRoutingNum();
    String str2;
    if ((getNickName() != null) && (getNickName().length() > 0))
    {
      str2 = null;
      try
      {
        str2 = ResourceUtil.getString("AccountDisplayTextNickNameCurrency", "com.ffusion.beans.accounts.resources", this.locale);
      }
      catch (Exception localException1) {}
      if ((str2 == null) || (str2.length() == 0)) {
        str1 = str1 + "-" + getNickName() + "-" + getCurrencyCode();
      } else {
        str1 = MessageFormat.format(str2, new Object[] { str1, getNickName(), getCurrencyCode() });
      }
    }
    else
    {
      str2 = null;
      try
      {
        str2 = ResourceUtil.getString("AccountDisplayTextCurrency", "com.ffusion.beans.accounts.resources", this.locale);
      }
      catch (Exception localException2) {}
      if ((str2 == null) || (str2.length() == 0)) {
        str1 = str1 + "-" + getCurrencyCode();
      } else {
        str1 = MessageFormat.format(str2, new Object[] { str1, getCurrencyCode() });
      }
    }
    return str1;
  }
  
  public String getXML(String paramString, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, paramString);
    XMLHandler.appendTag(localStringBuffer, "NUMBER", this.jo);
    XMLHandler.appendTag(localStringBuffer, "BANKID", this.iW);
    XMLHandler.appendTag(localStringBuffer, "NICKNAME", this.i6);
    XMLHandler.appendTag(localStringBuffer, "TYPE", this.i2);
    XMLHandler.appendTag(localStringBuffer, "CONTACT_ID", this.jq);
    if (this.iX != -1) {
      XMLHandler.appendTag(localStringBuffer, "STATUS", this.iX);
    }
    if (this.jg != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "CLOSINGBALANCE");
      localStringBuffer.append(this.je.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "CLOSINGBALANCE");
    }
    if (this.je != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "CURRENTBALANCE");
      localStringBuffer.append(this.je.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "CURRENTBALANCE");
    }
    if (this.iR != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "INTRADAYCURRENTBALANCE");
      localStringBuffer.append(this.iR.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "INTRADAYCURRENTBALANCE");
    }
    if (this.i7 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "AVAILABLEBALANCE");
      localStringBuffer.append(this.i7.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "AVAILABLEBALANCE");
    }
    if (this.jb != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "INTRADAYAVAILABLEBALANCE");
      localStringBuffer.append(this.jb.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "INTRADAYAVAILABLEBALANCE");
    }
    if (this.i5 != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "FILTERS");
      for (int i = 0; i < this.i5.size(); i++) {
        XMLHandler.appendTag(localStringBuffer, "FILTER", (String)this.i5.get(i));
      }
      XMLHandler.appendEndTag(localStringBuffer, "FILTERS");
    }
    if ((this.iZ != null) && (paramBoolean)) {
      localStringBuffer.append(this.iZ.getXML());
    }
    if (this.iT != 0) {
      XMLHandler.appendTag(localStringBuffer, "ACCOUNTGROUP", this.iT);
    }
    if (this.iQ != null) {
      XMLHandler.appendTag(localStringBuffer, "CURRENCY_CODE", this.iQ);
    }
    XMLHandler.appendTag(localStringBuffer, "BANKNAME", this.iK);
    XMLHandler.appendTag(localStringBuffer, "PRIMARYACCOUNT", this.jc);
    XMLHandler.appendTag(localStringBuffer, "COREACCOUNT", this.iU);
    XMLHandler.appendTag(localStringBuffer, "ROUTINGNUM", this.i3);
    XMLHandler.appendTag(localStringBuffer, "BICACCOUNT", this.iM);
    XMLHandler.appendTag(localStringBuffer, "POSITIVEPAY", this.iJ);
    XMLHandler.appendTag(localStringBuffer, "PERSONALACCOUNT", this.jl);
    XMLHandler.appendTag(localStringBuffer, "ZBAFLAG", this.i0);
    XMLHandler.appendTag(localStringBuffer, "SHOWPREVDAYOPENINGLEDGER", this.jj);
    XMLHandler.appendTag(localStringBuffer, "ISMASTER", Boolean.toString(this.i9));
    XMLHandler.appendTag(localStringBuffer, "INCLUDEZBACREDITINROLLUP", Boolean.toString(this.iI));
    XMLHandler.appendTag(localStringBuffer, "INCLUDEZBADEBITINROLLUP", Boolean.toString(this.ji));
    XMLHandler.appendTag(localStringBuffer, "WITHHOLDNONZEROBALANCESUBACCOUNTS", Boolean.toString(this.ja));
    XMLHandler.appendTag(localStringBuffer, "LOCATIONID", this.i8);
    XMLHandler.appendTag(localStringBuffer, "DIRECTORY_ID", this.iS);
    XMLHandler.appendTag(localStringBuffer, "STRIPPED_ACCOUNT_NUMBER", this.iN);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, paramString);
    return localStringBuffer.toString();
  }
  
  public String getXML()
  {
    return getXML("ACCOUNT", true);
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
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, boolean paramBoolean)
  {
    continueXMLParsing(paramXMLHandler);
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Account paramAccount, String paramString)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "NICKNAME", paramAccount.i6, this.i6, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CLOSINGBALANCEAMOUNT", paramAccount.jg, this.jg, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "CURRENTBALANCEAMOUNT", paramAccount.je, this.je, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "INTRADAYCURRENTBALANCEAMOUNT", paramAccount.iR, this.iR, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "AVAILABLEBALANCEAMOUNT", paramAccount.i7, this.i7, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "INTRADAYAVAILABLEBALANCEAMOUNT", paramAccount.jb, this.jb, paramString);
    String str1 = paramAccount.jg == null ? null : paramAccount.jg.getDate();
    String str2 = this.jg == null ? null : this.jg.getDate();
    paramHistoryTracker.detectChange(BEAN_NAME, "CLOSINGBALANCEDATE", str1, str2, paramString);
    str1 = paramAccount.je == null ? null : paramAccount.je.getDate();
    str2 = this.je == null ? null : this.je.getDate();
    paramHistoryTracker.detectChange(BEAN_NAME, "CURRENTBALANCEDATE", str1, str2, paramString);
    str1 = paramAccount.iR == null ? null : paramAccount.iR.getDate();
    str2 = this.iR == null ? null : this.iR.getDate();
    paramHistoryTracker.detectChange(BEAN_NAME, "INTRADAYCURRENTBALANCEDATE", str1, str2, paramString);
    str1 = paramAccount.i7 == null ? null : paramAccount.i7.getDate();
    str2 = this.i7 == null ? null : this.i7.getDate();
    paramHistoryTracker.detectChange(BEAN_NAME, "AVAILABLEBALANCEDATE", str1, str2, paramString);
    str1 = paramAccount.jb == null ? null : paramAccount.jb.getDate();
    str2 = this.jb == null ? null : this.jb.getDate();
    paramHistoryTracker.detectChange(BEAN_NAME, "INTRADAYAVAILABLEBALANCEDATE", str1, str2, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "STATUS", paramAccount.getStatusString(), getStatusString(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "TYPE", paramAccount.getType(), getType(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "BANKID", paramAccount.iW, this.iW, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCOUNTGROUP", paramAccount.getGroupString(), getGroupString(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "NUM_TRANSACTIONS", Integer.toString(paramAccount.iV), Integer.toString(this.iV), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "BANKNAME", paramAccount.iK, this.iK, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRIMARYACCOUNT", paramAccount.jc, this.jc, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "COREACCOUNT", paramAccount.iU, this.iU, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ROUTINGNUM", paramAccount.i3, this.i3, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "BICACCOUNT", paramAccount.iM, this.iM, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "POSITIVEPAY", paramAccount.getPositivePayString(), getPositivePayString(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "PERSONALACCOUNT", paramAccount.jl, this.jl, paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ZBAFLAG", paramAccount.getZBAFlagString(), getZBAFlagString(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "SHOWPREVDAYOPENINGLEDGER", paramAccount.getShowPreviousDayOpeningLedgerString(), getShowPreviousDayOpeningLedgerString(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "ISMASTER", paramAccount.getMaster(), getMaster(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "INCLUDEZBACREDITINROLLUP", paramAccount.getIncludeZBACreditInRollup(), getIncludeZBACreditInRollup(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "INCLUDEZBADEBITINROLLUP", paramAccount.getIncludeZBADebitInRollup(), getIncludeZBADebitInRollup(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "WITHHOLDNONZEROBALANCESUBACCOUNTS", paramAccount.getWithholdNonZeroBalanceSubAccounts(), getWithholdNonZeroBalanceSubAccounts(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "LOCATIONID", paramAccount.getLocationID(), this.i8, paramString);
    if (paramAccount.jq == -1)
    {
      if (this.iP != null) {
        this.iP.logCreation(paramHistoryTracker, paramString);
      }
    }
    else if (this.iP == null) {
      paramAccount.iP.logDeletion(paramHistoryTracker, paramString);
    } else {
      this.iP.logChanges(paramHistoryTracker, paramAccount.iP, paramString);
    }
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "ID", getID(), paramString);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, String paramString)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, "ID", this.i1, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "NUMBER", this.jo, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "NICKNAME", this.i6, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "CLOSINGBALANCEAMOUNT", this.jg, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "CURRENTBALANCEAMOUNT", this.je, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "INTRADAYCURRENTBALANCEAMOUNT", this.iR, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "AVAILABLEBALANCEAMOUNT", this.i7, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "INTRADAYAVAILABLEBALANCEAMOUNT", this.jb, paramString);
    String str = this.jg == null ? null : this.jg.getDate();
    paramHistoryTracker.logDelete(BEAN_NAME, "CLOSINGBALANCEDATE", str, paramString);
    str = this.je == null ? null : this.je.getDate();
    paramHistoryTracker.logDelete(BEAN_NAME, "CURRENTBALANCEDATE", str, paramString);
    str = this.iR == null ? null : this.iR.getDate();
    paramHistoryTracker.logDelete(BEAN_NAME, "INTRADAYCURRENTBALANCEDATE", str, paramString);
    str = this.i7 == null ? null : this.i7.getDate();
    paramHistoryTracker.logDelete(BEAN_NAME, "AVAILABLEBALANCEDATE", str, paramString);
    str = this.jb == null ? null : this.jb.getDate();
    paramHistoryTracker.logDelete(BEAN_NAME, "INTRADAYAVAILABLEBALANCEDATE", str, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "STATUS", getStatusString(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "TYPE", getType(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "BANKID", this.iW, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "ACCOUNTGROUP", getGroupString(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "NUM_TRANSACTIONS", Integer.toString(this.iV), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "BANKNAME", this.iK, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "PRIMARYACCOUNT", this.jc, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "COREACCOUNT", this.iU, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "ROUTINGNUM", this.i3, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "BICACCOUNT", this.iM, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "POSITIVEPAY", getPositivePayString(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "PERSONALACCOUNT", this.jl, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "ZBAFLAG", getZBAFlagString(), paramString);
    paramHistoryTracker.detectChange(BEAN_NAME, "SHOWPREVDAYOPENINGLEDGER", getShowPreviousDayOpeningLedgerString(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "ISMASTER", getMaster(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "INCLUDEZBACREDITINROLLUP", getIncludeZBACreditInRollup(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "INCLUDEZBADEBITINROLLUP", getIncludeZBADebitInRollup(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "WITHHOLDNONZEROBALANCESUBACCOUNTS", getWithholdNonZeroBalanceSubAccounts(), paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "LOCATIONID", this.i8, paramString);
    paramHistoryTracker.logDelete(BEAN_NAME, "STRIPPED_ACCOUNT_NUMBER", this.iN, paramString);
    if (this.iP != null) {
      this.iP.logDeletion(paramHistoryTracker, paramString);
    }
  }
  
  public void logChanges(HistoryTracker paramHistoryTracker, Account paramAccount, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.detectChange(BEAN_NAME, "NICKNAME", paramAccount.i6, this.i6, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "CLOSINGBALANCEAMOUNT", paramAccount.jg == null ? null : paramAccount.jg.getAmountValue(), this.jg == null ? null : this.jg.getAmountValue(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "CURRENTBALANCEAMOUNT", paramAccount.je == null ? null : paramAccount.je.getAmountValue(), this.je == null ? null : this.je.getAmountValue(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "INTRADAYCURRENTBALANCEAMOUNT", paramAccount.iR == null ? null : paramAccount.iR.getAmountValue(), this.iR == null ? null : this.iR.getAmountValue(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "AVAILABLEBALANCEAMOUNT", paramAccount.i7 == null ? null : paramAccount.i7.getAmountValue(), this.i7 == null ? null : this.i7.getAmountValue(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "INTRADAYAVAILABLEBALANCEAMOUNT", paramAccount.jb == null ? null : paramAccount.jb.getAmountValue(), this.jb == null ? null : this.jb.getAmountValue(), paramILocalizable);
    DateTime localDateTime1 = paramAccount.jg == null ? null : paramAccount.jg.getDateValue();
    DateTime localDateTime2 = this.jg == null ? null : this.jg.getDateValue();
    paramHistoryTracker.detectChange(BEAN_NAME, "CLOSINGBALANCEDATE", localDateTime1 == null ? null : new LocalizableDate(localDateTime1, false), localDateTime2 == null ? null : new LocalizableDate(localDateTime2, false), paramILocalizable);
    localDateTime1 = paramAccount.je == null ? null : paramAccount.je.getDateValue();
    localDateTime2 = this.je == null ? null : this.je.getDateValue();
    paramHistoryTracker.detectChange(BEAN_NAME, "CURRENTBALANCEDATE", localDateTime1 == null ? null : new LocalizableDate(localDateTime1, false), localDateTime2 == null ? null : new LocalizableDate(localDateTime2, false), paramILocalizable);
    localDateTime1 = paramAccount.iR == null ? null : paramAccount.iR.getDateValue();
    localDateTime2 = this.iR == null ? null : this.iR.getDateValue();
    paramHistoryTracker.detectChange(BEAN_NAME, "INTRADAYCURRENTBALANCEDATE", localDateTime1 == null ? null : new LocalizableDate(localDateTime1, false), localDateTime2 == null ? null : new LocalizableDate(localDateTime2, false), paramILocalizable);
    localDateTime1 = paramAccount.i7 == null ? null : paramAccount.i7.getDateValue();
    localDateTime2 = this.i7 == null ? null : this.i7.getDateValue();
    paramHistoryTracker.detectChange(BEAN_NAME, "AVAILABLEBALANCEDATE", localDateTime1 == null ? null : new LocalizableDate(localDateTime1, false), localDateTime2 == null ? null : new LocalizableDate(localDateTime2, false), paramILocalizable);
    localDateTime1 = paramAccount.jb == null ? null : paramAccount.jb.getDateValue();
    localDateTime2 = this.jb == null ? null : this.jb.getDateValue();
    paramHistoryTracker.detectChange(BEAN_NAME, "INTRADAYAVAILABLEBALANCEDATE", localDateTime1 == null ? null : new LocalizableDate(localDateTime1, false), localDateTime2 == null ? null : new LocalizableDate(localDateTime2, false), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "STATUS", new LocalizableString("com.ffusion.beans.accounts.resources", "AccountStatus" + paramAccount.iX, null), new LocalizableString("com.ffusion.beans.accounts.resources", "AccountStatus" + this.iX, null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "TYPE", new LocalizableString("com.ffusion.beans.accounts.resources", "AccountType" + paramAccount.i2, null), new LocalizableString("com.ffusion.beans.accounts.resources", "AccountType" + this.i2, null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "BANKID", paramAccount.iW, this.iW, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ACCOUNTGROUP", new LocalizableString("com.ffusion.beans.accounts.resources", "AccountGroup" + paramAccount.iT, null), new LocalizableString("com.ffusion.beans.accounts.resources", "AccountGroup" + this.iT, null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "NUM_TRANSACTIONS", Integer.toString(paramAccount.iV), Integer.toString(this.iV), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "BANKNAME", paramAccount.iK, this.iK, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PRIMARYACCOUNT", paramAccount.jc, this.jc, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "COREACCOUNT", paramAccount.iU, this.iU, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ROUTINGNUM", paramAccount.i3, this.i3, paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "BICACCOUNT", paramAccount.iM, this.iM, paramILocalizable);
    String str1 = this.iJ;
    if ((this.iJ == null) || (this.iJ.length() <= 0)) {
      str1 = "1";
    }
    String str2 = paramAccount.iJ;
    if ((paramAccount.iJ == null) || (paramAccount.iJ.length() <= 0)) {
      str2 = "1";
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "POSITIVEPAY", new LocalizableString("com.ffusion.beans.accounts.resources", "PositivePay" + str2, null), new LocalizableString("com.ffusion.beans.accounts.resources", "PositivePay" + str1, null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "PERSONALACCOUNT", paramAccount.jl, this.jl, paramILocalizable);
    String str3 = this.i0;
    if ((this.i0 == null) || (this.i0.length() <= 0)) {
      str3 = "B";
    }
    String str4 = paramAccount.i0;
    if ((paramAccount.i0 == null) || (paramAccount.i0.length() <= 0)) {
      str4 = "B";
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "ZBAFLAG", new LocalizableString("com.ffusion.beans.accounts.resources", "ZBAFlag" + str4, null), new LocalizableString("com.ffusion.beans.accounts.resources", "ZBAFlag" + str3, null), paramILocalizable);
    String str5 = this.jj;
    if ((this.jj == null) || (this.jj.length() <= 0)) {
      str5 = "Y";
    }
    String str6 = paramAccount.jj;
    if ((paramAccount.jj == null) || (paramAccount.jj.length() <= 0)) {
      str6 = "Y";
    }
    paramHistoryTracker.detectChange(BEAN_NAME, "SHOWPREVDAYOPENINGLEDGER", new LocalizableString("com.ffusion.beans.accounts.resources", "ShowPreviousDayOpeningLedger" + str6, null), new LocalizableString("com.ffusion.beans.accounts.resources", "ShowPreviousDayOpeningLedger" + str5, null), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "ISMASTER", paramAccount.getMaster(), getMaster(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "INCLUDEZBACREDITINROLLUP", paramAccount.getIncludeZBACreditInRollup(), getIncludeZBACreditInRollup(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "INCLUDEZBADEBITINROLLUP", paramAccount.getIncludeZBADebitInRollup(), getIncludeZBADebitInRollup(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "WITHHOLDNONZEROBALANCESUBACCOUNTS", paramAccount.getWithholdNonZeroBalanceSubAccounts(), getWithholdNonZeroBalanceSubAccounts(), paramILocalizable);
    paramHistoryTracker.detectChange(BEAN_NAME, "LOCATIONID", paramAccount.getLocationID(), this.i8, paramILocalizable);
    if (paramAccount.jq == -1)
    {
      if (this.iP != null) {
        this.iP.logCreation(paramHistoryTracker, paramILocalizable);
      }
    }
    else if (this.iP == null) {
      paramAccount.iP.logDeletion(paramHistoryTracker, paramILocalizable);
    } else {
      this.iP.logChanges(paramHistoryTracker, paramAccount.iP, paramILocalizable);
    }
  }
  
  public void logCreation(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logCreate(BEAN_NAME, "ID", buildLocalizableAccountID(), paramILocalizable);
  }
  
  public void logDeletion(HistoryTracker paramHistoryTracker, ILocalizable paramILocalizable)
  {
    paramHistoryTracker.logDelete(BEAN_NAME, "ID", buildLocalizableAccountID(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "NUMBER", this.jo, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "NICKNAME", this.i6, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "CLOSINGBALANCEAMOUNT", this.jg == null ? null : this.jg.getAmountValue(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "CURRENTBALANCEAMOUNT", this.je == null ? null : this.je.getAmountValue(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "INTRADAYCURRENTBALANCEAMOUNT", this.iR == null ? null : this.iR.getAmountValue(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "AVAILABLEBALANCEAMOUNT", this.i7 == null ? null : this.i7.getAmountValue(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "INTRADAYAVAILABLEBALANCEAMOUNT", this.jb == null ? null : this.jb.getAmountValue(), paramILocalizable);
    DateTime localDateTime = this.jg == null ? null : this.jg.getDateValue();
    paramHistoryTracker.logDelete(BEAN_NAME, "CLOSINGBALANCEDATE", localDateTime == null ? null : new LocalizableDate(localDateTime, false), paramILocalizable);
    localDateTime = this.je == null ? null : this.je.getDateValue();
    paramHistoryTracker.logDelete(BEAN_NAME, "CURRENTBALANCEDATE", localDateTime == null ? null : new LocalizableDate(localDateTime, false), paramILocalizable);
    localDateTime = this.iR == null ? null : this.iR.getDateValue();
    paramHistoryTracker.logDelete(BEAN_NAME, "INTRADAYCURRENTBALANCEDATE", localDateTime == null ? null : new LocalizableDate(localDateTime, false), paramILocalizable);
    localDateTime = this.i7 == null ? null : this.i7.getDateValue();
    paramHistoryTracker.logDelete(BEAN_NAME, "AVAILABLEBALANCEDATE", localDateTime == null ? null : new LocalizableDate(localDateTime, false), paramILocalizable);
    localDateTime = this.jb == null ? null : this.jb.getDateValue();
    paramHistoryTracker.logDelete(BEAN_NAME, "INTRADAYAVAILABLEBALANCEDATE", localDateTime == null ? null : new LocalizableDate(localDateTime, false), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "STATUS", new LocalizableString("com.ffusion.beans.accounts.resources", "AccountStatus" + this.iX, null), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "TYPE", new LocalizableString("com.ffusion.beans.accounts.resources", "AccountType" + this.i2, null), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "BANKID", this.iW, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "ACCOUNTGROUP", new LocalizableString("com.ffusion.beans.accounts.resources", "AccountGroup" + this.iT, null), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "NUM_TRANSACTIONS", Integer.toString(this.iV), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "BANKNAME", this.iK, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "PRIMARYACCOUNT", this.jc, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "COREACCOUNT", this.iU, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "ROUTINGNUM", this.i3, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "BICACCOUNT", this.iM, paramILocalizable);
    String str1 = this.iJ;
    if ((this.iJ == null) || (this.iJ.length() <= 0)) {
      str1 = "1";
    }
    paramHistoryTracker.logDelete(BEAN_NAME, "POSITIVEPAY", new LocalizableString("com.ffusion.beans.accounts.resources", "PositivePay" + str1, null), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "PERSONALACCOUNT", this.jl, paramILocalizable);
    String str2 = this.i0;
    if ((this.i0 == null) || (this.i0.length() <= 0)) {
      str2 = "B";
    }
    paramHistoryTracker.logDelete(BEAN_NAME, "ZBAFLAG", new LocalizableString("com.ffusion.beans.accounts.resources", "ZBAFlag" + str2, null), paramILocalizable);
    String str3 = this.jj;
    if ((this.jj == null) || (this.jj.length() <= 0)) {
      str3 = "Y";
    }
    paramHistoryTracker.logDelete(BEAN_NAME, "SHOWPREVDAYOPENINGLEDGER", new LocalizableString("com.ffusion.beans.accounts.resources", "ShowPreviousDayOpeningLedger" + str3, null), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "ISMASTER", getMaster(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "INCLUDEZBACREDITINROLLUP", getIncludeZBACreditInRollup(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "INCLUDEZBADEBITINROLLUP", getIncludeZBADebitInRollup(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "WITHHOLDNONZEROBALANCESUBACCOUNTS", getWithholdNonZeroBalanceSubAccounts(), paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "LOCATIONID", this.i8, paramILocalizable);
    paramHistoryTracker.logDelete(BEAN_NAME, "STRIPPED_ACCOUNT_NUMBER", this.iN, paramILocalizable);
    if (this.iP != null) {
      this.iP.logDeletion(paramHistoryTracker, paramILocalizable);
    }
  }
  
  public LocalizableAccountID buildLocalizableAccountID()
  {
    return new LocalizableAccountID(getNumber(), "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + getTypeValue());
  }
  
  public Balance getDisplayAvailableBalance()
  {
    return this.jn;
  }
  
  public Balance getDisplayClosingBalance()
  {
    return this.iO;
  }
  
  public String getDisplayCurrency()
  {
    return this.jd;
  }
  
  public Balance getDisplayCurrentBalance()
  {
    return this.iL;
  }
  
  public BigDecimal getDisplayFXRate()
  {
    return this.i4;
  }
  
  public Balance getDisplayIntradayAvailableBalance()
  {
    return this.jk;
  }
  
  public Balance getDisplayIntradayCurrentBalance()
  {
    return this.jp;
  }
  
  public void calculateDisplayBalances(String paramString, BigDecimal paramBigDecimal)
  {
    this.i4 = paramBigDecimal;
    this.jd = paramString;
    this.iO = jdMethod_for(this.jg);
    this.iL = jdMethod_for(this.je);
    this.jp = jdMethod_for(this.iR);
    this.jn = jdMethod_for(this.i7);
    this.jk = jdMethod_for(this.jb);
  }
  
  private Balance jdMethod_for(Balance paramBalance)
  {
    Balance localBalance = null;
    if ((this.jd == null) || (this.jd.trim().length() == 0) || ((this.jd != null) && (this.jd.equals(this.iQ))))
    {
      if (paramBalance == null) {
        return null;
      }
      localBalance = (Balance)paramBalance.clone();
    }
    else if ((this.i4 == null) || (paramBalance == null))
    {
      localBalance = null;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        BigDecimal localBigDecimal = paramBalance.getAmountValue().getAmountValue().multiply(this.i4);
        int i = FXUtil.getNumDecimals(this.jd, localHashMap);
        localBigDecimal = localBigDecimal.setScale(i, 6);
        Currency localCurrency = new Currency(localBigDecimal, this.locale);
        localCurrency.setCurrencyCode(this.jd);
        if (localBalance == null) {
          localBalance = new Balance(this.locale);
        }
        localBalance.setAmount(localCurrency);
      }
      catch (Exception localException) {}
    }
    return localBalance;
  }
  
  public Balance getDisplayOpeningBalance()
  {
    return this.iY;
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
      if (paramString.equals("TRANSACTIONS"))
      {
        Account.this.iZ = new Transactions(Account.this.locale);
        Account.this.iZ.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("CLOSINGBALANCE"))
      {
        Account.this.jg = new Balance(Account.this.locale);
        Account.this.jg.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("CURRENTBALANCE"))
      {
        Account.this.je = new Balance(Account.this.locale);
        Account.this.je.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("INTRADAYCURRENTBALANCE"))
      {
        Account.this.iR = new Balance(Account.this.locale);
        Account.this.iR.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("AVAILABLEBALANCE"))
      {
        Account.this.i7 = new Balance(Account.this.locale);
        Account.this.i7.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("INTRADAYAVAILABLEBALANCE"))
      {
        Account.this.jb = new Balance(Account.this.locale);
        Account.this.jb.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      if (paramString.equals("ACCOUNT"))
      {
        if (Account.this.iZ.size() > 0) {
          Account.this.setDownloadedItems(true);
        } else {
          Account.this.setDownloadedItems(false);
        }
      }
      else {
        super.endElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.Account
 * JD-Core Version:    0.7.0.1
 */