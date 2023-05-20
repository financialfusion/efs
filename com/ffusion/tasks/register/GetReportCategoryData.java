package com.ffusion.tasks.register;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.register.RegisterCategories;
import com.ffusion.beans.register.RegisterReport;
import com.ffusion.beans.register.RegisterTransactions;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportResult;
import com.ffusion.csil.CSILException;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.accounts.SetAccount;
import com.ffusion.tasks.reporting.GenerateReportBase;
import java.io.IOException;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetReportCategoryData
  extends SetReportCategory
  implements Task
{
  private String EZ = "RegisterTransactions";
  private String E0 = "RegisterCategories";
  private String E1 = GenerateReportBase.REPORT_NAME;
  
  public GetReportCategoryData()
  {
    this.collectionSessionName = "RegisterReport";
  }
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Report localReport = (Report)localHttpSession.getAttribute(this.E1);
    if (localReport == null)
    {
      this.error = 64;
      return super.getServiceErrorURL();
    }
    BaseTask localBaseTask = (BaseTask)localHttpSession.getAttribute("SetAccount");
    String str = localReport.getReportCriteria().getSearchCriteria().getProperty("Accounts");
    ((SetAccount)localBaseTask).setID(str);
    localBaseTask.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    int i = 0;
    try
    {
      i = Integer.parseInt(localBaseTask.getError());
    }
    catch (NumberFormatException localNumberFormatException1) {}
    if (i != 0)
    {
      this.error = i;
      return super.getTaskErrorURL();
    }
    localBaseTask = (BaseTask)localHttpSession.getAttribute("GetRegisterTransactions");
    localBaseTask.process(paramHttpServlet, paramHttpServletRequest, paramHttpServletResponse);
    try
    {
      i = Integer.parseInt(localBaseTask.getError());
    }
    catch (NumberFormatException localNumberFormatException2) {}
    if (i != 0)
    {
      this.error = i;
      return super.getTaskErrorURL();
    }
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    RegisterCategories localRegisterCategories = (RegisterCategories)localHttpSession.getAttribute(this.E0);
    RegisterTransactions localRegisterTransactions = (RegisterTransactions)localHttpSession.getAttribute(this.EZ);
    if (localRegisterTransactions != null)
    {
      if (localRegisterCategories != null)
      {
        try
        {
          RegisterReport localRegisterReport = super.getRegisterReport(localRegisterCategories, localRegisterTransactions);
          ReportResult localReportResult = RegisterReportUtil.getReportResult(localSecureUser, null, localReport, localRegisterReport);
          localReport.setReportResult(localReportResult);
        }
        catch (CSILException localCSILException)
        {
          this.error = localCSILException.getCode();
          return super.getTaskErrorURL();
        }
      }
      else
      {
        this.error = 20006;
        return this.taskErrorURL;
      }
    }
    else
    {
      this.error = 20004;
      return this.taskErrorURL;
    }
    return this.successURL;
  }
  
  public void setTransactionsSessionName(String paramString)
  {
    this.EZ = paramString;
  }
  
  public String getTransactionsSessionName()
  {
    return this.EZ;
  }
  
  public void setReportSessionName(String paramString)
  {
    this.E1 = paramString;
  }
  
  public String getReportSessionName()
  {
    return this.E1;
  }
  
  public void setCategoriesSessionName(String paramString)
  {
    this.E0 = paramString;
  }
  
  public String getCategoriesSessionName()
  {
    return this.E0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.register.GetReportCategoryData
 * JD-Core Version:    0.7.0.1
 */