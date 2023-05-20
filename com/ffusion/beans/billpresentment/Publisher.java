package com.ffusion.beans.billpresentment;

import com.ffusion.beans.Contact;
import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Locale;

public class Publisher
  extends Contact
  implements Comparable, Serializable
{
  public static final String PUBLISHER = "PUBLISHER";
  public static final String PUBLISHERID = "PUBLISHERID";
  public static final String SPNAME = "SPNAME";
  public static final String STATUSCODE = "STATUSCODE";
  public static final String MODIFIEDDATE = "MODIFIEDDATE";
  public static final String PUBLISHERURL = "PUBLISHERURL";
  public static final String CREATEDATE = "CREATEDATE";
  public static final String STATUSEFFECTIVEDATE = "STATUSEFFECTIVEDATE";
  private DateTime dt;
  private long dw;
  private String dr;
  private String ds;
  private DateTime du;
  private String dv;
  private DateTime dq;
  protected String trackingID;
  
  public Publisher()
  {
    this.datetype = "SHORT";
  }
  
  public Publisher(Locale paramLocale)
  {
    super(paramLocale);
    this.datetype = "SHORT";
  }
  
  public String getTrackingID()
  {
    return this.trackingID;
  }
  
  public void setTrackingID(String paramString)
  {
    this.trackingID = paramString;
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.dt != null) {
      this.dt.setLocale(paramLocale);
    }
    if (this.du != null) {
      this.du.setLocale(paramLocale);
    }
    if (this.dq != null) {
      this.dq.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.dt != null) {
      this.dt.setFormat(paramString);
    }
    if (this.du != null) {
      this.du.setFormat(paramString);
    }
    if (this.dq != null) {
      this.dq.setFormat(paramString);
    }
  }
  
  public final void setPublisherID(String paramString)
  {
    try
    {
      this.dw = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception setting ID:", localException);
    }
  }
  
  public final String getPublisherID()
  {
    return String.valueOf(this.dw);
  }
  
  public final long getPublisherIDValue()
  {
    return this.dw;
  }
  
  public final void setPublisherURL(String paramString)
  {
    this.dr = paramString;
  }
  
  public final String getPublisherURL()
  {
    return this.dr;
  }
  
  public final void setStatusCode(String paramString)
  {
    this.ds = paramString;
  }
  
  public final String getStatusCode()
  {
    return this.ds;
  }
  
  public final void setCreateDate(DateTime paramDateTime)
  {
    this.du = paramDateTime;
  }
  
  public final void setCreateDate(String paramString)
  {
    try
    {
      if (this.du == null) {
        this.du = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.du.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.du = null;
    }
  }
  
  public final DateTime getCreateDateValue()
  {
    return this.du;
  }
  
  public final String getCreateDate()
  {
    if (this.du != null) {
      return this.du.toString();
    }
    return "";
  }
  
  public final void setSpName(String paramString)
  {
    this.dv = paramString;
  }
  
  public final String getSpName()
  {
    return this.dv;
  }
  
  public final void setStatusEffectiveDate(DateTime paramDateTime)
  {
    this.dq = paramDateTime;
  }
  
  public final void setStatusEffectiveDate(String paramString)
  {
    try
    {
      if (this.dq == null) {
        this.dq = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.dq.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.dq = null;
    }
  }
  
  public final DateTime getStatusEffectiveDateValue()
  {
    return this.dq;
  }
  
  public final String getStatusEffectiveDate()
  {
    if (this.dq != null) {
      return this.dq.toString();
    }
    return "";
  }
  
  public final void setModifiedDate(DateTime paramDateTime)
  {
    this.dt = paramDateTime;
  }
  
  public final void setModifiedDate(String paramString)
  {
    try
    {
      if (this.dt == null) {
        this.dt = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.dt.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.dt = null;
    }
  }
  
  public final DateTime getModifiedDateValue()
  {
    return this.dt;
  }
  
  public final String getModifiedDate()
  {
    if (this.dt != null) {
      return this.dt.toString();
    }
    return "";
  }
  
  public void set(Publisher paramPublisher)
  {
    super.set(paramPublisher);
    setLocale(paramPublisher.locale);
    setDateFormat(paramPublisher.getDateFormat());
    if (paramPublisher.getCreateDateValue() != null) {
      setCreateDate((DateTime)paramPublisher.getCreateDateValue().clone());
    } else {
      setCreateDate((DateTime)null);
    }
    if (paramPublisher.getModifiedDateValue() != null) {
      setModifiedDate((DateTime)paramPublisher.getModifiedDateValue().clone());
    } else {
      setModifiedDate((DateTime)null);
    }
    setPublisherID(paramPublisher.getPublisherID());
    setPublisherURL(paramPublisher.getPublisherURL());
    setSpName(paramPublisher.getSpName());
    setStatusCode(paramPublisher.getStatusCode());
    if (paramPublisher.getStatusEffectiveDateValue() != null) {
      setStatusEffectiveDate((DateTime)paramPublisher.getStatusEffectiveDateValue().clone());
    } else {
      setStatusEffectiveDate((DateTime)null);
    }
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    Publisher localPublisher = (Publisher)paramObject;
    int i;
    if (this == localPublisher) {
      i = 0;
    } else if (getSpName() == null) {
      i = -1;
    } else if ((localPublisher == null) || (localPublisher.getSpName() == null)) {
      i = 1;
    } else {
      i = getSpName().compareTo(localPublisher.getSpName());
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    int i = 1;
    Publisher localPublisher = (Publisher)paramObject;
    if ((paramString.equals("PUBLISHERID")) && (getPublisherID() != null) && (localPublisher.getPublisherID() != null)) {
      i = getPublisherID().compareTo(localPublisher.getPublisherID());
    } else if ((paramString.equals("SPNAME")) && (getSpName() != null) && (localPublisher.getSpName() != null)) {
      i = getSpName().compareTo(localPublisher.getSpName());
    } else if ((paramString.equals("STATUSCODE")) && (getStatusCode() != null) && (localPublisher.getStatusCode() != null)) {
      i = getStatusCode().compareTo(localPublisher.getStatusCode());
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("PUBLISHERID"))
      {
        this.dw = Long.parseLong(paramString2);
      }
      else if (paramString1.equals("SPNAME"))
      {
        this.dv = paramString2;
      }
      else if (paramString1.equals("STATUSCODE"))
      {
        this.ds = paramString2;
      }
      else if (paramString1.equals("PUBLISHERURL"))
      {
        this.dr = paramString2;
      }
      else if (paramString1.equals("MODIFIEDDATE"))
      {
        if (this.dt == null)
        {
          this.dt = new DateTime(this.locale);
          this.dt.setFormat(this.datetype);
        }
        this.dt.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("CREATEDATE"))
      {
        if (this.du == null)
        {
          this.du = new DateTime(this.locale);
          this.du.setFormat(this.datetype);
        }
        this.du.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("STATUSEFFECTIVEDATE"))
      {
        if (this.dq == null)
        {
          this.dq = new DateTime(this.locale);
          this.dq.setFormat(this.datetype);
        }
        this.dq.fromXMLFormat(paramString2);
      }
      else
      {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception:", localException);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "PUBLISHER");
    XMLHandler.appendTag(localStringBuffer, "PUBLISHERID", Long.toString(this.dw));
    XMLHandler.appendTag(localStringBuffer, "SPNAME", this.dv);
    XMLHandler.appendTag(localStringBuffer, "STATUSCODE", this.ds);
    XMLHandler.appendTag(localStringBuffer, "PUBLISHERURL", this.dr);
    XMLHandler.appendTag(localStringBuffer, "MODIFIEDDATE", this.dt);
    XMLHandler.appendTag(localStringBuffer, "CREATEDATE", this.du);
    XMLHandler.appendTag(localStringBuffer, "STATUSEFFECTIVEDATE", this.dq);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "PUBLISHER");
    if (localStringBuffer.toString() == null) {
      return "";
    }
    return localStringBuffer.toString();
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(), paramString);
    }
    catch (Throwable localThrowable) {}
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  public String getDateFormat()
  {
    return super.getDateFormat();
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      Publisher.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.Publisher
 * JD-Core Version:    0.7.0.1
 */