package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.text.Collator;
import java.util.Locale;

public class ExtendedAccountSummary
  extends ExtendABean
{
  public static final String EXTENDED_ACCOUNT_SUMMARY = "EXTENDED_ACCOUNT_SUMMARY";
  public static final String ACCOUNT_ID = "ACCOUNT_ID";
  public static final String ACCOUNT_NUMBER = "ACCOUNT_NUMBER";
  public static final String BANK_ID = "BANK_ID";
  public static final String ROUTING_NUMBER = "ROUTING_NUMBER";
  public static final String SUMMARY_DATE = "SUMMARY_DATE";
  public static final String IMMED_AVAIL_AMT = "IMMED_AVAIL_AMT";
  public static final String ONE_DAY_AVAIL_AMT = "ONE_DAY_AVAIL_AMT";
  public static final String MORE_THAN_ONE_DAY_AVAIL_AMT = "MORE_THAN_ONE_DAY_AVAIL_AMT";
  public static final String VALUE_DATE_TIME = "VALUE_DATE_TIME";
  public static final String AMT = "AMT";
  public static final String SUMMARY_TYPE = "SUMMARY_TYPE";
  private String a3J;
  private String a3N;
  private String a3L;
  private String a3I;
  private DateTime a3K;
  private Currency a3M;
  private Currency a3C;
  private Currency a3G;
  private DateTime a3E;
  private Currency a3F;
  private int a3D;
  private int a3H = -1;
  
  public String getAccountID()
  {
    return this.a3J;
  }
  
  public String getAccountNumber()
  {
    return this.a3N;
  }
  
  public void setAccountID(String paramString)
  {
    this.a3J = paramString;
  }
  
  public void setAccountNumber(String paramString)
  {
    this.a3N = paramString;
  }
  
  public String getBankID()
  {
    return this.a3L;
  }
  
  public void setBankID(String paramString)
  {
    this.a3L = paramString;
  }
  
  public String getRoutingNumber()
  {
    return this.a3I;
  }
  
  public void setRoutingNumber(String paramString)
  {
    this.a3I = paramString;
  }
  
  public DateTime getSummaryDate()
  {
    return this.a3K;
  }
  
  public void setSummaryDate(DateTime paramDateTime)
  {
    this.a3K = paramDateTime;
  }
  
  public void setSummaryDate(String paramString)
  {
    try
    {
      if (this.a3K == null) {
        this.a3K = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a3K.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public int getSummaryType()
  {
    return this.a3D;
  }
  
  public void setSummaryType(int paramInt)
  {
    this.a3D = paramInt;
  }
  
  public Currency getAmt()
  {
    return this.a3F;
  }
  
  public void setAmt(Currency paramCurrency)
  {
    this.a3F = paramCurrency;
  }
  
  public void setAmt(String paramString)
  {
    if (this.a3F == null) {
      this.a3F = new Currency(paramString, this.locale);
    } else {
      this.a3F.fromString(paramString);
    }
  }
  
  public Currency getImmedAvailAmt()
  {
    return this.a3M;
  }
  
  public void setImmedAvailAmt(Currency paramCurrency)
  {
    this.a3M = paramCurrency;
  }
  
  public void setImmedAvailAmt(String paramString)
  {
    if (this.a3M == null) {
      this.a3M = new Currency(paramString, this.locale);
    } else {
      this.a3M.fromString(paramString);
    }
  }
  
  public Currency getOneDayAvailAmt()
  {
    return this.a3C;
  }
  
  public void setOneDayAvailAmt(Currency paramCurrency)
  {
    this.a3C = paramCurrency;
  }
  
  public void setOneDayAvailAmt(String paramString)
  {
    if (this.a3C == null) {
      this.a3C = new Currency(paramString, this.locale);
    } else {
      this.a3C.fromString(paramString);
    }
  }
  
  public Currency getMoreThanOneDayAvailAmt()
  {
    return this.a3G;
  }
  
  public void setMoreThanOneDayAvailAmt(Currency paramCurrency)
  {
    this.a3G = paramCurrency;
  }
  
  public void setMoreThanOneDayAvailAmt(String paramString)
  {
    if (this.a3G == null) {
      this.a3G = new Currency(paramString, this.locale);
    } else {
      this.a3G.fromString(paramString);
    }
  }
  
  public DateTime getValueDateTime()
  {
    return this.a3E;
  }
  
  public void setValueDateTime(DateTime paramDateTime)
  {
    this.a3E = paramDateTime;
  }
  
  public void setValueDateTime(String paramString)
  {
    try
    {
      if (this.a3E == null) {
        this.a3E = new DateTime(paramString, this.locale);
      } else {
        this.a3E.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a3K != null) {
      this.a3K.setLocale(paramLocale);
    }
    if (this.a3M != null) {
      this.a3M.setLocale(paramLocale);
    }
    if (this.a3C != null) {
      this.a3C.setLocale(paramLocale);
    }
    if (this.a3G != null) {
      this.a3G.setLocale(paramLocale);
    }
    if (this.a3E != null) {
      this.a3E.setLocale(paramLocale);
    }
    if (this.a3F != null) {
      this.a3F.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.a3K != null) {
      this.a3K.setFormat(paramString);
    }
    if (this.a3E != null) {
      this.a3E.setFormat(paramString);
    }
  }
  
  public int getItemsCount()
  {
    return this.a3H;
  }
  
  public void setItemsCount(int paramInt)
  {
    this.a3H = paramInt;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    ExtendedAccountSummary localExtendedAccountSummary = (ExtendedAccountSummary)paramObject;
    int i = 1;
    if ((paramString.equals("ACCOUNT_ID")) && (this.a3J != null) && (localExtendedAccountSummary.getAccountID() != null)) {
      i = localCollator.compare(this.a3J, localExtendedAccountSummary.getAccountID());
    } else if ((paramString.equals("ACCOUNT_NUMBER")) && (this.a3N != null) && (localExtendedAccountSummary.getAccountNumber() != null)) {
      i = localCollator.compare(this.a3N, localExtendedAccountSummary.getAccountNumber());
    } else if ((paramString.equals("BANK_ID")) && (this.a3L != null) && (localExtendedAccountSummary.getBankID() != null)) {
      i = localCollator.compare(this.a3L, localExtendedAccountSummary.getBankID());
    } else if ((paramString.equals("ROUTING_NUMBER")) && (this.a3I != null) && (localExtendedAccountSummary.getRoutingNumber() != null)) {
      i = localCollator.compare(this.a3I, localExtendedAccountSummary.getRoutingNumber());
    } else if ((paramString.equals("SUMMARY_DATE")) && (this.a3K != null) && (localExtendedAccountSummary.getSummaryDate() != null)) {
      i = this.a3K.compare(localExtendedAccountSummary.getSummaryDate(), paramString);
    } else if ((paramString.equals("IMMED_AVAIL_AMT")) && (this.a3M != null) && (localExtendedAccountSummary.getImmedAvailAmt() != null)) {
      i = this.a3M.compareTo(localExtendedAccountSummary.getImmedAvailAmt());
    } else if ((paramString.equals("ONE_DAY_AVAIL_AMT")) && (this.a3C != null) && (localExtendedAccountSummary.getOneDayAvailAmt() != null)) {
      i = this.a3C.compareTo(localExtendedAccountSummary.getOneDayAvailAmt());
    } else if ((paramString.equals("MORE_THAN_ONE_DAY_AVAIL_AMT")) && (this.a3G != null) && (localExtendedAccountSummary.getMoreThanOneDayAvailAmt() != null)) {
      i = this.a3G.compareTo(localExtendedAccountSummary.getMoreThanOneDayAvailAmt());
    } else if ((paramString.equals("VALUE_DATE_TIME")) && (this.a3E != null) && (localExtendedAccountSummary.getValueDateTime() != null)) {
      i = this.a3E.compare(localExtendedAccountSummary.getValueDateTime(), paramString);
    } else if ((paramString.equals("AMT")) && (this.a3F != null) && (localExtendedAccountSummary.getAmt() != null)) {
      i = this.a3F.compareTo(localExtendedAccountSummary.getAmt());
    } else if (paramString.equals("SUMMARY_TYPE")) {
      i = this.a3D == localExtendedAccountSummary.getSummaryType() ? 0 : this.a3D < localExtendedAccountSummary.getSummaryType() ? -1 : 1;
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ACCOUNT_ID")) && (this.a3J != null)) {
      return isFilterable(this.a3J, paramString2, paramString3);
    }
    if ((paramString1.equals("ACCOUNT_NUMBER")) && (this.a3N != null)) {
      return isFilterable(this.a3N, paramString2, paramString3);
    }
    if ((paramString1.equals("BANK_ID")) && (this.a3L != null)) {
      return isFilterable(this.a3L, paramString2, paramString3);
    }
    if ((paramString1.equals("ROUTING_NUMBER")) && (this.a3I != null)) {
      return isFilterable(this.a3I, paramString2, paramString3);
    }
    if ((paramString1.equals("SUMMARY_DATE")) && (this.a3K != null)) {
      return this.a3K.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("IMMED_AVAIL_AMT")) && (this.a3M != null)) {
      return this.a3M.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("ONE_DAY_AVAIL_AMT")) && (this.a3C != null)) {
      return this.a3C.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("MORE_THAN_ONE_DAY_AVAIL_AMT")) && (this.a3G != null)) {
      return this.a3G.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("VALUE_DATE_TIME")) && (this.a3E != null)) {
      return this.a3E.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if ((paramString1.equals("AMT")) && (this.a3F != null)) {
      return this.a3F.isFilterable(paramString1 + paramString2 + paramString3);
    }
    if (paramString1.equals("SUMMARY_TYPE")) {
      return isFilterable(Integer.toString(this.a3D), paramString2, Float.valueOf(paramString3));
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ACCOUNT_ID"))
    {
      this.a3J = paramString2;
    }
    else if (paramString1.equals("ACCOUNT_NUMBER"))
    {
      this.a3N = paramString2;
    }
    else if (paramString1.equals("BANK_ID"))
    {
      this.a3L = paramString2;
    }
    else if (paramString1.equals("ROUTING_NUMBER"))
    {
      this.a3I = paramString2;
    }
    else if (paramString1.equals("SUMMARY_DATE"))
    {
      if (this.a3K == null)
      {
        this.a3K = new DateTime(this.locale);
        this.a3K.setFormat(this.datetype);
      }
      this.a3K.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("IMMED_AVAIL_AMT"))
    {
      setImmedAvailAmt(paramString2);
    }
    else if (paramString1.equals("ONE_DAY_AVAIL_AMT"))
    {
      setOneDayAvailAmt(paramString2);
    }
    else if (paramString1.equals("MORE_THAN_ONE_DAY_AVAIL_AMT"))
    {
      setMoreThanOneDayAvailAmt(paramString2);
    }
    else if (paramString1.equals("VALUE_DATE_TIME"))
    {
      if (this.a3E == null)
      {
        this.a3E = new DateTime(this.locale);
        this.a3E.setFormat(this.datetype);
      }
      this.a3E.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("AMT"))
    {
      setAmt(paramString2);
    }
    else if (paramString1.equals("SUMMARY_TYPE"))
    {
      this.a3D = Integer.parseInt(paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "EXTENDED_ACCOUNT_SUMMARY");
    XMLHandler.appendTag(localStringBuffer, "ACCOUNT_ID", this.a3J);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNT_NUMBER", this.a3N);
    XMLHandler.appendTag(localStringBuffer, "BANK_ID", this.a3L);
    XMLHandler.appendTag(localStringBuffer, "ROUTING_NUMBER", this.a3I);
    if (this.a3K != null) {
      XMLHandler.appendTag(localStringBuffer, "SUMMARY_DATE", this.a3K.toXMLFormat());
    }
    if (this.a3M != null) {
      XMLHandler.appendTag(localStringBuffer, "IMMED_AVAIL_AMT", this.a3M.doubleValue());
    }
    if (this.a3C != null) {
      XMLHandler.appendTag(localStringBuffer, "ONE_DAY_AVAIL_AMT", this.a3C.doubleValue());
    }
    if (this.a3G != null) {
      XMLHandler.appendTag(localStringBuffer, "MORE_THAN_ONE_DAY_AVAIL_AMT", this.a3G.doubleValue());
    }
    if (this.a3E != null) {
      XMLHandler.appendTag(localStringBuffer, "VALUE_DATE_TIME", this.a3E.toXMLFormat());
    }
    if (this.a3F != null) {
      XMLHandler.appendTag(localStringBuffer, "AMT", this.a3F.doubleValue());
    }
    if (this.a3D != 0) {
      XMLHandler.appendTag(localStringBuffer, "SUMMARY_TYPE", this.a3D);
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "EXTENDED_ACCOUNT_SUMMARY");
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
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.ExtendedAccountSummary
 * JD-Core Version:    0.7.0.1
 */