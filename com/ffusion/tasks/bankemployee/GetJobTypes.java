package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
import com.ffusion.csil.beans.entitlements.EntitlementGroups;
import com.ffusion.csil.core.BankEmployeeAdmin;
import com.ffusion.tasks.BaseTask;
import com.ffusion.tasks.MapError;
import com.ffusion.util.entitlements.EntitlementsUtil;
import java.io.IOException;
import java.util.HashMap;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class GetJobTypes
  extends BaseTask
  implements BankEmployeeTask
{
  protected boolean _entBypass = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    EntitlementGroups localEntitlementGroups = null;
    try
    {
      HashMap localHashMap = new HashMap(1);
      if (this._entBypass) {
        localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
      }
      SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
      localEntitlementGroups = BankEmployeeAdmin.getJobTypes(localSecureUser, localHashMap);
    }
    catch (CSILException localCSILException)
    {
      this.error = MapError.mapError(localCSILException);
      str = this.serviceErrorURL;
    }
    finally
    {
      this._entBypass = false;
    }
    if ((this.error == 0) || (this.error == 12))
    {
      localHttpSession.setAttribute("JobTypes", localEntitlementGroups);
      str = this.successURL;
    }
    return str;
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this._entBypass = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this._entBypass);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.GetJobTypes
 * JD-Core Version:    0.7.0.1
 */