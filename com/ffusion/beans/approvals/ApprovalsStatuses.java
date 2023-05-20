package com.ffusion.beans.approvals;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class ApprovalsStatuses
  extends FilteredList
  implements XMLStrings, XMLable
{
  public ApprovalsStatuses(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ApprovalsStatus add()
  {
    ApprovalsStatus localApprovalsStatus = new ApprovalsStatus(this.locale);
    add(localApprovalsStatus);
    return localApprovalsStatus;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsStatuses)) {
      return false;
    }
    ApprovalsStatuses localApprovalsStatuses = (ApprovalsStatuses)paramObject;
    if (size() != localApprovalsStatuses.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localApprovalsStatuses.locale;
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
    return containsAll(localApprovalsStatuses);
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_STATUSES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ApprovalsStatus)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_STATUSES");
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
      if (paramString.equals("APPROVALS_STATUS"))
      {
        ApprovalsStatus localApprovalsStatus = new ApprovalsStatus(ApprovalsStatuses.this.locale);
        ApprovalsStatuses.this.add(localApprovalsStatus);
        localApprovalsStatus.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsStatuses
 * JD-Core Version:    0.7.0.1
 */