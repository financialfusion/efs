package com.ffusion.beans.accounts;

import com.ffusion.beans.banking.Transactions;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class BalanceDetailRecords
  extends FilteredList
{
  public static final String BALANCEDETAILRECORDS = "BALANCEDETAILRECORDS";
  protected String _datetype = "SHORT";
  
  public BalanceDetailRecords() {}
  
  public BalanceDetailRecords(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public BalanceDetailRecord create(BalanceSummaryRecord paramBalanceSummaryRecord, Transactions paramTransactions1, Transactions paramTransactions2)
  {
    BalanceDetailRecord localBalanceDetailRecord = new BalanceDetailRecord(this.locale, paramBalanceSummaryRecord, paramTransactions1, paramTransactions2);
    super.add(localBalanceDetailRecord);
    return localBalanceDetailRecord;
  }
  
  public BalanceDetailRecord create()
  {
    BalanceDetailRecord localBalanceDetailRecord = new BalanceDetailRecord(this.locale);
    super.add(localBalanceDetailRecord);
    return localBalanceDetailRecord;
  }
  
  public BalanceDetailRecord createNoAdd()
  {
    BalanceDetailRecord localBalanceDetailRecord = new BalanceDetailRecord();
    return localBalanceDetailRecord;
  }
  
  public void setDateFormat(String paramString)
  {
    this._datetype = paramString;
    BalanceDetailRecord localBalanceDetailRecord = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localBalanceDetailRecord = (BalanceDetailRecord)localIterator.next();
      localBalanceDetailRecord.setDateFormat(this._datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this._datetype;
  }
  
  public boolean add(Object paramObject)
  {
    BalanceDetailRecord localBalanceDetailRecord = (BalanceDetailRecord)paramObject;
    localBalanceDetailRecord.setLocale(this.locale);
    return super.add(localBalanceDetailRecord);
  }
  
  public void set(BalanceDetailRecords paramBalanceDetailRecords)
  {
    Iterator localIterator = paramBalanceDetailRecords.iterator();
    BalanceDetailRecord localBalanceDetailRecord = null;
    while (localIterator.hasNext())
    {
      localBalanceDetailRecord = (BalanceDetailRecord)localIterator.next();
      add(localBalanceDetailRecord);
    }
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BALANCEDETAILRECORDS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((BalanceDetailRecord)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "BALANCEDETAILRECORDS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("BALANCEDETAILRECORD"))
      {
        BalanceDetailRecord localBalanceDetailRecord = BalanceDetailRecords.this.create();
        localBalanceDetailRecord.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.BalanceDetailRecords
 * JD-Core Version:    0.7.0.1
 */