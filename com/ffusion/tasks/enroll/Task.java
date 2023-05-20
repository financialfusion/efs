package com.ffusion.tasks.enroll;

public abstract interface Task
  extends com.ffusion.tasks.Task
{
  public static final String ENROLL = "com.ffusion.services.Enroll";
  public static final int ERROR_NO_ENROLLMENT_SERVICE = 7000;
  public static final int ERROR_NO_PRIMARY_USER_FOUND = 7032;
  public static final int ERROR_FORMAT = 7020;
  public static final int ERROR_CLASSNAME_NOT_SPECIFIED = 7030;
  public static final int ERROR_UNABLE_TO_CREATE_SERVICE = 7031;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.enroll.Task
 * JD-Core Version:    0.7.0.1
 */