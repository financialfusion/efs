package com.ffusion.beans.applications;

import com.ffusion.beans.ExtendABean;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.beans.ExtendABean.InternalXMLHandler;

public class FormFieldI18N
  extends ExtendABean
{
  protected String formID;
  protected String fieldID;
  protected String language;
  protected String displayName = "";
  protected String errorString = "";
  
  public void setFormID(String paramString)
  {
    this.formID = paramString;
  }
  
  public String getFormID()
  {
    return this.formID;
  }
  
  public void setFieldID(String paramString)
  {
    this.fieldID = paramString;
  }
  
  public String getFieldID()
  {
    return this.fieldID;
  }
  
  public void setDisplayName(String paramString)
  {
    this.displayName = paramString;
  }
  
  public String getDisplayName()
  {
    return this.displayName;
  }
  
  public void setErrorString(String paramString)
  {
    this.errorString = paramString;
  }
  
  public String getErrorString()
  {
    return this.errorString;
  }
  
  public String getLanguage()
  {
    return this.language;
  }
  
  public void setLanguage(String paramString)
  {
    this.language = paramString;
  }
  
  public void set(FormFieldI18N paramFormFieldI18N)
  {
    this.formID = paramFormFieldI18N.formID;
    this.fieldID = paramFormFieldI18N.fieldID;
    this.language = paramFormFieldI18N.language;
    this.displayName = paramFormFieldI18N.displayName;
    this.errorString = paramFormFieldI18N.errorString;
    super.set(paramFormFieldI18N);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("FORM_ID")) {
      this.formID = paramString2;
    } else if (paramString1.equalsIgnoreCase("FIELD_ID")) {
      this.fieldID = paramString2;
    } else if (paramString1.equalsIgnoreCase("LANGUAGE")) {
      this.language = paramString2;
    } else if (paramString1.equalsIgnoreCase("DISPLAY_NAME")) {
      this.displayName = paramString2;
    } else if (paramString1.equalsIgnoreCase("ERROR_STRING")) {
      this.errorString = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String toXML()
  {
    return getXML();
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
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
      if (paramString.equals("FORM_FIELD")) {
        int i = 1;
      } else {
        super.startElement(paramString);
      }
    }
    
    public void endElement(String paramString)
      throws Exception
    {
      if (paramString.equals("FORM_FIELD")) {
        int i = 1;
      } else {
        super.endElement(paramString);
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.FormFieldI18N
 * JD-Core Version:    0.7.0.1
 */