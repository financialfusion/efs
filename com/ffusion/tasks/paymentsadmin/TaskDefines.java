package com.ffusion.tasks.paymentsadmin;

import com.ffusion.tasks.Task;

public abstract interface TaskDefines
  extends Task
{
  public static final String PROCESSING_WINDOW = "ProcessingWindow";
  public static final String PROCESSING_WINDOWS = "ProcessingWindows";
  public static final String OLD_PROCESSING_WINDOW = "OldProcessingWindow";
  public static final String BANK_PROCESSING_WINDOW = "BankProcessingWindow";
  public static final int ERROR_NO_PROC_WINDOW = 36000;
  public static final int ERROR_PROC_WINDOW_INVALID_TIME = 36001;
  public static final int ERROR_PROC_WINDOW_INVALID_TYPE = 36002;
  public static final int ERROR_PROC_WINDOW_INVALID_ID = 36003;
  public static final int ERROR_NO_SECURE_USER = 36004;
  public static final int ERROR_PROC_WINDOW_INVALID_SUB_TYPE = 36005;
  public static final int ERROR_PROC_WINDOW_START_TIME_TOO_LATE = 36006;
  public static final int ERROR_PROC_WINDOW_DUPLICATE = 36007;
  public static final int ERROR_PROC_WINDOW_NOT_WITHIN_BANK_WINDOW = 36008;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.paymentsadmin.TaskDefines
 * JD-Core Version:    0.7.0.1
 */