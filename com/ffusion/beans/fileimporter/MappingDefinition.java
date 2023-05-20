package com.ffusion.beans.fileimporter;

import com.ffusion.beans.ExtendABean;
import java.text.Collator;
import java.util.Locale;
import java.util.StringTokenizer;

public class MappingDefinition
  extends ExtendABean
{
  public static final int INPUT_FORMAT_TYPE_DELIMITED = 0;
  public static final int INPUT_FORMAT_TYPE_FIXED = 1;
  public static final int FIELD_DELIMITER_TYPE_TAB = 0;
  public static final int FIELD_DELIMITER_TYPE_SEMICOLON = 1;
  public static final int FIELD_DELIMITER_TYPE_COMMA = 2;
  public static final int FIELD_DELIMITER_TYPE_SPACE = 3;
  public static final int FIELD_DELIMITER_TYPE_PIPE = 4;
  public static final int FIELD_DELIMITER_TYPE_NONE = 5;
  public static final int RECORD_DELIMITER_TYPE_LF = 0;
  public static final int RECORD_DELIMITER_TYPE_CRLF = 1;
  public static final int RECORD_DELIMITER_TYPE_NONE = 2;
  public static final int DATE_FORMAT_TYPE_YYYYMMDD = 0;
  public static final int DATE_FORMAT_TYPE_YYMMDD = 1;
  public static final int DATE_FORMAT_TYPE_MMDDYYYY = 2;
  public static final int DATE_FORMAT_TYPE_MMDDYY = 3;
  public static final int DATE_FORMAT_TYPE_DDMMYYYY = 4;
  public static final int DATE_FORMAT_TYPE_DDMMYY = 5;
  public static final int DATE_SEPARATOR_TYPE_SLASH = 0;
  public static final int DATE_SEPARATOR_TYPE_HYPHEN = 1;
  public static final int DATE_SEPARATOR_TYPE_PERIOD = 2;
  public static final int DATE_SEPARATOR_TYPE_NONE = 3;
  public static final int MONEY_FORMAT_TYPE_AS_IS = 0;
  public static final int MONEY_FORMAT_TYPE_2_DECIMALS = 1;
  public static final int MONEY_FORMAT_TYPE_4_DECIMALS = 2;
  public static final int MATCH_RECORDS_BY_NONE = -1;
  public static final int MATCH_RECORDS_BY_NAME = 0;
  public static final int MATCH_RECORDS_BY_ID = 1;
  public static final int MATCH_RECORDS_BY_ID_NAME = 2;
  public static final int MATCH_RECORDS_BY_ACCOUNT = 3;
  public static final int MATCH_RECORDS_BY_ID_NAME_ACCOUNT = 4;
  public static final int UPDATE_RECORDS_BY_EXISTING = 0;
  public static final int UPDATE_RECORDS_BY_NEW = 1;
  public static final int UPDATE_RECORDS_BY_EXISTING_NEW = 2;
  public static final int UPDATE_RECORDS_BY_EXISTING_AMOUNTS_ONLY = 3;
  public static final String FIELD_NAME = "NAME";
  public static final String FIELD_DESCRIPTION = "DESCRIPTION";
  public static final String FIELD_OUTPUT_FORMAT = "OUTPUT_FORMAT";
  private int aXE;
  private String aXI;
  private String aXJ;
  private String aXN;
  private int aXR;
  private int aXH;
  private int aXF;
  private int aXD;
  private int aXM;
  private int aXK;
  private int aXQ;
  private int aXO;
  private int aXG;
  private int aXT;
  private int aXL;
  private FieldDefinitions aXP = new FieldDefinitions();
  private FieldDefinition aXS;
  
  public MappingDefinition() {}
  
  public MappingDefinition(Locale paramLocale)
  {
    super(paramLocale);
  }
  
  public void set(MappingDefinition paramMappingDefinition)
  {
    this.aXE = paramMappingDefinition.getMappingID();
    this.aXI = new String(paramMappingDefinition.getName() == null ? "" : paramMappingDefinition.getName());
    this.aXJ = new String(paramMappingDefinition.getDescription() == null ? "" : paramMappingDefinition.getDescription());
    this.aXN = new String(paramMappingDefinition.getOutputFormatName() == null ? "" : paramMappingDefinition.getOutputFormatName());
    this.aXR = paramMappingDefinition.getInputFormatType();
    this.aXH = paramMappingDefinition.getFieldDelimiterType();
    this.aXF = paramMappingDefinition.getRecordDelimiterType();
    this.aXD = paramMappingDefinition.getNumHeaderLines();
    this.aXM = paramMappingDefinition.getRecordLength();
    this.aXK = paramMappingDefinition.getNumHeaderChars();
    this.aXQ = paramMappingDefinition.getDateFormatType();
    this.aXO = paramMappingDefinition.getDateSeparatorType();
    this.aXG = paramMappingDefinition.getMoneyFormatType();
    this.aXT = paramMappingDefinition.getMatchRecordsBy();
    this.aXL = paramMappingDefinition.getUpdateRecordsBy();
    this.aXP = ((FieldDefinitions)paramMappingDefinition.getFieldDefinitions().clone());
    if (paramMappingDefinition.getCurrentFieldDefinition() != null)
    {
      this.aXS = new FieldDefinition();
      this.aXS.set(paramMappingDefinition.getCurrentFieldDefinition());
    }
  }
  
  public int getMappingID()
  {
    return this.aXE;
  }
  
  public void setMappingID(int paramInt)
  {
    this.aXE = paramInt;
  }
  
  public String getName()
  {
    return this.aXI;
  }
  
  public void setName(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    this.aXI = paramString;
  }
  
  public String getDescription()
  {
    return this.aXJ;
  }
  
  public void setDescription(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    this.aXJ = paramString;
  }
  
  public String getOutputFormatName()
  {
    return this.aXN;
  }
  
  public void setOutputFormatName(String paramString)
  {
    if (paramString == null) {
      paramString = "";
    }
    this.aXN = paramString;
  }
  
  public int getInputFormatType()
  {
    return this.aXR;
  }
  
  public void setInputFormatType(int paramInt)
  {
    this.aXR = paramInt;
  }
  
  public void setInputFormatType(String paramString)
  {
    this.aXR = Integer.parseInt(paramString);
  }
  
  public int getFieldDelimiterType()
  {
    return this.aXH;
  }
  
  public void setFieldDelimiterType(int paramInt)
  {
    this.aXH = paramInt;
  }
  
  public void setFieldDelimiterType(String paramString)
  {
    this.aXH = Integer.parseInt(paramString);
  }
  
  public int getRecordDelimiterType()
  {
    return this.aXF;
  }
  
  public void setRecordDelimiterType(int paramInt)
  {
    this.aXF = paramInt;
  }
  
  public void setRecordDelimiterType(String paramString)
  {
    this.aXF = Integer.parseInt(paramString);
  }
  
  public int getNumHeaderLines()
  {
    return this.aXD;
  }
  
  public void setNumHeaderLines(int paramInt)
  {
    this.aXD = paramInt;
  }
  
  public void setNumHeaderLines(String paramString)
  {
    this.aXD = Integer.parseInt(paramString);
  }
  
  public int getRecordLength()
  {
    return this.aXM;
  }
  
  public void setRecordLength(int paramInt)
  {
    this.aXM = paramInt;
  }
  
  public void setRecordLength(String paramString)
  {
    this.aXM = Integer.parseInt(paramString);
  }
  
  public int getNumHeaderChars()
  {
    return this.aXK;
  }
  
  public void setNumHeaderChars(int paramInt)
  {
    this.aXK = paramInt;
  }
  
  public void setNumHeaderChars(String paramString)
  {
    this.aXK = Integer.parseInt(paramString);
  }
  
  public String getDateFormat()
  {
    String str = "";
    switch (this.aXO)
    {
    case 0: 
      str = "/";
      break;
    case 1: 
      str = "-";
      break;
    case 2: 
      str = ".";
    }
    switch (this.aXQ)
    {
    case 0: 
      return "yyyy" + str + "MM" + str + "dd";
    case 1: 
      return "yy" + str + "MM" + str + "dd";
    case 2: 
      return "MM" + str + "dd" + str + "yyyy";
    case 3: 
      return "MM" + str + "dd" + str + "yy";
    case 4: 
      return "dd" + str + "MM" + str + "yyyy";
    case 5: 
      return "dd" + str + "MM" + str + "yy";
    }
    return "";
  }
  
  public int getDateFormatType()
  {
    return this.aXQ;
  }
  
  public void setDateFormatType(int paramInt)
  {
    this.aXQ = paramInt;
  }
  
  public void setDateFormatType(String paramString)
  {
    this.aXQ = Integer.parseInt(paramString);
  }
  
  public int getDateSeparatorType()
  {
    return this.aXO;
  }
  
  public void setDateSeparatorType(int paramInt)
  {
    this.aXO = paramInt;
  }
  
  public void setDateSeparatorType(String paramString)
  {
    this.aXO = Integer.parseInt(paramString);
  }
  
  public int getMoneyFormatType()
  {
    return this.aXG;
  }
  
  public void setMoneyFormatType(int paramInt)
  {
    this.aXG = paramInt;
  }
  
  public void setMoneyFormatType(String paramString)
  {
    this.aXG = Integer.parseInt(paramString);
  }
  
  public int getMatchRecordsBy()
  {
    return this.aXT;
  }
  
  public void setMatchRecordsBy(int paramInt)
  {
    this.aXT = paramInt;
  }
  
  public void setMatchRecordsBy(String paramString)
  {
    this.aXT = Integer.parseInt(paramString);
  }
  
  public int getUpdateRecordsBy()
  {
    return this.aXL;
  }
  
  public void setUpdateRecordsBy(int paramInt)
  {
    this.aXL = paramInt;
  }
  
  public void setUpdateRecordsBy(String paramString)
  {
    this.aXL = Integer.parseInt(paramString);
  }
  
  public FieldDefinitions getFieldDefinitions()
  {
    return this.aXP;
  }
  
  public void setFieldDefinitions(FieldDefinitions paramFieldDefinitions)
  {
    this.aXP = paramFieldDefinitions;
  }
  
  public void setCurrentFieldDefinitionName(String paramString)
  {
    if (this.aXP == null) {
      return;
    }
    this.aXS = this.aXP.findByName(paramString);
    if (this.aXS == null)
    {
      this.aXS = new FieldDefinition(paramString);
      this.aXP.add(this.aXS);
    }
  }
  
  public FieldDefinition getCurrentFieldDefinition()
  {
    return this.aXS;
  }
  
  public void setFieldNumber(String paramString)
  {
    if (this.aXS == null) {
      return;
    }
    this.aXS.setFieldNumber(paramString);
  }
  
  public void setFieldStart(String paramString)
  {
    if (this.aXS == null) {
      return;
    }
    this.aXS.setFieldStart(paramString);
  }
  
  public void setFieldEnd(String paramString)
  {
    if (this.aXS == null) {
      return;
    }
    this.aXS.setFieldEnd(paramString);
  }
  
  public void setDefaultValue(String paramString)
  {
    if (this.aXS == null) {
      return;
    }
    this.aXS.setDefaultValue(paramString);
  }
  
  public boolean isFilterable(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "=");
    if (localStringTokenizer.countTokens() >= 2)
    {
      String str1 = localStringTokenizer.nextToken();
      String str2 = localStringTokenizer.nextToken();
      if (str1.equalsIgnoreCase("NAME")) {
        return this.aXI == null ? false : this.aXI.equals(str2);
      }
      if (str1.equalsIgnoreCase("DESCRIPTION")) {
        return this.aXJ == null ? false : this.aXJ.equals(str2);
      }
      if (str1.equalsIgnoreCase("OUTPUT_FORMAT")) {
        return this.aXN == null ? false : this.aXN.equals(str2);
      }
    }
    return false;
  }
  
  public int compareTo(Object paramObject)
    throws ClassCastException
  {
    return compare(paramObject, "NAME");
  }
  
  public int compare(Object paramObject, String paramString)
    throws ClassCastException
  {
    MappingDefinition localMappingDefinition = (MappingDefinition)paramObject;
    Collator localCollator = doGetCollator();
    if (paramString.equalsIgnoreCase("NAME")) {
      return localCollator.compare(this.aXI.toUpperCase(), localMappingDefinition.aXI.toUpperCase());
    }
    if (paramString.equalsIgnoreCase("DESCRIPTION")) {
      return localCollator.compare(this.aXJ, localMappingDefinition.aXJ);
    }
    if (paramString.equalsIgnoreCase("OUTPUT_FORMAT")) {
      return localCollator.compare(this.aXN, localMappingDefinition.aXN);
    }
    return super.compare(paramObject, paramString);
  }
  
  public boolean set(String paramString1, String paramString2)
  {
    if (paramString1.equalsIgnoreCase("NAME"))
    {
      this.aXI = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("DESCRIPTION"))
    {
      this.aXJ = paramString2;
      return true;
    }
    if (paramString1.equalsIgnoreCase("OUTPUT_FORMAT"))
    {
      this.aXN = paramString2;
      return true;
    }
    return super.set(paramString1, paramString2);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.fileimporter.MappingDefinition
 * JD-Core Version:    0.7.0.1
 */