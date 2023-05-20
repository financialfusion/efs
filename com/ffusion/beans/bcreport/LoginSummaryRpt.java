package com.ffusion.beans.bcreport;

import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.LocaleableBean;
import java.util.HashMap;
import java.util.Iterator;

public class LoginSummaryRpt
  extends LocaleableBean
  implements IReportResult, ExportFormats
{
  private String SK;
  private String SM;
  LoginActivitySummaries SL;
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  
  public LoginSummaryRpt(LoginActivitySummaries paramLoginActivitySummaries)
  {
    this.SL = paramLoginActivitySummaries;
  }
  
  public LoginActivitySummaries getLoginActivitySummaries()
  {
    return this.SL;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    if (paramHashMap != null)
    {
      this.SK = ((String)paramHashMap.get("DateFormat"));
      this.SM = ((String)paramHashMap.get("TimeFormat"));
    }
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',');
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t');
    }
    return localStringBuffer;
  }
  
  protected StringBuffer getDelimitedReport(char paramChar)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append("Start Date");
    localStringBuffer.append(paramChar);
    localStringBuffer.append("End Date");
    localStringBuffer.append(paramChar);
    localStringBuffer.append("Number of Logins");
    localStringBuffer.append(paramChar);
    localStringBuffer.append("Number Successful");
    localStringBuffer.append(paramChar);
    localStringBuffer.append("Number Failed");
    localStringBuffer.append(paramChar);
    localStringBuffer.append("Percent Successful");
    localStringBuffer.append(paramChar);
    localStringBuffer.append("Percent Failed");
    localStringBuffer.append(_lineSeparator);
    if (this.SL != null)
    {
      LoginActivitySummary localLoginActivitySummary = null;
      Iterator localIterator = this.SL.iterator();
      while (localIterator.hasNext())
      {
        localLoginActivitySummary = (LoginActivitySummary)localIterator.next();
        if (this.SK != null) {
          localLoginActivitySummary.setDateFormat(this.SK);
        }
        localStringBuffer.append(localLoginActivitySummary.getStartDate());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localLoginActivitySummary.getEndDate());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localLoginActivitySummary.getNumLogins());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localLoginActivitySummary.getNumSuccessful());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localLoginActivitySummary.getNumFailed());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localLoginActivitySummary.getPercentSuccessful());
        localStringBuffer.append(paramChar);
        localStringBuffer.append(localLoginActivitySummary.getPercentFailed());
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.LoginSummaryRpt
 * JD-Core Version:    0.7.0.1
 */