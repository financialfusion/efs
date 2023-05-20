package com.ffusion.beans.disbursement;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;
import java.util.Locale;

public class DisbursementPresentmentSummaries
  extends FilteredList
  implements Localeable, XMLable
{
  public static final String DISBURSEMENTPRESENTMENTSUMMARIES = "DISBURSEMENTPRESENTMENTSUMMARIES";
  protected String dateformat;
  
  public DisbursementPresentmentSummaries() {}
  
  public DisbursementPresentmentSummaries(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public DisbursementPresentmentSummary create()
  {
    DisbursementPresentmentSummary localDisbursementPresentmentSummary = new DisbursementPresentmentSummary();
    add(localDisbursementPresentmentSummary);
    return localDisbursementPresentmentSummary;
  }
  
  public DisbursementPresentmentSummary createNoAdd()
  {
    DisbursementPresentmentSummary localDisbursementPresentmentSummary = new DisbursementPresentmentSummary();
    return localDisbursementPresentmentSummary;
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateformat = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      DisbursementPresentmentSummary localDisbursementPresentmentSummary = (DisbursementPresentmentSummary)localIterator.next();
      localDisbursementPresentmentSummary.setDateFormat(paramString);
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
    XMLHandler.appendBeginTag(localStringBuffer, "DISBURSEMENTPRESENTMENTSUMMARIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((DisbursementPresentmentSummary)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "DISBURSEMENTPRESENTMENTSUMMARIES");
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
      if (paramString.equals("DISBURSEMENTPRESENTMENTSUMMARY"))
      {
        DisbursementPresentmentSummary localDisbursementPresentmentSummary = DisbursementPresentmentSummaries.this.create();
        localDisbursementPresentmentSummary.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.disbursement.DisbursementPresentmentSummaries
 * JD-Core Version:    0.7.0.1
 */