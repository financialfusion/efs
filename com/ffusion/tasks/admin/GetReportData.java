package com.ffusion.tasks.admin;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.admin.AdminOBORpt;
import com.ffusion.beans.admin.AdminSysActivityRpt;
import com.ffusion.beans.admin.AdminUserEntitlementRpt;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.beans.util.StringList;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Admin;
import com.ffusion.reporting.IReportResult;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.beans.DateTime;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportData
  extends BaseTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    Report localReport = (Report)localHttpSession.getAttribute("ReportData");
    if (localReport == null)
    {
      this.error = 64;
      return super.getServiceErrorURL();
    }
    Properties localProperties1 = null;
    Properties localProperties2 = null;
    ReportCriteria localReportCriteria = localReport.getReportCriteria();
    if (localReportCriteria != null)
    {
      localProperties1 = localReportCriteria.getSearchCriteria();
      localProperties2 = localReportCriteria.getReportOptions();
    }
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
      UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
      localHashMap.put("UserLocale", localUserLocale);
      if ((localProperties2 != null) && ("Session Receipt Report".equals(localReportCriteria.getReportOptions().getProperty("REPORTTYPE"))))
      {
        localObject1 = null;
        if (localUserLocale != null) {
          localObject1 = localUserLocale.getDateFormat();
        } else {
          localObject1 = "MM/dd/yyyy";
        }
        jdMethod_for(localProperties1, localSecureUser, (String)localObject1);
      }
      if (this.error != 0) {
        return super.getTaskErrorURL();
      }
      Object localObject1 = Admin.getReportData(localSecureUser, localReportCriteria, localHashMap);
      localReport.setReportResult((IReportResult)localObject1);
      Object localObject2;
      if ((localObject1 instanceof AdminUserEntitlementRpt))
      {
        localObject2 = (AdminUserEntitlementRpt)localReport.getReportResult();
        localHttpSession.setAttribute("RptBusinessEmployees", ((AdminUserEntitlementRpt)localObject2).getBusinessEmployees());
        localHttpSession.setAttribute("RptAccounts", ((AdminUserEntitlementRpt)localObject2).getAccounts());
        localHttpSession.setAttribute("RptPermissions", ((AdminUserEntitlementRpt)localObject2).getEntitlementGroupPermissions());
        StringList localStringList = new StringList();
        localStringList.add(0, "Undefined");
        localStringList.add(1, "Transaction");
        localStringList.add(2, "Day");
        localStringList.add(3, "Week");
        localStringList.add(4, "Month");
        localHttpSession.setAttribute("RptLimitPeriodMap", localStringList);
      }
      else if ((localObject1 instanceof AdminSysActivityRpt))
      {
        localObject2 = (AdminSysActivityRpt)localReport.getReportResult();
        localHttpSession.setAttribute("RptAuditLogRecords", ((AdminSysActivityRpt)localObject2).getAuditLogRecords());
        localHttpSession.setAttribute("RptBusinessEmployees", ((AdminSysActivityRpt)localObject2).getUsers());
      }
      else if ((localObject1 instanceof AdminOBORpt))
      {
        localObject2 = (AdminOBORpt)localReport.getReportResult();
        localHttpSession.setAttribute("RptAuditLogRecords", ((AdminOBORpt)localObject2).getAuditLogRecords());
        localHttpSession.setAttribute("RptBusinessEmployees", ((AdminOBORpt)localObject2).getUsers());
        localHttpSession.setAttribute("RptBusinessAgents", ((AdminOBORpt)localObject2).getAgents());
      }
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return super.getServiceErrorURL();
    }
    catch (Exception localException) {}
    return super.getSuccessURL();
  }
  
  private void jdMethod_for(Properties paramProperties, SecureUser paramSecureUser, String paramString)
  {
    if (paramProperties != null)
    {
      String str1;
      String str2;
      if ("Relative".equals(paramProperties.getProperty("Date Range Type")))
      {
        str1 = paramProperties.getProperty("Date Range");
        if ((str1 == null) || (str1.length() == 0))
        {
          this.error = 100206;
        }
        else if ("Current Session".equals(str1))
        {
          str2 = paramProperties.getProperty("SessionLoginTime_HIDE");
          if ((str2 == null) || (str2.length() == 0)) {
            this.error = 100200;
          } else {
            paramProperties.setProperty("User", String.valueOf(paramSecureUser.getProfileID()));
          }
        }
      }
      else
      {
        str1 = paramProperties.getProperty("StartDate");
        str2 = paramProperties.getProperty("EndDate");
        if ((str1 == null) || (str1.length() == 0)) {
          this.error = 100201;
        } else {
          try
          {
            DateTime localDateTime1 = new DateTime(str1, paramSecureUser.getLocale(), paramString);
            if ((str2 == null) || (str2.length() == 0)) {
              this.error = 100202;
            } else {
              try
              {
                DateTime localDateTime2 = new DateTime(str2, paramSecureUser.getLocale(), paramString);
                DateTime localDateTime3 = new DateTime(Calendar.getInstance(paramSecureUser.getLocale()), paramSecureUser.getLocale());
                localDateTime3.set(11, 0);
                localDateTime3.clear(12);
                localDateTime3.clear(13);
                localDateTime3.clear(14);
                localDateTime3.add(5, 1);
                if (localDateTime2.compare(localDateTime1) < 0) {
                  this.error = 100207;
                } else if (localDateTime3.compare(localDateTime2) <= 0) {
                  this.error = 100208;
                }
              }
              catch (Exception localException2)
              {
                this.error = 100204;
              }
            }
          }
          catch (Exception localException1)
          {
            this.error = 100203;
          }
        }
      }
      if (this.error == 0)
      {
        str1 = paramProperties.getProperty("User");
        if ((str1 == null) || (str1.length() == 0)) {
          this.error = 100205;
        }
      }
    }
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.admin.GetReportData
 * JD-Core Version:    0.7.0.1
 */