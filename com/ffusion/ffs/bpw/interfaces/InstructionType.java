package com.ffusion.ffs.bpw.interfaces;

import java.util.Properties;

public class InstructionType
  extends BPWInfoBase
{
  public String FIId;
  public String InstructionType;
  public String HandlerClassName;
  public String SchedulerStartTime;
  public int SchedulerInterval;
  public String DispatchStartTime;
  public int DispatchInterval;
  public int ResubmitEventSupported;
  public int ChkTmAutoRecover;
  public int FileBasedRecovery;
  public int ActiveFlag;
  public int RouteID;
  public boolean useCutOffs;
  public boolean createEmptyFile;
  public boolean processOnHolidays;
  public String category;
  public String memo;
  public static String[] standardInstTypes = { "INTRATRN", "RECINTRATRN", "ACHFILETRN", "ACHBATCHTRN", "RECACHBATCHTRN", "PMTTRN", "RECPMTTRN", "PAYEETRN", "FUNDTRN", "RECFUNDTRN", "REVERTFUNDTRN", "FUNDAPPRTRN", "RECFUNDAPPRTRN", "REVERTFUNDAPPRTRN", "WIRETRN", "RECWIRETRN", "WIREAPPROVALTRN", "RECWIREAPPROVALTRN", "WIREREVERTTRN", "WIREBATCHTRN", "WIREBATCHAPPROVALTRN", "RECTRANSFERTRN", "ETFTRN", "RECETFTRN", "ACHCLEANUP", "TEMPHISTCLEANUP", "ON_US_PMTTRN", "SAMPLE_PMTTRN", "CHECKFREE_PMTTRN", "RPPS_PMTTRN", "METAVANTE_PMTTRN", "ORCC_PMTTRN", "CASHCONTRN" };
  
  public InstructionType() {}
  
  public InstructionType(InstructionType paramInstructionType)
  {
    this.FIId = paramInstructionType.FIId;
    this.InstructionType = paramInstructionType.InstructionType;
    this.HandlerClassName = paramInstructionType.HandlerClassName;
    this.SchedulerStartTime = paramInstructionType.SchedulerStartTime;
    this.SchedulerInterval = paramInstructionType.SchedulerInterval;
    this.DispatchStartTime = paramInstructionType.DispatchStartTime;
    this.DispatchInterval = paramInstructionType.DispatchInterval;
    this.ResubmitEventSupported = paramInstructionType.ResubmitEventSupported;
    this.ChkTmAutoRecover = paramInstructionType.ChkTmAutoRecover;
    this.FileBasedRecovery = paramInstructionType.FileBasedRecovery;
    this.ActiveFlag = paramInstructionType.ActiveFlag;
    this.RouteID = paramInstructionType.RouteID;
  }
  
  public InstructionType(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt1, String paramString5, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6, int paramInt7)
  {
    this.FIId = paramString1;
    this.InstructionType = paramString2;
    this.HandlerClassName = paramString3;
    this.SchedulerStartTime = paramString4;
    this.SchedulerInterval = paramInt1;
    this.DispatchStartTime = paramString5;
    this.DispatchInterval = paramInt2;
    this.ResubmitEventSupported = paramInt3;
    this.ChkTmAutoRecover = paramInt4;
    this.FileBasedRecovery = paramInt5;
    this.ActiveFlag = paramInt6;
    this.RouteID = paramInt7;
  }
  
  public InstructionType(Properties paramProperties)
    throws Exception
  {
    String str = null;
    this.FIId = paramProperties.getProperty("FIId");
    this.InstructionType = paramProperties.getProperty("InstructionType");
    this.HandlerClassName = paramProperties.getProperty("HandlerClassName");
    this.SchedulerStartTime = paramProperties.getProperty("SchedulerStartTime");
    str = paramProperties.getProperty("SchedulerInterval");
    if (str != null) {
      this.SchedulerInterval = Integer.parseInt(str);
    }
    this.DispatchStartTime = paramProperties.getProperty("DispatchStartTime");
    str = paramProperties.getProperty("DispatchInterval");
    if (str != null) {
      this.DispatchInterval = Integer.parseInt(str);
    }
    str = paramProperties.getProperty("ResubmitEventSupported");
    if (str != null) {
      this.ResubmitEventSupported = (str.equalsIgnoreCase("Y") ? 1 : 0);
    }
    str = paramProperties.getProperty("ChkTmAutoRecover");
    if (str != null) {
      this.ChkTmAutoRecover = (str.equalsIgnoreCase("Y") ? 1 : 0);
    }
    str = paramProperties.getProperty("FileBasedRecovery");
    if (str != null) {
      this.FileBasedRecovery = (str.equalsIgnoreCase("Y") ? 1 : 0);
    }
    str = paramProperties.getProperty("ActiveFlag");
    if (str != null) {
      this.ActiveFlag = (str.equalsIgnoreCase("Y") ? 1 : 0);
    }
    str = paramProperties.getProperty("RouteID");
    if (str != null) {
      this.RouteID = Integer.parseInt(str);
    }
    str = paramProperties.getProperty("ProcessOnHolidays");
    if (str != null) {
      this.processOnHolidays = (str.equalsIgnoreCase("Y"));
    }
    str = paramProperties.getProperty("CreateEmptyFile");
    if (str != null) {
      this.createEmptyFile = (str.equalsIgnoreCase("Y"));
    }
    str = paramProperties.getProperty("UseCutOffs");
    if (str != null) {
      this.useCutOffs = (str.equalsIgnoreCase("Y"));
    }
    this.category = paramProperties.getProperty("Category");
    this.memo = paramProperties.getProperty("Memo");
  }
  
  public boolean verify()
  {
    boolean bool = true;
    if ((this.FIId == null) || (this.FIId.trim().length() == 0)) {
      bool = false;
    }
    if ((this.InstructionType == null) || (this.InstructionType.trim().length() == 0)) {
      bool = false;
    }
    if ((this.HandlerClassName == null) || (this.HandlerClassName.trim().length() == 0)) {
      bool = false;
    }
    if (!this.useCutOffs)
    {
      if ((this.SchedulerStartTime == null) || (this.HandlerClassName.trim().length() == 0)) {
        bool = false;
      }
      if (this.SchedulerInterval <= 0) {
        bool = false;
      }
    }
    if ((this.ResubmitEventSupported != 0) && (this.ResubmitEventSupported != 1)) {
      bool = false;
    }
    if ((this.ChkTmAutoRecover != 0) && (this.ChkTmAutoRecover != 1)) {
      bool = false;
    }
    if ((this.FileBasedRecovery != 0) && (this.FileBasedRecovery != 1)) {
      bool = false;
    }
    if ((this.ActiveFlag != 0) && (this.ActiveFlag != 1)) {
      bool = false;
    }
    if ((this.category == null) || (this.category.trim().length() == 0)) {
      bool = false;
    }
    return bool;
  }
  
  public static boolean isStandardInstType(String paramString)
  {
    for (int i = 0; i < standardInstTypes.length; i++) {
      if (standardInstTypes[i].equals(paramString.trim())) {
        return true;
      }
    }
    return false;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.InstructionType
 * JD-Core Version:    0.7.0.1
 */