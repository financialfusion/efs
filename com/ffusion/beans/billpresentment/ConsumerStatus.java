package com.ffusion.beans.billpresentment;

import com.ffusion.beans.DateTime;
import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.util.Locale;

public class ConsumerStatus
  extends ExtendABean
{
  public static final String CONSUMERSTATUS = "CONSUMERSTATUS";
  public static final String CONSUMERSTATUSID = "CONSUMERSTATUSID";
  public static final String TCACCEPTED = "TCACCEPTED";
  public static final String NEWBILLERNOTIFYDESIRED = "NEWBILLERNOTIFYDESIRED";
  public static final String NEWBILLERNOTIFIEDDATE = "NEWBILLERNOTIFIEDDATE";
  public static final String TCURL = "TCURL";
  public static final String BILLDUENOTIFYDESIRED = "BILLDUENOTIFYDESIRED";
  public static final String BILLDUENOTIFYDAYS = "BILLDUENOTIFYDAYS";
  public static final String NEWBILLNOTIFYDESIRED = "NEWBILLNOTIFYDESIRED";
  public static final String ACCOUNTINFONOTIFYDESIRED = "ACCOUNTINFONOTIFYDESIRED";
  private long ID;
  private String Jg;
  private String Jc;
  private DateTime Jh;
  private long Ja;
  private String Jd;
  private long Jb;
  private String Jf;
  private String Je;
  
  public ConsumerStatus()
  {
    this.datetype = "SHORT";
  }
  
  public void setLocale(Locale paramLocale)
  {
    super.setLocale(paramLocale);
    if (this.Jh != null) {
      this.Jh.setLocale(paramLocale);
    }
  }
  
  public void setDateFormat(String paramString)
  {
    super.setDateFormat(paramString);
    if (this.Jh != null) {
      this.Jh.setFormat(paramString);
    }
  }
  
  public final String getID()
  {
    return String.valueOf(this.ID);
  }
  
  public final long getIDValue()
  {
    return this.ID;
  }
  
  public final void setID(String paramString)
  {
    try
    {
      this.ID = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception setting ID:", localException);
    }
  }
  
  public final void setID(long paramLong)
  {
    this.ID = paramLong;
  }
  
  public final void setTCAccepted(String paramString)
  {
    this.Jg = paramString;
  }
  
  public final String getTCAccepted()
  {
    return this.Jg;
  }
  
  public final void setNewBillerNotifyDesired(String paramString)
  {
    this.Jc = paramString;
  }
  
  public final String getNewBillerNotifyDesired()
  {
    return this.Jc;
  }
  
  public final void setNewBillerNotifiedDate(DateTime paramDateTime)
  {
    this.Jh = paramDateTime;
  }
  
  public final void setNewBillerNotifiedDate(String paramString)
  {
    try
    {
      if (this.Jh == null) {
        this.Jh = new DateTime(paramString, this.locale, this.datetype);
      } else {
        this.Jh.fromString(paramString);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception:", localException);
      this.Jh = null;
    }
  }
  
  public final DateTime getNewBillerNotifiedDateValue()
  {
    return this.Jh;
  }
  
  public final String getNewBillerNotifiedDate()
  {
    if (this.Jh != null) {
      return this.Jh.toString();
    }
    return "";
  }
  
  public final void setBillDueNotifyDesired(String paramString)
  {
    this.Jd = paramString;
  }
  
  public final String getBillDueNotifyDesired()
  {
    return this.Jd;
  }
  
  public final void setBillDueNotifyDays(String paramString)
  {
    this.Jb = Long.parseLong(paramString);
  }
  
  public final long getBillDueNotifyDaysValue()
  {
    return this.Jb;
  }
  
  public final String getBillDueNotifyDays()
  {
    return String.valueOf(this.Jb);
  }
  
  public final void setNewBillNotifyDesired(String paramString)
  {
    this.Jf = paramString;
  }
  
  public final String getNewBillNotifyDesired()
  {
    return this.Jf;
  }
  
  public final void setAccountInfoNotifyDesired(String paramString)
  {
    this.Je = paramString;
  }
  
  public final String getAccountInfoNotifyDesired()
  {
    return this.Je;
  }
  
  public final void setTCurl(long paramLong)
  {
    this.Ja = paramLong;
  }
  
  public final String getTCurl()
  {
    return String.valueOf(this.Ja);
  }
  
  public final long getTCurlValue()
  {
    return this.Ja;
  }
  
  public void set(ConsumerStatus paramConsumerStatus)
  {
    setLocale(paramConsumerStatus.locale);
    setDateFormat(paramConsumerStatus.getDateFormat());
    this.ID = Long.parseLong(paramConsumerStatus.getID());
    setTCAccepted(paramConsumerStatus.getTCAccepted());
    setTCurl(paramConsumerStatus.getTCurlValue());
    setNewBillerNotifyDesired(paramConsumerStatus.getNewBillerNotifyDesired());
    if (paramConsumerStatus.getNewBillerNotifiedDateValue() != null) {
      setNewBillerNotifiedDate((DateTime)paramConsumerStatus.getNewBillerNotifiedDateValue().clone());
    } else {
      setNewBillerNotifiedDate((DateTime)null);
    }
    setBillDueNotifyDesired(paramConsumerStatus.getBillDueNotifyDesired());
    setBillDueNotifyDays(paramConsumerStatus.getBillDueNotifyDays());
    setNewBillNotifyDesired(paramConsumerStatus.getNewBillNotifyDesired());
    setAccountInfoNotifyDesired(paramConsumerStatus.getAccountInfoNotifyDesired());
    super.set(paramConsumerStatus);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("TCACCEPTED")) {
      this.Jg = paramString2;
    } else if (paramString1.equals("CONSUMERSTATUSID")) {
      this.ID = Long.parseLong(paramString2);
    } else if (paramString1.equals("NEWBILLERNOTIFYDESIRED")) {
      this.Jc = paramString2;
    } else if (paramString1.equals("NEWBILLERNOTIFIEDDATE")) {
      try
      {
        this.Jh.fromString(paramString2);
      }
      catch (Exception localException1)
      {
        DebugLog.throwing("Exception:", localException1);
      }
    } else if (paramString1.equals("TCURL")) {
      try
      {
        this.Ja = Integer.parseInt(paramString2);
      }
      catch (Exception localException2)
      {
        DebugLog.throwing("Exception:", localException2);
      }
    } else if (paramString1.equals("BILLDUENOTIFYDESIRED")) {
      this.Jd = paramString2;
    } else if (paramString1.equals("BILLDUENOTIFYDAYS")) {
      this.Jb = Integer.parseInt(paramString2);
    } else if (paramString1.equals("NEWBILLNOTIFYDESIRED")) {
      this.Jf = paramString2;
    } else if (paramString1.equals("ACCOUNTINFONOTIFYDESIRED")) {
      this.Je = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "CONSUMERSTATUS");
    XMLHandler.appendTag(localStringBuffer, "CONSUMERSTATUSID", this.ID);
    XMLHandler.appendTag(localStringBuffer, "TCACCEPTED", this.Jg);
    XMLHandler.appendTag(localStringBuffer, "NEWBILLERNOTIFYDESIRED", this.Jc);
    XMLHandler.appendTag(localStringBuffer, "NEWBILLERNOTIFIEDDATE", this.Jh);
    XMLHandler.appendTag(localStringBuffer, "TCURL", this.Ja);
    XMLHandler.appendTag(localStringBuffer, "BILLDUENOTIFYDESIRED", this.Jd);
    XMLHandler.appendTag(localStringBuffer, "BILLDUENOTIFYDAYS", this.Jb);
    XMLHandler.appendTag(localStringBuffer, "NEWBILLNOTIFYDESIRED", this.Jf);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTINFONOTIFYDESIRED", this.Je);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "CONSUMERSTATUS");
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
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      ConsumerStatus.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.ConsumerStatus
 * JD-Core Version:    0.7.0.1
 */