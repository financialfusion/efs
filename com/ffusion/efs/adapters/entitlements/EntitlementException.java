package com.ffusion.efs.adapters.entitlements;

import java.io.PrintStream;
import java.io.PrintWriter;

public class EntitlementException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public Exception childException = null;
  public static final int ERROR_NOT_INITIALIZED = 1;
  public static final int ERROR_DB_EXCEPTION = 2;
  public static final int ERROR_ENTITLEMENT_GROUP_NOT_FOUND = 3;
  public static final int ERROR_GROUP_MEMBERS_NOT_FOUND = 4;
  public static final int ERROR_MEMBER_NOT_FOUND = 5;
  public static final int ERROR_DUPLICATE_LIMIT = 6;
  public static final int ERROR_ADDING_WHILE_CACHEON = 7;
  public static final int ERROR_DELETING_WHILE_CACHEON = 8;
  public static final int ERROR_MODIFYING_WHILE_CACHEON = 9;
  public static final int ERROR_TARGET_GROUPS_DIFFERENT = 10;
  public static final int ERROR_CANT_GEN_REPORT = 11;
  public static final int ERROR_ADMIN_DOESNT_EXIST = 12;
  public static final int ERROR_RESTRICTED_ENTITLEMENT_NOT_FOUND = 13;
  public static final int ERROR_LIMIT_NOT_FOUND = 14;
  public static final int ERROR_DUPLICATE_ENTITLEMENT = 15;
  public static final int ERROR_ENT_NOT_VALID = 16;
  public static final int ERROR_CANT_EXTEND_GROUP = 17;
  public static final int ERROR_NOT_GROUP_ADMINISTRATOR = 18;
  public static final int ERROR_GROUP_MEMBER_TO_GET_NOT_FOUND = 19;
  public static final int ERROR_LIMIT_PERIOD_INVALID = 20;
  public static final int ERROR_OBJECT_TYPE_NOT_FOUND = 21;
  public static final int ERROR_ADMIN_GRANTEE_GROUP_NOT_FOUND = 22;
  public static final int ERROR_ADMIN_GRANTEE_GROUP_MEMBER_NOT_FOUND = 23;
  public static final int ERROR_GROUP_TO_ADMIN_NOT_FOUND = 24;
  public static final int ERROR_CANT_SHUTDOWN = 25;
  public static final int ERROR_MULTI_ENT_NO_OPERATIONS = 26;
  public static final int ERROR_MULTI_ENT_NO_OBJECT_TYPES = 27;
  public static final int ERROR_MULTI_ENT_NO_OBJECT_IDS = 28;
  public static final int ERROR_MULTI_ENT_OBJECT_MISMATCH = 29;
  public static final int ERROR_TOO_MANY_ACCOUNTS = 30;
  public static final int ERROR_ACCOUNT_GROUP_TYPE_NOT_SUPPORTED = 31;
  public static final int ERROR_UNABLE_TO_CHECK_ACCOUNT_ENTITLEMENT = 32;
  public static final int ERROR_DB_TYPE_UNSUPPORTED = 33;
  public static final int ERROR_UNABLE_TO_GET_DB_TYPE = 34;
  public static final int ERROR_CANT_CONVERT_CURRENCY = 35;
  public static final int ERROR_NO_FX_SERVICE = 36;
  
  public EntitlementException(EntitlementException paramEntitlementException)
  {
    this.when = paramEntitlementException.when;
    this.where = paramEntitlementException.where;
    this.why = paramEntitlementException.why;
    this.code = paramEntitlementException.code;
    this.childException = paramEntitlementException;
  }
  
  public EntitlementException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public EntitlementException(Exception paramException, String paramString1, int paramInt, String paramString2)
  {
    this.childException = paramException;
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public EntitlementException(Exception paramException, String paramString, int paramInt)
  {
    this.childException = paramException;
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public EntitlementException(String paramString, int paramInt)
  {
    this.where = paramString;
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public EntitlementException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public EntitlementException(int paramInt, Exception paramException)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
    this.childException = paramException;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((this.where != null) && (this.where.length() > 0)) {
      localStringBuffer.append(this.where);
    }
    if ((this.why != null) && (this.why.length() > 0)) {
      localStringBuffer.append(": ").append(this.why);
    }
    if (this.code != 0) {
      localStringBuffer.append(": error code:" + String.valueOf(this.code) + " ");
    }
    if (this.childException != null) {
      localStringBuffer.append(this.childException.getMessage());
    }
    return localStringBuffer.toString();
  }
  
  public void printStackTrace()
  {
    super.printStackTrace();
    if (this.childException != null) {
      this.childException.printStackTrace();
    }
  }
  
  public void printStackTrace(PrintWriter paramPrintWriter)
  {
    super.printStackTrace(paramPrintWriter);
    if (this.childException != null) {
      this.childException.printStackTrace(paramPrintWriter);
    }
  }
  
  public void printStackTrace(PrintStream paramPrintStream)
  {
    super.printStackTrace(paramPrintStream);
    if (this.childException != null) {
      this.childException.printStackTrace(paramPrintStream);
    }
  }
}


/* Location:           D:\drops\jd\jars\ffiutil.jar
 * Qualified Name:     com.ffusion.efs.adapters.entitlements.EntitlementException
 * JD-Core Version:    0.7.0.1
 */