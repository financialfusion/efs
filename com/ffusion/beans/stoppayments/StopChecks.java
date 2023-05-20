package com.ffusion.beans.stoppayments;

import com.ffusion.beans.accounts.Accounts;
import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

public class StopChecks
  extends FilteredList
  implements Localeable, XMLable, Serializable
{
  public static final String STOPCHECKS = "STOPCHECKS";
  protected String dateformat = "SHORT";
  
  public StopChecks() {}
  
  public StopChecks(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.dateformat = "SHORT";
  }
  
  public void setDateFormat(String paramString)
  {
    this.dateformat = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      StopCheck localStopCheck = (StopCheck)localIterator.next();
      localStopCheck.setDateFormat(this.dateformat);
    }
  }
  
  public String getDateFormat()
  {
    return this.dateformat;
  }
  
  public Object create()
  {
    StopCheck localStopCheck = new StopCheck(this.locale);
    add(localStopCheck);
    return localStopCheck;
  }
  
  public Object createNoAdd()
  {
    StopCheck localStopCheck = new StopCheck(this.locale);
    return localStopCheck;
  }
  
  public boolean add(Object paramObject)
  {
    StopCheck localStopCheck = (StopCheck)paramObject;
    localStopCheck.setLocale(this.locale);
    return super.add(localStopCheck);
  }
  
  public StopCheck getByID(String paramString)
  {
    return (StopCheck)getFirstByFilter("ID=" + paramString);
  }
  
  public boolean equals(Object paramObject)
  {
    if (!(paramObject instanceof StopChecks)) {
      return false;
    }
    StopChecks localStopChecks = (StopChecks)paramObject;
    int i = 1;
    if ((this.locale != null) && (localStopChecks.locale != null))
    {
      if (!this.locale.equals(localStopChecks.locale)) {
        return false;
      }
    }
    else
    {
      if ((this.locale != null) && (localStopChecks.locale == null)) {
        return false;
      }
      if ((this.locale == null) && (localStopChecks.locale != null)) {
        return false;
      }
    }
    if ((getDateFormat() != null) && (localStopChecks.getDateFormat() != null))
    {
      i = getDateFormat().compareTo(localStopChecks.getDateFormat());
      if (i != 0) {
        return false;
      }
    }
    else
    {
      if ((getDateFormat() != null) && (localStopChecks.getDateFormat() == null)) {
        return false;
      }
      if ((getDateFormat() == null) && (localStopChecks.getDateFormat() != null)) {
        return false;
      }
    }
    if (!containsAll(localStopChecks)) {
      return false;
    }
    return localStopChecks.containsAll(this);
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString, Accounts paramAccounts)
  {
    setXML(paramString, paramAccounts);
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "STOPCHECKS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((StopCheck)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "STOPCHECKS");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString, Accounts paramAccounts)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(paramAccounts), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void setXML(String paramString)
  {
    setXML(paramString, null);
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, Accounts paramAccounts)
  {
    paramXMLHandler.continueWith(new a(paramAccounts));
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  class a
    extends XMLHandler
  {
    Accounts jdField_int;
    
    public a(Accounts paramAccounts)
    {
      this.jdField_int = paramAccounts;
    }
    
    public void startElement(String paramString)
    {
      if (paramString.equals("STOPCHECK"))
      {
        StopCheck localStopCheck = (StopCheck)StopChecks.this.createNoAdd();
        StopChecks.this.add(localStopCheck);
        localStopCheck.continueXMLParsing(getHandler(), this.jdField_int);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.stoppayments.StopChecks
 * JD-Core Version:    0.7.0.1
 */