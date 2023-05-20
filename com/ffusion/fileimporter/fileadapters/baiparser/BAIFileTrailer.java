package com.ffusion.fileimporter.fileadapters.baiparser;

import com.ffusion.csil.FIException;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.HashMap;

public class BAIFileTrailer
  implements BAIParserConsts
{
  public static final int NUMBER_FIELDS = 4;
  public BigDecimal _fileControlTotal = null;
  public int _numberOfGroups = -1;
  public int _numberOfRecords = -1;
  public int _lineNumber;
  
  public BAIFileTrailer(BAIFileHeader paramBAIFileHeader, String[] paramArrayOfString, int paramInt)
    throws FIException
  {
    this(-1, paramBAIFileHeader, paramArrayOfString, paramInt, null);
  }
  
  public BAIFileTrailer(int paramInt1, BAIFileHeader paramBAIFileHeader, String[] paramArrayOfString, int paramInt2, HashMap paramHashMap)
    throws FIException
  {
    this._lineNumber = paramInt1;
    Object localObject1;
    Object localObject2;
    if (paramBAIFileHeader == null)
    {
      localObject1 = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        ((StringBuffer)localObject1).append(paramInt1);
      } else {
        ((StringBuffer)localObject1).append("number unknown");
      }
      ((StringBuffer)localObject1).append(": Invalid file trailer record. No matching file header record.");
      localObject2 = new FIException("com.ffusion.fileimporter.BAIParser.FileTrailer", 9713, ((StringBuffer)localObject1).toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.FileTrailer", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if (paramArrayOfString.length != 4)
    {
      localObject1 = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        ((StringBuffer)localObject1).append(paramInt1);
      } else {
        ((StringBuffer)localObject1).append("number unknown");
      }
      ((StringBuffer)localObject1).append(": Invalid file trailer record. Expected ").append(4).append(" records. ");
      ((StringBuffer)localObject1).append("Found ").append(paramArrayOfString.length).append(".");
      localObject2 = new FIException("com.ffusion.fileimporter.BAIParser.FileTrailer", 9713, ((StringBuffer)localObject1).toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.FileTrailer", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if ((!paramArrayOfString[0].equals("99")) || (paramArrayOfString[1].length() == 0) || (paramArrayOfString[2].length() == 0) || (paramArrayOfString[3].length() == 0))
    {
      localObject1 = new FIException("com.ffusion.fileimporter.BAIParser.FileTrailer", 9713, "Error Parsing BAI File Trailer");
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.FileTrailer", (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
    try
    {
      this._fileControlTotal = BAIParserUtil.convertStrToAmount(paramArrayOfString[1], 0);
      this._numberOfGroups = BAIParserUtil.parseInt(paramArrayOfString[2]);
      this._numberOfRecords = BAIParserUtil.parseInt(paramArrayOfString[3]);
    }
    catch (Exception localException)
    {
      localObject2 = new FIException("com.ffusion.fileimporter.BAIParser.FileTrailer", 9713, "Error Parsing BAI File Trailer", localException);
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.FileTrailer", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    paramBAIFileHeader._numberOfRecords += paramInt2;
    StringBuffer localStringBuffer;
    FIException localFIException;
    if (this._numberOfRecords != paramBAIFileHeader._numberOfRecords)
    {
      localStringBuffer = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        localStringBuffer.append(paramInt1);
      } else {
        localStringBuffer.append("number unknown");
      }
      localStringBuffer.append(": Record count mismatch for the file");
      localStringBuffer.append(". The total number of records in the group section is ").append(this._numberOfRecords);
      localStringBuffer.append(" whereas the reported number of records is ").append(paramBAIFileHeader._numberOfRecords);
      localStringBuffer.append(".");
      localObject2 = localStringBuffer.toString();
      localFIException = new FIException("Constructing " + getClass().getName(), 9721, (String)localObject2);
      DebugLog.throwing(getClass().getName(), localFIException);
      throw localFIException;
    }
    if (!this._fileControlTotal.equals(paramBAIFileHeader._runningControlTotal))
    {
      localStringBuffer = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        localStringBuffer.append(paramInt1);
      } else {
        localStringBuffer.append("number unknown");
      }
      localStringBuffer.append(": Control total mismatch for the file");
      localStringBuffer.append(". The calculated total is ").append(paramBAIFileHeader._runningControlTotal);
      localStringBuffer.append(" whereas the reported value is ").append(this._fileControlTotal);
      localStringBuffer.append(".");
      localObject2 = localStringBuffer.toString();
      localFIException = new FIException("Constructing " + getClass().getName(), 9721, (String)localObject2);
      DebugLog.throwing(getClass().getName(), localFIException);
      throw localFIException;
    }
    if (this._numberOfGroups != paramBAIFileHeader._numberOfGroups)
    {
      localStringBuffer = new StringBuffer("Line ");
      localStringBuffer.append(this._lineNumber);
      localStringBuffer.append(": Control total mismatch for the file");
      localStringBuffer.append(". The number of groups is ").append(this._numberOfGroups);
      localStringBuffer.append(" whereas the reported value is ").append(paramBAIFileHeader._numberOfGroups);
      localStringBuffer.append(".");
      localObject2 = localStringBuffer.toString();
      localFIException = new FIException("Constructing " + getClass().getName(), 9721, (String)localObject2);
      DebugLog.throwing(getClass().getName(), localFIException);
      throw localFIException;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAIFileTrailer
 * JD-Core Version:    0.7.0.1
 */