package com.ffusion.tasks.portal;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.portal.NewsHeadline;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Portal;
import com.ffusion.services.PortalContentProvider;
import com.ffusion.services.PortalData4;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ViewStockNews
  extends BaseTask
  implements Task
{
  public static final String VIEW_STOCK_NEWS_SESSION = "ViewStockNews";
  private String my = "";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    PortalContentProvider localPortalContentProvider = (PortalContentProvider)localHttpSession.getAttribute("PortalContentProvider");
    PortalData4 localPortalData4 = null;
    if (localPortalContentProvider == null) {
      try
      {
        localPortalData4 = (PortalData4)localHttpSession.getAttribute("PortalData");
      }
      catch (Exception localException) {}
    }
    NewsHeadline localNewsHeadline = null;
    try
    {
      HashMap localHashMap = new HashMap();
      if (localPortalContentProvider != null) {
        localHashMap.put("SERVICE", localPortalContentProvider);
      } else if (localPortalData4 != null) {
        localHashMap.put("SERVICE", localPortalContentProvider);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localNewsHeadline = Portal.getNews(localSecureUser, this.my, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("ViewStockNews", localNewsHeadline);
    }
    return str;
  }
  
  public String getNewsURL()
  {
    return this.my;
  }
  
  public void setNewsURL(String paramString)
  {
    this.my = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.portal.ViewStockNews
 * JD-Core Version:    0.7.0.1
 */