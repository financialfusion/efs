package com.ffusion.beans.accounts;

import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.banking.Transactions;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Locale;

public class BalanceDetailRecord
  extends ExtendABean
{
  public static final String BALANCEDETAILRECORD = "BALANCEDETAILRECORD";
  public static final String CREDITTRANSACTIONS = "CREDITTRANSACTIONS";
  public static final String DEBITTRANSACTIONS = "DEBITTRANSACTIONS";
  private BalanceSummaryRecord a1p = null;
  private Transactions a1r = null;
  private Transactions a1q = null;
  
  public BalanceDetailRecord() {}
  
  protected BalanceDetailRecord(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  protected BalanceDetailRecord(Locale paramLocale, BalanceSummaryRecord paramBalanceSummaryRecord, Transactions paramTransactions1, Transactions paramTransactions2)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.a1p = paramBalanceSummaryRecord;
    this.a1r = paramTransactions1;
    this.a1q = paramTransactions2;
  }
  
  public BalanceSummaryRecord getBalanceSummaryRecord()
  {
    return this.a1p;
  }
  
  public void setBalanceSummaryRecord(BalanceSummaryRecord paramBalanceSummaryRecord)
  {
    this.a1p = paramBalanceSummaryRecord;
  }
  
  public Transactions getCreditTransactions()
  {
    return this.a1r;
  }
  
  public void setCreditTransactions(Transactions paramTransactions)
  {
    this.a1r = paramTransactions;
  }
  
  public Transactions getDebitTransactions()
  {
    return this.a1q;
  }
  
  public void setDebitTransactions(Transactions paramTransactions)
  {
    this.a1q = paramTransactions;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.a1p != null) {
      this.a1p.setLocale(paramLocale);
    }
    if (this.a1r != null) {
      this.a1r.setLocale(paramLocale);
    }
    if (this.a1q != null) {
      this.a1q.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.a1p != null) {
      this.a1p.setDateFormat(paramString);
    }
    if (this.a1r != null) {
      this.a1r.setDateFormat(paramString);
    }
    if (this.a1q != null) {
      this.a1q.setDateFormat(paramString);
    }
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BALANCEDETAILRECORD");
    if (this.a1p != null) {
      localStringBuffer.append(this.a1p.getXML());
    }
    if (this.a1r != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "CREDITTRANSACTIONS");
      localStringBuffer.append(this.a1r.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "CREDITTRANSACTIONS");
    }
    if (this.a1q != null)
    {
      XMLHandler.appendBeginTag(localStringBuffer, "DEBITTRANSACTIONS");
      localStringBuffer.append(this.a1q.getXML());
      XMLHandler.appendEndTag(localStringBuffer, "DEBITTRANSACTIONS");
    }
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BALANCEDETAILRECORD");
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
      if (paramString.equals("BALANCESUMMARYRECORD"))
      {
        BalanceDetailRecord.this.a1p = new BalanceSummaryRecord();
        BalanceDetailRecord.this.a1p.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("CREDITTRANSACTIONS"))
      {
        BalanceDetailRecord.this.a1r = new Transactions();
        BalanceDetailRecord.this.a1r.continueXMLParsing(getHandler());
      }
      else if (paramString.equals("DEBITTRANSACTIONS"))
      {
        BalanceDetailRecord.this.a1q = new Transactions();
        BalanceDetailRecord.this.a1q.continueXMLParsing(getHandler());
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
 * Qualified Name:     com.ffusion.beans.accounts.BalanceDetailRecord
 * JD-Core Version:    0.7.0.1
 */