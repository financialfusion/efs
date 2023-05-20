package com.ffusion.beans.lockbox;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class LockboxTransactions
  extends FilteredList
  implements Localeable, XMLable
{
  public static final String STR_OUTER_TAG = "LOCKBOX_TRANSACTIONS";
  
  public LockboxTransactions() {}
  
  public LockboxTransactions(Locale paramLocale)
  {
    super(paramLocale);
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOCKBOX_TRANSACTIONS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((LockboxTransaction)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "LOCKBOX_TRANSACTIONS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString) {}
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  public LockboxTransaction add()
  {
    LockboxTransaction localLockboxTransaction = new LockboxTransaction(this.locale);
    super.add(localLockboxTransaction);
    return localLockboxTransaction;
  }
  
  public LockboxTransaction create()
  {
    return add();
  }
  
  public LockboxTransaction createNoAdd()
  {
    LockboxTransaction localLockboxTransaction = new LockboxTransaction(this.locale);
    return localLockboxTransaction;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LockboxTransactions))) {
      return false;
    }
    LockboxTransactions localLockboxTransactions = (LockboxTransactions)paramObject;
    if (super.size() != localLockboxTransactions.size()) {
      return false;
    }
    if (this.locale != localLockboxTransactions.locale)
    {
      if ((this.locale == null) || (localLockboxTransactions.locale == null)) {
        return false;
      }
      if (!this.locale.equals(localLockboxTransactions.locale)) {
        return false;
      }
    }
    return super.containsAll(localLockboxTransactions);
  }
  
  private class a
    extends XMLHandler
  {
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("LOCKBOX_TRANSACTION"))
      {
        LockboxTransaction localLockboxTransaction = new LockboxTransaction(LockboxTransactions.this.locale);
        localLockboxTransaction.continueXMLParsing(getHandler());
        LockboxTransactions.this.add(localLockboxTransaction);
      }
    }
    
    a(LockboxTransactions.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.lockbox.LockboxTransactions
 * JD-Core Version:    0.7.0.1
 */