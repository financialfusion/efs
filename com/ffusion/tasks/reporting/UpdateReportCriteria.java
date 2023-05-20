package com.ffusion.tasks.reporting;

import com.ffusion.beans.reporting.Report;
import com.ffusion.beans.reporting.ReportCriteria;
import com.ffusion.beans.reporting.ReportSortCriteria;
import com.ffusion.beans.reporting.ReportSortCriterion;
import com.ffusion.beans.user.UserLocale;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.Task;
import com.ffusion.util.DateFormatUtil;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Properties;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class UpdateReportCriteria
  extends BaseTask
  implements Task
{
  public static final String SEARCH_CRIT_PREFIX = "RSeC_";
  public static final String SORT_CRIT_PREFIX = "RSoC_";
  public static final String REPORT_OPTION_PREFIX = "ROpt_";
  public static final String DATE_FLAG_PREFIX = "RDateFlag_";
  private static final int RM = "RDateFlag_".length();
  public static final String TIME_FLAG_PREFIX = "RTimeFlag_";
  private static final int RI = "RTimeFlag_".length();
  public static final String DATETIME_FLAG_ENABLE_STRING = "true";
  public static final String EXTRA_PREFIX = "ROpt_Extra_";
  private static final int RJ = "RSeC_".length();
  private static final int RN = "RSoC_".length();
  private static final int RK = "ROpt_".length();
  public static final String SORT_CRIT_ASC_PREFIX = "Asc";
  private static final int RL = "Asc".length();
  private String RH;
  private String RG;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    String str1 = this.successURL;
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    Report localReport = (Report)localHttpSession.getAttribute(this.RH);
    UserLocale localUserLocale = (UserLocale)localHttpSession.getAttribute("UserLocale");
    String str2 = null;
    String str3 = null;
    if (localUserLocale != null)
    {
      str2 = localUserLocale.getDateFormat();
      str3 = localUserLocale.getTimeFormat();
    }
    else
    {
      str2 = "MM/dd/yyyy";
      str3 = "HH:mm";
    }
    Enumeration localEnumeration = paramHttpServletRequest.getParameterNames();
    Object localObject2;
    Object localObject3;
    Object localObject4;
    while (localEnumeration.hasMoreElements())
    {
      str4 = (String)localEnumeration.nextElement();
      localObject1 = paramHttpServletRequest.getParameterValues(str4);
      if (("ROpt_EXTRAFOOTERLINE".equals(str4)) && (localObject1[0].length() > 255))
      {
        this.error = 85;
        return this.taskErrorURL;
      }
      localObject2 = new StringBuffer();
      localObject3 = paramHttpServletRequest.getParameter("RDateFlag_" + str4);
      localHttpSession.removeAttribute("RDateFlag_" + str4);
      int i = (localObject3 != null) && (((String)localObject3).equals("true")) ? 1 : 0;
      localObject4 = paramHttpServletRequest.getParameter("RTimeFlag_" + str4);
      localHttpSession.removeAttribute("RTimeFlag_" + str4);
      int j = (localObject4 != null) && (((String)localObject4).equals("true")) ? 1 : 0;
      Object localObject6;
      String str6;
      for (int k = 0; k < localObject1.length; k++)
      {
        if ((i != 0) || (j != 0))
        {
          localObject6 = null;
          str6 = null;
          if (i != 0)
          {
            localObject6 = str2;
            str6 = "MM/dd/yyyy";
          }
          else
          {
            localObject6 = str3;
            str6 = "HH:mm";
          }
          Date localDate = null;
          try
          {
            if ((localObject1[k] != null) && (localObject1[k].length() != 0) && (!localObject1[k].equalsIgnoreCase((String)localObject6))) {
              localDate = DateFormatUtil.getFormatter((String)localObject6).parse(localObject1[k]);
            }
          }
          catch (ParseException localParseException1)
          {
            try
            {
              localDate = DateFormatUtil.getFormatter(str6).parse(localObject1[k]);
            }
            catch (ParseException localParseException2)
            {
              this.error = 44;
              return this.taskErrorURL;
            }
          }
          if (localObject1[k].equalsIgnoreCase((String)localObject6)) {
            localObject1[k] = str6;
          } else if ((localObject1[k] != null) && (localObject1[k].length() != 0)) {
            localObject1[k] = DateFormatUtil.getFormatter(str6).format(localDate);
          }
        }
        ((StringBuffer)localObject2).append(localObject1[k]);
        if (k != localObject1.length - 1) {
          ((StringBuffer)localObject2).append(",");
        }
      }
      Object localObject5;
      if (str4.startsWith("RSeC_"))
      {
        localObject5 = str4.substring(RJ);
        localReport.getReportCriteria().set((String)localObject5, ((StringBuffer)localObject2).toString(), true, false);
        localHttpSession.removeAttribute(str4);
      }
      else if (str4.startsWith("RSoC_"))
      {
        localObject5 = localReport.getReportCriteria().getSortCriteria();
        localObject6 = null;
        str6 = str4.substring(RN);
        int m = 0;
        int n = 0;
        if (str6.startsWith("Asc")) {
          m = Integer.parseInt(str6.substring(RL));
        } else {
          m = Integer.parseInt(str6);
        }
        for (int i1 = 0; i1 < ((ReportSortCriteria)localObject5).size(); i1++)
        {
          localObject6 = (ReportSortCriterion)((ReportSortCriteria)localObject5).get(i1);
          if (((ReportSortCriterion)localObject6).getOrdinal() == m)
          {
            n = 1;
            break;
          }
        }
        if ((localObject6 != null) && (n != 0))
        {
          if (str6.startsWith("Asc")) {
            ((ReportSortCriterion)localObject6).setAsc(Boolean.valueOf(localObject1[0]).booleanValue());
          } else {
            ((ReportSortCriterion)localObject6).setName(localObject1[0]);
          }
        }
        else if (str6.startsWith("Asc")) {
          ((ReportSortCriteria)localObject5).create(m, "", Boolean.valueOf(localObject1[0]).booleanValue());
        } else {
          ((ReportSortCriteria)localObject5).create(m, localObject1[0], true);
        }
        localHttpSession.removeAttribute(str4);
      }
      else if (str4.startsWith("ROpt_"))
      {
        localObject5 = str4.substring(RK);
        localReport.getReportCriteria().set((String)localObject5, localObject1[0], false, true);
        localHttpSession.removeAttribute(str4);
      }
    }
    String str4 = localReport.getReportCriteria().getReportOptions().getProperty("TITLE");
    if ((str4 != null) && ((str4.length() > 255) || (!u(str4))))
    {
      this.error = 32;
      return this.taskErrorURL;
    }
    Object localObject1 = localReport.getReportCriteria().getSortCriteria();
    if ((localObject1 != null) && (!((ReportSortCriteria)localObject1).isEmpty()))
    {
      ((ReportSortCriteria)localObject1).setSortedBy("ORDINAL");
      localObject2 = new ArrayList();
      localObject3 = new ReportSortCriteria();
      Iterator localIterator = ((ReportSortCriteria)localObject1).iterator();
      localObject4 = (ReportSortCriterion)localIterator.next();
      String str5 = ((ReportSortCriterion)localObject4).getName();
      ((ArrayList)localObject2).add(str5);
      ((ReportSortCriteria)localObject3).create(((ReportSortCriterion)localObject4).getOrdinal(), str5, ((ReportSortCriterion)localObject4).getAsc());
      while (localIterator.hasNext())
      {
        localObject4 = (ReportSortCriterion)localIterator.next();
        str5 = ((ReportSortCriterion)localObject4).getName();
        if (!((ArrayList)localObject2).contains(str5))
        {
          ((ArrayList)localObject2).add(str5);
          ((ReportSortCriteria)localObject3).create(((ReportSortCriterion)localObject4).getOrdinal(), str5, ((ReportSortCriterion)localObject4).getAsc());
        }
      }
      localReport.getReportCriteria().setSortCriteria((ReportSortCriteria)localObject3);
    }
    localHttpSession.setAttribute(this.RH, localReport);
    if (this.RG != null)
    {
      paramHttpServletResponse.sendRedirect(this.RG);
      this.RG = null;
    }
    return str1;
  }
  
  public void setTarget(String paramString)
  {
    this.RG = paramString;
  }
  
  public void setReportName(String paramString)
  {
    this.RH = paramString;
  }
  
  private boolean u(String paramString)
  {
    boolean bool = true;
    for (int i = 0; (bool) && (i < paramString.length()); i++)
    {
      char c = paramString.charAt(i);
      if ((!Character.isLetterOrDigit(c)) && (!Character.isSpaceChar(c)) && (c != '_') && (c != '\'') && (c != '-')) {
        bool = false;
      }
    }
    return bool;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.UpdateReportCriteria
 * JD-Core Version:    0.7.0.1
 */