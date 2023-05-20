package com.ffusion.tasks.reporting;

import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetHeaderFooter
  extends BaseTask
  implements ReportingConsts
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = (String)localHttpSession.getAttribute("useCompanyName");
    localHttpSession.removeAttribute("useCompanyName");
    if ((str1 != null) && (str1.equalsIgnoreCase("TRUE"))) {
      localHttpSession.setAttribute("showCompanyName", "TRUE");
    } else {
      localHttpSession.setAttribute("showCompanyName", "FALSE");
    }
    String str2 = (String)localHttpSession.getAttribute("useTitle");
    localHttpSession.removeAttribute("useTitle");
    if ((str2 != null) && (str2.equalsIgnoreCase("TRUE")))
    {
      str3 = (String)localHttpSession.getAttribute("title");
      localHttpSession.setAttribute("title", str3);
      localHttpSession.setAttribute("showTitle", "TRUE");
    }
    else
    {
      localHttpSession.setAttribute("showTitle", "FALSE");
    }
    String str3 = (String)localHttpSession.getAttribute("useDate");
    localHttpSession.removeAttribute("useDate");
    if ((str3 != null) && (str3.equalsIgnoreCase("TRUE"))) {
      localHttpSession.setAttribute("showDate", "TRUE");
    } else {
      localHttpSession.setAttribute("showDate", "FALSE");
    }
    String str4 = (String)localHttpSession.getAttribute("useTime");
    localHttpSession.removeAttribute("useTime");
    if ((str4 != null) && (str4.equalsIgnoreCase("TRUE"))) {
      localHttpSession.setAttribute("showTime", "TRUE");
    } else {
      localHttpSession.setAttribute("showTime", "FALSE");
    }
    String str5 = (String)localHttpSession.getAttribute("headerAfterFirst");
    localHttpSession.removeAttribute("headerAfterFirst");
    if ((str5 != null) && (str5.equalsIgnoreCase("TRUE"))) {
      localHttpSession.setAttribute("showHeader", "ALLPAGES");
    } else {
      localHttpSession.setAttribute("showHeader", "FIRSTPAGE");
    }
    String str6 = (String)localHttpSession.getAttribute("usePageNumberFormat");
    localHttpSession.removeAttribute("usePageNumberFormat");
    if ((str6 != null) && (str6.equalsIgnoreCase("TRUE")))
    {
      str7 = (String)localHttpSession.getAttribute("pageNumberFormat");
      localHttpSession.setAttribute("pageNumberFormat", str7);
      localHttpSession.setAttribute("showPageNumberFormat", "TRUE");
    }
    else
    {
      localHttpSession.setAttribute("showPageNumberFormat", "FALSE");
    }
    String str7 = (String)localHttpSession.getAttribute("useExtraFooterLine");
    localHttpSession.removeAttribute("useExtraFooterLine");
    if ((str7 != null) && (str7.equalsIgnoreCase("TRUE")))
    {
      str8 = (String)localHttpSession.getAttribute("extraFooterLine");
      localHttpSession.setAttribute("extraFooterLine", str8);
      localHttpSession.setAttribute("showExtraFooterLine", "TRUE");
    }
    else
    {
      localHttpSession.setAttribute("showExtraFooterLine", "FALSE");
    }
    String str8 = (String)localHttpSession.getAttribute("footerFirstPage");
    localHttpSession.removeAttribute("footerFirstPage");
    if ((str8 != null) && (str8.equalsIgnoreCase("TRUE"))) {
      localHttpSession.setAttribute("showFooter", "ALLPAGES");
    } else {
      localHttpSession.setAttribute("showFooter", "SKIPFIRSTPAGE");
    }
    return super.getSuccessURL();
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.reporting.SetHeaderFooter
 * JD-Core Version:    0.7.0.1
 */