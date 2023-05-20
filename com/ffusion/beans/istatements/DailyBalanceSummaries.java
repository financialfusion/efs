package com.ffusion.beans.istatements;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

public class DailyBalanceSummaries
  extends FilteredList
  implements Localeable, Serializable, XMLStrings
{
  protected String datetype = "SHORT";
  
  public DailyBalanceSummaries() {}
  
  public DailyBalanceSummaries(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public Object create()
  {
    DailyBalanceSummary localDailyBalanceSummary = new DailyBalanceSummary(this.locale);
    add(localDailyBalanceSummary);
    return localDailyBalanceSummary;
  }
  
  public Object createNoAdd()
  {
    return new DailyBalanceSummary(this.locale);
  }
  
  public boolean add(Object paramObject)
  {
    DailyBalanceSummary localDailyBalanceSummary = (DailyBalanceSummary)paramObject;
    localDailyBalanceSummary.setLocale(this.locale);
    return super.add(localDailyBalanceSummary);
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      DailyBalanceSummary localDailyBalanceSummary = (DailyBalanceSummary)localIterator.next();
      localDailyBalanceSummary.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "DAILY_BALANCE_SUMMARIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((DailyBalanceSummary)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "DAILY_BALANCE_SUMMARIES");
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
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("DAILY_BALANCE_SUMMARY"))
      {
        DailyBalanceSummary localDailyBalanceSummary = (DailyBalanceSummary)DailyBalanceSummaries.this.create();
        localDailyBalanceSummary.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.istatements.DailyBalanceSummaries
 * JD-Core Version:    0.7.0.1
 */