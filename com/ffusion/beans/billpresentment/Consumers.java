package com.ffusion.beans.billpresentment;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class Consumers
  extends FilteredList
{
  public static final String CONSUMERS = "CONSUMERS";
  public static final String FIRSTNAME = "FIRSTNAME";
  public static final String LASTNAME = "LASTNAME";
  public static final String STATUSCODE = "STATUSCODE";
  public static final String CITY = "CITY";
  public static final String STATE = "STATE";
  public static final String COUNTRY = "COUNTRY";
  public static final String ZIPCODE = "ZIPCODE";
  private String Km;
  private String Kn;
  private String Kl;
  private String Kk;
  private String Ki;
  private String Kj;
  private String Kg;
  private String Kh;
  
  protected Consumers()
  {
    this.Kh = "SHORT";
  }
  
  public Consumers(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.Kh = "SHORT";
  }
  
  public void setDateFormat(String paramString)
  {
    this.Kh = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Consumer localConsumer = (Consumer)localIterator.next();
      localConsumer.setDateFormat(this.Kh);
    }
  }
  
  public String getDateFormat()
  {
    return this.Kh;
  }
  
  public final void setFirstName(String paramString)
  {
    this.Km = paramString;
  }
  
  public final String getFirstName()
  {
    return this.Km;
  }
  
  public final void setLastName(String paramString)
  {
    this.Kn = paramString;
  }
  
  public final String getLastName()
  {
    return this.Kn;
  }
  
  public final void setStatusCode(String paramString)
  {
    this.Kl = paramString;
  }
  
  public final String getStatusCode()
  {
    return this.Kl;
  }
  
  public final void setCity(String paramString)
  {
    this.Kk = paramString;
  }
  
  public final String getCity()
  {
    return this.Kk;
  }
  
  public final void setState(String paramString)
  {
    this.Ki = paramString;
  }
  
  public final String getState()
  {
    return this.Ki;
  }
  
  public final void setCountry(String paramString)
  {
    this.Kj = paramString;
  }
  
  public final String getCountry()
  {
    return this.Kj;
  }
  
  public final void setZipCode(String paramString)
  {
    this.Kg = paramString;
  }
  
  public final String getZipCode()
  {
    return this.Kg;
  }
  
  public Consumer add()
  {
    Consumer localConsumer = new Consumer(this.locale);
    add(localConsumer);
    return localConsumer;
  }
  
  public Consumer create()
  {
    return new Consumer(this.locale);
  }
  
  public Consumer getByConsumerID(long paramLong)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Consumer localConsumer = (Consumer)localIterator.next();
      if (localConsumer.getConsumerIDValue() == paramLong)
      {
        localObject = localConsumer;
        break;
      }
    }
    return localObject;
  }
  
  public Consumer getByTaxID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Consumer localConsumer = (Consumer)localIterator.next();
      if (paramString.equals(localConsumer.getTaxID()))
      {
        localObject = localConsumer;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByConsumerID(long paramLong)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Consumer localConsumer = (Consumer)localIterator.next();
      if (localConsumer.getConsumerIDValue() == paramLong)
      {
        localObject = localConsumer;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
  
  public void set(Consumers paramConsumers)
  {
    setFirstName(paramConsumers.getFirstName());
    setLastName(paramConsumers.getLastName());
    setStatusCode(paramConsumers.getStatusCode());
    setCity(paramConsumers.getCity());
    setState(paramConsumers.getState());
    setCountry(paramConsumers.getCountry());
    setZipCode(paramConsumers.getZipCode());
    addAll(paramConsumers);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equals("FIRSTNAME")) {
      setFirstName(paramString2);
    } else if (paramString1.equals("LASTNAME")) {
      setLastName(paramString2);
    } else if (paramString1.equals("STATUSCODE")) {
      setStatusCode(paramString2);
    } else if (paramString1.equals("CITY")) {
      setCity(paramString2);
    } else if (paramString1.equals("STATE")) {
      setState(paramString2);
    } else if (paramString1.equals("COUNTRY")) {
      setCountry(paramString2);
    } else if (paramString1.equals("ZIPCODE")) {
      setZipCode(paramString2);
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
    XMLHandler.appendBeginTag(localStringBuffer, "CONSUMERS");
    XMLHandler.appendTag(localStringBuffer, "FIRSTNAME", this.Km);
    XMLHandler.appendTag(localStringBuffer, "LASTNAME", this.Kn);
    XMLHandler.appendTag(localStringBuffer, "STATUSCODE", this.Kl);
    XMLHandler.appendTag(localStringBuffer, "CITY", this.Kk);
    XMLHandler.appendTag(localStringBuffer, "STATE", this.Ki);
    XMLHandler.appendTag(localStringBuffer, "COUNTRY", this.Kj);
    XMLHandler.appendTag(localStringBuffer, "ZIPCODE", this.Kg);
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Consumer)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "CONSUMERS");
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
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("CONSUMER"))
      {
        Consumer localConsumer = new Consumer();
        Consumers.this.add(localConsumer);
        localConsumer.continueXMLParsing(getHandler());
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      if (str.length() > 0) {
        Consumers.this.set(getElement(), str);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.Consumers
 * JD-Core Version:    0.7.0.1
 */