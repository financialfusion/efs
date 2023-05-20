package com.ffusion.beans.register;

import com.ffusion.beans.Currency;
import com.ffusion.util.LocaleableList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Reader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Locale;

public class TransactionCategories
  extends LocaleableList
  implements XMLable
{
  public static final String TRANSACTION_CATEGORIES = "TRANSACTION_CATEGORIES";
  private static final int a = -1;
  private TransactionCategory jdField_if = null;
  
  public TransactionCategories() {}
  
  public TransactionCategories(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public TransactionCategory create()
  {
    TransactionCategory localTransactionCategory = new TransactionCategory();
    add(localTransactionCategory);
    return localTransactionCategory;
  }
  
  public void setCurrent(String paramString)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.jdField_if = null;
      return;
    }
    while ((size() <= i) && (i < 100)) {
      add(new TransactionCategory());
    }
    this.jdField_if = ((TransactionCategory)get(i));
  }
  
  public void setRemoveCategory(String paramString)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(paramString);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      this.jdField_if = null;
      return;
    }
    try
    {
      remove(i);
    }
    catch (IndexOutOfBoundsException localIndexOutOfBoundsException) {}
    this.jdField_if = null;
  }
  
  public void setAmount(String paramString)
  {
    if (this.jdField_if == null)
    {
      if (size() > 0)
      {
        this.jdField_if = ((TransactionCategory)get(0));
        clear();
      }
      else
      {
        this.jdField_if = new TransactionCategory();
        this.jdField_if.setRegisterCategoryId(0);
      }
      add(this.jdField_if);
    }
    this.jdField_if.setAmount(paramString);
  }
  
  public void setAmount(Currency paramCurrency)
  {
    if (this.jdField_if == null)
    {
      if (size() > 0)
      {
        this.jdField_if = ((TransactionCategory)get(0));
        clear();
      }
      else
      {
        this.jdField_if = new TransactionCategory();
        this.jdField_if.setRegisterCategoryId(0);
      }
      add(this.jdField_if);
    }
    this.jdField_if.setAmount(paramCurrency);
  }
  
  public String getAmount()
  {
    Currency localCurrency = getAmountValue();
    if (localCurrency == null) {
      return "";
    }
    return localCurrency.toString();
  }
  
  public Currency getAmountValue()
  {
    if (this.jdField_if != null) {
      return this.jdField_if.getAmountValue();
    }
    clearEmptyTransactionCategories();
    Iterator localIterator = iterator();
    Currency localCurrency = new Currency("0", this.locale);
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      if (localTransactionCategory.getAmountValue() != null)
      {
        localCurrency.setCurrencyCode(localTransactionCategory.getAmountValue().getCurrencyCode());
        localCurrency.addAmount(localTransactionCategory.getAmountValue());
      }
    }
    return localCurrency;
  }
  
  public String getDecimalAmount()
  {
    Currency localCurrency = getAmountValue();
    if (localCurrency == null) {
      return "";
    }
    DecimalFormat localDecimalFormat = new DecimalFormat("#0.00");
    return localDecimalFormat.format(localCurrency.doubleValue());
  }
  
  public String getDecimalAbsoluteAmount()
  {
    Currency localCurrency = getAmountValue();
    if (localCurrency == null) {
      return "";
    }
    DecimalFormat localDecimalFormat = new DecimalFormat("#0.00");
    BigDecimal localBigDecimal1 = new BigDecimal(localCurrency.doubleValue());
    BigDecimal localBigDecimal2 = localBigDecimal1.abs();
    return localDecimalFormat.format(localBigDecimal2.doubleValue());
  }
  
  public boolean hasNegativeAmount()
  {
    clearEmptyTransactionCategories();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      if ((localTransactionCategory.getAmountValue() != null) && (localTransactionCategory.getAmountValue().getAmountValue() != null) && (localTransactionCategory.getAmountValue().getAmountValue().doubleValue() < 0.0D)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean hasInvalidAmount()
  {
    clearEmptyTransactionCategories();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      if (localTransactionCategory.hasInvalidAmount()) {
        return true;
      }
    }
    return false;
  }
  
  public boolean hasInvalidCategory()
  {
    clearEmptyTransactionCategories();
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      if (!a(localTransactionCategory.getRegisterCategoryId())) {
        return true;
      }
    }
    return false;
  }
  
  public void clearEmptyTransactionCategories()
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      if ((!a(localTransactionCategory.getRegisterCategoryId())) && (localTransactionCategory.getAmountValue() == null)) {
        localIterator.remove();
      }
    }
  }
  
  public void clearInvalidTransactionCategories()
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      if ((!a(localTransactionCategory.getRegisterCategoryId())) || (localTransactionCategory.getAmountValue() == null)) {
        localIterator.remove();
      }
    }
  }
  
  private boolean a(String paramString)
  {
    return (paramString != null) && (paramString.length() != 0) && (Integer.parseInt(paramString) != -1);
  }
  
  public void negateAmount()
  {
    if (this.jdField_if != null) {
      this.jdField_if.negateAmount();
    }
  }
  
  public void negateAmounts()
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      localTransactionCategory.negateAmount();
    }
  }
  
  public void setAmountsNegative()
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      if ((localTransactionCategory.getAmountValue() != null) && (localTransactionCategory.getAmountValue().getAmountValue().doubleValue() > 0.0D)) {
        localTransactionCategory.negateAmount();
      }
    }
  }
  
  public void setAmountsPositive()
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      if (localTransactionCategory.getAmountValue().getAmountValue().doubleValue() < 0.0D) {
        localTransactionCategory.negateAmount();
      }
    }
  }
  
  public void setCurrency(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      Currency localCurrency = localTransactionCategory.getAmountValue();
      if (localCurrency != null) {
        localCurrency.setCurrencyCode(paramString);
      }
    }
  }
  
  public void setRegisterCategoryId(int paramInt)
  {
    if (this.jdField_if == null)
    {
      if (size() > 0)
      {
        this.jdField_if = ((TransactionCategory)get(0));
        clear();
      }
      else
      {
        this.jdField_if = new TransactionCategory();
      }
      add(this.jdField_if);
    }
    this.jdField_if.setRegisterCategoryId(paramInt);
  }
  
  public String getRegisterCategoryId()
  {
    if (this.jdField_if != null) {
      return this.jdField_if.getRegisterCategoryId();
    }
    clearEmptyTransactionCategories();
    if (size() == 0) {
      return null;
    }
    TransactionCategory localTransactionCategory = (TransactionCategory)get(0);
    return localTransactionCategory.getRegisterCategoryId();
  }
  
  public TransactionCategory getTransactionCategory(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      if (localTransactionCategory.getRegisterCategoryId().equals(paramString)) {
        return localTransactionCategory;
      }
    }
    return null;
  }
  
  public boolean removeTransactionCategory(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      if (localTransactionCategory.getRegisterCategoryId().equals(paramString))
      {
        remove(localTransactionCategory);
        return true;
      }
    }
    return false;
  }
  
  public boolean removeTransactionCategory(RegisterCategory paramRegisterCategory)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      TransactionCategory localTransactionCategory = (TransactionCategory)localIterator.next();
      if (localTransactionCategory.getRegisterCategoryId().equals(paramRegisterCategory.getId()))
      {
        remove(localTransactionCategory);
        return true;
      }
    }
    return false;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "TRANSACTION_CATEGORIES");
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localStringBuffer.append(((TransactionCategory)localIterator.next()).toXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "TRANSACTION_CATEGORIES");
    return XMLHandler.format(localStringBuffer.toString());
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramString);
    }
    catch (Exception localException) {}
  }
  
  public void startXMLParsing(Reader paramReader)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramReader);
    }
    catch (Exception localException) {}
  }
  
  private class a
    extends XMLHandler
  {
    private String jdField_int = "";
    private String jdField_new = "";
    
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("TRANSACTION_CATEGORY"))
      {
        TransactionCategory localTransactionCategory = new TransactionCategory(TransactionCategories.this.locale);
        localTransactionCategory.continueXMLParsing(getHandler());
        TransactionCategories.this.add(localTransactionCategory);
      }
      this.jdField_int = "";
      this.jdField_new = "";
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      this.jdField_int = paramString1;
      this.jdField_new = paramString2;
    }
    
    a(TransactionCategories.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.register.TransactionCategories
 * JD-Core Version:    0.7.0.1
 */