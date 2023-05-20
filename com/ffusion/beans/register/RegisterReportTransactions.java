package com.ffusion.beans.register;

import com.ffusion.beans.Currency;
import com.ffusion.util.Filterable;
import com.ffusion.util.Sortable;
import com.ffusion.util.beans.ExtendABean;
import java.math.BigDecimal;
import java.text.Collator;
import java.util.Iterator;
import java.util.Locale;
import java.util.StringTokenizer;

public class RegisterReportTransactions
  extends RegisterTransactions
  implements Filterable, Sortable
{
  public static final String REGISTER_TYPE = "REGISTER_TYPE";
  public static final String PAYEE_NAME = "PAYEE_NAME";
  public static final String CATEGORY_NAME = "CATEGORY_NAME";
  public static final String TYPE = "TYPE";
  public static final String TAX = "TAX";
  private String jdField_else;
  private String jdField_char;
  private String jdField_long;
  private String jdField_null;
  private String jdField_goto;
  private String jdField_case;
  
  public String getName()
  {
    return this.jdField_char;
  }
  
  public void setName(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getId()
  {
    return this.jdField_else;
  }
  
  public void setId(String paramString)
  {
    this.jdField_else = paramString;
  }
  
  public String getParentName()
  {
    return this.jdField_long;
  }
  
  public void setParentName(String paramString)
  {
    this.jdField_long = paramString;
  }
  
  public String getType()
  {
    return this.jdField_null;
  }
  
  public void setType(String paramString)
  {
    this.jdField_null = paramString;
  }
  
  public String getTax()
  {
    return this.jdField_goto;
  }
  
  public void setTax(String paramString)
  {
    this.jdField_goto = paramString;
  }
  
  public String getFullName()
  {
    if (this.jdField_long != null)
    {
      if (this.jdField_char != null) {
        return this.jdField_long + ":" + this.jdField_char;
      }
      return this.jdField_long;
    }
    if (this.jdField_char != null) {
      return this.jdField_char;
    }
    return "";
  }
  
  public String getCurrencyCode()
  {
    return this.jdField_goto;
  }
  
  public void setCurrencyCode(String paramString)
  {
    this.jdField_case = paramString;
  }
  
  public String getAmount()
  {
    return getAmountValue().toString();
  }
  
  public Currency getAmountValue()
  {
    BigDecimal localBigDecimal = new BigDecimal(0.0D);
    Locale localLocale = null;
    Iterator localIterator = iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      if ((localObject instanceof RegisterTransaction))
      {
        localBigDecimal = localBigDecimal.add(((RegisterTransaction)localObject).getAmountValue().getAmountValue());
        localLocale = ((RegisterTransaction)localObject).getLocale();
      }
    }
    if (localLocale != null)
    {
      localObject = new Currency(localBigDecimal, localLocale);
      if (this.jdField_case != null) {
        ((Currency)localObject).setCurrencyCode(this.jdField_case);
      }
      return localObject;
    }
    return null;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    RegisterReportTransactions localRegisterReportTransactions = (RegisterReportTransactions)paramObject;
    int i = 0;
    Collator localCollator = doGetCollator();
    if ((paramString.equalsIgnoreCase("CATEGORY_NAME")) && (this.jdField_char != null) && (localRegisterReportTransactions.getName() != null)) {
      i = ExtendABean.compareStrings(getFullName(), localRegisterReportTransactions.getFullName(), localCollator);
    } else if ((paramString.equalsIgnoreCase("PAYEE_NAME")) && (this.jdField_char != null) && (localRegisterReportTransactions.getName() != null)) {
      i = ExtendABean.compareStrings(this.jdField_char, localRegisterReportTransactions.getName(), localCollator);
    } else if (paramString.equalsIgnoreCase("REGISTER_TYPE")) {
      i = ExtendABean.compareStrings(this.jdField_char, localRegisterReportTransactions.getName(), localCollator);
    }
    return i;
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=<>!", true);
    if ((localStringTokenizer.countTokens() == 3) || (localStringTokenizer.countTokens() == 4))
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      String str3 = localStringTokenizer.nextToken();
      if (localStringTokenizer.countTokens() == 1)
      {
        str2 = str2 + str3;
        str3 = localStringTokenizer.nextToken();
      }
      if ((str1.equals("TYPE")) && (this.jdField_null != null)) {
        return isFilterable(this.jdField_null, str2, str3);
      }
      if ((str1.equals("TAX")) && (this.jdField_goto != null)) {
        return isFilterable(this.jdField_goto, str2, str3);
      }
    }
    return false;
  }
  
  public boolean isFilterable(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = false;
    if (paramString2.equals("=")) {
      bool = paramString1.equals(paramString3);
    } else if (paramString2.equals("==")) {
      bool = paramString1.equalsIgnoreCase(paramString3);
    } else if (paramString2.equals("!")) {
      bool = !paramString1.equals(paramString3);
    } else if (paramString2.equals("!!")) {
      bool = !paramString1.equalsIgnoreCase(paramString3);
    } else if (paramString2.equals("<")) {
      bool = paramString1.compareTo(paramString3) == -1;
    } else if (paramString2.equals("<<")) {
      bool = paramString1.compareToIgnoreCase(paramString3) == -1;
    } else if (paramString2.equals(">")) {
      bool = paramString1.compareTo(paramString3) == 1;
    } else if (paramString2.equals(">>")) {
      bool = paramString1.compareToIgnoreCase(paramString3) == 1;
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.register.RegisterReportTransactions
 * JD-Core Version:    0.7.0.1
 */