package com.ffusion.fileimporter.fileadapters.baiparser;

import com.ffusion.beans.util.AccountUtil;
import com.ffusion.beans.util.UtilException;
import com.ffusion.csil.FIException;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;

public class BAIAccountTrailer
  implements BAIParserConsts
{
  public static final int NUMBER_FIELDS = 3;
  public BigDecimal _amountControlTotal = null;
  public int _numberOfRecords = -1;
  public int _lineNumber = 0;
  
  public BAIAccountTrailer(BAIGroupHeader paramBAIGroupHeader, BAIAccountIdentifier paramBAIAccountIdentifier, String[] paramArrayOfString, int paramInt)
    throws FIException
  {
    this(-1, paramBAIGroupHeader, paramBAIAccountIdentifier, paramArrayOfString, paramInt, null);
  }
  
  public BAIAccountTrailer(int paramInt1, BAIGroupHeader paramBAIGroupHeader, BAIAccountIdentifier paramBAIAccountIdentifier, String[] paramArrayOfString, int paramInt2, HashMap paramHashMap)
    throws FIException
  {
    this._lineNumber = paramInt1;
    Object localObject;
    FIException localFIException1;
    if (paramBAIAccountIdentifier == null)
    {
      localObject = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        ((StringBuffer)localObject).append(paramInt1);
      } else {
        ((StringBuffer)localObject).append("number unknown");
      }
      ((StringBuffer)localObject).append(": Invalid account trailer record. No matching account header record.");
      localFIException1 = new FIException("com.ffusion.fileimporter.BAIParser.AccountTrailer", 9711, ((StringBuffer)localObject).toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.AccountTrailer", localFIException1);
      throw localFIException1;
    }
    if (paramArrayOfString.length != 3)
    {
      localObject = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        ((StringBuffer)localObject).append(paramInt1);
      } else {
        ((StringBuffer)localObject).append("number unknown");
      }
      ((StringBuffer)localObject).append(": Invalid account trailer record. Expected ").append(3).append(" records. ");
      ((StringBuffer)localObject).append("Found ").append(paramArrayOfString.length).append(".");
      localFIException1 = new FIException("com.ffusion.fileimporter.BAIParser.AccountTrailer", 9711, ((StringBuffer)localObject).toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.AccountTrailer", localFIException1);
      throw localFIException1;
    }
    if ((!paramArrayOfString[0].equals("49")) || (paramArrayOfString[1].length() == 0) || (paramArrayOfString[2].length() == 0))
    {
      localObject = new FIException("com.ffusion.fileimporter.BAIParser.AccountTrailer", 9711, "Error Parsing BAI Account Trailer");
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.AccountTrailer", (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    try
    {
      this._amountControlTotal = BAIParserUtil.convertStrToAmount(paramArrayOfString[1], 0);
      this._numberOfRecords = BAIParserUtil.parseInt(paramArrayOfString[2]);
    }
    catch (Exception localException)
    {
      localFIException1 = new FIException("com.ffusion.fileimporter.BAIParser.AccountTrailer", 9711, "Error Parsing BAI Account Trailer", localException);
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.AccountTrailer", localFIException1);
      throw localFIException1;
    }
    paramBAIAccountIdentifier._numberOfRecords += paramInt2;
    StringBuffer localStringBuffer;
    if (paramBAIAccountIdentifier._numberOfRecords != this._numberOfRecords)
    {
      localStringBuffer = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        localStringBuffer.append(paramInt1);
      } else {
        localStringBuffer.append("number unknown");
      }
      localStringBuffer.append(": Record count total mismatch for account '");
      localStringBuffer.append(paramBAIGroupHeader._originatorID).append(":").append(paramBAIAccountIdentifier._customerAccountNumber);
      localStringBuffer.append("'. The total number of record is ").append(this._numberOfRecords);
      localStringBuffer.append(" whereas the number of reported records is ").append(paramBAIAccountIdentifier._numberOfRecords);
      localStringBuffer.append(".");
      localFIException1 = new FIException("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", 9719, localStringBuffer.toString());
      DebugLog.throwing("Constructing " + getClass().getName(), localFIException1);
      throw localFIException1;
    }
    if (!paramBAIAccountIdentifier._runningAmountTotal.equals(this._amountControlTotal))
    {
      localStringBuffer = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        localStringBuffer.append(this._lineNumber);
      } else {
        localStringBuffer.append("number unknown");
      }
      localStringBuffer.append(": Control total mismatch for account with ID '");
      try
      {
        localStringBuffer.append(AccountUtil.buildAccountDisplayText(paramBAIAccountIdentifier._customerAccountNumber, Locale.getDefault()));
      }
      catch (UtilException localUtilException)
      {
        DebugLog.log(Level.FINE, "Unable to build account display text for " + paramBAIAccountIdentifier._customerAccountNumber);
        localStringBuffer.append(paramBAIAccountIdentifier._customerAccountNumber);
      }
      localStringBuffer.append("'. The total amount is ").append(this._amountControlTotal);
      localStringBuffer.append(" whereas the total reported value is ").append(paramBAIAccountIdentifier._runningAmountTotal);
      localStringBuffer.append(".");
      FIException localFIException2 = new FIException("com.ffusion.fileimporter.BAIAdapter.processBAIRecord", 9719, localStringBuffer.toString());
      DebugLog.throwing("Constructing " + getClass().getName(), localFIException2);
      throw localFIException2;
    }
    paramBAIGroupHeader.addToControlTotal(this._amountControlTotal, this._numberOfRecords);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAIAccountTrailer
 * JD-Core Version:    0.7.0.1
 */