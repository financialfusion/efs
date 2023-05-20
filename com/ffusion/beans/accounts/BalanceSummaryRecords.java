package com.ffusion.beans.accounts;

import com.ffusion.beans.Currency;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class BalanceSummaryRecords
  extends FilteredList
{
  public static final String BALANCESUMMARYRECORDS = "BALANCESUMMARYRECORDS";
  protected String _datetype = "SHORT";
  
  public BalanceSummaryRecords() {}
  
  public BalanceSummaryRecords(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public BalanceSummaryRecord create(Account paramAccount, AccountHistory paramAccountHistory, int paramInt1, Currency paramCurrency1, int paramInt2, Currency paramCurrency2)
  {
    BalanceSummaryRecord localBalanceSummaryRecord = new BalanceSummaryRecord(this.locale, paramAccount, paramAccountHistory, paramInt1, paramCurrency1, paramInt2, paramCurrency2);
    super.add(localBalanceSummaryRecord);
    return localBalanceSummaryRecord;
  }
  
  public BalanceSummaryRecord create()
  {
    BalanceSummaryRecord localBalanceSummaryRecord = new BalanceSummaryRecord(this.locale);
    super.add(localBalanceSummaryRecord);
    return localBalanceSummaryRecord;
  }
  
  public BalanceSummaryRecord createNoAdd()
  {
    BalanceSummaryRecord localBalanceSummaryRecord = new BalanceSummaryRecord();
    return localBalanceSummaryRecord;
  }
  
  public void setDateFormat(String paramString)
  {
    this._datetype = paramString;
    BalanceSummaryRecord localBalanceSummaryRecord = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localBalanceSummaryRecord = (BalanceSummaryRecord)localIterator.next();
      localBalanceSummaryRecord.setDateFormat(this._datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this._datetype;
  }
  
  public boolean add(Object paramObject)
  {
    BalanceSummaryRecord localBalanceSummaryRecord = (BalanceSummaryRecord)paramObject;
    localBalanceSummaryRecord.setLocale(this.locale);
    return super.add(localBalanceSummaryRecord);
  }
  
  public void set(BalanceSummaryRecords paramBalanceSummaryRecords)
  {
    Iterator localIterator = paramBalanceSummaryRecords.iterator();
    BalanceSummaryRecord localBalanceSummaryRecord = null;
    while (localIterator.hasNext())
    {
      localBalanceSummaryRecord = (BalanceSummaryRecord)localIterator.next();
      add(localBalanceSummaryRecord);
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
    XMLHandler.appendBeginTag(localStringBuffer, "BALANCESUMMARYRECORDS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((BalanceSummaryRecord)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "BALANCESUMMARYRECORDS");
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
      if (paramString.equals("BALANCESUMMARYRECORD"))
      {
        BalanceSummaryRecord localBalanceSummaryRecord = BalanceSummaryRecords.this.create();
        localBalanceSummaryRecord.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.BalanceSummaryRecords
 * JD-Core Version:    0.7.0.1
 */