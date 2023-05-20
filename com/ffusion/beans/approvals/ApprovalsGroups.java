package com.ffusion.beans.approvals;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;
import java.util.Locale;

public class ApprovalsGroups
  extends FilteredList
  implements XMLStrings, XMLable
{
  public ApprovalsGroups(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ApprovalsGroup add()
  {
    ApprovalsGroup localApprovalsGroup = new ApprovalsGroup(this.locale);
    add(localApprovalsGroup);
    return localApprovalsGroup;
  }
  
  public ApprovalsGroup getByID(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ApprovalsGroup localApprovalsGroup = (ApprovalsGroup)localIterator.next();
      if (localApprovalsGroup.getApprovalsGroupID() == paramInt)
      {
        localObject = localApprovalsGroup;
        break;
      }
    }
    return localObject;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsGroups)) {
      return false;
    }
    ApprovalsGroups localApprovalsGroups = (ApprovalsGroups)paramObject;
    if (size() != localApprovalsGroups.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localApprovalsGroups.locale;
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
    return containsAll(localApprovalsGroups);
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALSGROUPS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ApprovalsGroup)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALSGROUPS");
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
      if (paramString.equals("APPROVALSGROUP"))
      {
        ApprovalsGroup localApprovalsGroup = new ApprovalsGroup(ApprovalsGroups.this.locale);
        ApprovalsGroups.this.add(localApprovalsGroup);
        localApprovalsGroup.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsGroups
 * JD-Core Version:    0.7.0.1
 */