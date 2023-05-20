package com.ffusion.beans.register;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.Filterable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

public class TransactionCategory
  extends ExtendABean
  implements XMLable, Filterable
{
  public static final String TRANSACTION_CATEGORY = "TRANSACTION_CATEGORY";
  public static final String AMOUNT = "AMOUNT";
  public static final String REGISTER_CATEGORY_ID = "REGISTER_CATEGORY_ID";
  Currency aVA = null;
  int aVB = -1;
  String aVC = null;
  
  public TransactionCategory() {}
  
  public TransactionCategory(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public TransactionCategory(String paramString)
  {
    setAmount(paramString);
  }
  
  public TransactionCategory(Locale paramLocale, String paramString)
  {
    super(paramLocale);
    setAmount(paramString);
  }
  
  public TransactionCategory(String paramString, int paramInt)
  {
    this.aVB = paramInt;
    setAmount(paramString);
  }
  
  public TransactionCategory(Currency paramCurrency, int paramInt)
  {
    this.aVB = paramInt;
    this.aVA = paramCurrency;
  }
  
  public boolean hasInvalidAmount()
  {
    return (this.aVC != null) && (this.aVA == null);
  }
  
  public void setAmount(Currency paramCurrency)
  {
    this.aVA = paramCurrency;
    this.aVC = null;
  }
  
  public void setAmount(String paramString)
  {
    if ((paramString == null) || (paramString.trim().equals("")))
    {
      this.aVA = null;
      return;
    }
    try
    {
      double d = Double.parseDouble(paramString);
      this.aVA = new Currency(paramString, this.locale);
      this.aVC = null;
    }
    catch (Exception localException)
    {
      this.aVA = null;
      this.aVC = paramString;
    }
  }
  
  public String getAmount()
  {
    if (this.aVA == null) {
      return this.aVC;
    }
    return this.aVA.toString();
  }
  
  public Currency getAmountValue()
  {
    return this.aVA;
  }
  
  public String getDecimalAmount()
  {
    if (this.aVA == null) {
      return this.aVC;
    }
    DecimalFormat localDecimalFormat = new DecimalFormat("#0.00");
    return localDecimalFormat.format(this.aVA.doubleValue());
  }
  
  public void negateAmount()
  {
    if (this.aVA != null) {
      this.aVA.setAmount(this.aVA.getAmountValue().negate());
    }
  }
  
  public void setRegisterCategoryId(int paramInt)
  {
    this.aVB = paramInt;
  }
  
  public void setRegisterCategoryId(String paramString)
  {
    this.aVB = Integer.parseInt(paramString);
  }
  
  public String getRegisterCategoryId()
  {
    return String.valueOf(this.aVB);
  }
  
  public int getRegisterCategoryIdValue()
  {
    return this.aVB;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    TransactionCategory localTransactionCategory = (TransactionCategory)paramObject;
    int i = 1;
    if ((paramString.equals("AMOUNT")) && (this.aVA != null)) {
      i = this.aVA.compareTo(localTransactionCategory.getAmountValue());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equals("AMOUNT")) && (this.aVA != null)) {
      return this.aVA.isFilterable("VALUE" + paramString2 + paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
  }
  
  public void set(TransactionCategory paramTransactionCategory)
  {
    this.aVA = new Currency(paramTransactionCategory.getAmountValue().getAmountValue(), paramTransactionCategory.getAmountValue().getCurrencyCode(), this.locale);
    this.aVB = paramTransactionCategory.getRegisterCategoryIdValue();
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("AMOUNT")) {
      setAmount(paramString2);
    } else if (paramString1.equalsIgnoreCase("REGISTER_CATEGORY_ID")) {
      setRegisterCategoryId(paramString2);
    } else {
      return super.set(paramString1, paramString2);
    }
    return true;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "TRANSACTION_CATEGORY");
    if (this.aVA != null) {
      XMLHandler.appendTag(localStringBuffer, "AMOUNT", this.aVA.getAmountValue().toString());
    }
    XMLHandler.appendTag(localStringBuffer, "REGISTER_CATEGORY_ID", this.aVB);
    XMLHandler.appendEndTag(localStringBuffer, "TRANSACTION_CATEGORY");
    return localStringBuffer.toString();
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.aVA != null) {
      this.aVA.setLocale(this.locale);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.register.TransactionCategory
 * JD-Core Version:    0.7.0.1
 */