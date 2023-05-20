package com.ffusion.ffs.bpw.config;

import com.ffusion.ffs.bpw.interfaces.BPWFIInfo;
import com.ffusion.ffs.bpw.interfaces.DBConnectionInfo;
import com.ffusion.ffs.bpw.interfaces.FulfillmentInfo;
import com.ffusion.ffs.bpw.interfaces.InstructionType;
import com.ffusion.ffs.bpw.interfaces.PayeeInfo;
import com.ffusion.ffs.bpw.interfaces.PayeeRouteInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowInfo;
import com.ffusion.ffs.bpw.interfaces.ProcessingWindowList;
import com.ffusion.ffs.bpw.interfaces.PropertyConfig;
import com.ffusion.ffs.bpw.interfaces.ServerGeneralConfig;
import com.ffusion.ffs.db.FFSDBProperties;
import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSProperties;

public abstract interface BPWAdmin
{
  public abstract PropertyConfig getPropertyConfig(String paramString)
    throws FFSException;
  
  public abstract PropertyConfig[] getAllServers()
    throws FFSException;
  
  public abstract void addServer(PropertyConfig paramPropertyConfig)
    throws FFSException;
  
  public abstract PropertyConfig updateServer(PropertyConfig paramPropertyConfig)
    throws FFSException;
  
  public abstract void deleteServer(String paramString)
    throws FFSException;
  
  public abstract void registerInstructionType(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, String paramString5, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, String paramString6, String paramString7)
    throws FFSException;
  
  public abstract void updateInstructionType(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, String paramString5, int paramInt2, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3, boolean paramBoolean4, int paramInt3, boolean paramBoolean5, boolean paramBoolean6, boolean paramBoolean7, String paramString6, String paramString7)
    throws FFSException;
  
  public abstract void deleteInstructionType(String paramString1, String paramString2)
    throws FFSException;
  
  public abstract InstructionType[] getInstructionTypes(String paramString)
    throws FFSException;
  
  public abstract InstructionType[] getInstructionTypes()
    throws FFSException;
  
  public abstract FFSProperties getProperties();
  
  public abstract void doDeploy()
    throws FFSException;
  
  public abstract FFSDBProperties getBPWServerDBProperties();
  
  public abstract FFSDBProperties getBPWServerDBProperties(DBConnectionInfo paramDBConnectionInfo);
  
  public abstract void registerInstructionType(InstructionType paramInstructionType)
    throws FFSException;
  
  public abstract void registerInstructionType(InstructionType[] paramArrayOfInstructionType)
    throws FFSException;
  
  public abstract void updateInstructionType(InstructionType paramInstructionType)
    throws FFSException;
  
  public abstract void deleteInstructionType()
    throws FFSException;
  
  public abstract boolean mbRuntimeConfigured();
  
  public abstract boolean serverDBConfigured();
  
  public abstract void setMBRuntimeConfigured(boolean paramBoolean);
  
  public abstract void setServerDBConfigured(boolean paramBoolean);
  
  public abstract DBConnectionInfo getBPWServerDBInfo();
  
  public abstract DBConnectionInfo getMBRuntimeDBInfo();
  
  public abstract void setBPWServerDBInfo(DBConnectionInfo paramDBConnectionInfo);
  
  public abstract void setMBRumtimeDBInfo(DBConnectionInfo paramDBConnectionInfo);
  
  public abstract BPWFIInfo[] getFinancialInstitutions()
    throws FFSException;
  
  public abstract BPWFIInfo addFinancialInstitution(BPWFIInfo paramBPWFIInfo)
    throws FFSException;
  
  public abstract BPWFIInfo modifyFinancialInstitution(BPWFIInfo paramBPWFIInfo)
    throws FFSException;
  
  public abstract void deleteFinancialInstitution(BPWFIInfo paramBPWFIInfo)
    throws FFSException;
  
  public abstract BPWFIInfo getFinancialInstitution(String paramString)
    throws FFSException;
  
  public abstract FulfillmentInfo[] getFulfillmentSystems()
    throws FFSException;
  
  public abstract void addFulfillmentSystem(FulfillmentInfo paramFulfillmentInfo)
    throws FFSException;
  
  public abstract void modifyFulfillmentSystem(FulfillmentInfo paramFulfillmentInfo)
    throws FFSException;
  
  public abstract void deleteFulfillmentSystem(FulfillmentInfo paramFulfillmentInfo)
    throws FFSException;
  
  public abstract PayeeRouteInfo getPayeeRouteInfo(String paramString, int paramInt)
    throws FFSException;
  
  public abstract String addPayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws FFSException;
  
  public abstract void modifyPayee(PayeeInfo paramPayeeInfo, PayeeRouteInfo paramPayeeRouteInfo)
    throws FFSException;
  
  public abstract PayeeInfo getPayeeByID(String paramString)
    throws FFSException;
  
  public abstract PayeeInfo[] searchGlobalPayees(String paramString)
    throws FFSException;
  
  public abstract void deletePayee(String paramString)
    throws FFSException;
  
  public abstract ServerGeneralConfig getServerGeneralConfig();
  
  public abstract void updateServerGeneralConfig(ServerGeneralConfig paramServerGeneralConfig)
    throws FFSException;
  
  public abstract void addClusterSettings(PropertyConfig paramPropertyConfig);
  
  public abstract void addClusterOtherProperties(PropertyConfig paramPropertyConfig);
  
  public abstract ProcessingWindowInfo addProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException;
  
  public abstract ProcessingWindowInfo modProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException;
  
  public abstract ProcessingWindowInfo delProcessingWindow(ProcessingWindowInfo paramProcessingWindowInfo)
    throws FFSException;
  
  public abstract ProcessingWindowList getProcessingWindows(ProcessingWindowList paramProcessingWindowList)
    throws FFSException;
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.config.BPWAdmin
 * JD-Core Version:    0.7.0.1
 */