package com.ffusion.beans.banking;

import com.ffusion.util.TransactionTypeCache;
import com.ffusion.util.beans.ExtendABean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;

public class TransactionTypeInfo
  extends ExtendABean
{
  public static final String TRANSACTION_TYPE = "TRANSACTIONTYPE";
  public static final String ID = "ID";
  public static final String APPLICATIONS = "APPLICATIONS";
  public static final String APPLICATION = "APPLICATION";
  public static final String ABBR = "ABBR";
  public static final String NAME = "NAME";
  public static final String APPLICATION_ALL_STR = "All";
  public static final String APPLICATION_CONSUMER_STR = "Consumer";
  public static final String APPLICATION_CORPORATE_STR = "Corporate";
  public static final int APPLICATION_ALL = 0;
  public static final int APPLICATION_CONSUMER = 1;
  public static final int APPLICATION_CORPORATE = 2;
  private int jdField_for = -1;
  private int[] jdField_if;
  private HashMap jdField_do = new HashMap();
  private HashMap a = new HashMap();
  
  public TransactionTypeInfo() {}
  
  public TransactionTypeInfo(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public int getID()
  {
    return this.jdField_for;
  }
  
  public void setID(int paramInt)
  {
    this.jdField_for = paramInt;
  }
  
  public boolean isCorporate()
  {
    if ((this.jdField_if == null) || (this.jdField_if.length <= 0)) {
      return false;
    }
    for (int i = 0; i < this.jdField_if.length; i++) {
      if ((this.jdField_if[i] == 0) || (this.jdField_if[i] == 2)) {
        return true;
      }
    }
    return false;
  }
  
  public boolean isConsumer()
  {
    if ((this.jdField_if == null) || (this.jdField_if.length <= 0)) {
      return false;
    }
    for (int i = 0; i < this.jdField_if.length; i++) {
      if ((this.jdField_if[i] == 0) || (this.jdField_if[i] == 1)) {
        return true;
      }
    }
    return false;
  }
  
  public String getDescription(Locale paramLocale)
  {
    if (paramLocale == null) {
      return null;
    }
    String str = (String)this.jdField_do.get(paramLocale);
    if (str == null) {
      str = (String)this.jdField_do.get(TransactionTypeCache.DEFAULT_LOCALE);
    }
    return str;
  }
  
  public String getDescription()
  {
    return getDescription(this.locale);
  }
  
  public void setDescription(Locale paramLocale, String paramString)
  {
    if ((paramLocale == null) || (paramString == null)) {
      return;
    }
    this.jdField_do.put(paramLocale, paramString);
  }
  
  public String getAbbr(Locale paramLocale)
  {
    if (paramLocale == null) {
      return null;
    }
    String str = (String)this.a.get(paramLocale);
    if (str == null) {
      str = (String)this.a.get(TransactionTypeCache.DEFAULT_LOCALE);
    }
    return str;
  }
  
  public String getAbbr()
  {
    return getAbbr(this.locale);
  }
  
  public void setAbbr(Locale paramLocale, String paramString)
  {
    if ((paramLocale == null) || (paramString == null)) {
      return;
    }
    this.a.put(paramLocale, paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(super.getXML());
    return localStringBuffer.toString();
  }
  
  public void setApplications(int[] paramArrayOfInt)
  {
    this.jdField_if = paramArrayOfInt;
  }
  
  public ArrayList getLanguages()
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = this.jdField_do.keySet().iterator();
    while (localIterator.hasNext())
    {
      Locale localLocale = (Locale)localIterator.next();
      if (!localArrayList.contains(localLocale)) {
        localArrayList.add(localLocale);
      }
    }
    return localArrayList;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.TransactionTypeInfo
 * JD-Core Version:    0.7.0.1
 */