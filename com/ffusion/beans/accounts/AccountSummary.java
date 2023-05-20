package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.fx.FXUtil;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.HashMap;
import java.util.Locale;

public class AccountSummary
  extends ExtendABean
{
  public static final String ACCOUNT_SUMMARY = "ACCOUNT_SUMMARY";
  public static final String ACCOUNT_ID = "ACCOUNT_ID";
  public static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public static final String BANK_ID = "BANK_ID";
  public static final String ROUTING_NUMBER = "ROUTING_NUMBER";
  public static final String VALUE_DATE = "VALUE_DATE";
  public static final String SUMMARY_DATE = "SUMMARY_DATE";
  private String a13;
  private String a1W;
  private String a1Y;
  private String a1X;
  private DateTime a12;
  private DateTime a1Z;
  private String a11 = "";
  private BigDecimal a10 = new BigDecimal(0.0D);
  
  public String getAccountID()
  {
    return this.a13;
  }
  
  public String getAccountNumber()
  {
    return this.a1W;
  }
  
  public void setAccountID(String paramString)
  {
    this.a13 = paramString;
  }
  
  public void setAccountNumber(String paramString)
  {
    this.a1W = paramString;
  }
  
  public String getBankID()
  {
    return this.a1Y;
  }
  
  public void setBankID(String paramString)
  {
    this.a1Y = paramString;
  }
  
  public String getRoutingNumber()
  {
    return this.a1X;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.a1X = paramString;
  }
  
  public DateTime getValueDate()
  {
    return this.a12;
  }
  
  public void setValueDate(DateTime paramDateTime)
  {
    this.a12 = paramDateTime;
  }
  
  public DateTime getSummaryDate()
  {
    return this.a1Z;
  }
  
  public void setSummaryDate(DateTime paramDateTime)
  {
    this.a1Z = paramDateTime;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a12 != null) {
      this.a12.setLocale(paramLocale);
    }
    if (this.a1Z != null) {
      this.a1Z.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    if (this.a12 != null) {
      this.a12.setFormat(paramString);
    }
    if (this.a1Z != null) {
      this.a1Z.setFormat(paramString);
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
    AccountSummary localAccountSummary = (AccountSummary)paramObject;
    int i = 1;
    if ((paramString.equals("ACCOUNT_ID")) && (getAccountID() != null) && (localAccountSummary.getAccountID() != null)) {
      i = numStringCompare(getAccountID(), localAccountSummary.getAccountID());
    } else if ((paramString.equals("ACCOUNT_NUMBER")) && (getAccountNumber() != null) && (localAccountSummary.getAccountNumber() != null)) {
      i = numStringCompare(getAccountNumber(), localAccountSummary.getAccountNumber());
    } else if ((paramString.equals("BANK_ID")) && (getBankID() != null) && (localAccountSummary.getBankID() != null)) {
      i = numStringCompare(getBankID(), localAccountSummary.getBankID());
    } else if ((paramString.equals("ROUTING_NUMBER")) && (getRoutingNumber() != null) && (localAccountSummary.getRoutingNumber() != null)) {
      i = localCollator.compare(getRoutingNumber(), localAccountSummary.getRoutingNumber());
    } else if ((paramString.equals("VALUE_DATE")) && (getValueDate() != null) && (localAccountSummary.getValueDate() != null)) {
      i = getValueDate().equals(localAccountSummary.getValueDate()) ? 0 : getValueDate().before(localAccountSummary.getValueDate()) ? -1 : 1;
    } else if ((paramString.equals("SUMMARY_DATE")) && (getSummaryDate() != null) && (localAccountSummary.getSummaryDate() != null)) {
      i = getSummaryDate().equals(localAccountSummary.getSummaryDate()) ? 0 : getSummaryDate().before(localAccountSummary.getSummaryDate()) ? -1 : 1;
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ACCOUNT_ID")) && (this.a13 != null)) {
      return isFilterable(this.a13, paramString2, paramString3);
    }
    if ((paramString1.equals("ACCOUNT_NUMBER")) && (this.a1W != null)) {
      return isFilterable(this.a1W, paramString2, paramString3);
    }
    if ((paramString1.equals("BANK_ID")) && (this.a1Y != null)) {
      return isFilterable(this.a1Y, paramString2, paramString3);
    }
    if ((paramString1.equals("ROUTING_NUMBER")) && (this.a1X != null)) {
      return isFilterable(this.a1X, paramString2, paramString3);
    }
    if ((paramString1.equals("VALUE_DATE")) && (this.a12 != null)) {
      return this.a12.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("SUMMARY_DATE")) && (this.a1Z != null)) {
      return this.a1Z.isFilterable("VALUE" + paramString2 + paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ACCOUNT_ID"))
    {
      setAccountID(paramString2);
    }
    else if (paramString1.equals("ACCOUNT_NUMBER"))
    {
      setAccountNumber(paramString2);
    }
    else if (paramString1.equals("BANK_ID"))
    {
      setBankID(paramString2);
    }
    else if (paramString1.equals("ROUTING_NUMBER"))
    {
      setRoutingNumber(paramString2);
    }
    else if (paramString1.equals("VALUE_DATE"))
    {
      if (this.a12 == null)
      {
        this.a12 = new DateTime(this.locale);
        this.a12.setFormat(this.datetype);
      }
      this.a12.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("SUMMARY_DATE"))
    {
      if (this.a1Z == null)
      {
        this.a1Z = new DateTime(this.locale);
        this.a1Z.setFormat(this.datetype);
      }
      this.a1Z.fromXMLFormat(paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACCOUNT_SUMMARY");
    if (this.a13 != null) {
      XMLHandler.appendTag(localStringBuffer, "ACCOUNT_ID", this.a13);
    }
    if (this.a1W != null) {
      XMLHandler.appendTag(localStringBuffer, "ACCOUNT_NUMBER", this.a1W);
    }
    if (this.a1Y != null) {
      XMLHandler.appendTag(localStringBuffer, "BANK_ID", this.a1Y);
    }
    if (this.a1X != null) {
      XMLHandler.appendTag(localStringBuffer, "ROUTING_NUMBER", this.a1X);
    }
    if (this.a12 != null) {
      XMLHandler.appendTag(localStringBuffer, "VALUE_DATE", this.a12.toXMLFormat());
    }
    if (this.a1Z != null) {
      XMLHandler.appendTag(localStringBuffer, "SUMMARY_DATE", this.a1Z.toXMLFormat());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "ACCOUNT_SUMMARY");
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
  
  public String getDisplayCurrency()
  {
    return this.a11;
  }
  
  public BigDecimal getDisplayFXRate()
  {
    return this.a10;
  }
  
  public void calculateDisplayBalances(String paramString, BigDecimal paramBigDecimal)
  {
    this.a10 = paramBigDecimal;
    this.a11 = paramString;
  }
  
  protected Currency convertDisplay(Currency paramCurrency)
  {
    Currency localCurrency = null;
    if ((this.a11 == null) || (this.a11.trim().length() == 0) || ((this.a11 != null) && (this.a11.equals(getCurrencyCode(null)))))
    {
      if (paramCurrency == null) {
        return null;
      }
      localCurrency = new Currency(paramCurrency.getAmountValue(), paramCurrency.getCurrencyCode(), paramCurrency.getLocale());
    }
    else if ((this.a10 == null) || (paramCurrency == null))
    {
      localCurrency = null;
    }
    else
    {
      try
      {
        HashMap localHashMap = new HashMap();
        BigDecimal localBigDecimal = paramCurrency.getAmountValue().multiply(this.a10);
        int i = FXUtil.getNumDecimals(this.a11, localHashMap);
        localBigDecimal = localBigDecimal.setScale(i, 6);
        if (localCurrency == null) {
          localCurrency = new Currency(localBigDecimal, this.locale);
        } else {
          localCurrency.setAmount(localBigDecimal);
        }
        localCurrency.setCurrencyCode(this.a11);
      }
      catch (Exception localException) {}
    }
    return localCurrency;
  }
  
  public String getCurrencyCode(Accounts paramAccounts)
  {
    if (paramAccounts != null)
    {
      Account localAccount = paramAccounts.getByIDAndBankIDAndRoutingNum(this.a13, this.a1Y, this.a1X);
      return localAccount.getCurrencyCode();
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AccountSummary
 * JD-Core Version:    0.7.0.1
 */