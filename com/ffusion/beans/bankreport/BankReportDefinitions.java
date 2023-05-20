package com.ffusion.beans.bankreport;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.XMLStrings;
import java.util.Locale;

public class BankReportDefinitions
  extends FilteredList
  implements XMLStrings, XMLable
{
  public BankReportDefinitions() {}
  
  public BankReportDefinitions(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public BankReportDefinition add()
  {
    BankReportDefinition localBankReportDefinition = new BankReportDefinition(this.locale);
    add(localBankReportDefinition);
    return localBankReportDefinition;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof BankReportDefinitions)) {
      return false;
    }
    BankReportDefinitions localBankReportDefinitions = (BankReportDefinitions)paramObject;
    if (size() != localBankReportDefinitions.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localBankReportDefinitions.locale;
    if (localLocale1 != null)
    {
      if (localLocale2 != null)
      {
        if (!localLocale1.equals(localLocale2)) {
          return false;
        }
      }
      else {
        return false;
      }
    }
    else if (localLocale2 != null) {
      return false;
    }
    return containsAll(localBankReportDefinitions);
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
    XMLHandler.appendBeginTag(localStringBuffer, "BANK_REPORT_DEFINITIONS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((BankReportDefinition)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "BANK_REPORT_DEFINITIONS");
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
      if (paramString.equals("BANK_REPORT_DEFINITION"))
      {
        BankReportDefinition localBankReportDefinition = new BankReportDefinition(BankReportDefinitions.this.locale);
        BankReportDefinitions.this.add(localBankReportDefinition);
        localBankReportDefinition.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bankreport.BankReportDefinitions
 * JD-Core Version:    0.7.0.1
 */