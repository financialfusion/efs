package com.ffusion.beans.billpay;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;

public class Payees
  extends FilteredList
  implements XMLable
{
  public static final String PAYEES = "PAYEES";
  protected String _sortImageAsc = null;
  protected String _sortImageDesc = null;
  protected String _noSortImage = null;
  protected String _sortCriterion = null;
  
  public Payee create()
  {
    Payee localPayee = new Payee();
    add(localPayee);
    return localPayee;
  }
  
  public Payee createNoAdd()
  {
    Payee localPayee = new Payee();
    return localPayee;
  }
  
  public boolean add(Object paramObject)
  {
    Payee localPayee = (Payee)paramObject;
    return super.add(localPayee);
  }
  
  public Payee getByID(String paramString)
  {
    return (Payee)getFirstByFilter("ID=" + paramString);
  }
  
  public Payee getByHostID(String paramString)
  {
    return (Payee)getFirstByFilter("HostID=" + paramString);
  }
  
  public Payee getByTransactionID(String paramString)
  {
    return (Payee)getFirstByFilter("TransactionID=" + paramString);
  }
  
  public Payee getByName(String paramString)
  {
    return (Payee)getFirstByFilter("Name==" + paramString);
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
    XMLHandler.appendBeginTag(localStringBuffer, "PAYEES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Payee)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "PAYEES");
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
  
  public String getSortImage()
  {
    String str = getSortedBy();
    if (str == null) {
      return this._sortImageAsc;
    }
    if (!str.startsWith(this._sortCriterion)) {
      return this._noSortImage;
    }
    if (str.indexOf("REVERSE") != -1) {
      return this._sortImageDesc;
    }
    return this._sortImageAsc;
  }
  
  public void setSortImageAsc(String paramString)
  {
    this._sortImageAsc = paramString;
  }
  
  public void setSortImageDesc(String paramString)
  {
    this._sortImageDesc = paramString;
  }
  
  public void setNoSortImage(String paramString)
  {
    this._noSortImage = paramString;
  }
  
  public void setSortCriterion(String paramString)
  {
    this._sortCriterion = paramString;
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
      if (paramString.equals("PAYEE"))
      {
        Payee localPayee = Payees.this.create();
        localPayee.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpay.Payees
 * JD-Core Version:    0.7.0.1
 */