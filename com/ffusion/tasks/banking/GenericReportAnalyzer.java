package com.ffusion.tasks.banking;

import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.ResourceUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GenericReportAnalyzer
  extends BaseTask
  implements Task
{
  private String yE = "GenericReport";
  private ArrayList yJ = new ArrayList();
  private IReportResult yH = null;
  private String yI = null;
  private String yG = null;
  private String yF = null;
  private Locale yD = null;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    this.yD = localUserLocale.getLocale();
    Report localReport = (Report)localHttpSession.getAttribute(this.yE);
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
    if (jdMethod_int(localReport) != 0) {
      return super.getServiceErrorURL();
    }
    this.yH = localReport.getReportResult();
    localHttpSession.setAttribute(getClass().getName(), this.yJ);
    this.error = 0;
    return super.getSuccessURL();
  }
  
  public void setReportKey(String paramString)
  {
    this.yE = paramString;
  }
  
  public String getReportKey()
  {
    return this.yE;
  }
  
  public IReportResult getReportResult()
  {
    return this.yH;
  }
  
  public ArrayList getReportCriteriaList()
  {
    return this.yJ;
  }
  
  public int getReportCriteriaListSize()
  {
    return this.yJ.size();
  }
  
  public String getDates()
  {
    return this.yG;
  }
  
  public String getReportType()
  {
    return this.yI;
  }
  
  public String getTransactionType()
  {
    return this.yF;
  }
  
  private int jdMethod_int(Report paramReport)
  {
    ReportCriteria localReportCriteria = paramReport.getReportCriteria();
    String str1 = localReportCriteria.getReportOptions().getProperty("REPORTTYPE");
    Properties localProperties = localReportCriteria.getSearchCriteria();
    StringBuffer localStringBuffer = new StringBuffer();
    this.yJ.clear();
    if ((str1 == null) || (str1.length() <= 0)) {
      return 67;
    }
    String str2 = localProperties.getProperty("StartDate");
    String str3 = localProperties.getProperty("EndDate");
    if ((str2 != null) && (str2.length() > 0))
    {
      if ((str3 != null) && (str3.length() > 0))
      {
        localStringBuffer.append(ResourceUtil.getString("From", "com.ffusion.beans.reporting.reports", this.yD));
        localStringBuffer.append(str2);
        localStringBuffer.append(ResourceUtil.getString("To", "com.ffusion.beans.reporting.reports", this.yD));
        localStringBuffer.append(str3);
      }
      else
      {
        localStringBuffer.append(ResourceUtil.getString("After", "com.ffusion.beans.reporting.reports", this.yD));
        localStringBuffer.append(str2);
      }
    }
    else if ((str3 != null) && (str3.length() > 0))
    {
      localStringBuffer.append(ResourceUtil.getString("Before", "com.ffusion.beans.reporting.reports", this.yD));
      localStringBuffer.append(str3);
    }
    else
    {
      localStringBuffer.append(ResourceUtil.getString("AllRecords", "com.ffusion.beans.reporting.reports", this.yD));
    }
    this.yG = localStringBuffer.toString();
    localStringBuffer.delete(0, localStringBuffer.length());
    if ((str1.equals("DepositDetail")) || (str1.equals("CreditReport")) || (str1.equals("DebitReport")) || (str1.equals("TransactionDetail")) || (str1.equals("AccountHistory")) || (str1.equals("AccountHistoryReport")) || (str1.equals("BalanceSummaryReport")) || (str1.equals("BalanceDetailOnlyReport")) || (str1.equals("CustomSummaryReport")))
    {
      str2 = (String)localProperties.get("Accounts");
      localStringBuffer.append(ResourceUtil.getString("Account", "com.ffusion.beans.reporting.reports", this.yD));
      if ((str2 == null) || (str2.length() <= 0) || (str2.equalsIgnoreCase("AllAccounts"))) {
        localStringBuffer.append(ResourceUtil.getString("AllAccounts", "com.ffusion.beans.reporting.reports", this.yD));
      } else {
        localStringBuffer.append(str2.substring(0, str2.indexOf(":")));
      }
      this.yJ.add(localStringBuffer.toString());
      localStringBuffer.delete(0, localStringBuffer.length());
      str3 = null;
      if ((!str1.equals("AccountHistoryReport")) && (!str1.equals("BalanceSummaryReport")))
      {
        str3 = localProperties.getProperty("MinimumAmount");
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append(ResourceUtil.getString("MinimumAmount", "com.ffusion.beans.reporting.reports", this.yD));
          localStringBuffer.append(str3);
          this.yJ.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        str3 = localProperties.getProperty("MaximumAmount");
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append(ResourceUtil.getString("MaximumAmount", "com.ffusion.beans.reporting.reports", this.yD));
          localStringBuffer.append(str3);
          this.yJ.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        str3 = localProperties.getProperty("TransactionSubType");
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append(ResourceUtil.getString("TransactionSubtype", "com.ffusion.beans.reporting.reports", this.yD));
          localStringBuffer.append(str3);
          this.yJ.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        str3 = localProperties.getProperty("TransactionStatus");
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append(ResourceUtil.getString("TransactionStatus", "com.ffusion.beans.reporting.reports", this.yD));
          localStringBuffer.append(str3);
          this.yJ.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        str3 = localProperties.getProperty("PayeePayor");
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append(ResourceUtil.getString("PayorPayee", "com.ffusion.beans.reporting.reports", this.yD));
          localStringBuffer.append(str3);
          this.yJ.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        str3 = localProperties.getProperty("PayorNumber");
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append(ResourceUtil.getString("PayorNumber", "com.ffusion.beans.reporting.reports", this.yD));
          localStringBuffer.append(str3);
          this.yJ.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        str3 = localProperties.getProperty("OriginatingUser");
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append(ResourceUtil.getString("OriginatingUser", "com.ffusion.beans.reporting.reports", this.yD));
          localStringBuffer.append(str3);
          this.yJ.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        str3 = localProperties.getProperty("PONumber");
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append(ResourceUtil.getString("PONumber", "com.ffusion.beans.reporting.reports", this.yD));
          localStringBuffer.append(str3);
          this.yJ.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        str3 = localProperties.getProperty("TransactionReferenceNumber");
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append(ResourceUtil.getString("TransactionReferenceNumber", "com.ffusion.beans.reporting.reports", this.yD));
          localStringBuffer.append(str3);
          this.yJ.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        str3 = localProperties.getProperty("BankReferenceNumber");
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append(ResourceUtil.getString("BankReferenceNumber", "com.ffusion.beans.reporting.reports", this.yD));
          localStringBuffer.append(str3);
          this.yJ.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        str3 = localProperties.getProperty("CustomerReferenceNumber");
        if ((str3 != null) && (str3.length() > 0))
        {
          localStringBuffer.append(ResourceUtil.getString("CustomerReferenceNumber", "com.ffusion.beans.reporting.reports", this.yD));
          localStringBuffer.append(str3);
          this.yJ.add(localStringBuffer.toString());
          localStringBuffer.delete(0, localStringBuffer.length());
        }
        if ((str1.equals("TransactionDetail")) || (str1.equals("AccountHistory")) || (str1.equals("BalanceDetailOnlyReport")))
        {
          str3 = localProperties.getProperty("TransactionType");
          if ((str3 != null) && (str3.length() > 0)) {
            this.yF = str3.trim();
          }
        }
        else if (str1.equals("CustomSummaryReport"))
        {
          str3 = localProperties.getProperty("BAISummaryCode");
          if ((str3 != null) && (str3.length() > 0))
          {
            localStringBuffer.append(ResourceUtil.getString("SummaryCode", "com.ffusion.beans.reporting.reports", this.yD));
            localStringBuffer.append(str3);
            this.yJ.add(localStringBuffer.toString());
            localStringBuffer.delete(0, localStringBuffer.length());
          }
        }
      }
    }
    else
    {
      return 68;
    }
    this.yI = str1;
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GenericReportAnalyzer
 * JD-Core Version:    0.7.0.1
 */