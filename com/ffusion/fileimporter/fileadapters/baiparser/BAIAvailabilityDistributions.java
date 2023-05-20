package com.ffusion.fileimporter.fileadapters.baiparser;

import com.ffusion.csil.FIException;
import com.ffusion.util.logging.DebugLog;
import java.math.BigDecimal;

public class BAIAvailabilityDistributions
  implements BAIParserConsts
{
  public int _availabilityInDays = -1;
  public BigDecimal _availableAmount = null;
  
  public BAIAvailabilityDistributions(BAIAccountIdentifier paramBAIAccountIdentifier, String paramString1, String paramString2)
    throws FIException
  {
    if ((paramString1.length() <= 0) && (paramString2.length() <= 0))
    {
      FIException localFIException1 = new FIException("com.ffusion.fileimporter.BAIParser.BAIAvailabilityDistributions", 9708, "Error Parsing BAI Availability Distribution");
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIAvailabilityDistributions", localFIException1);
      throw localFIException1;
    }
    try
    {
      this._availableAmount = BAIParserUtil.convertStrToAmount(paramString2, paramBAIAccountIdentifier._numberOfDecimals);
      this._availabilityInDays = BAIParserUtil.parseInt(paramString1);
    }
    catch (Exception localException)
    {
      FIException localFIException2 = new FIException("com.ffusion.fileimporter.BAIParser.BAIAvailabilityDistributions", 9708, "Error Parsing BAI Availability Distribution", localException);
      DebugLog.throwing("com.ffusion.fileimporter.BAIParser.BAIAvailabilityDistributions", localFIException2);
      throw localFIException2;
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.fileimporter.fileadapters.baiparser.BAIAvailabilityDistributions
 * JD-Core Version:    0.7.0.1
 */