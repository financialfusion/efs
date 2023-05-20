package com.ffusion.bankreport;

import java.io.PrintStream;
import java.io.PrintWriter;

public class BRException
  extends Exception
{
  private int jdField_if = -1;
  private Throwable jdField_do = null;
  private String a = null;
  public static final int UNKNOWN_ERROR = 60000;
  public static final int INITIALIZATION_FAILED = 60001;
  public static final int NOT_INITIALIZED = 60002;
  public static final int CACHE_ERROR = 60003;
  public static final int DB_EXCEPTION = 60010;
  public static final int CONNECTION_POOL_EXCEPTION = 60011;
  public static final int PROFILE_EXCEPTION = 60012;
  public static final int NULL_REPORT_TYPE = 60100;
  public static final int NULL_ACCOUNT = 60101;
  public static final int NULL_ACCOUNT_ID = 60102;
  public static final int NULL_BANK_ID = 60103;
  public static final int NULL_ROUTING_NUM = 60104;
  public static final int REMOVE_ACCOUNT_DATA_BANK_ID_MISMATCH = 60105;
  public static final int NULL_GENERATED_TIME = 60106;
  public static final int NEGATIVE_NUM_DAYS = 60107;
  public static final int INVALID_REPORT_ID = 60108;
  public static final int NULL_BANK_REPORT = 60109;
  public static final int NULL_LINE_ITEM = 60110;
  public static final int NULL_REPORT_CRITERIA = 60111;
  public static final int SEARCH_CRITERIA_MISSING_REPORT_ID = 60112;
  public static final int MISSING_REPORT_DEFINITION_FOR_REPORT_TYPE = 60113;
  public static final int NULL_BANK_REPORT_DEFN = 60114;
  public static final int PROCESSOR_INIT_FAILED = 60300;
  public static final int REPORT_PROCESSING_FAILED = 60301;
  public static final int PROCESSOR_NOT_FOUND = 60302;
  public static final int ENTITLEMENT_CHECK_FAILED = 60400;
  
  public BRException(int paramInt, String paramString, Throwable paramThrowable)
  {
    this.jdField_if = paramInt;
    this.a = paramString;
    this.jdField_do = paramThrowable;
  }
  
  public BRException(String paramString, Throwable paramThrowable)
  {
    this.a = paramString;
    this.jdField_do = paramThrowable;
  }
  
  public BRException(int paramInt, String paramString)
  {
    this.jdField_if = paramInt;
    this.a = paramString;
  }
  
  public BRException(int paramInt, Throwable paramThrowable)
  {
    this.jdField_if = paramInt;
    this.jdField_do = paramThrowable;
  }
  
  public int getErrorCode()
  {
    return this.jdField_if;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.a != null) {
      localStringBuffer.append(this.a).append(" ");
    }
    if (this.jdField_if != -1) {
      localStringBuffer.append(a()).append(" ");
    }
    if (this.jdField_do != null) {
      localStringBuffer.append(this.jdField_do.getMessage());
    }
    return localStringBuffer.toString();
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.jdField_do != null) {
      this.jdField_do.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.jdField_do != null) {
      this.jdField_do.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.jdField_do != null) {
      this.jdField_do.printStackTrace(paramPrintStream);
    }
  }
  
  private String a()
  {
    String str = "A bank report error has occurred.";
    switch (this.jdField_if)
    {
    case 60010: 
      str = "A database related error has occurred.";
      break;
    case 60011: 
      str = "A database connection pool related error has occurred.";
      break;
    case 60012: 
      str = "A profile database related error has occurred.";
      break;
    case 60100: 
      str = "The type of the report cannot be null.";
      break;
    case 60101: 
      str = "The account cannot be null.";
      break;
    case 60102: 
      str = "The account ID of the specified account cannot be null.";
      break;
    case 60103: 
      str = "The bank ID of the specified account cannot be null.";
      break;
    case 60104: 
      str = "The routing number of the specified account cannot be null.";
      break;
    case 60105: 
      str = "The business/consumer is not entitled to delete the report data belonging to the specified account.";
      break;
    case 60106: 
      str = "The generation time of the report cannot be null.";
      break;
    case 60107: 
      str = "The value for number of days cannnot be negative.";
      break;
    case 60108: 
      str = "The specified report ID is invalid.";
      break;
    case 60109: 
      str = "The bank report cannot be null.";
      break;
    case 60110: 
      str = "The bank report line data be null.";
      break;
    case 60111: 
      str = "No criteria has been specified for the bank report.";
      break;
    case 60112: 
      str = "The report ID has not been specified in the report search criteria.";
      break;
    case 60113: 
      str = "No report definition exists for the given report type.";
      break;
    case 60114: 
      str = "No report definition exists for the given report key.";
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.bankreport.BRException
 * JD-Core Version:    0.7.0.1
 */