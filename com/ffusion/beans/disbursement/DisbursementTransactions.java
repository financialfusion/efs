package com.ffusion.beans.disbursement;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;
import java.util.Locale;

public class DisbursementTransactions
  extends FilteredList
  implements Localeable, XMLable
{
  public static final String DISBURSEMENTTRANSACTIONS = "DISBURSEMENTTRANSACTIONS";
  protected String dateformat;
  
  public DisbursementTransactions() {}
  
  public DisbursementTransactions(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public DisbursementTransaction create()
  {
    DisbursementTransaction localDisbursementTransaction = new DisbursementTransaction();
    add(localDisbursementTransaction);
    return localDisbursementTransaction;
  }
  
  public DisbursementTransaction createNoAdd()
  {
    DisbursementTransaction localDisbursementTransaction = new DisbursementTransaction();
    return localDisbursementTransaction;
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateformat = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      DisbursementTransaction localDisbursementTransaction = (DisbursementTransaction)localIterator.next();
      localDisbursementTransaction.setDateFormat(paramString);
    }
  }
  
  public String getDateFormat()
  {
    return this.dateformat;
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
    XMLHandler.appendBeginTag(localStringBuffer, "DISBURSEMENTTRANSACTIONS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((DisbursementTransaction)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "DISBURSEMENTTRANSACTIONS");
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
      if (paramString.equals("DISBURSEMENTTRANSACTION"))
      {
        DisbursementTransaction localDisbursementTransaction = DisbursementTransactions.this.create();
        localDisbursementTransaction.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.disbursement.DisbursementTransactions
 * JD-Core Version:    0.7.0.1
 */