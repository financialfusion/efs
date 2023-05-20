package com.ffusion.beans.billpresentment;

import com.ffusion.beans.Contact;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;

public class Biller
  extends Contact
  implements Comparable, Serializable
{
  public static final String BILLER = "BILLER";
  public static final String BILLERID = "BILLERID";
  public static final String BILLERNAME = "BILLERNAME";
  public static final String PUBLISHERID = "PUBLISHERID";
  public static final String STATUSCODE = "STATUSCODE";
  public static final String PAYEEID = "PAYEEID";
  public static final String RESTRICTMESSAGE = "RESTRICTMESSAGE";
  public static final String BILLERENROLLURL = "BILLERENROLLURL";
  public static final String BILLERINFOURL = "BILLERINFOURL";
  public static final String LOGOURL = "LOGOURL";
  public static final String VALIDATEURL = "VALIDATEURL";
  public static final String ACCOUNTFORMAT = "ACCOUNTFORMAT";
  public static final String ACCOUNTEDITMASK = "ACCOUNTEDITMASK";
  public static final String HELPMESSAGE = "HELPMESSAGE";
  private long c7;
  private String dc;
  private long db;
  private String c8;
  private String dg;
  private String dd;
  private String dh;
  private String da;
  private String de;
  private String c9;
  private String di;
  private String df;
  private String dj;
  protected String trackingID;
  
  public Biller()
  {
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
  
  public boolean isFilterable(String paramString)
  {
    if (super.isFilterable(paramString) != true) {
      return false;
    }
    return (getStatusCode() != null) && (paramString.indexOf(getStatusCode()) != -1);
  }
  
  public final void setAccountFormat(String paramString)
  {
    this.di = paramString;
  }
  
  public final String getAccountFormat()
  {
    return this.di;
  }
  
  public final void setAccountEditMask(String paramString)
  {
    this.df = paramString;
  }
  
  public final String getAccountEditMask()
  {
    return this.df;
  }
  
  public final void setHelpMessage(String paramString)
  {
    this.dj = paramString;
  }
  
  public final String getHelpMessage()
  {
    return this.dj;
  }
  
  public final long getBillerIDValue()
  {
    return this.c7;
  }
  
  public final String getBillerID()
  {
    return String.valueOf(this.c7);
  }
  
  public final void setBillerID(long paramLong)
  {
    this.c7 = paramLong;
  }
  
  public final void setBillerID(String paramString)
  {
    try
    {
      this.c7 = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception setting BillerID:", localException);
    }
  }
  
  public final void setBillerName(String paramString)
  {
    this.dc = paramString;
  }
  
  public final String getBillerName()
  {
    return this.dc;
  }
  
  public final void setPublisherID(long paramLong)
  {
    this.db = paramLong;
  }
  
  public final void setPublisherID(String paramString)
  {
    try
    {
      this.db = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception setting PublisherID:", localException);
    }
  }
  
  public final long getPublisherIDValue()
  {
    return this.db;
  }
  
  public final String getPublisherID()
  {
    return String.valueOf(this.db);
  }
  
  public final void setStatusCode(String paramString)
  {
    this.c8 = paramString;
  }
  
  public final String getStatusCode()
  {
    return this.c8;
  }
  
  public final void setPayeeID(String paramString)
  {
    this.dg = paramString;
  }
  
  public final String getPayeeID()
  {
    return this.dg;
  }
  
  public final void setRestrictMessage(String paramString)
  {
    this.dd = paramString;
  }
  
  public final String getRestrictMessage()
  {
    return this.dd;
  }
  
  public final void setBillerEnrollURL(String paramString)
  {
    this.dh = paramString;
  }
  
  public final String getBillerEnrollURL()
  {
    return this.dh;
  }
  
  public final void setBillerInfoURL(String paramString)
  {
    this.da = paramString;
  }
  
  public final String getBillerInfoURL()
  {
    return this.da;
  }
  
  public final void setLogoURL(String paramString)
  {
    this.de = paramString;
  }
  
  public final String getLogoURL()
  {
    return this.de;
  }
  
  public final void setValidateURL(String paramString)
  {
    this.c9 = paramString;
  }
  
  public final String getValidateURL()
  {
    return this.c9;
  }
  
  public void set(Biller paramBiller)
  {
    if ((this == paramBiller) || (paramBiller == null)) {
      return;
    }
    setBillerID(paramBiller.getBillerIDValue());
    setBillerName(paramBiller.getBillerName());
    setPublisherID(paramBiller.getPublisherIDValue());
    setStatusCode(paramBiller.getStatusCode());
    setPayeeID(paramBiller.getPayeeID());
    setRestrictMessage(paramBiller.getRestrictMessage());
    setBillerEnrollURL(paramBiller.getBillerEnrollURL());
    setBillerInfoURL(paramBiller.getBillerInfoURL());
    setLogoURL(paramBiller.getLogoURL());
    setValidateURL(paramBiller.getValidateURL());
    setAccountFormat(paramBiller.getAccountFormat());
    setAccountEditMask(paramBiller.getAccountEditMask());
    setHelpMessage(paramBiller.getHelpMessage());
    super.set(paramBiller);
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    Biller localBiller = (Biller)paramObject;
    int i;
    if (this == localBiller) {
      i = 0;
    } else if (getBillerName() == null) {
      i = -1;
    } else if (localBiller.getBillerName() == null) {
      i = 1;
    } else {
      i = getBillerName().compareTo(localBiller.getBillerName());
    }
    return i;
  }
  
  public int compare(Object paramObject, String paramString)
  {
    int i = 1;
    try
    {
      Biller localBiller = (Biller)paramObject;
      if ((paramString.equals("BILLERID")) && (getBillerID() != null) && (localBiller.getBillerID() != null)) {
        i = getBillerID().compareTo(localBiller.getBillerID());
      } else if ((paramString.equals("BILLERNAME")) && (getBillerName() != null) && (localBiller.getBillerName() != null)) {
        i = getBillerName().compareTo(localBiller.getBillerName());
      } else if ((paramString.equals("PUBLISHERID")) && (getPublisherID() != null) && (localBiller.getPublisherID() != null)) {
        i = getPublisherID().compareTo(localBiller.getPublisherID());
      } else if ((paramString.equals("STATUSCODE")) && (getStatusCode() != null) && (localBiller.getStatusCode() != null)) {
        i = getStatusCode().compareTo(localBiller.getStatusCode());
      } else {
        i = super.compare(paramObject, paramString);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception:", localException);
    }
    return i;
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("BILLERID")) {
      this.c7 = Integer.parseInt(paramString2);
    } else if (paramString1.equals("BILLERNAME")) {
      this.dc = paramString2;
    } else if (paramString1.equals("PUBLISHERID")) {
      this.db = Integer.parseInt(paramString2);
    } else if (paramString1.equals("STATUSCODE")) {
      this.c8 = paramString2;
    } else if (paramString1.equals("PAYEEID")) {
      this.dg = paramString2;
    } else if (paramString1.equals("RESTRICTMESSAGE")) {
      this.dd = paramString2;
    } else if (paramString1.equals("BILLERENROLLURL")) {
      this.dh = paramString2;
    } else if (paramString1.equals("BILLERINFOURL")) {
      this.da = paramString2;
    } else if (paramString1.equals("LOGOURL")) {
      this.de = paramString2;
    } else if (paramString1.equals("VALIDATEURL")) {
      this.c9 = paramString2;
    } else if (paramString1.equals("ACCOUNTFORMAT")) {
      this.di = paramString2;
    } else if (paramString1.equals("ACCOUNTEDITMASK")) {
      this.df = paramString2;
    } else if (paramString1.equals("HELPMESSAGE")) {
      this.dj = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "BILLER");
    XMLHandler.appendTag(localStringBuffer, "BILLERID", String.valueOf(this.c7));
    XMLHandler.appendTag(localStringBuffer, "BILLERNAME", this.dc);
    XMLHandler.appendTag(localStringBuffer, "PUBLISHERID", String.valueOf(this.db));
    XMLHandler.appendTag(localStringBuffer, "STATUSCODE", this.c8);
    XMLHandler.appendTag(localStringBuffer, "PAYEEID", this.dg);
    XMLHandler.appendTag(localStringBuffer, "RESTRICTMESSAGE", this.dd);
    XMLHandler.appendTag(localStringBuffer, "BILLERENROLLURL", this.dh);
    XMLHandler.appendTag(localStringBuffer, "BILLERINFOURL", this.da);
    XMLHandler.appendTag(localStringBuffer, "LOGOURL", this.de);
    XMLHandler.appendTag(localStringBuffer, "VALIDATEURL", this.c9);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTFORMAT", this.di);
    XMLHandler.appendTag(localStringBuffer, "ACCOUNTEDITMASK", this.df);
    XMLHandler.appendTag(localStringBuffer, "HELPMESSAGE", this.dj);
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "BILLER");
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
      Biller.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.Biller
 * JD-Core Version:    0.7.0.1
 */