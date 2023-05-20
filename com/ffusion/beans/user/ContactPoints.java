package com.ffusion.beans.user;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Locale;

public class ContactPoints
  extends FilteredList
{
  public static final String CONTACT_POINTS = "ContactPoints";
  
  public ContactPoints(Locale paramLocale)
  {
    setLocale(paramLocale);
  }
  
  public ContactPoints() {}
  
  public boolean equals(Object paramObject)
  {
    if (this == paramObject) {
      return true;
    }
    if (!(paramObject instanceof ContactPoints)) {
      return false;
    }
    ContactPoints localContactPoints = (ContactPoints)paramObject;
    return containsAll(localContactPoints);
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
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ContactPoints");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ContactPoint)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ContactPoints");
    return localStringBuffer.toString();
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public ContactPoint getContactPointByID(int paramInt)
  {
    for (int i = 0; i < size(); i++)
    {
      ContactPoint localContactPoint = (ContactPoint)get(i);
      if (localContactPoint.getContactPointID() == paramInt) {
        return localContactPoint;
      }
    }
    return null;
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
      if (paramString.equals("ContactPoint"))
      {
        ContactPoint localContactPoint = new ContactPoint(ContactPoints.this.locale);
        ContactPoints.this.add(localContactPoint);
        localContactPoint.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.ContactPoints
 * JD-Core Version:    0.7.0.1
 */