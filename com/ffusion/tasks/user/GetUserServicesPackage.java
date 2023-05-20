package com.ffusion.tasks.user;

import com.ffusion.beans.business.MarketSegment;
import com.ffusion.beans.business.MarketSegments;
import com.ffusion.beans.business.ServicesPackage;
import com.ffusion.beans.business.ServicesPackages;
import com.ffusion.beans.user.User;
import com.ffusion.tasks.BaseTask;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetUserServicesPackage
  extends BaseTask
{
  protected String marketSegmentsCollectionName = "MarketSegments";
  protected String servicesPackagesCollectionName = "ServicesPackages";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = null;
    User localUser = (User)localHttpSession.getAttribute("User");
    ServicesPackages localServicesPackages = (ServicesPackages)localHttpSession.getAttribute(this.servicesPackagesCollectionName);
    MarketSegments localMarketSegments = (MarketSegments)localHttpSession.getAttribute(this.marketSegmentsCollectionName);
    ServicesPackage localServicesPackage = null;
    MarketSegment localMarketSegment = null;
    if (localUser == null)
    {
      str = this.taskErrorURL;
      this.error = 38;
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
      localServicesPackage = localServicesPackages.getById(localUser.getServicesPackageIdValue());
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
      localHttpSession.setAttribute("UserServicesPackage", localServicesPackage);
      localHttpSession.setAttribute("UserMarketSegment", localMarketSegment);
      str = this.successURL;
    }
    return str;
  }
  
  public void setMarketSegmentsCollectionName(String paramString)
  {
    this.marketSegmentsCollectionName = paramString;
  }
  
  public void setServicesPackagesCollectionName(String paramString)
  {
    this.servicesPackagesCollectionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.user.GetUserServicesPackage
 * JD-Core Version:    0.7.0.1
 */