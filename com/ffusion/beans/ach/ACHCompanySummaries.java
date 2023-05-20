package com.ffusion.beans.ach;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;
import java.util.Locale;

public class ACHCompanySummaries
  extends FilteredList
  implements XMLStrings
{
  public static final String ACHCOMPANYSUMMARIES = "ACHCOMPANYSUMMARIES";
  public static final String ACHCOMPANYSUMMARY = "ACHCOMPANYSUMMARY";
  
  public ACHCompanySummaries(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public ACHCompanySummary create()
  {
    ACHCompanySummary localACHCompanySummary = new ACHCompanySummary(this.locale);
    super.add(localACHCompanySummary);
    return localACHCompanySummary;
  }
  
  public ACHCompanySummary create(String paramString)
  {
    ACHCompanySummary localACHCompanySummary = new ACHCompanySummary(paramString);
    super.add(localACHCompanySummary);
    return localACHCompanySummary;
  }
  
  public ACHCompanySummary getByCompanyID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      ACHCompanySummary localACHCompanySummary = (ACHCompanySummary)localIterator.next();
      if (localACHCompanySummary.getCompanyID().equals(paramString))
      {
        localObject = localACHCompanySummary;
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
    XMLHandler.appendBeginTag(localStringBuffer, "ACHCOMPANYSUMMARIES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((ACHCompanySummary)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ACHCOMPANYSUMMARIES");
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
      if (paramString.equals("ACHCOMPANYSUMMARY"))
      {
        ACHCompanySummary localACHCompanySummary = ACHCompanySummaries.this.create();
        localACHCompanySummary.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.ACHCompanySummaries
 * JD-Core Version:    0.7.0.1
 */