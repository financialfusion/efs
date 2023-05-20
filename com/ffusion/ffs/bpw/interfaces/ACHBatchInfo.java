package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.interfaces.FFSException;
import com.ffusion.ffs.util.FFSConst;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeADVBatchControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeBatchControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeBatchHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeCBRandPBRBatchHeaderRecord;
import java.io.PrintStream;
import java.util.Hashtable;

public class ACHBatchInfo
  extends BPWInfoBase
  implements FFSConst, ACHConsts, Cloneable
{
  protected String sB = null;
  protected String sR;
  protected String sS = null;
  protected ACHRecordInfo s4 = null;
  protected ACHRecordInfo s5 = null;
  protected ACHRecordInfo[] s1 = null;
  protected String sW = null;
  protected String sw = null;
  protected String sx = "ACH_BATCH_PAYMENT";
  protected String sT = "UnbalancedBatch";
  protected String sV = null;
  protected String sX = null;
  protected String sH = null;
  protected String sK = null;
  protected String sC = "MsgSet";
  protected String sD = null;
  protected String sA = null;
  protected long sG = 0L;
  protected long sy = 0L;
  protected long sM = 0L;
  protected String su = "add";
  protected String sP = null;
  protected String sY = null;
  protected String s0 = null;
  protected String s3 = null;
  protected String sZ = null;
  protected String sF = null;
  protected String s6 = null;
  protected String sN = null;
  protected String sU;
  protected Hashtable sz;
  protected String s2 = null;
  protected Hashtable sE = null;
  protected String sv = null;
  protected String sO = null;
  protected long sQ = 0L;
  protected long sI = 0L;
  protected int sL = 0;
  protected String sJ = null;
  
  public ACHBatchInfo()
  {
    this.statusCode = -1;
    this.statusMsg = null;
  }
  
  public Hashtable getMemo()
  {
    return this.sE;
  }
  
  public void setMemo(Hashtable paramHashtable)
  {
    this.sE = paramHashtable;
  }
  
  public String getExportFileName()
  {
    return this.sU;
  }
  
  public void setExportFileName(String paramString)
  {
    this.sU = paramString;
  }
  
  public String getBatchId()
  {
    return this.sB;
  }
  
  public void setBatchId(String paramString)
  {
    this.sB = paramString;
  }
  
  public String getRecBatchId()
  {
    return this.sR;
  }
  
  public void setRecBatchId(String paramString)
  {
    this.sR = paramString;
  }
  
  public String getFileId()
  {
    return this.sS;
  }
  
  public void setFileId(String paramString)
  {
    this.sS = paramString;
  }
  
  public ACHRecordInfo getBatchHeader()
  {
    return this.s4;
  }
  
  public void setBatchHeader(ACHRecordInfo paramACHRecordInfo)
  {
    this.s4 = paramACHRecordInfo;
  }
  
  public ACHRecordInfo getBatchControl()
  {
    return this.s5;
  }
  
  public void setBatchControl(ACHRecordInfo paramACHRecordInfo)
  {
    this.s5 = paramACHRecordInfo;
  }
  
  public ACHRecordInfo[] getRecords()
  {
    return this.s1;
  }
  
  public ACHRecordInfo getRecordsAt(int paramInt)
  {
    if (this.s1 == null) {
      return null;
    }
    if (paramInt < this.s1.length) {
      return this.s1[paramInt];
    }
    return null;
  }
  
  public void setRecords(ACHRecordInfo[] paramArrayOfACHRecordInfo)
  {
    this.s1 = paramArrayOfACHRecordInfo;
  }
  
  public String getBatchContent()
  {
    return this.sW;
  }
  
  public void setBatchContent(String paramString)
  {
    this.sW = paramString;
  }
  
  public String getBatchType()
  {
    return this.sw;
  }
  
  public void setBatchType(String paramString)
  {
    this.sw = paramString;
  }
  
  public String getBatchCategory()
  {
    return this.sx;
  }
  
  public void setBatchCategory(String paramString)
  {
    this.sx = paramString;
  }
  
  public String getBatchBalanceType()
  {
    return this.sT;
  }
  
  public void setBatchBalanceType(String paramString)
  {
    this.sT = paramString;
  }
  
  public String getClassCode()
  {
    return this.sV;
  }
  
  public void setClassCode(String paramString)
  {
    this.sV = paramString;
  }
  
  public String getDueDate()
  {
    return this.sX;
  }
  
  public void setDueDate(String paramString)
  {
    this.sX = paramString;
  }
  
  public String getBatchStatus()
  {
    return this.sK;
  }
  
  public void setBatchStatus(String paramString)
  {
    this.sK = paramString;
  }
  
  public String getAchVersion()
  {
    return this.sC;
  }
  
  public void setAchVersion(String paramString)
  {
    this.sC = paramString;
  }
  
  public String getStartRecordId()
  {
    return this.sD;
  }
  
  public void setStartRecordId(String paramString)
  {
    this.sD = paramString;
  }
  
  public String getEndRecordId()
  {
    return this.sA;
  }
  
  public void setEndRecordId(String paramString)
  {
    this.sA = paramString;
  }
  
  public long getTotalBatchSize()
  {
    return this.sG;
  }
  
  public void setTotalBatchSize(long paramLong)
  {
    this.sG = paramLong;
  }
  
  public long getRecordCursor()
  {
    return this.sy;
  }
  
  public String getRecordCursorStr()
  {
    return new Long(this.sy).toString();
  }
  
  public void setRecordCursor(long paramLong)
  {
    this.sy = paramLong;
  }
  
  public void setRecordCursor(String paramString)
  {
    try
    {
      this.sy = Long.parseLong(paramString);
    }
    catch (Exception localException)
    {
      System.out.println("ACHBatchInfo.setRecordCursor failed: " + localException.toString());
      localException.printStackTrace();
      this.sy = 0L;
    }
  }
  
  public long getBatchPageSize()
  {
    return this.sM;
  }
  
  public void setBatchPageSize(long paramLong)
  {
    this.sM = paramLong;
  }
  
  public String getAction()
  {
    return this.su;
  }
  
  public void setAction(String paramString)
  {
    this.su = paramString;
  }
  
  public String getCompId()
  {
    return this.sP;
  }
  
  public void setCompId(String paramString)
  {
    this.sP = paramString;
  }
  
  public String getOdfiId()
  {
    return this.sY;
  }
  
  public void setOdfiId(String paramString)
  {
    this.sY = paramString;
  }
  
  public String getFiId()
  {
    return this.s0;
  }
  
  public void setFiId(String paramString)
  {
    this.s0 = paramString;
  }
  
  public String getCompAchId()
  {
    return this.s3;
  }
  
  public void setCompAchId(String paramString)
  {
    this.s3 = paramString;
  }
  
  public String getCompName()
  {
    return this.sZ;
  }
  
  public void setCompName(String paramString)
  {
    this.sZ = paramString;
  }
  
  public String getOdfiRTN()
  {
    return this.sF;
  }
  
  public void setOdfiRTN(String paramString)
  {
    this.sF = paramString;
  }
  
  public String getOdfiName()
  {
    return this.s6;
  }
  
  public void setOdfiName(String paramString)
  {
    this.s6 = paramString;
  }
  
  public String getDtProcessed()
  {
    return this.sH;
  }
  
  public void setDtProcessed(String paramString)
  {
    this.sH = paramString;
  }
  
  public void setCustomerId(String paramString)
  {
    this.sN = paramString;
  }
  
  public String getCustomerId()
  {
    return this.sN;
  }
  
  public void setTranId(String paramString)
  {
    this.s2 = paramString;
  }
  
  public String getTranId()
  {
    return this.s2;
  }
  
  public String getOffsetAccountID()
  {
    return this.sv;
  }
  
  public void setOffsetAccountID(String paramString)
  {
    this.sv = paramString;
  }
  
  public int getBatchHeaderFieldValueShort(int paramInt)
  {
    Short localShort = (Short)getBatchHeaderFieldValueObject(paramInt);
    int i = 0;
    try
    {
      i = localShort.shortValue();
    }
    catch (Exception localException)
    {
      System.out.println("ACHBatchInfo.getBatchHeaderFieldValueShort failed: " + localException.toString());
      localException.printStackTrace();
    }
    return i;
  }
  
  public int getBatchHeaderFieldValueInt(int paramInt)
  {
    Integer localInteger = (Integer)getBatchHeaderFieldValueObject(paramInt);
    int i = 0;
    try
    {
      i = localInteger.intValue();
    }
    catch (Exception localException)
    {
      System.out.println("ACHBatchInfo.getBatchHeaderFieldValueInt failed: " + localException.toString());
      localException.printStackTrace();
    }
    return i;
  }
  
  public String getBatchHeaderFieldValueString(int paramInt)
  {
    return (String)getBatchHeaderFieldValueObject(paramInt);
  }
  
  public Object getBatchHeaderFieldValueObject(int paramInt)
  {
    if (this.sV == null)
    {
      System.out.println("####ACHBatchInfo.getBatchHeaderFieldValueObject: classCode is null.");
      return null;
    }
    if (this.s4 == null)
    {
      System.out.println("####ACHBatchInfo.getBatchHeaderFieldValueObject: batchHeader is null.");
      return null;
    }
    if ((this.sV.equalsIgnoreCase("CBR")) || (this.sV.equalsIgnoreCase("PBR")))
    {
      localObject = (TypeCBRandPBRBatchHeaderRecord)this.s4.getRecord();
      switch (paramInt)
      {
      case 1: 
        return new Short(((TypeCBRandPBRBatchHeaderRecord)localObject).Record_Type_Code);
      case 2: 
        return new Short(((TypeCBRandPBRBatchHeaderRecord)localObject).Service_Class_Code);
      case 3: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).Company_Name;
      case 5: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).Company_Identification;
      case 6: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).Standard_Entry_Class_Code;
      case 7: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).Company_Entry_Description;
      case 9: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).Effective_Entry_Date;
      case 10: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).Settlement_Date;
      case 11: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).Originating_DFI_Identification;
      case 12: 
        return new Long(((TypeCBRandPBRBatchHeaderRecord)localObject).Batch_Number);
      case 13: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).Originator_Status_Code;
      case 14: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).ISO_Destination_Country_Code;
      case 15: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).ISO_Original_Currency_Code;
      case 16: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).ISO_Dest_Currency_Code;
      case 17: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).Foreign_Exchange_Indicator;
      case 18: 
        return new Short(((TypeCBRandPBRBatchHeaderRecord)localObject).Foreign_Exchange_Reference_Indicator);
      case 19: 
        return ((TypeCBRandPBRBatchHeaderRecord)localObject).Foreign_Exchange_Reference;
      }
      return null;
    }
    Object localObject = (TypeBatchHeaderRecord)this.s4.getRecord();
    switch (paramInt)
    {
    case 1: 
      return new Short(((TypeBatchHeaderRecord)localObject).Record_Type_Code);
    case 2: 
      return new Short(((TypeBatchHeaderRecord)localObject).Service_Class_Code);
    case 3: 
      return ((TypeBatchHeaderRecord)localObject).Company_Name;
    case 4: 
      return ((TypeBatchHeaderRecord)localObject).Company_Discretionary_Data;
    case 5: 
      return ((TypeBatchHeaderRecord)localObject).Company_Identification;
    case 6: 
      return ((TypeBatchHeaderRecord)localObject).Standard_Entry_Class_Code;
    case 7: 
      return ((TypeBatchHeaderRecord)localObject).Company_Entry_Description;
    case 8: 
      return ((TypeBatchHeaderRecord)localObject).Company_Descriptive_Date;
    case 9: 
      return ((TypeBatchHeaderRecord)localObject).Effective_Entry_Date;
    case 10: 
      return ((TypeBatchHeaderRecord)localObject).Settlement_Date;
    case 11: 
      return ((TypeBatchHeaderRecord)localObject).Originating_DFI_Identification;
    case 12: 
      return new Long(((TypeBatchHeaderRecord)localObject).Batch_Number);
    case 13: 
      return ((TypeBatchHeaderRecord)localObject).Originator_Status_Code;
    }
    return null;
  }
  
  public void setBatchHeaderFieldValueObject(int paramInt, String paramString)
  {
    if (this.sV == null)
    {
      System.out.println("####ACHBatchInfo.setBatchHeaderFieldValueObject: classCode is null.");
      return;
    }
    if (paramString == null) {
      return;
    }
    if (this.s4 == null)
    {
      System.out.println("####ACHBatchInfo.setBatchHeaderFieldValueObject: batchHeader is null.");
      return;
    }
    Object localObject;
    if ((this.sV.equalsIgnoreCase("CBR")) || (this.sV.equalsIgnoreCase("PBR"))) {
      localObject = (TypeCBRandPBRBatchHeaderRecord)this.s4.getRecord();
    }
    switch (paramInt)
    {
    case 1: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Record_Type_Code = Short.parseShort(paramString);
      break;
    case 2: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Service_Class_Code = Short.parseShort(paramString);
      break;
    case 3: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Company_Name = paramString;
      break;
    case 5: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Company_Identification = paramString;
      break;
    case 6: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Standard_Entry_Class_Code = paramString;
      break;
    case 7: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Company_Entry_Description = paramString;
      break;
    case 9: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Effective_Entry_Date = paramString;
      break;
    case 10: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Settlement_Date = paramString;
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Settlement_DateExists = true;
      break;
    case 11: 
      if ((paramString != null) && (paramString.length() > 8)) {
        paramString = paramString.substring(0, 8);
      }
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Originating_DFI_Identification = paramString;
      break;
    case 12: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Batch_Number = Long.parseLong(paramString);
      break;
    case 13: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Originator_Status_Code = paramString;
      break;
    case 14: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).ISO_Destination_Country_Code = paramString;
      break;
    case 15: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).ISO_Original_Currency_Code = paramString;
      break;
    case 16: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).ISO_Dest_Currency_Code = paramString;
      break;
    case 17: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Foreign_Exchange_Indicator = paramString;
      break;
    case 18: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Foreign_Exchange_Reference_Indicator = Short.parseShort(paramString);
      break;
    case 19: 
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Foreign_Exchange_Reference = paramString;
      break;
    case 4: 
    case 8: 
    default: 
      break;
      localObject = (TypeBatchHeaderRecord)this.s4.getRecord();
      switch (paramInt)
      {
      case 1: 
        ((TypeBatchHeaderRecord)localObject).Record_Type_Code = Short.parseShort(paramString);
        break;
      case 2: 
        ((TypeBatchHeaderRecord)localObject).Service_Class_Code = Short.parseShort(paramString);
        break;
      case 3: 
        ((TypeBatchHeaderRecord)localObject).Company_Name = paramString;
        break;
      case 4: 
        ((TypeBatchHeaderRecord)localObject).Company_Discretionary_Data = paramString;
        break;
      case 5: 
        ((TypeBatchHeaderRecord)localObject).Company_Identification = paramString;
        break;
      case 6: 
        ((TypeBatchHeaderRecord)localObject).Standard_Entry_Class_Code = paramString;
        break;
      case 7: 
        ((TypeBatchHeaderRecord)localObject).Company_Entry_Description = paramString;
        break;
      case 8: 
        ((TypeBatchHeaderRecord)localObject).Company_Descriptive_Date = paramString;
        ((TypeBatchHeaderRecord)localObject).Company_Descriptive_DateExists = true;
        break;
      case 9: 
        ((TypeBatchHeaderRecord)localObject).Effective_Entry_Date = paramString;
        break;
      case 10: 
        ((TypeBatchHeaderRecord)localObject).Settlement_Date = paramString;
        ((TypeBatchHeaderRecord)localObject).Settlement_DateExists = true;
        break;
      case 11: 
        if ((paramString != null) && (paramString.length() > 8)) {
          paramString = paramString.substring(0, 8);
        }
        ((TypeBatchHeaderRecord)localObject).Originating_DFI_Identification = paramString;
        break;
      case 12: 
        ((TypeBatchHeaderRecord)localObject).Batch_Number = Long.parseLong(paramString);
        break;
      case 13: 
        ((TypeBatchHeaderRecord)localObject).Originator_Status_Code = paramString;
      }
      break;
    }
  }
  
  public long getBatchControlFieldValueInt(int paramInt)
  {
    int i = 0;
    try
    {
      Integer localInteger = (Integer)getBatchControlFieldValueObject(paramInt);
      i = localInteger.intValue();
    }
    catch (Exception localException)
    {
      System.out.println("ACHBatchInfo.getBatchHeaderFieldValueInt failed: " + localException.toString());
      localException.printStackTrace();
    }
    return i;
  }
  
  public long getBatchControlFieldValueLong(int paramInt)
  {
    long l = 0L;
    try
    {
      Long localLong = (Long)getBatchControlFieldValueObject(paramInt);
      l = localLong.longValue();
    }
    catch (Exception localException)
    {
      System.out.println("ACHBatchInfo.getBatchHeaderFieldValueInt failed: " + localException.toString());
      localException.printStackTrace();
    }
    return l;
  }
  
  public String getBatchControlFieldValueString(int paramInt)
  {
    return (String)getBatchControlFieldValueObject(paramInt);
  }
  
  public Object getBatchControlFieldValueObject(int paramInt)
  {
    if (this.sV == null)
    {
      System.out.println("####ACHBatchInfo.getBatchControlFieldValueObject: classCode is null.");
      return null;
    }
    if (this.s5 == null) {
      return null;
    }
    if (this.sV.equalsIgnoreCase("ADV"))
    {
      localObject = (TypeADVBatchControlRecord)this.s5.getRecord();
      switch (paramInt)
      {
      case 1: 
        return new Short(((TypeADVBatchControlRecord)localObject).Record_Type_Code);
      case 2: 
        return new Short(((TypeADVBatchControlRecord)localObject).Service_Class_Code);
      case 20: 
        return new Integer(((TypeADVBatchControlRecord)localObject).Entry_Addenda_Count);
      case 21: 
        return new Long(((TypeADVBatchControlRecord)localObject).Entry_Hash);
      case 22: 
        return new Long(((TypeADVBatchControlRecord)localObject).Total_Debits);
      case 23: 
        return new Long(((TypeADVBatchControlRecord)localObject).Total_Credits);
      case 24: 
        return ((TypeADVBatchControlRecord)localObject).ACH_Operator_Data;
      case 11: 
        return ((TypeADVBatchControlRecord)localObject).Originating_DFI_Identification;
      case 12: 
        return new Long(((TypeADVBatchControlRecord)localObject).Batch_Number);
      }
      return null;
    }
    Object localObject = (TypeBatchControlRecord)this.s5.getRecord();
    switch (paramInt)
    {
    case 1: 
      return new Short(((TypeBatchControlRecord)localObject).Record_Type_Code);
    case 2: 
      return new Short(((TypeBatchControlRecord)localObject).Service_Class_Code);
    case 20: 
      return new Integer(((TypeBatchControlRecord)localObject).Entry_Addenda_Count);
    case 21: 
      return new Long(((TypeBatchControlRecord)localObject).Entry_Hash);
    case 22: 
      return new Long(((TypeBatchControlRecord)localObject).Total_Debits);
    case 23: 
      return new Long(((TypeBatchControlRecord)localObject).Total_Credits);
    case 5: 
      return ((TypeBatchControlRecord)localObject).Company_Identification;
    case 25: 
      return ((TypeBatchControlRecord)localObject).Message_Authentication_Code;
    case 11: 
      return ((TypeBatchControlRecord)localObject).Originating_DFI_Identification;
    case 12: 
      return new Long(((TypeBatchControlRecord)localObject).Batch_Number);
    }
    return null;
  }
  
  public void setBatchControlFieldValueObject(int paramInt, String paramString)
  {
    if (this.sV == null)
    {
      System.out.println("####ACHBatchInfo.setBatchControlFieldValueObject: classCode is null.");
      return;
    }
    if (paramString == null) {
      return;
    }
    if (this.s5 == null)
    {
      System.out.println("####ACHBatchInfo.setBatchControlFieldValueObject: batchControl is null.");
      return;
    }
    Object localObject;
    if (this.sV.equalsIgnoreCase("ADV"))
    {
      localObject = (TypeADVBatchControlRecord)this.s5.getRecord();
      switch (paramInt)
      {
      case 1: 
        ((TypeADVBatchControlRecord)localObject).Record_Type_Code = Short.parseShort(paramString);
        break;
      case 2: 
        ((TypeADVBatchControlRecord)localObject).Service_Class_Code = Short.parseShort(paramString);
        break;
      case 20: 
        ((TypeADVBatchControlRecord)localObject).Entry_Addenda_Count = Integer.parseInt(paramString);
        break;
      case 21: 
        ((TypeADVBatchControlRecord)localObject).Entry_Hash = Long.parseLong(paramString);
        break;
      case 22: 
        ((TypeADVBatchControlRecord)localObject).Total_Debits = Long.parseLong(paramString);
        break;
      case 23: 
        ((TypeADVBatchControlRecord)localObject).Total_Credits = Long.parseLong(paramString);
        break;
      case 24: 
        ((TypeADVBatchControlRecord)localObject).ACH_Operator_Data = paramString;
        ((TypeADVBatchControlRecord)localObject).ACH_Operator_DataExists = true;
        break;
      case 11: 
        if ((paramString != null) && (paramString.length() > 8)) {
          paramString = paramString.substring(0, 8);
        }
        ((TypeADVBatchControlRecord)localObject).Originating_DFI_Identification = paramString;
        break;
      case 12: 
        ((TypeADVBatchControlRecord)localObject).Batch_Number = Long.parseLong(paramString);
        break;
      }
    }
    else
    {
      localObject = (TypeBatchControlRecord)this.s5.getRecord();
      switch (paramInt)
      {
      case 1: 
        ((TypeBatchControlRecord)localObject).Record_Type_Code = Short.parseShort(paramString);
        break;
      case 2: 
        ((TypeBatchControlRecord)localObject).Service_Class_Code = Short.parseShort(paramString);
        break;
      case 20: 
        ((TypeBatchControlRecord)localObject).Entry_Addenda_Count = Integer.parseInt(paramString);
        break;
      case 21: 
        ((TypeBatchControlRecord)localObject).Entry_Hash = Long.parseLong(paramString);
        break;
      case 22: 
        ((TypeBatchControlRecord)localObject).Total_Debits = Long.parseLong(paramString);
        break;
      case 23: 
        ((TypeBatchControlRecord)localObject).Total_Credits = Long.parseLong(paramString);
        break;
      case 5: 
        ((TypeBatchControlRecord)localObject).Company_Identification = paramString;
        break;
      case 25: 
        ((TypeBatchControlRecord)localObject).Message_Authentication_Code = paramString;
        ((TypeBatchControlRecord)localObject).Message_Authentication_CodeExists = true;
        break;
      case 11: 
        if ((paramString != null) && (paramString.length() > 8)) {
          paramString = paramString.substring(0, 8);
        }
        ((TypeBatchControlRecord)localObject).Originating_DFI_Identification = paramString;
        break;
      case 12: 
        ((TypeBatchControlRecord)localObject).Batch_Number = Long.parseLong(paramString);
        break;
      }
    }
  }
  
  public void createBatchHeader()
  {
    this.s4 = new ACHRecordInfo();
    this.s4.setRecordType(5);
    Object localObject;
    if ((this.sV.equalsIgnoreCase("CBR")) || (this.sV.equalsIgnoreCase("PBR")))
    {
      localObject = new TypeCBRandPBRBatchHeaderRecord();
      ((TypeCBRandPBRBatchHeaderRecord)localObject).Record_Type_Code = 5;
      this.s4.setRecord(localObject);
      this.s4.setClassCode(this.sV);
    }
    else
    {
      localObject = new TypeBatchHeaderRecord();
      ((TypeBatchHeaderRecord)localObject).Record_Type_Code = 5;
      this.s4.setRecord(localObject);
    }
  }
  
  public void createBatchControl()
  {
    this.s5 = new ACHRecordInfo();
    this.s5.setRecordType(8);
    Object localObject;
    if (this.sV.equalsIgnoreCase("ADV"))
    {
      localObject = new TypeADVBatchControlRecord();
      ((TypeADVBatchControlRecord)localObject).Record_Type_Code = 8;
      ((TypeADVBatchControlRecord)localObject).ACH_Operator_Data = " ";
      ((TypeADVBatchControlRecord)localObject).ACH_Operator_DataExists = true;
      this.s5.setRecord(localObject);
      this.s5.setClassCode(this.sV);
    }
    else
    {
      localObject = new TypeBatchControlRecord();
      ((TypeBatchControlRecord)localObject).Record_Type_Code = 8;
      ((TypeBatchControlRecord)localObject).Message_Authentication_CodeExists = true;
      ((TypeBatchControlRecord)localObject).Message_Authentication_Code = " ";
      ((TypeBatchControlRecord)localObject).Reserved6 = " ";
      this.s5.setRecord(localObject);
    }
  }
  
  public int getRecordAddendaCount()
  {
    int i = 0;
    if (this.s1 == null) {
      return 0;
    }
    i += this.s1.length;
    for (int j = 0; j < this.s1.length; j++) {
      if (this.s1[j].getAddenda() != null) {
        i += this.s1[j].getAddenda().length;
      }
    }
    return i;
  }
  
  public boolean isLastPage()
  {
    return getRecordAddendaCount() < this.sM;
  }
  
  public String getHeaderCompName()
  {
    return this.sJ;
  }
  
  public void setHeaderCompName(String paramString)
  {
    this.sJ = paramString;
  }
  
  public String getTaxState()
  {
    if (this.sE != null) {
      return (String)this.sE.get("TAXSTATE");
    }
    return null;
  }
  
  public String getLastModifier()
  {
    return this.sO;
  }
  
  public void setLastModifier(String paramString)
  {
    this.sO = paramString;
  }
  
  public Object clone()
  {
    ACHBatchInfo localACHBatchInfo = null;
    try
    {
      localACHBatchInfo = (ACHBatchInfo)super.clone();
    }
    catch (Exception localException) {}
    if (this.s1 != null)
    {
      int i = this.s1.length;
      localACHBatchInfo.s1 = new ACHRecordInfo[i];
      for (int j = 0; j < i; j++) {
        localACHBatchInfo.s1[j] = ((ACHRecordInfo)this.s1[j].clone());
      }
    }
    if (this.s5 != null)
    {
      localACHBatchInfo.s5 = ((ACHRecordInfo)this.s5.clone());
      localACHBatchInfo.s5.setRecord(ai());
    }
    return localACHBatchInfo;
  }
  
  private Object ai()
  {
    if (this.sV == null) {
      this.sV = "";
    }
    if (this.sV.equalsIgnoreCase("ADV"))
    {
      localObject1 = new TypeADVBatchControlRecord();
      localObject2 = (TypeADVBatchControlRecord)this.s5.getRecord();
      ((TypeADVBatchControlRecord)localObject1).Record_Type_Code = ((TypeADVBatchControlRecord)localObject2).Record_Type_Code;
      ((TypeADVBatchControlRecord)localObject1).Service_Class_Code = ((TypeADVBatchControlRecord)localObject2).Service_Class_Code;
      ((TypeADVBatchControlRecord)localObject1).Entry_Addenda_Count = ((TypeADVBatchControlRecord)localObject2).Entry_Addenda_Count;
      ((TypeADVBatchControlRecord)localObject1).Entry_Hash = ((TypeADVBatchControlRecord)localObject2).Entry_Hash;
      ((TypeADVBatchControlRecord)localObject1).Total_Debits = ((TypeADVBatchControlRecord)localObject2).Total_Debits;
      ((TypeADVBatchControlRecord)localObject1).Total_Credits = ((TypeADVBatchControlRecord)localObject2).Total_Credits;
      ((TypeADVBatchControlRecord)localObject1).ACH_Operator_Data = ((TypeADVBatchControlRecord)localObject2).ACH_Operator_Data;
      ((TypeADVBatchControlRecord)localObject1).ACH_Operator_DataExists = ((TypeADVBatchControlRecord)localObject2).ACH_Operator_DataExists;
      ((TypeADVBatchControlRecord)localObject1).Originating_DFI_Identification = ((TypeADVBatchControlRecord)localObject2).Originating_DFI_Identification;
      ((TypeADVBatchControlRecord)localObject1).Batch_Number = ((TypeADVBatchControlRecord)localObject2).Batch_Number;
      return localObject1;
    }
    Object localObject1 = new TypeBatchControlRecord();
    Object localObject2 = (TypeBatchControlRecord)this.s5.getRecord();
    ((TypeBatchControlRecord)localObject1).Record_Type_Code = ((TypeBatchControlRecord)localObject2).Record_Type_Code;
    ((TypeBatchControlRecord)localObject1).Service_Class_Code = ((TypeBatchControlRecord)localObject2).Service_Class_Code;
    ((TypeBatchControlRecord)localObject1).Entry_Addenda_Count = ((TypeBatchControlRecord)localObject2).Entry_Addenda_Count;
    ((TypeBatchControlRecord)localObject1).Entry_Hash = ((TypeBatchControlRecord)localObject2).Entry_Hash;
    ((TypeBatchControlRecord)localObject1).Total_Debits = ((TypeBatchControlRecord)localObject2).Total_Debits;
    ((TypeBatchControlRecord)localObject1).Total_Credits = ((TypeBatchControlRecord)localObject2).Total_Credits;
    ((TypeBatchControlRecord)localObject1).Company_Identification = ((TypeBatchControlRecord)localObject2).Company_Identification;
    ((TypeBatchControlRecord)localObject1).Message_Authentication_Code = ((TypeBatchControlRecord)localObject2).Message_Authentication_Code;
    ((TypeBatchControlRecord)localObject1).Message_Authentication_CodeExists = ((TypeBatchControlRecord)localObject2).Message_Authentication_CodeExists;
    ((TypeBatchControlRecord)localObject1).Originating_DFI_Identification = ((TypeBatchControlRecord)localObject2).Originating_DFI_Identification;
    ((TypeBatchControlRecord)localObject1).Batch_Number = ((TypeBatchControlRecord)localObject2).Batch_Number;
    return localObject1;
  }
  
  public long getNonOffBatchDebitSum()
  {
    return this.sQ;
  }
  
  public void setNonOffBatchDebitSum(long paramLong)
  {
    this.sQ = paramLong;
  }
  
  public long getNonOffBatchCreditSum()
  {
    return this.sI;
  }
  
  public void setNonOffBatchCreditSum(long paramLong)
  {
    this.sI = paramLong;
  }
  
  public int getNonOffBatchEntryCount()
  {
    return this.sL;
  }
  
  public void setNonOffBatchEntryCount(int paramInt)
  {
    this.sL = paramInt;
  }
  
  public boolean hasAddenda()
    throws FFSException
  {
    boolean bool = false;
    if ((this.s1 != null) && (this.s1.length > 0))
    {
      int i = this.s1.length;
      for (int j = 0; j < i; j++) {
        if (this.s1[j].hasAddenda())
        {
          bool = true;
          break;
        }
      }
    }
    return bool;
  }
  
  public void setExtInfo(Hashtable paramHashtable)
  {
    this.sz = paramHashtable;
  }
  
  public Hashtable getExtInfo()
  {
    return this.sz;
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHBatchInfo
 * JD-Core Version:    0.7.0.1
 */