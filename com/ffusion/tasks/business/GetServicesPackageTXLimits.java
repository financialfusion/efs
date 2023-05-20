package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.beans.business.TransactionLimits;
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

public class GetServicesPackageTXLimits
  extends BaseTask
  implements BusinessTask
{
  private int hA;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    TransactionLimits localTransactionLimits = null;
    try
    {
      HashMap localHashMap = null;
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localTransactionLimits = Business.getTXLimitsByGroupId(localSecureUser, this.hA, "per package", localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0) {
      localHttpSession.setAttribute("ServicesPackageTransactionLimits", localTransactionLimits);
    }
    return str;
  }
  
  public void setServicesPackageEntGroupId(String paramString)
  {
    try
    {
      this.hA = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
  
  public void setServicesPackageEntGroupId(int paramInt)
  {
    this.hA = paramInt;
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetServicesPackageTXLimits
 * JD-Core Version:    0.7.0.1
 */