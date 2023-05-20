package com.ffusion.beans.banking;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Locale;

public class TransactionTypeInfos
  extends FilteredList
{
  public static final String TRANSACTION_TYPE_INFOS = "TransactionTypeInfos";
  
  public TransactionTypeInfos() {}
  
  public TransactionTypeInfos(Locale paramLocale)
  {
    this.locale = paramLocale;
  }
  
  public TransactionTypeInfo create()
  {
    return new TransactionTypeInfo(this.locale);
  }
  
  public TransactionTypeInfo add()
  {
    TransactionTypeInfo localTransactionTypeInfo = create();
    super.add(localTransactionTypeInfo);
    return localTransactionTypeInfo;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "TransactionTypeInfos");
    for (int i = 0; i < super.size(); i++)
    {
      TransactionTypeInfo localTransactionTypeInfo = (TransactionTypeInfo)super.get(i);
      localStringBuffer.append(localTransactionTypeInfo.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "TransactionTypeInfos");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  private class a
    extends XMLHandler
  {
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString == null) {
        return;
      }
      if (paramString.equals("TRANSACTIONTYPE"))
      {
        TransactionTypeInfo localTransactionTypeInfo = TransactionTypeInfos.this.add();
        localTransactionTypeInfo.continueXMLParsing(getHandler());
      }
    }
    
    a(TransactionTypeInfos.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.banking.TransactionTypeInfos
 * JD-Core Version:    0.7.0.1
 */