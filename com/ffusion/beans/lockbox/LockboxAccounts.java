package com.ffusion.beans.lockbox;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class LockboxAccounts
  extends FilteredList
  implements Localeable, XMLable
{
  public static final String STR_OUTER_TAG = "LOCKBOX_ACCOUNTS";
  
  public LockboxAccounts() {}
  
  public LockboxAccounts(Locale paramLocale)
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOCKBOX_ACCOUNTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((LockboxAccount)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "LOCKBOX_ACCOUNTS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString) {}
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  public LockboxAccount add()
  {
    LockboxAccount localLockboxAccount = new LockboxAccount(this.locale);
    super.add(localLockboxAccount);
    return localLockboxAccount;
  }
  
  public LockboxAccount create()
  {
    return new LockboxAccount(this.locale);
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LockboxAccounts))) {
      return false;
    }
    LockboxAccounts localLockboxAccounts = (LockboxAccounts)paramObject;
    if (super.size() != localLockboxAccounts.size()) {
      return false;
    }
    if (this.locale != localLockboxAccounts.locale)
    {
      if ((this.locale == null) || (localLockboxAccounts.locale == null)) {
        return false;
      }
      if (!this.locale.equals(localLockboxAccounts.locale)) {
        return false;
      }
    }
    return super.containsAll(localLockboxAccounts);
  }
  
  private class a
    extends XMLHandler
  {
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("LOCKBOX_ACCT"))
      {
        LockboxAccount localLockboxAccount = new LockboxAccount(LockboxAccounts.this.locale);
        localLockboxAccount.continueXMLParsing(getHandler());
        LockboxAccounts.this.add(localLockboxAccount);
      }
    }
    
    a(LockboxAccounts.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.lockbox.LockboxAccounts
 * JD-Core Version:    0.7.0.1
 */