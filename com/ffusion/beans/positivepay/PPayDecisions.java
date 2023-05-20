package com.ffusion.beans.positivepay;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Locale;

public class PPayDecisions
  extends FilteredList
  implements Serializable
{
  public static final String PPAYDECISIONS = "PPAYDECISIONS";
  
  public PPayDecisions(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public PPayDecision add()
  {
    PPayDecision localPPayDecision = new PPayDecision(this.locale);
    add(localPPayDecision);
    return localPPayDecision;
  }
  
  public PPayDecision create()
  {
    PPayDecision localPPayDecision = new PPayDecision(this.locale);
    return localPPayDecision;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PPayDecisions)) {
      return false;
    }
    PPayDecisions localPPayDecisions = (PPayDecisions)paramObject;
    if (size() != localPPayDecisions.size()) {
      return false;
    }
    if ((this.locale != null) && (localPPayDecisions.locale != null))
    {
      if (!this.locale.equals(localPPayDecisions.locale)) {
        return false;
      }
    }
    else
    {
      if ((this.locale != null) && (localPPayDecisions.locale == null)) {
        return false;
      }
      if ((this.locale == null) && (localPPayDecisions.locale != null)) {
        return false;
      }
    }
    return containsAll(localPPayDecisions);
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
    XMLHandler.appendBeginTag(localStringBuffer, "PPAYDECISIONS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((PPayDecision)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PPAYDECISIONS");
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
      if (paramString.equals("PPAYDECISION"))
      {
        PPayDecision localPPayDecision = new PPayDecision();
        PPayDecisions.this.add(localPPayDecision);
        localPPayDecision.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPayDecisions
 * JD-Core Version:    0.7.0.1
 */