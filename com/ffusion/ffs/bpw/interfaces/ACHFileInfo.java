package com.ffusion.ffs.bpw.interfaces;

import com.ffusion.ffs.util.FFSConst;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeADVFileHeaderRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeFileControlRecord;
import com.ffusion.msgbroker.generated.MessageBroker.mdf.ACHMsgSet.TypeFileHeaderRecord;
import java.io.PrintStream;
import java.util.Hashtable;

public class ACHFileInfo
  extends BPWInfoBase
  implements FFSConst, ACHConsts
{
  protected String u0 = null;
  protected String uJ = null;
  protected String uS = null;
  protected ACHBatchInfo[] uI = null;
  protected ACHRecordInfo[] uT = null;
  protected ACHRecordInfo uK = null;
  protected ACHRecordInfo uN = null;
  protected String uZ = null;
  protected String uX = null;
  protected String uF = null;
  protected String uO = "MsgSet";
  protected String u1 = null;
  protected long uP = 0L;
  protected long uU = 0L;
  protected long uM = 0L;
  protected String uL = null;
  protected String uH = null;
  protected String uR = null;
  protected String u2 = null;
  protected Hashtable uV = null;
  protected String uQ = null;
  protected int uY = 0;
  protected int uG = 0;
  protected String uW = null;
  protected String uE = null;
  
  public ACHFileInfo()
  {
    this.statusCode = -1;
    this.statusMsg = null;
    this.submitDate = null;
    this.submittedBy = null;
    this.logId = null;
  }
  
  public long getCumulativeChunkSize()
  {
    int i = 0;
    if (this.uS.indexOf("\r\n") != -1) {
      i = 2;
    } else if (this.uS.indexOf("\r") != -1) {
      i = 1;
    } else if (this.uS.indexOf("\n") != -1) {
      i = 1;
    }
    return this.uU + this.uS.length() / (94 + i);
  }
  
  public String getRdfiId()
  {
    return this.uR;
  }
  
  public void setRdfiId(String paramString)
  {
    this.uR = paramString;
  }
  
  public String getFiId()
  {
    return this.u2;
  }
  
  public void setFiId(String paramString)
  {
    this.u2 = paramString;
  }
  
  public String getOdfiId()
  {
    return this.uH;
  }
  
  public void setOdfiId(String paramString)
  {
    this.uH = paramString;
  }
  
  public String getDtProcessed()
  {
    return this.uX;
  }
  
  public void setDtProcessed(String paramString)
  {
    this.uX = paramString;
  }
  
  public Hashtable getMemo()
  {
    return this.uV;
  }
  
  public void setMemo(Hashtable paramHashtable)
  {
    this.uV = paramHashtable;
  }
  
  public String getFileId()
  {
    return this.u0;
  }
  
  public void setFileId(String paramString)
  {
    this.u0 = paramString;
  }
  
  public String getChunkId()
  {
    return this.uJ;
  }
  
  public void setChunkId(String paramString)
  {
    this.uJ = paramString;
  }
  
  public String getFileContent()
  {
    return this.uS;
  }
  
  public void setFileContent(String paramString)
  {
    this.uS = paramString;
  }
  
  public ACHBatchInfo[] getBatches()
  {
    return this.uI;
  }
  
  public ACHBatchInfo getBatches(int paramInt)
  {
    if (this.uI == null) {
      return null;
    }
    if (paramInt < this.uI.length) {
      return this.uI[paramInt];
    }
    return null;
  }
  
  public void setBatches(ACHBatchInfo[] paramArrayOfACHBatchInfo)
  {
    this.uI = paramArrayOfACHBatchInfo;
  }
  
  public ACHRecordInfo[] getRecords()
  {
    return this.uT;
  }
  
  public ACHRecordInfo getRecordsAt(int paramInt)
  {
    if (this.uT == null) {
      return null;
    }
    if (paramInt < this.uT.length) {
      return this.uT[paramInt];
    }
    return null;
  }
  
  public void setRecords(ACHRecordInfo[] paramArrayOfACHRecordInfo)
  {
    this.uT = paramArrayOfACHRecordInfo;
  }
  
  public ACHRecordInfo getFileHeader()
  {
    return this.uK;
  }
  
  public void setFileHeader(ACHRecordInfo paramACHRecordInfo)
  {
    this.uK = paramACHRecordInfo;
  }
  
  public ACHRecordInfo getFileControl()
  {
    return this.uN;
  }
  
  public void setFileControl(ACHRecordInfo paramACHRecordInfo)
  {
    this.uN = paramACHRecordInfo;
  }
  
  public String getDueDate()
  {
    return this.uZ;
  }
  
  public void setDueDate(String paramString)
  {
    this.uZ = paramString;
  }
  
  public String getFileStatus()
  {
    return this.uF;
  }
  
  public void setFileStatus(String paramString)
  {
    this.uF = paramString;
  }
  
  public String getAchVersion()
  {
    return this.uO;
  }
  
  public void setAchVersion(String paramString)
  {
    this.uO = paramString;
  }
  
  public String getFileHeaderType()
  {
    return this.u1;
  }
  
  public void setFileHeaderType(String paramString)
  {
    this.u1 = paramString;
  }
  
  public long getFileSize()
  {
    return this.uP;
  }
  
  public void setFileSize(long paramLong)
  {
    this.uP = paramLong;
  }
  
  public long getFileCursor()
  {
    return this.uU;
  }
  
  public void setFileCursor(long paramLong)
  {
    this.uU = paramLong;
  }
  
  public long getFilePageSize()
  {
    return this.uM;
  }
  
  public void setFilePageSize(long paramLong)
  {
    this.uM = paramLong;
  }
  
  public String getCustomerId()
  {
    return this.uL;
  }
  
  public void setCustomerId(String paramString)
  {
    this.uL = paramString;
  }
  
  public void setTranId(String paramString)
  {
    this.uQ = paramString;
  }
  
  public String getTranId()
  {
    return this.uQ;
  }
  
  public int getNumberOfDebits()
  {
    return this.uG;
  }
  
  public void setNumberOfDebits(int paramInt)
  {
    this.uG = paramInt;
  }
  
  public int getNumberOfCredits()
  {
    return this.uY;
  }
  
  public void setNumberOfCredits(int paramInt)
  {
    this.uY = paramInt;
  }
  
  public String getExportFileName()
  {
    return this.uW;
  }
  
  public void setExportFileName(String paramString)
  {
    this.uW = paramString;
  }
  
  public String getUploadFileName()
  {
    return this.uE;
  }
  
  public void setUploadFileName(String paramString)
  {
    this.uE = paramString;
  }
  
  public String getFileHeaderFieldValueObject(int paramInt)
  {
    if ((this.uK == null) || (this.uK.getRecord() == null)) {
      return null;
    }
    if ((this.uK.getRecord() instanceof TypeFileHeaderRecord))
    {
      localObject = (TypeFileHeaderRecord)this.uK.getRecord();
      switch (paramInt)
      {
      case 1: 
        return ((TypeFileHeaderRecord)localObject).Immediate_Destination;
      case 2: 
        return ((TypeFileHeaderRecord)localObject).Immediate_Origin;
      case 3: 
        return ((TypeFileHeaderRecord)localObject).Reference_Code;
      case 4: 
        return ((TypeFileHeaderRecord)localObject).File_Identification_Modifier;
      case 5: 
        return ((TypeFileHeaderRecord)localObject).File_Creation_Date;
      case 6: 
        return ((TypeFileHeaderRecord)localObject).File_Creation_Time;
      case 7: 
        return "NONADV";
      }
      return null;
    }
    Object localObject = (TypeADVFileHeaderRecord)this.uK.getRecord();
    switch (paramInt)
    {
    case 1: 
      return ((TypeADVFileHeaderRecord)localObject).Immediate_Destination;
    case 2: 
      return ((TypeADVFileHeaderRecord)localObject).Immediate_Origin;
    case 3: 
      return ((TypeADVFileHeaderRecord)localObject).Reference_Code;
    case 4: 
      return ((TypeADVFileHeaderRecord)localObject).File_Identification_Modifier;
    case 5: 
      return ((TypeADVFileHeaderRecord)localObject).File_Creation_Date;
    case 6: 
      return ((TypeADVFileHeaderRecord)localObject).File_Creation_Time;
    case 7: 
      return "ADV";
    }
    return null;
  }
  
  public Object getFileControlFieldValueObject(int paramInt)
  {
    if ((this.uN == null) || (this.uN.getRecord() == null)) {
      return null;
    }
    TypeFileControlRecord localTypeFileControlRecord = (TypeFileControlRecord)this.uN.getRecord();
    switch (paramInt)
    {
    case 9: 
      return new Integer(localTypeFileControlRecord.Batch_Count);
    case 8: 
      return new Integer(localTypeFileControlRecord.Block_Count);
    case 10: 
      return new Integer(localTypeFileControlRecord.FileEntry_Addenda_Count);
    case 22: 
      return new Long(localTypeFileControlRecord.Total_Debits);
    case 23: 
      return new Long(localTypeFileControlRecord.Total_Credits);
    }
    return null;
  }
  
  public void setFileHeaderFieldValueObject(int paramInt, String paramString)
  {
    if (this.u1 == null)
    {
      System.out.println("#### ACHFileInfo.setFileHeaderFieldValueObject: fileHeaderType is null.");
      return;
    }
    if (this.uK == null)
    {
      System.out.println("####ACHFileInfo.setFileHeaderFieldValueObject: fileHeader is null.");
      return;
    }
    if (paramString == null) {
      System.out.println("#### ACHFileInfo.setFileHeaderFieldValueObject: fieldVal parameter is null.");
    }
    Object localObject;
    if (this.u1.equals("NONADV"))
    {
      localObject = (TypeFileHeaderRecord)this.uK.getRecord();
      switch (paramInt)
      {
      case 1: 
        ((TypeFileHeaderRecord)localObject).Immediate_Destination = paramString;
        break;
      case 2: 
        ((TypeFileHeaderRecord)localObject).Immediate_Origin = paramString;
        break;
      case 3: 
        ((TypeFileHeaderRecord)localObject).Reference_Code = paramString;
        ((TypeFileHeaderRecord)localObject).Reference_CodeExists = true;
        break;
      case 4: 
        ((TypeFileHeaderRecord)localObject).File_Identification_Modifier = paramString;
        break;
      case 5: 
        ((TypeFileHeaderRecord)localObject).File_Creation_Date = paramString;
        break;
      case 6: 
        ((TypeFileHeaderRecord)localObject).File_Creation_Time = paramString;
        ((TypeFileHeaderRecord)localObject).File_Creation_TimeExists = true;
        break;
      }
    }
    else
    {
      localObject = (TypeADVFileHeaderRecord)this.uK.getRecord();
      switch (paramInt)
      {
      case 1: 
        ((TypeADVFileHeaderRecord)localObject).Immediate_Destination = paramString;
        break;
      case 2: 
        ((TypeADVFileHeaderRecord)localObject).Immediate_Origin = paramString;
        break;
      case 3: 
        ((TypeADVFileHeaderRecord)localObject).Reference_Code = paramString;
        ((TypeADVFileHeaderRecord)localObject).Reference_CodeExists = true;
        break;
      case 4: 
        ((TypeADVFileHeaderRecord)localObject).File_Identification_Modifier = paramString;
        break;
      case 5: 
        ((TypeADVFileHeaderRecord)localObject).File_Creation_Date = paramString;
        break;
      case 6: 
        ((TypeADVFileHeaderRecord)localObject).File_Creation_Time = paramString;
        ((TypeADVFileHeaderRecord)localObject).File_Creation_TimeExists = true;
        break;
      }
    }
  }
  
  public void setFileControlFieldValueObject(int paramInt, String paramString)
  {
    if (this.u1 == null)
    {
      System.out.println("#### ACHFileInfo.setFileControlFieldValueObject: fileHeaderType is null.");
      return;
    }
    if (paramString == null)
    {
      System.out.println("#### ACHFileInfo.setFileControlFieldValueObject: fieldVal parameter is null.");
      return;
    }
    if (this.uN == null)
    {
      System.out.println("####ACHFileInfo.setFileControlFieldValueObject: fileControl is null.");
      return;
    }
    TypeFileControlRecord localTypeFileControlRecord = (TypeFileControlRecord)this.uN.getRecord();
    switch (paramInt)
    {
    case 9: 
      localTypeFileControlRecord.Batch_Count = Integer.parseInt(paramString);
      break;
    case 8: 
      localTypeFileControlRecord.Block_Count = Integer.parseInt(paramString);
      break;
    case 10: 
      localTypeFileControlRecord.FileEntry_Addenda_Count = Integer.parseInt(paramString);
      break;
    case 22: 
      localTypeFileControlRecord.Total_Debits = Long.parseLong(paramString);
      break;
    case 23: 
      localTypeFileControlRecord.Total_Credits = Long.parseLong(paramString);
      break;
    }
  }
}


/* Location:           D:\drops\jd\jars\bpwcore.jar
 * Qualified Name:     com.ffusion.ffs.bpw.interfaces.ACHFileInfo
 * JD-Core Version:    0.7.0.1
 */