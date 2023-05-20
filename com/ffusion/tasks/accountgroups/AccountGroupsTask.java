package com.ffusion.tasks.accountgroups;

import com.ffusion.tasks.Task;

public abstract interface AccountGroupsTask
  extends Task
{
  public static final String ACCOUNTGROUPS = "AccountGroups";
  public static final String ACCOUNTGROUP = "AccountGroup";
  public static final String ACCOUNTGROUPSIDS = "AccountGroupsIds";
  public static final String ACCOUNTS = "Accounts";
  public static final int TASK_ERROR_MISSING_BUSINESS_DIRECTORY_ID = 70000;
  public static final int TASK_ERROR_MISSING_ACCOUNT_GROUP_PROFILE_ID = 70001;
  public static final int TASK_ERROR_INVALID_GROUP_NAME = 70002;
  public static final int TASK_ERROR_INVALID_GROUP_ID = 70003;
  public static final int TASK_ERROR_MISSING_ACCOUNTS_COLLECTION = 70004;
  public static final int TASK_ERROR_INVALID_ACCOUNTS_SIZE = 70005;
  public static final int TASK_ERROR_MISSING_ACCOUNT_GROUP = 70006;
  public static final int TASK_ERROR_MISSING_ACCOUNT_GROUPS_COLLECTION = 70007;
  public static final int TASK_ERROR_INVALID_ACCOUNT_GROUP_PROFILE_ID = 70008;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.accountgroups.AccountGroupsTask
 * JD-Core Version:    0.7.0.1
 */