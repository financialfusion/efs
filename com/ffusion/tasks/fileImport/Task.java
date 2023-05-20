package com.ffusion.tasks.fileImport;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final int ERROR_MAPPING_DEFINITION_NOT_FOUND = 23000;
  public static final int ERROR_MAPPING_DEFINITIONS_NOT_FOUND = 23001;
  public static final int ERROR_UPLOAD_RESULTS_NOT_FOUND = 23002;
  public static final int ERROR_UPDATED_FIELDS_NOT_FOUND = 23003;
  public static final int ERROR_ACH_CLASS_CODE_NOT_FOUND = 23004;
  public static final int ERROR_ACH_CLASS_CODE_TEXT_NOT_FOUND = 23005;
  public static final int ERROR_PPAY_CHECK_RECORDS_NOT_FOUND = 23006;
  public static final int ERROR_IMPORT_ERRORS_NOT_FOUND = 23007;
  public static final int ERROR_FILE_TYPE_NOT_FOUND = 23008;
  public static final int ERROR_DUPLICATE_MAPPING_NAME = 23009;
  public static final int ERROR_INVALID_FIELD_NUMBER = 23010;
  public static final int ERROR_INVALID_FIELD_START = 23011;
  public static final int ERROR_INVALID_FIELD_END = 23012;
  public static final int ERROR_INVALID_MAP_DEFINITION_NAME = 23013;
  public static final int ERROR_INVALID_MAP_DEFINITION_DESCRIPTION = 23014;
  public static final int ERROR_ACH_CLASS_CODE_NOT_ENTITLED = 23015;
  public static final int ERROR_FIELD_START_NOT_PARSEABLE = 23016;
  public static final int ERROR_FIELD_END_NOT_PARSEABLE = 23017;
  public static final int ERROR_FIXED_LENGTH_FIELDS_OVERLAP = 23018;
  public static final int ERROR_FIELD_END_EXCEEDS_RECORD_END = 23019;
  public static final int ERROR_PPAY_CHECK_RECORDS_EMPTY = 23020;
  public static final int ERROR_MESSAGE_NAME_REQUIRED = 23021;
  public static final int ERROR_MESSAGE_NAME_TOO_LONG = 23022;
  public static final int ERROR_MESSAGE_DESC_TOO_LONG = 23023;
  public static final int ERROR_MESSAGE_DUPLICATE_MAPPING = 23024;
  public static final int ERROR_MESSAGE_RECORD_DELIMITER_OR_LENGTH_REQUIRED = 23025;
  public static final int ERROR_MESSAGE_SPECIFY_ONLY_DELIMITER_OR_LENGTH = 23026;
  public static final int ERROR_MESSAGE_REQUIRED_FIELDS_MISSING = 23027;
  public static final int MAX_MAP_DEFINITION_NAME_LENGTH = 255;
  public static final int MAX_MAP_DEFINITION_DESCRIPTION_LENGTH = 255;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.fileImport.Task
 * JD-Core Version:    0.7.0.1
 */