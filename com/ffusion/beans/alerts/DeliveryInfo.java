package com.ffusion.beans.alerts;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.logging.DebugLog;
import java.io.Serializable;
import java.text.Collator;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Properties;
import java.util.StringTokenizer;

public class DeliveryInfo
  extends ExtendABean
  implements Serializable
{
  public static final String DELIVERYINFO = "DELIVERYINFO";
  public static final String CHANNELNAME = "CHANNELNAME";
  public static final String ORDER = "ORDER";
  public static final String MAXDELAY = "MAXDELAY";
  public static final String SUSPENDED = "SUSPENDED";
  public static final int DELIVERY_ORDER_PRIMARY = 1;
  public static final int DELIVERY_ORDER_SECONDARY = 2;
  public static final int DELIVERY_ORDER_OFF = 0;
  protected String id;
  protected String channelName;
  protected int order;
  protected int maxDelay;
  protected Properties properties;
  protected boolean suspended;
  protected String propertyKey;
  
  public DeliveryInfo()
  {
    this.properties = new Properties();
  }
  
  public DeliveryInfo(Locale paramLocale)
  {
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.properties = new Properties();
    setLocale(paramLocale);
  }
  
  public String getID()
  {
    return this.id;
  }
  
  public void setID(String paramString)
  {
    this.id = paramString;
  }
  
  public String getChannelName()
  {
    return this.channelName;
  }
  
  public void setChannelName(String paramString)
  {
    this.channelName = paramString;
  }
  
  public String getOrder()
  {
    return String.valueOf(this.order);
  }
  
  public int getOrderValue()
  {
    return this.order;
  }
  
  public void setOrder(String paramString)
  {
    if (paramString != null) {
      this.order = Integer.parseInt(paramString);
    }
  }
  
  public void setOrder(int paramInt)
  {
    this.order = paramInt;
  }
  
  public String getMaxDelay()
  {
    return String.valueOf(this.maxDelay);
  }
  
  public int getMaxDelayValue()
  {
    return this.maxDelay;
  }
  
  public void setMaxDelay(String paramString)
  {
    if (paramString != null) {
      this.maxDelay = Integer.parseInt(paramString);
    }
  }
  
  public void setMaxDelay(int paramInt)
  {
    this.maxDelay = paramInt;
  }
  
  public String getProperty()
  {
    if ((this.properties != null) && (this.propertyKey != null)) {
      return this.properties.getProperty(this.propertyKey);
    }
    return "";
  }
  
  public Properties getPropertiesValue()
  {
    return this.properties;
  }
  
  public String getPropertiesString()
  {
    if (this.properties != null)
    {
      String str2 = "";
      Enumeration localEnumeration = this.properties.propertyNames();
      while (localEnumeration.hasMoreElements())
      {
        String str1 = (String)localEnumeration.nextElement();
        str2 = str2 + str1 + "=" + this.properties.getProperty(str1) + ",";
      }
      if (str2.length() > 0) {
        str2 = str2.substring(0, str2.length() - 1);
      }
      return str2;
    }
    return "";
  }
  
  public String getPropertyKey()
  {
    return this.propertyKey;
  }
  
  public void setPropertyKey(String paramString)
  {
    this.propertyKey = paramString;
  }
  
  public void setPropertyValue(String paramString)
  {
    if (this.propertyKey != null) {
      this.properties.put(this.propertyKey, paramString);
    }
  }
  
  public void setProperties(Properties paramProperties)
  {
    this.properties = paramProperties;
  }
  
  public void setPropertiesString(String paramString)
  {
    this.properties.clear();
    String str1 = "";
    String str2 = "";
    StringTokenizer localStringTokenizer1 = new StringTokenizer(paramString, ",");
    while (localStringTokenizer1.hasMoreTokens())
    {
      StringTokenizer localStringTokenizer2 = new StringTokenizer(localStringTokenizer1.nextToken(), "=");
      while (localStringTokenizer2.hasMoreTokens()) {
        if (localStringTokenizer2.countTokens() % 2 == 0) {
          str1 = localStringTokenizer2.nextToken();
        } else {
          str2 = localStringTokenizer2.nextToken();
        }
      }
      this.properties.put(str1, str2);
    }
  }
  
  public String getSuspended()
  {
    return String.valueOf(this.suspended);
  }
  
  public boolean getSuspendedValue()
  {
    return this.suspended;
  }
  
  public void setSuspended(String paramString)
  {
    this.suspended = Boolean.valueOf(paramString).booleanValue();
  }
  
  public void setSuspended(boolean paramBoolean)
  {
    this.suspended = paramBoolean;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "ID");
  }
  
  public int compare(Object paramObject, String paramString)
  {
    Collator localCollator = doGetCollator();
    DeliveryInfo localDeliveryInfo = (DeliveryInfo)paramObject;
    int i = 1;
    if ((paramString.equals("ID")) && (this.id != null) && (localDeliveryInfo.getID() != null)) {
      i = localCollator.compare(this.id, localDeliveryInfo.getID());
    } else if ((paramString.equals("CHANNELNAME")) && (this.channelName != null) && (localDeliveryInfo.getChannelName() != null)) {
      i = localCollator.compare(this.channelName, localDeliveryInfo.getChannelName());
    } else if (paramString.equals("ORDER")) {
      i = getOrderValue() - localDeliveryInfo.getOrderValue();
    } else if (paramString.equals("MAXDELAY")) {
      i = getMaxDelayValue() - localDeliveryInfo.getMaxDelayValue();
    } else if (paramString.equals("PROPERTIES"))
    {
      if (getPropertiesValue().equals(localDeliveryInfo.getPropertiesValue())) {
        i = 0;
      } else {
        i = 1;
      }
    }
    else if (paramString.equals("SUSPENDED"))
    {
      if (this.suspended == localDeliveryInfo.getSuspendedValue()) {
        i = 0;
      } else {
        i = 1;
      }
    }
    else {
      i = super.compare(paramObject, paramString);
    }
    return i;
  }
  
  public void set(DeliveryInfo paramDeliveryInfo)
  {
    setID(paramDeliveryInfo.getID());
    setChannelName(paramDeliveryInfo.getChannelName());
    setOrder(paramDeliveryInfo.getOrderValue());
    setMaxDelay(paramDeliveryInfo.getMaxDelayValue());
    if (paramDeliveryInfo.getPropertiesValue() != null) {
      setProperties((Properties)paramDeliveryInfo.getPropertiesValue().clone());
    } else {
      setProperties((Properties)null);
    }
    setSuspended(paramDeliveryInfo.getSuspended());
    super.set(paramDeliveryInfo);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    try
    {
      if (paramString1.equals("ID")) {
        this.id = paramString2;
      } else if (paramString1.equals("CHANNELNAME")) {
        this.channelName = paramString2;
      } else if (paramString1.equals("ORDER")) {
        this.order = Integer.parseInt(paramString2);
      } else if (paramString1.equals("MAXDELAY")) {
        this.maxDelay = Integer.parseInt(paramString2);
      } else if (paramString1.equals("PROPERTIES")) {
        setPropertiesString(paramString2);
      } else if (paramString1.equals("SUSPENDED")) {
        this.suspended = Boolean.valueOf(paramString2).booleanValue();
      } else {
        bool = super.set(paramString1, paramString2);
      }
    }
    catch (Exception localException)
    {
      DebugLog.throwing("Exception:", localException);
    }
    return bool;
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
    XMLHandler.appendBeginTag(localStringBuffer, "DELIVERYINFO");
    XMLHandler.appendTag(localStringBuffer, "ID", this.id);
    XMLHandler.appendTag(localStringBuffer, "CHANNELNAME", this.channelName);
    XMLHandler.appendTag(localStringBuffer, "ORDER", this.order);
    XMLHandler.appendTag(localStringBuffer, "MAXDELAY", this.maxDelay);
    if (this.properties != null) {
      XMLHandler.appendTag(localStringBuffer, "PROPERTIES", getPropertiesString());
    }
    XMLHandler.appendTag(localStringBuffer, "SUSPENDED", String.valueOf(this.suspended));
    localStringBuffer.append(super.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "DELIVERYINFO");
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
      DeliveryInfo.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.alerts.DeliveryInfo
 * JD-Core Version:    0.7.0.1
 */