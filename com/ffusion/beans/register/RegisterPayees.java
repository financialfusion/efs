package com.ffusion.beans.register;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.io.Reader;
import java.util.Iterator;

public class RegisterPayees
  extends FilteredList
{
  public static final String REGISTER_PAYEES = "REGISTER_PAYEES";
  
  public RegisterPayee add()
  {
    RegisterPayee localRegisterPayee = new RegisterPayee();
    add(localRegisterPayee);
    return localRegisterPayee;
  }
  
  public RegisterPayee create()
  {
    RegisterPayee localRegisterPayee = new RegisterPayee();
    return localRegisterPayee;
  }
  
  public RegisterPayee getById(String paramString)
  {
    Object localObject = null;
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterPayee localRegisterPayee = (RegisterPayee)localIterator.next();
      if (localRegisterPayee.getId().equals(paramString))
      {
        localObject = localRegisterPayee;
        break;
      }
    }
    return localObject;
  }
  
  public void removeById(String paramString)
  {
    setFilter("All");
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      RegisterPayee localRegisterPayee = (RegisterPayee)localIterator.next();
      if (localRegisterPayee.getId().equalsIgnoreCase(paramString))
      {
        localIterator.remove();
        break;
      }
    }
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
    XMLHandler.appendBeginTag(localStringBuffer, "REGISTER_PAYEES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((RegisterPayee)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "REGISTER_PAYEES");
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void startXMLParsing(Reader paramReader)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramReader);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  private class a
    extends XMLHandler
  {
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("REGISTER_PAYEE"))
      {
        RegisterPayee localRegisterPayee = RegisterPayees.this.add();
        localRegisterPayee.continueXMLParsing(getHandler());
      }
    }
    
    a(RegisterPayees.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.register.RegisterPayees
 * JD-Core Version:    0.7.0.1
 */