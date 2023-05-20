package com.ffusion.beans.billpresentment;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class BillSummaries
  extends FilteredList
{
  public static final String BILLSUMMARIES = "BILLSUMMARIES";
  private String II = "SHORT";
  
  protected BillSummaries() {}
  
  public BillSummaries(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public BillSummary add()
  {
    BillSummary localBillSummary = new BillSummary(null);
    add(localBillSummary);
    return localBillSummary;
  }
  
  public static BillSummary create()
  {
    BillSummary localBillSummary = new BillSummary(null);
    return localBillSummary;
  }
  
  public BillSummary getByBillSummaryID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BillSummary localBillSummary = (BillSummary)localIterator.next();
      if (localBillSummary.getBillSummaryID().equals(paramString))
      {
        localObject = localBillSummary;
        break;
      }
    }
    return localObject;
  }
  
  public void setDateFormat(String paramString)
  {
    this.II = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BillSummary localBillSummary = (BillSummary)localIterator.next();
      localBillSummary.setDateFormat(this.II);
    }
  }
  
  public String getDateFormat()
  {
    return this.II;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString, EBillAccounts paramEBillAccounts, Consumers paramConsumers)
  {
    setXML(paramString, paramEBillAccounts, paramConsumers);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BILLSUMMARIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((BillSummary)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "BILLSUMMARIES");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString, EBillAccounts paramEBillAccounts, Consumers paramConsumers)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(paramEBillAccounts, paramConsumers), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, EBillAccounts paramEBillAccounts, Consumers paramConsumers)
  {
    paramXMLHandler.continueWith(new a(paramEBillAccounts, paramConsumers));
  }
  
  class a
    extends XMLHandler
  {
    EBillAccounts jdField_new;
    Consumers jdField_int;
    
    public a(EBillAccounts paramEBillAccounts, Consumers paramConsumers)
    {
      this.jdField_new = paramEBillAccounts;
      this.jdField_int = paramConsumers;
    }
    
    public void startElement(String paramString)
    {
      if (paramString.equals("BILLSUMMARY"))
      {
        BillSummary localBillSummary = new BillSummary();
        BillSummaries.this.add(localBillSummary);
        localBillSummary.continueXMLParsing(getHandler(), this.jdField_new, this.jdField_int);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.BillSummaries
 * JD-Core Version:    0.7.0.1
 */