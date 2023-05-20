package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.math.BigDecimal;
import java.util.Locale;

public class BalanceSummaryRecord
  extends ExtendABean
{
  public static final String BALANCESUMMARYRECORD = "BALANCESUMMARYRECORD";
  public static final String NUM_CREDITS = "NUM_CREDITS";
  public static final String TOTAL_CREDITS = "TOTAL_CREDITS";
  public static final String NUM_DEBITS = "NUM_DEBITS";
  public static final String TOTAL_DEBITS = "TOTAL_DEBITS";
  private Account a1S = null;
  private AccountHistory a1R = null;
  private int a1Q = 0;
  private Currency a1V = null;
  private int a1T = 0;
  private Currency a1U = null;
  
  public BalanceSummaryRecord() {}
  
  protected BalanceSummaryRecord(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  protected BalanceSummaryRecord(Locale paramLocale, Account paramAccount, AccountHistory paramAccountHistory, int paramInt1, Currency paramCurrency1, int paramInt2, Currency paramCurrency2)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.a1S = paramAccount;
    this.a1R = paramAccountHistory;
    this.a1Q = paramInt1;
    this.a1V = paramCurrency1;
    this.a1T = paramInt2;
    this.a1U = paramCurrency2;
  }
  
  public Account getAccount()
  {
    return this.a1S;
  }
  
  public void setAccount(Account paramAccount)
  {
    this.a1S = paramAccount;
  }
  
  public AccountHistory getAccountHistory()
  {
    return this.a1R;
  }
  
  public void setAccountHistory(AccountHistory paramAccountHistory)
  {
    this.a1R = paramAccountHistory;
  }
  
  public int getNumCredits()
  {
    return this.a1Q;
  }
  
  public void setNumCredits(int paramInt)
  {
    this.a1Q = paramInt;
  }
  
  public Currency getTotalCredits()
  {
    if (this.a1V == null) {
      return new Currency(new BigDecimal(0.0D), this.locale);
    }
    return this.a1V;
  }
  
  public void setTotalCredits(Currency paramCurrency)
  {
    this.a1V = paramCurrency;
  }
  
  public int getNumDebits()
  {
    return this.a1T;
  }
  
  public void setNumDebits(int paramInt)
  {
    this.a1T = paramInt;
  }
  
  public Currency getTotalDebits()
  {
    if (this.a1U == null) {
      return new Currency(new BigDecimal(0.0D), this.locale);
    }
    return this.a1U;
  }
  
  public Currency getTotalDebitsAbsolute()
  {
    if (this.a1U == null) {
      return null;
    }
    return new Currency(new BigDecimal(this.a1U.getAmountValue().abs().doubleValue()), this.locale);
  }
  
  public void setTotalDebits(Currency paramCurrency)
  {
    this.a1U = paramCurrency;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("NUM_CREDITS")) {
      setNumCredits(Integer.parseInt(paramString2));
    } else if (paramString1.equals("TOTAL_CREDITS")) {
      setTotalCredits(new Currency(paramString2, this.locale));
    } else if (paramString1.equals("NUM_DEBITS")) {
      setNumDebits(Integer.parseInt(paramString2));
    } else if (paramString1.equals("TOTAL_DEBITS")) {
      setTotalDebits(new Currency(paramString2, this.locale));
    }
    return bool;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a1V != null) {
      this.a1V.setLocale(paramLocale);
    }
    if (this.a1U != null) {
      this.a1U.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.a1S != null) {
      this.a1S.setDateFormat(paramString);
    }
    if (this.a1R != null) {
      this.a1R.setDateFormat(paramString);
    }
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BALANCESUMMARYRECORD");
    if (this.a1S != null) {
      localStringBuffer.append(this.a1S.getXML());
    }
    if (this.a1R != null) {
      localStringBuffer.append(this.a1R.getXML());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_CREDITS", this.a1Q);
    if (this.a1V != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_CREDITS", this.a1V.doubleValue());
    }
    XMLHandler.appendTag(localStringBuffer, "NUM_DEBITS", this.a1T);
    if (this.a1U != null) {
      XMLHandler.appendTag(localStringBuffer, "TOTAL_DEBITS", this.a1U.doubleValue());
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BALANCESUMMARYRECORD");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends ExtendABean.InternalXMLHandler
  {
    a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("ACCOUNT"))
      {
        BalanceSummaryRecord.this.a1S = new Account();
        BalanceSummaryRecord.this.a1S.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("ACCOUNT_HISTORY"))
      {
        BalanceSummaryRecord.this.a1R = new AccountHistory();
        BalanceSummaryRecord.this.a1R.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      super.endElement(paramString);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.BalanceSummaryRecord
 * JD-Core Version:    0.7.0.1
 */