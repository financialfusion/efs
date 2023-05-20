package com.ffusion.ffs.bpw.adminEJB;

import com.ffusion.ffs.bpw.interfaces.BPWStatistics;
import com.ffusion.ffs.bpw.interfaces.CutOffActivityInfoList;
import com.ffusion.ffs.bpw.interfaces.CutOffInfo;
import com.ffusion.ffs.bpw.interfaces.CutOffInfoList;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowList;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.ScheduleActivityList;
import com.ffusion.ffs.bpw.interfaces.ScheduleCategoryInfo;
import com.ffusion.ffs.bpw.interfaces.ScheduleHist;
import com.ffusion.ffs.bpw.interfaces.SchedulerInfo;
import com.ffusion.ffs.bpw.interfaces.SmartCalendarFile;
import com.ffusion.ffs.db.FFSDBProperties;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSProperties;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.ejb.EJBException;
import javax.ejb.EJBObject;

public abstract interface IBPWAdmin
  extends EJBObject
{
  /**
   * @deprecated
   */
  public abstract void start(FFSProperties paramFFSProperties, PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
    throws RemoteException, FFSException;
  
  public abstract void start(PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
    throws RemoteException, FFSException;
  
  /**
   * @deprecated
   */
  public abstract void refresh(FFSProperties paramFFSProperties, PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
    throws RemoteException, FFSException;
  
  public abstract void refresh(PropertyConfig paramPropertyConfig, InstructionType[] paramArrayOfInstructionType)
    throws RemoteException, FFSException;
  
  public abstract void stop()
    throws RemoteException, FFSException;
  
  public abstract void refreshSmartCalendar()
    throws RemoteException, FFSException;
  
  public abstract BPWStatistics getStatistics()
    throws RemoteException, FFSException;
  
  public abstract long getFreeMem()
    throws RemoteException, FFSException;
  
  public abstract long getTotalMem()
    throws RemoteException, FFSException;
  
  public abstract double getHeapUsage()
    throws RemoteException, FFSException;
  
  public abstract boolean ping()
    throws RemoteException, FFSException;
  
  public abstract void resubmitEvent(String paramString1, String paramString2, String paramString3, String paramString4)
    throws RemoteException, FFSException;
  
  public abstract void resubmitEvent(String paramString1, String paramString2, String paramString3)
    throws RemoteException, FFSException;
  
  public abstract void resubmitEvent(String paramString1, String paramString2)
    throws RemoteException, FFSException;
  
  public abstract String startScheduler()
    throws RemoteException, FFSException;
  
  public abstract String stopScheduler()
    throws RemoteException, FFSException;
  
  public abstract String refreshScheduler()
    throws RemoteException, FFSException;
  
  public abstract void registerPropertyConfig(PropertyConfig paramPropertyConfig)
    throws RemoteException, FFSException;
  
  public abstract void startEngine(String paramString)
    throws RemoteException, FFSException;
  
  public abstract void stopEngine(String paramString)
    throws RemoteException, FFSException;
  
  public abstract void stopLimitCheckApprovalProcessor(String paramString)
    throws RemoteException, FFSException;
  
  public abstract void startLimitCheckApprovalProcessor(String paramString)
    throws RemoteException, FFSException;
  
  public abstract void runBatchProcess(String paramString1, String paramString2)
    throws RemoteException, FFSException;
  
  public abstract void updateScheduleRunTimeConfig(InstructionType paramInstructionType)
    throws RemoteException, FFSException;
  
  public abstract void updateScheduleProcessingConfig(InstructionType paramInstructionType)
    throws RemoteException, FFSException;
  
  public abstract void updateScheduleConfig(InstructionType paramInstructionType)
    throws RemoteException, FFSException;
  
  public abstract InstructionType getScheduleConfig(String paramString1, String paramString2)
    throws RemoteException, FFSException;
  
  public abstract InstructionType[] getScheduleConfig()
    throws RemoteException, FFSException;
  
  public abstract SchedulerInfo getSchedulerInfo(String paramString1, String paramString2)
    throws RemoteException, FFSException;
  
  public abstract SchedulerInfo[] getSchedulerInfo()
    throws RemoteException, FFSException;
  
  public abstract ScheduleHist[] getScheduleHist(String paramString1, String paramString2, ScheduleHist paramScheduleHist)
    throws RemoteException, FFSException;
  
  public abstract PayeeInfo[] searchGlobalPayees(String paramString)
    throws RemoteException, FFSException;
  
  public abstract PayeeInfo[] searchGlobalPayees(PayeeInfo paramPayeeInfo, int paramInt)
    throws RemoteException, FFSException;
  
  public abstract PayeeInfo getGlobalPayee(String paramString)
    throws RemoteException, FFSException;
  
  public abstract PayeeInfo updateGlobalPayee(PayeeInfo paramPayeeInfo)
    throws RemoteException, FFSException;
  
  public abstract String addPayee(FFSDBProperties paramFFSDBProperties, PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws RemoteException, FFSException;
  
  public abstract void updatePayee(FFSDBProperties paramFFSDBProperties, PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws RemoteException, FFSException;
  
  public abstract void deletePayee(FFSDBProperties paramFFSDBProperties, String paramString)
    throws RemoteException, FFSException;
  
  public abstract PayeeRouteInfo getPayeeRoute(FFSDBProperties paramFFSDBProperties, String paramString, int paramInt)
    throws RemoteException, FFSException;
  
  public abstract PayeeInfo findPayeeByID(FFSDBProperties paramFFSDBProperties, String paramString)
    throws RemoteException, FFSException;
  
  public abstract FulfillmentInfo[] getAllFulfillmentInfo(FFSDBProperties paramFFSDBProperties)
    throws FFSException, RemoteException;
  
  public abstract FulfillmentInfo[] getFulfillmentSystems()
    throws FFSException, RemoteException;
  
  public abstract String[] getGlobalPayeeGroups()
    throws FFSException, RemoteException;
  
  public abstract void addFulfillmentInfo(FFSDBProperties paramFFSDBProperties, FulfillmentInfo paramFulfillmentInfo)
    throws FFSException, RemoteException;
  
  public abstract void updateFulfillmentInfo(FFSDBProperties paramFFSDBProperties, FulfillmentInfo paramFulfillmentInfo)
    throws FFSException, RemoteException;
  
  public abstract void deleteFulfillmentInfo(FFSDBProperties paramFFSDBProperties, int paramInt)
    throws FFSException, RemoteException;
  
  public abstract void setDebugLevel(int paramInt)
    throws FFSException, RemoteException;
  
  public abstract ProcessingWindowInfo addProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException, RemoteException;
  
  public abstract ProcessingWindowInfo modProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException, RemoteException;
  
  public abstract ProcessingWindowInfo delProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException, RemoteException;
  
  public abstract ProcessingWindowList getProcessingWindows(ProcessingWindowList paramProcessingWindowList)
    throws FFSException, RemoteException;
  
  public abstract InstructionType[] getScheduleConfigByCategory(InstructionType paramInstructionType)
    throws FFSException, RemoteException;
  
  public abstract void addScheduleConfig(InstructionType paramInstructionType)
    throws FFSException, RemoteException;
  
  public abstract void deleteScheduleConfig(InstructionType paramInstructionType)
    throws FFSException, RemoteException;
  
  public abstract CutOffInfo deleteCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException, RemoteException;
  
  public abstract CutOffInfo addCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException, RemoteException;
  
  public abstract CutOffInfo modCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException, RemoteException;
  
  public abstract CutOffInfo getCutOff(CutOffInfo paramCutOffInfo)
    throws FFSException, RemoteException;
  
  public abstract CutOffInfoList getCutOffList(CutOffInfoList paramCutOffInfoList)
    throws FFSException, RemoteException;
  
  public abstract ScheduleCategoryInfo getScheduleCategoryInfo(String paramString1, String paramString2)
    throws FFSException, RemoteException;
  
  public abstract ScheduleCategoryInfo modScheduleCategoryInfo(ScheduleCategoryInfo paramScheduleCategoryInfo)
    throws FFSException, RemoteException;
  
  public abstract void cleanup(String paramString1, String paramString2, int paramInt, HashMap paramHashMap)
    throws EJBException, RemoteException;
  
  public abstract void cleanup(String paramString, ArrayList paramArrayList1, ArrayList paramArrayList2, HashMap paramHashMap)
    throws EJBException, RemoteException;
  
  public abstract void cleanup(ArrayList paramArrayList1, ArrayList paramArrayList2, ArrayList paramArrayList3, HashMap paramHashMap)
    throws EJBException, RemoteException;
  
  public abstract CutOffActivityInfoList getCutOffActivityList(CutOffActivityInfoList paramCutOffActivityInfoList)
    throws FFSException, RemoteException;
  
  public abstract ScheduleActivityList getScheduleActivityList(ScheduleActivityList paramScheduleActivityList)
    throws FFSException, RemoteException;
  
  public abstract void rerunCutOff(String paramString1, String paramString2, String paramString3, String paramString4)
    throws FFSException, RemoteException;
  
  public abstract String getGeneratedFileName(String paramString1, String paramString2, String paramString3)
    throws FFSException, RemoteException;
  
  public abstract SmartCalendarFile importCalendar(SmartCalendarFile paramSmartCalendarFile)
    throws FFSException, RemoteException;
  
  public abstract SmartCalendarFile exportCalendar(SmartCalendarFile paramSmartCalendarFile)
    throws FFSException, RemoteException;
  
  public abstract PayeeInfo addGlobalPayee(PayeeInfo paramPayeeInfo)
    throws FFSException, RemoteException;
  
  public abstract PayeeInfo deleteGlobalPayee(PayeeInfo paramPayeeInfo)
    throws FFSException, RemoteException;
}


/* Location:           D:\drops\jd\jars\OFX200BPWServices.jar
 * Qualified Name:     com.ffusion.ffs.bpw.adminEJB.IBPWAdmin
 * JD-Core Version:    0.7.0.1
 */