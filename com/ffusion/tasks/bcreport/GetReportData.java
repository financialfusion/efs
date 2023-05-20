package com.ffusion.tasks.bcreport;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.bcreport.BusinessServiceChargeRpt;
import com.ffusion.beans.bcreport.CSRActivityRpt;
import com.ffusion.beans.bcreport.CSRPerformanceRpt;
import com.ffusion.beans.bcreport.CSRTeamActivityRpt;
import com.ffusion.beans.bcreport.CSRTeamPerformanceRpt;
import com.ffusion.beans.bcreport.ConsumerServiceChargeRpt;
import com.ffusion.beans.bcreport.LoginActivityRpt;
import com.ffusion.beans.bcreport.LoginSummaryRpt;
import com.ffusion.beans.bcreport.UserActivityRpt;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.BCReport;
import com.ffusion.reporting.IReportResult;
import com.ffusion.services.bptw.BillPay;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.Task;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportData
  extends BaseTask
  implements Task
{
  public static String REPORT_NAME = "ReportData";
  public static String USER_ACTIVITY_RECORDS = "UserActivityRecords";
  public static String LOGIN_ACTIVITY_DETAILS = "LoginActivityDetails";
  public static String LOGIN_ACTIVITY_SUMMARIES = "LoginActivitySummaries";
  public static String CASE_PERFORMANCES = "CasePerformances";
  public static String CSR_CASE_PERFORMANCE_DATA = "CSRCasePerformanceData";
  public static String BUSINESS_SERVICE_CHARGE_ENTRIES = "BusinessServiceChargeEntries";
  public static String CONSUMER_SERVICE_CHARGE_ENTRIES = "ConsumerServiceChargeEntries";
  protected boolean _entBypass = true;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Report localReport = (Report)localHttpSession.getAttribute(REPORT_NAME);
    ReportCriteria localReportCriteria = localReport.getReportCriteria();
    Properties localProperties = localReportCriteria.getReportOptions();
    String str2 = localProperties.getProperty("REPORTTYPE");
    Object localObject1;
    Object localObject2;
    if (str2.equals("Bank Employee Permissions Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("User");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37500;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("Customer Permissions Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("Business");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37501;
        return this.taskErrorURL;
      }
      localReportCriteria.setCurrentSearchCriterion("User");
      localObject2 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject2 == null) || (((String)localObject2).equals("")))
      {
        this.error = 37502;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("Bank Employee History Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("BankEmployee");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37500;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("Customer History Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("User");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37503;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("Business History Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("Business");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37504;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("Transaction History Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("TrackingId");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37505;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("Managed Participant History Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("ManagedParticipant");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37506;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("Beneficiary History Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("Beneficiary");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37507;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("Payee History Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("Payee");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37508;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("ACH Company History Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("ACHCompanyID");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37509;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("Account Group History Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("AccountGroup");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37510;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("Cash Concentration Company History Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("CashConCompany");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37511;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("External Transfer ACH Batch Information Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("Business");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37504;
        return this.taskErrorURL;
      }
    }
    else if (str2.equals("External Transfer Account Report"))
    {
      localReportCriteria.setCurrentSearchCriterion("Account");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37512;
        return this.taskErrorURL;
      }
    }
    else if ((str2.equals("ACH Batch Report")) || (str2.equals("ACH File Upload Report")) || (str2.equals("ACH File Upload Report")) || (str2.equals("Cash Concentration Company Activity Report")) || (str2.equals("Child Support Payment Report")) || (str2.equals("Tax Payment Report")))
    {
      localReportCriteria.setCurrentSearchCriterion("CreditFromAmount");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      localReportCriteria.setCurrentSearchCriterion("CreditToAmount");
      localObject2 = localReportCriteria.getCurrentSearchCriterionValue();
      double d1 = 0.0D;
      double d2 = 0.0D;
      if (localObject1 != null)
      {
        localObject1 = ((String)localObject1).trim();
        if (((String)localObject1).length() > 0) {
          try
          {
            d1 = Double.parseDouble((String)localObject1);
          }
          catch (Exception localException2)
          {
            this.error = 37520;
            return this.taskErrorURL;
          }
        }
        if (d1 < 0.0D)
        {
          this.error = 37520;
          return this.taskErrorURL;
        }
      }
      if (localObject2 != null)
      {
        localObject2 = ((String)localObject2).trim();
        if (((String)localObject2).length() > 0) {
          try
          {
            d2 = Double.parseDouble((String)localObject2);
          }
          catch (Exception localException3)
          {
            this.error = 37521;
            return this.taskErrorURL;
          }
        }
        if (d2 < 0.0D)
        {
          this.error = 37521;
          return this.taskErrorURL;
        }
      }
      if ((localObject2 != null) && (((String)localObject2).length() > 0) && (localObject1 != null) && (((String)localObject1).length() > 0) && (d2 < d1))
      {
        this.error = 37522;
        return this.taskErrorURL;
      }
      localReportCriteria.setCurrentSearchCriterion("DebitFromAmount");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      localReportCriteria.setCurrentSearchCriterion("DebitToAmount");
      localObject2 = localReportCriteria.getCurrentSearchCriterionValue();
      d1 = 0.0D;
      d2 = 0.0D;
      if (localObject1 != null)
      {
        localObject1 = ((String)localObject1).trim();
        if (((String)localObject1).length() > 0) {
          try
          {
            d1 = Double.parseDouble((String)localObject1);
          }
          catch (Exception localException4)
          {
            this.error = 37520;
            return this.taskErrorURL;
          }
        }
        if (d1 < 0.0D)
        {
          this.error = 37520;
          return this.taskErrorURL;
        }
      }
      if (localObject2 != null)
      {
        localObject2 = ((String)localObject2).trim();
        if (((String)localObject2).length() > 0) {
          try
          {
            d2 = Double.parseDouble((String)localObject2);
          }
          catch (Exception localException5)
          {
            this.error = 37521;
            return this.taskErrorURL;
          }
        }
        if (d2 < 0.0D)
        {
          this.error = 37521;
          return this.taskErrorURL;
        }
      }
      if ((localObject2 != null) && (((String)localObject2).length() > 0) && (localObject1 != null) && (((String)localObject1).length() > 0) && (d2 < d1))
      {
        this.error = 37522;
        return this.taskErrorURL;
      }
    }
    else if ((str2.equals("CSR Team Performance Report")) || (str2.equals("My Performance Report")))
    {
      localReportCriteria.setCurrentSearchCriterion("CaseType");
      localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
      if ((localObject1 == null) || (((String)localObject1).equals("")))
      {
        this.error = 37513;
        return this.taskErrorURL;
      }
      localObject2 = "FALSE";
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("NUM_CASES_OPENED");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("TIME_TO_OPEN_AVG");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("TIME_TO_OPEN_STD_DEV");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("TIME_TO_OPEN_RANGE");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("CASE_RES_NUM_SINGLE_AGENT_IN_PROG");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("CASE_RES_PERCENT_SINGLE_AGENT_IN_PROG");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("CASE_RES_PERCENT_SINGLE_AGENT_IN_PROG");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("CASE_RES_NUM_SINGLE_AGENT_CLOSED");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("CASE_RES_PERCENT_SINGLE_AGENT_CLOSED");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("CASE_RES_NUM_HELP_REQ_IN_PROG");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("CASE_RES_PERCENT_HELP_REQ_IN_PROG");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("CASE_RES_NUM_HELP_REQ_CLOSED");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("CASE_RES_PERCENT_HELP_REQ_CLOSED");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("CASE_RES_NUM_HELP_PROVIDED_CLOSED");
      }
      if (((String)localObject2).equals("FALSE")) {
        localObject2 = localProperties.getProperty("CASE_RES_PERCENT_HELP_PROVIDED_CLOSED");
      }
      if (((String)localObject2).equals("FALSE"))
      {
        this.error = 37519;
        return this.taskErrorURL;
      }
    }
    else
    {
      String str3;
      if (str2.equals("CSR Performance Report"))
      {
        localReportCriteria.setCurrentSearchCriterion("Agent");
        localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
        if ((localObject1 == null) || (((String)localObject1).equals("")))
        {
          this.error = 37514;
          return this.taskErrorURL;
        }
        localReportCriteria.setCurrentSearchCriterion("CaseType");
        localObject2 = localReportCriteria.getCurrentSearchCriterionValue();
        if ((localObject2 == null) || (((String)localObject2).equals("")))
        {
          this.error = 37513;
          return this.taskErrorURL;
        }
        str3 = "FALSE";
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("NUM_CASES_OPENED");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("TIME_TO_OPEN_AVG");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("TIME_TO_OPEN_STD_DEV");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("TIME_TO_OPEN_RANGE");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_NUM_SINGLE_AGENT_IN_PROG");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_PERCENT_SINGLE_AGENT_IN_PROG");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_PERCENT_SINGLE_AGENT_IN_PROG");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_NUM_SINGLE_AGENT_CLOSED");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_PERCENT_SINGLE_AGENT_CLOSED");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_NUM_HELP_REQ_IN_PROG");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_PERCENT_HELP_REQ_IN_PROG");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_NUM_HELP_REQ_CLOSED");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_PERCENT_HELP_REQ_CLOSED");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_NUM_HELP_PROVIDED_CLOSED");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_PERCENT_HELP_PROVIDED_CLOSED");
        }
        if (str3.equals("FALSE"))
        {
          this.error = 37519;
          return this.taskErrorURL;
        }
      }
      else if (str2.equals("My Organization's Performance Report"))
      {
        localReportCriteria.setCurrentSearchCriterion("Agent");
        localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
        if ((localObject1 == null) || (((String)localObject1).equals("")))
        {
          this.error = 37515;
          return this.taskErrorURL;
        }
        localReportCriteria.setCurrentSearchCriterion("CaseType");
        localObject2 = localReportCriteria.getCurrentSearchCriterionValue();
        if ((localObject2 == null) || (((String)localObject2).equals("")))
        {
          this.error = 37513;
          return this.taskErrorURL;
        }
        str3 = "FALSE";
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("NUM_CASES_OPENED");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("TIME_TO_OPEN_AVG");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("TIME_TO_OPEN_STD_DEV");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("TIME_TO_OPEN_RANGE");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_NUM_SINGLE_AGENT_IN_PROG");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_PERCENT_SINGLE_AGENT_IN_PROG");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_PERCENT_SINGLE_AGENT_IN_PROG");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_NUM_SINGLE_AGENT_CLOSED");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_PERCENT_SINGLE_AGENT_CLOSED");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_NUM_HELP_REQ_IN_PROG");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_PERCENT_HELP_REQ_IN_PROG");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_NUM_HELP_REQ_CLOSED");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_PERCENT_HELP_REQ_CLOSED");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_NUM_HELP_PROVIDED_CLOSED");
        }
        if (str3.equals("FALSE")) {
          str3 = localProperties.getProperty("CASE_RES_PERCENT_HELP_PROVIDED_CLOSED");
        }
        if (str3.equals("FALSE"))
        {
          this.error = 37519;
          return this.taskErrorURL;
        }
      }
      else if (str2.equals("Cash Concentration Inactive Divisions and Locations Report"))
      {
        localReportCriteria.setCurrentSearchCriterion("InactivePeriod");
        localObject1 = localReportCriteria.getCurrentSearchCriterionValue();
        if ((localObject1 == null) || (((String)localObject1).equals("")))
        {
          this.error = 37516;
          return this.taskErrorURL;
        }
        localObject2 = null;
        try
        {
          localObject2 = new Integer((String)localObject1);
        }
        catch (Exception localException1)
        {
          this.error = 37518;
          return this.taskErrorURL;
        }
        if (((Integer)localObject2).intValue() <= 0)
        {
          this.error = 37517;
          return this.taskErrorURL;
        }
      }
    }
    try
    {
      localObject1 = (HashMap)localHttpSession.getAttribute("Extra");
      if (localObject1 == null) {
        localObject1 = new HashMap();
      }
      ((HashMap)localObject1).put("UserLocale", localHttpSession.getAttribute("UserLocale"));
      if (this._entBypass) {
        localObject1 = EntitlementsUtil.allowEntitlementBypass((HashMap)localObject1);
      }
      if (str2.equals("Payee History Report"))
      {
        localObject2 = (BillPay)localHttpSession.getAttribute("BptwBillPay");
        if (localObject2 != null) {
          ((HashMap)localObject1).put("SERVICE", localObject2);
        }
      }
      else
      {
        ((HashMap)localObject1).remove("SERVICE");
      }
      localObject2 = BCReport.getReportData(localSecureUser, localReport.getReportCriteria(), (HashMap)localObject1);
      localReport.setReportResult((IReportResult)localObject2);
      localHttpSession.setAttribute(REPORT_NAME, localReport);
      if ((localObject2 instanceof CSRTeamActivityRpt)) {
        localHttpSession.setAttribute(USER_ACTIVITY_RECORDS, ((CSRTeamActivityRpt)localObject2).getUserActivityRecords());
      } else if ((localObject2 instanceof CSRActivityRpt)) {
        localHttpSession.setAttribute(USER_ACTIVITY_RECORDS, ((CSRActivityRpt)localObject2).getUserActivityRecords());
      } else if ((localObject2 instanceof UserActivityRpt)) {
        localHttpSession.setAttribute(USER_ACTIVITY_RECORDS, ((UserActivityRpt)localObject2).getUserActivityRecords());
      } else if ((localObject2 instanceof LoginActivityRpt)) {
        localHttpSession.setAttribute(LOGIN_ACTIVITY_DETAILS, ((LoginActivityRpt)localObject2).getLoginActivityDetails());
      } else if ((localObject2 instanceof LoginSummaryRpt)) {
        localHttpSession.setAttribute(LOGIN_ACTIVITY_SUMMARIES, ((LoginSummaryRpt)localObject2).getLoginActivitySummaries());
      } else if ((localObject2 instanceof CSRPerformanceRpt)) {
        localHttpSession.setAttribute(CSR_CASE_PERFORMANCE_DATA, ((CSRPerformanceRpt)localObject2).getCSRCasePerformanceData());
      } else if ((localObject2 instanceof CSRTeamPerformanceRpt)) {
        localHttpSession.setAttribute(CASE_PERFORMANCES, ((CSRTeamPerformanceRpt)localObject2).getCasePerformances());
      } else if ((localObject2 instanceof BusinessServiceChargeRpt)) {
        localHttpSession.setAttribute(BUSINESS_SERVICE_CHARGE_ENTRIES, ((BusinessServiceChargeRpt)localObject2).getBusinessServiceChargeEntries());
      } else if ((localObject2 instanceof ConsumerServiceChargeRpt)) {
        localHttpSession.setAttribute(CONSUMER_SERVICE_CHARGE_ENTRIES, ((ConsumerServiceChargeRpt)localObject2).getConsumerServiceChargeEntries());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str1 = this.serviceErrorURL;
    }
    return str1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bcreport.GetReportData
 * JD-Core Version:    0.7.0.1
 */