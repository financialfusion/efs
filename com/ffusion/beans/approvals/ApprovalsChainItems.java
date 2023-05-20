package com.ffusion.beans.approvals;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class ApprovalsChainItems
  extends FilteredList
  implements XMLStrings, XMLable
{
  public ApprovalsChainItems(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ApprovalsChainItem add()
  {
    ApprovalsChainItem localApprovalsChainItem = new ApprovalsChainItem(this.locale);
    add(localApprovalsChainItem);
    return localApprovalsChainItem;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsChainItems)) {
      return false;
    }
    ApprovalsChainItems localApprovalsChainItems = (ApprovalsChainItems)paramObject;
    if (size() != localApprovalsChainItems.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localApprovalsChainItems.locale;
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
    return containsAll(localApprovalsChainItems);
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_CHAIN_ITEMS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ApprovalsChainItem)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_CHAIN_ITEMS");
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
      if (paramString.equals("APPROVALS_CHAIN_ITEM"))
      {
        ApprovalsChainItem localApprovalsChainItem = new ApprovalsChainItem(ApprovalsChainItems.this.locale);
        ApprovalsChainItems.this.add(localApprovalsChainItem);
        localApprovalsChainItem.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsChainItems
 * JD-Core Version:    0.7.0.1
 */