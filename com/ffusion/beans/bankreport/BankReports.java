package com.ffusion.beans.bankreport;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import com.ffusion.util.beans.XMLStrings;
import java.util.Locale;

public class BankReports
  extends FilteredList
  implements XMLStrings, XMLable
{
  public BankReports() {}
  
  public BankReports(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public BankReport add()
  {
    BankReport localBankReport = new BankReport(this.locale);
    add(localBankReport);
    return localBankReport;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof BankReports)) {
      return false;
    }
    BankReports localBankReports = (BankReports)paramObject;
    if (size() != localBankReports.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localBankReports.locale;
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
    return containsAll(localBankReports);
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
    XMLHandler.appendBeginTag(localStringBuffer, "BANK_REPORTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((BankReport)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "BANK_REPORTS");
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
      if (paramString.equals("BANK_REPORT"))
      {
        BankReport localBankReport = new BankReport(BankReports.this.locale);
        BankReports.this.add(localBankReport);
        localBankReport.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bankreport.BankReports
 * JD-Core Version:    0.7.0.1
 */