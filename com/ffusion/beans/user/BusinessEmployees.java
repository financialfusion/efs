package com.ffusion.beans.user;

import com.ffusion.beans.IdCollection;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class BusinessEmployees
  extends FilteredList
  implements IdCollection
{
  public static final String BUSINESSEMPLOYEELIST = "BUSINESSEMPLOYEELIST";
  protected String datetype = "SHORT";
  
  protected BusinessEmployees() {}
  
  public BusinessEmployees(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
  }
  
  public BusinessEmployee add()
  {
    BusinessEmployee localBusinessEmployee = new BusinessEmployee(this.locale);
    add(localBusinessEmployee);
    return localBusinessEmployee;
  }
  
  public boolean add(Object paramObject)
  {
    BusinessEmployee localBusinessEmployee = (BusinessEmployee)paramObject;
    return super.add(localBusinessEmployee);
  }
  
  public BusinessEmployee create()
  {
    BusinessEmployee localBusinessEmployee = new BusinessEmployee(this.locale);
    return localBusinessEmployee;
  }
  
  public void set(BusinessEmployees paramBusinessEmployees)
  {
    Iterator localIterator = paramBusinessEmployees.iterator();
    while (localIterator.hasNext())
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)localIterator.next();
      if (localBusinessEmployee != null) {
        add(localBusinessEmployee);
      }
    }
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)localIterator.next();
      localBusinessEmployee.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public BusinessEmployee getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)localIterator.next();
      if (localBusinessEmployee.getId().equalsIgnoreCase(paramString))
      {
        localObject = localBusinessEmployee;
        break;
      }
    }
    return localObject;
  }
  
  public Object getElementByID(String paramString)
  {
    return getByID(paramString);
  }
  
  public BusinessEmployee getByUserName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)localIterator.next();
      if (localBusinessEmployee.getUserName().equalsIgnoreCase(paramString))
      {
        localObject = localBusinessEmployee;
        break;
      }
    }
    return localObject;
  }
  
  public void removeByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)localIterator.next();
      if (localBusinessEmployee.getId().equalsIgnoreCase(paramString))
      {
        localObject = localBusinessEmployee;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
    }
  }
  
  public BusinessEmployees getPrimaryUsers()
  {
    BusinessEmployees localBusinessEmployees = new BusinessEmployees(this.locale);
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)localIterator.next();
      String str = localBusinessEmployee.getPrimaryUser();
      if ((str != null) && (!str.equals("0"))) {
        localBusinessEmployees.add(localBusinessEmployee);
      }
    }
    return localBusinessEmployees;
  }
  
  public BusinessEmployee getPrimaryUser1()
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)localIterator.next();
      if (localBusinessEmployee.getPrimaryUser().equals("1"))
      {
        localObject = localBusinessEmployee;
        break;
      }
    }
    return localObject;
  }
  
  public BusinessEmployee getPrimaryUser2()
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      BusinessEmployee localBusinessEmployee = (BusinessEmployee)localIterator.next();
      if (localBusinessEmployee.getPrimaryUser().equals("2"))
      {
        localObject = localBusinessEmployee;
        break;
      }
    }
    return localObject;
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
    XMLHandler.appendBeginTag(localStringBuffer, "BUSINESSEMPLOYEELIST");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((BusinessEmployee)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "BUSINESSEMPLOYEELIST");
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
      if (paramString.equals("BUSINESSEMPLOYEEINFO"))
      {
        BusinessEmployee localBusinessEmployee = BusinessEmployees.this.add();
        localBusinessEmployee.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.BusinessEmployees
 * JD-Core Version:    0.7.0.1
 */