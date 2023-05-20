package com.ffusion.beans.istatements;

import com.ffusion.beans.XMLStrings;
import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

public class Statements
  extends FilteredList
  implements Localeable, Serializable, XMLStrings
{
  protected String datetype = "SHORT";
  
  public Statements() {}
  
  public Statements(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public Object create()
  {
    Statement localStatement = new Statement(this.locale);
    add(localStatement);
    return localStatement;
  }
  
  public Object create(String paramString1, String paramString2)
  {
    Statement localStatement = new Statement(paramString1, paramString2);
    add(localStatement);
    return localStatement;
  }
  
  public Object createNoAdd()
  {
    return new Statement(this.locale);
  }
  
  public boolean add(Object paramObject)
  {
    Statement localStatement = (Statement)paramObject;
    localStatement.setLocale(this.locale);
    return super.add(localStatement);
  }
  
  public Statement getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Statement localStatement = (Statement)localIterator.next();
      if (localStatement.getID().equalsIgnoreCase(paramString))
      {
        localObject = localStatement;
        break;
      }
    }
    return localObject;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Statement localStatement = (Statement)localIterator.next();
      localStatement.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "STATEMENTS");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((Statement)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "STATEMENTS");
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
      throws Exception
    {
      if (paramString.equals("STATEMENT"))
      {
        Statement localStatement = (Statement)Statements.this.create();
        localStatement.setLocale(Statements.this.locale);
        localStatement.setDateFormat(Statements.this.datetype);
        localStatement.continueXMLParsing(getHandler());
      }
      else
      {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.istatements.Statements
 * JD-Core Version:    0.7.0.1
 */