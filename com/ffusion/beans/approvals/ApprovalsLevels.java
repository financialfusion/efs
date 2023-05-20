package com.ffusion.beans.approvals;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class ApprovalsLevels
  extends FilteredList
  implements XMLStrings, XMLable
{
  public ApprovalsLevels(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ApprovalsLevel add()
  {
    ApprovalsLevel localApprovalsLevel = new ApprovalsLevel(this.locale);
    add(localApprovalsLevel);
    return localApprovalsLevel;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsLevels)) {
      return false;
    }
    ApprovalsLevels localApprovalsLevels = (ApprovalsLevels)paramObject;
    if (size() != localApprovalsLevels.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localApprovalsLevels.locale;
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
    return containsAll(localApprovalsLevels);
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_LEVELS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ApprovalsLevel)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_LEVELS");
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
      if (paramString.equals("APPROVALS_LEVEL"))
      {
        ApprovalsLevel localApprovalsLevel = new ApprovalsLevel(ApprovalsLevels.this.locale);
        ApprovalsLevels.this.add(localApprovalsLevel);
        localApprovalsLevel.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsLevels
 * JD-Core Version:    0.7.0.1
 */