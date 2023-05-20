package com.ffusion.beans;

import com.ffusion.util.FilteredList;
import com.ffusion.util.Localeable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;
import java.util.Locale;

public class FundsTransactionTemplates
  extends FilteredList
  implements Localeable, XMLable
{
  private static final String jdField_byte = "FUNDSTRANSACTIONTEMPLATES";
  protected String dateformat;
  
  public FundsTransactionTemplates() {}
  
  public FundsTransactionTemplates(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public FundsTransactionTemplate create()
  {
    FundsTransactionTemplate localFundsTransactionTemplate = new FundsTransactionTemplate();
    add(localFundsTransactionTemplate);
    return localFundsTransactionTemplate;
  }
  
  public FundsTransactionTemplate createNoAdd()
  {
    FundsTransactionTemplate localFundsTransactionTemplate = new FundsTransactionTemplate();
    return localFundsTransactionTemplate;
  }
  
  public FundsTransactionTemplate create(FundsTransaction paramFundsTransaction)
  {
    FundsTransactionTemplate localFundsTransactionTemplate = new FundsTransactionTemplate();
    add(localFundsTransactionTemplate);
    localFundsTransactionTemplate.set(paramFundsTransaction);
    return localFundsTransactionTemplate;
  }
  
  public FundsTransactionTemplate createNoAdd(FundsTransaction paramFundsTransaction)
  {
    FundsTransactionTemplate localFundsTransactionTemplate = new FundsTransactionTemplate();
    localFundsTransactionTemplate.set(paramFundsTransaction);
    return localFundsTransactionTemplate;
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
    XMLHandler.appendBeginTag(localStringBuffer, "FUNDSTRANSACTIONTEMPLATES");
    for (int i = 0; i < size(); i++) {
      localStringBuffer.append(((FundsTransactionTemplate)get(i)).getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "FUNDSTRANSACTIONTEMPLATES");
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
  
  public void setDateFormat(String paramString)
  {
    this.dateformat = paramString;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FundsTransactionTemplate localFundsTransactionTemplate = (FundsTransactionTemplate)localIterator.next();
      localFundsTransactionTemplate.getFundsTransaction().setDateFormat(paramString);
    }
  }
  
  public String getDateFormat()
  {
    return this.dateformat;
  }
  
  public FundsTransactionTemplate getByTemplateID(int paramInt)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FundsTransactionTemplate localFundsTransactionTemplate = (FundsTransactionTemplate)localIterator.next();
      if (localFundsTransactionTemplate.getID() == paramInt)
      {
        localObject = localFundsTransactionTemplate;
        break;
      }
    }
    return localObject;
  }
  
  public FundsTransactionTemplate getByTemplateName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FundsTransactionTemplate localFundsTransactionTemplate = (FundsTransactionTemplate)localIterator.next();
      String str = localFundsTransactionTemplate.getTemplateName();
      if ((str != null) && (str.equalsIgnoreCase(paramString)))
      {
        localObject = localFundsTransactionTemplate;
        break;
      }
    }
    return localObject;
  }
  
  public FundsTransactionTemplate getByTemplateName_Group(String paramString1, String paramString2)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FundsTransactionTemplate localFundsTransactionTemplate = (FundsTransactionTemplate)localIterator.next();
      String str1 = localFundsTransactionTemplate.getTemplateName();
      String str2 = localFundsTransactionTemplate.getTemplateGroup();
      if ((str1 != null) && (str1.equalsIgnoreCase(paramString1)) && (str2 != null) && (str2.equalsIgnoreCase(paramString2)))
      {
        localObject = localFundsTransactionTemplate;
        break;
      }
    }
    return localObject;
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
      if (paramString.equals("FUNDSTRANSACTIONTEMPLATE"))
      {
        FundsTransactionTemplate localFundsTransactionTemplate = FundsTransactionTemplates.this.create();
        localFundsTransactionTemplate.continueXMLParsing(getHandler());
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.FundsTransactionTemplates
 * JD-Core Version:    0.7.0.1
 */