package com.ffusion.beans.portal;

import com.ffusion.beans.ToXml;
import com.ffusion.util.XMLHandler;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class Advisors
  extends ArrayList
  implements ToXml
{
  public static final String ADVISORS = "ADVISORS";
  private String jdField_int;
  
  public void setContainsAdvisor(String paramString)
  {
    this.jdField_int = paramString;
  }
  
  public String getContainsAdvisor()
  {
    if (getAdvisor(this.jdField_int) == null) {
      return "false";
    }
    return "true";
  }
  
  public void setAddAdvisor(String paramString)
  {
    if (getAdvisor(paramString) == null)
    {
      Advisor localAdvisor = new Advisor();
      localAdvisor.setCategory(paramString);
      add(localAdvisor);
    }
  }
  
  public Advisor getAdvisor(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Advisor localAdvisor = (Advisor)localIterator.next();
      if (localAdvisor.getCategory().equalsIgnoreCase(paramString)) {
        return localAdvisor;
      }
    }
    return null;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "ADVISORS");
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localStringBuffer.append(((Advisor)localIterator.next()).toXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "ADVISORS");
    return XMLHandler.format(localStringBuffer.toString());
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a(null));
  }
  
  public void startXMLParsing(Reader paramReader)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new a(null), paramReader);
    }
    catch (Exception localException) {}
  }
  
  private class a
    extends XMLHandler
  {
    private boolean jdField_try = false;
    private String jdField_int = "";
    private String jdField_new = "";
    
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("ADVISORS"))
      {
        this.jdField_try = true;
      }
      else if ((this.jdField_try) && (paramString.equalsIgnoreCase("ADVISOR")))
      {
        Advisor localAdvisor = new Advisor();
        if (this.jdField_int.equalsIgnoreCase("CATEGORY")) {
          localAdvisor.setCategory(this.jdField_new);
        }
        localAdvisor.continueXMLParsing(getHandler());
        Advisors.this.add(localAdvisor);
      }
      this.jdField_int = "";
      this.jdField_new = "";
    }
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("ADVISORS")) {
        this.jdField_try = false;
      }
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      this.jdField_int = paramString1;
      this.jdField_new = paramString2;
    }
    
    a(Advisors.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.Advisors
 * JD-Core Version:    0.7.0.1
 */