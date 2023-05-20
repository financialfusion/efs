package com.ffusion.beans.disbursement;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;
import java.util.Locale;

public class DisbursementSummaries
  extends FilteredList
  implements Localeable, XMLable
{
  public static final String DISBURSEMENTSUMMARIES = "DISBURSEMENTSUMMARIES";
  protected String dateformat;
  
  public DisbursementSummaries() {}
  
  public DisbursementSummaries(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public DisbursementSummary create()
  {
    DisbursementSummary localDisbursementSummary = new DisbursementSummary();
    add(localDisbursementSummary);
    return localDisbursementSummary;
  }
  
  public DisbursementSummary createNoAdd()
  {
    DisbursementSummary localDisbursementSummary = new DisbursementSummary();
    return localDisbursementSummary;
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateformat = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      DisbursementSummary localDisbursementSummary = (DisbursementSummary)localIterator.next();
      localDisbursementSummary.setDateFormat(paramString);
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
    XMLHandler.appendBeginTag(localStringBuffer, "DISBURSEMENTSUMMARIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((DisbursementSummary)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "DISBURSEMENTSUMMARIES");
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
  
  public DisbursementSummary getByAccountID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      DisbursementSummary localDisbursementSummary = (DisbursementSummary)localIterator.next();
      DisbursementAccount localDisbursementAccount = localDisbursementSummary.getAccount();
      if ((localDisbursementAccount != null) && (localDisbursementAccount.getAccountID().equalsIgnoreCase(paramString)))
      {
        localObject = localDisbursementSummary;
        break;
      }
    }
    return localObject;
  }
  
  public DisbursementSummary getByAccountNumber(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      DisbursementSummary localDisbursementSummary = (DisbursementSummary)localIterator.next();
      DisbursementAccount localDisbursementAccount = localDisbursementSummary.getAccount();
      if ((localDisbursementAccount != null) && (localDisbursementAccount.getAccountNumber().equalsIgnoreCase(paramString)))
      {
        localObject = localDisbursementSummary;
        break;
      }
    }
    return localObject;
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
      if (paramString.equals("DISBURSEMENTSUMMARY"))
      {
        DisbursementSummary localDisbursementSummary = DisbursementSummaries.this.create();
        localDisbursementSummary.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.disbursement.DisbursementSummaries
 * JD-Core Version:    0.7.0.1
 */