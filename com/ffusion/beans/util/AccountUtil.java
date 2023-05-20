package com.ffusion.beans.util;

import com.ffusion.beans.Balance;
import com.ffusion.beans.Currency;
import com.ffusion.beans.aggregation.Accounts;
import com.ffusion.beans.fx.FXRate;
import com.ffusion.beans.fx.FXRates;
import com.ffusion.fx.FXUtil;
import com.ffusion.util.beans.LocalizableAccountID;
import com.ffusion.util.settings.AccountSettings;
import com.ffusion.util.settings.SystemException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;

public class AccountUtil
{
  public static final String RESOURCE_BUNDLE = "com.ffusion.beans.accounts.resources";
  public static final String KEY_ACCOUNT_DISPLAY_TEXT = "AccountDisplayText";
  public static final String KEY_ACCOUNT_TYPE = "AccountType";
  
  public static String buildAccountDisplayText(String paramString, Locale paramLocale)
    throws UtilException
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    int i = -1;
    if (paramString == null) {
      return null;
    }
    a(paramString);
    i = paramString.lastIndexOf('-');
    str2 = paramString.substring(0, i);
    str3 = paramString.substring(i + 1);
    try
    {
      str1 = AccountSettings.buildAccountDisplayText(str2, "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + str3, paramLocale);
    }
    catch (SystemException localSystemException)
    {
      throw new UtilException("An error occurred while building an account display text.", localSystemException);
    }
    return str1;
  }
  
  public static String buildAccountDisplayTextForExport(String paramString1, String paramString2, Locale paramLocale)
    throws UtilException
  {
    String str1 = null;
    String str2 = null;
    String str3 = null;
    int i = -1;
    if (paramString2 == null) {
      return null;
    }
    a(paramString2);
    i = paramString2.lastIndexOf('-');
    str2 = paramString2.substring(0, i);
    str3 = paramString2.substring(i + 1);
    try
    {
      int j = Integer.parseInt(str3);
      str1 = AccountSettings.buildAccountDisplayTextForExport(paramString1, str2, "com.ffusion.beans.accounts.resources", "AccountDisplayText", "com.ffusion.beans.accounts.resources", "AccountType" + str3, j, paramLocale);
    }
    catch (SystemException localSystemException)
    {
      throw new UtilException("An error occurred while building an account display text.", localSystemException);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new UtilException("Invalid account ID, '" + paramString2 + "'; account type could not be parsed as an integer.", localNumberFormatException);
    }
    return str1;
  }
  
  public static LocalizableAccountID buildLocalizableAccountID(String paramString)
    throws UtilException
  {
    String str1 = null;
    String str2 = null;
    int i = -1;
    if (paramString == null) {
      return null;
    }
    a(paramString);
    i = paramString.lastIndexOf('-');
    str1 = paramString.substring(0, i);
    str2 = paramString.substring(i + 1);
    com.ffusion.beans.accounts.Account localAccount = new com.ffusion.beans.accounts.Account();
    localAccount.setID(str1, str2);
    return localAccount.buildLocalizableAccountID();
  }
  
  private static void a(String paramString)
    throws UtilException
  {
    int i = paramString.lastIndexOf('-');
    if (i == -1) {
      throw new UtilException("Invalid account ID, '" + paramString + "'; missing hyphen detected.");
    }
    if (i == 0) {
      throw new UtilException("Invalid account ID, '" + paramString + "'; missing account number detected.");
    }
    if (i == paramString.length() - 1) {
      throw new UtilException("Invalid account ID, '" + paramString + "'; missing account type detected.");
    }
  }
  
  public static String getRoutingNumberFromAccountDisplayText(String paramString)
    throws UtilException
  {
    String str = null;
    int i = -1;
    if (paramString == null) {
      return null;
    }
    i = paramString.lastIndexOf('-');
    if (i == -1) {
      throw new UtilException("Invalid account display text, '" + paramString + "'; missing hyphen detected.");
    }
    if (i == 0) {
      throw new UtilException("Invalid account display text, '" + paramString + "'; missing account number detected.");
    }
    if (i == paramString.length() - 1) {
      throw new UtilException("Invalid account display text, '" + paramString + "'; missing account type detected.");
    }
    i = paramString.lastIndexOf('-', i - 1);
    if (i > 0) {
      try
      {
        str = paramString.substring(0, i);
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
      {
        throw new UtilException("An error occurred while getting the routing number from the account display text.", localIndexOutOfBoundsException);
      }
    }
    return str;
  }
  
  public static String getAccountInfoFromAccountDisplayText(String paramString)
    throws UtilException
  {
    String str = null;
    int i = -1;
    if (paramString == null) {
      return null;
    }
    i = paramString.lastIndexOf('-');
    if (i == -1) {
      throw new UtilException("Invalid account display text, '" + paramString + "'; missing hyphen detected.");
    }
    if (i == 0) {
      throw new UtilException("Invalid account display text, '" + paramString + "'; missing account number detected.");
    }
    if (i == paramString.length() - 1) {
      throw new UtilException("Invalid account display text, '" + paramString + "'; missing account type detected.");
    }
    i = paramString.lastIndexOf('-', i - 1);
    if (i > 0) {
      try
      {
        str = paramString.substring(i + 1);
      }
      catch (IndexOutOfBoundsException localIndexOutOfBoundsException)
      {
        throw new UtilException("An error occurred while getting the account information from the account display text.", localIndexOutOfBoundsException);
      }
    } else {
      str = paramString;
    }
    return str;
  }
  
  public static com.ffusion.beans.aggregation.Account mapAccountsAccountBeanToAggregationAccountBean(com.ffusion.beans.accounts.Account paramAccount, Locale paramLocale)
  {
    Accounts localAccounts = new Accounts(paramLocale);
    com.ffusion.beans.aggregation.Account localAccount = localAccounts.create(paramAccount.getNumber());
    localAccount.setLocale(paramAccount.getLocale());
    localAccount.setInstitutionID(paramAccount.getRoutingNum());
    localAccount.setNickName(paramAccount.getNickName());
    localAccount.setNumber(paramAccount.getNumber());
    localAccount.setInstitutionName(paramAccount.getBankName());
    localAccount.setType(String.valueOf(com.ffusion.beans.aggregation.Account.getAggregationTypeFromAccountType(paramAccount.getTypeValue())));
    localAccount.setStatus(paramAccount.getStatus());
    localAccount.setID(paramAccount.getID());
    localAccount.set("CURRENCY_CODE", paramAccount.getCurrencyCode());
    return localAccount;
  }
  
  public static Currency convertCurrency(String paramString1, String paramString2, BigDecimal paramBigDecimal, Currency paramCurrency)
  {
    Currency localCurrency = null;
    if ((paramString2 == null) || (paramString2.trim().length() == 0) || ((paramString2 != null) && (paramString2.equals(paramString1))))
    {
      if (paramCurrency == null) {
        return null;
      }
      localCurrency = new Currency(paramCurrency.getAmountValue(), paramCurrency.getCurrencyCode(), paramCurrency.getLocale());
    }
    else if ((paramBigDecimal == null) || (paramCurrency == null))
    {
      localCurrency = null;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        BigDecimal localBigDecimal = paramCurrency.getAmountValue().multiply(paramBigDecimal);
        int i = FXUtil.getNumDecimals(paramString2, localHashMap);
        localBigDecimal = localBigDecimal.setScale(i, 6);
        if (localCurrency == null) {
          localCurrency = new Currency(localBigDecimal, paramCurrency.getLocale());
        } else {
          localCurrency.setAmount(localBigDecimal);
        }
        localCurrency.setCurrencyCode(paramString2);
      }
      catch (Exception localException) {}
    }
    return localCurrency;
  }
  
  private Balance a(String paramString1, String paramString2, BigDecimal paramBigDecimal, Balance paramBalance)
  {
    Balance localBalance = null;
    if ((paramString2 == null) || (paramString2.trim().length() == 0) || ((paramString2 != null) && (paramString2.equals(paramString1))))
    {
      if (paramBalance == null) {
        return null;
      }
      localBalance = (Balance)paramBalance.clone();
    }
    else if ((paramBigDecimal == null) || (paramBalance == null))
    {
      localBalance = null;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        BigDecimal localBigDecimal = paramBalance.getAmountValue().getAmountValue().multiply(paramBigDecimal);
        int i = FXUtil.getNumDecimals(paramString2, localHashMap);
        localBigDecimal = localBigDecimal.setScale(i, 6);
        Currency localCurrency = new Currency(localBigDecimal, paramBalance.getLocale());
        localCurrency.setCurrencyCode(paramString2);
        if (localBalance == null) {
          localBalance = new Balance(paramBalance.getLocale());
        }
        localBalance.setAmount(localCurrency);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return null;
      }
    }
    return localBalance;
  }
  
  public static BigDecimal getConversionFXRate(FXRates paramFXRates, String paramString1, String paramString2)
  {
    BigDecimal localBigDecimal = new BigDecimal(1.0D);
    if ((paramString2 == null) || (paramString2.trim().length() == 0) || (paramFXRates == null))
    {
      localBigDecimal = null;
    }
    else
    {
      FXRate localFXRate = paramFXRates.getByCurrencyCode(paramString1, paramString2);
      if (localFXRate != null) {
        localBigDecimal = localFXRate.getBuyPrice().getAmountValue();
      } else {
        localBigDecimal = null;
      }
    }
    return localBigDecimal;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.AccountUtil
 * JD-Core Version:    0.7.0.1
 */