package com.ffusion.beans.ach;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

public class ACHOffsetAccounts
  extends FilteredList
  implements XMLable, XMLStrings, Serializable
{
  public ACHOffsetAccounts(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ACHOffsetAccount create()
  {
    ACHOffsetAccount localACHOffsetAccount = new ACHOffsetAccount(this.locale);
    super.add(localACHOffsetAccount);
    return localACHOffsetAccount;
  }
  
  public ACHOffsetAccount create(String paramString1, String paramString2, int paramInt, String paramString3)
  {
    ACHOffsetAccount localACHOffsetAccount = new ACHOffsetAccount(paramString1, paramString2, paramInt, paramString3);
    super.add(localACHOffsetAccount);
    return localACHOffsetAccount;
  }
  
  public ACHOffsetAccount getByID(String paramString)
  {
    Object localObject = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)localIterator.next();
        if (paramString.equals(localACHOffsetAccount.getID()))
        {
          localObject = localACHOffsetAccount;
          break;
        }
      }
    }
    return localObject;
  }
  
  public void removeByID(String paramString)
  {
    Object localObject = null;
    if (paramString != null)
    {
      Iterator localIterator = iterator();
      while (localIterator.hasNext())
      {
        ACHOffsetAccount localACHOffsetAccount = (ACHOffsetAccount)localIterator.next();
        if (paramString.equals(localACHOffsetAccount.getID()))
        {
          localObject = localACHOffsetAccount;
          break;
        }
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
    XMLHandler.appendBeginTag(localStringBuffer, "OFFSETACCOUNTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ACHOffsetAccount)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "OFFSETACCOUNTS");
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
      if (paramString.equals("OFFSETACCOUNT"))
      {
        ACHOffsetAccount localACHOffsetAccount = ACHOffsetAccounts.this.create();
        localACHOffsetAccount.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHOffsetAccounts
 * JD-Core Version:    0.7.0.1
 */