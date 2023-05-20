package com.ffusion.beans.positivepay;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Locale;

public class PPayAccounts
  extends FilteredList
  implements Serializable
{
  public static final String PPAYACCOUNTS = "PPAYACCOUNTS";
  
  public PPayAccounts(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public PPayAccount add()
  {
    PPayAccount localPPayAccount = new PPayAccount(this.locale);
    add(localPPayAccount);
    return localPPayAccount;
  }
  
  public PPayAccount create()
  {
    PPayAccount localPPayAccount = new PPayAccount(this.locale);
    return localPPayAccount;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PPayAccounts)) {
      return false;
    }
    PPayAccounts localPPayAccounts = (PPayAccounts)paramObject;
    if (size() != localPPayAccounts.size()) {
      return false;
    }
    if ((this.locale != null) && (localPPayAccounts.locale != null))
    {
      if (!this.locale.equals(localPPayAccounts.locale)) {
        return false;
      }
    }
    else
    {
      if ((this.locale != null) && (localPPayAccounts.locale == null)) {
        return false;
      }
      if ((this.locale == null) && (localPPayAccounts.locale != null)) {
        return false;
      }
    }
    return containsAll(localPPayAccounts);
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
    XMLHandler.appendBeginTag(localStringBuffer, "PPAYACCOUNTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((PPayAccount)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PPAYACCOUNTS");
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
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("PPAYACCOUNT"))
      {
        PPayAccount localPPayAccount = new PPayAccount(PPayAccounts.this.locale);
        PPayAccounts.this.add(localPPayAccount);
        localPPayAccount.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPayAccounts
 * JD-Core Version:    0.7.0.1
 */