package com.ffusion.tasks.affiliatebank;

import com.ffusion.tasks.Task;

public abstract interface CutOffTaskDefines
  extends Task
{
  public static final String SCHEDULE_CATEGORY = "ScheduleCategory";
  public static final String CUT_OFF_TIME = "CutOffTime";
  public static final String CUT_OFF_TIMES = "CutOffTimes";
  public static final String SCHEDULE_TYPE = "ScheduleType";
  public static final String OLD_CUT_OFF_TIME = "OldCutOffTime";
  public static final String OLD_SCHEDULE_TYPE = "OldScheduleType";
  public static final String FULFILLMENT_SYSTEMS = "FulfillmentSystems";
  public static final String CUT_OFF_ACTIVITY = "CutOffActivity";
  public static final String CUT_OFF_ACTIVITIES = "CutOffActivities";
  public static final String GENERATED_FILE_NAME = "GeneratedFileName";
  public static final String SCHEDULE_ACTIVITIES = "ScheduleActivities";
  public static final int ERROR_NO_AFFILIATE_BANK = 26000;
  public static final int ERROR_INVALID_PMT_GROUP = 26001;
  public static final int ERROR_NO_SCHEDULE_CATEGORY = 26002;
  public static final int ERROR_INVALID_SCHEDULE_NAME = 26003;
  public static final int ERROR_NO_CUT_OFF = 26004;
  public static final int ERROR_NO_OLD_CUT_OFF = 26005;
  public static final int ERROR_CUT_OFF_ALREADY_PROCESSING = 26006;
  public static final int ERROR_CUT_OFF_INVALID_DAY = 26007;
  public static final int ERROR_CUT_OFF_INVALID_TIME = 26008;
  public static final int ERROR_CUT_OFF_INVALID_EXTENSION = 26009;
  public static final int ERROR_CUT_OFF_INVALID_STATUS = 26010;
  public static final int ERROR_INVALID_FIID = 26011;
  public static final int ERROR_NO_SCHEDULE_TYPE = 26012;
  public static final int ERROR_NO_OLD_SCHEDULE_TYPE = 26013;
  public static final int ERROR_INVALID_SCHEDULE_START_TIME = 26014;
  public static final int ERROR_INVALID_SCHEDULE_INTERVAL = 26015;
  public static final int ERROR_INVALID_SCHEDULE_HANDLER_NAME = 26016;
  public static final int ERROR_CUT_OFF_INVALID_GROUP = 26017;
  public static final int ERROR_NO_SECURE_USER = 26018;
  public static final int ERROR_INVALID_LOG_DATE = 26019;
  public static final int ERROR_INVALID_PROCESS_ID = 26020;
  public static final int ERROR_NO_CUT_OFF_ACTIVITIES = 26021;
  public static final int ERROR_NO_CUT_OFF_ACTIVITY = 26022;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.affiliatebank.CutOffTaskDefines
 * JD-Core Version:    0.7.0.1
 */