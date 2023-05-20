package com.ffusion.tasks.business;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.core.Business;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetBankIdsByServicesPackage
  extends BaseTask
  implements BusinessTask
{
  int gG;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
    ArrayList localArrayList = null;
    try
    {
      HashMap localHashMap = new HashMap(1);
      localArrayList = Business.getBankIdsByServicesPackage(localSecureUser, this.gG, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    if (this.error == 0)
    {
      localHttpSession.setAttribute("BusinessBankIdsListByServicesPackage", localArrayList);
      str = this.successURL;
    }
    return str;
  }
  
  public void setServicesPackageId(String paramString)
  {
    try
    {
      this.gG = Integer.parseInt(paramString);
    }
    catch (Exception localException) {}
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.business.GetBankIdsByServicesPackage
 * JD-Core Version:    0.7.0.1
 */