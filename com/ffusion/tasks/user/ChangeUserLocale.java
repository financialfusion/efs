package com.ffusion.tasks.user;

import com.ffusion.beans.user.UserLocale;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.UserLocaleCore;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.tasks.util.GetCurrentDate;
import com.ffusion.util.Localeable;
import com.ffusion.util.UserLocaleConsts;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Locale;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeUserLocale
  extends BaseTask
  implements UserLocaleConsts
{
  private static final String aK3 = "ComImgExt";
  private static final String aK7 = "ComPathExt";
  private static final String aK0 = "DocPath";
  private static final String aLc = "ImgExt";
  private static final String aLa = "PathExt";
  private static final String aKZ = "PathExtNs";
  private static final String aLb = "SecurePath";
  private static final String aK5 = "uriRoot";
  private static final String aK4 = "en";
  private static final String aK9 = "US";
  private static final String aK6 = "locale";
  private static final String aK8 = "CurrentPage";
  private static final String aK2 = "RedirectPage";
  private static final String aK1 = "redirect.jsp";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str1 = paramHttpServletRequest.getParameter("locale");
    String str2 = paramHttpServletRequest.getParameter("CurrentPage");
    String str3 = paramHttpServletRequest.getParameter("RedirectPage");
    if (str3 == null) {
      str3 = "redirect.jsp";
    }
    if ((str2 == null) || (str2.trim().length() == 0))
    {
      this.error = 127;
      return this.taskErrorURL;
    }
    if ((str1 == null) || (str1.trim().length() == 0))
    {
      this.error = 41;
      return this.taskErrorURL;
    }
    StringTokenizer localStringTokenizer = new StringTokenizer(str1, "_");
    String str4 = null;
    String str5 = null;
    if (localStringTokenizer.hasMoreTokens()) {
      str4 = localStringTokenizer.nextToken();
    }
    if (localStringTokenizer.hasMoreTokens()) {
      str5 = localStringTokenizer.nextToken();
    }
    if ((str4 == null) || (str4.trim().length() == 0))
    {
      this.error = 125;
      return this.taskErrorURL;
    }
    Locale localLocale1;
    if (str5 == null) {
      localLocale1 = new Locale(str4);
    } else {
      localLocale1 = new Locale(str4, str5);
    }
    Locale localLocale2 = (Locale)localHttpSession.getAttribute("java.util.Locale");
    if (localLocale1.equals(localLocale2))
    {
      localObject1 = new URL(str2);
      String str6 = ((URL)localObject1).getPath();
      return (String)localHttpSession.getAttribute("SecurePath") + str3 + "?target=" + str6.toString();
    }
    Object localObject1 = null;
    try
    {
      if (str5 == null) {
        str5 = "";
      }
      localObject1 = UserLocaleCore.getUserLocale(str4, str5);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      return this.serviceErrorURL;
    }
    if (localObject1 == null)
    {
      this.error = 70000;
      return this.serviceErrorURL;
    }
    localHttpSession.setAttribute("UserLocale", localObject1);
    localHttpSession.setAttribute("java.util.Locale", localLocale1);
    GetCurrentDate localGetCurrentDate = (GetCurrentDate)localHttpSession.getAttribute("GetCurrentDate");
    localGetCurrentDate.setDateFormat(((UserLocale)localObject1).getDateFormat());
    Enumeration localEnumeration = localHttpSession.getAttributeNames();
    while (localEnumeration.hasMoreElements())
    {
      str7 = (String)localEnumeration.nextElement();
      localObject2 = localHttpSession.getAttribute(str7);
      if ((localObject2 instanceof Localeable))
      {
        localObject3 = (Localeable)localObject2;
        ((Localeable)localObject3).setLocale(localLocale1);
      }
    }
    String str7 = (String)localHttpSession.getAttribute("PathExt");
    Object localObject2 = null;
    if ((str4.equals("en")) && (str5.equals("US")))
    {
      localObject2 = "jsp/";
      localHttpSession.setAttribute("PathExt", localObject2);
      localHttpSession.setAttribute("PathExtNs", "jsp-ns/");
      localHttpSession.setAttribute("ImgExt", "");
      localHttpSession.setAttribute("ComPathExt", "jsp/");
      localHttpSession.setAttribute("ComImgExt", "");
    }
    else
    {
      localObject2 = str1 + "/jsp/";
      localHttpSession.setAttribute("PathExt", localObject2);
      localHttpSession.setAttribute("PathExtNs", str1 + "/jsp-ns/");
      localHttpSession.setAttribute("ImgExt", str1 + "/");
      localHttpSession.setAttribute("ComPathExt", str1 + "/jsp/");
      localHttpSession.setAttribute("ComImgExt", str1 + "/");
    }
    Object localObject3 = "";
    String str8 = (String)localHttpSession.getAttribute("SecurePath");
    if (str8 != null)
    {
      localObject4 = new StringBuffer(str8);
      localObject3 = ((StringBuffer)localObject4).replace(str8.indexOf(str7), str8.indexOf(str7) + str7.length(), (String)localObject2).toString();
      localHttpSession.setAttribute("SecurePath", localObject3);
    }
    Object localObject4 = (String)localHttpSession.getAttribute("DocPath");
    if (localObject4 != null)
    {
      localObject5 = new StringBuffer((String)localObject4);
      localObject6 = ((StringBuffer)localObject5).replace(((String)localObject4).indexOf(str7), str8.indexOf(str7) + str7.length(), (String)localObject2).toString();
      localHttpSession.setAttribute("DocPath", localObject6);
    }
    Object localObject5 = (String)localHttpSession.getAttribute("uriRoot");
    if (localObject5 != null)
    {
      localObject6 = new StringBuffer((String)localObject5);
      str9 = ((StringBuffer)localObject6).replace(((String)localObject5).indexOf(str7), ((String)localObject5).indexOf(str7) + str7.length(), (String)localObject2).toString();
      localHttpSession.setAttribute("uriRoot", str9);
    }
    localHttpSession.setAttribute("account-agg-init-touched", "false");
    localHttpSession.setAttribute("alerts-init-touched", "false");
    localHttpSession.setAttribute("banking-init-touched", "false");
    localHttpSession.setAttribute("billpresentment-init-touched", "false");
    localHttpSession.setAttribute("checkimage-init-touched", "false");
    localHttpSession.setAttribute("messaging-init-touched", "false");
    localHttpSession.setAttribute("payments-init-touched", "false");
    localHttpSession.setAttribute("portal-init-touched", "false");
    localHttpSession.setAttribute("register-init-touched", "false");
    localHttpSession.setAttribute("stoppayments-init-touched", "false");
    Object localObject6 = new URL(str2);
    String str9 = ((URL)localObject6).getPath();
    if (str9.indexOf(str7) > -1) {
      str9 = (String)localObject3 + str9.substring(str9.indexOf(str7) + str7.length());
    }
    String str10 = (String)localObject3 + str3 + "?target=" + str9;
    return str10;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.ChangeUserLocale
 * JD-Core Version:    0.7.0.1
 */