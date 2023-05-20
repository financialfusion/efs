package com.ffusion.fileimporter.fileadapters.baiparser;

import com.ffusion.csil.FIException;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;

public class BAIGroupHeader
  implements BAIParserConsts
{
  public static final int NUMBER_FIELDS = 8;
  public static final int INTERIM_PREVIOUS_DAY_DATA = 1;
  public static final int FINAL_PREVIOUS_DAY_DATA = 2;
  public static final int INTERIM_SAME_DAY_DATA = 3;
  public static final int FINAL_SAME_DAY_DATA = 4;
  public String _ultimateReceiverID = null;
  public String _originatorID = null;
  public int _groupStatus = -1;
  public Calendar _asOfDate = null;
  public String _currencyCode = "USD";
  public int _numberOfDecimals = 2;
  public int _asOfDateModifier = -1;
  public BigDecimal _runningControlTotal = new BigDecimal(0.0D);
  public int _numberOfRecords = 0;
  public int _numberOfAccounts = 0;
  public int _lineNumber;
  
  public BAIGroupHeader(String[] paramArrayOfString, int paramInt)
    throws FIException
  {
    this(-1, paramArrayOfString, paramInt, null);
  }
  
  public BAIGroupHeader(int paramInt1, String[] paramArrayOfString, int paramInt2, HashMap paramHashMap)
    throws FIException
  {
    this._lineNumber = paramInt1;
    Object localObject;
    FIException localFIException;
    if (paramArrayOfString.length != 8)
    {
      localObject = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        ((StringBuffer)localObject).append(paramInt1);
      } else {
        ((StringBuffer)localObject).append("number unknown");
      }
      ((StringBuffer)localObject).append(": Invalid group header record. Expected ").append(8).append(" records. ");
      ((StringBuffer)localObject).append("Found ").append(paramArrayOfString.length).append(".");
      localFIException = new FIException("com.ffusion.fileimporter.BAIParser.BAIGroupHeader", 9706, ((StringBuffer)localObject).toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIFileHeader", localFIException);
      throw localFIException;
    }
    if ((!paramArrayOfString[0].equals("02")) || (paramArrayOfString[2].length() == 0) || (paramArrayOfString[3].length() == 0) || (paramArrayOfString[4].length() == 0))
    {
      localObject = new FIException("com.ffusion.fileimporter.BAIParser.BAIGroupHeader", 9706, "Error Parsing BAI File Header");
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIFileHeader", (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    this._numberOfRecords += paramInt2;
    try
    {
      if (paramArrayOfString[1].length() > 0) {
        this._ultimateReceiverID = paramArrayOfString[1];
      }
      this._originatorID = paramArrayOfString[2];
      this._groupStatus = BAIParserUtil.parseInt(paramArrayOfString[3]);
      this._asOfDate = BAIParserUtil.parseDateTime(paramInt1, paramArrayOfString[4], paramArrayOfString[5]);
      if (paramArrayOfString[6].length() > 0)
      {
        this._currencyCode = paramArrayOfString[6];
        this._numberOfDecimals = BAIParserUtil.getCurrencyPrecision(this._currencyCode);
      }
      if (paramArrayOfString[7].length() > 0) {
        this._asOfDateModifier = BAIParserUtil.parseInt(paramArrayOfString[7]);
      }
    }
    catch (Exception localException)
    {
      localFIException = new FIException("com.ffusion.fileimporter.BAIParser.BAIGroupHeader", 9706, "Error Parsing BAI Group Header", localException);
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIGroupHeader", localFIException);
      throw localFIException;
    }
  }
  
  public void addToControlTotal(BigDecimal paramBigDecimal, int paramInt)
  {
    if (paramBigDecimal != null) {
      this._runningControlTotal = this._runningControlTotal.add(paramBigDecimal);
    }
    this._numberOfRecords += paramInt;
    this._numberOfAccounts += 1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAIGroupHeader
 * JD-Core Version:    0.7.0.1
 */