package com.ffusion.tasks.bankemployee;

import com.ffusion.beans.SecureUser;
import com.ffusion.csil.CSILException;
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

public class HasDirectReports
  extends BaseTask
  implements BankEmployeeTask
{
  protected String _id;
  protected String _affiliatebank;
  private boolean uo;
  private boolean up = false;
  
  public String process(HttpServlet paramHttpServlet, HttpServletRequest paramHttpServletRequest, HttpServletResponse paramHttpServletResponse)
    throws IOException
  {
    this.error = 0;
    String str = this.successURL;
    HttpSession localHttpSession = paramHttpServletRequest.getSession();
    if ((this._id != null) && (this._id.length() != 0))
    {
      try
      {
        HashMap localHashMap = new HashMap(1);
        if (this.up) {
          localHashMap = EntitlementsUtil.allowEntitlementBypass(localHashMap);
        }
        SecureUser localSecureUser = (SecureUser)localHttpSession.getAttribute("SecureUser");
        if ((this._affiliatebank != null) && (this._affiliatebank.length() != 0)) {
          this.uo = BankEmployeeAdmin.hasDirectReports(localSecureUser, Integer.parseInt(this._id), Integer.parseInt(this._affiliatebank), localHashMap);
        } else {
          this.uo = BankEmployeeAdmin.hasDirectReports(localSecureUser, Integer.parseInt(this._id), 0, localHashMap);
        }
      }
      catch (CSILException localCSILException)
      {
        this.error = MapError.mapError(localCSILException);
        str = this.serviceErrorURL;
      }
    }
    else
    {
      this.error = 5501;
      str = this.taskErrorURL;
    }
    return str;
  }
  
  public void setEmployeeId(String paramString)
  {
    this._id = paramString;
  }
  
  public void setAffiliateBankID(String paramString)
  {
    this._affiliatebank = paramString;
  }
  
  public String getHasReport()
  {
    return String.valueOf(this.uo).toUpperCase();
  }
  
  public void setEntitlementBypass(String paramString)
  {
    this.up = Boolean.valueOf(paramString).booleanValue();
  }
  
  public String getEntitlementBypass()
  {
    return Boolean.toString(this.up);
  }
}


/* Location:           D:\drops\jd\jars\efs.jar
 * Qualified Name:     com.ffusion.tasks.bankemployee.HasDirectReports
 * JD-Core Version:    0.7.0.1
 */