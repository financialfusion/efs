package com.ffusion.tasks.business;

import com.ffusion.beans.business.Business;
import com.ffusion.beans.business.MarketSegment;
import com.ffusion.beans.business.MarketSegments;
import com.ffusion.beans.business.ServicesPackage;
import com.ffusion.beans.business.ServicesPackages;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBusinessServicesPackage
  extends BaseTask
  implements BusinessTask
{
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    Business localBusiness = (Business)localHttpSession.getAttribute("Business");
    ServicesPackages localServicesPackages = (ServicesPackages)localHttpSession.getAttribute("ServicesPackages");
    MarketSegments localMarketSegments = (MarketSegments)localHttpSession.getAttribute("MarketSegments");
    ServicesPackage localServicesPackage = null;
    MarketSegment localMarketSegment = null;
    if (localBusiness == null)
    {
      str = this.taskErrorURL;
      this.error = 4104;
    }
    if ((this.error == 0) && (localServicesPackages == null))
    {
      str = this.taskErrorURL;
      this.error = 4115;
    }
    if ((this.error == 0) && (localMarketSegments == null))
    {
      str = this.taskErrorURL;
      this.error = 4110;
    }
    if (this.error == 0)
    {
      localServicesPackage = localServicesPackages.getById(localBusiness.getServicesPackageIdValue());
      if (localServicesPackage == null)
      {
        this.error = 4118;
        str = this.taskErrorURL;
      }
    }
    if (this.error == 0)
    {
      localMarketSegment = localMarketSegments.getById(localServicesPackage.getMarketSegmentIdValue());
      if (localMarketSegment == null)
      {
        this.error = 4117;
        str = this.taskErrorURL;
      }
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("BusinessServicesPackage", localServicesPackage);
      localHttpSession.setAttribute("BusinessMarketSegment", localMarketSegment);
      str = this.successURL;
    }
    return str;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetBusinessServicesPackage
 * JD-Core Version:    0.7.0.1
 */