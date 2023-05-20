package com.ffusion.beans.approvals;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Serializable;
import java.util.Locale;

public class ApprovalsItemCounts
  extends FilteredList
  implements Serializable, XMLStrings, XMLable
{
  public ApprovalsItemCounts(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ApprovalsItemCount add()
  {
    ApprovalsItemCount localApprovalsItemCount = new ApprovalsItemCount(this.locale);
    add(localApprovalsItemCount);
    return localApprovalsItemCount;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsItemCounts)) {
      return false;
    }
    ApprovalsItemCounts localApprovalsItemCounts = (ApprovalsItemCounts)paramObject;
    if (size() != localApprovalsItemCounts.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localApprovalsItemCounts.locale;
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
    return containsAll(localApprovalsItemCounts);
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_ITEM_COUNTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ApprovalsItemCount)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_ITEM_COUNTS");
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
      if (paramString.equals("APPROVALS_ITEM_COUNT"))
      {
        ApprovalsItemCount localApprovalsItemCount = new ApprovalsItemCount(ApprovalsItemCounts.this.locale);
        ApprovalsItemCounts.this.add(localApprovalsItemCount);
        localApprovalsItemCount.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsItemCounts
 * JD-Core Version:    0.7.0.1
 */