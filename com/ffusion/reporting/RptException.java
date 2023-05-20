package com.ffusion.reporting;

import java.io.PrintStream;
import java.io.PrintWriter;

public class RptException
  extends Exception
{
  public static final int RPT_OKAY = 0;
  public static final int RPT_CANT_INIT = 1;
  public static final int RPT_CANT_GET_CONN = 2;
  public static final int RPT_DOES_NOT_EXIST = 3;
  public static final int RPT_HAS_NO_ID = 4;
  public static final int RPT_SQL_EXCEPTION = 5;
  public static final int RPT_USER_DOESNT_OWN_RPT = 6;
  public static final int RPT_UNKNOWN_FORMAT = 7;
  public static final int RPT_TYPE_NOT_EXIST = 8;
  public static final int RPT_FIELD_MISSING = 9;
  public static final int RPT_MISSING_EXPORTER = 10;
  public static final int RPT_MISSING_FILENAME = 11;
  public static final int RPT_INVALID_FILENAME = 12;
  public static final int RPT_INVALID_BANKID = 13;
  public static final int RPT_INVALID_USERNAME = 14;
  public static final int RPT_MISSING_USER = 15;
  public static final int RPT_ERROR_CREATING_DIR = 16;
  public static final int RPT_RESULT_INTERNAL_ERROR = 100;
  public static final int RPT_RESULT_NULL_DATADIMENSION_NOTNULL_DATA = 101;
  public static final int RPT_RESULT_NOTNULL_DATADIMENSION_NULL_DATA = 102;
  public static final int RPT_RESULT_RPTCOLUMNS_DATADIMENSIONCOLUMNS_SIZE_MISMATCH = 103;
  public static final int RPT_RESULT_RPTROWS_DATADIMENSIONROWS_SIZE_MISMATCH = 104;
  public static final int RPT_RESULT_RPTHEADING_LABEL_MISSING = 105;
  public static final int RPT_RESULT_RPTDATAITEMS_RPTCOLUMNS_SIZE_MISMATCH = 106;
  public static final int RPT_RESULT_RPTDATAITEMS_RPTCOLUMNS_DATATYPE_MISMATCH = 107;
  public static final int RPT_RESULT_NULL_DATA = 108;
  public static final int RPT_RESULT_INVALID_DATATYPE = 109;
  public static final int RPT_RESULT_NULL_DATAITEMS = 110;
  public static final int RPT_RESULT_NULL_CRITERIA = 111;
  public static final int RPT_INVALID_DATE_RANGE_TYPE = 112;
  public static final int RPT_INVALID_START_DATE = 113;
  public static final int RPT_INVALID_END_DATE = 114;
  public static final int RPT_INVALID_DATE_RANGE = 115;
  public static final int RPT_INVALID_CALENDAR = 116;
  public static final int RPT_INVALID_START_TIME = 117;
  public static final int RPT_INVALID_END_TIME = 118;
  public static final int RPT_UNABLE_TO_CALCULATE_PREV_BUSINESS_DAY = 119;
  public static final int RPT_INVALID_DIR_TYPE = 120;
  public static final int RPT_START_DATE_AFTER_END_DATE = 121;
  public static final int RPT_NO_SESSION_LOGIN_TIME = 122;
  public static final int RPT_WRITER_PDF_INIT_ERROR = 200;
  public static final int RPT_WRITER_OBJ_NOT_SUPPORTED = 201;
  public static final int RPT_WRITER_IO_EXCEPTION = 202;
  public static final int RPT_WRITER_MISSING_RESPONSE = 203;
  public static final int RPT_WRITER_NO_STREAM = 204;
  public static final int RPT_EXP_INVALID_STATE_TRANSITION = 300;
  public static final int RPT_EXP_MISSING_METADATA = 301;
  public static final int RPT_EXP_INVALID_METADATA = 302;
  public static final int RPT_EXP_MISSING_EXPORTER = 303;
  public static final int RPT_EXP_INVALID_EXPORTER = 304;
  public static final int RPT_EXP_INVALID_PIECE = 305;
  public static final int RPT_EXP_MISSING_CSS = 306;
  public static final int RPT_EXP_ROW_TOO_LARGE_FOR_PAGE = 307;
  public static final int RPT_EXP_INVALID_BG_COLOR = 308;
  private Throwable jdField_if;
  private int a;
  
  public RptException(int paramInt)
  {
    this.a = paramInt;
  }
  
  public RptException(int paramInt, String paramString)
  {
    super(paramString);
    this.a = paramInt;
  }
  
  public RptException(int paramInt, String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.a = paramInt;
    this.jdField_if = paramThrowable;
  }
  
  public RptException(int paramInt, Throwable paramThrowable)
  {
    this.a = paramInt;
    this.jdField_if = paramThrowable;
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.jdField_if != null) {
      this.jdField_if.printStackTrace(paramPrintStream);
    }
  }
  
  public Throwable getChained()
  {
    return this.jdField_if;
  }
  
  public int getErrorCode()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.reporting.RptException
 * JD-Core Version:    0.7.0.1
 */