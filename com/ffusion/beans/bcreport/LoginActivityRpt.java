package com.ffusion.beans.bcreport;

import com.ffusion.beans.reporting.ExportFormats;
import com.ffusion.beans.reporting.ReportConsts;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.util.beans.LocaleableBean;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

public class LoginActivityRpt
  extends LocaleableBean
  implements IReportResult, ExportFormats
{
  LoginActivityDetails SR;
  private String SQ;
  private String SS;
  public static String _lineSeparator = System.getProperty("line.separator", "\n");
  
  public LoginActivityRpt(LoginActivityDetails paramLoginActivityDetails)
  {
    this.SR = paramLoginActivityDetails;
  }
  
  public LoginActivityDetails getLoginActivityDetails()
  {
    return this.SR;
  }
  
  public Object export(String paramString, HashMap paramHashMap)
  {
    if (paramHashMap != null)
    {
      this.SQ = ((String)paramHashMap.get("DateFormat"));
      this.SS = ((String)paramHashMap.get("TimeFormat"));
    }
    ReportCriteria localReportCriteria = (ReportCriteria)paramHashMap.get("REPORTCRITERIA");
    String str = (String)localReportCriteria.getSearchCriteria().get("Business");
    boolean bool = str.equals("AllConsumers");
    StringBuffer localStringBuffer = null;
    if (paramString.equals("COMMA")) {
      localStringBuffer = getDelimitedReport(',', bool);
    } else if (paramString.equals("TAB")) {
      localStringBuffer = getDelimitedReport('\t', bool);
    }
    return localStringBuffer;
  }
  
  protected StringBuffer getDelimitedReport(char paramChar, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(ReportConsts.getColumnName(350, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append(ReportConsts.getColumnName(356, this.locale));
    localStringBuffer.append(paramChar);
    if (!paramBoolean)
    {
      localStringBuffer.append(ReportConsts.getColumnName(352, this.locale));
      localStringBuffer.append(paramChar);
    }
    localStringBuffer.append(ReportConsts.getColumnName(353, this.locale));
    localStringBuffer.append(paramChar);
    localStringBuffer.append("Login status");
    localStringBuffer.append(_lineSeparator);
    if (this.SR != null)
    {
      LoginActivityDetail localLoginActivityDetail = null;
      Iterator localIterator = this.SR.iterator();
      while (localIterator.hasNext())
      {
        localLoginActivityDetail = (LoginActivityDetail)localIterator.next();
        if (this.SQ != null) {
          localLoginActivityDetail.setDateFormat(this.SQ);
        }
        localStringBuffer.append(localLoginActivityDetail.getDate());
        localStringBuffer.append(paramChar);
        if (this.SS != null) {
          localLoginActivityDetail.setDateFormat(this.SS);
        }
        localStringBuffer.append(localLoginActivityDetail.getDate());
        localStringBuffer.append(paramChar);
        if (!paramBoolean)
        {
          localStringBuffer.append(localLoginActivityDetail.getBusinessName());
          localStringBuffer.append(paramChar);
        }
        localStringBuffer.append(localLoginActivityDetail.getUserName());
        localStringBuffer.append(paramChar);
        if (localLoginActivityDetail.getSuccess().equals("TRUE")) {
          localStringBuffer.append("Successful");
        } else {
          localStringBuffer.append("Failed");
        }
        localStringBuffer.append(_lineSeparator);
      }
    }
    return localStringBuffer;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.beans.bcreport.LoginActivityRpt
 * JD-Core Version:    0.7.0.1
 */