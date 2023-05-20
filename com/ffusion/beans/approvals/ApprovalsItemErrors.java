package com.ffusion.beans.approvals;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class ApprovalsItemErrors
  extends FilteredList
  implements XMLStrings, XMLable
{
  public ApprovalsItemErrors(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ApprovalsItemError add()
  {
    ApprovalsItemError localApprovalsItemError = new ApprovalsItemError(this.locale);
    add(localApprovalsItemError);
    return localApprovalsItemError;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsItemErrors)) {
      return false;
    }
    ApprovalsItemErrors localApprovalsItemErrors = (ApprovalsItemErrors)paramObject;
    if (size() != localApprovalsItemErrors.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localApprovalsItemErrors.locale;
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
    return containsAll(localApprovalsItemErrors);
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_ITEM_ERRORS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ApprovalsItemError)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_ITEM_ERRORS");
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
      if (paramString.equals("APPROVALS_ITEM_ERROR"))
      {
        ApprovalsItemError localApprovalsItemError = new ApprovalsItemError(ApprovalsItemErrors.this.locale);
        ApprovalsItemErrors.this.add(localApprovalsItemError);
        localApprovalsItemError.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsItemErrors
 * JD-Core Version:    0.7.0.1
 */