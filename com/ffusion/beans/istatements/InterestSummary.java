package com.ffusion.beans.istatements;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.XMLStrings;
import com.ffusion.util.XMLHandler;
import java.util.Locale;

public class InterestSummary
  extends ExtendABean
  implements XMLStrings
{
  protected double apy;
  protected Currency basedAvgColBalAmount;
  protected Currency interestEarnedAmount;
  
  public InterestSummary() {}
  
  public InterestSummary(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.basedAvgColBalAmount != null) {
      this.basedAvgColBalAmount.setLocale(paramLocale);
    }
    if (this.interestEarnedAmount != null) {
      this.interestEarnedAmount.setLocale(paramLocale);
    }
  }
  
  public double getAPYValue()
  {
    return this.apy;
  }
  
  public String getAPYIntRate()
  {
    return String.valueOf(this.apy);
  }
  
  public void setAPYIntRate(double paramDouble)
  {
    this.apy = paramDouble;
  }
  
  public Currency getBasedAvgColBalValue()
  {
    return this.basedAvgColBalAmount;
  }
  
  public String getBasedAvgColBal()
  {
    String str = "";
    if (this.basedAvgColBalAmount != null) {
      str = this.basedAvgColBalAmount.toString();
    }
    return str;
  }
  
  public void setBasedAvgColBal(Currency paramCurrency)
  {
    this.basedAvgColBalAmount = paramCurrency;
  }
  
  public void setBasedAvgColBal(String paramString)
  {
    if (this.basedAvgColBalAmount == null) {
      this.basedAvgColBalAmount = new Currency(paramString, this.locale);
    } else {
      this.basedAvgColBalAmount.fromString(paramString);
    }
  }
  
  public Currency getInterestEarnedValue()
  {
    return this.interestEarnedAmount;
  }
  
  public String getInterestEarned()
  {
    String str = "";
    if (this.interestEarnedAmount != null) {
      str = this.interestEarnedAmount.toString();
    }
    return str;
  }
  
  public void setInterestEarned(String paramString)
  {
    if (this.interestEarnedAmount == null) {
      this.interestEarnedAmount = new Currency(paramString, this.locale);
    } else {
      this.interestEarnedAmount.fromString(paramString);
    }
  }
  
  public void setInterestEarned(Currency paramCurrency)
  {
    this.interestEarnedAmount = paramCurrency;
  }
  
  public void set(InterestSummary paramInterestSummary)
  {
    if (paramInterestSummary != null)
    {
      setAPYIntRate(paramInterestSummary.getAPYValue());
      if (paramInterestSummary.getBasedAvgColBalValue() != null) {
        setBasedAvgColBal((Currency)paramInterestSummary.getBasedAvgColBalValue().clone());
      }
      if (paramInterestSummary.getInterestEarnedValue() != null) {
        setInterestEarned((Currency)paramInterestSummary.getInterestEarnedValue().clone());
      }
      super.set(paramInterestSummary);
    }
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("APY")) {
      this.apy = Double.valueOf(paramString2).doubleValue();
    } else if (paramString1.equals("BASED_AVG_COLL_BAL")) {
      setBasedAvgColBal(paramString2);
    } else if (paramString1.equals("INTEREST_EARNED")) {
      setInterestEarned(paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "INTEREST_SUMMARY");
    XMLHandler.appendTag(localStringBuffer, "APY", this.apy);
    if (this.basedAvgColBalAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "BASED_AVG_COLL_BAL", this.basedAvgColBalAmount.doubleValue());
    }
    if (this.interestEarnedAmount != null) {
      XMLHandler.appendTag(localStringBuffer, "INTEREST_EARNED", this.interestEarnedAmount.doubleValue());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "INTEREST_SUMMARY");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.istatements.InterestSummary
 * JD-Core Version:    0.7.0.1
 */