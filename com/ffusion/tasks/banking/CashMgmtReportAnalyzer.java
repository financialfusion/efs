package com.ffusion.tasks.banking;

import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CashMgmtReportAnalyzer
  extends BaseTask
  implements Task
{
  private String xK = "CashReport";
  private ArrayList xO = new ArrayList();
  private IReportResult xM = null;
  private String xN = null;
  private String xL = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Report localReport = (Report)localHttpSession.getAttribute(this.xK);
    if (localReport == null)
    {
      this.error = 64;
      return super.getServiceErrorURL();
    }
    ReportCriteria localReportCriteria = localReport.getReportCriteria();
    if (localReportCriteria == null)
    {
      this.error = 64;
      return super.getServiceErrorURL();
    }
    if (jdMethod_for(localReport) != 0) {
      return super.getServiceErrorURL();
    }
    this.xM = localReport.getReportResult();
    localHttpSession.setAttribute(getClass().getName(), this.xO);
    this.error = 0;
    return super.getSuccessURL();
  }
  
  public void setReportKey(String paramString)
  {
    this.xK = paramString;
  }
  
  public String getReportKey()
  {
    return this.xK;
  }
  
  public IReportResult getReportResult()
  {
    return this.xM;
  }
  
  public ArrayList getReportCriteriaList()
  {
    return this.xO;
  }
  
  public int getReportCriteriaListSize()
  {
    return this.xO.size();
  }
  
  public String getDates()
  {
    return this.xL;
  }
  
  public String getReportType()
  {
    return this.xN;
  }
  
  private int jdMethod_for(Report paramReport)
  {
    ReportCriteria localReportCriteria = paramReport.getReportCriteria();
    String str1 = localReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    Properties localProperties = localReportCriteria.getSearchCriteria();
    StringBuffer localStringBuffer = new StringBuffer();
    this.xO.clear();
    if ((str1 == null) || (str1.length() <= 0)) {
      return 67;
    }
    String str2 = localProperties.getProperty("StartDate");
    String str3 = localProperties.getProperty("EndDate");
    if ((str2 != null) && (str2.length() > 0))
    {
      if ((str3 != null) && (str3.length() > 0))
      {
        localStringBuffer.append("From ");
        localStringBuffer.append(str2);
        localStringBuffer.append(" to ");
        localStringBuffer.append(str3);
      }
      else
      {
        localStringBuffer.append("After ");
        localStringBuffer.append(str2);
      }
    }
    else if ((str3 != null) && (str3.length() > 0))
    {
      localStringBuffer.append("Before ");
      localStringBuffer.append(str3);
    }
    else
    {
      localStringBuffer.append("All records");
    }
    this.xL = localStringBuffer.toString();
    localStringBuffer.delete(0, localStringBuffer.length());
    if ((str1.equals("CashFlowReport")) || (str1.equals("CashFlowForecastReport")) || (str1.equals("BalanceSheetReport")) || (str1.equals("BalanceSheetSummary")) || (str1.equals("GeneralLedgerReport")))
    {
      str2 = (String)localProperties.get("Accounts");
      localStringBuffer.append("Account : ");
      if ((str2 == null) || (str2.length() <= 0) || (str2.equalsIgnoreCase("AllAccounts"))) {
        localStringBuffer.append("All Accounts");
      } else {
        localStringBuffer.append(str2.substring(0, str2.indexOf(":")));
      }
      this.xO.add(localStringBuffer.toString());
      localStringBuffer.delete(0, localStringBuffer.length());
      if (str1.equals("CashFlowForecastReport"))
      {
        str3 = localProperties.getProperty("ForecastDate");
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append("Forecast Date : ");
          localStringBuffer.append(str3);
          this.xO.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
      }
    }
    else
    {
      return 68;
    }
    this.xN = str1;
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.CashMgmtReportAnalyzer
 * JD-Core Version:    0.7.0.1
 */