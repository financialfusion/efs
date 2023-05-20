package com.ffusion.ffs.bpw.interfaces;

public class ScheduleStatus
  extends BPWInfoBase
{
  public static final int STATUS_SUCCESS = 0;
  public static final int STATUS_FAILED = 1;
  public static final int CODE_NONE = 0;
  public static final int CODE_NO_SUCH_SCHEDULE = 1;
  public static final int CODE_UNABLE_TO_GRAB_TOKEN = 2;
  public static final int CODE_EXCEPTION_THROWN = 3;
  public static final int CODE_SCHEDULER_NOT_RUNNING = 4;
  public static final int CODE_OTHER = 1000;
  public int runStatus;
  public int errorCode;
  public String errorMsg;
  
  private ScheduleStatus() {}
  
  public ScheduleStatus(int paramInt)
  {
    this.runStatus = paramInt;
    this.errorCode = 0;
    this.errorMsg = null;
    this.extraFields = null;
  }
  
  public ScheduleStatus(int paramInt1, int paramInt2, String paramString)
  {
    this.runStatus = paramInt1;
    this.errorCode = paramInt2;
    this.errorMsg = paramString;
    this.extraFields = null;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ScheduleStatus
 * JD-Core Version:    0.7.0.1
 */