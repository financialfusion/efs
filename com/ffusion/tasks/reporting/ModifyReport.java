package com.ffusion.tasks.reporting;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportIdentification;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.EntitlementsUtil;
import com.ffusion.csil.core.Reporting;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.TaskUtil;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ModifyReport
  extends BaseTask
  implements ReportingConsts
{
  private int TV;
  private String TM = "";
  private String TX = "";
  private String TW;
  private boolean TP;
  private ArrayList TQ = new ArrayList();
  private ArrayList TS = new ArrayList();
  private ArrayList TL = new ArrayList();
  private ArrayList TU = new ArrayList();
  private ArrayList TT = new ArrayList();
  private ArrayList TN = new ArrayList();
  private ArrayList TR = new ArrayList();
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    try
    {
      EntitlementsUtil.OBOViewOnlyCheck(localSecureUser);
    }
    catch (CSILException localCSILException1)
    {
      this.error = MapError.mapError(localCSILException1);
      return super.getServiceErrorURL();
    }
    if ((this.error = validateName(this.TM, localSecureUser.getLocale())) != 0) {
      return super.getTaskErrorURL();
    }
    if ((this.error = validateDescription(this.TX, localSecureUser.getLocale())) != 0) {
      return super.getTaskErrorURL();
    }
    try
    {
      HashMap localHashMap = new HashMap();
      localHashMap.put("UserLocale", localHttpSession.getAttribute("UserLocale"));
      ReportIdentification localReportIdentification = Reporting.getReport(localSecureUser, this.TM, localHashMap);
      if ((localReportIdentification != null) && (localReportIdentification.getReportID() != this.TV))
      {
        this.error = 37002;
        return super.getTaskErrorURL();
      }
      Report localReport = null;
      if (this.TP)
      {
        localReport = (Report)localHttpSession.getAttribute(this.TW);
        localReport.getReportID().setReportName(this.TM);
        localReport.getReportID().setDescription(this.TX);
      }
      else
      {
        Object localObject1 = null;
        Object localObject2 = null;
        Properties localProperties1 = new Properties();
        for (int i = 0; i < this.TQ.size(); i++)
        {
          String str1 = (String)this.TQ.get(i);
          String str2 = (String)this.TS.get(i);
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
          j = TaskUtil.validateReportPeriod((String)localObject3, localObject1, localObject2);
          if (j != 0)
          {
            this.error = j;
            return super.getTaskErrorURL();
          }
        }
        Object localObject3 = new ReportSortCriteria();
        for (int j = 0; j < this.TL.size(); j++) {
          ((ReportSortCriteria)localObject3).create(Integer.parseInt((String)this.TU.get(j)), (String)this.TL.get(j), Boolean.valueOf((String)this.TT.get(j)).booleanValue());
        }
        Properties localProperties2 = new Properties();
        for (int k = 0; k < this.TN.size(); k++) {
          localProperties2.setProperty((String)this.TN.get(k), (String)this.TR.get(k));
        }
        localReport = new Report(new ReportIdentification(this.TV, this.TM, this.TX), new ReportCriteria(localProperties1, (ReportSortCriteria)localObject3, localProperties2), null);
      }
      Reporting.modifyReport(localSecureUser, localReport, localHashMap);
    }
    catch (CSILException localCSILException2)
    {
      this.error = MapError.mapError(localCSILException2);
      return super.getServiceErrorURL();
    }
    return super.getSuccessURL();
  }
  
  public void setIdentificationID(String paramString)
  {
    this.TV = Integer.parseInt(paramString);
  }
  
  public void setIdentificationName(String paramString)
  {
    this.TM = paramString;
  }
  
  public void setIdentificationDesc(String paramString)
  {
    this.TX = paramString;
  }
  
  public void setSearchCritName(String paramString)
  {
    this.TQ.add(paramString);
  }
  
  public void setSearchCritValue(String paramString)
  {
    this.TS.add(paramString);
  }
  
  public void setSortCritName(String paramString)
  {
    this.TL.add(paramString);
  }
  
  public void setSortCritOrdinal(String paramString)
  {
    this.TU.add(paramString);
  }
  
  public void setSortCritAsc(String paramString)
  {
    this.TT.add(paramString);
  }
  
  public void setReportOptionName(String paramString)
  {
    this.TN.add(paramString);
  }
  
  public void setReportOptionValue(String paramString)
  {
    this.TR.add(paramString);
  }
  
  public String getReportKey()
  {
    return this.TW;
  }
  
  public boolean getReportGenerated()
  {
    return this.TP;
  }
  
  public void setReportKey(String paramString)
  {
    this.TW = paramString;
  }
  
  public void setReportGenerated(String paramString)
  {
    this.TP = Boolean.valueOf(paramString).booleanValue();
  }
  
  protected int validateName(String paramString, Locale paramLocale)
  {
    if (paramString.length() <= 0) {
      return 37000;
    }
    if (paramString.length() > 40) {
      return 37000;
    }
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      if ((!Character.isLetterOrDigit(c)) && (!Character.isSpaceChar(c)) && (c != '_') && (c != '/') && (c != '-')) {
        return 37000;
      }
    }
    return 0;
  }
  
  protected int validateDescription(String paramString, Locale paramLocale)
  {
    if (paramString.length() > 255) {
      return 37001;
    }
    for (int i = 0; i < paramString.length(); i++)
    {
      char c = paramString.charAt(i);
      if ((!Character.isLetterOrDigit(c)) && (!Character.isSpaceChar(c)) && (c != '_') && (c != '/') && (c != '-')) {
        return 37001;
      }
    }
    return 0;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.ModifyReport
 * JD-Core Version:    0.7.0.1
 */