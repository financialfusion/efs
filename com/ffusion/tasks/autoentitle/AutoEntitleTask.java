package com.ffusion.tasks.autoentitle;

import com.ffusion.tasks.Task;

public abstract interface AutoEntitleTask
  extends Task
{
  public static final int ERROR_SETTINGS_RETRIEVAL = 19500;
  public static final int ERROR_SETTINGS_DELETE = 19501;
  public static final int ERROR_SETTINGS_MODIFY = 19502;
  public static final int ERROR_NO_ENTITLEMENT_GROUP = 19503;
  public static final int ERROR_NO_ENTITLEMENT_GROUP_MEMBER = 19504;
  public static final int ERROR_NO_AFFILIATE_BANK = 19505;
  public static final int ERROR_NO_SECURE_USER = 19506;
  public static final int ERROR_SETTINGS_SET = 19507;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.autoentitle.AutoEntitleTask
 * JD-Core Version:    0.7.0.1
 */