package com.ffusion.beans.util;

import com.ffusion.beans.Currency;
import com.ffusion.beans.DateTime;
import com.ffusion.util.LSLinearRegression;
import java.math.BigDecimal;
import java.util.Date;

public class CurrencyEstimate
{
  private LSLinearRegression jdField_if = new LSLinearRegression();
  private DateTime jdField_do;
  private Currency a;
  
  public void addPoint(DateTime paramDateTime, Currency paramCurrency)
  {
    if (this.jdField_do == null) {
      this.jdField_do = paramDateTime;
    }
    if (this.a == null) {
      this.a = paramCurrency;
    }
    this.jdField_if.addPoint(a(paramDateTime), paramCurrency.doubleValue());
  }
  
  public void calculateRegression()
  {
    this.jdField_if.calculateRegression();
  }
  
  public Currency getEstimated(DateTime paramDateTime)
  {
    double d = this.jdField_if.getEstimated(a(paramDateTime));
    Currency localCurrency = new Currency(new BigDecimal(d), this.a.getCurrencyCode(), this.a.getLocale());
    localCurrency.setFormat(this.a.getFormat());
    return localCurrency;
  }
  
  private static double a(DateTime paramDateTime)
  {
    return paramDateTime.getTime().getTime();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.CurrencyEstimate
 * JD-Core Version:    0.7.0.1
 */