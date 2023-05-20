package com.ffusion.util.db;

import java.io.PrintStream;
import java.io.PrintWriter;

public class PoolException
  extends Exception
{
  public static final int DBPE_OKAY = 0;
  public static final int DBPE_DB_EXCEPTION = 1;
  public static final int DBPE_DB_UNKNOWN_CONN_TYPE = 2;
  public static final int DBPE_DB_DRIVER_NOT_FOUND = 3;
  public static final int DBPE_DB_COULD_NOT_CONNECT = 4;
  public static final int DBPE_DB_NO_REPOSITORY = 5;
  public static final int DBPE_DB_REPOSITORY_TOO_OLD = 6;
  public static final int DBPE_DB_REPOSITORY_TOO_NEW = 7;
  public static final int DBPE_DB_CREATING_REPOSITORY = 8;
  public static final int DBPE_DB_DESTROYING_REPOSITORY = 9;
  public static final int DBPE_DB_MAX_CONN_POOL_SIZE = 10;
  public static final int DBPE_DB_CONN_NOT_IN_POOL = 11;
  public static final int DBPE_DB_UNCOMMITTED_TRANS = 12;
  public static final int DBPE_DB_NO_CONNECTION = 13;
  public static final int DBPE_POOL_NOT_INITIALIZED = 1000;
  public static final int DBPE_POOL_ALREADY_INITIALIZED = 1001;
  public static final int DBPE_POOL_TYPE_NOT_SPECIFIED = 1003;
  public static final int DBPE_POOL_COULD_NOT_CONNECT = 1004;
  private Throwable a;
  private int jdField_if;
  
  public PoolException(int paramInt)
  {
    this.jdField_if = paramInt;
  }
  
  public PoolException(int paramInt, String paramString)
  {
    super(paramString);
    this.jdField_if = paramInt;
  }
  
  public PoolException(int paramInt, String paramString, Throwable paramThrowable)
  {
    super(paramString);
    this.jdField_if = paramInt;
    this.a = paramThrowable;
  }
  
  public PoolException(int paramInt, Throwable paramThrowable)
  {
    this.jdField_if = paramInt;
    this.a = paramThrowable;
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
  
  public Throwable getChained()
  {
    return this.a;
  }
  
  public int getErrorCode()
  {
    return this.jdField_if;
  }
  
  public String getMessage()
  {
    if ((super.getMessage() == null) && (this.a != null)) {
      return this.a.getMessage();
    }
    return super.getMessage();
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.util.db.PoolException
 * JD-Core Version:    0.7.0.1
 */