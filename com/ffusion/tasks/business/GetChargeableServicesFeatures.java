package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.ServiceFeatures;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetChargeableServicesFeatures
  extends BaseTask
  implements BusinessTask
{
  private int gM;
  private String gL = "ServiceFeatures";
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    ServiceFeatures localServiceFeatures = null;
    try
    {
      HashMap localHashMap = null;
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localServiceFeatures = Business.getChargeableServiceFeaturesByGroupId(localSecureUser, this.gM, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute(this.gL, localServiceFeatures);
    }
    return str;
  }
  
  public void setGroupId(int paramInt)
  {
    this.gM = paramInt;
  }
  
  public void setGroupId(String paramString)
  {
    try
    {
      this.gM = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setCollectionName(String paramString)
  {
    this.gL = paramString;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetChargeableServicesFeatures
 * JD-Core Version:    0.7.0.1
 */