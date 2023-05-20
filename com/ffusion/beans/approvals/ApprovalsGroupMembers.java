package com.ffusion.beans.approvals;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class ApprovalsGroupMembers
  extends FilteredList
  implements XMLStrings, XMLable
{
  public ApprovalsGroupMembers(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ApprovalsGroupMember add()
  {
    ApprovalsGroupMember localApprovalsGroupMember = new ApprovalsGroupMember(this.locale);
    add(localApprovalsGroupMember);
    return localApprovalsGroupMember;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsGroupMembers)) {
      return false;
    }
    ApprovalsGroupMembers localApprovalsGroupMembers = (ApprovalsGroupMembers)paramObject;
    if (size() != localApprovalsGroupMembers.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localApprovalsGroupMembers.locale;
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
    return containsAll(localApprovalsGroupMembers);
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALSGROUPMEMBERS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ApprovalsGroupMember)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALSGROUPMEMBERS");
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
      if (paramString.equals("APPROVALSGROUPMEMBER"))
      {
        ApprovalsGroupMember localApprovalsGroupMember = new ApprovalsGroupMember(ApprovalsGroupMembers.this.locale);
        ApprovalsGroupMembers.this.add(localApprovalsGroupMember);
        localApprovalsGroupMember.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsGroupMembers
 * JD-Core Version:    0.7.0.1
 */