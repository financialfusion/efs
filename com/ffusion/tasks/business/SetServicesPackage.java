package com.ffusion.tasks.business;

import com.ffusion.beans.business.ServicesPackage;
import com.ffusion.beans.business.ServicesPackages;
import com.ffusion.tasks.BaseTask;
import com.ffusion.util.logging.DebugLog;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SetServicesPackage
  extends BaseTask
  implements BusinessTask
{
  private int hB;
  private String hC = "ServicesPackage";
  protected String servicePackagesCollectionName = "ServicesPackages";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    String str = this.taskErrorURL;
    ServicesPackage localServicesPackage = null;
    ServicesPackages localServicesPackages = (ServicesPackages)localHttpSession.getAttribute(this.servicePackagesCollectionName);
    if (localServicesPackages == null)
    {
      this.error = 4115;
    }
    else
    {
      localServicesPackage = localServicesPackages.getById(this.hB);
      if (localServicesPackage == null)
      {
        this.error = 4116;
      }
      else
      {
        localHttpSession.setAttribute(this.hC, localServicesPackage);
        str = this.successURL;
      }
    }
    return str;
  }
  
  public void setServicesPackageId(String paramString)
  {
    try
    {
      this.hB = Integer.parseInt(paramString);
    }
    catch (Exception localException)
    {
      DebugLog.throwing("SetServicesPackage Task Exception: ", localException);
    }
  }
  
  public void setServicesPackageSessionName(String paramString)
  {
    this.hC = paramString;
  }
  
  public void setServicePackagesCollectionName(String paramString)
  {
    this.servicePackagesCollectionName = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.SetServicesPackage
 * JD-Core Version:    0.7.0.1
 */