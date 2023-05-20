package com.ffusion.beans;

import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Locale;

public class Balance
  extends ExtendABean
  implements Cloneable
{
  private Currency a5U;
  private DateTime a5T;
  
  public Balance() {}
  
  public Balance(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public Object clone()
  {
    try
    {
      Balance localBalance = (Balance)super.clone();
      if (localBalance.a5U != null) {
        localBalance.a5U = ((Currency)localBalance.a5U.clone());
      }
      if (localBalance.a5T != null) {
        localBalance.a5T = ((DateTime)localBalance.a5T.clone());
      }
      return localBalance;
    }
    catch (Exception localException) {}
    return null;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Balance localBalance = (Balance)paramObject;
    int i = 1;
    if ((paramString.equals("AMOUNT")) && (this.a5U != null) && (localBalance.a5U != null)) {
      i = this.a5U.compareTo(localBalance.a5U);
    } else if ((paramString.equals("DATE")) && (getDateValue() != null) && (localBalance.getDateValue() != null)) {
      i = getDateValue().equals(localBalance.getDateValue()) ? 0 : getDateValue().before(localBalance.getDateValue()) ? -1 : 1;
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("AMOUNT")) && (this.a5U != null)) {
      return this.a5U.isFilterable("VALUE" + paramString2 + paramString3);
    }
    if ((paramString1.equals("DATE")) && (this.a5T != null)) {
      return this.a5T.isFilterable("VALUE" + paramString2 + paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void setFormat(String paramString)
  {
    this.a5U.setFormat(paramString);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a5U != null) {
      this.a5U.setLocale(paramLocale);
    }
    if (this.a5T != null) {
      this.a5T.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.a5T != null) {
      this.a5T.setFormat(paramString);
    }
  }
  
  public void setAmount(String paramString)
  {
    if (this.a5U == null) {
      this.a5U = new Currency(paramString, this.locale);
    } else {
      this.a5U.fromString(paramString);
    }
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.a5U = paramCurrency;
  }
  
  public Currency getAmountValue()
  {
    return this.a5U;
  }
  
  public String getAmount()
  {
    if (this.a5U != null) {
      return this.a5U.toString();
    }
    return null;
  }
  
  public void setDate(DateTime paramDateTime)
  {
    this.a5T = paramDateTime;
  }
  
  public void setDate(String paramString)
  {
    try
    {
      if (this.a5T == null) {
        this.a5T = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.a5T.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException) {}
  }
  
  public DateTime getDateValue()
  {
    return this.a5T;
  }
  
  public String getDate()
  {
    String str = "";
    if (this.a5T != null) {
      str = this.a5T.toString();
    }
    return str;
  }
  
  public String getDate(String paramString)
  {
    String str = null;
    if (this.a5T != null)
    {
      this.a5T.setFormat(paramString);
      str = this.a5T.toString();
    }
    return str;
  }
  
  public String toString()
  {
    if (this.a5U != null) {
      return this.a5U.toString();
    }
    return null;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("DATE"))
    {
      if (this.a5T == null)
      {
        this.a5T = new DateTime(this.locale);
        this.a5T.setFormat(this.datetype);
      }
      this.a5T.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("AMOUNT"))
    {
      setAmount(paramString2);
    }
    else
    {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public void set(Balance paramBalance)
  {
    if (paramBalance != null)
    {
      if (paramBalance.a5T != null) {
        setDate((DateTime)paramBalance.a5T.clone());
      } else {
        setDate((DateTime)null);
      }
      if (paramBalance.a5U != null) {
        setAmount((Currency)paramBalance.a5U.clone());
      } else {
        setAmount((Currency)null);
      }
      super.set(paramBalance);
    }
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BALANCE");
    if (this.a5T != null) {
      XMLHandler.appendTag(localStringBuffer, "DATE", this.a5T.toXMLFormat());
    }
    if (this.a5U != null) {
      XMLHandler.appendTag(localStringBuffer, "AMOUNT", this.a5U.doubleValue());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BALANCE");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.Balance
 * JD-Core Version:    0.7.0.1
 */