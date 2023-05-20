package com.ffusion.fileimporter.fileadapters.custom;

import com.ffusion.beans.fileimporter.OutputFormat;
import com.ffusion.beans.fileimporter.OutputFormatDisplayNames;
import com.ffusion.csil.FIException;
import java.util.ArrayList;
import java.util.HashMap;

public abstract interface ICustomAdapter
{
  public static final int FORMAT_DELIMITED = 0;
  public static final int FORMAT_FIXED_WIDTH = 1;
  public static final int FIELD_DELIMITER_TAB = 0;
  public static final int FIELD_DELIMITER_SEMICOLON = 1;
  public static final int FIELD_DELIMITER_COMMA = 2;
  public static final int FIELD_DELIMITER_SPACE = 3;
  public static final int FIELD_DELIMITER_PIPE = 4;
  public static final int RECORD_DELIMITER_CR = 0;
  public static final int RECORD_DELIMITER_CRLF = 1;
  public static final int RECORD_DELIMITER_NONE = 2;
  public static final int DATE_FORMAT_YYYYMMDD = 0;
  public static final int DATE_FORMAT_YYMMDD = 1;
  public static final int DATE_FORMAT_MMDDYYYY = 2;
  public static final int DATE_FORMAT_MMDDYY = 3;
  public static final int DATE_FORMAT_DDMMYYYY = 4;
  public static final int DATE_FORMAT_DDMMYY = 5;
  public static final int DATE_SEPARATOR_SLASH = 0;
  public static final int DATE_SEPARATOR_HYPHEN = 1;
  public static final int DATE_SEPARATOR_PERIOD = 2;
  public static final int DATE_SEPARATOR_NONE = 3;
  public static final int MONEY_FORMAT_AS_IS = 0;
  public static final int MONEY_FORMAT_2_DECIMIALS = 1;
  public static final int MONEY_FORMAT_4_DECIMALS = 2;
  public static final int MATCH_RECORDS_NAME = 0;
  public static final int MATCH_RECORDS_ID = 1;
  public static final int MATCH_RECORDS_NAME_ID = 2;
  public static final int UPDATE_ENTRIES_EXISTING = 0;
  public static final int UPDATE_ENTIRES_NEW = 1;
  public static final int UPDATE_ENTRIES_EXISTING_NEW = 2;
  
  public abstract ArrayList getOutputFormatNames(HashMap paramHashMap)
    throws FIException;
  
  public abstract ArrayList getOutputFormatNamesByCategory(String paramString, HashMap paramHashMap)
    throws FIException;
  
  public abstract OutputFormat getOutputFormat(String paramString, HashMap paramHashMap)
    throws FIException;
  
  public abstract OutputFormatDisplayNames getOutputFormatDisplayNames(String paramString, HashMap paramHashMap)
    throws FIException;
  
  public abstract OutputFormatDisplayNames getOutputFormatDisplayNamesByCategory(String paramString1, String paramString2, HashMap paramHashMap)
    throws FIException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.custom.ICustomAdapter
 * JD-Core Version:    0.7.0.1
 */