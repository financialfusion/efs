package com.ffusion.beans.ach;

import com.ffusion.beans.util.KeyValue;
import com.ffusion.beans.util.KeyValueList;
import com.ffusion.util.Sortable;
import com.ffusion.util.XMLHandler;
import com.ffusion.util.XMLable;
import java.util.Iterator;

public class TaxField
  implements XMLable, Sortable
{
  public static final String FIELD = "FIELD";
  public static final String KEY = "KEY";
  public static final String DATATYPE = "DATATYPE";
  public static final String LENGTH = "LENGTH";
  public static final String EDITABLE = "EDITABLE";
  public static final String VALUE = "VALUE";
  public static final String FIELDNAME = "FIELDNAME";
  public static final String REQUIRED = "REQUIRED";
  public static final String LEADINGCHARACTERS = "LEADINGCHARACTERS";
  public static final String LEADINGZEROS = "LEADINGZEROS";
  public static final String LEADINGSPACES = "LEADINGSPACES";
  public static final String TRAILINGZEROS = "TRAILINGZEROS";
  public static final String TRAILINGSPACES = "TRAILINGSPACES";
  public static final String NUMBERSONLY = "NUMBERSONLY";
  public static final String SELECT = "SELECT";
  public static final String OPTION = "OPTION";
  public static final String MEMO = "MEMO";
  protected String name = null;
  protected String dataType = null;
  protected int fixedLength = -1;
  protected int editable = -1;
  protected int leadingzeros = -1;
  protected int leadingspaces = -1;
  protected int trailingzeros = -1;
  protected int trailingspaces = -1;
  protected int numbersOnly = -1;
  protected boolean isCustom = false;
  protected String value = null;
  protected String memo = null;
  protected String leadingCharacters = null;
  protected String fieldname = null;
  protected int required = -1;
  protected KeyValueList options = null;
  protected int minLength = -1;
  protected int maxLength = -1;
  protected String taxTypePrefix = null;
  protected String validSuffixes = null;
  
  public int compare(Object paramObject, String paramString)
  {
    TaxField localTaxField = (TaxField)paramObject;
    return this.fieldname.compareTo(localTaxField.getFieldname());
  }
  
  public KeyValueList getOptions()
  {
    return this.options;
  }
  
  public void setOptions(KeyValueList paramKeyValueList)
  {
    this.options = paramKeyValueList;
  }
  
  public String getFieldname()
  {
    return this.fieldname;
  }
  
  public void setFieldname(String paramString)
  {
    this.fieldname = paramString;
  }
  
  public String getValue()
  {
    return this.value;
  }
  
  public void setValue(String paramString)
  {
    this.value = paramString;
  }
  
  public boolean getIsCustom()
  {
    return this.isCustom;
  }
  
  public void setIsCustom(boolean paramBoolean)
  {
    this.isCustom = paramBoolean;
  }
  
  public String getMemo()
  {
    return this.memo;
  }
  
  public void setMemo(String paramString)
  {
    this.memo = paramString;
  }
  
  public String getLeadingCharacters()
  {
    return this.leadingCharacters;
  }
  
  public void setLeadingCharacters(String paramString)
  {
    this.leadingCharacters = paramString;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public String getDataType()
  {
    return this.dataType;
  }
  
  public void setDataType(String paramString)
  {
    this.dataType = paramString;
  }
  
  protected void setLength(String paramString)
  {
    try
    {
      int i = paramString.indexOf('-');
      if (i != -1)
      {
        if (i == 0) {
          this.minLength = 0;
        } else {
          this.minLength = Integer.parseInt(paramString.substring(0, i));
        }
        this.maxLength = Integer.parseInt(paramString.substring(i + 1));
      }
      else
      {
        this.minLength = (this.maxLength = Integer.parseInt(paramString));
      }
    }
    catch (Throwable localThrowable) {}
  }
  
  public boolean getEditableValue()
  {
    return this.editable == 1;
  }
  
  public String getEditable()
  {
    if (this.editable == 1) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setEditableValue(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.editable = 1;
    } else {
      this.editable = 0;
    }
  }
  
  public void setEditable(String paramString)
  {
    if (paramString != null) {
      if (paramString.equalsIgnoreCase("TRUE")) {
        this.editable = 1;
      } else if (paramString.equalsIgnoreCase("FALSE")) {
        this.editable = 0;
      }
    }
  }
  
  public void setRequired(String paramString)
  {
    if (paramString != null) {
      if (paramString.equalsIgnoreCase("TRUE")) {
        this.required = 1;
      } else if (paramString.equalsIgnoreCase("FALSE")) {
        this.required = 0;
      }
    }
  }
  
  public boolean getRequiredValue()
  {
    return this.required == 1;
  }
  
  public String getRequired()
  {
    if (this.required == 1) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setRequiredValue(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.required = 1;
    } else {
      this.required = 0;
    }
  }
  
  public boolean getLeadingZerosValue()
  {
    return this.leadingzeros == 1;
  }
  
  public String getLeadingZeros()
  {
    if (this.leadingzeros == 1) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setLeadingZerosValue(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.leadingzeros = 1;
    } else {
      this.leadingzeros = 0;
    }
  }
  
  public void setLeadingZeros(String paramString)
  {
    if (paramString != null) {
      if (paramString.equalsIgnoreCase("TRUE")) {
        this.leadingzeros = 1;
      } else if (paramString.equalsIgnoreCase("FALSE")) {
        this.leadingzeros = 0;
      }
    }
  }
  
  public boolean getTrailingZerosValue()
  {
    return this.trailingzeros == 1;
  }
  
  public String getTrailingZeros()
  {
    if (this.trailingzeros == 1) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setTrailingZerosValue(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.trailingzeros = 1;
    } else {
      this.trailingzeros = 0;
    }
  }
  
  public void setTrailingZeros(String paramString)
  {
    if (paramString != null) {
      if (paramString.equalsIgnoreCase("TRUE")) {
        this.trailingzeros = 1;
      } else if (paramString.equalsIgnoreCase("FALSE")) {
        this.trailingzeros = 0;
      }
    }
  }
  
  public boolean getLeadingSpacesValue()
  {
    return this.leadingspaces == 1;
  }
  
  public String getLeadingSpaces()
  {
    if (this.leadingspaces == 1) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setLeadingSpacesValue(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.leadingspaces = 1;
    } else {
      this.leadingspaces = 0;
    }
  }
  
  public void setLeadingSpaces(String paramString)
  {
    if (paramString != null) {
      if (paramString.equalsIgnoreCase("TRUE")) {
        this.leadingspaces = 1;
      } else if (paramString.equalsIgnoreCase("FALSE")) {
        this.leadingspaces = 0;
      }
    }
  }
  
  public boolean getTrailingSpacesValue()
  {
    return this.trailingspaces == 1;
  }
  
  public String getTrailingSpaces()
  {
    if (this.trailingspaces == 1) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setTrailingSpacesValue(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.trailingspaces = 1;
    } else {
      this.trailingspaces = 0;
    }
  }
  
  public void setTrailingSpaces(String paramString)
  {
    if (paramString != null) {
      if (paramString.equalsIgnoreCase("TRUE")) {
        this.trailingspaces = 1;
      } else if (paramString.equalsIgnoreCase("FALSE")) {
        this.trailingspaces = 0;
      }
    }
  }
  
  public boolean getNumbersOnlyValue()
  {
    return this.numbersOnly == 1;
  }
  
  public String getNumbersOnly()
  {
    if (this.numbersOnly == 1) {
      return "TRUE";
    }
    return "FALSE";
  }
  
  public void setNumbersOnlyValue(boolean paramBoolean)
  {
    if (paramBoolean) {
      this.numbersOnly = 1;
    } else {
      this.numbersOnly = 0;
    }
  }
  
  public void setNumbersOnly(String paramString)
  {
    if (paramString != null) {
      if (paramString.equalsIgnoreCase("TRUE")) {
        this.numbersOnly = 1;
      } else if (paramString.equalsIgnoreCase("FALSE")) {
        this.numbersOnly = 0;
      }
    }
  }
  
  public int getMinLengthValue()
  {
    return this.minLength;
  }
  
  public int getMaxLengthValue()
  {
    return this.maxLength;
  }
  
  public String getMinLength()
  {
    return String.valueOf(this.minLength);
  }
  
  public void setMinLength(String paramString)
  {
    try
    {
      this.minLength = Integer.parseInt(paramString.substring(1));
    }
    catch (Throwable localThrowable) {}
  }
  
  public String getMaxLength()
  {
    return String.valueOf(this.maxLength);
  }
  
  public void setMaxLength(String paramString)
  {
    try
    {
      this.maxLength = Integer.parseInt(paramString.substring(1));
    }
    catch (Throwable localThrowable) {}
  }
  
  public String getTaxTypePrefix()
  {
    return this.taxTypePrefix;
  }
  
  public void setTaxTypePrefix(String paramString)
  {
    this.taxTypePrefix = paramString;
  }
  
  public String getValidSuffixes()
  {
    return this.validSuffixes;
  }
  
  public void setValidSuffixes(String paramString)
  {
    this.validSuffixes = paramString;
  }
  
  public String addSpaceFilledOrZeroFilled(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    if ((getLeadingCharacters() != null) && (getLeadingCharacters().length() > 0))
    {
      if (paramString.indexOf(getLeadingCharacters()) != 0) {
        paramString = getLeadingCharacters() + paramString;
      }
    }
    else
    {
      if (getLeadingZerosValue()) {
        while (paramString.length() < getMaxLengthValue()) {
          paramString = "0" + paramString;
        }
      }
      if (getLeadingSpacesValue()) {
        while (paramString.length() < getMaxLengthValue()) {
          paramString = " " + paramString;
        }
      }
    }
    if (getTrailingZerosValue()) {
      while (paramString.length() < getMaxLengthValue()) {
        paramString = paramString + "0";
      }
    }
    if (getTrailingSpacesValue()) {
      while (paramString.length() < getMaxLengthValue()) {
        paramString = paramString + " ";
      }
    }
    return paramString;
  }
  
  public boolean anyLeadingOrTrailing()
  {
    return ((getLeadingCharacters() != null) && (getLeadingCharacters().length() > 0)) || (getLeadingZerosValue()) || (getLeadingSpacesValue()) || (getTrailingZerosValue()) || (getTrailingSpacesValue());
  }
  
  public TaxField copy()
  {
    TaxField localTaxField = new TaxField();
    localTaxField.set(this);
    return localTaxField;
  }
  
  public void set(TaxField paramTaxField)
  {
    this.name = paramTaxField.name;
    this.dataType = paramTaxField.dataType;
    this.editable = paramTaxField.editable;
    this.leadingzeros = paramTaxField.leadingzeros;
    this.leadingspaces = paramTaxField.leadingspaces;
    this.trailingzeros = paramTaxField.trailingzeros;
    this.trailingspaces = paramTaxField.trailingspaces;
    this.numbersOnly = paramTaxField.numbersOnly;
    this.value = paramTaxField.value;
    this.memo = paramTaxField.memo;
    this.leadingCharacters = paramTaxField.leadingCharacters;
    this.fieldname = paramTaxField.fieldname;
    this.required = paramTaxField.required;
    this.minLength = paramTaxField.minLength;
    this.maxLength = paramTaxField.maxLength;
    this.options = paramTaxField.options;
    this.taxTypePrefix = paramTaxField.taxTypePrefix;
    this.validSuffixes = paramTaxField.validSuffixes;
  }
  
  public boolean merge(TaxField paramTaxField)
  {
    boolean bool = false;
    if (paramTaxField.name != null)
    {
      this.name = paramTaxField.name;
      bool = true;
    }
    if (paramTaxField.leadingCharacters != null)
    {
      this.leadingCharacters = paramTaxField.leadingCharacters;
      bool = true;
    }
    if (paramTaxField.dataType != null)
    {
      this.dataType = paramTaxField.dataType;
      bool = true;
    }
    if (paramTaxField.editable != -1)
    {
      this.editable = paramTaxField.editable;
      bool = true;
    }
    if (paramTaxField.leadingzeros != -1)
    {
      this.leadingzeros = paramTaxField.leadingzeros;
      bool = true;
    }
    if (paramTaxField.leadingspaces != -1)
    {
      this.leadingspaces = paramTaxField.leadingspaces;
      bool = true;
    }
    if (paramTaxField.trailingzeros != -1)
    {
      this.trailingzeros = paramTaxField.trailingzeros;
      bool = true;
    }
    if (paramTaxField.trailingspaces != -1)
    {
      this.trailingspaces = paramTaxField.trailingspaces;
      bool = true;
    }
    if (paramTaxField.numbersOnly != -1)
    {
      this.numbersOnly = paramTaxField.numbersOnly;
      bool = true;
    }
    if (paramTaxField.value != null)
    {
      this.value = paramTaxField.value;
      bool = true;
    }
    if (paramTaxField.memo != null)
    {
      this.memo = paramTaxField.memo;
      bool = true;
    }
    if (paramTaxField.fieldname != null)
    {
      this.fieldname = paramTaxField.fieldname;
      bool = true;
    }
    if (paramTaxField.required != -1)
    {
      this.required = paramTaxField.required;
      bool = true;
    }
    if (paramTaxField.minLength != -1)
    {
      this.minLength = paramTaxField.minLength;
      bool = true;
    }
    if (paramTaxField.maxLength != -1)
    {
      this.maxLength = paramTaxField.maxLength;
      bool = true;
    }
    if (paramTaxField.options != null)
    {
      this.options = paramTaxField.options;
      bool = true;
    }
    if (paramTaxField.taxTypePrefix != null)
    {
      this.taxTypePrefix = paramTaxField.taxTypePrefix;
      bool = true;
    }
    if (paramTaxField.validSuffixes != null)
    {
      this.validSuffixes = paramTaxField.validSuffixes;
      bool = true;
    }
    return bool;
  }
  
  public void set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("KEY")) {
      setName(paramString2);
    } else if (paramString1.equalsIgnoreCase("LENGTH")) {
      setLength(paramString2);
    } else if (paramString1.equalsIgnoreCase("DATATYPE")) {
      setDataType(paramString2);
    } else if (paramString1.equalsIgnoreCase("EDITABLE")) {
      setEditable(paramString2);
    } else if (paramString1.equalsIgnoreCase("LEADINGZEROS")) {
      setLeadingZeros(paramString2);
    } else if (paramString1.equalsIgnoreCase("LEADINGSPACES")) {
      setLeadingSpaces(paramString2);
    } else if (paramString1.equalsIgnoreCase("TRAILINGZEROS")) {
      setTrailingZeros(paramString2);
    } else if (paramString1.equalsIgnoreCase("TRAILINGSPACES")) {
      setTrailingSpaces(paramString2);
    } else if (paramString1.equalsIgnoreCase("NUMBERSONLY")) {
      setNumbersOnly(paramString2);
    } else if (paramString1.equalsIgnoreCase("VALUE")) {
      setValue(paramString2);
    } else if (paramString1.equalsIgnoreCase("MEMO")) {
      setMemo(paramString2);
    } else if (paramString1.equalsIgnoreCase("LEADINGCHARACTERS")) {
      setLeadingCharacters(paramString2);
    } else if (paramString1.equalsIgnoreCase("FIELDNAME")) {
      setFieldname(paramString2);
    } else if (paramString1.equalsIgnoreCase("REQUIRED")) {
      setRequired(paramString2);
    } else if (paramString1.equalsIgnoreCase("VALIDSUFFIXES")) {
      setValidSuffixes(paramString2);
    } else if (paramString1.equalsIgnoreCase("TaxTypePrefix")) {
      setTaxTypePrefix(paramString2);
    }
  }
  
  public void fromXML(String paramString)
  {
    setXML(paramString);
  }
  
  public String toXML()
  {
    return getXML();
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
  
  public String getXML()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    XMLHandler.appendBeginTag(localStringBuffer, "FIELD");
    XMLHandler.appendTag(localStringBuffer, "KEY", getName());
    XMLHandler.appendTag(localStringBuffer, "DATATYPE", getDataType());
    XMLHandler.appendTag(localStringBuffer, "LENGTH", getMinLength() + "-" + getMaxLength());
    XMLHandler.appendTag(localStringBuffer, "EDITABLE", getEditableValue() ? "TRUE" : "FALSE");
    XMLHandler.appendTag(localStringBuffer, "LEADINGZEROS", getLeadingZerosValue() ? "TRUE" : "FALSE");
    XMLHandler.appendTag(localStringBuffer, "TRAILINGZEROS", getTrailingZerosValue() ? "TRUE" : "FALSE");
    XMLHandler.appendTag(localStringBuffer, "LEADINGSPACES", getLeadingSpacesValue() ? "TRUE" : "FALSE");
    XMLHandler.appendTag(localStringBuffer, "TRAILINGSPACES", getTrailingSpacesValue() ? "TRUE" : "FALSE");
    XMLHandler.appendTag(localStringBuffer, "NUMBERSONLY", getNumbersOnlyValue() ? "TRUE" : "FALSE");
    XMLHandler.appendTag(localStringBuffer, "VALUE", getValue());
    XMLHandler.appendTag(localStringBuffer, "MEMO", getMemo());
    XMLHandler.appendTag(localStringBuffer, "LEADINGCHARACTERS", getLeadingCharacters());
    XMLHandler.appendTag(localStringBuffer, "FIELDNAME", getFieldname());
    XMLHandler.appendTag(localStringBuffer, "REQUIRED", getRequired());
    XMLHandler.appendTag(localStringBuffer, "VALIDSUFFIXES", getValidSuffixes());
    XMLHandler.appendTag(localStringBuffer, "TaxTypePrefix", getTaxTypePrefix());
    XMLHandler.appendEndTag(localStringBuffer, "FIELD");
    return localStringBuffer.toString();
  }
  
  public String toString()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(getFieldname()).append(' ');
    localStringBuffer.append(getDataType()).append(' ');
    localStringBuffer.append(getIsCustom() ? "*" : " ").append(' ');
    localStringBuffer.append(getEditableValue() ? "EDIT" : "    ").append(' ');
    localStringBuffer.append(getRequiredValue() ? "REQ" : "   ").append(' ');
    localStringBuffer.append(getLeadingZerosValue() ? "LZ" : "  ").append(' ');
    localStringBuffer.append(getLeadingSpacesValue() ? "LS" : "  ").append(' ');
    localStringBuffer.append(getTrailingZerosValue() ? "TZ" : "  ").append(' ');
    localStringBuffer.append(getTrailingSpacesValue() ? "TS" : "  ").append(' ');
    localStringBuffer.append(getNumbersOnlyValue() ? "NUM" : "   ").append(':');
    localStringBuffer.append(getName()).append(':');
    if (getMinLengthValue() == getMaxLengthValue()) {
      localStringBuffer.append("Len=" + getMinLength());
    } else {
      localStringBuffer.append("MinLen=" + getMinLength() + ",MaxLen=" + getMaxLength());
    }
    if (getValue() != null) {
      localStringBuffer.append(",Val='").append(getValue()).append('\'');
    }
    if (getMemo() != null) {
      localStringBuffer.append(",Memo='").append(getMemo()).append('\'');
    }
    if ((getLeadingCharacters() != null) && (getLeadingCharacters().length() > 0)) {
      localStringBuffer.append(",LeadingChars='").append(getLeadingCharacters()).append('\'');
    }
    if (getOptions() != null)
    {
      Iterator localIterator = getOptions().iterator();
      while (localIterator.hasNext())
      {
        KeyValue localKeyValue = (KeyValue)localIterator.next();
        localStringBuffer.append("\n       ").append(localKeyValue.getValue()).append('=').append(localKeyValue.getKey());
      }
    }
    return localStringBuffer.toString();
  }
  
  public void continueXMLParsing(XMLHandler paramXMLHandler)
  {
    paramXMLHandler.continueWith(new a());
  }
  
  class a
    extends XMLHandler
  {
    public a() {}
    
    public void startElement(String paramString)
    {
      if (paramString.equalsIgnoreCase("SELECT"))
      {
        TaxField.this.options = new KeyValueList();
        TaxField.this.options.continueXMLParsing(getHandler());
        TaxField.this.setDataType("ID");
      }
    }
    
    public void charData(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      String str = new String(paramArrayOfChar, paramInt1, paramInt2);
      str = str.trim();
      TaxField.this.set(getElement(), str);
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.ach.TaxField
 * JD-Core Version:    0.7.0.1
 */