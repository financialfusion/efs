package com.ffusion.beans.aggregation;

import com.ffusion.beans.Balance;
import com.ffusion.beans.accounts.AccountMaskConsts;
import com.ffusion.util.ResourceUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import com.ffusion.util.beans.LocalizableAccountID;
import com.ffusion.util.settings.AccountSettings;
import com.ffusion.util.settings.SystemException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

public class Account
  extends ExtendABean
  implements AccountMaskConsts, AccountTypes, AccountStatus, AccountFilters, Comparable
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.accounts.resources";
  public static final String KEY_ACCOUNT_DISPLAY_TEXT = "AccountDisplayText";
  public static final String KEY_ACCOUNT_TYPE = "AccountType";
  public static final String KEY_CONSUMER_ACCOUNT_DISPLAY_TEXT = "ConsumerAccountDisplayText";
  public static final String KEY_CONSUMER_ACCOUNT_MENU_DISPLAY_TEXT = "ConsumerAccountMenuDisplayText";
  public static final String KEY_CONSUMER_ACCOUNT_EXTERNAL_MENU_DISPLAY_TEXT = "ConsumerAccountExtMenuDisplayText";
  private String nC;
  private String nt;
  private String nv;
  private String nF;
  private int nw;
  private int nE;
  private Balance nB;
  private Balance nu;
  private boolean nx;
  private Transactions nD;
  private ArrayList nz;
  private AccountNVPairs nA;
  protected String trackingID;
  private String ny;
  
  protected Account()
  {
    this.nA = new AccountNVPairs();
    this.nx = false;
  }
  
  protected Account(Locale paramLocale, String paramString1, String paramString2, int paramInt)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
    this.nt = paramString2;
    this.nv = paramString1;
    this.nC = (paramString2 + '-' + paramInt);
    setTypeValue(paramInt);
    this.nD = new Transactions(paramLocale);
    this.nx = false;
    this.nA = new AccountNVPairs(paramLocale);
  }
  
  protected Account(Locale paramLocale, String paramString, int paramInt)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
    setID(paramString, String.valueOf(paramInt));
    setTypeValue(paramInt);
    this.nD = new Transactions(paramLocale);
    this.nx = false;
    this.nA = new AccountNVPairs(paramLocale);
  }
  
  protected Account(Locale paramLocale, String paramString)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
    setID(paramString);
    this.nD = new Transactions(paramLocale);
    this.nx = false;
    this.nB = new Balance(paramLocale);
    this.nu = new Balance(paramLocale);
    this.nA = new AccountNVPairs(paramLocale);
  }
  
  public Account(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
    this.nD = new Transactions(paramLocale);
    this.nx = false;
    this.nB = new Balance(paramLocale);
    this.nu = new Balance(paramLocale);
    this.nA = new AccountNVPairs(paramLocale);
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "TYPE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Account localAccount = (Account)paramObject;
    int i = 1;
    if ((paramString.equals("NUMBER")) && (getNumber() != null) && (localAccount.getNumber() != null)) {
      i = getNumber().compareTo(localAccount.getNumber());
    } else if ((paramString.equals("NICKNAME")) && (getNickName() != null) && (localAccount.getNickName() != null)) {
      i = getNickName().compareTo(localAccount.getNickName());
    } else if ((paramString.equals("CURRENTBALANCEAMOUNT")) && (getCurrentBalance() != null) && (localAccount.getCurrentBalance() != null)) {
      i = getCurrentBalance().compare(localAccount.getCurrentBalance(), "AMOUNT");
    } else if ((paramString.equals("AVAILABLEBALANCEAMOUNT")) && (getAvailableBalance() != null) && (localAccount.getAvailableBalance() != null)) {
      i = getAvailableBalance().compare(localAccount.getAvailableBalance(), "AMOUNT");
    } else if ((paramString.equals("CURRENTBALANCEDATE")) && (getCurrentBalance() != null) && (localAccount.getCurrentBalance() != null)) {
      i = getCurrentBalance().compare(localAccount.getCurrentBalance(), "DATE");
    } else if ((paramString.equals("AVAILABLEBALANCEDATE")) && (getAvailableBalance() != null) && (localAccount.getAvailableBalance() != null)) {
      i = getAvailableBalance().compare(localAccount.getAvailableBalance(), "DATE");
    } else if (paramString.equals("STATUS")) {
      i = getStatus() - localAccount.getStatus();
    } else if (paramString.equals("TYPE")) {
      i = getTypeValue() - localAccount.getTypeValue();
    } else if (paramString.equals("INSTITUTION_NAME")) {
      i = ExtendABean.compareStrings(this.ny, localAccount.ny);
    } else {
      i = super.compare(paramObject, paramString);
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
    if (this.nz != null) {
      return this.nz.contains(paramString);
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
    if (paramString1.equals("STATUS")) {
      return isFilterable(String.valueOf(getStatus()), paramString2, paramString3);
    }
    if ((paramString1.equals("TYPE")) && (getType() != null)) {
      return isFilterable(getType(), paramString2, paramString3);
    }
    if (paramString1.equals("INSTITUTION_NAME")) {
      return isFilterable(getInstitutionName(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public String getID()
  {
    return this.nC;
  }
  
  public void setID(String paramString1, String paramString2)
  {
    try
    {
      if (paramString1 != null) {
        this.nt = paramString1;
      }
      this.nC = AccountSettings.buildAccountId(paramString1, paramString2);
    }
    catch (SystemException localSystemException) {}
  }
  
  public void setID(String paramString)
  {
    if (paramString != null) {
      this.nC = paramString;
    }
  }
  
  public void setNumber(String paramString)
  {
    this.nt = paramString;
  }
  
  public String getNumber()
  {
    return this.nt;
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
    this.nF = paramString;
  }
  
  public String getNickName()
  {
    return this.nF;
  }
  
  public void setType(String paramString)
  {
    try
    {
      this.nw = Integer.parseInt(paramString);
      setTypeFilter(this.nw);
    }
    catch (Exception localException)
    {
      this.nw = 0;
    }
  }
  
  public void setTypeValue(int paramInt)
  {
    this.nw = paramInt;
    setTypeFilter(paramInt);
  }
  
  public int getTypeValue()
  {
    return this.nw;
  }
  
  public String getType()
  {
    return String.valueOf(this.nw);
  }
  
  public void setAccountNVPairs(AccountNVPairs paramAccountNVPairs)
  {
    this.nA = paramAccountNVPairs;
  }
  
  public AccountNVPairs getAccountNVPairs()
  {
    return this.nA;
  }
  
  public void setTypeFilter(int paramInt)
  {
    String[] arrayOfString = { "Checking", "Savings", "Credit", "Loan", "Mortgage", "HomeEquity", "CreditLine", "CD", "IRA", "Stocks", "Mortgage", "MoneyMarket", "BusinessLoan", "Other" };
    if (this.nz != null) {
      for (int i = this.nz.size() - 1; i >= 0; i--)
      {
        String str = (String)this.nz.get(i);
        for (int j = 0; j < arrayOfString.length; j++) {
          if (str.equals(arrayOfString[j]))
          {
            this.nz.remove(i);
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
      setFilterable("Mortgage");
      break;
    case 12: 
      setFilterable("MoneyMarket");
      break;
    case 13: 
      setFilterable("BusinessLoan");
      break;
    case 14: 
      setFilterable("Other");
      break;
    }
  }
  
  public void setStatus(int paramInt)
  {
    if ((paramInt > -1) && (paramInt < 3)) {
      this.nE = paramInt;
    }
  }
  
  public int getStatus()
  {
    return this.nE;
  }
  
  public void setInstitutionID(String paramString)
  {
    this.nv = paramString;
  }
  
  public String getInstitutionID()
  {
    return this.nv;
  }
  
  public void setFormat(String paramString)
  {
    this.nB.setFormat(paramString);
    this.nu.setFormat(paramString);
  }
  
  public void setCurrentBalance(Balance paramBalance)
  {
    this.nB = paramBalance;
  }
  
  public Balance getCurrentBalance()
  {
    return this.nB;
  }
  
  public void setAvailableBalance(Balance paramBalance)
  {
    this.nu = paramBalance;
  }
  
  public Balance getAvailableBalance()
  {
    return this.nu;
  }
  
  public void setDownloadedItems(boolean paramBoolean)
  {
    this.nx = paramBoolean;
  }
  
  public void setDownloadedItems(String paramString)
  {
    this.nx = Boolean.valueOf(paramString).booleanValue();
  }
  
  public boolean getDownloadedItems()
  {
    return this.nx;
  }
  
  public String getSupportsTransactions()
  {
    return String.valueOf(isFilterable("Transactions"));
  }
  
  public void setFilterable(String paramString)
  {
    if (this.nz == null) {
      this.nz = new ArrayList();
    }
    this.nz.add(paramString);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.nB != null) {
      this.nB.setLocale(paramLocale);
    }
    if (this.nu != null) {
      this.nu.setLocale(paramLocale);
    }
    if (this.nD != null) {
      this.nD.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.nB != null) {
      this.nB.setDateFormat(paramString);
    }
    if (this.nu != null) {
      this.nu.setDateFormat(paramString);
    }
    if (this.nD != null) {
      this.nD.setDateFormat(paramString);
    }
  }
  
  public Transactions getTransactions()
  {
    return this.nD;
  }
  
  public void setTransactions(Transactions paramTransactions)
  {
    this.nD = paramTransactions;
  }
  
  public Transaction findTransaction(String paramString)
  {
    return this.nD.find(paramString);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("NUMBER")) {
      this.nt = paramString2;
    } else if (paramString1.equals("INSTITUTIONID")) {
      this.nv = paramString2;
    } else if (paramString1.equals("INSTITUTION_NAME")) {
      this.ny = paramString2;
    } else if (paramString1.equals("NICKNAME")) {
      this.nF = paramString2;
    } else if (paramString1.equals("TYPE")) {
      this.nw = Integer.parseInt(paramString2);
    } else if (paramString1.equals("STATUS")) {
      this.nE = Integer.parseInt(paramString2);
    } else if (paramString1.equals("FILTER")) {
      setFilterable(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    if ((paramString1.equals("NUMBER")) || (paramString1.equals("TYPE"))) {
      setID(this.nt, String.valueOf(this.nw));
    }
    return bool;
  }
  
  public void set(Account paramAccount)
  {
    if ((this == paramAccount) || (paramAccount == null)) {
      return;
    }
    this.nt = paramAccount.getNumber();
    this.nv = paramAccount.getInstitutionID();
    this.ny = paramAccount.ny;
    this.nF = paramAccount.getNickName();
    this.nw = paramAccount.getTypeValue();
    this.nE = paramAccount.getStatus();
    setID(paramAccount.getNumber(), String.valueOf(paramAccount.getTypeValue()));
    if (paramAccount.getCurrentBalance() != null) {
      this.nB = ((Balance)paramAccount.getCurrentBalance().clone());
    } else {
      this.nB = null;
    }
    if (paramAccount.getAvailableBalance() != null) {
      this.nu = ((Balance)paramAccount.getAvailableBalance().clone());
    } else {
      this.nu = null;
    }
    this.nx = paramAccount.getDownloadedItems();
    if (paramAccount.getTransactions() != null) {
      this.nD = ((Transactions)paramAccount.getTransactions().clone());
    } else {
      this.nD = null;
    }
    if (paramAccount.getAccountNVPairs() != null) {
      this.nA = ((AccountNVPairs)paramAccount.getAccountNVPairs().clone());
    } else {
      this.nA = null;
    }
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
    String str1 = null;
    String str2 = getNumber();
    String str3 = getType();
    String str4 = getNickName();
    String str5 = getAvailableBalance() == null ? "0.0" : getAvailableBalance().getAmount();
    if ((str2 != null) && (str2.length() > 0) && (str3 != null) && (str3.length() > 0) && (str5 != null) && (str5.length() > 0))
    {
      String str6 = null;
      str6 = ResourceUtil.getString("ConsumerAccountDisplayText", "com.ffusion.beans.accounts.resources", this.locale);
      if (str6.length() == 0) {
        str1 = "";
      } else {
        try
        {
          str1 = MessageFormat.format(str6, new Object[] { str2, str3, str4, AccountSettings.getMaskedAccountNumber(str2, 4, 'x'), str5 });
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
    String str5 = getAvailableBalance() == null ? "0.0" : getAvailableBalance().getAmount();
    if ((str2 != null) && (str2.length() > 0) && (str3 != null) && (str3.length() > 0) && (str5 != null) && (str5.length() > 0))
    {
      String str6 = null;
      str6 = ResourceUtil.getString("ConsumerAccountMenuDisplayText", "com.ffusion.beans.accounts.resources", this.locale);
      if (str6.length() == 0) {
        str1 = "";
      } else {
        try
        {
          str1 = MessageFormat.format(str6, new Object[] { str2, str3, str4, AccountSettings.getMaskedAccountNumber(str2, 4, 'x'), str5 });
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
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACCOUNT");
    XMLHandler.appendTag(localStringBuffer, "NUMBER", this.nt);
    XMLHandler.appendTag(localStringBuffer, "INSTITUTIONID", this.nv);
    XMLHandler.appendTag(localStringBuffer, "INSTITUTION_NAME", this.ny);
    XMLHandler.appendTag(localStringBuffer, "NICKNAME", this.nF);
    if (this.nw != 0) {
      XMLHandler.appendTag(localStringBuffer, "TYPE", this.nw);
    }
    if (this.nE != -1) {
      XMLHandler.appendTag(localStringBuffer, "STATUS", this.nE);
    }
    if (this.nB != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "CURRENTBALANCE");
      localStringBuffer.append(this.nB.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "CURRENTBALANCE");
    }
    if (this.nu != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "AVAILABLEBALANCE");
      localStringBuffer.append(this.nu.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "AVAILABLEBALANCE");
    }
    if (this.nz != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "FILTERS");
      for (int i = 0; i < this.nz.size(); i++) {
        XMLHandler.appendTag(localStringBuffer, "FILTER", (String)this.nz.get(i));
      }
      XMLHandler.appendEndTag(localStringBuffer, "FILTERS");
    }
    if (this.nD != null) {
      localStringBuffer.append(this.nD.getXML());
    }
    localStringBuffer.append(this.nA.getXML());
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACCOUNT");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(true), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, boolean paramBoolean)
  {
    paramXMLHandler.continueWith(new a(paramBoolean));
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(true));
  }
  
  public LocalizableAccountID buildLocalizableAccountID()
  {
    return new LocalizableAccountID(getNumber(), "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + getTypeValue());
  }
  
  public String getInstitutionName()
  {
    return this.ny;
  }
  
  public void setInstitutionName(String paramString)
  {
    this.ny = paramString;
  }
  
  public static int getAccountTypeFromAggregationType(int paramInt)
  {
    if (paramInt == 14) {
      return 15;
    }
    if (paramInt == 15) {
      return 15;
    }
    return paramInt;
  }
  
  public static int getAggregationTypeFromAccountType(int paramInt)
  {
    if (paramInt == 15) {
      return 14;
    }
    if (paramInt == 15) {
      return 15;
    }
    return paramInt;
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    boolean jdField_int;
    
    public a(boolean paramBoolean)
    {
      super();
      this.jdField_int = paramBoolean;
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("TRANSACTIONS"))
      {
        Account.this.nD = new Transactions(Account.this.locale);
        Account.this.nD.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("ACCOUNTNVPAIRLIST"))
      {
        Account.this.nA = new AccountNVPairs(Account.this.locale);
        Account.this.nA.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("CURRENTBALANCE"))
      {
        Account.this.nB = new Balance(Account.this.locale);
        Account.this.nB.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("AVAILABLEBALANCE"))
      {
        Account.this.nu = new Balance(Account.this.locale);
        Account.this.nu.continueXMLParsing(getHandler());
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
        if (!this.jdField_int) {
          Account.this.nD.clear();
        }
        if (Account.this.nD.size() > 0) {
          Account.this.setDownloadedItems(true);
        } else {
          Account.this.setDownloadedItems(false);
        }
      }
      else
      {
        super.endElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.aggregation.Account
 * JD-Core Version:    0.7.0.1
 */