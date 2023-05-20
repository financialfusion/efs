package com.ffusion.beans.portal;

import com.ffusion.util.XMLHandler;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

public class CalculatorCategories
  extends ArrayList
{
  public static final String CALCULATOR_CATEGORIES = "CALCULATOR_CATEGORIES";
  
  public Calculator getCalculator(String paramString)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      Calculators localCalculators = (Calculators)localIterator.next();
      Calculator localCalculator = null;
      if ((localCalculator = localCalculators.getCalculator(paramString)) != null) {
        return localCalculator;
      }
    }
    return null;
  }
  
  public String toXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "CALCULATOR_CATEGORIES");
    Iterator localIterator = iterator();
    while (localIterator.hasNext()) {
      localStringBuffer.append(((Calculators)localIterator.next()).toXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "CALCULATOR_CATEGORIES");
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
      if (paramString.equalsIgnoreCase("CALCULATOR_CATEGORIES"))
      {
        this.jdField_try = true;
      }
      else if ((this.jdField_try) && (paramString.equalsIgnoreCase("CALCULATORS")))
      {
        Calculators localCalculators = new Calculators();
        if (this.jdField_int.equalsIgnoreCase("CATEGORY")) {
          localCalculators.setCategory(this.jdField_new);
        }
        localCalculators.setInCalculators(true);
        localCalculators.continueXMLParsing(getHandler());
        CalculatorCategories.this.add(localCalculators);
      }
      this.jdField_int = "";
      this.jdField_new = "";
    }
    
    public void endElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("CALCULATOR_CATEGORIES")) {
        this.jdField_try = false;
      }
    }
    
    public void attribute(String paramString1, String paramString2, boolean paramBoolean)
    {
      this.jdField_int = paramString1;
      this.jdField_new = paramString2;
    }
    
    a(CalculatorCategories.1 param1)
    {
      this();
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.portal.CalculatorCategories
 * JD-Core Version:    0.7.0.1
 */