package com.ffusion.beans.accounts;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;

public class ExtendedAccountSummaries
  extends FilteredList
{
  public static final String EXTENDED_ACCOUNT_SUMMARIES = "EXTENDED_ACCOUNT_SUMMARIES";
  protected String datetype = "SHORT";
  
  public ExtendedAccountSummary create()
  {
    ExtendedAccountSummary localExtendedAccountSummary = new ExtendedAccountSummary();
    localExtendedAccountSummary.setLocale(this.locale);
    super.add(localExtendedAccountSummary);
    return localExtendedAccountSummary;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ExtendedAccountSummary localExtendedAccountSummary = (ExtendedAccountSummary)localIterator.next();
      localExtendedAccountSummary.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
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
    XMLHandler.appendBeginTag(localStringBuffer, "EXTENDED_ACCOUNT_SUMMARIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ExtendedAccountSummary)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "EXTENDED_ACCOUNT_SUMMARIES");
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
      if (paramString.equals("EXTENDED_ACCOUNT_SUMMARY"))
      {
        ExtendedAccountSummary localExtendedAccountSummary = ExtendedAccountSummaries.this.create();
        localExtendedAccountSummary.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.ExtendedAccountSummaries
 * JD-Core Version:    0.7.0.1
 */