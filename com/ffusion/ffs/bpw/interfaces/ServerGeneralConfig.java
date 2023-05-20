package com.ffusion.ffs.bpw.interfaces;

import java.io.Serializable;
import java.util.Properties;

public class ServerGeneralConfig
  implements Serializable, Cloneable
{
  public int minuteNotToRecover;
  public int fundsAllocRetries;
  public int batchSize;
  public boolean enforceEnrollment;
  public boolean enforcePayment;
  public int startPayeeListID;
  public int retries;
  public boolean noImmediateTransfer;
  public boolean supportImmediatePmt;
  public int timeout;
  public boolean useExtdPayeeID;
  public String purgeWakeupTime;
  public int closedInstructionAge;
  public int eventHistoryAge;
  public int activityLogAge;
  public boolean clusterOn = false;
  public long clusterDelay = 60000L;
  public Properties otherProperties;
  
  public ServerGeneralConfig()
  {
    reset();
  }
  
  public void reset()
  {
    this.minuteNotToRecover = 240;
    this.fundsAllocRetries = 3;
    this.batchSize = 1000;
    this.enforceEnrollment = false;
    this.enforcePayment = false;
    this.startPayeeListID = 0;
    this.retries = 3;
    this.noImmediateTransfer = false;
    this.supportImmediatePmt = false;
    this.timeout = 60000;
    this.useExtdPayeeID = false;
    this.purgeWakeupTime = "12:00 AM";
    this.closedInstructionAge = 30;
    this.eventHistoryAge = 30;
    this.activityLogAge = 30;
    this.clusterOn = false;
    this.clusterDelay = 60000L;
    if (this.otherProperties != null) {
      this.otherProperties.clear();
    } else {
      this.otherProperties = new Properties();
    }
  }
  
  public PropertyConfig getNewPropertyConfig()
  {
    PropertyConfig localPropertyConfig = new PropertyConfig();
    localPropertyConfig.reset();
    localPropertyConfig.Retries = this.retries;
    localPropertyConfig.Timeout = this.timeout;
    localPropertyConfig.MinuteNotToRecover = this.minuteNotToRecover;
    localPropertyConfig.FundsAllocRetries = this.fundsAllocRetries;
    localPropertyConfig.BatchSize = this.batchSize;
    localPropertyConfig.EnforcePayment = this.enforcePayment;
    localPropertyConfig.EnforceEnrollment = this.enforceEnrollment;
    localPropertyConfig.StartPayeeListID = this.startPayeeListID;
    localPropertyConfig.NoImmediateTransfer = this.noImmediateTransfer;
    localPropertyConfig.SupportImmediatePmt = this.supportImmediatePmt;
    localPropertyConfig.UseExtdPayeeID = this.useExtdPayeeID;
    localPropertyConfig.PurgeWakeupTime = this.purgeWakeupTime;
    localPropertyConfig.DayToPurgeClosedInstruction = this.closedInstructionAge;
    localPropertyConfig.DayToPurgeEventHistory = this.eventHistoryAge;
    localPropertyConfig.DayToPurgeActivityLog = this.activityLogAge;
    localPropertyConfig.SupportCluster = this.clusterOn;
    localPropertyConfig.SchWaitTime = this.clusterDelay;
    localPropertyConfig.clusterConfig = this;
    return localPropertyConfig;
  }
  
  public void cascade(PropertyConfig paramPropertyConfig)
  {
    paramPropertyConfig.Retries = this.retries;
    paramPropertyConfig.Timeout = this.timeout;
    paramPropertyConfig.MinuteNotToRecover = this.minuteNotToRecover;
    paramPropertyConfig.FundsAllocRetries = this.fundsAllocRetries;
    paramPropertyConfig.BatchSize = this.batchSize;
    paramPropertyConfig.EnforcePayment = this.enforcePayment;
    paramPropertyConfig.EnforceEnrollment = this.enforceEnrollment;
    paramPropertyConfig.StartPayeeListID = this.startPayeeListID;
    paramPropertyConfig.NoImmediateTransfer = this.noImmediateTransfer;
    paramPropertyConfig.SupportImmediatePmt = this.supportImmediatePmt;
    paramPropertyConfig.UseExtdPayeeID = this.useExtdPayeeID;
    paramPropertyConfig.PurgeWakeupTime = this.purgeWakeupTime;
    paramPropertyConfig.DayToPurgeClosedInstruction = this.closedInstructionAge;
    paramPropertyConfig.DayToPurgeEventHistory = this.eventHistoryAge;
    paramPropertyConfig.DayToPurgeActivityLog = this.activityLogAge;
    paramPropertyConfig.SupportCluster = this.clusterOn;
    paramPropertyConfig.SchWaitTime = this.clusterDelay;
    paramPropertyConfig.clusterConfig = this;
  }
  
  public Object clone()
  {
    ServerGeneralConfig localServerGeneralConfig = new ServerGeneralConfig();
    localServerGeneralConfig.retries = this.retries;
    localServerGeneralConfig.timeout = this.timeout;
    localServerGeneralConfig.minuteNotToRecover = this.minuteNotToRecover;
    localServerGeneralConfig.fundsAllocRetries = this.fundsAllocRetries;
    localServerGeneralConfig.batchSize = this.batchSize;
    localServerGeneralConfig.enforcePayment = this.enforcePayment;
    localServerGeneralConfig.enforceEnrollment = this.enforceEnrollment;
    localServerGeneralConfig.startPayeeListID = this.startPayeeListID;
    localServerGeneralConfig.noImmediateTransfer = this.noImmediateTransfer;
    localServerGeneralConfig.supportImmediatePmt = this.supportImmediatePmt;
    localServerGeneralConfig.useExtdPayeeID = this.useExtdPayeeID;
    localServerGeneralConfig.purgeWakeupTime = this.purgeWakeupTime;
    localServerGeneralConfig.closedInstructionAge = this.closedInstructionAge;
    localServerGeneralConfig.eventHistoryAge = this.eventHistoryAge;
    localServerGeneralConfig.activityLogAge = this.activityLogAge;
    localServerGeneralConfig.otherProperties = (this.otherProperties == null ? new Properties() : (Properties)this.otherProperties.clone());
    localServerGeneralConfig.clusterOn = this.clusterOn;
    localServerGeneralConfig.clusterDelay = this.clusterDelay;
    return localServerGeneralConfig;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ServerGeneralConfig
 * JD-Core Version:    0.7.0.1
 */