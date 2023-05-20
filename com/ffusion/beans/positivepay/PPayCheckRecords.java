package com.ffusion.beans.positivepay;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Locale;

public class PPayCheckRecords
  extends FilteredList
  implements Serializable
{
  public static final String PPAYCHECKRECORDS = "PPAYCHECKRECORDS";
  
  public PPayCheckRecords(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public PPayCheckRecord add()
  {
    PPayCheckRecord localPPayCheckRecord = new PPayCheckRecord(this.locale);
    add(localPPayCheckRecord);
    return localPPayCheckRecord;
  }
  
  public PPayCheckRecord create()
  {
    PPayCheckRecord localPPayCheckRecord = new PPayCheckRecord(this.locale);
    return localPPayCheckRecord;
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof PPayCheckRecords)) {
      return false;
    }
    PPayCheckRecords localPPayCheckRecords = (PPayCheckRecords)paramObject;
    if (size() != localPPayCheckRecords.size()) {
      return false;
    }
    if ((this.locale != null) && (localPPayCheckRecords.locale != null))
    {
      if (!this.locale.equals(localPPayCheckRecords.locale)) {
        return false;
      }
    }
    else
    {
      if ((this.locale != null) && (localPPayCheckRecords.locale == null)) {
        return false;
      }
      if ((this.locale == null) && (localPPayCheckRecords.locale != null)) {
        return false;
      }
    }
    return containsAll(localPPayCheckRecords);
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
    XMLHandler.appendBeginTag(localStringBuffer, "PPAYCHECKRECORDS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((PPayCheckRecord)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PPAYCHECKRECORDS");
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
      if (paramString.equals("PPAYCHECKRECORD"))
      {
        PPayCheckRecord localPPayCheckRecord = new PPayCheckRecord();
        PPayCheckRecords.this.add(localPPayCheckRecord);
        localPPayCheckRecord.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.positivepay.PPayCheckRecords
 * JD-Core Version:    0.7.0.1
 */