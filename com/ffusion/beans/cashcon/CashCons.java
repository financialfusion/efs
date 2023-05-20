package com.ffusion.beans.cashcon;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

public class CashCons
  extends FilteredList
  implements XMLable, Serializable
{
  protected String datetype = "SHORT";
  
  public CashCons() {}
  
  public CashCons(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public Object createConcentration()
  {
    Object localObject = createConcentrationNoAdd();
    add(localObject);
    return localObject;
  }
  
  public Object createConcentrationNoAdd()
  {
    CashCon localCashCon = new CashCon();
    String str = localCashCon.getID();
    if ((str == null) || (str.length() == 0)) {
      localCashCon.setID(Long.toString(System.currentTimeMillis()));
    }
    localCashCon.setLocale(this.locale);
    localCashCon.setDateFormat(this.datetype);
    return localCashCon;
  }
  
  public Object createDisbursement()
  {
    Object localObject = createDisbursementNoAdd();
    add(localObject);
    return localObject;
  }
  
  public Object createDisbursementNoAdd()
  {
    CashCon localCashCon = new CashCon();
    localCashCon.setType(16);
    String str = localCashCon.getID();
    if ((str == null) || (str.length() == 0)) {
      localCashCon.setID(Long.toString(System.currentTimeMillis()));
    }
    localCashCon.setLocale(this.locale);
    localCashCon.setDateFormat(this.datetype);
    return localCashCon;
  }
  
  public boolean add(Object paramObject)
  {
    CashCon localCashCon = (CashCon)paramObject;
    localCashCon.setLocale(this.locale);
    localCashCon.setDateFormat(this.datetype);
    return super.add(localCashCon);
  }
  
  public CashCon getByID(String paramString)
  {
    Object localObject = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        CashCon localCashCon = (CashCon)localIterator.next();
        if (paramString.equals(localCashCon.getID()))
        {
          localObject = localCashCon;
          break;
        }
      }
    }
    return localObject;
  }
  
  public CashCon getByTrackingID(String paramString)
  {
    Object localObject = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        CashCon localCashCon = (CashCon)localIterator.next();
        if (paramString.equals(localCashCon.getTrackingID()))
        {
          localObject = localCashCon;
          break;
        }
      }
    }
    return localObject;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      CashCon localCashCon = (CashCon)localIterator.next();
      localCashCon.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "CASHCONS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((CashCon)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "CASHCONS");
    return localStringBuffer.toString();
  }
  
  public String toXML()
  {
    return getXML();
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
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
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
      if (paramString.equals("CASHCONS"))
      {
        CashCon localCashCon = new CashCon();
        CashCons.this.add(localCashCon);
        localCashCon.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.cashcon.CashCons
 * JD-Core Version:    0.7.0.1
 */