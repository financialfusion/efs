package com.ffusion.services;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.CutOffActivities;
import com.ffusion.beans.affiliatebank.CutOffTime;
import com.ffusion.beans.affiliatebank.CutOffTimes;
import com.ffusion.beans.affiliatebank.FulfillmentSystems;
import com.ffusion.beans.affiliatebank.ScheduleActivities;
import com.ffusion.beans.affiliatebank.ScheduleCategory;
import com.ffusion.beans.affiliatebank.ScheduleType;
import com.ffusion.beans.paymentsadmin.ProcessingWindow;
import com.ffusion.beans.paymentsadmin.ProcessingWindows;
import com.ffusion.util.beans.PagingContext;
import java.util.HashMap;

public abstract interface PaymentsAdminService
{
  public abstract int initialize(String paramString);
  
  public abstract ProcessingWindow addProcessingWindow(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws PAException;
  
  public abstract ProcessingWindow modifyProcessingWindow(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws PAException;
  
  public abstract void deleteProcessingWindow(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws PAException;
  
  public abstract ProcessingWindows getProcessingWindows(SecureUser paramSecureUser, ProcessingWindow paramProcessingWindow, HashMap paramHashMap)
    throws PAException;
  
  public abstract FulfillmentSystems getFulfillmentSystems(SecureUser paramSecureUser, HashMap paramHashMap)
    throws PAException;
  
  public abstract ScheduleCategory getScheduleCategory(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws PAException;
  
  public abstract ScheduleType addScheduleType(SecureUser paramSecureUser, ScheduleType paramScheduleType, HashMap paramHashMap)
    throws PAException;
  
  public abstract ScheduleType modifyScheduleType(SecureUser paramSecureUser, ScheduleType paramScheduleType, HashMap paramHashMap)
    throws PAException;
  
  public abstract void deleteScheduleType(SecureUser paramSecureUser, ScheduleType paramScheduleType, HashMap paramHashMap)
    throws PAException;
  
  public abstract ScheduleType getScheduleType(SecureUser paramSecureUser, String paramString1, String paramString2, HashMap paramHashMap)
    throws PAException;
  
  public abstract CutOffTime addCutOffTime(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws PAException;
  
  public abstract CutOffTime modifyCutOffTime(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws PAException;
  
  public abstract void deleteCutOffTime(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws PAException;
  
  public abstract CutOffTimes getCutOffTimes(SecureUser paramSecureUser, CutOffTime paramCutOffTime, HashMap paramHashMap)
    throws PAException;
  
  public abstract void resubmitSchedule(String paramString1, String paramString2, String paramString3, String paramString4)
    throws PAException;
  
  public abstract void runSchedule(String paramString1, String paramString2)
    throws PAException;
  
  public abstract CutOffActivities getCutOffActivities(SecureUser paramSecureUser, String paramString, PagingContext paramPagingContext, HashMap paramHashMap)
    throws PAException;
  
  public abstract void rerunCutOffProcess(SecureUser paramSecureUser, CutOffTime paramCutOffTime, String paramString, HashMap paramHashMap)
    throws PAException;
  
  public abstract String getGeneratedFileName(SecureUser paramSecureUser, CutOffTime paramCutOffTime, String paramString, HashMap paramHashMap)
    throws PAException;
  
  public abstract void cleanup(HashMap paramHashMap)
    throws PAException;
  
  public abstract ScheduleActivities getScheduleActivities(SecureUser paramSecureUser, String paramString1, String paramString2, PagingContext paramPagingContext, HashMap paramHashMap)
    throws PAException;
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.services.PaymentsAdminService
 * JD-Core Version:    0.7.0.1
 */