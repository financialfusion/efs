package com.ffusion.beans.util;

import java.io.PrintStream;
import java.io.PrintWriter;

public class UtilException
  extends Exception
{
  private int jdField_do = -1;
  private Throwable a = null;
  private String jdField_if = null;
  public static final int UNKNOWN_ERROR = 0;
  public static final int SERVICE_INIT_FAILED = 1;
  public static final int CANNOT_LOAD_CONFIG_XML = 2;
  public static final int CANNOT_PARSE_CONFIG_XML = 3;
  public static final int INVALID_COUNTRY_CODE = 4;
  public static final int INVALID_USER = 5;
  public static final int INVALID_PHONE_NUMBER = 6;
  public static final int INVALID_ZIP_CODE = 7;
  public static final int INVALID_SSN = 8;
  public static final int UNSUPPORTED_LANGUAGE = 9;
  
  public UtilException(int paramInt, String paramString, Throwable paramThrowable)
  {
    this.jdField_do = paramInt;
    this.jdField_if = paramString;
    this.a = paramThrowable;
  }
  
  public UtilException(String paramString, Throwable paramThrowable)
  {
    this.jdField_if = paramString;
    this.a = paramThrowable;
  }
  
  public UtilException(int paramInt, String paramString)
  {
    this.jdField_do = paramInt;
    this.jdField_if = paramString;
  }
  
  public UtilException(String paramString)
  {
    this.jdField_if = paramString;
  }
  
  public UtilException(int paramInt, Throwable paramThrowable)
  {
    this.jdField_do = paramInt;
    this.a = paramThrowable;
  }
  
  public int getErrorCode()
  {
    return this.jdField_do;
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.a != null) {
      this.a.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.a != null) {
      this.a.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.a != null) {
      this.a.printStackTrace(paramPrintStream);
    }
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.jdField_if != null) {
      localStringBuffer.append(this.jdField_if).append(" ");
    }
    if (this.jdField_do != -1) {
      localStringBuffer.append(a()).append(" ");
    }
    if (this.a != null) {
      localStringBuffer.append(this.a.getMessage());
    }
    return localStringBuffer.toString();
  }
  
  private String a()
  {
    String str = "";
    switch (this.jdField_do)
    {
    case 1: 
      str = "The Util Service could not be initialized. ";
      break;
    case 2: 
      str = " Unable to load Util Service configuration file fieldValidation.xml";
      break;
    case 3: 
      str = " An error occurred while parsing the Util Service configuration file fieldValidationFormats.xml";
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.util.UtilException
 * JD-Core Version:    0.7.0.1
 */