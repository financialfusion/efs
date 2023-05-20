package com.ffusion.beans.business;

import com.ffusion.beans.Currency;
import com.ffusion.csil.beans.entitlements.Limit;
import com.ffusion.util.Sortable;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class TransactionLimit
  extends Limit
  implements BusinessDefines, Sortable
{
  DecimalFormat ii;
  private String ig;
  private boolean ie = false;
  private String ic;
  public static final String DEFAULT_LIMIT_AMOUNT = "9999999.00";
  private boolean ib = true;
  private boolean ih = false;
  private String h9 = "USD";
  private boolean ia = false;
  
  public TransactionLimit() {}
  
  public TransactionLimit(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.ii = ((DecimalFormat)DecimalFormat.getCurrencyInstance(this.locale));
  }
  
  public String getCurrencyString()
  {
    String str1 = "";
    if (getData() != null)
    {
      Currency localCurrency = new Currency(getData(), this.locale);
      String str2 = localCurrency.getString();
      String str3 = this.ii.getDecimalFormatSymbols().getCurrencySymbol();
      StringBuffer localStringBuffer = new StringBuffer();
      int i = str2.indexOf(str3);
      if (i != -1)
      {
        localStringBuffer.append(str2.substring(0, i));
        localStringBuffer.append(str2.substring(i + str3.length()));
      }
      str1 = localStringBuffer.toString();
    }
    return str1;
  }
  
  public void setCurrencyString(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0))
    {
      Currency localCurrency = new Currency(paramString, this.locale);
      setData(localCurrency.getCurrencyStringNoSymbolNoComma());
    }
    else
    {
      setData(null);
    }
  }
  
  public String getDisplayName()
  {
    return this.ig;
  }
  
  public void setDisplayName(String paramString)
  {
    this.ig = paramString;
  }
  
  public void setHide(boolean paramBoolean)
  {
    this.ie = paramBoolean;
  }
  
  public boolean getHide()
  {
    return this.ie;
  }
  
  public void setLimitCategory(String paramString)
  {
    this.ic = paramString;
  }
  
  public String getLimitCategory()
  {
    return this.ic;
  }
  
  public boolean isCrossCurrency()
  {
    return this.ib;
  }
  
  public void setCrossCurrency(boolean paramBoolean)
  {
    this.ib = paramBoolean;
    this.ih = true;
  }
  
  public boolean isCrossCurrencySet()
  {
    return this.ih;
  }
  
  public String getCurrencyCode()
  {
    return this.h9;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.h9 = paramString;
    this.ia = true;
  }
  
  public boolean isCurrencyCodeSet()
  {
    return this.ia;
  }
  
  public void setLimit(Limit paramLimit)
  {
    setLimitId(paramLimit.getLimitId());
    setGroupId(paramLimit.getGroupId());
    setPeriod(paramLimit.getPeriod());
    setParentId(paramLimit.getParentId());
    setData(paramLimit.getData());
    setModifiedDate(paramLimit.getModifiedDate());
    setRetrievalDate(paramLimit.getRetrievalDate());
    setEntitlement(paramLimit.getEntitlement());
    setAllowApproval(paramLimit.isAllowedApproval());
    setCurrencyCode(paramLimit.getCurrencyCode());
    setCrossCurrency(paramLimit.isCrossCurrency());
  }
  
  public void setLimit(TransactionLimit paramTransactionLimit)
  {
    setLimitId(paramTransactionLimit.getLimitId());
    setGroupId(paramTransactionLimit.getGroupId());
    setPeriod(paramTransactionLimit.getPeriod());
    setParentId(paramTransactionLimit.getParentId());
    setData(paramTransactionLimit.getData());
    setModifiedDate(paramTransactionLimit.getModifiedDate());
    setRetrievalDate(paramTransactionLimit.getRetrievalDate());
    setEntitlement(paramTransactionLimit.getEntitlement());
    setAllowApproval(paramTransactionLimit.isAllowedApproval());
    setDisplayName(paramTransactionLimit.getDisplayName());
    setHide(paramTransactionLimit.getHide());
    setCurrencyCode(paramTransactionLimit.getCurrencyCode());
    setCrossCurrency(paramTransactionLimit.isCrossCurrency());
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    int i = 0;
    if (paramString.equals("DisplayName"))
    {
      TransactionLimit localTransactionLimit = (TransactionLimit)paramObject;
      i = localCollator.compare(getDisplayName(), localTransactionLimit.getDisplayName());
    }
    return i;
  }
  
  public int getCollatorStrength()
  {
    return 2;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.business.TransactionLimit
 * JD-Core Version:    0.7.0.1
 */