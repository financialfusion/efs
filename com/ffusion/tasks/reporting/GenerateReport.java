package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.util.TaskUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GenerateReport
  extends BaseTask
  implements ReportingConsts
{
  private String T6 = "Report";
  private int Ud = 0;
  private String T7 = "";
  private String Uf = "";
  private ArrayList T9 = new ArrayList();
  private ArrayList Ub = new ArrayList();
  private ArrayList T5 = new ArrayList();
  private ArrayList Ue = new ArrayList();
  private ArrayList Uc = new ArrayList();
  private ArrayList T8 = new ArrayList();
  private ArrayList Ua = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    if (this.T7.length() <= 0)
    {
      this.error = 37000;
      return super.getTaskErrorURL();
    }
    if (this.T7.length() > 40)
    {
      this.error = 37000;
      return super.getTaskErrorURL();
    }
    char c;
    for (int i = 0; i < this.T7.length(); i++)
    {
      c = this.T7.charAt(i);
      if ((!Character.isLetterOrDigit(c)) && (!Character.isSpaceChar(c)) && (c != '_') && (c != '/') && (c != '-'))
      {
        this.error = 37000;
        return super.getTaskErrorURL();
      }
    }
    if (this.Uf.length() > 255)
    {
      this.error = 37001;
      return super.getTaskErrorURL();
    }
    for (i = 0; i < this.Uf.length(); i++)
    {
      c = this.Uf.charAt(i);
      if ((!Character.isLetterOrDigit(c)) && (!Character.isSpaceChar(c)) && (c != '_') && (c != '/') && (c != '-'))
      {
        this.error = 37001;
        return super.getTaskErrorURL();
      }
    }
    try
    {
      Object localObject1 = null;
      Object localObject2 = null;
      Properties localProperties1 = new Properties();
      for (int j = 0; j < this.T9.size(); j++)
      {
        String str1 = (String)this.T9.get(j);
        String str2 = (String)this.Ub.get(j);
        localProperties1.setProperty(str1, str2);
        if (str1.equals("StartDate")) {
          localObject1 = str2;
        } else if (str1.equals("EndDate")) {
          localObject2 = str2;
        }
      }
      if (((localObject1 != null) && (localObject1.length() > 0)) || ((localObject2 != null) && (localObject2.length() > 0)))
      {
        localObject3 = null;
        if (localUserLocale != null) {
          localObject3 = localUserLocale.getDateFormat();
        } else {
          localObject3 = "MM/dd/yyyy";
        }
        k = TaskUtil.validateReportPeriod((String)localObject3, localObject1, localObject2);
        if (k != 0)
        {
          this.error = k;
          return super.getTaskErrorURL();
        }
      }
      Object localObject3 = new ReportSortCriteria();
      for (int k = 0; k < this.T5.size(); k++) {
        ((ReportSortCriteria)localObject3).create(Integer.parseInt((String)this.Ue.get(k)), (String)this.T5.get(k), Boolean.valueOf((String)this.Uc.get(k)).booleanValue());
      }
      Properties localProperties2 = new Properties();
      for (int m = 0; m < this.T8.size(); m++) {
        localProperties2.setProperty((String)this.T8.get(m), (String)this.Ua.get(m));
      }
      Report localReport = new Report(new ReportIdentification(this.Ud, this.T7, this.Uf), new ReportCriteria(localProperties1, (ReportSortCriteria)localObject3, localProperties2), null);
      localReport.setLocale(localSecureUser.getLocale());
      localHttpSession.setAttribute(this.T6, localReport);
    }
    catch (Exception localException)
    {
      return super.getTaskErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setIdentificationID(String paramString)
  {
    this.Ud = Integer.parseInt(paramString);
  }
  
  public void setIdentificationName(String paramString)
  {
    this.T7 = paramString;
  }
  
  public void setIdentificationDesc(String paramString)
  {
    this.Uf = paramString;
  }
  
  public void setSearchCritName(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.T9.add(paramString);
    }
  }
  
  public void setSearchCritValue(String paramString)
  {
    if ((paramString == null) || (paramString.trim().length() <= 0)) {
      this.T9.remove(this.T9.size() - 1);
    } else {
      this.Ub.add(paramString);
    }
  }
  
  public void setSortCritName(String paramString)
  {
    this.T5.add(paramString);
  }
  
  public void setSortCritOrdinal(String paramString)
  {
    this.Ue.add(paramString);
  }
  
  public void setSortCritAsc(String paramString)
  {
    this.Uc.add(paramString);
  }
  
  public void setReportOptionName(String paramString)
  {
    if ((paramString != null) && (paramString.trim().length() > 0)) {
      this.T8.add(paramString);
    }
  }
  
  public void setReportOptionValue(String paramString)
    throws Exception
  {
    if ((paramString == null) || (paramString.trim().length() <= 0)) {
      this.T8.remove(this.T8.size() - 1);
    } else {
      this.Ua.add(paramString);
    }
  }
  
  public void setReportName(String paramString)
  {
    this.T6 = paramString;
  }
  
  public String getReportName()
  {
    return this.T6;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.GenerateReport
 * JD-Core Version:    0.7.0.1
 */