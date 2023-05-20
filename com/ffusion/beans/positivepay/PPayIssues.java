package com.ffusion.beans.positivepay;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Locale;

public class PPayIssues
  extends FilteredList
  implements Serializable
{
  public static final String PPAYISSUES = "PPAYISSUES";
  
  public PPayIssues(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public PPayIssue add()
  {
    PPayIssue localPPayIssue = new PPayIssue(this.locale);
    add(localPPayIssue);
    return localPPayIssue;
  }
  
  public PPayIssue create()
  {
    PPayIssue localPPayIssue = new PPayIssue(this.locale);
    return localPPayIssue;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PPayIssues)) {
      return false;
    }
    PPayIssues localPPayIssues = (PPayIssues)paramObject;
    if (size() != localPPayIssues.size()) {
      return false;
    }
    if ((this.locale != null) && (localPPayIssues.locale != null))
    {
      if (!this.locale.equals(localPPayIssues.locale)) {
        return false;
      }
    }
    else
    {
      if ((this.locale != null) && (localPPayIssues.locale == null)) {
        return false;
      }
      if ((this.locale == null) && (localPPayIssues.locale != null)) {
        return false;
      }
    }
    return containsAll(localPPayIssues);
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
    XMLHandler.appendBeginTag(localStringBuffer, "PPAYISSUES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((PPayIssue)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PPAYISSUES");
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
      if (paramString.equals("PPAYISSUE"))
      {
        PPayIssue localPPayIssue = new PPayIssue();
        PPayIssues.this.add(localPPayIssue);
        localPPayIssue.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPayIssues
 * JD-Core Version:    0.7.0.1
 */