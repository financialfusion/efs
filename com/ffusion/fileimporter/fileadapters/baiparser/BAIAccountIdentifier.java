package com.ffusion.fileimporter.fileadapters.baiparser;

import com.ffusion.csil.FIException;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Vector;

public class BAIAccountIdentifier
  implements BAIParserConsts
{
  public static final int NUMBER_FIELDS = 7;
  public String _customerAccountNumber = null;
  public String _currencyCode = "USD";
  public int _numberOfDecimals = 2;
  public Vector _baiSummaryStatus = null;
  public BigDecimal _runningAmountTotal = new BigDecimal(0.0D);
  public int _numberOfRecords = 0;
  public int _lineNumber = 0;
  
  public BAIAccountIdentifier(BAIGroupHeader paramBAIGroupHeader, String[] paramArrayOfString, int paramInt)
    throws FIException
  {
    this(-1, paramBAIGroupHeader, paramArrayOfString, paramInt, null);
  }
  
  public BAIAccountIdentifier(int paramInt1, BAIGroupHeader paramBAIGroupHeader, String[] paramArrayOfString, int paramInt2, HashMap paramHashMap)
    throws FIException
  {
    Object localObject;
    FIException localFIException2;
    if (paramBAIGroupHeader == null)
    {
      localObject = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        ((StringBuffer)localObject).append(paramInt1);
      } else {
        ((StringBuffer)localObject).append("number unknown");
      }
      ((StringBuffer)localObject).append(": Invalid account header record. No matching group header record.");
      localFIException2 = new FIException("com.ffusion.fileimporter.BAIParser.BAIAccountIdentifier", 9710, ((StringBuffer)localObject).toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIAccountIdentifier", localFIException2);
      throw localFIException2;
    }
    this._lineNumber = paramInt1;
    if (paramArrayOfString.length < 7)
    {
      localObject = new StringBuffer("Line ");
      if (paramInt1 > 0) {
        ((StringBuffer)localObject).append(paramInt1);
      } else {
        ((StringBuffer)localObject).append("number unknown");
      }
      ((StringBuffer)localObject).append(": Invalid account header record. Expected ").append(7).append(" records. ");
      ((StringBuffer)localObject).append("Found ").append(paramArrayOfString.length).append(".");
      localFIException2 = new FIException("com.ffusion.fileimporter.BAIParser.BAIAccountIdentifier", 9710, ((StringBuffer)localObject).toString());
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIAccountIdentifier", localFIException2);
      throw localFIException2;
    }
    if ((!paramArrayOfString[0].equals("03")) || (paramArrayOfString[1].length() == 0))
    {
      localObject = new FIException("com.ffusion.fileimporter.BAIParser.BAIAccountIdentifier", 9710, "Error Parsing BAI account header: incorrect BAI code '" + paramArrayOfString[0] + "', expected '03'");
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIAccountIdentifier", (Throwable)localObject);
      throw ((Throwable)localObject);
    }
    this._numberOfRecords += paramInt2;
    this._currencyCode = paramBAIGroupHeader._currencyCode;
    this._numberOfDecimals = paramBAIGroupHeader._numberOfDecimals;
    this._baiSummaryStatus = new Vector();
    try
    {
      this._customerAccountNumber = paramArrayOfString[1];
      if (paramArrayOfString[2].length() > 0)
      {
        this._currencyCode = paramArrayOfString[2];
        this._numberOfDecimals = BAIParserUtil.getCurrencyPrecision(this._currencyCode);
      }
      localObject = null;
      int i = 3;
      while (i < paramArrayOfString.length)
      {
        localObject = new BAISummaryStatus();
        i = ((BAISummaryStatus)localObject).createBAISummaryStatus(this, paramArrayOfString, i);
        if (!((BAISummaryStatus)localObject)._isEmptySummary) {
          this._baiSummaryStatus.add(localObject);
        }
      }
    }
    catch (FIException localFIException1)
    {
      throw localFIException1;
    }
    catch (Exception localException)
    {
      FIException localFIException3 = new FIException("com.ffusion.fileimporter.BAIParser.BAIAccountIdentifier", 9710, "Error Parsing BAI Account Identifier", localException);
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIAccountIdentifier", localFIException3);
      throw localFIException3;
    }
  }
  
  public void addAmountToRunningTotal(String paramString, int paramInt)
  {
    if (paramString != null)
    {
      BigDecimal localBigDecimal = BAIParserUtil.convertStrToAmount(paramString, 0);
      this._runningAmountTotal = this._runningAmountTotal.add(localBigDecimal);
    }
    this._numberOfRecords += paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAIAccountIdentifier
 * JD-Core Version:    0.7.0.1
 */