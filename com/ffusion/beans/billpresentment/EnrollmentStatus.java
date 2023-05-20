package com.ffusion.beans.billpresentment;

public abstract interface EnrollmentStatus
{
  public static final int EBPP_ENROLLMENT_STATUS_COUNT = 5;
  public static final int EBPP_ENROLLMENT_NONE = 0;
  public static final int EBPP_ENROLLMENT_NEW = 1;
  public static final int EBPP_ENROLLMENT_PENDING = 2;
  public static final int EBPP_ENROLLMENT_ACTIVE = 3;
  public static final int EBPP_ENROLLMENT_REJECTED = 4;
  public static final String BEANS_EBPP_RESOURCES = "com.ffusion.beans.billpresentment.resources";
  public static final String EBPP_ENROLLMENT_STATUS = "EnrollmentStatus";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.billpresentment.EnrollmentStatus
 * JD-Core Version:    0.7.0.1
 */