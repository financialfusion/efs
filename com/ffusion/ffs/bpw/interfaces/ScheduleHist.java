package com.ffusion.ffs.bpw.interfaces;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.Properties;

public class ScheduleHist
  implements Serializable
{
  public String SchHistID;
  public String LogDate;
  public String ScheduleName;
  public String FIID;
  public String InstructionType;
  public String ServerName;
  public String EventType;
  public String EventTrigger;
  public String EventDescription;
  public String CutOffId;
  public int CutOffDay;
  public String CutOffProcessTime;
  public String CutOffExtension;
  public String ProcessId;
  public String FileName;
  
  public ScheduleHist() {}
  
  public ScheduleHist(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7)
  {
    this.LogDate = paramString1;
    this.ScheduleName = paramString2;
    this.FIID = paramString3;
    this.InstructionType = paramString4;
    this.ServerName = paramString5;
    this.EventType = paramString6;
    this.EventTrigger = this.EventTrigger;
    this.EventDescription = paramString7;
  }
  
  public ScheduleHist(Properties paramProperties)
  {
    this.SchHistID = paramProperties.getProperty("SchHistID");
    this.LogDate = paramProperties.getProperty("LogDate");
    this.ScheduleName = paramProperties.getProperty("ScheduleName");
    this.FIID = paramProperties.getProperty("FIId");
    this.InstructionType = paramProperties.getProperty("InstructionType");
    this.ServerName = paramProperties.getProperty("ServerName");
    this.EventType = paramProperties.getProperty("EventType");
    this.EventTrigger = paramProperties.getProperty("EventTrigger");
    this.EventDescription = paramProperties.getProperty("EventDescription");
  }
  
  public void printout(PrintWriter paramPrintWriter)
  {
    paramPrintWriter.println("LogDate=" + this.LogDate);
    paramPrintWriter.println("ScheduleName=" + this.ScheduleName);
    paramPrintWriter.println("InstructionType=" + this.InstructionType);
    paramPrintWriter.println("FIID=" + this.FIID);
    paramPrintWriter.println("ServerName=" + this.ServerName);
    paramPrintWriter.println("EventType=" + this.EventType);
    paramPrintWriter.println("EventTrigger=" + this.EventTrigger);
    paramPrintWriter.println("EventDescription=" + this.EventDescription);
    paramPrintWriter.println("");
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ScheduleHist
 * JD-Core Version:    0.7.0.1
 */