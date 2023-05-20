package com.ffusion.tw.interfaces;

import java.io.PrintStream;
import java.io.PrintWriter;

public class TWException
  extends Exception
{
  public static final int ERR_UNKNOWN = 0;
  public static final int ERR_DB_EXCEPTION = 1;
  public static final int ERR_POOL_EXCEPTION = 2;
  public static final int ERR_INIT_FAILED = 3;
  public static final int ERR_PLUGIN_LOOKUP_FAILED = 4;
  public static final int ERR_INVALID_TRANSACTION_TYPE = 100;
  public static final int ERR_INVALID_TRANSACTION_ID = 101;
  public static final int ERR_INVALID_TRANSACTION_CLEANUP_INT_VALUE = 102;
  public static final int ERR_NULL_TRANSACTION_AMOUNT = 103;
  public static final int ERR_NULL_TRANSACTION_TRACKINGID = 104;
  public static final int ERR_NULL_TRANSACTION_XMLDATA = 105;
  public static final int ERR_NEGATIVE_TRANSACTION_AMOUNT = 106;
  public static final int ERR_NULL_TRANSACTION_IDS = 107;
  public static final int ERR_NULL_TRANSACTION = 108;
  public static final int ERR_CANNOT_MODIFY_TRACKINGID = 109;
  public static final int ERR_CANNOT_MODIFY_TRANSACTIONTYPE = 110;
  public static final int ERR_NULL_USERNAME = 111;
  public static final int ERR_NULL_PASSWORD = 112;
  public static final int ERR_INVALID_SQL_DATA_TYPE = 200;
  private String a = null;
  private int jdField_if = -1;
  private Throwable jdField_do = null;
  
  public TWException(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public TWException(int paramInt, String paramString)
  {
    this.jdField_if = paramInt;
    this.a = paramString;
  }
  
  public TWException(Throwable paramThrowable, int paramInt)
  {
    this.jdField_do = paramThrowable;
    this.jdField_if = paramInt;
  }
  
  public TWException(Throwable paramThrowable, int paramInt, String paramString)
  {
    this.jdField_do = paramThrowable;
    this.jdField_if = paramInt;
    this.a = paramString;
  }
  
  public TWException(Throwable paramThrowable, String paramString)
  {
    this.jdField_do = paramThrowable;
    this.a = paramString;
  }
  
  public void setErrorMessage(String paramString)
  {
    this.a = paramString;
  }
  
  public String getErrorMessage()
  {
    return this.a;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.a != null) {
      localStringBuffer.append(this.a);
    }
    if (this.jdField_if != -1) {
      localStringBuffer.append(a());
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
    if (this.jdField_if == 1) {
      return " A database related error has occurred. ";
    }
    if (this.jdField_if == 2) {
      return " A database connectionpool related error has occurred. ";
    }
    if (this.jdField_if == 3) {
      return " The Transaction Warehouse could not be initialized. ";
    }
    if (this.jdField_if == 4) {
      return " Unable to lookup Transaction Warehouse plugin. ";
    }
    if (this.jdField_if == 100) {
      return " The provided transaction type is  not supported. ";
    }
    if (this.jdField_if == 101) {
      return " There is no transaction in the database with the given transaction id ";
    }
    if (this.jdField_if == 200) {
      return " The given SQL data type is not supported. ";
    }
    if (this.jdField_if == 102) {
      return " The value specified for number of days is not a positive integer. ";
    }
    if (this.jdField_if == 103) {
      return " The amount for the given transaction has not been specified. ";
    }
    if (this.jdField_if == 104) {
      return " The trackingID for the given transaction has not been specified. ";
    }
    if (this.jdField_if == 105) {
      return " The XML data for the given transaction has not been specified. ";
    }
    if (this.jdField_if == 106) {
      return " The amount for the given transaction cannot be negative. ";
    }
    if (this.jdField_if == 107) {
      return " The transaction IDs have not been specified.  ";
    }
    if (this.jdField_if == 108) {
      return " The transaction beans have not been specified.  ";
    }
    if (this.jdField_if == 109) {
      return " The trackingID cannot be modified.  ";
    }
    if (this.jdField_if == 110) {
      return " The transaction type cannot be modified.  ";
    }
    if (this.jdField_if == 111) {
      return " The username has not been specified.  ";
    }
    if (this.jdField_if == 112) {
      return " The password has not been specified.  ";
    }
    return "";
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tw.interfaces.TWException
 * JD-Core Version:    0.7.0.1
 */