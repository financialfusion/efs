package com.ffusion.fileimporter.fileadapters.baiparser;

import com.ffusion.csil.FIException;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;

public class BAIFileHeader
  implements BAIParserConsts
{
  public static final int NUMBER_FIELDS = 9;
  public String _senderIdentification = null;
  public String _receiverIdentification = null;
  public Calendar _fileCreationDate = null;
  public String _fileIdentificationNumber;
  public int _physicalRecordLength = -1;
  public int _blockSize = -1;
  public int _versionNumber = -1;
  public BigDecimal _runningControlTotal = new BigDecimal(0.0D);
  public int _numberOfRecords = 0;
  public int _numberOfGroups = 0;
  public int _lineNumber;
  public int _numberOfAccounts = 0;
  
  public BAIFileHeader(String[] paramArrayOfString, int paramInt)
    throws FIException
  {
    if ((paramArrayOfString.length != 9) || (!paramArrayOfString[0].equals("01")) || (paramArrayOfString[1].length() == 0) || (paramArrayOfString[2].length() == 0) || (paramArrayOfString[3].length() == 0) || (paramArrayOfString[4].length() == 0) || (paramArrayOfString[5].length() == 0) || (paramArrayOfString[8].length() == 0))
    {
      FIException localFIException1 = new FIException("com.ffusion.fileimporter.BAIParser.BAIFileHeader", 9704, "Error Parsing BAI File Header");
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIFileHeader", localFIException1);
      throw localFIException1;
    }
    this._numberOfRecords += paramInt;
    try
    {
      this._senderIdentification = paramArrayOfString[1];
      this._receiverIdentification = paramArrayOfString[2];
      this._fileCreationDate = BAIParserUtil.parseDateTime(this._lineNumber, paramArrayOfString[3], paramArrayOfString[4]);
      int i = BAIParserUtil.parseInt(paramArrayOfString[5]);
      this._fileIdentificationNumber = paramArrayOfString[5];
      if (paramArrayOfString[6].length() > 0) {
        this._physicalRecordLength = BAIParserUtil.parseInt(paramArrayOfString[6]);
      }
      if (paramArrayOfString[7].length() > 0) {
        this._blockSize = BAIParserUtil.parseInt(paramArrayOfString[7]);
      }
      this._versionNumber = BAIParserUtil.parseInt(paramArrayOfString[8]);
    }
    catch (Exception localException)
    {
      FIException localFIException2 = new FIException("com.ffusion.fileimporter.BAIParser.BAIFileHeader", 9704, "Error Parsing BAI File Header", localException);
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIFileHeader", localFIException2);
      throw localFIException2;
    }
  }
  
  public BAIFileHeader(int paramInt1, String[] paramArrayOfString, int paramInt2, HashMap paramHashMap)
    throws FIException
  {
    this._lineNumber = paramInt1;
    Object localObject;
    FIException localFIException;
    if (paramArrayOfString.length != 9)
    {
      localObject = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        ((StringBuffer)localObject).append(paramInt1);
      } else {
        ((StringBuffer)localObject).append("number unknown");
      }
      ((StringBuffer)localObject).append(": Invalid group header record. Expected ").append(9).append(" records. ");
      ((StringBuffer)localObject).append("Found ").append(paramArrayOfString.length).append(".");
      localFIException = new FIException("com.ffusion.fileimporter.BAIParser.BAIFileHeader", 9704, ((StringBuffer)localObject).toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIFileHeader", localFIException);
      throw localFIException;
    }
    if ((!paramArrayOfString[0].equals("01")) || (paramArrayOfString[1].length() == 0) || (paramArrayOfString[2].length() == 0) || (paramArrayOfString[3].length() == 0) || (paramArrayOfString[4].length() == 0) || (paramArrayOfString[5].length() == 0) || (paramArrayOfString[8].length() == 0))
    {
      localObject = new FIException("com.ffusion.fileimporter.BAIParser.BAIFileHeader", 9704, "Error Parsing BAI File Header");
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIFileHeader", (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    this._numberOfRecords += paramInt2;
    try
    {
      this._senderIdentification = paramArrayOfString[1];
      this._receiverIdentification = paramArrayOfString[2];
      this._fileCreationDate = BAIParserUtil.parseDateTime(paramInt1, paramArrayOfString[3], paramArrayOfString[4]);
      int i = BAIParserUtil.parseInt(paramArrayOfString[5]);
      this._fileIdentificationNumber = paramArrayOfString[5];
      if (paramArrayOfString[6].length() > 0) {
        this._physicalRecordLength = BAIParserUtil.parseInt(paramArrayOfString[6]);
      }
      if (paramArrayOfString[7].length() > 0) {
        this._blockSize = BAIParserUtil.parseInt(paramArrayOfString[7]);
      }
      this._versionNumber = BAIParserUtil.parseInt(paramArrayOfString[8]);
    }
    catch (Exception localException)
    {
      localFIException = new FIException("com.ffusion.fileimporter.BAIParser.BAIFileHeader", 9704, "Error Parsing BAI File Header", localException);
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIFileHeader", localFIException);
      throw localFIException;
    }
  }
  
  public void addToControlTotal(BigDecimal paramBigDecimal, int paramInt1, int paramInt2)
  {
    if (paramBigDecimal != null) {
      this._runningControlTotal = this._runningControlTotal.add(paramBigDecimal);
    }
    this._numberOfRecords += paramInt1;
    this._numberOfAccounts += paramInt2;
    this._numberOfGroups += 1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAIFileHeader
 * JD-Core Version:    0.7.0.1
 */