package com.ffusion.beans.ach;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Serializable;
import java.util.Iterator;

public class ACHPayees
  extends FilteredList
  implements XMLable, Serializable
{
  public Object create()
  {
    ACHPayee localACHPayee = new ACHPayee();
    add(localACHPayee);
    return localACHPayee;
  }
  
  public Object createNoAdd()
  {
    return new ACHPayee();
  }
  
  public boolean add(Object paramObject)
  {
    ACHPayee localACHPayee = (ACHPayee)paramObject;
    return super.add(localACHPayee);
  }
  
  public ACHPayee getByName(String paramString)
  {
    return (ACHPayee)getFirstByFilter("Name==" + paramString);
  }
  
  public ACHPayee isPayeeUnique(ACHPayee paramACHPayee)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ACHPayee localACHPayee = (ACHPayee)localIterator.next();
      if ((localACHPayee.getName().equalsIgnoreCase(paramACHPayee.getName())) && ((localACHPayee.getID() == null) || (!localACHPayee.getID().equals(paramACHPayee.getID()))))
      {
        String str1 = localACHPayee.getRoutingNumber();
        String str2 = localACHPayee.getAccountNumberNoSpaces();
        String str3 = localACHPayee.getUserAccountNumber();
        String str4 = localACHPayee.getNickName();
        if ((str4 == null) || (str4.length() == 0)) {
          str4 = localACHPayee.getName();
        }
        String str5 = paramACHPayee.getNickName();
        if ((str5 == null) || (str5.length() == 0)) {
          str5 = paramACHPayee.getName();
        }
        if (str1 == null) {
          str1 = "";
        }
        if (str2 == null) {
          str2 = "";
        }
        if (str3 == null) {
          str3 = "";
        }
        if (((str1.equalsIgnoreCase(paramACHPayee.getRoutingNumber())) && (str2.equalsIgnoreCase(paramACHPayee.getAccountNumberNoSpaces())) && (str3.equalsIgnoreCase(paramACHPayee.getUserAccountNumber()))) || (str4.equalsIgnoreCase(str5)))
        {
          if ((localACHPayee.getPayeeGroupValue() == paramACHPayee.getPayeeGroupValue()) && (paramACHPayee.getPayeeGroupValue() != 2)) {
            return localACHPayee;
          }
          if ((localACHPayee.getCompanyID().equals(paramACHPayee.getCompanyID())) && (paramACHPayee.getPayeeGroupValue() == 2)) {
            return localACHPayee;
          }
        }
      }
    }
    return null;
  }
  
  public ACHPayee getByID(String paramString)
  {
    return (ACHPayee)getFirstByFilter("ID==" + paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ACHPAYEES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ACHPayee)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ACHPAYEES");
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
      if (paramString.equals("ACHPAYEE"))
      {
        ACHPayee localACHPayee = new ACHPayee();
        ACHPayees.this.add(localACHPayee);
        localACHPayee.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHPayees
 * JD-Core Version:    0.7.0.1
 */