package com.ffusion.beans.register;

import com.ffusion.beans.Currency;
import com.ffusion.util.FilteredList;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class RegisterReport
  extends FilteredList
{
  public static final String REGISTER_REPORT = "REGISTER_REPORT";
  private String jdField_char;
  private String jdField_byte;
  private ArrayList jdField_case;
  
  public String getAccountId()
  {
    return this.jdField_char;
  }
  
  public void setAccountId(String paramString)
  {
    this.jdField_char = paramString;
  }
  
  public String getAccountName()
  {
    return this.jdField_byte;
  }
  
  public void setAccountName(String paramString)
  {
    this.jdField_byte = paramString;
  }
  
  public void addRegisterTransaction(RegisterTransaction paramRegisterTransaction)
  {
    if (paramRegisterTransaction == null) {
      return;
    }
    if (this.jdField_case == null) {
      this.jdField_case = new ArrayList();
    }
    this.jdField_case.add(paramRegisterTransaction);
  }
  
  public void addRegisterTransactions(RegisterTransactions paramRegisterTransactions)
  {
    if (paramRegisterTransactions == null) {
      return;
    }
    if (this.jdField_case == null) {
      this.jdField_case = new ArrayList();
    }
    this.jdField_case.addAll(paramRegisterTransactions);
  }
  
  public RegisterReportTransactions getById(String paramString)
  {
    if (paramString == null) {
      return null;
    }
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterReportTransactions localRegisterReportTransactions = (RegisterReportTransactions)localIterator.next();
      if ((localRegisterReportTransactions != null) && (paramString.equals(localRegisterReportTransactions.getId())))
      {
        localObject = localRegisterReportTransactions;
        break;
      }
    }
    return localObject;
  }
  
  public String getAmount()
  {
    return getAmountValue().toString();
  }
  
  public Currency getAmountValue()
  {
    double d = 0.0D;
    Locale localLocale = null;
    Iterator localIterator = iterator();
    Object localObject;
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      if ((localObject instanceof RegisterTransaction))
      {
        d += ((RegisterReportTransactions)localObject).getAmountValue().doubleValue();
        localLocale = ((RegisterTransaction)localObject).getLocale();
      }
    }
    if (localLocale != null)
    {
      localObject = new BigDecimal(d);
      Currency localCurrency = new Currency((BigDecimal)localObject, localLocale);
      return localCurrency;
    }
    return null;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.register.RegisterReport
 * JD-Core Version:    0.7.0.1
 */