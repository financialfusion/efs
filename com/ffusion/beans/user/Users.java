package com.ffusion.beans.user;

import com.ffusion.beans.IdCollection;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class Users
  extends FilteredList
  implements IdCollection
{
  public static final String USERLIST = "USERLIST";
  protected String datetype = "SHORT";
  
  public Users() {}
  
  public Users(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
    this.datetype = "SHORT";
  }
  
  public User add()
  {
    User localUser = new User(this.locale);
    add(localUser);
    return localUser;
  }
  
  public boolean add(Object paramObject)
  {
    User localUser = (User)paramObject;
    return super.add(localUser);
  }
  
  public void setUniquely(FilteredList paramFilteredList)
  {
    Object localObject = null;
    User localUser = null;
    Iterator localIterator = paramFilteredList.iterator();
    while (localIterator.hasNext())
    {
      localObject = localIterator.next();
      if ((localObject instanceof User))
      {
        localUser = (User)localObject;
        if (((localUser == null) && (!contains(null))) || (getByID(localUser.getId()) == null)) {
          add(localUser);
        }
      }
    }
  }
  
  public User create()
  {
    User localUser = new User(this.locale);
    return localUser;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      User localUser = (User)localIterator.next();
      localUser.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public User getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      User localUser = (User)localIterator.next();
      if (localUser.getId().equalsIgnoreCase(paramString))
      {
        localObject = localUser;
        break;
      }
    }
    return localObject;
  }
  
  public Object getElementByID(String paramString)
  {
    return getByID(paramString);
  }
  
  public User getByUserName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      User localUser = (User)localIterator.next();
      if (localUser.getUserName().equalsIgnoreCase(paramString))
      {
        localObject = localUser;
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
      User localUser = (User)localIterator.next();
      if (localUser.getId().equalsIgnoreCase(paramString))
      {
        localObject = localUser;
        break;
      }
    }
    if (localObject != null) {
      remove(localObject);
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
    XMLHandler.appendBeginTag(localStringBuffer, "USERLIST");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((User)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "USERLIST");
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
      if (paramString.equals("USERINFO"))
      {
        User localUser = Users.this.add();
        localUser.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.user.Users
 * JD-Core Version:    0.7.0.1
 */