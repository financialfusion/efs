package com.ffusion.ffs.bpw.interfaces;

import java.io.PrintWriter;

public class SchedulerInfo
  extends BPWInfoBase
{
  public String SchedulerName;
  public String FIID;
  public String InstructionType;
  public String HandlerClassName;
  public String SchedulerStartTime;
  public int SchedulerInterval;
  public String LastRunTime;
  public String NextRunTime;
  public String FinalRuntimeForNextProcessDate;
  public String SchedulerStatus;
  public int FileBasedRecovery;
  public int RecoveryTimeLimit;
  public int ActiveFlag;
  public String ThreadStatus;
  
  public SchedulerInfo() {}
  
  public SchedulerInfo(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, int paramInt1, String paramString6, String paramString7, String paramString8, String paramString9, int paramInt2, int paramInt3, int paramInt4, String paramString10)
  {
    this.SchedulerName = paramString1;
    this.FIID = paramString2;
    this.InstructionType = paramString3;
    this.HandlerClassName = paramString4;
    this.SchedulerStartTime = paramString5;
    this.SchedulerInterval = paramInt1;
    this.LastRunTime = paramString6;
    this.NextRunTime = paramString7;
    this.FinalRuntimeForNextProcessDate = paramString8;
    this.SchedulerStatus = paramString9;
    this.FileBasedRecovery = paramInt2;
    this.RecoveryTimeLimit = paramInt3;
    this.ActiveFlag = paramInt4;
    this.ThreadStatus = paramString10;
  }
  
  public void printout(PrintWriter paramPrintWriter)
  {
    paramPrintWriter.println("SchedulerName=" + this.SchedulerName);
    paramPrintWriter.println("InstructionType=" + this.InstructionType);
    paramPrintWriter.println("FIID=" + this.FIID);
    paramPrintWriter.println("HandlerClassName=" + this.HandlerClassName);
    paramPrintWriter.println("SchedulerStartTime=" + this.SchedulerStartTime);
    paramPrintWriter.println("SchedulerInterval=" + this.SchedulerInterval);
    paramPrintWriter.println("LastRunTime=" + this.LastRunTime);
    paramPrintWriter.println("NextRunTime=" + this.NextRunTime);
    paramPrintWriter.println("FinalRuntimeForNextProcessDate=" + this.FinalRuntimeForNextProcessDate);
    paramPrintWriter.println("SchedulerStatus=" + this.SchedulerStatus);
    paramPrintWriter.println("RecoveryTimeLimit=" + this.RecoveryTimeLimit);
    paramPrintWriter.println("FileBasedRecovery=" + this.FileBasedRecovery);
    paramPrintWriter.println("ActiveFlag=" + this.ActiveFlag);
    paramPrintWriter.println("ThreadStatus=" + this.ThreadStatus);
    paramPrintWriter.println("");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.SchedulerInfo
 * JD-Core Version:    0.7.0.1
 */