package com.ffusion.checkimaging;

public class CheckImagingException
  extends Exception
{
  public long when = 0L;
  public String where = null;
  public String why = null;
  public int code = -1;
  public Exception childException = null;
  public static final int ERROR_INVALID_SERVICE = 29100;
  public static final int ERROR_NOT_INITIALIZED = 29101;
  public static final int ERROR_FROMADDRESS_INVALID = 29105;
  public static final int ERROR_TOADDRESS_INVALID = 29106;
  public static final int ERROR_MESSAGING_IO_EXCEPTION = 29107;
  public static final int ERROR_DELETE_RESULTS_EXCEPTION = 29108;
  public static final int ERROR_UNABLE_TO_COMPLETE_REQUEST = 29109;
  public static final int ERROR_GET_RESULTS_EXCEPTION = 1010001;
  public static final int ERROR_GET_ITEM_EXCEPTION = 1010002;
  public static final int ERROR_UNKNOWN = 1010003;
  public static final int ERROR_FEE_NOT_LOGGED = 1010004;
  public static final int ERROR_NONE = 0;
  public static final int ERROR_PARTIAL_SUCCESS = 1010400;
  public static final int ERROR_MISSING_VIEW = 1010401;
  public static final int ERROR_MAX_ITEMS_EXCEEDED = 1010402;
  public static final int ERROR_MAX_ITEMS_MISSING_VIEWS = 1010403;
  public static final int ERROR_NO_HITS = 1010600;
  public static final int ERROR_IN_REQUEST = 1010800;
  public static final int ERROR_REQUEST_TOO_LARGE = 1010802;
  public static final int ERROR_NO_CRITERIA = 1010820;
  public static final int ERROR_CRITERIA_FIELD_INVALID_LENGTH = 1010821;
  public static final int ERROR_CRITERIA_FIELD_UNSEARCHABLE = 1010822;
  public static final int ERROR_CRITERIA_FIELD_UNDEFINED = 1010823;
  public static final int ERROR_INVALID_REQUEST_TYPE = 1010824;
  public static final int ERROR_REPOSITORY_LIST_UNDEFINED = 1010825;
  public static final int ERROR_INVALID_OPERATORS = 1010826;
  public static final int ERROR_INVALID_CONNECTORS = 1010827;
  public static final int ERROR_INVALID_VIEW = 1010828;
  public static final int ERROR_MAXIMUM_FIELDS = 1010829;
  public static final int ERROR_DIRECTEDID_TOO_LONG = 1010830;
  public static final int ERROR_ITEM_HANDLE_TOO_LONG = 1010831;
  public static final int ERROR_CONFIG_DATA = 1010832;
  public static final int ERROR_VALUE_FORMAT = 1010833;
  public static final int ERROR_INVALID_HANDLE = 1010834;
  public static final int ERROR_HANDLE_REJECTED = 1010835;
  public static final int ERROR_SYSTEM_ERROR = 1011200;
  public static final int ERROR_TIME_OUT = 1011240;
  public static final int ERROR_FAILOVER_TOO_SLOW = 1011241;
  public static final int ERROR_COMPUTE_STORAGE_TIER = 1011243;
  public static final int ERROR_SERVER_NOT_FOUND = 1011244;
  public static final int ERROR_SITE_NOT_FOUND = 1011245;
  public static final int ERROR_COMMUNICATION_ERROR = 1011246;
  public static final int ERROR_INVALID_DATA_FORMAT = 1011247;
  public static final int ERROR_ALL_ITEMS_FAILED = 1011248;
  public static final int ERROR_MAX_HITS = 1011249;
  public static final int ERROR_UNABLE_TO_CONNECT = 1011250;
  public static final int ERROR_SEARCH_FAILED = 1011251;
  public static final int ERROR_LIBRARY_FAILED = 1011252;
  public static final int ERROR_RETRIEVE_IMAGE = 1011253;
  public static final int ERROR_KEY_SEARCH = 1011254;
  public static final int ERROR_KEY_MATCH = 1011255;
  public static final int ERROR_INTERNAL_IRB_ERROR = 1011600;
  public static final int ERROR_SERVER_RESOURCE = 1012000;
  public static final int ERROR_DATA_CREATION = 1012800;
  
  public CheckImagingException(String paramString1, int paramInt, String paramString2)
  {
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public CheckImagingException(Exception paramException, String paramString1, int paramInt, String paramString2)
  {
    this.childException = paramException;
    this.where = paramString1;
    this.when = System.currentTimeMillis();
    this.why = paramString2;
    this.code = paramInt;
  }
  
  public CheckImagingException(int paramInt)
  {
    this.when = System.currentTimeMillis();
    this.code = paramInt;
  }
  
  public CheckImagingException(int paramInt, Exception paramException)
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
  
  public int getCode()
  {
    return this.code;
  }
  
  public void setCode(int paramInt)
  {
    this.code = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.checkimaging.CheckImagingException
 * JD-Core Version:    0.7.0.1
 */