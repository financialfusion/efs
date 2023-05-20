package com.ffusion.beans.accounts;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;

public class AccountHistories
  extends FilteredList
{
  public static final String ACCOUNT_HISTORIES = "ACCOUNT_HISTORIES";
  protected String datetype = "SHORT";
  
  public AccountHistory create()
  {
    AccountHistory localAccountHistory = new AccountHistory();
    super.add(localAccountHistory);
    return localAccountHistory;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      AccountHistory localAccountHistory = (AccountHistory)localIterator.next();
      localAccountHistory.setDateFormat(this.datetype);
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACCOUNT_HISTORIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((AccountHistory)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ACCOUNT_HISTORIES");
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
      if (paramString.equals("ACCOUNT_HISTORY"))
      {
        AccountHistory localAccountHistory = AccountHistories.this.create();
        localAccountHistory.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.AccountHistories
 * JD-Core Version:    0.7.0.1
 */