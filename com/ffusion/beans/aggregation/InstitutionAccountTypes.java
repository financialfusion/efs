package com.ffusion.beans.aggregation;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class InstitutionAccountTypes
  extends FilteredList
{
  public static final String INSTITUTIONACCOUNTTYPES = "INSTITUTIONACCOUNTTYPES";
  
  protected InstitutionAccountTypes() {}
  
  public InstitutionAccountTypes(Locale paramLocale)
  {
    super(paramLocale);
    if (paramLocale == null) {
      throw new IllegalArgumentException();
    }
  }
  
  public InstitutionAccountType add()
  {
    InstitutionAccountType localInstitutionAccountType = new InstitutionAccountType(this.locale);
    add(localInstitutionAccountType);
    return localInstitutionAccountType;
  }
  
  public InstitutionAccountType create()
  {
    InstitutionAccountType localInstitutionAccountType = new InstitutionAccountType(this.locale);
    return localInstitutionAccountType;
  }
  
  public InstitutionAccountType getByType(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      InstitutionAccountType localInstitutionAccountType = (InstitutionAccountType)localIterator.next();
      if (localInstitutionAccountType.getType().equalsIgnoreCase(paramString))
      {
        localObject = localInstitutionAccountType;
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
    XMLHandler.appendBeginTag(localStringBuffer, "INSTITUTIONACCOUNTTYPES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((InstitutionAccountType)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "INSTITUTIONACCOUNTTYPES");
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
      if (paramString.equals("INSTITUTIONACCOUNTTYPE"))
      {
        InstitutionAccountType localInstitutionAccountType = InstitutionAccountTypes.this.add();
        localInstitutionAccountType.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.aggregation.InstitutionAccountTypes
 * JD-Core Version:    0.7.0.1
 */