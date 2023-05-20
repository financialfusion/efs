package com.ffusion.tasks.banking;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.affiliatebank.AffiliateBank;
import com.ffusion.beans.business.Business;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.AffiliateBankAdmin;
import com.ffusion.csil.core.Banking;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.reporting.GenerateReportBase;
import com.ffusion.tasks.util.BAIExportSettingsXMLUtil;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GenericReportDataTask
  extends BaseTask
  implements Task
{
  protected String bankingServiceName = "com.ffusion.services.Banking";
  protected String _reportName = GenerateReportBase.REPORT_NAME;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Report localReport = (Report)localHttpSession.getAttribute(this._reportName);
    if (localReport == null) {
      localReport = (Report)localHttpSession.getAttribute("GenericReport");
    }
    if (localReport == null)
    {
      this.error = 64;
      return super.getTaskErrorURL();
    }
    ReportCriteria localReportCriteria = localReport.getReportCriteria();
    try
    {
      String str1 = localReportCriteria.getReportOptions().getProperty("REPORTTYPE");
      if ((str1.equals("BalanceDetailReport")) || (str1.equals("BalanceDetailOnlyReport")))
      {
        localObject1 = localReportCriteria.getSearchCriteria().getProperty("SortByLocation");
        boolean bool = new Boolean((String)localObject1).booleanValue();
        if (bool) {
          localReportCriteria.getSortCriteria().set(2, "Location", true);
        } else {
          localReportCriteria.getSortCriteria().removeCriteriaByName("Location");
        }
      }
      Object localObject1 = (HashMap)localHttpSession.getAttribute("Extra");
      if (localObject1 == null) {
        localObject1 = new HashMap();
      }
      if (((HashMap)localObject1).get("SERVICE") == null) {
        ((HashMap)localObject1).put("SERVICE", localHttpSession.getAttribute(this.bankingServiceName));
      }
      String str2 = localReportCriteria.getReportOptions().getProperty("FORMAT");
      if ("BAI2".equals(str2))
      {
        localObject2 = (Business)localHttpSession.getAttribute("Business");
        if (localObject2 == null)
        {
          this.error = 74;
          return this.taskErrorURL;
        }
        AffiliateBank localAffiliateBank = AffiliateBankAdmin.getAffiliateBankByID(localSecureUser, ((Business)localObject2).getAffiliateBankID(), new HashMap());
        ((HashMap)localObject1).put("AFFILIATE_BANK_ROUTING_NUMBER", localAffiliateBank.getAffiliateRoutingNum());
        HashMap localHashMap = null;
        try
        {
          localHashMap = BAIExportSettingsXMLUtil.getBAIExportSettings(localSecureUser, (Business)localObject2);
        }
        catch (Exception localException2)
        {
          DebugLog.throwing("The call to retrieve BAI Export Settings failed.", localException2);
          this.error = 3526;
          return this.taskErrorURL;
        }
        if ((localHashMap != null) && (!localHashMap.isEmpty())) {
          ((HashMap)localObject1).putAll(localHashMap);
        }
      }
      Object localObject2 = Banking.getReportData(localSecureUser, localReportCriteria, (HashMap)localObject1);
      localReport.setReportResult((IReportResult)localObject2);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    catch (Exception localException1)
    {
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setBankingServiceName(String paramString)
  {
    this.bankingServiceName = paramString;
  }
  
  public void setReportName(String paramString)
  {
    this._reportName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.banking.GenericReportDataTask
 * JD-Core Version:    0.7.0.1
 */