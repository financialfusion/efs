package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.ServiceFeature;
import com.ffusion.beans.business.ServiceFeatures;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetServicesPackageFeatures
  extends BaseTask
  implements BusinessTask
{
  private int hv;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ServiceFeatures localServiceFeatures1 = (ServiceFeatures)localHttpSession.getAttribute("MarketSegmentFeatures");
    if (localServiceFeatures1 == null)
    {
      this.error = 4120;
      return this.taskErrorURL;
    }
    ServiceFeatures localServiceFeatures2 = null;
    SecureUser localSecureUser = null;
    try
    {
      HashMap localHashMap = null;
      localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localServiceFeatures2 = Business.getServiceFeaturesByGroupId(localSecureUser, this.hv, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      ServiceFeatures localServiceFeatures3 = jdMethod_int(localServiceFeatures1, localServiceFeatures2, localSecureUser.getLocale());
      localHttpSession.setAttribute("ServicesPackageFeatures", localServiceFeatures3);
    }
    return str;
  }
  
  public void setServicesPackageId(String paramString)
  {
    try
    {
      this.hv = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setServicesPackageId(int paramInt)
  {
    this.hv = paramInt;
  }
  
  private ServiceFeatures jdMethod_int(ServiceFeatures paramServiceFeatures1, ServiceFeatures paramServiceFeatures2, Locale paramLocale)
  {
    Hashtable localHashtable = new Hashtable();
    Iterator localIterator = paramServiceFeatures1.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = new ServiceFeature(paramLocale);
      ((ServiceFeature)localObject1).set((ServiceFeature)localIterator.next());
      ((ServiceFeature)localObject1).setSelected("0");
      localHashtable.put(((ServiceFeature)localObject1).getFeatureName(), localObject1);
    }
    localIterator = null;
    localIterator = paramServiceFeatures2.iterator();
    while (localIterator.hasNext())
    {
      localObject1 = (ServiceFeature)localIterator.next();
      localObject2 = ((ServiceFeature)localObject1).getFeatureName();
      localHashtable.put(((ServiceFeature)localObject1).getFeatureName(), localObject1);
    }
    Object localObject1 = new ServiceFeatures();
    Object localObject2 = localHashtable.elements();
    while (((Enumeration)localObject2).hasMoreElements()) {
      ((ServiceFeatures)localObject1).add((ServiceFeature)((Enumeration)localObject2).nextElement());
    }
    return localObject1;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetServicesPackageFeatures
 * JD-Core Version:    0.7.0.1
 */