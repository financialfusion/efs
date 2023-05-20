package com.ffusion.beans.approvals;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class ApprovalsItems
  extends FilteredList
  implements XMLStrings, XMLable
{
  public ApprovalsItems(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ApprovalsItem add()
  {
    ApprovalsItem localApprovalsItem = new ApprovalsItem(this.locale);
    add(localApprovalsItem);
    return localApprovalsItem;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsItems)) {
      return false;
    }
    ApprovalsItems localApprovalsItems = (ApprovalsItems)paramObject;
    if (size() != localApprovalsItems.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localApprovalsItems.locale;
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
    return containsAll(localApprovalsItems);
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_ITEMS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ApprovalsItem)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_ITEMS");
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
      if (paramString.equals("APPROVALS_ITEM"))
      {
        ApprovalsItem localApprovalsItem = new ApprovalsItem(ApprovalsItems.this.locale);
        ApprovalsItems.this.add(localApprovalsItem);
        localApprovalsItem.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsItems
 * JD-Core Version:    0.7.0.1
 */