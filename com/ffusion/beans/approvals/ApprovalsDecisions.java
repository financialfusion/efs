package com.ffusion.beans.approvals;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class ApprovalsDecisions
  extends FilteredList
  implements XMLStrings, XMLable
{
  public ApprovalsDecisions(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ApprovalsDecision add()
  {
    ApprovalsDecision localApprovalsDecision = new ApprovalsDecision(this.locale);
    add(localApprovalsDecision);
    return localApprovalsDecision;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof ApprovalsDecisions)) {
      return false;
    }
    ApprovalsDecisions localApprovalsDecisions = (ApprovalsDecisions)paramObject;
    if (size() != localApprovalsDecisions.size()) {
      return false;
    }
    Locale localLocale1 = this.locale;
    Locale localLocale2 = localApprovalsDecisions.locale;
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
    return containsAll(localApprovalsDecisions);
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
    XMLHandler.appendBeginTag(localStringBuffer, "APPROVALS_DECISIONS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ApprovalsDecision)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "APPROVALS_DECISIONS");
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
      if (paramString.equals("APPROVALS_DECISION"))
      {
        ApprovalsDecision localApprovalsDecision = new ApprovalsDecision(ApprovalsDecisions.this.locale);
        ApprovalsDecisions.this.add(localApprovalsDecision);
        localApprovalsDecision.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.approvals.ApprovalsDecisions
 * JD-Core Version:    0.7.0.1
 */