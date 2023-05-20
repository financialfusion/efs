package com.ffusion.beans.alerts;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.beans.InvalidDateTimeException;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.text.Collator;
import java.util.Locale;

public class LogMessage
  extends ExtendABean
  implements Serializable
{
  public static final String LOGMESSAGE = "LOGMESSAGE";
  public static final String ALERTID = "ALERTID";
  public static final String DELIVERYCHANNEL = "DELIVERYCHANNEL";
  public static final String TIMEINFO = "TIMEINFO";
  protected String id;
  protected String alertID;
  protected String deliveryChannel;
  protected DateTime date;
  protected String type;
  protected String status;
  protected long timeInfo;
  
  public LogMessage() {}
  
  public LogMessage(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.date != null) {
      this.date.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.date != null) {
      this.date.setFormat(paramString);
    }
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getAlertID()
  {
    return this.alertID;
  }
  
  public void setAlertID(String paramString)
  {
    this.alertID = paramString;
  }
  
  public String getDeliveryChannel()
  {
    return this.deliveryChannel;
  }
  
  public void setDeliveryChannel(String paramString)
  {
    this.deliveryChannel = paramString;
  }
  
  public String getDate()
  {
    if (this.date != null) {
      return this.date.toString();
    }
    return "";
  }
  
  public DateTime getDateValue()
  {
    return this.date;
  }
  
  public void setDate(String paramString)
  {
    try
    {
      if (paramString == null) {
        this.date = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.date.fromString(paramString);
      }
    }
    catch (InvalidDateTimeException localInvalidDateTimeException)
    {
      DebugLog.throwing("Exception:", localInvalidDateTimeException);
      this.date = null;
    }
  }
  
  public void setDate(DateTime paramDateTime)
  {
    this.date = paramDateTime;
  }
  
  public String getType()
  {
    return String.valueOf(this.type);
  }
  
  public void setType(String paramString)
  {
    this.type = paramString;
  }
  
  public String getStatus()
  {
    return this.status;
  }
  
  public void setStatus(String paramString)
  {
    this.status = paramString;
  }
  
  public String getTimeInfo()
  {
    return String.valueOf(this.timeInfo);
  }
  
  public long getTimeInfoValue()
  {
    return this.timeInfo;
  }
  
  public void setTimeInfo(String paramString)
  {
    if (paramString != null) {
      this.timeInfo = Long.parseLong(paramString);
    }
  }
  
  public void setTimeInfo(long paramLong)
  {
    this.timeInfo = paramLong;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "DATE");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    LogMessage localLogMessage = (LogMessage)paramObject;
    int i = 1;
    if ((paramString.equals("ID")) && (this.id != null) && (localLogMessage.getID() != null)) {
      i = localCollator.compare(this.id, localLogMessage.getID());
    } else if ((paramString.equals("ALERTID")) && (this.alertID != null) && (localLogMessage.getAlertID() != null)) {
      i = localCollator.compare(this.alertID, localLogMessage.getAlertID());
    } else if ((paramString.equals("DELIVERYCHANNEL")) && (this.deliveryChannel != null) && (localLogMessage.getDeliveryChannel() != null)) {
      i = localCollator.compare(this.deliveryChannel, localLogMessage.getDeliveryChannel());
    } else if ((paramString.equals("DATE")) && (this.date != null) && (localLogMessage.getDateValue() != null)) {
      i = this.date.equals(localLogMessage.getDateValue()) ? 0 : this.date.before(localLogMessage.getDateValue()) ? -1 : 1;
    } else if ((paramString.equals("TYPE")) && (this.type != null) && (localLogMessage.getType() != null)) {
      i = localCollator.compare(this.type, localLogMessage.getType());
    } else if ((paramString.equals("STATUS")) && (this.status != null) && (localLogMessage.getStatus() != null)) {
      i = localCollator.compare(this.status, localLogMessage.getStatus());
    } else if (paramString.equals("TIMEINFO")) {
      i = Integer.parseInt(String.valueOf(getTimeInfoValue() - localLogMessage.getTimeInfoValue()));
    } else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void set(LogMessage paramLogMessage)
  {
    setID(paramLogMessage.getID());
    setAlertID(paramLogMessage.getAlertID());
    setDeliveryChannel(paramLogMessage.getDeliveryChannel());
    if (paramLogMessage.getDateValue() != null) {
      setDate((DateTime)paramLogMessage.getDateValue().clone());
    } else {
      setDate((DateTime)null);
    }
    setType(paramLogMessage.getType());
    setStatus(paramLogMessage.getStatus());
    setTimeInfo(paramLogMessage.getTimeInfo());
    super.set(paramLogMessage);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("ID"))
      {
        this.id = paramString2;
      }
      else if (paramString1.equals("ALERTID"))
      {
        this.alertID = paramString2;
      }
      else if (paramString1.equals("DELIVERYCHANNEL"))
      {
        this.deliveryChannel = paramString2;
      }
      else if (paramString1.equals("DATE"))
      {
        if (this.date == null)
        {
          this.date = new DateTime(this.locale);
          this.date.setFormat(this.datetype);
        }
        this.date.fromXMLFormat(paramString2);
      }
      else if (paramString1.equals("TYPE"))
      {
        this.type = paramString2;
      }
      else if (paramString1.equals("STATUS"))
      {
        this.status = paramString2;
      }
      else if (paramString1.equals("TIMEINFO"))
      {
        this.timeInfo = Long.parseLong(paramString2);
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
  
  public boolean isFilterablePreParsed(String paramString1, String paramString2, String paramString3)
  {
    if ((paramString1.equalsIgnoreCase("TYPE")) && (getType() != null)) {
      return isFilterable(getType(), paramString2, paramString3);
    }
    if ((paramString1.equals("DATE")) && (this.date != null)) {
      return this.date.isFilterable("VALUE" + paramString2 + paramString3);
    }
    return super.isFilterablePreParsed(paramString1, paramString2, paramString3);
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
    XMLHandler.appendBeginTag(localStringBuffer, "LOGMESSAGE");
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "ALERTID", this.alertID);
    XMLHandler.appendTag(localStringBuffer, "DELIVERYCHANNEL", this.deliveryChannel);
    if (this.date != null) {
      XMLHandler.appendTag(localStringBuffer, "DATE", this.date.toXMLFormat());
    }
    XMLHandler.appendTag(localStringBuffer, "TYPE", this.type);
    XMLHandler.appendTag(localStringBuffer, "STATUS", this.status);
    XMLHandler.appendTag(localStringBuffer, "TIMEINFO", this.timeInfo);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "LOGMESSAGE");
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
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      LogMessage.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.alerts.LogMessage
 * JD-Core Version:    0.7.0.1
 */