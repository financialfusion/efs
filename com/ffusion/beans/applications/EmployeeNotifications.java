package com.ffusion.beans.applications;

import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class EmployeeNotifications
  extends ArrayList
  implements Serializable
{
  public static final String EMPLOYEE_NOTIFY_GROUP = "EMPLOYEE_NOTIFY_GROUP";
  
  public EmployeeNotification add()
  {
    EmployeeNotification localEmployeeNotification = new EmployeeNotification();
    add(localEmployeeNotification);
    return localEmployeeNotification;
  }
  
  public EmployeeNotification create()
  {
    EmployeeNotification localEmployeeNotification = new EmployeeNotification();
    return localEmployeeNotification;
  }
  
  public EmployeeNotification getByProductID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      EmployeeNotification localEmployeeNotification = (EmployeeNotification)localIterator.next();
      if (paramString.equalsIgnoreCase(localEmployeeNotification.getProductID()))
      {
        localObject = localEmployeeNotification;
        break;
      }
    }
    return localObject;
  }
  
  public EmployeeNotification getByEmployeeID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      EmployeeNotification localEmployeeNotification = (EmployeeNotification)localIterator.next();
      if (paramString.equalsIgnoreCase(localEmployeeNotification.getEmployeeID()))
      {
        localObject = localEmployeeNotification;
        break;
      }
    }
    return localObject;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "EMPLOYEE_NOTIFY_GROUP");
    EmployeeNotification localEmployeeNotification = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localEmployeeNotification = (EmployeeNotification)localIterator.next();
      localStringBuffer.append(localEmployeeNotification.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "EMPLOYEE_NOTIFY_GROUP");
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
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("EMPLOYEE_NOTIFY")) {
        EmployeeNotifications.this.add().continueXMLParsing(getHandler());
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.EmployeeNotifications
 * JD-Core Version:    0.7.0.1
 */