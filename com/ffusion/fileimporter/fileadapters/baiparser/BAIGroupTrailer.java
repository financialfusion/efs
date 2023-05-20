package com.ffusion.fileimporter.fileadapters.baiparser;

import com.ffusion.csil.FIException;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.HashMap;

public class BAIGroupTrailer
  implements BAIParserConsts
{
  public static final int NUMBER_FIELDS = 4;
  public BigDecimal _groupControlTotal = null;
  public int _numberOfAccounts = -1;
  public int _numberOfRecords = -1;
  public int _lineNumber;
  
  public BAIGroupTrailer(BAIFileHeader paramBAIFileHeader, BAIGroupHeader paramBAIGroupHeader, String[] paramArrayOfString, int paramInt)
    throws FIException
  {
    this(-1, paramBAIFileHeader, paramBAIGroupHeader, paramArrayOfString, paramInt, null);
  }
  
  public BAIGroupTrailer(int paramInt1, BAIFileHeader paramBAIFileHeader, BAIGroupHeader paramBAIGroupHeader, String[] paramArrayOfString, int paramInt2, HashMap paramHashMap)
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
      ((StringBuffer)localObject1).append(": Invalid group trailer record. No file header record in the BAI file.");
      localObject2 = new FIException("com.ffusion.fileimporter.BAIParser.GroupTrailer", 9712, ((StringBuffer)localObject1).toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.GroupTrailer", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if (paramBAIGroupHeader == null)
    {
      localObject1 = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        ((StringBuffer)localObject1).append(paramInt1);
      } else {
        ((StringBuffer)localObject1).append("number unknown");
      }
      ((StringBuffer)localObject1).append(": Invalid group trailer record. No matching group header record.");
      localObject2 = new FIException("com.ffusion.fileimporter.BAIParser.GroupTrailer", 9712, ((StringBuffer)localObject1).toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.GroupTrailer", (Throwable)localObject2);
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
      ((StringBuffer)localObject1).append(": Invalid group trailer record. Expected ").append(4).append(" records. ");
      ((StringBuffer)localObject1).append("Found ").append(paramArrayOfString.length).append(".");
      localObject2 = new FIException("com.ffusion.fileimporter.BAIParser.GroupTrailer", 9712, ((StringBuffer)localObject1).toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.GroupTrailer", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    if ((!paramArrayOfString[0].equals("98")) || (paramArrayOfString[1].length() == 0) || (paramArrayOfString[2].length() == 0) || (paramArrayOfString[3].length() == 0))
    {
      localObject1 = new FIException("com.ffusion.fileimporter.BAIParser.GroupTrailer", 9712, "Error Parsing BAI Group Trailer. Invalid BAI record ID '" + paramArrayOfString[0] + "'. Exptected '49'.");
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.GroupTrailer", (Throwable)localObject1);
      throw ((Throwable)localObject1);
    }
    try
    {
      this._groupControlTotal = BAIParserUtil.convertStrToAmount(paramArrayOfString[1], 0);
      this._numberOfAccounts = BAIParserUtil.parseInt(paramArrayOfString[2]);
      this._numberOfRecords = BAIParserUtil.parseInt(paramArrayOfString[3]);
    }
    catch (Exception localException)
    {
      localObject2 = new FIException("com.ffusion.fileimporter.BAIParser.GroupTrailer", 9712, "Error Parsing BAI Group Trailer", localException);
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.GroupTrailer", (Throwable)localObject2);
      throw ((Throwable)localObject2);
    }
    paramBAIGroupHeader._numberOfRecords += paramInt2;
    StringBuffer localStringBuffer;
    FIException localFIException;
    if (this._numberOfRecords != paramBAIGroupHeader._numberOfRecords)
    {
      localStringBuffer = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        localStringBuffer.append(paramInt1);
      } else {
        localStringBuffer.append("number unknown");
      }
      localStringBuffer.append(": Record count mismatch for bank with ABA# ");
      localStringBuffer.append(paramBAIGroupHeader._ultimateReceiverID);
      localStringBuffer.append(". The total number of records in the group section is ").append(this._numberOfRecords);
      localStringBuffer.append(" whereas the reported number of records is ").append(paramBAIGroupHeader._numberOfRecords);
      localStringBuffer.append(".");
      localObject2 = localStringBuffer.toString();
      localFIException = new FIException("Constructing " + getClass().getName(), 9719, (String)localObject2);
      DebugLog.throwing("Constructing " + getClass().getName(), localFIException);
      throw localFIException;
    }
    if (!this._groupControlTotal.equals(paramBAIGroupHeader._runningControlTotal))
    {
      localStringBuffer = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        localStringBuffer.append(paramInt1);
      } else {
        localStringBuffer.append("number unknown");
      }
      localStringBuffer.append(": Control total mismatch for bank with ABA# ");
      localStringBuffer.append(paramBAIGroupHeader._ultimateReceiverID);
      localStringBuffer.append(". The calculated total is ").append(this._groupControlTotal);
      localStringBuffer.append(" whereas the reported value is ").append(paramBAIGroupHeader._runningControlTotal);
      localStringBuffer.append(".");
      localObject2 = localStringBuffer.toString();
      localFIException = new FIException("Constructing " + getClass().getName(), 9719, (String)localObject2);
      DebugLog.throwing("Constructing " + getClass().getName(), localFIException);
      throw localFIException;
    }
    paramBAIFileHeader.addToControlTotal(this._groupControlTotal, this._numberOfRecords, this._numberOfAccounts);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAIGroupTrailer
 * JD-Core Version:    0.7.0.1
 */