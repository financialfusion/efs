package com.ffusion.beans.lockbox;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class LockboxCreditItems
  extends FilteredList
  implements Localeable, XMLable
{
  public static final String STR_OUTER_TAG = "LOCKBOX_CREDIT_ITEMS";
  
  public LockboxCreditItems() {}
  
  public LockboxCreditItems(Locale paramLocale)
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOCKBOX_CREDIT_ITEMS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((LockboxCreditItem)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "LOCKBOX_CREDIT_ITEMS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString) {}
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  public LockboxCreditItem add()
  {
    LockboxCreditItem localLockboxCreditItem = new LockboxCreditItem(this.locale);
    super.add(localLockboxCreditItem);
    return localLockboxCreditItem;
  }
  
  public LockboxCreditItem create()
  {
    return add();
  }
  
  public LockboxCreditItem createNoAdd()
  {
    LockboxCreditItem localLockboxCreditItem = new LockboxCreditItem(this.locale);
    return localLockboxCreditItem;
  }
  
  public boolean equals(Object paramObject)
  {
    if ((paramObject == null) || (!(paramObject instanceof LockboxCreditItems))) {
      return false;
    }
    LockboxCreditItems localLockboxCreditItems = (LockboxCreditItems)paramObject;
    if (super.size() != localLockboxCreditItems.size()) {
      return false;
    }
    if (this.locale != localLockboxCreditItems.locale)
    {
      if ((this.locale == null) || (localLockboxCreditItems.locale == null)) {
        return false;
      }
      if (!this.locale.equals(localLockboxCreditItems.locale)) {
        return false;
      }
    }
    return super.containsAll(localLockboxCreditItems);
  }
  
  private class a
    extends XMLHandler
  {
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("LOCKBOX_CREDIT_ITEM"))
      {
        LockboxCreditItem localLockboxCreditItem = new LockboxCreditItem(LockboxCreditItems.this.locale);
        localLockboxCreditItem.continueXMLParsing(getHandler());
        LockboxCreditItems.this.add(localLockboxCreditItem);
      }
    }
    
    a(LockboxCreditItems.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.lockbox.LockboxCreditItems
 * JD-Core Version:    0.7.0.1
 */