package com.ffusion.beans.portal;

import com.ffusion.beans.ToXml;
import com.ffusion.util.XMLHandler;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class Calculators
  extends ArrayList
  implements ToXml
{
  public static final String CALCULATORS = "CALCULATORS";
  public static final String CATEGORY = "CATEGORY";
  private String jdField_do = "";
  private boolean jdField_if = false;
  private String a;
  
  public void setAddCalc(String paramString)
  {
    if (getCalculator(paramString) == null)
    {
      Calculator localCalculator = new Calculator();
      localCalculator.setID(paramString);
      add(localCalculator);
    }
  }
  
  public void setContainsCalc(String paramString)
  {
    this.a = paramString;
  }
  
  public String getContainsCalc()
  {
    if (getCalculator(this.a) == null) {
      return "false";
    }
    return "true";
  }
  
  public void setInCalculators(boolean paramBoolean)
  {
    this.jdField_if = paramBoolean;
  }
  
  public void setCategory(String paramString)
  {
    this.jdField_do = paramString;
  }
  
  public String getCategory()
  {
    return this.jdField_do;
  }
  
  public Calculator getCalculator(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Calculator localCalculator = (Calculator)localIterator.next();
      if (localCalculator.getID().equalsIgnoreCase(paramString)) {
        return localCalculator;
      }
    }
    return null;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.openTag(localStringBuffer, "CALCULATORS");
    XMLHandler.appendAttribute(localStringBuffer, "CATEGORY", this.jdField_do);
    XMLHandler.closeTag(localStringBuffer, false);
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localStringBuffer.append(((Calculator)localIterator.next()).toXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "CALCULATORS");
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
    private boolean jdField_int = false;
    private String jdField_new = "";
    private String jdField_try = "";
    
    private a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("CALCULATORS"))
      {
        this.jdField_int = true;
        if (this.jdField_new.equalsIgnoreCase("CATEGORY")) {
          Calculators.this.jdField_do = this.jdField_try;
        }
      }
      else if (((Calculators.this.jdField_if) || (this.jdField_int)) && (paramString.equalsIgnoreCase("CALCULATOR")))
      {
        Calculator localCalculator = new Calculator();
        if (this.jdField_new.equalsIgnoreCase("ID")) {
          localCalculator.setID(this.jdField_try);
        }
        localCalculator.continueXMLParsing(getHandler());
        Calculators.this.add(localCalculator);
      }
      this.jdField_new = "";
      this.jdField_try = "";
    }
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("CALCULATORS"))
      {
        this.jdField_int = false;
        Calculators.this.jdField_if = false;
      }
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      this.jdField_new = paramString1;
      this.jdField_try = paramString2;
    }
    
    a(Calculators.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.Calculators
 * JD-Core Version:    0.7.0.1
 */