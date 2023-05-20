package com.ffusion.services;

public class PAException
  extends Exception
{
  public static final int ERROR_NONE = 0;
  public static final int BPW_SERVICE_DOWN = 1;
  public static final int CAN_NOT_ADD_WIRE_PROC_WINDOW = 2;
  public static final int CAN_NOT_MODIFY_WIRE_PROC_WINDOW = 3;
  public static final int CAN_NOT_CANCEL_WIRE_PROC_WINDOW = 4;
  public static final int CAN_NOT_GET_WIRE_PROC_WINDOWS = 5;
  public static final int CAN_NOT_GET_SCH_CATEGORY = 6;
  public static final int CAN_NOT_ADD_SCH_TYPE = 7;
  public static final int CAN_NOT_MODIFY_SCH_TYPE = 8;
  public static final int CAN_NOT_DELETE_SCH_TYPE = 9;
  public static final int CAN_NOT_GET_SCH_TYPE = 10;
  public static final int CAN_NOT_ADD_CUT_OFF = 11;
  public static final int CAN_NOT_MODIFY_CUT_OFF = 12;
  public static final int CAN_NOT_DELETE_CUT_OFF = 13;
  public static final int CAN_NOT_GET_CUT_OFFS = 14;
  public static final int CAN_NOT_GET_FULFILLMENT_SYS = 15;
  public static final int ERROR_RESUBMIT_SCHEDULE = 16;
  public static final int ERROR_RUN_SCHEDULE = 17;
  public static final int CAN_NOT_GET_CUT_OFF_ACTIVITIES = 18;
  public static final int ERROR_RERUN_CUT_OFF = 19;
  public static final int ERROR_GET_PROCESS_FILENAME = 20;
  public static final int ERROR_CLEANUP = 21;
  public static final int ERROR_DUPLICATE_CUT_OFF = 22;
  public static final int ERROR_INVALID_INSTRUCTION_TYPE = 23;
  public static final int ERROR_CUTOFFID_NOT_NULL = 24;
  public static final int ERROR_CUTOFF_EXISTS = 25;
  public static final int ERROR_CUTOFFID_NULL = 26;
  public static final int ERROR_NO_CUTOFF_TIME = 27;
  public static final int ERROR_EXTENSION_EARLIER_THAN_NOW = 28;
  public static final int ERROR_INVALID_PROCESS_TIME_FORMAT = 29;
  public static final int ERROR_INVALID_EXTENSION_TIME_FORMAT = 30;
  public static final int ERROR_INVALID_CUTOFF_FREQUENCY = 31;
  public static final int ERROR_CUTOFF_EXISTS_BUT_INACTIVE = 32;
  public static final int ERROR_CUTOFF_EXISTS_BUT_CANCELED = 33;
  public static final int ERROR_CUTOFF_ALREADY_CANCELED = 34;
  public static final int ERROR_INVALID_EXTENSION_TIME = 35;
  public static final int ERROR_CANNOT_CHANGE_INSTRUCTION_TYPE = 36;
  public static final int ERROR_INSTRUCTION_PMT_MODEL_NOT_EXIST = 37;
  public static final int ERROR_PROCESSING_TIME_NOT_EXIST = 38;
  public static final int ERROR_IN_USE_BY_CASHCONCOMPANY = 39;
  public static final int ERROR_INVALID_DAY_FOR_FREQ = 40;
  public static final int ERROR_CC_BATCH_HIST_IS_NULL = 41;
  public static final int ERROR_CC_BATCH_HIST_ID_NOT_NULL = 42;
  public static final int ERROR_PROCESS_ID_NULL = 43;
  public static final int ERROR_CUTOFF_STATUS_NULL = 44;
  public static final int ERROR_INVALID_CUTOFF_STATUS = 45;
  public static final int ERROR_INVALID_BUSINESS_WINDOW = 46;
  public static final int ERROR_INVALID_BANK_WINDOW = 47;
  public static final int ERROR_INVALID_WINDOW_STARTTIME = 48;
  public static final int ERROR_INVALID_WINDOW_CLOSETIME = 49;
  public static final int ERROR_OVERLAPPING_WINDOW = 50;
  public static final int CAN_NOT_GET_SCHEDULE_ACTIVITIES = 51;
  public static final int ERROR_DUPLICATE_PROCESSING_WINDOW = 52;
  public static final int CAN_NOT_GET_BANKING_DAYS = 53;
  public static final int CAN_NOT_GET_PAYEE_GROUPS = 54;
  public static final int CAN_NOT_GET_GLOBAL_PAYEES = 55;
  public static final int CAN_NOT_GET_GLOBAL_PAYEE_BY_ID = 56;
  public static final int CAN_NOT_ADD_GLOBAL_PAYEE = 57;
  public static final int ERROR_DUPLICATE_GLOBAL_PAYEE = 58;
  public static final int CAN_NOT_MODIFY_GLOBAL_PAYEE_BY_ID = 59;
  public static final int CAN_NOT_DELETE_GLOBAL_PAYEE = 60;
  public static final int ERROR_GLOBAL_PAYEE_PENDING_LINK = 61;
  public static final int ERROR_GLOBAL_PAYEE_PENDING_PAYMENT = 62;
  private int a;
  
  public PAException(int paramInt)
  {
    this(paramInt, null);
  }
  
  public PAException(int paramInt, String paramString)
  {
    super(paramString);
    this.a = paramInt;
  }
  
  public String getMessage()
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if (this.a != 0) {
      localStringBuffer.append("Error Code: ").append(this.a);
    }
    String str = super.getMessage();
    if (str != null) {
      localStringBuffer.append(' ').append(str);
    }
    return localStringBuffer.toString();
  }
  
  public int getErrorCode()
  {
    return this.a;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.PAException
 * JD-Core Version:    0.7.0.1
 */