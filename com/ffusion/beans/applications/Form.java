package com.ffusion.beans.applications;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;
import java.util.Iterator;

public class Form
  extends ExtendABean
{
  private String ID;
  private String aWC;
  private FormFields aWE = new FormFields();
  private String aWD = "en_US";
  
  public void setID(String paramString)
  {
    this.ID = paramString;
  }
  
  public String getID()
  {
    return this.ID;
  }
  
  public void setName(String paramString)
  {
    this.aWC = paramString;
  }
  
  public String getName()
  {
    return this.aWC;
  }
  
  public void setFormFields(FormFields paramFormFields)
  {
    this.aWE = paramFormFields;
  }
  
  public FormFields getFormFields()
  {
    return this.aWE;
  }
  
  public void setSearchLanguage(String paramString)
  {
    this.aWD = paramString;
  }
  
  public String getSearchLanguage()
  {
    return this.aWD;
  }
  
  public void set(Form paramForm)
  {
    setID(paramForm.getID());
    setName(paramForm.getName());
    if (paramForm.getFormFields() != null) {
      setFormFields((FormFields)paramForm.getFormFields().clone());
    }
    super.set(paramForm);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("FORM_NAME")) {
      this.aWC = paramString2;
    } else if (paramString1.equalsIgnoreCase("FORM_ID")) {
      this.ID = paramString2;
    } else if (aq(paramString1)) {
      this.aWE.setFieldValue(paramString1, paramString2);
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  private boolean aq(String paramString)
  {
    Iterator localIterator = this.aWE.iterator();
    while (localIterator.hasNext())
    {
      FormField localFormField = (FormField)localIterator.next();
      if (paramString.equalsIgnoreCase(localFormField.getFieldName())) {
        return true;
      }
    }
    return false;
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
    XMLHandler.appendBeginTag(localStringBuffer, "FORM");
    XMLHandler.appendTag(localStringBuffer, "FORM_ID", this.ID);
    XMLHandler.appendTag(localStringBuffer, "FORM_NAME", this.aWC);
    localStringBuffer.append(this.aWE.getXML());
    XMLHandler.appendEndTag(localStringBuffer, "FORM");
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
    extends ExtendABean.InternalXMLHandler
  {
    a()
    {
      super();
    }
    
    public void startElement(String paramString)
      throws Exception
    {
      if (paramString.equals("FORM_FIELD_GROUP")) {
        Form.this.aWE.continueXMLParsing(getHandler());
      } else {
        super.startElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.Form
 * JD-Core Version:    0.7.0.1
 */