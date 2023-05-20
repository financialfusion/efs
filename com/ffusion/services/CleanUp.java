package com.ffusion.services;

import java.io.Serializable;

public abstract interface CleanUp
  extends Serializable
{
  public abstract int initialize(String paramString);
  
  public abstract int purgeMessages(int paramInt);
  
  public abstract int purgeApplications(int paramInt, String paramString);
  
  public abstract int purgeRegister(int paramInt);
  
  public abstract int purgeHistories(int paramInt);
  
  public abstract int purgeAppHistories(int paramInt);
  
  public abstract int purgeBusinesses(int paramInt);
  
  public abstract int purgeBankEmployees(int paramInt);
  
  public abstract int purgeCustomers(int paramInt);
  
  public abstract int purgeUserLevelAudit(int paramInt);
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.CleanUp
 * JD-Core Version:    0.7.0.1
 */