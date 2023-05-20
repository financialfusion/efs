package com.ffusion.beans.applications;

import com.ffusion.util.XMLHandler;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class FormField
  extends FormFieldI18N
{
  protected String fieldName = "";
  protected String fieldType = "";
  protected String minValue = "";
  protected String maxValue = "";
  protected String required = "";
  protected String fieldNumber = "";
  protected String dependValue = "";
  protected String dependField = "";
  protected String exactValue = "";
  protected String controlType = "";
  protected String fieldValue = "";
  protected String fieldLength = "150";
  private HashMap aWH = null;
  public static final String defaultLanguage = "en_US";
  private String aWG = "en_US";
  
  public FormField() {}
  
  public FormField(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.fieldID = paramString1;
    this.fieldName = paramString2;
    setDisplayName(paramString3);
    this.fieldType = paramString4;
    this.required = paramString5;
    setErrorString(paramString6);
    this.controlType = paramString7;
  }
  
  public void setFieldName(String paramString)
  {
    this.fieldName = paramString;
  }
  
  public String getFieldName()
  {
    return this.fieldName;
  }
  
  public void setFieldType(String paramString)
  {
    this.fieldType = paramString;
  }
  
  public String getFieldType()
  {
    return this.fieldType;
  }
  
  public void setMinValue(String paramString)
  {
    this.minValue = paramString;
  }
  
  public String getMinValue()
  {
    return this.minValue;
  }
  
  public int getMinValueInt()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.minValue);
    }
    catch (Exception localException)
    {
      return -1;
    }
    return i;
  }
  
  public void setMaxValue(String paramString)
  {
    this.maxValue = paramString;
  }
  
  public String getMaxValue()
  {
    return this.maxValue;
  }
  
  public int getMaxValueInt()
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(this.maxValue);
    }
    catch (Exception localException)
    {
      return -1;
    }
    return i;
  }
  
  public void setRequired(String paramString)
  {
    this.required = paramString;
  }
  
  public String getRequired()
  {
    return this.required;
  }
  
  public String getRequiredChecked()
  {
    if (this.required.equals("1")) {
      return "checked";
    }
    return "";
  }
  
  public void setFieldNumber(String paramString)
  {
    this.fieldNumber = paramString;
  }
  
  public String getFieldNumber()
  {
    return this.fieldNumber;
  }
  
  public void setDependValue(String paramString)
  {
    this.dependValue = paramString;
  }
  
  public String getDependValue()
  {
    return this.dependValue;
  }
  
  public void setDependField(String paramString)
  {
    this.dependField = paramString;
  }
  
  public String getDependField()
  {
    return this.dependField;
  }
  
  public void setExactValue(String paramString)
  {
    this.exactValue = paramString;
  }
  
  public String getExactValue()
  {
    return this.exactValue;
  }
  
  public void setControlType(String paramString)
  {
    this.controlType = paramString;
  }
  
  public String getControlType()
  {
    return this.controlType;
  }
  
  public void setFieldValue(String paramString)
  {
    this.fieldValue = paramString;
  }
  
  public String getFieldValue()
  {
    return this.fieldValue;
  }
  
  public void setFieldLength(String paramString)
  {
    this.fieldLength = paramString;
  }
  
  public String getFieldLength()
  {
    return this.fieldLength;
  }
  
  public String getDisplayName()
  {
    return getDisplayName(this.aWG);
  }
  
  public String getDisplayName(String paramString)
  {
    if (paramString.equals("en_US")) {
      return this.displayName;
    }
    if (this.aWH == null) {
      return null;
    }
    FormFieldI18N localFormFieldI18N = (FormFieldI18N)this.aWH.get(paramString);
    if (localFormFieldI18N == null) {
      return null;
    }
    return localFormFieldI18N.getDisplayName();
  }
  
  public void setDisplayName(String paramString1, String paramString2)
  {
    if (paramString1.equals("en_US"))
    {
      this.displayName = paramString2;
      return;
    }
    if (this.aWH == null) {
      this.aWH = new HashMap();
    }
    FormFieldI18N localFormFieldI18N = null;
    if (!this.aWH.containsKey(paramString1))
    {
      localFormFieldI18N = new FormFieldI18N();
      localFormFieldI18N.setLanguage(paramString1);
      this.aWH.put(paramString1, new FormFieldI18N());
    }
    else
    {
      localFormFieldI18N = (FormFieldI18N)this.aWH.get(paramString1);
    }
    localFormFieldI18N.setDisplayName(paramString2);
  }
  
  public String getErrorString()
  {
    return getErrorString(this.aWG);
  }
  
  public String getErrorString(String paramString)
  {
    if (paramString.equals("en_US")) {
      return this.errorString;
    }
    if (this.aWH == null) {
      return null;
    }
    FormFieldI18N localFormFieldI18N = (FormFieldI18N)this.aWH.get(paramString);
    if (localFormFieldI18N == null) {
      return null;
    }
    return localFormFieldI18N.getErrorString();
  }
  
  public void setErrorString(String paramString1, String paramString2)
  {
    if (paramString1.equals("en_US"))
    {
      this.errorString = paramString2;
      return;
    }
    if (this.aWH == null) {
      this.aWH = new HashMap();
    }
    FormFieldI18N localFormFieldI18N = null;
    if (!this.aWH.containsKey(paramString1))
    {
      localFormFieldI18N = new FormFieldI18N();
      localFormFieldI18N.setLanguage(paramString1);
      this.aWH.put(paramString1, localFormFieldI18N);
    }
    else
    {
      localFormFieldI18N = (FormFieldI18N)this.aWH.get(paramString1);
    }
    localFormFieldI18N.setErrorString(paramString2);
  }
  
  public Iterator getLanguages()
  {
    if (this.aWH == null) {
      this.aWH = new HashMap();
    }
    return this.aWH.keySet().iterator();
  }
  
  public void setCurrentLanguage(String paramString)
  {
    this.aWG = paramString;
  }
  
  public String getCurrentLanguage()
  {
    return this.aWG;
  }
  
  public void set(FormField paramFormField)
  {
    setFieldName(paramFormField.getFieldName());
    setFieldType(paramFormField.getFieldType());
    setMinValue(paramFormField.getMinValue());
    setMaxValue(paramFormField.getMaxValue());
    setRequired(paramFormField.getRequired());
    setFieldNumber(paramFormField.getFieldNumber());
    setDependValue(paramFormField.getDependValue());
    setDependField(paramFormField.getDependField());
    setExactValue(paramFormField.getExactValue());
    setControlType(paramFormField.getControlType());
    setFieldValue(paramFormField.getFieldValue());
    setFieldLength(paramFormField.getFieldLength());
    super.set(paramFormField);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    boolean bool = true;
    if (paramString1.equalsIgnoreCase("FIELD_NAME")) {
      this.fieldName = paramString2;
    } else if (paramString1.equalsIgnoreCase("FIELD_NUMBER")) {
      this.fieldNumber = paramString2;
    } else if (paramString1.equalsIgnoreCase("FIELD_TYPE")) {
      this.fieldType = paramString2;
    } else if (paramString1.equalsIgnoreCase("EXACT_VALUE")) {
      this.fieldValue = paramString2;
    } else if (paramString1.equalsIgnoreCase("DEPEND_FIELD")) {
      this.dependField = paramString2;
    } else if (paramString1.equalsIgnoreCase("DEPEND_VALUE")) {
      this.dependValue = paramString2;
    } else if (paramString1.equalsIgnoreCase("CONTROL_TYPE")) {
      this.controlType = paramString2;
    } else if (paramString1.equalsIgnoreCase("MAX_VALUE")) {
      this.maxValue = paramString2;
    } else if (paramString1.equalsIgnoreCase("MIN_VALUE")) {
      this.minValue = paramString2;
    } else if (paramString1.equalsIgnoreCase("REQUIRED")) {
      this.required = paramString2;
    } else {
      bool = super.set(paramString1, paramString2);
    }
    return bool;
  }
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "FORM_FIELD");
    XMLHandler.appendTag(localStringBuffer, "FIELD_ID", this.fieldID);
    XMLHandler.appendTag(localStringBuffer, "DISPLAY_NAME", this.displayName);
    XMLHandler.appendTag(localStringBuffer, "FIELD_NAME", this.fieldName);
    XMLHandler.appendTag(localStringBuffer, "FIELD_TYPE", this.fieldType);
    XMLHandler.appendTag(localStringBuffer, "MIN_VALUE", this.minValue);
    XMLHandler.appendTag(localStringBuffer, "MAX_VALUE", this.maxValue);
    XMLHandler.appendTag(localStringBuffer, "REQUIRED", this.required);
    XMLHandler.appendTag(localStringBuffer, "FIELD_NUMBER", this.fieldNumber);
    XMLHandler.appendTag(localStringBuffer, "ERROR_STRING", this.errorString);
    XMLHandler.appendTag(localStringBuffer, "DEPEND_VALUE", this.dependValue);
    XMLHandler.appendTag(localStringBuffer, "DEPEND_FIELD", this.dependField);
    XMLHandler.appendTag(localStringBuffer, "EXACT_VALUE", this.exactValue);
    XMLHandler.appendTag(localStringBuffer, "CONTROL_TYPE", this.controlType);
    XMLHandler.appendEndTag(localStringBuffer, "FORM_FIELD");
    return localStringBuffer.toString();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.applications.FormField
 * JD-Core Version:    0.7.0.1
 */