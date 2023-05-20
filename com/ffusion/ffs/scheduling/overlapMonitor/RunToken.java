package com.ffusion.ffs.scheduling.overlapMonitor;

import com.ffusion.ffs.util.FFSDebug;

public class RunToken
{
  public static final int STATUS_NOT_RUNNING = 1000;
  public static final int STATUS_RUNNING = 1010;
  private String jdField_for;
  private String jdField_do;
  private int jdField_if;
  private int a;
  
  public RunToken(String paramString1, String paramString2)
  {
    this.jdField_for = paramString1;
    this.jdField_do = paramString2;
    this.jdField_if = 1000;
    this.a = 0;
  }
  
  public String getFIID()
  {
    return this.jdField_for;
  }
  
  public String getRunningGroupName()
  {
    return this.jdField_do;
  }
  
  public int getRunStatus()
  {
    return this.jdField_if;
  }
  
  public int getRunningCount()
  {
    return this.a;
  }
  
  public synchronized void grabToken()
  {
    this.jdField_if = 1010;
    this.a += 1;
  }
  
  public synchronized void releaseToken()
  {
    this.a -= 1;
    if (this.a <= 0)
    {
      this.jdField_if = 1000;
      if (this.a < 0)
      {
        String str = "releaseToken call exceeds grabToken call count (" + this.jdField_for + ", " + this.jdField_do + ").";
        FFSDebug.console("*** RunToken.releaseToken:" + str);
        FFSDebug.log("*** RunToken.releaseToken:" + str);
        this.a = 0;
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.scheduling.overlapMonitor.RunToken
 * JD-Core Version:    0.7.0.1
 */