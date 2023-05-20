package com.ffusion.beans.positivepay;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Locale;

public class PPaySummaries
  extends FilteredList
  implements Serializable
{
  public static final String PPAYSUMMARIES = "PPAYSUMMARIES";
  
  public PPaySummaries(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public PPaySummary add()
  {
    PPaySummary localPPaySummary = new PPaySummary(this.locale);
    add(localPPaySummary);
    return localPPaySummary;
  }
  
  public PPaySummary create()
  {
    PPaySummary localPPaySummary = new PPaySummary(this.locale);
    return localPPaySummary;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PPaySummaries)) {
      return false;
    }
    PPaySummaries localPPaySummaries = (PPaySummaries)paramObject;
    if (size() != localPPaySummaries.size()) {
      return false;
    }
    if ((this.locale != null) && (localPPaySummaries.locale != null))
    {
      if (!this.locale.equals(localPPaySummaries.locale)) {
        return false;
      }
    }
    else
    {
      if ((this.locale != null) && (localPPaySummaries.locale == null)) {
        return false;
      }
      if ((this.locale == null) && (localPPaySummaries.locale != null)) {
        return false;
      }
    }
    return containsAll(localPPaySummaries);
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
    XMLHandler.appendBeginTag(localStringBuffer, "PPAYSUMMARIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((PPaySummary)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PPAYSUMMARIES");
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
      if (paramString.equals("PPAYSUMMARY"))
      {
        PPaySummary localPPaySummary = new PPaySummary(PPaySummaries.this.locale);
        PPaySummaries.this.add(localPPaySummary);
        localPPaySummary.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPaySummaries
 * JD-Core Version:    0.7.0.1
 */