package com.ffusion.beans.accounts;

import com.ffusion.util.FilteredList;
import com.ffusion.util.XMLHandler;
import java.util.Iterator;

public class FixedDepositInstruments
  extends FilteredList
{
  public static final String FIXED_DEP_INSTRUMENTS = "FIXED_DEP_INSTRUMENTS";
  protected String datetype = "SHORT";
  
  public FixedDepositInstrument createFixedDepositInstrument()
  {
    FixedDepositInstrument localFixedDepositInstrument = new FixedDepositInstrument();
    super.add(localFixedDepositInstrument);
    return localFixedDepositInstrument;
  }
  
  public void setDateFormat(String paramString)
  {
    this.datetype = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FixedDepositInstrument localFixedDepositInstrument = (FixedDepositInstrument)localIterator.next();
      localFixedDepositInstrument.setDateFormat(this.datetype);
    }
  }
  
  public String getDateFormat()
  {
    return this.datetype;
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
    XMLHandler.appendBeginTag(localStringBuffer, "FIXED_DEP_INSTRUMENTS");
    for (int i = 0; i < size(); i++)
    {
      FixedDepositInstrument localFixedDepositInstrument = (FixedDepositInstrument)get(i);
      localStringBuffer.append(localFixedDepositInstrument.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "FIXED_DEP_INSTRUMENTS");
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
  
  public void continueXMLParsing(XMLHandler paramXMLHandler, boolean paramBoolean)
  {
    continueXMLParsing(paramXMLHandler);
  }
  
  class a
    extends XMLHandler
  {
    a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equals("FIXED_DEP_INSTRUMENT"))
      {
        FixedDepositInstrument localFixedDepositInstrument = FixedDepositInstruments.this.createFixedDepositInstrument();
        localFixedDepositInstrument.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.accounts.FixedDepositInstruments
 * JD-Core Version:    0.7.0.1
 */