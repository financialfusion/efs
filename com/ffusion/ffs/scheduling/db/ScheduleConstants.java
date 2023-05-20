package com.ffusion.ffs.scheduling.db;

import com.ffusion.ffs.util.FFSConst;

public abstract interface ScheduleConstants
  extends FFSConst
{
  public static final int BIG_STAMP = 2147483647;
  public static final String SCH_STATUS_ACTIVE = "Active";
  public static final String SCH_STATUS_PROCESSING = "Processing";
  public static final String SCH_STATUS_PROCESSED = "Processed";
  public static final String SCH_STATUS_INACTIVE = "InActive";
  public static final String SCH_STATUS_CLOSED = "Close";
  public static final String SCH_STATUS_MODIFYING = "Modifying";
  public static final int SCH_STATUSOPTION_NONE = 0;
  public static final int SCH_STATUSOPTION_CLOSE_SCHEDULED = 1;
  public static final String EVT_STATUS_PREPARED = "Prepared";
  public static final String EVT_STATUS_SUBMIT = "Submitted";
  public static final int SCH_FLAG_RESUBMIT = -1;
  public static final int SCH_FLAG_NORMAL = 0;
  public static final int SCH_FLAG_CUST_NEW = 1;
  public static final int SCH_FLAG_CUST_MOD = 2;
  public static final int SCH_FLAG_CUST_CAN = 3;
  public static final int SCH_FLAG_CUSTPAYEE_NEW = 4;
  public static final int SCH_FLAG_CUSTPAYEE_MODACCT = 5;
  public static final int SCH_FLAG_CUSTPAYEE_MODPAYEE = 6;
  public static final int SCH_FLAG_CUSTPAYEE_MODBOTH = 7;
  public static final int SCH_FLAG_CUSTPAYEE_CAN = 8;
  public static final int SCH_FLAG_PAYEE_NEW = 9;
  public static final int SCH_FLAG_FAILEDON = 10;
  public static final int SCH_FLAG_NOFUNDSON = 11;
  public static final int SCH_FLAG_FUNDSFAILEDON = 12;
  public static final int SCH_FLAG_FUNDSREVERTED = 13;
  public static final int SCH_FLAG_FUNDSREVERTFAILED = 14;
  public static final int SCH_FLAG_LIMIT_REVERT_FAILED = 15;
  public static final int SCH_FLAG_LIMIT_CHECK_FAILED = 16;
  public static final int EVT_SEQUENCE_FIRST = 0;
  public static final int EVT_SEQUENCE_NORMAL = 1;
  public static final int EVT_SEQUENCE_LAST = 2;
  public static final int EVT_SEQUENCE_BATCH_START = 3;
  public static final int EVT_SEQUENCE_BATCH_END = 4;
  public static final String INT_STATUS_SCH_START = "SchedulerStarted";
  public static final String INT_STATUS_SCH_END = "SchedulerCompleted";
  public static final String INT_STATUS_DIS_START = "DispatcherStarted";
  public static final String INT_STATUS_DIS_END = "DispatcherClose";
  public static final String INT_STATUS_CLEAN_START = "DispatcherCleanup";
  public static final String INT_STATUS_CLEAN_END = "DispatcherCompleted";
  public static final String SCH_NAME_HOSTNAME = "HostName";
  public static final String SCH_NAME_IPADDRESS = "IpAddress";
  public static final String SCH_NAME_EJB_PROTOCOL = "EJBProtocol";
  public static final String SCH_NAME_EJB_HOST = "EJBHost";
  public static final String SCH_NAME_EJB_PORT = "EJBPort";
  public static final String SCH_NAME_EJB_USERNAME = "EJBUserName";
  public static final String SCH_NAME_EJB_PASSWORD = "EJBPassword";
  public static final String SCH_NAME_EJB_JNDINAME = "EJBJNDIName";
  public static final String SCH_NAME_EJB_NAMECONTEXT = "EJBNameContext";
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.db.ScheduleConstants
 * JD-Core Version:    0.7.0.1
 */