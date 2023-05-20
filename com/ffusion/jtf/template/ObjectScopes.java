package com.ffusion.jtf.template;

abstract interface ObjectScopes
{
  public static final int REQUEST = 0;
  public static final int SESSION = 1;
  public static final int APPLICATION = 2;
  public static final int PROCESS = 3;
  public static final String REQUEST_STR = "request";
  public static final String SESSION_STR = "session";
  public static final String PROCESS_STR = "process";
  public static final String APPLICATION_STR = "application";
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.jtf.template.ObjectScopes
 * JD-Core Version:    0.7.0.1
 */