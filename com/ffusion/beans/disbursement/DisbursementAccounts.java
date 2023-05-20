package com.ffusion.beans.disbursement;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class DisbursementAccounts
  extends FilteredList
  implements Localeable, XMLable
{
  public static final String DISBURSEMENTACCOUNTS = "DISBURSEMENTACCOUNTS";
  
  public DisbursementAccounts() {}
  
  public DisbursementAccounts(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public DisbursementAccount create()
  {
    DisbursementAccount localDisbursementAccount = new DisbursementAccount();
    add(localDisbursementAccount);
    return localDisbursementAccount;
  }
  
  public DisbursementAccount createNoAdd()
  {
    DisbursementAccount localDisbursementAccount = new DisbursementAccount();
    return localDisbursementAccount;
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
    XMLHandler.appendBeginTag(localStringBuffer, "DISBURSEMENTACCOUNTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((DisbursementAccount)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "DISBURSEMENTACCOUNTS");
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
      if (paramString.equals("DISBURSEMENTACCOUNT"))
      {
        DisbursementAccount localDisbursementAccount = DisbursementAccounts.this.create();
        localDisbursementAccount.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.disbursement.DisbursementAccounts
 * JD-Core Version:    0.7.0.1
 */