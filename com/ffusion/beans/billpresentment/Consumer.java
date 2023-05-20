package com.ffusion.beans.billpresentment;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.beans.Person;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.util.Locale;

public class Consumer
  extends Person
  implements Serializable, EnrollmentStatus
{
  public static final String CONSUMER = "CONSUMER";
  public static final String CONSUMERID = "CONSUMERID";
  public static final String FIRSTNAME = "FIRSTNAME";
  public static final String MIDDLENAME = "MIDDLENAME";
  public static final String LASTNAME = "LASTNAME";
  public static final String SECURITYNAME = "SECURITYNAME";
  public static final String TAXID = "TAXID";
  public static final String BIRTHDATE = "BIRTHDATE";
  public static final String STATUSCODE = "STATUSCODE";
  public static final String ACTIVE = "ACTIVE";
  public static final String DELETE = "DELETE";
  private long os;
  private String or;
  private String oo;
  private DateTime oq;
  private String op;
  protected String trackingID;
  
  public Consumer()
  {
    this.datetype = "SHORT";
  }
  
  public Consumer(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    setDateFormat("SHORT");
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
    if (this.oq != null) {
      this.oq.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.oq != null) {
      this.oq.setFormat(paramString);
    }
  }
  
  public final void setConsumerID(long paramLong)
  {
    try
    {
      this.os = paramLong;
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception setting consumerID:", localException);
    }
  }
  
  public final void setConsumerID(String paramString)
  {
    this.os = Long.parseLong(paramString);
  }
  
  public final long getConsumerIDValue()
  {
    return this.os;
  }
  
  public final String getConsumerID()
  {
    if (this.os == 0L) {
      return "";
    }
    return String.valueOf(this.os);
  }
  
  public final void setSecurityName(String paramString)
  {
    this.or = paramString;
  }
  
  public final String getSecurityName()
  {
    return this.or;
  }
  
  public final void setTaxID(String paramString)
  {
    this.oo = paramString;
  }
  
  public final String getTaxID()
  {
    return this.oo;
  }
  
  public final void setBirthDate(DateTime paramDateTime)
  {
    this.oq = paramDateTime;
  }
  
  public void setBirthDate(String paramString)
  {
    try
    {
      if (this.oq == null) {
        this.oq = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.oq.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.oq = null;
    }
  }
  
  public final DateTime getBirthDateValue()
  {
    return this.oq;
  }
  
  public final String getBirthDate()
  {
    if (this.oq != null) {
      return this.oq.toString();
    }
    return "";
  }
  
  public final void setStatusCode(String paramString)
  {
    this.op = paramString;
  }
  
  public final String getStatusCode()
  {
    return this.op;
  }
  
  public void set(Consumer paramConsumer)
  {
    super.set(paramConsumer);
    setConsumerID(paramConsumer.getConsumerIDValue());
    setSecurityName(paramConsumer.getSecurityName());
    setTaxID(paramConsumer.getTaxID());
    if (paramConsumer.getBirthDateValue() != null) {
      setBirthDate((DateTime)paramConsumer.getBirthDateValue().clone());
    } else {
      setBirthDate((DateTime)null);
    }
    setStatusCode(paramConsumer.getStatusCode());
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("CONSUMERID"))
    {
      setConsumerID(paramString2);
    }
    else if (paramString1.equals("SECURITYNAME"))
    {
      setSecurityName(paramString2);
    }
    else if (paramString1.equals("TAXID"))
    {
      setTaxID(paramString2);
    }
    else if (paramString1.equals("BIRTHDATE"))
    {
      if (this.oq == null)
      {
        this.oq = new DateTime(this.locale);
        this.oq.setFormat(this.datetype);
      }
      this.oq.fromXMLFormat(paramString2);
    }
    else if (paramString1.equals("STATUSCODE"))
    {
      setStatusCode(paramString2);
    }
    else
    {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "CONSUMER");
    XMLHandler.appendTag(localStringBuffer, "CONSUMERID", this.os);
    XMLHandler.appendTag(localStringBuffer, "SECURITYNAME", this.or);
    XMLHandler.appendTag(localStringBuffer, "TAXID", this.oo);
    XMLHandler.appendTag(localStringBuffer, "BIRTHDATE", this.oq);
    XMLHandler.appendTag(localStringBuffer, "STATUSCODE", this.op);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "CONSUMER");
    if (localStringBuffer.toString() == null) {
      return "";
    }
    return localStringBuffer.toString();
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
    a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if (str.length() > 0) {
        Consumer.this.set(getElement(), str);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.Consumer
 * JD-Core Version:    0.7.0.1
 */