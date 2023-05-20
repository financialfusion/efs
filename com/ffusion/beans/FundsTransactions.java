package com.ffusion.beans;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Locale;

public class FundsTransactions
  extends FilteredList
  implements Localeable, XMLable
{
  private static final String jdField_byte = "FUNDSTRANSACTIONS";
  
  public FundsTransactions() {}
  
  public FundsTransactions(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public FundsTransaction getByID(String paramString)
  {
    return (FundsTransaction)getFirstByFilter("ID=" + paramString);
  }
  
  public Object create()
  {
    FundsTransaction localFundsTransaction = new FundsTransaction();
    add(localFundsTransaction);
    return localFundsTransaction;
  }
  
  public Object createNoAdd()
  {
    FundsTransaction localFundsTransaction = new FundsTransaction();
    return localFundsTransaction;
  }
  
  public Object create(int paramInt)
  {
    FundsTransaction localFundsTransaction = (FundsTransaction)createNoAdd(paramInt);
    add(localFundsTransaction);
    return localFundsTransaction;
  }
  
  public Object createNoAdd(int paramInt)
  {
    FundsTransaction localFundsTransaction = new FundsTransaction();
    switch (paramInt)
    {
    }
    return localFundsTransaction;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String getXMLEncapsulationString()
  {
    return "FUNDSTRANSACTIONS";
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, getXMLEncapsulationString());
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((FundsTransaction)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, getXMLEncapsulationString());
    return localStringBuffer.toString();
  }
  
  public void setXML(String paramString)
  {
    try
    {
      XMLHandler localXMLHandler = new XMLHandler();
      localXMLHandler.start(new InternalXMLHandler(), paramString);
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
    }
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new InternalXMLHandler());
  }
  
  class InternalXMLHandler
    extends XMLHandler
  {
    InternalXMLHandler() {}
    
    public void startElement(String paramString)
    {
      if ((paramString.equals("TRANSFER")) || (paramString.equals("RECTRANSFER")) || (paramString.equals("PAYMENT")) || (paramString.equals("RECPAYMENT")) || (paramString.equals("WIRETRANSFER")) || (paramString.equals("ACHENTRY")) || (paramString.equals("RECACHENTRY")) || (paramString.equals("ACHBATCH")) || (paramString.equals("RECACHBATCH")) || (paramString.equals("ACHUPLOAD")) || (paramString.equals("TAXPAYMENT")) || (paramString.equals("RECTAXPAYMENT")))
      {
        FundsTransaction localFundsTransaction = (FundsTransaction)FundsTransactions.this.create();
        localFundsTransaction.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.FundsTransactions
 * JD-Core Version:    0.7.0.1
 */