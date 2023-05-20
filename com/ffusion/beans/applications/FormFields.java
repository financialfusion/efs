package com.ffusion.beans.applications;

import com.ffusion.util.XMLHandler;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class FormFields
  extends ArrayList
  implements Serializable
{
  public static final String FORM_FIELD_GROUP = "FORM_FIELD_GROUP";
  
  public FormField add()
  {
    FormField localFormField = new FormField();
    add(localFormField);
    return localFormField;
  }
  
  public FormField create()
  {
    FormField localFormField = new FormField();
    return localFormField;
  }
  
  public FormField getByID(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FormField localFormField = (FormField)localIterator.next();
      if (paramString.equalsIgnoreCase(localFormField.getFieldID()))
      {
        localObject = localFormField;
        break;
      }
    }
    return localObject;
  }
  
  public FormField getByFieldName(String paramString)
  {
    Object localObject = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FormField localFormField = (FormField)localIterator.next();
      if (localFormField.getFieldName().equalsIgnoreCase(paramString))
      {
        localObject = localFormField;
        break;
      }
    }
    return localObject;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "FORM_FIELD_GROUP");
    FormField localFormField = null;
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      localFormField = (FormField)localIterator.next();
      localStringBuffer.append(localFormField.getXML());
    }
    XMLHandler.appendEndTag(localStringBuffer, "FORM_FIELD_GROUP");
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
  
  public void setFieldValue(String paramString1, String paramString2)
  {
    Iterator localIterator = iterator();
    while (localIterator.hasNext())
    {
      FormField localFormField = (FormField)localIterator.next();
      if (paramString1.equalsIgnoreCase(localFormField.getFieldName()))
      {
        localFormField.setFieldValue(paramString2);
        return;
      }
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
      if (paramString.equals("FORM_FIELD")) {
        FormFields.this.add().continueXMLParsing(getHandler());
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.FormFields
 * JD-Core Version:    0.7.0.1
 */