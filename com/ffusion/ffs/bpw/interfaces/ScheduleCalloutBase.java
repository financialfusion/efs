package com.ffusion.ffs.bpw.interfaces;

public abstract class ScheduleCalloutBase
{
  public void eventSchedulePreProcess(ScheduleCalloutInfo paramScheduleCalloutInfo)
    throws Exception
  {}
  
  public void eventScheduleProcessingBegin(ScheduleCalloutInfo paramScheduleCalloutInfo)
    throws Exception
  {}
  
  public void eventScheduleProcessingEnd(ScheduleCalloutInfo paramScheduleCalloutInfo)
    throws Exception
  {}
  
  public void eventScheduleProcessingError(ScheduleCalloutInfo paramScheduleCalloutInfo) {}
  
  public void eventSchedulePostProcess(ScheduleCalloutInfo paramScheduleCalloutInfo) {}
  
  public void eventScheduleRecoveryBegin(ScheduleCalloutInfo paramScheduleCalloutInfo)
    throws Exception
  {}
  
  public void eventScheduleRecoveryEnd(ScheduleCalloutInfo paramScheduleCalloutInfo)
    throws Exception
  {}
  
  public void eventScheduleRecoveryError(ScheduleCalloutInfo paramScheduleCalloutInfo) {}
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ScheduleCalloutBase
 * JD-Core Version:    0.7.0.1
 */