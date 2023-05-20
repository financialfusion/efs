package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.math.BigDecimal;
import java.util.Locale;

public class AssetAcctSummary
  extends AccountSummary
{
  public static final String ASSET_ACCT_SUMMARY = "ASSET_ACCT_SUMMARY";
  public static final String BOOK_VALUE = "BOOK_VALUE";
  public static final String MARKET_VALUE = "MARKET_VALUE";
  private Currency a3y;
  private Currency a3z;
  private Currency a3B;
  private Currency a3A;
  
  public Currency getBookValue()
  {
    return this.a3y;
  }
  
  public void setBookValue(Currency paramCurrency)
  {
    this.a3y = paramCurrency;
  }
  
  public void setBookValue(String paramString)
  {
    if (this.a3y == null) {
      this.a3y = new Currency(paramString, this.locale);
    } else {
      this.a3y.fromString(paramString);
    }
  }
  
  public Currency getMarketValue()
  {
    return this.a3z;
  }
  
  public void setMarketValue(Currency paramCurrency)
  {
    this.a3z = paramCurrency;
  }
  
  public void setMarketValue(String paramString)
  {
    if (this.a3z == null) {
      this.a3z = new Currency(paramString, this.locale);
    } else {
      this.a3z.fromString(paramString);
    }
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a3y != null) {
      this.a3y.setLocale(paramLocale);
    }
    if (this.a3z != null) {
      this.a3z.setLocale(paramLocale);
    }
  }
  
  public int compare(Object paramObject, String paramString)
  {
    AssetAcctSummary localAssetAcctSummary = (AssetAcctSummary)paramObject;
    int i = 1;
    if (paramString.equals("BOOK_VALUE")) {
      i = Currency.compare(this.a3y, localAssetAcctSummary.getBookValue());
    } else if (paramString.equals("MARKET_VALUE")) {
      i = Currency.compare(this.a3z, localAssetAcctSummary.getMarketValue());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("BOOK_VALUE")) && (this.a3y != null)) {
      return this.a3y.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("MARKET_VALUE")) && (this.a3z != null)) {
      return this.a3z.isFilterable(paramString1 + paramString2 + paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("BOOK_VALUE")) {
      setBookValue(paramString2);
    } else if (paramString1.equals("MARKET_VALUE")) {
      setMarketValue(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ASSET_ACCT_SUMMARY");
    if (this.a3y != null) {
      XMLHandler.appendTag(localStringBuffer, "BOOK_VALUE", this.a3y.doubleValue());
    }
    if (this.a3z != null) {
      XMLHandler.appendTag(localStringBuffer, "MARKET_VALUE", this.a3z.doubleValue());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ASSET_ACCT_SUMMARY");
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
    this.a3B = convertDisplay(this.a3y);
    this.a3A = convertDisplay(this.a3z);
  }
  
  public Currency getDisplayBookValue()
  {
    return this.a3B;
  }
  
  public Currency getDisplayMarketValue()
  {
    return this.a3A;
  }
  
  public String getCurrencyCode(Accounts paramAccounts)
  {
    String str = super.getCurrencyCode(paramAccounts);
    if (str != null) {
      return str;
    }
    if (this.a3y != null) {
      return this.a3y.getCurrencyCode();
    }
    if (this.a3z != null) {
      return this.a3z.getCurrencyCode();
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AssetAcctSummary
 * JD-Core Version:    0.7.0.1
 */