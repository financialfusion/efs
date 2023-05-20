package com.ffusion.beans.aggregation;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Locale;

public class Transaction
  extends ExtendABean
  implements TransactionTypes, TransactionCategories, Comparable
{
  private String aZe;
  private int aZj;
  private int aZf;
  private String aZl;
  private String aZm;
  private String aZk;
  private DateTime aZg;
  private Currency aZh;
  private Currency aZi;
  protected String trackingID;
  
  protected Transaction() {}
  
  protected Transaction(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
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
    return compare(paramObject, "DATE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Transaction localTransaction = (Transaction)paramObject;
    int i = 1;
    if ((paramString.equals("ID")) && (getID() != null) && (localTransaction.getID() != null)) {
      i = getID().compareTo(localTransaction.getID());
    } else if ((paramString.equals("DESCRIPTION")) && (getDescription() != null) && (localTransaction.getDescription() != null)) {
      i = getDescription().compareTo(localTransaction.getDescription());
    } else if ((paramString.equals("REFERENCENUMBER")) && (getReferenceNumber() != null) && (localTransaction.getReferenceNumber() != null)) {
      i = getReferenceNumber().compareTo(localTransaction.getReferenceNumber());
    } else if ((paramString.equals("MEMO")) && (getMemo() != null) && (localTransaction.getMemo() != null)) {
      i = getMemo().compareTo(localTransaction.getMemo());
    } else if ((paramString.equals("AMOUNT")) && (getAmountValue() != null) && (localTransaction.getAmountValue() != null)) {
      i = getAmountValue().compareTo(localTransaction.getAmountValue());
    } else if ((paramString.equals("DATE")) && (getDateValue() != null) && (localTransaction.getDateValue() != null)) {
      i = getDateValue().equals(localTransaction.getDateValue()) ? 0 : getDateValue().before(localTransaction.getDateValue()) ? -1 : 1;
    } else if (paramString.equals("CATEGORY")) {
      i = getCategory() - localTransaction.getCategory();
    } else if (paramString.equals("TYPE")) {
      i = getTypeValue() - localTransaction.getTypeValue();
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("ID")) && (getID() != null)) {
      return isFilterable(getID(), paramString2, paramString3);
    }
    if ((paramString1.equals("DESCRIPTION")) && (getDescription() != null)) {
      return isFilterable(getDescription(), paramString2, paramString3);
    }
    if ((paramString1.equals("REFERENCENUMBER")) && (getReferenceNumber() != null)) {
      return isFilterable(getReferenceNumber(), paramString2, paramString3);
    }
    if ((paramString1.equals("MEMO")) && (getMemo() != null)) {
      return isFilterable(getMemo(), paramString2, paramString3);
    }
    if ((paramString1.equals("AMOUNT")) && (this.aZh != null)) {
      try
      {
        Currency localCurrency = new Currency(paramString3, this.locale);
        return this.aZh.compareTo(localCurrency) == 0;
      }
      catch (Exception localException1)
      {
        return false;
      }
    }
    if ((paramString1.equals("DATE")) && (getDateValue() != null)) {
      try
      {
        DateTime localDateTime = new DateTime(paramString3, this.locale, getDateFormat());
        return getDateValue().equals(localDateTime);
      }
      catch (Exception localException2)
      {
        return false;
      }
    }
    if (paramString1.equals("CATEGORY")) {
      return isFilterable(String.valueOf(getCategory()), paramString2, paramString3);
    }
    if (paramString1.equals("TYPE")) {
      return isFilterable(getType(), paramString2, paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.aZi != null) {
      this.aZi.setLocale(paramLocale);
    }
    if (this.aZh != null) {
      this.aZh.setLocale(paramLocale);
    }
    if (this.aZg != null) {
      this.aZg.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.aZg != null) {
      this.aZg.setFormat(paramString);
    }
  }
  
  public void setID(String paramString)
  {
    this.aZe = paramString;
  }
  
  public String getID()
  {
    return this.aZe;
  }
  
  public void setReferenceNumber(String paramString)
  {
    this.aZm = paramString;
  }
  
  public String getReferenceNumber()
  {
    return this.aZm;
  }
  
  public void setType(int paramInt)
  {
    this.aZj = paramInt;
  }
  
  public String getType()
  {
    return String.valueOf(this.aZj);
  }
  
  public int getTypeValue()
  {
    return this.aZj;
  }
  
  public void setCategory(int paramInt)
  {
    this.aZf = paramInt;
  }
  
  public int getCategory()
  {
    return this.aZf;
  }
  
  public void setDate(DateTime paramDateTime)
  {
    this.aZg = paramDateTime;
    this.aZg.setFormat(this.datetype);
  }
  
  public DateTime getDateValue()
  {
    return this.aZg;
  }
  
  public String getDate()
  {
    return this.aZg.toString();
  }
  
  public void setAmount(String paramString)
  {
    if (this.aZh == null) {
      this.aZh = new Currency(paramString, this.locale);
    } else {
      this.aZh.fromString(paramString);
    }
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.aZh = paramCurrency;
  }
  
  public Currency getAmountValue()
  {
    return this.aZh;
  }
  
  public String getAmount()
  {
    return this.aZh.toString();
  }
  
  public String getDebit()
  {
    if (this.aZh.doubleValue() < 0.0D) {
      return this.aZh.toStringAbsolute();
    }
    return "";
  }
  
  public String getCredit()
  {
    if (this.aZh.doubleValue() >= 0.0D) {
      return this.aZh.toString();
    }
    return "";
  }
  
  public void setDescription(String paramString)
  {
    this.aZl = paramString;
  }
  
  public String getDescription()
  {
    return this.aZl;
  }
  
  public void setMemo(String paramString)
  {
    this.aZk = paramString;
  }
  
  public String getMemo()
  {
    return this.aZk;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("ID"))
    {
      this.aZe = paramString2;
    }
    else if (paramString1.equals("TYPE"))
    {
      this.aZj = Integer.parseInt(paramString2);
    }
    else if (paramString1.equals("CATEGORY"))
    {
      this.aZf = Integer.parseInt(paramString2);
    }
    else if (paramString1.equals("DESCRIPTION"))
    {
      this.aZl = paramString2;
    }
    else if (paramString1.equals("REFERENCENUMBER"))
    {
      this.aZm = paramString2;
    }
    else if (paramString1.equals("MEMO"))
    {
      this.aZk = paramString2;
    }
    else if (paramString1.equals("DATE"))
    {
      if (this.aZg == null)
      {
        this.aZg = new DateTime(this.locale);
        this.aZg.setFormat(this.datetype);
      }
      this.aZg.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("AMOUNT"))
    {
      if (this.aZh == null) {
        this.aZh = new Currency(paramString2, this.locale);
      } else {
        this.aZh.fromString(paramString2);
      }
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
    XMLHandler.appendBeginTag(localStringBuffer, "TRANSACTION");
    XMLHandler.appendTag(localStringBuffer, "ID", this.aZe);
    if (this.aZj != 0) {
      XMLHandler.appendTag(localStringBuffer, "TYPE", this.aZj);
    }
    if (this.aZf != 0) {
      XMLHandler.appendTag(localStringBuffer, "CATEGORY", this.aZf);
    }
    XMLHandler.appendTag(localStringBuffer, "DESCRIPTION", this.aZl);
    XMLHandler.appendTag(localStringBuffer, "REFERENCENUMBER", this.aZm);
    XMLHandler.appendTag(localStringBuffer, "MEMO", this.aZk);
    if (this.aZg != null) {
      XMLHandler.appendTag(localStringBuffer, "DATE", this.aZg.toXMLFormat());
    }
    if (this.aZh != null) {
      XMLHandler.appendTag(localStringBuffer, "AMOUNT", this.aZh.doubleValue());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "TRANSACTION");
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new ExtendABean.InternalXMLHandler(this));
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.aggregation.Transaction
 * JD-Core Version:    0.7.0.1
 */